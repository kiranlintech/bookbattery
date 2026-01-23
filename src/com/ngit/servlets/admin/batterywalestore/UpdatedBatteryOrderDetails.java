/***********************************************************************		
	NGIT Confidential. 
	@File Name   : CancelOrderDetails.java
	@Description : This Servlet is used to allow the operator to Login
	@Date        : 12th November 2019
******************************************************************/ 
package com.ngit.servlets.admin.batterywalestore; 

import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.admin.batterywalestore.GenerateStoreInvoice;
import com.ngit.javabean.mail.*;
import com.ngit.javabean.sendsms.SendSMS;
import com.ngit.javabean.consumers.products.Order_Store_SMS;
import com.ngit.javabean.consumers.products.CompareTrans;
import com.instamojo.wrapper.api.OnlinePayment;
import org.json.JSONObject;
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
import java.util.Random;
import java.util.*;
import java.security.SecureRandom;
import java.net.URLDecoder;  
import java.net.URLEncoder; 

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import net.sf.json.*; 
import net.sf.ezmorph.*; 
 
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import javax.mail.*;
import javax.activation.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.HttpURLConnection;

/*
 * @author Prasanna Kumari
 */
/* This class is to Admin Login. This class initializes the necessary connection pools and perform the transactions on the pools. */
public class UpdatedBatteryOrderDetails extends HttpServlet 
{
 	private ServletContext context; 
	QueryManager qm;
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
		String struserName=(String)session.getAttribute("sesStoreOperatorName"); 
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
		String strWhatToDo        = (req.getParameter("hidWhatToDo")!=null)?req.getParameter("hidWhatToDo"):""; 
		ServletOutputStream out=res.getOutputStream();

		String strIpAddress =(propsMOPConfig.getProperty("TRANSMITTER_IP_ADDR")!=null)?(propsMOPConfig.getProperty("TRANSMITTER_IP_ADDR")):"";
		String strPort=(propsMOPConfig.getProperty("TRANSMITTER_IP_PORT")!=null)?(propsMOPConfig.getProperty("TRANSMITTER_IP_PORT")):"9910";
		String SMSFromAddress=(propsMOPConfig.getProperty("SMSFromAddress")!=null)?(propsMOPConfig.getProperty("SMSFromAddress")):"";
		String FromEmailId=(propsMOPConfig.getProperty("SupportEmailId")!=null)?(propsMOPConfig.getProperty("SupportEmailId")):"";
		String strsuppemaild=(propsMOPConfig.getProperty("suppemaild")!=null)?(propsMOPConfig.getProperty("suppemaild")):"";
		String domainname = (propsMOPConfig.getProperty("domainname")!=null)?(propsMOPConfig.getProperty("domainname")).trim():"bookbattery.com";
		String baseUrl =  propsMOPConfig.getProperty("publicUrl");
		String strsuppmobnumber =  propsMOPConfig.getProperty("suppmobnumber");
		String strCMSSERVERIP	= (propsMOPConfig.getProperty("CMS_SERVER_IP")!=null)?(propsMOPConfig.getProperty("CMS_SERVER_IP")):"";
		
		/*This methos is used to validate adminlogin details*/
		if(strWhatToDo.equalsIgnoreCase("updatestatus"))
		{
			
			try
			{
				String strRes=updatestatus(req,res,session,domainname,strIpAddress,strPort,FromEmailId,strsuppemaild,SMSFromAddress,strsuppmobnumber);
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
		* This action is used to get battery brands.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("generateorderinvoice"))
		{
			try
			{
				String strRes=generateorderinvoice(req,res,session);
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
		* This action is used to get battery brands.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("vieworderinvoice"))
		{
			try
			{
				String strRes=vieworderinvoice(req,res,session);
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
		* This action is used to get battery brands.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("sendinvoice"))
		{
			try
			{
				String strRes=sendinvoice(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);
				out.close();
			}
			catch (Exception e)
			{										
			LogLevel.ERROR(1, e, "Error :" + e);
			}	
	     }
	}//End of dopost
	
	/* **************************************************************************************************************************************\
	* This method is to fetch battery details details.
	* @strSqlQry : carries the query to select battery details from battery_details table.
	* @strSqlQry1 : Query has multiple pages to select battery details from battery_details table.
	\* **************************************************************************************************************************************/
	public String updatestatus(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort, String FromEmailId,String strsuppemaild,String SMSFromAddress, String strsuppmobnumber)
	{
		String strRes="";
 		String TableName="";
 	
		
		int reslt_new=0;
		
		
		try
		{	
		
			SendSMS sendsms = new SendSMS(qm);
			
			
			String strordernumber= (req.getParameter("ordno")!=null)?(req.getParameter("ordno")):"";
			LogLevel.DEBUG(5,new Throwable(),"strordernumber:"+strordernumber );
			
			String strorderreasons= (req.getParameter("orderreasons")!=null)?(req.getParameter("orderreasons")):"";
			LogLevel.DEBUG(5,new Throwable(),"strorderreasons:"+strorderreasons );
			
			String strpaymentmode= (req.getParameter("paymentmode_installed")!=null)?(req.getParameter("paymentmode_installed")):"";
			LogLevel.DEBUG(5,new Throwable(),"strpaymentmode:"+strpaymentmode );
			
			String strorderremarks= (req.getParameter("order_remarks")!=null)?(req.getParameter("order_remarks")):"";
			LogLevel.DEBUG(5,new Throwable(),"strorderremarks:"+strorderremarks );
			
			String strcustomerstats= (req.getParameter("customer_stats")!=null)?(req.getParameter("customer_stats")):"";
			LogLevel.DEBUG(5,new Throwable(),"strcustomerstats:"+strcustomerstats );
			
			String strpostponeddate= (req.getParameter("postponeddate")!=null)?(req.getParameter("postponeddate")):"";
			LogLevel.DEBUG(5,new Throwable(),"strpostponeddate:"+strpostponeddate );
			
			String order_type= (req.getParameter("order_type")!=null)?(req.getParameter("order_type")):"";
			LogLevel.DEBUG(5,new Throwable(),"order_type:"+order_type );
			
			String lastChar = strordernumber.substring(strordernumber.length() - 1);
			LogLevel.DEBUG(5,new Throwable(),"lastChar:"+lastChar );
			ServletOutputStream out=res.getOutputStream();
			
			
			String strpostponedate="";
			String OrderDetails_SQL="";
			String OT="";
			String customerpaid="";
			
			if(strorderreasons.equals("postponed") || strorderreasons.equals("Postponed"))
			{
				SimpleDateFormat sdfDate=new SimpleDateFormat("dd-MM-yyyy");
				Date txtpostponedate=sdfDate.parse(strpostponeddate);

				SimpleDateFormat sdfString=new SimpleDateFormat("yyyy-MM-dd");
				strpostponedate=sdfString.format(txtpostponedate); 
				LogLevel.DEBUG(5, new Throwable(), "strpostponedate :"+ strpostponedate);
				
			}			
			
			if(lastChar.equals("S")) {
				
				TableName = "servicestore_order_details";
				OrderDetails_SQL = "select ord_id,order_number,customer_name,customer_mobnumber,customer_emailid,price,discountprice,order_status,order_reasons,order_agent_comments,CAST(installed_date AS CHAR) as installed_date,CAST(updated_date AS CHAR) as updated_date,CAST(postponed_date AS CHAR) as postponed_date from "+TableName+" where order_number='"+strordernumber+"'";
				
			} else if(lastChar.equals("I")) {
				
				TableName = "inverterstore_order_details";
				
				OrderDetails_SQL = "select ord_id,order_number,customer_name,customer_mobnumber,customer_emailid,price,quantity,order_status,order_reasons,order_agent_comments,referred_coupon_code,CAST(installed_date AS CHAR) as installed_date,CAST(updated_date AS CHAR) as updated_date,CAST(postponed_date AS CHAR) as postponed_date from "+TableName+" where order_number='"+strordernumber+"'";
				
			} else if(lastChar.equals("B")) {
				
				TableName = "batterystore_order_details";
				
				OrderDetails_SQL = "select ord_id,order_number,customer_name,customer_mobnumber,customer_emailid,price,witholdbatprice,quantity,order_type,order_status,order_reasons,order_agent_comments,CAST(installed_date AS CHAR) as installed_date,CAST(updated_date AS CHAR) as updated_date,CAST(postponed_date AS CHAR) as postponed_date from "+TableName+" where order_number='"+strordernumber+"'";
				
			} else {
				
				
			} 
			
			LogLevel.DEBUG(5,new Throwable(),"OrderDetails_SQL:"+OrderDetails_SQL);
			Vector orddetails=qm.executeQuery(OrderDetails_SQL);
			LogLevel.DEBUG(5,new Throwable(),"orddetails:"+orddetails);
		
			LogLevel.DEBUG(5,new Throwable(),"TableName:"+TableName);
	
			if(orddetails==null || orddetails.size() == 0)
			{
				strRes="No Details  Found";
				return strRes;
			}
			else
			{
 				Hashtable ht3 = qm.getRow(OrderDetails_SQL);
				String OID=String.valueOf(ht3.get("ord_id"));
				String ONM=String.valueOf(ht3.get("order_number"));
				String CN=String.valueOf(ht3.get("customer_name"));
				String CM=String.valueOf(ht3.get("customer_mobnumber"));
				String CEM=String.valueOf(ht3.get("customer_emailid"));
				if(TableName.equals("servicestore_order_details")){
					
					String PRICE = String.valueOf(ht3.get("price"));
					customerpaid = String.valueOf(ht3.get("discountprice"));
					
				} else if(TableName.equals("inverterstore_order_details")){
					
					customerpaid = String.valueOf(ht3.get("price"));
					
				} else {
					
					String PRICE=String.valueOf(ht3.get("price"));
					String OBRP=String.valueOf(ht3.get("witholdbatprice"));
					String quantity=String.valueOf(ht3.get("quantity"));
					int QTY = Integer.parseInt(quantity);
					OT=String.valueOf(ht3.get("order_type"));
					
					int Price_Temp=0;
					if (OT=="New" || OT.equals("New"))
					{
						Price_Temp = Integer.parseInt(PRICE);
					}
					else
					{
						Price_Temp = Integer.parseInt(OBRP);
					}
					int QTY_int = Integer.parseInt(quantity);
					int Final_Price_Int=Price_Temp*QTY_int;
					customerpaid = Integer.toString(Final_Price_Int);
			
				
				
				}
				
				String OS=String.valueOf(ht3.get("order_status"));
				String OR=String.valueOf(ht3.get("order_reasons"));
				String OAC=String.valueOf(ht3.get("order_agent_comments"));
				String RCC=String.valueOf(ht3.get("referred_coupon_code"));
				String ID=String.valueOf(ht3.get("installed_date"));
				String UD=String.valueOf(ht3.get("updated_date"));
				String PD=String.valueOf(ht3.get("postponed_date")); 
				
				
				
				
				if(strorderremarks.equals(""))
				{
					if(OAC == null || OAC.equals("") || OAC.equals("null") || OAC.equals(null))
					{
						OAC="";
					}
					else
					{
						OAC=OAC;

					}

				}
				else
				{
					if(OAC == null || OAC.equals("") || OAC.equals("null") || OAC.equals(null))
					{
						OAC=strorderremarks;
					}
					else
					{
						OAC=OAC+","+strorderremarks;
					}
				}
				OAC = OAC.replace("\\","\\\\"); OAC= OAC.replace("'","\\'"); OAC= OAC.replace("<","<"); OAC= OAC.replace("+","+"); OAC= OAC.replace("%u201C","&ldquo;"); OAC= OAC.replace("%u201D","&rdquo;"); OAC= OAC.replace("%20"," "); OAC= OAC.replace("%22","&#34;"); 
				OAC= OAC.replace("%27","\\'"); OAC= OAC.replace("%7E","~"); OAC= OAC.replace("%21","!"); OAC= OAC.replace("%23","#"); OAC= OAC.replace("%24","$"); OAC= OAC.replace("%25","%"); OAC= OAC.replace("%5E","^"); OAC= OAC.replace("%26","&");
				OAC= OAC.replace("%28","("); OAC= OAC.replace("%29",")"); OAC= OAC.replace("%7C","|"); OAC= OAC.replace("%7D","{"); OAC= OAC.replace("%7B","}"); OAC= OAC.replace("%3A",":"); OAC= OAC.replace("%3F","?"); OAC= OAC.replace("%3E",">");
				OAC= OAC.replace("%3C","<"); OAC= OAC.replace("%60","`"); OAC= OAC.replace("%3D","="); OAC= OAC.replace("%5C","\\\\"); OAC= OAC.replace("%5D","]"); OAC= OAC.replace("%5B","[");
				OAC= OAC.replace("%2C",",");OAC= OAC.replace("%3B",";");OAC= OAC.replace("%0A","\n");
				
				LogLevel.DEBUG(5,new Throwable(),"OID:"+OID );
								
				String strSqlQry ="";
				if(strorderreasons.equals("installed")){
					
					/*if(TableName.equals("batterystore_order_details")){
						
							strSqlQry= "update "+TableName+" set order_type='"+order_type+"',order_status='"+strorderreasons+"',order_reasons='"+strorderreasons+"',order_agent_comments='"+OAC+"',payment_billing_mode='"+strpaymentmode+"',installed_date=now(),updated_date=now() WHERE  order_number='"+strordernumber+"' and ord_id='"+OID+"'";
					} else {
						
						strSqlQry= "update "+TableName+" set order_status='"+strorderreasons+"',order_reasons='"+strorderreasons+"',order_agent_comments='"+OAC+"',payment_billing_mode='"+strpaymentmode+"',installed_date=now(),updated_date=now() WHERE  order_number='"+strordernumber+"' and ord_id='"+OID+"'";
					}
					
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
					 
					int reslt = qm.executeUpdate(strSqlQry);

					if(reslt <0)
					{
						strRes = "Failed to update your details";
						return strRes;
					}
					else
					{	*/
						
						/**************************Reference Code Generation *************************/
						String referred_coupon_code ="";
						String Total_Commission_Amount_SQL = "";
						String[] order_coupon_code_array=strordernumber.split("ORDB");

						LogLevel.DEBUG(5,new Throwable(),"order_referred_code[0]:"+order_coupon_code_array[0]);
						LogLevel.DEBUG(5,new Throwable(),"order_referred_code[1]:"+order_coupon_code_array[1]);

						String order_coupon_code="REF"+order_coupon_code_array[1];
						LogLevel.DEBUG(5,new Throwable(),"order_coupon_code:"+order_coupon_code);
						if(TableName.equals("inverterstore_order_details")){
							
								Total_Commission_Amount_SQL = "SELECT referred_coupon_code,inverter_type as bat_type FROM "+TableName+" WHERE order_number='"+strordernumber+"' and ord_id='"+OID+"'";
							
						} else {
							
							Total_Commission_Amount_SQL = "SELECT referred_coupon_code,bat_type FROM "+TableName+" WHERE order_number='"+strordernumber+"' and ord_id='"+OID+"'";
							
						}	
					
						Hashtable Total_Commission_Amount_HT = qm.getRow(Total_Commission_Amount_SQL);
						LogLevel.DEBUG(5, new Throwable(), "Total_Commission_Amount_HT :" + Total_Commission_Amount_HT);
						LogLevel.DEBUG(5, new Throwable(), "Total_Commission_Amount_SQL :" + Total_Commission_Amount_SQL);
						if(Total_Commission_Amount_HT.isEmpty())
						{
							
						}
						else
						{
							referred_coupon_code =(String)Total_Commission_Amount_HT.get("referred_coupon_code");
							LogLevel.DEBUG(5, new Throwable(), "referred_coupon_code :" + referred_coupon_code);
						}
						

						if(strpaymentmode.equals("Online Payment After Fitment"))
							{

								String requestUrl="";
								String payment_link_url="";
								String CheckPaymentSql ="select order_id,status,payment_url from online_transaction_details where order_id='"+strordernumber+"'";
								
								LogLevel.DEBUG(5, new Throwable(), "CheckPaymentSql :" + CheckPaymentSql);	
								Hashtable CheckPaymentSql_HT = qm.getRow(CheckPaymentSql);
								LogLevel.DEBUG(5, new Throwable(), "CheckPaymentSql_HT :" + CheckPaymentSql_HT);

									if(CheckPaymentSql_HT.isEmpty())
									{
										LogLevel.DEBUG(5, new Throwable(), "Create_Payment_ Inside");
										
										//*********************################## Online Payment Request Generation Code ###### START #####################*****************//
										OnlinePayment Create_Payment_Link = new OnlinePayment(qm);
										LogLevel.DEBUG(5, new Throwable(), "Create_Payment_Link :" + Create_Payment_Link);
										String Create_Payment_Link_Resp=  Create_Payment_Link.getPaymentLink(req,res,session,CN,CEM,CM,customerpaid,strordernumber);
										LogLevel.DEBUG(5, new Throwable(), "Create_Payment_Link_Resp :" + Create_Payment_Link_Resp);
										
										//*********************################## Online Payment Request Generation Code ###### END #####################*****************//
								
										if(Create_Payment_Link_Resp.contains("ERROR | -"))
										{
											return Create_Payment_Link_Resp;
										}
										else
										{
											
											LogLevel.DEBUG(5, new Throwable(), "Create_Payment_Link_Resp :" + Create_Payment_Link_Resp);
											
											requestUrl="https://api-ssl.bitly.com/v3/shorten?access_token=accc1abefa0f57d73bad49cd2b80dbefe15cf10e&longUrl="+Create_Payment_Link_Resp;
											LogLevel.DEBUG(5, new Throwable(), "requestUrl :" + requestUrl);

										}  /********************* Payment Link ends***********/
										
										URL obj = new URL(requestUrl);
										HttpURLConnection con = (HttpURLConnection) obj.openConnection();
										con.setRequestMethod("GET");
										int responseCode = con.getResponseCode();
										LogLevel.DEBUG(5, new Throwable(), "responseCode" + responseCode);
										if (responseCode == HttpURLConnection.HTTP_OK) { // success
											BufferedReader in = new BufferedReader(new InputStreamReader(
											con.getInputStream()));
											String inputLine;
											StringBuffer response = new StringBuffer();
											while ((inputLine = in.readLine()) != null) {
											LogLevel.DEBUG(5, new Throwable(), "inputLine" + inputLine);
											response.append(inputLine);
											
										}
										LogLevel.DEBUG(5, new Throwable(), "string response :" + response);
										JSONObject jsonObject = new JSONObject(response.toString());
										LogLevel.DEBUG(5, new Throwable(), "string status_txt :" + jsonObject.get("status_txt"));
										LogLevel.DEBUG(5, new Throwable(), "string status_txt :" + jsonObject.get("data"));
										JSONObject jsonarray = new JSONObject(jsonObject.get("data").toString());
										LogLevel.DEBUG(5, new Throwable(), "json jsonarray" + jsonarray);
										String surl = jsonarray.getString("url");
										LogLevel.DEBUG(5, new Throwable(), "shorturl" + surl);
										payment_link_url = surl;
										LogLevel.DEBUG(5, new Throwable(), "shorturl" + surl);
										String lurl = jsonarray.getString("long_url");
										LogLevel.DEBUG(5, new Throwable(), "long_url" + lurl);
									
										String SuccessMessage ="Dear Customer, Please make your payment for the battery order with order number "+strordernumber+" in BookBattery by clicking on the link "+payment_link_url+" ."; 
										LogLevel.DEBUG(5,new Throwable(),"SuccessMessage:"+SuccessMessage);
										
										String strSMSResponse_PL=sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,SuccessMessage,CM);
										LogLevel.DEBUG(5,new Throwable(),"strSMSResponse_PL:"+strSMSResponse_PL);
										
										 if(TableName.equals("servicestore_order_details")){
						
												strSqlQry= "update "+TableName+" set order_status='"+strorderreasons+"',order_reasons='"+strorderreasons+"',order_agent_comments='"+OAC+"',payment_billing_mode='"+strpaymentmode+"',installed_date=now(),updated_date=now() WHERE  order_number='"+strordernumber+"' and ord_id='"+OID+"'";
											
										} else {
																				
											
											strSqlQry= "update "+TableName+" set order_status='inprocess',order_reasons='Payment Link Generated',payment_mode_type='"+strpaymentmode+"',order_type='"+order_type+"',order_agent_comments='"+OAC+"',updated_date=now(),order_coupon_code='"+order_coupon_code+"',installed_date=now(),coupon_code_expiry_date=DATE_ADD(now(),INTERVAL 6 MONTH),postponed_date='0000-00-00' WHERE  order_number='"+strordernumber+"' and ord_id='"+OID+"'";
										}
										//strSqlQry= "update batterystore_order_details set order_status='inprocess',order_reasons='Payment Link Generated',payment_mode_type='"+strpaymentmode+"',order_type='"+order_type+"',order_agent_comments='"+OAC+"',updated_date=now(),order_coupon_code='"+order_coupon_code+"',installed_date=now(),coupon_code_expiry_date=DATE_ADD(now(),INTERVAL 6 MONTH),postponed_date='0000-00-00' WHERE  order_number='"+strordernumber+"' and ord_id='"+OID+"'";
										LogLevel.DEBUG(5,new Throwable(),"str_Update_PaymentSqlQry:"+strSqlQry );
										//strRes="Successfully Updated order Status";
										
									} 
									else 
									{
										strRes="Please Try again!- Order Values updated";
										LogLevel.DEBUG(5, new Throwable(), "GET request not worked");
									}
											 
									} 
									else
									{
										
										 
										 String payment_orderid=(String)CheckPaymentSql_HT.get("order_id");
										 String payment_status=(String)CheckPaymentSql_HT.get("status");
										 requestUrl=(String)CheckPaymentSql_HT.get("payment_url");
										 LogLevel.DEBUG(5, new Throwable(), "payment_orderid" + payment_orderid);
										 LogLevel.DEBUG(5, new Throwable(), "payment_status" + payment_status);
										 LogLevel.DEBUG(5, new Throwable(), "payment_url" + requestUrl);
												
										 if(payment_status=="completed" || payment_status.equals("completed")){
											 
											LogLevel.DEBUG(5, new Throwable(), "Inside" + payment_status);
											 
											 if(TableName.equals("servicestore_order_details")){
						
												strSqlQry= "update "+TableName+" set order_status='"+strorderreasons+"',order_reasons='"+strorderreasons+"',order_agent_comments='"+OAC+"',payment_billing_mode='"+strpaymentmode+"',installed_date=now(),updated_date=now() WHERE  order_number='"+strordernumber+"' and ord_id='"+OID+"'";
											
											} else {
																					
												
												strSqlQry= "update "+TableName+" set order_status='inprocess',order_reasons='Payment Link Generated',payment_mode_type='"+strpaymentmode+"',order_type='"+order_type+"',order_agent_comments='"+OAC+"',updated_date=now(),order_coupon_code='"+order_coupon_code+"',installed_date=now(),coupon_code_expiry_date=DATE_ADD(now(),INTERVAL 6 MONTH),postponed_date='0000-00-00' WHERE  order_number='"+strordernumber+"' and ord_id='"+OID+"'";
											} 
											
											//strSqlQry= "update batterystore_order_details set order_status='installed',order_reasons='installed',payment_billing_mode='"+payment_mode+"',payment_mode_ordered='Online Payment After Fitment', order_type='"+order_type+"',order_agent_comments='"+OAC+"',updated_date=now(),order_coupon_code='"+order_coupon_code+"',installed_date=now(),coupon_code_expiry_date=DATE_ADD(now(),INTERVAL 6 MONTH),postponed_date='0000-00-00' WHERE  order_number='"+strordernumber+"' and ord_id='"+OID+"'";											
										 } 
										 else 
										 {
											
											//strSqlQry= "update batterystore_order_details set order_status='installed',order_reasons='Payment Pending',payment_billing_mode='"+payment_mode+"',payment_mode_ordered='Online Payment After Fitment', order_type='"+order_type+"',order_agent_comments='"+OAC+"',updated_date=now(),order_coupon_code='"+order_coupon_code+"',installed_date=now(),coupon_code_expiry_date=DATE_ADD(now(),INTERVAL 6 MONTH),postponed_date='0000-00-00' WHERE  order_number='"+strordernumber+"' and ord_id='"+OID+"'"; 
										 
											if(TableName.equals("servicestore_order_details")){
						
												strSqlQry= "update "+TableName+" set order_status='"+strorderreasons+"',order_reasons='"+strorderreasons+"',order_agent_comments='"+OAC+"',payment_billing_mode='"+strpaymentmode+"',installed_date=now(),updated_date=now() WHERE  order_number='"+strordernumber+"' and ord_id='"+OID+"'";
											
											} else if(TableName.equals("inverterstore_order_details")){
						
													strSqlQry= "update "+TableName+" set order_status='installed',order_reasons='Payment Pending',payment_billing_mode='"+strpaymentmode+"',payment_mode_ordered='Online Payment After Fitment',order_agent_comments='"+OAC+"',updated_date=now(),order_coupon_code='"+order_coupon_code+"',installed_date=now(),coupon_code_expiry_date=DATE_ADD(now(),INTERVAL 6 MONTH),postponed_date='0000-00-00' WHERE  order_number='"+strordernumber+"' and ord_id='"+OID+"'";
											
											} else {
																					
												
												strSqlQry= "update "+TableName+" set order_status='installed',order_reasons='Payment Pending',payment_billing_mode='"+strpaymentmode+"',payment_mode_ordered='Online Payment After Fitment', order_type='"+order_type+"',order_agent_comments='"+OAC+"',updated_date=now(),order_coupon_code='"+order_coupon_code+"',installed_date=now(),coupon_code_expiry_date=DATE_ADD(now(),INTERVAL 6 MONTH),postponed_date='0000-00-00' WHERE  order_number='"+strordernumber+"' and ord_id='"+OID+"'";
											} 
											LogLevel.DEBUG(5,new Throwable(),"str_Update_PaymentSqlQry:"+strSqlQry );
										 }
										 
									}  //********************* Payment Query HT ends***********/

							}
								
							else
							{
								
							/*Code to insert the coupon code and addd amount added by Prasanna to the wallet starts here*/
							if(TableName.equals("servicestore_order_details")){
						
								strSqlQry= "update "+TableName+" set order_status='"+strorderreasons+"',order_reasons='"+strorderreasons+"',order_agent_comments='"+OAC+"',payment_billing_mode='"+strpaymentmode+"',installed_date=now(),updated_date=now() WHERE  order_number='"+strordernumber+"' and ord_id='"+OID+"'";
							
							} else if(TableName.equals("inverterstore_order_details")){
		
									strSqlQry= "update "+TableName+" set order_status='installed',order_reasons='Payment Pending',payment_billing_mode='"+strpaymentmode+"',payment_mode_ordered='Online Payment After Fitment',order_agent_comments='"+OAC+"',updated_date=now(),order_coupon_code='"+order_coupon_code+"',installed_date=now(),coupon_code_expiry_date=DATE_ADD(now(),INTERVAL 6 MONTH),postponed_date='0000-00-00' WHERE  order_number='"+strordernumber+"' and ord_id='"+OID+"'";
							
							} else {
																	
								
								strSqlQry= "update "+TableName+" set order_status='installed',order_reasons='Payment Pending',payment_billing_mode='"+strpaymentmode+"',payment_mode_ordered='Online Payment After Fitment', order_type='"+order_type+"',order_agent_comments='"+OAC+"',updated_date=now(),order_coupon_code='"+order_coupon_code+"',installed_date=now(),coupon_code_expiry_date=DATE_ADD(now(),INTERVAL 6 MONTH),postponed_date='0000-00-00' WHERE  order_number='"+strordernumber+"' and ord_id='"+OID+"'";
							} 
											
							//strSqlQry= "update "+TableName+" set order_status='installed',order_reasons='"+strorderreasons+"',payment_billing_mode='"+strpaymentmode+"',order_type='"+order_type+"',order_agent_comments='"+OAC+"',updated_date=now(),order_coupon_code='"+order_coupon_code+"',installed_date=now(),coupon_code_expiry_date=DATE_ADD(now(),INTERVAL 6 MONTH),postponed_date='0000-00-00' WHERE  order_number='"+strordernumber+"' and ord_id='"+OID+"'";
							 
							
							//strSqlQry= "update batterystore_order_details set order_status='installed',order_reasons='Payment Pending',payment_billing_mode='"+payment_mode+"',payment_mode_ordered='Online Payment After Fitment', order_type='"+order_type+"',order_agent_comments='"+OAC+"',updated_date=now(),order_coupon_code='"+order_coupon_code+"',installed_date=now(),coupon_code_expiry_date=DATE_ADD(now(),INTERVAL 6 MONTH),postponed_date='0000-00-00' WHERE  order_number='"+strordernumber+"' and ord_id='"+OID+"'"; 
							
							LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );

							reslt_new = qm.executeUpdate(strSqlQry);
							LogLevel.DEBUG(5,new Throwable(),"reslt_new:"+reslt_new);

							if(reslt_new <0)
							{
								out.println("Failed to update ordered status!");
								strRes="Failed to update ordered status!";
							}
							else
							{
								
								/*added to insert into generated_referal_code_details table to track how many coupon codes are generated and for validating*/
								String product_type=(String)Total_Commission_Amount_HT.get("bat_type");
								
								String ins_gen_SqlQry = "insert into batterywaledb.generated_referal_code_details(order_coupon_code_id,order_coupon_code,order_number,product_type,referred_coupon_code,domain_name,coupon_code_expiry_date,creation_date) values( NULL,'"+order_coupon_code+"','"+strordernumber+"','"+product_type+"','"+referred_coupon_code+"','BookBattery',DATE_ADD(now(),INTERVAL 6 MONTH),now())";
								
								int resltgen_SqlQry = qm.executeUpdate(ins_gen_SqlQry);
								
								if(resltgen_SqlQry < 0)
								{
									out.println("Failed to generated_referal_code_details!");
									strRes="Failed to generated_referal_code_details!";
								}
								else
								{

									LogLevel.DEBUG(5, new Throwable(), "referred_coupon_code outside else:" + referred_coupon_code);
									
									if(referred_coupon_code.equalsIgnoreCase("0"))
									{
										LogLevel.DEBUG(5, new Throwable(), "referred_coupon_code inside if:" + referred_coupon_code);
									}
									else
									{
										LogLevel.DEBUG(5, new Throwable(), "referred_coupon_code inside else:" + referred_coupon_code);
										
										String fetch_coupon_details ="select order_number,coupon_code_expiry_date from batterywaledb.generated_referal_code_details where order_coupon_code='"+referred_coupon_code+"'";
										
										LogLevel.DEBUG(5, new Throwable(), "fetch_coupon_details :" + fetch_coupon_details);
										
										Hashtable fetch_coupon_details_HT = qm.getRow(fetch_coupon_details);
										LogLevel.DEBUG(5, new Throwable(), "fetch_coupon_details_HT :" + fetch_coupon_details_HT);
										
										String referred_order_number=(String)fetch_coupon_details_HT.get("order_number");
										LogLevel.DEBUG(5, new Throwable(), "referred_order_number :" + referred_order_number);
										
										String battery_type=(String)Total_Commission_Amount_HT.get("bat_type");
										LogLevel.DEBUG(5, new Throwable(), "battery_type :" + battery_type);
										int refferal_amount=0;
										if(battery_type.equals("Bike Batteries")){refferal_amount=25;}
										else if(battery_type.equals("Car Batteries")){refferal_amount=50;}
										else if(battery_type.equals("Inverter Batteries")){refferal_amount=100;}
										else {refferal_amount=50;}
										
										String fetch_coupon_code_details ="select referred_coupon_code,referred_order_number,refferal_amount from batterywaledb.coupon_code_details where referred_coupon_code='"+referred_coupon_code+"'"; 
										
										Hashtable fetch_coupon_code_details_HT = qm.getRow(fetch_coupon_code_details);
										LogLevel.DEBUG(5, new Throwable(), "fetch_coupon_code_details_HT :" + fetch_coupon_code_details_HT);
										
										String Ste_ref_SqlQry;
										
										if(fetch_coupon_code_details_HT.isEmpty())
										{
											//Enters into condition if there is no record in the coupon_code_details table with the reference code entered
											 Ste_ref_SqlQry = "insert into batterywaledb.coupon_code_details(coupon_code_id,referred_coupon_code,referred_order_number,coupon_code_expiry_date,refferal_amount,creation_date) values( NULL,'"+referred_coupon_code+"','"+referred_order_number+"',DATE_ADD(now(),INTERVAL 6 MONTH),'"+refferal_amount+"',now())";
										}
										else
										{
											//Enters into condition if there is a record in the coupon_code_details table and updates the his refferal_amount
											String existing_refferal_amount=String.valueOf(fetch_coupon_code_details_HT.get("refferal_amount"));
											
											refferal_amount=Integer.parseInt(existing_refferal_amount)+refferal_amount;
											
											LogLevel.DEBUG(5, new Throwable(), "refferal_amount added:" + refferal_amount);
											
											
											 Ste_ref_SqlQry = "update batterywaledb.coupon_code_details set refferal_amount='"+refferal_amount+"' where referred_coupon_code='"+referred_coupon_code+"'";
										}
										
										
										int resltref = qm.executeUpdate(Ste_ref_SqlQry);
										LogLevel.DEBUG(5, new Throwable(), "resltref :" + resltref);
										
											if(resltref < 0)
											{
												out.println("Failed to update ordered status!");
												strRes="Failed to update ordered status!";
											}
											else
											{
												String Ste_ref_count_SqlQry = "select referred_order_number,refferal_amount from batterywaledb.coupon_code_details where referred_coupon_code='"+referred_coupon_code+"'";
												
												LogLevel.DEBUG(5, new Throwable(), "Ste_ref_count_SqlQry :" + Ste_ref_count_SqlQry);
												
												Hashtable fetch_coupon_code_count_HT = qm.getRow(Ste_ref_count_SqlQry);
												LogLevel.DEBUG(5, new Throwable(), "fetch_coupon_code_count_HT :" + fetch_coupon_code_count_HT);
												
												//String ref_count = (String)fetch_coupon_code_count_HT.get("refcount");
												String refferal_amount_fetched = String.valueOf(fetch_coupon_code_count_HT.get("refferal_amount"));
												
												String referred_coupon_order_number = String.valueOf(fetch_coupon_code_count_HT.get("referred_order_number"));
												
												int refferal_amount_fetched_int = Integer.parseInt(refferal_amount_fetched);
												
												LogLevel.DEBUG(5, new Throwable(), "refferal_amount_fetched_int :" + refferal_amount_fetched_int);
												
												
												if(refferal_amount_fetched_int<300)
												{
													//Dont Send SMS
													LogLevel.DEBUG(5, new Throwable(), "Dont Send Sms");
												}
												else
												{
													LogLevel.DEBUG(5, new Throwable(), "Send SMS");
													
													String Ste_ref_mob_SqlQry = "select customer_name from batterywaledb.batterystore_order_details where order_number='"+referred_coupon_order_number+"'";
													
													LogLevel.DEBUG(5, new Throwable(), "Ste_ref_mob_SqlQry :" + Ste_ref_mob_SqlQry);
													
													Hashtable fetch_mob_SqlQry_HT = qm.getRow(Ste_ref_mob_SqlQry);
													LogLevel.DEBUG(5, new Throwable(), "fetch_mob_SqlQry_HT :" + fetch_mob_SqlQry_HT);
													
													if(fetch_mob_SqlQry_HT.isEmpty())
													{
														Ste_ref_mob_SqlQry = "select consumer_mobnumber from batterywaledb.inverter_order_details where order_number='"+referred_coupon_order_number+"'";
														LogLevel.DEBUG(5, new Throwable(), "Ste_ref_mob_SqlQry battery :" + Ste_ref_mob_SqlQry);
														fetch_mob_SqlQry_HT = qm.getRow(Ste_ref_mob_SqlQry);
														LogLevel.DEBUG(5, new Throwable(), "fetch_mob_SqlQry_HT battery :" + fetch_mob_SqlQry_HT);
													}
													
													String consumer_mobnumber_fetched = String.valueOf(fetch_mob_SqlQry_HT.get("consumer_mobnumber"));
													
													String CongratsMessage ="Congrats, you have earned "+refferal_amount_fetched+" Rs in your referral Wallet. Use code "+referred_coupon_code+" to avail free car/inverter battery health check of worth Rs.299/599 while ordering or call 9603467559 . Thanks for your support."; 
													LogLevel.DEBUG(5,new Throwable(),"CongratsMessage:"+CongratsMessage);
													
													String strSMSResponse5=sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,CongratsMessage,consumer_mobnumber_fetched);
													LogLevel.DEBUG(5,new Throwable(),"strSMSResponse5:"+strSMSResponse5);
												}

											}
											
									 }
									 
								}
							}

							/*Code to insert the coupon code and addd amount added by Prasanna to the wallet ends here*/

							}
						
						/**************************Reference Code Generation *************************/	
						if(TableName.equals("inverterstore_order_details")){
							
								
							//######## Send SMS for ORDER
							Order_Store_SMS Send_Order_SMS_Checkout = new Order_Store_SMS(qm);
							String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,strordernumber,"Inverter","Yes","Yes","No",strorderreasons);
							LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
							//######## Send SMS for ORDER
							
							//######## Send Mail for ORDER
							Order_Store_SMS Send_Order_MAIL_Checkout = new Order_Store_SMS(qm);
							String MAIL_Report=Send_Order_MAIL_Checkout.Send_Order_Mail(req,res,session,strordernumber,"Inverter","Sir","Yes","Yes","No",strorderreasons);
							LogLevel.DEBUG(5, new Throwable(), "MAIL_Report :" + MAIL_Report);
						
						
						
						} else if(TableName.equals("servicestore_order_details")){
							
							//######## Send SMS for ORDER
							Order_Store_SMS Send_Order_SMS_Checkout = new Order_Store_SMS(qm);
							String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,strordernumber,"Service","Yes","Yes","No",strorderreasons);
							LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
							//######## Send SMS for ORDER
							
							//######## Send Mail for ORDER
							Order_Store_SMS Send_Order_MAIL_Checkout = new Order_Store_SMS(qm);
							String MAIL_Report=Send_Order_MAIL_Checkout.Send_Order_Mail(req,res,session,strordernumber,"Service","Sir","Yes","Yes","No",strorderreasons);
							LogLevel.DEBUG(5, new Throwable(), "MAIL_Report :" + MAIL_Report);
						
						} else {
							
							
								//######## Send SMS for ORDER
							Order_Store_SMS Send_Order_SMS_Checkout = new Order_Store_SMS(qm);
							String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,strordernumber,"Battery","Yes","Yes","No",strorderreasons);
							LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
							//######## Send SMS for ORDER
							
							//######## Send Mail for ORDER
							Order_Store_SMS Send_Order_MAIL_Checkout = new Order_Store_SMS(qm);
							String MAIL_Report=Send_Order_MAIL_Checkout.Send_Order_Mail(req,res,session,strordernumber,"Battery","Sir","Yes","Yes","No",strorderreasons);
							LogLevel.DEBUG(5, new Throwable(), "MAIL_Report :" + MAIL_Report);
							
		
						}
						
					strRes = "Successfully Updated order Status as "+strorderreasons+"";	
					//}	
					
				} else if(strorderreasons.equals("inprocess")){
					
					strSqlQry= "update "+TableName+" set order_status='"+strorderreasons+"',order_reasons='"+strorderreasons+"',order_agent_comments='"+OAC+"',updated_date=now() WHERE  order_number='"+strordernumber+"' and ord_id='"+OID+"'";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
					 
					int reslt = qm.executeUpdate(strSqlQry);

					if(reslt <0)
					{
						strRes = "Failed to update your details";
						return strRes;
					}
					else
					{
						strRes = "Successfully Updated order Status as "+strorderreasons+"";
					}	
					
				} else if(strorderreasons.equals("postponed")){
					
					strSqlQry= "update "+TableName+" set order_status='"+strorderreasons+"',order_reasons='"+strorderreasons+"',order_agent_comments='"+OAC+"',postponed_date='"+strpostponedate+"',updated_date=now() WHERE  order_number='"+strordernumber+"' and ord_id='"+OID+"'";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
					 
					int reslt = qm.executeUpdate(strSqlQry);

					if(reslt <0)
					{
						strRes = "Failed to update your details";
						return strRes;
					}
					else
					{
						strRes = "Successfully Updated order Status as "+strorderreasons+"";
					}	
					
				} else {
					
					strSqlQry= "update "+TableName+" set order_status='"+strorderreasons+"',order_reasons='"+strorderreasons+"',order_agent_comments='"+OAC+"',updated_date=now() WHERE  order_number='"+strordernumber+"' and ord_id='"+OID+"'";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
					 
					int reslt = qm.executeUpdate(strSqlQry);

					if(reslt <0)
					{
						strRes = "Failed to update your details";
						return strRes;
					}
					else
					{
						
						if(TableName.equals("inverterstore_order_details")){
							
							
						//######## Send SMS for ORDER
						Order_Store_SMS Send_Order_SMS_Checkout = new Order_Store_SMS(qm);
						String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,strordernumber,"Inverter","Yes","Yes","No",strorderreasons);
						LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
						//######## Send SMS for ORDER
						
						//######## Send Mail for ORDER
						Order_Store_SMS Send_Order_MAIL_Checkout = new Order_Store_SMS(qm);
						String MAIL_Report=Send_Order_MAIL_Checkout.Send_Order_Mail(req,res,session,strordernumber,"Inverter","Sir","Yes","Yes","No",strorderreasons);
						LogLevel.DEBUG(5, new Throwable(), "MAIL_Report :" + MAIL_Report);
						
						
						
						} else if(TableName.equals("servicestore_order_details")){
							
								
							//######## Send SMS for ORDER
							Order_Store_SMS Send_Order_SMS_Checkout = new Order_Store_SMS(qm);
							String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,strordernumber,"Service","Yes","Yes","No",strorderreasons);
							LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
							//######## Send SMS for ORDER
							
							//######## Send Mail for ORDER
							Order_Store_SMS Send_Order_MAIL_Checkout = new Order_Store_SMS(qm);
							String MAIL_Report=Send_Order_MAIL_Checkout.Send_Order_Mail(req,res,session,strordernumber,"Service","Sir","Yes","Yes","No",strorderreasons);
							LogLevel.DEBUG(5, new Throwable(), "MAIL_Report :" + MAIL_Report);
						
						} else {
							
							//######## Send SMS for ORDER
							Order_Store_SMS Send_Order_SMS_Checkout = new Order_Store_SMS(qm);
							String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,strordernumber,"Battery","Yes","Yes","No",strorderreasons);
							LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
							//######## Send SMS for ORDER
							
							//######## Send Mail for ORDER
							Order_Store_SMS Send_Order_MAIL_Checkout = new Order_Store_SMS(qm);
							String MAIL_Report=Send_Order_MAIL_Checkout.Send_Order_Mail(req,res,session,strordernumber,"Battery","Sir","Yes","Yes","No",strorderreasons);
							LogLevel.DEBUG(5, new Throwable(), "MAIL_Report :" + MAIL_Report);
 
						}
						
						strRes = "Successfully Updated order Status as "+strorderreasons+"";
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
	/* **************************************************************************************************************************************\
	* This method is to fetch battery details details.
	* @strSqlQry : carries the query to select battery details from battery_details table.
	* @strSqlQry1 : Query has multiple pages to select battery details from battery_details table.
	\* **************************************************************************************************************************************/
	public String generateorderinvoice(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
		String strRes="";
 		String TableName="";
 			
		int reslt_new=0;
		
		try
		{	
		
			SendSMS sendsms = new SendSMS(qm);
			
			
			String ordernumber= (req.getParameter("ordernumber")!=null)?(req.getParameter("ordernumber")):"";
			LogLevel.DEBUG(5,new Throwable(),"ordernumber:"+ordernumber ); 
			
			//String lastChar = ordernumber.substring(ordernumber.length() - 1);
			String splitChar = ordernumber.substring(0, 4);
			String lastChar = splitChar.substring(splitChar.length() - 1);
			LogLevel.DEBUG(5,new Throwable(),"lastChar:"+lastChar );
			
			ServletOutputStream out=res.getOutputStream();
			
			
			String strpostponedate="";
			String OrderDetails_SQL="";
			 
			 if(lastChar.equals("S")) {
				
				TableName = "service_order_details";
				OrderDetails_SQL = "select ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_address1,retailer_name,retailer_mobilnumber,retailer_emailid,state,city,area,pincode,product_type,veh_name,veh_model,product_capacity,quantity,service_price_mrp,service_price_discount,payment_mode,payment_mode_type,service_commission_amount,order_status,CAST(creation_date AS CHAR) as creation_date,CAST(installed_date AS CHAR) as installed_date,CAST(updated_date AS CHAR) as updated_date,CAST(postpone_date AS CHAR) as postponed_date from "+TableName+" where order_number='"+ordernumber+"'";
				
			} else if(lastChar.equals("I")) {
				
				TableName = "inverter_order_details";
				
				OrderDetails_SQL = "Select order_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_address,retailer_name,retailer_mobile_number,retailer_emailid,state,city,area,pincode,inverter_brand,inverter_model,inverter_capacity,quantity,MRP_Price,price,erp_pre_tax,payment_mode,payment_mode_type,inverter_commission_amount,total_commission_amount,order_status,CAST(creation_date AS CHAR) as creation_date,CAST(installed_date AS CHAR) as installed_date,CAST(updated_date AS CHAR) as updated_date from "+TableName+" where order_number='"+ordernumber+"'";
				
			} else if(lastChar.equals("B")) {
				
				TableName = "battery_order_details";
				
				OrderDetails_SQL = "Select ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_address,retailer_name,retailer_mobilnumber,retailer_emailis,state,city,area,pincode,bat_type,battery_brand,battery_model,battery_capacity,quantity,veh_name,veh_model,MRP_Price,price,witholdbatprice,erp_pre_tax,order_type,payment_mode,payment_mode_type,battery_commission_amount,total_commission_amount,order_status,CAST(creation_date AS CHAR) as creation_date,CAST(installed_date AS CHAR) as installed_date,CAST(updated_date AS CHAR) as updated_date,CAST(postpone_date AS CHAR) as postponed_date from "+TableName+" where order_number='"+ordernumber+"'";
				
			} else if(lastChar.equals("T")) {
				
				TableName = "trolley_order_details";
				
				OrderDetails_SQL = "Select ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailid,state,city,area,pincode,trolley_brand,trolley_model,price,order_status,quantity,operator,agent_name,payment_mode,payment_mode_type,city_percentage,consumer_address,CAST(creation_date AS CHAR) as creation_date,CAST(installed_date AS CHAR) as installed_date,CAST(updated_date AS CHAR) as updated_date,CAST(postpone_date AS CHAR) as postponed_date from "+TableName+" where order_number='"+ordernumber+"'";
				
			} else {
				
				
			} 
			
			LogLevel.DEBUG(5,new Throwable(),"OrderDetails_SQL:"+OrderDetails_SQL);
			Vector orddetails=qm.executeQuery(OrderDetails_SQL);
			LogLevel.DEBUG(5,new Throwable(),"orddetails:"+orddetails);
		
			LogLevel.DEBUG(5,new Throwable(),"TableName:"+TableName);
	
			if(orddetails==null || orddetails.size() == 0)
			{
				strRes="No Details  Found";
				return strRes;
			}
			else
			{ 
				/******************************************Generate Invoice***************************/
					GenerateStoreInvoice gbpdf = new GenerateStoreInvoice();
					
					if(TableName.equals("inverter_order_details")){
						
						gbpdf.writeToInverterPdf(ordernumber);
						
					} else if(TableName.equals("service_order_details")){
						
						gbpdf.writeToServicePdf(ordernumber);
						
					} else if(TableName.equals("trolley_order_details")){
						
						gbpdf.writeToTrolleyPdf(ordernumber);
						
					} else {
						
						gbpdf.writeToBatteryPdf(ordernumber);
					}
				
					String InvoiceQuery_SQL="";
					String order_number_iq="";
					String invoice_number_iq="";
					String invoice_url_iq="";
					String invoice_file="";
					InvoiceQuery_SQL = "Select order_number,invoice_number,invoice_url,customer_name,store_name,order_type,seq_number from batterystore_invoice where order_number='"+ordernumber+"'";
					
					Hashtable InvoiceQuery_HT = qm.getRow(InvoiceQuery_SQL);
					LogLevel.DEBUG(5, new Throwable(), "InvoiceQuery_SQL :" + InvoiceQuery_SQL);
					if(InvoiceQuery_HT.isEmpty())
						{
							strRes="Invoice Not Generated";
							return strRes;
						}
						else
						{
							
							 order_number_iq=(String)InvoiceQuery_HT.get("order_number");
							 invoice_number_iq=(String)InvoiceQuery_HT.get("invoice_number");
							 invoice_url_iq=(String)InvoiceQuery_HT.get("invoice_url");
							 invoice_file = invoice_url_iq.replace("http://stage1.bookbattery.com/bookbattery/","/home/ngit/tomcat/webapps/");
						}
					LogLevel.DEBUG(5, new Throwable(), "invoice_number_iq :" + invoice_number_iq);
					LogLevel.DEBUG(5, new Throwable(), "invoice_url_iq :" + invoice_url_iq);
					LogLevel.DEBUG(5, new Throwable(), "invoice_file :" + invoice_file);
					/******************************************Generate Invoice***************************/
				strRes="Successfully Generated Invoice";
				
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
	public String vieworderinvoice(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
		String strRes="";
 		 
		int reslt_new=0;
		
		
		try
		{	
		
 			 
			String ordernumber= (req.getParameter("ordernumber")!=null)?(req.getParameter("ordernumber")):"";
			LogLevel.DEBUG(5,new Throwable(),"ordernumber:"+ordernumber ); 
			
			String CheckInvoiceQuery_SQL = "Select order_number,invoice_number,invoice_url,customer_name,store_name,order_type,seq_number from batterystore_invoice where order_number='"+ordernumber+"'";
					
			Hashtable InvoiceQuery_HT = qm.getRow(CheckInvoiceQuery_SQL);
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
				 String invoice_file = invoice_url_iq.replace("http://stage1.bookbattery.com/bookbattery/","/home/ngit/tomcat/webapps/");
				 LogLevel.DEBUG(1,new Throwable(),"invoice_file! " +invoice_file);
				 LogLevel.DEBUG(1,new Throwable(),"invoice_url_iq! " +invoice_url_iq);
				 strRes = invoice_url_iq;
			}
			
			
			 
		}
		catch (Exception e)
		{
			LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
			e.printStackTrace();

		}
		return strRes;
		
	} 
	
	/********************************
	 * 
	 Method to send Invoice
	 */
	public String sendinvoice(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
		String strRes="";

		try
		{	
			String ordernumber= (req.getParameter("ordernumber")!=null)?(req.getParameter("ordernumber")):"";
			LogLevel.DEBUG(5,new Throwable(),"sendinvoice ordernumber:"+ordernumber ); 
			
			String CheckInvoiceQuery_SQL = "Select order_number,invoice_number,invoice_url,customer_name,store_name,order_type,seq_number from batterystore_invoice where order_number='"+ordernumber+"'";
			
			Hashtable InvoiceQuery_HT = qm.getRow(CheckInvoiceQuery_SQL);
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
				 String invoice_file = invoice_url_iq.replace("http://stage1.bookbattery.com/bookbattery/","/home/ngit/tomcat/webapps/");
				 LogLevel.DEBUG(1,new Throwable(),"invoice_file! " +invoice_file);
				 LogLevel.DEBUG(1,new Throwable(),"invoice_url_iq! " +invoice_url_iq);
				 strRes = invoice_url_iq;
				 
				//######## Send Mail for ORDER
					Order_Store_SMS Send_Order_MAIL_Checkout = new Order_Store_SMS(qm);
					String MAIL_Report=Send_Order_MAIL_Checkout.Send_Order_Mail(req,res,session,order_number_iq,"Battery","Sir","Yes","Yes","No","confirmed");
					LogLevel.DEBUG(5, new Throwable(), "MAIL_Report :" + MAIL_Report);
					//######## Send Mail for ORDER
					
				strRes="Sucessfully | Sent Email"; 
			}
		}
		catch (Exception e)
		{
			LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
			e.printStackTrace();

		}
		// TODO Auto-generated method stub
		return strRes;
	}
 }