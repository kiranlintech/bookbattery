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
  
public class Inverter_List extends HttpServlet 
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
				String strRes=Get_Inverter_List(req,res,session, Browser_URL_Array[1]);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}	
	
	}
	
	
	
	public String Get_Inverter_List(HttpServletRequest req , HttpServletResponse res,HttpSession session, String REQ_URL)
	{
	String strRes="";
	String Vehicle_Models="";
	String Product_Type="";
	String Product_Brand="";
	String Product_State="";
	String Product_City="";
	String Product_Capacity="";
	String Product_Name="";
	String Product_PriceRange="";
	String Product_PriceType="";
	
	String Inverter_Brand_Condition="";
	String Inverter_Capacity_Condition="";
	String Inverter_Name_Condition="";
	String Inverter_PriceRange_Condition="";
	String Inverter_PriceType_Condition="";
		try
		{	
			String[] REQ_URL_Array  = REQ_URL.split("/");
			
			LogLevel.DEBUG(5, new Throwable(), "REQ_URL_Array.length :" + REQ_URL_Array.length);
			LogLevel.DEBUG(5, new Throwable(), "strRes  :" + REQ_URL);
			
			//################### Getting Location From Cookies 
				GetCookie State_City = new GetCookie(qm);
				String State_City_Resp=  State_City.getCookieStateCity(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "State_City_Resp :" + State_City_Resp);
				
				String[] State_City_Arr=State_City_Resp.split("~");
				Product_State=State_City_Arr[0];
				Product_City=State_City_Arr[1];
			//################### Getting Location From Cookies 
			
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
			else
			{
				if(Product_State.equals("") || Product_State.equals("undefined")|| Product_State.equals(null))
				{
					Product_State ="Karnataka";
				}
			}
			if(REQ_URL_Array.length>=4){
				Product_City =java.net.URLDecoder.decode(REQ_URL_Array[3], "UTF-8");
			}
			else
			{
				if(Product_City.equals("") || Product_City.equals("undefined")|| Product_City.equals(null))
				{
					Product_City ="Bangalore";
				}
			}
			if(REQ_URL_Array.length>=5){
				Product_Capacity =java.net.URLDecoder.decode(REQ_URL_Array[4], "UTF-8");
			}
			else{
				Product_Capacity ="";
			}
			if(REQ_URL_Array.length>=6){
				Product_Name =java.net.URLDecoder.decode(REQ_URL_Array[5], "UTF-8");
			}
			else{
				Product_Name ="";
			}
						
			if(REQ_URL_Array.length>=7){
				Product_PriceType =java.net.URLDecoder.decode(REQ_URL_Array[6], "UTF-8");
			}
			else{
				Product_PriceType ="";
			}
			LogLevel.DEBUG(5, new Throwable(), "Product_PriceType :" + Product_PriceType);
			if(REQ_URL_Array.length>=8){
				Product_PriceRange =java.net.URLDecoder.decode(REQ_URL_Array[7], "UTF-8");
			}
			else{
				Product_PriceRange ="";
			}

			
			Product_Type= Product_Type.replaceAll("-", " ");
			Product_Brand= Product_Brand.replaceAll("-", " ");
			Product_State= Product_State.replaceAll("-", " ");
			Product_City= Product_City.replaceAll("-", " ");
			
			
			//Inverter Brand Condition
			if(Product_Brand.equals("All"))
			{
				//Inverter_Brand_Condition="";
				String Check_Retailer_Location="select distinct city from retailer_location_mapping where retailer_name not like '%Amaron-Pitstop%' and city='"+Product_City+"'";
				
				LogLevel.DEBUG(5,new Throwable(),"Check_Retailer_Location:"+Check_Retailer_Location);
				
				Vector Vector_Check_Retailer_Location=qm.executeQuery(Check_Retailer_Location);
				LogLevel.DEBUG(5,new Throwable(),"Vector_Check_Retailer_Location:"+Vector_Check_Retailer_Location);
			
				if(Vector_Check_Retailer_Location.isEmpty())
				{ 
					Inverter_Brand_Condition=" and a.inverter_brand in ('Amaron')";
					LogLevel.DEBUG(5,new Throwable(),"Inside empty condition");
					LogLevel.DEBUG(5,new Throwable(),"Inverter_Brand_Condition:"+Inverter_Brand_Condition);
				}
				else
				{
					Inverter_Brand_Condition="";
					LogLevel.DEBUG(5,new Throwable(),"Inside else condition");
					LogLevel.DEBUG(5,new Throwable(),"Inverter_Brand_Condition:"+Inverter_Brand_Condition);
				}
				
			}
			else
			{
				if(Product_Brand.indexOf(",") >= 0)
				{
					//String Product_Brand_tmp= Product_Brand.replaceAll(",", "','");
					//Inverter_Brand_Condition= " and a.inverter_brand in ('"+Product_Brand_tmp+"')";

						String Check_Retailer_Location1="select distinct city from retailer_location_mapping where retailer_name not like '%Amaron-Pitstop%' and city='"+Product_City+"'";
						
						LogLevel.DEBUG(5,new Throwable(),"Check_Retailer_Location1:"+Check_Retailer_Location1);
						
						Vector Vector_Check_Retailer_Location1=qm.executeQuery(Check_Retailer_Location1);
						LogLevel.DEBUG(5,new Throwable(),"Vector_Check_Retailer_Location1:"+Vector_Check_Retailer_Location1);
					
						if(Vector_Check_Retailer_Location1.isEmpty())
						{ 
							String Product_Brand_tmp= Product_Brand.replaceAll(",", "','");
							
							if(Product_Brand_tmp.indexOf("Amaron") >= 0)
							{
								Inverter_Brand_Condition=" and a.inverter_brand in ('Amaron')";
								LogLevel.DEBUG(5,new Throwable(),"Inside empty condition");
								LogLevel.DEBUG(5,new Throwable(),"Inverter_Brand_Condition:"+Inverter_Brand_Condition);
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
							Inverter_Brand_Condition= " and a.inverter_brand in ('"+Product_Brand_tmp+"')";
							LogLevel.DEBUG(5,new Throwable(),"Inside else condition");
							LogLevel.DEBUG(5,new Throwable(),"Inverter_Brand_Condition:"+Inverter_Brand_Condition);
						}
				}
				else
				{
					//Inverter_Brand_Condition= " and a.inverter_brand in ('"+Product_Brand+"')";
					
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
								Inverter_Brand_Condition=" and a.inverter_brand in ('Amaron')";
								LogLevel.DEBUG(5,new Throwable(),"Inside empty condition");
								LogLevel.DEBUG(5,new Throwable(),"Inverter_Brand_Condition:"+Inverter_Brand_Condition);
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
							Inverter_Brand_Condition= " and a.inverter_brand in ('"+Product_Brand+"')";
							LogLevel.DEBUG(5,new Throwable(),"Inside else condition");
							LogLevel.DEBUG(5,new Throwable(),"Inverter_Brand_Condition:"+Inverter_Brand_Condition);
						}

				}
			}
			
			//Inverter Capacity Condition
			if(Product_Capacity.equals(""))
			{
				Inverter_Capacity_Condition="";
			}
			else
			{
				if(Product_Capacity.indexOf("=") >= 0)
				{
					String[] Product_Capacity_Array  = Product_Capacity.split("=");
					String Product_Capacity_tmp =Product_Capacity_Array[1];
					Product_Capacity_tmp= Product_Capacity_tmp.replaceAll(",", "','");
					Inverter_Capacity_Condition= " and b.inverter_capacity in ('"+Product_Capacity_tmp+"')";
					
					req.setAttribute("Inverter_Filter_Capacity",Product_Capacity_Array[1]);
					LogLevel.DEBUG(5, new Throwable(), "Inverter_Filter_Capacity :" + Product_Capacity_Array[1]);
				}
				else
				{
					Inverter_Capacity_Condition="";
				}
			}
			
			//Inverter Capacity Condition
			if(Product_Name.equals(""))
			{
				Inverter_Name_Condition="";
			}
			else
			{
				if(Product_Name.indexOf("=") >= 0)
				{
					String[] Product_Name_Array  = Product_Name.split("=");
					String Product_Name_tmp =Product_Name_Array[1];
					Product_Name_tmp= Product_Name_tmp.replaceAll(",", "','");
					Inverter_Name_Condition= " and a.inverter_name in ('"+Product_Name_tmp+"')";
					
					req.setAttribute("Inverter_Filter_Name",Product_Name_Array[1]);
					LogLevel.DEBUG(5, new Throwable(), "Inverter_Filter_Name :" + Product_Name_Array[1]);
				}
				else
				{
					Inverter_Name_Condition="";
				}
			}
			
		 //Inverter Price Condition to fetch based on warranty
			if(Product_PriceRange.equals(""))
			{
				Inverter_PriceRange_Condition="";
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
					
					Inverter_PriceRange_Condition= " and b.inverter_discount_price between "+Product_PriceRange_tmp_0+" and "+Product_PriceRange_tmp_1+"";
					req.setAttribute("Product_PriceRange_tmp_0",Product_PriceRange_Array[0]);
					req.setAttribute("Product_PriceRange_tmp_1",Product_PriceRange_Array[1]);
				}
				else
				{
					Inverter_PriceRange_Condition="";
				}
			}
			
			//Product Price Sort Condition
			if(Product_PriceType.indexOf("DESC") >= 0)
			{
				Inverter_PriceType_Condition= "ORDER BY CAST(b.inverter_discount_price as unsigned) DESC";
				req.setAttribute("Product_PriceType","DESC");
				LogLevel.DEBUG(5, new Throwable(), "Inverter_PriceType_Condition :" + Inverter_PriceType_Condition);
			}
			else
			{
				Inverter_PriceType_Condition= "ORDER BY CAST(b.inverter_discount_price as unsigned) ASC";
				req.setAttribute("Product_PriceType","ASC");
				LogLevel.DEBUG(5, new Throwable(), "Inverter_PriceType_Condition :" + Inverter_PriceType_Condition);
			}
			
			String Get_Inverter_SQL = "select DISTINCT a.inverter_brand,a.inverter_model,a.inverter_capacity,a.inverter_warranty,a.icon_url,a.computer,a.tubelights,a.fans,a.television,b.inverter_actual_price,b.inverter_discount_price from inverter_details a, inverter_price_details b  where  b.state='"+Product_State+"' and b.city='"+Product_City+"' "+Inverter_Brand_Condition+" "+Inverter_Capacity_Condition+" "+Inverter_Name_Condition+" "+Inverter_PriceRange_Condition+" and a.inverter_brand=b.inverter_brand and a.inverter_model=b.inverter_model "+Inverter_PriceType_Condition+"";
			LogLevel.DEBUG(5, new Throwable(), "Get_Inverter_SQL :" + Get_Inverter_SQL);
			
			Vector Vector_Inverter=qm.executeQuery(Get_Inverter_SQL);
			LogLevel.DEBUG(5,new Throwable(),"Vector_Inverter:"+Vector_Inverter);
			
			
			if(Vector_Inverter.isEmpty())
			{ 
				strRes="404 Page Not Found";
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/404.jsp");
				RD.forward(req, res);
				out.close();
				return strRes;
			}
			else
			{
				//### SQL to Get Inverter Brands ####Start 
				String Get_Inverter_Brands_SQL = "select DISTINCT a.inverter_brand from inverter_details a, inverter_price_details b  where  b.state='"+Product_State+"' and b.city='"+Product_City+"' and a.inverter_brand=b.inverter_brand ";
				LogLevel.DEBUG(5, new Throwable(), "Get_Inverter_Brands_SQL :" + Get_Inverter_Brands_SQL);
				
				Vector Vector_Inverter_Brands=qm.executeQuery(Get_Inverter_Brands_SQL);
				LogLevel.DEBUG(5,new Throwable(),"Vector_Inverter_Brands:"+Vector_Inverter_Brands);
				
				req.setAttribute("Vector_Inverter_Brands",Vector_Inverter_Brands);
				//### SQL to Get Inverter Brands ####End
				
				
				//### SQL to Get Inverter Capacity ####Start
				String Get_Inverter_Capacity_SQL = " select DISTINCT a.inverter_capacity from inverter_details a, inverter_price_details b  where a.inverter_brand=b.inverter_brand and b.state='"+Product_State+"' and b.city='"+Product_City+"' "+Inverter_Brand_Condition+" ORDER BY a.inverter_capacity" ;
				LogLevel.DEBUG(5, new Throwable(), "Get_Inverter_Capacity_SQL :" + Get_Inverter_Capacity_SQL);
				
				Vector Vector_Inverter_Capacity=qm.executeQuery(Get_Inverter_Capacity_SQL);
				LogLevel.DEBUG(5,new Throwable(),"Vector_Inverter_Capacity:"+Vector_Inverter_Capacity);
				
				req.setAttribute("Vector_Inverter_Capacity",Vector_Inverter_Capacity);
				//### SQL to Get Inverter Capacity ####End
				
				//### SQL to Get Inverter Name ####Start
				String Get_Inverter_Name_SQL = " select DISTINCT a.inverter_name from inverter_details a, inverter_price_details b  where a.inverter_brand=b.inverter_brand and b.state='"+Product_State+"' and b.city='"+Product_City+"' "+Inverter_Brand_Condition+" ORDER BY a.inverter_name" ;
				LogLevel.DEBUG(5, new Throwable(), "Get_Inverter_Name_SQL :" + Get_Inverter_Name_SQL);
				
				Vector Vector_Inverter_Name=qm.executeQuery(Get_Inverter_Name_SQL);
				LogLevel.DEBUG(5,new Throwable(),"Vector_Inverter_Name:"+Vector_Inverter_Name);
				
				req.setAttribute("Vector_Inverter_Name",Vector_Inverter_Name);
				//### SQL to Get Inverter Name ####End
				
				
				//### SQL to Get Inverter Lease and Max Price ####Start 
				String Get_Inverter_PriceRange_SQL = "SELECT MIN(CONVERT(inverter_discount_price, SIGNED INTEGER)) AS min_price,MAX(CONVERT(inverter_discount_price, SIGNED INTEGER)) AS max_price FROM  batteryprice where state='"+Product_State+"' and city='"+Product_City+"' "+Inverter_Brand_Condition+""; 
				LogLevel.DEBUG(5, new Throwable(), "Get_Inverter_PriceRange_SQL :" + Get_Inverter_PriceRange_SQL);
				
				Vector Vector_Inverter_PriceRange=qm.executeQuery(Get_Inverter_PriceRange_SQL);
				LogLevel.DEBUG(5,new Throwable(),"Vector_Inverter_PriceRange:"+Vector_Inverter_PriceRange);
				
				req.setAttribute("Vector_Inverter_PriceRange",Vector_Inverter_PriceRange);
				//### SQL to Get Inverter Lease and Max Price ####End
				
				
				req.setAttribute("Vector_Inverter",Vector_Inverter);
				LogLevel.DEBUG(5, new Throwable(), "Vector_Inverter :" + Vector_Inverter);
					
				req.setAttribute("Product_Type",Product_Type);
				LogLevel.DEBUG(5, new Throwable(), "Product_Type:" +Product_Type);
				
				req.setAttribute("Product_Brand",Product_Brand);
				LogLevel.DEBUG(5, new Throwable(), "Product_Brand:" +Product_Brand);
				
				req.setAttribute("Product_State",Product_State);
				LogLevel.DEBUG(5, new Throwable(), "Product_State:" +Product_State);
				
				req.setAttribute("Product_City",Product_City);
				LogLevel.DEBUG(5, new Throwable(), "Product_City:" +Product_City);
				
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/inverter_list.jsp");
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


