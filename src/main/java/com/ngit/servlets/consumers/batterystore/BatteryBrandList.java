/***********************************************************************		
	NGIT Confidential. 
	@File Name   : Battery_List.java 
	@Description : This Servlet is used to select the Battery Details.
	@Author	     : Sai Krishna Daddala
	@Date        : 30th August 2016
******************************************************************/ 
 
package com.ngit.servlets.consumers.batterystore; 
 
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.consumers.products.GetCookie;
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
  
public class BatteryBrandList extends HttpServlet 
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
		String[] URL_Array=Browser_URL_Array[1].split("/");
		LogLevel.DEBUG(5, new Throwable(), "URL_Array 0 :" + URL_Array[0]);
		LogLevel.DEBUG(5, new Throwable(), "URL_Array 1 :" + URL_Array[1]);
		
		/* ****************************************************************************************\
		* This action is used to get vehicle model.
		\* *****************************************************************************************/		

		if(URL_Array[0].equals("BatteryBrand"))
		{ 
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

				if(Results_to_JSP[1].equals(null))
				{
					req.setAttribute("State","Karnataka");
					req.setAttribute("City","Bangalore");
				}
				else
				{					
					req.setAttribute("State",Results_to_JSP[1]);
					LogLevel.DEBUG(5, new Throwable(), "State :" + Results_to_JSP[1]);
					req.setAttribute("City",Results_to_JSP[2]);
					LogLevel.DEBUG(5, new Throwable(), "City :" + Results_to_JSP[2]);
				}

				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/battery_brand_list.jsp");
				RD.forward(req, res);
				out.close();
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}
		else if(URL_Array[0].equals("Brand"))
		{
			try
			{
				String strRes=get_battery_brands(req,res,session, Browser_URL_Array[1]);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				String[] Results_to_JSP=strRes.split("~");
				req.setAttribute("Battery_Type",Results_to_JSP[0]);
				LogLevel.DEBUG(5, new Throwable(), "Battery_Type :" + Results_to_JSP[0]);
				req.setAttribute("State",Results_to_JSP[1]);
				LogLevel.DEBUG(5, new Throwable(), "State :" + Results_to_JSP[1]);
				req.setAttribute("City",Results_to_JSP[2]);
				LogLevel.DEBUG(5, new Throwable(), "City :" + Results_to_JSP[2]);
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/product_brands_list.jsp");
				RD.forward(req, res);
				out.close();
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}			
	}
	
	
	
	public String get_battery_List(HttpServletRequest req , HttpServletResponse res,HttpSession session, String REQ_URL)
	{
	String strRes="";
	String Battery_Type="";
	String Brand="";
	String State="";
	String City="";
	String Product_Warranty="";
	String Battery_Model_list="";
	String Battery_Warranty_Condition="";
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
		

			if(REQ_URL_Array.length>=5){
				
				Brand =java.net.URLDecoder.decode(REQ_URL_Array[1], "UTF-8");
				LogLevel.DEBUG(5, new Throwable(), "Brand :" + Brand);
				Battery_Type =java.net.URLDecoder.decode(REQ_URL_Array[2], "UTF-8");
				LogLevel.DEBUG(5, new Throwable(), "Battery_Type :" + Battery_Type);
				State =java.net.URLDecoder.decode(REQ_URL_Array[3], "UTF-8");
				LogLevel.DEBUG(5, new Throwable(), "State :" + State);	
				City =java.net.URLDecoder.decode(REQ_URL_Array[4], "UTF-8");
				LogLevel.DEBUG(5, new Throwable(), "City :" + City);
				Battery_Type=Battery_Type.replaceAll("-", " ");	
				LogLevel.DEBUG(5, new Throwable(), "Battery_Type replaced :" + Battery_Type);	
			}
			else
			{
				Brand =java.net.URLDecoder.decode(REQ_URL_Array[1], "UTF-8");
				LogLevel.DEBUG(5, new Throwable(), "Brand :" + Brand);
				Battery_Type =java.net.URLDecoder.decode(REQ_URL_Array[2], "UTF-8");
				LogLevel.DEBUG(5, new Throwable(), "Battery_Type :" + Battery_Type);
				State ="Karnataka";
				LogLevel.DEBUG(5, new Throwable(), "State :" + State);	
				City ="Bangalore";
				LogLevel.DEBUG(5, new Throwable(), "City :" + City);
			}
			Brand= Brand.replaceAll("-", " ");
			Battery_Type= Battery_Type.replaceAll("-", " ");
			State= State.replaceAll("-", " ");
			City= City.replaceAll("-", " ");
			

			String Get_Batteries_Model_List_Details_SQL ="select distinct(b.bat_model) from batteryprice a, application_chat_mapping b where b.bat_type='"+Battery_Type+"' and a.state='"+State+"' and a.city='"+City+"' and a.bat_brand='"+Brand+"' and b.bat_brand='"+Brand+"' and b.bat_brand=a.bat_brand and b.bat_model=a.bat_model;";
			
			LogLevel.DEBUG(5, new Throwable(), "Get_Batteries_Model_List_Details_SQL :" + Get_Batteries_Model_List_Details_SQL);
			
			Vector Vector_Batteries__Model_List_Details=qm.executeQuery(Get_Batteries_Model_List_Details_SQL);
			
			LogLevel.DEBUG(5, new Throwable(), "Vector_Batteries__Model_List_Details :" + Vector_Batteries__Model_List_Details);
			
				if(Vector_Batteries__Model_List_Details.isEmpty())
				{ 
					strRes="404 Page Not Found";
					RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/404.jsp");
					RD.forward(req, res);
					out.close();
					return strRes;
				}
				else
				{
						
						for ( int i=0; i<Vector_Batteries__Model_List_Details.size() ; i++)
						{						
							Hashtable ht=(Hashtable)Vector_Batteries__Model_List_Details.get(i);
							
							
							String Battery_Model_list_TEMP=String.valueOf(ht.get("bat_model"));
							
							
							Battery_Model_list_TEMP="'"+Battery_Model_list_TEMP+"'";
							
							LogLevel.DEBUG(5, new Throwable(), "Battery_Model_list_TEMP :" + Battery_Model_list_TEMP);
							
							
							if(i< Vector_Batteries__Model_List_Details.size()-1)
							{
								Battery_Model_list=Battery_Model_list+Battery_Model_list_TEMP+",";
								LogLevel.DEBUG(5, new Throwable(), "Battery_Model_list in if :" + Battery_Model_list);
								
							}
							else
							{
								Battery_Model_list=Battery_Model_list+Battery_Model_list_TEMP;
								LogLevel.DEBUG(5, new Throwable(), "Battery_Model_list in else :" + Battery_Model_list);
							}
							
						}

						LogLevel.DEBUG(5, new Throwable(), "Battery_Model_list :" + Battery_Model_list);

						String Get_Batteries_List_Details_SQL ="select distinct(b.bat_model),a.bat_witbat_price,a.bat_ret_price,b.bat_brand,b.bat_capacity,b.bat_warranty,b.icon_url,b.description,a.bat_act_price,a.bat_witbat_price,a.bat_ret_price,b.battery_type_flag from batteryprice a, application_chat_mapping b where b.bat_type='"+Battery_Type+"' and a.state='"+State+"' and a.city='"+City+"' and a.bat_brand='"+Brand+"' and b.bat_brand='"+Brand+"' and b.bat_brand=a.bat_brand and b.bat_model=a.bat_model and b.bat_model in("+Battery_Model_list+") and a.bat_model in("+Battery_Model_list+");";

						LogLevel.DEBUG(5, new Throwable(), "Get_Batteries_List_Details_SQL :" + Get_Batteries_List_Details_SQL);

						Vector Vector_Batteries_List_Details=qm.executeQuery(Get_Batteries_List_Details_SQL);
						LogLevel.DEBUG(5,new Throwable(),"Vector_Batteries_List_Details:"+Vector_Batteries_List_Details);

						if(Vector_Batteries_List_Details.isEmpty())
						{ 
							
							LogLevel.DEBUG(5,new Throwable(),"Inside empty condition");
							
							strRes="404 Page Not Found";
							RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/404.jsp");
							RD.forward(req, res);
							out.close();
							return strRes;
						}
						else
						{

							LogLevel.DEBUG(5,new Throwable(),"Inside empty condition");
							
							req.setAttribute("Vector_Get_Battery_Brand_List",Brand);

							LogLevel.DEBUG(5,new Throwable(),"Vector_Get_Battery_Brand_List:"+Brand);
							//### SQL to Get Battery Brand for SEO ####End

							//### SQL to Get Battery Warranty ####Start 				
							String Get_Inverter_Battery_Warranty_SQL = " select DISTINCT a.bat_warranty from application_chat_mapping a, batteryprice b where a.bat_type='"+Battery_Type+"' and b.state='"+State+"' and b.city='"+City+"' ORDER BY a.bat_warranty" ;

							LogLevel.DEBUG(5, new Throwable(), "Get_Inverter_Battery_Warranty_SQL :" + Get_Inverter_Battery_Warranty_SQL);

							Vector Vector_Inverter_Battery_Warranty=qm.executeQuery(Get_Inverter_Battery_Warranty_SQL);
							LogLevel.DEBUG(5,new Throwable(),"Vector_Inverter_Battery_Warranty:"+Vector_Inverter_Battery_Warranty);

							req.setAttribute("Vector_Inverter_Battery_Warranty",Vector_Inverter_Battery_Warranty);
							//### SQL to Get Battery Warranty ####End

							req.setAttribute("Product_Vector",Vector_Batteries_List_Details);
							LogLevel.DEBUG(5, new Throwable(), "Product_Vector :" + Vector_Batteries_List_Details);

							strRes=Battery_Type+"~"+State+"~"+City;
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
	
	public String get_battery_brands(HttpServletRequest req , HttpServletResponse res,HttpSession session, String REQ_URL)
	{
	String strRes="";
	String Product_Type="";
	String State="";
	String City="";
		try
		{	

			String[] REQ_URL_Array  = REQ_URL.split("/");
			
			LogLevel.DEBUG(5, new Throwable(), "REQ_URL_Array length :" + REQ_URL_Array.length);
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
			Product_Type =java.net.URLDecoder.decode(REQ_URL_Array[1], "UTF-8");
			LogLevel.DEBUG(5, new Throwable(), "Product_Type :" + Product_Type);


			if(REQ_URL_Array.length==2)
			{
				State =Product_State_Cookie;
				LogLevel.DEBUG(5, new Throwable(), "State :" + State);	
				City =Product_City_Cookie;
				LogLevel.DEBUG(5, new Throwable(), "City :" + City);
			}
			else
			{
				State =java.net.URLDecoder.decode(REQ_URL_Array[2], "UTF-8");
				LogLevel.DEBUG(5, new Throwable(), "State :" + State);	
				City =java.net.URLDecoder.decode(REQ_URL_Array[3], "UTF-8");
				LogLevel.DEBUG(5, new Throwable(), "City :" + City);
			}

		
			Product_Type= Product_Type.replaceAll("-", " ");
			State= State.replaceAll("-", " ");
			City= City.replaceAll("-", " ");
			

			String Get_Batteries_logo_Utrls_SQL ="select distinct(icon_url),brand from brand_logos;";
			
			LogLevel.DEBUG(5, new Throwable(), "Get_Batteries_logo_Utrls_SQL :" + Get_Batteries_logo_Utrls_SQL);
			
			Vector Vector_Brand_logo_Urls=qm.executeQuery(Get_Batteries_logo_Utrls_SQL);
			
			LogLevel.DEBUG(5, new Throwable(), "Vector_Brand_logo_Urls :" + Vector_Brand_logo_Urls);
			
			if(Vector_Brand_logo_Urls.isEmpty())
			{ 
				strRes="404 Page Not Found";
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/404.jsp");
				RD.forward(req, res);
				out.close();
				return strRes;
			}
			else
			{
				
				//### SQL to Get Battery Warranty ####Start 				
				String Get_Inverter_Battery_Warranty_SQL = " select DISTINCT a.bat_warranty from application_chat_mapping a, batteryprice b where a.bat_type='"+Product_Type+"' and b.state='"+State+"' and b.city='"+City+"' ORDER BY a.bat_warranty" ;

				LogLevel.DEBUG(5, new Throwable(), "Get_Inverter_Battery_Warranty_SQL :" + Get_Inverter_Battery_Warranty_SQL);

				Vector Vector_Inverter_Battery_Warranty=qm.executeQuery(Get_Inverter_Battery_Warranty_SQL);
				LogLevel.DEBUG(5,new Throwable(),"Vector_Inverter_Battery_Warranty:"+Vector_Inverter_Battery_Warranty);

				req.setAttribute("Vector_Inverter_Battery_Warranty",Vector_Inverter_Battery_Warranty);
				//### SQL to Get Battery Warranty ####End
				
				req.setAttribute("Vector_Brand_logo_Urls",Vector_Brand_logo_Urls);
				LogLevel.DEBUG(5, new Throwable(), "Vector_Brand_logo_Urls :" + Vector_Brand_logo_Urls);	
				strRes=Product_Type+"~"+State+"~"+City;
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


