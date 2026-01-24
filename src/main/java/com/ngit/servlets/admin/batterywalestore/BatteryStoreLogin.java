/***********************************************************************		
	NGIT Confidential. 
	@File Name   : AdminBatteryLoginServlet.java
	@Description : This Servlet is used to allow the operator to Login
	@Date        : 30th August 2013
******************************************************************/ 
package com.ngit.servlets.admin.batterywalestore; 
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import java.io.IOException; 
import java.io.FileInputStream; 
import java.util.Properties;
import java.util.Calendar;
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
 * @author Prasanna Kumari.
 */
/* This class is to Admin Login. This class initializes the necessary connection pools and perform the transactions on the pools. */
public class BatteryStoreLogin extends HttpServlet 
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
			String strLogFilePath = (propsMOPConfig.getProperty("LogFilePath")!=null)?propsMOPConfig.getProperty("LogFilePath"):"/home/ngit/tomcat/webapps/bookbattery/logs/";
			if(strLogFilePath.equals(""))
			strLogFilePath = "/home/ngit/tomcat/webapps/bookbattery/logs/";
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
		
		/*This methos is used to validate adminlogin details*/
		if(strWhatToDo.equalsIgnoreCase("checkstorelogin"))
		{
			String strUserId = (req.getParameter("username")!=null)?(req.getParameter("username")):"";
			LogLevel.DEBUG(1,new Throwable(),"In strUserId " +strUserId);
			String strPassword = (req.getParameter("password")!=null)?(req.getParameter("password")):"";
			LogLevel.DEBUG(1,new Throwable(),"In strPassword " +strPassword);
			try
			{
				//Connection connection = qm.getConnection();

				
				/*Query to get the Admin User name and Password*/
 				//String strSqlQry = "SELECT operator_loginname,operator_passwd FROM batteryoperator WHERE BINARY operator_loginname='"+strUserId+"' and BINARY operator_passwd='"+strPassword+"'"; 
				String strStoreId = "";
				String strStoreName = "";
				
				String strSqlQry = "SELECT store_username FROM batterystore_login WHERE BINARY store_username=?";
				
				boolean isAuthenticate = qm.authenticateUser(strSqlQry, strUserId);
				
				if (!isAuthenticate)
				{
					LogLevel.DEBUG(1,new Throwable(),"Invalid User Name " +strUserId);
					session.setAttribute("priority","1"); 
					session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'>Invalid Login!</font> ");
					res.sendRedirect("../jsp/batterywalestore/storelogin.jsp");
					return;
				} 
				else
				{
					String strSqlPwdQry = "SELECT store_password FROM batterystore_login WHERE BINARY store_password=?";
					boolean isAuthenticatePwd = qm.authenticateUser(strSqlPwdQry, strPassword);
					
				 
					if (!isAuthenticatePwd) 
					{
						LogLevel.DEBUG(1,new Throwable(),"Invalid User Name " +strUserId);
						session.setAttribute("priority","1"); 
						session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'>Invalid Login!</font> ");
						res.sendRedirect("../jsp/batterywalestore/storelogin.jsp");
						return;
					}
					else 
					{
						String strstore_query = "select store_id,store_name FROM batterystore_login WHERE BINARY 						store_username='"+strUserId+"' and BINARY store_password='"+strPassword+"'";
						LogLevel.DEBUG(5,new Throwable(),"strstore_query :"+strstore_query);
		                          
						Hashtable htgetlogin = qm.getRow(strstore_query); 
						LogLevel.DEBUG(5,new Throwable(),"htgetlogin :"+htgetlogin);
						strStoreId=(String)htgetlogin.get("store_id");
						 strStoreName=(String)htgetlogin.get("store_name");
							LogLevel.DEBUG(1,new Throwable(),"strStoreId " +strStoreId);
							LogLevel.DEBUG(1,new Throwable(),"strStoreName " +strStoreName);
							session.setAttribute("sesstrStoreId",strStoreId);
							session.setAttribute("sesstrStoreName",strStoreName);
							
							LogLevel.DEBUG(1,new Throwable(),"Login Successful ");
							session.setAttribute("sesStoreOperatorName",strUserId);
							res.sendRedirect(baseURL+"/servlet/BatteryStoreLogin?hidWhatToDo=storehome");
							return;
					}
				}
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace(); 
				res.sendRedirect("../jsp/batterywalestore/storelogin.jsp");
			}
			catch(Exception e)
			{
				e.printStackTrace(); 
				res.sendRedirect("../jsp/batterywalestore/storelogin.jsp"); 
			}
		}
		/*This methos is used to get admin summary*/
		else if(strWhatToDo.equalsIgnoreCase("storehome"))
		{
           	try
			{  
           		Calendar cal = Calendar.getInstance();
				int currentMonth = cal.get(Calendar.MONTH) + 1;
				int currentYear = cal.get(Calendar.YEAR);
				cal.add(Calendar.MONTH, 1);  
				cal.set(Calendar.DAY_OF_MONTH, 1);  
				cal.add(Calendar.DATE, -1);  

				cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
				
				int lastDayOfMonth =  cal.getActualMaximum(Calendar.DAY_OF_MONTH);
				
				String Firstday = String.valueOf(currentYear)+"-"+String.valueOf(currentMonth)+"-01";
				String Lastday = String.valueOf(currentYear)+"-"+String.valueOf(currentMonth)+"-"+lastDayOfMonth;
				
				LogLevel.DEBUG(5,new Throwable(),"currentMonth :"+currentMonth );	
				LogLevel.DEBUG(5,new Throwable(),"currentYear :"+currentYear );		
				LogLevel.DEBUG(5,new Throwable(),"Firstday :"+Firstday );	
				LogLevel.DEBUG(5,new Throwable(),"Lastday :"+Lastday );
				
 				String strStoreOperatorName=(String)session.getAttribute("sesStoreOperatorName"); 
				LogLevel.DEBUG(5,new Throwable(),"strStoreOperatorName :"+strStoreOperatorName );	
				 
				String strStoreId=(String)session.getAttribute("sesstrStoreId"); 
				LogLevel.DEBUG(5,new Throwable(),"strStoreId :"+strStoreId );	
				 
				String strStoreName=(String)session.getAttribute("sesstrStoreName"); 
				LogLevel.DEBUG(5,new Throwable(),"strStoreName :"+strStoreName );	
				
				String StrCond="";
				
				StrCond=" and date(creation_date) between '"+Firstday+"' and '"+Lastday+"'";
				
				String strgetbatteryorders = "SELECT COUNT(*) AS count1,COUNT(CASE WHEN order_reasons LIKE '%installed%' THEN 1 END) AS count2,COUNT(CASE WHEN order_status LIKE '%Not Confirmed %' THEN 1 END) AS count3,COUNT(CASE WHEN order_reasons LIKE '%In Process%' THEN 1 END) AS count4 FROM `battery_order_details` where retailer_name='"+strStoreName+"' "+StrCond+"";
				LogLevel.DEBUG(5,new Throwable(),"strgetbatteryorders :"+strgetbatteryorders);
            	 
				String strgetinverterorders = "SELECT COUNT(*) AS count1,COUNT(CASE WHEN order_reasons LIKE '%installed%' THEN 1 END) AS count2,COUNT(CASE WHEN order_status LIKE '%Not Confirmed %' THEN 1 END) AS count3,COUNT(CASE WHEN order_reasons LIKE '%In Process%' THEN 1 END) AS count4 FROM `inverter_order_details` where retailer_name='"+strStoreName+"' "+StrCond+"";
				LogLevel.DEBUG(5,new Throwable(),"strgetinverterorders :"+strgetinverterorders);
            
				String strgetservicehealthorders = "SELECT COUNT(*) AS count1,COUNT(CASE WHEN order_reasons LIKE '%installed%' THEN 1 END) AS count2,COUNT(CASE WHEN order_status LIKE '%Not Confirmed %' THEN 1 END) AS count3,COUNT(CASE WHEN order_reasons LIKE '%In Process%' THEN 1 END) AS count4 FROM `service_order_details` where retailer_name='"+strStoreName+"' "+StrCond+"";
				LogLevel.DEBUG(5,new Throwable(),"strgetservicehealthorders :"+strgetservicehealthorders);
            
				//String strgetservicejumpstartorders = "SELECT COUNT(*) AS count1,COUNT(CASE WHEN order_status LIKE '%confirmed%' THEN 1 END) AS count2,COUNT(CASE WHEN order_reasons LIKE '%installed%' THEN 1 END) AS count3,COUNT(CASE WHEN order_reasons LIKE '%cancelled%' THEN 1 END) AS count4 FROM `service_order_details` where services_type='Jump Start' and retailer_name='"+strStoreName+"'";
				//LogLevel.DEBUG(5,new Throwable(),"strgetservicejumpstartorders :"+strgetservicejumpstartorders);
            
				//String strgetservicerechargeorders = "SELECT COUNT(*) AS count1,COUNT(CASE WHEN order_status LIKE '%confirmed%' THEN 1 END) AS count2,COUNT(CASE WHEN order_reasons LIKE '%installed%' THEN 1 END) AS count3,COUNT(CASE WHEN order_reasons LIKE '%cancelled%' THEN 1 END) AS count4 FROM `service_order_details` where services_type='Recharge' and retailer_name='"+strStoreName+"'";
				//LogLevel.DEBUG(5,new Throwable(),"strgetservicerechargeorders :"+strgetservicerechargeorders);
            
				Vector result1=qm.executeQuery(strgetbatteryorders);
				LogLevel.DEBUG(5,new Throwable(),"result1 :"+result1);

				Vector result2=qm.executeQuery(strgetinverterorders);
				LogLevel.DEBUG(5,new Throwable(),"result2 :"+result2);

				Vector result3=qm.executeQuery(strgetservicehealthorders);
				LogLevel.DEBUG(5,new Throwable(),"result3 :"+result3);

				//Vector result4=qm.executeQuery(strgetservicejumpstartorders);
				//LogLevel.DEBUG(5,new Throwable(),"result4 :"+result4);

				//Vector result5=qm.executeQuery(strgetservicerechargeorders);
				//LogLevel.DEBUG(5,new Throwable(),"result5 :"+result5);

				 
				if(session.getAttribute("sesCountofBatterynrand")!=null)
				session.removeAttribute("sesCountofBatterynrand");
				session.setAttribute("sesCountofBatterynrand",result1 );
				session.setAttribute("sesCountofInverterbrand",result2 );
				session.setAttribute("sesCountofServiceHealth",result3 );
				//session.setAttribute("sesCountofServiceJumpStart",result4 );
				//session.setAttribute("sesCountofServiceRecharge",result5 );

   
				session.setAttribute("sesStoreOperatorName",strStoreOperatorName); 
				session.setAttribute("sesstrStoreId",strStoreId); 
				session.setAttribute("sesstrStoreName",strStoreName); 
				res.sendRedirect("../jsp/batterywalestore/storehome.jsp");
				return;
			}
			catch(IOException ioe)
			{
				LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
				ioe.printStackTrace();
				session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
				res.sendRedirect("../jsp/batterywalestore/storelogin.jsp");
			}
			catch(Exception e)
			{
				LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
				e.printStackTrace();
				session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
				res.sendRedirect("../jsp/batterywalestore/storelogin.jsp");
			}
		}

	}//End of dopost
}//end of Class