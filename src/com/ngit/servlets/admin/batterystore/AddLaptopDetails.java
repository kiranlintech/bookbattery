/***********************************************************************		
	NGIT Confidential. 
	@File Name   : AddLaptopDetails.java
	@Description : This Servlet is used to allow the operator to Login
	@Date        : 6th Feb 2014
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
 * @author Sai Krishna Daddala
 */
/* This class is to Admin Login. This class initializes the necessary connection pools and perform the transactions on the pools. */
public class AddLaptopDetails extends HttpServlet 
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
		if(strWhatToDo.equalsIgnoreCase("checklaptopproduct"))
		  {
			String laptopbrand = (req.getParameter("laptopbrand")!=null)?(req.getParameter("laptopbrand")):"";
			LogLevel.DEBUG(5,new Throwable(),"laptopbrand :"+laptopbrand );

			String strbatterytype = (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatterytype :"+strbatterytype );

			String laptopmodel = (req.getParameter("laptopmodel")!=null)?(req.getParameter("laptopmodel")):"";
			LogLevel.DEBUG(5,new Throwable(),"laptopmodel :"+laptopmodel );

			String laptopproduct = (req.getParameter("laptopproduct")!=null)?(req.getParameter("laptopproduct")):"";
			LogLevel.DEBUG(5,new Throwable(),"laptopproduct :"+laptopproduct );

			String[] battertype = strbatterytype.split(",");
			String battypeids=battertype[0];
			String batterytypes=battertype[1];

			ServletOutputStream out=res.getOutputStream();
			System.out.println("ajaxmodelname called");

			try
			  {
				String strSqlQry = "SELECT laptop_product FROM laptop_details WHERE  laptop_name='"+laptopbrand+"'and laptop_model='"+laptopmodel+"' and laptop_product='"+laptopproduct+"' and battery_type='"+batterytypes+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry :"+strSqlQry);
				
				Hashtable ht = qm.getRow(strSqlQry);
				String laptop_product=(String)ht.get("laptop_product");
				LogLevel.DEBUG(5,new Throwable(),"laptop_product :"+laptop_product);

				if(ht.isEmpty())
				 { 
					LogLevel.DEBUG(1,new Throwable(),"No Name exists u can continue ");
					out.println("<font color='#00FF00'>Product Avaliable!</font>");
					return;
				 }
				else
				 {
					LogLevel.DEBUG(1,new Throwable(),"");
					out.println("<font color='#FF0000'>Product Already Exists!</font>");
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
		else if(strWhatToDo.equalsIgnoreCase("addlaptopdetails"))
		{
			String strbattypeid = (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbattypeid :"+strbattypeid );

			String laptopbrand = (req.getParameter("laptopbrand")!=null)?(req.getParameter("laptopbrand")):"";
			LogLevel.DEBUG(5,new Throwable(),"laptopbrand :"+laptopbrand );

			String laptopmodel = (req.getParameter("laptopmodel")!=null)?(req.getParameter("laptopmodel")):"";
			LogLevel.DEBUG(5,new Throwable(),"laptopmodel :"+laptopmodel );

			String laptopproduct = (req.getParameter("laptopproduct")!=null)?(req.getParameter("laptopproduct")):"";
			LogLevel.DEBUG(5,new Throwable(),"laptopproduct :"+laptopproduct );

			String description = (req.getParameter("description")!=null)?(req.getParameter("description")):"";
			LogLevel.DEBUG(5,new Throwable(),"description :"+description );

				String[] battertype = strbattypeid.split(",");
				String battypeids=battertype[0];
				String batterytypes=battertype[1];

			try
			{
				String strSqlQryveh = "SELECT laptop_product FROM laptop_details WHERE  laptop_name='"+laptopbrand+"'and laptop_model='"+laptopmodel+"' and laptop_product='"+laptopproduct+"' and  battery_type='"+batterytypes+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQryveh :"+strSqlQryveh);
				
				Hashtable ht = qm.getRow(strSqlQryveh);
				String laptop_product=(String)ht.get("laptop_product");
				LogLevel.DEBUG(5,new Throwable(),"laptop_product :"+laptop_product);

				if(ht.isEmpty())
				 { 
				/*Query to get the Admin User name and Password*/
 				String strSqlQry = "insert into laptop_details(laptop_id,batterytype_id,battery_type,laptop_name,laptop_model,laptop_product,description,creation_date)values(NULL,'"+battypeids+"','"+batterytypes+"','"+laptopbrand+"','"+laptopmodel+"','"+laptopproduct+"','"+description+"',now())";
				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
				
				int reslt = qm.executeUpdate(strSqlQry);

				if(reslt <0)
				{
						LogLevel.DEBUG(1,new Throwable()," Failed to update your details! ");
						session.setAttribute("priority","1");
						session.setAttribute("sesvehErrorMsg", "<font color='#FF3333' class='top1'>Failed to add laptop details! </font> ");
						res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/addlaptop.jsp");
						return;
					}
				   else
				    {
						LogLevel.DEBUG(1,new Throwable(),"Successfully updated your details! ");
						session.setAttribute("sesvehErrorMsg", "<font color='#000000' class='top1'>Successfully added laptop details!</font>");
						//session.setAttribute("sescontVector", reslt);
						res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/addlaptop.jsp");
				    }
			     }
				 else
				{
						LogLevel.DEBUG(1,new Throwable(),"Successfully updated your details! ");
						session.setAttribute("sesvehErrorMsg", "<font color='#000000' class='top1'>Already added Vehicle Make "+laptopbrand+" and Model "+laptopmodel+" and Product "+laptopproduct+".!</font>");
						//session.setAttribute("sescontVector", reslt);
						res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/addlaptop.jsp");
				}
			    }

			    catch(IOException ioe)
			     {
						LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
						ioe.printStackTrace();
						session.setAttribute("sesvehErrorMsg",	"Generall Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/addlaptop.jsp");
			     }
			    catch(Exception e)
			     {
						LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
						e.printStackTrace();
						session.setAttribute("sesvehErrorMsg",	"General Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/addlaptop.jsp");
			     }
		}
		
		else if(strWhatToDo.equalsIgnoreCase("getlaptopbatterytype"))
		  {
			try
			  {
				String strSqlQry ="select distinct(battery_type) from laptop_details order by battery_type asc";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

				Vector LaptopBatteryTypeVector=qm.executeQuery(strSqlQry);
				LogLevel.DEBUG(5,new Throwable(),"LaptopBatteryTypeVector:"+LaptopBatteryTypeVector );
				if(LaptopBatteryTypeVector.isEmpty())
				{
					LogLevel.DEBUG(1,new Throwable(),"Failed to fetch battery types ");
					session.setAttribute("priority","1");
					session.setAttribute("sesLaptopErrorMsg", "<font color='#00dd00' class='vrb10'>Failed to Laptop Battery types! </font> ");
					res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/deletelaptop.jsp");
					return;
				}
				else
				{
					LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched Categories");
					session.setAttribute("sesLaptopBatteryTypeVector", LaptopBatteryTypeVector);
					session.setAttribute("sesErrorMsgss","<font color='#CC0000' class='vrb10'>Successfully Laptop Fetched Battery Types.</font> ");
					res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/deletelaptop.jsp");
					return;
				}
				}
			catch(IOException ioe)
				{
					//LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
					ioe.printStackTrace();
					session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/deletelaptop.jsp");
				}
			catch(Exception e)
				{
					//LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/deletelaptop.jsp");
				}
		     }
		else if(strWhatToDo.equalsIgnoreCase("getlaptopmakes"))
		  {
			String batterytype = (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
			String result="";
			try
			  {
			String laptopname="";	

	 		ServletOutputStream out=res.getOutputStream();
			
			String strSqlQry ="select distinct(laptop_name) from laptop_details where battery_type='"+batterytype+"' order by laptop_name asc";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector LaptopMakeVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"LaptopMakeVector:"+LaptopMakeVector );

			if(LaptopMakeVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&lt;-&nbsp;&nbsp; Select Laptop Make &nbsp;&nbsp;-&gt;</option>");
				return;
			}
			else
			{
			for (int i=0; i<LaptopMakeVector.size(); i++)
				{
					if(i==0)
					{
					out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&lt;-&nbsp;&nbsp; Select Laptop Make &nbsp;&nbsp;-&gt;</option>");
					}
					Hashtable ht1=(Hashtable)LaptopMakeVector.get(i);
					//bat_id =String.valueOf(ht1.get("bat_id"));
					laptopname =String.valueOf(ht1.get("laptop_name"));
					out.print("<option style='background:#FFF' value='"+laptopname+"'>"+laptopname+"</option>"); 
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
			 else if(strWhatToDo.equalsIgnoreCase("getlaptopmodels"))
			{
			String batterytype = (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
			String make = (req.getParameter("make")!=null)?(req.getParameter("make")):"";
			String result="";
			try
			  {
			String laptopmodel="";	

	 		ServletOutputStream out=res.getOutputStream();
			
			String strSqlQry ="select distinct(laptop_model) from laptop_details where battery_type='"+batterytype+"' and laptop_name='"+make+"' order by laptop_model asc";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector LaptopModelVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"LaptopModelVector:"+LaptopModelVector );

			if(LaptopModelVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&lt;-&nbsp;&nbsp; Select Laptop Make &nbsp;&nbsp;-&gt;</option>");
				return;
			}
			else
			{
			for (int i=0; i<LaptopModelVector.size(); i++)
				{
					if(i==0)
					{
					out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&lt;-&nbsp;&nbsp; Select Laptop Make &nbsp;&nbsp;-&gt;</option>");
					}
					Hashtable ht1=(Hashtable)LaptopModelVector.get(i);
					//bat_id =String.valueOf(ht1.get("bat_id"));
					laptopmodel =String.valueOf(ht1.get("laptop_model"));
					out.print("<option style='background:#FFF' value='"+laptopmodel+"'>"+laptopmodel+"</option>"); 
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
		else if(strWhatToDo.equalsIgnoreCase("getlaptopsproducts"))
		  {
			String batterytype = (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
			LogLevel.DEBUG(5,new Throwable(),"batterytype :"+batterytype );

			String make= (req.getParameter("make")!=null)?(req.getParameter("make")):"";
			LogLevel.DEBUG(5,new Throwable(),"make :"+make );

			String model= (req.getParameter("model")!=null)?(req.getParameter("model")):"";
			LogLevel.DEBUG(5,new Throwable(),"model :"+model );

			ServletOutputStream out=res.getOutputStream();
			String strRes="";
			try
			  {  
			/* @strSqlQry3 : carries the query to Insert the user Work details into professional_detl table.*/
				String strSqlQry4 = "select laptop_id,laptop_product from laptop_details where battery_type='"+batterytype+"' and laptop_name='"+make+"' and laptop_model='"+model+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry4 :"+strSqlQry4 );

				Vector lapproducts=qm.executeQuery(strSqlQry4);
				LogLevel.DEBUG(5,new Throwable(),"lapproducts:"+lapproducts);

			if(lapproducts==null || lapproducts.size() == 0)
			{
				out.println("<p align='center' valign='top' class='insidecontent'><table valign='top' width='500' cellspacing='0' border='1' align='center' bgcolor='#FFFFFF' cellpadding='0'><tr><td><table valign='top' width='500' bgcolor='#FFFFFF' border='0'><tr><td class='insidecontent' align='center'>No Laptops details Avaliable!</td></tr></table></td></tr></table><a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
			}
			else
			{
				strRes=strRes+"<table border='0' align='center' bgcolor='#FFFFFF' width='700'><tr>";
				//strRes=strRes+"<td align='center'  valign='top'><input type='button' onClick=\"javascript:deleteappichatdetails();\" value='DELETE' class='button4'></td></tr></table>";
						
				strRes=strRes+"<table border='0' cellspacing='1' align='center' cellpadding='2' bgcolor='#FFFFFF' width='100%'><tr bgcolor='#2364b1'><td class='prodheading' width='20%'>Product</td><td class='prodheading' width='20%'>DELETE</td></tr>";
			  	for(int j=0; j<lapproducts.size();j++)
				{
					Hashtable ht1=(Hashtable)lapproducts.get(j);
					String lapid=String.valueOf(ht1.get("laptop_id"));
					String strlapproduct=(String)ht1.get("laptop_product");
					LogLevel.DEBUG(5,new Throwable(),"lapid :"+lapid );

					int Jcnt=j+1;
					
					strRes=strRes+"<table bgcolor='#FFFFFF' width='100%' cellspacing='1' border='0' align='center'  cellpadding='2'><input type='checkbox' name='chkSi' value='' style='display:none'/>";
					//strRes=strRes+"<tr><td width='6%' class='table1' align='left' >"+Jcnt+"<input type='checkbox' name='chkSi' value='"+strmapid+"'/></td>";
					strRes=strRes+"<td width='20%'   class='table1 align='left'  >"+strlapproduct+"</td>";
					strRes=strRes+"<td  width='20%'  class='table1 align='left'  ><a href=\"javascript:deletelaptops('"+lapid+"','"+strlapproduct+"');\">Delete</td></tr>";
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
					res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/deletelaptop.jsp");
			    }
			   catch(Exception e)
			    {
					LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/laptopbatterywale/deletelaptop.jsp");
			    }
		  }
	   /*This methos is used to insert vehicle details into vehicle_details*/
		else if(strWhatToDo.equalsIgnoreCase("deletelaptops"))
		{
		String batterytype= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
		LogLevel.DEBUG(5, new Throwable(), "batterytype :" + batterytype);

		String make= (req.getParameter("make")!=null)?(req.getParameter("make")):"";
		LogLevel.DEBUG(5, new Throwable(), "make :" + make);

		String model= (req.getParameter("model")!=null)?(req.getParameter("model")):"";
		LogLevel.DEBUG(5, new Throwable(), "model :" + model);

		String laptopproduct= (req.getParameter("laptopproduct")!=null)?(req.getParameter("laptopproduct")):"";
		LogLevel.DEBUG(5, new Throwable(), "laptopproduct :" + laptopproduct);

		String chkSi= (req.getParameter("chkSi")!=null)?(req.getParameter("chkSi")):"";
		LogLevel.DEBUG(5, new Throwable(), "chkSi :" + chkSi);
		String strRes="";
		try
		{	
			ServletOutputStream out=res.getOutputStream();

			String strSqlQrychk = "SELECT battery_model FROM laptop_application_chart_mapping WHERE  battery_type='"+batterytype+"' and laptop_name='"+make+"' and laptop_model='"+model+"' and laptop_product='"+laptopproduct+"'";				
			LogLevel.DEBUG(5,new Throwable(),"strSqlQrychk :"+strSqlQrychk);

			Hashtable htchk = qm.getRow(strSqlQrychk);
			String bat_model=(String)htchk.get("bat_model");
			LogLevel.DEBUG(5,new Throwable(),"bat_model :"+bat_model);

			if(htchk.isEmpty())
			{  
				String strSqlQry = "delete from laptop_details where laptop_id='"+chkSi+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );

				int i=qm.executeUpdate(strSqlQry);

				if(i <0)
				{
					LogLevel.DEBUG(1,new Throwable(),"No Name exists u can continue ");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#00dd00' class='vrb10'></font> ");
					out.println("Failed to delete Laptop details ");
				}
				else
				{
					LogLevel.DEBUG(1,new Throwable(),"");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'></font> ");
					//System.out.println(email);
					out.println("Successfully deleted Laptop details");
				}
			}
			else
			{
				LogLevel.DEBUG(1,new Throwable(),"");
				out.println("<font color='#FF0000'>Please delete same details first in Laptop Application Chat Mapping and try Here.  Click <a href=\"../jsp/admin/batterystore/laptopbatterywale/deletelaptopapplicationchart.jsp\">Here</a> to go Laptop Application Chat Mapping!</font>");
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