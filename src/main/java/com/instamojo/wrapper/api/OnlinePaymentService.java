/***********************************************************************		
	NGIT Confidential. 
	@File Name   : OnlinePaymentService.java 
	@Description : This Servlet is used for Generation of Payment URL and Tranasaction Status
	@Author	     : Bharath Kumar 
	@Date        : 24rd Aug 2018
******************************************************************/ 
 
package com.instamojo.wrapper.api;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.qrymgr.QueryManager;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.URL;
import com.instamojo.wrapper.exception.ConnectionException;
import com.instamojo.wrapper.exception.InvalidPaymentOrderException;
import com.instamojo.wrapper.exception.InvalidRefundException;
import com.instamojo.wrapper.model.PaymentOrder;
import com.instamojo.wrapper.model.PaymentOrderFilter;
import com.instamojo.wrapper.model.Refund;
import com.instamojo.wrapper.response.CreatePaymentOrderResponse;
import com.instamojo.wrapper.response.CreateRefundResponse;
import com.instamojo.wrapper.response.PaymentOrderDetailsResponse;
import com.instamojo.wrapper.response.PaymentOrderListResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.ngit.javabean.mail.*;
import com.ngit.javabean.sendsms.SendSMS;
import java.util.Properties;
import java.util.Vector;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.io.*;
import java.net.URLDecoder;  
import java.net.URLEncoder;
import java.util.Random;
import java.security.SecureRandom;
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


import net.sf.json.*; 
import net.sf.ezmorph.*; 

public class OnlinePaymentService extends HttpServlet 
{
		
    QueryManager qmgr;
    private ServletContext context; 
    String baseURL;
	
    public OnlinePaymentService(QueryManager qm) 
	{
        qmgr=qm;
    }
    
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
			 baseURL = (propsMOPConfig.getProperty("baseURL")!=null)?propsMOPConfig.getProperty("baseURL"):"";
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
	
	String URL=baseURL+"/bookbattery/transaction/";
    
	private static final Logger LOGGER = Logger.getLogger(OnlinePaymentService.class.getName());
	//######### This method is used to get cookies Of State and City
	public String getPaymentLink(HttpServletRequest req, HttpServletResponse res, HttpSession session, String UserName,String UserEmail,String UserMobileNumber,String Order_Price,String TransactionId)
	{
		String result="";
		String Order_ID=TransactionId;
		
		PaymentOrder order = new PaymentOrder();
		
		/*following code is for generating the random number */
		Random Generator_Trans_ID_Ran = new Random();   
		Generator_Trans_ID_Ran.setSeed(System.currentTimeMillis());
		LogLevel.DEBUG(5, new Throwable(), "Generator_Trans_ID_Ran :" + Generator_Trans_ID_Ran);
		int Num = Generator_Trans_ID_Ran.nextInt(900) + 100;
		if (Num < 1000 || Num > 9999)
		{   
			Num = Generator_Trans_ID_Ran.nextInt(900) + 100;
			if (Num < 1000 || Num > 9999)
			{   
			}   
		} 
		String Trans_Random = Integer.toString(Num);
		TransactionId=TransactionId+Trans_Random;
		TransactionId=TransactionId.trim();
		
		
		double Order_Price_Double = Double.parseDouble(Order_Price);
		LogLevel.DEBUG(5, new Throwable(), "Order_Price_Double :" + Order_Price_Double);
		LogLevel.DEBUG(5, new Throwable(), "TransactionId :" + TransactionId);
		order.setName(UserName);
		order.setEmail(UserEmail);
		order.setPhone(UserMobileNumber);
		order.setCurrency("INR");
		order.setAmount(Order_Price_Double);
		order.setDescription("Payment for Order No:" + Order_ID);
		// gets the reference to the instamojo api Production Payment API
		//**/
		
		order.setRedirectUrl("https://www.bookbattery.com/bookbattery/transaction_service/");
		order.setWebhookUrl("https://www.bookbattery.com/bookbattery/transaction_service/");
		
		
		// gets the reference to the instamojo api Test Payment API
		//order.setRedirectUrl("https://www.bookbattery.com/bookbattery/transaction/");
		//order.setWebhookUrl("https://www.bookbattery.com/bookbattery/transaction/");
		
		//order.setRedirectUrl(URL);
		//order.setWebhookUrl(URL);
		
		
		//order.setRedirectUrl("https://www.bookbattery.com/bookbattery/transaction_service/");
		//order.setWebhookUrl("https://www.bookbattery.com/bookbattery/transaction_service/");
		
		//order.setRedirectUrl("http://localhost:8080/bookbattery/transaction_service/");
		//order.setWebhookUrl("http://localhost:8080/bookbattery/transaction_service/");

		//order.setRedirectUrl("https://www.bookbattery.com/bookbattery/transaction_service/");
		//order.setWebhookUrl("https://www.bookbattery.com/bookbattery/transaction_service/");
		
		order.setTransactionId(TransactionId);
		
		Instamojo api = null;
	
		try {
				// gets the reference to the instamojo api Production Payment API
				
				api = InstamojoImpl.getApi("LR2Z20z9sF8ByoyW6wa1xueXbEPzIuKjjRf3TStj", "cjnMQOdWRpVhwFBfCShyrHJhjlhB3JHipWF3ddBKXzwRdm0V5p7lK91ZbZED9kCLcKEdiYsZvNKUs0e3NcQWc7FURXlabqlsruZ6WU3ZnlBPyWMMSo1NZ6vhDmBFHtDO", "https://api.instamojo.com/v2/", "https://www.instamojo.com/oauth2/token/");
								
				// gets the reference to the instamojo api Test Payment API
				
				//api = InstamojoImpl.getApi("Fh4GautvgzJXBwpICi9Tm915tWvqY86MYvmI3VpL", "EWELjqzYuTFbJ5LBViT5ionelv61XwBOqE8rKH2hBocbUP73MTZTdstl3OnHw2m9H02SOU1QhSsDHaUjBI7yMZGvlLfaQBK1rBcweRB7aB4aM9Es7a1wLzMU6GkMm5HI", "https://test.instamojo.com/v2/", "https://test.instamojo.com/oauth2/token/");
				
			} 
			catch (ConnectionException e) {
				LOGGER.log(Level.SEVERE, e.toString(), e);
			}

		boolean isOrderValid = order.validate();
		LogLevel.DEBUG(5, new Throwable(), "isOrderValid :" + isOrderValid);
		if (isOrderValid) {
			try {
				
				
				session.setAttribute("PaymentStatus"+TransactionId,"Inprocess");
				
				String Get_Transaction_Details_Val_SQL ="select payment_url from online_transaction_details where transaction_id='"+TransactionId+"' ";
				LogLevel.DEBUG(5, new Throwable(), "Get_Transaction_Details_Val_SQL :" + Get_Transaction_Details_Val_SQL);
				
				Hashtable Get_Transaction_Details_Val_HT = qmgr.getRow(Get_Transaction_Details_Val_SQL);
				LogLevel.DEBUG(5, new Throwable(), "Get_Transaction_Details_Val_HT :" + Get_Transaction_Details_Val_HT);
				
				if(Get_Transaction_Details_Val_HT.isEmpty())
				{
					CreatePaymentOrderResponse createPaymentOrderResponse = api.createNewPaymentOrder(order);
					LogLevel.DEBUG(5, new Throwable(), "createPaymentOrderResponse :" + createPaymentOrderResponse);
					
					
					// print the status of the payment order.
					//System.out.println(createPaymentOrderResponse.getPaymentOrder().getResourceUri());
					//System.out.println(createPaymentOrderResponse.getJsonResponse());
					//System.out.println(createPaymentOrderResponse.getPaymentOrder());
					// System.out.println("<br> id: "+ order_res.getString("id"));
					// System.out.println("<br> payment_options: "+ payment_options.getString("payment_url"));
					
					// Create_Payment_Link_Resp= Create_Payment_Link_Resp.replaceAll("CreatePaymentOrderResponse", "");
					// JSONObject json = JSONObject.fromObject(Create_Payment_Link_Resp);

					// JSONObject order_res = json.getJSONObject("order");
					// JSONObject payment_options = json.getJSONObject("payment_options");
					
					//LogLevel.DEBUG(5, new Throwable(), " id: "+ order_res.getString("id"));
					//LogLevel.DEBUG(5, new Throwable(), " payment_options: "+ payment_options.getString("payment_url"));
					
					JSONObject json = (JSONObject) JSONSerializer.toJSON(createPaymentOrderResponse.getJsonResponse());        
					JSONObject order_res = json.getJSONObject("order");
					JSONObject payment_options = json.getJSONObject("payment_options");
					
					String trans_payment_request_id=order_res.getString("id");
					String trans_transaction_id=order_res.getString("transaction_id");
					String trans_status=order_res.getString("status");
					String trans_currency=order_res.getString("currency");
					String trans_amount=order_res.getString("amount");
					String trans_name=order_res.getString("name");
					String trans_email=order_res.getString("email");
					String trans_phone=order_res.getString("phone");
					String trans_description=order_res.getString("description");
					String trans_redirect_url=order_res.getString("redirect_url");
					String trans_webhook_url=order_res.getString("webhook_url");
					String trans_created_at=order_res.getString("created_at");
					String trans_resource_uri=order_res.getString("resource_uri");
					
					String trans_payment_url=payment_options.getString("payment_url");
					
					String Online_Tranasaction_SQL= "insert into online_transaction_details(slno_id,order_id,transaction_id,payment_request_id,status,currency,amount,name,email,phone,description,redirect_url,webhook_url,created_at,resource_uri,payment_url,created_date,fees,mac,payment_id)values(NULL,'"+Order_ID+"','"+TransactionId+"','"+trans_payment_request_id+"','"+trans_status+"','"+trans_currency+"','"+trans_amount+"','"+trans_name+"','"+trans_email+"','"+trans_phone+"','"+trans_description+"','"+trans_redirect_url+"','"+trans_webhook_url+"','"+trans_created_at+"','"+trans_resource_uri+"','"+trans_payment_url+"',now(),'','','')";
					LogLevel.DEBUG(5, new Throwable(), "Online_Tranasaction_SQL :" + Online_Tranasaction_SQL);
					
					//#### To Update Payment Status In Order Table  ### START
					String Order_to_DB_SQL = "update service_order_details set payment_status='"+trans_status+"', payment_link='"+trans_payment_url+"', payment_transaction_id='"+TransactionId+"' where order_number='"+Order_ID+"'";
					
					LogLevel.DEBUG(5, new Throwable(), "Order_to_DB_SQL :" + Order_to_DB_SQL);
					
					int Order_to_DB_Response = qmgr.executeUpdate(Order_to_DB_SQL);
					LogLevel.DEBUG(5, new Throwable(), "Order_to_DB_Response :" + Order_to_DB_Response);
					
					//#### To Update Payment Status In Order Table  ### STOP
					
					int Online_Tranasaction_Response = qmgr.executeUpdate(Online_Tranasaction_SQL);
					LogLevel.DEBUG(5, new Throwable(), "Online_Tranasaction_Response :" + Online_Tranasaction_Response);
					
					if(Online_Tranasaction_Response <0)
					{
						return "ERROR | - Creating Tranasaction Log Failed";
					}

					return trans_payment_url; 
				}
				else
				{
					String Payment_Url=(String)Get_Transaction_Details_Val_HT.get("payment_url");
					return Payment_Url; 
				}
				
			} catch (InvalidPaymentOrderException e) {
				LOGGER.log(Level.SEVERE, e.toString(), e);

				if (order.isTransactionIdInvalid()) {
					System.out.println("Transaction id is invalid. This is mostly due to duplicate transaction id.");
					LogLevel.DEBUG(5, new Throwable(), "Error :" + "Transaction id is invalid. This is mostly due to duplicate transaction id.");
					result="ERROR | - Transaction id is invalid. This is mostly due to duplicate transaction id.";
				}
				if (order.isCurrencyInvalid()) {
					System.out.println("Currency is invalid.");
					LogLevel.DEBUG(5, new Throwable(), "Error :" + "Currency is invalid.");
					result="ERROR | - Currency is invalid.";
				}
				
			}catch (ConnectionException e) {
				LOGGER.log(Level.SEVERE, e.toString(), e);
				result="ERROR | -";
			}
		} else {
			// inform validation errors to the user.
			if (order.isTransactionIdInvalid()) {
				result="ERROR | - Transaction id is invalid.";
			}
			if (order.isAmountInvalid()) {
				result="ERROR | - Amount can not be less than 9.00.";
			}
			if (order.isCurrencyInvalid()) {
				result="ERROR | - Please provide the currency.";
			}
			if (order.isDescriptionInvalid()) {
				result="ERROR | - Description can not be greater than 255 characters.";
			}
			if (order.isEmailInvalid()) {
				result="ERROR | - Please provide valid Email Address.";
			}
			if (order.isNameInvalid()) {
				result="ERROR | - Name can not be greater than 100 characters.";
			}
			if (order.isPhoneInvalid()) {
				result="ERROR | - Phone is invalid.";
			}
			if (order.isRedirectUrlInvalid()) {
				result="ERROR | - Please provide valid Redirect url.";
			}
			if (order.isWebhookInvalid()) {
				result="ERROR | - Provide a valid webhook url";
            }
		}
		return result;
	}

	public String getPaymentOrderDetails(String TransactionId)
	{
		String result="";
		LogLevel.DEBUG(5, new Throwable(), "TransactionId :" + TransactionId);
		TransactionId=TransactionId.trim();
		try {
			
			// gets the reference to the instamojo api Production Payment API
			
			
			Instamojo api = InstamojoImpl.getApi("LR2Z20z9sF8ByoyW6wa1xueXbEPzIuKjjRf3TStj", "cjnMQOdWRpVhwFBfCShyrHJhjlhB3JHipWF3ddBKXzwRdm0V5p7lK91ZbZED9kCLcKEdiYsZvNKUs0e3NcQWc7FURXlabqlsruZ6WU3ZnlBPyWMMSo1NZ6vhDmBFHtDO", "https://api.instamojo.com/v2/", "https://www.instamojo.com/oauth2/token/");
			
			// gets the reference to the instamojo api Test Payment API
			
			//Instamojo api = InstamojoImpl.getApi("Fh4GautvgzJXBwpICi9Tm915tWvqY86MYvmI3VpL", "EWELjqzYuTFbJ5LBViT5ionelv61XwBOqE8rKH2hBocbUP73MTZTdstl3OnHw2m9H02SOU1QhSsDHaUjBI7yMZGvlLfaQBK1rBcweRB7aB4aM9Es7a1wLzMU6GkMm5HI", "https://test.instamojo.com/v2/", "https://test.instamojo.com/oauth2/token/");
			
			
			String Get_Transaction_Status_SQL ="select status from online_transaction_details where transaction_id='"+TransactionId+"' and status='completed'";
			LogLevel.DEBUG(5, new Throwable(), "Get_Transaction_Status_SQL :" + Get_Transaction_Status_SQL);
			
			Hashtable Get_Transaction_Status_HT = qmgr.getRow(Get_Transaction_Status_SQL);
			LogLevel.DEBUG(5, new Throwable(), "Get_Transaction_Status_HT :" + Get_Transaction_Status_HT);
			
			if(Get_Transaction_Status_HT.isEmpty())
			{
				
				PaymentOrderDetailsResponse paymentOrderDetailsResponse = api.getPaymentOrderDetailsByTransactionId(TransactionId);
				LogLevel.DEBUG(5, new Throwable(), "paymentOrderDetailsResponse :" + paymentOrderDetailsResponse);
				if (paymentOrderDetailsResponse.getId() != null) 
				{
					// print the status of the payment order.
					System.out.println(paymentOrderDetailsResponse.getStatus());
					LogLevel.DEBUG(5, new Throwable(), "paymentOrderDetailsResponse :" + paymentOrderDetailsResponse.getStatus());
					LogLevel.DEBUG(5, new Throwable(), "paymentOrderDetailsResponse :" + paymentOrderDetailsResponse.getJsonResponse());
					
					JSONObject json = (JSONObject) JSONSerializer.toJSON(paymentOrderDetailsResponse.getJsonResponse());        
					//JSONObject order_res = json.getJSONObject("order");
					//JSONObject payments = json.getJSONObject("payments");

					String payment_id ="NA";
					if (paymentOrderDetailsResponse.getStatus().equals("completed"))
					{
						JSONArray payments_array = json.getJSONArray("payments");
						JSONObject payment_obj = payments_array.getJSONObject(0);
						payment_id = payment_obj.getString("id");
						LogLevel.DEBUG(5, new Throwable(), "payment_id :" + payment_id);
						LogLevel.DEBUG(5, new Throwable(), "Payments id  :" + json.getString("payments"));
					}

								
					String payment_status = json.getString("status");
					LogLevel.DEBUG(5, new Throwable(), "payment_status :" + payment_status);

					String Update_Transaction_Status_SQL = "UPDATE online_transaction_details SET status = '"+payment_status+"', payment_id = '"+payment_id+"', updated_date = now() WHERE transaction_id = '"+TransactionId+"'";
					
					//#### To Update Payment Status In Order Table  ### START
					String Order_to_DB_SQL = "update service_order_details set payment_status='"+payment_status+"' where payment_transaction_id='"+TransactionId+"'";
					
					LogLevel.DEBUG(5, new Throwable(), "Order_to_DB_SQL :" + Order_to_DB_SQL);
				
					int Order_to_DB_Response = qmgr.executeUpdate(Order_to_DB_SQL);
					LogLevel.DEBUG(5, new Throwable(), "Order_to_DB_Response :" + Order_to_DB_Response);
					//#### To Update Payment Status In Order Table  ### STOP
					
					
					int Update_Transaction_Status_Response = qmgr.executeUpdate(Update_Transaction_Status_SQL);
					if(Update_Transaction_Status_Response <0)
					{
						return "ERROR | - Update Tranasaction Log Failed";
					}
					//session.setAttribute("PaymentStatus"+TransactionId,payment_status);
					return payment_status; 
				}
				else
				{
					System.out.println("Please enter valid order id.");
					result="Please enter valid order id.";
					LogLevel.DEBUG(5, new Throwable(), "Error :" + "Please enter valid order id.");
				}
			}
			else
			{
				String Payment_Status=(String)Get_Transaction_Status_HT.get("status");
				//session.setAttribute("PaymentStatus"+TransactionId,Payment_Status);
				return Payment_Status; 
			}

		} catch (ConnectionException e) {
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
		
		return result;
	}
}//end of Class
