/***********************************************************************		
	NGIT Confidential.   
	@File Name   : Checkout.java 
	@Description : This Servlet is used for Orders insertion to DB and Payment Gateway
	@Author	     : Sai Krishna Daddala
	@Date        : 23rd Jan 2017
******************************************************************/ 
 
package com.ngit.servlets.consumers.batterystore; 

import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.consumers.products.Order_SMS;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.mail.*;
import com.ngit.javabean.sendsms.SendSMS;
import com.ngit.javabean.consumers.products.CompareTrans;
import com.ngit.javabean.consumers.products.GenerateExcelinvoice;
import com.ngit.javabean.consumers.products.GetCookie;
import com.instamojo.wrapper.api.OnlinePayment;
import net.sf.json.*; 
import net.sf.ezmorph.*; 


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
import java.net.URLDecoder;  
import java.net.URLEncoder; 


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
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.HttpURLConnection;


public class Checkout extends HttpServlet 
{  
	private ServletContext context; 
	QueryManager qm;
	static final long serialVersionUID=21;
	public static final int PASSWORD_LENGTH = 8;
	private static final Random RANDOM = new SecureRandom();
	
	String baseUrl="";
	String serverURL="";
	String OperatorTeamCount="";
	String strIpAddress ="";
	String strPort="";
	String SMSFromAddress="";

	String baseurldirectory ="";
	
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
		
		strIpAddress =(propsMOPConfig.getProperty("TRANSMITTER_IP_ADDR")!=null)?(propsMOPConfig.getProperty("TRANSMITTER_IP_ADDR")):"";
		strPort=(propsMOPConfig.getProperty("TRANSMITTER_IP_PORT")!=null)?(propsMOPConfig.getProperty("TRANSMITTER_IP_PORT")):"9910";
		SMSFromAddress=(propsMOPConfig.getProperty("SMSFromAddress")!=null)?(propsMOPConfig.getProperty("SMSFromAddress")):"";
		
		
		String domainname = (propsMOPConfig.getProperty("domainname")!=null)?(propsMOPConfig.getProperty("domainname")).trim():"bookbattery.com";
		baseUrl =  propsMOPConfig.getProperty("publicUrl");
		serverURL =  propsMOPConfig.getProperty("serverURL");

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
		* This action is used to Insert Inverter and Battery data in to DB
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("Generate_Payment_Link"))
		{ 
			try
			{
				String strRes=Generate_Payment_Link(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("Get_Transaction_Details"))
		{ 
			try
			{
				String strRes=Get_Transaction_Details(req,res,session);
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
				String strRes=InsertOrderDetails_Inverter(req,res,session,domainname,strIpAddress,strPort,SMSFromAddress,FromEmailId,strsuppemaild,baseUrl,strsuppmobnumber,strCMSSERVERIP,strPDFFilePath,strPDFRelFilePath,baseurldirectory,OperatorTeamCount);
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
				String strRes=InsertOrderDetails_Combo(req,res,session,domainname,strIpAddress,strPort,SMSFromAddress,FromEmailId,strsuppemaild,baseUrl,strsuppmobnumber,strCMSSERVERIP,strPDFFilePath,strPDFRelFilePath,baseurldirectory,OperatorTeamCount);
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


	/* *************************************************************************************************************\
	* This method is used to insert Consumer Battery Order Details in DB
	\* *************************************************************************************************************/
	public String Generate_Payment_Link(HttpServletRequest req , HttpServletResponse res,HttpSession session) 
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
		String ProductBatteryType= (req.getParameter("OBT")!=null)?(req.getParameter("OBT")):"";
		String ProductQuality= (req.getParameter("OPQ")!=null)?(req.getParameter("OPQ")):"";
		
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
		ProductBatteryType =ProductBatteryType.replaceAll("\\+", " ");
		ProductQuality =ProductQuality.replaceAll("\\+", " ");
	
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
			String Order_Number_Generate = "";
			Order_Number_Generate = Integer.toString(num);
				
			OnlinePayment Create_Payment_Link = new OnlinePayment(qm);
			LogLevel.DEBUG(5, new Throwable(), "Create_Payment_Link :" + Create_Payment_Link);
			String Create_Payment_Link_Resp=  Create_Payment_Link.getPaymentLink(req,res,session,UserName,UserEmail,UserMobileNumber,WithoutOldBatteryPrice,Order_Number_Generate);
			LogLevel.DEBUG(5, new Throwable(), "Create_Payment_Link_Resp :" + Create_Payment_Link_Resp);
			// Create_Payment_Link_Resp= Create_Payment_Link_Resp.replaceAll("CreatePaymentOrderResponse", "");
			// JSONObject json = JSONSerializer.toJSON(Create_Payment_Link_Resp);
			// JSONObject json = new JSONObject(Create_Payment_Link_Resp);
			// JSONObject json = JSONObject.fromObject(Create_Payment_Link_Resp);
			
			// JSONObject json = (JSONObject) JSONSerializer.toJSON(Create_Payment_Link_Resp);        
			// JSONObject order_res = json.getJSONObject("order");
			// JSONObject payment_options = json.getJSONObject("payment_options");
			
			// LogLevel.DEBUG(5, new Throwable(), " id: "+ order_res.getString("id"));
			// LogLevel.DEBUG(5, new Throwable(), " payment_options: "+ payment_options.getString("payment_url"));
			
			// strRes=payment_options.getString("payment_url");
			
			strRes=Create_Payment_Link_Resp;
			
			
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
	public String Get_Transaction_Details(HttpServletRequest req , HttpServletResponse res,HttpSession session) 
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
		String ProductBatteryType= (req.getParameter("OBT")!=null)?(req.getParameter("OBT")):"";
		String ProductQuality= (req.getParameter("OPQ")!=null)?(req.getParameter("OPQ")):"";
		
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
		ProductBatteryType =ProductBatteryType.replaceAll("\\+", " ");
		ProductQuality =ProductQuality.replaceAll("\\+", " ");
	
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
			String Order_Number_Generate = "";
			Order_Number_Generate = Integer.toString(num);
				
			OnlinePayment Create_Payment_Link = new OnlinePayment(qm);
			LogLevel.DEBUG(5, new Throwable(), "Create_Payment_Link :" + Create_Payment_Link);
			String Create_Payment_Link_Resp=  Create_Payment_Link.getPaymentLink(req,res,session,UserName,UserEmail,UserMobileNumber,WithoutOldBatteryPrice,Order_Number_Generate);
			LogLevel.DEBUG(5, new Throwable(), "Create_Payment_Link_Resp :" + Create_Payment_Link_Resp);
			Create_Payment_Link_Resp= Create_Payment_Link_Resp.replaceAll("CreatePaymentOrderResponse", "");
			//JSONObject json = JSONSerializer.toJSON(Create_Payment_Link_Resp);
			//JSONObject json = new JSONObject(Create_Payment_Link_Resp);
			// JSONObject json = JSONObject.fromObject(Create_Payment_Link_Resp);
			
			//JSONObject json = (JSONObject) JSONSerializer.toJSON(Create_Payment_Link_Resp);        
			// JSONObject order_res = json.getJSONObject("order");
			// JSONObject payment_options = json.getJSONObject("payment_options");
			
			// LogLevel.DEBUG(5, new Throwable(), " id: "+ order_res.getString("id"));
			// LogLevel.DEBUG(5, new Throwable(), " payment_options: "+ payment_options.getString("payment_url"));
			
			// strRes=payment_options.getString("payment_url");
			
			strRes=Create_Payment_Link_Resp;
			
			
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
		String Order_Battery_Type= (req.getParameter("OBT")!=null)?(req.getParameter("OBT")):"";
		String Product_Quantity= (req.getParameter("OPQ")!=null)?(req.getParameter("OPQ")):"";		
		String Product_Payment_Mode= (req.getParameter("OPM")!=null)?(req.getParameter("OPM")):"";
		String Product_Delivery_Mode= (req.getParameter("ODM")!=null)?(req.getParameter("ODM")):"";	
		String Order_Refference_Code= (req.getParameter("ORC")!=null)?(req.getParameter("ORC")):"";				
		String showdelivery= (req.getParameter("SDY")!=null)?(req.getParameter("SDY")):"";				
		
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
		String Final_Price="";
		
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
		Order_Battery_Type =Order_Battery_Type.replaceAll("\\+", " ");
		Product_Quantity =Product_Quantity.replaceAll("\\+", " ");
		Product_Payment_Mode =Product_Payment_Mode.replaceAll("\\+", " ");
		Product_Delivery_Mode =Product_Delivery_Mode.replaceAll("\\+", " ");
		Order_Refference_Code =Order_Refference_Code.replaceAll("\\+", " ");
	
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
			Order_Battery_Type =java.net.URLDecoder.decode(Order_Battery_Type, "UTF-8");
			Product_Quantity =java.net.URLDecoder.decode(Product_Quantity, "UTF-8");
			Product_Payment_Mode =java.net.URLDecoder.decode(Product_Payment_Mode, "UTF-8");
			Product_Delivery_Mode =java.net.URLDecoder.decode(Product_Delivery_Mode, "UTF-8");
			Order_Refference_Code =java.net.URLDecoder.decode(Order_Refference_Code, "UTF-8");
			LogLevel.DEBUG(5, new Throwable(), "Order_Refference_Code :" + Order_Refference_Code);
			
			String Get_Product_Price_SQL ="select DISTINCT a.batprice_id, a.bat_act_price, a.bat_witbat_price, a.bat_ret_price, a.bat_capacity, a.erp_pre_tax, b.city_percentage from batteryprice a, percentage b where a.bat_brand='"+ProductBrand+"' and a.bat_model='"+ProductModel+"' and a.state='"+UserState+"' and a.city='"+UserCity+"' and a.city=b.city ORDER BY batprice_id DESC LIMIT 1";
			
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
			
			
			int Price_Temp=0;
			if (Order_Battery_Type=="New" || Order_Battery_Type.equals("New"))
			{
				Price_Temp = Integer.parseInt(WithoutOldBatteryPrice);
			}
			else
			{
				Price_Temp = Integer.parseInt(WithOldBatteryPrice);
			}
			int QTY_int = Integer.parseInt(Product_Quantity);
			int Final_Price_Int=Price_Temp*QTY_int;
			
				
				
			int delivery_charge=0;
			if ((ProductType=="Bike Batteries" || ProductType.equals("Bike Batteries"))&&((Product_Delivery_Mode=="Yes") || Product_Delivery_Mode.equals("Yes"))&&((showdelivery=="true") || showdelivery.equals("true")))
			{
				delivery_charge = 150;
				Final_Price_Int=Final_Price_Int+delivery_charge;
			}
			else
			{
				delivery_charge = delivery_charge;
				Final_Price_Int=Final_Price_Int;
				Product_Delivery_Mode="No";
			}	
				
			Final_Price = Integer.toString(Final_Price_Int);
			
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
					String StrSqlQry13 = "select retailer_id from retailer_location_mapping where pincode='"+UserPincode+"' and bat_brand='"+ProductBrand+"'  and weekend_dealer_flag='Yes' limit 1";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQry13 :" + StrSqlQry13);
					Hashtable htretailerid12 = qm.getRow(StrSqlQry13); 
					if(htretailerid12.isEmpty())
					{
						StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+UserPincode+"' and bat_brand='"+ProductBrand+"'  and weekend_dealer_flag='No' limit 1";
						LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
					}
					else
					{
						StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+UserPincode+"' and bat_brand='"+ProductBrand+"'  and weekend_dealer_flag='Yes' limit 1";
						LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
					}
				}
				else
				{
					StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+UserPincode+"' and bat_brand='"+ProductBrand+"'  and weekend_dealer_flag='No' limit 1";
						LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
				}

				htretailerid = qm.getRow(StrSqlQry); 
				Strretid=String.valueOf(htretailerid.get("retailer_id"));
				LogLevel.DEBUG(5, new Throwable(), "Strretid :" + Strretid);
				Strlocorpin = UserPincode;

			}
			else
			{
				
				strstate1=UserState;

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
					String StrSqlQry12 = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+UserCity+"' and area='"+UserArea+"' and bat_brand='"+ProductBrand+"'  and weekend_dealer_flag='Yes' limit 1";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQry12 :" + StrSqlQry12);
					Hashtable htretailerid1 = qm.getRow(StrSqlQry12); 
					if(htretailerid1.isEmpty())
					{
						StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+UserCity+"' and area='"+UserArea+"' and bat_brand='"+ProductBrand+"'  and weekend_dealer_flag='No' limit 1";
						LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
					}
					else
					{
						StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+UserCity+"' and area='"+UserArea+"' and bat_brand='"+ProductBrand+"'  and weekend_dealer_flag='Yes' limit 1";
						LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
					}
				}
				else
				{
					StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+UserCity+"' and bat_brand='"+ProductBrand+"'  and area='"+UserArea+"'  and weekend_dealer_flag='No' limit 1";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
				}

				htretailerid = qm.getRow(StrSqlQry); 
				Strretid=String.valueOf(htretailerid.get("retailer_id"));
				LogLevel.DEBUG(5, new Throwable(), "Strretid :" + Strretid);
				Strlocorpin = UserCity;
			}
			
			
			
			/*############### Asigning Retailer for a Order ################*/
			
		
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
					String Order_Number_Generate = "";
					String Order_Number_Generate1 = Integer.toString(Num);
					Order_Number_Generate ="ORDB"+Last_Order_Count_String+""+Order_Number_Generate1+"B";
					LogLevel.DEBUG(5, new Throwable(), "Order_Number_Generate :" + Order_Number_Generate);
					
				
					if(Pre_Order_Number.equals("Failed"))
					{
						Order_Number_Generate=Order_Number_Generate;
					}
					else
					{
						Order_Number_Generate=Pre_Order_Number;
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
						CustomerAddress=", Address: "+UserAddress+", LandMark: "+UserLandmark+"";
					}
					
					
					
					//######################################## Assigning Order to a Operator ###### Start	####################
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
					//######################################## Assigning Order to a Operator ###### End	####################
			

					String strSqlQry="";
					String payment_mode_temp="";
					String Order_Status_Temp="";
					String User_Address_Landmark=UserAddress+" Landmark : "+UserLandmark;
					User_Address_Landmark = User_Address_Landmark.replace("'","\\'");
					LogLevel.DEBUG(5,new Throwable()," User_Address_Landmark "+User_Address_Landmark);
					
					if(Product_Payment_Mode.equals("Cash On Delivery"))
					{
						payment_mode_temp="Cash";
						Order_Status_Temp="confirmed";
					}
					else if(Product_Payment_Mode.equals("Online Payment After Fitment"))
					{
						payment_mode_temp="Online Payment After Fitment";
						Order_Status_Temp="confirmed";
					}
					else
					{
						payment_mode_temp="Online";
						Order_Status_Temp="Payment In Process";
					}
					LogLevel.DEBUG(5,new Throwable()," Order_Status "+Order_Status_Temp);
					
					if(Pre_Order_Number.equals("Failed"))
					{
						//############### Back Up condition 
						//Worst Software failure. This Condition works when Insert Before order Fails
						//############### By Sai Krishna Daddala
						
						strSqlQry = "insert into battery_order_details(ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_verif_code,retailer_name,retailer_mobilnumber,retailer_emailis,state,city,battery_brand,battery_model,battery_capacity,price,area,pincode,bat_type,veh_name,veh_model,witholdbatprice,creation_date,agent_name,pdfurl,order_status,erp_pre_tax,MRP_Price,city_percentage,consumer_address,quantity,order_type,payment_mode,payment_mode_type,delivery_mode,referred_coupon_code,delivery_charge,confirm_by)values(NULL,'"+Order_Number_Generate+"','"+UserName+"','"+UserMobileNumber+"','"+UserEmail+"','"+UserMobileNumberOTP+"','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+UserState+"','"+UserCity+"','"+ProductBrand+"','"+ProductModel+"','"+ProductCapacity+"','"+WithoutOldBatteryPrice+"','"+UserArea+"','"+UserPincode+"','"+ProductType+"','"+ProductVehicleMake+"','"+ProductVehicleModel+"','"+WithOldBatteryPrice+"',now(),'"+agent_name+"','','"+Order_Status_Temp+"','"+ERP_Pre_TAX+"','"+ProductActualPrice+"','"+City_Percentage+"','"+User_Address_Landmark+"','"+Product_Quantity+"','"+Order_Battery_Type+"','"+payment_mode_temp+"','"+Product_Payment_Mode+"','"+Product_Delivery_Mode+"','"+Order_Refference_Code+"','"+delivery_charge+"','Customer')";
							
						LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);	
					}
					else
					{
						strSqlQry = "UPDATE battery_order_details SET order_number = '"+Order_Number_Generate+"', consumer_name = '"+UserName+"', consumer_mobnumber = '"+UserMobileNumber+"', consumer_emailid = '"+UserEmail+"', consumer_verif_code='"+UserMobileNumberOTP+"', retailer_name = '"+strretname+"', retailer_mobilnumber = '"+strretmobnum+"', retailer_emailis = '"+strretemail+"', city = '"+UserCity+"', battery_brand = '"+ProductBrand+"', battery_model = '"+ProductModel+"', battery_capacity = '"+ProductCapacity+"', price = '"+WithoutOldBatteryPrice+"', area = '"+UserArea+"', pincode = '"+UserPincode+"',bat_type = '"+ProductType+"',veh_name = '"+ProductVehicleMake+"', veh_model = '"+ProductVehicleModel+"', witholdbatprice = '"+WithOldBatteryPrice+"', creation_date = now(), order_status='"+Order_Status_Temp+"', consumer_address='"+User_Address_Landmark+"', quantity='"+Product_Quantity+"', order_type='"+Order_Battery_Type+"', payment_mode='"+payment_mode_temp+"' , payment_mode_type='"+Product_Payment_Mode+"', delivery_mode='"+Product_Delivery_Mode+"',delivery_charge='"+delivery_charge+"',referred_coupon_code='"+Order_Refference_Code+"',confirm_by='Customer' WHERE order_number = '"+Order_Number_Generate+"'";
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
					
					// StringBuilder result = new StringBuilder();
					// URL url = new URL("http://"+serverURL+"/bookbattery/servlet/UserLoginServlet?hidWhatToDo=loginwithanotherserver&email="+UserEmail+"&mobilenumber="+UserMobileNumber+"&password="+pw+"&username="+UserName+"&city="+UserCity+"&state="+UserState+"&deviceflag=web&producttype=battery&vehiclename="+ProductVehicleMake+"&vehiclemodel="+ProductVehicleModel+"&productbrand="+ProductBrand+"&productmodel="+ProductModel+"&withoutoldproductprice="+WithoutOldBatteryPrice+"&witholdproductprice="+WithOldBatteryPrice+"&quantity=1&retailername="+strretname+"&retailermobileno="+strretmobnum+"");
					// LogLevel.DEBUG(5, new Throwable(), "result.toString() :" + url);
					// HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					// conn.setRequestMethod("POST");
					// BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					// String line;
					// while ((line = rd.readLine()) != null) {
					 // result.append(line);
					// }
					// rd.close();
					   
					// LogLevel.DEBUG(5, new Throwable(), "result.toString() :" + result.toString());
					
					
																
						
					if(Product_Payment_Mode.equals("Cash On Delivery") || Product_Payment_Mode.equals("Online Payment After Fitment"))
					{
						//######## Send SMS for ORDER
						Order_SMS Send_Order_SMS_Checkout = new Order_SMS(qm);
						String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,Order_Number_Generate,"Battery","Yes","No","No");
						LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
						//######## Send SMS for ORDER
						
						//######## Send Mail for ORDER
						Order_SMS Send_Order_MAIL_Checkout = new Order_SMS(qm);
						String MAIL_Report=Send_Order_MAIL_Checkout.Send_Order_Mail(req,res,session,Order_Number_Generate,"Battery","Sir","No","No");
						LogLevel.DEBUG(5, new Throwable(), "MAIL_Report :" + MAIL_Report);
						//######## Send Mail for ORDER
						
						strRes=baseUrl+baseurldirectory+"transaction/?id="+Order_Number_Generate;
					}
					else
					{
						
	//*********************################## Online Payment Request Generation Code ###### START #####################*****************//
						OnlinePayment Create_Payment_Link = new OnlinePayment(qm);
						LogLevel.DEBUG(5, new Throwable(), "Create_Payment_Link :" + Create_Payment_Link);
						String Create_Payment_Link_Resp=  Create_Payment_Link.getPaymentLink(req,res,session,UserName,UserEmail,UserMobileNumber,Final_Price,Order_Number_Generate);
						LogLevel.DEBUG(5, new Throwable(), "Create_Payment_Link_Resp :" + Create_Payment_Link_Resp);
						
	//*********************################## Online Payment Request Generation Code ###### END #####################*****************//
	
						if(Create_Payment_Link_Resp.contains("ERROR | -"))
						{
							return Create_Payment_Link_Resp;
						}
						else
						{
							
							//JSONObject json = JSONSerializer.toJSON(Create_Payment_Link_Resp);
							//JSONObject json = new JSONObject(Create_Payment_Link_Resp);
							//JSONObject json = (JSONObject) JSONSerializer.toJSON(Create_Payment_Link_Resp);  
							
							
							// Create_Payment_Link_Resp= Create_Payment_Link_Resp.replaceAll("CreatePaymentOrderResponse", "");
							// JSONObject json = JSONObject.fromObject(Create_Payment_Link_Resp);

							// JSONObject order_res = json.getJSONObject("order");
							// JSONObject payment_options = json.getJSONObject("payment_options");
							
							// LogLevel.DEBUG(5, new Throwable(), " id: "+ order_res.getString("id"));
							// LogLevel.DEBUG(5, new Throwable(), " payment_options: "+ payment_options.getString("payment_url"));
							
							//res.sendRedirect(payment_options.getString("payment_url"));
							//res.setHeader("Location", payment_options.getString("payment_url"));
							strRes=Create_Payment_Link_Resp;
							
						}
					}

					//strRes="Sucessfully | "+Order_Number_Generate+"";
				
				}
			}
		}
		catch (Exception e)
		{
			LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
			strRes="Some thing went wrong, Please contact your DevOps Team";
		}
		return strRes;
	}
		
	
	
/* *************************************************************************************************************\
	* This method is used to insert Consumer Inverter Order Details in DB
	\* *************************************************************************************************************/
	public String InsertOrderDetails_Inverter(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort,String SMSFromAddress, String FromEmailId, String strsuppemaild, String baseUrl, String strsuppmobnumber,String strCMSSERVERIP,String strPDFFilePath,String strPDFRelFilePath,String baseurldirectory,String OperatorTeamCount) 
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
		String Product_Quantity= (req.getParameter("OPQ")!=null)?(req.getParameter("OPQ")):"";		
		String Product_Payment_Mode= (req.getParameter("OPM")!=null)?(req.getParameter("OPM")):"";
		String Order_Refference_Code= (req.getParameter("ORC")!=null)?(req.getParameter("ORC")):"";		
		
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
		String Final_Price="";
		
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
		Product_Quantity =Product_Quantity.replaceAll("\\+", " ");
		Product_Payment_Mode =Product_Payment_Mode.replaceAll("\\+", " ");
		Order_Refference_Code =Order_Refference_Code.replaceAll("\\+", " ");
		
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
			Product_Quantity =java.net.URLDecoder.decode(Product_Quantity, "UTF-8");
			Product_Payment_Mode =java.net.URLDecoder.decode(Product_Payment_Mode, "UTF-8");
			Order_Refference_Code =java.net.URLDecoder.decode(Order_Refference_Code, "UTF-8");
			
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
			
			int ProductDiscountPrice_int_Temp = Integer.parseInt(ProductDiscountPrice);
			int QTY_int = Integer.parseInt(Product_Quantity);
			int Final_Price_Int=ProductDiscountPrice_int_Temp*QTY_int;
			Final_Price = Integer.toString(Final_Price_Int);

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
				String StrSqlQry13 = "select retailer_id from retailer_location_mapping where pincode='"+UserPincode+"' and bat_brand='"+ProductBrand+"'  and weekend_dealer_flag='Yes' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry13 :" + StrSqlQry13);
				Hashtable htretailerid12 = qm.getRow(StrSqlQry13); 
			if(htretailerid12.isEmpty())
			{
				StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+UserPincode+"' and bat_brand='"+ProductBrand+"'  and weekend_dealer_flag='No' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);

			}
			else
				{
					StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+UserPincode+"' and bat_brand='"+ProductBrand+"'  and weekend_dealer_flag='Yes' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);

				}
			}
			else
			{
				StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+UserPincode+"' and bat_brand='"+ProductBrand+"'  and weekend_dealer_flag='No' limit 1";
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

				String StrSqlQry12 = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+UserCity+"' and area='"+UserArea+"' and bat_brand='"+ProductBrand+"'  and weekend_dealer_flag='Yes' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry12 :" + StrSqlQry12);
				Hashtable htretailerid1 = qm.getRow(StrSqlQry12); 
				if(htretailerid1.isEmpty())
				{
					StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+UserCity+"' and area='"+UserArea+"' and bat_brand='"+ProductBrand+"'  and weekend_dealer_flag='No' limit 1";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);

				}
				else
				{

					StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+UserCity+"' and area='"+UserArea+"' and bat_brand='"+ProductBrand+"'  and weekend_dealer_flag='Yes' limit 1";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
				}
			}
			else
			{
				StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+UserCity+"' and area='"+UserArea+"' and bat_brand='"+ProductBrand+"'  and weekend_dealer_flag='No' limit 1";
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
			String Order_Number_Generate = "";
			String Order_Number_Generate1 = Integer.toString(Num);
			Order_Number_Generate ="ORDI"+Last_Order_Count_String+""+Order_Number_Generate1+"B";
			LogLevel.DEBUG(5, new Throwable(), "Order_Number_Generate :" + Order_Number_Generate);
			
			
			if(Pre_Order_Number.equals("Failed"))
			{
				Order_Number_Generate=Order_Number_Generate;
			}
			else
			{
				Order_Number_Generate=Pre_Order_Number;
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
			
			String CustomerAddress="";

			if(UserAddress.equals("") && UserLandmark.equals(""))
			{
				CustomerAddress="";

			}
			else
			{
				CustomerAddress=", Address: "+UserAddress+",LandMark: "+UserLandmark+"";
			}


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
			String payment_mode_temp="";
			String Order_Status_Temp="";
			String User_Address_Landmark=UserAddress+" Landmark : "+UserLandmark;
			User_Address_Landmark = User_Address_Landmark.replace("'","\\'");
			LogLevel.DEBUG(5,new Throwable()," User_Address_Landmark "+User_Address_Landmark);
			
			if(Product_Payment_Mode.equals("Cash On Delivery"))
			{
				payment_mode_temp="Cash";
				Order_Status_Temp="confirmed";
			}
			else if(Product_Payment_Mode.equals("Online Payment After Fitment"))
			{
				payment_mode_temp="Online Payment After Fitment";
				Order_Status_Temp="confirmed";
			}
			else
			{
				payment_mode_temp="Online";
				Order_Status_Temp="Payment In Process";
			}
			LogLevel.DEBUG(5,new Throwable()," Order_Status "+Order_Status_Temp);
			
			if(Pre_Order_Number.equals("Failed"))
			{
				//############### Back Up condition 
				//Worst Software failure. This Condition works when Insert Before order Fails
				//############### By Sai Krishna Daddala
					
				strSqlQry = "insert into inverter_order_details(order_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_verify_code,retailer_name,retailer_mobile_number,retailer_emailid,state,city,inverter_brand,inverter_model,inverter_capacity,price,area,pincode,creation_date,agent_name,order_status,erp_pre_tax,MRP_Price,city_percentage,consumer_address,quantity,payment_mode_type,referred_coupon_code,payment_mode,order_agent_comments,confirm_by)values(NULL,'"+Order_Number_Generate+"','"+UserName+"','"+UserMobileNumber+"','"+UserEmail+"','"+UserMobileNumberOTP+"','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+UserState+"','"+UserCity+"','"+ProductBrand+"','"+ProductModel+"','"+ProductCapacity+"','"+ProductDiscountPrice+"','"+UserArea+"','"+UserPincode+"',now(),'"+agent_name+"','"+Order_Status_Temp+"','"+ERP_Pre_TAX+"','"+ProductActualPrice+"','"+City_Percentage+"','"+User_Address_Landmark+"','"+Product_Quantity+"','"+Product_Payment_Mode+"','"+Order_Refference_Code+"','"+payment_mode_temp+"',' ','Customer')";
			}
			else
			{
				strSqlQry = "UPDATE inverter_order_details SET order_number = '"+Pre_Order_Number+"', consumer_name = '"+UserName+"', consumer_mobnumber = '"+UserMobileNumber+"', consumer_emailid = '"+UserEmail+"', consumer_verify_code = '"+UserMobileNumberOTP+"', retailer_name = '"+strretname+"', retailer_mobile_number = '"+strretmobnum+"', retailer_emailid = '"+strretemail+"', state = '"+UserState+"', city = '"+UserCity+"', inverter_brand = '"+ProductBrand+"', inverter_model = '"+ProductModel+"', inverter_capacity = '"+ProductCapacity+"', price = '"+ProductDiscountPrice+"', area = '"+UserArea+"', pincode = '"+UserPincode+"', creation_date = now(), order_status='"+Order_Status_Temp+"', consumer_address='"+User_Address_Landmark+"', payment_mode='"+payment_mode_temp+"', quantity='"+Product_Quantity+"', payment_mode_type='"+Product_Payment_Mode+"',referred_coupon_code='"+Order_Refference_Code+"', order_agent_comments=' ',confirm_by='Customer' WHERE order_number = '"+Pre_Order_Number+"'";
			}

			LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
			int reslt = qm.executeUpdate(strSqlQry);
		

			String strSqlQryleads ="insert into leads(lead_id,retailer_name,retailer_id,deal_id,email_id,vendor_id,product_id,product_name,mobile_number,vendor_name,promotion_code,price,subcategory_id,category_id,creation_date,created_by,updated_date,updated_by)values(NULL,'"+strretname+"','"+Strretid+"','0','"+UserEmail+"','22','0','undefined','"+UserMobileNumber+"','"+ProductBrand+"','0','0','0','0',now(),'ngit',now(),'ngit')"; 
			LogLevel.DEBUG(5,new Throwable()," :strSqlQryleads "+strSqlQryleads);
			int resltleads = qm.executeUpdate(strSqlQryleads);

				strRes="Sucessfully | "+Order_Number_Generate+""; 

				
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
			// StringBuilder result = new StringBuilder();
			// URL url = new URL("http://"+serverURL+"/bookbattery/servlet/UserLoginServlet?hidWhatToDo=loginwithanotherserver&email="+UserEmail+"&mobilenumber="+UserMobileNumber+"&password="+pw+"&username="+UserName+"&city="+UserCity+"&state="+UserState+"&deviceflag=web&producttype=inverter&vehiclename=&vehiclemodel=&productbrand="+ProductBrand+"&productmodel="+ProductModel+"&withoutoldproductprice="+ProductDiscountPrice+"&witholdproductprice=&quantity=1&retailername="+strretname+"&retailermobileno="+strretmobnum+"");
			// LogLevel.DEBUG(5, new Throwable(), "result.toString() :" + url);
			// HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// conn.setRequestMethod("POST");
			// BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			// String line;
			// while ((line = rd.readLine()) != null) {
			 // result.append(line);
			// }
			// rd.close();
			   
			// LogLevel.DEBUG(5, new Throwable(), "result.toString() :" + result.toString());


				
			if(Product_Payment_Mode.equals("Cash On Delivery")|| Product_Payment_Mode.equals("Online Payment After Fitment"))
			{
				//######## Send SMS for ORDER
				Order_SMS Send_Order_SMS_Checkout = new Order_SMS(qm);
				String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,Order_Number_Generate,"Inverter","Yes","No","No");
				LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
				//######## Send SMS for ORDER
						
				//######## Send Mail for ORDER
				Order_SMS Send_Order_MAIL_Checkout = new Order_SMS(qm);
				String MAIL_Report=Send_Order_MAIL_Checkout.Send_Order_Mail(req,res,session,Order_Number_Generate,"Inverter","Sir","No","No");
				LogLevel.DEBUG(5, new Throwable(), "MAIL_Report :" + MAIL_Report);
				//######## Send Mail for ORDER
				
				strRes=baseUrl+baseurldirectory+"transaction/?id="+Order_Number_Generate;
			}
			else
			{
				
//*********************################## Online Payment Request Generation Code ###### START #####################*****************//
				OnlinePayment Create_Payment_Link = new OnlinePayment(qm);
				LogLevel.DEBUG(5, new Throwable(), "Create_Payment_Link :" + Create_Payment_Link);
				String Create_Payment_Link_Resp=  Create_Payment_Link.getPaymentLink(req,res,session,UserName,UserEmail,UserMobileNumber,Final_Price,Order_Number_Generate);
				LogLevel.DEBUG(5, new Throwable(), "Create_Payment_Link_Resp :" + Create_Payment_Link_Resp);
				
//*********************################## Online Payment Request Generation Code ###### END #####################*****************//

				if(Create_Payment_Link_Resp.contains("ERROR | -"))
				{
					return Create_Payment_Link_Resp;
				}
				else
				{
					
					//JSONObject json = JSONSerializer.toJSON(Create_Payment_Link_Resp);
					//JSONObject json = new JSONObject(Create_Payment_Link_Resp);
					//JSONObject json = (JSONObject) JSONSerializer.toJSON(Create_Payment_Link_Resp);  
					
					
					// Create_Payment_Link_Resp= Create_Payment_Link_Resp.replaceAll("CreatePaymentOrderResponse", "");
					// JSONObject json = JSONObject.fromObject(Create_Payment_Link_Resp);

					// JSONObject order_res = json.getJSONObject("order");
					// JSONObject payment_options = json.getJSONObject("payment_options");
					
					// LogLevel.DEBUG(5, new Throwable(), " id: "+ order_res.getString("id"));
					// LogLevel.DEBUG(5, new Throwable(), " payment_options: "+ payment_options.getString("payment_url"));
					
					//res.sendRedirect(payment_options.getString("payment_url"));
					//res.setHeader("Location", payment_options.getString("payment_url"));
					strRes=Create_Payment_Link_Resp;
					
				}
			}

			//strRes="Sucessfully | "+Order_Number_Generate+"";
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
	public String InsertOrderDetails_Combo(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort,String SMSFromAddress, String FromEmailId, String strsuppemaild, String baseUrl, String strsuppmobnumber,String strCMSSERVERIP,String strPDFFilePath,String strPDFRelFilePath,String baseurldirectory,String OperatorTeamCount) 
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
	String ProductInverterBatteryBrand= (req.getParameter("UIBB")!=null)?(req.getParameter("UIBB")):"";
	LogLevel.DEBUG(5, new Throwable(), "ProductInverterBatteryBrand :" + ProductInverterBatteryBrand);
	String Pre_Order_Number= (req.getParameter("ORD")!=null)?(req.getParameter("ORD")):"";
	String Order_Battery_Type= (req.getParameter("OBT")!=null)?(req.getParameter("OBT")):"";	
	String Product_Payment_Mode= (req.getParameter("OPM")!=null)?(req.getParameter("OPM")):"";	
	String Order_Refference_Code= (req.getParameter("ORC")!=null)?(req.getParameter("ORC")):"";		
		
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
	
	String Order_Number_Generate = "";
	String payment_mode_temp="";
	String Order_Status_Temp="";
	
	String Inverter_Battery_Act_Price="";
	String WithOutOldBatteryPrice="";
	String WithOldBatteryPrice="";
	String City_Percentage="";
	String City_Percentage_Battery="";
	String ERP_Pre_TAX_Inverter="";
	String ERP_Pre_TAX_Battery="";
	String Final_Price="";
	
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
	Order_Battery_Type =Order_Battery_Type.replaceAll("\\+", " ");
	Product_Payment_Mode =Product_Payment_Mode.replaceAll("\\+", " ");
	Order_Refference_Code =Order_Refference_Code.replaceAll("\\+", " ");
	
	UserInverterBatteryModel =UserInverterBatteryModel.replaceAll("\\+", " ");
	ProductInverterBatteryBrand =ProductInverterBatteryBrand.replaceAll("\\+", " ");
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
		Pre_Order_Number =java.net.URLDecoder.decode(Pre_Order_Number, "UTF-8");
		Order_Battery_Type =java.net.URLDecoder.decode(Order_Battery_Type, "UTF-8");
		Product_Payment_Mode =java.net.URLDecoder.decode(Product_Payment_Mode, "UTF-8");
		Order_Refference_Code =java.net.URLDecoder.decode(Order_Refference_Code, "UTF-8");

		UserInverterBatteryModel =java.net.URLDecoder.decode(UserInverterBatteryModel, "UTF-8");
		ProductInverterBatteryBrand =java.net.URLDecoder.decode(ProductInverterBatteryBrand, "UTF-8");
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
		else
		{
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
		
		Final_Price=InverterDiscount;
				
		String Get_Inverter_Battery_Price_SQL ="select DISTINCT a.batprice_id, a.bat_act_price, a.bat_capacity, a.bat_witbat_price, a.bat_ret_price, a.erp_pre_tax, b.city_percentage from batteryprice a, percentage b where a.bat_brand='"+ProductInverterBatteryBrand+"' and a.bat_model='"+UserInverterBatteryModel+"' and a.state='"+UserState+"' and a.city='"+UserCity+"' and a.city=b.city ORDER BY batprice_id DESC LIMIT 1"; 
		LogLevel.DEBUG(5, new Throwable(), "Get_Inverter_Battery_Price_SQL :" + Get_Inverter_Battery_Price_SQL);

		Hashtable Get_Inverter_Battery_Price_HT = qm.getRow(Get_Inverter_Battery_Price_SQL);
		LogLevel.DEBUG(5, new Throwable(), "Get_Inverter_Battery_Price_HT :" + Get_Inverter_Battery_Price_HT);

		if(Get_Inverter_Battery_Price_HT.isEmpty())
		{
			strRes="Session Expired or Server Down. Please regenerate your order.";
			return strRes;
		}
		else
		{
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
			
			City_Percentage_Battery=(String)Get_Inverter_Battery_Price_HT.get("city_percentage");
			LogLevel.DEBUG(5, new Throwable(), "City_Percentage_Battery :" + City_Percentage_Battery);
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
				String StrSqlQry13 = "select retailer_id from retailer_location_mapping where pincode='"+UserPincode+"' and bat_brand='"+ProductBrand+"'  and weekend_dealer_flag='Yes' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry13 :" + StrSqlQry13);
				Hashtable htretailerid12 = qm.getRow(StrSqlQry13); 
			if(htretailerid12.isEmpty())
			{
				StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+UserPincode+"' and bat_brand='"+ProductBrand+"'  and weekend_dealer_flag='No' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);

			}
			else
				{
					StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+UserPincode+"' and bat_brand='"+ProductBrand+"'  and weekend_dealer_flag='Yes' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);

				}
			}
			else
			{
				StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+UserPincode+"' and bat_brand='"+ProductBrand+"'  and weekend_dealer_flag='No' limit 1";
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

				String StrSqlQry12 = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+UserCity+"' and area='"+UserArea+"' and bat_brand='"+ProductBrand+"'   and weekend_dealer_flag='Yes' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry12 :" + StrSqlQry12);
				Hashtable htretailerid1 = qm.getRow(StrSqlQry12); 
				if(htretailerid1.isEmpty())
				{
					StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+UserCity+"' and area='"+UserArea+"' and bat_brand='"+ProductBrand+"'  and weekend_dealer_flag='No' limit 1";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);

				}
				else
				{

					StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+UserCity+"' and area='"+UserArea+"' and bat_brand='"+ProductBrand+"'  and weekend_dealer_flag='Yes' limit 1";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
				}
			}
			else
			{
				StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+UserCity+"' and area='"+UserArea+"' and bat_brand='"+ProductBrand+"'  and weekend_dealer_flag='No' limit 1";
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
			String verificationcode1 = Integer.toString(Num);
			Order_Number_Generate ="ORDI"+Last_Order_Count_String+""+verificationcode1+"B";
			LogLevel.DEBUG(5, new Throwable(), "Order_Number_Generate :" + Order_Number_Generate);

			if(Pre_Order_Number.equals("Failed"))
			{
				Order_Number_Generate=Order_Number_Generate;
			}
			else
			{
				Order_Number_Generate=Pre_Order_Number;
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
				CustomerAddress=", Address: "+UserAddress+", LandMark: "+UserLandmark+"";

			}


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

			String User_Address_Landmark=UserAddress+" Landmark : "+UserLandmark;
			User_Address_Landmark = User_Address_Landmark.replace("'","\\'");
			LogLevel.DEBUG(5,new Throwable()," User_Address_Landmark "+User_Address_Landmark);
			
			if(Product_Payment_Mode.equals("Cash On Delivery"))
			{
				payment_mode_temp="Cash";
				Order_Status_Temp="confirmed";
			}
			else if(Product_Payment_Mode.equals("Online Payment After Fitment"))
			{
				payment_mode_temp="Online Payment After Fitment";
				Order_Status_Temp="confirmed";
			}
			else
			{
				payment_mode_temp="Online";
				Order_Status_Temp="Payment In Process";
			}
			
			LogLevel.DEBUG(5,new Throwable()," Order_Status "+Order_Status_Temp);

				//############## Deduct the combo discount  ##### START
				
				int Battery_Quantity;
				
				String Inverter_Battery_Count="";
				
				String Get_Inverter_Battery_Count_SQL ="select DISTINCT inverter_battery_pcs from inverter_details where inverter_brand='"+ProductBrand+"' and inverter_model='"+ProductModel+"'"; 
				LogLevel.DEBUG(5, new Throwable(), "Get_Inverter_Battery_Count_SQL :" + Get_Inverter_Battery_Count_SQL);

				Hashtable Get_Inverter_Battery_Count_HT = qm.getRow(Get_Inverter_Battery_Count_SQL);
				LogLevel.DEBUG(5, new Throwable(), "Get_Inverter_Battery_Count_HT :" + Get_Inverter_Battery_Count_HT);

				if(Get_Inverter_Battery_Count_HT.isEmpty())
				{
					Battery_Quantity=1;
				}
				else
				{
					
					Inverter_Battery_Count=(String)Get_Inverter_Battery_Count_HT.get("inverter_battery_pcs");
					LogLevel.DEBUG(5, new Throwable(), "Inverter_Battery_Count :" + Inverter_Battery_Count);
					
					Battery_Quantity = Integer.parseInt(Inverter_Battery_Count);
				}

				if(Battery_Quantity > 1)
				{
					String Get_Inv_Combo_Discount_SQL = "select discount_percent from discount_details;"; 
					LogLevel.DEBUG(5, new Throwable(), "Get_Inv_Combo_Discount_SQL :" + Get_Inv_Combo_Discount_SQL);
					
					Hashtable Inverter_Combo_Inv_disc_HT = qm.getRow(Get_Inv_Combo_Discount_SQL);
					
					Float Inverter_Combo_Inv_disc=(Float)Inverter_Combo_Inv_disc_HT.get("discount_percent");
					
					LogLevel.DEBUG(5, new Throwable(), "Inverter_Combo_Inv_disc :" + Inverter_Combo_Inv_disc);
					
					Float FinalInverter_Combo_Inv_disc=1-Inverter_Combo_Inv_disc;
					
					LogLevel.DEBUG(5, new Throwable(), "FinalInverter_Combo_Inv_disc :" + FinalInverter_Combo_Inv_disc);

					InverterDiscount=Integer.toString((int)((Integer.parseInt(InverterDiscount))*FinalInverter_Combo_Inv_disc));
					 
					LogLevel.DEBUG(5, new Throwable(), "InverterDiscount if :" + InverterDiscount);
				}
				else
				{
					InverterDiscount=InverterDiscount;
					
					LogLevel.DEBUG(5, new Throwable(), "InverterDiscount else :" + InverterDiscount);
				}	
				
			//############## Deduct the combo discount  ##### End
	
			if(Pre_Order_Number.equals("Failed"))
			{
				//############### Back Up condition 
				//Worst Software failure. This Condition works when Insert Before order Fails
				//############### By Sai Krishna Daddala
					
				strSqlQry = "insert into inverter_order_details(order_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_verify_code,retailer_name,retailer_mobile_number,retailer_emailid,state,city,inverter_brand,inverter_model,inverter_capacity,price,area,pincode,creation_date,agent_name,order_status,erp_pre_tax,MRP_Price,city_percentage,consumer_address,quantity,payment_mode_type,payment_mode)values(NULL,'"+Order_Number_Generate+"','"+UserName+"','"+UserMobileNumber+"','"+UserEmail+"','"+UserMobileNumberOTP+"','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+UserState+"','"+UserCity+"','"+ProductBrand+"','"+ProductModel+"','"+InverterCapacity+"','"+InverterDiscount+"','"+UserArea+"','"+UserPincode+"',now(),'"+agent_name+"','"+Order_Status_Temp+"','"+ERP_Pre_TAX_Inverter+"','"+InverterPrice+"','"+City_Percentage+"','"+User_Address_Landmark+"','1','"+Product_Payment_Mode+"','"+payment_mode_temp+"')";
			}
			else
			{
				strSqlQry = "UPDATE inverter_order_details SET order_number = '"+Pre_Order_Number+"', consumer_name = '"+UserName+"', consumer_mobnumber = '"+UserMobileNumber+"', consumer_emailid = '"+UserEmail+"', consumer_verify_code = '"+UserMobileNumberOTP+"', retailer_name = '"+strretname+"', retailer_mobile_number = '"+strretmobnum+"', retailer_emailid = '"+strretemail+"', state = '"+UserState+"', city = '"+UserCity+"', inverter_brand = '"+ProductBrand+"', inverter_model = '"+ProductModel+"', inverter_capacity = '"+InverterCapacity+"', price = '"+InverterDiscount+"', area = '"+UserArea+"', pincode = '"+UserPincode+"', creation_date = now(), order_status='"+Order_Status_Temp+"', consumer_address='"+User_Address_Landmark+"', payment_mode='"+payment_mode_temp+"' WHERE order_number = '"+Pre_Order_Number+"'";
			}

			LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
			int reslt = qm.executeUpdate(strSqlQry);


			String strSqlQryleads ="insert into leads(lead_id,retailer_name,retailer_id,deal_id,email_id,vendor_id,product_id,product_name,mobile_number,vendor_name,promotion_code,price,subcategory_id,category_id,creation_date,created_by,updated_date,updated_by)values(NULL,'"+strretname+"','"+Strretid+"','0','"+UserEmail+"','22','0','undefined','"+UserMobileNumber+"','"+ProductBrand+"','0','0','0','0',now(),'ngit',now(),'ngit')"; 
			LogLevel.DEBUG(5,new Throwable()," :strSqlQryleads "+strSqlQryleads);
			int resltleads = qm.executeUpdate(strSqlQryleads);

			
			String verificationcodeinvbat = "";

			if(UserInverterBatteryModel.equals("0") || UserInverterBatteryModel.equals("") || UserInverterBatteryModel == "")
			{
				LogLevel.DEBUG(5, new Throwable(), "battery model :" + UserInverterBatteryModel);
			}
			else
			{
				/*int Battery_Quantity;
				
				String Inverter_Battery_Count="";
				
				String Get_Inverter_Battery_Count_SQL ="select DISTINCT inverter_battery_pcs from inverter_details where inverter_brand='"+ProductBrand+"' and inverter_model='"+ProductModel+"'"; 
				LogLevel.DEBUG(5, new Throwable(), "Get_Inverter_Battery_Count_SQL :" + Get_Inverter_Battery_Count_SQL);

				Hashtable Get_Inverter_Battery_Count_HT = qm.getRow(Get_Inverter_Battery_Count_SQL);
				LogLevel.DEBUG(5, new Throwable(), "Get_Inverter_Battery_Count_HT :" + Get_Inverter_Battery_Count_HT);

				if(Get_Inverter_Battery_Count_HT.isEmpty())
				{
					Battery_Quantity=1;
				}
				else{
					
					Inverter_Battery_Count=(String)Get_Inverter_Battery_Count_HT.get("inverter_battery_pcs");
					LogLevel.DEBUG(5, new Throwable(), "Inverter_Battery_Count :" + Inverter_Battery_Count);
					
					Battery_Quantity = Integer.parseInt(Inverter_Battery_Count);
				}*/

				
				int Price_Temp=0;
				if (Order_Battery_Type=="New" || Order_Battery_Type.equals("New"))
				{
					Price_Temp = Integer.parseInt(WithOutOldBatteryPrice);
				}
				else
				{
					Price_Temp = Integer.parseInt(WithOldBatteryPrice);
				}
				
				int Final_Price_Int=Price_Temp*Battery_Quantity;
				
				int Final_Price_with_Inv_Bat = Integer.parseInt(Final_Price)+Final_Price_Int;
				Final_Price = Integer.toString(Final_Price_with_Inv_Bat);
				
				LogLevel.DEBUG(5, new Throwable(), "Final_Price_with_Inv_Bat :" + Final_Price);
					

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
				
				if(Battery_Quantity > 1)
				{
				
				//############## Update Order Agent Comment  ##### Start
				String Update_Order_Agent_Comments = "UPDATE inverter_order_details SET order_agent_comments = '"+verificationcodeinvbat+" = Inverter and Combo Order with Combo Discount' WHERE order_number = '"+Order_Number_Generate+"'";
				
				int Update_Order_Agent_Comments_Response = qm.executeUpdate(Update_Order_Agent_Comments);
					
				LogLevel.DEBUG(5, new Throwable(), "Update_Order_Agent_Comments_Response :" + Update_Order_Agent_Comments_Response);

				//############## Update Order Agent Comment  ##### End
				
				//############## Deduct the combo discount ##### Start
				
				String Get_Combo_Discount_SQL = "select discount_percent from discount_details;"; 
				LogLevel.DEBUG(5, new Throwable(), "Get_Combo_Discount_SQL :" + Get_Combo_Discount_SQL);
				
				Hashtable Inverter_Combo_disc_HT = qm.getRow(Get_Combo_Discount_SQL);
				
				Float Inverter_Combo_disc=(Float)Inverter_Combo_disc_HT.get("discount_percent");
				
				LogLevel.DEBUG(5, new Throwable(), "Inverter_Combo_disc :" + Inverter_Combo_disc);
				
				Float FinalInverter_Combo_disc=1-Inverter_Combo_disc;
				
				LogLevel.DEBUG(5, new Throwable(), "FinalInverter_Combo_disc :" + FinalInverter_Combo_disc);

				 WithOldBatteryPrice=Integer.toString((int)((Integer.parseInt(WithOldBatteryPrice))*FinalInverter_Combo_disc));
				 
				 LogLevel.DEBUG(5, new Throwable(), "WithOldBatteryPrice :" + WithOldBatteryPrice);			

				 WithOutOldBatteryPrice=Integer.toString((int)((Integer.parseInt(WithOutOldBatteryPrice))*FinalInverter_Combo_disc));
				 
				 LogLevel.DEBUG(5, new Throwable(), "WithOutOldBatteryPrice :" + WithOutOldBatteryPrice);
				
				//############## Deduct the combo discount  ##### End
				
				String strSqlQry123 = "insert into battery_order_details(ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_verif_code,retailer_name,retailer_mobilnumber,retailer_emailis,state,city,battery_brand,battery_model,battery_capacity,price,area,pincode,pdfurl,bat_type,veh_name,veh_model,witholdbatprice,creation_date,agent_name,order_status,erp_pre_tax,MRP_Price,city_percentage,consumer_address,order_agent_comments,quantity,order_type,payment_mode,payment_mode_type,referred_coupon_code)values(NULL,'"+verificationcodeinvbat+"','"+UserName+"','"+UserMobileNumber+"','"+UserEmail+"','"+UserMobileNumberOTP+"','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+UserState+"','"+UserCity+"','"+ProductInverterBatteryBrand+"','"+UserInverterBatteryModel+"','"+InverterBatteryCapacity+"','"+WithOutOldBatteryPrice+"','"+UserArea+"','"+UserPincode+"','','Inverter Batteries','','','"+WithOldBatteryPrice+"',now(),'"+agent_name+"','"+Order_Status_Temp+"','"+ERP_Pre_TAX_Battery+"','"+Inverter_Battery_Act_Price+"','"+City_Percentage_Battery+"','"+User_Address_Landmark+"','"+Order_Number_Generate+" = Inverter and Combo Order with Combo Discount','"+Inverter_Battery_Count+"','"+Order_Battery_Type+"','"+payment_mode_temp+"','"+Product_Payment_Mode+"','"+Order_Refference_Code+"')";
				LogLevel.DEBUG(5,new Throwable()," strSqlQry123: "+strSqlQry123);
				int reslt123 = qm.executeUpdate(strSqlQry123);

				}
				else
				{

				//############## Update Order Agent Comment  ##### Start
				
				
				String Update_Order_Agent_Comments = "UPDATE inverter_order_details SET order_agent_comments = '"+verificationcodeinvbat+" = Inverter and Combo Order' WHERE order_number = '"+Order_Number_Generate+"'";
				
				int Update_Order_Agent_Comments_Response = qm.executeUpdate(Update_Order_Agent_Comments);
					
				LogLevel.DEBUG(5, new Throwable(), "Update_Order_Agent_Comments_Response :" + Update_Order_Agent_Comments_Response);
				
				//############## Update Order Agent Comment  ##### End


				String strSqlQry123 = "insert into battery_order_details(ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_verif_code,retailer_name,retailer_mobilnumber,retailer_emailis,state,city,battery_brand,battery_model,battery_capacity,price,area,pincode,pdfurl,bat_type,veh_name,veh_model,witholdbatprice,creation_date,agent_name,order_status,erp_pre_tax,MRP_Price,city_percentage,consumer_address,order_agent_comments,quantity,order_type,payment_mode,payment_mode_type,referred_coupon_code)values(NULL,'"+verificationcodeinvbat+"','"+UserName+"','"+UserMobileNumber+"','"+UserEmail+"','"+UserMobileNumberOTP+"','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+UserState+"','"+UserCity+"','"+ProductInverterBatteryBrand+"','"+UserInverterBatteryModel+"','"+InverterBatteryCapacity+"','"+WithOutOldBatteryPrice+"','"+UserArea+"','"+UserPincode+"','','Inverter Batteries','','','"+WithOldBatteryPrice+"',now(),'"+agent_name+"','"+Order_Status_Temp+"','"+ERP_Pre_TAX_Battery+"','"+Inverter_Battery_Act_Price+"','"+City_Percentage_Battery+"','"+User_Address_Landmark+"','"+Order_Number_Generate+" = Inverter and Combo Order','"+Inverter_Battery_Count+"','"+Order_Battery_Type+"','"+payment_mode_temp+"','"+Product_Payment_Mode+"','"+Order_Refference_Code+"')";
				LogLevel.DEBUG(5,new Throwable()," strSqlQry123: "+strSqlQry123);
				int reslt123 = qm.executeUpdate(strSqlQry123);

				
				}
				

				String strSqlQryleads123 ="insert into leads(lead_id,retailer_name,retailer_id,deal_id,email_id,vendor_id,product_id,product_name,mobile_number,vendor_name,promotion_code,price,subcategory_id,category_id,creation_date,created_by,updated_date,updated_by)values(NULL,'"+strretname+"','"+Strretid+"','0','"+UserEmail+"','22','0','undefined','"+UserMobileNumber+"','"+ProductBrand+"','0','0','0','0',now(),'ngit',now(),'ngit')"; 
				LogLevel.DEBUG(5,new Throwable()," :strSqlQryleads123 "+strSqlQryleads123);
				int resltleads123 = qm.executeUpdate(strSqlQryleads123);
				
				
				if(Product_Payment_Mode.equals("Cash On Delivery")|| Product_Payment_Mode.equals("Online Payment After Fitment"))
				{
					//######## Send SMS for ORDER
					Order_SMS Send_Order_SMS_Checkout = new Order_SMS(qm);
					String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,Order_Number_Generate,"Inverter","Yes","No","No");
					LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
					//######## Send SMS for ORDER
					
					//######## Send Mail for ORDER
					Order_SMS Send_Order_MAIL_Checkout = new Order_SMS(qm);
					String MAIL_Report=Send_Order_MAIL_Checkout.Send_Order_Mail(req,res,session,Order_Number_Generate,"Inverter","Sir","No","No");
					LogLevel.DEBUG(5, new Throwable(), "MAIL_Report :" + MAIL_Report);
					//######## Send Mail for ORDER
					
					//######## Send SMS for ORDER
					Order_SMS Send_Order_SMS_Checkout_INVB = new Order_SMS(qm);
					String SMS_Report_INVB=Send_Order_SMS_Checkout_INVB.Send_Order_SMS(req,res,session,verificationcodeinvbat,"Battery","Yes","No","No");
					LogLevel.DEBUG(5, new Throwable(), "SMS_Report_INVB :" + SMS_Report_INVB);
					//######## Send SMS for ORDER
					
					//######## Send Mail for ORDER
					Order_SMS Send_Order_MAIL_Checkout_INVB = new Order_SMS(qm);
					String MAIL_Report_INVB=Send_Order_MAIL_Checkout_INVB.Send_Order_Mail(req,res,session,verificationcodeinvbat,"Battery","Sir","No","No");
					LogLevel.DEBUG(5, new Throwable(), "MAIL_Report_INVB :" + MAIL_Report_INVB);
					//######## Send Mail for ORDER
					
					strRes=baseUrl+baseurldirectory+"transaction/?id="+Order_Number_Generate;
				}
				else
				{
									
					//*********************################## Online Payment Request Generation Code ###### START #####################*****************//
					OnlinePayment Create_Payment_Link = new OnlinePayment(qm);
					LogLevel.DEBUG(5, new Throwable(), "Create_Payment_Link :" + Create_Payment_Link);
					String Create_Payment_Link_Resp=  Create_Payment_Link.getPaymentLink(req,res,session,UserName,UserEmail,UserMobileNumber,Final_Price,Order_Number_Generate);
					LogLevel.DEBUG(5, new Throwable(), "Create_Payment_Link_Resp :" + Create_Payment_Link_Resp);
									
					//*********************################## Online Payment Request Generation Code ###### END #####################*****************//

					if(Create_Payment_Link_Resp.contains("ERROR | -"))
					{
						return Create_Payment_Link_Resp;
					}
					else
					{
						
						//JSONObject json = JSONSerializer.toJSON(Create_Payment_Link_Resp);
						//JSONObject json = new JSONObject(Create_Payment_Link_Resp);
						//JSONObject json = (JSONObject) JSONSerializer.toJSON(Create_Payment_Link_Resp);  
						
						
						// Create_Payment_Link_Resp= Create_Payment_Link_Resp.replaceAll("CreatePaymentOrderResponse", "");
						// JSONObject json = JSONObject.fromObject(Create_Payment_Link_Resp);

						// JSONObject order_res = json.getJSONObject("order");
						// JSONObject payment_options = json.getJSONObject("payment_options");
						
						// LogLevel.DEBUG(5, new Throwable(), " id: "+ order_res.getString("id"));
						// LogLevel.DEBUG(5, new Throwable(), " payment_options: "+ payment_options.getString("payment_url"));
						
						//res.sendRedirect(payment_options.getString("payment_url"));
						//res.setHeader("Location", payment_options.getString("payment_url"));
						strRes=Create_Payment_Link_Resp;
						
					}
				}
				
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
				
			}	
		}
		catch (Exception e)
		{
			LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
			strRes=strRes;
		}
		return strRes;
	}
	
	
		
}// end of class




