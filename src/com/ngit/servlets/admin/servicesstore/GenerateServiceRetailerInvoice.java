/***********************************************************************		
	Asistmi Solutions Pvt.Ltd. 
	@File Name   : GenerateRetailerInvoice.java
	@Description : This Servlet is used to Generate and view retailer invoice
	@Date        : Feb 16th, 2016.
******************************************************************/ 
package com.ngit.servlets.admin.servicesstore; 
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.admin.mis.GenerateExcelService;
import java.io.IOException; 
import java.io.FileInputStream; 
import java.util.Properties; 
import java.util.Hashtable; 
import java.util.Vector;
import java.util.Date;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Random;
import java.io.*;
import javax.servlet.ServletConfig; 
import javax.servlet.ServletContext; 
import javax.servlet.ServletException; 
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse; 
import javax.servlet.http.HttpSession;
/*
 * @author Lavanya Chowdary.
 */
/* This class is to Admin Login. This class initializes the necessary connection pools and perform the transactions on the pools. */
public class GenerateServiceRetailerInvoice extends HttpServlet 
{
 	private ServletContext context; 
	QueryManager qm;
	static final long serialVersionUID=21;
	/*This init method initializes the necessary connection pools and perform the transactions on the pools from respective files. */
	public void init(ServletConfig config)throws ServletException
	{
		super.init(config); 
		context = getServletContext(); 
		try
		{
			String strMOPConfig = getInitParameter("paramMopConfig"); 
			Properties propsMOPConfig = new Properties(); 
			FileInputStream fin1      = new FileInputStream(new File(context.getRealPath(strMOPConfig))); 
			propsMOPConfig.load(fin1); 
			fin1.close(); 
			context.setAttribute("contextPropMOPConfig",propsMOPConfig); 
			//	initialize log
			String strLogLevel = (propsMOPConfig.getProperty("LogLevel")!=null)?propsMOPConfig.getProperty("LogLevel"):"1";
			if(strLogLevel.equals(""))
			strLogLevel = "1";
			String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?propsMOPConfig.getProperty("baseurldirectory"):"";
			String strLogFilePath = (propsMOPConfig.getProperty("LogFilePath")!=null)?propsMOPConfig.getProperty("LogFilePath"):"/home/ngit/tomcat/webapps/bookbattery/logs/";
			if(strLogFilePath.equals(""))
			strLogFilePath = "/home/ngit/tomcat/webapps/bookbattery/logs/";
			LogLevel.setLogLevel( strLogLevel, strLogFilePath, "bookbattery.log"); 
  		}
		catch(IOException ioe)
		{
			ioe.printStackTrace(); 
		}
		catch(Exception e)
		{
			e.printStackTrace(); 
		}
	}
	/* This doGet service method calls doPost service method to handle all the reqs and responses to perform Admin Login. */
 	public void doGet (HttpServletRequest req , HttpServletResponse res )throws IOException , ServletException
	{
		doPost(req, res);
	}
	/*This doPost service method handles all the requests and responses passing from doGet service method to perform Admin Login.*/
	public void doPost( HttpServletRequest req , HttpServletResponse res )throws IOException , ServletException
	{
		res.setContentType("text/html;charset=UTF-8");
        HttpSession session=req.getSession(true);
 		session=req.getSession(true);
		String struserName=(String)session.getAttribute("sesBatteryAdminName"); 
		LogLevel.DEBUG(5,new Throwable(),"struserName :"+struserName );
		Properties propsMOPConfig = (Properties)context.getAttribute("contextPropMOPConfig"); 		
		 String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?(propsMOPConfig.getProperty("baseurldirectory")):"";

		if(propsMOPConfig==null)
		{
			LogLevel.DEBUG(1,new Throwable(),"Properties not loaded. So reloading" );
			try
			{
				String strMOPConfig = "/bookbattery/properties/bookbatteryconfig.properties"; 
				propsMOPConfig = new Properties(); 
				FileInputStream fin1      = new FileInputStream(new File(context.getRealPath(strMOPConfig))); 
				propsMOPConfig.load(fin1); 
				fin1.close(); 
				context.setAttribute("contextPropMOPConfig",propsMOPConfig); 
			}
 			catch(Exception e)
			{
				LogLevel.ERROR(1, e ,"Permanent Problem So Giving UP: Not able to load properties" );
				e.printStackTrace();
				return;
			}
		}
		qm = QueryManager.getInstance(propsMOPConfig) ;
		String strWhatToDo = (req.getParameter("hidWhatToDo")!=null)?req.getParameter("hidWhatToDo"):""; 
		/*This method is used to get retailers*/
		if(strWhatToDo.equalsIgnoreCase("getretailers"))
		  {
			try
			  {
				String strSqlQry ="select distinct(retailer_name) from service_order_details where order_status='Customer Contacted' and order_reasons='installed' order by retailer_name asc";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

				Vector RetailerVector=qm.executeQuery(strSqlQry);
				LogLevel.DEBUG(5,new Throwable(),"RetailerVector:"+RetailerVector );
				if(RetailerVector.isEmpty())
				{
					LogLevel.DEBUG(1,new Throwable(),"Failed to get Retailers ");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsgsity", "<font color='#00dd00' class='vrb10'>Failed to get Retailers! </font> ");
					res.sendRedirect("/bookbattery/jsp/admin/servicesstore/mis/generateretailerinvoice.jsp");
					return;
				}
				else
				{
					LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched Retailers");
					session.setAttribute("sesretailervector", RetailerVector);
					session.setAttribute("sesErrorMsgsity","<font color='#CC0000' class='vrb10'>Succesfully Fetched Retailers.</font> ");
					res.sendRedirect("/bookbattery/jsp/admin/servicesstore/mis/generateretailerinvoice.jsp");
					return;
				}
				}
			catch(IOException ioe)
				{
					ioe.printStackTrace();
					session.setAttribute("sesErrorMsgsst",	"Generall Error...Please Try Again" );
					res.sendRedirect("/bookbattery/jsp/admin/servicesstore/mis/generateretailerinvoice.jsp");
				}
			catch(Exception e)
				{
					e.printStackTrace();
					session.setAttribute("sesErrorMsgsst",	"General Error...Please Try Again" );
					res.sendRedirect("/bookbattery/jsp/admin/servicesstore/mis/generateretailerinvoice.jsp");
				}
		     }
		else if(strWhatToDo.equalsIgnoreCase("getretailers"))
		  {
			try
			  {
				String strSqlQry ="select distinct(retailer_name) from service_order_details where order_status='Customer Contacted' and order_reasons='installed' order by retailer_name asc";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

				Vector RetailerVector=qm.executeQuery(strSqlQry);
				LogLevel.DEBUG(5,new Throwable(),"RetailerVector:"+RetailerVector );
				if(RetailerVector.isEmpty())
				{
					LogLevel.DEBUG(1,new Throwable(),"Failed to get Retailers ");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsgsity", "<font color='#00dd00' class='vrb10'>Failed to get Retailers! </font> ");
					res.sendRedirect("/bookbattery/jsp/admin/servicesstore/mis/invertergenerateretailerinvoice.jsp");
					return;
				}
				else
				{
					LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched Retailers");
					session.setAttribute("sesretailervector", RetailerVector);
					session.setAttribute("sesErrorMsgsity","<font color='#CC0000' class='vrb10'>Succesfully Fetched Retailers.</font> ");
					res.sendRedirect("/bookbattery/jsp/admin/servicesstore/mis/invertergenerateretailerinvoice.jsp");
					return;
				}
				}
			catch(IOException ioe)
				{
					ioe.printStackTrace();
					session.setAttribute("sesErrorMsgsst",	"Generall Error...Please Try Again" );
					res.sendRedirect("/bookbattery/jsp/admin/servicesstore/mis/invertergenerateretailerinvoice.jsp");
				}
			catch(Exception e)
				{
					e.printStackTrace();
					session.setAttribute("sesErrorMsgsst",	"General Error...Please Try Again" );
					res.sendRedirect("/bookbattery/jsp/admin/servicesstore/mis/invertergenerateretailerinvoice.jsp");
				}
		     }
		else if(strWhatToDo.equalsIgnoreCase("Generateinvoicereport"))
		  {
			String strPDFFilePath1 = (propsMOPConfig.getProperty("PDFFilePath1")!=null)?(propsMOPConfig.getProperty("PDFFilePath1")).trim():"";
			LogLevel.DEBUG(5, new Throwable(), "strPDFFilePath1:" + strPDFFilePath1);

			String strCMSServerIP = (propsMOPConfig.getProperty("CMS_SERVER_IP")!=null)?(propsMOPConfig.getProperty("CMS_SERVER_IP")).trim():"bookbattery.com";
			LogLevel.DEBUG(5, new Throwable(), "strCMSServerIP:" + strCMSServerIP);

			String strRelativePath = (propsMOPConfig.getProperty("RelativePath")!=null)?(propsMOPConfig.getProperty("RelativePath")).trim():"";
			LogLevel.DEBUG(5, new Throwable(), "strRelativePath:" + strRelativePath);

			String complanypecent = (propsMOPConfig.getProperty("company_commision")!=null)?(propsMOPConfig.getProperty("company_commision")).trim():"";
			LogLevel.DEBUG(5, new Throwable(), "complanypecent:" + complanypecent);

			String servicetax = (propsMOPConfig.getProperty("servicetax")!=null)?(propsMOPConfig.getProperty("servicetax")).trim():"";
			LogLevel.DEBUG(5, new Throwable(), "servicetax:" + servicetax);

			String retailers  = (req.getParameter("retailer")!=null)?req.getParameter("retailer").trim():"";
			LogLevel.DEBUG(5,new Throwable(),"retailers :"+retailers );	

			String months  = (req.getParameter("months")!=null)?req.getParameter("months").trim():"";
			LogLevel.DEBUG(5,new Throwable(),"months :"+months );	

			String txtFromDate  = (req.getParameter("txtFromDate")!=null)?req.getParameter("txtFromDate").trim():"";
			String txtToDate  = (req.getParameter("txtToDate")!=null)?req.getParameter("txtToDate").trim():"";
			String key = "services";
			String retailerid ="";

			ServletOutputStream out=res.getOutputStream();
			try
			  { 
			    int reslt = 0;
				String strConditions="";
				String strConditions1="";
				String strQuery="";

				SimpleDateFormat sdfDate=new SimpleDateFormat("dd-MM-yyyy");
				Date fromDate=sdfDate.parse(txtFromDate);  
				Date toDate=sdfDate.parse(txtToDate); 

				SimpleDateFormat sdfString=new SimpleDateFormat("yyyy-MM-dd");
				String strFromDate=sdfString.format(fromDate); 
				LogLevel.DEBUG(5, new Throwable(), "strFromDate :"+ strFromDate);

				String strToDate=sdfString.format(toDate); 
				LogLevel.DEBUG(5, new Throwable(), "strToDate :"+ strToDate);

				if(strConditions.equals(""))
					strConditions=" date(updated_date) between '"+strFromDate+"' and '"+strToDate+"'";
				else
					strConditions=strConditions+" and date(updated_date) between '"+strFromDate+"' and '"+strToDate+"'";
				
				if(strConditions1.equals(""))
					strConditions1=" date(a.updated_date) between '"+strFromDate+"' and '"+strToDate+"'";
				else
					strConditions1=strConditions1+" and date(a.updated_date) between '"+strFromDate+"' and '"+strToDate+"'";

				if(retailers.equals("ALL"))
				{
				strQuery = "select distinct(retailer_name),city,services_type,services_package from service_order_details where "+strConditions+" and order_status='Customer Contacted' and order_reasons='installed' ";
				LogLevel.DEBUG(5,new Throwable(),"strQuery :"+strQuery );
				}
				else
				{
				strQuery = "select distinct(retailer_name),city,services_type,services_package from service_order_details where "+strConditions+" and order_status='Customer Contacted' and order_reasons='installed' and retailer_name='"+retailers+"'";
				LogLevel.DEBUG(5,new Throwable(),"strQuery :"+strQuery );
				}
				Vector serviceorddetails=qm.executeQuery(strQuery);
				LogLevel.DEBUG(5,new Throwable(),"serviceorddetails :"+serviceorddetails );
				if(serviceorddetails.isEmpty())
				 { 
					out.println("<p align='center' valign='top' class='insidecontent'><table valign='top' width='500' cellspacing='0' border='0' align='center' bgcolor='#FFFFFF' cellpadding='0'><tr><td><table valign='top' width='500' bgcolor='#FFFFFF' border='0'><tr><td class='insidecontent' align='center'>No Record found based on selection!</td></tr></table></td></tr></table><a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
				 }
				 else
				 {
				/* Starts to generate retailers invoice */
				for ( int j=0; j<serviceorddetails.size(); j++)
				{
				Hashtable ht2=(Hashtable)serviceorddetails.get(j);
				String retailer=String.valueOf(ht2.get("retailer_name"));
				String city=String.valueOf(ht2.get("city"));
				String servicestype=String.valueOf(ht2.get("services_type"));
				String servicespackage=String.valueOf(ht2.get("services_package"));
				String servicepercentage="";

				Calendar cal = Calendar.getInstance();
				int currentMonth = cal.get(Calendar.MONTH) + 1;
				int currentYear = cal.get(Calendar.YEAR);
				
				if(servicestype.equals("Tyre Services Wheel Balancing and Alignment"))
				{
					servicepercentage="0.07";
				}				
				
				else if(servicestype.equals("Car Battery Health Checkup"))/*Modified by bharath as said by priyanka on 19/4/2017 based on comission for different services*/
				{
					servicepercentage="0.5";
				}				
				else
				{
					servicepercentage="0.1";					
					
				}


				String StrQryPrice = "select a.services_type,a.services_package,a.service_price_discount,CAST(round("+servicepercentage+"*a.service_price_discount) AS SIGNED) as comission,CAST(round("+servicepercentage+"* a.service_price_discount)*(count(a.services_package)) AS SIGNED) as ourcommision from service_order_details a,  percentage c where "+strConditions1+" and a.order_status='Customer Contacted' and a.order_reasons='installed' and a.retailer_name='"+retailer+"'  and a.city='"+city+"' and c.city=a.city group by a.services_package,a.service_price_discount";
				LogLevel.DEBUG(5, new Throwable(), "StrQryPrice :" + StrQryPrice);

				Vector RetInoiceVector=qm.executeQuery(StrQryPrice);
				LogLevel.DEBUG(5,new Throwable(),"RetInoiceVector:"+RetInoiceVector );					

				String Strqry1 = "select CAST(round("+servicepercentage+"*a.service_price_discount)*(count(a.services_package)) AS SIGNED) as grandtotal from service_order_details a, percentage c where "+strConditions1+" and a.order_status='Customer Contacted' and a.order_reasons='installed' and a.retailer_name='"+retailer+"' and   a.city='"+city+"' and c.city=a.city group by a.services_package,a.service_price_discount";
				LogLevel.DEBUG(5, new Throwable(), "Strqry1 :" + Strqry1);
				
				ArrayList htav=new ArrayList();
				htav=qm.getField(Strqry1);

				String serviceids="";
				for(int i=0;i<htav.size();i++)
				{
				if(serviceids.equals(""))
				serviceids=htav.get(i).toString();
				else
				serviceids=serviceids+"+"+htav.get(i).toString();
				}
				LogLevel.DEBUG(5,new Throwable(),"serviceids:"+serviceids);

				String SqlQrysum = "select CAST(sum("+serviceids+") AS SIGNED) as grandtotal";
				LogLevel.DEBUG(5,new Throwable(),"SqlQrysum:"+SqlQrysum);
				
				Hashtable htgrandtot = qm.getRow(SqlQrysum); 
				String strgrandtot=String.valueOf(htgrandtot.get("grandtotal"));
				LogLevel.DEBUG(5, new Throwable(), "strgrandtot :" + strgrandtot);

				String StrQryservicetax = "select CAST(round(("+strgrandtot+"-(("+strgrandtot+"/"+servicetax+")*100))) AS SIGNED) as servicetaxyprice";
				LogLevel.DEBUG(5, new Throwable(), "StrQryservicetax :" + StrQryservicetax);

				Hashtable htservicetax = qm.getRow(StrQryservicetax); 
				String strservicetax=String.valueOf(htservicetax.get("servicetaxyprice"));
				LogLevel.DEBUG(5, new Throwable(), "strservicetax :" + strservicetax);

				String StrQrytottax = "select CAST(round(("+strgrandtot+"/"+servicetax+")*100) AS SIGNED) as tottaxdedaction";
				LogLevel.DEBUG(5, new Throwable(), "StrQrytottax :" + StrQrytottax);

				Hashtable httottax = qm.getRow(StrQrytottax); 
				String strtottax=String.valueOf(httottax.get("tottaxdedaction"));
				LogLevel.DEBUG(5, new Throwable(), "strtottax :" + strtottax);
				
				
				String strSqlQry4 = "select gstid from gstdetailstable where retailername='"+retailer+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry4 :"+strSqlQry4 );

				Vector RetailerVector=qm.executeQuery(strSqlQry4);
				LogLevel.DEBUG(5,new Throwable(),"RetailerVector:"+RetailerVector);
				
				if(RetailerVector==null || RetailerVector.size() == 0)
				{
					
					retailerid = "";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry4 :"+strSqlQry4 );
				}
				else
				{
					for ( int k=0; k<RetailerVector.size() ; k++)
					{
						Hashtable htRetid=(Hashtable)RetailerVector.get(k);
						retailerid=String.valueOf(htRetid.get("gstid"));
						LogLevel.DEBUG(5,new Throwable(),"strSqlQry4 :"+strSqlQry4 );
					}
				}
				
			
				/*following code is for generating the random number */
				Random generator = new Random();   
				generator.setSeed(System.currentTimeMillis());   
				int num = generator.nextInt(99999) + 99999;   
				if (num < 100000 || num > 999999)
				{   
				num = generator.nextInt(99999) + 99999;   
				if (num < 100000 || num > 999999)
				{   
				}   
				}   
				String verificationcode = ""; 
				String verificationcode1 = Integer.toString(num);
				verificationcode ="ASSIST-AMR-BT"+verificationcode1+"";
				LogLevel.DEBUG(5, new Throwable(), "verificationcode :" + verificationcode);

				
						ArrayList arrExcelHeader1 = new ArrayList();
						arrExcelHeader1.add("Services Type");
						arrExcelHeader1.add("Services Package");
						arrExcelHeader1.add("SRP");
						arrExcelHeader1.add("Commission");
						arrExcelHeader1.add("Total Comm"); 

						ArrayList dbHeaders2 = new ArrayList();
						dbHeaders2.add("services_type");
						dbHeaders2.add("services_package");
						dbHeaders2.add("service_price_discount");
						dbHeaders2.add("comission");
						dbHeaders2.add("ourcommision");


				String excelName = ""+retailer+"-ASPL-Invoice-"+verificationcode+".pdf";
				LogLevel.DEBUG(5, new Throwable(), "excelName :" + excelName);
				String strPDFFilePath = strPDFFilePath1+ "/" +currentYear+ "/" +months+ "/" +"services"+ "/" +"invoice"+ "/" +city;
				LogLevel.DEBUG(5, new Throwable(), "strPDFFilePath :" + strPDFFilePath);
				File excelUploadPathCreate = new File(strPDFFilePath);
				LogLevel.DEBUG(5, new Throwable(), "excelUploadPathCreate :" + excelUploadPathCreate);
				if(excelUploadPathCreate.mkdirs())
				{
				LogLevel.DEBUG(3,new Throwable()," PDF for invoice	Name directory created :  "	+ excelUploadPathCreate);
				}

				String excelFile = excelUploadPathCreate+"/"+excelName;
				LogLevel.DEBUG(5, new Throwable(), "excelFile :" + excelFile);
				File file=new File(excelFile);
				if(!file.exists()) 
				file.createNewFile(); 
				FileWriter fw=new FileWriter(file);	
				GenerateExcelService generateExcel	= new GenerateExcelService(); 
				generateExcel.writeToPdf1(retailer,city,verificationcode,txtFromDate,txtToDate,arrExcelHeader1,dbHeaders2,RetInoiceVector,strgrandtot,strservicetax,strtottax,excelFile,strCMSServerIP,key,retailerid);
				
				String strPdfURL="http://"+strCMSServerIP+"/bookbattery/userdata/billing/salesorders/"+currentYear+"/"+months+"/"+"services"+"/"+"invoice"+"/"+city+"/"+excelName;
				LogLevel.DEBUG(5, new Throwable(), "strPdfURL : " + strPdfURL);
				/* Ends to generate retailers invoice */

				/* Starts to generate retailers orders summary */
				String Strquerys = "select order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailid,city,services_type,services_package,service_price_mrp,pincode,pdfurl,veh_name,veh_model,area,service_price_discount,order_status,creation_date from service_order_details where "+strConditions+" and order_status='Customer Contacted' and order_reasons='installed' and retailer_name='"+retailer+"' ";
				LogLevel.DEBUG(5, new Throwable(), "Strquerys : " + Strquerys);

				Vector dailyVectorwithcat=qm.executeQuery(Strquerys);
				LogLevel.DEBUG(5,new Throwable(),"dailyVectorwithcat:"+dailyVectorwithcat );
				
				String ReportDate = "Report Duration : " +fromDate +  " To "  +toDate;
				String Rettitle = retailer+" - Order Summary"; 
				String excelName1 = ""+Rettitle+".pdf";

				ArrayList arrExcelHeaderwithcat = new ArrayList();
				arrExcelHeaderwithcat.add("Order No");
				arrExcelHeaderwithcat.add("Customer Name");
				arrExcelHeaderwithcat.add("Customer Email");
				arrExcelHeaderwithcat.add("Customer Mobile");
				arrExcelHeaderwithcat.add("Retailer Name");
				arrExcelHeaderwithcat.add("Retailer Mobile");
				arrExcelHeaderwithcat.add("Services Type");
				arrExcelHeaderwithcat.add("Services Package");
				arrExcelHeaderwithcat.add("City");
				arrExcelHeaderwithcat.add("Price");
				arrExcelHeaderwithcat.add("Discount Price");
				arrExcelHeaderwithcat.add("Order Status");
				arrExcelHeaderwithcat.add("Date");
				arrExcelHeaderwithcat.add("Postponed Date");

				ArrayList dbHeaderswithcat = new ArrayList();
				dbHeaderswithcat.add("order_number");
				dbHeaderswithcat.add("consumer_name");
				dbHeaderswithcat.add("consumer_emailid");
				dbHeaderswithcat.add("consumer_mobnumber");
				dbHeaderswithcat.add("retailer_name");
				dbHeaderswithcat.add("retailer_mobilnumber");
				dbHeaderswithcat.add("services_type");
				dbHeaderswithcat.add("services_package"); 
				dbHeaderswithcat.add("city");
				dbHeaderswithcat.add("service_price_mrp");
				dbHeaderswithcat.add("service_price_discount");
				dbHeaderswithcat.add("order_status");
				dbHeaderswithcat.add("creation_date");
				dbHeaderswithcat.add("postpone_date");

				
				String strPDFFilePath12 = strPDFFilePath1+ "/" +currentYear+ "/" +months+ "/" +"services"+ "/" +"orderedsummary"+ "/" +city;
				LogLevel.DEBUG(5, new Throwable(), "strPDFFilePath12 :" + strPDFFilePath12);
				File excelUploadPathCreate1 = new File(strPDFFilePath12);
				LogLevel.DEBUG(5, new Throwable(), "excelUploadPathCreate1 :" + excelUploadPathCreate1);
				if(excelUploadPathCreate1.mkdirs())
				{
				LogLevel.DEBUG(3,new Throwable()," PDF for invoice	Name directory created :  "	+ excelUploadPathCreate1);
				}

				String excelFile1 = excelUploadPathCreate1+"/"+excelName1;
				LogLevel.DEBUG(5, new Throwable(), "excelFile1 :" + excelFile1);
				File file1=new File(excelFile1); 
				if(!file1.exists()) 
				file1.createNewFile(); 
				FileWriter fw1=new FileWriter(file1);
				GenerateExcelService generateExcel1	= new GenerateExcelService();
				generateExcel1.writeToPdf3(arrExcelHeaderwithcat,dbHeaderswithcat,dailyVectorwithcat,excelFile1,strCMSServerIP,Rettitle,ReportDate);

				String strsalesurl="http://"+strCMSServerIP+"/bookbattery/userdata/billing/salesorders/"+currentYear+"/"+months+"/"+"services"+"/"+"orderedsummary"+"/"+city+"/"+excelName1;
				LogLevel.DEBUG(5, new Throwable(), "strsalesurl : " + strsalesurl);
				/* Ends to generate retailers orders summary */
			
				String strSqlQry = "insert into retailer_invoice(ret_inv_id,invoice_number,retailer_name,from_date,to_date,ret_pdf_url,salesurl,invoice_type,creation_date)values(NULL,'"+verificationcode+"','"+retailer+"','"+strFromDate+"','"+strToDate+"','"+strPdfURL+"','"+strsalesurl+"','services',now())";
				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
				reslt = qm.executeUpdate(strSqlQry);
			
			}
				}
			if(reslt >0)
				{
				out.println("<p align='center' valign='top' class='insidecontent'><table valign='top' width='500' cellspacing='0' border='0' align='center' bgcolor='#FFFFFF' cellpadding='0'><tr><td><table valign='top' width='500' bgcolor='#FFFFFF' border='0'><tr><td class='insidecontent' align='center'>Successfully created retailers Invoices!</td></tr></table></td></tr></table><a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
				}
			   else
				{
				out.println("<p align='center' valign='top' class='insidecontent'><table valign='top' width='500' cellspacing='0' border='0' align='center' bgcolor='#FFFFFF' cellpadding='0'><tr><td><table valign='top' width='500' bgcolor='#FFFFFF' border='0'><tr><td class='insidecontent' align='center'>Failed to create retailers invoices!</td></tr></table></td></tr></table><a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");				
				}
			   
			  }
			  catch(Exception e)
				{
					e.printStackTrace();
					session.setAttribute("sesErrorMsgsst",	"General Error...Please Try Again" );
					res.sendRedirect("/bookbattery/jsp/admin/servicesstore/mis/generateretailerinvoice.jsp");
				}
		    }
		
		else if(strWhatToDo.equalsIgnoreCase("getcities"))
		  {
			String keyword  = (req.getParameter("keyword")!=null)?req.getParameter("keyword"):"";
			LogLevel.DEBUG(5,new Throwable(),"keyword :"+keyword );
			try
			  {
				String strSqlQry ="select distinct(location) from location_area order by location asc";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

				Vector CityVector=qm.executeQuery(strSqlQry);
				LogLevel.DEBUG(5,new Throwable(),"CityVector:"+CityVector );
				if(CityVector.isEmpty())
				{
					LogLevel.DEBUG(1,new Throwable(),"Failed to get city ");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsgsst", "<font color='#00dd00' class='vrb10'>Failed to get city! </font> ");
					res.sendRedirect("/bookbattery/jsp/admin/servicesstore/percentage/retailerpercent.jsp");
					return;
				}
				else
				{
					if(keyword.equals("editpercent"))
					{
					LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched city");
					session.setAttribute("sescityvector", CityVector);
					session.setAttribute("sesErrorMsgsst","<font color='#CC0000' class='vrb10'>Succesfully Fetched city.</font> ");
					res.sendRedirect("/bookbattery/jsp/admin/servicesstore/percentage/editretailerpercent.jsp?keyword="+keyword);
					return;
					}
					else if(keyword.equals("cuspercent"))
					{
					LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched city");
					session.setAttribute("sescityvector", CityVector);
					session.setAttribute("sesErrorMsgsst","<font color='#CC0000' class='vrb10'>Succesfully Fetched city.</font> ");
					res.sendRedirect("/bookbattery/jsp/admin/servicesstore/percentage/cuspercent.jsp?keyword="+keyword);
					return;
					}
					else if(keyword.equals("editcuspercent"))
					{
					LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched city");
					session.setAttribute("sescityvector", CityVector);
					session.setAttribute("sesErrorMsgsst","<font color='#CC0000' class='vrb10'>Succesfully Fetched city.</font> ");
					res.sendRedirect("/bookbattery/jsp/admin/servicesstore/percentage/editcuspercent.jsp?keyword="+keyword);
					return;
					}
					else
					{
					LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched city");
					session.setAttribute("sescityvector", CityVector);
					session.setAttribute("sesErrorMsgsst","<font color='#CC0000' class='vrb10'>Succesfully Fetched city.</font> ");
					res.sendRedirect("/bookbattery/jsp/admin/servicesstore/percentage/retailerpercent.jsp");
					return;
					}
				}
				}
			catch(IOException ioe)
				{
					ioe.printStackTrace();
					session.setAttribute("sesErrorMsgsst",	"Generall Error...Please Try Again" );
					res.sendRedirect("/bookbattery/jsp/admin/servicesstore/percentage/retailerpercent.jsp");
				}
			catch(Exception e)
				{
					e.printStackTrace();
					session.setAttribute("sesErrorMsgsst",	"General Error...Please Try Again" );
					res.sendRedirect("/bookbattery/jsp/admin/servicesstore/percentage/retailerpercent.jsp");
				}
		     }
		else if(strWhatToDo.equalsIgnoreCase("insertretpercent"))
			{
			String city = (req.getParameter("city") != null)? (req.getParameter("city")) : ""; 
			String srptax = (req.getParameter("srptax") != null)? (req.getParameter("srptax")) : ""; 
			
			int result = 0;
			try
			{
			String StrSqlQery1 = "select city from percentage where city='"+city+"'";
			LogLevel.DEBUG(5,new Throwable(),"StrSqlQery1 :"+StrSqlQery1);

			Hashtable htapp = qm.getRow(StrSqlQery1);
			String city1=(String)htapp.get("city");
			
			if(htapp.isEmpty())
			{ 
			String strSqlQry = "insert into percentage(percent_id,city,city_percentage,creation_date) values(NULL,'"+city+"','"+srptax+"',now())";

			result=qm.executeUpdate(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"result :"+result );

				if(result < 0)
				{
					LogLevel.DEBUG(1,new Throwable(),"Failed to insert Percentage details!");
					session.setAttribute("priority","1");
					session.setAttribute("sespercentErrorMsg", "<font color='#ff3333' class='vrb10'>Failed to insert Percentage details! </font> ");
					res.sendRedirect("/bookbattery/jsp/admin/servicesstore/percentage/retailerpercent.jsp");
					return;
				}
				else
				{
					LogLevel.DEBUG(1,new Throwable(),"Successfully inserted Percentage details! ");
					session.setAttribute("priority","1");
					session.setAttribute("sespercentErrorMsg", "<font color='#000000' class='vrb10'>Successfully inserted Percentage details! </font> ");
					res.sendRedirect("/bookbattery/jsp/admin/servicesstore/percentage/retailerpercent.jsp");
					return;
					
				}
			}
			else
			{
				LogLevel.DEBUG(1,new Throwable(),"Successfully inserted services price details! ");
				session.setAttribute("priority","1");
				session.setAttribute("sespercentErrorMsg", "<font color='#000000' class='vrb10'>Already added Percentage for selected city.Please update...! </font> ");
				res.sendRedirect("/bookbattery/jsp/admin/servicesstore/percentage/retailerpercent.jsp");
				return;
			}
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
				session.setAttribute("sespercentErrorMsg",	"Generall Error...Please Try Again" );
				res.sendRedirect("/bookbattery/jsp/admin/servicesstore/percentage/retailerpercent.jsp");
			}
			catch(Exception e)
			{
				e.printStackTrace();
				session.setAttribute("sespercentErrorMsg",	"General Error...Please Try Again" );
				res.sendRedirect("/bookbattery/jsp/admin/servicesstore/percentage/retailerpercent.jsp");
			}
		}
		else if(strWhatToDo.equalsIgnoreCase("getpercentoupdate"))
		  {
			String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
			LogLevel.DEBUG(5,new Throwable(),"city :"+city );

			ServletOutputStream out=res.getOutputStream();
			try
			  {  
				/* @strSqlQry3 : carries the query to Insert the percentage details into percentage table.*/
				String strSqlQry4 = "select percent_id,city_percentage from percentage where city='"+city+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry4 :"+strSqlQry4 );

				Vector invoicepercente=qm.executeQuery(strSqlQry4);
				LogLevel.DEBUG(5,new Throwable(),"invoicepercente:"+invoicepercente);
				String strRes="<table  align='center' width='100%' border='0'>";
							
				if(invoicepercente==null || invoicepercente.size() == 0)
				  {
					out.println("<p align='center' class='insidecontent'>No Percentages are Avaliable based on selection!<a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
					return;
				  }
				 else
				  {
				   for(int j=0; j<invoicepercente.size();j++)
					 {
						Hashtable ht3=(Hashtable)invoicepercente.get(j);
						String perid=String.valueOf(ht3.get("percent_id"));
						String strsrp=String.valueOf(ht3.get("city_percentage"));
						
				
						
						strRes=strRes+"<tr><td align='right' class='insidecontent' width='42%'>SRP</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='srptax' value='"+strsrp+"' size='30' maxlength='7'></td></tr>";
				
						strRes=strRes+"<tr><td align='right' width='42%'></td><td align='left'><input type='button'  onclick=\"funToUpdatpercent('"+perid+"');\" value='Update' class='smallbutton'></td>";
						
					   }
					}
					strRes=strRes+"</table>";							
					out.println(strRes);
					return;				
				}
			   catch(IOException ioe)
			    {
					LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
					ioe.printStackTrace();
					session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
					res.sendRedirect("/bookbattery/jsp/admin/servicesstore/percentage/editretailerpercent.jsp");
			    }
			   catch(Exception e)
			    {
					LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
					res.sendRedirect("/bookbattery/jsp/admin/servicesstore/percentage/editretailerpercent.jsp");
			    }
		  }
		  else if(strWhatToDo.equalsIgnoreCase("editpercentage"))
		{
			String srptax = (req.getParameter("srptax")!=null)?(req.getParameter("srptax")):"";
			LogLevel.DEBUG(5,new Throwable(),"srptax :"+srptax );

			String perid = (req.getParameter("perid")!=null)?(req.getParameter("perid")):"";
			LogLevel.DEBUG(5,new Throwable(),"perid :"+perid );

			String keyword = (req.getParameter("keyword")!=null)?(req.getParameter("keyword")):"";
			LogLevel.DEBUG(5,new Throwable(),"keyword :"+keyword );
			int reslt=0;
			try
			{
				/*Query to get the Admin User name and Password*/
 				String strSqlQry = "update percentage set city_percentage='"+srptax+"' where percent_id='"+perid+"'";
				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
				
				reslt = qm.executeUpdate(strSqlQry);
		
				if(reslt <0)
				{
						LogLevel.DEBUG(1,new Throwable()," Failed to update percentage details! ");
						session.setAttribute("priority","1");
						session.setAttribute("sesleditperceErrorMsg", "<font color='#FF3333' class='top1'>Failed to update percentage price! </font> ");
						res.sendRedirect("/bookbattery/jsp/admin/servicesstore/percentage/editretailerpercent.jsp?keyword="+keyword);
						return;
					}
				   else
				    {
						LogLevel.DEBUG(1,new Throwable(),"Successfully updated percentage details! ");
						session.setAttribute("sesleditperceErrorMsg", "<font color='#000000' class='top1'>Successfully Updated percentage details.!</font>");
						res.sendRedirect("/bookbattery/jsp/admin/servicesstore/percentage/editretailerpercent.jsp?keyword="+keyword);
				    }
			     }
			    catch(IOException ioe)
			     {
						LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
						ioe.printStackTrace();
						session.setAttribute("sesleditperceErrorMsg",	"Generall Error...Please Try Again" );
						res.sendRedirect("/bookbattery/jsp/admin/servicesstore/percentage/editretailerpercent.jsp?keyword="+keyword);
			     }
			    catch(Exception e)
			     {
						LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
						e.printStackTrace();
						session.setAttribute("sesleditperceErrorMsg",	"General Error...Please Try Again" );
						res.sendRedirect("/bookbattery/jsp/admin/servicesstore/percentage/editretailerpercent.jsp?keyword="+keyword);
			     }
		}
		else if(strWhatToDo.equalsIgnoreCase("insertcuspercent"))
			{
			String city = (req.getParameter("city") != null)? (req.getParameter("city")) : ""; 
			String citytax = (req.getParameter("citytax") != null)? (req.getParameter("citytax")) : ""; 
			String citylocaltax = (req.getParameter("citylocaltax") != null)? (req.getParameter("citylocaltax")) : ""; 
			String keyword = (req.getParameter("keyword") != null)? (req.getParameter("keyword")) : ""; 
			
			int result = 0;
			try
			{
			String StrSqlQery1 = "select city from customer_percentage where city='"+city+"'";
			LogLevel.DEBUG(5,new Throwable(),"StrSqlQery1 :"+StrSqlQery1);

			Hashtable htapp = qm.getRow(StrSqlQery1);
			String city1=(String)htapp.get("city");
			
			if(htapp.isEmpty())
			{ 
			String strSqlQry = "insert into customer_percentage(cus_percent_id,city,city_percentage,city_local_percentage,creation_date) values(NULL,'"+city+"','"+citytax+"','"+citylocaltax+"',now())";

			result=qm.executeUpdate(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"result :"+result );

				if(result < 0)
				{
					LogLevel.DEBUG(1,new Throwable(),"Failed to insert customer invoice tax details!");
					session.setAttribute("priority","1");
					session.setAttribute("sespercentErrorMsg", "<font color='#ff3333' class='vrb10'>Failed to insert customer invoice tax details! </font> ");
					res.sendRedirect("/bookbattery/jsp/admin/servicesstore/percentage/cuspercent.jsp?keyword="+keyword);
					return;
				}
				else
				{
					LogLevel.DEBUG(1,new Throwable(),"Successfully inserted customer invoice tax details! ");
					session.setAttribute("priority","1");
					session.setAttribute("sespercentErrorMsg", "<font color='#000000' class='vrb10'>Successfully inserted customer invoice tax details! </font> ");
					res.sendRedirect("/bookbattery/jsp/admin/servicesstore/percentage/cuspercent.jsp?keyword="+keyword);
					return;
					
				}
			}
			else
			{
				LogLevel.DEBUG(1,new Throwable(),"Successfully inserted customer invoice tax details! ");
				session.setAttribute("priority","1");
				session.setAttribute("sespercentErrorMsg", "<font color='#000000' class='vrb10'>Already added customer invoice tax for selected city.Please update...! </font> ");
				res.sendRedirect("/bookbattery/jsp/admin/servicesstore/percentage/cuspercent.jsp?keyword="+keyword);
				return;
			}
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
				session.setAttribute("sespercentErrorMsg",	"Generall Error...Please Try Again" );
				res.sendRedirect("/bookbattery/jsp/admin/servicesstore/percentage/cuspercent.jsp?keyword="+keyword);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				session.setAttribute("sespercentErrorMsg",	"General Error...Please Try Again" );
				res.sendRedirect("/bookbattery/jsp/admin/servicesstore/percentage/cuspercent.jsp?keyword="+keyword);
			}
		}
		else if(strWhatToDo.equalsIgnoreCase("getcuspercentoupdate"))
		  {
			String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
			LogLevel.DEBUG(5,new Throwable(),"city :"+city );

			ServletOutputStream out=res.getOutputStream();
			try
			  {  
				/* @strSqlQry3 : carries the query to Insert the customer percentage details into customer_percentage table.*/
				String strSqlQry4 = "select cus_percent_id,city_percentage,city_local_percentage from customer_percentage where city='"+city+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry4 :"+strSqlQry4 );

				Vector invoicecuspercente=qm.executeQuery(strSqlQry4);
				LogLevel.DEBUG(5,new Throwable(),"invoicecuspercente:"+invoicecuspercente);
				String strRes="<table  align='center' width='100%' border='0'>";
							
				if(invoicecuspercente==null || invoicecuspercente.size() == 0)
				  {
					out.println("<p align='center' class='insidecontent'>No Customer invoice tax are Avaliable based on selection!<a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
					return;
				  }
				 else
				  {
				   for(int j=0; j<invoicecuspercente.size();j++)
					 {
						Hashtable ht3=(Hashtable)invoicecuspercente.get(j);
						String cusperid=String.valueOf(ht3.get("cus_percent_id"));
						String strcitytax=String.valueOf(ht3.get("city_percentage"));
						String strcitylocaltax=String.valueOf(ht3.get("city_local_percentage"));
				
						
						strRes=strRes+"<tr><td align='right' class='insidecontent' width='42%'>City Tax<font color='FF0000'>*</font></td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='citytax' value='"+strcitytax+"' size='30' maxlength='7'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='insidecontent' width='42%'>City Local Tax<font color='FF0000'>*</font>&nbsp;</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='citylocaltax' value='"+strcitylocaltax+"' size='30' maxlength='7'></td></tr>";
						strRes=strRes+"<tr><td align='right' width='42%'></td><td align='left'><input type='button'  onclick=\"funToUpdatcuspercent('"+cusperid+"');\" value='Update' class='smallbutton'></td>";
					   }
					}
					strRes=strRes+"</table>";							
					out.println(strRes);
					return;				
				}
			   catch(IOException ioe)
			    {
					LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
					ioe.printStackTrace();
					session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
					res.sendRedirect("/bookbattery/jsp/admin/servicesstore/percentage/editcuspercent.jsp");
			    }
			   catch(Exception e)
			    {
					LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
					res.sendRedirect("/bookbattery/jsp/admin/servicesstore/percentage/editcuspercent.jsp");
			    }
		  }
		  else if(strWhatToDo.equalsIgnoreCase("editcustomertax"))
		{
			String citytax = (req.getParameter("citytax")!=null)?(req.getParameter("citytax")):"";
			LogLevel.DEBUG(5,new Throwable(),"citytax :"+citytax );

			String citylocaltax = (req.getParameter("citylocaltax")!=null)?(req.getParameter("citylocaltax")):"";
			LogLevel.DEBUG(5,new Throwable(),"citylocaltax :"+citylocaltax );

			String cusperid = (req.getParameter("cusperid")!=null)?(req.getParameter("cusperid")):"";
			LogLevel.DEBUG(5,new Throwable(),"cusperid :"+cusperid );

			String keyword = (req.getParameter("keyword")!=null)?(req.getParameter("keyword")):"";
			LogLevel.DEBUG(5,new Throwable(),"keyword :"+keyword );
			int reslt=0;
			try
			{
				/*Query to get the Admin User name and Password*/
 				String strSqlQry = "update customer_percentage set city_percentage='"+citytax+"',city_local_percentage='"+citylocaltax+"' where cus_percent_id='"+cusperid+"'";
				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
				
				reslt = qm.executeUpdate(strSqlQry);
		
				if(reslt <0)
				{
						LogLevel.DEBUG(1,new Throwable()," Failed to update percentage details! ");
						session.setAttribute("priority","1");
						session.setAttribute("sesleditcusErrorMsg", "<font color='#FF3333' class='top1'>Failed to update percentage price! </font> ");
						res.sendRedirect("/bookbattery/jsp/admin/servicesstore/percentage/editcuspercent.jsp?keyword="+keyword);
						return;
					}
				   else
				    {
						LogLevel.DEBUG(1,new Throwable(),"Successfully updated percentage details! ");
						session.setAttribute("sesleditcusErrorMsg", "<font color='#000000' class='top1'>Successfully Updated percentage details.!</font>");
						res.sendRedirect("/bookbattery/jsp/admin/servicesstore/percentage/editcuspercent.jsp?keyword="+keyword);
				    }
			     }
			    catch(IOException ioe)
			     {
						LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
						ioe.printStackTrace();
						session.setAttribute("sesleditcusErrorMsg",	"Generall Error...Please Try Again" );
						res.sendRedirect("/bookbattery/jsp/admin/servicesstore/percentage/editcuspercent.jsp?keyword="+keyword);
			     }
			    catch(Exception e)
			     {
						LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
						e.printStackTrace();
						session.setAttribute("sesleditcusErrorMsg",	"General Error...Please Try Again" );
						res.sendRedirect("/bookbattery/jsp/admin/servicesstore/percentage/editcuspercent.jsp?keyword="+keyword);
			     }
		}
		 else if(strWhatToDo.equalsIgnoreCase("viewinvoicereports"))
		  {
			String txtFromDate  = (req.getParameter("txtFromDate")!=null)?req.getParameter("txtFromDate").trim():"";
			String txtToDate  = (req.getParameter("txtToDate")!=null)?req.getParameter("txtToDate").trim():"";
			String invoicetype  = (req.getParameter("invoicetype")!=null)?req.getParameter("invoicetype").trim():"";

			ServletOutputStream out=res.getOutputStream();
			String strRes="";
			Vector retinvoice = new Vector();
			try
			  {
				String strConditions="";

				SimpleDateFormat sdfDate=new SimpleDateFormat("dd-MM-yyyy");
				Date fromDate=sdfDate.parse(txtFromDate);  
				Date toDate=sdfDate.parse(txtToDate); 

				SimpleDateFormat sdfString=new SimpleDateFormat("yyyy-MM-dd");
				String strFromDate=sdfString.format(fromDate); 
				LogLevel.DEBUG(5, new Throwable(), "strFromDate :"+ strFromDate);

				String strToDate=sdfString.format(toDate); 
				LogLevel.DEBUG(5, new Throwable(), "strToDate :"+ strToDate);

				if(strConditions.equals(""))
					strConditions=" date(creation_date) between '"+strFromDate+"' and '"+strToDate+"'";
				else
					strConditions=strConditions+" and date(creation_date) between '"+strFromDate+"' and '"+strToDate+"'";

			/* @strSqlQry3 : carries the query to Insert the retailer details into retailer_invoice table.*/
				if(invoicetype.equals("services"))
				  {
					String strSqlQry4 = "select retailer_name,invoice_number,date(from_date),date(to_date),ret_pdf_url,salesurl from retailer_invoice where "+strConditions+" and invoice_type='services' order by retailer_name asc";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry4 :"+strSqlQry4 );

					retinvoice=qm.executeQuery(strSqlQry4);
					LogLevel.DEBUG(5,new Throwable(),"retinvoice:"+retinvoice);
				  }
				  else
				  {
					String strSqlQry4 = "select retailer_name,invoice_number,date(from_date),date(to_date),ret_pdf_url,salesurl from retailer_invoice where "+strConditions+" and invoice_type='services' order by retailer_name asc";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry4 :"+strSqlQry4 );

					retinvoice=qm.executeQuery(strSqlQry4);
					LogLevel.DEBUG(5,new Throwable(),"retinvoice:"+retinvoice);
				  }

			if(retinvoice==null || retinvoice.size() == 0)
			{
				out.println("<p align='center' valign='top' class='insidecontent'><table valign='top' width='500' cellspacing='0' border='1' align='center' bgcolor='#FFFFFF' cellpadding='0'><tr><td><table valign='top' width='500' bgcolor='#FFFFFF' border='0'><tr><td class='insidecontent' align='center'>No Invoice details Avaliable!</td></tr></table></td></tr></table><a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
			}
			else
			{
				strRes=strRes+"<table border='0' align='center' bgcolor='#FFFFFF' width='100%'><tr>";
						
				strRes=strRes+"<table border='0' cellspacing='1' align='center' cellpadding='2' bgcolor='#FFFFFF' width='100%'><tr bgcolor='#2364b1'><td class='prodheading' width='5%'>SlNo</td><td class='prodheading' width='20%'>RetailerName</td><td class='prodheading' width='15%'>InvoiceNo</td><td class='prodheading' width='15%'>From Date</td><td class='prodheading' width='15%'>To Date</td><td class='prodheading' width='15%'>Invoice</td><td class='prodheading' width='15%'>SalesOrder</td></tr>";
			  	for(int j=0; j<retinvoice.size();j++)
				{
					Hashtable ht1=(Hashtable)retinvoice.get(j);
					String retname=(String)ht1.get("retailer_name");
					String invoicenumber=(String)ht1.get("invoice_number");
					String fromdate=String.valueOf(ht1.get("date(from_date)"));
					String todate=String.valueOf(ht1.get("date(to_date)"));
					String pdfurl=(String)ht1.get("ret_pdf_url");
					String salesurl=(String)ht1.get("salesurl");
			

					int Jcnt=j+1;
					
					strRes=strRes+"<table bgcolor='#FFFFFF' width='100%' cellspacing='1' border='0' align='center'  cellpadding='2'><input type='checkbox' name='chkSi' value='' style='display:none'/>";
					strRes=strRes+"<td width='5%'   class='table1 align='left'  >"+Jcnt+"</td>";
					strRes=strRes+"<td width='20%'   class='table1 align='left'  >"+retname+"</td>";
					strRes=strRes+"<td width='15%'   class='table1 align='left'  >"+invoicenumber+"</td>";
					strRes=strRes+"<td width='15%'   class='table1 align='left'  >"+fromdate+"</td>";
					strRes=strRes+"<td width='15%'   class='table1 align='left'  >"+todate+"</td>";
						if(pdfurl.equals("") || pdfurl == "NULL")
						{
						strRes=strRes+"<td width='15%'   class='table1 align='left'  >No PDF</td>";
						}
						else
						{
						strRes=strRes+"<td width='15%' class='table1 align='left'  ><a href='"+pdfurl+"' target='new'><img src=\"/bookbattery/images/pdf_xls.gif\" border='0'/></td>";
						}
						if(salesurl.equals("") || salesurl == "NULL")
						{
						strRes=strRes+"<td width='15%'   class='table1 align='left'  >No PDF</td>";
						}
						else
						{
						strRes=strRes+"<td width='15%' class='table1 align='left'  ><a href='"+salesurl+"' target='new'><img src=\"/bookbattery/images/pdf_xls.gif\" border='0'/></td>";
						}
					strRes=strRes+"</table>";
					strRes=strRes+"</table>";
				} 
				out.println(strRes);
					return;				
				}			
				}
			   catch(IOException ioe)
			    {
					LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
					ioe.printStackTrace();
					session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
					res.sendRedirect("/bookbattery/jsp/admin/batterystore/mis/viewretailerinvoice.jsp");
			    }
			   catch(Exception e)
			    {
					LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
					res.sendRedirect("/bookbattery/jsp/admin/batterystore/mis/viewretailerinvoice.jsp");
			    }
		  }
		}
     }


