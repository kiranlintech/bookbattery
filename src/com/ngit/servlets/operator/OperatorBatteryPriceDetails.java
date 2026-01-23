/***********************************************************************		
	NGIT Confidential. 
	@File Name   : OperatorBatteryPriceDetails.java 
	@Description : This Servlet is used to fetch battery price details according model, state and city.
	@Author	     : Sai Krishna Daddala.
	@Date        : 26th Jan 2014.
******************************************************************/ 
 
package com.ngit.servlets.operator; 
 
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
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

  
public class OperatorBatteryPriceDetails extends HttpServlet 
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

	/* ****************************************************************************************\
	* This action is used to get battery brands.
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
	* This action is used to get battery brands.
	\* *****************************************************************************************/		
		  if(strWhatToDo.equalsIgnoreCase("getbatterymodelsdetails"))
			{ 
			try
			{
				String strRes=getbatterymodelsdetails(req,res,session);
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
	* This action is used to get inverter capacities.
	\* *****************************************************************************************/		
		  if(strWhatToDo.equalsIgnoreCase("getinvertercapacity"))
			{ 
			try
			{
				String strRes=getinvertercapacity(req,res,session);
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
		  if(strWhatToDo.equalsIgnoreCase("getstates"))
			{ 
			try
			{
				String strRes=getstates(req,res,session);
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
		  if(strWhatToDo.equalsIgnoreCase("getcities"))
			{ 
			try
			{
				String strRes=getcities(req,res,session);
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
	* This action is used to get states
	\* *****************************************************************************************/		
		  if(strWhatToDo.equalsIgnoreCase("getinverterstates"))
			{ 
			try
			{
				String strRes=getinverterstates(req,res,session);
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
	* This action is used to get cities
	\* *****************************************************************************************/		
		  if(strWhatToDo.equalsIgnoreCase("getinvertercities"))
			{ 
			try
			{
				String strRes=getinvertercities(req,res,session);
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
		  if(strWhatToDo.equalsIgnoreCase("getbatteryprices"))
			{ 
			try
			{
				String strRes=getbatteryprices(req,res,session);
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
		  if(strWhatToDo.equalsIgnoreCase("getbatterypricesdetails"))
			{ 
			try
			{
				String strRes=getbatterypricesdetails(req,res,session);
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
	* This action is used to get inverter prices based on state and city
	\* *****************************************************************************************/		
		  if(strWhatToDo.equalsIgnoreCase("getinverterprices"))
			{ 
			try
			{
				String strRes=getinverterprices(req,res,session);
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
		  if(strWhatToDo.equalsIgnoreCase("getbatbrands"))
			{ 
			try
			{
				String strRes=getbatbrands(req,res,session);
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
	* This method is to get battery models.
	* @strSqlQry : carries the query to battery models details in battery_details table.
	\* **************************************************************************************************************************************/
	public String getbatbrands(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
		String keyword= (req.getParameter("keyword")!=null)?(req.getParameter("keyword")):"";
	String strRes="";
	String strSqlQryBrand ="";
		try
		{
		if(keyword.equals("battery"))
		  {
			strSqlQryBrand ="select distinct(bat_brand) from batteryprice order by bat_brand asc";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQryBrand :" + strSqlQryBrand);
		  }
		  else if(keyword.equals("inverter"))
		  {
			strSqlQryBrand ="select distinct(inverter_brand) from inverter_price_details order by inverter_brand asc";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQryBrand :" + strSqlQryBrand);
		  }

		Vector BatBrandVector=qm.executeQuery(strSqlQryBrand);
		LogLevel.DEBUG(5,new Throwable(),"BatBrandVector:"+BatBrandVector );
			
		if(BatBrandVector.isEmpty())
		{ 
			LogLevel.DEBUG(1,new Throwable(),"Failed to fetch brands");
			session.setAttribute("priority","1");
			session.setAttribute("sesbatterydetailsErrorMsg", "<font color='#ff3333' class='vrb10'>Failed to fetch brands! </font> ");
			session.removeAttribute("BatBrandVector");
			res.sendRedirect("/bookbattery/jsp/operator/findbatteryprice/findbatteryprice.jsp");
			return strRes;
		}
		else
		{
			if(keyword.equals("battery"))
			{
			LogLevel.DEBUG(1, new Throwable(),"Successfully Fetched brands");
			session.setAttribute("BatBrandVector", BatBrandVector);
			//session.setAttribute("BatteryLocalTaxVector", strcustax);
			res.sendRedirect("/bookbattery/jsp/operator/findbatteryprice/findbatteryprice.jsp");
			return strRes;
			}
			else if(keyword.equals("inverter"))
			{
			LogLevel.DEBUG(1, new Throwable(),"Successfully Fetched brands");
			session.setAttribute("BatBrandVector", BatBrandVector);
			//session.setAttribute("BatteryLocalTaxVector", strcustax);
			res.sendRedirect("/bookbattery/jsp/operator/inverter/inverterpricedetails.jsp");
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
	* This method is to get battery models.
	* @strSqlQry : carries the query to battery models details in battery_details table.
	\* **************************************************************************************************************************************/
	public String getbatterymodelsdetails(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
		String strSqlQryModel ="select distinct(bat_model) from batteryprice order by bat_model asc";
		LogLevel.DEBUG(5, new Throwable(), "strSqlQryModel :" + strSqlQryModel);

		Vector BatModelVector=qm.executeQuery(strSqlQryModel);
		LogLevel.DEBUG(5,new Throwable(),"BatModelVector:"+BatModelVector );
			
		if(BatModelVector.isEmpty())
		{ 
			LogLevel.DEBUG(1,new Throwable(),"Failed to fetch states");
			session.setAttribute("priority","1");
			session.setAttribute("sesbatterydetailsErrorMsg", "<font color='#ff3333' class='vrb10'>Failed to fetch states! </font> ");
			session.removeAttribute("BatModelVector");
			res.sendRedirect(baseURL+"/checkwarranty.jsp");
			return strRes;
		}
		else
		{
			LogLevel.DEBUG(1, new Throwable(),"Successfully Fetched States");
			session.setAttribute("sesBatModelVector", BatModelVector);
			//session.setAttribute("BatteryLocalTaxVector", strcustax);
			res.sendRedirect(baseURL+"/checkwarranty.jsp");
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
	* This method is to get vehicle name.
	* @strSqlQry : carries the query to battery details in battery_details table.
	\* **************************************************************************************************************************************/
	public String getbatterymodels(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String brand= (req.getParameter("brand")!=null)?(req.getParameter("brand")):"";
			String batmodel="";	

	 		ServletOutputStream out=res.getOutputStream();

			String strgetmodel = "select distinct(bat_model) from batteryprice where bat_brand='"+brand+"' order by bat_model asc"; 
			LogLevel.DEBUG(5, new Throwable(), "strgetmodel :" + strgetmodel);
			
			Vector BatModelVector=qm.executeQuery(strgetmodel);
			LogLevel.DEBUG(5,new Throwable(),"BatModelVector:"+BatModelVector);
			if(BatModelVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&lt;-&nbsp;&nbsp; Select Model -&gt;</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<BatModelVector.size(); i++)
					{
						if(i==0)
						{
						out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&lt;-&nbsp;&nbsp; Select Model -&gt;</option>");
						}
						Hashtable ht=(Hashtable)BatModelVector.get(i);
						batmodel =String.valueOf(ht.get("bat_model"));
						LogLevel.DEBUG(5,new Throwable(),"batmodel:"+batmodel);
						out.print("<option style='background:#FFF' value='"+batmodel+"'>"+batmodel+"</option>"); 
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
	* This method is to get vehicle name.
	* @strSqlQry : carries the query to battery details in battery_details table.
	\* **************************************************************************************************************************************/
	public String getinvertercapacity(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String brand= (req.getParameter("brand")!=null)?(req.getParameter("brand")):"";
		String strRes="";
		try
		  {	
			String invcapacity="";	

			ServletOutputStream out=res.getOutputStream();

			String strgetinvcapacity = "select distinct(inverter_capacity) from inverter_price_details where inverter_brand='"+brand+"' order by inverter_capacity asc"; 
			LogLevel.DEBUG(5, new Throwable(), "strgetinvcapacity :" + strgetinvcapacity);
			
			Vector InvCapacityVector=qm.executeQuery(strgetinvcapacity);
			LogLevel.DEBUG(5,new Throwable(),"InvCapacityVector:"+InvCapacityVector);
			if(InvCapacityVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&lt;-&nbsp;&nbsp; Select Capacity -&gt;</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<InvCapacityVector.size(); i++)
					{
						if(i==0)
						{
						out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&lt;-&nbsp;&nbsp; Select Capacity -&gt;</option>");
						}
						Hashtable ht=(Hashtable)InvCapacityVector.get(i);
						invcapacity =String.valueOf(ht.get("inverter_capacity"));
						LogLevel.DEBUG(5,new Throwable(),"invcapacity:"+invcapacity);
						out.print("<option style='background:#FFF' value='"+invcapacity+"'>"+invcapacity+"</option>"); 
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
	* This method is to get vehicle name.
	* @strSqlQry : carries the query to battery details in battery_details table.
	\* **************************************************************************************************************************************/
	public String getstates(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String model= (req.getParameter("model")!=null)?(req.getParameter("model")):"";
			String state="";	

	 		ServletOutputStream out=res.getOutputStream();

			String strgetstate = "select distinct(state) from batteryprice where bat_model='"+model+"' order by state asc"; 
			LogLevel.DEBUG(5, new Throwable(), "strgetstate :" + strgetstate);
			
			Vector getstate=qm.executeQuery(strgetstate);
			LogLevel.DEBUG(5,new Throwable(),"getstate:"+getstate);
			if(getstate.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&lt;-&nbsp;&nbsp; Select State -&gt;</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<getstate.size(); i++)
					{
						if(i==0)
						{
						out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&lt;-&nbsp;&nbsp; Select State -&gt;</option>");
						}
						Hashtable ht=(Hashtable)getstate.get(i);
						state =String.valueOf(ht.get("state"));
						LogLevel.DEBUG(5,new Throwable(),"state:"+state);
						out.print("<option style='background:#FFF' value='"+state+"'>"+state+"</option>"); 
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
	/* ********************************************************
	* This method is to get states from inverter _price_details table
	* 
	\* ******************************************************/
	public String getinverterstates(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String capacity= (req.getParameter("capacity")!=null)?(req.getParameter("capacity")):"";
			String state="";	

	 		ServletOutputStream out=res.getOutputStream();

			String strgetstate = "select distinct(state) from inverter_price_details where inverter_capacity='"+capacity+"' order by state asc"; 
			LogLevel.DEBUG(5, new Throwable(), "strgetstate :" + strgetstate);
			
			Vector getstate=qm.executeQuery(strgetstate);
			LogLevel.DEBUG(5,new Throwable(),"getstate:"+getstate);
			if(getstate.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&lt;-&nbsp;&nbsp; Select State -&gt;</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<getstate.size(); i++)
					{
						if(i==0)
						{
						out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&lt;-&nbsp;&nbsp; Select State -&gt;</option>");
						}
						Hashtable ht=(Hashtable)getstate.get(i);
						state =String.valueOf(ht.get("state"));
						LogLevel.DEBUG(5,new Throwable(),"state:"+state);
						out.print("<option style='background:#FFF' value='"+state+"'>"+state+"</option>"); 
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
	* This method is to get vehicle name.
	* @strSqlQry : carries the query to battery details in battery_details table.
	\* **************************************************************************************************************************************/
	public String getcities(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String state= (req.getParameter("state")!=null)?(req.getParameter("state")):"";
			String city="";	

	 		ServletOutputStream out=res.getOutputStream();

			String strgetcity = "select distinct(city) from batteryprice where state='"+state+"' order by city asc"; 
			LogLevel.DEBUG(5, new Throwable(), "strgetcity :" + strgetcity);
			
			Vector getcity=qm.executeQuery(strgetcity);
			LogLevel.DEBUG(5,new Throwable(),"getcity:"+getcity);
			if(getcity.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&lt;-&nbsp;&nbsp; Select City -&gt;</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<getcity.size(); i++)
					{
						if(i==0)
						{
						out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&lt;-&nbsp;&nbsp; Select City -&gt;</option>");
						}
						Hashtable ht=(Hashtable)getcity.get(i);
						city =String.valueOf(ht.get("city"));
						LogLevel.DEBUG(5,new Throwable(),"city:"+city);
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
	/* **************************************************
	* This method is to get cities from inverter_price_details
	**************************************************/
	public String getinvertercities(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String state= (req.getParameter("state")!=null)?(req.getParameter("state")):"";
			String city="";	

	 		ServletOutputStream out=res.getOutputStream();

			String strgetcity = "select distinct(city) from inverter_price_details where state='"+state+"' order by city asc"; 
			LogLevel.DEBUG(5, new Throwable(), "strgetcity :" + strgetcity);
			
			Vector getcity=qm.executeQuery(strgetcity);
			LogLevel.DEBUG(5,new Throwable(),"getcity:"+getcity);
			if(getcity.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&lt;-&nbsp;&nbsp; Select City -&gt;</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<getcity.size(); i++)
					{
						if(i==0)
						{
						out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&lt;-&nbsp;&nbsp; Select City -&gt;</option>");
						}
						Hashtable ht=(Hashtable)getcity.get(i);
						city =String.valueOf(ht.get("city"));
						LogLevel.DEBUG(5,new Throwable(),"city:"+city);
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
	* This method is to get vehicle name.
	* @strSqlQry : carries the query to battery details in battery_details table.
	\* **************************************************************************************************************************************/
	public String getbatteryprices(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
	
	String model= (req.getParameter("model")!=null)?(req.getParameter("model")):"";
	LogLevel.DEBUG(5,new Throwable(),"model :"+model );

	String state = (req.getParameter("state")!=null)?(req.getParameter("state")):"";
	LogLevel.DEBUG(5,new Throwable(),"state :"+state );

	String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
	LogLevel.DEBUG(5,new Throwable(),"city :"+city );
	String strRes="";
	try
	  {  
		ServletOutputStream out=res.getOutputStream();
	   /* @strSqlQry3 : carries the query to Insert the user Work details into professional_detl table.*/
		String strSqlQrygetprice = "select bat_act_price,bat_witbat_price,bat_ret_price from batteryprice where state='"+state+"' and city='"+city+"' and bat_model='"+model+"'";
		LogLevel.DEBUG(5,new Throwable(),"strSqlQrygetprice :"+strSqlQrygetprice );

		Vector batteryprice=qm.executeQuery(strSqlQrygetprice);
		LogLevel.DEBUG(5,new Throwable(),"batteryprice:"+batteryprice);
		strRes="<table  align='center' width='100%' border='0'>";
							
		if(batteryprice==null || batteryprice.size() == 0)
		  {
			out.println("<p align='center' class='insidecontent'>No Prices are Avaliable based on selection!<a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
			return strRes;
		  }
		 else
		  {
			strRes=strRes+"<table border='0' align='center' bgcolor='#FFFFFF' width='700'><tr>";
			//strRes=strRes+"<td align='center'  valign='top'><input type='button' onClick=\"javascript:deleteappichatdetails();\" value='DELETE' class='button4'></td></tr></table>";
					
			strRes=strRes+"<table border='0' cellspacing='1' align='center' cellpadding='2' bgcolor='#FFFFFF' width='100%'><tr bgcolor='#2364b1'><td class='prodheading' width='18%'>Model</td><td  class='prodheading' width='20%'>State</td><td class='prodheading' width='15%'>City</td><td class='prodheading' width='10%'>MRP</td><td class='prodheading' width='19%'>WithOutOldBatPrice</td><td class='prodheading' width='18%'>WithOldBatPrice</td></tr>";
			for(int j=0; j<batteryprice.size();j++)
			{
				Hashtable ht3=(Hashtable)batteryprice.get(j);
				String stractprice=String.valueOf(ht3.get("bat_act_price"));
				String strdisprice=String.valueOf(ht3.get("bat_witbat_price"));
				String strreturnprice=String.valueOf(ht3.get("bat_ret_price"));
				
				int strdiscountprice = Integer.parseInt(strdisprice);
				int strwitbatprice = Integer.parseInt(strreturnprice);

				int strwithoutprice = strdiscountprice - strwitbatprice;
				LogLevel.DEBUG(5,new Throwable(),"dddddddhhhhh :"+strwithoutprice );
								
				strRes=strRes+"<table bgcolor='#FFFFFF' width='100%' cellspacing='1' border='0' align='center'  cellpadding='2'>";
				strRes=strRes+"<td width='18%'   class='table1 align='left'  >"+model+"</td>";
				strRes=strRes+"<td  width='20%'  class='table1 align='left'  >"+state+"</td>";
				strRes=strRes+"<td  width='15%'  class='table1 align='left'>"+city+"</td>";
				strRes=strRes+"<td  width='10%'  class='table1 align='left'  >"+stractprice+"</td>";
				strRes=strRes+"<td  width='19%'  class='table1 align='left'  >"+strdisprice+"</td>";
				strRes=strRes+"<td  width='18%'  class='table1 align='left'  >"+strwithoutprice+"</td></tr>";
				strRes=strRes+"</table>";
				strRes=strRes+"</table>";
			} 
		}
		//out.println(strRes);
	   }
	 catch (Exception e)
		{
			LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
			e.printStackTrace();
		}
		return strRes;
	}

	/* **************************************************************************************************************************************\
	* This method is to get vehicle name.
	* @strSqlQry : carries the query to battery details in battery_details table.
	\* **************************************************************************************************************************************/
	public String getbatterypricesdetails(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
	
	String model= (req.getParameter("model")!=null)?(req.getParameter("model")):"";
	LogLevel.DEBUG(5,new Throwable(),"model :"+model );

	String strRes="";
	try
	  {  
		ServletOutputStream out=res.getOutputStream();
	   /* @strSqlQry3 : carries the query to Insert the user Work details into professional_detl table.*/
		String strSqlQrygetprice = "select bat_brand,bat_model,bat_warranty,bat_capacity,icon_name,icon_url from battery_details where bat_model='"+model+"'";
		LogLevel.DEBUG(5,new Throwable(),"strSqlQrygetprice :"+strSqlQrygetprice );

		Vector batteryprice=qm.executeQuery(strSqlQrygetprice);
		LogLevel.DEBUG(5,new Throwable(),"batteryprice:"+batteryprice);
		strRes="<table  align='center' width='100%' border='0'>";
							
		if(batteryprice==null || batteryprice.size() == 0)
		  {
			out.println("<p align='center' class='insidecontent'>No Prices are Avaliable based on selection!<a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
			return strRes;
		  }
		 else
		  {
			strRes=strRes+"<table border='0' align='center' bgcolor='#FFFFFF' width='650'><tr>";
			//strRes=strRes+"<td align='center'  valign='top'><input type='button' onClick=\"javascript:deleteappichatdetails();\" value='DELETE' class='button4'></td></tr></table>";
					
			strRes=strRes+"<table border='0' style='border: 1px solid orange;border-collapse: collapse;margin-top:15px;' cellspacing='0' align='center' cellpadding='2' bgcolor='#FFFFFF' width='80%'><tr bgcolor='#FFFFFF'><td class='prodheadingbat' width='26.6%' align='center' style='border: 1px solid orange;border-collapse: collapse;'>Battery</td><td  class='prodheadingbat' width='26.6%' align='center' style='border: 1px solid orange;border-collapse: collapse;'>Warranty</td><td class='prodheadingbat' width='26.6%' align='center'>Capacity (Ah)</td></tr>";
			for(int j=0; j<batteryprice.size();j++)
			{
				Hashtable ht3=(Hashtable)batteryprice.get(j);
				String batterybrand=String.valueOf(ht3.get("bat_brand"));
				LogLevel.DEBUG(5,new Throwable(),"batterybrand :"+batterybrand );
				String batterymodel=String.valueOf(ht3.get("bat_model"));
				LogLevel.DEBUG(5,new Throwable(),"batterymodel :"+batterymodel );
				String warranty=String.valueOf(ht3.get("bat_warranty"));
				LogLevel.DEBUG(5,new Throwable(),"warranty :"+warranty );
				String capacity=String.valueOf(ht3.get("bat_capacity"));
				LogLevel.DEBUG(5,new Throwable(),"capacity :"+capacity );
				String iconname=String.valueOf(ht3.get("icon_name"));
				LogLevel.DEBUG(5,new Throwable(),"iconname :"+iconname );
				String iconurl=String.valueOf(ht3.get("icon_url"));
				String ThumbIconPath = "";
						if(iconurl == null)
							{
								iconurl = "../images/noimage.jpg";
							}
							else
							{
								iconurl =iconurl;
							}
				LogLevel.DEBUG(5,new Throwable(),"iconurl :"+iconurl );

				strRes=strRes+"<tr><td  width='26.6%'  bgcolor='#FFFFFF' align='center'  style='border: 1px solid orange;border-collapse: collapse;'><img  src="+iconurl+" width='120' height='120'/> <br/><b>"+batterybrand+"</b><br/><b>"+model+"</b></td>";
				strRes=strRes+"<td  width='26.6%'  bgcolor='#FFFFFF' align='center' style='border: 1px solid orange;border-collapse: collapse;'><b>"+warranty+"</b></td>";
				strRes=strRes+"<td  width='26.6%'  bgcolor='#FFFFFF' align='center'  style='border: 1px solid orange;border-collapse: collapse;'><b>"+capacity+"</b></td></tr>";
				strRes=strRes+"</table>";
				strRes=strRes+"</table>";
			} 
		}
		//out.println(strRes);
	   }
	 catch (Exception e)
		{
			LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
			e.printStackTrace();
		}
		return strRes;
	}

	/* ******************************************************\
	* This method is to get inverter prices from inverter_price_details
	
	\* *****************************************************/
	public String getinverterprices(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
	String brand= (req.getParameter("brand")!=null)?(req.getParameter("brand")):"";
	LogLevel.DEBUG(5,new Throwable(),"brand :"+brand );
	
	String capacity= (req.getParameter("capacity")!=null)?(req.getParameter("capacity")):"";
	LogLevel.DEBUG(5,new Throwable(),"capacity :"+capacity );

	String state = (req.getParameter("state")!=null)?(req.getParameter("state")):"";
	LogLevel.DEBUG(5,new Throwable(),"state :"+state );

	String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
	LogLevel.DEBUG(5,new Throwable(),"city :"+city );
	String strRes="";
	try
	  {  
		ServletOutputStream out=res.getOutputStream();
	   /* @strSqlQry3 : carries the query to Insert the user Work details into professional_detl table.*/
		String strSqlQrygetprice = "select inverter_actual_price,inverter_discount_price from inverter_price_details where state='"+state+"' and city='"+city+"' and inverter_capacity='"+capacity+"' and inverter_brand='"+brand+"'";
		LogLevel.DEBUG(5,new Throwable(),"strSqlQrygetprice :"+strSqlQrygetprice );

		Vector inverterprice=qm.executeQuery(strSqlQrygetprice);
		LogLevel.DEBUG(5,new Throwable(),"inverterprice:"+inverterprice);
		strRes="<table  align='center' width='100%' border='0'>";
							
		if(inverterprice==null || inverterprice.size() == 0)
		  {
			out.println("<p align='center' class='insidecontent'>No Prices are Avaliable based on selection!<a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
			return strRes;
		  }
		 else
		  {
			strRes=strRes+"<table border='0' align='center' bgcolor='#FFFFFF' width='700'><tr>";
			//strRes=strRes+"<td align='center'  valign='top'><input type='button' onClick=\"javascript:deleteappichatdetails();\" value='DELETE' class='button4'></td></tr></table>";
					
			strRes=strRes+"<table border='0' cellspacing='1' align='center' cellpadding='2' bgcolor='#FFFFFF' width='100%'><tr bgcolor='#2364b1'><td class='prodheading' width='20%'>Capacity</td><td  class='prodheading' width='20%'>State</td><td class='prodheading' width='20%'>City</td><td class='prodheading' width='20%'>MRP</td><td class='prodheading' width='20%'>DiscountPrice</td></tr>";
			for(int j=0; j<inverterprice.size();j++)
			{
				Hashtable ht3=(Hashtable)inverterprice.get(j);
				String stractprice=String.valueOf(ht3.get("inverter_actual_price"));
				String strdisprice=String.valueOf(ht3.get("inverter_discount_price"));
				
				int strdiscountprice = Integer.parseInt(strdisprice);
				int stractualprice = Integer.parseInt(stractprice);
							
				strRes=strRes+"<table bgcolor='#FFFFFF' width='100%' cellspacing='1' border='0' align='center'  cellpadding='2'>";
				strRes=strRes+"<td width='20%'   class='table1 align='left'  >"+capacity+"</td>";
				strRes=strRes+"<td  width='20%'  class='table1 align='left'  >"+state+"</td>";
				strRes=strRes+"<td  width='20%'  class='table1 align='left'>"+city+"</td>";
				strRes=strRes+"<td  width='20%'  class='table1 align='left'  >"+stractprice+"</td>";
				strRes=strRes+"<td  width='20%'  class='table1 align='left'  >"+strdisprice+"</td>";
				strRes=strRes+"</table>";
				strRes=strRes+"</table>";
			} 
		}
		//out.println(strRes);
	   }
	 catch (Exception e)
		{
			LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
			e.printStackTrace();
		}
		return strRes;
	}

}// end of class
