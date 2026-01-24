/***********************************************************************		
	NGIT Confidential. 
	@File Name   : Inverter_Details.java 
	@Description : This Servlet get the Inverter details
	@Author	     : Sai Krishna Daddala.
	@Date        : 30th August 2013
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
  
public class Inverter_Details extends HttpServlet 
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
		
		String[] Browser_URL_Array=Browser_URL.split("/bookbattery/product-inverters/");
		
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
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/inverter_details.jsp");
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
			
			if(REQ_URL_Array.length>=3){
				State =java.net.URLDecoder.decode(REQ_URL_Array[2], "UTF-8");
			}
			else{
				State ="Karnataka";
			}
			if(REQ_URL_Array.length>=4){
				City =java.net.URLDecoder.decode(REQ_URL_Array[3], "UTF-8");
			}
			else{
				City ="Bangalore";
			}
			
			State= State.replaceAll("-", " ");
			City= City.replaceAll("-", " ");

			String Get_Inverter_Details_SQL = "select DISTINCT a.inverter_brand,a.inverter_model,a.inverter_name,a.inverter_capacity,a.inverter_warranty,a.computer,a.fans,a.tubelights,a.television,a.icon_url,a.description,b.inverter_actual_price,b.inverter_discount_price,b.model_stock from inverter_details a, inverter_price_details b  where  b.state='"+State+"' and b.city='"+City+"' and a.inverter_brand='"+Manufacturer+"' and a.inverter_model='"+Model+"' and  b.inverter_model='"+Model+"' LIMIT 1"; 
			LogLevel.DEBUG(5, new Throwable(), "Get_Inverter_SQL :" + Get_Inverter_Details_SQL);
	
			Vector Vector_Inverter_Details=qm.executeQuery(Get_Inverter_Details_SQL);
			LogLevel.DEBUG(5,new Throwable(),"Vector_Inverter_Details:"+Vector_Inverter_Details);
			
			if(Vector_Inverter_Details.isEmpty())
			{ 
				strRes="404 Page Not Found";
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/404.jsp");
				RD.forward(req, res);
				out.close();
				return strRes;
			}
			else
			{
					int i=0;
					Hashtable ht=(Hashtable)Vector_Inverter_Details.get(i);
					req.setAttribute("Inverter_Brand",String.valueOf(ht.get("inverter_brand")));
					LogLevel.DEBUG(5, new Throwable(), "Inverter_Brand :" + String.valueOf(ht.get("inverter_brand")));
					
					req.setAttribute("Inverter_Model",String.valueOf(ht.get("inverter_model")));
					LogLevel.DEBUG(5, new Throwable(), "Inverter_Model :" + String.valueOf(ht.get("inverter_model")));
					
					// This is used to get simular products or Batteries 
					String Inverter_Capacity_tmp=String.valueOf(ht.get("inverter_capacity"));
					
					req.setAttribute("Inverter_Capacity",String.valueOf(ht.get("inverter_capacity")));
					LogLevel.DEBUG(5, new Throwable(), "Inverter_Capacity :" + String.valueOf(ht.get("inverter_capacity")));
					
					req.setAttribute("Inverter_Warranty",String.valueOf(ht.get("inverter_warranty")));
					LogLevel.DEBUG(5, new Throwable(), "Inverter_Warranty :" + String.valueOf(ht.get("inverter_warranty")));
					
					req.setAttribute("Computer",String.valueOf(ht.get("computer")));
					LogLevel.DEBUG(5, new Throwable(), "Computer :" + String.valueOf(ht.get("computer")));
					
					req.setAttribute("Fans",String.valueOf(ht.get("fans")));
					LogLevel.DEBUG(5, new Throwable(), "Fans :" + String.valueOf(ht.get("fans")));
					
					req.setAttribute("Tubelights",String.valueOf(ht.get("tubelights")));
					LogLevel.DEBUG(5, new Throwable(), "Tubelights :" + String.valueOf(ht.get("tubelights")));
					
					req.setAttribute("Television",String.valueOf(ht.get("television")));
					LogLevel.DEBUG(5, new Throwable(), "Television :" + String.valueOf(ht.get("television")));
					
					req.setAttribute("Inverter_Image",String.valueOf(ht.get("icon_url")));
					LogLevel.DEBUG(5, new Throwable(), "Inverter_Image :" + String.valueOf(ht.get("icon_url")));
					
					req.setAttribute("Inverter_Description",String.valueOf(ht.get("description")));
					LogLevel.DEBUG(5, new Throwable(), "Inverter_Description :" + String.valueOf(ht.get("description")));
					
					req.setAttribute("Inverter_Actual_Price",String.valueOf(ht.get("inverter_actual_price")));
					LogLevel.DEBUG(5, new Throwable(), "Inverter_Actual_Price :" + String.valueOf(ht.get("inverter_actual_price")));
					
					req.setAttribute("Inverter_Discount_Price",String.valueOf(ht.get("inverter_discount_price")));
					LogLevel.DEBUG(5, new Throwable(), "Inverter_Discount_Price :" + String.valueOf(ht.get("inverter_discount_price")));
					
					req.setAttribute("Inverter_Stock",String.valueOf(ht.get("model_stock")));
					LogLevel.DEBUG(5, new Throwable(), "Inverter_Stock :" + String.valueOf(ht.get("model_stock")));
					
					req.setAttribute("State",State);
					LogLevel.DEBUG(5, new Throwable(), "State :" + State);
					
					req.setAttribute("City",City);
					LogLevel.DEBUG(5, new Throwable(), "City :" + City);
					
					// SQL to get Similar Batteries baced on AH
					
					String Get_Similar_Inverter_SQL = "select DISTINCT a.inverter_brand,a.inverter_model,a.inverter_capacity,a.inverter_warranty,a.icon_url,b.inverter_actual_price,b.inverter_discount_price from inverter_details a, inverter_price_details b  where b.state='"+State+"' and b.city='"+City+"' and a.inverter_brand=b.inverter_brand and a.inverter_model=b.inverter_model ORDER BY RAND() LIMIT 10";
					LogLevel.DEBUG(5, new Throwable(), "Get_Similar_Inverter_SQL :" + Get_Similar_Inverter_SQL);
					
					Vector Vector_Get_Similar_Inverter=qm.executeQuery(Get_Similar_Inverter_SQL);
					LogLevel.DEBUG(5,new Throwable(),"Vector_Get_Similar_Inverter:"+Vector_Get_Similar_Inverter);
			
					req.setAttribute("Vector_Get_Similar_Inverter",Vector_Get_Similar_Inverter);

					//return strRes;
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


