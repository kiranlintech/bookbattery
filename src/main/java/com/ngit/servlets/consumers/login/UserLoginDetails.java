/***********************************************************************		
	NGIT Confidential. 
	@File Name   : UserLoginDetails.java
	@Description : This Servlet is used to allow the user to Login
	@Date        : 03th April 2015
******************************************************************/ 
package com.ngit.servlets.consumers.login; 
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.mail.*;
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
import java.io.File;
/*
 * @author Sai Krishna Daddala.
 */
/* This class is to Admin Login. This class initializes the necessary connection pools and perform the transactions on the pools. */
public class UserLoginDetails extends HttpServlet 
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
		String struserName=(String)session.getAttribute("sesBatteryUserLogin"); 
		LogLevel.DEBUG(5,new Throwable(),"struserName :"+struserName );
		Properties propsMOPConfig = (Properties)context.getAttribute("contextPropMOPConfig"); 		
		 String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?(propsMOPConfig.getProperty("baseurldirectory")):"";

		if(propsMOPConfig==null)
		{
			LogLevel.DEBUG(1,new Throwable(),"Properties not loaded. So reloading" );
			try
			{
				String strMOPConfig = "/home/ngit/tomcat/webapps"+baseurldirectory+"properties/bookbatteryconfig.properties"; 
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
		if(strWhatToDo.equalsIgnoreCase("batteryuserlogin"))
		{
			String strUserEmailId = (req.getParameter("useremailid")!=null)?(req.getParameter("useremailid")):"";
			String strPassword = (req.getParameter("password")!=null)?(req.getParameter("password")):"";
			try
			{
				/*Query to get the Admin User name and Password*/
 				String strSqlQry = "SELECT email_id,password,name FROM batterywale_user_profile WHERE BINARY email_id='"+strUserEmailId+"' and BINARY password='"+strPassword+"'"; 
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry :"+strSqlQry );
				Hashtable ht = qm.getRow(strSqlQry);
				String srtname=String.valueOf(ht.get("name"));
				
				//String strSqlQryloy = "select sum(points_earned) as loyalitypointscount from user_loyalty_program where email_id='"+strUserEmailId+"' and reedem_flag='new'"; 
				String strSqlQryloy = "select sum(a.points_earned) as loyalitypointscount from  user_loyalty_program a,loyalty_program b where a.loyalty_id=b.loyalty_id and a.email_id='"+strUserEmailId+"' and a.merchant_flag='yes'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQryloy :"+strSqlQryloy );
				Hashtable htloyalitypointscount= qm.getRow(strSqlQryloy);
				String strloyalitypointscount=String.valueOf(htloyalitypointscount.get("loyalitypointscount"));
				if (strloyalitypointscount =="null")
				{
					strloyalitypointscount = "0";
				}
				else
				{
					strloyalitypointscount=strloyalitypointscount;
				}
				Float strloyalitypointscount1= Float.parseFloat(strloyalitypointscount);
				int loyaltyvalue= (int)Math.round(strloyalitypointscount1 * 10) / 10;
				String loyaltyvalueString = new Integer(loyaltyvalue).toString();
				LogLevel.DEBUG(5,new Throwable(),"loyaltyvalueString :"+loyaltyvalueString );

				if(ht.isEmpty())
				{
					LogLevel.DEBUG(1,new Throwable(),"Invalid User Name " +strUserEmailId);
					session.setAttribute("priority","1"); 
					session.setAttribute("sesErrorloginMsg", "<font color='#CC0000' class='vrb10'>Invalid Login!</font> ");
					res.sendRedirect("../jsp/login/login.jsp");
					return;
				}
				else
				{
					LogLevel.DEBUG(1,new Throwable(),"Login Successful ");
					session.setAttribute("sesBatteryUserLogin",strUserEmailId);
					session.setAttribute("sesloyaltyvalue",loyaltyvalueString);
					session.setAttribute("sesusername",srtname);
					//res.sendRedirect("../jsp/userloyalty/userloyalty.jsp");
					res.sendRedirect("../servlet/UserLoginDetails?hidWhatToDo=merchantLoyalty&emailid="+strUserEmailId+"");
					return;
				}
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace(); 
				res.sendRedirect("../jsp/admin/batterystore/batteryadminlogin.jsp");
			}
			catch(Exception e)
			{
				e.printStackTrace(); 
				res.sendRedirect("../jsp/admin/batterystore/batteryadminlogin.jsp"); 
			}
		}
		if(strWhatToDo.equalsIgnoreCase("merchantLoyalty"))
		{
			String stremailid= (req.getParameter("emailid")!=null)?(req.getParameter("emailid")):"0";
			LogLevel.DEBUG(5,new Throwable(),"stremailid:"+stremailid );
			Vector nameVector=new Vector();
			String pointsearned;
	   try
		 {
			ServletOutputStream out=res.getOutputStream();
			String strSqlfetchmerchantpoints = "select sum(a.points_earned) as count from  user_loyalty_program a,loyalty_program b where a.loyalty_id=b.loyalty_id and a.email_id='"+stremailid+"' and a.merchant_flag='yes'";
			LogLevel.DEBUG(5,new Throwable(),"strSqlfetchmerchantpoints:"+strSqlfetchmerchantpoints);
			Hashtable htmerchantpoints=qm.getRow(strSqlfetchmerchantpoints);	
			if( htmerchantpoints.isEmpty())
			{
				//if null then sending the result using session to jsp page
				pointsearned="0";
				LogLevel.DEBUG(1,new Throwable(),"There are No loyaltyprogram for this user");
				if(session.getAttribute("pointsearned")!=null)
				session.removeAttribute("pointsearned");
				session.setAttribute("pointsearned",pointsearned );	
				LogLevel.DEBUG(5,new Throwable(),"pointsearned :"+pointsearned );

				session.setAttribute("sesErrorMsg", "<font color='#483D8B' class='subtitle'>There are No loyaltyprogram for this retailer!</font> ");
				res.sendRedirect("../jsp/userloyalty/userloyalty.jsp");
			} 
			else
			{
				String merchantpointsearned=String.valueOf(htmerchantpoints.get("count"));
				if (merchantpointsearned =="null")
				{
					merchantpointsearned = "0";
				}
				else
				{
					merchantpointsearned=merchantpointsearned;
				}
				Float strloyalitypoints1= Float.parseFloat(merchantpointsearned);
				int valuemerchant= (int)Math.round(strloyalitypoints1 * 10) / 10;
				pointsearned = Integer.toString(valuemerchant);
				LogLevel.DEBUG(5,new Throwable(),"pointsearned :"+pointsearned );
				
				if(session.getAttribute("pointsearned")!=null)
				session.removeAttribute("pointsearned");
				LogLevel.DEBUG(5,new Throwable(),"pointsearned :"+pointsearned );
				session.setAttribute("pointsearned",pointsearned );	
				LogLevel.DEBUG(5,new Throwable(),"pointsearned :"+pointsearned );
				res.sendRedirect("../jsp/userloyalty/userloyalty.jsp");
			}
		 }
		 catch(IOException ioe)
			{
				ioe.printStackTrace(); 
				res.sendRedirect("../jsp/admin/batterystore/batteryadminlogin.jsp");
			}
			catch(Exception e)
			{
				e.printStackTrace(); 
				res.sendRedirect("../jsp/admin/batterystore/batteryadminlogin.jsp"); 
			}

		}
		else if(strWhatToDo.equalsIgnoreCase("forgotpassword"))
		{
			String strto=(req.getParameter("emailid")!=null)?(req.getParameter("emailid")):"";
			String SupportEmailId=(propsMOPConfig.getProperty("SupportEmailId")!=null)?(propsMOPConfig.getProperty("SupportEmailId")):"";
			String domainname=(propsMOPConfig.getProperty("domainname")!=null)?(propsMOPConfig.getProperty("domainname")):"";
			try
			{ 
				ServletOutputStream out=res.getOutputStream();

				String strThanks="Thanks & Regards,"+"\r\n"+"BookBattery Support Team.";
				String strSqlQry = "SELECT email_id,password,name FROM batterywale_user_profile WHERE email_id='"+strto+"'"; 
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry :"+strSqlQry );
				Hashtable ht = qm.getRow(strSqlQry);
				String strPassword=(String)ht.get("password");
				String strfname=(String)ht.get("name");
					
				if(ht.isEmpty())
				{ 
					LogLevel.DEBUG(5,new Throwable(),"Invalid EmailID " + strto);
					session.setAttribute("priority","1"); 
					out.println("Invalid EmailID");
					return;
				}
				else
				{
						String fname=strfname;
						if(fname.startsWith("&#"))
						{
							fname=strto;
						}
						else
						{
							fname=strfname;
						}
						MailTrans mtrans=new MailTrans();
						mtrans.setStrSmtpHost(domainname);
						mtrans.setStrFrom(SupportEmailId);
						mtrans.setStrTo(strto);
						mtrans.setStrSubject("BookBattery Password Recovery");
						String strMailMessage="Dear "+fname+",\nYour BookBattery account Password is: " +strPassword+"\r\n\r\n"+strThanks+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any errors,omissions,viruses etc in the contents  of  this message.";
						mtrans.setStrText(strMailMessage);
						Thread mt=new MailThread(mtrans,"");
						mt.start();
						out.println("Your Password has been Sent Successfully to your email-id");
				}
			}
			catch(IOException ioe)
			{
				LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
				ioe.printStackTrace();
				session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
				res.sendRedirect("../jsp/login/login.jsp");
			}
			catch(Exception e)
			{
				LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
				e.printStackTrace();
				session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
				res.sendRedirect("../jsp/login/login.jsp");
			}
		}
	}//End of dopost
}//end of Class