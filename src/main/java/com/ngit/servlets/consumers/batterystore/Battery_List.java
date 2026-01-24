/***********************************************************************		
	NGIT Confidential. 
	@File Name   : Battery_List.java 
	@Description : This Servlet is used to select the Battery Details.
	@Author	     : Sai Krishna Daddala
	@Date        : 30th August 2016
******************************************************************/ 
 
package com.ngit.servlets.consumers.batterystore; 
 
import static java.lang.System.out;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ngit.javabean.consumers.products.GetCookie;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.qrymgr.QueryManager;
import java.io.File;
  
public class Battery_List extends HttpServlet 
{  
	private ServletContext context; 
	QueryManager qm;
	static final long serialVersionUID=21;
	/*This init method initializes the necessary connection pools and perform the transactions on the pools from respectvie files. */
	String Browser_URL="";
	String baseUrl ="";
	String baseurldirectory ="";
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
			baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?propsMOPConfig.getProperty("baseurldirectory"):"";
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
		
		String[] Browser_URL_Array=Browser_URL.split("/bookbattery/");;
		
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
			
			String[] Split_URL_Array  = Browser_URL_Array[1].split("/");
			
			LogLevel.DEBUG(5, new Throwable(), "strRes  :" + Split_URL_Array.length);

			if(Split_URL_Array.length==4)
			{
				LogLevel.DEBUG(5, new Throwable(), "strRes  :" + Split_URL_Array.length);
				try
				{
					String strRes=get_battery_List(req,res,session, Browser_URL_Array[1]);
					LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
					
					String[] Results_to_JSP=strRes.split("~");
					//req.setAttribute("Product_Vector",Results_to_JSP[0]);
					//LogLevel.DEBUG(5, new Throwable(), "Product_Vector :" + Results_to_JSP[0]);
					//req.setAttribute("Product_Vector",Vector_Batteries_List_Details);
					//LogLevel.DEBUG(5, new Throwable(), "Product_Vector :" + Vector_Batteries_List_Details);
					req.setAttribute("Battery_Type",Results_to_JSP[0]);
					LogLevel.DEBUG(5, new Throwable(), "Battery_Type :" + Results_to_JSP[0]);
					req.setAttribute("Vehicle_Name",Results_to_JSP[1]);
					LogLevel.DEBUG(5, new Throwable(), "Vehicle_Name :" + Results_to_JSP[1]);
					req.setAttribute("Vehicle_Model",Results_to_JSP[2]);
					LogLevel.DEBUG(5, new Throwable(), "Vehicle_Model :" + Results_to_JSP[2]);
					req.setAttribute("State",Results_to_JSP[3]);
					LogLevel.DEBUG(5, new Throwable(), "State :" + Results_to_JSP[3]);
					req.setAttribute("City",Results_to_JSP[4]);
					LogLevel.DEBUG(5, new Throwable(), "City :" + Results_to_JSP[4]);
					//RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/battery_list.jsp");
					RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/product_brands_list.jsp");
					
					RD.forward(req, res);
					out.close();
				}
				catch (Exception e)
				{										
					LogLevel.ERROR(1, e, "Error :" + e);
				}	
			}
			else
			{
				LogLevel.DEBUG(5, new Throwable(), "strRes  :" + Split_URL_Array.length);
			try
			{
				String strRes=get_battery_List(req,res,session, Browser_URL_Array[1]);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				
				String[] Results_to_JSP=strRes.split("~");
				//req.setAttribute("Product_Vector",Results_to_JSP[0]);
				//LogLevel.DEBUG(5, new Throwable(), "Product_Vector :" + Results_to_JSP[0]);
				//req.setAttribute("Product_Vector",Vector_Batteries_List_Details);
				//LogLevel.DEBUG(5, new Throwable(), "Product_Vector :" + Vector_Batteries_List_Details);
				req.setAttribute("Battery_Type",Results_to_JSP[0]);
				LogLevel.DEBUG(5, new Throwable(), "Battery_Type :" + Results_to_JSP[0]);
				req.setAttribute("Vehicle_Name",Results_to_JSP[1]);
				LogLevel.DEBUG(5, new Throwable(), "Vehicle_Name :" + Results_to_JSP[1]);
				req.setAttribute("Vehicle_Model",Results_to_JSP[2]);
				LogLevel.DEBUG(5, new Throwable(), "Vehicle_Model :" + Results_to_JSP[2]);
				req.setAttribute("State",Results_to_JSP[3]);
				LogLevel.DEBUG(5, new Throwable(), "State :" + Results_to_JSP[3]);
				req.setAttribute("City",Results_to_JSP[4]);
				LogLevel.DEBUG(5, new Throwable(), "City :" + Results_to_JSP[4]);
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/battery_list.jsp");
				//RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/product_brands_list.jsp");
				
				RD.forward(req, res);
				out.close();
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
			}
	

		}	
	
	}
	
	
	
	public String get_battery_List(HttpServletRequest req , HttpServletResponse res,HttpSession session, String REQ_URL)
	{
	String strRes="";
	String Battery_Type="";
	String Vehicle_Name="";
	String Vehicle_Model="";
	String Manufacture="";
	String State="";
	String City="";
	String SQL_Condition_Brand="";
	String Product_PriceType="";
	String Battery_PriceType_Condition="";
	String Product_Warranty="";
	String Battery_Warranty_Condition="";
	String Battery_PriceRange_Condition="";
	String SQL_make_model_condition="";
	String SQL_fuel_type_condition="";
	String SQL_make_condition="";
	String Product_PriceRange="";
	String FuelType=""; 
		try
		{	
			//REQ_URL= REQ_URL.replaceAll("-", " ");
			String[] REQ_URL_Array  = REQ_URL.split("/");
			
			LogLevel.DEBUG(5, new Throwable(), "strRes  :" + REQ_URL_Array.length);
			LogLevel.DEBUG(5, new Throwable(), "strRes  :" + REQ_URL);
			

			//################### Getting Location From Cookies 
			GetCookie State_City = new GetCookie(qm);
			String State_City_Resp=  State_City.getCookieStateCity(req,res,session);
			LogLevel.DEBUG(5, new Throwable(), "State_City_Resp :" + State_City_Resp);

			String[] State_City_Arr=State_City_Resp.split("~");
			String Product_State_Cookie=State_City_Arr[0];
			String Product_City_Cookie=State_City_Arr[1];
			String Product_State_Cookie_URL = Product_State_Cookie.replaceAll(" ", "-");
			String Product_City_Cookie_URL = Product_City_Cookie.replaceAll(" ", "-");
			//################### Getting Location From Cookies 
		
			if(REQ_URL_Array.length < 1)
			{
				res.sendRedirect(baseUrl+baseurldirectory+"manufacturers/"+java.net.URLDecoder.decode(REQ_URL_Array[0], "UTF-8")+"/"+Product_State_Cookie_URL+"/"+Product_City_Cookie_URL+"/");
				return strRes;
			}
			if(REQ_URL_Array.length < 2)
			{
				res.sendRedirect(baseUrl+baseurldirectory+"manufacturers/"+java.net.URLDecoder.decode(REQ_URL_Array[0], "UTF-8")+"/"+Product_State_Cookie_URL+"/"+Product_City_Cookie_URL+"/");
				return strRes;
			}
			else if(REQ_URL_Array.length < 3)
			{
				res.sendRedirect(baseUrl+baseurldirectory+"manufacturer/"+java.net.URLDecoder.decode(REQ_URL_Array[0], "UTF-8")+"/"+java.net.URLDecoder.decode(REQ_URL_Array[1], "UTF-8")+"/"+Product_State_Cookie_URL+"/"+Product_City_Cookie_URL+"/");
				return strRes;
			}
			else
			{
				
			}
			
			Battery_Type =java.net.URLDecoder.decode(REQ_URL_Array[0], "UTF-8");
			Vehicle_Name =java.net.URLDecoder.decode(REQ_URL_Array[1], "UTF-8");
			Vehicle_Model =java.net.URLDecoder.decode(REQ_URL_Array[2], "UTF-8");
			Vehicle_Model = Vehicle_Model.replaceAll("%7", "/");
			Vehicle_Model = Vehicle_Model.replaceAll("[|]", "/");
			LogLevel.DEBUG(5, new Throwable(), "Vehicle_Model Coming:" + Vehicle_Model);
			
			Battery_Type= Battery_Type.replaceAll("-", " ");
			Vehicle_Name= Vehicle_Name.replaceAll("-", " ");
			
			if(REQ_URL_Array.length>4){
				State =java.net.URLDecoder.decode(REQ_URL_Array[3], "UTF-8");
			}
			else{	
				State ="Karnataka";
			}
			if(REQ_URL_Array.length>=5){
				City =java.net.URLDecoder.decode(REQ_URL_Array[4], "UTF-8");
			}
			else{
				City ="Bangalore";
			}
			if(REQ_URL_Array.length>=6){
				
				FuelType =java.net.URLDecoder.decode(REQ_URL_Array[3], "UTF-8");	
				LogLevel.DEBUG(5, new Throwable(), "REQ_URL_Array[3]" + REQ_URL_Array[3]);
				LogLevel.DEBUG(5, new Throwable(), "REQ_URL_Array[4]" + REQ_URL_Array[4]);
				LogLevel.DEBUG(5, new Throwable(), "REQ_URL_Array[5]" + REQ_URL_Array[5]);
				LogLevel.DEBUG(5, new Throwable(), "FuelType" + FuelType);
				State =java.net.URLDecoder.decode(REQ_URL_Array[4], "UTF-8");
				City =java.net.URLDecoder.decode(REQ_URL_Array[5], "UTF-8");
			}
			else
			{
				FuelType ="Petrol";
			}
			if(REQ_URL_Array.length>=7){
				
				Manufacture =java.net.URLDecoder.decode(REQ_URL_Array[6], "UTF-8");
			}
			else
			{
				Manufacture ="All";
			}
			
			if(REQ_URL_Array.length>=8){
				
				Manufacture =java.net.URLDecoder.decode(REQ_URL_Array[6], "UTF-8");
				LogLevel.DEBUG(5, new Throwable(), "Manufacture :" + Manufacture);
				Product_Warranty =java.net.URLDecoder.decode(REQ_URL_Array[7], "UTF-8");
				LogLevel.DEBUG(5, new Throwable(), "Product_Warranty :" + Product_Warranty);
			}
			else
			{
				Product_Warranty ="";
				LogLevel.DEBUG(5, new Throwable(), "Product_Warranty :" + Product_Warranty);
			}			
			if(REQ_URL_Array.length>=9){
				Product_PriceType =java.net.URLDecoder.decode(REQ_URL_Array[8], "UTF-8");
			}
			else{
				Product_PriceType ="";
				LogLevel.DEBUG(5, new Throwable(), "Product_PriceType :" + Product_PriceType);
			}
			if(REQ_URL_Array.length>=10){
				
				Product_PriceRange =java.net.URLDecoder.decode(REQ_URL_Array[9], "UTF-8");
				LogLevel.DEBUG(5, new Throwable(), "Product_PriceRange :" + Product_PriceRange);
			}
			else
			{
				Product_PriceRange ="";
				LogLevel.DEBUG(5, new Throwable(), "Product_PriceRange :" + Product_PriceRange);
			}
			
			State	= State.replaceAll("-", " ");
			City	= City.replaceAll("-", " ");
			
			LogLevel.DEBUG(5, new Throwable(), "Manufacture :" + Manufacture);
			
			if(Manufacture.equals("Amaron") || Manufacture.equals("Exide")|| Manufacture.equals("Luminous") || Manufacture.equals("All"))
			{
				Product_Warranty=Product_Warranty;
				Manufacture=Manufacture;
				LogLevel.DEBUG(5, new Throwable(), "Product_Warranty :" + Product_Warranty);
				LogLevel.DEBUG(5, new Throwable(), "Manufacture :" + Manufacture);
			}
			else
			{
				Product_Warranty=Manufacture;
				Manufacture ="All";
				LogLevel.DEBUG(5, new Throwable(), "Product_Warranty :" + Product_Warranty);
				LogLevel.DEBUG(5, new Throwable(), "Manufacture :" + Manufacture);
			}

			Manufacture= Manufacture.replaceAll("-", " ");
			
			req.setAttribute("Manufacture",Manufacture);
			LogLevel.DEBUG(5, new Throwable(), "Manufacture :" + Manufacture);
			
	
			if( Manufacture.equals("All") || Manufacture.equals("") || Manufacture.equals("ALL")|| Manufacture.equals("all") )
			{
				//SQL_Condition_Brand="";
				
				//Battery_Brand_Condition="";
				String Check_Retailer_Location="select distinct city from retailer_location_mapping where retailer_name not like '%Amaron-Pitstop%' and city='"+City+"'";
				
				LogLevel.DEBUG(5,new Throwable(),"Check_Retailer_Location:"+Check_Retailer_Location);
				
				Vector Vector_Check_Retailer_Location=qm.executeQuery(Check_Retailer_Location);
				LogLevel.DEBUG(5,new Throwable(),"Vector_Check_Retailer_Location:"+Vector_Check_Retailer_Location);
			
				if(Vector_Check_Retailer_Location.isEmpty())
				{ 
					SQL_Condition_Brand=" and a.bat_brand in ('Amaron')";
					LogLevel.DEBUG(5,new Throwable(),"Inside empty condition");
					LogLevel.DEBUG(5,new Throwable(),"SQL_Condition_Brand:"+SQL_Condition_Brand);
				}
				else
				{
					SQL_Condition_Brand="";
					LogLevel.DEBUG(5,new Throwable(),"Inside else condition");
					LogLevel.DEBUG(5,new Throwable(),"SQL_Condition_Brand:"+SQL_Condition_Brand);
				}
			}
			else
			{
				//SQL_Condition_Brand="and a.bat_brand='"+Manufacture+"'";
				
				String Check_Retailer_Location1="select distinct city from retailer_location_mapping where retailer_name not like '%Amaron-Pitstop%' and city='"+City+"'";
				
				LogLevel.DEBUG(5,new Throwable(),"Check_Retailer_Location1:"+Check_Retailer_Location1);
				
				Vector Vector_Check_Retailer_Location1=qm.executeQuery(Check_Retailer_Location1);
				LogLevel.DEBUG(5,new Throwable(),"Vector_Check_Retailer_Location1:"+Vector_Check_Retailer_Location1);
			
				if(Vector_Check_Retailer_Location1.isEmpty())
				{ 
					
					if(Manufacture.indexOf("Amaron") >= 0)
					{
						SQL_Condition_Brand="and a.bat_brand='"+Manufacture+"'";
						LogLevel.DEBUG(5,new Throwable(),"Inside else condition");
						LogLevel.DEBUG(5,new Throwable(),"SQL_Condition_Brand:"+SQL_Condition_Brand);
					}
					else
					{
						strRes="404 Page Not Found";
						RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/404.jsp");
						RD.forward(req, res);
						out.close();
						return strRes;
					}

				}
				else
				{
					SQL_Condition_Brand="and a.bat_brand='"+Manufacture+"'";
					LogLevel.DEBUG(5,new Throwable(),"Inside else condition");
					LogLevel.DEBUG(5,new Throwable(),"SQL_Condition_Brand:"+SQL_Condition_Brand);
				}

			}

			
			String SQL_fuel_condition ="";
			
			if( FuelType.equals("All") || FuelType.equals("") || FuelType.equals("ALL")|| FuelType.equals("all") )
			{
				SQL_fuel_type_condition="";
				SQL_fuel_condition = "";
				LogLevel.DEBUG(5,new Throwable(),"Inside if fueltype condition");
			}
			else 
			{
				LogLevel.DEBUG(5,new Throwable(),"Inside else fueltype condition");
				SQL_fuel_type_condition= "";
				SQL_fuel_condition= "";
				
			}

			
			/*Condition if vehicle make and model is all or Empty starts here */
			
			if( Vehicle_Name.equals("All") || Vehicle_Name.equals("") || Vehicle_Name.equals("ALL")|| Vehicle_Name.equals("all") )
			{				
				if( Vehicle_Model.equals("All") || Vehicle_Model.equals("") || Vehicle_Model.equals("ALL")|| Vehicle_Model.equals("all") )
				{
					SQL_make_model_condition="";
				}
				else
				{
					SQL_make_model_condition="and a.veh_model='"+Vehicle_Model+"'";
				}
				
				SQL_make_condition="";
			}
			else
			{
				SQL_make_model_condition="and a.veh_name='"+Vehicle_Name+"' and a.veh_model='"+Vehicle_Model+"'";
				
				SQL_make_condition="and vehicle_name='"+Vehicle_Name+"'";
			}
			
			/*Condition if vehicle make and model is all or Empty ends here */
			
			
			LogLevel.DEBUG(5, new Throwable(), "Product_Warranty :" + Product_Warranty);
			//Battery Warranty Condition
			if(Product_Warranty.equals(""))
			{
				Battery_Warranty_Condition="";
				LogLevel.DEBUG(5, new Throwable(), "Battery_Warranty_Condition :" + Battery_Warranty_Condition);
			}
			else
			{
				if(Product_Warranty.indexOf("=") >= 0)
				{
					String[] Product_Warranty_Array  = Product_Warranty.split("=");
					String Product_Warranty_tmp =Product_Warranty_Array[1];
					LogLevel.DEBUG(5, new Throwable(), "Product_Warranty_tmp :" + Product_Warranty_tmp);
					Product_Warranty_tmp= Product_Warranty_tmp.replaceAll(",", "','");
					LogLevel.DEBUG(5, new Throwable(), "Product_Warranty_tmp :" + Product_Warranty_tmp);
					Product_Warranty_tmp= Product_Warranty_tmp.replaceAll("_"," ");
					
					Product_Warranty_tmp= Product_Warranty_tmp.replaceAll("plus","+");
					LogLevel.DEBUG(5, new Throwable(), "Product_Warranty_tmp :" + Product_Warranty_tmp);
					Battery_Warranty_Condition= " and a.bat_warranty in ('"+Product_Warranty_tmp+"')";
					LogLevel.DEBUG(5, new Throwable(), "Battery_Warranty_Condition :" + Battery_Warranty_Condition);
					req.setAttribute("Filter_Product_Warranty",Product_Warranty_Array[1]);
				}
				else
				{
					Battery_Warranty_Condition="";
				}
			}			
			
			
			//Battery Price Condition to fetch based on warranty
			if(Product_PriceRange.equals(""))
			{
				Battery_PriceRange_Condition="";
			}
			else
			{
				if(Product_PriceRange.indexOf("=") >= 0)
				{
					LogLevel.DEBUG(5, new Throwable(), "Inside");
					
					String[] Product_PriceRange_Array_split  = Product_PriceRange.split("=");

					String[] Product_PriceRange_Array  = Product_PriceRange_Array_split[1].split(",");

					String Product_PriceRange_tmp_0 =Product_PriceRange_Array[0];
					String Product_PriceRange_tmp_1 =Product_PriceRange_Array[1];
					LogLevel.DEBUG(5, new Throwable(), "Product_PriceRange_tmp_0 :" + Product_PriceRange_tmp_0);
					Product_PriceRange_tmp_0= Product_PriceRange_tmp_0.replaceAll(",", "','");
					LogLevel.DEBUG(5, new Throwable(), "Product_PriceRange_tmp_0 :" + Product_PriceRange_tmp_0);
					Product_PriceRange_tmp_0= Product_PriceRange_tmp_0.replaceAll("_"," ");
					Product_PriceRange_tmp_0= Product_PriceRange_tmp_0.replaceAll("plus","+");
					LogLevel.DEBUG(5, new Throwable(), "Product_PriceRange_tmp_0 :" + Product_PriceRange_tmp_0);	
					
					LogLevel.DEBUG(5, new Throwable(), "Product_PriceRange_tmp_1 :" + Product_PriceRange_tmp_1);
					Product_PriceRange_tmp_1= Product_PriceRange_tmp_1.replaceAll(",", "','");
					LogLevel.DEBUG(5, new Throwable(), "Product_PriceRange_tmp_1 :" + Product_PriceRange_tmp_1);
					Product_PriceRange_tmp_1= Product_PriceRange_tmp_1.replaceAll("_"," ");
					Product_PriceRange_tmp_1= Product_PriceRange_tmp_1.replaceAll("plus","+");
					LogLevel.DEBUG(5, new Throwable(), "Product_PriceRange_tmp_1 :" + Product_PriceRange_tmp_1);
					
					Battery_PriceRange_Condition= " and b.bat_witbat_price between "+Product_PriceRange_tmp_0+" and "+Product_PriceRange_tmp_1+" ";
					req.setAttribute("Product_PriceRange_tmp_0",Product_PriceRange_Array[0]);
					req.setAttribute("Product_PriceRange_tmp_1",Product_PriceRange_Array[1]);
				}
				else
				{
					Battery_PriceRange_Condition="";
				}
			}
			
			
			// Code to GET list of Other Models for Left Menu
			//String Get_Vehicle_Models_SQL = "SELECT DISTINCT vehicle_model,vehicle_name,battery_type from vehicle_details where vehicle_name='"+Vehicle_Name+"' and battery_type='"+Battery_Type+"' ORDER BY RAND() LIMIT 10";			
			
			String Get_Vehicle_Models_SQL = "SELECT DISTINCT vehicle_model,vehicle_name,battery_type from vehicle_details where battery_type='"+Battery_Type+"'"+SQL_fuel_type_condition+""+SQL_make_condition+" ORDER BY RAND() LIMIT 10";
							
			LogLevel.DEBUG(5, new Throwable(), "Get_Vehicle_Models_SQL :" + Get_Vehicle_Models_SQL);
			
			Vector Vector_Vehicle_Models=qm.executeQuery(Get_Vehicle_Models_SQL);
			req.setAttribute("Vector_Vehicle_Models",Vector_Vehicle_Models);
			LogLevel.DEBUG(5,new Throwable(),"Vector_Vehicle_Models:"+Vector_Vehicle_Models);
			
			//Product Price Sort Condition
			if(Product_PriceType.indexOf("DESC") >= 0)
			{
				Battery_PriceType_Condition= "ORDER BY CAST(b.bat_witbat_price as unsigned) DESC, CAST(b.bat_ret_price as unsigned) DESC";
				req.setAttribute("Product_PriceType","DESC");
				LogLevel.DEBUG(5, new Throwable(), "Battery_PriceType_Condition :" + Battery_PriceType_Condition);
			}
			else
			{
				Battery_PriceType_Condition= "ORDER BY CAST(b.bat_witbat_price as unsigned) ASC, CAST(b.bat_ret_price as unsigned) ASC";
				req.setAttribute("Product_PriceType","ASC");
				LogLevel.DEBUG(5, new Throwable(), "Battery_PriceType_Condition :" + Battery_PriceType_Condition);
			}
					
			String Get_Batteries_List_Details_SQL ="select distinct(a.bat_model),a.bat_brand,a.bat_capacity,a.bat_warranty,a.icon_url,a.description,b.bat_act_price,b.bat_witbat_price,b.bat_ret_price,a.battery_type_flag from application_chat_mapping a, batteryprice b where a.bat_type='"+Battery_Type+"' "+SQL_make_model_condition+""+SQL_fuel_condition+" and b.state='"+State+"' and b.city='"+City+"' and a.bat_model=b.bat_model "+SQL_Condition_Brand+""+Battery_Warranty_Condition+""+Battery_PriceRange_Condition+""+Battery_PriceType_Condition+"";
			LogLevel.DEBUG(5, new Throwable(), "Get_Batteries_List_Details_SQL :" + Get_Batteries_List_Details_SQL);
			
			Vector Vector_Batteries_List_Details=qm.executeQuery(Get_Batteries_List_Details_SQL);
			LogLevel.DEBUG(5,new Throwable(),"Vector_Batteries_List_Details:"+Vector_Batteries_List_Details);
			
			if(Vector_Batteries_List_Details.isEmpty())
			{ 
				strRes="404 Page Not Found";
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/404.jsp");
				RD.forward(req, res);
				out.close();
				return strRes;
			}
			else
			{
				//### SQL to Get Battery Brand for SEO ####Start 		
				//String Get_Battery_Brand_List_SQL ="select DISTINCT bat_brand from application_chat_mapping where bat_type='"+Battery_Type+"' and veh_name='"+Vehicle_Name+"' and veh_model='"+Vehicle_Model+"'";
				
				String Get_Battery_Brand_List_SQL ="select DISTINCT bat_brand from application_chat_mapping where bat_type='"+Battery_Type+"' "+SQL_make_model_condition+"";
				
				LogLevel.DEBUG(5, new Throwable(), "Get_Battery_Brand_List_SQL :" + Get_Battery_Brand_List_SQL);
			
				Vector Vector_Get_Battery_Brand_List=qm.executeQuery(Get_Battery_Brand_List_SQL);
				
				req.setAttribute("Vector_Get_Battery_Brand_List",Vector_Get_Battery_Brand_List);
				
				LogLevel.DEBUG(5,new Throwable(),"Vector_Get_Battery_Brand_List:"+Vector_Get_Battery_Brand_List);
				//### SQL to Get Battery Brand for SEO ####End
				
				String Get_Batteries_logo_Utrls_SQL ="select distinct(icon_url),brand from brand_logos;";
				
				LogLevel.DEBUG(5, new Throwable(), "Get_Batteries_logo_Urls__SQL :" + Get_Batteries_logo_Utrls_SQL);
				
				Vector Vector_Brand_logo_Urls=qm.executeQuery(Get_Batteries_logo_Utrls_SQL);
				
				LogLevel.DEBUG(5, new Throwable(), "Vector_Brand_logo_Urls :" + Vector_Brand_logo_Urls);
					
				//### SQL to Get Battery Warranty ####Start 				
				String Get_Inverter_Battery_Warranty_SQL = " select DISTINCT a.bat_warranty from application_chat_mapping a, batteryprice b where a.bat_type='"+Battery_Type+"'"+SQL_fuel_condition+" and b.state='"+State+"' and b.city='"+City+"' "+SQL_Condition_Brand+" and a.bat_model=b.bat_model "+SQL_make_model_condition+" ORDER BY a.bat_warranty" ;
				
				LogLevel.DEBUG(5, new Throwable(), "Get_Inverter_Battery_Warranty_SQL :" + Get_Inverter_Battery_Warranty_SQL);
				
				Vector Vector_Inverter_Battery_Warranty=qm.executeQuery(Get_Inverter_Battery_Warranty_SQL);
				LogLevel.DEBUG(5,new Throwable(),"Vector_Inverter_Battery_Warranty:"+Vector_Inverter_Battery_Warranty);
				
				req.setAttribute("Vector_Brand_logo_Urls",Vector_Brand_logo_Urls);
				LogLevel.DEBUG(5, new Throwable(), "Vector_Brand_logo_Urls :" + Vector_Brand_logo_Urls);
				
				req.setAttribute("Vector_Inverter_Battery_Warranty",Vector_Inverter_Battery_Warranty);
				
				req.setAttribute("FuelType",FuelType);
				
				//### SQL to Get Battery Warranty ####End

				req.setAttribute("Product_Vector",Vector_Batteries_List_Details);
				LogLevel.DEBUG(5, new Throwable(), "Product_Vector :" + Vector_Batteries_List_Details);
				
				strRes=Battery_Type+"~"+Vehicle_Name+"~"+Vehicle_Model+"~"+State+"~"+City;
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


