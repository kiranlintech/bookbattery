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

  
public class MISBatteryDetails extends HttpServlet 
{  
	private ServletContext context; 
	QueryManager qm;
	String baseURL;
	public void init(ServletConfig config)throws ServletException
	{ 
		super.init(config); 
		context = getServletContext(); 
		String baseURL;

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
		if(strWhatToDo.equalsIgnoreCase("getbatterybrands"))
		{
			try
			{
				String strRes=getbatterybrands(req,res,session);
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
		* This action is used to get battery models.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("getbatterymodels"))
		{
			try
			{
				String strRes=getbatterymodels(req,res,session);
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
		* This action is used to get city.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("getcity"))
		{
			try
			{
				String strRes=getcity(req,res,session);
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
		* This action is used to get ordered battery details.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("getorderbatterydetails"))
		{
			try
			{
				String strRes=getorderbatterydetails(req,res,session);
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
	/* ****************************************************************************************\
	* This action is used to get retailers.
	\* *****************************************************************************************/		

	if(strWhatToDo.equalsIgnoreCase("getbatteryorderlocation"))
			{ 
			try
			{
				String strRes=getbatteryorderlocation(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("getbatteryordnumber"))
		{
			try
			{
				String strRes=getbatteryordnumber(req,res,session);
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

	if(strWhatToDo.equalsIgnoreCase("getlocationord"))
			{ 
			try
			{
				String strRes=getlocationord(req,res,session);
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

	if(strWhatToDo.equalsIgnoreCase("getbatteryordernodetails"))
			{ 
			try
			{
				String strRes=getbatteryordernodetails(req,res,session);
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
		    /* ****************************************************************************************\
	* This action is used to get retailers.
	\* *****************************************************************************************/		

	if(strWhatToDo.equalsIgnoreCase("getbatteryhealthcheck"))
			{ 
			try
			{
				String strRes=getbatteryhealthcheck(req,res,session);
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

	if(strWhatToDo.equalsIgnoreCase("getregisterbattery"))
			{ 
			try
			{
				String strRes=getregisterbattery(req,res,session);
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

	if(strWhatToDo.equalsIgnoreCase("getlaptopbatterydetails"))
			{ 
			try
			{
				String strRes=getlaptopbatterydetails(req,res,session);
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
	public String getbatterybrands(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
	String strDateOpt  = (req.getParameter("dates")!=null)?req.getParameter("dates"):"";
	LogLevel.DEBUG(5, new Throwable(), "strDateOpt :"+ strDateOpt);

	String txtFromDate  = (req.getParameter("txtFromDate")!=null)?req.getParameter("txtFromDate"):"";
	String txtToDate  = (req.getParameter("txtToDate")!=null)?req.getParameter("txtToDate"):"";
	 try
	   {	
		String strSqlQry ="select distinct(battery_brand) from battery_order_details";
		LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
		
		Vector BatteryBrandVector=qm.executeQuery(strSqlQry);
		LogLevel.DEBUG(5,new Throwable(),"BatteryBrandVector:"+BatteryBrandVector );
		if(BatteryBrandVector.isEmpty())
		{ 
			LogLevel.DEBUG(1,new Throwable(),"Failed to fetch categories ");
			session.setAttribute("priority","1");
			session.setAttribute("sesadminloginErrorMsg", "<font color='#00dd00' class='vrb10'>Failed to fetch categories! </font> ");
			res.sendRedirect("../jsp/admin/batterystore/mis/misseloptions.jsp?dates="+strDateOpt+"&txtFromDate="+txtFromDate+" &txtToDate="+txtToDate);
			return strRes;
		}
		else
		{
			LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched Categories");
			session.setAttribute("sesbatterybrandvector", BatteryBrandVector);
			session.setAttribute("sesErrorMsgss","<font color='#CC0000' class='vrb10'>Succesfully Fetched Categories.</font> ");
			res.sendRedirect("../jsp/admin/batterystore/mis/misseloptions.jsp?dates="+strDateOpt+"&txtFromDate="+txtFromDate+" &txtToDate="+txtToDate);
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
	* This method is to fetch model details.
	* @strSqlQry : carries the query to get model from battery_order_details table.
	\* **************************************************************************************************************************************/
	public String getbatterymodels(HttpServletRequest req , HttpServletResponse res, HttpSession session)
	{
	String strbatbrand  = (req.getParameter("batbrand")!=null)?req.getParameter("batbrand"):"";
	LogLevel.DEBUG(5,new Throwable(),"strbatbrand :"+strbatbrand );

	String strMsg="";
	String result="";
	String strSqlQry1="";
	try
	{
	ServletOutputStream out = res.getOutputStream();

	if(strbatbrand.equals("ALL"))
	{
	strSqlQry1 = "select distinct(battery_model) from battery_order_details";
	LogLevel.DEBUG(5,new Throwable(),"strSqlQry1:"+strSqlQry1 );
	}
	else
	{
	strSqlQry1 = "select distinct(battery_model) from battery_order_details where battery_brand='"+strbatbrand+"'";
	LogLevel.DEBUG(5,new Throwable(),"strSqlQry1:"+strSqlQry1 );
	}	
	Vector modelVector=qm.executeQuery(strSqlQry1);
	LogLevel.DEBUG(5,new Throwable(),"modelVector :"+modelVector);

	if( modelVector==null || modelVector.size() == 0)
	{
		LogLevel.DEBUG(1, new Throwable(),"Failed to fetch model ");
		session.setAttribute("priority", "1");
		session.setAttribute("sesErrorMsg",	"<font color='#CC0000' class='vrb10'>Failed to fetch model</font> ");
		out.println("Battery Models are not available under the selected BatteryBrand");
	}
	else
	{
		for (int i = 0; i < modelVector.size(); i++)
		{
			Hashtable ht = (Hashtable)modelVector.get(i);
			String model = (String)ht.get("battery_model");
			int id=0;
			if (result.equals(""))
			result = id + ":" + model;
			else
			result = result + "|" + id + ":" + model;
		}
	 }
    }
	catch(Exception e)
	{
		LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
		e.printStackTrace();
	}
		return result;
   } 

   /* **************************************************************************************************************************************\
	* This method is to fetch city details.
	* @strSqlQry : carries the query to get city from battery_order_details table.
	\* **************************************************************************************************************************************/
	public String getcity(HttpServletRequest req , HttpServletResponse res, HttpSession session)
	{
	String strMsg="";
	String result="";
	try
	{
	ServletOutputStream out = res.getOutputStream();

	String strSqlQry1 = "select distinct(city) from battery_order_details where city!=''";
	LogLevel.DEBUG(5,new Throwable(),"strSqlQry1:"+strSqlQry1 );
		
	Vector cityVector=qm.executeQuery(strSqlQry1);
	LogLevel.DEBUG(5,new Throwable(),"cityVector :"+cityVector);

	if( cityVector==null || cityVector.size() == 0)
	{
		LogLevel.DEBUG(1, new Throwable(),"Failed to fetch city ");
		session.setAttribute("priority", "1");
		session.setAttribute("sesErrorMsg",	"<font color='#CC0000' class='vrb10'>Failed to fetch city</font> ");
		out.println("City are not available under the selected Model");
	}
	else
	{
		for (int i = 0; i < cityVector.size(); i++)
		{
			Hashtable ht = (Hashtable)cityVector.get(i);
			String city = (String)ht.get("city");
			int id=0;
			if (result.equals(""))
			result = id + ":" + city;
			else
			result = result + "|" + id + ":" + city;
		}
	 }
    }
	catch(Exception e)
	{
		LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
		e.printStackTrace();
	}
		return result;
   } 
/* **************************************************************************************************************************************\
* This method is to get with out ordered battery details details.
* @strSqlQry: carries the query to ordered battery details from battery_order_details table.
\* **************************************************************************************************************************************/
	public String getorderbatterydetails(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";

	Hashtable htOptions=new Hashtable();
	String strbatname = (req.getParameter("batname")!=null)?(req.getParameter("batname")):"0";
	LogLevel.DEBUG(5,new Throwable(),"strbatname :"+strbatname );

	String strmodel = (req.getParameter("model")!=null)?(req.getParameter("model")):"0";
	LogLevel.DEBUG(5,new Throwable(),"strmodel :"+strmodel );

	String strcity  = (req.getParameter("city")!=null)?req.getParameter("city").trim() :"";
	LogLevel.DEBUG(5, new Throwable(), "strcity :"+ strcity);
	
	String strstatus  = (req.getParameter("status")!=null)?req.getParameter("status").trim() :"";
	LogLevel.DEBUG(5, new Throwable(), "strstatus :"+ strstatus);

	String strDateOpt  = (req.getParameter("dates")!=null)?req.getParameter("dates").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "strDateOpt :"+ strDateOpt);

	String txtFromDate  = (req.getParameter("txtFromDate")!=null)?req.getParameter("txtFromDate").trim():"";
	String txtToDate  = (req.getParameter("txtToDate")!=null)?req.getParameter("txtToDate").trim():"";

	
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

	LogLevel.DEBUG(5, new Throwable(), "strbatname :"+ strbatname);
	htOptions.put("strbatname",strbatname);

	LogLevel.DEBUG(5, new Throwable(), "strmodel :"+ strmodel);
	htOptions.put("strmodel",strmodel);

	LogLevel.DEBUG(5, new Throwable(), "strcity :"+ strcity);
	htOptions.put("strcity",strcity);
	 
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
	
	if(!strbatname.equals("ALL") && strmodel.equals("ALL") && strcity.equals("ALL"))
		{
		strConditions1= " and battery_brand='"+strbatname+"'";
		}
	else if(strbatname.equals("ALL") && !strmodel.equals("ALL") && strcity.equals("ALL"))
		{
		strConditions1= " and battery_model='"+strmodel+"'";
		}
	else if(strbatname.equals("ALL") && strmodel.equals("ALL") && !strcity.equals("ALL"))
		{
		strConditions1= " and city='"+strcity+"'";
		}
	else if(!strbatname.equals("ALL") && !strmodel.equals("ALL") && strcity.equals("ALL"))
		{
		strConditions1= " and battery_brand='"+strbatname+"' and battery_model='"+strmodel+"'";
		}
	else if(strbatname.equals("ALL") && !strmodel.equals("ALL") && !strcity.equals("ALL"))
		{
		strConditions1= " and battery_model='"+strmodel+"' and city='"+strcity+"'";
		}
	else if(!strbatname.equals("ALL") && strmodel.equals("ALL") && !strcity.equals("ALL"))
		{
		strConditions1= " and battery_brand='"+strbatname+"' and city='"+strcity+"'";
		}
	else if(!strbatname.equals("ALL") && !strmodel.equals("ALL") && !strcity.equals("ALL"))
		{
		strConditions1= " and battery_brand='"+strbatname+"' and battery_model='"+strmodel+"' and city='"+strcity+"'";
		}
		else
		{
		strConditions1= "";
		}

	//String strSqlQry ="select distinct emp_id,time_slot,description,activity_category,emp_name,date(creation_date),date_format(creation_date, '%W %M %D %Y'), count(time_slot) as count from mis where "+strConditions+" and emp_name='"+strempname+"' and emp_id='"+strempid+"' group by date(creation_date),time_slot,activity_category order by emp_name asc";
	String strSqlQry="";
	if(strstatus.equals("postponed"))
	{
		strSqlQry="select order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailis,city,battery_brand,battery_model,price,pdfurl,bat_type,veh_name,veh_model,area,witholdbatprice,order_status,creation_date,postpone_date from battery_order_details where "+strConditions+" "+strConditions1+" and order_status='"+strstatus+"'  order by creation_date";
		LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
	}
	else if(strstatus.equals("installed"))
	{
		strSqlQry="select order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailis,city,battery_brand,battery_model,price,pdfurl,bat_type,veh_name,veh_model,area,witholdbatprice,order_status,creation_date from battery_order_details where date(updated_date) between '"+strFromDate+"' and '"+strToDate+"' "+strConditions1+" and order_status='"+strstatus+"'  order by updated_date";
		LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
	}
	else
	{
		strSqlQry="select order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailis,city,battery_brand,battery_model,price,pdfurl,bat_type,veh_name,veh_model,area,witholdbatprice,order_status,creation_date from battery_order_details where "+strConditions+" "+strConditions1+" and order_status='"+strstatus+"'  order by creation_date";
		LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
	}


	Vector BatOrdVector=qm.executeQuery(strSqlQry);
	LogLevel.DEBUG(5,new Throwable(),"BatOrdVector:"+ BatOrdVector );

	if(session.getAttribute("sesEmpdetailwithoutcatVector")!=null)
	session.removeAttribute("sesEmpdetailwithoutcatVector");
	session.setAttribute("sesBatOrdVectorVector",BatOrdVector);
	
	if(session.getAttribute("sesOptions")!=null)
	session.removeAttribute("sesOptions");
	session.setAttribute("sesOptions",htOptions);
	res.sendRedirect("../jsp/admin/batterystore/mis/orderbatterydetailsummary.jsp?txtFromDate="+txtFromDate+"&txtToDate="+txtToDate+"&strbatname="+strbatname+"&strmodel="+strmodel+"&strcity="+strcity+"&status="+strstatus+"&dates="+strDateOpt);
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
		String strSqlQry ="select distinct(city) from battery_order_details where city!='' order by city asc";
		LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
		
		Vector LocationVector=qm.executeQuery(strSqlQry);
		LogLevel.DEBUG(5,new Throwable(),"LocationVector:"+LocationVector);
		if(LocationVector.isEmpty())
		{ 
			LogLevel.DEBUG(1,new Throwable(),"Failed to fetch locations ");
			session.setAttribute("priority","1");
			session.setAttribute("sesadminloginErrorMsg", "<font color='#00dd00' class='vrb10'>Failed to fetch locations! </font> ");
			res.sendRedirect("../jsp/admin/batterystore/mis/missellocation.jsp?dates="+strDateOpt+"&txtFromDate="+txtFromDate+" &txtToDate="+txtToDate);
			return strRes;
		}
		else
		{
			if(keyword.equals("confirm") || keyword.equals("ordered") || keyword.equals("installed") || keyword.equals("cancelled-franchisee-offbushrs") || keyword.equals("cancelled-franchisee-denied") || keyword.equals("cancelled-franchisee-notresponded") || keyword.equals("cancelled-franchisee-modeloutofstock") || keyword.equals("cancelled-customer")  || keyword.equals("cancelled-regenerated"))
			{
			LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched Categories");
			session.setAttribute("seslocatiovector", LocationVector);
			session.setAttribute("sesErrorMsgss","<font color='#CC0000' class='vrb10'>Succesfully Fetched Categories.</font> ");
			res.sendRedirect("../jsp/admin/batterystore/mis/misselconfirm.jsp?keyword="+keyword+"&dates="+strDateOpt+"&txtFromDate="+txtFromDate+" &txtToDate="+txtToDate);
			return strRes;
			}
			else if(keyword.equals("postponed"))
			{
					session.setAttribute("seslocatiovector", LocationVector);
					session.setAttribute("sesErrorMsgss","<font color='#CC0000' class='vrb10'>Succesfully Fetched Locations.</font> ");
					res.sendRedirect("../jsp/admin/batterystore/mis/selectlocation.jsp?keyword="+keyword+"&txtFromDate="+txtFromDate+" &txtToDate="+txtToDate);	
				
			}
			else
			{
				LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched Categories");
				session.setAttribute("seslocatiovector", LocationVector);
				session.setAttribute("sesErrorMsgss","<font color='#CC0000' class='vrb10'>Succesfully Fetched Categories.</font> ");
				res.sendRedirect("../jsp/admin/batterystore/mis/missellocation.jsp?dates="+strDateOpt+"&txtFromDate="+txtFromDate+" &txtToDate="+txtToDate);
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
	* This method is to get vehicle model.
	* @strSqlQry : carries the query to vehicle model details in vehicle_details table.
	\* **************************************************************************************************************************************/
	public String getretailers(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String city= (req.getParameter("city")!=null)?(req.getParameter("city")):"";
			LogLevel.DEBUG(5,new Throwable(),"city:"+city);

			//String veh_name_fetched="";
			String retailer_name="";	
			String strSqlqry ="";

	 		ServletOutputStream out=res.getOutputStream();
			
			if(city.equals("ALL"))
			{
				strSqlqry = "select distinct(retailer_name) from battery_order_details order by retailer_name asc"; 
				LogLevel.DEBUG(5, new Throwable(), "strSqlqry :" + strSqlqry);

			}
			else
			{
			strSqlqry = "select distinct(retailer_name) from battery_order_details where city='"+city+"' order by retailer_name asc"; 
			LogLevel.DEBUG(5, new Throwable(), "strSqlqry :" + strSqlqry);
			}

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
						out.print("<option value='ALL'>All</option>");
						}
						Hashtable ht=(Hashtable)RetailerVector.get(i);
						//veh_name_fetched =String.valueOf(ht.get("vehicle_name"));
						retailer_name =String.valueOf(ht.get("retailer_name"));
						out.print("<option style='background:#FFF' value='"+retailer_name+"'>"+retailer_name+"</option>"); 
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
* This method is to get with out ordered battery details details.
* @strSqlQry: carries the query to ordered battery details from battery_order_details table.
\* **************************************************************************************************************************************/
	public String getbatteryorderlocation(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";

	Hashtable htOptions=new Hashtable();

	String retailer = (req.getParameter("retailer")!=null)?(req.getParameter("retailer")):"0";
	LogLevel.DEBUG(5,new Throwable(),"retailer :"+retailer );

	String strcity  = (req.getParameter("city")!=null)?req.getParameter("city").trim() :"";
	LogLevel.DEBUG(5, new Throwable(), "strcity :"+ strcity);

	String strstatus = (req.getParameter("status")!=null)?req.getParameter("status").trim() :"";
	LogLevel.DEBUG(5, new Throwable(), "strstatus :"+ strstatus);

	String strDateOpt  = (req.getParameter("dates")!=null)?req.getParameter("dates").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "strDateOpt :"+ strDateOpt);

	String txtFromDate  = (req.getParameter("txtFromDate")!=null)?req.getParameter("txtFromDate").trim():"";
	String txtToDate  = (req.getParameter("txtToDate")!=null)?req.getParameter("txtToDate").trim():"";

	
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

	LogLevel.DEBUG(5, new Throwable(), "retailer :"+ retailer);
	htOptions.put("retailer",retailer);

	LogLevel.DEBUG(5, new Throwable(), "strcity :"+ strcity);
	htOptions.put("strcity",strcity);
	 
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

	if(!strcity.equals("ALL") && !retailer.equals("ALL"))
	{
		strConditions1= " and city='"+strcity+"' and retailer_name='"+retailer+"'";
	}
	else if(strcity.equals("ALL") && retailer.equals("ALL"))
	{
		strConditions1= " ";
	}
	else if(!strcity.equals("ALL") && retailer.equals("ALL"))
	{
		strConditions1= " and city='"+strcity+"' ";
	}
	else if(strcity.equals("ALL") && !retailer.equals("ALL"))
	{
		strConditions1= " and retailer_name='"+retailer+"' ";
	}
	
	//String strSqlQry ="select distinct emp_id,time_slot,description,activity_category,emp_name,date(creation_date),date_format(creation_date, '%W %M %D %Y'), count(time_slot) as count from mis where "+strConditions+" and emp_name='"+strempname+"' and emp_id='"+strempid+"' group by date(creation_date),time_slot,activity_category order by emp_name asc";
	String strSqlQry="";
	if(strstatus.equals("postponed"))
	{
		strSqlQry="select order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailis,city,battery_brand,battery_model,price,pincode,pdfurl,bat_type,veh_name,veh_model,area,witholdbatprice,order_status,creation_date,postpone_date from battery_order_details where "+strConditions+" "+strConditions1+"  and order_status='"+strstatus+"'  order by creation_date";
		LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
	}
	else if(strstatus.equals("installed"))
	{
		strSqlQry="select order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailis,city,battery_brand,battery_model,price,pdfurl,bat_type,veh_name,veh_model,area,witholdbatprice,order_status,creation_date from battery_order_details where date(updated_date) between '"+strFromDate+"' and '"+strToDate+"' "+strConditions1+" and order_status='"+strstatus+"'  order by updated_date";
		LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
	}
	else
	{
		strSqlQry="select order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailis,city,battery_brand,battery_model,price,pincode,pdfurl,bat_type,veh_name,veh_model,area,witholdbatprice,order_status,creation_date from battery_order_details where "+strConditions+" "+strConditions1+"  and order_status='"+strstatus+"'  order by creation_date";
		LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
	}

	Vector BatOrdLocVector=qm.executeQuery(strSqlQry);
	LogLevel.DEBUG(5,new Throwable(),"BatOrdLocVector:"+ BatOrdLocVector );

	if(session.getAttribute("sesBatOrdLocVectorVector")!=null)
	session.removeAttribute("sesBatOrdLocVectorVector");
	session.setAttribute("sesBatOrdLocVectorVector",BatOrdLocVector);
	
	if(session.getAttribute("sesOptions")!=null)
	session.removeAttribute("sesOptions");
	session.setAttribute("sesOptions",htOptions);
	res.sendRedirect("../jsp/admin/batterystore/mis/orderbatteryretlocationsummary.jsp?txtFromDate="+txtFromDate+"&txtToDate="+txtToDate+"&retailer="+retailer+"&strcity="+strcity+"&dates="+strDateOpt+"&strstatus="+strstatus);
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
	* This method is to fetch battery brands details.
	* @strSqlQry : carries the query to get battery brands from battery_oreder_details table.
	\* **************************************************************************************************************************************/
	public String getbatteryordnumber(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
	String strDateOpt  = (req.getParameter("dates")!=null)?req.getParameter("dates"):"";
	LogLevel.DEBUG(5, new Throwable(), "strDateOpt :"+ strDateOpt);

	String txtFromDate  = (req.getParameter("txtFromDate")!=null)?req.getParameter("txtFromDate"):"";
	String txtToDate  = (req.getParameter("txtToDate")!=null)?req.getParameter("txtToDate"):"";
	 try
	   {	
		String strSqlQry ="select distinct(order_number) from battery_order_details";
		LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
		
		Vector BatteryOrdNoVector=qm.executeQuery(strSqlQry);
		LogLevel.DEBUG(5,new Throwable(),"BatteryOrdNoVector:"+BatteryOrdNoVector );
		if(BatteryOrdNoVector.isEmpty())
		{ 
			LogLevel.DEBUG(1,new Throwable(),"Failed to fetch battery ord number ");
			session.setAttribute("priority","1");
			session.setAttribute("sesadminloginErrorMsg", "<font color='#00dd00' class='vrb10'>Failed to fetch battery ord number! </font> ");
			res.sendRedirect("../jsp/admin/batterystore/mis/misbatteryordnumber.jsp?dates="+strDateOpt+"&txtFromDate="+txtFromDate+" &txtToDate="+txtToDate);
			return strRes;
		}
		else
		{
			LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched battery ord number");
			session.setAttribute("sesbatteryOrdNovector", BatteryOrdNoVector);
			session.setAttribute("sesErrorMsgss","<font color='#CC0000' class='vrb10'>Succesfully Fetched battery ord number.</font> ");
			res.sendRedirect("../jsp/admin/batterystore/mis/misbatteryordnumber.jsp?dates="+strDateOpt+"&txtFromDate="+txtFromDate+" &txtToDate="+txtToDate);
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
	* This method is to get vehicle model.
	* @strSqlQry : carries the query to vehicle model details in vehicle_details table.
	\* **************************************************************************************************************************************/
	public String getlocationord(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String ordnumb= (req.getParameter("ordnumb")!=null)?(req.getParameter("ordnumb")):"";
			LogLevel.DEBUG(5,new Throwable(),"ordnumb:"+ordnumb);

			//String veh_name_fetched="";
			String city="";	
			String strSqlqry="";

	 		ServletOutputStream out=res.getOutputStream();

			if(ordnumb.equals("ALL") || ordnumb == "ALL")
			{
			strSqlqry = "select distinct(city) from battery_order_details order by city asc"; 
			LogLevel.DEBUG(5, new Throwable(), "strSqlqry :" + strSqlqry);
			}
			else
			{
			strSqlqry = "select distinct(city) from battery_order_details where order_number='"+ordnumb+"' order by city asc"; 
			LogLevel.DEBUG(5, new Throwable(), "strSqlqry :" + strSqlqry);
			}			
			Vector LocationOrdVector=qm.executeQuery(strSqlqry);
			LogLevel.DEBUG(5,new Throwable(),"LocationOrdVector:"+LocationOrdVector);
			if(LocationOrdVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;Location</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<LocationOrdVector.size(); i++)
					{
						if(i==0)
						{
						out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;Location</option>");
						if(ordnumb.equals("ALL") || ordnumb == "ALL")
						{
						out.print("<option style='background:#FFF' value='ALL'>ALL</option>");
						}
						else
						{
						}
						}
						Hashtable ht=(Hashtable)LocationOrdVector.get(i);
						//veh_name_fetched =String.valueOf(ht.get("vehicle_name"));
						city =String.valueOf(ht.get("city"));
						out.print("<option style='background:#FFF' value='"+city+"'>"+city+"</option>"); 
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
* This method is to get with out ordered battery details details.
* @strSqlQry: carries the query to ordered battery details from battery_order_details table.
\* **************************************************************************************************************************************/
	public String getbatteryordernodetails(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";

	Hashtable htOptions=new Hashtable();

	String ordnumb = (req.getParameter("ordnumb")!=null)?(req.getParameter("ordnumb")):"0";
	LogLevel.DEBUG(5,new Throwable(),"ordnumb :"+ordnumb );

	String locations  = (req.getParameter("locations")!=null)?req.getParameter("locations").trim() :"";
	LogLevel.DEBUG(5, new Throwable(), "locations :"+ locations); 

	String strDateOpt  = (req.getParameter("dates")!=null)?req.getParameter("dates").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "strDateOpt :"+ strDateOpt);

	String txtFromDate  = (req.getParameter("txtFromDate")!=null)?req.getParameter("txtFromDate").trim():"";
	String txtToDate  = (req.getParameter("txtToDate")!=null)?req.getParameter("txtToDate").trim():"";

	
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

	LogLevel.DEBUG(5, new Throwable(), "ordnumb :"+ ordnumb);
	htOptions.put("ordnumb",ordnumb);

	LogLevel.DEBUG(5, new Throwable(), "locations :"+ locations);
	htOptions.put("locations",locations);
	 
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

	if(!ordnumb.equals("ALL") && !locations.equals("ALL"))
		{
		strConditions1= " and order_number='"+ordnumb+"' and city='"+locations+"' ";
		}
	else if(ordnumb.equals("ALL") && !locations.equals("ALL"))
		{
		strConditions1= " and city='"+locations+"'";
		}
	else if(ordnumb.equals("ALL") && locations.equals("ALL"))
		{
		
		}
	
	//String strSqlQry ="select distinct emp_id,time_slot,description,activity_category,emp_name,date(creation_date),date_format(creation_date, '%W %M %D %Y'), count(time_slot) as count from mis where "+strConditions+" and emp_name='"+strempname+"' and emp_id='"+strempid+"' group by date(creation_date),time_slot,activity_category order by emp_name asc";
	
	String strSqlQry="select order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailis,city,battery_brand,battery_model,price,pincode,pdfurl,bat_type,veh_name,veh_model,area,witholdbatprice,order_status,creation_date from battery_order_details where "+strConditions+" "+strConditions1+"  order by creation_date";
	LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);


	Vector BatOrdLocVector=qm.executeQuery(strSqlQry);
	LogLevel.DEBUG(5,new Throwable(),"BatOrdLocVector:"+ BatOrdLocVector );

	if(session.getAttribute("sesBatOrderLocVectorVector")!=null)
	session.removeAttribute("sesBatOrderLocVectorVector");
	session.setAttribute("sesBatOrderLocVectorVector",BatOrdLocVector);
	
	if(session.getAttribute("sesOptions")!=null)
	session.removeAttribute("sesOptions");
	session.setAttribute("sesOptions",htOptions);
	res.sendRedirect("../jsp/admin/batterystore/mis/batteryordernumber.jsp?txtFromDate="+txtFromDate+"&txtToDate="+txtToDate+"&ordnumb="+ordnumb+"&locations="+locations+"&dates="+strDateOpt);
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
			strSqlQry="select order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailis,city,battery_brand,battery_model,price,pincode,pdfurl,bat_type,veh_name,veh_model,area,witholdbatprice,order_status,creation_date,postpone_date,postpone_message from battery_order_details where date(postpone_date) between '"+strFromDate+"' and '"+strToDate+"' "+strConditions1+"  order by postpone_date";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

		}
		else if(keyword.equals("installed"))
		{
			strSqlQry="select order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailis,city,battery_brand,battery_model,price,pincode,pdfurl,bat_type,veh_name,veh_model,area,witholdbatprice,order_status,creation_date from battery_order_details where date(updated_date) between '"+strFromDate+"' and '"+strToDate+"' "+strConditions1+"  order by updated_date";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

		}
		else
		{	
			strSqlQry="select order_number,consumer_name,consumer_mobnumber,consumer_emailid,retailer_name,retailer_mobilnumber,retailer_emailis,city,battery_brand,battery_model,price,pincode,pdfurl,bat_type,veh_name,veh_model,area,witholdbatprice,order_status,creation_date from battery_order_details where "+strConditions+" "+strConditions1+"  order by creation_date";
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
	res.sendRedirect("../jsp/admin/batterystore/mis/confirmedordereddetails.jsp?txtFromDate="+txtFromDate+"&txtToDate="+txtToDate+"&city="+city+"&keyword="+keyword+"&dates="+strDateOpt);
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
	public String getbatteryhealthcheck(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";

	Hashtable htOptions=new Hashtable();
	String strDateOpt  = (req.getParameter("dates")!=null)?req.getParameter("dates").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "strDateOpt :"+ strDateOpt);

	String txtFromDate  = (req.getParameter("txtFromDate")!=null)?req.getParameter("txtFromDate").trim():"";
	String txtToDate  = (req.getParameter("txtToDate")!=null)?req.getParameter("txtToDate").trim():"";

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

	String bathealthChks = "Battery Health Check Details";

	LogLevel.DEBUG(5, new Throwable(), "bathealthChks :"+ bathealthChks);
	htOptions.put("bathealthChks",bathealthChks);

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
	
	//String strSqlQry="select bat_chk_id,bat_ins_month,bat_ins_year,veh_type,veh_model,bat_type,bat_brand,bat_model,customer_name,customer_mobilnumber,support_name,support_mobilnumber,city,area,pincode,creation_date from battery_health_check where "+strConditions+" order by creation_date";

	String strSqlQry="select bat_service_id,bat_ins_month,bat_ins_year,veh_type,veh_model,bat_type,bat_brand,bat_model,customer_name,customer_mobilnumber,support_name,support_mobilnumber,city,area,pincode,creation_date,service_need_date from battery_service where "+strConditions+" order by creation_date";


	LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);


	Vector BatHealthChkVector=qm.executeQuery(strSqlQry);
	LogLevel.DEBUG(5,new Throwable(),"BatHealthChkVector:"+ BatHealthChkVector );

	if(session.getAttribute("sesBatHealthChkVector")!=null)
	session.removeAttribute("sesBatHealthChkVector");
	session.setAttribute("sesBatHealthChkVector",BatHealthChkVector);
	
	if(session.getAttribute("sesOptions")!=null)
	session.removeAttribute("sesOptions");
	session.setAttribute("sesOptions",htOptions);
	res.sendRedirect("../jsp/admin/batterystore/mis/batteryhealthchksummary.jsp?txtFromDate="+txtFromDate+"&txtToDate="+txtToDate+"&dates="+strDateOpt);
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
	public String getregisterbattery(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";

	Hashtable htOptions=new Hashtable();
	String strDateOpt  = (req.getParameter("dates")!=null)?req.getParameter("dates").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "strDateOpt :"+ strDateOpt);

	String txtFromDate  = (req.getParameter("txtFromDate")!=null)?req.getParameter("txtFromDate").trim():"";
	String txtToDate  = (req.getParameter("txtToDate")!=null)?req.getParameter("txtToDate").trim():"";

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

	String bathealthChks = "Battery Registration Details";

	LogLevel.DEBUG(5, new Throwable(), "bathealthChks :"+ bathealthChks);
	htOptions.put("bathealthChks",bathealthChks);

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
	
	String strSqlQry="select bat_reg_id,bat_ins_month,bat_ins_year,bat_type,bat_brand,bat_model,veh_type,veh_model,customer_name,customer_mobilnumber,city,area,pincode,creation_date from battery_registration where "+strConditions+" order by creation_date";
	LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);


	Vector BatRegDetailsVector=qm.executeQuery(strSqlQry);
	LogLevel.DEBUG(5,new Throwable(),"BatRegDetailsVector:"+ BatRegDetailsVector );

	if(session.getAttribute("sesBatRegDetailsVector")!=null)
	session.removeAttribute("sesBatRegDetailsVector");
	session.setAttribute("sesBatRegDetailsVector",BatRegDetailsVector);
	
	if(session.getAttribute("sesOptions")!=null)
	session.removeAttribute("sesOptions");
	session.setAttribute("sesOptions",htOptions);
	res.sendRedirect("../jsp/admin/batterystore/mis/batteryregistrationsummary.jsp?txtFromDate="+txtFromDate+"&txtToDate="+txtToDate+"&dates="+strDateOpt);
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
	public String getlaptopbatterydetails(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";

	Hashtable htOptions=new Hashtable();
	String strDateOpt  = (req.getParameter("dates")!=null)?req.getParameter("dates").trim():"";
	LogLevel.DEBUG(5, new Throwable(), "strDateOpt :"+ strDateOpt);

	String txtFromDate  = (req.getParameter("txtFromDate")!=null)?req.getParameter("txtFromDate").trim():"";
	String txtToDate  = (req.getParameter("txtToDate")!=null)?req.getParameter("txtToDate").trim():"";

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

	String laptoporddetails = "Laptop Battery Ordered Details";

	LogLevel.DEBUG(5, new Throwable(), "laptoporddetails :"+ laptoporddetails);
	htOptions.put("laptoporddetails",laptoporddetails);

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
	
	String strSqlQry="select lap_bat_ord_id,consumer_mobnumber,battery_type,laptop_make,laptop_product,laptop_model,state,city,area,price,creation_date from laptop_battery_order_details where "+strConditions+"";
	LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);


	Vector LaptopBatteryDetailsVector=qm.executeQuery(strSqlQry);
	LogLevel.DEBUG(5,new Throwable(),"LaptopBatteryDetailsVector:"+ LaptopBatteryDetailsVector );

	if(session.getAttribute("sesLaptopBatOrdDetailsVector")!=null)
	session.removeAttribute("sesLaptopBatOrdDetailsVector");
	session.setAttribute("sesLaptopBatOrdDetailsVector",LaptopBatteryDetailsVector);
	
	if(session.getAttribute("sesOptions")!=null)
	session.removeAttribute("sesOptions");
	session.setAttribute("sesOptions",htOptions);
	res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/laptopbatorddetailssummary.jsp?txtFromDate="+txtFromDate+"&txtToDate="+txtToDate+"&dates="+strDateOpt);
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
