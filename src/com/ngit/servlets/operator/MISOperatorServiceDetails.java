/***********************************************************************		
	Asistmi Solutions Confidential. 
	@File Name   : MISOperatorServiceDetails.java
	@Description : This Servlet is used to select the service order Details.
	@Author	     : Bharath Beeky
	@Date        : Aug 30th, 2016.
******************************************************************/ 
 
package com.ngit.servlets.operator; 
 
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import com.instamojo.wrapper.api.OnlinePayment;
import com.ngit.javabean.admin.mis.GenerateExcelbattery;
import com.ngit.javabean.mail.*;
import com.ngit.javabean.sendsms.SendSMS;
import com.ngit.javabean.consumers.products.CompareTrans;
import com.ngit.javabean.consumers.products.Order_Service_SMS;
import com.ngit.javabean.consumers.products.Order_SMS;

import java.util.Properties;
import java.util.Random;
import java.util.Vector;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
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

import org.json.JSONObject;

  
public class MISOperatorServiceDetails extends HttpServlet 
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
		
		
		/* ****************************************************************************************\
		* This action is used to get locations order mis.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("getlocationsordermis"))
		{
			try
			{
				String strRes=getlocationsordermis(req,res,session);
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
	* This action is used to get confirmed orders mis.
	\* *****************************************************************************************/		

		if(strWhatToDo.equalsIgnoreCase("getconfirmedordersmis"))
			{ 
			try
			{
				String strRes=getconfirmedordersmis(req,res,session,struserName);
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
	* This action is used to get service prices.
	\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("getservicepricedetls"))
		{
			try
			{
				String strRes=getservicepricedetls(req,res,session);
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
		* This action is used to update order status.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("updateorderstatus"))
		{
			try
			{
				String strRes=updateorderstatus(req,res,session,domainname,strIpAddress,strPort,FromEmailId,strsuppemaild,SMSFromAddress);
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
	* This method is to fetch locations order mis details.
	* @strSqlQry : carries the query to fetch locations details from service_order_details table.
	\* **************************************************************************************************************************************/
	public String getlocationsordermis(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
	String strDateOpt  = (req.getParameter("dates")!=null)?req.getParameter("dates"):"";
	LogLevel.DEBUG(5, new Throwable(), "strDateOpt :"+ strDateOpt);

	String keyword  = (req.getParameter("keyword")!=null)?req.getParameter("keyword"):"";
	LogLevel.DEBUG(5, new Throwable(), "keyword :"+ keyword);

	String txtFromDate  = (req.getParameter("txtFromDate")!=null)?req.getParameter("txtFromDate"):"";
	String txtToDate  = (req.getParameter("txtToDate")!=null)?req.getParameter("txtToDate"):"";
	 try
	   {	
		String strSqlQry ="select distinct(city) from service_order_details where city!='' order by city asc";
		LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
		
		Vector LocationVector=qm.executeQuery(strSqlQry);
		LogLevel.DEBUG(5,new Throwable(),"LocationVector:"+LocationVector);
		if(LocationVector.isEmpty())
		{ 
			LogLevel.DEBUG(1,new Throwable(),"Failed to fetch locations ");
			session.setAttribute("priority","1");
			session.setAttribute("sesadminloginErrorMsg", "<font color='#00dd00' class='vrb10'>Failed to fetch locations! </font> ");
			res.sendRedirect("../jsp/serviceoperator/mis/missellocation.jsp?dates="+strDateOpt+"&txtFromDate="+txtFromDate+" &txtToDate="+txtToDate);
			return strRes;
		}
		else
		{
			
			LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched Categories");
			session.setAttribute("seslocatiovector", LocationVector);
			session.setAttribute("sesErrorMsgss","<font color='#CC0000' class='vrb10'>Succesfully Fetched Categories.</font> ");
			res.sendRedirect("../jsp/serviceoperator/ordermis/misselconfirm.jsp?keyword="+keyword+"&dates="+strDateOpt+"&txtFromDate="+txtFromDate+" &txtToDate="+txtToDate);
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
	

  /* **************************************************************************************************************************************\
* This method is to get confirmed orders mis details.
* @strSqlQry: carries the query to fetch service ordered status details from service_order_details table.
\* **************************************************************************************************************************************/
	public String getconfirmedordersmis(HttpServletRequest req , HttpServletResponse res,HttpSession session,String struserName)
	{
	String strRes="";

	Hashtable htOptions=new Hashtable();

	String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"0";
	LogLevel.DEBUG(5,new Throwable(),"city :"+city );

	String keyword  = (req.getParameter("keyword")!=null)?req.getParameter("keyword").trim() :"";
	LogLevel.DEBUG(5, new Throwable(), "keyword :"+ keyword); 

	String strDateOpt  = (req.getParameter("dates")!=null)?req.getParameter("dates").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "strDateOpt :"+ strDateOpt);

	String txtFromDate  = (req.getParameter("txtFromDate")!=null)?req.getParameter("txtFromDate").trim():"";
	String txtToDate  = (req.getParameter("txtToDate")!=null)?req.getParameter("txtToDate").trim():"";

	String statusfilter  = (req.getParameter("statusfilter")!=null)?req.getParameter("statusfilter").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "statusfilter :"+ statusfilter);

	String substatusfilter  = (req.getParameter("substatusfilter")!=null)?req.getParameter("substatusfilter").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "substatusfilter :"+ substatusfilter);

	String servicetypefilter  = (req.getParameter("servicetypefilter")!=null)?req.getParameter("servicetypefilter").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "servicetypefilter :"+ servicetypefilter);

	String retselectname  = (req.getParameter("retselectname")!=null)?req.getParameter("retselectname").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "retselectname :"+ retselectname);
	
	String operatorfilter  = (req.getParameter("operatorfilter")!=null)?req.getParameter("operatorfilter").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "operatorfilter :"+ operatorfilter);

	String fromdatefilter  = (req.getParameter("fromdatefilter")!=null)?req.getParameter("fromdatefilter").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "fromdatefilter :"+ fromdatefilter);
	
	String fromtimefilter  = (req.getParameter("fromtimefilter")!=null)?req.getParameter("fromtimefilter").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "fromtimefilter :"+ fromtimefilter);
	
	String todatefilter  = (req.getParameter("todatefilter")!=null)?req.getParameter("todatefilter").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "todatefilter :"+ todatefilter);
	
	String totimefilter  = (req.getParameter("totimefilter")!=null)?req.getParameter("totimefilter").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "totimefilter :"+ totimefilter);
	
	String test_orders  = (req.getParameter("test_orders")!=null)?req.getParameter("test_orders").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "test_orders :"+ test_orders);
	
	String orders_of  = (req.getParameter("orders_of")!=null)?req.getParameter("orders_of").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "orders_of :"+ orders_of);

	String orderstatus="";
	
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
	
	else if(strDateOpt.equals("selected"))
	{
		if(!fromdatefilter.equals(""))
		{
			txtFromDate=fromdatefilter;
		}
		if(!todatefilter.equals(""))
		{
			txtToDate=todatefilter;
		}
	}
	
	String test_orders_Condition="";
	if(test_orders.equals("yes") || test_orders.equals("Yes"))
	{
		test_orders_Condition ="";
	}
	else 
	{
		test_orders_Condition = "and area NOT LIKE 'test%' and pincode NOT LIKE '1000%'";
	}
	
	//filter for consumer or operator orders 
	if(orders_of.equals("0") || orders_of.equals("All") || orders_of.equals("null") || orders_of.equals(""))
	{
		test_orders_Condition =test_orders_Condition;
	}
	else 
	{
		test_orders_Condition = test_orders_Condition+" and operator='"+orders_of+"'";
	}
	
	
	if(keyword.equals("confirm"))
		{
		orderstatus = "Confirmed";
		}
	else if(keyword.equals("All") || keyword.equals(""))
		{
		orderstatus = "All";
		}
	else if(keyword.equals("ordered"))
		{
		orderstatus = "Ordered";
		}
	else if(keyword.equals("installed"))
		{
		orderstatus = "Installed";
		}
	else if(keyword.equals("cancelled-franchisee-offbushrs"))
		{
		orderstatus = "Cancelled-Franchisee-OffBusHrs";
		}
	else if(keyword.equals("cancelled-franchisee-denied"))
		{
		orderstatus = "Cancelled-Franchisee-Denied";
		}
	else if(keyword.equals("cancelled-franchisee-notresponded"))
		{
		orderstatus = "Cancelled-Franchisee-NotResponded";
		}
	else if(keyword.equals("cancelled-franchisee-modeloutofstock"))
		{
		orderstatus = "Cancelled-Franchisee-ModelOutOfStock";
		}
	else if(keyword.equals("cancelled-customer"))
		{
		orderstatus = "Cancelled-Customer";
		}
		else if(keyword.equals("cancelled-regenerated"))
		{
		orderstatus = "Cancelled-Regenerated";
		}
		else if(keyword.equals("postponed") || keyword.equals("Postponed"))	
		{
		orderstatus = "Postponed";
		}
	LogLevel.DEBUG(5, new Throwable(), "txtFromDate :"+ txtFromDate);
	htOptions.put("txtFromDate",txtFromDate);

	LogLevel.DEBUG(5, new Throwable(), "txtToDate :"+ txtToDate);
	htOptions.put("txtToDate",txtToDate);

	LogLevel.DEBUG(5, new Throwable(), "city :"+ city);
	htOptions.put("city",city);

	LogLevel.DEBUG(5, new Throwable(), "orderstatus :"+ orderstatus);
	htOptions.put("orderstatus",orderstatus);
	 
	try
	{	
	
	String strConditions="";
	String strConditions1="";
	String strConditions2="";
	String strConditions3="";
	String strConditionssubstatus="";
	String strConditionsretailerselect="";
	String strOperatorConditions="";
	String strOperatorFilterConditions="";

	String strSqlQry="";
	String strSqlQry1="";
	SimpleDateFormat sdfDate=new SimpleDateFormat("dd-MM-yyyy");
	Date fromDate=sdfDate.parse(txtFromDate); 
	Date toDate=sdfDate.parse(txtToDate); 

	SimpleDateFormat sdfString=new SimpleDateFormat("yyyy-MM-dd");
	String strFromDate=sdfString.format(fromDate); 
	LogLevel.DEBUG(5, new Throwable(), "strFromDate :"+ strFromDate);

	String strToDate=sdfString.format(toDate); 
	LogLevel.DEBUG(5, new Throwable(), "strToDate :"+ strToDate);
	if(struserName.equals("operator") )
	{
		strOperatorConditions="";
	}
	else
	{
		strOperatorConditions=" and agent_name in ('"+struserName+"','null','NULL','storeoperator')";
	}

	if(statusfilter.equals("") || statusfilter.equals("Select Status"))
	{
		strConditions2="";
	}
	else
	{
		strConditions2=" and order_status='"+statusfilter+"'";
	}
	if(substatusfilter.equals("") || substatusfilter.equals("Select Sub Status") || substatusfilter.equals("0"))
	{
		strConditionssubstatus="";
	}
	else
	{
		strConditionssubstatus=" and order_reasons='"+substatusfilter+"'";
	}

	if(retselectname.equals("") || retselectname.equals("Select Franchisee") || retselectname.equals("0"))
	{
		strConditionsretailerselect="";
	}
	else
	{
		strConditionsretailerselect=" and retailer_name='"+retselectname+"'";
	}

	if(servicetypefilter.equals("") || servicetypefilter.equals("Select Service Type") || servicetypefilter.equals("All"))
	{
		strConditions3="";
	}
	else
	{
		
			strConditions3=" and services_type in ("+servicetypefilter+")";
	}
	
	if(operatorfilter.equals("") || operatorfilter.equals("Select Operator"))
	{
		strOperatorFilterConditions="";
	}
	else
	{
		strOperatorFilterConditions=" and agent_name='"+operatorfilter+"'";
	}

	
	
	
	if(fromdatefilter.equals(""))
	{
		if(substatusfilter.equals("confirm"))
		{
			if(strConditions.equals(""))
			strConditions=" ( date(creation_date) between '"+strFromDate+"' and '"+strToDate+"')";
			else
			strConditions=strConditions+" and ( date(creation_date) between '"+strFromDate+"' and '"+strToDate+"')";
		}
		else if(substatusfilter.equals("installed"))
		{
			if(strConditions.equals(""))
			strConditions="( date(installed_date) between '"+strFromDate+"' and '"+strToDate+"')";
			else
			strConditions=strConditions+" and ( date(installed_date) between '"+strFromDate+"' and '"+strToDate+"')";
		}
		else if(substatusfilter.contains("cancelled"))
		{
			if(strConditions.equals(""))
			strConditions="( date(creation_date) between '"+strFromDate+"' and '"+strToDate+"')";
			else
			strConditions=strConditions+" and ( date(creation_date) between '"+strFromDate+"' and '"+strToDate+"')";
		}
		else if(substatusfilter.equals("postponed") || substatusfilter.equals("Postponed"))
		{
			if(strConditions.equals(""))
			strConditions=" ( date(postpone_date) between '"+strFromDate+"' and '"+strToDate+"')";
			else
			strConditions=strConditions+" and (date(postpone_date) between '"+strFromDate+"' and '"+strToDate+"')";
		}
		else
		{
			if(strConditions.equals(""))
			strConditions=" ( date(creation_date) between '"+strFromDate+"' and '"+strToDate+"' or date(postpone_date) between '"+strFromDate+"' and '"+strToDate+"' or date(installed_date) between '"+strFromDate+"' and '"+strToDate+"' or date(updated_date) between '"+strFromDate+"' and '"+strToDate+"')";
			else
			strConditions=strConditions+" and ( date(creation_date) between '"+strFromDate+"' and '"+strToDate+"' or date(postpone_date) between '"+strFromDate+"' and '"+strToDate+"' or date(installed_date) between '"+strFromDate+"' and '"+strToDate+"' or date(updated_date) between '"+strFromDate+"' and '"+strToDate+"')";
		}
		
		if(statusfilter.equals("SortByCreationDate") )
		{
			strConditions=" ( date(creation_date) between '"+strFromDate+"' and '"+strToDate+"')";
			strConditions2="";
		}
	}
	else
	{
		Date fromDate1=sdfDate.parse(fromdatefilter); 
		String strFromDate1=sdfString.format(fromDate1); 
		LogLevel.DEBUG(5, new Throwable(), "strFromDate1 :"+ strFromDate1);
		
		
		Date toDate1=sdfDate.parse(todatefilter); 
		String strToDate1=sdfString.format(toDate1); 
		LogLevel.DEBUG(5, new Throwable(), "strToDate1 :"+ strToDate1);

		String FromTimeCondition="";
		String ToTimeCondition="";
		
		if(fromtimefilter.equals(""))
		{
			FromTimeCondition=" 00:00:00";
		}
		else{
			FromTimeCondition=" "+fromtimefilter;
		}
		if(totimefilter.equals(""))
		{
			ToTimeCondition=" 23:59:59";
		}
		else{
			ToTimeCondition=" "+totimefilter;
		}
		
		if(substatusfilter.equals("confirm"))
		{

			if(strConditions.equals(""))
			strConditions=" ( creation_date >= '"+strFromDate1+""+FromTimeCondition+"' and creation_date <= '"+strToDate1+""+ToTimeCondition+"')";
			else
			strConditions=strConditions+"( and creation_date >= '"+strFromDate1+""+FromTimeCondition+"' and creation_date <= '"+strToDate1+""+ToTimeCondition+"')";

		}
		else if(substatusfilter.equals("installed"))
		{
			if(strConditions.equals(""))
			strConditions=" ( installed_date >= '"+strFromDate1+""+FromTimeCondition+"' and installed_date <= '"+strToDate1+""+ToTimeCondition+"')";
			else
			strConditions=strConditions+"and installed_date >= '"+strFromDate1+""+FromTimeCondition+"' and installed_date <= '"+strToDate1+""+ToTimeCondition+"'";
		}
		else if(substatusfilter.contains("cancelled"))
		{
			if(strConditions.equals(""))
			strConditions=" ( creation_date >= '"+strFromDate1+""+FromTimeCondition+"' and creation_date <= '"+strToDate1+""+ToTimeCondition+"')";
			else
			strConditions=strConditions+"and creation_date >= '"+strFromDate1+""+FromTimeCondition+"' and creation_date <= '"+strToDate1+""+ToTimeCondition+"'";
		}
		else if(substatusfilter.equals("postponed") || substatusfilter.equals("Postponed"))
		{
			if(strConditions.equals(""))
			strConditions=" (postpone_date >= '"+strFromDate1+""+FromTimeCondition+"' and postpone_date <= '"+strToDate1+""+ToTimeCondition+"')";
			else
			strConditions=strConditions+" and ( postpone_date >= '"+strFromDate1+""+FromTimeCondition+"' and postpone_date <= '"+strToDate1+""+ToTimeCondition+"')";

		}
		else
		{
			if(strConditions.equals(""))
			strConditions=" ( creation_date >= '"+strFromDate1+""+FromTimeCondition+"' and creation_date <= '"+strToDate1+""+ToTimeCondition+"' or postpone_date >= '"+strFromDate1+""+FromTimeCondition+"' and postpone_date <= '"+strToDate1+""+ToTimeCondition+"' or installed_date >= '"+strFromDate1+""+FromTimeCondition+"' and installed_date <= '"+strToDate1+""+ToTimeCondition+"' or updated_date >= '"+strFromDate1+""+FromTimeCondition+"' and updated_date <= '"+strToDate1+""+ToTimeCondition+"' )";
			else
			strConditions=strConditions+" and (creation_date >= '"+strFromDate1+""+FromTimeCondition+"' and creation_date <= '"+strToDate1+""+ToTimeCondition+"' or postpone_date >= '"+strFromDate1+""+FromTimeCondition+"' and postpone_date <= '"+strToDate1+""+ToTimeCondition+"' or installed_date >= '"+strFromDate1+""+FromTimeCondition+"' and installed_date <= '"+strToDate1+""+ToTimeCondition+"' or updated_date >= '"+strFromDate1+""+FromTimeCondition+"' and updated_date <= '"+strToDate1+""+ToTimeCondition+"')";
		}
		
		if(statusfilter.equals("SortByCreationDate") )
		{
			strConditions=" ( creation_date >= '"+strFromDate1+""+FromTimeCondition+"' and creation_date <= '"+strToDate1+""+ToTimeCondition+"')";
			strConditions2="";
		}

	}
	
	if(keyword.equals("confirm"))
		{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' and order_status='confirmed' ";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= " and order_status='confirmed'";
			}
		}
	else if(keyword.equals("All") || keyword.equals(""))
		{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' ";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= "";
			}
		}
	else if(keyword.equals("ordered"))
		{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' and order_status='ordered' ";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= " and order_status='ordered'";
			}
		}
		else if(keyword.equals("installed"))
		{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' and order_status='installed' ";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= " and order_status='installed'";
			}
		}
		else if(keyword.equals("postponed") || keyword.equals("Postponed"))
		{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' and order_status in('postponed','Postponed')";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= " and order_status in('postponed','Postponed')";
			}
		}
		else if(keyword.equals("cancelled-franchisee-offbushrs"))
		{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' and order_status='cancelled-franchisee-offbushrs' ";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= " and order_status='cancelled-franchisee-offbushrs'";
			}
		}
		else if(keyword.equals("cancelled-franchisee-denied"))
			{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' and order_status='cancelled-franchisee-denied' ";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= " and order_status='cancelled-franchisee-denied'";
			}
		}
		else if(keyword.equals("cancelled-franchisee-notresponded"))
			{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' and order_status='cancelled-franchisee-notresponded' ";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= " and order_status='cancelled-franchisee-notresponded'";
			}
		}
		else if(keyword.equals("cancelled-franchisee-modeloutofstock"))
			{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' and order_status='cancelled-franchisee-modeloutofstock' ";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= " and order_status='cancelled-franchisee-modeloutofstock'";
			}
		}
		else if(keyword.equals("cancelled-customer"))
			{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' and order_status='cancelled-customer' ";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= " and order_status='cancelled-customer'";
			}
		}
			else if(keyword.equals("cancelled-regenerated"))
			{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' and order_status='cancelled-regenerated' ";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= " and order_status='cancelled-regenerated'";
			}
		}

	
		strSqlQry="select ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailid,state,city,services_package,service_price_discount,pincode,pdfurl,services_type,veh_name,veh_model,area,service_price_mrp,order_status,payment_mode,service_engineer_name,service_engineer_mobile,payment_mode_type,services_place,order_status,order_reasons,agent_name,order_agent_comments,creation_date,CAST(postpone_date AS CHAR) as postpone_date,CAST(installed_date AS CHAR) as installed_date,CAST(updated_date AS CHAR) as updated_date,CAST(first_contacted_date AS CHAR) as first_contacted_date,operator,consumer_address1,quantity,product_type,batterywale_order,confirm_by from service_order_details where "+strConditions+" "+strConditions1+" "+strConditions2+" "+strConditions3+" "+strConditionssubstatus+" "+strConditionsretailerselect+" "+strOperatorFilterConditions+" "+strOperatorConditions+" "+test_orders_Condition+" and agent_name in ('operator1','null','NULL','storeoperator') order by field(order_status, 'Repeated','Customer Contacted','Postponed','Customer Not Contacted','confirmed','Not Confirmed'),order_reasons,creation_date" ;
		LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
		
		
		//strSqlQry="select ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailis,state,city,battery_brand,battery_model,price,pincode,pdfurl,bat_type,veh_name,veh_model,area,witholdbatprice,quantity,payment_mode,payment_mode_type,order_status,order_reasons,agent_name,order_agent_comments,creation_date,order_type,CAST(postpone_date AS CHAR) as postpone_date, CAST(installed_date AS CHAR) as installed_date, CAST(updated_date AS CHAR) as updated_date,operator,consumer_address,battery_capacity from battery_order_details where  "+strConditions+" "+strConditions1+" "+strConditions2+" "+strConditions3+" "+strConditionssubstatus+" "+strConditionsretailerselect+" "+strOperatorFilterConditions+" "+strOperatorConditions+" "+test_orders_Condition+"  order by field(order_status, 'Repeated','Customer Contacted','Postponed','Customer Not Contacted','confirmed','Not Confirmed'),creation_date" ;
		//LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
		


	Vector ServiceOrdstatusVector=qm.executeQuery(strSqlQry);
	LogLevel.DEBUG(5,new Throwable(),"ServiceOrdstatusVector:"+ ServiceOrdstatusVector );

	strSqlQry1="select distinct retailer_name from service_order_details where "+strConditions+" "+strConditions1+" "+strConditions2+" "+strConditions3+" "+strConditionssubstatus+" "+strOperatorConditions+" order by creation_date";
		LogLevel.DEBUG(5, new Throwable(), "strSqlQry1 :" + strSqlQry1);


	Vector RetOrdstatusVector=qm.executeQuery(strSqlQry1);
	LogLevel.DEBUG(5,new Throwable(),"RetOrdstatusVector:"+ RetOrdstatusVector );


	if(session.getAttribute("sesRetOrderstatusVectorVector")!=null)
	session.removeAttribute("sesRetOrderstatusVectorVector");
	session.setAttribute("sesRetOrderstatusVectorVector",RetOrdstatusVector);

	if(session.getAttribute("sesServiceOrderstatusVector")!=null)
	session.removeAttribute("sesServiceOrderstatusVector");
	session.setAttribute("sesServiceOrderstatusVector",ServiceOrdstatusVector);
	
	if(session.getAttribute("sesOptions")!=null)
	session.removeAttribute("sesOptions");
	session.setAttribute("sesOptions",htOptions);
	res.sendRedirect("../jsp/serviceoperator/ordermis/confirmedordereddetails.jsp?txtFromDate="+txtFromDate+"&txtToDate="+txtToDate+"&city="+city+"&keyword="+keyword+"&dates="+strDateOpt+"&statusfilter="+statusfilter+"&substatusfilter="+substatusfilter+"&servicetypefilter="+servicetypefilter+"&retselectname="+retselectname);
	return strRes;
	}
	catch (Exception e)
	{
	LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
	e.printStackTrace();
	}
 	return strRes;
  }
  /* **************************************************************************************************************************************\
	* This method is to get update order status.
	* @strSqlQry : carries the query to fetch updated service ordered status details from service_order_details table.
	\* **************************************************************************************************************************************/
	public String updateorderstatus(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort, String FromEmailId,String strsuppemaild,String SMSFromAddress)
	{
		String orderid= (req.getParameter("orderid")!=null)?(req.getParameter("orderid")):"";
		LogLevel.DEBUG(5, new Throwable(), "orderid :" + orderid);

		String ordernumber= (req.getParameter("ordernumber")!=null)?(req.getParameter("ordernumber")):"";
		LogLevel.DEBUG(5, new Throwable(), "ordernumber :" + ordernumber);
		
		String rating= (req.getParameter("rating")!=null)?(req.getParameter("rating")):"";
		LogLevel.DEBUG(5, new Throwable(), "rating :" + rating);
        
        String paymentcollected= (req.getParameter("paymentcollected")!=null)?(req.getParameter("paymentcollected")):"";
		LogLevel.DEBUG(5, new Throwable(), "paymentcollected :" + paymentcollected);

		String orderstatus= (req.getParameter("orderstatus")!=null)?(req.getParameter("orderstatus")):"";
		LogLevel.DEBUG(5, new Throwable(), "orderstatus :" + orderstatus);
			
		String servicetype= (req.getParameter("servicetype")!=null)?(req.getParameter("servicetype")):"";
		LogLevel.DEBUG(5, new Throwable(), "servicetype :" + servicetype);
		
		String battype= (req.getParameter("battype")!=null)?(req.getParameter("battype")):"";
		LogLevel.DEBUG(5, new Throwable(), "battype :" + battype);
		
		String serviceplace= (req.getParameter("serviceplace")!=null)?(req.getParameter("serviceplace")):"";
		LogLevel.DEBUG(5, new Throwable(), "serviceplace :" + serviceplace);
		
		String serviceprice= (req.getParameter("serviceprice")!=null)?(req.getParameter("serviceprice")):"";
		LogLevel.DEBUG(5, new Throwable(), "serviceprice :" + serviceprice);
		
		String Quantity= (req.getParameter("Quantity")!=null)?(req.getParameter("Quantity")):"";
		LogLevel.DEBUG(5, new Throwable(), "Quantity :" + Quantity);
		
		String cusname= (req.getParameter("cusname")!=null)?(req.getParameter("cusname")):"";
		LogLevel.DEBUG(5, new Throwable(), "cusname :" + cusname);

		String cusemailid= (req.getParameter("cusemailid")!=null)?(req.getParameter("cusemailid")):"";
		LogLevel.DEBUG(5, new Throwable(), "cusemailid :" + cusemailid);

		String cusaddr1= (req.getParameter("cusaddr1")!=null)?(req.getParameter("cusaddr1")):"";
		LogLevel.DEBUG(5, new Throwable(), "cusaddr1 :" + cusaddr1);

		String cusaddr2= (req.getParameter("cusaddr2")!=null)?(req.getParameter("cusaddr2")):"";
		LogLevel.DEBUG(5, new Throwable(), "cusaddr2 :" + cusaddr2);

		String cusstate= (req.getParameter("cusstate")!=null)?(req.getParameter("cusstate")):"";
		LogLevel.DEBUG(5, new Throwable(), "cusstate :" + cusstate);

		String cuscity= (req.getParameter("cuscity")!=null)?(req.getParameter("cuscity")):"";
		LogLevel.DEBUG(5, new Throwable(), "cuscity :" + cuscity);
		
		String cusarea= (req.getParameter("cusarea")!=null)?(req.getParameter("cusarea")):"";
		LogLevel.DEBUG(5, new Throwable(), "cusarea :" + cusarea);

		String orderreason= (req.getParameter("orderreason")!=null)?(req.getParameter("orderreason")):"";
		LogLevel.DEBUG(5, new Throwable(), "orderreason :" + orderreason);

		String agentcomments= (req.getParameter("agentcomments")!=null)?(req.getParameter("agentcomments")):"";
		LogLevel.DEBUG(5, new Throwable(), "agentcomments :" + agentcomments);

		String postponedate= (req.getParameter("postponedate")!=null)?(req.getParameter("postponedate")):"";
		LogLevel.DEBUG(5, new Throwable(), "postponedate :" + postponedate);
		
		String retailertoorder= (req.getParameter("retailertoorder")!=null)?(req.getParameter("retailertoorder")):"";
		LogLevel.DEBUG(5, new Throwable(), "retailertoorder :" + retailertoorder);
		
		String SMSURL= (req.getParameter("SMSURL")!=null)?(req.getParameter("SMSURL")):"";
		LogLevel.DEBUG(5, new Throwable(), "SMSURL :" + SMSURL);
		
		String shorturl= (req.getParameter("shorturl")!=null)?(req.getParameter("shorturl")):"";
		LogLevel.DEBUG(5, new Throwable(), "shorturl :" + shorturl);
		
		String payment_mode= (req.getParameter("payment_mode")!=null)?(req.getParameter("payment_mode")):"";
		LogLevel.DEBUG(5, new Throwable(), "payment_mode at begin:" + payment_mode);

		String strRes="";
		String service_commission_amount="";
		String stroperatorName=(String)session.getAttribute("sesBatteryOperatorName"); 
		LogLevel.DEBUG(5,new Throwable(),"stroperatorName :"+stroperatorName );
		
		int reslt_new=0;
		
		String googleshorturl= "https://goo.gl/eKGqvC";
		LogLevel.DEBUG(5, new Throwable(), "googleshorturl :" + googleshorturl);
		
		
		String User_Address_Landmark=cusaddr1+" Landmark : "+cusaddr2;
		User_Address_Landmark = User_Address_Landmark.replace("'","\\'");
		LogLevel.DEBUG(5,new Throwable()," User_Address_Landmark "+User_Address_Landmark);
		
		String customerpaid="";
		
		try
		{	
			SendSMS sendsms = new SendSMS(qm);
			String strpostponedate="";

			
			if(orderstatus.equals("postponed") || orderstatus.equals("Postponed"))
			{
				SimpleDateFormat sdfDate=new SimpleDateFormat("dd-MM-yyyy");
				Date txtpostponedate=sdfDate.parse(postponedate);

				SimpleDateFormat sdfString=new SimpleDateFormat("yyyy-MM-dd");
				strpostponedate=sdfString.format(txtpostponedate); 
				LogLevel.DEBUG(5, new Throwable(), "strpostponedate :"+ strpostponedate);

			}


			ServletOutputStream out=res.getOutputStream();
			
			String strSqlQryordstat = "SELECT order_agent_comments,order_status,service_price_discount,order_reasons,consumer_name,consumer_emailid,services_package,order_number,veh_name,veh_model,services_type,batterywale_order,consumer_mobnumber,services_place,retailer_name,retailer_mobilnumber,retailer_emailid,city,product_type,quantity,CAST(first_contacted_date AS CHAR) as first_contacted_date FROM service_order_details WHERE  order_number='"+ordernumber+"' and ord_id='"+orderid+"'";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQryordstat :"+strSqlQryordstat);

			Hashtable ht = qm.getRow(strSqlQryordstat);
			String order_agent_comments=(String)ht.get("order_agent_comments");
			LogLevel.DEBUG(5,new Throwable(),"order_agent_comments :"+order_agent_comments);
			String order_status=(String)ht.get("order_status");
			LogLevel.DEBUG(5,new Throwable(),"order_status :"+order_status);
			String order_reasons=(String)ht.get("order_reasons");
			LogLevel.DEBUG(5,new Throwable(),"order_reasons :"+order_reasons);
			String consumer_name=(String)ht.get("consumer_name");
			LogLevel.DEBUG(5,new Throwable(),"consumer_name :"+consumer_name);
			String consumer_emailid=(String)ht.get("consumer_emailid");
			LogLevel.DEBUG(5,new Throwable(),"consumer_emailid :"+consumer_emailid);
			String services_package=(String)ht.get("services_package");
			LogLevel.DEBUG(5,new Throwable(),"services_package :"+services_package);
			String order_number=(String)ht.get("order_number");
			LogLevel.DEBUG(5,new Throwable(),"order_number :"+order_number);
			String veh_name=(String)ht.get("veh_name");
			LogLevel.DEBUG(5,new Throwable(),"veh_name :"+veh_name);
			String veh_model=(String)ht.get("veh_model");
			LogLevel.DEBUG(5,new Throwable(),"veh_model :"+veh_model);
			String services_type=(String)ht.get("services_type");
			LogLevel.DEBUG(5,new Throwable(),"services_type :"+services_type);
			String consumer_mobnumber=(String)ht.get("consumer_mobnumber");
			LogLevel.DEBUG(5,new Throwable(),"consumer_mobnumber :"+consumer_mobnumber);
			String retailer_name=(String)ht.get("retailer_name");
			LogLevel.DEBUG(5,new Throwable(),"retailer_name :"+retailer_name);
			String retailer_mobilnumber=(String)ht.get("retailer_mobilnumber");
			LogLevel.DEBUG(5,new Throwable(),"retailer_mobilnumber :"+retailer_mobilnumber);
			String retailer_emailid=(String)ht.get("retailer_emailid");
			LogLevel.DEBUG(5,new Throwable(),"retailer_emailid :"+retailer_emailid);
			String city=(String)ht.get("city");
			LogLevel.DEBUG(5,new Throwable(),"city :"+city);		
			String batterywale_order=(String)ht.get("batterywale_order");
			LogLevel.DEBUG(5,new Throwable(),"batterywale_order :"+batterywale_order);	
			String product_type=(String)ht.get("product_type");
			LogLevel.DEBUG(5,new Throwable(),"product_type :"+product_type);		
			String quantity=(String)ht.get("quantity");
			LogLevel.DEBUG(5,new Throwable(),"quantity :"+quantity);			
            String services_place=(String)ht.get("services_place");
			LogLevel.DEBUG(5,new Throwable(),"services_place :"+services_place);            
            String service_price_discount=(String)ht.get("service_price_discount");
			LogLevel.DEBUG(5,new Throwable(),"service_price_discount :"+service_price_discount);
			
			
			String first_contacted_date=(String)ht.get("first_contacted_date");
			LogLevel.DEBUG(5,new Throwable(),"first_contacted_date :"+first_contacted_date);
			
			String Website_Name="BookBattery";
			String Website_URL="www.bookbattery.com";
			
			if(batterywale_order.equals("Yes"))
			{
				 Website_Name="BookBattery";
				 Website_URL="www.bookbattery.com";
			}
			else
			{
				 Website_Name="BookBattery";
				 Website_URL="www.bookbattery.com";
			}
			
			
			/**Code added by Bharath to insert first contacted date starts here**/
			
			if(first_contacted_date.equals("0000-00-00 00:00:00"))
			{
				LogLevel.DEBUG(5,new Throwable(),"first_contacted_date if:"+first_contacted_date);

				String Strinsertfirs_contact_dateQry="update service_order_details set first_contacted_date=now() where order_number='"+ordernumber+"'";
				int Strinsertfirs_contact_dateQryreslt = qm.executeUpdate(Strinsertfirs_contact_dateQry);
				
				LogLevel.DEBUG(5,new Throwable(),"Strinsertfirs_contact_dateQryreslt:"+Strinsertfirs_contact_dateQryreslt);
			}
			else
			{
				LogLevel.DEBUG(5,new Throwable(),"first_contacted_date else:"+first_contacted_date);
			}
			
			/**Code added by Bharath to insert first contacted date ends here**/
			
			if(agentcomments.equals(""))
			{
				if(order_agent_comments == null || order_agent_comments.equals("") || order_agent_comments.equals("null") || order_agent_comments.equals(null))
				{
					order_agent_comments="";
				}
				else
				{
					order_agent_comments=order_agent_comments;

				}

			}
			else
			{
				if(order_agent_comments == null || order_agent_comments.equals("") || order_agent_comments.equals("null") || order_agent_comments.equals(null))
				{
					order_agent_comments=agentcomments+"-"+stroperatorName;
				}
				else
				{
					order_agent_comments=order_agent_comments+","+agentcomments+"-"+stroperatorName;
				}
			}


				order_agent_comments = order_agent_comments.replace("\\","\\\\"); order_agent_comments= order_agent_comments.replace("'","\\'"); order_agent_comments= order_agent_comments.replace("<","<"); order_agent_comments= order_agent_comments.replace("+","+"); order_agent_comments= order_agent_comments.replace("%u201C","&ldquo;"); order_agent_comments= order_agent_comments.replace("%u201D","&rdquo;"); order_agent_comments= order_agent_comments.replace("%20"," "); order_agent_comments= order_agent_comments.replace("%22","&#34;"); 
				order_agent_comments= order_agent_comments.replace("%27","\\'"); order_agent_comments= order_agent_comments.replace("%7E","~"); order_agent_comments= order_agent_comments.replace("%21","!"); order_agent_comments= order_agent_comments.replace("%23","#"); order_agent_comments= order_agent_comments.replace("%24","$"); order_agent_comments= order_agent_comments.replace("%25","%"); order_agent_comments= order_agent_comments.replace("%5E","^"); order_agent_comments= order_agent_comments.replace("%26","&");
				order_agent_comments= order_agent_comments.replace("%28","("); order_agent_comments= order_agent_comments.replace("%29",")"); order_agent_comments= order_agent_comments.replace("%7C","|"); order_agent_comments= order_agent_comments.replace("%7D","{"); order_agent_comments= order_agent_comments.replace("%7B","}"); order_agent_comments= order_agent_comments.replace("%3A",":"); order_agent_comments= order_agent_comments.replace("%3F","?"); order_agent_comments= order_agent_comments.replace("%3E",">");
				order_agent_comments= order_agent_comments.replace("%3C","<"); order_agent_comments= order_agent_comments.replace("%60","`"); order_agent_comments= order_agent_comments.replace("%3D","="); order_agent_comments= order_agent_comments.replace("%5C","\\\\"); order_agent_comments= order_agent_comments.replace("%5D","]"); order_agent_comments= order_agent_comments.replace("%5B","[");
				order_agent_comments= order_agent_comments.replace("%2C",",");order_agent_comments= order_agent_comments.replace("%3B",";");order_agent_comments= order_agent_comments.replace("%0A","\n");

			String strSqlQry ="";
			if(orderstatus.equals("postponed") || orderstatus.equals("Postponed"))
			{
				strSqlQry= "update service_order_details set order_status='"+orderstatus+"',order_reasons='"+orderreason+"',order_agent_comments='"+order_agent_comments+"',agent_name='"+stroperatorName+"',postpone_date='"+strpostponedate+"',updated_date=now(),installed_date='0000-00-00 00:00:00' WHERE  order_number='"+ordernumber+"' and ord_id='"+orderid+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
			}
			
			
			else if(orderstatus.equals("Customer Contacted") && orderreason.equals("Not Confirmed Order - Customer Confirmed to Place Order"))
			{
				
				String ThankUSMSMsg="";
				String pw = "";
				String retailer_id="";
				String strretmobnum="";
				String strretname="";
				String strretemail="";
				String straddress1="";
				String strretothermobnum="";
				String strinvoiceflag="";
				String Strretid="";
				String Strlocorpin="";
				String StrSqlQry="";
				String ProductDiscountPrice="";

				Date now = new Date();
				SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely
				//System.out.println(simpleDateformat.format(now));
				LogLevel.DEBUG(5, new Throwable(), "Day Info :" + simpleDateformat.format(now));
				String day =simpleDateformat.format(now);
				LogLevel.DEBUG(5, new Throwable(), "Day Info String:" + day);
				
				if(day.equals("Sunday"))
				{

					String StrSqlQry12 = "select retailer_id from retailer_location_mapping where state='"+cusstate+"' and city='"+cuscity+"' and area='"+cusarea+"' and bat_brand='Exide' and weekend_dealer_flag='Yes' limit 1";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQry12 :" + StrSqlQry12);
					Hashtable htretailerid1 = qm.getRow(StrSqlQry12); 
					if(htretailerid1.isEmpty())
					{
						StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+cusstate+"' and city='"+cuscity+"' and area='"+cusarea+"' and bat_brand='Exide' and weekend_dealer_flag='No' limit 1";
						LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);

					}  
					else
					{

						StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+cusstate+"' and city='"+cuscity+"' and area='"+cusarea+"' and bat_brand='Exide' and weekend_dealer_flag='Yes' limit 1";
						LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
					}
				}
				else
				{
					StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+cusstate+"' and city='"+cuscity+"' and area='"+cusarea+"' and bat_brand='Exide' and weekend_dealer_flag='No' limit 1";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
				}

				Hashtable htretailerid = qm.getRow(StrSqlQry); 
				Strretid=String.valueOf(htretailerid.get("retailer_id"));
				LogLevel.DEBUG(5, new Throwable(), "Strretid :" + Strretid);
				Strlocorpin = cuscity;
				
				if(Strretid.equals(null) || Strretid.equals("null") || Strretid == null || Strretid == "null" || Strretid =="")
				{

						strRes="<div class='col-md-4 col-md-offset-4'>  <table border='0' width='300px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'>  <tr height='10'><table width='100%'  valign='top'></table><table  width='100%'  valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 25px;font-weight: 600;text-align: center;'> BookBattery</td> <td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: left;'  href=\"javascript:closemobdiv(greyout(false));\" > <a style='color: #F96F2B;' href=\"onclick='closemobdivindex1();'\");\"> X </a> </td></tr></table><hr style='background: white; width: 90%;margin-top: 1px;margin-bottom: 1px;'></tr> </table><table border='0' style='margin-top: 10px;' width='100%' height='2px'  valign'top'><tr ><td align='justify'  style='font-family:Verdana;font-size:14px;color:#FFFFFF;padding: 16px;'>No Retailers Found on Selected City.</td></tr><tr height='10'><td></td></tr><tr height='10'><td align='center'> <input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn' name='Submitrret' value='Submit' disable='false' onclick='closemobdivindex1();' > <br></td></tr><tr><td height='10'></td></tr></table> </div>";
				}
				else
				{

				String cusstate1=cusstate.trim().replaceAll(" ", "_"); 
				LogLevel.DEBUG(5, new Throwable(), "cusstate1 :" + cusstate1);

				String StrSqlQrydet = "select retailer_id,retailer_name,mobile_number,email_id,address1,mobile_numberother,invoice_flag from "+cusstate1+"_retailers where retailer_id='"+Strretid+"'";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQrydet :" + StrSqlQrydet);

				Hashtable htretailerdetls = qm.getRow(StrSqlQrydet); 
				retailer_id=String.valueOf(htretailerdetls.get("retailer_id"));
				strretmobnum=String.valueOf(htretailerdetls.get("mobile_number"));
				strretname=(String)htretailerdetls.get("retailer_name");
				strretemail=(String)htretailerdetls.get("email_id");
				straddress1=(String)htretailerdetls.get("address1");
				strretothermobnum=String.valueOf(htretailerdetls.get("mobile_numberother"));
				strinvoiceflag=String.valueOf(htretailerdetls.get("invoice_flag"));
				
				if(retailer_id.equals(null) || retailer_id.equals("null") || retailer_id == null || retailer_id == "null" || retailer_id =="")
				{
				strRes="<table border='0' width='300px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#562d82'>BookBattery</td><td align='right'><a href='javascript:closemobdivindex1(greyout(false));'><img src=\"./images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='300px' height='2px' bgcolor='#FFFFFF' valign'top'><tr class='pages' bgcolor='#FFFFFF'><td align='center' bgcolor='#FFFFFF'>Session Expired or Server down. please regenerate your order.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closemobdivindex1();' class='button4'><br></td></tr><tr height='15'></tr></table>";
				}
				else
				{
	                
				String verificationcode = "";
				verificationcode =ordernumber;
				LogLevel.DEBUG(5, new Throwable(), "verificationcode :" + verificationcode);
				
				String Get_Product_Price_SQL ="select "+serviceplace+" as service_price,store from service_prices where service_type='"+servicetype+"' and battery_type='"+battype+"'";
	            
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
					ProductDiscountPrice=(String)Get_Product_Price_SQL_HT.get("service_price");
					LogLevel.DEBUG(5, new Throwable(), "ProductDiscountPrice :" + ProductDiscountPrice);
					
					String store=(String)Get_Product_Price_SQL_HT.get("store");
					LogLevel.DEBUG(5, new Throwable(), "store :" + store);
					LogLevel.DEBUG(5, new Throwable(), "quantity :" + quantity);

					if(serviceplace.equals("store"))
					{
					
						 ProductDiscountPrice=String.valueOf(Integer.parseInt(ProductDiscountPrice)*Integer.parseInt(quantity));
					}
					else
					{
						 ProductDiscountPrice=String.valueOf(Integer.parseInt(ProductDiscountPrice)+(Integer.parseInt(store)*(Integer.parseInt(quantity)-1)));
					}
					
					
	                String ProductActualPrice="400";
	                
	                LogLevel.DEBUG(5, new Throwable(), "ProductDiscountPrice * Quantity :" + ProductDiscountPrice);
	                
	               if(servicetype.equals("Health Check"))
	               {
	                  ProductActualPrice="700";
	               }
	               else
	               {
	                   ProductActualPrice=ProductActualPrice;
	               }

					String strSqlQry_Update = "UPDATE service_order_details SET service_price_mrp = '"+ProductActualPrice+"', service_price_discount = '"+ProductDiscountPrice+"', consumer_address1='"+User_Address_Landmark+"' WHERE  order_number='"+verificationcode+"' ";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry_Update:"+strSqlQry_Update );
					
					int reslt_Update = qm.executeUpdate(strSqlQry_Update);
					LogLevel.DEBUG(5, new Throwable(), "reslt_Update :" + reslt_Update);
				}
				
				
				
				/*following code is for generating the random number */
				String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";

				
				for (int i=0; i<PASSWORD_LENGTH; i++)
				{
					int index = (int)(RANDOM.nextDouble()*letters.length());
					pw += letters.substring(index, index+1);
				}
				LogLevel.DEBUG(5, new Throwable(), "pw :" + pw);
				//String ThankUSMSMsg = "";
				/*code to generate the random number ends here */
				/* code to send SMS and Email retailers details to consumer */ 
				CompareTrans ct = new CompareTrans(qm);
				//SendSMS sendsms = new SendSMS(qm);

				/* code to send SMS and Email consumer details to retailers */ 

				String CustomerAddress="";

				if(cusaddr1.equals("") && cusaddr2.equals(""))
				{
					CustomerAddress="";

				}
				else
				{
					CustomerAddress=", Address: "+cusaddr1+","+cusaddr2+"";

				}

				strSqlQry= "update service_order_details set order_status='"+orderstatus+"',order_reasons='"+orderreason+"',order_agent_comments='"+order_agent_comments+"',agent_name='"+stroperatorName+"',services_type='"+servicetype+"',product_type='"+battype+"',services_place='"+serviceplace+"',service_price_discount='"+ProductDiscountPrice+"',consumer_name='"+cusname+"',consumer_emailid='"+cusemailid+"',retailer_name='"+strretname+"',retailer_mobilnumber='"+strretmobnum+"',retailer_emailid='"+strretemail+"',state='"+cusstate+"',city='"+cuscity+"',area='"+cusarea+"',quantity='"+Quantity+"',updated_date=now(),installed_date='0000-00-00 00:00:00',payment_mode='Cash', payment_mode_type='Cash On Delivery',confirm_by='Operator' WHERE  order_number='"+ordernumber+"' and ord_id='"+orderid+"'";
	            
				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
				int reslt = qm.executeUpdate(strSqlQry);	
				

				//######## Send SMS for ORDER
				Order_Service_SMS Send_Order_SMS_Checkout = new Order_Service_SMS(qm);
				String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,verificationcode,"Yes","No","Yes");
				LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
				//######## Send SMS for ORDER
				
				//######## Send Mail for ORDER
				Order_Service_SMS Send_Order_MAIL_Checkout = new Order_Service_SMS(qm);
				String MAIL_Report=Send_Order_MAIL_Checkout.Send_Order_Mail(req,res,session,verificationcode,"Yes","No","Yes");
				LogLevel.DEBUG(5, new Throwable(), "MAIL_Report :" + MAIL_Report);
				//######## Send Mail for ORDER
							
				String StrSqlQryuser = "select email_id from batterywale_user_profile where email_id='"+cusemailid+"'";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQryuser :" + StrSqlQryuser);

				Hashtable htruser = qm.getRow(StrSqlQryuser); 
				String Stremailid=String.valueOf(htruser.get("email_id"));
				LogLevel.DEBUG(5, new Throwable(), "Stremailid :" + Stremailid);

				if(Stremailid.equals(null) || Stremailid.equals("null") || Stremailid == null || Stremailid == "null" || Stremailid =="")
				{
					cusaddr1 = cusaddr1.replace("'","\\'");
					cusaddr2 = cusaddr2.replace("'","\\'");

					String strSqlQryuserprof = "insert into batterywale_user_profile(user_id,email_id,mobile_number,password,name,address,address1,zipcode,city,state,creation_date,created_by) values(NULL,'"+cusemailid+"','"+consumer_mobnumber+"','"+pw+"','"+cusname+"','"+cusaddr1+"','"+cusaddr2+"','','"+cuscity+"','"+cusstate+"',now(),'ngit')";

					LogLevel.DEBUG(5,new Throwable()," : "+strSqlQryuserprof);
					int reslt1 = qm.executeUpdate(strSqlQryuserprof);
				}
				else
				{
					String strSqlQryupdatepassword = "update batterywale_user_profile set password='"+pw+"' where email_id='"+cusemailid+"'";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQryupdatepassword : "+strSqlQryupdatepassword);
					int reslt12 = qm.executeUpdate(strSqlQryupdatepassword);
				}
				
				}
				}
				
			}
			else if(orderstatus.equals("Customer Contacted") && orderreason.equals("installed"))
			{
					
				
				String verificationcode = "";
				verificationcode =ordernumber;
				String ProductDiscountedPrice="";
				LogLevel.DEBUG(5, new Throwable(), "verificationcode :" + verificationcode);
				
				String Get_Product_Price_SQL ="select "+services_place+" as service_price,store from service_prices where service_type='"+services_type+"' and battery_type='"+product_type+"'";
	            
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
					ProductDiscountedPrice=(String)Get_Product_Price_SQL_HT.get("service_price");
					LogLevel.DEBUG(5, new Throwable(), "ProductActualPrice :" + ProductDiscountedPrice);
					
					String store=(String)Get_Product_Price_SQL_HT.get("store");
					LogLevel.DEBUG(5, new Throwable(), "store :" + store);
					
					if(serviceplace.equals("store"))
					{
					
						ProductDiscountedPrice=String.valueOf(Integer.parseInt(ProductDiscountedPrice)*Integer.parseInt(quantity));
					}
					else
					{
						ProductDiscountedPrice=String.valueOf(Integer.parseInt(ProductDiscountedPrice)+(Integer.parseInt(store)*(Integer.parseInt(quantity)-1)));
					}
					
					
	                String ProductActualPrice="400";
	                
	                LogLevel.DEBUG(5, new Throwable(), "ProductDiscountPrice * Quantity :" + ProductDiscountedPrice);
	                
	               if(servicetype.equals("Health Check"))
	               {
	                  ProductActualPrice="700";
	               }
	               else
	               {
	                   ProductActualPrice=ProductActualPrice;
	               }
				
				
                
                if(services_place.equals("store"))
				{
					service_commission_amount=String.valueOf(Integer.parseInt(service_price_discount)*0.5);
				}				
                else
				{
					service_commission_amount=String.valueOf(Integer.parseInt(service_price_discount)*0.33);
				}
                
                	
                
				if(serviceplace.equals("store"))
				{
				
					customerpaid=ProductDiscountedPrice;
				}
				else
				{
					customerpaid=ProductDiscountedPrice;
				}
                
                
                if(payment_mode.equals("Online Payment After Fitment"))
				{

				String requestUrl="";
				String payment_link_url="";
				String CheckPaymentSql ="select order_id,status,payment_url from online_transaction_details where order_id='"+ordernumber+"'";
				
				LogLevel.DEBUG(5, new Throwable(), "CheckPaymentSql :" + CheckPaymentSql);	
				Hashtable CheckPaymentSql_HT = qm.getRow(CheckPaymentSql);
				LogLevel.DEBUG(5, new Throwable(), "CheckPaymentSql_HT :" + CheckPaymentSql_HT);

					if(CheckPaymentSql_HT.isEmpty())
					{
						LogLevel.DEBUG(5, new Throwable(), "Create_Payment_ Inside");
						
						//*********************################## Online Payment Request Generation Code ###### START #####################*****************//
						OnlinePayment Create_Payment_Link = new OnlinePayment(qm);
						LogLevel.DEBUG(5, new Throwable(), "Create_Payment_Link :" + Create_Payment_Link);
						String Create_Payment_Link_Resp=  Create_Payment_Link.getPaymentLink(req,res,session,consumer_name,consumer_emailid,consumer_mobnumber,customerpaid,ordernumber);
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
					
						String SuccessMessage ="Dear Customer, Please make your payment for the battery order with order number "+ordernumber+" in BookBattery by clicking on the link "+payment_link_url+" ."; 
						LogLevel.DEBUG(5,new Throwable(),"SuccessMessage:"+SuccessMessage);
						
						String strSMSResponse_PL=sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,SuccessMessage,consumer_mobnumber);
						LogLevel.DEBUG(5,new Throwable(),"strSMSResponse_PL:"+strSMSResponse_PL);
						

						strSqlQry= "update service_order_details set order_status='"+orderstatus+"',order_reasons='Payment Link Generated',payment_mode='"+payment_mode+"',order_agent_comments='"+order_agent_comments+"',agent_name='"+stroperatorName+"',collected_party='"+paymentcollected+"', updated_date=now(),installed_date=now(),postpone_date='0000-00-00', service_commission_amount='"+service_commission_amount+"' WHERE  order_number='"+ordernumber+"' and ord_id='"+orderid+"'";
						
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
						
					} 
					else 
					{
						strRes="Please Try again!- Order Values updated";
						LogLevel.DEBUG(5, new Throwable(), "GET request not worked");
					}
							 
					} 
					else
					{						
						String[] order_coupon_code_array=ordernumber.split("ORDI");
									 
						 String payment_orderid=(String)CheckPaymentSql_HT.get("order_id");
						 String payment_status=(String)CheckPaymentSql_HT.get("status");
						 requestUrl=(String)CheckPaymentSql_HT.get("payment_url");
						 LogLevel.DEBUG(5, new Throwable(), "payment_orderid" + payment_orderid);
						 LogLevel.DEBUG(5, new Throwable(), "payment_status" + payment_status);
						 LogLevel.DEBUG(5, new Throwable(), "payment_url" + requestUrl);
								
						 if(payment_status=="completed" || payment_status.equals("completed")){
							 
							LogLevel.DEBUG(5, new Throwable(), "Inside" + payment_status);
							
							strSqlQry= "update service_order_details set order_status='"+orderstatus+"',order_reasons='installed',payment_mode='Online',payment_mode_type='"+payment_mode+"',payment_installation='Online Payment After Fitment',order_agent_comments='"+order_agent_comments+"',agent_name='"+stroperatorName+"',collected_party='"+paymentcollected+"', updated_date=now(),installed_date=now(),postpone_date='0000-00-00', service_commission_amount='"+service_commission_amount+"' WHERE  order_number='"+ordernumber+"' and ord_id='"+orderid+"'";
							LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
								
						 } 
						 else 
						 {
							LogLevel.DEBUG(5, new Throwable(), "Outside" + payment_status);
							
							strSqlQry= "update service_order_details set order_status='"+orderstatus+"',order_reasons='Payment Pending',payment_mode='"+payment_mode+"',order_agent_comments='"+order_agent_comments+"',agent_name='"+stroperatorName+"',collected_party='"+paymentcollected+"', updated_date=now(),installed_date=now(),postpone_date='0000-00-00', service_commission_amount='"+service_commission_amount+"' WHERE  order_number='"+ordernumber+"' and ord_id='"+orderid+"'";
							LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );

						 }
						 
					}
				}//********************* Payment Query HT ends***********/
			
                else
                {
                
				strSqlQry= "update service_order_details set order_status='"+orderstatus+"',order_reasons='"+orderreason+"',order_agent_comments='"+order_agent_comments+"',agent_name='"+stroperatorName+"',collected_party='"+paymentcollected+"', updated_date=now(),installed_date=now(),postpone_date='0000-00-00', service_commission_amount='"+service_commission_amount+"' WHERE  order_number='"+ordernumber+"' and ord_id='"+orderid+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
				
                }
			}
		}
			
			else if(orderstatus.equals("confirmed") && orderreason.equals("Confirmed Order To Retailer"))
			{
				
					String ThankUSMSMsg="";
					
					String[] retailertoorderarray=retailertoorder.split(",");

					String retailerid=retailertoorderarray[0];
					String retailername=retailertoorderarray[1];
					String retailerstate=retailertoorderarray[2];

					retailerstate=retailerstate.trim().replaceAll(" ", "_"); 
					LogLevel.DEBUG(5, new Throwable(), "retailerstate :" + retailerstate);

					String StrSqlQrydet = "select retailer_id,retailer_name,mobile_number,email_id,zipcode,address1,mobile_numberother,invoice_flag from "+retailerstate+"_retailers where retailer_id='"+retailerid+"'";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQrydet :" + StrSqlQrydet);

					Hashtable htretailerdetls = qm.getRow(StrSqlQrydet); 
					String retailer_id=String.valueOf(htretailerdetls.get("retailer_id"));
					String strretmobnum=String.valueOf(htretailerdetls.get("mobile_number"));
					String strretname=(String)htretailerdetls.get("retailer_name");
					String strretemail=(String)htretailerdetls.get("email_id");
					String straddress1=(String)htretailerdetls.get("address1");
					String strretothermobnum=String.valueOf(htretailerdetls.get("mobile_numberother"));
					String strinvoiceflag=String.valueOf(htretailerdetls.get("invoice_flag"));
                    String zipcode=String.valueOf(htretailerdetls.get("zipcode"));

					
					/*Code Added to assign the Service engineer for an order*/
					String strSqlserv_engnr_Qry="SELECT a.service_engineer_id,a.username,a.pincode,b.mobilenumber FROM service_engineer_mappings a, service_engineers b WHERE a.service_engineer_id=b.register_id and a.pincode='"+zipcode+"'";
					
					Hashtable ht_strSqlserv_engnr_Qry = qm.getRow(strSqlserv_engnr_Qry);
                    
                    String service_engineer_id="NULL";
                    String service_engineer_name="NULL";
                    String service_engineer_mobile="NULL";
                    String service_engineer_pincode="NULL";
                    if(ht_strSqlserv_engnr_Qry.isEmpty())
                    {
                        
                        
                    }
                    else
                    {
                         service_engineer_id=String.valueOf(ht_strSqlserv_engnr_Qry.get("service_engineer_id"));
                         service_engineer_name=String.valueOf(ht_strSqlserv_engnr_Qry.get("username"));
                         service_engineer_mobile=String.valueOf(ht_strSqlserv_engnr_Qry.get("mobilenumber"));
                         service_engineer_pincode=String.valueOf(ht_strSqlserv_engnr_Qry.get("pincode"));
                         LogLevel.DEBUG(5,new Throwable(),"service_engineer_id :"+service_engineer_id);  
                    }
                    
                    
					strSqlQry= "update service_order_details set order_status='"+orderstatus+"',order_reasons='"+orderreason+"',order_agent_comments='"+order_agent_comments+"',agent_name='"+stroperatorName+"',updated_date=now(),retailer_name='"+retailername+"',retailer_mobilnumber='"+strretmobnum+"',service_engineer_name='"+service_engineer_name+"',service_engineer_pincode='"+service_engineer_pincode+"',service_engineer_mobile='"+service_engineer_mobile+"',retailer_emailid='"+strretemail+"',postpone_date='0000-00-00',area='"+cusarea+"',confirm_by='Operator' WHERE  order_number='"+ordernumber+"' and ord_id='"+orderid+"'";
                   
										
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry_Update:"+strSqlQry);
					reslt_new = qm.executeUpdate(strSqlQry);
					
					if(reslt_new <0)
					{
						out.println("Failed to update ordered status!");
						strRes="Failed to update ordered status!";
					}
					else
					{
						//######## Send SMS for ORDER
						Order_Service_SMS Send_Order_SMS_Checkout = new Order_Service_SMS(qm);
						String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,ordernumber,"Confirm","Yes","Yes");
						
						
						LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
						
						//Order_SMS Send_Order_SMS_Checkout_confirm = new Order_SMS(qm);
						//String SMS_Report_confirm=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,ordernumber,"Battery","Confirm","No","No");
						
						//LogLevel.DEBUG(5, new Throwable(), "SMS_Report_confirm :" + SMS_Report_confirm);	

						LogLevel.DEBUG(5, new Throwable(), "SMS_Report Newwwwww :" + SMS_Report);
						//######## Send SMS for ORDER
						
						//######## Send Mail for ORDER
						Order_Service_SMS Send_Order_MAIL_Checkout = new Order_Service_SMS(qm);
						String MAIL_Report=Send_Order_MAIL_Checkout.Send_Order_Mail(req,res,session,ordernumber,"Yes","Yes","Yes");
						
						LogLevel.DEBUG(5, new Throwable(), "MAIL_Report :" + MAIL_Report);
						//######## Send Mail for ORDER
					}
			}
			else
			{

				strSqlQry= "update service_order_details set order_status='"+orderstatus+"',order_reasons='"+orderreason+"',order_agent_comments='"+order_agent_comments+"',agent_name='"+stroperatorName+"',updated_date=now(),installed_date='0000-00-00 00:00:00',postpone_date='0000-00-00' WHERE  order_number='"+ordernumber+"' and ord_id='"+orderid+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
			}
			
			int i=qm.executeUpdate(strSqlQry);
			if(i <0)
			{
				LogLevel.DEBUG(1,new Throwable(),"No Name exists u can continue ");
				session.setAttribute("priority","1");
				session.setAttribute("sesErrorMsg", "<font color='#00dd00' class='vrb10'></font> ");
				out.println("Failed to update ordered status!");
			}
			else
			{

			if(orderstatus.equals("Customer Contacted") && orderreason.equals("installed"))
			{

			String Get_Cust_Details_SQL = "select consumer_name,consumer_mobnumber,consumer_emailid,services_type,city,batterywale_order,quantity,product_type,product_capacity from service_order_details  WHERE  order_number='"+ordernumber+"' and ord_id='"+orderid+"'";
			LogLevel.DEBUG(5,new Throwable(),"Get_Cust_Details_SQL:"+Get_Cust_Details_SQL );			
			Hashtable Get_Cust_Details_HT = qm.getRow(Get_Cust_Details_SQL);
			String cust_name=(String)Get_Cust_Details_HT.get("consumer_name");
			LogLevel.DEBUG(5,new Throwable(),"cust_name:"+cust_name );
			String cust_mobile_number=(String)Get_Cust_Details_HT.get("consumer_mobnumber");
			LogLevel.DEBUG(5,new Throwable(),"cust_mobile_number:"+cust_mobile_number );
			String cust_emailid=(String)Get_Cust_Details_HT.get("consumer_emailid");
			LogLevel.DEBUG(5,new Throwable(),"cust_emailid:"+cust_emailid );
			String cust_bat_type=(String)Get_Cust_Details_HT.get("services_type");
			LogLevel.DEBUG(5,new Throwable(),"cust_bat_type:"+cust_bat_type );
			String cust_city=(String)Get_Cust_Details_HT.get("city");	
			LogLevel.DEBUG(5,new Throwable(),"cust_city:"+cust_city);	
			String product_capacity=(String)Get_Cust_Details_HT.get("product_capacity");	
			LogLevel.DEBUG(5,new Throwable(),"product_capacity:"+product_capacity);
				//String serverURL="192.168.1.7";	
				String satisfy_flag;	
				String DomainCusName;	

				/** Code starts here to send SMS to Consumer **/
				String DomainName="BookBattery.com";
				String ThankUSMSMsg2="";

			   if( batterywale_order.equals("Yes") || batterywale_order.equals("YES")|| batterywale_order.equals("yes"))
				{
					DomainName="BookBattery";
					LogLevel.DEBUG(5, new Throwable(), "DomainName"+DomainName);
					googleshorturl="https://goo.gl/RnpFVA";

				}
				else
				{
					DomainName="BookBattery";
					LogLevel.DEBUG(5, new Throwable(), "DomainName"+DomainName);
				}

				if(rating.equals("Satisfied"))
				{										
				String Get_order_id_SQL ="select DISTINCT order_id from customer_rating_details where order_id='"+ordernumber+"'"; 
				LogLevel.DEBUG(5, new Throwable(), "Get_order_id_SQL :" + Get_order_id_SQL);

				Hashtable Get_order_id_SQL_HT = qm.getRow(Get_order_id_SQL);
				LogLevel.DEBUG(5, new Throwable(), "Get_order_id_SQL_HT :" + Get_order_id_SQL_HT);

				if(Get_order_id_SQL_HT.isEmpty())
				{
				String SteSqlQry = "insert into customer_rating_details(s_id,name,order_id,email,mobile_number,city,batterytype,rating_comments,no_of_rating,sms_link_url,short_url,creation_date,updated_date,sent_flag,upd_comm_flag,satisfy_flag) values(NULL,'"+cust_name+"','"+ordernumber+"','"+cust_emailid+"','"+cust_mobile_number+"','"+cust_city+"','"+cust_bat_type+"','','','"+SMSURL+"','"+shorturl+"',now(),now(),'No','No','Yes')";	

				LogLevel.DEBUG(5,new Throwable(),"SteSqlQry:"+SteSqlQry );

				int reslt = qm.executeUpdate(SteSqlQry);

				if(reslt <0)
				{
				LogLevel.DEBUG(1,new Throwable(),"Failed to update your details! ");
				}
				else
				{
				ThankUSMSMsg2 ="Your Order with "+DomainName+" , Ref No: "+ordernumber+" has been successfully installed. Please provide your valuable ratings by clicking "+googleshorturl+" "; 

				//Your Order with XXXX , Ref No: XXXX has been successfully installed. Please provide your valuable ratings by clicking XXXX 

				// ThankUSMSMsg2 ="Your Order with "+DomainName+" , Ref No: "+ordernumber+" has been Cancelled. For any kind of Assistance please call to "+DomainMobNumber+" .";
				LogLevel.DEBUG(5,new Throwable(),"Satisfied ThankUSMSMsg2:"+ThankUSMSMsg2 );
				}

				}
				else
				{
				ThankUSMSMsg2 ="Your Order with "+DomainName+" , Ref No: "+ordernumber+" has been successfully installed. Please provide your valuable ratings by clicking "+googleshorturl+" "; 

				LogLevel.DEBUG(5,new Throwable(),"Not inserted already exists");

				}

				//LogLevel.DEBUG(5,new Throwable(),"Not Satisfied ThankUSMSMsg2:"+ThankUSMSMsg2 );
				LogLevel.DEBUG(5,new Throwable(),"strIpAddress:"+strIpAddress );
				LogLevel.DEBUG(5,new Throwable(),"strPort:"+strPort );
				LogLevel.DEBUG(5,new Throwable(),"SMSFromAddress:"+SMSFromAddress );
				LogLevel.DEBUG(5,new Throwable(),"consumer_mobnumber:"+consumer_mobnumber );

				String strSMSResponse3=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUSMSMsg2, consumer_mobnumber);
				LogLevel.DEBUG(5,new Throwable(),"strSMSResponse3:"+strSMSResponse3 );
				//ThankUSMSMsg2="Your ordered battery on "+battery_brand+" "+battery_model+" with order number "+order_number+" has been successfully installed by "+retailer_name+".";	
				LogLevel.DEBUG(5,new Throwable(),"Not Satisfied ThankUSMSMsg2:"+ThankUSMSMsg2 );

				}
				else
				{
					/*Condition Starts here for not satisfied Customer*/

					String Get_order_id_SQL ="select DISTINCT order_id from customer_rating_details where order_id='"+ordernumber+"'"; 
					LogLevel.DEBUG(5, new Throwable(), "Get_order_id_SQL :" + Get_order_id_SQL);

					Hashtable Get_order_id_SQL_HT = qm.getRow(Get_order_id_SQL);
					LogLevel.DEBUG(5, new Throwable(), "Get_order_id_SQL_HT :" + Get_order_id_SQL_HT);

					if(Get_order_id_SQL_HT.isEmpty())
					{
					String SteSqlQry = "insert into customer_rating_details(s_id,name,order_id,email,mobile_number,city,batterytype,rating_comments,no_of_rating,sms_link_url,short_url,creation_date,updated_date,sent_flag,upd_comm_flag,satisfy_flag) values(NULL,'"+cust_name+"','"+ordernumber+"','"+cust_emailid+"','"+cust_mobile_number+"','"+cust_city+"','"+cust_bat_type+"','','','"+SMSURL+"','"+shorturl+"',now(),now(),'No','No','No')";	

					LogLevel.DEBUG(5,new Throwable(),"SteSqlQry:"+SteSqlQry );

					int reslt = qm.executeUpdate(SteSqlQry);

					if(reslt <0)
					{
					LogLevel.DEBUG(1,new Throwable(),"Failed to update your details! ");
					}
					else
					{
					ThankUSMSMsg2 ="Your Order with "+DomainName+" , Ref No: "+ordernumber+" has been successfully installed."; 
					LogLevel.DEBUG(5, new Throwable(), "ThankUSMSMsg2 :" + ThankUSMSMsg2);

					//Your Order with XXXX , Ref No: XXXX has been successfully installed. Please provide your valuable ratings by clicking XXXX 

					// ThankUSMSMsg2 ="Your Order with "+DomainName+" , Ref No: "+ordernumber+" has been Cancelled. For any kind of Assistance please call to "+DomainMobNumber+" .";
					LogLevel.DEBUG(5,new Throwable(),"Satisfied ThankUSMSMsg2:"+ThankUSMSMsg2 );
					}

					}
					else
					{
					ThankUSMSMsg2 ="Your Order with "+DomainName+" , Ref No: "+ordernumber+" has been successfully installed."; 
					LogLevel.DEBUG(5, new Throwable(), "ThankUSMSMsg2 :" + ThankUSMSMsg2);
					LogLevel.DEBUG(5,new Throwable(),"Not inserted already exists");

					}

					//LogLevel.DEBUG(5,new Throwable(),"Not Satisfied ThankUSMSMsg2:"+ThankUSMSMsg2 );
					LogLevel.DEBUG(5,new Throwable(),"strIpAddress:"+strIpAddress );
					LogLevel.DEBUG(5,new Throwable(),"strPort:"+strPort );
					LogLevel.DEBUG(5,new Throwable(),"SMSFromAddress:"+SMSFromAddress );
					LogLevel.DEBUG(5,new Throwable(),"consumer_mobnumber:"+consumer_mobnumber );

					String strSMSResponse3=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUSMSMsg2, consumer_mobnumber);
					LogLevel.DEBUG(5,new Throwable(),"strSMSResponse3:"+strSMSResponse3 );
					//ThankUSMSMsg2="Your ordered battery on "+battery_brand+" "+battery_model+" with order number "+order_number+" has been successfully installed by "+retailer_name+".";	
					LogLevel.DEBUG(5,new Throwable(),"Not Satisfied ThankUSMSMsg2:"+ThankUSMSMsg2 );

					}

					/** Code starts here to send SMS to Consumer **/

					/** Code starts here to send Email to Consumer **/

					String CustomerMessage;
					
					
					if(product_type.equals("Car Batteries"))
					{
						CustomerMessage="Your order for "+services_type+"  for "+veh_name+" "+veh_model+" with order number "+order_number+" has been successfully installed by "+retailer_name+".";
					}
					else
					{
						CustomerMessage="Your order for "+services_type+" with order number "+order_number+" for "+product_type+" with capacity "+product_capacity+" of quantity "+quantity+" has been successfully installed by "+retailer_name+".";
					}
					
					if(batterywale_order.equals("Yes"))
					{
						DomainCusName="BookBattery";
					}
					else
					{
						DomainCusName="BookBattery";
					}

					String strCustomerMessage="Dear "+consumer_name+",\r\n\r\n"+""+CustomerMessage+"";
					String strThanksMessage="Thanks & Regards,"+"\r\n"+""+DomainName+" Support Team."; 
					String strThanksMessageCus="Thanks & Regards,"+"\r\n"+""+DomainCusName+" Support Team."; 
					MailTrans consumer=new MailTrans();
					consumer.setStrSmtpHost(strdomainname);
					consumer.setStrFrom(FromEmailId);
					consumer.setStrTo(consumer_emailid);
					LogLevel.DEBUG(5, new Throwable(), "consumer_emailid :" + consumer_emailid);
					consumer.setStrSubject(""+DomainCusName+" Battery Health Check Ordered Status");
					String customerMail = strCustomerMessage+"\r\n\r\n"+strThanksMessageCus+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from "+DomainCusName+" is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. "+DomainCusName+" does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "customerMail :" + customerMail);
					consumer.setStrText(customerMail);
					Thread customerMailThread=new MailThread(consumer,"");
					customerMailThread.start();

					/** Code ends here to send Email to Consumer **/

					/** Code starts here to send Sms and Email to Franchisee **/
					String FranchiseMessage="";
					String FranchiseemailMessage="";

					/** jhansi started placing code to calculate commission amount **/
					//String strSqlQrycalculatecom = "select CAST(round(((bat_witbat_price))-(erp_pre_tax))/ 2 AS SIGNED) as grandtotal FROM batteryprice WHERE  bat_model='"+battery_model+"' and city='"+city+"'";
					
					String strSqlQrycalculatecom = "select CAST(round(service_commission_amount) AS SIGNED) as grandtotal FROM service_order_details where order_number='"+ordernumber+"'";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQrycalculatecom :"+strSqlQrycalculatecom);

					Hashtable htcalculate = qm.getRow(strSqlQrycalculatecom);
					String grandtotal=String.valueOf(htcalculate.get("grandtotal"));
					LogLevel.DEBUG(5,new Throwable(),"grandtotal :"+grandtotal);
					String strSMSResponse22 ="";
					/** jhansi ended placing code to calculate commission amount **/

					//FranchiseemailMessage="The ordered battery with order number "+order_number+" has been successfully installed. Order Details Customer Name- "+consumer_name+" , Customer Mobile Num- "+consumer_mobnumber+" , Battery Brand- "+battery_brand+" , Battery Model- "+battery_model+" , Vehicle Make- "+veh_name+" , Vehicle Model- "+veh_model+" .Please credit commission amount Rs. "+grandtotal+" in the company bank account with in 72hours.";
					
					
					
					FranchiseMessage="The ordered services with order number "+order_number+" has been successfully installed. Please credit commission amount Rs. "+grandtotal+" in the company bank account with in 72hours";
					
					//The ordered services with order number xxxx has been successfully installed. Please credit commission amount Rs. XXXX in the company bank account with in 72hours
					
					//FranchiseMessage="The ordered services with order number "+order_number+" has been successfully installed. Order Details Customer Name- "+consumer_name+", Customer Mobile Num- "+consumer_mobnumber+",Services Package- "+services_package+",  Vehicle Make- "+veh_name+", Vehicle Model- "+veh_model+"";
					
					if(product_type.equals("Car Batteries"))
					{
						FranchiseemailMessage="The ordered services with order number "+order_number+" has been successfully installed. Order Details Customer Name- "+consumer_name+", Customer Mobile Num- "+consumer_mobnumber+",Services Package- "+services_package+",  Vehicle Make- "+veh_name+", Vehicle Model- "+veh_model+" .Please credit commission amount Rs. "+grandtotal+" in the company bank account with in 72hours.";
					}
					else
					{
						FranchiseemailMessage="The ordered services with order number "+order_number+" has been successfully installed. Order Details Customer Name- "+consumer_name+", Customer Mobile Num- "+consumer_mobnumber+",Services Package- "+services_package+",  Quantity - "+quantity+", Inverter Capacity- "+product_capacity+" .Please credit commission amount Rs. "+grandtotal+" in the company bank account with in 72hours.";
					}
					
					if(batterywale_order.equals("Yes"))
					{
						DomainCusName="BookBattery";
					}
					else
					{
						DomainCusName="BookBattery";
					}
					


					strSMSResponse22 = sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,FranchiseMessage, retailer_mobilnumber);
					
					LogLevel.DEBUG(5, new Throwable(), "strSMSResponse22 :" + strSMSResponse22);
					
					String strFranchiseMessage="Dear "+retailer_name+",\r\n\r\n"+""+FranchiseemailMessage+"";
					MailTrans franchise=new MailTrans();
					franchise.setStrSmtpHost(strdomainname);
					franchise.setStrFrom(FromEmailId);
					franchise.setStrTo(retailer_emailid);
					LogLevel.DEBUG(5, new Throwable(), "retailer_emailid :" + retailer_emailid);
					String strThanksMessagedealer=""; 
					String franchiseMail ="";

					strThanksMessagedealer="Thanks & Regards,"+"\r\n"+"BookBattery Support Team.";
					
					franchise.setStrSubject("BookBattery Battery Ordered Status");
					franchiseMail = strFranchiseMessage+"\r\n\r\n"+strThanksMessage+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "franchiseMail :" + franchiseMail);

					franchise.setStrText(franchiseMail);
					Thread franchiseMailThread=new MailThread(franchise,"");
					franchiseMailThread.start();

					/** Code ends here to send Email to Franchisee **/


				   /** Code starts here to send Email to Operator **/

					String OperatorMessage;
				
					OperatorMessage="The ordered services with order number "+order_number+" has been successfully installed. Order Details Customer Name- "+consumer_name+", Customer Mobile Num- "+consumer_mobnumber+",Services Package- "+services_package+", Vehicle Make- "+veh_name+", Vehicle Model- "+veh_model+", Franchisee Name- "+retailer_name+", Franchisee Mobile Number- "+retailer_mobilnumber+"";
					
					String strOperatorMessage="Dear Operator,\r\n\r\n"+""+OperatorMessage+"";
					MailTrans operator=new MailTrans();
					operator.setStrSmtpHost(strdomainname);
					operator.setStrFrom(FromEmailId);
					operator.setStrTo(strsuppemaild);
					LogLevel.DEBUG(5, new Throwable(), "strsuppemaild :" + strsuppemaild);
					operator.setStrSubject("BookBattery Service Ordered Status");
					String operatorMail = strOperatorMessage+"\r\n\r\n"+strThanksMessage+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "operatorMail :" + operatorMail);
					operator.setStrText(operatorMail);
					Thread operatorMailThread=new MailThread(operator,"");
					operatorMailThread.start();

				  /** Code ends here to send Email to Operator **/

			}

			if(orderstatus.equals("Customer Contacted") && orderreason.equals("cancelled-franchisee-notresponded")||orderreason.equals("cancelled-franchisee-modeloutofstock")||orderreason.equals("cancelled-franchisee-offbushrs")||orderreason.equals("cancelled-franchisee-denied")||orderreason.equals("cancelled-customer") ||orderreason.equals("cancelled-regenerated"))
			{

					String CustomerMessage;
					String CancelledCustomerMesg="";
					String CancelledOperatorMesg="";
					if(orderreason.equals("cancelled-franchisee-notresponded"))
					{
						CancelledOperatorMesg="due to franchisee has not responsed";
						CancelledCustomerMesg="due to your ordered model is out of stock";
					}
					else if(orderreason.equals("cancelled-franchisee-modeloutofstock"))
					{
						CancelledOperatorMesg="due to your ordered model is out of stock";
						CancelledCustomerMesg="due to your ordered model is out of stock";
					}
					else if(orderreason.equals("cancelled-franchisee-offbushrs"))
					{
						CancelledOperatorMesg="due to off business hours for franchisee";
						CancelledCustomerMesg="due to off business hours for franchisee";
					}
					else if(orderreason.equals("cancelled-franchisee-denied"))
					{
						CancelledOperatorMesg="due to our franchisee has denied";
						CancelledCustomerMesg="due to your ordered model is out of stock";
					}
					else if(orderreason.equals("cancelled-customer"))
					{
						CancelledOperatorMesg="by customer";
						CancelledCustomerMesg="due to "+agentcomments+"";
					}
					else
					{

					}

						CustomerMessage="Your ordered Service on "+services_package+" for "+veh_name+" "+veh_model+" with order number "+order_number+" has been cancelled "+CancelledCustomerMesg+".\r\n\r\n We are looking forward to see you again at "+Website_URL+" .";
					
					
					/** Code starts here to send Email to Customer if order has been cancelled **/
					String strCustomerMessage="Dear "+consumer_name+",\r\n\r\n"+""+CustomerMessage+"";
					String strThanksMessage="Thanks & Regards,"+"\r\n"+""+Website_Name+" Support Team."; 
					MailTrans consumer=new MailTrans();
					consumer.setStrSmtpHost(strdomainname);
					consumer.setStrFrom(FromEmailId);
					consumer.setStrTo(consumer_emailid);
					LogLevel.DEBUG(5, new Throwable(), "consumer_emailid :" + consumer_emailid);
					consumer.setStrSubject(""+Website_Name+" Service Ordered Status");
					String customerMail = strCustomerMessage+"\r\n\r\n"+strThanksMessage+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from "+Website_Name+" is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. "+Website_Name+" does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "customerMail :" + customerMail);
					consumer.setStrText(customerMail);
					Thread customerMailThread=new MailThread(consumer,"");
					customerMailThread.start();

					/** Code ends here to send Email to Customer if order has been cancelled **/

					/** Code starts here to send Email to Operator if order has been cancelled **/
					String OperatorMessage;
					
					OperatorMessage="The ordered Service with order number "+order_number+" has been cancelled "+CancelledOperatorMesg+". Order Details Customer Name- "+consumer_name+", Customer Mobile Num- "+consumer_mobnumber+", Service Brand- "+services_package+", Vehicle Make- "+veh_name+", Vehicle Model- "+veh_model+", Franchisee Name- "+retailer_name+"";
					
					String strOperatorMessage="Dear Operator,\r\n\r\n"+""+OperatorMessage+"";
					MailTrans operator=new MailTrans();
					operator.setStrSmtpHost(strdomainname);
					operator.setStrFrom(FromEmailId);
					operator.setStrTo(strsuppemaild);
					LogLevel.DEBUG(5, new Throwable(), "strsuppemaild :" + strsuppemaild);
					operator.setStrSubject(""+Website_Name+" Service Ordered Status");
					String operatorMail = strOperatorMessage+"\r\n\r\n"+strThanksMessage+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from "+Website_Name+" is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. "+Website_Name+" does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "operatorMail :" + operatorMail);
					operator.setStrText(operatorMail);
					Thread operatorMailThread=new MailThread(operator,"");
					operatorMailThread.start();

					/** Code ends here to send Email to Operator if order has been cancelled **/

			}


				LogLevel.DEBUG(1,new Throwable(),"");
				session.setAttribute("priority","1");
				session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'></font> ");
				out.println("Successfully Updated order Status as "+orderstatus+".!");
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
	* This method is to fetch service prices.
	* @strSqlQry : carries the query to battery brand from battery_details table.
	\* **************************************************************************************************************************************/
	public String getservicepricedetls(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String servicetype= (req.getParameter("servicetype")!=null)?(req.getParameter("servicetype")):"";
			LogLevel.DEBUG(5, new Throwable(), "servicetype :" + servicetype);

			String battype= (req.getParameter("battype")!=null)?(req.getParameter("battype")):"";
			LogLevel.DEBUG(5, new Throwable(), "battype :" + battype);

			String serviceplace= (req.getParameter("serviceplace")!=null)?(req.getParameter("serviceplace")):"";
			LogLevel.DEBUG(5, new Throwable(), "serviceplace :" + serviceplace);

	 		ServletOutputStream out=res.getOutputStream();

			String strSqlQry ="select "+serviceplace+" as serviceprice from service_prices where battery_type='"+battype+"' and service_type='"+servicetype+"'";
			
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Hashtable ht1 = qm.getRow(strSqlQry); 

			if(ht1.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:11px;color:#CCCCCC;' value='defaultss'>&nbsp;Select&nbsp;Inverter&nbsp;Model</option>");
				return strRes;
			}
			else
			{
				String serviceprice =String.valueOf(ht1.get("serviceprice"));
				strRes=""+serviceprice+"";
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
