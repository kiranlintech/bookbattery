/***********************************************************************		
	NGIT Confidential. 
	@File Name   : Transaction.java 
	@Description : This Servlet get the Inverter details
	@Author	     : Sai Krishna Daddala
	@Date        : 06th Feb 2017
******************************************************************/ 
 
package com.ngit.servlets.consumers.batterystore; 
 
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

import com.instamojo.wrapper.api.OnlinePayment;
import net.sf.json.*; 
import net.sf.ezmorph.*; 
  
public class Transaction extends HttpServlet 
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

		if(REQ_URL_Array[0].equals("transaction"))
		{ 
		try
			{
				String strRes=online_transaction_status(req,res,session, Browser_URL_Array[1]);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/online_transaction.jsp");
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
				String strRes=cod_transaction_status(req,res,session, Browser_URL_Array[1]);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/online_transaction.jsp");
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
			String table_name="battery_order_details";
			String order_status="";
			
			Transaction_Id=Transaction_Id.trim(); 
			if( Transaction_Id.equals("") || Transaction_Id.isEmpty())
			{
				String Order_ID= (req.getParameter("id")!=null)?(req.getParameter("id")):"";
				LogLevel.DEBUG(5, new Throwable(), "Order_ID :" + Order_ID);
				
				if(Order_ID.contains("ORDB"))
				{
					Order_ID=Order_ID.trim(); 
					LogLevel.DEBUG(5, new Throwable(), "Order_ID.trim() :" + Order_ID.trim());
					
					//############## Select Tranasaction Details for Battery Orders and Send SMS ##### Start
												
					String Select_Transaction_Status_Battery_SQL ="select  ord_id, order_number, consumer_name, consumer_mobnumber, consumer_emailid, retailer_name, retailer_mobilnumber, retailer_emailis, city, battery_brand, battery_model, price, bat_type, veh_name, veh_model, witholdbatprice, state, payment_mode, order_type, consumer_address, battery_capacity, quantity, payment_mode_type, delivery_mode, delivery_charge from battery_order_details where order_number='"+Order_ID+"' ";
					LogLevel.DEBUG(5, new Throwable(), "Select_Transaction_Status_Battery_SQL :" + Select_Transaction_Status_Battery_SQL);
					
					Hashtable Select_Transaction_Status_Battery_HT = qm.getRow(Select_Transaction_Status_Battery_SQL);
					LogLevel.DEBUG(5, new Throwable(), "Select_Transaction_Status_Battery_HT :" + Select_Transaction_Status_Battery_HT);
					
					
					table_name="battery_order_details";
					
					LogLevel.DEBUG(5, new Throwable(), "table_name  in side battery:" + table_name);
					
					if(Select_Transaction_Status_Battery_HT.isEmpty())
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
						
						req.setAttribute("BatteryOrderDetails",Select_Transaction_Status_Battery_HT);
						LogLevel.DEBUG(5, new Throwable(), "Select_Transaction_Status_Battery_HT :" + Select_Transaction_Status_Battery_HT);
					}
						
				}
				else if(Order_ID.contains("ORDI"))
				{
							
					String Check_Inverter_Combo_Order_SQL ="select  order_id, order_number, order_agent_comments, consumer_name, consumer_mobnumber, consumer_emailid, retailer_name, retailer_mobile_number, retailer_emailid, inverter_brand, inverter_model, inverter_capacity, price, payment_mode, consumer_address, quantity, payment_mode_type from inverter_order_details where order_number='"+Order_ID+"'";
					LogLevel.DEBUG(5, new Throwable(), "Check_Inverter_Combo_Order_SQL :" + Check_Inverter_Combo_Order_SQL);
					
					Hashtable Check_Inverter_Combo_Order_HT = qm.getRow(Check_Inverter_Combo_Order_SQL);
					LogLevel.DEBUG(5, new Throwable(), "Check_Inverter_Combo_Order_HT :" + Check_Inverter_Combo_Order_HT);
					
					table_name="battery_order_details";
					
					LogLevel.DEBUG(5, new Throwable(), "table_name  in side battery:" + table_name);
					
					if(Check_Inverter_Combo_Order_HT.isEmpty())
					{
						//Some thing went wrong
						RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/403.jsp");
						RD.forward(req, res);
						out.close();
					}
					else
					{
						String Order_Agent_Comments=(String)Check_Inverter_Combo_Order_HT.get("order_agent_comments");
						LogLevel.DEBUG(5, new Throwable(), "Order_Agent_Comments :" + Order_Agent_Comments);
						
						String Inverter_Order_Number=(String)Check_Inverter_Combo_Order_HT.get("order_number");
						LogLevel.DEBUG(5, new Throwable(), "Inverter_Order_Number :" + Inverter_Order_Number);
						
						
						if(Order_Agent_Comments.contains("ORDB"))
						{
							String[] Order_Agent_Comments_Array=Order_Agent_Comments.split("=");
							LogLevel.DEBUG(5, new Throwable(), "Order_Agent_Comments_Array :" + Order_Agent_Comments_Array);

							String Order_Battery_Number = Order_Agent_Comments_Array[0];
							LogLevel.DEBUG(5, new Throwable(), "Order_Battery_Number :" + Order_Battery_Number);
							
							Order_Battery_Number=Order_Battery_Number.trim(); 
							LogLevel.DEBUG(5, new Throwable(), "Order_Battery_Number.trim() :" + Order_Battery_Number.trim());
														
							String Check_Inverter_and_Battery_Combo_Order_SQL ="select ord_id, order_number, consumer_name, consumer_mobnumber, consumer_emailid, retailer_name, retailer_mobilnumber, retailer_emailis, city, battery_brand, battery_model, price, bat_type, veh_name, veh_model, witholdbatprice, state, payment_mode, order_type, consumer_address, battery_capacity, quantity, payment_mode_type from battery_order_details WHERE order_number='"+Order_Battery_Number+"'";
							LogLevel.DEBUG(5, new Throwable(), "Check_Inverter_and_Battery_Combo_Order_SQL :" + Check_Inverter_and_Battery_Combo_Order_SQL);
							
							Hashtable Check_Inverter_and_Battery_Combo_Order_HT = qm.getRow(Check_Inverter_and_Battery_Combo_Order_SQL);
							LogLevel.DEBUG(5, new Throwable(), "Check_Inverter_and_Battery_Combo_Order_HT :" + Check_Inverter_and_Battery_Combo_Order_HT);
							
							
							//######## Jsp Response Order Type
							req.setAttribute("OrderType","Inverter_and_Battery");
							LogLevel.DEBUG(5, new Throwable(), "OrderType : Inverter_and_Battery");
							
							//######## Jsp Response Total Details 
							req.setAttribute("BatteryOrderDetails",Check_Inverter_and_Battery_Combo_Order_HT);
							LogLevel.DEBUG(5, new Throwable(), "BatteryOrderDetails Chombo :" + Check_Inverter_and_Battery_Combo_Order_HT);
						}
						else
						{
							//######## Jsp Response Order Type
							req.setAttribute("OrderType","Inverter");
							LogLevel.DEBUG(5, new Throwable(), "OrderType : Inverter");
						}

						//######## Jsp Response Total Details 
						req.setAttribute("InverterOrderDetails",Check_Inverter_Combo_Order_HT);
						LogLevel.DEBUG(5, new Throwable(), "InverterOrderDetails :" + Check_Inverter_Combo_Order_HT);
						
						req.setAttribute("Payment_Status","completed");
						LogLevel.DEBUG(5, new Throwable(), "Payment_Status : completed");
						
						req.setAttribute("Payment_Type","COD");
						LogLevel.DEBUG(5, new Throwable(), "Payment_Type : COD");
						//######## Jsp Response Total Details 
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
			
			
			
			//############## Condition to not repeat SMS and Mail Sending ########### START
			
			String Get_Transaction_Status_SQL ="select status from online_transaction_details where transaction_id='"+Transaction_Id+"' and status='completed'";
			LogLevel.DEBUG(5, new Throwable(), "Get_Transaction_Status_SQL :" + Get_Transaction_Status_SQL);
			
			Hashtable Get_Transaction_Status_HT = qm.getRow(Get_Transaction_Status_SQL);
			LogLevel.DEBUG(5, new Throwable(), "Get_Transaction_Status_HT :" + Get_Transaction_Status_HT);
			
			String SEND_SMS_MAIL="";
			if(Get_Transaction_Status_HT.isEmpty())
			{	
				SEND_SMS_MAIL="Yes";
			}
			else
			{
				SEND_SMS_MAIL="No";
			}
			
			//############## Condition to not repeat SMS and Mail Sending ########### STOP
			
			
			
			
			OnlinePayment Create_Payment_Link = new OnlinePayment(qm);
			LogLevel.DEBUG(5, new Throwable(), "Create_Payment_Link :" + Create_Payment_Link);
			String PaymentOrderDetails_Resp=  Create_Payment_Link.getPaymentOrderDetails(Transaction_Id);
			LogLevel.DEBUG(5, new Throwable(), "PaymentOrderDetails_Resp :" + PaymentOrderDetails_Resp);
			
			if(PaymentOrderDetails_Resp.equals("completed"))
			{
				//######## Jsp Response Payment_Status
				req.setAttribute("Payment_Status","completed");
				LogLevel.DEBUG(5, new Throwable(), "Payment_Status : completed");
				//######## Jsp Response Payment_Status
				
				if(Transaction_Id.contains("ORDB"))
				{
					if(SEND_SMS_MAIL.equals("Yes"))
					{
						LogLevel.DEBUG(5, new Throwable(), "payment_mode  near update:" + payment_mode);
						
						LogLevel.DEBUG(5, new Throwable(), "Transaction_Id  out side:" + Transaction_Id);
						
						LogLevel.DEBUG(5, new Throwable(), "table_name  out side:" + table_name);
						
						String Get_Paymet_Mode_details= "select a.order_id,b.payment_mode from online_transaction_details a, battery_order_details b where a.transaction_id='"+Transaction_Id+"' and a.order_id=b.order_number";
						
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
						String Update_Transaction_Status_Battery_SQL = "UPDATE battery_order_details a, online_transaction_details b SET "+order_status+", a.updated_date = now() WHERE b.transaction_id = '"+Transaction_Id+"' and b.order_id=a.order_number";
						
						int Update_Transaction_Status_Battery_Response = qm.executeUpdate(Update_Transaction_Status_Battery_SQL);
							
						LogLevel.DEBUG(5, new Throwable(), "Update_Transaction_Status_Battery_Response :" + Update_Transaction_Status_Battery_Response);
						
						//############## Update Tranasaction Details for Battery Orders ##### End
					}
					
					
					
					//############## Select Tranasaction Details for Battery Orders and Send SMS ##### Start
												
					String Select_Transaction_Status_Battery_SQL ="select  a.ord_id, a.order_number, a.consumer_name, a.consumer_mobnumber, a.consumer_emailid, a.retailer_name, a.retailer_mobilnumber, a.retailer_emailis, a.city, a.battery_brand, a.battery_model, a.price, a.bat_type, a.veh_name, a.veh_model, a.witholdbatprice, a.state, a.payment_mode, a.order_type, a.consumer_address, a.battery_capacity, a.quantity, a.payment_mode_type, b.payment_id from battery_order_details a, online_transaction_details b  where b.transaction_id='"+Transaction_Id+"' and b.order_id=a.order_number ";
					LogLevel.DEBUG(5, new Throwable(), "Select_Transaction_Status_Battery_SQL :" + Select_Transaction_Status_Battery_SQL);
					
					Hashtable Select_Transaction_Status_Battery_HT = qm.getRow(Select_Transaction_Status_Battery_SQL);
					LogLevel.DEBUG(5, new Throwable(), "Select_Transaction_Status_Battery_HT :" + Select_Transaction_Status_Battery_HT);
					
					if(Select_Transaction_Status_Battery_HT.isEmpty())
					{
						//Some thing went wrong
					}
					else
					{
						String Battery_Order_Number=(String)Select_Transaction_Status_Battery_HT.get("order_number");
						LogLevel.DEBUG(5, new Throwable(), "Battery_Order_Number :" + Battery_Order_Number);
						
						if(SEND_SMS_MAIL.equals("Yes"))
						{
							//######## Send SMS for ORDER
							Order_SMS Send_Order_SMS_Checkout = new Order_SMS(qm);
							String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,Battery_Order_Number,"Battery","Yes","No","No");
							LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
							//######## Send SMS for ORDER
							
							//######## Send Mail for ORDER
							Order_SMS Send_Order_MAIL_Checkout = new Order_SMS(qm);
							String MAIL_Report=Send_Order_MAIL_Checkout.Send_Order_Mail(req,res,session,Battery_Order_Number,"Battery","Sir","No","No");
							LogLevel.DEBUG(5, new Throwable(), "MAIL_Report :" + MAIL_Report);
							//######## Send Mail for ORDER
						}
						
					}
					//############## Select Tranasaction Details for Battery Orders and Send SMS ##### End
					
					//######## Jsp Response Order Type
					req.setAttribute("OrderType","Battery");
					LogLevel.DEBUG(5, new Throwable(), "OrderType : Battery");
					
					//######## Jsp Response Total Details 
					req.setAttribute("BatteryOrderDetails",Select_Transaction_Status_Battery_HT);
					LogLevel.DEBUG(5, new Throwable(), "Select_Transaction_Status_Battery_HT :" + Select_Transaction_Status_Battery_HT);
					
				}
				else if(Transaction_Id.contains("ORDI"))
				{
							
					String Check_Inverter_Combo_Order_SQL ="select  a.order_id, a.order_number, a.order_agent_comments, a.consumer_name, a.consumer_mobnumber, a.consumer_emailid, a.retailer_name, a.retailer_mobile_number, a.retailer_emailid, a.inverter_brand, a.inverter_model, a.inverter_capacity, a.price, a.payment_mode, a.consumer_address, a.quantity, a.payment_mode_type, b.payment_id from inverter_order_details a, online_transaction_details b  where b.transaction_id='"+Transaction_Id+"' and b.order_id=a.order_number ";
					LogLevel.DEBUG(5, new Throwable(), "Check_Inverter_Combo_Order_SQL :" + Check_Inverter_Combo_Order_SQL);
					
					Hashtable Check_Inverter_Combo_Order_HT = qm.getRow(Check_Inverter_Combo_Order_SQL);
					LogLevel.DEBUG(5, new Throwable(), "Check_Inverter_Combo_Order_HT :" + Check_Inverter_Combo_Order_HT);
					
					if(Check_Inverter_Combo_Order_HT.isEmpty())
					{
						//Some thing went wrong
					}
					else
					{
						String Order_Agent_Comments=(String)Check_Inverter_Combo_Order_HT.get("order_agent_comments");
						LogLevel.DEBUG(5, new Throwable(), "Order_Agent_Comments :" + Order_Agent_Comments);
						
						String Inverter_Order_Number=(String)Check_Inverter_Combo_Order_HT.get("order_number");
						LogLevel.DEBUG(5, new Throwable(), "Inverter_Order_Number :" + Inverter_Order_Number);
						
						if(Order_Agent_Comments.equals("null") || Order_Agent_Comments.equals(null) || Order_Agent_Comments.equals("NULL") || Order_Agent_Comments.equals(""))
						{
							Order_Agent_Comments="";
						}
						else
						{
							//Do Nothing
						}
						
						
						if(Order_Agent_Comments.contains("ORDB"))
						{
							String[] Order_Agent_Comments_Array=Order_Agent_Comments.split("=");
							LogLevel.DEBUG(5, new Throwable(), "Order_Agent_Comments_Array :" + Order_Agent_Comments_Array);

							String Order_Battery_Number = Order_Agent_Comments_Array[0];
							LogLevel.DEBUG(5, new Throwable(), "Order_Battery_Number :" + Order_Battery_Number);
							
							Order_Battery_Number=Order_Battery_Number.trim(); 
							LogLevel.DEBUG(5, new Throwable(), "Order_Battery_Number.trim() :" + Order_Battery_Number.trim());
							
							if(SEND_SMS_MAIL.equals("Yes"))
							{
								//######## Send SMS for ORDER
								Order_SMS Send_Order_SMS_Checkout = new Order_SMS(qm);
								String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,Order_Battery_Number,"Battery","Yes","No","No");
								LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
								//######## Send SMS for ORDER
								
								//######## Send Mail for ORDER
								Order_SMS Send_Order_MAIL_Checkout = new Order_SMS(qm);
								String MAIL_Report=Send_Order_MAIL_Checkout.Send_Order_Mail(req,res,session,Order_Battery_Number,"Battery","Sir","No","No");
								LogLevel.DEBUG(5, new Throwable(), "MAIL_Report :" + MAIL_Report);
								//######## Send Mail for ORDER
								
							String Get_Paymet_Mode_details= "select a.order_id,b.payment_mode from online_transaction_details a, battery_order_details b where a.transaction_id='"+Transaction_Id+"' and a.order_id=b.order_number";
								
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
										order_status="a.order_status = 'Customer Contacted' , a.order_reasons='Payment Completed'";
										
										SEND_SMS_MAIL="No";
									}
									else
									{
										order_status="a.order_status = 'confirmed'";
									}
								}
								
						
							String Update_Transaction_Status_Battery_Chombo_SQL = "UPDATE battery_order_details SET "+order_status+", updated_date = now() WHERE order_number='"+Order_Battery_Number+"'";
							
							int Update_Transaction_Status_Battery_Chombo_Response = qm.executeUpdate(Update_Transaction_Status_Battery_Chombo_SQL);
							
							LogLevel.DEBUG(5, new Throwable(), "Update_Transaction_Status_Battery_Chombo_Response :" + Update_Transaction_Status_Battery_Chombo_Response);
							
							}
							
							
							String Check_Inverter_and_Battery_Combo_Order_SQL ="select ord_id, order_number, consumer_name, consumer_mobnumber, consumer_emailid, retailer_name, retailer_mobilnumber, retailer_emailis, city, battery_brand, battery_model, price, bat_type, veh_name, veh_model, witholdbatprice, state, payment_mode, order_type, consumer_address, battery_capacity, quantity, payment_mode_type from battery_order_details WHERE order_number='"+Order_Battery_Number+"'";
							LogLevel.DEBUG(5, new Throwable(), "Check_Inverter_and_Battery_Combo_Order_SQL :" + Check_Inverter_and_Battery_Combo_Order_SQL);
							
							Hashtable Check_Inverter_and_Battery_Combo_Order_HT = qm.getRow(Check_Inverter_and_Battery_Combo_Order_SQL);
							LogLevel.DEBUG(5, new Throwable(), "Check_Inverter_and_Battery_Combo_Order_HT :" + Check_Inverter_and_Battery_Combo_Order_HT);
							
							
							
							//######## Jsp Response Order Type
							req.setAttribute("OrderType","Inverter_and_Battery");
							LogLevel.DEBUG(5, new Throwable(), "OrderType : Inverter_and_Battery");
							
							//######## Jsp Response Total Details 
							req.setAttribute("BatteryOrderDetails",Check_Inverter_and_Battery_Combo_Order_HT);
							LogLevel.DEBUG(5, new Throwable(), "BatteryOrderDetails Chombo :" + Check_Inverter_and_Battery_Combo_Order_HT);
						}
						else
						{
							//######## Jsp Response Order Type
							req.setAttribute("OrderType","Inverter");
							LogLevel.DEBUG(5, new Throwable(), "OrderType : Inverter");
						}
						
						if(SEND_SMS_MAIL.equals("Yes"))
						{
								String Get_Paymet_Mode_details= "select a.order_id,b.payment_mode from online_transaction_details a, inverter_order_details b where a.transaction_id='"+Transaction_Id+"' and a.order_id=b.order_number";
								
								LogLevel.DEBUG(5, new Throwable(), "Get_Paymet_Mode_details" + Get_Paymet_Mode_details);
								
								Hashtable Get_Paymet_Mode_details_HT = qm.getRow(Get_Paymet_Mode_details);
								LogLevel.DEBUG(5, new Throwable(), "Get_Paymet_Mode_details_HT :" + Get_Paymet_Mode_details_HT);
								
								if(Get_Paymet_Mode_details_HT.isEmpty())
								{	
									order_status="a.order_status = 'confirmed'";
									
									//######## Send SMS for ORDER
									Order_SMS Send_Order_SMS_Checkout = new Order_SMS(qm);
									String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,Inverter_Order_Number,"Inverter","Yes","No","No");
									LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
									//######## Send SMS for ORDER


									//######## Send Mail for ORDER
									Order_SMS Send_Order_MAIL_Checkout = new Order_SMS(qm);
									String MAIL_Report=Send_Order_MAIL_Checkout.Send_Order_Mail(req,res,session,Inverter_Order_Number,"Inverter","Sir","No","No");
									LogLevel.DEBUG(5, new Throwable(), "MAIL_Report :" + MAIL_Report);
									//######## Send Mail for ORDER

									LogLevel.DEBUG(5, new Throwable(), "payment_mode  near update:" + payment_mode);
									
								}
								else
								{
									payment_mode=(String)Get_Paymet_Mode_details_HT.get("payment_mode");
									LogLevel.DEBUG(5, new Throwable(), "payment_mode :" + payment_mode);
									
									if (payment_mode.equals("Online Payment After Fitment"))
									{
										order_status="a.order_status = 'Customer Contacted' , a.order_reasons='Payment Completed'";
										
										SEND_SMS_MAIL="No";
									}
									else
									{
										order_status="a.order_status = 'confirmed'";
										
										//######## Send SMS for ORDER
										Order_SMS Send_Order_SMS_Checkout = new Order_SMS(qm);
										String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,Inverter_Order_Number,"Inverter","Yes","No","No");
										LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
										//######## Send SMS for ORDER


										//######## Send Mail for ORDER
										Order_SMS Send_Order_MAIL_Checkout = new Order_SMS(qm);
										String MAIL_Report=Send_Order_MAIL_Checkout.Send_Order_Mail(req,res,session,Inverter_Order_Number,"Inverter","Sir","No","No");
										LogLevel.DEBUG(5, new Throwable(), "MAIL_Report :" + MAIL_Report);
										//######## Send Mail for ORDER

										LogLevel.DEBUG(5, new Throwable(), "payment_mode  near update:" + payment_mode);
									}
								}
						
							String Update_Transaction_Status_Inverter_Chombo_SQL = "UPDATE inverter_order_details a, online_transaction_details b SET "+order_status+", a.updated_date = now() WHERE b.transaction_id = '"+Transaction_Id+"' and b.order_id=a.order_number";
								
							int Update_Transaction_Status_Inverter_Chombo_Response = qm.executeUpdate(Update_Transaction_Status_Inverter_Chombo_SQL);
						
						
							LogLevel.DEBUG(5, new Throwable(), "Update_Transaction_Status_Inverter_Chombo_Response :" + Update_Transaction_Status_Inverter_Chombo_Response);
						
						}
							
						//######## Jsp Response Total Details 
						req.setAttribute("InverterOrderDetails",Check_Inverter_Combo_Order_HT);
						LogLevel.DEBUG(5, new Throwable(), "InverterOrderDetails :" + Check_Inverter_Combo_Order_HT);
					}
				}
				else
				{
					//Some thing went wrong
					RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/403.jsp");
					RD.forward(req, res);
					out.close();
				}
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

		
	public String cod_transaction_status(HttpServletRequest req , HttpServletResponse res,HttpSession session, String REQ_URL)
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
			
			strRes=Transaction_Id;
			String TransactionId_Session=(String)session.getAttribute("TransactionId"); 
			LogLevel.DEBUG(5, new Throwable(), "TransactionId_Session :" + TransactionId_Session);
			
			OnlinePayment Create_Payment_Link = new OnlinePayment(qm);
			LogLevel.DEBUG(5, new Throwable(), "Create_Payment_Link :" + Create_Payment_Link);
			String PaymentOrderDetails_Resp=  Create_Payment_Link.getPaymentOrderDetails(Transaction_Id);
			LogLevel.DEBUG(5, new Throwable(), "PaymentOrderDetails_Resp :" + PaymentOrderDetails_Resp);
			
			if(PaymentOrderDetails_Resp.equals("completed"))
			{
				if(Transaction_Id.contains("ORDB"))
				{
					//############## Update Tranasaction Details for Battery Orders ##### Start
					String Update_Transaction_Status_Battery_SQL = "UPDATE battery_order_details a, online_transaction_details b SET a.order_status = 'confirmed', a.updated_date = now() WHERE b.transaction_id = '"+Transaction_Id+"' and b.order_id=a.order_number";
					
					int Update_Transaction_Status_Battery_Response = qm.executeUpdate(Update_Transaction_Status_Battery_SQL);
						
					LogLevel.DEBUG(5, new Throwable(), "Update_Transaction_Status_Battery_Response :" + Update_Transaction_Status_Battery_Response);
					
					//############## Update Tranasaction Details for Battery Orders ##### End
					
					
					
					
					//############## Select Tranasaction Details for Battery Orders and Send SMS ##### Start
												
					String Select_Transaction_Status_Battery_SQL ="select  a.ord_id, a.order_number, a.consumer_name, a.consumer_mobnumber, a.consumer_emailid, a.retailer_name, a.retailer_mobilnumber, a.retailer_emailis, a.city, a.battery_brand, a.battery_model, a.price, a.bat_type, a.veh_name, a.veh_model, a.witholdbatprice, a.state, a.payment_mode, a.order_type, a.consumer_address, a.battery_capacity, a.quantity, a.payment_mode_type, b.payment_id from battery_order_details a, online_transaction_details b  where b.transaction_id='"+Transaction_Id+"' and b.order_id=a.order_number ";
					LogLevel.DEBUG(5, new Throwable(), "Select_Transaction_Status_Battery_SQL :" + Select_Transaction_Status_Battery_SQL);
					
					Hashtable Select_Transaction_Status_Battery_HT = qm.getRow(Select_Transaction_Status_Battery_SQL);
					LogLevel.DEBUG(5, new Throwable(), "Select_Transaction_Status_Battery_HT :" + Select_Transaction_Status_Battery_HT);
					
					if(Select_Transaction_Status_Battery_HT.isEmpty())
					{
						//Some thing went wrong
					}
					else
					{
						String Battery_Order_Number=(String)Select_Transaction_Status_Battery_HT.get("order_number");
						LogLevel.DEBUG(5, new Throwable(), "Battery_Order_Number :" + Battery_Order_Number);
						
						//######## Send SMS for ORDER
						Order_SMS Send_Order_SMS_Checkout = new Order_SMS(qm);
						String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,Battery_Order_Number,"Battery","Yes","No","No");
						LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
						//######## Send SMS for ORDER
						
					}
					//############## Select Tranasaction Details for Battery Orders and Send SMS ##### End
					
					//######## Jsp Response Order Type
					req.setAttribute("OrderType","Battery");
					LogLevel.DEBUG(5, new Throwable(), "OrderType : Battery");
					
					//######## Jsp Response Total Details 
					req.setAttribute("BatteryOrderDetails",Select_Transaction_Status_Battery_HT);
					LogLevel.DEBUG(5, new Throwable(), "Select_Transaction_Status_Battery_HT :" + Select_Transaction_Status_Battery_HT);
					
				}
				else if(Transaction_Id.contains("ORDI"))
				{
							
					String Check_Inverter_Combo_Order_SQL ="select  a.order_id, a.order_number, a.consumer_name, a.consumer_mobnumber, a.consumer_emailid, a.retailer_name, a.retailer_mobile_number, a.retailer_emailid, a.inverter_brand, a.inverter_model, a.inverter_capacity, a.price, a.payment_mode, a.consumer_address, a.quantity, a.payment_mode_type, b.payment_id from inverter_order_details a, online_transaction_details b  where b.transaction_id='"+Transaction_Id+"' and b.order_id=a.order_number ";
					LogLevel.DEBUG(5, new Throwable(), "Check_Inverter_Combo_Order_SQL :" + Check_Inverter_Combo_Order_SQL);
					
					Hashtable Check_Inverter_Combo_Order_HT = qm.getRow(Check_Inverter_Combo_Order_SQL);
					LogLevel.DEBUG(5, new Throwable(), "Check_Inverter_Combo_Order_HT :" + Check_Inverter_Combo_Order_HT);
					
					if(Check_Inverter_Combo_Order_HT.isEmpty())
					{
						//Some thing went wrong
					}
					else
					{
						String Order_Agent_Comments=(String)Check_Inverter_Combo_Order_HT.get("order_agent_comments");
						LogLevel.DEBUG(5, new Throwable(), "Order_Agent_Comments :" + Order_Agent_Comments);
						
						String Inverter_Order_Number=(String)Check_Inverter_Combo_Order_HT.get("order_number");
						LogLevel.DEBUG(5, new Throwable(), "Inverter_Order_Number :" + Inverter_Order_Number);
						
						if(Order_Agent_Comments.contains("ORDB"))
						{
							String[] Order_Agent_Comments_Array=Order_Agent_Comments.split("|");

							String Order_Battery_Number = Order_Agent_Comments_Array[0];
							Order_Battery_Number=Order_Battery_Number.trim(); 
							
							//######## Send SMS for ORDER
							Order_SMS Send_Order_SMS_Checkout = new Order_SMS(qm);
							String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,Order_Battery_Number,"Battery","Yes","No","No");
							LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
							//######## Send SMS for ORDER
							
							String Update_Transaction_Status_Battery_Chombo_SQL = "UPDATE battery_order_details SET order_status = 'confirmed', updated_date = now() WHERE order_number='"+Order_Battery_Number+"'";
							
							int Update_Transaction_Status_Battery_Chombo_Response = qm.executeUpdate(Update_Transaction_Status_Battery_Chombo_SQL);
							
							LogLevel.DEBUG(5, new Throwable(), "Update_Transaction_Status_Battery_Chombo_Response :" + Update_Transaction_Status_Battery_Chombo_Response);
							
							
							
							String Check_Inverter_and_Battery_Combo_Order_SQL ="select ord_id, order_number, consumer_name, consumer_mobnumber, consumer_emailid, retailer_name, retailer_mobilnumber, retailer_emailis, city, battery_brand, battery_model, price, bat_type, veh_name, veh_model, witholdbatprice, state, payment_mode, order_type, consumer_address, battery_capacity, quantity, payment_mode_type from battery_order_details WHERE order_number='"+Order_Battery_Number+"'";
							LogLevel.DEBUG(5, new Throwable(), "Check_Inverter_and_Battery_Combo_Order_SQL :" + Check_Inverter_and_Battery_Combo_Order_SQL);
							
							Hashtable Check_Inverter_and_Battery_Combo_Order_HT = qm.getRow(Check_Inverter_and_Battery_Combo_Order_SQL);
							LogLevel.DEBUG(5, new Throwable(), "Check_Inverter_and_Battery_Combo_Order_HT :" + Check_Inverter_and_Battery_Combo_Order_HT);
							
							
							
							//######## Jsp Response Order Type
							req.setAttribute("OrderType","Inverter_and_Battery");
							LogLevel.DEBUG(5, new Throwable(), "OrderType : Inverter_and_Battery");
							
							//######## Jsp Response Total Details 
							req.setAttribute("BatteryOrderDetails",Check_Inverter_and_Battery_Combo_Order_HT);
							LogLevel.DEBUG(5, new Throwable(), "BatteryOrderDetails Chombo :" + Check_Inverter_and_Battery_Combo_Order_HT);
						}
						else
						{
							//######## Jsp Response Order Type
							req.setAttribute("OrderType","Inverter");
							LogLevel.DEBUG(5, new Throwable(), "OrderType : Inverter");
						}
						
						//######## Send SMS for ORDER
						Order_SMS Send_Order_SMS_Checkout = new Order_SMS(qm);
						String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,Inverter_Order_Number,"Inverter","Yes","No","No");
						LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
						//######## Send SMS for ORDER
						
						String Update_Transaction_Status_Inverter_Chombo_SQL = "UPDATE inverter_order_details SET order_status = 'confirmed', updated_date = now() WHERE order_number='"+Inverter_Order_Number+"'";
							
						int Update_Transaction_Status_Inverter_Chombo_Response = qm.executeUpdate(Update_Transaction_Status_Inverter_Chombo_SQL);
						
						LogLevel.DEBUG(5, new Throwable(), "Update_Transaction_Status_Inverter_Chombo_Response :" + Update_Transaction_Status_Inverter_Chombo_Response);
							
						//######## Jsp Response Total Details 
						req.setAttribute("InverterOrderDetails",Check_Inverter_Combo_Order_HT);
						LogLevel.DEBUG(5, new Throwable(), "InverterOrderDetails :" + Check_Inverter_Combo_Order_HT);
					}
				}
				else
				{
					//Some thing went wrong
				}
			}
			else
			{
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


