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
public class BatterystoreScrapDetails extends HttpServlet 
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
		if(strWhatToDo.equalsIgnoreCase("insertscrapdetails"))
		{ 
			try
			{
				String strRes=insertscrapdetails(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("changescrapdetails"))
		{ 
			try
			{
				String strRes=changescrapdetails(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("getscrapreport"))
		{ 
			try
			{
				String strRes=getscrapreport(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("getscrapinventoryreport"))
		{ 
			try
			{
				String strRes=getscrapinventoryreport(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("insertreturnbatterydetails"))
		{ 
			try
			{
				String strRes=insertreturnbatterydetails(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("insertexchangebatterydetails"))
		{ 
			try
			{
				String strRes=insertexchangebatterydetails(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("BatteryReturn_Details"))
		{ 
			try
			{
				String strRes=BatteryReturn_Details(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("BatteryExchange_Details"))
		{ 
			try
			{
				String strRes=BatteryExchange_Details(req,res,session);
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
	public String insertscrapdetails(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		try
		{	
			String strbattype= (req.getParameter("battery_type")!=null)?(req.getParameter("battery_type")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbattype:"+strbattype );

			String strbatcapacity= (req.getParameter("battery_capacity")!=null)?(req.getParameter("battery_capacity")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatcapacity:"+strbatcapacity );
			
			String strbatterymodel= (req.getParameter("battery_model")!=null)?(req.getParameter("battery_model")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatterymodel:"+strbatterymodel );

			String strquantity = (req.getParameter("quantity")!=null)?(req.getParameter("quantity")):"";
			LogLevel.DEBUG(5,new Throwable(),"strquantity:"+strquantity );

			String strstorename= (req.getParameter("storename")!=null)?(req.getParameter("storename")):"";
			LogLevel.DEBUG(5,new Throwable(),"strstorename:"+strstorename );

			String strstoreid= (req.getParameter("storeid")!=null)?(req.getParameter("storeid")):"";
			LogLevel.DEBUG(5,new Throwable(),"strstoreid:"+strstoreid );

			String strSqlQry="";
			 
			strSqlQry = "insert into batterystore_scrapdetails(battery_type,battery_capacity,battery_model,battery_quantity,store_id,store_name)values('"+strbattype+"','"+strbatcapacity+"','"+strbatterymodel+"','"+strquantity+"','"+strstoreid+"','"+strstorename+"')";

		 	
			
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
	public String changescrapdetails(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		try
		{	
			String strbattype= (req.getParameter("battery_type")!=null)?(req.getParameter("battery_type")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbattype:"+strbattype );
 
			String strscrapprice = (req.getParameter("scrap_price")!=null)?(req.getParameter("scrap_price")):"";
			LogLevel.DEBUG(5,new Throwable(),"strscrapprice:"+strscrapprice );

			String strstorename= (req.getParameter("storename")!=null)?(req.getParameter("storename")):"";
			LogLevel.DEBUG(5,new Throwable(),"strstorename:"+strstorename );

			String strstoreid= (req.getParameter("storeid")!=null)?(req.getParameter("storeid")):"";
			LogLevel.DEBUG(5,new Throwable(),"strstoreid:"+strstoreid );

			String strSqlQry="";
			
			
			strSqlQry = "update batterystore_scrapprices set price='"+strscrapprice+"' where battery_type='"+strbattype+"'";
				
			
			LogLevel.DEBUG(5,new Throwable()," insert strSqlQry: "+strSqlQry);	
			
			int reslt = qm.executeUpdate(strSqlQry);
			LogLevel.DEBUG(5,new Throwable()," reslt: "+reslt);	
			
			if(reslt>0) {
				
				strRes="Sucessfully Updated";
				
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
	public String getscrapreport(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		String strConditions="";
		
		String strfromdate  = (req.getParameter("fromdate")!=null)?req.getParameter("fromdate"):"";
		LogLevel.DEBUG(5, new Throwable(), "strfromdate :"+ strfromdate);
		
		
		String strtodate  = (req.getParameter("todate")!=null)?req.getParameter("todate"):"";
		LogLevel.DEBUG(5, new Throwable(), "strtodate :"+ strtodate);
	  
		strConditions="date(b.creation_date) between '"+strfromdate+"' and '"+strtodate+"'";
		LogLevel.DEBUG(5, new Throwable(), "strConditions :"+ strConditions);
		
		try
		{	
			
				String strSqlQry = "select a.battery_type,a.battery_capacity,a.battery_weight,sum(b.battery_quantity) as Qty,date_format(b.creation_date, '%d-%m-%Y') as Date,c.battery_type,c.price from batterystore_scrapmodels a, batterystore_scrapdetails b, batterystore_scrapprices c where a.battery_capacity=b.battery_capacity and a.battery_type=c.battery_type and "+strConditions+" group by b.battery_capacity order by a.battery_type,CAST(a.battery_capacity AS SIGNED) asc";
			
				LogLevel.DEBUG(5,new Throwable()," insert strSqlQry: "+strSqlQry);	
				
				 
			//int reslt = qm.executeUpdate(strSqlQry);
			//LogLevel.DEBUG(5,new Throwable()," reslt: "+reslt);	
			
			Vector MisScrapVector=qm.executeQuery(strSqlQry);
			session.setAttribute("sesMisScrapVector",MisScrapVector);
			LogLevel.DEBUG(5,new Throwable(),"MisScrapVector :"+MisScrapVector);
	 
	
			res.sendRedirect("../jsp/batterywalestore/scrapbatteries/getscrapdetails.jsp");
			
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
	public String getscrapinventoryreport(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		String strConditions="";
		
		String strfromdate  = (req.getParameter("fromdate")!=null)?req.getParameter("fromdate"):"";
		LogLevel.DEBUG(5, new Throwable(), "strfromdate :"+ strfromdate);
		
		
		String strtodate  = (req.getParameter("todate")!=null)?req.getParameter("todate"):"";
		LogLevel.DEBUG(5, new Throwable(), "strtodate :"+ strtodate);
		
		String strstore_name  = (req.getParameter("store_name")!=null)?req.getParameter("store_name"):"";
		LogLevel.DEBUG(5, new Throwable(), "strstore_name :"+ strstore_name);
		
		
		strConditions="date(creation_date) between '"+strfromdate+"' and '"+strtodate+"' and store_name='"+strstore_name+"'";
		LogLevel.DEBUG(5, new Throwable(), "strConditions :"+ strConditions);
		
		try
		{	
			
				String strSqlQry = "Select battery_type,battery_capacity,battery_model,sum(battery_quantity) as battery_quantity,date_format(creation_date, '%d-%m-%Y') as Date from batterystore_scrapdetails  where "+strConditions+" GROUP BY battery_model order by creation_date,battery_type asc";
			
				LogLevel.DEBUG(5,new Throwable()," getscrapinventoryreport strSqlQry: "+strSqlQry);	
				
				 
			//int reslt = qm.executeUpdate(strSqlQry);
			//LogLevel.DEBUG(5,new Throwable()," reslt: "+reslt);	
			
			Vector MisScrapVector=qm.executeQuery(strSqlQry);
			session.setAttribute("sesMisScrapVector",MisScrapVector);
			LogLevel.DEBUG(5,new Throwable(),"MisScrapVector :"+MisScrapVector);
	 
	
			res.sendRedirect("../jsp/batterywalestore/scrapbatteries/viewscrapdetails.jsp");
			
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
	public String BatteryReturn_Details(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		String strConditions="";
		String strbrandConditions="";
		
		String strfromdate  = (req.getParameter("FDate")!=null)?req.getParameter("FDate"):"";
		LogLevel.DEBUG(5, new Throwable(), "strfromdate :"+ strfromdate);
		
		String strtodate  = (req.getParameter("TDate")!=null)?req.getParameter("TDate"):"";
		LogLevel.DEBUG(5, new Throwable(), "strtodate :"+ strtodate);
	  
		String strbrand  = (req.getParameter("Brand")!=null)?req.getParameter("Brand"):"";
		LogLevel.DEBUG(5, new Throwable(), "strbrand :"+ strbrand);
		
		String strSID  = (req.getParameter("SID")!=null)?req.getParameter("SID"):"";
		LogLevel.DEBUG(5, new Throwable(), "strSID :"+ strSID);

		String strSNAME  = (req.getParameter("SNAME")!=null)?req.getParameter("SNAME"):"";
		LogLevel.DEBUG(5, new Throwable(), "strSNAME :"+ strSNAME);
		 
		strConditions="date(creation_date) between '"+strfromdate+"' and '"+strtodate+"'";
		LogLevel.DEBUG(5, new Throwable(), "strConditions :"+ strConditions);
		
		if((strbrand.equals("All")) || (strbrand.equals(""))) {
			
			strbrandConditions = "";
			
		} else {
			
			strbrandConditions = " and battery_brand='"+strbrand+"'";
		}
		
		
		
		try
		{	
			
				//String strSqlQry = "select battery_type,battery_brand,battery_capacity,battery_model,battery_quantity,battery_type_exchanged,battery_brand_exchanged,battery_capacity_exchanged,battery_model_exchanged,battery_quantity_exchanged from "+strConditions+" ";
			
			String strSqlQry = "Select battery_type,battery_brand,battery_name,battery_model,battery_quantity,date_format(creation_date, '%d-%m-%Y') as Date from batterystore_returnbatteries where "+strConditions+" "+strbrandConditions+" order by battery_type asc";
			
				LogLevel.DEBUG(5,new Throwable()," insert strSqlQry: "+strSqlQry);	
				
				 
			//int reslt = qm.executeUpdate(strSqlQry);
			//LogLevel.DEBUG(5,new Throwable()," reslt: "+reslt);	
			
			Vector MisReturnVector=qm.executeQuery(strSqlQry);
			session.setAttribute("sesMisReturnVector",MisReturnVector);
			LogLevel.DEBUG(5,new Throwable(),"MisReturnVector :"+MisReturnVector);
	 
	
			res.sendRedirect("../jsp/batterywalestore/returnexchange/getreturndetails.jsp");
			
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
	public String BatteryExchange_Details(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		String strConditions="";
		String strbrandConditions="";
		
		String strfromdate  = (req.getParameter("FDate")!=null)?req.getParameter("FDate"):"";
		LogLevel.DEBUG(5, new Throwable(), "strfromdate :"+ strfromdate);
		
		String strtodate  = (req.getParameter("TDate")!=null)?req.getParameter("TDate"):"";
		LogLevel.DEBUG(5, new Throwable(), "strtodate :"+ strtodate);
	  
		String strbrand  = (req.getParameter("Brand")!=null)?req.getParameter("Brand"):"";
		LogLevel.DEBUG(5, new Throwable(), "strbrand :"+ strbrand);
		
		String strSID  = (req.getParameter("SID")!=null)?req.getParameter("SID"):"";
		LogLevel.DEBUG(5, new Throwable(), "strSID :"+ strSID);

		String strSNAME  = (req.getParameter("SNAME")!=null)?req.getParameter("SNAME"):"";
		LogLevel.DEBUG(5, new Throwable(), "strSNAME :"+ strSNAME);
		 
		strConditions="date(creation_date) between '"+strfromdate+"' and '"+strtodate+"'";
		LogLevel.DEBUG(5, new Throwable(), "strConditions :"+ strConditions);

		if((strbrand.equals("All")) || (strbrand.equals(""))) {
			
			strbrandConditions = "";
			
		} else {
			
			strbrandConditions = " and battery_brand='"+strbrand+"'";
		}
		
		try
		{	
			
				String strSqlQry = "select battery_type,battery_brand,battery_name,battery_model,battery_quantity,battery_type_exchanged,battery_brand_exchanged,battery_name_exchanged,battery_model_exchanged,battery_quantity_exchanged,date_format(creation_date, '%d-%m-%Y') as Date from batterystore_exchangebatteries where "+strConditions+"  "+strbrandConditions+" order by battery_type asc";
			
			
				LogLevel.DEBUG(5,new Throwable()," insert strSqlQry: "+strSqlQry);	
				
				 
			//int reslt = qm.executeUpdate(strSqlQry);
			//LogLevel.DEBUG(5,new Throwable()," reslt: "+reslt);	
			
			Vector MisExchangeVector=qm.executeQuery(strSqlQry);
			session.setAttribute("sesMisExchangeVector",MisExchangeVector);
			LogLevel.DEBUG(5,new Throwable(),"MisExchangeVector :"+MisExchangeVector);
	 
	
			res.sendRedirect("../jsp/batterywalestore/returnexchange/getexchangedetails.jsp");
			
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
	public String insertreturnbatterydetails(HttpServletRequest req,HttpServletResponse res,HttpSession session)
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
			 
			strSqlQry = "insert into batterystore_returnbatteries(battery_type,battery_brand,battery_name,battery_model,battery_quantity,store_id,store_name)values('"+strbattype+"','"+strbatterybrand+"','"+strbatteryname+"','"+strbatterymodel+"','"+strquantity+"','"+strstoreid+"','"+strstorename+"')";

		 	
			
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
	public String insertexchangebatterydetails(HttpServletRequest req,HttpServletResponse res,HttpSession session)
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

			String strbattypeexchanged= (req.getParameter("battery_type_exchanged")!=null)?(req.getParameter("battery_type_exchanged")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbattypeexchanged:"+strbattypeexchanged );

			String strbatterybrandexchanged= (req.getParameter("battery_brand_exchanged")!=null)?(req.getParameter("battery_brand_exchanged")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatterybrandexchanged:"+strbatterybrandexchanged );
			
			String strbatnameexchanged= (req.getParameter("battery_name_exchanged")!=null)?(req.getParameter("battery_name_exchanged")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatnameexchanged:"+strbatnameexchanged );
			
			String strbatterymodelexchanged= (req.getParameter("battery_model_exchanged")!=null)?(req.getParameter("battery_model_exchanged")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatterymodelexchanged:"+strbatterymodelexchanged );

			String strquantityexchanged = (req.getParameter("quantity_exchanged")!=null)?(req.getParameter("quantity_exchanged")):"";
			LogLevel.DEBUG(5,new Throwable(),"strquantityexchanged:"+strquantityexchanged );

			
			String strstorename= (req.getParameter("storename")!=null)?(req.getParameter("storename")):"";
			LogLevel.DEBUG(5,new Throwable(),"strstorename:"+strstorename );

			String strstoreid= (req.getParameter("storeid")!=null)?(req.getParameter("storeid")):"";
			LogLevel.DEBUG(5,new Throwable(),"strstoreid:"+strstoreid );

			String strSqlQry="";
			 
			strSqlQry = "insert into batterystore_exchangebatteries(battery_type,battery_brand,battery_name,battery_model,battery_quantity,battery_type_exchanged,battery_brand_exchanged,battery_name_exchanged,battery_model_exchanged,battery_quantity_exchanged,store_id,store_name)values('"+strbattype+"','"+strbatterybrand+"','"+strbatteryname+"','"+strbatterymodel+"','"+strquantity+"','"+strbattypeexchanged+"','"+strbatterybrandexchanged+"','"+strbatnameexchanged+"','"+strbatterymodelexchanged+"','"+strquantityexchanged+"','"+strstoreid+"','"+strstorename+"')";

		 	
			
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