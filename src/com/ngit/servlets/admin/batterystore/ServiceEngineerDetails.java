/* 
Author(s)		: Chandra Prakash G.
File Name		: RetailerRegistration.java
Created on		: July 09, 2013.
Copyright Notice	: BookBattery Pvt.Ltd. Confidential
Description		: Retailer Registration
*/
package com.ngit.servlets.admin.batterystore; 
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.sendsms.SendSMS;
import com.ngit.javabean.mail.*;
import java.io.IOException; 
import java.io.FileInputStream; 
import java.util.Properties;
import java.util.Hashtable; 
import java.util.ArrayList; 
import java.util.Vector; 
import javax.servlet.ServletConfig; 
import javax.servlet.ServletContext; 
import javax.servlet.ServletException; 
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse; 
import javax.servlet.http.HttpSession;
import java.io.File;
/*This class is used to register a retailer*/
public class ServiceEngineerDetails extends HttpServlet 
{ 
	private ServletContext context; 
	QueryManager qm;
	static final long serialVersionUID=21;
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
			String strLogFilePath = (propsMOPConfig.getProperty("LogFilePath")!=null)?propsMOPConfig.getProperty("LogFilePath"):"";
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
	/* This doGet service method calls doPost service method to handle all the requests and responses to perform register a retailer. */
	public void doGet (HttpServletRequest req , HttpServletResponse res )throws IOException , ServletException
	{
		doPost(req, res);
	}
	/* This doPost service method handles all the requests and responses passing from doGet service method to perform register a retailer.*/
	public void doPost( HttpServletRequest req , HttpServletResponse res )throws IOException , ServletException
	{ 
		res.setContentType("text/html;charset=UTF-8");
        HttpSession session=req.getSession(true);
 		session=req.getSession(true);
		String struserName=(String)session.getAttribute("sesBatteryAdminName"); 
		LogLevel.DEBUG(5,new Throwable(),"struserName :"+struserName );
		Properties propsMOPConfig = (Properties)context.getAttribute("contextPropMOPConfig"); 	
		String domainname =  propsMOPConfig.getProperty("domainname");
		String fromemailid =  propsMOPConfig.getProperty("SupportEmailId");
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
		ServletOutputStream out=res.getOutputStream();
		String strWhatToDo        = (req.getParameter("hidWhatToDo")!=null)?req.getParameter("hidWhatToDo"):""; 
		/*This action is to add the retailers*/
		
		/*This action is used to insert Service Engineer Details*/
		 if(strWhatToDo.equalsIgnoreCase("insertserviceengineerdetails"))
		{ 			
			try
			{ 
 				String strinsertserviceengineerdetails = insertserviceengineerdetails(req ,res,struserName,session);
				out.println(strinsertserviceengineerdetails);
				out.close();
			} 
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
				out.println("-1");
				out.close();
			}
		}
		/*This action is used to insert GSTID*/
		else if(strWhatToDo.equalsIgnoreCase("getServiceEngineerdetails"))
		{ 			
			try
			{ 
 				String strgetServiceEngineerdetails = getServiceEngineerdetails(req ,res,struserName,session);
				out.println(strgetServiceEngineerdetails);
				out.close();
			} 
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
				out.println("-1");
				out.close();
			}
		}		
		/*This action is used to insert GSTID*/
		else if(strWhatToDo.equalsIgnoreCase("insertpincodemappings"))
		{ 			
			try
			{ 
 				String strinsertpincodemappings = insertpincodemappings(req ,res,struserName,session);
				out.println(strinsertpincodemappings);
				out.close();
			} 
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
				out.println("-1");
				out.close();
			}
		}
		
	}//end of do post
	/* This method is used to insert Retailer details into retailers table.*/

	/*****this method is added to insert Service Engineer Details**/

	public String insertserviceengineerdetails(HttpServletRequest req , HttpServletResponse res, String struserName,HttpSession session)
    {
		String strResGSTRetname="";
		String state = (req.getParameter("state")!=null)?(req.getParameter("state")):"0";
		LogLevel.DEBUG(5,new Throwable(),"state :"+state);
		String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"0";
		LogLevel.DEBUG(5,new Throwable(),"city :"+city);
		//String pincode = (req.getParameter("pincode")!=null)?(req.getParameter("pincode")):"0";
		String username = (req.getParameter("username")!=null)?(req.getParameter("username")):"0";
		String mobilenumber = (req.getParameter("mobilenumber")!=null)?(req.getParameter("mobilenumber")):"0";
		try
		{  
			String strSqlQry4 = "select register_id from service_engineers where mobilenumber="+mobilenumber+"";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQry4 :"+strSqlQry4 );

			Vector Service_Engineer_Vector=qm.executeQuery(strSqlQry4);
			LogLevel.DEBUG(5,new Throwable(),"Service_Engineer_Vector:"+Service_Engineer_Vector);
			
			if(Service_Engineer_Vector==null || Service_Engineer_Vector.size() == 0)
			{
				String insergstretailer = "insert into service_engineers(register_id,mobilenumber,state,location,username) values(NULL,'"+mobilenumber+"','"+state+"','"+city+"','"+username+"')";
				
				int Insertgstdetails = qm.executeUpdate(insergstretailer);
				if(Insertgstdetails > 0)
				{
				  strResGSTRetname+="<p align='center' class='insidecontent'>Successfully inserted details!<a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>";
				}
				else
				{
				  strResGSTRetname+="<p align='center' class='insidecontent'>Failed to insert details!<a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>";
				}
			}
			else
			{
				strResGSTRetname+="<p align='center' class='insidecontent'>Engineer Details are already Exists.<a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>";
			}
		} 
		catch(Exception e)
		{ 
			LogLevel.ERROR(0,e,"problem Caught Exception !! "+e);
			e.printStackTrace(); 
			session.setAttribute("sesErrorMsg",	"Error...Please Try Again" );
		} 

		return strResGSTRetname;
	}	
	
/*****this method is added to insert Service Engineer Details**/

	public String getServiceEngineerdetails(HttpServletRequest req , HttpServletResponse res, String struserName,HttpSession session)
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
			String username ="";	
			String mobilenumber ="";	
	 		ServletOutputStream out=res.getOutputStream();

			strSqlQry ="select distinct(username),mobilenumber from service_engineers where state='"+State+"' and location='"+city+"';";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector ServiceEngineerVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"ServiceEngineerVector:"+ServiceEngineerVector );

			if(ServiceEngineerVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&nbsp;Select&nbsp;Service&nbsp;Engineer</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<ServiceEngineerVector.size(); i++)
					{
						if(i==0)
						{
						out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&nbsp;Select&nbsp;Service&nbsp;Engineer</option>");
						}
						
						Hashtable ht1=(Hashtable)ServiceEngineerVector.get(i);
						//bat_id =String.valueOf(ht1.get("bat_id"));
						username =String.valueOf(ht1.get("username"));
						mobilenumber =String.valueOf(ht1.get("mobilenumber"));
						out.print("<option  value='"+username+"-"+mobilenumber+"'>"+username+"-"+mobilenumber+"</option>"); 
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
	/*****this method is added to insert Service Engineer Mapping Details**//*****this method is added to insert Service Engineer Details**/

	public String insertpincodemappings(HttpServletRequest req , HttpServletResponse res, String struserName,HttpSession session)
    {
		String strResGSTRetname="";
		String register_id="";
		String username="";
		String mobilenumber = "";
		String state = (req.getParameter("state")!=null)?(req.getParameter("state")):"0";
		String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"0";
		String serviceengineer = (req.getParameter("serviceengineer")!=null)?(req.getParameter("serviceengineer")):"0";
		LogLevel.DEBUG(5,new Throwable(),"serviceengineer:"+serviceengineer);
		
		String[] serviceengineer_array = serviceengineer.split("-");
		LogLevel.DEBUG(5,new Throwable(),"serviceengineer_array:"+serviceengineer_array);
		LogLevel.DEBUG(5,new Throwable(),"serviceengineer_array[0]:"+serviceengineer_array[0]);
		LogLevel.DEBUG(5,new Throwable(),"serviceengineer_array[1]:"+serviceengineer_array[1]);
		username=serviceengineer_array[0];
		mobilenumber=serviceengineer_array[1];
		
		String pincode = (req.getParameter("pincode")!=null)?(req.getParameter("pincode")):"0";
		LogLevel.DEBUG(5,new Throwable(),"pincode:"+pincode);
		
		try
		{  
			String strSqlQry4 = "select register_id,username from service_engineers where mobilenumber="+mobilenumber+"";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQry4 :"+strSqlQry4 );

			Vector Service_Engineer_Vector=qm.executeQuery(strSqlQry4);
			LogLevel.DEBUG(5,new Throwable(),"Service_Engineer_Vector:"+Service_Engineer_Vector);
			
			if(Service_Engineer_Vector!=null || Service_Engineer_Vector.size() != 0)
			{
				Hashtable ht2=(Hashtable)Service_Engineer_Vector.get(0);
				//bat_id =String.valueOf(ht1.get("bat_id"));
				register_id =String.valueOf(ht2.get("register_id"));
				username =String.valueOf(ht2.get("username"));
				
				String[] pincode_array = pincode.split(",");
				LogLevel.DEBUG(5,new Throwable(),"pincode_array:"+pincode_array);
				String insergstretailer="";
				int Insertgstdetails=0;
				
				
				for(int j=0; j<pincode_array.length; j++)
				{
					String strSqlQry5 = "select mapping_id,service_engineer_id,username,pincode from service_engineer_mappings where mobilenumber="+mobilenumber+" and pincode='"+pincode_array[j]+"'";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry5 :"+strSqlQry5 );

					Vector Service_Mapping_Vector=qm.executeQuery(strSqlQry5);
					LogLevel.DEBUG(5,new Throwable(),"Service_Mapping_Vector:"+Service_Mapping_Vector); 
					 
					 
						if(Service_Engineer_Vector!=null || Service_Engineer_Vector.size() != 0)
						{
							insergstretailer = "insert into service_engineer_mappings(mapping_id,service_engineer_id,state,location,username,pincode) values(NULL,'"+register_id+"','"+state+"','"+city+"','"+username+"','"+pincode_array[j]+"')";
							Insertgstdetails = qm.executeUpdate(insergstretailer);	
							LogLevel.DEBUG(5,new Throwable(),"Insertgstdetails:"+Insertgstdetails);


						}
						else
						{

						}
					}
					
					if(Insertgstdetails > 0)
					{
					strResGSTRetname+="<p align='center' class='insidecontent'>Successfully inserted details!<a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>";
					}
					else
					{
					strResGSTRetname+="<p align='center' class='insidecontent'>Failed to insert details!<a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>";
					}
			}
			else
			{
				strResGSTRetname+="<p align='center' class='insidecontent'>Engineer Details does not exists. Add Engineer Details<a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>";
			}
		} 
		catch(Exception e)
		{ 
			LogLevel.ERROR(0,e,"problem Caught Exception !! "+e);
			e.printStackTrace(); 
			session.setAttribute("sesErrorMsg",	"Error...Please Try Again" );
		} 

		return strResGSTRetname;
	}	
	/*****this method is added to insert Service Engineer Mapping Details**/

}//End of class
