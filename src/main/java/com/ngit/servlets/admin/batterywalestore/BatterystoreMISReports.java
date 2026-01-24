/***********************************************************************		
	NGIT Confidential. 
	@File Name   : BusinessBatteryDetails.java
	@Description : This Servlet is used to allow the operator to Login
	@Date        : 18th October 2019
******************************************************************/ 
package com.ngit.servlets.admin.batterywalestore; 

import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.mail.*;
import com.ngit.javabean.consumers.products.Order_Store_SMS;
import com.ngit.javabean.sendsms.SendSMS;

import java.util.Properties;
import java.util.Vector;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.io.*;
import java.util.Random;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.net.URLDecoder;  
import java.net.URLEncoder; 

import java.util.Properties;
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
import static java.lang.System.out;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.json.*; 
import net.sf.ezmorph.*; 

/*
 * @author Sai Krishna Daddala.
 */
/* This class is to Admin Login. This class initializes the necessary connection pools and perform the transactions on the pools. */
public class BatterystoreMISReports extends HttpServlet 
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
	
	/*This doPost service method handles all the requests and responses passing from doGet service method to perform Admin Login.*/
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
		doGet(req,res);
	}
 	/* This doGet service method calls doPost service method to handle all the reqs and responses to perform Admin Login. */
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
		//ServletOutputStream out=res.getOutputStream(); 
		
		/*This method is used to get Battery details*/
		 /* ****************************************************************************************\
		* This action is used to get battery details.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("batteryorderdetails"))
		{ 
			try
			{
				String strRes=batteryorderdetails(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				ServletOutputStream out=res.getOutputStream(); 
				out.println(strRes);
				out.close();
			
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}
		/*This method is used to get Battery details*/
		 /* ****************************************************************************************\
		* This action is used to get battery details.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("inverterorderdetails"))
		{ 
			try
			{
				String strRes=inverterorderdetails(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				ServletOutputStream out=res.getOutputStream(); 
				out.println(strRes);
				out.close();
			
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}	
		/*This method is used to get Battery details*/
		 /* ****************************************************************************************\
		* This action is used to get battery details.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("serviceorderdetails"))
		{ 
			try
			{
				String strRes=serviceorderdetails(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				ServletOutputStream out=res.getOutputStream(); 
				out.println(strRes);
				out.close();
			
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}
		/*This method is used to get Battery details*/
		 /* ****************************************************************************************\
		* This action is used to get battery details.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("misbatterydetails"))
		{ 
			try
			{
				String strRes=misbatterydetails(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				ServletOutputStream out=res.getOutputStream(); 
				out.println(strRes);
				out.close();
			
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}	
		/*This method is used to get Battery details*/
		 /* ****************************************************************************************\
		* This action is used to get battery details.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("misinverterdetails"))
		{ 
			try
			{
				String strRes=misinverterdetails(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				ServletOutputStream out=res.getOutputStream(); 
				out.println(strRes);
				out.close();
			
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}
		/*This method is used to get Battery details*/
		 /* ****************************************************************************************\
		* This action is used to get battery details.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("misservicedetails"))
		{ 
			try
			{
				String strRes=misservicedetails(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				ServletOutputStream out=res.getOutputStream(); 
				out.println(strRes);
				out.close();
			
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}
		/*This method is used to get Battery details*/
		 /* ****************************************************************************************\
		* This action is used to get battery details.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("trolleyorderdetails"))
		{ 
			try
			{
				String strRes=trolleyorderdetails(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				ServletOutputStream out=res.getOutputStream(); 
				out.println(strRes);
				out.close();
			
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}
		
		
		 
	} //End of DoGet

	/* **************************************************************************************************************************************\
	* This method is to fetch battery details details.
	* @strSqlQry : carries the query to select battery details from battery_details table.
	* @strSqlQry1 : Query has multiple pages to select battery details from battery_details table.
	\* **************************************************************************************************************************************/
	public String batteryorderdetails(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		String strConditions="";
		
		String strstoreConditions="";
		
		String strfromdate  = (req.getParameter("fromdate")!=null)?req.getParameter("fromdate"):"";
		LogLevel.DEBUG(5, new Throwable(), "strfromdate :"+ strfromdate);
		
		String strtodate  = (req.getParameter("todate")!=null)?req.getParameter("todate"):"";
		LogLevel.DEBUG(5, new Throwable(), "strtodate :"+ strtodate);
		
		String strstore_id  = (req.getParameter("store_id")!=null)?req.getParameter("store_id"):"";
		LogLevel.DEBUG(5, new Throwable(), "strfromdate :"+ strfromdate);
		
		String strstore_name  = (req.getParameter("store_name")!=null)?req.getParameter("store_name"):"";
		LogLevel.DEBUG(5, new Throwable(), "strstore_name :"+ strstore_name);
		
	  
		strConditions="date(creation_date) between '"+strfromdate+"' and '"+strtodate+"'";
		LogLevel.DEBUG(5, new Throwable(), "strConditions :"+ strConditions);
		
		strstoreConditions=" and retailer_name='"+strstore_name+"'";
		LogLevel.DEBUG(5, new Throwable(), "strstoreConditions :"+ strstoreConditions);
		
		try
		{
			String strSqlQry ="select ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_address,retailer_name,retailer_mobilnumber,retailer_emailis,state,city,area,pincode,bat_type,battery_brand,battery_model,battery_capacity,quantity,veh_name,veh_model,MRP_Price,price,witholdbatprice,erp_pre_tax,order_type,payment_mode,payment_mode_type,delivery_charge,delivery_mode,order_status,order_reasons,order_agent_comments,CAST(creation_date AS CHAR) as creation_date,CAST(installed_date AS CHAR) as installed_date,CAST(updated_date AS CHAR) as updated_date,CAST(postpone_date AS CHAR) as postponed_date from battery_order_details where "+strConditions+" "+strstoreConditions+" order by creation_date asc";
			
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
			
			Vector MisOrdersVector=qm.executeQuery(strSqlQry);
			session.setAttribute("sesMisOrdersVector",MisOrdersVector);
			LogLevel.DEBUG(5,new Throwable(),"MisOrdersVector:"+MisOrdersVector);
			 
			 
			res.sendRedirect("../jsp/batterywalestore/mis/misorderdetails.jsp");
			
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
	* This method is to fetch battery details details.
	* @strSqlQry : carries the query to select battery details from battery_details table.
	* @strSqlQry1 : Query has multiple pages to select battery details from battery_details table.
	\* **************************************************************************************************************************************/
	public String inverterorderdetails(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		String strConditions="";
		
		String strfromdate  = (req.getParameter("fromdate")!=null)?req.getParameter("fromdate"):"";
		LogLevel.DEBUG(5, new Throwable(), "strfromdate :"+ strfromdate);
		
		
		String strtodate  = (req.getParameter("todate")!=null)?req.getParameter("todate"):"";
		LogLevel.DEBUG(5, new Throwable(), "strtodate :"+ strtodate);
	  
		strConditions="date(creation_date) between '"+strfromdate+"' and '"+strtodate+"'";
		LogLevel.DEBUG(5, new Throwable(), "strConditions :"+ strConditions);
		
		try
		{
			String strSqlQry ="select order_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_address,retailer_name,retailer_mobile_number,retailer_emailid,state,city,area,pincode,inverter_brand,inverter_model,inverter_capacity,quantity,MRP_Price,price,erp_pre_tax,payment_mode,payment_mode_type,inverter_commission_amount,total_commission_amount,order_status,order_reasons,order_agent_comments,CAST(creation_date AS CHAR) as creation_date,CAST(installed_date AS CHAR) as installed_date,CAST(updated_date AS CHAR) as updated_date,CAST(postpone_date AS CHAR) as postponed_date from inverter_order_details where "+strConditions+" order by creation_date asc";
			
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
			
			Vector MisOrdersVector=qm.executeQuery(strSqlQry);
			session.setAttribute("sesMisOrdersVector",MisOrdersVector);
			LogLevel.DEBUG(5,new Throwable(),"MisOrdersVector:"+MisOrdersVector);
			 
			 
			res.sendRedirect("../jsp/batterywalestore/mis/misinverterdetails.jsp");
			
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
	* This method is to fetch battery details details.
	* @strSqlQry : carries the query to select battery details from battery_details table.
	* @strSqlQry1 : Query has multiple pages to select battery details from battery_details table.
	\* **************************************************************************************************************************************/
	public String serviceorderdetails(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		String strConditions="";
		
		String strfromdate  = (req.getParameter("fromdate")!=null)?req.getParameter("fromdate"):"";
		LogLevel.DEBUG(5, new Throwable(), "strfromdate :"+ strfromdate);
		
		
		String strtodate  = (req.getParameter("todate")!=null)?req.getParameter("todate"):"";
		LogLevel.DEBUG(5, new Throwable(), "strtodate :"+ strtodate);
	  
		strConditions="date(creation_date) between '"+strfromdate+"' and '"+strtodate+"'";
		LogLevel.DEBUG(5, new Throwable(), "strConditions :"+ strConditions);
		
		try
		{
			String strSqlQry ="select ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_address1,retailer_name,retailer_mobilnumber,retailer_emailid,state,city,area,pincode,product_type,veh_name,veh_model,product_capacity,quantity,service_price_mrp,service_price_discount,services_type,services_place,payment_mode,payment_mode_type,service_commission_amount,order_status,order_reasons,order_agent_comments,CAST(creation_date AS CHAR) as creation_date,CAST(installed_date AS CHAR) as installed_date,CAST(updated_date AS CHAR) as updated_date,CAST(postpone_date AS CHAR) as postponed_date from service_order_details where "+strConditions+" order by creation_date asc";
			
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
			
			Vector MisServiceOrdersVector=qm.executeQuery(strSqlQry);
			session.setAttribute("sesMisServiceOrdersVector",MisServiceOrdersVector);
			LogLevel.DEBUG(5,new Throwable(),"MisServiceOrdersVector:"+MisServiceOrdersVector);
			 
			 
			res.sendRedirect("../jsp/batterywalestore/mis/misservicedetails.jsp");
			
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
		* This method is to fetch battery details details.
		* @strSqlQry : carries the query to select battery details from battery_details table.
		* @strSqlQry1 : Query has multiple pages to select battery details from battery_details table.
		\* **************************************************************************************************************************************/
public String trolleyorderdetails(HttpServletRequest req,HttpServletResponse res,HttpSession session)
		{
			String strRes="";
			String strConditions="";
			
			String strfromdate  = (req.getParameter("fromdate")!=null)?req.getParameter("fromdate"):"";
			LogLevel.DEBUG(5, new Throwable(), "strfromdate :"+ strfromdate);
			
			
			String strtodate  = (req.getParameter("todate")!=null)?req.getParameter("todate"):"";
			LogLevel.DEBUG(5, new Throwable(), "strtodate :"+ strtodate);
		  
			strConditions="date(creation_date) between '"+strfromdate+"' and '"+strtodate+"'";
			LogLevel.DEBUG(5, new Throwable(), "strConditions :"+ strConditions);
			
			try
			{
				String strSqlQry ="select ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailid,state,city,area,pincode,trolley_brand,trolley_model,price,order_status,order_reasons,order_agent_comments,quantity,operator,agent_name,payment_mode,payment_mode_type,city_percentage,consumer_address,CAST(creation_date AS CHAR) as creation_date,CAST(installed_date AS CHAR) as installed_date,CAST(updated_date AS CHAR) as updated_date,CAST(postpone_date AS CHAR) as postponed_date from trolley_order_details where "+strConditions+" order by creation_date asc";
				
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
				
				Vector MisTrolleyOrdersVector=qm.executeQuery(strSqlQry);
				session.setAttribute("sesMisTrolleyOrdersVector",MisTrolleyOrdersVector);
				LogLevel.DEBUG(5,new Throwable(),"MisTrolleyOrdersVector:"+MisTrolleyOrdersVector);
				 
				 
				res.sendRedirect("../jsp/batterywalestore/mis/mistrolleydetails.jsp");
				
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
	* This method is to fetch battery details details.
	* @strSqlQry : carries the query to select battery details from battery_details table.
	* @strSqlQry1 : Query has multiple pages to select battery details from battery_details table.
	\* **************************************************************************************************************************************/
	public String misbatterydetails(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		String strConditions="";
		
		String strstoreConditions="";
		String strmisConditions="";
		String strstatusConditions="";
		
		String strfromdate  = (req.getParameter("fromdate")!=null)?req.getParameter("fromdate"):"";
		LogLevel.DEBUG(5, new Throwable(), "strfromdate :"+ strfromdate);
		
		String strtodate  = (req.getParameter("todate")!=null)?req.getParameter("todate"):"";
		LogLevel.DEBUG(5, new Throwable(), "strtodate :"+ strtodate);
		
		String strstore_id  = (req.getParameter("store_id")!=null)?req.getParameter("store_id"):"";
		LogLevel.DEBUG(5, new Throwable(), "strfromdate :"+ strfromdate);
		
		String strstore_name  = (req.getParameter("store_name")!=null)?req.getParameter("store_name"):"";
		LogLevel.DEBUG(5, new Throwable(), "strstore_name :"+ strstore_name);
		
		String strbrand_type  = (req.getParameter("brand_type")!=null)?req.getParameter("brand_type"):"";
		LogLevel.DEBUG(5, new Throwable(), "strbrand_type :"+ strbrand_type);
		
		String strbattery_type  = (req.getParameter("battery_type")!=null)?req.getParameter("battery_type"):"";
		LogLevel.DEBUG(5, new Throwable(), "strbattery_type :"+ strbattery_type);
		
		String strpayment_type  = (req.getParameter("payment_type")!=null)?req.getParameter("payment_type"):"";
		LogLevel.DEBUG(5, new Throwable(), "strpayment_type :"+ strpayment_type);
		
		String strorder_status  = (req.getParameter("order_status")!=null)?req.getParameter("order_status"):"";
		LogLevel.DEBUG(5, new Throwable(), "strorder_status :"+ strorder_status);
		
		
		
		strstoreConditions=" and retailer_name='"+strstore_name+"'";
		LogLevel.DEBUG(5, new Throwable(), "strstoreConditions :"+ strstoreConditions);
		
		if(strorder_status.equals("total")) {
			
			strstatusConditions ="";
			strConditions="date(creation_date) between '"+strfromdate+"' and '"+strtodate+"'";
			LogLevel.DEBUG(5, new Throwable(), "strConditions :"+ strConditions);
			
			
		} else if(strorder_status.equals("installed") || strorder_status.equals("In Process")) {
			
			strstatusConditions =" and order_reasons='"+strorder_status+"'";
			strConditions="date(updated_date) between '"+strfromdate+"' and '"+strtodate+"'";
			LogLevel.DEBUG(5, new Throwable(), "strConditions :"+ strConditions);
			
		} else {
			
			strstatusConditions =" and order_status='"+strorder_status+"'";
			strConditions="date(creation_date) between '"+strfromdate+"' and '"+strtodate+"'";
			LogLevel.DEBUG(5, new Throwable(), "strConditions :"+ strConditions);
		}
		if(strbrand_type.equals("All") && strbattery_type.equals("All") && strpayment_type.equals("All")) {
			
			strmisConditions ="";
						
		} else if(strbrand_type.equals("") && strbattery_type.equals("") && strpayment_type.equals("")) {
			
			strmisConditions ="";
			
		} else if(strbrand_type.equals("All") && !strbattery_type.equals("All") && !strpayment_type.equals("All")) {
			
			LogLevel.DEBUG(5, new Throwable(), "Madhan :"+ strbattery_type);
			LogLevel.DEBUG(5, new Throwable(), "Madhan :"+ strpayment_type);
			if(strbattery_type.equals("") && strpayment_type.equals("")) {
				
				strmisConditions ="";
				LogLevel.DEBUG(5, new Throwable(), "Madhan :"+ strmisConditions);
			} else if(strbattery_type.equals("")) {
				
				strmisConditions =" and payment_mode='"+strpayment_type+"'";

			} else if(strpayment_type.equals("")) {
				
				strmisConditions =" and bat_type='"+strbattery_type+"'";

			} else {
				
				strmisConditions =" and bat_type='"+strbattery_type+"' and payment_mode='"+strpayment_type+"'";
				LogLevel.DEBUG(5, new Throwable(), "Madhan else:"+ strmisConditions);
			}
			
		} else if(strbrand_type.equals("All") && strbattery_type.equals("All") && !strpayment_type.equals("All")) {
			
			if(strpayment_type.equals("")) {
				
				strmisConditions ="";

			} else {
				
				strmisConditions =" and payment_mode='"+strpayment_type+"'";

			}
			
		} else if(!strbrand_type.equals("All") && strbattery_type.equals("All") && strpayment_type.equals("All")) {
			
			if(strbrand_type.equals("")) {
				
				strmisConditions ="";

			} else {
				
				strmisConditions =" and battery_brand='"+strbrand_type+"'";

			}
			
		} else if(!strbrand_type.equals("All") && !strbattery_type.equals("All") && strpayment_type.equals("All")) {
			
			if(strbrand_type.equals("") && strbattery_type.equals("")) {
				
				strmisConditions ="";
				
			} else if(strbrand_type.equals("")) {
				
				strmisConditions ="  and bat_type='"+strbattery_type+"'";

			} else if(strbattery_type.equals("")) {
				
				strmisConditions =" and battery_brand='"+strbrand_type+"'";

			} else {
				
				strmisConditions =" and battery_brand='"+strbrand_type+"' and bat_type='"+strbattery_type+"'";
			}
			
			
		} else if(!strbrand_type.equals("All") && strbattery_type.equals("All") && !strpayment_type.equals("All")) {
			
			
			if(strbrand_type.equals("") && strpayment_type.equals("")) {
				
				strmisConditions ="";
				
			} else if(strbrand_type.equals("")) {
				
				strmisConditions =" and payment_mode='"+strpayment_type+"'";

			} else if(strpayment_type.equals("")) {
				
				strmisConditions =" and battery_brand='"+strbrand_type+"'";

			} else {
				
				strmisConditions =" and battery_brand='"+strbrand_type+"' and payment_mode='"+strpayment_type+"'";
			}
			
			
		} else if(!strbrand_type.equals("All") && !strbattery_type.equals("All") && !strpayment_type.equals("All")) {
							
			if(!strbrand_type.equals("All") && strbattery_type.equals("") && strpayment_type.equals("")) {
				
				strmisConditions =" and battery_brand='"+strbrand_type+"' ";
				
				LogLevel.DEBUG(5, new Throwable(), "Madhan Prasanna:"+ strmisConditions);
				
			} else if(strbattery_type.equals("")) {
				
				strmisConditions =" and payment_mode='"+strpayment_type+"'";

			} else if(strpayment_type.equals("")) {
				
				strmisConditions =" and bat_type='"+strbattery_type+"'";

			} else {
				
				strmisConditions =" and battery_brand='"+strbrand_type+"'  and bat_type='"+strbattery_type+"' and payment_mode='"+strpayment_type+"'";
				LogLevel.DEBUG(5, new Throwable(), "Madhan Prasanna else:"+ strmisConditions);
			}	
			
			//strmisConditions =" and battery_brand='"+strbrand_type+"'  and bat_type='"+strbattery_type+"' and payment_mode='"+strpayment_type+"'";
		 
		}  else {
			
			strmisConditions ="";
		}
		LogLevel.DEBUG(5, new Throwable(), "strmisConditions :"+ strmisConditions);
		
		try
		{
			String strSqlQry ="select ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_address,retailer_name,retailer_mobilnumber,retailer_emailis,state,city,area,pincode,bat_type,battery_brand,battery_model,battery_capacity,quantity,veh_name,veh_model,MRP_Price,price,witholdbatprice,erp_pre_tax,order_type,payment_mode,payment_mode_type,delivery_charge,delivery_mode,order_status,order_reasons,order_agent_comments,CAST(creation_date AS CHAR) as creation_date,CAST(installed_date AS CHAR) as installed_date,CAST(updated_date AS CHAR) as updated_date,CAST(postpone_date AS CHAR) as postponed_date from inverter_order_details where "+strConditions+" "+strstoreConditions+" "+strmisConditions+" "+strstatusConditions+" order by creation_date asc";
			
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
			
			Vector MisOrdersVector=qm.executeQuery(strSqlQry);
			session.setAttribute("sesMisOrdersVector",MisOrdersVector);
			LogLevel.DEBUG(5,new Throwable(),"MisOrdersVector:"+MisOrdersVector);
			 
			 
			res.sendRedirect("../jsp/batterywalestore/dashboard/misdetails.jsp");
			
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
	* This method is to fetch battery details details.
	* @strSqlQry : carries the query to select battery details from battery_details table.
	* @strSqlQry1 : Query has multiple pages to select battery details from battery_details table.
	\* **************************************************************************************************************************************/
	public String misinverterdetails(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		String strConditions="";
		
		String strstoreConditions="";
		String strmisConditions="";
		String strstatusConditions="";
		
		String strfromdate  = (req.getParameter("fromdate")!=null)?req.getParameter("fromdate"):"";
		LogLevel.DEBUG(5, new Throwable(), "strfromdate :"+ strfromdate);
		
		String strtodate  = (req.getParameter("todate")!=null)?req.getParameter("todate"):"";
		LogLevel.DEBUG(5, new Throwable(), "strtodate :"+ strtodate);
		
		String strstore_id  = (req.getParameter("store_id")!=null)?req.getParameter("store_id"):"";
		LogLevel.DEBUG(5, new Throwable(), "strfromdate :"+ strfromdate);
		
		String strstore_name  = (req.getParameter("store_name")!=null)?req.getParameter("store_name"):"";
		LogLevel.DEBUG(5, new Throwable(), "strstore_name :"+ strstore_name);
		
		String strbrand_type  = (req.getParameter("brand_type")!=null)?req.getParameter("brand_type"):"";
		LogLevel.DEBUG(5, new Throwable(), "strbrand_type :"+ strbrand_type);
		  
		String strpayment_type  = (req.getParameter("payment_type")!=null)?req.getParameter("payment_type"):"";
		LogLevel.DEBUG(5, new Throwable(), "strpayment_type :"+ strpayment_type);
		
		String strorder_status  = (req.getParameter("order_status")!=null)?req.getParameter("order_status"):"";
		LogLevel.DEBUG(5, new Throwable(), "strorder_status :"+ strorder_status);
		
		
		
		strstoreConditions=" and retailer_name='"+strstore_name+"'";
		LogLevel.DEBUG(5, new Throwable(), "strstoreConditions :"+ strstoreConditions);
		
		if(strorder_status.equals("total")) {
			
			strstatusConditions ="";
			strConditions="date(creation_date) between '"+strfromdate+"' and '"+strtodate+"'";
			LogLevel.DEBUG(5, new Throwable(), "strConditions :"+ strConditions);
			
			
		} else if(strorder_status.equals("installed") || strorder_status.equals("In Process")) {
			
			strstatusConditions =" and order_reasons='"+strorder_status+"'";
			strConditions="date(updated_date) between '"+strfromdate+"' and '"+strtodate+"'";
			LogLevel.DEBUG(5, new Throwable(), "strConditions :"+ strConditions);
			
		} else {
			
			strstatusConditions =" and order_status='"+strorder_status+"'";
			strConditions="date(creation_date) between '"+strfromdate+"' and '"+strtodate+"'";
			LogLevel.DEBUG(5, new Throwable(), "strConditions :"+ strConditions);
		}
		if(strbrand_type.equals("All")  && strpayment_type.equals("All")) {
				
				strmisConditions ="";
							
		} else if(strbrand_type.equals("") && strpayment_type.equals("")) {
			
				strmisConditions ="";
			
		} else if(strbrand_type.equals("All") && !strpayment_type.equals("All")) {
			
			if(strpayment_type.equals("")) {
					
					strmisConditions ="";
	
				} else {
					
					strmisConditions =" and payment_mode='"+strpayment_type+"'";	
	
				}
				 
		} else if(!strbrand_type.equals("All") && strpayment_type.equals("All")) {
			
			if(strbrand_type.equals("")) {
					
					strmisConditions ="";
	
				} else {
					
					strmisConditions =" and battery_brand='"+strbrand_type+"'";	
	
				}
			
				
		} else if(!strbrand_type.equals("All") && !strpayment_type.equals("All")) {
			
				if(strbrand_type.equals("")) {
					
					strmisConditions =" and payment_mode='"+strpayment_type+"'";
	
				} else if(strpayment_type.equals("")) {
					
					strmisConditions =" and battery_brand='"+strbrand_type+"'";
	
				} else {
					
					strmisConditions =" and battery_brand='"+strbrand_type+"' and payment_mode='"+strpayment_type+"'";
				}
			 
		} else {
			
			strmisConditions ="";
		}
		
		LogLevel.DEBUG(5, new Throwable(), "strmisConditions :"+ strmisConditions);
		
		try
		{
			String strSqlQry ="select order_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_verify_code,retailer_name,retailer_mobile_number,retailer_emailid,state,city,inverter_brand,inverter_model,inverter_capacity,price,area,pincode,order_status,operator,confirm_by,postpone_date,creation_date,created_by,updated_date,updated_by,inverter_commission_amount,order_reasons,agent_name,order_agent_comments,installed_date,payment_mode,erp_pre_tax,MRP_Price,city_percentage,total_commission_amount,consumer_address,quantity,payment_mode_type from inverter_order_details where "+strConditions+" "+strstoreConditions+" "+strmisConditions+" "+strstatusConditions+" order by creation_date asc";
			
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
			
			Vector MisInverterVector=qm.executeQuery(strSqlQry);
			session.setAttribute("sesMisInverterVector",MisInverterVector);
			LogLevel.DEBUG(5,new Throwable(),"MisInverterVector:"+MisInverterVector);
		 
			 
			res.sendRedirect("../jsp/batterywalestore/dashboard/misinverterdetails.jsp");
			
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
	* This method is to fetch battery details details.
	* @strSqlQry : carries the query to select battery details from battery_details table.
	* @strSqlQry1 : Query has multiple pages to select battery details from battery_details table.
	\* **************************************************************************************************************************************/
	public String misservicedetails(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		String strConditions="";
		
		String strstoreConditions="";
		String strmisConditions="";
		String strstatusConditions="";
		
		String strfromdate  = (req.getParameter("fromdate")!=null)?req.getParameter("fromdate"):"";
		LogLevel.DEBUG(5, new Throwable(), "strfromdate :"+ strfromdate);
		
		String strtodate  = (req.getParameter("todate")!=null)?req.getParameter("todate"):"";
		LogLevel.DEBUG(5, new Throwable(), "strtodate :"+ strtodate);
		
		String strstore_id  = (req.getParameter("store_id")!=null)?req.getParameter("store_id"):"";
		LogLevel.DEBUG(5, new Throwable(), "strfromdate :"+ strfromdate);
		
		String strstore_name  = (req.getParameter("store_name")!=null)?req.getParameter("store_name"):"";
		LogLevel.DEBUG(5, new Throwable(), "strstore_name :"+ strstore_name);
 		
		String strbattery_type  = (req.getParameter("battery_type")!=null)?req.getParameter("battery_type"):"";
		LogLevel.DEBUG(5, new Throwable(), "strbattery_type :"+ strbattery_type);
		
		String strpayment_type  = (req.getParameter("payment_type")!=null)?req.getParameter("payment_type"):"";
		LogLevel.DEBUG(5, new Throwable(), "strpayment_type :"+ strpayment_type);
		
		String strorder_status  = (req.getParameter("order_status")!=null)?req.getParameter("order_status"):"";
		LogLevel.DEBUG(5, new Throwable(), "strorder_status :"+ strorder_status);
		
		
		
		strstoreConditions=" and retailer_name='"+strstore_name+"'";
		LogLevel.DEBUG(5, new Throwable(), "strstoreConditions :"+ strstoreConditions);
		
		if(strorder_status.equals("total")) {
			
			strstatusConditions ="";
			strConditions="date(creation_date) between '"+strfromdate+"' and '"+strtodate+"'";
			LogLevel.DEBUG(5, new Throwable(), "strConditions :"+ strConditions);
			
			
		} else if(strorder_status.equals("installed") || strorder_status.equals("In Process")) {
			
			strstatusConditions =" and order_reasons='"+strorder_status+"'";
			strConditions="date(updated_date) between '"+strfromdate+"' and '"+strtodate+"'";
			LogLevel.DEBUG(5, new Throwable(), "strConditions :"+ strConditions);
			
		} else {
			
			strstatusConditions =" and order_status='"+strorder_status+"'";
			strConditions="date(creation_date) between '"+strfromdate+"' and '"+strtodate+"'";
			LogLevel.DEBUG(5, new Throwable(), "strConditions :"+ strConditions);
		}
		
		LogLevel.DEBUG(5, new Throwable(), "strmisConditions :"+ strmisConditions);
		
		try
		{
			String strSqlQry ="select ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailid,city,services_type,services_place,services_package,service_price_mrp,service_price_discount,creation_date,created_by,updated_date,updated_by,area,pincode,veh_name,veh_model,pdfurl,order_status,operator,postpone_date,service_commission_amount,order_reasons,agent_name,order_agent_comments,installed_date,consumer_address1,consumer_address2,state,payment_mode,product_type,product_capacity,quantity,order_type  from service_order_details where "+strConditions+" "+strstoreConditions+" "+strmisConditions+" "+strstatusConditions+" order by creation_date asc";
			
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
			
			Vector MisOrdersVector=qm.executeQuery(strSqlQry);
			session.setAttribute("sesMisOrdersVector",MisOrdersVector);
			LogLevel.DEBUG(5,new Throwable(),"MisOrdersVector:"+MisOrdersVector);
			 
			 
			res.sendRedirect("../jsp/batterywalestore/dashboard/misservicedetails.jsp");
			
			return strRes;
			
		}
		catch (Exception e)
		{
			LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
			e.printStackTrace();
		}
		
		return strRes;
				
			 
	} 
	
 }