/***********************************************************************		
	NGIT Confidential. 
	@File Name   : ProductDetails.java 
	@Description : This Servlet is used to select the Battery Details.
	@Author	     : Sai Krishna Daddala
	@Date        : 23th Sep 2016
******************************************************************/ 
 
package com.ngit.servlets.consumers.feedback; 
 
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
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
  
public class CustomerFeedBack extends HttpServlet 
{  
	private ServletContext context; 
	QueryManager qm;
	static final long serialVersionUID=21;
	/*This init method initializes the necessary connection pools and perform the transactions on the pools from respectvie files. */
	String Browser_URL="";
	String baseUrl ="";
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
		String strWhatToDo = (req.getParameter("hidWhatToDo")!=null)?req.getParameter("hidWhatToDo"):""; 
		//ServletOutputStream out=res.getOutputStream();
		
		LogLevel.DEBUG(5, new Throwable(), "strWhatToDo :" + strWhatToDo);
		
		baseUrl =  propsMOPConfig.getProperty("publicUrl");
		LogLevel.DEBUG(5, new Throwable(), "baseUrl :" + baseUrl);
		
		Browser_URL = req.getRequestURL().toString();
		LogLevel.DEBUG(5, new Throwable(), "Browser_URL :" + Browser_URL);
		//out.println(Browser_URL);
		
		String[] Browser_URL_Array=Browser_URL.split("/bookbattery/");;
		
		String results_FOR = Browser_URL_Array[1];
		//out.println(Browser_URL_Array[0]);
		//out.println(Browser_URL_Array[1]);
		LogLevel.DEBUG(5, new Throwable(), "Browser_URL 1 :" + Browser_URL_Array[0]);
		LogLevel.DEBUG(5, new Throwable(), "Browser_URL 2:" + Browser_URL_Array[1]);
		
		
		/* ****************************************************************************************\
		* This action is used to get vehicle model.
		\* *****************************************************************************************/		

		if(strWhatToDo.equalsIgnoreCase("insertcustomerfeedback"))
		{
			try
			{
				String strRes=insertcustomerfeedback(req,res,session);
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
		* This action is used to get vehicle model.
		\* *****************************************************************************************/		

		if(strWhatToDo.equalsIgnoreCase("insertcustomerfeedbackbattery"))
		{
			try
			{
				String strRes=insertcustomerfeedbackbattery(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("insertcustomerfeedbackinverter"))
		{
			try
			{
				String strRes=insertcustomerfeedbackinverter(req,res,session);
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
		else if(!Browser_URL_Array[0].equals("NULL"))
		{ 
				//Browser_URL_Array[1].split("/")[1]
				LogLevel.DEBUG(5, new Throwable(), "Browser_URL_Array[1].split" + Browser_URL_Array[1].split("/")[1]);
				String[] REQ_URL_Array  = Browser_URL_Array[1].split("/");
				LogLevel.DEBUG(5, new Throwable(), "REQ_URL_Array" + REQ_URL_Array);
				
				if(Browser_URL_Array[1].split("/")[1].contains("ORDB"))
				{
					LogLevel.DEBUG(5, new Throwable(), "battery"+Browser_URL_Array[1].split("/")[1]);
					try
					{
					String strRes=get_consumer_details_battery(req,res,session, Browser_URL_Array[1]);
					LogLevel.DEBUG(5, new Throwable(), "strRes after response :" + strRes);
					RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/customerfeedback.jsp");
					RD.forward(req, res);
					}
					catch (Exception e)
					{										
					LogLevel.ERROR(1, e, "Error :" + e);
					}	
				}
				else if(Browser_URL_Array[1].split("/")[1].contains("ORDI"))
				{
					LogLevel.DEBUG(5, new Throwable(), "inverter"+Browser_URL_Array[1].split("/")[1]);
					try
					{
					String strRes=get_consumer_details_inverter(req,res,session, Browser_URL_Array[1]);
					LogLevel.DEBUG(5, new Throwable(), "strRes after response :" + strRes);
					RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/invertercustomerfeedback.jsp");
					RD.forward(req, res);
					}
					catch (Exception e)
					{										
					LogLevel.ERROR(1, e, "Error :" + e);
					}	
				}
				else
				{
					LogLevel.DEBUG(5, new Throwable(), "service"+Browser_URL_Array[1].split("/")[1]);
					try
					{
					String strRes=get_consumer_details(req,res,session, Browser_URL_Array[1]);
					LogLevel.DEBUG(5, new Throwable(), "strRes after response :" + strRes);
					RequestDispatcher RD = getServletContext().getRequestDispatcher("/cusfeedback/index.jsp");
					RD.forward(req, res);
					}
					catch (Exception e)
					{										
					LogLevel.ERROR(1, e, "Error :" + e);
					}	
				}

		}
		else
		{
			String strRes="404 Page Not Found";
			LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
			RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/404.jsp");
			RD.forward(req, res);
			out.close();
		}
	
	}

	public String get_consumer_details(HttpServletRequest req , HttpServletResponse res,HttpSession session, String REQ_URL)
	{
	String strRes="";
	String Order_Number="";
		try
		{	
			LogLevel.DEBUG(5, new Throwable(), "strRes  :" + REQ_URL);

			String[] REQ_URL_Array  = REQ_URL.split("/");
			
			if(REQ_URL_Array.length<2){
				strRes="404 Page Not Found";
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/404.jsp");
				RD.forward(req, res);
				out.close();
				return strRes;
			}
			else
			{
				Order_Number=java.net.URLDecoder.decode(REQ_URL_Array[1], "UTF-8");
			}
			String strquery="Select order_number,consumer_name,mobile_number,battery_type,vehicle_make,vehicle_model,capacity,quantity,terminals_src_spc,battery_cleanup_cloth,volts_before_car_started,volts_after_car_started,volts_car_cranking,volts_car_acceleration,battery_good,battery_not_charge,battery_dead,battery_overcharge,battery_needs_charging,service_eng_mobile,warranty_expiry_date,volts_power_off,volts_power_on,water_gravity from health_check_feedback where order_number='"+Order_Number+"'";
			LogLevel.DEBUG(5, new Throwable(), "strquery :" + strquery);
			Hashtable ht = qm.getRow(strquery);
			if(ht.isEmpty())
			{
				strRes="404 Page Not Found";
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/404.jsp");
				RD.forward(req, res);
				out.close();
				return strRes;
			}
			else
			{
				req.setAttribute("order_number",String.valueOf(ht.get("order_number")));
				LogLevel.DEBUG(5, new Throwable(), "order_number :" + String.valueOf(ht.get("order_number")));
				req.setAttribute("consumer_name",String.valueOf(ht.get("consumer_name")).replaceAll(" ", "_"));
				LogLevel.DEBUG(5, new Throwable(), "consumer_name :" + String.valueOf(ht.get("consumer_name")));
				req.setAttribute("mobile_number",String.valueOf(ht.get("mobile_number")));
				LogLevel.DEBUG(5, new Throwable(), "mobile_number :" + String.valueOf(ht.get("mobile_number")));
				req.setAttribute("battery_type",String.valueOf(ht.get("battery_type")).replaceAll(" ", "_"));
				LogLevel.DEBUG(5, new Throwable(), "battery_type :" + String.valueOf(ht.get("battery_type")));
				req.setAttribute("vehicle_make",String.valueOf(ht.get("vehicle_make")).replaceAll(" ", "_"));
				LogLevel.DEBUG(5, new Throwable(), "vehicle_make :" + String.valueOf(ht.get("vehicle_make")));
				req.setAttribute("vehicle_model",String.valueOf(ht.get("vehicle_model")).replaceAll(" ", "_"));
				LogLevel.DEBUG(5, new Throwable(), "vehicle_model :" + String.valueOf(ht.get("vehicle_model")));
				req.setAttribute("capacity",String.valueOf(ht.get("capacity")).replaceAll(" ", "_"));
				LogLevel.DEBUG(5, new Throwable(), "capacity :" + String.valueOf(ht.get("capacity")));
				
				req.setAttribute("quantity",String.valueOf(ht.get("quantity")));
				LogLevel.DEBUG(5, new Throwable(), "quantity :" + String.valueOf(ht.get("quantity")));				
				req.setAttribute("volts_before_car_started",String.valueOf(ht.get("volts_before_car_started")));
				
				LogLevel.DEBUG(5, new Throwable(), "volts_before_car_started :" + String.valueOf(ht.get("volts_before_car_started")));	
				
				req.setAttribute("volts_after_car_started",String.valueOf(ht.get("volts_after_car_started")));
				
				LogLevel.DEBUG(5, new Throwable(), "volts_after_car_started :" + String.valueOf(ht.get("volts_after_car_started")));	
				
				req.setAttribute("volts_car_cranking",String.valueOf(ht.get("volts_car_cranking")));
				LogLevel.DEBUG(5, new Throwable(), "volts_car_cranking :" + String.valueOf(ht.get("volts_car_cranking")));	
				
				req.setAttribute("volts_car_acceleration",String.valueOf(ht.get("volts_car_acceleration")));
				LogLevel.DEBUG(5, new Throwable(), "volts_car_acceleration :" + String.valueOf(ht.get("volts_car_acceleration")));
				
				req.setAttribute("terminals_src_spc",String.valueOf(ht.get("terminals_src_spc")));
				LogLevel.DEBUG(5, new Throwable(), "terminals_src_spc :" + String.valueOf(ht.get("terminals_src_spc")));
				
				req.setAttribute("battery_cleanup_cloth",String.valueOf(ht.get("battery_cleanup_cloth")));
				LogLevel.DEBUG(5, new Throwable(), "battery_cleanup_cloth :" + String.valueOf(ht.get("battery_cleanup_cloth")));	
				
				req.setAttribute("battery_good",String.valueOf(ht.get("battery_good")));
				LogLevel.DEBUG(5, new Throwable(), "battery_good :" + String.valueOf(ht.get("battery_good")));
				
				req.setAttribute("battery_not_charge",String.valueOf(ht.get("battery_not_charge")));
				LogLevel.DEBUG(5, new Throwable(), "battery_not_charge :" + String.valueOf(ht.get("battery_not_charge")));
				
				req.setAttribute("battery_dead",String.valueOf(ht.get("battery_dead")));
				LogLevel.DEBUG(5, new Throwable(), "battery_dead :" + String.valueOf(ht.get("battery_dead")));
				
				req.setAttribute("battery_overcharge",String.valueOf(ht.get("battery_overcharge")));
				LogLevel.DEBUG(5, new Throwable(), "battery_overcharge :" + String.valueOf(ht.get("battery_overcharge")));				
				
				req.setAttribute("battery_needs_charging",String.valueOf(ht.get("battery_needs_charging")));
				LogLevel.DEBUG(5, new Throwable(), "battery_needs_charging :" + String.valueOf(ht.get("battery_needs_charging")));
				
				req.setAttribute("warranty_expiry_date",String.valueOf(ht.get("warranty_expiry_date")));
				LogLevel.DEBUG(5, new Throwable(), "warranty_expiry_date :" + String.valueOf(ht.get("warranty_expiry_date")));
				
				req.setAttribute("volts_power_off",String.valueOf(ht.get("volts_power_off")));
				LogLevel.DEBUG(5, new Throwable(), "volts_power_off :" + String.valueOf(ht.get("volts_power_off")));
				
				req.setAttribute("volts_power_on",String.valueOf(ht.get("volts_power_on")));
				LogLevel.DEBUG(5, new Throwable(), "volts_power_on :" + String.valueOf(ht.get("volts_power_on")));				
				
				req.setAttribute("water_gravity",String.valueOf(ht.get("water_gravity")));
				LogLevel.DEBUG(5, new Throwable(), "water_gravity :" + String.valueOf(ht.get("water_gravity")));

				req.setAttribute("service_eng_mobile",String.valueOf(ht.get("service_eng_mobile")));
				LogLevel.DEBUG(5, new Throwable(), "water_gravity :" + String.valueOf(ht.get("service_eng_mobile")));
			}
		}
		catch (Exception e)
		{
			LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
			e.printStackTrace();
		}
		return strRes;
	}
	
	public String get_consumer_details_battery(HttpServletRequest req , HttpServletResponse res,HttpSession session, String REQ_URL)
	{
	String strRes="";
	String Order_Number="";
		try
		{	
			LogLevel.DEBUG(5, new Throwable(), "strRes  :" + REQ_URL);

			String[] REQ_URL_Array  = REQ_URL.split("/");
			
			if(REQ_URL_Array.length<2){
				strRes="404 Page Not Found";
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/404.jsp");
				RD.forward(req, res);
				out.close();
				return strRes;
			}
			else
			{
				Order_Number=java.net.URLDecoder.decode(REQ_URL_Array[1], "UTF-8");
			}
			String strquery="Select order_number,consumer_name,mobile_number,product_type,product_brand,product_model,veh_name,veh_model,exisiting_inverter_make,exisiting_inverter_model,exisiting_inverter_working,capacity,quantity,terminals_src_spc,battery_cleanup_cloth,volts_before_car_started,volts_after_car_started,volts_car_cranking,volts_car_acceleration,invoice_bill,service_eng_mobile,warranty_card_seal,customer_satisfied,warranty_expiry_date from installation_check_list_details where order_number='"+Order_Number+"'";
			LogLevel.DEBUG(5, new Throwable(), "strquery :" + strquery);
			Hashtable ht = qm.getRow(strquery);
			if(ht.isEmpty())
			{
				strRes="404 Page Not Found";
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/404.jsp");
				RD.forward(req, res);
				out.close();
				return strRes;
			}
			else
			{
				req.setAttribute("order_number",String.valueOf(ht.get("order_number")));
				LogLevel.DEBUG(5, new Throwable(), "order_number :" + String.valueOf(ht.get("order_number")));
				req.setAttribute("consumer_name",String.valueOf(ht.get("consumer_name")).replaceAll(" ", "_"));
				LogLevel.DEBUG(5, new Throwable(), "consumer_name :" + String.valueOf(ht.get("consumer_name")));
				req.setAttribute("mobile_number",String.valueOf(ht.get("mobile_number")));
				LogLevel.DEBUG(5, new Throwable(), "mobile_number :" + String.valueOf(ht.get("mobile_number")));
				req.setAttribute("product_type",String.valueOf(ht.get("product_type")).replaceAll(" ", "_"));
				LogLevel.DEBUG(5, new Throwable(), "product_type :" + String.valueOf(ht.get("product_type")));			
				req.setAttribute("product_brand",String.valueOf(ht.get("product_brand")).replaceAll(" ", "_"));
				LogLevel.DEBUG(5, new Throwable(), "product_brand :" + String.valueOf(ht.get("product_brand")));					req.setAttribute("product_model",String.valueOf(ht.get("product_model")).replaceAll(" ", "_"));
				LogLevel.DEBUG(5, new Throwable(), "product_model :" + String.valueOf(ht.get("product_model")));
				
				req.setAttribute("exisiting_inverter_make",String.valueOf(ht.get("exisiting_inverter_make")).replaceAll(" ", "_"));
				LogLevel.DEBUG(5, new Throwable(), "exisiting_inverter_make :" + String.valueOf(ht.get("exisiting_inverter_make")));
				
				req.setAttribute("exisiting_inverter_model",String.valueOf(ht.get("exisiting_inverter_model")).replaceAll(" ", "_"));
				LogLevel.DEBUG(5, new Throwable(), "exisiting_inverter_model :" + String.valueOf(ht.get("exisiting_inverter_model")));	
				
				req.setAttribute("exisiting_inverter_working",String.valueOf(ht.get("exisiting_inverter_working")).replaceAll(" ", "_"));
				LogLevel.DEBUG(5, new Throwable(), "exisiting_inverter_working :" + String.valueOf(ht.get("exisiting_inverter_working")));				
				
				req.setAttribute("veh_name",String.valueOf(ht.get("veh_name")).replaceAll(" ", "_"));
				LogLevel.DEBUG(5, new Throwable(), "veh_name :" + String.valueOf(ht.get("veh_name")));
				
				req.setAttribute("veh_model",String.valueOf(ht.get("veh_model")).replaceAll(" ", "_"));
				LogLevel.DEBUG(5, new Throwable(), "veh_model :" + String.valueOf(ht.get("veh_model")));
				
				req.setAttribute("capacity",String.valueOf(ht.get("capacity")).replaceAll(" ", "_"));
				LogLevel.DEBUG(5, new Throwable(), "capacity :" + String.valueOf(ht.get("capacity")));
				
				req.setAttribute("quantity",String.valueOf(ht.get("quantity")));
				LogLevel.DEBUG(5, new Throwable(), "quantity :" + String.valueOf(ht.get("quantity")));
				
				req.setAttribute("terminals_src_spc",String.valueOf(ht.get("terminals_src_spc")));
				LogLevel.DEBUG(5, new Throwable(), "terminals_src_spc :" + String.valueOf(ht.get("terminals_src_spc")));
				
				req.setAttribute("battery_cleanup_cloth",String.valueOf(ht.get("battery_cleanup_cloth")));
				LogLevel.DEBUG(5, new Throwable(), "battery_cleanup_cloth :" + String.valueOf(ht.get("battery_cleanup_cloth")));					
				req.setAttribute("volts_before_car_started",String.valueOf(ht.get("volts_before_car_started")));
				LogLevel.DEBUG(5, new Throwable(), "volts_before_car_started :" + String.valueOf(ht.get("volts_before_car_started")));	
				
				req.setAttribute("volts_after_car_started",String.valueOf(ht.get("volts_after_car_started")));
				LogLevel.DEBUG(5, new Throwable(), "volts_after_car_started :" + String.valueOf(ht.get("volts_after_car_started")));	
				
				req.setAttribute("volts_car_cranking",String.valueOf(ht.get("volts_car_cranking")));
				LogLevel.DEBUG(5, new Throwable(), "volts_car_cranking :" + String.valueOf(ht.get("volts_car_cranking")));	
				
				req.setAttribute("volts_car_acceleration",String.valueOf(ht.get("volts_car_acceleration")));
				LogLevel.DEBUG(5, new Throwable(), "volts_car_acceleration :" + String.valueOf(ht.get("volts_car_acceleration")));	
				
				req.setAttribute("invoice_bill",String.valueOf(ht.get("invoice_bill")));
				LogLevel.DEBUG(5, new Throwable(), "invoice_bill :" + String.valueOf(ht.get("invoice_bill")));				
				
				req.setAttribute("warranty_card_seal",String.valueOf(ht.get("warranty_card_seal")));
				LogLevel.DEBUG(5, new Throwable(), "warranty_card_seal :" + String.valueOf(ht.get("warranty_card_seal")));
				
				req.setAttribute("customer_satisfied",String.valueOf(ht.get("customer_satisfied")));
				LogLevel.DEBUG(5, new Throwable(), "customer_satisfied :" + String.valueOf(ht.get("customer_satisfied")));
				
				req.setAttribute("warranty_expiry_date",String.valueOf(ht.get("warranty_expiry_date")));
				LogLevel.DEBUG(5, new Throwable(), "warranty_expiry_date :" + String.valueOf(ht.get("warranty_expiry_date")));				
                req.setAttribute("service_eng_mobile",String.valueOf(ht.get("service_eng_mobile")));
				LogLevel.DEBUG(5, new Throwable(), "service_eng_mobile :" + String.valueOf(ht.get("service_eng_mobile")));
				
			}
		}
		catch (Exception e)
		{
			LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
			e.printStackTrace();
		}
		return strRes;
	}		
	
	public String get_consumer_details_inverter(HttpServletRequest req , HttpServletResponse res,HttpSession session, String REQ_URL)
	{
	String strRes="";
	String Order_Number="";
		try
		{	
			LogLevel.DEBUG(5, new Throwable(), "strRes  :" + REQ_URL);

			String[] REQ_URL_Array  = REQ_URL.split("/");
			
			if(REQ_URL_Array.length<2){
				strRes="404 Page Not Found";
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/404.jsp");
				RD.forward(req, res);
				out.close();
				return strRes;
			}
			else
			{
				Order_Number=java.net.URLDecoder.decode(REQ_URL_Array[1], "UTF-8");
			}
			String strquery="Select order_number,consumer_name,consumer_mobnumber,inverter_make,inverter_model,existing_battery_make,existing_battery_model,existing_no_batteries,terminals_src_spc,battery_cleanup_cloth,volts_power_off,volts_power_on,invoice_bill,warranty_card_seal,customer_satisfied,warranty_expiry_date,existing_battery_warranty_expiry,inverter_status_charge_on,service_eng_mobile,inverter_status_main_on,inverter_status_inverter_on,inverter_status_overload from inverter_orders_check_list_details where order_number='"+Order_Number+"'";
			LogLevel.DEBUG(5, new Throwable(), "strquery :" + strquery);
			Hashtable ht = qm.getRow(strquery);
			if(ht.isEmpty())
			{
				strRes="404 Page Not Found";
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/404.jsp");
				RD.forward(req, res);
				out.close();
				return strRes;
			}
			else
			{
				req.setAttribute("order_number",String.valueOf(ht.get("order_number")));
				LogLevel.DEBUG(5, new Throwable(), "order_number :" + String.valueOf(ht.get("order_number")));
				req.setAttribute("consumer_name",String.valueOf(ht.get("consumer_name")).replaceAll(" ", "_"));
				LogLevel.DEBUG(5, new Throwable(), "consumer_name :" + String.valueOf(ht.get("consumer_name")));
				req.setAttribute("consumer_mobnumber",String.valueOf(ht.get("consumer_mobnumber")));
				LogLevel.DEBUG(5, new Throwable(), "consumer_mobnumber :" + String.valueOf(ht.get("consumer_mobnumber")));
				req.setAttribute("inverter_make",String.valueOf(ht.get("inverter_make")).replaceAll(" ", "_"));
				LogLevel.DEBUG(5, new Throwable(), "inverter_make :" + String.valueOf(ht.get("inverter_make")));			
				req.setAttribute("inverter_model",String.valueOf(ht.get("inverter_model")).replaceAll(" ", "_"));
				LogLevel.DEBUG(5, new Throwable(), "inverter_model :" + String.valueOf(ht.get("inverter_model")));	
								
				req.setAttribute("existing_battery_make",String.valueOf(ht.get("existing_battery_make")).replaceAll(" ", "_"));
				LogLevel.DEBUG(5, new Throwable(), "existing_battery_make :" + String.valueOf(ht.get("existing_battery_make")));
				req.setAttribute("existing_battery_model",String.valueOf(ht.get("existing_battery_model")).replaceAll(" ", "_"));
				LogLevel.DEBUG(5, new Throwable(), "existing_battery_model :" + String.valueOf(ht.get("existing_battery_model")));
				req.setAttribute("existing_no_batteries",String.valueOf(ht.get("existing_no_batteries")).replaceAll(" ", "_"));
				LogLevel.DEBUG(5, new Throwable(), "existing_no_batteries :" + String.valueOf(ht.get("existing_no_batteries")));

				req.setAttribute("terminals_src_spc",String.valueOf(ht.get("terminals_src_spc")));
				LogLevel.DEBUG(5, new Throwable(), "terminals_src_spc :" + String.valueOf(ht.get("terminals_src_spc")));
				
				req.setAttribute("battery_cleanup_cloth",String.valueOf(ht.get("battery_cleanup_cloth")));
				LogLevel.DEBUG(5, new Throwable(), "battery_cleanup_cloth :" + String.valueOf(ht.get("battery_cleanup_cloth")));	
				
				req.setAttribute("volts_power_off",String.valueOf(ht.get("volts_power_off")));
				LogLevel.DEBUG(5, new Throwable(), "volts_power_off :" + String.valueOf(ht.get("volts_power_off")));	
				
				req.setAttribute("volts_power_on",String.valueOf(ht.get("volts_power_on")));
				LogLevel.DEBUG(5, new Throwable(), "volts_power_on :" + String.valueOf(ht.get("volts_power_on")));	
				
				req.setAttribute("invoice_bill",String.valueOf(ht.get("invoice_bill")));
				LogLevel.DEBUG(5, new Throwable(), "invoice_bill :" + String.valueOf(ht.get("invoice_bill")));				
				req.setAttribute("warranty_card_seal",String.valueOf(ht.get("warranty_card_seal")));
				LogLevel.DEBUG(5, new Throwable(), "warranty_card_seal :" + String.valueOf(ht.get("warranty_card_seal")));
				
				req.setAttribute("customer_satisfied",String.valueOf(ht.get("customer_satisfied")));
				LogLevel.DEBUG(5, new Throwable(), "customer_satisfied :" + String.valueOf(ht.get("customer_satisfied")));
				
				req.setAttribute("warranty_expiry_date",String.valueOf(ht.get("warranty_expiry_date")));
				LogLevel.DEBUG(5, new Throwable(), "warranty_expiry_date :" + String.valueOf(ht.get("warranty_expiry_date")));
				
				req.setAttribute("existing_battery_warranty_expiry",String.valueOf(ht.get("existing_battery_warranty_expiry")));
				LogLevel.DEBUG(5, new Throwable(), "existing_battery_warranty_expiry :" + String.valueOf(ht.get("existing_battery_warranty_expiry")));			

				req.setAttribute("inverter_status_charge_on",String.valueOf(ht.get("inverter_status_charge_on")));
				LogLevel.DEBUG(5, new Throwable(), "inverter_status_charge_on :" + String.valueOf(ht.get("inverter_status_charge_on")));
				
				req.setAttribute("inverter_status_main_on",String.valueOf(ht.get("inverter_status_main_on")));
				LogLevel.DEBUG(5, new Throwable(), "inverter_status_main_on :" + String.valueOf(ht.get("inverter_status_main_on")));
				
				req.setAttribute("inverter_status_inverter_on",String.valueOf(ht.get("inverter_status_inverter_on")));
				LogLevel.DEBUG(5, new Throwable(), "inverter_status_inverter_on :" + String.valueOf(ht.get("inverter_status_inverter_on")));
				
				req.setAttribute("inverter_status_overload",String.valueOf(ht.get("inverter_status_overload")));
				LogLevel.DEBUG(5, new Throwable(), "inverter_status_overload :" + String.valueOf(ht.get("inverter_status_overload")));
                                
                req.setAttribute("service_eng_mobile",String.valueOf(ht.get("service_eng_mobile")));
				LogLevel.DEBUG(5, new Throwable(), "service_eng_mobile :" + String.valueOf(ht.get("service_eng_mobile")));
			}
		}
		catch (Exception e)
		{
			LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
			e.printStackTrace();
		}
		return strRes;
	}	
	
	public String insertcustomerfeedback(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		String strSqlQry="";
		try
		{	
		LogLevel.DEBUG(5, new Throwable(), "Inside function");
		String order_number= (req.getParameter("order_number")!=null)?(req.getParameter("order_number")):"";
		LogLevel.DEBUG(5, new Throwable(), "order_number"+order_number);
		String consumer_name= (req.getParameter("consumer_name")!=null)?(req.getParameter("consumer_name")):"";
		String mobile_number= (req.getParameter("mobile_number")!=null)?(req.getParameter("mobile_number")):"";
		String battery_type= (req.getParameter("battery_type")!=null)?(req.getParameter("battery_type")):"";
		String vehicle_make= (req.getParameter("vehicle_make")!=null)?(req.getParameter("vehicle_make")):"";
		String vehicle_model= (req.getParameter("vehicle_model")!=null)?(req.getParameter("vehicle_model")):"";
		String capacity= (req.getParameter("capacity")!=null)?(req.getParameter("capacity")):"";
		String quantity= (req.getParameter("quantity")!=null)?(req.getParameter("quantity")):"";	   
		String terminals_src_spc= (req.getParameter("terminals_src_spc")!=null)?(req.getParameter("terminals_src_spc")):"";	   
		String battery_cleanup_cloth= (req.getParameter("battery_cleanup_cloth")!=null)?(req.getParameter("battery_cleanup_cloth")):"";	   
		String volts_before_car_started= (req.getParameter("volts_before_car_started")!=null)?(req.getParameter("volts_before_car_started")):"";	   
		String volts_after_car_started= (req.getParameter("volts_after_car_started")!=null)?(req.getParameter("volts_after_car_started")):"";	   
		String volts_car_cranking= (req.getParameter("volts_car_cranking")!=null)?(req.getParameter("volts_car_cranking")):"";	   
		String volts_car_acceleration= (req.getParameter("volts_car_acceleration")!=null)?(req.getParameter("volts_car_acceleration")):"";	   
		String battery_good= (req.getParameter("battery_good")!=null)?(req.getParameter("battery_good")):"";	   
		String battery_not_charge= (req.getParameter("battery_not_charge")!=null)?(req.getParameter("battery_not_charge")):"";	   
		String battery_dead= (req.getParameter("battery_dead")!=null)?(req.getParameter("battery_dead")):"";	   
		String battery_overcharge= (req.getParameter("battery_overcharge")!=null)?(req.getParameter("battery_overcharge")):"";	   
		String battery_needs_charging= (req.getParameter("battery_needs_charging")!=null)?(req.getParameter("battery_needs_charging")):"";	   
		String warranty_expiry_date= (req.getParameter("warranty_expiry_date")!=null)?(req.getParameter("warranty_expiry_date")):"";	   
		String volts_power_off= (req.getParameter("volts_power_off")!=null)?(req.getParameter("volts_power_off")):"";	   
		String volts_power_on= (req.getParameter("volts_power_on")!=null)?(req.getParameter("volts_power_on")):"";	   
		String water_gravity= (req.getParameter("water_gravity")!=null)?(req.getParameter("water_gravity")):"";	   
		String star_rated= (req.getParameter("star_rated")!=null)?(req.getParameter("star_rated")):"";	   
		LogLevel.DEBUG(5, new Throwable(), "star_rated :" + star_rated);				
        
        String service_eng_mobile= (req.getParameter("service_eng_mobile")!=null)?(req.getParameter("service_eng_mobile")):"";	   
		LogLevel.DEBUG(5, new Throwable(), "service_eng_mobile :" + service_eng_mobile);
		
		String message= (req.getParameter("message")!=null)?(req.getParameter("message")):"";	   
			
			consumer_name =consumer_name.replaceAll("\\+", " ");
			battery_type =battery_type.replaceAll("\\+", " ");
			vehicle_make =vehicle_make.replaceAll("\\+", " ");
			vehicle_model =vehicle_model.replaceAll("\\+", " ");
			capacity =capacity.replaceAll("\\+", " ");
			quantity =quantity.replaceAll("\\+", " ");
			message =message.replaceAll("\\+", " ");

			String Get_order_number = "select order_number from customer_health_check_feedback where order_number='"+order_number+"'";
			LogLevel.DEBUG(5, new Throwable(), "Get_order_number :" + Get_order_number);

			Hashtable Get_order_number_HT = qm.getRow(Get_order_number);
			LogLevel.DEBUG(5, new Throwable(), "Get_order_number_HT :" + Get_order_number_HT);

			if(Get_order_number_HT.isEmpty())
			{
				//#### In changes in bellow condition, You need to Update the same in InsertOrderDetails_XXXXX Condition #####
				strSqlQry = "insert into customer_health_check_feedback(hcf_id,order_number,consumer_name,mobile_number,battery_type,vehicle_make,vehicle_model,capacity,quantity,terminals_src_spc,battery_cleanup_cloth,volts_before_car_started,volts_after_car_started,volts_car_cranking,volts_car_acceleration,battery_good,battery_not_charge,battery_dead,battery_overcharge,battery_needs_charging,warranty_expiry_date,creation_date,volts_power_off,volts_power_on,water_gravity,star_rated,message,service_eng_mobile)values(NULL,'"+order_number+"','"+consumer_name+"','"+mobile_number+"','"+battery_type+"','"+vehicle_make+"','"+vehicle_model+"','"+capacity+"','"+quantity+"','"+terminals_src_spc+"','"+battery_cleanup_cloth+"','"+volts_before_car_started+"','"+volts_after_car_started+"','"+volts_car_cranking+"','"+volts_car_acceleration+"','"+battery_good+"','"+battery_not_charge+"','"+battery_dead+"','"+battery_overcharge+"','"+battery_needs_charging+"','"+warranty_expiry_date+"',now(),'"+volts_power_off+"','"+volts_power_on+"','"+water_gravity+"','"+star_rated+"','"+message+"','"+service_eng_mobile+"')";
				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
				
				int reslt = qm.executeUpdate(strSqlQry);
				if(reslt<0)
				{
					strRes="Failed to Insert FeedBack";
					return strRes;
				}
				else
				{
					strRes="Successfully Added";
				}
				
			}
			else
			{
				strRes="Your FeedBack Already Submited";
			}
			
		}
		catch (Exception e)
		{
			LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
			e.printStackTrace();
		}
		return strRes;
	}	
	
	
	public String insertcustomerfeedbackbattery(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		String strSqlQry="";
		try
		{	
		LogLevel.DEBUG(5, new Throwable(), "Inside function");
		String ordernumber= (req.getParameter("ordernumber")!=null)?(req.getParameter("ordernumber")):"";
		LogLevel.DEBUG(5, new Throwable(), "ordernumber"+ordernumber);
		String username= (req.getParameter("username")!=null)?(req.getParameter("username")):"";
		String mobile_number= (req.getParameter("usermobilenumber")!=null)?(req.getParameter("usermobilenumber")):"";
		String producttype= (req.getParameter("producttype")!=null)?(req.getParameter("producttype")):"";
		String product_brand_model= (req.getParameter("product_brand_model")!=null)?(req.getParameter("product_brand_model")):"";
		String vehiclemakemodel= (req.getParameter("vehiclemakemodel")!=null)?(req.getParameter("vehiclemakemodel")):"";
		String capacity= (req.getParameter("capacity")!=null)?(req.getParameter("capacity")):"";
		String terminalclean= (req.getParameter("terminalclean")!=null)?(req.getParameter("terminalclean")):"";
		String batteryclean= (req.getParameter("batteryclean")!=null)?(req.getParameter("batteryclean")):"";	   
		String check_water_gravity= (req.getParameter("check_water_gravity")!=null)?(req.getParameter("check_water_gravity")):"";	   
		String provideinvoicebill= (req.getParameter("provideinvoicebill")!=null)?(req.getParameter("provideinvoicebill")):"";	 
		String volts_before_car_started= (req.getParameter("volts_before_car_started")!=null)?(req.getParameter("volts_before_car_started")):"";	 
		String volts_after_car_started= (req.getParameter("volts_after_car_started")!=null)?(req.getParameter("volts_after_car_started")):"";	   
		String volts_car_cranking= (req.getParameter("volts_car_cranking")!=null)?(req.getParameter("volts_car_cranking")):"";	   
		String volts_car_acceleration= (req.getParameter("volts_car_acceleration")!=null)?(req.getParameter("volts_car_acceleration")):"";	   
		String warrantyexpirydate= (req.getParameter("warrantyexpirydate")!=null)?(req.getParameter("warrantyexpirydate")):"";	   
		String providewarrantycard= (req.getParameter("providewarrantycard")!=null)?(req.getParameter("providewarrantycard")):"";	   
		String cus_satisfied= (req.getParameter("cus_satisfied")!=null)?(req.getParameter("cus_satisfied")):"";	   
		String message= (req.getParameter("message")!=null)?(req.getParameter("message")):"";	   
		String exisiting_inverter_working= (req.getParameter("exisiting_inverter_working")!=null)?(req.getParameter("exisiting_inverter_working")):"";	   
		String exisiting_inverter_make_model= (req.getParameter("exisiting_inverter_make_model")!=null)?(req.getParameter("exisiting_inverter_make_model")):"";	   	   
		String star_rated= (req.getParameter("star_rated")!=null)?(req.getParameter("star_rated")):"";	
        String service_eng_mobile= (req.getParameter("service_eng_mobile")!=null)?(req.getParameter("service_eng_mobile")):"";	   
		LogLevel.DEBUG(5, new Throwable(), "service_eng_mobile :" + service_eng_mobile);        
			
			username =username.replaceAll("\\+", " ");
			producttype =producttype.replaceAll("\\+", " ");
			vehiclemakemodel =vehiclemakemodel.replaceAll("\\+", " ");
			capacity =capacity.replaceAll("\\+", " ");
			message =message.replaceAll("\\+", " ");

			String Get_order_number = "select order_number from customer_installation_feedback where order_number='"+ordernumber+"'";
			LogLevel.DEBUG(5, new Throwable(), "Get_order_number :" + Get_order_number);

			Hashtable Get_order_number_HT = qm.getRow(Get_order_number);
			LogLevel.DEBUG(5, new Throwable(), "Get_order_number_HT :" + Get_order_number_HT);

			if(Get_order_number_HT.isEmpty())
			{
				//#### In changes in bellow condition, You need to Update the same in InsertOrderDetails_XXXXX Condition #####
				strSqlQry = "insert into customer_installation_feedback(cf_id,order_number,consumer_name,mobile_number,producttype,bat_brand_model,vehiclemakemodel,exisiting_inverter_make_model,exisiting_inverter_working,capacity,terminals_src_spc,batteryclean,volts_before_car_started,volts_after_car_started,volts_car_cranking,volts_car_acceleration,provideinvoicebill,warrantyexpirydate,providewarrantycard,cus_satisfied,creation_date,message,star_rated,service_eng_mobile)values(NULL,'"+ordernumber+"','"+username+"','"+mobile_number+"','"+producttype+"','"+product_brand_model+"','"+vehiclemakemodel+"','"+exisiting_inverter_make_model+"','"+exisiting_inverter_working+"','"+capacity+"','"+terminalclean+"','"+batteryclean+"','"+volts_before_car_started+"','"+volts_after_car_started+"','"+volts_car_cranking+"','"+volts_car_acceleration+"','"+provideinvoicebill+"','"+warrantyexpirydate+"','"+providewarrantycard+"','"+cus_satisfied+"',now(),'"+message+"','"+star_rated+"','"+service_eng_mobile+"')";
				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
				
				int reslt = qm.executeUpdate(strSqlQry);
				if(reslt<0)
				{
					strRes="Failed to Insert FeedBack";
					return strRes;
				}
				else
				{
					strRes="Successfully Added";
				}
				
			}
			else
			{
				strRes="Your FeedBack Already Submited";
			}
			
		}
		catch (Exception e)
		{
			LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
			e.printStackTrace();
		}
		return strRes;
	}	
	
	public String insertcustomerfeedbackinverter(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		String strSqlQry="";
		try
		{	
		LogLevel.DEBUG(5, new Throwable(), "Inside function");
		String ordernumber= (req.getParameter("ordernumber")!=null)?(req.getParameter("ordernumber")):"";
		LogLevel.DEBUG(5, new Throwable(), "ordernumber"+ordernumber);
		String username= (req.getParameter("username")!=null)?(req.getParameter("username")):"";
		String mobile_number= (req.getParameter("usermobilenumber")!=null)?(req.getParameter("usermobilenumber")):"";
		String inverter_make_model= (req.getParameter("inverter_make_model")!=null)?(req.getParameter("inverter_make_model")):"";
		String existing_battery_make_model= (req.getParameter("existing_battery_make_model")!=null)?(req.getParameter("existing_battery_make_model")):"";
		String existing_no_batteries= (req.getParameter("existing_no_batteries")!=null)?(req.getParameter("existing_no_batteries")):"";
		String existing_battery_warranty_expiry= (req.getParameter("existing_battery_warranty_expiry")!=null)?(req.getParameter("existing_battery_warranty_expiry")):"";
		String terminalclean= (req.getParameter("terminalclean")!=null)?(req.getParameter("terminalclean")):"";
		String batteryclean= (req.getParameter("batteryclean")!=null)?(req.getParameter("batteryclean")):"";	   
		String volts_power_off= (req.getParameter("volts_power_off")!=null)?(req.getParameter("volts_power_off")):"";	   
		String volts_power_on= (req.getParameter("volts_power_on")!=null)?(req.getParameter("volts_power_on")):"";	   
		String provideinvoicebill= (req.getParameter("provideinvoicebill")!=null)?(req.getParameter("provideinvoicebill")):"";	   
		String warrantyexpirydate= (req.getParameter("warrantyexpirydate")!=null)?(req.getParameter("warrantyexpirydate")):"";	   
		String providewarrantycard= (req.getParameter("providewarrantycard")!=null)?(req.getParameter("providewarrantycard")):"";	   
		String cus_satisfied= (req.getParameter("cus_satisfied")!=null)?(req.getParameter("cus_satisfied")):"";	   
		String inverter_status_charge_on= (req.getParameter("inverter_status_charge_on")!=null)?(req.getParameter("inverter_status_charge_on")):"";	   
		String inverter_status_main_on= (req.getParameter("inverter_status_main_on")!=null)?(req.getParameter("inverter_status_main_on")):"";	   
		String inverter_status_inverter_on= (req.getParameter("inverter_status_inverter_on")!=null)?(req.getParameter("inverter_status_inverter_on")):"";	   
		String inverter_status_overload= (req.getParameter("inverter_status_overload")!=null)?(req.getParameter("inverter_status_overload")):"";	   
		String message= (req.getParameter("message")!=null)?(req.getParameter("message")):"";	   
		String star_rated= (req.getParameter("star_rated")!=null)?(req.getParameter("star_rated")):"";	 
        
        String service_eng_mobile= (req.getParameter("service_eng_mobile")!=null)?(req.getParameter("service_eng_mobile")):"";	   
		LogLevel.DEBUG(5, new Throwable(), "service_eng_mobile :" + service_eng_mobile);           
			
			username =username.replaceAll("\\+", " ");
			existing_battery_make_model =existing_battery_make_model.replaceAll("\\+", " ");
			existing_no_batteries =existing_no_batteries.replaceAll("\\+", " ");
			existing_battery_warranty_expiry =existing_battery_warranty_expiry.replaceAll("\\+", " ");
			message =message.replaceAll("\\+", " ");

			String Get_order_number = "select order_number from customer_inverter_installation_feedback where order_number='"+ordernumber+"'";
			LogLevel.DEBUG(5, new Throwable(), "Get_order_number :" + Get_order_number);

			Hashtable Get_order_number_HT = qm.getRow(Get_order_number);
			LogLevel.DEBUG(5, new Throwable(), "Get_order_number_HT :" + Get_order_number_HT);

			if(Get_order_number_HT.isEmpty())
			{
				//#### In changes in bellow condition, You need to Update the same in InsertOrderDetails_XXXXX Condition #####
				strSqlQry = "insert into customer_inverter_installation_feedback(cf_id,order_number,consumer_name,mobile_number,inverter_make_model,existing_battery_make_model,existing_no_batteries,existing_battery_warranty_expiry,terminals_src_spc,batteryclean,volts_power_off,volts_power_on,provideinvoicebill,warrantyexpirydate,providewarrantycard,inverter_status_charge_on,inverter_status_main_on,inverter_status_inverter_on,inverter_status_overload,cus_satisfied,creation_date,message,star_rated,service_eng_mobile)values(NULL,'"+ordernumber+"','"+username+"','"+mobile_number+"','"+inverter_make_model+"','"+existing_battery_make_model+"','"+existing_no_batteries+"','"+existing_battery_warranty_expiry+"','"+terminalclean+"','"+batteryclean+"','"+volts_power_off+"','"+volts_power_on+"','"+provideinvoicebill+"','"+warrantyexpirydate+"','"+providewarrantycard+"','"+inverter_status_charge_on+"','"+inverter_status_main_on+"','"+inverter_status_inverter_on+"','"+inverter_status_overload+"','"+cus_satisfied+"',now(),'"+message+"','"+star_rated+"','"+service_eng_mobile+"')";
				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
				
				int reslt = qm.executeUpdate(strSqlQry);
				if(reslt<0)
				{
					strRes="Failed to Insert FeedBack";
					return strRes;
				}
				else
				{
					strRes="Successfully Added";
				}
				
			}
			else
			{
				strRes="Your FeedBack Already Submited";
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


