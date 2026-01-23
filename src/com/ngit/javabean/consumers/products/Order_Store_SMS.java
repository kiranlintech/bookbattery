package com.ngit.javabean.consumers.products;
import com.ngit.javabean.qrymgr.QueryManager;
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


public class Order_Store_SMS 
{
	private ServletContext context; 
	QueryManager qmer;
	static final long serialVersionUID=21;
	/*This init method initializes the necessary connection pools and perform the transactions on the pools from respectvie files. */
	String Browser_URL="";
	String baseUrl ="";
	String baseurldirectory ="";
	String OperatorTeamCount="";
	String strIpAddress ="";
	String strPort="9910";
	String SMSFromAddress="ASSIST";

	
	public void init(ServletConfig config)throws ServletException
	{
		
		try
		{
			String strMOPConfig = "/bookbattery/properties/bookbatteryconfig.properties"; 
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
    public Order_Store_SMS(QueryManager qm) 
	{
        qmer=qm;
    }
	/* *************************************************************************************************************\
	* This method is used to Send SMS Order Details to Customer
	\* *************************************************************************************************************/
	public String Send_Order_SMS(HttpServletRequest req , HttpServletResponse res,HttpSession session, String OrderNumber, String Order_Type,String SMS_Customer,String SMS_Retailer,String SMS_Operator,String strorderreasons) 
	{

		SendSMS Send_SMS = new SendSMS(qmer);
		
		/*####  Variables Declaration  ###*/
		String CN ="";
		String CMN ="";
		String RN ="";
		String RM ="";
		String BB ="";
		String BM ="";
		String PRI ="";
		String WOOBOPRI ="";
		String BOT ="";
		String BT ="";
		String QTY ="";
		String PM ="";
		String DM ="";
		String VN ="";
		String VM ="";
		String DC ="";
		String ST ="";
		String strRes ="";
		String Area ="";
		String CTY ="";
		String SP ="";
		String DiPRI ="";
		String STATE ="";
		String SType ="";
		String Area_Pincode ="";
		
		
		String IB ="";
		String IM ="";
		String IC ="";
		
		String TB = "";
		String TM = "";
		
		String SMS_For_Customer="";
		String SMS_For_Customer1="";
		String SMS_For_Retailer="";
		String SMS_For_Operator="";
		String SMS_For_ME_BM_msg="";
		
		String SMS_Domain="BookBattery";
		String SMS_Contact="9603467559";
		String Website_URL="www.bookbattery.com";
		
		String Retailer_Name_truncated="";
		String CancelledCustomerMesg="";
		

		String googleshorturl1= "https://goo.gl/eKGqvC";
		LogLevel.DEBUG(5, new Throwable(), "googleshorturl1 :" + googleshorturl1);		
		
		String googleshorturl= "https://goo.gl/RnpFVA";
		LogLevel.DEBUG(5, new Throwable(), "googleshorturl1 :" + googleshorturl);
		
		if (Order_Type=="Battery" || Order_Type.equals("Battery"))
		{
			LogLevel.DEBUG(5, new Throwable(), "OrderNumber :" + OrderNumber);
			LogLevel.DEBUG(5, new Throwable(), "SMS_Retailer :" + SMS_Retailer);
			LogLevel.DEBUG(5, new Throwable(), "Order_Type :" + Order_Type);
			String Get_Order_Details ="select consumer_mobnumber, consumer_name,retailer_name,area, retailer_mobilnumber, battery_brand, battery_model, price, witholdbatprice, order_type, quantity, veh_name, veh_model, bat_type, payment_mode,city,state,delivery_mode,delivery_charge from battery_order_details where order_number ='"+OrderNumber+"'";
			LogLevel.DEBUG(5, new Throwable(), "Get_Order_Details :" + Get_Order_Details);
			
			Hashtable Get_Order_Details_HT = qmer.getRow(Get_Order_Details);
			
			LogLevel.DEBUG(5, new Throwable(), "Get_Order_Details_HT :" + Get_Order_Details_HT);

			if(Get_Order_Details_HT.isEmpty())
			{
				strRes="Some thing went wrong, Please contact your DevOps Team";
				return strRes;
			}
			else
			{
				CMN=(String)Get_Order_Details_HT.get("consumer_mobnumber");
				LogLevel.DEBUG(5, new Throwable(), "consumer_mobnumber :" + CMN);
				
				CN=(String)Get_Order_Details_HT.get("consumer_name");
				LogLevel.DEBUG(5, new Throwable(), "consumer_name :" + CN);
				
				RN=(String)Get_Order_Details_HT.get("retailer_name");
				LogLevel.DEBUG(5, new Throwable(), "retailer_name :" + RN);
				
				Area=(String)Get_Order_Details_HT.get("area");
				LogLevel.DEBUG(5, new Throwable(), "area :" + Area);
				
				RM=(String)Get_Order_Details_HT.get("retailer_mobilnumber");
				LogLevel.DEBUG(5, new Throwable(), "retailer_mobilnumber :" + RM);
				
				BB=(String)Get_Order_Details_HT.get("battery_brand");
				LogLevel.DEBUG(5, new Throwable(), "battery_brand :" + BB);
				
				BM =(String)Get_Order_Details_HT.get("battery_model");
				LogLevel.DEBUG(5, new Throwable(), "battery_model :" + BM);
				
				PRI=(String)Get_Order_Details_HT.get("price");
				LogLevel.DEBUG(5, new Throwable(), "price :" + PRI);
				
				WOOBOPRI=(String)Get_Order_Details_HT.get("witholdbatprice");
				LogLevel.DEBUG(5, new Throwable(), "witholdbatprice :" + WOOBOPRI);
				
				BOT=(String)Get_Order_Details_HT.get("order_type");
				LogLevel.DEBUG(5, new Throwable(), "order_type :" + BOT);
				
				BT=(String)Get_Order_Details_HT.get("bat_type");
				LogLevel.DEBUG(5, new Throwable(), "bat_type :" + BT);
				
				QTY=(String)Get_Order_Details_HT.get("quantity");
				LogLevel.DEBUG(5, new Throwable(), "quantity :" + QTY);
				
				String PAYMODE=(String)Get_Order_Details_HT.get("payment_mode");
				PM = PAYMODE.replace(" ","");
				
				VN=(String)Get_Order_Details_HT.get("veh_name");
				LogLevel.DEBUG(5, new Throwable(), "veh_name :" + VN);				
				
				VM=(String)Get_Order_Details_HT.get("veh_model");
				LogLevel.DEBUG(5, new Throwable(), "veh_model :" + VM);				
				
				DM=(String)Get_Order_Details_HT.get("delivery_mode");
				LogLevel.DEBUG(5, new Throwable(), "delivery_mode :" + DM);				
				
				DC=(String)Get_Order_Details_HT.get("delivery_charge");
				LogLevel.DEBUG(5, new Throwable(), "delivery_charge :" + DC);
				
				ST=(String)Get_Order_Details_HT.get("state");
				LogLevel.DEBUG(5, new Throwable(), "state :" + ST);
				
				CTY=(String)Get_Order_Details_HT.get("city");
				LogLevel.DEBUG(5, new Throwable(), "city :" + CTY);
				
				int Price_Temp=0;
				int QTY_int=0;
				
				if (BOT=="New" || BOT.equals("New"))
				{
					Price_Temp = Integer.parseInt(PRI);
				}
				else
				{
					Price_Temp = Integer.parseInt(WOOBOPRI);
				}
				
				QTY_int = Integer.parseInt(QTY);
				int Final_Price_Int=Price_Temp*QTY_int;
				String Final_Price = Integer.toString(Final_Price_Int);
				
				String Retailer_Price_WOB = Integer.toString(Integer.parseInt(PRI)*QTY_int);
				String Retailer_Price_WoutOldB= Integer.toString(Integer.parseInt(WOOBOPRI)*QTY_int);
				String Retailer_Final_Price=Retailer_Price_WOB+", OBRP Rs:"+Retailer_Price_WoutOldB;
				
				SMS_Domain=SMS_Domain.trim();
				OrderNumber=OrderNumber.trim();
				Website_URL=Website_URL.trim();
				BB=BB.trim();
				BM=BM.trim();
				BOT=BOT.trim();
				QTY=QTY.trim();
				VN=VN.trim();
				VM=VM.trim();
				DM=DM.trim();
				DC=DC.trim();
				Final_Price=Final_Price.trim();
				SMS_Contact=SMS_Contact.trim();
				RM=RM.trim();
				CN=CN.trim();
				
				/*String[] Retailer_Name_split_array=RN.split("-");
				
				LogLevel.DEBUG(5, new Throwable(), "Retailer_Name_split_array :" + Retailer_Name_split_array);
				
				Retailer_Name_truncated=Retailer_Name_split_array[0];
				
				LogLevel.DEBUG(5, new Throwable(), "Retailer_Name_truncated :" + Retailer_Name_truncated);*/
				
				if((BT=="Bike Batteries" || BT.equals("Bike Batteries"))&& (DM=="Yes" || DM.equals("Yes")))
				{
					Final_Price=Final_Price+", Delivery Cost: "+DC;
					Retailer_Final_Price=Retailer_Final_Price+", Delivery Cost: "+DC;
				}
				else
				{
					Final_Price=Final_Price;
					Retailer_Final_Price=Retailer_Final_Price;
				}

				LogLevel.DEBUG(5, new Throwable(), "Final_Price if yes:" + Final_Price);
				/**********************Code Based on Status ******************/
				if(strorderreasons.equals("cancelled")){
					
					if (BT=="Inverter Batteries" || BT.equals("Inverter Batteries"))
					{
						SMS_For_Customer="Your ordered battery on "+BB+" "+BM+" with order number "+OrderNumber+" has been cancelled "+CancelledCustomerMesg+".  For any kind of Assistance please call to 9603467559.\r\n\r\n We are looking forward to see you again at "+Website_URL+" .";
						
					} else {
						
						SMS_For_Customer="Your ordered battery on "+BB+" "+BM+" for "+VN+" "+VM+" with order number "+OrderNumber+" has been cancelled "+CancelledCustomerMesg+".  For any kind of Assistance please call to 9603467559. \r\n\r\n We are looking forward to see you again at "+Website_URL+" .";
						
					}
			
				} else if(strorderreasons.equals("installed")){
					
					 SMS_For_Customer="Your Order with BookBattery.com , Ref No: "+OrderNumber+" has been successfully installed. Please provide your valuable ratings by clicking "+googleshorturl1+" ";
					 
					
				} else {
					
					if (BT=="Bike Batteries" || BT.equals("Bike Batteries"))
					{

						if(DM=="Yes" || DM.equals("Yes"))
						{
							SMS_For_Customer="Thanks for ordering with "+SMS_Domain+". Your Order Details No: "+OrderNumber+", "+BB+", "+BM+", Battery Type: "+BOT+", Qty: "+QTY+", Price: Rs "+Final_Price+". You will shortly receive a call. For any kind of assistance please call to "+SMS_Contact+"";
						}
						else
						{
							 SMS_For_Customer="Thanks for ordering with "+SMS_Domain+". Your Order Details No: "+OrderNumber+", "+BB+", "+BM+", Battery Type: "+BOT+", Qty: "+QTY+", Price: Rs "+Final_Price+". Please collect the battery at the store. For further info check your MailBox";
						}
						 
						 LogLevel.DEBUG(5, new Throwable(), "SMS_For_Customer :" + SMS_For_Customer);

					}
					else
					{
						SMS_For_Customer="Thanks for ordering with "+SMS_Domain+". Your Order Details No: "+OrderNumber+", "+BB+", "+BM+", Battery Type: "+BOT+", Qty: "+QTY+", Price: Rs "+Final_Price+". You will shortly receive a call. For any kind of assistance please call to "+SMS_Contact+" ";
						
						LogLevel.DEBUG(5, new Throwable(), "SMS_For_Customer :" + SMS_For_Customer);
					}
					
				} 
				
				/**********************Code Based on Status ******************/
				LogLevel.DEBUG(5, new Throwable(), "SMS_For_Customer :" + SMS_For_Customer); 
				
				SMS_For_Customer=SMS_For_Customer.trim();
				
				/*################### Template for Retailer ##################*/
				
				SMS_Domain="BookBattery";
				SMS_Contact="9603467559"; 
				if(strorderreasons.equals("cancelled")){
					
					SMS_For_Retailer=""+SMS_Domain+" Consumer "+CN+", Mob no-"+CMN+" with Ord Ref No: "+OrderNumber+". Consumer Order got Cancelled. For any kind of Assistance please call to "+SMS_Contact+".";
					LogLevel.DEBUG(5, new Throwable(), "SMS_For_Retailer :" + SMS_For_Retailer);
				
				
				} else if(strorderreasons.equals("installed")){
					
					
					SMS_For_Retailer="The ordered battery with order number "+OrderNumber+" has been successfully installed. Please credit commission amount Rs. "+DC+" in the company bank account with in 72hours";
					LogLevel.DEBUG(5, new Throwable(), "SMS_For_Retailer :" + SMS_For_Retailer);
					
					
				} else {
					
					SMS_For_Retailer=""+SMS_Domain+" Cust Name: "+CN+" , Mob No: "+CMN+" , Order No: "+OrderNumber+" , ordered "+BB+" , Model: "+BM+" battery at Price: "+Retailer_Final_Price+" , Battery Replacement: "+BOT+" , Qty: "+QTY+" , Payment Mode: "+PM+" . Please install";
					LogLevel.DEBUG(5, new Throwable(), "SMS_For_Retailer :" + SMS_For_Retailer);
					
			
					
				}
				
				/*################### Template for Operator ##################*/
			
				SMS_For_Operator="Cust Name: "+CN+" , Mob No: "+CMN+" , Order No: "+OrderNumber+" ordered "+BB+" , Model: "+BM+" Battery. Price: "+Final_Price+" Battery Replacement: "+BOT+" , Qty: "+QTY+" , Payment Mode: "+PM+"";
				LogLevel.DEBUG(5, new Throwable(), "SMS_For_Operator :" + SMS_For_Operator);
			}
		}
		else if (Order_Type=="Inverter" ||  Order_Type.equals("Inverter"))
		{
			LogLevel.DEBUG(5, new Throwable(), "OrderNumber :" + OrderNumber);
			
			String Get_Order_Details ="select consumer_mobnumber, consumer_name, retailer_name, retailer_mobile_number, inverter_brand, inverter_model, price, quantity, payment_mode, state from inverter_order_details where order_number ='"+OrderNumber+"'";
			LogLevel.DEBUG(5, new Throwable(), "Get_Order_Details :" + Get_Order_Details);
			
			Hashtable Get_Order_Details_HT = qmer.getRow(Get_Order_Details);
			
			LogLevel.DEBUG(5, new Throwable(), "Get_Order_Details_HT :" + Get_Order_Details_HT);

			if(Get_Order_Details_HT.isEmpty())
			{
				strRes="Some thing went wrong, Please contact your DevOps Team";
				return strRes;
			}
			else
			{
				CMN=(String)Get_Order_Details_HT.get("consumer_mobnumber");
				LogLevel.DEBUG(5, new Throwable(), "consumer_mobnumber :" + CMN);
				
				CN=(String)Get_Order_Details_HT.get("consumer_name");
				LogLevel.DEBUG(5, new Throwable(), "consumer_name :" + CN);
				
				RN=(String)Get_Order_Details_HT.get("retailer_name");
				LogLevel.DEBUG(5, new Throwable(), "retailer_name :" + RN);
				
				RM=(String)Get_Order_Details_HT.get("retailer_mobile_number");
				LogLevel.DEBUG(5, new Throwable(), "retailer_mobile_number :" + RM);
				
				IB=(String)Get_Order_Details_HT.get("inverter_brand");
				LogLevel.DEBUG(5, new Throwable(), "inverter_brand :" + BB);
				
				IM =(String)Get_Order_Details_HT.get("inverter_model");
				LogLevel.DEBUG(5, new Throwable(), "inverter_model :" + BM);
				
				IC=(String)Get_Order_Details_HT.get("inverter_capacity");
				LogLevel.DEBUG(5, new Throwable(), "inverter_capacity :" + IC);
				
				PRI=(String)Get_Order_Details_HT.get("price");
				LogLevel.DEBUG(5, new Throwable(), "price :" + PRI);
				
				QTY=(String)Get_Order_Details_HT.get("quantity");
				LogLevel.DEBUG(5, new Throwable(), "quantity :" + QTY);
				
				String PAYMODE=(String)Get_Order_Details_HT.get("payment_mode");
				PM = PAYMODE.replace(" ","");
				LogLevel.DEBUG(5, new Throwable(), "payment_mode :" + PM);
								
				ST=(String)Get_Order_Details_HT.get("state");
				LogLevel.DEBUG(5, new Throwable(), "state :" + ST);
				
				int Final_Price_Int=Integer.parseInt(PRI)*Integer.parseInt(QTY);
				String Final_Price = Integer.toString(Final_Price_Int);
				SMS_Domain="BookBattery";
				
				if(strorderreasons.equals("cancelled")){
					
					
					/*################### Template for Customer ##################*/
						
					SMS_For_Customer="Your Order with "+SMS_Domain+" , Ref No: "+OrderNumber+" has been Cancelled. For any kind of Assistance please call to "+SMS_Contact+" .";
					LogLevel.DEBUG(5, new Throwable(), "SMS_For_Customer :" + SMS_For_Customer);
				
					/*################### Template for Retailer ##################*/ 
					 
					SMS_For_Retailer="BookBattery.com Consumer "+CN+" , Mob no-"+CMN+" with Ord Ref No: "+OrderNumber+" . Consumer Order got Cancelled. For any kind of Assistance please call to 9603467559 .";
					LogLevel.DEBUG(5, new Throwable(), "SMS_For_Retailer :" + SMS_For_Retailer);
					
					/*################### Template for Operator ##################*/
				
					SMS_For_Operator="BookBattery.com Consumer "+CN+" , Mob no-"+CMN+" with Ord Ref No: "+OrderNumber+" . Consumer Order got Cancelled. For any kind of Assistance please call to 9603467559 .";
					LogLevel.DEBUG(5, new Throwable(), "SMS_For_Operator :" + SMS_For_Operator);
					
				} else if(strorderreasons.equals("installed")){
					
					/*################### Template for Customer ##################*/
						
					 SMS_For_Customer="Your Order with BookBattery.com , Ref No: "+OrderNumber+" has been successfully installed. Please provide your valuable ratings by clicking "+googleshorturl1+" ";
					 
					LogLevel.DEBUG(5, new Throwable(), "SMS_For_Customer :" + SMS_For_Customer);
				
					/*################### Template for Retailer ##################*/ 
					 
					SMS_For_Retailer="The ordered inverter with order number "+OrderNumber+" has been successfully installed. Order Details Customer Name- "+CN+", Customer Mobile Num- "+CMN+", Inverter Brand- "+IB+", Inverter Model- "+IM+", Inverter Capacity- "+IC+"";
					LogLevel.DEBUG(5, new Throwable(), "SMS_For_Retailer :" + SMS_For_Retailer);
					
					/*################### Template for Operator ##################*/
				
					SMS_For_Operator="The ordered inverter with order number "+OrderNumber+" has been successfully installed. Order Details Customer Name- "+CN+", Customer Mobile Num- "+CMN+", Inverter Brand- "+IB+", Inverter Model- "+IM+", Inverter Capacity- "+IC+"";
					LogLevel.DEBUG(5, new Throwable(), "SMS_For_Operator :" + SMS_For_Operator);
					
				} else {
					
					/*################### Template for Customer ##################*/
						
					SMS_For_Customer="Thanks for ordering with "+SMS_Domain+" . Your Order No: "+OrderNumber+" , Brand: "+IB+" , Model: "+IM+" , Qty: "+QTY+" , Discount Price: Rs "+Final_Price+" . You will shortly receive a call. For any kind of assistance please call to "+SMS_Contact+"";
					LogLevel.DEBUG(5, new Throwable(), "SMS_For_Customer :" + SMS_For_Customer);
				
					/*################### Template for Retailer ##################*/
					
					SMS_Domain="BookBattery";
									
					SMS_For_Retailer=""+SMS_Domain+" Cust Name: "+CN+" , Mob No: "+CMN+" , Order No: "+OrderNumber+" ordered "+IB+" Model: "+IM+" Inverter. Price: "+Final_Price+" , Qty: "+QTY+" , Payment Mode: "+PM+" . Please install";
					LogLevel.DEBUG(5, new Throwable(), "SMS_For_Retailer :" + SMS_For_Retailer);
					
					/*################### Template for Operator ##################*/
				
					SMS_For_Operator="Cust Name: "+CN+" , Mob No: "+CMN+" , Order No: "+OrderNumber+" ordered "+IB+" Model: "+IM+" Inverter. Price: "+Final_Price+" , Qty: "+QTY+" , Payment Mode: "+PM+"";
					LogLevel.DEBUG(5, new Throwable(), "SMS_For_Operator :" + SMS_For_Operator);
					
					
					
				}
				

				
				
			}
			/*************SMS for SErvices *********************/
		} else if (Order_Type=="Service" ||  Order_Type.equals("Service")){
			
			
LogLevel.DEBUG(5, new Throwable(), "OrderNumber :" + OrderNumber);
			
			String Get_Order_Details ="select consumer_emailid,consumer_mobnumber, consumer_name,consumer_address1, consumer_address2, retailer_name, retailer_emailid,retailer_mobilnumber, services_type, services_package, service_price_mrp, service_price_discount, payment_mode, state, area, pincode from service_order_details where order_number ='"+OrderNumber+"'";
			LogLevel.DEBUG(5, new Throwable(), "Get_Order_Details :" + Get_Order_Details);
			
			Hashtable Get_Order_Details_HT = qmer.getRow(Get_Order_Details);
			
			LogLevel.DEBUG(5, new Throwable(), "Get_Order_Details_HT :" + Get_Order_Details_HT);

			if(Get_Order_Details_HT.isEmpty())
			{
				strRes="Some thing went wrong, Please contact your DevOps Team";
				return strRes;
			}
			else
			{
			
				
				LogLevel.DEBUG(5, new Throwable(), "Get_Order_Details_HT :" + Get_Order_Details_HT);

				
					CMN=(String)Get_Order_Details_HT.get("consumer_mobnumber");
					LogLevel.DEBUG(5, new Throwable(), "consumer_mobnumber :" + CMN);
					
					CN=(String)Get_Order_Details_HT.get("consumer_name");
					LogLevel.DEBUG(5, new Throwable(), "consumer_name :" + CN);
					
					RN=(String)Get_Order_Details_HT.get("retailer_name");
					LogLevel.DEBUG(5, new Throwable(), "retailer_name :" + RN);
					
					RM=(String)Get_Order_Details_HT.get("retailer_mobilnumber");
					LogLevel.DEBUG(5, new Throwable(), "retailer_mobilnumber :" + RM);
					
					ST=(String)Get_Order_Details_HT.get("services_type");
					LogLevel.DEBUG(5, new Throwable(), "services_type :" + ST);
					
					SP=(String)Get_Order_Details_HT.get("services_package");
					LogLevel.DEBUG(5, new Throwable(), "services_package :" + SP);
					
					PRI=(String)Get_Order_Details_HT.get("service_price_mrp");
					LogLevel.DEBUG(5, new Throwable(), "service_price_mrp :" + PRI);
					
					DiPRI=(String)Get_Order_Details_HT.get("service_price_discount");
					LogLevel.DEBUG(5, new Throwable(), "service_price_discount :" + DiPRI);
					
					PM=(String)Get_Order_Details_HT.get("payment_mode");
					LogLevel.DEBUG(5, new Throwable(), "payment_mode :" + PM);
				
					if(strorderreasons.equals("cancelled")){
						
						/*################### Template for Customer ##################*/
						
						SMS_For_Customer="Your Order with "+SMS_Domain+" , Ref No: "+OrderNumber+" has been Cancelled. For any kind of Assistance please call to "+SMS_Contact+" .";
						LogLevel.DEBUG(5, new Throwable(), "SMS_For_Customer :" + SMS_For_Customer);
					
						/*################### Template for Retailer ##################*/ 
						 
						SMS_For_Retailer="BookBattery.com Consumer "+CN+" , Mob no-"+CMN+" with Ord Ref No: "+OrderNumber+" . Consumer Order got Cancelled. For any kind of Assistance please call to 9603467559 .";
						LogLevel.DEBUG(5, new Throwable(), "SMS_For_Retailer :" + SMS_For_Retailer);
						
						/*################### Template for Operator ##################*/
					
						SMS_For_Operator="BookBattery.com Consumer "+CN+" , Mob no-"+CMN+" with Ord Ref No: "+OrderNumber+" . Consumer Order got Cancelled. For any kind of Assistance please call to 9603467559 .";
						LogLevel.DEBUG(5, new Throwable(), "SMS_For_Operator :" + SMS_For_Operator);
						
						
					} else if(strorderreasons.equals("installed")){
						
/*################### Template for Customer	##################*/
						
						SMS_For_Customer="Your Order with BookBattery.com , Ref No: "+OrderNumber+" has been successfully installed. Please provide your valuable ratings by clicking "+googleshorturl+"";
					
						LogLevel.DEBUG(5, new Throwable(), "Customer_Battery_Order_Msg :" + SMS_For_Customer);
										
					/*################### Template for Retailer	##################*/
				
					   SMS_For_Retailer="The ordered services with order number "+OrderNumber+" has been successfully installed.";
						
						LogLevel.DEBUG(5, new Throwable(), "Retailer_Battery_Order_Msg :" + SMS_For_Retailer);
						
					/*################### Template for Operator	##################*/
						
						//SMS_For_Operator=""+SMS_Domain+" Ord No: "+OrderNumber.trim()+" and "+ST.trim()+" at Price: Rs. "+DiPRI.trim()+" and PM: "+PM.trim()+" .Cust Mob No: "+CMN.trim()+" Dealer Mob No: "+RM.trim()+" ";
						
						LogLevel.DEBUG(5, new Throwable(), "Operator_Battery_Order_Msg Empty:" + SMS_For_Operator);	
						
					} else {
						
						/*################### Template for Customer	##################*/
						
						SMS_For_Customer="Thanks for Booking "+ST.trim()+" with "+SMS_Domain.trim()+" . Order No: "+OrderNumber.trim()+" , Details: "+ST.trim()+" ,Price: Rs. "+DiPRI.trim()+" and Payment Mode: "+PM.trim().replaceAll(" ", "")+" . You will shortly receive a call. For any kind of assistance please call to "+SMS_Contact+"";
					
						LogLevel.DEBUG(5, new Throwable(), "Customer_Battery_Order_Msg :" + SMS_For_Customer);
										
					/*################### Template for Retailer	##################*/
				
						SMS_For_Retailer="Cust Name: "+CN.trim()+" Mob No: "+CMN.trim()+" & Order No: "+OrderNumber.trim()+" ordered "+ST.trim()+" at Price: Rs. "+DiPRI.trim()+" & Payment Mode: "+PM.trim().replaceAll(" ", "")+" . Please Contact";
						
						LogLevel.DEBUG(5, new Throwable(), "Retailer_Battery_Order_Msg :" + SMS_For_Retailer);
						
					/*################### Template for Operator	##################*/
						
						SMS_For_Operator=""+SMS_Domain+" Ord No: "+OrderNumber.trim()+" and "+ST.trim()+" at Price: Rs. "+DiPRI.trim()+" and PM: "+PM.trim().replaceAll(" ", "")+" .Cust Mob No: "+CMN.trim()+" Dealer Mob No: "+RM.trim()+" ";
						
						LogLevel.DEBUG(5, new Throwable(), "Operator_Battery_Order_Msg :" + SMS_For_Operator);
					}
			}
		} else if (Order_Type=="Trolley" ||  Order_Type.equals("Trolley")){
		
			String Get_Order_Details ="select order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailid,state,city,area,pincode,trolley_brand,trolley_model,price,payment_mode,consumer_address from trolley_order_details where order_number ='"+OrderNumber+"'";
			LogLevel.DEBUG(5, new Throwable(), "Get_Order_Details :" + Get_Order_Details);
			
			Hashtable Get_Order_Details_HT = qmer.getRow(Get_Order_Details);
			
			LogLevel.DEBUG(5, new Throwable(), "Get_Order_Details_HT :" + Get_Order_Details_HT);

			if(Get_Order_Details_HT.isEmpty())
			{
				strRes="Some thing went wrong, Please contact your DevOps Team";
				return strRes;
			}
			else
			{
			
				
				LogLevel.DEBUG(5, new Throwable(), "Get_Order_Details_HT :" + Get_Order_Details_HT);

				
					CMN=(String)Get_Order_Details_HT.get("consumer_mobnumber");
					LogLevel.DEBUG(5, new Throwable(), "consumer_mobnumber :" + CMN);
					
					CN=(String)Get_Order_Details_HT.get("consumer_name");
					LogLevel.DEBUG(5, new Throwable(), "consumer_name :" + CN);
					
					RN=(String)Get_Order_Details_HT.get("retailer_name");
					LogLevel.DEBUG(5, new Throwable(), "retailer_name :" + RN);
					
					RM=(String)Get_Order_Details_HT.get("retailer_mobilnumber");
					LogLevel.DEBUG(5, new Throwable(), "retailer_mobilnumber :" + RM);
					
					TB=(String)Get_Order_Details_HT.get("trolley_brand");
					LogLevel.DEBUG(5, new Throwable(), "trolley_brand :" + TB);
					
					TM=(String)Get_Order_Details_HT.get("trolley_model");
					LogLevel.DEBUG(5, new Throwable(), "trolley_model :" + TM);
					
					PRI=(String)Get_Order_Details_HT.get("price");
					LogLevel.DEBUG(5, new Throwable(), "price :" + PRI);
					
					PM=(String)Get_Order_Details_HT.get("payment_mode");
					LogLevel.DEBUG(5, new Throwable(), "payment_mode :" + PM);
				
					if(strorderreasons.equals("cancelled")){
						
						/*################### Template for Customer ##################*/
						
						SMS_For_Customer="Your Order with "+SMS_Domain+" , Ref No: "+OrderNumber+" has been Cancelled. For any kind of Assistance please call to "+SMS_Contact+" .";
						LogLevel.DEBUG(5, new Throwable(), "SMS_For_Customer :" + SMS_For_Customer);
					
						/*################### Template for Retailer ##################*/ 
						 
						SMS_For_Retailer="BookBattery.com Consumer "+CN+" , Mob no-"+CMN+" with Ord Ref No: "+OrderNumber+" . Consumer Order got Cancelled. For any kind of Assistance please call to 9603467559 .";
						LogLevel.DEBUG(5, new Throwable(), "SMS_For_Retailer :" + SMS_For_Retailer);
						
						/*################### Template for Operator ##################*/
					
						SMS_For_Operator="BookBattery.com Consumer "+CN+" , Mob no-"+CMN+" with Ord Ref No: "+OrderNumber+" . Consumer Order got Cancelled. For any kind of Assistance please call to 9603467559 .";
						LogLevel.DEBUG(5, new Throwable(), "SMS_For_Operator :" + SMS_For_Operator);
						
						
					} else if(strorderreasons.equals("installed")){
						
/*################### Template for Customer	##################*/
						
						SMS_For_Customer="Your Order with BookBattery.com , Ref No: "+OrderNumber+" has been successfully installed. Please provide your valuable ratings by clicking "+googleshorturl+"";
					
						LogLevel.DEBUG(5, new Throwable(), "Customer_Battery_Order_Msg :" + SMS_For_Customer);
										
					/*################### Template for Retailer	##################*/
				
					   SMS_For_Retailer="The ordered services with order number "+OrderNumber+" has been successfully installed.";
						
						LogLevel.DEBUG(5, new Throwable(), "Retailer_Battery_Order_Msg :" + SMS_For_Retailer);
						
					/*################### Template for Operator	##################*/
						
						//SMS_For_Operator=""+SMS_Domain+" Ord No: "+OrderNumber.trim()+" and "+ST.trim()+" at Price: Rs. "+DiPRI.trim()+" and PM: "+PM.trim()+" .Cust Mob No: "+CMN.trim()+" Dealer Mob No: "+RM.trim()+" ";
						
						LogLevel.DEBUG(5, new Throwable(), "Operator_Battery_Order_Msg Empty:" + SMS_For_Operator);	
						
					} else {
						
						/*################### Template for Customer	##################*/
						
						SMS_For_Customer="Thanks for Booking "+TM.trim()+" with "+SMS_Domain.trim()+" . Order No: "+OrderNumber.trim()+" , Details: "+TB.trim()+" ,Price: Rs. "+PRI.trim()+" and Payment Mode: "+PM.trim().replaceAll(" ", "")+" . You will shortly receive a call. For any kind of assistance please call to "+SMS_Contact+"";
					
						LogLevel.DEBUG(5, new Throwable(), "Customer_Battery_Order_Msg :" + SMS_For_Customer);
										
					/*################### Template for Retailer	##################*/
				
						SMS_For_Retailer="Cust Name: "+CN.trim()+" Mob No: "+CMN.trim()+" & Order No: "+OrderNumber.trim()+" ordered "+TM.trim()+" at Price: Rs. "+PRI.trim()+" & Payment Mode: "+PM.trim().replaceAll(" ", "")+" . Please Contact";
						
						LogLevel.DEBUG(5, new Throwable(), "Retailer_Battery_Order_Msg :" + SMS_For_Retailer);
						
					/*################### Template for Operator	##################*/
						
						SMS_For_Operator=""+SMS_Domain+" Ord No: "+OrderNumber.trim()+" and "+TB.trim()+" at Price: Rs. "+PM.trim()+" and PM: "+PM.trim().replaceAll(" ", "")+" .Cust Mob No: "+CMN.trim()+" Dealer Mob No: "+RM.trim()+" ";
						
						LogLevel.DEBUG(5, new Throwable(), "Operator_Battery_Order_Msg :" + SMS_For_Operator);
					}
			}
		}
		else
		{
			strRes="Some thing went wrong, Please contact your DevOps Team";
			return strRes;
		}		
		LogLevel.DEBUG(5, new Throwable(), "strIpAddress :" + strIpAddress);
		LogLevel.DEBUG(5, new Throwable(), "strPort :" + strPort);
		LogLevel.DEBUG(5, new Throwable(), "SMSFromAddress :" + SMSFromAddress);
		LogLevel.DEBUG(5, new Throwable(), "SMS_For_Customer :" + SMS_For_Customer);
		
		/*################### SMS for Customer ##################*/
		if( SMS_Customer.equals("Yes") || SMS_Customer.equals("yes") || SMS_Customer == "Yes" || SMS_Customer =="yes")
		{
			String Send_SMS_Response=  Send_SMS.sendSMS(strIpAddress,strPort,SMSFromAddress,SMS_For_Customer,CMN);
			LogLevel.DEBUG(5, new Throwable(), "Send_SMS_Response :" + Send_SMS_Response);
			
			strRes=strRes+"SMS sent to Customer "+CMN;
		}
		
		else{
			LogLevel.DEBUG(5, new Throwable(), "SMS Not Sent to Consumer");
		}
	
		/*################### SMS for Retailer ##################*/
		if(SMS_Retailer.equals("Yes") || SMS_Retailer.equals("yes") || SMS_Retailer == "Yes" || SMS_Retailer =="yes")
		{
			String ST_ORY=ST.trim().replaceAll(" ", "_"); 
			LogLevel.DEBUG(5, new Throwable(), "ST_ORY :" + ST_ORY);
			
			String Get_Retailer_Details = "select retailer_id,retailer_name,mobile_number,mobile_numberother from "+ST_ORY+"_retailers where retailer_name='"+RN+"'";
			LogLevel.DEBUG(5, new Throwable(), "Get_Retailer_Details :" + Get_Retailer_Details);

			Hashtable Get_Retailer_Details_HT = qmer.getRow(Get_Retailer_Details); 
			String Retailer_Id =String.valueOf(Get_Retailer_Details_HT.get("retailer_id"));
			String Retailer_Number =String.valueOf(Get_Retailer_Details_HT.get("mobile_number"));
			String Retailer_Name =(String)Get_Retailer_Details_HT.get("retailer_name");
			String Retailer_Other_Number=String.valueOf(Get_Retailer_Details_HT.get("mobile_numberother"));
			
			String Send_SMS_Response=  Send_SMS.sendSMS(strIpAddress,strPort,SMSFromAddress,SMS_For_Retailer, Retailer_Number);
			LogLevel.DEBUG(5, new Throwable(), "Send_SMS_Response :" + Send_SMS_Response);
			
			strRes=strRes+"  SMS sent to Retailer "+Retailer_Number;
			
			if(Retailer_Id.equals(null) || Retailer_Id.equals("null") || Retailer_Id == null || Retailer_Id == "null" || Retailer_Id =="")
			{
				strRes="Some thing went wrong, Please contact your DevOps Team";
			}
			else
			{
				String[] Retailer_Other_Number_Arr=Retailer_Other_Number.split(",");
				LogLevel.DEBUG(5,new Throwable(),"Retailer_Other_Number_Arr.length :"+Retailer_Other_Number_Arr.length );
				LogLevel.DEBUG(5,new Throwable(),"Retailer_Other_Number_Arr:"+Retailer_Other_Number_Arr);
				if(Retailer_Other_Number_Arr.length>0)
				{
					String Retailer_Other_Number_Arr_TempValue="";
					for (int k=0;k<Retailer_Other_Number_Arr.length ;k++)
					{	
						Retailer_Other_Number_Arr_TempValue=Retailer_Other_Number_Arr[k];
						LogLevel.DEBUG(5,new Throwable(),"Retailer_Other_Number_Arr_TempValue:"+Retailer_Other_Number_Arr_TempValue);
						Send_SMS_Response = Send_SMS.sendSMS(strIpAddress,strPort,SMSFromAddress,SMS_For_Retailer, Retailer_Other_Number_Arr_TempValue);
						LogLevel.DEBUG(5, new Throwable(), "Send_SMS_Response :" + Send_SMS_Response);
						strRes=strRes+"SMS sent to Retailer "+Retailer_Other_Number_Arr_TempValue;
					}
				}
			}
			
		}
		else
		{
			LogLevel.DEBUG(5, new Throwable(), " SMS Not Sent to Retailer");
		}
		
		/*################### SMS for Operator ##################*/
		if( SMS_Operator.equals("Yes") || SMS_Operator.equals("yes") || SMS_Operator == "Yes" || SMS_Operator =="yes")
		{
			String Send_SMS_Response=  Send_SMS.sendSMS(strIpAddress,strPort,SMSFromAddress,SMS_For_Operator,SMS_Contact);
			LogLevel.DEBUG(5, new Throwable(), "Send_SMS_Response :" + Send_SMS_Response);
			strRes=strRes+"SMS sent to Operator "+SMS_Contact;
		}
		else{
			LogLevel.DEBUG(5, new Throwable(), " SMS Not Sent to Operator");
		}
	
		LogLevel.DEBUG(5, new Throwable(), " SMS Response"+strRes);
		return strRes;
	}
						

	/* *************************************************************************************************************\
	* This method is used to Send Mail Order Details to Customer
	\* *************************************************************************************************************/
	public String Send_Order_Mail(HttpServletRequest req , HttpServletResponse res,HttpSession session, String OrderNumber, String Order_Type,String Mail_Sir,String Mail_Customer,String Mail_Retailer,String Mail_Operator,String strorderreasons)
	
	{ 	
		String CN ="";
		String CMN ="";
		String CML ="";
		String CA ="";
		String CA_LandMark ="";
		String RN ="";
		String RMN ="";
		String RML ="";
		String BB ="";
		String BM ="";
		String VehMake ="";
		String VehModel ="";
		String PRI ="";
		String WOOBOPRI ="";
		String BOT ="";
		String BT ="";
		String QTY ="";
		String PM ="";
		String DM ="";
		String DC ="";
		String ST ="";
		String DiPRI ="";
		String SP ="";
		String Area ="";
		String STATE ="";
		String strRes ="";
		String order_status ="";
		String forwareded_order ="";
		
		String IB ="";
		String IM ="";
		String IC ="";
		
		String TB ="";
		String TM ="";
		
		String INF ="";
		
		String Customer_Battery_Order_Msg ="";
		String Retailer_Battery_Order_Msg ="";
		String Operator_Battery_Order_Msg ="";
		String Customer_Inverter_Order_Msg ="";
		String Retailer_Inverter_Order_Msg ="";
		String Operator_Inverter_Order_Msg ="";
		String Retailer_Battery_Order_Mail_Msg ="";
		String Retailer_Battery_new_Order_Mail_Msg ="";
		String MAIL_Domain="BookBattery";
		String MAIL_SMS_Domain="9603467559";
		String From_Name_MailID="BookBattery<helpdesk.bookbattery@gmail.com>";
		String strdomainname="www.bookbattery.com";		
		String MAIL_CUS_Domain="BookBattery";
		String MAIL_SMS_CUS_Domain="9603467559";
		String From_Name_CUS_MailID="BookBattery<helpdesk.bookbattery@gmail.com>";
		String strdomaincusname="www.bookbattery.com";
		
		LogLevel.DEBUG(5, new Throwable(), " Mail Order_Type :" + Order_Type);
		LogLevel.DEBUG(5, new Throwable(), " Mail Mail_Customer :" + Mail_Customer);
		LogLevel.DEBUG(5, new Throwable(), " Mail Mail_Retailer :" + Mail_Retailer);
		LogLevel.DEBUG(5, new Throwable(), " Mail Mail_Operator :" + Mail_Operator);
		LogLevel.DEBUG(5, new Throwable(), " Mail orderreasons :" + strorderreasons);
		if (Order_Type=="Battery" || Order_Type.equals("Battery"))
		{
			LogLevel.DEBUG(5, new Throwable(), " Mail orderreasons :" + strorderreasons);
			LogLevel.DEBUG(5, new Throwable(), "OrderNumber :" + OrderNumber);
			String Get_Order_Details ="select consumer_mobnumber, consumer_name, consumer_emailid, retailer_name, retailer_mobilnumber, retailer_emailis, battery_brand, battery_model, veh_name, veh_model,order_status, price, witholdbatprice, order_type, consumer_address, quantity, bat_type, area, payment_mode, state, delivery_mode, delivery_charge from battery_order_details where order_number ='"+OrderNumber+"'";
			LogLevel.DEBUG(5, new Throwable(), "Get_Order_Details :" + Get_Order_Details);
			
			Hashtable Get_Order_Details_HT = qmer.getRow(Get_Order_Details);
			
			LogLevel.DEBUG(5, new Throwable(), "Get_Order_Details_HT :" + Get_Order_Details_HT);

			if(Get_Order_Details_HT.isEmpty())
			{
				strRes="Some thing went wrong, Please contact your DevOps Team";
				return strRes;
			}
			else
			{
				CMN=(String)Get_Order_Details_HT.get("consumer_mobnumber");
				LogLevel.DEBUG(5, new Throwable(), "consumer_mobnumber :" + CMN);
				
				CN=(String)Get_Order_Details_HT.get("consumer_name");
				LogLevel.DEBUG(5, new Throwable(), "consumer_name :" + CN);
				
				CML=(String)Get_Order_Details_HT.get("consumer_emailid");
				LogLevel.DEBUG(5, new Throwable(), "consumer_emailid :" + CML);
				
				CA=(String)Get_Order_Details_HT.get("consumer_address");
				LogLevel.DEBUG(5, new Throwable(), "consumer_address :" + CA);
				
				RN=(String)Get_Order_Details_HT.get("retailer_name");
				LogLevel.DEBUG(5, new Throwable(), "retailer_name :" + RN);
				
				RMN=(String)Get_Order_Details_HT.get("retailer_mobilnumber");
				LogLevel.DEBUG(5, new Throwable(), "retailer_mobilnumber :" + RMN);
				
				RML=(String)Get_Order_Details_HT.get("retailer_emailis");
				LogLevel.DEBUG(5, new Throwable(), "retailer_emailis :" + RML);
				
				BB=(String)Get_Order_Details_HT.get("battery_brand");
				LogLevel.DEBUG(5, new Throwable(), "battery_brand :" + BB);
				
				BM =(String)Get_Order_Details_HT.get("battery_model");
				LogLevel.DEBUG(5, new Throwable(), "battery_model :" + BM);
				
				VehMake =(String)Get_Order_Details_HT.get("veh_name");
				LogLevel.DEBUG(5, new Throwable(), "veh_name :" + BM);
				
				VehModel =(String)Get_Order_Details_HT.get("veh_model");
				LogLevel.DEBUG(5, new Throwable(), "veh_model :" + BM);
				
				PRI=(String)Get_Order_Details_HT.get("price");
				LogLevel.DEBUG(5, new Throwable(), "price :" + PRI);
				
				WOOBOPRI=(String)Get_Order_Details_HT.get("witholdbatprice");
				LogLevel.DEBUG(5, new Throwable(), "witholdbatprice :" + WOOBOPRI);
				
				BOT=(String)Get_Order_Details_HT.get("order_type");
				LogLevel.DEBUG(5, new Throwable(), "order_type :" + BOT);
				
				BT=(String)Get_Order_Details_HT.get("bat_type");
				LogLevel.DEBUG(5, new Throwable(), "bat_type :" + BT);
				
				QTY=(String)Get_Order_Details_HT.get("quantity");
				LogLevel.DEBUG(5, new Throwable(), "quantity :" + QTY);
				
				PM=(String)Get_Order_Details_HT.get("payment_mode");
				LogLevel.DEBUG(5, new Throwable(), "payment_mode :" + PM);
				
				DM=(String)Get_Order_Details_HT.get("delivery_mode");
				LogLevel.DEBUG(5, new Throwable(), "delivery_mode :" + DM);				
				
				DC=(String)Get_Order_Details_HT.get("delivery_charge");
				LogLevel.DEBUG(5, new Throwable(), "delivery_charge :" + DC);
				
				ST=(String)Get_Order_Details_HT.get("state");
				LogLevel.DEBUG(5, new Throwable(), "state :" + ST);
				
				Area=(String)Get_Order_Details_HT.get("area");
				LogLevel.DEBUG(5, new Throwable(), "area :" + ST);
			
				order_status=(String)Get_Order_Details_HT.get("order_status");
				LogLevel.DEBUG(5, new Throwable(), "order_status :" + order_status);
				
				 
				
				int Price_Temp=0;
				int QTY_int=0;
				
				if (BOT=="New" || BOT.equals("New"))
				{
					Price_Temp = Integer.parseInt(PRI);
				}
				else
				{
					Price_Temp = Integer.parseInt(WOOBOPRI);
				}
				
				
				QTY_int = Integer.parseInt(QTY);
				int Final_Price_Int=Price_Temp*QTY_int;
				String Final_Price = Integer.toString(Final_Price_Int);
				
				String Retailer_Price_WOB = Integer.toString(Integer.parseInt(PRI)*QTY_int);
				String Retailer_Price_WoutOldB= Integer.toString(Integer.parseInt(WOOBOPRI)*QTY_int);
				String Retailer_Final_Price=Retailer_Price_WOB+", OBRP Rs:"+Retailer_Price_WoutOldB;
				String Retailer_Final_Price_for_mail=Retailer_Price_WOB+", Old Battery Replacement Price Rs:"+Retailer_Price_WoutOldB;
				String ST_ORY=ST.trim().replaceAll(" ", "_"); 
				LogLevel.DEBUG(5, new Throwable(), "ST_ORY :" + ST_ORY);
				
				String Get_Retailer_Details = "select retailer_id,state,city,retailer_name,address1,email_id,email_id1 from "+ST_ORY+"_retailers where retailer_name='"+RN+"'";
				LogLevel.DEBUG(5, new Throwable(), "Get_Retailer_Details :" + Get_Retailer_Details);

				Hashtable Get_Retailer_Details_HT = qmer.getRow(Get_Retailer_Details); 
				String Retailer_Id =String.valueOf(Get_Retailer_Details_HT.get("retailer_id"));
				String Retailer_Name =String.valueOf(Get_Retailer_Details_HT.get("retailer_name"));
				String Retailer_Address =(String)Get_Retailer_Details_HT.get("address1");
				String Retailer_Other_Number=String.valueOf(Get_Retailer_Details_HT.get("mobile_numberother"));
				String Retailer_state=String.valueOf(Get_Retailer_Details_HT.get("state"));
				String Retailer_city=String.valueOf(Get_Retailer_Details_HT.get("city"));
				LogLevel.DEBUG(5, new Throwable(), "ST_ORY Retailer_Id:" + Retailer_Id);	
				LogLevel.DEBUG(5, new Throwable(), "ST_ORY Retailer_Name:" + Retailer_Name);
				LogLevel.DEBUG(5, new Throwable(), "ST_ORY Retailer_Name:" + Retailer_Name);
				MAIL_Domain="BookBattery";
				MAIL_SMS_Domain="9603467559";
				From_Name_MailID="BookBattery<helpdesk.bookbattery@gmail.com>";
				strdomainname="www.bookbattery.com";
		
				MAIL_CUS_Domain="BookBattery";
				MAIL_SMS_CUS_Domain="9603467559";
				//From_Name_CUS_MailID="BookBattery<helpdesk.bookbattery@gmail.com>";
				From_Name_CUS_MailID="BookBattery<helpdesk.bookbattery@gmail.com>";
				strdomaincusname="www.bookbattery.com";					
				
				/*################### Template for Customer	##################*/
				
				
				if((BT=="Bike Batteries" || BT.equals("Bike Batteries"))&& (DM=="Yes" || DM.equals("Yes")))
				{
					Final_Price=Final_Price+", Delivery Cost: "+DC;
					Retailer_Final_Price=Retailer_Final_Price+", Delivery Cost: "+DC;
					
				}
				else
				{
					Final_Price=Final_Price;
					Retailer_Final_Price=Retailer_Final_Price;
				}
					
				if(strorderreasons.equals("cancelled")){
					
					if (BT=="Bike Batteries" || BT.equals("Bike Batteries"))
					{
						Customer_Battery_Order_Msg="Your "+MAIL_CUS_Domain+" Battery Ord Ref No: "+OrderNumber+". \r\nOrder Details \r\n\r\nBattery Brand: "+BB+", Battery Model: "+BM+", Vehicle Make: "+VehMake+", Vehicle Model: "+VehModel+", Price: "+Final_Price+", Battery Quantity : "+QTY+", Battery Replacement: "+BOT+", Payment Mode: "+PM+".  \r\n\r\n has been cancelled. We are looking forward to see you again at "+strdomainname+".";
					}
					else
					{
						
						Customer_Battery_Order_Msg="Your "+MAIL_CUS_Domain+" Battery Ord Ref No: "+OrderNumber+". \r\nOrder Details \r\nBattery Brand: "+BB+", Battery Model: "+BM+", Vehicle Make: "+VehMake+", Vehicle Model: "+VehModel+", Price: "+Final_Price+", Battery Quantity : "+QTY+", Battery Replacement: "+BOT+", Payment Mode: "+PM+".  \r\n\r\n has been cancelled. We are looking forward to see you again at "+strdomainname+".";
					}
					
					LogLevel.DEBUG(5, new Throwable(), "Customer_Battery_Order_Msg :" + Customer_Battery_Order_Msg);
					
					
					/*################### Template for Retailer ##################*/

					Retailer_Battery_Order_Msg=""+MAIL_Domain+" Consumer "+CN+" with Mob no : "+CMN+", Address : "+CA+" and Order Ref Number "+OrderNumber+".\r\nOrdered for "+BB+", "+BM+" at Price: Rs."+Retailer_Final_Price+", Battery Replacement: "+BOT+", Vehicle Make: "+VehMake+", Vehicle Model: "+VehModel+", Battery Quantity : "+QTY+", Payment Mode: "+PM+".\r\n has been cancelled. For any kind of Assistance please call to "+MAIL_SMS_Domain+".";
					LogLevel.DEBUG(5, new Throwable(), "Retailer_Battery_Order_Msg :" + Retailer_Battery_Order_Msg);
					
					Retailer_Battery_Order_Mail_Msg=""+MAIL_Domain+" Consumer "+CN+" with Mob no : "+CMN+", Address : "+CA+", Area : "+Area+", and Order Ref Number "+OrderNumber+".\r\nOrdered for "+BB+", "+BM+" at New Battery Price: Rs."+Retailer_Final_Price_for_mail+", Battery Replacement: "+BOT+", Vehicle Make: "+VehMake+", Vehicle Model: "+VehModel+", Battery Quantity : "+QTY+", Payment Mode: "+PM+".\r\n\r\n has been cancelled. For any kind of Assistance please call to "+MAIL_SMS_Domain+".";
					LogLevel.DEBUG(5, new Throwable(), "Retailer_Battery_Order_Msg :" + Retailer_Battery_Order_Msg);
					
					Retailer_Battery_new_Order_Mail_Msg=""+MAIL_Domain+" Consumer "+CN+" with Mob no : "+CMN+", Address : "+CA+", Area : "+Area+", and Order Ref Number "+OrderNumber+".\r\nOrdered for "+BB+", "+BM+" at New Battery Price: Rs."+Retailer_Final_Price_for_mail+", Battery Replacement: "+BOT+", Vehicle Make: "+VehMake+", Vehicle Model: "+VehModel+", Battery Quantity : "+QTY+", Payment Mode: "+PM+".\r\n\r\nhas been cancelled. For any kind of Assistance please call to "+MAIL_SMS_Domain+".";
					LogLevel.DEBUG(5, new Throwable(), "Retailer_Battery_Order_Msg :" + Retailer_Battery_Order_Msg);
					
					/*################### Template for Operator ##################*/
					
					Operator_Battery_Order_Msg=""+MAIL_Domain+" Consumer "+CN+" with Mob no : "+CMN+", Address : "+CA+", Area : "+Area+", and Order Ref Number "+OrderNumber+".\r\nOrdered for "+BB+", "+BM+" at New Battery Price: Rs."+Retailer_Final_Price_for_mail+", Battery Replacement: "+BOT+", Vehicle Make: "+VehMake+", Vehicle Model: "+VehModel+", Battery Quantity : "+QTY+", Payment Mode: "+PM+".\r\n\r\nhas been cancelled. For any kind of Assistance please call to "+MAIL_SMS_Domain+".";
					
					LogLevel.DEBUG(5, new Throwable(), "Operator_Battery_Order_Msg :" + Operator_Battery_Order_Msg);
					
				} else if(strorderreasons.equals("installed")){
					
					if (BT=="Bike Batteries" || BT.equals("Bike Batteries"))
					{
						Customer_Battery_Order_Msg="Your "+MAIL_CUS_Domain+" Battery Ord Ref No: "+OrderNumber+". \r\nOrder Details \r\n\r\nBattery Brand: "+BB+", Battery Model: "+BM+", Vehicle Make: "+VehMake+", Vehicle Model: "+VehModel+", Price: "+Final_Price+", Battery Quantity : "+QTY+", Battery Replacement: "+BOT+", Payment Mode: "+PM+".  \r\n\r\n has been successfully installed. For any kind of Assistance please call to "+MAIL_SMS_CUS_Domain+".";
					}
					else
					{
						
						Customer_Battery_Order_Msg="Your "+MAIL_CUS_Domain+" Battery Ord Ref No: "+OrderNumber+". \r\nOrder Details \r\nBattery Brand: "+BB+", Battery Model: "+BM+", Vehicle Make: "+VehMake+", Vehicle Model: "+VehModel+", Price: "+Final_Price+", Battery Quantity : "+QTY+", Battery Replacement: "+BOT+", Payment Mode: "+PM+".  \r\n\r\n has been successfully installed. For any kind of Assistance please call to "+MAIL_SMS_CUS_Domain+".";
					}
					
					LogLevel.DEBUG(5, new Throwable(), "Customer_Battery_Order_Msg :" + Customer_Battery_Order_Msg);
					
					
					/*################### Template for Retailer ##################*/

					Retailer_Battery_Order_Msg=""+MAIL_Domain+" Consumer "+CN+" with Mob no : "+CMN+", Address : "+CA+" and Order Ref Number "+OrderNumber+".\r\nOrdered for "+BB+", "+BM+" at Price: Rs."+Retailer_Final_Price+", Battery Replacement: "+BOT+", Vehicle Make: "+VehMake+", Vehicle Model: "+VehModel+", Battery Quantity : "+QTY+", Payment Mode: "+PM+".\r\n has been successfully installed. For any kind of Assistance please call to "+MAIL_SMS_CUS_Domain+".";
					LogLevel.DEBUG(5, new Throwable(), "Retailer_Battery_Order_Msg :" + Retailer_Battery_Order_Msg);
					
					Retailer_Battery_Order_Mail_Msg=""+MAIL_Domain+" Consumer "+CN+" with Mob no : "+CMN+", Address : "+CA+", Area : "+Area+", and Order Ref Number "+OrderNumber+".\r\nOrdered for "+BB+", "+BM+" at New Battery Price: Rs."+Retailer_Final_Price_for_mail+", Battery Replacement: "+BOT+", Vehicle Make: "+VehMake+", Vehicle Model: "+VehModel+", Battery Quantity : "+QTY+", Payment Mode: "+PM+".\r\n\r\nRetailer Name: "+Retailer_Name+", Retailer State: "+Retailer_state+", Retailer City: "+Retailer_city+", Retailer Mobile number: "+RMN+".\r\n\r\n has been successfully installed. For any kind of Assistance please call to "+MAIL_SMS_CUS_Domain+".";
					LogLevel.DEBUG(5, new Throwable(), "Retailer_Battery_Order_Msg :" + Retailer_Battery_Order_Msg);
					
					Retailer_Battery_new_Order_Mail_Msg=""+MAIL_Domain+" Consumer "+CN+" with Mob no : "+CMN+", Address : "+CA+", Area : "+Area+", and Order Ref Number "+OrderNumber+".\r\nOrdered for "+BB+", "+BM+" at New Battery Price: Rs."+Retailer_Final_Price_for_mail+", Battery Replacement: "+BOT+", Vehicle Make: "+VehMake+", Vehicle Model: "+VehModel+", Battery Quantity : "+QTY+", Payment Mode: "+PM+".\r\n\r\nRetailer Name: "+Retailer_Name+", Retailer State: "+Retailer_state+", Retailer City: "+Retailer_city+", Retailer Mobile number: "+RMN+".\r\n\r\n has been successfully installed. For any kind of Assistance please call to "+MAIL_SMS_CUS_Domain+".";
					LogLevel.DEBUG(5, new Throwable(), "Retailer_Battery_Order_Msg :" + Retailer_Battery_Order_Msg);
					
					/*################### Template for Operator ##################*/
					
					Operator_Battery_Order_Msg="The ordered battery with order number "+OrderNumber+" has been successfully installed. Order Details Customer Name- "+CN+", Customer Mobile Num- "+CMN+", Battery Brand- "+BB+", Battery Model- "+BM+", Vehicle Make- "+VehMake+", Vehicle Model- "+VehModel+", Retailer Name- "+Retailer_Name+", Retailer Mobile Number- "+RMN+"";
					
					LogLevel.DEBUG(5, new Throwable(), "Operator_Battery_Order_Msg :" + Operator_Battery_Order_Msg);
					
				} else {
					
					if (BT=="Bike Batteries" || BT.equals("Bike Batteries"))
					{
						Customer_Battery_Order_Msg="Your "+MAIL_CUS_Domain+" Battery Ord Ref No: "+OrderNumber+". \r\nOrder Details \r\n\r\nBattery Brand: "+BB+", Battery Model: "+BM+", Vehicle Make: "+VehMake+", Vehicle Model: "+VehModel+", Price: "+Final_Price+", Battery Quantity : "+QTY+", Battery Replacement: "+BOT+", Payment Mode: "+PM+".  \r\n\r\nPlease collect your Battery from "+Retailer_Name+" with Mob No-"+RMN+" and Address "+Retailer_Address+". For any kind of Assistance please call to "+MAIL_SMS_CUS_Domain+".";
					}
					else
					{
						
						Customer_Battery_Order_Msg="Your "+MAIL_CUS_Domain+" Battery Ord Ref No: "+OrderNumber+". \r\nOrder Details \r\nBattery Brand: "+BB+", Battery Model: "+BM+", Vehicle Make: "+VehMake+", Vehicle Model: "+VehModel+", Price: "+Final_Price+", Battery Quantity : "+QTY+", Battery Replacement: "+BOT+", Payment Mode: "+PM+".  \r\n\r\nOur Professional Installation Partner with Mob No-"+RMN+" will fullfill your order. For any kind of Assistance please call to "+MAIL_SMS_CUS_Domain+".";
					}
					
					LogLevel.DEBUG(5, new Throwable(), "Customer_Battery_Order_Msg :" + Customer_Battery_Order_Msg);
					
					
					/*################### Template for Retailer ##################*/

					Retailer_Battery_Order_Msg=""+MAIL_Domain+" Consumer "+CN+" with Mob no : "+CMN+", Address : "+CA+" and Order Ref Number "+OrderNumber+".\r\nOrdered for "+BB+", "+BM+" at Price: Rs."+Retailer_Final_Price+", Battery Replacement: "+BOT+", Vehicle Make: "+VehMake+", Vehicle Model: "+VehModel+", Battery Quantity : "+QTY+", Payment Mode: "+PM+".\r\nPlease re-confirm with the customer and deliver it. For any kind of Assistance please call to "+MAIL_SMS_Domain+".";
					LogLevel.DEBUG(5, new Throwable(), "Retailer_Battery_Order_Msg :" + Retailer_Battery_Order_Msg);
					
					Retailer_Battery_Order_Mail_Msg=""+MAIL_Domain+" Consumer "+CN+" with Mob no : "+CMN+", Address : "+CA+", Area : "+Area+", and Order Ref Number "+OrderNumber+".\r\nOrdered for "+BB+", "+BM+" at New Battery Price: Rs."+Retailer_Final_Price_for_mail+", Battery Replacement: "+BOT+", Vehicle Make: "+VehMake+", Vehicle Model: "+VehModel+", Battery Quantity : "+QTY+", Payment Mode: "+PM+".\r\n\r\nRetailer Name: "+Retailer_Name+", Retailer State: "+Retailer_state+", Retailer City: "+Retailer_city+", Retailer Mobile number: "+RMN+".\r\n\r\nPlease re-confirm with the customer and deliver it. For any kind of Assistance please call to "+MAIL_SMS_Domain+".";
					LogLevel.DEBUG(5, new Throwable(), "Retailer_Battery_Order_Msg :" + Retailer_Battery_Order_Msg);
					
					Retailer_Battery_new_Order_Mail_Msg=""+MAIL_Domain+" Consumer "+CN+" with Mob no : "+CMN+", Address : "+CA+", Area : "+Area+", and Order Ref Number "+OrderNumber+".\r\nOrdered for "+BB+", "+BM+" at New Battery Price: Rs."+Retailer_Final_Price_for_mail+", Battery Replacement: "+BOT+", Vehicle Make: "+VehMake+", Vehicle Model: "+VehModel+", Battery Quantity : "+QTY+", Payment Mode: "+PM+".\r\n\r\nRetailer Name: "+Retailer_Name+", Retailer State: "+Retailer_state+", Retailer City: "+Retailer_city+", Retailer Mobile number: "+RMN+".\r\n\r\nPlease re-confirm with the customer and deliver it. For any kind of Assistance please call to "+MAIL_SMS_Domain+".";
					LogLevel.DEBUG(5, new Throwable(), "Retailer_Battery_Order_Msg :" + Retailer_Battery_Order_Msg);
					
					/*################### Template for Operator ##################*/
					
					Operator_Battery_Order_Msg=""+MAIL_Domain+" Consumer "+CN+" with Mob no : "+CMN+", Address : "+CA+" and Order Ref Number "+OrderNumber+".\r\nOrdered for "+BB+", "+BM+" at Price: Rs."+Final_Price+", Battery Replacement: "+BOT+", Vehicle Make: "+VehMake+", Vehicle Model: "+VehModel+", Battery Quantity : "+QTY+", Payment Mode: "+PM+". \r\n"+Retailer_Name+" with Mob No-"+RMN+" and Address "+Retailer_Address+" will fullfill this order.";
					
					LogLevel.DEBUG(5, new Throwable(), "Operator_Battery_Order_Msg :" + Operator_Battery_Order_Msg);
				}
				
				
			}
		}
		else if (Order_Type=="Inverter" ||  Order_Type.equals("Inverter"))
		{
			LogLevel.DEBUG(5, new Throwable(), "OrderNumber :" + OrderNumber);
			
			String Get_Order_Details ="select consumer_mobnumber, consumer_name, consumer_address, consumer_emailid, retailer_name, retailer_mobile_number, retailer_emailid, inverter_brand, inverter_capacity, area, inverter_model, price, quantity, payment_mode,payment_mode_type, state from inverter_order_details where order_number ='"+OrderNumber+"'";
			
			LogLevel.DEBUG(5, new Throwable(), "Get_Order_Details :" + Get_Order_Details);
			
			Hashtable Get_Order_Details_HT = qmer.getRow(Get_Order_Details);
			
			LogLevel.DEBUG(5, new Throwable(), "Get_Order_Details_HT :" + Get_Order_Details_HT);

			if(Get_Order_Details_HT.isEmpty())
			{
				strRes="Some thing went wrong, Please contact your DevOps Team";
				return strRes;
			}
			else
			{
				CMN=(String)Get_Order_Details_HT.get("consumer_mobnumber");
				LogLevel.DEBUG(5, new Throwable(), "consumer_mobnumber :" + CMN);
				
				CN=(String)Get_Order_Details_HT.get("consumer_name");
				LogLevel.DEBUG(5, new Throwable(), "consumer_name :" + CN);
				
				CA=(String)Get_Order_Details_HT.get("consumer_address");
				LogLevel.DEBUG(5, new Throwable(), "consumer_address :" + CA);
								
				CML=(String)Get_Order_Details_HT.get("consumer_emailid");
				LogLevel.DEBUG(5, new Throwable(), "consumer_emailid :" + CML);
				
				RN=(String)Get_Order_Details_HT.get("retailer_name");
				LogLevel.DEBUG(5, new Throwable(), "retailer_name :" + RN);
				
				RMN=(String)Get_Order_Details_HT.get("retailer_mobile_number");
				LogLevel.DEBUG(5, new Throwable(), "retailer_mobile_number :" + RMN);
				
				RML=(String)Get_Order_Details_HT.get("retailer_emailis");
				LogLevel.DEBUG(5, new Throwable(), "retailer_emailis :" + RML);
				
				IB=(String)Get_Order_Details_HT.get("inverter_brand");
				LogLevel.DEBUG(5, new Throwable(), "inverter_brand :" + BB);
				
				IM =(String)Get_Order_Details_HT.get("inverter_model");
				LogLevel.DEBUG(5, new Throwable(), "inverter_model :" + BM);
				
				IC=(String)Get_Order_Details_HT.get("inverter_capacity");
				LogLevel.DEBUG(5, new Throwable(), "inverter_capacity :" + IC);
				
				PRI=(String)Get_Order_Details_HT.get("price");
				LogLevel.DEBUG(5, new Throwable(), "price :" + PRI);
				
				QTY=(String)Get_Order_Details_HT.get("quantity");
				LogLevel.DEBUG(5, new Throwable(), "quantity :" + QTY);
				
				PM=(String)Get_Order_Details_HT.get("payment_mode");
				LogLevel.DEBUG(5, new Throwable(), "payment_mode :" + PM);
								
				ST=(String)Get_Order_Details_HT.get("state");
				LogLevel.DEBUG(5, new Throwable(), "state :" + ST);
				
				Area=(String)Get_Order_Details_HT.get("area");
				LogLevel.DEBUG(5, new Throwable(), "area :" + ST);
				
				int Final_Price_Int=Integer.parseInt(PRI)*Integer.parseInt(QTY);
				String Final_Price = Integer.toString(Final_Price_Int);
				
				
				String ST_ORY=ST.trim().replaceAll(" ", "_"); 
				LogLevel.DEBUG(5, new Throwable(), "ST_ORY :" + ST_ORY);
				
				String Get_Retailer_Details = "select retailer_id,state,city,retailer_name,address1,email_id,email_id1 from "+ST_ORY+"_retailers where retailer_name='"+RN+"'";
				LogLevel.DEBUG(5, new Throwable(), "Get_Retailer_Details :" + Get_Retailer_Details);

				Hashtable Get_Retailer_Details_HT = qmer.getRow(Get_Retailer_Details); 
				String Retailer_Id =String.valueOf(Get_Retailer_Details_HT.get("retailer_id"));
				String Retailer_Name =String.valueOf(Get_Retailer_Details_HT.get("retailer_name"));
				String Retailer_Address =(String)Get_Retailer_Details_HT.get("address1");
				String Retailer_state=String.valueOf(Get_Retailer_Details_HT.get("state"));
				String Retailer_city=String.valueOf(Get_Retailer_Details_HT.get("city"));
				
			
					MAIL_Domain="BookBattery";
					MAIL_SMS_Domain="9603467559";
					From_Name_MailID="BookBattery<helpdesk.bookbattery@gmail.com>";
					strdomainname="www.bookbattery.com";
				
					MAIL_CUS_Domain="BookBattery";
					MAIL_SMS_CUS_Domain="9603467559";
					//From_Name_CUS_MailID="BookBattery<helpdesk.bookbattery@gmail.com>";
					From_Name_CUS_MailID="BookBattery<helpdesk.bookbattery@gmail.com>";
					strdomaincusname="www.bookbattery.com";					
				
				if(strorderreasons.equals("cancelled")){ 
					
					/*################### Template for Customer##################*/
				
					Customer_Battery_Order_Msg="Your "+MAIL_CUS_Domain+" Inverter Ord Ref No: "+OrderNumber+". \r\n Order Details \r\n Inverter Brand: "+IB+", Inverter Model: "+IM+", Price: "+Final_Price+", Inverter Quantity : "+QTY+", Payment Mode: "+PM+".  \r\n\r\n  has been cancelled. We are looking forward to see you again at "+strdomainname+".";
					
					LogLevel.DEBUG(5, new Throwable(), "Customer_Battery_Order_Msg :" + Customer_Battery_Order_Msg);
					
					/*################### Template for Retailer##################*/
					
					Retailer_Battery_Order_Msg=""+MAIL_Domain+" Consumer "+CN+" with Mob no : "+CMN+", Address : "+CA+" and Order Ref Number "+OrderNumber+".\r\nOrdered for "+IB+", "+IM+" at Price: Rs."+Final_Price+", Inverter Quantity : "+QTY+", Payment Mode: "+PM+".\r\n has been cancelled";
					LogLevel.DEBUG(5, new Throwable(), "Retailer_Battery_Order_Msg :" + Retailer_Battery_Order_Msg);
					
					/*################### Template for ME ##################*/
					
					Retailer_Battery_Order_Mail_Msg=""+MAIL_Domain+" Consumer "+CN+" with Mob no : "+CMN+", Address : "+CA+" , Area : "+Area+", and Order Ref Number "+OrderNumber+".\r\nOrdered for "+IB+", "+IM+" at Inverter Price: Rs."+Final_Price+", Inverter Quantity : "+QTY+", Payment Mode: "+PM+".\r\n\r\nRetailer Name: "+Retailer_Name+", Retailer State: "+Retailer_state+", Retailer City: "+Retailer_city+", Retailer Mobile number: "+RMN+".\r\n\r\n has been cancelled";
					
					  /*################### Template for ME ##################*/
					
					Retailer_Battery_new_Order_Mail_Msg=""+MAIL_Domain+" Consumer "+CN+" with Mob no : "+CMN+", Address : "+CA+" , Area : "+Area+", and Order Ref Number "+OrderNumber+".\r\nOrdered for "+IB+", "+IM+" at Inverter Price: Rs."+Final_Price+", Inverter Quantity : "+QTY+", Payment Mode: "+PM+".\r\n\r\nRetailer Name: "+Retailer_Name+", Retailer State: "+Retailer_state+", Retailer City: "+Retailer_city+", Retailer Mobile number: "+RMN+".\r\n\r\n has been cancelled.";
					

					/*################### Template for Operator
					##################*/
					Operator_Battery_Order_Msg=""+MAIL_Domain+" Consumer "+CN+" with Mob no : "+CMN+", Address : "+CA+" and Order Ref Number "+OrderNumber+".\r\nOrdered for "+IB+", "+IM+" at Price: Rs."+Final_Price+", Inverter Quantity : "+QTY+", Payment Mode: "+PM+". \r\n"+Retailer_Name+" with Mob No-"+RMN+" and Address "+Retailer_Address+" Order got Cancelled.";
					
					LogLevel.DEBUG(5, new Throwable(), "Operator_Battery_Order_Msg :" + Operator_Battery_Order_Msg);
					
				
				} else if(strorderreasons.equals("installed")){ 
				
						/*################### Template for Customer##################*/
				
					Customer_Battery_Order_Msg="Your "+MAIL_CUS_Domain+" Inverter Ord Ref No: "+OrderNumber+". \r\n Order Details \r\n Inverter Brand: "+IB+", Inverter Model: "+IM+", Price: "+Final_Price+", Inverter Quantity : "+QTY+", Payment Mode: "+PM+".  \r\n\r\n has been successfully installed. For any kind of Assistance please call to "+MAIL_SMS_CUS_Domain+".";
					
					LogLevel.DEBUG(5, new Throwable(), "Customer_Battery_Order_Msg :" + Customer_Battery_Order_Msg);
					
					/*################### Template for Retailer##################*/
					
					Retailer_Battery_Order_Msg=""+MAIL_Domain+" Consumer "+CN+" with Mob no : "+CMN+", Address : "+CA+" and Order Ref Number "+OrderNumber+".\r\nOrdered for "+IB+", "+IM+" at Price: Rs."+Final_Price+", Inverter Quantity : "+QTY+", Payment Mode: "+PM+".\r\n has been successfully installed. For any kind of Assistance please call to "+MAIL_SMS_Domain+".";
					LogLevel.DEBUG(5, new Throwable(), "Retailer_Battery_Order_Msg :" + Retailer_Battery_Order_Msg);
					
					/*################### Template for ME ##################*/
					
					Retailer_Battery_Order_Mail_Msg=""+MAIL_Domain+" Consumer "+CN+" with Mob no : "+CMN+", Address : "+CA+" , Area : "+Area+", and Order Ref Number "+OrderNumber+".\r\nOrdered for "+IB+", "+IM+" at Inverter Price: Rs."+Final_Price+", Inverter Quantity : "+QTY+", Payment Mode: "+PM+".\r\n\r\nRetailer Name: "+Retailer_Name+", Retailer State: "+Retailer_state+", Retailer City: "+Retailer_city+", Retailer Mobile number: "+RMN+".\r\n\r\n has been successfully installed. For any kind of Assistance please call to 9603467559.";
					
					  /*################### Template for ME ##################*/
					
					Retailer_Battery_new_Order_Mail_Msg=""+MAIL_Domain+" Consumer "+CN+" with Mob no : "+CMN+", Address : "+CA+" , Area : "+Area+", and Order Ref Number "+OrderNumber+".\r\nOrdered for "+IB+", "+IM+" at Inverter Price: Rs."+Final_Price+", Inverter Quantity : "+QTY+", Payment Mode: "+PM+".\r\n\r\nRetailer Name: "+Retailer_Name+", Retailer State: "+Retailer_state+", Retailer City: "+Retailer_city+", Retailer Mobile number: "+RMN+".\r\n\r\n has been successfully installed. For any kind of Assistance please call to 9603467559.";
					

					/*################### Template for Operator
					##################*/
					Operator_Battery_Order_Msg=""+MAIL_Domain+" Consumer "+CN+" with Mob no : "+CMN+", Address : "+CA+" and Order Ref Number "+OrderNumber+".\r\nOrdered for "+IB+", "+IM+" at Price: Rs."+Final_Price+", Inverter Quantity : "+QTY+", Payment Mode: "+PM+". \r\n"+Retailer_Name+" with Mob No-"+RMN+" and Address "+Retailer_Address+" has been successfully installed.";
					
					LogLevel.DEBUG(5, new Throwable(), "Operator_Battery_Order_Msg :" + Operator_Battery_Order_Msg);
					
					
				} else {
					
					/*################### Template for Customer##################*/
				
					Customer_Battery_Order_Msg="Your "+MAIL_CUS_Domain+" Inverter Ord Ref No: "+OrderNumber+". \r\n Order Details \r\n Inverter Brand: "+IB+", Inverter Model: "+IM+", Price: "+Final_Price+", Inverter Quantity : "+QTY+", Payment Mode: "+PM+".  \r\n\r\n Our Professional Installation Partner with Mob No-"+RMN+" will fullfill your order. For any kind of Assistance please call to "+MAIL_SMS_CUS_Domain+".";
					
					LogLevel.DEBUG(5, new Throwable(), "Customer_Battery_Order_Msg :" + Customer_Battery_Order_Msg);
					
					/*################### Template for Retailer##################*/
					
					Retailer_Battery_Order_Msg=""+MAIL_Domain+" Consumer "+CN+" with Mob no : "+CMN+", Address : "+CA+" and Order Ref Number "+OrderNumber+".\r\nOrdered for "+IB+", "+IM+" at Price: Rs."+Final_Price+", Inverter Quantity : "+QTY+", Payment Mode: "+PM+".\r\nPlease re-confirm with the customer and deliver it. For any kind of Assistance please call to "+MAIL_SMS_Domain+".";
					LogLevel.DEBUG(5, new Throwable(), "Retailer_Battery_Order_Msg :" + Retailer_Battery_Order_Msg);
					
					/*################### Template for ME ##################*/
					
					Retailer_Battery_Order_Mail_Msg=""+MAIL_Domain+" Consumer "+CN+" with Mob no : "+CMN+", Address : "+CA+" , Area : "+Area+", and Order Ref Number "+OrderNumber+".\r\nOrdered for "+IB+", "+IM+" at Inverter Price: Rs."+Final_Price+", Inverter Quantity : "+QTY+", Payment Mode: "+PM+".\r\n\r\nRetailer Name: "+Retailer_Name+", Retailer State: "+Retailer_state+", Retailer City: "+Retailer_city+", Retailer Mobile number: "+RMN+".\r\n\r\nPlease re-confirm with the customer and deliver it. For any kind of Assistance please call to 9603467559.";
					
					  /*################### Template for ME ##################*/
					
					Retailer_Battery_new_Order_Mail_Msg=""+MAIL_Domain+" Consumer "+CN+" with Mob no : "+CMN+", Address : "+CA+" , Area : "+Area+", and Order Ref Number "+OrderNumber+".\r\nOrdered for "+IB+", "+IM+" at Inverter Price: Rs."+Final_Price+", Inverter Quantity : "+QTY+", Payment Mode: "+PM+".\r\n\r\nRetailer Name: "+Retailer_Name+", Retailer State: "+Retailer_state+", Retailer City: "+Retailer_city+", Retailer Mobile number: "+RMN+".\r\n\r\nPlease re-confirm with the customer and deliver it. For any kind of Assistance please call to 9603467559.";
					

					/*################### Template for Operator
					##################*/
					Operator_Battery_Order_Msg=""+MAIL_Domain+" Consumer "+CN+" with Mob no : "+CMN+", Address : "+CA+" and Order Ref Number "+OrderNumber+".\r\nOrdered for "+IB+", "+IM+" at Price: Rs."+Final_Price+", Inverter Quantity : "+QTY+", Payment Mode: "+PM+". \r\n"+Retailer_Name+" with Mob No-"+RMN+" and Address "+Retailer_Address+" will fullfill this order.";
					
					LogLevel.DEBUG(5, new Throwable(), "Operator_Battery_Order_Msg :" + Operator_Battery_Order_Msg);
					
				
				}
			}
		}
		else if (Order_Type=="Service" ||  Order_Type.equals("Service"))
		{
		
		LogLevel.DEBUG(5, new Throwable(), "OrderNumber :" + OrderNumber);
		String Get_Order_Details ="select consumer_emailid,consumer_mobnumber, consumer_name,consumer_address1,retailer_name, retailer_emailid,retailer_mobilnumber, services_type, product_type, service_price_mrp, service_price_discount, payment_mode, state, city, area, pincode from service_order_details where order_number ='"+OrderNumber+"'";
		
		LogLevel.DEBUG(5, new Throwable(), "Get_Order_Details :" + Get_Order_Details);
		
		Hashtable Get_Order_Details_HT = qmer.getRow(Get_Order_Details);
		
		LogLevel.DEBUG(5, new Throwable(), "Get_Order_Details_HT :" + Get_Order_Details_HT);

		if(Get_Order_Details_HT.isEmpty())
		{
			strRes="Some thing went wrong, Please contact your DevOps Team";
			return strRes;
		}
		else
		{
			CMN=(String)Get_Order_Details_HT.get("consumer_mobnumber");
			LogLevel.DEBUG(5, new Throwable(), "consumer_mobnumber :" + CMN);
			
			CN=(String)Get_Order_Details_HT.get("consumer_name");
			LogLevel.DEBUG(5, new Throwable(), "consumer_name :" + CN);
			
			CML=(String)Get_Order_Details_HT.get("consumer_emailid");
			LogLevel.DEBUG(5, new Throwable(), "consumer_emailid :" + CML);
			
			CA=(String)Get_Order_Details_HT.get("consumer_address");
			LogLevel.DEBUG(5, new Throwable(), "consumer_address :" + CA);
			
			CA_LandMark="";
			LogLevel.DEBUG(5, new Throwable(), "LandMark :" + CA_LandMark);
			
			RN=(String)Get_Order_Details_HT.get("retailer_name");
			LogLevel.DEBUG(5, new Throwable(), "retailer_name :" + RN);
			
			RMN=(String)Get_Order_Details_HT.get("retailer_mobilnumber");
			LogLevel.DEBUG(5, new Throwable(), "retailer_mobilnumber :" + RMN);
			
			RML=(String)Get_Order_Details_HT.get("retailer_emailid");
			LogLevel.DEBUG(5, new Throwable(), "retailer_emailid :" + RML);
			
			ST=(String)Get_Order_Details_HT.get("services_type");
			LogLevel.DEBUG(5, new Throwable(), "services_type :" + ST);
			
			BT=(String)Get_Order_Details_HT.get("product_type");
			LogLevel.DEBUG(5, new Throwable(), "product_type :" + BT);
			
			PRI=(String)Get_Order_Details_HT.get("service_price_mrp");
			LogLevel.DEBUG(5, new Throwable(), "service_price_mrp :" + PRI);
			
			DiPRI=(String)Get_Order_Details_HT.get("service_price_discount");
			LogLevel.DEBUG(5, new Throwable(), "service_price_discount :" + DiPRI);
			
			PM=(String)Get_Order_Details_HT.get("payment_mode");
			LogLevel.DEBUG(5, new Throwable(), "payment_mode :" + PM);
			
			STATE=(String)Get_Order_Details_HT.get("state");
			LogLevel.DEBUG(5, new Throwable(), "state :" + STATE);
			
			String ST_ORY=STATE.trim().replaceAll(" ", "_"); 
			LogLevel.DEBUG(5, new Throwable(), "ST_ORY :" + ST_ORY);
			
			String Get_Retailer_Details = "select retailer_id,retailer_name,address1,email_id,email_id1 from "+ST_ORY+"_retailers where retailer_name='"+RN+"'";
			LogLevel.DEBUG(5, new Throwable(), "Get_Retailer_Details :" + Get_Retailer_Details);

			Hashtable Get_Retailer_Details_HT = qmer.getRow(Get_Retailer_Details); 
			String Retailer_Id =String.valueOf(Get_Retailer_Details_HT.get("retailer_id"));
			String Retailer_Name =String.valueOf(Get_Retailer_Details_HT.get("retailer_name"));
			String Retailer_Address =(String)Get_Retailer_Details_HT.get("address1");
			
			if(CA_LandMark.isEmpty())
			{
				CA_LandMark=" N/A";
			}
			else if( CA_LandMark.equals(" ") || CA_LandMark.equals("") || CA_LandMark == "" || CA_LandMark ==" ")
			{
				CA_LandMark=" N/A";
			}
			else
			{
				//Do Noting :)
			}
		
			if(strorderreasons.equals("cancelled")){
				
				/*################### Template for Customer ##################*/
				
				Customer_Battery_Order_Msg="Your Order No: "+OrderNumber+" ordered for "+BT+" "+ST+" Service with Price: Rs. "+PRI+" and Payment Mode : "+PM+" has been Cancelled.\nFor any kind of Assistance please call to +919603467559."; 
				
				LogLevel.DEBUG(5, new Throwable(), "Customer_Battery_Order_Msg :" + Customer_Battery_Order_Msg);
				
				
				
				/*################### Template for Retailer	##################*/
		
				Retailer_Battery_Order_Msg="BookBattery Consumer "+CN+" with Mobile No:"+CMN+" and Order Ref Number :"+OrderNumber+" ordered for "+BT+" "+ST+" Service with Price: Rs "+PRI+" and Payment Mode : "+PM+".\n\n Customer Address:"+CA+" and LandMark:"+CA_LandMark+".\n has been Cancelled.\nFor any kind of Assistance please call to +919603467559.";
				
				LogLevel.DEBUG(5, new Throwable(), "Retailer_Battery_Order_Msg :" + Retailer_Battery_Order_Msg);
				
				
				/*################### Template for Operator	##################*/
				
				Operator_Battery_Order_Msg="BookBattery Consumer "+CN+" with Mobile No:"+CMN+" and Order Ref Number :"+OrderNumber+" ordered for "+BT+" "+ST+" Service with Price: Rs "+PRI+" and Payment Mode : "+PM+".\n\n Customer Address:"+CA+" and LandMark:"+CA_LandMark+".\n has been Cancelled.\nFor any kind of Assistance please call to +919603467559.";
				
				LogLevel.DEBUG(5, new Throwable(), "Operator_Battery_Order_Msg :" + Operator_Battery_Order_Msg);
				
			} else if(strorderreasons.equals("installed")){
				
				/*################### Template for Customer ##################*/
				
				Customer_Battery_Order_Msg="Your Order No: "+OrderNumber+" ordered for "+BT+" "+ST+" Service with Price: Rs. "+PRI+" and Payment Mode : "+PM+" has been Successfully Fulfilled by "+Retailer_Name+" with Mob No: "+RMN+" and address "+Retailer_Address+".\nFor any kind of Assistance please call to +919603467559."; 
				
				LogLevel.DEBUG(5, new Throwable(), "Customer_Battery_Order_Msg :" + Customer_Battery_Order_Msg);
				
				
				
				/*################### Template for Retailer	##################*/
		
				Retailer_Battery_Order_Msg="BookBattery Consumer "+CN+" with Mobile No:"+CMN+" and Order Ref Number :"+OrderNumber+" ordered for "+BT+" "+ST+" Service with Price: Rs. "+PRI+" and Payment Mode : "+PM+" has been Successfully Fulfilled.\n\n";
				
				LogLevel.DEBUG(5, new Throwable(), "Retailer_Battery_Order_Msg :" + Retailer_Battery_Order_Msg);
				
				
				/*################### Template for Operator	##################*/
				
				Operator_Battery_Order_Msg="BookBattery Consumer "+CN+" with Mobile No:"+CMN+" and Order Ref Number :"+OrderNumber+" ordered for "+BT+" "+ST+" Service with Price: Rs "+DiPRI+" and Payment Mode : "+PM+" has been Successfully Fulfilled.\n\n";
				
				LogLevel.DEBUG(5, new Throwable(), "Operator_Battery_Order_Msg :" + Operator_Battery_Order_Msg);
				
			} else {
				
				
				/*################### Template for Customer ##################*/
				
				Customer_Battery_Order_Msg="Thank you for booking Service with BookBattery.com.\n\nYour Order No: "+OrderNumber+" ordered for "+BT+" "+ST+" Service for Price: Rs. "+PRI+" and Payment Mode : "+PM+". Our fitment centre rep "+Retailer_Name+" with Mob No: "+RMN+" and address "+Retailer_Address+" will call you soon.\nFor any kind of Assistance please call to +919603467559."; 
				
				LogLevel.DEBUG(5, new Throwable(), "Customer_Battery_Order_Msg :" + Customer_Battery_Order_Msg);
				
				
				
				/*################### Template for Retailer	##################*/
		
				Retailer_Battery_Order_Msg="BookBattery Consumer "+CN+" with Mobile No:"+CMN+" and Order Ref Number :"+OrderNumber+" ordered for  "+BT+" "+ST+" Service for Price: Rs. "+PRI+" and Payment Mode : "+PM+".\n\nCustomer Address:"+CA+" and LandMark:"+CA_LandMark+".\nPlease Contact Customer";
				
				LogLevel.DEBUG(5, new Throwable(), "Retailer_Battery_Order_Msg :" + Retailer_Battery_Order_Msg);
				
				
				/*################### Template for Operator	##################*/
				
				Operator_Battery_Order_Msg="BookBattery Consumer "+CN+" with Mobile No:"+CMN+" and Order Ref Number :"+OrderNumber+" "+BT+" "+ST+" Service for Price: Rs. "+PRI+" and Payment Mode : "+PM+".\n\nCustomer Address:"+CA+" and LandMark:"+CA_LandMark+". Order for Retailer  "+Retailer_Name+" with Mob No: "+RMN+" ";
				
				LogLevel.DEBUG(5, new Throwable(), "Operator_Battery_Order_Msg :" + Operator_Battery_Order_Msg);
			}

		}
		} else if (Order_Type=="Trolley" ||  Order_Type.equals("Trolley")){
			
			LogLevel.DEBUG(5, new Throwable(), "OrderNumber :" + OrderNumber);
			String Get_Order_Details ="select order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailid,state,city,area,pincode,trolley_brand,trolley_model,price,payment_mode,consumer_address from trolley_order_details where order_number ='"+OrderNumber+"'";
			
			LogLevel.DEBUG(5, new Throwable(), "Get_Order_Details :" + Get_Order_Details);
			
			Hashtable Get_Order_Details_HT = qmer.getRow(Get_Order_Details);
			
			LogLevel.DEBUG(5, new Throwable(), "Get_Order_Details_HT :" + Get_Order_Details_HT);

			if(Get_Order_Details_HT.isEmpty())
			{
				strRes="Some thing went wrong, Please contact your DevOps Team";
				return strRes;
			}
			else
			{
				CMN=(String)Get_Order_Details_HT.get("consumer_mobnumber");
				LogLevel.DEBUG(5, new Throwable(), "consumer_mobnumber :" + CMN);
				
				CN=(String)Get_Order_Details_HT.get("consumer_name");
				LogLevel.DEBUG(5, new Throwable(), "consumer_name :" + CN);
				
				CML=(String)Get_Order_Details_HT.get("consumer_emailid");
				LogLevel.DEBUG(5, new Throwable(), "consumer_emailid :" + CML);
				
				CA=(String)Get_Order_Details_HT.get("consumer_address");
				LogLevel.DEBUG(5, new Throwable(), "consumer_address :" + CA);
				
				CA_LandMark="";
				LogLevel.DEBUG(5, new Throwable(), "LandMark :" + CA_LandMark);
				
				RN=(String)Get_Order_Details_HT.get("retailer_name");
				LogLevel.DEBUG(5, new Throwable(), "retailer_name :" + RN);
				
				RMN=(String)Get_Order_Details_HT.get("retailer_mobilnumber");
				LogLevel.DEBUG(5, new Throwable(), "retailer_mobilnumber :" + RMN);
				
				RML=(String)Get_Order_Details_HT.get("retailer_emailid");
				LogLevel.DEBUG(5, new Throwable(), "retailer_emailid :" + RML);
				
				ST=(String)Get_Order_Details_HT.get("services_type");
				LogLevel.DEBUG(5, new Throwable(), "services_type :" + ST);
				
				TB=(String)Get_Order_Details_HT.get("trolley_brand");
				LogLevel.DEBUG(5, new Throwable(), "trolley_brand :" + TB);
				
				TM=(String)Get_Order_Details_HT.get("trolley_model");
				LogLevel.DEBUG(5, new Throwable(), "trolley_model :" + TM);
				
				PRI=(String)Get_Order_Details_HT.get("price");
				LogLevel.DEBUG(5, new Throwable(), "price :" + PRI);
				
				PM=(String)Get_Order_Details_HT.get("payment_mode");
				LogLevel.DEBUG(5, new Throwable(), "payment_mode :" + PM);
				
				STATE=(String)Get_Order_Details_HT.get("state");
				LogLevel.DEBUG(5, new Throwable(), "state :" + STATE);
				
				String ST_ORY=STATE.trim().replaceAll(" ", "_"); 
				LogLevel.DEBUG(5, new Throwable(), "ST_ORY :" + ST_ORY);
				
				String Get_Retailer_Details = "select retailer_id,retailer_name,address1,email_id,email_id1 from "+ST_ORY+"_retailers where retailer_name='"+RN+"'";
				LogLevel.DEBUG(5, new Throwable(), "Get_Retailer_Details :" + Get_Retailer_Details);

				Hashtable Get_Retailer_Details_HT = qmer.getRow(Get_Retailer_Details); 
				String Retailer_Id =String.valueOf(Get_Retailer_Details_HT.get("retailer_id"));
				String Retailer_Name =String.valueOf(Get_Retailer_Details_HT.get("retailer_name"));
				String Retailer_Address =(String)Get_Retailer_Details_HT.get("address1");
				
				if(CA_LandMark.isEmpty())
				{
					CA_LandMark=" N/A";
				}
				else if( CA_LandMark.equals(" ") || CA_LandMark.equals("") || CA_LandMark == "" || CA_LandMark ==" ")
				{
					CA_LandMark=" N/A";
				}
				else
				{
					//Do Noting :)
				}
			
				if(strorderreasons.equals("cancelled")){
					
					/*################### Template for Customer ##################*/
					
					Customer_Battery_Order_Msg="Your Order No: "+OrderNumber+" ordered for "+TB+" "+TM+" Trolley with Price: Rs. "+PRI+" and Payment Mode : "+PM+" has been Cancelled.\nFor any kind of Assistance please call to +919603467559."; 
					
					LogLevel.DEBUG(5, new Throwable(), "Customer_Battery_Order_Msg :" + Customer_Battery_Order_Msg);
					
					
					
					/*################### Template for Retailer	##################*/
			
					Retailer_Battery_Order_Msg="BookBattery Consumer "+CN+" with Mobile No:"+CMN+" and Order Ref Number :"+OrderNumber+" ordered for "+TB+" "+TM+" Trolley with Price: Rs "+PRI+" and Payment Mode : "+PM+".\n\n Customer Address:"+CA+" and LandMark:"+CA_LandMark+".\n has been Cancelled.\nFor any kind of Assistance please call to +919603467559.";
					
					LogLevel.DEBUG(5, new Throwable(), "Retailer_Battery_Order_Msg :" + Retailer_Battery_Order_Msg);
					
					
					/*################### Template for Operator	##################*/
					
					Operator_Battery_Order_Msg="BookBattery Consumer "+CN+" with Mobile No:"+CMN+" and Order Ref Number :"+OrderNumber+" ordered for "+TB+" "+TM+" Trolley with Price: Rs "+PRI+" and Payment Mode : "+PM+".\n\n Customer Address:"+CA+" and LandMark:"+CA_LandMark+".\n has been Cancelled.\nFor any kind of Assistance please call to +919603467559.";
					
					LogLevel.DEBUG(5, new Throwable(), "Operator_Battery_Order_Msg :" + Operator_Battery_Order_Msg);
					
				} else if(strorderreasons.equals("installed")){
					
					/*################### Template for Customer ##################*/
					
					Customer_Battery_Order_Msg="Your Order No: "+OrderNumber+" ordered for "+TB+" "+TM+" Trolley with Price: Rs. "+PRI+" and Payment Mode : "+PM+" has been Successfully Fulfilled by "+Retailer_Name+" with Mob No: "+RMN+" and address "+Retailer_Address+".\nFor any kind of Assistance please call to +919603467559."; 
					
					LogLevel.DEBUG(5, new Throwable(), "Customer_Battery_Order_Msg :" + Customer_Battery_Order_Msg);
					
					
					
					/*################### Template for Retailer	##################*/
			
					Retailer_Battery_Order_Msg="BookBattery Consumer "+CN+" with Mobile No:"+CMN+" and Order Ref Number :"+OrderNumber+" ordered for "+TB+" "+TM+" Service with Price: Rs. "+PRI+" and Payment Mode : "+PM+" has been Successfully Fulfilled.\n\n";
					
					LogLevel.DEBUG(5, new Throwable(), "Retailer_Battery_Order_Msg :" + Retailer_Battery_Order_Msg);
					
					
					/*################### Template for Operator	##################*/
					
					Operator_Battery_Order_Msg="BookBattery Consumer "+CN+" with Mobile No:"+CMN+" and Order Ref Number :"+OrderNumber+" ordered for "+TB+" "+TM+" Trolley with Price: Rs "+PRI+" and Payment Mode : "+PM+" has been Successfully Fulfilled.\n\n";
					
					LogLevel.DEBUG(5, new Throwable(), "Operator_Battery_Order_Msg :" + Operator_Battery_Order_Msg);
					
				} else {
					
					
					/*################### Template for Customer ##################*/
					
					Customer_Battery_Order_Msg="Thank you for booking Trolley with BookBattery.com.\n\nYour Order No: "+OrderNumber+" ordered for "+TB+" "+TM+" Trolley for Price: Rs. "+PRI+" and Payment Mode : "+PM+". Our fitment centre rep "+Retailer_Name+" with Mob No: "+RMN+" and address "+Retailer_Address+" will call you soon.\nFor any kind of Assistance please call to +919603467559."; 
					
					LogLevel.DEBUG(5, new Throwable(), "Customer_Battery_Order_Msg :" + Customer_Battery_Order_Msg);
					
					
					
					/*################### Template for Retailer	##################*/
			
					Retailer_Battery_Order_Msg="BookBattery Consumer "+CN+" with Mobile No:"+CMN+" and Order Ref Number :"+OrderNumber+" ordered for  "+TB+" "+TM+" Trolley for Price: Rs. "+PRI+" and Payment Mode : "+PM+".\n\nCustomer Address:"+CA+" and LandMark:"+CA_LandMark+".\nPlease Contact Customer";
					
					LogLevel.DEBUG(5, new Throwable(), "Retailer_Battery_Order_Msg :" + Retailer_Battery_Order_Msg);
					
					
					/*################### Template for Operator	##################*/
					
					Operator_Battery_Order_Msg="BookBattery Consumer "+CN+" with Mobile No:"+CMN+" and Order Ref Number :"+OrderNumber+" "+TB+" "+TM+" Trolley for Price: Rs. "+PRI+" and Payment Mode : "+PM+".\n\nCustomer Address:"+CA+" and LandMark:"+CA_LandMark+". Order for Retailer  "+Retailer_Name+" with Mob No: "+RMN+" ";
					
					LogLevel.DEBUG(5, new Throwable(), "Operator_Battery_Order_Msg :" + Operator_Battery_Order_Msg);
				}
			}
		}		
		
		else
		{
			strRes="Some thing went wrong, Please contact your DevOps Team";
			return strRes;
		}
		
		String Mail_Order_Info="";
		if(Order_Type=="Inverter" ||  Order_Type.equals("Inverter"))
		{
			Mail_Order_Info="Inverter and Inverter Battery Orders:\n1.Wiring should be ready before fitment of inverter.\n2.Existing battery AH should be same as the new battery. Prices will vary if AH differs. \n3.If upgrading to higher rating inverter need to keep ready higher amps rating wiring before fitment of inverter/wiring should support to higher amps inverter .\n4.If location is more than 5 Kms from the Our location, extra delivery charges may be applicable.\n \n";
		}
		else{
			Mail_Order_Info="";
		}
	
	
		String Get_Invoice_Details_SQL = "select order_number,invoice_number,invoice_url,customer_name,store_name from batterystore_invoice where order_number='"+OrderNumber+"'";
		LogLevel.DEBUG(5, new Throwable(), "Get_Invoice_Details_SQL :" + Get_Invoice_Details_SQL);
		Hashtable InvoiceQuery_HT = qmer.getRow(Get_Invoice_Details_SQL);
		if(InvoiceQuery_HT.isEmpty())
		{
				
			strRes = "No Details found";
			return strRes;
		}
		else
		{
			 
			 String order_number_iq=(String)InvoiceQuery_HT.get("order_number");
			 String invoice_number_iq=(String)InvoiceQuery_HT.get("invoice_number");
			 String invoice_url_iq=(String)InvoiceQuery_HT.get("invoice_url");
			 INF = invoice_url_iq.replace("http://stage1.bookbattery.com/bookbattery/","/home/ngit/tomcat/webapps/bookbattery/");
			 LogLevel.DEBUG(1,new Throwable(),"invoice_file! Prasannna " +INF);
			 LogLevel.DEBUG(1,new Throwable(),"invoice_url_iq! Madhann" +invoice_url_iq);
			// strRes = invoice_url_iq;
		}
		
		/*################### Mail for Customer ##################*/
		if( Mail_Customer.equals("Yes") || Mail_Customer.equals("yes") || Mail_Customer == "Yes" || Mail_Customer =="yes")
		{
			String Mail_Customer_Battery="Dear "+CN+",\r\n\r\n"+""+Customer_Battery_Order_Msg+"";
			String Mail_Customer_Battery_Footer="Thanks & Regards,"+"\r\n"+""+MAIL_CUS_Domain+" Support Team."; 
			MailTrans Mail_Customer_Battery_Tran=new MailTrans();
			Mail_Customer_Battery_Tran.setStrSmtpHost(strdomaincusname);
			Mail_Customer_Battery_Tran.setStrFrom(From_Name_CUS_MailID);
			Mail_Customer_Battery_Tran.setStrTo(CML);
			Mail_Customer_Battery_Tran.setStrSubject(""+MAIL_CUS_Domain+" "+Order_Type+" Details. Order No : "+OrderNumber+"");
			String SEND_Mail_Link = Mail_Customer_Battery+"\r\n\r\n"+Mail_Customer_Battery_Footer+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\n"+Mail_Order_Info+"Confidentiality Information and Disclaimer:\nThis communication sent from "+MAIL_CUS_Domain+" is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. "+MAIL_CUS_Domain+" does not accept any liability what-so-ever including on account of any errors, omissions, viruses etc in the contents of this message.";
			LogLevel.DEBUG(5, new Throwable(), "SEND_Mail_Link :" + SEND_Mail_Link);
			LogLevel.DEBUG(5, new Throwable(), "setStrFilesToAttach :" + INF);
			Mail_Customer_Battery_Tran.setStrFilesToAttach(INF);
			LogLevel.DEBUG(5, new Throwable(), "After setStrFilesToAttach :" + INF);
			Mail_Customer_Battery_Tran.setStrText(SEND_Mail_Link);
			Thread Mail_Customer_Battery_Thread=new MailThread(Mail_Customer_Battery_Tran,"");
			Mail_Customer_Battery_Thread.start();
			
		}
		else{
			LogLevel.DEBUG(5, new Throwable(), "SMS Not Sent to Consumer");
		}
		

			MAIL_Domain="BookBattery";
			MAIL_SMS_Domain="9603467559";
			From_Name_MailID="BookBattery<helpdesk.bookbattery@gmail.com>";
			strdomainname="www.bookbattery.com";
		
		LogLevel.DEBUG(5, new Throwable(), "Mail_Customer :" + Mail_Customer);
		
	/*################### Mail for sir for new order ##################*/
		if( Mail_Sir.equals("Yes") || Mail_Sir.equals("yes") || Mail_Sir == "Yes" || Mail_Sir =="yes")
		{
			/***Code to send Email to Praveen Sir starts here*****/
			
			//String Sir_emailid1 ="helpdesk.bookbattery@gmail.com";
			String Sir_emailid1 ="prasanna@bookbattery.com";
			String mailsubject="Ordered";
			
			
			mailsubject="Ordered";
			LogLevel.DEBUG(5, new Throwable(), "Inside Ordered :" + mailsubject);
			
			String Mail_Sir_Battery="Dear Sir,\r\n\r\n"+""+Retailer_Battery_new_Order_Mail_Msg+"";
			String Mail_Sir_Battery_Footer="Thanks & Regards,"+"\r\n"+""+MAIL_Domain+" Support Team."; 
			MailTrans Mail_Sir_Battery_Tran=new MailTrans();
			Mail_Sir_Battery_Tran.setStrSmtpHost(strdomainname);
			Mail_Sir_Battery_Tran.setStrFrom(From_Name_MailID);
			Mail_Sir_Battery_Tran.setStrTo(Sir_emailid1);
			Mail_Sir_Battery_Tran.setStrSubject(""+MAIL_Domain+" "+mailsubject+" "+Order_Type+" Order Details. Order No : "+OrderNumber+"");
			String SEND_Mail_Link = Mail_Sir_Battery+"\r\n\r\n"+Mail_Sir_Battery_Footer+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from "+MAIL_Domain+" is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. "+MAIL_Domain+" does not accept any liability what-so-ever including on account of any errors, omissions, viruses etc in the contents of this message.";
			LogLevel.DEBUG(5, new Throwable(), "SEND_Mail_Link :" + SEND_Mail_Link);
			
			Mail_Sir_Battery_Tran.setStrText(SEND_Mail_Link);
			Thread Mail_Sir_Battery_Thread=new MailThread(Mail_Sir_Battery_Tran,"");
			Mail_Sir_Battery_Thread.start();
			
			/***Code to send Email to the Praveen Sir Ends here*****/
		}
		else{
			LogLevel.DEBUG(5, new Throwable(), "SMS Not Sent to Consumer");
		}
		

		/*################### Mail for Retailer ##################*/
		if( Mail_Retailer.equals("Yes") || Mail_Retailer.equals("yes") || Mail_Retailer == "Yes" || Mail_Retailer =="yes")
		{
			String Mail_Retailer_Battery="Dear "+RN+",\r\n\r\n"+""+Retailer_Battery_Order_Msg+"";
			String Mail_Retailer_Battery_Footer="Thanks & Regards,"+"\r\n"+""+MAIL_Domain+" Support Team."; 
			MailTrans Mail_Retailer_Battery_Tran=new MailTrans();
			Mail_Retailer_Battery_Tran.setStrSmtpHost(strdomainname);
			Mail_Retailer_Battery_Tran.setStrFrom(From_Name_MailID);
			Mail_Retailer_Battery_Tran.setStrTo(RML);
			Mail_Retailer_Battery_Tran.setStrSubject(""+MAIL_Domain+" "+Order_Type+" Details. Order No : "+OrderNumber+"");
			String SEND_Mail_Link = Mail_Retailer_Battery+"\r\n\r\n"+Mail_Retailer_Battery_Footer+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from "+MAIL_Domain+" is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. "+MAIL_Domain+" does not accept any liability what-so-ever including on account of any errors, omissions, viruses etc in the contents of this message.";
			LogLevel.DEBUG(5, new Throwable(), "SEND_Mail_Link :" + SEND_Mail_Link);
			
			Mail_Retailer_Battery_Tran.setStrText(SEND_Mail_Link);
			Thread Mail_Retailer_Battery_Thread=new MailThread(Mail_Retailer_Battery_Tran,"");
			Mail_Retailer_Battery_Thread.start();
			
		
		}
		else{
			LogLevel.DEBUG(5, new Throwable(), "SMS Not Sent to Consumer");
		}
	
		/*################### Mail for Operator ##################*/
		if( Mail_Operator.equals("Yes") || Mail_Operator.equals("yes") || Mail_Operator == "Yes" || Mail_Operator =="yes")
		{
			String Mail_Operator_Battery="Dear Operator,\r\n\r\n"+""+Operator_Battery_Order_Msg+"";
			String Mail_Operator_Battery_Footer="Thanks & Regards,"+"\r\n"+""+MAIL_Domain+" Support Team."; 
			MailTrans Mail_Operator_Battery_Tran=new MailTrans();
			Mail_Operator_Battery_Tran.setStrSmtpHost(strdomainname);
			Mail_Operator_Battery_Tran.setStrFrom(From_Name_MailID);
			//Mail_Operator_Battery_Tran.setStrTo("bookbattery@ngit.in");
			Mail_Operator_Battery_Tran.setStrTo("helpdesk.bookbattery@gmail.com");			
			Mail_Operator_Battery_Tran.setStrSubject(""+MAIL_Domain+" "+Order_Type+" Details. Order No : "+OrderNumber+"");
			String SEND_Mail_Link = Mail_Operator_Battery+"\r\n\r\n"+Mail_Operator_Battery_Footer+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from "+MAIL_Domain+" is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. "+MAIL_Domain+" does not accept any liability what-so-ever including on account of any errors, omissions, viruses etc in the contents of this message.";
			LogLevel.DEBUG(5, new Throwable(), "SEND_Mail_Link :" + SEND_Mail_Link);
			
			Mail_Operator_Battery_Tran.setStrText(SEND_Mail_Link);
			Thread Mail_Operator_Battery_Thread=new MailThread(Mail_Operator_Battery_Tran,"");
			Mail_Operator_Battery_Thread.start();
		}
		else{
			LogLevel.DEBUG(5, new Throwable(), "SMS Not Sent to Consumer");
		}
	
		return strRes;
	}
	
}//end of Class


