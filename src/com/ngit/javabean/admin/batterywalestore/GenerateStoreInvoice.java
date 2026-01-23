/*****************************************************
NGIT. Confidential.
@File Name		: GenerateExcel.java
@Description	: TO generate Excel Report
@Author			: Sai Krishna Daddala  
@Date			: 18-08-2009
@Reviewed By	: 
*******************************************************/


package com.ngit.javabean.admin.batterywalestore;

import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.mail.MailTrans;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Locale;
import java.util.Iterator;
import java.util.Set;
import java.text.SimpleDateFormat;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Vector;



import java.util.Hashtable;
import java.util.Enumeration;
import java.io.FileInputStream;
import java.util.Properties;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.Range;
import jxl.CellView;
import jxl.CellReferenceHelper;
import jxl.HeaderFooter;
import jxl.write.WritableWorkbook;
import jxl.write.WritableSheet;
import jxl.write.WritableFont;
import jxl.write.WritableCellFormat;
import jxl.write.NumberFormats;
import jxl.write.DateFormats;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.DateTime;
import jxl.write.NumberFormat;
import jxl.write.DateFormat;
import jxl.write.WriteException;
import jxl.write.WritableHyperlink;
import jxl.write.Boolean;
import jxl.write.Formula;
import jxl.write.WritableImage;
import jxl.write.WritableCellFeatures;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.ScriptStyle;
import jxl.format.Orientation;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;
import java.text.DecimalFormat;
import java.lang.*;

//For PDF fro IText.jar
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.html.HtmlWriter;
import com.lowagie.text.pdf.*;
import java.awt.Color;
import com.lowagie.text.Header;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Font;
import com.lowagie.text.Element;

import java.io.*;
import java.util.*;
import javax.servlet.ServletContext;
//Pdf Import Ends here
import javax.servlet.http.HttpServlet;

public class GenerateStoreInvoice extends HttpServlet 
{
	private String outfile;
	private String strPDFFilePath1;
	private String strRelativePath;
	private WritableWorkbook workbook;
	Properties propsMOPConfig;
	QueryManager qm;

	public GenerateStoreInvoice()
	{
	}
	public GenerateStoreInvoice(String ofn)
	{
		outfile = ofn;
		//reading props file starts here
		String strMOPConfig = "/bookbattery/properties/bookbatteryconfig.properties";
		LogLevel.DEBUG(5, new Throwable(), "propsMOPConfig---->"+propsMOPConfig);
		propsMOPConfig = new Properties();
		ServletContext context = getServletContext();
		 
		try
		{
			FileInputStream fin      = new FileInputStream(new File(context.getRealPath(strMOPConfig)));
			propsMOPConfig.load(fin);
			fin.close();
		}
		catch(IOException ioe)
		{
			LogLevel.ERROR(0, ioe,"IOException in loading properties : " + ioe);
			System.exit(-1);
		}
	}

	public  void write(ArrayList arrExcelHeader,ArrayList dbHeaders,Vector alReport,String sheetTitle) throws IOException, WriteException
	{

		LogLevel.DEBUG(5,new Throwable(),"GenerateExcel : write() function");	   	
		LogLevel.DEBUG(5,new Throwable(),"outfile is : "+outfile);

		WorkbookSettings ws = new WorkbookSettings();
		ws.setLocale(new Locale("en", "EN"));

		workbook = Workbook.createWorkbook(new File(outfile), ws);
		LogLevel.DEBUG(5,new Throwable(),"Successfully created workbook");
		WritableSheet sheet = workbook.createSheet(sheetTitle, 0);

		writeExcelSheet(sheet,arrExcelHeader,dbHeaders,alReport);

		// Modify the colour palette to bright red for the lime colour
		workbook.setColourRGB(Colour.LIME, 0xff, 0, 0);

		workbook.write();
		workbook.close();

	} 
	private void writeExcelSheet(WritableSheet ws,ArrayList arrExcelHeader,ArrayList dbHeaders,Vector alReport) throws WriteException
	{
		LogLevel.DEBUG(5,new Throwable(),"Inside writeExcelSheet()"); 
		WritableFont wf = new WritableFont(WritableFont.ARIAL, 10);
		wf.setBoldStyle(WritableFont.BOLD );

		WritableCellFormat wcf = new WritableCellFormat(wf);

		CellView cv = new CellView();
		cv.setSize(15 * 256);
		cv.setFormat(wcf);
		for (int l=0;l<arrExcelHeader.size() ;l++ )
		{
			ws.setColumnView(l, cv);
		} 
		try
		{
			String line="";
			
			for (int ij=0;ij<arrExcelHeader.size() ;ij++ )
			{
 				String strHeader=(String)arrExcelHeader.get(ij);
				//System.out.println("strHeader:"+strHeader);
				LogLevel.DEBUG(5,new Throwable(),"strHeader is : "+strHeader);

				strHeader = strHeader.replaceAll("_"," ");
				//Colored labels for Headings
				WritableFont wf2 = new WritableFont(WritableFont.TAHOMA, 10);
				wf2.setBoldStyle(WritableFont.BOLD );
				wf2.setColour(Colour.WHITE);
				WritableCellFormat wcf2 = new WritableCellFormat(wf2);
				wcf2.setBackground(Colour.DARK_PURPLE);
				CellView cv2 = new CellView();
				cv2.setSize(15 * 256);
				cv2.setFormat(wcf2);
				Label lh = new Label(ij, 0, strHeader,wcf2);
				ws.addCell(lh);
 			}
			Hashtable hashContent = new Hashtable();
			int i=2;
			String serialno="0";
			for (int j=0; j<alReport.size(); j++)
			{			
				 
				hashContent   = (Hashtable)alReport.get(j);
				LogLevel.DEBUG(5,new Throwable(),"hashContent is : "+hashContent);

				int value = Integer.parseInt(serialno);
				LogLevel.DEBUG(5,new Throwable(),"value is : "+value);
				int nextValue = value + 1; // find the int value plus 1

				serialno= String.valueOf(nextValue) ;			
				LogLevel.DEBUG(4,new Throwable(),"serialno value"+serialno);
						
				for (int ik=0;ik < dbHeaders.size() ;ik++ )
				{
					String strHeader=(String)dbHeaders.get(ik);
					String strData=String.valueOf(hashContent.get(strHeader));
		 
					LogLevel.DEBUG(5,new Throwable(),"strHeader is : "+strHeader);
					LogLevel.DEBUG(5,new Throwable(),"strData is : "+strData);
					if(strHeader.equals("0") || strHeader.equals(0))
					{
						strData=serialno;
					}
					if(strData.equals("null") || strData.equals(null) || strData.equals("0000-00-00"))
					{
						strData = "-";
					}
					else
					{
					if(strHeader.equals("operator"))
						{
							if(strData.equals("no"))
							{

								strData ="Customer";
							}
							else
							{

								strData ="Operator";
							}

						}
						else
						{

							strData = strData;

						}
				     }
					LogLevel.DEBUG(5,new Throwable(),"strData is Prasanna: "+strData);
					Label l1 = new Label(ik,i,strData);
					ws.addCell(l1);
					
				}
				i++;
				
							
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception during excel creation"+e);
			e.printStackTrace();
		}
	}
	/*******************PDF Creation for Batteries Starts*********************/
	public int writeToBatteryPdf(String ordernumber)
	{
		try
		{
				qm = QueryManager.getInstance(propsMOPConfig) ;
			ArrayList arrPDFHeader = new ArrayList();
 			ArrayList dbHeaders = new ArrayList();
			
			LogLevel.DEBUG(4,new Throwable(),"Inside writeToPdf");
			LogLevel.DEBUG(5,new Throwable(),"strTempTextFile in WriteToExcel: "+ordernumber);
			 
 			
			String verificationcode = ""; 
 			LogLevel.DEBUG(5, new Throwable(), "generateinvoice Inside:");
			LogLevel.DEBUG(5, new Throwable(), "generateinvoice ordernumber:" +ordernumber);
			
			String strSqlQry = "Select ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_address,retailer_name,retailer_mobilnumber,retailer_emailis,state,city,area,pincode,bat_type,battery_brand,battery_model,battery_capacity,quantity,veh_name,veh_model,MRP_Price,price,witholdbatprice,erp_pre_tax,order_type,payment_mode,payment_mode_type,battery_commission_amount,total_commission_amount,order_status,CAST(creation_date AS CHAR) as creation_date,CAST(installed_date AS CHAR) as installed_date,CAST(updated_date AS CHAR) as updated_date,CAST(postpone_date AS CHAR) as postponed_date FROM battery_order_details WHERE  order_number='"+ordernumber+"'";
			
			LogLevel.DEBUG(5,new Throwable(),"strSqlQry :"+strSqlQry);
			 
			Vector DataVector=qm.executeQuery(strSqlQry);
			
			Hashtable ht = qm.getRow(strSqlQry);
			
			String ordertype="battery";
			String order_number=(String)ht.get("order_number");
			LogLevel.DEBUG(5,new Throwable(),"order_number :"+order_number);
						
			String consumer_name=(String)ht.get("consumer_name");
			LogLevel.DEBUG(5,new Throwable(),"consumer_name :"+consumer_name);
			
			String consumer_mobnumber=(String)ht.get("consumer_mobnumber");
			LogLevel.DEBUG(5,new Throwable(),"consumer_mobnumber :"+consumer_mobnumber);
			
			String consumer_emailid=(String)ht.get("consumer_emailid");
			LogLevel.DEBUG(5,new Throwable(),"consumer_emailid :"+consumer_emailid);
			
			String customer_address=(String)ht.get("consumer_address");
			LogLevel.DEBUG(5,new Throwable(),"customer_address :"+customer_address);
			
			String retailer_name=(String)ht.get("retailer_name");
			LogLevel.DEBUG(5,new Throwable(),"retailer_name :"+retailer_name);
			
			String retailer_mobilnumber=(String)ht.get("retailer_mobilnumber");
			LogLevel.DEBUG(5,new Throwable(),"retailer_mobilnumber :"+retailer_mobilnumber);
			
			String retailer_emailis=(String)ht.get("retailer_emailis");
			LogLevel.DEBUG(5,new Throwable(),"retailer_emailis :"+retailer_emailis);
			 
			String state=(String)ht.get("state");
			LogLevel.DEBUG(5,new Throwable(),"state :"+state);
			
			String city=(String)ht.get("city");
			LogLevel.DEBUG(5,new Throwable(),"city :"+city);
			
			String area=(String)ht.get("area");
			LogLevel.DEBUG(5,new Throwable(),"area :"+area);
			
			String pincode=(String)ht.get("pincode");
			LogLevel.DEBUG(5,new Throwable(),"pincode :"+pincode);
			
			String bat_type=(String)ht.get("bat_type");
			LogLevel.DEBUG(5,new Throwable(),"bat_type :"+bat_type); 
			
			String battery_brand=(String)ht.get("battery_brand");
			LogLevel.DEBUG(5,new Throwable(),"battery_brand :"+battery_brand);
			
			String battery_model=(String)ht.get("battery_model");
			LogLevel.DEBUG(5,new Throwable(),"battery_model :"+battery_model);
			
			String battery_capacity=(String)ht.get("battery_capacity");
			LogLevel.DEBUG(5,new Throwable(),"battery_capacity :"+battery_capacity);
			
			String quantity=(String)ht.get("quantity");
			int QTY = Integer.parseInt(quantity);
			LogLevel.DEBUG(5,new Throwable(),"quantity :"+quantity);
			
			String veh_name=(String)ht.get("veh_name");
			LogLevel.DEBUG(5,new Throwable(),"veh_name :"+veh_name);
			
			String veh_model=(String)ht.get("veh_model");
			LogLevel.DEBUG(5,new Throwable(),"veh_model :"+veh_model);
			
			String MRP_Price=(String)ht.get("MRP_Price");
			LogLevel.DEBUG(5,new Throwable(),"MRP_Price :"+MRP_Price);
			
			String price=(String)ht.get("price");
			int strprice = Integer.parseInt(price);
			LogLevel.DEBUG(5,new Throwable(),"price :"+price);
			
			String witholdbatprice=(String)ht.get("witholdbatprice");
			int strwitholdbatprice = Integer.parseInt(witholdbatprice);
			LogLevel.DEBUG(5,new Throwable(),"witholdbatprice :"+witholdbatprice);
			
			String erp_pre_tax=(String)ht.get("erp_pre_tax");
			int strerp_pre_tax = Integer.parseInt(erp_pre_tax);
			LogLevel.DEBUG(5,new Throwable(),"erp_pre_tax :"+erp_pre_tax);
			
			String order_type=(String)ht.get("order_type");
			LogLevel.DEBUG(5,new Throwable(),"order_type :"+order_type);
			
			String payment_billing_mode=(String)ht.get("payment_mode");
			LogLevel.DEBUG(5,new Throwable(),"payment_billing_mode :"+payment_billing_mode);
			
			String payment_mode_ordered=(String)ht.get("payment_mode_type");
			LogLevel.DEBUG(5,new Throwable(),"payment_mode_ordered :"+payment_mode_ordered);
			
			String order_status=(String)ht.get("order_status");
			LogLevel.DEBUG(5,new Throwable(),"order_status :"+order_status);
			
			String creation_date=(String)ht.get("creation_date");
			LogLevel.DEBUG(5,new Throwable(),"creation_date :"+creation_date);
			
			String installed_date=(String)ht.get("installed_date");
			LogLevel.DEBUG(5,new Throwable(),"installed_date :"+installed_date);
			
			/************** Headers *********/
			arrPDFHeader.add("ITEM DESCRIPTION");
			arrPDFHeader.add("HSN/SAC");
			arrPDFHeader.add("QUANTITY/UOM");
			arrPDFHeader.add("UNIT PRICE");
			arrPDFHeader.add("CGST");				
			arrPDFHeader.add("SGST");	
			arrPDFHeader.add("AMOUNT");
			
			/*************Data Content*********/
			dbHeaders.add("battery_model");						
 			dbHeaders.add("quantity");
			dbHeaders.add("price");
			dbHeaders.add("comission");
			dbHeaders.add("comission");	
			dbHeaders.add("comission");	
			dbHeaders.add("comission");	
			
					
			double SCRAP_BATTERY = 0;	
			double UNIT_QTY = 0;	
			/**************Battery COST FOrmulas**************/
			
			double UNIT_PRICE = 0;
			double UNIT_PRICE_CGST = 0;
			double UNIT_PRICE_SGST = 0;
			double UNIT_PRICE_TOTAL = 0;
			
			
			/**************SCRAP Battery COST FOrmulas**************/
 			double UNIT_SCRAP = 0;
 			double UNIT_SCRAP_CGST = 0;
			double UNIT_SCRAP_SGST = 0;
			double UNIT_SCRAP_IGST = 0;
			double UNIT_SCRAP_TOTAL = 0;
			
			/**************TOTAL Battery COST FOrmulas**************/
			double UNIT_TOTAL = 0.00;
			double UNIT_TAX = 0.00;
			double UNIT_CGST_TOTAL = 0.00;
			double UNIT_SGST_TOTAL = 0.00;
			double UNIT_AMOUNT_TOTAL = 0.00;
			double UNIT_GRAND_TOTAL = 0.00;
			double UNIT_OVERALL_TOTAL = 0.00;
			 
			
			UNIT_QTY = QTY;
			LogLevel.DEBUG(5,new Throwable(),"UNIT_QTY :"+UNIT_QTY);
			SCRAP_BATTERY = (strprice-strwitholdbatprice) * UNIT_QTY; 
			if(order_type.equals("New")){	/***************For New ******************/
			
				UNIT_PRICE = strprice * UNIT_QTY;
				
				UNIT_PRICE_CGST = (UNIT_PRICE*14/100);
				UNIT_PRICE_SGST = (UNIT_PRICE*14/100);
				UNIT_PRICE_TOTAL = UNIT_PRICE + UNIT_PRICE_CGST + UNIT_PRICE_SGST;
				
				UNIT_TOTAL = UNIT_PRICE * UNIT_QTY;	
				UNIT_CGST_TOTAL = UNIT_PRICE_CGST * UNIT_QTY;	
				UNIT_SGST_TOTAL = UNIT_PRICE_SGST * UNIT_QTY;	
				UNIT_TAX = (UNIT_CGST_TOTAL + UNIT_SGST_TOTAL);	
				UNIT_AMOUNT_TOTAL = (UNIT_PRICE + UNIT_CGST_TOTAL + UNIT_SGST_TOTAL) ;
				UNIT_GRAND_TOTAL = UNIT_PRICE;
				UNIT_OVERALL_TOTAL = UNIT_PRICE_TOTAL;
				
			} else {	/***************For Replaced ******************/
			
				UNIT_PRICE = (strprice/1.28) * UNIT_QTY;
				
				UNIT_PRICE_CGST = (UNIT_PRICE*14/100);
				UNIT_PRICE_SGST = (UNIT_PRICE*14/100);
				UNIT_PRICE_TOTAL = (UNIT_PRICE + UNIT_PRICE_CGST + UNIT_PRICE_SGST);
				UNIT_SCRAP = (SCRAP_BATTERY/1.18);	
					
				UNIT_SCRAP_CGST = (UNIT_SCRAP*9/100);
				UNIT_SCRAP_SGST = (UNIT_SCRAP*9/100);
				
				UNIT_CGST_TOTAL = UNIT_PRICE_CGST - UNIT_SCRAP_CGST;	
				UNIT_SGST_TOTAL = UNIT_PRICE_SGST - UNIT_SCRAP_SGST;	
				  
				
				UNIT_TOTAL = (UNIT_PRICE - UNIT_SCRAP);
				UNIT_TAX = (UNIT_PRICE_SGST + UNIT_PRICE_SGST);
				
				UNIT_AMOUNT_TOTAL = (UNIT_PRICE_TOTAL - SCRAP_BATTERY);
				UNIT_GRAND_TOTAL = UNIT_PRICE;
				UNIT_OVERALL_TOTAL = UNIT_PRICE_TOTAL;
			} 
			 
			
			UNIT_PRICE = Math.round (UNIT_PRICE * 100.0) / 100.0;
			UNIT_PRICE_CGST = Math.round (UNIT_PRICE_CGST * 100.0) / 100.0;
			UNIT_PRICE_SGST = Math.round (UNIT_PRICE_SGST * 100.0) / 100.0;
			UNIT_PRICE_TOTAL = Math.round (UNIT_PRICE_TOTAL * 100.0) / 100.0;
			UNIT_SCRAP = Math.round (UNIT_SCRAP * 100.0) / 100.0;
			UNIT_SCRAP_CGST = Math.round (UNIT_SCRAP_CGST * 100.0) / 100.0;
			UNIT_SCRAP_SGST = Math.round (UNIT_SCRAP_SGST * 100.0) / 100.0;
			UNIT_SCRAP_TOTAL = Math.round (UNIT_SCRAP_TOTAL * 100.0) / 100.0;
			UNIT_TOTAL = Math.round (UNIT_TOTAL * 100.0) / 100.0;
			UNIT_CGST_TOTAL = Math.round (UNIT_CGST_TOTAL * 100.0) / 100.0;
			UNIT_SGST_TOTAL = Math.round (UNIT_SGST_TOTAL * 100.0) / 100.0;
			UNIT_AMOUNT_TOTAL = Math.round (UNIT_AMOUNT_TOTAL * 100.0) / 100.0;
			UNIT_TAX = Math.round (UNIT_TAX * 100.0) / 100.0;
			UNIT_GRAND_TOTAL = Math.round (UNIT_GRAND_TOTAL * 100.0) / 100.0;
		 
	    LogLevel.DEBUG(5, new Throwable(), "UNIT_PRICE :" + UNIT_PRICE);
		LogLevel.DEBUG(5, new Throwable(), "UNIT_PRICE_TOTAL :" + UNIT_PRICE_TOTAL);
		LogLevel.DEBUG(5, new Throwable(), "SCRAP_BATTERY :" + SCRAP_BATTERY);
		LogLevel.DEBUG(5, new Throwable(), "UNIT_SCRAP :" + UNIT_SCRAP);
		LogLevel.DEBUG(5, new Throwable(), "UNIT_SCRAP_IGST :" + UNIT_SCRAP_IGST);
 		LogLevel.DEBUG(5, new Throwable(), "UNIT_TOTAL :" + UNIT_TOTAL);
		LogLevel.DEBUG(5, new Throwable(), "UNIT_TAX :" + UNIT_TAX);
		LogLevel.DEBUG(5, new Throwable(), "UNIT_AMOUNT_TOTAL :" + UNIT_AMOUNT_TOTAL);
		LogLevel.DEBUG(5, new Throwable(), "UNIT_GRAND_TOTAL :" + UNIT_GRAND_TOTAL);
		
		/**********************Invoice Directory Creation *****************/		
		Date date = new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			int currentYear = calendar.get(Calendar.YEAR);
			//Add one to month {0 - 11}
			//int months = calendar.get(Calendar.MONTH) + 1;
			String months = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

			int day = calendar.get(Calendar.DAY_OF_MONTH);
			SimpleDateFormat sdfDate=new SimpleDateFormat("dd-MM-yyyy");
			String strDate = sdfDate.format(date);
			 
			
			LogLevel.DEBUG(5,new Throwable(),"months :"+months);
			LogLevel.DEBUG(5,new Throwable(),"currentYear :"+currentYear);
			
			String Sequence_Query = "select seq_number from batterystore_invoice where seq_number!='0' and store_name='"+retailer_name+"' and  order_type='"+ordertype+"' order by inv_id desc limit 1";
			LogLevel.DEBUG(5,new Throwable(),"Sequence_Query :"+Sequence_Query );
			Hashtable htSeq_number = qm.getRow(Sequence_Query); 
								
				int invoice_code = 0;
				int Seq_number_Add = 0;
				if(htSeq_number.isEmpty())
				{ 
					invoice_code = 1;
					String verificationcode1 = Integer.toString(invoice_code);
					verificationcode =verificationcode1;
					LogLevel.DEBUG(5, new Throwable(), "verificationcode Empty:" + verificationcode);
					LogLevel.DEBUG(5,new Throwable(),"INSIDE EMPTY FIRST Time");
					
				} else {
					
					LogLevel.DEBUG(5,new Throwable(),"INSIDE ELSE Second Time");
				
					String Seq_number=String.valueOf(htSeq_number.get("seq_number"));
					//int Seq_number=htSeq_number.getInt("seq_number");
					Seq_number_Add = Integer.parseInt(Seq_number);
					invoice_code =  Seq_number_Add + 1;
					LogLevel.DEBUG(5,new Throwable(),"Seq_number String:"+Seq_number);
					LogLevel.DEBUG(5,new Throwable(),"Seq_number_Add Integer:"+Seq_number_Add);
				
					LogLevel.DEBUG(5,new Throwable(),"invoice_code:"+invoice_code);
				  
					String verificationcode1 = Integer.toString(invoice_code);
					verificationcode =verificationcode1;
					LogLevel.DEBUG(5, new Throwable(), "verificationcode 9:" + verificationcode);
					
					 
				}
			String excelName = "ASIST-B2C-Battery-"+verificationcode+".pdf";
			LogLevel.DEBUG(5, new Throwable(), "excelName :" + excelName);
			//String strCMSServerIP = (propsMOPConfig.getProperty("CMS_SERVER_IP")!=null)?(propsMOPConfig.getProperty("CMS_SERVER_IP")).trim():"bookbattery.com";
			//String strCMSSERVERIP	= (propsMOPConfig.getProperty("CMS_SERVER_IP")!=null)?(propsMOPConfig.getProperty("CMS_SERVER_IP")):"";
			//LogLevel.DEBUG(5, new Throwable(), "strCMSServerIP:" + strCMSSERVERIP);
			LogLevel.DEBUG(5, new Throwable(), "excelName :" + excelName);
			String strPDFFilePath = "/home/ngit/tomcat/webapps/bookbattery/userdata/billing/batterystoreinvoice/" +currentYear+ "/" +months+ "/" +city+ "/" +ordertype;
			LogLevel.DEBUG(5, new Throwable(), "strPDFFilePath :" + strPDFFilePath);
			File excelUploadPathCreate = new File(strPDFFilePath);
			LogLevel.DEBUG(5, new Throwable(), "excelUploadPathCreate :" + excelUploadPathCreate);
			if(excelUploadPathCreate.mkdirs())
			{
				LogLevel.DEBUG(5,new Throwable()," PDF for invoice	Name directory created :  "	+ excelUploadPathCreate);
				
			} else {
				
				
			}

			//strPDFRelFilePath	= strPDFRelFilePath +"/"+ currentYear + "/" +months;

			String excelFile = excelUploadPathCreate+"/"+excelName;
			LogLevel.DEBUG(5, new Throwable(), "excelFile :" + excelFile);
			File file=new File(excelFile);
			if(!file.exists()) 
			file.createNewFile(); 
			FileWriter fw=new FileWriter(file);
				
			//String strPdfURL="http://"+strCMSServerIP+"/bookbattery/userdata/billing/salesorders/"+currentYear+"/"+months+"/"+"battery"+"/"+"invoice"+"/"+city+"/"+excelName;
			//String strPdfURL="http://stage1.bookbattery.com/bookbattery/userdata/billing/batterystoreinvoice/"+currentYear+"/"+months+ "/" +city+ "/"  +ordertype+ "/" +excelName;
			String strPdfURL="http://stage1.bookbattery.com/bookbattery/userdata/billing/batterystoreinvoice/"+currentYear+"/"+months+ "/" +city+ "/"  +ordertype+ "/" +excelName;
			LogLevel.DEBUG(5, new Throwable(), "strPdfURL : " + strPdfURL);	
			
			String order_number_iq="";
			String invoice_number_iq="";
			String invoice_url_iq="";
			String invoice_file="";
					
					
			String CheckInvoiceQuery_SQL = "Select order_number,invoice_number,invoice_url,customer_name,store_name,order_type,seq_number from batterystore_invoice where order_number='"+ordernumber+"'";
					
			Hashtable InvoiceQuery_HT = qm.getRow(CheckInvoiceQuery_SQL);
			if(InvoiceQuery_HT.isEmpty())
				{
					
					String strInoviceQry = "insert into batterystore_invoice(order_number,invoice_number,invoice_url,customer_name,store_name,order_type,seq_number)values('"+ordernumber+"','"+excelName+"','"+strPdfURL+"','"+consumer_name+"','"+retailer_name+"','"+ordertype+"','"+invoice_code+"')";
					LogLevel.DEBUG(5,new Throwable(),"strInoviceQry : "+strInoviceQry);
					int reslt = qm.executeUpdate(strInoviceQry);

					if(reslt <0)
					{
						LogLevel.DEBUG(1,new Throwable(),"Failed to Generate! ");
					}
					else
					{
						LogLevel.DEBUG(1,new Throwable(),"Successfully Generated! ");	
					}
				}
				else
				{
					
					 order_number_iq=(String)InvoiceQuery_HT.get("order_number");
					 invoice_number_iq=(String)InvoiceQuery_HT.get("invoice_number");
					 invoice_url_iq=(String)InvoiceQuery_HT.get("invoice_url");
					 invoice_file = invoice_url_iq.replace("http://stage1.bookbattery.com/","/home/ngit/tomcat/webapps/");
				}
		
				/***************************Printing PDF ******************************/			
			Document document = new Document();
			PdfWriter pdf = PdfWriter.getInstance(document,new FileOutputStream(excelFile));
			document.open();
			
			PdfContentByte cb = pdf.getDirectContent();
			cb.setLineWidth(1f);
			cb.moveTo(0, 755);
			cb.lineTo(595, 755);
			cb.stroke();

			
			Image imghead = Image.getInstance("/home/ngit/tomcat/webapps/bookbattery/images/batterywalelogo.png");
			imghead.setAbsolutePosition(50,760);
			imghead.scaleAbsolute(120, 35);
			document.add(imghead);	
				
			
			//Paragraph paragraph= (new Paragraph("Original for Recipient \r\n TAX INVOICE NO : "+excelName.replace(".pdf","")+" \r\n Date : "+strDate+" \r\n P.O.# : "+order_number+" \r\n P.O.Date : "+strDate+"" ,FontFactory.getFont("Tahoma", 9, Font.NORMAL, new Color(0, 0, 0))));
			
			 Paragraph paragraph= (new Paragraph("Original for Recipient \r\n TAX INVOICE NO : "+excelName.replace(".pdf","")+" \r\n Date : "+strDate+" \r\n \r\n \r\n" ,FontFactory.getFont("Tahoma", 9, Font.NORMAL, new Color(0, 0, 0))));
			paragraph.setAlignment(Element.ALIGN_RIGHT);
			paragraph.setSpacingBefore(0.2f);
			document.add(paragraph);
			
			Paragraph parag_order= (new Paragraph("P.O.# : "+order_number+" \r\n P.O.Date : "+strDate+"" ,FontFactory.getFont("Tahoma", 9, Font.NORMAL, new Color(0, 0, 0))));
			parag_order.setAlignment(Element.ALIGN_RIGHT);
			parag_order.setSpacingBefore(2.2f);
			document.add(parag_order);
			
			Paragraph parag1 = (new Paragraph("BookBattery \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t  Bill To:" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag1.setAlignment(Element.ALIGN_LEFT);
			parag1.setSpacingBefore(1.2f);
			document.add(parag1);
			
			Paragraph parag2 = (new Paragraph("Shop No. 3, No. 166,Pattandu Aghrahara \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+consumer_name+"",FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag2.setAlignment(Element.ALIGN_LEFT);
			parag2.setSpacingBefore(0.5f);
			document.add(parag2);
				
			Paragraph parag3 = (new Paragraph("Opp to Govt Polytechnic College \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+customer_address+"",FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag3.setAlignment(Element.ALIGN_LEFT);
			parag3.setSpacingBefore(0.5f);
			document.add(parag3);
				
			Paragraph parag4 = (new Paragraph("WhiteField, Bangalore - 560066 \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t Place of Supply: "+city+"",FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag4.setAlignment(Element.ALIGN_LEFT);
			parag4.setSpacingBefore(0.5f);
			document.add(parag4);
				
			Paragraph parag5 = (new Paragraph("GSTIN No: 29AAJCA7469F2Z7 \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t",FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag5.setAlignment(Element.ALIGN_LEFT);
			parag5.setSpacingBefore(0.5f);
			document.add(parag5);
				
			Paragraph parag6 = (new Paragraph("Phone:+91 9666 300003 \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t Phone:"+consumer_mobnumber+"",FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag6.setAlignment(Element.ALIGN_LEFT);
			parag6.setSpacingBefore(0.5f);
			document.add(parag6);
			
			PdfPCell cell02 = new PdfPCell(new Paragraph("ITEM DESCRIPTION", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell02.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell02.setBackgroundColor(Color.lightGray ); 
			
			PdfPCell cell03 = new PdfPCell(new Paragraph("HSN/SAC", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell03.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell03.setBackgroundColor(Color.lightGray ); 
			
			PdfPCell cell04 = new PdfPCell(new Paragraph("QUANTITY/UOM", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell04.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell04.setBackgroundColor(Color.lightGray ); 
			
			PdfPCell cell05 = new PdfPCell(new Paragraph("UNIT PRICE", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell05.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell05.setBackgroundColor(Color.lightGray ); 
			
			PdfPCell cell06 = new PdfPCell(new Paragraph("CGST", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));			
			cell06.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell06.setBackgroundColor(Color.lightGray );
			
			PdfPCell cell07 = new PdfPCell(new Paragraph("SGST", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));			
			cell07.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell07.setBackgroundColor(Color.lightGray );
			
			PdfPCell cell08 = new PdfPCell(new Paragraph("AMOUNT", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));			
			cell08.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell08.setBackgroundColor(Color.lightGray );
			
		     PdfPTable table = new PdfPTable(arrPDFHeader.size());
             table.setWidthPercentage(100); 
			 table.setSpacingBefore(15);
			
            table.addCell(cell02); 
            table.addCell(cell03); 
            table.addCell(cell04); 
            table.addCell(cell05); 
			table.addCell(cell06); 
			table.addCell(cell07);  
			table.addCell(cell08); 
			
			if(order_type.equals("New")){
					
					 
					cell02 = new PdfPCell(new Paragraph(""+battery_model+"\r\n\r\n Make:"+battery_brand+"\r\n("+battery_capacity+")\r\n\r\n\r\n\r\n\r\n\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell02.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell02);
					
					cell03 = new PdfPCell(new Paragraph("85071000", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell03.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell03);
					
					cell04 = new PdfPCell(new Paragraph(""+quantity+"\r\n Nos", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell04.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell04);
								
					cell05 = new PdfPCell(new Paragraph(""+UNIT_PRICE+"\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell05.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell05);
					
					cell06 = new PdfPCell(new Paragraph(""+UNIT_PRICE_CGST+"\r\n 14%", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell06.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell06);
							
					cell07 = new PdfPCell(new Paragraph(""+UNIT_PRICE_SGST+"\r\n 14%", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell07.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell07);
					
					cell08 = new PdfPCell(new Paragraph(""+UNIT_AMOUNT_TOTAL+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell08.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell08);
					
					/*************************CODE FOR TOTAL ****************************/		
					cell02 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell02.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell02);
					
					cell03 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell03.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell03);
					
					cell04 = new PdfPCell(new Paragraph("Total\r\n\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell04.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell04);
								
					cell05 = new PdfPCell(new Paragraph(""+UNIT_PRICE+"\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell05.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell05);
					
					cell06 = new PdfPCell(new Paragraph(""+UNIT_PRICE_CGST+"\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell06.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell06);
							
					cell07 = new PdfPCell(new Paragraph(""+UNIT_PRICE_SGST+"\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell07.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell07);
					
					cell08 = new PdfPCell(new Paragraph(""+UNIT_AMOUNT_TOTAL+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell08.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell08);
					
					
					
				} else {
					
					cell02 = new PdfPCell(new Paragraph(""+battery_model+"\r\n\r\n Make:"+battery_brand+"\r\n("+battery_capacity+")\r\n\r\n\r\n\r\n\r\n\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell02.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell02);
					
					cell03 = new PdfPCell(new Paragraph("85071000", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell03.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell03);
					
					cell04 = new PdfPCell(new Paragraph(""+quantity+"\r\n Nos", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell04.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell04);
								
					cell05 = new PdfPCell(new Paragraph(""+UNIT_PRICE+"\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell05.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell05);
					
					cell06 = new PdfPCell(new Paragraph(""+UNIT_PRICE_CGST+"\r\n 14%", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell06.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell06);
							
					cell07 = new PdfPCell(new Paragraph(""+UNIT_PRICE_SGST+"\r\n 14%", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell07.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell07);
					
					cell08 = new PdfPCell(new Paragraph(""+UNIT_PRICE_TOTAL+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell08.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell08);
					
					/******************************FOR SCRAP ***************************/
					
					cell02 = new PdfPCell(new Paragraph("Discount (Scrap)\r\n\r\n\r\n\r\n\r\n\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell02.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell02);
					
					cell03 = new PdfPCell(new Paragraph("8548", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell03.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell03);
					
					cell04 = new PdfPCell(new Paragraph(""+quantity+"\r\n Nos", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell04.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell04);
								
					cell05 = new PdfPCell(new Paragraph(""+UNIT_SCRAP+"\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell05.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell05);
					
					cell06 = new PdfPCell(new Paragraph(""+UNIT_SCRAP_CGST+"\r\n 9%", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell06.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell06);
							
					cell07 = new PdfPCell(new Paragraph(""+UNIT_SCRAP_SGST+"\r\n 9%", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell07.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell07);
					
					cell08 = new PdfPCell(new Paragraph(""+SCRAP_BATTERY+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell08.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell08);
					
						/*************************CODE FOR TOTAL ****************************/		
					cell02 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell02.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell02);
					
					cell03 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell03.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell03);
					
					cell04 = new PdfPCell(new Paragraph("Total\r\n\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell04.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell04);
								
					cell05 = new PdfPCell(new Paragraph(""+UNIT_TOTAL+"\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell05.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell05);
					
					cell06 = new PdfPCell(new Paragraph(""+UNIT_CGST_TOTAL+"\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell06.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell06);
							
					cell07 = new PdfPCell(new Paragraph(""+UNIT_SGST_TOTAL+"\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell07.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell07);
					
					cell08 = new PdfPCell(new Paragraph(""+UNIT_AMOUNT_TOTAL+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
					cell08.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell08);
					
					
				}
			
			document.add(table);
			
			/*********************************Table for Total*******************************/
				
				//Paragraph parag_Amount = (new Paragraph("Total:(Amount Chargeable in words) \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t TOTAL BEFORE TAX\t\t\t:"+UNIT_TOTAL+"",FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
				/*Paragraph parag_Amount = (new Paragraph("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t TOTAL BEFORE TAX\t\t\t:"+UNIT_GRAND_TOTAL+"",FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
				parag_Amount.setAlignment(Element.ALIGN_LEFT);
				parag_Amount.setSpacingBefore(10.5f);
				document.add(parag_Amount);
			 
				Paragraph parag_Amount_one = (new Paragraph("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tTOTAL TAX\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t:"+UNIT_TAX+"",FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
				parag_Amount_one.setAlignment(Element.ALIGN_LEFT);
				parag_Amount_one.setSpacingBefore(2.5f);
				document.add(parag_Amount_one);
			 
				Paragraph parag_Amount_two = (new Paragraph("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tTOTAL \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t:"+UNIT_OVERALL_TOTAL+"",FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
				parag_Amount_two.setAlignment(Element.ALIGN_LEFT);
				parag_Amount_two.setSpacingBefore(2.5f);
				document.add(parag_Amount_two);*/
			 
				/*********************************Table for Total*******************************/
				
			/***************************Terms & Condition *****************************/
			Paragraph parag_terms = (new Paragraph("Terms & Conditions:-: \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag_terms.setAlignment(Element.ALIGN_LEFT);
			parag_terms.setSpacingBefore(15.2f);
			document.add(parag_terms);
			Paragraph parag_tcond = (new Paragraph("We Declare that the Product in this invoice Are Covered by the Manufacture's\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag_tcond.setAlignment(Element.ALIGN_LEFT);
			parag_tcond.setSpacingBefore(0.2f);
			document.add(parag_tcond);
			Paragraph parag_tcond_one = (new Paragraph("STANDARD WARRANTY, We have no Legal/Financial Liability for the Same.\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag_tcond_one.setAlignment(Element.ALIGN_LEFT);
			parag_tcond_one.setSpacingBefore(0.2f);
			document.add(parag_tcond_one);
			Paragraph parag_bank_head = (new Paragraph("Company Bank Details:- \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t For, BookBattery" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag_bank_head.setAlignment(Element.ALIGN_LEFT);
			parag_bank_head.setSpacingBefore(20.2f);
			document.add(parag_bank_head);
 			Paragraph parag_account = (new Paragraph("Bank Name & Branch -  HDFC Bank Ltd, BTM Layout(Branch), \r\n Account Name : ASISTMI SOLUTIONS PRIVATE LIMITED. \r\n Account Number & IFSC Code: 08858470000098, HDFC0000885. \r\n PAN No : AAJCA7469F." ,FontFactory.getFont("Tahoma", 9, Font.NORMAL, new Color(0, 0, 0))));
			parag_account.setAlignment(Element.ALIGN_LEFT);
			parag_account.setSpacingBefore(2.2f);
			document.add(parag_account);
 			Paragraph parag_sign = (new Paragraph("Authorized Signatory" ,FontFactory.getFont("Tahoma", 9, Font.NORMAL, new Color(0, 0, 0))));
			parag_sign.setAlignment(Element.ALIGN_RIGHT);
			parag_sign.setSpacingBefore(2.2f);
			document.add(parag_sign);
 			Paragraph parag_dec = (new Paragraph("This is Computer Generated Invoice" ,FontFactory.getFont("Tahoma", 9, Font.NORMAL, new Color(0, 0, 0))));
			parag_dec.setAlignment(Element.ALIGN_CENTER);
			parag_dec.setSpacingBefore(12.2f);
			document.add(parag_dec);	
			
			document.close();
			
			//return 0;
		}
		/*catch(IOException ioe)
		{
			System.out.println("IOExcepion Occured in writeMessagesSentToPdf : " + ioe);
			ioe.printStackTrace();
			return -1;
		}
		catch(NullPointerException npe)
		{
			System.out.println("Null Pointer Excepion Occured in writeMessagesSentToPdf: " + npe);
			npe.printStackTrace();
			return -1;
		}*/
		catch(Exception e)
		{
			System.out.println("Excepion Occured in writeMessagesSentToPdf : " + e);
			e.printStackTrace();
			return -1;
		}
		return 0;		
			
	}
	/*******************PDF Creation for Batteries Ends*********************/
	/*******************PDF Creation for Inverter PDF Starts*********************/
	public int writeToInverterPdf(String ordernumber)
	{
		try
		{
				qm = QueryManager.getInstance(propsMOPConfig) ;
			ArrayList arrPDFHeader = new ArrayList();
 			ArrayList dbHeaders = new ArrayList();
			
			LogLevel.DEBUG(4,new Throwable(),"Inside writeToPdf");
			LogLevel.DEBUG(5,new Throwable(),"strTempTextFile in WriteToExcel: "+ordernumber);
			 
 			
			String verificationcode = ""; 
 			LogLevel.DEBUG(5, new Throwable(), "generateinvoice Inside:");
			LogLevel.DEBUG(5, new Throwable(), "generateinvoice ordernumber:" +ordernumber);
			
			String strSqlQry = "SELECT  order_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_verify_code,retailer_name,retailer_mobile_number,retailer_emailid,state,city,inverter_brand,inverter_model,inverter_capacity,price,area,pincode,erp_pre_tax,MRP_Price,city_percentage,quantity,payment_mode,payment_mode_type,order_status,CAST(creation_date AS CHAR) as creation_date,CAST(installed_date AS CHAR) as installed_date,CAST(updated_date AS CHAR) as updated_date,CAST(postpone_date AS CHAR) as postponed_date FROM inverter_order_details WHERE  order_number='"+ordernumber+"'";
			
			LogLevel.DEBUG(5,new Throwable(),"strSqlQry :"+strSqlQry);
			 
			Vector DataVector=qm.executeQuery(strSqlQry);
			
			Hashtable ht = qm.getRow(strSqlQry);
			
			String order_type="inverter";
			String order_number=(String)ht.get("order_number");
			LogLevel.DEBUG(5,new Throwable(),"order_number :"+order_number);
						
			String consumer_name=(String)ht.get("consumer_name");
			LogLevel.DEBUG(5,new Throwable(),"consumer_name :"+consumer_name);
			
			String consumer_mobnumber=(String)ht.get("consumer_mobnumber");
			LogLevel.DEBUG(5,new Throwable(),"consumer_mobnumber :"+consumer_mobnumber);
			
			String consumer_emailid=(String)ht.get("consumer_emailid");
			LogLevel.DEBUG(5,new Throwable(),"consumer_emailid :"+consumer_emailid);
			
			String customer_address=(String)ht.get("consumer_address");
			LogLevel.DEBUG(5,new Throwable(),"customer_address :"+customer_address);
			
			String retailer_name=(String)ht.get("retailer_name");
			LogLevel.DEBUG(5,new Throwable(),"retailer_name :"+retailer_name);
			
			String retailer_mobilnumber=(String)ht.get("retailer_mobile_number");
			LogLevel.DEBUG(5,new Throwable(),"retailer_mobilnumber :"+retailer_mobilnumber);
			
			String retailer_emailis=(String)ht.get("retailer_emailid");
			LogLevel.DEBUG(5,new Throwable(),"retailer_emailis :"+retailer_emailis);
			 
			String state=(String)ht.get("state");
			LogLevel.DEBUG(5,new Throwable(),"state :"+state);
			
			String city=(String)ht.get("city");
			LogLevel.DEBUG(5,new Throwable(),"city :"+city);
			
			String area=(String)ht.get("area");
			LogLevel.DEBUG(5,new Throwable(),"area :"+area);
			
			String pincode=(String)ht.get("pincode");
			LogLevel.DEBUG(5,new Throwable(),"pincode :"+pincode);
			  
			String inverter_brand=(String)ht.get("inverter_brand");
			LogLevel.DEBUG(5,new Throwable(),"inverter_brand :"+inverter_brand);
			
			String inverter_model=(String)ht.get("inverter_model");
			LogLevel.DEBUG(5,new Throwable(),"inverter_model :"+inverter_model);
			
			String inverter_capacity=(String)ht.get("inverter_capacity");
			LogLevel.DEBUG(5,new Throwable(),"inverter_capacity :"+inverter_capacity);
			
			String quantity=(String)ht.get("quantity");
			int QTY =  Integer.parseInt(quantity);
			LogLevel.DEBUG(5,new Throwable(),"quantity :"+quantity);
			 
			String MRP_Price=(String)ht.get("MRP_Price");
			LogLevel.DEBUG(5,new Throwable(),"MRP_Price :"+MRP_Price);
			
			String price=(String)ht.get("price");
			int strprice = Integer.parseInt(price);
			LogLevel.DEBUG(5,new Throwable(),"price :"+price);
			 
			String erp_pre_tax=(String)ht.get("erp_pre_tax");
			int strerp_pre_tax = Integer.parseInt(erp_pre_tax);
			LogLevel.DEBUG(5,new Throwable(),"erp_pre_tax :"+erp_pre_tax);
		
			String payment_billing_mode=(String)ht.get("payment_mode");
			LogLevel.DEBUG(5,new Throwable(),"payment_billing_mode :"+payment_billing_mode);
			
			String payment_mode_ordered=(String)ht.get("payment_mode_type");
			LogLevel.DEBUG(5,new Throwable(),"payment_mode_ordered :"+payment_mode_ordered);
			
			String order_status=(String)ht.get("order_status");
			LogLevel.DEBUG(5,new Throwable(),"order_status :"+order_status);
			
			String creation_date=(String)ht.get("creation_date");
			LogLevel.DEBUG(5,new Throwable(),"creation_date :"+creation_date);
			
			String installed_date=(String)ht.get("installed_date");
			LogLevel.DEBUG(5,new Throwable(),"installed_date :"+installed_date);
			
			/************** Headers *********/
			arrPDFHeader.add("ITEM DESCRIPTION");
			arrPDFHeader.add("HSN/SAC");
			arrPDFHeader.add("QUANTITY/UOM");
			arrPDFHeader.add("UNIT PRICE");
			arrPDFHeader.add("CGST");				
			arrPDFHeader.add("SGST");	
			arrPDFHeader.add("AMOUNT");
			
			/*************Data Content*********/
			dbHeaders.add("battery_model");						
 			dbHeaders.add("quantity");
			dbHeaders.add("price");
			dbHeaders.add("comission");
			dbHeaders.add("comission");	
			dbHeaders.add("comission");	
			dbHeaders.add("comission");	
			
			
			
			/**************Battery COST FOrmulas**************/
			double UNIT_PRICE = 0;
			double UNIT_PRICE_CGST = 0;
			double UNIT_PRICE_SGST = 0;
			double UNIT_PRICE_TOTAL = 0;
			
			/**************TOTAL Battery COST FOrmulas**************/
			double UNIT_TOTAL = 0.00;
			double UNIT_TAX = 0.00;
			double UNIT_CGST_TOTAL = 0.00;
			double UNIT_SGST_TOTAL = 0.00;
			double UNIT_AMOUNT_TOTAL = 0.00;
			double UNIT_GRAND_TOTAL = 0.00;
			double UNIT_QTY = 0.00;
			 
			UNIT_QTY = QTY;
			UNIT_PRICE = (strprice/1.18) * UNIT_QTY;
			
			UNIT_PRICE_CGST = (UNIT_PRICE*14/100);
			UNIT_PRICE_SGST = (UNIT_PRICE*14/100);
			UNIT_PRICE_TOTAL = UNIT_PRICE + UNIT_PRICE_CGST + UNIT_PRICE_SGST;
			
			UNIT_TOTAL = UNIT_PRICE;	
			UNIT_CGST_TOTAL = UNIT_PRICE_CGST;	
			UNIT_SGST_TOTAL = UNIT_PRICE_SGST;	
			UNIT_TAX = UNIT_CGST_TOTAL + UNIT_SGST_TOTAL;	
			UNIT_AMOUNT_TOTAL = UNIT_PRICE + UNIT_CGST_TOTAL + UNIT_SGST_TOTAL;
			UNIT_GRAND_TOTAL = UNIT_PRICE;
			
			/*********************Calculations ************************/
			
			UNIT_PRICE_CGST = Math.round (UNIT_PRICE_CGST * 100.0) / 100.0;
			UNIT_PRICE_SGST = Math.round (UNIT_PRICE_SGST * 100.0) / 100.0;
			UNIT_PRICE_TOTAL = Math.round (UNIT_PRICE_TOTAL * 100.0) / 100.0;
			UNIT_TOTAL = Math.round (UNIT_TOTAL * 100.0) / 100.0;
			UNIT_CGST_TOTAL = Math.round (UNIT_CGST_TOTAL * 100.0) / 100.0;
			UNIT_SGST_TOTAL = Math.round (UNIT_SGST_TOTAL * 100.0) / 100.0;
			UNIT_AMOUNT_TOTAL = Math.round (UNIT_AMOUNT_TOTAL * 100.0) / 100.0;
			UNIT_TAX = Math.round (UNIT_TAX * 100.0) / 100.0;
			UNIT_GRAND_TOTAL = Math.round (UNIT_GRAND_TOTAL * 100.0) / 100.0;
			
			LogLevel.DEBUG(5, new Throwable(), "UNIT_PRICE :" + UNIT_PRICE);
			LogLevel.DEBUG(5, new Throwable(), "UNIT_PRICE_TOTAL :" + UNIT_PRICE_TOTAL);
			LogLevel.DEBUG(5, new Throwable(), "UNIT_TOTAL :" + UNIT_TOTAL);
			LogLevel.DEBUG(5, new Throwable(), "UNIT_TAX :" + UNIT_TAX);
			LogLevel.DEBUG(5, new Throwable(), "UNIT_AMOUNT_TOTAL :" + UNIT_AMOUNT_TOTAL);
			LogLevel.DEBUG(5, new Throwable(), "UNIT_GRAND_TOTAL :" + UNIT_GRAND_TOTAL);
						
			Date date = new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			int currentYear = calendar.get(Calendar.YEAR);
			//Add one to month {0 - 11}
			//int months = calendar.get(Calendar.MONTH) + 1;
			String months = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

			int day = calendar.get(Calendar.DAY_OF_MONTH);
			SimpleDateFormat sdfDate=new SimpleDateFormat("dd-MM-yyyy");
			String strDate = sdfDate.format(date);
			 
			
			LogLevel.DEBUG(5,new Throwable(),"months :"+months);
			LogLevel.DEBUG(5,new Throwable(),"currentYear :"+currentYear);
			
			String Sequence_Query = "select seq_number from batterystore_invoice where seq_number!='0' and store_name='"+retailer_name+"' and order_type='"+order_type+"' order by inv_id desc limit 1";
			LogLevel.DEBUG(5,new Throwable(),"Sequence_Query :"+Sequence_Query );
			Hashtable htSeq_number = qm.getRow(Sequence_Query); 
								
				int invoice_code = 0;
				int Seq_number_Add = 0;
				if(htSeq_number.isEmpty())
				{ 
					invoice_code = 1;
					String verificationcode1 = Integer.toString(invoice_code);
					verificationcode =verificationcode1;
					LogLevel.DEBUG(5, new Throwable(), "verificationcode Empty:" + verificationcode);
					LogLevel.DEBUG(5,new Throwable(),"INSIDE EMPTY FIRST Time");
					
				} else {
					
					LogLevel.DEBUG(5,new Throwable(),"INSIDE ELSE Second Time");
				
					String Seq_number=String.valueOf(htSeq_number.get("seq_number"));
					//int Seq_number=htSeq_number.getInt("seq_number");
					Seq_number_Add = Integer.parseInt(Seq_number);
					invoice_code =  Seq_number_Add + 1;
					LogLevel.DEBUG(5,new Throwable(),"Seq_number String:"+Seq_number);
					LogLevel.DEBUG(5,new Throwable(),"Seq_number_Add Integer:"+Seq_number_Add);
				
					LogLevel.DEBUG(5,new Throwable(),"invoice_code:"+invoice_code);
				  
					String verificationcode1 = Integer.toString(invoice_code);
					verificationcode =verificationcode1;
					LogLevel.DEBUG(5, new Throwable(), "verificationcode 9:" + verificationcode);
					
					 
				}
			String excelName = "ASIST-B2C-Inverter-"+verificationcode+".pdf";
			//String strCMSServerIP = (propsMOPConfig.getProperty("CMS_SERVER_IP")!=null)?(propsMOPConfig.getProperty("CMS_SERVER_IP")).trim():"bookbattery.com";
			//String strCMSSERVERIP	= (propsMOPConfig.getProperty("CMS_SERVER_IP")!=null)?(propsMOPConfig.getProperty("CMS_SERVER_IP")):"";
			//LogLevel.DEBUG(5, new Throwable(), "strCMSServerIP:" + strCMSSERVERIP);
			LogLevel.DEBUG(5, new Throwable(), "excelName :" + excelName);
			String strPDFFilePath = "/home/ngit/tomcat/webapps/bookbattery/userdata/billing/batterystoreinvoice/" +currentYear+ "/" +months+ "/" +city+ "/" +order_type;
			LogLevel.DEBUG(5, new Throwable(), "strPDFFilePath :" + strPDFFilePath);
			File excelUploadPathCreate = new File(strPDFFilePath);
			LogLevel.DEBUG(5, new Throwable(), "excelUploadPathCreate :" + excelUploadPathCreate);
			if(excelUploadPathCreate.mkdirs())
			{
				LogLevel.DEBUG(5,new Throwable()," PDF for invoice	Name directory created :  "	+ excelUploadPathCreate);
				
			} else {
				
				
			}

				//strPDFRelFilePath	= strPDFRelFilePath +"/"+ currentYear + "/" +months;

				String excelFile = excelUploadPathCreate+"/"+excelName;
				LogLevel.DEBUG(5, new Throwable(), "excelFile :" + excelFile);
				File file=new File(excelFile);
				if(!file.exists()) 
				file.createNewFile(); 
				FileWriter fw=new FileWriter(file);
				
			//String strPdfURL="http://"+strCMSServerIP+"/bookbattery/userdata/billing/salesorders/"+currentYear+"/"+months+"/"+"battery"+"/"+"invoice"+"/"+city+"/"+excelName;
			String strPdfURL="http://stage1.bookbattery.com/bookbattery/userdata/billing/batterystoreinvoice/"+currentYear+"/"+months+ "/" +city+ "/"  +order_type+ "/" +excelName;
			LogLevel.DEBUG(5, new Throwable(), "strPdfURL : " + strPdfURL);	
			
			String order_number_iq="";
			String invoice_number_iq="";
			String invoice_url_iq="";
			String invoice_file="";
					
					
			String CheckInvoiceQuery_SQL = "Select order_number,invoice_number,invoice_url,customer_name,store_name,order_type,seq_number from batterystore_invoice where order_number='"+ordernumber+"'";
					
			Hashtable InvoiceQuery_HT = qm.getRow(CheckInvoiceQuery_SQL);
			if(InvoiceQuery_HT.isEmpty())
				{
					
					String strInoviceQry = "insert into batterystore_invoice(order_number,invoice_number,invoice_url,customer_name,store_name,order_type,seq_number)values('"+ordernumber+"','"+excelName+"','"+strPdfURL+"','"+consumer_name+"','"+retailer_name+"','"+order_type+"','"+invoice_code+"')";
					LogLevel.DEBUG(5,new Throwable(),"strInoviceQry : "+strInoviceQry);
					int reslt = qm.executeUpdate(strInoviceQry);

					if(reslt <0)
					{
						LogLevel.DEBUG(1,new Throwable(),"Failed to Generate! ");
					}
					else
					{
						LogLevel.DEBUG(1,new Throwable(),"Successfully Generated! ");	
					}
				}
				else
				{
					
					 order_number_iq=(String)InvoiceQuery_HT.get("order_number");
					 invoice_number_iq=(String)InvoiceQuery_HT.get("invoice_number");
					 invoice_url_iq=(String)InvoiceQuery_HT.get("invoice_url");
					 invoice_file = invoice_url_iq.replace("http://stage1.bookbattery.com/","/home/ngit/tomcat/webapps/");
				}
				

			/***************************Printing PDF ******************************/			
			Document document = new Document();
			PdfWriter pdf = PdfWriter.getInstance(document,new FileOutputStream(excelFile));
			document.open();
			
			PdfContentByte cb = pdf.getDirectContent();
			cb.setLineWidth(1f);
			cb.moveTo(0, 755);
			cb.lineTo(595, 755);
			cb.stroke();

			
			Image imghead = Image.getInstance("/home/ngit/tomcat/webapps/bookbattery/images/batterywalelogo.png");
			imghead.setAbsolutePosition(50,760);
			imghead.scaleAbsolute(120, 35);
			document.add(imghead);	
				
			
			//Paragraph paragraph= (new Paragraph("Original for Recipient \r\n TAX INVOICE NO : "+excelName.replace(".pdf","")+" \r\n Date : "+strDate+" \r\n P.O.# : "+order_number+" \r\n P.O.Date : "+strDate+"" ,FontFactory.getFont("Tahoma", 9, Font.NORMAL, new Color(0, 0, 0))));
			
			 Paragraph paragraph= (new Paragraph("Original for Recipient \r\n TAX INVOICE NO : "+excelName.replace(".pdf","")+" \r\n Date : "+strDate+" \r\n \r\n \r\n" ,FontFactory.getFont("Tahoma", 9, Font.NORMAL, new Color(0, 0, 0))));
			paragraph.setAlignment(Element.ALIGN_RIGHT);
			paragraph.setSpacingBefore(0.2f);
			document.add(paragraph);
			
			Paragraph parag_order= (new Paragraph("P.O.# : "+order_number+" \r\n P.O.Date : "+strDate+"" ,FontFactory.getFont("Tahoma", 9, Font.NORMAL, new Color(0, 0, 0))));
			parag_order.setAlignment(Element.ALIGN_RIGHT);
			parag_order.setSpacingBefore(2.2f);
			document.add(parag_order);
			
			Paragraph parag1 = (new Paragraph("BookBattery \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t  Bill To:" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag1.setAlignment(Element.ALIGN_LEFT);
			parag1.setSpacingBefore(1.2f);
			document.add(parag1);
			
			Paragraph parag2 = (new Paragraph("Shop No. 3, No. 166,Pattandu Aghrahara \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+consumer_name+"",FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag2.setAlignment(Element.ALIGN_LEFT);
			parag2.setSpacingBefore(0.5f);
			document.add(parag2);
				
			Paragraph parag3 = (new Paragraph("Opp to Govt Polytechnic College \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+customer_address+"",FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag3.setAlignment(Element.ALIGN_LEFT);
			parag3.setSpacingBefore(0.5f);
			document.add(parag3);
				
			Paragraph parag4 = (new Paragraph("WhiteField, Bangalore - 560066 \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t Place of Supply: "+city+"",FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag4.setAlignment(Element.ALIGN_LEFT);
			parag4.setSpacingBefore(0.5f);
			document.add(parag4);
				
			Paragraph parag5 = (new Paragraph("GSTIN No: 29AAJCA7469F2Z7 \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t",FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag5.setAlignment(Element.ALIGN_LEFT);
			parag5.setSpacingBefore(0.5f);
			document.add(parag5);
				
			Paragraph parag6 = (new Paragraph("Phone:+91 9666 300003 \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t Phone:"+consumer_mobnumber+"",FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag6.setAlignment(Element.ALIGN_LEFT);
			parag6.setSpacingBefore(0.5f);
			document.add(parag6);
			
			/*******************************************Headings************************************/
			PdfPCell cell02 = new PdfPCell(new Paragraph("ITEM DESCRIPTION", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell02.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell02.setBackgroundColor(Color.lightGray ); 
			
			PdfPCell cell03 = new PdfPCell(new Paragraph("HSN/SAC", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell03.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell03.setBackgroundColor(Color.lightGray ); 
			
			PdfPCell cell04 = new PdfPCell(new Paragraph("QUANTITY/UOM", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell04.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell04.setBackgroundColor(Color.lightGray ); 
			
			PdfPCell cell05 = new PdfPCell(new Paragraph("UNIT PRICE", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell05.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell05.setBackgroundColor(Color.lightGray ); 
			
			PdfPCell cell06 = new PdfPCell(new Paragraph("CGST", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));			
			cell06.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell06.setBackgroundColor(Color.lightGray );
			
			PdfPCell cell07 = new PdfPCell(new Paragraph("SGST", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));			
			cell07.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell07.setBackgroundColor(Color.lightGray );
			
			PdfPCell cell08 = new PdfPCell(new Paragraph("AMOUNT", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell08.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell08.setBackgroundColor(Color.lightGray ); 
			
			PdfPTable table = new PdfPTable(arrPDFHeader.size());
			table.setWidthPercentage(100); 
			table.setSpacingBefore(15);
			
			
            table.addCell(cell02); 
            table.addCell(cell03); 
            table.addCell(cell04); 
            table.addCell(cell05); 
			table.addCell(cell06); 
			table.addCell(cell07); 
			table.addCell(cell08); 
			
			
				/*******************************************Data************************************/
			cell02 = new PdfPCell(new Paragraph(""+inverter_model+"\r\n\r\n Make:"+inverter_brand+"\r\n("+inverter_capacity+")\r\n\r\n\r\n\r\n\r\n\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell02.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell02);
			
			cell03 = new PdfPCell(new Paragraph("85043100", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell03.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell03);
			
			cell04 = new PdfPCell(new Paragraph(""+quantity+"\r\n Nos", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell04.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell04);
						
			cell05 = new PdfPCell(new Paragraph(""+UNIT_PRICE+"\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell05.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell05);
			
			cell06 = new PdfPCell(new Paragraph(""+UNIT_PRICE_CGST+"\r\n 14%", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell06.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell06);
					
			cell07 = new PdfPCell(new Paragraph(""+UNIT_PRICE_SGST+"\r\n 14%", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell07.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell07);
			
			cell08 = new PdfPCell(new Paragraph(""+UNIT_AMOUNT_TOTAL+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell08.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell08);
			
			/*************************CODE FOR TOTAL ****************************/		
			cell02 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell02.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell02);
			
			cell03 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell03.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell03);
			
			cell04 = new PdfPCell(new Paragraph("Total\r\n\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell04.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell04);
						
			cell05 = new PdfPCell(new Paragraph(""+UNIT_PRICE+"\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell05.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell05);
			
			cell06 = new PdfPCell(new Paragraph(""+UNIT_PRICE_CGST+"\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell06.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell06);
					
			cell07 = new PdfPCell(new Paragraph(""+UNIT_PRICE_SGST+"\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell07.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell07);
			
			cell08 = new PdfPCell(new Paragraph(""+UNIT_AMOUNT_TOTAL+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell08.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell08);
			document.add(table);
			

			/*********************************Table for Total*******************************/
			
			//Paragraph parag_Amount = (new Paragraph("Total:(Amount Chargeable in words) \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t TOTAL BEFORE TAX\t\t\t:"+UNIT_TOTAL+"",FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			/*Paragraph parag_Amount = (new Paragraph("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t TOTAL BEFORE TAX\t\t\t:"+UNIT_GRAND_TOTAL+"",FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag_Amount.setAlignment(Element.ALIGN_LEFT);
			parag_Amount.setSpacingBefore(10.5f);
			document.add(parag_Amount);
		 
			Paragraph parag_Amount_one = (new Paragraph("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tTOTAL TAX\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t:"+UNIT_TAX+"",FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag_Amount_one.setAlignment(Element.ALIGN_LEFT);
			parag_Amount_one.setSpacingBefore(2.5f);
			document.add(parag_Amount_one);
		 
			Paragraph parag_Amount_two = (new Paragraph("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tTOTAL \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t:"+UNIT_AMOUNT_TOTAL+"",FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag_Amount_two.setAlignment(Element.ALIGN_LEFT);
			parag_Amount_two.setSpacingBefore(2.5f);
			document.add(parag_Amount_two);*/
		 
			/*********************************Table for Total*******************************/

			
			
			/***************************Terms & Condition *****************************/
			Paragraph parag_terms = (new Paragraph("Terms & Conditions:-: \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag_terms.setAlignment(Element.ALIGN_LEFT);
			parag_terms.setSpacingBefore(15.2f);
			document.add(parag_terms);
			Paragraph parag_tcond = (new Paragraph("We Declare that the Product in this invoice Are Covered by the Manufacture's\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag_tcond.setAlignment(Element.ALIGN_LEFT);
			parag_tcond.setSpacingBefore(0.2f);
			document.add(parag_tcond);
			Paragraph parag_tcond_one = (new Paragraph("STANDARD WARRANTY, We have no Legal/Financial Liability for the Same.\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag_tcond_one.setAlignment(Element.ALIGN_LEFT);
			parag_tcond_one.setSpacingBefore(0.2f);
			document.add(parag_tcond_one);
			Paragraph parag_bank_head = (new Paragraph("Company Bank Details:- \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t For, BookBattery" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag_bank_head.setAlignment(Element.ALIGN_LEFT);
			parag_bank_head.setSpacingBefore(20.2f);
			document.add(parag_bank_head);
 			Paragraph parag_account = (new Paragraph("Bank Name & Branch -  HDFC Bank Ltd, BTM Layout(Branch), \r\n Account Name : ASISTMI SOLUTIONS PRIVATE LIMITED. \r\n Account Number & IFSC Code: 08858470000098, HDFC0000885. \r\n PAN No : AAJCA7469F." ,FontFactory.getFont("Tahoma", 9, Font.NORMAL, new Color(0, 0, 0))));
			parag_account.setAlignment(Element.ALIGN_LEFT);
			parag_account.setSpacingBefore(2.2f);
			document.add(parag_account);
 			Paragraph parag_sign = (new Paragraph("Authorized Signatory" ,FontFactory.getFont("Tahoma", 9, Font.NORMAL, new Color(0, 0, 0))));
			parag_sign.setAlignment(Element.ALIGN_RIGHT);
			parag_sign.setSpacingBefore(2.2f);
			document.add(parag_sign);
 			Paragraph parag_dec = (new Paragraph("This is Computer Generated Invoice" ,FontFactory.getFont("Tahoma", 9, Font.NORMAL, new Color(0, 0, 0))));
			parag_dec.setAlignment(Element.ALIGN_CENTER);
			parag_dec.setSpacingBefore(12.2f);
			document.add(parag_dec);	
			
			document.close();
			
			//return 0;
		}
		/*catch(IOException ioe)
		{
			System.out.println("IOExcepion Occured in writeMessagesSentToPdf : " + ioe);
			ioe.printStackTrace();
			return -1;
		}
		catch(NullPointerException npe)
		{
			System.out.println("Null Pointer Excepion Occured in writeMessagesSentToPdf: " + npe);
			npe.printStackTrace();
			return -1;
		}*/
		catch(Exception e)
		{
			System.out.println("Excepion Occured in writeMessagesSentToPdf : " + e);
			e.printStackTrace();
			return -1;
		}
		return 0;		
				
			
	}
	/*******************PDF Creation for Inverter PDF Ends*********************/
	
	/*******************PDF Creation for Inverter PDF Starts*********************/
	public int writeToServicePdf(String ordernumber)
	{
		try
		{
				qm = QueryManager.getInstance(propsMOPConfig) ;
			ArrayList arrPDFHeader = new ArrayList();
 			ArrayList dbHeaders = new ArrayList();
			
			LogLevel.DEBUG(4,new Throwable(),"Inside writeToPdf");
			LogLevel.DEBUG(5,new Throwable(),"strTempTextFile in WriteToExcel: "+ordernumber);
			 
 			
			String verificationcode = ""; 
 			LogLevel.DEBUG(5, new Throwable(), "generateinvoice Inside:");
			LogLevel.DEBUG(5, new Throwable(), "generateinvoice ordernumber:" +ordernumber);
			
			String strSqlQry = "SELECT  order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_verif_code,consumer_address1,retailer_name,retailer_mobilnumber,retailer_emailid,city,services_type,services_place,services_package,service_price_mrp,service_price_discount,order_status,product_type,product_capacity,quantity,payment_mode,payment_mode_type,CAST(creation_date AS CHAR) as creation_date,CAST(installed_date AS CHAR) as installed_date,CAST(updated_date AS CHAR) as updated_date,CAST(postpone_date AS CHAR) as postponed_date FROM service_order_details WHERE  order_number='"+ordernumber+"'";
			
			LogLevel.DEBUG(5,new Throwable(),"strSqlQry :"+strSqlQry);
			 
			Vector DataVector=qm.executeQuery(strSqlQry);
			
			Hashtable ht = qm.getRow(strSqlQry);
			
			String order_type="service";
			String order_number=(String)ht.get("order_number");
			LogLevel.DEBUG(5,new Throwable(),"order_number :"+order_number);
						
			String consumer_name=(String)ht.get("consumer_name");
			LogLevel.DEBUG(5,new Throwable(),"consumer_name :"+consumer_name);
			
			String consumer_mobnumber=(String)ht.get("consumer_mobnumber");
			LogLevel.DEBUG(5,new Throwable(),"consumer_mobnumber :"+consumer_mobnumber);
			
			String consumer_emailid=(String)ht.get("consumer_emailid");
			LogLevel.DEBUG(5,new Throwable(),"consumer_emailid :"+consumer_emailid);
			
			String customer_address=(String)ht.get("consumer_address1");
			LogLevel.DEBUG(5,new Throwable(),"customer_address :"+customer_address);
			
			String retailer_name=(String)ht.get("retailer_name");
			LogLevel.DEBUG(5,new Throwable(),"retailer_name :"+retailer_name);
			
			String retailer_mobilnumber=(String)ht.get("retailer_mobilnumber");
			LogLevel.DEBUG(5,new Throwable(),"retailer_mobilnumber :"+retailer_mobilnumber);
			
			String retailer_emailis=(String)ht.get("retailer_emailid");
			LogLevel.DEBUG(5,new Throwable(),"retailer_emailis :"+retailer_emailis);
			 
			String state=(String)ht.get("state");
			LogLevel.DEBUG(5,new Throwable(),"state :"+state);
					
			String city=(String)ht.get("city");
			LogLevel.DEBUG(5,new Throwable(),"city :"+city);
			
			String area=(String)ht.get("area");
			LogLevel.DEBUG(5,new Throwable(),"area :"+area);
			
			String pincode=(String)ht.get("pincode");
			LogLevel.DEBUG(5,new Throwable(),"pincode :"+pincode);
			 
			String bat_type=(String)ht.get("product_type");
			LogLevel.DEBUG(5,new Throwable(),"bat_type :"+bat_type); 
			
			String veh_name=(String)ht.get("veh_name");
			LogLevel.DEBUG(5,new Throwable(),"veh_name :"+veh_name);
			
			String veh_model=(String)ht.get("veh_model");
			LogLevel.DEBUG(5,new Throwable(),"veh_model :"+veh_model);
			
			String battery_capacity=(String)ht.get("product_capacity");
			LogLevel.DEBUG(5,new Throwable(),"battery_capacity :"+battery_capacity);
			
			String quantity1=(String)ht.get("quantity");
			String quantity="";
			
			if(quantity1.equals("")){
				
				quantity="1";
				
			} else {
				
				quantity=quantity1;
				
			}
			int quantity2 = Integer.parseInt(quantity);
			LogLevel.DEBUG(5,new Throwable(),"quantity2 :"+quantity2);
				
			String price=(String)ht.get("service_price_mrp");
			int strprice = Integer.parseInt(price);
			LogLevel.DEBUG(5,new Throwable(),"price :"+price);
			 
			
		 	String payment_mode_installed=(String)ht.get("payment_mode");
			LogLevel.DEBUG(5,new Throwable(),"payment_mode_installed :"+payment_mode_installed);
			
			String payment_mode_ordered=(String)ht.get("payment_mode_type");
			LogLevel.DEBUG(5,new Throwable(),"payment_mode_ordered :"+payment_mode_ordered);
			
			String order_status=(String)ht.get("order_status");
			LogLevel.DEBUG(5,new Throwable(),"order_status :"+order_status);
			
			String creation_date=(String)ht.get("creation_date");
			LogLevel.DEBUG(5,new Throwable(),"creation_date :"+creation_date);
			
			String installed_date=(String)ht.get("installed_date");
			LogLevel.DEBUG(5,new Throwable(),"installed_date :"+installed_date);
			
			/************** Headers *********/
			arrPDFHeader.add("ITEM DESCRIPTION");
 			arrPDFHeader.add("QUANTITY/UOM");
			arrPDFHeader.add("UNIT PRICE");
			arrPDFHeader.add("CGST");				
			arrPDFHeader.add("SGST");	
			arrPDFHeader.add("AMOUNT");
			
			/*************Data Content*********/
			dbHeaders.add("battery_model");						
 			dbHeaders.add("quantity");
			dbHeaders.add("price");
 			dbHeaders.add("comission");	
			dbHeaders.add("comission");	
			dbHeaders.add("comission");	
			
			
			
			/**************Battery COST FOrmulas**************/
			double UNIT_PRICE = 0;
			double UNIT_PRICE_CGST = 0;
			double UNIT_PRICE_SGST = 0;
			double UNIT_PRICE_TOTAL = 0;
			
			/**************TOTAL Battery COST FOrmulas**************/
			double UNIT_TOTAL = 0.00;
			double UNIT_TAX = 0.00;
			double UNIT_CGST_TOTAL = 0.00;
			double UNIT_SGST_TOTAL = 0.00;
			double UNIT_AMOUNT_TOTAL = 0.00;
			double UNIT_GRAND_TOTAL = 0.00;
			
			double UNIT_QTY = 0.00; 
			UNIT_QTY = quantity2;
			UNIT_PRICE = (strprice/1.18) * UNIT_QTY;
			
			UNIT_PRICE_CGST = (UNIT_PRICE*9/100);
			UNIT_PRICE_SGST = (UNIT_PRICE*9/100);
			UNIT_PRICE_TOTAL = UNIT_PRICE + UNIT_PRICE_CGST + UNIT_PRICE_SGST;
			
			UNIT_TOTAL = UNIT_PRICE;	
			UNIT_CGST_TOTAL = UNIT_PRICE_CGST;	
			UNIT_SGST_TOTAL = UNIT_PRICE_SGST;	
			UNIT_TAX = UNIT_CGST_TOTAL + UNIT_SGST_TOTAL;	
			UNIT_AMOUNT_TOTAL = UNIT_PRICE + UNIT_CGST_TOTAL + UNIT_SGST_TOTAL;
			UNIT_GRAND_TOTAL = UNIT_PRICE;
			
			/*********************Calculations ************************/
			
			UNIT_PRICE = Math.round (UNIT_PRICE * 100.0) / 100.0;
			UNIT_PRICE_CGST = Math.round (UNIT_PRICE_CGST * 100.0) / 100.0;
			UNIT_PRICE_SGST = Math.round (UNIT_PRICE_SGST * 100.0) / 100.0;
			UNIT_PRICE_TOTAL = Math.round (UNIT_PRICE_TOTAL * 100.0) / 100.0;
			UNIT_TOTAL = Math.round (UNIT_TOTAL * 100.0) / 100.0;
			UNIT_CGST_TOTAL = Math.round (UNIT_CGST_TOTAL * 100.0) / 100.0;
			UNIT_SGST_TOTAL = Math.round (UNIT_SGST_TOTAL * 100.0) / 100.0;
			UNIT_AMOUNT_TOTAL = Math.round (UNIT_AMOUNT_TOTAL * 100.0) / 100.0;
			UNIT_TAX = Math.round (UNIT_TAX * 100.0) / 100.0;
			UNIT_GRAND_TOTAL = Math.round (UNIT_GRAND_TOTAL * 100.0) / 100.0;
			
			LogLevel.DEBUG(5, new Throwable(), "UNIT_PRICE :" + UNIT_PRICE);
			LogLevel.DEBUG(5, new Throwable(), "UNIT_PRICE_TOTAL :" + UNIT_PRICE_TOTAL);
			LogLevel.DEBUG(5, new Throwable(), "UNIT_TOTAL :" + UNIT_TOTAL);
			LogLevel.DEBUG(5, new Throwable(), "UNIT_TAX :" + UNIT_TAX);
			LogLevel.DEBUG(5, new Throwable(), "UNIT_AMOUNT_TOTAL :" + UNIT_AMOUNT_TOTAL);
			LogLevel.DEBUG(5, new Throwable(), "UNIT_GRAND_TOTAL :" + UNIT_GRAND_TOTAL);
						
			Date date = new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			int currentYear = calendar.get(Calendar.YEAR);
			//Add one to month {0 - 11}
			//int months = calendar.get(Calendar.MONTH) + 1;
			String months = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

			int day = calendar.get(Calendar.DAY_OF_MONTH);
			SimpleDateFormat sdfDate=new SimpleDateFormat("dd-MM-yyyy");
			String strDate = sdfDate.format(date);
			 
			
			LogLevel.DEBUG(5,new Throwable(),"months :"+months);
			LogLevel.DEBUG(5,new Throwable(),"currentYear :"+currentYear);
			
			String Sequence_Query = "select seq_number from batterystore_invoice where seq_number!='0' and store_name='"+retailer_name+"' and order_type='"+order_type+"' order by inv_id desc limit 1";
			LogLevel.DEBUG(5,new Throwable(),"Sequence_Query :"+Sequence_Query );
			Hashtable htSeq_number = qm.getRow(Sequence_Query); 
								
				int invoice_code = 0;
				int Seq_number_Add = 0;
				if(htSeq_number.isEmpty())
				{ 
					invoice_code = 1;
					String verificationcode1 = Integer.toString(invoice_code);
					verificationcode =verificationcode1;
					LogLevel.DEBUG(5, new Throwable(), "verificationcode Empty:" + verificationcode);
					LogLevel.DEBUG(5,new Throwable(),"INSIDE EMPTY FIRST Time");
					
				} else {
					
					LogLevel.DEBUG(5,new Throwable(),"INSIDE ELSE Second Time");
				
					String Seq_number=String.valueOf(htSeq_number.get("seq_number"));
					//int Seq_number=htSeq_number.getInt("seq_number");
					Seq_number_Add = Integer.parseInt(Seq_number);
					invoice_code =  Seq_number_Add + 1;
					LogLevel.DEBUG(5,new Throwable(),"Seq_number String:"+Seq_number);
					LogLevel.DEBUG(5,new Throwable(),"Seq_number_Add Integer:"+Seq_number_Add);
				
					LogLevel.DEBUG(5,new Throwable(),"invoice_code:"+invoice_code);
				  
					String verificationcode1 = Integer.toString(invoice_code);
					verificationcode =verificationcode1;
					LogLevel.DEBUG(5, new Throwable(), "verificationcode 9:" + verificationcode);
					
					 
				}
			String excelName = "ASIST-B2C-Service-"+verificationcode+".pdf";
			//String strCMSServerIP = (propsMOPConfig.getProperty("CMS_SERVER_IP")!=null)?(propsMOPConfig.getProperty("CMS_SERVER_IP")).trim():"bookbattery.com";
			//String strCMSSERVERIP	= (propsMOPConfig.getProperty("CMS_SERVER_IP")!=null)?(propsMOPConfig.getProperty("CMS_SERVER_IP")):"";
			//LogLevel.DEBUG(5, new Throwable(), "strCMSServerIP:" + strCMSSERVERIP);
			LogLevel.DEBUG(5, new Throwable(), "excelName :" + excelName);
			String strPDFFilePath = "/home/ngit/tomcat/webapps/bookbattery/userdata/billing/batterystoreinvoice/" +currentYear+ "/" +months+ "/" +city+ "/" +order_type;
			LogLevel.DEBUG(5, new Throwable(), "strPDFFilePath :" + strPDFFilePath);
			File excelUploadPathCreate = new File(strPDFFilePath);
			LogLevel.DEBUG(5, new Throwable(), "excelUploadPathCreate :" + excelUploadPathCreate);
			if(excelUploadPathCreate.mkdirs())
			{
				LogLevel.DEBUG(5,new Throwable()," PDF for invoice	Name directory created :  "	+ excelUploadPathCreate);
				
			} else {
				
				
			}

				//strPDFRelFilePath	= strPDFRelFilePath +"/"+ currentYear + "/" +months;

				String excelFile = excelUploadPathCreate+"/"+excelName;
				LogLevel.DEBUG(5, new Throwable(), "excelFile :" + excelFile);
				File file=new File(excelFile);
				if(!file.exists()) 
				file.createNewFile(); 
				FileWriter fw=new FileWriter(file);
				
			//String strPdfURL="http://"+strCMSServerIP+"/bookbattery/userdata/billing/salesorders/"+currentYear+"/"+months+"/"+"battery"+"/"+"invoice"+"/"+city+"/"+excelName;
			String strPdfURL="http://stage1.bookbattery.com/bookbattery/userdata/billing/batterystoreinvoice/"+currentYear+"/"+months+ "/" +city+ "/"  +order_type+ "/" +excelName;
			LogLevel.DEBUG(5, new Throwable(), "strPdfURL : " + strPdfURL);	
			
			String order_number_iq="";
			String invoice_number_iq="";
			String invoice_url_iq="";
			String invoice_file="";
					
					
			String CheckInvoiceQuery_SQL = "Select order_number,invoice_number,invoice_url,customer_name,store_name,order_type,seq_number from batterystore_invoice where order_number='"+ordernumber+"'";
					
			Hashtable InvoiceQuery_HT = qm.getRow(CheckInvoiceQuery_SQL);
			if(InvoiceQuery_HT.isEmpty())
				{
					
					String strInoviceQry = "insert into batterystore_invoice(order_number,invoice_number,invoice_url,customer_name,store_name,order_type,seq_number)values('"+ordernumber+"','"+excelName+"','"+strPdfURL+"','"+consumer_name+"','"+retailer_name+"','"+order_type+"','"+invoice_code+"')";
					LogLevel.DEBUG(5,new Throwable(),"strInoviceQry : "+strInoviceQry);
					int reslt = qm.executeUpdate(strInoviceQry);

					if(reslt <0)
					{
						LogLevel.DEBUG(1,new Throwable(),"Failed to Generate! ");
					}
					else
					{
						LogLevel.DEBUG(1,new Throwable(),"Successfully Generated! ");	
					}
				}
				else
				{
					
					 order_number_iq=(String)InvoiceQuery_HT.get("order_number");
					 invoice_number_iq=(String)InvoiceQuery_HT.get("invoice_number");
					 invoice_url_iq=(String)InvoiceQuery_HT.get("invoice_url");
					 invoice_file = invoice_url_iq.replace("http://stage1.bookbattery.com/","/home/ngit/tomcat/webapps/");
				}
				

			/***************************Printing PDF ******************************/			
			Document document = new Document();
			PdfWriter pdf = PdfWriter.getInstance(document,new FileOutputStream(excelFile));
			document.open();
			
			PdfContentByte cb = pdf.getDirectContent();
			cb.setLineWidth(1f);
			cb.moveTo(0, 755);
			cb.lineTo(595, 755);
			cb.stroke();

			
			Image imghead = Image.getInstance("/home/ngit/tomcat/webapps/bookbattery/images/batterywalelogo.png");
			imghead.setAbsolutePosition(50,760);
			imghead.scaleAbsolute(120, 35);
			document.add(imghead);	
				
			
			//Paragraph paragraph= (new Paragraph("Original for Recipient \r\n TAX INVOICE NO : "+excelName.replace(".pdf","")+" \r\n Date : "+strDate+" \r\n P.O.# : "+order_number+" \r\n P.O.Date : "+strDate+"" ,FontFactory.getFont("Tahoma", 9, Font.NORMAL, new Color(0, 0, 0))));
			
			 Paragraph paragraph= (new Paragraph("Original for Recipient \r\n TAX INVOICE NO : "+excelName.replace(".pdf","")+" \r\n Date : "+strDate+" \r\n \r\n \r\n" ,FontFactory.getFont("Tahoma", 9, Font.NORMAL, new Color(0, 0, 0))));
			paragraph.setAlignment(Element.ALIGN_RIGHT);
			paragraph.setSpacingBefore(0.2f);
			document.add(paragraph);
			
			Paragraph parag_order= (new Paragraph("P.O.# : "+order_number+" \r\n P.O.Date : "+strDate+"" ,FontFactory.getFont("Tahoma", 9, Font.NORMAL, new Color(0, 0, 0))));
			parag_order.setAlignment(Element.ALIGN_RIGHT);
			parag_order.setSpacingBefore(2.2f);
			document.add(parag_order);
			
			Paragraph parag1 = (new Paragraph("BookBattery \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t  Bill To:" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag1.setAlignment(Element.ALIGN_LEFT);
			parag1.setSpacingBefore(1.2f);
			document.add(parag1);
			
			Paragraph parag2 = (new Paragraph("Shop No. 3, No. 166,Pattandu Aghrahara \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+consumer_name+"",FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag2.setAlignment(Element.ALIGN_LEFT);
			parag2.setSpacingBefore(0.5f);
			document.add(parag2);
				
			Paragraph parag3 = (new Paragraph("Opp to Govt Polytechnic College \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+customer_address+"",FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag3.setAlignment(Element.ALIGN_LEFT);
			parag3.setSpacingBefore(0.5f);
			document.add(parag3);
				
			Paragraph parag4 = (new Paragraph("WhiteField, Bangalore - 560066 \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t Place of Supply: "+city+"",FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag4.setAlignment(Element.ALIGN_LEFT);
			parag4.setSpacingBefore(0.5f);
			document.add(parag4);
				
			Paragraph parag5 = (new Paragraph("GSTIN No: 29AAJCA7469F2Z7 \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t",FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag5.setAlignment(Element.ALIGN_LEFT);
			parag5.setSpacingBefore(0.5f);
			document.add(parag5);
				
			Paragraph parag6 = (new Paragraph("Phone:+91 9666 300003 \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t Phone:"+consumer_mobnumber+"",FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag6.setAlignment(Element.ALIGN_LEFT);
			parag6.setSpacingBefore(0.5f);
			document.add(parag6);
			
			/*********************************************Invoice Content ************************************/
			
			PdfPCell cell02 = new PdfPCell(new Paragraph("ITEM DESCRIPTION", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell02.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell02.setBackgroundColor(Color.lightGray ); 
					
			PdfPCell cell04 = new PdfPCell(new Paragraph("QUANTITY/UOM", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell04.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell04.setBackgroundColor(Color.lightGray ); 
			
			PdfPCell cell05 = new PdfPCell(new Paragraph("UNIT PRICE", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell05.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell05.setBackgroundColor(Color.lightGray ); 
			
			PdfPCell cell06 = new PdfPCell(new Paragraph("CGST", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));			
			cell06.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell06.setBackgroundColor(Color.lightGray );
			
			PdfPCell cell07 = new PdfPCell(new Paragraph("SGST", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));			
			cell07.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell07.setBackgroundColor(Color.lightGray );
			
			PdfPCell cell09 = new PdfPCell(new Paragraph("AMOUNT", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));			
			cell09.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell09.setBackgroundColor(Color.lightGray );
			
		     PdfPTable table = new PdfPTable(arrPDFHeader.size());
             table.setWidthPercentage(100); 
			 table.setSpacingBefore(15);
			
            table.addCell(cell02); 
            table.addCell(cell04); 
            table.addCell(cell05); 
			table.addCell(cell06); 
			table.addCell(cell07); 
 			table.addCell(cell09); 
			
			cell02 = new PdfPCell(new Paragraph(""+bat_type+"\r\n\r\n"+battery_capacity+"\r\n\r\n\r\n\r\n\r\n\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell02.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell02);
			 
			cell04 = new PdfPCell(new Paragraph(""+quantity+"\r\n Nos", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell04.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell04);
						
			cell05 = new PdfPCell(new Paragraph(""+UNIT_PRICE+"\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell05.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell05);
			
			cell06 = new PdfPCell(new Paragraph(""+UNIT_PRICE_CGST+"\r\n 9%", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell06.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell06);
					
			cell07 = new PdfPCell(new Paragraph(""+UNIT_PRICE_SGST+"\r\n 9%", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell07.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell07);
			
			cell09 = new PdfPCell(new Paragraph(""+UNIT_AMOUNT_TOTAL+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell09.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell09);
			
			/*************************CODE FOR TOTAL ****************************/		
			cell02 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell02.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell02);
			 
			cell04 = new PdfPCell(new Paragraph("Total\r\n\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell04.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell04);
						
			cell05 = new PdfPCell(new Paragraph(""+UNIT_PRICE+"\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell05.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell05);
			
			cell06 = new PdfPCell(new Paragraph(""+UNIT_PRICE_CGST+"\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell06.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell06);
					
			cell07 = new PdfPCell(new Paragraph(""+UNIT_PRICE_SGST+"\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell07.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell07);
			
			cell09 = new PdfPCell(new Paragraph(""+UNIT_AMOUNT_TOTAL+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell09.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell09);
			document.add(table);
			/*********************************************Invoice Content ************************************/
			
			/***************************Terms & Condition *****************************/
			Paragraph parag_terms = (new Paragraph("Terms & Conditions:-: \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag_terms.setAlignment(Element.ALIGN_LEFT);
			parag_terms.setSpacingBefore(15.2f);
			document.add(parag_terms);
			Paragraph parag_tcond = (new Paragraph("We Declare that the Product in this invoice Are Covered by the Manufacture's\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag_tcond.setAlignment(Element.ALIGN_LEFT);
			parag_tcond.setSpacingBefore(0.2f);
			document.add(parag_tcond);
			Paragraph parag_tcond_one = (new Paragraph("STANDARD WARRANTY, We have no Legal/Financial Liability for the Same.\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag_tcond_one.setAlignment(Element.ALIGN_LEFT);
			parag_tcond_one.setSpacingBefore(0.2f);
			document.add(parag_tcond_one);
			Paragraph parag_bank_head = (new Paragraph("Company Bank Details:- \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t For, BookBattery" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag_bank_head.setAlignment(Element.ALIGN_LEFT);
			parag_bank_head.setSpacingBefore(20.2f);
			document.add(parag_bank_head);
 			Paragraph parag_account = (new Paragraph("Bank Name & Branch -  HDFC Bank Ltd, BTM Layout(Branch), \r\n Account Name : ASISTMI SOLUTIONS PRIVATE LIMITED. \r\n Account Number & IFSC Code: 08858470000098, HDFC0000885. \r\n PAN No : AAJCA7469F." ,FontFactory.getFont("Tahoma", 9, Font.NORMAL, new Color(0, 0, 0))));
			parag_account.setAlignment(Element.ALIGN_LEFT);
			parag_account.setSpacingBefore(2.2f);
			document.add(parag_account);
 			Paragraph parag_sign = (new Paragraph("Authorized Signatory" ,FontFactory.getFont("Tahoma", 9, Font.NORMAL, new Color(0, 0, 0))));
			parag_sign.setAlignment(Element.ALIGN_RIGHT);
			parag_sign.setSpacingBefore(2.2f);
			document.add(parag_sign);
 			Paragraph parag_dec = (new Paragraph("This is Computer Generated Invoice" ,FontFactory.getFont("Tahoma", 9, Font.NORMAL, new Color(0, 0, 0))));
			parag_dec.setAlignment(Element.ALIGN_CENTER);
			parag_dec.setSpacingBefore(12.2f);
			document.add(parag_dec);	
			
			document.close();
			
			//return 0;
		}
		/*catch(IOException ioe)
		{
			System.out.println("IOExcepion Occured in writeMessagesSentToPdf : " + ioe);
			ioe.printStackTrace();
			return -1;
		}
		catch(NullPointerException npe)
		{
			System.out.println("Null Pointer Excepion Occured in writeMessagesSentToPdf: " + npe);
			npe.printStackTrace();
			return -1;
		}*/
		catch(Exception e)
		{
			System.out.println("Excepion Occured in writeMessagesSentToPdf : " + e);
			e.printStackTrace();
			return -1;
		}
		return 0;		
				
			
	}
	/*******************PDF Creation for Inverter PDF Ends*********************/
	
	/*******************PDF Creation for Inverter PDF Starts*********************/
	public int writeToTrolleyPdf(String ordernumber)
	{
		try
		{
				qm = QueryManager.getInstance(propsMOPConfig) ;
			ArrayList arrPDFHeader = new ArrayList();
 			ArrayList dbHeaders = new ArrayList();
			
			LogLevel.DEBUG(4,new Throwable(),"Inside writeToPdf");
			LogLevel.DEBUG(5,new Throwable(),"strTempTextFile in WriteToExcel: "+ordernumber);
			 
 			
			String verificationcode = ""; 
 			LogLevel.DEBUG(5, new Throwable(), "generateinvoice Inside:");
			LogLevel.DEBUG(5, new Throwable(), "generateinvoice ordernumber:" +ordernumber);
			
			String strSqlQry = "SELECT  ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailid,state,city,area,pincode,trolley_brand,trolley_model,price,order_status,quantity,operator,agent_name,payment_mode,payment_mode_type,city_percentage,consumer_address,CAST(creation_date AS CHAR) as creation_date,CAST(installed_date AS CHAR) as installed_date,CAST(updated_date AS CHAR) as updated_date,CAST(postpone_date AS CHAR) as postponed_date FROM trolley_order_details WHERE  order_number='"+ordernumber+"'";
			
			LogLevel.DEBUG(5,new Throwable(),"strSqlQry :"+strSqlQry);
			 
			Vector DataVector=qm.executeQuery(strSqlQry);
			
			Hashtable ht = qm.getRow(strSqlQry);
			
 			String order_number=(String)ht.get("order_number");
			LogLevel.DEBUG(5,new Throwable(),"order_number :"+order_number);
						
			String consumer_name=(String)ht.get("consumer_name");
			LogLevel.DEBUG(5,new Throwable(),"consumer_name :"+consumer_name);
			
			String consumer_mobnumber=(String)ht.get("consumer_mobnumber");
			LogLevel.DEBUG(5,new Throwable(),"consumer_mobnumber :"+consumer_mobnumber);
			
			String consumer_emailid=(String)ht.get("consumer_emailid");
			LogLevel.DEBUG(5,new Throwable(),"consumer_emailid :"+consumer_emailid);
			
			String customer_address=(String)ht.get("consumer_address");
			LogLevel.DEBUG(5,new Throwable(),"customer_address :"+customer_address);
			
			String retailer_name=(String)ht.get("retailer_name");
			LogLevel.DEBUG(5,new Throwable(),"retailer_name :"+retailer_name);
			
			String retailer_mobilnumber=(String)ht.get("retailer_mobilnumber");
			LogLevel.DEBUG(5,new Throwable(),"retailer_mobilnumber :"+retailer_mobilnumber);
			
			String retailer_emailis=(String)ht.get("retailer_emailid");
			LogLevel.DEBUG(5,new Throwable(),"retailer_emailis :"+retailer_emailis);
			 
			String state=(String)ht.get("state");
			LogLevel.DEBUG(5,new Throwable(),"state :"+state);
					
			String city=(String)ht.get("city");
			LogLevel.DEBUG(5,new Throwable(),"city :"+city);
			
			String area=(String)ht.get("area");
			LogLevel.DEBUG(5,new Throwable(),"area :"+area);
			
			String pincode=(String)ht.get("pincode");
			LogLevel.DEBUG(5,new Throwable(),"pincode :"+pincode);
			 
			String trolley_brand=(String)ht.get("trolley_brand");
			LogLevel.DEBUG(5,new Throwable(),"trolley_brand :"+trolley_brand); 
			
			String trolley_model=(String)ht.get("trolley_model");
			LogLevel.DEBUG(5,new Throwable(),"trolley_model :"+trolley_model);
			
			 
			String quantity1=(String)ht.get("quantity");
			String quantity="";
			
			if(quantity1.equals("")){
				
				quantity="1";
				
			} else {
				
				quantity=quantity1;
				
			}
			int quantity2 = Integer.parseInt(quantity);
			LogLevel.DEBUG(5,new Throwable(),"quantity2 :"+quantity2);
				
			String price=(String)ht.get("price");
			int strprice = Integer.parseInt(price);
			LogLevel.DEBUG(5,new Throwable(),"price :"+price);
			 
			
		 	String payment_mode_installed=(String)ht.get("payment_mode");
			LogLevel.DEBUG(5,new Throwable(),"payment_mode_installed :"+payment_mode_installed);
			
			String payment_mode_ordered=(String)ht.get("payment_mode_type");
			LogLevel.DEBUG(5,new Throwable(),"payment_mode_ordered :"+payment_mode_ordered);
			
			String order_status=(String)ht.get("order_status");
			LogLevel.DEBUG(5,new Throwable(),"order_status :"+order_status);
			
			String creation_date=(String)ht.get("creation_date");
			LogLevel.DEBUG(5,new Throwable(),"creation_date :"+creation_date);
			
			String installed_date=(String)ht.get("installed_date");
			LogLevel.DEBUG(5,new Throwable(),"installed_date :"+installed_date);
			
			String order_type="Trolley";
			
			/************** Headers *********/
			arrPDFHeader.add("ITEM DESCRIPTION");
 			arrPDFHeader.add("QUANTITY/UOM");
			arrPDFHeader.add("UNIT PRICE");
			arrPDFHeader.add("CGST");				
			arrPDFHeader.add("SGST");	
			arrPDFHeader.add("AMOUNT");
			
			/*************Data Content*********/
			dbHeaders.add("trolley_model");						
 			dbHeaders.add("quantity");
			dbHeaders.add("price");
 			dbHeaders.add("comission");	
			dbHeaders.add("comission");	
			dbHeaders.add("comission");	
			
			
			
			/**************Battery COST FOrmulas**************/
			double UNIT_PRICE = 0;
			double UNIT_PRICE_CGST = 0;
			double UNIT_PRICE_SGST = 0;
			double UNIT_PRICE_TOTAL = 0;
			
			/**************TOTAL Battery COST FOrmulas**************/
			double UNIT_TOTAL = 0.00;
			double UNIT_TAX = 0.00;
			double UNIT_CGST_TOTAL = 0.00;
			double UNIT_SGST_TOTAL = 0.00;
			double UNIT_AMOUNT_TOTAL = 0.00;
			double UNIT_GRAND_TOTAL = 0.00;
			
			double UNIT_QTY = 0.00; 
			UNIT_QTY = quantity2;
			UNIT_PRICE = (strprice/1.18) * UNIT_QTY;
			
			UNIT_PRICE_CGST = (UNIT_PRICE*9/100);
			UNIT_PRICE_SGST = (UNIT_PRICE*9/100);
			UNIT_PRICE_TOTAL = UNIT_PRICE + UNIT_PRICE_CGST + UNIT_PRICE_SGST;
			
			UNIT_TOTAL = UNIT_PRICE;	
			UNIT_CGST_TOTAL = UNIT_PRICE_CGST;	
			UNIT_SGST_TOTAL = UNIT_PRICE_SGST;	
			UNIT_TAX = UNIT_CGST_TOTAL + UNIT_SGST_TOTAL;	
			UNIT_AMOUNT_TOTAL = UNIT_PRICE + UNIT_CGST_TOTAL + UNIT_SGST_TOTAL;
			UNIT_GRAND_TOTAL = UNIT_PRICE;
			
			/*********************Calculations ************************/
			
			UNIT_PRICE = Math.round (UNIT_PRICE * 100.0) / 100.0;
			UNIT_PRICE_CGST = Math.round (UNIT_PRICE_CGST * 100.0) / 100.0;
			UNIT_PRICE_SGST = Math.round (UNIT_PRICE_SGST * 100.0) / 100.0;
			UNIT_PRICE_TOTAL = Math.round (UNIT_PRICE_TOTAL * 100.0) / 100.0;
			UNIT_TOTAL = Math.round (UNIT_TOTAL * 100.0) / 100.0;
			UNIT_CGST_TOTAL = Math.round (UNIT_CGST_TOTAL * 100.0) / 100.0;
			UNIT_SGST_TOTAL = Math.round (UNIT_SGST_TOTAL * 100.0) / 100.0;
			UNIT_AMOUNT_TOTAL = Math.round (UNIT_AMOUNT_TOTAL * 100.0) / 100.0;
			UNIT_TAX = Math.round (UNIT_TAX * 100.0) / 100.0;
			UNIT_GRAND_TOTAL = Math.round (UNIT_GRAND_TOTAL * 100.0) / 100.0;
			
			LogLevel.DEBUG(5, new Throwable(), "UNIT_PRICE :" + UNIT_PRICE);
			LogLevel.DEBUG(5, new Throwable(), "UNIT_PRICE_TOTAL :" + UNIT_PRICE_TOTAL);
			LogLevel.DEBUG(5, new Throwable(), "UNIT_TOTAL :" + UNIT_TOTAL);
			LogLevel.DEBUG(5, new Throwable(), "UNIT_TAX :" + UNIT_TAX);
			LogLevel.DEBUG(5, new Throwable(), "UNIT_AMOUNT_TOTAL :" + UNIT_AMOUNT_TOTAL);
			LogLevel.DEBUG(5, new Throwable(), "UNIT_GRAND_TOTAL :" + UNIT_GRAND_TOTAL);
						
			Date date = new Date();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			int currentYear = calendar.get(Calendar.YEAR);
			//Add one to month {0 - 11}
			//int months = calendar.get(Calendar.MONTH) + 1;
			String months = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

			int day = calendar.get(Calendar.DAY_OF_MONTH);
			SimpleDateFormat sdfDate=new SimpleDateFormat("dd-MM-yyyy");
			String strDate = sdfDate.format(date);
			 
			
			LogLevel.DEBUG(5,new Throwable(),"months :"+months);
			LogLevel.DEBUG(5,new Throwable(),"currentYear :"+currentYear);
			
			String Sequence_Query = "select seq_number from batterystore_invoice where seq_number!='0' and store_name='"+retailer_name+"' and order_type='"+order_type+"' order by inv_id desc limit 1";
			LogLevel.DEBUG(5,new Throwable(),"Sequence_Query :"+Sequence_Query );
			Hashtable htSeq_number = qm.getRow(Sequence_Query); 
								
				int invoice_code = 0;
				int Seq_number_Add = 0;
				if(htSeq_number.isEmpty())
				{ 
					invoice_code = 1;
					String verificationcode1 = Integer.toString(invoice_code);
					verificationcode =verificationcode1;
					LogLevel.DEBUG(5, new Throwable(), "verificationcode Empty:" + verificationcode);
					LogLevel.DEBUG(5,new Throwable(),"INSIDE EMPTY FIRST Time");
					
				} else {
					
					LogLevel.DEBUG(5,new Throwable(),"INSIDE ELSE Second Time");
				
					String Seq_number=String.valueOf(htSeq_number.get("seq_number"));
					//int Seq_number=htSeq_number.getInt("seq_number");
					Seq_number_Add = Integer.parseInt(Seq_number);
					invoice_code =  Seq_number_Add + 1;
					LogLevel.DEBUG(5,new Throwable(),"Seq_number String:"+Seq_number);
					LogLevel.DEBUG(5,new Throwable(),"Seq_number_Add Integer:"+Seq_number_Add);
				
					LogLevel.DEBUG(5,new Throwable(),"invoice_code:"+invoice_code);
				  
					String verificationcode1 = Integer.toString(invoice_code);
					verificationcode =verificationcode1;
					LogLevel.DEBUG(5, new Throwable(), "verificationcode 9:" + verificationcode);
					
					 
				}
			String excelName = "ASIST-B2C-Trolley-"+verificationcode+".pdf";
			//String strCMSServerIP = (propsMOPConfig.getProperty("CMS_SERVER_IP")!=null)?(propsMOPConfig.getProperty("CMS_SERVER_IP")).trim():"bookbattery.com";
			//String strCMSSERVERIP	= (propsMOPConfig.getProperty("CMS_SERVER_IP")!=null)?(propsMOPConfig.getProperty("CMS_SERVER_IP")):"";
			//LogLevel.DEBUG(5, new Throwable(), "strCMSServerIP:" + strCMSSERVERIP);
			LogLevel.DEBUG(5, new Throwable(), "excelName :" + excelName);
			String strPDFFilePath = "/home/ngit/tomcat/webapps/bookbattery/userdata/billing/batterystoreinvoice/" +currentYear+ "/" +months+ "/" +city+ "/" +order_type;
			LogLevel.DEBUG(5, new Throwable(), "strPDFFilePath :" + strPDFFilePath);
			File excelUploadPathCreate = new File(strPDFFilePath);
			LogLevel.DEBUG(5, new Throwable(), "excelUploadPathCreate :" + excelUploadPathCreate);
			if(excelUploadPathCreate.mkdirs())
			{
				LogLevel.DEBUG(5,new Throwable()," PDF for invoice	Name directory created :  "	+ excelUploadPathCreate);
				
			} else {
				
				
			}

				//strPDFRelFilePath	= strPDFRelFilePath +"/"+ currentYear + "/" +months;

				String excelFile = excelUploadPathCreate+"/"+excelName;
				LogLevel.DEBUG(5, new Throwable(), "excelFile :" + excelFile);
				File file=new File(excelFile);
				if(!file.exists()) 
				file.createNewFile(); 
				FileWriter fw=new FileWriter(file);
				
			//String strPdfURL="http://"+strCMSServerIP+"/bookbattery/userdata/billing/salesorders/"+currentYear+"/"+months+"/"+"battery"+"/"+"invoice"+"/"+city+"/"+excelName;
			String strPdfURL="http://stage1.bookbattery.com/bookbattery/userdata/billing/batterystoreinvoice/"+currentYear+"/"+months+ "/" +city+ "/"  +order_type+ "/" +excelName;
			LogLevel.DEBUG(5, new Throwable(), "strPdfURL : " + strPdfURL);	
			
			String order_number_iq="";
			String invoice_number_iq="";
			String invoice_url_iq="";
			String invoice_file="";
					
					
			String CheckInvoiceQuery_SQL = "Select order_number,invoice_number,invoice_url,customer_name,store_name,order_type,seq_number from batterystore_invoice where order_number='"+ordernumber+"'";
					
			Hashtable InvoiceQuery_HT = qm.getRow(CheckInvoiceQuery_SQL);
			if(InvoiceQuery_HT.isEmpty())
				{
					
					String strInoviceQry = "insert into batterystore_invoice(order_number,invoice_number,invoice_url,customer_name,store_name,order_type,seq_number)values('"+ordernumber+"','"+excelName+"','"+strPdfURL+"','"+consumer_name+"','"+retailer_name+"','"+order_type+"','"+invoice_code+"')";
					LogLevel.DEBUG(5,new Throwable(),"strInoviceQry : "+strInoviceQry);
					int reslt = qm.executeUpdate(strInoviceQry);

					if(reslt <0)
					{
						LogLevel.DEBUG(1,new Throwable(),"Failed to Generate! ");
					}
					else
					{
						LogLevel.DEBUG(1,new Throwable(),"Successfully Generated! ");	
					}
				}
				else
				{
					
					 order_number_iq=(String)InvoiceQuery_HT.get("order_number");
					 invoice_number_iq=(String)InvoiceQuery_HT.get("invoice_number");
					 invoice_url_iq=(String)InvoiceQuery_HT.get("invoice_url");
					 invoice_file = invoice_url_iq.replace("http://stage1.bookbattery.com/","/home/ngit/tomcat/webapps/");
				}
				

			/***************************Printing PDF ******************************/			
			Document document = new Document();
			PdfWriter pdf = PdfWriter.getInstance(document,new FileOutputStream(excelFile));
			document.open();
			
			PdfContentByte cb = pdf.getDirectContent();
			cb.setLineWidth(1f);
			cb.moveTo(0, 755);
			cb.lineTo(595, 755);
			cb.stroke();

			
			Image imghead = Image.getInstance("/home/ngit/tomcat/webapps/bookbattery/images/batterywalelogo.png");
			imghead.setAbsolutePosition(50,760);
			imghead.scaleAbsolute(120, 35);
			document.add(imghead);	
				
			
			//Paragraph paragraph= (new Paragraph("Original for Recipient \r\n TAX INVOICE NO : "+excelName.replace(".pdf","")+" \r\n Date : "+strDate+" \r\n P.O.# : "+order_number+" \r\n P.O.Date : "+strDate+"" ,FontFactory.getFont("Tahoma", 9, Font.NORMAL, new Color(0, 0, 0))));
			
			 Paragraph paragraph= (new Paragraph("Original for Recipient \r\n TAX INVOICE NO : "+excelName.replace(".pdf","")+" \r\n Date : "+strDate+" \r\n \r\n \r\n" ,FontFactory.getFont("Tahoma", 9, Font.NORMAL, new Color(0, 0, 0))));
			paragraph.setAlignment(Element.ALIGN_RIGHT);
			paragraph.setSpacingBefore(0.2f);
			document.add(paragraph);
			
			Paragraph parag_order= (new Paragraph("P.O.# : "+order_number+" \r\n P.O.Date : "+strDate+"" ,FontFactory.getFont("Tahoma", 9, Font.NORMAL, new Color(0, 0, 0))));
			parag_order.setAlignment(Element.ALIGN_RIGHT);
			parag_order.setSpacingBefore(2.2f);
			document.add(parag_order);
			
			Paragraph parag1 = (new Paragraph("BookBattery \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t  Bill To:" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag1.setAlignment(Element.ALIGN_LEFT);
			parag1.setSpacingBefore(1.2f);
			document.add(parag1);
			
			Paragraph parag2 = (new Paragraph("Shop No. 3, No. 166,Pattandu Aghrahara \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+consumer_name+"",FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag2.setAlignment(Element.ALIGN_LEFT);
			parag2.setSpacingBefore(0.5f);
			document.add(parag2);
				
			Paragraph parag3 = (new Paragraph("Opp to Govt Polytechnic College \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+customer_address+"",FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag3.setAlignment(Element.ALIGN_LEFT);
			parag3.setSpacingBefore(0.5f);
			document.add(parag3);
				
			Paragraph parag4 = (new Paragraph("WhiteField, Bangalore - 560066 \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t Place of Supply: "+city+"",FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag4.setAlignment(Element.ALIGN_LEFT);
			parag4.setSpacingBefore(0.5f);
			document.add(parag4);
				
			Paragraph parag5 = (new Paragraph("GSTIN No: 29AAJCA7469F2Z7 \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t",FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag5.setAlignment(Element.ALIGN_LEFT);
			parag5.setSpacingBefore(0.5f);
			document.add(parag5);
				
			Paragraph parag6 = (new Paragraph("Phone:+91 9666 300003 \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t Phone:"+consumer_mobnumber+"",FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag6.setAlignment(Element.ALIGN_LEFT);
			parag6.setSpacingBefore(0.5f);
			document.add(parag6);
			
			/*********************************************Invoice Content ************************************/
			
			PdfPCell cell02 = new PdfPCell(new Paragraph("ITEM DESCRIPTION", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell02.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell02.setBackgroundColor(Color.lightGray ); 
					
			PdfPCell cell04 = new PdfPCell(new Paragraph("QUANTITY/UOM", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell04.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell04.setBackgroundColor(Color.lightGray ); 
			
			PdfPCell cell05 = new PdfPCell(new Paragraph("UNIT PRICE", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell05.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell05.setBackgroundColor(Color.lightGray ); 
			
			PdfPCell cell06 = new PdfPCell(new Paragraph("CGST", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));			
			cell06.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell06.setBackgroundColor(Color.lightGray );
			
			PdfPCell cell07 = new PdfPCell(new Paragraph("SGST", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));			
			cell07.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell07.setBackgroundColor(Color.lightGray );
			
			PdfPCell cell09 = new PdfPCell(new Paragraph("AMOUNT", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));			
			cell09.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell09.setBackgroundColor(Color.lightGray );
			
		     PdfPTable table = new PdfPTable(arrPDFHeader.size());
             table.setWidthPercentage(100); 
			 table.setSpacingBefore(15);
			
            table.addCell(cell02); 
            table.addCell(cell04); 
            table.addCell(cell05); 
			table.addCell(cell06); 
			table.addCell(cell07); 
 			table.addCell(cell09); 
			
			cell02 = new PdfPCell(new Paragraph(""+trolley_brand+" TROLLEY \r\n\r\n"+trolley_model+"\r\n\r\n\r\n\r\n\r\n\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell02.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell02);
			 
			cell04 = new PdfPCell(new Paragraph(""+quantity+"\r\n Nos", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell04.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell04);
						
			cell05 = new PdfPCell(new Paragraph(""+UNIT_PRICE+"\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell05.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell05);
			
			cell06 = new PdfPCell(new Paragraph(""+UNIT_PRICE_CGST+"\r\n 9%", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell06.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell06);
					
			cell07 = new PdfPCell(new Paragraph(""+UNIT_PRICE_SGST+"\r\n 9%", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell07.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell07);
			
			cell09 = new PdfPCell(new Paragraph(""+UNIT_AMOUNT_TOTAL+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell09.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell09);
			
			/*************************CODE FOR TOTAL ****************************/		
			cell02 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell02.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell02);
			 
			cell04 = new PdfPCell(new Paragraph("Total\r\n\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell04.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell04);
						
			cell05 = new PdfPCell(new Paragraph(""+UNIT_PRICE+"\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell05.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell05);
			
			cell06 = new PdfPCell(new Paragraph(""+UNIT_PRICE_CGST+"\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell06.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell06);
					
			cell07 = new PdfPCell(new Paragraph(""+UNIT_PRICE_SGST+"\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell07.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell07);
			
			cell09 = new PdfPCell(new Paragraph(""+UNIT_AMOUNT_TOTAL+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell09.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(cell09);
			document.add(table);
			/*********************************************Invoice Content ************************************/
			
			/***************************Terms & Condition *****************************/
			Paragraph parag_terms = (new Paragraph("Terms & Conditions:-: \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag_terms.setAlignment(Element.ALIGN_LEFT);
			parag_terms.setSpacingBefore(15.2f);
			document.add(parag_terms);
			Paragraph parag_tcond = (new Paragraph("We Declare that the Product in this invoice Are Covered by the Manufacture's\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag_tcond.setAlignment(Element.ALIGN_LEFT);
			parag_tcond.setSpacingBefore(0.2f);
			document.add(parag_tcond);
			Paragraph parag_tcond_one = (new Paragraph("STANDARD WARRANTY, We have no Legal/Financial Liability for the Same.\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag_tcond_one.setAlignment(Element.ALIGN_LEFT);
			parag_tcond_one.setSpacingBefore(0.2f);
			document.add(parag_tcond_one);
			Paragraph parag_bank_head = (new Paragraph("Company Bank Details:- \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t For, BookBattery" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag_bank_head.setAlignment(Element.ALIGN_LEFT);
			parag_bank_head.setSpacingBefore(20.2f);
			document.add(parag_bank_head);
 			Paragraph parag_account = (new Paragraph("Bank Name & Branch -  HDFC Bank Ltd, BTM Layout(Branch), \r\n Account Name : ASISTMI SOLUTIONS PRIVATE LIMITED. \r\n Account Number & IFSC Code: 08858470000098, HDFC0000885. \r\n PAN No : AAJCA7469F." ,FontFactory.getFont("Tahoma", 9, Font.NORMAL, new Color(0, 0, 0))));
			parag_account.setAlignment(Element.ALIGN_LEFT);
			parag_account.setSpacingBefore(2.2f);
			document.add(parag_account);
 			Paragraph parag_sign = (new Paragraph("Authorized Signatory" ,FontFactory.getFont("Tahoma", 9, Font.NORMAL, new Color(0, 0, 0))));
			parag_sign.setAlignment(Element.ALIGN_RIGHT);
			parag_sign.setSpacingBefore(2.2f);
			document.add(parag_sign);
 			Paragraph parag_dec = (new Paragraph("This is Computer Generated Invoice" ,FontFactory.getFont("Tahoma", 9, Font.NORMAL, new Color(0, 0, 0))));
			parag_dec.setAlignment(Element.ALIGN_CENTER);
			parag_dec.setSpacingBefore(12.2f);
			document.add(parag_dec);	
			
			document.close();
			
			//return 0;
		}
		/*catch(IOException ioe)
		{
			System.out.println("IOExcepion Occured in writeMessagesSentToPdf : " + ioe);
			ioe.printStackTrace();
			return -1;
		}
		catch(NullPointerException npe)
		{
			System.out.println("Null Pointer Excepion Occured in writeMessagesSentToPdf: " + npe);
			npe.printStackTrace();
			return -1;
		}*/
		catch(Exception e)
		{
			System.out.println("Excepion Occured in writeMessagesSentToPdf : " + e);
			e.printStackTrace();
			return -1;
		}
		return 0;		
				
			
	}
	/*******************PDF Creation for trolley PDF Ends*********************/
	
}