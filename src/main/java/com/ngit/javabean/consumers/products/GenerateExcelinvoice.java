/*****************************************************
NGIT. Confidential.
@File Name		: GenerateExcel.java
@Description	: TO generate Excel Report
@Author			: Sai Krishna Daddala  
@Date			: 18-08-2009
@Reviewed By	: 
*******************************************************/


package com.ngit.javabean.consumers.products;

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

public class GenerateExcelinvoice extends HttpServlet 
{
	private String outfile;
	private WritableWorkbook workbook;
	Properties propsMOPConfig;
	private ServletContext context; 

	public GenerateExcelinvoice()
	{
	}
	public GenerateExcelinvoice(String ofn)
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
	public int writeToPdf(String strusername,String pincity,String verificationcode,String invoice,String batterybrand,String batterymodel,String price,String Strbatteryprice,String Strbatteryvatprice,String Strvatstring,String Strbatterylocalprice,String Strlocalstring,String strTempTextFile,String strCMSSERVERIP)
	{
		try
		{
			LogLevel.DEBUG(4,new Throwable(),"Inside writeToPdf");
			LogLevel.DEBUG(5,new Throwable(),"strTempTextFile in WriteToExcel: "+strTempTextFile);
			//LogLevel.DEBUG(5,new Throwable(),"strReportName in WriteToExcel: "+strReportTitle);
			LogLevel.DEBUG(5,new Throwable(),"strCMSSERVERIP: "+strCMSSERVERIP);

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

			// PdfPTable table = new PdfPTable(arrPDFHeader.size());
            // table.setWidthPercentage(100);  

			Image imghead = Image.getInstance("https://"+strCMSSERVERIP+"/bookbattery/images/batterywalelogo.png");
			imghead.setAbsolutePosition(50,780);
			imghead.scaleAbsolute(100, 30);
			document.add(imghead);	

			/*Image imgheadfot = Image.getInstance("https://"+strCMSSERVERIP+"/bookbattery/images/Asistmiletterpad.png");
			imgheadfot.setAbsolutePosition(210,35);
			imgheadfot.scaleAbsolute(170, 30);
			document.add(imgheadfot);	
			
			PdfContentByte cb1 = pdf.getDirectContent();
			cb1.setLineWidth(0.5f);
			cb1.moveTo(15, 75);
			cb1.lineTo(585, 75);
			cb1.stroke();		
				
			Image imghead1 = Image.getInstance("https://"+strCMSSERVERIP+"/bookbattery/images/combined_logo_new.png");
			imghead1.setAbsolutePosition(230,5);
			imghead1.scaleAbsolute(125,25);
			document.add(imghead1);*/
				
			
			Paragraph paragraph= (new Paragraph("www.bookbattery.com", FontFactory.getFont("Tahoma", 18, Font.NORMAL, new Color(0,206,209))));
			paragraph.setAlignment(Element.ALIGN_RIGHT);
			document.add(paragraph);

			Paragraph parag32 = (new Paragraph("Invoice No : "+invoice+"" ,FontFactory.getFont("Tahoma", 6, Font.UNDERLINE, new Color(0, 0, 0))));
			parag32.setAlignment(Element.ALIGN_CENTER);
			parag32.setSpacingBefore(16.2f);
			parag32.setSpacingAfter(16.2f);
			//parag32.setAlignment(Element.VALIGN_TOP);
			document.add(parag32);
			
			//Paragraph parag = (new Paragraph(new Date().toString(),FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0, 0, 0))));
			//parag.setAlignment(Element.ALIGN_RIGHT);
			//document.add(parag);

			//Paragraph parag1 = (new Paragraph(ReportDate,FontFactory.getFont("Tahoma", 6, Font.NORMAL, new Color(0, 0, 0))));
			//parag1.setAlignment(Element.ALIGN_RIGHT);
			//document.add(parag1);

			Paragraph parag1 = (new Paragraph("Customer Name : "+strusername+" \r\n Location : "+pincity+" \r\n Ord Ref Number :"+verificationcode+" \r\n Ordered date : "+dates+"" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag1.setAlignment(Element.ALIGN_LEFT);
			parag1.setSpacingAfter(10.2f);
			document.add(parag1);

			Paragraph paragraphtit= (new Paragraph("Tax Invoice & Delivery Challan", FontFactory.getFont("Tahoma", 12, Font.UNDERLINE, new Color(255,140,0))));
			paragraphtit.setSpacingBefore(2.2f);
			paragraphtit.setSpacingAfter(2.2f);
			paragraphtit.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraphtit);



			//Paragraph parag3 = (new Paragraph("Ord Ref Number :"+verificationcode+" \r\n Ordered date : "+dates+"" ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			//parag3.setAlignment(Element.ALIGN_LEFT);
			//document.add(parag3);

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
		PdfPTable table = new PdfPTable(4); // 3 columns.
		table.setWidthPercentage(90);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.setSpacingBefore(25);

            PdfPCell cell1 = new PdfPCell(new Paragraph("Product", FontFactory.getFont("Tahoma", 9, Font.BOLD, new Color(0,0,0))));
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell1.setBackgroundColor(Color.lightGray );
			PdfPCell cell2 = new PdfPCell(new Paragraph("Quantity", FontFactory.getFont("Tahoma", 9, Font.BOLD, new Color(0,0,0))));
            cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell2.setBackgroundColor(Color.lightGray );
			PdfPCell cell3 = new PdfPCell(new Paragraph("Price", FontFactory.getFont("Tahoma", 9, Font.BOLD, new Color(0,0,0))));
			cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell3.setBackgroundColor(Color.lightGray );
			PdfPCell cell4 = new PdfPCell(new Paragraph("Total Price", FontFactory.getFont("Tahoma", 9, Font.BOLD, new Color(0,0,0))));
			cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell4.setBackgroundColor(Color.lightGray );

            table.addCell(cell1);
            table.addCell(cell2);
            table.addCell(cell3);
			table.addCell(cell4);

			PdfPCell cell11 = new PdfPCell(new Paragraph(""+batterybrand+" - "+batterymodel+"\r\n\r\n\r\n\r\n\r\n\r\n", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell11.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell21 = new PdfPCell(new Paragraph("1", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
            cell21.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell31 = new PdfPCell(new Paragraph("INR \t"+Strbatteryprice+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell31.setHorizontalAlignment(Element.ALIGN_CENTER);
			PdfPCell cell41 = new PdfPCell(new Paragraph("INR \t"+Strbatteryprice+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));
			cell41.setHorizontalAlignment(Element.ALIGN_CENTER);
			//PdfPCell cell41 = new PdfPCell(new Paragraph(""+Strbatteryprice+" \r\n\r\n\r\n "+Strbatteryvatprice+" \r\n\r\n\r\n "+price+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));

            table.addCell(cell11);
            table.addCell(cell21);
            table.addCell(cell31);
			table.addCell(cell41);

			PdfPCell cell111 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 9, Font.BOLD, new Color(0,0,0))));
            cell111.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell111.setBackgroundColor(Color.lightGray );
			PdfPCell cell211 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 9, Font.BOLD, new Color(0,0,0))));
            cell211.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell211.setBackgroundColor(Color.lightGray );
			PdfPCell cell311 = new PdfPCell(new Paragraph(""+Strvatstring+"", FontFactory.getFont("Tahoma", 9, Font.BOLD, new Color(0,0,0))));
			cell311.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell311.setBackgroundColor(Color.lightGray );
			PdfPCell cell411 = new PdfPCell(new Paragraph("INR \t"+Strbatteryvatprice+"", FontFactory.getFont("Tahoma", 9, Font.BOLD, new Color(0,0,0))));
			cell411.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell411.setBackgroundColor(Color.lightGray );
			//PdfPCell cell41 = new PdfPCell(new Paragraph(""+Strbatteryprice+" \r\n\r\n\r\n "+Strbatteryvatprice+" \r\n\r\n\r\n "+price+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));

            table.addCell(cell111);
            table.addCell(cell211);
            table.addCell(cell311);
			table.addCell(cell411);

			if(Strbatterylocalprice.equals("0"))
			{
			}
			else
			{

			PdfPCell cell112 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 9, Font.BOLD, new Color(0,0,0))));
            cell112.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell112.setBackgroundColor(Color.lightGray );
			PdfPCell cell212 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 9, Font.BOLD, new Color(0,0,0))));
            cell212.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell212.setBackgroundColor(Color.lightGray );
			PdfPCell cell313 = new PdfPCell(new Paragraph(""+Strlocalstring+"", FontFactory.getFont("Tahoma", 9, Font.BOLD, new Color(0,0,0))));
			cell313.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell313.setBackgroundColor(Color.lightGray );
			PdfPCell cell414 = new PdfPCell(new Paragraph("INR \t"+Strbatterylocalprice+"", FontFactory.getFont("Tahoma", 9, Font.BOLD, new Color(0,0,0))));
			cell414.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell414.setBackgroundColor(Color.lightGray );
			//PdfPCell cell41 = new PdfPCell(new Paragraph(""+Strbatteryprice+" \r\n\r\n\r\n "+Strbatteryvatprice+" \r\n\r\n\r\n "+price+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));

            table.addCell(cell112);
            table.addCell(cell212);
            table.addCell(cell313);
			table.addCell(cell414);
			}

			PdfPCell cell1111 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 9, Font.BOLD, new Color(0,0,0))));
            cell1111.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell1111.setBackgroundColor(Color.lightGray );
			PdfPCell cell2111 = new PdfPCell(new Paragraph("", FontFactory.getFont("Tahoma", 9, Font.BOLD, new Color(0,0,0))));
            cell2111.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell2111.setBackgroundColor(Color.lightGray );
			PdfPCell cell3111 = new PdfPCell(new Paragraph("Total", FontFactory.getFont("Tahoma", 9, Font.BOLD, new Color(0,0,0))));
			cell3111.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell3111.setBackgroundColor(Color.lightGray );
			PdfPCell cell4111 = new PdfPCell(new Paragraph("INR \t"+price+"", FontFactory.getFont("Tahoma", 9, Font.BOLD, new Color(0,0,0))));
			cell4111.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell4111.setBackgroundColor(Color.lightGray );
			//PdfPCell cell41 = new PdfPCell(new Paragraph(""+Strbatteryprice+" \r\n\r\n\r\n "+Strbatteryvatprice+" \r\n\r\n\r\n "+price+"", FontFactory.getFont("Tahoma", 8, Font.NORMAL, new Color(0,0,0))));

            table.addCell(cell1111);
            table.addCell(cell2111);
            table.addCell(cell3111);
			table.addCell(cell4111);
			
			document.add(table);
			
			Paragraph parag21 = (new Paragraph("For \r\n Asistmi Solutions (P) Ltd. \r\n Mobile no: 9603467559, 9666300002." ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag21.setAlignment(Element.ALIGN_LEFT);
			parag21.setSpacingBefore(60.2f);
			document.add(parag21);
			
			Paragraph parag211 = (new Paragraph("\r\n Terms" ,FontFactory.getFont("Tahoma", 10, Font.BOLD, new Color(0, 0, 0))));
			parag211.setAlignment(Element.ALIGN_LEFT);
			parag211.setSpacingBefore(2.2f);
			document.add(parag211);

			Paragraph parag212 = (new Paragraph("\r\n 1. As per Battery(Management and Handling Rules 2000), return the bookbattery to the nearest Amaron franchisee(pitstop). Non compliance will attract severe action by Ministry of Environment and Forest. \r\n 2.Warranty Card per Company Norms. Please read warranty card for more details. \r\n 3.Goods once sold can't be taken back or exchanged " ,FontFactory.getFont("Tahoma", 10, Font.NORMAL, new Color(0, 0, 0))));
			parag212.setAlignment(Element.ALIGN_LEFT);
			parag212.setSpacingBefore(2.2f);
			document.add(parag212);
			
			
			
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
