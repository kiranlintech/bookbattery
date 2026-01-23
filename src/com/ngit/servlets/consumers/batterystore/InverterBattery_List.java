/***********************************************************************		
	NGIT Confidential. 
	@File Name   : InverterBattery_List.java
	@Description : This Servlet is used to select the Manufacturer Details.
	@Author	     : Sai Krishna Daddala
	@Date        : 10th Sep 2016
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
  
public class InverterBattery_List extends HttpServlet 
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
		try
			{
				String strRes=Get_Inverter_Battery_List(req,res,session, Browser_URL_Array[1]);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}	
	
	}
	
	
	
	public String Get_Inverter_Battery_List(HttpServletRequest req , HttpServletResponse res,HttpSession session, String REQ_URL)
	{
	String strRes="";
	String Vehicle_Models="";
	String Product_Type="";
	String Product_Brand="";
	String Product_State="";
	String Product_City="";
	String Product_Capacity="";
	String Product_Battery_Type="";
	String Product_PriceRange="";
	String Product_PriceType="";
	String Product_Warranty="";
	
	
	String Battery_Brand_Condition="";
	String Battery_Capacity_Condition="";
	String Battery_BatteryType_Condition="";
	String Battery_PriceRange_Condition="";
	String Battery_PriceType_Condition="";
	String Battery_Warranty_Condition="";
		try
		{	
			String[] REQ_URL_Array  = REQ_URL.split("/");
			
			LogLevel.DEBUG(5, new Throwable(), "REQ_URL_Array.length :" + REQ_URL_Array.length);
			LogLevel.DEBUG(5, new Throwable(), "strRes  :" + REQ_URL);
			
			Product_Type=java.net.URLDecoder.decode(REQ_URL_Array[0], "UTF-8");
			
			if(REQ_URL_Array.length>=2){
				Product_Brand=java.net.URLDecoder.decode(REQ_URL_Array[1], "UTF-8");
			}
			else{
				Product_Brand ="All";
			}
			if(REQ_URL_Array.length>=3){
				Product_State =java.net.URLDecoder.decode(REQ_URL_Array[2], "UTF-8");
			}
			else{
				//Product_State ="Karnataka";
				res.sendRedirect(baseUrl+baseurldirectory+Product_Type+"/"+Product_Brand+"/Karnataka/Bangalore/" );
				return strRes;
			}
			if(REQ_URL_Array.length>=4){
				Product_City =java.net.URLDecoder.decode(REQ_URL_Array[3], "UTF-8");
			}
			else{
				//Product_City ="Bangalore";
				res.sendRedirect(baseUrl+baseurldirectory+Product_Type+"/"+Product_Brand+"/Karnataka/Bangalore/" );
				return strRes;
			}
			if(REQ_URL_Array.length>=5){
				Product_Capacity =java.net.URLDecoder.decode(REQ_URL_Array[4], "UTF-8");
			}
			else{
				Product_Capacity ="";
			}
			if(REQ_URL_Array.length>=6){
				Product_Battery_Type =java.net.URLDecoder.decode(REQ_URL_Array[5], "UTF-8");
			}
			else{
				Product_Battery_Type ="";
			}
			if(REQ_URL_Array.length>=7){
				Product_Warranty =java.net.URLDecoder.decode(REQ_URL_Array[6], "UTF-8");
				LogLevel.DEBUG(5, new Throwable(), "Product_Warranty :" + Product_Warranty);
			}
			else{
				Product_Warranty ="";
			}			
			if(REQ_URL_Array.length>=8){
				Product_PriceType =java.net.URLDecoder.decode(REQ_URL_Array[7], "UTF-8");
			}
			else{
				Product_PriceType ="";
			}
			if(REQ_URL_Array.length>=9){
				Product_PriceRange =java.net.URLDecoder.decode(REQ_URL_Array[8], "UTF-8");
			}
			else{
				Product_PriceRange ="";
			}

			
			Product_Type= Product_Type.replaceAll("-", " ");
			Product_Brand= Product_Brand.replaceAll("-", " ");
			Product_State= Product_State.replaceAll("-", " ");
			Product_City= Product_City.replaceAll("-", " ");
			
			
			//Battery Brand Condition
			if(Product_Brand.equals("All"))
			{
				//Battery_Brand_Condition="";
				String Check_Retailer_Location="select distinct city from retailer_location_mapping where retailer_name not like '%Amaron-Pitstop%' and city='"+Product_City+"'";
				
				LogLevel.DEBUG(5,new Throwable(),"Check_Retailer_Location:"+Check_Retailer_Location);
				
				Vector Vector_Check_Retailer_Location=qm.executeQuery(Check_Retailer_Location);
				LogLevel.DEBUG(5,new Throwable(),"Vector_Check_Retailer_Location:"+Vector_Check_Retailer_Location);
			
				if(Vector_Check_Retailer_Location.isEmpty())
				{ 
					Battery_Brand_Condition=" and a.bat_brand in ('Amaron')";
					LogLevel.DEBUG(5,new Throwable(),"Inside empty condition");
					LogLevel.DEBUG(5,new Throwable(),"Battery_Brand_Condition:"+Battery_Brand_Condition);
				}
				else
				{
					Battery_Brand_Condition="";
					LogLevel.DEBUG(5,new Throwable(),"Inside else condition");
					LogLevel.DEBUG(5,new Throwable(),"Battery_Brand_Condition:"+Battery_Brand_Condition);
				}
				
			}
			else
			{
				if(Product_Brand.indexOf(",") >= 0)
				{

					//Battery_Brand_Condition="";
						String Check_Retailer_Location1="select distinct city from retailer_location_mapping where retailer_name not like '%Amaron-Pitstop%' and city='"+Product_City+"'";
						
						LogLevel.DEBUG(5,new Throwable(),"Check_Retailer_Location1:"+Check_Retailer_Location1);
						
						Vector Vector_Check_Retailer_Location1=qm.executeQuery(Check_Retailer_Location1);
						LogLevel.DEBUG(5,new Throwable(),"Vector_Check_Retailer_Location1:"+Vector_Check_Retailer_Location1);
					
						if(Vector_Check_Retailer_Location1.isEmpty())
						{ 
							String Product_Brand_tmp= Product_Brand.replaceAll(",", "','");
							
							if(Product_Brand_tmp.indexOf("Amaron") >= 0)
							{
								Battery_Brand_Condition=" and a.bat_brand in ('Amaron')";
								LogLevel.DEBUG(5,new Throwable(),"Inside empty condition");
								LogLevel.DEBUG(5,new Throwable(),"Battery_Brand_Condition:"+Battery_Brand_Condition);
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
							String Product_Brand_tmp= Product_Brand.replaceAll(",", "','");
							Battery_Brand_Condition= " and a.bat_brand in ('"+Product_Brand_tmp+"')";
							LogLevel.DEBUG(5,new Throwable(),"Inside else condition");
							LogLevel.DEBUG(5,new Throwable(),"Battery_Brand_Condition:"+Battery_Brand_Condition);
						}

				}
				else
				{
					//Battery_Brand_Condition= " and a.bat_brand in ('"+Product_Brand+"')";
						//Battery_Brand_Condition="";
							String Check_Retailer_Location2="select distinct city from retailer_location_mapping where retailer_name not like '%Amaron-Pitstop%' and city='"+Product_City+"'";
							
							LogLevel.DEBUG(5,new Throwable(),"Check_Retailer_Location2:"+Check_Retailer_Location2);
							
							Vector Vector_Check_Retailer_Location2=qm.executeQuery(Check_Retailer_Location2);
							LogLevel.DEBUG(5,new Throwable(),"Vector_Check_Retailer_Location2:"+Vector_Check_Retailer_Location2);
						
							if(Vector_Check_Retailer_Location2.isEmpty())
							{ 
								String Product_Brand_tmp= Product_Brand.replaceAll(",", "','");
								
								if(Product_Brand_tmp.indexOf("Amaron") >= 0)
								{
									Battery_Brand_Condition=" and a.bat_brand in ('Amaron')";
									LogLevel.DEBUG(5,new Throwable(),"Inside empty condition");
									LogLevel.DEBUG(5,new Throwable(),"Battery_Brand_Condition:"+Battery_Brand_Condition);
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
								Battery_Brand_Condition= " and a.bat_brand in ('"+Product_Brand+"')";
								LogLevel.DEBUG(5,new Throwable(),"Inside else condition");
								LogLevel.DEBUG(5,new Throwable(),"Battery_Brand_Condition:"+Battery_Brand_Condition);
							}

				}
			}
			
			//Battery Capacity Condition
			if(Product_Capacity.equals(""))
			{
				Battery_Capacity_Condition="";
			}
			else
			{
				if(Product_Capacity.indexOf("=") >= 0)
				{
					String[] Product_Capacity_Array  = Product_Capacity.split("=");
					String Product_Capacity_tmp =Product_Capacity_Array[1];
					Product_Capacity_tmp= Product_Capacity_tmp.replaceAll(",", "','");
					Battery_Capacity_Condition= " and a.bat_capacity in ('"+Product_Capacity_tmp+"')";
					req.setAttribute("Filter_Product_Capacity",Product_Capacity_Array[1]);
				}
				else
				{
					Battery_Capacity_Condition="";
				}
			}
			
			//Battery Type Condition
			if(Product_Battery_Type.equals(""))
			{
				Battery_BatteryType_Condition="";
			}
			else
			{
				if(Product_Battery_Type.indexOf("=") >= 0)
				{
					String[] Product_Battery_Array  = Product_Battery_Type.split("=");
					String Product_Battery_tmp =Product_Battery_Array[1];
					Product_Battery_tmp= Product_Battery_tmp.replaceAll(",", "','");
					Battery_BatteryType_Condition= " and a.battery_type_flag in ('"+Product_Battery_tmp+"')";
					req.setAttribute("Filter_Product_Battery_Type",Product_Battery_Array[1]);
				}
				else
				{
					Battery_BatteryType_Condition="";
				}
			}
			
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
			
			
			//Battery Warranty Condition to fetch based on warranty
			if(Product_Warranty.equals(""))
			{
				Battery_Warranty_Condition="";
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
					
					Battery_PriceRange_Condition= " and b.bat_witbat_price between "+Product_PriceRange_tmp_0+" and "+Product_PriceRange_tmp_1+"";
					req.setAttribute("Product_PriceRange_tmp_0",Product_PriceRange_Array[0]);
					req.setAttribute("Product_PriceRange_tmp_1",Product_PriceRange_Array[1]);
				}
				else
				{
					Battery_PriceRange_Condition="";
				}
			}
			
			
			String Location_checkup = "select DISTINCT a.bat_brand,a.bat_model,a.veh_model,a.bat_capacity,a.bat_warranty,a.icon_url,a.description,b.bat_act_price,b.bat_witbat_price,b.bat_ret_price,a.battery_type_flag from application_chat_mapping a, batteryprice b where a.bat_type='"+Product_Type+"' and b.state='"+Product_State+"' and b.city='"+Product_City+"' "+Battery_Brand_Condition+" "+Battery_Capacity_Condition+" "+Battery_BatteryType_Condition+" "+Battery_Warranty_Condition+" "+Battery_PriceRange_Condition+" and a.bat_model=b.bat_model "+Battery_PriceType_Condition+"";
			
			
			
			
			String Get_Inverter_Battery_SQL = "select DISTINCT a.bat_brand,a.bat_model,a.veh_model,a.bat_capacity,a.bat_warranty,a.icon_url,a.description,b.bat_act_price,b.bat_witbat_price,b.bat_ret_price,a.battery_type_flag from application_chat_mapping a, batteryprice b where a.bat_type='"+Product_Type+"' and b.state='"+Product_State+"' and b.city='"+Product_City+"' "+Battery_Brand_Condition+" "+Battery_Capacity_Condition+" "+Battery_BatteryType_Condition+" "+Battery_Warranty_Condition+" "+Battery_PriceRange_Condition+" and a.bat_model=b.bat_model "+Battery_PriceType_Condition+"";
			LogLevel.DEBUG(5, new Throwable(), "Get_Inverter_Battery_SQL :" + Get_Inverter_Battery_SQL);
			
			Vector Vector_Inverter_Battery=qm.executeQuery(Get_Inverter_Battery_SQL);
			LogLevel.DEBUG(5,new Throwable(),"Vector_Inverter_Battery:"+Vector_Inverter_Battery);
			
			
			if(Vector_Inverter_Battery.isEmpty())
			{ 
				strRes="404 Page Not Found";
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/404.jsp");
				RD.forward(req, res);
				out.close();
				return strRes;
			}
			else
			{
				//### SQL to Get Battery Brands ####Start
				String Get_Inverter_Battery_Brands_SQL = "select DISTINCT a.bat_brand from application_chat_mapping a, batteryprice b where a.bat_type='Inverter Batteries' and b.state='"+Product_State+"' and b.city='"+Product_City+"' and a.bat_brand=b.bat_brand";
				LogLevel.DEBUG(5, new Throwable(), "Get_Inverter_Battery_Brands_SQL :" + Get_Inverter_Battery_Brands_SQL);
				
				Vector Vector_Inverter_Battery_Brands=qm.executeQuery(Get_Inverter_Battery_Brands_SQL);
				LogLevel.DEBUG(5,new Throwable(),"Vector_Inverter_Battery_Brands:"+Vector_Inverter_Battery_Brands);
				
				req.setAttribute("Vector_Inverter_Battery_Brands",Vector_Inverter_Battery_Brands);
				//### SQL to Get Battery Brands ####End
				
				
				//### SQL to Get Battery Capacity ####Start
				String Get_Inverter_Battery_Capacity_SQL = " select DISTINCT a.bat_capacity from application_chat_mapping a, batteryprice b where a.bat_type='Inverter Batteries' and b.state='"+Product_State+"' and b.city='"+Product_City+"' "+Battery_Brand_Condition+" ORDER BY a.bat_capacity  " ;
				LogLevel.DEBUG(5, new Throwable(), "Get_Inverter_Battery_Brands_SQL :" + Get_Inverter_Battery_Brands_SQL);
				
				Vector Vector_Inverter_Battery_Capacity=qm.executeQuery(Get_Inverter_Battery_Capacity_SQL);
				LogLevel.DEBUG(5,new Throwable(),"Vector_Inverter_Battery_Capacity:"+Vector_Inverter_Battery_Capacity);
				
				req.setAttribute("Vector_Inverter_Battery_Capacity",Vector_Inverter_Battery_Capacity);
				//### SQL to Get Battery Capacity ####End
				
				//### SQL to Get Battery Type ####Start
				String Get_Inverter_Battery_Type_SQL = " select DISTINCT a.battery_type_flag from application_chat_mapping a, batteryprice b where a.bat_type='Inverter Batteries' and b.state='"+Product_State+"' and b.city='"+Product_City+"' "+Battery_Brand_Condition+" and a.battery_type_flag not in('0') ORDER BY a.bat_capacity" ;
				LogLevel.DEBUG(5, new Throwable(), "Get_Inverter_Battery_Type_SQL :" + Get_Inverter_Battery_Type_SQL);
				
				Vector Vector_Inverter_Battery_Type=qm.executeQuery(Get_Inverter_Battery_Type_SQL);
				LogLevel.DEBUG(5,new Throwable(),"Vector_Inverter_Battery_Type:"+Vector_Inverter_Battery_Type);
				
				req.setAttribute("Vector_Inverter_Battery_Type",Vector_Inverter_Battery_Type);
				//### SQL to Get Battery Type ####End
				
				
				//### SQL to Get Battery Lease and Max Price ####Start 
				String Get_Inverter_Battery_PriceRange_SQL = "SELECT MIN(CONVERT(b.bat_witbat_price, SIGNED INTEGER)) AS min_price,MAX(CONVERT(b.bat_witbat_price, SIGNED INTEGER)) AS max_price FROM application_chat_mapping a, batteryprice b where a.bat_type='Inverter Batteries' and b.state='"+Product_State+"' and b.city='"+Product_City+"' "+Battery_Brand_Condition+""; 
				LogLevel.DEBUG(5, new Throwable(), "Get_Inverter_Battery_PriceRange_SQL :" + Get_Inverter_Battery_PriceRange_SQL);
				
				Vector Vector_Inverter_Battery_PriceRange=qm.executeQuery(Get_Inverter_Battery_PriceRange_SQL);
				LogLevel.DEBUG(5,new Throwable(),"Vector_Inverter_Battery_PriceRange:"+Vector_Inverter_Battery_PriceRange);
				
				req.setAttribute("Vector_Inverter_Battery_PriceRange",Vector_Inverter_Battery_PriceRange);
				//### SQL to Get Battery Lease and Max Price ####End
				
				//### SQL to Get Battery Warranty ####Start 
				
				String Get_Inverter_Battery_Warranty_SQL = " select DISTINCT a.bat_warranty from application_chat_mapping a, batteryprice b where a.bat_type='Inverter Batteries' and b.state='"+Product_State+"' and b.city='"+Product_City+"' "+Battery_Brand_Condition+" and a.bat_model=b.bat_model ORDER BY a.bat_warranty" ;
				
				LogLevel.DEBUG(5, new Throwable(), "Get_Inverter_Battery_Warranty_SQL :" + Get_Inverter_Battery_Warranty_SQL);
				
				Vector Vector_Inverter_Battery_Warranty=qm.executeQuery(Get_Inverter_Battery_Warranty_SQL);
				LogLevel.DEBUG(5,new Throwable(),"Vector_Inverter_Battery_Warranty:"+Vector_Inverter_Battery_Warranty);
				
				req.setAttribute("Vector_Inverter_Battery_Warranty",Vector_Inverter_Battery_Warranty);
				//### SQL to Get Battery Warranty ####Ended here
				

				req.setAttribute("Vector_Inverter_Battery",Vector_Inverter_Battery);
				LogLevel.DEBUG(5, new Throwable(), "Vector_Inverter_Battery :" + Vector_Inverter_Battery);
					
				req.setAttribute("Product_Type",Product_Type);
				LogLevel.DEBUG(5, new Throwable(), "Product_Type:" +Product_Type);
				
				req.setAttribute("Product_Brand",Product_Brand);
				LogLevel.DEBUG(5, new Throwable(), "Product_Brand:" +Product_Brand);
				
				req.setAttribute("Product_State",Product_State);
				LogLevel.DEBUG(5, new Throwable(), "Product_State:" +Product_State);
				
				req.setAttribute("Product_City",Product_City);
				LogLevel.DEBUG(5, new Throwable(), "Product_City:" +Product_City);
				
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/inverterbattery_list.jsp");
				RD.forward(req, res);
				out.close();

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


