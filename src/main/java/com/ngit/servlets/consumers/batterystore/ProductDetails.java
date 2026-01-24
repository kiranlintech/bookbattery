/***********************************************************************		
	NGIT Confidential. 
	@File Name   : ProductDetails.java 
	@Description : This Servlet is used to select the Battery Details.
	@Author	     : Sai Krishna Daddala
	@Date        : 23th Sep 2016
******************************************************************/ 
 
package com.ngit.servlets.consumers.batterystore; 
 
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
  
public class ProductDetails extends HttpServlet 
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
		
		baseUrl =  propsMOPConfig.getProperty("publicUrl");
		LogLevel.DEBUG(5, new Throwable(), "baseUrl :" + baseUrl);
		
		Browser_URL = req.getRequestURL().toString();
		LogLevel.DEBUG(5, new Throwable(), "Browser_URL :" + Browser_URL);
		//out.println(Browser_URL);
		
		String[] Browser_URL_Array=Browser_URL.split("/bookbattery/products/");
		
		String results_FOR = Browser_URL_Array[1];
		//out.println(Browser_URL_Array[0]);
		//out.println(Browser_URL_Array[1]);
		LogLevel.DEBUG(5, new Throwable(), "Browser_URL 1 :" + Browser_URL_Array[0]);
		LogLevel.DEBUG(5, new Throwable(), "Browser_URL 2:" + Browser_URL_Array[1]);
		
		
		/* ****************************************************************************************\
		* This action is used to get vehicle model.
		\* *****************************************************************************************/		

		if(!Browser_URL_Array[1].equals("NULL"))
		{ 
		try
			{
				String strRes=get_product(req,res,session, Browser_URL_Array[1]);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				
				String[] Results_to_JSP=strRes.split("~");
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/product_details.jsp");
				RD.forward(req, res);
				out.close();
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}	
	
	}
	
	
	
	public String get_product(HttpServletRequest req , HttpServletResponse res,HttpSession session, String REQ_URL)
	{
	String strRes="";
	String Battery_Type="";
	String Vehicle_Name="";
	String Vehicle_Model="";
	String Manufacturer="";
	String Model="";
	String State="";
	String City="";
	String FuelType="";
		try
		{	
			//REQ_URL= REQ_URL.replaceAll("-", " ");
			String[] REQ_URL_Array  = REQ_URL.split("/");
			
			LogLevel.DEBUG(5, new Throwable(), "strRes  :" + REQ_URL_Array.length);
			LogLevel.DEBUG(5, new Throwable(), "strRes  :" + REQ_URL);
			
			Manufacturer =java.net.URLDecoder.decode(REQ_URL_Array[0], "UTF-8");
			Model =java.net.URLDecoder.decode(REQ_URL_Array[1], "UTF-8");
			//Battery_Type= Battery_Type.replaceAll("-", " ");
			//Vehicle_Name= Vehicle_Name.replaceAll("-", " ");
			
			if(REQ_URL_Array.length>=2){
				State =java.net.URLDecoder.decode(REQ_URL_Array[2], "UTF-8");
			}
			else{
				State ="Karnataka";
			}
			if(REQ_URL_Array.length>=3){
				City =java.net.URLDecoder.decode(REQ_URL_Array[3], "UTF-8");
			}
			else{
				City ="Bangalore";
			}
			if(REQ_URL_Array.length>=4){
				//FuelType =java.net.URLDecoder.decode(REQ_URL_Array[2], "UTF-8");
				State =java.net.URLDecoder.decode(REQ_URL_Array[2], "UTF-8");
				City =java.net.URLDecoder.decode(REQ_URL_Array[3], "UTF-8");
				LogLevel.DEBUG(5, new Throwable(), "REQ_URL_Array[2]  :" + REQ_URL_Array[2]);
				LogLevel.DEBUG(5, new Throwable(), "REQ_URL_Array[3]   :" + REQ_URL_Array[3] );
				
			}
			else{
				FuelType ="Petrol";
			}
			
			State= State.replaceAll("-", " ");
			City= City.replaceAll("-", " ");
			
			String Product_Type_Cookie="";
			String Product_Type_Condition="";
			Cookie[] cookies = req.getCookies();
			if (cookies != null) 
			{
			 for (Cookie cookie : cookies) {
				 
			   if (cookie.getName().equals("product_type_cookie")) {
					Product_Type_Cookie=cookie.getValue();
				}	 
			  }
			}
			
			if(Product_Type_Cookie.isEmpty() || Product_Type_Cookie.equals("0") || Product_Type_Cookie.equals("") || Product_Type_Cookie == "")
			{
				Product_Type_Condition="";
			}
			else
			{
				Product_Type_Condition= " and a.bat_type in ('"+Product_Type_Cookie+"')";
			}
			LogLevel.DEBUG(5, new Throwable(), "Product_Type_Condition :" + Product_Type_Condition);
			LogLevel.DEBUG(5, new Throwable(), "Product_Type_Cookie :" + Product_Type_Cookie);
			String Get_Product_Details_SQL ="select  DISTINCT a.bat_brand,a.bat_id,a.bat_model,a.veh_name,a.veh_model,a.bat_capacity,a.bat_warranty,a.bat_name,a.icon_url,a.description,b.bat_act_price,b.bat_witbat_price,b.bat_ret_price,a.battery_type_flag,a.bat_type,b.model_stock from application_chat_mapping a, batteryprice b where a.bat_brand='"+Manufacturer+"'  and b.state='"+State+"' and b.city='"+City+"' and a.bat_model='"+Model+"' "+Product_Type_Condition+" and b.bat_model='"+Model+"' ORDER BY a.bat_id DESC LIMIT 1"; 
			LogLevel.DEBUG(5, new Throwable(), "Get_Product_Details_SQL :" + Get_Product_Details_SQL);
			
			Vector Vector_Product_Details=qm.executeQuery(Get_Product_Details_SQL);
			LogLevel.DEBUG(5,new Throwable(),"Vector_Product_Details:"+Vector_Product_Details);
			
			String Get_Product_Details_SQL_2;
			Vector Vector_Product_Details_2;
			
			if(Vector_Product_Details.isEmpty())
			{ 
		
				Get_Product_Details_SQL_2 ="select  DISTINCT a.bat_brand,a.bat_id,a.bat_model,a.veh_name,a.veh_model,a.bat_capacity,a.bat_warranty,a.bat_name,a.icon_url,a.description,b.bat_act_price,b.bat_witbat_price,b.bat_ret_price,a.battery_type_flag,a.bat_type,b.model_stock from application_chat_mapping a, batteryprice b where a.bat_brand='"+Manufacturer+"'  and b.state='"+State+"' and b.city='"+City+"' and a.bat_model='"+Model+"' and b.bat_model='"+Model+"' ORDER BY a.bat_id DESC LIMIT 1"; 
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
				}
			}
			else
			{
				Vector_Product_Details=Vector_Product_Details;
			}
			
			int i=0;
			Hashtable ht=(Hashtable)Vector_Product_Details.get(i);
			req.setAttribute("Battery_Brand",String.valueOf(ht.get("bat_brand")));
			LogLevel.DEBUG(5, new Throwable(), "Battery_Brand :" + String.valueOf(ht.get("bat_brand")));
			
			req.setAttribute("Battery_Type",String.valueOf(ht.get("bat_type")));
			LogLevel.DEBUG(5, new Throwable(), "Battery_Type :" + String.valueOf(ht.get("bat_type")));
			
			Battery_Type=String.valueOf(ht.get("bat_type"));
			
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
			
			req.setAttribute("Battery_Flag",String.valueOf(ht.get("battery_type_flag")));
			LogLevel.DEBUG(5, new Throwable(), "Battery_Flag :" + String.valueOf(ht.get("battery_type_flag")));
			
			req.setAttribute("Stock_Status",String.valueOf(ht.get("model_stock")));
			LogLevel.DEBUG(5, new Throwable(), "Stock_Status :" + String.valueOf(ht.get("model_stock")));
			
			req.setAttribute("State",State);
			LogLevel.DEBUG(5, new Throwable(), "State :" + State);
			
			req.setAttribute("City",City);
			LogLevel.DEBUG(5, new Throwable(), "City :" + City);
			
			req.setAttribute("FuelType","");
			LogLevel.DEBUG(5, new Throwable(), "FuelType :");
			
			// SQL to get Recomended products List			
			String Get_Other_Vehicle_List_SQL ="select DISTINCT a.veh_name,a.veh_model,a.bat_type from application_chat_mapping a, batteryprice b where a.bat_brand='"+Manufacturer+"'  and b.state='"+State+"' and b.city='"+City+"' and a.bat_model='"+Model+"' and b.bat_model='"+Model+"' and a.bat_type not in('Inverter Batteries') ORDER BY a.veh_name ASC"; 
			LogLevel.DEBUG(5, new Throwable(), "Get_Other_Vehicle_List_SQL :" + Get_Other_Vehicle_List_SQL);
			
			Vector Vector_Other_Vehicle_List_SQL=qm.executeQuery(Get_Other_Vehicle_List_SQL);
			LogLevel.DEBUG(5,new Throwable(),"Vector_Other_Vehicle_List_SQL:"+Vector_Other_Vehicle_List_SQL);
			
			req.setAttribute("Vector_Other_Vehicle_List",Vector_Other_Vehicle_List_SQL);
			LogLevel.DEBUG(5, new Throwable(), "Vector_Other_Vehicle_List_SQL :" + Vector_Other_Vehicle_List_SQL);
			
			
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
			

			// SQL to get Similar Batteries baced on AH 
			String Get_Similar_Batteries_List_SQL ="SELECT DISTINCT a.bat_model, a.bat_brand,a.bat_capacity,a.bat_warranty,a.icon_url,b.bat_act_price,b.bat_witbat_price,b.bat_ret_price,a.battery_type_flag from application_chat_mapping a, batteryprice b where a.bat_capacity='"+Battery_Capacity_tmp+"' and a.bat_type in ('"+Battery_Type+"') and b.state='"+State+"' and b.city='"+City+"' and a.bat_model=b.bat_model ORDER BY RAND() LIMIT 10";
			LogLevel.DEBUG(5, new Throwable(), "Get_Similar_Batteries_List_SQL :" + Get_Similar_Batteries_List_SQL);
			
			Vector Vector_Get_Similar_Batteries_List=qm.executeQuery(Get_Similar_Batteries_List_SQL);
			LogLevel.DEBUG(5,new Throwable(),"Vector_Get_Similar_Batteries_List:"+Vector_Get_Similar_Batteries_List);
			
			req.setAttribute("Vector_Get_Similar_Batteries_List",Vector_Get_Similar_Batteries_List);
			
			//return strRes;
		}
		catch (Exception e)
		{
			LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
			e.printStackTrace();
		}
		return strRes;
	}
}// end of class


