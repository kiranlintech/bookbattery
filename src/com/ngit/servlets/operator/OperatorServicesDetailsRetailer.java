/***********************************************************************		
	Asistmi Solutions Confidential. 
	@File Name   : OperatorServicesDetailsRetailer.java 
	@Description : This Servlet is used to fetch order service for retailer details.
	@Author	     : Lavanya Chowdary.
	@Date        : 6th February 2016.
******************************************************************/ 
 
package com.ngit.servlets.operator;  
 
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.mail.*;
import com.ngit.javabean.sendsms.SendSMS;
import com.ngit.javabean.consumers.products.Order_Service_SMS;
import com.ngit.javabean.consumers.products.GenerateExcelinvoice;
import com.ngit.javabean.consumers.products.CompareTrans;
import com.ngit.javabean.consumers.products.Order_SMS;
import com.ngit.javabean.consumers.products.GenerateExcelinvoice;

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
import java.net.URLDecoder;  
import java.net.URLEncoder; 

import java.util.Properties;
import javax.mail.*;
import javax.activation.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

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

  
public class OperatorServicesDetailsRetailer extends HttpServlet 
{  
	private ServletContext context; 
	QueryManager qm;
	String baseURL;
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
		String struserName=(String)session.getAttribute("sesBatteryOperatorName"); 
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
		String strPDFFilePath	= (propsMOPConfig.getProperty("PDFFilePath")!=null)?(propsMOPConfig.getProperty("PDFFilePath")):"";
		String strPDFRelFilePath = (propsMOPConfig.getProperty("PDFRelFilePath")!=null)?(propsMOPConfig.getProperty("PDFRelFilePath")):"";
	
	/* ****************************************************************************************\
	* This action is used to get service details.
	\* *****************************************************************************************/		
	if(strWhatToDo.equalsIgnoreCase("getservicedetails"))
		{ 
			try
			{
				String strRes=getservicedetails(req,res,session);
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
	* This action is used to insert consumer details.
	\* *****************************************************************************************/		
	if(strWhatToDo.equalsIgnoreCase("insertconsumerdetails"))
		{ 
			try
			{
				String strRes=insertconsumerdetails(req,res,session,domainname,strIpAddress,strPort,SMSFromAddress,FromEmailId,strsuppemaild,baseUrl,strsuppmobnumber,strCMSSERVERIP,strPDFFilePath,strPDFRelFilePath,struserName);
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
	* This action is used to update visitors comments.
	\* *****************************************************************************************/		
	if(strWhatToDo.equalsIgnoreCase("updatevisitorcomment"))
		{ 
			try
			{
				String strRes=updatevisitorcomment(req,res,session);
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
	* This action is used to get retailers.
	\* *****************************************************************************************/		

	if(strWhatToDo.equalsIgnoreCase("getretailers"))
			{ 
			try
			{
				String strRes=getretailers(req,res,session);
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
	/* **************************************************************************************************************************************\
	* This method is to fetch service details.
	* @strSqlQry : carries the query to fetch service details from services_details table.
	* @strSqlQry1 : Query has multiple pages to fetch service details from services_details table.
	\* **************************************************************************************************************************************/
	public String getservicedetails(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		try
		{	
			
			String strvehmake= (req.getParameter("vehiclename")!=null)?(req.getParameter("vehiclename")):"";
			LogLevel.DEBUG(5,new Throwable(),"strvehmake:"+strvehmake );
			
			String strvehmodel= (req.getParameter("vehiclemodel")!=null)?(req.getParameter("vehiclemodel")):"";
			LogLevel.DEBUG(5,new Throwable(),"strvehmodel:"+strvehmodel );

			String strservicestype= (req.getParameter("servicestype")!=null)?(req.getParameter("servicestype")):"";
			LogLevel.DEBUG(5,new Throwable(),"strservicestype:"+strservicestype );
			
			String strservicespackage= (req.getParameter("servicespackage")!=null)?(req.getParameter("servicespackage")):"";
			LogLevel.DEBUG(5,new Throwable(),"strservicespackage:"+strservicespackage );

			String state= (req.getParameter("state")!=null)?(req.getParameter("state")):"";
			LogLevel.DEBUG(5,new Throwable(),"state:"+state);

			String strcity= (req.getParameter("city")!=null)?(req.getParameter("city")):"";
			LogLevel.DEBUG(5,new Throwable(),"strcity:"+strcity);
			
			String strarea= (req.getParameter("area")!=null)?(req.getParameter("area")):"";
			LogLevel.DEBUG(5,new Throwable(),"strarea:"+strarea);

			String strpincode= (req.getParameter("pincode")!=null)?(req.getParameter("pincode")):"";
			LogLevel.DEBUG(5,new Throwable(),"strpincode:"+strpincode);
			
            String retailer= (req.getParameter("retailer")!=null)?(req.getParameter("retailer")):"";
			LogLevel.DEBUG(5,new Throwable(),"retailer:"+retailer);
			
			

			String strSqlQry ="";
			String strstate="";
			String city="";
			String strConditions1="";
			String strConditionsDetails="";
			String strvehmodel1 = URLEncoder.encode(strvehmodel, "UTF-8");
			LogLevel.DEBUG(5,new Throwable(),"strvehmodel1:"+strvehmodel1);

			if(strpincode == "")
			{
			
				strstate=state;
				city=strcity;
			}
			else
			{
			
			String StrSqlQrystate1 = "select state,city from battery_pincode where pincode='"+strpincode+"'";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQrystate1 :" + StrSqlQrystate1);

			Hashtable htgetstate1 = qm.getRow(StrSqlQrystate1); 
			strstate=(String)htgetstate1.get("state");
			String strcity1=(String)htgetstate1.get("city");
			city=strcity1;	
			}
				
			if(strservicestype.equals("Exterior and Interior Cleaning")) 
				{
				 LogLevel.DEBUG(5,new Throwable(),"Exterior and Interior Cleaning");
			
				strSqlQry ="select distinct a.services_type,a.services_package,b.vehicle_model,a.services_package_display,a.description,a.icon_url,a.icon_name,a.service_price_mrp,a.service_price_discount from services_details a, vehicle_details b where a.services_type='"+strservicestype+"' and a.services_package='"+strservicespackage+"' and b.vehicle_model='"+strvehmodel+"' and b.vehicle_name='"+strvehmake+"'";


				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
				}

				else if (strservicestype.equals("Tyre Services Wheel Balancing and Alignment"))
				{
					LogLevel.DEBUG(5,new Throwable(),"Tyre Services Wheel Balancing and Alignment");
					strSqlQry ="select distinct   a.services_type,a.services_package,b.vehicle_model,a.services_package_display,a.description,a.icon_url,a.icon_name,a.service_price_mrp,a.service_price_discount from services_details a, vehicle_details b where a.services_type='"+strservicestype+"' and a.services_package='"+strservicespackage+"' and b.vehicle_model='"+strvehmodel+"' and b.vehicle_name='"+strvehmake+"' and a.vehicle_price_type=b.vehicle_wheelradius";
					LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
				}
				
				else if (strservicestype.equals("Dent Removal and Painting"))
				{
					LogLevel.DEBUG(5,new Throwable(),"Dent Removal and Painting");
					strSqlQry ="select distinct   a.services_type,a.services_package,b.vehicle_model,a.services_package_display,a.description,a.icon_url,a.icon_name,a.service_price_mrp,a.service_price_discount from services_details a, vehicle_details b where a.services_type='"+strservicestype+"' and a.services_package='"+strservicespackage+"' and b.vehicle_model='"+strvehmodel+"' and b.vehicle_name='"+strvehmake+"' and a.vehicle_price_type=b.vehicle_size";
					LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
				}
				
				
				else if (strservicestype.equals("Road Side Assistance"))
				{
					LogLevel.DEBUG(5,new Throwable(),"Road Side Assistance");
					strSqlQry ="select distinct   a.services_type,a.services_package,b.vehicle_model,a.services_package_display,a.description,a.icon_url,a.icon_name,a.service_price_mrp,a.service_price_discount from services_details a, vehicle_details b where a.services_type='"+strservicestype+"' and a.services_package='"+strservicespackage+"' and b.vehicle_model='"+strvehmodel+"' and b.vehicle_name='"+strvehmake+"' and a.vehicle_price_type=b.vehicle_size";
					LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
				}

				
				else if (strservicestype.equals("AC Tuneup and Repair"))
				{
					LogLevel.DEBUG(5,new Throwable(),"AC Tuneup and Repair");
					strSqlQry ="select distinct   a.services_type,a.services_package,b.vehicle_model,a.services_package_display,a.description,a.icon_url,a.icon_name,a.service_price_mrp,a.service_price_discount from services_details a, vehicle_details b where a.services_type='"+strservicestype+"' and a.services_package='"+strservicespackage+"' and b.vehicle_model='"+strvehmodel+"' and b.vehicle_name='"+strvehmake+"' and a.services_package=b.vehicle_ACtype";
					LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
				}

				
				else if (strservicestype.equals("General Checkup"))
				{
					LogLevel.DEBUG(5,new Throwable(),"General Checkup");
					strSqlQry ="select distinct   a.services_type,a.services_package,b.vehicle_model,a.services_package_display,a.description,a.icon_url,a.icon_name,a.service_price_mrp,a.service_price_discount from services_details a, vehicle_details b where a.services_type='"+strservicestype+"' and a.services_package='"+strservicespackage+"' and b.vehicle_model='"+strvehmodel+"' and b.vehicle_name='"+strvehmake+"' and a.vehicle_price_type=b.vehicle_size";
					LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
				}

				else if (strservicestype.equals("Winshield and Glass Repair"))
				{
					LogLevel.DEBUG(5,new Throwable(),"Winshield and Glass Repair");
					strSqlQry ="select distinct   a.services_type,a.services_package,a.vehicle_model,a.services_package_display,a.description,a.icon_url,a.icon_name,a.service_price_mrp,a.service_price_discount from services_details a, vehicle_details b where a.services_type='"+strservicestype+"' and a.services_package='"+strservicespackage+"' and a.vehicle_model='"+strvehmodel+"' and a.vehicle_make='"+strvehmake+"' ";
					LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
				}
				
				else if (strservicestype.equals("Car Battery Health Checkup"))
				{
					LogLevel.DEBUG(5,new Throwable(),"Car Battery Health Checkup");
					strSqlQry ="select distinct   a.services_type,a.services_package,b.vehicle_model,a.services_package_display,a.description,a.icon_url,a.icon_name,a.service_price_mrp,a.service_price_discount from services_details a, vehicle_details b where a.services_type='"+strservicestype+"' and a.services_package='"+strservicespackage+"' and b.vehicle_model='"+strvehmodel+"' and b.vehicle_name='"+strvehmake+"'";
					LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
				}



			Vector ServicesdetailsVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"ServicesdetailsVector:"+ServicesdetailsVector );

			String strstatevis ="";
			String cityvis ="";

			if(strstate == null) { strstatevis = ""; } else { strstatevis = strstate; } 
			if(city == null) { cityvis = ""; } else { cityvis=city;	}

			/*following code is for generating the random number */
			GregorianCalendar date = new GregorianCalendar();
			int millseconds = date.get(Calendar.MILLISECOND);

			String milli = Integer.toString(millseconds);
			char milliseond = milli.charAt(0);
			
			Random r = new Random( System.currentTimeMillis() );
			int num = ((milliseond + r.nextInt(2)) * 10000 + r.nextInt(10000));
			String keyvalue = Integer.toString(num);
			LogLevel.DEBUG(5, new Throwable(), "keyvalue :" + keyvalue);
			
			/*following code is for generating the random number */
			
			String strSqlQryvisit = "insert into service_visitors_orders(vis_ord_id,services_type,veh_make,veh_model,services_package,state,city,area,pincode,option_type,operator_flag,keynumber,visitors_comments,creation_date)values(NULL,'"+strservicestype+"','"+strvehmake+"','"+strvehmodel+"','"+strservicespackage+"','"+strstatevis+"','"+cityvis+"','"+strarea+"','"+strpincode+"','order','yes','"+keyvalue+"','',now())";
			
			LogLevel.DEBUG(5,new Throwable(),"strSqlQryvisit: "+strSqlQryvisit);
			int reslt = qm.executeUpdate(strSqlQryvisit);

			String StrSqlQrey = "select vis_ord_id from service_visitors_orders where keynumber='"+keyvalue+"'";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQrey :" + StrSqlQrey);
			
			Hashtable htvisid = qm.getRow(StrSqlQrey); 
			String visid=String.valueOf(htvisid.get("vis_ord_id"));
			
			if(ServicesdetailsVector.isEmpty())
			{ 
				LogLevel.DEBUG(1,new Throwable(),"Failed to fetch Service details ");
				session.setAttribute("priority","1");
				session.setAttribute("sesbatterydetailsErrorMsg", "<font color='#ff3333' class='vrb10'>No Service details found based on selection.! </font> ");
				session.removeAttribute("ServicesdetailsVector");
				res.sendRedirect("../jsp/operator/orderService/orderServiceretailer.jsp");
				return strRes;
			}
			else
			{
				LogLevel.DEBUG(1, new Throwable(),"Successfully Fetched Service Details");
				session.setAttribute("ServicesdetailsVector", ServicesdetailsVector);
				res.sendRedirect("../jsp/serviceoperator/orderService/orderServicedetailsretailer.jsp?services_type="+strservicestype+"&vehiclemake="+strvehmake+"&vehiclemodel="+strvehmodel1+"&services_package="+strservicespackage+"&city="+strcity+"&pincity="+city+"&strarea="+strarea+"&strstate="+strstate+"&strpincode="+strpincode+"&visid="+visid+"&retailer="+retailer);
				LogLevel.DEBUG(5,new Throwable(),"Successfully: "+ strRes);
				return strRes;
			}
			
		}
		catch (Exception e)
		{
			LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
			e.printStackTrace();

		}
		return strRes;
	}
	/* *************************************************************************************************************\
* This method is used to send  verification code to user email id and mobile number when user enters mobile number in order now services for retailers 
\* *************************************************************************************************************/
public String sentverificationcode(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort,String SMSFromAddress, String FromEmailId) 
{ 	
	String strUsermobileno = (req.getParameter("strUsermobileno")!=null)?(req.getParameter("strUsermobileno")):"0";
	String servicestype = (req.getParameter("servicestype")!=null)?(req.getParameter("servicestype")):"0";
	String servicespackage = (req.getParameter("servicespackage")!=null)?(req.getParameter("servicespackage")):"0";
	String price = (req.getParameter("price")!=null)?(req.getParameter("price")):"0";
	LogLevel.DEBUG(5, new Throwable(), "price :" + price);


	String strRes="";
	try
	{
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
			SendSMS sendsms = new SendSMS(qm);
			String ThankUMsg="Your Verification Code is "+verificationcode+"";
			String strSMSResponse=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUMsg, strUsermobileno);
		
			strRes=strRes+"<table cellspacing='10' cellpadding='0' bgcolor='#FFFFFF'><table width='350' bgcolor='#FFFFFF' valign='top'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobdiv(greyout(false));\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b></td></tr></table><tr><td><table width='350'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF'><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font size='2' color='#FF8C00'>Please Enter Verification code received on <br> SMS</font></td></tr></table></td><tr><td height='10'></td></tr></tr><tr><td width='100%' align='center'><input type='hidden' name='passcode' value='"+verificationcode+"'><input class='insidecontent' type='text' autocomplete='off' name='verifycode' id='verifycode' placeholder='123456' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='6' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:checkverification('"+strUsermobileno+"','"+verificationcode+"','"+servicespackage+"','"+price+"');return false;} else return true;\"/></td></tr><tr><td height='5'></td></tr><tr><td  width='50%' align='center' ><input type='button' class='batterywalesubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:checkverification('"+strUsermobileno+"','"+verificationcode+"','"+servicespackage+"','"+price+"');\");\"></td></tr><tr height='26'><td colspan='3' align='center' class='subheading' id='codeerrormsg'></td></tr><tr><td height='15'></td></tr></table>";
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
public String insertconsumerdetails(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort,String SMSFromAddress, String FromEmailId, String strsuppemaild, String baseUrl, String strsuppmobnumber,String strCMSSERVERIP,String strPDFFilePath,String strPDFRelFilePath,String struserName) 
{ 	
	String strusername= (req.getParameter("username")!=null)?(req.getParameter("username")):"";
	LogLevel.DEBUG(5, new Throwable(), "strusername :" + strusername);
	String emailid= (req.getParameter("emailid")!=null)?(req.getParameter("emailid")):"";
	String addrs1= (req.getParameter("addrs1")!=null)?(req.getParameter("addrs1")):""; 
	String addrs2= (req.getParameter("addrs2")!=null)?(req.getParameter("addrs2")):"";
	String userarea= (req.getParameter("userarea")!=null)?(req.getParameter("userarea")):"";
	String usercity= (req.getParameter("usercity")!=null)?(req.getParameter("usercity")):"";
	String pincity = (req.getParameter("pincity")!=null)?(req.getParameter("pincity")):"";
	LogLevel.DEBUG(5, new Throwable(), "pincity :" + pincity); 
	String userzipcode= (req.getParameter("userzipcode")!=null)?(req.getParameter("userzipcode")):"";
	String strUsermobileno = (req.getParameter("mobilenumber")!=null)?(req.getParameter("mobilenumber")):"";
	String price = (req.getParameter("servicepricemrp")!=null)?(req.getParameter("servicepricemrp")):"";
	String DiscountPrice = (req.getParameter("servicepricediscount")!=null)?(req.getParameter("servicepricediscount")):"";
	String servicespackage = (req.getParameter("servicespackage")!=null)?(req.getParameter("servicespackage")):"";
	String verifycode = (req.getParameter("verifycode")!=null)?(req.getParameter("verifycode")):"";
	String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"";

	String strarea = (req.getParameter("strarea")!=null)?(req.getParameter("strarea")):"";
	String strstate3 = (req.getParameter("strstate")!=null)?(req.getParameter("strstate")):"";
	String strpincode = (req.getParameter("strpincode")!=null)?(req.getParameter("strpincode")):"";
	String servicestype = (req.getParameter("servicestype")!=null)?(req.getParameter("servicestype")):"";
	LogLevel.DEBUG(5, new Throwable(), "servicestype :" + servicestype); 
	String vehiclemake = (req.getParameter("vehiclemake")!=null)?(req.getParameter("vehiclemake")):"";
	String vehiclemodel = (req.getParameter("vehiclemodel")!=null)?(req.getParameter("vehiclemodel")):"";
	String retailer = (req.getParameter("retailer")!=null)?(req.getParameter("retailer")):"";
	String product_capacity = (req.getParameter("product_capacity")!=null)?(req.getParameter("product_capacity")):"";
	String product_type = (req.getParameter("product_type")!=null)?(req.getParameter("product_type")):"";
	LogLevel.DEBUG(5, new Throwable(), "retailer :" + retailer);	
	String payment_mode_sg = (req.getParameter("payment_mode_sg")!=null)?(req.getParameter("payment_mode_sg")):"";
	LogLevel.DEBUG(5, new Throwable(), "payment_mode_sg :" + payment_mode_sg);	
	String order_agent_comments_sg = (req.getParameter("order_agent_comments_sg")!=null)?(req.getParameter("order_agent_comments_sg")):"";
	LogLevel.DEBUG(5, new Throwable(), "order_agent_comments_sg :" + order_agent_comments_sg);
	
	if (order_agent_comments_sg.contains("Forwarded from BookBattery"))
	{
		order_agent_comments_sg="Forwarded from BookBattery";
	}
	else
	{
		order_agent_comments_sg=order_agent_comments_sg;
	}

	String strRes="";
	String strstate="";
	String strstate1="";
	String StrSqlQry ="";
	String Strretid="";
	String Strlocorpin="";
	String strcustax="";
	String strcusper="";
	struserName="operator1";
	Hashtable htretailerid =new Hashtable();
	try
	{

			String StrSqlQrystate = "select state from search_whereever_keywords where city='"+city+"'";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQrystate :" + StrSqlQrystate); 

			Hashtable htgetstate = qm.getRow(StrSqlQrystate); 
			strstate1=(String)htgetstate.get("state");

			strstate=strstate1.trim().replaceAll(" ", "_"); 
			LogLevel.DEBUG(5, new Throwable(), "strstate :" + strstate);

			String[] tempArr12=retailer.split(",");
		    Strretid=tempArr12[0];
		

			String StrSqlQrydet = "select retailer_name,mobile_number,email_id,address1,mobile_numberother,invoice_flag from "+strstate+"_retailers where retailer_id='"+Strretid+"'";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQrydet :" + StrSqlQrydet);

			Hashtable htretailerdetls = qm.getRow(StrSqlQrydet); 
			String strretmobnum=String.valueOf(htretailerdetls.get("mobile_number"));
			String strretname=String.valueOf(htretailerdetls.get("retailer_name"));
			String strretemail=(String)htretailerdetls.get("email_id");
			String straddress1=(String)htretailerdetls.get("address1");
			String strretothermobnum=String.valueOf(htretailerdetls.get("mobile_numberother"));
			String strinvoiceflag=String.valueOf(htretailerdetls.get("invoice_flag"));

			/*following code is for generating the random number */
			GregorianCalendar date = new GregorianCalendar();
			int millseconds = date.get(Calendar.MILLISECOND);
			LogLevel.DEBUG(5, new Throwable(), "millseconds :" + millseconds);

			String milli = Integer.toString(millseconds);
			LogLevel.DEBUG(5, new Throwable(), "milli :" + milli);
			char milliseond = milli.charAt(0);
			LogLevel.DEBUG(5, new Throwable(), "milliseond :" + milliseond);
			
			Random r = new Random( System.currentTimeMillis() );
			LogLevel.DEBUG(5, new Throwable(), "r :" + r);
			int num = ((milliseond + r.nextInt(2)) * 10000 + r.nextInt(10000));
			LogLevel.DEBUG(5, new Throwable(), "num :" + num);


			String verificationcode = "";
			String verificationcode1 = Integer.toString(num);
			verificationcode ="ORDS"+verificationcode1+"B";
			LogLevel.DEBUG(5, new Throwable(), "verificationcode :" + verificationcode);
			
			/*following code is for generating the random number */
			String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789";

			String pw = "";
			for (int i=0; i<PASSWORD_LENGTH; i++)
				{
				int index = (int)(RANDOM.nextDouble()*letters.length());
				pw += letters.substring(index, index+1);	
				}
			LogLevel.DEBUG(5, new Throwable(), "pw :" + pw);
			

			Calendar cal = Calendar.getInstance();
			int currentMonth = cal.get(Calendar.MONTH) + 1;
			int currentYear = cal.get(Calendar.YEAR);

			String strPdfURL="";
			strRes="<table border='0' width='300px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href=\"javascript:navigatetobatterywale('"+emailid+"','"+strUsermobileno+"','"+pw+"','"+strusername+"','"+usercity+"','"+strstate3+"');\"><img src=\"/bookbattery/images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='300px' height='2px' bgcolor='#FFFFFF' valign'top'><tr bgcolor='#FFFFFF'><td align='justify' bgcolor='#FFFFFF' style='font-family:Verdana;font-size:11px;color:#000000;'>Your BookBattery services Ord Ref No: <b>"+verificationcode+"</b>. <br>Services Type: <b>"+servicestype+"</b>.<br>Services Package: <b>"+servicespackage+"</b>. <br>MRP: <b>"+price+"</b>. <br>Discount Price: <b>"+DiscountPrice+"</b>. <br>"+strretname+", Mob No-"+strretmobnum+" will fullfill your order.</td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' class='button4' onclick=\"javascript:closemobdiv(greyout(false));\"><br></td></tr><tr height='15'></tr></table>";
			
			if(strinvoiceflag.equals("yes") || strinvoiceflag == "yes")
			{
			String strSqlQryloctax ="select city_percentage,city_local_percentage  from customer_percentage where city='"+pincity+"'";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQryloctax :" + strSqlQryloctax);

			Hashtable htgetloctax = qm.getRow(strSqlQryloctax); 
			strcusper=String.valueOf(htgetloctax.get("city_percentage"));
			strcustax=String.valueOf(htgetloctax.get("city_local_percentage"));
			LogLevel.DEBUG(5,new Throwable(),"mallidipd:"+strcustax );

			String Strlocalstring ="";
			String Strvatstring="";

			String StrQryPrice = "select CAST(round("+price+"-("+price+" * ("+strcusper+" + "+strcustax+"))) AS SIGNED) as serviceprice";
			LogLevel.DEBUG(5, new Throwable(), "StrQryPrice :" + StrQryPrice);

			Hashtable htprice = qm.getRow(StrQryPrice); 
			String Strserviceprice=String.valueOf(htprice.get("serviceprice"));
			LogLevel.DEBUG(5, new Throwable(), "Strserviceprice :" + Strserviceprice);

			String StrQryvatPrice = "select CAST(round("+price+" * "+strcusper+") AS SIGNED) as servicevatyprice";
			LogLevel.DEBUG(5, new Throwable(), "StrQryvatPrice :" + StrQryvatPrice);

			Hashtable htvatprice = qm.getRow(StrQryvatPrice); 
			String Strservicevatprice=String.valueOf(htvatprice.get("servicevatprice"));
			
			if(pincity.equals("Mumbai") || pincity == "Mumbai")
			{
			Strvatstring = "12.5% VAT";
			}
			else if(pincity.equals("Pune") || pincity == "Pune")
			{
			Strvatstring = "12.5% VAT";
			}
			else
			{
			Strvatstring = "14.5% VAT";
			}
			LogLevel.DEBUG(5, new Throwable(), "Strservicevatprice :" + Strservicevatprice);

			String StrQrylocaltax = "select CAST(round("+price+" * "+strcustax+") AS SIGNED) as localtax";
			LogLevel.DEBUG(5, new Throwable(), "StrQrylocaltax :" + StrQrylocaltax);

			Hashtable htvatlocal = qm.getRow(StrQrylocaltax); 
			String Strservicelocalprice=String.valueOf(htvatlocal.get("localtax"));
			if(pincity.equals("Mumbai") || pincity == "Mumbai")
			{
			Strlocalstring = "5.5% Locala";
			}
			else if(pincity.equals("Pune") || pincity == "Pune")
			{
			Strlocalstring = "3.5% Octroi";
			}

			LogLevel.DEBUG(5, new Throwable(), "Strservicelocalprice :" + Strservicelocalprice);

			String verifiycode ="ASSIST-AMR-BT"+verificationcode1+"";
			LogLevel.DEBUG(5, new Throwable(), "verificationcode :" + verificationcode);

			String excelName = "ASPL_"+strusername+"_"+verifiycode+"_Invoice.pdf";
			String invoice ="ASSIST-AMR-BT"+verificationcode1+"";
			
			strPDFFilePath = strPDFFilePath +"/"+ currentYear + "/" +currentMonth;
			File excelUploadPathCreate = new File(strPDFFilePath);

			if(excelUploadPathCreate.mkdirs())
			{
				LogLevel.DEBUG(3,new Throwable()," PDF for invoice	Name directory created :  "	+ excelUploadPathCreate);
			}
			
			strPDFRelFilePath	= strPDFRelFilePath +"/"+ currentYear + "/" +currentMonth;

			String excelFile = excelUploadPathCreate+"/"+excelName;
			File file=new File(excelFile); 
			if(!file.exists()) 
				file.createNewFile(); 
			FileWriter fw=new FileWriter(file);	
			GenerateExcelinvoice generateExcel	= new GenerateExcelinvoice();
			generateExcel.writeToPdf(strusername,pincity,verificationcode,invoice,servicestype,servicespackage,price,Strserviceprice,Strservicevatprice,Strvatstring,Strservicelocalprice,Strlocalstring,excelFile,strCMSSERVERIP);
			
			final String username = "admin@ngit.in";
			final String password = "adminNGIT";
			String subject="BookBattery Customer Invoice for "+strusername+""; 
		
			Properties props = new Properties();
			props.put("mail.smtp.auth", true);
			props.put("mail.smtp.starttls.enable", true);
			props.put("mail.smtp.host", "smtp.net4india.com");
			props.put("mail.smtp.port", "587");

			Session session1 = Session.getInstance(props,
			new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
						}
					});

			 Message msg = new MimeMessage(session1);  
            
              msg.setFrom(new InternetAddress(FromEmailId));  
           // create the message part   
              MimeBodyPart messageBodyPart = new MimeBodyPart();  
          //fill message  
              messageBodyPart.setText("Dear "+strretname+",\r\n\r\nThis is an automated invoice generated by www.bookbattery.com  for the services ordered by "+strusername+". Please find the attachment for invoice details. \r\n\r\n\r\n Thanks & Regards,\r\n BookBattery Team.\r\n www.BookBattery.com.");  
              Multipart multipart = new MimeMultipart();  
              multipart.addBodyPart(messageBodyPart);  
           // Part two is attachment  
              messageBodyPart = new MimeBodyPart();  
              DataSource source =   
                new FileDataSource(file);  
              messageBodyPart.setDataHandler(  
                new DataHandler(source));  
             // messageBodyPart.setFileName(file); 
			  messageBodyPart.setFileName(""+strusername+"_invoice.pdf");
              multipart.addBodyPart(messageBodyPart);  
          // Put parts in message  
              msg.setContent(multipart);  
           
  
              //msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(SMTP_TO_ADDRESS));  
			  msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(strretemail));
  
              msg.setSubject(subject); 
			  Transport.send(msg);  

			LogLevel.DEBUG(5,new Throwable(),"trans :"+ msg );

			strPdfURL="http://"+strCMSSERVERIP+"/bookbattery/userdata/billing/consumerinvoices/"+currentYear+"/"+currentMonth+"/"+excelName;
			LogLevel.DEBUG(5, new Throwable(), "strPdfURL : " + strPdfURL);
			}
				String agent_name="";
				LogLevel.DEBUG(5,new Throwable(),"struserName :"+ struserName);
				LogLevel.DEBUG(5,new Throwable(),"servicestype down :"+ servicestype);
				if(struserName.equals("chatoperator"))
				{
					//Query to check mobile number in visitors_orders table
					String StrSqlQryOperatorname1 = "select agent_name from visitors_orders where mobile_number='"+strUsermobileno+"' and (NOT agent_name = '0' or agent_name = ' ' ) and YEAR(creation_date) = YEAR(NOW()) AND MONTH(creation_date) = MONTH(NOW()) AND DAY(creation_date) = DAY(NOW()) order by creation_date limit 1";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQryOperatorname1 :" + StrSqlQryOperatorname1);

					Hashtable htgetoperatorname1 = qm.getRow(StrSqlQryOperatorname1);
					LogLevel.DEBUG(5, new Throwable(), "htgetoperatorname1 :" + htgetoperatorname1);

					if(htgetoperatorname1.isEmpty())
					{
						
							//Query to check mobile number in service_order_details table
							String StrSqlQryOperatorname12 = "select agent_name from service_order_details where consumer_mobnumber='"+strUsermobileno+"' and (NOT agent_name = '0' or agent_name = ' ' ) and YEAR(creation_date) = YEAR(NOW()) AND MONTH(creation_date) = MONTH(NOW()) AND DAY(creation_date) = DAY(NOW()) order by creation_date limit 1";
							LogLevel.DEBUG(5, new Throwable(), "StrSqlQryOperatorname12 :" + StrSqlQryOperatorname12);

							Hashtable htgetoperatorname12 = qm.getRow(StrSqlQryOperatorname12);
							LogLevel.DEBUG(5, new Throwable(), "htgetoperatorname12 :" + htgetoperatorname12);

							if(htgetoperatorname12.isEmpty())
							{
									//Query to get the operator name which has been assigned last
									String StrSqlQryOperatorname = "select agent_name from service_order_details where (NOT agent_name = '0' or agent_name = ' ' ) order by creation_date desc limit 1";
									LogLevel.DEBUG(5, new Throwable(), "StrSqlQryOperatorname :" + StrSqlQryOperatorname);

									Hashtable htgetoperatorname = qm.getRow(StrSqlQryOperatorname); 
									agent_name=(String)htgetoperatorname.get("agent_name");
									LogLevel.DEBUG(5, new Throwable(), "agent_name :" + agent_name);

									
									if(agent_name.equals("operator1"))
									{
										agent_name="operator2";
									}
									else if(agent_name.equals("operator2"))
									{
										agent_name="operator3";
									}
									else if(agent_name.equals("operator3"))
									{
										agent_name="operator1";
									}
									else
									{
										agent_name="operator1";
									}
									String strSqlQry = "insert into service_order_details(ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailid,state,city,services_type,services_package,service_price_mrp,area,pincode,pdfurl,veh_name,veh_model,service_price_discount,creation_date,payment_mode,agent_name,order_status,operator,consumer_address1,consumer_address2,product_type,product_capacity,order_agent_comments)values(NULL,'"+verificationcode+"','"+strusername+"','"+strUsermobileno+"','"+emailid+"','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+strstate3+"','"+pincity+"','"+servicestype+"','"+servicespackage+"','"+price+"','"+strarea+"','"+strpincode+"','"+strPdfURL+"','"+vehiclemake+"','"+vehiclemodel+"','"+DiscountPrice+"',now(),'"+payment_mode_sg+"','"+agent_name+"','confirmed','yes','"+addrs1+"','"+addrs2+"','"+product_type+"','"+product_capacity+"','"+order_agent_comments_sg+"')";
									LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
									int reslt = qm.executeUpdate(strSqlQry);
							}
							else
							{
								agent_name=(String)htgetoperatorname12.get("agent_name");
								LogLevel.DEBUG(5, new Throwable(), "agent_name :" + agent_name);
								String strSqlQry = "insert into service_order_details(ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailid,state,city,services_type,services_package,service_price_mrp,area,pincode,pdfurl,veh_name,veh_model,service_price_discount,creation_date,payment_mode,agent_name,order_status,operator,consumer_address1,consumer_address2,product_type,product_capacity,order_agent_comments)values(NULL,'"+verificationcode+"','"+strusername+"','"+strUsermobileno+"','"+emailid+"','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+strstate3+"','"+pincity+"','"+servicestype+"','"+servicespackage+"','"+price+"','"+strarea+"','"+strpincode+"','"+strPdfURL+"','"+vehiclemake+"','"+vehiclemodel+"','"+DiscountPrice+"',now(),'"+payment_mode_sg+"','"+agent_name+"','confirmed','yes','"+addrs1+"','"+addrs2+"','"+product_type+"','"+product_capacity+"','"+order_agent_comments_sg+"')";
								LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
								int reslt = qm.executeUpdate(strSqlQry);

							}
						
					}
					else
					{
							agent_name=(String)htgetoperatorname1.get("agent_name");
							LogLevel.DEBUG(5, new Throwable(), "agent_name :" + agent_name);
							String strSqlQry = "insert into service_order_details(ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailid,state,city,services_type,services_package,service_price_mrp,area,pincode,pdfurl,veh_name,veh_model,service_price_discount,creation_date,payment_mode,agent_name,order_status,operator,consumer_address1,consumer_address2,product_type,product_capacity,order_agent_comments)values(NULL,'"+verificationcode+"','"+strusername+"','"+strUsermobileno+"','"+emailid+"','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+strstate3+"','"+pincity+"','"+servicestype+"','"+servicespackage+"','"+price+"','"+strarea+"','"+strpincode+"','"+strPdfURL+"','"+vehiclemake+"','"+vehiclemodel+"','"+DiscountPrice+"',now(),'"+payment_mode_sg+"','"+agent_name+"','confirmed','yes','"+addrs1+"','"+addrs2+"','"+product_type+"','"+product_capacity+"','"+order_agent_comments_sg+"')";
							LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
							int reslt = qm.executeUpdate(strSqlQry);
				   }

				}
				else   
				{
					String strSqlQry = "insert into service_order_details(ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailid,state,city,services_type,services_package,service_price_mrp,area,pincode,pdfurl,veh_name,veh_model,service_price_discount,creation_date,payment_mode,agent_name,order_status,operator,consumer_address1,consumer_address2,product_type,product_capacity,order_agent_comments)values(NULL,'"+verificationcode+"','"+strusername+"','"+strUsermobileno+"','"+emailid+"','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+strstate3+"','"+pincity+"','"+servicestype+"','"+servicespackage+"','"+price+"','"+strarea+"','"+strpincode+"','"+strPdfURL+"','"+vehiclemake+"','"+vehiclemodel+"','"+DiscountPrice+"',now(),'"+payment_mode_sg+"','"+struserName+"','confirmed','yes','"+addrs1+"','"+addrs2+"','"+product_type+"','"+product_capacity+"','"+order_agent_comments_sg+"')";
					LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
					int reslt = qm.executeUpdate(strSqlQry);
				}
				
			String strSqlQryleads ="insert into leads(lead_id,retailer_name,retailer_id,deal_id,email_id,vendor_id,product_id,product_name,mobile_number,vendor_name,promotion_code,price,subcategory_id,category_id,creation_date,created_by,updated_date,updated_by)values(NULL,'"+strretname+"','"+Strretid+"','0','"+emailid+"','22','0','undefined','"+strUsermobileno+"','"+servicestype+"','0','0','0','0',now(),'ngit',now(),'ngit')"; 
			LogLevel.DEBUG(5,new Throwable()," :strSqlQryleads "+strSqlQryleads);
			int resltleads = qm.executeUpdate(strSqlQryleads);

			String StrSqlQryuser = "select email_id from batterywale_user_profile where email_id='"+emailid+"'";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQryuser :" + StrSqlQryuser);

			Hashtable htruser = qm.getRow(StrSqlQryuser); 
			String Stremailid=String.valueOf(htruser.get("email_id"));
			LogLevel.DEBUG(5, new Throwable(), "Stremailid :" + Stremailid);
			
			
			//######## Send SMS for ORDER
			Order_Service_SMS Send_Order_SMS_Checkout = new Order_Service_SMS(qm);
			String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,verificationcode,"Yes","No","No");
			LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
			//######## Send SMS for ORDER

			//######## Send Mail for ORDER
			Order_Service_SMS Send_Order_MAIL_Checkout = new Order_Service_SMS(qm);
			String MAIL_Report=Send_Order_MAIL_Checkout.Send_Order_Mail(req,res,session,verificationcode,"Sir","No","No");
			LogLevel.DEBUG(5, new Throwable(), "MAIL_Report :" + MAIL_Report);
			//######## Send Mail for ORDER


			if(Stremailid.equals(null) || Stremailid.equals("null") || Stremailid == null || Stremailid == "null" || Stremailid =="")
			{
				addrs1 = addrs1.replace("'","\\'");
				addrs2 = addrs2.replace("'","\\'");
				
				String strSqlQryuserprof = "insert into batterywale_user_profile(user_id,email_id,mobile_number,password,name,address,address1,zipcode,city,state,mobile_verify_code,creation_date,created_by) values(NULL,'"+emailid+"','"+strUsermobileno+"','"+pw+"','"+strusername+"','"+addrs1+"','"+addrs2+"','"+userzipcode+"','"+city+"','"+strstate3+"','"+verifycode+"',now(),'ngit')";

				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQryuserprof);
				int reslt1 = qm.executeUpdate(strSqlQryuserprof);
			}
			else
			{
				String strSqlQryupdatepassword = "update batterywale_user_profile set password='"+pw+"' where email_id='"+emailid+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQryupdatepassword : "+strSqlQryupdatepassword);
				int reslt12 = qm.executeUpdate(strSqlQryupdatepassword);
			}
		
	}
	
	catch (Exception e)
	{
	LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
	strRes=strRes;
	}
	return strRes;
}
	
/* **************************************************************************************************************************************
* This method is to update visitors comments.
* @strSqlQry : carries the query to fetch updated visitors comments in visitors_orders table.
\* **************************************************************************************************************************************/
public String updatevisitorcomment(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
		String visid= (req.getParameter("visid")!=null)?(req.getParameter("visid")):"";
		String vismobilenumber= (req.getParameter("vismobilenumber")!=null)?(req.getParameter("vismobilenumber")):"";
		String viscomment= (req.getParameter("viscomment")!=null)?(req.getParameter("viscomment")):"";

		String strRes="";
		try
		{	
			ServletOutputStream out=res.getOutputStream();

			String strSqlQry = "update visitors_orders set mobile_number='"+vismobilenumber+"',visitors_comments='"+viscomment+"' where vis_ord_id='"+visid+"'";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
			
			int i=qm.executeUpdate(strSqlQry);
			
			if(i <0)
				{
					LogLevel.DEBUG(1,new Throwable(),"Fail to update visitor comment ");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#00dd00' class='vrb10'></font> ");
					out.println("<font color='#FF0000'>Failed to Update Visitors Comment!</font>  ");
				}
				else
				{
					LogLevel.DEBUG(1,new Throwable(),"");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'></font> ");
					out.println("Successfully Updated visitors Comment!");
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
	* This method is to get retailers.
	* @strSqlQry : carries the query to fetch retailers in retailer_location_mapping table.
	\* **************************************************************************************************************************************/
	public String getretailers(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	

			String state= (req.getParameter("state")!=null)?(req.getParameter("state")):"";
			LogLevel.DEBUG(5,new Throwable(),"state:"+state);

			String city= (req.getParameter("city")!=null)?(req.getParameter("city")):"";
			LogLevel.DEBUG(5,new Throwable(),"city:"+city);
			
			
			
			String services_type= (req.getParameter("services_type")!=null)?(req.getParameter("services_type")):"";
			LogLevel.DEBUG(5,new Throwable(),"services_type:"+services_type);

			String retailer_name="";
			String retailer_id="";
			String strSqlqry ="";


	 		ServletOutputStream out=res.getOutputStream();
			
			strSqlqry = "select distinct retailer_id,retailer_name from service_retailer_location_mapping where state='"+state+"' and city='"+city+"' order by retailer_name asc;"; 
			LogLevel.DEBUG(5, new Throwable(), "strSqlqry :" + strSqlqry);
			
			
			Vector RetailerVector=qm.executeQuery(strSqlqry);
			LogLevel.DEBUG(5,new Throwable(),"RetailerVector:"+RetailerVector);
			if(RetailerVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&lt;-&nbsp;&nbsp; Select Retailer -&gt;</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<RetailerVector.size(); i++)
					{
						if(i==0)
						{
						out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&lt;-&nbsp;&nbsp; Select Retailer -&gt;</option>");
						}
						Hashtable ht=(Hashtable)RetailerVector.get(i);
						retailer_name =String.valueOf(ht.get("retailer_name"));
						retailer_id =String.valueOf(ht.get("retailer_id"));

						out.print("<option style='background:#FFF' value='"+retailer_id+","+retailer_name+"'>"+retailer_name+"</option>"); 
					}
				return strRes;
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
