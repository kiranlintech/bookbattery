/***********************************************************************		
	NGIT Confidential. 
	@File Name   : BatteryHome.java 
	@Description : This Servlet is used to select the Battery Details.
	@Author	     : Gowtham.K
	@Date        : 30th August 2013
******************************************************************/ 
 
package com.ngit.servlets.operator; 
 
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.admin.mis.GenerateExcelbattery;
import com.ngit.javabean.mail.*;
import com.ngit.javabean.sendsms.SendSMS;
import com.ngit.javabean.consumers.products.Order_SMS;
import com.ngit.javabean.consumers.products.CompareTrans;
import com.instamojo.wrapper.api.OnlinePayment;
import org.json.JSONObject;
import java.util.Properties;
import java.util.Vector;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.*;
import java.security.SecureRandom;
import java.net.URLDecoder;  
import java.net.URLEncoder; 

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import net.sf.json.*; 
import net.sf.ezmorph.*; 
 
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import javax.mail.*;
import javax.activation.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.HttpURLConnection;

  
public class MISOperatorInverterDetails extends HttpServlet 
{  
	private ServletContext context; 
	QueryManager qm;
	String baseURL;
	private static final Random RANDOM = new SecureRandom();
		 /** Length of password. @see #generateRandomPassword() */
		 public static final int PASSWORD_LENGTH = 8;
 
	public void init(ServletConfig config)throws ServletException
	{ 
		super.init(config); 
		context = getServletContext(); 

		try
		{
			String strMOPConfig = getInitParameter("paramMOPConfig"); 
  
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
			String strLogFilePath = (propsMOPConfig.getProperty("LogFilePath")!=null)?propsMOPConfig.getProperty("LogFilePath"):"/home/ngit/tomcat/webapps"+baseurldirectory+"logs/";
			if(strLogFilePath.equals(""))
			strLogFilePath = "/home/ngit/tomcat/webapps"+baseurldirectory+"logs/";
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
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
		doGet(req,res);
	}
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
        res.setContentType("text/html;charset=UTF-8");
        HttpSession session=req.getSession(true);
 		session=req.getSession(true);
		String struserName=(String)session.getAttribute("sesBatteryOperatorName"); 
		LogLevel.DEBUG(5,new Throwable(),"struserName :"+struserName );

		Properties propsMOPConfig = (Properties)context.getAttribute("contextPropMOPConfig");
 		String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?(propsMOPConfig.getProperty("baseurldirectory")):"";

		if(propsMOPConfig==null)
		{
			LogLevel.DEBUG(1,new Throwable(),"Properties not loaded. So reloading" );
			try
			{
				String strMOPConfig = "/home/ngit/tomcat/webapps"+baseurldirectory+"properties/bookbatteryconfig.properties"; 
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
		String strWhatToDo        = (req.getParameter("hidWhatToDo")!=null)?req.getParameter("hidWhatToDo"):""; 
		ServletOutputStream out=res.getOutputStream();
		String strIpAddress =(propsMOPConfig.getProperty("TRANSMITTER_IP_ADDR")!=null)?(propsMOPConfig.getProperty("TRANSMITTER_IP_ADDR")):"";
		String strPort=(propsMOPConfig.getProperty("TRANSMITTER_IP_PORT")!=null)?(propsMOPConfig.getProperty("TRANSMITTER_IP_PORT")):"9910";
		String SMSFromAddress=(propsMOPConfig.getProperty("SMSFromAddress")!=null)?(propsMOPConfig.getProperty("SMSFromAddress")):"";
		String FromEmailId=(propsMOPConfig.getProperty("SupportEmailId")!=null)?(propsMOPConfig.getProperty("SupportEmailId")):"";
		String strsuppemaild=(propsMOPConfig.getProperty("suppemaild")!=null)?(propsMOPConfig.getProperty("suppemaild")):"";
		String domainname = (propsMOPConfig.getProperty("domainname")!=null)?(propsMOPConfig.getProperty("domainname")).trim():"BookBattery.com";
		String baseUrl =  propsMOPConfig.getProperty("publicUrl");
		String strsuppmobnumber =  propsMOPConfig.getProperty("suppmobnumber");
		String strCMSSERVERIP	= (propsMOPConfig.getProperty("CMS_SERVER_IP")!=null)?(propsMOPConfig.getProperty("CMS_SERVER_IP")):"";
	  /* ****************************************************************************************\
		* This action is used to get battery brands.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("getlocations"))
		{
			try
			{
				String strRes=getlocations(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);
				out.close();
			}
			catch (Exception e)
			{										
			LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}

		 /* ****************************************************************************************\
		* This action is used to get battery brands.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("getlocationsmis"))
		{
			try
			{
				String strRes=getlocationsmis(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);
				out.close();
			}
			catch (Exception e)
			{										
			LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}
		  /* ****************************************************************************************\
	* This action is used to get retailers.
	\* *****************************************************************************************/		

	if(strWhatToDo.equalsIgnoreCase("getconfirmedorders"))
			{ 
			try
			{
				String strRes=getconfirmedorders(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);
				out.close();
			}
			catch (Exception e)
			{										
			LogLevel.ERROR(1, e, "Error :" + e);
			}	
	      }
		    /* ****************************************************************************************\
	* This action is used to get retailers.
	\* *****************************************************************************************/		

	if(strWhatToDo.equalsIgnoreCase("getconfirmedordersmis"))
			{ 
			try
			{
				String strRes=getconfirmedordersmis(req,res,session,struserName);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);
				out.close();
			}
			catch (Exception e)
			{										
			LogLevel.ERROR(1, e, "Error :" + e);
			}	
	      }

		   /* ****************************************************************************************\
		* This action is used to get battery brands.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("updateorderstatus"))
		{
			try
			{
				String strRes=updateorderstatus(req,res,session,domainname,strIpAddress,strPort,FromEmailId,strsuppemaild,SMSFromAddress,strsuppmobnumber);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);
				out.close();
			}
			catch (Exception e)
			{										
			LogLevel.ERROR(1, e, "Error :" + e);
			}	
	     }

		    /* ****************************************************************************************\
		* This action is used to get battery brands.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("getinverterpricedetls"))
		{
			try
			{
				String strRes=getinverterpricedetls(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);
				out.close();
			}
			catch (Exception e)
			{										
			LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}
	}
	 /* **************************************************************************************************************************************\
	* This method is to fetch battery brands details.
	* @strSqlQry : carries the query to get battery brands from battery_oreder_details table.
	\* **************************************************************************************************************************************/
	public String getlocations(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
	String strDateOpt  = (req.getParameter("dates")!=null)?req.getParameter("dates"):"";
	LogLevel.DEBUG(5, new Throwable(), "strDateOpt :"+ strDateOpt);

	String keyword  = (req.getParameter("keyword")!=null)?req.getParameter("keyword"):"";
	LogLevel.DEBUG(5, new Throwable(), "keyword :"+ keyword);

	String txtFromDate  = (req.getParameter("txtFromDate")!=null)?req.getParameter("txtFromDate"):"";
	String txtToDate  = (req.getParameter("txtToDate")!=null)?req.getParameter("txtToDate"):"";
	 try
	   {	
		String strSqlQry ="select distinct(city) from inverter_order_details where city!='' order by city asc";
		LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
		
		Vector LocationVector=qm.executeQuery(strSqlQry);
		LogLevel.DEBUG(5,new Throwable(),"LocationVector:"+LocationVector);
		if(LocationVector.isEmpty())
		{ 
			LogLevel.DEBUG(1,new Throwable(),"Failed to fetch locations ");
			session.setAttribute("priority","1");
			session.setAttribute("sesadminloginErrorMsg", "<font color='#00dd00' class='vrb10'>Failed to fetch locations! </font> ");
			res.sendRedirect("../jsp/operator/mis/invertermisselconfirm.jsp?dates="+strDateOpt+"&txtFromDate="+txtFromDate+" &txtToDate="+txtToDate);
			return strRes;
		}
		else
		{
			if(keyword.equals("confirm") || keyword.equals("ordered") || keyword.equals("installed") || keyword.equals("cancelled-franchisee-offbushrs") || keyword.equals("cancelled-franchisee-denied") || keyword.equals("cancelled-franchisee-notresponded") || keyword.equals("cancelled-franchisee-modeloutofstock") || keyword.equals("cancelled-customer") || keyword.equals("cancelled-regenerated"))
			{
			LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched Categories");
			session.setAttribute("seslocatiovector", LocationVector);
			session.setAttribute("sesErrorMsgss","<font color='#CC0000' class='vrb10'>Succesfully Fetched Categories.</font> ");
			res.sendRedirect("../jsp/operator/mis/invertermisselconfirm.jsp?keyword="+keyword+"&dates="+strDateOpt+"&txtFromDate="+txtFromDate+" &txtToDate="+txtToDate);
			return strRes;
			}
			else if(keyword.equals("postponed"))
			{
				session.setAttribute("seslocatiovector", LocationVector);
				session.setAttribute("sesErrorMsgss","<font color='#CC0000' class='vrb10'>Succesfully Fetched Locations.</font> ");
				res.sendRedirect("../jsp/operator/mis/inverterselectlocation.jsp?keyword="+keyword+"&txtFromDate="+txtFromDate+" &txtToDate="+txtToDate);	
				
			}
			else
			{
				LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched Categories");
				session.setAttribute("seslocatiovector", LocationVector);
				session.setAttribute("sesErrorMsgss","<font color='#CC0000' class='vrb10'>Succesfully Fetched Categories.</font> ");
				res.sendRedirect("../jsp/operator/mis/invertermisselconfirm.jsp?dates="+strDateOpt+"&txtFromDate="+txtFromDate+" &txtToDate="+txtToDate);
				return strRes;
			}
		}
		}
		catch (Exception e)
		{
			LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
			e.printStackTrace();
		}
 	return strRes;
	}

	/* **************************************************************************************************************************************\
	* This method is to fetch battery brands details.
	* @strSqlQry : carries the query to get battery brands from battery_oreder_details table.
	\* **************************************************************************************************************************************/
	public String getlocationsmis(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
	String strDateOpt  = (req.getParameter("dates")!=null)?req.getParameter("dates"):"";
	LogLevel.DEBUG(5, new Throwable(), "strDateOpt :"+ strDateOpt);

	String keyword  = (req.getParameter("keyword")!=null)?req.getParameter("keyword"):"";
	LogLevel.DEBUG(5, new Throwable(), "keyword :"+ keyword);

	String txtFromDate  = (req.getParameter("txtFromDate")!=null)?req.getParameter("txtFromDate"):"";
	String txtToDate  = (req.getParameter("txtToDate")!=null)?req.getParameter("txtToDate"):"";
	 try
	   {	
		String strSqlQry ="select distinct(city) from inverter_order_details where city!='' order by city asc";
		LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
		
		Vector LocationVector=qm.executeQuery(strSqlQry);
		LogLevel.DEBUG(5,new Throwable(),"LocationVector:"+LocationVector);
		if(LocationVector.isEmpty())
		{ 
			LogLevel.DEBUG(1,new Throwable(),"Failed to fetch locations ");
			session.setAttribute("priority","1");
			session.setAttribute("sesadminloginErrorMsg", "<font color='#00dd00' class='vrb10'>Failed to fetch locations! </font> ");
			res.sendRedirect("../jsp/operator/ordermis/invertermisselconfirm.jsp?dates="+strDateOpt+"&txtFromDate="+txtFromDate+" &txtToDate="+txtToDate);
			return strRes;
		}
		else
		{
			
			LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched Categories");
			session.setAttribute("seslocatiovector", LocationVector);
			session.setAttribute("sesErrorMsgss","<font color='#CC0000' class='vrb10'>Succesfully Fetched Categories.</font> ");
			res.sendRedirect("../jsp/operator/ordermis/invertermisselconfirm.jsp?keyword="+keyword+"&dates="+strDateOpt+"&txtFromDate="+txtFromDate+" &txtToDate="+txtToDate);
			return strRes;
			
		}
		}
		catch (Exception e)
		{
			LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
			e.printStackTrace();
		}
 	return strRes;
	}
	 /* **************************************************************************************************************************************\
* This method is to get with out ordered battery details details.
* @strSqlQry: carries the query to ordered battery details from battery_order_details table.
\* **************************************************************************************************************************************/
	public String getconfirmedorders(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";

	Hashtable htOptions=new Hashtable();

	String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"0";
	LogLevel.DEBUG(5,new Throwable(),"city :"+city );

	String keyword  = (req.getParameter("keyword")!=null)?req.getParameter("keyword").trim() :"";
	LogLevel.DEBUG(5, new Throwable(), "keyword :"+ keyword); 

	String strDateOpt  = (req.getParameter("dates")!=null)?req.getParameter("dates").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "strDateOpt :"+ strDateOpt);

	String txtFromDate  = (req.getParameter("txtFromDate")!=null)?req.getParameter("txtFromDate").trim():"";
	String txtToDate  = (req.getParameter("txtToDate")!=null)?req.getParameter("txtToDate").trim():"";
	String orderstatus="";
	
	Calendar calendar = new GregorianCalendar();
	int curMonth=calendar.get(Calendar.MONTH)+1;
	int curYear=calendar.get(Calendar.YEAR);
	int curDate=calendar.get(Calendar.DAY_OF_MONTH);
	if(strDateOpt.equals("current"))
	{
		txtFromDate="01-"+curMonth+"-"+curYear;
		txtToDate=curDate+"-"+curMonth+"-"+curYear;
	}
	else if(strDateOpt.equals("candpmonth"))
	{
		int prvsMonth=curMonth-1;
		int prvsYear=curYear;
		if(prvsMonth==0)
		{
			prvsMonth=12;
			prvsYear=curYear-1;
		}
		txtFromDate="01-"+prvsMonth+"-"+prvsYear;
		//txtToDate=curDate+"-"+curMonth+"-"+curYear;
		txtToDate=curDate+"-"+curMonth+"-"+curYear;
	}
	else if(strDateOpt.equals("sixmonths"))
	{
		int prvsMonth=curMonth-6;
		int prvsYear=curYear;
		if(prvsMonth<=0)
		{
			prvsMonth=prvsMonth+12;
			prvsYear=curYear-1;
		}
		txtFromDate="01-"+prvsMonth+"-"+prvsYear;
		txtToDate=curDate+"-"+curMonth+"-"+curYear;
	}

	if(keyword.equals("confirm"))
		{
		orderstatus = "Confirmed";
		}
	else if(keyword.equals("ordered"))
		{
		orderstatus = "Ordered";
		}
	else if(keyword.equals("installed"))
		{
		orderstatus = "Installed";
		}
	else if(keyword.equals("cancelled-franchisee-offbushrs"))
		{
		orderstatus = "Cancelled-Franchisee-OffBusHrs";
		}
	else if(keyword.equals("cancelled-franchisee-denied"))
		{
		orderstatus = "Cancelled-Franchisee-Denied";
		}
	else if(keyword.equals("cancelled-franchisee-notresponded"))
		{
		orderstatus = "Cancelled-Franchisee-NotResponded";
		}
	else if(keyword.equals("cancelled-franchisee-modeloutofstock"))
		{
		orderstatus = "Cancelled-Franchisee-ModelOutOfStock";
		}
	else if(keyword.equals("cancelled-customer"))
		{
		orderstatus = "Cancelled-Customer";
		}
else if(keyword.equals("cancelled-regenerated"))
		{
		orderstatus = "Cancelled-Regenerated";
		}
		else if(keyword.equals("postponed"))
		{
		orderstatus = "Postponed";
		}
	LogLevel.DEBUG(5, new Throwable(), "txtFromDate :"+ txtFromDate);
	htOptions.put("txtFromDate",txtFromDate);

	LogLevel.DEBUG(5, new Throwable(), "txtToDate :"+ txtToDate);
	htOptions.put("txtToDate",txtToDate);

	LogLevel.DEBUG(5, new Throwable(), "city :"+ city);
	htOptions.put("city",city);

	LogLevel.DEBUG(5, new Throwable(), "orderstatus :"+ orderstatus);
	htOptions.put("orderstatus",orderstatus);
	 
	try
	{	
	
	String strConditions="";
	String strConditions1="";
	String strSqlQry="";
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
	
	if(keyword.equals("confirm"))
		{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' and order_status='confirmed' ";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= " and order_status='confirmed'";
			}
		}
	else if(keyword.equals("ordered"))
		{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' and order_status='ordered' ";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= " and order_status='ordered'";
			}
		}
		else if(keyword.equals("installed"))
		{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' and order_status='installed' ";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= " and order_status='installed'";
			}
		}
		else if(keyword.equals("postponed"))
		{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' and order_status='postponed' ";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= " and order_status='postponed'";
			}
		}
		else if(keyword.equals("cancelled-franchisee-offbushrs"))
		{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' and order_status='cancelled-franchisee-offbushrs' ";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= " and order_status='cancelled-franchisee-offbushrs'";
			}
		}
		else if(keyword.equals("cancelled-franchisee-denied"))
			{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' and order_status='cancelled-franchisee-denied' ";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= " and order_status='cancelled-franchisee-denied'";
			}
		}
		else if(keyword.equals("cancelled-franchisee-notresponded"))
			{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' and order_status='cancelled-franchisee-notresponded' ";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= " and order_status='cancelled-franchisee-notresponded'";
			}
		}
		else if(keyword.equals("cancelled-franchisee-modeloutofstock"))
			{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' and order_status='cancelled-franchisee-modeloutofstock' ";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= " and order_status='cancelled-franchisee-modeloutofstock'";
			}
		}
		else if(keyword.equals("cancelled-customer"))
			{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' and order_status='cancelled-customer' ";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= " and order_status='cancelled-customer'";
			}
		}
		else if(keyword.equals("cancelled-regenerated"))
			{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' and order_status='cancelled-regenerated' ";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= " and order_status='cancelled-regenerated'";
			}
		}
	
	//String strSqlQry ="select distinct emp_id,time_slot,description,activity_category,emp_name,date(creation_date),date_format(creation_date, '%W %M %D %Y'), count(time_slot) as count from mis where "+strConditions+" and emp_name='"+strempname+"' and emp_id='"+strempid+"' group by date(creation_date),time_slot,activity_category order by emp_name asc";
		if(keyword.equals("postponed"))
		{
			strSqlQry="select order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobile_number,retailer_emailid,state,city,inverter_brand,service_engineer_name,service_engineer_mobile,inverter_capacity,price,pincode,area,order_status,creation_date,postpone_date,postpone_message from inverter_order_details where date(postpone_date) between '"+strFromDate+"' and '"+strToDate+"' "+strConditions1+"  order by postpone_date";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

		}
		else if(keyword.equals("installed"))
		{
			strSqlQry="select order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobile_number,retailer_emailid,state,city,inverter_brand,service_engineer_name,service_engineer_mobile,inverter_capacity,price,pincode,area,order_status,creation_date,CAST(postpone_date AS CHAR) as postpone_date from inverter_order_details where date(updated_date) between '"+strFromDate+"' and '"+strToDate+"' "+strConditions1+"  order by updated_date";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

		}
		else
		{
			strSqlQry="select order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobile_number,retailer_emailid,state,city,inverter_brand,service_engineer_name,service_engineer_mobile,inverter_capacity,price,pincode,area,order_status,creation_date,CAST(postpone_date AS CHAR) as postpone_date from inverter_order_details where "+strConditions+" "+strConditions1+" order by creation_date";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
		}


	Vector BatOrdstatusVector=qm.executeQuery(strSqlQry);
	LogLevel.DEBUG(5,new Throwable(),"BatOrdstatusVector:"+ BatOrdstatusVector );

	if(session.getAttribute("sesBatOrderstatusVectorVector")!=null)
	session.removeAttribute("sesBatOrderstatusVectorVector");
	session.setAttribute("sesBatOrderstatusVectorVector",BatOrdstatusVector);
	
	if(session.getAttribute("sesOptions")!=null)
	session.removeAttribute("sesOptions");
	session.setAttribute("sesOptions",htOptions);
	res.sendRedirect("../jsp/operator/mis/inverterconfirmedordereddetails.jsp?txtFromDate="+txtFromDate+"&txtToDate="+txtToDate+"&city="+city+"&keyword="+keyword+"&dates="+strDateOpt);
	return strRes;
	}
	catch (Exception e)
	{
	LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
	e.printStackTrace();
	}
 	return strRes;
  }

  /* **************************************************************************************************************************************\
* This method is to get with out ordered battery details details.
* @strSqlQry: carries the query to ordered battery details from battery_order_details table.
\* **************************************************************************************************************************************/
	public String getconfirmedordersmis(HttpServletRequest req , HttpServletResponse res,HttpSession session,String struserName)
	{
	String strRes="";

	Hashtable htOptions=new Hashtable();

	String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"0";
	LogLevel.DEBUG(5,new Throwable(),"city :"+city );

	String keyword  = (req.getParameter("keyword")!=null)?req.getParameter("keyword").trim() :"";
	LogLevel.DEBUG(5, new Throwable(), "keyword :"+ keyword); 

	String strDateOpt  = (req.getParameter("dates")!=null)?req.getParameter("dates").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "strDateOpt :"+ strDateOpt);

	String txtFromDate  = (req.getParameter("txtFromDate")!=null)?req.getParameter("txtFromDate").trim():"";
	String txtToDate  = (req.getParameter("txtToDate")!=null)?req.getParameter("txtToDate").trim():"";

	String statusfilter  = (req.getParameter("statusfilter")!=null)?req.getParameter("statusfilter").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "statusfilter :"+ statusfilter);

	String substatusfilter  = (req.getParameter("substatusfilter")!=null)?req.getParameter("substatusfilter").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "substatusfilter :"+ substatusfilter);

	String retselectname  = (req.getParameter("retselectname")!=null)?req.getParameter("retselectname").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "retselectname :"+ retselectname);
	
	String operatorfilter  = (req.getParameter("operatorfilter")!=null)?req.getParameter("operatorfilter").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "operatorfilter :"+ operatorfilter);

	String fromdatefilter  = (req.getParameter("fromdatefilter")!=null)?req.getParameter("fromdatefilter").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "fromdatefilter :"+ fromdatefilter);
	
	String fromtimefilter  = (req.getParameter("fromtimefilter")!=null)?req.getParameter("fromtimefilter").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "fromtimefilter :"+ fromtimefilter);
	
	String todatefilter  = (req.getParameter("todatefilter")!=null)?req.getParameter("todatefilter").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "todatefilter :"+ todatefilter);
	
	String totimefilter  = (req.getParameter("totimefilter")!=null)?req.getParameter("totimefilter").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "totimefilter :"+ totimefilter);
	
	String test_orders  = (req.getParameter("test_orders")!=null)?req.getParameter("test_orders").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "test_orders :"+ test_orders);
	
	String orders_of  = (req.getParameter("orders_of")!=null)?req.getParameter("orders_of").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "orders_of :"+ orders_of);


	String orderstatus="";
	
	Calendar calendar = new GregorianCalendar();
	int curMonth=calendar.get(Calendar.MONTH)+1;
	int curYear=calendar.get(Calendar.YEAR);
	int curDate=calendar.get(Calendar.DAY_OF_MONTH);
	if(strDateOpt.equals("current"))
	{
		txtFromDate="01-"+curMonth+"-"+curYear;
		txtToDate=curDate+"-"+curMonth+"-"+curYear;
	}
	else if(strDateOpt.equals("candpmonth"))
	{
		int prvsMonth=curMonth-1;
		int prvsYear=curYear;
		if(prvsMonth==0)
		{
			prvsMonth=12;
			prvsYear=curYear-1;
		}
		txtFromDate="01-"+prvsMonth+"-"+prvsYear;
		//txtToDate=curDate+"-"+curMonth+"-"+curYear;
		txtToDate=curDate+"-"+curMonth+"-"+curYear;
	}
	else if(strDateOpt.equals("sixmonths"))
	{
		int prvsMonth=curMonth-6;
		int prvsYear=curYear;
		if(prvsMonth<=0)
		{
			prvsMonth=prvsMonth+12;
			prvsYear=curYear-1;
		}
		txtFromDate="01-"+prvsMonth+"-"+prvsYear;
		txtToDate=curDate+"-"+curMonth+"-"+curYear;
	}
	else if(strDateOpt.equals("selected"))
	{
		if(!fromdatefilter.equals(""))
		{
			txtFromDate=fromdatefilter;
		}
		if(!todatefilter.equals(""))
		{
			txtToDate=todatefilter;
		}
	}
	
	
	
	String test_orders_Condition="";
	if(test_orders.equals("yes") || test_orders.equals("Yes"))
	{
		test_orders_Condition ="";
	}
	else 
	{
		test_orders_Condition = "and area NOT LIKE 'test%' and pincode NOT LIKE '1000%'";
	}
	
	//filter for consumer or operator orders 
	if(orders_of.equals("0") || orders_of.equals("All") || orders_of.equals("null") || orders_of.equals(""))
	{
		test_orders_Condition =test_orders_Condition;
	}
	else 
	{
		test_orders_Condition = test_orders_Condition+" and operator='"+orders_of+"'";
	}
	
	if(keyword.equals("confirm"))
		{
		orderstatus = "Confirmed";
		}
	else if(keyword.equals("All") || keyword.equals(""))
		{
		orderstatus = "All";
		}
	else if(keyword.equals("ordered"))
		{
		orderstatus = "Ordered";
		}
	else if(keyword.equals("installed"))
		{
		orderstatus = "Installed";
		}
	else if(keyword.equals("cancelled-franchisee-offbushrs"))
		{
		orderstatus = "Cancelled-Franchisee-OffBusHrs";
		}
	else if(keyword.equals("cancelled-franchisee-denied"))
		{
		orderstatus = "Cancelled-Franchisee-Denied";
		}
	else if(keyword.equals("cancelled-franchisee-notresponded"))
		{
		orderstatus = "Cancelled-Franchisee-NotResponded";
		}
	else if(keyword.equals("cancelled-franchisee-modeloutofstock"))
		{
		orderstatus = "Cancelled-Franchisee-ModelOutOfStock";
		}
	else if(keyword.equals("cancelled-customer"))
		{
		orderstatus = "Cancelled-Customer";
		}
	else if(keyword.equals("cancelled-regenerated"))
		{
		orderstatus = "Cancelled-Regenerated";
		}
		else if(keyword.equals("postponed") || keyword.equals("Postponed"))
		{
		orderstatus = "Postponed";
		}
	LogLevel.DEBUG(5, new Throwable(), "txtFromDate :"+ txtFromDate);
	htOptions.put("txtFromDate",txtFromDate);

	LogLevel.DEBUG(5, new Throwable(), "txtToDate :"+ txtToDate);
	htOptions.put("txtToDate",txtToDate);

	LogLevel.DEBUG(5, new Throwable(), "city :"+ city);
	htOptions.put("city",city);

	LogLevel.DEBUG(5, new Throwable(), "orderstatus :"+ orderstatus);
	htOptions.put("orderstatus",orderstatus);
        
	try
	{	
	
	String strConditions="";
	String strConditions1="";
	String strConditions2="";
	String strConditionssubstatus="";
	String strConditionsretailerselect="";
	String strOperatorConditions="";
	String strOperatorFilterConditions="";


	String strSqlQry="";
	SimpleDateFormat sdfDate=new SimpleDateFormat("dd-MM-yyyy");
	Date fromDate=sdfDate.parse(txtFromDate); 
	Date toDate=sdfDate.parse(txtToDate); 

	SimpleDateFormat sdfString=new SimpleDateFormat("yyyy-MM-dd");
	String strFromDate=sdfString.format(fromDate); 
	LogLevel.DEBUG(5, new Throwable(), "strFromDate :"+ strFromDate);

	String strToDate=sdfString.format(toDate); 
	LogLevel.DEBUG(5, new Throwable(), "strToDate :"+ strToDate);
	
	if(struserName.equals("operator") )
	{
		strOperatorConditions="";
	}
	else
	{
		strOperatorConditions=" and agent_name in ('"+struserName+"','null','NULL','storeoperator')";
	}

	if(statusfilter.equals("") || statusfilter.equals("Select Status"))
	{
		strConditions2="";
	}
	else
	{
		strConditions2=" and order_status='"+statusfilter+"'";
	}

	if(substatusfilter.equals("") || substatusfilter.equals("Select Sub Status") || substatusfilter.equals("0")  || substatusfilter.equals("null") || substatusfilter.equals("Select"))
	{
		strConditionssubstatus="";
	}
	else
	{
		String substatusfilter_2= substatusfilter.replaceAll(",", "','");
		
		strConditionssubstatus=" and order_reasons in ('"+substatusfilter_2+"')";
	}

	if(retselectname.equals("") || retselectname.equals("Select Franchisee") || retselectname.equals("0"))
	{
		strConditionsretailerselect="";
	}
	else
	{
		strConditionsretailerselect=" and retailer_name='"+retselectname+"'";
	}
	
	if(fromdatefilter.equals(""))
	{
		if(substatusfilter.equals("confirm"))
		{
			if(strConditions.equals(""))
			strConditions=" ( date(creation_date) between '"+strFromDate+"' and '"+strToDate+"')";
			else
			strConditions=strConditions+" and ( date(creation_date) between '"+strFromDate+"' and '"+strToDate+"')";
		}
		else if(substatusfilter.equals("installed"))
		{
			if(strConditions.equals(""))
			strConditions="( date(installed_date) between '"+strFromDate+"' and '"+strToDate+"')";
			else
			strConditions=strConditions+" and ( date(installed_date) between '"+strFromDate+"' and '"+strToDate+"')";
		}
		else if(substatusfilter.contains("cancelled"))
		{
			if(strConditions.equals(""))
			strConditions="( date(creation_date) between '"+strFromDate+"' and '"+strToDate+"')";
			else
			strConditions=strConditions+" and ( date(creation_date) between '"+strFromDate+"' and '"+strToDate+"')";
		}
		else if(substatusfilter.equals("postponed") || substatusfilter.equals("Postponed"))
		{
			if(strConditions.equals(""))
			strConditions=" ( date(postpone_date) between '"+strFromDate+"' and '"+strToDate+"')";
			else
			strConditions=strConditions+" and (date(postpone_date) between '"+strFromDate+"' and '"+strToDate+"')";
		}
		else
		{
			if(strConditions.equals(""))
			strConditions=" ( date(creation_date) between '"+strFromDate+"' and '"+strToDate+"' or date(postpone_date) between '"+strFromDate+"' and '"+strToDate+"' or date(installed_date) between '"+strFromDate+"' and '"+strToDate+"' or date(updated_date) between '"+strFromDate+"' and '"+strToDate+"')";
			else
			strConditions=strConditions+" and ( date(creation_date) between '"+strFromDate+"' and '"+strToDate+"' or date(postpone_date) between '"+strFromDate+"' and '"+strToDate+"' or date(installed_date) between '"+strFromDate+"' and '"+strToDate+"' or date(updated_date) between '"+strFromDate+"' and '"+strToDate+"')";
		}
		
		if(statusfilter.equals("SortByCreationDate") )
		{
			strConditions=" ( date(creation_date) between '"+strFromDate+"' and '"+strToDate+"')";
			strConditions2="";
		}
	}
	else
	{
		Date fromDate1=sdfDate.parse(fromdatefilter); 
		String strFromDate1=sdfString.format(fromDate1); 
		LogLevel.DEBUG(5, new Throwable(), "strFromDate1 :"+ strFromDate1);
		
		
		Date toDate1=sdfDate.parse(todatefilter); 
		String strToDate1=sdfString.format(toDate1); 
		LogLevel.DEBUG(5, new Throwable(), "strToDate1 :"+ strToDate1);

		String FromTimeCondition="";
		String ToTimeCondition="";
		
		if(fromtimefilter.equals(""))
		{
			FromTimeCondition=" 00:00:00";
		}
		else{
			FromTimeCondition=" "+fromtimefilter;
		}
		if(totimefilter.equals(""))
		{
			ToTimeCondition=" 23:59:59";
		}
		else{
			ToTimeCondition=" "+totimefilter;
		}
		
		if(substatusfilter.equals("confirm"))
		{

			if(strConditions.equals(""))
			strConditions=" ( creation_date >= '"+strFromDate1+""+FromTimeCondition+"' and creation_date <= '"+strToDate1+""+ToTimeCondition+"')";
			else
			strConditions=strConditions+"( and creation_date >= '"+strFromDate1+""+FromTimeCondition+"' and creation_date <= '"+strToDate1+""+ToTimeCondition+"')";

		}
		else if(substatusfilter.equals("installed"))
		{
			if(strConditions.equals(""))
			strConditions=" ( installed_date >= '"+strFromDate1+""+FromTimeCondition+"' and installed_date <= '"+strToDate1+""+ToTimeCondition+"')";
			else
			strConditions=strConditions+"and installed_date >= '"+strFromDate1+""+FromTimeCondition+"' and installed_date <= '"+strToDate1+""+ToTimeCondition+"'";
		}
		else if(substatusfilter.contains("cancelled"))
		{
			if(strConditions.equals(""))
			strConditions=" ( creation_date >= '"+strFromDate1+""+FromTimeCondition+"' and creation_date <= '"+strToDate1+""+ToTimeCondition+"')";
			else
			strConditions=strConditions+"and creation_date >= '"+strFromDate1+""+FromTimeCondition+"' and creation_date <= '"+strToDate1+""+ToTimeCondition+"'";
		}
		else if(substatusfilter.equals("postponed") || substatusfilter.equals("Postponed"))
		{
			if(strConditions.equals(""))
			strConditions=" (postpone_date >= '"+strFromDate1+""+FromTimeCondition+"' and postpone_date <= '"+strToDate1+""+ToTimeCondition+"')";
			else
			strConditions=strConditions+" and ( postpone_date >= '"+strFromDate1+""+FromTimeCondition+"' and postpone_date <= '"+strToDate1+""+ToTimeCondition+"')";

		}
		else
		{
			if(strConditions.equals(""))
			strConditions=" ( creation_date >= '"+strFromDate1+""+FromTimeCondition+"' and creation_date <= '"+strToDate1+""+ToTimeCondition+"' or postpone_date >= '"+strFromDate1+""+FromTimeCondition+"' and postpone_date <= '"+strToDate1+""+ToTimeCondition+"' or installed_date >= '"+strFromDate1+""+FromTimeCondition+"' and installed_date <= '"+strToDate1+""+ToTimeCondition+"' or updated_date >= '"+strFromDate1+""+FromTimeCondition+"' and updated_date <= '"+strToDate1+""+ToTimeCondition+"' )";
			else
			strConditions=strConditions+" and (creation_date >= '"+strFromDate1+""+FromTimeCondition+"' and creation_date <= '"+strToDate1+""+ToTimeCondition+"' or postpone_date >= '"+strFromDate1+""+FromTimeCondition+"' and postpone_date <= '"+strToDate1+""+ToTimeCondition+"' or installed_date >= '"+strFromDate1+""+FromTimeCondition+"' and installed_date <= '"+strToDate1+""+ToTimeCondition+"' or updated_date >= '"+strFromDate1+""+FromTimeCondition+"' and updated_date <= '"+strToDate1+""+ToTimeCondition+"')";
		}
		if(statusfilter.equals("SortByCreationDate") )
		{
			strConditions=" ( date(creation_date) between '"+strFromDate+""+FromTimeCondition+"' and '"+strToDate+""+ToTimeCondition+"')";
			strConditions2="";
		}
	}

	if(keyword.equals("confirm"))
		{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' and order_status='confirmed' ";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= " and order_status='confirmed'";
			}
		}
	else if(keyword.equals("All") || keyword.equals(""))
		{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' ";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= "";
			}
		}
	else if(keyword.equals("ordered"))
		{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' and order_status='ordered' ";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= " and order_status='ordered'";
			}
		}
		else if(keyword.equals("installed"))
		{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' and order_status='installed' ";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= " and order_status='installed'";
			}
		}
		else if(keyword.equals("postponed") || keyword.equals("Postponed"))
		{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' and order_status in('postponed','Postponed')";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= " and order_status in('postponed','Postponed') ";
			}
		}
		else if(keyword.equals("cancelled-franchisee-offbushrs"))
		{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' and order_status='cancelled-franchisee-offbushrs' ";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= " and order_status='cancelled-franchisee-offbushrs'";
			}
		}
		else if(keyword.equals("cancelled-franchisee-denied"))
			{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' and order_status='cancelled-franchisee-denied' ";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= " and order_status='cancelled-franchisee-denied'";
			}
		}
		else if(keyword.equals("cancelled-franchisee-notresponded"))
			{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' and order_status='cancelled-franchisee-notresponded' ";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= " and order_status='cancelled-franchisee-notresponded'";
			}
		}
		else if(keyword.equals("cancelled-franchisee-modeloutofstock"))
			{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' and order_status='cancelled-franchisee-modeloutofstock' ";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= " and order_status='cancelled-franchisee-modeloutofstock'";
			}
		}
		else if(keyword.equals("cancelled-customer"))
			{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' and order_status='cancelled-customer' ";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= " and order_status='cancelled-customer'";
			}
		}
		else if(keyword.equals("cancelled-regenerated"))
			{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' and order_status='cancelled-regenerated' ";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= " and order_status='cancelled-regenerated'";
			}
		}
		if(operatorfilter.equals("") || operatorfilter.equals("Select Operator"))
		{
			strOperatorFilterConditions="";
		}
		else
		{
			strOperatorFilterConditions=" and agent_name in ('"+struserName+"','null','NULL','storeoperator')";
		}
			strSqlQry="select order_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobile_number,retailer_emailid,service_engineer_name,service_engineer_mobile,state,city,inverter_brand,inverter_capacity,quantity,payment_mode,payment_mode_type,inverter_model,price,pincode,area,order_status,order_reasons,operator,agent_name,order_agent_comments,creation_date, CAST(first_contacted_date AS CHAR) as first_contacted_date,CAST(postpone_date AS CHAR) as postpone_date,CAST(installed_date AS CHAR) as installed_date,CAST(updated_date AS CHAR) as updated_date,consumer_address,confirm_by  from inverter_order_details where "+strConditions+" "+strConditions1+" "+strConditions2+" "+strConditionssubstatus+" "+strConditionsretailerselect+" "+strOperatorFilterConditions+" "+strOperatorConditions+" "+test_orders_Condition+" order by (CASE order_status WHEN 'Repeated' THEN 1 WHEN 'Postponed' THEN 2 WHEN 'Customer Not Contacted' THEN 3 WHEN 'Customer Contacted' THEN 4 WHEN 'confirmed' THEN 5 WHEN 'Not Confirmed' THEN 6 ELSE 100 END) ASC" ;
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);


			Vector BatOrdstatusVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"BatOrdstatusVector:"+ BatOrdstatusVector );


		String strSqlQry1="select distinct retailer_name from inverter_order_details where "+strConditions+" "+strConditions1+" "+strConditions2+"  "+strConditionssubstatus+" "+strOperatorFilterConditions+" "+strOperatorConditions+" "+test_orders_Condition+"  order by creation_date";
		LogLevel.DEBUG(5, new Throwable(), "strSqlQry1 :" + strSqlQry1);


		Vector RetOrdstatusVector=qm.executeQuery(strSqlQry1);
		LogLevel.DEBUG(5,new Throwable(),"RetOrdstatusVector:"+ RetOrdstatusVector );

	if(session.getAttribute("sesRetOrderstatusVectorVector")!=null)
	session.removeAttribute("sesRetOrderstatusVectorVector");
	session.setAttribute("sesRetOrderstatusVectorVector",RetOrdstatusVector);


	if(session.getAttribute("sesBatOrderstatusVectorVector")!=null)
	session.removeAttribute("sesBatOrderstatusVectorVector");
	session.setAttribute("sesBatOrderstatusVectorVector",BatOrdstatusVector);
	
	if(session.getAttribute("sesOptions")!=null)
	session.removeAttribute("sesOptions");
	session.setAttribute("sesOptions",htOptions);
	res.sendRedirect("../jsp/operator/ordermis/inverterconfirmedordereddetails.jsp?txtFromDate="+txtFromDate+"&txtToDate="+txtToDate+"&city="+city+"&keyword="+keyword+"&dates="+strDateOpt+"&statusfilter="+statusfilter+"&substatusfilter="+substatusfilter+"&retselectname="+retselectname+"&operatorfilter="+operatorfilter+"&fromdatefilter="+fromdatefilter+"&fromtimefilter="+fromtimefilter+"&todatefilter="+todatefilter+"&totimefilter="+totimefilter+"&test_orders="+test_orders+"&orders_of="+orders_of);
	return strRes;
	}
	catch (Exception e)
	{
	LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
	e.printStackTrace();
	}
 	return strRes;
  }

  /* **************************************************************************************************************************************\
	* This method is to get battery models.
	* @strSqlQry : carries the query to battery models details in battery_details table.
	\* **************************************************************************************************************************************/
	public String updateorderstatus(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort, String FromEmailId,String strsuppemaild,String SMSFromAddress, String strsuppmobnumber)
	{
		String orderid= (req.getParameter("orderid")!=null)?(req.getParameter("orderid")):"";
		LogLevel.DEBUG(5, new Throwable(), "orderid :" + orderid);

		String ordernum= (req.getParameter("ordernumber")!=null)?(req.getParameter("ordernumber")):"";
		LogLevel.DEBUG(5, new Throwable(), "ordernum :" + ordernum);

		String orderstatus= (req.getParameter("orderstatus")!=null)?(req.getParameter("orderstatus")):"";
		LogLevel.DEBUG(5, new Throwable(), "orderstatus :" + orderstatus);

		String orderreason= (req.getParameter("orderreason")!=null)?(req.getParameter("orderreason")):"";
		LogLevel.DEBUG(5, new Throwable(), "orderreason :" + orderreason);

		String agentcomments= (req.getParameter("agentcomments")!=null)?(req.getParameter("agentcomments")):"";
		LogLevel.DEBUG(5, new Throwable(), "agentcomments :" + agentcomments);

		String postponedate= (req.getParameter("postponedate")!=null)?(req.getParameter("postponedate")):"";
		LogLevel.DEBUG(5, new Throwable(), "postponedate :" + postponedate);

		String retailertoorder= (req.getParameter("retailertoorder")!=null)?(req.getParameter("retailertoorder")):"";
		LogLevel.DEBUG(5, new Throwable(), "retailertoorder :" + retailertoorder);

		String inbrand= (req.getParameter("inverterbrand")!=null)?(req.getParameter("inverterbrand")):"";
		LogLevel.DEBUG(5, new Throwable(), "inbrand :" + inbrand);

		String incap= (req.getParameter("invertercap")!=null)?(req.getParameter("invertercap")):"";
		LogLevel.DEBUG(5, new Throwable(), "incap :" + incap);

		String inprice= (req.getParameter("inverterprice")!=null)?(req.getParameter("inverterprice")):"";
		LogLevel.DEBUG(5, new Throwable(), "inprice :" + inprice);

		String inmodel= (req.getParameter("invertermodel")!=null)?(req.getParameter("invertermodel")):"";
		LogLevel.DEBUG(5, new Throwable(), "inmodel :" + inmodel);

		String cusname= (req.getParameter("cusname")!=null)?(req.getParameter("cusname")):"";
		LogLevel.DEBUG(5, new Throwable(), "cusname :" + cusname);

		String cusemailid= (req.getParameter("cusemailid")!=null)?(req.getParameter("cusemailid")):"";
		LogLevel.DEBUG(5, new Throwable(), "cusemailid :" + cusemailid);

		String cusaddr1= (req.getParameter("cusaddr1")!=null)?(req.getParameter("cusaddr1")):"";
		LogLevel.DEBUG(5, new Throwable(), "cusaddr1 :" + cusaddr1);

		String cusaddr2= (req.getParameter("cusaddr2")!=null)?(req.getParameter("cusaddr2")):"";
		LogLevel.DEBUG(5, new Throwable(), "cusaddr2 :" + cusaddr2);

		String cusstate= (req.getParameter("cusstate")!=null)?(req.getParameter("cusstate")):"";
		LogLevel.DEBUG(5, new Throwable(), "cusstate :" + cusstate);

		String cuscity= (req.getParameter("cuscity")!=null)?(req.getParameter("cuscity")):"";
		LogLevel.DEBUG(5, new Throwable(), "cuscity :" + cuscity);

		String cusarea= (req.getParameter("cusarea")!=null)?(req.getParameter("cusarea")):"";
		LogLevel.DEBUG(5, new Throwable(), "cusarea :" + cusarea);
		
		String payment_mode= (req.getParameter("payment_mode")!=null)?(req.getParameter("payment_mode")):"";
		LogLevel.DEBUG(5, new Throwable(), "payment_mode :" + payment_mode);
		
		String Quantity= (req.getParameter("Quantity")!=null)?(req.getParameter("Quantity")):"";
		LogLevel.DEBUG(5, new Throwable(), "Quantity :" + Quantity);
		
		String rating= (req.getParameter("rating")!=null)?(req.getParameter("rating")):"";
		LogLevel.DEBUG(5, new Throwable(), "rating :" + rating);		
        
        String paymentcollected= (req.getParameter("paymentcollected")!=null)?(req.getParameter("paymentcollected")):"";
		LogLevel.DEBUG(5, new Throwable(), "paymentcollected :" + paymentcollected);
		
		String SMSURL= (req.getParameter("SMSURL")!=null)?(req.getParameter("SMSURL")):"";
		LogLevel.DEBUG(5, new Throwable(), "SMSURL :" + SMSURL);
		
		String shorturl= (req.getParameter("shorturl")!=null)?(req.getParameter("shorturl")):"";
		LogLevel.DEBUG(5, new Throwable(), "shorturl :" + shorturl);		
		
		String SMSURL1= (req.getParameter("SMSURL1")!=null)?(req.getParameter("SMSURL1")):"";
		LogLevel.DEBUG(5, new Throwable(), "SMSURL1 :" + SMSURL1);
		
		String shorturl1= (req.getParameter("shorturl1")!=null)?(req.getParameter("shorturl1")):"";
		LogLevel.DEBUG(5, new Throwable(), "shorturl1 :" + shorturl1);
		
		String googleshorturl1= "https://goo.gl/eKGqvC";
		LogLevel.DEBUG(5, new Throwable(), "googleshorturl1 :" + googleshorturl1);
		
		String googleshorturl= "https://goo.gl/RnpFVA";
		LogLevel.DEBUG(5, new Throwable(), "googleshorturl1 :" + googleshorturl);
		
		String strRes="";
		String stroperatorName=(String)session.getAttribute("sesBatteryOperatorName"); 
		LogLevel.DEBUG(5,new Throwable(),"stroperatorName :"+stroperatorName );
		String referred_msg_coupon_code="";
		int reslt_new=0;
		
		String User_Address_Landmark=cusaddr1+" Landmark : "+cusaddr2;
		User_Address_Landmark = User_Address_Landmark.replace("'","\\'");
		LogLevel.DEBUG(5,new Throwable()," User_Address_Landmark "+User_Address_Landmark);
		
		String customerpaid="";
		
		try
		{	
			SendSMS sendsms = new SendSMS(qm);
			String strpostponedate="";

			if(orderstatus.equals("postponed") || orderstatus.equals("Postponed"))
			{
				SimpleDateFormat sdfDate=new SimpleDateFormat("dd-MM-yyyy");
				Date txtpostponedate=sdfDate.parse(postponedate);

				SimpleDateFormat sdfString=new SimpleDateFormat("yyyy-MM-dd");
				strpostponedate=sdfString.format(txtpostponedate); 
				LogLevel.DEBUG(5, new Throwable(), "strpostponedate :"+ strpostponedate);

			}


			ServletOutputStream out=res.getOutputStream();
			
			String strSqlQryordstat = "SELECT order_agent_comments,order_status,consumer_name,consumer_emailid,order_reasons,inverter_brand,inverter_model,inverter_capacity,order_number,consumer_mobnumber,retailer_name,retailer_mobile_number,retailer_emailid,quantity,order_reasons,city,state,price,forwareded_order,CAST(first_contacted_date AS CHAR) as first_contacted_date FROM inverter_order_details WHERE  order_number='"+ordernum+"' and order_id='"+orderid+"'";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQryordstat :"+strSqlQryordstat);

			Hashtable ht = qm.getRow(strSqlQryordstat);
			String order_agent_comments=(String)ht.get("order_agent_comments");
			LogLevel.DEBUG(5,new Throwable(),"order_agent_comments :"+order_agent_comments);
			String order_status=(String)ht.get("order_status");
			LogLevel.DEBUG(5,new Throwable(),"order_status :"+order_status);
			String consumername=(String)ht.get("consumer_name");
			LogLevel.DEBUG(5,new Throwable(),"consumername :"+consumername);
			String consumeremailid=(String)ht.get("consumer_emailid");
			LogLevel.DEBUG(5,new Throwable(),"consumeremailid :"+consumeremailid);
			String inverterbrand=(String)ht.get("inverter_brand");
			LogLevel.DEBUG(5,new Throwable(),"inverterbrand :"+inverterbrand);
			String invertermodel=(String)ht.get("inverter_model");
			LogLevel.DEBUG(5,new Throwable(),"invertermodel :"+invertermodel);
			String invertercapacity=(String)ht.get("inverter_capacity");
			LogLevel.DEBUG(5,new Throwable(),"invertercapacity :"+invertercapacity);
			String ordernumber=(String)ht.get("order_number");
			LogLevel.DEBUG(5,new Throwable(),"ordernumber :"+ordernumber);
			String consumermobilenumber=(String)ht.get("consumer_mobnumber");
			LogLevel.DEBUG(5,new Throwable(),"consumermobilenumber :"+consumermobilenumber);
			String retailername=(String)ht.get("retailer_name");
			LogLevel.DEBUG(5,new Throwable(),"retailername :"+retailername);
			String retailermobilenumber=(String)ht.get("retailer_mobile_number");
			LogLevel.DEBUG(5,new Throwable(),"retailermobilenumber :"+retailermobilenumber);
			String retaileremail=(String)ht.get("retailer_emailid");
			LogLevel.DEBUG(5,new Throwable(),"retaileremail :"+retaileremail);
			String order_reasons=(String)ht.get("order_reasons");
			LogLevel.DEBUG(5,new Throwable(),"order_reasons :"+order_reasons);
			String city=(String)ht.get("city");
			LogLevel.DEBUG(5,new Throwable(),"city :"+city);
            String state=(String)ht.get("state");
			LogLevel.DEBUG(5,new Throwable(),"state :"+state);
			String forward_order=(String)ht.get("forwareded_order");
			LogLevel.DEBUG(5,new Throwable(),"forwareded_order :"+forward_order);
			String price=(String)ht.get("price");
			LogLevel.DEBUG(5,new Throwable(),"price :"+price);
			String quantity=(String)ht.get("quantity");
			LogLevel.DEBUG(5,new Throwable(),"quantity :"+quantity);
			
			String first_contacted_date=(String)ht.get("first_contacted_date");
			LogLevel.DEBUG(5,new Throwable(),"first_contacted_date :"+first_contacted_date);
			
			String Website_Name="BookBattery";
			String Website_URL="www.bookbattery.com";
			
			if(forward_order.equals("Yes"))
			{
				 Website_Name="BookBattery";
				 Website_URL="www.bookbattery.com";
			}
			else
			{
				 Website_Name="BookBattery";
				 Website_URL="www.bookbattery.com";
			}
				
			
			/**Code added by Bharath to insert first contacted date starts here**/
			
			if(first_contacted_date.equals("0000-00-00 00:00:00"))
			{
				LogLevel.DEBUG(5,new Throwable(),"first_contacted_date if:"+first_contacted_date);

				String Strinsertfirs_contact_dateQry="update inverter_order_details set first_contacted_date=now() where order_number='"+ordernumber+"'";
				int Strinsertfirs_contact_dateQryreslt = qm.executeUpdate(Strinsertfirs_contact_dateQry);
				
				LogLevel.DEBUG(5,new Throwable(),"Strinsertfirs_contact_dateQryreslt:"+Strinsertfirs_contact_dateQryreslt);
			}
			else
			{
				LogLevel.DEBUG(5,new Throwable(),"first_contacted_date else:"+first_contacted_date);
			}
			
			/**Code added by Bharath to insert first contacted date ends here**/

			if(agentcomments.equals("")) 
			{
				if(order_agent_comments == null || order_agent_comments.equals("") || order_agent_comments.equals("null") || order_agent_comments.equals(null))
				{
					order_agent_comments="";
				}
				else
				{
					order_agent_comments=order_agent_comments;

				}

			}
			else
			{
				if(order_agent_comments == null || order_agent_comments.equals("") || order_agent_comments.equals("null") || order_agent_comments.equals(null))
				{
					order_agent_comments=agentcomments+"-"+stroperatorName;
				}
				else
				{
					order_agent_comments=order_agent_comments+","+agentcomments+"-"+stroperatorName;
				}
			}
				order_agent_comments = order_agent_comments.replace("\\","\\\\"); order_agent_comments= order_agent_comments.replace("'","\\'"); order_agent_comments= order_agent_comments.replace("<","<"); order_agent_comments= order_agent_comments.replace("+","+"); order_agent_comments= order_agent_comments.replace("%u201C","&ldquo;"); order_agent_comments= order_agent_comments.replace("%u201D","&rdquo;"); order_agent_comments= order_agent_comments.replace("%20"," "); order_agent_comments= order_agent_comments.replace("%22","&#34;"); 
				order_agent_comments= order_agent_comments.replace("%27","\\'"); order_agent_comments= order_agent_comments.replace("%7E","~"); order_agent_comments= order_agent_comments.replace("%21","!"); order_agent_comments= order_agent_comments.replace("%23","#"); order_agent_comments= order_agent_comments.replace("%24","$"); order_agent_comments= order_agent_comments.replace("%25","%"); order_agent_comments= order_agent_comments.replace("%5E","^"); order_agent_comments= order_agent_comments.replace("%26","&");
				order_agent_comments= order_agent_comments.replace("%28","("); order_agent_comments= order_agent_comments.replace("%29",")"); order_agent_comments= order_agent_comments.replace("%7C","|"); order_agent_comments= order_agent_comments.replace("%7D","{"); order_agent_comments= order_agent_comments.replace("%7B","}"); order_agent_comments= order_agent_comments.replace("%3A",":"); order_agent_comments= order_agent_comments.replace("%3F","?"); order_agent_comments= order_agent_comments.replace("%3E",">");
				order_agent_comments= order_agent_comments.replace("%3C","<"); order_agent_comments= order_agent_comments.replace("%60","`"); order_agent_comments= order_agent_comments.replace("%3D","="); order_agent_comments= order_agent_comments.replace("%5C","\\\\"); order_agent_comments= order_agent_comments.replace("%5D","]"); order_agent_comments= order_agent_comments.replace("%5B","[");
				order_agent_comments= order_agent_comments.replace("%2C",",");order_agent_comments= order_agent_comments.replace("%3B",";");order_agent_comments= order_agent_comments.replace("%0A","\n");
			String strSqlQry ="";
			if(orderstatus.equals("postponed") || orderstatus.equals("Postponed"))
			{
				if(stroperatorName.equals("null") || stroperatorName.equals("NULL") || stroperatorName.equals(null) || stroperatorName == "null" || stroperatorName == "NULL" || stroperatorName == null || stroperatorName.equals("0") || stroperatorName == "0" || stroperatorName.equals(" ") || stroperatorName == "" || stroperatorName.equals("undefined") || stroperatorName == "undefined")
				{

					LogLevel.DEBUG(1,new Throwable(),"Operator Name is Empty or null");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#00dd00' class='vrb10'></font> ");
					out.println("Failed to update ordered status!");
				}
				else
				{
					strSqlQry= "update inverter_order_details set order_status='"+orderstatus+"',order_reasons='"+orderreason+"',order_agent_comments='"+order_agent_comments+"',agent_name='"+stroperatorName+"',postpone_date='"+strpostponedate+"',updated_date=now(),installed_date='0000-00-00' WHERE  order_number='"+ordernum+"' and order_id='"+orderid+"'";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
				}
			}
			else if(orderstatus.equals("Customer Contacted") && orderreason.equals("installed"))
			{

				String grandtotal="";
				String Total_Commission_Amount="";
				String Final_Commission_Amount="";
				double Commission_Diction_Rate;
				String Commission_Diction_Amount="";
				String Commission_WithOut_Old_Battery="";
				String Commission_With_Old_Battery="";
				
				
				customerpaid=String.valueOf((Integer.parseInt(price)*Integer.parseInt(quantity)));							
				LogLevel.DEBUG(5, new Throwable(), "customerpaid after:" + customerpaid);
				
				
				if(inverterbrand.equals("Amaron"))
				{
					if(retailername.contains("Amaron-Pitstop"))
					{
						LogLevel.DEBUG(5,new Throwable(),"payment_mode :"+payment_mode);
						
						if(payment_mode=="Debit Card" || payment_mode.equals("Debit Card") || payment_mode.equals("debit card")){
							Commission_Diction_Rate=0.01;
						}
						else if(payment_mode=="Credit Card" || payment_mode.equals("Credit Card") || payment_mode.equals("credit card")){
							Commission_Diction_Rate=0.02;
						}
						else{
							Commission_Diction_Rate=0.00;
						}
						
						
						Commission_Diction_Amount="price";

						String Total_Commission_Amount_SQL = "SELECT price,erp_pre_tax,referred_coupon_code,CAST(round((price/city_percentage)-(erp_pre_tax )) AS SIGNED) as Total_Commission,CAST(round((price/city_percentage)-(erp_pre_tax ))-("+Commission_Diction_Rate+"*"+Commission_Diction_Amount+") AS SIGNED) as Final_Commission FROM inverter_order_details WHERE order_number='"+ordernum+"' and order_id='"+orderid+"'";
						
						Hashtable Total_Commission_Amount_HT = qm.getRow(Total_Commission_Amount_SQL);
						LogLevel.DEBUG(5, new Throwable(), "Total_Commission_Amount_HT :" + Total_Commission_Amount_HT);
						LogLevel.DEBUG(5, new Throwable(), "Total_Commission_Amount_SQL :" + Total_Commission_Amount_SQL);
						if(Total_Commission_Amount_HT.isEmpty())
						{
							/*following code is for generating the random number */
						}
						else
						{
							String erp_pre_tax_tmp=(String)Total_Commission_Amount_HT.get("erp_pre_tax");
							LogLevel.DEBUG(5, new Throwable(), "erp_pre_tax_tmp :" + erp_pre_tax_tmp);
							
							if(erp_pre_tax_tmp.equals(null) || erp_pre_tax_tmp.equals("NULL") || erp_pre_tax_tmp.equals("0") || erp_pre_tax_tmp.equals("null") || erp_pre_tax_tmp == null || erp_pre_tax_tmp == "null")
							{
								String ProductActualPrice="";
								String ERP_Pre_TAX="";
								String City_Percentage="";
								
								int ProductActualPrice_2=0;
								int ERP_Pre_TAX_2=0;
								
								String Get_Product_Price_SQL ="select DISTINCT  a.inverter_actual_price,a.inverter_eretailer_price, b.inverter_city_percentage from inverter_price_details a, percentage b, inverter_order_details c WHERE c.order_number='"+ordernum+"' and c.order_id='"+orderid+"' and a.inverter_brand=c.inverter_brand and a.inverter_model=c.inverter_model and a.city=c.city and b.city=c.city ORDER BY inverter_price_id DESC LIMIT 1"; 
								LogLevel.DEBUG(5, new Throwable(), "Get_Product_Price_SQL :" + Get_Product_Price_SQL);

								Hashtable Get_Product_Price_SQL_HT = qm.getRow(Get_Product_Price_SQL);
								LogLevel.DEBUG(5, new Throwable(), "Get_Product_Price_SQL_HT :" + Get_Product_Price_SQL_HT);

								if(Get_Product_Price_SQL_HT.isEmpty())
								{
									strRes="Session Expired or Server Down. Please regenerate your order.";
									return strRes;
								}
								else
								{
									ProductActualPrice_2=(Integer)Get_Product_Price_SQL_HT.get("inverter_actual_price");
									LogLevel.DEBUG(5, new Throwable(), "ProductActualPrice :" + ProductActualPrice_2);
									
									ERP_Pre_TAX_2 =(Integer)Get_Product_Price_SQL_HT.get("inverter_eretailer_price");
									LogLevel.DEBUG(5, new Throwable(), "ERP_Pre_TAX :" + ERP_Pre_TAX_2);
									
									ProductActualPrice = Integer.toString(ProductActualPrice_2);
									ERP_Pre_TAX = Integer.toString(ERP_Pre_TAX_2);
									
									City_Percentage=(String)Get_Product_Price_SQL_HT.get("inverter_city_percentage");
									LogLevel.DEBUG(5, new Throwable(), "City_Percentage :" + City_Percentage);
								}
								
								strSqlQry = "UPDATE inverter_order_details SET erp_pre_tax = '"+ERP_Pre_TAX+"', MRP_Price = '"+ProductActualPrice+"', city_percentage = '"+City_Percentage+"'WHERE  order_number='"+ordernum+"' and order_id='"+orderid+"' ";
								LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
								
								int reslt = qm.executeUpdate(strSqlQry);
								if(reslt <0)
								{
									out.println("Failed to update ordered status!");
									strRes="Failed to update ordered status!";
								}
								else
								{
									out.println("Please Try again!");
									strRes="Please Try again!- Order Values updated";
								}
								return strRes;
							}
													
							long Total_Commission_Amount2=(Long)Total_Commission_Amount_HT.get("Total_Commission");
							LogLevel.DEBUG(5, new Throwable(), "Total_Commission_Amount2 :" + Total_Commission_Amount2);
							
							Total_Commission_Amount = Long.toString(Total_Commission_Amount2);
							LogLevel.DEBUG(5, new Throwable(), "Total_Commission_Amount :" + Total_Commission_Amount);
							
							long Final_Commission2=(Long)Total_Commission_Amount_HT.get("Final_Commission");
							LogLevel.DEBUG(5, new Throwable(), "Final_Commission2 :" + Final_Commission2);
							
							Final_Commission_Amount = Long.toString(Final_Commission2);
							LogLevel.DEBUG(5, new Throwable(), "Final_Commission_Amount :" + Final_Commission_Amount);
							
							Commission_WithOut_Old_Battery=(String)Total_Commission_Amount_HT.get("price");
							LogLevel.DEBUG(5, new Throwable(), "Commission_WithOut_Old_Battery :" + Commission_WithOut_Old_Battery);
							
							Commission_With_Old_Battery=(String)Total_Commission_Amount_HT.get("witholdbatprice");
							LogLevel.DEBUG(5, new Throwable(), "Commission_With_Old_Battery :" + Commission_With_Old_Battery);
						}
						
						/*Code to calculate and add referral amount to wallet starts here*/
					
						if(payment_mode.equals("Online Payment After Fitment"))
						{
	
						String requestUrl="";
						String payment_link_url="";
						String CheckPaymentSql ="select order_id,status,payment_url from online_transaction_details where order_id='"+ordernumber+"'";
						
						LogLevel.DEBUG(5, new Throwable(), "CheckPaymentSql :" + CheckPaymentSql);	
						Hashtable CheckPaymentSql_HT = qm.getRow(CheckPaymentSql);
						LogLevel.DEBUG(5, new Throwable(), "CheckPaymentSql_HT :" + CheckPaymentSql_HT);

							if(CheckPaymentSql_HT.isEmpty())
							{
								LogLevel.DEBUG(5, new Throwable(), "Create_Payment_ Inside");
								
								//*********************################## Online Payment Request Generation Code ###### START #####################*****************//
								OnlinePayment Create_Payment_Link = new OnlinePayment(qm);
								LogLevel.DEBUG(5, new Throwable(), "Create_Payment_Link :" + Create_Payment_Link);
								String Create_Payment_Link_Resp=  Create_Payment_Link.getPaymentLink(req,res,session,consumername,consumeremailid,consumermobilenumber,customerpaid,ordernumber);
								LogLevel.DEBUG(5, new Throwable(), "Create_Payment_Link_Resp :" + Create_Payment_Link_Resp);
								
								//*********************################## Online Payment Request Generation Code ###### END #####################*****************//
						
								if(Create_Payment_Link_Resp.contains("ERROR | -"))
								{
									return Create_Payment_Link_Resp;
								}
								else
								{
									
									LogLevel.DEBUG(5, new Throwable(), "Create_Payment_Link_Resp :" + Create_Payment_Link_Resp);
									
									requestUrl="https://api-ssl.bitly.com/v3/shorten?access_token=accc1abefa0f57d73bad49cd2b80dbefe15cf10e&longUrl="+Create_Payment_Link_Resp;
									LogLevel.DEBUG(5, new Throwable(), "requestUrl :" + requestUrl);

								}  /********************* Payment Link ends***********/
								
								URL obj = new URL(requestUrl);
								HttpURLConnection con = (HttpURLConnection) obj.openConnection();
								con.setRequestMethod("GET");
								int responseCode = con.getResponseCode();
								LogLevel.DEBUG(5, new Throwable(), "responseCode" + responseCode);
								if (responseCode == HttpURLConnection.HTTP_OK) { // success
									BufferedReader in = new BufferedReader(new InputStreamReader(
									con.getInputStream()));
									String inputLine;
									StringBuffer response = new StringBuffer();
									while ((inputLine = in.readLine()) != null) {
									LogLevel.DEBUG(5, new Throwable(), "inputLine" + inputLine);
									response.append(inputLine);
									
								}
								LogLevel.DEBUG(5, new Throwable(), "string response :" + response);
								JSONObject jsonObject = new JSONObject(response.toString());
								LogLevel.DEBUG(5, new Throwable(), "string status_txt :" + jsonObject.get("status_txt"));
								LogLevel.DEBUG(5, new Throwable(), "string status_txt :" + jsonObject.get("data"));
								JSONObject jsonarray = new JSONObject(jsonObject.get("data").toString());
								LogLevel.DEBUG(5, new Throwable(), "json jsonarray" + jsonarray);
								String surl = jsonarray.getString("url");
								LogLevel.DEBUG(5, new Throwable(), "shorturl" + surl);
								payment_link_url = surl;
								LogLevel.DEBUG(5, new Throwable(), "shorturl" + surl);
								String lurl = jsonarray.getString("long_url");
								LogLevel.DEBUG(5, new Throwable(), "long_url" + lurl);
							
								String SuccessMessage ="Dear Customer, Please make your payment for the battery order with order number "+ordernumber+" in BookBattery by clicking on the link "+payment_link_url+" ."; 
								LogLevel.DEBUG(5,new Throwable(),"SuccessMessage:"+SuccessMessage);
								
								String strSMSResponse_PL=sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,SuccessMessage,consumermobilenumber);
								LogLevel.DEBUG(5,new Throwable(),"strSMSResponse_PL:"+strSMSResponse_PL);
								
								String[] order_coupon_code_array=ordernumber.split("ORDI");
					
								//LogLevel.DEBUG(5,new Throwable(),"order_referred_code[0]:"+order_coupon_code_array[0]);
								LogLevel.DEBUG(5,new Throwable(),"order_referred_code[1]:"+order_coupon_code_array[1]);
								
								String order_coupon_code="REF"+order_coupon_code_array[1];
								LogLevel.DEBUG(5,new Throwable(),"order_coupon_code:"+order_coupon_code);
								
								String referred_coupon_code =(String)Total_Commission_Amount_HT.get("referred_coupon_code");
								LogLevel.DEBUG(5, new Throwable(), "referred_coupon_code :" + referred_coupon_code);
								
								strSqlQry= "update inverter_order_details set order_status='Customer Contacted',order_reasons='Payment Link Generated',payment_mode='"+payment_mode+"', order_agent_comments='"+order_agent_comments+"',total_commission_amount='"+Total_Commission_Amount+"',inverter_commission_amount='"+Final_Commission_Amount+"',agent_name='"+stroperatorName+"',updated_date=now(),order_coupon_code='"+order_coupon_code+"', collected_party='"+paymentcollected+"',installed_date=now(),coupon_code_expiry_date=DATE_ADD(now(),INTERVAL 6 MONTH),postpone_date='0000-00-00' WHERE  order_number='"+ordernum+"' and order_id='"+orderid+"'";
								LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
							
							} 
							else 
							{
								strRes="Please Try again!- Order Values updated";
								LogLevel.DEBUG(5, new Throwable(), "GET request not worked");
							}
									 
							} 
							else
							{
								
								String[] order_coupon_code_array=ordernumber.split("ORDI");
					
								//LogLevel.DEBUG(5,new Throwable(),"order_referred_code[0]:"+order_coupon_code_array[0]);
								LogLevel.DEBUG(5,new Throwable(),"order_referred_code[1]:"+order_coupon_code_array[1]);
								
								String order_coupon_code="REF"+order_coupon_code_array[1];
								LogLevel.DEBUG(5,new Throwable(),"order_coupon_code:"+order_coupon_code);
								
								String referred_coupon_code =(String)Total_Commission_Amount_HT.get("referred_coupon_code");
								LogLevel.DEBUG(5, new Throwable(), "referred_coupon_code :" + referred_coupon_code);
								 
								 String payment_orderid=(String)CheckPaymentSql_HT.get("order_id");
								 String payment_status=(String)CheckPaymentSql_HT.get("status");
								 requestUrl=(String)CheckPaymentSql_HT.get("payment_url");
								 LogLevel.DEBUG(5, new Throwable(), "payment_orderid" + payment_orderid);
								 LogLevel.DEBUG(5, new Throwable(), "payment_status" + payment_status);
								 LogLevel.DEBUG(5, new Throwable(), "payment_url" + requestUrl);
										
								 if(payment_status=="completed" || payment_status.equals("completed")){
									 
									LogLevel.DEBUG(5, new Throwable(), "Inside" + payment_status);
									strSqlQry= "update inverter_order_details set order_status='"+orderstatus+"',order_reasons='installed',payment_mode='Online',payment_mode_type='"+payment_mode+"',payment_installation='Online Payment After Fitment', order_agent_comments='"+order_agent_comments+"',total_commission_amount='"+Total_Commission_Amount+"',inverter_commission_amount='"+Final_Commission_Amount+"',agent_name='"+stroperatorName+"',updated_date=now(),order_coupon_code='"+order_coupon_code+"', collected_party='"+paymentcollected+"',installed_date=now(),coupon_code_expiry_date=DATE_ADD(now(),INTERVAL 6 MONTH),postpone_date='0000-00-00' WHERE  order_number='"+ordernum+"' and order_id='"+orderid+"'";
									LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
										
								 } 
								 else 
								 {
									
									LogLevel.DEBUG(5, new Throwable(), "Outside" + payment_status);
									strSqlQry= "update inverter_order_details set order_status='"+orderstatus+"',order_reasons='Payment Pending',payment_mode='"+payment_mode+"', order_agent_comments='"+order_agent_comments+"',total_commission_amount='"+Total_Commission_Amount+"',inverter_commission_amount='"+Final_Commission_Amount+"',agent_name='"+stroperatorName+"',updated_date=now(),order_coupon_code='"+order_coupon_code+"',installed_date=now(), collected_party='"+paymentcollected+"',coupon_code_expiry_date=DATE_ADD(now(),INTERVAL 6 MONTH),postpone_date='0000-00-00' WHERE  order_number='"+ordernum+"' and order_id='"+orderid+"'";
									LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
								 }
								 
							}  //********************* Payment Query HT ends***********/

					}
					
					
					else
					{
					
					String[] order_coupon_code_array=ordernumber.split("ORDI");
					
					//LogLevel.DEBUG(5,new Throwable(),"order_referred_code[0]:"+order_coupon_code_array[0]);
					LogLevel.DEBUG(5,new Throwable(),"order_referred_code[1]:"+order_coupon_code_array[1]);
					
					String order_coupon_code="REF"+order_coupon_code_array[1];
					LogLevel.DEBUG(5,new Throwable(),"order_coupon_code:"+order_coupon_code);
					
					String referred_coupon_code =(String)Total_Commission_Amount_HT.get("referred_coupon_code");
					LogLevel.DEBUG(5, new Throwable(), "referred_coupon_code :" + referred_coupon_code);					
					strSqlQry= "update inverter_order_details set order_status='"+orderstatus+"',order_reasons='"+orderreason+"',payment_mode='"+payment_mode+"',payment_mode_type='"+payment_mode+"', order_agent_comments='"+order_agent_comments+"',total_commission_amount='"+Total_Commission_Amount+"',inverter_commission_amount='"+Final_Commission_Amount+"',agent_name='"+stroperatorName+"',updated_date=now(),order_coupon_code='"+order_coupon_code+"', collected_party='"+paymentcollected+"',installed_date=now(),coupon_code_expiry_date=DATE_ADD(now(),INTERVAL 6 MONTH),postpone_date='0000-00-00' WHERE  order_number='"+ordernum+"' and order_id='"+orderid+"'";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
					
					reslt_new = qm.executeUpdate(strSqlQry);
					
					if(reslt_new <0)
					{
						out.println("Failed to update ordered status!");
					    strRes="Failed to update ordered status!";
					}
					else
					{
						/*added to insert into generated_referal_code_details table to track how many coupon codes are generated and for validating*/
						String product_type="Inverter";
						
						String ins_gen_SqlQry = "insert into batterywaledb.generated_referal_code_details(order_coupon_code_id,order_coupon_code,order_number,product_type,referred_coupon_code,domain_name,coupon_code_expiry_date,creation_date) values( NULL,'"+order_coupon_code+"','"+ordernumber+"','"+product_type+"','"+referred_coupon_code+"','BookBattery',DATE_ADD(now(),INTERVAL 6 MONTH),now())";
						
						int resltgen_SqlQry = qm.executeUpdate(ins_gen_SqlQry);
						
						if(resltgen_SqlQry < 0)
						{
							out.println("Failed to generated_referal_code_details!");
							strRes="Failed to generated_referal_code_details!";
						}
						else
						{
						
						LogLevel.DEBUG(5, new Throwable(), "referred_coupon_code outside else:" + referred_coupon_code);
						
						if(referred_coupon_code.equalsIgnoreCase("0"))
						{
							LogLevel.DEBUG(5, new Throwable(), "referred_coupon_code inside if:" + referred_coupon_code);
						}
						else
						{
							LogLevel.DEBUG(5, new Throwable(), "referred_coupon_code inside else:" + referred_coupon_code);
							
							String fetch_coupon_details ="select order_number,coupon_code_expiry_date from batterywaledb.generated_referal_code_details where order_coupon_code='"+referred_coupon_code+"'";
							
							
							LogLevel.DEBUG(5, new Throwable(), "fetch_coupon_details :" + fetch_coupon_details);

							Hashtable fetch_coupon_details_HT = qm.getRow(fetch_coupon_details);
							LogLevel.DEBUG(5, new Throwable(), "fetch_coupon_details_HT :" + fetch_coupon_details_HT);
							
							String referred_order_number=(String)fetch_coupon_details_HT.get("order_number");
							LogLevel.DEBUG(5, new Throwable(), "referred_order_number :" + referred_order_number);
							
							int refferal_amount=50;
							
							String fetch_coupon_code_details ="select referred_coupon_code,referred_order_number,refferal_amount from batterywaledb.coupon_code_details where referred_coupon_code='"+referred_coupon_code+"'"; 
							
							Hashtable fetch_coupon_code_details_HT = qm.getRow(fetch_coupon_code_details);
							LogLevel.DEBUG(5, new Throwable(), "fetch_coupon_code_details_HT :" + fetch_coupon_code_details_HT);
								
							String Ste_ref_SqlQry;
							
							if(fetch_coupon_code_details_HT.isEmpty())
							{
								//Enters into condition if there is no record in the coupon_code_details table with the reference code entered
								 Ste_ref_SqlQry = "insert into batterywaledb.coupon_code_details(coupon_code_id,referred_coupon_code,referred_order_number,coupon_code_expiry_date,refferal_amount,coupon_applied_order_number,state,city,creation_date) values( NULL,'"+referred_coupon_code+"','"+referred_order_number+"',DATE_ADD(now(),INTERVAL 6 MONTH),'"+refferal_amount+"','"+ordernumber+"','"+state+"','"+city+"',now())";
							}
							else
							{
								//Enters into condition if there is a record in the coupon_code_details table and updates the his refferal_amount
								String existing_refferal_amount=String.valueOf(fetch_coupon_code_details_HT.get("refferal_amount"));
								
								refferal_amount=Integer.parseInt(existing_refferal_amount)+refferal_amount;
								
								LogLevel.DEBUG(5, new Throwable(), "refferal_amount added:" + refferal_amount);
								
								
								 Ste_ref_SqlQry = "update batterywaledb.coupon_code_details set refferal_amount='"+refferal_amount+"' where referred_coupon_code='"+referred_coupon_code+"'";
							}
							
							
								int resltref = qm.executeUpdate(Ste_ref_SqlQry);
								LogLevel.DEBUG(5, new Throwable(), "resltref :" + resltref);
							
								if(resltref < 0)
								{
									out.println("Failed to update ordered status!");
									strRes="Failed to update ordered status!";
								}
								else
								{
									String Ste_ref_count_SqlQry = "select referred_order_number,refferal_amount from batterywaledb.coupon_code_details where referred_coupon_code='"+referred_coupon_code+"'";
									
									LogLevel.DEBUG(5, new Throwable(), "Ste_ref_count_SqlQry :" + Ste_ref_count_SqlQry);
									
									Hashtable fetch_coupon_code_count_HT = qm.getRow(Ste_ref_count_SqlQry);
									LogLevel.DEBUG(5, new Throwable(), "fetch_coupon_code_count_HT :" + fetch_coupon_code_count_HT);
									
									//String ref_count = (String)fetch_coupon_code_count_HT.get("refcount");
									String referred_coupon_order_number = String.valueOf(fetch_coupon_code_count_HT.get("referred_order_number"));
									
									String refferal_amount_fetched = String.valueOf(fetch_coupon_code_count_HT.get("refferal_amount"));
									
									int refferal_amount_fetched_int = Integer.parseInt(refferal_amount_fetched);
									
									LogLevel.DEBUG(5, new Throwable(), "refferal_amount_fetched_int :" + refferal_amount_fetched_int);
									
									if(refferal_amount_fetched_int<300)
									{
										//Dont Send SMS
										LogLevel.DEBUG(5, new Throwable(), "Dont Send Sms");
									}
									else
									{
										LogLevel.DEBUG(5, new Throwable(), "Send SMS");
										
										String Ste_ref_mob_SqlQry = "select consumer_mobnumber from batterywaledb.battery_order_details where order_number='"+referred_coupon_order_number+"' Union select consumer_mobnumber from batterywaledb.battery_order_details where order_number='"+referred_coupon_order_number+"'";
										
										LogLevel.DEBUG(5, new Throwable(), "Ste_ref_mob_SqlQry :" + Ste_ref_mob_SqlQry);
										
										Hashtable fetch_mob_SqlQry_HT = qm.getRow(Ste_ref_mob_SqlQry);
										LogLevel.DEBUG(5, new Throwable(), "fetch_mob_SqlQry_HT :" + fetch_mob_SqlQry_HT);
										
										if(fetch_mob_SqlQry_HT.isEmpty())
										{
											Ste_ref_mob_SqlQry = "select consumer_mobnumber from batterywaledb.inverter_order_details where order_number='"+referred_coupon_order_number+"' Union select consumer_mobnumber from batterywaledb.inverter_order_details where order_number='"+referred_coupon_order_number+"'";
											LogLevel.DEBUG(5, new Throwable(), "Ste_ref_mob_SqlQry battery :" + Ste_ref_mob_SqlQry);
											fetch_mob_SqlQry_HT = qm.getRow(Ste_ref_mob_SqlQry);
											LogLevel.DEBUG(5, new Throwable(), "fetch_mob_SqlQry_HT battery :" + fetch_mob_SqlQry_HT);
										}
										
										String consumer_mobnumber_fetched = String.valueOf(fetch_mob_SqlQry_HT.get("consumer_mobnumber"));
										
										String CongratsMessage ="Congrats, you have earned "+refferal_amount_fetched+" Rs in your referral Wallet. Use code "+referred_coupon_code+" to avail free car/inverter battery health check of worth Rs.299/599 while ordering or call 9603467559 . Thanks for your support."; 
										LogLevel.DEBUG(5,new Throwable(),"CongratsMessage:"+CongratsMessage);
										
										String strSMSResponse5=sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,CongratsMessage,consumer_mobnumber_fetched);
										LogLevel.DEBUG(5,new Throwable(),"strSMSResponse5:"+strSMSResponse5);
									}

								}
						}
						
						}
					}	
					
				/*Code to calculate and add referral amount to wallet ends here*/
						
					}
					
					}
					else
					{
						LogLevel.DEBUG(5,new Throwable(),"payment_mode :"+payment_mode);
						
						if(payment_mode=="Debit Card" || payment_mode.equals("Debit Card") || payment_mode.equals("debit card")){
							Commission_Diction_Rate=0.01;
						}
						else if(payment_mode=="Credit Card" || payment_mode.equals("Credit Card") || payment_mode.equals("credit card")){
							Commission_Diction_Rate=0.02;
						}
						else{
							Commission_Diction_Rate=0.00;
						}
						
						
						Commission_Diction_Amount="price";

						//String Total_Commission_Amount_SQL = "SELECT price,erp_pre_tax, CAST(round(((price))-(erp_pre_tax*city_percentage))/ 2 AS SIGNED) as Total_Commission, CAST(( round(((price))-(erp_pre_tax*city_percentage))/ 2 )-(("+Commission_Diction_Rate+"*"+Commission_Diction_Amount+")/2) AS SIGNED) as Final_Commission FROM inverter_order_details WHERE order_number='"+ordernum+"' and order_id='"+orderid+"'";
						
						String Total_Commission_Amount_SQL = "SELECT price,referred_coupon_code,erp_pre_tax,CAST(round(((price/city_percentage)-(erp_pre_tax ))/2) AS SIGNED) as Total_Commission,CAST(round(((price/city_percentage)-(erp_pre_tax ))/2)-("+Commission_Diction_Rate+"*"+Commission_Diction_Amount+") AS SIGNED) as Final_Commission FROM inverter_order_details WHERE order_number='"+ordernum+"' and order_id='"+orderid+"'";
	
						Hashtable Total_Commission_Amount_HT = qm.getRow(Total_Commission_Amount_SQL);
						LogLevel.DEBUG(5, new Throwable(), "Total_Commission_Amount_HT :" + Total_Commission_Amount_HT);
						LogLevel.DEBUG(5, new Throwable(), "Total_Commission_Amount_SQL :" + Total_Commission_Amount_SQL);
						if(Total_Commission_Amount_HT.isEmpty())
						{
							/*following code is for generating the random number */
						}
						else
						{
							String erp_pre_tax_tmp=(String)Total_Commission_Amount_HT.get("erp_pre_tax");
							LogLevel.DEBUG(5, new Throwable(), "erp_pre_tax_tmp :" + erp_pre_tax_tmp);
							
							if(erp_pre_tax_tmp.equals(null) || erp_pre_tax_tmp.equals("NULL") || erp_pre_tax_tmp.equals("0") || erp_pre_tax_tmp.equals("null") || erp_pre_tax_tmp == null || erp_pre_tax_tmp == "null")
							{
								String ProductActualPrice="";
								String ERP_Pre_TAX="";
								String City_Percentage="";
								
								int ProductActualPrice_2=0;
								int ERP_Pre_TAX_2=0;
								
								String Get_Product_Price_SQL ="select DISTINCT  a.inverter_actual_price,a.inverter_eretailer_price, b.inverter_city_percentage from inverter_price_details a, percentage b, inverter_order_details c WHERE c.order_number='"+ordernum+"' and c.order_id='"+orderid+"' and a.inverter_brand=c.inverter_brand and a.inverter_model=c.inverter_model and a.city=c.city and b.city=c.city ORDER BY inverter_price_id DESC LIMIT 1"; 
								LogLevel.DEBUG(5, new Throwable(), "Get_Product_Price_SQL :" + Get_Product_Price_SQL);

								Hashtable Get_Product_Price_SQL_HT = qm.getRow(Get_Product_Price_SQL);
								LogLevel.DEBUG(5, new Throwable(), "Get_Product_Price_SQL_HT :" + Get_Product_Price_SQL_HT);

								if(Get_Product_Price_SQL_HT.isEmpty())
								{
									strRes="Session Expired or Server Down. Please regenerate your order.";
									return strRes;
								}
								else
								{
									ProductActualPrice_2=(Integer)Get_Product_Price_SQL_HT.get("inverter_actual_price");
									LogLevel.DEBUG(5, new Throwable(), "ProductActualPrice :" + ProductActualPrice_2);
									
									ERP_Pre_TAX_2 =(Integer)Get_Product_Price_SQL_HT.get("inverter_eretailer_price");
									LogLevel.DEBUG(5, new Throwable(), "ERP_Pre_TAX :" + ERP_Pre_TAX_2);
									
									ProductActualPrice = Integer.toString(ProductActualPrice_2);
									ERP_Pre_TAX = Integer.toString(ERP_Pre_TAX_2);
									
									City_Percentage=(String)Get_Product_Price_SQL_HT.get("inverter_city_percentage");
									LogLevel.DEBUG(5, new Throwable(), "City_Percentage :" + City_Percentage);
								}
								
								strSqlQry = "UPDATE inverter_order_details SET erp_pre_tax = '"+ERP_Pre_TAX+"', MRP_Price = '"+ProductActualPrice+"', city_percentage = '"+City_Percentage+"'WHERE  order_number='"+ordernum+"' and order_id='"+orderid+"' ";
								LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
								
								int reslt = qm.executeUpdate(strSqlQry);
								if(reslt <0)
								{
									out.println("Failed to update ordered status!");
									strRes="Failed to update ordered status!";
								}
								else
								{
									out.println("Please Try again!");
									strRes="Please Try again!- Order Values updated";
								}
								return strRes;
							}
													
							long Total_Commission_Amount2=(Long)Total_Commission_Amount_HT.get("Total_Commission");
							LogLevel.DEBUG(5, new Throwable(), "Total_Commission_Amount2 :" + Total_Commission_Amount2);
							
							Total_Commission_Amount = Long.toString(Total_Commission_Amount2);
							LogLevel.DEBUG(5, new Throwable(), "Total_Commission_Amount :" + Total_Commission_Amount);
							
							long Final_Commission2=(Long)Total_Commission_Amount_HT.get("Final_Commission");
							LogLevel.DEBUG(5, new Throwable(), "Final_Commission2 :" + Final_Commission2);
							
							Final_Commission_Amount = Long.toString(Final_Commission2);
							LogLevel.DEBUG(5, new Throwable(), "Final_Commission_Amount :" + Final_Commission_Amount);
							
							Commission_WithOut_Old_Battery=(String)Total_Commission_Amount_HT.get("price");
							LogLevel.DEBUG(5, new Throwable(), "Commission_WithOut_Old_Battery :" + Commission_WithOut_Old_Battery);
							
							Commission_With_Old_Battery=(String)Total_Commission_Amount_HT.get("witholdbatprice");
							LogLevel.DEBUG(5, new Throwable(), "Commission_With_Old_Battery :" + Commission_With_Old_Battery);
						}
						
						if(payment_mode.equals("Online Payment After Fitment"))
						{
	
						String requestUrl="";
						String payment_link_url="";
						String CheckPaymentSql ="select order_id,status,payment_url from online_transaction_details where order_id='"+ordernumber+"'";
						
						LogLevel.DEBUG(5, new Throwable(), "CheckPaymentSql :" + CheckPaymentSql);	
						Hashtable CheckPaymentSql_HT = qm.getRow(CheckPaymentSql);
						LogLevel.DEBUG(5, new Throwable(), "CheckPaymentSql_HT :" + CheckPaymentSql_HT);

							if(CheckPaymentSql_HT.isEmpty())
							{
								LogLevel.DEBUG(5, new Throwable(), "Create_Payment_ Inside");
								
								//*********************################## Online Payment Request Generation Code ###### START #####################*****************//
								OnlinePayment Create_Payment_Link = new OnlinePayment(qm);
								LogLevel.DEBUG(5, new Throwable(), "Create_Payment_Link :" + Create_Payment_Link);
								String Create_Payment_Link_Resp=  Create_Payment_Link.getPaymentLink(req,res,session,consumername,consumeremailid,consumermobilenumber,customerpaid,ordernumber);
								LogLevel.DEBUG(5, new Throwable(), "Create_Payment_Link_Resp :" + Create_Payment_Link_Resp);
								
								//*********************################## Online Payment Request Generation Code ###### END #####################*****************//
						
								if(Create_Payment_Link_Resp.contains("ERROR | -"))
								{
									return Create_Payment_Link_Resp;
								}
								else
								{
									
									LogLevel.DEBUG(5, new Throwable(), "Create_Payment_Link_Resp :" + Create_Payment_Link_Resp);
									
									requestUrl="https://api-ssl.bitly.com/v3/shorten?access_token=accc1abefa0f57d73bad49cd2b80dbefe15cf10e&longUrl="+Create_Payment_Link_Resp;
									LogLevel.DEBUG(5, new Throwable(), "requestUrl :" + requestUrl);

								}  /********************* Payment Link ends***********/
								
								URL obj = new URL(requestUrl);
								HttpURLConnection con = (HttpURLConnection) obj.openConnection();
								con.setRequestMethod("GET");
								int responseCode = con.getResponseCode();
								LogLevel.DEBUG(5, new Throwable(), "responseCode" + responseCode);
								if (responseCode == HttpURLConnection.HTTP_OK) { // success
									BufferedReader in = new BufferedReader(new InputStreamReader(
									con.getInputStream()));
									String inputLine;
									StringBuffer response = new StringBuffer();
									while ((inputLine = in.readLine()) != null) {
									LogLevel.DEBUG(5, new Throwable(), "inputLine" + inputLine);
									response.append(inputLine);
									
								}
								LogLevel.DEBUG(5, new Throwable(), "string response :" + response);
								JSONObject jsonObject = new JSONObject(response.toString());
								LogLevel.DEBUG(5, new Throwable(), "string status_txt :" + jsonObject.get("status_txt"));
								LogLevel.DEBUG(5, new Throwable(), "string status_txt :" + jsonObject.get("data"));
								JSONObject jsonarray = new JSONObject(jsonObject.get("data").toString());
								LogLevel.DEBUG(5, new Throwable(), "json jsonarray" + jsonarray);
								String surl = jsonarray.getString("url");
								LogLevel.DEBUG(5, new Throwable(), "shorturl" + surl);
								payment_link_url = surl;
								LogLevel.DEBUG(5, new Throwable(), "shorturl" + surl);
								String lurl = jsonarray.getString("long_url");
								LogLevel.DEBUG(5, new Throwable(), "long_url" + lurl);
							
								String SuccessMessage ="Dear Customer, Please make your payment for the battery order with order number "+ordernumber+" in BookBattery by clicking on the link "+payment_link_url+" ."; 
								LogLevel.DEBUG(5,new Throwable(),"SuccessMessage:"+SuccessMessage);
								
								String strSMSResponse_PL=sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,SuccessMessage,consumermobilenumber);
								LogLevel.DEBUG(5,new Throwable(),"strSMSResponse_PL:"+strSMSResponse_PL);
								
								String[] order_coupon_code_array=ordernumber.split("ORDI");
					
								//LogLevel.DEBUG(5,new Throwable(),"order_referred_code[0]:"+order_coupon_code_array[0]);
								LogLevel.DEBUG(5,new Throwable(),"order_referred_code[1]:"+order_coupon_code_array[1]);
								
								String order_coupon_code="REF"+order_coupon_code_array[1];
								LogLevel.DEBUG(5,new Throwable(),"order_coupon_code:"+order_coupon_code);
								
								String referred_coupon_code =(String)Total_Commission_Amount_HT.get("referred_coupon_code");
								LogLevel.DEBUG(5, new Throwable(), "referred_coupon_code :" + referred_coupon_code);
								
								strSqlQry= "update inverter_order_details set order_status='Customer Contacted',order_reasons='Payment Link Generated',payment_mode='"+payment_mode+"', order_agent_comments='"+order_agent_comments+"',total_commission_amount='"+Total_Commission_Amount+"',inverter_commission_amount='"+Final_Commission_Amount+"',agent_name='"+stroperatorName+"',updated_date=now(),order_coupon_code='"+order_coupon_code+"', collected_party='"+paymentcollected+"',installed_date=now(),coupon_code_expiry_date=DATE_ADD(now(),INTERVAL 6 MONTH),postpone_date='0000-00-00' WHERE  order_number='"+ordernum+"' and order_id='"+orderid+"'";
								LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
							
							} 
							else 
							{
								strRes="Please Try again!- Order Values updated";
								LogLevel.DEBUG(5, new Throwable(), "GET request not worked");
							}
									 
							} 
							else
							{
								
								String[] order_coupon_code_array=ordernumber.split("ORDI");
					
								//LogLevel.DEBUG(5,new Throwable(),"order_referred_code[0]:"+order_coupon_code_array[0]);
								LogLevel.DEBUG(5,new Throwable(),"order_referred_code[1]:"+order_coupon_code_array[1]);
								
								String order_coupon_code="REF"+order_coupon_code_array[1];
								LogLevel.DEBUG(5,new Throwable(),"order_coupon_code:"+order_coupon_code);
								
								String referred_coupon_code =(String)Total_Commission_Amount_HT.get("referred_coupon_code");
								LogLevel.DEBUG(5, new Throwable(), "referred_coupon_code :" + referred_coupon_code);
								 
								 String payment_orderid=(String)CheckPaymentSql_HT.get("order_id");
								 String payment_status=(String)CheckPaymentSql_HT.get("status");
								 requestUrl=(String)CheckPaymentSql_HT.get("payment_url");
								 LogLevel.DEBUG(5, new Throwable(), "payment_orderid" + payment_orderid);
								 LogLevel.DEBUG(5, new Throwable(), "payment_status" + payment_status);
								 LogLevel.DEBUG(5, new Throwable(), "payment_url" + requestUrl);
										
								 if(payment_status=="completed" || payment_status.equals("completed")){
									 
									 
									LogLevel.DEBUG(5, new Throwable(), "Inside" + payment_status);
									strSqlQry= "update inverter_order_details set order_status='"+orderstatus+"',order_reasons='installed',payment_mode='Online',payment_mode_type='"+payment_mode+"',payment_installation='Online Payment After Fitment', order_agent_comments='"+order_agent_comments+"',total_commission_amount='"+Total_Commission_Amount+"',inverter_commission_amount='"+Final_Commission_Amount+"',agent_name='"+stroperatorName+"',updated_date=now(),order_coupon_code='"+order_coupon_code+"', collected_party='"+paymentcollected+"',installed_date=now(),coupon_code_expiry_date=DATE_ADD(now(),INTERVAL 6 MONTH),postpone_date='0000-00-00' WHERE  order_number='"+ordernum+"' and order_id='"+orderid+"'";
									LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
										
								 } 
								 else 
								 {
									
									LogLevel.DEBUG(5, new Throwable(), "Outside" + payment_status);
									strSqlQry= "update inverter_order_details set order_status='"+orderstatus+"',order_reasons='Payment Pending',payment_mode='"+payment_mode+"', order_agent_comments='"+order_agent_comments+"',total_commission_amount='"+Total_Commission_Amount+"',inverter_commission_amount='"+Final_Commission_Amount+"',agent_name='"+stroperatorName+"',updated_date=now(),order_coupon_code='"+order_coupon_code+"', collected_party='"+paymentcollected+"',installed_date=now(),coupon_code_expiry_date=DATE_ADD(now(),INTERVAL 6 MONTH),postpone_date='0000-00-00' WHERE  order_number='"+ordernum+"' and order_id='"+orderid+"'";
									LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
								 }
								 
							}  //********************* Payment Query HT ends***********/

					}
					
					else
					{
					/*Code to calculate and add referral amount to wallet starts here*/
					
					String[] order_coupon_code_array=ordernumber.split("ORDI");
					
					//LogLevel.DEBUG(5,new Throwable(),"order_referred_code[0]:"+order_coupon_code_array[0]);
					LogLevel.DEBUG(5,new Throwable(),"order_referred_code[1]:"+order_coupon_code_array[1]);
					
					String order_coupon_code="REF"+order_coupon_code_array[1];
					LogLevel.DEBUG(5,new Throwable(),"order_coupon_code:"+order_coupon_code);
					
					String referred_coupon_code =(String)Total_Commission_Amount_HT.get("referred_coupon_code");
					LogLevel.DEBUG(5, new Throwable(), "referred_coupon_code :" + referred_coupon_code);					
					strSqlQry= "update inverter_order_details set order_status='"+orderstatus+"',order_reasons='"+orderreason+"',payment_mode='"+payment_mode+"',payment_mode_type='"+payment_mode+"', order_agent_comments='"+order_agent_comments+"',total_commission_amount='"+Total_Commission_Amount+"',inverter_commission_amount='"+Final_Commission_Amount+"',agent_name='"+stroperatorName+"',updated_date=now(),order_coupon_code='"+order_coupon_code+"', collected_party='"+paymentcollected+"',installed_date=now(),coupon_code_expiry_date=DATE_ADD(now(),INTERVAL 6 MONTH),postpone_date='0000-00-00' WHERE  order_number='"+ordernum+"' and order_id='"+orderid+"'";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
					
					reslt_new = qm.executeUpdate(strSqlQry);
					
					if(reslt_new <0)
					{
						out.println("Failed to update ordered status!");
					    strRes="Failed to update ordered status!";
					}
					else
					{
						
						/*added to insert into generated_referal_code_details table to track how many coupon codes are generated and for validating*/
						String product_type="Inverter";
						
						String ins_gen_SqlQry = "insert into batterywaledb.generated_referal_code_details(order_coupon_code_id,order_coupon_code,order_number,product_type,referred_coupon_code,domain_name,coupon_code_expiry_date,creation_date) values( NULL,'"+order_coupon_code+"','"+ordernumber+"','"+product_type+"','"+referred_coupon_code+"','BookBattery',DATE_ADD(now(),INTERVAL 6 MONTH),now())";
						
						int resltgen_SqlQry = qm.executeUpdate(ins_gen_SqlQry);
						
						if(resltgen_SqlQry < 0)
						{
							out.println("Failed to generated_referal_code_details!");
							strRes="Failed to generated_referal_code_details!";
						}
						else
						{
							
							
						LogLevel.DEBUG(5, new Throwable(), "referred_coupon_code outside else:" + referred_coupon_code);
						
						if(referred_coupon_code.equalsIgnoreCase("0"))
						{
							LogLevel.DEBUG(5, new Throwable(), "referred_coupon_code inside if:" + referred_coupon_code);
						}
						else
						{
							LogLevel.DEBUG(5, new Throwable(), "referred_coupon_code inside else:" + referred_coupon_code);
							
							/***code to fetch referred order_number and expiry date of referred order_number**/
							
							String fetch_coupon_details ="select order_number,coupon_code_expiry_date from batterywaledb.generated_referal_code_details where order_coupon_code='"+referred_coupon_code+"'";
							
							LogLevel.DEBUG(5, new Throwable(), "fetch_coupon_details :" + fetch_coupon_details);

							Hashtable fetch_coupon_details_HT = qm.getRow(fetch_coupon_details);
							LogLevel.DEBUG(5, new Throwable(), "fetch_coupon_details_HT :" + fetch_coupon_details_HT);
							
							String referred_order_number=(String)fetch_coupon_details_HT.get("order_number");
							LogLevel.DEBUG(5, new Throwable(), "referred_order_number :" + referred_order_number);
							
							int refferal_amount=50;
							
							/***code to fetch referred order_number and expirry date of referred order_number**/
							
							String fetch_coupon_code_details ="select referred_coupon_code,referred_order_number,refferal_amount from batterywaledb.coupon_code_details where referred_coupon_code='"+referred_coupon_code+"'"; 
							
							Hashtable fetch_coupon_code_details_HT = qm.getRow(fetch_coupon_code_details);
							LogLevel.DEBUG(5, new Throwable(), "fetch_coupon_code_details_HT :" + fetch_coupon_code_details_HT);
								
							String Ste_ref_SqlQry;
							
							if(fetch_coupon_code_details_HT.isEmpty())
							{
								//Enters into condition if there is no record in the coupon_code_details table with the reference code entered
								 Ste_ref_SqlQry = "insert into batterywaledb.coupon_code_details(coupon_code_id,referred_coupon_code,referred_order_number,coupon_code_expiry_date,refferal_amount,coupon_applied_order_number,state,city,creation_date) values( NULL,'"+referred_coupon_code+"','"+referred_order_number+"',DATE_ADD(now(),INTERVAL 6 MONTH),'"+refferal_amount+"','"+ordernumber+"','"+state+"','"+city+"',now())";
							}
							else
							{
								//Enters into condition if there is a record in the coupon_code_details table and updates the his refferal_amount
								String existing_refferal_amount=String.valueOf(fetch_coupon_code_details_HT.get("refferal_amount"));
								
								refferal_amount=Integer.parseInt(existing_refferal_amount)+refferal_amount;
								
								LogLevel.DEBUG(5, new Throwable(), "refferal_amount added:" + refferal_amount);
								
								
								 Ste_ref_SqlQry = "update batterywaledb.coupon_code_details set refferal_amount='"+refferal_amount+"' where referred_coupon_code='"+referred_coupon_code+"'";
							}
							
							
							int resltref = qm.executeUpdate(Ste_ref_SqlQry);
							LogLevel.DEBUG(5, new Throwable(), "resltref :" + resltref);
							
								if(resltref < 0)
								{
									out.println("Failed to update ordered status!");
									strRes="Failed to update ordered status!";
								}
								else
								{
									String Ste_ref_count_SqlQry = "select referred_order_number,refferal_amount from batterywaledb.coupon_code_details where referred_coupon_code='"+referred_coupon_code+"'";
									
									LogLevel.DEBUG(5, new Throwable(), "Ste_ref_count_SqlQry :" + Ste_ref_count_SqlQry);
									
									Hashtable fetch_coupon_code_count_HT = qm.getRow(Ste_ref_count_SqlQry);
									LogLevel.DEBUG(5, new Throwable(), "fetch_coupon_code_count_HT :" + fetch_coupon_code_count_HT);
									
									String referred_coupon_order_number = String.valueOf(fetch_coupon_code_count_HT.get("referred_order_number"));
									
									//String ref_count = (String)fetch_coupon_code_count_HT.get("refcount");
									String refferal_amount_fetched = String.valueOf(fetch_coupon_code_count_HT.get("refferal_amount"));
									
									int refferal_amount_fetched_int = Integer.parseInt(refferal_amount_fetched);
									
									LogLevel.DEBUG(5, new Throwable(), "refferal_amount_fetched_int :" + refferal_amount_fetched_int);
									
									if(refferal_amount_fetched_int<300)
									{
										//Dont Send SMS
										LogLevel.DEBUG(5, new Throwable(), "Dont Send Sms");
									}
									else
									{
										LogLevel.DEBUG(5, new Throwable(), "Send SMS");
										
										String Ste_ref_mob_SqlQry = "select consumer_mobnumber from batterywaledb.battery_order_details where order_number='"+referred_coupon_order_number+"' Union select consumer_mobnumber from batterywaledb.battery_order_details where order_number='"+referred_coupon_order_number+"'";
										
										LogLevel.DEBUG(5, new Throwable(), "Ste_ref_mob_SqlQry :" + Ste_ref_mob_SqlQry);
										
										Hashtable fetch_mob_SqlQry_HT = qm.getRow(Ste_ref_mob_SqlQry);
										LogLevel.DEBUG(5, new Throwable(), "fetch_mob_SqlQry_HT :" + fetch_mob_SqlQry_HT);
										
										if(fetch_mob_SqlQry_HT.isEmpty())
										{
											Ste_ref_mob_SqlQry = "select consumer_mobnumber from batterywaledb.inverter_order_details where order_number='"+referred_coupon_order_number+"' Union select consumer_mobnumber from batterywaledb.inverter_order_details where order_number='"+referred_coupon_order_number+"'";
											LogLevel.DEBUG(5, new Throwable(), "Ste_ref_mob_SqlQry battery :" + Ste_ref_mob_SqlQry);
											fetch_mob_SqlQry_HT = qm.getRow(Ste_ref_mob_SqlQry);
											LogLevel.DEBUG(5, new Throwable(), "fetch_mob_SqlQry_HT battery :" + fetch_mob_SqlQry_HT);
										}
										
										String consumer_mobnumber_fetched = String.valueOf(fetch_mob_SqlQry_HT.get("consumer_mobnumber"));
										
										String CongratsMessage ="Congrats, you have earned "+refferal_amount_fetched+" Rs in your referral Wallet. Use code "+referred_coupon_code+" to avail free car/inverter battery health check of worth Rs.299/599 while ordering or call 9603467559 . Thanks for your support."; 
										LogLevel.DEBUG(5,new Throwable(),"CongratsMessage:"+CongratsMessage);
										
										String strSMSResponse5=sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,CongratsMessage,consumer_mobnumber_fetched);
										LogLevel.DEBUG(5,new Throwable(),"strSMSResponse5:"+strSMSResponse5);
									}

								}
						}
						
					  }
					}	
					
					/*Code to calculate and add referral amount to wallet ends here*/
					}
				}
				}
				else
				{
					LogLevel.DEBUG(5,new Throwable(),"payment_mode :"+payment_mode);
					
					if(payment_mode=="Debit Card" || payment_mode.equals("Debit Card") || payment_mode.equals("debit card")){
						Commission_Diction_Rate=0.01;
					}
					else if(payment_mode=="Credit Card" || payment_mode.equals("Credit Card") || payment_mode.equals("credit card")){
						Commission_Diction_Rate=0.02;
					}
					else{
						Commission_Diction_Rate=0.00;
					}
					
					
					Commission_Diction_Amount="price";

					String Total_Commission_Amount_SQL = "SELECT price,referred_coupon_code,erp_pre_tax, CAST(round(((price/1.28))-(erp_pre_tax/1.28))/ 2 AS SIGNED) as Total_Commission, CAST(( round(((price/1.28))-(erp_pre_tax/1.28))/ 2 )-(("+Commission_Diction_Rate+"*"+Commission_Diction_Amount+")/2) AS SIGNED) as Final_Commission FROM inverter_order_details WHERE order_number='"+ordernum+"' and order_id='"+orderid+"'";

					Hashtable Total_Commission_Amount_HT = qm.getRow(Total_Commission_Amount_SQL);
					LogLevel.DEBUG(5, new Throwable(), "Total_Commission_Amount_HT :" + Total_Commission_Amount_HT);
					LogLevel.DEBUG(5, new Throwable(), "Total_Commission_Amount_SQL :" + Total_Commission_Amount_SQL);
					if(Total_Commission_Amount_HT.isEmpty())
					{
						/*following code is for generating the random number */
					}
					else
					{
						String erp_pre_tax_tmp=(String)Total_Commission_Amount_HT.get("erp_pre_tax");
						LogLevel.DEBUG(5, new Throwable(), "erp_pre_tax_tmp :" + erp_pre_tax_tmp);
						
						if(erp_pre_tax_tmp.equals(null) || erp_pre_tax_tmp.equals("NULL") || erp_pre_tax_tmp.equals("0") || erp_pre_tax_tmp.equals("null") || erp_pre_tax_tmp == null || erp_pre_tax_tmp == "null")
						{
							String ProductActualPrice="";
							String ERP_Pre_TAX="";
							String City_Percentage="";
							
							int ProductActualPrice_2=0;
							int ERP_Pre_TAX_2=0;
							
							String Get_Product_Price_SQL ="select DISTINCT  a.inverter_actual_price,a.inverter_eretailer_price, b.inverter_city_percentage from inverter_price_details a, percentage b, inverter_order_details c WHERE c.order_number='"+ordernum+"' and c.order_id='"+orderid+"' and a.inverter_brand=c.inverter_brand and a.inverter_model=c.inverter_model and a.city=c.city and b.city=c.city ORDER BY inverter_price_id DESC LIMIT 1"; 
							LogLevel.DEBUG(5, new Throwable(), "Get_Product_Price_SQL :" + Get_Product_Price_SQL);

							Hashtable Get_Product_Price_SQL_HT = qm.getRow(Get_Product_Price_SQL);
							LogLevel.DEBUG(5, new Throwable(), "Get_Product_Price_SQL_HT :" + Get_Product_Price_SQL_HT);

							if(Get_Product_Price_SQL_HT.isEmpty())
							{
								strRes="Session Expired or Server Down. Please regenerate your order.";
								return strRes;
							}
							else
							{
								ProductActualPrice_2=(Integer)Get_Product_Price_SQL_HT.get("inverter_actual_price");
								LogLevel.DEBUG(5, new Throwable(), "ProductActualPrice :" + ProductActualPrice_2);
								
								ERP_Pre_TAX_2 =(Integer)Get_Product_Price_SQL_HT.get("inverter_eretailer_price");
								LogLevel.DEBUG(5, new Throwable(), "ERP_Pre_TAX :" + ERP_Pre_TAX_2);
								
								ProductActualPrice = Integer.toString(ProductActualPrice_2);
								ERP_Pre_TAX = Integer.toString(ERP_Pre_TAX_2);
								
								City_Percentage=(String)Get_Product_Price_SQL_HT.get("inverter_city_percentage");
								LogLevel.DEBUG(5, new Throwable(), "City_Percentage :" + City_Percentage);
							}
							
							strSqlQry = "UPDATE inverter_order_details SET erp_pre_tax = '"+ERP_Pre_TAX+"', MRP_Price = '"+ProductActualPrice+"', city_percentage = '"+City_Percentage+"'WHERE  order_number='"+ordernum+"' and order_id='"+orderid+"' ";
							LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
							
							int reslt = qm.executeUpdate(strSqlQry);
							if(reslt <0)
							{
								out.println("Failed to update ordered status!");
								strRes="Failed to update ordered status!";
							}
							else
							{
								out.println("Please Try again!");
								strRes="Please Try again!- Order Values updated";
							}
							return strRes;
						}
												
						long Total_Commission_Amount2=(Long)Total_Commission_Amount_HT.get("Total_Commission");
						LogLevel.DEBUG(5, new Throwable(), "Total_Commission_Amount2 :" + Total_Commission_Amount2);
						
						Total_Commission_Amount = Long.toString(Total_Commission_Amount2);
						LogLevel.DEBUG(5, new Throwable(), "Total_Commission_Amount :" + Total_Commission_Amount);
						
						long Final_Commission2=(Long)Total_Commission_Amount_HT.get("Final_Commission");
						LogLevel.DEBUG(5, new Throwable(), "Final_Commission2 :" + Final_Commission2);
						
						Final_Commission_Amount = Long.toString(Final_Commission2);
						LogLevel.DEBUG(5, new Throwable(), "Final_Commission_Amount :" + Final_Commission_Amount);
						
						Commission_WithOut_Old_Battery=(String)Total_Commission_Amount_HT.get("price");
						LogLevel.DEBUG(5, new Throwable(), "Commission_WithOut_Old_Battery :" + Commission_WithOut_Old_Battery);
						
						Commission_With_Old_Battery=(String)Total_Commission_Amount_HT.get("witholdbatprice");
						LogLevel.DEBUG(5, new Throwable(), "Commission_With_Old_Battery :" + Commission_With_Old_Battery);
					}
					
					
						if(payment_mode.equals("Online Payment After Fitment"))
						{
	
						String requestUrl="";
						String payment_link_url="";
						String CheckPaymentSql ="select order_id,status,payment_url from online_transaction_details where order_id='"+ordernumber+"'";
						
						LogLevel.DEBUG(5, new Throwable(), "CheckPaymentSql :" + CheckPaymentSql);	
						Hashtable CheckPaymentSql_HT = qm.getRow(CheckPaymentSql);
						LogLevel.DEBUG(5, new Throwable(), "CheckPaymentSql_HT :" + CheckPaymentSql_HT);

							if(CheckPaymentSql_HT.isEmpty())
							{
								LogLevel.DEBUG(5, new Throwable(), "Create_Payment_ Inside");
								
								//*********************################## Online Payment Request Generation Code ###### START #####################*****************//
								OnlinePayment Create_Payment_Link = new OnlinePayment(qm);
								LogLevel.DEBUG(5, new Throwable(), "Create_Payment_Link :" + Create_Payment_Link);
								String Create_Payment_Link_Resp=  Create_Payment_Link.getPaymentLink(req,res,session,consumername,consumeremailid,consumermobilenumber,customerpaid,ordernumber);
								LogLevel.DEBUG(5, new Throwable(), "Create_Payment_Link_Resp :" + Create_Payment_Link_Resp);
								
								//*********************################## Online Payment Request Generation Code ###### END #####################*****************//
						
								if(Create_Payment_Link_Resp.contains("ERROR | -"))
								{
									return Create_Payment_Link_Resp;
								}
								else
								{
									
									LogLevel.DEBUG(5, new Throwable(), "Create_Payment_Link_Resp :" + Create_Payment_Link_Resp);
									
									requestUrl="https://api-ssl.bitly.com/v3/shorten?access_token=accc1abefa0f57d73bad49cd2b80dbefe15cf10e&longUrl="+Create_Payment_Link_Resp;
									LogLevel.DEBUG(5, new Throwable(), "requestUrl :" + requestUrl);

								}  /********************* Payment Link ends***********/
								
								URL obj = new URL(requestUrl);
								HttpURLConnection con = (HttpURLConnection) obj.openConnection();
								con.setRequestMethod("GET");
								int responseCode = con.getResponseCode();
								LogLevel.DEBUG(5, new Throwable(), "responseCode" + responseCode);
								if (responseCode == HttpURLConnection.HTTP_OK) { // success
									BufferedReader in = new BufferedReader(new InputStreamReader(
									con.getInputStream()));
									String inputLine;
									StringBuffer response = new StringBuffer();
									while ((inputLine = in.readLine()) != null) {
									LogLevel.DEBUG(5, new Throwable(), "inputLine" + inputLine);
									response.append(inputLine);
									
								}
								LogLevel.DEBUG(5, new Throwable(), "string response :" + response);
								JSONObject jsonObject = new JSONObject(response.toString());
								LogLevel.DEBUG(5, new Throwable(), "string status_txt :" + jsonObject.get("status_txt"));
								LogLevel.DEBUG(5, new Throwable(), "string status_txt :" + jsonObject.get("data"));
								JSONObject jsonarray = new JSONObject(jsonObject.get("data").toString());
								LogLevel.DEBUG(5, new Throwable(), "json jsonarray" + jsonarray);
								String surl = jsonarray.getString("url");
								LogLevel.DEBUG(5, new Throwable(), "shorturl" + surl);
								payment_link_url = surl;
								LogLevel.DEBUG(5, new Throwable(), "shorturl" + surl);
								String lurl = jsonarray.getString("long_url");
								LogLevel.DEBUG(5, new Throwable(), "long_url" + lurl);
							
								String SuccessMessage ="Dear Customer, Please make your payment for the battery order with order number "+ordernumber+" in BookBattery by clicking on the link "+payment_link_url+" ."; 
								LogLevel.DEBUG(5,new Throwable(),"SuccessMessage:"+SuccessMessage);
								
								String strSMSResponse_PL=sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,SuccessMessage,consumermobilenumber);
								LogLevel.DEBUG(5,new Throwable(),"strSMSResponse_PL:"+strSMSResponse_PL);
								
								String[] order_coupon_code_array=ordernumber.split("ORDI");
					
								//LogLevel.DEBUG(5,new Throwable(),"order_referred_code[0]:"+order_coupon_code_array[0]);
								LogLevel.DEBUG(5,new Throwable(),"order_referred_code[1]:"+order_coupon_code_array[1]);
								
								String order_coupon_code="REF"+order_coupon_code_array[1];
								LogLevel.DEBUG(5,new Throwable(),"order_coupon_code:"+order_coupon_code);
								
								String referred_coupon_code =(String)Total_Commission_Amount_HT.get("referred_coupon_code");
								LogLevel.DEBUG(5, new Throwable(), "referred_coupon_code :" + referred_coupon_code);
								

								strSqlQry= "update inverter_order_details set order_status='Customer Contacted',order_reasons='Payment Link Generated',payment_mode='"+payment_mode+"', order_agent_comments='"+order_agent_comments+"',total_commission_amount='"+Total_Commission_Amount+"',inverter_commission_amount='"+Final_Commission_Amount+"',agent_name='"+stroperatorName+"',updated_date=now(),order_coupon_code='"+order_coupon_code+"', collected_party='"+paymentcollected+"',installed_date=now(),coupon_code_expiry_date=DATE_ADD(now(),INTERVAL 6 MONTH),postpone_date='0000-00-00' WHERE  order_number='"+ordernum+"' and order_id='"+orderid+"'";
								LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
								
							
							} 
							else 
							{
								strRes="Please Try again!- Order Values updated";
								LogLevel.DEBUG(5, new Throwable(), "GET request not worked");
							}
									 
							} 
							else
							{
								
								String[] order_coupon_code_array=ordernumber.split("ORDI");
					
								//LogLevel.DEBUG(5,new Throwable(),"order_referred_code[0]:"+order_coupon_code_array[0]);
								LogLevel.DEBUG(5,new Throwable(),"order_referred_code[1]:"+order_coupon_code_array[1]);
								
								String order_coupon_code="REF"+order_coupon_code_array[1];
								LogLevel.DEBUG(5,new Throwable(),"order_coupon_code:"+order_coupon_code);
								
								String referred_coupon_code =(String)Total_Commission_Amount_HT.get("referred_coupon_code");
								LogLevel.DEBUG(5, new Throwable(), "referred_coupon_code :" + referred_coupon_code);
								 
								 String payment_orderid=(String)CheckPaymentSql_HT.get("order_id");
								 String payment_status=(String)CheckPaymentSql_HT.get("status");
								 requestUrl=(String)CheckPaymentSql_HT.get("payment_url");
								 LogLevel.DEBUG(5, new Throwable(), "payment_orderid" + payment_orderid);
								 LogLevel.DEBUG(5, new Throwable(), "payment_status" + payment_status);
								 LogLevel.DEBUG(5, new Throwable(), "payment_url" + requestUrl);
										
								 if(payment_status=="completed" || payment_status.equals("completed")){
									 
									LogLevel.DEBUG(5, new Throwable(), "Inside" + payment_status);
									strSqlQry= "update inverter_order_details set order_status='"+orderstatus+"',order_reasons='installed',payment_mode='Online',payment_mode_type='"+payment_mode+"',payment_installation='Online Payment After Fitment', order_agent_comments='"+order_agent_comments+"',total_commission_amount='"+Total_Commission_Amount+"',inverter_commission_amount='"+Final_Commission_Amount+"',agent_name='"+stroperatorName+"',updated_date=now(),order_coupon_code='"+order_coupon_code+"', collected_party='"+paymentcollected+"',installed_date=now(),coupon_code_expiry_date=DATE_ADD(now(),INTERVAL 6 MONTH),postpone_date='0000-00-00' WHERE  order_number='"+ordernum+"' and order_id='"+orderid+"'";
									LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
										
								 } 
								 else 
								 {
									strSqlQry= "update inverter_order_details set order_status='"+orderstatus+"',order_reasons='Payment Pending',payment_mode='"+payment_mode+"', order_agent_comments='"+order_agent_comments+"',total_commission_amount='"+Total_Commission_Amount+"',inverter_commission_amount='"+Final_Commission_Amount+"',agent_name='"+stroperatorName+"',updated_date=now(),order_coupon_code='"+order_coupon_code+"', collected_party='"+paymentcollected+"',installed_date=now(),coupon_code_expiry_date=DATE_ADD(now(),INTERVAL 6 MONTH),postpone_date='0000-00-00' WHERE  order_number='"+ordernum+"' and order_id='"+orderid+"'";
									LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
								 }
								 
							}  //********************* Payment Query HT ends***********/

					}
					
					else
					{
					/*Code to calculate and add referral amount to wallet starts here*/
					
					String[] order_coupon_code_array=ordernumber.split("ORDI");
					
					//LogLevel.DEBUG(5,new Throwable(),"order_referred_code[0]:"+order_coupon_code_array[0]);
					LogLevel.DEBUG(5,new Throwable(),"order_referred_code[1]:"+order_coupon_code_array[1]);
					
					String order_coupon_code="REF"+order_coupon_code_array[1];
					LogLevel.DEBUG(5,new Throwable(),"order_coupon_code:"+order_coupon_code);
					
					String referred_coupon_code =(String)Total_Commission_Amount_HT.get("referred_coupon_code");
					LogLevel.DEBUG(5, new Throwable(), "referred_coupon_code :" + referred_coupon_code);					
					strSqlQry= "update inverter_order_details set order_status='"+orderstatus+"',order_reasons='"+orderreason+"',payment_mode='"+payment_mode+"',payment_mode_type='"+payment_mode+"', order_agent_comments='"+order_agent_comments+"',total_commission_amount='"+Total_Commission_Amount+"',inverter_commission_amount='"+Final_Commission_Amount+"',agent_name='"+stroperatorName+"',updated_date=now(),order_coupon_code='"+order_coupon_code+"',collected_party='"+paymentcollected+"',installed_date=now(),coupon_code_expiry_date=DATE_ADD(now(),INTERVAL 6 MONTH),postpone_date='0000-00-00' WHERE  order_number='"+ordernum+"' and order_id='"+orderid+"'";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
					
					reslt_new = qm.executeUpdate(strSqlQry);
					
					if(reslt_new <0)
					{
						out.println("Failed to update ordered status!");
					    strRes="Failed to update ordered status!";
					}
					else
					{
						
						/*added to insert into generated_referal_code_details table to track how many coupon codes are generated and for validating*/
						String product_type="Inverter";
						
						String ins_gen_SqlQry = "insert into batterywaledb.generated_referal_code_details(order_coupon_code_id,order_coupon_code,order_number,product_type,referred_coupon_code,domain_name,coupon_code_expiry_date,creation_date) values( NULL,'"+order_coupon_code+"','"+ordernumber+"','"+product_type+"','"+referred_coupon_code+"','BookBattery',DATE_ADD(now(),INTERVAL 6 MONTH),now())";
						
						int resltgen_SqlQry = qm.executeUpdate(ins_gen_SqlQry);
						
						if(resltgen_SqlQry < 0)
						{
							out.println("Failed to generated_referal_code_details!");
							strRes="Failed to generated_referal_code_details!";
						}
						else
						{
						
						LogLevel.DEBUG(5, new Throwable(), "referred_coupon_code outside else:" + referred_coupon_code);
						
						if(referred_coupon_code.equalsIgnoreCase("0"))
						{
							LogLevel.DEBUG(5, new Throwable(), "referred_coupon_code inside if:" + referred_coupon_code);
						}
						else
						{
							LogLevel.DEBUG(5, new Throwable(), "referred_coupon_code inside else:" + referred_coupon_code);
							
							String fetch_coupon_details ="select order_number,coupon_code_expiry_date from batterywaledb.generated_referal_code_details where order_coupon_code='"+referred_coupon_code+"'";
							
							
							LogLevel.DEBUG(5, new Throwable(), "fetch_coupon_details :" + fetch_coupon_details);

							Hashtable fetch_coupon_details_HT = qm.getRow(fetch_coupon_details);
							LogLevel.DEBUG(5, new Throwable(), "fetch_coupon_details_HT :" + fetch_coupon_details_HT);
							
							String referred_order_number=(String)fetch_coupon_details_HT.get("order_number");
							LogLevel.DEBUG(5, new Throwable(), "referred_order_number :" + referred_order_number);
							
							int refferal_amount=50;
							
							String fetch_coupon_code_details ="select referred_coupon_code,referred_order_number,refferal_amount from batterywaledb.coupon_code_details where referred_coupon_code='"+referred_coupon_code+"'"; 
							
							Hashtable fetch_coupon_code_details_HT = qm.getRow(fetch_coupon_code_details);
							LogLevel.DEBUG(5, new Throwable(), "fetch_coupon_code_details_HT :" + fetch_coupon_code_details_HT);
								
							String Ste_ref_SqlQry;
							
							if(fetch_coupon_code_details_HT.isEmpty())
							{
								//Enters into condition if there is no record in the coupon_code_details table with the reference code entered
								 Ste_ref_SqlQry = "insert into batterywaledb.coupon_code_details(coupon_code_id,referred_coupon_code,referred_order_number,coupon_code_expiry_date,refferal_amount,coupon_applied_order_number,state,city,creation_date) values( NULL,'"+referred_coupon_code+"','"+referred_order_number+"',DATE_ADD(now(),INTERVAL 6 MONTH),'"+refferal_amount+"','"+ordernumber+"','"+state+"','"+city+"',now())";
							}
							else
							{
								//Enters into condition if there is a record in the coupon_code_details table and updates the his refferal_amount
								String existing_refferal_amount=String.valueOf(fetch_coupon_code_details_HT.get("refferal_amount"));
								
								refferal_amount=Integer.parseInt(existing_refferal_amount)+refferal_amount;
								
								LogLevel.DEBUG(5, new Throwable(), "refferal_amount added:" + refferal_amount);
								
								
								 Ste_ref_SqlQry = "update batterywaledb.coupon_code_details set refferal_amount='"+refferal_amount+"' where referred_coupon_code='"+referred_coupon_code+"'";
							}
							
							
							int resltref = qm.executeUpdate(Ste_ref_SqlQry);
							LogLevel.DEBUG(5, new Throwable(), "resltref :" + resltref);
							
								if(resltref < 0)
								{
									out.println("Failed to update ordered status!");
									strRes="Failed to update ordered status!";
								}
								else
								{
									String Ste_ref_count_SqlQry = "select referred_order_number,refferal_amount from batterywaledb.coupon_code_details where referred_coupon_code='"+referred_coupon_code+"'";
									
									LogLevel.DEBUG(5, new Throwable(), "Ste_ref_count_SqlQry :" + Ste_ref_count_SqlQry);
									
									Hashtable fetch_coupon_code_count_HT = qm.getRow(Ste_ref_count_SqlQry);
									LogLevel.DEBUG(5, new Throwable(), "fetch_coupon_code_count_HT :" + fetch_coupon_code_count_HT);
									
									String referred_coupon_order_number = String.valueOf(fetch_coupon_code_count_HT.get("referred_order_number"));
									
									//String ref_count = (String)fetch_coupon_code_count_HT.get("refcount");
									String refferal_amount_fetched = String.valueOf(fetch_coupon_code_count_HT.get("refferal_amount"));
									
									int refferal_amount_fetched_int = Integer.parseInt(refferal_amount_fetched);
									
									LogLevel.DEBUG(5, new Throwable(), "refferal_amount_fetched_int :" + refferal_amount_fetched_int);
									
									if(refferal_amount_fetched_int<300)
									{
										//Dont Send SMS
										LogLevel.DEBUG(5, new Throwable(), "Dont Send Sms");
									}
									else
									{
										LogLevel.DEBUG(5, new Throwable(), "Send SMS");
										String Ste_ref_mob_SqlQry = "select consumer_mobnumber from batterywaledb.battery_order_details where order_number='"+referred_coupon_order_number+"' Union select consumer_mobnumber from batterywaledb.battery_order_details where order_number='"+referred_coupon_order_number+"'";
										
										LogLevel.DEBUG(5, new Throwable(), "Ste_ref_mob_SqlQry :" + Ste_ref_mob_SqlQry);
										
										Hashtable fetch_mob_SqlQry_HT = qm.getRow(Ste_ref_mob_SqlQry);
										LogLevel.DEBUG(5, new Throwable(), "fetch_mob_SqlQry_HT :" + fetch_mob_SqlQry_HT);
										
										if(fetch_mob_SqlQry_HT.isEmpty())
										{
											Ste_ref_mob_SqlQry = "select consumer_mobnumber from batterywaledb.inverter_order_details where order_number='"+referred_coupon_order_number+"' Union select consumer_mobnumber from batterywaledb.inverter_order_details where order_number='"+referred_coupon_order_number+"'";
											LogLevel.DEBUG(5, new Throwable(), "Ste_ref_mob_SqlQry battery :" + Ste_ref_mob_SqlQry);
											fetch_mob_SqlQry_HT = qm.getRow(Ste_ref_mob_SqlQry);
											LogLevel.DEBUG(5, new Throwable(), "fetch_mob_SqlQry_HT battery :" + fetch_mob_SqlQry_HT);
										}
										
										String consumer_mobnumber_fetched = String.valueOf(fetch_mob_SqlQry_HT.get("consumer_mobnumber"));
										
										String CongratsMessage ="Congrats, you have earned "+refferal_amount_fetched+" Rs in your referral Wallet. Use code "+referred_coupon_code+" to avail free car/inverter battery health check of worth Rs.299/599 while ordering or call 9603467559 . Thanks for your support."; 
										LogLevel.DEBUG(5,new Throwable(),"CongratsMessage:"+CongratsMessage);
										
										String strSMSResponse5=sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,CongratsMessage,consumer_mobnumber_fetched);
										LogLevel.DEBUG(5,new Throwable(),"strSMSResponse5:"+strSMSResponse5);
									}

								}
						}
						
						}
					}	
					
						/*Code to calculate and add referral amount to wallet ends here*/
				
					}
				}
				
				if(stroperatorName.equals("null") || stroperatorName.equals("NULL") || stroperatorName.equals(null) || stroperatorName == "null" || stroperatorName == "NULL" || stroperatorName == null || stroperatorName.equals("0") || stroperatorName == "0" || stroperatorName.equals(" ") || stroperatorName == "" || stroperatorName.equals("undefined") || stroperatorName == "undefined")
				{

					LogLevel.DEBUG(1,new Throwable(),"Operator Name is Empty or null");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#00dd00' class='vrb10'></font> ");
					out.println("Failed to update ordered status!");
				}
				else
				{
						
					LogLevel.DEBUG(5,new Throwable(),"else conditions:");
					
					//strSqlQry= "update inverter_order_details set order_status='"+orderstatus+"',order_reasons='"+orderreason+"',payment_mode='"+payment_mode+"', order_agent_comments='"+order_agent_comments+"',total_commission_amount='"+Total_Commission_Amount+"',inverter_commission_amount='"+Final_Commission_Amount+"',agent_name='"+stroperatorName+"',updated_date=now(),installed_date=now(),postpone_date='0000-00-00' WHERE  order_number='"+ordernum+"' and order_id='"+orderid+"'";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
				}
			}
			else if(orderstatus.equals("Customer Contacted") && orderreason.equals("Regenerated to Another Retailer"))
			{
				
					
					String[] retailertoorderarray=retailertoorder.split(",");

					String retailerid=retailertoorderarray[0];
					String retailername1=retailertoorderarray[1];
					String retailerstate=retailertoorderarray[2];

					retailerstate=retailerstate.trim().replaceAll(" ", "_"); 
					LogLevel.DEBUG(5, new Throwable(), "retailerstate :" + retailerstate);

					String StrSqlQrydet = "select retailer_id,retailer_name,mobile_number,email_id,address1,mobile_numberother,invoice_flag from "+retailerstate+"_retailers where retailer_id='"+retailerid+"'";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQrydet :" + StrSqlQrydet);

					Hashtable htretailerdetls = qm.getRow(StrSqlQrydet); 
					String retailer_id=String.valueOf(htretailerdetls.get("retailer_id"));
					String strretmobnum=String.valueOf(htretailerdetls.get("mobile_number"));
					String strretname=(String)htretailerdetls.get("retailer_name");
					String strretemail=(String)htretailerdetls.get("email_id");
					String straddress1=(String)htretailerdetls.get("address1");
					String strretothermobnum=String.valueOf(htretailerdetls.get("mobile_numberother"));
					String strinvoiceflag=String.valueOf(htretailerdetls.get("invoice_flag"));

					strSqlQry= "update inverter_order_details set order_status='"+orderstatus+"',order_reasons='"+orderreason+"',order_agent_comments='"+order_agent_comments+"',agent_name='"+stroperatorName+"',updated_date=now(),retailer_name='"+strretname+"',retailer_mobile_number='"+strretmobnum+"',retailer_emailid='"+strretemail+"',postpone_date='0000-00-00' WHERE  order_number='"+ordernum+"' and order_id='"+orderid+"'";

					reslt_new = qm.executeUpdate(strSqlQry);

				if(reslt_new <0)
				{
					out.println("Failed to update ordered status!");
					strRes="Failed to update ordered status!";
				}
			else
				{
					
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );

					/* code to send SMS and Email retailers details to consumer */ 

					String ThankUSMSMsg ="Thanks for choosing BookBattery.com .Your order has been modified and it has been processed to "+strretname+" Mobile No :"+strretmobnum+" .You will shortly receive a call.For any kind of Assistance please call to 9603467559. ";

			String strSMSResponse=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUSMSMsg, consumermobilenumber);
			LogLevel.DEBUG(5, new Throwable(), "strSMSResponse :" + strSMSResponse);			

			String ThankUMsg="Your BookBattery Inverter Ord Ref No: "+ordernum+" Inverter Model: "+invertermodel+" Inverter Capacity: "+invertercapacity+" Price: Rs "+price+"  has been modified.It has been processed to "+strretname+" with Mob No-"+strretmobnum+" and address "+straddress1+" will fullfill your order.For any kind of Assistance please call to 9603467559.";

			String strsub="Dear "+consumername+",\r\n\r\n"+""+ThankUMsg+"";
			String strThanks="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
			MailTrans sendverifycode=new MailTrans();
			sendverifycode.setStrSmtpHost(strdomainname);
			sendverifycode.setStrFrom(FromEmailId);
			sendverifycode.setStrTo(consumeremailid);
			sendverifycode.setStrSubject("BookBattery Inverter Details.");
			String activateLink = strsub+"\r\n\r\n"+strThanks+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
			LogLevel.DEBUG(5, new Throwable(), "activateLink :" + activateLink);
			sendverifycode.setStrText(activateLink);
			Thread mt=new MailThread(sendverifycode,"");
			mt.start();


			/* code to send SMS and Email consumer details to retailers */ 

			/*String CustomerAddress="";

			if(addrs1.equals("") && addrs2.equals(""))
			{
				CustomerAddress="";

			}
			else
			{
				CustomerAddress=", Address: "+addrs1+","+addrs2+"";

			}*/
						
			//######## Send SMS for ORDER
			Order_SMS Send_Order_SMS_Checkout = new Order_SMS(qm);
			String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,ordernum,"Inverter","No","Yes","Yes");
			LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
			//######## Send SMS for ORDER
					
			//######## Send Mail for ORDER
			Order_SMS Send_Order_MAIL_Checkout = new Order_SMS(qm);
			String MAIL_Report=Send_Order_MAIL_Checkout.Send_Order_Mail(req,res,session,ordernum,"Inverter","No","Yes","Yes");
			LogLevel.DEBUG(5, new Throwable(), "MAIL_Report :" + MAIL_Report);
			//######## Send Mail for ORDER	


			}

		}
			
			else if(orderstatus.equals("confirmed") && orderreason.equals("Confirmed Order To Retailer"))
			{
				
					String ThankUSMSMsg="";
					
					String[] retailertoorderarray=retailertoorder.split(",");

					String retailerid=retailertoorderarray[0];
					String retailername2=retailertoorderarray[1];
					String retailerstate=retailertoorderarray[2];

					retailerstate=retailerstate.trim().replaceAll(" ", "_"); 
					LogLevel.DEBUG(5, new Throwable(), "retailerstate :" + retailerstate);


					String StrSqlQrydet = "select retailer_id,retailer_name,mobile_number,email_id,zipcode,address1,mobile_numberother,invoice_flag from "+retailerstate+"_retailers where retailer_id='"+retailerid+"'";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQrydet :" + StrSqlQrydet);

					Hashtable htretailerdetls = qm.getRow(StrSqlQrydet); 
					String retailer_id=String.valueOf(htretailerdetls.get("retailer_id"));
					String strretmobnum=String.valueOf(htretailerdetls.get("mobile_number"));
					String strretname=(String)htretailerdetls.get("retailer_name");
					String strretemail=(String)htretailerdetls.get("email_id");
					String straddress1=(String)htretailerdetls.get("address1");
					String strretothermobnum=String.valueOf(htretailerdetls.get("mobile_numberother"));
					String strinvoiceflag=String.valueOf(htretailerdetls.get("invoice_flag"));
					String zipcode=String.valueOf(htretailerdetls.get("zipcode"));

	
					String strSqlerpQry="";
					
					if(strretname.contains("Amaron-Pitstop"))
					{
						strSqlerpQry="SELECT inverter_eretailer_price FROM franchisee_inverter_price_details WHERE inverter_model='"+invertermodel+"' and city='"+city+"'";
					}
					else
					{
						strSqlerpQry="SELECT inverter_eretailer_price FROM inverter_price_details WHERE inverter_model='"+invertermodel+"' and city='"+city+"'";
					}
					
					
					
					LogLevel.DEBUG(5,new Throwable(),"strSqlerpQry :"+strSqlerpQry);

					Hashtable ht_erp_pre_tax = qm.getRow(strSqlerpQry);
					String inverter_eretailer_price=String.valueOf(ht_erp_pre_tax.get("inverter_eretailer_price"));
					LogLevel.DEBUG(5,new Throwable(),"inverter_eretailer_price :"+inverter_eretailer_price);
					
					
					/*Code Added to assign the Service engineer for an order*/
					String strSqlserv_engnr_Qry="SELECT a.service_engineer_id,a.username,a.pincode,b.mobilenumber FROM service_engineer_mappings a, service_engineers b WHERE a.service_engineer_id=b.register_id and a.pincode='"+zipcode+"'";
					
					Hashtable ht_strSqlserv_engnr_Qry = qm.getRow(strSqlserv_engnr_Qry);
                    
                    String service_engineer_id="NULL";
                    String service_engineer_name="NULL";
                    String service_engineer_mobile="NULL";
                    String service_engineer_pincode="NULL";
                    if(ht_strSqlserv_engnr_Qry.isEmpty())
                    {
                        
                        
                    }
                    else
                    {
                         service_engineer_id=String.valueOf(ht_strSqlserv_engnr_Qry.get("service_engineer_id"));
                         service_engineer_name=String.valueOf(ht_strSqlserv_engnr_Qry.get("username"));
                         service_engineer_mobile=String.valueOf(ht_strSqlserv_engnr_Qry.get("mobilenumber"));
                         service_engineer_pincode=String.valueOf(ht_strSqlserv_engnr_Qry.get("pincode"));
                         LogLevel.DEBUG(5,new Throwable(),"service_engineer_id :"+service_engineer_id);  
                    }
					
					strSqlQry= "update inverter_order_details set order_status='"+orderstatus+"',order_reasons='"+orderreason+"',erp_pre_tax='"+inverter_eretailer_price+"',order_agent_comments='"+order_agent_comments+"',agent_name='"+stroperatorName+"',updated_date=now(),retailer_name='"+strretname+"',retailer_mobile_number='"+strretmobnum+"',service_engineer_name='"+service_engineer_name+"',service_engineer_pincode='"+service_engineer_pincode+"',service_engineer_mobile='"+service_engineer_mobile+"',retailer_emailid='"+strretemail+"',postpone_date='0000-00-00', area='"+cusarea+"',confirm_by='Operator' WHERE  order_number='"+ordernumber+"' and order_id='"+orderid+"'";
					
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry_Update:"+strSqlQry );
					reslt_new = qm.executeUpdate(strSqlQry);
					
					if(reslt_new <0)
					{
						out.println("Failed to update ordered status!");
						strRes="Failed to update ordered status!";
					}
					else
						{
							
							//######## Send SMS for ORDER
							Order_SMS Send_Order_SMS_Checkout = new Order_SMS(qm);
							String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,ordernumber,"Inverter","Confirm","Yes","Yes");
							LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
							LogLevel.DEBUG(5, new Throwable(), "SMS_Report Newwwwww :" + SMS_Report);
							//######## Send SMS for ORDER
							
							//Order_SMS Send_Order_SMS_Checkout_confirm = new Order_SMS(qm);
							//String SMS_Report_confirm=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,ordernumber,"Inverter","Confirm","No","No");
							
							//LogLevel.DEBUG(5, new Throwable(), "SMS_Report_confirm :" + SMS_Report_confirm);
							
							
							//######## Send Mail for ORDER
							Order_SMS Send_Order_MAIL_Checkout = new Order_SMS(qm);
							String MAIL_Report=Send_Order_MAIL_Checkout.Send_Order_Mail(req,res,session,ordernumber,"Inverter","Yes","Yes","Yes");
							LogLevel.DEBUG(5, new Throwable(), "MAIL_Report :" + MAIL_Report);
							//######## Send Mail for ORDER
						}
			}

			else if(orderstatus.equals("Customer Contacted") && orderreason.equals("Modify Battery Details"))
			{
				
				/* code to send SMS and Email retailer details to consumers */ 

				//String ThankUSMSMsg ="Thanks for choosing BookBattery.com .Your order has been modified and it has been processed to "+retailername+" Mobile No :"+retailermobilenumber+" .You will shortly receive a call.For any kind of Assistance please call to 9603467559. ";
			  

				//String strSMSResponse=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUSMSMsg, consumermobilenumber);
				//LogLevel.DEBUG(5, new Throwable(), "strSMSResponse :" + strSMSResponse);			

				/*String ThankUMsg="Your BookBattery Inverter Ord Ref No: "+ordernum+" Inverter Model: "+inmodel+" Inverter Capacity: "+incap+" Price: Rs "+inprice+"  has been modified.It has been processed to "+retailername+" with Mob No-"+retailermobilenumber+"  will fullfill your order.For any kind of Assistance please call to 9603467559.";

				String strsub="Dear "+consumername+",\r\n\r\n"+""+ThankUMsg+"";
				String strThanks="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
				MailTrans sendverifycode=new MailTrans();
				sendverifycode.setStrSmtpHost(strdomainname);
				sendverifycode.setStrFrom(FromEmailId);
				sendverifycode.setStrTo(consumeremailid);
				sendverifycode.setStrSubject("BookBattery Inverter Details.");
				String activateLink = strsub+"\r\n\r\n"+strThanks+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
				LogLevel.DEBUG(5, new Throwable(), "activateLink :" + activateLink);
				sendverifycode.setStrText(activateLink);
				Thread mt=new MailThread(sendverifycode,"");
				mt.start();
*/
			/* code to send SMS and Email consumer details to retailers */ 
			/*if(retailername.contains("Amaron-Pitstop"))
			{
				String ThankUMsg1="BookBattery Consumer "+consumername+" with Mob no-"+consumermobilenumber+"  and Order Ref Number "+ordernum+" ordered for "+inbrand+" >> "+inmodel+" >> "+incap+" at price: Rs "+inprice+" .  Please re-confirm with the customer and deliver it. For any kind of Assistance please call to 9603467559.";

				LogLevel.DEBUG(5, new Throwable(), "ThankUMsg1 :" + ThankUMsg1);				

				String ThanksmsMsg=" "+consumername+" Mob No: "+consumermobilenumber+" and Ord Ref No: "+ordernum+" ordered "+inbrand+" >> "+inmodel+" >> "+incap+" at price: Rs. "+inprice+" Please install";

				String strSMSResponse1=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThanksmsMsg, retailermobilenumber);
				LogLevel.DEBUG(5, new Throwable(), "strSMSResponse1 :" + strSMSResponse1);

				String strsub1="Dear "+retailername+",\r\n\r\n"+""+ThankUMsg1+"";
				String strThanks1="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
				MailTrans retdetails=new MailTrans();
				retdetails.setStrSmtpHost(strdomainname);
				retdetails.setStrFrom(FromEmailId);
				retdetails.setStrTo(retaileremail);
				retdetails.setStrSubject("BookBattery Inverter Details.");
				String activateLink1 = strsub1+"\r\n\r\n"+strThanks1+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
				LogLevel.DEBUG(5, new Throwable(), "activateLink1 :" + activateLink1);
				retdetails.setStrText(activateLink1);
				Thread mt1=new MailThread(retdetails,"");
				mt1.start();

			}
			else
				{

				String ThankUMsg1="BookBattery Consumer "+consumername+" with Mob no-"+consumermobilenumber+"  and Order Ref Number "+ordernum+" ordered for "+inbrand+" >> "+inmodel+" >> "+incap+" at price: Rs "+inprice+" .  Please re-confirm with the customer and deliver it. For any kind of Assistance please call to 9603467559.";

				LogLevel.DEBUG(5, new Throwable(), "ThankUMsg1 :" + ThankUMsg1);				

				String ThanksmsMsg=" "+consumername+" Mob No: "+consumermobilenumber+" and Ord Ref No: "+ordernum+" ordered "+inbrand+" >> "+inmodel+" >> "+incap+" at price: Rs. "+inprice+" Please install";

				String strSMSResponse1=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThanksmsMsg, retailermobilenumber);
				LogLevel.DEBUG(5, new Throwable(), "strSMSResponse1 :" + strSMSResponse1);

				String strsub1="Dear "+retailername+",\r\n\r\n"+""+ThankUMsg1+"";
				String strThanks1="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
				MailTrans retdetails=new MailTrans();
				retdetails.setStrSmtpHost(strdomainname);
				retdetails.setStrFrom(FromEmailId);
				retdetails.setStrTo(retaileremail);
				retdetails.setStrSubject("BookBattery Inverter Details.");
				String activateLink1 = strsub1+"\r\n\r\n"+strThanks1+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
				LogLevel.DEBUG(5, new Throwable(), "activateLink1 :" + activateLink1);
				retdetails.setStrText(activateLink1);
				Thread mt1=new MailThread(retdetails,"");
				mt1.start();
				}
				*/
				String User_City="";
				String Get_Erp_For_Order_City ="SELECT city FROM inverter_order_details  WHERE  order_number='"+ordernumber+"' and order_id='"+orderid+"'";
				LogLevel.DEBUG(5, new Throwable(), "Get_Erp_For_Order_City :" + Get_Erp_For_Order_City);
				
				Hashtable Get_Erp_For_Order_City_HT = qm.getRow(Get_Erp_For_Order_City);
				LogLevel.DEBUG(5, new Throwable(), "Get_Erp_For_Order_City_HT :" + Get_Erp_For_Order_City_HT);

				if(Get_Erp_For_Order_City_HT.isEmpty())
				{
					strRes="Session Expired or Server Down. Please regenerate your order.";
					return strRes;
				}
				else
				{
					User_City=(String)Get_Erp_For_Order_City_HT.get("city");
					LogLevel.DEBUG(5, new Throwable(), "User_City :" + User_City);
				}
				
				strSqlQry= "update inverter_order_details set order_status='"+orderstatus+"',order_reasons='"+orderreason+"',order_agent_comments='"+order_agent_comments+"',agent_name='"+stroperatorName+"',updated_date=now(),inverter_brand='"+inbrand+"',inverter_capacity='"+incap+"',inverter_model='"+inmodel+"',price='"+inprice+"', erp_pre_tax= (SELECT inverter_eretailer_price FROM inverter_price_details where city='"+User_City+"' and inverter_model='"+inmodel+"'), quantity='"+Quantity+"',postpone_date='0000-00-00' WHERE  order_number='"+ordernum+"' and order_id='"+orderid+"'";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
			}

			else if(orderstatus.equals("Customer Contacted") && orderreason.equals("Not Confirmed Order - Customer Confirmed to Place Order"))
			{
				String Strretid="";
				String Strlocorpin="";
				String StrSqlQry ="";
				String strstate="";
				String strstate1="";
				String retailer_id="";
				String StrDiscountPrice="";

				
				
				Date now = new Date();
			SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely
			//System.out.println(simpleDateformat.format(now));
			LogLevel.DEBUG(5, new Throwable(), "Day Info :" + simpleDateformat.format(now));
			String day =simpleDateformat.format(now);
			LogLevel.DEBUG(5, new Throwable(), "Day Info String:" + day);
			
			if(day.equals("Sunday"))
			{

				String StrSqlQry12 = "select retailer_id from retailer_location_mapping where state='"+cusstate+"' and city='"+cuscity+"' and area='"+cusarea+"' and bat_brand='"+inbrand+"' and weekend_dealer_flag='Yes' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry12 :" + StrSqlQry12);
				Hashtable htretailerid1 = qm.getRow(StrSqlQry12); 
				if(htretailerid1.isEmpty())
				{
					StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+cusstate+"' and city='"+cuscity+"' and area='"+cusarea+"' and bat_brand='"+inbrand+"' and weekend_dealer_flag='No' limit 1";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);

				}
				else
				{

					StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+cusstate+"' and city='"+cuscity+"' and area='"+cusarea+"' and bat_brand='"+inbrand+"' and weekend_dealer_flag='Yes' limit 1";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
				}
			}
			else
			{
				StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+cusstate+"' and city='"+cuscity+"' and area='"+cusarea+"' and bat_brand='"+inbrand+"' and weekend_dealer_flag='No' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
			}

				Hashtable htretailerid = qm.getRow(StrSqlQry); 
				Strretid=String.valueOf(htretailerid.get("retailer_id"));
				LogLevel.DEBUG(5, new Throwable(), "Strretid :" + Strretid);
				Strlocorpin = cuscity;


			if(Strretid.equals(null) || Strretid.equals("null") || Strretid == null || Strretid == "null" || Strretid =="")
			{
				strRes="<div class='col-md-4 col-md-offset-4'>  <table border='0' width='300px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'>  <tr height='10'><table width='100%'  valign='top'></table><table  width='100%'  valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 25px;font-weight: 600;text-align: center;'> BookBattery</td> <td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: left;'  href=\"javascript:closemobdiv(greyout(false));\" > <a style='color: #F96F2B;' href=\"javascript:closemobdiv(greyout(false));\");\"> X </a> </td></tr></table><hr style='background: white; width: 90%;margin-top: 1px;margin-bottom: 1px;'></tr> </table><table border='0' style='margin-top: 10px;' width='100%' height='2px'  valign'top'><tr ><td align='justify'  style='font-family:Verdana;font-size:14px;color:#FFFFFF;padding: 16px;'>No Retailers Found on Selected City.</td></tr><tr height='10'><td></td></tr><tr height='10'><td align='center'> <input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:closemobdiv(greyout(false));\");\" > <br></td></tr><tr><td height='10'></td></tr></table> </div>";				
			}
			else
			{
			strstate=cusstate.trim().replaceAll(" ", "_"); 
			LogLevel.DEBUG(5, new Throwable(), "strstate :" + strstate);
			String StrSqlQrydet = "select retailer_id,retailer_name,mobile_number,email_id,address1,mobile_numberother,invoice_flag from "+strstate+"_retailers where retailer_id='"+Strretid+"'";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQrydet :" + StrSqlQrydet);

			Hashtable htretailerdetls = qm.getRow(StrSqlQrydet); 
			retailer_id=String.valueOf(htretailerdetls.get("retailer_id"));
			String strretmobnum=String.valueOf(htretailerdetls.get("mobile_number"));
			String strretname=(String)htretailerdetls.get("retailer_name");
			String strretemail=(String)htretailerdetls.get("email_id");
			String straddress1=(String)htretailerdetls.get("address1");
			String strretothermobnum=String.valueOf(htretailerdetls.get("mobile_numberother"));
			String strinvoiceflag=String.valueOf(htretailerdetls.get("invoice_flag"));

			
			
			if(retailer_id.equals(null) || retailer_id.equals("null") || retailer_id == null || retailer_id == "null" || retailer_id =="")
			{
			strRes="<table border='0' width='300px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#562d82'>BookBattery</td><td align='right'><a href='javascript:closemobdivindex1(greyout(false));'><img src=\"./images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='300px' height='2px' bgcolor='#FFFFFF' valign'top'><tr class='pages' bgcolor='#FFFFFF'><td align='center' bgcolor='#FFFFFF'>Session Expired or Server down. please regenerate your order.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closemobdivindex1();' class='button4'><br></td></tr><tr height='15'></tr></table>";
			}
			else
			{

			String verificationcode = "";
			verificationcode =ordernum;
			LogLevel.DEBUG(5, new Throwable(), "verificationcode :" + verificationcode);
			

			String Get_Product_Price_SQL ="select DISTINCT a.inverter_price_id, a.inverter_actual_price, a.inverter_discount_price, a.inverter_capacity,a.inverter_eretailer_price, b.inverter_city_percentage from inverter_price_details a, percentage b where a.inverter_brand='"+inbrand+"' and a.inverter_model='"+inmodel+"' and a.state='"+cusstate+"' and a.city='"+cuscity+"' and a.city=b.city ORDER BY inverter_price_id DESC LIMIT 1"; 
			
			
			LogLevel.DEBUG(5, new Throwable(), "Get_Product_Price_SQL :" + Get_Product_Price_SQL);

			Hashtable Get_Product_Price_SQL_HT = qm.getRow(Get_Product_Price_SQL);
			LogLevel.DEBUG(5, new Throwable(), "Get_Product_Price_SQL_HT :" + Get_Product_Price_SQL_HT);

			if(Get_Product_Price_SQL_HT.isEmpty())
			{
				strRes="Session Expired or Server Down. Please regenerate your order.";
				return strRes;
			}
			else
			{

				int ProductActualPrice=(Integer)Get_Product_Price_SQL_HT.get("inverter_actual_price");
				LogLevel.DEBUG(5, new Throwable(), "ProductActualPrice :" + ProductActualPrice);
				
				int ERP_Pre_TAX=(Integer)Get_Product_Price_SQL_HT.get("inverter_eretailer_price");
				LogLevel.DEBUG(5, new Throwable(), "ERP_Pre_TAX :" + ERP_Pre_TAX);
				
				String City_Percentage=(String)Get_Product_Price_SQL_HT.get("inverter_city_percentage");
				LogLevel.DEBUG(5, new Throwable(), "City_Percentage :" + City_Percentage);
				
				int StrDiscountPriceint=(Integer)Get_Product_Price_SQL_HT.get("inverter_discount_price");
				LogLevel.DEBUG(5, new Throwable(), "StrDiscountPriceint :" + StrDiscountPriceint);
					
				StrDiscountPrice=String.valueOf(StrDiscountPriceint*Integer.parseInt(Quantity));
				
				String Inverter_Capacity=(String)Get_Product_Price_SQL_HT.get("inverter_capacity");
				LogLevel.DEBUG(5, new Throwable(), "Inverter_Capacity :" + Inverter_Capacity);
									
				String strSqlQry_Update = "UPDATE inverter_order_details SET erp_pre_tax = '"+ERP_Pre_TAX+"', MRP_Price = '"+ProductActualPrice+"', city_percentage = '"+City_Percentage+"', consumer_address='"+User_Address_Landmark+"', inverter_capacity='"+Inverter_Capacity+"' WHERE  order_number='"+verificationcode+"' ";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry_Update:"+strSqlQry_Update );
				
				int reslt_Update = qm.executeUpdate(strSqlQry_Update);
				LogLevel.DEBUG(5, new Throwable(), "reslt_Update :" + reslt_Update);
			}

			/*following code is for generating the random number */
			GregorianCalendar date = new GregorianCalendar();
			int millseconds = date.get(Calendar.MILLISECOND);
			LogLevel.DEBUG(5, new Throwable(), "millseconds :" + millseconds);

			String milli = Integer.toString(millseconds);
			LogLevel.DEBUG(5, new Throwable(), "milli :" + milli);
			char milliseond = milli.charAt(0);
			LogLevel.DEBUG(5, new Throwable(), "milliseond :" + milliseond);
			
			Random r = new Random( System.currentTimeMillis() );
			LogLevel.DEBUG(5, new Throwable(), "r :" + r);
			int num = ((milliseond + r.nextInt(2)) * 10000 + r.nextInt(10000));
			//LogLevel.DEBUG(5, new Throwable(), "formula :" + r.nextInt(2) * 100000 + r.nextInt(100000));
			LogLevel.DEBUG(5, new Throwable(), "num :" + num);
			

			String verificationcode1 = Integer.toString(num);
			//verificationcode ="ORD"+verificationcode1+"";
			verificationcode =ordernum;
			LogLevel.DEBUG(5, new Throwable(), "verificationcode :" + verificationcode);
			
			/*following code is for generating the random number */
			String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";

			String pw = "";
				for (int i=0; i<PASSWORD_LENGTH; i++)
				{
				int index = (int)(RANDOM.nextDouble()*letters.length());
				pw += letters.substring(index, index+1);
				}
			LogLevel.DEBUG(5, new Throwable(), "pw :" + pw);
			String ThankUSMSMsg = "";
			String ThankUMsg="";
			/*code to generate the random number ends here */


			/* code to send SMS and Email retailers details to consumer */ 
			CompareTrans ct = new CompareTrans(qm);
			//SendSMS sendsms = new SendSMS(qm);

			/* code to send SMS and Email consumer details to retailers */ 
			SendSMS sendsms1 = new SendSMS(qm);
			
			String CustomerAddress="";

			if(cusaddr1.equals("") && cusaddr2.equals(""))
			{
				CustomerAddress="";

			}
			else
			{
				CustomerAddress=", Address: "+cusaddr1+","+cusaddr1+"";

			}

			
			strSqlQry= "update inverter_order_details set order_status='"+orderstatus+"',order_reasons='"+orderreason+"',order_agent_comments='"+order_agent_comments+"',agent_name='"+stroperatorName+"',updated_date=now(),inverter_brand='"+inbrand+"',inverter_capacity='"+incap+"',inverter_model='"+inmodel+"',price='"+inprice+"',postpone_date='0000-00-00',consumer_name='"+cusname+"',consumer_emailid='"+cusemailid+"',state='"+cusstate+"',city='"+cuscity+"',area='"+cusarea+"',retailer_name='"+strretname+"',retailer_mobile_number='"+strretmobnum+"',retailer_emailid='"+strretemail+"',order_status='"+orderstatus+"',order_reasons='"+orderreason+"', quantity='"+Quantity+"', consumer_address='"+CustomerAddress+"',order_agent_comments='"+orderreason+"' , payment_mode='Cash', payment_mode_type='Cash On Delivery',confirm_by='Operator' WHERE  order_number='"+ordernum+"' and order_id='"+orderid+"'";
			
			

			//######## Send SMS for ORDER
			Order_SMS Send_Order_SMS_Checkout = new Order_SMS(qm);
			String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,ordernum,"Inverter","Yes","No","Yes");
			LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
			//######## Send SMS for ORDER
					
			//######## Send Mail for ORDER
			Order_SMS Send_Order_MAIL_Checkout = new Order_SMS(qm);
			String MAIL_Report=Send_Order_MAIL_Checkout.Send_Order_Mail(req,res,session,ordernum,"Inverter","Yes","No","Yes");
			LogLevel.DEBUG(5, new Throwable(), "MAIL_Report :" + MAIL_Report);
			//######## Send Mail for ORDER	

			}
			}
			}
			else
			{
				if(stroperatorName.equals("null") || stroperatorName.equals("NULL") || stroperatorName.equals(null) || stroperatorName == "null" || stroperatorName == "NULL" || stroperatorName == null || stroperatorName.equals("0") || stroperatorName == "0" || stroperatorName.equals(" ") || stroperatorName == "" || stroperatorName.equals("undefined") || stroperatorName == "undefined")
				{

					LogLevel.DEBUG(1,new Throwable(),"Operator Name is Empty or null");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#00dd00' class='vrb10'></font> ");
					out.println("Failed to update ordered status!");
				}
				else
				{
					if(order_reasons=="0"||order_reasons.equals("0"))
					{					
						order_agent_comments=order_agent_comments+" cancel before confirm";
						LogLevel.DEBUG(5, new Throwable(), "order_agent_comments :" + order_agent_comments);
					}
					else
					{
						order_agent_comments=order_agent_comments;
						LogLevel.DEBUG(5, new Throwable(), "order_agent_comments :" + order_agent_comments);
					}
					
					strSqlQry= "update inverter_order_details set order_status='"+orderstatus+"',order_reasons='"+orderreason+"',order_agent_comments='"+order_agent_comments+"',agent_name='"+stroperatorName+"',updated_date=now(),installed_date='0000-00-00',postpone_date='0000-00-00 00:00:00' WHERE  order_number='"+ordernum+"' and order_id='"+orderid+"'";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
				}

			}
			
			int i=qm.executeUpdate(strSqlQry);
			if(i <0)
			{
				LogLevel.DEBUG(1,new Throwable(),"No Name exists u can continue ");
				session.setAttribute("priority","1");
				session.setAttribute("sesErrorMsg", "<font color='#00dd00' class='vrb10'></font> ");
				out.println("Failed to update ordered status!");
			}
			else
			{
				
			if(orderstatus.equals("Customer Contacted") && orderreason.equals("installed"))
			{
					
				String Get_Cust_Details_SQL = "select consumer_name,order_coupon_code,consumer_mobnumber,consumer_emailid,payment_mode_type,order_reasons,city,forwareded_order from inverter_order_details  WHERE  order_number='"+ordernumber+"' and order_id='"+orderid+"'";
				LogLevel.DEBUG(5,new Throwable(),"Get_Cust_Details_SQL:"+Get_Cust_Details_SQL );				
				Hashtable Get_Cust_Details_HT = qm.getRow(Get_Cust_Details_SQL); 
				String cust_name=(String)Get_Cust_Details_HT.get("consumer_name");
				LogLevel.DEBUG(5,new Throwable(),"cust_name:"+cust_name );
				String cust_mobile_number=(String)Get_Cust_Details_HT.get("consumer_mobnumber");
				LogLevel.DEBUG(5,new Throwable(),"cust_mobile_number:"+cust_mobile_number );
				String cust_emailid=(String)Get_Cust_Details_HT.get("consumer_emailid");
				LogLevel.DEBUG(5,new Throwable(),"cust_emailid:"+cust_emailid );
				String cust_bat_type="Inverter";
				LogLevel.DEBUG(5,new Throwable(),"cust_bat_type:"+cust_bat_type );
				String cust_city=(String)Get_Cust_Details_HT.get("city");	
				LogLevel.DEBUG(5,new Throwable(),"cust_city:"+cust_city );	
				String forwareded_order=(String)Get_Cust_Details_HT.get("forwareded_order");	
				LogLevel.DEBUG(5,new Throwable(),"forwareded_order:"+forwareded_order );
				String payment_mode_type=(String)Get_Cust_Details_HT.get("payment_mode_type");	
				LogLevel.DEBUG(5,new Throwable(),"payment_mode_type:"+payment_mode_type);				
				String order_reason=(String)Get_Cust_Details_HT.get("order_reasons");	
				LogLevel.DEBUG(5,new Throwable(),"order_reasons:"+order_reason);
				referred_msg_coupon_code=(String)Get_Cust_Details_HT.get("order_coupon_code");	
				LogLevel.DEBUG(5,new Throwable(),"referred_msg_coupon_code:"+referred_msg_coupon_code );
				//String serverURL="192.168.1.7";	
				String satisfy_flag;	
				String DomainCusName="BookBattery.com";	
				String serverURL="www.bookbattery.com";	
				LogLevel.DEBUG(5,new Throwable(),"serverURL:"+serverURL );
				

			/** Code starts here to send SMS to Consumer **/
				String DomainName="BookBattery.com";
				String ThankUSMSMsg2="";
				String ReffMessage="";
				
				ReffMessage ="Please Share your Reference code "+referred_msg_coupon_code+" to your friends and get Free Battery Health Check for your Car/Inverter Battery of worth Rs.299/599 by clicking "+shorturl+" . Thanks for your support."; 
				
				if(rating.equals("Satisfied"))
				{										
					if(forwareded_order.equals("Yes"))
					{
						
						String URL_Encode="http://"+serverURL+"/bookbattery/servlet/CustomerRatings?hidWhatToDo=insertcustomerdetails&name="+cust_name+"&ordernumber="+ordernumber+"&email="+cust_emailid+"&phone="+cust_mobile_number+"&city="+cust_city+"&batterytype="+cust_bat_type+"&sms_link_url="+SMSURL+"&shorturl="+shorturl+"&rating="+rating+"";
						LogLevel.DEBUG(5,new Throwable(),"Inside bookbattery condition:"+URL_Encode );
						
						LogLevel.DEBUG(5, new Throwable(), "URL_Encode Before :" + URL_Encode);
						URL_Encode =URL_Encode.replaceAll(" ","%20");
						LogLevel.DEBUG(5, new Throwable(), "URL_Encode After :" + URL_Encode);
						
						StringBuilder result = new StringBuilder();
						
						URL url = new URL(URL_Encode);
						
					
						LogLevel.DEBUG(5, new Throwable(), "result.toString() :" + url);
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						conn.setRequestMethod("POST");
						BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						String line;
						while ((line = rd.readLine()) != null) {
						 result.append(line);
						}
						rd.close();
						   
						LogLevel.DEBUG(5, new Throwable(), "result.toString() :" + result.toString());
						
						ThankUSMSMsg2 ="Your Order with BookBattery.com , Ref No: "+ordernumber+" has been successfully installed. Please provide your valuable ratings by clicking "+googleshorturl1+" "; 
						
						LogLevel.DEBUG(5,new Throwable(),"strIpAddress:"+strIpAddress );
						LogLevel.DEBUG(5,new Throwable(),"strPort:"+strPort );
						LogLevel.DEBUG(5,new Throwable(),"SMSFromAddress:"+SMSFromAddress );
						LogLevel.DEBUG(5,new Throwable(),"consumermobilenumber:"+consumermobilenumber );

						
					}
					else
					{
						
						String Get_order_id_SQL ="select DISTINCT order_id from customer_rating_details where order_id='"+ordernumber+"'"; 
						LogLevel.DEBUG(5, new Throwable(), "Get_order_id_SQL :" + Get_order_id_SQL);

						Hashtable Get_order_id_SQL_HT = qm.getRow(Get_order_id_SQL);
						LogLevel.DEBUG(5, new Throwable(), "Get_order_id_SQL_HT :" + Get_order_id_SQL_HT);

						if(Get_order_id_SQL_HT.isEmpty())
						{
							String SteSqlQry = "insert into customer_rating_details(s_id,name,order_id,email,mobile_number,city,batterytype,rating_comments,no_of_rating,sms_link_url,short_url,creation_date,updated_date,sent_flag,upd_comm_flag,satisfy_flag) values(NULL,'"+cust_name+"','"+ordernumber+"','"+cust_emailid+"','"+cust_mobile_number+"','"+cust_city+"','"+cust_bat_type+"','','','"+SMSURL1+"','"+shorturl1+"',now(),now(),'No','No','Yes')";	

							LogLevel.DEBUG(5,new Throwable(),"SteSqlQry:"+SteSqlQry );

							int reslt = qm.executeUpdate(SteSqlQry);

							if(reslt <0)
							{
							LogLevel.DEBUG(1,new Throwable(),"Failed to update your details! ");
							}
							else
							{
							ThankUSMSMsg2 ="Your Order with "+DomainName+" , Ref No: "+ordernumber+" has been successfully installed. Please provide your valuable ratings by clicking "+googleshorturl+" "; 

							//Your Order with XXXX , Ref No: XXXX has been successfully installed. Please provide your valuable ratings by clicking XXXX 

							// ThankUSMSMsg2 ="Your Order with "+DomainName+" , Ref No: "+ordernumber+" has been Cancelled. For any kind of Assistance please call to "+DomainMobNumber+" .";
							LogLevel.DEBUG(5,new Throwable(),"Satisfied ThankUSMSMsg2:"+ThankUSMSMsg2 );
							}
							
						}
						else
						{
							ThankUSMSMsg2 ="Your Order with "+DomainName+" , Ref No: "+ordernumber+" has been successfully installed. Please provide your valuable ratings by clicking "+googleshorturl+" "; 
							 
							LogLevel.DEBUG(5,new Throwable(),"Not inserted already exists");
							
						}
						

					}
				}
				else
				{
					/*Condition Starts here for not satisfied Customer*/
					if(forwareded_order.equals("Yes"))
					{
						
						String URL_Encode="http://"+serverURL+"/bookbattery/servlet/CustomerRatings?hidWhatToDo=insertcustomerdetails&name="+cust_name+"&ordernumber="+ordernumber+"&email="+cust_emailid+"&phone="+cust_mobile_number+"&city="+cust_city+"&batterytype="+cust_bat_type+"&sms_link_url="+SMSURL+"&shorturl="+shorturl+"&rating="+rating+"";
						LogLevel.DEBUG(5,new Throwable(),"Inside bookbattery condition:"+URL_Encode );
						
						LogLevel.DEBUG(5, new Throwable(), "URL_Encode Before :" + URL_Encode);
						URL_Encode =URL_Encode.replaceAll(" ","%20");
						LogLevel.DEBUG(5, new Throwable(), "URL_Encode After :" + URL_Encode);
						
						StringBuilder result = new StringBuilder();
						
						URL url = new URL(URL_Encode);
						
					
						LogLevel.DEBUG(5, new Throwable(), "result.toString() :" + url);
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						conn.setRequestMethod("POST");
						BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						String line;
						while ((line = rd.readLine()) != null) {
						 result.append(line);
						}
						rd.close();
						   
						LogLevel.DEBUG(5, new Throwable(), "result.toString() :" + result.toString());
						
						ThankUSMSMsg2 ="Your Order with "+DomainName+" , Ref No: "+ordernumber+" has been successfully installed."; 
						LogLevel.DEBUG(5, new Throwable(), "ThankUSMSMsg2 :" + ThankUSMSMsg2);
						
					}
					else
					{
						
						String Get_order_id_SQL ="select DISTINCT order_id from customer_rating_details where order_id='"+ordernumber+"'"; 
						LogLevel.DEBUG(5, new Throwable(), "Get_order_id_SQL :" + Get_order_id_SQL);

						Hashtable Get_order_id_SQL_HT = qm.getRow(Get_order_id_SQL);
						LogLevel.DEBUG(5, new Throwable(), "Get_order_id_SQL_HT :" + Get_order_id_SQL_HT);

						if(Get_order_id_SQL_HT.isEmpty())
						{
							String SteSqlQry = "insert into customer_rating_details(s_id,name,order_id,email,mobile_number,city,batterytype,rating_comments,no_of_rating,sms_link_url,short_url,creation_date,updated_date,sent_flag,upd_comm_flag,satisfy_flag) values(NULL,'"+cust_name+"','"+ordernumber+"','"+cust_emailid+"','"+cust_mobile_number+"','"+cust_city+"','"+cust_bat_type+"','','','"+SMSURL1+"','"+shorturl1+"',now(),now(),'No','No','No')";	

							LogLevel.DEBUG(5,new Throwable(),"SteSqlQry:"+SteSqlQry );

							int reslt = qm.executeUpdate(SteSqlQry);

							if(reslt <0)
							{
							LogLevel.DEBUG(1,new Throwable(),"Failed to update your details! ");
							}
							else
							{
								ThankUSMSMsg2 ="Your Order with "+DomainName+" , Ref No: "+ordernumber+" has been successfully installed."; 
								LogLevel.DEBUG(5, new Throwable(), "ThankUSMSMsg2 :" + ThankUSMSMsg2);

							//Your Order with XXXX , Ref No: XXXX has been successfully installed. Please provide your valuable ratings by clicking XXXX 

							// ThankUSMSMsg2 ="Your Order with "+DomainName+" , Ref No: "+ordernumber+" has been Cancelled. For any kind of Assistance please call to "+DomainMobNumber+" .";
							LogLevel.DEBUG(5,new Throwable(),"Satisfied ThankUSMSMsg2:"+ThankUSMSMsg2 );
							}
							
						}
						else
						{
							ThankUSMSMsg2 ="Your Order with "+DomainName+" , Ref No: "+ordernumber+" has been successfully installed."; 
							LogLevel.DEBUG(5, new Throwable(), "ThankUSMSMsg2 :" + ThankUSMSMsg2);
							LogLevel.DEBUG(5,new Throwable(),"Not inserted already exists");
							
						}
						
						//LogLevel.DEBUG(5,new Throwable(),"Not Satisfied ThankUSMSMsg2:"+ThankUSMSMsg2 );
						LogLevel.DEBUG(5,new Throwable(),"strIpAddress:"+strIpAddress );
						LogLevel.DEBUG(5,new Throwable(),"strPort:"+strPort );
						LogLevel.DEBUG(5,new Throwable(),"SMSFromAddress:"+SMSFromAddress );
						LogLevel.DEBUG(5,new Throwable(),"consumermobilenumber:"+consumermobilenumber );
						
					}
				
				}
				
					if(payment_mode_type.equals("Online Payment After Fitment"))
					{
					if(order_reason.equals("installed"))
					{
					String strSMSResponse3=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUSMSMsg2, consumermobilenumber);
					LogLevel.DEBUG(5,new Throwable(),"strSMSResponse3:"+strSMSResponse3 );
					//ThankUSMSMsg2="Your ordered battery on "+battery_brand+" "+battery_model+" with order number "+order_number+" has been successfully installed by "+retailer_name+".";	
					LogLevel.DEBUG(5,new Throwable(),"Not Satisfied ThankUSMSMsg2:"+ThankUSMSMsg2 );

					String strSMSResponse4=sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ReffMessage,consumermobilenumber);
					LogLevel.DEBUG(5,new Throwable(),"strSMSResponse4:"+strSMSResponse4);
					}
					else
					{

					}
					}
					else
					{
					String strSMSResponse3=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUSMSMsg2, consumermobilenumber);
					LogLevel.DEBUG(5,new Throwable(),"strSMSResponse3:"+strSMSResponse3 );
					//ThankUSMSMsg2="Your ordered battery on "+battery_brand+" "+battery_model+" with order number "+order_number+" has been successfully installed by "+retailer_name+".";	
					LogLevel.DEBUG(5,new Throwable(),"Not Satisfied ThankUSMSMsg2:"+ThankUSMSMsg2 );

					String strSMSResponse4=sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ReffMessage,consumermobilenumber);
					LogLevel.DEBUG(5,new Throwable(),"strSMSResponse4:"+strSMSResponse4);

					}

				/** Code starts here to send SMS to Consumer **/
					
					/** Code Starts here to send email  to customer **/

					String CustomerMessage;
					//CustomerMessage="Your inverter order battery on "+inverterbrand+" "+invertermodel+" with  "+invertercapacity+" with order number "+ordernumber+" has been successfully installed by "+retailername+".";
					CustomerMessage="Your inverter order battery on "+inverterbrand+" "+invertermodel+" with  "+invertercapacity+" with order number "+ordernumber+" has been successfully installed.";
					
					if(forwareded_order.equals("Yes"))
					{
						DomainCusName="BookBattery";
					}
					else
					{
						DomainCusName="BookBattery";
					}
					
					String strCustomerMessage="Dear "+consumername+",\r\n\r\n"+""+CustomerMessage+"";
					String strThanksMessage="Thanks & Regards,"+"\r\n"+""+DomainCusName+" Support Team."; 
					String strThanksMessageCus="Thanks & Regards,"+"\r\n"+""+DomainCusName+" Support Team."; 
					MailTrans consumer=new MailTrans();
					consumer.setStrSmtpHost(strdomainname);
					consumer.setStrFrom(FromEmailId);
					consumer.setStrTo(consumeremailid);
					LogLevel.DEBUG(5, new Throwable(), "consumeremailid :" + consumeremailid);
					consumer.setStrSubject(""+DomainCusName+" Inverter Ordered Status");
					String customerMail = strCustomerMessage+"\r\n\r\n"+strThanksMessageCus+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from "+DomainCusName+" is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. "+DomainCusName+" does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "customerMail :" + customerMail);
					consumer.setStrText(customerMail);
					
					if(payment_mode_type.equals("Online Payment After Fitment"))
					{
						if(order_reason.equals("installed"))
						{
							Thread customerMailThread=new MailThread(consumer,"");
							customerMailThread.start();
						}
						else
						{
							
						}
					}
					else
					{
							Thread customerMailThread=new MailThread(consumer,"");
							customerMailThread.start();
					}

					/** Code Ends here to send email  to customer **/

					/** Code Starts here to send email and sms to Franchisee **/

					String FranchiseMessage="";
					String FranchiseemailMessage="";

					if(inverterbrand.equals("Amaron"))
					{
						if(retailername.contains("Amaron-Pitstop"))
						{
						FranchiseMessage="The ordered inverter with order number "+ordernumber+" has been successfully installed. Order Details Customer Name- "+consumername+", Customer Mobile Num- "+consumermobilenumber+", Inverter Brand- "+inverterbrand+", Inverter Model- "+invertermodel+", Inverter Capacity- "+invertercapacity+"";

						FranchiseemailMessage="The ordered inverter with order number "+ordernumber+" has been successfully installed. Order Details Customer Name- "+consumername+", Customer Mobile Num- "+consumermobilenumber+", Inverter Brand- "+inverterbrand+", Inverter Model- "+invertermodel+", Inverter Capacity- "+invertercapacity+"";
						
						
						if(payment_mode_type.equals("Online Payment After Fitment"))
						{
							if(order_reason.equals("installed"))
							{
								String strSMSResponse22 =  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,FranchiseMessage, retailermobilenumber);
								LogLevel.DEBUG(5, new Throwable(), "strSMSResponse22 :" + strSMSResponse22);
							}
							else
							{
								
							}
						}
						else
						{
								String strSMSResponse22 =  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,FranchiseMessage, retailermobilenumber);
								LogLevel.DEBUG(5, new Throwable(), "strSMSResponse22 :" + strSMSResponse22);
						}

						}
						else
						{

						/** jhansi started placing code to calculate commission amount **/

						String strSqlQrygetcitypercentage = "select inverter_city_percentage FROM percentage WHERE  city='"+city+"'";
						LogLevel.DEBUG(5,new Throwable(),"strSqlQrygetcitypercentage :"+strSqlQrygetcitypercentage);

						Hashtable htgetcitypercentage = qm.getRow(strSqlQrygetcitypercentage);
						String city_percentage=String.valueOf(htgetcitypercentage.get("inverter_city_percentage"));
						LogLevel.DEBUG(5,new Throwable(),"city_percentage :"+city_percentage);

						String strSqlQrycalculatecom = "select CAST(round(((inverter_discount_price))-(inverter_eretailer_price*"+city_percentage+"))/ 2 AS SIGNED) as grandtotal FROM inverter_price_details WHERE  inverter_capacity='"+invertercapacity+"' and inverter_model='"+invertermodel+"' and city='"+city+"'";
						LogLevel.DEBUG(5,new Throwable(),"strSqlQrycalculatecom :"+strSqlQrycalculatecom);

						Hashtable htcalculate = qm.getRow(strSqlQrycalculatecom);
						String grandtotal=String.valueOf(htcalculate.get("grandtotal"));
						LogLevel.DEBUG(5,new Throwable(),"grandtotal :"+grandtotal);
						/** jhansi ended placing code to calculate commission amount **/

						FranchiseemailMessage="The ordered inverter with order number "+ordernumber+" has been successfully installed. Order Details Customer Name- "+consumername+" , Customer Mobile Num- "+consumermobilenumber+" , Inverter Brand- "+inverterbrand+" , Inverter Model- "+invertermodel+" , Inverter Capacity- "+invertercapacity+" .Please credit commission amount Rs. "+grandtotal+" in the company bank account with in 72hours.";

						FranchiseMessage="The ordered battery with order number "+ordernumber+" has been successfully installed. Please credit commission amount Rs. "+grandtotal+" in the company bank account with in 72hours";


						if(payment_mode_type.equals("Online Payment After Fitment"))
						{
							if(order_reason.equals("installed"))
							{
								String strSMSResponse22 =  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,FranchiseMessage, retailermobilenumber);
								LogLevel.DEBUG(5, new Throwable(), "strSMSResponse22 :" + strSMSResponse22);
							}
							else
							{
								
							}
						}
						else
						{
								String strSMSResponse22 =  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,FranchiseMessage, retailermobilenumber);
								LogLevel.DEBUG(5, new Throwable(), "strSMSResponse22 :" + strSMSResponse22);
						}
						
						}
					}
					else
					{

						/** jhansi started placing code to calculate commission amount **/
						String strSqlQrycalculatecom = "select CAST(round(((inverter_discount_price))-(inverter_eretailer_price))/ 2 AS SIGNED) as grandtotal FROM inverter_price_details WHERE  inverter_capacity='"+invertercapacity+"' and inverter_model='"+invertermodel+"' and city='"+city+"'";
						LogLevel.DEBUG(5,new Throwable(),"strSqlQrycalculatecom :"+strSqlQrycalculatecom);

						Hashtable htcalculate = qm.getRow(strSqlQrycalculatecom);
						String grandtotal=String.valueOf(htcalculate.get("grandtotal"));
						LogLevel.DEBUG(5,new Throwable(),"grandtotal :"+grandtotal);
						/** jhansi ended placing code to calculate commission amount **/

						FranchiseemailMessage="The ordered inverter with order number "+ordernumber+" has been successfully installed. Order Details Customer Name- "+consumername+" , Customer Mobile Num- "+consumermobilenumber+" , Inverter Brand- "+inverterbrand+" , Inverter Model- "+invertermodel+" , Inverter Capacity- "+invertercapacity+" .Please credit commission amount Rs. "+grandtotal+" in the company bank account with in 72hours.";

						FranchiseMessage="The ordered battery with order number "+ordernumber+" has been successfully installed. Please credit commission amount Rs. "+grandtotal+" in the company bank account with in 72hours";


						
						if(payment_mode_type.equals("Online Payment After Fitment"))
						{
							if(order_reason.equals("installed"))
							{
									String strSMSResponse22 =  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,FranchiseMessage, retailermobilenumber);
									LogLevel.DEBUG(5, new Throwable(), "strSMSResponse22 :" + strSMSResponse22);
							}
							else
							{
								
							}
						}
						else
						{
								String strSMSResponse22 =  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,FranchiseMessage, retailermobilenumber);
								LogLevel.DEBUG(5, new Throwable(), "strSMSResponse22 :" + strSMSResponse22);
						}
						
					}

					String strFranchiseMessage="Dear "+retailername+",\r\n\r\n"+""+FranchiseemailMessage+"";
					MailTrans franchise=new MailTrans();
					franchise.setStrSmtpHost(strdomainname);
					franchise.setStrFrom(FromEmailId);
					franchise.setStrTo(retaileremail);
					LogLevel.DEBUG(5, new Throwable(), "retailermobilenumber :" + retailermobilenumber);
					String franchiseMail ="";
					String strThanksMessagedealer=""; 

					if(inverterbrand.equals("Amaron"))
					{

						if(retailername.contains("Amaron-Pitstop"))
						{
							strThanksMessagedealer="Thanks & Regards,"+"\r\n"+"AmaronExpress Support Team."; 
							franchise.setStrSubject("AmaronExpress Inverter Ordered Status");
							franchiseMail = strFranchiseMessage+"\r\n\r\n"+strThanksMessage+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from AmaronExpress is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. AmaronExpress does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
							LogLevel.DEBUG(5, new Throwable(), "franchiseMail :" + franchiseMail);
						}

						else
						{
							strThanksMessagedealer="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
							franchise.setStrSubject("BookBattery Inverter Ordered Status");
							franchiseMail = strFranchiseMessage+"\r\n\r\n"+strThanksMessage+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
							LogLevel.DEBUG(5, new Throwable(), "franchiseMail :" + franchiseMail);

						}
					}
					else
					{
						strThanksMessagedealer="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
						franchise.setStrSubject("BookBattery Inverter Ordered Status");
						franchiseMail = strFranchiseMessage+"\r\n\r\n"+strThanksMessage+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
						LogLevel.DEBUG(5, new Throwable(), "franchiseMail :" + franchiseMail);

					}
					franchise.setStrText(franchiseMail);
					
					if(payment_mode_type.equals("Online Payment After Fitment"))
					{
						if(order_reason.equals("installed"))
						{
							Thread franchiseMailThread=new MailThread(franchise,"");
							franchiseMailThread.start();
						}
						else
						{
							
						}
					}
					else
					{
						Thread franchiseMailThread=new MailThread(franchise,"");
						franchiseMailThread.start();
					}


					/** Code Ends here to send email and sms to Franchisee **/

					/** Code Starts here to send email and sms to Operator **/

					String OperatorMessage;
					
					OperatorMessage="The ordered inverter with order number "+ordernumber+" has been successfully installed. Order Details Customer Name- "+consumername+", Customer Mobile Num- "+consumermobilenumber+", Inverter Brand- "+inverterbrand+", Inverter Model- "+invertermodel+", Inverter Capacity- "+invertercapacity+", Franchisee Name- "+retailername+", Franchisee Mobile Number- "+retailermobilenumber+"";
				
					String strOperatorMessage="Dear Operator,\r\n\r\n"+""+OperatorMessage+"";
					MailTrans operator=new MailTrans();
					operator.setStrSmtpHost(strdomainname);
					operator.setStrFrom(FromEmailId);
					operator.setStrTo(strsuppemaild);
					LogLevel.DEBUG(5, new Throwable(), "strsuppemaild :" + strsuppemaild);
					operator.setStrSubject("BookBattery Inverter Ordered Status");
					String operatorMail = strOperatorMessage+"\r\n\r\n"+strThanksMessage+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "operatorMail :" + operatorMail);
					operator.setStrText(operatorMail);
					
					if(payment_mode_type.equals("Online Payment After Fitment"))
					{
						if(order_reason.equals("installed"))
						{
							Thread operatorMailThread=new MailThread(operator,"");
							operatorMailThread.start();

						}
						else
						{
							
						}
					}
					else
					{
						Thread operatorMailThread=new MailThread(operator,"");
						operatorMailThread.start();

					}

					/** Code Ends here to send email and sms to Franchisee **/

				}
				if(orderstatus.equals("Customer Contacted") && orderreason.equals("cancelled-franchisee-notresponded")||orderreason.equals("cancelled-franchisee-modeloutofstock")||orderreason.equals("cancelled-franchisee-offbushrs")||orderreason.equals("cancelled-franchisee-denied")||orderreason.equals("cancelled-customer") ||orderreason.equals("cancelled-regenerated"))
				{
					String CustomerMessage;
					String CancelledCustomerMesg="";
					String CancelledOperatorMesg="";
					if(orderstatus.equals("cancelled-franchisee-notresponded"))
					{
						CancelledOperatorMesg="due to franchisee has not responsed";
						CancelledCustomerMesg="due to your ordered model is out of stock";
					}
					else if(orderstatus.equals("cancelled-franchisee-modeloutofstock"))
					{
						CancelledOperatorMesg="due to your ordered model is out of stock";
						CancelledCustomerMesg="due to your ordered model is out of stock";
					}
					else if(orderstatus.equals("cancelled-franchisee-offbushrs"))
					{
						CancelledOperatorMesg="due to off business hours for franchisee";
						CancelledCustomerMesg="due to off business hours for franchisee";
					}
					else if(orderstatus.equals("cancelled-franchisee-denied"))
					{
						CancelledOperatorMesg="due to our franchisee has denied";
						CancelledCustomerMesg="due to your ordered model is out of stock";
					}
					else if(orderstatus.equals("cancelled-customer"))
					{
						CancelledOperatorMesg="by customer";
						CancelledCustomerMesg="due to "+agentcomments+"";
					}
					else if(orderstatus.equals("cancelled-regenerated"))
					{						
						CancelledOperatorMesg="and regenerated with another model";
						CancelledCustomerMesg="and regenerated with another model";												
					}
					else
					{
					}
					
					
				
					
					//####################### Consumer SMS - Cancelled Order.##########################
					String DomainName="BookBattery.com";
					String DomainMobNumber="9603467559";
					String ThankUSMSMsg ="Your Order with "+Website_Name+" , Ref No: "+ordernumber+" has been Cancelled. For any kind of Assistance please call to "+DomainMobNumber+" .";
					
			  
					String strSMSResponse=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUSMSMsg, consumermobilenumber);
					LogLevel.DEBUG(5, new Throwable(), "strSMSResponse :" + strSMSResponse);
					
					CustomerMessage="Your ordered inverter on "+inverterbrand+" "+invertermodel+" with "+invertercapacity+"  with order number "+ordernumber+" has been cancelled . For any kind of Assistance please call to 9603467559. \r\n\r\n We are looking forward to see you again at "+Website_URL+" .";
				
					String strCustomerMessage="Dear "+consumername+",\r\n\r\n"+""+CustomerMessage+"";
					String strThanksMessage="Thanks & Regards,"+"\r\n"+""+Website_Name+" Support Team."; 
					MailTrans consumer=new MailTrans();
					consumer.setStrSmtpHost(strdomainname);
					consumer.setStrFrom(FromEmailId);
					consumer.setStrTo(consumeremailid);
					LogLevel.DEBUG(5, new Throwable(), "consumeremailid :" + consumeremailid);
					consumer.setStrSubject(""+Website_Name+" Inverter Ordered Status");
					String customerMail = strCustomerMessage+"\r\n\r\n"+strThanksMessage+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from "+Website_Name+" is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. "+Website_Name+" does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "customerMail :" + customerMail);
					consumer.setStrText(customerMail);
					Thread customerMailThread=new MailThread(consumer,"");
					customerMailThread.start();
					

					//####################### Retailer SMS - Cancelled Order.##########################
					
					String ThankUMsg1;
					if(inverterbrand.equals("Amaron") && retailername.contains("Amaron-Pitstop"))
					{
						ThankUMsg1="BookBattery.com Consumer "+consumername+" , Mob no-"+consumermobilenumber+" with Ord Ref No: "+ordernum+" . Consumer Order got Cancelled. For any kind of Assistance please call to 9603467559 .";
						
						DomainName="BookBattery.com";
						DomainMobNumber="9603467559";
					}
					else
					{
						ThankUMsg1="BookBattery.com Consumer "+consumername+" , Mob no-"+consumermobilenumber+" with Ord Ref No: "+ordernum+" . Consumer Order got Cancelled. For any kind of Assistance please call to 9603467559 .";
						
						DomainName="BookBattery.com";
						DomainMobNumber="9603467559";
					}
					
					

					LogLevel.DEBUG(5, new Throwable(), "ThankUMsg1 :" + ThankUMsg1);
					
					
					String Get_cancel_ord_Details_SQL = "select order_agent_comments from inverter_order_details  WHERE  order_number='"+ordernumber+"' and order_id='"+orderid+"'";
					LogLevel.DEBUG(5,new Throwable(),"Get_cancel_ord_Details_SQL:"+Get_cancel_ord_Details_SQL );				
					Hashtable Get_cancel_ord_Details_SQL_HT = qm.getRow(Get_cancel_ord_Details_SQL); 
					String canc_order_agent_comments=(String)Get_cancel_ord_Details_SQL_HT.get("order_agent_comments");
					LogLevel.DEBUG(5,new Throwable(),"canc_order_agent_comments:"+canc_order_agent_comments );
					
					if (canc_order_agent_comments.contains("cancel before confirm"))
					{
						LogLevel.DEBUG(5,new Throwable(),"inside cancel before confirm :"+canc_order_agent_comments);
					}
					else
					{
					
					String  ThanksmsMsg=""+consumername+" Mob No: "+consumermobilenumber+" with Ord Ref No: "+ordernum+" . This Order got Cancelled. For any kind of Assistance please call to "+DomainMobNumber+" .";

					String strSMSResponse1=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThanksmsMsg, retailermobilenumber);
					LogLevel.DEBUG(5, new Throwable(), "strSMSResponse1 :" + strSMSResponse1);

					String strsub1="Dear "+retailername+",\r\n\r\n"+""+ThankUMsg1+"";
					String strThanks1="Thanks & Regards,"+"\r\n"+""+Website_Name+" Support Team."; 
					MailTrans retdetails=new MailTrans();
					retdetails.setStrSmtpHost(strdomainname);
					retdetails.setStrFrom(FromEmailId);
					retdetails.setStrTo(retaileremail);
					retdetails.setStrSubject(""+Website_Name+" Inverter Ordered Status");
					String activateLink1 = strsub1+"\r\n\r\n"+strThanks1+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from "+Website_Name+" is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. "+Website_Name+" does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "activateLink1 :" + activateLink1);
					retdetails.setStrText(activateLink1);
					Thread mt1=new MailThread(retdetails,"");
					mt1.start();
					
					
					}
					

					// Operator Code


					String OperatorMessage;
					
						OperatorMessage="The ordered inverter with order number "+ordernumber+" has been cancelled "+CancelledOperatorMesg+". Order Details Customer Name- "+consumername+", Customer Mobile Num- "+consumermobilenumber+", Inverter Brand- "+inverterbrand+", Inverter Model- "+invertermodel+", Inverter Capacity- "+invertercapacity+", Franchisee Name- "+retailername+"";
			
					String strOperatorMessage="Dear Operator,\r\n\r\n"+""+OperatorMessage+"";
					MailTrans operator=new MailTrans();
					operator.setStrSmtpHost(strdomainname);
					operator.setStrFrom(FromEmailId);
					operator.setStrTo(strsuppemaild);
					LogLevel.DEBUG(5, new Throwable(), "strsuppemaild :" + strsuppemaild);
					operator.setStrSubject(""+Website_Name+" Inverter Ordered Status");
					String operatorMail = strOperatorMessage+"\r\n\r\n"+strThanksMessage+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from "+Website_Name+" is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. "+Website_Name+" does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "operatorMail :" + operatorMail);
					operator.setStrText(operatorMail);
					Thread operatorMailThread=new MailThread(operator,"");
					operatorMailThread.start();
				}
				

				LogLevel.DEBUG(1,new Throwable(),"");
				session.setAttribute("priority","1");
				session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'></font> ");
				//System.out.println(email);
				out.println("Successfully Updated order Status as "+orderstatus+".!");
			}				
	}
	catch (Exception e)
	{
		LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
		e.printStackTrace();
	}
		return strRes;
	}

	/* **************************************************************************************************************************************\
	* This method is to fetch battery brand.
	* @strSqlQry : carries the query to battery brand from battery_details table.
	\* **************************************************************************************************************************************/
	public String getinverterpricedetls(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String constate= (req.getParameter("constate")!=null)?(req.getParameter("constate")):"";
			LogLevel.DEBUG(5, new Throwable(), "constate :" + constate);

			String concity= (req.getParameter("concity")!=null)?(req.getParameter("concity")):"";
			LogLevel.DEBUG(5, new Throwable(), "concity :" + concity);

			String inverterbrand= (req.getParameter("inverterbrand")!=null)?(req.getParameter("inverterbrand")):"";
			LogLevel.DEBUG(5, new Throwable(), "inverterbrand :" + inverterbrand);

			String invertercap= (req.getParameter("invertercap")!=null)?(req.getParameter("invertercap")):"";
			LogLevel.DEBUG(5, new Throwable(), "invertercap :" + invertercap);

			String invertermodel= (req.getParameter("invertermodel")!=null)?(req.getParameter("invertermodel")):"";
			LogLevel.DEBUG(5, new Throwable(), "invertermodel :" + invertermodel);


			

	 		ServletOutputStream out=res.getOutputStream();

			
			String strSqlQry ="select inverter_discount_price from inverter_price_details where state='"+constate+"' and city='"+concity+"' and inverter_brand='"+inverterbrand+"' and inverter_capacity='"+invertercap+"' and inverter_model='"+invertermodel+"'";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Hashtable ht1 = qm.getRow(strSqlQry); 

			if(ht1.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:11px;color:#CCCCCC;' value='defaultss'>&nbsp;Select&nbsp;Inverter&nbsp;Model</option>");
				return strRes;
			}
			else
			{
				String inverter_discount_price =String.valueOf(ht1.get("inverter_discount_price"));
				strRes=""+inverter_discount_price+"";
				return strRes;
			}

		}
		catch (Exception e)
		{
			LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
			e.printStackTrace();
		}
		return strRes;
	}
}// end of class
