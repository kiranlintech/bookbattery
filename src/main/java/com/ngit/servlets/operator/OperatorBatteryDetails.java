/***********************************************************************		
	NGIT Confidential. 
	@File Name   : BatteryDetails.java 
	@Description : This Servlet is used to select the Battery details.
	@Author	     : Sai Krishna Daddala.
	@Date        : 1st September 2013
******************************************************************/ 
 
package com.ngit.servlets.operator;  
 
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.mail.*;
import com.ngit.javabean.sendsms.SendSMS;
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

import net.sf.json.*; 
import net.sf.ezmorph.*; 

  
public class OperatorBatteryDetails extends HttpServlet 
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
		//String strUserName=(String)session.getAttribute("sesAdminName"); 
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
		String strWhatToDo = (req.getParameter("hidWhatToDo")!=null)?req.getParameter("hidWhatToDo"):""; 
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
		String OperatorTeamCount = (propsMOPConfig.getProperty("OperatorTeamCount")!=null)?(propsMOPConfig.getProperty("OperatorTeamCount")):"";
		LogLevel.DEBUG(5, new Throwable(), "OperatorTeamCount :" + OperatorTeamCount);
	/* ****************************************************************************************\
	* This action is used to get battery details.
	\* *****************************************************************************************/		
	if(strWhatToDo.equalsIgnoreCase("getbatdetails"))
		{ 
			try
			{
				String strRes=getbatdetails(req,res,session,OperatorTeamCount);
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
	* This action is used to  insert consumer details.
	\* *****************************************************************************************/		
	if(strWhatToDo.equalsIgnoreCase("insertconsumerdetails"))
		{ 
			try
			{
				String strRes=insertconsumerdetails(req,res,session,domainname,strIpAddress,strPort,SMSFromAddress,FromEmailId,strsuppemaild,baseUrl,strsuppmobnumber,strCMSSERVERIP,strPDFFilePath,strPDFRelFilePath,struserName,OperatorTeamCount);
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
	if(strWhatToDo.equalsIgnoreCase("calculatepercentageprice"))
		{ 
			try
			{
				String strRes=calculatepercentageprice(req,res,session);
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
		* This action is used to get battery brands.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("GetOrderDetails"))
		{
			try
			{
				String strRes=GetOrderDetails(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);
				out.close();
			}
			catch (Exception e)
			{										
			LogLevel.ERROR(1, e, "Error :" + e);
			}	
	    }
				
		if(strWhatToDo.equalsIgnoreCase("UpdatePaymentMode"))
		{
			try
			{
				String strRes=UpdatePaymentMode(req,res,session);
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
	* This method is to fetch battery details details.
	* @strSqlQry : carries the query to select battery details from battery_details table.
	* @strSqlQry1 : Query has multiple pages to select battery details from battery_details table.
	\* **************************************************************************************************************************************/
	public String getbatdetails(HttpServletRequest req,HttpServletResponse res,HttpSession session,String OperatorTeamCount)
	{
		String strRes="";
		try
		{	
			String strbattype= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbattype:"+strbattype );

			String strvehmake= (req.getParameter("vehiclename")!=null)?(req.getParameter("vehiclename")):"";
			LogLevel.DEBUG(5,new Throwable(),"strvehmake:"+strvehmake );
			
			String strvehmodel= (req.getParameter("vehiclemodel")!=null)?(req.getParameter("vehiclemodel")):"";
			LogLevel.DEBUG(5,new Throwable(),"strvehmodel:"+strvehmodel );

			String strbatterycapacity= (req.getParameter("batterycapacity")!=null)?(req.getParameter("batterycapacity")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatterycapacity:"+strbatterycapacity );
			
			String strbattbrand= (req.getParameter("batterybrand")!=null)?(req.getParameter("batterybrand")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbattbrand:"+strbattbrand );

			String strbatterycapty= (req.getParameter("batterycapty")!=null)?(req.getParameter("batterycapty")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatterycapty:"+strbatterycapty );
			
			String state= (req.getParameter("state")!=null)?(req.getParameter("state")):"";
			LogLevel.DEBUG(5,new Throwable(),"state:"+state);

			String strcity= (req.getParameter("city")!=null)?(req.getParameter("city")):"";
			LogLevel.DEBUG(5,new Throwable(),"strcity:"+strcity);
			
			String strarea= (req.getParameter("area")!=null)?(req.getParameter("area")):"";
			LogLevel.DEBUG(5,new Throwable(),"strarea:"+strarea);

			String strpincode= (req.getParameter("pincode")!=null)?(req.getParameter("pincode")):"";
			LogLevel.DEBUG(5,new Throwable(),"strpincode:"+strpincode);

			String strSqlQry ="";
			String strstate="";
			String strSqlQrybat="";
			String city="";
			String strConditions1="";
			String strConditionsDetails="";
			String strvehmodel1 = URLEncoder.encode(strvehmodel, "UTF-8");
			LogLevel.DEBUG(5,new Throwable(),"strvehmodel1:"+strvehmodel1);

			if(strpincode == "")
			{
			//String StrSqlQrystate1 = "select state from search_whereever_keywords where city='"+strcity+"'";
			//LogLevel.DEBUG(5, new Throwable(), "StrSqlQrystate1 :" + StrSqlQrystate1);

			//Hashtable htgetstate1 = qm.getRow(StrSqlQrystate1); 
			// strstate=(String)htgetstate1.get("state");	
				strstate=state;
				city=strcity;
			}
			else
			{
			/*String Firsttwochars = "";
			Firsttwochars = strpincode.substring(0,2);
			LogLevel.DEBUG(5,new Throwable(),"Firsttwochars:"+Firsttwochars);*/
			
			String StrSqlQrystate1 = "select state,city from battery_pincode where pincode='"+strpincode+"'";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQrystate1 :" + StrSqlQrystate1);

			Hashtable htgetstate1 = qm.getRow(StrSqlQrystate1); 
			strstate=(String)htgetstate1.get("state");
			String strcity1=(String)htgetstate1.get("city");
			city=strcity1;	
			}
			if(!strbattbrand.equals("All") )
			{
				strConditions1= " and bat_brand='"+strbattbrand+"' ";
			}
			else
			{	
				strConditions1= "";
			}


			if(!strbattbrand.equals("All") )
			{
				strConditionsDetails= " and a.bat_brand='"+strbattbrand+"' ";
			}
			else
			{	
				strConditionsDetails= "";
			}
			if(strbattype.equals("Inverter Batteries"))
			{
				strSqlQrybat ="select distinct(bat_id) from application_chat_mapping where bat_type='"+strbattype+"' and bat_capacity='"+strbatterycapty+"' "+strConditions1+" ";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQrybat :" + strSqlQrybat);
			}
			else
			{
				strSqlQrybat ="select distinct(bat_id) from application_chat_mapping where bat_type='"+strbattype+"' and veh_name='"+strvehmake+"' and veh_model='"+strvehmodel+"' "+strConditions1+" ";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQrybat :" + strSqlQrybat);
			}
			
			ArrayList htav=new ArrayList();
			htav=qm.getField(strSqlQrybat);

			String batids="";
			for(int i=0;i<htav.size();i++)
			{
			if(batids.equals(""))
			batids=htav.get(i).toString();
			else
			batids=batids+","+htav.get(i).toString();
			}
			LogLevel.DEBUG(5,new Throwable(),"batids:"+batids);
			//Vector battmodel=qm.executeQuery(strSqlQrybat);
			//LogLevel.DEBUG(5,new Throwable(),"battmodel:"+battmodel);

			//for ( int j=0; j<battmodel.size(); j++)
			//{
			//Hashtable ht21=(Hashtable)battmodel.get(j);
			//String bat_model =String.valueOf(ht21.get("bat_model"));
			
			//String strSqlQrym ="select bat_act_price,bat_witbat_price from batteryprice where bat_brand='"+strbattbrand+"' and bat_model='"+bat_model+"' and state='"+strstate+"'";
			//LogLevel.DEBUG(5, new Throwable(), "strSqlQrym :" + strSqlQrym);
			
			
			//Vector BatpriceVector=qm.executeQuery(strSqlQrym);
			//LogLevel.DEBUG(5,new Throwable(),"BatpriceVector:"+BatpriceVector );
			if(strbattype.equals("Inverter Batteries"))
			{
				strSqlQry ="select a.map_id,a.bat_brand,a.bat_model,a.bat_capacity,a.bat_warranty,a.icon_url,a.description,b.bat_act_price,b.bat_witbat_price,b.bat_ret_price from application_chat_mapping a, batteryprice b where a.bat_type='"+strbattype+"' and a.bat_capacity='"+strbatterycapty+"' "+strConditionsDetails+" and b.state='"+strstate+"' and b.city='"+city+"' and a.bat_model=b.bat_model and a.bat_id in ("+batids+") order by b.bat_witbat_price asc";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
			}
			else
			{
				strSqlQry ="select a.map_id,a.bat_brand,a.bat_model,a.bat_capacity,a.bat_warranty,a.icon_url,a.description,b.bat_act_price,b.bat_witbat_price,b.bat_ret_price from application_chat_mapping a, batteryprice b where a.bat_type='"+strbattype+"' and a.veh_name='"+strvehmake+"' and a.veh_model='"+strvehmodel+"' "+strConditionsDetails+" and b.state='"+strstate+"' and b.city='"+city+"' and a.bat_model=b.bat_model and a.bat_id in ("+batids+") order by b.bat_witbat_price asc";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
			}
			Vector BatdetailsVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"BatdetailsVector:"+BatdetailsVector );

			String strstatevis ="";
			String cityvis ="";

			if(strbatterycapty.equals("0") || strbatterycapty == "0"){ strbatterycapty = ""; } else  { strbatterycapty=strbatterycapty; }
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

			String strSqlQryvisit = "insert into visitors_orders(vis_ord_id,bat_type,veh_make,veh_model,bat_brand,bat_capacity,state,city,area,pincode,option_type,operator_flag,keynumber,visitors_comments,creation_date)values(NULL,'"+strbattype+"','"+strvehmake+"','"+strvehmodel+"','"+strbattbrand+"','"+strbatterycapty+"','"+strstatevis+"','"+cityvis+"','"+strarea+"','"+strpincode+"','order','yes','"+keyvalue+"','',now())";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQryvisit: "+strSqlQryvisit);
			int reslt = qm.executeUpdate(strSqlQryvisit);

			String StrSqlQrey = "select vis_ord_id from visitors_orders where keynumber='"+keyvalue+"'";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQrey :" + StrSqlQrey);
			
			Hashtable htvisid = qm.getRow(StrSqlQrey); 
			String visid=String.valueOf(htvisid.get("vis_ord_id"));
			
			if(BatdetailsVector.isEmpty())
			{ 
				LogLevel.DEBUG(5,new Throwable(),"Failed to fetch Battery details ");
				session.setAttribute("priority","1");
				session.setAttribute("sesbatterydetailsErrorMsg", "<font color='#ff3333' class='vrb10'>No Battery details found based on selection.! </font> ");
				session.removeAttribute("BatdetailsVector");
				res.sendRedirect("../jsp/operator/orderbattery/orderbattery.jsp");
				return strRes;
			}
			else
			{
				LogLevel.DEBUG(5, new Throwable(),"Successfully Fetched Battery Details");
				session.setAttribute("BatdetailsVector", BatdetailsVector);
				//session.setAttribute("BatpriceVector", BatpriceVector);
				res.sendRedirect("../jsp/operator/orderbattery/orderbatterydetails.jsp?batterytype="+strbattype+"&vehiclemake="+strvehmake+"&vehiclemodel="+strvehmodel1+"&batterybrand="+strbattbrand+"&city="+strcity+"&pincity="+city+"&strarea="+strarea+"&strstate="+strstate+"&strpincode="+strpincode+"&visid="+visid);
				return strRes;
			}
			//}
		}
		catch (Exception e)
		{
			LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
			e.printStackTrace();

		}
		return strRes;
	}
	/* *************************************************************************************************************\
* This method is used to send  verification to user email id and mobile number when user enters mobile number in order now battery 
\* *************************************************************************************************************/
public String sentverificationcode(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort,String SMSFromAddress, String FromEmailId) 
{ 	
	String strUsermobileno = (req.getParameter("strUsermobileno")!=null)?(req.getParameter("strUsermobileno")):"0";
	String batterymodel = (req.getParameter("batterymodel")!=null)?(req.getParameter("batterymodel")):"0";
	String batterybrand = (req.getParameter("batterybrand")!=null)?(req.getParameter("batterybrand")):"0";
	String price = (req.getParameter("price")!=null)?(req.getParameter("price")):"0";
	String withbatprice = (req.getParameter("withbatprice")!=null)?(req.getParameter("withbatprice")):"0";
	String mobileversion = (req.getParameter("mobileversion")!=null)?(req.getParameter("mobileversion")):"0";
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
			CompareTrans ct = new CompareTrans(qm);
			SendSMS sendsms = new SendSMS(qm);
			String ThankUMsg="Your Verification Code is "+verificationcode+"";
			String strSMSResponse=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUMsg, strUsermobileno);
		
			strRes=strRes+"<table cellspacing='10' cellpadding='0' bgcolor='#FFFFFF'><table width='350' bgcolor='#FFFFFF' valign='top'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobdiv(greyout(false));\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b></td></tr></table><tr><td><table width='350'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF'><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font size='2' color='#FF8C00'>Please Enter Verification code received on <br> SMS</font></td></tr></table></td><tr><td height='10'></td></tr></tr><tr><td width='100%' align='center'><input type='hidden' name='passcode' value='"+verificationcode+"'><input class='insidecontent' type='text' autocomplete='off' name='verifycode' id='verifycode' placeholder='123456' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='6' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:checkverification('"+strUsermobileno+"','"+verificationcode+"','"+batterybrand+"','"+batterymodel+"','"+price+"','"+withbatprice+"');return false;} else return true;\"/></td></tr><tr><td height='5'></td></tr><tr><td  width='50%' align='center' ><input type='button' class='batterywalesubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:checkverification('"+strUsermobileno+"','"+verificationcode+"','"+batterybrand+"','"+batterymodel+"','"+price+"','"+withbatprice+"');\");\"></td></tr><tr height='26'><td colspan='3' align='center' class='subheading' id='codeerrormsg'></td></tr><tr><td height='15'></td></tr></table>";
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
public String insertconsumerdetails(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort,String SMSFromAddress, String FromEmailId, String strsuppemaild, String baseUrl, String strsuppmobnumber,String strCMSSERVERIP,String strPDFFilePath,String strPDFRelFilePath,String struserName,String OperatorTeamCount) 
{ 	
	String strusername= (req.getParameter("username")!=null)?(req.getParameter("username")):"";
	String emailid= (req.getParameter("emailid")!=null)?(req.getParameter("emailid")):"";
	String addrs1= (req.getParameter("addrs1")!=null)?(req.getParameter("addrs1")):""; 
	String addrs2= (req.getParameter("addrs2")!=null)?(req.getParameter("addrs2")):"";
	//String userstate= (req.getParameter("userstate")!=null)?(req.getParameter("userstate")):"";
	String userarea= (req.getParameter("userarea")!=null)?(req.getParameter("userarea")):"";
	String usercity= (req.getParameter("usercity")!=null)?(req.getParameter("usercity")):"";
	String userzipcode= (req.getParameter("userzipcode")!=null)?(req.getParameter("userzipcode")):"";
	String strUsermobileno = (req.getParameter("mobilenumber")!=null)?(req.getParameter("mobilenumber")):"";
	String batterymodel = (req.getParameter("batterymodel")!=null)?(req.getParameter("batterymodel")):"";
	String price = (req.getParameter("price")!=null)?(req.getParameter("price")):"";
	String withbatprice = (req.getParameter("withbatprice")!=null)?(req.getParameter("withbatprice")):"";
	String verifycode = (req.getParameter("verifycode")!=null)?(req.getParameter("verifycode")):"";
	String batterybrand = (req.getParameter("batterybrand")!=null)?(req.getParameter("batterybrand")):"";
	LogLevel.DEBUG(5,new Throwable(),"batterybrand:"+batterybrand );
	String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
	String pincity = (req.getParameter("pincity")!=null)?(req.getParameter("pincity")):"";
	String strarea = (req.getParameter("strarea")!=null)?(req.getParameter("strarea")):"";
	String strstate3 = (req.getParameter("strstate")!=null)?(req.getParameter("strstate")):"";
	String strpincode = (req.getParameter("strpincode")!=null)?(req.getParameter("strpincode")):"";
	String batterytype = (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
	String vehiclemake = (req.getParameter("vehiclemake")!=null)?(req.getParameter("vehiclemake")):"";
	String vehiclemodel = (req.getParameter("vehiclemodel")!=null)?(req.getParameter("vehiclemodel")):"";

	String orderedby = (req.getParameter("orderedby")!=null)?(req.getParameter("orderedby")):"";
	LogLevel.DEBUG(5, new Throwable(), "orderedby :" + orderedby);
	
	String Quantity = (req.getParameter("Quantity")!=null)?(req.getParameter("Quantity")):"";
	LogLevel.DEBUG(5, new Throwable(), "Quantity :" + Quantity);
	
	String paymenttype = (req.getParameter("paymenttype")!=null)?(req.getParameter("paymenttype")):"";
	LogLevel.DEBUG(5, new Throwable(), "paymenttype :" + paymenttype);
	
	String refcode = (req.getParameter("refcode")!=null)?(req.getParameter("refcode")):"";
	LogLevel.DEBUG(5, new Throwable(), "refcode :" + refcode);	
	
	String deliverytype = (req.getParameter("deliverytype")!=null)?(req.getParameter("deliverytype")):"";
	LogLevel.DEBUG(5, new Throwable(), "deliverytype :" + deliverytype);
	
	String Order_battery_type = (req.getParameter("Order_battery_type")!=null)?(req.getParameter("Order_battery_type")):"";
	LogLevel.DEBUG(5, new Throwable(), "Order_battery_type :" + Order_battery_type);

	int delivery_charge=0;
	//password = password.replace("\\","\\\\");

	if((batterytype=="Bike Batteries" || batterytype.equals("Bike Batteries"))&& (deliverytype=="Yes" || deliverytype.equals("Yes")))
	{
		delivery_charge=150;
	}
	else
	{
		delivery_charge=delivery_charge;
	}

	//password = password.replace("\\","\\\\");

	String strRes="";
	String strstate="";
	String strstate1="";
	String StrSqlQry ="";
	String Strretid="";
	String Strlocorpin="";
	String strcustax="";
	String strcusper="";
	
	
	String User_Address_Landmark=addrs1+"\r\n Landmark : "+addrs2;
	User_Address_Landmark = User_Address_Landmark.replace("'","\\'");
	LogLevel.DEBUG(5,new Throwable()," User_Address_Landmark "+User_Address_Landmark);
	
	Hashtable htretailerid =new Hashtable();
	try
	{

		if(orderedby.equals("Operator"))
		{
			orderedby="yes";
		}
		else
		{
			orderedby=orderedby;
		}
			if(city.equals("0") || city.equals("") || city == "")
			{
				String strstate2 = strstate3;
				strstate=strstate2.trim().replaceAll(" ", "_"); 
				LogLevel.DEBUG(5, new Throwable(), "strstate :" + strstate);
				strstate1=strstate3;
				LogLevel.DEBUG(5, new Throwable(), "strstate1 :" + strstate1);
			Date now = new Date();
			SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely
			//System.out.println(simpleDateformat.format(now));
			LogLevel.DEBUG(5, new Throwable(), "Day Info :" + simpleDateformat.format(now));
			String day =simpleDateformat.format(now);
			LogLevel.DEBUG(5, new Throwable(), "Day Info String:" + day);
			
			if(day.equals("Sunday"))
			{

			String StrSqlQry13 = "select retailer_id from retailer_location_mapping where pincode='"+strpincode+"'and bat_brand='"+batterybrand+"' and weekend_dealer_flag='Yes' limit 1";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQry13 :" + StrSqlQry13);
			Hashtable htretailerid12 = qm.getRow(StrSqlQry13); 
			if(htretailerid12.isEmpty())
			{
				StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+strpincode+"' and bat_brand='"+batterybrand+"' and weekend_dealer_flag='No' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);

			}
			else
				{
					StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+strpincode+"' and bat_brand='"+batterybrand+"' and weekend_dealer_flag='Yes' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);

				}
			}
			else
			{
				StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+strpincode+"' and bat_brand='"+batterybrand+"' and weekend_dealer_flag='No' limit 1";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
			}

			htretailerid = qm.getRow(StrSqlQry); 
			Strretid=String.valueOf(htretailerid.get("retailer_id"));
			LogLevel.DEBUG(5, new Throwable(), "Strretid :" + Strretid);
			Strlocorpin = strpincode;
			}
			else
			{
			String StrSqlQrystate = "select state from search_whereever_keywords where city='"+city+"'";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQrystate :" + StrSqlQrystate); 

			Hashtable htgetstate = qm.getRow(StrSqlQrystate); 
			strstate1=(String)htgetstate.get("state");

			strstate=strstate1.trim().replaceAll(" ", "_"); 
			LogLevel.DEBUG(5, new Throwable(), "strstate :" + strstate);
			
			Date now = new Date();
			SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely
			//System.out.println(simpleDateformat.format(now));
			LogLevel.DEBUG(5, new Throwable(), "Day Info :" + simpleDateformat.format(now));
			String day =simpleDateformat.format(now);
			LogLevel.DEBUG(5, new Throwable(), "Day Info String:" + day);
			
			if(day.equals("Sunday"))
			{

				String StrSqlQry12 = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+city+"' and area='"+strarea+"' and bat_brand='"+batterybrand+"' and weekend_dealer_flag='Yes' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry12 :" + StrSqlQry12);
				Hashtable htretailerid1 = qm.getRow(StrSqlQry12); 
				if(htretailerid1.isEmpty())
				{
					StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+city+"' and area='"+strarea+"' and bat_brand='"+batterybrand+"' and weekend_dealer_flag='No' limit 1";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);

				}
				else
				{

					StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+city+"' and area='"+strarea+"' and bat_brand='"+batterybrand+"' and weekend_dealer_flag='Yes' limit 1";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
				}
			}
			else
			{
				StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+city+"' and area='"+strarea+"' and bat_brand='"+batterybrand+"' and weekend_dealer_flag='No' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
			}

			htretailerid = qm.getRow(StrSqlQry); 
			Strretid=String.valueOf(htretailerid.get("retailer_id"));
			LogLevel.DEBUG(5, new Throwable(), "Strretid :" + Strretid);
			Strlocorpin = city;
			}

			if(Strretid.equals(null) || Strretid.equals("null") || Strretid == null || Strretid == "null" || Strretid =="")
			{
				strRes="<table border='0' width='300px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closemobdiv(greyout(false));'><img src=\"../images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='300px' height='2px' bgcolor='#FFFFFF' valign'top'><tr class='pages' bgcolor='#FFFFFF'><td align='center' bgcolor='#FFFFFF'>No Retailers Found on Selected City.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closemobdiv();' class='button4'><br></td></tr><tr height='15'></tr></table>";
			}
			else
			{
			String StrSqlQrydet = "select retailer_name,mobile_number,email_id,address1,mobile_numberother,invoice_flag from "+strstate+"_retailers where retailer_id='"+Strretid+"'";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQrydet :" + StrSqlQrydet);

			Hashtable htretailerdetls = qm.getRow(StrSqlQrydet); 
			String strretmobnum=String.valueOf(htretailerdetls.get("mobile_number"));
			String strretname=(String)htretailerdetls.get("retailer_name");
			String strretemail=(String)htretailerdetls.get("email_id");
			String straddress1=(String)htretailerdetls.get("address1");
			String strretothermobnum=String.valueOf(htretailerdetls.get("mobile_numberother"));
			String strinvoiceflag=String.valueOf(htretailerdetls.get("invoice_flag"));

			//######################################## Order Number Code	####################
			String New_Order_ID_SQL = "SELECT ord_id as ORDER_ID FROM battery_order_details ORDER BY ord_id DESC LIMIT 1";
			Hashtable New_Order_ID_HT = qm.getRow(New_Order_ID_SQL);
			LogLevel.DEBUG(5, new Throwable(), "New_Order_ID_HT :" + New_Order_ID_HT);
			int Last_Order_Count;
			if(New_Order_ID_HT.isEmpty())
			{
				/*following code is for generating the random number */
				Random Generator_Order_ID = new Random();   
				Generator_Order_ID.setSeed(System.currentTimeMillis());   
				int num = Generator_Order_ID.nextInt(90) + 10; 
				if (num < 100 || num > 999)
				{   
					num = Generator_Order_ID.nextInt(90) + 10;
					if (num < 100 || num > 999)
					{   
					}   
				} 
				Last_Order_Count=num;
			}
			else
			{
				Last_Order_Count=(Integer)New_Order_ID_HT.get("ORDER_ID");
				LogLevel.DEBUG(5, new Throwable(), "Last_Order_Count :" + Last_Order_Count);
			}

			// #######################Increment on order ID Count
			Last_Order_Count=Last_Order_Count+1;

			/*following code is for generating the random number */
			Random Generator_Order_ID_Ran = new Random();   
			Generator_Order_ID_Ran.setSeed(System.currentTimeMillis());   
			int Num = Generator_Order_ID_Ran.nextInt(90) + 10;
			if (Num < 100 || Num > 999)
			{   
				Num = Generator_Order_ID_Ran.nextInt(90) + 10;
				if (Num < 100 || Num > 999)
				{   
				}   
			} 

			String Last_Order_Count_String = Integer.toString(Last_Order_Count);
			String verificationcode = "";
			String verificationcode1 = Integer.toString(Num);
			verificationcode ="ORDB"+Last_Order_Count_String+""+verificationcode1+"B";
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
			String ThankUSMSMsg = "";
			String ThankUMsg="";
			/*code to generate the random number ends here */
			/* code to send SMS and Email retailers details to consumer */ 
			CompareTrans ct = new CompareTrans(qm);
				String CustomerAddress="";

			if(addrs1.equals(""))
			{
				CustomerAddress="";

			}
			else
			{
				if(addrs2.equals(""))
				{
					addrs2="N/A";
				}
				CustomerAddress=", Address: "+addrs1+", LandMark: "+addrs2+"";

			}
			

			Calendar cal = Calendar.getInstance();
			int currentMonth = cal.get(Calendar.MONTH) + 1;
			int currentYear = cal.get(Calendar.YEAR);

			String strPdfURL="";
			strRes="<table border='0' width='300px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href=\"javascript:navigatetobatterywale('"+emailid+"','"+strUsermobileno+"','"+pw+"','"+strusername+"','"+usercity+"','"+strstate3+"','battery','"+vehiclemake+"','"+vehiclemodel+"','"+batterybrand+"','"+batterymodel+"','"+price+"','"+withbatprice+"','1','"+strretname+"','"+strretmobnum+"');\"><img src=\"/bookbattery/images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='300px' height='2px' bgcolor='#FFFFFF' valign'top'><tr bgcolor='#FFFFFF'><td align='justify' bgcolor='#FFFFFF' style='font-family:Verdana;font-size:11px;color:#000000;'>Your BookBattery Battery Ord Ref No: <b>"+verificationcode+"</b>. <br>Battery Model: <b>"+batterymodel+"</b>. <br>Price: <b>"+price+"</b>. <br>"+strretname+", Mob No-"+strretmobnum+" will fullfill your order.</td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' class='button4' onclick=\"javascript:closemobdiv(greyout(false));\"><br></td></tr><tr height='15'></tr></table>";
			
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

			String StrQryPrice = "select CAST(round("+price+"-("+price+" * ("+strcusper+" + "+strcustax+"))) AS SIGNED) as batteryprice";
			LogLevel.DEBUG(5, new Throwable(), "StrQryPrice :" + StrQryPrice);

			Hashtable htprice = qm.getRow(StrQryPrice); 
			String Strbatteryprice=String.valueOf(htprice.get("batteryprice"));
			LogLevel.DEBUG(5, new Throwable(), "Strbatteryprice :" + Strbatteryprice);

			String StrQryvatPrice = "select CAST(round("+price+" * "+strcusper+") AS SIGNED) as battervatyprice";
			LogLevel.DEBUG(5, new Throwable(), "StrQryvatPrice :" + StrQryvatPrice);

			Hashtable htvatprice = qm.getRow(StrQryvatPrice); 
			String Strbatteryvatprice=String.valueOf(htvatprice.get("battervatyprice"));
			
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
			LogLevel.DEBUG(5, new Throwable(), "Strbatteryvatprice :" + Strbatteryvatprice);

			String StrQrylocaltax = "select CAST(round("+price+" * "+strcustax+") AS SIGNED) as localtax";
			LogLevel.DEBUG(5, new Throwable(), "StrQrylocaltax :" + StrQrylocaltax);

			Hashtable htvatlocal = qm.getRow(StrQrylocaltax); 
			String Strbatterylocalprice=String.valueOf(htvatlocal.get("localtax"));
			if(pincity.equals("Mumbai") || pincity == "Mumbai")
			{
			Strlocalstring = "5.5% Locala";
			}
			else if(pincity.equals("Pune") || pincity == "Pune")
			{
			Strlocalstring = "3.5% Octroi";
			}

			LogLevel.DEBUG(5, new Throwable(), "Strbatterylocalprice :" + Strbatterylocalprice);

			String verifiycode ="ASSIST-AMR-BT"+verificationcode1+"";
			LogLevel.DEBUG(5, new Throwable(), "verificationcode :" + verificationcode);

			//String excelName = "invoice.pdf";
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
			generateExcel.writeToPdf(strusername,pincity,verificationcode,invoice,batterybrand,batterymodel,price,Strbatteryprice,Strbatteryvatprice,Strvatstring,Strbatterylocalprice,Strlocalstring,excelFile,strCMSSERVERIP);
			

			// send the file 
			//res.setHeader("Content-disposition", "attachment; filename=" +excelName );
			//FileInputStream	fis	= new FileInputStream(excelFile);
			//ServletOutputStream	outs = res.getOutputStream();
			//int	j =	0;
			//while ((j =	fis.read())	!= -1) 
			//{
			//		outs.write(j);
			//}
			//outs.flush();
			//outs.close();
			//fis.close();
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
              messageBodyPart.setText("Dear "+strretname+",\r\n\r\nThis is an automated invoice generated by www.bookbattery.com  for the battery ordered by "+strusername+". Please find the attachment for invoice details. \r\n\r\n\r\n Thanks & Regards,\r\n BookBattery Team.\r\n www.BookBattery.com.");  
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

				if(struserName.equals("chatoperator"))
				{
					//Query to check mobile number in visitors_orders table
					String StrSqlQryOperatorname1 = "select agent_name from visitors_orders where mobile_number='"+strUsermobileno+"' and (NOT agent_name = '0' or agent_name = ' ' ) and YEAR(creation_date) = YEAR(NOW()) AND MONTH(creation_date) = MONTH(NOW()) AND DAY(creation_date) = DAY(NOW()) order by creation_date limit 1";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQryOperatorname1 :" + StrSqlQryOperatorname1);

					Hashtable htgetoperatorname1 = qm.getRow(StrSqlQryOperatorname1);
					LogLevel.DEBUG(5, new Throwable(), "htgetoperatorname1 :" + htgetoperatorname1);

					if(htgetoperatorname1.isEmpty())
					{
						
							//Query to check mobile number in battery_order_details table
							String StrSqlQryOperatorname12 = "select agent_name from battery_order_details where consumer_mobnumber='"+strUsermobileno+"' and (NOT agent_name = '0' or agent_name = ' ' ) and YEAR(creation_date) = YEAR(NOW()) AND MONTH(creation_date) = MONTH(NOW()) AND DAY(creation_date) = DAY(NOW()) order by creation_date limit 1";
							LogLevel.DEBUG(5, new Throwable(), "StrSqlQryOperatorname12 :" + StrSqlQryOperatorname12);

							Hashtable htgetoperatorname12 = qm.getRow(StrSqlQryOperatorname12);
							LogLevel.DEBUG(5, new Throwable(), "htgetoperatorname12 :" + htgetoperatorname12);

							if(htgetoperatorname12.isEmpty())
							{
							
								//Query to check mobile number in inverter_order_details table
								String StrSqlQryOperatorname123 = "select agent_name from inverter_order_details where consumer_mobnumber='"+strUsermobileno+"' and (NOT agent_name = '0' or agent_name = ' ' ) and YEAR(creation_date) = YEAR(NOW()) AND MONTH(creation_date) = MONTH(NOW()) AND DAY(creation_date) = DAY(NOW()) order by creation_date limit 1";
								LogLevel.DEBUG(5, new Throwable(), "StrSqlQryOperatorname123 :" + StrSqlQryOperatorname123);

								Hashtable htgetoperatorname123 = qm.getRow(StrSqlQryOperatorname123);
								LogLevel.DEBUG(5, new Throwable(), "htgetoperatorname123 :" + htgetoperatorname123);

								if(htgetoperatorname123.isEmpty())
								{
									
									//Query to get the operator name which has been assigned last
									String StrSqlQryOperatorname = "select agent_name from battery_order_details where (NOT agent_name = '0' or agent_name = ' ' ) order by creation_date desc limit 1";
									LogLevel.DEBUG(5, new Throwable(), "StrSqlQryOperatorname :" + StrSqlQryOperatorname);

									Hashtable htgetoperatorname = qm.getRow(StrSqlQryOperatorname); 
									agent_name=(String)htgetoperatorname.get("agent_name");
									LogLevel.DEBUG(5, new Throwable(), "agent_name :" + agent_name);

									
									String assigntoagent="";
						
						int operatorteamcountint = Integer.parseInt(OperatorTeamCount);

						 for(int i=1; i<operatorteamcountint; i++)
						{
							LogLevel.DEBUG(5, new Throwable(), "i value:" +i);						

							if(agent_name.equals("operator"+i+""))
							{

								int jk = new Integer(i+ 1);

								if(jk == operatorteamcountint)
								{
									assigntoagent="operator1";
									LogLevel.DEBUG(5, new Throwable(), "assigntoagent:" +assigntoagent);
									break;
								}
								else
								{
									assigntoagent="operator"+jk;
									LogLevel.DEBUG(5, new Throwable(), "assigntoagent:" +assigntoagent);
									break;
								}

							}
							
						}
						
						if(assigntoagent.equals(""))
						{
							agent_name="operator1";

						}
						else
						{
							agent_name=assigntoagent;

						}
									String strSqlQry = "insert into battery_order_details(ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailis,state,city,battery_brand,battery_model,price,area,pincode,pdfurl,bat_type,veh_name,veh_model,witholdbatprice,operator,creation_date,agent_name,forwareded_order,delivery_mode,referred_coupon_code,delivery_charge,confirm_by)values(NULL,'"+verificationcode+"','"+strusername+"','"+strUsermobileno+"','"+emailid+"','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+strstate1+"','"+pincity+"','"+batterybrand+"','"+batterymodel+"','"+price+"','"+strarea+"','"+strpincode+"','"+strPdfURL+"','"+batterytype+"','"+vehiclemake+"','"+vehiclemodel+"','"+withbatprice+"','"+orderedby+"',now(),'"+agent_name+"','Yes','"+deliverytype+"','"+refcode+"','"+delivery_charge+"','Operator')";
									LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
									int reslt = qm.executeUpdate(strSqlQry);
								

								}

								else
								{

								agent_name=(String)htgetoperatorname123.get("agent_name");
								LogLevel.DEBUG(5, new Throwable(), "agent_name :" + agent_name);
								String strSqlQry = "insert into battery_order_details(ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailis,state,city,battery_brand,battery_model,price,area,pincode,pdfurl,bat_type,veh_name,veh_model,witholdbatprice,operator,creation_date,agent_name,forwareded_order,delivery_mode,referred_coupon_code,delivery_charge,confirm_by)values(NULL,'"+verificationcode+"','"+strusername+"','"+strUsermobileno+"','"+emailid+"','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+strstate1+"','"+pincity+"','"+batterybrand+"','"+batterymodel+"','"+price+"','"+strarea+"','"+strpincode+"','"+strPdfURL+"','"+batterytype+"','"+vehiclemake+"','"+vehiclemodel+"','"+withbatprice+"','"+orderedby+"',now(),'"+agent_name+"','Yes','"+deliverytype+"','"+refcode+"','"+delivery_charge+"','Operator')";
								LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
								int reslt = qm.executeUpdate(strSqlQry);

								}
							}
							else
							{
								agent_name=(String)htgetoperatorname12.get("agent_name");
								LogLevel.DEBUG(5, new Throwable(), "agent_name :" + agent_name);
								String strSqlQry = "insert into battery_order_details(ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailis,state,city,battery_brand,battery_model,price,area,pincode,pdfurl,bat_type,veh_name,veh_model,witholdbatprice,operator,creation_date,agent_name,forwareded_order,delivery_mode,referred_coupon_code,delivery_charge,confirm_by)values(NULL,'"+verificationcode+"','"+strusername+"','"+strUsermobileno+"','"+emailid+"','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+strstate1+"','"+pincity+"','"+batterybrand+"','"+batterymodel+"','"+price+"','"+strarea+"','"+strpincode+"','"+strPdfURL+"','"+batterytype+"','"+vehiclemake+"','"+vehiclemodel+"','"+withbatprice+"','"+orderedby+"',now(),'"+agent_name+"','Yes','"+deliverytype+"','"+refcode+"','"+delivery_charge+"','Operator')";
								LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
								int reslt = qm.executeUpdate(strSqlQry);

							}
						
					}
					else
					{
							agent_name=(String)htgetoperatorname1.get("agent_name");
							LogLevel.DEBUG(5, new Throwable(), "agent_name :" + agent_name);
							String strSqlQry = "insert into battery_order_details(ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailis,state,city,battery_brand,battery_model,price,area,pincode,pdfurl,bat_type,veh_name,veh_model,witholdbatprice,operator,creation_date,agent_name,forwareded_order,delivery_mode,referred_coupon_code,delivery_charge,confirm_by)values(NULL,'"+verificationcode+"','"+strusername+"','"+strUsermobileno+"','"+emailid+"','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+strstate1+"','"+pincity+"','"+batterybrand+"','"+batterymodel+"','"+price+"','"+strarea+"','"+strpincode+"','"+strPdfURL+"','"+batterytype+"','"+vehiclemake+"','"+vehiclemodel+"','"+withbatprice+"','"+orderedby+"',now(),'"+agent_name+"','Yes','"+deliverytype+"','"+refcode+"','"+delivery_charge+"','Operator')";
							LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
							int reslt = qm.executeUpdate(strSqlQry);
				   }

				}
				else
				{
					String strSqlQry = "insert into battery_order_details(ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailis,state,city,battery_brand,battery_model,price,area,pincode,pdfurl,bat_type,veh_name,veh_model,witholdbatprice,operator,creation_date,agent_name,forwareded_order,delivery_mode,referred_coupon_code,delivery_charge,confirm_by)values(NULL,'"+verificationcode+"','"+strusername+"','"+strUsermobileno+"','"+emailid+"','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+strstate1+"','"+pincity+"','"+batterybrand+"','"+batterymodel+"','"+price+"','"+strarea+"','"+strpincode+"','"+strPdfURL+"','"+batterytype+"','"+vehiclemake+"','"+vehiclemodel+"','"+withbatprice+"','"+orderedby+"',now(),'"+struserName+"','Yes','"+deliverytype+"','"+refcode+"','"+delivery_charge+"','Operator')";
					LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
					int reslt = qm.executeUpdate(strSqlQry);
				}
			String strSqlQryleads ="insert into leads(lead_id,retailer_name,retailer_id,deal_id,email_id,vendor_id,product_id,product_name,mobile_number,vendor_name,promotion_code,price,subcategory_id,category_id,creation_date,created_by,updated_date,updated_by)values(NULL,'"+strretname+"','"+Strretid+"','0','"+emailid+"','22','0','undefined','"+strUsermobileno+"','"+batterybrand+"','0','0','0','0',now(),'ngit',now(),'ngit')"; 
			LogLevel.DEBUG(5,new Throwable()," :strSqlQryleads "+strSqlQryleads);
			int resltleads = qm.executeUpdate(strSqlQryleads);
			

			String Get_Product_Price_SQL ="select DISTINCT a.batprice_id, a.bat_act_price, a.bat_witbat_price, a.bat_capacity, a.bat_ret_price, a.erp_pre_tax, b.city_percentage from batteryprice a, percentage b where a.bat_brand='"+batterybrand+"' and a.bat_model='"+batterymodel+"' and a.state='"+strstate1+"' and a.city='"+pincity+"' and a.city=b.city ORDER BY batprice_id DESC LIMIT 1"; 
			LogLevel.DEBUG(5, new Throwable(), "Get_Product_Price_SQL :" + Get_Product_Price_SQL);

			Hashtable Get_Product_Price_SQL_HT = qm.getRow(Get_Product_Price_SQL);
			LogLevel.DEBUG(5, new Throwable(), "Get_Product_Price_SQL_HT :" + Get_Product_Price_SQL_HT);

			if(Get_Product_Price_SQL_HT.isEmpty())
			{
				strRes="Session Expired or Server Down. Please regenerate your order.";
				return strRes;
			}
			else
			{
				String ProductActualPrice=(String)Get_Product_Price_SQL_HT.get("bat_act_price");
				LogLevel.DEBUG(5, new Throwable(), "ProductActualPrice :" + ProductActualPrice);
				
				String ERP_Pre_TAX =(String)Get_Product_Price_SQL_HT.get("erp_pre_tax");
				LogLevel.DEBUG(5, new Throwable(), "ERP_Pre_TAX :" + ERP_Pre_TAX);
				
				String City_Percentage=(String)Get_Product_Price_SQL_HT.get("city_percentage");
				LogLevel.DEBUG(5, new Throwable(), "City_Percentage :" + City_Percentage);
				
				String Battery_Capacity=(String)Get_Product_Price_SQL_HT.get("bat_capacity");
				LogLevel.DEBUG(5, new Throwable(), "Battery_Capacity :" + Battery_Capacity);
									
				String strSqlQry_Update = "UPDATE battery_order_details SET erp_pre_tax = '"+ERP_Pre_TAX+"', MRP_Price = '"+ProductActualPrice+"', city_percentage = '"+City_Percentage+"', consumer_address='"+User_Address_Landmark+"', battery_capacity='"+Battery_Capacity+"', payment_mode='"+paymenttype+"', payment_mode_type='"+paymenttype+"', quantity='"+Quantity+"', order_type='"+Order_battery_type+"' WHERE  order_number='"+verificationcode+"' ";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry_Update:"+strSqlQry_Update );
				
				int reslt_Update = qm.executeUpdate(strSqlQry_Update);

			}
			

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
									
			//######## Send SMS for ORDER
			Order_SMS Send_Order_SMS_Checkout = new Order_SMS(qm);
			String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,verificationcode,"Battery","Yes","No","No");
			LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
			//######## Send SMS for ORDER
			
			//######## Send Mail for ORDER
			Order_SMS Send_Order_MAIL_Checkout = new Order_SMS(qm);
			String MAIL_Report=Send_Order_MAIL_Checkout.Send_Order_Mail(req,res,session,verificationcode,"Battery","Sir","No","No");
			LogLevel.DEBUG(5, new Throwable(), "MAIL_Report :" + MAIL_Report);
			//######## Send Mail for ORDER
			
			
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
public String calculatepercentageprice(HttpServletRequest req , HttpServletResponse res,HttpSession session) 
{ 	
	String appmapid = (req.getParameter("appmapid")!=null)?(req.getParameter("appmapid")):"";
	String strbatbrand = (req.getParameter("strbatbrand")!=null)?(req.getParameter("strbatbrand")):"";
	String strBatmodel = (req.getParameter("strBatmodel")!=null)?(req.getParameter("strBatmodel")):"";
	String strdisprice = (req.getParameter("strdisprice")!=null)?(req.getParameter("strdisprice")):"";
	String strwithoutprice = (req.getParameter("strwithoutprice")!=null)?(req.getParameter("strwithoutprice")):"";
	String strbattretprice = (req.getParameter("strbattretprice")!=null)?(req.getParameter("strbattretprice")):"";
	String pricediscount = (req.getParameter("pricediscount")!=null)?(req.getParameter("pricediscount")):"";

	String strRes=""; 
	try
	{
		ServletOutputStream out=res.getOutputStream();
		String strSqlQry = "select CAST(round("+strdisprice+"-("+strdisprice+"*"+pricediscount+")) AS SIGNED) as discountprice";
		LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
		
		Vector DiscountPriceVector=qm.executeQuery(strSqlQry);
		LogLevel.DEBUG(5,new Throwable(),"DiscountPriceVector :"+DiscountPriceVector);

		if(DiscountPriceVector==null || DiscountPriceVector.size() == 0)
		{
			out.println("<p align='center' valign='top' class='insidecontent'><table valign='top' width='500' cellspacing='0' border='1' align='center' bgcolor='#FFFFFF' cellpadding='0'><tr><td><table valign='top' width='500' bgcolor='#FFFFFF' border='0'><tr><td class='insidecontent' align='center'>Failed to Calculate discount price!</td></tr></table></td></tr></table><a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
		}
		else
		{
			strRes=strRes+"<table border='0' align='center' bgcolor='#FFFFFF' width='200'><tr>";
			//strRes=strRes+"<td align='center'  valign='top'><input type='button' onClick=\"javascript:deleteappichatdetails();\" value='DELETE' class='button4'></td></tr></table>";
					
			//strRes=strRes+"<table border='0' cellspacing='1' align='center' cellpadding='2' bgcolor='#FFFFFF' width='100%'><tr bgcolor='#2364b1'><td class='prodheading' width='15%'>BatteryType</td><td  class='prodheading' width='15%'>Make</td><td class='prodheading' width='15%'>Model</td><td class='prodheading' width='15%'>BatteryCapacity</td><td class='prodheading' width='25%'>BatteryModel</td><td class='prodheading' width='15%'>DELETE</td></tr>";
			for(int j=0; j<DiscountPriceVector.size();j++)
			{
				Hashtable ht1=(Hashtable)DiscountPriceVector.get(j);
				String discountprice=String.valueOf(ht1.get("discountprice"));
				
				int strdisprices = Integer.parseInt(discountprice);
				int strwitbatprices = Integer.parseInt(strbattretprice);

				int strwithoutprices = strdisprices - strwitbatprices;
				LogLevel.DEBUG(5,new Throwable(),"dddddddhhhhh :"+strwithoutprice );
			
				strRes=strRes+"<table width='95%' border='0' class='tablebat1'  align='center'>";
				strRes=strRes+"<tr><td class='tablebat1'  width='55%'  align='left'><b>With&nbsp;Out&nbsp;Old&nbsp;Battery</b></td><td><b>:</b></td>";
				strRes=strRes+"<td class='tablebat1' width='45%' align='left'><b>"+discountprice+"</b></td></tr>";
				strRes=strRes+"<tr><td height='6'></td><td height='5'></td></tr>";
				strRes=strRes+"<tr><td class='tablebat1' width='55%' align='left'><b>With&nbsp;Old&nbsp;Battery</b></td><td><b>:</b></td>";
				strRes=strRes+"<td class='tablebat1' width='45%' align='left'><b>"+strwithoutprices+"</b></td></tr>";
				strRes=strRes+"<tr><td width='100%' align='right' style='font-family:Verdana;font-size:9px;color:#cccccc;	text-decoration:none;padding:1px 1px;'><input type='button' value='Order&nbsp;it&nbsp;Now' class='buttonindex' onclick=\"javascript:askcosumerdetails('"+strbatbrand+"','"+strBatmodel+"','"+discountprice+"','"+strwithoutprices+"');\"></td><tr>";
				strRes=strRes+"</table>";
				strRes=strRes+"</table>";
				//strRes=strRes+"<table border='0' width='100%'><tr><td><div id='divdiscountprice"+appmapid+"'></div></td></tr></table>";
				
			} 
		}
		//out.println(strRes);

	}
	catch (Exception e)
	{
	LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
	
	}
	return strRes;
}
/* **************************************************************************************************************************************\
* This method is to get battery models.
* @strSqlQry : carries the query to battery models details in battery_details table.
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
					//System.out.println(email);
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
* This method is to get order details for modifying the online to cash payment.
* @strSqlQry : carries the query to battery models details in battery_details table.
\* **************************************************************************************************************************************/	
			
public String GetOrderDetails(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	 
	String ordrefnumber  = (req.getParameter("ordrefnumber")!=null)?req.getParameter("ordrefnumber").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "ordrefnumber :"+ ordrefnumber);


	String DB_Orders_Table="";
	String DB_Orders_Select="";
	String strRes="";
	String strtimerangecond="";
	String Ordered_Type="";
	String Order_Details_String="";
	String Order_Online_Details_String="";
	String forwarded_order="";
	String mobile_number="";
	String Get_Online_Order_Details_SQL="";
		if(ordrefnumber.contains("ORDI"))
		{
			DB_Orders_Table="inverter_order_details";
			DB_Orders_Select="order_number, consumer_name, consumer_mobnumber, retailer_name, retailer_mobile_number as retailer_mobile_number, city, state, price,quantity, payment_mode,forwareded_order, order_reasons";
			Ordered_Type="Inverter";
		}
		else if(ordrefnumber.contains("ORDB"))
		{
			DB_Orders_Table="battery_order_details";
			DB_Orders_Select="order_number, consumer_name, consumer_mobnumber, retailer_name, retailer_mobilnumber  as retailer_mobile_number, city, state, witholdbatprice, price, quantity, payment_mode,forwareded_order, order_type, order_reasons";
			Ordered_Type="Battery";
		}
		else if(ordrefnumber.contains("ORDS"))
		{
			DB_Orders_Table="service_order_details";
			DB_Orders_Select="order_number, consumer_name, consumer_mobnumber, retailer_name, retailer_mobilnumber  as retailer_mobile_number, city, state, service_price_discount as price, quantity, payment_mode,forwareded_order, services_type as order_type, order_reasons";
			Ordered_Type="Service";
		}
		else
		{
			strRes="Wrong Order Number";
			return strRes;
		}
		
		String Get_Order_Details_SQL="SELECT "+DB_Orders_Select+" FROM "+DB_Orders_Table+" WHERE order_number='"+ordrefnumber+"'";
		LogLevel.DEBUG(5, new Throwable(), "Get_Order_Details_SQL :"+ Get_Order_Details_SQL);
		
		Hashtable Get_Order_Details_HT = qm.getRow(Get_Order_Details_SQL);
		LogLevel.DEBUG(5, new Throwable(), "Get_Order_Details_HT :" + Get_Order_Details_HT);
		
		if(Get_Order_Details_HT.isEmpty())
		{
			Order_Details_String="NA";
		}
		else
		{
			JSONObject Get_Order_Details_Json = (JSONObject) JSONSerializer.toJSON(Get_Order_Details_HT);        
			LogLevel.DEBUG(5, new Throwable(), "Get_Order_Details_Json :" + Get_Order_Details_Json);
			Order_Details_String=Get_Order_Details_Json.toString();
			forwarded_order=String.valueOf(Get_Order_Details_HT.get("forwareded_order"));
			mobile_number=String.valueOf(Get_Order_Details_HT.get("consumer_mobnumber"));
		}				
		if(forwarded_order.equals("Yes") || forwarded_order.equals("YES"))
		{			
			//Get_Online_Order_Details_SQL="SELECT a.status as Payment_status, a.payment_url,a.amount,a.description FROM batterywaledb.online_transaction_details a,batterywaledb.battery_order_details b  WHERE a.phone='+91"+mobile_number+"' and b.consumer_emailid=a.email and b.order_reasons='Not Installed-Cancelled-Order Forwarded to BookBattery'";
			Get_Online_Order_Details_SQL="SELECT status as Payment_status, payment_url,amount,description  FROM online_transaction_details WHERE order_id='"+ordrefnumber+"'";
			LogLevel.DEBUG(5, new Throwable(), "Get_Online_Order_Details_SQL :"+ Get_Online_Order_Details_SQL);			
		}
		else
		{
			Get_Online_Order_Details_SQL="SELECT status as Payment_status, payment_url,amount,description  FROM online_transaction_details WHERE order_id='"+ordrefnumber+"'";
			LogLevel.DEBUG(5, new Throwable(), "Get_Online_Order_Details_SQL :"+ Get_Online_Order_Details_SQL);			
		}
		
		Hashtable Get_Online_Order_Details_HT = qm.getRow(Get_Online_Order_Details_SQL);
		LogLevel.DEBUG(5, new Throwable(), "Get_Online_Order_Details_HT :" + Get_Online_Order_Details_HT);
		
		if(Get_Online_Order_Details_HT.isEmpty())
		{
			Order_Online_Details_String="NA";
		}
		else
		{
			JSONObject Order_Online_Details_Json = (JSONObject) JSONSerializer.toJSON(Get_Online_Order_Details_HT);        
			LogLevel.DEBUG(5, new Throwable(), "Order_Online_Details_Json :" + Order_Online_Details_Json);
			Order_Online_Details_String=Order_Online_Details_Json.toString();
		}
		

		JSONObject Get_Online_Order_Details_Json = (JSONObject) JSONSerializer.toJSON(Get_Online_Order_Details_HT);        
		LogLevel.DEBUG(5, new Throwable(), "Get_Online_Order_Details_Json :" + Get_Online_Order_Details_Json);
		
		strRes= Order_Details_String+"|~|"+Order_Online_Details_String+"|~|"+Ordered_Type;
		
		return strRes;

	}

	
	public String UpdatePaymentMode(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	 
		String ordrefnumber  = (req.getParameter("ordrefnumber")!=null)?req.getParameter("ordrefnumber").trim():"";
		LogLevel.DEBUG(5, new Throwable(), "ordrefnumber :"+ ordrefnumber);


		String DB_Orders_Table="";
		String DB_Orders_Select="";
		String strRes="";
		String strtimerangecond="";
		String Ordered_Type="";
		String Order_Details_String="";
		String Order_Online_Details_String="";
		
		try
	   {
		
			if(ordrefnumber.contains("ORDI"))
			{
				DB_Orders_Table="inverter_order_details";
			}
			else if(ordrefnumber.contains("ORDB"))
			{
				DB_Orders_Table="battery_order_details";
			}
			else
			{
				session.setAttribute("sesErrorMsgss","<font color='#CC0000' class='vrb10'>Wrong Order Number.</font> ");
				return strRes;
			}
			

			String strSqlQry = "UPDATE "+DB_Orders_Table+" SET payment_mode = 'Cash', payment_mode_type = 'Cash On Delivary from Online', payment_online_to_cash = 'Yes' WHERE  order_number='"+ordrefnumber+"' ";
			LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
			
			int reslt = qm.executeUpdate(strSqlQry);
			if(reslt <0)
			{
				LogLevel.DEBUG(5, new Throwable(),"Failed Updated The Status");
				session.setAttribute("sesErrorMsgss","<font color='#CC0000' class='vrb10'>Failes Updated The Status.</font> ");
				strRes="Failed Updated The Status";
				//res.sendRedirect("/bookbattery/jsp/operator/order-from-online-to-cash/index.jsp");
			}
			else
			{
				LogLevel.DEBUG(5, new Throwable(),"Succesfully Updated The Status");
				session.setAttribute("sesErrorMsgss","<font color='#08860d'  style='  color: #08860d; font-size: 13px;font-weight: 700;' class='vrb10'>Succesfully Updated The Status.</font> ");
				strRes="Succesfully Updated The Status";
				//res.sendRedirect("/bookbattery/jsp/operator/order-from-online-to-cash/index.jsp");
			}
			
			//res.sendRedirect("/bookbattery/jsp/operator/order-from-online-to-cash/index.jsp");
			return strRes;
		}
		catch (Exception e)
		{
			LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
			e.printStackTrace();
		}
		return strRes;
		
	}


	
}// end of class
