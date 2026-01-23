/* ********************************************************************\ 
	NGIT Pvt.Ltd. Confidential. 
	@File Name   : BrandLoyalty.java  
	@Description : To manage loyalty programs of brands
		
\* *******************************************************************/ 

 /* ******************************************************************\
* Package name
* Importing the required packages and predefined classes from them
\* ********************************************************************/
package com.ngit.servlets.admin.batterystore;
import com.ngit.javabean.admin.mis.AddBatteryTrans;
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;

import com.oreilly.servlet.MultipartRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FilenameUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.Vector;
import java.util.Hashtable;
import java.util.List;
import java.util.Enumeration;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.io.*;
import java.lang.*;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Manjunath G
 */

 /* ******************************************************************\
* This class is to Manage the BRand loyalty program. 
* This class initializes the necessary connection pools and perform the transactions on the pools. 
\* ********************************************************************/

public class BrandLoyalty extends HttpServlet 
{
	//declaring a private context
   	private ServletContext context;
	//declaring a variable of typr Query Manager
	QueryManager qm;
	String baseURL;

	/* ******************************************************************\
	* This init method initializes the necessary connection pools and perform the transactions on the pools from respectvie files. 
	\* ********************************************************************/

	public void init(ServletConfig config)throws ServletException
	{
		super.init(config);
		//calling method getServletConfig().
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

 
  		} catch(IOException ioe)
		{
			ioe.printStackTrace();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/* ******************************************************************\
	* This doGet service method calls doPost service method to handle all the requests and responses to manage brand loyalty program. 
	\* ********************************************************************/
	public void doGet (HttpServletRequest req , HttpServletResponse res )throws IOException , ServletException
	{
		//calling doPost method with parameters as req and res
		doPost(req, res);
	}
	/* ******************************************************************\
	* This doPost service method handles all the requests and responses passing from doGet service method to manage brand loyalty program. 
	\* ********************************************************************/
	public void doPost( HttpServletRequest req , HttpServletResponse res )throws IOException , ServletException
	{
		
			res.setContentType("text/html;charset=UTF-8");
        HttpSession session=req.getSession(true);
 		session=req.getSession(true);
		String struserName=(String)session.getAttribute("sesBatteryAdminName"); 
		LogLevel.DEBUG(5,new Throwable(),"struserName :"+struserName );
		String strvendorName= "Amaron";
		Properties propsMOPConfig = (Properties)context.getAttribute("contextPropMOPConfig"); 	
		String domainname =  propsMOPConfig.getProperty("domainname");
		String fromemailid =  propsMOPConfig.getProperty("SupportEmailId");
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
		//getting action parameter into string strWhatToDo
 		String strWhatToDo  = (req.getParameter("hidWhatToDo")!=null)?req.getParameter("hidWhatToDo"):"";
		LogLevel.DEBUG(5,new Throwable(),"strWhatToDo :"+strWhatToDo );
		ServletOutputStream out=res.getOutputStream();

		/* ****************************************************************************************\
			* This action is used to fetch loyalty programs.
		\* *****************************************************************************************/

		if(strWhatToDo.equalsIgnoreCase("loyaltyManagement"))
		{ 	
			try
			{
			
				//calling loyaltyManagement() method and storing the result in strRes String
				String strRes=loyaltyManagement(req ,res,session,strvendorName);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
		

			}catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		
		}

		/* ****************************************************************************************\
			* This action is used to fetch Loyalty program users.
		\* *****************************************************************************************/

		else if(strWhatToDo.equalsIgnoreCase("loyaltyManagement1"))
		{ 	
			try
			{
			
				//calling loyaltyManagement1() method and storing the result in strRes String
				String strRes=loyaltyManagement1 (req ,res,session,strvendorName);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);


			}catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		
		}	

		/* ****************************************************************************************\
			* This action is used to check the availability loyalty program name.
		\* *****************************************************************************************/

		else if(strWhatToDo.equalsIgnoreCase("loyaltyCheck"))
		{ 	
			try
			{
			
				//calling loyaltyCheck() method and storing the result in strRes String
				String strRes=loyaltyCheck (req ,res,session,strvendorName);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);



			}catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);

			}	
		
		}	

		/* ****************************************************************************************\
			* This action is used to fetch the loyalty partners of that particular brand.
		\* *****************************************************************************************/

		else if(strWhatToDo.equalsIgnoreCase("getPartners"))
		{ 	
			try
			{
			
				//calling getPartners() method and storing the result in strRes String
				String strRes=getPartners (req ,res,session,strvendorName);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				//Printing the result on to the console
				out.println(strRes);

			}catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);

			}	
		
		}	

		/* ****************************************************************************************\
			* This action is used to insert loyalty program in to loyalty_program table.
		\* *****************************************************************************************/

		else if(strWhatToDo.equalsIgnoreCase("addloyaltyprog"))
		{ 
			String strScriptPath = (propsMOPConfig.getProperty("THUMBNAIL_SCRIPT_PATH2")!=null)?(propsMOPConfig.getProperty("THUMBNAIL_SCRIPT_PATH2")).trim():"/home/ngit/tomcat/webapps/bookbattery/properties/thumbnail2.pl";
			LogLevel.DEBUG(5, new Throwable(), "strScriptPath:" + strScriptPath);
			//VirtualPath is declared as string to check whether VirtualPath2 is null or not
			String strVirtualPath = (propsMOPConfig.getProperty("VirtualPath")!=null)?(propsMOPConfig.getProperty("VirtualPath")).trim():"";
			LogLevel.DEBUG(5, new Throwable(), "strVirtualPath:" + strVirtualPath);
			//RelativePath is declared as string to check whether  RelativePath2 is null or not
			String strRelativePath = (propsMOPConfig.getProperty("RelativePathloy")!=null)?(propsMOPConfig.getProperty("RelativePathloy")).trim():"";
			LogLevel.DEBUG(5, new Throwable(), "strRelativePath:" + strRelativePath);
			//CMSServerIP is declared as string to check whether  CMS_SERVER_IP is null or not
            String strCMSServerIP = (propsMOPConfig.getProperty("CMS_SERVER_IP")!=null)?(propsMOPConfig.getProperty("CMS_SERVER_IP")).trim():"";
			LogLevel.DEBUG(5, new Throwable(), "strCMSServerIP:" + strCMSServerIP);
			try
			{
			
				//calling addloyaltyprog() method and storing the result in strRes String
				String strRes=addloyaltyprog (req ,res,session,strvendorName,strRelativePath,strVirtualPath,strScriptPath,strCMSServerIP);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				

			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);

			}	
		
		}	

		/* ****************************************************************************************\
			* This action is used to fetch loyalty partners of a particular loyalty program.
		\* *****************************************************************************************/

		else if(strWhatToDo.equalsIgnoreCase("getPartnerName"))
		{ 	
			try
			{
			
				//calling getPartnerName() method and storing the result in strRes String
				String strRes=getPartnerName (req ,res,session,strvendorName);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);

			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
	
			}	
		
		}	

		/* ****************************************************************************************\
			* This action is used to check the availability of the userid.
		\* *****************************************************************************************/

		else if(strWhatToDo.equalsIgnoreCase("userCheck"))
		{ 	
			try
			{
			
				//calling userCheck() method and storing the result in strRes String
				String strRes=userCheck (req ,res,session,strvendorName);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);


			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);

			}	
		
		}	

		/* ****************************************************************************************\
			* This action is used tp fetch the details of the loyalty program to be modified.
		\* *****************************************************************************************/

		else if(strWhatToDo.equalsIgnoreCase("modifyprogram"))
		{ 	
			try
			{
			
				//calling modifyprogram() method and storing the result in strRes String
				String strRes=modifyprogram (req ,res,session,strvendorName);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
			

			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);

			}	
		
		}	

		/* ****************************************************************************************\
			* This action is used to update the details of the loyalty program.
		\* *****************************************************************************************/

		else if(strWhatToDo.equalsIgnoreCase("modify"))
		{ 	
			String strScriptPath = (propsMOPConfig.getProperty("THUMBNAIL_SCRIPT_PATH2")!=null)?(propsMOPConfig.getProperty("THUMBNAIL_SCRIPT_PATH2")).trim():"/home/ngit/tomcat/webapps/bookbattery/properties/thumbnail2.pl";
			LogLevel.DEBUG(5, new Throwable(), "strScriptPath:" + strScriptPath);
			//VirtualPath is declared as string to check whether VirtualPath2 is null or not
			String strVirtualPath = (propsMOPConfig.getProperty("VirtualPath")!=null)?(propsMOPConfig.getProperty("VirtualPath")).trim():"";
			LogLevel.DEBUG(5, new Throwable(), "strVirtualPath:" + strVirtualPath);
			//RelativePath is declared as string to check whether  RelativePath2 is null or not
			String strRelativePath = (propsMOPConfig.getProperty("RelativePathloy")!=null)?(propsMOPConfig.getProperty("RelativePathloy")).trim():"";
			LogLevel.DEBUG(5, new Throwable(), "strRelativePath:" + strRelativePath);
			//CMSServerIP is declared as string to check whether  CMS_SERVER_IP is null or not
            String strCMSServerIP = (propsMOPConfig.getProperty("CMS_SERVER_IP")!=null)?(propsMOPConfig.getProperty("CMS_SERVER_IP")).trim():"";
			LogLevel.DEBUG(5, new Throwable(), "strCMSServerIP:" + strCMSServerIP);
			try
			{
			
				//calling modify() method and storing the result in strRes String
				String strRes=modify (req ,res,session,strvendorName,strRelativePath,strVirtualPath,strScriptPath,strCMSServerIP);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);


			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
		
			}	
		
		}	

		/* ****************************************************************************************\
			* This action is used to delete a loyalty program.
		\* *****************************************************************************************/

		else if(strWhatToDo.equalsIgnoreCase("deleteprogram"))
		{ 	
			try
			{
			
				//calling deleteprogram() method and storing the result in strRes String
				String strRes=deleteprogram (req ,res,session,strvendorName);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);


			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
		
			}	
		
		}	

		/* ****************************************************************************************\
			* This action is used to fetch the loyalty partners and make them checked .
		\* *****************************************************************************************/

		else if(strWhatToDo.equalsIgnoreCase("getUpdatePartners"))
		{ 	
			try
			{
			
				//calling getUpdatePartners() method and storing the result in strRes String
				String strRes=getUpdatePartners (req ,res,session,strvendorName);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);	

			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		
		}	

		/* ****************************************************************************************\
			* This action is used to insert loyalty points for a user in to user_loyalty_program table.
		\* *****************************************************************************************/

		else if(strWhatToDo.equalsIgnoreCase("addloyaltypoints"))
		{ 	
			try
			{
			
				//calling addloyaltypoints() method and storing the result in strRes String
				String strRes=addloyaltypoints (req ,res,session,strvendorName);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);


			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);

			}	
		
		}	

		/* ****************************************************************************************\
			* This action is used to fetch the loyalty programs available for that brand.
		\* *****************************************************************************************/

		else if(strWhatToDo.equalsIgnoreCase("getAjaxPrograms"))
		{ 	
			try
			{
			
				//calling getAjaxPrograms() method and storing the result in strRes String
				String strRes=getAjaxPrograms (req ,res,session,strvendorName);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
					out.println(strRes);

			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		
		}	

		/* ****************************************************************************************\
			* This action is used to fetch the details of the user to be modified.
		\* *****************************************************************************************/

		else if(strWhatToDo.equalsIgnoreCase("modifypoints"))
		{ 	
			try
			{
			
				//calling modifypoints() method and storing the result in strRes String
				String strRes=modifypoints (req ,res,session,strvendorName);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);

			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		
		}	

		/* ****************************************************************************************\
			* This action is used to update the user details.
		\* *****************************************************************************************/

		else if(strWhatToDo.equalsIgnoreCase("updateloyaltypoints"))
		{ 	
			try
			{
			
				//calling updateloyaltypoints() method and storing the result in strRes String
				String strRes=updateloyaltypoints (req ,res,session,strvendorName);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);

			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		
		}	

		/* ****************************************************************************************\
			* This action is used to fetch the selected programs of a particular user and make them checked.
		\* *****************************************************************************************/

		else if(strWhatToDo.equalsIgnoreCase("getSelProgram"))
		{ 	
			try
			{
			
				//calling getSelProgram() method and storing the result in strRes String
				String strRes=getSelProgram (req ,res,session,strvendorName);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);

			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		
		}	

		/* ****************************************************************************************\
			* This action is used to fetch points and value of a particular loyalty program.
		\* *****************************************************************************************/

		else if(strWhatToDo.equalsIgnoreCase("getTextBox"))
		{ 	
			try
			{
			
				//calling getTextBox() method and storing the result in strRes String
				String strRes=getTextBox (req ,res,session,strvendorName);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);
		
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		
		}	

		/* ****************************************************************************************\
			* This action is used to fetch loyalty program names in to a dropdown.
		\* *****************************************************************************************/

		else if(strWhatToDo.equalsIgnoreCase("getLoyaltyNames"))
		{ 	
			try
			{
			
				//calling getLoyaltyNames() method and storing the result in strRes String
				String strRes=getLoyaltyNames (req ,res,session,strvendorName);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);

			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		
		}	

		/* ****************************************************************************************\
			* This action is used to delete a loyalty program user.
		\* *****************************************************************************************/

		else if(strWhatToDo.equalsIgnoreCase("deleteuser"))
		{ 	
			try
			{
			
				//calling deleteuser() method and storing the result in strRes String
				String strRes=deleteuser (req ,res,session,strvendorName);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);

			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		
		}	

		/* ****************************************************************************************\
			* This action is used to redeem the points and values of a particular user.
		\* *****************************************************************************************/

		else if(strWhatToDo.equalsIgnoreCase("redeempoints"))
		{ 	
			try
			{
			
				//calling redeempoints() method and storing the result in strRes String
				String strRes=redeempoints (req ,res,session,strvendorName);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);

			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		
		}
		
		/* ****************************************************************************************\
			* This action is used to redeem points of a user.
		\* *****************************************************************************************/


		else if(strWhatToDo.equalsIgnoreCase("redeemloyaltypoints"))
		{ 	
			try
			{
			
				//calling redeemloyaltypoints() method and storing the result in strRes String
				String strRes=redeemloyaltypoints (req ,res,session,strvendorName);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);

			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		
		}	

		/* ****************************************************************************************\
			* This action is used to fetch the partners of a loyalty program.
		\* *****************************************************************************************/

		else if(strWhatToDo.equalsIgnoreCase("getSelPartner"))
		{ 	
			try
			{
			
				//calling getSelPartner() method and storing the result in strRes String
				String strRes=getSelPartner (req ,res,session,strvendorName);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);

			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		
		}

		/* ****************************************************************************************\
			* This action is used to check the availability of the userid.
		\* *****************************************************************************************/

		else if(strWhatToDo.equalsIgnoreCase("loyaltyusercheck"))
		{ 	
			try
			{
			
				//calling loyaltyusercheck() method and storing the result in strRes String
				String strRes=loyaltyusercheck (req ,res,session,strvendorName);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);

			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		
		}

		/* ****************************************************************************************\
			* This action is used to check the availability of the userid.
		\* *****************************************************************************************/

		else if(strWhatToDo.equalsIgnoreCase("funonclickprog"))
		{ 	
			try
			{
			
				//calling funonclickprog() method and storing the result in strRes String
				String strRes=funonclickprog (req ,res,session,strvendorName);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);

			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		
		}
		/* ****************************************************************************************\
			* This action is used to check the availability of the userid.
		\* *****************************************************************************************/

		else if(strWhatToDo.equalsIgnoreCase("loyaltyManagement2"))
		{ 	
			try
			{
			
				//calling loyaltyManagement2() method and storing the result in strRes String
				String strRes=loyaltyManagement2 (req ,res,session,strvendorName);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);

			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);

			}	
		
		}
	else if(strWhatToDo.equalsIgnoreCase("recharge"))
	{ 	
		String pagenumber = (req.getParameter("pagenumber")!=null)?(req.getParameter("pagenumber")):"0";
		String strDateOpt  = (req.getParameter("dates")!=null)?req.getParameter("dates"):"";
		String txtFromDate  = (req.getParameter("txtFromDate")!=null)?req.getParameter("txtFromDate"):"";
		String txtToDate  = (req.getParameter("txtToDate")!=null)?req.getParameter("txtToDate"):"";
		try
		{
			List<Hashtable> result=fetchserviceproviders(req ,res,session);
			if(result!=null || result.size()>0)
			{
				if(session.getAttribute("result")!=null)
				{
					session.removeAttribute("result");
				}
				session.setAttribute("result",result);
			}
			res.sendRedirect("../jsp/admin/batterystore/brandloyaltyprogram/serviceprovider.jsp?dates="+strDateOpt+"&txtFromDate="+txtFromDate+"&txtToDate="+txtToDate+"&pagenumber="+pagenumber);
		}catch (Exception e)
		{										
			LogLevel.ERROR(1, e, "Error :" + e);
			out.println("-1");
			out.close();
		}	
	}
		if(strWhatToDo.equalsIgnoreCase("fetchreedempointstorecharge"))
	{ 	
		String pagenumber = (req.getParameter("pagenumber")!=null)?(req.getParameter("pagenumber")):"0";
		String strDateOpt  = (req.getParameter("dates")!=null)?req.getParameter("dates"):"";
		String txtFromDate  = (req.getParameter("txtFromDate")!=null)?req.getParameter("txtFromDate"):"";
		String txtToDate  = (req.getParameter("txtToDate")!=null)?req.getParameter("txtToDate"):"";
		try
		{
			String strRes=fetchreedempointstorecharge(req ,res,session);
			out.println(strRes);
			out.close();
		}catch (Exception e)
		{										
			LogLevel.ERROR(1, e, "Error :" + e);
			out.println("-1");
			out.close();
		}	
	}
	else if(strWhatToDo.equalsIgnoreCase("deleterechargeforreedempoints"))
	{ 	
			String serviecprovider = (req.getParameter("serviecprovider")!=null)?(req.getParameter("serviecprovider")):"0";
			String pagenumber = (req.getParameter("pagenumber")!=null)?(req.getParameter("pagenumber")):"0";
			String strDateOpt  = (req.getParameter("date")!=null)?req.getParameter("date"):"";
			String txtFromDate  = (req.getParameter("txtFromDate")!=null)?req.getParameter("txtFromDate"):"";
			String txtToDate  = (req.getParameter("txtToDate")!=null)?req.getParameter("txtToDate"):"";
			try
			{
				int result = deleterechargeforreedempoints(req ,res,session);
				if(result <0)
				{
					
					session.setAttribute("priority","1");
					res.sendRedirect("../jsp/admin/batterystore/brandloyaltyprogram/serviceprovider.jsp?pagenumber="+pagenumber);
					return;
				}
				else
				{
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#2364b1' class='vrb10'>Successfully deleted reedem points to recharge!</font> ");
					res.sendRedirect("../jsp/admin/batterystore/brandloyaltyprogram/serviceprovider.jsp?pagenumber="+pagenumber+"&dates="+strDateOpt+"&txtFromDate="+txtFromDate+"&txtToDate="+txtToDate+"&serviecprovider="+serviecprovider);
					return;
				}
			}catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
				out.println("-1");
				out.close();
			}	
	}
	else if(strWhatToDo.equalsIgnoreCase("rechargeforreedempoints"))
	{ 	
			String serviecprovider = (req.getParameter("serviecprovider")!=null)?(req.getParameter("serviecprovider")):"0";
			String pagenumber = (req.getParameter("pagenumber")!=null)?(req.getParameter("pagenumber")):"0";
			String strDateOpt  = (req.getParameter("date")!=null)?req.getParameter("date"):"";
			String txtFromDate  = (req.getParameter("txtFromDate")!=null)?req.getParameter("txtFromDate"):"";
			String txtToDate  = (req.getParameter("txtToDate")!=null)?req.getParameter("txtToDate"):"";
			try
			{
				int result = rechargeforreedempoints(req ,res,session);
				if(result <0)
				{
					session.setAttribute("priority","1");
					res.sendRedirect("../jsp/admin/batterystore/brandloyaltyprogram/serviceprovider.jsp?pagenumber="+pagenumber);
					return;
				}
				else
				{
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#2364b1' class='vrb10'>Successfully recharged!</font> ");
					res.sendRedirect("../jsp/admin/batterystore/brandloyaltyprogram/serviceprovider.jsp?pagenumber="+pagenumber+"&dates="+strDateOpt+"&txtFromDate="+txtFromDate+"&txtToDate="+txtToDate+"&serviecprovider="+serviecprovider);
					return;
				}
			}catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
				out.println("-1");
				out.close();
			}	
	}
	else if(strWhatToDo.equalsIgnoreCase("checkrechargedetails"))
	{ 	
	 try
	 {
			String strRes=checkrechargedetails(req ,res,session);
			out.println(strRes);
			out.close();
	 }
	 catch (Exception e)
	 {										
		out.println("-1");
		out.close();
	 }	

	}

	}//end of doPost method.

/* **************************************************************************************************************************************\
* This method is used to fetch loyalty program, points, value from loyalty_program table..
* @VendIdQry : carries the query to fetch vendor_id, vendor_name from vendor_master table.
* @loyalNameQry : carries the query to fetch loyalty_name, loyalty_points, value from loyalty_program table.
* @countQry : carries the query to fetch count of loyalty_name from loyalty_program table based on vendor_name.
\* **************************************************************************************************************************************/

public String loyaltyManagement (HttpServletRequest req , HttpServletResponse res,HttpSession session, String strvendorName)
{

		//if pagenumner paramenter is not null then stored in the string if its null then '0' is stored in the string
		String pagenumber = (req.getParameter("pagenumber")!=null)?(req.getParameter("pagenumber")):"0";
		LogLevel.DEBUG(5,new Throwable(),"pagenumber:"+pagenumber );
		
		//if pagetype paramenter is not null then stored in the string if its null then '0' is stored in the string
		String pagetype = (req.getParameter("pagetype")!=null)?(req.getParameter("pagetype")):"0";
		LogLevel.DEBUG(5,new Throwable(),"pagetype:"+pagetype );
		
		//if lastpage paramenter is not null then stored in the string if its null then '0' is stored in the string	
		String lastpage = (req.getParameter("lastpage")!=null)?(req.getParameter("lastpage")):"0";
		LogLevel.DEBUG(5,new Throwable(),"lastpage:"+lastpage );
		
				
		try
		{
			//checking if lastpage is equals to '0' or not
			if(lastpage.equals("0"))
			{
				//if yes then setting lastpage as '1'.
				lastpage="1";
			}
			//checking if pagetype is equals to first or not	
			if(pagetype.equals("first"))
			{
				//if yes then setting pagenumber as '1'.
				pagenumber="1"; 		
			}
			//checking if pagetype is equals to previous or not	
			if(pagetype.equals("previous"))
			{
				//if yes then setting pagenumber as one reduced by pagenumber
				int pn=Integer.parseInt(pagenumber)-1;
				pagenumber=Integer.toString(pn);
			}
			//checking if pagetype is equals to next or not	
			if(pagetype.equals("next"))
			{
				//if yes then setting pagenumber as one increased by pagenumber
				int pn=Integer.parseInt(pagenumber)+1;
				pagenumber=Integer.toString(pn);				
			}
			//checking if pagetype is equals to last or not	
			if(pagetype.equals("last"))
			{
				//if yes then setting pagenumber as lastpage
				pagenumber=lastpage;
			}
			//checking if pagetype is equals to '0' or not	
			if(pagenumber.equals("0"))
			{
				//if yes then setting pagenumber as '1'
				pagenumber="1";
			}
			
			//declaring a variable of type int and initializing to 10.
			int Opinions_per_page=10;
			//declaring and initializing a variable startlimit
			int Startlimt=Opinions_per_page*(Integer.parseInt(pagenumber)-1);
			
			//Declaring a startindex String and initializing with the value of startlimit.
			String Startindex=Integer.toString(Startlimt);
 			LogLevel.DEBUG(5,new Throwable(),"Startindex:"+Startindex );
			
			//Query to fetch vendor id from vendor_master
			String VendIdQry = "SELECT vendor_id FROM loyalty_program WHERE vendor_name='"+strvendorName+"'"; 
			LogLevel.DEBUG(5,new Throwable(),"VendIdQry :"+VendIdQry );
			
			//Getting the result of the query in to a hashtable.
			Hashtable Vendht = qm.getRow(VendIdQry);
			//getting the value from hashtable using key 'vendor_id'
			String strvendorid=String.valueOf(Vendht.get("vendor_id"));
			LogLevel.DEBUG(5,new Throwable(),"strvendorid :"+strvendorid );	
			
			//Query to fetch loyalty id, loyalty name, loyalty points and value from loyalty_program table
			String loyalNameQry = "SELECT  loyalty_id,loyalty_name,loyalty_points,value FROM loyalty_program where vendor_id='"+strvendorid+"' limit "+Startindex+",10"; 
			LogLevel.DEBUG(5,new Throwable(),"loyalNameQry :"+loyalNameQry );
 			
			//getting the result into a vector
			Vector result=qm.executeQuery(loyalNameQry);
			LogLevel.DEBUG(5,new Throwable(),"result :"+result );
			
			//Query to fetch count of loyalty programs for that particular vendor.
			String countQry="select count(*)as count from loyalty_program WHERE vendor_id='"+strvendorid+"'";
			LogLevel.DEBUG(5,new Throwable(),"countQry :"+countQry );
			
			//Storing the result of the query in a vector.
			Vector Count=qm.executeQuery(countQry);
			LogLevel.DEBUG(5,new Throwable(),"Count :"+Count );

			//checking if result vector is null or not
			if(result == null || result.size()<=0)
			{
					//if null then sending an error message using sessions to the jsp page
					LogLevel.DEBUG(1,new Throwable(),"Failed to fetch account details ");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'>Failed to fetch account details!</font> ");
					session.removeAttribute("sesLoyaltyVector");
					session.removeAttribute("sesLoyaltyCount");
					res.sendRedirect("../jsp/admin/batterystore/brandloyaltyprogram/brandloyaltymanage.jsp?pagenumber="+pagenumber);
				
			}
			else
			{
					//if not null then sending the result using session to jsp page
					LogLevel.DEBUG(1,new Throwable(),"Successfully fetched account details ");
					session.setAttribute("sesErrorMsg", "");
					if(session.getAttribute("sesLoyaltyVector")!=null)
					session.removeAttribute("sesLoyaltyVector");
					session.setAttribute("sesLoyaltyVector", result);
					if(session.getAttribute("sesLoyaltyCount")!=null)
					session.removeAttribute("sesLoyaltyCount");
					session.setAttribute("sesLoyaltyCount",Count);
					res.sendRedirect("../jsp/admin/batterystore/brandloyaltyprogram/brandloyaltymanage.jsp?pagenumber="+pagenumber);
					
			}
		}catch(IOException ioe)
		{
				//If any exceptionsa re found in try block, this block will catch those exception
				LogLevel.ERROR(0,ioe,"problem Caught IOException if(submit) !! "+ioe);
				//printstacktrace will print the line where exception is being arised
				ioe.printStackTrace();
				session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
		}catch(Exception e)
		{
				//If any exceptionsa re found in try block, this block will catch those exception
				LogLevel.ERROR(0,e,"Problem Caught Exception if(submit)!! "+e);
				//printstacktrace will print the line where exception is being arised
				e.printStackTrace();
				session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
		}
		//returning the result to the place where this method is called.
		return null;
			
}

/* **************************************************************************************************************************************\
* This method is used to fetch emailid from user_loyalty_program table..
* @VendIdQry : carries the query to fetch vendor_id, vendor_name from vendor_master table.
* @partnrQry : carries the query to fetch partners_list, loyalty_name from user_loyalty_program table. 
* @emailidQry : carries the query to fetch email_id from user_loyalty_program table
* @countQry : carried the query to fetch the count of all the rows in user_loyalty_program table based on vendor_name.
\* **************************************************************************************************************************************/


public String loyaltyManagement1 (HttpServletRequest req , HttpServletResponse res,HttpSession session,String strvendorName)
{
		//if pagenumner paramenter is not null then stored in the string if its null then '0' is stored in the string
		String pagenumber = (req.getParameter("pagenumber")!=null)?(req.getParameter("pagenumber")):"0";
		LogLevel.DEBUG(5,new Throwable(),"pagenumber:"+pagenumber );
		
		//if pagetype paramenter is not null then stored in the string if its null then '0' is stored in the string
		String pagetype = (req.getParameter("pagetype")!=null)?(req.getParameter("pagetype")):"0";
		LogLevel.DEBUG(5,new Throwable(),"pagetype:"+pagetype );
		
		//if lastpage paramenter is not null then stored in the string if its null then '0' is stored in the string	
		String lastpage = (req.getParameter("lastpage")!=null)?(req.getParameter("lastpage")):"0";
		LogLevel.DEBUG(5,new Throwable(),"lastpage:"+lastpage );
		
		
			
		try
		{
			//checking if lastpage is equals to '0' or not
			if(lastpage.equals("0"))
			{
				//if yes then setting lastpage as '1'.
				lastpage="1";
			}
			//checking if pagetype is equals to first or not	
			if(pagetype.equals("first"))
			{
				//if yes then setting pagenumber as '1'.
				pagenumber="1"; 		
			}
			//checking if pagetype is equals to previous or not	
			if(pagetype.equals("previous"))
			{
				//if yes then setting pagenumber as one reduced by pagenumber
				int pn=Integer.parseInt(pagenumber)-1;
				pagenumber=Integer.toString(pn);
			}
			//checking if pagetype is equals to next or not	
			if(pagetype.equals("next"))
			{
				//if yes then setting pagenumber as one increased by pagenumber
				int pn=Integer.parseInt(pagenumber)+1;
				pagenumber=Integer.toString(pn);				
			}
			//checking if pagetype is equals to last or not	
			if(pagetype.equals("last"))
			{
				//if yes then setting pagenumber as lastpage
				pagenumber=lastpage;
			}
			//checking if pagetype is equals to '0' or not	
			if(pagenumber.equals("0"))
			{
				//if yes then setting pagenumber as '1'
				pagenumber="1";
			}
			
			//declaring a variable of type int and initializing to 10.
			int Opinions_per_page=10;
			//declaring and initializing a variable startlimit
			int Startlimt=Opinions_per_page*(Integer.parseInt(pagenumber)-1);

			//Declaring a startindex String and initializing with the value of startlimit.
			String Startindex=Integer.toString(Startlimt);
 			LogLevel.DEBUG(5,new Throwable(),"Startindex:"+Startindex );

			//Query used to fetch vendot id from vendor_master table
			String VendIdQry = "SELECT vendor_id FROM loyalty_program WHERE vendor_name='"+strvendorName+"'"; 
			LogLevel.DEBUG(5,new Throwable(),"VendIdQry :"+VendIdQry );
			
			//Getting the result of the query in to a hashtable.
			Hashtable vendht = qm.getRow(VendIdQry);
			//Getting the value from hashtable using the key 'vendor_id'
			String strvendorid=String.valueOf(vendht.get("vendor_id"));
			LogLevel.DEBUG(5,new Throwable(),"strvendorid :"+strvendorid );
			
			//Query used to fetch email_id, loyalty_id from user_loyalty_program table based on vendor_id
			String emailidQry = "SELECT distinct (email_id) FROM user_loyalty_program where vendor_id='"+strvendorid+"' limit "+Startindex+",10"; 
			LogLevel.DEBUG(5,new Throwable(),"emailidQry :"+emailidQry);

			//Getting the result of the query into vector
			Vector result=qm.executeQuery(emailidQry);
			LogLevel.DEBUG(5,new Throwable(),"result :"+result);
			
			//Query used to fetch email_id, loyalty_id from user_loyalty_program table based on vendor_id
			String loyalidQry = "SELECT loyalty_id FROM user_loyalty_program where vendor_id='"+strvendorid+"' limit "+Startindex+",10"; 
			LogLevel.DEBUG(5,new Throwable(),"loyalidQry :"+loyalidQry);

			//Getting the result of the query into vector
			Vector result1=qm.executeQuery(loyalidQry);
			LogLevel.DEBUG(5,new Throwable(),"result1 :"+result1);

			//Query used for getting count o the users in user_loyalty_program table
			String countQry="select count(distinct(email_id))as count from user_loyalty_program WHERE vendor_id='"+strvendorid+"' and merchant_flag='yes'";
			LogLevel.DEBUG(5,new Throwable(),"countQry :"+countQry );

			//Getting the result of the query in to vector
			Vector Count=qm.executeQuery(countQry);
			LogLevel.DEBUG(5,new Throwable(),"Count :"+Count );

			//if null then sending an error message using sessions to the jsp page
			if(result == null || result.size()<=0)
			{
					LogLevel.DEBUG(1,new Throwable(),"Failed to fetch account details ");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'>The user you have Entered Already Holds the same Program</font> ");
					session.removeAttribute("sesLoyaltyPointsVector1");
					session.removeAttribute("sesLoyaltyPointsCount1");
					res.sendRedirect("../jsp/admin/batterystore/brandloyaltyprogram/brandusermanage.jsp?pagenumber="+pagenumber);
					
			}
			else
			{
					//if not null then sending the result using session to jsp page
					LogLevel.DEBUG(1,new Throwable(),"Successfully fetched account details ");
					session.setAttribute("sesErrorMsg", "");
					if(session.getAttribute("sesLoyaltyPointsVector1")!=null)
					session.removeAttribute("sesLoyaltyPointsVector1");
					session.setAttribute("sesLoyaltyPointsVector1", result);

					LogLevel.DEBUG(1,new Throwable(),"Successfully fetched account details ");
					session.setAttribute("sesErrorMsg", "");
					if(session.getAttribute("sesloyalid")!=null)
					session.removeAttribute("sesloyalid");
					session.setAttribute("sesloyalid", result1);
		
					if(session.getAttribute("sesLoyaltyPointsCount1")!=null)
					session.removeAttribute("sesLoyaltyPointsCount1");
					session.setAttribute("sesLoyaltyPointsCount1",Count);
					res.sendRedirect("../jsp/admin/batterystore/brandloyaltyprogram/brandusermanage.jsp?pagenumber="+pagenumber);
					
			}
		}catch(IOException ioe)
		{
				//If any exceptionsa re found in try block, this block will catch those exception
				LogLevel.ERROR(0,ioe,"problem Caught IOException if(submit) !! "+ioe);
				//printstacktrace will print the line where exception is being arised
				ioe.printStackTrace();
				session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
		}catch(Exception e)
		{
				//If any exceptionsa re found in try block, this block will catch those exception
				LogLevel.ERROR(0,e,"Problem Caught Exception if(submit)!! "+e);
				//printstacktrace will print the line where exception is being arised
				e.printStackTrace();
				session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
		}
		//returning the result to the place where this method is called.
			return null;
 }



/* **************************************************************************************************************************************\
* This method is used to check the availability of loaylty program name from loyalty_program table..
* @loyalNameQry : carries the query to check the availability of loyalty_name from loyalty_program table.
\* **************************************************************************************************************************************/

public String loyaltyCheck (HttpServletRequest req , HttpServletResponse res,HttpSession session, String strvendorName)
{
		//if loyaltyname parameter is not null then its value is passed in to the String, if its null then null is store in String
		String strprogramname = (req.getParameter("loyaltyname")!=null)?(req.getParameter("loyaltyname")):"";
		LogLevel.DEBUG(5,new Throwable(),"strprogramname :"+strprogramname);

		
		//Printing that ajax model is called
		System.out.println("ajaxmodelname called");

		try
		{ 
			ServletOutputStream out=res.getOutputStream();
			//Query to fetch loyalty_name from loyalty_program table.
 			String loyalNameQry = "SELECT loyalty_name FROM loyalty_program WHERE loyalty_name='"+strprogramname+"'"; 
			LogLevel.DEBUG(5,new Throwable(),"loyalNameQry :"+loyalNameQry );
			//Getting the result in to hashtable
			Hashtable nameht = qm.getRow(loyalNameQry);
			
			//Checking if hashtable is empty or not
			if(nameht.isEmpty())
			{ 
				//If hashtbale is empty then no loyalty program exist
				LogLevel.DEBUG(1,new Throwable(),"No Name exists u can continue " +strprogramname);
				out.println("No Loyalty Name exists u can continue");
					
			}
			else
			{
				//if hashtable is not empty then loyalty program exist
				LogLevel.DEBUG(1,new Throwable(),"");
				out.println("Loyalty Name already exists");
					
			}
		}catch(IOException ioe)
		{ 
				//If any exceptionsa re found in try block, this block will catch those exception
				LogLevel.ERROR(0,ioe,"problem Caught IOException if(submit) !! "+ioe);
				//printstacktrace will print the line where exception is being arised
				ioe.printStackTrace();
				session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
		}catch(Exception e)
		{
				//If any exceptionsa re found in try block, this block will catch those exception
				LogLevel.ERROR(0,e,"Problem Caught Exception if(submit)!! "+e);
				//printstacktrace will print the line where exception is being arised
				e.printStackTrace();
				session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
		}
		//returning the result to the place where this method is called.
		return null;
 }

/* **************************************************************************************************************************************\
* This method is used to fetch all the platinum brands as loyalty partners from vendor_master table..
* @platnmVendQry : carries the query to vendor_id, vendor_name from vendor_master table based on merchant_category
\* **************************************************************************************************************************************/

public String getPartners (HttpServletRequest req , HttpServletResponse res,HttpSession session, String strvendorName)
{
	//printing that ajax model name is called	 
	System.out.println("ajaxmodelname called");

	String pagenumber = (req.getParameter("pagenumber")!=null)?(req.getParameter("pagenumber")):"0";
	LogLevel.DEBUG(5,new Throwable(),"pagenumber:"+pagenumber );

	String pagetype = (req.getParameter("pagetype")!=null)?(req.getParameter("pagetype")):"0";
	LogLevel.DEBUG(5,new Throwable(),"pagetype:"+pagetype );

	String lastpage = (req.getParameter("lastpage")!=null)?(req.getParameter("lastpage")):"0";
	LogLevel.DEBUG(5,new Throwable(),"lastpage:"+lastpage );

	String selbrand = (req.getParameter("selbrand")!=null)?(req.getParameter("selbrand")):"";
	LogLevel.DEBUG(5,new Throwable(),"selbrand:"+selbrand );
	
	String selectedbrand=selbrand;
	String strRes="";
	try
	{	
		String strselected="";
		String strpermisssion="";
		String strselectedbrand="";
		String strTempValue="";
		
		if(lastpage.equals("0"))
		{
			lastpage="1";
		}
		if(pagetype.equals("first"))
		{
			pagenumber="1"; 
		}
		if(pagetype.equals("previous"))
		{
			int pn=Integer.parseInt(pagenumber)-1;
			pagenumber=Integer.toString(pn);
		}
		if(pagetype.equals("next"))
		{
			int pn=Integer.parseInt(pagenumber)+1;
			pagenumber=Integer.toString(pn);
			LogLevel.DEBUG(5,new Throwable(),"test:"+pagenumber );

		}
		if(pagetype.equals("last"))
		{
			pagenumber=lastpage;
		}
		if(pagenumber.equals("0"))
		{
			pagenumber="1";
		}
		int Opinions_per_page=10;

		int Startlimt=Opinions_per_page*(Integer.parseInt(pagenumber)-1);
		String Startindex=Integer.toString(Startlimt);
		LogLevel.DEBUG(5,new Throwable(),"Startindex:"+Startindex );

		ServletOutputStream out = res.getOutputStream();
		
		String strSqlQry=" select merchant_category_name  from merchant_category where type like '%Loyalty%'";
		LogLevel.DEBUG(5,new Throwable(),"strSqlQry to get the loyalty category :"+strSqlQry );

		ArrayList htcategory=new ArrayList();
		htcategory=qm.getField(strSqlQry);
		String category="";
		for(int i=0;i<htcategory.size();i++)
		{
			if(category.equals(""))
			category=htcategory.get(i).toString();
			else
			category=category+"','"+htcategory.get(i).toString();
		}
		LogLevel.DEBUG(5, new Throwable(), "category :" + category);

		String Vendorname="select vendor_name from loyalty_program where vendor_name='"+strvendorName+"'";
		LogLevel.DEBUG(5,new Throwable(),"Vendorname :"+Vendorname );
		Hashtable getVendorname = qm.getRow(Vendorname);
		//Getting the values from hashtbale using keys in hashtable.
		String strgetVendorname=(String)getVendorname.get("vendor_name");
		LogLevel.DEBUG(5,new Throwable(),"strgetVendorname :"+strgetVendorname );

		//String strgetBrandname=(String)getVendorname.get("brandname");
		//LogLevel.DEBUG(5,new Throwable(),"strgetBrandname :"+strgetBrandname );

		// The strSqlQry1 query is used to fetch the retailer details from retailers table based on retailer_id //
		String platnmVendQry = "select vendor_id,vendor_name, brandname from vendor_master where merchant_category in('"+category+"') and brandname='"+strvendorName+"' order by brandname asc limit "+Startindex+",10";
		LogLevel.DEBUG(5,new Throwable(),"platnmVendQry :"+platnmVendQry );
	
		String strSqlQry1="select brandname from vendor_master where merchant_category in('"+category+"') and brandname='"+strvendorName+"' order by brandname asc limit "+Startindex+",10";
		LogLevel.DEBUG(5,new Throwable(),"strSqlQry to get the loyalty category 123:"+strSqlQry1 );

		ArrayList htcategorybrand=new ArrayList();
		htcategorybrand=qm.getField(strSqlQry1);
		String categorybrands="";
		for(int i=0;i<htcategorybrand.size();i++)
		{
			categorybrands=htcategorybrand.get(i).toString();
			String comm=",";
			String categorybrands1=categorybrands.concat(comm);
			selbrand = selbrand.replace(categorybrands1,"");
			selbrand = selbrand.replace(categorybrands,"");
		}
		LogLevel.DEBUG(5, new Throwable(), "selbrand bhanu :" + selbrand);
		
		//The below strSqlQry2 query is used to fetch the total count for the above strSqlQry1 query //
		String strSqlQry2 = "select count(*) as count from (select vendor_id,vendor_name, brandname from vendor_master where brandname='"+strvendorName+"' and merchant_category in('"+category+"'))as T";
		LogLevel.DEBUG(5,new Throwable(),"strSqlQry2 getting count:"+strSqlQry2 );
		
		//The htRetailersCount hash table is used to store the result of strSqlQry2.//
		Hashtable htbrandsCount = qm.getRow(strSqlQry2);
		String strcount=String.valueOf(htbrandsCount.get("count"));
		LogLevel.DEBUG(5,new Throwable(),"strcount :"+strcount );
		
		//The VendorVector is used to store the result of the strSqlQry1.//
		Vector VendorVector=qm.executeQuery(platnmVendQry);
		LogLevel.DEBUG(5,new Throwable(),"VendorVector :"+VendorVector);
		
		int TotalCount=Integer.parseInt(strcount);
		LogLevel.DEBUG(5,new Throwable(),"TotalCount:"+TotalCount );
		int opinions_per_page=10;
		LogLevel.DEBUG(5,new Throwable(),"opinions_per_page:"+opinions_per_page );
		int tp=((int)(Math.ceil((double)TotalCount/opinions_per_page)));
		LogLevel.DEBUG(5,new Throwable(),"tp:"+tp );

		String Lastpage=Integer.toString(tp);
		LogLevel.DEBUG(5,new Throwable(),"testlastpage:"+Lastpage );

		//pagination starts from here//

		strRes=strRes+"<tr class='insidecontent'><td colspan='8' align='center'><table border='0' width='100%'><tr><td width='45%' align='left'> ";

		if(pagenumber.equals("1"))
		{
			strRes=strRes+"<font class='blue2'>First</font>&nbsp;&nbsp;";
			strRes=strRes+"<font class='blue2'>Previous</font>&nbsp;&nbsp;";	
		}
		else
		{		
			strRes=strRes+"<font ><a class='blue1' href=\"javascript:funOnClickFirstbrands('"+pagenumber+"','"+selbrand+"');\">First</a></font>&nbsp;&nbsp;";
			strRes=strRes+"<font> <a class='blue1' href=\"javascript:funOnClickPreviousbrands('"+pagenumber+"','"+selbrand+"');\" > Previous</a> </font>&nbsp;&nbsp;";
		}
		
		if(pagenumber.equals(Lastpage))
		{
			strRes=strRes+"&nbsp;&nbsp;<font class='blue2'>Next</font>&nbsp;&nbsp;";
			strRes=strRes+"<font class='blue2'>Last</font>&nbsp;&nbsp;";
		}
		else
		{
			strRes=strRes+"&nbsp;&nbsp;<font><a class='blue1' href=\"javascript:funOnClickNextbrands('"+pagenumber+"','"+selbrand+"');\" > Next</a> </font>&nbsp;&nbsp;";
			strRes=strRes+"<font><a class='blue1' href=\"javascript:funOnClickLastbrands('"+Lastpage+"','"+pagenumber+"','"+selbrand+"');\" >Last</a>&nbsp;&nbsp;</font>";
		}
		strRes=strRes+"</td><td align='left' width='55%'><font class='blue1'>Page&nbsp;<select name='page' id='page' onChange=\"javascript:getpagebrands('"+selbrand+"');\">";
		String strsel="";
		for(int i=1;i<=tp;i++)
		{
			String si=Integer.toString(i);
			LogLevel.DEBUG(5,new Throwable(),"selecttest :"+si );
			if(pagenumber.equals(si))
			{
				strsel="selected";
			}
			else
			{
				strsel="";
	
			}
			LogLevel.DEBUG(5,new Throwable(),"strsel :"+strsel );
			strRes=strRes+"<option value='"+i+"'"+strsel+">"+i+"</option>"; 
		}
		strRes=strRes+"</select>  of&nbsp;&nbsp;"; 
		if(Lastpage.equals("0"))
		{
			Lastpage="1";
		}
		else
		{
			Lastpage=Lastpage;
		}
		strRes=strRes+""+Lastpage+"</font></td></tr>";
		strRes=strRes+"</table></td></tr>";
		strRes=strRes+"<tr class='pages'><td colspan='6'></td></tr> ";
		//pagination ends here//
				
		strRes=strRes+"<table height='250' border='0' cellspacing='1' align='center' cellpadding='1' width='100%'><tr valign='top'><td>";
		for(int j=0; j<VendorVector.size();j++)
		{
			//Getting the values in to a hashtable
			Hashtable platnmVendor=(Hashtable)VendorVector.get(j);
			//Getting the values from hashtable using keys.
			String strVendid=String.valueOf(platnmVendor.get("vendor_id"));	  
			String strVendname=(String)platnmVendor.get("vendor_name");
			String strBrandname=(String)platnmVendor.get("brandname");
			LogLevel.DEBUG(5,new Throwable(),"strBrandname :"+strBrandname );
			//checking if strvendname is equal to the vendorname logged in or not
			if(selbrand!=null)
			{
				String[] tempArr=selectedbrand.split(",");
				LogLevel.DEBUG(5,new Throwable(),"tempArr.length :"+tempArr.length );
				LogLevel.DEBUG(5,new Throwable(),"tempArr:"+tempArr);
				for (int k=0;k<tempArr.length ;k++ )
				{	
					strTempValue=tempArr[k];
					LogLevel.DEBUG(5,new Throwable(),"strTempValue :"+strTempValue ); 
					//Checking if the platinum vendor from vendor_master is equal to the vendor selecter as partner.
					if(strBrandname.equals(strgetVendorname))
					{
						LogLevel.DEBUG(5,new Throwable(),"UNCHECKED  disabled:" ); 
						strselected="UNCHECKED";
						strpermisssion="disabled";
						strselectedbrand="";
						break;
					}
					else 
					{
						if(strBrandname.equals(strTempValue))
						{
							strselected="CHECKED";
							strpermisssion="";
							strselectedbrand=strBrandname;
							break;
						}
						else
						{
							strselected="UNCHECKED";
							strpermisssion="";
							strselectedbrand=strBrandname;
						}
					}
				}
			}
			int Jcount = j+1;
			int Startin=Integer.parseInt(Startindex);
			LogLevel.DEBUG(5,new Throwable(),"Startin:"+Startin);
			int Jcnt=Jcount + Startin;

			strRes=strRes+"<table border='0' cellspacing='1' valign='top' cellpadding='1' width='100%'><input type='checkbox' name='chkSi' value='' style='display:none'/>";

			strRes=strRes+"<tr class='insidecontent'><td width='45' align='left'  >"+Jcnt+"<input type='checkbox' "+strselected+" "+strpermisssion+" name='programpartner' value='"+strselectedbrand+"' size='3'></td><td align='left' >"+strBrandname+"</td>";
			strRes=strRes+"</table>";
			
	}
	strRes=strRes+"</td></tr></table>";
	//strRes=strRes+"<table border='0' cellspacing='1' align='center' cellpadding='1' width='100%'><tr class='insidecontent'><td width='110'><font color='#2364b1'><b>Selected Partners</b> :</font></td><td align='left' width='400'><font color='#2364b1'>"+selectedbrand+"</font></td></td></tr></table>";
	strRes=strRes+"<table  border='0'  align='center' valign='bottom' bgcolor='#FFFFFF' cellpadding='0' cellspacing='1'><tr height='20'><td></td><td>&nbsp;&nbsp;&nbsp;<a href=\"javascript:addLoyaltyProgram('"+selbrand+"');\" class='smallbutton'>Submit</a>&nbsp;&nbsp;&nbsp;</td> <td >&nbsp;&nbsp;&nbsp;<a href=\"../jsp/admin/batterystore/brandloyaltyprogram/brandaddloyalty.jsp;\" class='smallbutton'>Reset</a>&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;<a href=\"../servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement\" class='smallbutton'>Back</a>&nbsp;&nbsp;&nbsp;</td><td width='80'></tr></table>";
	}
	catch(Exception e)
	{
		LogLevel.ERROR(0,e,"problem Caught IOException if(submit) !! "+e);
		e.printStackTrace();
		session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
	}	
	//Returning the result to the place where method is called
	return strRes;
}

/* **************************************************************************************************************************************\
* This method is used to insert loyalty program, points, value and partners in to loyalty_program table..
* @VendIdQry : carries the query to fetch vendor_id from vendor_master table.
* @insrtProgQry : carries the query to insert loyalty_name, loyalty_points, value and partners_list from loyalty_program table.
\* **************************************************************************************************************************************/

public String addloyaltyprog (HttpServletRequest req , HttpServletResponse res,HttpSession session, String strvendorName,String strRelativePath,String strVirtualPath,String strScriptPath,String strCMSServerIP)
{
		//If parameter loyaltyname is not null then value will store in the String if it is null then null will be stored.
		String strloyaltyname = (req.getParameter("loyaltyname")!=null)?(req.getParameter("loyaltyname")):"";
		LogLevel.DEBUG(5,new Throwable(),"strloyaltyname :"+strloyaltyname);
		//If parameter loyalpartner is not null then value will store in the String if it is null then null will be stored.
		String strpartnername = (req.getParameter("loyalpartner")!=null)?(req.getParameter("loyalpartner")):"";
		LogLevel.DEBUG(5,new Throwable(),"strpartnername :"+strpartnername);
		//If parameter loyaltypoints is not null then value will store in the String if it is null then null will be stored.
		String strpoints = (req.getParameter("loyaltypoints")!=null)?(req.getParameter("loyaltypoints")):"";
		LogLevel.DEBUG(5,new Throwable(),"strpoints :"+strpoints);
		//If parameter loyaltyvalue is not null then value will store in the String if it is null then null will be stored.
		String strvalue = (req.getParameter("loyaltyvalue")!=null)?(req.getParameter("loyaltyvalue")):"";
		LogLevel.DEBUG(5,new Throwable(),"strvalue :"+strvalue);

		//If parameter program desc is not null then value will store in the String if it is null then null will be stored.
		String strprogdesc= (req.getParameter("progdesc")!=null)?(req.getParameter("progdesc")):"";
		LogLevel.DEBUG(5,new Throwable(),"strprogdesc :"+strprogdesc);

		//If parameter image is not null then value will store in the String if it is null then null will be stored.
		String strIconPath = (req.getParameter("image")!=null)?(req.getParameter("image")):"";
		LogLevel.DEBUG(5,new Throwable(),"strimage :"+strIconPath );

		//If parameter pagenumber is not null then value will store in the String if it is null then null will be stored.
		String pagenumber = (req.getParameter("pagenumber")!=null)?(req.getParameter("pagenumber")):"0";
		LogLevel.DEBUG(5,new Throwable(),"pagenumber:"+pagenumber );
				
		//Declaring a int variable for further usage.
		int result=0;
		
		strloyaltyname = strloyaltyname.replace("\\","\\\\");
		strloyaltyname = strloyaltyname.replace("'","\\'");

		 strprogdesc = strprogdesc.replace("\\","\\\\");
		 strprogdesc = strprogdesc.replace("'","\\'");
		double attachmentLimit = 4.0;
		try
		{
			//Query to fetch vendor id, vendor name from vendor_master.	
			String VendIdQry = "SELECT vendor_id, vendor_name,brandname FROM vendor_master WHERE vendor_name='"+strvendorName+"'"; 
			LogLevel.DEBUG(5,new Throwable(),"VendIdQry :"+VendIdQry );
			
			//Getting the result of the query in to hashtable
			Hashtable vendht = qm.getRow(VendIdQry);
			//Getting the value from hashtable using key
			String strvendorid=String.valueOf(vendht.get("vendor_id"));
			LogLevel.DEBUG(5,new Throwable(),"strvendorid :"+strvendorid );	
			String strvendorname=(String)vendht.get("vendor_name");
			String strbrandname=(String)vendht.get("brandname");
			LogLevel.DEBUG(5,new Throwable(),"strbrandname :"+strbrandname );	
			if (strpartnername.equals(""))
			{
				strpartnername=strbrandname;
			}
			else
			{
				String comm=",";
				strpartnername=strpartnername.concat(comm);
				strpartnername=strpartnername.concat(strbrandname);

			}
			LogLevel.DEBUG(5,new Throwable(),"bhanu string :"+strpartnername);
			if(strIconPath==null || strIconPath.equals(""))
					{ 

			//Query to insert details in to loyalty program table
			String insrtProgQry = "insert into loyalty_program(loyalty_name,loyalty_points,value,partners_list,vendor_id,vendor_name,description,creation_date,created_by,updation_date,updated_by) values('"+strloyaltyname+"','"+strpoints+"','"+strvalue+"','"+strpartnername+"','"+strvendorid+"','"+strbrandname+"','"+strprogdesc+"',now(),'ngit',now(),'ngit')";
			LogLevel.DEBUG(5,new Throwable(),"insrtProgQry :"+insrtProgQry );
			
			//Getting the result od the query in to result variable
			int i=qm.executeUpdate(insrtProgQry);
			LogLevel.DEBUG(5,new Throwable(),"result :"+result );
			//session.setAttribute("sesErrorMsg","<font color='#CC0000' class='vrb10'></font>");
			//res.sendRedirect("/surfmug/servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement");
							
			}
		else  // icon attached for program
			{
			LogLevel.DEBUG(5,new Throwable(),"image query:");
						String FilePath = strVirtualPath+"/"+strRelativePath+"/";
						File f1 = new File(FilePath);
						if(!f1.exists())
							f1.mkdirs();
						
						String strloyaltyname1= strloyaltyname.replace(" ", "");
						FilePath = FilePath+strloyaltyname1;
						LogLevel.DEBUG(5,new Throwable(),"FilePath :"+FilePath);

						f1 = new File(FilePath);
						if(!f1.exists())
						f1.mkdirs();

						File fileUploadPathCreate = new File(FilePath);
					/* checking the uploaded directory exist or not */
					if(fileUploadPathCreate.mkdirs())
					{
						LogLevel.DEBUG(5, new Throwable(), "Directory created");
					}
					else
					{
						LogLevel.DEBUG(5, new Throwable(), "Directory exists");
						/* if exists delete the directory and re create it */
						if(deleteDir(fileUploadPathCreate))
						{
							fileUploadPathCreate.mkdirs();
						}
					}


				// create a multipart object to upload files from client machine to server
				MultipartRequest multi = new MultipartRequest(req, FilePath , 15 * 1024 * 1024,new com.oreilly.servlet.multipart.DefaultFileRenamePolicy());

				// find the file path, name and extension
				strIconPath = strIconPath.replace('\\','/');
				LogLevel.DEBUG(4, new Throwable(), "Category icon path is : " +strIconPath );

				String strIconFileName = strIconPath.substring (strIconPath.lastIndexOf("/")+1,strIconPath.length());
				strIconFileName = strIconFileName.trim();
				strIconFileName= strIconFileName.replace(" ", "%20");
				LogLevel.DEBUG(5,new Throwable(),"strIconFileName is : "+strIconFileName);

				int index = strIconPath.lastIndexOf('.');
				String strExtension = strIconPath.substring( ++index );;

				// allow only image files
				if (( strExtension.equalsIgnoreCase("gif") ) || ( strExtension.equalsIgnoreCase("bmp") )||( strExtension.equalsIgnoreCase("jpg") ) ||   ( strExtension.equalsIgnoreCase("jpeg") )||( strExtension.equalsIgnoreCase("jpe") ) ||  ( strExtension.equalsIgnoreCase("png") )||( strExtension.equalsIgnoreCase("wbmp") )  )
				{
					// get the list of files attached in the request
					Enumeration files = multi.getFileNames();

					while (files.hasMoreElements())
					{
						String name = (String)files.nextElement();
						//OrigFileName declerd as string to get OrigFileName.
						String strOrigFileName = multi.getOriginalFileName(name);
						strOrigFileName= strOrigFileName.replace(" ", "%20");
						LogLevel.DEBUG(5, new Throwable(), "strOrigFileName = " + strOrigFileName);
						if(strOrigFileName==null || strOrigFileName.equals(""))
						{
							LogLevel.DEBUG(5, new Throwable(), "File not selected");
							continue;
						}

						//String strFileSysName = multi.getFilesystemName(name);
						//LogLevel.DEBUG(5, new Throwable(), "File Name System Name = " + strFileSysName);

						//if(strFileSysName==null || strFileSysName.equals(""))
						//{
						//LogLevel.DEBUG(5, new Throwable(), "File not selected");
						//continue;
						//}

						File f = multi.getFile(name);
						double picSize = (f.length())/(1024*1024);
						//double picSize = f.length();
						
						/* validating file size */
						if(picSize > attachmentLimit)
						{

							LogLevel.DEBUG(5, new Throwable(), "Attachment size exceeded");

							session.setAttribute("sesErrorMsg","<font color='#CC0000' class='vrb10'>You can upload Photo of size upto "+attachmentLimit+" MB only</font>");
							res.sendRedirect("../jsp/admin/batterystore/brandloyaltyprogram/brandaddloyalty.jsp");
							return null;
					
						}
						else
						{
							// create a thumbnail
							AddBatteryTrans pt= new AddBatteryTrans(qm);
							int result1=pt.createThumbnail11(FilePath,strOrigFileName,strScriptPath);
							LogLevel.DEBUG(5, new Throwable(), "result1:" + result1);
						if(result1<0)
						{
							session.setAttribute("sesErrorMsg","<font color='#CC0000' class='vrb10'>Failed to create thumbnail</font>");
							res.sendRedirect(baseURL+"/servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement");
							
						}
							

						//	String strFilePath = FilePath +"/"+strFileSysName;
							String strFilePath  = FilePath + "/"+strOrigFileName;
							LogLevel.DEBUG(5, new Throwable(), "strFilePath: " + strFilePath);

						
							// form the picture URL
							String strPicURL="http://"+strCMSServerIP+strRelativePath+"/"+strOrigFileName;
							LogLevel.DEBUG(5, new Throwable(), "strPicURL : " + strPicURL);

										String strSqlQry =" insert into loyalty_program(loyalty_name,loyalty_points,description,value,partners_list,vendor_id,vendor_name,icon,icon_name,creation_date,created_by,updation_date,updated_by) values('"+strloyaltyname+"','"+strpoints+"','"+strprogdesc+"','"+strvalue+"','"+strpartnername+"','"+strvendorid+"','"+strbrandname+"','?','"+strIconFileName+"',now(),'ngit',now(),'ngit')";
																
							LogLevel.DEBUG(5,new Throwable(),"Add category query is : "+strSqlQry);
							int reslt = qm.executeUpdate(strSqlQry);
							LogLevel.DEBUG(5, new Throwable(), "reslt: " + reslt);
						}
					}
				}
					
			//Checking if result is less than '0' or not
			if(result<0)
			{
				//if it is lees than '0' means query is not executed properly, error msg will display
				LogLevel.DEBUG(1,new Throwable(),"Failed to add retailer ");
				session.setAttribute("priority","1");
				session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'>Failed to add retailer!</font> ");
				res.sendRedirect(baseURL+"/servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement");
				
			}
			else
			{
				//if result is greater than '0' then sendRedirect is called.
				LogLevel.DEBUG(1,new Throwable(),"Successfully accepted the retailer ");
				session.setAttribute("sesErrorMsg", "<font color='#2364b1' class='vrb10'>Successfully added your account!</font> ");
				res.sendRedirect(baseURL+"/servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement");
					
			}
				 
		}
			int i=0;
				 if(i < 0)
				{
					LogLevel.DEBUG(1,new Throwable(),"Failed to accept Deal ");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'>Failed to accept plan!</font> ");
					res.sendRedirect(baseURL+"/servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement");
					
				}
				else
				{
					LogLevel.DEBUG(1,new Throwable(),"Successfully added your Deal ");
					session.setAttribute("sesErrorMsg", "<font color='#2364b1' class='vrb10'>Successfully added your Deal!</font> ");
				res.sendRedirect(baseURL+"/servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement");
				
				}
		}
		catch(IOException ioe)
		{
				//If any exceptionsa re found in try block, this block will catch those exception
				LogLevel.ERROR(0,ioe,"problem Caught IOException if(submit) !! "+ioe);
				//printstacktrace will print the line where exception is being arised
				ioe.printStackTrace();
				session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
		}catch(Exception e)
		{
				//If any exceptionsa re found in try block, this block will catch those exception
				LogLevel.ERROR(0,e,"Problem Caught Exception if(submit)!! "+e);
				//printstacktrace will print the line where exception is being arised
				e.printStackTrace();
				session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
		}
		//returning the result to the place where this method is called.
		return null;
}

/* **************************************************************************************************************************************\
* This method is used to fetch the partner names of a loyalty program from loyalty_program table..
* @partnrNameQry : carries the query to fetch partners_list from loyalty_program table.
\* **************************************************************************************************************************************/

public String getPartnerName (HttpServletRequest req , HttpServletResponse res,HttpSession session, String strvendorName)
{
					
		//If parameter loyaltyname is not null then value will store in the String if it is null then null will be stored.
		String varloyalid = (req.getParameter("varloyalid")!=null)?(req.getParameter("varloyalid")):"0";
		LogLevel.DEBUG(5,new Throwable(),"varloyalid:"+varloyalid );
					
		System.out.println("ajaxmodelname called");

		String strRes="";
		
		try
		{
					
			ServletOutputStream out=res.getOutputStream();	
			
			//Query to fetch partner_list, loyalty_points from loyalty_program table
			String partnrNameQry ="select partners_list,loyalty_points from loyalty_program where loyalty_id='"+varloyalid+"'";
			LogLevel.DEBUG(5,new Throwable(),"partnrNameQry :"+partnrNameQry );
			
			//Getting the result of the Query in to a vector
			Vector loyalVector=qm.executeQuery(partnrNameQry);
			
			//Getting the values from vector using for lop
			for(int j=0; j<loyalVector.size(); j++)
			{
				//Getting the values from vector in to Hashtable.
				Hashtable patrnName=(Hashtable)loyalVector.get(j);
				LogLevel.DEBUG(5,new Throwable(),"patrnName :"+patrnName ); 
				//Getting the values from hashtbale using keys.
				String strpartnername=(String)patrnName.get("partners_list");
				String loyaltypoints=String.valueOf(patrnName.get("loyalty_points"));
				//If strpartnername is not equal to null then Splitting it near ','.
				if(strpartnername!=null)
				{
					String[] tempArr=strpartnername.split(",");
					LogLevel.DEBUG(5,new Throwable(),"tempArr.length :"+tempArr.length);
					LogLevel.DEBUG(5,new Throwable(),"tempArr:"+tempArr);
					if(tempArr.length>0)
					{
						String strTempValue="";
						for (int k=0;k<tempArr.length ;k++)
						{	
							//Getting the partner name in to strTempValue
							strTempValue=tempArr[k];
							LogLevel.DEBUG(5,new Throwable(),"strTempValue:"+strTempValue);
							//Displaying each and every partner after splitting 
							strRes=strRes+"<tr><td><table align='center'><tr  class='insidecontent'><td width='100%' align='center' valign='middle' class='insidecontent'>"+strTempValue+"</td></tr></table></td></tr>";
						}
					}
				}
																										
			}					
			
																																								
		}catch(Exception e)
		{
				//If any exceptionsa re found in try block, this block will catch those exception
				LogLevel.ERROR(0,e,"Problem Caught Exception if(submit)!! "+e);
				//printstacktrace will print the line where exception is being arised
				e.printStackTrace();
				session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
		}
		//returning the result to the place where this method is called.
		return strRes;
}

/* **************************************************************************************************************************************\
* This method is used to check the availability of the user from user_profiles table.
* @checkUserQry : carries the query to check the availability of email_id from user_profiles table.
\* **************************************************************************************************************************************/

public String userCheck (HttpServletRequest req , HttpServletResponse res,HttpSession session, String strvendorName)
{
		//If parameter username is not null then value will store in the String if it is null then null will be stored.
		String strusername = (req.getParameter("username")!=null)?(req.getParameter("username")):"";
		LogLevel.DEBUG(5,new Throwable(),"strusername :"+strusername);

			
		System.out.println("ajaxmodelname called");
		
		try
		{ 
			ServletOutputStream out=res.getOutputStream();
			//Query to fetch email id from user_loyalty_program table
 			String checkUserQry = "SELECT email_id FROM batterywale_user_profile WHERE email_id='"+strusername+"'"; 
			LogLevel.DEBUG(5,new Throwable(),"checkUserQry :"+checkUserQry );
			//Getting the result of the query into hashtable
			Hashtable checkht = qm.getRow(checkUserQry);
			
			//ckecking if hashtable is empty or not
			if(checkht.isEmpty())
			{ 
				//If it is empty then that emailid doesnt exist
				LogLevel.DEBUG(1,new Throwable(),"No Name exists" +strusername);
				out.println("This user does not exist in BookBattery");
					
			}
			else
			{
				//if it is not empty then the emailid exist
				LogLevel.DEBUG(1,new Throwable(),"");
				out.println("This user exist in BookBattery");
					
			}
		}catch(IOException ioe)
		{ 
				//If any exceptionsa re found in try block, this block will catch those exception
				LogLevel.ERROR(0,ioe,"problem Caught IOException if(submit) !! "+ioe);
				//printstacktrace will print the line where exception is being arised
				ioe.printStackTrace();
				session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
		}catch(Exception e)
		{
				//If any exceptionsa re found in try block, this block will catch those exception
				LogLevel.ERROR(0,e,"Problem Caught Exception if(submit)!! "+e);
				//printstacktrace will print the line where exception is being arised
				e.printStackTrace();
				session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
		}
		//returning the result to the place where this method is called.
		return null;
} 

/* **************************************************************************************************************************************\
* This method is used to fetch all the details of a loyalty program from loyalty_program table..
* @loyalDtlsQry : carries the query to fetch loyalty_name, loyalty_points, value from loyalty_program table.
* @partnrDtls : carries the query to fetch partners_list from loyalty_program table.
\* **************************************************************************************************************************************/

public String modifyprogram (HttpServletRequest req , HttpServletResponse res,HttpSession session, String strvendorName)
{
		//If parameter check is not null then value will store in the String if it is null then null will be stored.
		String strcheck=(req.getParameter("check")!=null)?(req.getParameter("check")):"";
		LogLevel.DEBUG(5,new Throwable(),"strcheck:"+strcheck );

		//If parameter pagenumber is not null then value will store in the String if it is null then '0' will be stored.
		String pagenumber = (req.getParameter("pagenumber")!=null)?(req.getParameter("pagenumber")):"0";
		LogLevel.DEBUG(5,new Throwable(),"pagenumber:"+pagenumber );
			
					
 		try
		{

 				//Query to fetch loyalty_id, loyalty_name value from loyalty_program table.
				String loyalDtlsQry = "select loyalty_id,loyalty_name,loyalty_points,value,icon,icon_name,description,created_by,creation_date,updation_date,updated_by from loyalty_program where loyalty_id='"+strcheck+"' "; 
				LogLevel.DEBUG(5,new Throwable(),"loyalDtlsQry :"+loyalDtlsQry );
				
				//Getting the result of the query into vector
                Vector result=qm.executeQuery(loyalDtlsQry);
				LogLevel.DEBUG(5,new Throwable(),"result :"+result );
				
				//Query to fetch partner_list from loyalty_program table
				String partnrDtls = "select partners_list from loyalty_program where loyalty_id ='"+strcheck+"'";
				LogLevel.DEBUG(5,new Throwable(),"partnrDtls :"+partnrDtls);
				
				//Getting the result from query into vector
				Vector LoyalVector=qm.executeQuery(partnrDtls);
				LogLevel.DEBUG(5,new Throwable(),"LoyalVector :"+LoyalVector);
				
				//Ckecking if the result is null or not
				if(result == null || result.size()<=0)
				{
					//if it is null errormsg is send redirected to the jsp
					LogLevel.DEBUG(1,new Throwable(),"Failed to fetch account details ");
					session.setAttribute("priority","1");
					session.removeAttribute("sesLoyalty1");
					session.removeAttribute("sesAllPartnerVector1");
					session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'>Failed to fetch account details!</font> ");
					res.sendRedirect("../jsp/admin/batterystore/brandloyaltyprogram/brandmodifyloyalty.jsp");
				
	
				}
				else
				{	
					//if it is not null then sessions are send to the jsp which holds the result
					LogLevel.DEBUG(1,new Throwable(),"Successfully fetched account details ");
					session.setAttribute("sesErrorMsg", "");
					
					
					if(session.getAttribute("sesLoyalty1")!=null)
					session.removeAttribute("sesLoyalty1");
					session.setAttribute("sesLoyalty1", result);

					if(session.getAttribute("sesAllPartnerVector1")!=null)
					session.removeAttribute("sesAllPartnerVector1");
					session.setAttribute("sesAllPartnerVector1",LoyalVector);
				
					res.sendRedirect("../jsp/admin/batterystore/brandloyaltyprogram/brandmodifyloyalty.jsp");
				}
					
				
			}catch(IOException ioe)
			{
				//If any exceptionsa re found in try block, this block will catch those exception
				LogLevel.ERROR(0,ioe,"problem Caught IOException if(submit) !! "+ioe);
				//printstacktrace will print the line where exception is being arised
				ioe.printStackTrace();
				session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
		}catch(Exception e)
		{
				//If any exceptionsa re found in try block, this block will catch those exception
				LogLevel.ERROR(0,e,"Problem Caught Exception if(submit)!! "+e);
				//printstacktrace will print the line where exception is being arised
				e.printStackTrace();
				session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
		}
		//returning the result to the place where this method is called.
		return null;
}

/* **************************************************************************************************************************************\
* This method is used to update the modified details of loyalty program in loyalty_program table..
* @updateProgQry : carries the query to update loyalty_name, loyalty_points, value and partners_list from loyalty_program table.
\* **************************************************************************************************************************************/

public String modify (HttpServletRequest req , HttpServletResponse res,HttpSession session, String strvendorName,String strRelativePath,String strVirtualPath,String strScriptPath,String strCMSServerIP)
{


		//If parameter loyalty_id is not null then value will store in the String if it is null then null will be stored.
		String strloyaltyid = (req.getParameter("loyalty_id")!=null)?(req.getParameter("loyalty_id")):"";
		LogLevel.DEBUG(5,new Throwable(),"strloyaltyid :"+strloyaltyid);

		//If parameter loyaltyname is not null then value will store in the String if it is null then null will be stored.
		String strloyaltyname = (req.getParameter("loyaltyname")!=null)?(req.getParameter("loyaltyname")):"";
		LogLevel.DEBUG(5,new Throwable(),"strloyaltyname :"+strloyaltyname);

		//If parameter loyalpartner is not null then value will store in the String if it is null then null will be stored.
		String strpartnername = (req.getParameter("loyalpartner")!=null)?(req.getParameter("loyalpartner")):"";
		LogLevel.DEBUG(5,new Throwable(),"strpartnername :"+strpartnername);

		//If parameter loyaltypoints is not null then value will store in the String if it is null then null will be stored.
		String strpoints = (req.getParameter("loyaltypoints")!=null)?(req.getParameter("loyaltypoints")):"";
		LogLevel.DEBUG(5,new Throwable(),"strpoints :"+strpoints);

		//If parameter loyaltyvalue is not null then value will store in the String if it is null then null will be stored.
		String strvalue = (req.getParameter("loyaltyvalue")!=null)?(req.getParameter("loyaltyvalue")):"";
		LogLevel.DEBUG(5,new Throwable(),"strvalue :"+strvalue);

		//If parameter program desc is not null then value will store in the String if it is null then null will be stored.
		String strprogdesc= (req.getParameter("progdesc")!=null)?(req.getParameter("progdesc")):"";
		LogLevel.DEBUG(5,new Throwable(),"strprogdesc :"+strprogdesc);

		//If parameter image is not null then value will store in the String if it is null then null will be stored.
		String strIconPath = (req.getParameter("image")!=null)?(req.getParameter("image")):"";
		LogLevel.DEBUG(5,new Throwable(),"strimage :"+strIconPath );

		//If parameter pagenumber is not null then value will store in the String if it is null then null will be stored.
		String pagenumber = (req.getParameter("pagenumber")!=null)?(req.getParameter("pagenumber")):"0";
		LogLevel.DEBUG(5,new Throwable(),"pagenumber:"+pagenumber );

		//If parameter partner is not null then value will store in the String if it is null then null will be stored.
		String strpartner= (req.getParameter("partner")!=null)?(req.getParameter("partner")):"";
		LogLevel.DEBUG(5,new Throwable(),"strpartner :"+strpartner);

		strloyaltyname = strloyaltyname.replace("\\","\\\\");
		strloyaltyname = strloyaltyname.replace("'","\\'");

		 strprogdesc = strprogdesc.replace("\\","\\\\");
		 strprogdesc = strprogdesc.replace("'","\\'");

		double attachmentLimit = 4.0;

		int result=0;

		try
		{

			//Query to fetch vendor id, vendor name from vendor_master.	
			String VendIdQry = "SELECT vendor_id, vendor_name,brandname FROM vendor_master WHERE vendor_name='"+strvendorName+"'"; 
			LogLevel.DEBUG(5,new Throwable(),"VendIdQry :"+VendIdQry );
			
			//Getting the result of the query in to hashtable
			Hashtable vendht = qm.getRow(VendIdQry);
			//Getting the value from hashtable using key
			String strvendorid=String.valueOf(vendht.get("vendor_id"));
			LogLevel.DEBUG(5,new Throwable(),"strvendorid :"+strvendorid );	
			String strvendorname=(String)vendht.get("vendor_name");
			String strbrandname=(String)vendht.get("brandname");
			LogLevel.DEBUG(5,new Throwable(),"strbrandname :"+strbrandname );	

			if(strIconPath==null || strIconPath.equals(""))
			{ 

				//Query to update the details in loyalty_progrma table	
				String updateProgQry = "update loyalty_program set loyalty_name='"+strloyaltyname+"',description='"+strprogdesc+"',loyalty_points='"+strpoints+"',value='"+strvalue+"',partners_list='"+strpartnername+"' where loyalty_id="+strloyaltyid;
				//Result is stored in the ariable result
				result=qm.executeUpdate(updateProgQry);
				LogLevel.DEBUG(5,new Throwable(),"updateProgQry :"+updateProgQry);

					//Query to fetch email_id from user_loyalty_program table
			String selUserQry= "select distinct email_id from user_loyalty_program where loyalty_id="+strloyaltyid+" and merchant_flag='yes'";
			LogLevel.DEBUG(5,new Throwable(),"selUserQry :"+selUserQry);
			
			//Getting the values of the query into vector.
			Vector selemail=qm.executeQuery(selUserQry);
			LogLevel.DEBUG(5,new Throwable(),"selemail :"+selemail);
			
			//Declaring a variable of type int and intializing it with '0' for further usage.
			int result1=0;

			for( int i=0;i<selemail.size();i++)
			{
				//GEtting the values from vector into hashtable using for loop
				Hashtable email=(Hashtable)selemail.get(i);

				//Getting the values from hashtable using keys
				String stremail=(String)email.get("email_id");
				LogLevel.DEBUG(5,new Throwable(),"stremail :"+stremail);

				//Query used to fetched points earned orm user_loyalty_program table
				String selPntsQry= "Select points_earned from user_loyalty_program where email_id='"+stremail+"' and loyalty_id="+strloyaltyid+" and merchant_flag='yes'";
				LogLevel.DEBUG(5,new Throwable(),"selPntsQry :"+selPntsQry);
				//Getting the result into hashtable using getRow method
				Hashtable pntsht=qm.getRow(selPntsQry);
				LogLevel.DEBUG(5,new Throwable(),"pntsht :"+pntsht);
				//Converting String variable in to int for calculation
				int strpoints1=Integer.parseInt(strpoints);
				//Converting String variable in to int for calculation
				int value1=Integer.parseInt(strvalue);
				//Getting value from hashtbale using key and converting it in to int.
				int points=Integer.parseInt(String.valueOf(pntsht.get("points_earned")));
				//calculating the value for the points earned
				int addvalue=(points*value1) / strpoints1;	
				LogLevel.DEBUG(5,new Throwable(),"addvalue :"+addvalue);

				//Query to update hte points_earned and value_earned in user_loyalty_program table
				String updateUserQry= "update user_loyalty_program set value_earned="+addvalue+" where email_id='"+stremail+"'  and loyalty_id="+strloyaltyid+" and merchant_flag='yes'";
				LogLevel.DEBUG(5,new Throwable(),"updateUserQry :"+updateUserQry);
				
				//Getting the result of the query in to result1 variable
				result1=qm.executeUpdate(updateUserQry);
				LogLevel.DEBUG(5,new Throwable(),"result1 :"+result1);
			}

			}
			else  // icon attached for deal
			{
						LogLevel.DEBUG(5,new Throwable(),"image query:");
						String FilePath = strVirtualPath+"/"+strRelativePath+"/";
						File f1 = new File(FilePath);
						if(!f1.exists())
							f1.mkdirs();
						
					
						FilePath = FilePath+strloyaltyname;
						LogLevel.DEBUG(5,new Throwable(),"FilePath :"+FilePath);

						f1 = new File(FilePath);
						if(!f1.exists())
						f1.mkdirs();

						File fileUploadPathCreate = new File(FilePath);
					/* checking the uploaded directory exist or not */
					if(fileUploadPathCreate.mkdirs())
					{
						LogLevel.DEBUG(5, new Throwable(), "Directory created");
					}
					else
					{
						LogLevel.DEBUG(5, new Throwable(), "Directory exists");
						/* if exists delete the directory and re create it */
						if(deleteDir(fileUploadPathCreate))
						{
							fileUploadPathCreate.mkdirs();
						}
					}


				// create a multipart object to upload files from client machine to server
				MultipartRequest multi = new MultipartRequest(req, FilePath , 15 * 1024 * 1024,new com.oreilly.servlet.multipart.DefaultFileRenamePolicy());

				// find the file path, name and extension
				strIconPath = strIconPath.replace('\\','/');
				LogLevel.DEBUG(4, new Throwable(), "Category icon path is : " +strIconPath );

				String strIconFileName = strIconPath.substring (strIconPath.lastIndexOf("/")+1,strIconPath.length());
				strIconFileName = strIconFileName.trim();
				strIconFileName= strIconFileName.replace(" ", "%20");
				LogLevel.DEBUG(5,new Throwable(),"strIconFileName is : "+strIconFileName);

				int index = strIconPath.lastIndexOf('.');
				String strExtension = strIconPath.substring( ++index );;

				// allow only image files
				if (( strExtension.equalsIgnoreCase("gif") ) || ( strExtension.equalsIgnoreCase("bmp") )||( strExtension.equalsIgnoreCase("jpg") ) ||   ( strExtension.equalsIgnoreCase("jpeg") )||( strExtension.equalsIgnoreCase("jpe") ) ||  ( strExtension.equalsIgnoreCase("png") )||( strExtension.equalsIgnoreCase("wbmp") )  )
				{
					// get the list of files attached in the request
					Enumeration files = multi.getFileNames();

					while (files.hasMoreElements())
					{
						String name = (String)files.nextElement();
						//OrigFileName declerd as string to get OrigFileName.
						String strOrigFileName = multi.getOriginalFileName(name);
						strOrigFileName= strOrigFileName.replace(" ", "%20");
						LogLevel.DEBUG(5, new Throwable(), "strOrigFileName = " + strOrigFileName);
						if(strOrigFileName==null || strOrigFileName.equals(""))
						{
							LogLevel.DEBUG(5, new Throwable(), "File not selected");
							continue;
						}

						//String strFileSysName = multi.getFilesystemName(name);
						//LogLevel.DEBUG(5, new Throwable(), "File Name System Name = " + strFileSysName);

						//if(strFileSysName==null || strFileSysName.equals(""))
						//{
						//LogLevel.DEBUG(5, new Throwable(), "File not selected");
						//continue;
						//}

						File f = multi.getFile(name);
						double picSize = (f.length())/(1024*1024);
						//double picSize = f.length();
						
						/* validating file size */
						if(picSize > attachmentLimit)
						{

							LogLevel.DEBUG(5, new Throwable(), "Attachment size exceeded");

							session.setAttribute("sesErrorMsg","<font color='#CC0000' class='vrb10'>You can upload Photo of size upto "+attachmentLimit+" MB only</font>");
							res.sendRedirect("../jsp/admin/batterystore/brandloyaltyprogram/brandmodifyloyalty.jsp");
							return null;
					
						}
						else
						{
							// create a thumbnail
							AddBatteryTrans pt= new AddBatteryTrans(qm);
							int result1=pt.createThumbnail11(FilePath,strOrigFileName,strScriptPath);
							LogLevel.DEBUG(5, new Throwable(), "result1:" + result1);
						if(result1<0)
						{
							session.setAttribute("sesErrorMsg","<font color='#CC0000' class='vrb10'>Failed to create thumbnail</font>");
							res.sendRedirect(baseURL+"/servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement");
							
						}
							

						//	String strFilePath = FilePath +"/"+strFileSysName;
							String strFilePath  = FilePath + "/"+strOrigFileName;
							LogLevel.DEBUG(5, new Throwable(), "strFilePath: " + strFilePath);

						
							// form the picture URL
							String strPicURL="http://"+strCMSServerIP+strRelativePath+"/"+strOrigFileName;
							LogLevel.DEBUG(5, new Throwable(), "strPicURL : " + strPicURL);

							String strSqlQry = "update loyalty_program set loyalty_name='"+strloyaltyname+"',icon_name='"+strIconFileName+"',description='"+strprogdesc+"',loyalty_points='"+strpoints+"',value='"+strvalue+"',partners_list='"+strpartnername+"' where loyalty_id="+strloyaltyid;
																
							LogLevel.DEBUG(5,new Throwable(),"Add category query is : "+strSqlQry);
							int reslt = qm.executeUpdate(strSqlQry);
							LogLevel.DEBUG(5, new Throwable(), "reslt: " + reslt);
						}
					}
				}

			//Query to fetch email_id from user_loyalty_program table
			String selUserQry= "select distinct email_id from user_loyalty_program where loyalty_id="+strloyaltyid+" and merchant_flag='yes'";
			LogLevel.DEBUG(5,new Throwable(),"selUserQry :"+selUserQry);
			
			//Getting the values of the query into vector.
			Vector selemail=qm.executeQuery(selUserQry);
			LogLevel.DEBUG(5,new Throwable(),"selemail :"+selemail);
			
			//Declaring a variable of type int and intializing it with '0' for further usage.
			int result1=0;

			for( int i=0;i<selemail.size();i++)
			{
				//GEtting the values from vector into hashtable using for loop
				Hashtable email=(Hashtable)selemail.get(i);

				//Getting the values from hashtable using keys
				String stremail=(String)email.get("email_id");
				LogLevel.DEBUG(5,new Throwable(),"stremail :"+stremail);

				//Query used to fetched points earned orm user_loyalty_program table
				String selPntsQry= "Select points_earned from user_loyalty_program where email_id='"+stremail+"' and loyalty_id="+strloyaltyid+" and merchant_flag='yes'";
				LogLevel.DEBUG(5,new Throwable(),"selPntsQry :"+selPntsQry);
				//Getting the result into hashtable using getRow method
				Hashtable pntsht=qm.getRow(selPntsQry);
				LogLevel.DEBUG(5,new Throwable(),"pntsht :"+pntsht);
				//Converting String variable in to int for calculation
				int strpoints1=Integer.parseInt(strpoints);
				//Converting String variable in to int for calculation
				int value1=Integer.parseInt(strvalue);
				//Getting value from hashtbale using key and converting it in to int.
				int points=Integer.parseInt(String.valueOf(pntsht.get("points_earned")));
				//calculating the value for the points earned
				int addvalue=(points*value1) / strpoints1;	
				LogLevel.DEBUG(5,new Throwable(),"addvalue :"+addvalue);

				//Query to update hte points_earned and value_earned in user_loyalty_program table
				String updateUserQry= "update user_loyalty_program set value_earned="+addvalue+" where email_id='"+stremail+"'  and loyalty_id="+strloyaltyid+" and merchant_flag='yes'";
				LogLevel.DEBUG(5,new Throwable(),"updateUserQry :"+updateUserQry);
				
				//Getting the result of the query in to result1 variable
				result1=qm.executeUpdate(updateUserQry);
				LogLevel.DEBUG(5,new Throwable(),"result1 :"+result1);
			}
					
			//Checking if result is less than '0' or not
			if(result<0)
			{
				//if it is lees than '0' means query is not executed properly, error msg will display
				LogLevel.DEBUG(1,new Throwable(),"Failed to add retailer ");
				session.setAttribute("priority","1");
				session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'>Failed to add retailer!</font> ");
				res.sendRedirect(baseURL+"/servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement");
				
			}
			else
			{
				//if result is greater than '0' then sendRedirect is called.
				LogLevel.DEBUG(1,new Throwable(),"Successfully accepted the retailer ");
				session.setAttribute("sesErrorMsg", "<font color='#2364b1' class='vrb10'>Successfully added your account!</font> ");
				res.sendRedirect(baseURL+"/servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement");
					
			}
				 
		}

		int i=0;	
			
			
			//Checking if result and result1 are less than '0' or not
			if(i<0)
			{
				//If less than '0' then query is not executed properly, error message is sent.
				LogLevel.DEBUG(1,new Throwable(),"Failed to modify account ");
				session.setAttribute("priority","1");
				session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'>Failed to modify account!</font> ");
				res.sendRedirect(baseURL+"/servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement");
				
			}
			else
			{
				//If not less than '0' then Query executed successfully
				LogLevel.DEBUG(1,new Throwable(),"Successfully modified your account ");
				session.setAttribute("sesErrorMsg", "<font color='#2364b1' class='vrb10'>Successfully modified your account!</font> ");
				res.sendRedirect(baseURL+"/servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement");
				
			}
		}catch(IOException ioe)
		{
				//If any exceptionsa re found in try block, this block will catch those exception
				LogLevel.ERROR(0,ioe,"problem Caught IOException if(submit) !! "+ioe);
				//printstacktrace will print the line where exception is being arised
				ioe.printStackTrace();
				session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
		}catch(Exception e)
		{
				//If any exceptionsa re found in try block, this block will catch those exception
				LogLevel.ERROR(0,e,"Problem Caught Exception if(submit)!! "+e);
				//printstacktrace will print the line where exception is being arised
				e.printStackTrace();
				session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
		}
		//returning the result to the place where this method is called.
		return null;
}

/* **************************************************************************************************************************************\
* This method is used to delete loyalty program from loyalty_program table..
* @getNameQry : carries the query to fetch loyalty_name from loyalty_program table.
* @strDelQry : carries the query to delete loyalty_name from loyalty_program table.
* @strDelUserQry2 : carries the query to delete loyalty program user from user_loyalty_program table based on loyalty_name.
\* **************************************************************************************************************************************/

public String deleteprogram (HttpServletRequest req , HttpServletResponse res,HttpSession session, String strvendorName)
{
		//If parameter check is not null then value will store in the String if it is null then null will be stored.
		String strcheck=(req.getParameter("check")!=null)?(req.getParameter("check")):"";
		LogLevel.DEBUG(5,new Throwable(),"strcheck:"+strcheck );

		//If parameter pagenumber is not null then value will store in the String if it is null then null will be stored.
		String pagenumber = (req.getParameter("pagenumber")!=null)?(req.getParameter("pagenumber")):"0";
		LogLevel.DEBUG(5,new Throwable(),"pagenumber:"+pagenumber );
		
				
		try
		{
			
			//Query to fetch loyalty name from loyalty_program table based on loyalty_id.
			String getNameQry="select loyalty_name from loyalty_program where loyalty_id="+strcheck;
			LogLevel.DEBUG(5,new Throwable(),"getNameQry :"+getNameQry );
			
			//Result of the Query is stores in hashtable
			Hashtable nameht=qm.getRow(getNameQry);
			
			//Getting values form hashtbale using key.
			String strloyalName=(String)nameht.get("loyalty_name");
			LogLevel.DEBUG(5,new Throwable(),"strloyalName :"+strloyalName );
			//Query to delete a row from loyalty_program table based on loyalty_id.
 			String strDelQry = "delete from loyalty_program where loyalty_id="+strcheck; 
			LogLevel.DEBUG(5,new Throwable(),"strDelQry :"+strDelQry );
			
			//Getting the result into variable result variable
            int result=qm.executeUpdate(strDelQry);
			LogLevel.DEBUG(5,new Throwable(),"result :"+result );

			//Query  to delete a row from user_loyalty_program table based on loyalty_id.
			String strDelUserQry2 = "delete from user_loyalty_program where loyalty_id='"+strcheck+"'"; 
			LogLevel.DEBUG(5,new Throwable(),"strDelUserQry2 :"+strDelUserQry2 );

			//Storing the result of the query into result1 variable
            int result1=qm.executeUpdate(strDelUserQry2);
			LogLevel.DEBUG(5,new Throwable(),"result1 :"+result1 );
						
			//Checking if result is less than '0' or not
			if(result < 0)
			{
				// if result is less than '0' then error message is sent that query is not executed properly
				LogLevel.DEBUG(1,new Throwable(),"Failed to delete account details ");
				session.setAttribute("priority","1");
				session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'>Failed to fetch account details!</font> ");
				res.sendRedirect(baseURL+"/servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement");
			}
			else
			{
				//if not less than '0' then query is executed properly.
				LogLevel.DEBUG(1,new Throwable(),"Successfully deleted account details ");
				session.setAttribute("sesErrorMsg", "");
				session.setAttribute("priority","1");
				session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'>Failed to fetch account details!</font> ");
				res.sendRedirect(baseURL+"/servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement");
			}
					
				
		}catch(IOException ioe)
		{
				//If any exceptionsa re found in try block, this block will catch those exception
				LogLevel.ERROR(0,ioe,"problem Caught IOException if(submit) !! "+ioe);
				//printstacktrace will print the line where exception is being arised
				ioe.printStackTrace();
				session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
		}catch(Exception e)
		{
				//If any exceptionsa re found in try block, this block will catch those exception
				LogLevel.ERROR(0,e,"Problem Caught Exception if(submit)!! "+e);
				//printstacktrace will print the line where exception is being arised
				e.printStackTrace();
				session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
		}
		//returning the result to the place where this method is called.
		return null;
}

/* **************************************************************************************************************************************\
* This method is used to fetch selected partners from loyalty_program table.
* @partnrNameQry : carries the query to fetch partners_list from loyalty_program table.
* @platinmVendQry : carries the query to fetch vendor_name, vendor_id from vendor_master table based on merchant_category
\* **************************************************************************************************************************************/

public String getUpdatePartners (HttpServletRequest req , HttpServletResponse res,HttpSession session, String strvendorName)   
{
	System.out.println("ajaxmodelname called");

	String loyaltyid=(req.getParameter("loyalty_id")!=null)?(req.getParameter("loyalty_id")):"";
	LogLevel.DEBUG(5,new Throwable(),"loyaltyid:"+loyaltyid );
		
	String pagenumber = (req.getParameter("pagenumber")!=null)?(req.getParameter("pagenumber")):"0";
	LogLevel.DEBUG(5,new Throwable(),"pagenumber:"+pagenumber );

	String pagetype = (req.getParameter("pagetype")!=null)?(req.getParameter("pagetype")):"0";
	LogLevel.DEBUG(5,new Throwable(),"pagetype:"+pagetype );

	String lastpage = (req.getParameter("lastpage")!=null)?(req.getParameter("lastpage")):"0";
	LogLevel.DEBUG(5,new Throwable(),"lastpage:"+lastpage );

	String selbrand = (req.getParameter("selbrand")!=null)?(req.getParameter("selbrand")):"";
	LogLevel.DEBUG(5,new Throwable(),"selbrand:"+selbrand );
	
	String strRes="";

	try
	{	
		String strselected="";
		String strpermisssion="";
		String strselectedbrand="";
		String strTempValue="";
		
		if(lastpage.equals("0"))
		{
			lastpage="1";
		}
		if(pagetype.equals("first"))
		{
			pagenumber="1"; 
		}
		if(pagetype.equals("previous"))
		{
			int pn=Integer.parseInt(pagenumber)-1;
			pagenumber=Integer.toString(pn);
		}
		if(pagetype.equals("next"))
		{
			int pn=Integer.parseInt(pagenumber)+1;
			pagenumber=Integer.toString(pn);
			LogLevel.DEBUG(5,new Throwable(),"test:"+pagenumber );

		}
		if(pagetype.equals("last"))
		{
			pagenumber=lastpage;
		}
		if(pagenumber.equals("0"))
		{
			pagenumber="1";
		}
		int Opinions_per_page=10;

		int Startlimt=Opinions_per_page*(Integer.parseInt(pagenumber)-1);
		String Startindex=Integer.toString(Startlimt);
		LogLevel.DEBUG(5,new Throwable(),"Startindex:"+Startindex );

		ServletOutputStream out = res.getOutputStream();
		
		String strSqlQry=" select merchant_category_name  from merchant_category where type like '%Loyalty%'";
		LogLevel.DEBUG(5,new Throwable(),"strSqlQry to get the loyalty category :"+strSqlQry );

		ArrayList htcategory=new ArrayList();
		htcategory=qm.getField(strSqlQry);
		String category="";
		for(int i=0;i<htcategory.size();i++)
		{
			if(category.equals(""))
			category=htcategory.get(i).toString();
			else
			category=category+"','"+htcategory.get(i).toString();
		}
		LogLevel.DEBUG(5, new Throwable(), "category :" + category);

		String Vendorname="select vendor_name,brandname from vendor_master where vendor_name='"+strvendorName+"'";
		LogLevel.DEBUG(5,new Throwable(),"Vendorname :"+Vendorname );
		Hashtable getVendorname = qm.getRow(Vendorname);
		//Getting the values from hashtbale using keys in hashtable.
		String strgetVendorname=(String)getVendorname.get("vendor_name");
		LogLevel.DEBUG(5,new Throwable(),"strgetVendorname :"+strgetVendorname );

		//String strgetBrandname=(String)getVendorname.get("brandname");
		String strgetBrandname="Amaron";
		LogLevel.DEBUG(5,new Throwable(),"strgetBrandname :"+strgetBrandname );

		//Query to fetch partners_list from loyalty_program based on loyalty_id
		String partnrNameQry = "select partners_list from loyalty_program where loyalty_id ="+loyaltyid;
		LogLevel.DEBUG(5,new Throwable(),"partnrNameQry :"+partnrNameQry );
		//Getting the result of the query into vector
		Vector LoyalVector=qm.executeQuery(partnrNameQry);
		LogLevel.DEBUG(5,new Throwable(),"LoyalVector :"+LoyalVector);

		ArrayList loaylpartners=new ArrayList();
		loaylpartners=qm.getField(partnrNameQry);
		String partnerslist="";
		for(int i=0;i<loaylpartners.size();i++)
		{
			if(partnerslist.equals(""))
			partnerslist=loaylpartners.get(i).toString();
			else
			partnerslist=partnerslist+"','"+loaylpartners.get(i).toString();
		}
		LogLevel.DEBUG(5, new Throwable(), "partnerslist :" + partnerslist);
		if (selbrand.equals(""))
		{
			selbrand=partnerslist;
		}
		else 
		{
			selbrand=selbrand;
		}
		String strselpartner=selbrand;
		// The strSqlQry1 query is used to fetch the retailer details from retailers table based on retailer_id //
		String platnmVendQry = "select vendor_id,vendor_name, brandname from vendor_master where merchant_category in('"+category+"') and brandname='"+strvendorName+"' order by brandname asc limit "+Startindex+",10";
		LogLevel.DEBUG(5,new Throwable(),"platnmVendQry :"+platnmVendQry );
		
		//The VendorVector is used to store the result of the strSqlQry1.//
		Vector VendorVector=qm.executeQuery(platnmVendQry);
		LogLevel.DEBUG(5,new Throwable(),"VendorVector :"+VendorVector);

		String strSqlQry1="select brandname from vendor_master where merchant_category in('"+category+"') and brandname='"+strvendorName+"' order by brandname asc limit "+Startindex+",10";
		LogLevel.DEBUG(5,new Throwable(),"strSqlQry to get the loyalty category 123:"+strSqlQry1 );

		ArrayList htcategorybrand=new ArrayList();
		htcategorybrand=qm.getField(strSqlQry1);
		String categorybrands="";
		for(int i=0;i<htcategorybrand.size();i++)
		{
			categorybrands=htcategorybrand.get(i).toString();
			String comm=",";
			String categorybrands1=categorybrands.concat(comm);
			selbrand = selbrand.replace(categorybrands1,"");
			selbrand = selbrand.replace(categorybrands,"");
		}
		LogLevel.DEBUG(5, new Throwable(), "selbrand bhanu :" + selbrand);
		
		//The below strSqlQry2 query is used to fetch the total count for the above strSqlQry1 query //
		String strSqlQry2 = "select count(*) as count from (select vendor_id,vendor_name, brandname from vendor_master where brandname='"+strvendorName+"' and merchant_category in('"+category+"'))as T";
		LogLevel.DEBUG(5,new Throwable(),"strSqlQry2 getting count:"+strSqlQry2 );
		
		//The htRetailersCount hash table is used to store the result of strSqlQry2.//
		Hashtable htbrandsCount = qm.getRow(strSqlQry2);
		String strcount=String.valueOf(htbrandsCount.get("count"));
		LogLevel.DEBUG(5,new Throwable(),"strcount :"+strcount );
		
		int TotalCount=Integer.parseInt(strcount);
		LogLevel.DEBUG(5,new Throwable(),"TotalCount:"+TotalCount );
		int opinions_per_page=10;
		LogLevel.DEBUG(5,new Throwable(),"opinions_per_page:"+opinions_per_page );
		int tp=((int)(Math.ceil((double)TotalCount/opinions_per_page)));
		LogLevel.DEBUG(5,new Throwable(),"tp:"+tp );

		String Lastpage=Integer.toString(tp);
		LogLevel.DEBUG(5,new Throwable(),"testlastpage:"+Lastpage );

		//pagination starts from here//

		strRes=strRes+"<tr class='insidecontent'><td colspan='8' align='center'><table border='0' width='500'><tr><td width='65%' align='left'> ";

		if(pagenumber.equals("1"))
		{
			strRes=strRes+"<font class='blue2'>First</font>&nbsp;";
			strRes=strRes+"<font class='blue2'>Previous</font>&nbsp;";	
		}
		else
		{		
			strRes=strRes+"<font ><a class='blue1' href=\"javascript:funOnClickFirstbrands('"+pagenumber+"','"+selbrand+"','"+loyaltyid+"');\">First</a></font>&nbsp;";
			strRes=strRes+"<font> <a class='blue1' href=\"javascript:funOnClickPreviousbrands('"+pagenumber+"','"+selbrand+"','"+loyaltyid+"');\" > Previous</a> </font>&nbsp;";
		}
		
		if(pagenumber.equals(Lastpage))
		{
			strRes=strRes+"&nbsp;&nbsp;<font class='blue2'>Next</font>&nbsp;";
			strRes=strRes+"<font class='blue2'>Last</font>&nbsp;";
		}
		else
		{
			strRes=strRes+"&nbsp;&nbsp;<font><a class='blue1' href=\"javascript:funOnClickNextbrands('"+pagenumber+"','"+selbrand+"','"+loyaltyid+"');\" > Next</a> </font>&nbsp;";
			strRes=strRes+"<font><a class='blue1' href=\"javascript:funOnClickLastbrands('"+Lastpage+"','"+pagenumber+"','"+selbrand+"','"+loyaltyid+"');\" >Last</a>&nbsp;</font>";
		}
		strRes=strRes+"</td><td align='left' width='35%'><font class='blue1'>Page<select name='page' id='page' onChange=\"javascript:getpagebrands('"+selbrand+"','"+loyaltyid+"');\">";
		String strsel="";
		for(int i=1;i<=tp;i++)
		{
			String si=Integer.toString(i);
			LogLevel.DEBUG(5,new Throwable(),"selecttest :"+si );
			if(pagenumber.equals(si))
			{
				strsel="selected";
			}
			else
			{
				strsel="";
	
			}
			LogLevel.DEBUG(5,new Throwable(),"strsel :"+strsel );
			strRes=strRes+"<option value='"+i+"'"+strsel+">"+i+"</option>"; 
		}
		strRes=strRes+"</select>of&nbsp;"; 
		if(Lastpage.equals("0"))
		{
			Lastpage="1";
		}
		else
		{
			Lastpage=Lastpage;
		}
		strRes=strRes+""+Lastpage+"</font></td></tr>";
		strRes=strRes+"</table></td></tr>";
		strRes=strRes+"<tr class='pages'><td colspan='6'></td></tr> ";
		//pagination ends here//
				
		strRes=strRes+"<table height='250' border='0' cellspacing='1' align='center' cellpadding='1' width='100%'><tr valign='top'><td>";
		for(int j=0; j<VendorVector.size();j++)
		{
			//Getting the values in to a hashtable
			Hashtable platnmVendor=(Hashtable)VendorVector.get(j);
			//Getting the values from hashtable using keys.
			String strVendid=String.valueOf(platnmVendor.get("vendor_id"));	  
			String strVendname=(String)platnmVendor.get("vendor_name");
			String strBrandname=(String)platnmVendor.get("brandname");
			//checking if strvendname is equal to the vendorname logged in or not
			if(strselpartner!=null)
			{
				String[] tempArr=strselpartner.split(",");
				LogLevel.DEBUG(5,new Throwable(),"tempArr.length :"+tempArr.length );
				LogLevel.DEBUG(5,new Throwable(),"tempArr:"+tempArr);
				for (int k=0;k<tempArr.length ;k++ )
				{	
					strTempValue=tempArr[k];
					LogLevel.DEBUG(5,new Throwable(),"strTempValue :"+strTempValue );
					//Checking if the platinum vendor from vendor_master is equal to the vendor selecter as partner.
					
					if(strBrandname.equals(strTempValue))
					{
						if(strBrandname.equals(strgetBrandname))
						{
							strselected="CHECKED";
							strpermisssion="disabled";
							strselectedbrand=strBrandname;
							break;
						}
						else
						{	
							strselected="CHECKED";
							strpermisssion="";
							strselectedbrand=strBrandname;
													
						}
						break;
					}
					else 
					{
						strselected="UNCHECKED";
						strpermisssion="";
						strselectedbrand=strBrandname;
						
					
					}
				}
			}
			int Jcount = j+1;
			int Startin=Integer.parseInt(Startindex);
			LogLevel.DEBUG(5,new Throwable(),"Startin:"+Startin);
			int Jcnt=Jcount + Startin;

			strRes=strRes+"<table border='0' cellspacing='1' valign='top' cellpadding='1' width='100%'><input type='checkbox' name='chkSi' value='' style='display:none'/>";

			strRes=strRes+"<tr class='insidecontent'><td width='45' align='left'  >"+Jcnt+"<input type='checkbox' name='programpartner' value='Dummy' style='display:none'/><input type='checkbox' "+strselected+" "+strpermisssion+" name='programpartner' value='"+strselectedbrand+"' size='3'></td><td align='left' >"+strBrandname+"</td>";
			strRes=strRes+"</table>";
			
		}
		strRes=strRes+"</td></tr></table>";
		//strRes=strRes+"<table border='0' cellspacing='1' align='center' cellpadding='1' width='100%'><tr class='insidecontent'><td width='110'><font color='#2364b1'><b>Selected Partners</b> :</font></td><td align='left' width='400'><font color='#2364b1'>"+selbrand+"</font></td></td></tr></table>";
		strRes=strRes+"<table  border='0'  align='center' valign='bottom' bgcolor='#FFFFFF' cellpadding='0' cellspacing='1'><tr height='20'><td></td><td>&nbsp;&nbsp;&nbsp;<a href=\"javascript:updateprogram('"+loyaltyid+"','"+selbrand+"');\" class='smallbutton'>save</a>&nbsp;&nbsp;&nbsp;</td><td><td><a href='brandmodifyloyalty.jsp' class='smallbutton'>Reset</a>&nbsp;&nbsp;&nbsp;</td><td><a href='../servlet/BrandLoyalty?hidWhatToDo=modify' class='smallbutton'>Back</a>&nbsp;&nbsp;&nbsp;</td><td width='80'></tr></table>";
	}
	catch(Exception e)
	{
		LogLevel.ERROR(0,e,"problem Caught IOException if(submit) !! "+e);
		e.printStackTrace();
		session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
	}	
	//Returning the result to the place where method is called
	return strRes;
}
/* **************************************************************************************************************************************\
* This method is used to insert user with loyalty points and value in to user_loyalty_program table..
* @getPntsQry : carries the query to fetch loyalty_poi9nts, value from loyalty_program table.
* @getPartnrQry : carries the query to fetch partners_list from loyalty_program table.
* @getIdQry : carries the query to fetch vendor_id from vendor_master table.
* @insertUserQry : carries the query to insert loyalty_name, loyalty_points, value, partners_list, vendor_name, vendor_id, email_id into user_loyalty_program table.
\* **************************************************************************************************************************************/

public String addloyaltypoints (HttpServletRequest req , HttpServletResponse res,HttpSession session, String strvendorName)
 {

		//If parameter email_id is not null then value will store in the String if it is null then null will be stored.
		String stremailid = (req.getParameter("email_id")!=null)?(req.getParameter("email_id")):"";
		LogLevel.DEBUG(5,new Throwable(),"stremailid :"+stremailid);
		
		//If parameter loyalprograms is not null then value will store in the String if it is null then null will be stored.
		String strloyalprogramid = (req.getParameter("loyalprogramid")!=null)?(req.getParameter("loyalprogramid")):"";
		LogLevel.DEBUG(5,new Throwable(),"strloyalprogramid :"+strloyalprogramid);
		
		//If parameter loyaltypoints is not null then value will store in the String if it is null then null will be stored.
		String strpoints = (req.getParameter("loyaltypoints")!=null)?(req.getParameter("loyaltypoints")):"";
		LogLevel.DEBUG(5,new Throwable(),"strpoints :"+strpoints);
		
		//If parameter pagenumber is not null then value will store in the String if it is null then null will be stored.
		String pagenumber = (req.getParameter("pagenumber")!=null)?(req.getParameter("pagenumber")):"0";
		LogLevel.DEBUG(5,new Throwable(),"pagenumber:"+pagenumber);
		
		//Declaring variables for further usage.
		int result=-1;
		String strvalue="";
		try
		{
			//Query to fetch loyalty_id, loyalty_points, value from loyalty_program table based on loyalty_name
			String getPntsQry= "select loyalty_id,loyalty_points,value from loyalty_program where loyalty_id='"+strloyalprogramid+"'";
			//Getting the result from query into hashtable
			Hashtable getPnts=qm.getRow(getPntsQry);
			//converting String into int for calculation
			int strpoints1=Integer.parseInt(strpoints);
			//Getting the value from hashtable using the key and converting it into int.
			int value1=Integer.parseInt(String.valueOf(getPnts.get("value")));
			//Getting the value from hashtable using the key and converting it into int.
			int points=Integer.parseInt(String.valueOf(getPnts.get("loyalty_points")));
			//Getting the value from hashtable using the key.
			String strloyalid=String.valueOf(getPnts.get("loyalty_id"));
			
			//Calculating the value for the points earned
			int addvalue=strpoints1*value1 / points;	
			
			//Query to fetch vendor_id from vendor_master based on vendor_name.
			String getIdQry = "SELECT vendor_id, brandname FROM vendor_master WHERE vendor_name='"+strvendorName+"'"; 
			LogLevel.DEBUG(5,new Throwable(),"getIdQry :"+getIdQry );
			
			//Geting the value from query into hashtbale.
			Hashtable getId = qm.getRow(getIdQry);
			//Getting the value from hashtbale into string using the key.
			String strvendorid=String.valueOf(getId.get("vendor_id"));
			LogLevel.DEBUG(5,new Throwable(),"strvendorid :"+strvendorid );

			String strbrandname=String.valueOf(getId.get("brandname"));
			LogLevel.DEBUG(5,new Throwable(),"strbrandname :"+strbrandname );
			
			String checkprogQry="select email_id,loyalty_id from user_loyalty_program where email_id='"+stremailid+"' and loyalty_id="+strloyalid+" and merchant_flag='yes'";
			LogLevel.DEBUG(5,new Throwable(),"checkprogQry :"+checkprogQry );

			Vector getprog = qm.executeQuery(checkprogQry);
			LogLevel.DEBUG(5,new Throwable(),"getprog :"+getprog );

			if(getprog.size()<=0)
			{
				//Query used to inser the details in to user_loyalty_program table.
				String insertUserQry = "insert into user_loyalty_program(email_id,points_earned,value_earned,loyalty_id,vendor_id,vendor_name,creation_date,created_by,updation_date,updated_by) values('"+stremailid+"','"+strpoints+"',"+addvalue+",'"+strloyalid+"','"+strvendorid+"','"+strbrandname+"',now(),'ngit',now(),'ngit')";
				LogLevel.DEBUG(5,new Throwable(),"insertUserQry :"+insertUserQry );
				
				//Getting the result of the query into result variable.
				result=qm.executeUpdate(insertUserQry);
				LogLevel.DEBUG(5,new Throwable(),"result :"+result );
				
			
			}
			
				//Checking if the result is less than '0' or not.		
				if(result<0)
				{
					//If yes then error message i sprinted and query is not executed properly
					LogLevel.DEBUG(1,new Throwable(),"Failed to add user ");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'>The user you have Entered Already Holds the same Program</font> ");
					res.sendRedirect("../jsp/admin/batterystore/brandloyaltyprogram/brandaddpoints.jsp");
						
				}
				else
				{
					//else query is executed successfully.
					LogLevel.DEBUG(1,new Throwable(),"Successfully accepted the user ");
					session.setAttribute("sesErrorMsg", "<font color='#2364b1' class='vrb10'>Successfully added your user!</font> ");
					res.sendRedirect(baseURL+"/servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement1");
						
				}
		
		}catch(IOException ioe)
		{
				//If any exceptionsa re found in try block, this block will catch those exception
				LogLevel.ERROR(0,ioe,"problem Caught IOException if(submit) !! "+ioe);
				//printstacktrace will print the line where exception is being arised
				ioe.printStackTrace();
				session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
		}catch(Exception e)
		{
				//If any exceptionsa re found in try block, this block will catch those exception
				LogLevel.ERROR(0,e,"Problem Caught Exception if(submit)!! "+e);
				//printstacktrace will print the line where exception is being arised
				e.printStackTrace();
				session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
		}
		//returning the result to the place where this method is called.
		return null;
}

/* **************************************************************************************************************************************\
* This method is used to fetch loyalty name, points, value from user_loyalty_program table..
* @getLoyalNameQry : carries the query to fetch loyalty_name, loyalty_points, value from user_loyalty_program table.
\* **************************************************************************************************************************************/

public String getAjaxPrograms (HttpServletRequest req , HttpServletResponse res,HttpSession session, String strvendorName)
{
		//If parameter emailid is not null then value will store in the String if it is null then null will be stored.
		String stremailid= (req.getParameter("emailid")!=null)?(req.getParameter("emailid")):"0";
		LogLevel.DEBUG(5,new Throwable(),"stremailid :"+stremailid );
		
		//declaring string variable for further usage.
	
		String strRes="";
		try
		{  
			ServletOutputStream out=res.getOutputStream();

			String getbrand="select brandname, vendor_name from vendor_master where vendor_name='"+strvendorName+"'";
			Hashtable strbrandht=qm.getRow(getbrand);
			String strbrand=(String)strbrandht.get("brandname");
				String getDtlsQry= "select b.loyalty_name,a.points_earned,a.value_earned from user_loyalty_program a,loyalty_program b where a.loyalty_id=b.loyalty_id and email_id='"+stremailid+"' and a.vendor_name='"+strbrand+"' and a.merchant_flag='yes'";
				
				Vector UserVector=qm.executeQuery(getDtlsQry);
				strRes="<table border='0' width='100%' bgcolor='#DDDDDD'>";
				
				//Checking if the vector is null or not.
				if(UserVector==null || UserVector.size() == 0)
				{	//If yes then printing the error message
					out.println("<p align='center' class='style1'><a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
					
				}
				else
				{
					//else Displaying the table.
					strRes=strRes+"<tr height='1' bgcolor='#DAD0E1' class='subheading'><td  class='subheading' align='center'>Loyalty Program</td><td  class='subheading' align='center'>Loyalty Points</td><td  class='subheading' align='center'>Value(Rs)</td><td  class='subheading' align='center'>Loyalty Program Owner</td></tr>";
					
					for ( int j=0; j<UserVector.size() ; j++)
					{
						//Getting the values from vector into hashtable using for loop
						Hashtable Userht=(Hashtable)UserVector.get(j);
						//getting the value from hashtable using the keys.
						String strvalue=String.valueOf(Userht.get("value_earned"));
						LogLevel.DEBUG(5,new Throwable(),"strvalue :"+strvalue);

						String strloyalpoints=String.valueOf(Userht.get("points_earned"));
						LogLevel.DEBUG(5,new Throwable(),"strloyalpoints :"+strloyalpoints);

						String strloyaltyname=String.valueOf(Userht.get("loyalty_name"));
						strloyaltyname = strloyaltyname.replaceAll("<","&lt;");
						strloyaltyname = strloyaltyname.replaceAll(">","&gt;");
						LogLevel.DEBUG(5,new Throwable(),"strloyaltyname :"+strloyaltyname);

						//Dispalying the String variables.
						strRes=strRes+"<tr height='1' bgcolor='#DAD0E1' class='insidecontent'>";
						strRes=strRes+"<td width='20%' class='insidecontent' align='center'>"+strloyaltyname+"</td>";
						strRes=strRes+"<td width='20%' class='insidecontent' align='center'>"+strloyalpoints+"</td>";
						strRes=strRes+"<td width='20%' class='insidecontent' align='center'>"+strvalue+"</td>";
						strRes=strRes+"<td width='20%' class='insidecontent' align='center'>"+strbrand+"</td></tr>";
					}
				}
			
				strRes=strRes+"</table>";							
			
								
			
				  
		}catch(IOException ioe)
		{
				//If any exceptionsa re found in try block, this block will catch those exception
				LogLevel.ERROR(0,ioe,"problem Caught IOException if(submit) !! "+ioe);
				//printstacktrace will print the line where exception is being arised
				ioe.printStackTrace();
				session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
		}catch(Exception e)
		{
				//If any exceptionsa re found in try block, this block will catch those exception
				LogLevel.ERROR(0,e,"Problem Caught Exception if(submit)!! "+e);
				//printstacktrace will print the line where exception is being arised
				e.printStackTrace();
				session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
		}
		//returning the result to the place where this method is called.
		return strRes;
}
		
/* **************************************************************************************************************************************\
* This method is used to fetch emailid and loyalty name from user_loyalty_program table..
* @getEmailQry : carries the query to fetch email_id, loyalty_name from user_loyalty_program table.
\* **************************************************************************************************************************************/

public String modifypoints (HttpServletRequest req , HttpServletResponse res,HttpSession session, String strvendorName)
{

		//If parameter check is not null then value will store in the String if it is null then null will be stored.
		String strcheck=(req.getParameter("check")!=null)?(req.getParameter("check")):"";
		LogLevel.DEBUG(5,new Throwable(),"strcheck:"+strcheck );
		//If parameter pagenumber is not null then value will store in the String if it is null then null will be stored.
		String pagenumber = (req.getParameter("pagenumber")!=null)?(req.getParameter("pagenumber")):"0";
		LogLevel.DEBUG(5,new Throwable(),"pagenumber:"+pagenumber );
		
		
		
		try
		{

			String getbrand="select brandname, vendor_name from vendor_master where vendor_name='"+strvendorName+"'";
		LogLevel.DEBUG(5,new Throwable(),"getbrand:"+getbrand );

			Hashtable getbrandht=qm.getRow(getbrand);
					LogLevel.DEBUG(5,new Throwable(),"getbrandht:"+getbrandht );

			String strbrand=(String)getbrandht.get("brandname");
		LogLevel.DEBUG(5,new Throwable(),"strbrand:"+strbrand );

			//Query to fetch email_id, loyalty_id from user_loyalty_program table.
			String getDtlsQry= "select b.loyalty_name, b.loyalty_id,a.email_id from user_loyalty_program a,loyalty_program b where a.loyalty_id=b.loyalty_id and email_id='"+strcheck+"' and a.vendor_name='"+strbrand+"'";
				
			Vector result=qm.executeQuery(getDtlsQry);
			//Checking if result is null or not
			if(result == null || result.size()<=0)
			{
				//if yes then erroe message is sent to jsp
				LogLevel.DEBUG(1,new Throwable(),"Failed to fetch account details ");
				session.setAttribute("priority","1");
				session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'>Failed to fetch account details!</font> ");
				res.sendRedirect("../jsp/admin/batterystore/brandloyaltyprogram/brandmodifypoints.jsp");
					
	
			}
			else
			{
				//else serssions are sent to jsp which hold the data.
				LogLevel.DEBUG(1,new Throwable(),"Successfully fetched account details ");
				session.setAttribute("sesErrorMsg", "");
					
				if(session.getAttribute("sesPoints1")!=null)
				session.removeAttribute("sesPoints1");
				session.setAttribute("sesPoints1", result);	
				
				res.sendRedirect("../jsp/admin/batterystore/brandloyaltyprogram/brandmodifypoints.jsp");
			}	
		}catch(IOException ioe)
		{
				//If any exceptionsa re found in try block, this block will catch those exception
				LogLevel.ERROR(0,ioe,"problem Caught IOException if(submit) !! "+ioe);
				//printstacktrace will print the line where exception is being arised
				ioe.printStackTrace();
				session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
		}catch(Exception e)
		{
				//If any exceptionsa re found in try block, this block will catch those exception
				LogLevel.ERROR(0,e,"Problem Caught Exception if(submit)!! "+e);
				//printstacktrace will print the line where exception is being arised
				e.printStackTrace();
				session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
		}
		//returning the result to the place where this method is called.
		return null;
}

/* **************************************************************************************************************************************\
* This method is used to update loyalty name, points and value in user_loyalty_program table..
* @getPntsQry : carries the query to fetch loyalty_points, value from loyalty_program table.
* @updateQry : carries the query to update loyalty_points, email_id, value, loyalty_name, partners_list in user_loyalty_program table.
\* **************************************************************************************************************************************/

public String updateloyaltypoints (HttpServletRequest req , HttpServletResponse res,HttpSession session, String strvendorName)
{

		//If parameter email_id is not null then value will store in the String if it is null then null will be stored.
		String stremailid = (req.getParameter("email_id")!=null)?(req.getParameter("email_id")):"";
		LogLevel.DEBUG(5,new Throwable(),"stremailid :"+stremailid);

		//If parameter loyalprograms is not null then value will store in the String if it is null then null will be stored.
		String strprogramnameid = (req.getParameter("loyalprogramid")!=null)?(req.getParameter("loyalprogramid")):"";
		LogLevel.DEBUG(5,new Throwable(),"strprogramnameid :"+strprogramnameid);

		//If parameter loyaltypoints is not null then value will store in the String if it is null then null will be stored.
		String strpoints = (req.getParameter("loyaltypoints")!=null)?(req.getParameter("loyaltypoints")):"";
		LogLevel.DEBUG(5,new Throwable(),"strpoints :"+strpoints);

		//If parameter pagenumber is not null then value will store in the String if it is null then null will be stored.
		String pagenumber = (req.getParameter("pagenumber")!=null)?(req.getParameter("pagenumber")):"0";
		LogLevel.DEBUG(5,new Throwable(),"pagenumber:"+pagenumber);

		
		try
		{

			//Query to fetch loyalty_id, loyalty_points and value from loyalty_program based on loyalty_name.
			String getPntsQry="select loyalty_id,loyalty_points, value from loyalty_program where loyalty_id='"+strprogramnameid+"'";
			//Getting the result of the query into Hashtbale.
			Hashtable getpntht=qm.getRow(getPntsQry);
			
			//Converting the String variable into int for calculation
			int strpoints1=Integer.parseInt(strpoints);
			//Getting the value from hashtable using the key and converting it into int.
			int value1=Integer.parseInt(String.valueOf(getpntht.get("value")));
			//Getting the value from hashtable using the key and converting it into int.
			int points=Integer.parseInt(String.valueOf(getpntht.get("loyalty_points")));
			//Calculating the value to be inserted using points earned.
			int varOne=strpoints1*value1 / points;
			//int addvalue = Math.ceil(varOne);
			int addvalue=((int)(Math.ceil((double)varOne)));
			//getting the value form hashtable using the key.
			String strloyaltyid=String.valueOf(getpntht.get("loyalty_id"));
			
			//Query to update The details into user_loyalty_program table.
			String updateQry = "update user_loyalty_program set points_earned='"+strpoints+"',value_earned="+addvalue+" where email_id='"+stremailid+"' and loyalty_id='"+strloyaltyid+"' and merchant_flag='yes'";
			//Storing the result of the query in a result of type int.
			int result=qm.executeUpdate(updateQry);
			LogLevel.DEBUG(5,new Throwable(),"updateQry :"+updateQry);

			//Ckecking if the result is less than '0' are not.
			if(result < 0)
			{
					//if yes then query is not execute properly.
					LogLevel.DEBUG(1,new Throwable(),"Failed to modify account");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'>Failed to modify account!</font>");
					res.sendRedirect(baseURL+"/servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement1");
				
			}
			else
			{
					//else query is executed sucessfully
					LogLevel.DEBUG(1,new Throwable(),"Successfully modified your account ");
					session.setAttribute("sesErrorMsg", "<font color='#2364b1' class='vrb10'>Successfully modified your account!</font> ");
					res.sendRedirect(baseURL+"/servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement1");
				
			}
		}catch(IOException ioe)
		{
				//If any exceptionsa re found in try block, this block will catch those exception
				LogLevel.ERROR(0,ioe,"problem Caught IOException if(submit) !! "+ioe);
				//printstacktrace will print the line where exception is being arised
				ioe.printStackTrace();
				session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
		}catch(Exception e)
		{
				//If any exceptionsa re found in try block, this block will catch those exception
				LogLevel.ERROR(0,e,"Problem Caught Exception if(submit)!! "+e);
				//printstacktrace will print the line where exception is being arised
				e.printStackTrace();
				session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
		}
		//returning the result to the place where this method is called.
		return null;

}

/* **************************************************************************************************************************************\
* This method is used to fetch loyalty points, value from loyalty_program and user_loyalty_program tables..
* @getpntQry : carries the query to fetch loyalty_points, value from user_loyalty_program table.
* @pntsQry : carries the query to fetch loyalty_points, value from loyalty_points table.
\* **************************************************************************************************************************************/

public String getSelProgram (HttpServletRequest req , HttpServletResponse res,HttpSession session, String strvendorName)
{
		//If parameter email_id is not null then value will store in the String if it is null then null will be stored.
		String stremailid = (req.getParameter("emailid")!=null)?(req.getParameter("emailid")):"0";
		LogLevel.DEBUG(5,new Throwable(),"stremailid :"+stremailid );

		//If parameter loyaltyname is not null then value will store in the String if it is null then null will be stored.
		String strloyaltyid = (req.getParameter("loyaltyid")!=null)?(req.getParameter("loyaltyid")):"0";
		LogLevel.DEBUG(5,new Throwable(),"strloyaltyid :"+strloyaltyid );

			
		System.out.println("ajaxmodelname called");
		//Declaring String variable for further usage.
	
        String strRes="";
		try
		{
			ServletOutputStream out=res.getOutputStream();
			//Query to fetch loyalty_id,  from loyalty_program based on loyalty_name.
			String getidQry= "select loyalty_id from loyalty_program where loyalty_id='"+strloyaltyid+"'";
			LogLevel.DEBUG(5,new Throwable(),"getidQry :"+getidQry );

			//Getting the result of the query into Hashtbale.
			Hashtable getid=qm.getRow(getidQry);
			String strloyalid=String.valueOf(getid.get("loyalty_id"));
			LogLevel.DEBUG(5,new Throwable(),"strloyalid :"+strloyalid );
			//Query distinct points_earned, value_earned from user_loyalty_program where loyalty_id table.
			String getpntQry="select distinct points_earned, value_earned from user_loyalty_program where loyalty_id='"+strloyalid+"' and email_id='"+stremailid+"' and merchant_flag='yes'";
			LogLevel.DEBUG(5,new Throwable(),"getpntQry :"+getpntQry );
			//getting the result from query into a vector.
            Vector progName=qm.executeQuery(getpntQry);
			LogLevel.DEBUG(5,new Throwable(),"progName :"+progName);
			//Query  to fetch loyalty_points,value from loyalty_program where loyalty_id table.
			String pntsQry="select loyalty_points,value from loyalty_program where loyalty_id='"+strloyalid+"'";
			//getting the result from query into a vector.
			Vector loyalPoints=qm.executeQuery(pntsQry);
			LogLevel.DEBUG(5,new Throwable(),"loyalPoints :"+loyalPoints);

			for(int i=0;i<loyalPoints.size();i++)
			{
					//Getting the result of the query into Hashtbale.
					Hashtable pntht=(Hashtable)loyalPoints.get(i);
					String strpoints=String.valueOf(pntht.get("loyalty_points"));
					LogLevel.DEBUG(5,new Throwable(),"strpoints :"+strpoints);

					String strvalue=String.valueOf(pntht.get("value"));
					LogLevel.DEBUG(5,new Throwable(),"strvalue :"+strvalue);

					for(int j=0; j<progName.size(); j++)
					{
							//Getting the result of the query into Hashtbale.
							Hashtable getpntht=(Hashtable)progName.get(j);
						 	String strloyalpoints=String.valueOf(getpntht.get("points_earned"));
							LogLevel.DEBUG(5,new Throwable(),"strloyalpoints :"+strloyalpoints );

							//Dispalying the String variables.
							strRes=strRes+"<td class='subheading' >Points<font color='FF0000'>*</font></td>";								
							strRes=strRes+"<td>:&nbsp;<input type='text' name='points' disabled='true' value='"+strpoints+"' maxlength='50' size='5' ></td>";
							strRes=strRes+"&nbsp;<td class='subheading'>Value(Rs)<font color='FF0000'>*</font></td>";
							strRes=strRes+"<td>:&nbsp;<input type='text' name='value' disabled='true' value='"+strvalue+"' size='5' ></td></tr>";
							strRes=strRes+"<tr>&nbsp;<td class='subheading' >Points Earned<font color='FF0000'>*</font></td>";	
							strRes=strRes+"<td>:&nbsp;<input type='text' name='pointsearned' value='"+strloyalpoints+"' size='5' disabled='true'></td>";
							strRes=strRes+"<td>Add Points:&nbsp;<input type='text' name='addpoints'  size='5' maxlength='6' ></td>";
					
					}	
					LogLevel.DEBUG(5,new Throwable(),"strRes :"+strRes );			

					
			}
		}catch(Exception e)
		{
				//If any exceptionsa re found in try block, this block will catch those exception
				LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
				//printstacktrace will print the line where exception is being arised
				e.printStackTrace();
				session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
		}
		//returning the result to the place where this method is called.
		return strRes;
}

/* **************************************************************************************************************************************\
* This method is used to fetch points, value from loyalty_program table..
* @getPontsQry : carries the query to fetch loyalty_points, value from loyalty_program table.
\* **************************************************************************************************************************************/

public String getTextBox (HttpServletRequest req , HttpServletResponse res,HttpSession session, String strvendorName)
{
		//If parameter loyalprog is not null then value will store in the String if it is null then null will be stored.
		String strloyalprogid=(req.getParameter("loyalprogid")!=null)?(req.getParameter("loyalprogid")):"0";
		System.out.println("ajaxmodelname called");
		//Declaring String variable for further usage.
		
        String strRes="";
		try
		{
			ServletOutputStream out=res.getOutputStream();

			//Query  to fetch loyalty_points, value from loyalty_program based loyalty_name.
			String getPontsQry="select loyalty_points, value from loyalty_program where loyalty_id='"+strloyalprogid+"'"; 
			LogLevel.DEBUG(5,new Throwable(),"getPontsQry :"+getPontsQry );
			//getting the result from query into a vector.
            Vector progName=qm.executeQuery(getPontsQry);
			LogLevel.DEBUG(5,new Throwable(),"progName :"+progName); 

			for ( int j=0; j<progName.size(); j++)
			{
				//Getting the result of the query into Hashtbale.
				Hashtable getPnts=(Hashtable)progName.get(j);
				String strloyalpoints=String.valueOf(getPnts.get("loyalty_points"));
				LogLevel.DEBUG(5,new Throwable(),"strloyalpoints :"+strloyalpoints );


				String strvalue=String.valueOf(getPnts.get("value"));
				LogLevel.DEBUG(5,new Throwable(),"strvalue :"+strvalue );
				//Dispalying the String variables.

				strRes=strRes+"<td class='insidecontent' >Points<font color='FF0000'>*</font></td>";						
				strRes=strRes+"<td>:&nbsp;<input type='text' name='points' disabled='true' value='"+strloyalpoints+"'  size='5' ></td>";
				strRes=strRes+"&nbsp;<td class='insidecontent'>Value(Rs)<font color='FF0000'>*</font></td>";
				strRes=strRes+"<td>:&nbsp;<input type='text' name='value' disabled='true' value='"+strvalue+"'  size='5' ></td></tr>";
				strRes=strRes+"<tr>&nbsp;<td class='insidecontent' >Points Earned<font color='FF0000'>*</font></td>";											
				strRes=strRes+"<td>:&nbsp;<input type='text' name='pointsearned' Value=''  size='5' maxlength='6' ></td>";
		
			}			
			LogLevel.DEBUG(5,new Throwable(),"strRes :"+strRes );																																							
		}catch(Exception e)
		{
				//If any exceptionsa re found in try block, this block will catch those exception
				LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
				e.printStackTrace();
				//printstacktrace will print the line where exception is being arised
				session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
		}
		//returning the result to the place where this method is called.
		return strRes;
}

/* **************************************************************************************************************************************\
* This method is used to fetch loyalty name, loyalty id from loyalty_program table..
* @progNameQry : carries the query to fetch loyalty_name, loyalty_points, value from loyalty_program table.
\* **************************************************************************************************************************************/

public String getLoyaltyNames (HttpServletRequest req , HttpServletResponse res,HttpSession session, String strvendorName)
{
		//Declaring String variable for further usage.
	
		String strRes="";
		try
		{

			String getvendname="select vendor_id, brandname from vendor_master where vendor_name='"+strvendorName+"'";
			LogLevel.DEBUG(5,new Throwable(),"getvendname :"+getvendname );

			Hashtable getht=qm.getRow(getvendname);
			String strbrand=(String)getht.get("brandname");
			LogLevel.DEBUG(5,new Throwable(),"strbrand :"+strbrand );

			//Query  to fetch  loyalty_name,loyalty_id from loyalty_program based vendor_name.
			String progNameQry="select loyalty_name,loyalty_id from loyalty_program where vendor_name='"+strbrand+"'";
			LogLevel.DEBUG(5,new Throwable(),"progNameQry :"+progNameQry );
			//getting the result from query into a vector.
            Vector progName=qm.executeQuery(progNameQry);
			LogLevel.DEBUG(5,new Throwable(),"progName :"+progName);

			if(progName == null || progName.size()<=0)
			{
				//if yes then query is not execute properly.
				LogLevel.DEBUG(1,new Throwable(),"Failed to fetch account details ");
				session.setAttribute("priority","1");
				session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'>Failed to fetch account details!</font> ");
				res.sendRedirect("../jsp/admin/batterystore/brandloyaltyprogram/brandaddpoints.jsp");
			}
			else
			{
				//else query is executed sucessfully
				LogLevel.DEBUG(1,new Throwable(),"Successfully fetched account details ");
				session.setAttribute("sesErrorMsg", "");
				if(session.getAttribute("sesNames1")!=null)
				session.removeAttribute("sesNames1");
				session.setAttribute("sesNames1", progName);
				res.sendRedirect("../jsp/admin/batterystore/brandloyaltyprogram/brandaddpoints.jsp");
			}	
		}catch(Exception e)
		{
				//If any exceptionsa re found in try block, this block will catch those exception
				LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
				e.printStackTrace();
				//printstacktrace will print the line where exception is being arised
				session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
		}
		//returning the result to the place where this method is called.
		return null;
}
/* **************************************************************************************************************************************\
* This method is used to delete loyalty program user from user_loyalty_program table..
* @strDelQry : carries the query to delete a row in user_loyalty_program.
\* **************************************************************************************************************************************/

public String deleteuser (HttpServletRequest req , HttpServletResponse res,HttpSession session, String strvendorName)
{
		//If parameter check is not null then value will store in the String if it is null then null will be stored.	
		String strcheck=(req.getParameter("check")!=null)?(req.getParameter("check")):"";
		LogLevel.DEBUG(5,new Throwable(),"strcheck:"+strcheck );

		//If parameter pagenumber is not null then value will store in the String if it is null then null will be stored.	
		String pagenumber = (req.getParameter("pagenumber")!=null)?(req.getParameter("pagenumber")):"0";
		LogLevel.DEBUG(5,new Throwable(),"pagenumber:"+pagenumber );
		//If parameter //If parameter pagenumber is not null then value will store in the String if it is null then null will be stored.	 is not null then value will store in the String if it is null then null will be stored.	
		String strloyaltyid = (req.getParameter("loyaltyid")!=null)?(req.getParameter("loyaltyid")):"0";
		LogLevel.DEBUG(5,new Throwable(),"loyaltyname:"+pagenumber );
		
		try
		{
			String getbrand="select brandname, vendor_name from vendor_master where vendor_name='"+strvendorName+"'";
			LogLevel.DEBUG(5,new Throwable(),"getbrand :"+getbrand);

			Hashtable getbrandht=qm.getRow(getbrand);
			LogLevel.DEBUG(5,new Throwable(),"getbrandht :"+getbrandht);

			String strbrand=(String)getbrandht.get("brandname");
			LogLevel.DEBUG(5,new Throwable(),"strbrand :"+strbrand);

			//Query  to delete from user_loyalty_program based email_id.
			String strDelQry = "delete from user_loyalty_program where email_id='"+strcheck+"' and vendor_name='"+strbrand+"' and merchant_flag='yes'"; 
			LogLevel.DEBUG(5,new Throwable(),"strSqlQry :"+strDelQry);
			//Converting the String variable into int for calculation
			int result=qm.executeUpdate(strDelQry);
			LogLevel.DEBUG(5,new Throwable(),"result :"+result );
							
			//Ckecking if the result is less than '0' are not.
			if(result < 0)
			{
				//if yes then query is not execute properly.
				LogLevel.DEBUG(1,new Throwable(),"Failed to delete account details ");
				session.setAttribute("priority","1");
				session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'>Failed to fetch account details!</font> ");
				res.sendRedirect(baseURL+"/servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement1");
			}
			
			else
			{	
				//else query is executed sucessfully
				LogLevel.DEBUG(1,new Throwable(),"Successfully deleted account details ");
				session.setAttribute("sesErrorMsg", "");
				session.setAttribute("priority","1");
				session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'>Failed to fetch account details!</font> ");
				res.sendRedirect(baseURL+"/servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement1");
			}
			
	}catch(IOException ioe)
	{
		//If any exceptionsa re found in try block, this block will catch those exception
		LogLevel.ERROR(0,ioe,"problem Caught IOException if(modifyaccount) !! "+ioe);
		ioe.printStackTrace();
		//printstacktrace will print the line where exception is being arised
		session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
	}catch(Exception e)
	{
		//If any exceptionsa re found in try block, this block will catch those exception
		LogLevel.ERROR(0,e,"Problem Caught Exception if(modifyaccount)!! "+e);
		e.printStackTrace();
		//printstacktrace will print the line where exception is being arised
		session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
	}
	//returning the result to the place where this method is called.
	return null;
}

/* **************************************************************************************************************************************\
* This method is used to fetch emailid,loyalty_name and partners_list from user_loyalty_program table..
* @getEmailQry : carries the query to fetch loyalty_name, email_id from user_loyalty_program table.
* @getPartnrQry : carries the query to fetch partners_list from user_loyalty_program table.
\* **************************************************************************************************************************************/

public String redeempoints (HttpServletRequest req , HttpServletResponse res,HttpSession session, String strvendorName)
{
		//If parameter loyalprog is not null then value will store in the String if it is null then null will be stored.
		String strloyalprogramid=(req.getParameter("loyalprogramid")!=null)?(req.getParameter("loyalprogramid")):"";
		LogLevel.DEBUG(5,new Throwable(),"strloyalprogramid:"+strloyalprogramid );
		//If parameter emailid is not null then value will store in the String if it is null then null will be stored.
		String stremail=(req.getParameter("emailid")!=null)?(req.getParameter("emailid")):"";
		LogLevel.DEBUG(5,new Throwable(),"stremail:"+stremail );
		//If parameter loyalprog is not null then value will store in the String if it is null then null will be stored.
		String pagenumber = (req.getParameter("pagenumber")!=null)?(req.getParameter("pagenumber")):"0";
		LogLevel.DEBUG(5,new Throwable(),"pagenumber:"+pagenumber );
		//Declaring String variable for further usage.
	LogLevel.DEBUG(5,new Throwable(),"strvendorName:"+strvendorName );
		String strloyalid="";
		
		try
		{
			//Query  to fetch loyalty_id from loyalty_program based  on loyalty_name.
			String getidQry="select loyalty_id from loyalty_program where loyalty_id='"+strloyalprogramid+"'";
			LogLevel.DEBUG(5,new Throwable(),"getidQry :"+getidQry );
			//Getting the result of the query into Hashtbale.
			Hashtable ht1=qm.getRow(getidQry);
			strloyalid=String.valueOf(ht1.get("loyalty_id"));
			//Query  to fetch email_id from user_loyalty_program based  on loyalty_id.
			String getEmailQry = "select email_id from user_loyalty_program where loyalty_id='"+strloyalid+"' and email_id='"+stremail+"'"; 
			LogLevel.DEBUG(5,new Throwable(),"getEmailQry :"+getEmailQry );
			//getting the result from query into a vector.
			Vector result=qm.executeQuery(getEmailQry);
			LogLevel.DEBUG(5,new Throwable(),"result :"+result );
			//Query  to fetch loyalty_id,loyalty_name, partners_list from loyalty_program based  on loyalty_id.
			String getNameQry= "select loyalty_id,loyalty_name, partners_list,vendor_name from loyalty_program where loyalty_id="+strloyalid;
			LogLevel.DEBUG(5,new Throwable(),"getNameQry :"+getNameQry );
			//getting the result from query into a vector.
			Vector partners=qm.executeQuery(getNameQry);
			LogLevel.DEBUG(5,new Throwable(),"partners :"+partners );

			if(result == null || result.size()<=0)
			{
				//if yes then query is not execute properly.
				LogLevel.DEBUG(1,new Throwable(),"Failed to fetch account details ");
				session.setAttribute("priority","1");
				session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'>Failed to fetch account details!</font> ");
				res.sendRedirect("../jsp/admin/batterystore/brandloyaltyprogram/brandredeempoints.jsp");
			

			}
			else
			{	//else query is executed sucessfully
				LogLevel.DEBUG(1,new Throwable(),"Successfully fetched account details ");
				session.setAttribute("sesErrorMsg", "");
				
				if(session.getAttribute("sesemailid")!=null)
				session.removeAttribute("sesemailid");
				session.setAttribute("sesemailid", result);
				
				if(session.getAttribute("sesAllPartners")!=null)
				session.removeAttribute("sesAllPartners");
				session.setAttribute("sesAllPartners",partners);
				res.sendRedirect("../jsp/admin/batterystore/brandloyaltyprogram/brandredeempoints.jsp");
			}
				
			
		}catch(IOException ioe)
		{
			//If any exceptionsa re found in try block, this block will catch those exception
			LogLevel.ERROR(0,ioe,"problem Caught IOException if(redeemaccount) !! "+ioe);
			ioe.printStackTrace();
			//printstacktrace will print the line where exception is being arised
			session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
		}catch(Exception e)
		{
			//If any exceptionsa re found in try block, this block will catch those exception
			LogLevel.ERROR(0,e,"Problem Caught Exception if(redeemaccount)!! "+e);
			e.printStackTrace();
			//printstacktrace will print the line where exception is being arised
			session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
		}
		//returning the result to the place where this method is called.
		return null;
	}

/* **************************************************************************************************************************************\
* This method is used to fetch partners_list from user_loyalty_program table..
* @getPntsQry : carries the query to fetch loyalty_points,value from user_loyalty_program table.
* @pointsQry : carries the query to fetch loyalty_points, value from loyalty_program table.
\* **************************************************************************************************************************************/

public String getSelPartner (HttpServletRequest req , HttpServletResponse res,HttpSession session, String strvendorName)
{		
		//If parameter emailid is not null then value will store in the String if it is null then null will be stored.
		String stremailid = (req.getParameter("emailid")!=null)?(req.getParameter("emailid")):"0";
		LogLevel.DEBUG(5,new Throwable(),"stremailid :"+stremailid );
		//If parameter loyaltypartner is not null then value will store in the String if it is null then null will be stored.
		String strloyaltypartner = (req.getParameter("loyaltypartner")!=null)?(req.getParameter("loyaltypartner")):"0";
		LogLevel.DEBUG(5,new Throwable(),"strloyaltypartner :"+strloyaltypartner );
		//If parameter loyaltyid is not null then value will store in the String if it is null then null will be stored.
		String strloyaltyid = (req.getParameter("loyaltyid")!=null)?(req.getParameter("loyaltyid")):"0";
		LogLevel.DEBUG(5,new Throwable(),"strloyaltyid :"+strloyaltyid );
		

		
		System.out.println("ajaxmodelname called");
		//Declaring String variable for further usage.
        String strRes="";
		try
		{

			ServletOutputStream out=res.getOutputStream();	
			//Query  to fetchpoints_earned from user_loyalty_program based  on email_id.
			String getPntsQry="select points_earned from user_loyalty_program where email_id='"+stremailid+"' and loyalty_id='"+strloyaltyid+"' and merchant_flag='yes'"; 
			LogLevel.DEBUG(5,new Throwable(),"getPntsQry :"+getPntsQry);
			//getting the result from query into a vector.
			Vector loyalUserPoints=qm.executeQuery(getPntsQry);
			LogLevel.DEBUG(5,new Throwable(),"loyalUserPoints :"+loyalUserPoints);
			
			//Query  to fetch loyalty_points, value from loyalty_program based  on loyalty_id.
			String getloyalpntsQry="select loyalty_points, value from loyalty_program where loyalty_id="+strloyaltyid;
			LogLevel.DEBUG(5,new Throwable(),"getloyalpntsQry :"+getloyalpntsQry);
			//getting the result from query into a vector.
			Vector loyalPoints=qm.executeQuery(getloyalpntsQry);
	
			for(int i=0;i<loyalPoints.size();i++)
			{
				//Getting the result of the query into Hashtbale.
				Hashtable getPnt=(Hashtable)loyalPoints.get(i);
				String strpoints=String.valueOf(getPnt.get("loyalty_points"));
				LogLevel.DEBUG(5,new Throwable(),"strpoints :"+strpoints);
				//Getting the result of the query into Hashtbale key.
				String strvalue=String.valueOf(getPnt.get("value"));
				LogLevel.DEBUG(5,new Throwable(),"strvalue :"+strvalue);

				for(int j=0; j<loyalUserPoints.size(); j++)
				{
						//Getting the result of the query into Hashtbale.
						Hashtable Points=(Hashtable)loyalUserPoints.get(j);
						String strloyalpoints=String.valueOf(Points.get("points_earned"));
						LogLevel.DEBUG(5,new Throwable(),"strloyalpoints :"+strloyalpoints );
						//Dispalying the String variables.
						strRes=strRes+"<tr height='40'><td class='subheading' >Points<font color='FF0000'>*</font></td>";									
						strRes=strRes+"<td>:&nbsp;<input type='text' name='points' disabled='true' value='"+strpoints+"' size='10' ></td>";										
						strRes=strRes+"&nbsp;&nbsp;&nbsp;<td class='subheading'>Value(Rs)<font color='FF0000'>*</font></td>";
						strRes=strRes+"<td>:&nbsp;<input type='text' name='value' disabled='true' value='"+strvalue+"'  size='10' ></td></tr>";
						strRes=strRes+"&nbsp;&nbsp;&nbsp;<td class='subheading' >Points Earned<font color='FF0000'>*</font></td>";											
						strRes=strRes+"<td>:&nbsp;<input type='text' name='pointsearned'  disabled='true' value='"+strloyalpoints+"' size='10' ></td>";
						strRes=strRes+"&nbsp;&nbsp;&nbsp;<td class='subheading' >Points to redeem<font color='FF0000'>*</font></td>";											
						strRes=strRes+"<td>:&nbsp;<input type='text' name='pointsredeemed' value=''  size='10' maxlength='6' ></td></tr>";
				
				}	
				LogLevel.DEBUG(5,new Throwable(),"strRes :"+strRes );				
			}
	 }catch(Exception e)
	 {
			//If any exceptionsa re found in try block, this block will catch those exception
			LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
			e.printStackTrace();
			//printstacktrace will print the line where exception is being arised
			session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
	 }
	 //returning the result to the place where this method is called.
	 return strRes;					 
}

/* **************************************************************************************************************************************\
* This method is used to update loyalty_points, value and loyalty_name from user_loyalty_program table..
* @updateQry : carries the query to update loyalty_points,value in user_loyalty_program table.
\* **************************************************************************************************************************************/

public String redeemloyaltypoints (HttpServletRequest req , HttpServletResponse res,HttpSession session, String strvendorName)
{
		//If parameter emailid is not null then value will store in the String if it is null then null will be stored.	
		String stremailid = (req.getParameter("email_id")!=null)?(req.getParameter("email_id")):"";
		LogLevel.DEBUG(5,new Throwable(),"stremailid :"+stremailid);
		//If parameter loyaltyid is not null then value will store in the String if it is null then null will be stored.
		String strloyaltyid = (req.getParameter("loyaltyid")!=null)?(req.getParameter("loyaltyid")):"";
		LogLevel.DEBUG(5,new Throwable(),"strloyaltyid :"+strloyaltyid);
		//If parameter points is not null then value will store in the String if it is null then null will be stored.	
		String strpoints = (req.getParameter("points")!=null)?(req.getParameter("points")):"";
		LogLevel.DEBUG(5,new Throwable(),"strpoints :"+strpoints);
		//If parameter loyalpoints is not null then value will store in the String if it is null then null will be stored.	
		String strpointsearnd = (req.getParameter("loyalpoints")!=null)?(req.getParameter("loyalpoints")):"";
		LogLevel.DEBUG(5,new Throwable(),"strpointsearnd :"+strpointsearnd);
		//If parameter redpoints is not null then value will store in the String if it is null then null will be stored.	
		String strpointsredeemd = (req.getParameter("redpoints")!=null)?(req.getParameter("redpoints")):"";
		LogLevel.DEBUG(5,new Throwable(),"strpointsredeemd :"+strpointsredeemd);
		//If parameter value is not null then value will store in the String if it is null then null will be stored.	
		String strvalue = (req.getParameter("value")!=null)?(req.getParameter("value")):"";
		LogLevel.DEBUG(5,new Throwable(),"strvalue :"+strvalue);
		//If parameter loyaltypartner is not null then value will store in the String if it is null then null will be stored.	
		String strredeempartner = (req.getParameter("loyaltypartner")!=null)?(req.getParameter("loyaltypartner")):"";
		LogLevel.DEBUG(5,new Throwable(),"strredeempartner :"+strredeempartner);
		
		//Declaring String variable for further usage.
		int result=0;
		String strRes="";
		try
		{

				//Converting the String variable into int for calculation
				int point1=Integer.parseInt(strpoints);
				//Converting the String variable into int for calculation
				int point2=Integer.parseInt(strpointsearnd);
				//Converting the String variable into int for calculation
				int point3=Integer.parseInt(strpointsredeemd);
				//Converting the String variable into int for calculation
				int value1=Integer.parseInt(strvalue);
				int point4=(point2-point3);
				int addvalue=(point4*value1)/point1;
				LogLevel.DEBUG(5,new Throwable(),"addvalue bhanu prasad  :"+addvalue);

				if(addvalue == 0)
				{
						String deleteQry="delete from user_loyalty_program where loyalty_id='"+strloyaltyid+"' and merchant_flag='yes'";
						result=qm.executeUpdate(deleteQry);
				}
				else
				{
				String updateQry = "update user_loyalty_program set points_earned="+point4+",value_earned="+addvalue+", redeemed_at='"+strredeempartner+"' where loyalty_id='"+strloyaltyid+"' and email_id='"+stremailid+"' and merchant_flag='yes'";
				//Converting the String variable into int for calculation
				result=qm.executeUpdate(updateQry);
				LogLevel.DEBUG(5,new Throwable(),"updateQry :"+updateQry);
				}

				if(result < 0)
				{
					//if yes then query is not execute properly.
					LogLevel.DEBUG(1,new Throwable(),"Failed to redeem points");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'>Failed to redeem points!</font>");
					res.sendRedirect("../jsp/admin/batterystore/brandloyaltyprogram/brandredeemloyalty.jsp");
					
				}
				else
				{
					//else query is executed sucessfully
					LogLevel.DEBUG(1,new Throwable(),"Successfully Redeemed Your Points ");
					session.setAttribute("sesErrorMsg", "<font color='#2364b1' class='vrb10'>Successfully Redeemed "+strpointsredeemd+" points</font> ");
					res.sendRedirect("../jsp/admin/batterystore/brandloyaltyprogram/brandredeemloyalty.jsp");
				}
		}catch(IOException ioe)
		{		
				//If any exceptionsa re found in try block, this block will catch those exception
				LogLevel.ERROR(0,ioe,"problem Caught IOException if(submit) !! "+ioe);
				//printstacktrace will print the line where exception is being arised
				ioe.printStackTrace();
			
				session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
		}catch(Exception e)
		{
				//If any exceptionsa re found in try block, this block will catch those exception
				LogLevel.ERROR(0,e,"Problem Caught Exception if(submit)!! "+e);
				//printstacktrace will print the line where exception is being arised
				e.printStackTrace();
				
				session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
		}
		//returning the result to the place where this method is called.
		return null;
}

/* **************************************************************************************************************************************\
* This method is used to check the availability of the user from user_profiles table.
* @checkUserQry : carries the query to check the availability of email_id from user_profiles table.
\* **************************************************************************************************************************************/

public String loyaltyusercheck (HttpServletRequest req , HttpServletResponse res,HttpSession session, String strvendorName)
{
		//If parameter username is not null then value will store in the String if it is null then null will be stored.	
		String strusername = (req.getParameter("username")!=null)?(req.getParameter("username")):"";
		LogLevel.DEBUG(5,new Throwable(),"strusername :"+strusername);
		String brandloginname = (req.getParameter("sesusername")!=null)?(req.getParameter("sesusername")):"";
		LogLevel.DEBUG(5,new Throwable(),"brandloginname :"+brandloginname);
					
		System.out.println("ajaxmodelname called");

		try
		{ 
			ServletOutputStream out=res.getOutputStream();
			//query to fetch email_id from user_loyalty_program.
			String vendorname="select brandname from vendor_master where vendor_name='"+brandloginname+"'";
			Hashtable ht1=qm.getRow(vendorname);
			String brandname=(String)ht1.get("brandname");
			LogLevel.DEBUG(5,new Throwable(),"brandname :"+brandname );
			String loyaltyids2="";
			String loyaltyids="select distinct(loyalty_id) from user_loyalty_program where vendor_name='"+brandname+"'";
			LogLevel.DEBUG(5,new Throwable(),"loyaltyids :"+loyaltyids );
			ArrayList htids=new ArrayList();
			htids=qm.getField(loyaltyids);
			for(int i=0;i<htids.size();i++)
			{
				if(loyaltyids2.equals(""))
				loyaltyids2=htids.get(i).toString();
				else
				loyaltyids2=loyaltyids2+"','"+htids.get(i).toString();
			}	
			LogLevel.DEBUG(5,new Throwable(),"loyaltyids2 :"+loyaltyids2 );
				
 			String checkUserQry = "SELECT a.email_id FROM user_loyalty_program a, loyalty_program b WHERE a.email_id='"+strusername+"' and a.merchant_flag='yes' and a.loyalty_id in('"+loyaltyids2+"')"; 
			LogLevel.DEBUG(5,new Throwable(),"checkUserQry :"+checkUserQry );
			//Getting the result of the query into Hashtbale.
			Hashtable checkht = qm.getRow(checkUserQry);
				
			if(checkht.isEmpty())
			{ //else query is executed sucessfully
				LogLevel.DEBUG(1,new Throwable(),"This user does not hold this program" +strusername);
				out.println("This user does not hold any program");
					
			}
			else
			{ //else query is executed sucessfully
				LogLevel.DEBUG(1,new Throwable(),"");
				out.println("This user holds program");
					
			}
		}catch(IOException ioe)
		{ 
			//If any exceptionsa re found in try block, this block will catch those exception
			LogLevel.ERROR(0,ioe,"problem Caught IOException -- if(userLogin) !! "+ioe);
			//printstacktrace will print the line where exception is being arised
			ioe.printStackTrace(); 
			
			session.setAttribute("sesErrorMsg",	"Error...Please Try Again" );  
		}catch(Exception e)
		{ 
			//If any exceptionsa re found in try block, this block will catch those exception
			LogLevel.ERROR(0,e,"problem Caught Exception -- if(userLogin) !! "+e);
			//printstacktrace will print the line where exception is being arised
			e.printStackTrace(); 
			
			session.setAttribute("sesErrorMsg",	"Error...Please Try Again" );
		}
		//returning the result to the place where this method is called.	
		return null;
} 

/* **************************************************************************************************************************************\
* This method is used to check the availability of the user from user_profiles table.
* @checkUserQry : carries the query to check the availability of email_id from user_profiles table.
\* **************************************************************************************************************************************/

public String funonclickprog (HttpServletRequest req , HttpServletResponse res,HttpSession session, String strvendorName)
{
	
		//If parameter username is not null then value will store in the String if it is null then null will be stored.	
		String stremailid = (req.getParameter("emailid")!=null)?(req.getParameter("emailid")):"";
		LogLevel.DEBUG(5,new Throwable(),"stremailid :"+stremailid);

		
		System.out.println("ajaxmodelname called");
		//Declaring String variable for further usage.		
		String strRes="";

		try
		{								
				
		if(strvendorName==null)
			{
				strRes="<input type='hidden' value="+strvendorName+" name='sesusername'>";
			}
			else
			{
			ServletOutputStream out=res.getOutputStream();

			String strSqlQry5="select vendor_name, brandname from vendor_master where vendor_name='"+strvendorName+"'";
			
			Hashtable loyalht=qm.getRow(strSqlQry5);
			String vendorname=(String)loyalht.get("vendor_name");
			LogLevel.DEBUG(5,new Throwable(),"vendorname :"+vendorname );

			String brandname=(String)loyalht.get("brandname");
			LogLevel.DEBUG(5,new Throwable(),"brandname :"+brandname );
			
			String strSqlQry="select a.loyalty_id, b.partners_list from user_loyalty_program a, loyalty_program b where a.email_id='"+stremailid+"' and a.loyalty_id=b.loyalty_id and a.merchant_flag='yes'";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQry :"+strSqlQry );

			Vector loyalVectr=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"loyalVectr :"+loyalVectr );
			for(int i=0;i<loyalVectr.size();i++)
			{

				Hashtable partnerht=(Hashtable)loyalVectr.get(i);
				LogLevel.DEBUG(5,new Throwable(),"partnerht :"+partnerht );
				
				String partners_list=(String)partnerht.get("partners_list");
				LogLevel.DEBUG(5,new Throwable(),"partners_list :"+partners_list );
				String loyalty_id=String.valueOf(partnerht.get("loyalty_id"));
				LogLevel.DEBUG(5,new Throwable(),"loyalty_id :"+loyalty_id );

				if(partners_list!=null)
				{
					String[] tempArr=partners_list.split(",");
					LogLevel.DEBUG(5,new Throwable(),"tempArr.length :"+tempArr.length);
					LogLevel.DEBUG(5,new Throwable(),"tempArr:"+tempArr);
					if(tempArr.length>0)
					{
						String strTempValue="";
						for (int k=0;k<tempArr.length ;k++)
						{	
							//Getting the partner name in to strTempValue
							strTempValue=tempArr[k];
							LogLevel.DEBUG(5,new Throwable(),"strTempValue:"+strTempValue);

							if(strTempValue.equals(brandname))
							{
								
								String strSqlQry2="select loyalty_name,loyalty_id from loyalty_program where loyalty_id="+loyalty_id;
								LogLevel.DEBUG(5,new Throwable(),"strSqlQry2:"+strSqlQry2);

								Hashtable ht3=qm.getRow(strSqlQry2);
								String strloyalname=(String)ht3.get("loyalty_name");
								String strloyaltyid=String.valueOf(ht3.get("loyalty_id"));
								LogLevel.DEBUG(5,new Throwable(),"strloyalname:"+strloyalname);

								//Dispalying the String variables.
								strRes=strRes+"<table  width='200%' border='0'><tr  class='insidecontent'><td width='50%'></td><td width='150%' valign='middle' class='insidecontent'><input type='checkbox' name='programname' value='"+strloyaltyid+"' size='3'>"+strloyalname+"</td></tr></table>";													
								//out.println("program");
							}

						}
									
						
					}
					
				}
			}
		}
			
																																					
		}catch(Exception e)
		{
				//If any exceptionsa re found in try block, this block will catch those exception
				LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
				//printstacktrace will print the line where exception is being arised
				e.printStackTrace();
				session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
		}
		//returning the result to the place where this method is called.
		return strRes;
	}

	/* ******************************************************************\
* This method is to fetch Reported Abuses.
* @fetchRptQry : carries the query to fetch report_id,email_id,report_desc,creation_date from report_abuse_detl table using report_id.
\* ********************************************************************/
public List<Hashtable> fetchserviceproviders(HttpServletRequest req , HttpServletResponse res,HttpSession session)
{
try
{
	String pagenumber = (req.getParameter("pagenumber")!=null)?(req.getParameter("pagenumber")):"0";
	LogLevel.DEBUG(5,new Throwable(),"pagenumber :"+pagenumber );

	String pagetype = (req.getParameter("pagetype")!=null)?(req.getParameter("pagetype")):"0";
	LogLevel.DEBUG(5,new Throwable(),"pagetype :"+pagetype );

	String lastpage = (req.getParameter("lastpage")!=null)?(req.getParameter("lastpage")):"0";
	LogLevel.DEBUG(5,new Throwable(),"lastpage :"+lastpage );

	String strDateOpt  = (req.getParameter("dates")!=null)?req.getParameter("dates"):"";
	LogLevel.DEBUG(5, new Throwable(), "strDateOpt :"+ strDateOpt);

	String txtFromDate  = (req.getParameter("txtFromDate")!=null)?req.getParameter("txtFromDate"):"";
	String txtToDate  = (req.getParameter("txtToDate")!=null)?req.getParameter("txtToDate"):"";

Calendar calendar = new GregorianCalendar();
	int curMonth=calendar.get(Calendar.MONTH)+1;
	int curYear=calendar.get(Calendar.YEAR);
	int curDate=calendar.get(Calendar.DAY_OF_MONTH);
	if(strDateOpt.equals("current"))
	{
		txtFromDate="01-"+curMonth+"-"+curYear;
		txtToDate=curDate+"-"+curMonth+"-"+curYear;
	}
	else if(strDateOpt.equals("candpmonth"))
	{
		int prvsMonth=curMonth-1;
		int prvsYear=curYear;
		if(prvsMonth==0)
		{
			prvsMonth=12;
			prvsYear=curYear-1;
		}
		txtFromDate="01-"+prvsMonth+"-"+prvsYear;
		txtToDate=curDate+"-"+curMonth+"-"+curYear;
	}
	else if(strDateOpt.equals("sixmonths"))
	{
		int prvsMonth=curMonth-6;
		int prvsYear=curYear;
		if(prvsMonth<=0)
		{
			prvsMonth=prvsMonth+12;
			prvsYear=curYear-1;
		}
		txtFromDate="01-"+prvsMonth+"-"+prvsYear;
		txtToDate=curDate+"-"+curMonth+"-"+curYear;
	}
	String strConditions="";
	SimpleDateFormat sdfDate=new SimpleDateFormat("dd-MM-yyyy");
	Date fromDate=sdfDate.parse(txtFromDate); 
	Date toDate=sdfDate.parse(txtToDate); 
	SimpleDateFormat sdfString=new SimpleDateFormat("yyyy-MM-dd");
	String strFromDate=sdfString.format(fromDate); 
	LogLevel.DEBUG(5, new Throwable(), "strFromDate :"+ strFromDate);

	String strToDate=sdfString.format(toDate); 
	LogLevel.DEBUG(5, new Throwable(), "strToDate :"+ strToDate);
	
	if(strConditions.equals(""))
	{
		strConditions=" date(creation_date) between '"+strFromDate+"' and '"+strToDate+"'";
	}
	else
	{
		strConditions=strConditions+" and date(creation_date) between '"+strFromDate+"' and '"+strToDate+"'";
	}
	//this query is to fetch the Reported abuses based on the given date.
	String strQuery="select distinct(mobile_serviceprovider) from consumer_reedem_detl where "+strConditions+" and recharge_status='no'"; 
	LogLevel.DEBUG(5, new Throwable(), "strQuery:" + strQuery);

	Vector serviceprovider=qm.executeQuery(strQuery);
	LogLevel.DEBUG(5, new Throwable(), "serviceprovider:" + serviceprovider);

	if(serviceprovider==null || serviceprovider.size() <=0 )
	{
		LogLevel.DEBUG(1, new Throwable(),"No reportabuses available or DB failure. ");
	}
	List<Hashtable> serviceproviderList=new Vector<Hashtable>();
	for (int i=0;i<serviceprovider.size() ;i++ )
	{
		Hashtable htCurrent=(Hashtable)serviceprovider.get(i);
		htCurrent.put("pagenumber",pagenumber);
		LogLevel.DEBUG(5, new Throwable(), "txtFromDate :"+ txtFromDate);
	
		htCurrent.put("txtFromDate",txtFromDate);
		LogLevel.DEBUG(5, new Throwable(), "txtToDate :"+ txtToDate);
		htCurrent.put("txtToDate",txtToDate);
		serviceproviderList.add(htCurrent);
	}
	return serviceproviderList;
}catch (Exception e)
 {
	LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
	e.printStackTrace();
	return null;
 }
}
/* ******************************************************************\
* This method is to fetch Reported Abuses.
* @fetchRptQry : carries the query to fetch report_id,email_id,report_desc,creation_date from report_abuse_detl table using report_id.
\* ********************************************************************/
public String fetchreedempointstorecharge(HttpServletRequest req , HttpServletResponse res,HttpSession session)
{
try
{
	String serviecprovider = (req.getParameter("serviecprovider")!=null)?(req.getParameter("serviecprovider")):"0";
	LogLevel.DEBUG(5,new Throwable(),"serviecprovider :"+serviecprovider );

	String pagenumber = (req.getParameter("pagenumber")!=null)?(req.getParameter("pagenumber")):"0";
	LogLevel.DEBUG(5,new Throwable(),"pagenumber :"+pagenumber );

	String pagetype = (req.getParameter("pagetype")!=null)?(req.getParameter("pagetype")):"0";
	LogLevel.DEBUG(5,new Throwable(),"pagetype :"+pagetype );

	String lastpage = (req.getParameter("lastpage")!=null)?(req.getParameter("lastpage")):"0";
	LogLevel.DEBUG(5,new Throwable(),"lastpage :"+lastpage );

	String strDateOpt  = (req.getParameter("dates")!=null)?req.getParameter("dates"):"";
	LogLevel.DEBUG(5, new Throwable(), "strDateOpt :"+ strDateOpt);

	String txtFromDate  = (req.getParameter("txtFromDate")!=null)?req.getParameter("txtFromDate"):"0000-00-00 00:00:00";
	
	String txtToDate  = (req.getParameter("txtToDate")!=null)?req.getParameter("txtToDate"):"0000-00-00 00:00:00";

	String strRes="";
	Calendar calendar = new GregorianCalendar();
	int curMonth=calendar.get(Calendar.MONTH)+1;
	int curYear=calendar.get(Calendar.YEAR);
	int curDate=calendar.get(Calendar.DAY_OF_MONTH);
	if(strDateOpt.equals("current"))
	{
		txtFromDate="01-"+curMonth+"-"+curYear;
		txtToDate=curDate+"-"+curMonth+"-"+curYear;
	}
	else if(strDateOpt.equals("candpmonth"))
	{
		int prvsMonth=curMonth-1;
		int prvsYear=curYear;
		if(prvsMonth==0)
		{
			prvsMonth=12;
			prvsYear=curYear-1;
		}
		txtFromDate="01-"+prvsMonth+"-"+prvsYear;
		txtToDate=curDate+"-"+curMonth+"-"+curYear;
	}
	else if(strDateOpt.equals("sixmonths"))
	{
		int prvsMonth=curMonth-6;
		int prvsYear=curYear;
		if(prvsMonth<=0)
		{
			prvsMonth=prvsMonth+12;
			prvsYear=curYear-1;
		}
		txtFromDate="01-"+prvsMonth+"-"+prvsYear;
		txtToDate=curDate+"-"+curMonth+"-"+curYear;
	}
	String strConditions="";
	SimpleDateFormat sdfDate=new SimpleDateFormat("dd-MM-yyyy");
	Date fromDate=sdfDate.parse(txtFromDate); 
	Date toDate=sdfDate.parse(txtToDate); 
	SimpleDateFormat sdfString=new SimpleDateFormat("yyyy-MM-dd");
	String strFromDate=sdfString.format(fromDate); 
	LogLevel.DEBUG(5, new Throwable(), "strFromDate :"+ strFromDate);

	String strToDate=sdfString.format(toDate); 
	LogLevel.DEBUG(5, new Throwable(), "strToDate :"+ strToDate);
	
	if(strConditions.equals(""))
	{
		strConditions=" date(creation_date) between '"+strFromDate+"' and '"+strToDate+"'";
	}
	else
	{
		strConditions=strConditions+" and date(creation_date) between '"+strFromDate+"' and '"+strToDate+"'";
	}
	if(lastpage.equals("0"))
	{
		lastpage="1";
	}
	if(pagetype.equals("first"))
	{
		pagenumber="1"; 
	}
	if(pagetype.equals("previous"))
	{
		int pn=Integer.parseInt(pagenumber)-1;
		pagenumber=Integer.toString(pn);
	}
	if(pagetype.equals("next"))
	{
		int pn=Integer.parseInt(pagenumber)+1;
		pagenumber=Integer.toString(pn);
		LogLevel.DEBUG(5,new Throwable(),"test:"+pagenumber );
	}
	if(pagetype.equals("last"))
	{
		pagenumber=lastpage;
	}
	if(pagenumber.equals("0"))
	{
		pagenumber="1";
	}
	int plans_per_page=10;
	int Startlimt=plans_per_page*(Integer.parseInt(pagenumber)-1);
	String Startindex=Integer.toString(Startlimt);


String strQuery="";
String fetchRptQry = "";
String strcount="";

if(serviecprovider.equals("overall"))
	{
	//this query is to fetch the Reported abuses based on the given date.
	strQuery="select reedem_id,email_id,mobile_number,recharge_amount,location,mobile_serviceprovider,rechrg_flag from consumer_reedem_detl where "+strConditions+" and recharge_status='no' order by creation_date desc limit "+Startindex+",10"; 
	LogLevel.DEBUG(5, new Throwable(), "strQuery:" + strQuery);
	}
	else
	{

	strQuery="select reedem_id,email_id,mobile_number,recharge_amount,location,mobile_serviceprovider,rechrg_flag from consumer_reedem_detl where "+strConditions+"  and mobile_serviceprovider='"+serviecprovider+"' and recharge_status='no' order by creation_date desc limit "+Startindex+",10"; 
	LogLevel.DEBUG(5, new Throwable(), "strQuery:" + strQuery);

	}

	Vector reportabuses=qm.executeQuery(strQuery);
	LogLevel.DEBUG(5, new Throwable(), "reportabuses:" + reportabuses);

	
	if(serviecprovider.equals("overall"))
	{
	//Query to get the count of the Reported Abuses.
		fetchRptQry = "select count(*) as count from consumer_reedem_detl where "+strConditions+" and recharge_status='no'"; 
		LogLevel.DEBUG(5,new Throwable(),"fetchRptQry :"+fetchRptQry );
		Hashtable fetchrptht = qm.getRow(fetchRptQry);
		strcount=String.valueOf(fetchrptht.get("count"));
		LogLevel.DEBUG(5,new Throwable(),"strcount :"+strcount );
	}
	else
	{

//Query to get the count of the Reported Abuses.
		 fetchRptQry = "select count(*) as count from consumer_reedem_detl where "+strConditions+"  and mobile_serviceprovider='"+serviecprovider+"' and recharge_status='no'"; 
		LogLevel.DEBUG(5,new Throwable(),"fetchRptQry :"+fetchRptQry );
		Hashtable fetchrptht = qm.getRow(fetchRptQry);
		strcount=String.valueOf(fetchrptht.get("count"));
		LogLevel.DEBUG(5,new Throwable(),"strcount :"+strcount );

	}
	if(reportabuses==null || reportabuses.size() <=0 )
	{
		LogLevel.DEBUG(1, new Throwable(),"No reportabuses available or DB failure. ");

		strRes=strRes+"<table  align='center' width='300' border='0'  cellspacing='1'  cellpadding='2' bgcolor='#FFFFFF'>";
		strRes=strRes+"<tr><td>There are no Reedem points to recharge.</td></tr></table>";
	}
	else
	{
		
		
					strRes=strRes+"<table border='0'><tr height='5'><td colspan='6' ></td></tr></table>";	
					strRes=strRes+"<tr><td colspan='6' ><hr width='600' align='center'  color='#BEADCB'></td></tr>";	
					strRes=strRes+"<table  align='center' width='200' border='0'  cellspacing='1'  cellpadding='2' bgcolor='#FFFFFF'>";
					strRes=strRes+"<tr><td align='center'><a href=\"javascript:deleterecharge();\" class='button4'>Delete</a></td>";
					strRes=strRes+"<td align='center'><a href=\"javascript:recharge();\" class='button4'>Recharge</a></td>";
					strRes=strRes+"</tr></table>";
					strRes=strRes+"<table  align='center' width='200' border='0'  cellspacing='1'  cellpadding='2' bgcolor='#FFFFFF'>";
					//pagination starts here
					strRes=strRes+"<tr><td colspan='8' align='center'><table  align='center' width='200' border='0'><tr><td>";


					int TotalCount=Integer.parseInt(strcount);
					int opinions_per_page=10;
					int tp=((int)(Math.ceil((double)TotalCount/opinions_per_page)));
					String Lastpage=Integer.toString(tp);
					LogLevel.DEBUG(5,new Throwable(),"testlastpage:"+Lastpage );
					if(pagenumber.equals("1"))
					{
						strRes=strRes+"<font align='center' class='blue2'>Previous</font>&nbsp;&nbsp;";
					}
					else
					{
						strRes=strRes+"<font align='center'> <a class='blue1' align='left' href=\"javascript:funOnClickPreviousonlinesurveys('"+pagenumber+"','"+serviecprovider+"');\" >Previous</a> </font>&nbsp;&nbsp;";
					}
					if(pagenumber.equals(Lastpage))
					{
						strRes=strRes+"<font class='blue2' align='right'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Next</font>";
					}
					else
					{
						strRes=strRes+"<font align='right'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class='blue1' href=\"javascript:funOnClickNextonlinesurveys('"+pagenumber+"','"+serviecprovider+"');\" >Next</a> </font>";
					}
					strRes=strRes+"</td></tr></table></td></tr>";
					//pagination ends here

		
				
		
		
					strRes=strRes+"<table width ='600' cellspacing = '2' cellpadding = '2'  bgcolor='#BEADCB' align='center' border='0'>";	
					strRes=strRes+"<tr><td><table width = '100%' cellspacing = '0' cellpadding = '0'  bgcolor='#FFFFFF' align='center'>";
		
					strRes=strRes+"<tr><td><table width='600' align='center' bgcolor='#FFFFFF'><tr><td align='center' valign='middle' class='prodheading'>S.NO</td>";
					strRes=strRes+"<td align='center' valign='middle' class='prodheading'>UserEmailID</td>";
					strRes=strRes+"<td align='center' valign='middle' class='prodheading'>MobileNumber</td>";
					strRes=strRes+"<td align='center' valign='middle' class='prodheading'>Amount in Rs.</td>";
					strRes=strRes+"<td align='center' valign='middle' class='prodheading'>Serviceprovider</td>";
					strRes=strRes+"<td align='center' valign='middle' class='prodheading'>Location</td>";
					strRes=strRes+"<td align='center' valign='middle' class='prodheading'>Reedem Type</td>";
					strRes=strRes+"<td align='center' valign='middle' class='prodheading'>Address</td></tr>";
					int j=1;
					for ( int i=0; i<reportabuses.size() ; i++)
						{
							Hashtable ht=(Hashtable)reportabuses.get(i);
							String strreedemid=String.valueOf(ht.get("reedem_id"));
							String struseremailid=String.valueOf(ht.get("email_id"));
							String strMobileno=String.valueOf(ht.get("mobile_number"));
							String strRechargeamount=String.valueOf(ht.get("recharge_amount"));
							String strserviceprovider=String.valueOf(ht.get("mobile_serviceprovider"));
							String strlocation=String.valueOf(ht.get("location"));
							String rechrg_flag=String.valueOf(ht.get("rechrg_flag"));	
							String fetchaddress="select address from user_profile where email_id='"+struseremailid+"'";
							Hashtable htfetchaddress=qm.getRow(fetchaddress);	
							String address=(String)htfetchaddress.get("address");
							if (rechrg_flag.equals("Cash Back"))
							{
								address=address;
							}
							else
							{
								address="NA";
							}
							//String strDesc=String.valueOf(ht.get("report_desc"));
							
							int pagenum=Integer.parseInt(pagenumber);
							int test=10*(pagenum-1)+j;
							strRes=strRes+"<tr><td align='center' valign='middle' class='table1'>"+test+"";
							strRes=strRes+"<input type='checkbox' name='check' value="+strreedemid+"></td>";
							strRes=strRes+"<td align='center' valign='middle' class='table1'>"+struseremailid+"</td>";
							strRes=strRes+"<td align='center' valign='middle' class='table1'>"+strMobileno+"</td>";
							strRes=strRes+"<td align='center' valign='middle' class='table1'>"+strRechargeamount+"</td>";
							strRes=strRes+"<td align='center' valign='middle' class='table1'>"+strserviceprovider+"</td>";
							strRes=strRes+"<td align='center' valign='middle' class='table1'>"+strlocation+"</td>";
							strRes=strRes+"<td align='center' valign='middle' class='table1'>"+rechrg_flag+"</td>";
							strRes=strRes+"<td align='center' valign='middle' class='table1'>"+address+"</td></tr>";
							j++;
						}
						strRes=strRes+"</table></td></tr></table></td></tr> ";
							strRes=strRes+"</table></td></tr>";
	}
	return strRes;
	
}catch (Exception e)
 {
	LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
	e.printStackTrace();
	return null;
 }
}
/* ************************************************************************\
* This method is used to delete Reported Abuses.
* @delRptQry : carries the query to delete Reported Abuses from report_abuse_detl using report_id.
\* ***********************************************************************/
public int deleterechargeforreedempoints (HttpServletRequest req , HttpServletResponse res,HttpSession session)
{
	String pagenumber = (req.getParameter("pagenumber")!=null)?(req.getParameter("pagenumber")):"0";
	String[] strcheck=(req.getParameterValues("check")!=null)?(req.getParameterValues("check")):null;
	String retid="";
	String reedemids="";
	String delRptQry ="";
	ArrayList al = new ArrayList(); 
	try
	{		
	if(strcheck!=null)
	{
		for (int i=0;i<strcheck.length ;i++ )
		{
			if(reedemids.equals(""))
				reedemids=strcheck[i];
			else
				reedemids=reedemids+","+strcheck[i];
		}
	}
	LogLevel.DEBUG(5,new Throwable(),"reedemids :"+reedemids );
	// This Query is to delete Reports from report_abuse_detl using report_id.
	delRptQry = "delete from consumer_reedem_detl where reedem_id in ("+reedemids+")";
	LogLevel.DEBUG(5,new Throwable(),"delRptQry :"+delRptQry );
	int k=qm.executeUpdate(delRptQry);
	return (k <=0)?-1:k;
}catch (Exception e)
 {
	LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
	e.printStackTrace();
	return -1;
 }
}
/* ************************************************************************\
* This method is used to delete Reported Abuses.
* @delRptQry : carries the query to delete Reported Abuses from report_abuse_detl using report_id.
\* ***********************************************************************/
public int rechargeforreedempoints (HttpServletRequest req , HttpServletResponse res,HttpSession session)
{
	String pagenumber = (req.getParameter("pagenumber")!=null)?(req.getParameter("pagenumber")):"0";
	LogLevel.DEBUG(5,new Throwable(),"pagenumber :"+pagenumber );

	String rechargeconfirmcode = (req.getParameter("rechargeconfirmcode")!=null)?(req.getParameter("rechargeconfirmcode")):"0";
	LogLevel.DEBUG(5,new Throwable(),"rechargeconfirmcode :"+rechargeconfirmcode );

	String[] strcheck=(req.getParameterValues("check")!=null)?(req.getParameterValues("check")):null;
	LogLevel.DEBUG(5,new Throwable(),"strcheck:"+strcheck );

	String retid="";
	String reedemids="";
	String delRptQry ="";
	ArrayList al = new ArrayList(); 
	try
	{		
	if(strcheck!=null)
	{
		for (int i=0;i<strcheck.length ;i++ )
		{
			if(reedemids.equals(""))
				reedemids=strcheck[i];
			else
				reedemids=reedemids+","+strcheck[i];
		}
	}
	LogLevel.DEBUG(5,new Throwable(),"reedemids :"+reedemids );
	// This Query is to delete Reports from report_abuse_detl using report_id.
	delRptQry = "update consumer_reedem_detl set recharge_status='yes',rechargeconfirm_code='"+rechargeconfirmcode+"',recharged_date=now() where reedem_id in ("+reedemids+")";
	LogLevel.DEBUG(5,new Throwable(),"delRptQry :"+delRptQry );
	int k=qm.executeUpdate(delRptQry);

	String strSqlQry3 = "select loyalty_ids from consumer_reedem_detl where reedem_id in ("+reedemids+")";
	LogLevel.DEBUG(5,new Throwable(),"strSqlQry3 :"+strSqlQry3 );
	Vector getloyaltyids= qm.executeQuery(strSqlQry3);
	LogLevel.DEBUG(5, new Throwable(), "getloyaltyids:" + getloyaltyids);

	for ( int j=0; j<getloyaltyids.size() ; j++)
	{
		Hashtable ht=(Hashtable)getloyaltyids.get(j);
		String strloyaltyids=String.valueOf(ht.get("loyalty_ids"));

		String[] tempArr=strloyaltyids.split(",");
		LogLevel.DEBUG(5,new Throwable(),"tempArr.length :"+tempArr.length );
		
		if(tempArr.length>0)
		{
			String strTempValue="";
			for (int l=0;l<tempArr.length ;l++ )
			{	
				strTempValue=tempArr[l];
				String updatereedemflag = "update  user_loyalty_program set reedem_flag='active' where loyaltyprogramm_id='"+strTempValue+"'";
				LogLevel.DEBUG(5,new Throwable(),"updatereedemflag :"+updatereedemflag );
				int m=qm.executeUpdate(updatereedemflag);
			}
		}
	}
	return (k <=0)?-1:k;
}catch (Exception e)
 {
	LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
	e.printStackTrace();
	return -1;
 }
}
/* ******************************************************************\
* This action is to send friend request through email.
* @strSqlQry : carries the query to fetch userid,emailid from user_profile table using loginemailid.
* @strSqlQry1 : carries the query to fetch userid,emailid from user_profile table  using friends emailid getting form jsp.
* @strSqlQry2 : carries the query to fetch friendid from friends table using  the userid fetched from strSqlQry1 
\* ********************************************************************/
public String checkrechargedetails (HttpServletRequest req , HttpServletResponse res,HttpSession session)
{
	String strRes="";
	String promotionalcode = (req.getParameter("promotionalcode")!=null)?(req.getParameter("promotionalcode")):"0";
	try
	{  	
		String getrechargedetails = "select email_id,points_reedem,recharge_amount,mobile_serviceprovider,location,recharge_status,mobile_number,creation_date,recharged_date from consumer_reedem_detl where promotional_code='"+promotionalcode+"' "; 
	
		Hashtable rechargedetails= qm.getRow(getrechargedetails);
		if(rechargedetails==null || rechargedetails.size() == 0)
		{
			strRes=strRes+"<table width ='200' cellspacing = '0' cellpadding = '1'  bgcolor='#BEADCB' align='center'>";	
			strRes=strRes+"<tr><td>No loyalty points </td></tr></table>";
			return strRes;
		}
		else
		  {
			String stremailid=String.valueOf(rechargedetails.get("email_id"));
			String strpoints_reedem=String.valueOf(rechargedetails.get("points_reedem"));
			String strrecharge_amount=String.valueOf(rechargedetails.get("recharge_amount"));
			String strmobile_serviceprovider=String.valueOf(rechargedetails.get("mobile_serviceprovider"));
			String strlocation=String.valueOf(rechargedetails.get("location"));
			String strrecharge_status=String.valueOf(rechargedetails.get("recharge_status"));
			String strrecharged_date=String.valueOf(rechargedetails.get("recharged_date"));
			String strmobile_number=String.valueOf(rechargedetails.get("mobile_number"));
			String strreedem_date=String.valueOf(rechargedetails.get("creation_date"));
					if(strrecharge_status.equals("no"))
			  {
						strrecharged_date="Not yet recharged";
			  }
			strRes=strRes+"<table width='600'  border='0' align='center' valign='top' border='0'>";
			strRes=strRes+"<tr><td height='1' bgcolor='#BEADCB'></td></tr>";
			strRes=strRes+"<tr><td height='1' bgcolor='#FFFFFF'></td></tr>";
			strRes=strRes+"<tr><td><table align='center' width='360' border='0' cellspacing='0' cellpadding='0'>";
			strRes=strRes+"<tr><td class='content' align='left'><b>Recharge Details</b></td></tr></table></td></tr> ";
			strRes=strRes+"<tr><td><table align='center' width='360' border='0' cellspacing='0' cellpadding='0'>";
			strRes=strRes+"<tr>";
			strRes=strRes+"<td  class='content' align='left'><b>Email ID :</b> "+stremailid+" </td>";
			strRes=strRes+"</tr></table></td></tr> ";
			strRes=strRes+"<tr><td height='1' bgcolor='#FFFFFF'></td></tr>";
			strRes=strRes+"<tr><td><table align='center' width='360' border='0' cellspacing='0' cellpadding='0'>";
			strRes=strRes+"<tr>";
			strRes=strRes+"<td  class='content' align='left'><b> Redeem points :</b> "+strpoints_reedem+"</td>";
			strRes=strRes+"</tr></table></td></tr> ";
			strRes=strRes+"<tr><td height='1' bgcolor='#FFFFFF'></td></tr>";
			strRes=strRes+"<tr><td><table align='center' width='360' border='0' cellspacing='0' cellpadding='0'>";
			strRes=strRes+"<tr>";
			strRes=strRes+"<td  class='content' align='left'> <b>Recharge Amount :</b> "+strrecharge_amount+"</td>";
			strRes=strRes+"</tr></table></td></tr> ";
			strRes=strRes+"<tr><td height='1' bgcolor='#FFFFFF'></td></tr>";
			strRes=strRes+"<tr><td><table align='center' width='360' border='0' cellspacing='0' cellpadding='0'>";
			strRes=strRes+"<tr>";
			strRes=strRes+"<td  class='content' align='left'> <b>Service Provider :</b> "+strmobile_serviceprovider+"</td>";
			strRes=strRes+"</tr></table></td></tr> ";
			strRes=strRes+"<tr><td height='1' bgcolor='#FFFFFF'></td></tr>";
			strRes=strRes+"<tr><td><table align='center' width='360' border='0' cellspacing='0' cellpadding='0'>";
			strRes=strRes+"<tr>";
			strRes=strRes+"<td  class='content' align='left'> <b>Location :</b> "+strlocation+"</td>";
			strRes=strRes+"</tr></table></td></tr> ";
			strRes=strRes+"<tr><td height='1' bgcolor='#FFFFFF'></td></tr>";
			strRes=strRes+"<tr><td><table align='center' width='360' border='0' cellspacing='0' cellpadding='0'>";
			strRes=strRes+"<tr>";
			strRes=strRes+"<td  class='content' align='left'> <b>Recharge Status :</b> "+strrecharge_status+"</td>";
			strRes=strRes+"</tr></table></td></tr> ";
			strRes=strRes+"<tr><td height='1' bgcolor='#FFFFFF'></td></tr>";
			strRes=strRes+"<tr><td><table align='center' width='360' border='0' cellspacing='0' cellpadding='0'>";
			strRes=strRes+"<tr>";
			strRes=strRes+"<td  class='content' align='left'> <b>Recharged Date :</b> "+strrecharged_date+"</td>";
			strRes=strRes+"</tr></table></td></tr> ";
			strRes=strRes+"<tr><td height='1' bgcolor='#FFFFFF'></td></tr>";
			strRes=strRes+"<tr><td><table align='center' width='360' border='0' cellspacing='0' cellpadding='0'>";
			strRes=strRes+"<tr>";
			strRes=strRes+"<td  class='content' align='left'> <b>Redeem Date :</b> "+strreedem_date+"</td>";
			strRes=strRes+"</tr></table></td></tr> ";
			strRes=strRes+"<tr><td height='1' bgcolor='#FFFFFF'></td></tr>";
			strRes=strRes+"<tr><td><table align='center' width='360' border='0' cellspacing='0' cellpadding='0'>";
			strRes=strRes+"<tr>";
			strRes=strRes+"<td  class='content' align='left'> <b>Mobile Number : </b> "+strmobile_number+"</td>";
			//strRes=strRes+"<td class='content'><a class='click'  href=\"javascript:hiderechargedetails();\" >Hide</a></td>"; 
			
			strRes=strRes+"</tr></table></td></tr>";
			strRes=strRes+"</table>";
			return strRes;
	      }
	}
    catch(Exception e)
	{
		LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
		e.printStackTrace();
		session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
		strRes=strRes+"Problem Caught Exception";
		return strRes;
	}
				
}
	/* **************************************************************************************************************************************\
* This method is used to fetch emailid,loyalty_name and partners_list from user_loyalty_program table..
* @getEmailQry : carries the query to fetch loyalty_name, email_id from user_loyalty_program table.
* @getPartnrQry : carries the query to fetch partners_list from user_loyalty_program table.
\* **************************************************************************************************************************************/

public String loyaltyManagement2 (HttpServletRequest req , HttpServletResponse res,HttpSession session, String strvendorName)
{
	
		try
		{
			//query to fetch vendor_id, vendor_name from vendor_master  based on vendor_name..
			String getVendidQry="select vendor_id, vendor_name from vendor_master where vendor_name='"+strvendorName+"'";
			Vector result=qm.executeQuery(getVendidQry);

			if(result == null || result.size()<=0)
			{
				//if yes then query is not execute properly.
				LogLevel.DEBUG(1,new Throwable(),"Failed to fetch account details ");
				session.setAttribute("priority","1");
				session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'>Failed to fetch account details!</font> ");
				res.sendRedirect("../jsp/admin/batterystore/brandloyaltyprogram/brandredeemloyalty.jsp");
			

			}
			else
			{	//else query is executed sucessfully
				LogLevel.DEBUG(1,new Throwable(),"Successfully fetched account details ");
				session.setAttribute("sesErrorMsg", "");
				res.sendRedirect("../jsp/admin/batterystore/brandloyaltyprogram/brandredeemloyalty.jsp");
			}
				
			
		}catch(IOException ioe)
		{
			//If any exceptionsa re found in try block, this block will catch those exception
			LogLevel.ERROR(0,ioe,"problem Caught IOException if(redeemaccount) !! "+ioe);
			ioe.printStackTrace();
			//printstacktrace will print the line where exception is being arised
			session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
		}catch(Exception e)
		{
			//If any exceptionsa re found in try block, this block will catch those exception
			LogLevel.ERROR(0,e,"Problem Caught Exception if(redeemaccount)!! "+e);
			e.printStackTrace();
			//printstacktrace will print the line where exception is being arised
			session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
		}
		//returning the result to the place where this method is called.
		return null;
	}
private static boolean deleteDir(File paramDir)
{
if (paramDir.isDirectory())
{
String[] strChildren = paramDir.list();
for (int i=0; i<strChildren.length; i++)
{
boolean success = deleteDir(new File(paramDir, strChildren[i]));
if (!success)
{
return false;
}
}
}
return paramDir.delete();
}



}//end of class

