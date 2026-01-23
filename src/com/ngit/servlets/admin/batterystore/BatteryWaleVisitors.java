/***********************************************************************		
	NGIT Confidential. 
	@File Name   : BatteryHome.java 
	@Description : This Servlet is used to select the Battery Details.
	@Author	     : Sai Krishna Daddala
	@Date        : 30th August 2013
******************************************************************/ 
 
package com.ngit.servlets.admin.batterystore; 
 
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.admin.mis.GenerateExcelbattery;
import com.ngit.javabean.sendsms.SendSMS;
import com.ngit.javabean.consumers.products.CompareTrans;

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

  
public class BatteryWaleVisitors extends HttpServlet 
{  
	private ServletContext context; 
	QueryManager qm;
	String baseURL;
 
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
		String struserName=(String)session.getAttribute("sesBatteryAdminName"); 
		LogLevel.DEBUG(5,new Throwable(),"struserName :"+struserName );

		String stroperatorName=(String)session.getAttribute("sesBatteryOperatorName"); 
		LogLevel.DEBUG(5,new Throwable(),"stroperatorName :"+stroperatorName );

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

		/* ****************************************************************************************\
		* This action is used to get battery brands.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("getvisitors"))
		{
			try
			{
				String strRes=getvisitors(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("getvisitorsoperator"))
		{
			try
			{
				String strRes=getvisitorsoperator(req,res,session,stroperatorName);
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
		if(strWhatToDo.equalsIgnoreCase("updatevisitorstatus"))
		{
			try
			{
				String strRes=updatevisitorstatus(req,res,session,strIpAddress,strPort,SMSFromAddress);
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
		if(strWhatToDo.equalsIgnoreCase("getvisitorsstate"))
		{
			try
			{
				String strRes=getvisitorsstate(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("getvisitorscity"))
		{
			try
			{
				String strRes=getvisitorscity(req,res,session);
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
* This method is to get with out ordered battery details details.
* @strSqlQry: carries the query to ordered battery details from battery_order_details table.
\* **************************************************************************************************************************************/
	public String getvisitors(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";

	Hashtable htOptions=new Hashtable();

	String strDateOpt  = (req.getParameter("dates")!=null)?req.getParameter("dates").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "strDateOpt :"+ strDateOpt);

	String txtFromDate  = (req.getParameter("txtFromDate")!=null)?req.getParameter("txtFromDate").trim():"";
	String txtToDate  = (req.getParameter("txtToDate")!=null)?req.getParameter("txtToDate").trim():"";
	String type  = (req.getParameter("type")!=null)?req.getParameter("type").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "type :"+ type);
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
		//txtToDate=curDate+"-"+curMonth+"-"+curYear;
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
	
	LogLevel.DEBUG(5, new Throwable(), "txtFromDate :"+ txtFromDate);
	htOptions.put("txtFromDate",txtFromDate);

	LogLevel.DEBUG(5, new Throwable(), "txtToDate :"+ txtToDate);
	htOptions.put("txtToDate",txtToDate);

	//LogLevel.DEBUG(5, new Throwable(), "city :"+ city);
	//htOptions.put("city",city);

	//LogLevel.DEBUG(5, new Throwable(), "orderstatus :"+ orderstatus);
	//htOptions.put("orderstatus",orderstatus);
	 
	try
	{	
	
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
		strConditions=" date(creation_date) between '"+strFromDate+"' and '"+strToDate+"'";
	else
		strConditions=strConditions+" and date(creation_date) between '"+strFromDate+"' and '"+strToDate+"'";
	
		//String strSqlQry ="select distinct emp_id,time_slot,description,activity_category,emp_name,date(creation_date),date_format(creation_date, '%W %M %D %Y'), count(time_slot) as count from mis where "+strConditions+" and emp_name='"+strempname+"' and emp_id='"+strempid+"' group by date(creation_date),time_slot,activity_category order by emp_name asc";
	
	String strSqlQry="select vis_ord_id,bat_type,veh_make,veh_model,bat_brand,bat_capacity,state,city,area,pincode,mobile_number,operator_flag,creation_date from visitors_orders where "+strConditions+" and option_type='"+type+"' and operator_flag='no' order by creation_date" ;
	LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

	Vector batterywalevisitorsVector=qm.executeQuery(strSqlQry);
	LogLevel.DEBUG(5,new Throwable(),"batterywalevisitorsVector:"+ batterywalevisitorsVector );

	String sqlrycount = "select count(*) as visitors from visitors_orders where "+strConditions+" and option_type='"+type+"' and operator_flag='no'";
	LogLevel.DEBUG(5, new Throwable(), "sqlrycount :" + sqlrycount);

	Vector visitorcountVector=qm.executeQuery(sqlrycount);
	LogLevel.DEBUG(5,new Throwable(),"visitorcountVector:"+ visitorcountVector );

	if(session.getAttribute("sesbatterywalevisitorsVectorVector")!=null)
	session.removeAttribute("sesbatterywalevisitorsVectorVector");
	session.setAttribute("sesbatterywalevisitorsVectorVector",batterywalevisitorsVector);
	session.setAttribute("sesvisitorcountVector",visitorcountVector);
	
	if(session.getAttribute("sesOptions")!=null)
	session.removeAttribute("sesOptions");
	session.setAttribute("sesOptions",htOptions);
	if(type.equals("inverter"))
	{
		res.sendRedirect("../jsp/admin/batterystore/visitors/invertervisitorssummary.jsp?txtFromDate="+txtFromDate+"&txtToDate="+txtToDate+"&dates="+strDateOpt+"&type="+type);
	}
	else if(type.equals("laptopbattery"))
	{
		res.sendRedirect("../jsp/admin/batterystore/visitors/laptopvisitorssummary.jsp?txtFromDate="+txtFromDate+"&txtToDate="+txtToDate+"&dates="+strDateOpt+"&type="+type);
	}
	else
	{
			res.sendRedirect("../jsp/admin/batterystore/visitors/visitorssummary.jsp?txtFromDate="+txtFromDate+"&txtToDate="+txtToDate+"&dates="+strDateOpt+"&type="+type);
	}
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
* This method is to get with out ordered battery details details.
* @strSqlQry: carries the query to ordered battery details from battery_order_details table.
\* **************************************************************************************************************************************/
	public String getvisitorsoperator(HttpServletRequest req , HttpServletResponse res,HttpSession session,String stroperatorName)
	{
	String strRes="";

	Hashtable htOptions=new Hashtable();

	String strDateOpt  = (req.getParameter("dates")!=null)?req.getParameter("dates").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "strDateOpt :"+ strDateOpt);

	String txtFromDate  = (req.getParameter("txtFromDate")!=null)?req.getParameter("txtFromDate").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "txtFromDate :"+ txtFromDate);

	String txtToDate  = (req.getParameter("txtToDate")!=null)?req.getParameter("txtToDate").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "txtToDate :"+ txtToDate);

	String type  = (req.getParameter("type")!=null)?req.getParameter("type").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "type :"+ type);

	String statusfilter  = (req.getParameter("statusfilter")!=null)?req.getParameter("statusfilter").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "statusfilter :"+ statusfilter);

	String batterytypefilter  = (req.getParameter("batterytypefilter")!=null)?req.getParameter("batterytypefilter").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "batterytypefilter :"+ batterytypefilter);

	String state  = (req.getParameter("state")!=null)?req.getParameter("state").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "state :"+ state);

	String city  = (req.getParameter("city")!=null)?req.getParameter("city").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "city :"+ city);

	String orderstatus="";
	String strSqlQry="";
	String sqlrycount ="";

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
		//txtToDate=curDate+"-"+curMonth+"-"+curYear;
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
	
	LogLevel.DEBUG(5, new Throwable(), "txtFromDate :"+ txtFromDate);
	htOptions.put("txtFromDate",txtFromDate);

	LogLevel.DEBUG(5, new Throwable(), "txtToDate :"+ txtToDate);
	htOptions.put("txtToDate",txtToDate);

	//LogLevel.DEBUG(5, new Throwable(), "city :"+ city);
	//htOptions.put("city",city);

	//LogLevel.DEBUG(5, new Throwable(), "orderstatus :"+ orderstatus);
	//htOptions.put("orderstatus",orderstatus);
	 
	try
	{	
	
	String strConditions="";
	String strConditions1="";
	String strConditions2="";
	String strOperatorConditions="";
	String strStateConditions="";
	String strCityConditions="";

	SimpleDateFormat sdfDate=new SimpleDateFormat("dd-MM-yyyy");
	Date fromDate=sdfDate.parse(txtFromDate); 
	Date toDate=sdfDate.parse(txtToDate); 

	SimpleDateFormat sdfString=new SimpleDateFormat("yyyy-MM-dd");
	String strFromDate=sdfString.format(fromDate); 
	LogLevel.DEBUG(5, new Throwable(), "strFromDate :"+ strFromDate);

	String strToDate=sdfString.format(toDate); 
	LogLevel.DEBUG(5, new Throwable(), "strToDate :"+ strToDate);
	if(stroperatorName.equals("operator") )
	{
		strOperatorConditions="";
	}
	else
	{
		strOperatorConditions=" and agent_name='"+stroperatorName+"'";
	}


	if(statusfilter.equals("") || statusfilter.equals("Select Status"))
	{
		strConditions1="";
	}
	else
	{
		strConditions1=" and visitor_status='"+statusfilter+"'";
	}

	if(batterytypefilter.equals("") || batterytypefilter.equals("Select Battery Type") || batterytypefilter.equals("All"))
	{
		strConditions2="";
	}
	else
	{
		if(batterytypefilter.equals("Inverter Batteries"))	
		{
			strConditions2=" and bat_type in ('Inverter Batteries','Tubular Battery','Flat Plate Battery','Tall Tubular Battery')";
		}
		else if(batterytypefilter.equals("Inverters"))	
		{
			strConditions2=" and bat_type in ('Inverters','Inverter')";
		}
		else
		{
			strConditions2=" and bat_type in ("+batterytypefilter+")";

		}
	}
	

	if(strConditions.equals(""))
		strConditions=" (date(creation_date) between '"+strFromDate+"' and '"+strToDate+"' or date(postponed_date) between '"+strFromDate+"' and '"+strToDate+"')";
	else
		strConditions=strConditions+" and (date(creation_date) between '"+strFromDate+"' and '"+strToDate+"' or date(postponed_date) between '"+strFromDate+"' and '"+strToDate+"')";
	
		//String strSqlQry ="select distinct emp_id,time_slot,description,activity_category,emp_name,date(creation_date),date_format(creation_date, '%W %M %D %Y'), count(time_slot) as count from mis where "+strConditions+" and emp_name='"+strempname+"' and emp_id='"+strempid+"' group by date(creation_date),time_slot,activity_category order by emp_name asc";
		if(state.equals("0") || state.equals("ALL") || state.equals("") || state.equals("Select State") || state.equals("defaultss") || state.equals("default"))
		{
			strStateConditions="";
		}
		else
		{
			strStateConditions=" and state='"+state+"'";
		}

		if(city.equals("0") || city.equals("ALL") || city.equals("") || city.equals("Select City") ||  city.equals("defaultss") ||  city.equals("default"))
		{
			strCityConditions="";
		}
		else
		{
			strCityConditions=" and city='"+city+"'";
		}
		if(batterytypefilter.equals("'healthcheck'") && type.equals("order"))
		{

				strSqlQry="select vis_ord_id,bat_type,veh_make,veh_model,bat_brand,bat_capacity,state,city,area,pincode,mobile_number,operator_flag,visitor_status,visitor_reasons,visitors_comments,agent_name,creation_date,CAST(updated_date AS CHAR) as updateddate,option_type from visitors_orders where option_type in ('healthcheck') "+strConditions1+"  "+strOperatorConditions+"  and operator_flag='no' and "+strConditions+" group by mobile_number order by field(visitor_status,'Repeated','Customer Contacted','Postponed','Customer Not Contacted','Not Called'),creation_date" ;
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

				sqlrycount = "select count(mobile_number) as visitors from visitors_orders where option_type in ('healthcheck') "+strConditions1+"  "+strOperatorConditions+"  and operator_flag='no'   and "+strConditions+"";
				LogLevel.DEBUG(5, new Throwable(), "sqlrycount :" + sqlrycount);

		}
		else
		{
	
			if(type.equals("order"))
			{
					
					strSqlQry="select vis_ord_id,bat_type,veh_make,veh_model,bat_brand,bat_capacity,state,city,area,pincode,mobile_number,operator_flag,visitor_status,visitor_reasons,visitors_comments,agent_name,creation_date,CAST(updated_date AS CHAR) as updateddate,option_type from visitors_orders where option_type in ('order','inverter') "+strConditions1+" "+strConditions2+" "+strOperatorConditions+" "+strStateConditions+" "+strCityConditions+" and operator_flag='no' and "+strConditions+" order by field(visitor_status,'Repeated','Customer Contacted','Postponed','Customer Not Contacted','Not Called'),creation_date" ;
					LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

					sqlrycount = "select count(mobile_number) as visitors from visitors_orders where option_type in ('order','inverter') "+strConditions1+" "+strConditions2+" "+strOperatorConditions+" "+strStateConditions+" "+strCityConditions+" and operator_flag='no'   and "+strConditions+"";
					LogLevel.DEBUG(5, new Throwable(), "sqlrycount :" + sqlrycount);

			}
			else
			{

					strSqlQry="select vis_ord_id,bat_type,veh_make,veh_model,bat_brand,bat_capacity,state,city,area,pincode,mobile_number,operator_flag,visitor_status,visitor_reasons,visitors_comments,agent_name,creation_date,CAST(updated_date AS CHAR) as updateddate,option_type from visitors_orders where option_type = '"+type+"' "+strConditions1+" "+strConditions2+" "+strOperatorConditions+" "+strStateConditions+" "+strCityConditions+" and operator_flag='no'  and "+strConditions+" order by field(visitor_status,'Repeated','Customer Contacted','Postponed','Customer Not Contacted','Not Called'),creation_date" ;
					LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

					sqlrycount = "select count(mobile_number) as visitors from visitors_orders where option_type='"+type+"' "+strConditions1+" "+strConditions2+" "+strOperatorConditions+" "+strStateConditions+" "+strCityConditions+" and operator_flag='no'  and "+strConditions+"";
					LogLevel.DEBUG(5, new Throwable(), "sqlrycount :" + sqlrycount);
			}
		}
	Vector batterywalevisitorsVector=qm.executeQuery(strSqlQry);
	LogLevel.DEBUG(5,new Throwable(),"batterywalevisitorsVector:"+ batterywalevisitorsVector );

	Vector visitorcountVector=qm.executeQuery(sqlrycount);
	LogLevel.DEBUG(5,new Throwable(),"visitorcountVector:"+ visitorcountVector );

	if(session.getAttribute("sesbatterywalevisitorsVectorVector")!=null)
	session.removeAttribute("sesbatterywalevisitorsVectorVector");
	session.setAttribute("sesbatterywalevisitorsVectorVector",batterywalevisitorsVector);
	session.setAttribute("sesvisitorcountVector",visitorcountVector);
	
	if(session.getAttribute("sesOptions")!=null)
	session.removeAttribute("sesOptions");
	session.setAttribute("sesOptions",htOptions);
	if(type.equals("inverter"))
	{
		res.sendRedirect("../jsp/operator/batterystore/visitors/invertervisitorssummary.jsp?txtFromDate="+txtFromDate+"&txtToDate="+txtToDate+"&dates="+strDateOpt+"&type="+type+"&statusfilter="+statusfilter+"&batterytypefilter="+batterytypefilter+"&statefilter="+state+"&cityfilter="+city);
	}
	else if(type.equals("laptopbattery"))
	{
		res.sendRedirect("../jsp/operator/batterystore/visitors/laptopvisitorssummary.jsp?txtFromDate="+txtFromDate+"&txtToDate="+txtToDate+"&dates="+strDateOpt+"&type="+type+"&statusfilter="+statusfilter+"&batterytypefilter="+batterytypefilter+"&statefilter="+state+"&cityfilter="+city);
	}
	else
	{
			res.sendRedirect("../jsp/operator/batterystore/visitors/visitorssummary.jsp?txtFromDate="+txtFromDate+"&txtToDate="+txtToDate+"&dates="+strDateOpt+"&type="+type+"&statusfilter="+statusfilter+"&batterytypefilter="+batterytypefilter+"&statefilter="+state+"&cityfilter="+city);
	}
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
	* This method is to get battery models.
	* @strSqlQry : carries the query to battery models details in battery_details table.
	\* **************************************************************************************************************************************/
	public String updatevisitorstatus(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strIpAddress,String strPort,String SMSFromAddress)
	{
		String chkSi= (req.getParameter("chkSi")!=null)?(req.getParameter("chkSi")):"";
		LogLevel.DEBUG(5, new Throwable(), "chkSi :" + chkSi);

		String visitorstatus= (req.getParameter("visitorstatus")!=null)?(req.getParameter("visitorstatus")):"";
		LogLevel.DEBUG(5, new Throwable(), "visitorstatus :" + visitorstatus);

		String visitorreason= (req.getParameter("visitorreason")!=null)?(req.getParameter("visitorreason")):"";
		LogLevel.DEBUG(5, new Throwable(), "visitorreason :" + visitorreason);

		String agentcomments= (req.getParameter("agentcomments")!=null)?(req.getParameter("agentcomments")):"";
		LogLevel.DEBUG(5, new Throwable(), "agentcomments :" + agentcomments);

		String postponedate= (req.getParameter("postponedate")!=null)?(req.getParameter("postponedate")):"";
		LogLevel.DEBUG(5, new Throwable(), "postponedate :" + postponedate);

		String cusmobilenumber= (req.getParameter("cusmobilenumber")!=null)?(req.getParameter("cusmobilenumber")):"";
		LogLevel.DEBUG(5, new Throwable(), "cusmobilenumber :" + cusmobilenumber);

		String strRes="";
		String stroperatorName=(String)session.getAttribute("sesBatteryOperatorName"); 
		LogLevel.DEBUG(5,new Throwable(),"stroperatorName :"+stroperatorName );
		
		try
		{	
			
			String strpostponedate="";

			
			if(visitorstatus.equals("Postponed"))
			{
				SimpleDateFormat sdfDate=new SimpleDateFormat("dd-MM-yyyy");
				Date txtpostponedate=sdfDate.parse(postponedate);

				SimpleDateFormat sdfString=new SimpleDateFormat("yyyy-MM-dd");
				strpostponedate=sdfString.format(txtpostponedate); 
				LogLevel.DEBUG(5, new Throwable(), "strpostponedate :"+ strpostponedate);

			}


			ServletOutputStream out=res.getOutputStream();
			
			String strSqlQryordstat = "SELECT visitors_comments FROM visitors_orders WHERE  vis_ord_id='"+chkSi+"'";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQryordstat :"+strSqlQryordstat);

			Hashtable ht = qm.getRow(strSqlQryordstat);
			String visitors_comments=(String)ht.get("visitors_comments");
			LogLevel.DEBUG(5,new Throwable(),"visitors_comments :"+visitors_comments);
			

			if(agentcomments.equals(""))
			{
				visitors_comments=visitors_comments;

			}
			else
			{
				if(visitors_comments == null || visitors_comments.equals(""))
				{
					visitors_comments=agentcomments+"-"+stroperatorName;
				}
				else
				{
					visitors_comments=visitors_comments+","+agentcomments+"-"+stroperatorName;
				}
			}

				visitors_comments = visitors_comments.replace("\\","\\\\"); visitors_comments= visitors_comments.replace("'","\\'"); visitors_comments= visitors_comments.replace("<","<"); visitors_comments= visitors_comments.replace("+","+"); visitors_comments= visitors_comments.replace("%u201C","&ldquo;"); visitors_comments= visitors_comments.replace("%u201D","&rdquo;"); visitors_comments= visitors_comments.replace("%20"," "); visitors_comments= visitors_comments.replace("%22","&#34;"); 
				visitors_comments= visitors_comments.replace("%27","\\'"); visitors_comments= visitors_comments.replace("%7E","~"); visitors_comments= visitors_comments.replace("%21","!"); visitors_comments= visitors_comments.replace("%23","#"); visitors_comments= visitors_comments.replace("%24","$"); visitors_comments= visitors_comments.replace("%25","%"); visitors_comments= visitors_comments.replace("%5E","^"); visitors_comments= visitors_comments.replace("%26","&");
				visitors_comments= visitors_comments.replace("%28","("); visitors_comments= visitors_comments.replace("%29",")"); visitors_comments= visitors_comments.replace("%7C","|"); visitors_comments= visitors_comments.replace("%7D","{"); visitors_comments= visitors_comments.replace("%7B","}"); visitors_comments= visitors_comments.replace("%3A",":"); visitors_comments= visitors_comments.replace("%3F","?"); visitors_comments= visitors_comments.replace("%3E",">");
				visitors_comments= visitors_comments.replace("%3C","<"); visitors_comments= visitors_comments.replace("%60","`"); visitors_comments= visitors_comments.replace("%3D","="); visitors_comments= visitors_comments.replace("%5C","\\\\"); visitors_comments= visitors_comments.replace("%5D","]"); visitors_comments= visitors_comments.replace("%5B","[");
				visitors_comments= visitors_comments.replace("%2C",",");visitors_comments= visitors_comments.replace("%3B",";");visitors_comments= visitors_comments.replace("%0A","\n");


			String strSqlQry ="";
			if(visitorstatus.equals("Postponed"))
			{
				if(stroperatorName.equals("null") || stroperatorName.equals("NULL") || stroperatorName.equals(null) || stroperatorName == "null" || stroperatorName == "NULL" || stroperatorName == null || stroperatorName.equals("0") || stroperatorName == "0" || stroperatorName.equals(" ") || stroperatorName == "" || stroperatorName.equals("undefined") || stroperatorName == "undefined")
				{

					LogLevel.DEBUG(1,new Throwable(),"Operator Name is Empty or null");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#00dd00' class='vrb10'></font> ");
					out.println("Failed to update Visitor status!");
				}
				else
				{
					strSqlQry= "update visitors_orders set visitor_status='"+visitorstatus+"',visitor_reasons='"+visitorreason+"',visitors_comments='"+visitors_comments+"',agent_name='"+stroperatorName+"',postponed_date='"+strpostponedate+"',updated_date=now() where vis_ord_id='"+chkSi+"'";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
				}
			}
			else
			{
				if(stroperatorName.equals("null") || stroperatorName.equals("NULL") || stroperatorName.equals(null) || stroperatorName == "null" || stroperatorName == "NULL" || stroperatorName == null || stroperatorName.equals("0") || stroperatorName == "0" || stroperatorName.equals(" ") || stroperatorName == "" || stroperatorName.equals("undefined") || stroperatorName == "undefined")
				{

					LogLevel.DEBUG(1,new Throwable(),"Operator Name is Empty or null");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#00dd00' class='vrb10'></font> ");
					out.println("Failed to update Visitor status!");
				}
				else
				{

					if(visitorstatus.equals("Customer Not Contacted") && visitorreason.equals("Sent SMS"))
					{

						CompareTrans ct = new CompareTrans(qm);
						SendSMS sendsms = new SendSMS(qm);
						String sitename="BookBattery.com";
						String sitesupportnumber="9603467559 ";
						String ThankUMsg="Sir/Madam we are calling you from "+sitename+" . You have visited our site for buying battery.Please call back us on "+sitesupportnumber+" so that, we can help you to get a Genuine battery at Reasonable Price.";						String strSMSResponse=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUMsg,cusmobilenumber);
						LogLevel.DEBUG(5, new Throwable(), "strSMSResponse :" + strSMSResponse);
					}
					else
					{


					}

					strSqlQry= "update visitors_orders set visitor_status='"+visitorstatus+"',visitor_reasons='"+visitorreason+"',visitors_comments='"+visitors_comments+"',agent_name='"+stroperatorName+"',updated_date=now() where vis_ord_id='"+chkSi+"'";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
				}

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
				LogLevel.DEBUG(1,new Throwable(),"");
				session.setAttribute("priority","1");
				session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'></font> ");
				//System.out.println(email);
				out.println("Successfully Updated Visitor Status as "+visitorstatus+".!");
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
	* This method is to fetch battery brand.
	* @strSqlQry : carries the query to battery brand from battery_details table.
	\* **************************************************************************************************************************************/
	public String getvisitorsstate(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	
	 String strDateOpt  = (req.getParameter("dates")!=null)?req.getParameter("dates").trim():"";
	 LogLevel.DEBUG(5, new Throwable(), "strDateOpt :"+ strDateOpt);

	 String txtFromDate  = (req.getParameter("txtFromDate")!=null)?req.getParameter("txtFromDate").trim():"";
	 LogLevel.DEBUG(5, new Throwable(), "txtFromDate :"+ txtFromDate);

	 String txtToDate  = (req.getParameter("txtToDate")!=null)?req.getParameter("txtToDate").trim():"";
	 LogLevel.DEBUG(5, new Throwable(), "txtToDate :"+ txtToDate);


	 String state="";
	 String strRes="";
	 String strSqlQry ="";

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
		//txtToDate=curDate+"-"+curMonth+"-"+curYear;
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

	 try
		{	

			SimpleDateFormat sdfDate=new SimpleDateFormat("dd-MM-yyyy");
			Date fromDate=sdfDate.parse(txtFromDate); 
			Date toDate=sdfDate.parse(txtToDate); 

			SimpleDateFormat sdfString=new SimpleDateFormat("yyyy-MM-dd");
			String strFromDate=sdfString.format(fromDate); 
			LogLevel.DEBUG(5, new Throwable(), "strFromDate :"+ strFromDate);

			String strToDate=sdfString.format(toDate); 
			LogLevel.DEBUG(5, new Throwable(), "strToDate :"+ strToDate);

	 		ServletOutputStream out=res.getOutputStream();
		
				String strSqlQryState ="select distinct(state) from visitors_orders where state not in('','null') and (date(creation_date) between '"+strFromDate+"' and '"+strToDate+"' or date(postponed_date) between '"+strFromDate+"' and '"+strToDate+"') order by state asc";
				LogLevel.DEBUG(1, new Throwable(), "strSqlQryState :" + strSqlQryState);
			

		Vector StateVector=qm.executeQuery(strSqlQryState);
		LogLevel.DEBUG(1,new Throwable(),"StateVector:"+StateVector );

			if(StateVector.isEmpty())
		   { 
				out.println("<option  value='defaultss'>Select State</option>");
				return strRes;
		   }
		  else
		   {
				for (int i=0; i<StateVector.size(); i++)
				{
					if(i==0)
					{
						out.print("<option  value='default'>Select State</option>");
						
							
							//out.print("<option style='background:#FFF' value='ALL'>All</option>");
							
						
					}
					Hashtable ht1=(Hashtable)StateVector.get(i);
					state =(String)ht1.get("state");
					out.print("<option  value='"+state+"'>"+state+"</option>"); 
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



	/* **************************************************************************************************************************************\
	* This method is to fetch battery brand.
	* @strSqlQry : carries the query to battery brand from battery_details table.
	\* **************************************************************************************************************************************/
	public String getvisitorscity(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	 
	  String strDateOpt  = (req.getParameter("dates")!=null)?req.getParameter("dates").trim():"";
	 LogLevel.DEBUG(5, new Throwable(), "strDateOpt :"+ strDateOpt);

	 String txtFromDate  = (req.getParameter("txtFromDate")!=null)?req.getParameter("txtFromDate").trim():"";
	 LogLevel.DEBUG(5, new Throwable(), "txtFromDate :"+ txtFromDate);

	 String txtToDate  = (req.getParameter("txtToDate")!=null)?req.getParameter("txtToDate").trim():"";
	 LogLevel.DEBUG(5, new Throwable(), "txtToDate :"+ txtToDate);

	 String state= (req.getParameter("state")!=null)?(req.getParameter("state")):"";
	 LogLevel.DEBUG(1, new Throwable(), "state :" + state);

	 String city="";
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
		//txtToDate=curDate+"-"+curMonth+"-"+curYear;
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
	 try
		{	

			SimpleDateFormat sdfDate=new SimpleDateFormat("dd-MM-yyyy");
			Date fromDate=sdfDate.parse(txtFromDate); 
			Date toDate=sdfDate.parse(txtToDate); 

			SimpleDateFormat sdfString=new SimpleDateFormat("yyyy-MM-dd");
			String strFromDate=sdfString.format(fromDate); 
			LogLevel.DEBUG(5, new Throwable(), "strFromDate :"+ strFromDate);

			String strToDate=sdfString.format(toDate); 
			LogLevel.DEBUG(5, new Throwable(), "strToDate :"+ strToDate);

	 		ServletOutputStream out=res.getOutputStream();
		
				String strSqlQryCity ="select distinct(city) from visitors_orders where city not in('','null') and state='"+state+"' and (date(creation_date) between '"+strFromDate+"' and '"+strToDate+"' or date(postponed_date) between '"+strFromDate+"' and '"+strToDate+"') order by city asc";
				LogLevel.DEBUG(1, new Throwable(), "strSqlQryCity :" + strSqlQryCity);
			

		Vector CityVector=qm.executeQuery(strSqlQryCity);
		LogLevel.DEBUG(1,new Throwable(),"CityVector:"+CityVector );

			if(CityVector.isEmpty())
		   { 
				out.println("<option value='defaultss'>Select City</option>");
				return strRes;
		   }
		  else
		   {
				for (int i=0; i<CityVector.size(); i++)
				{
					if(i==0)
					{
						out.print("<option  value='default'>Select City</option>");
						
							
							//out.print("<option style='background:#FFF' value='ALL'>All</option>");
							
						
					}
					Hashtable ht1=(Hashtable)CityVector.get(i);
					city =(String)ht1.get("city");
					out.print("<option value='"+city+"'>"+city+"</option>"); 
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
