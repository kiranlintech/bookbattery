/***********************************************************************		
	NGIT Confidential. 
	@File Name   : Transaction.java 
	@Description : This Servlet get the Inverter details
	@Author	     : Sai Krishna Daddala
	@Date        : 06th Feb 2017
******************************************************************/ 
 
package com.ngit.servlets.consumers.servicestore; 
 
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.consumers.products.Order_SMS;
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

import com.instamojo.wrapper.api.OnlinePaymentService;
import net.sf.json.*; 
import net.sf.ezmorph.*; 
  
public class Transaction_Service extends HttpServlet 
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
		baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?(propsMOPConfig.getProperty("baseurldirectory")):"";

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
		
		String[] Browser_URL_Array=Browser_URL.split("/bookbattery/");
		
		String results_FOR = Browser_URL_Array[1];
		//out.println(Browser_URL_Array[0]);
		//out.println(Browser_URL_Array[1]);
		LogLevel.DEBUG(5, new Throwable(), "Browser_URL 1 :" + Browser_URL_Array[0]);
		LogLevel.DEBUG(5, new Throwable(), "Browser_URL 2:" + Browser_URL_Array[1]);
		
		String[] REQ_URL_Array  = results_FOR.split("/");
		LogLevel.DEBUG(5, new Throwable(), "REQ_URL_Array.length :" + REQ_URL_Array.length);
		
	/* ****************************************************************************************\
		* This action is used to get vehicle model. 
		\* *****************************************************************************************/		

		if(REQ_URL_Array[0].equals("transaction_service"))
		{ 
		try
			{
				String strRes=online_transaction_status(req,res,session, Browser_URL_Array[1]);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/online_transaction_service.jsp");
				RD.forward(req, res);
				out.close();
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}
		
		if(REQ_URL_Array[0].equals("order"))
		{ 
		try
			{
				String strRes=online_transaction_status(req,res,session, Browser_URL_Array[1]);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/consumer/online_transaction.jsp");
				RD.forward(req, res);
				out.close();
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}	
	
	}
	
	
	
	public String online_transaction_status(HttpServletRequest req , HttpServletResponse res,HttpSession session, String REQ_URL)
	{
		String strRes="";
		try
		{	
			String Payment_Request_Id= (req.getParameter("id")!=null)?(req.getParameter("id")):"";
			String Payment_Id= (req.getParameter("payment_id")!=null)?(req.getParameter("payment_id")):"";
			String Transaction_Id= (req.getParameter("transaction_id")!=null)?(req.getParameter("transaction_id")):"";
			
			LogLevel.DEBUG(5, new Throwable(), "Payment_Request_Id :" + Payment_Request_Id);
			LogLevel.DEBUG(5, new Throwable(), "Payment_Id :" + Payment_Id);
			LogLevel.DEBUG(5, new Throwable(), "Transaction_Id :" + Transaction_Id);
			
			String payment_mode="";
			//String table_name="battery_order_details";
			String order_status="";
			
			Transaction_Id=Transaction_Id.trim(); 
			if( Transaction_Id.equals("") || Transaction_Id.isEmpty())
			{
				String Order_ID= (req.getParameter("id")!=null)?(req.getParameter("id")):"";
				LogLevel.DEBUG(5, new Throwable(), "Order_ID :" + Order_ID);
				
				if(Order_ID.contains("ORD"))
				{
					Order_ID=Order_ID.trim(); 
					LogLevel.DEBUG(5, new Throwable(), "Order_ID.trim() :" + Order_ID.trim());
					
					//############## Select Tranasaction Details for Battery Orders and Send SMS ##### Start
												
					String Select_Transaction_Status_SQL ="select a.ord_id, a.order_number, a.consumer_name, a.city, a.services_type, a.services_package, a.service_price_mrp, a.service_price_discount, a.veh_name, a.veh_model, a.order_status, a.state, a.product_type,a.product_capacity,a.quantity,a.services_place,b.icon_url from service_order_details a, services_details b where a.order_number='"+Order_ID+"' group by a.order_number ";

					LogLevel.DEBUG(5, new Throwable(), "Select_Transaction_Status_SQL :" + Select_Transaction_Status_SQL);
					
					Hashtable Select_Transaction_Status_HT = qm.getRow(Select_Transaction_Status_SQL);
					LogLevel.DEBUG(5, new Throwable(), "Select_Transaction_Status_HT :" + Select_Transaction_Status_HT);
					
					if(Select_Transaction_Status_HT.isEmpty())
					{
						//Some thing went wrong
						RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/403.jsp");
						RD.forward(req, res);
						out.close();
					}
					else
					{	
						//######## Jsp Response Order Type
						req.setAttribute("OrderType","Battery");
						LogLevel.DEBUG(5, new Throwable(), "OrderType : Battery");
						
						req.setAttribute("Payment_Status","completed");
						LogLevel.DEBUG(5, new Throwable(), "Payment_Status : completed");
						
						req.setAttribute("Payment_Type","COD");
						LogLevel.DEBUG(5, new Throwable(), "Payment_Type : COD");
						//######## Jsp Response Total Details 
						
						req.setAttribute("ServiceOrderDetails",Select_Transaction_Status_HT);
						LogLevel.DEBUG(5, new Throwable(), "Select_Transaction_Status_HT :" + Select_Transaction_Status_HT);
					}
						
				}
				
				else
				{
					//Some thing went wrong
					RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/403.jsp");
					RD.forward(req, res);
					out.close();
				}
				
				return strRes;
			}
			
			strRes=Transaction_Id;
			String TransactionId_Session=(String)session.getAttribute("TransactionId"); 
			LogLevel.DEBUG(5, new Throwable(), "TransactionId_Session :" + TransactionId_Session);
			
			String PaymentOrderDetails_Resp="";
			
			//############## Condition to not repeat SMS and Mail Sending ########### START
			
			String Get_Transaction_Status_SQL ="select status from online_transaction_details where transaction_id='"+Transaction_Id+"' and status='completed'";
			LogLevel.DEBUG(5, new Throwable(), "Get_Transaction_Status_SQL :" + Get_Transaction_Status_SQL);
			
			Hashtable Get_Transaction_Status_HT = qm.getRow(Get_Transaction_Status_SQL);
			LogLevel.DEBUG(5, new Throwable(), "Get_Transaction_Status_HT :" + Get_Transaction_Status_HT);
			
			String SEND_SMS_MAIL="";
			if(Get_Transaction_Status_HT.isEmpty())
			{	
				SEND_SMS_MAIL="Yes";
				
				OnlinePaymentService Create_Payment_Link = new OnlinePaymentService(qm);
				LogLevel.DEBUG(5, new Throwable(), "Create_Payment_Link :" + Create_Payment_Link);
				PaymentOrderDetails_Resp=  Create_Payment_Link.getPaymentOrderDetails(Transaction_Id);
				LogLevel.DEBUG(5, new Throwable(), "PaymentOrderDetails_Resp :" + PaymentOrderDetails_Resp);
			}
			else
			{
				SEND_SMS_MAIL="No";
			}
			
			//############## Condition to not repeat SMS and Mail Sending ########### STOP
			
			
			String Select_Transaction_Status_SQL ="select a.ord_id, a.order_number, a.consumer_name, a.city, a.services_type, a.services_package, a.service_price_mrp, a.service_price_discount, a.veh_name, a.veh_model, a.order_status, a.state, a.product_type,a.quantity,a.product_capacity, b.icon_url from service_order_details a, services_details b where a.payment_transaction_id='"+Transaction_Id+"' group by a.order_number ";

			LogLevel.DEBUG(5, new Throwable(), "Select_Transaction_Status_SQL :" + Select_Transaction_Status_SQL);
			
			Hashtable Select_Transaction_Status_HT = qm.getRow(Select_Transaction_Status_SQL);
			LogLevel.DEBUG(5, new Throwable(), "Select_Transaction_Status_HT :" + Select_Transaction_Status_HT);
			
			//######## Jsp Response Total Details 
			req.setAttribute("ServiceOrderDetails",Select_Transaction_Status_HT);
			LogLevel.DEBUG(5, new Throwable(), "Select_Transaction_Status_HT :" + Select_Transaction_Status_HT);
			
			if(SEND_SMS_MAIL.equals("No"))
			{
				req.setAttribute("Payment_Status","completed");
				LogLevel.DEBUG(5, new Throwable(), "Payment_Status : completed");
				return "Page Re-Loaded";
			}
			else
			{
				// Do Nothing On this Step, Go to Next Step
			}
			
			
			if(PaymentOrderDetails_Resp.equals("completed"))
			{
				//######## Jsp Response Payment_Status
				req.setAttribute("Payment_Status","completed");
				LogLevel.DEBUG(5, new Throwable(), "Payment_Status : completed");
				//######## Jsp Response Payment_Status
				
			
				if(SEND_SMS_MAIL.equals("Yes"))
				{
					
						LogLevel.DEBUG(5, new Throwable(), "payment_mode  near update:" + payment_mode);
						
						LogLevel.DEBUG(5, new Throwable(), "Transaction_Id  out side:" + Transaction_Id);
						
						
						String Get_Paymet_Mode_details= "select a.order_id,b.payment_mode from online_transaction_details a, service_order_details b where a.transaction_id='"+Transaction_Id+"' and a.order_id=b.order_number";
						
						LogLevel.DEBUG(5, new Throwable(), "Get_Paymet_Mode_details" + Get_Paymet_Mode_details);
						
						Hashtable Get_Paymet_Mode_details_HT = qm.getRow(Get_Paymet_Mode_details);
						LogLevel.DEBUG(5, new Throwable(), "Get_Paymet_Mode_details_HT :" + Get_Paymet_Mode_details_HT);
						
						if(Get_Paymet_Mode_details_HT.isEmpty())
						{	
							order_status="a.order_status = 'confirmed'";
						}
						else
						{
							payment_mode=(String)Get_Paymet_Mode_details_HT.get("payment_mode");
							LogLevel.DEBUG(5, new Throwable(), "payment_mode :" + payment_mode);
							
							if (payment_mode.equals("Online Payment After Fitment"))
							{
								LogLevel.DEBUG(5, new Throwable(), "payment_mode inside :" + payment_mode);
								
								order_status="a.order_status = 'Customer Contacted' , a.order_reasons='Payment Completed'";
								
								SEND_SMS_MAIL="No";
							}
							else
							{
								order_status="a.order_status = 'confirmed'";
							}
						}

					
					//############## Update Tranasaction Details for Battery Orders ##### Start
					String Update_Transaction_Status_SQL = "UPDATE service_order_details a, online_transaction_details b SET "+order_status+", a.updated_date = now() WHERE b.transaction_id = '"+Transaction_Id+"' and b.order_id=a.order_number";
					
					int Update_Transaction_Status_Response = qm.executeUpdate(Update_Transaction_Status_SQL);
						
					LogLevel.DEBUG(5, new Throwable(), "Update_Transaction_Status_Response :" + Update_Transaction_Status_Response);
					
					//############## Update Tranasaction Details for Battery Orders ##### End
				}
				
				if(Select_Transaction_Status_HT.isEmpty())
				{
					//Some thing went wrong
					RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/403.jsp");
					RD.forward(req, res);
					out.close();
				}
				else
				{
					String Service_Order_Number=(String)Select_Transaction_Status_HT.get("order_number");
					LogLevel.DEBUG(5, new Throwable(), "Service_Order_Number :" + Service_Order_Number);
					
					if(SEND_SMS_MAIL.equals("Yes"))
					{						

						//######## Send SMS for ORDER
						Order_SMS Send_Order_SMS_Checkout = new Order_SMS(qm);
						String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,Service_Order_Number,"Service","Yes","No","No");
						LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
						//######## Send SMS for ORDER
						
						//######## Send Mail for ORDER
						Order_SMS Send_Order_MAIL_Checkout = new Order_SMS(qm);
						String MAIL_Report=Send_Order_MAIL_Checkout.Send_Order_Mail(req,res,session,Service_Order_Number,"Service","Sir","No","No");
						LogLevel.DEBUG(5, new Throwable(), "MAIL_Report :" + MAIL_Report);
						//######## Send Mail for ORDER
						
						
					}
					
				}
				//############## Select Tranasaction Details for Battery Orders and Send SMS ##### End
			}
			else if(PaymentOrderDetails_Resp.equals("pending"))
			{
				
				String Get_Payment_Link_SQL ="select payment_url from online_transaction_details where transaction_id='"+Transaction_Id+"'";
				LogLevel.DEBUG(5, new Throwable(), "Get_Payment_Link_SQL :" + Get_Payment_Link_SQL);
				
				Hashtable Get_Payment_Link_HT = qm.getRow(Get_Payment_Link_SQL);
				LogLevel.DEBUG(5, new Throwable(), "Get_Payment_Link_HT :" + Get_Payment_Link_HT);
				
				String Payment_Link=(String)Get_Payment_Link_HT.get("payment_url");
				LogLevel.DEBUG(5, new Throwable(), "Payment_Link :" + Payment_Link);
				
				//######## Jsp Response Payment_Status
				req.setAttribute("Payment_Status",PaymentOrderDetails_Resp);
				LogLevel.DEBUG(5, new Throwable(), "Payment_Status : "+PaymentOrderDetails_Resp);
				//######## Jsp Response Payment_Status
				
				//######## Jsp Response Paymnet_Link
				req.setAttribute("Payment_Link",Payment_Link);
				LogLevel.DEBUG(5, new Throwable(), "Payment_Link : "+Payment_Link);
				//######## Jsp Response Payment_Link
			}
			else if(PaymentOrderDetails_Resp.equals(""))
			{
				//Some thing went wrong
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/403.jsp");
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


