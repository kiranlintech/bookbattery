/*****************************************************
NGIT. Confidential.
@File Name		: GenerateExcel.java
@Description	: TO generate Excel Report
@Author			: Sai Krishna Daddala  
@Date			: 18-08-2009
@Reviewed By	: 
*******************************************************/


package com.ngit.javabean.admin.mis;

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

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

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
import java.awt.image.BufferedImage;

import com.lowagie.text.Header;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Font;
import com.lowagie.text.Element;

import java.io.*;
import java.util.*;
//Pdf Import Ends here

public class GenerateExcelbattery extends HttpServlet 
{
	private String outfile;
	private WritableWorkbook workbook;
	Properties propsMOPConfig;
	QueryManager qm;
	private ServletContext context; 

	public GenerateExcelbattery()
	{
	}
	public GenerateExcelbattery(String ofn)
	{
		outfile = ofn;
		//reading props file starts here
		String strMOPConfig = "/bookbattery/properties/bookbatteryconfig.properties";
		LogLevel.DEBUG(5, new Throwable(), "propsMOPConfig---->"+propsMOPConfig);
		propsMOPConfig = new Properties();
		context = getServletContext();
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
					LogLevel.DEBUG(5,new Throwable(),"strData is prakash: "+strData);
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

	public int writeToText(ArrayList arrPDFHeader,ArrayList dbHeaders,Vector alReport,String strTempTextFile,String strReportTitle)
	{
		try
		{
			LogLevel.DEBUG(4,new Throwable(),"Inside writeToText");
			
			File file=new File(strTempTextFile); 
			if(!file.exists()) 
				file.createNewFile(); 
			//FileWriter fw=new FileWriter(file); 
			FileOutputStream fw=new FileOutputStream(file); 

			String strData=strReportTitle;
			strData=strData+"\n";
			fw.write(strData.getBytes());
 			strData="";
			//Writing the Headings to the Text File 
			for(int h=0;h<arrPDFHeader.size();h++)
			{
				String strHeader = String.valueOf(arrPDFHeader.get(h));
				strData=(strData.equals("")?strHeader:strData+"|"+strHeader);
 			}
			strData=strData+"\n";
			fw.write(strData.getBytes());
		
 			//Writing the Report Data under the respective Heading into Text File
			Hashtable hashContent = new Hashtable();
			for (int j=0; j<alReport.size(); j++)
			{
				strData="";
				hashContent   = (Hashtable)alReport.get(j);
				for (int ik=0;ik < dbHeaders.size() ;ik++ )
				{
					String strHeader=(String)dbHeaders.get(ik);
					String strData1=(hashContent.get(strHeader)!=null)?String.valueOf(hashContent.get(strHeader)):"-";
					strData=(strData.equals("")?strData1:strData+"|"+strData1);
 				}
				strData=strData+"\n";
				fw.write(strData.getBytes());
			}
			fw.close();
		
		}
		catch(IOException ioe)
		{
			System.out.println("IOExcepion Occured in writeToText : " + ioe);
			ioe.printStackTrace();
			return -1;
		}
		catch(NullPointerException npe)
		{
			System.out.println("Null Pointer Excepion Occured in writeMessagesSentToPdf: " + npe);
			npe.printStackTrace();
			return -1;
		}
		catch(Exception e)
		{
			System.out.println("Excepion Occured in writeMessagesSentToPdf : " + e);
			e.printStackTrace();
			return -1;
		}
		return 0;
	}
	
	public int writeToPdf(ArrayList arrPDFHeader,ArrayList dbHeaders,ArrayList arrPDFHeader1,ArrayList dbHeaders1,Vector alReport,Vector alReport1,ArrayList arrInvPDFHeader,ArrayList dbInvHeaders,ArrayList arrInvPDFHeader1,ArrayList dbInvHeaders1,Vector alInvReport,Vector alInvReport1,ArrayList arrSerPDFHeader,ArrayList dbSerHeaders,ArrayList arrSerPDFHeader1,ArrayList dbSerHeaders1,Vector alSerReport,Vector alSerReport1,String strTempTextFile,String strCMSSERVERIP,String strReportTitle,String strReportTitle1,String strReportInvTitle,String strReportInvTitle1,String strReportSerTitle,String strReportserTitle1,String ReportDate)
	{
		try
		{
			LogLevel.DEBUG(4,new Throwable(),"Inside writeToPdf");
			LogLevel.DEBUG(5,new Throwable(),"strTempTextFile in WriteToExcel: "+strTempTextFile);
			LogLevel.DEBUG(5,new Throwable(),"strReportName in WriteToExcel: "+strReportTitle);
			LogLevel.DEBUG(5,new Throwable(),"strCMSSERVERIP: "+strCMSSERVERIP);
			LogLevel.DEBUG(5,new Throwable(),"strReportTitle1: "+strReportTitle1);
			LogLevel.DEBUG(5,new Throwable(),"arrPDFHeader: "+arrPDFHeader);
			LogLevel.DEBUG(5,new Throwable(),"dbHeaders: "+dbHeaders);
			LogLevel.DEBUG(5,new Throwable(),"arrPDFHeader1: "+arrPDFHeader1);
			LogLevel.DEBUG(5,new Throwable(),"dbHeaders1: "+dbHeaders1);
			LogLevel.DEBUG(5,new Throwable(),"alReport: "+alReport);
			LogLevel.DEBUG(5,new Throwable(),"alReport1: "+alReport1);
			LogLevel.DEBUG(5,new Throwable(),"strTempTextFile: "+strTempTextFile);
			LogLevel.DEBUG(5,new Throwable(),"strCMSSERVERIP: "+strCMSSERVERIP);
			LogLevel.DEBUG(5,new Throwable(),"strReportTitle: "+strReportTitle);
			LogLevel.DEBUG(5,new Throwable(),"strReportTitle1: "+strReportTitle1);
			LogLevel.DEBUG(5,new Throwable(),"ReportDate: "+ReportDate);
			
			
			Document document = new Document();
			PdfWriter pdf = PdfWriter.getInstance(document,new FileOutputStream(strTempTextFile));
			document.open();
			
			PdfContentByte cb = pdf.getDirectContent();
			cb.setLineWidth(1f);
			cb.moveTo(0, 755);
			cb.lineTo(595, 755);
			cb.stroke();

			 PdfPTable table = new PdfPTable(arrPDFHeader.size());
             table.setWidthPercentage(100);  
			 PdfPTable table1 = new PdfPTable(arrPDFHeader1.size());
             table1.setWidthPercentage(100);  			 
			 PdfPTable tableinv = new PdfPTable(arrInvPDFHeader.size());
             tableinv.setWidthPercentage(100);  
			 PdfPTable table1inv = new PdfPTable(arrInvPDFHeader1.size());
             table1inv.setWidthPercentage(100);  			 
			 PdfPTable tableser = new PdfPTable(arrSerPDFHeader.size());
             tableser.setWidthPercentage(100);  
			 PdfPTable table1ser = new PdfPTable(arrSerPDFHeader1.size());
             table1ser.setWidthPercentage(100);  

			

			if(strReportTitle.contains("Amaron-Pitstop"))
			{
				Image imghead = Image.getInstance("/home/ngit/tomcat/webapps/bookbattery/images/amaronexpresslogo_amaron.png");
				imghead.setAbsolutePosition(50,760);
				imghead.scaleAbsolute(120, 35);
				document.add(imghead);		
			
				Image imghead1 = Image.getInstance("/home/ngit/tomcat/webapps/bookbattery/images/amaronexpresslogo_amaron.png");
				imghead1.setAbsolutePosition(440,810);
				imghead1.scaleAbsolute(125,25);
				//document.add(imghead1);
					
				
				Paragraph paragraph= (new Paragraph("www.amaronexpress.com", FontFactory.getFont("Tahoma", 18, Font.NORMAL, new Color(112, 86, 245))));
				paragraph.setAlignment(Element.ALIGN_RIGHT);
				document.add(paragraph);
			}
			else
			{
						
				//Image imghead = Image.getInstance("https://www.bookbattery.com/bookbattery/images/batterywalelogo.png");
				Image imghead = Image.getInstance("/home/ngit/tomcat/webapps/bookbattery/images/batterywalelogo.png");
				imghead.setAbsolutePosition(50,760);
				imghead.scaleAbsolute(120, 35);
				document.add(imghead);		
				
				Image imghead1 = Image.getInstance("/home/ngit/tomcat/webapps/bookbattery/images/combined_logo_new.png");
				imghead1.setAbsolutePosition(440,810);
				imghead1.scaleAbsolute(125,25);
				//document.add(imghead1);
					
				
				Paragraph paragraph= (new Paragraph("www.bookbattery.com", FontFactory.getFont("Tahoma", 18, Font.NORMAL, new Color(112, 86, 245))));
				paragraph.setAlignment(Element.ALIGN_RIGHT);
				document.add(paragraph);
			}
				

			Paragraph parag = (new Paragraph(new Date().toString(),FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0, 0, 0))));
			parag.setAlignment(Element.ALIGN_RIGHT);
			document.add(parag);

			Paragraph parag1 = (new Paragraph(ReportDate,FontFactory.getFont("Tahoma", 6, Font.NORMAL, new Color(0, 0, 0))));
			parag1.setAlignment(Element.ALIGN_RIGHT);
			document.add(parag1);

		//###############Generating Order Summary for Batteries Starts here##################//
			
			//###############Generating Offline Order Summary Starts here##################//
			if(alReport.size()==0 && alReport1.size()==0)
			{
				
			}
			else
			{
				Paragraph paragraphtit1OT= (new Paragraph("Battery Order Details", FontFactory.getFont("Tahoma", 12, Font.NORMAL, new Color(52,52,247))));
				paragraphtit1OT.setSpacingBefore(7.2f); 
				paragraphtit1OT.setAlignment(Element.ALIGN_CENTER);
				document.add(paragraphtit1OT);				
			}
			

			Paragraph paragraphtit= (new Paragraph(strReportTitle, FontFactory.getFont("Tahoma", 12, Font.NORMAL, new Color(52,52,247))));
			paragraphtit.setSpacingBefore(7.2f); 
			paragraphtit.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraphtit);

			//Ends OF report Summary
			 table.setSpacingBefore(10);

			//Writing the Headings to the Pdf 
			for(int h=0;h<arrPDFHeader.size();h++)
			{
				String strHeader = String.valueOf(arrPDFHeader.get(h));
				Phrase phrase2 = new Phrase(strHeader, FontFactory.getFont("Tahoma", 6, Font.BOLD, new Color(0, 0, 0)));
				//Font fontbold = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
				// document.add(new Paragraph("Times-Roman, Bold", fontbold));
				PdfPCell cell = new PdfPCell(new Paragraph(phrase2));
				cell.setBackgroundColor(Color.lightGray );    
				table.addCell(cell);
			}
			table.setHeaderRows(1);	
			//Writing Heading Ends here
			//Writing the Report Data under the respective Heading into PDF
			Hashtable hashContent = new Hashtable();
			String serialno="0";
			for (int j=0; j<alReport.size(); j++)
			{
				hashContent   = (Hashtable)alReport.get(j);
				int value = Integer.parseInt(serialno);
				LogLevel.DEBUG(5,new Throwable(),"value is : "+value);
				int nextValue = value + 1; // find the int value plus 1

				serialno= String.valueOf(nextValue) ;			
				LogLevel.DEBUG(4,new Throwable(),"serialno value"+serialno);
				
				for (int ik=0;ik < dbHeaders.size() ;ik++ )
				{
					String strHeader=(String)dbHeaders.get(ik);
					String strData=(hashContent.get(strHeader)!=null)?String.valueOf(hashContent.get(strHeader)):"-";
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
						strData =strData;
					}
					
					Phrase phrase3 = new Phrase(strData, FontFactory.getFont("Tahoma", 6, Font.NORMAL, new Color(0, 0, 0)));
					table.addCell(phrase3);

				}
			}						
			document.add(table);
			
			
			//###############Generating Online Order Summary Ends here##################//
			
			table1.setSpacingBefore(10);
			
			Paragraph paragraphtit1= (new Paragraph(strReportTitle1, FontFactory.getFont("Tahoma", 12, Font.NORMAL, new Color(52,52,247))));
			paragraphtit1.setSpacingBefore(7.2f); 
			paragraphtit1.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraphtit1);

			table1.setSpacingBefore(10);
			
			
			//Writing the Headings to the Pdf 
			for(int h1=0;h1<arrPDFHeader1.size();h1++)
			{
				String strHeader1 = String.valueOf(arrPDFHeader1.get(h1));
				
				
				Phrase phrase21 = new Phrase(strHeader1, FontFactory.getFont("Tahoma", 6, Font.BOLD, new Color(0, 0, 0)));
				//Font fontbold = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
				// document.add(new Paragraph("Times-Roman, Bold", fontbold));
				PdfPCell cell1 = new PdfPCell(new Paragraph(phrase21));
				cell1.setBackgroundColor(Color.lightGray );    
				table1.addCell(cell1);
			}
			table1.setHeaderRows(1);	
			//Writing Heading Ends here
			
			//Writing the Report Data under the respective Heading into PDF
			Hashtable hashContent1 = new Hashtable();
			String serialno1="0";
			for (int j1=0; j1<alReport1.size(); j1++)
			{
				hashContent1   = (Hashtable)alReport1.get(j1);
				LogLevel.DEBUG(4,new Throwable(),"hashContent1 :"+hashContent1);
				int value1 = Integer.parseInt(serialno1);
				LogLevel.DEBUG(5,new Throwable(),"value1 is : "+value1);
				int nextValue1 = value1 + 1; // find the int value1 plus 1

				serialno1= String.valueOf(nextValue1) ;			
				LogLevel.DEBUG(4,new Throwable(),"serialno1 value1"+serialno1);
				
				for (int ik1=0;ik1 < dbHeaders1.size() ;ik1++ )
				{
					String strHeader1=(String)dbHeaders1.get(ik1);
					String strData1=(hashContent1.get(strHeader1)!=null)?String.valueOf(hashContent1.get(strHeader1)):"-";
					LogLevel.DEBUG(5,new Throwable(),"strData1 Outside :"+strData1);
					if(strHeader1.equals("0") || strHeader1.equals(0))
					{

						strData1=serialno1;
						LogLevel.DEBUG(5,new Throwable(),"strData1 :"+strData1);

					}
					if(strData1.equals("null") || strData1.equals(null) || strData1.equals("0000-00-00"))
					{
						strData1 = "-";
					}
					else
					{
						strData1 =strData1;
					}
					
					Phrase phrase31 = new Phrase(strData1, FontFactory.getFont("Tahoma", 6, Font.NORMAL, new Color(0, 0, 0)));
					table1.addCell(phrase31);

				}
			}						
			
			document.add(table1);
			
			
			
			//###############Generating Online Order Summary Ends here##################//
			
			//###############Generating Order Summary for Batteries Ends here##################//
			
			
			
	//###############Generating Order Summary for Inverters Starts here##################//
			
		//###############Generating Offline Order Summary Starts here##################//

			if(alInvReport.size()==0 && alInvReport1.size()==0)
			{
				
			}
			else
			{
				Paragraph paragraphtit1OTinv= (new Paragraph("Inverter Order Details", FontFactory.getFont("Tahoma", 12, Font.NORMAL, new Color(52,52,247))));
				paragraphtit1OTinv.setSpacingBefore(7.2f); 
				paragraphtit1OTinv.setAlignment(Element.ALIGN_CENTER);
				document.add(paragraphtit1OTinv);
			}
			

			Paragraph paragraphtitinv= (new Paragraph(strReportInvTitle, FontFactory.getFont("Tahoma", 12, Font.NORMAL, new Color(52,52,247))));
			paragraphtitinv.setSpacingBefore(7.2f); 
			paragraphtitinv.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraphtitinv);
			

			//Ends OF report Summary
			 tableinv.setSpacingBefore(10);

			//Writing the Headings to the Pdf 
			for(int h=0;h<arrInvPDFHeader.size();h++)
			{
				String strHeader = String.valueOf(arrInvPDFHeader.get(h));
				Phrase phrase2 = new Phrase(strHeader, FontFactory.getFont("Tahoma", 6, Font.BOLD, new Color(0, 0, 0)));
				//Font fontbold = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
				// document.add(new Paragraph("Times-Roman, Bold", fontbold));
				PdfPCell cell = new PdfPCell(new Paragraph(phrase2));
				cell.setBackgroundColor(Color.lightGray );    
				tableinv.addCell(cell);
			}
			tableinv.setHeaderRows(1);	
			//Writing Heading Ends here
			//Writing the Report Data under the respective Heading into PDF
			Hashtable hashContentinv = new Hashtable();
			String serialnoinv="0";
			for (int j=0; j<alInvReport.size(); j++)
			{
				hashContentinv   = (Hashtable)alInvReport.get(j);
				int value = Integer.parseInt(serialno);
				LogLevel.DEBUG(5,new Throwable(),"value is : "+value);
				int nextValue = value + 1; // find the int value plus 1

				serialno= String.valueOf(nextValue) ;			
				LogLevel.DEBUG(4,new Throwable(),"serialno value"+serialno);
				
				for (int ik=0;ik < dbInvHeaders.size() ;ik++ )
				{
					String strHeader=(String)dbInvHeaders.get(ik);
					String strData=(hashContentinv.get(strHeader)!=null)?String.valueOf(hashContentinv.get(strHeader)):"-";
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
						strData =strData;
					}
					
					Phrase phrase3 = new Phrase(strData, FontFactory.getFont("Tahoma", 6, Font.NORMAL, new Color(0, 0, 0)));
					tableinv.addCell(phrase3);

				}
			}						
			document.add(tableinv);
			
			
			//###############Generating Online Order Summary Ends here##################//
			
			table1inv.setSpacingBefore(10);
			
			Paragraph paragraphtit1inv= (new Paragraph(strReportInvTitle1, FontFactory.getFont("Tahoma", 12, Font.NORMAL, new Color(52,52,247))));
			paragraphtit1inv.setSpacingBefore(7.2f); 
			paragraphtit1inv.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraphtit1inv);
			
			
			table1inv.setSpacingBefore(10);
			
			
			//Writing the Headings to the Pdf 
			for(int h1=0;h1<arrInvPDFHeader1.size();h1++)
			{
				String strHeader1 = String.valueOf(arrInvPDFHeader1.get(h1));
				
				
				Phrase phrase21 = new Phrase(strHeader1, FontFactory.getFont("Tahoma", 6, Font.BOLD, new Color(0, 0, 0)));
				//Font fontbold = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
				// document.add(new Paragraph("Times-Roman, Bold", fontbold));
				PdfPCell cell1 = new PdfPCell(new Paragraph(phrase21));
				cell1.setBackgroundColor(Color.lightGray );    
				table1inv.addCell(cell1);
			}
			table1inv.setHeaderRows(1);	
			//Writing Heading Ends here
			
			//Writing the Report Data under the respective Heading into PDF
			Hashtable hashContent1inv = new Hashtable();
			String serialno1inv="0";
			for (int j1=0; j1<alInvReport1.size(); j1++)
			{
				hashContent1inv   = (Hashtable)alInvReport1.get(j1);
				LogLevel.DEBUG(4,new Throwable(),"hashContent1inv :"+hashContent1inv);
				int value1 = Integer.parseInt(serialno1);
				LogLevel.DEBUG(5,new Throwable(),"value1 is : "+value1);
				int nextValue1 = value1 + 1; // find the int value1 plus 1

				serialno1= String.valueOf(nextValue1) ;			
				LogLevel.DEBUG(4,new Throwable(),"serialno1 value1"+serialno1);
				
				for (int ik1=0;ik1 < dbInvHeaders1.size() ;ik1++ )
				{
					String strHeader1=(String)dbInvHeaders1.get(ik1);
					String strData1=(hashContent1inv.get(strHeader1)!=null)?String.valueOf(hashContent1inv.get(strHeader1)):"-";
					LogLevel.DEBUG(5,new Throwable(),"strData1 Outside :"+strData1);
					if(strHeader1.equals("0") || strHeader1.equals(0))
					{

						strData1=serialno1;
						LogLevel.DEBUG(5,new Throwable(),"strData1 :"+strData1);

					}
					if(strData1.equals("null") || strData1.equals(null) || strData1.equals("0000-00-00"))
					{
						strData1 = "-";
					}
					else
					{
						strData1 =strData1;
					}
					
					Phrase phrase31 = new Phrase(strData1, FontFactory.getFont("Tahoma", 6, Font.NORMAL, new Color(0, 0, 0)));
					table1inv.addCell(phrase31);

				}
			}						
			
			document.add(table1inv);
			
			
			//###############Generating Online Order Summary Ends here##################//
			
			//###############Generating Order Summary for Inverters Ends here##################//		
			
			//###############Generating Order Summary for Services Starts here##################//
			
			//###############Generating Offline Order Summary Starts here##################//
			

			if(alSerReport.size()==0 && alSerReport1.size()==0)
			{
				
			}
			else
			{
				Paragraph paragraphtit1OTser= (new Paragraph("Service Order Details", FontFactory.getFont("Tahoma", 12, Font.NORMAL, new Color(52,52,247))));
				paragraphtit1OTser.setSpacingBefore(7.2f); 
				paragraphtit1OTser.setAlignment(Element.ALIGN_CENTER);
				document.add(paragraphtit1OTser);	
			}
			
			
			Paragraph paragraphtitser= (new Paragraph(strReportSerTitle, FontFactory.getFont("Tahoma", 12, Font.NORMAL, new Color(52,52,247))));
			paragraphtitser.setSpacingBefore(7.2f); 
			paragraphtitser.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraphtitser);

			//Ends OF report Summary
			 tableser.setSpacingBefore(10);

			//Writing the Headings to the Pdf 
			for(int h=0;h<arrSerPDFHeader.size();h++)
			{
				String strHeader = String.valueOf(arrSerPDFHeader.get(h));
				Phrase phrase2 = new Phrase(strHeader, FontFactory.getFont("Tahoma", 6, Font.BOLD, new Color(0, 0, 0)));
				//Font fontbold = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
				// document.add(new Paragraph("Times-Roman, Bold", fontbold));
				PdfPCell cell = new PdfPCell(new Paragraph(phrase2));
				cell.setBackgroundColor(Color.lightGray );    
				tableser.addCell(cell);
			}
			tableser.setHeaderRows(1);	
			//Writing Heading Ends here
			//Writing the Report Data under the respective Heading into PDF
			Hashtable hashContentser = new Hashtable();
			String serialnoser="0";
			for (int j=0; j<alSerReport.size(); j++)
			{
				hashContentser   = (Hashtable)alSerReport.get(j);
				int value = Integer.parseInt(serialno);
				LogLevel.DEBUG(5,new Throwable(),"value is : "+value);
				int nextValue = value + 1; // find the int value plus 1

				serialno= String.valueOf(nextValue) ;			
				LogLevel.DEBUG(4,new Throwable(),"serialno value"+serialno);
				
				for (int ik=0;ik < dbSerHeaders.size() ;ik++ )
				{
					String strHeader=(String)dbSerHeaders.get(ik);
					String strData=(hashContentser.get(strHeader)!=null)?String.valueOf(hashContentser.get(strHeader)):"-";
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
						strData =strData;
					}
					
					Phrase phrase3 = new Phrase(strData, FontFactory.getFont("Tahoma", 6, Font.NORMAL, new Color(0, 0, 0)));
					tableser.addCell(phrase3);

				}
			}						
			document.add(tableser);
			
			
			//###############Generating Online Order Summary Ends here##################//
			
			table1ser.setSpacingBefore(10);
			
			Paragraph paragraphtit1ser= (new Paragraph(strReportserTitle1, FontFactory.getFont("Tahoma", 12, Font.NORMAL, new Color(52,52,247))));
			paragraphtit1ser.setSpacingBefore(7.2f); 
			paragraphtit1ser.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraphtit1ser);
			
			
			table1ser.setSpacingBefore(10);
			
			
			//Writing the Headings to the Pdf 
			for(int h1=0;h1<arrSerPDFHeader1.size();h1++)
			{
				String strHeader1 = String.valueOf(arrSerPDFHeader1.get(h1));
				
				
				Phrase phrase21 = new Phrase(strHeader1, FontFactory.getFont("Tahoma", 6, Font.BOLD, new Color(0, 0, 0)));
				//Font fontbold = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
				// document.add(new Paragraph("Times-Roman, Bold", fontbold));
				PdfPCell cell1 = new PdfPCell(new Paragraph(phrase21));
				cell1.setBackgroundColor(Color.lightGray );    
				table1ser.addCell(cell1);
			}
			table1ser.setHeaderRows(1);	
			//Writing Heading Ends here
			
			//Writing the Report Data under the respective Heading into PDF
			Hashtable hashContent1ser = new Hashtable();
			String serialno1ser="0";
			for (int j1=0; j1<alSerReport1.size(); j1++)
			{
				hashContent1ser   = (Hashtable)alSerReport1.get(j1);
				LogLevel.DEBUG(4,new Throwable(),"hashContent1ser :"+hashContent1ser);
				int value1 = Integer.parseInt(serialno1);
				LogLevel.DEBUG(5,new Throwable(),"value1 is : "+value1);
				int nextValue1 = value1 + 1; // find the int value1 plus 1

				serialno1= String.valueOf(nextValue1) ;			
				LogLevel.DEBUG(4,new Throwable(),"serialno1 value1"+serialno1);
				
				for (int ik1=0;ik1 < dbSerHeaders1.size() ;ik1++ )
				{
					String strHeader1=(String)dbSerHeaders1.get(ik1);
					String strData1=(hashContent1ser.get(strHeader1)!=null)?String.valueOf(hashContent1ser.get(strHeader1)):"-";
					LogLevel.DEBUG(5,new Throwable(),"strData1 Outside :"+strData1);
					if(strHeader1.equals("0") || strHeader1.equals(0))
					{

						strData1=serialno1;
						LogLevel.DEBUG(5,new Throwable(),"strData1 :"+strData1);

					}
					if(strData1.equals("null") || strData1.equals(null) || strData1.equals("0000-00-00"))
					{
						strData1 = "-";
					}
					else
					{
						strData1 =strData1;
					}
					
					Phrase phrase31 = new Phrase(strData1, FontFactory.getFont("Tahoma", 6, Font.NORMAL, new Color(0, 0, 0)));
					table1ser.addCell(phrase31);

				}
			}						
			
			document.add(table1ser);
			
			
			
			//###############Generating Online Order Summary Ends here##################//
			
			//###############Generating Order Summary for Services Ends here##################//	
			

			
			document.close(); 
			
		//Ends OF report Summary			
		}
		catch(IOException ioe)
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
		}
		catch(Exception e)
		{
			System.out.println("Excepion Occured in writeMessagesSentToPdf : " + e);
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	public int writeToPdf3(ArrayList arrPDFHeader,ArrayList dbHeaders,Vector alReport,String strTempTextFile,String strCMSSERVERIP,String strReportTitle,String ReportDate)
	{
		try
		{
			LogLevel.DEBUG(4,new Throwable(),"Inside writeToPdf");
			LogLevel.DEBUG(5,new Throwable(),"strTempTextFile in WriteToExcel: "+strTempTextFile);
			LogLevel.DEBUG(5,new Throwable(),"strReportName in WriteToExcel: "+strReportTitle);
			LogLevel.DEBUG(5,new Throwable(),"strCMSSERVERIP: "+strCMSSERVERIP);
			
			Document document = new Document();
			PdfWriter pdf = PdfWriter.getInstance(document,new FileOutputStream(strTempTextFile));
			document.open();
			
			PdfContentByte cb = pdf.getDirectContent();
			cb.setLineWidth(1f);
			cb.moveTo(0, 755);
			cb.lineTo(595, 755);
			cb.stroke();

			 PdfPTable table = new PdfPTable(arrPDFHeader.size());
             table.setWidthPercentage(100);  

			Image imghead = Image.getInstance("https://"+strCMSSERVERIP+"/bookbattery/images/batterywalelogo.png");
			imghead.setAbsolutePosition(50,760);
			imghead.scaleAbsolute(120, 35);
			document.add(imghead);		
		
				
			Image imghead1 = Image.getInstance("https://"+strCMSSERVERIP+"/bookbattery/images/combined_logo_new.png");
			imghead1.setAbsolutePosition(440,810);
			imghead1.scaleAbsolute(125,25);
			//document.add(imghead1);
				
			
			Paragraph paragraph= (new Paragraph("www.bookbattery.com", FontFactory.getFont("Tahoma", 18, Font.NORMAL, new Color(112, 86, 245))));
			paragraph.setAlignment(Element.ALIGN_RIGHT);
			document.add(paragraph);
			
			Paragraph parag = (new Paragraph(new Date().toString(),FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0, 0, 0))));
			parag.setAlignment(Element.ALIGN_RIGHT);
			document.add(parag);

			Paragraph parag1 = (new Paragraph(ReportDate,FontFactory.getFont("Tahoma", 6, Font.NORMAL, new Color(0, 0, 0))));
			parag1.setAlignment(Element.ALIGN_RIGHT);
			document.add(parag1);

			Paragraph paragraphtit= (new Paragraph(strReportTitle, FontFactory.getFont("Tahoma", 12, Font.NORMAL, new Color(52,52,247))));
			paragraphtit.setSpacingBefore(7.2f); 
			paragraphtit.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraphtit);

			//Ends OF report Summary
			 table.setSpacingBefore(10);

			//Writing the Headings to the Pdf 
			for(int h=0;h<arrPDFHeader.size();h++)
			{
				String strHeader = String.valueOf(arrPDFHeader.get(h));
				Phrase phrase2 = new Phrase(strHeader, FontFactory.getFont("Tahoma", 6, Font.BOLD, new Color(0, 0, 0)));
				//Font fontbold = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
				// document.add(new Paragraph("Times-Roman, Bold", fontbold));
				PdfPCell cell = new PdfPCell(new Paragraph(phrase2));
				cell.setBackgroundColor(Color.lightGray );    
				table.addCell(cell);
			}
			table.setHeaderRows(1);	
			//Writing Heading Ends here
			//Writing the Report Data under the respective Heading into PDF
			Hashtable hashContent = new Hashtable();
			String serialno="0";
			for (int j=0; j<alReport.size(); j++)
			{
				hashContent   = (Hashtable)alReport.get(j);
				int value = Integer.parseInt(serialno);
				LogLevel.DEBUG(5,new Throwable(),"value is : "+value);
				int nextValue = value + 1; // find the int value plus 1

				serialno= String.valueOf(nextValue) ;			
				LogLevel.DEBUG(4,new Throwable(),"serialno value"+serialno);
				
				for (int ik=0;ik < dbHeaders.size() ;ik++ )
				{
					String strHeader=(String)dbHeaders.get(ik);
					String strData=(hashContent.get(strHeader)!=null)?String.valueOf(hashContent.get(strHeader)):"-";
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
						strData =strData;
					}
					
					Phrase phrase3 = new Phrase(strData, FontFactory.getFont("Tahoma", 6, Font.NORMAL, new Color(0, 0, 0)));
					table.addCell(phrase3);

				}
			}
			
			document.add(table);
			document.close(); 
			
		}
		catch(IOException ioe)
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
		}
		catch(Exception e)
		{
			System.out.println("Excepion Occured in writeMessagesSentToPdf : " + e);
			e.printStackTrace();
			return -1;
		}
		return 0;
	}
	

  public int writeToPdf1(String retailer,String Strlocorpin,String verificationcode,String strFromDate,String strToDate,ArrayList arrPDFHeader,ArrayList dbHeaders,Vector RetInoiceVector,Vector RetInoiceVector_Debit_Card,Vector RetInoiceVector_Credit_Card,Vector RetInoiceVector_Online,ArrayList arrPDFInverterHeader,ArrayList dbInverterHeaders,Vector RetInverterInoiceVector,Vector RetInverterInoiceVector_Debit_Card,Vector RetInverterInoiceVector_Credit_Card,Vector RetInverterInoiceVector_Online,ArrayList arrPDFServiceHeader,ArrayList dbServiceHeaders,Vector RetServiceInoiceVector,Vector RetServiceInoiceVector_Debit_Card,Vector RetServiceInoiceVector_Credit_Card,Vector RetServiceInoiceVector_Online,String strgrandtot,String Total_Reconciled_Amount,String strgrandtot_Online_Payment,String strservicetax,String strtottax,String strTempTextFile,String strCMSSERVERIP,String key,String retailerid)
	{
		try
		{
			LogLevel.DEBUG(4,new Throwable(),"Inside writeToPdf");
			LogLevel.DEBUG(5,new Throwable(),"strTempTextFile in WriteToExcel: "+strTempTextFile);
			//LogLevel.DEBUG(5,new Throwable(),"strReportName in WriteToExcel: "+strReportTitle);
			LogLevel.DEBUG(5,new Throwable(),"strCMSSERVERIP: "+strCMSSERVERIP);
			
			LogLevel.DEBUG(5,new Throwable(),"RetInoiceVector: "+RetInoiceVector);
			LogLevel.DEBUG(5,new Throwable(),"RetInoiceVector_Debit_Card: "+RetInoiceVector_Debit_Card);
			LogLevel.DEBUG(5,new Throwable(),"RetInoiceVector_Credit_Card: "+RetInoiceVector_Credit_Card);
			LogLevel.DEBUG(5,new Throwable(),"RetInoiceVector_Online: "+RetInoiceVector_Online);
			LogLevel.DEBUG(5,new Throwable(),"strgrandtot_Online_Payment: "+strgrandtot_Online_Payment);
			LogLevel.DEBUG(5,new Throwable(),"Total_Reconciled_Amount: "+Total_Reconciled_Amount);
			LogLevel.DEBUG(5,new Throwable(),"strgrandtot: "+strgrandtot);
			LogLevel.DEBUG(5,new Throwable(),"retailerid: "+retailerid);
			
			
			
			double strgrandtot_Online_Payment_double = Double.parseDouble(strgrandtot_Online_Payment);

			LogLevel.DEBUG(5,new Throwable(),"strgrandtot_Online_Payment_double: "+strgrandtot_Online_Payment_double);		

			double strgrandtot_int = Double.parseDouble(strgrandtot);

			LogLevel.DEBUG(5,new Throwable(),"strgrandtot_int: "+strgrandtot_int);
			
			double doublevalue = ((strgrandtot_int)/118)*(100);
			
			LogLevel.DEBUG(5,new Throwable(),"Sgst: "+doublevalue);
			
			
			double doublefinvalue = Math.round (doublevalue * 100.0) / 100.0;
			
			
			LogLevel.DEBUG(5,new Throwable(),"doublefinvalue: "+doublefinvalue);
			

			double Totgst1 = ((strgrandtot_int)*(0.18));
			
			
			//double Totgst1 = strgrandtot_int-doublefinvalue;
			
			LogLevel.DEBUG(5,new Throwable(),"Totgst1: "+Totgst1);
			
			double Totgst = Math.round (Totgst1 * 100.0) / 100.0;
			
			LogLevel.DEBUG(5,new Throwable(),"Totgst: "+Totgst);
			
			
			double Tot_comm_with_tax_double = strgrandtot_int + Totgst;

			LogLevel.DEBUG(5,new Throwable(),"Tot_comm_with_tax_double: "+Tot_comm_with_tax_double);
			
			double Tot_comm_with_tax_double_round= Math.round (Tot_comm_with_tax_double * 100.0) / 100.0;
			
			int Tot_comm_with_tax_double_round_final= (int) Math.round (Tot_comm_with_tax_double_round);
			
			double Cgst1 = (Totgst/2);
			
			double Cgst = Math.round (Cgst1 * 100.0) / 100.0;
			
			
			LogLevel.DEBUG(5,new Throwable(),"Cgst: "+Cgst);
			
			double Sgst1 = (Totgst/2);
			
			double Sgst = Math.round (Sgst1 * 100.0) / 100.0;
			
			
			LogLevel.DEBUG(5,new Throwable(),"Sgst: "+Sgst);
			
			double Total_Reconciled_Amount_double_with_tax = Tot_comm_with_tax_double-strgrandtot_Online_Payment_double;
			
			double Total_Reconciled_Amount_double_with_tax_round= Math.round (Total_Reconciled_Amount_double_with_tax * 100.0) / 100.0;
			
			int Total_Reconciled_Amount_double_with_tax_round_int= (int) Math.round (Total_Reconciled_Amount_double_with_tax_round);
			
			//double doublevalue= strgrandtot_int-Totgst;

			String strgrandtot1 = Double.toString(doublefinvalue);

			LogLevel.DEBUG(5,new Throwable(),"strgrandtot1: "+strgrandtot1);
			
			String Totgststrr = Double.toString(Totgst);
			
			LogLevel.DEBUG(5,new Throwable(),"Totgststrr: "+Totgststrr);
			
			String Cgststr = Double.toString(Cgst);
			
			LogLevel.DEBUG(5,new Throwable(),"Cgststr: "+Cgststr);
			
			String Sgststr = Double.toString(Sgst);
			
			LogLevel.DEBUG(5,new Throwable(),"Sgststr: "+Sgststr);			
			
			String Tot_comm_with_tax = Integer.toString(Tot_comm_with_tax_double_round_final);
			
			LogLevel.DEBUG(5,new Throwable(),"Tot_comm_with_tax: "+Tot_comm_with_tax);			
			
	        String Total_Reconciled_Amount_double_with_tax_round_final = Integer.toString(Total_Reconciled_Amount_double_with_tax_round_int);
			
			LogLevel.DEBUG(5,new Throwable(),"Tot_comm_with_tax: "+Tot_comm_with_tax);
			
			LogLevel.DEBUG(5,new Throwable(),"Key: "+key);
			
			String subheading ="";
			if(key.equals("battery"))
			{
				subheading="Battery Model";
			}
			else
			{
				subheading="Inverter Model";
			}

			SimpleDateFormat sdfString=new SimpleDateFormat("dd-MM-yyyy");
			Date date = new Date();
			String dates=sdfString.format(date); 
			//String dates="02-01-2020";
			
			Document document = new Document();
			PdfWriter pdf = PdfWriter.getInstance(document,new FileOutputStream(strTempTextFile));
			document.open();

			
			PdfContentByte cb = pdf.getDirectContent();
			cb.setLineWidth(0.5f);
			cb.moveTo(15, 765);
			cb.lineTo(585, 765);
			cb.stroke();

			PdfPTable table = new PdfPTable(arrPDFHeader.size());
            table.setWidthPercentage(100);  			
			
			PdfPTable tableI = new PdfPTable(arrPDFInverterHeader.size());
            tableI.setWidthPercentage(100);  			
						
			PdfPTable tableS = new PdfPTable(arrPDFServiceHeader.size());
            tableS.setWidthPercentage(100);  
			
			
			if(retailer.contains("Amaron-Pitstop"))
			{
				Image imghead = Image.getInstance("/home/ngit/tomcat/webapps/bookbattery/images/amaronexpresslogo_amaron.png");
				LogLevel.DEBUG(5,new Throwable(),"inside Amaron-Pitstop: "+imghead);
				imghead.setAbsolutePosition(50,780);
				imghead.scaleAbsolute(100, 30);
				document.add(imghead);	
					
				Paragraph paragraph= (new Paragraph("www.amaronexpress.com", FontFactory.getFont("Tahoma", 18, Font.NORMAL, new Color(0,206,209))));
				paragraph.setAlignment(Element.ALIGN_RIGHT);
				document.add(paragraph);
				
			}
			else
			{		
				System.setProperty("http.agnet","chrome");
				//URL url = new URL("https://www.bookbattery.com/bookbattery/images/batterywalelogo.png");
				//HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
				//BufferedImage image = ImageIO.
				Image imghead = Image.getInstance("/home/ngit/tomcat/webapps/bookbattery/images/batterywalelogo.png");
				LogLevel.DEBUG(5,new Throwable(),"inside else: "+imghead);
				imghead.setAbsolutePosition(50,780);
				imghead.scaleAbsolute(100, 30);
				document.add(imghead);	

				Paragraph paragraph= (new Paragraph("www.bookbattery.com", FontFactory.getFont("Tahoma", 18, Font.NORMAL, new Color(0,206,209))));
				paragraph.setAlignment(Element.ALIGN_RIGHT);
				document.add(paragraph);
			}
			

			Paragraph parag32 = (new Paragraph("Invoice No : "+verificationcode+"" ,FontFactory.getFont("Tahoma", 6, Font.UNDERLINE, new Color(0, 0, 0))));
			parag32.setAlignment(Element.ALIGN_CENTER);
			parag32.setSpacingBefore(16.2f);
			parag32.setSpacingAfter(16.2f);
			//parag32.setAlignment(Element.VALIGN_TOP);
			document.add(parag32);

			
			
			if(retailerid=="")
			{
				Paragraph parag1 = (new Paragraph("Retailer Name : "+retailer+" \r\n Location : "+Strlocorpin+" \r\n Invoice date : "+dates+"" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
				//Paragraph parag1 = (new Paragraph("Retailer Name : "+retailer+" \r\n GSTIN : 19ACMPH1325H1ZP \r\n Location : "+Strlocorpin+" \r\n Invoice date : "+dates+"" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
				
				
				parag1.setAlignment(Element.ALIGN_LEFT);
				parag1.setSpacingAfter(12.2f);
				document.add(parag1);
				LogLevel.DEBUG(5,new Throwable(),"Insideempty: "+retailer);
			}
			else
			{
				Paragraph parag1 = (new Paragraph("Retailer Name : "+retailer+" \r\n GSTIN : "+retailerid+" \r\n Location : "+Strlocorpin+" \r\n Invoice date : "+dates+"" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
				parag1.setAlignment(Element.ALIGN_LEFT);
				parag1.setSpacingAfter(12.2f);
				document.add(parag1);
				LogLevel.DEBUG(5,new Throwable(),"InsideNonempty: "+retailer);
			}
			


				
			table.setSpacingBefore(5);

			
			PdfPTable table1 = new PdfPTable(arrPDFHeader.size());
             table1.setWidthPercentage(100);  

			PdfPCell cell111 = new PdfPCell(new Paragraph("Invoice Period", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell111.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell211 = new PdfPCell(new Paragraph("From", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell211.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell311 = new PdfPCell(new Paragraph(""+strFromDate+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell311.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell41 = new PdfPCell(new Paragraph("TO", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell41.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell51 = new PdfPCell(new Paragraph(""+strToDate+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell51.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell61 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));			
			cell61.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell611 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));			
			cell611.setHorizontalAlignment(Element.ALIGN_CENTER);
			
            table1.addCell(cell111);
            table1.addCell(cell211);
            table1.addCell(cell311);
			table1.addCell(cell41);
			table1.addCell(cell51);
			table1.addCell(cell61);
			if(key.equals("battery"))
			{
				table1.addCell(cell61);
			}
			else
			{}
			table1.addCell(cell61);
			table1.addCell(cell611);
			document.add(table1);

			
			//######################## Table for Battery Order Details  ##################################### Start
			

			
			//######################## Table for Cash  ##################################### Start
			
			
			if(RetInoiceVector.size()==0 && RetInoiceVector_Debit_Card.size()==0 &&RetInoiceVector_Credit_Card.size()==0 && RetInoiceVector_Online.size()==0)
			{
				
			}
			else
			{
				Paragraph parag1121OT_C = (new Paragraph("Battery Order Details" ,FontFactory.getFont("Tahoma", 10, Font.BOLD, new Color(0, 0, 0))));
				parag1121OT_C.setAlignment(Element.ALIGN_CENTER);
				parag1121OT_C.setSpacingBefore(8.2f);
				document.add(parag1121OT_C);
			}

			if(RetInoiceVector.size()<=0)
			{
				
			}
			else
			{

				Paragraph parag1121_C = (new Paragraph("Payment by Cash" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
				parag1121_C.setAlignment(Element.ALIGN_LEFT);
				parag1121_C.setSpacingBefore(8.2f);
				document.add(parag1121_C);
				
				
				table.setSpacingBefore(5);
				PdfPCell cell21 = new PdfPCell(new Paragraph(""+subheading+"", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell21.setBackgroundColor(Color.lightGray ); 
				PdfPCell cell22 = new PdfPCell(new Paragraph("Quantity", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell22.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell22.setBackgroundColor(Color.lightGray ); 
				
				PdfPCell cell23 = new PdfPCell();
				PdfPCell cell24 = new PdfPCell();
				
		
				cell23 = new PdfPCell(new Paragraph("PreTax-SRP", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell23.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell23.setBackgroundColor(Color.lightGray ); 
				
				cell24 = new PdfPCell(new Paragraph("Dealer Price", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell24.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell24.setBackgroundColor(Color.lightGray ); 
				
				PdfPCell cell25 = new PdfPCell(new Paragraph("Commission", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell25.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell25.setBackgroundColor(Color.lightGray ); 
				PdfPCell cell27 = new PdfPCell(new Paragraph("Transaction Charge", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell27.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell27.setBackgroundColor(Color.lightGray ); 
				PdfPCell cell28 = new PdfPCell(new Paragraph("Battery Replacement", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell28.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell28.setBackgroundColor(Color.lightGray ); 
				
				//PdfPCell cell281 = new PdfPCell(new Paragraph("Delivery Charge", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				//cell281.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell281.setBackgroundColor(Color.lightGray ); 
				
				PdfPCell cell26 = new PdfPCell(new Paragraph("Total Comm", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell26.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell26.setBackgroundColor(Color.lightGray ); 
		
				table.addCell(cell21);
				table.addCell(cell22);
				table.addCell(cell23);
				table.addCell(cell24);
				table.addCell(cell25);
				table.addCell(cell27);
				if(key.equals("battery"))
				{
					table.addCell(cell28);
				}
				else
				{}
				//table.addCell(cell281);
				table.addCell(cell26);


				String serialno="0";
				Hashtable hashContent = new Hashtable();
				for (int j=0; j<RetInoiceVector.size(); j++)
				{
					hashContent   = (Hashtable)RetInoiceVector.get(j);
					int value = Integer.parseInt(serialno);
					LogLevel.DEBUG(5,new Throwable(),"value is : "+value);
					int nextValue = value + 1; // find the int value plus 1

					serialno= String.valueOf(nextValue) ;			
					LogLevel.DEBUG(4,new Throwable(),"serialno value"+serialno);
					for (int ik=0;ik < dbHeaders.size() ;ik++ )
					{
						String strHeader=(String)dbHeaders.get(ik);
						String strData=(hashContent.get(strHeader)!=null)?String.valueOf(hashContent.get(strHeader)):"-";
						if(strHeader.equals("0") || strHeader.equals(0))
						{
								strData=serialno;
						}
						Phrase phrase3 = new Phrase(strData, FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0, 0, 0)));
						PdfPCell cell1 = new PdfPCell(new Paragraph(phrase3));
						cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell1);
					}
				}
				document.add(table);
			}
			//######################## Table for Cash  ##################################### End
			
			//######################## Table for Debit Card  ##################################### Start
			
			if(RetInoiceVector_Debit_Card.size()<=0)
			{
				
			}
			else
			{
				Paragraph parag1121_DC = (new Paragraph("Payment by Debit Card - 1% of Customer Payment" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
				parag1121_DC.setAlignment(Element.ALIGN_LEFT);
				parag1121_DC.setSpacingBefore(8.2f);
				document.add(parag1121_DC);
				
				PdfPTable table_Debit_Card = new PdfPTable(arrPDFHeader.size());
				table_Debit_Card.setWidthPercentage(100); 
				table_Debit_Card.setSpacingBefore(5);			 
				 
				 PdfPCell cell21_DC = new PdfPCell(new Paragraph(""+subheading+"", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell21_DC.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell21_DC.setBackgroundColor(Color.lightGray ); 
				PdfPCell cell22_DC = new PdfPCell(new Paragraph("Quantity", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell22_DC.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell22_DC.setBackgroundColor(Color.lightGray ); 
								
				PdfPCell cell23_DC = new PdfPCell();
				PdfPCell cell24_DC = new PdfPCell();

				cell23_DC = new PdfPCell(new Paragraph("PreTax-SRP", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell23_DC.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell23_DC.setBackgroundColor(Color.lightGray ); 
				
				cell24_DC = new PdfPCell(new Paragraph("Dealer Price", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell24_DC.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell24_DC.setBackgroundColor(Color.lightGray );  
				
				PdfPCell cell25_DC = new PdfPCell(new Paragraph("Commission", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell25_DC.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell25_DC.setBackgroundColor(Color.lightGray ); 
				PdfPCell cell27_DC = new PdfPCell(new Paragraph("Transaction Charge", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell27_DC.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell27_DC.setBackgroundColor(Color.lightGray ); 
			
				PdfPCell cell28_DC = new PdfPCell(new Paragraph("Battery Replacement", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell28_DC.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell28_DC.setBackgroundColor(Color.lightGray ); 
				
				//PdfPCell cell281_DC  = new PdfPCell(new Paragraph("Delivery Charge", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				//cell281_DC.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell281_DC.setBackgroundColor(Color.lightGray ); 

				PdfPCell cell26_DC = new PdfPCell(new Paragraph("Total Comm", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell26_DC.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell26_DC.setBackgroundColor(Color.lightGray ); 
		
				table_Debit_Card.addCell(cell21_DC);
				table_Debit_Card.addCell(cell22_DC);
				table_Debit_Card.addCell(cell23_DC);
				table_Debit_Card.addCell(cell24_DC);
				table_Debit_Card.addCell(cell25_DC);
				table_Debit_Card.addCell(cell27_DC);
				
				if(key.equals("battery"))
				{
					table_Debit_Card.addCell(cell28_DC);
				}
				else
				{}
				//table_Debit_Card.addCell(cell281_DC);
				table_Debit_Card.addCell(cell26_DC);
				
				String serialno_DC="0";
				Hashtable hashContent_DC = new Hashtable();
				LogLevel.DEBUG(5,new Throwable(),"RetInoiceVector_Debit_Card.size(): "+RetInoiceVector_Debit_Card.size());

				for (int j=0; j<RetInoiceVector_Debit_Card.size(); j++)
				{
					hashContent_DC   = (Hashtable)RetInoiceVector_Debit_Card.get(j);
					int value = Integer.parseInt(serialno_DC);
					LogLevel.DEBUG(5,new Throwable(),"value is : "+value);
					int nextValue = value + 1; // find the int value plus 1

					serialno_DC= String.valueOf(nextValue) ;			
					LogLevel.DEBUG(4,new Throwable(),"Serialno value"+serialno_DC);
					for (int ik=0;ik < dbHeaders.size() ;ik++ )
					{
						String strHeader_DC=(String)dbHeaders.get(ik);
						String strData_DC=(hashContent_DC.get(strHeader_DC)!=null)?String.valueOf(hashContent_DC.get(strHeader_DC)):"-";
						if(strHeader_DC.equals("0") || strHeader_DC.equals(0))
						{
								strData_DC=serialno_DC;
						}
						Phrase phrase3_DC = new Phrase(strData_DC, FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0, 0, 0)));
						PdfPCell cell1_DC = new PdfPCell(new Paragraph(phrase3_DC));
						cell1_DC.setHorizontalAlignment(Element.ALIGN_CENTER);
						table_Debit_Card.addCell(cell1_DC);
					}
				}
				document.add(table_Debit_Card);
			}
			//######################## Table for Debit Card  ##################################### End's
			
			//######################## Table for Credit Card  ##################################### Start
			
			if(RetInoiceVector_Credit_Card.size()<=0)
			{
				
			}
			else
			{
				Paragraph parag1121_CC = (new Paragraph("Payment by Credit Card - 2% of Customer Payment" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
				parag1121_CC.setAlignment(Element.ALIGN_LEFT);
				parag1121_CC.setSpacingBefore(8.2f);
				document.add(parag1121_CC);
				
				PdfPTable table_Credit_Card = new PdfPTable(arrPDFHeader.size());
				table_Credit_Card.setWidthPercentage(100); 
				table_Credit_Card.setSpacingBefore(5);			 
				 
				 PdfPCell cell21_CC = new PdfPCell(new Paragraph(""+subheading+"", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell21_CC.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell21_CC.setBackgroundColor(Color.lightGray ); 
				PdfPCell cell22_CC = new PdfPCell(new Paragraph("Quantity", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell22_CC.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell22_CC.setBackgroundColor(Color.lightGray ); 
				
				PdfPCell cell23_CC = new PdfPCell();
				PdfPCell cell24_CC = new PdfPCell();
				

				cell23_CC = new PdfPCell(new Paragraph("PreTax-SRP", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell23_CC.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell23_CC.setBackgroundColor(Color.lightGray ); 
				
				cell24_CC = new PdfPCell(new Paragraph("Dealer Price", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell24_CC.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell24_CC.setBackgroundColor(Color.lightGray ); 

				
				PdfPCell cell25_CC = new PdfPCell(new Paragraph("Commission", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell25_CC.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell25_CC.setBackgroundColor(Color.lightGray ); 
				PdfPCell cell27_CC = new PdfPCell(new Paragraph("Transaction Charge", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell27_CC.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell27_CC.setBackgroundColor(Color.lightGray ); 

				PdfPCell cell28_CC = new PdfPCell(new Paragraph("Battery Replacement", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell28_CC.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell28_CC.setBackgroundColor(Color.lightGray ); 
				
				//PdfPCell cell281_CC  = new PdfPCell(new Paragraph("Delivery Charge", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				//cell281_CC.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell281_CC.setBackgroundColor(Color.lightGray ); 


				PdfPCell cell26_CC = new PdfPCell(new Paragraph("Total Comm", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell26_CC.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell26_CC.setBackgroundColor(Color.lightGray ); 
		
				table_Credit_Card.addCell(cell21_CC);
				table_Credit_Card.addCell(cell22_CC);
				table_Credit_Card.addCell(cell23_CC);
				table_Credit_Card.addCell(cell24_CC);
				table_Credit_Card.addCell(cell25_CC);
				table_Credit_Card.addCell(cell27_CC);
				if(key.equals("battery"))
				{
					table_Credit_Card.addCell(cell28_CC);
				}
				else
				{}
				//table_Credit_Card.addCell(cell281_CC);
				table_Credit_Card.addCell(cell26_CC);
				
				String serialno_CC="0";
				Hashtable hashContent_CC = new Hashtable();
				for (int j=0; j<RetInoiceVector_Credit_Card.size(); j++)
				{
					hashContent_CC   = (Hashtable)RetInoiceVector_Credit_Card.get(j);
					int value = Integer.parseInt(serialno_CC);
					LogLevel.DEBUG(5,new Throwable(),"value is : "+value);
					int nextValue = value + 1; // find the int value plus 1

					serialno_CC= String.valueOf(nextValue) ;			
					LogLevel.DEBUG(4,new Throwable(),"Serialno value"+serialno_CC);
					for (int ik=0;ik < dbHeaders.size() ;ik++ )
					{
						String strHeader_CC=(String)dbHeaders.get(ik);
						String strData_CC=(hashContent_CC.get(strHeader_CC)!=null)?String.valueOf(hashContent_CC.get(strHeader_CC)):"-";
						if(strHeader_CC.equals("0") || strHeader_CC.equals(0))
						{
								strData_CC=serialno_CC;
						}
						Phrase phrase3_CC = new Phrase(strData_CC, FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0, 0, 0)));
						PdfPCell cell1_CC = new PdfPCell(new Paragraph(phrase3_CC));
						cell1_CC.setHorizontalAlignment(Element.ALIGN_CENTER);
						table_Credit_Card.addCell(cell1_CC);
					}
				}
				document.add(table_Credit_Card);
			}
			//######################## Table for Credit Card  ##################################### End's
			
			//######################## Table for Online Trans  ##################################### Start
			
			if(RetInoiceVector_Online.size()<=0)
			{
				
			}
			else
			{
				Paragraph parag1121_OT = (new Paragraph("Online Payments" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
				parag1121_OT.setAlignment(Element.ALIGN_LEFT);
				parag1121_OT.setSpacingBefore(8.2f);
				document.add(parag1121_OT);
				
				PdfPTable table_Credit_Card = new PdfPTable(arrPDFHeader.size());
				table_Credit_Card.setWidthPercentage(100); 
				table_Credit_Card.setSpacingBefore(5);			 
				 
				 PdfPCell cell21_OT = new PdfPCell(new Paragraph(""+subheading+"", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell21_OT.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell21_OT.setBackgroundColor(Color.lightGray ); 
				PdfPCell cell22_OT = new PdfPCell(new Paragraph("Quantity", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell22_OT.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell22_OT.setBackgroundColor(Color.lightGray ); 
				
				
				PdfPCell cell23_OT = new PdfPCell();
				PdfPCell cell24_OT = new PdfPCell();
				

				cell23_OT = new PdfPCell(new Paragraph("PreTax-SRP", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell23_OT.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell23_OT.setBackgroundColor(Color.lightGray ); 
				
				cell24_OT = new PdfPCell(new Paragraph("Dealer Price", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell24_OT.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell24_OT.setBackgroundColor(Color.lightGray ); 

				PdfPCell cell25_OT = new PdfPCell(new Paragraph("Commission", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell25_OT.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell25_OT.setBackgroundColor(Color.lightGray ); 
				PdfPCell cell27_OT = new PdfPCell(new Paragraph("Transaction Charge", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell27_OT.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell27_OT.setBackgroundColor(Color.lightGray ); 
				PdfPCell cell28_OT = new PdfPCell(new Paragraph("Battery Replacement", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell28_OT.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell28_OT.setBackgroundColor(Color.lightGray ); 
				
				//PdfPCell cell281_OT  = new PdfPCell(new Paragraph("Delivery Charge", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				//cell281_OT.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cell281_OT.setBackgroundColor(Color.lightGray ); 
				
				
				PdfPCell cell26_OT = new PdfPCell(new Paragraph("Total Comm", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell26_OT.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell26_OT.setBackgroundColor(Color.lightGray ); 
		
				table_Credit_Card.addCell(cell21_OT);
				table_Credit_Card.addCell(cell22_OT);
				table_Credit_Card.addCell(cell23_OT);
				table_Credit_Card.addCell(cell24_OT);
				table_Credit_Card.addCell(cell25_OT);
				table_Credit_Card.addCell(cell27_OT);
				
				if(key.equals("battery"))
				{
					table_Credit_Card.addCell(cell28_OT);
				}
				else
				{}
				
				//table_Credit_Card.addCell(cell281_OT);
				table_Credit_Card.addCell(cell26_OT);
				
				String serialno_OT="0";
				Hashtable hashContent_OT = new Hashtable();
				for (int j=0; j<RetInoiceVector_Online.size(); j++)
				{
					hashContent_OT   = (Hashtable)RetInoiceVector_Online.get(j);
					int value = Integer.parseInt(serialno_OT);
					LogLevel.DEBUG(5,new Throwable(),"value is : "+value);
					int nextValue = value + 1; // find the int value plus 1

					serialno_OT= String.valueOf(nextValue) ;			
					LogLevel.DEBUG(4,new Throwable(),"Serialno value"+serialno_OT);
					for (int ik=0;ik < dbHeaders.size() ;ik++ )
					{
						String strHeader_OT=(String)dbHeaders.get(ik);
						String strData_OT=(hashContent_OT.get(strHeader_OT)!=null)?String.valueOf(hashContent_OT.get(strHeader_OT)):"-";
						if(strHeader_OT.equals("0") || strHeader_OT.equals(0))
						{
								strData_OT=serialno_OT;
						}
						Phrase phrase3_OT = new Phrase(strData_OT, FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0, 0, 0)));
						PdfPCell cell1_OT = new PdfPCell(new Paragraph(phrase3_OT));
						cell1_OT.setHorizontalAlignment(Element.ALIGN_CENTER);
						table_Credit_Card.addCell(cell1_OT);
					}
				}
				document.add(table_Credit_Card);
			}
			//######################## Table for Online Trans  ##################################### End's
			
			
			
			//######################## Table for Battery Order Details ends here ##################################### End's
			
			
		  //######################## Table for Inverter Order Details starts here ##################################### Start
			

			//######################## Table for Cash  ##################################### Start
			
			if(RetInverterInoiceVector.size()==0 && RetInverterInoiceVector_Debit_Card.size()==0 &&RetInverterInoiceVector_Credit_Card.size()==0 && RetInverterInoiceVector_Online.size()==0)
			{
				
			}
			else
			{
				Paragraph parag1121OT_CI = (new Paragraph("Inverter Order Details" ,FontFactory.getFont("Tahoma", 10, Font.BOLD, new Color(0, 0, 0))));
				parag1121OT_CI.setAlignment(Element.ALIGN_CENTER);
				parag1121OT_CI.setSpacingBefore(8.2f);
				document.add(parag1121OT_CI);
			}

			if(RetInverterInoiceVector.size()<=0)
			{
				
			}
			else
			{

				Paragraph parag1121_CI = (new Paragraph("Payment by Cash" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
				parag1121_CI.setAlignment(Element.ALIGN_LEFT);
				parag1121_CI.setSpacingBefore(8.2f);
				document.add(parag1121_CI);

				tableI.setSpacingBefore(5);
				PdfPCell cell21I = new PdfPCell(new Paragraph("Inveter model", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell21I.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell21I.setBackgroundColor(Color.lightGray ); 
				PdfPCell cell22I = new PdfPCell(new Paragraph("Quantity", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell22I.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell22I.setBackgroundColor(Color.lightGray ); 
				
				PdfPCell cell23I = new PdfPCell(new Paragraph("PreTax-SRP", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell23I.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell23I.setBackgroundColor(Color.lightGray ); 
				
				PdfPCell cell24I = new PdfPCell(new Paragraph("Dealer Price", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell24I.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell24I.setBackgroundColor(Color.lightGray ); 
				
				PdfPCell cell25I = new PdfPCell(new Paragraph("Commission", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell25I.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell25I.setBackgroundColor(Color.lightGray ); 
				
				PdfPCell cell27I = new PdfPCell(new Paragraph("Transaction Charge", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell27I.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell27I.setBackgroundColor(Color.lightGray ); 
				
				PdfPCell cell28I = new PdfPCell(new Paragraph("Battery Replacement", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell28I.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell28I.setBackgroundColor(Color.lightGray ); 
				
				PdfPCell cell26I = new PdfPCell(new Paragraph("Total Comm", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell26I.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell26I.setBackgroundColor(Color.lightGray ); 
		
				tableI.addCell(cell21I);
				tableI.addCell(cell22I);
				tableI.addCell(cell23I);
				tableI.addCell(cell24I);
				tableI.addCell(cell25I);
				tableI.addCell(cell27I);
				if(key.equals("battery"))
				{
					//tableI.addCell(cell28I);
				}
				else
				{}
				tableI.addCell(cell26I);


				String serialnoI="0";
				Hashtable hashContentInv = new Hashtable();
				for (int j=0; j<RetInverterInoiceVector.size(); j++)
				{
					hashContentInv   = (Hashtable)RetInverterInoiceVector.get(j);
					int valuei = Integer.parseInt(serialnoI);
					LogLevel.DEBUG(5,new Throwable(),"value is : "+valuei);
					int nextValuei = valuei + 1; // find the int value plus 1

					serialnoI= String.valueOf(nextValuei) ;			
					LogLevel.DEBUG(4,new Throwable(),"serialnoI value"+serialnoI);
					for (int ikI=0;ikI < dbInverterHeaders.size() ;ikI++ )
					{
						String strHeaderI=(String)dbInverterHeaders.get(ikI);
						String strDataI=(hashContentInv.get(strHeaderI)!=null)?String.valueOf(hashContentInv.get(strHeaderI)):"-";
						if(strHeaderI.equals("0") || strHeaderI.equals(0))
						{
								strDataI=serialnoI;
						}
						Phrase phrase3I = new Phrase(strDataI, FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0, 0, 0)));
						PdfPCell cell1I = new PdfPCell(new Paragraph(phrase3I));
						cell1I.setHorizontalAlignment(Element.ALIGN_CENTER);
						tableI.addCell(cell1I);
					}
				}
				document.add(tableI);
			}
			//######################## Table for Cash  ##################################### End
			
			//######################## Table for Debit Card  ##################################### Start
			
			if(RetInverterInoiceVector_Debit_Card.size()<=0)
			{
				
			}
			else
			{
				Paragraph parag1121_DCI = (new Paragraph("Payment by Debit Card - 1% of Customer Payment" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
				parag1121_DCI.setAlignment(Element.ALIGN_LEFT);
				parag1121_DCI.setSpacingBefore(8.2f);
				document.add(parag1121_DCI);
				
				PdfPTable table_Debit_CardI = new PdfPTable(arrPDFInverterHeader.size());			
				table_Debit_CardI.setWidthPercentage(100); 
				table_Debit_CardI.setSpacingBefore(5);			 
				 
				 PdfPCell cell21_DCI = new PdfPCell(new Paragraph("Inveter model", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell21_DCI.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell21_DCI.setBackgroundColor(Color.lightGray ); 
				PdfPCell cell22_DCI = new PdfPCell(new Paragraph("Quantity", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell22_DCI.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell22_DCI.setBackgroundColor(Color.lightGray ); 

				PdfPCell cell23_DCI = new PdfPCell(new Paragraph("PreTax-SRP", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell23_DCI.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell23_DCI.setBackgroundColor(Color.lightGray ); 
				
				PdfPCell cell24_DCI = new PdfPCell(new Paragraph("Dealer Price", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell24_DCI.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell24_DCI.setBackgroundColor(Color.lightGray );  
				
				PdfPCell cell25_DCI = new PdfPCell(new Paragraph("Commission", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell25_DCI.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell25_DCI.setBackgroundColor(Color.lightGray ); 
				
				PdfPCell cell27_DCI = new PdfPCell(new Paragraph("Transaction Charge", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell27_DCI.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell27_DCI.setBackgroundColor(Color.lightGray ); 
			
				PdfPCell cell28_DCI = new PdfPCell(new Paragraph("Battery Replacement", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell28_DCI.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell28_DCI.setBackgroundColor(Color.lightGray ); 

				PdfPCell cell26_DCI = new PdfPCell(new Paragraph("Total Comm", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell26_DCI.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell26_DCI.setBackgroundColor(Color.lightGray ); 
		
				table_Debit_CardI.addCell(cell21_DCI);
				table_Debit_CardI.addCell(cell22_DCI);
				table_Debit_CardI.addCell(cell23_DCI);
				table_Debit_CardI.addCell(cell24_DCI);
				table_Debit_CardI.addCell(cell25_DCI);
				table_Debit_CardI.addCell(cell27_DCI);
				
				if(key.equals("battery"))
				{
					//table_Debit_CardI.addCell(cell28_DCI);
				}
				else
				{}
				table_Debit_CardI.addCell(cell26_DCI);
				
				String serialno_DCI="0";
				Hashtable hashContent_DCI = new Hashtable();
				LogLevel.DEBUG(5,new Throwable(),"RetInverterInoiceVector_Debit_Card.size(): "+RetInverterInoiceVector_Debit_Card.size());


				for (int j=0; j<RetInverterInoiceVector_Debit_Card.size(); j++)
				{
					hashContent_DCI   = (Hashtable)RetInverterInoiceVector_Debit_Card.get(j);
					int valueI = Integer.parseInt(serialno_DCI);
					LogLevel.DEBUG(5,new Throwable(),"value is : "+valueI);
					int nextValueI = valueI + 1; // find the int value plus 1

					serialno_DCI= String.valueOf(nextValueI) ;			
					LogLevel.DEBUG(4,new Throwable(),"Serialno valueI"+serialno_DCI);
					for (int ik=0;ik < dbInverterHeaders.size() ;ik++ )
					{
						String strHeader_DCI=(String)dbInverterHeaders.get(ik);
						String strData_DCI=(hashContent_DCI.get(strHeader_DCI)!=null)?String.valueOf(hashContent_DCI.get(strHeader_DCI)):"-";
						if(strHeader_DCI.equals("0") || strHeader_DCI.equals(0))
						{
								strData_DCI=serialno_DCI;
						}
						Phrase phrase3_DCI = new Phrase(strData_DCI, FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0, 0, 0)));
						PdfPCell cell1_DCI = new PdfPCell(new Paragraph(phrase3_DCI));
						cell1_DCI.setHorizontalAlignment(Element.ALIGN_CENTER);
						table_Debit_CardI.addCell(cell1_DCI);
					}
				}
				document.add(table_Debit_CardI);
			}
			//######################## Table for Debit Card  ##################################### End's
			
			//######################## Table for Credit Card  ##################################### Start
			
			if(RetInverterInoiceVector_Credit_Card.size()<=0)
			{
				
			}
			else
			{
				Paragraph parag1121_CCI = (new Paragraph("Payment by Credit Card - 2% of Customer Payment" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
				parag1121_CCI.setAlignment(Element.ALIGN_LEFT);
				parag1121_CCI.setSpacingBefore(8.2f);
				document.add(parag1121_CCI);
				
				PdfPTable table_Credit_CardI = new PdfPTable(arrPDFInverterHeader.size());
				table_Credit_CardI.setWidthPercentage(100); 
				table_Credit_CardI.setSpacingBefore(5);			 
				 
				 PdfPCell cell21_CCI = new PdfPCell(new Paragraph("Inveter model", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell21_CCI.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell21_CCI.setBackgroundColor(Color.lightGray ); 
				
				PdfPCell cell22_CCI = new PdfPCell(new Paragraph("Quantity", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell22_CCI.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell22_CCI.setBackgroundColor(Color.lightGray ); 
				

				PdfPCell cell23_CCI = new PdfPCell(new Paragraph("PreTax-SRP", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell23_CCI.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell23_CCI.setBackgroundColor(Color.lightGray ); 
				
				PdfPCell cell24_CCI = new PdfPCell(new Paragraph("Dealer Price", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell24_CCI.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell24_CCI.setBackgroundColor(Color.lightGray ); 

				
				PdfPCell cell25_CCI = new PdfPCell(new Paragraph("Commission", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell25_CCI.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell25_CCI.setBackgroundColor(Color.lightGray ); 
				
				PdfPCell cell27_CCI = new PdfPCell(new Paragraph("Transaction Charge", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell27_CCI.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell27_CCI.setBackgroundColor(Color.lightGray ); 

				PdfPCell cell28_CCI = new PdfPCell(new Paragraph("Battery Replacement", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell28_CCI.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell28_CCI.setBackgroundColor(Color.lightGray ); 

				PdfPCell cell26_CCI = new PdfPCell(new Paragraph("Total Comm", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell26_CCI.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell26_CCI.setBackgroundColor(Color.lightGray ); 
		
				table_Credit_CardI.addCell(cell21_CCI);
				table_Credit_CardI.addCell(cell22_CCI);
				table_Credit_CardI.addCell(cell23_CCI);
				table_Credit_CardI.addCell(cell24_CCI);
				table_Credit_CardI.addCell(cell25_CCI);
				table_Credit_CardI.addCell(cell27_CCI);
				if(key.equals("battery"))
				{
					//table_Credit_CardI.addCell(cell28_CCI);
				}
				else
				{}
				table_Credit_CardI.addCell(cell26_CCI);
				

				String serialno_CCI="0";
				Hashtable hashContent_CCI = new Hashtable();
				for (int j=0; j<RetInverterInoiceVector_Credit_Card.size(); j++)
				{
					hashContent_CCI   = (Hashtable)RetInverterInoiceVector_Credit_Card.get(j);
					int valueI = Integer.parseInt(serialno_CCI);
					LogLevel.DEBUG(5,new Throwable(),"value is : "+valueI);
					int nextValueI = valueI + 1; // find the int value plus 1

					serialno_CCI= String.valueOf(nextValueI) ;			
					LogLevel.DEBUG(4,new Throwable(),"Serialno value"+serialno_CCI);
					for (int ik=0;ik < dbInverterHeaders.size() ;ik++ )
					{
						String strHeader_CCI=(String)dbInverterHeaders.get(ik);
						String strData_CCI=(hashContent_CCI.get(strHeader_CCI)!=null)?String.valueOf(hashContent_CCI.get(strHeader_CCI)):"-";
						if(strHeader_CCI.equals("0") || strHeader_CCI.equals(0))
						{
								strData_CCI=serialno_CCI;
						}
						Phrase phrase3_CCI = new Phrase(strData_CCI, FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0, 0, 0)));
						PdfPCell cell1_CCI = new PdfPCell(new Paragraph(phrase3_CCI));
						cell1_CCI.setHorizontalAlignment(Element.ALIGN_CENTER);
						table_Credit_CardI.addCell(cell1_CCI);
					}
				}
				document.add(table_Credit_CardI);
			}
			//######################## Table for Credit Card  ##################################### End's
			
			//######################## Table for Online Trans  ##################################### Start
			
			if(RetInverterInoiceVector_Online.size()<=0)
			{
				
			}
			else
			{
				Paragraph parag1121_OTI = (new Paragraph("Online Payments" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
				parag1121_OTI.setAlignment(Element.ALIGN_LEFT);
				parag1121_OTI.setSpacingBefore(8.2f);
				document.add(parag1121_OTI);
				
				PdfPTable table_Credit_CardI = new PdfPTable(arrPDFInverterHeader.size());
				table_Credit_CardI.setWidthPercentage(100); 
				table_Credit_CardI.setSpacingBefore(5);			 
				 
				 PdfPCell cell21_OTI = new PdfPCell(new Paragraph("Inveter model", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell21_OTI.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell21_OTI.setBackgroundColor(Color.lightGray ); 
				PdfPCell cell22_OTI = new PdfPCell(new Paragraph("Quantity", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell22_OTI.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell22_OTI.setBackgroundColor(Color.lightGray ); 
				

				PdfPCell cell23_OTI = new PdfPCell(new Paragraph("PreTax-SRP", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell23_OTI.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell23_OTI.setBackgroundColor(Color.lightGray ); 
				
				PdfPCell cell24_OTI = new PdfPCell(new Paragraph("Dealer Price", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell24_OTI.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell24_OTI.setBackgroundColor(Color.lightGray ); 

				PdfPCell cell25_OTI = new PdfPCell(new Paragraph("Commission", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell25_OTI.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell25_OTI.setBackgroundColor(Color.lightGray ); 
				
				PdfPCell cell27_OTI = new PdfPCell(new Paragraph("Transaction Charge", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell27_OTI.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell27_OTI.setBackgroundColor(Color.lightGray ); 
				
				PdfPCell cell28_OTI = new PdfPCell(new Paragraph("Battery Replacement", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell28_OTI.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell28_OTI.setBackgroundColor(Color.lightGray ); 
				PdfPCell cell26_OTI = new PdfPCell(new Paragraph("Total Comm", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell26_OTI.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell26_OTI.setBackgroundColor(Color.lightGray ); 
		
				table_Credit_CardI.addCell(cell21_OTI);
				table_Credit_CardI.addCell(cell22_OTI);
				table_Credit_CardI.addCell(cell23_OTI);
				table_Credit_CardI.addCell(cell24_OTI);
				table_Credit_CardI.addCell(cell25_OTI);
				table_Credit_CardI.addCell(cell27_OTI);
				
				if(key.equals("battery"))
				{
					//table_Credit_CardI.addCell(cell28_OTI);
				}
				else
				{}
				
				table_Credit_CardI.addCell(cell26_OTI);
				
				
				String serialno_OTI="0";
				Hashtable hashContent_OTI = new Hashtable();
				for (int j=0; j<RetInverterInoiceVector_Online.size(); j++)
				{
					hashContent_OTI   = (Hashtable)RetInverterInoiceVector_Online.get(j);
					int valueI = Integer.parseInt(serialno_OTI);
					LogLevel.DEBUG(5,new Throwable(),"value is : "+valueI);
					int nextValueI = valueI + 1; // find the int value plus 1

					serialno_OTI= String.valueOf(nextValueI) ;			
					LogLevel.DEBUG(4,new Throwable(),"Serialno value"+serialno_OTI);
					for (int ik=0;ik < dbInverterHeaders.size() ;ik++ )
					{
						String strHeader_OTI=(String)dbInverterHeaders.get(ik);
						String strData_OTI=(hashContent_OTI.get(strHeader_OTI)!=null)?String.valueOf(hashContent_OTI.get(strHeader_OTI)):"-";
						if(strHeader_OTI.equals("0") || strHeader_OTI.equals(0))
						{
								strData_OTI=serialno_OTI;
						}
						Phrase phrase3_OTI = new Phrase(strData_OTI, FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0, 0, 0)));
						PdfPCell cell1_OTI = new PdfPCell(new Paragraph(phrase3_OTI));
						cell1_OTI.setHorizontalAlignment(Element.ALIGN_CENTER);
						table_Credit_CardI.addCell(cell1_OTI);
					}
				}
				document.add(table_Credit_CardI);
			}
			//######################## Table for Online Trans  ##################################### End's
			
			
			
	  //######################## Table for Inverter Order Details ends here ##################################### End's	
			
			
	//######################## Table for Service Order Details Starts here   ##################################### Start
			
		//######################## Table for Cash  ##################################### Start

			if(RetServiceInoiceVector.size()==0 && RetServiceInoiceVector_Debit_Card.size()==0 &&RetServiceInoiceVector_Credit_Card.size()==0 && RetServiceInoiceVector_Online.size()==0)
			{
				
			}
			else
			{
				Paragraph parag1121OT_CS = (new Paragraph("Service Order Details" ,FontFactory.getFont("Tahoma", 10, Font.BOLD, new Color(0, 0, 0))));
				parag1121OT_CS.setAlignment(Element.ALIGN_CENTER);
				parag1121OT_CS.setSpacingBefore(2.2f);
				document.add(parag1121OT_CS);	
			}


		if(RetServiceInoiceVector.size()<=0)
			{
				
			}
			else
			{

				Paragraph parag1121_CS = (new Paragraph("Payment by Cash" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL,new Color(0, 0, 0))));
				parag1121_CS.setAlignment(Element.ALIGN_LEFT);
				parag1121_CS.setSpacingBefore(8.2f);
				document.add(parag1121_CS);
								
				tableS.setSpacingBefore(5);
				PdfPCell cell21S = new PdfPCell(new Paragraph("Services Type", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell21S.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell21S.setBackgroundColor(Color.lightGray ); 
				PdfPCell cell22S = new PdfPCell(new Paragraph("Battery Type", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell22S.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell22S.setBackgroundColor(Color.lightGray ); 
				
				PdfPCell cell23S = new PdfPCell(new Paragraph("SRP", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell23S.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell23S.setBackgroundColor(Color.lightGray ); 				
				
				PdfPCell cell25S = new PdfPCell(new Paragraph("Commission", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell25S.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell25S.setBackgroundColor(Color.lightGray ); 
								
				PdfPCell cell26S = new PdfPCell(new Paragraph("Total Comm", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell26S.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell26S.setBackgroundColor(Color.lightGray ); 
		
				tableS.addCell(cell21S);
				tableS.addCell(cell22S);
				tableS.addCell(cell23S);
				tableS.addCell(cell25S);
				if(key.equals("battery"))
				{
					//tableS.addCell(cell28S);
				}
				else
				{}
				tableS.addCell(cell26S);


				String serialnoS="0";
				Hashtable hashContentS = new Hashtable();
				for (int j=0; j<RetServiceInoiceVector.size(); j++)
				{
					hashContentS   = (Hashtable)RetServiceInoiceVector.get(j);
					int value = Integer.parseInt(serialnoS);
					LogLevel.DEBUG(5,new Throwable(),"value is : "+value);
					int nextValue = value + 1; // find the int value plus 1

					serialnoS= String.valueOf(nextValue) ;			
					LogLevel.DEBUG(4,new Throwable(),"serialnoS value"+serialnoS);
					for (int ik=0;ik < dbServiceHeaders.size() ;ik++ )
					{
						String strHeader=(String)dbServiceHeaders.get(ik);
						String strData=(hashContentS.get(strHeader)!=null)?String.valueOf(hashContentS.get(strHeader)):"-";
						if(strHeader.equals("0") || strHeader.equals(0))
						{
								strData=serialnoS;
						}
						Phrase phrase3S = new Phrase(strData, FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0, 0, 0)));
						PdfPCell cell1S = new PdfPCell(new Paragraph(phrase3S));
						cell1S.setHorizontalAlignment(Element.ALIGN_CENTER);
						tableS.addCell(cell1S);
					}
				}
				document.add(tableS);
			}
			//######################## Table for Cash  ##################################### End
			
			//######################## Table for Debit Card  ##################################### Start
			
			if(RetServiceInoiceVector_Debit_Card.size()<=0)
			{
				
			}
			else
			{
				Paragraph parag1121_DCS = (new Paragraph("Payment by Debit Card - 1% of Customer Payment" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
				parag1121_DCS.setAlignment(Element.ALIGN_LEFT);
				parag1121_DCS.setSpacingBefore(8.2f);
				document.add(parag1121_DCS);
				
				PdfPTable table_Debit_CardS = new PdfPTable(arrPDFServiceHeader.size());
				table_Debit_CardS.setWidthPercentage(100); 
				table_Debit_CardS.setSpacingBefore(5);			 
				 
				 PdfPCell cell21_DCS = new PdfPCell(new Paragraph("Services Type", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell21_DCS.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell21_DCS.setBackgroundColor(Color.lightGray ); 
				
				PdfPCell cell22_DCS = new PdfPCell(new Paragraph("Battery Type", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell22_DCS.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell22_DCS.setBackgroundColor(Color.lightGray ); 
								
				PdfPCell cell23_DCS = new PdfPCell(new Paragraph("SRP", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell23_DCS.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell23_DCS.setBackgroundColor(Color.lightGray ); 
								
				PdfPCell cell25_DCS = new PdfPCell(new Paragraph("Commission", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell25_DCS.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell25_DCS.setBackgroundColor(Color.lightGray ); 
								
				PdfPCell cell26_DCS = new PdfPCell(new Paragraph("Total Comm", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell26_DCS.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell26_DCS.setBackgroundColor(Color.lightGray ); 
		
				table_Debit_CardS.addCell(cell21_DCS);
				table_Debit_CardS.addCell(cell22_DCS);
				table_Debit_CardS.addCell(cell23_DCS);
				table_Debit_CardS.addCell(cell25_DCS);
				
				if(key.equals("battery"))
				{
					//table_Debit_CardS.addCell(cell28_DCS);
				}
				else
				{}
				table_Debit_CardS.addCell(cell26_DCS);
				
				String serialno_DCS="0";
				Hashtable hashContent_DCS = new Hashtable();
				LogLevel.DEBUG(5,new Throwable(),"RetServiceInoiceVector_Debit_Card.size(): "+RetServiceInoiceVector_Debit_Card.size());

				for (int j=0; j<RetServiceInoiceVector_Debit_Card.size(); j++)
				{
					hashContent_DCS   = (Hashtable)RetServiceInoiceVector_Debit_Card.get(j);
					int value = Integer.parseInt(serialno_DCS);
					LogLevel.DEBUG(5,new Throwable(),"value is : "+value);
					int nextValue = value + 1; // find the int value plus 1

					serialno_DCS= String.valueOf(nextValue) ;			
					LogLevel.DEBUG(4,new Throwable(),"Serialno value"+serialno_DCS);
					for (int ik=0;ik < dbServiceHeaders.size() ;ik++ )
					{
						String strHeader_DCS=(String)dbServiceHeaders.get(ik);
						String strData_DCS=(hashContent_DCS.get(strHeader_DCS)!=null)?String.valueOf(hashContent_DCS.get(strHeader_DCS)):"-";
						if(strHeader_DCS.equals("0") || strHeader_DCS.equals(0))
						{
								strData_DCS=serialno_DCS;
						}
						Phrase phrase3_DCS = new Phrase(strData_DCS, FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0, 0, 0)));
						PdfPCell cell1_DCS = new PdfPCell(new Paragraph(phrase3_DCS));
						cell1_DCS.setHorizontalAlignment(Element.ALIGN_CENTER);
						table_Debit_CardS.addCell(cell1_DCS);
					}
				}
				document.add(table_Debit_CardS);
			}
			//######################## Table for Debit Card  ##################################### End's
			
			//######################## Table for Credit Card  ##################################### Start
						

			
			if(RetServiceInoiceVector_Credit_Card.size()<=0)
			{
				
			}
			else
			{
				Paragraph parag1121_CCS = (new Paragraph("Payment by Credit Card - 2% of Customer Payment" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
				parag1121_CCS.setAlignment(Element.ALIGN_LEFT);
				parag1121_CCS.setSpacingBefore(8.2f);
				document.add(parag1121_CCS);
				
				PdfPTable table_Credit_CardS = new PdfPTable(arrPDFServiceHeader.size());
				table_Credit_CardS.setWidthPercentage(100); 
				table_Credit_CardS.setSpacingBefore(5);			 
				 
				PdfPCell cell21_CCS = new PdfPCell(new Paragraph("Services Type", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell21_CCS.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell21_CCS.setBackgroundColor(Color.lightGray ); 
				
				PdfPCell cell22_CCS = new PdfPCell(new Paragraph("Battery Type", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell22_CCS.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell22_CCS.setBackgroundColor(Color.lightGray ); 
				
				
				PdfPCell cell23_CCS = new PdfPCell(new Paragraph("SRP", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell23_CCS.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell23_CCS.setBackgroundColor(Color.lightGray ); 
				
				PdfPCell cell25_CCS = new PdfPCell(new Paragraph("Commission", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell25_CCS.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell25_CCS.setBackgroundColor(Color.lightGray ); 

				PdfPCell cell26_CCS = new PdfPCell(new Paragraph("Total Comm", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell26_CCS.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell26_CCS.setBackgroundColor(Color.lightGray ); 
		
				table_Credit_CardS.addCell(cell21_CCS);
				table_Credit_CardS.addCell(cell22_CCS);
				table_Credit_CardS.addCell(cell23_CCS);
				table_Credit_CardS.addCell(cell25_CCS);
				if(key.equals("battery"))
				{
					//table_Credit_Card.addCell(cell28_CCS);
				}
				else
				{}
				table_Credit_CardS.addCell(cell26_CCS);
				
				String serialno_CCS="0";
				Hashtable hashContent_CCS = new Hashtable();
				for (int j=0; j<RetServiceInoiceVector_Credit_Card.size(); j++)
				{
					hashContent_CCS   = (Hashtable)RetServiceInoiceVector_Credit_Card.get(j);
					int value = Integer.parseInt(serialno_CCS);
					LogLevel.DEBUG(5,new Throwable(),"value is : "+value);
					int nextValue = value + 1; // find the int value plus 1

					serialno_CCS= String.valueOf(nextValue) ;			
					LogLevel.DEBUG(4,new Throwable(),"Serialno value"+serialno_CCS);
					for (int ik=0;ik < dbServiceHeaders.size() ;ik++ )
					{
						String strHeader_CCS=(String)dbServiceHeaders.get(ik);
						String strData_CCS=(hashContent_CCS.get(strHeader_CCS)!=null)?String.valueOf(hashContent_CCS.get(strHeader_CCS)):"-";
						if(strHeader_CCS.equals("0") || strHeader_CCS.equals(0))
						{
								strData_CCS=serialno_CCS;
						}
						Phrase phrase3_CCS = new Phrase(strData_CCS, FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0, 0, 0)));
						PdfPCell cell1_CCS = new PdfPCell(new Paragraph(phrase3_CCS));
						cell1_CCS.setHorizontalAlignment(Element.ALIGN_CENTER);
						table_Credit_CardS.addCell(cell1_CCS);
					}
				}
				document.add(table_Credit_CardS);
			}
			//######################## Table for Credit Card  ##################################### End's
			
			//######################## Table for Online Trans  ##################################### Start

			
			if(RetServiceInoiceVector_Online.size()<=0)
			{
				
			}
			else
			{
				Paragraph parag1121_OTS = (new Paragraph("Online Payments" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
				parag1121_OTS.setAlignment(Element.ALIGN_LEFT);
				parag1121_OTS.setSpacingBefore(8.2f);
				document.add(parag1121_OTS);
				
				PdfPTable table_Credit_CardS = new PdfPTable(arrPDFServiceHeader.size());
				table_Credit_CardS.setWidthPercentage(100); 
				table_Credit_CardS.setSpacingBefore(5);			 
				 
				 PdfPCell cell21_OTS = new PdfPCell(new Paragraph("Services Type", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell21_OTS.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell21_OTS.setBackgroundColor(Color.lightGray ); 
				PdfPCell cell22_OTS = new PdfPCell(new Paragraph("Battery Type", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell22_OTS.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell22_OTS.setBackgroundColor(Color.lightGray ); 
				
				PdfPCell cell23_OTS = new PdfPCell(new Paragraph("SRP", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell23_OTS.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell23_OTS.setBackgroundColor(Color.lightGray ); 
				
				PdfPCell cell25_OTS = new PdfPCell(new Paragraph("Commission", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell25_OTS.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell25_OTS.setBackgroundColor(Color.lightGray ); 
				
				PdfPCell cell26_OTS = new PdfPCell(new Paragraph("Total Comm", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
				cell26_OTS.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell26_OTS.setBackgroundColor(Color.lightGray ); 
		
				table_Credit_CardS.addCell(cell21_OTS);
				table_Credit_CardS.addCell(cell22_OTS);
				table_Credit_CardS.addCell(cell23_OTS);
				table_Credit_CardS.addCell(cell25_OTS);
				
				if(key.equals("battery"))
				{
					//table_Credit_CardS.addCell(cell28_OTS);
				}
				else
				{}
				
				table_Credit_CardS.addCell(cell26_OTS);
				
				String serialno_OTS="0";
				Hashtable hashContent_OTS = new Hashtable();
				for (int j=0; j<RetServiceInoiceVector_Online.size(); j++)
				{
					hashContent_OTS   = (Hashtable)RetServiceInoiceVector_Online.get(j);
					int value = Integer.parseInt(serialno_OTS);
					LogLevel.DEBUG(5,new Throwable(),"value is : "+value);
					int nextValue = value + 1; // find the int value plus 1

					serialno_OTS= String.valueOf(nextValue) ;			
					LogLevel.DEBUG(4,new Throwable(),"Serialno value"+serialno_OTS);
					for (int ik=0;ik < dbServiceHeaders.size() ;ik++ )
					{
						String strHeader_OTS=(String)dbServiceHeaders.get(ik);
						String strData_OTS=(hashContent_OTS.get(strHeader_OTS)!=null)?String.valueOf(hashContent_OTS.get(strHeader_OTS)):"-";
						if(strHeader_OTS.equals("0") || strHeader_OTS.equals(0))
						{
								strData_OTS=serialno_OTS;
						}
						Phrase phrase3_OTS = new Phrase(strData_OTS, FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0, 0, 0)));
						PdfPCell cell1_OTS = new PdfPCell(new Paragraph(phrase3_OTS));
						cell1_OTS.setHorizontalAlignment(Element.ALIGN_CENTER);
						table_Credit_CardS.addCell(cell1_OTS);
					}
				}
				document.add(table_Credit_CardS);
			}
			//######################## Table for Online Trans  ##################################### End's
			
			
			
			//######################## Table for Service Order Details ##################################### End's	

			
			PdfPTable table_End = new PdfPTable(arrPDFHeader.size());
            table_End.setWidthPercentage(100); 
			table_End.setSpacingBefore(5);	
			
			PdfPCell cell511 = new PdfPCell(new Paragraph("Total Commission Amount", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell511.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell511.setBackgroundColor(Color.lightGray ); 
			PdfPCell cell52 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell52.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell53 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell53.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell54 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell54.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell55 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell55.setHorizontalAlignment(Element.ALIGN_CENTER);		
			//PdfPCell cell551 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new //Color(0,0,0))));
			//cell551.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell56 = new PdfPCell(new Paragraph(""+strgrandtot+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell56.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell56.setBackgroundColor(Color.lightGray ); 
			
            table_End.addCell(cell511);
            table_End.addCell(cell52);
            table_End.addCell(cell53);
			table_End.addCell(cell54);
			table_End.addCell(cell54);
			
			if(key.equals("battery"))
			{
				table_End.addCell(cell54);
			}
			else
			{}
			table_End.addCell(cell55);
			//table_End.addCell(cell551);
			table_End.addCell(cell56);

	//***********Code added by bharath for GST ***********/ 
		if(Strlocorpin.equals("Bangalore"))
		{
			PdfPCell cell41113_OP = new PdfPCell(new Paragraph("CGST-9%", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell41113_OP.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell41113_OP.setBackgroundColor(Color.lightGray ); 
			PdfPCell cell4213_OP = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell4213_OP.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell4313_OP = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell4313_OP.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell4413_OP = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell4413_OP.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell4513_OP = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell4513_OP.setHorizontalAlignment(Element.ALIGN_CENTER);
			//PdfPCell cell45131_OP = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			//cell45131_OP.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell4613_OP = new PdfPCell(new Paragraph(""+Cgststr+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell4613_OP.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell4613_OP.setBackgroundColor(Color.lightGray ); 

            table_End.addCell(cell41113_OP);
            table_End.addCell(cell4213_OP);
            table_End.addCell(cell4313_OP);
			table_End.addCell(cell4413_OP);
			table_End.addCell(cell4413_OP);
			if(key.equals("battery"))
			{
				table_End.addCell(cell4413_OP);
			}
			else
			{}
			table_End.addCell(cell4513_OP);
			//table_End.addCell(cell45131_OP);
			table_End.addCell(cell4613_OP);
			
			PdfPCell cell41114_OP = new PdfPCell(new Paragraph("SGST-9%", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell41114_OP.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell41114_OP.setBackgroundColor(Color.lightGray ); 
			PdfPCell cell4214_OP = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell4214_OP.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell4314_OP = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell4314_OP.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell4414_OP = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell4414_OP.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell4514_OP = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell4514_OP.setHorizontalAlignment(Element.ALIGN_CENTER);
			//PdfPCell cell4514_OP1 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			//cell4514_OP1.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell4614_OP = new PdfPCell(new Paragraph(""+Sgststr+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell4614_OP.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell4614_OP.setBackgroundColor(Color.lightGray ); 
			
            table_End.addCell(cell41114_OP);
            table_End.addCell(cell4214_OP);
            table_End.addCell(cell4314_OP);
			table_End.addCell(cell4414_OP);
			table_End.addCell(cell4414_OP);
			if(key.equals("battery"))
			{
				table_End.addCell(cell4414_OP);
			}
			else
			{}
			//table_End.addCell(cell4514_OP1);
			table_End.addCell(cell4514_OP);
			table_End.addCell(cell4614_OP);
		}
		else
		{
			PdfPCell cell41115_OP = new PdfPCell(new Paragraph("IGST-18%", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell41115_OP.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell41115_OP.setBackgroundColor(Color.lightGray ); 
			PdfPCell cell4215_OP = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell4215_OP.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell4315_OP = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell4315_OP.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell4415_OP = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell4415_OP.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell4515_OP = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell4515_OP.setHorizontalAlignment(Element.ALIGN_CENTER);
			//PdfPCell cell45151_OP = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			//cell45151_OP.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell4615_OP = new PdfPCell(new Paragraph(""+Totgststrr+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell4615_OP.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell4615_OP.setBackgroundColor(Color.lightGray ); 
			

            table_End.addCell(cell41115_OP);
            table_End.addCell(cell4215_OP);
            table_End.addCell(cell4315_OP);
			table_End.addCell(cell4415_OP);
			table_End.addCell(cell4415_OP);
			if(key.equals("battery"))
			{
				table_End.addCell(cell4415_OP);
			}
			else
			{}
			table_End.addCell(cell4515_OP);
			//table_End.addCell(cell45151_OP);
			table_End.addCell(cell4615_OP);  
		}

		//********Code added by bharath for GST *********/ 
		
			PdfPCell cell5119 = new PdfPCell(new Paragraph("Total Commission Amount with Tax", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
            cell5119.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell5119.setBackgroundColor(Color.lightGray ); 
			PdfPCell cell529 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell529.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell539 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell539.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell549 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell549.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell559 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell559.setHorizontalAlignment(Element.ALIGN_CENTER);
			//PdfPCell cell5591 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			//cell5591.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell569 = new PdfPCell(new Paragraph(""+Tot_comm_with_tax+"", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
			cell569.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell569.setBackgroundColor(Color.lightGray ); 
			

            table_End.addCell(cell5119);
            table_End.addCell(cell529);
            table_End.addCell(cell539);
			table_End.addCell(cell549);
			table_End.addCell(cell549);
			
			if(key.equals("battery"))
			{
				table_End.addCell(cell549);
			}
			else
			{}
		
			table_End.addCell(cell559);
			//table_End.addCell(cell5591);
			table_End.addCell(cell569);
		
			PdfPCell cell4111_OP = new PdfPCell(new Paragraph("Online Payment", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
            cell4111_OP.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell4111_OP.setBackgroundColor(Color.lightGray ); 
			PdfPCell cell421_OP = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell421_OP.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell431_OP = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell431_OP.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell441_OP = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell441_OP.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell451_OP = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell451_OP.setHorizontalAlignment(Element.ALIGN_CENTER);
			//PdfPCell cell4511_OP = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			//cell4511_OP.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell461_OP = new PdfPCell(new Paragraph(""+strgrandtot_Online_Payment+"", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
			cell461_OP.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell461_OP.setBackgroundColor(Color.lightGray ); 
			
            table_End.addCell(cell4111_OP);
            table_End.addCell(cell421_OP);
            table_End.addCell(cell431_OP);
			table_End.addCell(cell441_OP);
			table_End.addCell(cell441_OP);
			if(key.equals("battery"))
			{
				table_End.addCell(cell441_OP);
			}
			else
			{}
			table_End.addCell(cell451_OP);
			//table_End.addCell(cell4511_OP);
			table_End.addCell(cell461_OP);
		
			PdfPCell cell3111 = new PdfPCell(new Paragraph("Total Reconciled Amount", FontFactory.getFont("Tahoma", 10, Font.BOLD, new Color(0,0,0))));
            cell3111.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell3111.setBackgroundColor(Color.lightGray );
			PdfPCell cell321 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell321.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell331 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell331.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell341 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell341.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell351 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell351.setHorizontalAlignment(Element.ALIGN_CENTER);
			//PdfPCell cell3511 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			//cell3511.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell361 = new PdfPCell(new Paragraph(""+Total_Reconciled_Amount_double_with_tax_round_final+"", FontFactory.getFont("Tahoma", 10, Font.BOLD, new Color(0,0,0))));
			cell361.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell361.setBackgroundColor(Color.lightGray );
			

            table_End.addCell(cell3111);
            table_End.addCell(cell321);
            table_End.addCell(cell331);
			table_End.addCell(cell341);
			table_End.addCell(cell341);
			if(key.equals("battery"))
			{
				table_End.addCell(cell341);
			}
			else
			{}
			table_End.addCell(cell351);
			//table_End.addCell(cell3511);
			table_End.addCell(cell361);
			
			document.add(table_End);
			
			Paragraph parag112 = (new Paragraph("Asistmi Solutions (P) Ltd. \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t  Authorized Signatory" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag112.setAlignment(Element.ALIGN_LEFT);
			parag112.setSpacingBefore(50.2f);
			document.add(parag112);
			Paragraph parag1123 = (new Paragraph("GSTIN: 29AAJCA7469F2Z7. \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t " ,FontFactory.getFont("Tahoma", 11, Font.BOLD, new Color(0, 0, 0))));
			parag1123.setAlignment(Element.ALIGN_LEFT);
			parag1123.setSpacingBefore(20.2f);
			document.add(parag1123);
			Paragraph parag1121 = (new Paragraph("Payment Details" ,FontFactory.getFont("Tahoma", 10, Font.UNDERLINE, new Color(0, 0, 0))));
			parag1121.setAlignment(Element.ALIGN_LEFT);
			parag1121.setSpacingBefore(20.2f);
			document.add(parag1121);
			//Paragraph parag1122 = (new Paragraph("Asistmi Solutions Private Limited. \r\n Account No : 08858470000098 \r\n IFSC Code : HDFC0000885" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			Paragraph parag1122 = (new Paragraph("Bank Name - HDFC Bank Ltd, \r\n Account Name : ASISTMI SOLUTIONS PRIVATE LIMITED. \r\n Account Number : 08858470000098. \r\n IFSC Code : HDFC0000885. \r\n Branch Code : 0885. \r\n Bank Area Name : BTM Layout. \r\n Bank City Name : Bangalore. \r\n Bank State / Province : Karnataka. \r\n Bank Pin Code : 560068. \r\n Bank Country : India. \r\n HDFC BANK SWIFT code : HDFCINBBBNG. \r\n PAN No : AAJCA7469F." ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag1122.setAlignment(Element.ALIGN_LEFT);
			parag1122.setSpacingBefore(2.2f);
			document.add(parag1122);
			document.close(); 
			
		}
		catch(IOException ioe)
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
		}
		catch(Exception e)
		{
			System.out.println("Excepion Occured in writeMessagesSentToPdf : " + e);
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	 public int writeToPdf2(String retailer,String Strlocorpin,String verificationcode,String strFromDate,String strToDate,String strTempTextFile,String strCMSSERVERIP,String key,String servicetax)
	{
		try
		{
			qm = QueryManager.getInstance(propsMOPConfig) ;

			LogLevel.DEBUG(4,new Throwable(),"Inside writeToPdf");
			LogLevel.DEBUG(5,new Throwable(),"strTempTextFile in WriteToExcel: "+strTempTextFile);
			//LogLevel.DEBUG(5,new Throwable(),"strReportName in WriteToExcel: "+strReportTitle);
			LogLevel.DEBUG(5,new Throwable(),"strCMSSERVERIP: "+strCMSSERVERIP);
			//LogLevel.DEBUG(5,new Throwable(),"RetInoiceVector: "+RetInoiceVector);
			String subheading ="";
			String strQuerytogetbrands="";
			String getStockDetails="";
			ArrayList htav=new ArrayList();
			String batids="";
			String batids1="";
			String batids2="";
			String SqlQrysum = "";
			Hashtable htgrandtot =new Hashtable();
			String strgrandtot="";
			String StrQryservicetax = "";
			Hashtable htservicetax =new Hashtable();
			String strservicetax="";
			String StrQrytottax = "";
			Hashtable httottax =new Hashtable();
			String strtottax="";
			String Strqry1="";
			ArrayList arrPDFHeader = new ArrayList();
			ArrayList dbHeaders = new ArrayList();


			if(key.equals("battery"))
			{
				subheading="Battery Model";

				arrPDFHeader.add(subheading);
				arrPDFHeader.add("Quantity");
				arrPDFHeader.add("PreTax-SRP");
				arrPDFHeader.add("Dealer Price");
				arrPDFHeader.add("Commission");
				arrPDFHeader.add("Total Comm");				
				

				dbHeaders.add("battery_model");						
				dbHeaders.add("quantity");
				dbHeaders.add("price");
				dbHeaders.add("erp_pre_tax");
				dbHeaders.add("comission");
				dbHeaders.add("ourcommision");	
			}
			else
			{
				subheading="Inverter Model";

				arrPDFHeader.add("Inveter model");
				arrPDFHeader.add("Quantity");
				arrPDFHeader.add("PreTax-SRP");
				arrPDFHeader.add("Dealer Price");
				arrPDFHeader.add("Commission");
				arrPDFHeader.add("Total Comm"); 

				dbHeaders.add("inverter_model");
				dbHeaders.add("quantity");
				dbHeaders.add("price");
				dbHeaders.add("inverter_eretailer_price");
				dbHeaders.add("comission");
				dbHeaders.add("ourcommision");
			}

				SimpleDateFormat sdfDate=new SimpleDateFormat("yyyy-MM-dd");
				Date fromDate=sdfDate.parse(strFromDate);  
				Date toDate=sdfDate.parse(strToDate);				


			SimpleDateFormat sdfString=new SimpleDateFormat("dd-MM-yyyy");
			Date date = new Date();
			String dates=sdfString.format(date); 
			//String dates="02-01-2020";
            
			String stFromDate=sdfString.format(fromDate); 
			LogLevel.DEBUG(5, new Throwable(), "stFromDate :"+ stFromDate);

			String stToDate=sdfString.format(toDate); 
			LogLevel.DEBUG(5, new Throwable(), "stToDate :"+ stToDate);
			
			Document document = new Document();
			PdfWriter pdf = PdfWriter.getInstance(document,new FileOutputStream(strTempTextFile));
			document.open();

		
			
			PdfContentByte cb = pdf.getDirectContent();
			cb.setLineWidth(0.5f);
			cb.moveTo(15, 765);
			cb.lineTo(585, 765);
			cb.stroke();

			 PdfPTable table = new PdfPTable(arrPDFHeader.size());
             table.setWidthPercentage(100);  

			Image imghead = Image.getInstance("https://"+strCMSSERVERIP+"/bookbattery/images/batterywalelogo.png");
			imghead.setAbsolutePosition(50,780);
			imghead.scaleAbsolute(100, 30);
			document.add(imghead);	

			
				
			
			Paragraph paragraph= (new Paragraph("www.bookbattery.com", FontFactory.getFont("Tahoma", 18, Font.NORMAL, new Color(0,206,209))));
			paragraph.setAlignment(Element.ALIGN_RIGHT);
			document.add(paragraph);
		

			Paragraph parag32 = (new Paragraph("Invoice No : "+verificationcode+"" ,FontFactory.getFont("Tahoma", 6, Font.UNDERLINE, new Color(0, 0, 0))));
			parag32.setAlignment(Element.ALIGN_CENTER);
			parag32.setSpacingBefore(16.2f);
			parag32.setSpacingAfter(16.2f);
			//parag32.setAlignment(Element.VALIGN_TOP);
			document.add(parag32);

			if(retailer=="")
			{
				Paragraph parag1 = (new Paragraph("Retailer Name : "+retailer+" \r\n Location : "+Strlocorpin+" \r\n Invoice date : "+dates+"" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
				parag1.setAlignment(Element.ALIGN_LEFT);
				parag1.setSpacingAfter(12.2f);
				document.add(parag1);
			}
			else
			{
				Paragraph parag1 = (new Paragraph("Retailer Name : "+retailer+" \r\n Location : "+Strlocorpin+" \r\n Invoice date : "+dates+"" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
				parag1.setAlignment(Element.ALIGN_LEFT);
				parag1.setSpacingAfter(12.2f);
				document.add(parag1);
			}

				
			table.setSpacingBefore(5);

		

			PdfPTable table1 = new PdfPTable(arrPDFHeader.size());
             table1.setWidthPercentage(100);  

			PdfPCell cell111 = new PdfPCell(new Paragraph("Invoice Period", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell111.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell211 = new PdfPCell(new Paragraph("From", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell211.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell311 = new PdfPCell(new Paragraph(""+stFromDate+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell311.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell41 = new PdfPCell(new Paragraph("TO", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell41.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell51 = new PdfPCell(new Paragraph(""+stToDate+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell51.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell61 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell61.setHorizontalAlignment(Element.ALIGN_CENTER);
			

            table1.addCell(cell111);
            table1.addCell(cell211);
            table1.addCell(cell311);
			table1.addCell(cell41);
			table1.addCell(cell51);
			table1.addCell(cell61);
			document.add(table1);

			



			//Writing the Headings to the Pdf 
			for(int h=0;h<arrPDFHeader.size();h++)
			{
				String strHeader = String.valueOf(arrPDFHeader.get(h));
				Phrase phrase2 = new Phrase(strHeader, FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0, 0, 0)));
				//Font fontbold = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
				// document.add(new Paragraph("Times-Roman, Bold", fontbold));
				PdfPCell cell = new PdfPCell(new Paragraph(phrase2));
				cell.setBackgroundColor(Color.lightGray ); 
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);
			}
			table.setHeaderRows(1);	
			//Writing Heading Ends here

			//Writing the Report Data under the respective Heading into PDF
				Hashtable hashContent = new Hashtable();
				String serialno="0";
				/* query to get the transaction history details  */

				if(key.equals("battery"))
				{

						strQuerytogetbrands = "select distinct battery_brand from battery_order_details where date(updated_date) between '"+strFromDate+"' and '"+strToDate+"' and order_status='Customer Contacted' and order_reasons='installed' ";
						LogLevel.DEBUG(5,new Throwable(),"strQuerytogetbrands :"+strQuerytogetbrands );
						
						Vector retbatterybrands=qm.executeQuery(strQuerytogetbrands);
						LogLevel.DEBUG(5,new Throwable(),"retbatterybrands :"+retbatterybrands );


						if(retbatterybrands.isEmpty())
						 { 
							
						 }
						 else
						 {
						for ( int n=0; n<retbatterybrands.size(); n++)
						{
							Hashtable htretbatterybrands=(Hashtable)retbatterybrands.get(n);
							String batterybrand=String.valueOf(htretbatterybrands.get("battery_brand"));
							
							if(batterybrand.equals("Amaron"))
							{
								//################# By Cash Payment
								getStockDetails="select a.battery_model,a.price,count(a.battery_model) as quantity,CAST(round(b.erp_pre_tax*c.city_percentage) AS SIGNED) as erp_pre_tax,CAST(round(((a.price))-(b.erp_pre_tax*c.city_percentage))/ 2 AS SIGNED) as comission,CAST((round(((a.price))-(b.erp_pre_tax*c.city_percentage))/ 2)*(count(a.battery_model)) AS SIGNED) as ourcommision from battery_order_details a, batteryprice b, percentage c where  date(a.updated_date) between '"+strFromDate+"' and '"+strToDate+"' and a.order_status='Customer Contacted' and a.order_reasons='installed' and a.retailer_name='"+retailer+"' and a.battery_model=b.bat_model and b.city='"+Strlocorpin+"' and a.battery_brand='"+batterybrand+"' and a.payment_mode not in('Credit Card','Debit Card')  and c.city=b.city group by a.battery_model,a.price";
								LogLevel.DEBUG(1,new Throwable(),"getStockDetails: "+getStockDetails);
								
								
								//################# By Debit Card Payment
								getStockDetails="select a.battery_model,a.price,count(a.battery_model) as quantity,CAST(round(b.erp_pre_tax*c.city_percentage) AS SIGNED) as erp_pre_tax,CAST(round(((a.price))-(b.erp_pre_tax*c.city_percentage))/ 2 AS SIGNED) as comission,CAST((round(((a.price))-(b.erp_pre_tax*c.city_percentage))/ 2)*(count(a.battery_model)) AS SIGNED) as ourcommision from battery_order_details a, batteryprice b, percentage c where  date(a.updated_date) between '"+strFromDate+"' and '"+strToDate+"' and a.order_status='Customer Contacted' and a.order_reasons='installed' and a.retailer_name='"+retailer+"' and a.battery_model=b.bat_model and b.city='"+Strlocorpin+"' and a.battery_brand='"+batterybrand+"' and a.payment_mode='Debit Card'  and c.city=b.city group by a.battery_model,a.price";
								LogLevel.DEBUG(1,new Throwable(),"getStockDetails: "+getStockDetails);
								
								
								//################# By Credit Card Payment
								getStockDetails="select a.battery_model,a.price,count(a.battery_model) as quantity,CAST(round(b.erp_pre_tax*c.city_percentage) AS SIGNED) as erp_pre_tax,CAST(round(((a.price))-(b.erp_pre_tax*c.city_percentage))/ 2 AS SIGNED) as comission,CAST((round(((a.price))-(b.erp_pre_tax*c.city_percentage))/ 2)*(count(a.battery_model)) AS SIGNED) as ourcommision from battery_order_details a, batteryprice b, percentage c where  date(a.updated_date) between '"+strFromDate+"' and '"+strToDate+"' and a.order_status='Customer Contacted' and a.order_reasons='installed' and a.retailer_name='"+retailer+"' and a.battery_model=b.bat_model and b.city='"+Strlocorpin+"' and a.battery_brand='"+batterybrand+"' and a.payment_mode='Credit Card' and a.payment_mode not in('Credit Card','Debit Card')  and c.city=b.city group by a.battery_model,a.price";
								LogLevel.DEBUG(1,new Throwable(),"getStockDetails: "+getStockDetails);
								
								
								

								 Strqry1 = "select CAST((round(((a.price))-(b.erp_pre_tax*c.city_percentage))/ 2)*(count(a.battery_model)) AS SIGNED) as grandtotal from battery_order_details a, batteryprice b, percentage c where date(a.updated_date) between '"+strFromDate+"' and '"+strToDate+"'  and a.order_status='Customer Contacted' and a.order_reasons='installed' and a.retailer_name='"+retailer+"' and  a.battery_model=b.bat_model and b.city='"+Strlocorpin+"' and a.battery_brand='"+batterybrand+"' and c.city=b.city group by a.battery_model,a.price";
								LogLevel.DEBUG(5, new Throwable(), "Strqry1 :" + Strqry1);

								htav=new ArrayList();
								htav=qm.getField(Strqry1);

								for(int i=0;i<htav.size();i++)
								{
								if(batids1.equals(""))
								batids1=htav.get(i).toString();
								else
								batids1=batids1+"+"+htav.get(i).toString();
								}
								LogLevel.DEBUG(5,new Throwable(),"batids1:"+batids1);

							}
							else
							{

								getStockDetails = "select a.battery_model,a.price,count(a.battery_model) as quantity,CAST(round(a.price/c.city_percentage) AS SIGNED) as 14percentvat,b.erp_pre_tax,CAST(round(((a.price))-(b.erp_pre_tax))/ 2 AS SIGNED) as comission,CAST((round(((a.price))-(b.erp_pre_tax))/ 2)*(count(a.battery_model)) AS SIGNED) as ourcommision from battery_order_details a, batteryprice b, percentage c where date(a.updated_date) between '"+strFromDate+"' and '"+strToDate+"' and a.order_status='Customer Contacted' and a.order_reasons='installed' and a.retailer_name='"+retailer+"' and a.battery_model=b.bat_model and b.city='"+Strlocorpin+"' and a.battery_brand='"+batterybrand+"' and c.city=b.city group by a.battery_model,a.price";
								LogLevel.DEBUG(5, new Throwable(), "getStockDetails :" + getStockDetails);

								 Strqry1 = "select CAST((round(((a.price))-(b.erp_pre_tax))/ 2)*(count(a.battery_model)) AS SIGNED) as grandtotal from battery_order_details a, batteryprice b, percentage c where date(a.updated_date) between '"+strFromDate+"' and '"+strToDate+"' and a.order_status='Customer Contacted' and a.order_reasons='installed' and a.retailer_name='"+retailer+"' and  a.battery_model=b.bat_model and b.city='"+Strlocorpin+"' and a.battery_brand='"+batterybrand+"'  and c.city=b.city group by a.battery_model,a.price";
								LogLevel.DEBUG(5, new Throwable(), "Strqry1 :" + Strqry1);

								htav=new ArrayList();
								htav=qm.getField(Strqry1);

								for(int i=0;i<htav.size();i++)
								{
								if(batids2.equals(""))
								batids2=htav.get(i).toString();
								else
								batids2=batids2+"+"+htav.get(i).toString();
								}
								LogLevel.DEBUG(5,new Throwable(),"batids2:"+batids2);


							}

				Vector alReport=qm.executeQuery(getStockDetails);


				for (int j=0; j<alReport.size(); j++)
				{
					hashContent   = (Hashtable)alReport.get(j);
					int value = Integer.parseInt(serialno);
					LogLevel.DEBUG(1,new Throwable(),"value is : "+value);
					int nextValue = value + 1; // find the int value plus 1

					serialno= String.valueOf(nextValue) ;			
					LogLevel.DEBUG(1,new Throwable(),"serialno value"+serialno);


					
					for (int ik=0;ik < dbHeaders.size() ;ik++ )
					{
						String strHeader=(String)dbHeaders.get(ik);
						
						String strData=(hashContent.get(strHeader)!=null)?String.valueOf(hashContent.get(strHeader)):"-";
						
						Phrase phrase3 = new Phrase(strData, FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0, 0, 0)));
						PdfPCell cell = new PdfPCell(new Paragraph(phrase3));
						//cell.setBackgroundColor(Color.lightGray ); 
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);
						//table.addCell(phrase3);

					}
					}
						}


						 }

				}

				else
			{

						strQuerytogetbrands = "select distinct inverter_brand from inverter_order_details where date(updated_date) between '"+strFromDate+"' and '"+strToDate+"' and order_status='Customer Contacted' and order_reasons='installed' ";
						LogLevel.DEBUG(5,new Throwable(),"strQuerytogetbrands :"+strQuerytogetbrands );
						
						Vector retbatterybrands=qm.executeQuery(strQuerytogetbrands);
						LogLevel.DEBUG(5,new Throwable(),"retbatterybrands :"+retbatterybrands );


						if(retbatterybrands.isEmpty())
						 { 
							
						 }
						 else
						 {
						for ( int n=0; n<retbatterybrands.size(); n++)
						{
							Hashtable htretbatterybrands=(Hashtable)retbatterybrands.get(n);
							String inverterbrand=String.valueOf(htretbatterybrands.get("inverter_brand"));
							
							if(inverterbrand.equals("Amaron"))
							{
								

								getStockDetails="select a.inverter_model,a.price,count(a.inverter_capacity) as quantity,CAST(round(b.inverter_eretailer_price*c.city_percentage) AS SIGNED) as inverter_eretailer_price,CAST(round(((a.price))-(b.inverter_eretailer_price*c.city_percentage))/ 2 AS SIGNED) as comission,CAST((round(((a.price))-(b.inverter_eretailer_price*c.city_percentage))/ 2)*(count(a.inverter_capacity)) AS SIGNED) as ourcommision from inverter_order_details a, inverter_price_details b, percentage c where date(a.updated_date) between '"+strFromDate+"' and '"+strToDate+"' and a.order_status='Customer Contacted' and a.order_reasons='installed' and a.retailer_name='"+retailer+"' and a.inverter_capacity=b.inverter_capacity and a.inverter_model=b.inverter_model and b.city='"+Strlocorpin+"' and a.inverter_brand='"+inverterbrand+"' and c.city=b.city group by a.inverter_capacity,a.price";
								LogLevel.DEBUG(1,new Throwable(),"getStockDetails: "+getStockDetails);

								Strqry1 = "select CAST((round(((a.price))-(b.inverter_eretailer_price*c.city_percentage))/ 2)*(count(a.inverter_capacity)) AS SIGNED) as grandtotal from inverter_order_details a, inverter_price_details b, percentage c where date(a.updated_date) between '"+strFromDate+"' and '"+strToDate+"' and a.order_status='Customer Contacted' and a.order_reasons='installed' and a.retailer_name='"+retailer+"' and  a.inverter_capacity=b.inverter_capacity and b.city='"+Strlocorpin+"' and a.inverter_brand='"+inverterbrand+"' and a.inverter_model=b.inverter_model and c.city=b.city group by a.inverter_capacity,a.price";

								LogLevel.DEBUG(5, new Throwable(), "Strqry1 :" + Strqry1);

								htav=new ArrayList();
								htav=qm.getField(Strqry1);

								for(int i=0;i<htav.size();i++)
								{
								if(batids1.equals(""))
								batids1=htav.get(i).toString();
								else
								batids1=batids1+"+"+htav.get(i).toString();
								}
								LogLevel.DEBUG(5,new Throwable(),"batids1:"+batids1);

							}
							else
							{

								//getStockDetails="select a.inverter_model,b.inverter_discount_price,count(a.inverter_capacity) as quantity,b.inverter_eretailer_price,CAST(round(((b.inverter_discount_price))-(b.inverter_eretailer_price))/ 2 AS SIGNED) as comission,CAST((round(((b.inverter_discount_price))-(b.inverter_eretailer_price))/ 2)*(count(a.inverter_capacity)) AS SIGNED) as ourcommision from inverter_order_details a, inverter_price_details b, percentage c where date(a.updated_date) between '"+strFromDate+"' and '"+strToDate+"' and a.order_status='Customer Contacted' and a.order_reasons='installed' and a.retailer_name='"+retailer+"' and a.inverter_capacity=b.inverter_capacity and a.inverter_model=b.inverter_model and b.city='"+Strlocorpin+"' and a.inverter_brand='"+inverterbrand+"' and c.city=b.city group by a.inverter_capacity,a.price";
								getStockDetails="select a.inverter_model,a.price,count(a.inverter_capacity) as quantity,b.inverter_eretailer_price,CAST(round(((a.price))-(b.inverter_eretailer_price))/ 2 AS SIGNED) as comission,CAST((round(((a.price))-(b.inverter_eretailer_price))/ 2)*(count(a.inverter_capacity)) AS SIGNED) as ourcommision from inverter_order_details a, inverter_price_details b, percentage c where date(a.updated_date) between '"+strFromDate+"' and '"+strToDate+"' and a.order_status='Customer Contacted' and a.order_reasons='installed' and a.retailer_name='"+retailer+"' and a.inverter_capacity=b.inverter_capacity and a.inverter_model=b.inverter_model and b.city='"+Strlocorpin+"' and a.inverter_brand='"+inverterbrand+"' and c.city=b.city group by a.inverter_capacity,a.price";
								LogLevel.DEBUG(1,new Throwable(),"getStockDetails: "+getStockDetails);

								Strqry1 = "select CAST((round(((a.price))-(b.inverter_eretailer_price))/ 2)*(count(a.inverter_capacity)) AS SIGNED) as grandtotal from inverter_order_details a, inverter_price_details b, percentage c where date(a.updated_date) between '"+strFromDate+"' and '"+strToDate+"' and a.order_status='Customer Contacted' and a.order_reasons='installed' and a.retailer_name='"+retailer+"' and  a.inverter_capacity=b.inverter_capacity and b.city='"+Strlocorpin+"' and a.inverter_brand='"+inverterbrand+"' and a.inverter_model=b.inverter_model and c.city=b.city group by a.inverter_capacity,a.price";

								htav=new ArrayList();
								htav=qm.getField(Strqry1);

								for(int i=0;i<htav.size();i++)
								{
								if(batids2.equals(""))
								batids2=htav.get(i).toString();
								else
								batids2=batids2+"+"+htav.get(i).toString();
								}
								LogLevel.DEBUG(5,new Throwable(),"batids2:"+batids2);


							}

				Vector alReport=qm.executeQuery(getStockDetails);


				for (int j=0; j<alReport.size(); j++)
				{
					hashContent   = (Hashtable)alReport.get(j);
					int value = Integer.parseInt(serialno);
					LogLevel.DEBUG(1,new Throwable(),"value is : "+value);
					int nextValue = value + 1; // find the int value plus 1

					serialno= String.valueOf(nextValue) ;			
					LogLevel.DEBUG(1,new Throwable(),"serialno value"+serialno);


					
					for (int ik=0;ik < dbHeaders.size() ;ik++ )
					{
						String strHeader=(String)dbHeaders.get(ik);
						
						String strData=(hashContent.get(strHeader)!=null)?String.valueOf(hashContent.get(strHeader)):"-";
						
						Phrase phrase3 = new Phrase(strData, FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0, 0, 0)));
						PdfPCell cell = new PdfPCell(new Paragraph(phrase3));
						//cell.setBackgroundColor(Color.lightGray ); 
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(cell);
						//table.addCell(phrase3);

					}
					}
						}


						 }



			}

						if(batids2.equals(""))
						{
							batids2="0";
						}
						else
						{
							batids2=batids2;
						}
						if(batids1.equals(""))
						{
							batids1="0";
						}
						else
						{
							batids1=batids1;
						}
						batids=batids1+"+"+batids2;

						//System.out.println("//test/me".replaceAll("^/+", ""));
						//batids=batids.replaceAll("^/+", "");
						//LogLevel.DEBUG(5,new Throwable(),"batids after removing leading characters:"+batids);


						//System.out.println("//test/me//".replaceAll("/+$", ""));
						//batids=batids.trim();
						//LogLevel.DEBUG(5,new Throwable(),"batids after removing trailing characters:"+batids);


						SqlQrysum = "select CAST(sum("+batids+") AS SIGNED) as grandtotal";
						LogLevel.DEBUG(5,new Throwable(),"SqlQrysum:"+SqlQrysum);
						
						htgrandtot = qm.getRow(SqlQrysum); 
						strgrandtot=String.valueOf(htgrandtot.get("grandtotal"));
						LogLevel.DEBUG(5, new Throwable(), "strgrandtot :" + strgrandtot);

						StrQryservicetax = "select CAST(round(("+strgrandtot+"-(("+strgrandtot+"/"+servicetax+")*100))) AS SIGNED) as servicetaxyprice";
						LogLevel.DEBUG(5, new Throwable(), "StrQryservicetax :" + StrQryservicetax);

						htservicetax = qm.getRow(StrQryservicetax); 
						strservicetax=String.valueOf(htservicetax.get("servicetaxyprice"));
						LogLevel.DEBUG(5, new Throwable(), "strservicetax :" + strservicetax);

						StrQrytottax = "select CAST(round(("+strgrandtot+"/"+servicetax+")*100) AS SIGNED) as tottaxdedaction";
						LogLevel.DEBUG(5, new Throwable(), "StrQrytottax :" + StrQrytottax);

						httottax = qm.getRow(StrQrytottax); 
						strtottax=String.valueOf(httottax.get("tottaxdedaction"));
						LogLevel.DEBUG(5, new Throwable(), "strtottax :" + strtottax);
			
			PdfPCell cell31 = new PdfPCell(new Paragraph("Total Commission", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell31.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell31.setBackgroundColor(Color.lightGray ); 
			PdfPCell cell32 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell32.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell33 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell33.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell34 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell34.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell35 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell35.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell36 = new PdfPCell(new Paragraph(""+strgrandtot+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell36.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell36.setBackgroundColor(Color.lightGray ); 
			

            table.addCell(cell31);
            table.addCell(cell32);
            table.addCell(cell33);
			table.addCell(cell34);
			table.addCell(cell35);
			table.addCell(cell36);

			/*PdfPCell cell411 = new PdfPCell(new Paragraph("Service Tax Adjustment", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell411.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell411.setBackgroundColor(Color.lightGray ); 
			PdfPCell cell42 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell42.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell43 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell43.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell44 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell44.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell45 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell45.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell46 = new PdfPCell(new Paragraph(""+strservicetax+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell46.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell46.setBackgroundColor(Color.lightGray ); 
			

            table.addCell(cell411);
            table.addCell(cell42);
            table.addCell(cell43);
			table.addCell(cell44);
			table.addCell(cell45);
			table.addCell(cell46);

			PdfPCell cell511 = new PdfPCell(new Paragraph("Total Net Commission", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell511.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell511.setBackgroundColor(Color.lightGray ); 
			PdfPCell cell52 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell52.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell53 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell53.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell54 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell54.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell55 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell55.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell56 = new PdfPCell(new Paragraph(""+strtottax+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell56.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell56.setBackgroundColor(Color.lightGray ); 
			

            table.addCell(cell511);
            table.addCell(cell52);
            table.addCell(cell53);
			table.addCell(cell54);
			table.addCell(cell55);
			table.addCell(cell56);


			PdfPCell cell4111 = new PdfPCell(new Paragraph("Service Tax", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell4111.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell4111.setBackgroundColor(Color.lightGray ); 
			PdfPCell cell421 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell421.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell431 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell431.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell441 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell441.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell451 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell451.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell461 = new PdfPCell(new Paragraph(""+strservicetax+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell461.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell461.setBackgroundColor(Color.lightGray ); 
			

            table.addCell(cell4111);
            table.addCell(cell421);
            table.addCell(cell431);
			table.addCell(cell441);
			table.addCell(cell451);
			table.addCell(cell461);

			PdfPCell cell3111 = new PdfPCell(new Paragraph("Total Payable", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell3111.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell3111.setBackgroundColor(Color.lightGray );
			PdfPCell cell321 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell321.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell331 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell33.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell341 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell341.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell351 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell351.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell361 = new PdfPCell(new Paragraph(""+strgrandtot+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell361.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell361.setBackgroundColor(Color.lightGray );
			

            table.addCell(cell3111);
            table.addCell(cell321);
            table.addCell(cell331);
			table.addCell(cell341);
			table.addCell(cell351);
			table.addCell(cell361);*/
			
			
			
			document.add(table);
			Paragraph parag112 = (new Paragraph("Asistmi Solutions (P) Ltd. \t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t  Authorized Signatory" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag112.setAlignment(Element.ALIGN_LEFT);
			parag112.setSpacingBefore(50.2f);
			document.add(parag112);
			Paragraph parag1121 = (new Paragraph("Payment Details" ,FontFactory.getFont("Tahoma", 10, Font.UNDERLINE, new Color(0, 0, 0))));
			parag1121.setAlignment(Element.ALIGN_LEFT);
			parag1121.setSpacingBefore(20.2f);
			document.add(parag1121);
			//Paragraph parag1122 = (new Paragraph("Asistmi Solutions Private Limited. \r\n Account No : 08858470000098 \r\n IFSC Code : HDFC0000885" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			Paragraph parag1122 = (new Paragraph("Bank Name - HDFC Bank Ltd, \r\n Account Name : ASISTMI SOLUTIONS PRIVATE LIMITED. \r\n Account Number : 08858470000098. \r\n IFSC Code : HDFC0000885. \r\n Branch Code : 0885. \r\n Bank Area Name : BTM Layout. \r\n Bank City Name : Bangalore. \r\n Bank State / Province : Karnataka. \r\n Bank Pin Code : 560068. \r\n Bank Country : India. \r\n HDFC BANK SWIFT code : HDFCINBBBNG. \r\n PAN No : AAJCA7469F." ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag1122.setAlignment(Element.ALIGN_LEFT);
			parag1122.setSpacingBefore(2.2f);
			document.add(parag1122);
			document.close(); 
			
		}
		catch(IOException ioe)
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
		}
		catch(Exception e)
		{
			System.out.println("Excepion Occured in writeMessagesSentToPdf : " + e);
			e.printStackTrace();
			return -1;
		}
		return 0;
	}
}
