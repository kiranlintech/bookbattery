/***********************************************************************		
	NGIT Confidential. 
	@File Name   : AdminBatteryLoginServlet.java
	@Description : This Servlet is used to allow the operator to Login
	@Date        : 30th August 2013
******************************************************************/ 
package com.ngit.servlets.admin.batterystore; 
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
 * @author Sai Krishna Daddala.
 */
/* This class is to Admin Login. This class initializes the necessary connection pools and perform the transactions on the pools. */
public class AdminBatteryLogin extends HttpServlet 
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
		String struserName=(String)session.getAttribute("sesBatteryAdminName"); 
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
		if(strWhatToDo.equalsIgnoreCase("batteryadminlogin"))
		{
			String strUserId = (req.getParameter("username")!=null)?(req.getParameter("username")):"";
			String strPassword = (req.getParameter("passwd")!=null)?(req.getParameter("passwd")):"";
			try
			{
				
				//Connection connection = qm.getConnection();
				
				/*Query to get the Admin User name and Password*/
 				//String strSqlQry = "SELECT admin_loginname,admin_passwd FROM batteryadmin WHERE BINARY admin_loginname='"+strUserId+"' and BINARY admin_passwd='"+strPassword+"'"; 
				
				String strSqlQry = "SELECT admin_loginname FROM batteryadmin WHERE BINARY admin_loginname=?";
				boolean isAuthenticate = qm.authenticateUser(strSqlQry, strUserId);
				
				if (!isAuthenticate) 
				{
					LogLevel.DEBUG(1,new Throwable(),"Invalid User Name " +strUserId);
					session.setAttribute("priority","1"); 
					session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'>Invalid Login!</font> ");
					res.sendRedirect("/bookbattery/jsp/admin/batterystore/batteryadminlogin.jsp");
					return;
				}
				else
				{
					String strSqlPwdQry = "SELECT admin_passwd FROM batteryadmin WHERE BINARY admin_passwd=?";
					boolean isAuthenticatePwd = qm.authenticateUser(strSqlPwdQry, strPassword);
					
					if (!isAuthenticatePwd) 
					{
						LogLevel.DEBUG(1,new Throwable(),"Invalid User Name " +strUserId);
						session.setAttribute("priority","1"); 
						session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'>Invalid Login!</font> ");
						res.sendRedirect("/bookbattery/jsp/admin/batterystore/batteryadminlogin.jsp");
						return;
					}
					
					LogLevel.DEBUG(1,new Throwable(),"Login Successful ");
					session.setAttribute("sesBatteryAdminName",strUserId);
					res.sendRedirect("/bookbattery/servlet/AdminBatteryLogin?hidWhatToDo=batteryadminhome");
					return;
				}
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace(); 
				res.sendRedirect("/bookbattery/jsp/admin/batterystore/batteryadminlogin.jsp");
			}
			catch(Exception e)
			{
				e.printStackTrace(); 
				res.sendRedirect("/bookbattery/jsp/admin/batterystore/batteryadminlogin.jsp"); 
			}
		}
		/*This methos is used to get admin summary*/
		else if(strWhatToDo.equalsIgnoreCase("batteryadminhome"))
		{
           	try
			{  
				String strUserId ="admin";
				String strgetbatterybrands = "select count(distinct(bat_brand)) as count from battery_details";
				LogLevel.DEBUG(5,new Throwable(),"strgetbatterybrands :"+strgetbatterybrands);

				String strgetlaptopbatterybrands = "select count(distinct(battery_brand)) as count from laptop_battery_details";
				LogLevel.DEBUG(5,new Throwable(),"strgetlaptopbatterybrands :"+strgetlaptopbatterybrands);
                          
				Vector result1=qm.executeQuery(strgetbatterybrands);
				LogLevel.DEBUG(5,new Throwable(),"result1 :"+result1);

				Vector laptopbrandcount=qm.executeQuery(strgetlaptopbatterybrands);
				LogLevel.DEBUG(5,new Throwable(),"laptopbrandcount :"+laptopbrandcount);
				
				if(session.getAttribute("sesCountofBatterynrand")!=null)
				session.removeAttribute("sesCountofBatterynrand");
				session.setAttribute("sesCountofBatterynrand",result1 );

				if(session.getAttribute("sesLaptopBrandCount")!=null)
				session.removeAttribute("sesLaptopBrandCount");
				session.setAttribute("sesLaptopBrandCount",laptopbrandcount);

				session.setAttribute("sesBatteryAdminName",strUserId); 
				res.sendRedirect("/bookbattery/jsp/admin/batterystore/batteryadminmain.jsp");
				return;
			}
			catch(IOException ioe)
			{
				LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
				ioe.printStackTrace();
				session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
				res.sendRedirect("/bookbattery/jsp/admin/batterystore/batteryadminmain.jsp");
			}
			catch(Exception e)
			{
				LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
				e.printStackTrace();
				session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
				res.sendRedirect("/bookbattery/jsp/admin/batterystore/batteryadminmain.jsp");
			}
		}
		else if(strWhatToDo.equalsIgnoreCase("checkpassword"))
		  {
			String password = (req.getParameter("password")!=null)?(req.getParameter("password")):"";
			LogLevel.DEBUG(5,new Throwable(),"password :"+password );

			ServletOutputStream out=res.getOutputStream();
			System.out.println("ajaxmodelname called");

			try
			  {
				String strSqlQry = "SELECT accs_admin_passwd FROM adminaccess WHERE  accs_admin_passwd='"+password+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry :"+strSqlQry);
				
				Hashtable ht = qm.getRow(strSqlQry);
				String accs_admin_passwd=(String)ht.get("accs_admin_passwd");
				LogLevel.DEBUG(5,new Throwable(),"accs_admin_passwd :"+accs_admin_passwd);

				if(ht.isEmpty())
				 { 
					LogLevel.DEBUG(1,new Throwable(),"Password is not matched");
					out.println("<font color='#FF3333'>No Access for you!</font>");
					return;
				 }
				else
				 {
					LogLevel.DEBUG(1,new Throwable(),"");
					out.println("<font color='#FF0000'>Successfully accessed this option!</font>");
					return;
				  }
			  }
			 catch(IOException ioe)
			  {
					LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
					ioe.printStackTrace();
					out.println("problem Caught IOException");
			  }
			 catch(Exception e)
			  {
					LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					out.println("problem Caught Exception");
			  }
					out.flush();
					out.close();
			}

	}//End of dopost
}//end of Class