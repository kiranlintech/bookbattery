/***********************************************************************		
	NGIT Confidential. 
	@File Name   : Service_List_Make_Model.java 
	@Description : This Servlet is used to get the List of Services By Make and Model.
	@Author	     : Sai Krishna Daddala
	@Date        : 10th Sep 2016
******************************************************************/ 
 
package com.ngit.servlets.consumers.servicestore; 
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
  
public class Battery_Service extends HttpServlet 
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
			String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?propsMOPConfig.getProperty("baseurldirectory"):"";
			String strLogFilePath = (propsMOPConfig.getProperty("LogFilePath")!=null)?propsMOPConfig.getProperty("LogFilePath"):"/home/ngit/tomcat/webapps/bookbattery/logs/";
			if(strLogFilePath.equals(""))
			strLogFilePath = "/home/ngit/tomcat/webapps/bookbattery/logs/";
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
		* This action is used to send the Function Based On URL
		\* *****************************************************************************************/	
		
		String[] results_FOR_Array  = results_FOR.split("/");
		LogLevel.DEBUG(5, new Throwable(), "results_FOR_Array.length :" + results_FOR_Array.length);
			
		if(results_FOR_Array.length<4 || results_FOR_Array.length<3 || results_FOR_Array.length<2  || results_FOR_Array.length<1 )
		{
			try
			{
				String strRes=Get_Vehical_Make_List(req,res,session, Browser_URL_Array[1]);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.close();
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}
				
		else if(results_FOR_Array.length<5)
		{
			try
			{
				String strRes=Get_Vehical_Model_List(req,res,session, Browser_URL_Array[1]);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.close();
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}
		
		else if(results_FOR_Array.length<6  || results_FOR_Array.length<7 )
		{
			try
			{
				String strRes=Get_Services_List(req,res,session, Browser_URL_Array[1]);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.close();
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}
		
		else if(results_FOR_Array.length>8)
		{
			try
			{
				String strRes=Get_Services_Packages_List(req,res,session, Browser_URL_Array[1]);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.close();
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}
	}
	
	
	/*This method is to fetch the Make List */
	
	public String Get_Vehical_Make_List(HttpServletRequest req , HttpServletResponse res,HttpSession session, String REQ_URL)
	{
		String strRes="";
		
		String Product_City="";
		String Vehicle_Make="";
		
		try
		{	

			String Get_Vehicle_Names_SQL = "SELECT DISTINCT vehicle_name from vehicle_details ORDER BY vehicle_name ASC";
			
			LogLevel.DEBUG(5, new Throwable(), "Get_Vehicle_Names_SQL :" + Get_Vehicle_Names_SQL);
			Vector Vector_Vehicle_Names=qm.executeQuery(Get_Vehicle_Names_SQL);
			
			if(Vector_Vehicle_Names.isEmpty())
			{ 
				strRes="404 Page Not Found";
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/404.jsp");
				RD.forward(req, res);
				out.close();
				return strRes;
			}
			else
			{
				
				req.setAttribute("Vector_Vehicle_Names",Vector_Vehicle_Names);
				LogLevel.DEBUG(5,new Throwable(),"Vector_Vehicle_Names:"+Vector_Vehicle_Names);
			
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/consumer/manufacturers_list.jsp");
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
	
	/*This method is to fetch the Models details for the particular make */
	
	public String Get_Vehical_Model_List(HttpServletRequest req , HttpServletResponse res,HttpSession session, String REQ_URL)
	{
		String strRes="";
		
		String Product_State="";
		String Product_City="";
		String Vehicle_Make="";
		
		try
		{	
			String[] REQ_URL_Array  = REQ_URL.split("/");
			
			LogLevel.DEBUG(5, new Throwable(), "REQ_URL_Array.length :" + REQ_URL_Array.length);
			LogLevel.DEBUG(5, new Throwable(), "strRes  :" + REQ_URL);
		
			Product_State=java.net.URLDecoder.decode(REQ_URL_Array[1], "UTF-8");
			Product_City=java.net.URLDecoder.decode(REQ_URL_Array[2], "UTF-8");
			Vehicle_Make=java.net.URLDecoder.decode(REQ_URL_Array[3], "UTF-8");
			
			Vehicle_Make= Vehicle_Make.replaceAll("-", " ");
			
			String Get_Vehicle_Model_SQL = "select distinct(vehicle_model) from vehicle_details where vehicle_name='"+Vehicle_Make+"' order by vehicle_model asc"; 
			
			Vector Get_Vehicle_Model_Vector=qm.executeQuery(Get_Vehicle_Model_SQL);
			LogLevel.DEBUG(5,new Throwable(),"Get_Vehicle_Model_Vector:"+Get_Vehicle_Model_Vector);
			
			if(Get_Vehicle_Model_Vector.isEmpty())
			{ 
				strRes="404 Page Not Found";
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/404.jsp");
				RD.forward(req, res);
				out.close();
				return strRes;
			}
			else
			{
				
				// Code to GET list of Other Models for Left Menu
				String Get_Vehicle_Names_SQL = "SELECT DISTINCT vehicle_name from vehicle_details ORDER BY RAND() LIMIT 10";
					
				LogLevel.DEBUG(5, new Throwable(), "Get_Vehicle_Names_SQL :" + Get_Vehicle_Names_SQL);
				
				Vector Vector_Vehicle_Names=qm.executeQuery(Get_Vehicle_Names_SQL);
				
				req.setAttribute("Vector_Vehicle_Names",Vector_Vehicle_Names);
				LogLevel.DEBUG(5,new Throwable(),"Vector_Vehicle_Names:"+Vector_Vehicle_Names);
			
			
				req.setAttribute("Vehicle_Model_Vector",Get_Vehicle_Model_Vector);
				LogLevel.DEBUG(5, new Throwable(), "Vehicle_Model_Vector :" + Get_Vehicle_Model_Vector);
				
				req.setAttribute("Vehicle_Make",Vehicle_Make);
				LogLevel.DEBUG(5, new Throwable(), "Vehicle_Make :" + Vehicle_Make);
				
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/consumer/vehical_model_list.jsp");
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
	
	/*This method is to fetch the services details for the particular make and model*/
	
	public String Get_Services_List(HttpServletRequest req , HttpServletResponse res,HttpSession session, String REQ_URL)
	{
		String strRes="";
		
		String Product_State="";
		String Product_City="";
		String Vehicle_Make="";
		String Vehicle_Model="";
		
		try
		{	
			String[] REQ_URL_Array  = REQ_URL.split("/");
			
			LogLevel.DEBUG(5, new Throwable(), "REQ_URL_Array.length :" + REQ_URL_Array.length);
			LogLevel.DEBUG(5, new Throwable(), "strRes  :" + REQ_URL);
		
			Product_State=java.net.URLDecoder.decode(REQ_URL_Array[1], "UTF-8");
			Product_City=java.net.URLDecoder.decode(REQ_URL_Array[2], "UTF-8");
			Vehicle_Make=java.net.URLDecoder.decode(REQ_URL_Array[3], "UTF-8");
			Vehicle_Model=java.net.URLDecoder.decode(REQ_URL_Array[4], "UTF-8");
			
			Vehicle_Make = Vehicle_Make.replaceAll("-", " ");
			Vehicle_Model = Vehicle_Model.replaceAll("\\+", " ");
			Vehicle_Model = Vehicle_Model.replaceAll("_", "/");
			
			String Get_Services_List_SQL = "select distinct (a.services_type) from services_details a, vehicle_details b where b.vehicle_model='"+Vehicle_Model+"' and b.vehicle_name='"+Vehicle_Make+"'  and ( a.vehicle_price_type=b.vehicle_size or a.vehicle_price_type=b.vehicle_wheelradius or a.services_package=b.vehicle_ACtype or ( a.vehicle_model='"+Vehicle_Model+"' and a.vehicle_make='"+Vehicle_Make+"') ) ORDER BY a.services_type ASC"; 
			LogLevel.DEBUG(5,new Throwable(),"Get_Services_List_SQL:"+Get_Services_List_SQL);
			
			Vector Get_Services_List_Vector=qm.executeQuery(Get_Services_List_SQL);
			LogLevel.DEBUG(5,new Throwable(),"Get_Services_List_Vector:"+Get_Services_List_Vector);
			
			if(Get_Services_List_Vector.isEmpty())
			{ 
				strRes="404 Page Not Found";
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/404.jsp");
				RD.forward(req, res);
				out.close();
				return strRes;
			}
			else
			{
				String Services_Type_SQL="";
				String Services_Package_SQL="";
				String Services_Type_SQL_Condition="";
				
				req.setAttribute("Services_List_Vector",Get_Services_List_Vector);
				LogLevel.DEBUG(5,new Throwable(),"Services_List_Vector:"+Get_Services_List_Vector);
			
				
				for (int i=0; i<Get_Services_List_Vector.size(); i++)
				{
					Hashtable Get_Services_List_Hashtable =(Hashtable)Get_Services_List_Vector.get(i);
					String Services_Type_XMNTT =String.valueOf(Get_Services_List_Hashtable.get("services_type"));
					Services_Type_XMNTT=Services_Type_XMNTT.trim();
					
					//### This function will create unique condition based on service. ############## START 
					if(Services_Type_XMNTT.equals("Exterior and Interior Cleaning") || Services_Type_XMNTT.equals("Dent Removal and Painting") || Services_Type_XMNTT.equals("Road Side Assistance") || Services_Type_XMNTT.equals("General Checkup") || Services_Type_XMNTT.equals("Car Battery Health Checkup") )
					{
						
						Services_Type_SQL_Condition="and b.vehicle_model='"+Vehicle_Model+"' and b.vehicle_name='"+Vehicle_Make+"' and a.vehicle_price_type=b.vehicle_size ";
						
					}
					else if(Services_Type_XMNTT.equals("AC Tuneup and Repair"))
					{
						
						Services_Type_SQL_Condition="and b.vehicle_model='"+Vehicle_Model+"' and b.vehicle_name='"+Vehicle_Make+"' and a.services_package=b.vehicle_ACtype ";
						
					}
					else if(Services_Type_XMNTT.equals("Tyre Services Wheel Balancing and Alignment"))
					{
						
						Services_Type_SQL_Condition="and b.vehicle_model='"+Vehicle_Model+"' and b.vehicle_name='"+Vehicle_Make+"' and a.vehicle_price_type=b.vehicle_wheelradius ";
						
					}
					else if(Services_Type_XMNTT.equals("Winshield and Glass Repair"))
					{
						
						Services_Type_SQL_Condition=" and a.vehicle_model='"+Vehicle_Model+"' and a.vehicle_make='"+Vehicle_Make+"' and b.vehicle_name=a.vehicle_make and  b.vehicle_model=a.vehicle_model ";
						
					}
					else
					{
						
						Services_Type_SQL_Condition=" ";
						
					}
					
					LogLevel.DEBUG(5, new Throwable(), "Services_Type_XMNTT :" + Services_Type_XMNTT);
					LogLevel.DEBUG(5, new Throwable(), "Services_Type_SQL_Condition :" + Services_Type_SQL_Condition);
					//### This function will create unique condition based on service. ############## END 
					
					
					//### SQL to Get the Service Details for Vehicle Make and Model. ############## START 
					
					Services_Type_SQL="SELECT DISTINCT   a.services_type,a.services_package,b.vehicle_model,a.services_package_display,a.description,a.icon_url,a.icon_name FROM services_details a, vehicle_details b WHERE a.services_type='"+Services_Type_XMNTT+"' "+Services_Type_SQL_Condition+" LIMIT 1 ";
					LogLevel.DEBUG(5, new Throwable(), "Services_Type_SQL :" + Services_Type_SQL);
					
					Vector Services_Type_Vector=qm.executeQuery(Services_Type_SQL);
					
					req.setAttribute(Services_Type_XMNTT+"_Vector",Services_Type_Vector);
					LogLevel.DEBUG(5, new Throwable(), Services_Type_XMNTT+"_Vector : " + Services_Type_Vector);
					
					//### SQL to Get the Service Details for Vehicle Make and Model. ############## END 
					
					
					
					
					//### SQL to Get the Service package for that Particular Vehicle make and model   ############## START 
					
					Services_Package_SQL="SELECT DISTINCT a.services_package FROM services_details a, vehicle_details b WHERE a.services_type='"+Services_Type_XMNTT+"' "+Services_Type_SQL_Condition+" ";
					LogLevel.DEBUG(5, new Throwable(), "Services_Package_SQL :" + Services_Package_SQL);
					
					Vector Services_Package_Vector=qm.executeQuery(Services_Package_SQL);
					
					req.setAttribute(Services_Type_XMNTT+"_Vector_Options",Services_Package_Vector);
					LogLevel.DEBUG(5, new Throwable(), Services_Type_XMNTT+"_Vector_Options : " + Services_Package_Vector);
					
					//### SQL to Get the Service package for that Particular Vehicle make and model   ############## END 
					
				}
				
				
				// Code to GET list of Other Models for Left Menu
				String Get_Vehicle_Names_SQL = "SELECT DISTINCT vehicle_model from vehicle_details where vehicle_name='"+Vehicle_Make+"' ORDER BY RAND() LIMIT 10";
					
				LogLevel.DEBUG(5, new Throwable(), "Get_Vehicle_Names_SQL :" + Get_Vehicle_Names_SQL);
				
				Vector Vector_Vehicle_Model_Names=qm.executeQuery(Get_Vehicle_Names_SQL);
				
				req.setAttribute("Vector_Vehicle_Model_Names",Vector_Vehicle_Model_Names);
				LogLevel.DEBUG(5,new Throwable(),"Vector_Vehicle_Model_Names:"+Vector_Vehicle_Model_Names);
			
		
				req.setAttribute("Vehicle_Make",Vehicle_Make);
				LogLevel.DEBUG(5, new Throwable(), "Vehicle_Make :" + Vehicle_Make);
				
				req.setAttribute("Vehicle_Model",Vehicle_Model);
				LogLevel.DEBUG(5, new Throwable(), "Vehicle_Model :" + Vehicle_Model);
				
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/consumer/service_type_list.jsp");
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
	
	/*This method is to fetch the service Package details for the particular service,make and model*/
	
	public String Get_Services_Packages_List(HttpServletRequest req , HttpServletResponse res,HttpSession session, String REQ_URL)
	{
		String strRes="";
		
		String Product_State="";
		String Product_City="";
		String Vehicle_Make="";
		String Vehicle_Model="";
		String Product_Type="";
		String Product_Capacity="";
		String Services_Type="";
		String Services_Place="";
		String Quantity="";
		
		try
		{	
			String[] REQ_URL_Array  = REQ_URL.split("/");
			
			LogLevel.DEBUG(5, new Throwable(), "REQ_URL_Array.length :" + REQ_URL_Array.length);
			LogLevel.DEBUG(5, new Throwable(), "strRes  :" + REQ_URL);
		
			Product_State=java.net.URLDecoder.decode(REQ_URL_Array[1], "UTF-8");
			Product_City=java.net.URLDecoder.decode(REQ_URL_Array[2], "UTF-8");
			Vehicle_Make=java.net.URLDecoder.decode(REQ_URL_Array[3], "UTF-8");
			Vehicle_Model=java.net.URLDecoder.decode(REQ_URL_Array[4], "UTF-8");
			Product_Type=java.net.URLDecoder.decode(REQ_URL_Array[5], "UTF-8");
			Product_Capacity=java.net.URLDecoder.decode(REQ_URL_Array[6], "UTF-8");
			Services_Type=java.net.URLDecoder.decode(REQ_URL_Array[7], "UTF-8");
			Services_Place=java.net.URLDecoder.decode(REQ_URL_Array[8], "UTF-8");
			Quantity=java.net.URLDecoder.decode(REQ_URL_Array[9], "UTF-8");
			
			Product_State = Product_State.replaceAll("-", " ");
			Product_City = Product_City.replaceAll("-", " ");
			Vehicle_Make = Vehicle_Make.replaceAll("-", " ");
			Vehicle_Model = Vehicle_Model.replaceAll("\\+", " ");
			Vehicle_Model = Vehicle_Model.replaceAll("_", "/");			
			Product_Type = Product_Type.replaceAll("\\+", " ");
			Product_Type = Product_Type.replaceAll("_", "/");		
			Product_Capacity = Product_Capacity.replaceAll("\\+", " ");
			Product_Capacity = Product_Capacity.replaceAll("_", "/");
			Services_Type = Services_Type.replaceAll("-", " ");
			Services_Place = Services_Place.replaceAll("\\+", " ");
			Quantity = Quantity.replaceAll("\\+", " ");
			
			String Services_Package_SQL="";
			String Services_Type_SQL_Condition="";

			//### SQL to Get the Service Details for Vehicle Make and Model. ############## START 
			
			Services_Package_SQL="select service_type,battery_type,store,within_10km from service_prices where service_type='"+Services_Type+"' and battery_type='"+Product_Type+"'";
            
			LogLevel.DEBUG(5, new Throwable(), "Services_Package_SQL :" + Services_Package_SQL);
			
			Vector Services_Package_Vector =qm.executeQuery(Services_Package_SQL);
			
			//### SQL to Get the Service Details for Vehicle Make and Model. ############## END 
			
			if(Services_Package_Vector.isEmpty())
			{ 
				strRes="404 Page Not Found";
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/404.jsp");
				RD.forward(req, res);
				out.close();
				return strRes;
			}
			else
			{
				req.setAttribute("Services_Package_Vector",Services_Package_Vector);
				LogLevel.DEBUG(5, new Throwable(),"Services_Package_Vector" + Services_Package_Vector);
				
				req.setAttribute("Vehicle_Make",Vehicle_Make);
				LogLevel.DEBUG(5, new Throwable(), "Vehicle_Make :" + Vehicle_Make);
				
				req.setAttribute("Vehicle_Model",Vehicle_Model);
				LogLevel.DEBUG(5, new Throwable(), "Vehicle_Model :" + Vehicle_Model);
				
				req.setAttribute("Product_Type",Product_Type);
				LogLevel.DEBUG(5, new Throwable(), "Product_Type :" + Product_Type);
				
				req.setAttribute("Product_Capacity",Product_Capacity);
				LogLevel.DEBUG(5, new Throwable(), "Product_Capacity :" + Product_Capacity);
				
				req.setAttribute("Services_Type",Services_Type);
				LogLevel.DEBUG(5, new Throwable(), "Services_Type :" + Services_Type);
				
				req.setAttribute("Services_Place",Services_Place);
				LogLevel.DEBUG(5, new Throwable(), "Services_Place :" + Services_Place);				
				
				req.setAttribute("Quantity",Quantity);
				LogLevel.DEBUG(5, new Throwable(), "Quantity :" + Quantity);
				
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/service_details.jsp");
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


