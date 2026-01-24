package com.ngit.servlets.admin.batterywalestore;

import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.mail.*;
import com.ngit.javabean.consumers.products.Order_Store_SMS;
import com.ngit.javabean.sendsms.SendSMS;

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
import static java.lang.System.out;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.json.*; 
import net.sf.ezmorph.*; 

/*
 * @author Prasanna Kumari.
 */
/* This class is to Admin Login. This class initializes the necessary connection pools and perform the transactions on the pools. */
public class BatterystoreTrolleyDetails extends HttpServlet 
{
 	private ServletContext context; 
	QueryManager qm;
	String baseURL;
	static final long serialVersionUID=21;
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
			baseURL = (propsMOPConfig.getProperty("baseURL")!=null)?(propsMOPConfig.getProperty("baseURL")):"";
			LogLevel.DEBUG(5, new Throwable(), "baseURL :" + baseURL);
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
	
	/*This doPost service method handles all the requests and responses passing from doGet service method to perform Admin Login.*/
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
		doGet(req,res);
	}
 	/* This doGet service method calls doPost service method to handle all the reqs and responses to perform Admin Login. */
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
		res.setContentType("text/html;charset=UTF-8");
        HttpSession session=req.getSession(true);
 		session=req.getSession(true);
		String struserName=(String)session.getAttribute("sesBatteryOperatorName"); 
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
		//ServletOutputStream out=res.getOutputStream(); 
		
		String strIpAddress =(propsMOPConfig.getProperty("TRANSMITTER_IP_ADDR")!=null)?(propsMOPConfig.getProperty("TRANSMITTER_IP_ADDR")):"";
		String strPort=(propsMOPConfig.getProperty("TRANSMITTER_IP_PORT")!=null)?(propsMOPConfig.getProperty("TRANSMITTER_IP_PORT")):"9910";
		String SMSFromAddress=(propsMOPConfig.getProperty("SMSFromAddress")!=null)?(propsMOPConfig.getProperty("SMSFromAddress")):"";
		String FromEmailId=(propsMOPConfig.getProperty("SupportEmailId")!=null)?(propsMOPConfig.getProperty("SupportEmailId")):"";
		String strsuppemaild=(propsMOPConfig.getProperty("suppemaild")!=null)?(propsMOPConfig.getProperty("suppemaild")):"";
		String domainname = (propsMOPConfig.getProperty("domainname")!=null)?(propsMOPConfig.getProperty("domainname")).trim():"bookbattery.com";
		String baseUrl =  propsMOPConfig.getProperty("publicUrl");
		String strsuppmobnumber =  propsMOPConfig.getProperty("suppmobnumber");
		String strCMSSERVERIP	= (propsMOPConfig.getProperty("CMS_SERVER_IP")!=null)?(propsMOPConfig.getProperty("CMS_SERVER_IP")):"";
		
		/*This method is used to get Battery details*/
		 /* ****************************************************************************************\
		* This action is used to get battery details.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("insertorderdetails"))
		{ 
			try
			{
				String strRes=insertorderdetails(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				ServletOutputStream out=res.getOutputStream(); 
				out.println(strRes);
				out.close();
			
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}
		
		/*This method is used to get Battery details*/
		 /* ****************************************************************************************\
		* This action is used to get battery details.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("gettrolleydetails"))
		{ 
			try
			{
				String strRes=gettrolleydetails(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				ServletOutputStream out=res.getOutputStream(); 
				out.println(strRes);
				out.close();
			
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}
		 /* ****************************************************************************************\
		* This action is used to update order status.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("updateorderstatus"))
		{
			try
			{
				String strRes=updateorderstatus(req,res,session,domainname,strIpAddress,strPort,FromEmailId,strsuppemaild,SMSFromAddress);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);
				out.close();
			}
			catch (Exception e)
			{										
			LogLevel.ERROR(1, e, "Error :" + e);
			}	
	     }
		
		/*This method is used to get Battery details*/
		 /* ****************************************************************************************\
		* This action is used to get battery details.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("gettrolleysalesreport"))
		{ 
			try
			{
				String strRes=gettrolleysalesreport(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				ServletOutputStream out=res.getOutputStream(); 
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
	* This method is to fetch battery details details.
	* @strSqlQry : carries the query to select battery details from battery_details table.
	* @strSqlQry1 : Query has multiple pages to select battery details from battery_details table.
	\* **************************************************************************************************************************************/
	public String gettrolleydetails(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		try
		{	
			String strtrolleybrand= (req.getParameter("trolleybrand")!=null)?(req.getParameter("trolleybrand")):"";
			LogLevel.DEBUG(5,new Throwable(),"strtrolleybrand:"+strtrolleybrand );

			String strtrolleymodel= (req.getParameter("trolleymodel")!=null)?(req.getParameter("trolleymodel")):"";
			LogLevel.DEBUG(5,new Throwable(),"strtrolleymodel:"+strtrolleymodel );
			  
			String state= (req.getParameter("state")!=null)?(req.getParameter("state")):"";
			LogLevel.DEBUG(5,new Throwable(),"state:"+state);

			String strcity= (req.getParameter("city")!=null)?(req.getParameter("city")):"";
			LogLevel.DEBUG(5,new Throwable(),"strcity:"+strcity);
			
			String strarea= (req.getParameter("area")!=null)?(req.getParameter("area")):"";
			LogLevel.DEBUG(5,new Throwable(),"strarea:"+strarea);

			String strpincode= (req.getParameter("pincode")!=null)?(req.getParameter("pincode")):"";
			//String strpincode= "";
			LogLevel.DEBUG(5,new Throwable(),"strpincode:"+strpincode);

			String strSqlQry ="";
			String strstate="";
			String strSqlQrybat="";
			String strpincity="";
			String city="";
			String strConditions1="";
			String strConditionsDetails="";
 
			 
			if(strpincode == "")
			{
			 
				strstate=state;
				city=strcity;
			}
			else
			{
			 
			String StrSqlQrystate1 = "select state,city from battery_pincode where pincode='"+strpincode+"'";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQrystate1 :" + StrSqlQrystate1);

			Hashtable htgetstate1 = qm.getRow(StrSqlQrystate1); 
			strstate=(String)htgetstate1.get("state");
			String strcity1=(String)htgetstate1.get("city");
			city=strcity1;	
			}
			 
			strSqlQrybat ="select distinct(t_id) from batterystore_trolleydetails where trolley_brand='"+strtrolleybrand+"' and trolley_model='"+strtrolleymodel+"' ";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQrybat :" + strSqlQrybat);
 			ArrayList htav=new ArrayList();
			htav=qm.getField(strSqlQrybat);

			String trids="";
			for(int i=0;i<htav.size();i++)
			{
			if(trids.equals(""))
				trids=htav.get(i).toString();
			else
			trids=trids+","+htav.get(i).toString();
			}
			LogLevel.DEBUG(5,new Throwable(),"batids:"+trids);
			
			 
				strSqlQry ="select t_id,trolley_brand,trolley_model,trolley_price,icon_url from batterystore_trolleydetails where trolley_brand='"+strtrolleybrand+"' and trolley_model='"+strtrolleymodel+"' and state='"+strstate+"' and city='"+city+"'  and t_id in ("+trids+") order by trolley_brand asc";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

				//message = "User selected  "+strbattype+" >> "+strvehmake+" >> "+strvehmodel+" >> "+city+" for order Battery. User Mob No: "+strUsermobileno+"";
			 
			 Vector TrolledetailsVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"TrolledetailsVector"+TrolledetailsVector );
			
			if(TrolledetailsVector.isEmpty())
			{ 
				LogLevel.DEBUG(1,new Throwable(),"Failed to fetch Battery details ");
				session.setAttribute("priority","1");
				session.setAttribute("sesbatterydetailsErrorMsg", "<font color='#ff3333' class='vrb10'>No Battery details found based on selection.! </font> ");
				session.removeAttribute("TrolledetailsVector");
				res.sendRedirect("../jsp/batterywalestore/orders/ordertrolley.jsp");
				return strRes;
			}
			else
			{
				LogLevel.DEBUG(1, new Throwable(),"Successfully Fetched Trolley Details");
				session.setAttribute("TrolledetailsVector", TrolledetailsVector);
				//session.setAttribute("BatpriceVector", BatpriceVector);
				res.sendRedirect("../jsp/batterywalestore/orders/ordertrolleydetails.jsp?trolleybrand="+strtrolleybrand+"&trolleymodel="+strtrolleymodel+"&state="+strstate+"&city="+strcity+"&area="+strarea+"&pincode="+strpincode+"&pincity="+city);
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
 
	/**************************************************************************************************************************************\
	* This method is to fetch battery product details.
	* @strSqlQry : carries the query to select battery details from battery_details table.
	* @strSqlQry1 : Query has multiple pages to select battery details from battery_details table.
	\* **************************************************************************************************************************************/
	public String insertorderdetails(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	 
 		String Consumer_Mobile = (req.getParameter("mobile_number")!=null)?(req.getParameter("mobile_number")):"";
 		String Consumer_Name = (req.getParameter("consumer_name")!=null)?(req.getParameter("consumer_name")):"";
 		String Consumer_Email = (req.getParameter("consumer_emailid")!=null)?(req.getParameter("consumer_emailid")):"";
 		String Consumer_Address = (req.getParameter("consumer_address")!=null)?(req.getParameter("consumer_address")):"";
 		String Ordered_By = (req.getParameter("ordered_by")!=null)?(req.getParameter("ordered_by")):"";
  		String Payment_Mode = (req.getParameter("payment_mode")!=null)?(req.getParameter("payment_mode")):"";
 		String Quantity = (req.getParameter("quantity")!=null)?(req.getParameter("quantity")):"";
  		String Pincode = (req.getParameter("zipcode")!=null)?(req.getParameter("zipcode")):"";
 		String Area = (req.getParameter("area")!=null)?(req.getParameter("area")):"";
 		String City = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
 		String State = (req.getParameter("state")!=null)?(req.getParameter("state")):"";
  		String TrolleyModel = (req.getParameter("trolleymodel")!=null)?(req.getParameter("trolleymodel")):"";
 		String TrolleyBrand = (req.getParameter("trolleybrand")!=null)?(req.getParameter("trolleybrand")):"";
  		String Price = (req.getParameter("price")!=null)?(req.getParameter("price")):"";
  		String StoreId = (req.getParameter("store_id")!=null)?(req.getParameter("store_id")):"";
 		String StoreName = (req.getParameter("store_name")!=null)?(req.getParameter("store_name")):"";
		 
		String strRes=""; 
		String CustomerAddress=""; 
		
		try
		{
			ServletOutputStream out=res.getOutputStream();
			
			
			
			int Price_Temp=0;
			 
			int QTY_int = Integer.parseInt(Quantity);
			int Final_Price_Int=Price_Temp*QTY_int;
						
			String Final_Price = Integer.toString(Final_Price_Int);
			
			
				String StrSqlQrydet = "select retailer_id,retailer_name,mobile_number,email_id,address1,mobile_numberother,invoice_flag from "+State+"_retailers where retailer_id='"+StoreId+"' and retailer_name='"+StoreName+"'";
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
					String New_Order_ID_SQL = "SELECT ord_id as ORDER_ID FROM trolley_order_details ORDER BY ord_id DESC LIMIT 1";
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
					Order_Number_Generate ="ORDT"+Last_Order_Count_String+""+Order_Number_Generate1+"T";
					LogLevel.DEBUG(5, new Throwable(), "Order_Number_Generate :" + Order_Number_Generate);
					Order_Number_Generate=Order_Number_Generate;
					 	  
					if(Consumer_Address.equals(""))
					{
						CustomerAddress="";
					}
					else
					{
					    
						CustomerAddress=", Address: "+Consumer_Address+"";
					}
					
		

					String strSqlQry="";
 					String User_Address_Landmark=CustomerAddress;
					User_Address_Landmark = User_Address_Landmark.replace("'","\\'");
					LogLevel.DEBUG(5,new Throwable()," User_Address_Landmark "+User_Address_Landmark);
					 
					strSqlQry = "insert into trolley_order_details(ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailid,state,city,area,pincode,trolley_brand,trolley_model,price,quantity,creation_date,order_status,operator,agent_name,payment_mode,payment_mode_type,city_percentage,consumer_address)values(NULL,'"+Order_Number_Generate+"','"+Consumer_Name+"','"+Consumer_Mobile+"','"+Consumer_Email+"','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+State+"','"+City+"','"+Area+"','"+Pincode+"','"+TrolleyBrand+"','"+TrolleyModel+"','"+Price+"','"+Quantity+"',now(),'confirmed','storeoperator','storeoperator','"+Payment_Mode+"','"+Payment_Mode+"','1.18','"+Consumer_Address+"')";
		
				 	
					
					LogLevel.DEBUG(5,new Throwable()," insert strSqlQry: "+strSqlQry);	
					
					int reslt = qm.executeUpdate(strSqlQry);
					LogLevel.DEBUG(5,new Throwable()," reslt: "+reslt);	
					 
						//######## Send SMS for ORDER
						Order_Store_SMS Send_Order_SMS_Checkout = new Order_Store_SMS(qm);
						String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,Order_Number_Generate,"Trolley","Yes","Yes","No","confirmed");
						LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
						//######## Send SMS for ORDER
						
						//######## Send Mail for ORDER
						Order_Store_SMS Send_Order_MAIL_Checkout = new Order_Store_SMS(qm);
						String MAIL_Report=Send_Order_MAIL_Checkout.Send_Order_Mail(req,res,session,Order_Number_Generate,"Trolley","Sir","Yes","Yes","No","confirmed");
						LogLevel.DEBUG(5, new Throwable(), "MAIL_Report :" + MAIL_Report);
						//######## Send Mail for ORDER
						
					strRes="Sucessfully | "+Order_Number_Generate+"";
					 
				}
			 

		}
		catch (Exception e)
		{
		LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
		
		}
		return strRes;
	} 	
	
	  
	 
	 /* **************************************************************************************************************************************\
		* This method is to get update order status.
		* @strSqlQry : carries the query to fetch updated service ordered status details from trolley_order_details table.
		\* **************************************************************************************************************************************/
		public String updateorderstatus(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort, String FromEmailId,String strsuppemaild,String SMSFromAddress)
		{
			
			 
			
			String orderid= (req.getParameter("orderid")!=null)?(req.getParameter("orderid")):"";
			LogLevel.DEBUG(5, new Throwable(), "orderid :" + orderid);

			String ordernumber= (req.getParameter("ordernumber")!=null)?(req.getParameter("ordernumber")):"";
			LogLevel.DEBUG(5, new Throwable(), "ordernumber :" + ordernumber);
			 
			String orderstatus= (req.getParameter("orderstatus")!=null)?(req.getParameter("orderstatus")):"";
			LogLevel.DEBUG(5, new Throwable(), "orderstatus :" + orderstatus);
			
			String orderreason= (req.getParameter("orderreason")!=null)?(req.getParameter("orderreason")):"";
			LogLevel.DEBUG(5, new Throwable(), "orderreason :" + orderreason);

			String agentcomments= (req.getParameter("agentcomments")!=null)?(req.getParameter("agentcomments")):"";
			LogLevel.DEBUG(5, new Throwable(), "agentcomments :" + agentcomments);

			String postponedate= (req.getParameter("postponedate")!=null)?(req.getParameter("postponedate")):"";
			LogLevel.DEBUG(5, new Throwable(), "postponedate :" + postponedate);
			
			String rating= (req.getParameter("rating")!=null)?(req.getParameter("rating")):"";
			LogLevel.DEBUG(5, new Throwable(), "rating :" + rating);
			
			String retailertoorder= (req.getParameter("retailertoorder")!=null)?(req.getParameter("retailertoorder")):"";
			LogLevel.DEBUG(5, new Throwable(), "retailertoorder :" + retailertoorder);
			
			String payment_mode= (req.getParameter("payment_mode")!=null)?(req.getParameter("payment_mode")):"";
			LogLevel.DEBUG(5, new Throwable(), "payment_mode :" + payment_mode);
			
			String SMSURL= (req.getParameter("SMSURL")!=null)?(req.getParameter("SMSURL")):"";
			LogLevel.DEBUG(5, new Throwable(), "SMSURL :" + SMSURL);
			
			String shorturl= (req.getParameter("shorturl")!=null)?(req.getParameter("shorturl")):"";
			LogLevel.DEBUG(5, new Throwable(), "shorturl :" + shorturl);

			String cusarea= (req.getParameter("cusarea")!=null)?(req.getParameter("cusarea")):"";
			LogLevel.DEBUG(5, new Throwable(), "cusarea :" + cusarea);

			String paymentcollected= (req.getParameter("paymentcollected")!=null)?(req.getParameter("paymentcollected")):"";
			LogLevel.DEBUG(5, new Throwable(), "paymentcollected :" + paymentcollected);

			String strRes="";
			String service_commission_amount="";
			String stroperatorName=(String)session.getAttribute("sesBatteryOperatorName"); 
			LogLevel.DEBUG(5,new Throwable(),"stroperatorName :"+stroperatorName );
			
			int reslt_new=0;
			
			String googleshorturl= "https://goo.gl/eKGqvC";
			LogLevel.DEBUG(5, new Throwable(), "googleshorturl :" + googleshorturl);
			
			
			try
			{	
				SendSMS sendsms = new SendSMS(qm);
				String strpostponedate="";

				
				if(orderstatus.equals("postponed") || orderstatus.equals("Postponed"))
				{
					SimpleDateFormat sdfDate=new SimpleDateFormat("dd-MM-yyyy");
					Date txtpostponedate=sdfDate.parse(postponedate);

					SimpleDateFormat sdfString=new SimpleDateFormat("yyyy-MM-dd");
					strpostponedate=sdfString.format(txtpostponedate); 
					LogLevel.DEBUG(5, new Throwable(), "strpostponedate :"+ strpostponedate);

				}


				ServletOutputStream out=res.getOutputStream();
				
				String strSqlQryordstat = "SELECT ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailid,state,city,area,pincode,trolley_brand,trolley_model,price,order_status,order_reasons,quantity,operator,agent_name,order_agent_comments,payment_mode,payment_mode_type,city_percentage,consumer_address,CAST(creation_date AS CHAR) as creation_date,CAST(installed_date AS CHAR) as installed_date,CAST(updated_date AS CHAR) as updated_date,CAST(postpone_date AS CHAR) as postponed_date FROM trolley_order_details WHERE  order_number='"+ordernumber+"' and ord_id='"+orderid+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQryordstat :"+strSqlQryordstat);

				Hashtable ht = qm.getRow(strSqlQryordstat);
			

				String order_number=(String)ht.get("order_number");
				LogLevel.DEBUG(5,new Throwable(),"order_number :"+order_number);
				String consumer_name=(String)ht.get("consumer_name");
				LogLevel.DEBUG(5,new Throwable(),"consumer_name :"+consumer_name);
				String consumer_mobnumber=(String)ht.get("consumer_mobnumber");
				LogLevel.DEBUG(5,new Throwable(),"consumer_mobnumber :"+consumer_mobnumber);
				String consumer_emailid=(String)ht.get("consumer_emailid");
				LogLevel.DEBUG(5,new Throwable(),"consumer_emailid :"+consumer_emailid);
				String retailer_name=(String)ht.get("retailer_name");
				LogLevel.DEBUG(5,new Throwable(),"retailer_name :"+retailer_name);
				String retailer_mobilnumber=(String)ht.get("retailer_mobilnumber");
				LogLevel.DEBUG(5,new Throwable(),"retailer_mobilnumber :"+retailer_mobilnumber);
				String retailer_emailid=(String)ht.get("retailer_emailid");
				LogLevel.DEBUG(5,new Throwable(),"retailer_emailid :"+retailer_emailid);
				
				String city=(String)ht.get("city");
				LogLevel.DEBUG(5,new Throwable(),"city :"+city);		
				String area=(String)ht.get("area");
				LogLevel.DEBUG(5,new Throwable(),"area :"+area);		
				String pincode=(String)ht.get("pincode");
				LogLevel.DEBUG(5,new Throwable(),"pincode :"+pincode);		
						
				String trolley_brand=(String)ht.get("trolley_brand");
				LogLevel.DEBUG(5,new Throwable(),"trolley_brand :"+trolley_brand);		
				String trolley_model=(String)ht.get("trolley_model");
				LogLevel.DEBUG(5,new Throwable(),"trolley_model :"+trolley_model);		
				String price=(String)ht.get("price");
				LogLevel.DEBUG(5,new Throwable(),"price :"+price);		
						
				String quantity=(String)ht.get("quantity");
				LogLevel.DEBUG(5,new Throwable(),"quantity :"+quantity);		
				
				String order_status=(String)ht.get("order_status");
				LogLevel.DEBUG(5,new Throwable(),"order_status :"+order_status);
				String order_reasons=(String)ht.get("order_reasons");
				LogLevel.DEBUG(5,new Throwable(),"order_reasons :"+order_reasons);
				
				String order_agent_comments=(String)ht.get("order_agent_comments");
				LogLevel.DEBUG(5,new Throwable(),"order_agent_comments :"+order_agent_comments);
				 
				 
				String Website_Name="BookBattery";
				String Website_URL="www.bookbattery.com";
				
			  
				if(agentcomments.equals(""))
				{
					if(order_agent_comments == null || order_agent_comments.equals("") || order_agent_comments.equals("null") || order_agent_comments.equals(null))
					{
						order_agent_comments="";
					}
					else
					{
						order_agent_comments=order_agent_comments;

					}

				}
				else
				{
					if(order_agent_comments == null || order_agent_comments.equals("") || order_agent_comments.equals("null") || order_agent_comments.equals(null))
					{
						order_agent_comments=agentcomments+"-"+stroperatorName;
					}
					else
					{
						order_agent_comments=order_agent_comments+","+agentcomments+"-"+stroperatorName;
					}
				}


					order_agent_comments = order_agent_comments.replace("\\","\\\\"); order_agent_comments= order_agent_comments.replace("'","\\'"); order_agent_comments= order_agent_comments.replace("<","<"); order_agent_comments= order_agent_comments.replace("+","+"); order_agent_comments= order_agent_comments.replace("%u201C","&ldquo;"); order_agent_comments= order_agent_comments.replace("%u201D","&rdquo;"); order_agent_comments= order_agent_comments.replace("%20"," "); order_agent_comments= order_agent_comments.replace("%22","&#34;"); 
					order_agent_comments= order_agent_comments.replace("%27","\\'"); order_agent_comments= order_agent_comments.replace("%7E","~"); order_agent_comments= order_agent_comments.replace("%21","!"); order_agent_comments= order_agent_comments.replace("%23","#"); order_agent_comments= order_agent_comments.replace("%24","$"); order_agent_comments= order_agent_comments.replace("%25","%"); order_agent_comments= order_agent_comments.replace("%5E","^"); order_agent_comments= order_agent_comments.replace("%26","&");
					order_agent_comments= order_agent_comments.replace("%28","("); order_agent_comments= order_agent_comments.replace("%29",")"); order_agent_comments= order_agent_comments.replace("%7C","|"); order_agent_comments= order_agent_comments.replace("%7D","{"); order_agent_comments= order_agent_comments.replace("%7B","}"); order_agent_comments= order_agent_comments.replace("%3A",":"); order_agent_comments= order_agent_comments.replace("%3F","?"); order_agent_comments= order_agent_comments.replace("%3E",">");
					order_agent_comments= order_agent_comments.replace("%3C","<"); order_agent_comments= order_agent_comments.replace("%60","`"); order_agent_comments= order_agent_comments.replace("%3D","="); order_agent_comments= order_agent_comments.replace("%5C","\\\\"); order_agent_comments= order_agent_comments.replace("%5D","]"); order_agent_comments= order_agent_comments.replace("%5B","[");
					order_agent_comments= order_agent_comments.replace("%2C",",");order_agent_comments= order_agent_comments.replace("%3B",";");order_agent_comments= order_agent_comments.replace("%0A","\n");

				String strSqlQry ="";
				if(orderstatus.equals("postponed") || orderstatus.equals("Postponed"))
				{
					strSqlQry= "update trolley_order_details set order_status='"+orderstatus+"',order_reasons='"+orderreason+"',order_agent_comments='"+order_agent_comments+"',agent_name='"+stroperatorName+"',postpone_date='"+strpostponedate+"',updated_date=now(),installed_date='0000-00-00 00:00:00' WHERE  order_number='"+ordernumber+"' and ord_id='"+orderid+"'";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
				}
				else if(orderstatus.equals("Customer Contacted") && orderreason.equals("installed"))
				{
						
					                
					strSqlQry= "update trolley_order_details set order_status='"+orderstatus+"',order_reasons='"+orderreason+"',order_agent_comments='"+order_agent_comments+"',agent_name='"+stroperatorName+"',payment_mode='"+payment_mode+"', updated_date=now(),installed_date=now(),postpone_date='0000-00-00' WHERE  order_number='"+ordernumber+"' and ord_id='"+orderid+"'";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
					
				}
				else
				{

					strSqlQry= "update trolley_order_details set order_status='"+orderstatus+"',order_reasons='"+orderreason+"',order_agent_comments='"+order_agent_comments+"',agent_name='"+stroperatorName+"',updated_date=now(),installed_date='0000-00-00 00:00:00',postpone_date='0000-00-00' WHERE  order_number='"+ordernumber+"' and ord_id='"+orderid+"'";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
				}
				
				int i=qm.executeUpdate(strSqlQry);
				if(i <0)
				{
					LogLevel.DEBUG(1,new Throwable(),"No Name exists u can continue ");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#00dd00' class='vrb10'></font> ");
					out.println("Failed to update ordered status!");
				}
				else
				{

				if(orderstatus.equals("Customer Contacted") && orderreason.equals("installed"))
				{

				String Get_Cust_Details_SQL = "select consumer_name,consumer_mobnumber,consumer_emailid,trolley_brand,trolley_model,city,quantity from trolley_order_details  WHERE  order_number='"+ordernumber+"' and ord_id='"+orderid+"'";
				LogLevel.DEBUG(5,new Throwable(),"Get_Cust_Details_SQL:"+Get_Cust_Details_SQL );			
				Hashtable Get_Cust_Details_HT = qm.getRow(Get_Cust_Details_SQL);
				String cust_name=(String)Get_Cust_Details_HT.get("consumer_name");
				LogLevel.DEBUG(5,new Throwable(),"cust_name:"+cust_name );
				String cust_mobile_number=(String)Get_Cust_Details_HT.get("consumer_mobnumber");
				LogLevel.DEBUG(5,new Throwable(),"cust_mobile_number:"+cust_mobile_number );
				String cust_emailid=(String)Get_Cust_Details_HT.get("consumer_emailid");
				LogLevel.DEBUG(5,new Throwable(),"cust_emailid:"+cust_emailid );
				String trolleybrand=(String)Get_Cust_Details_HT.get("trolley_brand");
				LogLevel.DEBUG(5,new Throwable(),"trolleybrand:"+trolleybrand );
				String trolleymodel=(String)Get_Cust_Details_HT.get("trolley_model");
				LogLevel.DEBUG(5,new Throwable(),"trolleymodel:"+trolleymodel );
				String cust_city=(String)Get_Cust_Details_HT.get("city");	
				LogLevel.DEBUG(5,new Throwable(),"cust_city:"+cust_city);	 
					//String serverURL="192.168.1.7";	
					String satisfy_flag;	
					String DomainCusName;	

					/** Code starts here to send SMS to Consumer **/
					String DomainName="BookBattery.com";
					String ThankUSMSMsg2="";
 
						DomainName="BookBattery";
						LogLevel.DEBUG(5, new Throwable(), "DomainName"+DomainName);
						googleshorturl="https://goo.gl/RnpFVA";

					 

					if(rating.equals("Satisfied"))
					{										
					String Get_order_id_SQL ="select DISTINCT order_id from customer_rating_details where order_id='"+ordernumber+"'"; 
					LogLevel.DEBUG(5, new Throwable(), "Get_order_id_SQL :" + Get_order_id_SQL);

					Hashtable Get_order_id_SQL_HT = qm.getRow(Get_order_id_SQL);
					LogLevel.DEBUG(5, new Throwable(), "Get_order_id_SQL_HT :" + Get_order_id_SQL_HT);

					if(Get_order_id_SQL_HT.isEmpty())
					{
					String SteSqlQry = "insert into customer_rating_details(s_id,name,order_id,email,mobile_number,city,batterytype,rating_comments,no_of_rating,sms_link_url,short_url,creation_date,updated_date,sent_flag,upd_comm_flag,satisfy_flag) values(NULL,'"+cust_name+"','"+ordernumber+"','"+cust_emailid+"','"+cust_mobile_number+"','"+cust_city+"','Trolley','','','"+SMSURL+"','"+shorturl+"',now(),now(),'No','No','Yes')";	

					LogLevel.DEBUG(5,new Throwable(),"SteSqlQry:"+SteSqlQry );

					int reslt = qm.executeUpdate(SteSqlQry);

					if(reslt <0)
					{
					LogLevel.DEBUG(1,new Throwable(),"Failed to update your details! ");
					}
					else
					{
					ThankUSMSMsg2 ="Your Order with "+DomainName+" , Ref No: "+ordernumber+" has been successfully installed. Please provide your valuable ratings by clicking "+googleshorturl+" "; 

					//Your Order with XXXX , Ref No: XXXX has been successfully installed. Please provide your valuable ratings by clicking XXXX 

					// ThankUSMSMsg2 ="Your Order with "+DomainName+" , Ref No: "+ordernumber+" has been Cancelled. For any kind of Assistance please call to "+DomainMobNumber+" .";
					LogLevel.DEBUG(5,new Throwable(),"Satisfied ThankUSMSMsg2:"+ThankUSMSMsg2 );
					}

					}
					else
					{
					ThankUSMSMsg2 ="Your Order with "+DomainName+" , Ref No: "+ordernumber+" has been successfully installed. Please provide your valuable ratings by clicking "+googleshorturl+" "; 

					LogLevel.DEBUG(5,new Throwable(),"Not inserted already exists");

					}

					//LogLevel.DEBUG(5,new Throwable(),"Not Satisfied ThankUSMSMsg2:"+ThankUSMSMsg2 );
					LogLevel.DEBUG(5,new Throwable(),"strIpAddress:"+strIpAddress );
					LogLevel.DEBUG(5,new Throwable(),"strPort:"+strPort );
					LogLevel.DEBUG(5,new Throwable(),"SMSFromAddress:"+SMSFromAddress );
					LogLevel.DEBUG(5,new Throwable(),"consumer_mobnumber:"+consumer_mobnumber );

					String strSMSResponse3=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUSMSMsg2, consumer_mobnumber);
					LogLevel.DEBUG(5,new Throwable(),"strSMSResponse3:"+strSMSResponse3 );
					//ThankUSMSMsg2="Your ordered battery on "+battery_brand+" "+battery_model+" with order number "+order_number+" has been successfully installed by "+retailer_name+".";	
					LogLevel.DEBUG(5,new Throwable(),"Not Satisfied ThankUSMSMsg2:"+ThankUSMSMsg2 );

					}
					else
					{
						/*Condition Starts here for not satisfied Customer*/

						String Get_order_id_SQL ="select DISTINCT order_id from customer_rating_details where order_id='"+ordernumber+"'"; 
						LogLevel.DEBUG(5, new Throwable(), "Get_order_id_SQL :" + Get_order_id_SQL);

						Hashtable Get_order_id_SQL_HT = qm.getRow(Get_order_id_SQL);
						LogLevel.DEBUG(5, new Throwable(), "Get_order_id_SQL_HT :" + Get_order_id_SQL_HT);

						if(Get_order_id_SQL_HT.isEmpty())
						{
						String SteSqlQry = "insert into customer_rating_details(s_id,name,order_id,email,mobile_number,city,batterytype,rating_comments,no_of_rating,sms_link_url,short_url,creation_date,updated_date,sent_flag,upd_comm_flag,satisfy_flag) values(NULL,'"+cust_name+"','"+ordernumber+"','"+cust_emailid+"','"+cust_mobile_number+"','"+cust_city+"','Trolley','','','"+SMSURL+"','"+shorturl+"',now(),now(),'No','No','No')";	

						LogLevel.DEBUG(5,new Throwable(),"SteSqlQry:"+SteSqlQry );

						int reslt = qm.executeUpdate(SteSqlQry);

						if(reslt <0)
						{
						LogLevel.DEBUG(1,new Throwable(),"Failed to update your details! ");
						}
						else
						{
						ThankUSMSMsg2 ="Your Order with "+DomainName+" , Ref No: "+ordernumber+" has been successfully installed."; 
						LogLevel.DEBUG(5, new Throwable(), "ThankUSMSMsg2 :" + ThankUSMSMsg2);

						//Your Order with XXXX , Ref No: XXXX has been successfully installed. Please provide your valuable ratings by clicking XXXX 

						// ThankUSMSMsg2 ="Your Order with "+DomainName+" , Ref No: "+ordernumber+" has been Cancelled. For any kind of Assistance please call to "+DomainMobNumber+" .";
						LogLevel.DEBUG(5,new Throwable(),"Satisfied ThankUSMSMsg2:"+ThankUSMSMsg2 );
						}

						}
						else
						{
						ThankUSMSMsg2 ="Your Order with "+DomainName+" , Ref No: "+ordernumber+" has been successfully installed."; 
						LogLevel.DEBUG(5, new Throwable(), "ThankUSMSMsg2 :" + ThankUSMSMsg2);
						LogLevel.DEBUG(5,new Throwable(),"Not inserted already exists");

						}

						//LogLevel.DEBUG(5,new Throwable(),"Not Satisfied ThankUSMSMsg2:"+ThankUSMSMsg2 );
						LogLevel.DEBUG(5,new Throwable(),"strIpAddress:"+strIpAddress );
						LogLevel.DEBUG(5,new Throwable(),"strPort:"+strPort );
						LogLevel.DEBUG(5,new Throwable(),"SMSFromAddress:"+SMSFromAddress );
						LogLevel.DEBUG(5,new Throwable(),"consumer_mobnumber:"+consumer_mobnumber );

						String strSMSResponse3=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUSMSMsg2, consumer_mobnumber);
						LogLevel.DEBUG(5,new Throwable(),"strSMSResponse3:"+strSMSResponse3 );
						//ThankUSMSMsg2="Your ordered battery on "+battery_brand+" "+battery_model+" with order number "+order_number+" has been successfully installed by "+retailer_name+".";	
						LogLevel.DEBUG(5,new Throwable(),"Not Satisfied ThankUSMSMsg2:"+ThankUSMSMsg2 );

						}

						/** Code starts here to send SMS to Consumer **/
	 

				}

				if(orderstatus.equals("Customer Contacted") && orderreason.equals("Cancelled"))
				{

					strSqlQry= "update trolley_order_details set 					order_status='"+orderstatus+"',order_reasons='"+orderreason+"',order_agent_comments='"+order_agent_comments+"		',agent_name='"+stroperatorName+"',updated_date=now(),installed_date='0000-00-00 00:00:00',postpone_date='0000-00-00' WHERE  order_number='"+ordernumber+"' and ord_id='"+orderid+"'";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );

				}


					LogLevel.DEBUG(1,new Throwable(),"");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'></font> ");
					out.println("Successfully Updated order Status as "+orderstatus+".!");
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
		* This method is to fetch battery details details.
		* @strSqlQry : carries the query to select battery details from battery_details table.
		* @strSqlQry1 : Query has multiple pages to select battery details from battery_details table.
		\* **************************************************************************************************************************************/
		public String gettrolleysalesreport(HttpServletRequest req,HttpServletResponse res,HttpSession session)
		{
			String strRes="";
			String strConditions="";
			
			String strbrandConditions="";
			 
			String strfromdate  = (req.getParameter("fromdate")!=null)?req.getParameter("fromdate"):"";
			LogLevel.DEBUG(5, new Throwable(), "strfromdate :"+ strfromdate);
			
			String strtodate  = (req.getParameter("todate")!=null)?req.getParameter("todate"):"";
			LogLevel.DEBUG(5, new Throwable(), "strtodate :"+ strtodate);
			
			String strtrolley_brand  = (req.getParameter("trolley_brand")!=null)?req.getParameter("trolley_brand"):"";
			LogLevel.DEBUG(5, new Throwable(), "strtrolley_brand :"+ strtrolley_brand);
			
			String strstore_name  = (req.getParameter("store_name")!=null)?req.getParameter("store_name"):"";
			LogLevel.DEBUG(5, new Throwable(), "strstore_name :"+ strstore_name);
			
			strConditions="date(installed_date) between '"+strfromdate+"' and '"+strtodate+"'  and retailer_name='"+strstore_name+"' ";
			LogLevel.DEBUG(5, new Throwable(), "strConditions :"+ strConditions);
				
			if(strtrolley_brand.equals("All")) {
				
				strbrandConditions = "";
				
			} else {
				
				strbrandConditions = " and trolley_brand='"+strtrolley_brand+"'";
			}
			
			
	 		try
			
	 		{
				
	 			 //String strSqlQry ="select a.consumer_name,a.price,a.witholdbatprice,(((Case a.order_type WHEN 'New' THEN price ELSE a.witholdbatprice END)*a.quantity)+a.delivery_charge) as amount_paid_customer,a.delivery_charge,a.order_type,a.quantity,a.payment_mode,a.battery_model,a.battery_brand,a.bat_type,date(a.installed_date) as installed_date,a.state,b.city,b.erp_pre_tax from battery_order_details a, batteryprice b where "+strConditions+" "+strbrandConditions+" and a.battery_model=b.bat_model and a.city=b.city order by date(a.installed_date) asc";
	 			String strSqlQry ="select consumer_name,price,((price)*quantity) as amount_paid_customer,quantity,payment_mode,trolley_model,trolley_brand,date_format(creation_date, '%d-%m-%Y') as installed_date,state,city,erp_pre_tax from trolley_order_details where "+strConditions+" "+strbrandConditions+" order by date(installed_date) asc";
	 			
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
				
				Vector MisTrolleyVector=qm.executeQuery(strSqlQry);
				session.setAttribute("sesMisTrolleyVector",MisTrolleyVector);
				LogLevel.DEBUG(5,new Throwable(),"MisTrolleyVector:"+MisTrolleyVector);
				 
				 
				res.sendRedirect("../jsp/batterywalestore/mis/trolleysalesdetails.jsp");
				
				return strRes;
				
			}
			catch (Exception e)
			{
				LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
				e.printStackTrace();
			}
			
			return strRes;
					
				 
		} 		
	
}