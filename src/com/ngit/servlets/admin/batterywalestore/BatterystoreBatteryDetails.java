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
public class BatterystoreBatteryDetails extends HttpServlet 
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
		if(strWhatToDo.equalsIgnoreCase("getbatdetails"))
		{ 
			try
			{
				String strRes=getbatdetails(req,res,session);
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
		
		 
		/*This method is used to View Battery details*/
		 /* ****************************************************************************************\
		* This action is used to View Battery details.
		\* *****************************************************************************************/		
		
		if(strWhatToDo.equalsIgnoreCase("viewbatdetails"))
		{ 
			try
				{
					String strRes=viewbatdetails(req,res,session);
					LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
					
					
					RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/batterywalestore/orders/orderproductdetails.jsp");
					RD.forward(req, res);
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
		if(strWhatToDo.equalsIgnoreCase("calculatepercentage"))
		{ 
			try
			{
				String strRes=calculatepercentage(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("calculatediscount"))
		{ 
			try
			{
				String strRes=calculatediscount(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("insertorderdetails"))
		{ 
			try
			{
				String strRes=insertorderdetails(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("getbatterydetails"))
		{ 
			try
			{
				String strRes=getbatterydetails(req,res,session);
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
		/* ****************************************************************************************\
		* This action is used to get battery capacity.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("getbatterycapacity"))
			{ 
			try
			{
				String strRes=getbatterycapacity(req,res,session);
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
		* This action is used to get battery capacity.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("getbrandcapacity"))
			{ 
			try
			{
				String strRes=getbrandcapacity(req,res,session);
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
		* This action is used to get battery capacity.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("getbatteryname"))
			{ 
			try
			{
				String strRes=getbatteryname(req,res,session);
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
		* This action is used to get battery capacity.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("getbatterybrands"))
			{ 
			try
			{
				String strRes=getbatterybrands(req,res,session);
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
		* This action is used to get battery capacity.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("getbatterymodels"))
			{ 
			try
			{
				String strRes=getbatterymodels(req,res,session);
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
		* This action is used to get battery capacity.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("getmodels"))
			{ 
			try
			{
				String strRes=getmodels(req,res,session);
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
		* This action is used to get battery capacity.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("getcapacitymodels"))
			{ 
			try
			{
				String strRes=getcapacitymodels(req,res,session);
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
	* This method is to fetch battery details details.
	* @strSqlQry : carries the query to select battery details from battery_details table.
	* @strSqlQry1 : Query has multiple pages to select battery details from battery_details table.
	\* **************************************************************************************************************************************/
	public String getbatdetails(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		try
		{	
			String strbattype= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbattype:"+strbattype );

			String strvehmake= (req.getParameter("vehiclename")!=null)?(req.getParameter("vehiclename")):"";
			LogLevel.DEBUG(5,new Throwable(),"strvehmake:"+strvehmake );
			
			String strvehmodel= (req.getParameter("vehiclemodel")!=null)?(req.getParameter("vehiclemodel")):"";
			LogLevel.DEBUG(5,new Throwable(),"strvehmodel:"+strvehmodel );

			String strbattbrand= (req.getParameter("batterybrand")!=null)?(req.getParameter("batterybrand")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbattbrand:"+strbattbrand );

			String strbatterycapty= (req.getParameter("batt_capacity")!=null)?(req.getParameter("batt_capacity")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatterycapty:"+strbatterycapty );

			String state= (req.getParameter("state")!=null)?(req.getParameter("state")):"";
			LogLevel.DEBUG(5,new Throwable(),"state:"+state);

			String strcity= (req.getParameter("city")!=null)?(req.getParameter("city")):"";
			LogLevel.DEBUG(5,new Throwable(),"strcity:"+strcity);
			
			String strarea= (req.getParameter("area")!=null)?(req.getParameter("area")):"";
			LogLevel.DEBUG(5,new Throwable(),"strarea:"+strarea);

			String strpincode= (req.getParameter("pincode")!=null)?(req.getParameter("pincode")):"";
			//String strpincode= "";
			LogLevel.DEBUG(5,new Throwable(),"strpincode:"+strpincode);

			String strSqlQry ="";
			String strstate="";
			String strSqlQrybat="";
			String strpincity="";
			String city="";
			String strConditions1="";
			String strConditionsDetails="";
			String strvehmodel1 = URLEncoder.encode(strvehmodel, "UTF-8");
			LogLevel.DEBUG(5,new Throwable(),"strvehmodel1:"+strvehmodel1);

			 
			if(strpincode == "")
			{
			 
				strstate=state;
				city=strcity;
			}
			else
			{
			 
			String StrSqlQrystate1 = "select state,city from battery_pincode where pincode='"+strpincode+"'";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQrystate1 :" + StrSqlQrystate1);

			Hashtable htgetstate1 = qm.getRow(StrSqlQrystate1); 
			strstate=(String)htgetstate1.get("state");
			String strcity1=(String)htgetstate1.get("city");
			city=strcity1;	
			}
			if(!strbattbrand.equals("All") )
			{
				strConditions1= " and bat_brand='"+strbattbrand+"' ";
			}
			else
			{	
				strConditions1= "";
			}


			if(!strbattbrand.equals("All") )
			{
				strConditionsDetails= " and a.bat_brand='"+strbattbrand+"' ";
			}
			else
			{	
				strConditionsDetails= "";
			}
			
			if(strbattype.equals("Inverter Batteries"))
			{
				strSqlQrybat ="select distinct(bat_id) from application_chat_mapping where bat_type='"+strbattype+"' and bat_capacity='"+strbatterycapty+"' "+strConditions1+" ";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQrybat :" + strSqlQrybat);
				
			} else {
				
				strSqlQrybat ="select distinct(bat_id) from application_chat_mapping where bat_type='"+strbattype+"' and veh_name='"+strvehmake+"' and veh_model='"+strvehmodel+"' "+strConditions1+" ";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQrybat :" + strSqlQrybat);
			}
			ArrayList htav=new ArrayList();
			htav=qm.getField(strSqlQrybat);

			String batids="";
			for(int i=0;i<htav.size();i++)
			{
			if(batids.equals(""))
			batids=htav.get(i).toString();
			else
			batids=batids+","+htav.get(i).toString();
			}
			LogLevel.DEBUG(5,new Throwable(),"batids:"+batids);
			
			if(strbattype.equals("Inverter Batteries"))
			{
				strSqlQry ="select a.map_id,a.bat_brand,a.bat_model,a.veh_model,a.bat_capacity,a.bat_warranty,a.icon_url,a.description,b.bat_act_price,b.bat_witbat_price,b.bat_ret_price,a.battery_type_flag from application_chat_mapping a, batteryprice b where a.bat_type='"+strbattype+"' and a.bat_capacity='"+strbatterycapty+"' "+strConditionsDetails+" and b.state='"+strstate+"' and b.city='"+city+"' and a.bat_model=b.bat_model and a.bat_id in ("+batids+") order by a.bat_brand asc";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
				
				//message = "User selected  "+strbattype+" >> "+strbatterycapty+" >> "+city+" for order Battery. User Mob No: "+strUsermobileno+""; 
			} else
			{
				strSqlQry ="select a.map_id,a.bat_brand,a.bat_model,a.veh_model,a.bat_capacity,a.bat_warranty,a.icon_url,a.description,b.bat_act_price,b.bat_witbat_price,b.bat_ret_price,a.battery_type_flag from application_chat_mapping a, batteryprice b where a.bat_type='"+strbattype+"' and a.veh_name='"+strvehmake+"' and a.veh_model='"+strvehmodel+"' "+strConditionsDetails+" and b.state='"+strstate+"' and b.city='"+city+"' and a.bat_model=b.bat_model and a.bat_id in ("+batids+") order by a.bat_brand asc";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

				//message = "User selected  "+strbattype+" >> "+strvehmake+" >> "+strvehmodel+" >> "+city+" for order Battery. User Mob No: "+strUsermobileno+"";
			}

			 Vector BatdetailsVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"BatdetailsVector:"+BatdetailsVector );
			
			if(BatdetailsVector.isEmpty())
			{ 
				LogLevel.DEBUG(1,new Throwable(),"Failed to fetch Battery details ");
				session.setAttribute("priority","1");
				session.setAttribute("sesbatterydetailsErrorMsg", "<font color='#ff3333' class='vrb10'>No Battery details found based on selection.! </font> ");
				session.removeAttribute("BatdetailsVector");
				res.sendRedirect("../jsp/batterywalestore/orders/orderbattery.jsp");
				return strRes;
			}
			else
			{
				LogLevel.DEBUG(1, new Throwable(),"Successfully Fetched Battery Details");
				session.setAttribute("BatdetailsVector", BatdetailsVector);
				//session.setAttribute("BatpriceVector", BatpriceVector);
				res.sendRedirect("../jsp/batterywalestore/orders/orderbatterydetails.jsp?batterytype="+strbattype+"&vehiclemake="+strvehmake+"&vehiclemodel="+strvehmodel1+"&batterybrand="+strbattbrand+"&city="+strcity+"&pincity="+city+"&strarea="+strarea+"&strstate="+strstate+"&strpincode="+strpincode+"&strbatterycapty="+strbatterycapty);
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
 
	/**************************************************************************************************************************************\
	* This method is to fetch battery product details.
	* @strSqlQry : carries the query to select battery details from battery_details table.
	* @strSqlQry1 : Query has multiple pages to select battery details from battery_details table.
	\* **************************************************************************************************************************************/
	public String viewbatdetails(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
	String Battery_Type="";
	String Vehicle_Name="";
	String Vehicle_Model="";
	String Manufacturer="";
	String Model="";
	String State="";
	String City="";
	String Pincity="";
	String Area="";
		try
		{	
			
			Battery_Type= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
			LogLevel.DEBUG(5,new Throwable(),"Battery_Type:"+Battery_Type );

			Model= (req.getParameter("bat_model")!=null)?(req.getParameter("bat_model")):"";
			LogLevel.DEBUG(5,new Throwable(),"Model:"+Model );
			
			Manufacturer= (req.getParameter("batterybrand")!=null)?(req.getParameter("batterybrand")):"";
			LogLevel.DEBUG(5,new Throwable(),"Manufacturer:"+Manufacturer );
			
			State= (req.getParameter("state")!=null)?(req.getParameter("state")):"";
			LogLevel.DEBUG(5,new Throwable(),"State:"+State );
			
			City= (req.getParameter("city")!=null)?(req.getParameter("city")):"";
			LogLevel.DEBUG(5,new Throwable(),"City:"+City );
			
			Area= (req.getParameter("area")!=null)?(req.getParameter("area")):"";
			LogLevel.DEBUG(5,new Throwable(),"Area:"+Area );
			
			Pincity= (req.getParameter("pincode")!=null)?(req.getParameter("pincode")):"";
			LogLevel.DEBUG(5,new Throwable(),"Pincity:"+Pincity );
			
			State= State.replaceAll("-", " ");
			City= City.replaceAll("-", " ");

			String Get_Product_Details_SQL ="select  DISTINCT a.map_id,a.bat_brand,a.bat_model,a.veh_name,a.veh_model,a.bat_capacity,a.bat_warranty,a.bat_name,a.icon_url,a.description,b.bat_act_price,b.bat_witbat_price,b.bat_ret_price,b.erp_pre_tax,a.battery_type_flag,a.bat_type,b.model_stock from application_chat_mapping a, batteryprice b where a.bat_brand='"+Manufacturer+"'  and b.state='"+State+"' and b.city='"+City+"' and a.bat_model='"+Model+"' and a.bat_type in ('"+Battery_Type+"') and b.bat_model='"+Model+"' ORDER BY a.bat_id DESC LIMIT 1"; 
			LogLevel.DEBUG(5, new Throwable(), "Get_Product_Details_SQL :" + Get_Product_Details_SQL);
			
			Vector Vector_Product_Details=qm.executeQuery(Get_Product_Details_SQL);
			LogLevel.DEBUG(5,new Throwable(),"Vector_Product_Details Query:"+Vector_Product_Details);
			
			String Get_Product_Details_SQL_2;
			Vector Vector_Product_Details_2;
			
			if(Vector_Product_Details.isEmpty())
			{ 
		
				Get_Product_Details_SQL_2 ="select  DISTINCT a.map_id,a.bat_brand,a.bat_model,a.veh_name,a.veh_model,a.bat_capacity,a.bat_warranty,a.bat_name,a.icon_url,a.description,b.bat_act_price,b.bat_witbat_price,b.erp_pre_tax,b.bat_ret_price,a.battery_type_flag,a.bat_type,b.model_stock from application_chat_mapping a, batteryprice b where a.bat_brand='"+Manufacturer+"'  and b.state='"+State+"' and b.city='"+City+"' and a.bat_model='"+Model+"' and b.bat_model='"+Model+"' ORDER BY a.bat_id DESC LIMIT 1"; 
				LogLevel.DEBUG(5, new Throwable(), "Get_Product_Details_SQL_2 :" + Get_Product_Details_SQL_2);
				
				Vector_Product_Details_2=qm.executeQuery(Get_Product_Details_SQL_2);
				LogLevel.DEBUG(5,new Throwable(),"Vector_Product_Details_2:"+Vector_Product_Details_2);
				
				if(Vector_Product_Details_2.isEmpty())
				{ 
					strRes="404 Page Not Found";
					RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/404.jsp");
					RD.forward(req, res);
					out.close();
					return strRes;
				}
				else
				{
					Vector_Product_Details=Vector_Product_Details_2;
					LogLevel.DEBUG(5, new Throwable(),"Vector_Product_Details inside Condition" +Vector_Product_Details);

				}
			}
			else
			{
				Vector_Product_Details=Vector_Product_Details;
				LogLevel.DEBUG(5, new Throwable(),"Vector_Product_Details inside Else" +Vector_Product_Details);
			}
			
			int i=0;
			Hashtable ht=(Hashtable)Vector_Product_Details.get(i);
			req.setAttribute("Map_Id",String.valueOf(ht.get("map_id")));
			LogLevel.DEBUG(5, new Throwable(), "Map_Id :" + String.valueOf(ht.get("map_id")));
			
			req.setAttribute("Battery_Brand",String.valueOf(ht.get("bat_brand")));
			LogLevel.DEBUG(5, new Throwable(), "Battery_Brand :" + String.valueOf(ht.get("bat_brand")));
			
			req.setAttribute("Battery_Type",String.valueOf(ht.get("bat_type")));
			LogLevel.DEBUG(5, new Throwable(), "Battery_Type :" + String.valueOf(ht.get("bat_type")));
			
			req.setAttribute("Battery_Model",String.valueOf(ht.get("bat_model")));
			LogLevel.DEBUG(5, new Throwable(), "Battery_Model :" + String.valueOf(ht.get("bat_model")));
			
			req.setAttribute("Vehicle_Make",String.valueOf(ht.get("veh_name")));
			LogLevel.DEBUG(5, new Throwable(), "Vehicle_Make :" + String.valueOf(ht.get("veh_name")));
			
			req.setAttribute("Vehicle_Model",String.valueOf(ht.get("veh_model")));
			LogLevel.DEBUG(5, new Throwable(), "Vehicle_Model :" + String.valueOf(ht.get("veh_model")));
			
			req.setAttribute("Battery_Capacity",String.valueOf(ht.get("bat_capacity")));
			LogLevel.DEBUG(5, new Throwable(), "Battery_Capacity :" + String.valueOf(ht.get("bat_capacity")));
			
			// This is used to get simular products or Batteries 
			String Battery_Capacity_tmp=String.valueOf(ht.get("bat_capacity"));
			
			req.setAttribute("Battery_Warranty",String.valueOf(ht.get("bat_warranty")));
			LogLevel.DEBUG(5, new Throwable(), "Battery_Warranty :" + String.valueOf(ht.get("bat_warranty")));
			
			
			req.setAttribute("Battery_Name",String.valueOf(ht.get("bat_name")));
			LogLevel.DEBUG(5, new Throwable(), "Battery_Name :" + String.valueOf(ht.get("bat_name")));
			
			req.setAttribute("Battery_Image",String.valueOf(ht.get("icon_url")));
			LogLevel.DEBUG(5, new Throwable(), "Battery_Image :" + String.valueOf(ht.get("icon_url")));
			
			req.setAttribute("Battery_Description",String.valueOf(ht.get("description")));
			LogLevel.DEBUG(5, new Throwable(), "Battery_Description :" + String.valueOf(ht.get("description")));
			
			req.setAttribute("Battery_Act_Price",String.valueOf(ht.get("bat_act_price")));
			LogLevel.DEBUG(5, new Throwable(), "Battery_Act_Price :" + String.valueOf(ht.get("bat_act_price")));
			
			req.setAttribute("Battery_Witbat_Price",String.valueOf(ht.get("bat_witbat_price")));
			LogLevel.DEBUG(5, new Throwable(), "Battery_Witbat_Price :" + String.valueOf(ht.get("bat_witbat_price")));
			
			req.setAttribute("Battery_Ret_Price",String.valueOf(ht.get("bat_ret_price")));
			LogLevel.DEBUG(5, new Throwable(), "Battery_Ret_Price :" + String.valueOf(ht.get("bat_ret_price")));
			
			req.setAttribute("Battery_ERP_Price",String.valueOf(ht.get("erp_pre_tax")));
			LogLevel.DEBUG(5, new Throwable(), "Battery_ERP_Price :" + String.valueOf(ht.get("erp_pre_tax")));
			
			req.setAttribute("Battery_Flag",String.valueOf(ht.get("battery_type_flag")));
			LogLevel.DEBUG(5, new Throwable(), "Battery_Flag :" + String.valueOf(ht.get("battery_type_flag")));
			
			req.setAttribute("Stock_Status",String.valueOf(ht.get("model_stock")));
			LogLevel.DEBUG(5, new Throwable(), "Stock_Status :" + String.valueOf(ht.get("model_stock")));
			
			req.setAttribute("State",State);
			LogLevel.DEBUG(5, new Throwable(), "State :" + State);
			
			req.setAttribute("City",City);
			LogLevel.DEBUG(5, new Throwable(), "City :" + City);
			
			req.setAttribute("Area",Area);
			LogLevel.DEBUG(5, new Throwable(), "Area :" + Area);
			
			req.setAttribute("Pincity",Pincity);
			LogLevel.DEBUG(5, new Throwable(), "Pincity :" + Pincity);
			
   
			// SQL to get home deliverable cities List			
			String Get_Delivery_Cities_List_SQL ="select distinct(city) from delivery_cities";
			LogLevel.DEBUG(5, new Throwable(), "Get_Delivery_Cities_List_SQL :" + Get_Delivery_Cities_List_SQL);
			
			Vector Vector_Delivery_Cities_List_SQL=qm.executeQuery(Get_Delivery_Cities_List_SQL);
			LogLevel.DEBUG(5,new Throwable(),"Vector_Delivery_Cities_List_SQL:"+Vector_Delivery_Cities_List_SQL);
			
			String delivery_cities="";
			
			for(int j=0;j<Vector_Delivery_Cities_List_SQL.size();j++)
			{
				
				Hashtable ht_city=(Hashtable)Vector_Delivery_Cities_List_SQL.get(j);
				
				delivery_cities=delivery_cities+","+String.valueOf(ht_city.get("city"));
			}
			
			LogLevel.DEBUG(5, new Throwable(), "delivery_cities in java :" + delivery_cities);
			req.setAttribute("delivery_cities",delivery_cities);
			LogLevel.DEBUG(5, new Throwable(), "delivery_cities after sett :" + delivery_cities);
			 
			 
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
	public String getbatterybrands(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String strbatterytype= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
			LogLevel.DEBUG(5, new Throwable(), "strbatterytype :" + strbatterytype);

		 
			String battery_brand="";	
			String strSqlQry ="";	
	 		ServletOutputStream out=res.getOutputStream();

			if(strbatterytype.equals("Inverter Batteries"))
			{
				strSqlQry ="select distinct(bat_brand) from battery_details order by bat_brand asc";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
			}
			else
			{
				strSqlQry ="select distinct(bat_brand) from battery_details where bat_brand NOT IN ('Luminous') order by bat_brand asc";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
			}

			Vector BatteryVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"BatteryVector:"+BatteryVector );

			if(BatteryVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='0'>&nbsp;Select&nbsp;Battery&nbsp;Brand</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<BatteryVector.size(); i++)
					{
						if(strbatterytype.equals("Inverter Batteries"))
						{
								if(i==0)
								{
								out.print("<option value='0' selected>&nbsp;Select&nbsp;Battery&nbsp;Brand</option>");
								//out.print("<option style='' value='All'>All</option>");
								}
						}
						else
						{
							if(i==0)
								{
								out.print("<option value='0' selected>&nbsp;Select&nbsp;Battery&nbsp;Brand</option>");
								}
						}
						Hashtable ht1=(Hashtable)BatteryVector.get(i);
						//bat_id =String.valueOf(ht1.get("bat_id"));
						battery_brand =String.valueOf(ht1.get("bat_brand"));
						out.print("<option  value='"+battery_brand+"'>"+battery_brand+"</option>"); 
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
	
		
	

	/**************************************************************************************************************************************\
	* This method is to fetch battery product details.
	* @strSqlQry : carries the query to select battery details from battery_details table.
	* @strSqlQry1 : Query has multiple pages to select battery details from battery_details table.
	\* **************************************************************************************************************************************/
	public String calculatediscount(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	 
 		String strbatbrand = (req.getParameter("batbrand")!=null)?(req.getParameter("batbrand")):"";
		String strBatmodel = (req.getParameter("batmodel")!=null)?(req.getParameter("batmodel")):"";
		String strchangeprice = (req.getParameter("changeprice")!=null)?(req.getParameter("changeprice")):"";
		String strchangebatretprice = (req.getParameter("changebatretprice")!=null)?(req.getParameter("changebatretprice")):"";

		String strRes=""; 
		try
		{
			ServletOutputStream out=res.getOutputStream();
			String strSqlQry = "select CAST(round("+strchangeprice+"-("+strchangebatretprice+")) AS SIGNED) as newobrpprice";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
			
			Vector DiscountPriceVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"DiscountPriceVector :"+DiscountPriceVector);

			if(DiscountPriceVector==null || DiscountPriceVector.size() == 0)
			{
				out.println("<p align='center' valign='top' class='insidecontent'><table valign='top' width='500' cellspacing='0' border='1' align='center' bgcolor='#FFFFFF' cellpadding='0'><tr><td><table valign='top' width='500' bgcolor='#FFFFFF' border='0'><tr><td class='insidecontent' align='center'>Failed to Calculate discount price!</td></tr></table></td></tr></table><a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
			}
			else
			{
				strRes=strRes+"<div class='form-group'><table class='table table-bordered'>";
				
				for(int j=0; j<DiscountPriceVector.size();j++)
				{
					Hashtable ht1=(Hashtable)DiscountPriceVector.get(j);
					String newobrpprice=String.valueOf(ht1.get("newobrpprice"));
					
					int strdisprices = Integer.parseInt(newobrpprice);
					int strwitbatprices = Integer.parseInt(strchangebatretprice);
					LogLevel.DEBUG(5,new Throwable(),"New Scrap Prices :"+strwitbatprices );
					int strwithoutprices = strdisprices - strwitbatprices;
					LogLevel.DEBUG(5,new Throwable(),"New Prices :"+strchangeprice );
				
 					 
 					strRes=strRes+"<tr><th>Discount New Battery Price</th><th>"+strchangeprice+"</th></tr><tr><th>Discount Scrap Price</th><th>"+strchangebatretprice+"</th></tr><tr><th>Discount Scrap Price</th><th>"+strdisprices+"</th></tr>"; 
 					strRes=strRes+"</table></div>"; 
 					//strRes=strRes+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type='button' class='btn bg-green waves-effect' onclick=\"javascript:Askcosumerdetails('"+strbatbrand+"','"+strBatmodel+"','"+discountprice+"','"+strwithoutprices+"');\"><i class='material-icons'>done_all</i><span>ORDER NOW</span></button>"; 
				} 
			}
			//out.println(strRes);

		}
		catch (Exception e)
		{
		LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
		
		}
		return strRes;
	} 
	
	/**************************************************************************************************************************************\
	* This method is to fetch battery product details.
	* @strSqlQry : carries the query to select battery details from battery_details table.
	* @strSqlQry1 : Query has multiple pages to select battery details from battery_details table.
	\* **************************************************************************************************************************************/
	public String calculatepercentage(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	 
 		String strbatbrand = (req.getParameter("batbrand")!=null)?(req.getParameter("batbrand")):"";
		String strBatmodel = (req.getParameter("batmodel")!=null)?(req.getParameter("batmodel")):"";
		String strdisprice = (req.getParameter("disprice")!=null)?(req.getParameter("disprice")):"";
		String strwithoutprice = (req.getParameter("withoutprice")!=null)?(req.getParameter("withoutprice")):"";
		String strbattretprice = (req.getParameter("battretprice")!=null)?(req.getParameter("battretprice")):"";
		String pricediscount = (req.getParameter("pricediscount")!=null)?(req.getParameter("pricediscount")):"";

		String strRes=""; 
		try
		{
			ServletOutputStream out=res.getOutputStream();
			String strSqlQry = "select CAST(round("+strdisprice+"-("+strdisprice+"*"+pricediscount+")) AS SIGNED) as discountprice";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
			
			Vector DiscountPriceVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"DiscountPriceVector :"+DiscountPriceVector);

			if(DiscountPriceVector==null || DiscountPriceVector.size() == 0)
			{
				out.println("<p align='center' valign='top' class='insidecontent'><table valign='top' width='500' cellspacing='0' border='1' align='center' bgcolor='#FFFFFF' cellpadding='0'><tr><td><table valign='top' width='500' bgcolor='#FFFFFF' border='0'><tr><td class='insidecontent' align='center'>Failed to Calculate discount price!</td></tr></table></td></tr></table><a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
			}
			else
			{
				strRes=strRes+"<table class='table'>";
				
				for(int j=0; j<DiscountPriceVector.size();j++)
				{
					Hashtable ht1=(Hashtable)DiscountPriceVector.get(j);
					String discountprice=String.valueOf(ht1.get("discountprice"));
					
					int strdisprices = Integer.parseInt(discountprice);
					int strwitbatprices = Integer.parseInt(strbattretprice);
					LogLevel.DEBUG(5,new Throwable(),"New Scrap Prices :"+strwitbatprices );
					int strwithoutprices = strdisprices - strwitbatprices;
					LogLevel.DEBUG(5,new Throwable(),"New Prices :"+strwithoutprice );
				
 					 
 					strRes=strRes+"<div class='demo-radio-button'><tr><th><input  name='battery_buy_type' value='New' type='radio' id='radio_1' onclick=\"javascript:changepriceneg('"+discountprice+"','"+strwithoutprices+"');\"/><label for='radio_1'>With Out Old Battery Take Back </th><th>: "+discountprice+"</th></tr><tr><th><input name='battery_buy_type' value='Replaced' type='radio' onclick=\"javascript:changepriceneg('"+discountprice+"','"+strwithoutprices+"');\" id='radio_2' checked/><label for='radio_2'>With Old Battery Take Back<span style='font-size: 11px;'> (Same Ah)</span></th><th>:"+strwithoutprices+"</th></tr></div> "; 
 					strRes=strRes+"</table>"; 
 					strRes=strRes+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type='button' class='btn bg-green waves-effect' onclick=\"javascript:Askcosumerdetails('"+strbatbrand+"','"+strBatmodel+"','"+discountprice+"','"+strwithoutprices+"');\"><i class='material-icons'>done_all</i><span>ORDER NOW</span></button>"; 
				} 
			}
			//out.println(strRes);

		}
		catch (Exception e)
		{
		LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
		
		}
		return strRes;
	} 
	/**************************************************************************************************************************************\
	* This method is to fetch battery product details.
	* @strSqlQry : carries the query to select battery details from battery_details table.
	* @strSqlQry1 : Query has multiple pages to select battery details from battery_details table.
	\* **************************************************************************************************************************************/
	public String getbatterydetails(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
		String strRes=""; 
		try
		{
			ServletOutputStream out=res.getOutputStream();
			String Battery_Price = "select distinct(bat_model),bat_brand,bat_act_price,bat_witbat_price,bat_ret_price,bat_capacity from batteryprice where city='Bangalore';";
			LogLevel.DEBUG(5,new Throwable(),"Battery_Price"+Battery_Price );
			
			Vector PriceVector=qm.executeQuery(Battery_Price);
			LogLevel.DEBUG(5,new Throwable(),"PriceVector :"+PriceVector);
			if(PriceVector==null || PriceVector.size() == 0)
			{
				strRes = "No Details Found";
				return strRes;
				
			} else {
				
				session.setAttribute("BatPriceVector", PriceVector);
				LogLevel.DEBUG(5, new Throwable(), "BatPriceVector in java :" + PriceVector);
				LogLevel.DEBUG(1,new Throwable(),"Success fetch Battery details ");
				session.setAttribute("priority","1");
 				res.sendRedirect("../jsp/batterywalestore/prices/pricedetails.jsp");
				return strRes;
			}
			
		}
		catch (Exception e)
		{
		LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
		
		}
		return strRes;
	}
	
	/* **************************************************************************************************************************************\
	* This method is to get battery capacity.
	* @strSqlQry : carries the query to battery details in battery_details table.
	\* **************************************************************************************************************************************/
	public String getbrandcapacity(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strbatterybrand= (req.getParameter("batterybrand")!=null)?(req.getParameter("batterybrand")):"";
		LogLevel.DEBUG(5,new Throwable(),"strbatterybrand:"+strbatterybrand);
		
		String strbatterytype= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
		LogLevel.DEBUG(5,new Throwable(),"strbatterytype:"+strbatterytype);
		
		
		String strRes="";
		String strbatcap = "";
		try
		{	
			//String veh_id_fetched="";
			String bat_capacity="";	

	 		ServletOutputStream out=res.getOutputStream();
			
				strbatcap = "select distinct(bat_capacity) from application_chat_mapping where bat_type='"+strbatterytype+"' and bat_brand='"+strbatterybrand+"' order by  cast(bat_capacity as signed) asc";  
				LogLevel.DEBUG(5, new Throwable(), "strbatcap :" + strbatcap);
			
			
			Vector strbatcapa=qm.executeQuery(strbatcap);
			LogLevel.DEBUG(5,new Throwable(),"strbatcapa:"+strbatcapa);
			if(strbatcapa.isEmpty())
			{ 
				out.println("<option value='0' selected>&nbsp;Select&nbsp;Capacity</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<strbatcapa.size(); i++)
					{
						if(i==0)
						{
						out.print("<option value='0' selected>&nbsp;Select&nbsp;Capacity</option>");
						}
						Hashtable ht2=(Hashtable)strbatcapa.get(i);
						//veh_id_fetched =String.valueOf(ht.get("veh_id"));
						//LogLevel.DEBUG(5,new Throwable(),"veh_id_fetched:"+veh_id_fetched);
						bat_capacity =String.valueOf(ht2.get("bat_capacity"));
						LogLevel.DEBUG(5,new Throwable(),"bat_capacity:"+bat_capacity);
						out.print("<option  value='"+bat_capacity+"'>"+bat_capacity+"</option>"); 
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
	* This method is to get battery capacity.
	* @strSqlQry : carries the query to battery details in battery_details table.
	\* **************************************************************************************************************************************/
	public String getbatteryname(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strbatterybrand= (req.getParameter("batterybrand")!=null)?(req.getParameter("batterybrand")):"";
		LogLevel.DEBUG(5,new Throwable(),"strbatterybrand:"+strbatterybrand);
		
		String strbatterytype= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
		LogLevel.DEBUG(5,new Throwable(),"strbatterytype:"+strbatterytype);
		
		
		String strRes="";
		String strbatname = "";
		try
		{	
			//String veh_id_fetched="";
			String bat_name="";	

	 		ServletOutputStream out=res.getOutputStream();
			
	 		strbatname = "select distinct(bat_name) from application_chat_mapping where bat_type='"+strbatterytype+"' and bat_brand='"+strbatterybrand+"' order by bat_name asc";  
				LogLevel.DEBUG(5, new Throwable(), "strbatcap :" + strbatname);
			
			
			Vector strbatcapa=qm.executeQuery(strbatname);
			LogLevel.DEBUG(5,new Throwable(),"strbatcapa:"+strbatcapa);
			if(strbatcapa.isEmpty())
			{ 
				out.println("<option value='0' selected>&nbsp;Select&nbsp;Battery&nbsp;Name</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<strbatcapa.size(); i++)
					{
						if(i==0)
						{
						out.print("<option value='0' selected>&nbsp;Select&nbsp;Battery&nbsp;Name</option>");
						}
						Hashtable ht2=(Hashtable)strbatcapa.get(i);
						//veh_id_fetched =String.valueOf(ht.get("veh_id"));
						//LogLevel.DEBUG(5,new Throwable(),"veh_id_fetched:"+veh_id_fetched);
						bat_name =String.valueOf(ht2.get("bat_name"));
						LogLevel.DEBUG(5,new Throwable(),"bat_capacity:"+bat_name);
						out.print("<option  value='"+bat_name+"'>"+bat_name+"</option>"); 
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
	* This method is to get battery capacity.
	* @strSqlQry : carries the query to battery details in battery_details table.
	\* **************************************************************************************************************************************/
	public String getbatterycapacity(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String batterytype= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
		LogLevel.DEBUG(5,new Throwable(),"batterytype:"+batterytype);
		String strRes="";
		String strbatcap = "";
		try
		{	
			//String veh_id_fetched="";
			String bat_capacity="";	

	 		ServletOutputStream out=res.getOutputStream();
			if (batterytype.equals("Bike Batteries"))
			{
				strbatcap = "select distinct(bat_capacity) from application_chat_mapping where bat_type='"+batterytype+"' order by bat_capacity";  
				LogLevel.DEBUG(5, new Throwable(), "strbatcap :" + strbatcap);
			}
			else if(batterytype.equals("Inverter Batteries"))
			{
				strbatcap = "select distinct(bat_capacity) from application_chat_mapping where bat_type='"+batterytype+"' order by bat_capacity";  
				LogLevel.DEBUG(5, new Throwable(), "strbatcap :" + strbatcap);
			}
			else
			{
				strbatcap = "select distinct(bat_capacity) from application_chat_mapping where bat_type NOT IN('Bike Batteries','Inverter Batteries') order by bat_capacity"; 
				LogLevel.DEBUG(5, new Throwable(), "strbatcap :" + strbatcap);
			}
			
			Vector strbatcapa=qm.executeQuery(strbatcap);
			LogLevel.DEBUG(5,new Throwable(),"strbatcapa:"+strbatcapa);
			if(strbatcapa.isEmpty())
			{ 
				out.println("<option value='0' selected>&nbsp;Select&nbsp;Capacity</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<strbatcapa.size(); i++)
					{
						if(i==0)
						{
						out.print("<option value='0' selected>&nbsp;Select&nbsp;Capacity</option>");
						}
						Hashtable ht2=(Hashtable)strbatcapa.get(i);
						//veh_id_fetched =String.valueOf(ht.get("veh_id"));
						//LogLevel.DEBUG(5,new Throwable(),"veh_id_fetched:"+veh_id_fetched);
						bat_capacity =String.valueOf(ht2.get("bat_capacity"));
						LogLevel.DEBUG(5,new Throwable(),"bat_capacity:"+bat_capacity);
						out.print("<option  value='"+bat_capacity+"'>"+bat_capacity+"</option>"); 
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
	* This method is to get battery capacity.
	* @strSqlQry : carries the query to battery details in battery_details table.
	\* **************************************************************************************************************************************/
	public String getbatterymodels(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String batterycapacity= (req.getParameter("batterycapacity")!=null)?(req.getParameter("batterycapacity")):"";
		LogLevel.DEBUG(5,new Throwable(),"batterycapacity:"+batterycapacity);
		String strRes="";
		String strbatcap = "";
		try
		{	
			//String veh_id_fetched="";
			String battery_model="";	

	 		ServletOutputStream out=res.getOutputStream();
			
				strbatcap = "select distinct(bat_model) from application_chat_mapping where bat_capacity='"+batterycapacity+"' order by bat_brand"; 
				LogLevel.DEBUG(5, new Throwable(), "strbatcap :" + strbatcap);
			
			
			Vector strbatcapa=qm.executeQuery(strbatcap);
			LogLevel.DEBUG(5,new Throwable(),"strbatcapa:"+strbatcapa);
			if(strbatcapa.isEmpty())
			{ 
				out.println("<option value='0' selected>&nbsp;Select&nbsp;Model</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<strbatcapa.size(); i++)
					{
						if(i==0)
						{
						out.print("<option value='0' selected>&nbsp;Select&nbsp;Model</option>");
						}
						Hashtable ht2=(Hashtable)strbatcapa.get(i);
						//veh_id_fetched =String.valueOf(ht.get("veh_id"));
						//LogLevel.DEBUG(5,new Throwable(),"veh_id_fetched:"+veh_id_fetched);
						battery_model =String.valueOf(ht2.get("bat_model"));
						LogLevel.DEBUG(5,new Throwable(),"battery_model:"+battery_model);
						out.print("<option  value='"+battery_model+"'>"+battery_model+"</option>"); 
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
	* This method is to get battery capacity.
	* @strSqlQry : carries the query to battery details in battery_details table.
	\* **************************************************************************************************************************************/
	public String getcapacitymodels(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strbatterybrand= (req.getParameter("batterybrand")!=null)?(req.getParameter("batterybrand")):"";
		LogLevel.DEBUG(5,new Throwable(),"strbatterybrand:"+strbatterybrand);
		
		String strbatteryname= (req.getParameter("batteryname")!=null)?(req.getParameter("batteryname")):"";
		LogLevel.DEBUG(5,new Throwable(),"strbatteryname:"+strbatteryname);
		
		String strRes="";
		String strbatcap = "";
		try
		{	
			//String veh_id_fetched="";
			String battery_model="";	
			String battery_capacity ="";
	 		ServletOutputStream out=res.getOutputStream();
			
				strbatcap = "select distinct(bat_model),bat_capacity from application_chat_mapping where bat_brand='"+strbatterybrand+"' and  bat_name='"+strbatteryname+"' order by bat_model,bat_capacity"; 
				LogLevel.DEBUG(5, new Throwable(), "strbatcap :" + strbatcap);
			
			
			Vector strbatcapa=qm.executeQuery(strbatcap);
			LogLevel.DEBUG(5,new Throwable(),"strbatcapa:"+strbatcapa);
			if(strbatcapa.isEmpty())
			{ 
				out.println("<option value='0' selected>&nbsp;Select&nbsp;Model</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<strbatcapa.size(); i++)
					{
						if(i==0)
						{
						out.print("<option value='0' selected>&nbsp;Select&nbsp;Model</option>");
						}
						Hashtable ht2=(Hashtable)strbatcapa.get(i);
						//veh_id_fetched =String.valueOf(ht.get("veh_id"));
						//LogLevel.DEBUG(5,new Throwable(),"veh_id_fetched:"+veh_id_fetched);
						battery_model =String.valueOf(ht2.get("bat_model"));
						battery_capacity =String.valueOf(ht2.get("bat_capacity"));
						LogLevel.DEBUG(5,new Throwable(),"battery_model:"+battery_model);
						out.print("<option  value='"+battery_model+"("+battery_capacity+")'>"+battery_model+"("+battery_capacity+")</option>"); 
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
	* This method is to get battery capacity.
	* @strSqlQry : carries the query to battery details in battery_details table.
	\* **************************************************************************************************************************************/
	public String getmodels(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strbatterybrand= (req.getParameter("batterybrand")!=null)?(req.getParameter("batterybrand")):"";
		LogLevel.DEBUG(5,new Throwable(),"strbatterybrand:"+strbatterybrand);
		
		String strbatterycapacity= (req.getParameter("batterycapacity")!=null)?(req.getParameter("batterycapacity")):"";
		LogLevel.DEBUG(5,new Throwable(),"strbatterycapacity:"+strbatterycapacity);
		
		String strRes="";
		String strbatcap = "";
		try
		{	
			//String veh_id_fetched="";
			String battery_model="";	

	 		ServletOutputStream out=res.getOutputStream();
			
				strbatcap = "select distinct(bat_model) from application_chat_mapping where bat_brand='"+strbatterybrand+"' and  bat_capacity='"+strbatterycapacity+"' order by bat_brand"; 
				LogLevel.DEBUG(5, new Throwable(), "strbatcap :" + strbatcap);
			
			
			Vector strbatcapa=qm.executeQuery(strbatcap);
			LogLevel.DEBUG(5,new Throwable(),"strbatcapa:"+strbatcapa);
			if(strbatcapa.isEmpty())
			{ 
				out.println("<option value='0' selected>&nbsp;Select&nbsp;Model</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<strbatcapa.size(); i++)
					{
						if(i==0)
						{
						out.print("<option value='0' selected>&nbsp;Select&nbsp;Model</option>");
						}
						Hashtable ht2=(Hashtable)strbatcapa.get(i);
						//veh_id_fetched =String.valueOf(ht.get("veh_id"));
						//LogLevel.DEBUG(5,new Throwable(),"veh_id_fetched:"+veh_id_fetched);
						battery_model =String.valueOf(ht2.get("bat_model"));
						LogLevel.DEBUG(5,new Throwable(),"battery_model:"+battery_model);
						out.print("<option  value='"+battery_model+"'>"+battery_model+"</option>"); 
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
	
	
	/**************************************************************************************************************************************\
	* This method is to fetch battery product details.
	* @strSqlQry : carries the query to select battery details from battery_details table.
	* @strSqlQry1 : Query has multiple pages to select battery details from battery_details table.
	\* **************************************************************************************************************************************/
	public String insertorderdetails(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	 
 		String Consumer_Mobile = (req.getParameter("mobile_number")!=null)?(req.getParameter("mobile_number")):"";
 		String Consumer_Name = (req.getParameter("consumer_name")!=null)?(req.getParameter("consumer_name")):"";
 		String Consumer_Email = (req.getParameter("consumer_emailid")!=null)?(req.getParameter("consumer_emailid")):"";
 		String Consumer_Address = (req.getParameter("consumer_address")!=null)?(req.getParameter("consumer_address")):"";
 		String Ordered_By = (req.getParameter("ordered_by")!=null)?(req.getParameter("ordered_by")):"";
 		String Ordered_Type = (req.getParameter("order_type")!=null)?(req.getParameter("order_type")):"";
 		String Payment_Mode = (req.getParameter("payment_mode")!=null)?(req.getParameter("payment_mode")):"";
 		String Quantity = (req.getParameter("quantity")!=null)?(req.getParameter("quantity")):"";
 		String Delivery_Mode = (req.getParameter("delivery_mode")!=null)?(req.getParameter("delivery_mode")):"";
 		String Referrence_Code = (req.getParameter("referrence_code")!=null)?(req.getParameter("referrence_code")):"";
 		String Pincode = (req.getParameter("zipcode")!=null)?(req.getParameter("zipcode")):"";
 		String Area = (req.getParameter("area")!=null)?(req.getParameter("area")):"";
 		String City = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
 		String State = (req.getParameter("state")!=null)?(req.getParameter("state")):"";
 		String BatteryType = (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
 		String VehicleMake = (req.getParameter("vehiclemake")!=null)?(req.getParameter("vehiclemake")):"";
 		String VehicleModel = (req.getParameter("vehiclemodel")!=null)?(req.getParameter("vehiclemodel")):"";
 		String BatteryBrand = (req.getParameter("batterybrand")!=null)?(req.getParameter("batterybrand")):"";
 		String BatteryCapacity = (req.getParameter("batterycapacity")!=null)?(req.getParameter("batterycapacity")):"";
 		String MRP = (req.getParameter("battery_mrpprice")!=null)?(req.getParameter("battery_mrpprice")):"";
 		String Price_Without = (req.getParameter("battery_withoutprice")!=null)?(req.getParameter("battery_withoutprice")):"";
 		String Price_OBRP = (req.getParameter("battery_withprice")!=null)?(req.getParameter("battery_withprice")):"";
 		String ERP = (req.getParameter("battery_erpprice")!=null)?(req.getParameter("battery_erpprice")):"";
 		String BatteryModel = (req.getParameter("batterymodel")!=null)?(req.getParameter("batterymodel")):"";
 		String StoreId = (req.getParameter("store_id")!=null)?(req.getParameter("store_id")):"";
 		String StoreName = (req.getParameter("store_name")!=null)?(req.getParameter("store_name")):"";
		 
		String strRes=""; 
		String CustomerAddress=""; 
		
		int Delivery_Charge=0;
		//password = password.replace("\\","\\\\");

		if((BatteryType=="Bike Batteries" || BatteryType.equals("Bike Batteries"))&& (Delivery_Mode=="Yes" || Delivery_Mode.equals("Yes")))
		{
			Delivery_Charge=150;
		}
		else
		{
			Delivery_Charge=Delivery_Charge;
		} 
		try
		{
			ServletOutputStream out=res.getOutputStream();
			
			
			
			int Price_Temp=0;
			if (Ordered_Type=="New" || Ordered_Type.equals("New"))
			{
				Price_Temp = Integer.parseInt(Price_Without);
			}
			else
			{
				Price_Temp = Integer.parseInt(Price_OBRP);
			}
			int QTY_int = Integer.parseInt(Quantity);
			int Final_Price_Int=Price_Temp*QTY_int;
						
			String Final_Price = Integer.toString(Final_Price_Int);
			
			
				String StrSqlQrydet = "select retailer_id,retailer_name,mobile_number,email_id,address1,mobile_numberother,invoice_flag from "+State+"_retailers where retailer_id='"+StoreId+"' and retailer_name='"+StoreName+"'";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQrydet :" + StrSqlQrydet);

				Hashtable htretailerdetls = qm.getRow(StrSqlQrydet); 
				String retailer_id=String.valueOf(htretailerdetls.get("retailer_id"));
				String strretmobnum=String.valueOf(htretailerdetls.get("mobile_number"));
				String strretname=(String)htretailerdetls.get("retailer_name");
				String strretemail=(String)htretailerdetls.get("email_id");
				String straddress1=(String)htretailerdetls.get("address1");
				String strretothermobnum=String.valueOf(htretailerdetls.get("mobile_numberother"));
				String strinvoiceflag=String.valueOf(htretailerdetls.get("invoice_flag"));
			
				if(retailer_id.equals(null) || retailer_id.equals("null") || retailer_id == null || retailer_id == "null" || retailer_id =="")
				{
				strRes="Session Expired or Server Down. Please regenerate your order.";
				}
				else
				{
					//######################################## Order Number Code	####################
					String New_Order_ID_SQL = "SELECT ord_id as ORDER_ID FROM battery_order_details ORDER BY ord_id DESC LIMIT 1";
					Hashtable New_Order_ID_HT = qm.getRow(New_Order_ID_SQL);
					LogLevel.DEBUG(5, new Throwable(), "New_Order_ID_HT :" + New_Order_ID_HT);
					int Last_Order_Count;
					if(New_Order_ID_HT.isEmpty())
					{
						/*following code is for generating the random number */
						Random Generator_Order_ID = new Random();   
						Generator_Order_ID.setSeed(System.currentTimeMillis());   
						int num = Generator_Order_ID.nextInt(90) + 10; 
						if (num < 100 || num > 999)
						{   
							num = Generator_Order_ID.nextInt(90) + 10;
							if (num < 100 || num > 999)
							{   
							}   
						} 
						Last_Order_Count=num;
					}
					else
					{
						Last_Order_Count=(Integer)New_Order_ID_HT.get("ORDER_ID");
						LogLevel.DEBUG(5, new Throwable(), "Last_Order_Count :" + Last_Order_Count);
					}
				
					// #######################Increment on order ID Count
					Last_Order_Count=Last_Order_Count+1;
					
					/*following code is for generating the random number */
					Random Generator_Order_ID_Ran = new Random();   
					Generator_Order_ID_Ran.setSeed(System.currentTimeMillis());   
					int Num = Generator_Order_ID_Ran.nextInt(90) + 10;
					if (Num < 100 || Num > 999)
					{   
						Num = Generator_Order_ID_Ran.nextInt(90) + 10;
						if (Num < 100 || Num > 999)
						{   
						}   
					} 
					
					String Last_Order_Count_String = Integer.toString(Last_Order_Count);
					String Order_Number_Generate = "";
					String Order_Number_Generate1 = Integer.toString(Num);
					Order_Number_Generate ="ORDB"+Last_Order_Count_String+""+Order_Number_Generate1+"B";
					LogLevel.DEBUG(5, new Throwable(), "Order_Number_Generate :" + Order_Number_Generate);
					Order_Number_Generate=Order_Number_Generate;
					 	  
					if(Consumer_Address.equals(""))
					{
						CustomerAddress="";
					}
					else
					{
					    
						CustomerAddress=", Address: "+Consumer_Address+"";
					}
					
		

					String strSqlQry="";
 					String User_Address_Landmark=CustomerAddress;
					User_Address_Landmark = User_Address_Landmark.replace("'","\\'");
					LogLevel.DEBUG(5,new Throwable()," User_Address_Landmark "+User_Address_Landmark);
					 
					strSqlQry = "insert into battery_order_details(order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_address,retailer_name,retailer_mobilnumber,retailer_emailis,state,city,area,pincode,bat_type,battery_brand,battery_model,battery_capacity,quantity,veh_name,veh_model,MRP_Price,price,witholdbatprice,erp_pre_tax,order_type,payment_mode,payment_mode_type,city_percentage,order_reasons,agent_name,confirm_by,delivery_mode,referred_coupon_code,delivery_charge,creation_date)values('"+Order_Number_Generate+"','"+Consumer_Name+"','"+Consumer_Mobile+"','"+Consumer_Email+"','"+Consumer_Address+"','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+State+"','"+City+"','"+Area+"','"+Pincode+"','"+BatteryType+"','"+BatteryBrand+"','"+BatteryModel+"','"+BatteryCapacity+"','"+Quantity+"','"+VehicleMake+"','"+VehicleModel+"','"+MRP+"','"+Price_Without+"','"+Price_OBRP+"','"+ERP+"','"+Ordered_Type+"','"+Payment_Mode+"','"+Payment_Mode+"','1.28','confirmed','storeoperator','Storeoperator','"+Delivery_Mode+"','"+Referrence_Code+"','"+Delivery_Charge+"',now())";
		
				 	
					
					LogLevel.DEBUG(5,new Throwable()," insert strSqlQry: "+strSqlQry);	
					
					int reslt = qm.executeUpdate(strSqlQry);
					LogLevel.DEBUG(5,new Throwable()," reslt: "+reslt);	
					 
						//######## Send SMS for ORDER
						Order_Store_SMS Send_Order_SMS_Checkout = new Order_Store_SMS(qm);
						String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,Order_Number_Generate,"Battery","Yes","Yes","No","confirmed");
						LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
						//######## Send SMS for ORDER
						
						//######## Send Mail for ORDER
						Order_Store_SMS Send_Order_MAIL_Checkout = new Order_Store_SMS(qm);
						String MAIL_Report=Send_Order_MAIL_Checkout.Send_Order_Mail(req,res,session,Order_Number_Generate,"Battery","Sir","Yes","Yes","No","confirmed");
						LogLevel.DEBUG(5, new Throwable(), "MAIL_Report :" + MAIL_Report);
						//######## Send Mail for ORDER
						
					strRes="Sucessfully | "+Order_Number_Generate+"";
					 
				}
			 

		}
		catch (Exception e)
		{
		LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
		
		}
		return strRes;
	} 
	
 }