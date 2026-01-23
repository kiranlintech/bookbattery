package com.ngit.javabean.consumers.products;
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.mail.*;
import com.ngit.javabean.sendsms.SendSMS;
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


public class Order_Service_SMS 
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
    public Order_Service_SMS(QueryManager qm) 
	{
        qmer=qm;
    }
	/* *************************************************************************************************************\
	* This method is used to Send SMS Order Details to Customer
	\* *************************************************************************************************************/
	public String Send_Order_SMS(HttpServletRequest req , HttpServletResponse res,HttpSession session, String OrderNumber,String SMS_Customer,String SMS_Retailer,String SMS_Operator) 
	{

		SendSMS Send_SMS = new SendSMS(qmer);
		
		/*####  Variables Declaration  ###*/
		String CN ="";
		String CMN ="";
		String RN ="";
		String RM ="";
		String ST ="";
		String SP ="";
		String PRI ="";
		String DiPRI ="";
		String PM ="";
		String STATE ="";
		String Area_Pincode ="";
		String strRes ="";
        String PMODE ="";
		
		
		String SMS_For_Customer="";
		String SMS_For_Customer1="";
		String SMS_For_Retailer="";
		String SMS_For_Operator="";
		
		String SMS_Domain="BookBattery";
		String SMS_Contact="9603467559";
		

		LogLevel.DEBUG(5, new Throwable(), "OrderNumber :" + OrderNumber);
		String Get_Order_Details ="select consumer_mobnumber, consumer_name,retailer_name, retailer_mobilnumber, services_type, services_package, service_price_mrp, service_price_discount,payment_mode,state,area,pincode,batterywale_order from batterywaledb.service_order_details where order_number ='"+OrderNumber+"'";
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
			
			RM=(String)Get_Order_Details_HT.get("retailer_mobilnumber");
			LogLevel.DEBUG(5, new Throwable(), "retailer_mobilnumber :" + RM);
			
			ST=(String)Get_Order_Details_HT.get("services_type");
			LogLevel.DEBUG(5, new Throwable(), "services_type :" + ST);
			
			SP =(String)Get_Order_Details_HT.get("services_package");
			LogLevel.DEBUG(5, new Throwable(), "services_package :" + SP);
			
			PRI=(String)Get_Order_Details_HT.get("service_price_mrp");
			LogLevel.DEBUG(5, new Throwable(), "service_price_mrp :" + PRI);
			
			DiPRI=(String)Get_Order_Details_HT.get("service_price_discount");
			LogLevel.DEBUG(5, new Throwable(), "service_price_discount :" + DiPRI);

			PM=(String)Get_Order_Details_HT.get("payment_mode");
			LogLevel.DEBUG(5, new Throwable(), "payment_mode :" + PM);
            
            if(PM.equals("Online Payment After Fitment"))
            {
                PMODE="OPAF";
            }
            else
            {
                PMODE=PM;
            }
            
            PM=PM.trim().replaceAll(" ", ""); 
            
			STATE=(String)Get_Order_Details_HT.get("state");
			LogLevel.DEBUG(5, new Throwable(), "STATE :" + STATE);
			
			String Area=(String)Get_Order_Details_HT.get("area");
			LogLevel.DEBUG(5, new Throwable(), "area :" + Area);
			
			String Pincode=(String)Get_Order_Details_HT.get("pincode");
			LogLevel.DEBUG(5, new Throwable(), "pincode :" + Pincode);			
			
			String batterywale_order=(String)Get_Order_Details_HT.get("batterywale_order");
			LogLevel.DEBUG(5, new Throwable(), "batterywale_order :" + batterywale_order);
			
			if( Pincode.equals("0") || Pincode.equals("")|| Pincode.equals(" ") || Pincode.equals("0") || Pincode == "0" || Pincode =="0")
			{
				Area_Pincode=Area;
				LogLevel.DEBUG(5, new Throwable(), "Area");
			}
			else
			{
				Area_Pincode=Pincode;
				LogLevel.DEBUG(5, new Throwable(), "Pincode");
			}
			
			if( batterywale_order.equals("Yes") || batterywale_order.equals("YES")|| batterywale_order.equals("yes"))
			{
				SMS_Domain="BookBattery";
				LogLevel.DEBUG(5, new Throwable(), "SMS_Domain"+SMS_Domain);
			}
			else
			{
				SMS_Domain=SMS_Domain;
				LogLevel.DEBUG(5, new Throwable(), "SMS_Domain"+SMS_Domain);
			}
						
			/*################### Template for Customer
			Thanks for Booking Service with XXXX . Order No: XXXX , Details: XXXX ,Price: Rs. XXXX and Payment Mode: XXXX . You will shortly receive a call. For any kind of assistance please call to XXXX 
			##################*/

			//SMS_For_Customer="Thanks for Booking Service with "+SMS_Domain.trim()+" . Order No: "+OrderNumber.trim()+" , Details: "+ST.trim()+", "+SP.trim()+" ,Price: Rs. "+DiPRI.trim()+" and Payment Mode: "+PM.trim()+" . Our fitment centre rep with Mob No: "+RM.trim()+" will call you soon";
						
			//SMS_For_Customer="Thanks for Booking Service with "+SMS_Domain.trim()+" . Order No: "+OrderNumber.trim()+" , Details: "+ST.trim()+" ,Price: Rs. "+DiPRI.trim()+" and Payment Mode: "+PM.trim()+" . You will shortly receive a call. For any kind of assistance please call to "+SMS_Contact+"";
			
			
			SMS_For_Customer="Thanks for Booking "+ST.trim()+" with "+SMS_Domain.trim()+" . Order No: "+OrderNumber.trim()+" , Details: "+ST.trim()+" ,Price: Rs. "+DiPRI.trim()+" and Payment Mode: "+PMODE.trim()+" . You will shortly receive a call. For any kind of assistance please call to "+SMS_Contact+"";
			
			//SMS_For_Customer=SMS_For_Customer.trim();
					
			LogLevel.DEBUG(5, new Throwable(), "SMS_For_Customer :" + SMS_For_Customer);
		
			//SMS_For_Customer1="Thanks for ordering with us. Your Order for "+ST.trim()+" "+SP.trim()+" model with Order no: "+OrderNumber+" is confirmed. Our fitment Rep with Mob No: "+RM+" will process your order. For any kind of assistance please call to "+SMS_Contact+"";
			
			SMS_For_Customer1="Thanks for ordering with us. Your Order for "+ST.trim()+" with Order no: "+OrderNumber+" is confirmed. Our Service Rep with Mob No: "+RM+" will process your order. For any kind of assistance please call to "+SMS_Contact+"";
			

			/*################### Template for Retailer
			Cust Name: XXXX Mob No: XXXX & Order No: XXXX ordered XXXX at Price: Rs. XXXX & Payment Mode: XXXX . Please Contact
			##################*/
			
			SMS_For_Retailer="Cust Name: "+CN.trim()+" Mob No: "+CMN.trim()+" & Order No: "+OrderNumber.trim()+" ordered "+ST.trim()+" at Price: Rs. "+DiPRI.trim()+" & Payment Mode: "+PMODE.trim()+" . Please Contact";
			
			//SMS_For_Retailer=SMS_For_Retailer.trim();
				
			LogLevel.DEBUG(5, new Throwable(), "SMS_For_Retailer :" + SMS_For_Retailer);
			
			/*################### Template for Operator
			Service Ord No: XXXX and XXXX at Price: Rs. XXXX and PM: XXXX .Cust Mob No: XXXX Dealer Mob No: XXXX 
			##################*/
		
			SMS_For_Operator=""+SMS_Domain+" Ord No: "+OrderNumber.trim()+" and "+ST.trim()+" at Price: Rs. "+DiPRI.trim()+" and PM: "+PMODE.trim()+" .Cust Mob No: "+CMN.trim()+" Dealer Mob No: "+RM.trim()+" ";
			
			//SMS_For_Operator=SMS_For_Operator.trim();
			
			LogLevel.DEBUG(5, new Throwable(), "SMS_For_Operator :" + SMS_For_Operator);						
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
		else if( SMS_Customer.equals("Confirm") || SMS_Customer.equals("confirm") || SMS_Customer == "Confirm" || SMS_Customer =="confirm")
		{
			String Send_SMS_Response=  Send_SMS.sendSMS(strIpAddress,strPort,SMSFromAddress,SMS_For_Customer1,CMN);
			LogLevel.DEBUG(5, new Throwable(), "Send_SMS_Response :" + Send_SMS_Response);
			strRes=strRes+"SMS sent to Customer afterconfirm "+CMN;
		}
		else
		{
			LogLevel.DEBUG(5, new Throwable(), "SMS Not Sent to Consumer");
		}
		/*################### SMS for Retailer ##################*/
		if( SMS_Retailer.equals("Yes") || SMS_Retailer.equals("yes") || SMS_Retailer == "Yes" || SMS_Retailer =="yes")
		{
			String ST_ORY=STATE.trim().replaceAll(" ", "_"); 
			LogLevel.DEBUG(5, new Throwable(), "ST_ORY :" + ST_ORY);
			
			String Get_Retailer_Details = "select retailer_id,retailer_name,mobile_number,mobile_numberother from batterywaledb."+ST_ORY+"_retailers where retailer_name='"+RN+"'";
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
					for (int k=0;k<Retailer_Other_Number_Arr.length ;k++ )
					{	
						Retailer_Other_Number_Arr_TempValue=Retailer_Other_Number_Arr[k];
						LogLevel.DEBUG(5,new Throwable(),"Retailer_Other_Number_Arr_TempValue:"+Retailer_Other_Number_Arr_TempValue+" \n");
						Send_SMS_Response=  Send_SMS.sendSMS(strIpAddress,strPort,SMSFromAddress,SMS_For_Retailer, Retailer_Other_Number_Arr_TempValue);
						LogLevel.DEBUG(5, new Throwable(), "Send_SMS_Response :" + Send_SMS_Response);
						
						strRes=strRes+"SMS sent to Retailer "+Retailer_Other_Number_Arr_TempValue+"  ";
					}
				}
			}
			
		}
		else{
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
	public String Send_Order_Mail(HttpServletRequest req , HttpServletResponse res,HttpSession session, String OrderNumber,String Mail_Customer,String Mail_Retailer,String Mail_Operator) 
	{ 		
		String CN ="";
		String CMN ="";
		String CML ="";
		String CA ="";
		String CA_LandMark ="";
		String RN ="";
		String RMN ="";
		String RML ="";
		String ST ="";
		String SP ="";
		String PRI ="";
		String DiPRI ="";
		String PM ="";
		String STATE ="";
		String strRes ="";
		String ORDBY ="";
		String QTY ="";
		String batterywale_order ="";

		String Customer_Mail_Order_Msg ="";
		String Retailer_Mail_Order_Msg ="";
		String Operator_Mail_Order_Msg ="";
		String SMS_Domain ="";

		/*String MAIL_Domain="BookBattery";
		String From_Name_MailID="BookBattery<he>";
		String strdomainname="www.bookbattery.com";	*/	
		
		
		String MAIL_Domain="BookBattery";
		String From_Name_MailID="BookBattery<helpdesk.bookbattery@gmail.com>";
		String strdomainname="www.bookbattery.com";
		
		LogLevel.DEBUG(5, new Throwable(), "OrderNumber :" + OrderNumber);
		String Get_Order_Details ="select consumer_emailid,consumer_mobnumber, consumer_name,consumer_address1, consumer_address2, retailer_name, retailer_emailid,retailer_mobilnumber, services_type, batterywale_order, services_package, service_price_mrp, service_price_discount,quantity, payment_mode, operator, state, area, pincode from batterywaledb.service_order_details where order_number ='"+OrderNumber+"'";
		
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
			
			CA=(String)Get_Order_Details_HT.get("consumer_address1");
			LogLevel.DEBUG(5, new Throwable(), "consumer_address1 :" + CA);
			
			CA_LandMark=(String)Get_Order_Details_HT.get("consumer_address2");
			LogLevel.DEBUG(5, new Throwable(), "LandMark :" + CA_LandMark);
			
			RN=(String)Get_Order_Details_HT.get("retailer_name");
			LogLevel.DEBUG(5, new Throwable(), "retailer_name :" + RN);
			
			RMN=(String)Get_Order_Details_HT.get("retailer_mobilnumber");
			LogLevel.DEBUG(5, new Throwable(), "retailer_mobilnumber :" + RMN);
			
			RML=(String)Get_Order_Details_HT.get("retailer_emailid");
			LogLevel.DEBUG(5, new Throwable(), "retailer_emailid :" + RML);
			
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
			
			STATE=(String)Get_Order_Details_HT.get("state");
			LogLevel.DEBUG(5, new Throwable(), "state :" + STATE);
			
			String ST_ORY=STATE.trim().replaceAll(" ", "_"); 
			LogLevel.DEBUG(5, new Throwable(), "ST_ORY :" + ST_ORY);
			
			ORDBY=(String)Get_Order_Details_HT.get("operator");
			LogLevel.DEBUG(5, new Throwable(), "ORDBY :" + ORDBY);			

			QTY=(String)Get_Order_Details_HT.get("quantity");
			LogLevel.DEBUG(5, new Throwable(), "QTY :" + QTY);			
			
			batterywale_order=(String)Get_Order_Details_HT.get("batterywale_order");
			LogLevel.DEBUG(5, new Throwable(), "batterywale_order :" + batterywale_order);
			
			
			
			if( batterywale_order.equals("Yes") || batterywale_order.equals("YES")|| batterywale_order.equals("yes"))
			{
				SMS_Domain="BookBattery";
				LogLevel.DEBUG(5, new Throwable(), "SMS_Domain"+SMS_Domain);
				MAIL_Domain="BookBattery";
				From_Name_MailID="BookBattery<helpdesk.bookbattery@gmail.com>";
				strdomainname="www.bookbattery.com";
			}
			else
			{
				SMS_Domain="BookBattery";
				MAIL_Domain="BookBattery";
				From_Name_MailID="BookBattery<helpdesk.bookbattery@gmail.com>";
				strdomainname="www.bookbattery.com";
				LogLevel.DEBUG(5, new Throwable(), "SMS_Domain"+SMS_Domain);
			}
			

			String Get_Retailer_Details = "select retailer_id,retailer_name,address1,email_id,email_id1 from batterywaledb."+ST_ORY+"_retailers where retailer_name='"+RN+"'";
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
			
			/*################### Template for Customer
			##################*/	
			
			Customer_Mail_Order_Msg="Thank you for booking Battery Health Checkup with "+SMS_Domain+".\n\nYour Order No: "+OrderNumber+" ordered for  "+ST+" with Price: Rs. "+DiPRI+", Quantity: "+QTY+"  and Payment Mode : "+PM+". Our Service rep "+Retailer_Name+" with Mob No: "+RMN+" and address "+Retailer_Address+" will call you soon.\nFor any kind of Assistance please call to +919603467559.";
			
			LogLevel.DEBUG(5, new Throwable(), "Customer_Mail_Order_Msg :" + Customer_Mail_Order_Msg);
			
			/*################### Template for Retailer
			##################*/
	
			Retailer_Mail_Order_Msg=""+SMS_Domain+" Consumer "+CN+" with Mobile No:"+CMN+" and Order Ref Number :"+OrderNumber+" ordered for  "+ST+" with Price: Rs "+DiPRI+", Quantity: "+QTY+" and Payment Mode : "+PM+".\n\nCustomer Address:"+CA+" and LandMark:"+CA_LandMark+".\nPlease Contact Customer";
			
			LogLevel.DEBUG(5, new Throwable(), "Retailer_Mail_Order_Msg :" + Retailer_Mail_Order_Msg);
						
			/*################### Template for Operator
			##################*/			
			Operator_Mail_Order_Msg=""+SMS_Domain+" Consumer "+CN+" with Mobile No:"+CMN+" and Order Ref Number :"+OrderNumber+" ordered for "+ST+" Service with Price: Rs "+DiPRI+", Quantity: "+QTY+" and Payment Mode : "+PM+".\n\nCustomer Address:"+CA+" and LandMark:"+CA_LandMark+". Order for Retailer  "+Retailer_Name+" with Mob No: "+RMN+" ";
			
			LogLevel.DEBUG(5, new Throwable(), "Operator_Mail_Order_Msg :" + Operator_Mail_Order_Msg);
			LogLevel.DEBUG(5, new Throwable(), "Mail_Customer :" + Mail_Customer);
		}	
		/*################### Mail for Customer ##################*/
		if( Mail_Customer.equals("Yes") || Mail_Customer.equals("yes") || Mail_Customer == "Yes" || Mail_Customer =="yes")
		{
			String Mail_Customer_Battery="Dear "+CN+",\r\n\r\n"+""+Customer_Mail_Order_Msg+"";
			String Mail_Customer_Footer="Thanks & Regards,"+"\r\n"+""+SMS_Domain+" Support Team."; 
			MailTrans Mail_Customer_Tran=new MailTrans();
			Mail_Customer_Tran.setStrSmtpHost(strdomainname);
			Mail_Customer_Tran.setStrFrom(From_Name_MailID);
			Mail_Customer_Tran.setStrTo(CML);
			Mail_Customer_Tran.setStrSubject(""+SMS_Domain+" Battery Health Checkup Details. Order No : "+OrderNumber+"");
			String SEND_Mail_Link = Mail_Customer_Battery+"\r\n\r\n"+Mail_Customer_Footer+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from "+SMS_Domain+" is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. "+SMS_Domain+" does not accept any liability what-so-ever including on account of any errors, omissions, viruses etc in the contents of this message.";
			LogLevel.DEBUG(5, new Throwable(), "SEND_Mail_Link :" + SEND_Mail_Link);
			
			Mail_Customer_Tran.setStrText(SEND_Mail_Link);
			Thread Mail_Customer_Thread=new MailThread(Mail_Customer_Tran,"");
			Mail_Customer_Thread.start();
		}
		else
		{
			LogLevel.DEBUG(5, new Throwable(), "SMS Not Sent to Consumer");
		}				
		
		/*################### Mail for sir for new order ##################*/
		
		if(Mail_Customer.equals("Sir") || Mail_Customer.equals("sir") || Mail_Customer == "SIR" || Mail_Customer =="SIr")
		{
			/***Code to send Email to helpdesk Sir starts here*****/
			
			String Sir_emailid1 ="helpdesk.bookbattery@gmail.com";
			//String Sir_emailid1 ="bharath@bookbattery.com";
			String ORDBY_MSG ="This is the order placed by "+ORDBY+". ";
			
			//String Mail_Sir_Battery="Dear Sir,\r\n\r\n"+ORDBY_MSG+"\r\n\r\n"+Retailer_Battery_new_Order_Mail_Msg+"";
			String Mail_Sir_Battery="Dear Sir,\r\n\r\n"+""+Retailer_Mail_Order_Msg+"";
			String Mail_Sir_Battery_Footer="Thanks & Regards,"+"\r\n"+""+SMS_Domain+" Support Team."; 
			MailTrans Mail_Sir_Battery_Tran=new MailTrans();
			Mail_Sir_Battery_Tran.setStrSmtpHost(strdomainname);
			Mail_Sir_Battery_Tran.setStrFrom(From_Name_MailID);
			Mail_Sir_Battery_Tran.setStrTo(Sir_emailid1);
			Mail_Sir_Battery_Tran.setStrSubject(""+SMS_Domain+" Battery Health Checkup Details. Order No : "+OrderNumber+"");
			String SEND_Mail_Link = Mail_Sir_Battery+"\r\n\r\n"+Mail_Sir_Battery_Footer+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from "+SMS_Domain+" is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. "+SMS_Domain+" does not accept any liability what-so-ever including on account of any errors, omissions, viruses etc in the contents of this message.";
			LogLevel.DEBUG(5, new Throwable(), "SEND_Mail_Link :" + SEND_Mail_Link);
			
			Mail_Sir_Battery_Tran.setStrText(SEND_Mail_Link);
			Thread Mail_Sir_Battery_Thread=new MailThread(Mail_Sir_Battery_Tran,"");
			Mail_Sir_Battery_Thread.start();

			/***Code to send Email to the helpdesk Sir Ends here*****/
		}
		else
		{
			LogLevel.DEBUG(5, new Throwable(), "SMS Not Sent to Consumer");
		}
		
		/*################### Mail for Retailer ##################*/
		if( Mail_Retailer.equals("Yes") || Mail_Retailer.equals("yes") || Mail_Retailer == "Yes" || Mail_Retailer =="yes")
		{
			String Mail_Retailer_Battery="Dear "+RN+",\r\n\r\n"+""+Retailer_Mail_Order_Msg+"";
			String Mail_Retailer_Footer="Thanks & Regards,"+"\r\n"+""+SMS_Domain+" Support Team."; 
			MailTrans Mail_Retailer_Tran=new MailTrans();
			Mail_Retailer_Tran.setStrSmtpHost(strdomainname);
			Mail_Retailer_Tran.setStrFrom(From_Name_MailID);
			Mail_Retailer_Tran.setStrTo(RML);
			Mail_Retailer_Tran.setStrSubject(""+SMS_Domain+" Battery Health Checkup Details. Order No : "+OrderNumber+"");
			String SEND_Mail_Link = Mail_Retailer_Battery+"\r\n\r\n"+Mail_Retailer_Footer+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from "+SMS_Domain+" is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. "+SMS_Domain+" does not accept any liability what-so-ever including on account of any errors, omissions, viruses etc in the contents of this message.";
			LogLevel.DEBUG(5, new Throwable(), "SEND_Mail_Link :" + SEND_Mail_Link);
			
			Mail_Retailer_Tran.setStrText(SEND_Mail_Link);
			Thread Mail_Retailer_Thread=new MailThread(Mail_Retailer_Tran,"");
			Mail_Retailer_Thread.start();
						
			/***Code to send Email to Helpdesk Sir starts here*****/
				
			//String Mail_Sir_Battery="Dear Sir,\r\n\r\n"+""+Retailer_Battery_Order_Msg+"";
			String Mail_Sir_Battery="Dear Supervisor/Operator,\r\n\r\n"+""+Retailer_Mail_Order_Msg+"";
			String Mail_Sir_Battery_Footer="Thanks & Regards,"+"\r\n"+""+SMS_Domain+" Support Team."; 
			MailTrans Mail_Sir_Battery_Tran=new MailTrans();
			//String Sir_emailid ="bharath@bookbattery.com";
			String Sir_emailid ="helpdesk.bookbattery@gmail.com";
			Mail_Sir_Battery_Tran.setStrSmtpHost(strdomainname);
			Mail_Sir_Battery_Tran.setStrFrom(From_Name_MailID);
			Mail_Sir_Battery_Tran.setStrTo(Sir_emailid);
			Mail_Sir_Battery_Tran.setStrSubject(""+SMS_Domain+" Confirmed Battery Health Checkup Order Details. Order No : "+OrderNumber+"");
			SEND_Mail_Link = Mail_Sir_Battery+"\r\n\r\n"+Mail_Sir_Battery_Footer+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from "+SMS_Domain+" is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. "+SMS_Domain+" does not accept any liability what-so-ever including on account of any errors, omissions, viruses etc in the contents of this message.";
			LogLevel.DEBUG(5, new Throwable(), "SEND_Mail_Link :" + SEND_Mail_Link);
			
			Mail_Sir_Battery_Tran.setStrText(SEND_Mail_Link);
			Thread Mail_Sir_Battery_Thread=new MailThread(Mail_Sir_Battery_Tran,"");
			Mail_Sir_Battery_Thread.start();
			
			/***Code to send Email to the Helpdesk Sir Ends here*****/
		}
		else{
			LogLevel.DEBUG(5, new Throwable(), "SMS Not Sent to Consumer");
		}
	
		/*################### Mail for Operator ##################*/
		if( Mail_Operator.equals("Yes") || Mail_Operator.equals("yes") || Mail_Operator == "Yes" || Mail_Operator =="yes")
		{
			String Mail_Operator_Battery="Dear Operator,\r\n\r\n"+""+Operator_Mail_Order_Msg+"";
			String Mail_Operator_Footer="Thanks & Regards,"+"\r\n"+""+SMS_Domain+" Support Team."; 
			MailTrans Mail_Operator_Tran=new MailTrans();
			Mail_Operator_Tran.setStrSmtpHost(strdomainname);
			Mail_Operator_Tran.setStrFrom(From_Name_MailID);
			//Mail_Operator_Tran.setStrTo("bookbattery@ngit.in");
			Mail_Operator_Tran.setStrTo("helpdesk.bookbattery@gmail.com");
			Mail_Operator_Tran.setStrSubject(""+SMS_Domain+" Battery Health Checkup Details. Order No : "+OrderNumber+"");
			String SEND_Mail_Link = Mail_Operator_Battery+"\r\n\r\n"+Mail_Operator_Footer+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from "+SMS_Domain+" is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. "+SMS_Domain+" does not accept any liability what-so-ever including on account of any errors, omissions, viruses etc in the contents of this message.";
			LogLevel.DEBUG(5, new Throwable(), "SEND_Mail_Link :" + SEND_Mail_Link);
			
			Mail_Operator_Tran.setStrText(SEND_Mail_Link);
			Thread Mail_Operator_Thread=new MailThread(Mail_Operator_Tran,"");
			Mail_Operator_Thread.start();
		}
		else{
			LogLevel.DEBUG(5, new Throwable(), "SMS Not Sent to Consumer");
		}
	
		return strRes;
	}
	
}//end of Class


