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
public class BatterystoreServiceDetails extends HttpServlet 
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
		if(strWhatToDo.equalsIgnoreCase("getservicedetails"))
		{ 
			try
			{
				String strRes=getservicedetails(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("getrechargedetails"))
		{ 
			try
			{
				String strRes=getrechargedetails(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("insertserviceorderdetails"))
		{ 
			try
			{
				String strRes=insertserviceorderdetails(req,res,session);
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
	public String getservicedetails(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		try
		{	
			String strservice_type= (req.getParameter("service_type")!=null)?(req.getParameter("service_type")):"";
			LogLevel.DEBUG(5,new Throwable(),"strservice_type:"+strservice_type );

			String strbattype= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbattype:"+strbattype );

			String strvehmake= (req.getParameter("vehiclename")!=null)?(req.getParameter("vehiclename")):"";
			LogLevel.DEBUG(5,new Throwable(),"strvehmake:"+strvehmake );
			
			String strvehmodel= (req.getParameter("vehiclemodel")!=null)?(req.getParameter("vehiclemodel")):"";
			LogLevel.DEBUG(5,new Throwable(),"strvehmodel:"+strvehmodel );

			String strbatterycapty= (req.getParameter("batt_capacity")!=null)?(req.getParameter("batt_capacity")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatterycapty:"+strbatterycapty );

			String strstate= (req.getParameter("state")!=null)?(req.getParameter("state")):"";
			LogLevel.DEBUG(5,new Throwable(),"strstate:"+strstate);

			String strcity= (req.getParameter("city")!=null)?(req.getParameter("city")):"";
			LogLevel.DEBUG(5,new Throwable(),"strcity:"+strcity);
			
			String strarea= (req.getParameter("area")!=null)?(req.getParameter("area")):"";
			LogLevel.DEBUG(5,new Throwable(),"strarea:"+strarea);

			String strpincode= (req.getParameter("pincode")!=null)?(req.getParameter("pincode")):"";
			//String strpincode= "";
			LogLevel.DEBUG(5,new Throwable(),"strpincode:"+strpincode);
			
			String strSqlQry ="";
			String state="";
			String city="";
			String strConditions1="";
			String strConditionsDetails="";
			String strvehmodel1 = URLEncoder.encode(strvehmodel, "UTF-8");
			LogLevel.DEBUG(5,new Throwable(),"strvehmodel1:"+strvehmodel1);

			if(strpincode == "")
			{	
				state=strstate;
				city=strcity;
			}
			else
			{
			
			String StrSqlQrystate1 = "select state,city from battery_pincode where pincode='"+strpincode+"'";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQrystate1 :" + StrSqlQrystate1);

			Hashtable htgetstate1 = qm.getRow(StrSqlQrystate1); 
			state=(String)htgetstate1.get("state");
			String strcity1=(String)htgetstate1.get("city");
			city=strcity1;	
			}
			
			strSqlQry ="select service_type,battery_type,store,within_5km,within_10km from store_service_prices where service_type='"+strservice_type+"' and battery_type='"+strbattype+"'";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
			
			Vector ServicesdetailsVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"ServicesdetailsVector:"+ServicesdetailsVector );
			
			if(ServicesdetailsVector.isEmpty())
			{ 
				LogLevel.DEBUG(1,new Throwable(),"Failed to fetch Services details");
				session.setAttribute("priority","1");
				session.setAttribute("sesServicesdetailsErrorMsg", "<font color='#ff3333' class='vrb10'>No Services details found based on selection.! </font> ");
				session.removeAttribute("ServicesdetailsVector");
				res.sendRedirect("../jsp/batterywalestore/orders/rechargeservice.jsp");
				return strRes;
			}
			else
			{
				LogLevel.DEBUG(1, new Throwable(),"Successfully Fetched Services Details");
				session.setAttribute("ServicesdetailsVector", ServicesdetailsVector);
				res.sendRedirect("../jsp/batterywalestore/orders/orderservicedetails.jsp?vehiclemake="+strvehmake+"&vehiclemodel="+strvehmodel1+"&city="+city+"&pincity="+city+"&strarea="+strarea+"&strstate="+state+"&batterytype="+strbattype+"&strpincode="+strpincode+"&batterycapty="+strbatterycapty+"&services_type="+strservice_type);
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
	public String getrechargedetails(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		try
		
		{	
		 
			String strservice_type= (req.getParameter("service_type")!=null)?(req.getParameter("service_type")):"";
			LogLevel.DEBUG(5,new Throwable(),"strservice_type:"+strservice_type );

			String strbattype= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbattype:"+strbattype );

			String strvehmake= (req.getParameter("vehiclename")!=null)?(req.getParameter("vehiclename")):"";
			LogLevel.DEBUG(5,new Throwable(),"strvehmake:"+strvehmake );
			
			String strvehmodel= (req.getParameter("vehiclemodel")!=null)?(req.getParameter("vehiclemodel")):"";
			LogLevel.DEBUG(5,new Throwable(),"strvehmodel:"+strvehmodel );

			String strbatterycapty= (req.getParameter("batt_capacity")!=null)?(req.getParameter("batt_capacity")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatterycapty:"+strbatterycapty );

			String strstate= (req.getParameter("state")!=null)?(req.getParameter("state")):"";
			LogLevel.DEBUG(5,new Throwable(),"strstate:"+strstate);

			String strcity= (req.getParameter("city")!=null)?(req.getParameter("city")):"";
			LogLevel.DEBUG(5,new Throwable(),"strcity:"+strcity);
			
			String strarea= (req.getParameter("area")!=null)?(req.getParameter("area")):"";
			LogLevel.DEBUG(5,new Throwable(),"strarea:"+strarea);

			String strpincode= (req.getParameter("pincode")!=null)?(req.getParameter("pincode")):"";
			//String strpincode= "";
			LogLevel.DEBUG(5,new Throwable(),"strpincode:"+strpincode);
			
			String strSqlQry ="";
			String state="";
			String city="";
			String strConditions1="";
			String strConditionsDetails="";
			String strvehmodel1 = URLEncoder.encode(strvehmodel, "UTF-8");
			LogLevel.DEBUG(5,new Throwable(),"strvehmodel1:"+strvehmodel1);

			if(strpincode == "")
			{	
				state=strstate;
				city=strcity;
			}
			else
			{
			
			String StrSqlQrystate1 = "select state,city from battery_pincode where pincode='"+strpincode+"'";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQrystate1 :" + StrSqlQrystate1);

			Hashtable htgetstate1 = qm.getRow(StrSqlQrystate1); 
			state=(String)htgetstate1.get("state");
			String strcity1=(String)htgetstate1.get("city");
			city=strcity1;	
			}
			
			strSqlQry ="select service_type,battery_type,store,within_5km,within_10km from store_service_prices where service_type='"+strservice_type+"' and battery_type='"+strbattype+"'";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
			
			Vector ServicesdetailsVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"ServicesdetailsVector:"+ServicesdetailsVector );
			
			if(ServicesdetailsVector.isEmpty())
			{ 
				LogLevel.DEBUG(1,new Throwable(),"Failed to fetch Services details");
				session.setAttribute("priority","1");
				session.setAttribute("sesServicesdetailsErrorMsg", "<font color='#ff3333' class='vrb10'>No Services details found based on selection.! </font> ");
				session.removeAttribute("ServicesdetailsVector");
				res.sendRedirect("../jsp/batterywalestore/orders/rechargeservice.jsp");
				return strRes;
			}
			else
			{
				LogLevel.DEBUG(1, new Throwable(),"Successfully Fetched Services Details");
				session.setAttribute("ServicesdetailsVector", ServicesdetailsVector);
				res.sendRedirect("../jsp/batterywalestore/orders/services.jsp?vehiclemake="+strvehmake+"&vehiclemodel="+strvehmodel1+"&city="+city+"&pincity="+city+"&strarea="+strarea+"&strstate="+state+"&batterytype="+strbattype+"&strpincode="+strpincode+"&batterycapty="+strbatterycapty+"&services_type="+strservice_type);
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
	public String insertserviceorderdetails(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String Consumer_Mobile = (req.getParameter("mobile_number")!=null)?(req.getParameter("mobile_number")):"";
 		String Consumer_Name = (req.getParameter("consumer_name")!=null)?(req.getParameter("consumer_name")):"";
 		String Consumer_Email = (req.getParameter("consumer_emailid")!=null)?(req.getParameter("consumer_emailid")):"";
 		String Consumer_Address = (req.getParameter("consumer_address")!=null)?(req.getParameter("consumer_address")):"";
 		String Ordered_By = (req.getParameter("ordered_by")!=null)?(req.getParameter("ordered_by")):"";
 		String Order_Type = (req.getParameter("order_type")!=null)?(req.getParameter("order_type")):"";
 		String Referrence_Code = (req.getParameter("referrence_code")!=null)?(req.getParameter("referrence_code")):"";
 		String Payment_Mode = (req.getParameter("payment_mode")!=null)?(req.getParameter("payment_mode")):"";
 		String Quantity = (req.getParameter("quantity")!=null)?(req.getParameter("quantity")):"";
 		String Pincode = (req.getParameter("zipcode")!=null)?(req.getParameter("zipcode")):"";
 		String Area = (req.getParameter("area")!=null)?(req.getParameter("area")):"";
 		String City = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
 		String State = (req.getParameter("state")!=null)?(req.getParameter("state")):"";
 		String ServiceType = (req.getParameter("service_type")!=null)?(req.getParameter("service_type")):"";
 		String BatteryType = (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
 		String VehicleMake = (req.getParameter("vehiclemake")!=null)?(req.getParameter("vehiclemake")):"";
 		String VehicleModel = (req.getParameter("vehiclemodel")!=null)?(req.getParameter("vehiclemodel")):"";
 		String BatteryCapacity = (req.getParameter("batterycapacity")!=null)?(req.getParameter("batterycapacity")):"";
 		String ServicePrice = (req.getParameter("service_price")!=null)?(req.getParameter("service_price")):"";
 		String ServiceRange = (req.getParameter("service_range")!=null)?(req.getParameter("service_range")):"";
 		String StoreId = (req.getParameter("store_id")!=null)?(req.getParameter("store_id")):"";
 		String StoreName = (req.getParameter("store_name")!=null)?(req.getParameter("store_name")):"";
		 
		String strRes=""; 
		String CustomerAddress=""; 
		
		 
		try
		{
			ServletOutputStream out=res.getOutputStream();
			 
			
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
					String New_Order_ID_SQL = "SELECT ord_id as ORDER_ID FROM service_order_details ORDER BY ord_id DESC LIMIT 1";
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
					Order_Number_Generate ="ORDS"+Last_Order_Count_String+""+Order_Number_Generate1+"S";
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
					 
					strSqlQry = "insert into service_order_details(order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_address1,retailer_name,retailer_mobilnumber,retailer_emailid,state,city,area,pincode,services_type,product_type,veh_name,veh_model,product_capacity,quantity,service_price_mrp, service_price_discount,services_place,payment_mode,payment_mode_type,order_status,operator,confirm_by,reffered_code,agent_name,creation_date)values('"+Order_Number_Generate+"','"+Consumer_Name+"','"+Consumer_Mobile+"','"+Consumer_Email+"','"+Consumer_Address+"','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+State+"','"+City+"','"+Area+"','"+Pincode+"','"+ServiceType+"','"+BatteryType+"','"+VehicleMake+"','"+VehicleModel+"','"+BatteryCapacity+"','"+Quantity+"','"+ServicePrice+"','"+ServicePrice+"','"+ServiceRange+"','"+Payment_Mode+"','"+Payment_Mode+"','confirmed','"+Ordered_By+"','Storeoperator','"+Referrence_Code+"','storeoperator',now())";
							
					LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);	
					
					int reslt = qm.executeUpdate(strSqlQry);
						
					 
						//######## Send SMS for ORDER
						Order_Store_SMS Send_Order_SMS_Checkout = new Order_Store_SMS(qm);
						String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,Order_Number_Generate,"Service","Yes","Yes","No","confirmed");
						LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
						//######## Send SMS for ORDER
						
						//######## Send Mail for ORDER
						Order_Store_SMS Send_Order_MAIL_Checkout = new Order_Store_SMS(qm);
						String MAIL_Report=Send_Order_MAIL_Checkout.Send_Order_Mail(req,res,session,Order_Number_Generate,"Service","Sir","Yes","Yes","No","confirmed");
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