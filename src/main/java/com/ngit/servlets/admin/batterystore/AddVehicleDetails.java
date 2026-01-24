/***********************************************************************		
	NGIT Confidential. 
	@File Name   : AdminBatteryLoginServlet.java
	@Description : This Servlet is used to allow the operator to Login
	@Date        : 30th August 2013
******************************************************************/ 
package com.ngit.servlets.admin.batterystore; 
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import java.io.IOException; 
import java.io.FileInputStream; 
import java.util.Properties; 
import java.util.Hashtable; 
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
/*
 * @author Sai Krishna Daddala.
 */
/* This class is to Admin Login. This class initializes the necessary connection pools and perform the transactions on the pools. */
public class AddVehicleDetails extends HttpServlet 
{
 	private ServletContext context; 
	QueryManager qm;
	String baseURL;
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
		String strWhatToDo = (req.getParameter("hidWhatToDo")!=null)?req.getParameter("hidWhatToDo"):""; 
		/*This methos is used to check vehicle models avliable or not*/
		if(strWhatToDo.equalsIgnoreCase("checkvehmodel"))
		  {
			String strvehmake = (req.getParameter("vehmake")!=null)?(req.getParameter("vehmake")):"";
			LogLevel.DEBUG(5,new Throwable(),"strvehmake :"+strvehmake );

			String strbatterytype = (req.getParameter("battery_type")!=null)?(req.getParameter("battery_type")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatterytype :"+strbatterytype );

			String strvehmodel = (req.getParameter("vehmodel")!=null)?(req.getParameter("vehmodel")):"";
			LogLevel.DEBUG(5,new Throwable(),"strvehmodel :"+strvehmodel );

			String[] battertype = strbatterytype.split(",");
			String battypeids=battertype[0];
			String batterytypes=battertype[1];

			ServletOutputStream out=res.getOutputStream();
			System.out.println("ajaxmodelname called");

			try
			  {
				String strSqlQry = "SELECT vehicle_model FROM vehicle_details WHERE  vehicle_name='"+strvehmake+"'and vehicle_model='"+strvehmodel+"' and battery_type='"+batterytypes+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry :"+strSqlQry);
				
				Hashtable ht = qm.getRow(strSqlQry);
				String vehicle_model=(String)ht.get("vehicle_model");
				LogLevel.DEBUG(5,new Throwable(),"vehicle_model :"+vehicle_model);

				if(ht.isEmpty())
				 { 
					LogLevel.DEBUG(1,new Throwable(),"No Name exists u can continue ");
					out.println("<font color='#00FF00'>Model Avaliable!</font>");
					return;
				 }
				else
				 {
					LogLevel.DEBUG(1,new Throwable(),"");
					out.println("<font color='#FF0000'>Model Already Exists!</font>");
					return;
				  }
			  }
			 catch(IOException ioe)
			  {
					LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
					ioe.printStackTrace();
					out.println("problem Caught IOException");
			  }
			 catch(Exception e)
			  {
					LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					out.println("problem Caught Exception");
			  }
					out.flush();
					out.close();
			}
		/*This methos is used to insert vehicle details into vehicle_details*/
		else if(strWhatToDo.equalsIgnoreCase("addvehicledetails"))
		{
			String strbattypeid = (req.getParameter("battypeid")!=null)?(req.getParameter("battypeid")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbattypeid :"+strbattypeid );

			String strvehmak = (req.getParameter("vehmak")!=null)?(req.getParameter("vehmak")):"";
			LogLevel.DEBUG(5,new Throwable(),"strvehmak :"+strvehmak );

			String strvehmodel = (req.getParameter("vehmodel")!=null)?(req.getParameter("vehmodel")):"";
			LogLevel.DEBUG(5,new Throwable(),"strvehmodel :"+strvehmodel );
			
			String strvehfuel = (req.getParameter("vehfuel")!=null)?(req.getParameter("vehfuel")):"";
			LogLevel.DEBUG(5,new Throwable(),"strvehfuel :"+strvehfuel );

			String strvecdesc = (req.getParameter("vecdesc")!=null)?(req.getParameter("vecdesc")):"";
			LogLevel.DEBUG(5,new Throwable(),"strvecdesc :"+strvecdesc );

				String[] battertype = strbattypeid.split(",");
				String battypeids=battertype[0];
				String batterytypes=battertype[1];

			try
			{
				String strSqlQryveh = "SELECT vehicle_model FROM vehicle_details WHERE  vehicle_name='"+strvehmak+"'and vehicle_model='"+strvehmodel+"' and fuel_type='"+strvehfuel+"' and  battery_type='"+batterytypes+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQryveh :"+strSqlQryveh);
				
				Hashtable ht = qm.getRow(strSqlQryveh);
				String vehicle_model=(String)ht.get("vehicle_model");
				LogLevel.DEBUG(5,new Throwable(),"vehicle_model :"+vehicle_model);

				if(ht.isEmpty())
				 { 
				/*Query to get the Admin User name and Password*/
 				String strSqlQry = "insert into vehicle_details(veh_id,battype_id,battery_type,vehicle_name,vehicle_model,fuel_type,description,creation_date)values(NULL,'"+battypeids+"','"+batterytypes+"','"+strvehmak+"','"+strvehmodel+"','"+strvehfuel+"','"+strvecdesc+"',now())";
				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
				
				int reslt = qm.executeUpdate(strSqlQry);

				if(reslt <0)
				{
						LogLevel.DEBUG(1,new Throwable()," Failed to update your details! ");
						session.setAttribute("priority","1");
						session.setAttribute("sesvehErrorMsg", "<font color='#FF3333' class='top1'>Failed to add Make details! </font> ");
						res.sendRedirect("../jsp/admin/batterystore/automobiles/addvehicles.jsp");
						return;
					}
				   else
				    {
						LogLevel.DEBUG(1,new Throwable(),"Successfully updated your details! ");
						session.setAttribute("sesvehErrorMsg", "<font color='#000000' class='top1'>Successfully added Make details!</font>");
						//session.setAttribute("sescontVector", reslt);
						res.sendRedirect("../jsp/admin/batterystore/automobiles/addvehicles.jsp");
				    }
			     }
				 else
				{
						LogLevel.DEBUG(1,new Throwable(),"Successfully updated your details! ");
						session.setAttribute("sesvehErrorMsg", "<font color='#000000' class='top1'>Already added Vehicle Make "+strvehmak+" and Model "+strvehmodel+".!</font>");
						//session.setAttribute("sescontVector", reslt);
						res.sendRedirect("../jsp/admin/batterystore/automobiles/addvehicles.jsp");
				}
			    }

			    catch(IOException ioe)
			     {
						LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
						ioe.printStackTrace();
						session.setAttribute("sesvehErrorMsg",	"Generall Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/automobiles/addvehicles.jsp");
			     }
			    catch(Exception e)
			     {
						LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
						e.printStackTrace();
						session.setAttribute("sesvehErrorMsg",	"General Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/automobiles/addvehicles.jsp");
			     }
		}
		else if(strWhatToDo.equalsIgnoreCase("getbatterytype"))
		 {
			try
			  {
					String strSqlQry ="select distinct(battery_type) from vehicle_details order by battery_type asc";
					LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

					Vector BatteryTypeVector=qm.executeQuery(strSqlQry);
					LogLevel.DEBUG(5,new Throwable(),"BatteryTypeVector:"+BatteryTypeVector );
					if(BatteryTypeVector.isEmpty())
					{
						LogLevel.DEBUG(1,new Throwable(),"Failed to fetch battery types ");
						session.setAttribute("priority","1");
						session.setAttribute("sesadminloginErrorMsg", "<font color='#00dd00' class='vrb10'>Failed to Battery types! </font> ");
						res.sendRedirect("../jsp/admin/batterystore/automobiles/deleteautomobiles.jsp");
						return;
					}
					else
					{
						LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched Categories");
						session.setAttribute("sesBatteryTypeVector", BatteryTypeVector);
						session.setAttribute("sesErrorMsgss","<font color='#CC0000' class='vrb10'>Successfully Fetched Battery Types.</font> ");
						res.sendRedirect("../jsp/admin/batterystore/automobiles/deleteautomobiles.jsp");
						return;
					}
				}
				catch(IOException ioe)
				{
					//LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
					ioe.printStackTrace();
					session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/automobiles/deleteautomobiles.jsp");
				}
				catch(Exception e)
				{
					//LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/automobiles/deleteautomobiles.jsp");
				}
		     }
		else if(strWhatToDo.equalsIgnoreCase("getmakes"))
		  {
			String batterytype = (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
			String result="";
			try
			  {
			String vehicle_name="";	

	 		ServletOutputStream out=res.getOutputStream();
			
			String strSqlQry ="select distinct(vehicle_name) from vehicle_details where battery_type='"+batterytype+"' order by vehicle_name asc";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector MakeVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"MakeVector:"+MakeVector );

			if(MakeVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&lt;-&nbsp;&nbsp; Select Make &nbsp;&nbsp;-&gt;</option>");
				return;
			}
			else
			{
			for (int i=0; i<MakeVector.size(); i++)
				{
					if(i==0)
					{
					out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&lt;-&nbsp;&nbsp; Select Make &nbsp;&nbsp;-&gt;</option>");
					}
					Hashtable ht1=(Hashtable)MakeVector.get(i);
					//bat_id =String.valueOf(ht1.get("bat_id"));
					vehicle_name =String.valueOf(ht1.get("vehicle_name"));
					out.print("<option style='background:#FFF' value='"+vehicle_name+"'>"+vehicle_name+"</option>"); 
				}
				//out.println(result);
				 }
				}
				catch(Exception e)
				{
					LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
					e.printStackTrace();
				}
				return;
			   } 
	   else if(strWhatToDo.equalsIgnoreCase("getvehiclemodels"))
		  {
			String batterytype = (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
			LogLevel.DEBUG(5,new Throwable(),"batterytype :"+batterytype );

			String make= (req.getParameter("make")!=null)?(req.getParameter("make")):"";
			LogLevel.DEBUG(5,new Throwable(),"make :"+make );

			ServletOutputStream out=res.getOutputStream();
			String strRes="";
			try
			  {  
			/* @strSqlQry3 : carries the query to Insert the user Work details into professional_detl table.*/
				String strSqlQry4 = "select veh_id,vehicle_model from vehicle_details where battery_type='"+batterytype+"' and vehicle_name='"+make+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry4 :"+strSqlQry4 );

				Vector vehmodels=qm.executeQuery(strSqlQry4);
				LogLevel.DEBUG(5,new Throwable(),"vehmodels:"+vehmodels);

			if(vehmodels==null || vehmodels.size() == 0)
			{
				out.println("<p align='center' valign='top' class='insidecontent'><table valign='top' width='500' cellspacing='0' border='1' align='center' bgcolor='#FFFFFF' cellpadding='0'><tr><td><table valign='top' width='500' bgcolor='#FFFFFF' border='0'><tr><td class='insidecontent' align='center'>No Automobiles details Avaliable!</td></tr></table></td></tr></table><a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
			}
			else
			{
				strRes=strRes+"<table border='0' align='center' bgcolor='#FFFFFF' width='700'><tr>";
				//strRes=strRes+"<td align='center'  valign='top'><input type='button' onClick=\"javascript:deleteappichatdetails();\" value='DELETE' class='button4'></td></tr></table>";
						
				strRes=strRes+"<table border='0' cellspacing='1' align='center' cellpadding='2' bgcolor='#FFFFFF' width='100%'><tr bgcolor='#2364b1'><td class='prodheading' width='20%'>Model</td><td class='prodheading' width='20%'>DELETE</td></tr>";
			  	for(int j=0; j<vehmodels.size();j++)
				{
					Hashtable ht1=(Hashtable)vehmodels.get(j);
					String vehid=String.valueOf(ht1.get("veh_id"));
					String strvehmodel=(String)ht1.get("vehicle_model");
					LogLevel.DEBUG(5,new Throwable(),"vehid :"+vehid );

					int Jcnt=j+1;
					
					strRes=strRes+"<table bgcolor='#FFFFFF' width='100%' cellspacing='1' border='0' align='center'  cellpadding='2'><input type='checkbox' name='chkSi' value='' style='display:none'/>";
					//strRes=strRes+"<tr><td width='6%' class='table1' align='left' >"+Jcnt+"<input type='checkbox' name='chkSi' value='"+strmapid+"'/></td>";
					strRes=strRes+"<td width='20%'   class='table1 align='left'  >"+strvehmodel+"</td>";
					strRes=strRes+"<td  width='20%'  class='table1 align='left'  ><a href=\"javascript:deleteautomobiles('"+vehid+"','"+strvehmodel+"');\">Delete</td></tr>";
					strRes=strRes+"</table>";
					strRes=strRes+"</table>";
				} 
				out.println(strRes);
					return;				
				}			
				}
			   catch(IOException ioe)
			    {
					LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
					ioe.printStackTrace();
					session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/automobiles/deletautomobiles.jsp");
			    }
			   catch(Exception e)
			    {
					LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/automobiles/deletautomobiles.jsp");
			    }
		  }
		  /*This methos is used to insert vehicle details into vehicle_details*/
		else if(strWhatToDo.equalsIgnoreCase("deleterautomobiles"))
		{
		String chkSi= (req.getParameter("chkSi")!=null)?(req.getParameter("chkSi")):"";
		LogLevel.DEBUG(5, new Throwable(), "chkSi :" + chkSi);
		String batterytype= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
		String make= (req.getParameter("make")!=null)?(req.getParameter("make")):"";
		String vehmodel= (req.getParameter("vehmodel")!=null)?(req.getParameter("vehmodel")):"";

		String strRes="";
		try
		{	
			ServletOutputStream out=res.getOutputStream();
			
			String strSqlQrychk = "SELECT bat_model FROM application_chat_mapping WHERE  bat_type='"+batterytype+"' and veh_name='"+make+"' and veh_model='"+vehmodel+"'";				
			LogLevel.DEBUG(5,new Throwable(),"strSqlQrychk :"+strSqlQrychk);

			Hashtable htchk = qm.getRow(strSqlQrychk);
			String bat_model=(String)htchk.get("bat_model");
			LogLevel.DEBUG(5,new Throwable(),"bat_model :"+bat_model);

				if(htchk.isEmpty())
				 {  
						String strSqlQry = "delete from vehicle_details where veh_id='"+chkSi+"'";
						LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );

						int i=qm.executeUpdate(strSqlQry);

						if(i <0)
						{
							LogLevel.DEBUG(1,new Throwable(),"No Name exists u can continue ");
							session.setAttribute("priority","1");
							session.setAttribute("sesErrorMsg", "<font color='#00dd00' class='vrb10'></font> ");
							out.println("Failed to delete Automobile details ");
						}
						else
						{
							LogLevel.DEBUG(1,new Throwable(),"");
							session.setAttribute("priority","1");
							session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'></font> ");
							//System.out.println(email);
							out.println("Successfully deleted Automobile details");
						}
				 }
				else
				 {
					LogLevel.DEBUG(1,new Throwable(),"");
					out.println("<font color='#FF0000'>Please delete same details first in Application Chat Mapping and try Here.  Click <a href=\"../jsp/admin/batterystore/applicationchat/editapplicationchatmapping.jsp\">Here</a> to go Application Chat Mapping!</font>");
					return;
				  }			
		}
		catch (Exception e)
		{
			LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
			e.printStackTrace();
		}
		return;
		}

	}//End of dopost
}//end of Class