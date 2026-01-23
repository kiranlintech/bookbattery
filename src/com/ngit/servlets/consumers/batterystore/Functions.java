/***********************************************************************		
	NGIT Confidential. 
	@File Name   : Functions.java 
	@Description : This Servlet is used for some functions and valadations.
	@Author	     : Sai Krishna Daddala
	@Date        : 30th Sep 2016
******************************************************************/ 
 
package com.ngit.servlets.consumers.batterystore; 
 
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.mail.*;
import com.ngit.javabean.sendsms.SendSMS;
import com.ngit.javabean.consumers.products.CompareTrans;
import com.ngit.javabean.consumers.products.GenerateExcelinvoice;
import com.ngit.javabean.consumers.products.GetCookie;


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
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.HttpURLConnection;
  
public class Functions extends HttpServlet 
{  
	private ServletContext context; 
	QueryManager qm;
	static final long serialVersionUID=21;
	public static final int PASSWORD_LENGTH = 8;
	private static final Random RANDOM = new SecureRandom();
	
	String baseUrl="";
	String OperatorTeamCount="";
	
	/** Length of password. @see #generateRandomPassword() */
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
		String struserName=(String)session.getAttribute("sesBatteryUserLogin"); 
		LogLevel.DEBUG(5,new Throwable(),"struserName :"+struserName );
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
		ServletOutputStream out=res.getOutputStream();
		
		String strIpAddress =(propsMOPConfig.getProperty("TRANSMITTER_IP_ADDR")!=null)?(propsMOPConfig.getProperty("TRANSMITTER_IP_ADDR")):"";
		String strPort=(propsMOPConfig.getProperty("TRANSMITTER_IP_PORT")!=null)?(propsMOPConfig.getProperty("TRANSMITTER_IP_PORT")):"9910";
		String SMSFromAddress=(propsMOPConfig.getProperty("SMSFromAddress")!=null)?(propsMOPConfig.getProperty("SMSFromAddress")):"";
		String domainname = (propsMOPConfig.getProperty("domainname")!=null)?(propsMOPConfig.getProperty("domainname")).trim():"bookbattery.com";
		baseUrl =  propsMOPConfig.getProperty("publicUrl");

		String FromEmailId=(propsMOPConfig.getProperty("SupportEmailId")!=null)?(propsMOPConfig.getProperty("SupportEmailId")):"";
		String strsuppemaild=(propsMOPConfig.getProperty("suppemaild")!=null)?(propsMOPConfig.getProperty("suppemaild")):"";
		String strsuppmobnumber =  propsMOPConfig.getProperty("suppmobnumber");
		String strsuppmobnumber1 =  propsMOPConfig.getProperty("suppmobnumber1");
		String strCMSSERVERIP	= (propsMOPConfig.getProperty("CMS_SERVER_IP")!=null)?(propsMOPConfig.getProperty("CMS_SERVER_IP")):"";
		String strPDFFilePath	= (propsMOPConfig.getProperty("PDFFilePath")!=null)?(propsMOPConfig.getProperty("PDFFilePath")):"";
		String strPDFRelFilePath = (propsMOPConfig.getProperty("PDFRelFilePath")!=null)?(propsMOPConfig.getProperty("PDFRelFilePath")):"";
		OperatorTeamCount = (propsMOPConfig.getProperty("OperatorTeamCount")!=null)?(propsMOPConfig.getProperty("OperatorTeamCount")):"";
		LogLevel.DEBUG(5, new Throwable(), "OperatorTeamCount :" + OperatorTeamCount);

		/* ****************************************************************************************\
		* This Functionis to get the Random Battery List In Home Page
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("Get_BatteryList_Random"))
			{ 
			try
			{
				String strRes=Get_BatteryList_Random(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);
				out.close();
			}
			catch (Exception e)
			{										
			LogLevel.ERROR(1, e, "Error :" + e);
			}	
	      }
		  
		  /* ****************************************************************************************\		/* ****************************************************************************************\
		* This Functionis to get the Random Battery List In Home Page
		\* *****************************************************************************************/		
			if(strWhatToDo.equalsIgnoreCase("Get_Customer_ratings_Testimonial1"))
			{ 
				try
				{
					String strRes=Get_Customer_ratings_Testimonial1(req,res,session);
					LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
					out.println(strRes);
					out.close();
				}
				catch (Exception e)
				{										
				LogLevel.ERROR(1, e, "Error :" + e);
				}	
		   }			 

 /* ****************************************************************************************\		/* ****************************************************************************************\
		* This Functionis to get the Random Battery List In Home Page
		\* *****************************************************************************************/		
			if(strWhatToDo.equalsIgnoreCase("Get_Customer_ratings_Testimonial"))
			{ 
				try
				{
					String strRes=Get_Customer_ratings_Testimonial(req,res,session);
					LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
					out.println(strRes);
					out.close();
				}
				catch (Exception e)
				{										
				LogLevel.ERROR(1, e, "Error :" + e);
				}	
		   }		

 /* ****************************************************************************************\		/* ****************************************************************************************\
		* This Functionis to get the Random Battery List In Home Page
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("Get_Customer_ratings"))
			{ 
			try
			{
				String strRes=Get_Customer_ratings(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);
				out.close();
			}
			catch (Exception e)
			{										
			LogLevel.ERROR(1, e, "Error :" + e);
			}	
	      }		 

		/* ****************************************************************************************\
		* This action is used to Insert Visitor Details in DB
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("Insert_Visitor_Details"))
			{ 
			try
			{
				String strRes=Insert_Visitor_Details(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);
				out.close();
			}
			catch (Exception e)
			{										
			LogLevel.ERROR(1, e, "Error :" + e);
			}	
	      }
		  
		/* ****************************************************************************************\
		* This action is to check a Battery model supports for Some Make and Model
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("CheckBatteryModelwithVehicle"))
			{ 
			try
			{
				String strRes=CheckBatteryModelwithVehicle(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);
				out.close();
			}
			catch (Exception e)
			{										
			LogLevel.ERROR(1, e, "Error :" + e);
			}	
	      }
		  
		/* ****************************************************************************************\
		* This action is used to get send mobile verification code details.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("GetVerificationSms"))
		{ 
			try
			{
				String strRes=GetVerificationSms(req,res,session,domainname,strIpAddress,strPort,SMSFromAddress);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);
				out.close();
			
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}  
		/* ****************************************************************************************\
		* This action is used to Insert order details in to Orders DB before Ordering 
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("InsertBeforeOrder"))
		{ 
			try
			{
				String strRes=InsertBeforeOrder(req,res,session,domainname,strIpAddress,strPort,SMSFromAddress,FromEmailId,strsuppemaild,baseUrl,strsuppmobnumber,strCMSSERVERIP,strPDFFilePath,strPDFRelFilePath,baseurldirectory,OperatorTeamCount);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);
				out.close();
			
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}
		
		/* ****************************************************************************************\
		* This action is used to Insert order details in to Orders DB before Ordering 
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("InsertBeforeServiceOrder"))
		{ 
			try
			{
				String strRes=InsertBeforeServiceOrder(req,res,session,domainname,strIpAddress,strPort,SMSFromAddress,FromEmailId,strsuppemaild,baseUrl,strsuppmobnumber,strCMSSERVERIP,strPDFFilePath,strPDFRelFilePath,baseurldirectory,OperatorTeamCount);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);
				out.close();
			
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}		
		
		/* ****************************************************************************************\
		* This action is used to Insert Battery Order data in to DB
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("InsertOrderDetails_Battery"))
		{ 
			try
			{
				String strRes=InsertOrderDetails_Battery(req,res,session,domainname,strIpAddress,strPort,SMSFromAddress,FromEmailId,strsuppemaild,baseUrl,strsuppmobnumber,strCMSSERVERIP,strPDFFilePath,strPDFRelFilePath,baseurldirectory,OperatorTeamCount);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);
				out.close();
			
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}
		
		/* ****************************************************************************************\
		* This action is used to Insert Inverter Order data in to DB
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("InsertOrderDetails_Inverter"))
		{ 
			try
			{
				String strRes=InsertOrderDetails_Inverter(req,res,session,domainname,strIpAddress,strPort,SMSFromAddress,FromEmailId,strsuppemaild,baseUrl,strsuppmobnumber,strCMSSERVERIP,strPDFFilePath,strPDFRelFilePath,OperatorTeamCount);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);
				out.close();
			
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}
		
		/* ****************************************************************************************\
		* This action is used to Insert Inverter and Battery data in to DB
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("InsertOrderDetails_Combo"))
		{ 
			try
			{
				String strRes=InsertOrderDetails_Combo(req,res,session,domainname,strIpAddress,strPort,SMSFromAddress,FromEmailId,strsuppemaild,baseUrl,strsuppmobnumber,strCMSSERVERIP,strPDFFilePath,strPDFRelFilePath,OperatorTeamCount);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);
				out.close();
			
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}
		
		/* ****************************************************************************************\
		* This action is used to Find Retailers for Area 
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("Find_Retailer"))
		{ 
			try
			{
				String strRes=Find_Retailer(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);
				out.close();
			
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}
		/* ****************************************************************************************\
		* This action is used to get battery barnd.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("Fx_Get_Product_Brand"))
		{ 
			try
			{
				String strRes=Fx_Get_Product_Brand(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);
				out.close();
			}
			catch (Exception e)
			{										
			LogLevel.ERROR(1, e, "Error :" + e);
			}	
	    }
		/* ****************************************************************************************\
		* This action is used to get battery barnd.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("Validate_Ref_Code"))
		{ 
			try
			{
				String strRes=Validate_Ref_Code(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);
				out.close();
			}
			catch (Exception e)
			{										
			LogLevel.ERROR(1, e, "Error :" + e);
			}	
	    }
	}
	
	/* **************************************************************************************************************************************\
	* This method is to Check, Vehicle Make and Model will support of for a Battery or not
	* @strSqlQry : carries the query find this model support or not.
	\* **************************************************************************************************************************************/
	public String CheckBatteryModelwithVehicle(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String BatteryModel= (req.getParameter("BatteryModel")!=null)?(req.getParameter("BatteryModel")):"";
			LogLevel.DEBUG(5, new Throwable(), "BatteryModel :" + BatteryModel);

			String VehicleMake= (req.getParameter("VehicleMake")!=null)?(req.getParameter("VehicleMake")):"";
			LogLevel.DEBUG(5, new Throwable(), "VehicleMake :" + VehicleMake);
			
			String VehicleModel= (req.getParameter("VehicleModel")!=null)?(req.getParameter("VehicleModel")):"";
			LogLevel.DEBUG(5, new Throwable(), "VehicleModel :" + VehicleModel);
			
			if(BatteryModel.isEmpty() || BatteryModel.equals("")|| BatteryModel.equals("null")|| BatteryModel.equals(null) || VehicleMake.isEmpty() || VehicleMake.equals("")|| VehicleMake.equals("null")|| VehicleMake.equals(null) || VehicleModel.isEmpty() || VehicleModel.equals("")|| VehicleModel.equals("null")|| VehicleModel.equals(null) )
			{ 
				strRes="Some Thing went Wrong";
			}
			else
			{
				String Check_Details_SQL ="SELECT * from application_chat_mapping  where veh_name='"+VehicleMake+"' and veh_model='"+VehicleModel+"' and bat_model='"+BatteryModel+"' LIMIT 1";
				LogLevel.DEBUG(5, new Throwable(), "Check_Details_SQL :" + Check_Details_SQL);
				
				Hashtable Check_Details_Hashtable=qm.getRow(Check_Details_SQL);
				LogLevel.DEBUG(5,new Throwable(),"Check_Details_Hashtable:"+Check_Details_Hashtable);
				
				if(Check_Details_Hashtable.isEmpty())
				{ 
					strRes="Opps, It Dosent Match";
				}
				else
				{
					strRes="Yep, It works.";
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
	
	
		/* **************************************************************************************************************************************\
	* This Functionis to get the Random Battery List In Home Page #################################
	\* **************************************************************************************************************************************/
	public String Get_BatteryList_Random(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			//################### Getting Location From Cookies 
			GetCookie State_City = new GetCookie(qm);
			String State_City_Resp=  State_City.getCookieStateCity(req,res,session);
			LogLevel.DEBUG(5, new Throwable(), "State_City_Resp :" + State_City_Resp);
			
			String[] State_City_Arr=State_City_Resp.split("~");
			String Product_State_Cookie=State_City_Arr[0];
			String Product_City_Cookie=State_City_Arr[1];
			//################### Getting Location From Cookies 
			
			
			
			String Get_BatteryList_Random_SQL="";
			Vector Vector_Get_BatteryList_Random;
			
			//################### SQL Query to get Battery list random from Cooke location  
			Get_BatteryList_Random_SQL ="SELECT DISTINCT a.bat_model, a.bat_brand,a.bat_capacity,a.bat_warranty,a.bat_type,a.icon_url,b.bat_act_price,b.bat_witbat_price,b.bat_ret_price,a.battery_type_flag,b.state,b.city from application_chat_mapping a, batteryprice b where b.state='"+Product_State_Cookie+"' and b.city='"+Product_City_Cookie+"' and a.bat_model=b.bat_model ORDER BY RAND() LIMIT 5";
			LogLevel.DEBUG(5, new Throwable(), "Get_BatteryList_Random_SQL - Cooke :" + Get_BatteryList_Random_SQL);
			
			Vector_Get_BatteryList_Random =qm.executeQuery(Get_BatteryList_Random_SQL);
			
			//If SQL Query to get Battery list random from Cooke location  fails Forcing it to fetch battery list from default location Karnataka, Bangalore
			if(Vector_Get_BatteryList_Random.isEmpty())
			{ 
				//################### SQL Query to get Battery list random from Karnataka, Bangalore
				Get_BatteryList_Random_SQL ="SELECT DISTINCT a.bat_model, a.bat_brand,a.bat_capacity,a.bat_warranty,a.bat_type,a.icon_url,b.bat_act_price,b.bat_witbat_price,b.bat_ret_price,a.battery_type_flag,b.state,b.city from application_chat_mapping a, batteryprice b where b.state='Karnataka' and b.city='Bangalore' and a.bat_model=b.bat_model ORDER BY RAND() LIMIT 5";
				LogLevel.DEBUG(5, new Throwable(), "Get_BatteryList_Random_SQL - Default :" + Get_BatteryList_Random_SQL);
				
				Vector_Get_BatteryList_Random =qm.executeQuery(Get_BatteryList_Random_SQL);
				
				if(Vector_Get_BatteryList_Random.isEmpty())
				{ 
					//################### Nightmare SQL Failure  
					strRes="Opps!, Some thing went Wrong";
					return strRes;
				}
				else
				{
						
				}
			}
			else
			{
				
			}
			
			for (int i=0; i<Vector_Get_BatteryList_Random.size(); i++)
			{
				Hashtable ht=(Hashtable)Vector_Get_BatteryList_Random.get(i);
				
				String bat_model =String.valueOf(ht.get("bat_model"));
				String bat_brand =String.valueOf(ht.get("bat_brand"));
				String bat_capacity =String.valueOf(ht.get("bat_capacity"));
				String bat_warranty =String.valueOf(ht.get("bat_warranty"));
				String icon_url =String.valueOf(ht.get("icon_url"));
				
				String city =String.valueOf(ht.get("city"));
				String state =String.valueOf(ht.get("state"));
	
				String WithOutOldBattery_String=String.valueOf(ht.get("bat_witbat_price"));
				String WithOldBattery_Less_String=String.valueOf(ht.get("bat_ret_price"));
				String bat_act_price=String.valueOf(ht.get("bat_act_price"));
				
				int WithOutOldBattery_Price = Integer.parseInt(WithOutOldBattery_String);
				int WithOldBattery_Less_Price = Integer.parseInt(WithOldBattery_Less_String);
				int WithOldBattery_Price = WithOutOldBattery_Price - WithOldBattery_Less_Price;
				
				LogLevel.DEBUG(5,new Throwable(),"WithOldBattery :"+WithOldBattery_Price );
				LogLevel.DEBUG(5,new Throwable(),"WithOutOldBattery :"+WithOutOldBattery_Price );

				String bat_brand_URL=bat_brand;
				String bat_model_URL=bat_model;
				
				String State_URL=state;
				String City_URL=city;
				
				bat_brand_URL= bat_brand_URL.replaceAll(" ", "-");
				bat_model_URL= bat_model_URL.replaceAll(" ", "+");
				
				State_URL = State_URL.replaceAll(" ", "-");
				City_URL = City_URL.replaceAll(" ", "-");
				
				strRes=strRes+"  <div class='item'> <div class='item-inner'><div class='item-img'> <div class='product-block'><div class='product-image'> <a href='"+baseUrl+"/bookbattery/products/"+bat_brand_URL+"/"+bat_model_URL+"/"+State_URL+"/"+City_URL+"/' style='height: auto;'> <figure class='product-display'><img src='"+icon_url+"' class='lazyOwl product-mainpic' alt=' "+bat_brand+" "+bat_model+" ("+bat_capacity+")' style='display: block;'> <img class='product-secondpic' alt='"+bat_brand+" "+bat_model+" ("+bat_capacity+")' src='"+icon_url+"'> </figure> </a> </div><div class='product-meta'> <div class='product-action'> <a class='addcart'  href='"+baseUrl+"/bookbattery/products/"+bat_brand_URL+"/"+bat_model_URL+"/"+State_URL+"/"+City_URL+"/' > <i class='icon-external-link-sign'>&nbsp;</i> View More </a></div> </div></div><div class='item-info' style='text-align: left;'> <div class='info-inner'><div class='item-title'> <a  href='"+baseUrl+"/bookbattery/products/"+bat_brand_URL+"/"+bat_model_URL+"/"+State_URL+"/"+City_URL+"/' title='Click to View '> "+bat_brand+" "+bat_model+" ("+bat_capacity+") </a> </div><div class='item-content'> <div class='item-price'><div class='price-box'> <div class='old-price'> <span class='price-label' style='display: block;'>Regular Price: <span class='price' ><i class='icon-inr' aria-hidden='true'></i> "+bat_act_price+" </span> </span></div><br>  <span class='price-label' style='display: block;'>Warranty: <span > "+bat_warranty+" </span> </span><br><table style=' color: #494848;font-size: 15px;'> <tr> <td>With Out Old Battery</td> <td>: <i class='icon-inr' aria-hidden='true'></i> "+WithOutOldBattery_Price+" </td> </tr> <tr> <td>With Old Battery</td> <td>: <i class='icon-inr' aria-hidden='true'></i> "+WithOldBattery_Price+" </td> </tr> </table> <br></div> </div></div> </div></div> </div></div> </div>";
			}
			
		}
		catch (Exception e)
		{
			LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
			e.printStackTrace();
		}
		return strRes;
	}	/* **************************************************************************************************************************************\
	* This Functionis to get the Random Battery List In Home Page #################################
	\* **************************************************************************************************************************************/
	public String Get_Customer_ratings(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String Get_Customer_ratings_SQL="";
			Vector Vector_Get_Customer_ratings;
			String pngimage="";
			int length;
			int im=0;
			

			//################### SQL Query to get Battery list random from Cooke location  
			Get_Customer_ratings_SQL ="SELECT no_of_rating,order_id,city,rating_comments,name,email from customer_rating_details where upd_comm_flag='Yes' and satisfy_flag='Yes' order by updated_date desc,no_of_rating desc;";
			LogLevel.DEBUG(5, new Throwable(), "Get_Customer_ratings_SQL :" + Get_Customer_ratings_SQL);
			
			Vector_Get_Customer_ratings =qm.executeQuery(Get_Customer_ratings_SQL);
			
			if(Vector_Get_Customer_ratings.isEmpty())
			{
				strRes=strRes+"<div class='no_backgrd item active'><img src='/bookbattery/images/banners/banner_2.jpg'  alt='Batterywale_New_banner_image'></div><div class='no_backgrd item'><img src='/bookbattery/images/banners/Car-Battery-Health-Checkup.png'  alt='Car Battery Health Checkup'></div><div class='no_backgrd item'><img src='/bookbattery/images/banners/Why-shop-with-bookbattery.jpg' alt='Why to shop with bookbattery'></div><div class='no_backgrd item'><img src='/bookbattery/images/banners/Exide_Batteries.png'  alt='BookBattery Exide bookbattery'></div><div class='no_backgrd item'><img src='/bookbattery/images/banners/Batterywale_Amaron.png'  alt='BookBattery Amaron bookbattery'></div><div class='no_backgrd item'><img src='/bookbattery/images/banners/Batteries_Reasonable_Price.png'  alt='BookBattery Batteries Reasonable'></div>";
			}
			else
			{
					 length = Vector_Get_Customer_ratings.size();
					
					
					/*if((length%3)==0)
					{
						length=Vector_Get_Customer_ratings.size();
					}
					else
					{
						if((length%3)==1)
						{
							length=length-1;
						}
						else if((length%3)==2)
						{
							length=length-2;
						}
						else
						{
							length=length;
						}
					}
					*/
				if (length>=6)
				{
					for (int k=0; k<length; k++)
					{
					Hashtable htr=(Hashtable)Vector_Get_Customer_ratings.get(k);
						im=k;
						
						String rating_comments =String.valueOf(htr.get("rating_comments"));
						String order_id =String.valueOf(htr.get("order_id"));
						LogLevel.DEBUG(5, new Throwable(), "order_id :" + order_id);
						String name =String.valueOf(htr.get("name"));
						String no_of_rating =String.valueOf(htr.get("no_of_rating"));
						String city =String.valueOf(htr.get("city"));

						
						
						String Get_Customer_ratings_state_SQL ="SELECT state from search_whereever_keywords where city='"+city+"';";
						
						Hashtable State_Name_HT = qm.getRow(Get_Customer_ratings_state_SQL);
						
						String State=(String)State_Name_HT.get("state");
						
						LogLevel.DEBUG(5, new Throwable(), "State :" + State);
						
						int no_of_rating_int = Integer.parseInt(no_of_rating);
						
						if(k==0)
						{
							strRes=strRes+"<div class='item active'>";
						}
						else
						{
							strRes=strRes+"<div class='item'>";
						}

						strRes=strRes+"<div class='col-md-12'><div class='col-md-4'><div style='margin: 93px 24px 18px 65px;'><span class='rating-input'>";
						
							for(int r=0;r<5;r++)
							{
								if(no_of_rating_int>r)
								{
									strRes=strRes+"<span data-value='"+r+"' class='glyphicon glyphicon-star'></span>";
								}
								else
								{
									strRes=strRes+"<span data-value='"+r+"' class='glyphicon glyphicon-star-empty'></span>";
								}
								
								
							}
								
								
						strRes=strRes+"</span></div><h3 id='t"+k+"'>"+name+"</h3><h5><i class='fa fa-map-marker' aria-hidden='true'></i>"+State+", "+city+"</h5></div><div class='col-md-8 paratext'><h3>Our Happy Customer</h3><br><p id='p"+k+"'>"+rating_comments+"</p></div></div></div>";


						if(im%6==0)
						{
							  pngimage ="Car-Battery-Health-Checkup.png";
						}
						else if(im%6==5)
						{
							  pngimage ="banner_2.jpg";
						}
						else if(im%6==4)
						{
							  pngimage ="Why-shop-with-bookbattery.jpg";
						}else if(im%6==3)
						{
							  pngimage ="Exide_Batteries.png";
						}
						else if(im%6==2)
						{
							  pngimage ="Batterywale_Amaron.png";
						}
						else if(im%6==1)
						{
							 pngimage ="Batteries_Reasonable_Price.png";
						}
						else
						{
							
						}
						
						strRes=strRes+"<div class='no_backgrd item'><img src='/bookbattery/images/banners/"+pngimage+"'  alt='BookBattery Batteries inverter and battery combos offer '></div>";
						
					}
				}
				
				else
				{

					for (int m=0; m<6; m++)
					{
						
						
						im=m;
						
						if(m<length)
						{
							Hashtable htrk=(Hashtable)Vector_Get_Customer_ratings.get(m);
							
							
							String rating_comments1 =String.valueOf(htrk.get("rating_comments"));
							String order_id1 =String.valueOf(htrk.get("order_id"));
							LogLevel.DEBUG(5, new Throwable(), "order_id :" + order_id1);
							String name1 =String.valueOf(htrk.get("name"));
							String no_of_rating1 =String.valueOf(htrk.get("no_of_rating"));
							String city1 =String.valueOf(htrk.get("city"));

							
							
							String Get_Customer_ratings_state_SQL1 ="SELECT state from search_whereever_keywords where city='"+city1+"';";
							
							Hashtable State_Name_HT1 = qm.getRow(Get_Customer_ratings_state_SQL1);
							
							String State1=(String)State_Name_HT1.get("state");
							
							LogLevel.DEBUG(5, new Throwable(), "State :" + State1);
							
							int no_of_rating_int1 = Integer.parseInt(no_of_rating1);
							
						
							if(m==0)
							{
							strRes=strRes+"<div class='item active'>";
							}
							else
							{
							strRes=strRes+"<div class='item'>";
							}

							strRes=strRes+"<div class='col-md-12'><div class='col-md-4'><div style='margin: 93px 24px 18px 65px;'><span class='rating-input'>";

							for(int r=0;r<5;r++)
							{
								if(no_of_rating_int1>r)
								{
								strRes=strRes+"<span data-value='"+r+"' class='glyphicon glyphicon-star'></span>";
								}
								else
								{
								strRes=strRes+"<span data-value='"+r+"' class='glyphicon glyphicon-star-empty'></span>";
							    }

						    }
							
							strRes=strRes+"</span></div><h3 id='t"+m+"'>"+name1+"</h3><h5><i class='fa fa-map-marker' aria-hidden='true'></i>"+State1+", "+city1+"</h5></div><div class='col-md-8 paratext'><h3>Our Happy Customer</h3><br><p id='p"+m+"'>"+rating_comments1+"</p></div></div></div>";

						}
						else
						{
							
							
						}
						
						if(im%6==0)
						{
							  pngimage ="Car-Battery-Health-Checkup.png";
						}
						else if(im%6==5)
						{
							  pngimage ="banner_2.jpg";
						}
						else if(im%6==4)
						{
							  pngimage ="Why-shop-with-bookbattery.jpg";
						}else if(im%6==3)
						{
							  pngimage ="Exide_Batteries.png";
						}
						else if(im%6==2)
						{
							  pngimage ="Batterywale_Amaron.png";
						}
						else if(im%6==1)
						{
							 pngimage ="Batteries_Reasonable_Price.png";
						}
						
						else
						{
							
						}
						strRes=strRes+"<div class='no_backgrd item'><img src='/bookbattery/images/banners/"+pngimage+"'  alt='BookBattery Batteries Customer rating '></div>";
					}
					
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
			
/*	
/**************************************************************************************************************************************\
	* This Function is th get the customer ratings for testimonials page#################################
	\* **************************************************************************************************************************************/
	public String Get_Customer_ratings_Testimonial(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String Get_Customer_ratings_tes_SQL="";
			Vector Vector_Get_Customer_tes_ratings;
			String pngimage="";
			int length;
			int im;
			
			//################### SQL Query to get Battery list random from Cooke location  
			Get_Customer_ratings_tes_SQL ="SELECT no_of_rating,order_id,city,DATE_FORMAT(creation_date,'%b %d %Y') as date,rating_comments,name,email from customer_rating_details where upd_comm_flag='Yes'and no_of_rating >3 and CHAR_LENGTH(rating_comments)>200 order by updated_date desc,no_of_rating desc limit 30;";
			LogLevel.DEBUG(5, new Throwable(), "Get_Customer_ratings_tes_SQL :" + Get_Customer_ratings_tes_SQL);
			
			Vector_Get_Customer_tes_ratings =qm.executeQuery(Get_Customer_ratings_tes_SQL);
			
			if(Vector_Get_Customer_tes_ratings.isEmpty())
			{
				
			}
			else
			{
					strRes=strRes+"<div class='carousel-inner'>";
					
					for(int i=0;i<10;i++)
					{
							Hashtable htr=(Hashtable)Vector_Get_Customer_tes_ratings.get(i);
							im=i;
							
							String rating_comments =String.valueOf(htr.get("rating_comments"));
							String order_id =String.valueOf(htr.get("order_id"));
							LogLevel.DEBUG(5, new Throwable(), "order_id :" + order_id);
							String name =String.valueOf(htr.get("name"));
							String no_of_rating =String.valueOf(htr.get("no_of_rating"));
							String city =String.valueOf(htr.get("city"));
							String date =String.valueOf(htr.get("date"));
							
							if(i==0){strRes=strRes+"<div class='item carousel-item active'>";}
							else{strRes=strRes+"<div class='item carousel-item'>";}
							
							strRes=strRes+"<div class='img-box'><img src='/bookbattery/images/happy_cust.png' alt=''></div><div class='star-rating'><ul class='list-inline'>";
							
							int no_of_rating_int = Integer.parseInt(no_of_rating);
							
							for(int r=0;r<5;r++)
							{
								if(no_of_rating_int>r)
								{
									strRes=strRes+"<li class='list-inline-item'><i class='fa fa-star'></i></li>";
								}
								else
								{
									strRes=strRes+"<li class='list-inline-item'><i class='fa fa-star-o'></i></li>";
								}
								
							}
						
							strRes=strRes+"</ul></div><p class='testimonial'>"+rating_comments+"</p><p class='date'>"+date+"</p><p class='overview'><b>"+name+"</b>, "+city+"</p></div>";
							
							//strRes=strRes+"<div class='row'><div class='col-md-12'><div class='testimonial-slider owl-carousel owl-theme' style='display: block; opacity: 1;'><div class='owl-wrapper-outer'><div class='owl-wrapper' style='width: 2224px; left: 0px; display: block;'>";
								
							/*if(i%2==0)
							{
								strRes=strRes+"<div class='row'><div class='col-md-12'><div class='testimonial-slider owl-carousel owl-theme' style='display: block; opacity: 1;'><div class='owl-wrapper-outer'><div class='owl-wrapper' style='width: 2224px; left: 0px; display: block;'>";
							}
							else
							{
								strRes=strRes;
							}

							strRes=strRes+"<div class='timeline-badge'><a><i class='fa fa-circle' id=''></i></a></div><div class='timeline-panel'><div class='timeline-heading'><h4>"+name+"</h4><br/></div><div class='timeline-body'><p>"+rating_comments+"</p></div><div class='timeline-footer'><p class='text-right'>"+city+"</p></div></div></li>";*/
							
							
					}
					
					
					strRes=strRes+"</div>";
				
				

					// length = Vector_Get_Customer_ratings.size();
					
					
					/*if((length%3)==0)
					{
						length=Vector_Get_Customer_ratings.size();
					}
					else
					{
						if((length%3)==1)
						{
							length=length-1;
						}
						else if((length%3)==2)
						{
							length=length-2;
						}
						else
						{
							length=length;
						}
					}
					

					for (int k=0; k<length; k++)
					{
					
					Hashtable htr=(Hashtable)Vector_Get_Customer_ratings.get(k);
						im=k;
						
						String rating_comments =String.valueOf(htr.get("rating_comments"));
						String order_id =String.valueOf(htr.get("order_id"));
						LogLevel.DEBUG(5, new Throwable(), "order_id :" + order_id);
						String name =String.valueOf(htr.get("name"));
						String no_of_rating =String.valueOf(htr.get("no_of_rating"));
						String city =String.valueOf(htr.get("city"));

						
						
						String Get_Customer_ratings_state_SQL ="SELECT state from search_whereever_keywords where city='"+city+"';";
						
						Hashtable State_Name_HT = qm.getRow(Get_Customer_ratings_state_SQL);
						
						String State=(String)State_Name_HT.get("state");
						
						LogLevel.DEBUG(5, new Throwable(), "State :" + State);
						
						int no_of_rating_int = Integer.parseInt(no_of_rating);
						
						if(k==0)
						{
							strRes=strRes+"<div class='item active'>";
						}
						else
						{
							strRes=strRes+"<div class='item'>";
						}

						strRes=strRes+"<div class='col-md-12'><div class='col-md-4'><div style='margin: 93px 24px 18px 65px;'><span class='rating-input'>";
						
							for(int r=0;r<5;r++)
							{
								if(no_of_rating_int>r)
								{
									strRes=strRes+"<span data-value='"+r+"' class='glyphicon glyphicon-star'></span>";
								}
								else
								{
									strRes=strRes+"<span data-value='"+r+"' class='glyphicon glyphicon-star-empty'></span>";
								}
								
							}
								
								
						strRes=strRes+"</span></div><h3 id='t"+k+"'>"+name+"</h3><h5><i class='fa fa-map-marker' aria-hidden='true'></i>"+State+", "+city+"</h5></div><div class='col-md-8 paratext'><h3>Our Happy Customer</h3><br><p id='p"+k+"'>"+rating_comments+"</p></div></div></div>";


						
						
						
						if(im%4==0)
						{
							  pngimage ="BookBattery-Amaron.png";
						}
						else if(im%4==3)
						{
							  pngimage ="Car-Battery-Health-Checkup.png";
						}
						else if(im%4==2)
						{
							  pngimage ="BookBattery-with-Manufacture-Warranty-Guarantee-amaron.png";
						}
						else if(im%4==1)
						{
							 pngimage ="Inverter-and-Battery-Combos.png";
						}
						else
						{
							
						}
						strRes=strRes+"<div class='no_backgrd item'><img src='/bookbattery/images/banners/"+pngimage+"'  alt='BookBattery Amaron inverter and battery combos offer '></div>";
					}  */
		
		
			}
		}

		
		catch (Exception e)
		{
			LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
			e.printStackTrace();
		}
		return strRes;
	}	

	/*	
/**************************************************************************************************************************************\
	* This Function is th get the customer ratings for testimonials page#################################
	\* **************************************************************************************************************************************/
	public String Validate_Ref_Code(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
	String strRes="";
	
		String refcode= (req.getParameter("refcode")!=null)?(req.getParameter("refcode")):"";
		LogLevel.DEBUG(5,new Throwable(),"refcode:"+refcode);
	
		try
		{	
			String Get_Coupon_Code_SQL="";
			
			//################### SQL Query to check vaild referral code or not   
			Get_Coupon_Code_SQL ="SELECT order_coupon_code,coupon_code_expiry_date from generated_referal_code_details where order_coupon_code='"+refcode+"'";
			
			LogLevel.DEBUG(5, new Throwable(), "Get_Coupon_Code_SQL :" + Get_Coupon_Code_SQL);
			
			Hashtable HT_Ref_Code =qm.getRow(Get_Coupon_Code_SQL);
			
			if(HT_Ref_Code.isEmpty())
			{
				strRes="Code is not Available";
			}
			else
			{
				String order_coupon_code = String.valueOf(HT_Ref_Code.get("order_coupon_code"));
				
				LogLevel.DEBUG(5,new Throwable(),"order_coupon_code:"+order_coupon_code);
				
				String coupon_code_expiry_date = String.valueOf(HT_Ref_Code.get("coupon_code_expiry_date"));
				
				LogLevel.DEBUG(5,new Throwable(),"coupon_code_expiry_date:"+coupon_code_expiry_date);
	
				  SimpleDateFormat objSDF = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
				  SimpleDateFormat objSDF1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				  Date dt_1 = objSDF1.parse(coupon_code_expiry_date);
				  
				  LogLevel.DEBUG(5,new Throwable(),"Date 1"+dt_1);
				  Date dt_2=java.util.Calendar.getInstance().getTime();  
				  // Date dt_2 = objSDF.parse(date);
				  
				  LogLevel.DEBUG(5,new Throwable(),"Date 2"+dt_2);
				  
					if (dt_1.compareTo(dt_2) < 0) {
						
						LogLevel.DEBUG(5,new Throwable(),"Date 1 occurs before Date 2");
						strRes="Code is expired";
					} 
					else
					{
						if(order_coupon_code.equals(refcode))
						{
							 strRes="Code Applied Successfully";
						}
						else
						{
							 strRes="Invalid Code";
						}
						
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
	
	/*	
/**************************************************************************************************************************************\
	* This Function is th get the customer ratings for testimonials page#################################
	\* **************************************************************************************************************************************/
	public String Get_Customer_ratings_Testimonial1(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String Get_Customer_ratings_tes_SQL="";
			Vector Vector_Get_Customer_tes_ratings;
			String pngimage="";
			int length;
			int im;
			
			//################### SQL Query to get Battery list random from Cooke location  
			Get_Customer_ratings_tes_SQL ="SELECT no_of_rating,order_id,city,rating_comments,name,email from customer_rating_details where upd_comm_flag='Yes' order by updated_date desc,no_of_rating desc;";
			LogLevel.DEBUG(5, new Throwable(), "Get_Customer_ratings_tes_SQL :" + Get_Customer_ratings_tes_SQL);
			
			Vector_Get_Customer_tes_ratings =qm.executeQuery(Get_Customer_ratings_tes_SQL);
			
			if(Vector_Get_Customer_tes_ratings.isEmpty())
			{
				
			}
			else
			{
				
					for(int i=0;i<Vector_Get_Customer_tes_ratings.size();i++)
					{
							
							Hashtable htr=(Hashtable)Vector_Get_Customer_tes_ratings.get(i);
							im=i;
							
							String rating_comments =String.valueOf(htr.get("rating_comments"));
							String order_id =String.valueOf(htr.get("order_id"));
							LogLevel.DEBUG(5, new Throwable(), "order_id :" + order_id);
							String name =String.valueOf(htr.get("name"));
							String no_of_rating =String.valueOf(htr.get("no_of_rating"));
							String city =String.valueOf(htr.get("city"));
							
						
						/*	
							strRes=strRes+"<div class='testimonial col-md-12'><div class='testimonial-content'><div class='col-md-3' style='margin-top: 3%;'><h3 class='testimonial-title'>"+name+"<small class='post'><i class='fa fa-map-marker' aria-hidden='true'></i>"+city+"</small></h3><div style='margin: 0px 4px 1px 55px;'><span class='rating-input'><span data-value='0' class='glyphicon glyphicon-star'></span><span data-value='1' class='glyphicon glyphicon-star'></span><span data-value='2' class='glyphicon glyphicon-star'></span><span data-value='3' class='glyphicon glyphicon-star'></span><span data-value='4' class='glyphicon glyphicon-star'></span></span></div></div><div class='col-md-9' style='padding-left: 0px;'><p class='description'>"+rating_comments+"</p></div></div></div>";
							
							*/
								
							if(i%2==0)
							{
								strRes=strRes+"<li>";
							}
							else
							{
								strRes=strRes+"<li class='timeline-inverted'>";
							}

							strRes=strRes+"<div class='timeline-badge'><a><i class='fa fa-circle' id=''></i></a></div><div class='timeline-panel'><div class='timeline-heading'><h4>"+name+"</h4><br/></div><div class='timeline-body'><p>"+rating_comments+"</p></div><div class='timeline-footer'><p class='text-right'><i class='fa fa-map-marker' aria-hidden='true' style='padding: 4px;font-size: 14px;'></i>"+city+"</p></div></div></li>";
							
							
					}
					
					
				
				
				

					// length = Vector_Get_Customer_ratings.size();
					
					
					/*if((length%3)==0)
					{
						length=Vector_Get_Customer_ratings.size();
					}
					else
					{
						if((length%3)==1)
						{
							length=length-1;
						}
						else if((length%3)==2)
						{
							length=length-2;
						}
						else
						{
							length=length;
						}
					}
					

					for (int k=0; k<length; k++)
					{
					
					Hashtable htr=(Hashtable)Vector_Get_Customer_ratings.get(k);
						im=k;
						
						String rating_comments =String.valueOf(htr.get("rating_comments"));
						String order_id =String.valueOf(htr.get("order_id"));
						LogLevel.DEBUG(5, new Throwable(), "order_id :" + order_id);
						String name =String.valueOf(htr.get("name"));
						String no_of_rating =String.valueOf(htr.get("no_of_rating"));
						String city =String.valueOf(htr.get("city"));

						
						
						String Get_Customer_ratings_state_SQL ="SELECT state from search_whereever_keywords where city='"+city+"';";
						
						Hashtable State_Name_HT = qm.getRow(Get_Customer_ratings_state_SQL);
						
						String State=(String)State_Name_HT.get("state");
						
						LogLevel.DEBUG(5, new Throwable(), "State :" + State);
						
						int no_of_rating_int = Integer.parseInt(no_of_rating);
						
						if(k==0)
						{
							strRes=strRes+"<div class='item active'>";
						}
						else
						{
							strRes=strRes+"<div class='item'>";
						}

						strRes=strRes+"<div class='col-md-12'><div class='col-md-4'><div style='margin: 93px 24px 18px 65px;'><span class='rating-input'>";
						
							for(int r=0;r<5;r++)
							{
								if(no_of_rating_int>r)
								{
									strRes=strRes+"<span data-value='"+r+"' class='glyphicon glyphicon-star'></span>";
								}
								else
								{
									strRes=strRes+"<span data-value='"+r+"' class='glyphicon glyphicon-star-empty'></span>";
								}
								
								
							}
								
								
						strRes=strRes+"</span></div><h3 id='t"+k+"'>"+name+"</h3><h5><i class='fa fa-map-marker' aria-hidden='true'></i>"+State+", "+city+"</h5></div><div class='col-md-8 paratext'><h3>Our Happy Customer</h3><br><p id='p"+k+"'>"+rating_comments+"</p></div></div></div>";


						
						
						
						if(im%4==0)
						{
							  pngimage ="BookBattery-Amaron.png";
						}
						else if(im%4==3)
						{
							  pngimage ="Car-Battery-Health-Checkup.png";
						}
						else if(im%4==2)
						{
							  pngimage ="BookBattery-with-Manufacture-Warranty-Guarantee-amaron.png";
						}
						else if(im%4==1)
						{
							 pngimage ="Inverter-and-Battery-Combos.png";
						}
						else
						{
							
						}
						strRes=strRes+"<div class='no_backgrd item'><img src='/bookbattery/images/banners/"+pngimage+"'  alt='BookBattery Amaron inverter and battery combos offer '></div>";
					}  */
		
		
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
	* This method is Insert the Mobile Number of Visitor in Visitor table #################################
	\* **************************************************************************************************************************************/
	public String Insert_Visitor_Details(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
	String strRes="";
	String agent_name="";
	
	String product_type= (req.getParameter("product_type")!=null)?(req.getParameter("product_type")):"";
	LogLevel.DEBUG(5,new Throwable(),"product_type:"+product_type);
	
	String product_make= (req.getParameter("product_make")!=null)?(req.getParameter("product_make")):"";
	LogLevel.DEBUG(5,new Throwable(),"product_make:"+product_make);

	String product_capacity= (req.getParameter("product_capacity")!=null)?(req.getParameter("product_capacity")):"";
	LogLevel.DEBUG(5,new Throwable(),"product_capacity:"+product_capacity);
	
	String product_brand= (req.getParameter("product_brand")!=null)?(req.getParameter("product_brand")):"";
	LogLevel.DEBUG(5,new Throwable(),"product_brand:"+product_brand);

	String product_model= (req.getParameter("product_model")!=null)?(req.getParameter("product_model")):"";
	LogLevel.DEBUG(5,new Throwable(),"product_model:"+product_model);

	String product_state= (req.getParameter("product_state")!=null)?(req.getParameter("product_state")):"";
	LogLevel.DEBUG(5,new Throwable(),"product_state:"+product_state);

	String product_city= (req.getParameter("product_city")!=null)?(req.getParameter("product_city")):"";
	LogLevel.DEBUG(5,new Throwable(),"product_city:"+product_city);
	
	String UserMobileNumber= (req.getParameter("UserMobileNumber")!=null)?(req.getParameter("UserMobileNumber")):"";
	LogLevel.DEBUG(5,new Throwable(),"UserMobileNumber:"+UserMobileNumber);

		try
		{	
			//Query to get the operator name which has been assigned last
			String Operator_Name_SQL = "select agent_name from visitors_orders where mobile_number='"+UserMobileNumber+"' and (NOT agent_name = '0' or agent_name = ' ' ) and YEAR(creation_date) = YEAR(NOW()) AND MONTH(creation_date) = MONTH(NOW()) AND DAY(creation_date) = DAY(NOW()) order by creation_date limit 1";
			LogLevel.DEBUG(5, new Throwable(), "Operator_Name_SQL :" + Operator_Name_SQL);

			Hashtable Operator_Name_HT = qm.getRow(Operator_Name_SQL);
			LogLevel.DEBUG(5, new Throwable(), "Operator_Name_HT :" + Operator_Name_HT);

			if(Operator_Name_HT.isEmpty())
			{
				String Operator_Name_Last_Assigned_SQL = "select agent_name from visitors_orders where (NOT agent_name = '0' or agent_name = ' ' ) order by creation_date desc limit 1";
				LogLevel.DEBUG(5, new Throwable(), "Operator_Name_Last_Assigned_SQL :" + Operator_Name_Last_Assigned_SQL);

				Hashtable Operator_Name_Last_Assigned_HT = qm.getRow(Operator_Name_Last_Assigned_SQL); 
				agent_name=(String)Operator_Name_Last_Assigned_HT.get("agent_name");
				LogLevel.DEBUG(5, new Throwable(), "agent_name :" + agent_name);

				String AssignToAgent="";
				
				int operatorteamcountint = Integer.parseInt(OperatorTeamCount);

				 for(int i=1; i<operatorteamcountint; i++)
				{
					LogLevel.DEBUG(5, new Throwable(), "i value:" +i);						

					if(agent_name.equals("operator"+i+""))
					{

						int jk = new Integer(i+ 1);

						if(jk == operatorteamcountint)
						{
							AssignToAgent="operator1";
							LogLevel.DEBUG(5, new Throwable(), "AssignToAgent:" +AssignToAgent);
							break;
						}
						else
						{
							AssignToAgent="operator"+jk;
							LogLevel.DEBUG(5, new Throwable(), "AssignToAgent:" +AssignToAgent);
							break;
						}

					}
					
				}
				
				if(AssignToAgent.equals(""))
				{
					agent_name="operator1";
				}
				else
				{
					agent_name=AssignToAgent;
				}
			}
			else
			{
				agent_name=(String)Operator_Name_HT.get("agent_name");
				LogLevel.DEBUG(5, new Throwable(), "agent_name :" + agent_name);
			}
			
			String KeyType="";
			
			if(product_type.equals("Inverter"))
			{
				KeyType="inverter";
			}
			else
			{
				KeyType="order";
			}
			//########################## Temp Code to insert agent_name after disabling Visitor Mobile Number Capture 24-Oct-2016
			if(UserMobileNumber.equals("0") || UserMobileNumber.equals(" "))
			{
				agent_name="operator";
			}
			else
			{
				agent_name=agent_name;
			}
			
			String Insert_Visitor_SQL = "insert into visitors_orders(vis_ord_id,bat_type,veh_make,veh_model,bat_brand,bat_capacity,state,city,area,pincode,mobile_number,option_type,keynumber,visitors_comments,creation_date,agent_name)values(NULL,'"+product_type+"','"+product_make+"','"+product_model+"','"+product_brand+"','"+product_capacity+"','"+product_state+"','"+product_city+"','','','"+UserMobileNumber+"','"+KeyType+"','','',now(),'"+agent_name+"')";
			LogLevel.DEBUG(5,new Throwable(),"Insert_Visitor_SQL: "+Insert_Visitor_SQL);
			int reslt = qm.executeUpdate(Insert_Visitor_SQL);
			
			
			if (reslt>=0)
			{
				strRes="Successfully Added";
			}
			else
			{
				strRes="Failed to Add";
			}
		}
		catch (Exception e)
		{
			LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
			e.printStackTrace();
		}
		return strRes;
	}
	
	/* *************************************************************************************************************\
	* This method is used to send  verification to user email id and mobile number when user enters mobile number in order now battery 
	\* *************************************************************************************************************/
	public String GetVerificationSms(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort,String SMSFromAddress) 
	{ 	
		String UserMobileNumber = (req.getParameter("UserMobileNumber")!=null)?(req.getParameter("UserMobileNumber")):"0";
		LogLevel.DEBUG(5, new Throwable(), "UserMobileNumber :" + UserMobileNumber);
		
		//String UserMobileNumber_Coo="";
		//String UserMobileNumber_code_Coo="";
		
		String OTP = (req.getParameter("OTP")!=null)?(req.getParameter("OTP")):"0";
		LogLevel.DEBUG(5, new Throwable(), "OTP1 :" + OTP);

		String strRes="";
		try
		{
			/*Cookie[] cookies = req.getCookies();
			if (cookies != null) {
			 for (Cookie cookie : cookies) {
				 
			   if (cookie.getName().equals("UserMobileNumber_Coo")) {
					UserMobileNumber_Coo=cookie.getValue();
				}	 
			   if (cookie.getName().equals("UserMobileNumber_code_Coo")) {
					UserMobileNumber_code_Coo=cookie.getValue();
				}
				
			  }
			}*/
			//LogLevel.DEBUG(5, new Throwable(), "UserMobileNumber_Coo :" + UserMobileNumber_Coo);
			//LogLevel.DEBUG(5, new Throwable(), "UserMobileNumber_code_Coo :" + UserMobileNumber_code_Coo);
			if(UserMobileNumber.length() < 10)
			{
				strRes="Opps, Failed to send SMS";
				return strRes;
			}
			else if(!OTP.equals("0") && !OTP.equals(""))
			{
				
				
				OTP = OTP.substring(4, 10); 
				OTP=OTP.trim();
				LogLevel.DEBUG(5, new Throwable(), "OTP :" + OTP);				
				CompareTrans ct = new CompareTrans(qm);
				SendSMS sendsms = new SendSMS(qm);
				String ThankUMsg="Your Verification Code is "+OTP+"";
				LogLevel.DEBUG(5, new Throwable(), "strIpAddress :" + strIpAddress);
				LogLevel.DEBUG(5, new Throwable(), "strPort :" + strPort);
				LogLevel.DEBUG(5, new Throwable(), "SMSFromAddress :" + SMSFromAddress);
				LogLevel.DEBUG(5, new Throwable(), "ThankUMsg :" + ThankUMsg);
				LogLevel.DEBUG(5, new Throwable(), "UserMobileNumber :" + UserMobileNumber);

				String strSMSResponse=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUMsg, UserMobileNumber);
				LogLevel.DEBUG(5, new Throwable(), "strSMSResponse :" + strSMSResponse);
				
				if(strSMSResponse.isEmpty())
				{ 
					strRes="Opps, Failed to send SMS";
					return strRes;
				}
				else
				{
					strRes=OTP;		
					return strRes;
				}
			}
			else
			{
				/*following code is for generating the random number */
				Random generator = new Random();   
				generator.setSeed(System.currentTimeMillis());   
				int num = generator.nextInt(900000) + 100000;   
				if (num < 100000 || num > 999999)
				{   
				num = generator.nextInt(900000) + 100000;   
				if (num < 100000 || num > 999999)
				{   
				}   
				}   
				String verificationcode = "";
				verificationcode = Integer.toString(num);
				/*code to generate the random number ends here */
				CompareTrans ct = new CompareTrans(qm);
				SendSMS sendsms = new SendSMS(qm);
				String ThankUMsg="Your Verification Code is "+verificationcode+"";
				LogLevel.DEBUG(5, new Throwable(), "strIpAddress :" + strIpAddress);
				LogLevel.DEBUG(5, new Throwable(), "strPort :" + strPort);
				LogLevel.DEBUG(5, new Throwable(), "SMSFromAddress :" + SMSFromAddress);
				LogLevel.DEBUG(5, new Throwable(), "ThankUMsg :" + ThankUMsg);
				LogLevel.DEBUG(5, new Throwable(), "UserMobileNumber :" + UserMobileNumber);

				String strSMSResponse=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUMsg, UserMobileNumber);
				LogLevel.DEBUG(5, new Throwable(), "strSMSResponse :" + strSMSResponse);
				
				if(strSMSResponse.isEmpty())
				{ 
					strRes="Opps, Failed to send SMS";
					return strRes;
				}
				else
				{
					// Create cookies for first and last names.      
					//Cookie UserMobileNumber_Cookie = new Cookie("UserMobileNumber_Coo",UserMobileNumber);
					//Cookie UserMobileNumber_code_Cookie = new Cookie("UserMobileNumber_code_Coo",verificationcode);
					
					// Set expiry date after 15 min for both the cookies.
					//UserMobileNumber_Cookie.setMaxAge(60*15); 
					//UserMobileNumber_code_Cookie.setMaxAge(60*15); 
					
					// Set path for cookies.
					//UserMobileNumber_Cookie.setPath("/");
					//UserMobileNumber_code_Cookie.setPath("/");
					
					
					// Add both the cookies in the response header.
					//res.addCookie( UserMobileNumber_Cookie );
					//res.addCookie( UserMobileNumber_code_Cookie );
					// Set response content type
					//res.setContentType("text/html");
			
					strRes=verificationcode;
					
					/* code to add the random digits in fron of OTP and Back of OTP for security Purpose starts here */						
					Random generator1 = new Random();   
					generator1.setSeed(System.currentTimeMillis());   
					int num1 = generator1.nextInt(900) + 100;   
					if (num1 < 100 || num1 > 999)
					{   
					num1 = generator1.nextInt(900) + 100;   
					if (num1 < 100 || num1 > 999)
					{   
					}   
					}
					Random generator2 = new Random();   
					generator2.setSeed(System.currentTimeMillis());   
					int num2 = generator2.nextInt(9000) + 1000;   
					if (num2 < 1000 || num2 > 9999)
					{   
					num2 = generator2.nextInt(9000) + 1000;   
					if (num2 < 1000 || num2 > 9999)
					{   
					}   
					}
					String last = Integer.toString(num1);
					LogLevel.DEBUG(5, new Throwable(), "last :" + last);
					String first = Integer.toString(num2);
					LogLevel.DEBUG(5, new Throwable(), "first :" + first);
					
					//req.setAttribute("OTP",verificationcode);
					/* code to add the random digits in fron of OTP and Back of OTP for security Purpose ends here */	
					
					strRes=first+strRes+last;
					LogLevel.DEBUG(5, new Throwable(), "FinalstrRes :" + strRes);		
					return strRes;
				}
			}
		}
		catch (Exception e)
		{
			LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
			strRes=strRes;
		}
		return strRes;
	}

	/* *************************************************************************************************************\
	* This action is used to Insert order details in to Orders DB before Ordering 
	\* *************************************************************************************************************/
	public String InsertBeforeOrder(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort,String SMSFromAddress, String FromEmailId, String strsuppemaild, String baseUrl, String strsuppmobnumber,String strCMSSERVERIP,String strPDFFilePath,String strPDFRelFilePath,String baseurldirectory,String OperatorTeamCount) 
	{ 	
	
		String UserMobileNumber= (req.getParameter("UMN")!=null)?(req.getParameter("UMN")):"";
		String ProductType= (req.getParameter("UPT")!=null)?(req.getParameter("UPT")):"";
		String ProductBrand= (req.getParameter("UPB")!=null)?(req.getParameter("UPB")):"";
		String ProductModel= (req.getParameter("UPM")!=null)?(req.getParameter("UPM")):"";
		String ProductVehicleMake= (req.getParameter("UVMK")!=null)?(req.getParameter("UVMK")):"";
		String ProductVehicleModel= (req.getParameter("UVMD")!=null)?(req.getParameter("UVMD")):"";
		String UserState= (req.getParameter("UST")!=null)?(req.getParameter("UST")):"";
		String UserCity= (req.getParameter("UCT")!=null)?(req.getParameter("UCT")):"";

		
		String agent_name ="";
		//password = password.replace("\\","\\\\"); 

		String WithOldBatteryPrice;
		String WithoutOldBatteryPrice;
		String strRes="";
		String State_1="";
		String strstate="";
		String strstate1="";
		String StrSqlQry ="";
		String Strretid="";
		String Strlocorpin="";
		String strcustax="";
		String strcusper="";	
		
		String ProductCapacity="";
		String ProductActualPrice="";
		String ProductDiscountPrice="";
		String City_Percentage="";
		String ERP_Pre_TAX ="";
		
		UserMobileNumber =UserMobileNumber.replaceAll("\\+", " ");
		ProductType =ProductType.replaceAll("\\+", " ");
		ProductBrand =ProductBrand.replaceAll("\\+", " ");
		ProductModel =ProductModel.replaceAll("\\+", " ");
		ProductVehicleMake =ProductVehicleMake.replaceAll("\\+", " ");
		ProductVehicleModel =ProductVehicleModel.replaceAll("\\+", " ");
		UserState =UserState.replaceAll("\\+", " ");
		UserCity =UserCity.replaceAll("\\+", " ");

	
		Hashtable htretailerid =new Hashtable();
		try
		{
			UserMobileNumber =java.net.URLDecoder.decode(UserMobileNumber, "UTF-8");
			ProductType =java.net.URLDecoder.decode(ProductType, "UTF-8");
			ProductBrand =java.net.URLDecoder.decode(ProductBrand, "UTF-8");
			ProductModel =java.net.URLDecoder.decode(ProductModel, "UTF-8");
			ProductVehicleMake =java.net.URLDecoder.decode(ProductVehicleMake, "UTF-8");
			ProductVehicleModel =java.net.URLDecoder.decode(ProductVehicleModel, "UTF-8");
			UserState =java.net.URLDecoder.decode(UserState, "UTF-8");
			UserCity =java.net.URLDecoder.decode(UserCity, "UTF-8");
			String DB_Orders_Table="";
			String DB_Orders_Table_ID="";
			String DB_Orders_Cap="";
			if(ProductType.equals("Inverter") || ProductType.equals("Inverter and Battery Combo"))
			{
				DB_Orders_Table_ID="order_id";
				DB_Orders_Table="inverter_order_details";
				DB_Orders_Cap="I";
			}
			else
			{
				DB_Orders_Table_ID="ord_id";
				DB_Orders_Table="battery_order_details";
				DB_Orders_Cap="B";
			}
			
			//######################################## Operator Code	####################
			String Get_Previous_Operator_SQL = "select agent_name from "+DB_Orders_Table+" where consumer_mobnumber='"+UserMobileNumber+"' and (NOT agent_name = '0' or agent_name = ' ' ) and YEAR(creation_date) = YEAR(NOW()) AND MONTH(creation_date) = MONTH(NOW()) AND DAY(creation_date) = DAY(NOW()) order by creation_date limit 1";
			LogLevel.DEBUG(5, new Throwable(), "Get_Previous_Operator_SQL :" + Get_Previous_Operator_SQL);

			Hashtable Get_Previous_Operator_HT = qm.getRow(Get_Previous_Operator_SQL);
			LogLevel.DEBUG(5, new Throwable(), "Get_Previous_Operator_HT :" + Get_Previous_Operator_HT);

			if(Get_Previous_Operator_HT.isEmpty())
			{
				String Operator_Name_Last_Assigned_SQL = "select agent_name from "+DB_Orders_Table+" where (NOT agent_name = '0' or agent_name = ' ' ) order by creation_date desc limit 1";
				LogLevel.DEBUG(5, new Throwable(), "Operator_Name_Last_Assigned_SQL :" + Operator_Name_Last_Assigned_SQL);

				Hashtable Operator_Name_Last_Assigned_HT = qm.getRow(Operator_Name_Last_Assigned_SQL); 
				agent_name=(String)Operator_Name_Last_Assigned_HT.get("agent_name");
				String AssignToAgent="";
				int operatorteamcountint = Integer.parseInt(OperatorTeamCount);

				for(int i=1; i<operatorteamcountint; i++)
				{
					LogLevel.DEBUG(5, new Throwable(), "i value:" +i);						
					if(agent_name.equals("operator"+i+""))
					{
						int jk = new Integer(i+ 1);
						if(jk == operatorteamcountint)
						{
							AssignToAgent="operator1";
							LogLevel.DEBUG(5, new Throwable(), "AssignToAgent:" +AssignToAgent);
							break;
						}
						else
						{
							AssignToAgent="operator"+jk;
							LogLevel.DEBUG(5, new Throwable(), "AssignToAgent:" +AssignToAgent);
							break;
						}
					}
				}
				if(AssignToAgent.equals(""))
				{
					agent_name="operator1";
				}
				else
				{
					agent_name=AssignToAgent;
				}

			}
			else
			{
				agent_name=(String)Get_Previous_Operator_HT.get("agent_name");
				LogLevel.DEBUG(5, new Throwable(), "agent_name :" + agent_name);
				// This condition is to assign operator in Worst case of Software failure.  
				if(agent_name.equals(""))
				{
					agent_name="operator1";
				}
				else
				{
					agent_name=agent_name;
				}
			}
			
			
			//######################################## Order Number Code	####################
				
			String New_Order_ID_SQL = "SELECT "+DB_Orders_Table_ID+" as ORDER_ID FROM "+DB_Orders_Table+" ORDER BY "+DB_Orders_Table_ID+" DESC LIMIT 1";
			LogLevel.DEBUG(5, new Throwable(), "New_Order_ID_SQL :" + New_Order_ID_SQL);

			Hashtable New_Order_ID_HT = qm.getRow(New_Order_ID_SQL);
			LogLevel.DEBUG(5, new Throwable(), "New_Order_ID_HT :" + New_Order_ID_HT);

			int Last_Order_Count;
			if(New_Order_ID_HT.isEmpty())
			{
				/*following code is for generating the random number */
				Random Generator_Order_ID = new Random();   
				Generator_Order_ID.setSeed(System.currentTimeMillis());  
				LogLevel.DEBUG(5, new Throwable(), "Generator_Order_ID:" +Generator_Order_ID);				
				int num = Generator_Order_ID.nextInt(90) + 10;
				LogLevel.DEBUG(5, new Throwable(), "num:" +num);
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
			LogLevel.DEBUG(5, new Throwable(), "Generator_Order_ID_Ran:" +Generator_Order_ID_Ran);	
			int Num = Generator_Order_ID_Ran.nextInt(90) + 10;   
			LogLevel.DEBUG(5, new Throwable(), "Num:" +Num);	
			if (Num < 100 || Num > 999)
			{   
				Num = Generator_Order_ID_Ran.nextInt(90) + 10;   
				if (Num < 100 || Num > 999)
				{   
				}   
			} 
			LogLevel.DEBUG(5, new Throwable(), "Num:" +Num);	
			String Last_Order_Count_String = Integer.toString(Last_Order_Count);
			String verificationcode = "";
			String verificationcode1 = Integer.toString(Num);
			verificationcode ="ORD"+DB_Orders_Cap+""+Last_Order_Count_String+""+verificationcode1+"B";
			LogLevel.DEBUG(5, new Throwable(), "verificationcode :" + verificationcode);
			
			
			LogLevel.DEBUG(5, new Throwable(), "ProductType :" + ProductType);
			
			String strSqlQry="";
			if(ProductType.equals("Inverter") || ProductType.equals("Inverter and Battery Combo"))
			{
	
				String Get_Product_Price_SQL ="select DISTINCT a.inverter_actual_price, a.inverter_discount_price, a.inverter_capacity, a.inverter_eretailer_price, b.inverter_city_percentage from inverter_price_details a, percentage b where a.state='"+UserState+"' and a.city='"+UserCity+"' and a.inverter_brand='"+ProductBrand+"' and a.inverter_model='"+ProductModel+"' and a.city=b.city "; 
				LogLevel.DEBUG(5, new Throwable(), "Get_Product_Price_SQL :" + Get_Product_Price_SQL);

				Hashtable Get_Product_Price_SQL_HT = qm.getRow(Get_Product_Price_SQL);
				LogLevel.DEBUG(5, new Throwable(), "Get_Product_Price_SQL_HT :" + Get_Product_Price_SQL_HT);

				if(Get_Product_Price_SQL_HT.isEmpty())
				{
					strRes="Session Expired or Server Down. Please regenerate your order.";
					return strRes;
				}
				else{
					int ProductActualPrice_int=(Integer)Get_Product_Price_SQL_HT.get("inverter_actual_price");
					LogLevel.DEBUG(5, new Throwable(), "ProductActualPrice_int :" + ProductActualPrice_int);
					
					int ProductDiscountPrice_int=(Integer)Get_Product_Price_SQL_HT.get("inverter_discount_price");
					LogLevel.DEBUG(5, new Throwable(), "ProductDiscountPrice_int :" + ProductDiscountPrice_int);
					
					int ProductERP_Pre_TAX_int=(Integer)Get_Product_Price_SQL_HT.get("inverter_eretailer_price");
					LogLevel.DEBUG(5, new Throwable(), "ProductERP_Pre_TAX_int :" + ProductERP_Pre_TAX_int);
					
					ProductActualPrice = Integer.toString(ProductActualPrice_int);
					ProductDiscountPrice = Integer.toString(ProductDiscountPrice_int);
					ERP_Pre_TAX = Integer.toString(ProductERP_Pre_TAX_int);
					
					ProductCapacity=(String)Get_Product_Price_SQL_HT.get("inverter_capacity");
					LogLevel.DEBUG(5, new Throwable(), "ProductCapacity :" + ProductCapacity);
					
					City_Percentage=(String)Get_Product_Price_SQL_HT.get("inverter_city_percentage");
					LogLevel.DEBUG(5, new Throwable(), "City_Percentage :" + City_Percentage);

				}
				
				//#### In changes in bellow condition, You need to Update the same in InsertOrderDetails_XXXXX Condition #####
				strSqlQry = "insert into inverter_order_details(order_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_verify_code,retailer_name,retailer_mobile_number,retailer_emailid,state,city,inverter_brand,inverter_model,inverter_capacity,price,area,pincode,creation_date,agent_name,order_status,erp_pre_tax,MRP_Price,city_percentage)values(NULL,'"+verificationcode+"','','"+UserMobileNumber+"','','0','','','','"+UserState+"','"+UserCity+"','"+ProductBrand+"','"+ProductModel+"','"+ProductCapacity+"','"+ProductDiscountPrice+"','','',now(),'"+agent_name+"','Not Confirmed','"+ERP_Pre_TAX+"','"+ProductActualPrice+"','"+City_Percentage+"')";
				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
			}
			else
			{
				
				String Get_Product_Price_SQL ="select DISTINCT a.batprice_id, a.bat_act_price, a.bat_witbat_price, a.bat_ret_price, a.erp_pre_tax, b.city_percentage from batteryprice a, percentage b where a.bat_brand='"+ProductBrand+"' and a.bat_model='"+ProductModel+"' and a.state='"+UserState+"' and a.city='"+UserCity+"' and a.city=b.city ORDER BY batprice_id DESC LIMIT 1"; 
				LogLevel.DEBUG(5, new Throwable(), "Get_Product_Price_SQL :" + Get_Product_Price_SQL);

				Hashtable Get_Product_Price_SQL_HT = qm.getRow(Get_Product_Price_SQL);
				LogLevel.DEBUG(5, new Throwable(), "Get_Product_Price_SQL_HT :" + Get_Product_Price_SQL_HT);

				if(Get_Product_Price_SQL_HT.isEmpty())
				{
					strRes="Session Expired or Server Down. Please regenerate your order.";
					return strRes;
				}
				else
				{
					ProductActualPrice=(String)Get_Product_Price_SQL_HT.get("bat_act_price");
					LogLevel.DEBUG(5, new Throwable(), "ProductActualPrice :" + ProductActualPrice);
					
					WithoutOldBatteryPrice=(String)Get_Product_Price_SQL_HT.get("bat_witbat_price");
					LogLevel.DEBUG(5, new Throwable(), "WithoutOldBatteryPrice :" + WithoutOldBatteryPrice);
					
					String Battery_Ret_Price=(String)Get_Product_Price_SQL_HT.get("bat_ret_price");
					LogLevel.DEBUG(5, new Throwable(), "Battery_Ret_Price :" + Battery_Ret_Price);
					
					ERP_Pre_TAX =(String)Get_Product_Price_SQL_HT.get("erp_pre_tax");
					LogLevel.DEBUG(5, new Throwable(), "ERP_Pre_TAX :" + ERP_Pre_TAX);
					
					City_Percentage=(String)Get_Product_Price_SQL_HT.get("city_percentage");
					LogLevel.DEBUG(5, new Throwable(), "City_Percentage :" + City_Percentage);

					int Battery_Act_Price_int = Integer.parseInt(ProductActualPrice);
					int WithoutOldBatteryPrice_int = Integer.parseInt(WithoutOldBatteryPrice);
					int Battery_Ret_Price_int = Integer.parseInt(Battery_Ret_Price);
					int WithOldBatteryPrice_int = WithoutOldBatteryPrice_int - Battery_Ret_Price_int;
					
					WithOldBatteryPrice = Integer.toString(WithOldBatteryPrice_int);
					WithoutOldBatteryPrice = Integer.toString(WithoutOldBatteryPrice_int);
				}
				
				//#### In changes in bellow condition, You need to Update the same in InsertOrderDetails_XXXXX Condition #####
				strSqlQry = "insert into battery_order_details(ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_verif_code,retailer_name,retailer_mobilnumber,retailer_emailis,state,city,battery_brand,battery_model,price,area,pincode,pdfurl,bat_type,veh_name,veh_model,witholdbatprice,creation_date,agent_name,order_status,erp_pre_tax,MRP_Price,city_percentage)values(NULL,'"+verificationcode+"','','"+UserMobileNumber+"','','0','','','','"+UserState+"','"+UserCity+"','"+ProductBrand+"','"+ProductModel+"','"+WithoutOldBatteryPrice+"','','','','"+ProductType+"','"+ProductVehicleMake+"','"+ProductVehicleModel+"','"+WithOldBatteryPrice+"',now(),'"+agent_name+"','Not Confirmed','"+ERP_Pre_TAX+"','"+ProductActualPrice+"','"+City_Percentage+"')";
				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
				
			}

			int reslt = qm.executeUpdate(strSqlQry);
			if(reslt<0)
			{
				strRes="Failed";
				return strRes;
			}
			else
			{
				strRes=verificationcode;
				return strRes;
			}

		}
		catch (Exception e)
		{
		LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
		strRes=strRes;
		}
		return strRes;
	}
	
		/* *************************************************************************************************************\
	* This action is used to Insert order details in to Orders DB before Ordering 
	\* *************************************************************************************************************/
	public String InsertBeforeServiceOrder(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort,String SMSFromAddress, String FromEmailId, String strsuppemaild, String baseUrl, String strsuppmobnumber,String strCMSSERVERIP,String strPDFFilePath,String strPDFRelFilePath,String baseurldirectory,String OperatorTeamCount) 
	{ 	
	
		String UserMobileNumber= (req.getParameter("UMN")!=null)?(req.getParameter("UMN")):"";
		String ProductServiceType= (req.getParameter("USeT")!=null)?(req.getParameter("USeT")):"";
		String ProductServicePackage= (req.getParameter("USP")!=null)?(req.getParameter("USP")):"";
		String ProductVehicleMake= (req.getParameter("UVMK")!=null)?(req.getParameter("UVMK")):"";
		String ProductVehicleModel= (req.getParameter("UVMD")!=null)?(req.getParameter("UVMD")):"";
		String UserState= (req.getParameter("UST")!=null)?(req.getParameter("UST")):"";
		String UserCity= (req.getParameter("UCT")!=null)?(req.getParameter("UCT")):"";

		String agent_name ="";
		//password = password.replace("\\","\\\\"); 

		String strRes="";
		
		String ProductMRPPrice="";
		String ProductDiscountPrice="";
		
		UserMobileNumber =UserMobileNumber.replaceAll("\\+", " ");
		ProductServiceType =ProductServiceType.replaceAll("\\+", " ");
		ProductServicePackage =ProductServicePackage.replaceAll("\\+", " ");
		ProductVehicleMake =ProductVehicleMake.replaceAll("\\+", " ");
		ProductVehicleModel =ProductVehicleModel.replaceAll("\\+", " ");
		UserState =UserState.replaceAll("\\+", " ");
		UserCity =UserCity.replaceAll("\\+", " ");

	
		Hashtable htretailerid =new Hashtable();
		try
		{
			UserMobileNumber =java.net.URLDecoder.decode(UserMobileNumber, "UTF-8");
			ProductServiceType =java.net.URLDecoder.decode(ProductServiceType, "UTF-8");
			ProductServicePackage =java.net.URLDecoder.decode(ProductServicePackage, "UTF-8");
			ProductVehicleMake =java.net.URLDecoder.decode(ProductVehicleMake, "UTF-8");
			ProductVehicleModel =java.net.URLDecoder.decode(ProductVehicleModel, "UTF-8");
			UserState =java.net.URLDecoder.decode(UserState, "UTF-8");
			UserCity =java.net.URLDecoder.decode(UserCity, "UTF-8");
			String DB_Orders_Table="";
			String DB_Orders_Table_ID="";
			String DB_Orders_Cap="";
			
			DB_Orders_Table_ID="ord_id";
			DB_Orders_Table="service_order_details";
			DB_Orders_Cap="S";
			
			
			//######################################## Operator Code	####################
			String Get_Previous_Operator_SQL = "select agent_name from "+DB_Orders_Table+" where consumer_mobnumber='"+UserMobileNumber+"' and (NOT agent_name = '0' or agent_name = ' ' ) and YEAR(creation_date) = YEAR(NOW()) AND MONTH(creation_date) = MONTH(NOW()) AND DAY(creation_date) = DAY(NOW()) order by creation_date limit 1";
			LogLevel.DEBUG(5, new Throwable(), "Get_Previous_Operator_SQL :" + Get_Previous_Operator_SQL);

			Hashtable Get_Previous_Operator_HT = qm.getRow(Get_Previous_Operator_SQL);
			LogLevel.DEBUG(5, new Throwable(), "Get_Previous_Operator_HT :" + Get_Previous_Operator_HT);

			if(Get_Previous_Operator_HT.isEmpty())
			{
				String Operator_Name_Last_Assigned_SQL = "select agent_name from "+DB_Orders_Table+" where (NOT agent_name = '0' or agent_name = ' ' ) order by creation_date desc limit 1";
				LogLevel.DEBUG(5, new Throwable(), "Operator_Name_Last_Assigned_SQL :" + Operator_Name_Last_Assigned_SQL);

				Hashtable Operator_Name_Last_Assigned_HT = qm.getRow(Operator_Name_Last_Assigned_SQL); 
				agent_name=(String)Operator_Name_Last_Assigned_HT.get("agent_name");
				String AssignToAgent="";
				int operatorteamcountint = Integer.parseInt(OperatorTeamCount);

				for(int i=1; i<operatorteamcountint; i++)
				{
					LogLevel.DEBUG(5, new Throwable(), "i value:" +i);						
					if(agent_name.equals("operator"+i+""))
					{
						int jk = new Integer(i+ 1);
						if(jk == operatorteamcountint)
						{
							AssignToAgent="operator1";
							LogLevel.DEBUG(5, new Throwable(), "AssignToAgent:" +AssignToAgent);
							break;
						}
						else
						{
							AssignToAgent="operator"+jk;
							LogLevel.DEBUG(5, new Throwable(), "AssignToAgent:" +AssignToAgent);
							break;
						}
					}
				}
				if(AssignToAgent.equals(""))
				{
					agent_name="operator1";
				}
				else
				{
					agent_name=AssignToAgent;
				}

			}
			else
			{
				agent_name=(String)Get_Previous_Operator_HT.get("agent_name");
				LogLevel.DEBUG(5, new Throwable(), "agent_name :" + agent_name);
				// This condition is to assign operator in Worst case of Software failure.  
				if(agent_name.equals(""))
				{
					agent_name="operator1";
				}
				else
				{
					agent_name=agent_name;
				}
			}
			
			
			//######################################## Order Number Code	####################
				
			String New_Order_ID_SQL = "SELECT "+DB_Orders_Table_ID+" as ORDER_ID FROM "+DB_Orders_Table+" ORDER BY "+DB_Orders_Table_ID+" DESC LIMIT 1";
			LogLevel.DEBUG(5, new Throwable(), "New_Order_ID_SQL :" + New_Order_ID_SQL);

			Hashtable New_Order_ID_HT = qm.getRow(New_Order_ID_SQL);
			LogLevel.DEBUG(5, new Throwable(), "New_Order_ID_HT :" + New_Order_ID_HT);

			int Last_Order_Count;
			if(New_Order_ID_HT.isEmpty())
			{
				/*following code is for generating the random number */
				Random Generator_Order_ID = new Random();   
				Generator_Order_ID.setSeed(System.currentTimeMillis());  
				LogLevel.DEBUG(5, new Throwable(), "Generator_Order_ID:" +Generator_Order_ID);				
				int num = Generator_Order_ID.nextInt(90) + 10;
				LogLevel.DEBUG(5, new Throwable(), "num:" +num);
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
			LogLevel.DEBUG(5, new Throwable(), "Generator_Order_ID_Ran:" +Generator_Order_ID_Ran);	
			int Num = Generator_Order_ID_Ran.nextInt(90) + 10;   
			LogLevel.DEBUG(5, new Throwable(), "Num:" +Num);	
			if (Num < 100 || Num > 999)
			{   
				Num = Generator_Order_ID_Ran.nextInt(90) + 10;   
				if (Num < 100 || Num > 999)
				{   
				}   
			} 
			LogLevel.DEBUG(5, new Throwable(), "Num:" +Num);	
			String Last_Order_Count_String = Integer.toString(Last_Order_Count);
			String Order_Number = "";
			String verificationcode1 = Integer.toString(Num);
			Order_Number ="ORD"+DB_Orders_Cap+""+Last_Order_Count_String+""+verificationcode1+"B";
			LogLevel.DEBUG(5, new Throwable(), "Order_Number :" + Order_Number);
			
			
			String strSqlQry="";
			String Services_Type_SQL_Condition="";

			//### To Get The Prices for Make and Model with Service Type and Service Package ############## START 
			if(ProductServiceType.equals("Exterior and Interior Cleaning") || ProductServiceType.equals("Dent Removal and Painting") || ProductServiceType.equals("Road Side Assistance") || ProductServiceType.equals("General Checkup") || ProductServiceType.equals("Car Battery Health Checkup") )
			{
				
				Services_Type_SQL_Condition="and b.vehicle_model='"+ProductVehicleModel+"' and b.vehicle_name='"+ProductVehicleMake+"' and a.vehicle_price_type=b.vehicle_size ";
				
			}
			else if(ProductServiceType.equals("AC Tuneup and Repair"))
			{
				
				Services_Type_SQL_Condition="and b.vehicle_model='"+ProductVehicleModel+"' and b.vehicle_name='"+ProductVehicleMake+"' and a.services_package=b.vehicle_ACtype ";
				
			}
			else if(ProductServiceType.equals("Tyre Services Wheel Balancing and Alignment"))
			{
				
				Services_Type_SQL_Condition="and b.vehicle_model='"+ProductVehicleModel+"' and b.vehicle_name='"+ProductVehicleMake+"' and a.vehicle_price_type=b.vehicle_wheelradius ";
				
			}
			else if(ProductServiceType.equals("Winshield and Glass Repair"))
			{
				
				Services_Type_SQL_Condition=" and a.vehicle_model='"+ProductVehicleModel+"' and a.vehicle_make='"+ProductVehicleMake+"' ";
				
			}
			else
			{
				Services_Type_SQL_Condition=" ";
			}
			
			
			
			String Services_Package_Prices_SQL="SELECT a.service_price_mrp,a.service_price_discount from services_details a, vehicle_details b WHERE a.services_type='"+ProductServiceType+"' and a.services_package='"+ProductServicePackage+"' "+Services_Type_SQL_Condition+" LIMIT 1 ";
			LogLevel.DEBUG(5, new Throwable(), "Services_Package_Prices_SQL :" + Services_Package_Prices_SQL);
			
			Hashtable Services_Package_Prices_HT = qm.getRow(Services_Package_Prices_SQL);
			LogLevel.DEBUG(5, new Throwable(), "Services_Package_Prices_HT :" + Services_Package_Prices_HT);

			if(Services_Package_Prices_HT.isEmpty())
			{
				strRes="Session Expired or Server Down. Please regenerate your order.";
				return strRes;
			}
			else{
				
				ProductMRPPrice=(String)Services_Package_Prices_HT.get("service_price_mrp");
				LogLevel.DEBUG(5, new Throwable(), "ProductMRPPrice :" + ProductMRPPrice);
				
				ProductDiscountPrice=(String)Services_Package_Prices_HT.get("service_price_discount");
				LogLevel.DEBUG(5, new Throwable(), "ProductDiscountPrice :" + ProductDiscountPrice);

			}
			
			
			//#### In changes in bellow condition, You need to Update the same in InsertOrderDetails_XXXXX Condition #####
			strSqlQry = "insert into service_order_details(ord_id,order_number,consumer_mobnumber,consumer_verif_code,services_type,services_package,service_price_mrp,service_price_discount,veh_name,veh_model,creation_date,agent_name,order_status,state,city,created_by,operator)values(NULL,'"+Order_Number+"','"+UserMobileNumber+"','0','"+ProductServiceType+"','"+ProductServicePackage+"','"+ProductMRPPrice+"','"+ProductDiscountPrice+"','"+ProductVehicleMake+"','"+ProductVehicleModel+"',now(),'"+agent_name+"','Not Confirmed','"+UserState+"','"+UserCity+"','ngit','Customer')";
			LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
			
			
			int reslt = qm.executeUpdate(strSqlQry);
			if(reslt<0)
			{
				strRes="Failed";
				return strRes;
			}
			else
			{
				strRes=Order_Number;
				return strRes;
			}

		}
		catch (Exception e)
		{
			LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
			strRes=strRes;
		}
		return strRes;
	}

	
	/* *************************************************************************************************************\
	* This method is used to insert Consumer Battery Order Details in DB
	\* *************************************************************************************************************/
	public String InsertOrderDetails_Battery(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort,String SMSFromAddress, String FromEmailId, String strsuppemaild, String baseUrl, String strsuppmobnumber,String strCMSSERVERIP,String strPDFFilePath,String strPDFRelFilePath,String baseurldirectory,String OperatorTeamCount) 
	{ 	
	
		String UserMobileNumber= (req.getParameter("UMN")!=null)?(req.getParameter("UMN")):"";
		String UserMobileNumberOTP= (req.getParameter("UOTP")!=null)?(req.getParameter("UOTP")):"";
		String ProductType= (req.getParameter("UPT")!=null)?(req.getParameter("UPT")):"";
		String ProductBrand= (req.getParameter("UPB")!=null)?(req.getParameter("UPB")):"";
		String ProductModel= (req.getParameter("UPM")!=null)?(req.getParameter("UPM")):"";
		String ProductVehicleMake= (req.getParameter("UVMK")!=null)?(req.getParameter("UVMK")):"";
		String ProductVehicleModel= (req.getParameter("UVMD")!=null)?(req.getParameter("UVMD")):"";
		String UserName= (req.getParameter("UNA")!=null)?(req.getParameter("UNA")):"";
		String UserEmail= (req.getParameter("UEM")!=null)?(req.getParameter("UEM")):"";
		String UserArea= (req.getParameter("UAR")!=null)?(req.getParameter("UAR")):"";
		String UserPincode= (req.getParameter("UPN")!=null)?(req.getParameter("UPN")):"";
		String UserState= (req.getParameter("UST")!=null)?(req.getParameter("UST")):"";
		String UserCity= (req.getParameter("UCT")!=null)?(req.getParameter("UCT")):"";
		String UserAddress= (req.getParameter("UAD")!=null)?(req.getParameter("UAD")):"";
		String UserLandmark= (req.getParameter("ULM")!=null)?(req.getParameter("ULM")):"";
		String Pre_Order_Number= (req.getParameter("ORD")!=null)?(req.getParameter("ORD")):"";
		
		String agent_name = "";
		LogLevel.DEBUG(5, new Throwable(), "UserMobileNumberOTP:" + UserMobileNumberOTP);
		//password = password.replace("\\","\\\\"); 

		String WithOldBatteryPrice;
		String WithoutOldBatteryPrice;
		String strRes="";
		String State_1="";
		String strstate="";
		String strstate1="";
		String StrSqlQry ="";
		String Strretid="";
		String Strlocorpin="";
		String strcustax="";
		String strcusper="";
		String City_Percentage="";
		String ERP_Pre_TAX="";
		String ProductActualPrice="";
		String ProductCapacity="";
		
		UserMobileNumber =UserMobileNumber.replaceAll("\\+", " ");
		UserMobileNumberOTP =UserMobileNumberOTP.replaceAll("\\+", " ");
		UserMobileNumberOTP=UserMobileNumberOTP.replaceAll("[\\t\\n\\r]","");
		ProductType =ProductType.replaceAll("\\+", " ");
		ProductBrand =ProductBrand.replaceAll("\\+", " ");
		ProductModel =ProductModel.replaceAll("\\+", " ");
		ProductVehicleMake =ProductVehicleMake.replaceAll("\\+", " ");
		ProductVehicleModel =ProductVehicleModel.replaceAll("\\+", " ");
		UserName =UserName.replaceAll("\\+", " ");
		UserEmail =UserEmail.replaceAll("\\+", " ");
		UserArea =UserArea.replaceAll("\\+", " ");
		UserPincode =UserPincode.replaceAll("\\+", " ");
		UserState =UserState.replaceAll("\\+", " ");
		UserCity =UserCity.replaceAll("\\+", " ");
		UserAddress =UserAddress.replaceAll("\\+", " ");
		UserLandmark =UserLandmark.replaceAll("\\+", " ");
		Pre_Order_Number =Pre_Order_Number.replaceAll("\\+", " ");
	
		Hashtable htretailerid =new Hashtable();
		try
		{
			UserMobileNumber =java.net.URLDecoder.decode(UserMobileNumber, "UTF-8");
			UserMobileNumberOTP =java.net.URLDecoder.decode(UserMobileNumberOTP, "UTF-8");
			ProductType =java.net.URLDecoder.decode(ProductType, "UTF-8");
			ProductBrand =java.net.URLDecoder.decode(ProductBrand, "UTF-8");
			ProductModel =java.net.URLDecoder.decode(ProductModel, "UTF-8");
			ProductVehicleMake =java.net.URLDecoder.decode(ProductVehicleMake, "UTF-8");
			ProductVehicleModel =java.net.URLDecoder.decode(ProductVehicleModel, "UTF-8");
			UserName =java.net.URLDecoder.decode(UserName, "UTF-8");
			UserEmail =java.net.URLDecoder.decode(UserEmail, "UTF-8");
			UserArea =java.net.URLDecoder.decode(UserArea, "UTF-8");
			UserPincode =java.net.URLDecoder.decode(UserPincode, "UTF-8");
			UserState =java.net.URLDecoder.decode(UserState, "UTF-8");
			UserCity =java.net.URLDecoder.decode(UserCity, "UTF-8");
			UserAddress =java.net.URLDecoder.decode(UserAddress, "UTF-8");
			UserLandmark =java.net.URLDecoder.decode(UserLandmark, "UTF-8");
			Pre_Order_Number =java.net.URLDecoder.decode(Pre_Order_Number, "UTF-8");
			
			String Get_Product_Price_SQL ="select DISTINCT a.batprice_id, a.bat_act_price, a.bat_witbat_price, a.bat_ret_price, a.bat_capacity, a.erp_pre_tax, b.city_percentage from batteryprice a, percentage b where a.bat_brand='"+ProductBrand+"' and a.bat_model='"+ProductModel+"' and a.state='"+UserState+"' and a.city='"+UserCity+"' and a.city=b.city ORDER BY batprice_id DESC LIMIT 1";
			
			LogLevel.DEBUG(5, new Throwable(), "Get_Product_Price_SQL :" + Get_Product_Price_SQL);


			Hashtable Get_Product_Price_SQL_HT = qm.getRow(Get_Product_Price_SQL);
			LogLevel.DEBUG(5, new Throwable(), "Get_Product_Price_SQL_HT :" + Get_Product_Price_SQL_HT);

			if(Get_Product_Price_SQL_HT.isEmpty())
			{
				strRes="Session Expired or Server Down. Please regenerate your order.";
				return strRes;
			}
			else{
				ProductActualPrice=(String)Get_Product_Price_SQL_HT.get("bat_act_price");
				LogLevel.DEBUG(5, new Throwable(), "ProductActualPrice :" + ProductActualPrice);
				
				WithoutOldBatteryPrice=(String)Get_Product_Price_SQL_HT.get("bat_witbat_price");
				LogLevel.DEBUG(5, new Throwable(), "WithoutOldBatteryPrice :" + WithoutOldBatteryPrice);
				
				String Battery_Ret_Price=(String)Get_Product_Price_SQL_HT.get("bat_ret_price");
				LogLevel.DEBUG(5, new Throwable(), "Battery_Ret_Price :" + Battery_Ret_Price);
				
				ProductCapacity=(String)Get_Product_Price_SQL_HT.get("bat_capacity");
				LogLevel.DEBUG(5, new Throwable(), "ProductCapacity :" + ProductCapacity);
				
				ERP_Pre_TAX =(String)Get_Product_Price_SQL_HT.get("erp_pre_tax");
				LogLevel.DEBUG(5, new Throwable(), "ERP_Pre_TAX :" + ERP_Pre_TAX);
				
				City_Percentage=(String)Get_Product_Price_SQL_HT.get("city_percentage");
				LogLevel.DEBUG(5, new Throwable(), "City_Percentage :" + City_Percentage);

				int Battery_Act_Price_int = Integer.parseInt(ProductActualPrice);
				int WithoutOldBatteryPrice_int = Integer.parseInt(WithoutOldBatteryPrice);
				int Battery_Ret_Price_int = Integer.parseInt(Battery_Ret_Price);
				int WithOldBatteryPrice_int = WithoutOldBatteryPrice_int - Battery_Ret_Price_int;
				
				WithOldBatteryPrice = Integer.toString(WithOldBatteryPrice_int);
				WithoutOldBatteryPrice = Integer.toString(WithoutOldBatteryPrice_int);
			}
			
			if(UserArea.equals("0") || UserArea.equals("") || UserArea == "")
			{
				String strstate2 = UserState;
				strstate=strstate2.trim().replaceAll(" ", "_"); 
				LogLevel.DEBUG(5, new Throwable(), "strstate :" + strstate);
				
				Date now = new Date();
				SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely
				//System.out.println(simpleDateformat.format(now));
				LogLevel.DEBUG(5, new Throwable(), "Day Info :" + simpleDateformat.format(now));
				String day =simpleDateformat.format(now);
				LogLevel.DEBUG(5, new Throwable(), "Day Info String:" + day);
			
				if(day.equals("Sunday"))
				{
					String StrSqlQry13 = "select retailer_id from retailer_location_mapping where pincode='"+UserPincode+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='Yes' limit 1";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQry13 :" + StrSqlQry13);
					Hashtable htretailerid12 = qm.getRow(StrSqlQry13); 
					if(htretailerid12.isEmpty())
					{
						StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+UserPincode+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='No' limit 1";
						LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
					}
					else
					{
						StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+UserPincode+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='Yes' limit 1";
						LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
					}
				}
				else
				{
					StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+UserPincode+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='No' limit 1";
						LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
				}

				htretailerid = qm.getRow(StrSqlQry); 
				Strretid=String.valueOf(htretailerid.get("retailer_id"));
				LogLevel.DEBUG(5, new Throwable(), "Strretid :" + Strretid);
				Strlocorpin = UserPincode;

				}
				else
				{
					String StrSqlQrystate = "select state from search_whereever_keywords where city='"+UserCity+"'";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQrystate :" + StrSqlQrystate); 

					Hashtable htgetstate = qm.getRow(StrSqlQrystate); 
					strstate1=(String)htgetstate.get("state");

					strstate=strstate1.trim().replaceAll(" ", "_"); 
					LogLevel.DEBUG(5, new Throwable(), "strstate :" + strstate);
					Date now = new Date();
					SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely
					//System.out.println(simpleDateformat.format(now));
					LogLevel.DEBUG(5, new Throwable(), "Day Info :" + simpleDateformat.format(now));
					String day =simpleDateformat.format(now);
					LogLevel.DEBUG(5, new Throwable(), "Day Info String:" + day);
				
					if(day.equals("Sunday"))
					{

						String StrSqlQry12 = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+UserCity+"' and area='"+UserArea+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='Yes' limit 1";
						LogLevel.DEBUG(5, new Throwable(), "StrSqlQry12 :" + StrSqlQry12);
						Hashtable htretailerid1 = qm.getRow(StrSqlQry12); 
						if(htretailerid1.isEmpty())
						{
							StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+UserCity+"' and area='"+UserArea+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='No' limit 1";
							LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);

						}
						else
						{

							StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+UserCity+"' and area='"+UserArea+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='Yes' limit 1";
							LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
						}
					}
					else
					{
						StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+UserCity+"' and area='"+UserArea+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='No' limit 1";
						LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
					}

					htretailerid = qm.getRow(StrSqlQry); 
					Strretid=String.valueOf(htretailerid.get("retailer_id"));
					LogLevel.DEBUG(5, new Throwable(), "Strretid :" + Strretid);
					Strlocorpin = UserCity;
				}
				if(Strretid.equals(null) || Strretid.equals("null") || Strretid == null || Strretid == "null" || Strretid =="")
				{						
					strRes="Sorry!, No Retailers Found on Selected City or Area.";  
		
				}
				else
				{
				String StrSqlQrydet = "select retailer_id,retailer_name,mobile_number,email_id,address1,mobile_numberother,invoice_flag from "+strstate+"_retailers where retailer_id='"+Strretid+"'";
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
					String New_Order_ID_SQL = "SELECT ord_id as ORDER_ID FROM battery_order_details ORDER BY ord_id DESC LIMIT 1";
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
					String verificationcode = "";
					String verificationcode1 = Integer.toString(Num);
					verificationcode ="ORDB"+Last_Order_Count_String+""+verificationcode1+"B";
					LogLevel.DEBUG(5, new Throwable(), "verificationcode :" + verificationcode);
					
				
					if(Pre_Order_Number.equals("Failed"))
					{
						verificationcode=verificationcode;
					}
					else
					{
						verificationcode=Pre_Order_Number;
					}
					
					/*following code is for generating the random number */
					String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";

					String pw = "";
					for (int i=0; i<PASSWORD_LENGTH; i++)
					{
						int index = (int)(RANDOM.nextDouble()*letters.length());
						pw += letters.substring(index, index+1);
					}
					LogLevel.DEBUG(5, new Throwable(), "pw :" + pw);
					String ThankUSMSMsg = "";
					String ThankUMsg="";
					/*code to generate the random number ends here */
					/* code to send SMS and Email retailers details to consumer */ 
					CompareTrans ct = new CompareTrans(qm);
					SendSMS sendsms = new SendSMS(qm);

					if(ProductBrand.equals("Amaron"))
					{
						ThankUMsg="Your BookBattery Battery Ord Ref No: "+verificationcode+". Battery Model: "+ProductModel+". Price: "+WithoutOldBatteryPrice+" OBRP Price: Rs. "+WithOldBatteryPrice+". "+strretname+" with Mob No-"+strretmobnum+" and address "+straddress1+" will fullfill your order.For any kind of Assistance please call to +919603467559.";
						
					}
					else
					{
						ThankUMsg="Your BookBattery Battery Ord Ref No: "+verificationcode+". Battery Model: "+ProductModel+". Price: "+WithoutOldBatteryPrice+" OBRP Price: Rs. "+WithOldBatteryPrice+". "+strretname+" with Mob No-"+strretmobnum+" and address "+straddress1+" will fullfill your order.For any kind of Assistance please call to +919603467559.";

					}

				if(ProductType.equals("Bike Batteries"))
				  {
						ThankUSMSMsg ="Thanks for choosing bookbattery.com .Your Battery Ord Ref No: "+verificationcode+" for Battery Model: "+ProductModel+" Price: Rs. "+WithoutOldBatteryPrice+" OBRP Price: Rs. "+WithOldBatteryPrice+" Please pick up the bike battery at the store.For any kind of Assistance please call to 9603467559. ";
				  }
				  else
				  {
						ThankUSMSMsg ="Thanks for choosing bookbattery.com .Your Battery Ord Ref No: "+verificationcode+" for Battery Model: "+ProductModel+" Price: Rs. "+WithoutOldBatteryPrice+" OBRP Price: Rs. "+WithOldBatteryPrice+" You will shortly receive a call.For any kind of Assistance please call to 9603467559. ";
				  }
				String strSMSResponse=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUSMSMsg, UserMobileNumber);
				LogLevel.DEBUG(5, new Throwable(), "strSMSResponse :" + strSMSResponse);
				String strsub="Dear "+UserName+",\r\n\r\n"+""+ThankUMsg+"";
				String strThanks="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
				MailTrans sendverifycode=new MailTrans();
				sendverifycode.setStrSmtpHost(strdomainname);
				sendverifycode.setStrFrom(FromEmailId);
				sendverifycode.setStrTo(UserEmail);
				sendverifycode.setStrSubject("BookBattery Battery Details.");
				String activateLink = strsub+"\r\n\r\n"+strThanks+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
				LogLevel.DEBUG(5, new Throwable(), "activateLink :" + activateLink);
				sendverifycode.setStrText(activateLink);
				Thread mt=new MailThread(sendverifycode,"");
				mt.start();

				/* code to send SMS and Email consumer details to retailers */ 
				SendSMS sendsms1 = new SendSMS(qm);
				//String ThankUMsg1="Asistmi Battery Consumer details is "+UserName+",mob-"+UserMobileNumber+". Ordered Ref N0: "+verificationcode+". Model: "+ProductModel+". Price: "+WithoutOldBatteryPrice+".";
				String ThankUMsg1="";
				String activateLink1 = "";
				String strThanks1="";
				String CustomerAddress="";
				
				if(UserAddress.equals(""))
				{
					CustomerAddress="";
				}
				else
				{
					if(UserLandmark.equals(""))
					{
						UserLandmark="N/A";
					}
					CustomerAddress=", Address: "+UserAddress+",LandMark: "+UserLandmark+"";
				}
				
				if(ProductBrand.equals("Amaron") && strretname.contains("Amaron-Pitstop"))
				{
					ThankUMsg1="BookBattery Consumer "+UserName+" with Mob no-"+UserMobileNumber+" "+CustomerAddress+" and Order Ref Number "+verificationcode+" ordered for "+ProductBrand+" >> "+ProductModel+" at price: Rs."+WithoutOldBatteryPrice+" OBRP Price: Rs. "+WithOldBatteryPrice+". Please re-confirm with the customer and deliver it. For any kind of Assistance please call to 9603467559.";
				}
				else
				{
					ThankUMsg1="BookBattery Consumer "+UserName+" with Mob no-"+UserMobileNumber+" "+CustomerAddress+" and Order Ref Number "+verificationcode+" ordered for "+ProductBrand+" >> "+ProductModel+" at price: Rs."+WithoutOldBatteryPrice+" OBRP Price: Rs. "+WithOldBatteryPrice+". Please re-confirm with the customer and deliver it. For any kind of Assistance please call to 9603467559.";
				}
				//String ThanksmsMsg=""+UserName+" Mob No: "+UserMobileNumber+" and Ord Ref No: "+verificationcode+" ordered "+ProductBrand+" >> "+ProductModel+" at price: Rs."+WithoutOldBatteryPrice+", OBRP Price : Rs."+WithOldBatteryPrice+" . Please install.";
				String ThanksmsMsg=""+UserName+" Mob No: "+UserMobileNumber+" and Ord Ref No: "+verificationcode+" ordered "+ProductBrand+" >> "+ProductModel+" at Price: Rs. "+WithoutOldBatteryPrice+" OBRP Price: Rs. "+WithOldBatteryPrice+" Please install";

				String strSMSResponse1=  sendsms1.sendSMS(strIpAddress,strPort,SMSFromAddress,ThanksmsMsg, strretmobnum);
				LogLevel.DEBUG(5, new Throwable(), "strSMSResponse1 :" + strSMSResponse1);
				String strsub1="Dear "+strretname+",\r\n\r\n"+""+ThankUMsg1+"";
				MailTrans retdetails=new MailTrans();
				retdetails.setStrSmtpHost(strdomainname);
				retdetails.setStrFrom(FromEmailId);
				retdetails.setStrTo(strretemail);
				if(ProductBrand.equals("Amaron") && strretname.contains("Amaron-Pitstop"))
				{
					strThanks1="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
					retdetails.setStrSubject("BookBattery Battery Details.");
					activateLink1 = strsub1+"\r\n\r\n"+strThanks1+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "activateLink1 :" + activateLink1);
				}
				else
				{
					strThanks1="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
					retdetails.setStrSubject("BookBattery Battery Details.");
					activateLink1 = strsub1+"\r\n\r\n"+strThanks1+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "activateLink1 :" + activateLink1);

				}
				
				retdetails.setStrText(activateLink1);
				Thread mt1=new MailThread(retdetails,"");
				mt1.start();

				String[] tempArr=strretothermobnum.split(",");
				LogLevel.DEBUG(5,new Throwable(),"tempArr.length :"+tempArr.length );
				LogLevel.DEBUG(5,new Throwable(),"tempArr:"+tempArr);
					if(tempArr.length>0)
					  {
						String strTempValue="";
						for (int k=0;k<tempArr.length ;k++ )
							{	
							strTempValue=tempArr[k];
							LogLevel.DEBUG(5,new Throwable(),"tempArr:"+strTempValue);
							String strSMSResponse11=  sendsms1.sendSMS(strIpAddress,strPort,SMSFromAddress,ThanksmsMsg, strTempValue);
							LogLevel.DEBUG(5, new Throwable(), "strSMSResponse11 :" + strSMSResponse11);

							}
					  }
				
				/* code to send SMS and Email consumer details to Operators */ 
				//String ThanksuppsmsMsg="Battery Ord Ref No: "+verificationcode+" ,Cust Mob No: "+UserMobileNumber+" ,Franchisee Mob No: "+strretmobnum+" ,Location : "+Strlocorpin+" .";
				String ThanksuppsmsMsg="Battery Ord Ref No: "+verificationcode+" Cust Mob No: "+UserMobileNumber+" Franchisee Mob No: "+strretmobnum+" Location : "+Strlocorpin+""; 
				String strSMSResponse22 =  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThanksuppsmsMsg, strsuppmobnumber);
				LogLevel.DEBUG(5, new Throwable(), "strSMSResponse22 :" + strSMSResponse22);
				String ThankUMsgopt="BookBattery Consumer "+UserName+" with Mob no-"+UserMobileNumber+", EmailID : "+UserEmail+"   ordered for "+ProductBrand+" >> "+ProductModel+" at price: Rs."+WithoutOldBatteryPrice+" OBRP Price: Rs. "+WithOldBatteryPrice+" for  "+ProductType+" >> "+ProductVehicleMake+" >> "+ProductVehicleModel+", Order Ref Number "+verificationcode+" Franchisee Name: "+strretname+" Franchisee Mob No: "+strretmobnum+" Franchisee EmailID: "+strretemail+" Location : "+Strlocorpin+"";

				String strsubop="Dear operator,\r\n\r\n"+""+ThankUMsgopt+"";
				String strThanksop="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
				MailTrans operator=new MailTrans();
				operator.setStrSmtpHost(strdomainname);
				operator.setStrFrom(FromEmailId);
				operator.setStrTo(strsuppemaild);
				operator.setStrSubject("BookBattery Battery Ordered Details.");
				String activateLinkop = strsubop+"\r\n\r\n"+strThanksop+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
				LogLevel.DEBUG(5, new Throwable(), "activateLinkop :" + activateLinkop);
				operator.setStrText(activateLinkop);
				Thread mtopt=new MailThread(operator,"");
				mtopt.start();

				Calendar cal = Calendar.getInstance();
				int currentMonth = cal.get(Calendar.MONTH) + 1;
				int currentYear = cal.get(Calendar.YEAR);

				String strPdfURL="";
				
				strRes="Sucessfully | "+verificationcode+"";
				
				if(strinvoiceflag.equals("yes") || strinvoiceflag == "yes")
				{
					String strSqlQryloctax ="select city_percentage,city_local_percentage  from customer_percentage where city='"+UserCity+"'";
					LogLevel.DEBUG(5, new Throwable(), "strSqlQryloctax :" + strSqlQryloctax);

					Hashtable htgetloctax = qm.getRow(strSqlQryloctax); 
					strcusper=String.valueOf(htgetloctax.get("city_percentage"));
					strcustax=String.valueOf(htgetloctax.get("city_local_percentage"));
					LogLevel.DEBUG(5,new Throwable(),"mallidipd:"+strcustax );

					String Strlocalstring ="";
					String Strvatstring="";

					String StrQryPrice = "select CAST(round("+WithoutOldBatteryPrice+"-("+WithoutOldBatteryPrice+" * ("+strcusper+" + "+strcustax+"))) AS SIGNED) as batteryprice";
					LogLevel.DEBUG(5, new Throwable(), "StrQryPrice :" + StrQryPrice);

					Hashtable htprice = qm.getRow(StrQryPrice); 
					String Strbatteryprice=String.valueOf(htprice.get("batteryprice"));
					LogLevel.DEBUG(5, new Throwable(), "Strbatteryprice :" + Strbatteryprice);

					String StrQryvatPrice = "select CAST(round("+WithoutOldBatteryPrice+" * "+strcusper+") AS SIGNED) as battervatyprice";
					LogLevel.DEBUG(5, new Throwable(), "StrQryvatPrice :" + StrQryvatPrice);

					Hashtable htvatprice = qm.getRow(StrQryvatPrice); 
					String Strbatteryvatprice=String.valueOf(htvatprice.get("battervatyprice"));
				
					if(UserCity.equals("Mumbai") || UserCity == "Mumbai")
					{
					Strvatstring = "12.5% VAT";
					}
					else if(UserCity.equals("Pune") || UserCity == "Pune")
					{
					Strvatstring = "12.5% VAT";
					}
					else
					{
					Strvatstring = "14.5% VAT";
					}
					LogLevel.DEBUG(5, new Throwable(), "Strbatteryvatprice :" + Strbatteryvatprice);

					String StrQrylocaltax = "select CAST(round("+WithoutOldBatteryPrice+" * "+strcustax+") AS SIGNED) as localtax";
					LogLevel.DEBUG(5, new Throwable(), "StrQrylocaltax :" + StrQrylocaltax);

					Hashtable htvatlocal = qm.getRow(StrQrylocaltax); 
					String Strbatterylocalprice=String.valueOf(htvatlocal.get("localtax"));
					if(UserCity.equals("Mumbai") || UserCity == "Mumbai")
					{
					Strlocalstring = "5.5% Locala";
					}
					else if(UserCity.equals("Pune") || UserCity == "Pune")
					{
					Strlocalstring = "3.5% Octroi";
					}

					LogLevel.DEBUG(5, new Throwable(), "Strbatterylocalprice :" + Strbatterylocalprice);

					String verifiycode ="ASSIST-AMR-BT"+verificationcode1+"";
					LogLevel.DEBUG(5, new Throwable(), "verificationcode :" + verificationcode);

					//String excelName = "invoice.pdf";
					String excelName = "ASPL_"+UserName+"_"+verifiycode+"_Invoice.pdf";
					String invoice ="ASSIST-AMR-BT"+verificationcode1+"";
					
					strPDFFilePath = strPDFFilePath +"/"+ currentYear + "/" +currentMonth;
					File excelUploadPathCreate = new File(strPDFFilePath);

				if(excelUploadPathCreate.mkdirs())
				{
					LogLevel.DEBUG(3,new Throwable()," PDF for invoice	Name directory created :  "	+ excelUploadPathCreate);
				}
				
				strPDFRelFilePath	= strPDFRelFilePath +"/"+ currentYear + "/" +currentMonth;

				String excelFile = excelUploadPathCreate+"/"+excelName;
				File file=new File(excelFile); 
				if(!file.exists()) 
					file.createNewFile(); 
				FileWriter fw=new FileWriter(file);	
				GenerateExcelinvoice generateExcel	= new GenerateExcelinvoice();
				generateExcel.writeToPdf(UserName,UserCity,verificationcode,invoice,ProductBrand,ProductModel,WithOldBatteryPrice,Strbatteryprice,Strbatteryvatprice,Strvatstring,Strbatterylocalprice,Strlocalstring,excelFile,strCMSSERVERIP);

				final String username = "admin@ngit.in";
				final String password = "adminNGIT";
				String subject="BookBattery Customer Invoice for "+UserName+""; 
			
				Properties props = new Properties();
				props.put("mail.smtp.auth", true);
				props.put("mail.smtp.starttls.enable", true);
				props.put("mail.smtp.host", "smtp.net4india.com");
				props.put("mail.smtp.port", "587");

				Session session1 = Session.getInstance(props,
				new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
							}
						});

				 Message msg = new MimeMessage(session1);  
				
				  msg.setFrom(new InternetAddress(FromEmailId));  
			   // create the message part   
				  MimeBodyPart messageBodyPart = new MimeBodyPart();  
			  //fill message  
				  messageBodyPart.setText("Dear "+strretname+",\r\n\r\nThis is an automated invoice generated by www.bookbattery.com  for the battery ordered by "+UserName+". Please find the attachment for invoice details. \r\n\r\n\r\n Thanks & Regards,\r\n BookBattery Team.\r\n www.BookBattery.com.");  
				  Multipart multipart = new MimeMultipart();  
				  multipart.addBodyPart(messageBodyPart);  
			   // Part two is attachment  
				  messageBodyPart = new MimeBodyPart();  
				  DataSource source =   
					new FileDataSource(file);  
				  messageBodyPart.setDataHandler(  
					new DataHandler(source));  
				 // messageBodyPart.setFileName(file); 
				  messageBodyPart.setFileName(""+UserName+"_invoice.pdf");
				  multipart.addBodyPart(messageBodyPart);  
			  // Put parts in message  
				  msg.setContent(multipart);  
			   
	  
				  //msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(SMTP_TO_ADDRESS));  
				  msg.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(strretemail));
	  
				  msg.setSubject(subject); 
				  Transport.send(msg);  

				LogLevel.DEBUG(5,new Throwable(),"trans :"+ msg );

				strPdfURL="http://"+strCMSSERVERIP+""+baseurldirectory+"userdata/billing/consumerinvoices/"+currentYear+"/"+currentMonth+"/"+excelName;
				LogLevel.DEBUG(5, new Throwable(), "strPdfURL : " + strPdfURL);
			}
				
			//######################################## Operator Code	####################
			String Get_Previous_Operator_SQL = "select agent_name from battery_order_details where consumer_mobnumber='"+UserMobileNumber+"' and (NOT agent_name = '0' or agent_name = ' ' ) and YEAR(creation_date) = YEAR(NOW()) AND MONTH(creation_date) = MONTH(NOW()) AND DAY(creation_date) = DAY(NOW()) order by creation_date limit 1";
			LogLevel.DEBUG(5, new Throwable(), "Get_Previous_Operator_SQL :" + Get_Previous_Operator_SQL);

			Hashtable Get_Previous_Operator_HT = qm.getRow(Get_Previous_Operator_SQL);
			LogLevel.DEBUG(5, new Throwable(), "Get_Previous_Operator_HT :" + Get_Previous_Operator_HT);

			if(Get_Previous_Operator_HT.isEmpty())
			{
				String Operator_Name_Last_Assigned_SQL = "select agent_name from battery_order_details where (NOT agent_name = '0' or agent_name = ' ' ) order by creation_date desc limit 1";
				LogLevel.DEBUG(5, new Throwable(), "Operator_Name_Last_Assigned_SQL :" + Operator_Name_Last_Assigned_SQL);

				Hashtable Operator_Name_Last_Assigned_HT = qm.getRow(Operator_Name_Last_Assigned_SQL); 
				agent_name=(String)Operator_Name_Last_Assigned_HT.get("agent_name");
				
				String AssignToAgent="";
						
				int operatorteamcountint = Integer.parseInt(OperatorTeamCount);

				 for(int i=1; i<operatorteamcountint; i++)
				{
					LogLevel.DEBUG(5, new Throwable(), "i value:" +i);						

					if(agent_name.equals("operator"+i+""))
					{
						int jk = new Integer(i+ 1);

						if(jk == operatorteamcountint)
						{
							AssignToAgent="operator1";
							LogLevel.DEBUG(5, new Throwable(), "AssignToAgent:" +AssignToAgent);
							break;
						}
						else
						{
							AssignToAgent="operator"+jk;
							LogLevel.DEBUG(5, new Throwable(), "AssignToAgent:" +AssignToAgent);
							break;
						}
					}
				}
				
				if(AssignToAgent.equals(""))
				{
					agent_name="operator1";
				}
				else
				{
					agent_name=AssignToAgent;
				}
			}
			else
			{
				agent_name=(String)Get_Previous_Operator_HT.get("agent_name");
				LogLevel.DEBUG(5, new Throwable(), "agent_name :" + agent_name);
				// This condition is to assign operator in Worst case of Software failure.  
				if(agent_name.equals(""))
				{
					agent_name="operator1";
				}
				else
				{
					agent_name=agent_name;
				}
			}
			//######################################## Operator Code	####################
	

			String strSqlQry="";
			String User_Address_Landmark=UserAddress+"\r\n Landmark : "+UserLandmark;
			User_Address_Landmark = User_Address_Landmark.replace("'","\\'");
			LogLevel.DEBUG(5,new Throwable()," User_Address_Landmark "+User_Address_Landmark);
			
			if(Pre_Order_Number.equals("Failed"))
			{
				//############### Back Up condition 
				//Worst Software failure. This Condition works when Insert Before order Fails
				//############### By Sai Krishna Daddala
				
				strSqlQry = "insert into battery_order_details(ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_verif_code,retailer_name,retailer_mobilnumber,retailer_emailis,state,city,battery_brand,battery_model,battery_capacity,price,area,pincode,pdfurl,bat_type,veh_name,veh_model,witholdbatprice,creation_date,agent_name,order_status,erp_pre_tax,MRP_Price,city_percentage,consumer_address,confirm_by)values(NULL,'"+verificationcode+"','"+UserName+"','"+UserMobileNumber+"','"+UserEmail+"','"+UserMobileNumberOTP+"','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+UserState+"','"+UserCity+"','"+ProductBrand+"','"+ProductModel+"','"+ProductCapacity+"','"+WithoutOldBatteryPrice+"','"+UserArea+"','"+UserPincode+"','"+strPdfURL+"','"+ProductType+"','"+ProductVehicleMake+"','"+ProductVehicleModel+"','"+WithOldBatteryPrice+"',now(),'"+agent_name+"','confirmed','"+ERP_Pre_TAX+"','"+ProductActualPrice+"','"+City_Percentage+"','"+User_Address_Landmark+"','Customer')";
					
				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);	
			}
			else
			{
				strSqlQry = "UPDATE battery_order_details SET order_number = '"+Pre_Order_Number+"', consumer_name = '"+UserName+"', consumer_mobnumber = '"+UserMobileNumber+"', consumer_emailid = '"+UserEmail+"', consumer_verif_code='"+UserMobileNumberOTP+"', retailer_name = '"+strretname+"', retailer_mobilnumber = '"+strretmobnum+"', retailer_emailis = '"+strretemail+"', city = '"+UserCity+"', battery_brand = '"+ProductBrand+"', battery_model = '"+ProductModel+"', battery_capacity = '"+ProductCapacity+"', price = '"+WithoutOldBatteryPrice+"', area = '"+UserArea+"', pincode = '"+UserPincode+"',pdfurl = '"+strPdfURL+"',bat_type = '"+ProductType+"',veh_name = '"+ProductVehicleMake+"', veh_model = '"+ProductVehicleModel+"', witholdbatprice = '"+WithOldBatteryPrice+"', creation_date = now(), order_status='confirmed', consumer_address='"+User_Address_Landmark+"',confirm_by='Customer' WHERE order_number = '"+Pre_Order_Number+"'";
			}

			LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
			int reslt = qm.executeUpdate(strSqlQry);
				

				String strSqlQryleads ="insert into leads(lead_id,retailer_name,retailer_id,deal_id,email_id,vendor_id,product_id,product_name,mobile_number,vendor_name,promotion_code,price,subcategory_id,category_id,creation_date,created_by,updated_date,updated_by)values(NULL,'"+strretname+"','"+Strretid+"','0','"+UserEmail+"','22','0','undefined','"+UserMobileNumber+"','"+ProductBrand+"','0','0','0','0',now(),'ngit',now(),'ngit')"; 
				LogLevel.DEBUG(5,new Throwable()," :strSqlQryleads "+strSqlQryleads);
				int resltleads = qm.executeUpdate(strSqlQryleads);

				String StrSqlQryuser = "select email_id from batterywale_user_profile where email_id='"+UserEmail+"'";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQryuser :" + StrSqlQryuser);

				Hashtable htruser = qm.getRow(StrSqlQryuser); 
				String Stremailid=String.valueOf(htruser.get("email_id"));
				LogLevel.DEBUG(5, new Throwable(), "Stremailid :" + Stremailid);

				if(Stremailid.equals(null) || Stremailid.equals("null") || Stremailid == null || Stremailid == "null" || Stremailid =="")
				{
				UserAddress = UserAddress.replace("'","\\'");
				UserLandmark = UserLandmark.replace("'","\\'");

					//String strSqlQryuserprof = "insert into user_profile(user_id,email_id,mobile_number,password,first_name,city,state,status,creation_date,created_by)values(NULL,'"+UserEmail+"','"+UserMobileNumber+"','"+password+"','"+UserName+"','"+city+"','"+State_1+"','active',now(),'ngit')";
					String strSqlQryuserprof = "insert into batterywale_user_profile(user_id,email_id,mobile_number,password,name,address,address1,zipcode,city,state,mobile_verify_code,creation_date,created_by) values(NULL,'"+UserEmail+"','"+UserMobileNumber+"','"+pw+"','"+UserName+"','"+UserAddress+"','"+UserLandmark+"','"+UserPincode+"','"+UserCity+"','"+UserState+"','"+UserMobileNumberOTP+"',now(),'ngit')";

					LogLevel.DEBUG(5,new Throwable()," : "+strSqlQryuserprof);
					int reslt1 = qm.executeUpdate(strSqlQryuserprof);
				}
				else
				{
					String strSqlQryupdatepassword = "update batterywale_user_profile set password='"+pw+"' where email_id='"+UserEmail+"'";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQryupdatepassword : "+strSqlQryupdatepassword);
					int reslt12 = qm.executeUpdate(strSqlQryupdatepassword);
				}
				
				StringBuilder result = new StringBuilder();
				URL url = new URL(""+baseUrl+"/bookbattery/servlet/UserLoginServlet?hidWhatToDo=loginwithanotherserver&email="+UserEmail+"&mobilenumber="+UserMobileNumber+"&password="+pw+"&username="+UserName+"&city="+UserCity+"&state="+UserState+"&deviceflag=web&producttype=battery&vehiclename="+ProductVehicleMake+"&vehiclemodel="+ProductVehicleModel+"&productbrand="+ProductBrand+"&productmodel="+ProductModel+"&withoutoldproductprice="+WithoutOldBatteryPrice+"&witholdproductprice="+WithOldBatteryPrice+"&quantity=1&retailername="+strretname+"&retailermobileno="+strretmobnum+"");
				LogLevel.DEBUG(5, new Throwable(), "result.toString() :" + url);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("POST");
				BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line;
				while ((line = rd.readLine()) != null) {
				 result.append(line);
				}
				rd.close();
				   
				LogLevel.DEBUG(5, new Throwable(), "result.toString() :" + result.toString());

			}
			}
		}
		catch (Exception e)
		{
		LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
		strRes=strRes;
		}
		return strRes;
	}
		
	/* *************************************************************************************************************\
	* This method is used to insert Consumer Inverter Order Details in DB
	\* *************************************************************************************************************/
	public String InsertOrderDetails_Inverter(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort,String SMSFromAddress, String FromEmailId, String strsuppemaild, String baseUrl, String strsuppmobnumber,String strCMSSERVERIP,String strPDFFilePath,String strPDFRelFilePath,String OperatorTeamCount) 
	{ 	
	
		String UserMobileNumber= (req.getParameter("UMN")!=null)?(req.getParameter("UMN")):"";
		String UserMobileNumberOTP= (req.getParameter("UOTP")!=null)?(req.getParameter("UOTP")):"";
		String ProductType= (req.getParameter("UPT")!=null)?(req.getParameter("UPT")):"";
		String ProductBrand= (req.getParameter("UPB")!=null)?(req.getParameter("UPB")):"";
		String ProductModel= (req.getParameter("UPM")!=null)?(req.getParameter("UPM")):"";
		String UserName= (req.getParameter("UNA")!=null)?(req.getParameter("UNA")):"";
		String UserEmail= (req.getParameter("UEM")!=null)?(req.getParameter("UEM")):"";
		String UserArea= (req.getParameter("UAR")!=null)?(req.getParameter("UAR")):"";
		String UserPincode= (req.getParameter("UPN")!=null)?(req.getParameter("UPN")):"";
		String UserState= (req.getParameter("UST")!=null)?(req.getParameter("UST")):"";
		String UserCity= (req.getParameter("UCT")!=null)?(req.getParameter("UCT")):"";
		String UserAddress= (req.getParameter("UAD")!=null)?(req.getParameter("UAD")):"";
		String UserLandmark= (req.getParameter("ULM")!=null)?(req.getParameter("ULM")):"";
		String Pre_Order_Number= (req.getParameter("ORD")!=null)?(req.getParameter("ORD")):"";
		
		String agent_name = "";

		String ProductCapacity="";
		String ProductActualPrice="";
		String ProductDiscountPrice="";

		String strRes="";
		String strstate="";
		String strstate1="";
		String StrSqlQry ="";
		String Strretid="";
		String Strlocorpin="";
		String strcustax="";
		String strcusper="";
		String City_Percentage="";
		String ERP_Pre_TAX="";
		
		UserMobileNumber =UserMobileNumber.replaceAll("\\+", " ");
		UserMobileNumberOTP =UserMobileNumberOTP.replaceAll("\\+", " ");
		UserMobileNumberOTP=UserMobileNumberOTP.replaceAll("[\\t\\n\\r]","");
		ProductType =ProductType.replaceAll("\\+", " ");
		ProductBrand =ProductBrand.replaceAll("\\+", " ");
		ProductModel =ProductModel.replaceAll("\\+", " ");
		UserName =UserName.replaceAll("\\+", " ");
		UserEmail =UserEmail.replaceAll("\\+", " ");
		UserArea =UserArea.replaceAll("\\+", " ");
		UserPincode =UserPincode.replaceAll("\\+", " ");
		UserState =UserState.replaceAll("\\+", " ");
		UserCity =UserCity.replaceAll("\\+", " ");
		UserAddress =UserAddress.replaceAll("\\+", " ");
		UserLandmark =UserLandmark.replaceAll("\\+", " ");
		Pre_Order_Number =Pre_Order_Number.replaceAll("\\+", " ");
		
		Hashtable htretailerid =new Hashtable();
		try
		{		
			UserMobileNumber =java.net.URLDecoder.decode(UserMobileNumber, "UTF-8");
			UserMobileNumberOTP =java.net.URLDecoder.decode(UserMobileNumberOTP, "UTF-8");
			ProductType =java.net.URLDecoder.decode(ProductType, "UTF-8");
			ProductBrand =java.net.URLDecoder.decode(ProductBrand, "UTF-8");
			ProductModel =java.net.URLDecoder.decode(ProductModel, "UTF-8");
			UserName =java.net.URLDecoder.decode(UserName, "UTF-8");
			UserEmail =java.net.URLDecoder.decode(UserEmail, "UTF-8");
			UserArea =java.net.URLDecoder.decode(UserArea, "UTF-8");
			UserPincode =java.net.URLDecoder.decode(UserPincode, "UTF-8");
			UserState =java.net.URLDecoder.decode(UserState, "UTF-8");
			UserCity =java.net.URLDecoder.decode(UserCity, "UTF-8");
			UserAddress =java.net.URLDecoder.decode(UserAddress, "UTF-8");
			UserLandmark =java.net.URLDecoder.decode(UserLandmark, "UTF-8");
			Pre_Order_Number =java.net.URLDecoder.decode(Pre_Order_Number, "UTF-8");
		
			ServletOutputStream out=res.getOutputStream();
			
			
			String Get_Product_Price_SQL ="select DISTINCT a.inverter_actual_price, a.inverter_discount_price, a.inverter_capacity, a.inverter_eretailer_price, b.inverter_city_percentage from inverter_price_details a, percentage b where a.state='"+UserState+"' and a.city='"+UserCity+"' and a.inverter_brand='"+ProductBrand+"' and a.inverter_model='"+ProductModel+"' and a.city=b.city "; 
				
			LogLevel.DEBUG(5, new Throwable(), "Get_Product_Price_SQL :" + Get_Product_Price_SQL);

			Hashtable Get_Product_Price_SQL_HT = qm.getRow(Get_Product_Price_SQL);
			LogLevel.DEBUG(5, new Throwable(), "Get_Product_Price_SQL_HT :" + Get_Product_Price_SQL_HT);

			if(Get_Product_Price_SQL_HT.isEmpty())
			{
				strRes="Session Expired or Server Down. Please regenerate your order.";
				return strRes;
			}
			else{
				int ProductActualPrice_int=(Integer)Get_Product_Price_SQL_HT.get("inverter_actual_price");
				LogLevel.DEBUG(5, new Throwable(), "ProductActualPrice_int :" + ProductActualPrice_int);
				
				int ProductDiscountPrice_int=(Integer)Get_Product_Price_SQL_HT.get("inverter_discount_price");
				LogLevel.DEBUG(5, new Throwable(), "ProductDiscountPrice_int :" + ProductDiscountPrice_int);
				
				int ProductERP_Pre_TAX_int=(Integer)Get_Product_Price_SQL_HT.get("inverter_eretailer_price");
				LogLevel.DEBUG(5, new Throwable(), "ProductERP_Pre_TAX_int :" + ProductERP_Pre_TAX_int);
				
				ProductActualPrice = Integer.toString(ProductActualPrice_int);
				ProductDiscountPrice = Integer.toString(ProductDiscountPrice_int);
				ERP_Pre_TAX = Integer.toString(ProductERP_Pre_TAX_int);
				
				ProductCapacity=(String)Get_Product_Price_SQL_HT.get("inverter_capacity");
				LogLevel.DEBUG(5, new Throwable(), "ProductCapacity :" + ProductCapacity);
				
				City_Percentage=(String)Get_Product_Price_SQL_HT.get("inverter_city_percentage");
				LogLevel.DEBUG(5, new Throwable(), "City_Percentage :" + City_Percentage);

			}

			if(UserArea.equals("0") || UserArea.equals("") || UserArea == "")
			{
				String strstate2 = UserState;
				strstate=strstate2.trim().replaceAll(" ", "_"); 
				LogLevel.DEBUG(5, new Throwable(), "strstate :" + strstate);

				Date now = new Date();
				SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely
				//System.out.println(simpleDateformat.format(now));
				LogLevel.DEBUG(5, new Throwable(), "Day Info :" + simpleDateformat.format(now));
				String day =simpleDateformat.format(now);
				LogLevel.DEBUG(5, new Throwable(), "Day Info String:" + day);
			
			if(day.equals("Sunday"))
			{
				String StrSqlQry13 = "select retailer_id from retailer_location_mapping where pincode='"+UserPincode+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='Yes' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry13 :" + StrSqlQry13);
				Hashtable htretailerid12 = qm.getRow(StrSqlQry13); 
			if(htretailerid12.isEmpty())
			{
				StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+UserPincode+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='No' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);

			}
			else
				{
					StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+UserPincode+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='Yes' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);

				}
			}
			else
			{
				StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+UserPincode+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='No' limit 1";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
			}

				htretailerid = qm.getRow(StrSqlQry); 
				Strretid=String.valueOf(htretailerid.get("retailer_id"));
				LogLevel.DEBUG(5, new Throwable(), "Strretid :" + Strretid);
				Strlocorpin = UserPincode;

			}
			else
			{
				String StrSqlQrystate = "select state from search_whereever_keywords where city='"+UserCity+"'";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQrystate :" + StrSqlQrystate); 

				Hashtable htgetstate = qm.getRow(StrSqlQrystate); 
				strstate1=(String)htgetstate.get("state");

				strstate=strstate1.trim().replaceAll(" ", "_"); 
				LogLevel.DEBUG(5, new Throwable(), "strstate :" + strstate);
				
				Date now = new Date();
			SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely
			//System.out.println(simpleDateformat.format(now));
			LogLevel.DEBUG(5, new Throwable(), "Day Info :" + simpleDateformat.format(now));
			String day =simpleDateformat.format(now);
			LogLevel.DEBUG(5, new Throwable(), "Day Info String:" + day);
			
			if(day.equals("Sunday"))
			{

				String StrSqlQry12 = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+UserCity+"' and area='"+UserArea+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='Yes' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry12 :" + StrSqlQry12);
				Hashtable htretailerid1 = qm.getRow(StrSqlQry12); 
				if(htretailerid1.isEmpty())
				{
					StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+UserCity+"' and area='"+UserArea+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='No' limit 1";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);

				}
				else
				{

					StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+UserCity+"' and area='"+UserArea+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='Yes' limit 1";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
				}
			}
			else
			{
				StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+UserCity+"' and area='"+UserArea+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='No' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
			}

				htretailerid = qm.getRow(StrSqlQry); 
				Strretid=String.valueOf(htretailerid.get("retailer_id"));
				LogLevel.DEBUG(5, new Throwable(), "Strretid :" + Strretid);
				Strlocorpin = UserCity;
			}
			LogLevel.DEBUG(5, new Throwable(), "strstate :" + strstate);
			if(Strretid.equals(null) || Strretid.equals("null") || Strretid == null || Strretid == "null" || Strretid =="")
			{
				strRes="Sorry!, No Retailers Found on Selected City or Area.";  
			}
			else
			{
			String StrSqlQrydet = "select retailer_name,mobile_number,email_id,address1,mobile_numberother,invoice_flag from "+strstate+"_retailers where retailer_id='"+Strretid+"'";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQrydet :" + StrSqlQrydet);

			Hashtable htretailerdetls = qm.getRow(StrSqlQrydet); 
			String strretmobnum=String.valueOf(htretailerdetls.get("mobile_number"));
			String strretname=(String)htretailerdetls.get("retailer_name");
			String strretemail=(String)htretailerdetls.get("email_id");
			String straddress1=(String)htretailerdetls.get("address1");
			String strretothermobnum=String.valueOf(htretailerdetls.get("mobile_numberother"));
			String strinvoiceflag=String.valueOf(htretailerdetls.get("invoice_flag"));


			//######################################## Order Number Code	####################
			String New_Order_ID_SQL = "SELECT order_id as ORDER_ID FROM inverter_order_details ORDER BY order_id DESC LIMIT 1";
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
			String verificationcode = "";
			String verificationcode1 = Integer.toString(Num);
			verificationcode ="ORDI"+Last_Order_Count_String+""+verificationcode1+"B";
			LogLevel.DEBUG(5, new Throwable(), "verificationcode :" + verificationcode);
			
			
			if(Pre_Order_Number.equals("Failed"))
			{
				verificationcode=verificationcode;
			}
			else
			{
				verificationcode=Pre_Order_Number;
			}
			
			/*following code is for generating the random number */
			String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";

			String pw = "";
				for (int i=0; i<PASSWORD_LENGTH; i++)
				{
				int index = (int)(RANDOM.nextDouble()*letters.length());
				pw += letters.substring(index, index+1);
				}
			LogLevel.DEBUG(5, new Throwable(), "pw :" + pw);
			String ThankUSMSMsg = "";
			String ThankUMsg="";
			/*code to generate the random number ends here */
			/* code to send SMS and Email retailers details to consumer */ 
			CompareTrans ct = new CompareTrans(qm);
			SendSMS sendsms = new SendSMS(qm);


			ThankUMsg="Your BookBattery Inverter Ord Ref No: "+verificationcode+" Inverter Model: "+ProductModel+" Inverter Capacity: "+ProductCapacity+" Price: Rs "+ProductActualPrice+" Discount Price: Rs. "+ProductDiscountPrice+" "+strretname+" with Mob No-"+strretmobnum+" and address "+straddress1+" will fullfill your order.For any kind of Assistance please call to 9603467559. ";

			

			ThankUSMSMsg ="Thanks for choosing bookbattery.com .Your Inverter Ord Ref No: "+verificationcode+" for Inverter Model: "+ProductModel+" Price: Rs. "+ProductActualPrice+" Discount Price: Rs. "+ProductDiscountPrice+" You will shortly receive a call.For any kind of Assistance please call to 9603467559. ";
			
			String strSMSResponse=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUSMSMsg, UserMobileNumber);
			LogLevel.DEBUG(5, new Throwable(), "strSMSResponse :" + strSMSResponse);

			String strsub="Dear "+UserName+",\r\n\r\n"+""+ThankUMsg+"";
			String strThanks="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
			MailTrans sendverifycode=new MailTrans();
			sendverifycode.setStrSmtpHost(strdomainname);
			sendverifycode.setStrFrom(FromEmailId);
			sendverifycode.setStrTo(UserEmail);
			sendverifycode.setStrSubject("BookBattery Inverter Details.");
			String activateLink = strsub+"\r\n\r\n"+strThanks+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
			LogLevel.DEBUG(5, new Throwable(), "activateLink :" + activateLink);
			sendverifycode.setStrText(activateLink);
			Thread mt=new MailThread(sendverifycode,"");
			mt.start();

			/* code to send SMS and Email consumer details to retailers */ 
			SendSMS sendsms1 = new SendSMS(qm);
			String ThankUMsg1="";
			String activateLink1 ="";
			String strThanks1="";
			String CustomerAddress="";

			if(UserAddress.equals("") && UserLandmark.equals(""))
			{
				CustomerAddress="";

			}
			else
			{
				CustomerAddress=", Address: "+UserAddress+",LandMark: "+UserLandmark+"";
			}
			if(ProductBrand.equals("Amaron"))
				{
					if(strretname.contains("Amaron-Pitstop"))
					{
						ThankUMsg1="BookBattery Consumer "+UserName+" with Mob no-"+UserMobileNumber+" "+CustomerAddress+" and Order Ref Number "+verificationcode+" ordered for "+ProductBrand+" >> "+ProductModel+" >> "+ProductCapacity+" at price: Rs "+ProductActualPrice+" Discount Price: Rs "+ProductDiscountPrice+".  Please re-confirm with the customer and deliver it. For any kind of Assistance please call to 9603467559. ";
					}
					else
					{
					ThankUMsg1="BookBattery Consumer "+UserName+" with Mob no-"+UserMobileNumber+" "+CustomerAddress+" and Order Ref Number "+verificationcode+" ordered for "+ProductBrand+" >> "+ProductModel+" >> "+ProductCapacity+" at price: Rs "+ProductActualPrice+" Discount Price: Rs "+ProductDiscountPrice+".  Please re-confirm with the customer and deliver it. For any kind of Assistance please call to 9603467559. ";

					}
				}
				else
				{
					ThankUMsg1="BookBattery Consumer "+UserName+" with Mob no-"+UserMobileNumber+" "+CustomerAddress+" and Order Ref Number "+verificationcode+" ordered for "+ProductBrand+" >> "+ProductModel+" >> "+ProductCapacity+" at price: Rs "+ProductActualPrice+" Discount Price: Rs "+ProductDiscountPrice+".  Please re-confirm with the customer and deliver it. For any kind of Assistance please call to 9603467559. ";


				}
			LogLevel.DEBUG(5, new Throwable(), "ThankUMsg1 :" + ThankUMsg1);

			String ThanksmsMsg=""+UserName+" Mob No: "+UserMobileNumber+" and Ord Ref No: "+verificationcode+" ordered "+ProductBrand+" >> "+ProductModel+" >> "+ProductCapacity+" at price: Rs "+ProductActualPrice+" Discount Price:  Rs "+ProductDiscountPrice+" Please install";

			String strSMSResponse1=  sendsms1.sendSMS(strIpAddress,strPort,SMSFromAddress,ThanksmsMsg, strretmobnum);
			LogLevel.DEBUG(5, new Throwable(), "strSMSResponse1 :" + strSMSResponse1);

			String strsub1="Dear "+strretname+",\r\n\r\n"+""+ThankUMsg1+"";
			
			MailTrans retdetails=new MailTrans();
			retdetails.setStrSmtpHost(strdomainname);
			retdetails.setStrFrom(FromEmailId);
			retdetails.setStrTo(strretemail);
			if(ProductBrand.equals("Amaron") && strretname.contains("Amaron-Pitstop"))
				{
					strThanks1="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
					retdetails.setStrSubject("BookBattery Inverter Details.");
					activateLink1 = strsub1+"\r\n\r\n"+strThanks1+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "activateLink1 :" + activateLink1);

				}
				else
				{
					strThanks1="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
					retdetails.setStrSubject("BookBattery Inverter Details.");
					activateLink1 = strsub1+"\r\n\r\n"+strThanks1+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "activateLink1 :" + activateLink1);

				}
			
			retdetails.setStrText(activateLink1);
			Thread mt1=new MailThread(retdetails,"");
			mt1.start();

			String[] tempArr=strretothermobnum.split(",");
			LogLevel.DEBUG(5,new Throwable(),"tempArr.length :"+tempArr.length );
			LogLevel.DEBUG(5,new Throwable(),"tempArr:"+tempArr);
				if(tempArr.length>0)
				  {
					String strTempValue="";
					for (int k=0;k<tempArr.length ;k++ )
						{	
						strTempValue=tempArr[k];
						LogLevel.DEBUG(5,new Throwable(),"tempArr:"+strTempValue);
						String strSMSResponse11=  sendsms1.sendSMS(strIpAddress,strPort,SMSFromAddress,ThanksmsMsg, strTempValue);
						LogLevel.DEBUG(5, new Throwable(), "strSMSResponse11 :" + strSMSResponse11);

						}
				  }
			
			/* code to send SMS and Email consumer details to operators */ 

			String ThanksuppsmsMsg="Inverter Ord Ref No: "+verificationcode+" Cust Mob No: "+UserMobileNumber+" Franchisee Mob No: "+strretmobnum+" Location : "+Strlocorpin+""; 

			String strSMSResponse22 =  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThanksuppsmsMsg, strsuppmobnumber);
			LogLevel.DEBUG(5, new Throwable(), "strSMSResponse22 :" + strSMSResponse22);

			String ThankUMsgopt="BookBattery Consumer "+UserName+" with Mob no-"+UserMobileNumber+", EmailID : "+UserEmail+"   ordered for "+ProductBrand+" >> "+ProductModel+" >> "+ProductCapacity+" at price: Rs."+ProductActualPrice+" Discount Price:  Rs. "+ProductDiscountPrice+" for  "+ProductType+" , Order Ref Number "+verificationcode+" Franchisee Name: "+strretname+" Franchisee Mob No: "+strretmobnum+" Franchisee EmailID: "+strretemail+" Location : "+Strlocorpin+".";

			String strsubop="Dear operator,\r\n\r\n"+""+ThankUMsgopt+"";
			String strThanksop="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
			MailTrans operator=new MailTrans();
			operator.setStrSmtpHost(strdomainname);
			operator.setStrFrom(FromEmailId);
			operator.setStrTo(strsuppemaild);
			operator.setStrSubject("BookBattery Inverter Ordered Details.");
			String activateLinkop = strsubop+"\r\n\r\n"+strThanksop+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
			LogLevel.DEBUG(5, new Throwable(), "activateLinkop :" + activateLinkop);
			operator.setStrText(activateLinkop);
			Thread mtopt=new MailThread(operator,"");
			mtopt.start();

			Calendar cal = Calendar.getInstance();
			int currentMonth = cal.get(Calendar.MONTH) + 1;
			int currentYear = cal.get(Calendar.YEAR);

			String strPdfURL="";
			
		//######################################## Operator Code	####################
		String Get_Previous_Operator_SQL = "select agent_name from inverter_order_details where consumer_mobnumber='"+UserMobileNumber+"' and (NOT agent_name = '0' or agent_name = ' ' ) and YEAR(creation_date) = YEAR(NOW()) AND MONTH(creation_date) = MONTH(NOW()) AND DAY(creation_date) = DAY(NOW()) order by creation_date limit 1";
		LogLevel.DEBUG(5, new Throwable(), "Get_Previous_Operator_SQL :" + Get_Previous_Operator_SQL);

		Hashtable Get_Previous_Operator_HT = qm.getRow(Get_Previous_Operator_SQL);
		LogLevel.DEBUG(5, new Throwable(), "Get_Previous_Operator_HT :" + Get_Previous_Operator_HT);

		if(Get_Previous_Operator_HT.isEmpty())
		{
			String StrSqlQryOperatorname = "select agent_name from inverter_order_details where (NOT agent_name = '0' or agent_name = ' ' ) order by creation_date desc limit 1";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQryOperatorname :" + StrSqlQryOperatorname);

			Hashtable htgetoperatorname = qm.getRow(StrSqlQryOperatorname); 
			agent_name=(String)htgetoperatorname.get("agent_name");
			
			String assigntoagent="";
			int operatorteamcountint = Integer.parseInt(OperatorTeamCount);
			 for(int i=1; i<operatorteamcountint; i++)
			{
				LogLevel.DEBUG(5, new Throwable(), "i value:" +i);						

				if(agent_name.equals("operator"+i+""))
				{
					int jk = new Integer(i+ 1);
					if(jk == operatorteamcountint)
					{
						assigntoagent="operator1";
						LogLevel.DEBUG(5, new Throwable(), "assigntoagent:" +assigntoagent);
						break;
					}
					else
					{
						assigntoagent="operator"+jk;
						LogLevel.DEBUG(5, new Throwable(), "assigntoagent:" +assigntoagent);
						break;
					}
				}
			}
			
			if(assigntoagent.equals(""))
			{
				agent_name="operator1";
			}
			else
			{
				agent_name=assigntoagent;
			}
		}
		else
		{
			agent_name=(String)Get_Previous_Operator_HT.get("agent_name");
			LogLevel.DEBUG(5, new Throwable(), "agent_name :" + agent_name);
			// This condition is to assign operator in Worst case of Software failure.  
			if(agent_name.equals(""))
			{
				agent_name="operator1";
			}
			else
			{
				agent_name=agent_name;
			}
		}
		//######################################## Operator Code	####################

		String strSqlQry="";
		String User_Address_Landmark=UserAddress+"\r\n Landmark : "+UserLandmark;
		User_Address_Landmark = User_Address_Landmark.replace("'","\\'");
		LogLevel.DEBUG(5,new Throwable()," User_Address_Landmark "+User_Address_Landmark);
		
		if(Pre_Order_Number.equals("Failed"))
		{
			//############### Back Up condition 
			//Worst Software failure. This Condition works when Insert Before order Fails
			//############### By Sai Krishna Daddala
				
			strSqlQry = "insert into inverter_order_details(order_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_verify_code,retailer_name,retailer_mobile_number,retailer_emailid,state,city,inverter_brand,inverter_model,inverter_capacity,price,area,pincode,creation_date,agent_name,order_status,erp_pre_tax,MRP_Price,city_percentage,consumer_address,confirm_by)values(NULL,'"+verificationcode+"','"+UserName+"','"+UserMobileNumber+"','"+UserEmail+"','"+UserMobileNumberOTP+"','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+UserState+"','"+UserCity+"','"+ProductBrand+"','"+ProductModel+"','"+ProductCapacity+"','"+ProductDiscountPrice+"','"+UserArea+"','"+UserPincode+"',now(),'"+agent_name+"','confirmed','"+ERP_Pre_TAX+"','"+ProductActualPrice+"','"+City_Percentage+"','"+User_Address_Landmark+"','Customer')";
		}
		else
		{
			strSqlQry = "UPDATE inverter_order_details SET order_number = '"+Pre_Order_Number+"', consumer_name = '"+UserName+"', consumer_mobnumber = '"+UserMobileNumber+"', consumer_emailid = '"+UserEmail+"', consumer_verify_code = '"+UserMobileNumberOTP+"', retailer_name = '"+strretname+"', retailer_mobile_number = '"+strretmobnum+"', retailer_emailid = '"+strretemail+"', state = '"+UserState+"', city = '"+UserCity+"', inverter_brand = '"+ProductBrand+"', inverter_model = '"+ProductModel+"', inverter_capacity = '"+ProductCapacity+"', price = '"+ProductDiscountPrice+"', area = '"+UserArea+"', pincode = '"+UserPincode+"', creation_date = now(), order_status='confirmed', consumer_address='"+User_Address_Landmark+"',confirm_by='Customer' WHERE order_number = '"+Pre_Order_Number+"'";
		}

		LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
		int reslt = qm.executeUpdate(strSqlQry);
		

			String strSqlQryleads ="insert into leads(lead_id,retailer_name,retailer_id,deal_id,email_id,vendor_id,product_id,product_name,mobile_number,vendor_name,promotion_code,price,subcategory_id,category_id,creation_date,created_by,updated_date,updated_by)values(NULL,'"+strretname+"','"+Strretid+"','0','"+UserEmail+"','22','0','undefined','"+UserMobileNumber+"','"+ProductBrand+"','0','0','0','0',now(),'ngit',now(),'ngit')"; 
			LogLevel.DEBUG(5,new Throwable()," :strSqlQryleads "+strSqlQryleads);
			int resltleads = qm.executeUpdate(strSqlQryleads);

				strRes="Sucessfully | "+verificationcode+""; 

				/** Jhansi Ended placing code for inverter battery **/
				String StrSqlQryuser = "select email_id from batterywale_user_profile where email_id='"+UserEmail+"'";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQryuser :" + StrSqlQryuser);

				Hashtable htruser = qm.getRow(StrSqlQryuser); 
				String Stremailid=String.valueOf(htruser.get("email_id"));
				LogLevel.DEBUG(5, new Throwable(), "Stremailid :" + Stremailid);

				if(Stremailid.equals(null) || Stremailid.equals("null") || Stremailid == null || Stremailid == "null" || Stremailid =="")
				{
					UserAddress = UserAddress.replace("'","\\'");
					UserLandmark = UserLandmark.replace("'","\\'");

					String strSqlQryuserprof = "insert into batterywale_user_profile(user_id,email_id,mobile_number,password,name,address,address1,zipcode,city,state,mobile_verify_code,creation_date,created_by) values(NULL,'"+UserEmail+"','"+UserMobileNumber+"','"+pw+"','"+UserName+"','"+UserAddress+"','"+UserLandmark+"','"+UserPincode+"','"+UserCity+"','"+UserState+"','"+UserMobileNumberOTP+"',now(),'ngit')";

					LogLevel.DEBUG(5,new Throwable()," : "+strSqlQryuserprof);
					int reslt1 = qm.executeUpdate(strSqlQryuserprof);
				}
				else
				{
					String strSqlQryupdatepassword = "update batterywale_user_profile set password='"+pw+"' where email_id='"+UserEmail+"'";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQryupdatepassword : "+strSqlQryupdatepassword);
					int reslt12 = qm.executeUpdate(strSqlQryupdatepassword);
				}
			StringBuilder result = new StringBuilder();
			URL url = new URL(""+baseUrl+"/bookbattery/servlet/UserLoginServlet?hidWhatToDo=loginwithanotherserver&email="+UserEmail+"&mobilenumber="+UserMobileNumber+"&password="+pw+"&username="+UserName+"&city="+UserCity+"&state="+UserState+"&deviceflag=web&producttype=inverter&vehiclename=&vehiclemodel=&productbrand="+ProductBrand+"&productmodel="+ProductModel+"&withoutoldproductprice="+ProductDiscountPrice+"&witholdproductprice=&quantity=1&retailername="+strretname+"&retailermobileno="+strretmobnum+"");
			LogLevel.DEBUG(5, new Throwable(), "result.toString() :" + url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
			 result.append(line);
			}
			rd.close();
			   
			LogLevel.DEBUG(5, new Throwable(), "result.toString() :" + result.toString());

			}	
		}
		catch (Exception e)
		{
			LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
			strRes=strRes;
		}
		return strRes;
	}
	
	/* *************************************************************************************************************\
	* This action is used to Insert Inverter and Battery data in to DB
	\* *************************************************************************************************************/
	public String InsertOrderDetails_Combo(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort,String SMSFromAddress, String FromEmailId, String strsuppemaild, String baseUrl, String strsuppmobnumber,String strCMSSERVERIP,String strPDFFilePath,String strPDFRelFilePath,String OperatorTeamCount) 
	{ 	
	
	String UserMobileNumber= (req.getParameter("UMN")!=null)?(req.getParameter("UMN")):"";
	String UserMobileNumberOTP= (req.getParameter("UOTP")!=null)?(req.getParameter("UOTP")):"";
	String ProductType= (req.getParameter("UPT")!=null)?(req.getParameter("UPT")):"";
	String ProductBrand= (req.getParameter("UPB")!=null)?(req.getParameter("UPB")):"";
	String ProductModel= (req.getParameter("UPM")!=null)?(req.getParameter("UPM")):"";
	String UserName= (req.getParameter("UNA")!=null)?(req.getParameter("UNA")):"";
	String UserEmail= (req.getParameter("UEM")!=null)?(req.getParameter("UEM")):"";
	String UserArea= (req.getParameter("UAR")!=null)?(req.getParameter("UAR")):"";
	String UserPincode= (req.getParameter("UPN")!=null)?(req.getParameter("UPN")):"";
	String UserState= (req.getParameter("UST")!=null)?(req.getParameter("UST")):"";
	String UserCity= (req.getParameter("UCT")!=null)?(req.getParameter("UCT")):"";
	String UserAddress= (req.getParameter("UAD")!=null)?(req.getParameter("UAD")):"";
	String UserLandmark= (req.getParameter("ULM")!=null)?(req.getParameter("ULM")):"";
	
	String UserInverterBatteryModel= (req.getParameter("UIBM")!=null)?(req.getParameter("UIBM")):"";
	String UserInverterBatteryCapacity= (req.getParameter("UIBC")!=null)?(req.getParameter("UIBC")):"";
	String Pre_Order_Number= (req.getParameter("ORD")!=null)?(req.getParameter("ORD")):"";
		
	String agent_name = "";

	String strRes="";
	String strstate="";
	String strstate1="";
	String StrSqlQry ="";
	String Strretid="";
	String Strlocorpin="";
	String strcustax="";
	String strcusper="";
	
	String InverterPrice="";
	String InverterDiscount="";
	String InverterType="";
	String InverterCapacity="";
	String InverterBatteryCapacity="";
	
	String Inverter_Battery_Act_Price="";
	String WithOutOldBatteryPrice="";
	String WithOldBatteryPrice="";
	String City_Percentage="";
	String ERP_Pre_TAX_Inverter="";
	String ERP_Pre_TAX_Battery="";
	
	UserMobileNumber =UserMobileNumber.replaceAll("\\+", " ");
	UserMobileNumberOTP =UserMobileNumberOTP.replaceAll("\\+", " ");
	UserMobileNumberOTP=UserMobileNumberOTP.replaceAll("[\\t\\n\\r]","");
	ProductType =ProductType.replaceAll("\\+", " ");
	ProductBrand =ProductBrand.replaceAll("\\+", " ");
	ProductModel =ProductModel.replaceAll("\\+", " ");
	UserName =UserName.replaceAll("\\+", " ");
	UserEmail =UserEmail.replaceAll("\\+", " ");
	UserArea =UserArea.replaceAll("\\+", " ");
	UserPincode =UserPincode.replaceAll("\\+", " ");
	UserState =UserState.replaceAll("\\+", " ");
	UserCity =UserCity.replaceAll("\\+", " ");
	UserAddress =UserAddress.replaceAll("\\+", " ");
	UserLandmark =UserLandmark.replaceAll("\\+", " ");
	
	UserInverterBatteryModel =UserInverterBatteryModel.replaceAll("\\+", " ");
	UserInverterBatteryCapacity =UserInverterBatteryCapacity.replaceAll("\\+", " ");
	
	Hashtable htretailerid =new Hashtable();
	try
	{	
		UserMobileNumber =java.net.URLDecoder.decode(UserMobileNumber, "UTF-8");
		UserMobileNumberOTP =java.net.URLDecoder.decode(UserMobileNumberOTP, "UTF-8");
		ProductType =java.net.URLDecoder.decode(ProductType, "UTF-8");
		ProductBrand =java.net.URLDecoder.decode(ProductBrand, "UTF-8");
		ProductModel =java.net.URLDecoder.decode(ProductModel, "UTF-8");
		UserName =java.net.URLDecoder.decode(UserName, "UTF-8");
		UserEmail =java.net.URLDecoder.decode(UserEmail, "UTF-8");
		UserArea =java.net.URLDecoder.decode(UserArea, "UTF-8");
		UserPincode =java.net.URLDecoder.decode(UserPincode, "UTF-8");
		UserState =java.net.URLDecoder.decode(UserState, "UTF-8");
		UserCity =java.net.URLDecoder.decode(UserCity, "UTF-8");
		UserAddress =java.net.URLDecoder.decode(UserAddress, "UTF-8");
		UserLandmark =java.net.URLDecoder.decode(UserLandmark, "UTF-8");

		UserInverterBatteryModel =java.net.URLDecoder.decode(UserInverterBatteryModel, "UTF-8");
		UserInverterBatteryCapacity =java.net.URLDecoder.decode(UserInverterBatteryCapacity, "UTF-8");
	
		String Get_Inverter_Price_SQL ="select DISTINCT a.inverter_actual_price, a.inverter_discount_price, a.inverter_capacity, a.inverter_eretailer_price, b.inverter_city_percentage from inverter_price_details a, percentage b where a.state='"+UserState+"' and a.city='"+UserCity+"' and a.inverter_brand='"+ProductBrand+"' and a.inverter_model='"+ProductModel+"' and a.city=b.city "; 
		LogLevel.DEBUG(5, new Throwable(), "Get_Inverter_Price_SQL :" + Get_Inverter_Price_SQL);
				
		Hashtable Get_Inverter_Price_SQL_HT = qm.getRow(Get_Inverter_Price_SQL);
		LogLevel.DEBUG(5, new Throwable(), "Get_Inverter_Price_SQL_HT :" + Get_Inverter_Price_SQL_HT);

		if(Get_Inverter_Price_SQL_HT.isEmpty())
		{
			strRes="Session Expired or Server Down. Please regenerate your order.";
			return strRes;
		}
		else{
			int ProductActualPrice_int=(Integer)Get_Inverter_Price_SQL_HT.get("inverter_actual_price");
			LogLevel.DEBUG(5, new Throwable(), "ProductActualPrice_int :" + ProductActualPrice_int);
			
			int ProductDiscountPrice_int=(Integer)Get_Inverter_Price_SQL_HT.get("inverter_discount_price");
			LogLevel.DEBUG(5, new Throwable(), "ProductDiscountPrice_int :" + ProductDiscountPrice_int);
			
			int ProductERP_Pre_TAX_int=(Integer)Get_Inverter_Price_SQL_HT.get("inverter_eretailer_price");
			LogLevel.DEBUG(5, new Throwable(), "ProductERP_Pre_TAX_int :" + ProductERP_Pre_TAX_int);
			
			InverterPrice = Integer.toString(ProductActualPrice_int);
			InverterDiscount = Integer.toString(ProductDiscountPrice_int);
			ERP_Pre_TAX_Inverter = Integer.toString(ProductERP_Pre_TAX_int);
			
			InverterCapacity=(String)Get_Inverter_Price_SQL_HT.get("inverter_capacity");
			LogLevel.DEBUG(5, new Throwable(), "InverterCapacity :" + InverterCapacity);
			
			City_Percentage=(String)Get_Inverter_Price_SQL_HT.get("inverter_city_percentage");
			LogLevel.DEBUG(5, new Throwable(), "City_Percentage :" + City_Percentage);
		}
				
		String Get_Inverter_Battery_Price_SQL ="select DISTINCT a.batprice_id, a.bat_act_price,a.bat_capacity, a.bat_witbat_price, a.bat_ret_price, a.erp_pre_tax, b.city_percentage from batteryprice a, percentage b where a.bat_brand='"+ProductBrand+"' and a.bat_model='"+UserInverterBatteryModel+"' and a.state='"+UserState+"' and a.city='"+UserCity+"' and a.city=b.city ORDER BY batprice_id DESC LIMIT 1"; 
		LogLevel.DEBUG(5, new Throwable(), "Get_Inverter_Battery_Price_SQL :" + Get_Inverter_Battery_Price_SQL);

		Hashtable Get_Inverter_Battery_Price_HT = qm.getRow(Get_Inverter_Battery_Price_SQL);
		LogLevel.DEBUG(5, new Throwable(), "Get_Inverter_Battery_Price_HT :" + Get_Inverter_Battery_Price_HT);

		if(Get_Inverter_Battery_Price_HT.isEmpty())
		{
			strRes="Session Expired or Server Down. Please regenerate your order.";
			return strRes;
		}
		else{
			Inverter_Battery_Act_Price=(String)Get_Inverter_Battery_Price_HT.get("bat_act_price");
			LogLevel.DEBUG(5, new Throwable(), "Inverter_Battery_Act_Price :" + Inverter_Battery_Act_Price);
			
			WithOutOldBatteryPrice=(String)Get_Inverter_Battery_Price_HT.get("bat_witbat_price");
			LogLevel.DEBUG(5, new Throwable(), "WithOutOldBatteryPrice :" + WithOutOldBatteryPrice);
						
			InverterBatteryCapacity=(String)Get_Inverter_Battery_Price_HT.get("bat_capacity");
			LogLevel.DEBUG(5, new Throwable(), "InverterBatteryCapacity :" + InverterBatteryCapacity);
			
			String Battery_Ret_Price=(String)Get_Inverter_Battery_Price_HT.get("bat_ret_price");
			LogLevel.DEBUG(5, new Throwable(), "Battery_Ret_Price :" + Battery_Ret_Price);

			int Battery_Act_Price_int = Integer.parseInt(Inverter_Battery_Act_Price);
			int WithoutOldBatteryPrice_int = Integer.parseInt(WithOutOldBatteryPrice);
			int Battery_Ret_Price_int = Integer.parseInt(Battery_Ret_Price);
			int WithOldBatteryPrice_int = WithoutOldBatteryPrice_int - Battery_Ret_Price_int;
			
			WithOldBatteryPrice = Integer.toString(WithOldBatteryPrice_int);
			WithOutOldBatteryPrice = Integer.toString(WithoutOldBatteryPrice_int);
			
			ERP_Pre_TAX_Battery =(String)Get_Inverter_Battery_Price_HT.get("erp_pre_tax");
			LogLevel.DEBUG(5, new Throwable(), "ERP_Pre_TAX_Battery :" + ERP_Pre_TAX_Battery);
		}
		
		
		ServletOutputStream out=res.getOutputStream();

		if(UserArea.equals("0") || UserArea.equals("") || UserArea == "")
		{
			String strstate2 = UserState;
			strstate=strstate2.trim().replaceAll(" ", "_"); 
			LogLevel.DEBUG(5, new Throwable(), "strstate :" + strstate);

		Date now = new Date();
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely
		//System.out.println(simpleDateformat.format(now));
		LogLevel.DEBUG(5, new Throwable(), "Day Info :" + simpleDateformat.format(now));
		String day =simpleDateformat.format(now);
		LogLevel.DEBUG(5, new Throwable(), "Day Info String:" + day);
		
		if(day.equals("Sunday"))
		{
			String StrSqlQry13 = "select retailer_id from retailer_location_mapping where pincode='"+UserPincode+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='Yes' limit 1";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQry13 :" + StrSqlQry13);
			Hashtable htretailerid12 = qm.getRow(StrSqlQry13); 
		if(htretailerid12.isEmpty())
		{
			StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+UserPincode+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='No' limit 1";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);

		}
		else
			{
				StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+UserPincode+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='Yes' limit 1";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);

			}
		}
		else
		{
			StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+UserPincode+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='No' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
		}

			htretailerid = qm.getRow(StrSqlQry); 
			Strretid=String.valueOf(htretailerid.get("retailer_id"));
			LogLevel.DEBUG(5, new Throwable(), "Strretid :" + Strretid);
			Strlocorpin = UserPincode;

		}
		else
		{
			String StrSqlQrystate = "select state from search_whereever_keywords where city='"+UserCity+"'";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQrystate :" + StrSqlQrystate); 

			Hashtable htgetstate = qm.getRow(StrSqlQrystate); 
			strstate1=(String)htgetstate.get("state");

			strstate=strstate1.trim().replaceAll(" ", "_"); 
			LogLevel.DEBUG(5, new Throwable(), "strstate :" + strstate);
			
			Date now = new Date();
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely
		//System.out.println(simpleDateformat.format(now));
		LogLevel.DEBUG(5, new Throwable(), "Day Info :" + simpleDateformat.format(now));
		String day =simpleDateformat.format(now);
		LogLevel.DEBUG(5, new Throwable(), "Day Info String:" + day);
		
		if(day.equals("Sunday"))
		{

			String StrSqlQry12 = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+UserCity+"' and area='"+UserArea+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='Yes' limit 1";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQry12 :" + StrSqlQry12);
			Hashtable htretailerid1 = qm.getRow(StrSqlQry12); 
			if(htretailerid1.isEmpty())
			{
				StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+UserCity+"' and area='"+UserArea+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='No' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);

			}
			else
			{

				StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+UserCity+"' and area='"+UserArea+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='Yes' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
			}
		}
		else
		{
			StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+UserCity+"' and area='"+UserArea+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='No' limit 1";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
		}

			htretailerid = qm.getRow(StrSqlQry); 
			Strretid=String.valueOf(htretailerid.get("retailer_id"));
			LogLevel.DEBUG(5, new Throwable(), "Strretid :" + Strretid);
			Strlocorpin = UserCity;
		}
		LogLevel.DEBUG(5, new Throwable(), "strstate :" + strstate);
		if(Strretid.equals(null) || Strretid.equals("null") || Strretid == null || Strretid == "null" || Strretid =="")
		{
			strRes="Sorry!, No Retailers Found on Selected City or Area.";  
		}
		else
		{
		String StrSqlQrydet = "select retailer_name,mobile_number,email_id,address1,mobile_numberother,invoice_flag from "+strstate+"_retailers where retailer_id='"+Strretid+"'";
		LogLevel.DEBUG(5, new Throwable(), "StrSqlQrydet :" + StrSqlQrydet);

		Hashtable htretailerdetls = qm.getRow(StrSqlQrydet); 
		String strretmobnum=String.valueOf(htretailerdetls.get("mobile_number"));
		String strretname=(String)htretailerdetls.get("retailer_name");
		String strretemail=(String)htretailerdetls.get("email_id");
		String straddress1=(String)htretailerdetls.get("address1");
		String strretothermobnum=String.valueOf(htretailerdetls.get("mobile_numberother"));
		String strinvoiceflag=String.valueOf(htretailerdetls.get("invoice_flag"));


		//######################################## Order Number Code	####################
		String New_Order_ID_SQL = "SELECT order_id as ORDER_ID FROM inverter_order_details ORDER BY order_id DESC LIMIT 1";
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
		String verificationcode = "";
		String verificationcode1 = Integer.toString(Num);
		verificationcode ="ORDI"+Last_Order_Count_String+""+verificationcode1+"B";
		LogLevel.DEBUG(5, new Throwable(), "verificationcode :" + verificationcode);

		if(Pre_Order_Number.equals("Failed"))
		{
			verificationcode=verificationcode;
		}
		else
		{
			verificationcode=Pre_Order_Number;
		}
		
		/*following code is for generating the random number */
		String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";

		String pw = "";
			for (int i=0; i<PASSWORD_LENGTH; i++)
			{
			int index = (int)(RANDOM.nextDouble()*letters.length());
			pw += letters.substring(index, index+1);
			}
		LogLevel.DEBUG(5, new Throwable(), "pw :" + pw);
		String ThankUSMSMsg = "";
		String ThankUMsg="";
		/*code to generate the random number ends here */
		/* code to send SMS and Email retailers details to consumer */ 
		CompareTrans ct = new CompareTrans(qm);
		SendSMS sendsms = new SendSMS(qm);

			/*if(ProductBrand.equals("Amaron"))
			{

				ThankUMsg="Your BookBattery Inverter Ord Ref No: "+verificationcode+" Inverter Model: "+ProductModel+" Inverter Capacity: "+InverterCapacity+" Price: Rs "+InverterPrice+" Discount Price: Rs. "+InverterDiscount+" "+strretname+" with Mob No-"+strretmobnum+" and address "+straddress1+" will fullfill your order. Please call +91-9603467559 to avail additional 1% cash back.";
			}
			else
			{
				ThankUMsg="Your BookBattery Inverter Ord Ref No: "+verificationcode+" Inverter Model: "+ProductModel+" Inverter Capacity: "+InverterCapacity+" Price: Rs "+InverterPrice+" Discount Price: Rs. "+InverterDiscount+" "+strretname+" with Mob No-"+strretmobnum+" and address "+straddress1+" will fullfill your order. Please call +91-9603467559 to avail additional 1% cash back.";

			}*/

		ThankUMsg="Your BookBattery Inverter Ord Ref No: "+verificationcode+" Inverter Model: "+ProductModel+" Inverter Capacity: "+InverterCapacity+" Price: Rs "+InverterPrice+" Discount Price: Rs. "+InverterDiscount+" "+strretname+" with Mob No-"+strretmobnum+" and address "+straddress1+" will fullfill your order.For any kind of Assistance please call to 9603467559. ";

		

		ThankUSMSMsg ="Thanks for choosing bookbattery.com .Your Inverter Ord Ref No: "+verificationcode+" for Inverter Model: "+ProductModel+" Price: Rs. "+InverterPrice+" Discount Price: Rs. "+InverterDiscount+" You will shortly receive a call.For any kind of Assistance please call to 9603467559. ";
		
		String strSMSResponse=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUSMSMsg, UserMobileNumber);
		LogLevel.DEBUG(5, new Throwable(), "strSMSResponse :" + strSMSResponse);

		String strsub="Dear "+UserName+",\r\n\r\n"+""+ThankUMsg+"";
		String strThanks="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
		MailTrans sendverifycode=new MailTrans();
		sendverifycode.setStrSmtpHost(strdomainname);
		sendverifycode.setStrFrom(FromEmailId);
		sendverifycode.setStrTo(UserEmail);
		sendverifycode.setStrSubject("BookBattery Inverter Details.");
		String activateLink = strsub+"\r\n\r\n"+strThanks+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
		LogLevel.DEBUG(5, new Throwable(), "activateLink :" + activateLink);
		sendverifycode.setStrText(activateLink);
		Thread mt=new MailThread(sendverifycode,"");
		mt.start();

		/* code to send SMS and Email consumer details to retailers */ 
		SendSMS sendsms1 = new SendSMS(qm);
		String ThankUMsg1="";
		String activateLink1 ="";
		String strThanks1="";
		String CustomerAddress="";

		if(UserAddress.equals("") && UserLandmark.equals(""))
		{
			CustomerAddress="";

		}
		else
		{
			CustomerAddress="Address: "+UserAddress+","+UserLandmark+"";

		}
		if(ProductBrand.equals("Amaron"))
			{
				if(strretname.contains("Amaron-Pitstop"))
				{
					ThankUMsg1="BookBattery Consumer "+UserName+" with Mob no-"+UserMobileNumber+" "+CustomerAddress+" and Order Ref Number "+verificationcode+" ordered for "+ProductBrand+" >> "+ProductModel+" >> "+InverterCapacity+" at price: Rs "+InverterPrice+" Discount Price: Rs "+InverterDiscount+".  Please re-confirm with the customer and deliver it. For any kind of Assistance please call to 9603467559. ";
				}
				else
				{
					ThankUMsg1="BookBattery Consumer "+UserName+" with Mob no-"+UserMobileNumber+" "+CustomerAddress+" and Order Ref Number "+verificationcode+" ordered for "+ProductBrand+" >> "+ProductModel+" >> "+InverterCapacity+" at price: Rs "+InverterPrice+" Discount Price: Rs "+InverterDiscount+".  Please re-confirm with the customer and deliver it. For any kind of Assistance please call to 9603467559. ";

				}
			}
			else
			{
				ThankUMsg1="BookBattery Consumer "+UserName+" with Mob no-"+UserMobileNumber+" "+CustomerAddress+" and Order Ref Number "+verificationcode+" ordered for "+ProductBrand+" >> "+ProductModel+" >> "+InverterCapacity+" at price: Rs "+InverterPrice+" Discount Price: Rs "+InverterDiscount+".  Please re-confirm with the customer and deliver it. For any kind of Assistance please call to 9603467559. ";


			}
		LogLevel.DEBUG(5, new Throwable(), "ThankUMsg1 :" + ThankUMsg1);

		String ThanksmsMsg=""+UserName+" Mob No: "+UserMobileNumber+" and Ord Ref No: "+verificationcode+" ordered "+ProductBrand+" >> "+ProductModel+" >> "+InverterCapacity+" at price: Rs "+InverterPrice+" Discount Price:  Rs "+InverterDiscount+" Please install";

		String strSMSResponse1=  sendsms1.sendSMS(strIpAddress,strPort,SMSFromAddress,ThanksmsMsg, strretmobnum);
		LogLevel.DEBUG(5, new Throwable(), "strSMSResponse1 :" + strSMSResponse1);

		String strsub1="Dear "+strretname+",\r\n\r\n"+""+ThankUMsg1+"";
		
		MailTrans retdetails=new MailTrans();
		retdetails.setStrSmtpHost(strdomainname);
		retdetails.setStrFrom(FromEmailId);
		retdetails.setStrTo(strretemail);
		if(ProductBrand.equals("Amaron")  && strretname.contains("Amaron-Pitstop"))
			{
				strThanks1="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
				retdetails.setStrSubject("BookBattery Inverter Details.");
				activateLink1 = strsub1+"\r\n\r\n"+strThanks1+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
				LogLevel.DEBUG(5, new Throwable(), "activateLink1 :" + activateLink1);

			}
			else
			{
				strThanks1="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
				retdetails.setStrSubject("BookBattery Inverter Details.");
				activateLink1 = strsub1+"\r\n\r\n"+strThanks1+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
				LogLevel.DEBUG(5, new Throwable(), "activateLink1 :" + activateLink1);

			}
		
		retdetails.setStrText(activateLink1);
		Thread mt1=new MailThread(retdetails,"");
		mt1.start();

		String[] tempArr=strretothermobnum.split(",");
		LogLevel.DEBUG(5,new Throwable(),"tempArr.length :"+tempArr.length );
		LogLevel.DEBUG(5,new Throwable(),"tempArr:"+tempArr);
			if(tempArr.length>0)
			  {
				String strTempValue="";
				for (int k=0;k<tempArr.length ;k++ )
					{	
					strTempValue=tempArr[k];
					LogLevel.DEBUG(5,new Throwable(),"tempArr:"+strTempValue);
					String strSMSResponse11=  sendsms1.sendSMS(strIpAddress,strPort,SMSFromAddress,ThanksmsMsg, strTempValue);
					LogLevel.DEBUG(5, new Throwable(), "strSMSResponse11 :" + strSMSResponse11);

					}
			  }
		
		/* code to send SMS and Email consumer details to operators */ 

		String ThanksuppsmsMsg="Inverter Ord Ref No: "+verificationcode+" Cust Mob No: "+UserMobileNumber+" Franchisee Mob No: "+strretmobnum+" Location : "+Strlocorpin+""; 

		String strSMSResponse22 =  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThanksuppsmsMsg, strsuppmobnumber);
		LogLevel.DEBUG(5, new Throwable(), "strSMSResponse22 :" + strSMSResponse22);

		String ThankUMsgopt="BookBattery Consumer "+UserName+" with Mob no-"+UserMobileNumber+", EmailID : "+UserEmail+"   ordered for "+ProductBrand+" >> "+ProductModel+" >> "+InverterCapacity+" at price: Rs."+InverterPrice+" Discount Price:  Rs. "+InverterDiscount+" for  "+InverterType+" , Order Ref Number "+verificationcode+" Franchisee Name: "+strretname+" Franchisee Mob No: "+strretmobnum+" Franchisee EmailID: "+strretemail+" Location : "+Strlocorpin+".";

		String strsubop="Dear operator,\r\n\r\n"+""+ThankUMsgopt+"";
		String strThanksop="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
		MailTrans operator=new MailTrans();
		operator.setStrSmtpHost(strdomainname);
		operator.setStrFrom(FromEmailId);
		operator.setStrTo(strsuppemaild);
		operator.setStrSubject("BookBattery Inverter Ordered Details.");
		String activateLinkop = strsubop+"\r\n\r\n"+strThanksop+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
		LogLevel.DEBUG(5, new Throwable(), "activateLinkop :" + activateLinkop);
		operator.setStrText(activateLinkop);
		Thread mtopt=new MailThread(operator,"");
		mtopt.start();

		Calendar cal = Calendar.getInstance();
		int currentMonth = cal.get(Calendar.MONTH) + 1;
		int currentYear = cal.get(Calendar.YEAR);

		String strPdfURL="";
		//######################################## Operator Code	####################
		String Get_Previous_Operator_SQL = "select agent_name from battery_order_details where consumer_mobnumber='"+UserMobileNumber+"' and (NOT agent_name = '0' or agent_name = ' ' ) and YEAR(creation_date) = YEAR(NOW()) AND MONTH(creation_date) = MONTH(NOW()) AND DAY(creation_date) = DAY(NOW()) order by creation_date limit 1";
		LogLevel.DEBUG(5, new Throwable(), "Get_Previous_Operator_SQL :" + Get_Previous_Operator_SQL);

		Hashtable Get_Previous_Operator_HT = qm.getRow(Get_Previous_Operator_SQL);
		LogLevel.DEBUG(5, new Throwable(), "Get_Previous_Operator_HT :" + Get_Previous_Operator_HT);

		if(Get_Previous_Operator_HT.isEmpty())
		{
			String StrSqlQryOperatorname = "select agent_name from inverter_order_details where (NOT agent_name = '0' or agent_name = ' ' ) order by creation_date desc limit 1";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQryOperatorname :" + StrSqlQryOperatorname);

			Hashtable htgetoperatorname = qm.getRow(StrSqlQryOperatorname); 
			agent_name=(String)htgetoperatorname.get("agent_name");
			
			String assigntoagent="";
				
			int operatorteamcountint = Integer.parseInt(OperatorTeamCount);

			 for(int i=1; i<operatorteamcountint; i++)
			{
				LogLevel.DEBUG(5, new Throwable(), "i value:" +i);						

				if(agent_name.equals("operator"+i+""))
				{
					int jk = new Integer(i+ 1);
					if(jk == operatorteamcountint)
					{
						assigntoagent="operator1";
						LogLevel.DEBUG(5, new Throwable(), "assigntoagent:" +assigntoagent);
						break;
					}
					else
					{
						assigntoagent="operator"+jk;
						LogLevel.DEBUG(5, new Throwable(), "assigntoagent:" +assigntoagent);
						break;
					}
				}
			}
			
			if(assigntoagent.equals(""))
			{
				agent_name="operator1";
			}
			else
			{
				agent_name=assigntoagent;
			}
		}
		else
		{
			agent_name=(String)Get_Previous_Operator_HT.get("agent_name");
			LogLevel.DEBUG(5, new Throwable(), "agent_name :" + agent_name);
			// This condition is to assign operator in Worst case of Software failure.  
			if(agent_name.equals(""))
			{
				agent_name="operator1";
			}
			else
			{
				agent_name=agent_name;
			}
		}
		//######################################## Operator Code	####################
		
		String strSqlQry="";
		String User_Address_Landmark=UserAddress+"\r\n Landmark : "+UserLandmark;
		User_Address_Landmark = User_Address_Landmark.replace("'","\\'");
		LogLevel.DEBUG(5,new Throwable()," User_Address_Landmark "+User_Address_Landmark);
		
		if(Pre_Order_Number.equals("Failed"))
		{	
			//############### Back Up condition 
			//Worst Software failure. This Condition works when Insert Before order Fails
			//############### By Sai Krishna Daddala
			
			strSqlQry = "insert into inverter_order_details(order_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_verify_code,retailer_name,retailer_mobile_number,retailer_emailid,state,city,inverter_brand,inverter_model,inverter_capacity,price,area,pincode,creation_date,agent_name,order_status,erp_pre_tax,MRP_Price,city_percentage,consumer_address,confirm_by)values(NULL,'"+verificationcode+"','"+UserName+"','"+UserMobileNumber+"','"+UserEmail+"','"+UserMobileNumberOTP+"','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+UserState+"','"+UserCity+"','"+ProductBrand+"','"+ProductModel+"','"+InverterCapacity+"','"+InverterDiscount+"','"+UserArea+"','"+UserPincode+"',now(),'"+agent_name+"','confirmed','"+ERP_Pre_TAX_Inverter+"','"+InverterPrice+"','"+City_Percentage+"','"+User_Address_Landmark+"','Customer')";
		}
		else
		{
			strSqlQry = "UPDATE inverter_order_details SET order_number = '"+Pre_Order_Number+"', consumer_name = '"+UserName+"', consumer_mobnumber = '"+UserMobileNumber+"', consumer_emailid = '"+UserEmail+"', consumer_verify_code = '"+UserMobileNumberOTP+"', retailer_name = '"+strretname+"', retailer_mobile_number = '"+strretmobnum+"', retailer_emailid = '"+strretemail+"', state = '"+UserState+"', city = '"+UserCity+"', inverter_brand = '"+ProductBrand+"', inverter_model = '"+ProductModel+"', inverter_capacity = '"+InverterCapacity+"', price = '"+InverterDiscount+"', area = '"+UserArea+"', pincode = '"+UserPincode+"', creation_date = now(), order_status='confirmed',consumer_address='"+User_Address_Landmark+"',confirm_by='Customer' WHERE order_number = '"+Pre_Order_Number+"'";
		}

		LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
		int reslt = qm.executeUpdate(strSqlQry);
			


		String strSqlQryleads ="insert into leads(lead_id,retailer_name,retailer_id,deal_id,email_id,vendor_id,product_id,product_name,mobile_number,vendor_name,promotion_code,price,subcategory_id,category_id,creation_date,created_by,updated_date,updated_by)values(NULL,'"+strretname+"','"+Strretid+"','0','"+UserEmail+"','22','0','undefined','"+UserMobileNumber+"','"+ProductBrand+"','0','0','0','0',now(),'ngit',now(),'ngit')"; 
		LogLevel.DEBUG(5,new Throwable()," :strSqlQryleads "+strSqlQryleads);
		int resltleads = qm.executeUpdate(strSqlQryleads);



	/** Jhansi Started placing code for inverter battery **/
		String verificationcodeinvbat = "";

		if(UserInverterBatteryModel.equals("0") || UserInverterBatteryModel.equals("") || UserInverterBatteryModel == "")
			{
				LogLevel.DEBUG(5, new Throwable(), "battery model :" + UserInverterBatteryModel);

		}
		else
		{
			int looprun;
			
			String Get_Inverter_Battery_Count_SQL ="select DISTINCT inverter_battery_pcs from inverter_details where inverter_brand='"+ProductBrand+"' and inverter_model='"+ProductModel+"'"; 
			LogLevel.DEBUG(5, new Throwable(), "Get_Inverter_Battery_Count_SQL :" + Get_Inverter_Battery_Count_SQL);

			Hashtable Get_Inverter_Battery_Count_HT = qm.getRow(Get_Inverter_Battery_Count_SQL);
			LogLevel.DEBUG(5, new Throwable(), "Get_Inverter_Battery_Count_HT :" + Get_Inverter_Battery_Count_HT);

			if(Get_Inverter_Battery_Count_HT.isEmpty())
			{
				looprun=1;
			}
			else{
				
				String Inverter_Battery_Count=(String)Get_Inverter_Battery_Count_HT.get("inverter_battery_pcs");
				LogLevel.DEBUG(5, new Throwable(), "Inverter_Battery_Count :" + Inverter_Battery_Count);
				
				looprun = Integer.parseInt(Inverter_Battery_Count);
			}

			for(int z=0;z<looprun;z++)
			{
				LogLevel.DEBUG(5, new Throwable(), "Entering to for loop :" + z);


			//######################################## Order Number Code	####################
			String New_Order_ID_SQL_invbat = "SELECT ord_id as ORDER_ID FROM battery_order_details ORDER BY ord_id DESC LIMIT 1";
			Hashtable New_Order_ID_HT_invbat = qm.getRow(New_Order_ID_SQL_invbat);
			LogLevel.DEBUG(5, new Throwable(), "New_Order_ID_HT_invbat :" + New_Order_ID_HT_invbat);
			int Last_Order_Count_invbat;
			if(New_Order_ID_HT_invbat.isEmpty())
			{
				/*following code is for generating the random number */
				Random Generator_Order_ID_invbat = new Random();   
				Generator_Order_ID_invbat.setSeed(System.currentTimeMillis());   
				int num = Generator_Order_ID_invbat.nextInt(90) + 10; 
				if (num < 100 || num > 999)
				{   
					num = Generator_Order_ID_invbat.nextInt(90) + 10;
					if (num < 100 || num > 999)
					{   
					}   
				} 
				Last_Order_Count_invbat=num;
			}
			else
			{
				Last_Order_Count_invbat=(Integer)New_Order_ID_HT_invbat.get("ORDER_ID");
				LogLevel.DEBUG(5, new Throwable(), "agent_name :" + agent_name);
			}
			
			// #######################Increment on order ID Count
			Last_Order_Count_invbat=Last_Order_Count_invbat+1;
			
			/*following code is for generating the random number */
			Random Generator_Order_ID_Ran_invbat = new Random();   
			Generator_Order_ID_Ran_invbat.setSeed(System.currentTimeMillis());   
			int Num_invbat = Generator_Order_ID_Ran_invbat.nextInt(90) + 10;
			if (Num_invbat < 100 || Num_invbat > 999)
			{   
				Num = Generator_Order_ID_Ran_invbat.nextInt(90) + 10;
				if (Num_invbat < 100 || Num_invbat > 999)
				{   
				}   
			} 
			
			String Last_Order_Count_String_invbat = Integer.toString(Last_Order_Count_invbat);
			String verificationcode1_invbat = Integer.toString(Num_invbat);
			verificationcodeinvbat ="ORDB"+Last_Order_Count_String_invbat+""+verificationcode1_invbat+"B";
			LogLevel.DEBUG(5, new Throwable(), "verificationcodeinvbat :" + verificationcodeinvbat);
						
			/*following code is for generating the random number */
			String letters1 = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";

			String pw1 = "";
				for (int i=0; i<PASSWORD_LENGTH; i++)
				{
					int index = (int)(RANDOM.nextDouble()*letters1.length());
					pw1 += letters1.substring(index, index+1);
				}
			LogLevel.DEBUG(5, new Throwable(), "pw1 :" + pw1);
			
			/*code to generate the random number ends here */

			/* code to send SMS and Email retailers details to consumer */ 
			String ThankUMsg12="";
			String strThanks12=""; 
			String activateLink12 ="";
			if(ProductBrand.equals("Amaron")  && strretname.contains("Amaron-Pitstop"))
				{
					ThankUMsg12="Thanks for choosing bookbattery.com .Your BookBattery Battery Ord Ref No: "+verificationcodeinvbat+". Battery Model: "+UserInverterBatteryModel+". Price: "+WithOutOldBatteryPrice+" OBRP Price: Rs. "+WithOldBatteryPrice+". "+strretname+" with Mob No-"+strretmobnum+" and address "+straddress1+" will fullfill your order.For any kind of Assistance please call to 9603467559.";
					strThanks12="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
				}
				else
				{

					ThankUMsg12="Thanks for choosing bookbattery.com .Your BookBattery Battery Ord Ref No: "+verificationcodeinvbat+". Battery Model: "+UserInverterBatteryModel+". Price: "+WithOutOldBatteryPrice+" OBRP Price: Rs. "+WithOldBatteryPrice+". "+strretname+" with Mob No-"+strretmobnum+" and address "+straddress1+" will fullfill your order.For any kind of Assistance please call to 9603467559.";
					strThanks12="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 

				}

			//String ThankUSMSMsg = "Ord Ref No: "+verificationcode+" . Model: "+UserInverterBatteryModel+" . Price: Rs."+price+" . OBRP Price : Rs."+WithOldBatteryPrice+" . You will shortly receive a call about battery pickup or delivery details.";
		
			//String ThankUSMSMsginvbat ="Ord Ref No: "+verificationcodeinvbat+" . Model: "+UserInverterBatteryModel+" Price: Rs. "+WithOutOldBatteryPrice+" OBRP Price: Rs. "+WithOldBatteryPrice+" You will shortly receive a call about battery pickup or delivery details";

			String ThankUSMSMsginvbat ="Thanks for choosing bookbattery.com .Your Battery Ord Ref No: "+verificationcodeinvbat+" for Battery Model: "+UserInverterBatteryModel+" Price: Rs. "+WithOutOldBatteryPrice+" OBRP Price: Rs. "+WithOldBatteryPrice+" You will shortly receive a call.For any kind of Assistance please call to 9603467559. ";
			
			String strSMSResponse12=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUSMSMsginvbat, UserMobileNumber);
			LogLevel.DEBUG(5, new Throwable(), "strSMSResponse12 :" + strSMSResponse12);

			String strsub12="Dear "+UserName+",\r\n\r\n"+""+ThankUMsg12+"";
			MailTrans sendverifycode12=new MailTrans();
			sendverifycode12.setStrSmtpHost(strdomainname);
			sendverifycode12.setStrFrom(FromEmailId);
			sendverifycode12.setStrTo(UserEmail);
			if(ProductBrand.equals("Amaron")  && strretname.contains("Amaron-Pitstop"))
				{
					sendverifycode12.setStrSubject("BookBattery Inverter Battery Details.");
					activateLink12 = strsub12+"\r\n\r\n"+strThanks12+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "activateLink12 :" + activateLink12);


				}
			else
				{

					sendverifycode12.setStrSubject("BookBattery Inverter Battery Details.");
					activateLink12 = strsub12+"\r\n\r\n"+strThanks12+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "activateLink12 :" + activateLink12);

				}
			sendverifycode12.setStrText(activateLink12);
			Thread mt12=new MailThread(sendverifycode12,"");
			mt12.start();

			
			/* code to send SMS and Email consumer details to retailers */ 
			String ThankUMsg123="";
			String strThanks123=""; 
			String activateLink123 ="";
			if(ProductBrand.equals("Amaron")  && strretname.contains("Amaron-Pitstop"))
				{
					ThankUMsg123="BookBattery Consumer "+UserName+" with Mob no-"+UserMobileNumber+" and Order Ref Number "+verificationcodeinvbat+" ordered for "+ProductBrand+" >> "+UserInverterBatteryModel+" at price: Rs."+WithOutOldBatteryPrice+" OBRP Price: Rs. "+WithOldBatteryPrice+".  Please re-confirm with the customer and deliver it. .For any kind of Assistance please call to 9603467559.";
					strThanks123="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 

				}
				else
				{
					ThankUMsg123="BookBattery Consumer "+UserName+" with Mob no-"+UserMobileNumber+" and Order Ref Number "+verificationcodeinvbat+" ordered for "+ProductBrand+" >> "+UserInverterBatteryModel+" at price: Rs."+WithOutOldBatteryPrice+" OBRP Price: Rs. "+WithOldBatteryPrice+".  Please re-confirm with the customer and deliver it. .For any kind of Assistance please call to 9603467559.";
					strThanks123="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 


				}

			String ThanksmsMsg123=""+UserName+" Mob No: "+UserMobileNumber+" and Ord Ref No: "+verificationcodeinvbat+" ordered "+ProductBrand+" >> "+UserInverterBatteryModel+" at Price: Rs. "+WithOutOldBatteryPrice+" OBRP Price: Rs. "+WithOldBatteryPrice+" Please install";

			String strSMSResponse1234=  sendsms1.sendSMS(strIpAddress,strPort,SMSFromAddress,ThanksmsMsg123, strretmobnum);
			LogLevel.DEBUG(5, new Throwable(), "strSMSResponse1234 :" + strSMSResponse1234);

			String strsub123="Dear "+strretname+",\r\n\r\n"+""+ThankUMsg123+"";
			MailTrans retdetails123=new MailTrans();
			retdetails123.setStrSmtpHost(strdomainname);
			retdetails123.setStrFrom(FromEmailId);
			retdetails123.setStrTo(strretemail);

				if(ProductBrand.equals("Amaron") && strretname.contains("Amaron-Pitstop"))
				{
					retdetails123.setStrSubject("BookBattery Inverter Battery Details.");
					activateLink123 = strsub123+"\r\n\r\n"+strThanks123+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "activateLink123 :" + activateLink123);
				}

				else
				{
					retdetails123.setStrSubject("BookBattery Inverter Battery Details.");
					activateLink123 = strsub123+"\r\n\r\n"+strThanks123+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "activateLink123 :" + activateLink123);


				}
			retdetails123.setStrText(activateLink123);
			Thread mt123=new MailThread(retdetails123,"");
			mt123.start();

			String[] tempArr123=strretothermobnum.split(",");
			LogLevel.DEBUG(5,new Throwable(),"tempArr123.length :"+tempArr123.length );
			LogLevel.DEBUG(5,new Throwable(),"tempArr123:"+tempArr123);
				if(tempArr123.length>0)
				  {
					String strTempValue123="";
					for (int k=0;k<tempArr123.length ;k++ )
						{	
						strTempValue123=tempArr123[k];
						LogLevel.DEBUG(5,new Throwable(),"tempArr123:"+strTempValue123);
						String strSMSResponse1123=  sendsms1.sendSMS(strIpAddress,strPort,SMSFromAddress,ThanksmsMsg123, strTempValue123);
						LogLevel.DEBUG(5, new Throwable(), "strSMSResponse1123 :" + strSMSResponse1123);

						}
				  }
			

			/* Code to send SMS and Email consumer details to Operator */ 

			String ThanksuppsmsMsg123="Battery Ord Ref No: "+verificationcodeinvbat+" Cust Mob No: "+UserMobileNumber+" Franchisee Mob No: "+strretmobnum+" Location : "+Strlocorpin+""; 

			String strSMSResponse223 =  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThanksuppsmsMsg123, strsuppmobnumber);
			LogLevel.DEBUG(5, new Throwable(), "strSMSResponse223 :" + strSMSResponse223);

			String ThankUMsgopt123="BookBattery Consumer "+UserName+" with Mob no-"+UserMobileNumber+", EmailID : "+UserEmail+"   ordered for "+ProductBrand+" >> "+UserInverterBatteryModel+" at price: Rs."+WithOutOldBatteryPrice+" OBRP Price: Rs. "+WithOldBatteryPrice+" for  Inverter Batteries , Order Ref Number "+verificationcodeinvbat+" Franchisee Name: "+strretname+" Franchisee Mob No: "+strretmobnum+" Franchisee EmailID: "+strretemail+" Location : "+Strlocorpin+"";

			String strsubop123="Dear operator,\r\n\r\n"+""+ThankUMsgopt123+"";
			String strThanksop123="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
			MailTrans operator123=new MailTrans();
			operator123.setStrSmtpHost(strdomainname);
			operator123.setStrFrom(FromEmailId);
			operator123.setStrTo(strsuppemaild);
			operator123.setStrSubject("BookBattery Inverter Battery Ordered Details.");
			String activateLinkop123 = strsubop123+"\r\n\r\n"+strThanksop123+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
			LogLevel.DEBUG(5, new Throwable(), "activateLinkop123 :" + activateLinkop123);
			operator123.setStrText(activateLinkop123);
			Thread mtopt123=new MailThread(operator123,"");
			mtopt123.start();

				String strSqlQry123 = "insert into battery_order_details(ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_verif_code,retailer_name,retailer_mobilnumber,retailer_emailis,state,city,battery_brand,battery_model,battery_capacity,price,area,pincode,pdfurl,bat_type,veh_name,veh_model,witholdbatprice,creation_date,agent_name,order_status,erp_pre_tax,MRP_Price,city_percentage,consumer_address,confirm_by)values(NULL,'"+verificationcodeinvbat+"','"+UserName+"','"+UserMobileNumber+"','"+UserEmail+"','"+UserMobileNumberOTP+"','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+UserState+"','"+UserCity+"','"+ProductBrand+"','"+UserInverterBatteryModel+"','"+InverterBatteryCapacity+"','"+WithOutOldBatteryPrice+"','"+UserArea+"','"+UserPincode+"','','Inverter Batteries','','','"+WithOldBatteryPrice+"',now(),'"+agent_name+"','confirmed','"+ERP_Pre_TAX_Battery+"','"+Inverter_Battery_Act_Price+"','"+City_Percentage+"','"+User_Address_Landmark+"','Customer')";
				LogLevel.DEBUG(5,new Throwable()," strSqlQry123: "+strSqlQry123);
				int reslt123 = qm.executeUpdate(strSqlQry123);

				String strSqlQryleads123 ="insert into leads(lead_id,retailer_name,retailer_id,deal_id,email_id,vendor_id,product_id,product_name,mobile_number,vendor_name,promotion_code,price,subcategory_id,category_id,creation_date,created_by,updated_date,updated_by)values(NULL,'"+strretname+"','"+Strretid+"','0','"+UserEmail+"','22','0','undefined','"+UserMobileNumber+"','"+ProductBrand+"','0','0','0','0',now(),'ngit',now(),'ngit')"; 
				LogLevel.DEBUG(5,new Throwable()," :strSqlQryleads123 "+strSqlQryleads123);
				int resltleads123 = qm.executeUpdate(strSqlQryleads123);

			}

		}

		String portalname="";
		if(UserInverterBatteryModel.equals("0") || UserInverterBatteryModel.equals("") || UserInverterBatteryModel == "")
			{
				if(ProductBrand.equals("Amaron"))
				{
					portalname="BookBattery";
				}
				else
				{
					portalname="BookBattery";
				}
				
				strRes="Sucessfully | "+verificationcode+"";
			}
			else
			{
				strRes="Sucessfully | "+verificationcodeinvbat+"";
			}
	/** Jhansi Ended placing code for inverter battery **/
		String StrSqlQryuser = "select email_id from batterywale_user_profile where email_id='"+UserEmail+"'";
		LogLevel.DEBUG(5, new Throwable(), "StrSqlQryuser :" + StrSqlQryuser);

		Hashtable htruser = qm.getRow(StrSqlQryuser); 
		String Stremailid=String.valueOf(htruser.get("email_id"));
		LogLevel.DEBUG(5, new Throwable(), "Stremailid :" + Stremailid);

		if(Stremailid.equals(null) || Stremailid.equals("null") || Stremailid == null || Stremailid == "null" || Stremailid =="")
		{
			UserAddress = UserAddress.replace("'","\\'");
			UserLandmark = UserLandmark.replace("'","\\'");

			String strSqlQryuserprof = "insert into batterywale_user_profile(user_id,email_id,mobile_number,password,name,address,address1,zipcode,city,state,mobile_verify_code,creation_date,created_by) values(NULL,'"+UserEmail+"','"+UserMobileNumber+"','"+pw+"','"+UserName+"','"+UserAddress+"','"+UserLandmark+"','"+UserPincode+"','"+UserCity+"','"+UserState+"','"+UserMobileNumberOTP+"',now(),'ngit')";

			LogLevel.DEBUG(5,new Throwable()," : "+strSqlQryuserprof);
			int reslt1 = qm.executeUpdate(strSqlQryuserprof);
		}
		else
		{
			String strSqlQryupdatepassword = "update batterywale_user_profile set password='"+pw+"' where email_id='"+UserEmail+"'";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQryupdatepassword : "+strSqlQryupdatepassword);
			int reslt12 = qm.executeUpdate(strSqlQryupdatepassword);
		}

			/* code to send SMS and Email new user signup details to consumer */ 

			/*String ThankUMsguser="Please visit www.bookbattery.com with login name: "+UserEmail+" and Password: "+pw+" to know interesting news related to automobiles.";
			String strSMSResponse2=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUMsguser, UserMobileNumber);
			LogLevel.DEBUG(5, new Throwable(), "strSMSResponse2 :" + strSMSResponse2);
			String strsub11="Dear "+UserName+",\r\n\r\n"+""+ThankUMsguser+"";
			String strThanks11="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
			MailTrans retdetails1=new MailTrans();
			retdetails1.setStrSmtpHost(strdomainname);
			retdetails1.setStrFrom(FromEmailId);
			retdetails1.setStrTo(UserEmail);
			retdetails1.setStrSubject("Thank You for Ordering the Inverter.");
			String activateLink12 = strsub11+"\r\n\r\n"+strThanks11+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
			LogLevel.DEBUG(5, new Throwable(), "activateLink12 :" + activateLink12);
			retdetails1.setStrText(activateLink12);
			Thread mt11=new MailThread(retdetails1,"");
			mt11.start();	*/		
				if(UserInverterBatteryModel.equals("0") || UserInverterBatteryModel.equals("") || UserInverterBatteryModel == "")
				{
					if(ProductBrand.equals("Amaron"))
					{
						portalname="BookBattery";
					}
					else
					{
						portalname="BookBattery";
					}
					
					StringBuilder result = new StringBuilder();
					URL url = new URL(""+baseUrl+"/bookbattery/servlet/UserLoginServlet?hidWhatToDo=loginwithanotherserver&email="+UserEmail+"&mobilenumber="+UserMobileNumber+"&password="+pw+"&username="+UserName+"&city="+UserCity+"&state="+UserState+"&deviceflag=web&producttype=inverter&vehiclename=&vehiclemodel=&productbrand="+ProductBrand+"&productmodel="+ProductModel+"&withoutoldproductprice="+InverterDiscount+"&witholdproductprice=&quantity=1&retailername="+strretname+"&retailermobileno="+strretmobnum+"");
					LogLevel.DEBUG(5, new Throwable(), "result.toString() :" + url);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("POST");
					BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					String line;
					while ((line = rd.readLine()) != null) {
					 result.append(line);
					}
					rd.close();
					   
					LogLevel.DEBUG(5, new Throwable(), "result.toString() :" + result.toString());
					
					
						
				}
				else
				{

					StringBuilder result = new StringBuilder();
					URL url = new URL(""+baseUrl+"/bookbattery/servlet/UserLoginServlet?hidWhatToDo=loginwithanotherserver&email="+UserEmail+"&mobilenumber="+UserMobileNumber+"&password="+pw+"&username="+UserName+"&city="+UserCity+"&state="+UserState+"&deviceflag=web&producttype=inverterandinverterbattery&vehiclename=&vehiclemodel=&productbrand="+ProductBrand+"&productmodel="+ProductModel+"&withoutoldproductprice="+InverterDiscount+"&witholdproductprice=&quantity=1&retailername="+strretname+"&retailermobileno="+strretmobnum+"");
					LogLevel.DEBUG(5, new Throwable(), "result.toString() :" + url);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("POST");
					BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					String line;
					while ((line = rd.readLine()) != null) {
					 result.append(line);
					}
					rd.close();
					   
					LogLevel.DEBUG(5, new Throwable(), "result.toString() :" + result.toString());
					
				}
			
			}	
		}
		catch (Exception e)
		{
			LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
			strRes=strRes;
		}
		return strRes;
	}
	
	/* *************************************************************************************************************\
	* This method used to Find Retailers for Area 
	\* *************************************************************************************************************/
	public String Find_Retailer(HttpServletRequest req , HttpServletResponse res,HttpSession session) 
	{ 	
		String UserArea= (req.getParameter("UAR")!=null)?(req.getParameter("UAR")):"";
		String UserPincode= (req.getParameter("UPN")!=null)?(req.getParameter("UPN")):"";
		String UserState= (req.getParameter("UST")!=null)?(req.getParameter("UST")):"";
		String UserCity= (req.getParameter("UCT")!=null)?(req.getParameter("UCT")):"";
		String ProductBrand= (req.getParameter("UPB")!=null)?(req.getParameter("UPB")):"";
		
		UserArea =UserArea.replaceAll("\\+", " ");
		UserPincode =UserPincode.replaceAll("\\+", " ");
		UserState =UserState.replaceAll("\\+", " ");
		UserCity =UserCity.replaceAll("\\+", " ");
		ProductBrand =ProductBrand.replaceAll("\\+", " ");
		
		String strRes="";

		String strstate="";
		String strstate1="";
		String StrSqlQry ="";
		String Strretid="";
		String Strlocorpin="";
		String strcustax="";
		String strcusper="";
	
		Hashtable htretailerid =new Hashtable();
		Hashtable Location_HT =new Hashtable();
		try
		{
			UserArea =java.net.URLDecoder.decode(UserArea, "UTF-8");
			UserPincode =java.net.URLDecoder.decode(UserPincode, "UTF-8");
			UserState =java.net.URLDecoder.decode(UserState, "UTF-8");
			UserCity =java.net.URLDecoder.decode(UserCity, "UTF-8");
			ProductBrand =java.net.URLDecoder.decode(ProductBrand, "UTF-8");
			
			
			String Verify_Area_Pincode="";
			
			if(UserArea.equals("0") || UserArea.equals("") || UserArea == "")
			{
				Verify_Area_Pincode = "select COUNT(*) AS Count from battery_pincode where state='"+UserState+"' and city='"+UserCity+"' and pincode='"+UserPincode+"' ";
			}
			else
			{
				Verify_Area_Pincode = "select COUNT(*) AS Count from location_area where state='"+UserState+"' and location='"+UserCity+"' and area='"+UserArea+"' ";
			}
			LogLevel.DEBUG(5, new Throwable(), "Verify_Area_Pincode :" + Verify_Area_Pincode);
			Location_HT = qm.getRow(Verify_Area_Pincode); 
			String Count=String.valueOf(Location_HT.get("Count"));
			LogLevel.DEBUG(5, new Throwable(), "Count :" + Count);
			if(Count.equals(null) || Count.equals("null") || Count.equals("0") || Count == null  || Count == "0" || Count == "null" || Count =="")
			{						
				strRes="Invalid Area or Pincode";  
				return strRes;
			}
			else
			{}
	
			
			if(UserArea.equals("0") || UserArea.equals("") || UserArea == "")
			{
				String strstate2 = UserState;
				strstate=strstate2.trim().replaceAll(" ", "_"); 
				LogLevel.DEBUG(5, new Throwable(), "strstate :" + strstate);
				
				Date now = new Date();
				SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely
				//System.out.println(simpleDateformat.format(now));
				LogLevel.DEBUG(5, new Throwable(), "Day Info :" + simpleDateformat.format(now));
				String day =simpleDateformat.format(now);
				LogLevel.DEBUG(5, new Throwable(), "Day Info String:" + day);
			
				if(day.equals("Sunday"))
				{
					String StrSqlQry13 = "select retailer_id from retailer_location_mapping where pincode='"+UserPincode+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='Yes' limit 1";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQry13 :" + StrSqlQry13);
					Hashtable htretailerid12 = qm.getRow(StrSqlQry13); 
					if(htretailerid12.isEmpty())
					{
						StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+UserPincode+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='No' limit 1";
						LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
					}
					else
					{
						StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+UserPincode+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='Yes' limit 1";
						LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
					}
				}
				else
				{
					StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+UserPincode+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='No' limit 1";
						LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
				}

				htretailerid = qm.getRow(StrSqlQry); 
				Strretid=String.valueOf(htretailerid.get("retailer_id"));
				LogLevel.DEBUG(5, new Throwable(), "Strretid :" + Strretid);
				Strlocorpin = UserPincode;

				}
				else
				{
					String StrSqlQrystate = "select state from search_whereever_keywords where city='"+UserCity+"'";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQrystate :" + StrSqlQrystate); 

					Hashtable htgetstate = qm.getRow(StrSqlQrystate); 
					strstate1=(String)htgetstate.get("state");

					strstate=strstate1.trim().replaceAll(" ", "_"); 
					LogLevel.DEBUG(5, new Throwable(), "strstate :" + strstate);
					Date now = new Date();
					SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely
					//System.out.println(simpleDateformat.format(now));
					LogLevel.DEBUG(5, new Throwable(), "Day Info :" + simpleDateformat.format(now));
					String day =simpleDateformat.format(now);
					LogLevel.DEBUG(5, new Throwable(), "Day Info String:" + day);
				
					if(day.equals("Sunday"))
					{

						String StrSqlQry12 = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+UserCity+"' and area='"+UserArea+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='Yes' limit 1";
						LogLevel.DEBUG(5, new Throwable(), "StrSqlQry12 :" + StrSqlQry12);
						Hashtable htretailerid1 = qm.getRow(StrSqlQry12); 
						if(htretailerid1.isEmpty())
						{
							StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+UserCity+"' and area='"+UserArea+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='No' limit 1";
							LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);

						}
						else
						{

							StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+UserCity+"' and area='"+UserArea+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='Yes' limit 1";
							LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
						}
					}
					else
					{
						StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+UserCity+"' and area='"+UserArea+"' and bat_brand='"+ProductBrand+"' and weekend_dealer_flag='No' limit 1";
						LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
					}

					htretailerid = qm.getRow(StrSqlQry); 
					Strretid=String.valueOf(htretailerid.get("retailer_id"));
					LogLevel.DEBUG(5, new Throwable(), "Strretid :" + Strretid);
					Strlocorpin = UserCity;
				}
				if(Strretid.equals(null) || Strretid.equals("null") || Strretid == null || Strretid == "null" || Strretid =="")
				{						
					strRes="Sorry";  
				}
				else
				{
					strRes="Congrats";  
				}
		}
		catch (Exception e)
		{
		LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
		strRes=strRes;
		}
		return strRes;
	}
	
	/* **************************************************************************************************************************************\
	* This method is to fetch battery brand.
	* @strSqlQry : carries the query to battery brand from battery_details table.
	\* **************************************************************************************************************************************/
	public String Fx_Get_Product_Brand(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
	String product_type= (req.getParameter("product_type")!=null)?(req.getParameter("product_type")):"";
	LogLevel.DEBUG(5, new Throwable(), "product_type :" + product_type);
		try
		{	
			//################### Getting Location From Cookies 
			GetCookie State_City = new GetCookie(qm);
			String State_City_Resp=  State_City.getCookieStateCity(req,res,session);
			LogLevel.DEBUG(5, new Throwable(), "State_City_Resp :" + State_City_Resp);
			
			String[] State_City_Arr=State_City_Resp.split("~");
			String Product_State_Cookie=State_City_Arr[0];
			String Product_City_Cookie=State_City_Arr[1];
			//################### Getting Location From Cookies
			String table_name="batteryprice";
			

			
			String Product_Location_Condition="where a.state='"+Product_State_Cookie.replaceAll("-"," ")+"' and a.city='"+Product_City_Cookie.replaceAll("-"," ")+"'";

			LogLevel.DEBUG(5, new Throwable(), "Product_State_Cookie :" + Product_State_Cookie);
			LogLevel.DEBUG(5, new Throwable(), "Product_City_Cookie :" + Product_City_Cookie);
			LogLevel.DEBUG(5, new Throwable(), "Product_Location_Condition :" + Product_Location_Condition);
			

			//String vehiclename= (req.getParameter("vehiclename")!=null)?(req.getParameter("vehiclename")):"";
			//String bat_id="";
			String battery_brand="";	
			String strSqlQry ="";	
	 		ServletOutputStream out=res.getOutputStream();
			
	 		if(product_type.equals("Inverter") || product_type.equals("Inverter and Battery Combo") )
			{
	 			strSqlQry ="select distinct(bat_brand) from batteryprice where state='Karnataka' and city='Bangalore' order by bat_brand asc";
			}
			else
			{
				strSqlQry ="select distinct(a.bat_brand) from batteryprice a,application_chat_mapping b "+Product_Location_Condition+" and b.bat_type='"+product_type+"' and a.bat_brand=b.bat_brand order by a.bat_brand asc";
			}
	 		
	 		
			
			//strSqlQry ="select distinct(bat_brand) from batteryprice "+Product_Location_Condition+" order by bat_brand asc";

			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
			

			Vector BatteryVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"BatteryVector:"+BatteryVector );

			if(BatteryVector.isEmpty())
			{ 
				out.println("<option value='0'>Oops!! . Some Thing went wrong</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<BatteryVector.size(); i++)
				{
					if(i==0)
					{
						out.print("<option style='font-size:11px;color:#CCCCCC;' value='default'>&nbsp;Select&nbsp;Brand</option>");
						out.print("<option style='' value='All'>All</option>");
					}
					
					Hashtable ht1=(Hashtable)BatteryVector.get(i);
					//bat_id =String.valueOf(ht1.get("bat_id"));
					battery_brand =String.valueOf(ht1.get("bat_brand"));
					out.print("<option  value='"+battery_brand+"'>"+battery_brand+"</option>"); 
				}
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




