/***********************************************************************		
	NGIT Confidential. 
	@File Name   : LaptopApplicatioChartMapping.java 
	@Description : This Servlet is used to select the Battery Details.
	@Author	     : Gowtham.K
	@Date        : 7th Feb 2014
******************************************************************/ 
 
package com.ngit.servlets.admin.batterystore; 
 
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

  
public class LaptopApplicationChartMapping extends HttpServlet 
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
		* This action is used to get vehicle name.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("getlaptopnames"))
			{
			try
			{
				String strRes=getlaptopnames(req,res,session);
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
	* This action is used to get vehicle model.
	\* *****************************************************************************************/		

	if(strWhatToDo.equalsIgnoreCase("getlaptop_model"))
			{ 
			try
			{
				String strRes=getlaptop_model(req,res,session);
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
	* This action is used to get laptop product.
	\* *****************************************************************************************/		

	if(strWhatToDo.equalsIgnoreCase("getlaptop_product"))
		{ 
			try
			{
				String strRes=getlaptop_product(req,res,session);
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
	* This action is used to get battery model.
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
	* This action is used to map battery details.
	\* *****************************************************************************************/		

	if(strWhatToDo.equalsIgnoreCase("insertapplicationchartmapping"))
			{ 
			try
			{
				String strRes=insertapplicationchartmapping(req,res,session);
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
	* This action is used to get the laptop battery brand details.
	\* *****************************************************************************************/		

	if(strWhatToDo.equalsIgnoreCase("getlaptopbrandtoedit"))
			{ 
			try
			{
				String strRes=getlaptopbrandtoedit(req,res,session);
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
	* This action is used to get the laptop battery models details.
	\* *****************************************************************************************/		

	if(strWhatToDo.equalsIgnoreCase("getlaptopbatterymodeltoedit"))
			{ 
			try
			{
				String strRes=getlaptopbatterymodeltoedit(req,res,session);
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
	* This action is used to get the application chart details details.
	\* *****************************************************************************************/		

	if(strWhatToDo.equalsIgnoreCase("getapplicationchartdetailstoedit"))
			{ 
			try
			{
				String strRes=getapplicationchartdetailstoedit(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);
				out.close();
			}
			catch (Exception e)
			{										
			LogLevel.ERROR(1, e, "Error :" + e);
			}	
	      }
		  /**This methos is used to update status in application chart details**/
		if(strWhatToDo.equalsIgnoreCase("updateapplicationchartdetails"))
		{ 
			try
			{
				String strRes=updateapplicationchartdetails(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);
				out.close();
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
	    }
		if(strWhatToDo.equalsIgnoreCase("deleteapplicationchartdetails"))
		{ 
			try
			{
				String strRes=deleteapplicationchartdetails(req,res,session);
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
	* This method is to get vehicle name.
	* @strSqlQry : carries the query to battery details in battery_details table.
	\* **************************************************************************************************************************************/
	public String getlaptopnames(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String batterytype= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";

			if(batterytype.equals(0) || batterytype.equals("0"))
			{
				batterytype = "0,default";
			}
			else
			{
				batterytype=batterytype;
			}

			String[] battertype = batterytype.split(",");
			String battypeids=battertype[0];
			String batterytypes=battertype[1];

			//String veh_id_fetched="";
			String laptop_name="";	

	 		ServletOutputStream out=res.getOutputStream();

			String strlaptopname = "select distinct(laptop_name) from laptop_details where battery_type='"+batterytypes+"' order by laptop_name asc"; 
			LogLevel.DEBUG(5, new Throwable(), "strlaptopname :" + strlaptopname);
			
			Vector laptopvector=qm.executeQuery(strlaptopname);
			LogLevel.DEBUG(5,new Throwable(),"laptopvector:"+laptopvector);
			if(laptopvector.isEmpty())
			{ 
				out.println("<option value='defaultss'><font color='#00dd00'><- Select Laptop Make -></font></option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<laptopvector.size(); i++)
					{
						if(i==0)
						{
						out.print("<option value='default'><font color='#00CC00'><- Select Laptop Make -></font></option>");
						}
						Hashtable ht=(Hashtable)laptopvector.get(i);
						//veh_id_fetched =String.valueOf(ht.get("veh_id"));
						//LogLevel.DEBUG(5,new Throwable(),"veh_id_fetched:"+veh_id_fetched);
						laptop_name =String.valueOf(ht.get("laptop_name"));
						LogLevel.DEBUG(5,new Throwable(),"laptop_name:"+laptop_name);
						out.print("<option value='"+laptop_name+"'>"+laptop_name+"</option>"); 
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
	* This method is to get vehicle model.
	* @strSqlQry : carries the query to vehicle model details in vehicle_details table.
	\* **************************************************************************************************************************************/

public String getlaptop_model(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String laptopname= (req.getParameter("laptopname")!=null)?(req.getParameter("laptopname")):"";
			String batterytype= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";

			String[] battertype = batterytype.split(",");
			String battypeids=battertype[0];
			String batterytypes=battertype[1];

			String laptop_id="";
			String laptop_model="";	

	 		ServletOutputStream out=res.getOutputStream();

			String strlaptopidqry = "select distinct(laptop_model) from laptop_details where laptop_name='"+laptopname+"' and battery_type='"+batterytypes+"' order by laptop_model asc"; 
			LogLevel.DEBUG(5, new Throwable(), "strlaptopidqry :" + strlaptopidqry);
			
			Vector laptopidvect=qm.executeQuery(strlaptopidqry);
			LogLevel.DEBUG(5,new Throwable(),"laptopidvect:"+laptopidvect);
			if(laptopidvect.isEmpty())
			{ 
				out.println("<option value='defaultss'><font color='#00dd00'><- Select Laptop Model -></font></option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<laptopidvect.size(); i++)
					{
						if(i==0)
						{
						out.print("<option value='default'><font color='#00CC00'><- Select Laptop Model -></font></option>");
						}
						Hashtable ht=(Hashtable)laptopidvect.get(i);
						laptop_model =String.valueOf(ht.get("laptop_model"));
						out.print("<option value='"+laptop_model+"'>"+laptop_model+"</option>"); 
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
	* This method is to get laptop products.
	* @strSqlQry : carries the query to fetch laptop products details in laptop_details table.
	\* **************************************************************************************************************************************/

	public String getlaptop_product(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		try
		{	
			String laptopname= (req.getParameter("laptopname")!=null)?(req.getParameter("laptopname")):"";
			String laptopmodel= (req.getParameter("laptopmodel")!=null)?(req.getParameter("laptopmodel")):"";
			String batterytype= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";

			String[] battertype = batterytype.split(",");
			String battypeids=battertype[0];
			String batterytypes=battertype[1];

			

			String laptop_id="";
			String laptop_product="";	

	 		ServletOutputStream out=res.getOutputStream();

			String strlaptopidqry = "select laptop_id,laptop_product from laptop_details where laptop_name='"+laptopname+"' and laptop_model='"+laptopmodel+"' and battery_type='"+batterytypes+"' order by laptop_product asc"; 
			LogLevel.DEBUG(5, new Throwable(), "strlaptopidqry :" + strlaptopidqry);
			
			Vector laptopidvect=qm.executeQuery(strlaptopidqry);
			LogLevel.DEBUG(5,new Throwable(),"laptopidvect:"+laptopidvect);
			if(laptopidvect.isEmpty())
			{ 
				out.println("<option value='defaultss'><font color='#00dd00'><- Select Laptop Product -></font></option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<laptopidvect.size(); i++)
					{
						if(i==0)
						{
						out.print("<option value='default'><font color='#00CC00'><- Select Laptop Product -></font></option>");
						}
						Hashtable ht=(Hashtable)laptopidvect.get(i);
						laptop_product =String.valueOf(ht.get("laptop_product"));
						laptop_id =String.valueOf(ht.get("laptop_id"));
						out.print("<option value='"+laptop_id+","+laptop_product+"'>"+laptop_product+"</option>"); 
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
	* This method is to get battery brands.
	* @strSqlQry : carries the query to laptop battery brands details in laptop_battery_details table.
	\* **************************************************************************************************************************************/

public String getbatterybrands(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
	 		ServletOutputStream out=res.getOutputStream();

			String SqlQuery = "select distinct(battery_brand) from laptop_battery_details"; 
			LogLevel.DEBUG(5, new Throwable(), "SqlQuery :" + SqlQuery);
			
			Vector battbrand=qm.executeQuery(SqlQuery);
			LogLevel.DEBUG(5,new Throwable(),"battbrand:"+battbrand);
			if(battbrand.isEmpty())
			{ 
				out.println("<table width='100%' border='0'><tr  class='#FFFFFF'><td width='95%' valign='middle' class='insidecontent'>No Battery Brands found</td></tr></table>");
				return strRes;
			}
			else
			{
				for ( int j=0; j<battbrand.size(); j++)
				{
				Hashtable ht2=(Hashtable)battbrand.get(j);
				String strbat_brand=(String)ht2.get("battery_brand");
				///String strbat_id = "2";
				
				strRes=strRes+"<table width='100%' border='0'><tr  class='#FFFFFF'><td width='5%'></td><td width='95%' valign='middle' class='insidecontent'><input type='checkbox' name='batteryban' value=''style='display:none'/><input type='checkbox' name='batteryban' value='"+strbat_brand+"' onclick='javascript:getbattertmod()' size='3' >"+strbat_brand+"</td></tr></table>";
				}
				//out.println(strRes);
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
	* This method is to get laptop battery models.
	* @strSqlQry : carries the query to laptop battery models details from laptop_battery_details table.
	\* **************************************************************************************************************************************/
	public String getbatterymodels(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
		String batterybrand= (req.getParameter("batterybrand")!=null)?(req.getParameter("batterybrand")):"";
		LogLevel.DEBUG(5, new Throwable(), "batterybrand :" + batterybrand);

		String strRes="";
		try
		{	
		ServletOutputStream out=res.getOutputStream();
		String[] tempArr=batterybrand.split(",");
		LogLevel.DEBUG(5,new Throwable(),"tempArr.length :"+tempArr.length );
		LogLevel.DEBUG(5,new Throwable(),"tempArr:"+tempArr);
		if(tempArr.length>0)
		{
			String strTempValue="";
			for (int k=0;k<tempArr.length ;k++ )
			{	
			strTempValue=tempArr[k];
			LogLevel.DEBUG(5,new Throwable(),"tempArr:"+strTempValue);
			String SqlQuery = "select distinct(battery_model) from laptop_battery_details where battery_brand='"+strTempValue+"'"; 
			LogLevel.DEBUG(5, new Throwable(), "SqlQuery :" + SqlQuery);
			
			Vector battmodel=qm.executeQuery(SqlQuery);
			LogLevel.DEBUG(5,new Throwable(),"battmodel:"+battmodel);
			if(battmodel.isEmpty())
			{ 
				out.println("<table width='100%' border='0'><tr  class='#FFFFFF'><td width='95%' valign='middle' class='insidecontent'>No Battery Models found</td></tr></table>");
				return strRes;
			}
			else
			{
				strRes=strRes+"<table  width='100%' border='0'><tr  class='insidecontent'><td width='5%'></td><td width='95%' valign='middle' class='insidecontent'><font color='1E5DA9'>"+strTempValue+"</font></td></tr></table>";

				for ( int j=0; j<battmodel.size(); j++)
				{
				Hashtable ht2=(Hashtable)battmodel.get(j);
				String strbat_model=(String)ht2.get("battery_model");
				
				strRes=strRes+"<table width='100%' border='0'><tr  class='#FFFFFF'><td width='5%'></td><td width='95%' valign='middle' class='insidecontent'><input type='checkbox' name='batterymodel' value='' style='display:none'/><input type='checkbox' name='batterymodel' onclick='javascript:getbatterymodels()'  value='"+strbat_model+"' size='5' >"+strbat_model+"</td></tr></table>";
				}
				//out.println(strRes);
			
			}
			}
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
	* This method is to map laptop battery details.
	* @strSqlQry : carries the query to map laptop battery details in laptop_application_chart_mapping table.
	\* **************************************************************************************************************************************/
	public String insertapplicationchartmapping(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		String strbatterytype = (req.getParameter("batterytype") != null) ? (req.getParameter("batterytype")) : "";
		String strlaptopmake = (req.getParameter("laptopmake") != null)? (req.getParameter("laptopmake")) : "";
		String strlaptopmodel = (req.getParameter("laptopmodel") != null)? (req.getParameter("laptopmodel")) : "";
		String strlaptopproduct = (req.getParameter("laptopproduct") != null)? (req.getParameter("laptopproduct")) : "";
			LogLevel.DEBUG(5,new Throwable(),"strlaptopproduct:"+strlaptopproduct);

		String strbatterbrand = (req.getParameter("batterbrand") != null)? (req.getParameter("batterbrand")) : "";
		String strbatterymodel = (req.getParameter("batterymodel") != null)? (req.getParameter("batterymodel")) : "";

		String[] batterytype = strbatterytype.split(","); 
		String battertypeid = batterytype[0];
		String battertypename = batterytype[1];

		

		String[] laptopproduct = strlaptopproduct.split(",");
		String laptoppid = laptopproduct[0];
		String lapproduct = laptopproduct[1];

		try
		  {	
				int result = 0;
				String[] tempArr=strbatterymodel.split(",");
				LogLevel.DEBUG(5,new Throwable(),"tempArr.length :"+tempArr.length );
				LogLevel.DEBUG(5,new Throwable(),"tempArr:"+tempArr);
				if(tempArr.length>0)
				{
					String strTempValue="";
					for (int k=0;k<tempArr.length ;k++ )
					{	
						strTempValue=tempArr[k];
						LogLevel.DEBUG(5,new Throwable(),"tempArr:"+strTempValue);
						String SqlQuery = "select battery_id,battery_model,battery_brand,battery_warranty,voltage,watt_hr,battery_cellcount,battery_actual_price,icon_url,amazonlink,battery_part_no,description from laptop_battery_details where battery_model='"+strTempValue+"'"; 
						LogLevel.DEBUG(5, new Throwable(), "SqlQuery :" + SqlQuery);

						Vector battmodel=qm.executeQuery(SqlQuery);
						LogLevel.DEBUG(5,new Throwable(),"battmodel:"+battmodel);

						for ( int j=0; j<battmodel.size(); j++)
						{
							Hashtable ht2=(Hashtable)battmodel.get(j);
							String battery_id =String.valueOf(ht2.get("battery_id"));							
							String battery_model=(String)ht2.get("battery_model");
							LogLevel.DEBUG(5,new Throwable(),"battery_model :"+battery_model);
							String battery_brand=(String)ht2.get("battery_brand");
							String battery_warranty=(String)ht2.get("battery_warranty");
							String battery_cellcount=(String)ht2.get("battery_cellcount");
							String voltage=(String)ht2.get("voltage");
							String watt_hr=(String)ht2.get("watt_hr");
							String batterypartno=(String)ht2.get("battery_part_no");
							String battery_actual_price =String.valueOf(ht2.get("battery_actual_price"));
							String description=(String)ht2.get("description");
							String icon_url=(String)ht2.get("icon_url"); 
							String amazonlink=(String)ht2.get("amazonlink");

							
							String StrSqlQery = "select battery_model from laptop_application_chart_mapping where battery_type='"+battertypename+"' and laptop_name='"+strlaptopmake+"' and laptop_model='"+strlaptopmodel+"' and laptop_product='"+lapproduct+"' and battery_brand='"+battery_brand+"' and battery_model='"+strTempValue+"'";
							LogLevel.DEBUG(5,new Throwable(),"StrSqlQery :"+StrSqlQery);

							Hashtable htapp = qm.getRow(StrSqlQery);
							String strbat_model=(String)htapp.get("battery_model");
							LogLevel.DEBUG(5,new Throwable(),"strbat_model :"+strbat_model);

							if(htapp.isEmpty())
							{ 
								/* Query to insert the values entered battery details into application mapping table. */   
								String SteSqlQry = "insert into laptop_application_chart_mapping(map_id,batterytype_id,laptop_id,battery_id,battery_brand,battery_model,laptop_name,laptop_model,laptop_product,battery_type,battery_cellcount,battery_warranty,battery_actual_price,voltage,watt_hr,icon_url,amazonlink,battery_part_no,description,creation_date,created_by) values(NULL,'"+battertypeid+"','"+laptoppid+"','"+battery_id+"','"+battery_brand+"','"+battery_model+"','"+strlaptopmake+"','"+strlaptopmodel+"','"+lapproduct+"','"+battertypename+"','"+battery_cellcount+"','"+battery_warranty+"','"+battery_actual_price+"','"+voltage+"','"+watt_hr+"','"+icon_url+"','"+amazonlink+"','"+batterypartno+"','"+description+"',now(),'ngit')";
								
								result=qm.executeUpdate(SteSqlQry);
								LogLevel.DEBUG(5,new Throwable(),"result :"+result );
							}
							else
							{
							}
						
						}
					}
				}				 
				if(result < 0)
				{
					LogLevel.DEBUG(1,new Throwable()," Failed to Add application chart mapping details! ");
					session.setAttribute("priority","1");
					session.setAttribute("sesmapErrorMsg", "<font color='#ff3333' class='top1'>Failed to Add application chart mapping details! </font> ");
					res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/addlaptopapplicationchart.jsp");
					return strRes;
				}
				else
				{
					LogLevel.DEBUG(1,new Throwable(),"Successfully Added application chat mapping details! ");
					session.setAttribute("sesmapErrorMsg", "<font color='#000000' class='top1'>Successfully Added application chart mapping details!</font>");
					//session.setAttribute("sescontVector", reslt);
					res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/addlaptopapplicationchart.jsp");
					return strRes;
				}
				
			
		}
		catch(Exception e)
		{
			LogLevel.ERROR(0,e,"Problem Caught Exception"+e);
			e.printStackTrace();
			session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
		}
		return strRes;
	}
	/* **************************************************************************************************************************************\
	* This method is to get laptop battery  brands
	* @strSqlQry : carries the query to laptop battery brands details in laptop_battery_details table.
	\* **************************************************************************************************************************************/

	public String getlaptopbrandtoedit(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
		String strbatterytype = (req.getParameter("batterytype") != null)? (req.getParameter("batterytype")) : "";
		String strlaptopname = (req.getParameter("laptopname") != null)? (req.getParameter("laptopname")) : "";
		String strlaptopmodel = (req.getParameter("laptopmodel") != null)? (req.getParameter("laptopmodel")) : "";
		String strlaptopproduct = (req.getParameter("laptopproduct") != null)? (req.getParameter("laptopproduct")) : "";
		String strRes="";
		String[] batterytype = strbatterytype.split(","); 
		String battertypeid = batterytype[0];
		String battertypename = batterytype[1];

		String lapmodel="";
		String lapproduct="";

		if(strlaptopmodel.indexOf(",")!=-1)
		{
			String[] laptopmodel = strlaptopmodel.split(","); 
			String lapid = laptopmodel[0];
			lapmodel = laptopmodel[1];
		}
		else
		{
			lapmodel = strlaptopmodel;
		}
		if(strlaptopproduct.indexOf(",")!=-1)
		{
			String[] laptopproudct = strlaptopproduct.split(","); 
			String lappid = laptopproudct[0];
			lapproduct = laptopproudct[1];
		}
		else
		{
			lapproduct = strlaptopproduct;
		}
		try
		{	
			//String veh_id="";
			String strbrand="";	

	 		ServletOutputStream out=res.getOutputStream();

			String strbrandqry = "select distinct(battery_brand) from laptop_application_chart_mapping where battery_type='"+battertypename+"' and laptop_name='"+strlaptopname+"' and laptop_model='"+lapmodel+"' and laptop_product='"+lapproduct+"' order by battery_brand asc"; 
			LogLevel.DEBUG(5, new Throwable(), "strbrandqry :" + strbrandqry);
			
			Vector brandvect=qm.executeQuery(strbrandqry);
			LogLevel.DEBUG(5,new Throwable(),"brandvect:"+brandvect);
			if(brandvect.isEmpty())
			{ 
				out.println("<option value='defaultss'><font color='#00dd00'><- Select Laptop Brand -></font></option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<brandvect.size(); i++)
					{
						if(i==0)
						{
						out.print("<option value='default'><font color='#00CC00'><- Select Laptop Brand -></font></option>");
						}
						Hashtable ht=(Hashtable)brandvect.get(i);
						strbrand =String.valueOf(ht.get("battery_brand"));
						//veh_id =String.valueOf(ht.get("veh_id"));
						out.print("<option value='"+strbrand+"'>"+strbrand+"</option>"); 
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
	* This method is to get vehicle model.
	* @strSqlQry : carries the query to vehicle model details in vehicle_details table.
	\* **************************************************************************************************************************************/

public String getlaptopbatterymodeltoedit(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strbatterybrand = (req.getParameter("batterybrand") != null)? (req.getParameter("batterybrand")) : "";
	String strbatterytype = (req.getParameter("batterytype") != null)? (req.getParameter("batterytype")) : "";
	String strlaptopname = (req.getParameter("laptopname") != null)? (req.getParameter("laptopname")) : "";
	String strlaptopmodel = (req.getParameter("laptopmodel") != null)? (req.getParameter("laptopmodel")) : "";
		String strlaptopproduct = (req.getParameter("laptopproduct") != null)? (req.getParameter("laptopproduct")) : "";


	String[] batterytype = strbatterytype.split(","); 
	String battertypeid = batterytype[0];
	String battertypename = batterytype[1];

	String lapmodel="";
	String lapproduct="";

	if(strlaptopmodel.indexOf(",")!=-1)
	{
		String[] laptopmodel = strlaptopmodel.split(","); 
		String lapid = laptopmodel[0];
		lapmodel = laptopmodel[1];
	}
	else
	{
		lapmodel = strlaptopmodel;
	}
	if(strlaptopproduct.indexOf(",")!=-1)
	{
		String[] laptopproudct = strlaptopproduct.split(","); 
		String lappid = laptopproudct[0];
		lapproduct = laptopproudct[1];
	}
	else
	{
		lapproduct = strlaptopproduct;
	}
	Vector brandvect = new Vector();
	String strRes="";
		try
		{	
			//String veh_id="";
			String strmodel="";	

	 		ServletOutputStream out=res.getOutputStream();

			
			String strbrandqry = "select distinct(battery_model) from laptop_application_chart_mapping where battery_type='"+battertypename+"' and laptop_name='"+strlaptopname+"' and laptop_model='"+lapmodel+"' and laptop_product='"+lapproduct+"' and battery_brand='"+strbatterybrand+"' order by battery_model asc"; 
			LogLevel.DEBUG(5, new Throwable(), "strbrandqry :" + strbrandqry);
			
			brandvect=qm.executeQuery(strbrandqry);
			LogLevel.DEBUG(5,new Throwable(),"brandvect:"+brandvect);
			
			if(brandvect.isEmpty())
			{ 
				out.println("<option value='defaultss'><font color='#00dd00'><- Select Battery Model -></font></option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<brandvect.size(); i++)
					{
						if(i==0)
						{
						out.print("<option value='default'><font color='#00CC00'><- Select Battery Model -></font></option>");
						}
						Hashtable ht=(Hashtable)brandvect.get(i);
						strmodel =String.valueOf(ht.get("battery_model"));
						//veh_id =String.valueOf(ht.get("veh_id"));
						out.print("<option value='"+strmodel+"'>"+strmodel+"</option>"); 
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
	* This method is to get battery models.
	* @strSqlQry : carries the query to battery models details in battery_details table.
	\* **************************************************************************************************************************************/
	public String getapplicationchartdetailstoedit(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
		String batterytype= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
		LogLevel.DEBUG(5, new Throwable(), "batterytype :" + batterytype);

		String laptopmake= (req.getParameter("laptopmake")!=null)?(req.getParameter("laptopmake")):"";
		LogLevel.DEBUG(5, new Throwable(), "laptopmake :" + laptopmake);

		String laptopmodel= (req.getParameter("laptopmodel")!=null)?(req.getParameter("laptopmodel")):"";
		LogLevel.DEBUG(5, new Throwable(), "laptopmodel :" + laptopmodel);

		String laptopproduct= (req.getParameter("laptopproduct")!=null)?(req.getParameter("laptopproduct")):"";
		LogLevel.DEBUG(5, new Throwable(), "laptopproduct :" + laptopproduct);

		String batterybrand= (req.getParameter("batterybrand")!=null)?(req.getParameter("batterybrand")):"";
		LogLevel.DEBUG(5, new Throwable(), "batterybrand :" + batterybrand);

		String batterymodel= (req.getParameter("batterymodel")!=null)?(req.getParameter("batterymodel")):"";
		LogLevel.DEBUG(5, new Throwable(), "batterymodel :" + batterymodel);

		String[] strbatterytype = batterytype.split(","); 
		String battertypeid = strbatterytype[0];
		String battertypename = strbatterytype[1];

		//String[] strvehicemodel = vehicemodel.split(","); 
		//String vehid = strvehicemodel[0];
		//String vehmodel = strvehicemodel[1];

		Vector AppchartVector = new Vector();
		String strRes="";
		try
		{	
			ServletOutputStream out=res.getOutputStream();

			
			String strSqlQry = "select map_id,battery_type,laptop_name,laptop_model,laptop_product,battery_brand,battery_model,battery_cellcount,battery_warranty,voltage,watt_hr,display_status from laptop_application_chart_mapping where battery_type='"+battertypename+"' and  laptop_name='"+laptopmake+"' and laptop_model='"+laptopmodel+"' and laptop_product='"+laptopproduct+"' and battery_brand='"+batterybrand+"' and battery_model='"+batterymodel+"'";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
			
			AppchartVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"AppchartVector :"+AppchartVector);
			
			if(AppchartVector==null || AppchartVector.size() == 0)
			{
				out.println("<p align='center' valign='top' class='insidecontent'><table valign='top' width='500' cellspacing='0' border='1' align='center' bgcolor='#FFFFFF' cellpadding='0'><tr><td><table valign='top' width='500' bgcolor='#FFFFFF' border='0'><tr><td class='insidecontent' align='center'>No Application Chart Mapping Details Avaliable!</td></tr></table></td></tr></table><a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
			}
			else
			{
				strRes=strRes+"<table border='0' align='center' bgcolor='#FFFFFF' width='700'><tr>";
				//strRes=strRes+"<td align='center'  valign='top'><input type='button' onClick=\"javascript:deleteappichatdetails();\" value='DELETE' class='button4'></td></tr></table>";
						
				strRes=strRes+"<table border='0' cellspacing='1' align='center' cellpadding='2' bgcolor='#FFFFFF' width='100%'><tr bgcolor='#2364b1'><td class='prodheading' width='15%'>BatteryType</td><td  class='prodheading' width='15%'>Model</td><td class='prodheading' width='15%'>Product</td><td class='prodheading' width='13%'>Battery Brand</td><td class='prodheading' width='15%'>BatteryModel</td><td class='prodheading' width='12%'>DELETE</td><td class='prodheading' width='15%'>Status</td></tr>";
			  	for(int j=0; j<AppchartVector.size();j++)
				{
					Hashtable ht1=(Hashtable)AppchartVector.get(j);
					String strmapid=String.valueOf(ht1.get("map_id"));
					String strbattype=(String)ht1.get("battery_type");
					String strlaptopname=(String)ht1.get("laptop_name");
					String strlaptopmodel=(String)ht1.get("laptop_model");
					String strlaptopproduct=(String)ht1.get("laptop_product");
					String strbatterybrand=(String)ht1.get("battery_brand");
					String strbatterymodel=(String)ht1.get("battery_model");
					String strbatterycellcount=(String)ht1.get("battery_cellcount");
					String voltage=(String)ht1.get("voltage");
					String watthr=(String)ht1.get("watt_hr");
					String displaystatus=(String)ht1.get("display_status");

					LogLevel.DEBUG(5,new Throwable(),"strmapid :"+strmapid );

					int Jcnt=j+1;
					
					strRes=strRes+"<table bgcolor='#FFFFFF' width='100%' cellspacing='1' border='0' align='center'  cellpadding='2'><input type='checkbox' name='chkSi' value='' style='display:none'/>";
					//strRes=strRes+"<tr><td width='6%' class='table1' align='left' >"+Jcnt+"<input type='checkbox' name='chkSi' value='"+strmapid+"'/></td>";
					strRes=strRes+"<td width='15%'   class='table1 align='left'  >"+strbattype+"</td>";
					strRes=strRes+"<td  width='15%'  class='table1 align='left'  >"+strlaptopmodel+"</td>";
					strRes=strRes+"<td  width='15%'  class='table1 align='left'>"+strlaptopproduct+"</td>";
					strRes=strRes+"<td  width='13%'  class='table1 align='left'  >"+strbatterybrand+"</td>";
					strRes=strRes+"<td  width='15%'  class='table1 align='left'  >"+strbatterymodel+"</td>";
					strRes=strRes+"<td  width='12%'  class='table1 align='left'  ><a href=\"javascript:deleteappichatdetails('"+strmapid+"');\">Delete</td>";
					strRes=strRes+"<td  width='15%'  class='table1 align='left'  >"+displaystatus+"<br><a href=\"javascript:editStatus('"+strmapid+"','"+displaystatus+"');\">Click here to change status</a></td></tr>";
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
	* This method is to update application chart detials
	* @strSqlQry : carries the query to update application chart detials from laptop_application_chart_mapping table.
	\* **************************************************************************************************************************************/
	public String updateapplicationchartdetails(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
		String chkSi= (req.getParameter("chkSi")!=null)?(req.getParameter("chkSi")):"";
		LogLevel.DEBUG(5, new Throwable(), "chkSi :" + chkSi);

		String displaystatus= (req.getParameter("displaystatus")!=null)?(req.getParameter("displaystatus")):"";
		LogLevel.DEBUG(5, new Throwable(), "displaystatus :" + displaystatus);

		String strRes="";
		try
		{	
			if(displaystatus.equals("active"))
			{
				displaystatus="inactive";
			}
			else
			{
				displaystatus="active";
			}
			LogLevel.DEBUG(5, new Throwable(), "displaystatus :" + displaystatus);

			ServletOutputStream out=res.getOutputStream();

			String strSqlQry = "update laptop_application_chart_mapping set display_status='"+displaystatus+"' where map_id='"+chkSi+"'";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
			
			int i=qm.executeUpdate(strSqlQry);

			if(i <0)
				{
					LogLevel.DEBUG(1,new Throwable(),"No Name exists u can continue ");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#00dd00' class='vrb10'></font> ");
					out.println("Failed to update  Application chart details ");
				}
				else
				{
					LogLevel.DEBUG(1,new Throwable(),"");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'></font> ");
					//System.out.println(email);
					out.println("Successfully updated status.");
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
	* This method is to delete application chart detials
	* @strSqlQry : carries the query to delete application chart detials from laptop_application_chart_mapping table.
	\* **************************************************************************************************************************************/
	public String deleteapplicationchartdetails(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
		String chkSi= (req.getParameter("chkSi")!=null)?(req.getParameter("chkSi")):"";
		LogLevel.DEBUG(5, new Throwable(), "chkSi :" + chkSi);

		String strRes="";
		try
		{	
			ServletOutputStream out=res.getOutputStream();

			String strSqlQry = "delete from laptop_application_chart_mapping where map_id='"+chkSi+"'";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
			
			int i=qm.executeUpdate(strSqlQry);

			if(i <0)
				{
					LogLevel.DEBUG(1,new Throwable(),"No Name exists u can continue ");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#00dd00' class='vrb10'></font> ");
					out.println("Failed to delete Application chart details ");
				}
				else
				{
					LogLevel.DEBUG(1,new Throwable(),"");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'></font> ");
					//System.out.println(email);
					out.println("Successfully deleted Application chart details");
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
