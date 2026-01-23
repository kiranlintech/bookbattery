/***********************************************************************		
	NGIT Confidential. 
	@File Name   : AdminBatteryLoginServlet.java
	@Description : This Servlet is used to allow the operator to Login
	@Date        : 30th August 2013
******************************************************************/ 
package com.ngit.servlets.admin.batterystore; 
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.admin.mis.GenerateExcelbattery;
import java.io.IOException; 
import java.io.FileInputStream; 
import java.util.Properties; 
import java.util.Hashtable; 
import java.util.Vector;
import java.util.*;
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
 * @author Sai Krishna Daddala.
 */
/* This class is to Admin Login. This class initializes the necessary connection pools and perform the transactions on the pools. */
public class GenerateRetailerInvoice extends HttpServlet 
{
 	private ServletContext context; 
	QueryManager qm;
	String baseURL;
	static final long serialVersionUID=21;
	/*This init method initializes the necessary connection pools and perform the transactions on the pools from respectvie files. */
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
			baseURL = (propsMOPConfig.getProperty("baseURL")!=null)?(propsMOPConfig.getProperty("baseURL")):"";
			LogLevel.DEBUG(5, new Throwable(), "baseURL :" + baseURL);
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
		/*This methos is used to check vehicle models avliable or not*/
		if(strWhatToDo.equalsIgnoreCase("getretailers"))
		  {
			try
			  {
				String strSqlQry ="select distinct(retailer_name) from battery_order_details where order_status='Customer Contacted' and order_reasons='installed' order by retailer_name asc";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

				Vector RetailerVector=qm.executeQuery(strSqlQry);
				LogLevel.DEBUG(5,new Throwable(),"RetailerVector:"+RetailerVector );
				if(RetailerVector.isEmpty())
				{
					LogLevel.DEBUG(1,new Throwable(),"Failed to get Retailers ");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsgsity", "<font color='#00dd00' class='vrb10'>Failed to get Retailers! </font> ");
					res.sendRedirect("../jsp/admin/batterystore/mis/generateretailerinvoice.jsp");
					return;
				}
				else
				{
					LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched Retailers");
					session.setAttribute("sesretailervector", RetailerVector);
					session.setAttribute("sesErrorMsgsity","<font color='#CC0000' class='vrb10'>Succesfully Fetched Retailers.</font> ");
					res.sendRedirect("../jsp/admin/batterystore/mis/generateretailerinvoice.jsp");
					return;
				}
				}
			catch(IOException ioe)
				{
					//LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
					ioe.printStackTrace();
					session.setAttribute("sesErrorMsgsst",	"Generall Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/mis/generateretailerinvoice.jsp");
				}
			catch(Exception e)
				{
					//LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsgsst",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/mis/generateretailerinvoice.jsp");
				}
		     }
		else if(strWhatToDo.equalsIgnoreCase("getinvretailers"))
		  {
			try
			  {
				String strSqlQry ="select distinct(retailer_name) from inverter_order_details where order_status='Customer Contacted' and order_reasons='installed' order by retailer_name asc";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

				Vector RetailerVector=qm.executeQuery(strSqlQry);
				LogLevel.DEBUG(5,new Throwable(),"RetailerVector:"+RetailerVector );
				if(RetailerVector.isEmpty())
				{
					LogLevel.DEBUG(1,new Throwable(),"Failed to get Retailers ");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsgsity", "<font color='#00dd00' class='vrb10'>Failed to get Retailers! </font> ");
					res.sendRedirect("../jsp/admin/batterystore/mis/invertergenerateretailerinvoice.jsp");
					return;
				}
				else
				{
					LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched Retailers");
					session.setAttribute("sesretailervector", RetailerVector);
					session.setAttribute("sesErrorMsgsity","<font color='#CC0000' class='vrb10'>Succesfully Fetched Retailers.</font> ");
					res.sendRedirect("../jsp/admin/batterystore/mis/invertergenerateretailerinvoice.jsp");
					return;
				}
				}
			catch(IOException ioe)
				{
					//LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
					ioe.printStackTrace();
					session.setAttribute("sesErrorMsgsst",	"Generall Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/mis/invertergenerateretailerinvoice.jsp");
				}
			catch(Exception e)
				{
					//LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsgsst",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/mis/invertergenerateretailerinvoice.jsp");
				}
		     }
			 
			 /******This method is used for generating invoices*/
			 
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

			//String city  = (req.getParameter("city")!=null)?req.getParameter("city").trim():""; 
			//LogLevel.DEBUG(5,new Throwable(),"city :"+city );	

			String retailers  = (req.getParameter("retailer")!=null)?req.getParameter("retailer").trim():"";
			LogLevel.DEBUG(5,new Throwable(),"retailers :"+retailers );	

			String months  = (req.getParameter("months")!=null)?req.getParameter("months").trim():"";
			LogLevel.DEBUG(5,new Throwable(),"months :"+months );	

			String txtFromDate  = (req.getParameter("txtFromDate")!=null)?req.getParameter("txtFromDate").trim():"";
			String txtToDate  = (req.getParameter("txtToDate")!=null)?req.getParameter("txtToDate").trim():"";
			String key = "battery";
			String retailerid ="";

			ServletOutputStream out=res.getOutputStream();

			String StrQryPrice = "";
			Vector RetInoiceVector= new Vector();
			//String Strqry1 = "";
			ArrayList htav=new ArrayList();
			
			String SqlQrysum = "";
			Hashtable htgrandtot =new Hashtable();
			//String strgrandtot="";
			String StrQryservicetax = "";
			Hashtable htservicetax =new Hashtable();
			String strservicetax="";
			String StrQrytottax = "";
			Hashtable httottax =new Hashtable();
			String strtottax="";
			String verificationcode = ""; 
			String erp_pre_tax_condition = ""; 
			
			
			
			String Total_Reconciled_Amountinv="";
			String strgrandtotinv="";
			String strgrandtot_Online_Paymentinv="";
			String strservicetaxinv="";
			String strtottaxinv="";

			String Total_Reconciled_Amountser="";
			String strgrandtotser="";
			String strgrandtot_Online_Paymentser="";
			String strtottaxser="";
			String strservicetaxser="";

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
					strConditions1=" date(updated_date) between '"+strFromDate+"' and '"+strToDate+"'";
				else
					strConditions1=strConditions1+" and date(updated_date) between '"+strFromDate+"' and '"+strToDate+"'";

				if(retailers.equals("ALL"))
				{
					strQuery = "(select distinct(retailer_name),city from battery_order_details where "+strConditions+" and order_status='Customer Contacted' and order_reasons='installed') UNION (select distinct(retailer_name),city from inverter_order_details where "+strConditions+" and order_status='Customer Contacted' and order_reasons='installed') UNION (select distinct(retailer_name),city from service_order_details where "+strConditions+" and order_status='Customer Contacted' and order_reasons='installed') order by retailer_name asc";
					LogLevel.DEBUG(5,new Throwable(),"strQuery :"+strQuery );
				}
				else
				{
				//strQuery = "select distinct(retailer_name),city from battery_order_details where "+strConditions+" and order_status='Customer Contacted' and order_reasons='installed' and retailer_name='"+retailers+"'";
				strQuery = "(select distinct(retailer_name),city from battery_order_details where "+strConditions+" and order_status='Customer Contacted' and order_reasons='installed' and retailer_name='"+retailers+"') UNION (select distinct(retailer_name),city from inverter_order_details where "+strConditions+" and order_status='Customer Contacted' and order_reasons='installed' and retailer_name='"+retailers+"') UNION (select distinct(retailer_name),city from service_order_details where "+strConditions+" and order_status='Customer Contacted' and order_reasons='installed' and retailer_name='"+retailers+"') order by retailer_name asc";
				LogLevel.DEBUG(5,new Throwable(),"strQuery :"+strQuery );
				}
				Vector batteryorddetails=qm.executeQuery(strQuery);
				LogLevel.DEBUG(5,new Throwable(),"batteryorddetails :"+batteryorddetails );
				if(batteryorddetails.isEmpty())
				 { 
					out.println("<p align='center' valign='top' class='insidecontent'><table valign='top' width='500' cellspacing='0' border='0' align='center' bgcolor='#FFFFFF' cellpadding='0'><tr><td><table valign='top' width='500' bgcolor='#FFFFFF' border='0'><tr><td class='insidecontent' align='center'>No Record found based on selection!</td></tr></table></td></tr></table><a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
				 }
				 else
				 {
					String[] datatables = {"battery_order_details", "inverter_order_details", "service_order_details"};
					Calendar cal = Calendar.getInstance();
					int currentMonth = cal.get(Calendar.MONTH) + 1;
					int currentYear = cal.get(Calendar.YEAR);
					
					
				//Declarations Starts
					String retailer="";
					 String city="";
					// Vector RetInoiceVector = new Vector();
					Vector RetInoiceVector_Debit_Card = new Vector();
					Vector RetInoiceVector_Credit_Card = new Vector();
					Vector RetInoiceVector_Online = new Vector();
					
					 Vector RetInverterInoiceVector = new Vector();
					 Vector RetInverterInoiceVector_Debit_Card = new Vector();
					 Vector RetInverterInoiceVector_Credit_Card = new Vector();
					 Vector RetInverterInoiceVector_Online = new Vector();					 
					 
					 
					 Vector RetServiceInoiceVector = new Vector();
					 Vector RetServiceInoiceVector_Debit_Card = new Vector();
					 Vector RetServiceInoiceVector_Credit_Card = new Vector();
					 Vector RetServiceInoiceVector_Online = new Vector();
					
					
					// Vector RetServiceInoiceVector = new Vector();
					
					// String batids="";
					// String batids_Debit_Card="";
					// String batids_Credit_Card="";
					
					// String Inverterbatids="";
					// String Inverterbatids_Debit_Card="";
					// String Inverterbatids_Credit_Card="";
					
					String Total_Reconciled_Amount ="";
					String strgrandtot_Online_Payment ="";
					
					// ArrayList arrExcelHeader1;
					// ArrayList dbHeaders2;
					
					// ArrayList arrInverterExcelHeader1;
					// ArrayList dbInverterHeaders2;
					
					// ArrayList arrServiceExcelHeader1;
					// ArrayList dbServiceHeaders2;
					
					// Vector dailyVectorwithcat = new Vector();
					// Vector dailyInverterVectorwithcat = new Vector();  					
					// Vector dailyServiceVectorwithcat = new Vector();

					// String Rettitle="";
					// String Rettitle1="";
					String strgrandtot="";
					// String strservicetax="";
					// String strtottax="";
					// String servicepercentage="0.5";
					//Declarations Ends */

					 
				/* Starts to generate retailers invoice */
				for ( int j=0; j<batteryorddetails.size(); j++)
				{
					
				for (int d = 0; d < datatables.length; d++) {
							
					String tableName = datatables[d];
					LogLevel.DEBUG(5,new Throwable(),"tableName :"+tableName);	
					
				Hashtable ht2=(Hashtable)batteryorddetails.get(j);
				 retailer=String.valueOf(ht2.get("retailer_name"));
				 LogLevel.DEBUG(5,new Throwable(),"retailer :"+retailer);	
				 city=String.valueOf(ht2.get("city"));
				
				/**********************Battery Order Details****************/
			
			if(tableName.equals("battery_order_details")){
										
				if(retailer.contains("Amaron"))
				{
					if(retailer.contains("Amaron-Pitstop"))
					{
						erp_pre_tax_condition="CAST(round(price/city_percentage) AS SIGNED) as 14percentvat, erp_pre_tax";
					}
					else
					{
						//erp_pre_tax_condition="round(price) as 14percentvat, round(erp_pre_tax*city_percentage,0) as erp_pre_tax";
						//erp_pre_tax_condition="round(price) as 14percentvat, round(erp_pre_tax,0) as erp_pre_tax";//prasanna
						erp_pre_tax_condition="CAST(round(price/city_percentage) AS SIGNED) as 14percentvat, round(erp_pre_tax,0) as erp_pre_tax";
					}
				}
				else
				{
					erp_pre_tax_condition="CAST(round(price/city_percentage) AS SIGNED) as 14percentvat,CAST(round(erp_pre_tax/city_percentage) AS SIGNED) as erp_pre_tax";
				}
					
				LogLevel.DEBUG(5, new Throwable(), "Bharath :" + erp_pre_tax_condition);
				LogLevel.DEBUG(5, new Throwable(), "retailer before :" + retailer);

				StrQryPrice = "select battery_model,CASE order_type WHEN 'Replaced' THEN 'Yes' else 'No' END as order_type, CAST(round(SUM(quantity)) AS SIGNED) as quantity, "+erp_pre_tax_condition+",total_commission_amount*quantity as comission,battery_commission_amount*quantity as battery_commission_amount,CAST(round((total_commission_amount-battery_commission_amount )*quantity ) AS SIGNED) as Transaction_Amount , CAST(round(SUM((battery_commission_amount*quantity)) )AS SIGNED) as ourcommision, delivery_charge/2 as Delivery_Charge from battery_order_details where "+strConditions1+" and retailer_name='"+retailer+"' and order_status='Customer Contacted' and payment_mode not in('Credit Card','Debit Card','Online') and order_reasons='installed' group by battery_model,order_type,battery_commission_amount ,order_number";

				LogLevel.DEBUG(5, new Throwable(), "StrQryPrice :" + StrQryPrice);

				RetInoiceVector=qm.executeQuery(StrQryPrice);
				LogLevel.DEBUG(5,new Throwable(),"RetInoiceVector:"+RetInoiceVector );

				String StrQryPrice_Debit_Card = "select battery_model,CASE order_type WHEN 'Replaced' THEN 'Yes' else 'No' END as order_type, CAST(round(SUM(quantity)) AS SIGNED) as quantity,"+erp_pre_tax_condition+",total_commission_amount*quantity as comission,battery_commission_amount*quantity as battery_commission_amount,CAST(round((total_commission_amount-battery_commission_amount )*quantity) AS SIGNED) as Transaction_Amount ,CAST(round(SUM((battery_commission_amount*quantity)) )AS SIGNED) as ourcommision, delivery_charge/2 as Delivery_Charge from battery_order_details where "+strConditions1+" and retailer_name='"+retailer+"' and order_status='Customer Contacted' and payment_mode='Debit Card' and order_reasons='installed' group by battery_model,order_type,battery_commission_amount,order_number";
				
				
				LogLevel.DEBUG(5, new Throwable(), "StrQryPrice_Debit_Card :" + StrQryPrice_Debit_Card);
				RetInoiceVector_Debit_Card=qm.executeQuery(StrQryPrice_Debit_Card);
				LogLevel.DEBUG(5,new Throwable(),"RetInoiceVector_Debit_Card:"+RetInoiceVector_Debit_Card );


				String StrQryPrice_Credit_Card = "select battery_model,CASE order_type WHEN 'Replaced' THEN 'Yes' else 'No' END as order_type, CAST(round(SUM(quantity)) AS SIGNED) as quantity,"+erp_pre_tax_condition+",total_commission_amount*quantity as comission,battery_commission_amount*quantity as battery_commission_amount,CAST(round((total_commission_amount-battery_commission_amount )*quantity) AS SIGNED) as Transaction_Amount , CAST(round(SUM((battery_commission_amount*quantity)) )AS SIGNED) as ourcommision, delivery_charge/2 as Delivery_Charge from battery_order_details where "+strConditions1+" and retailer_name='"+retailer+"' and order_status='Customer Contacted' and payment_mode='Credit Card' and order_reasons='installed' group by battery_model,order_type,battery_commission_amount,order_number";
				
				LogLevel.DEBUG(5, new Throwable(), "StrQryPrice_Credit_Card :" + StrQryPrice_Credit_Card);

			   RetInoiceVector_Credit_Card=qm.executeQuery(StrQryPrice_Credit_Card);
				LogLevel.DEBUG(5,new Throwable(),"RetInoiceVector_Credit_Card:"+RetInoiceVector_Credit_Card );

				
				String StrQryPrice_Online= "select battery_model,CASE order_type WHEN 'Replaced' THEN 'Yes' else 'No' END as order_type, CAST(round(SUM(quantity)) AS SIGNED) as quantity,"+erp_pre_tax_condition+",total_commission_amount*quantity as comission,battery_commission_amount*quantity as battery_commission_amount,CAST(round(total_commission_amount-battery_commission_amount )*quantity AS SIGNED) as Transaction_Amount ,CAST(round(SUM((battery_commission_amount*quantity)) )AS SIGNED) as ourcommision, delivery_charge/2 as Delivery_Charge from battery_order_details where "+strConditions1+" and retailer_name='"+retailer+"' and order_status='Customer Contacted' and payment_mode='Online' and order_reasons='installed' group by battery_model,order_type,battery_commission_amount ,order_number";
				
				
				LogLevel.DEBUG(5, new Throwable(), "StrQryPrice_Online :" + StrQryPrice_Online);

				RetInoiceVector_Online=qm.executeQuery(StrQryPrice_Online);
				LogLevel.DEBUG(5,new Throwable(),"RetInoiceVector_Online:"+RetInoiceVector_Online );
				
				
				String Strqry1 = "select CAST(round(SUM((battery_commission_amount*quantity)) )AS SIGNED) as grandtotal from battery_order_details where "+strConditions1+" and retailer_name='"+retailer+"' and order_status='Customer Contacted' and order_reasons='installed'";
				LogLevel.DEBUG(5, new Throwable(), "Strqry1 :" + Strqry1);
				
					Hashtable htgrandtot_battery_commission = qm.getRow(Strqry1); 
					String strgrandtot_battery_commission=String.valueOf(htgrandtot_battery_commission.get("grandtotal"));
					LogLevel.DEBUG(5, new Throwable(), "strgrandtot_battery_commission :" + strgrandtot_battery_commission);

					if(strgrandtot_battery_commission.equals("NULL") || strgrandtot_battery_commission.equals(null)|| strgrandtot_battery_commission.equals("null") || strgrandtot_battery_commission.equals("") )
					{
						LogLevel.DEBUG(5, new Throwable(), "Inside NULL if condition:" + Strqry1);						
						 strgrandtot_battery_commission="0";
						 strgrandtot="0";
						 strgrandtot_Online_Payment="0";
						 strtottax="0";
						 strservicetax="0";
					}
				
				else
				{

					htav=qm.getField(Strqry1);

					String batids="";
					for(int i=0;i<htav.size();i++)
					{
					if(batids.equals(""))
					batids=htav.get(i).toString();
					else
					batids=batids+"+"+htav.get(i).toString();
					}
					LogLevel.DEBUG(5,new Throwable(),"batids:"+batids);

					SqlQrysum = "select CAST(round("+batids+") AS SIGNED) as grandtotal";
					LogLevel.DEBUG(5,new Throwable(),"SqlQrysum:"+SqlQrysum);
					
					htgrandtot = qm.getRow(SqlQrysum); 
				    strgrandtot="";
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
					
					
					
				
					
					
				//########## Total online payment recived 
				
				String SqlQrysum_Online_Payment = "select CAST(SUM(((CASE WHEN order_type = 'Replaced' THEN witholdbatprice ELSE price END)*quantity)+delivery_charge) AS SIGNED)  AS OnlineAmountPayed from battery_order_details where "+strConditions1+" and retailer_name='"+retailer+"' and order_status='Customer Contacted' and order_reasons='installed' and payment_mode='Online'";
				LogLevel.DEBUG(5,new Throwable(),"SqlQrysum_Online_Payment:"+SqlQrysum_Online_Payment);

				Hashtable htgrandtot_Online_Payment = qm.getRow(SqlQrysum_Online_Payment); 
			   strgrandtot_Online_Payment=String.valueOf(htgrandtot_Online_Payment.get("OnlineAmountPayed"));
				LogLevel.DEBUG(5, new Throwable(), "strgrandtot_Online_Payment :" + strgrandtot_Online_Payment);
				
				if(strgrandtot_Online_Payment.equals("NULL") || strgrandtot_Online_Payment.equals(null)|| strgrandtot_Online_Payment.equals("null") || strgrandtot_Online_Payment.equals("") )
					strgrandtot_Online_Payment="0";
				else
					strgrandtot_Online_Payment=strgrandtot_Online_Payment;

				int strgrandtot_Online_Payment_int = Integer.parseInt(strgrandtot_Online_Payment);
				int strgrandtot_int = Integer.parseInt(strgrandtot);

				int strgrandtot_tmp_int=strgrandtot_int-strgrandtot_Online_Payment_int;
								
				Total_Reconciled_Amount = Integer.toString(strgrandtot_tmp_int);
				
				}
			}
				
			else if(tableName.equals("inverter_order_details")){
						
					//####### Condition for Cash
					String StrQryInvPrice = "select inverter_model,CAST(round(SUM(quantity)) AS SIGNED) as quantity, "+erp_pre_tax_condition+",total_commission_amount*quantity as comission,inverter_commission_amount*quantity as inverter_commission_amount,CAST(round(total_commission_amount-inverter_commission_amount )*quantity AS SIGNED) as Transaction_Amount,CAST(round(SUM(inverter_commission_amount*quantity)) AS SIGNED) as ourcommision from inverter_order_details where "+strConditions1+" and retailer_name='"+retailer+"' and order_status='Customer Contacted' and payment_mode not in('Credit Card','Debit Card','Online') and order_reasons='installed' group by inverter_model,inverter_commission_amount,order_number";
					LogLevel.DEBUG(5, new Throwable(), "StrQryInvPrice :" + StrQryInvPrice);

					RetInverterInoiceVector=qm.executeQuery(StrQryInvPrice);
					LogLevel.DEBUG(5,new Throwable(),"RetInverterInoiceVector:"+RetInverterInoiceVector );

					
					//####### Condition for Debit Card

					String StrQryPrice_Debit_Card = "select inverter_model,CAST(round(SUM(quantity)) AS SIGNED) as quantity,"+erp_pre_tax_condition+",total_commission_amount*quantity as comission,inverter_commission_amount*quantity as inverter_commission_amount,CAST(round(total_commission_amount-inverter_commission_amount )*quantity AS SIGNED) as Transaction_Amount,CAST(round(SUM(inverter_commission_amount*quantity)) AS SIGNED) as ourcommision from inverter_order_details where "+strConditions1+" and retailer_name='"+retailer+"' and order_status='Customer Contacted' and payment_mode='Debit Card' and order_reasons='installed' group by inverter_model,inverter_commission_amount ,order_number";
					
					
					
					LogLevel.DEBUG(5, new Throwable(), "StrQryPrice_Debit_Card :" + StrQryPrice_Debit_Card);

					RetInverterInoiceVector_Debit_Card=qm.executeQuery(StrQryPrice_Debit_Card);
					LogLevel.DEBUG(5,new Throwable(),"RetInverterInoiceVector_Debit_Card:"+RetInverterInoiceVector_Debit_Card );

					//####### Condition for Credit Card

					String StrQryPrice_Credit_Card = "select inverter_model,CAST(round(SUM(quantity)) AS SIGNED) as quantity,"+erp_pre_tax_condition+" ,total_commission_amount*quantity as comission,inverter_commission_amount*quantity as inverter_commission_amount,CAST(round(total_commission_amount-inverter_commission_amount )*quantity AS SIGNED) as Transaction_Amount , CAST(round(SUM(inverter_commission_amount*quantity)) AS SIGNED) as ourcommision from inverter_order_details where "+strConditions1+" and retailer_name='"+retailer+"' and order_status='Customer Contacted' and payment_mode='Credit Card' and order_reasons='installed' group by inverter_model,inverter_commission_amount ,order_number";
					LogLevel.DEBUG(5, new Throwable(), "StrQryPrice_Credit_Card :" + StrQryPrice_Credit_Card);

					RetInverterInoiceVector_Credit_Card=qm.executeQuery(StrQryPrice_Credit_Card);
					LogLevel.DEBUG(5,new Throwable(),"RetInverterInoiceVector_Credit_Card:"+RetInverterInoiceVector_Credit_Card );

					//####### Condition for Online
					
					String StrQryPrice_Online = "select inverter_model,CAST(round(SUM(quantity)) AS SIGNED) as quantity,"+erp_pre_tax_condition+",total_commission_amount*quantity as comission,inverter_commission_amount*quantity as inverter_commission_amount,CAST(round(total_commission_amount-inverter_commission_amount )*quantity AS SIGNED) as Transaction_Amount , CAST(round(SUM(inverter_commission_amount*quantity)) AS SIGNED) as ourcommision from inverter_order_details where "+strConditions1+" and retailer_name='"+retailer+"' and order_status='Customer Contacted' and payment_mode='Online' and order_reasons='installed' group by inverter_model,inverter_commission_amount ,order_number";
					LogLevel.DEBUG(5, new Throwable(), "StrQryPrice_Online :" + StrQryPrice_Online);

					RetInverterInoiceVector_Online=qm.executeQuery(StrQryPrice_Online);
					LogLevel.DEBUG(5,new Throwable(),"RetInverterInoiceVector_Online:"+RetInverterInoiceVector_Online );


					String Strqry1 = "select CAST(round(SUM(inverter_commission_amount*quantity )) AS SIGNED) as grandtotal from inverter_order_details where "+strConditions1+" and retailer_name='"+retailer+"' and order_status='Customer Contacted' and order_reasons='installed'";
					LogLevel.DEBUG(5, new Throwable(), "Strqry1 :" + Strqry1);
					
					
					Hashtable htgrandtot_inverter_commission = qm.getRow(Strqry1); 
					String strgrandtot_inverter_commission=String.valueOf(htgrandtot_inverter_commission.get("grandtotal"));
					LogLevel.DEBUG(5, new Throwable(), "strgrandtot_inverter_commission :" + strgrandtot_inverter_commission);
					
					if(strgrandtot_inverter_commission.equals("NULL") || strgrandtot_inverter_commission.equals(null)|| strgrandtot_inverter_commission.equals("null") || strgrandtot_inverter_commission.equals("") )
					{
						LogLevel.DEBUG(5, new Throwable(), "Inside NULL if condition:" + Strqry1);						
						 Total_Reconciled_Amountinv="0";
						 strgrandtotinv="0";
						 strgrandtot_Online_Paymentinv="0";
						 strtottaxinv="0";
						 strservicetaxinv="0";
					}
					else
					{
						LogLevel.DEBUG(5, new Throwable(), "Inside NULL else condition:" + Strqry1);
					

					//########## Total online payment recived 
				
					//Code commented due to null

						ArrayList htavinv=new ArrayList();
						htavinv=qm.getField(Strqry1);
						String batids="";
						for(int i=0;i<htavinv.size();i++)
						{
						if(batids.equals(""))
						batids=htavinv.get(i).toString();
						else
						batids=batids+"+"+htavinv.get(i).toString();
						}
						LogLevel.DEBUG(5,new Throwable(),"batids:"+batids);

						String SqlQrysuminv = "select CAST(round("+batids+") AS SIGNED) as grandtotal";
						LogLevel.DEBUG(5,new Throwable(),"SqlQrysuminv:"+SqlQrysuminv);
						
						Hashtable htgrandtotinv = qm.getRow(SqlQrysuminv); 
						 strgrandtotinv=String.valueOf(htgrandtotinv.get("grandtotal"));
						LogLevel.DEBUG(5, new Throwable(), "strgrandtotinv :" + strgrandtotinv);

						String StrQryservicetaxinv = "select CAST(round(("+strgrandtotinv+"-(("+strgrandtotinv+"/"+servicetax+")*100))) AS SIGNED) as servicetaxyprice";
						LogLevel.DEBUG(5, new Throwable(), "StrQryservicetaxinv :" + StrQryservicetaxinv);

						Hashtable htservicetaxinv = qm.getRow(StrQryservicetaxinv); 
						strservicetaxinv=String.valueOf(htservicetaxinv.get("servicetaxyprice"));
						LogLevel.DEBUG(5, new Throwable(), "strservicetaxinv :" + strservicetaxinv);

						String StrQrytottaxinv = "select CAST(round(("+strgrandtotinv+"/"+servicetax+")*100) AS SIGNED) as tottaxdedaction";
						LogLevel.DEBUG(5, new Throwable(), "StrQrytottaxinv :" + StrQrytottaxinv);

						Hashtable httottaxinv = qm.getRow(StrQrytottaxinv); 
						strtottaxinv=String.valueOf(httottaxinv.get("tottaxdedaction"));
						LogLevel.DEBUG(5, new Throwable(), "strtottaxinv :" + strtottaxinv);
					
				

						String SqlQrysum_Online_Payment = "select CAST(SUM(price)*quantity AS SIGNED)AS OnlineAmountPayed from inverter_order_details where "+strConditions1+" and retailer_name='"+retailer+"' and order_status='Customer Contacted' and order_reasons='installed' and payment_mode='Online'";
						LogLevel.DEBUG(5,new Throwable(),"SqlQrysum_Online_Payment:"+SqlQrysum_Online_Payment);

						Hashtable htgrandtot_Online_Payment = qm.getRow(SqlQrysum_Online_Payment); 
						strgrandtot_Online_Paymentinv=String.valueOf(htgrandtot_Online_Payment.get("OnlineAmountPayed"));
						LogLevel.DEBUG(5, new Throwable(), "strgrandtot_Online_Paymentinv :" + strgrandtot_Online_Paymentinv);
						
						if(strgrandtot_Online_Paymentinv.equals("NULL") || strgrandtot_Online_Paymentinv.equals(null)|| strgrandtot_Online_Paymentinv.equals("null") || strgrandtot_Online_Paymentinv.equals("") )
							strgrandtot_Online_Paymentinv="0";
						else
							strgrandtot_Online_Paymentinv=strgrandtot_Online_Paymentinv;

						int strgrandtot_Online_Payment_int = Integer.parseInt(strgrandtot_Online_Paymentinv);
						int strgrandtot_int = Integer.parseInt(strgrandtotinv);

						int strgrandtot_tmp_int=strgrandtot_int-strgrandtot_Online_Payment_int;
										
						 Total_Reconciled_Amountinv = Integer.toString(strgrandtot_tmp_int);
						
					}
			}
			else
			{
					String servicepercentage="0.5";
					//####### Condition for Cash
					
					String strConditions_ser=" date(a.updated_date) between '"+strFromDate+"' and '"+strToDate+"'";

					
					String StrQrySerPrice = "select a.services_type,a.services_package,a.service_price_discount,CAST(round(service_commission_amount) AS SIGNED) as comission,CAST(round(SUM(service_commission_amount)) AS SIGNED) as ourcommision,a.product_type as batterytype from service_order_details a,  percentage c where "+strConditions_ser+" and a.order_status='Customer Contacted' and a.payment_mode not in('Credit Card','Debit Card','Online') and a.order_reasons='installed' and a.retailer_name='"+retailer+"'  and a.city='"+city+"' and c.city=a.city group by a.services_package,a.service_price_discount";
					
					
					LogLevel.DEBUG(5, new Throwable(), "StrQrySerPrice :" + StrQrySerPrice);
					
					RetServiceInoiceVector=qm.executeQuery(StrQrySerPrice);
					LogLevel.DEBUG(5,new Throwable(),"RetInoiceVector:"+RetInoiceVector );		

					//####### Condition for Debit Card
					
					String StrQryPrice_Debit_Card = "select a.services_type,a.services_package,a.service_price_discount,CAST(round((service_commission_amount)) AS SIGNED) as comission,CAST(round(SUM(service_commission_amount)) AS SIGNED) as ourcommision,a.product_type as batterytype from service_order_details a,  percentage c where "+strConditions_ser+" and a.order_status='Customer Contacted' and a.payment_mode='Debit Card' and a.order_reasons='installed' and a.retailer_name='"+retailer+"'  and a.city='"+city+"' and c.city=a.city group by a.services_package,a.service_price_discount";
					
					
					LogLevel.DEBUG(5, new Throwable(), "StrQryPrice_Debit_Card :" + StrQryPrice_Debit_Card);
					
					RetServiceInoiceVector_Debit_Card=qm.executeQuery(StrQryPrice_Debit_Card);
					LogLevel.DEBUG(5,new Throwable(),"RetServiceInoiceVector_Debit_Card:"+RetServiceInoiceVector_Debit_Card );	


					//####### Condition for Credit Card

					String StrQryPrice_Credit_Card = "select a.services_type,a.services_package,a.service_price_discount,CAST(round((service_commission_amount)) AS SIGNED) as comission,CAST(round(SUM(service_commission_amount)) AS SIGNED) as ourcommision,a.product_type as batterytype from service_order_details a,  percentage c where "+strConditions_ser+" and a.order_status='Customer Contacted' and a.payment_mode='Credit Card' and a.order_reasons='installed' and a.retailer_name='"+retailer+"'  and a.city='"+city+"' and c.city=a.city group by a.services_package,a.service_price_discount";
					
					LogLevel.DEBUG(5, new Throwable(), "StrQryPrice_Credit_Card :" + StrQryPrice_Credit_Card);

					RetServiceInoiceVector_Credit_Card=qm.executeQuery(StrQryPrice_Credit_Card);
					LogLevel.DEBUG(5,new Throwable(),"RetServiceInoiceVector_Credit_Card:"+RetServiceInoiceVector_Credit_Card);

					//####### Condition for Online

					//String StrQryPrice_Online = "select a.services_type,a.services_package,a.service_price_discount,CAST(service_commission_amount) as comission,CAST(round((service_commission_amount))*(count(a.services_package)) AS SIGNED) as ourcommision,a.product_type as batterytype from service_order_details a,  percentage c where "+strConditions_ser+" and a.order_status='Customer Contacted' and a.payment_mode='Online' and a.order_reasons='installed' and a.retailer_name='"+retailer+"'  and a.city='"+city+"' and c.city=a.city group by a.services_package,a.service_price_discount";
					String StrQryPrice_Online = "select a.services_type,a.services_package,a.service_price_discount,CAST(round((service_commission_amount)) AS SIGNED) as comission,CAST(round(SUM(service_commission_amount)) AS SIGNED) as ourcommision,a.product_type as batterytype from service_order_details a,  percentage c where "+strConditions_ser+" and a.order_status='Customer Contacted' and a.payment_mode='Online' and a.order_reasons='installed' and a.retailer_name='"+retailer+"'  and a.city='"+city+"' and c.city=a.city group by a.services_package,a.service_price_discount";
					LogLevel.DEBUG(5, new Throwable(), "StrQryPrice_Online :" + StrQryPrice_Online);
					
					RetServiceInoiceVector_Online=qm.executeQuery(StrQryPrice_Online);
					LogLevel.DEBUG(5,new Throwable(),"RetServiceInoiceVector_Online:"+RetServiceInoiceVector_Online );
					
					
					String Strqry1 = "select CAST(round(sum((service_commission_amount))) AS SIGNED) as grandtotal from service_order_details where "+strConditions1+" and retailer_name='"+retailer+"' and order_status='Customer Contacted' and order_reasons='installed'";
					LogLevel.DEBUG(5, new Throwable(), "Strqry1 :" + Strqry1);
					
					
					Hashtable htgrandtot_service_commission = qm.getRow(Strqry1); 
					String strgrandtot_service_commission=String.valueOf(htgrandtot_service_commission.get("grandtotal"));
					LogLevel.DEBUG(5, new Throwable(), "strgrandtot_service_commission :" + strgrandtot_service_commission);
					
					if(strgrandtot_service_commission.equals("NULL") || strgrandtot_service_commission.equals(null)|| strgrandtot_service_commission.equals("null") || strgrandtot_service_commission.equals("") )
					{
						LogLevel.DEBUG(5, new Throwable(), "Inside NULL if condition:" + Strqry1);						
						 Total_Reconciled_Amountser="0";
						 strgrandtotser="0";
						 strgrandtot_Online_Paymentser="0";
						 strtottaxser="0";
						 strservicetaxser="0";
					}
					else
					{
						LogLevel.DEBUG(5, new Throwable(), "Inside NULL else condition:" + Strqry1);
					

					//########## Total online payment recived 
				
					//Code commented due to null

						ArrayList htavser=new ArrayList();
						htavser=qm.getField(Strqry1);
						String batids="";
						for(int i=0;i<htavser.size();i++)
						{
						if(batids.equals(""))
						batids=htavser.get(i).toString();
						else
						batids=batids+"+"+htavser.get(i).toString();
						}
						LogLevel.DEBUG(5,new Throwable(),"batids:"+batids);

						String SqlQrysumser = "select CAST(round("+batids+") AS SIGNED) as grandtotal";
						LogLevel.DEBUG(5,new Throwable(),"SqlQrysumser:"+SqlQrysumser);
						
						Hashtable htgrandtotser = qm.getRow(SqlQrysumser); 
						 strgrandtotser=String.valueOf(htgrandtotser.get("grandtotal"));
						LogLevel.DEBUG(5, new Throwable(), "strgrandtotser :" + strgrandtotser);

						String StrQryservicetaxser = "select CAST(round(("+strgrandtotser+"-(("+strgrandtotser+"/"+servicetax+")*100))) AS SIGNED) as servicetaxyprice";
						LogLevel.DEBUG(5, new Throwable(), "StrQryservicetaxser :" + StrQryservicetaxser);

						Hashtable htservicetaxser = qm.getRow(StrQryservicetaxser); 
						strservicetaxser=String.valueOf(htservicetaxser.get("servicetaxyprice"));
						LogLevel.DEBUG(5, new Throwable(), "strservicetaxser :" + strservicetaxser);

						String StrQrytottaxser = "select CAST(round(("+strgrandtotser+"/"+servicetax+")*100) AS SIGNED) as tottaxdedaction";
						LogLevel.DEBUG(5, new Throwable(), "StrQrytottaxser :" + StrQrytottaxser);

						Hashtable httottaxser = qm.getRow(StrQrytottaxser); 
						strtottaxser=String.valueOf(httottaxser.get("tottaxdedaction"));
						LogLevel.DEBUG(5, new Throwable(), "strtottaxser :" + strtottaxser);
					
				

						String SqlQrysum_Online_Payment = "select CAST(SUM(service_price_discount) AS SIGNED)AS OnlineAmountPayed from service_order_details where "+strConditions1+" and retailer_name='"+retailer+"' and order_status='Customer Contacted' and order_reasons='installed' and payment_mode='Online'";
						LogLevel.DEBUG(5,new Throwable(),"SqlQrysum_Online_Payment:"+SqlQrysum_Online_Payment);

						Hashtable htgrandtot_Online_Payment = qm.getRow(SqlQrysum_Online_Payment); 
						strgrandtot_Online_Paymentser=String.valueOf(htgrandtot_Online_Payment.get("OnlineAmountPayed"));
						LogLevel.DEBUG(5, new Throwable(), "strgrandtot_Online_Paymentser :" + strgrandtot_Online_Paymentser);
						
						if(strgrandtot_Online_Paymentser.equals("NULL") || strgrandtot_Online_Paymentser.equals(null)|| strgrandtot_Online_Paymentser.equals("null") || strgrandtot_Online_Paymentser.equals("") )
							strgrandtot_Online_Paymentser="0";
						else
							strgrandtot_Online_Paymentser=strgrandtot_Online_Paymentser;

						int strgrandtot_Online_Payment_int = Integer.parseInt(strgrandtot_Online_Paymentser);
						int strgrandtot_int = Integer.parseInt(strgrandtotser);

						int strgrandtot_tmp_int=strgrandtot_int-strgrandtot_Online_Payment_int;
										
						Total_Reconciled_Amountser = Integer.toString(strgrandtot_tmp_int);
					
					

				}
				
			}
		}
				// Table Name for loop
				String strSqlQry4="";

				if(retailer.contains("Amaron-Pitstop"))
				{
				strSqlQry4 = "select gstid from amaronexpressdb.gstdetailstable where retailername='"+retailer+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry4 :"+strSqlQry4 );
				}
				else
				{
				strSqlQry4 = "select gstid from gstdetailstable where retailername='"+retailer+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry4 :"+strSqlQry4 );
				}

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
				

				//Code Added by Bharath for generating sequence number

				String Sequence_Query = "select seq_number from retailer_invoice where seq_number!='0' and invoice_type='battery' order by ret_inv_id desc limit 1";

				LogLevel.DEBUG(5,new Throwable(),"Sequence_Query :"+Sequence_Query );
			
			
				Hashtable htSeq_number = qm.getRow(Sequence_Query); 
								
				int invoice_code = 0;
				int Seq_number_Add = 0;
				if(htSeq_number.isEmpty())
				{ 
					invoice_code = 1;
					String verificationcode1 = Integer.toString(invoice_code);
					verificationcode ="ASIST-BW-00000"+verificationcode1+"";
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
				
					if(Seq_number_Add <9){
						
					String verificationcode1 = Integer.toString(invoice_code);
					verificationcode ="ASIST-BW-00000"+verificationcode1+"";
					LogLevel.DEBUG(5, new Throwable(), "verificationcode 9:" + verificationcode);
					
					
					} else if(Seq_number_Add < 100){
					
						String verificationcode1 = Integer.toString(invoice_code);
						verificationcode ="ASIST-BW-0000"+verificationcode1+"";
						LogLevel.DEBUG(5, new Throwable(), "verificationcode 99:" + verificationcode);
					
						
					} else if(Seq_number_Add < 1000){
						
						String verificationcode1 = Integer.toString(invoice_code);
						verificationcode ="ASIST-BW-000"+verificationcode1+"";
						LogLevel.DEBUG(5, new Throwable(), "verificationcode 999:" + verificationcode);
						
					} else if(Seq_number_Add < 10000){
						
						String verificationcode1 = Integer.toString(invoice_code);
						verificationcode ="ASIST-BW-00"+verificationcode1+"";
						LogLevel.DEBUG(5, new Throwable(), "verificationcode 9999:" + verificationcode);
						
					} else if(Seq_number_Add < 100000){
						
						String verificationcode1 = Integer.toString(invoice_code);
						verificationcode ="ASIST-BW-0"+verificationcode1+"";
						LogLevel.DEBUG(5, new Throwable(), "verificationcode 99999:" + verificationcode);
						
					} else{
						
						String verificationcode1 = Integer.toString(invoice_code);
						verificationcode ="ASIST-BW-"+verificationcode1+"";
						LogLevel.DEBUG(5, new Throwable(), "verificationcode 999999:" + verificationcode);
						
					}
				
				}
				

			//Code Added by Bharath for generating sequence number

					//For Batteries headings and values
					
					ArrayList arrExcelHeader1 = new ArrayList();
					arrExcelHeader1.add("Battery model");
					//arrExcelHeader1.add("price");
					arrExcelHeader1.add("Quantity");
					arrExcelHeader1.add("PreTax-SRP");
					arrExcelHeader1.add("PreTax-ERP");
					arrExcelHeader1.add("Commission");
					arrExcelHeader1.add("Transaction Charge");			
					arrExcelHeader1.add("Battery Replacement");
					//arrExcelHeader1.add("Delivery Charge");
					arrExcelHeader1.add("Total Comm"); 

					ArrayList dbHeaders2 = new ArrayList();
					dbHeaders2.add("battery_model");
					//dbHeaders2.add("price");
					dbHeaders2.add("quantity");
					dbHeaders2.add("14percentvat");
					dbHeaders2.add("erp_pre_tax");
					dbHeaders2.add("comission");
					dbHeaders2.add("Transaction_Amount");					
					dbHeaders2.add("order_type");
					//dbHeaders2.add("Delivery_Charge");
					dbHeaders2.add("ourcommision");
					
					
					//For Inverters headings and values
					
					ArrayList arrInverterExcelHeader1 = new ArrayList();
					arrInverterExcelHeader1.add("Inveter model");
					//arrExcelHeader1.add("price");
					arrInverterExcelHeader1.add("Quantity");
					arrInverterExcelHeader1.add("PreTax-SRP");
					arrInverterExcelHeader1.add("PreTax-ERP");
					arrInverterExcelHeader1.add("Commission");
					arrInverterExcelHeader1.add("Transaction Charge");
					arrInverterExcelHeader1.add("Total Comm"); 

					ArrayList dbInverterHeaders2 = new ArrayList();
					dbInverterHeaders2.add("inverter_model");
					//dbInverterHeaders2.add("price");
					dbInverterHeaders2.add("quantity");
					dbInverterHeaders2.add("14percentvat");
					dbInverterHeaders2.add("erp_pre_tax");
					dbInverterHeaders2.add("comission");
					dbInverterHeaders2.add("Transaction_Amount");
					dbInverterHeaders2.add("ourcommision");
					
					
					//For Services headings and values
										
					ArrayList arrExcelServiceHeader1 = new ArrayList();
					arrExcelServiceHeader1.add("Services Type");
					arrExcelServiceHeader1.add("Battery Type");
					arrExcelServiceHeader1.add("SRP");
					arrExcelServiceHeader1.add("Commission");
					arrExcelServiceHeader1.add("Total Comm"); 

					ArrayList dbServiceHeaders2 = new ArrayList();
					dbServiceHeaders2.add("services_type");
					dbServiceHeaders2.add("batterytype");
					dbServiceHeaders2.add("service_price_discount");
					dbServiceHeaders2.add("comission");
					dbServiceHeaders2.add("ourcommision");
					

					//String excelName = "invoice.pdf";
					String excelName = ""+retailer+"-ASPL-Invoice-"+verificationcode+".pdf";
					LogLevel.DEBUG(5, new Throwable(), "excelName :" + excelName);
					String strPDFFilePath = strPDFFilePath1+ "/" +currentYear+ "/" +months+ "/" +"battery"+ "/" +"invoice"+ "/" +city;
					LogLevel.DEBUG(5, new Throwable(), "strPDFFilePath :" + strPDFFilePath);
					File excelUploadPathCreate = new File(strPDFFilePath);
					LogLevel.DEBUG(5, new Throwable(), "excelUploadPathCreate :" + excelUploadPathCreate);
					if(excelUploadPathCreate.mkdirs())
					{
					LogLevel.DEBUG(5,new Throwable()," PDF for invoice	Name directory created :  "	+ excelUploadPathCreate);
					}

					//strPDFRelFilePath	= strPDFRelFilePath +"/"+ currentYear + "/" +months;

					String excelFile = excelUploadPathCreate+"/"+excelName;
					LogLevel.DEBUG(5, new Throwable(), "excelFile :" + excelFile);
					File file=new File(excelFile);
					if(!file.exists()) 
					file.createNewFile(); 
					FileWriter fw=new FileWriter(file);	
					GenerateExcelbattery generateExcel	= new GenerateExcelbattery(); 
					
					
					LogLevel.DEBUG(5, new Throwable(), "excelFile :" + Total_Reconciled_Amountinv);
					LogLevel.DEBUG(5, new Throwable(), "strgrandtotinv :" + strgrandtotinv);
					LogLevel.DEBUG(5, new Throwable(), "strgrandtot_Online_Paymentinv :" + strgrandtot_Online_Paymentinv);
					LogLevel.DEBUG(5, new Throwable(), "strservicetaxinv :" + strservicetaxinv);
					LogLevel.DEBUG(5, new Throwable(), "strtottaxinv :" + strtottaxinv);
					LogLevel.DEBUG(5, new Throwable(), "strgrandtotinv :" + strgrandtotinv);					
					

					LogLevel.DEBUG(5, new Throwable(), "Total_Reconciled_Amountser :" + Total_Reconciled_Amountser);
					LogLevel.DEBUG(5, new Throwable(), "strgrandtotser :" + strgrandtotser);
					LogLevel.DEBUG(5, new Throwable(), "strgrandtot_Online_Paymentser :" + strgrandtot_Online_Paymentser);
					LogLevel.DEBUG(5, new Throwable(), "strservicetaxser :" + strservicetaxser);
					LogLevel.DEBUG(5, new Throwable(), "strtottaxser :" + strtottaxser);
					LogLevel.DEBUG(5, new Throwable(), "strgrandtotser :" + strgrandtotser);
					
					
					strgrandtot=Integer.toString((Integer.parseInt(strgrandtot))+(Integer.parseInt(strgrandtotinv))+(Integer.parseInt(strgrandtotser)));
					Total_Reconciled_Amount=Integer.toString((Integer.parseInt(Total_Reconciled_Amount))+(Integer.parseInt(Total_Reconciled_Amountinv))+(Integer.parseInt(Total_Reconciled_Amountser)));
					strgrandtot_Online_Payment=Integer.toString((Integer.parseInt(strgrandtot_Online_Payment))+(Integer.parseInt(strgrandtot_Online_Paymentinv))+(Integer.parseInt(strgrandtot_Online_Paymentser)));
					strservicetax=Integer.toString((Integer.parseInt(strservicetax))+(Integer.parseInt(strservicetaxinv))+(Integer.parseInt(strservicetaxser)));
					strtottax=Integer.toString((Integer.parseInt(strtottax))+(Integer.parseInt(strtottaxinv))+(Integer.parseInt(strtottaxser)));
					
					LogLevel.DEBUG(5, new Throwable(), "Total_Reconciled_Amountinv finally :" + Total_Reconciled_Amount);
										
					generateExcel.writeToPdf1(retailer,city,verificationcode,txtFromDate,txtToDate,arrExcelHeader1,dbHeaders2,RetInoiceVector,RetInoiceVector_Debit_Card,RetInoiceVector_Credit_Card,RetInoiceVector_Online,arrInverterExcelHeader1,dbInverterHeaders2,RetInverterInoiceVector,RetInverterInoiceVector_Debit_Card,RetInverterInoiceVector_Credit_Card,RetInverterInoiceVector_Online,arrExcelServiceHeader1,dbServiceHeaders2,RetServiceInoiceVector,RetServiceInoiceVector_Debit_Card,RetServiceInoiceVector_Credit_Card,RetServiceInoiceVector_Online,strgrandtot,Total_Reconciled_Amount,strgrandtot_Online_Payment,strservicetax,strtottax,excelFile,strCMSServerIP,key,retailerid);
					
					
					
					String strPdfURL="https://"+strCMSServerIP+"/bookbattery/userdata/billing/salesorders/"+currentYear+"/"+months+"/"+"battery"+"/"+"invoice"+"/"+city+"/"+excelName;
					LogLevel.DEBUG(5, new Throwable(), "strPdfURL : " + strPdfURL);
					/* Ends to generate retailers invoice */

					/* Starts to generate retailers orders summary */
					
					/* Starts to generate retailers orders summary  for Batteries*/
					
					String Strquerys = "select order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailis,city,battery_brand,battery_model,price,pincode,pdfurl,bat_type,veh_name,veh_model,area,witholdbatprice,order_status,quantity,creation_date from battery_order_details where "+strConditions+" and order_status='Customer Contacted' and order_reasons='installed' and payment_mode not in('Online') and retailer_name='"+retailer+"' ";
					LogLevel.DEBUG(5, new Throwable(), "Strquerys : " + Strquerys);
					
					
					/* Query to get only online orders starts here */
					
					String Strquerys1 = "select order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailis,city,battery_brand,battery_model,price,pincode,pdfurl,bat_type,veh_name,veh_model,area,witholdbatprice,order_status,quantity,creation_date from battery_order_details where "+strConditions+" and order_status='Customer Contacted' and order_reasons='installed' and payment_mode='Online' and retailer_name='"+retailer+"'";
									
					/* Query to get only online orders ends here */
					

					LogLevel.DEBUG(5, new Throwable(), "Strquerys1 : " + Strquerys1);
					
					Vector dailyVectorwithcat=qm.executeQuery(Strquerys);
					LogLevel.DEBUG(5,new Throwable(),"dailyVectorwithcat:"+dailyVectorwithcat );

					Vector dailyVectorwithcat1=qm.executeQuery(Strquerys1);
					LogLevel.DEBUG(5,new Throwable(),"dailyVectorwithcat1:"+dailyVectorwithcat1 );

					String Rettitle;
					String Rettitle_excelname;
					String Rettitle1;

					if(dailyVectorwithcat1.isEmpty())
					{
						 	if(dailyVectorwithcat.isEmpty())
							{
								Rettitle = ""; 
								Rettitle1 = ""; 
							}
							else
							{
								Rettitle = retailer+" - Order Summary"; 
								Rettitle1 = ""; 
							}
					}
					else
					{
						if(dailyVectorwithcat.isEmpty())
						{
							//Rettitle = retailer+" - Offline Order Summary"; 
							Rettitle1 = retailer+" - Online Order Summary"; 
							Rettitle ="";
						}
						else
						{
							Rettitle = retailer+" - Offline Order Summary"; 
							Rettitle1 = retailer+" - Online Order Summary"; 
						}
					}
				

				/* Ends to generate retailers orders summary  for Batteries*/
				
				/* Starts to generate retailers orders summary  for Inverters*/
					
						String Strquerys_inv = "select order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobile_number,retailer_emailid,city,inverter_brand,inverter_model,inverter_capacity,price,pincode,area,order_status,quantity,creation_date from inverter_order_details where "+strConditions+" and  order_reasons='installed' and order_status='Customer Contacted' and payment_mode not in('Online') and retailer_name='"+retailer+"' ";
						LogLevel.DEBUG(5, new Throwable(), "Strquerys_inv : " + Strquerys_inv);						
						
						/* Query to get the only Online orders starts here */
						String Strquerys1_inv = "select order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobile_number,retailer_emailid,city,inverter_brand,inverter_model,inverter_capacity,price,pincode,area,order_status,quantity,creation_date from inverter_order_details where "+strConditions+" and  order_reasons='installed' and order_status='Customer Contacted' and payment_mode='Online' and retailer_name='"+retailer+"' ";
						/* Query to get the only Online orders ends here */
						LogLevel.DEBUG(5, new Throwable(), "Strquerys1_inv : " + Strquerys1_inv);
						

						Vector dailyInvVectorwithcat=qm.executeQuery(Strquerys_inv);
						LogLevel.DEBUG(5,new Throwable(),"dailyInvVectorwithcat:"+dailyInvVectorwithcat );
						
								
						Vector dailyInvVectorwithcat1=qm.executeQuery(Strquerys1_inv);
						LogLevel.DEBUG(5,new Throwable(),"dailyInvVectorwithcat1:"+dailyInvVectorwithcat1 );
						

						String RetInvtitle;
						String RetInvtitle1;
						
						if(dailyInvVectorwithcat1.isEmpty())
						{

							 if(dailyInvVectorwithcat.isEmpty())
							{
								RetInvtitle = ""; 
								RetInvtitle1 = ""; 
								
							}
							else
							{
								 RetInvtitle = retailer+" - Order Summary"; 
								 RetInvtitle1 = ""; 
							}
							 
						}
						else
						{
							if(dailyInvVectorwithcat.isEmpty())
							{
								//RetInvtitle = retailer+" - Offline Order Summary"; 
								RetInvtitle1 = retailer+" - Online Order Summary"; 
								RetInvtitle ="";
							}
							else
							{
								RetInvtitle = retailer+" - Offline Order Summary"; 
								RetInvtitle1 = retailer+" - Online Order Summary"; 
							}
						}

				/* Ends to generate retailers orders summary  for Inverters*/
				

					/* Starts to generate retailers orders summary  for Services*/

						String Strquerys_ser = "select order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailid,city,services_type,services_package,service_price_mrp,pincode,pdfurl,veh_name,veh_model,area,service_price_discount,order_status,creation_date,product_type from service_order_details where "+strConditions+" and order_status='Customer Contacted' and payment_mode not in('Online') and order_reasons='installed' and retailer_name='"+retailer+"' ";

						LogLevel.DEBUG(5, new Throwable(), "Strquerys_ser : " + Strquerys_ser);
						/* Query to get the only Online orders starts here */
						
						String Strquerys1_ser = "select order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailid,city,services_type,services_package,service_price_mrp,pincode,pdfurl,veh_name,veh_model,area,service_price_discount,order_status,creation_date,product_type from service_order_details where "+strConditions+" and order_status='Customer Contacted' and payment_mode='Online' and order_reasons='installed' and retailer_name='"+retailer+"' ";
						

						/* Query to get the only Online orders ends here */
						LogLevel.DEBUG(5, new Throwable(), "Strquerys1_ser : " + Strquerys1_ser);
						
						Vector dailySerVectorwithcat=qm.executeQuery(Strquerys_ser);
						LogLevel.DEBUG(5,new Throwable(),"dailySerVectorwithcat:"+dailySerVectorwithcat);
														
						Vector dailySerVectorwithcat1=qm.executeQuery(Strquerys1_ser);
						LogLevel.DEBUG(5,new Throwable(),"dailySerVectorwithcat1:"+dailySerVectorwithcat1);
						

						String RetSertitle;
						String RetSertitle1;
						
						if(dailySerVectorwithcat1.isEmpty())
						{
							if(dailySerVectorwithcat.isEmpty())
							{
								RetSertitle = ""; 
								RetSertitle1 = ""; 
								
							}
							else
							{
								 RetSertitle = retailer+" - Order Summary"; 
								 RetSertitle1 = ""; 
							}

						}
						else
						{
							if(dailySerVectorwithcat.isEmpty())
							{
								//RetSertitle = retailer+" - Offline Order Summary"; 
								RetSertitle1 = retailer+" - Online Order Summary"; 
								RetSertitle ="";
							}
							else
							{
								RetSertitle = retailer+" - Offline Order Summary"; 
								RetSertitle1 = retailer+" - Online Order Summary"; 
							}
						}

				/* Ends to generate retailers orders summary  for Services*/

				
					String ReportDate = "Report Duration : " +fromDate +  " To "  +toDate;
					Rettitle_excelname = retailer+" - Order Summary";
					 String excelName1 = ""+Rettitle_excelname+".pdf";

					ArrayList arrExcelHeaderwithcat = new ArrayList();
					arrExcelHeaderwithcat.add("Order No");
					arrExcelHeaderwithcat.add("Customer Name");
					arrExcelHeaderwithcat.add("Customer Email");
					arrExcelHeaderwithcat.add("Customer Mobile");
					arrExcelHeaderwithcat.add("Battery");
					arrExcelHeaderwithcat.add("Model");
					arrExcelHeaderwithcat.add("City");
					arrExcelHeaderwithcat.add("Price");
					arrExcelHeaderwithcat.add("price(OBRP)");
					arrExcelHeaderwithcat.add("Quantity");
					arrExcelHeaderwithcat.add("Date");

					ArrayList dbHeaderswithcat = new ArrayList();
					dbHeaderswithcat.add("order_number");
					dbHeaderswithcat.add("consumer_name");
					dbHeaderswithcat.add("consumer_emailid");
					dbHeaderswithcat.add("consumer_mobnumber");
					dbHeaderswithcat.add("battery_brand");
					dbHeaderswithcat.add("battery_model"); 
					dbHeaderswithcat.add("city");
					dbHeaderswithcat.add("price");
					dbHeaderswithcat.add("witholdbatprice");
					dbHeaderswithcat.add("quantity");
					dbHeaderswithcat.add("creation_date");
					
					
					
					ArrayList arrExcelHeaderwithcat1 = new ArrayList();
					arrExcelHeaderwithcat1.add("Order No");
					arrExcelHeaderwithcat1.add("Customer Name");
					arrExcelHeaderwithcat1.add("Customer Email");
					arrExcelHeaderwithcat1.add("Customer Mobile");
					arrExcelHeaderwithcat1.add("Battery");
					arrExcelHeaderwithcat1.add("Model");
					arrExcelHeaderwithcat1.add("City");
					arrExcelHeaderwithcat1.add("Price");
					arrExcelHeaderwithcat1.add("price(OBRP)");
					arrExcelHeaderwithcat1.add("Quantity");
					arrExcelHeaderwithcat1.add("Date");

					ArrayList dbHeaderswithcat1 = new ArrayList();
					dbHeaderswithcat1.add("order_number");
					dbHeaderswithcat1.add("consumer_name");
					dbHeaderswithcat1.add("consumer_emailid");
					dbHeaderswithcat1.add("consumer_mobnumber");
					dbHeaderswithcat1.add("battery_brand");
					dbHeaderswithcat1.add("battery_model"); 
					dbHeaderswithcat1.add("city");
					dbHeaderswithcat1.add("price");
					dbHeaderswithcat1.add("witholdbatprice");
					dbHeaderswithcat1.add("quantity");
					dbHeaderswithcat1.add("creation_date");
					
					
				/**********Inverter Order Details********************/			
					
					
						ArrayList arrExcelInvHeaderwithcat = new ArrayList();
						arrExcelInvHeaderwithcat.add("Order No");
						arrExcelInvHeaderwithcat.add("Customer Name");
						arrExcelInvHeaderwithcat.add("Customer Email");
						arrExcelInvHeaderwithcat.add("Customer Mobile");
						arrExcelInvHeaderwithcat.add("Inverter");
						arrExcelInvHeaderwithcat.add("Model");
						arrExcelInvHeaderwithcat.add("City");
						arrExcelInvHeaderwithcat.add("Price");
						arrExcelInvHeaderwithcat.add("Quantity");
						arrExcelInvHeaderwithcat.add("Date");

						ArrayList dbHeadersInvwithcat = new ArrayList();
						dbHeadersInvwithcat.add("order_number");
						dbHeadersInvwithcat.add("consumer_name");
						dbHeadersInvwithcat.add("consumer_emailid");
						dbHeadersInvwithcat.add("consumer_mobnumber");
						dbHeadersInvwithcat.add("inverter_brand");
						dbHeadersInvwithcat.add("inverter_model");
						dbHeadersInvwithcat.add("city");
						dbHeadersInvwithcat.add("price");
						dbHeadersInvwithcat.add("quantity");
						dbHeadersInvwithcat.add("creation_date");
						

						
						ArrayList arrExcelInvHeaderwithcat1 = new ArrayList();
						arrExcelInvHeaderwithcat1.add("Order No");
						arrExcelInvHeaderwithcat1.add("Customer Name");
						arrExcelInvHeaderwithcat1.add("Customer Email");
						arrExcelInvHeaderwithcat1.add("Customer Mobile");
						arrExcelInvHeaderwithcat1.add("Inverter");
						arrExcelInvHeaderwithcat1.add("Model");
						arrExcelInvHeaderwithcat1.add("City");
						arrExcelInvHeaderwithcat1.add("Price");
						arrExcelInvHeaderwithcat1.add("Quantity");
						arrExcelInvHeaderwithcat1.add("Date");

						ArrayList dbHeadersInvwithcat1 = new ArrayList();
						dbHeadersInvwithcat1.add("order_number");
						dbHeadersInvwithcat1.add("consumer_name");
						dbHeadersInvwithcat1.add("consumer_emailid");
						dbHeadersInvwithcat1.add("consumer_mobnumber");
						dbHeadersInvwithcat1.add("inverter_brand");
						dbHeadersInvwithcat1.add("inverter_model");
						dbHeadersInvwithcat1.add("city");
						dbHeadersInvwithcat1.add("price");
						dbHeadersInvwithcat1.add("quantity");
						dbHeadersInvwithcat1.add("creation_date");
						
						/**********Service Order Details********************/				
						
						ArrayList arrExcelSerHeaderwithcat = new ArrayList();
						arrExcelSerHeaderwithcat.add("Order No");
						arrExcelSerHeaderwithcat.add("Customer Name");
						arrExcelSerHeaderwithcat.add("Customer Email");
						arrExcelSerHeaderwithcat.add("Customer Mobile");
						arrExcelSerHeaderwithcat.add("Services Type");
						arrExcelSerHeaderwithcat.add("Battery Type");
						arrExcelSerHeaderwithcat.add("City");
						arrExcelSerHeaderwithcat.add("Price");
						arrExcelSerHeaderwithcat.add("Discount Price");
						arrExcelSerHeaderwithcat.add("Creation Date");

						ArrayList dbHeadersSerwithcat = new ArrayList();
						dbHeadersSerwithcat.add("order_number");
						dbHeadersSerwithcat.add("consumer_name");
						dbHeadersSerwithcat.add("consumer_emailid");
						dbHeadersSerwithcat.add("consumer_mobnumber");						
						dbHeadersSerwithcat.add("services_package"); 
						dbHeadersSerwithcat.add("product_type");
						dbHeadersSerwithcat.add("city");
						dbHeadersSerwithcat.add("service_price_mrp");
						dbHeadersSerwithcat.add("service_price_discount");
						dbHeadersSerwithcat.add("creation_date");
						

						ArrayList arrExcelSerHeaderwithcat1 = new ArrayList();
						arrExcelSerHeaderwithcat1.add("Order No");
						arrExcelSerHeaderwithcat1.add("Customer Name");
						arrExcelSerHeaderwithcat1.add("Customer Email");
						arrExcelSerHeaderwithcat1.add("Customer Mobile");
						arrExcelSerHeaderwithcat1.add("Services Type");
						arrExcelSerHeaderwithcat1.add("Battery Type");
						arrExcelSerHeaderwithcat1.add("City");
						arrExcelSerHeaderwithcat1.add("Price");
						arrExcelSerHeaderwithcat1.add("Discount Price");
						arrExcelSerHeaderwithcat1.add("Creation Date");

						ArrayList dbHeadersSerwithcat1 = new ArrayList();
						dbHeadersSerwithcat1.add("order_number");
						dbHeadersSerwithcat1.add("consumer_name");
						dbHeadersSerwithcat1.add("consumer_emailid");
						dbHeadersSerwithcat1.add("consumer_mobnumber");
						dbHeadersSerwithcat1.add("services_package"); 
						dbHeadersSerwithcat.add("product_type");
						dbHeadersSerwithcat1.add("city");
						dbHeadersSerwithcat1.add("service_price_mrp");
						dbHeadersSerwithcat1.add("service_price_discount");
						dbHeadersSerwithcat1.add("creation_date");
					

					String strPDFFilePath12 = strPDFFilePath1+ "/" +currentYear+ "/" +months+ "/" +"battery"+ "/" +"orderedsummary"+ "/" +city;
					LogLevel.DEBUG(5, new Throwable(), "strPDFFilePath12 :" + strPDFFilePath12);
					File excelUploadPathCreate1 = new File(strPDFFilePath12);
					LogLevel.DEBUG(5, new Throwable(), "excelUploadPathCreate1 :" + excelUploadPathCreate1);
					if(excelUploadPathCreate1.mkdirs())
					{
					LogLevel.DEBUG(3,new Throwable()," PDF for invoice	Name directory created :  "	+ excelUploadPathCreate1);
					}

					//strPDFRelFilePath	= strPDFRelFilePath +"/"+ currentYear + "/" +months;

					String excelFile1 = excelUploadPathCreate1+"/"+excelName1;
					LogLevel.DEBUG(5, new Throwable(), "excelFile1 :" + excelFile1);
					File file1=new File(excelFile1); 
					if(!file1.exists()) 
					file1.createNewFile(); 
					FileWriter fw1=new FileWriter(file1);
					GenerateExcelbattery generateExcel1	= new GenerateExcelbattery();
					//generateExcel1.writeToPdf(arrExcelHeaderwithcat,dbHeaderswithcat,dailyVectorwithcat,excelFile1,strCMSServerIP,Rettitle,ReportDate);
					generateExcel1.writeToPdf(arrExcelHeaderwithcat,dbHeaderswithcat,arrExcelHeaderwithcat1,dbHeaderswithcat1,dailyVectorwithcat,dailyVectorwithcat1,arrExcelInvHeaderwithcat,dbHeadersInvwithcat,arrExcelInvHeaderwithcat1,dbHeadersInvwithcat1,dailyInvVectorwithcat,dailyInvVectorwithcat1,arrExcelSerHeaderwithcat,dbHeadersSerwithcat,arrExcelSerHeaderwithcat1,dbHeadersSerwithcat1,dailySerVectorwithcat,dailySerVectorwithcat1,excelFile1,strCMSServerIP,Rettitle,Rettitle1,RetInvtitle,RetInvtitle1,RetSertitle,RetSertitle1,ReportDate);

					String strsalesurl="https://"+strCMSServerIP+"/bookbattery/userdata/billing/salesorders/"+currentYear+"/"+months+"/"+"battery"+"/"+"orderedsummary"+"/"+city+"/"+excelName1;
					LogLevel.DEBUG(5, new Throwable(), "strsalesurl : " + strsalesurl);
					/* Ends to generate retailers orders summary */
					

				String strSqlQry = "insert into retailer_invoice(ret_inv_id,invoice_number,retailer_name,from_date,to_date,ret_pdf_url,salesurl,seq_number,invoice_type,creation_date)values(NULL,'"+verificationcode+"','"+retailer+"','"+strFromDate+"','"+strToDate+"','"+strPdfURL+"','"+strsalesurl+"','"+invoice_code+"','battery',now())";
				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
				reslt = qm.executeUpdate(strSqlQry);
				
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
			  }
			  catch(Exception e)
				{
					//LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsgsst",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/mis/generateretailerinvoice.jsp");
				}
		}
		else if(strWhatToDo.equalsIgnoreCase("InverterGenerateinvoicereport"))
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

			//String city  = (req.getParameter("city")!=null)?req.getParameter("city").trim():""; 
			//LogLevel.DEBUG(5,new Throwable(),"city :"+city );	

			String retailers  = (req.getParameter("retailer")!=null)?req.getParameter("retailer").trim():"";
			LogLevel.DEBUG(5,new Throwable(),"retailers :"+retailers );	

			String months  = (req.getParameter("months")!=null)?req.getParameter("months").trim():"";
			LogLevel.DEBUG(5,new Throwable(),"months :"+months );	

			String txtFromDate  = (req.getParameter("txtFromDate")!=null)?req.getParameter("txtFromDate").trim():"";
			String txtToDate  = (req.getParameter("txtToDate")!=null)?req.getParameter("txtToDate").trim():"";
			String key = "inverter";
			String retailerid = "";
			String erp_pre_tax_condition = "";

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
					strConditions1=" date(updated_date) between '"+strFromDate+"' and '"+strToDate+"'";
				else
					strConditions1=strConditions1+" and date(updated_date) between '"+strFromDate+"' and '"+strToDate+"'";

				if(retailers.equals("ALL"))
				{
				strQuery = "select distinct(retailer_name),city from inverter_order_details where "+strConditions+" and order_status='Customer Contacted' and order_reasons='installed' ";
				LogLevel.DEBUG(5,new Throwable(),"strQuery :"+strQuery );
				}
				else
				{
				strQuery = "select distinct(retailer_name),city from inverter_order_details where "+strConditions+" and order_status='Customer Contacted' and order_reasons='installed' and retailer_name='"+retailers+"'";
				LogLevel.DEBUG(5,new Throwable(),"strQuery :"+strQuery );
				}
				Vector batteryorddetails=qm.executeQuery(strQuery);
				if(batteryorddetails.isEmpty())
				{ 
					out.println("<p align='center' valign='top' class='insidecontent'><table valign='top' width='500' cellspacing='0' border='0' align='center' bgcolor='#FFFFFF' cellpadding='0'><tr><td><table valign='top' width='500' bgcolor='#FFFFFF' border='0'><tr><td class='insidecontent' align='center'>No Record found based on selection!</td></tr></table></td></tr></table><a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
				 }
				 else
				 {
				/* Starts to generate retailers invoice */
				for ( int j=0; j<batteryorddetails.size(); j++)
				{
				Hashtable ht2=(Hashtable)batteryorddetails.get(j);
				String retailer=String.valueOf(ht2.get("retailer_name"));
				String city=String.valueOf(ht2.get("city"));
				//String inverter_brand=String.valueOf(ht2.get("inverter_brand"));
				//String inverter_model=String.valueOf(ht2.get("inverter_model"));

				Calendar cal = Calendar.getInstance();
				int currentMonth = cal.get(Calendar.MONTH) + 1;
				int currentYear = cal.get(Calendar.YEAR);
				
								
				if(retailer.contains("Amaron"))
				{
					if(retailer.contains("Amaron-Pitstop"))
					{
						erp_pre_tax_condition="CAST(round(price/city_percentage) AS SIGNED) as 14percentvat, erp_pre_tax";
					}
					else
					{
						//erp_pre_tax_condition="round(price) as 14percentvat, round(erp_pre_tax*city_percentage,0) as erp_pre_tax";
						//erp_pre_tax_condition="round(price) as 14percentvat, round(erp_pre_tax,0) as erp_pre_tax";
						erp_pre_tax_condition="CAST(round(price/city_percentage) AS SIGNED) as 14percentvat, erp_pre_tax";
					}
				}
				else
				{
					erp_pre_tax_condition="CAST(round(price/city_percentage) AS SIGNED) as 14percentvat,CAST(round(erp_pre_tax/city_percentage) AS SIGNED) as erp_pre_tax";
				}
					
			
				
					//####### Condition for Cash

					String StrQryPrice = "select inverter_model,CAST(round(SUM(quantity)) AS SIGNED) as quantity, "+erp_pre_tax_condition+",total_commission_amount*quantity as comission,inverter_commission_amount*quantity as inverter_commission_amount,CAST(round(total_commission_amount-inverter_commission_amount )*quantity AS SIGNED) as Transaction_Amount,CAST(round(SUM(inverter_commission_amount*quantity)) AS SIGNED) as ourcommision from inverter_order_details where "+strConditions1+" and retailer_name='"+retailer+"' and order_status='Customer Contacted' and payment_mode not in('Credit Card','Debit Card','Online') and order_reasons='installed' group by inverter_model,inverter_commission_amount,order_number";
					LogLevel.DEBUG(5, new Throwable(), "StrQryPrice :" + StrQryPrice);

					Vector RetInoiceVector=qm.executeQuery(StrQryPrice);
					LogLevel.DEBUG(5,new Throwable(),"RetInoiceVector:"+RetInoiceVector );

					
					//####### Condition for Debit Card

					String StrQryPrice_Debit_Card = "select inverter_model,CAST(round(SUM(quantity)) AS SIGNED) as quantity,"+erp_pre_tax_condition+",total_commission_amount*quantity as comission,inverter_commission_amount*quantity as inverter_commission_amount,CAST(round(total_commission_amount-inverter_commission_amount )*quantity AS SIGNED) as Transaction_Amount,CAST(round(SUM(inverter_commission_amount*quantity)) AS SIGNED) as ourcommision from inverter_order_details where "+strConditions1+" and retailer_name='"+retailer+"' and order_status='Customer Contacted' and payment_mode='Debit Card' and order_reasons='installed' group by inverter_model,inverter_commission_amount ,order_number";
					
					
					
					LogLevel.DEBUG(5, new Throwable(), "StrQryPrice_Debit_Card :" + StrQryPrice_Debit_Card);

					Vector RetInoiceVector_Debit_Card=qm.executeQuery(StrQryPrice_Debit_Card);
					LogLevel.DEBUG(5,new Throwable(),"RetInoiceVector_Debit_Card:"+RetInoiceVector_Debit_Card );

					//####### Condition for Credit Card

					String StrQryPrice_Credit_Card = "select inverter_model,CAST(round(SUM(quantity)) AS SIGNED) as quantity,"+erp_pre_tax_condition+" ,total_commission_amount*quantity as comission,inverter_commission_amount*quantity as inverter_commission_amount,CAST(round(total_commission_amount-inverter_commission_amount )*quantity AS SIGNED) as Transaction_Amount , CAST(round(SUM(inverter_commission_amount*quantity)) AS SIGNED) as ourcommision from inverter_order_details where "+strConditions1+" and retailer_name='"+retailer+"' and order_status='Customer Contacted' and payment_mode='Credit Card' and order_reasons='installed' group by inverter_model,inverter_commission_amount ,order_number";
					LogLevel.DEBUG(5, new Throwable(), "StrQryPrice_Credit_Card :" + StrQryPrice_Credit_Card);

					Vector RetInoiceVector_Credit_Card=qm.executeQuery(StrQryPrice_Credit_Card);
					LogLevel.DEBUG(5,new Throwable(),"RetInoiceVector_Credit_Card:"+RetInoiceVector_Credit_Card );

					//####### Condition for Online
					
					String StrQryPrice_Online = "select inverter_model,CAST(round(SUM(quantity)) AS SIGNED) as quantity,"+erp_pre_tax_condition+",total_commission_amount*quantity as comission,inverter_commission_amount*quantity as inverter_commission_amount,CAST(round(total_commission_amount-inverter_commission_amount )*quantity AS SIGNED) as Transaction_Amount , CAST(round(SUM(inverter_commission_amount*quantity)) AS SIGNED) as ourcommision from inverter_order_details where "+strConditions1+" and retailer_name='"+retailer+"' and order_status='Customer Contacted' and payment_mode='Online' and order_reasons='installed' group by inverter_model,inverter_commission_amount ,order_number";
					LogLevel.DEBUG(5, new Throwable(), "StrQryPrice_Online :" + StrQryPrice_Online);

					Vector RetInoiceVector_Online=qm.executeQuery(StrQryPrice_Online);
					LogLevel.DEBUG(5,new Throwable(),"RetInoiceVector_Online:"+RetInoiceVector_Online );


					String Strqry1 = "select CAST(round(SUM(inverter_commission_amount*quantity )) AS SIGNED) as grandtotal from inverter_order_details where "+strConditions1+" and retailer_name='"+retailer+"' and order_status='Customer Contacted' and order_reasons='installed'";
					LogLevel.DEBUG(5, new Throwable(), "Strqry1 :" + Strqry1);
					
					//########## Total online payment recived 
				
						ArrayList htav=new ArrayList();
						htav=qm.getField(Strqry1);

						String batids="";
						for(int i=0;i<htav.size();i++)
						{
						if(batids.equals(""))
						batids=htav.get(i).toString();
						else
						batids=batids+"+"+htav.get(i).toString();
						}
						LogLevel.DEBUG(5,new Throwable(),"batids:"+batids);

						String SqlQrysum = "select CAST(round("+batids+") AS SIGNED) as grandtotal";
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
					
				

						String SqlQrysum_Online_Payment = "select CAST(SUM(price)*quantity AS SIGNED)AS OnlineAmountPayed from inverter_order_details where "+strConditions1+" and retailer_name='"+retailer+"' and order_status='Customer Contacted' and order_reasons='installed' and payment_mode='Online'";
						LogLevel.DEBUG(5,new Throwable(),"SqlQrysum_Online_Payment:"+SqlQrysum_Online_Payment);

						Hashtable htgrandtot_Online_Payment = qm.getRow(SqlQrysum_Online_Payment); 
						String strgrandtot_Online_Payment=String.valueOf(htgrandtot_Online_Payment.get("OnlineAmountPayed"));
						LogLevel.DEBUG(5, new Throwable(), "strgrandtot_Online_Payment :" + strgrandtot_Online_Payment);
						
						if(strgrandtot_Online_Payment.equals("NULL") || strgrandtot_Online_Payment.equals(null)|| strgrandtot_Online_Payment.equals("null") || strgrandtot_Online_Payment.equals("") )
							strgrandtot_Online_Payment="0";
						else
							strgrandtot_Online_Payment=strgrandtot_Online_Payment;

						int strgrandtot_Online_Payment_int = Integer.parseInt(strgrandtot_Online_Payment);
						int strgrandtot_int = Integer.parseInt(strgrandtot);

						int strgrandtot_tmp_int=strgrandtot_int-strgrandtot_Online_Payment_int;
										
						String Total_Reconciled_Amount = Integer.toString(strgrandtot_tmp_int);	
			
						String strSqlQry4="";

						if(retailer.contains("Amaron-Pitstop"))
						{
						strSqlQry4 = "select gstid from amaronexpressdb.gstdetailstable where retailername='"+retailer+"'";
						LogLevel.DEBUG(5,new Throwable(),"strSqlQry4 :"+strSqlQry4 );
						}
						else
						{
						strSqlQry4 = "select gstid from gstdetailstable where retailername='"+retailer+"'";
						LogLevel.DEBUG(5,new Throwable(),"strSqlQry4 :"+strSqlQry4 );
						}

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
					/*	Random generator = new Random();   
						generator.setSeed(System.currentTimeMillis());   
						int num = generator.nextInt(99999) + 99999;   
						if (num < 100000 || num > 999999)
						{   
						num = generator.nextInt(99999) + 99999;   
						if (num < 100000 || num > 999999)
						{   
						}   
						}   */
						
						
				String verificationcode = ""; 
				//Code Added by Bharath for generating sequence number

				//String Sequence_Query = "select seq_number from retailer_invoice where seq_number!='0' and invoice_type='inverter' order by creation_date desc limit 1";
				String Sequence_Query = "select seq_number from retailer_invoice where seq_number!='0' and invoice_type='inverter' order by ret_inv_id desc limit 1";

				LogLevel.DEBUG(5,new Throwable(),"Sequence_Query :"+Sequence_Query );
			
			
				Hashtable htSeq_number = qm.getRow(Sequence_Query); 
								
				int invoice_code = 0;
				int Seq_number_Add = 0;
				if(htSeq_number.isEmpty())
				{ 
					invoice_code = 1;
					String verificationcode1 = Integer.toString(invoice_code);
					verificationcode ="ASIST-SGI-00000"+verificationcode1+"";
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
				
					if(Seq_number_Add <9){
						
					String verificationcode1 = Integer.toString(invoice_code);
					verificationcode ="ASIST-SGI-00000"+verificationcode1+"";
					LogLevel.DEBUG(5, new Throwable(), "verificationcode 9:" + verificationcode);
					
					
					} else if(Seq_number_Add < 100){
					
						String verificationcode1 = Integer.toString(invoice_code);
						verificationcode ="ASIST-SGI-0000"+verificationcode1+"";
						LogLevel.DEBUG(5, new Throwable(), "verificationcode 99:" + verificationcode);
					
						
					} else if(Seq_number_Add < 1000){
						
						String verificationcode1 = Integer.toString(invoice_code);
						verificationcode ="ASIST-SGI-000"+verificationcode1+"";
						LogLevel.DEBUG(5, new Throwable(), "verificationcode 999:" + verificationcode);
						
					} else if(Seq_number_Add < 10000){
						
						String verificationcode1 = Integer.toString(invoice_code);
						verificationcode ="ASIST-SGI-00"+verificationcode1+"";
						LogLevel.DEBUG(5, new Throwable(), "verificationcode 9999:" + verificationcode);
						
					} else if(Seq_number_Add < 100000){
						
						String verificationcode1 = Integer.toString(invoice_code);
						verificationcode ="ASIST-SGI-0"+verificationcode1+"";
						LogLevel.DEBUG(5, new Throwable(), "verificationcode 99999:" + verificationcode);
						
					} else{
						
						String verificationcode1 = Integer.toString(invoice_code);
						verificationcode ="ASIST-SGI-"+verificationcode1+"";
						LogLevel.DEBUG(5, new Throwable(), "verificationcode 999999:" + verificationcode);
						
					}
				
				}
				

			//Code Added by Bharath for generating sequence number

						ArrayList arrExcelHeader1 = new ArrayList();
						arrExcelHeader1.add("Inveter model");
						//arrExcelHeader1.add("price");
						arrExcelHeader1.add("Quantity");
						arrExcelHeader1.add("PreTax-SRP");
						arrExcelHeader1.add("PreTax-ERP");
						arrExcelHeader1.add("Commission");
						arrExcelHeader1.add("Transaction Charge");
						arrExcelHeader1.add("Total Comm"); 

						ArrayList dbHeaders2 = new ArrayList();
						dbHeaders2.add("inverter_model");
						//dbHeaders2.add("price");
						dbHeaders2.add("quantity");
						dbHeaders2.add("14percentvat");
						dbHeaders2.add("erp_pre_tax");
						dbHeaders2.add("comission");
						dbHeaders2.add("Transaction_Amount");
						dbHeaders2.add("ourcommision");

														
						//String excelName = "invoice.pdf";
						String excelName = ""+retailer+"-ASPL-Invoice-"+verificationcode+".pdf";
						LogLevel.DEBUG(5, new Throwable(), "excelName :" + excelName);
						String strPDFFilePath = strPDFFilePath1+ "/" +currentYear+ "/" +months+ "/" +"inverter"+ "/" +"invoice"+ "/" +city;
						LogLevel.DEBUG(5, new Throwable(), "strPDFFilePath :" + strPDFFilePath);
						File excelUploadPathCreate = new File(strPDFFilePath);
						LogLevel.DEBUG(5, new Throwable(), "excelUploadPathCreate :" + excelUploadPathCreate);
						if(excelUploadPathCreate.mkdirs())
						{
						LogLevel.DEBUG(3,new Throwable()," PDF for invoice	Name directory created :  "	+ excelUploadPathCreate);
						}

						//strPDFRelFilePath	= strPDFRelFilePath +"/"+ currentYear + "/" +months;

						String excelFile = excelUploadPathCreate+"/"+excelName;
						LogLevel.DEBUG(5, new Throwable(), "excelFile :" + excelFile);
						File file=new File(excelFile);
						if(!file.exists()) 
						file.createNewFile(); 
						FileWriter fw=new FileWriter(file);	
						GenerateExcelbattery generateExcel	= new GenerateExcelbattery(); 
						//generateExcel.writeToPdf1(retailer,city,verificationcode,txtFromDate,txtToDate,arrExcelHeader1,dbHeaders2,RetInoiceVector,strgrandtot,strservicetax,strtottax,excelFile,strCMSServerIP,key);
						
						//generateExcel.writeToPdf1(retailer,city,verificationcode,txtFromDate,txtToDate,arrExcelHeader1,dbHeaders2,RetInoiceVector,RetInoiceVector_Debit_Card,RetInoiceVector_Credit_Card,RetInoiceVector_Online,strgrandtot,Total_Reconciled_Amount,strgrandtot_Online_Payment,strservicetax,strtottax,excelFile,strCMSServerIP,key,retailerid);
						
						String strPdfURL="https://"+strCMSServerIP+"/bookbattery/userdata/billing/salesorders/"+currentYear+"/"+months+"/"+"inverter"+"/"+"invoice"+"/"+city+"/"+excelName;
						LogLevel.DEBUG(5, new Throwable(), "strPdfURL : " + strPdfURL);
						/* Ends to generate retailers invoice */

						/* Starts to generate retailers orders summary */
						String Strquerys = "select order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobile_number,retailer_emailid,city,inverter_brand,inverter_model,inverter_capacity,price,pincode,area,order_status,quantity,creation_date from inverter_order_details where "+strConditions+" and  order_reasons='installed' and order_status='Customer Contacted' and payment_mode not in('Online') and retailer_name='"+retailer+"' ";
						LogLevel.DEBUG(5, new Throwable(), "Strquerys : " + Strquerys);						
						
						/* Query to get the only Online orders starts here */
						String Strquerys1 = "select order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobile_number,retailer_emailid,city,inverter_brand,inverter_model,inverter_capacity,price,pincode,area,order_status,quantity,creation_date from inverter_order_details where "+strConditions+" and  order_reasons='installed' and order_status='Customer Contacted' and payment_mode='Online' and retailer_name='"+retailer+"' ";
						/* Query to get the only Online orders ends here */
						LogLevel.DEBUG(5, new Throwable(), "Strquerys : " + Strquerys);
						

						Vector dailyVectorwithcat=qm.executeQuery(Strquerys);
						LogLevel.DEBUG(5,new Throwable(),"dailyVectorwithcat:"+dailyVectorwithcat );
						
								
						Vector dailyVectorwithcat1=qm.executeQuery(Strquerys1);
						LogLevel.DEBUG(5,new Throwable(),"dailyVectorwithcat1:"+dailyVectorwithcat1 );
						
						
						
						String Rettitle;
						String Rettitle_excelname;
						String Rettitle1;
						
						
						if(dailyVectorwithcat1.isEmpty())
						{
							 //if online orders are empty

							 if(dailyVectorwithcat.isEmpty())
							{
								Rettitle = ""; 
								Rettitle1 = ""; 
								
							}
							else
							{
								Rettitle = retailer+" - Order Summary"; 
								Rettitle1 = ""; 
							}
							 
						}
						else
						{
							 //if online orders are not empty
							if(dailyVectorwithcat.isEmpty())
							{
								//if offline orders are empty
								//Rettitle = retailer+" - Offline Order Summary"; 
								Rettitle1 = retailer+" - Online Order Summary"; 
								Rettitle ="";
								
							}
							
							else
							{
								Rettitle = retailer+" - Offline Order Summary"; 
								Rettitle1 = retailer+" - Online Order Summary"; 
							}

						}
						
						
						
						String ReportDate = "Report Duration : " +fromDate +  " To "  +toDate;
						Rettitle_excelname = retailer+" - Order Summary";
						String excelName1 = ""+Rettitle_excelname+".pdf";

						ArrayList arrExcelHeaderwithcat = new ArrayList();
						arrExcelHeaderwithcat.add("Order No");
						arrExcelHeaderwithcat.add("Customer Name");
						arrExcelHeaderwithcat.add("Customer Email");
						arrExcelHeaderwithcat.add("Customer Mobile");
						arrExcelHeaderwithcat.add("Retailer Name");
						arrExcelHeaderwithcat.add("Retailer Mobile");
						arrExcelHeaderwithcat.add("Inverter");
						arrExcelHeaderwithcat.add("Model");
						arrExcelHeaderwithcat.add("City");
						arrExcelHeaderwithcat.add("Price");
					//	arrExcelHeaderwithcat.add("Order Status");
						arrExcelHeaderwithcat.add("Quantity");
						arrExcelHeaderwithcat.add("Date");
					//	arrExcelHeaderwithcat.add("Postponed Date");

						ArrayList dbHeaderswithcat = new ArrayList();
						dbHeaderswithcat.add("order_number");
						dbHeaderswithcat.add("consumer_name");
						dbHeaderswithcat.add("consumer_emailid");
						dbHeaderswithcat.add("consumer_mobnumber");
						dbHeaderswithcat.add("retailer_name");
						dbHeaderswithcat.add("retailer_mobile_number");
						dbHeaderswithcat.add("inverter_brand");
						dbHeaderswithcat.add("inverter_model");
						dbHeaderswithcat.add("city");
						dbHeaderswithcat.add("price");
						//dbHeaderswithcat.add("order_status");
						dbHeaderswithcat.add("quantity");
						dbHeaderswithcat.add("creation_date");
						//dbHeaderswithcat.add("postpone_date");
						
						
						
						
						ArrayList arrExcelHeaderwithcat1 = new ArrayList();
						arrExcelHeaderwithcat1.add("Order No");
						arrExcelHeaderwithcat1.add("Customer Name");
						arrExcelHeaderwithcat1.add("Customer Email");
						arrExcelHeaderwithcat1.add("Customer Mobile");
						arrExcelHeaderwithcat1.add("Retailer Name");
						arrExcelHeaderwithcat1.add("Retailer Mobile");
						arrExcelHeaderwithcat1.add("Inverter");
						arrExcelHeaderwithcat1.add("Model");
						arrExcelHeaderwithcat1.add("City");
						arrExcelHeaderwithcat1.add("Price");
					//	arrExcelHeaderwithcat1.add("Order Status");
						arrExcelHeaderwithcat1.add("Quantity");
						arrExcelHeaderwithcat1.add("Date");
					//	arrExcelHeaderwithcat1.add("Postponed Date");

						ArrayList dbHeaderswithcat1 = new ArrayList();
						dbHeaderswithcat1.add("order_number");
						dbHeaderswithcat1.add("consumer_name");
						dbHeaderswithcat1.add("consumer_emailid");
						dbHeaderswithcat1.add("consumer_mobnumber");
						dbHeaderswithcat1.add("retailer_name");
						dbHeaderswithcat1.add("retailer_mobile_number");
						dbHeaderswithcat1.add("inverter_brand");
						dbHeaderswithcat1.add("inverter_model");
						dbHeaderswithcat1.add("city");
						dbHeaderswithcat1.add("price");
						//dbHeaderswithcat1.add("order_status");
						dbHeaderswithcat1.add("quantity");
						dbHeaderswithcat1.add("creation_date");
						//dbHeaderswithcat1.add("postpone_date");
						
						

						
						String strPDFFilePath12 = strPDFFilePath1+ "/" +currentYear+ "/" +months+ "/" +"inverter"+ "/" +"orderedsummary"+ "/" +city;
						LogLevel.DEBUG(5, new Throwable(), "strPDFFilePath12 :" + strPDFFilePath12);
						File excelUploadPathCreate1 = new File(strPDFFilePath12);
						LogLevel.DEBUG(5, new Throwable(), "excelUploadPathCreate1 :" + excelUploadPathCreate1);
						if(excelUploadPathCreate1.mkdirs())
						{
						LogLevel.DEBUG(3,new Throwable()," PDF for invoice	Name directory created :  "	+ excelUploadPathCreate1);
						}

						//strPDFRelFilePath	= strPDFRelFilePath +"/"+ currentYear + "/" +months;

						String excelFile1 = excelUploadPathCreate1+"/"+excelName1;
						LogLevel.DEBUG(5, new Throwable(), "excelFile1 :" + excelFile1);
						File file1=new File(excelFile1); 
						if(!file1.exists()) 
						file1.createNewFile(); 
						FileWriter fw1=new FileWriter(file1);
						GenerateExcelbattery generateExcel1	= new GenerateExcelbattery();
						//generateExcel1.writeToPdf(arrExcelHeaderwithcat,dbHeaderswithcat,dailyVectorwithcat,excelFile1,strCMSServerIP,Rettitle,ReportDate);
						
						//generateExcel1.writeToPdf(arrExcelHeaderwithcat,arrExcelHeaderwithcat1,dbHeaderswithcat,dbHeaderswithcat1,dailyVectorwithcat,dailyVectorwithcat1,excelFile1,strCMSServerIP,Rettitle,Rettitle1,ReportDate);
						
						//generateExcel1.writeToPdf(arrExcelHeaderwithcat,dbHeaderswithcat,arrExcelHeaderwithcat1,dbHeaderswithcat1,dailyVectorwithcat,dailyVectorwithcat1,excelFile1,strCMSServerIP,Rettitle,Rettitle1,ReportDate);
						
						

						String strsalesurl="https://"+strCMSServerIP+"/bookbattery/userdata/billing/salesorders/"+currentYear+"/"+months+"/"+"inverter"+"/"+"orderedsummary"+"/"+city+"/"+excelName1;
						LogLevel.DEBUG(5, new Throwable(), "strsalesurl : " + strsalesurl);
						/* Ends to generate retailers orders summary */
					
					String strSqlQry = "insert into retailer_invoice(ret_inv_id,invoice_number,retailer_name,from_date,to_date,ret_pdf_url,salesurl,seq_number,invoice_type,creation_date)values(NULL,'"+verificationcode+"','"+retailer+"','"+strFromDate+"','"+strToDate+"','"+strPdfURL+"','"+strsalesurl+"','"+invoice_code+"','inverter',now())";
					LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
					reslt = qm.executeUpdate(strSqlQry);
					
			
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
			  }
			  catch(Exception e)
				{
					//LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsgsst",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/mis/generateretailerinvoice.jsp");
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
					res.sendRedirect("../jsp/admin/batterystore/percentage/retailerpercent.jsp");
					return;
				}
				else
				{
					if(keyword.equals("editpercent"))
					{
					LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched city");
					session.setAttribute("sescityvector", CityVector);
					session.setAttribute("sesErrorMsgsst","<font color='#CC0000' class='vrb10'>Succesfully Fetched city.</font> ");
					res.sendRedirect("../jsp/admin/batterystore/percentage/editretailerpercent.jsp?keyword="+keyword);
					return;
					}
					else if(keyword.equals("cuspercent"))
					{
					LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched city");
					session.setAttribute("sescityvector", CityVector);
					session.setAttribute("sesErrorMsgsst","<font color='#CC0000' class='vrb10'>Succesfully Fetched city.</font> ");
					res.sendRedirect("../jsp/admin/batterystore/percentage/cuspercent.jsp?keyword="+keyword);
					return;
					}
					else if(keyword.equals("editcuspercent"))
					{
					LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched city");
					session.setAttribute("sescityvector", CityVector);
					session.setAttribute("sesErrorMsgsst","<font color='#CC0000' class='vrb10'>Succesfully Fetched city.</font> ");
					res.sendRedirect("../jsp/admin/batterystore/percentage/editcuspercent.jsp?keyword="+keyword);
					return;
					}
					else
					{
					LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched city");
					session.setAttribute("sescityvector", CityVector);
					session.setAttribute("sesErrorMsgsst","<font color='#CC0000' class='vrb10'>Succesfully Fetched city.</font> ");
					res.sendRedirect("../jsp/admin/batterystore/percentage/retailerpercent.jsp");
					return;
					}
				}
				}
			catch(IOException ioe)
				{
					//LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
					ioe.printStackTrace();
					session.setAttribute("sesErrorMsgsst",	"Generall Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/percentage/retailerpercent.jsp");
				}
			catch(Exception e)
				{
					//LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsgsst",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/percentage/retailerpercent.jsp");
				}
		     }
		else if(strWhatToDo.equalsIgnoreCase("insertretpercent"))
			{
			String city = (req.getParameter("city") != null)? (req.getParameter("city")) : ""; 
			String srptax = (req.getParameter("srptax") != null)? (req.getParameter("srptax")) : ""; 
			//String erptax = (req.getParameter("erptax") != null)? (req.getParameter("erptax")) : ""; 
			
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
			//LogLevel.DEBUG(5,new Throwable(),"strSqlQry :"+strSqlQry );

			result=qm.executeUpdate(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"result :"+result );

				if(result < 0)
				{
					LogLevel.DEBUG(1,new Throwable(),"Failed to insert Percentage details!");
					session.setAttribute("priority","1");
					session.setAttribute("sespercentErrorMsg", "<font color='#ff3333' class='vrb10'>Failed to insert Percentage details! </font> ");
					res.sendRedirect("../jsp/admin/batterystore/percentage/retailerpercent.jsp");
					return;
				}
				else
				{
					LogLevel.DEBUG(1,new Throwable(),"Successfully inserted Percentage details! ");
					session.setAttribute("priority","1");
					session.setAttribute("sespercentErrorMsg", "<font color='#000000' class='vrb10'>Successfully inserted Percentage details! </font> ");
					res.sendRedirect("../jsp/admin/batterystore/percentage/retailerpercent.jsp");
					return;
					
				}
			}
			else
			{
				LogLevel.DEBUG(1,new Throwable(),"Successfully inserted battery price details! ");
				session.setAttribute("priority","1");
				session.setAttribute("sespercentErrorMsg", "<font color='#000000' class='vrb10'>Already added Percentage for selected city.Please update...! </font> ");
				res.sendRedirect("../jsp/admin/batterystore/percentage/retailerpercent.jsp");
				return;
			}
			}
			catch(IOException ioe)
			{
				//LogLevel.ERROR(0,ioe,"problem Caught IOException if(editcomplaint) !! "+ioe);
				ioe.printStackTrace();
				session.setAttribute("sespercentErrorMsg",	"Generall Error...Please Try Again" );
				res.sendRedirect("../jsp/admin/batterystore/percentage/retailerpercent.jsp");
			}
			catch(Exception e)
			{
				//LogLevel.ERROR(0,e,"Problem Caught Exception if(editcomplaint)!! "+e);
				e.printStackTrace();
				session.setAttribute("sespercentErrorMsg",	"General Error...Please Try Again" );
				res.sendRedirect("../jsp/admin/batterystore/percentage/retailerpercent.jsp");
			}
		}
		else if(strWhatToDo.equalsIgnoreCase("getpercentoupdate"))
		  {
			String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
			LogLevel.DEBUG(5,new Throwable(),"city :"+city );

			ServletOutputStream out=res.getOutputStream();
			try
			  {  
				/* @strSqlQry3 : carries the query to Insert the user Work details into professional_detl table.*/
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
						//strRes=strRes+"<tr><td align='right' class='insidecontent' width='42%'>ERP</font>&nbsp;</td></tr>";
						strRes=strRes+"<tr><td align='right' width='42%'></td><td align='left'><input type='button'  onclick=\"funToUpdatpercent('"+perid+"');\" value='Update' class='smallbutton'></td>";
						//strRes=strRes+"<td align='left' ><input type='button' onclick=\"Schhide('"+id+"');\" value='Cancel' class='smallbutton'></td></tr>";
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
					res.sendRedirect("../jsp/admin/batterystore/percentage/editretailerpercent.jsp");
			    }
			   catch(Exception e)
			    {
					LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/percentage/editretailerpercent.jsp");
			    }
		  }
		  else if(strWhatToDo.equalsIgnoreCase("editpercentage"))
		{
			String srptax = (req.getParameter("srptax")!=null)?(req.getParameter("srptax")):"";
			LogLevel.DEBUG(5,new Throwable(),"srptax :"+srptax );

			//String erptax = (req.getParameter("erptax")!=null)?(req.getParameter("erptax")):"";
			//LogLevel.DEBUG(5,new Throwable(),"erptax :"+erptax );

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
						res.sendRedirect("../jsp/admin/batterystore/percentage/editretailerpercent.jsp?keyword="+keyword);
						return;
					}
				   else
				    {
						LogLevel.DEBUG(1,new Throwable(),"Successfully updated percentage details! ");
						session.setAttribute("sesleditperceErrorMsg", "<font color='#000000' class='top1'>Successfully Updated percentage details.!</font>");
						//session.setAttribute("sescontVector", reslt);
						res.sendRedirect("../jsp/admin/batterystore/percentage/editretailerpercent.jsp?keyword="+keyword);
				    }
			     }
			    catch(IOException ioe)
			     {
						LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
						ioe.printStackTrace();
						session.setAttribute("sesleditperceErrorMsg",	"Generall Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/percentage/editretailerpercent.jsp?keyword="+keyword);
			     }
			    catch(Exception e)
			     {
						LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
						e.printStackTrace();
						session.setAttribute("sesleditperceErrorMsg",	"General Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/percentage/editretailerpercent.jsp?keyword="+keyword);
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
			//LogLevel.DEBUG(5,new Throwable(),"strSqlQry :"+strSqlQry );

			result=qm.executeUpdate(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"result :"+result );

				if(result < 0)
				{
					LogLevel.DEBUG(1,new Throwable(),"Failed to insert customer invoice tax details!");
					session.setAttribute("priority","1");
					session.setAttribute("sespercentErrorMsg", "<font color='#ff3333' class='vrb10'>Failed to insert customer invoice tax details! </font> ");
					res.sendRedirect("../jsp/admin/batterystore/percentage/cuspercent.jsp?keyword="+keyword);
					return;
				}
				else
				{
					LogLevel.DEBUG(1,new Throwable(),"Successfully inserted customer invoice tax details! ");
					session.setAttribute("priority","1");
					session.setAttribute("sespercentErrorMsg", "<font color='#000000' class='vrb10'>Successfully inserted customer invoice tax details! </font> ");
					res.sendRedirect("../jsp/admin/batterystore/percentage/cuspercent.jsp?keyword="+keyword);
					return;
					
				}
			}
			else
			{
				LogLevel.DEBUG(1,new Throwable(),"Successfully inserted customer invoice tax details! ");
				session.setAttribute("priority","1");
				session.setAttribute("sespercentErrorMsg", "<font color='#000000' class='vrb10'>Already added customer invoice tax for selected city.Please update...! </font> ");
				res.sendRedirect("../jsp/admin/batterystore/percentage/cuspercent.jsp?keyword="+keyword);
				return;
			}
			}
			catch(IOException ioe)
			{
				//LogLevel.ERROR(0,ioe,"problem Caught IOException if(editcomplaint) !! "+ioe);
				ioe.printStackTrace();
				session.setAttribute("sespercentErrorMsg",	"Generall Error...Please Try Again" );
				res.sendRedirect("../jsp/admin/batterystore/percentage/cuspercent.jsp?keyword="+keyword);
			}
			catch(Exception e)
			{
				//LogLevel.ERROR(0,e,"Problem Caught Exception if(editcomplaint)!! "+e);
				e.printStackTrace();
				session.setAttribute("sespercentErrorMsg",	"General Error...Please Try Again" );
				res.sendRedirect("../jsp/admin/batterystore/percentage/cuspercent.jsp?keyword="+keyword);
			}
		}
		else if(strWhatToDo.equalsIgnoreCase("getcuspercentoupdate"))
		  {
			String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
			LogLevel.DEBUG(5,new Throwable(),"city :"+city );

			ServletOutputStream out=res.getOutputStream();
			try
			  {  
				/* @strSqlQry3 : carries the query to Insert the user Work details into professional_detl table.*/
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
						//strRes=strRes+"<td align='left' ><input type='button' onclick=\"Schhide('"+id+"');\" value='Cancel' class='smallbutton'></td></tr>";
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
					res.sendRedirect("../jsp/admin/batterystore/percentage/editcuspercent.jsp");
			    }
			   catch(Exception e)
			    {
					LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/percentage/editcuspercent.jsp");
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
						res.sendRedirect("../jsp/admin/batterystore/percentage/editcuspercent.jsp?keyword="+keyword);
						return;
					}
				   else
				    {
						LogLevel.DEBUG(1,new Throwable(),"Successfully updated percentage details! ");
						session.setAttribute("sesleditcusErrorMsg", "<font color='#000000' class='top1'>Successfully Updated percentage details.!</font>");
						//session.setAttribute("sescontVector", reslt);
						res.sendRedirect("../jsp/admin/batterystore/percentage/editcuspercent.jsp?keyword="+keyword);
				    }
			     }
			    catch(IOException ioe)
			     {
						LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
						ioe.printStackTrace();
						session.setAttribute("sesleditcusErrorMsg",	"Generall Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/percentage/editcuspercent.jsp?keyword="+keyword);
			     }
			    catch(Exception e)
			     {
						LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
						e.printStackTrace();
						session.setAttribute("sesleditcusErrorMsg",	"General Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/percentage/editcuspercent.jsp?keyword="+keyword);
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

			/* @strSqlQry3 : carries the query to Insert the user Work details into professional_detl table.*/
				if(invoicetype.equals("battery"))
				  {
					String strSqlQry4 = "select retailer_name,invoice_number,date(from_date),date(to_date),ret_pdf_url,salesurl from retailer_invoice where "+strConditions+" and invoice_type='battery' order by retailer_name asc";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry4 :"+strSqlQry4 );

					retinvoice=qm.executeQuery(strSqlQry4);
					LogLevel.DEBUG(5,new Throwable(),"retinvoice:"+retinvoice);
				  }
				  else
				  {
					String strSqlQry4 = "select retailer_name,invoice_number,date(from_date),date(to_date),ret_pdf_url,salesurl from retailer_invoice where "+strConditions+" and invoice_type='inverter' order by retailer_name asc";
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
				//strRes=strRes+"<td align='center'  valign='top'><input type='button' onClick=\"javascript:deleteappichatdetails();\" value='DELETE' class='button4'></td></tr></table>";
						
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
					//strRes=strRes+"<tr><td width='6%' class='table1' align='left' >"+Jcnt+"<input type='checkbox' name='chkSi' value='"+strmapid+"'/></td>";
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
					res.sendRedirect("../jsp/admin/batterystore/mis/viewretailerinvoice.jsp");
			    }
			   catch(Exception e)
			    {
					LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/mis/viewretailerinvoice.jsp");
			    }
		  }
		}
     }


