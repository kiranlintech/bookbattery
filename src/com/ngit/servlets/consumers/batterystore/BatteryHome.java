/***********************************************************************		
	NGIT Confidential. 
	@File Name   : BatteryHome.java 
	@Description : This Servlet is used to select the Battery Details.
	@Author	     : Sai Krishna Daddala.
	@Date        : 30th August 2013
******************************************************************/ 
 
package com.ngit.servlets.consumers.batterystore; 
 
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

  
public class BatteryHome extends HttpServlet 
{  
	private ServletContext context; 
	QueryManager qm;
	static final long serialVersionUID=21;
	/*This init method initializes the necessary connection pools and perform the transactions on the pools from respectvie files. */
	public void init(ServletConfig config)throws ServletException
	{
		super.init(config); 
		context = getServletContext(); 
		try
		{
			String strMOPConfig = getInitParameter("paramMopConfig"); 
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
  
	/* This doGet service method calls doPost service method to handle all the reqs and responses to perform Admin Login. */
 	public void doGet (HttpServletRequest req , HttpServletResponse res )throws IOException , ServletException
	{
		doPost(req, res);
	}
	/*This doPost service method handles all the requests and responses passing from doGet service method to perform Admin Login.*/
	public void doPost( HttpServletRequest req , HttpServletResponse res )throws IOException , ServletException
	{
		res.setContentType("text/html;charset=UTF-8");
        HttpSession session=req.getSession(true);
 		session=req.getSession(true);
		String struserName=(String)session.getAttribute("sesBatteryUserLogin"); 
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


		/* ****************************************************************************************\
		* This action is used to get vehicle name.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("getvehiclename"))
			{ 
			try
			{
				String strRes=getvehiclename(req,res,session);
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
		* This action is used to get battery pincode.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("getpincode"))
			{ 
			try
			{
				String strRes=getpincode(req,res,session);
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
	* This action is used to get vehicle Fuel Type.
	\* *****************************************************************************************/		

	if(strWhatToDo.equalsIgnoreCase("getvehicle_fuel_type"))
			{ 
			try
			{
				String strRes=getvehicle_fuel_type(req,res,session);
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
	* This action is used to get battery barnd.
	\* *****************************************************************************************/		
	if(strWhatToDo.equalsIgnoreCase("getbat_brand"))
			{ 
			try
			{
				String strRes=getbat_brand(req,res,session);
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
	* This action is used to get battery barnd.
	\* *****************************************************************************************/		
	if(strWhatToDo.equalsIgnoreCase("getbatterybrand"))
			{ 
			try
			{
				String strRes=getbatterybrand(req,res,session);
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
	* This action is used to get battery barnd.
	\* *****************************************************************************************/		
	if(strWhatToDo.equalsIgnoreCase("getbat_brand1"))
			{ 
			try
			{
				String strRes=getbat_brand1(req,res,session);
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
	* This action is used to get battery barnd.
	\* *****************************************************************************************/		
	if(strWhatToDo.equalsIgnoreCase("getstate"))
			{ 
			try
			{
				String strRes=getstate(req,res,session);
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
	* This action is used to get battery barnd.
	\* *****************************************************************************************/		
	   if(strWhatToDo.equalsIgnoreCase("getservicestate"))
			{ 
			try
			{
				String strRes=getservicestate(req,res,session);
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
	* This action is used to get battery barnd.
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
	* This action is used to get battery barnd.
	\* *****************************************************************************************/			if(strWhatToDo.equalsIgnoreCase("getservicecity"))
			{ 
			try
			{
				String strRes=getservicecity(req,res,session);
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
	* This action is used to get battery barnd.
	\* *****************************************************************************************/		
	if(strWhatToDo.equalsIgnoreCase("getarea"))
			{ 
			try
			{
				String strRes=getarea(req,res,session);
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
	* This action is used to get battery barnd.
	\* *****************************************************************************************/		
	if(strWhatToDo.equalsIgnoreCase("getservicearea"))
	{ 
		try
		{
			String strRes=getservicearea(req,res,session);
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
	* This action is used to get battery barnd.
	\* *****************************************************************************************/		
	if(strWhatToDo.equalsIgnoreCase("getbat_model"))
			{ 
			try
			{
				String strRes=getbat_model(req,res,session);
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
	* This action is used to get battery models using ajax.
	\* *****************************************************************************************/		
		  if(strWhatToDo.equalsIgnoreCase("getbatterymodelsdetailsajax"))
			{ 
			try
			{
				String strRes=getbatterymodelsdetailsajax(req,res,session);
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
		  if(strWhatToDo.equalsIgnoreCase("getbatterywarrantydetails"))
			{ 
			try
			{
				String strRes=getbatterywarrantydetails(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);
				out.close();			}
			catch (Exception e)
			{										
			LogLevel.ERROR(1, e, "Error :" + e);
			}	
	      }

		  /* ****************************************************************************************\
	* This action is used to get battery brands.
	\* *****************************************************************************************/		
		  if(strWhatToDo.equalsIgnoreCase("getbat_models_orders_update"))
			{ 
			try
			{
				String strRes=getbat_models_orders_update(req,res,session);
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
		  if(strWhatToDo.equalsIgnoreCase("getbatpricedetls"))
			{ 
			try
			{
				String strRes=getbatpricedetls(req,res,session);
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
	public String getvehiclename(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String batterytype= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
						LogLevel.DEBUG(5,new Throwable(),"batterytype:"+batterytype);

			String veh_id_fetched="";
			String vehicle_name="";	
			String strvehid="";	

	 		ServletOutputStream out=res.getOutputStream();
            
            if((batterytype.equals("Car Battery")) || (batterytype.equals("Car Batteries")))
            {
                strvehid = "select distinct(vehicle_name) from vehicle_details where battery_type='"+batterytype+"' order by field(vehicle_name,'Ford','Nissan','Renault','Skoda','Volks Wagen','Honda','Toyota','Mahindra and Mahindra','Tata','Hyundai','Maruti Suzuki') desc"; 
            }
            else if((batterytype.equals("Bike Battery")) || (batterytype.equals("Bike Batteries")))
            {
                strvehid = "select distinct(vehicle_name) from vehicle_details where battery_type='"+batterytype+"' order by field(vehicle_name,'TVS','Royal Enfield','Yamaha','Bajaj','Honda','Hero MotoCorp') desc"; 
            }
            else
            {
                strvehid = "select distinct(vehicle_name) from vehicle_details where battery_type='"+batterytype+"' order by vehicle_name desc";
            }
            
			LogLevel.DEBUG(5, new Throwable(), "strvehid :" + strvehid);
			
			Vector vehidvec=qm.executeQuery(strvehid);
			LogLevel.DEBUG(5,new Throwable(),"vehidvec:"+vehidvec);

			String vehtype="";

		if(batterytype.equals("Car Batteries"))
		{
			vehtype="Car";
		}
		if(batterytype.equals("Bike Batteries"))
		{
			vehtype="Bike";
		}
		if(batterytype.equals("Bus Batteries"))
		{
			vehtype="Bus";
		}
		if(batterytype.equals("Tractor Batteries"))
		{
			vehtype="Tractor";
		}
		if(batterytype.equals("Truck Batteries"))
		{
			vehtype="Truck";
		}
		if(batterytype.equals("Three Wheeler Batteries"))
		{
			vehtype="Three Wheeler";
		}
		if(batterytype.equals("Special Vehicle Batteries"))
		{
			vehtype="Special Vehicle";
		}
		if(batterytype.equals("Genset Batteries"))
		{
			vehtype="Genset";
		}
		if(batterytype.equals("Crane Batteries"))
		{
			vehtype="Crane";
		}
		if(batterytype.equals("Roller Batteries"))
		{
			vehtype="Roller";
		}
		if(batterytype.equals("Loader Batteries"))
		{
			vehtype="Loader";
		}
		if(batterytype.equals("Dozer Batteries"))
		{
			vehtype="Dozer";
		}
		if(batterytype.equals("Excavator Batteries"))
		{
			vehtype="Excavator";
		}
		if(batterytype.equals("Tyre Handler Batteries"))
		{
			vehtype="Tyre Handler";
		}
		if(batterytype.equals("Hydraulic Shovel Batteries"))
		{
			vehtype="Hydraulic Shovel";
		}
		if(batterytype.equals("Harvestor Batteries"))
		{
			vehtype="Harvestor";
		}
		if(batterytype.equals("Generator Batteries"))
		{
			vehtype="Generator";
		}
		if(batterytype.equals("Compactor Batteries"))
		{
			vehtype="Compactor";
		}
		if(batterytype.equals("Telescopic Handler Batteries"))
		{
			vehtype="Telescopic Handler";
		}
		if(batterytype.equals("Forwarder Batteries"))
		{
			vehtype="Forwarder";
		}
		if(batterytype.equals("Wheeled Harvester Batteries"))
		{
			vehtype="Wheeled Harvester";
		}
		if(batterytype.equals("Minibus Batteries"))
		{
			vehtype="Minibus";
		}
		if(batterytype.equals("Dumper Batteries"))
		{
			vehtype="Dumper";
		}
		if(batterytype.equals("Construction Equipment Batteries"))
		{
			vehtype="Construction Equipment";
		}
		if(batterytype.equals("Hydralic Excavator Batteries"))
		{
			vehtype="Hydralic Excavator";
		}

			if(vehidvec.isEmpty())
			{ 
				out.println("<option value='0' selected>Select&nbsp;"+vehtype+"&nbsp;Make</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<vehidvec.size(); i++)
					{
						if(i==0)
						{
						out.print("<option value='0' selected>Select&nbsp;"+vehtype+"&nbsp;Make</option>");
						}
						Hashtable ht=(Hashtable)vehidvec.get(i);
						//veh_id_fetched =String.valueOf(ht.get("veh_id"));
						//LogLevel.DEBUG(5,new Throwable(),"veh_id_fetched:"+veh_id_fetched);
						vehicle_name =String.valueOf(ht.get("vehicle_name"));
						LogLevel.DEBUG(5,new Throwable(),"vehicle_name:"+vehicle_name);
						out.print("<option style='color: black;' value='"+vehicle_name+"'>"+vehicle_name+"</option>"); 
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
	* This method is to get battery capacity.
	* @strSqlQry : carries the query to battery details in battery_details table.
	\* **************************************************************************************************************************************/
	public String getbatterycapacity(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String batterytype= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
		LogLevel.DEBUG(5,new Throwable(),"batterytype:"+batterytype);
		String strRes="";
		String strbatcap = "";
		try
		{	
			//String veh_id_fetched="";
			String bat_capacity="";	

	 		ServletOutputStream out=res.getOutputStream();
			if(batterytype.equals("Flat Plate Battery"))
			{
				strbatcap = "select distinct(bat_capacity) from application_chat_mapping where battery_type_flag='"+batterytype+"' order by bat_capacity"; 
				LogLevel.DEBUG(5, new Throwable(), "strbatcap :" + strbatcap);
			}
			else if (batterytype.equals("Tubular Battery"))
			{
				strbatcap = "select distinct(bat_capacity) from application_chat_mapping where battery_type_flag='"+batterytype+"' order by bat_capacity";  
				LogLevel.DEBUG(5, new Throwable(), "strbatcap :" + strbatcap);
			}
			else if(batterytype.equals("Tall Tubular Battery"))
			{
				strbatcap = "select distinct(bat_capacity) from application_chat_mapping where battery_type_flag='"+batterytype+"' order by bat_capacity";  
				LogLevel.DEBUG(5, new Throwable(), "strbatcap :" + strbatcap);
			}
			else
			{
				strbatcap = "select distinct(bat_capacity) from application_chat_mapping where bat_type='"+batterytype+"' order by bat_capacity"; 
				LogLevel.DEBUG(5, new Throwable(), "strbatcap :" + strbatcap);
			}
			
			Vector strbatcapa=qm.executeQuery(strbatcap);
			LogLevel.DEBUG(5,new Throwable(),"strbatcapa:"+strbatcapa);
			if(strbatcapa.isEmpty())
			{ 
				out.println("<option value='0' selected>&nbsp;Select&nbsp;Capacity</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<strbatcapa.size(); i++)
					{
						if(i==0)
						{
						out.print("<option value='0' selected>&nbsp;Select&nbsp;Capacity</option>");
						}
						Hashtable ht2=(Hashtable)strbatcapa.get(i);
						//veh_id_fetched =String.valueOf(ht.get("veh_id"));
						//LogLevel.DEBUG(5,new Throwable(),"veh_id_fetched:"+veh_id_fetched);
						bat_capacity =String.valueOf(ht2.get("bat_capacity"));
						LogLevel.DEBUG(5,new Throwable(),"bat_capacity:"+bat_capacity);
						out.print("<option  value='"+bat_capacity+"'>"+bat_capacity+"</option>"); 
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
			LogLevel.DEBUG(5,new Throwable(),"vehiclename:"+vehiclename);
			String batterytype= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
			LogLevel.DEBUG(5,new Throwable(),"batterytype:"+batterytype);
			//String fueltype= (req.getParameter("fueltype")!=null)?(req.getParameter("fueltype")):"";
			//LogLevel.DEBUG(5,new Throwable(),"fueltype:"+fueltype);

			String veh_name_fetched="";
			String vehicle_model="";
			//String fueltype_condition="";

	 		ServletOutputStream out=res.getOutputStream();
	 		
	 		
	 		/*if(fueltype.equals("")||fueltype.equals(null)||fueltype.equals(" "))
	 		{
	 			fueltype_condition="";
	 		}
	 		else
	 		{
	 			fueltype_condition="and fuel_type='"+fueltype+"'";
	 		}*/

			String strveh_idqry = "select veh_id,vehicle_model from vehicle_details where vehicle_name='"+vehiclename+"' and battery_type='"+batterytype+"' order by vehicle_model asc"; 
			LogLevel.DEBUG(5, new Throwable(), "strveh_idqry :" + strveh_idqry);
			
			Vector vehidvect=qm.executeQuery(strveh_idqry);
			LogLevel.DEBUG(5,new Throwable(),"vehidvect:"+vehidvect);

			String vehtype="";

		if(batterytype.equals("Car Batteries"))
		{
			vehtype="Car";
		}
		if(batterytype.equals("Bike Batteries"))
		{
			vehtype="Bike";
		}
		if(batterytype.equals("Bus Batteries"))
		{
			vehtype="Bus";
		}
		if(batterytype.equals("Tractor Batteries"))
		{
			vehtype="Tractor";
		}
		if(batterytype.equals("Truck Batteries"))
		{
			vehtype="Truck";
		}
		if(batterytype.equals("Three Wheeler Batteries"))
		{
			vehtype="Three Wheeler";
		}
		if(batterytype.equals("Special Vehicle Batteries"))
		{
			vehtype="Special Vehicle";
		}
		if(batterytype.equals("Genset Batteries"))
		{
			vehtype="Genset";
		}
		if(batterytype.equals("Crane Batteries"))
		{
			vehtype="Crane";
		}
		if(batterytype.equals("Roller Batteries"))
		{
			vehtype="Roller";
		}
		if(batterytype.equals("Loader Batteries"))
		{
			vehtype="Loader";
		}
		if(batterytype.equals("Dozer Batteries"))
		{
			vehtype="Dozer";
		}
		if(batterytype.equals("Excavator Batteries"))
		{
			vehtype="Excavator";
		}
		if(batterytype.equals("Tyre Handler Batteries"))
		{
			vehtype="Tyre Handler";
		}
		if(batterytype.equals("Hydraulic Shovel Batteries"))
		{
			vehtype="Hydraulic Shovel";
		}
		if(batterytype.equals("Harvestor Batteries"))
		{
			vehtype="Harvestor";
		}
		if(batterytype.equals("Generator Batteries"))
		{
			vehtype="Generator";
		}
		if(batterytype.equals("Compactor Batteries"))
		{
			vehtype="Compactor";
		}
		if(batterytype.equals("Telescopic Handler Batteries"))
		{
			vehtype="Telescopic Handler";
		}
		if(batterytype.equals("Forwarder Batteries"))
		{
			vehtype="Forwarder";
		}
		if(batterytype.equals("Wheeled Harvester Batteries"))
		{
			vehtype="Wheeled Harvester";
		}
		if(batterytype.equals("Minibus Batteries"))
		{
			vehtype="Minibus";
		}
		if(batterytype.equals("Dumper Batteries"))
		{
			vehtype="Dumper";
		}
		if(batterytype.equals("Construction Equipment Batteries"))
		{
			vehtype="Construction Equipment";
		}
		if(batterytype.equals("Hydralic Excavator Batteries"))
		{
			vehtype="Hydralic Excavator";
		}
			if(vehidvect.isEmpty())
			{ 
				out.println("<option value='0' selected>Select&nbsp;"+vehtype+"&nbsp;Model</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<vehidvect.size(); i++)
					{
						if(i==0)
						{
						out.print("<option value='0' selected>Select&nbsp;"+vehtype+"&nbsp;Model</option>");
						}
						Hashtable ht=(Hashtable)vehidvect.get(i);
						//veh_name_fetched =String.valueOf(ht.get("vehicle_name"));
						vehicle_model =String.valueOf(ht.get("vehicle_model"));
						out.print("<option  value='"+vehicle_model+"'>"+vehicle_model+"</option>"); 
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
	public String getvehicle_fuel_type(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		try
		{	
			String vehiclename= (req.getParameter("vehiclename")!=null)?(req.getParameter("vehiclename")):"";
			LogLevel.DEBUG(5,new Throwable(),"vehiclename:"+vehiclename);
			String batterytype= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
			LogLevel.DEBUG(5,new Throwable(),"batterytype:"+batterytype);

			String veh_name_fetched="";
			String fuel_type="";	

	 		ServletOutputStream out=res.getOutputStream();

			String strveh_idqry = "select distinct(fuel_type) from vehicle_details where vehicle_name='"+vehiclename+"' and battery_type='"+batterytype+"' order by field(fuel_type,'Petrol','Diesel','CNG','LPG') asc"; 
			LogLevel.DEBUG(5, new Throwable(), "strveh_idqry :" + strveh_idqry);
			
			Vector vehidvect=qm.executeQuery(strveh_idqry);
			LogLevel.DEBUG(5,new Throwable(),"vehidvect:"+vehidvect);

			String vehtype="";

		if(batterytype.equals("Car Batteries"))
		{
			vehtype="Car";
		}
		if(batterytype.equals("Bike Batteries"))
		{
			vehtype="Bike";
		}
		if(batterytype.equals("Bus Batteries"))
		{
			vehtype="Bus";
		}
		if(batterytype.equals("Tractor Batteries"))
		{
			vehtype="Tractor";
		}
		if(batterytype.equals("Truck Batteries"))
		{
			vehtype="Truck";
		}
		if(batterytype.equals("Three Wheeler Batteries"))
		{
			vehtype="Three Wheeler";
		}
		if(batterytype.equals("Special Vehicle Batteries"))
		{
			vehtype="Special Vehicle";
		}
		if(batterytype.equals("Genset Batteries"))
		{
			vehtype="Genset";
		}
		if(batterytype.equals("Crane Batteries"))
		{
			vehtype="Crane";
		}
		if(batterytype.equals("Roller Batteries"))
		{
			vehtype="Roller";
		}
		if(batterytype.equals("Loader Batteries"))
		{
			vehtype="Loader";
		}
		if(batterytype.equals("Dozer Batteries"))
		{
			vehtype="Dozer";
		}
		if(batterytype.equals("Excavator Batteries"))
		{
			vehtype="Excavator";
		}
		if(batterytype.equals("Tyre Handler Batteries"))
		{
			vehtype="Tyre Handler";
		}
		if(batterytype.equals("Hydraulic Shovel Batteries"))
		{
			vehtype="Hydraulic Shovel";
		}
		if(batterytype.equals("Harvestor Batteries"))
		{
			vehtype="Harvestor";
		}
		if(batterytype.equals("Generator Batteries"))
		{
			vehtype="Generator";
		}
		if(batterytype.equals("Compactor Batteries"))
		{
			vehtype="Compactor";
		}
		if(batterytype.equals("Telescopic Handler Batteries"))
		{
			vehtype="Telescopic Handler";
		}
		if(batterytype.equals("Forwarder Batteries"))
		{
			vehtype="Forwarder";
		}
		if(batterytype.equals("Wheeled Harvester Batteries"))
		{
			vehtype="Wheeled Harvester";
		}
		if(batterytype.equals("Minibus Batteries"))
		{
			vehtype="Minibus";
		}
		if(batterytype.equals("Dumper Batteries"))
		{
			vehtype="Dumper";
		}
		if(batterytype.equals("Construction Equipment Batteries"))
		{
			vehtype="Construction Equipment";
		}
		if(batterytype.equals("Hydralic Excavator Batteries"))
		{
			vehtype="Hydralic Excavator";
		}
			if(vehidvect.isEmpty())
			{ 
				out.println("<option value='0' selected>Select&nbsp;"+vehtype+"&nbsp;Fuel&nbsp;Type</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<vehidvect.size(); i++)
					{
						if(i==0)
						{
						out.print("<option value='0' selected>Select&nbsp;"+vehtype+"&nbsp;Fuel&nbsp;Type</option>");
						}
						Hashtable ht=(Hashtable)vehidvect.get(i);
						//veh_name_fetched =String.valueOf(ht.get("vehicle_name"));
						fuel_type =String.valueOf(ht.get("fuel_type"));
						out.print("<option  value='"+fuel_type+"'>"+fuel_type+"</option>"); 
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
	public String getbatterybrand(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String keyword= (req.getParameter("keyword")!=null)?(req.getParameter("keyword")):"";
			LogLevel.DEBUG(5, new Throwable(), "keyword :" + keyword);

			String vehiclemodel= (req.getParameter("vehiclemodel")!=null)?(req.getParameter("vehiclemodel")):"";
			LogLevel.DEBUG(5, new Throwable(), "vehiclemodel :" + vehiclemodel);

			//String vehiclename= (req.getParameter("vehiclename")!=null)?(req.getParameter("vehiclename")):"";
			//String bat_id="";
			String battery_brand="";	
			String strSqlQry ="";	
	 		ServletOutputStream out=res.getOutputStream();

			if(keyword.equals("Common"))
			{
				strSqlQry ="select distinct(bat_brand) from battery_details where bat_brand NOT IN ('Luminous') order by bat_brand asc";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
			}
			else
			{
				strSqlQry ="select distinct(bat_brand) from battery_details where bat_brand='"+keyword+"' order by bat_brand asc";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
			}

			Vector BatteryVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"BatteryVector:"+BatteryVector );

			if(BatteryVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='0'>&nbsp;Select&nbsp;Battery&nbsp;Brand</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<BatteryVector.size(); i++)
					{
						if(keyword.equals("Common"))
						{
								if(i==0)
								{
								out.print("<option value='0' selected>&nbsp;Select&nbsp;Battery&nbsp;Brand</option>");
								out.print("<option style='' value='All'>All</option>");
								}
						}
						else
						{
							if(i==0)
								{
								out.print("<option value='0' selected>&nbsp;Select&nbsp;Battery&nbsp;Brand</option>");
								}
						}
						Hashtable ht1=(Hashtable)BatteryVector.get(i);
						//bat_id =String.valueOf(ht1.get("bat_id"));
						battery_brand =String.valueOf(ht1.get("bat_brand"));
						out.print("<option  value='"+battery_brand+"'>"+battery_brand+"</option>"); 
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
	public String getbat_brand(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String keyword= (req.getParameter("keyword")!=null)?(req.getParameter("keyword")):"";
			LogLevel.DEBUG(5, new Throwable(), "keyword :" + keyword);

			String vehiclemodel= (req.getParameter("vehiclemodel")!=null)?(req.getParameter("vehiclemodel")):"";
			LogLevel.DEBUG(5, new Throwable(), "vehiclemodel :" + vehiclemodel);

			//String vehiclename= (req.getParameter("vehiclename")!=null)?(req.getParameter("vehiclename")):"";
			//String bat_id="";
			String battery_brand="";	
			String strSqlQry ="";	
	 		ServletOutputStream out=res.getOutputStream();

			if(keyword.equals("Common"))
			{
				strSqlQry ="select distinct(bat_brand) from battery_details order by bat_brand asc";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
			}
			else
			{
				strSqlQry ="select distinct(bat_brand) from battery_details where bat_brand='"+keyword+"' order by bat_brand asc";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
			}

			Vector BatteryVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"BatteryVector:"+BatteryVector );

			if(BatteryVector.isEmpty())
			{ 
				out.println("<option value='0' selected>&nbsp;Select&nbsp;Battery&nbsp;Brand</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<BatteryVector.size(); i++)
					{
						if(keyword.equals("Common"))
						{
								if(i==0)
								{
								out.print("<option value='0' selected>&nbsp;Select&nbsp;Battery&nbsp;Brand</option>");
								out.print("<option style='' value='All'>All</option>");
								}
						}
						else
						{
							if(i==0)
								{
								out.print("<option value='0' selected>&nbsp;Select&nbsp;Battery&nbsp;Brand</option>");
								}
						}
						Hashtable ht1=(Hashtable)BatteryVector.get(i);
						//bat_id =String.valueOf(ht1.get("bat_id"));
						battery_brand =String.valueOf(ht1.get("bat_brand"));
						out.print("<option  value='"+battery_brand+"'>"+battery_brand+"</option>"); 
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
	public String getpincode(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String State= (req.getParameter("state")!=null)?(req.getParameter("state")):"";
			LogLevel.DEBUG(5, new Throwable(), "State :" + State);

			String city= (req.getParameter("city")!=null)?(req.getParameter("city")):"";
			LogLevel.DEBUG(5, new Throwable(), "city :" + city);

			//String vehiclename= (req.getParameter("vehiclename")!=null)?(req.getParameter("vehiclename")):"";
			//String bat_id="";
			String keyword ="Common";	
			String strSqlQry ="";	
			String strpincode ="";	
	 		ServletOutputStream out=res.getOutputStream();

			if(keyword.equals("Common"))
			{
				strSqlQry ="select distinct(pincode) from retailer_location_mapping;";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
			}
			else
			{
				strSqlQry ="select distinct(pincode) from retailer_location_mapping;";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
			}

			Vector BatteryVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"BatteryVector:"+BatteryVector );

			if(BatteryVector.isEmpty())
			{ 
				out.println("<option value='defaultss'>&nbsp;Select&nbsp;Pincode</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<BatteryVector.size(); i++)
					{
						if(i==0)
						{
						out.print("<option value='default'>&nbsp;Select&nbsp;Pincode</option>");
						}
						
						Hashtable ht1=(Hashtable)BatteryVector.get(i);
						//bat_id =String.valueOf(ht1.get("bat_id"));
						strpincode =String.valueOf(ht1.get("pincode"));
						out.print("<option  value='"+strpincode+"'>"+strpincode+"</option>"); 
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
	public String getbat_brand1(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String batterytype= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
			//String bat_id="";
			String battery_brand="";	

	 		ServletOutputStream out=res.getOutputStream();

			
			String strSqlQry ="select distinct(bat_brand) from battery_details order by bat_brand asc";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector BatteryVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"BatteryVector:"+BatteryVector );

			if(BatteryVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&nbsp;Select&nbsp;Battery&nbsp;Brand</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<BatteryVector.size(); i++)
					{
						if(i==0)
						{
						out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&nbsp;Select&nbsp;Battery&nbsp;Brand</option>");
						}
						Hashtable ht1=(Hashtable)BatteryVector.get(i);
						//bat_id =String.valueOf(ht1.get("bat_id"));
						battery_brand =String.valueOf(ht1.get("bat_brand"));
						out.print("<option  value='"+battery_brand+"'>"+battery_brand+"</option>"); 
					}
					if(batterytype.equals("Bike Batteries"))
					{
					out.print("<option  value='SF Sonic'>SF Sonic</option><option  value='Bosch'>Bosch</option><option  value='Prestolite'>Prestolite</option><option  value='Luminous'>Luminous</option>");
					}
					else if(batterytype.equals("Car Batteries"))
				    {
					out.print("<option  value='SF Sonic'>SF Sonic</option><option  value='Bosch'>Bosch</option><option  value='Prestolite'>Prestolite</option><option  value='Su-Kam'>Su-Kam</option><option  value='Luminous'>Luminous</option>");
					}
					else if(batterytype.equals("Inverter Batteries"))
				    {
					out.print("<option  value='SF Sonic'>SF Sonic</option><option  value='Prestolite'>Prestolite</option><option  value='Su-Kam'>Su-Kam</option><option  value='Luminous'>Luminous</option>");
					}
					else
				    {
					out.print("<option  value='SF Sonic'>SF Sonic</option><option  value='Bosch'>Bosch</option><option  value='Prestolite'>Prestolite</option><option  value='Su-Kam'>Su-Kam</option><option  value='Luminous'>Luminous</option>");
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
	public String getstate(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			//String vehiclename= (req.getParameter("vehiclename")!=null)?(req.getParameter("vehiclename")):"";
			//String bat_id="";
			String state="";	

	 		ServletOutputStream out=res.getOutputStream();

			
			String strSqlQry ="select distinct(state) from location_area order by state asc";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector StateVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"StateVector:"+StateVector );

			if(StateVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&nbsp;Select&nbsp;State</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<StateVector.size(); i++)
					{
						if(i==0)
						{
						out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&nbsp;Select&nbsp;State</option>");
						}
						Hashtable ht1=(Hashtable)StateVector.get(i);
						//bat_id =String.valueOf(ht1.get("bat_id"));
						state =String.valueOf(ht1.get("state"));
						out.print("<option  value='"+state.trim()+"'>"+state.trim()+"</option>"); 
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
	public String getservicestate(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	    String strRes="";
		try
		{	
			//String vehiclename= (req.getParameter("vehiclename")!=null)?(req.getParameter("vehiclename")):"";
			//String bat_id="";
			String state="";	

	 		ServletOutputStream out=res.getOutputStream();

			
			String strSqlQry ="select distinct(state) from service_retailer_location_mapping order by state asc";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector StateVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"StateVector:"+StateVector );

			if(StateVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&nbsp;Select&nbsp;State</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<StateVector.size(); i++)
					{
						if(i==0)
						{
						out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&nbsp;Select&nbsp;State</option>");
						}
						Hashtable ht1=(Hashtable)StateVector.get(i);
						//bat_id =String.valueOf(ht1.get("bat_id"));
						state =String.valueOf(ht1.get("state"));
						out.print("<option  value='"+state.trim()+"'>"+state.trim()+"</option>"); 
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
	public String getcity(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String state= (req.getParameter("state")!=null)?(req.getParameter("state")):"";
			//String bat_id="";
			String location="";	

	 		ServletOutputStream out=res.getOutputStream();
			
			String strSqlQry ="select distinct(location) from location_area where state='"+state+"' ORDER BY FIELD(location,'Warangal','Nellore','Tirupati','Visakhapatnam','Vijayawada','Hyderabad','Hubli','Mysore','Mangalore','Bangalore','Coimbatore','Chennai','Kochi','Thrissur','Trivandrum','Aurangabad-Maharashtra','Nagpur','Pune','Mumbai','Vapi','Surat','Rajkot','Ahmedabad','Cuttack','Bhubaneswar','Faridabad','Gurgaon','Siliguri','Kolkata','Ghaziabad','Kanpur Dehat','Kanpur Nagar','Lucknow','Noida','Jaipur','Patna','Guwahati','Raipur','Ludhiana','Ranchi','Bhopal','Indore') desc,location";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector LocationVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"LocationVector:"+LocationVector );

			if(LocationVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&nbsp;Select&nbsp;City</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<LocationVector.size(); i++)
					{
						if(i==0)
						{
						out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&nbsp;Select&nbsp;City</option>");
						}
						Hashtable ht1=(Hashtable)LocationVector.get(i);
						//bat_id =String.valueOf(ht1.get("bat_id"));
						location =String.valueOf(ht1.get("location"));
						out.print("<option  value='"+location.trim()+"'>"+location.trim()+"</option>"); 
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
	
	public String getservicecity(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String state= (req.getParameter("state")!=null)?(req.getParameter("state")):"";
			//String bat_id="";
			String location="";	

	 		ServletOutputStream out=res.getOutputStream();
			
			String strSqlQry ="select distinct(city) from service_retailer_location_mapping where state='"+state+"' ORDER BY FIELD(city,'Warangal','Nellore','Tirupati','Visakhapatnam','Vijayawada','Hyderabad','Hubli','Mysore','Mangalore','Bangalore','Coimbatore','Chennai','Kochi','Thrissur','Trivandrum','Aurangabad-Maharashtra','Nagpur','Pune','Mumbai','Vapi','Surat','Rajkot','Ahmedabad','Cuttack','Bhubaneswar','Faridabad','Gurgaon','Siliguri','Kolkata','Ghaziabad','Kanpur Dehat','Kanpur Nagar','Lucknow','Noida','Jaipur','Patna','Guwahati','Raipur','Ludhiana','Ranchi','Bhopal','Indore') desc,city";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector LocationVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"LocationVector:"+LocationVector );

			if(LocationVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&nbsp;Select&nbsp;City</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<LocationVector.size(); i++)
					{
						if(i==0)
						{
						out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&nbsp;Select&nbsp;City</option>");
						}
						Hashtable ht1=(Hashtable)LocationVector.get(i);
						//bat_id =String.valueOf(ht1.get("bat_id"));
						location =String.valueOf(ht1.get("city"));
						out.print("<option  value='"+location.trim()+"'>"+location.trim()+"</option>"); 
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
	public String getarea(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String city= (req.getParameter("city")!=null)?(req.getParameter("city")):"";
			LogLevel.DEBUG(5,new Throwable(),"city:"+city);
			String strarea="";	

	 		ServletOutputStream out=res.getOutputStream();

			
			String strSqlQry ="select distinct(area) from location_area where location='"+city+"' order by area asc";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector AreaVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"AreaVector:"+AreaVector );

			if(AreaVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&nbsp;Select&nbsp;Area</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<AreaVector.size(); i++)
					{
						if(i==0)
						{
						out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&nbsp;Select&nbsp;Area</option>");
						}
						Hashtable ht1=(Hashtable)AreaVector.get(i);
						//bat_id =String.valueOf(ht1.get("bat_id"));
						strarea =String.valueOf(ht1.get("area"));
						out.print("<option  value='"+strarea+"'>"+strarea+"</option>"); 
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
	public String getservicearea(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String city= (req.getParameter("city")!=null)?(req.getParameter("city")):"";
			LogLevel.DEBUG(5,new Throwable(),"city:"+city);
			String strarea="";	

	 		ServletOutputStream out=res.getOutputStream();

			String strSqlQry ="select distinct(area) from retailer_location_mapping where city='"+city+"' order by area asc";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector AreaVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"AreaVector:"+AreaVector );

			if(AreaVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&nbsp;Select&nbsp;Area</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<AreaVector.size(); i++)
					{
						if(i==0)
						{
						out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&nbsp;Select&nbsp;Area</option>");
						}
						Hashtable ht1=(Hashtable)AreaVector.get(i);
						//bat_id =String.valueOf(ht1.get("bat_id"));
						strarea =String.valueOf(ht1.get("area"));
						out.print("<option  value='"+strarea+"'>"+strarea+"</option>"); 
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
	public String getbat_model(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			//String vehiclename= (req.getParameter("vehiclename")!=null)?(req.getParameter("vehiclename")):"";
			//String bat_id="";
			String battery_model="";	

	 		ServletOutputStream out=res.getOutputStream();

			
			String strSqlQry ="select distinct(bat_model) from battery_details order by bat_model asc";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector BatteryModelVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"BatteryModelVector:"+BatteryModelVector );

			if(BatteryModelVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:11px;color:#CCCCCC;' value='defaultss'>&nbsp;Select&nbsp;Battery&nbsp;Model</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<BatteryModelVector.size(); i++)
					{
						if(i==0)
						{
						out.print("<option style='font-family:Verdana;font-size:11px;color:#CCCCCC;' value='default'>&nbsp;Select&nbsp;Battery&nbsp;Model</option>");
						}
						Hashtable ht1=(Hashtable)BatteryModelVector.get(i);
						//bat_id =String.valueOf(ht1.get("bat_id"));
						battery_model =String.valueOf(ht1.get("bat_model"));
						out.print("<option  value='"+battery_model+"'>"+battery_model+"</option>"); 
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
	public String getbatterymodelsdetailsajax(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String brand= (req.getParameter("brand")!=null)?(req.getParameter("brand")):"";
			LogLevel.DEBUG(5,new Throwable(),"brand:"+brand);
	 		ServletOutputStream out=res.getOutputStream();

			String strvehid = "select distinct(bat_model) from batteryprice where bat_brand='"+brand+"' order by bat_model asc"; 
			LogLevel.DEBUG(5, new Throwable(), "strvehid :" + strvehid);
			
			Vector vehidvec=qm.executeQuery(strvehid);
			LogLevel.DEBUG(5,new Throwable(),"vehidvec:"+vehidvec);
			if(vehidvec.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&nbsp;Select&nbsp;Model</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<vehidvec.size(); i++)
					{
						if(i==0)
						{
						out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&nbsp;Select&nbsp;Model</option>");
						}
						Hashtable ht=(Hashtable)vehidvec.get(i);
						//veh_id_fetched =String.valueOf(ht.get("veh_id"));
						//LogLevel.DEBUG(5,new Throwable(),"veh_id_fetched:"+veh_id_fetched);
						String bat_model =String.valueOf(ht.get("bat_model"));
						LogLevel.DEBUG(5,new Throwable(),"bat_model:"+bat_model);
						out.print("<option style='color: black;' value='"+bat_model+"'>"+bat_model+"</option>"); 
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
	public String getbatterywarrantydetails(HttpServletRequest req,HttpServletResponse res,HttpSession session)
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
					
			strRes=strRes+"<table border='0' style='border: 1px solid orange;border-collapse: collapse;margin-top:15px;' cellspacing='0' align='center' cellpadding='2' bgcolor='#FFFFFF' width='100%'><tr bgcolor='#FFFFFF'><td class='prodheadingbat' width='26.6%' align='center' style='border: 1px solid orange;border-collapse: collapse;'>Battery</td><td  class='prodheadingbat' width='26.6%' align='center' style='border: 1px solid orange;border-collapse: collapse;'>Warranty</td><td class='prodheadingbat' width='26.6%' align='center'>Capacity (Ah)</td></tr>";
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
								iconurl = "./images/noimage.jpg";
							}
							else
							{
								iconurl =iconurl;
							}
							iconurl = iconurl.replaceAll(" ", "%20");
				LogLevel.DEBUG(5,new Throwable(),"iconurl :"+iconurl );

				strRes=strRes+"<tr><td  width='26.6%'  bgcolor='#FFFFFF' align='center'  style='border: 1px solid orange;border-collapse: collapse;'><img  src='"+iconurl+"' width='120' height='120'/> <br/><b>"+batterybrand+"</b><br/><b>"+model+"</b></td>";
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

	/* **************************************************************************************************************************************\
	* This method is to fetch battery brand.
	* @strSqlQry : carries the query to battery brand from battery_details table.
	\* **************************************************************************************************************************************/
	public String getbat_models_orders_update(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String batterytype= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
			LogLevel.DEBUG(5, new Throwable(), "batterytype :" + batterytype);

			String vehiclemake= (req.getParameter("vehiclemake")!=null)?(req.getParameter("vehiclemake")):"";
			LogLevel.DEBUG(5, new Throwable(), "vehiclemake :" + vehiclemake);

			String vehiclemodel= (req.getParameter("vehiclemodel")!=null)?(req.getParameter("vehiclemodel")):"";
			LogLevel.DEBUG(5, new Throwable(), "vehiclemodel :" + vehiclemodel);

			String batbrand= (req.getParameter("batbrand")!=null)?(req.getParameter("batbrand")):"";
			LogLevel.DEBUG(5, new Throwable(), "batbrand :" + batbrand);


			//String bat_id="";
			String battery_model="";
			String strSqlQry ="";

	 		ServletOutputStream out=res.getOutputStream();
			

			if(batterytype.equals("Inverter Batteries") || batterytype.equals("Flat Plate Battery") || batterytype.equals("Tubular Battery") || batterytype.equals("Tall Tubular Battery"))
			{

				strSqlQry ="select distinct(bat_model) from application_chat_mapping where bat_type='"+batterytype+"'  and bat_brand='"+batbrand+"' order by bat_model asc";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
			}
			else
			{
				strSqlQry ="select distinct(bat_model) from application_chat_mapping where bat_type='"+batterytype+"' and veh_name='"+vehiclemake+"' and veh_model='"+vehiclemodel+"' and bat_brand='"+batbrand+"' order by bat_model asc";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
			}

			Vector BatteryModelVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"BatteryModelVector:"+BatteryModelVector );

			if(BatteryModelVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:11px;color:#CCCCCC;' value='defaultss'>&nbsp;Select&nbsp;Battery&nbsp;Model</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<BatteryModelVector.size(); i++)
					{
						if(i==0)
						{
						out.print("<option style='font-family:Verdana;font-size:11px;color:#CCCCCC;' value='default'>&nbsp;Select&nbsp;Battery&nbsp;Model</option>");
						}
						Hashtable ht1=(Hashtable)BatteryModelVector.get(i);
						//bat_id =String.valueOf(ht1.get("bat_id"));
						battery_model =String.valueOf(ht1.get("bat_model"));
						out.print("<option style='background:#FFF' value='"+battery_model+"'>"+battery_model+"</option>"); 
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
	public String getbatpricedetls(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String constate= (req.getParameter("constate")!=null)?(req.getParameter("constate")):"";
			LogLevel.DEBUG(5, new Throwable(), "constate :" + constate);

			String concity= (req.getParameter("concity")!=null)?(req.getParameter("concity")):"";
			LogLevel.DEBUG(5, new Throwable(), "concity :" + concity);

			String batbrand= (req.getParameter("batbrand")!=null)?(req.getParameter("batbrand")):"";
			LogLevel.DEBUG(5, new Throwable(), "batbrand :" + batbrand);

			String batmodel= (req.getParameter("batmodel")!=null)?(req.getParameter("batmodel")):"";
			LogLevel.DEBUG(5, new Throwable(), "batmodel :" + batmodel);


			

	 		ServletOutputStream out=res.getOutputStream();

			
			String strSqlQry ="select bat_witbat_price,bat_ret_price from batteryprice where state='"+constate+"' and city='"+concity+"' and bat_brand='"+batbrand+"' and bat_model='"+batmodel+"' ";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Hashtable ht1 = qm.getRow(strSqlQry); 

			if(ht1.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:11px;color:#CCCCCC;' value='defaultss'>&nbsp;Select&nbsp;Battery&nbsp;Model</option>");
				return strRes;
			}
			else
			{
				
						
						//bat_id =String.valueOf(ht1.get("bat_id"));
						String bat_witbat_price =String.valueOf(ht1.get("bat_witbat_price"));
						String bat_ret_price =String.valueOf(ht1.get("bat_ret_price"));

						int bat_witbat_priceint = Integer.parseInt(bat_witbat_price);
						int bat_ret_priceint = Integer.parseInt(bat_ret_price);
						//out.print("'"+bat_act_price+","+bat_witbat_price+"'"); 
						int strwithoutprice = bat_witbat_priceint - bat_ret_priceint;
						LogLevel.DEBUG(5,new Throwable(),"dddddddhhhhh :"+strwithoutprice );

					strRes=""+bat_witbat_price+","+strwithoutprice+"";
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
