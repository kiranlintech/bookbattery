/***********************************************************************		
	NGIT Confidential. 
	@File Name   : Functions.java 
	@Description : This Servlet is used for some functions and valadations.
	@Author	     : Sai Krishna Daddala
	@Date        : 03th Apr 2017
******************************************************************/ 

 
package com.ngit.servlets.consumers.servicestore; 
 
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.mail.*;
import com.ngit.javabean.sendsms.SendSMS;
import com.ngit.javabean.consumers.products.GenerateExcelinvoice;
import com.ngit.javabean.consumers.products.GetCookie;
import com.ngit.javabean.consumers.products.Order_Service_SMS;
import com.instamojo.wrapper.api.OnlinePaymentService;
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
  
public class Functions_Service extends HttpServlet 
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
		String struserName=(String)session.getAttribute("sesBatteryUserLogin"); 
		LogLevel.DEBUG(5,new Throwable(),"struserName :"+struserName );
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
		* This action is used to Insert order details in to DB
		\* *****************************************************************************************/
		if(strWhatToDo.equalsIgnoreCase("Book_A_Service_Online"))
		{ 
			try
			{
				String strRes=Book_A_Service_Online(req,res,session,domainname,strIpAddress,strPort,SMSFromAddress,FromEmailId,strsuppemaild,baseUrl,strsuppmobnumber,strCMSSERVERIP,strPDFFilePath,strPDFRelFilePath,baseurldirectory,OperatorTeamCount);
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
		* This action is used to get vehicle name.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("Get_Vehicle_Make"))
			{ 
			try
			{
				String strRes=Get_Vehicle_Make(req,res,session);
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
		* This action is used to get vehicle models.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("Get_Vehicle_Model"))
			{ 
			try
			{
				String strRes=Get_Vehicle_Model(req,res,session);
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
		* This action is used to get Service Type List.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("Get_Service_Type_List"))
			{ 
			try
			{
				String strRes=Get_Service_Type_List(req,res,session);
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
		* This action is used to get Service Type List.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("Get_Service_Package_List"))
			{ 
			try
			{
				String strRes=Get_Service_Package_List(req,res,session);
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
		* This action is used to get Service list of services Random
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("Get_ServiceList_Random"))
			{ 
			try
			{
				String strRes=Get_ServiceList_Random(req,res,session);
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
	* This method is used to send verification to user mobile number when user enters mobile number in Ordering the Service 
	\* *************************************************************************************************************/
	public String GetVerificationSms(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort,String SMSFromAddress) 
	{ 	
		String UserMobileNumber = (req.getParameter("UserMobileNumber")!=null)?(req.getParameter("UserMobileNumber")):"0";
		LogLevel.DEBUG(5, new Throwable(), "UserMobileNumber :" + UserMobileNumber);
		
		String UserMobileNumber_Coo="";
		String UserMobileNumber_code_Coo="";


		String strRes="";
		try
		{
			Cookie[] cookies = req.getCookies();
			if (cookies != null) {
			 for (Cookie cookie : cookies) {
				 
			   if (cookie.getName().equals("UserMobileNumber_Coo")) {
					UserMobileNumber_Coo=cookie.getValue();
				}	 
			   if (cookie.getName().equals("UserMobileNumber_code_Coo")) {
					UserMobileNumber_code_Coo=cookie.getValue();
				}
				
			  }
			}
			LogLevel.DEBUG(5, new Throwable(), "UserMobileNumber_Coo :" + UserMobileNumber_Coo);
			LogLevel.DEBUG(5, new Throwable(), "UserMobileNumber_code_Coo :" + UserMobileNumber_code_Coo);
			if(UserMobileNumber.length() < 10)
			{
				strRes="Opps, Failed to send SMS";
				return strRes;
			}
			else if(UserMobileNumber_Coo.equals(UserMobileNumber) && !UserMobileNumber_code_Coo.isEmpty())
			{
			
				SendSMS sendsms = new SendSMS(qm);
				String ThankUMsg="Your Verification Code is "+UserMobileNumber_code_Coo+"";
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
					strRes=UserMobileNumber_code_Coo;
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
					Cookie UserMobileNumber_Cookie = new Cookie("UserMobileNumber_Coo",UserMobileNumber);
					Cookie UserMobileNumber_code_Cookie = new Cookie("UserMobileNumber_code_Coo",verificationcode);
					
					// Set expiry date after 15 min for both the cookies.
					UserMobileNumber_Cookie.setMaxAge(60*15); 
					UserMobileNumber_code_Cookie.setMaxAge(60*15); 
					
					// Set path for cookies.
					UserMobileNumber_Cookie.setPath("/");
					UserMobileNumber_code_Cookie.setPath("/");
					
					
					// Add both the cookies in the response header.
					res.addCookie( UserMobileNumber_Cookie );
					res.addCookie( UserMobileNumber_code_Cookie );
					// Set response content type
					res.setContentType("text/html");
			
					strRes=verificationcode;
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
		String ProductServiceType= (req.getParameter("USeT")!=null)?(req.getParameter("USeT")):"";
		String ProductServicePackage= "Battery Service";
		String ProductServicePlace= (req.getParameter("USP")!=null)?(req.getParameter("USP")):"";
		String ProductVehicleMake= (req.getParameter("UVMK")!=null)?(req.getParameter("UVMK")):"";
		String ProductVehicleModel= (req.getParameter("UVMD")!=null)?(req.getParameter("UVMD")):"";
		String UserState= (req.getParameter("UST")!=null)?(req.getParameter("UST")):"";
		String UserCity= (req.getParameter("UCT")!=null)?(req.getParameter("UCT")):"";
		LogLevel.DEBUG(5, new Throwable(), "UserCity :" + UserCity);
		String UserMRP= (req.getParameter("UMRP")!=null)?(req.getParameter("UMRP")):"";
		LogLevel.DEBUG(5, new Throwable(), "UserMRP :" + UserMRP);
		String UserDP= (req.getParameter("UDP")!=null)?(req.getParameter("UDP")):"";
		LogLevel.DEBUG(5, new Throwable(), "UserDP :" + UserDP);
		String UQTY= (req.getParameter("UQTY")!=null)?(req.getParameter("UQTY")):"";
		LogLevel.DEBUG(5, new Throwable(), "UQTY :" + UQTY);
		String UPT= (req.getParameter("UPT")!=null)?(req.getParameter("UPT")):"";
		LogLevel.DEBUG(5, new Throwable(), "UPT :" + UPT);

		String agent_name ="";
		//password = password.replace("\\","\\\\"); 

		String strRes="";
		
		String ProductMRPPrice="";
		String ProductDiscountPrice="";
		
		UserMobileNumber =UserMobileNumber.replaceAll("\\+", " ");
		ProductServiceType =ProductServiceType.replaceAll("\\+", " ");
		ProductServicePackage =ProductServicePackage.replaceAll("\\+", " ");
		ProductServicePlace =ProductServicePlace.replaceAll("\\+", " ");
		ProductVehicleMake =ProductVehicleMake.replaceAll("\\+", " ");
		ProductVehicleModel =ProductVehicleModel.replaceAll("\\+", " ");
		UserState =UserState.replaceAll("\\+", " ");
		UserCity =UserCity.replaceAll("\\+", " ");
		UPT =UPT.replaceAll("\\+", " ");

	
		Hashtable htretailerid =new Hashtable();
		try
		{
			UserMobileNumber =java.net.URLDecoder.decode(UserMobileNumber, "UTF-8");
			ProductServiceType =java.net.URLDecoder.decode(ProductServiceType, "UTF-8");
			ProductServicePackage =java.net.URLDecoder.decode(ProductServicePackage, "UTF-8");
			ProductServicePlace =java.net.URLDecoder.decode(ProductServicePlace, "UTF-8");
			ProductVehicleMake =java.net.URLDecoder.decode(ProductVehicleMake, "UTF-8");
			ProductVehicleModel =java.net.URLDecoder.decode(ProductVehicleModel, "UTF-8");
			UserState =java.net.URLDecoder.decode(UserState, "UTF-8");
			UserCity =java.net.URLDecoder.decode(UserCity, "UTF-8");
			UPT =java.net.URLDecoder.decode(UPT, "UTF-8");
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
			Order_Number ="ORD"+DB_Orders_Cap+""+Last_Order_Count_String+""+verificationcode1+"S";
			LogLevel.DEBUG(5, new Throwable(), "Order_Number :" + Order_Number);
			
			
			String strSqlQry="";

				//ProductMRPPrice=(String)Services_Package_Prices_HT.get("service_price_mrp");
				ProductMRPPrice=UserMRP;
				LogLevel.DEBUG(5, new Throwable(), "ProductMRPPrice :" + ProductMRPPrice);
				
				//ProductDiscountPrice=(String)Services_Package_Prices_HT.get("service_price_discount");
				ProductDiscountPrice=UserDP;
				LogLevel.DEBUG(5, new Throwable(), "ProductDiscountPrice :" + ProductDiscountPrice);
				
				
				if(UPT.equals("Inverter Batteries"))
				{
					if(Integer.parseInt(UQTY)==1 || UQTY.equals("1") || UQTY.equals(1))
					{
						ProductDiscountPrice=ProductDiscountPrice;
						ProductMRPPrice=ProductMRPPrice;
						LogLevel.DEBUG(5, new Throwable(), "ProductDiscountPrice when 1 :" + ProductDiscountPrice);
					}
					else
					{
						int ProductDiscountPrice_int=599+(300*(Integer.parseInt(UQTY)));
						ProductDiscountPrice_int= ProductDiscountPrice_int - 300;
						LogLevel.DEBUG(5, new Throwable(), "ProductDiscountPrice when more than 1 :" + ProductDiscountPrice_int);
						ProductDiscountPrice=String.valueOf(ProductDiscountPrice_int);
						LogLevel.DEBUG(5, new Throwable(), "ProductDiscountPrice when more than 1 :" + ProductDiscountPrice);						
						
						int ProductMRPPrice_int=700+(300*(Integer.parseInt(UQTY)));
						ProductMRPPrice_int= ProductMRPPrice_int - 300;
						LogLevel.DEBUG(5, new Throwable(), "ProductMRPPrice when more than 1 :" + ProductMRPPrice_int);
						ProductMRPPrice=String.valueOf(ProductMRPPrice_int);
						LogLevel.DEBUG(5, new Throwable(), "ProductMRPPrice when more than 1 :" + ProductMRPPrice);
					}
				}
				else
				{
					ProductDiscountPrice=ProductDiscountPrice;
					ProductMRPPrice=ProductMRPPrice;
				}
				

			
			
			
			//#### In changes in bellow condition, You need to Update the same in InsertOrderDetails_XXXXX Condition #####
			strSqlQry = "insert into service_order_details(ord_id,order_number,consumer_mobnumber,consumer_verif_code,services_type,services_place,services_package,service_price_mrp,service_price_discount,veh_name,veh_model,creation_date,agent_name,order_status,state,city,created_by,operator,quantity,batterywale_order,product_type,order_agent_comments)values(NULL,'"+Order_Number+"','"+UserMobileNumber+"','0','"+ProductServiceType+"','"+ProductServicePlace+"','"+ProductServicePackage+"','"+ProductMRPPrice+"','"+ProductDiscountPrice+"','"+ProductVehicleMake+"','"+ProductVehicleModel+"',now(),'"+agent_name+"','Not Confirmed','"+UserState+"','"+UserCity+"','ngit','Customer','"+UQTY+"','No','"+UPT+"','')";
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
	* This method used to Find Retailers for Area 
	\* *************************************************************************************************************/
	public String Find_Retailer(HttpServletRequest req , HttpServletResponse res,HttpSession session) 
	{ 	
		String UserArea= (req.getParameter("UAR")!=null)?(req.getParameter("UAR")):"";
		String UserPincode= (req.getParameter("UPN")!=null)?(req.getParameter("UPN")):"";
		String UserState= (req.getParameter("UST")!=null)?(req.getParameter("UST")):"";
		String UserCity= (req.getParameter("UCT")!=null)?(req.getParameter("UCT")):"";
		String ServiceType= (req.getParameter("USeT")!=null)?(req.getParameter("USeT")):"";
		String ServicePackage= (req.getParameter("USPT")!=null)?(req.getParameter("USPT")):"";
		
		UserArea =UserArea.replaceAll("\\+", " ");
		UserPincode =UserPincode.replaceAll("\\+", " ");
		UserState =UserState.replaceAll("\\+", " ");
		UserCity =UserCity.replaceAll("\\+", " ");
		ServiceType =ServiceType.replaceAll("\\+", " ");
		ServicePackage =ServicePackage.replaceAll("\\+", " ");
		
		String strRes="";
		String Services_Category_Condition_SQL ="";
	
		Hashtable Retailer_ID_HT =new Hashtable();
		Hashtable Location_HT =new Hashtable();
		try
		{
			UserArea =java.net.URLDecoder.decode(UserArea, "UTF-8");
			UserPincode =java.net.URLDecoder.decode(UserPincode, "UTF-8");
			UserState =java.net.URLDecoder.decode(UserState, "UTF-8");
			UserCity =java.net.URLDecoder.decode(UserCity, "UTF-8");
			ServiceType =java.net.URLDecoder.decode(ServiceType, "UTF-8");
			ServicePackage =java.net.URLDecoder.decode(ServicePackage, "UTF-8");
			
			
			String Verify_Area_Pincode="";
			
			if(ServiceType.equals("Exterior and Interior Cleaning") && ServicePackage.contains("Dealer"))
			{
				Services_Category_Condition_SQL="and services_category='Dealer'";
			}
			else if(ServiceType.equals("Exterior and Interior Cleaning") && ServicePackage.contains("Door Service"))
			{
				Services_Category_Condition_SQL="and services_category='Door Service'";
			}
			else 
			{
				Services_Category_Condition_SQL="";
			}
			
			
			if(UserArea.equals("0") || UserArea.equals("") || UserArea == "")
			{
				//Verify_Area_Pincode = "and pincode='"+UserPincode+"' ";
				Verify_Area_Pincode = "pincode='"+UserPincode+"' ";
			}
			else
			{
				//Verify_Area_Pincode = "and area='"+UserArea+"' ";
				Verify_Area_Pincode = "state='"+UserState+"' and city='"+UserCity+"' and area='"+UserArea+"' ";
			}
			
			String Find_A_Retailer = "select retailer_id from retailer_location_mapping where "+Verify_Area_Pincode+" and ( weekend_dealer_flag='Yes' or weekend_dealer_flag='No' ) limit 1";
			//String Find_A_Retailer = "select retailer_id from retailer_location_mapping where state='"+UserState+"' and city='"+UserCity+"' "+Verify_Area_Pincode+" and ( weekend_dealer_flag='Yes' or weekend_dealer_flag='No' ) limit 1";
		
			LogLevel.DEBUG(5, new Throwable(), "Find_A_Retailer :" + Find_A_Retailer);
			
			Retailer_ID_HT = qm.getRow(Find_A_Retailer); 
			String Retailer_ID_RES=String.valueOf(Retailer_ID_HT.get("retailer_id"));
			LogLevel.DEBUG(5, new Throwable(), "Retailer_ID_RES :" + Retailer_ID_RES);
			
			if(Retailer_ID_RES.equals(null) || Retailer_ID_RES.equals("null") || Retailer_ID_RES == null || Retailer_ID_RES == "null" || Retailer_ID_RES =="")
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
	
	
	/* **********************************************************************************************\
	* This method is to get vehicle name.
	* @strSqlQry : carries the query to fetch vehicle name details from vehicle_details table.
	\* ***********************************************************************************************************/
	public String Get_Vehicle_Make(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		try
		{	
			
	 		ServletOutputStream out=res.getOutputStream();

			String Get_Vehical_Details_SQL = "select distinct(vehicle_name) from vehicle_details order by vehicle_name asc"; 
			LogLevel.DEBUG(5, new Throwable(), "Get_Vehical_Details_SQL :" + Get_Vehical_Details_SQL);
			
			Vector Get_Vehical_Details_Vector=qm.executeQuery(Get_Vehical_Details_SQL);
			LogLevel.DEBUG(5,new Throwable(),"Get_Vehical_Details_Vector:"+Get_Vehical_Details_Vector);

			
			if(Get_Vehical_Details_Vector.isEmpty())
			{ 
				out.println("<option value='0'>Oops, Something Went Wrong</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<Get_Vehical_Details_Vector.size(); i++)
					{
						if(i==0)
						{
							out.print("<option value='0' style='font-size: 13px;font-weight: 700;color: #757575;'>Select Vehicle Make</option>");
						}
							Hashtable Get_Vehical_Details_Ht=(Hashtable)Get_Vehical_Details_Vector.get(i);
							String Vehicle_Name =String.valueOf(Get_Vehical_Details_Ht.get("vehicle_name"));
							LogLevel.DEBUG(5,new Throwable(),"Vehicle_Name:"+Vehicle_Name);
							out.print("<option value='"+Vehicle_Name+"'>"+Vehicle_Name+"</option>"); 
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
	
	
	/* **********************************************************************************************\
	* This method is to get vehicle models.
	* @strSqlQry : carries the query to fetch vehicle models details from vehicle_details table.
	\* ***********************************************************************************************************/
	public String Get_Vehicle_Model(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		try
		{	
			String Vehicle_Make= (req.getParameter("Vehicle_Make")!=null)?(req.getParameter("Vehicle_Make")):"";
			LogLevel.DEBUG(5,new Throwable(),"Vehicle_Make:"+Vehicle_Make);
			
	 		ServletOutputStream out=res.getOutputStream();

			String Get_Vehical_Details_SQL = "select distinct(vehicle_model) from vehicle_details where vehicle_name='"+Vehicle_Make+"' order by vehicle_model asc"; 
			LogLevel.DEBUG(5, new Throwable(), "Get_Vehical_Details_SQL :" + Get_Vehical_Details_SQL);
			
			Vector Get_Vehical_Details_Vector=qm.executeQuery(Get_Vehical_Details_SQL);
			LogLevel.DEBUG(5,new Throwable(),"Get_Vehical_Details_Vector:"+Get_Vehical_Details_Vector);

			
			if(Get_Vehical_Details_Vector.isEmpty())
			{ 
				out.println("<option value='0'>Oops, Something Went Wrong</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<Get_Vehical_Details_Vector.size(); i++)
					{
						if(i==0)
						{
							out.print("<option value='0'  style='font-size: 13px;font-weight: 700;color: #757575;'>Select Vehicle Model</option>");
						}
							Hashtable Get_Vehical_Details_Ht=(Hashtable)Get_Vehical_Details_Vector.get(i);
							String Vehicle_Model =String.valueOf(Get_Vehical_Details_Ht.get("vehicle_model"));
							LogLevel.DEBUG(5,new Throwable(),"Vehicle_Model:"+Vehicle_Model);
							out.print("<option value='"+Vehicle_Model+"'>"+Vehicle_Model+"</option>"); 
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
	
	
	
	/* **********************************************************************************************\
	* This method is to get service list
	* @strSqlQry : carries the query to fetch service type list for vehical make and modle details from serviice_detailes table.
	\* ***********************************************************************************************************/
	public String Get_Service_Type_List(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		try
		{	
			String Vehicle_Make= (req.getParameter("Vehicle_Make")!=null)?(req.getParameter("Vehicle_Make")):"";
			LogLevel.DEBUG(5,new Throwable(),"Vehicle_Make:"+Vehicle_Make);
			
			String Vehicle_Model= (req.getParameter("Vehicle_Model")!=null)?(req.getParameter("Vehicle_Model")):"";
			LogLevel.DEBUG(5,new Throwable(),"Vehicle_Model:"+Vehicle_Model);
			
	 		ServletOutputStream out=res.getOutputStream();

			String Get_Services_List_SQL = "select distinct (a.services_type) from services_details a, vehicle_details b where b.vehicle_model='"+Vehicle_Model+"' and b.vehicle_name='"+Vehicle_Make+"'  and ( a.vehicle_price_type=b.vehicle_size or a.vehicle_price_type=b.vehicle_wheelradius or a.services_package=b.vehicle_ACtype or ( a.vehicle_model='"+Vehicle_Model+"' and a.vehicle_make='"+Vehicle_Make+"') ) ORDER BY a.services_type ASC"; 
			LogLevel.DEBUG(5,new Throwable(),"Get_Services_List_SQL:"+Get_Services_List_SQL);
			
			Vector Get_Services_List_Vector=qm.executeQuery(Get_Services_List_SQL);
			LogLevel.DEBUG(5,new Throwable(),"Get_Services_List_Vector:"+Get_Services_List_Vector);
			
			
			if(Get_Services_List_Vector.isEmpty())
			{ 
				out.println("<option value='0'>Service List Not Avaliable</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<Get_Services_List_Vector.size(); i++)
					{
						if(i==0)
						{
							out.print("<option value='0'  style='font-size: 13px;font-weight: 700;color: #757575;'>Select Service Type</option>");
						}
							Hashtable Get_Services_List_HT=(Hashtable)Get_Services_List_Vector.get(i);
							String Services_Type =String.valueOf(Get_Services_List_HT.get("services_type"));
							LogLevel.DEBUG(5,new Throwable(),"Services_Type:"+Services_Type);
							out.print("<option value='"+Services_Type+"'>"+Services_Type+"</option>"); 
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
	
	
	
	/* **********************************************************************************************\
	* This method is to get service list
	* @strSqlQry : carries the query to fetch service type list for vehical make and modle details from serviice_detailes table.
	\* ***********************************************************************************************************/
	public String Get_Service_Package_List(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		String Services_Type_SQL_Condition="";
		try
		{	
			String Vehicle_Make= (req.getParameter("Vehicle_Make")!=null)?(req.getParameter("Vehicle_Make")):"";
			LogLevel.DEBUG(5,new Throwable(),"Vehicle_Make:"+Vehicle_Make);
			
			String Vehicle_Model= (req.getParameter("Vehicle_Model")!=null)?(req.getParameter("Vehicle_Model")):"";
			LogLevel.DEBUG(5,new Throwable(),"Vehicle_Model:"+Vehicle_Model);
			
			String Services_Type= (req.getParameter("Services_Type")!=null)?(req.getParameter("Services_Type")):"";
			LogLevel.DEBUG(5,new Throwable(),"Services_Type:"+Services_Type);
			
	 		ServletOutputStream out=res.getOutputStream();

			//### This function will create unique condition based on service. ############## START 
			if(Services_Type.equals("Exterior and Interior Cleaning") || Services_Type.equals("Dent Removal and Painting") || Services_Type.equals("Road Side Assistance") || Services_Type.equals("General Checkup") || Services_Type.equals("Car Battery Health Checkup") )
			{
				
				Services_Type_SQL_Condition="and b.vehicle_model='"+Vehicle_Model+"' and b.vehicle_name='"+Vehicle_Make+"' and a.vehicle_price_type=b.vehicle_size ";
				
			}
			else if(Services_Type.equals("AC Tuneup and Repair"))
			{
				
				Services_Type_SQL_Condition="and b.vehicle_model='"+Vehicle_Model+"' and b.vehicle_name='"+Vehicle_Make+"' and a.services_package=b.vehicle_ACtype ";
				
			}
			else if(Services_Type.equals("Tyre Services Wheel Balancing and Alignment"))
			{
				
				Services_Type_SQL_Condition="and b.vehicle_model='"+Vehicle_Model+"' and b.vehicle_name='"+Vehicle_Make+"' and a.vehicle_price_type=b.vehicle_wheelradius ";
				
			}
			else if(Services_Type.equals("Winshield and Glass Repair"))
			{
				
				Services_Type_SQL_Condition=" and a.vehicle_model='"+Vehicle_Model+"' and a.vehicle_make='"+Vehicle_Make+"' ";
				
			}
			else
			{
				
				Services_Type_SQL_Condition=" ";
				
			}
					
					
			LogLevel.DEBUG(5, new Throwable(), "Services_Type :" + Services_Type);
			LogLevel.DEBUG(5, new Throwable(), "Services_Type_SQL_Condition :" + Services_Type_SQL_Condition);
			//### This function will create unique condition based on service. ############## END 
		
			//### SQL to Get the Service package for that Particular Vehicle make and model   ############## START 
			
			String Services_Package_SQL="SELECT DISTINCT a.services_package FROM services_details a, vehicle_details b WHERE a.services_type='"+Services_Type+"' "+Services_Type_SQL_Condition+" ";
			LogLevel.DEBUG(5, new Throwable(), "Services_Package_SQL :" + Services_Package_SQL);
			
			Vector Services_Package_Vector=qm.executeQuery(Services_Package_SQL);
			
			//### SQL to Get the Service package for that Particular Vehicle make and model   ############## END 
			
			
			if(Services_Package_Vector.isEmpty())
			{ 
				out.println("<option value='0'>No Service Packages Avaliable</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<Services_Package_Vector.size(); i++)
					{
						if(i==0)
						{
							out.print("<option value='0'  style='font-size: 13px;font-weight: 700;color: #757575;'>Select Services Package</option>");
						}
							Hashtable Get_Services_List_HT=(Hashtable)Services_Package_Vector.get(i);
							String Services_Package =String.valueOf(Get_Services_List_HT.get("services_package"));
							LogLevel.DEBUG(5,new Throwable(),"Services_Package:"+Services_Package);
							out.print("<option value='"+Services_Package+"'>"+Services_Package+"</option>"); 
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
	
	
	
	/* *************************************************************************************************************\
	* This method is used to insert Order Detailes in to DB, Send Mails and SMS
	\* *************************************************************************************************************/
	public String Book_A_Service_Online(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort,String SMSFromAddress, String FromEmailId, String strsuppemaild, String baseUrl, String strsuppmobnumber,String strCMSSERVERIP,String strPDFFilePath,String strPDFRelFilePath,String baseurldirectory,String OperatorTeamCount)
	{ 	
	
		String User_Moblie_Number = (req.getParameter("UMN")!=null)?(req.getParameter("UMN")):"";
		String User_Moblie_OTP = (req.getParameter("UOTP")!=null)?(req.getParameter("UOTP")):"";
		String User_Name = (req.getParameter("UNA")!=null)?(req.getParameter("UNA")):"";
		String User_Email_Id = (req.getParameter("UEM")!=null)?(req.getParameter("UEM")):"";
		String User_Area = (req.getParameter("UAR")!=null)?(req.getParameter("UAR")):"";
		String User_Pincode = (req.getParameter("UPN")!=null)?(req.getParameter("UPN")):"";
		String User_State = (req.getParameter("UST")!=null)?(req.getParameter("UST")):"";
		String User_City = (req.getParameter("UCT")!=null)?(req.getParameter("UCT")):"";
		String User_Address = (req.getParameter("UAD")!=null)?(req.getParameter("UAD")):"";
		String User_Landmark = (req.getParameter("ULM")!=null)?(req.getParameter("ULM")):"";
		String Pre_Order_Number = (req.getParameter("ORD")!=null)?(req.getParameter("ORD")):"";
		String User_Service_Type = (req.getParameter("USeT")!=null)?(req.getParameter("USeT")):"";
		String User_Service_Place = (req.getParameter("USPT")!=null)?(req.getParameter("USPT")):"";
		String User_Service_Package_Type = "Health Check";
		String User_Vehicle_Make = (req.getParameter("UVMK")!=null)?(req.getParameter("UVMK")):"";
		String User_Vehicle_Model = (req.getParameter("UVMD")!=null)?(req.getParameter("UVMD")):"";		
		String User_Product_Type = (req.getParameter("UPT")!=null)?(req.getParameter("UPT")):"";
		String User_Inverter_Capacity = (req.getParameter("UIC")!=null)?(req.getParameter("UIC")):"";
		String Product_Payment_Mode = (req.getParameter("OPPM")!=null)?(req.getParameter("OPPM")):"";
		String Product_Quantity = (req.getParameter("OQTY")!=null)?(req.getParameter("OQTY")):"";
		String refcode= (req.getParameter("ORC")!=null)?(req.getParameter("ORC")):"";
		
		
		User_Moblie_Number =User_Moblie_Number.replaceAll("\\+", " ");
		User_Moblie_OTP =User_Moblie_OTP.replaceAll("\\+", " ");
		User_Moblie_OTP=User_Moblie_OTP.replaceAll("[\\t\\n\\r]","");
		User_Name =User_Name.replaceAll("\\+", " ");
		User_Email_Id =User_Email_Id.replaceAll("\\+", " ");
		User_Area =User_Area.replaceAll("\\+", " ");
		User_Pincode =User_Pincode.replaceAll("\\+", " ");
		User_State =User_State.replaceAll("\\+", " ");
		User_City =User_City.replaceAll("\\+", " ");
		User_Address =User_Address.replaceAll("\\+", " ");
		User_Landmark =User_Landmark.replaceAll("\\+", " ");
		Pre_Order_Number =Pre_Order_Number.replaceAll("\\+", " ");
		User_Service_Type =User_Service_Type.replaceAll("\\+", " ");
		User_Service_Package_Type =User_Service_Package_Type.replaceAll("\\+", " ");
		User_Service_Place =User_Service_Place.replaceAll("\\+", " ");
		User_Vehicle_Make =User_Vehicle_Make.replaceAll("\\+", " ");
		User_Vehicle_Model =User_Vehicle_Model.replaceAll("\\+", " ");				
		User_Product_Type =User_Product_Type.replaceAll("\\+", " ");
		User_Inverter_Capacity =User_Inverter_Capacity.replaceAll("\\+", " ");
		Product_Payment_Mode =Product_Payment_Mode.replaceAll("\\+", " ");
		Product_Quantity =Product_Quantity.replaceAll("\\+", " ");
		refcode =refcode.replaceAll("\\+", " ");
		
		LogLevel.DEBUG(5, new Throwable(), "User_Service_Type :" + User_Service_Type);
		LogLevel.DEBUG(5, new Throwable(), "User_Service_Package_Type :" + User_Service_Package_Type);
		LogLevel.DEBUG(5, new Throwable(), "User_Service_Place :" + User_Service_Place);
		
		
		String Services_Category_Condition_SQL="";
		String Verify_Area_Pincode="";
		String Retailer_ID_RES="";
		String Order_Number="";
		String Service_Price="";
		String Service_Discount_Price="";
		String Location_Or_Pincode="";
		String strRes="";
		String agent_name="";
		
		Hashtable GetRetailerID_Ht =new Hashtable();
		Hashtable Services_Package_Price_Ht =new Hashtable();
		try
		{
			
			User_Moblie_Number = java.net.URLDecoder.decode(User_Moblie_Number,"UTF-8");
			User_Moblie_OTP = java.net.URLDecoder.decode(User_Moblie_OTP,"UTF-8");
			User_Name = java.net.URLDecoder.decode(User_Name,"UTF-8");
			User_Email_Id = java.net.URLDecoder.decode(User_Email_Id,"UTF-8");
			User_Area = java.net.URLDecoder.decode(User_Area,"UTF-8");
			User_Pincode = java.net.URLDecoder.decode(User_Pincode,"UTF-8");
			User_State = java.net.URLDecoder.decode(User_State,"UTF-8");
			User_City = java.net.URLDecoder.decode(User_City,"UTF-8");
			User_Address = java.net.URLDecoder.decode(User_Address,"UTF-8");
			User_Landmark = java.net.URLDecoder.decode(User_Landmark,"UTF-8");
			Pre_Order_Number = java.net.URLDecoder.decode(Pre_Order_Number,"UTF-8");
			User_Service_Type = java.net.URLDecoder.decode(User_Service_Type,"UTF-8");
			User_Service_Package_Type = java.net.URLDecoder.decode(User_Service_Package_Type,"UTF-8");
			User_Service_Place = java.net.URLDecoder.decode(User_Service_Place,"UTF-8");
			User_Vehicle_Make = java.net.URLDecoder.decode(User_Vehicle_Make,"UTF-8");
			User_Vehicle_Model = java.net.URLDecoder.decode(User_Vehicle_Model,"UTF-8");			
			User_Product_Type = java.net.URLDecoder.decode(User_Product_Type,"UTF-8");
			User_Inverter_Capacity = java.net.URLDecoder.decode(User_Inverter_Capacity,"UTF-8");
			Product_Payment_Mode = java.net.URLDecoder.decode(Product_Payment_Mode,"UTF-8");
			Product_Quantity = java.net.URLDecoder.decode(Product_Quantity,"UTF-8");
			refcode = java.net.URLDecoder.decode(refcode,"UTF-8");
			
			String Services_Package_Price_SQL="";
			String Services_Type_SQL_Condition="";
			String order_type="";
			
			User_Address =User_Address.replaceAll("\"", "&quot;");
			User_Address =User_Address.replaceAll("\'", "&#39;");
			
			if(refcode.equals("0"))
			{
				LogLevel.DEBUG(5, new Throwable(), "refcode if:" + refcode);
				order_type="Paid Order";
			}
			else
			{
				LogLevel.DEBUG(5, new Throwable(), "refcode else:" + refcode);
				order_type="Free Order";
			}
			

			//### SQL to Get the Service Details for Vehicle Make and Model. ############## START 
			
		
			Services_Package_Price_SQL="SELECT DISTINCT service_type,battery_type,store,"+User_Service_Place+" as serv_place from service_prices a WHERE service_type='"+User_Service_Type+"' and battery_type='"+User_Product_Type+"'";
            
			LogLevel.DEBUG(5, new Throwable(), "Services_Package_Price_SQL :" + Services_Package_Price_SQL);
			
			Hashtable Get_Services_Package_Price_HT = qm.getRow(Services_Package_Price_SQL); 
            
			Service_Discount_Price=String.valueOf(Get_Services_Package_Price_HT.get("serv_place"));
			String store_price=String.valueOf(Get_Services_Package_Price_HT.get("store"));
            
            if(store_price.equals("NA") && User_Product_Type.equals("Car Batteries"))
            {
                store_price="100";
            }
            else if(store_price.equals("NA") && User_Product_Type.equals("Bike Batteries"))
            {
                store_price="50";
            }
            else
            {
                store_price=store_price;
            }
            
               if(Integer.parseInt(Product_Quantity)==1 || Product_Quantity.equals("1") || Product_Quantity.equals(1))
               {
                   Service_Discount_Price=Service_Discount_Price;
               }
               else
               {
                   Service_Discount_Price=String.valueOf((Integer.parseInt(Service_Discount_Price))+(Integer.parseInt(store_price)*(Integer.parseInt(Product_Quantity)-1)));
               }
            
                if(User_Product_Type.equals("Car Batteries"))
                {
                    Service_Price = "400";
                }
                else if(User_Product_Type.equals("Bike Batteries"))
                {
                    Service_Price = "250";
                } 
                else
                {
					Service_Price = "700";
			    }
                
			
            
            
			//############ Prices Fetching Code Ended @@@@@@@@@@@@@@@@ Sai Krishna Daddala 05/04/2017
			
			
			//############ Getting Retailer for selected values Code Started @@@@@@@@@@@@@@@@ Sai Krishna Daddala 05/04/2017
			
			if(User_Service_Type.equals("Exterior and Interior Cleaning") && User_Service_Package_Type.contains("Dealer"))
			{
				Services_Category_Condition_SQL="and services_category='Dealer'";
			}
			else if(User_Service_Type.equals("Exterior and Interior Cleaning") && User_Service_Package_Type.contains("Door Service"))
			{
				Services_Category_Condition_SQL="and services_category='Door Service'";
			}
			else 
			{
				Services_Category_Condition_SQL="";
			}
				
			if(User_Area.equals("0") || User_Area.equals("") || User_Area == "")
			{
				Verify_Area_Pincode = "and pincode='"+User_Pincode+"' ";
				Location_Or_Pincode=User_Pincode;
			}
			else
			{
				Verify_Area_Pincode = "and area='"+User_Area+"' ";
				Location_Or_Pincode=User_Area;
			}
			
			String Find_A_Retailer = "select retailer_id,state,city from retailer_location_mapping where state='"+User_State+"' and city='"+User_City+"' "+Verify_Area_Pincode+" "+Services_Category_Condition_SQL+" and ( weekend_dealer_flag='Yes' or weekend_dealer_flag='No' ) limit 1";
		
			LogLevel.DEBUG(5, new Throwable(), "Find_A_Retailer :" + Find_A_Retailer);
			
			GetRetailerID_Ht = qm.getRow(Find_A_Retailer); 
			Retailer_ID_RES = String.valueOf(GetRetailerID_Ht.get("retailer_id"));
			LogLevel.DEBUG(5, new Throwable(), "Retailer_ID_RES :" + Retailer_ID_RES);
			
			//############ Getting Retailer for selected values Code Ended @@@@@@@@@@@@@@@@ Sai Krishna Daddala 05/04/2017
			
			
			if(Retailer_ID_RES.equals(null) || Retailer_ID_RES.equals("null") || Retailer_ID_RES == null || Retailer_ID_RES == "null" || Retailer_ID_RES =="")
			{
				return "No Retailers Found";
			}
			else
			{
				User_State=User_State.trim();
				
				String User_Retailer_State =User_State.replaceAll(" ", "_");
			
				//############ Code to Get Retailer Detailes | Started @@@@@@@@@@@@@@@@ Sai Krishna Daddala 05/04/2017
			
				String Get_Retailer_Details_SQL = "select retailer_id,retailer_name,mobile_number,email_id,address1,mobile_numberother,invoice_flag from "+User_Retailer_State+"_retailers where retailer_id='"+Retailer_ID_RES+"'";
				LogLevel.DEBUG(5, new Throwable(), "Get_Retailer_Details_SQL :" + Get_Retailer_Details_SQL);

				Hashtable Get_Retailer_Details_HT = qm.getRow(Get_Retailer_Details_SQL); 
				String Retailer_Id_Res=String.valueOf(Get_Retailer_Details_HT.get("retailer_id"));
				String Retailer_Mobile_Number_Res=String.valueOf(Get_Retailer_Details_HT.get("mobile_number"));
				String Retailer_Name_Res=(String)Get_Retailer_Details_HT.get("retailer_name");
				String Retailer_Email_Res=(String)Get_Retailer_Details_HT.get("email_id");
				String Retailer_Address=(String)Get_Retailer_Details_HT.get("address1");
				String Retailer_Other_Mobile_Numbers=String.valueOf(Get_Retailer_Details_HT.get("mobile_numberother"));
				
				//############ Code to Get Retailer Detailes | Ended @@@@@@@@@@@@@@@@ Sai Krishna Daddala 05/04/2017
				
				if(Retailer_Id_Res.equals(null) || Retailer_Id_Res.equals("null") || Retailer_Id_Res == null || Retailer_Id_Res == "null" || Retailer_Id_Res =="")
				{
					return "No Retailers Found";
				}
				else
				{
					
					if(Pre_Order_Number.equals("Failed"))
					{
								
						String DB_Orders_Table_ID="ord_id";
						String DB_Orders_Table="service_order_details";
						String DB_Orders_Cap="S";
						
						//######################################## Operator Code	#################### ???@@@ Sai Krishna
						String Get_Previous_Operator_SQL = "select agent_name from "+DB_Orders_Table+" where consumer_mobnumber='"+User_Moblie_Number+"' and (NOT agent_name = '0' or agent_name = ' ' ) and YEAR(creation_date) = YEAR(NOW()) AND MONTH(creation_date) = MONTH(NOW()) AND DAY(creation_date) = DAY(NOW()) order by creation_date limit 1";
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
							// This condition is to assign operator in Worst case of Software failure.  ???@@@ Sai Krishna
							if(agent_name.equals(""))
							{
								agent_name="operator1";
							}
							else
							{
								agent_name=agent_name;
							}
						}
						//######################################## Operator Code	#################### ???@@@ Sai Krishna
						
						
						
								
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
						String verificationcode1 = Integer.toString(Num);
						Order_Number ="ORD"+DB_Orders_Cap+""+Last_Order_Count_String+""+verificationcode1+"S";
						LogLevel.DEBUG(5, new Throwable(), "Order_Number :" + Order_Number);
								
											
						Order_Number=Order_Number;
					}
					else
					{
						Order_Number=Pre_Order_Number;
					}	
					
					String Order_to_DB_SQL="";
					String payment_mode_temp="";
					String Order_Status_Temp="";
					
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
						Order_to_DB_SQL = "insert into service_order_details(ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_verif_code,retailer_name,retailer_mobilnumber,retailer_emailid,city,services_type,services_package,service_price_mrp,area,pincode,veh_name,veh_model,service_price_discount,creation_date,agent_name,order_status,consumer_address1,consumer_address2,service_commission_amount,order_reasons,order_agent_comments,state,operator,created_by,payment_mode,payment_mode_type,product_type,product_capacity,quantity,reffered_code,order_type,batterywale_order,confirm_by)values(NULL,'"+Order_Number+"','"+User_Name+"','"+User_Moblie_Number+"','"+User_Email_Id+"','"+User_Moblie_OTP+"','"+Retailer_Name_Res+"','"+Retailer_Mobile_Number_Res+"','"+Retailer_Email_Res+"','"+User_City+"','"+User_Service_Type+"','"+User_Service_Type+"','"+Service_Price+"','"+User_Area+"','"+User_Pincode+"','"+User_Vehicle_Make+"','"+User_Vehicle_Model+"','"+Service_Discount_Price+"',now(),'"+agent_name+"','"+Order_Status_Temp+"','"+User_Address+"','"+User_Landmark+"',' ',' ',' ','"+User_State+"','Customer','ngit','"+payment_mode_temp+"','"+Product_Payment_Mode+"','"+User_Product_Type+"','"+User_Inverter_Capacity+"','"+Product_Quantity+"','"+refcode+"','"+order_type+"','No','Customer')";
						
					}
					else
					{
						
						Order_to_DB_SQL = "update service_order_details set consumer_name='"+User_Name+"', consumer_mobnumber='"+User_Moblie_Number+"', consumer_emailid='"+User_Email_Id+"', consumer_verif_code='"+User_Moblie_OTP+"', retailer_name='"+Retailer_Name_Res+"', retailer_mobilnumber='"+Retailer_Mobile_Number_Res+"', retailer_emailid='"+Retailer_Email_Res+"', services_type='"+User_Service_Type+"' , services_package='"+User_Service_Package_Type+"' , service_price_mrp='"+Service_Price+"', area='"+User_Area+"', pincode='"+User_Pincode+"' ,state='"+User_State+"' ,city='"+User_City+"' , veh_name='"+User_Vehicle_Make+"', veh_model='"+User_Vehicle_Model+"', service_price_discount='"+Service_Discount_Price+"', creation_date=now(), order_status='"+Order_Status_Temp+"', consumer_address1='"+User_Address+"', consumer_address2='"+User_Landmark+"',  payment_mode='"+payment_mode_temp+"', payment_mode_type='"+Product_Payment_Mode+"',  product_type='"+User_Product_Type+"', product_capacity='"+User_Inverter_Capacity+"',reffered_code='"+refcode+"',order_type='"+order_type+"',confirm_by='Customer', order_agent_comments='' where order_number='"+Order_Number+"'";
						
					}
				
					LogLevel.DEBUG(5,new Throwable(),"Order_to_DB_SQL : "+Order_to_DB_SQL);
					int Order_to_DB_SQL_Response = qm.executeUpdate(Order_to_DB_SQL);
					if(Order_to_DB_SQL_Response <0)
					{
						return "Opps !, Something went Wrong";
					}
					else
					{
						strRes="Sucessfully | "+Order_Number+""; 
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
				
		
		if(Product_Payment_Mode.equals("Cash On Delivery") || Product_Payment_Mode.equals("Online Payment After Fitment"))
			{
/* 				//######## Send SMS for ORDER
				Order_SMS Send_Order_SMS_Checkout = new Order_SMS(qm);
				String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,Order_Number,"Yes","Yes","Yes");
				LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
				//######## Send SMS for ORDER
						
				//######## Send Mail for ORDER
				Order_SMS Send_Order_MAIL_Checkout = new Order_SMS(qm);
				String MAIL_Report=Send_Order_MAIL_Checkout.Send_Order_Mail(req,res,session,Order_Number,"Yes","Yes","Yes");
				LogLevel.DEBUG(5, new Throwable(), "MAIL_Report :" + MAIL_Report);
				//######## Send Mail for ORDER */
				
				//######## Send SMS for ORDER
				Order_Service_SMS Send_Order_SMS_Checkout = new Order_Service_SMS(qm);
				String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,Order_Number,"Yes","No","No");
				LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
				//######## Send SMS for ORDER
				
				//######## Send Mail for ORDER
				Order_Service_SMS Send_Order_MAIL_Checkout = new Order_Service_SMS(qm);
				String MAIL_Report=Send_Order_MAIL_Checkout.Send_Order_Mail(req,res,session,Order_Number,"Sir","No","Yes");
				LogLevel.DEBUG(5, new Throwable(), "MAIL_Report :" + MAIL_Report);
				//######## Send Mail for ORDER
				
				strRes=baseUrl+baseurldirectory+"transaction_service/?id="+Order_Number;
			}
			else
			{
				
				//*********************################## 
				//Online Payment Request Generation Code 
				//###### START #####################*****************//
				
				OnlinePaymentService Create_Payment_Link = new OnlinePaymentService(qm);
				LogLevel.DEBUG(5, new Throwable(), "Create_Payment_Link :" + Create_Payment_Link);
				String Create_Payment_Link_Resp=  Create_Payment_Link.getPaymentLink(req,res,session,User_Name,User_Email_Id,User_Moblie_Number,Service_Discount_Price,Order_Number);
				LogLevel.DEBUG(5, new Throwable(), "Create_Payment_Link_Resp :" + Create_Payment_Link_Resp);
				
				//*********************################## 
				//Online Payment Request Generation Code 
				//###### END #####################*****************//


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
	* This Functionis to get the Random Battery List In Home Page #################################
	\* **************************************************************************************************************************************/
	public String Get_ServiceList_Random(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			
			String Get_ServiceList_Random_SQL="";
			Vector Vector_Get_ServiceList_Random;
			
			//################### SQL Query to get Battery list random from Cooke location  
			Get_ServiceList_Random_SQL ="select DISTINCT a.services_type,a.services_package,b.vehicle_name,b.vehicle_model,a.icon_url,a.service_price_mrp,a.service_price_discount from services_details a, vehicle_details b where  a.vehicle_price_type=b.vehicle_size or a.vehicle_price_type=b.vehicle_wheelradius or a.services_package=b.vehicle_ACtype or ( a.vehicle_model=b.vehicle_model and a.vehicle_make=b.vehicle_name) ORDER BY RAND() LIMIT 10";
			LogLevel.DEBUG(5, new Throwable(), "Get_ServiceList_Random_SQL - Cooke :" + Get_ServiceList_Random_SQL);
			
			Vector_Get_ServiceList_Random =qm.executeQuery(Get_ServiceList_Random_SQL);
			
			//If SQL Query to get Battery list random from Cooke location  fails Forcing it to fetch battery list from default location Karnataka, Bangalore
			if(Vector_Get_ServiceList_Random.isEmpty())
			{ 
				
			}
			else
			{
				
			}
			
			for (int i=0; i<Vector_Get_ServiceList_Random.size(); i++)
			{
				Hashtable ht=(Hashtable)Vector_Get_ServiceList_Random.get(i);
				
				String services_type =String.valueOf(ht.get("services_type"));
				String services_package =String.valueOf(ht.get("services_package"));
				String vehicle_name =String.valueOf(ht.get("vehicle_name"));
				String vehicle_model =String.valueOf(ht.get("vehicle_model"));
				String icon_url =String.valueOf(ht.get("icon_url"));
				String service_price_mrp =String.valueOf(ht.get("service_price_mrp"));
				String service_price_discount =String.valueOf(ht.get("service_price_discount"));
				

				String services_type_URL= services_type.replaceAll(" ", "-");
				String services_package_URL= services_package.replaceAll(" ", "\\+");
				String vehicle_name_URL= vehicle_name.replaceAll(" ", "-");
				String vehicle_model_URL= vehicle_model.replaceAll(" ", "\\+");
				
				strRes=strRes+"  <div class='item'> <div class='item-inner'><div class='item-img'> <div class='product-block'><div class='product-image'> <a href='"+baseUrl+"/services/carservices/Karnataka/Bangalore/"+vehicle_name_URL+"/"+vehicle_model_URL+"/"+services_type_URL+"/"+services_package_URL+"/' style='height: auto;'> <figure class='product-display'><img src='"+icon_url+"' class='lazyOwl product-mainpic' alt=' "+services_package+" "+services_type+" "+vehicle_name+" "+vehicle_model+" ' style='display: block;'> <img class='product-secondpic' alt='"+services_package+" "+services_type+" "+vehicle_name+" "+vehicle_model+"' src='"+icon_url+"'> </figure> </a> </div><div class='product-meta'> <div class='product-action'> <a class='addcart'  href='"+baseUrl+"/services/carservices/Karnataka/Bangalore/"+vehicle_name_URL+"/"+vehicle_model_URL+"/"+services_type_URL+"/"+services_package_URL+"/' > <i class='icon-external-link-sign'>&nbsp;</i> View More </a></div> </div></div><div class='item-info' style='text-align: left;'> <div class='info-inner'><div class='item-title'> <a  href='"+baseUrl+"/services/carservices/Karnataka/Bangalore/"+vehicle_name_URL+"/"+vehicle_model_URL+"/"+services_type_URL+"/"+services_package_URL+"/' title='Click to View ' style='display: block;white-space: nowrap;text-overflow: ellipsis;overflow: hidden;'  > "+services_package+" </br>"+services_type+" </a> </div><div class='item-content'> <div class='item-price'><div class='price-box'> <div class='old-price'> <span class='price-label' style='display: block;'>Regular Price: <span class='price' ><i class='icon-inr' aria-hidden='true'></i> "+service_price_mrp+" </span> </span></div> <span class='price-label' style='display: block;white-space: nowrap;text-overflow: ellipsis;overflow: hidden;'  ><b>Vehicle Details</b>: <span > "+vehicle_name+", "+vehicle_model+" </span> </span><br><table style=' color: #494848;font-size: 15px;'> <tr> <td><b>Discount Price</b></td> <td>: <i class='icon-inr' aria-hidden='true'></i> "+service_price_discount+" </td> </tr> </table> </div> </div></div> </div></div> </div></div> </div>";
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




