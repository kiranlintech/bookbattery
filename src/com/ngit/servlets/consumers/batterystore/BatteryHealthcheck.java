/***********************************************************************		
	NGIT Confidential. 
	@File Name   : BatteryDetails.java 
	@Description : This Servlet is used to select the Battery details.
	@Author	     : Sai Krishna Daddala.
	@Date        : 1st September 2013
******************************************************************/ 
 
package com.ngit.servlets.consumers.batterystore;  
 
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.mail.*;
import com.ngit.javabean.sendsms.SendSMS;
import com.ngit.javabean.consumers.products.CompareTrans;
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

  
public class BatteryHealthcheck extends HttpServlet 
{  
	private ServletContext context; 
	QueryManager qm;
	 private static final Random RANDOM = new SecureRandom();
		 /** Length of password. @see #generateRandomPassword() */
		 public static final int PASSWORD_LENGTH = 8;

 
	public void init(ServletConfig config)throws ServletException
	{ 
		super.init(config); 
		context = getServletContext(); 
		
		try
		{
			String strMOPConfig = getInitParameter("paramMOPConfig"); 
  
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
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
		doGet(req,res);
	}
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
        res.setContentType("text/html;charset=UTF-8");
        HttpSession session=req.getSession(true);
 		session=req.getSession(true);
		String strUserName=(String)session.getAttribute("sesAdminName"); 

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

	/* ****************************************************************************************\
	* This action is used to get send mobile verification code details.
	\* *****************************************************************************************/		
	if(strWhatToDo.equalsIgnoreCase("sentverificationcode"))
	{ 
		try
		{
			String strRes=sentverificationcode(req,res,session,domainname,strIpAddress,strPort,SMSFromAddress,FromEmailId);
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
	* This action is used to get send mobile verification code details.
	\* *****************************************************************************************/		
	if(strWhatToDo.equalsIgnoreCase("sentverificationcode1"))
		{ 
			try
			{
				String strRes=sentverificationcode1(req,res,session,domainname,strIpAddress,strPort,SMSFromAddress,FromEmailId);
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
	* This action is used to  check retailer details
	\* *****************************************************************************************/		
	if(strWhatToDo.equalsIgnoreCase("checkretailerdetails"))
		{ 
			try
			{
				String strRes=checkretailerdetails(req,res,session);
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
	* This action is used to  insert consumer details.
	\* *****************************************************************************************/		
	if(strWhatToDo.equalsIgnoreCase("insertconsumerdetails"))
		{ 
			try
			{
				String strRes=insertconsumerdetails(req,res,session,domainname,strIpAddress,strPort,SMSFromAddress,FromEmailId,strsuppemaild,baseUrl,strsuppmobnumber);
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
	* This action is used to  insert consumer details.
	\* *****************************************************************************************/		
	if(strWhatToDo.equalsIgnoreCase("insertconsumerdetails1"))
		{ 
			try
			{
				String strRes=insertconsumerdetails1(req,res,session,domainname,strIpAddress,strPort,SMSFromAddress,FromEmailId,baseUrl,strsuppmobnumber);
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
	* This action is used to  insert consumer details.
	\* *****************************************************************************************/		
	if(strWhatToDo.equalsIgnoreCase("insertvisitors"))
		{ 
			try
			{
				String strRes=insertvisitors(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);
				out.close();
			
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}

	}
/* *************************************************************************************************************\
* This method is used to send  verification to user email id and mobile number when user enters mobile number in order now battery 
\* *************************************************************************************************************/
public String sentverificationcode(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort,String SMSFromAddress, String FromEmailId) 
{ 	
	String inmonth = (req.getParameter("inmonth")!=null)?(req.getParameter("inmonth")):"";
	String inyear = (req.getParameter("inyear")!=null)?(req.getParameter("inyear")):"";
	String strUsermobileno = (req.getParameter("strUsermobileno")!=null)?(req.getParameter("strUsermobileno")):"";
	String batterytype = (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
	String vehiclename = (req.getParameter("vehiclename")!=null)?(req.getParameter("vehiclename")):"";
	String vehmodel = (req.getParameter("vehmodel")!=null)?(req.getParameter("vehmodel")):"";
	String batterybrand = (req.getParameter("batterybrand")!=null)?(req.getParameter("batterybrand")):"";
	String batterycapcity = (req.getParameter("batterycapcity")!=null)?(req.getParameter("batterycapcity")):"";
	String state = (req.getParameter("state")!=null)?(req.getParameter("state")):"";
	String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
	String area = (req.getParameter("area")!=null)?(req.getParameter("area")):"";
	String pincode = (req.getParameter("pincode")!=null)?(req.getParameter("pincode")):"";
	String keyword = (req.getParameter("keyword")!=null)?(req.getParameter("keyword")):"";
	String mobileversion = (req.getParameter("mobileversion")!=null)?(req.getParameter("mobileversion")):"";


	String strRes="";
	String visitorcity="";
	String strstate="";
	try
	{
			if(batterycapcity.equals("0") || batterycapcity == "0"){ batterycapcity = ""; } else  { batterycapcity=batterycapcity; }
			if(city == null || city.equals("0") || city.equals("") || city == "") 
			{
				String StrSqlQrystate1 = "select state,city from battery_pincode where pincode='"+pincode+"'";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQrystate1 :" + StrSqlQrystate1);

				Hashtable htgetstate1 = qm.getRow(StrSqlQrystate1); 
				String strstate1=(String)htgetstate1.get("state");	
				String strcity=(String)htgetstate1.get("city");
				visitorcity=strcity;
				strstate=strstate1;
			} 
			else 
			{ 
				visitorcity=city;	
				strstate=state;
			}

			String strSqlQryvisit = "insert into visitors_orders(vis_ord_id,bat_type,veh_make,veh_model,bat_brand,bat_capacity,state,city,area,pincode,mobile_number,option_type,keynumber,visitors_comments,creation_date)values(NULL,'"+batterytype+"','"+vehiclename+"','"+vehmodel+"','"+batterybrand+"','"+batterycapcity+"','"+strstate+"','"+visitorcity+"','"+area+"','"+pincode+"','"+strUsermobileno+"','"+keyword+"','','',now())";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQryvisit: "+strSqlQryvisit);
			int reslt = qm.executeUpdate(strSqlQryvisit);

			/*following code is for generating the random number */
			Random generator = new Random();   
			generator.setSeed(System.currentTimeMillis());   
			int num = generator.nextInt(99999) + 99999;   
			if (num < 100000 || num > 999999)
			{   
				num = generator.nextInt(99999) + 99999;   
				if (num < 100000 || num > 999999)
				{   
				}   
			}   
			String verificationcode = "";
			verificationcode = Integer.toString(num);
			/*code to generate the random number ends here */
			CompareTrans ct = new CompareTrans(qm);
			SendSMS sendsms = new SendSMS(qm);
			String ThankUMsg="Your Verification Code is "+verificationcode+"";
			String strSMSResponse=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUMsg, strUsermobileno);
			if(mobileversion.equals("mobile"))
			{
				//strRes=strRes+"<table width='100%' bgcolor='#FFFFFF' valign='top'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobdiv(greyout(false));\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b></td></tr><tr><td><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF'><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font size='2' color='#FF8C00'>Please Enter Verification code received on <br> SMS</font></td></tr></table></td><tr height='10'><td colspan='3' align='center' class='subheading' id='codeerrormsg'></td></tr><tr><td height='10'></td></tr></tr><tr><td width='100%' align='center'><input type='hidden' name='passcode' value='"+verificationcode+"'><input class='insidecontent' type='tel' autocomplete='off' name='verifycode' id='verifycode' placeholder='123456' style='width:195px;height:30px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='6' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:checkverification('"+inmonth+"','"+inyear+"','"+strUsermobileno+"','"+verificationcode+"','"+batterytype+"','"+vehiclename+"','"+vehmodel+"','"+batterybrand+"','"+batterycapcity+"','"+state+"','"+city+"','"+area+"','"+pincode+"');return false;} else return true;\"/></td></tr><tr><td align='center' class='popupmobilehints'><font color='#FF3333'>*</font>If you did not get verification code <br> please call <font color='#FF8C00'>+91 9603467559.</font></td></tr><tr><td height='0'></td></tr></tr><tr><td  width='50%' align='center' ><input type='button' class='batterywalesubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:checkverification('"+inmonth+"','"+inyear+"','"+strUsermobileno+"','"+verificationcode+"','"+batterytype+"','"+vehiclename+"','"+vehmodel+"','"+batterybrand+"','"+batterycapcity+"','"+state+"','"+city+"','"+area+"','"+pincode+"');\");\"></td></tr><tr><td height='15'></td></tr></table></table>";
				
				strRes=strRes+"<table width='100%'  valign='top'><tr height='2'></tr><tr><table width='100%'  valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: center;'> BookBattery</td><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: left;' onclick=\"javascript:closemobilediv(greyout(false));\"> X</td></tr></table><hr style='background: white; width: 95%;margin-top: 1px;margin-bottom: 1px;'></tr><table width='100%'  cellspacing='0' cellpadding='0' ><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontentmob' align='center'><font size='4' color='#FFFFFF' style='padding: 2px;'>Please Enter Verification code received on SMS</font></td></tr></table></td><tr height='10'><td colspan='3' align='center' class='subheading' id='codeerrormsg'></td></tr><tr><td height='10'></td></tr></tr><tr><td width='100%' align='center'><input type='hidden' name='passcode' value='"+verificationcode+"'><input class='insidecontent' type='tel' autocomplete='off' name='verifycode' id='verifycode' placeholder='123456' style='width:250px;height:40px;border: 2px solid #CCC;font-size: 13px;' maxlength='6' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:checkverification('"+inmonth+"','"+inyear+"','"+strUsermobileno+"','"+verificationcode+"','"+batterytype+"','"+vehiclename+"','"+vehmodel+"','"+batterybrand+"','"+batterycapcity+"','"+state+"','"+city+"','"+area+"','"+pincode+"');return false;} else return true;\"/></td></tr><tr><td align='center' class='popupmobilehints'><font color='#FF3333'>*</font>If you did not get verification code <br> please call <font color='#FF8C00'>+91 9603467559.</font></td></tr><tr><td height='0'></td></tr><tr><td  width='50%' align='center' ><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn' class='batterywalesubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:checkverification('"+inmonth+"','"+inyear+"','"+strUsermobileno+"','"+verificationcode+"','"+batterytype+"','"+vehiclename+"','"+vehmodel+"','"+batterybrand+"','"+batterycapcity+"','"+state+"','"+city+"','"+area+"','"+pincode+"');\");\"><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  value='Close' align='left' onclick=\"javascript:closemobilediv(greyout(false));\"> </td></tr><tr><td height='15'></td></tr></table></table>";
				
			}
			else
			{
				//strRes=strRes+"<table cellspacing='10' cellpadding='0' bgcolor='#FFFFFF'><table width='350' bgcolor='#FFFFFF' valign='top'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobdiv(greyout(false));\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b></td></tr></table><tr><td><table width='350'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF'><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font size='2' color='#FF8C00'>Please Enter Verification code received on <br> SMS</font></td></tr></table></td><tr><td height='10'></td></tr></tr><tr><td width='100%' align='center'><input type='hidden' name='passcode' value='"+verificationcode+"'><input class='insidecontent' type='tel' autocomplete='off' name='verifycode' id='verifycode' placeholder='123456' style='width:195px;height:30px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='6' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:checkverification('"+inmonth+"','"+inyear+"','"+strUsermobileno+"','"+verificationcode+"','"+batterytype+"','"+vehiclename+"','"+vehmodel+"','"+batterybrand+"','"+batterycapcity+"','"+state+"','"+city+"','"+area+"','"+pincode+"');return false;} else return true;\"/></td></tr><tr><td align='center' style='font-family:Verdana;font-size:10px;color:#000000;font-weight:bold;text-decoration:none;padding:1px 1px;'><font color='#FF3333'>*</font>If you did not get verification code <br> please call <font color='#FF8C00'>+91 9603467559.</font></td></tr><tr><td height='0'></td></tr></tr><tr><td  width='50%' align='center' ><input type='button' class='batterywalesubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:checkverification('"+inmonth+"','"+inyear+"','"+strUsermobileno+"','"+verificationcode+"','"+batterytype+"','"+vehiclename+"','"+vehmodel+"','"+batterybrand+"','"+batterycapcity+"','"+state+"','"+city+"','"+area+"','"+pincode+"');\");\"></td></tr><tr height='26'><td colspan='3' align='center' class='subheading' id='codeerrormsg'></td></tr><tr><td height='15'></td></tr></table>";
				
				strRes=strRes+"<div class='col-md-4 col-md-offset-4'> <table cellspacing='10' cellpadding='0' ><tr height='10'><table width='100%'  valign='top'></table><table  width='100%' bgcolor='#FFFFFF' valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 25px;font-weight: 600;text-align: center;'> BookBattery</td> <td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: left;'  href=\"javascript:closemobilediv(greyout(false));\" > <a style='color: #F96F2B;' href=\"javascript:closemobilediv(greyout(false));\"> X </a> </td></tr></table><hr style='background: white; width: 90%;margin-top: 1px;margin-bottom: 1px;'></tr><tr><td><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font style='font-size: 15px;color: #ffffff;'>Please Enter Verification code received on SMS</font></td></tr></table></td></tr><tr><td height='10'></td></tr> <table cellspacing='10' cellpadding='0'  width='100%' height='10'> <tr><td width='100%' align='center'><input type='hidden' name='passcode' value='"+verificationcode+"'><input class='insidecontent' type='tel' autocomplete='off' name='verifycode' id='verifycode' placeholder='123456' style='width:70%;height:38px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 0px 0px 0px 0px;' maxlength='6' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:checkverification('"+inmonth+"','"+inyear+"','"+strUsermobileno+"','"+verificationcode+"','"+batterytype+"','"+vehiclename+"','"+vehmodel+"','"+batterybrand+"','"+batterycapcity+"','"+state+"','"+city+"','"+area+"','"+pincode+"');return false;} else return true;\"/></td></tr> </table> <table cellspacing='10' cellpadding='0'  width='100%' height='10' > <tr><td align='center' style='font-family:Verdana;font-size:12px;color:#FFFFFF; font-weight:bold;text-decoration:none;padding:1px 1px;'> <font color='#FF3333'>*</font>If you did not get verification code <br> please call <font color='#FF8C00'>+91 9603467559.</font></td></tr> </table> <tr><td height='10'></td></tr>  <table cellspacing='10' cellpadding='0'  width='100%' height='10' >  <tr><td  width='50%' align='right' ><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:checkverification('"+inmonth+"','"+inyear+"','"+strUsermobileno+"','"+verificationcode+"','"+batterytype+"','"+vehiclename+"','"+vehmodel+"','"+batterybrand+"','"+batterycapcity+"','"+state+"','"+city+"','"+area+"','"+pincode+"');\");\"> </td> <td><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  value='Close' align='left' onclick=\"javascript:closemobilediv(greyout(false));\" ></td></tr> </table> <table cellspacing='10' cellpadding='0'  width='100%' height='10' >  <tr height='26'><td colspan='3' align='center' class='subheading' id='codeerrormsg'></td></tr> </table ><tr><td height='15'></td></tr></table></div>"; 
				
				
			}
	}
	catch (Exception e)
	{
		LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
		strRes=strRes;
	}
	return strRes;
}
/* *************************************************************************************************************\
* This method is used to send  verification to user email id and mobile number when user enters mobile number in order now battery 
\* *************************************************************************************************************/
public String sentverificationcode1(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort,String SMSFromAddress, String FromEmailId) 
{ 	
	String inmonth = (req.getParameter("inmonth")!=null)?(req.getParameter("inmonth")):"";
	String inyear = (req.getParameter("inyear")!=null)?(req.getParameter("inyear")):"";
	String batmodelnumber = (req.getParameter("batmodelnumber")!=null)?(req.getParameter("batmodelnumber")):"";
	String strUsermobileno = (req.getParameter("strUsermobileno")!=null)?(req.getParameter("strUsermobileno")):"";
	String batterytype = (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
	String vehiclename = (req.getParameter("vehiclename")!=null)?(req.getParameter("vehiclename")):"";
	String vehmodel = (req.getParameter("vehmodel")!=null)?(req.getParameter("vehmodel")):"";
	String batterybrand = (req.getParameter("batterybrand")!=null)?(req.getParameter("batterybrand")):"";
	String batterycapcity = (req.getParameter("batterycapcity")!=null)?(req.getParameter("batterycapcity")):"";
	String state = (req.getParameter("state")!=null)?(req.getParameter("state")):"";
	String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
	String area = (req.getParameter("area")!=null)?(req.getParameter("area")):"";
	String pincode = (req.getParameter("pincode")!=null)?(req.getParameter("pincode")):"";
	String keyword = (req.getParameter("keyword")!=null)?(req.getParameter("keyword")):"";
	String mobileversion = (req.getParameter("mobileversion")!=null)?(req.getParameter("mobileversion")):"";

	String visitorcity="";
	String strRes="";
	String strstate="";
	try
	{
			if(batterycapcity.equals("0") || batterycapcity == "0"){ batterycapcity = ""; } else  { batterycapcity=batterycapcity; }
			if(city == null || city.equals("0") || city.equals("") || city == "") 
			{
				String StrSqlQrystate1 = "select state,city from battery_pincode where pincode='"+pincode+"'";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQrystate1 :" + StrSqlQrystate1);

				Hashtable htgetstate1 = qm.getRow(StrSqlQrystate1); 
				String strstate1=(String)htgetstate1.get("state");	
				String strcity=(String)htgetstate1.get("city");
				visitorcity=strcity;
				strstate=strstate1;
			} 
			else
			{
				visitorcity=city;	
				strstate=state;
			}

			String strSqlQryvisit = "insert into visitors_orders(vis_ord_id,bat_type,veh_make,veh_model,bat_brand,bat_capacity,state,city,area,pincode,mobile_number,option_type,keynumber,visitors_comments,creation_date)values(NULL,'"+batterytype+"','"+vehiclename+"','"+vehmodel+"','"+batterybrand+"','"+batterycapcity+"','"+strstate+"','"+visitorcity+"','"+area+"','"+pincode+"','"+strUsermobileno+"','"+keyword+"','','',now())";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQryvisit: "+strSqlQryvisit);
			int reslt = qm.executeUpdate(strSqlQryvisit);

			/*following code is for generating the random number */
			Random generator = new Random();   
			generator.setSeed(System.currentTimeMillis());   
			int num = generator.nextInt(99999) + 99999;   
			if (num < 100000 || num > 999999)
			{   
			num = generator.nextInt(99999) + 99999;   
			if (num < 100000 || num > 999999)
			{   
			}   
			}   
			String verificationcode = "";
			verificationcode = Integer.toString(num);
			/*code to generate the random number ends here */
			CompareTrans ct = new CompareTrans(qm);
			SendSMS sendsms = new SendSMS(qm);
			String ThankUMsg="Your Verification Code is "+verificationcode+"";
			String strSMSResponse=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUMsg, strUsermobileno);

			
			
			strRes="javascript:checkverification('"+inmonth+"','"+inyear+"','"+batmodelnumber+"','"+strUsermobileno+"','"+verificationcode+"','"+batterytype+"','"+vehiclename+"','"+vehmodel+"','"+batterybrand+"','"+batterycapcity+"','"+state+"','"+city+"','"+area+"','"+pincode+"')";

	}
	catch (Exception e)
	{
	LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
	strRes=strRes;
	}
	return strRes;
}
/* *************************************************************************************************************\
* This method is used to send  verification to user email id and mobile number when user enters mobile number in order now battery 
\* *************************************************************************************************************/
public String insertvisitors(HttpServletRequest req , HttpServletResponse res,HttpSession session) 
{ 	
	String batterytype = (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
	String vehiclename = (req.getParameter("vehiclename")!=null)?(req.getParameter("vehiclename")):"";
	String vehmodel = (req.getParameter("vehmodel")!=null)?(req.getParameter("vehmodel")):"";
	String batterybrand = (req.getParameter("batterybrand")!=null)?(req.getParameter("batterybrand")):"";
	String batterycapcity = (req.getParameter("batterycapcity")!=null)?(req.getParameter("batterycapcity")):"";
	String state = (req.getParameter("state")!=null)?(req.getParameter("state")):"";
	String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
	String area = (req.getParameter("area")!=null)?(req.getParameter("area")):"";
	String pincode = (req.getParameter("pincode")!=null)?(req.getParameter("pincode")):"";
	String keyword = (req.getParameter("keyword")!=null)?(req.getParameter("keyword")):"";
	String strRes="";
	try
	{
		String strstate1="";
		if(batterycapcity.equals("0") || batterycapcity == "0")
		{
			batterycapcity = ""; 
		} 
		else  
		{ 
			batterycapcity=batterycapcity;
		}
		if(city == null || city.equals("0") || city.equals("") || city == "") 
		{
			String StrSqlQrystate1 = "select state,city from battery_pincode where pincode='"+pincode+"'";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQrystate1 :" + StrSqlQrystate1);

			Hashtable htgetstate1 = qm.getRow(StrSqlQrystate1); 
			strstate1=(String)htgetstate1.get("state");	
			String strcity=(String)htgetstate1.get("city");
			city=strcity;
		} 
		else
		{ 
			city=city;
		}

		String strSqlQryvisit = "insert into visitors_orders(vis_ord_id,bat_type,veh_make,veh_model,bat_brand,bat_capacity,state,city,area,pincode,mobile_number,option_type,keynumber,visitors_comments,creation_date)values(NULL,'"+batterytype+"','"+vehiclename+"','"+vehmodel+"','"+batterybrand+"','"+batterycapcity+"','"+state+"','"+city+"','"+area+"','"+pincode+"','','"+keyword+"','','',now())";
		LogLevel.DEBUG(5,new Throwable(),"strSqlQryvisit: "+strSqlQryvisit);
		int reslt = qm.executeUpdate(strSqlQryvisit);
		if(reslt >0)
		{
		strRes=strRes+"<p align='center' valign='top' class='insidecontent'><table valign='top' width='500' cellspacing='0' border='0' align='center' bgcolor='#FFFFFF' cellpadding='0'><tr><td><table valign='top' width='500' bgcolor='#FFFFFF' border='0'><tr><td class='insidecontent' align='center'>Successfully inserted visitors details!</td></tr></table></td></tr></table><a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>";
		}
		else
		{
		strRes=strRes+"<p align='center' valign='top' class='insidecontent'><table valign='top' width='500' cellspacing='0' border='0' align='center' bgcolor='#FFFFFF' cellpadding='0'><tr><td><table valign='top' width='500' bgcolor='#FFFFFF' border='0'><tr><td class='insidecontent' align='center'>Failed to insert visitors details!</td></tr></table></td></tr></table><a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>";				
		}

	}
	catch (Exception e)
	{
	LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
	strRes=strRes;
	}
	return strRes;
}
/* *************************************************************************************************************\
* This method is used to insert consumer details
\* *************************************************************************************************************/
public String checkretailerdetails(HttpServletRequest req , HttpServletResponse res,HttpSession session) 
{ 	

	String state = (req.getParameter("state")!=null)?(req.getParameter("state")):"";
	String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
	String area = (req.getParameter("area")!=null)?(req.getParameter("area")):"";
	String pincode = (req.getParameter("pincode")!=null)?(req.getParameter("pincode")):"";
	String mobileversion = (req.getParameter("mobileversion")!=null)?(req.getParameter("mobileversion")):"";

	//password = password.replace("\\","\\\\");  

	String strRes="";
	String strstate="";
	String strstate1="";
	String StrSqlQry ="";
	String Strretid="";
	String Strlocorpin="";
	try
	{
			if(city.equals("0") || city.equals("") || city == "")
			{
				StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+pincode+"' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);

				Hashtable htretailerid = qm.getRow(StrSqlQry); 
				Strretid=String.valueOf(htretailerid.get("retailer_id"));
			
				String StrSqlQrystate1 = "select state,city from battery_pincode where pincode='"+pincode+"'";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQrystate1 :" + StrSqlQrystate1);

				Hashtable htgetstate1 = qm.getRow(StrSqlQrystate1); 
				strstate1=(String)htgetstate1.get("state");	
				String strcity=(String)htgetstate1.get("city");
				if(strcity == null)
				{
					city = "";
				}
				else
				{
					city=strcity;
					strstate=strstate1.trim().replaceAll(" ", "_"); 
					LogLevel.DEBUG(5, new Throwable(), "strstate :" + strstate);
					Strlocorpin = pincode;
				}
			}
			else
			{	
				strstate1 = state;
				strstate=strstate1.trim().replaceAll(" ", "_"); 
				LogLevel.DEBUG(5, new Throwable(), "strstate :" + strstate);
				
				StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+city+"' and area='"+area+"' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);

				Hashtable htretailerid = qm.getRow(StrSqlQry); 
				Strretid=String.valueOf(htretailerid.get("retailer_id"));
				LogLevel.DEBUG(5, new Throwable(), "Strretid :" + Strretid);
				Strlocorpin = city;
			}
			if(Strretid.equals(null) || Strretid.equals("null") || Strretid == null || Strretid == "null" || Strretid =="")
			{
				if(mobileversion.equals("mobile"))
				{
					strRes="<table border='0' width='100%' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td align='left'>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closemobdiv(greyout(false));'><img src=\"./images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='100%' height='2px' bgcolor='#FFFFFF' valign'top'><tr class='pages' bgcolor='#FFFFFF'><td align='center' bgcolor='#FFFFFF'>No service is provided to the areas under this pincode.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closemobdiv();' class='button4'><br></td></tr><tr height='15'></tr></table>";				
				}
				else
				{
					strRes="<table border='0' width='300px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closemobdiv(greyout(false));'><img src=\"./images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='300px' height='2px' bgcolor='#FFFFFF' valign'top'><tr class='pages' bgcolor='#FFFFFF'><td align='center' bgcolor='#FFFFFF'>No service is provided to the areas under this pincode.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closemobdiv();' class='button4'><br></td></tr><tr height='15'></tr></table>";			
				}
			}
			else
			{	
				strRes="Franchisee Details are found";
			}
	}
	catch (Exception e)
	{
		LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
		strRes=strRes;
	}
	return strRes;
}
/* *************************************************************************************************************\
* This method is used to insert consumer details
\* *************************************************************************************************************/
public String insertconsumerdetails(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort,String SMSFromAddress, String FromEmailId, String strsuppemaild, String baseUrl, String strsuppmobnumber) 
{ 	
	String strusername= (req.getParameter("username")!=null)?(req.getParameter("username")):"";
	String emailid= (req.getParameter("emailid")!=null)?(req.getParameter("emailid")):"";
	String addrs1= (req.getParameter("addrs1")!=null)?(req.getParameter("addrs1")):""; 
	String addrs2= (req.getParameter("addrs2")!=null)?(req.getParameter("addrs2")):"";
	//String userstate= (req.getParameter("userstate")!=null)?(req.getParameter("userstate")):"";
	String userarea= (req.getParameter("userarea")!=null)?(req.getParameter("userarea")):"";
	String usercity= (req.getParameter("usercity")!=null)?(req.getParameter("usercity")):"";
	String userzipcode= (req.getParameter("userzipcode")!=null)?(req.getParameter("userzipcode")):"";
	String inmonth = (req.getParameter("inmonth")!=null)?(req.getParameter("inmonth")):"";
	String inyear = (req.getParameter("inyear")!=null)?(req.getParameter("inyear")):"";
	String strUsermobileno = (req.getParameter("mobilenumber")!=null)?(req.getParameter("mobilenumber")):"";
	String verifycode = (req.getParameter("verifycode")!=null)?(req.getParameter("verifycode")):"";
	String batterytype = (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
	String vehiclename = (req.getParameter("vehiclename")!=null)?(req.getParameter("vehiclename")):"";
	String vehmodel = (req.getParameter("vehmodel")!=null)?(req.getParameter("vehmodel")):"";
	String batterybrand = (req.getParameter("batterybrand")!=null)?(req.getParameter("batterybrand")):"";
	String batterycapcity = (req.getParameter("batterycapcity")!=null)?(req.getParameter("batterycapcity")):"";
	String state = (req.getParameter("state")!=null)?(req.getParameter("state")):"";
	String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
	String area = (req.getParameter("area")!=null)?(req.getParameter("area")):"";
	String pincode = (req.getParameter("pincode")!=null)?(req.getParameter("pincode")):"";
	String mobileversion = (req.getParameter("mobileversion")!=null)?(req.getParameter("mobileversion")):"";

	//password = password.replace("\\","\\\\");  

	String strRes="";
	String strstate="";
	String strstate1="";
	String StrSqlQry ="";
	String Strretid="";
	String Strlocorpin="";
	try
	{
			if(city.equals("0") || city.equals("") || city == "")
			{
				StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+pincode+"' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);

				Hashtable htretailerid = qm.getRow(StrSqlQry); 
				Strretid=String.valueOf(htretailerid.get("retailer_id"));
				
				String StrSqlQrystate1 = "select state,city from battery_pincode where pincode='"+pincode+"'";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQrystate1 :" + StrSqlQrystate1);

				Hashtable htgetstate1 = qm.getRow(StrSqlQrystate1); 
				strstate1=(String)htgetstate1.get("state");	
				String strcity=(String)htgetstate1.get("city");
				if(strcity == null)
				{
					city = "";
				}
				else
				{
					city=strcity;
					strstate=strstate1.trim().replaceAll(" ", "_"); 
					LogLevel.DEBUG(5, new Throwable(), "strstate :" + strstate);
					Strlocorpin = pincode;
				}
			}
			else
			{
				//String StrSqlQrystate = "select state from search_whereever_keywords where city='"+city+"'";
				//LogLevel.DEBUG(5, new Throwable(), "StrSqlQrystate :" + StrSqlQrystate); 

				//Hashtable htgetstate = qm.getRow(StrSqlQrystate); 
				//strstate1=(String)htgetstate.get("state");
				strstate1 = state;
				strstate=strstate1.trim().replaceAll(" ", "_"); 
				LogLevel.DEBUG(5, new Throwable(), "strstate :" + strstate);
				
				StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+city+"' and area='"+area+"' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);

				Hashtable htretailerid = qm.getRow(StrSqlQry); 
				Strretid=String.valueOf(htretailerid.get("retailer_id"));
				LogLevel.DEBUG(5, new Throwable(), "Strretid :" + Strretid);
				Strlocorpin = city;
			}

			if(Strretid.equals(null) || Strretid.equals("null") || Strretid == null || Strretid == "null" || Strretid =="")
			{
				if(mobileversion.equals("mobile"))
				{
				strRes="<table border='0' width='100%' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td align='left'>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closemobdiv(greyout(false));'><img src=\"./images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='100%' height='2px' bgcolor='#FFFFFF' valign'top'><tr class='pages' bgcolor='#FFFFFF'><td align='center' bgcolor='#FFFFFF'>No Retailers Found on Selected City OR Pincode.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closemobdiv();' class='button4'><br></td></tr><tr height='15'></tr></table>";
				
				}
				else
				{
				strRes="<table border='0' width='300px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closemobdiv(greyout(false));'><img src=\"./images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='300px' height='2px' bgcolor='#FFFFFF' valign'top'><tr class='pages' bgcolor='#FFFFFF'><td align='center' bgcolor='#FFFFFF'>No Retailers Found on Selected City OR Pincode.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closemobdiv();' class='button4'><br></td></tr><tr height='15'></tr></table>";
			
				}
			}
			else
			{
			String StrSqlQrydet = "select mobile_number,mobile_numberother,retailer_name from "+strstate+"_retailers where retailer_id='"+Strretid+"'";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQrydet :" + StrSqlQrydet);

			Hashtable htretailerdetls = qm.getRow(StrSqlQrydet); 
			String strretmobile=String.valueOf(htretailerdetls.get("mobile_number"));
			LogLevel.DEBUG(5, new Throwable(), "strretmobile :" + strretmobile);
			String strtechmobnum=String.valueOf(htretailerdetls.get("mobile_numberother"));
			LogLevel.DEBUG(5, new Throwable(), "strtechmobnum :" + strtechmobnum);
			String strretname=String.valueOf(htretailerdetls.get("retailer_name"));

			if(strtechmobnum.equals("null") || strtechmobnum.equals(null) || strtechmobnum==null || strtechmobnum.equals(""))
			{
				strtechmobnum=strretmobile;
			}
			LogLevel.DEBUG(5, new Throwable(), "strtechmobnum :" + strtechmobnum);
			/*following code is for generating the random number */
			String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";

			String pw = "";
				for (int i=0; i<PASSWORD_LENGTH; i++)
				{
					int index = (int)(RANDOM.nextDouble()*letters.length());
					pw += letters.substring(index, index+1);
				}
			LogLevel.DEBUG(5, new Throwable(), "pw :" + pw);

			/*code to generate the random number ends here */
			/* code to send SMS and Email support guy */ 
			CompareTrans ct = new CompareTrans(qm);
			SendSMS sendsms = new SendSMS(qm);
			String ThankUMsg="Battery Check Cust Name: "+strusername+" , Mobile: "+strUsermobileno+" , Location: "+Strlocorpin+"";
			String strSMSResponse=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUMsg, strretmobile);

			String[] tempArr=strtechmobnum.split(",");
			LogLevel.DEBUG(5,new Throwable(),"tempArr.length :"+tempArr.length );
			LogLevel.DEBUG(5,new Throwable(),"tempArr:"+tempArr);
			  if(tempArr.length>0)
			  {
					String strTempValue="";
					for(int k=0;k<tempArr.length ;k++)
					{	
						strTempValue=tempArr[k];
						LogLevel.DEBUG(5,new Throwable(),"tempArr:"+strTempValue);
						String strSMSResponse11=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUMsg, strTempValue);
						LogLevel.DEBUG(5, new Throwable(), "strSMSResponse11 :" + strSMSResponse11);
					}
			  }						
			String ThanksuppsmsMsg="Battery Check Cust Name: "+strusername+", Mobile: "+strUsermobileno+", SE Mobile: "+strtechmobnum+", Location: "+Strlocorpin+".";   
			String strSMSResponse22 =  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThanksuppsmsMsg, strsuppmobnumber);
			LogLevel.DEBUG(5, new Throwable(), "strSMSResponse22 :" + strSMSResponse22);
			
			String ThankUMsgopt="Battery Check Cust Name "+strusername+" with Mob no-"+strUsermobileno+" for battery health check "+batterybrand+" >> "+batterytype+" >> "+vehiclename+" >> "+vehmodel+", SE Mobile: "+strtechmobnum+", Location: "+Strlocorpin+"";
			LogLevel.DEBUG(5, new Throwable(), "ThankUMsgopt :" + ThankUMsgopt);

			String strsubop="Dear operator,\r\n\r\n"+""+ThankUMsgopt+"";
			String strThanksop="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
			MailTrans operator=new MailTrans();
			operator.setStrSmtpHost(strdomainname);
			operator.setStrFrom(FromEmailId);
			operator.setStrTo(strsuppemaild);
			operator.setStrSubject("BookBattery Battery Health Check Details.");
			String activateLinkop = strsubop+"\r\n\r\n"+strThanksop+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
			LogLevel.DEBUG(5, new Throwable(), "activateLinkop :" + activateLinkop);
			operator.setStrText(activateLinkop);
			Thread mtopt=new MailThread(operator,"");
			mtopt.start();

			if(mobileversion.equals("mobile"))
			{
				//strRes="<table border='0' width='100%' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td align='left'>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closemobilediv(greyout(false));'><img src=\"./images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='100%' height='2px' bgcolor='#FFFFFF' valign'top'><tr bgcolor='#FFFFFF'><td align='justify' bgcolor='#FFFFFF' style='font-family:Verdana;font-size:11px;color:#000000;'>Successfully registered your battery health check details. Tech Support member will schedule a battery health check appointment with you.</td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closemobdiv();' class='button4'><br></td></tr><tr height='15'></tr></table>";
				
				strRes ="<table cellspacing='10' cellpadding='0'><table width='100%' valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: center;'> BookBattery</td><td width='100%' align='center' style='COLOR:#F96F2B;     padding-right: 10px; font-size: 24px;font-weight: 600;text-align: left;' onclick='javascript:closemobilediv(greyout(false));' > X</td></tr></table><hr style='background: white; width: 95%;margin-top: 1px;margin-bottom: 1px;'><table border='0' width='100%' height='2px'  valign'top'><tr><td align='justify' style='font-family:Verdana;font-size:14px;color:#FFFFFF;padding: 10px;'>Successfully registered your battery health check details. Tech Support member will schedule a battery health check appointment with you.</td></tr><td width='100%' align='center'> <input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  value='Close' align='left'  onclick='javascript:closemobilediv(greyout(false));' ></td></tr></table></table><tr height='26'><td colspan='3' align='center' class='subheading' id='displayerrormsg'><tr></tr>tr><td height='10'></td></tr></td></tr></table><tr><td height='0'></td></tr></table>";
				
			}
			else
			{
				//strRes="<table border='0' width='300px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closemobilediv(greyout(false));'><img src=\"./images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='300px' height='2px' bgcolor='#FFFFFF' valign'top'><tr bgcolor='#FFFFFF'><td align='justify' bgcolor='#FFFFFF' style='font-family:Verdana;font-size:11px;color:#000000;'>Successfully registered your battery health check details. Tech Support member will schedule a battery health check appointment with you.</td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closemobdiv();' class='button4'><br></td></tr><tr height='15'></tr></table>";
				
				strRes="<div class='col-md-4 col-md-offset-4'>  <table border='0' width='300px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'>  <tr height='10'><table width='100%'  valign='top'></table><table  width='100%'  valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 25px;font-weight: 600;text-align: center;'> BookBattery</td> <td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: left;'  href=\"javascript:closemobilediv(greyout(false));\" > <a style='color: #F96F2B;' href='javascript:closemobilediv(greyout(false));'> X </a> </td></tr></table><hr style='background: white; width: 90%;margin-top: 1px;margin-bottom: 1px;'></tr> </table><table border='0' style='margin-top: 10px;' width='100%' height='2px'  valign'top'><tr ><td align='justify'  style='font-family:Verdana;font-size:14px;color:#FFFFFF;padding: 16px;'>Successfully registered your battery health check details. Tech Support member will schedule a battery health check appointment with you.</td></tr><tr height='10'><td></td></tr><tr height='10'><td align='center'> <input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn' name='Submitrret' value='Submit' disable='false' onclick='javascript:closemobilediv(greyout(false));' > <br></td></tr><tr><td height='10'></td></tr></table> </div>";
			}
			String strSqlQry = "insert into battery_health_check(bat_chk_id,bat_ins_month,bat_ins_year,veh_type,veh_model,bat_type,bat_brand,bat_model,customer_name,customer_mobilnumber,support_name,support_mobilnumber,city,area,pincode,creation_date)values(NULL,'"+inmonth+"','"+inyear+"','"+vehiclename+"','"+vehmodel+"','"+batterytype+"','"+batterybrand+"','','"+strusername+"','"+strUsermobileno+"','"+strretname+"','"+strtechmobnum+"','"+city+"','"+area+"','"+pincode+"',now())";
			LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
			int reslt = qm.executeUpdate(strSqlQry);

			String StrSqlQryuser = "select email_id from batterywale_user_profile where email_id='"+emailid+"'";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQryuser :" + StrSqlQryuser);

			Hashtable htruser = qm.getRow(StrSqlQryuser); 
			String Stremailid=String.valueOf(htruser.get("email_id"));
			LogLevel.DEBUG(5, new Throwable(), "Stremailid :" + Stremailid);

			if(Stremailid.equals(null) || Stremailid.equals("null") || Stremailid == null || Stremailid == "null" || Stremailid =="")
			{
				addrs1 = addrs1.replace("'","\\'");
				addrs2 = addrs2.replace("'","\\'");
				//String strSqlQryuserprof = "insert into user_profile(user_id,email_id,mobile_number,password,first_name,city,state,status,creation_date,created_by)values(NULL,'"+emailid+"','"+strUsermobileno+"','"+password+"','"+strusername+"','"+city+"','"+strstate+"','active',now(),'ngit')";
				String strSqlQryuserprof = "insert into batterywale_user_profile(user_id,email_id,mobile_number,password,name,address,address1,zipcode,city,state,mobile_verify_code,creation_date,created_by) values(NULL,'"+emailid+"','"+strUsermobileno+"','"+pw+"','"+strusername+"','"+addrs1+"','"+addrs2+"','"+userzipcode+"','"+city+"','"+strstate1+"','"+verifycode+"',now(),'ngit')";

				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQryuserprof);
				int reslt1 = qm.executeUpdate(strSqlQryuserprof);
			}
			else
			{
				String strSqlQryupdatepassword = "update batterywale_user_profile set password='"+pw+"' where email_id='"+emailid+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQryupdatepassword : "+strSqlQryupdatepassword);
				int reslt12 = qm.executeUpdate(strSqlQryupdatepassword);
			}
			String ThankUMsguser="Thank You for Ordering the Battery Health Check. Please visit www.bookbattery.com with login name: "+emailid+" and Password: "+pw+" ."; 
			String strSMSResponse2=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUMsguser, strUsermobileno);
			LogLevel.DEBUG(5, new Throwable(), "strSMSResponse2 :" + strSMSResponse2);
			
			String strsub11="Dear "+strusername+",\r\n\r\n"+""+ThankUMsguser+"";
			String strThanks11="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
			MailTrans retdetails11=new MailTrans();
			retdetails11.setStrSmtpHost(strdomainname);
			retdetails11.setStrFrom(FromEmailId);
			retdetails11.setStrTo(emailid);
			retdetails11.setStrSubject("Thank You for Ordering the Battery Health Check.");
			String activateLink12 = strsub11+"\r\n\r\n"+strThanks11+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
			LogLevel.DEBUG(5, new Throwable(), "activateLink12 :" + activateLink12);
			retdetails11.setStrText(activateLink12);
			Thread mt11=new MailThread(retdetails11,"");
			mt11.start();
		}
	}
	catch (Exception e)
	{
	LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
	strRes=strRes;
	}
	return strRes;
}
/* *************************************************************************************************************\
* This method is used to insert consumer details
\* *************************************************************************************************************/
public String insertconsumerdetails1(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort,String SMSFromAddress, String FromEmailId, String baseUrl, String strsuppmobnumber) 
{ 	
	String strusername= (req.getParameter("username")!=null)?(req.getParameter("username")):"";
	String emailid= (req.getParameter("emailid")!=null)?(req.getParameter("emailid")):"";
	String addrs1= (req.getParameter("addrs1")!=null)?(req.getParameter("addrs1")):""; 
	String addrs2= (req.getParameter("addrs2")!=null)?(req.getParameter("addrs2")):"";
	//String userstate= (req.getParameter("userstate")!=null)?(req.getParameter("userstate")):"";
	String userarea= (req.getParameter("userarea")!=null)?(req.getParameter("userarea")):"";
	String usercity= (req.getParameter("usercity")!=null)?(req.getParameter("usercity")):"";
	String userzipcode= (req.getParameter("userzipcode")!=null)?(req.getParameter("userzipcode")):"";
	String inmonth = (req.getParameter("inmonth")!=null)?(req.getParameter("inmonth")):"";
	String inyear = (req.getParameter("inyear")!=null)?(req.getParameter("inyear")):"";
	String batmodelnumber = (req.getParameter("batmodelnumber")!=null)?(req.getParameter("batmodelnumber")):"";
	String strUsermobileno = (req.getParameter("mobilenumber")!=null)?(req.getParameter("mobilenumber")):"";
	String verifycode = (req.getParameter("verifycode")!=null)?(req.getParameter("verifycode")):"";
	String batterytype = (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
	String vehiclename = (req.getParameter("vehiclename")!=null)?(req.getParameter("vehiclename")):"";
	String vehmodel = (req.getParameter("vehmodel")!=null)?(req.getParameter("vehmodel")):"";
	String batterybrand = (req.getParameter("batterybrand")!=null)?(req.getParameter("batterybrand")):"";
	String batterycapcity = (req.getParameter("batterycapcity")!=null)?(req.getParameter("batterycapcity")):"";
	String state = (req.getParameter("state")!=null)?(req.getParameter("state")):"";
	String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
	String area = (req.getParameter("area")!=null)?(req.getParameter("area")):"";
	String pincode = (req.getParameter("pincode")!=null)?(req.getParameter("pincode")):"";
	String mobileversion = (req.getParameter("mobileversion")!=null)?(req.getParameter("mobileversion")):"";

	//password = password.replace("\\","\\\\"); 

	String strRes="";
	String strstate="";
	String strstate1="";
	String StrSqlQry ="";
	String Strretid="";
	
	try
	{		
		CompareTrans ct = new CompareTrans(qm);
		SendSMS sendsms = new SendSMS(qm);

		/*following code is for generating the random number */
		String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";

			String pw = "";
			for (int i=0; i<PASSWORD_LENGTH; i++)
			{
			int index = (int)(RANDOM.nextDouble()*letters.length());
			pw += letters.substring(index, index+1);
			}
		LogLevel.DEBUG(5, new Throwable(), "pw :" + pw);
		if(strUsermobileno.equals("") || strUsermobileno.equals("null") || strUsermobileno.equals(null) || strUsermobileno.equals("undefined"))
		{
			LogLevel.DEBUG(5,new Throwable()," if: ");
			strRes="<table border='0' width='100%' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td align='left'>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closemobdiv(greyout(false));'><img src=\"./images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='100%' height='2px' bgcolor='#FFFFFF' valign'top'><tr bgcolor='#FFFFFF'><td align='justify' bgcolor='#FFFFFF' style='font-family:Verdana;font-size:11px;color:#000000;'>Your session has been expired. Please register your battery details again.</td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closemobdiv();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		}
		else
		{
			if(city.equals("0") || city.equals("") || city == "")
			{
				String StrSqlQrystate1 = "select state,city from battery_pincode where pincode='"+pincode+"'";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQrystate1 :" + StrSqlQrystate1);

				Hashtable htgetstate1 = qm.getRow(StrSqlQrystate1); 
				strstate1=(String)htgetstate1.get("state");	
				String strcity=(String)htgetstate1.get("city");
				if(strcity == null)
				{
					city = "";
				}
				else
				{
					city=strcity;
				}
			}
			LogLevel.DEBUG(5,new Throwable()," else: ");
			String strSqlQry = "insert into battery_registration(bat_reg_id,bat_ins_month,bat_ins_year,bat_type,bat_brand,bat_model,veh_type,veh_model,customer_name,customer_mobilnumber,city,area,pincode,creation_date)values(NULL,'"+inmonth+"','"+inyear+"','"+batterytype+"','"+batterybrand+"','"+batmodelnumber+"','"+vehiclename+"','"+vehmodel+"','"+strusername+"','"+strUsermobileno+"','"+city+"','"+area+"','"+pincode+"',now())";
			LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
			int reslt = qm.executeUpdate(strSqlQry);
			if(mobileversion.equals("mobile"))
			{
				//strRes="<table border='0' width='100%' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td align='left'>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closemobilediv(greyout(false));'><img src=\"./images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='100%' height='2px' bgcolor='#FFFFFF' valign'top'><tr bgcolor='#FFFFFF'><td align='justify' bgcolor='#FFFFFF' style='font-family:Verdana;font-size:11px;color:#000000;'>Successfully registered your battery details. We will remind you on your appropriate battery replacement.!</td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closemobdiv();' class='button4'><br></td></tr><tr height='15'></tr></table>";
				
				
				strRes ="<table cellspacing='10' cellpadding='0'><table width='100%' valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: center;'> BookBattery</td><td width='100%' align='center' style='COLOR:#F96F2B;     padding-right: 10px; font-size: 24px;font-weight: 600;text-align: left;' onclick='javascript:closemobilediv(greyout(false));' > X</td></tr></table><hr style='background: white; width: 95%;margin-top: 1px;margin-bottom: 1px;'><table border='0' width='100%' height='2px'  valign'top'><tr><td align='justify' style='font-family:Verdana;font-size:14px;color:#FFFFFF;padding: 10px;'>Successfully registered your battery details. We will remind you on your appropriate battery replacement.!</td></tr><td width='100%' align='center'> <input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  value='Close' align='left'  onclick='javascript:closemobilediv(greyout(false));' ></td></tr></table></table><tr height='26'><td colspan='3' align='center' class='subheading' id='displayerrormsg'><tr></tr>tr><td height='10'></td></tr></td></tr></table><tr><td height='0'></td></tr></table>";
				
				
			}
			else
			{
				//strRes="<table border='0' width='300px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closemobilediv(greyout(false));'><img src=\"./images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='300px' height='2px' bgcolor='#FFFFFF' valign'top'><tr bgcolor='#FFFFFF'><td align='justify' bgcolor='#FFFFFF' style='font-family:Verdana;font-size:11px;color:#000000;'>Successfully registered your battery details. We will remind you on your appropriate battery replacement.!</td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closemobdiv();' class='button4'><br></td></tr><tr height='15'></tr></table>";
				
				
				strRes="<div class='col-md-4 col-md-offset-4'>  <table border='0' width='300px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'>  <tr height='10'><table width='100%'  valign='top'></table><table  width='100%'  valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 25px;font-weight: 600;text-align: center;'> BookBattery</td> <td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: left;' > <a style='color: #F96F2B;' href='javascript:closemobilediv(greyout(false));'> X </a> </td></tr></table><hr style='background: white; width: 90%;margin-top: 1px;margin-bottom: 1px;'></tr> </table><table border='0' style='margin-top: 10px;' width='100%' height='2px'  valign'top'><tr ><td align='justify'  style='font-family:Verdana;font-size:14px;color:#FFFFFF;padding: 16px;'>Successfully registered your battery details. We will remind you on your appropriate battery replacement.!</td></tr><tr height='10'><td></td></tr><tr height='10'><td align='center'> <input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn' name='Submitrret' value='Submit' disable='false' onclick='javascript:closemobilediv(greyout(false));' > <br></td></tr><tr><td height='10'></td></tr></table> </div>";
				
			}
		}
		/*String StrSqlQryuser = "select email_id from batterywale_user_profile where email_id='"+emailid+"'";
		LogLevel.DEBUG(5, new Throwable(), "StrSqlQryuser :" + StrSqlQryuser);

		Hashtable htruser = qm.getRow(StrSqlQryuser); 
		String Stremailid=String.valueOf(htruser.get("email_id"));
		LogLevel.DEBUG(5, new Throwable(), "Stremailid :" + Stremailid);

		if(Stremailid.equals(null) || Stremailid.equals("null") || Stremailid == null || Stremailid == "null" || Stremailid =="")
		{
				addrs1 = addrs1.replace("'","\\'");
				addrs2 = addrs2.replace("'","\\'");
				//String strSqlQryuserprof = "insert into user_profile(user_id,email_id,mobile_number,password,first_name,city,state,status,creation_date,created_by)values(NULL,'"+emailid+"','"+strUsermobileno+"','"+password+"','"+strusername+"','"+city+"','"+strstate+"','active',now(),'ngit')";
				String strSqlQryuserprof = "insert into batterywale_user_profile(user_id,email_id,mobile_number,password,name,address,address1,zipcode,city,state,mobile_verify_code,creation_date,created_by) values(NULL,'"+emailid+"','"+strUsermobileno+"','"+pw+"','"+strusername+"','"+addrs1+"','"+addrs2+"','"+userzipcode+"','"+city+"','"+state+"','"+verifycode+"',now(),'ngit')";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQryuserprof :" + strSqlQryuserprof);
				int reslt1 = qm.executeUpdate(strSqlQryuserprof);
		}
		else
		{
				String strSqlQryupdatepassword = "update batterywale_user_profile set password='"+pw+"' where email_id='"+emailid+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQryupdatepassword : "+strSqlQryupdatepassword);
				int reslt12 = qm.executeUpdate(strSqlQryupdatepassword);
		}
				
		String ThankUMsguser="Thank You for Registering your battery details. Please visit www.bookbattery.com with login name: "+emailid+" and Password: "+pw+" ."; 
		String strSMSResponse2=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUMsguser, strUsermobileno);
		LogLevel.DEBUG(5, new Throwable(), "strSMSResponse2 :" + strSMSResponse2);
		
		String strsub11="Dear "+strusername+",\r\n\r\n"+""+ThankUMsguser+"";
		String strThanks11="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
		MailTrans retdetails1=new MailTrans();
		retdetails1.setStrSmtpHost(strdomainname);
		retdetails1.setStrFrom(FromEmailId);
		retdetails1.setStrTo(emailid);
		retdetails1.setStrSubject("Thank You for Registering the Battery Details.");
		String activateLink12 = strsub11+"\r\n\r\n"+strThanks11+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
		LogLevel.DEBUG(5, new Throwable(), "activateLink12 :" + activateLink12);
		retdetails1.setStrText(activateLink12);
		Thread mt11=new MailThread(retdetails1,"");
		mt11.start();
		*/
	
		return strRes;
	}
	catch (Exception e)
	{
	LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
	strRes=strRes;
	}
	return strRes;
}
	
}// end of class
