/***********************************************************************		
	NGIT Confidential. 
	@File Name   : AdminBatteryLoginServlet.java
	@Description : This Servlet is used to allow the operator to Login
	@Date        : 30th August 2013
******************************************************************/ 
package com.ngit.servlets.admin.batterystore; 
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.Iterator;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.io.File;
import java.util.Date;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Vector;
import java.io.IOException;
import com.oreilly.servlet.MultipartRequest;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/*
 * @author Sai Krishna Daddala.
 */
/* This class is to Admin Login. This class initializes the necessary connection pools and perform the transactions on the pools. */
public class ModifyRetailer extends HttpServlet 
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
		if(strWhatToDo.equalsIgnoreCase("getretailerdetailstomodify"))
		  {
			String state = (req.getParameter("state")!=null)?(req.getParameter("state")):"";
			LogLevel.DEBUG(5,new Throwable(),"state :"+state );

			String city= (req.getParameter("city")!=null)?(req.getParameter("city")):"";
			LogLevel.DEBUG(5,new Throwable(),"city :"+city );

			String retailer= (req.getParameter("retailer")!=null)?(req.getParameter("retailer")):"";
			LogLevel.DEBUG(5,new Throwable(),"retailer :"+retailer );

			String[] strretailer = retailer.split(",");
			String retid = strretailer[0];
			String retname = strretailer[1];

			String strstate=state.trim().replaceAll(" ", "_"); 
			LogLevel.DEBUG(5, new Throwable(), "strstate :" + strstate);

			ServletOutputStream out=res.getOutputStream();
			try
			  {  
				String strSqlQry4 = "select retailer_id,retailer_loginname,password,email_id,email_id1,phone_number,phone_numberother,mobile_number,mobile_numberother,tollfree_number,address1,address2,area,zipcode,invoice_flag from "+strstate+"_retailers where retailer_id="+retid+"";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry4 :"+strSqlQry4 );

				Vector RetailerVector=qm.executeQuery(strSqlQry4);
				LogLevel.DEBUG(5,new Throwable(),"RetailerVector:"+RetailerVector);
				String strRes="<table  align='center' width='100%' border='0'>";
							
				if(RetailerVector==null || RetailerVector.size() == 0)
				  {
					out.println("<p align='center' class='insidecontent'>No Retailer are Avaliable based on selection!<a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
					return;
				  }
				 else
				  {
				   for(int j=0; j<RetailerVector.size();j++)
					 {
						Hashtable ht3=(Hashtable)RetailerVector.get(j);
						String retailerid=String.valueOf(ht3.get("retailer_id"));
						String retailerloginname=String.valueOf(ht3.get("retailer_loginname"));
						String password=String.valueOf(ht3.get("password"));
						String emailid=String.valueOf(ht3.get("email_id"));
						String emailidother=String.valueOf(ht3.get("email_id1"));
						String phonenumber=String.valueOf(ht3.get("phone_number"));
						String phonenumberother=String.valueOf(ht3.get("phone_numberother"));
						String mobilenumber=String.valueOf(ht3.get("mobile_number"));
						String mobilenumberother=String.valueOf(ht3.get("mobile_numberother"));
						String tollfreenumber=String.valueOf(ht3.get("tollfree_number"));
						String address1=String.valueOf(ht3.get("address1"));
						String weburl=String.valueOf(ht3.get("address2"));
						String area=String.valueOf(ht3.get("area"));
						String zipcode=String.valueOf(ht3.get("zipcode"));
						String invoiceflag=String.valueOf(ht3.get("invoice_flag"));
						
						strRes=strRes+"<tr><td align='right' class='subheading' width='5%'>Retailer&nbsp;Loginname<font color = '#FF0000'>*</font>&nbsp;:&nbsp;</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='retailerloginname' value='"+retailerloginname+"' size='30' maxlength='50'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='subheading' width='5%'>Password<font color = '#FF0000'>*</font>&nbsp;:&nbsp;</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='password' name='password' value='"+password+"' size='30' maxlength='50'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='subheading' width='5%'>Email&nbsp;Id<font color = '#FF0000'>*</font>&nbsp;:&nbsp;</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='emailid' value='"+emailid+"' size='30' maxlength='100'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='subheading' width='5%'>Email&nbsp;Id&nbsp;Other&nbsp;:&nbsp;</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='emailidother' value='"+emailidother+"' size='30' maxlength='100'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='subheading' width='5%'>Land&nbsp;Line&nbsp;:&nbsp;</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='landline' value='"+phonenumber+"' size='30' maxlength='20'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='subheading' width='5%'>Land&nbsp;Line&nbsp;Other&nbsp;:&nbsp;</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='landlineother' value='"+phonenumberother+"' size='30' maxlength='50'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='subheading' width='5%'>Mobile&nbsp;Number&nbsp;:&nbsp;</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='mobilenumber' value='"+mobilenumber+"' size='30' maxlength='20'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='subheading' width='5%'>Mobile&nbsp;Number&nbsp;Other&nbsp;:&nbsp;</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='mobilenumberother' value='"+mobilenumberother+"' size='30' maxlength='50'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='subheading' width='5%'>toll&nbsp;Free&nbsp;Number&nbsp;:&nbsp;</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='tollfreenumber' value='"+tollfreenumber+"' size='30' maxlength='20'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='subheading' width='5%'>Address<font color = '#FF0000'>*</font>&nbsp;:&nbsp;</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='address1' value='"+address1+"' size='30'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='subheading' width='5%'>Web&nbsp;Address&nbsp;:&nbsp;</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='webaddress' value='"+weburl+"' size='30'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='subheading' width='5%'>Area<font color = '#FF0000'>*</font>&nbsp;:&nbsp;</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='area' value='"+area+"' size='30' maxlength='50'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='subheading' width='5%'>Zipcode&nbsp;:&nbsp;</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='zipcode' value='"+zipcode+"' size='30' maxlength='10'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='subheading' width='5%'>Invoice&nbsp;Flag&nbsp;:&nbsp;</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='invoiceflag' value='"+invoiceflag+"' size='30' maxlength='10'></td></tr>";
						strRes=strRes+"<tr><td align='right' width='42%'></td><td align='left'><input type='button'  onclick=\"funToUpdateretailers('"+retailerid+"');\" value='Update' class='smallbutton'>&nbsp;<input type='button'  onclick=\"funToDeleteretailers('"+retailerid+"');\" value='Delete' class='smallbutton'></td>";
						//strRes=strRes+"<td align='left' ><input type='button' onclick=\"Schhide('"+id+"');\" value='Cancel' class='smallbutton'></td></tr>";
					   }
					}
					strRes=strRes+"</table>";							
					out.println(strRes);
					return;				
				}
			   catch(IOException ioe)
			    {
					LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
					ioe.printStackTrace();
					session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/battery/editbatteryprice.jsp");
			    }
			   catch(Exception e)
			    {
					LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/battery/editbatteryprice.jsp");
			    }
		  }
		  /*This methos is used to insert vehicle details into vehicle_details*/
		else if(strWhatToDo.equalsIgnoreCase("modifyretailers"))
		{

			String state = (req.getParameter("state")!=null)?(req.getParameter("state")):"";
			LogLevel.DEBUG(5,new Throwable(),"state :"+state );

			String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
			LogLevel.DEBUG(5,new Throwable(),"city :"+city );

			String retailerloginname = (req.getParameter("retailerloginname")!=null)?(req.getParameter("retailerloginname")):"";
			LogLevel.DEBUG(5,new Throwable(),"retailerloginname :"+retailerloginname );

			String password = (req.getParameter("password")!=null)?(req.getParameter("password")):"";
			LogLevel.DEBUG(5,new Throwable(),"password :"+password );

			String emailid = (req.getParameter("emailid")!=null)?(req.getParameter("emailid")):"";
			LogLevel.DEBUG(5,new Throwable(),"emailid :"+emailid );

			String emailidother = (req.getParameter("emailidother")!=null)?(req.getParameter("emailidother")):"";
			LogLevel.DEBUG(5,new Throwable(),"emailidother :"+emailidother );

			String landline = (req.getParameter("landline")!=null)?(req.getParameter("landline")):"";
			LogLevel.DEBUG(5,new Throwable(),"landline :"+landline );

			String landlineother = (req.getParameter("landlineother")!=null)?(req.getParameter("landlineother")):"";
			LogLevel.DEBUG(5,new Throwable(),"landlineother :"+landlineother );

			String mobilenumber = (req.getParameter("mobilenumber")!=null)?(req.getParameter("mobilenumber")):"";
			LogLevel.DEBUG(5,new Throwable(),"mobilenumber :"+mobilenumber );
			
			String mobilenumberother = (req.getParameter("mobilenumberother")!=null)?(req.getParameter("mobilenumberother")):"";
			LogLevel.DEBUG(5,new Throwable(),"mobilenumberother :"+mobilenumberother );

			String tollfreenumber = (req.getParameter("tollfreenumber")!=null)?(req.getParameter("tollfreenumber")):"";
			LogLevel.DEBUG(5,new Throwable(),"tollfreenumber :"+tollfreenumber );

			String address1 = (req.getParameter("address1")!=null)?(req.getParameter("address1")):"";
			LogLevel.DEBUG(5,new Throwable(),"address1 :"+address1 );

			String webaddress = (req.getParameter("webaddress")!=null)?(req.getParameter("webaddress")):"";
			LogLevel.DEBUG(5,new Throwable(),"webaddress :"+webaddress );

			String area = (req.getParameter("area")!=null)?(req.getParameter("area")):"";
			LogLevel.DEBUG(5,new Throwable(),"area :"+area );

			String zipcode = (req.getParameter("zipcode")!=null)?(req.getParameter("zipcode")):"";
			LogLevel.DEBUG(5,new Throwable(),"zipcode :"+zipcode );

			String invoiceflag = (req.getParameter("invoiceflag")!=null)?(req.getParameter("invoiceflag")):"";
			LogLevel.DEBUG(5,new Throwable(),"invoiceflag :"+invoiceflag ); 

			String retid = (req.getParameter("retid")!=null)?(req.getParameter("retid")):"";
			LogLevel.DEBUG(5,new Throwable(),"retid :"+retid );

			String strstate=state.trim().replaceAll(" ", "_"); 
			LogLevel.DEBUG(5, new Throwable(), "strstate :" + strstate);
			ServletOutputStream out=res.getOutputStream();
			int reslt=0;
			int reslt1=0;
			try
			{
				/*Query to get the Admin User name and Password*/
 				String strSqlQry = "update "+strstate+"_retailers set retailer_loginname='"+retailerloginname+"',password='"+password+"',email_id='"+emailid+"',email_id1='"+emailidother+"',phone_number='"+landline+"',phone_numberother='"+landlineother+"',mobile_number='"+mobilenumber+"',mobile_numberother='"+mobilenumberother+"',tollfree_number='"+tollfreenumber+"',address1='"+address1+"',address2='"+webaddress+"',area='"+area+"',zipcode='"+zipcode+"',invoice_flag='"+invoiceflag+"' where retailer_id='"+retid+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry : "+strSqlQry);
				
				reslt = qm.executeUpdate(strSqlQry);

				String strSqlQryret = "update retailers set retailer_loginname='"+retailerloginname+"',password='"+password+"' where retailer_id='"+retid+"'";
				LogLevel.DEBUG(5,new Throwable()," strSqlQryret: "+strSqlQryret);
				
				reslt1 = qm.executeUpdate(strSqlQryret);
		
		
				if(reslt <0)
					{
						out.println("<p align='center' class='insidecontent'>Failed to update Retailers details!<a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
						return;
					}
				   else
				    {
						out.println("<p align='center' class='insidecontent'>Successfully updated Retailers details!<a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
						return;
				    }
			     }
			    catch(IOException ioe)
			     {
						LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
						ioe.printStackTrace();
						session.setAttribute("sesleditbatterypriceErrorMsg",	"Generall Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/battery/editbatteryprice.jsp");
			     }
			    catch(Exception e)
			     {
						LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
						e.printStackTrace();
						session.setAttribute("sesleditbatterypriceErrorMsg",	"General Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/battery/editbatteryprice.jsp");
			     }
		}
		else if(strWhatToDo.equalsIgnoreCase("deleteretailers"))
		{

			String state = (req.getParameter("state")!=null)?(req.getParameter("state")):"";
			LogLevel.DEBUG(5,new Throwable(),"state :"+state );

			String retid = (req.getParameter("retid")!=null)?(req.getParameter("retid")):"";
			LogLevel.DEBUG(5,new Throwable(),"retid :"+retid );

			String strstate=state.trim().replaceAll(" ", "_"); 
			LogLevel.DEBUG(5, new Throwable(), "strstate :" + strstate);
			ServletOutputStream out=res.getOutputStream();
			int reslt=0;
			int reslt1=0;
			try
			{
				/*Query to get the Admin User name and Password*/
 				String strSqlQry = "delete from "+strstate+"_retailers where retailer_id='"+retid+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry : "+strSqlQry);
				
				reslt = qm.executeUpdate(strSqlQry);

				String strSqlQryret = "delete from retailers where retailer_id='"+retid+"'";
				LogLevel.DEBUG(5,new Throwable()," strSqlQryret: "+strSqlQryret);
				
				reslt1 = qm.executeUpdate(strSqlQryret);
			
				if(reslt <0)
					{
						out.println("<p align='center' class='insidecontent'>Failed to delete Retailers details!<a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
						return;
					}
				   else
				    {
						out.println("<p align='center' class='insidecontent'>Successfully deleted Retailers details!<a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
						return;
				    }
			     }
			    catch(IOException ioe)
			     {
						LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
						ioe.printStackTrace();
						session.setAttribute("sesleditbatterypriceErrorMsg",	"Generall Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/battery/editbatteryprice.jsp");
			     }
			    catch(Exception e)
			     {
						LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
						e.printStackTrace();
						session.setAttribute("sesleditbatterypriceErrorMsg",	"General Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/battery/editbatteryprice.jsp");
			     }
		}
	}
}