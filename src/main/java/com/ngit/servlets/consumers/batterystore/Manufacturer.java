/***********************************************************************		
	NGIT Confidential. 
	@File Name   : Manufacturer.java 
	@Description : This Servlet is used to select the Manufacturer Details.
	@Author	     : Sai Krishna Daddala
	@Date        : 10th Sep 2016
******************************************************************/ 
 
package com.ngit.servlets.consumers.batterystore; 

import com.ngit.javabean.consumers.products.GetCookie;
 
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
  
public class Manufacturer extends HttpServlet 
{  
	private ServletContext context; 
	QueryManager qm;
	static final long serialVersionUID=21;
	/*This init method initializes the necessary connection pools and perform the transactions on the pools from respectvie files. */
	String Browser_URL="";
	String baseUrl ="";
	String Brand ="";
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
     * @throws IOException if an I/O error occurss
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
		
		baseUrl =  propsMOPConfig.getProperty("publicUrl");
		LogLevel.DEBUG(5, new Throwable(), "baseUrl :" + baseUrl);
		
		Browser_URL = req.getRequestURL().toString();
		LogLevel.DEBUG(5, new Throwable(), "Browser_URL :" + Browser_URL);
		//out.println(Browser_URL);
		
		String[] Browser_URL_Array=Browser_URL.split("/bookbattery/");
		
		String results_FOR = Browser_URL_Array[1];
		//out.println(Browser_URL_Array[0]);
		//out.println(Browser_URL_Array[1]);
		LogLevel.DEBUG(5, new Throwable(), "Browser_URL 1 :" + Browser_URL_Array[0]);
		LogLevel.DEBUG(5, new Throwable(), "Browser_URL 2:" + Browser_URL_Array[1]);
		
		
		results_FOR= results_FOR.replaceAll("-", " ");
		String[] REQ_URL_Array_2 = results_FOR.split("/");
		
		/* ****************************************************************************************\
		* This action is used to get vehicle model. without state and city
		\* *****************************************************************************************/		
		if(REQ_URL_Array_2[0].equals("manufacturer"))
		{ 
		try
			{
				String strRes=get_manufacturer(req,res,session, Browser_URL_Array[1]);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				
				String[] Results_to_JSP=strRes.split("~");
				req.setAttribute("Products_of_Manufacturer",Results_to_JSP[0]);
				LogLevel.DEBUG(5, new Throwable(), "Results_to_JSP[0] :" + Results_to_JSP[0]);
				req.setAttribute("Name_of_Manufacturer",Results_to_JSP[1]);
				LogLevel.DEBUG(5, new Throwable(), "Results_to_JSP[1] :" + Results_to_JSP[1]);
				req.setAttribute("Type_of_Manufacturer",Results_to_JSP[2]);
				LogLevel.DEBUG(5, new Throwable(), "Results_to_JSP[2] :" + Results_to_JSP[2]);
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/manufacturer.jsp");
				RD.forward(req, res);
				out.close();
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}	
		/* ****************************************************************************************\
		* This action is used to get vehicle model with state and city
		\* *****************************************************************************************/	
		if(REQ_URL_Array_2[0].equals("manufacturers"))
		{ 
		try
			{
				String strRes=get_manufacturer_with_state(req,res,session, Browser_URL_Array[1]);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				
				String[] Results_to_JSP=strRes.split("~");
				req.setAttribute("Products_of_Manufacturer",Results_to_JSP[0]);
				LogLevel.DEBUG(5, new Throwable(), "Results_to_JSP[0] :" + Results_to_JSP[0]);
				req.setAttribute("Name_of_Manufacturer",Results_to_JSP[1]);
				LogLevel.DEBUG(5, new Throwable(), "Results_to_JSP[1] :" + Results_to_JSP[1]);
				req.setAttribute("Type_of_Manufacturer",Results_to_JSP[2]);
				LogLevel.DEBUG(5, new Throwable(), "Results_to_JSP[2] :" + Results_to_JSP[2]);
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/manufacturer.jsp");
				RD.forward(req, res);
				out.close();
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}	
	
		/* ****************************************************************************************\
		* This action is used to get vehicle model with state and city
		\* *****************************************************************************************/	
		if(REQ_URL_Array_2[0].equals("fueltype"))
		{ 
		try
			{
				String strRes=get_fuel_types(req,res,session, Browser_URL_Array[1]);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				
				String[] Results_to_JSP=strRes.split("~");
				req.setAttribute("Products_of_Manufacturer",Results_to_JSP[0]);
				LogLevel.DEBUG(5, new Throwable(), "Results_to_JSP[0] :" + Results_to_JSP[0]);
				req.setAttribute("Name_of_Manufacturer",Results_to_JSP[1]);
				LogLevel.DEBUG(5, new Throwable(), "Results_to_JSP[1] :" + Results_to_JSP[1]);
				req.setAttribute("Type_of_Manufacturer",Results_to_JSP[2]);
				LogLevel.DEBUG(5, new Throwable(), "Results_to_JSP[2] :" + Results_to_JSP[2]);
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/fuel_type_details.jsp");
				RD.forward(req, res);
				out.close();
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}	

		/* ****************************************************************************************\
		* This action is used to get vehicle model with state and city
		\* *****************************************************************************************/	
		if(REQ_URL_Array_2[0].equals("location"))
		{ 
		try
			{
				String strRes=get_locations(req,res,session, Browser_URL_Array[1]);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				
				String[] Results_to_JSP=strRes.split("~");
				req.setAttribute("Products_of_Manufacturer",Results_to_JSP[0]);
				LogLevel.DEBUG(5, new Throwable(), "Results_to_JSP[0] :" + Results_to_JSP[0]);
				req.setAttribute("Name_of_Manufacturer",Results_to_JSP[1]);
				LogLevel.DEBUG(5, new Throwable(), "Results_to_JSP[1] :" + Results_to_JSP[1]);
				req.setAttribute("Type_of_Manufacturer",Results_to_JSP[2]);
				LogLevel.DEBUG(5, new Throwable(), "Results_to_JSP[2] :" + Results_to_JSP[2]);
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/fuel_type_details.jsp");
				RD.forward(req, res);
				out.close();
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}	
		
		
	}
	
	
	
	public String get_manufacturer(HttpServletRequest req , HttpServletResponse res,HttpSession session, String REQ_URL)
	{
	String strRes="";
	String Vehicle_Models="";
	String SQL_fuel_condition ="";
	LogLevel.DEBUG(5, new Throwable(), "Brand :" + Brand);
		try
		{	
			REQ_URL= REQ_URL.replaceAll("-", " ");
			String[] REQ_URL_Array  = REQ_URL.split("/");
			
			LogLevel.DEBUG(5, new Throwable(), "strRes  :" + REQ_URL_Array.length);
			LogLevel.DEBUG(5, new Throwable(), "strRes  :" + REQ_URL);
			
			if(REQ_URL_Array.length < 2)
			{
				strRes="404 Page Not Found";
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/404.jsp");
				RD.forward(req, res);
				out.close();
				return strRes;
			}
			
					
			if(REQ_URL_Array.length < 3)
			{
				REQ_URL_Array[1]=java.net.URLDecoder.decode(REQ_URL_Array[1], "UTF-8");
				REQ_URL_Array[1]=REQ_URL_Array[1].trim();
				
				String Get_Vehicle_Names_SQL;
				
				if(REQ_URL_Array[1].equals("Inverter Batteries"))
				{
					 Get_Vehicle_Names_SQL = "select DISTINCT bat_brand from application_chat_mapping  where bat_type='Inverter Batteries' order by bat_brand asc"; 

				}
				else if((REQ_URL_Array[1].equals("Inverter")) || (REQ_URL_Array[1].equals("Inverter and Battery Combo")))
				{
					Get_Vehicle_Names_SQL = "select distinct(inverter_brand) as bat_brand from inverter_details order by inverter_brand asc"; 
				}
				else
				{
					 Get_Vehicle_Names_SQL = "SELECT DISTINCT vehicle_name from vehicle_details where battery_type='"+REQ_URL_Array[1]+"' order by field(vehicle_name,'Ford','Nissan','Renault','Skoda','Volks Wagen','Honda','Toyota','Mahindra and Mahindra','Tata','Hyundai','Maruti Suzuki') desc"; 
				}
				
				LogLevel.DEBUG(5, new Throwable(), "Get_Vehicle_Names_SQL :" + Get_Vehicle_Names_SQL);
				
				Vector Vector_Vehicle_Names=qm.executeQuery(Get_Vehicle_Names_SQL);
				LogLevel.DEBUG(5,new Throwable(),"Vector_Vehicle_Names:"+Vector_Vehicle_Names);
				
				if(Vector_Vehicle_Names.isEmpty())
				{ 
					req.setAttribute("404_report","No Details Found");
					LogLevel.DEBUG(5, new Throwable(), "404_report : No Details Found");
					RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/404.jsp");
					RD.forward(req, res);
					out.close();
					return strRes;
				}
				else
				{
									
					String Get_Vehicle_Type_SQL = "SELECT DISTINCT bat_type from application_chat_mapping ORDER BY RAND() LIMIT 10";
					
					LogLevel.DEBUG(5, new Throwable(), "Get_Vehicle_Type_SQL :" + Get_Vehicle_Type_SQL);
					
					Vector Vector_Vehicle_Type=qm.executeQuery(Get_Vehicle_Type_SQL);
					
					req.setAttribute("Vector_Vehicle_Type",Vector_Vehicle_Type);
					LogLevel.DEBUG(5,new Throwable(),"Vector_Vehicle_Type:"+Vector_Vehicle_Type);
				
					req.setAttribute("Vector_Vehicle_Names",Vector_Vehicle_Names);
					LogLevel.DEBUG(5, new Throwable(), "Vector_Vehicle_Names :" + Vector_Vehicle_Names);
					req.setAttribute("Type_of_Manufacturer",REQ_URL_Array[1]);
					LogLevel.DEBUG(5, new Throwable(), "REQ_URL_Array[1] :" + REQ_URL_Array[1]);
					RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/manufacturer_list_display.jsp");
					RD.forward(req, res);
					out.close();
					return strRes;
				}
			}
			/*if(REQ_URL_Array.length<6)
			{
				REQ_URL_Array[3]=java.net.URLDecoder.decode(REQ_URL_Array[2], "UTF-8");
			}
			else
			{
				REQ_URL_Array[3]=java.net.URLDecoder.decode(REQ_URL_Array[3], "UTF-8");
			}*/
			REQ_URL_Array[2]=java.net.URLDecoder.decode(REQ_URL_Array[2], "UTF-8");
			REQ_URL_Array[1]=java.net.URLDecoder.decode(REQ_URL_Array[1], "UTF-8");
			
			if(REQ_URL_Array[3].equals("ALL")|| REQ_URL_Array[3].equals("All")|| REQ_URL_Array[3].equals("all")|| REQ_URL_Array[3].equals("aLL")||REQ_URL_Array[3].equals("")) {
				
				SQL_fuel_condition = "";
				LogLevel.DEBUG(5,new Throwable(),"Inside if fueltype condition");
			}
			else 
			{
				LogLevel.DEBUG(5,new Throwable(),"Inside else fueltype condition");
				SQL_fuel_condition= "";
	
			}

			
			
			String Get_Vehicle_Details_SQL = "select veh_id,vehicle_model from vehicle_details where vehicle_name='"+REQ_URL_Array[2]+"' and battery_type='"+REQ_URL_Array[1]+"' "+SQL_fuel_condition+" order by vehicle_model asc"; 
			LogLevel.DEBUG(5, new Throwable(), "Get_Vehicle_Details_SQL :" + Get_Vehicle_Details_SQL);
			
			Vector Vector_Vehicle_Details=qm.executeQuery(Get_Vehicle_Details_SQL);
			LogLevel.DEBUG(5,new Throwable(),"Vector_Vehicle_Details:"+Vector_Vehicle_Details);
			
			// Code to GET list of Other Models for Left Menu
			String Get_Vehicle_Names_SQL = "SELECT DISTINCT vehicle_name,battery_type from vehicle_details where battery_type='"+REQ_URL_Array[1]+"' order by field(vehicle_name,'Ford','Nissan','Renault','Skoda','Volks Wagen','Honda','Toyota','Mahindra and Mahindra','Tata','Hyundai','Maruti Suzuki') desc LIMIT 10";
				
			LogLevel.DEBUG(5, new Throwable(), "Get_Vehicle_Names_SQL :" + Get_Vehicle_Names_SQL);
			
			Vector Vector_Vehicle_Names=qm.executeQuery(Get_Vehicle_Names_SQL);
			req.setAttribute("Vector_Vehicle_Names",Vector_Vehicle_Names);
			LogLevel.DEBUG(5,new Throwable(),"Vector_Vehicle_Names:"+Vector_Vehicle_Names);
			
			if(Vector_Vehicle_Details.isEmpty())
			{ 
				strRes="404 Page Not Found";
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/404.jsp");
				RD.forward(req, res);
				out.close();
				return strRes;
			}
			else
			{ 
				// Products_of_Manufacturer_List is declared to get the List of Models and Used for Seo 
				String Products_of_Manufacturer_List="";
				
				for (int i=0; i<Vector_Vehicle_Details.size(); i++)
				{
					Hashtable ht=(Hashtable)Vector_Vehicle_Details.get(i);
					Vehicle_Models =String.valueOf(ht.get("vehicle_model"));
					//strRes=strRes+Vehicle_Models+"</br>";
					String Products_of_Manufacturer_URL=REQ_URL_Array[1];
					LogLevel.DEBUG(5, new Throwable(), "Products_of_Manufacturer_URL :" + Products_of_Manufacturer_URL);
					String Brand=REQ_URL_Array[2];
					LogLevel.DEBUG(5, new Throwable(), "Brand :" + Brand);
					String Name_of_Manufacturer_URL=REQ_URL_Array[2];
					LogLevel.DEBUG(5, new Throwable(), "Name_of_Manufacturer_URL :" + Name_of_Manufacturer_URL);
					//Vehicle_Models=Vehicle_Models.replaceAll("/", "^");
					LogLevel.DEBUG(5, new Throwable(), "Vehicle_Models after replace:" + Vehicle_Models);
					
					String Vehicle_Models_URL=Vehicle_Models;
					// Products_of_Manufacturer_List is declared to get the List of Models and Used for Seo 
					Products_of_Manufacturer_List=Products_of_Manufacturer_List+Vehicle_Models+", ";
					
					Products_of_Manufacturer_URL= Products_of_Manufacturer_URL.replaceAll(" ", "-");
					Name_of_Manufacturer_URL= Name_of_Manufacturer_URL.replaceAll(" ", "-");
					Vehicle_Models_URL= Vehicle_Models_URL.replaceAll(" ", "+");
					Vehicle_Models_URL= Vehicle_Models_URL.replaceAll("/", "|");
					LogLevel.DEBUG(5, new Throwable(), "Vehicle_Models_URL after replace:" + Vehicle_Models_URL);
					
					//################### Getting Location From Cookies 
					GetCookie State_City = new GetCookie(qm);
					String State_City_Resp=  State_City.getCookieStateCity(req,res,session);
					LogLevel.DEBUG(5, new Throwable(), "State_City_Resp :" + State_City_Resp);
					
					String[] State_City_Arr=State_City_Resp.split("~");
					String Product_State_Cookie=State_City_Arr[0];
					String Product_City_Cookie=State_City_Arr[1];
					//################### Getting Location From Cookies 
					
					String Product_State_Cookie_URL= Product_State_Cookie.replaceAll(" ", "-");
					String Product_City_Cookie_URL= Product_City_Cookie.replaceAll(" ", "-");
					
					//strRes=strRes+"<li class='item col-md-6 col-xs-12'><p class='product-name'><a href='"+baseUrl+"/bookbattery/"+Products_of_Manufacturer_URL+"/"+Name_of_Manufacturer_URL+"/"+Vehicle_Models_URL+"/"+Product_State_Cookie_URL+"/"+Product_City_Cookie_URL+"/"+Brand+"/'>"+REQ_URL_Array[2]+"&nbsp;"+Vehicle_Models+"</a></p></li>";//
					
					strRes=strRes+"<li class='item col-md-6 col-xs-12'><p class='product-name'><a href='"+baseUrl+"/bookbattery/"+Products_of_Manufacturer_URL+"/"+Name_of_Manufacturer_URL+"/"+Vehicle_Models_URL+"/"+Product_State_Cookie_URL+"/"+Product_City_Cookie_URL+"/'>"+REQ_URL_Array[2]+"&nbsp;"+Vehicle_Models+"</a></p></li>";
				}
				
				req.setAttribute("Products_of_Manufacturer_List",Products_of_Manufacturer_List);
				LogLevel.DEBUG(5, new Throwable(), "Products_of_Manufacturer_List :" + Products_of_Manufacturer_List);
				
				req.setAttribute("fuel_type","");
				LogLevel.DEBUG(5, new Throwable(), "fuel_type :" );
				
				//req.setAttribute("Brand",Brand);
				//LogLevel.DEBUG(5, new Throwable(), "Brand setted :" + Brand);
				
				return strRes+"~"+REQ_URL_Array[2]+"~"+REQ_URL_Array[1];
			}
		}
		catch (Exception e)
		{
			LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
			e.printStackTrace();
		}
		return strRes;
	}
	
	
	public String get_manufacturer_with_state(HttpServletRequest req , HttpServletResponse res,HttpSession session, String REQ_URL)
	{
	String strRes="";
	String Vehicle_Models="";
		try
		{	
			REQ_URL= REQ_URL.replaceAll("-", " ");
			String[] REQ_URL_Array  = REQ_URL.split("/");
			
			LogLevel.DEBUG(5, new Throwable(), "strRes  :" + REQ_URL_Array.length);
			LogLevel.DEBUG(5, new Throwable(), "strRes  :" + REQ_URL);
			
			if(REQ_URL_Array.length < 2)
			{
				strRes="404 Page Not Found";
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/404.jsp");
				RD.forward(req, res);
				out.close();
				return strRes;
			}

			
			if(REQ_URL_Array.length < 3 || REQ_URL_Array.length > 3)
			{
				REQ_URL_Array[1]=java.net.URLDecoder.decode(REQ_URL_Array[1], "UTF-8");
				REQ_URL_Array[1]=REQ_URL_Array[1].trim();
				
				String Get_Vehicle_Names_SQL;
				
				if(REQ_URL_Array[1].equals("Inverter Batteries"))
				{
					 Get_Vehicle_Names_SQL = "select DISTINCT bat_brand from application_chat_mapping  where bat_type='Inverter Batteries' order by bat_brand asc"; 

					 LogLevel.DEBUG(5, new Throwable(), "Batterytype :" + REQ_URL_Array[1]);
					 
				}
				else if((REQ_URL_Array[1].equals("Inverter")) || (REQ_URL_Array[1].equals("Inverter and Battery Combo")))
				{
					Get_Vehicle_Names_SQL = "select distinct(inverter_brand) as bat_brand from inverter_details order by inverter_brand asc"; 
					
					LogLevel.DEBUG(5, new Throwable(), "Batterytype :" + REQ_URL_Array[1]);
				}				
                else if((REQ_URL_Array[1].equals("Car Battery")) || (REQ_URL_Array[1].equals("Car Batteries")))
				{
					Get_Vehicle_Names_SQL = "SELECT DISTINCT vehicle_name from vehicle_details where battery_type='"+REQ_URL_Array[1]+"' order by field(vehicle_name,'Ford','Nissan','Renault','Skoda','Volks Wagen','Honda','Toyota','Mahindra and Mahindra','Tata','Hyundai','Maruti Suzuki')desc"; 
					
					LogLevel.DEBUG(5, new Throwable(), "Batterytype :" + REQ_URL_Array[1]);
				}
				else if((REQ_URL_Array[1].equals("Bike Battery")) || (REQ_URL_Array[1].equals("Bike Batteries")))
				{
					 Get_Vehicle_Names_SQL = "SELECT DISTINCT vehicle_name from vehicle_details where battery_type='"+REQ_URL_Array[1]+"' order by field(vehicle_name,'TVS','Royal Enfield','Yamaha','Bajaj','Honda','Hero MotoCorp') desc"; 
					 
					 LogLevel.DEBUG(5, new Throwable(), "Batterytype :" + REQ_URL_Array[1]);
				}
                else
                {
                    Get_Vehicle_Names_SQL = "SELECT DISTINCT vehicle_name from vehicle_details where battery_type='"+REQ_URL_Array[1]+"' order by vehicle_name"; 
                    
                }
				
				LogLevel.DEBUG(5, new Throwable(), "Get_Vehicle_Names_SQL :" + Get_Vehicle_Names_SQL);
				
				Vector Vector_Vehicle_Names=qm.executeQuery(Get_Vehicle_Names_SQL);
				LogLevel.DEBUG(5,new Throwable(),"Vector_Vehicle_Names:"+Vector_Vehicle_Names);
				
				if(Vector_Vehicle_Names.isEmpty())
				{ 
					req.setAttribute("404_report","No Details Found");
					LogLevel.DEBUG(5, new Throwable(), "404_report : No Details Found");
					RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/404.jsp");
					RD.forward(req, res);
					out.close();
					return strRes;
				}
				else
				{
									
					String Get_Vehicle_Type_SQL = "SELECT DISTINCT bat_type from application_chat_mapping ORDER BY RAND() LIMIT 10";
					
					LogLevel.DEBUG(5, new Throwable(), "Get_Vehicle_Type_SQL :" + Get_Vehicle_Type_SQL);
					
					Vector Vector_Vehicle_Type=qm.executeQuery(Get_Vehicle_Type_SQL);
					
					req.setAttribute("Vector_Vehicle_Type",Vector_Vehicle_Type);
					LogLevel.DEBUG(5,new Throwable(),"Vector_Vehicle_Type:"+Vector_Vehicle_Type);
				
					req.setAttribute("Vector_Vehicle_Names",Vector_Vehicle_Names);
					LogLevel.DEBUG(5, new Throwable(), "Vector_Vehicle_Names :" + Vector_Vehicle_Names);
					req.setAttribute("Type_of_Manufacturer",REQ_URL_Array[1]);
					LogLevel.DEBUG(5, new Throwable(), "REQ_URL_Array[1] :" + REQ_URL_Array[1]);
                    
					
					if(REQ_URL_Array.length==2)
					{
						req.setAttribute("State","Karnataka");
						req.setAttribute("City","Bangalore");
					}
					else
					{
						req.setAttribute("State",REQ_URL_Array[2]);
						LogLevel.DEBUG(5, new Throwable(), "REQ_URL_Array[1] :" + REQ_URL_Array[1]);
						
						req.setAttribute("City",REQ_URL_Array[3]);
						LogLevel.DEBUG(5, new Throwable(), "REQ_URL_Array[1] :" + REQ_URL_Array[1]);
					}

					RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/manufacturer_list_display.jsp");
					RD.forward(req, res);
					out.close();
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
	
	public String get_fuel_types(HttpServletRequest req , HttpServletResponse res,HttpSession session, String REQ_URL)
	{
	String strRes="";
	String Vehicle_Models="";
		try
		{	
			REQ_URL= REQ_URL.replaceAll("-", " ");
			String[] REQ_URL_Array  = REQ_URL.split("/");
			
			LogLevel.DEBUG(5, new Throwable(), "strRes  :" + REQ_URL_Array.length);
			LogLevel.DEBUG(5, new Throwable(), "strRes  :" + REQ_URL);
			
			if(REQ_URL_Array.length < 2)
			{
				strRes="404 Page Not Found";
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/404.jsp");
				RD.forward(req, res);
				out.close();
				return strRes;
			}
			
			
			if(REQ_URL_Array.length <=3 || REQ_URL_Array.length > 3)
			{
				REQ_URL_Array[1]=java.net.URLDecoder.decode(REQ_URL_Array[1], "UTF-8");
				REQ_URL_Array[1]=REQ_URL_Array[1].trim();
				
				String Get_Fuel_Types_SQL;
				
				if(REQ_URL_Array[1].equals("Inverter Batteries"))
				{
					Get_Fuel_Types_SQL = "select DISTINCT bat_brand from application_chat_mapping  where bat_type='Inverter Batteries' order by bat_brand asc"; 

					 LogLevel.DEBUG(5, new Throwable(), "Batterytype :" + REQ_URL_Array[1]);
					 
				}
				else if((REQ_URL_Array[1].equals("Inverter")) || (REQ_URL_Array[1].equals("Inverter and Battery Combo")))
				{
					Get_Fuel_Types_SQL = "select distinct(inverter_brand) as bat_brand from inverter_details order by inverter_brand asc"; 
					
					LogLevel.DEBUG(5, new Throwable(), "Batterytype :" + REQ_URL_Array[1]);
				}				
                else if((REQ_URL_Array[1].equals("Car Battery")) || (REQ_URL_Array[1].equals("Car Batteries")))
				{
                	Get_Fuel_Types_SQL = "SELECT DISTINCT fuel_type from vehicle_details where battery_type='"+REQ_URL_Array[1]+"' and vehicle_name='"+REQ_URL_Array[2]+"' order by field(fuel_type,'Petrol','Diesel','CNG','LPG')"; 
					
					LogLevel.DEBUG(5, new Throwable(), "Batterytype :" + REQ_URL_Array[1]);
				}
				else if((REQ_URL_Array[1].equals("Bike Battery")) || (REQ_URL_Array[1].equals("Bike Batteries")))
				{
					Get_Fuel_Types_SQL = "SELECT DISTINCT fuel_type from vehicle_details where battery_type='"+REQ_URL_Array[1]+"' and vehicle_name='"+REQ_URL_Array[2]+"' order by field(fuel_type,'Petrol','Diesel','CNG','LPG')"; 
					 
					 LogLevel.DEBUG(5, new Throwable(), "Batterytype :" + REQ_URL_Array[1]);
				}
                else
                {
                	Get_Fuel_Types_SQL = "SELECT DISTINCT fuel_type from vehicle_details where battery_type='"+REQ_URL_Array[1]+"' and vehicle_name='"+REQ_URL_Array[2]+"' order by field(fuel_type,'Petrol','Diesel','CNG','LPG')"; 
                    
                }
				
				LogLevel.DEBUG(5, new Throwable(), "Get_Fuel_Types_SQL :" + Get_Fuel_Types_SQL);
				
				Vector Vector_Fuel_Types=qm.executeQuery(Get_Fuel_Types_SQL);
				LogLevel.DEBUG(5,new Throwable(),"Vector_Fuel_Types:"+Vector_Fuel_Types);
				
				if(Vector_Fuel_Types.isEmpty())
				{ 
					req.setAttribute("404_report","No Details Found");
					LogLevel.DEBUG(5, new Throwable(), "404_report : No Details Found");
					RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/404.jsp");
					RD.forward(req, res);
					out.close();
					return strRes;
				}
				else
				{
									
					String Get_Vehicle_Type_SQL = "SELECT DISTINCT bat_type from application_chat_mapping ORDER BY RAND() LIMIT 10";
					
					LogLevel.DEBUG(5, new Throwable(), "Get_Vehicle_Type_SQL :" + Get_Vehicle_Type_SQL);
					
					Vector Vector_Vehicle_Type=qm.executeQuery(Get_Vehicle_Type_SQL);
					
					req.setAttribute("Vector_Vehicle_Type",Vector_Vehicle_Type);
					LogLevel.DEBUG(5,new Throwable(),"Vector_Vehicle_Type:"+Vector_Vehicle_Type);
				
					req.setAttribute("Vector_Fuel_Types",Vector_Fuel_Types);
					LogLevel.DEBUG(5, new Throwable(), "Vector_Fuel_Types :" + Vector_Fuel_Types);
					req.setAttribute("Type_of_Manufacturer",REQ_URL_Array[1]);
					LogLevel.DEBUG(5, new Throwable(), "REQ_URL_Array[1] :" + REQ_URL_Array[1]);
					req.setAttribute("Vehicle_Name",REQ_URL_Array[2]);
					LogLevel.DEBUG(5, new Throwable(), "REQ_URL_Array[2] :" + REQ_URL_Array[2]);
                    
					
					if(REQ_URL_Array.length <=3)
					{
						req.setAttribute("State","Karnataka");
						req.setAttribute("City","Bangalore");
					}
					else
					{
						req.setAttribute("State",REQ_URL_Array[3]);
						LogLevel.DEBUG(5, new Throwable(), "REQ_URL_Array[3] :" + REQ_URL_Array[3]);
						
						req.setAttribute("City",REQ_URL_Array[4]);
						LogLevel.DEBUG(5, new Throwable(), "REQ_URL_Array[3] :" + REQ_URL_Array[3]);
					}

					RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/fuel_type_details.jsp");
					RD.forward(req, res);
					out.close();
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
	
	public String get_locations(HttpServletRequest req , HttpServletResponse res,HttpSession session, String REQ_URL)
	{
	String strRes="";
	String Vehicle_Models="";
		try
		{	
			REQ_URL= REQ_URL.replaceAll("-", " ");
			String[] REQ_URL_Array  = REQ_URL.split("/");
			
			LogLevel.DEBUG(5, new Throwable(), "strRes  :" + REQ_URL_Array.length);
			LogLevel.DEBUG(5, new Throwable(), "strRes  :" + REQ_URL);
			
			if(REQ_URL_Array.length < 2)
			{
				strRes="404 Page Not Found";
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/404.jsp");
				RD.forward(req, res);
				out.close();
				return strRes;
			}
			
			
			if(REQ_URL_Array.length <=3 || REQ_URL_Array.length > 3)
			{
				REQ_URL_Array[1]=java.net.URLDecoder.decode(REQ_URL_Array[1], "UTF-8");
				REQ_URL_Array[1]=REQ_URL_Array[1].trim();
				
				String Get_Fuel_Types_SQL;
				
				if(REQ_URL_Array[1].equals("Inverter Batteries"))
				{
					Get_Fuel_Types_SQL = "select DISTINCT bat_brand from application_chat_mapping  where bat_type='Inverter Batteries' order by bat_brand asc"; 

					 LogLevel.DEBUG(5, new Throwable(), "Batterytype :" + REQ_URL_Array[1]);
					 
				}
				else if((REQ_URL_Array[1].equals("Inverter")) || (REQ_URL_Array[1].equals("Inverter and Battery Combo")))
				{
					Get_Fuel_Types_SQL = "select distinct(inverter_brand) as bat_brand from inverter_details order by inverter_brand asc"; 
					
					LogLevel.DEBUG(5, new Throwable(), "Batterytype :" + REQ_URL_Array[1]);
				}				
                else if((REQ_URL_Array[1].equals("Car Battery")) || (REQ_URL_Array[1].equals("Car Batteries")))
				{
                	Get_Fuel_Types_SQL = "SELECT DISTINCT fuel_type from vehicle_details where battery_type='"+REQ_URL_Array[1]+"' and vehicle_name='"+REQ_URL_Array[2]+"' order by field(fuel_type,'Petrol','Diesel','CNG','LPG')"; 
					
					LogLevel.DEBUG(5, new Throwable(), "Batterytype :" + REQ_URL_Array[1]);
				}
				else if((REQ_URL_Array[1].equals("Bike Battery")) || (REQ_URL_Array[1].equals("Bike Batteries")))
				{
					Get_Fuel_Types_SQL = "SELECT DISTINCT fuel_type from vehicle_details where battery_type='"+REQ_URL_Array[1]+"' and vehicle_name='"+REQ_URL_Array[2]+"' order by field(fuel_type,'Petrol','Diesel','CNG','LPG')"; 
					 
					 LogLevel.DEBUG(5, new Throwable(), "Batterytype :" + REQ_URL_Array[1]);
				}
                else
                {
                	Get_Fuel_Types_SQL = "SELECT DISTINCT fuel_type from vehicle_details where battery_type='"+REQ_URL_Array[1]+"' and vehicle_name='"+REQ_URL_Array[2]+"' order by field(fuel_type,'Petrol','Diesel','CNG','LPG')"; 
                    
                }
				
				LogLevel.DEBUG(5, new Throwable(), "Get_Fuel_Types_SQL :" + Get_Fuel_Types_SQL);
				
				Vector Vector_Fuel_Types=qm.executeQuery(Get_Fuel_Types_SQL);
				LogLevel.DEBUG(5,new Throwable(),"Vector_Fuel_Types:"+Vector_Fuel_Types);
				
				if(Vector_Fuel_Types.isEmpty())
				{ 
					req.setAttribute("404_report","No Details Found");
					LogLevel.DEBUG(5, new Throwable(), "404_report : No Details Found");
					RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/404.jsp");
					RD.forward(req, res);
					out.close();
					return strRes;
				}
				else
				{
									
					String Get_Vehicle_Type_SQL = "SELECT DISTINCT bat_type from application_chat_mapping ORDER BY RAND() LIMIT 10";
					
					LogLevel.DEBUG(5, new Throwable(), "Get_Vehicle_Type_SQL :" + Get_Vehicle_Type_SQL);
					
					Vector Vector_Vehicle_Type=qm.executeQuery(Get_Vehicle_Type_SQL);
					
					req.setAttribute("Vector_Vehicle_Type",Vector_Vehicle_Type);
					LogLevel.DEBUG(5,new Throwable(),"Vector_Vehicle_Type:"+Vector_Vehicle_Type);
				
					req.setAttribute("Vector_Fuel_Types",Vector_Fuel_Types);
					LogLevel.DEBUG(5, new Throwable(), "Vector_Fuel_Types :" + Vector_Fuel_Types);
					req.setAttribute("Type_of_Manufacturer",REQ_URL_Array[1]);
					LogLevel.DEBUG(5, new Throwable(), "REQ_URL_Array[1] :" + REQ_URL_Array[1]);
					req.setAttribute("Vehicle_Name",REQ_URL_Array[2]);
					LogLevel.DEBUG(5, new Throwable(), "REQ_URL_Array[2] :" + REQ_URL_Array[2]);
                    
					
					if(REQ_URL_Array.length <=3)
					{
						req.setAttribute("State","Karnataka");
						req.setAttribute("City","Bangalore");
					}
					else
					{
						req.setAttribute("State",REQ_URL_Array[3]);
						LogLevel.DEBUG(5, new Throwable(), "REQ_URL_Array[3] :" + REQ_URL_Array[3]);
						
						req.setAttribute("City",REQ_URL_Array[4]);
						LogLevel.DEBUG(5, new Throwable(), "REQ_URL_Array[3] :" + REQ_URL_Array[3]);
					}

					RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/fuel_type_details.jsp");
					RD.forward(req, res);
					out.close();
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
	
}// end of class


