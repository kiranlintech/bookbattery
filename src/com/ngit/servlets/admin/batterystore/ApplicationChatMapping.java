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

  
public class ApplicationChatMapping extends HttpServlet 
{  
	private ServletContext context; 
	QueryManager qm;
 
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
		String struserName=(String)session.getAttribute("sesBatteryAdminName"); 
		LogLevel.DEBUG(5,new Throwable(),"struserName :"+struserName );

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

		/* ****************************************************************************************\
		* This action is used to get vehicle name.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("getvehiclenames"))
			{ 
			try
			{
				String strRes=getvehiclenames(req,res,session);
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
		* This action is used to get battery capacity.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("getbatterycapacity"))
			{ 
			try
			{
				String strRes=getbatterycapacity(req,res,session);
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

	if(strWhatToDo.equalsIgnoreCase("getvehicle_model"))
			{ 
			try
			{
				String strRes=getvehicle_model(req,res,session);
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

	if(strWhatToDo.equalsIgnoreCase("insertapplicationchatmapping"))
			{ 
			try
			{
				String strRes=insertapplicationchatmapping(req,res,session);
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

	if(strWhatToDo.equalsIgnoreCase("getbrandtoedit"))
			{ 
			try
			{
				String strRes=getbrandtoedit(req,res,session);
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

	if(strWhatToDo.equalsIgnoreCase("getmodeltoedit"))
			{ 
			try
			{
				String strRes=getmodeltoedit(req,res,session);
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
		  if(strWhatToDo.equalsIgnoreCase("getapplichatdetailstoedit"))
			{ 
			try
			{
				String strRes=getapplichatdetailstoedit(req,res,session);
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
		  if(strWhatToDo.equalsIgnoreCase("getdeleteappication"))
			{ 
			try
			{
				String strRes=getdeleteappication(req,res,session);
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
	public String getvehiclenames(HttpServletRequest req,HttpServletResponse res,HttpSession session)
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
			String vehicle_name="";	

	 		ServletOutputStream out=res.getOutputStream();

			String strvehid = "select distinct(vehicle_name) from vehicle_details where battery_type='"+batterytypes+"' order by vehicle_name asc"; 
			LogLevel.DEBUG(5, new Throwable(), "strvehid :" + strvehid);
			
			Vector vehidvec=qm.executeQuery(strvehid);
			LogLevel.DEBUG(5,new Throwable(),"vehidvec:"+vehidvec);
			if(vehidvec.isEmpty())
			{ 
				out.println("<option value='defaultss'><font color='#00dd00'><- Select Make -></font></option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<vehidvec.size(); i++)
					{
						if(i==0)
						{
						out.print("<option value='default'><font color='#00CC00'><- Select Make -></font></option>");
						}
						Hashtable ht=(Hashtable)vehidvec.get(i);
						//veh_id_fetched =String.valueOf(ht.get("veh_id"));
						//LogLevel.DEBUG(5,new Throwable(),"veh_id_fetched:"+veh_id_fetched);
						vehicle_name =String.valueOf(ht.get("vehicle_name"));
						LogLevel.DEBUG(5,new Throwable(),"vehicle_name:"+vehicle_name);
						out.print("<option value='"+vehicle_name+"'>"+vehicle_name+"</option>"); 
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
	* This method is to get battery capacity
	* @strSqlQry : carries the query to battery capacity in battery_details table.
	\* **************************************************************************************************************************************/
	public String getbatterycapacity(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
	String batterytype= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
	String key= (req.getParameter("key")!=null)?(req.getParameter("key")):"";
	String strRes="";
	String[] strbatterytype = batterytype.split(","); 
	String battertypeid = strbatterytype[0];
	String battertypename = strbatterytype[1];
	Vector strbatcapa = new Vector();
		try
		{	
			//String veh_id_fetched="";
			String bat_capacity="";	

	 		ServletOutputStream out=res.getOutputStream();

			if(key.equals("app") || key == "app")
			{
				String strbatcap = "select distinct(bat_capacity) from application_chat_mapping where bat_type='"+battertypename+"' order by bat_capacity asc"; 
				LogLevel.DEBUG(5, new Throwable(), "strbatcap :" + strbatcap);
				strbatcapa=qm.executeQuery(strbatcap);
				LogLevel.DEBUG(5,new Throwable(),"strbatcapa:"+strbatcapa);
			}
			else
			{
				String strbatcap = "select distinct(bat_capacity) from battery_details order by bat_capacity asc"; 
				LogLevel.DEBUG(5, new Throwable(), "strbatcap :" + strbatcap);
				strbatcapa=qm.executeQuery(strbatcap);
				LogLevel.DEBUG(5,new Throwable(),"strbatcapa:"+strbatcapa);
			}
			if(strbatcapa.isEmpty())
			{ 
				out.println("<option value='default'><font color='#00dd00'><- Select Battery Capacity -></font></option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<strbatcapa.size(); i++)
					{
						if(i==0)
						{
						out.print("<option value='default'><font color='#00CC00'><- Select Battery Capacity -></font></option>");
						}
						Hashtable ht2=(Hashtable)strbatcapa.get(i);
						//veh_id_fetched =String.valueOf(ht.get("veh_id"));
						//LogLevel.DEBUG(5,new Throwable(),"veh_id_fetched:"+veh_id_fetched);
						bat_capacity =String.valueOf(ht2.get("bat_capacity"));
						LogLevel.DEBUG(5,new Throwable(),"bat_capacity:"+bat_capacity);
						out.print("<option value='"+bat_capacity+"'>"+bat_capacity+"</option>"); 
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

public String getvehicle_model(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String vehiclename= (req.getParameter("vehiclename")!=null)?(req.getParameter("vehiclename")):"";
			String batterytype= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";

			String[] battertype = batterytype.split(",");
			String battypeids=battertype[0];
			String batterytypes=battertype[1];

			String veh_id="";
			String vehicle_model="";	

	 		ServletOutputStream out=res.getOutputStream();

			String strveh_idqry = "select veh_id,vehicle_model from vehicle_details where vehicle_name='"+vehiclename+"' and battery_type='"+batterytypes+"' order by vehicle_model asc"; 
			LogLevel.DEBUG(5, new Throwable(), "strveh_idqry :" + strveh_idqry);
			
			Vector vehidvect=qm.executeQuery(strveh_idqry);
			LogLevel.DEBUG(5,new Throwable(),"vehidvect:"+vehidvect);
			if(vehidvect.isEmpty())
			{ 
				out.println("<option value='defaultss'><font color='#00dd00'><- Select Model -></font></option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<vehidvect.size(); i++)
					{
						if(i==0)
						{
						out.print("<option value='default'><font color='#00CC00'><- Select Model -></font></option>");
						}
						Hashtable ht=(Hashtable)vehidvect.get(i);
						vehicle_model =String.valueOf(ht.get("vehicle_model"));
						veh_id =String.valueOf(ht.get("veh_id"));
						out.print("<option value='"+veh_id+","+vehicle_model+"'>"+vehicle_model+"</option>"); 
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
	* @strSqlQry : carries the query to battery brands details in battery_details table.
	\* **************************************************************************************************************************************/

public String getbatterybrands(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
	 		ServletOutputStream out=res.getOutputStream();

			String SqlQuery = "select distinct(bat_brand) from battery_details"; 
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
				String strbat_brand=(String)ht2.get("bat_brand");
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
	* This method is to get battery models.
	* @strSqlQry : carries the query to battery models details in battery_details table.
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
			String SqlQuery = "select distinct(bat_model) from battery_details where bat_brand='"+strTempValue+"'"; 
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
				String strbat_model=(String)ht2.get("bat_model");
				
				strRes=strRes+"<table width='100%' border='0'><tr  class='#FFFFFF'><td width='5%'></td><td width='95%' valign='middle' class='insidecontent'><input type='checkbox' name='batterymodel' value=''style='display:none'/><input type='checkbox' name='batterymodel' onclick='javascript:getbatterymodels()'  value='"+strbat_model+"' size='3' >"+strbat_model+"</td></tr></table>";
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
	* This method is to map battery details.
	* @strSqlQry : carries the query to map battery details in application_chat_mapping table.
	\* **************************************************************************************************************************************/
	public String insertapplicationchatmapping(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		String strbatterytype = (req.getParameter("batterytype") != null) ? (req.getParameter("batterytype")) : "";
		String strvehiclemake = (req.getParameter("vehiclemake") != null)? (req.getParameter("vehiclemake")) : "";
		String strvehicemodel = (req.getParameter("vehicemodel") != null)? (req.getParameter("vehicemodel")) : "";
		
		LogLevel.DEBUG(5,new Throwable(),"strvehicemodel:"+strvehicemodel);
		String strbatterbrand = (req.getParameter("batterbrand") != null)? (req.getParameter("batterbrand")) : "";
		String strbatterymodel = (req.getParameter("batterymodel") != null)? (req.getParameter("batterymodel")) : "";

		String[] batterytype = strbatterytype.split(","); 
		String battertypeid = batterytype[0];
		String battertypename = batterytype[1];
		String vehicemodel_Array[]={};
		String vehiceid_Array[]={};
		String vehicemodel_split_Array[]={};
		String vehid="";
		String vehmodel="";
		String fueltype="";

						if(battertypename.equals("Inverter Batteries"))
						{
							vehid="0";
						}
						else
						{
							String[] vehiclemodel = strvehicemodel.split(",");
							vehid = vehiclemodel[0];
							LogLevel.DEBUG(5,new Throwable(),"vehid:"+vehid);
							vehmodel = vehiclemodel[1];
							LogLevel.DEBUG(5,new Throwable(),"vehmodel:"+vehmodel);
							
							
							String Get_fuel_type="select fuel_type from vehicle_details where veh_id='"+vehid+"'";
							Hashtable htfuel_type = qm.getRow(Get_fuel_type);
							fueltype=(String)htfuel_type.get("fuel_type");
							LogLevel.DEBUG(5,new Throwable(),"fueltype :"+fueltype);
						}

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
						String SqlQuery = "select bat_id,bat_name,bat_model,bat_brand,bat_warranty,bat_capacity,bat_act_price,bat_witbat_price,icon_url,description from battery_details where bat_model='"+strTempValue+"'"; 
						LogLevel.DEBUG(5, new Throwable(), "SqlQuery :" + SqlQuery);

						Vector battmodel=qm.executeQuery(SqlQuery);
						LogLevel.DEBUG(5,new Throwable(),"battmodel:"+battmodel);

						for ( int j=0; j<battmodel.size(); j++)
						{
						Hashtable ht2=(Hashtable)battmodel.get(j);
						String bat_id =String.valueOf(ht2.get("bat_id"));
						String bat_name=(String)ht2.get("bat_name");
						String bat_model=(String)ht2.get("bat_model");
						String bat_brand=(String)ht2.get("bat_brand");
						String bat_warranty=(String)ht2.get("bat_warranty");
						String bat_capacity=(String)ht2.get("bat_capacity");
						String bat_act_price =String.valueOf(ht2.get("bat_act_price"));
						String bat_witbat_price =String.valueOf(ht2.get("bat_witbat_price"));
						String description=(String)ht2.get("description");
						String icon_url=(String)ht2.get("icon_url");
						LogLevel.DEBUG(5, new Throwable(), "icon_url :" + icon_url);
						if(battertypename.equals("Inverter Batteries"))
						{
						String StrSqlQery1 = "select bat_model from application_chat_mapping where bat_type='"+battertypename+"' and bat_capacity='"+bat_capacity+"' and bat_brand='"+bat_brand+"' and bat_model='"+strTempValue+"'";
						LogLevel.DEBUG(5,new Throwable(),"StrSqlQery1 :"+StrSqlQery1);

						Hashtable htapp1 = qm.getRow(StrSqlQery1);
						String strbat_model1=(String)htapp1.get("bat_model");
						LogLevel.DEBUG(5,new Throwable(),"strbat_model1 :"+strbat_model1);

						if(htapp1.isEmpty())
						{ 
						/* Query to insert the values entered battery details into application mapping table. */
						String SteSqlQry = "insert into application_chat_mapping(map_id,battype_id,veh_id,bat_id,bat_brand,bat_model,bat_name,veh_name,veh_model,bat_type,bat_capacity,bat_warranty,bat_act_price,bat_witbat_price,icon_url,description,creation_date,created_by) values(NULL,'"+battertypeid+"','"+vehid+"','"+bat_id+"','"+bat_brand+"','"+bat_model+"','"+bat_name+"','','','"+battertypename+"','"+bat_capacity+"','"+bat_warranty+"','"+bat_act_price+"','"+bat_witbat_price+"','"+icon_url+"','"+description+"',now(),'ngit')";
						
						result=qm.executeUpdate(SteSqlQry);
						LogLevel.DEBUG(5,new Throwable(),"result :"+result );
						}
						else
						{
						}
						}
						else
						{

									String StrSqlQery = "select bat_model from application_chat_mapping where bat_type='"+battertypename+"' and veh_name='"+strvehiclemake+"' and veh_model='"+vehmodel+"' and bat_brand='"+bat_brand+"' and bat_model='"+strTempValue+"'";
									LogLevel.DEBUG(5,new Throwable(),"StrSqlQery :"+StrSqlQery);

									Hashtable htapp = qm.getRow(StrSqlQery);
									String strbat_model=(String)htapp.get("bat_model");
									LogLevel.DEBUG(5,new Throwable(),"strbat_model :"+strbat_model);

									if(htapp.isEmpty())
									{ 
										String strvehicemodel_Array[] =strvehicemodel.split("~");
										int length=strvehicemodel_Array.length;

										for(int i=0;i<strvehicemodel_Array.length;i++)			
										{
											String strvehicemodel_splitvalue=strvehicemodel_Array[i];
											LogLevel.DEBUG(5,new Throwable(),"strvehicemodel_splitvalue:"+strvehicemodel_splitvalue);
											vehicemodel_split_Array=strvehicemodel_splitvalue.split(",");
											
											int arrlength=vehicemodel_split_Array.length;
											LogLevel.DEBUG(5,new Throwable(),"arrlength:"+arrlength);
											LogLevel.DEBUG(5,new Throwable(),"vehicemodel_split_Array:"+vehicemodel_split_Array);
											LogLevel.DEBUG(5,new Throwable(),"vehicemodel_split_Array0:"+vehicemodel_split_Array[0]);
											LogLevel.DEBUG(5,new Throwable(),"vehicemodel_split_Array1:"+vehicemodel_split_Array[1]);
											 vehid=vehicemodel_split_Array[0];
											LogLevel.DEBUG(5,new Throwable(),"vehid:"+vehid);
											 vehmodel=vehicemodel_split_Array[1];
											LogLevel.DEBUG(5,new Throwable(),"vehmodel:"+vehmodel);
											
											/* Query to insert the values entered battery details into application mapping table. */   
											String SteSqlQry = "insert into application_chat_mapping(map_id,battype_id,veh_id,bat_id,bat_brand,bat_model,bat_name,veh_name,veh_model,fuel_type,bat_type,bat_capacity,bat_warranty,bat_act_price,bat_witbat_price,icon_url,description,creation_date,created_by) values(NULL,'"+battertypeid+"','"+vehid+"','"+bat_id+"','"+bat_brand+"','"+bat_model+"','"+bat_name+"','"+strvehiclemake+"','"+vehmodel+"','"+fueltype+"','"+battertypename+"','"+bat_capacity+"','"+bat_warranty+"','"+bat_act_price+"','"+bat_witbat_price+"','"+icon_url+"','"+description+"',now(),'ngit')";
											
											LogLevel.DEBUG(5,new Throwable(),"SteSqlQry :"+SteSqlQry );
											
											
											result=qm.executeUpdate(SteSqlQry);
											LogLevel.DEBUG(5,new Throwable(),"result :"+result );
									
										}

									}
									else
									{
									}
						}
						}
					    }
				  }
				 
				if(result < 0)
				{
					LogLevel.DEBUG(1,new Throwable()," Failed to Add application chat mapping details! ");
					session.setAttribute("priority","1");
					session.setAttribute("sesmapErrorMsg", "<font color='#ff3333' class='top1'>Failed to Add application chat mapping details! </font> ");
					res.sendRedirect("/bookbattery/jsp/admin/batterystore/applicationchat/applicationchatmapping.jsp");
					return strRes;
				}
				else
				{
					LogLevel.DEBUG(1,new Throwable(),"Successfully Added application chat mapping details! ");
					session.setAttribute("sesmapErrorMsg", "<font color='#000000' class='top1'>Successfully Added application chat mapping details!</font>");
					//session.setAttribute("sescontVector", reslt);
					res.sendRedirect("/bookbattery/jsp/admin/batterystore/applicationchat/applicationchatmapping.jsp");
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
	* This method is to get vehicle model.
	* @strSqlQry : carries the query to vehicle model details in vehicle_details table.
	\* **************************************************************************************************************************************/

public String getbrandtoedit(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			//String veh_id="";
			String strbrand="";	

	 		ServletOutputStream out=res.getOutputStream();

			String strbrandqry = "select distinct(bat_brand) from battery_details order by bat_brand asc"; 
			LogLevel.DEBUG(5, new Throwable(), "strbrandqry :" + strbrandqry);
			
			Vector brandvect=qm.executeQuery(strbrandqry);
			LogLevel.DEBUG(5,new Throwable(),"brandvect:"+brandvect);
			if(brandvect.isEmpty())
			{ 
				out.println("<option value='defaultss'><font color='#00dd00'><- Select Brand -></font></option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<brandvect.size(); i++)
					{
						if(i==0)
						{
						out.print("<option value='default'><font color='#00CC00'><- Select Brand -></font></option>");
						}
						Hashtable ht=(Hashtable)brandvect.get(i);
						strbrand =String.valueOf(ht.get("bat_brand"));
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

public String getmodeltoedit(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strbatterybrand = (req.getParameter("batterybrand") != null)? (req.getParameter("batterybrand")) : "";
	String strbatterytype = (req.getParameter("batterytype") != null)? (req.getParameter("batterytype")) : "";
	String strvehiclename = (req.getParameter("vehiclename") != null)? (req.getParameter("vehiclename")) : "";
	String strvehiclemodel = (req.getParameter("vehiclemodel") != null)? (req.getParameter("vehiclemodel")) : "";

	String[] batterytype = strbatterytype.split(","); 
	String battertypeid = batterytype[0];
	String battertypename = batterytype[1];

	String vehmodel="";

	if(strvehiclemodel.indexOf(",")!=-1)
	{
		String[] vehiclemodel = strvehiclemodel.split(","); 
		String vehid = vehiclemodel[0];
		vehmodel = vehiclemodel[1];
	}
	else
	{
		vehmodel = strvehiclemodel;
	}
	Vector brandvect = new Vector();
	String strRes="";
		try
		{	
			//String veh_id="";
			String strmodel="";	

	 		ServletOutputStream out=res.getOutputStream();

			if(battertypename == "Inverter Batteries" || battertypename.equals("Inverter Batteries"))
			{
			String strbrandqry = "select distinct(bat_model) from application_chat_mapping where bat_type='"+battertypename+"' and bat_capacity='"+vehmodel+"' and bat_brand='"+strbatterybrand+"' order by bat_model asc"; 
			LogLevel.DEBUG(5, new Throwable(), "strbrandqry :" + strbrandqry);
			
			brandvect=qm.executeQuery(strbrandqry);
			LogLevel.DEBUG(5,new Throwable(),"brandvect:"+brandvect);
			}
			else
			{
			String strbrandqry = "select distinct(bat_model) from application_chat_mapping where bat_type='"+battertypename+"' and veh_name='"+strvehiclename+"' and veh_model='"+vehmodel+"' and bat_brand='"+strbatterybrand+"' order by bat_model asc"; 
			LogLevel.DEBUG(5, new Throwable(), "strbrandqry :" + strbrandqry);
			
			brandvect=qm.executeQuery(strbrandqry);
			LogLevel.DEBUG(5,new Throwable(),"brandvect:"+brandvect);
			}
			if(brandvect.isEmpty())
			{ 
				out.println("<option value='defaultss'><font color='#00dd00'><- Select Model -></font></option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<brandvect.size(); i++)
					{
						if(i==0)
						{
						out.print("<option value='default'><font color='#00CC00'><- Select Model -></font></option>");
						}
						Hashtable ht=(Hashtable)brandvect.get(i);
						strmodel =String.valueOf(ht.get("bat_model"));
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
	public String getapplichatdetailstoedit(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
		String batterytype= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
		LogLevel.DEBUG(5, new Throwable(), "batterytype :" + batterytype);

		String vehiclemake= (req.getParameter("vehiclemake")!=null)?(req.getParameter("vehiclemake")):"";
		LogLevel.DEBUG(5, new Throwable(), "vehiclemake :" + vehiclemake);

		String vehicemodel= (req.getParameter("vehicemodel")!=null)?(req.getParameter("vehicemodel")):"";
		LogLevel.DEBUG(5, new Throwable(), "vehicemodel :" + vehicemodel);

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

		Vector AppchatVector = new Vector();
		String strRes="";
		try
		{	
			ServletOutputStream out=res.getOutputStream();

			if(battertypename == "Inverter Batteries" || battertypename.equals("Inverter Batteries"))
			{
			String strSqlQry = "select map_id,bat_type,veh_name,veh_model,bat_brand,bat_model,bat_capacity,bat_warranty from application_chat_mapping where bat_type='"+battertypename+"' and bat_capacity='"+vehicemodel+"' and bat_brand='"+batterybrand+"' and bat_model='"+batterymodel+"'";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
			
			AppchatVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"AppchatVector :"+AppchatVector);
			}
			else
			{
			String strSqlQry = "select map_id,bat_type,veh_name,veh_model,bat_brand,bat_model,bat_capacity,bat_warranty from application_chat_mapping where bat_type='"+battertypename+"' and veh_name='"+vehiclemake+"' and veh_model='"+vehicemodel+"' and bat_brand='"+batterybrand+"' and bat_model='"+batterymodel+"'";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
			
			AppchatVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"AppchatVector :"+AppchatVector);
			}
			if(AppchatVector==null || AppchatVector.size() == 0)
			{
				out.println("<p align='center' valign='top' class='insidecontent'><table valign='top' width='500' cellspacing='0' border='1' align='center' bgcolor='#FFFFFF' cellpadding='0'><tr><td><table valign='top' width='500' bgcolor='#FFFFFF' border='0'><tr><td class='insidecontent' align='center'>No Application Chat Mapping details Avaliable!</td></tr></table></td></tr></table><a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
			}
			else
			{
				strRes=strRes+"<table border='0' align='center' bgcolor='#FFFFFF' width='700'><tr>";
				//strRes=strRes+"<td align='center'  valign='top'><input type='button' onClick=\"javascript:deleteappichatdetails();\" value='DELETE' class='button4'></td></tr></table>";
						
				strRes=strRes+"<table border='0' cellspacing='1' align='center' cellpadding='2' bgcolor='#FFFFFF' width='100%'><tr bgcolor='#2364b1'><td class='prodheading' width='15%'>BatteryType</td><td  class='prodheading' width='15%'>Make</td><td class='prodheading' width='15%'>Model</td><td class='prodheading' width='15%'>BatteryCapacity</td><td class='prodheading' width='25%'>BatteryModel</td><td class='prodheading' width='15%'>DELETE</td></tr>";
			  	for(int j=0; j<AppchatVector.size();j++)
				{
					Hashtable ht1=(Hashtable)AppchatVector.get(j);
					String strmapid=String.valueOf(ht1.get("map_id"));
					String strbattype=(String)ht1.get("bat_type");
					String strvehname=(String)ht1.get("veh_name");
					String strvehmodel=(String)ht1.get("veh_model");
					String strbatbrand=(String)ht1.get("bat_brand");
					String strbatmodel=(String)ht1.get("bat_model");
					String strbatcap=(String)ht1.get("bat_capacity");
					LogLevel.DEBUG(5,new Throwable(),"strmapid :"+strmapid );

					int Jcnt=j+1;
					
					strRes=strRes+"<table bgcolor='#FFFFFF' width='100%' cellspacing='1' border='0' align='center'  cellpadding='2'><input type='checkbox' name='chkSi' value='' style='display:none'/>";
					//strRes=strRes+"<tr><td width='6%' class='table1' align='left' >"+Jcnt+"<input type='checkbox' name='chkSi' value='"+strmapid+"'/></td>";
					strRes=strRes+"<td width='15%'   class='table1 align='left'  >"+strbattype+"</td>";
					strRes=strRes+"<td  width='15%'  class='table1 align='left'  >"+strvehname+"</td>";
					strRes=strRes+"<td  width='15%'  class='table1 align='left'>"+strvehmodel+"</td>";
					strRes=strRes+"<td  width='15%'  class='table1 align='left'  >"+strbatcap+"</td>";
					strRes=strRes+"<td  width='25%'  class='table1 align='left'  >"+strbatmodel+"</td>";
					strRes=strRes+"<td  width='15%'  class='table1 align='left'  ><a href=\"javascript:deleteappichatdetails('"+strmapid+"');\">Delete</td></tr>";
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
	* This method is to get battery models.
	* @strSqlQry : carries the query to battery models details in battery_details table.
	\* **************************************************************************************************************************************/
	public String getdeleteappication(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
		String chkSi= (req.getParameter("chkSi")!=null)?(req.getParameter("chkSi")):"";
		LogLevel.DEBUG(5, new Throwable(), "chkSi :" + chkSi);

		String strRes="";
		try
		{	
			ServletOutputStream out=res.getOutputStream();

			String strSqlQry = "delete from application_chat_mapping where map_id='"+chkSi+"'";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
			
			int i=qm.executeUpdate(strSqlQry);

			if(i <0)
				{
					LogLevel.DEBUG(1,new Throwable(),"No Name exists u can continue ");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#00dd00' class='vrb10'></font> ");
					out.println("Failed to delete Application Mapping details ");
				}
				else
				{
					LogLevel.DEBUG(1,new Throwable(),"");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'></font> ");
					//System.out.println(email);
					out.println("Successfully deleted Application details");
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
