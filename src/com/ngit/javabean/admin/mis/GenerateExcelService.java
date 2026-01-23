/*****************************************************
NGIT. Confidential.
@File Name		: GenerateExcel.java
@Description	: TO generate Excel Report
@Author			: Prakash Mallidi  
@Date			: 18-08-2009
@Reviewed By	: 
*******************************************************/


package com.ngit.javabean.admin.mis;

import com.ngit.javabean.loglevel.LogLevel;
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
//Pdf Import Ends here

public class GenerateExcelService extends HttpServlet 
{
	private String outfile;
	private WritableWorkbook workbook;
	Properties propsMOPConfig;
	private ServletContext context; 

	public GenerateExcelService()
	{
	}
	public GenerateExcelService(String ofn)
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
					strData = strData;
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
	
	public int writeToPdf(ArrayList arrPDFHeader,ArrayList dbHeaders,Vector alReport,String strTempTextFile,String strCMSSERVERIP,String strReportTitle,String ReportDate)
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

			Image imghead = Image.getInstance("http://"+strCMSSERVERIP+"/bookbattery/images/batterywalelogo.png");
			imghead.setAbsolutePosition(50,760);
			imghead.scaleAbsolute(120, 35);
			document.add(imghead);		
		
				
			Image imghead1 = Image.getInstance("http://"+strCMSSERVERIP+"/bookbattery/images/combined_logo_new.png");
			imghead1.setAbsolutePosition(440,810);
			imghead1.scaleAbsolute(125,25);
			document.add(imghead1);
				
			
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

			Image imghead = Image.getInstance("http://"+strCMSSERVERIP+"/bookbattery/images/batterywalelogo.png");
			imghead.setAbsolutePosition(50,760);
			imghead.scaleAbsolute(120, 35);
			document.add(imghead);		
		
				
			Image imghead1 = Image.getInstance("http://"+strCMSSERVERIP+"/bookbattery/images/combined_logo_new.png");
			imghead1.setAbsolutePosition(440,810);
			imghead1.scaleAbsolute(125,25);
			document.add(imghead1);
				
			
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

  public int writeToPdf1(String retailer,String Strlocorpin,String verificationcode,String strFromDate,String strToDate,ArrayList arrPDFHeader,ArrayList dbHeaders,Vector RetInoiceVector,String strgrandtot,String strservicetax,String strtottax,String strTempTextFile,String strCMSSERVERIP,String key,String retailerid)
	{
		try
		{
			LogLevel.DEBUG(4,new Throwable(),"Inside writeToPdf");
			LogLevel.DEBUG(5,new Throwable(),"strTempTextFile in WriteToExcel: "+strTempTextFile);
			//LogLevel.DEBUG(5,new Throwable(),"strReportName in WriteToExcel: "+strReportTitle);
			LogLevel.DEBUG(5,new Throwable(),"strCMSSERVERIP: "+strCMSSERVERIP);
			LogLevel.DEBUG(5,new Throwable(),"RetInoiceVector: "+RetInoiceVector);
			LogLevel.DEBUG(5,new Throwable(),"retailerid: "+retailerid);
			
						double strgrandtot_Online_Payment_double = Double.parseDouble(strgrandtot);

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
			
			int Total_Reconciled_Amount_double_with_tax_round_int= (int) Math.round(Total_Reconciled_Amount_double_with_tax_round);
			
			
			
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
			


			SimpleDateFormat sdfString=new SimpleDateFormat("dd-MM-yyyy");
			Date date = new Date();
			String dates=sdfString.format(date); 
			
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

			Image imghead = Image.getInstance("http://"+strCMSSERVERIP+"/bookbattery/images/batterywalelogo.png");
			imghead.setAbsolutePosition(50,780);
			imghead.scaleAbsolute(100, 30);
			document.add(imghead);	

			/*Image imgheadfot = Image.getInstance("http://"+strCMSSERVERIP+"/bookbattery/images/Asistmiletterpad.png");
			imgheadfot.setAbsolutePosition(210,35);
			imgheadfot.scaleAbsolute(170, 30);
			document.add(imgheadfot);*/	
			
			//PdfContentByte cb1 = pdf.getDirectContent();
			//cb1.setLineWidth(0.5f);
			//cb1.moveTo(15, 75);
			//cb1.lineTo(585, 75);
			//cb1.stroke();		
				
		//	Image imghead1 = Image.getInstance("http://"+strCMSSERVERIP+"/bookbattery/images/combined_logo_new.png");
			//imghead1.setAbsolutePosition(230,5);
			//imghead1.scaleAbsolute(125,25);
		//	document.add(imghead1);
				
			
			Paragraph paragraph= (new Paragraph("www.bookbattery.com", FontFactory.getFont("Tahoma", 18, Font.NORMAL, new Color(0,206,209))));
			paragraph.setAlignment(Element.ALIGN_RIGHT);
			document.add(paragraph);
			
			//Paragraph parag = (new Paragraph(new Date().toString(),FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0, 0, 0))));
			//parag.setAlignment(Element.ALIGN_RIGHT);
			//document.add(parag);

			//Paragraph parag1 = (new Paragraph(ReportDate,FontFactory.getFont("Tahoma", 6, Font.NORMAL, new Color(0, 0, 0))));
			//parag1.setAlignment(Element.ALIGN_RIGHT);
			//document.add(parag1);

			Paragraph parag32 = (new Paragraph("Invoice No : "+verificationcode+"" ,FontFactory.getFont("Tahoma", 6, Font.UNDERLINE, new Color(0, 0, 0))));
			parag32.setAlignment(Element.ALIGN_CENTER);
			parag32.setSpacingBefore(16.2f);
			parag32.setSpacingAfter(16.2f);
			//parag32.setAlignment(Element.VALIGN_TOP);
			document.add(parag32);

			
			if(retailerid=="")
			{
				Paragraph parag1 = (new Paragraph("Retailer Name : "+retailer+" \r\n Location : "+Strlocorpin+" \r\n Invoice date : "+dates+"" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
				parag1.setAlignment(Element.ALIGN_LEFT);
				parag1.setSpacingAfter(12.2f);
				document.add(parag1);
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

			/*PdfPTable table1 = new PdfPTable(7); // 3 columns.
		table1.setWidthPercentage(100);
        table1.setHorizontalAlignment(Element.ALIGN_LEFT);
		table1.setSpacingBefore(25);*/

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
			

            table1.addCell(cell111);
            table1.addCell(cell211);
            table1.addCell(cell311);
			table1.addCell(cell41);
			table1.addCell(cell51);
			table1.addCell(cell61);
			document.add(table1);

			PdfPCell cell21 = new PdfPCell(new Paragraph("Services Type", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
            cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
		    cell21.setBackgroundColor(Color.lightGray ); 
			PdfPCell cell22 = new PdfPCell(new Paragraph("Services Package", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
            cell22.setHorizontalAlignment(Element.ALIGN_CENTER);
		    cell22.setBackgroundColor(Color.lightGray ); 
			PdfPCell cell23 = new PdfPCell(new Paragraph("SRP", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
			cell23.setHorizontalAlignment(Element.ALIGN_CENTER);
		    cell23.setBackgroundColor(Color.lightGray ); 
			PdfPCell cell24 = new PdfPCell(new Paragraph("Commission", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
			cell24.setHorizontalAlignment(Element.ALIGN_CENTER);
		    cell24.setBackgroundColor(Color.lightGray ); 
			PdfPCell cell25 = new PdfPCell(new Paragraph("Total Comm", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
			cell25.setHorizontalAlignment(Element.ALIGN_CENTER);
		    cell25.setBackgroundColor(Color.lightGray ); 
			//PdfPCell cell26 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
			//cell26.setHorizontalAlignment(Element.ALIGN_CENTER);
		    //cell26.setBackgroundColor(Color.lightGray ); 
	
            table.addCell(cell21);
            table.addCell(cell22);
            table.addCell(cell23);
			table.addCell(cell24);
			table.addCell(cell25);
			//table.addCell(cell26);



			//Writing the Headings to the Pdf 
			/*for(int h=0;h<arrPDFHeader.size();h++)
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
			table.setHeaderRows(1);	*/
			//Writing Heading Ends here
			//Writing the Report Data under the respective Heading into PDF
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
			
			PdfPCell cell31 = new PdfPCell(new Paragraph("Total Commission Amount", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell31.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell31.setBackgroundColor(Color.lightGray ); 
			PdfPCell cell32 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell32.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell33 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell33.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell34 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell34.setHorizontalAlignment(Element.ALIGN_CENTER);
			//PdfPCell cell35 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			//cell35.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell36 = new PdfPCell(new Paragraph(""+strgrandtot+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell36.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell36.setBackgroundColor(Color.lightGray ); 
			

            table.addCell(cell31);
            table.addCell(cell32);
            table.addCell(cell33);
			table.addCell(cell34);
			//table.addCell(cell35);
			table.addCell(cell36);

		if(Strlocorpin.equals("Bangalore"))
		{			
			PdfPCell cell411 = new PdfPCell(new Paragraph("IGST -18 %", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell411.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell411.setBackgroundColor(Color.lightGray ); 
			PdfPCell cell42 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell42.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell43 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell43.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell44 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell44.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell46 = new PdfPCell(new Paragraph(""+Totgststrr+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell46.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell46.setBackgroundColor(Color.lightGray ); 
			
            table.addCell(cell411);
            table.addCell(cell42);
            table.addCell(cell43);
			table.addCell(cell44);
			table.addCell(cell46);
			
		}
		else
		{
			PdfPCell cell4111 = new PdfPCell(new Paragraph("CGST -9 %", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell4111.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell4111.setBackgroundColor(Color.lightGray ); 
			PdfPCell cell421 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell421.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell431 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell431.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell441 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell441.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell461 = new PdfPCell(new Paragraph(""+Cgststr+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell461.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell461.setBackgroundColor(Color.lightGray ); 
			
            table.addCell(cell4111);
            table.addCell(cell421);
            table.addCell(cell431);
			table.addCell(cell441);
			table.addCell(cell461);
			
			PdfPCell cell4112 = new PdfPCell(new Paragraph("SGST -9 %", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell4112.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell4112.setBackgroundColor(Color.lightGray ); 
			PdfPCell cell422 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell422.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell432 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell432.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell442 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell442.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell462 = new PdfPCell(new Paragraph(""+Sgststr+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell462.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell462.setBackgroundColor(Color.lightGray ); 
			
            table.addCell(cell4112);
            table.addCell(cell422);
            table.addCell(cell432);
			table.addCell(cell442);
			table.addCell(cell462);	
		}

			PdfPCell cell511 = new PdfPCell(new Paragraph("Total Commission Amount with GST", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell511.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell511.setBackgroundColor(Color.lightGray ); 
			PdfPCell cell52 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell52.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell53 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell53.setHorizontalAlignment(Element.ALIGN_CENTER);
			//PdfPCell cell54 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			//cell54.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell55 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell55.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell56 = new PdfPCell(new Paragraph(""+Tot_comm_with_tax+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell56.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell56.setBackgroundColor(Color.lightGray ); 
			

            table.addCell(cell511);
            table.addCell(cell52);
            table.addCell(cell53);
			//table.addCell(cell54);
			table.addCell(cell55);
			table.addCell(cell56);


			PdfPCell cell3111 = new PdfPCell(new Paragraph("Total Reconciled Amount", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
            cell3111.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell3111.setBackgroundColor(Color.lightGray );
			PdfPCell cell321 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell321.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell331 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell33.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell341 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell341.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cell361 = new PdfPCell(new Paragraph(""+Tot_comm_with_tax+"", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
			cell361.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell361.setBackgroundColor(Color.lightGray );
			
            table.addCell(cell3111);
            table.addCell(cell321);
            table.addCell(cell331);
			table.addCell(cell341);
			table.addCell(cell361);
			

			document.add(table);
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

	 public int writeToPdf2(String retailer,String Strlocorpin,String verificationcode,String strFromDate,String strToDate,ArrayList arrPDFHeader,ArrayList dbHeaders,Vector RetInoiceVector,String strgrandtot,String strservicetax,String strtottax,String strTempTextFile,String strCMSSERVERIP,String key)
	{
		try
		{
			LogLevel.DEBUG(4,new Throwable(),"Inside writeToPdf");
			LogLevel.DEBUG(5,new Throwable(),"strTempTextFile in WriteToExcel: "+strTempTextFile);
			//LogLevel.DEBUG(5,new Throwable(),"strReportName in WriteToExcel: "+strReportTitle);
			LogLevel.DEBUG(5,new Throwable(),"strCMSSERVERIP: "+strCMSSERVERIP);
			LogLevel.DEBUG(5,new Throwable(),"RetInoiceVector: "+RetInoiceVector);
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

			Image imghead = Image.getInstance("http://"+strCMSSERVERIP+"/bookbattery/images/batterywalelogo.png");
			imghead.setAbsolutePosition(50,780);
			imghead.scaleAbsolute(100, 30);
			document.add(imghead);	

			/*Image imgheadfot = Image.getInstance("http://"+strCMSSERVERIP+"/bookbattery/images/Asistmiletterpad.png");
			imgheadfot.setAbsolutePosition(210,35);
			imgheadfot.scaleAbsolute(170, 30);
			document.add(imgheadfot);*/	
			
			//PdfContentByte cb1 = pdf.getDirectContent();
			//cb1.setLineWidth(0.5f);
			//cb1.moveTo(15, 75);
			//cb1.lineTo(585, 75);
			//cb1.stroke();		
				
		//	Image imghead1 = Image.getInstance("http://"+strCMSSERVERIP+"/bookbattery/images/combined_logo_new.png");
			//imghead1.setAbsolutePosition(230,5);
			//imghead1.scaleAbsolute(125,25);
		//	document.add(imghead1);
				
			
			Paragraph paragraph= (new Paragraph("www.bookbattery.com", FontFactory.getFont("Tahoma", 18, Font.NORMAL, new Color(0,206,209))));
			paragraph.setAlignment(Element.ALIGN_RIGHT);
			document.add(paragraph);
			
			//Paragraph parag = (new Paragraph(new Date().toString(),FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0, 0, 0))));
			//parag.setAlignment(Element.ALIGN_RIGHT);
			//document.add(parag);

			//Paragraph parag1 = (new Paragraph(ReportDate,FontFactory.getFont("Tahoma", 6, Font.NORMAL, new Color(0, 0, 0))));
			//parag1.setAlignment(Element.ALIGN_RIGHT);
			//document.add(parag1);

			Paragraph parag32 = (new Paragraph("Invoice No : "+verificationcode+"" ,FontFactory.getFont("Tahoma", 6, Font.UNDERLINE, new Color(0, 0, 0))));
			parag32.setAlignment(Element.ALIGN_CENTER);
			parag32.setSpacingBefore(16.2f);
			parag32.setSpacingAfter(16.2f);
			//parag32.setAlignment(Element.VALIGN_TOP);
			document.add(parag32);

			Paragraph parag1 = (new Paragraph("Retailer Name : "+retailer+" \r\n Location : "+Strlocorpin+" \r\n Invoice date : "+dates+"" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag1.setAlignment(Element.ALIGN_LEFT);
			parag1.setSpacingAfter(12.2f);
			document.add(parag1);
			
				
			table.setSpacingBefore(5);

			/*PdfPTable table1 = new PdfPTable(7); // 3 columns.
		table1.setWidthPercentage(100);
        table1.setHorizontalAlignment(Element.ALIGN_LEFT);
		table1.setSpacingBefore(25);*/

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
			

            table1.addCell(cell111);
            table1.addCell(cell211);
            table1.addCell(cell311);
			table1.addCell(cell41);
			table1.addCell(cell51);
			table1.addCell(cell61);
			document.add(table1);

			PdfPCell cell21 = new PdfPCell(new Paragraph(""+subheading+"", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
            cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
		    cell21.setBackgroundColor(Color.lightGray ); 
			PdfPCell cell22 = new PdfPCell(new Paragraph("Quantity", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
            cell22.setHorizontalAlignment(Element.ALIGN_CENTER);
		    cell22.setBackgroundColor(Color.lightGray ); 
			PdfPCell cell23 = new PdfPCell(new Paragraph("PreTax-SRP", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
			cell23.setHorizontalAlignment(Element.ALIGN_CENTER);
		    cell23.setBackgroundColor(Color.lightGray ); 
			PdfPCell cell24 = new PdfPCell(new Paragraph("PreTax-ERP", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
			cell24.setHorizontalAlignment(Element.ALIGN_CENTER);
		    cell24.setBackgroundColor(Color.lightGray ); 
			PdfPCell cell25 = new PdfPCell(new Paragraph("Commission", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
			cell25.setHorizontalAlignment(Element.ALIGN_CENTER);
		    cell25.setBackgroundColor(Color.lightGray ); 
			PdfPCell cell26 = new PdfPCell(new Paragraph("Total Comm", FontFactory.getFont("Tahoma", 8, Font.BOLD, new Color(0,0,0))));
			cell26.setHorizontalAlignment(Element.ALIGN_CENTER);
		    cell26.setBackgroundColor(Color.lightGray ); 
	
            table.addCell(cell21);
            table.addCell(cell22);
            table.addCell(cell23);
			table.addCell(cell24);
			table.addCell(cell25);
			table.addCell(cell26);



			//Writing the Headings to the Pdf 
			/*for(int h=0;h<arrPDFHeader.size();h++)
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
			table.setHeaderRows(1);	*/
			//Writing Heading Ends here
			//Writing the Report Data under the respective Heading into PDF
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

			PdfPCell cell411 = new PdfPCell(new Paragraph("Service Tax Adjustment", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
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
			table.addCell(cell361);
			
			//Ends OF report Summary
		//	 table.setSpacingBefore(10);

			//Writing the Headings to the Pdf 
		/*	for(int h=0;h<arrPDFHeader.size();h++)
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
			for (int j=0; j<alReport.size(); j++)
			{
				hashContent   = (Hashtable)alReport.get(j);
				
				for (int ik=0;ik < dbHeaders.size() ;ik++ )
				{
					String strHeader=(String)dbHeaders.get(ik);
					String strData=(hashContent.get(strHeader)!=null)?String.valueOf(hashContent.get(strHeader)):"-";
					Phrase phrase3 = new Phrase(strData, FontFactory.getFont("Tahoma", 6, Font.NORMAL, new Color(0, 0, 0)));
					table.addCell(phrase3);
				}
			}*/
			
			//document.add(table);
		/*PdfPTable table = new PdfPTable(7); // 3 columns.
		table.setWidthPercentage(90);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.setSpacingBefore(25);

          /*  PdfPCell cell1 = new PdfPCell(new Paragraph("Product", FontFactory.getFont("Tahoma", 9, Font.BOLD, new Color(0,0,0))));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setBackgroundColor(Color.lightGray );
			PdfPCell cell2 = new PdfPCell(new Paragraph("Quantity", FontFactory.getFont("Tahoma", 9, Font.BOLD, new Color(0,0,0))));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setBackgroundColor(Color.lightGray );
			PdfPCell cell3 = new PdfPCell(new Paragraph("Price", FontFactory.getFont("Tahoma", 9, Font.BOLD, new Color(0,0,0))));
			cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell3.setBackgroundColor(Color.lightGray );
			//PdfPCell cell4 = new PdfPCell(new Paragraph("Price", FontFactory.getFont("Tahoma", 9, Font.NORMAL, new Color(0,0,0))));
			//cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			//cell4.setBackgroundColor(Color.lightGray );

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
			//table.addCell(cell4);

			/*PdfPCell cell11 = new PdfPCell(new Paragraph(""+batterybrand+" - "+batterymodel+"\r\n\r\n\r\n\r\n\r\n\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell21 = new PdfPCell(new Paragraph("1", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell31 = new PdfPCell(new Paragraph("INR \t"+Strbatteryprice+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell31.setHorizontalAlignment(Element.ALIGN_CENTER);
			//PdfPCell cell41 = new PdfPCell(new Paragraph(""+Strbatteryprice+" \r\n\r\n\r\n "+Strbatteryvatprice+" \r\n\r\n\r\n "+price+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));

            table.addCell(cell11);
            table.addCell(cell21);
            table.addCell(cell31);*/
			
		
				
		/*	PdfPCell cell111 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 9, Font.BOLD, new Color(0,0,0))));
            cell111.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell111.setBackgroundColor(Color.lightGray );
			PdfPCell cell211 = new PdfPCell(new Paragraph("14.5% VAT", FontFactory.getFont("Tahoma", 9, Font.BOLD, new Color(0,0,0))));
            cell211.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell211.setBackgroundColor(Color.lightGray );
			PdfPCell cell311 = new PdfPCell(new Paragraph("INR \t"+Strbatteryvatprice+"", FontFactory.getFont("Tahoma", 9, Font.BOLD, new Color(0,0,0))));
			cell311.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell311.setBackgroundColor(Color.lightGray );
			//PdfPCell cell41 = new PdfPCell(new Paragraph(""+Strbatteryprice+" \r\n\r\n\r\n "+Strbatteryvatprice+" \r\n\r\n\r\n "+price+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));

            table.addCell(cell111);
            table.addCell(cell211);
            table.addCell(cell311);
			//table.addCell(cell41);

			PdfPCell cell1111 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 9, Font.BOLD, new Color(0,0,0))));
            cell1111.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1111.setBackgroundColor(Color.lightGray );
			PdfPCell cell2111 = new PdfPCell(new Paragraph("Total", FontFactory.getFont("Tahoma", 9, Font.BOLD, new Color(0,0,0))));
            cell2111.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell2111.setBackgroundColor(Color.lightGray );
			PdfPCell cell3111 = new PdfPCell(new Paragraph("INR \t"+price+"", FontFactory.getFont("Tahoma", 9, Font.BOLD, new Color(0,0,0))));
			cell3111.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell3111.setBackgroundColor(Color.lightGray );
			//PdfPCell cell41 = new PdfPCell(new Paragraph(""+Strbatteryprice+" \r\n\r\n\r\n "+Strbatteryvatprice+" \r\n\r\n\r\n "+price+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));

            table.addCell(cell1111);
            table.addCell(cell2111);
            table.addCell(cell3111);
			//table.addCell(cell41);*/
			
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
