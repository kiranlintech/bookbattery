/***********************************************************************		
	NGIT Confidential. 
	@File Name   : GISViewConstituency.java 
	@Description : This Servlet is used to select the GISViewConstituency Details.
	@Author	     : Sai Krishna Daddala.
	@Date        : 30th August 2013
******************************************************************/ 
 
package com.ngit.servlets.consumers.batterystore; 
import com.ngit.javabean.mail.*;
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import java.util.Properties;
import java.util.Vector;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.*;
import java.io.*;
import java.io.PrintWriter;
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

public class InlineMakeSearch extends HttpServlet 
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
		String struserName=(String)session.getAttribute("sesSuperUserName");
		Properties propsMOPConfig = (Properties)context.getAttribute("contextPropMOPConfig");
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
		String strIpAddress =(propsMOPConfig.getProperty("TRANSMITTER_IP_ADDR")!=null)?(propsMOPConfig.getProperty("TRANSMITTER_IP_ADDR")):"";
		String strPort=(propsMOPConfig.getProperty("TRANSMITTER_IP_PORT")!=null)?(propsMOPConfig.getProperty("TRANSMITTER_IP_PORT")):"9910";
		String SMSFromAddress=(propsMOPConfig.getProperty("SMSFromAddress")!=null)?(propsMOPConfig.getProperty("SMSFromAddress")):"";
		String FromEmailId=(propsMOPConfig.getProperty("SupportEmailId")!=null)?(propsMOPConfig.getProperty("SupportEmailId")):"";
		String strsuppemaild=(propsMOPConfig.getProperty("suppemaild")!=null)?(propsMOPConfig.getProperty("suppemaild")):"";
		String domainname = (propsMOPConfig.getProperty("domainname")!=null)?(propsMOPConfig.getProperty("domainname")).trim():"";
		PrintWriter out = res.getWriter();

		/* ****************************************************************************************\
		* This action is used to get State name.
		\* *****************************************************************************************/		
	
				String s =  (req.getParameter("com") != null) ? (req.getParameter("com")) : "";
				LogLevel.DEBUG(5,new Throwable(),"s :"+s);

				String vehiclename= (req.getParameter("vehiclename")!=null)?(req.getParameter("vehiclename")):"";
				LogLevel.DEBUG(5,new Throwable(),"vehiclename:"+vehiclename);
				
				String batterytype= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
				LogLevel.DEBUG(5,new Throwable(),"batterytype:"+batterytype);
			
				String keyword =  (req.getParameter("keyword") != null) ? (req.getParameter("keyword")) : "";
				LogLevel.DEBUG(5,new Throwable(),"keyword :"+keyword);	

				Vector panchayatnamevector = new Vector();
				
				String strveh_idqry = "select veh_id,vehicle_model from vehicle_details where vehicle_name='"+vehiclename+"' and battery_type='"+batterytype+"' order by vehicle_model asc"; 
				LogLevel.DEBUG(5, new Throwable(), "strveh_idqry :" + strveh_idqry);
				panchayatnamevector=qm.executeQuery(strveh_idqry);
					
				LogLevel.DEBUG(5,new Throwable(),"panchayatnamevector :"+panchayatnamevector);	
				String strRes="";
				ArrayList<String> as=new ArrayList<String>();

				s=s.toLowerCase();

				for(int j=0; j<panchayatnamevector.size();j++)
				{			
					Hashtable panchayatht=(Hashtable)panchayatnamevector.get(j);
					String vehicle_model=String.valueOf(panchayatht.get("vehicle_model"));
					LogLevel.DEBUG(5,new Throwable(),"vehicle_model:"+vehicle_model);

					String vehiclemodellower=vehicle_model.toLowerCase();
					LogLevel.DEBUG(5,new Throwable(),"vehiclemodellower :"+vehiclemodellower);
					
						if(keyword.equals("onchange"))
						{
							as.add(vehicle_model);	
						}
						else
						{
							if(vehiclemodellower.startsWith(s)) 
							{
								LogLevel.DEBUG(5,new Throwable(),"if :"+vehiclemodellower);							
								as.add(vehicle_model);							
							}
						}
					
				}
				LogLevel.DEBUG(5,new Throwable(),"as:"+as);
			
				for(String st:as)
				{					
						out.println(st);					
				}
				out.close();
	}
	

}// end of class
