/***********************************************************************		
	NGIT Confidential. 
	@File Name   : CancelOrderDetails.java
	@Description : This Servlet is used to allow the operator to Login
	@Date        : 12th November 2019
******************************************************************/ 
package com.ngit.servlets.admin.batterywalestore; 
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import java.io.IOException; 
import java.io.FileInputStream; 
import java.util.Properties; 
import java.util.Hashtable; 
import java.util.Vector;
import javax.servlet.ServletConfig; 
import javax.servlet.ServletContext; 
import javax.servlet.ServletException; 
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse; 
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.io.File;

/*
 * @author Prasanna Kumari
 */
/* This class is to Admin Login. This class initializes the necessary connection pools and perform the transactions on the pools. */
public class CheckOrderDetails extends HttpServlet 
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
		String strWhatToDo = (req.getParameter("hidWhatToDo")!=null)?req.getParameter("hidWhatToDo"):""; 
		
		/*This methos is used to validate adminlogin details*/
		if(strWhatToDo.equalsIgnoreCase("getpaymentstatus"))
		{
			
			try
			{
				String strRes=getpaymentstatus(req,res,session);
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
	    
	}//End of dopost
	/* **************************************************************************************************************************************\
	* This method is to fetch battery details details.
	* @strSqlQry : carries the query to select battery details from battery_details table.
	* @strSqlQry1 : Query has multiple pages to select battery details from battery_details table.
	\* **************************************************************************************************************************************/
	public String getpaymentstatus(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
 		String OrderDetails_SQL="";
		try
		{	
			String strordernumber= (req.getParameter("ordernumber")!=null)?(req.getParameter("ordernumber")):"";
			LogLevel.DEBUG(5,new Throwable(),"strordernumber:"+strordernumber );
			
			OrderDetails_SQL = "Select order_id,transaction_id,payment_request_id,status,currency,amount,name,email,phone,description,redirect_url,webhook_url,created_at,resource_uri,payment_url,created_date,payment_id from online_transaction_details where order_id='"+strordernumber+"'"; 
			
			Vector orddetails=qm.executeQuery(OrderDetails_SQL);
			LogLevel.DEBUG(5,new Throwable(),"orddetails:"+orddetails);
	
			if(orddetails==null || orddetails.size() == 0)
			{
				strRes=strRes+"<p align='center' valign='top' class='insidecontent'><table valign='top' width='500' cellspacing='0' border='1' align='center' bgcolor='#FFFFFF' cellpadding='0'><tr><td><table valign='top' width='500' bgcolor='#FFFFFF' border='0'><tr><td class='insidecontent' align='center'>Invalid Ord Ref Number!</td></tr></table></td></tr></table><a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>";
			}
			else
			{
				
				strRes=strRes+"<div class='table-responsive'><table class='table table-bordered'>";
				
			  	for(int j=0; j<orddetails.size();j++)
				{
						Hashtable ht3=(Hashtable)orddetails.get(j);
						String OID=String.valueOf(ht3.get("order_id"));
						String TID=String.valueOf(ht3.get("transaction_id"));
						String PRID=String.valueOf(ht3.get("payment_request_id"));
						String STS=String.valueOf(ht3.get("status"));
						String SRCNY=String.valueOf(ht3.get("currency"));
						String AMNT=String.valueOf(ht3.get("amount"));
 						String NM=String.valueOf(ht3.get("name"));
 						String EM=String.valueOf(ht3.get("email"));
 						String PH=String.valueOf(ht3.get("phone"));
						String DPTN=String.valueOf(ht3.get("description"));
						String RDL=String.valueOf(ht3.get("redirect_url"));
						String CA=String.valueOf(ht3.get("webhook_url"));
						String OC=String.valueOf(ht3.get("created_at"));
						String RUR=String.valueOf(ht3.get("resource_uri"));
						String DT=String.valueOf(ht3.get("created_date")); 
						String PID=String.valueOf(ht3.get("payment_id")); 
						String PURL=String.valueOf(ht3.get("payment_url")); 
						 
						strRes=strRes+"<tr><td><strong>Order Number</strong></td><td>"+OID+"</td><td><strong>Name</strong></td><td>"+NM+"</td></tr><tr><td><strong>Mobile Number</strong></td><td>"+PH+"</td><td><strong>Email Id</strong></td><td>"+EM+"</td></tr><tr><td><strong>Amount Paid</strong></td><td>"+AMNT+"</td><td><strong>Payment Status</strong></td><td>"+STS+"</td></tr><tr><td><strong>Resource URL</strong></td><td>"+RUR+"</td><td><strong>Payment URL</strong></td><td>"+PURL+"</td></tr><tr><td><strong>Date</strong></td><td>"+DT+"</td></tr>";
						
					int Jcnt=j+1;
					 
					strRes=strRes+"</table></div>";
					
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
	
 }