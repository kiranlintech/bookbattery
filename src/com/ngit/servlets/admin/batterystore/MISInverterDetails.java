/***********************************************************************		
	NGIT Confidential. 
	@File Name   : BatteryHome.java 
	@Description : This Servlet is used to select the Battery Details.
	@Author	     : Gowtham.K
	@Date        : 30th August 2013
******************************************************************/ 
 
package com.ngit.servlets.admin.batterystore; 
 
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.admin.mis.GenerateExcelbattery;

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

  
public class MISInverterDetails extends HttpServlet 
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

		/* ****************************************************************************************\
		* This action is used to get battery brands.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("getlocations"))
		{
			try
			{
				String strRes=getlocations(req,res,session);
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

		if(strWhatToDo.equalsIgnoreCase("getconfirmedorders"))
			{ 
			try
			{
				String strRes=getconfirmedorders(req,res,session);
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
	* This method is to fetch battery brands details.
	* @strSqlQry : carries the query to get battery brands from battery_oreder_details table.
	\* **************************************************************************************************************************************/
	public String getlocations(HttpServletRequest req , HttpServletResponse res,HttpSession session)
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
		String strSqlQry ="select distinct(city) from inverter_order_details where city!='' order by city asc";
		LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
		
		Vector LocationVector=qm.executeQuery(strSqlQry);
		LogLevel.DEBUG(5,new Throwable(),"LocationVector:"+LocationVector);
		if(LocationVector.isEmpty())
		{ 
			LogLevel.DEBUG(1,new Throwable(),"Failed to fetch locations ");
			session.setAttribute("priority","1");
			session.setAttribute("sesadminloginErrorMsg", "<font color='#00dd00' class='vrb10'>Failed to fetch locations! </font> ");
			res.sendRedirect("../jsp/admin/batterystore/mis/invertermisselconfirm.jsp?dates="+strDateOpt+"&txtFromDate="+txtFromDate+" &txtToDate="+txtToDate);
			return strRes;
		}
		else
		{
			if(keyword.equals("confirm") || keyword.equals("ordered") || keyword.equals("installed") || keyword.equals("cancelled-franchisee-offbushrs") || keyword.equals("cancelled-franchisee-denied") || keyword.equals("cancelled-franchisee-notresponded") || keyword.equals("cancelled-franchisee-modeloutofstock") || keyword.equals("cancelled-customer") || keyword.equals("cancelled-regenerated") )
			{
			LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched Categories");
			session.setAttribute("seslocatiovector", LocationVector);
			session.setAttribute("sesErrorMsgss","<font color='#CC0000' class='vrb10'>Succesfully Fetched Categories.</font> ");
			res.sendRedirect("../jsp/admin/batterystore/mis/invertermisselconfirm.jsp?keyword="+keyword+"&dates="+strDateOpt+"&txtFromDate="+txtFromDate+" &txtToDate="+txtToDate);
			return strRes;
			}
			else if(keyword.equals("postponed"))
			{
					session.setAttribute("seslocatiovector", LocationVector);
					session.setAttribute("sesErrorMsgss","<font color='#CC0000' class='vrb10'>Succesfully Fetched Locations.</font> ");
					res.sendRedirect("../jsp/admin/batterystore/mis/inverterselectlocation.jsp?keyword="+keyword+"&txtFromDate="+txtFromDate+" &txtToDate="+txtToDate);	
				
			}
			else
			{
			LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched Categories");
			session.setAttribute("seslocatiovector", LocationVector);
			session.setAttribute("sesErrorMsgss","<font color='#CC0000' class='vrb10'>Succesfully Fetched Categories.</font> ");
			res.sendRedirect("../jsp/admin/batterystore/mis/invertermisselconfirm.jsp?dates="+strDateOpt+"&txtFromDate="+txtFromDate+" &txtToDate="+txtToDate);
			return strRes;
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
	/* **************************************************************************************************************************************\
* This method is to get with out ordered battery details details.
* @strSqlQry: carries the query to ordered battery details from battery_order_details table.
\* **************************************************************************************************************************************/
	public String getconfirmedorders(HttpServletRequest req , HttpServletResponse res,HttpSession session)
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
	if(keyword.equals("confirm"))
		{
		orderstatus = "Confirmed";
		}
	else if(keyword.equals("ordered"))
		{
		orderstatus = "Ordered";
		}
	else if(keyword.equals("installed"))
		{
		orderstatus = "Installed";
		}
	else if(keyword.equals("postponed"))
		{
		orderstatus = "Postponed";
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
		else if(keyword.equals("postponed"))
		{
		if(!city.equals("ALL") )
			{
			strConditions1= " and city='"+city+"' and order_status='postponed' ";
			}
		else if(city.equals("ALL") )
			{
			strConditions1= " and order_status='postponed'";
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

	
	//String strSqlQry ="select distinct emp_id,time_slot,description,activity_category,emp_name,date(creation_date),date_format(creation_date, '%W %M %D %Y'), count(time_slot) as count from mis where "+strConditions+" and emp_name='"+strempname+"' and emp_id='"+strempid+"' group by date(creation_date),time_slot,activity_category order by emp_name asc";
	String strSqlQry="";
		if(keyword.equals("postponed"))
		{
			strSqlQry="select order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobile_number,retailer_emailid,state,city,inverter_brand,inverter_capacity,price,pincode,area,order_status,creation_date,postpone_date,postpone_message from inverter_order_details where date(postpone_date) between '"+strFromDate+"' and '"+strToDate+"' "+strConditions1+"  order by postpone_date";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

		}
		else if(keyword.equals("installed"))
		{
			strSqlQry="select order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobile_number,retailer_emailid,state,city,inverter_brand,inverter_capacity,price,pincode,area,order_status,creation_date,CAST(postpone_date AS CHAR) as postpone_date from inverter_order_details where date(updated_date) between '"+strFromDate+"' and '"+strToDate+"' "+strConditions1+"  order by updated_date";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

		}
		else
		{
	
			strSqlQry="select order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobile_number,retailer_emailid,state,city,inverter_brand,inverter_capacity,price,pincode,area,order_status,creation_date,CAST(postpone_date AS CHAR) as postpone_date from inverter_order_details where "+strConditions+" "+strConditions1+"  order by creation_date";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
		}
	
	Vector BatOrdstatusVector=qm.executeQuery(strSqlQry);
	LogLevel.DEBUG(5,new Throwable(),"BatOrdstatusVector:"+ BatOrdstatusVector );

	if(session.getAttribute("sesBatOrderstatusVectorVector")!=null)
	session.removeAttribute("sesBatOrderstatusVectorVector");
	session.setAttribute("sesBatOrderstatusVectorVector",BatOrdstatusVector);
	
	if(session.getAttribute("sesOptions")!=null)
	session.removeAttribute("sesOptions");
	session.setAttribute("sesOptions",htOptions);
	res.sendRedirect("../jsp/admin/batterystore/mis/inverterconfirmedordereddetails.jsp?txtFromDate="+txtFromDate+"&txtToDate="+txtToDate+"&city="+city+"&keyword="+keyword+"&dates="+strDateOpt);
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
