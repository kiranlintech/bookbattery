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
 * @author Prasanna Kumari.
 */
/* This class is to Admin Login. This class initializes the necessary connection pools and perform the transactions on the pools. */
public class BatterystoreInventoryDetails extends HttpServlet 
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
		if(strWhatToDo.equalsIgnoreCase("addinventory"))
		{ 
			try
			{
				String strRes=addinventory(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("deleteinventory"))
		{ 
			try
			{
				String strRes=deleteinventory(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("getinventoryreport"))
		{ 
			try
			{
				String strRes=getinventoryreport(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("getsalesreport"))
		{ 
			try
			{
				String strRes=getsalesreport(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("getsalesinverterreport"))
		{ 
			try
			{
				String strRes=getsalesinverterreport(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("getsalesservicesreport"))
		{ 
			try
			{
				String strRes=getsalesservicesreport(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("gettrolleybrands"))
		{ 
			try
			{
				String strRes=gettrolleybrands(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("gettrolleymodels"))
		{ 
			try
			{
				String strRes=gettrolleymodels(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("addtrolleyinventory"))
		{ 
			try
			{
				String strRes=addtrolleyinventory(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("deductedtrolleyinventory"))
		{ 
			try
			{
				String strRes=deductedtrolleyinventory(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("getinverterbrand"))
		{ 
			try
			{
				String strRes=getinverterbrand(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("getinvertercapacity"))
		{ 
			try
			{
				String strRes=getinvertercapacity(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("getinvertermodels"))
		{ 
			try
			{
				String strRes=getinvertermodels(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("addinverterinventory"))
		{ 
			try
			{
				String strRes=addinverterinventory(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("deleteinverterinventory"))
		{ 
			try
			{
				String strRes=deleteinverterinventory(req,res,session);
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
		
			
	}
	
	/* **************************************************************************************************************************************\
	* This method is to fetch battery details details.
	* @strSqlQry : carries the query to select battery details from battery_details table.
	* @strSqlQry1 : Query has multiple pages to select battery details from battery_details table.
	\* **************************************************************************************************************************************/
	public String addinventory(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		try
		{	
			String strbattype= (req.getParameter("battery_type")!=null)?(req.getParameter("battery_type")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbattype:"+strbattype );

			String strbatterybrand= (req.getParameter("battery_brand")!=null)?(req.getParameter("battery_brand")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatterybrand:"+strbatterybrand );
			
			String strbattery_name= (req.getParameter("battery_name")!=null)?(req.getParameter("battery_name")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbattery_name:"+strbattery_name );
			
			String strbatterymodel= (req.getParameter("battery_model")!=null)?(req.getParameter("battery_model")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatterymodel:"+strbatterymodel );

			String strquantity = (req.getParameter("quantity")!=null)?(req.getParameter("quantity")):"";
			LogLevel.DEBUG(5,new Throwable(),"strquantity:"+strquantity );

			String strstorename= (req.getParameter("storename")!=null)?(req.getParameter("storename")):"";
			LogLevel.DEBUG(5,new Throwable(),"strstorename:"+strstorename );

			String strstoreid= (req.getParameter("storeid")!=null)?(req.getParameter("storeid")):"";
			LogLevel.DEBUG(5,new Throwable(),"strstoreid:"+strstoreid );

			String strSqlQry="";
			 
			strSqlQry = "insert into batterystore_inventorydetails(battery_type,battery_brand,battery_name,battery_model,battery_quantity,inventory_type,store_id,store_name)values('"+strbattype+"','"+strbatterybrand+"','"+strbattery_name+"','"+strbatterymodel+"','"+strquantity+"','Add','"+strstoreid+"','"+strstorename+"')";

		 	
			
			LogLevel.DEBUG(5,new Throwable()," insert strSqlQry: "+strSqlQry);	
			
			int reslt = qm.executeUpdate(strSqlQry);
			LogLevel.DEBUG(5,new Throwable()," reslt: "+reslt);	
			
			if(reslt>0) {
				
				strRes="Sucessfully Inserted";
				
			} else {
				
				strRes="Failed to Insert";
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
	* This method is to fetch battery details details.
	* @strSqlQry : carries the query to select battery details from battery_details table.
	* @strSqlQry1 : Query has multiple pages to select battery details from battery_details table.
	\* **************************************************************************************************************************************/
	public String deleteinventory(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		try
		{	
			String strbattype= (req.getParameter("battery_type")!=null)?(req.getParameter("battery_type")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbattype:"+strbattype );

			String strbatterybrand= (req.getParameter("battery_brand")!=null)?(req.getParameter("battery_brand")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatterybrand:"+strbatterybrand );
			
			String strbatteryname= (req.getParameter("battery_name")!=null)?(req.getParameter("battery_name")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatteryname:"+strbatteryname );
			
			String strbatterymodel= (req.getParameter("battery_model")!=null)?(req.getParameter("battery_model")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatterymodel:"+strbatterymodel );

			String strquantity = (req.getParameter("quantity")!=null)?(req.getParameter("quantity")):"";
			LogLevel.DEBUG(5,new Throwable(),"strquantity:"+strquantity );

			String strstorename= (req.getParameter("storename")!=null)?(req.getParameter("storename")):"";
			LogLevel.DEBUG(5,new Throwable(),"strstorename:"+strstorename );

			String strstoreid= (req.getParameter("storeid")!=null)?(req.getParameter("storeid")):"";
			LogLevel.DEBUG(5,new Throwable(),"strstoreid:"+strstoreid );

			String strSqlQry="";
			 
			strSqlQry = "insert into batterystore_inventorydetails(battery_type,battery_brand,battery_name,battery_model,battery_quantity,inventory_type,store_id,store_name)values('"+strbattype+"','"+strbatterybrand+"','"+strbatteryname+"','"+strbatterymodel+"','"+strquantity+"','Delete','"+strstoreid+"','"+strstorename+"')";

		 	
			
			LogLevel.DEBUG(5,new Throwable()," insert strSqlQry: "+strSqlQry);	
			
			int reslt = qm.executeUpdate(strSqlQry);
			LogLevel.DEBUG(5,new Throwable()," reslt: "+reslt);	
			
			if(reslt>0) {
				
				strRes="Sucessfully Inserted";
				
			} else {
				
				strRes="Failed to Insert";
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
	* This method is to fetch battery details details.
	* @strSqlQry : carries the query to select battery details from battery_details table.
	* @strSqlQry1 : Query has multiple pages to select battery details from battery_details table.
	\* **************************************************************************************************************************************/
	public String getinventoryreport(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		String strConditions="";
		
		String strstoreConditions="";
		 
		String strfromdate  = (req.getParameter("fromdate")!=null)?req.getParameter("fromdate"):"";
		LogLevel.DEBUG(5, new Throwable(), "strfromdate :"+ strfromdate);
		
		String strtodate  = (req.getParameter("todate")!=null)?req.getParameter("todate"):"";
		LogLevel.DEBUG(5, new Throwable(), "strtodate :"+ strtodate);
		
		String strstore_name  = (req.getParameter("store_name")!=null)?req.getParameter("store_name"):"";
		LogLevel.DEBUG(5, new Throwable(), "strstore_name :"+ strstore_name);
		
		strConditions="date(creation_date) between '"+strfromdate+"' and '"+strtodate+"'  and store_name='"+strstore_name+"' ";
		LogLevel.DEBUG(5, new Throwable(), "strConditions :"+ strConditions);
			
		 
 		try
		
 		{
			
 			//String strSqlQry ="select battery_type,battery_brand,battery_capacity,battery_model,battery_quantity,date(creation_date, %d-%m-%Y) as Date, count(*) AS total, sum(case when inventory_type = 'Add' then 1 else 0 end) AS AddedCount,sum(case when inventory_type = 'Delete' then 1 else 0 end) AS DeleteCount from batterystore_inventorydetails where "+strConditions+" GROUP BY battery_model order by creation_date asc";
 			
 			String strSqlQry ="select battery_type,battery_brand,battery_name,battery_model,battery_quantity,date_format(creation_date, '%d-%m-%Y') as Date, count(*) AS total, sum(case when inventory_type = 'Add' then 1 else 0 end) AS AddedCount,sum(case when inventory_type = 'Delete' then 1 else 0 end) AS DeleteCount from batterystore_inventorydetails where "+strConditions+" GROUP BY battery_model order by creation_date asc";
			
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
			
			Vector MisOrdersVector=qm.executeQuery(strSqlQry);
			session.setAttribute("sesMisOrdersVector",MisOrdersVector);
			LogLevel.DEBUG(5,new Throwable(),"MisOrdersVector:"+MisOrdersVector);
			 
			 
			res.sendRedirect("../jsp/batterywalestore/inventory/inventorydetails.jsp");
			
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
	public String getsalesreport(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		String strConditions="";
		
		String strbrandConditions="";
		 
		String strfromdate  = (req.getParameter("fromdate")!=null)?req.getParameter("fromdate"):"";
		LogLevel.DEBUG(5, new Throwable(), "strfromdate :"+ strfromdate);
		
		String strtodate  = (req.getParameter("todate")!=null)?req.getParameter("todate"):"";
		LogLevel.DEBUG(5, new Throwable(), "strtodate :"+ strtodate);
		
		String strbrand_type  = (req.getParameter("brand_type")!=null)?req.getParameter("brand_type"):"";
		LogLevel.DEBUG(5, new Throwable(), "strbrand_type :"+ strbrand_type);
		
		String strstore_name  = (req.getParameter("store_name")!=null)?req.getParameter("store_name"):"";
		LogLevel.DEBUG(5, new Throwable(), "strstore_name :"+ strstore_name);
		
		strConditions="date(installed_date) between '"+strfromdate+"' and '"+strtodate+"'  and retailer_name='"+strstore_name+"' ";
		LogLevel.DEBUG(5, new Throwable(), "strConditions :"+ strConditions);
			
		if((strbrand_type.equals("All")) || (strbrand_type.equals(""))) {
			
			strbrandConditions = "";
			
		} else {
			
			strbrandConditions = " and battery_brand='"+strbrand_type+"'";
		}
		
		
 		try
		
 		{
			
 			 //String strSqlQry ="select a.consumer_name,a.price,a.witholdbatprice,(((Case a.order_type WHEN 'New' THEN price ELSE a.witholdbatprice END)*a.quantity)+a.delivery_charge) as amount_paid_customer,a.delivery_charge,a.order_type,a.quantity,a.payment_mode,a.battery_model,a.battery_brand,a.bat_type,date(a.installed_date) as installed_date,a.state,b.city,b.erp_pre_tax from battery_order_details a, batteryprice b where "+strConditions+" "+strbrandConditions+" and a.battery_model=b.bat_model and a.city=b.city order by date(a.installed_date) asc";
 			String strSqlQry ="select consumer_name,price,witholdbatprice,(((Case order_type WHEN 'New' THEN price ELSE witholdbatprice END)*quantity)+delivery_charge) as amount_paid_customer,delivery_charge,order_type,quantity,battery_capacity,payment_mode,battery_model,battery_brand,bat_type,date_format(installed_date, '%d-%m-%Y') as installed_date,state,city,erp_pre_tax from battery_order_details where "+strConditions+" "+strbrandConditions+" order by date(installed_date) asc";
 			
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
			
			Vector MisOrdersVector=qm.executeQuery(strSqlQry);
			session.setAttribute("sesMisOrdersVector",MisOrdersVector);
			LogLevel.DEBUG(5,new Throwable(),"MisOrdersVector:"+MisOrdersVector);
			 
			 
			res.sendRedirect("../jsp/batterywalestore/mis/batterysalesdetails.jsp");
			
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
	public String getsalesinverterreport(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		String strConditions="";
		
		String strbrandConditions="";
		 
		String strfromdate  = (req.getParameter("fromdate")!=null)?req.getParameter("fromdate"):"";
		LogLevel.DEBUG(5, new Throwable(), "strfromdate :"+ strfromdate);
		
		String strtodate  = (req.getParameter("todate")!=null)?req.getParameter("todate"):"";
		LogLevel.DEBUG(5, new Throwable(), "strtodate :"+ strtodate);
		
		String strbrand_type  = (req.getParameter("brand_type")!=null)?req.getParameter("brand_type"):"";
		LogLevel.DEBUG(5, new Throwable(), "strbrand_type :"+ strbrand_type);
		
		String strstore_name  = (req.getParameter("store_name")!=null)?req.getParameter("store_name"):"";
		LogLevel.DEBUG(5, new Throwable(), "strstore_name :"+ strstore_name);
		
		strConditions="date(installed_date) between '"+strfromdate+"' and '"+strtodate+"'  and retailer_name='"+strstore_name+"' ";
		LogLevel.DEBUG(5, new Throwable(), "strConditions :"+ strConditions);
			
		if((strbrand_type.equals("All")) || (strbrand_type.equals(""))) {
			
			strbrandConditions = "";
			
		} else {
			
			strbrandConditions = " and inverter_brand='"+strbrand_type+"'";
		}
		
		
 		try
		
 		{
			
 			 String strSqlQry ="select consumer_name,price,((price)*quantity) as amount_paid_customer,quantity,payment_mode,inverter_model,inverter_brand,inverter_capacity,date_format(installed_date, '%d-%m-%Y') as installed_date,state,city,erp_pre_tax from inverter_order_details where "+strConditions+" "+strbrandConditions+"  order by date(installed_date) asc";
			
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
			
			Vector SalesInverterVector=qm.executeQuery(strSqlQry);
			session.setAttribute("sesSalesInverterVector",SalesInverterVector);
			LogLevel.DEBUG(5,new Throwable(),"SalesInverterVector"+SalesInverterVector);
			 
			 
			res.sendRedirect("../jsp/batterywalestore/mis/invertersalesdetails.jsp");
			
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
	public String getsalesservicesreport(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		String strConditions="";
		
		String strbrandConditions="";
		 
		String strfromdate  = (req.getParameter("fromdate")!=null)?req.getParameter("fromdate"):"";
		LogLevel.DEBUG(5, new Throwable(), "strfromdate :"+ strfromdate);
		
		String strtodate  = (req.getParameter("todate")!=null)?req.getParameter("todate"):"";
		LogLevel.DEBUG(5, new Throwable(), "strtodate :"+ strtodate);
		
		String strservice_type  = (req.getParameter("service_type")!=null)?req.getParameter("service_type"):"";
		LogLevel.DEBUG(5, new Throwable(), "strservice_type :"+ strservice_type);
		
		String strstore_name  = (req.getParameter("store_name")!=null)?req.getParameter("store_name"):"";
		LogLevel.DEBUG(5, new Throwable(), "strstore_name :"+ strstore_name);
		
		strConditions="date(installed_date) between '"+strfromdate+"' and '"+strtodate+"'  and retailer_name='"+strstore_name+"' ";
		LogLevel.DEBUG(5, new Throwable(), "strConditions :"+ strConditions);
			
		if((strservice_type.equals("All")) || (strservice_type.equals(""))) {
			
			strbrandConditions = "";
			
		} else {
			
			strbrandConditions = " and services_type='"+strservice_type+"'";
		}
		
		
 		try
		
 		{
			
 			 String strSqlQry ="select consumer_name,service_price_discount,((service_price_discount)*quantity) as amount_paid_customer,quantity,payment_mode,product_type,services_type,date_format(installed_date, '%d-%m-%Y') as installed_date,state,city from service_order_details where "+strConditions+" "+strbrandConditions+"  order by date(installed_date) asc";
			
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
			
			Vector SalesServiceVector=qm.executeQuery(strSqlQry);
			session.setAttribute("sesSalesServiceVector",SalesServiceVector);
			LogLevel.DEBUG(5,new Throwable(),"SalesServiceVector"+SalesServiceVector);
			 
			 
			res.sendRedirect("../jsp/batterywalestore/mis/servicesalesdetails.jsp");
			
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
	* This method is to fetch battery brand.
	* @strSqlQry : carries the query to battery brand from battery_details table.
	\* **************************************************************************************************************************************/
	public String gettrolleybrands(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String trolley_brand="";		
			String strSqlQry ="";	
	 		ServletOutputStream out=res.getOutputStream();

			
				strSqlQry ="select distinct(trolley_brand) from batterystore_trolleydetails order by trolley_brand asc";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
			
			Vector BatteryVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"BatteryVector:"+BatteryVector );

			if(BatteryVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='0'>&nbsp;Select&nbsp;Brand</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<BatteryVector.size(); i++)
					{
						
						if(i==0)
						{
							out.print("<option value='0' selected>&nbsp;Select&nbsp;Brand</option>");
						}
						
						Hashtable ht1=(Hashtable)BatteryVector.get(i);
						//bat_id =String.valueOf(ht1.get("bat_id"));
						trolley_brand =String.valueOf(ht1.get("trolley_brand"));
						out.print("<option  value='"+trolley_brand+"'>"+trolley_brand+"</option>"); 
					}
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
	* This method is to fetch battery brand.
	* @strSqlQry : carries the query to battery brand from battery_details table.
	\* **************************************************************************************************************************************/
	public String gettrolleymodels(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	
		String strRes="";
	
		String strtrolleybrand= (req.getParameter("trolleybrand")!=null)?(req.getParameter("trolleybrand")):"";
		LogLevel.DEBUG(5, new Throwable(), "strtrolleybrand :" + strtrolleybrand);
	
		try
		{	
			String trolley_model="";		
			String strSqlQry ="";	
	 		ServletOutputStream out=res.getOutputStream();

			
				strSqlQry ="select distinct(trolley_model) from batterystore_trolleydetails  where trolley_brand='"+strtrolleybrand+"' order by trolley_model asc";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
			
			Vector BatteryVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"BatteryVector:"+BatteryVector );

			if(BatteryVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='0'>&nbsp;Select&nbsp;Model</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<BatteryVector.size(); i++)
					{
						
						if(i==0)
						{
							out.print("<option value='0' selected>&nbsp;Select&nbsp;Model</option>");
						}
						
						Hashtable ht1=(Hashtable)BatteryVector.get(i);
						//bat_id =String.valueOf(ht1.get("bat_id"));
						trolley_model =String.valueOf(ht1.get("trolley_model"));
						out.print("<option  value='"+trolley_model+"'>"+trolley_model+"</option>"); 
					}
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
	* This method is to fetch battery details details.
	* @strSqlQry : carries the query to select battery details from battery_details table.
	* @strSqlQry1 : Query has multiple pages to select battery details from battery_details table.
	\* **************************************************************************************************************************************/
	public String addtrolleyinventory(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		try
		{	
			String strtrolley_brand= (req.getParameter("trolley_brand")!=null)?(req.getParameter("trolley_brand")):"";
			LogLevel.DEBUG(5,new Throwable(),"strtrolley_brand:"+strtrolley_brand );

			String strtrolley_model= (req.getParameter("trolley_model")!=null)?(req.getParameter("trolley_model")):"";
			LogLevel.DEBUG(5,new Throwable(),"strtrolley_model:"+strtrolley_model );
			
			String strquantity = (req.getParameter("quantity")!=null)?(req.getParameter("quantity")):"";
			LogLevel.DEBUG(5,new Throwable(),"strquantity:"+strquantity );

			String strstorename= (req.getParameter("storename")!=null)?(req.getParameter("storename")):"";
			LogLevel.DEBUG(5,new Throwable(),"strstorename:"+strstorename );

			String strstoreid= (req.getParameter("storeid")!=null)?(req.getParameter("storeid")):"";
			LogLevel.DEBUG(5,new Throwable(),"strstoreid:"+strstoreid );

			String strSqlQry="";
			 
			//strSqlQry = "insert into batterystore_trolleyinventory(trolley_brand,trolley_model,trolley_quantity,inventory_type,store_id,store_name)values('"+strtrolley_brand+"','"+strtrolley_model+"','"+strquantity+"','Add','"+strstoreid+"','"+strstorename+"')";
			strSqlQry = "insert into batterystore_inventorydetails(battery_type,battery_brand,battery_brand,battery_model,battery_quantity,inventory_type,store_id,store_name)values('Trolley','"+strtrolley_brand+"','0','"+strtrolley_model+"','"+strquantity+"','Add','"+strstoreid+"','"+strstorename+"')";
		 	
			
			LogLevel.DEBUG(5,new Throwable()," insert strSqlQry: "+strSqlQry);	
			
			int reslt = qm.executeUpdate(strSqlQry);
			LogLevel.DEBUG(5,new Throwable()," reslt: "+reslt);	
			
			if(reslt>0) {
				
				strRes="Sucessfully Inserted";
				
			} else {
				
				strRes="Failed to Insert";
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
	* This method is to fetch battery details details.
	* @strSqlQry : carries the query to select battery details from battery_details table.
	* @strSqlQry1 : Query has multiple pages to select battery details from battery_details table.
	\* **************************************************************************************************************************************/
	public String deductedtrolleyinventory(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		try
		{	
			String strtrolley_brand= (req.getParameter("trolley_brand")!=null)?(req.getParameter("trolley_brand")):"";
			LogLevel.DEBUG(5,new Throwable(),"strtrolley_brand:"+strtrolley_brand );

			String strtrolley_model= (req.getParameter("trolley_model")!=null)?(req.getParameter("trolley_model")):"";
			LogLevel.DEBUG(5,new Throwable(),"strtrolley_model:"+strtrolley_model );
			
			String strquantity = (req.getParameter("quantity")!=null)?(req.getParameter("quantity")):"";
			LogLevel.DEBUG(5,new Throwable(),"strquantity:"+strquantity );

			String strstorename= (req.getParameter("storename")!=null)?(req.getParameter("storename")):"";
			LogLevel.DEBUG(5,new Throwable(),"strstorename:"+strstorename );

			String strstoreid= (req.getParameter("storeid")!=null)?(req.getParameter("storeid")):"";
			LogLevel.DEBUG(5,new Throwable(),"strstoreid:"+strstoreid );

			String strSqlQry="";
			 
			//strSqlQry = "insert into batterystore_trolleyinventory(trolley_brand,trolley_model,trolley_quantity,inventory_type,store_id,store_name)values('"+strtrolley_brand+"','"+strtrolley_model+"','"+strquantity+"','Delete','"+strstoreid+"','"+strstorename+"')";
			strSqlQry = "insert into batterystore_inventorydetails(battery_type,battery_brand,battery_name,battery_model,battery_quantity,inventory_type,store_id,store_name)values('Trolley','"+strtrolley_brand+"','0','"+strtrolley_model+"','"+strquantity+"','Delete','"+strstoreid+"','"+strstorename+"')";
		 	
			
			LogLevel.DEBUG(5,new Throwable()," insert strSqlQry: "+strSqlQry);	
			
			int reslt = qm.executeUpdate(strSqlQry);
			LogLevel.DEBUG(5,new Throwable()," reslt: "+reslt);	
			
			if(reslt>0) {
				
				strRes="Sucessfully Inserted";
				
			} else {
				
				strRes="Failed to Insert";
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
	* This method is to get the inverter brands
	* @strinverterbrand : carries the query to get the inverter brands from inverter_details table.
	\* **************************************************************************************************************************************/
	public String getinverterbrand(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
 	String strRes="";
		try
		{	
			
			String inverter_brand="";
			String strinverterbrand="";
	 		ServletOutputStream out=res.getOutputStream();
			 
			strinverterbrand = "select distinct(inverter_brand) from inverter_details order by inverter_brand asc"; 
			LogLevel.DEBUG(5, new Throwable(), "strinverterbrand :" + strinverterbrand);
			  
			Vector BatteryVector=qm.executeQuery(strinverterbrand);
			LogLevel.DEBUG(5,new Throwable(),"inverterbrandvector:"+BatteryVector);
			if(BatteryVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='0'>&nbsp;Select&nbsp;Trolley&nbsp;Model</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<BatteryVector.size(); i++)
					{
						
						if(i==0)
						{
							out.print("<option value='0' selected>&nbsp;Select&nbsp;Inverter&nbsp;Brand</option>");
						}
						
						Hashtable ht1=(Hashtable)BatteryVector.get(i);
						//bat_id =String.valueOf(ht1.get("bat_id"));
						inverter_brand =String.valueOf(ht1.get("inverter_brand"));
						out.print("<option  value='"+inverter_brand+"'>"+inverter_brand+"</option>"); 
					}
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
	* This method is to get the inverter brands
	* @strinverterbrand : carries the query to get the inverter brands from inverter_details table.
	\* **************************************************************************************************************************************/
	public String getinvertercapacity(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
 	String strRes="";
 	
	 	String strinverterbrand= (req.getParameter("inverterbrand")!=null)?(req.getParameter("inverterbrand")):"";
		LogLevel.DEBUG(5,new Throwable(),"strinverterbrand:"+strinverterbrand );
		
		try
		{	
			
			String inverter_capacity="";
			String strinvertercapacity="";
	 		ServletOutputStream out=res.getOutputStream();
			 
			strinverterbrand = "select distinct(inverter_capacity) from inverter_details where inverter_brand='"+strinverterbrand+"' order by inverter_capacity asc"; 
			LogLevel.DEBUG(5, new Throwable(), "strinverterbrand :" + strinverterbrand);
			  
			Vector BatteryVector=qm.executeQuery(strinverterbrand);
			LogLevel.DEBUG(5,new Throwable(),"inverterbrandvector:"+BatteryVector);
			if(BatteryVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='0'>&nbsp;Select&nbsp;Inverter&nbsp;Capacity</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<BatteryVector.size(); i++)
					{
						
						if(i==0)
						{
							out.print("<option value='0' selected>&nbsp;Select&nbsp;Inverter&nbsp;Capacity</option>");
						}
						
						Hashtable ht1=(Hashtable)BatteryVector.get(i);
						//bat_id =String.valueOf(ht1.get("bat_id"));
						inverter_capacity =String.valueOf(ht1.get("inverter_capacity"));
						out.print("<option  value='"+inverter_capacity+"'>"+inverter_capacity+"</option>"); 
					}
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
	* This method is to get the inverter brands
	* @strinverterbrand : carries the query to get the inverter brands from inverter_details table.
	\* **************************************************************************************************************************************/
	public String getinvertermodels(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
 	String strRes="";
 	
	 	String strinverterbrand= (req.getParameter("inverterbrand")!=null)?(req.getParameter("inverterbrand")):"";
		LogLevel.DEBUG(5,new Throwable(),"strinverterbrand:"+strinverterbrand );
		
		String strinvertercapacity= (req.getParameter("invertercapacity")!=null)?(req.getParameter("invertercapacity")):"";
		LogLevel.DEBUG(5,new Throwable(),"strinvertercapacity:"+strinvertercapacity );
		
		
		try
		{	
			
			String inverter_model="";
			String strinvertermodel="";
	 		ServletOutputStream out=res.getOutputStream();
			 
			strinverterbrand = "select distinct(inverter_model) from inverter_details where inverter_brand='"+strinverterbrand+"' and inverter_capacity='"+strinvertercapacity+"' order by inverter_model asc"; 
			LogLevel.DEBUG(5, new Throwable(), "strinverterbrand :" + strinverterbrand);
			  
			Vector BatteryVector=qm.executeQuery(strinverterbrand);
			LogLevel.DEBUG(5,new Throwable(),"inverterbrandvector:"+BatteryVector);
			if(BatteryVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='0'>&nbsp;Select&nbsp;Inverter&nbsp;Model</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<BatteryVector.size(); i++)
					{
						
						if(i==0)
						{
							out.print("<option value='0' selected>&nbsp;Select&nbsp;Inverter&nbsp;Model</option>");
						}
						
						Hashtable ht1=(Hashtable)BatteryVector.get(i);
						//bat_id =String.valueOf(ht1.get("bat_id"));
						inverter_model =String.valueOf(ht1.get("inverter_model"));
						out.print("<option  value='"+inverter_model+"'>"+inverter_model+"</option>"); 
					}
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
	* This method is to fetch battery details details.
	* @strSqlQry : carries the query to select battery details from battery_details table.
	* @strSqlQry1 : Query has multiple pages to select battery details from battery_details table.
	\* **************************************************************************************************************************************/
	public String addinverterinventory(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		try
		{	
			String strinvertertype= "Inverter";
			LogLevel.DEBUG(5,new Throwable(),"strinvertertype:"+strinvertertype );

			String strinverterbrand= (req.getParameter("inverter_brand")!=null)?(req.getParameter("inverter_brand")):"";
			LogLevel.DEBUG(5,new Throwable(),"strinverterbrand:"+strinverterbrand );
			
			String strinvertercapacity= (req.getParameter("inverter_capacity")!=null)?(req.getParameter("inverter_capacity")):"";
			LogLevel.DEBUG(5,new Throwable(),"strinvertercapacity:"+strinvertercapacity );
			
			String strinvertermodel= (req.getParameter("inverter_model")!=null)?(req.getParameter("inverter_model")):"";
			LogLevel.DEBUG(5,new Throwable(),"strinvertermodel:"+strinvertermodel );

			String strquantity = (req.getParameter("quantity")!=null)?(req.getParameter("quantity")):"";
			LogLevel.DEBUG(5,new Throwable(),"strquantity:"+strquantity );

			String strstorename= (req.getParameter("storename")!=null)?(req.getParameter("storename")):"";
			LogLevel.DEBUG(5,new Throwable(),"strstorename:"+strstorename );

			String strstoreid= (req.getParameter("storeid")!=null)?(req.getParameter("storeid")):"";
			LogLevel.DEBUG(5,new Throwable(),"strstoreid:"+strstoreid );

			String strSqlQry="";
			 
			strSqlQry = "insert into batterystore_inventorydetails(battery_type,battery_brand,battery_name,battery_model,battery_quantity,inventory_type,store_id,store_name)values('"+strinvertertype+"','"+strinverterbrand+"','"+strinvertercapacity+"','"+strinvertermodel+"','"+strquantity+"','Add','"+strstoreid+"','"+strstorename+"')";

		 	
			
			LogLevel.DEBUG(5,new Throwable()," insert strSqlQry: "+strSqlQry);	
			
			int reslt = qm.executeUpdate(strSqlQry);
			LogLevel.DEBUG(5,new Throwable()," reslt: "+reslt);	
			
			if(reslt>0) {
				
				strRes="Sucessfully Inserted";
				
			} else {
				
				strRes="Failed to Insert";
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
	* This method is to fetch battery details details.
	* @strSqlQry : carries the query to select battery details from battery_details table.
	* @strSqlQry1 : Query has multiple pages to select battery details from battery_details table.
	\* **************************************************************************************************************************************/
	public String deleteinverterinventory(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		try
		{	
			String strinvertertype= "Inverter";
			LogLevel.DEBUG(5,new Throwable(),"strinvertertype:"+strinvertertype );

			String strinverterbrand= (req.getParameter("inverter_brand")!=null)?(req.getParameter("inverter_brand")):"";
			LogLevel.DEBUG(5,new Throwable(),"strinverterbrand:"+strinverterbrand );
			
			String strinvertercapacity= (req.getParameter("inverter_capacity")!=null)?(req.getParameter("inverter_capacity")):"";
			LogLevel.DEBUG(5,new Throwable(),"strinvertercapacity:"+strinvertercapacity );
			
			String strinvertermodel= (req.getParameter("inverter_model")!=null)?(req.getParameter("inverter_model")):"";
			LogLevel.DEBUG(5,new Throwable(),"strinvertermodel:"+strinvertermodel );

			String strquantity = (req.getParameter("quantity")!=null)?(req.getParameter("quantity")):"";
			LogLevel.DEBUG(5,new Throwable(),"strquantity:"+strquantity );

			String strstorename= (req.getParameter("storename")!=null)?(req.getParameter("storename")):"";
			LogLevel.DEBUG(5,new Throwable(),"strstorename:"+strstorename );

			String strstoreid= (req.getParameter("storeid")!=null)?(req.getParameter("storeid")):"";
			LogLevel.DEBUG(5,new Throwable(),"strstoreid:"+strstoreid );

			String strSqlQry="";
			 
			strSqlQry = "insert into batterystore_inventorydetails(battery_type,battery_brand,battery_name,battery_model,battery_quantity,inventory_type,store_id,store_name)values('"+strinvertertype+"','"+strinverterbrand+"','"+strinvertercapacity+"','"+strinvertermodel+"','"+strquantity+"','Delete','"+strstoreid+"','"+strstorename+"')";

		 	
			
			LogLevel.DEBUG(5,new Throwable()," insert strSqlQry: "+strSqlQry);	
			
			int reslt = qm.executeUpdate(strSqlQry);
			LogLevel.DEBUG(5,new Throwable()," reslt: "+reslt);	
			
			if(reslt>0) {
				
				strRes="Sucessfully Inserted";
				
			} else {
				
				strRes="Failed to Insert";
			}
		}
		catch (Exception e)
		{
			LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
			e.printStackTrace();

		}
		return strRes;
	} 
	
 }