/* ********************************************************************\ 
	NGIT Pvt.Ltd. Confidential. 
	@File Name   : ConsumerLoyalty.java  
	@Description : To evolute bookbattery Merchant loyalty program
		
\* *******************************************************************/ 


 /* ******************************************************************\
* Package name
* Importing the required packages and predefined classes from them
\* ********************************************************************/
package com.ngit.servlets.consumers.consumerloyalty;
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.mail.*;
import com.ngit.javabean.sendsms.SendSMS;

import java.util.Hashtable;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Vector;
import java.lang.*;
import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletOutputStream;
import java.io.File;

/**
 *
 * @author Manjunath G
 */
/* ******************************************************************\
* This class is to evolute bookbattery Merchant loyalty program
* This class initializes the necessary connection pools and perform the transactions on the pools. 
\* ********************************************************************/
public class ConsumerLoyalty extends HttpServlet
{
	
//declaring a private context
private ServletContext context;
//declaring a variable of typr Query Manager
QueryManager qm;
String baseURL;
/* ******************************************************************\
	* This init method initializes the necessary connection pools and perform the transactions on the pools from respectvie files. 
	\* ********************************************************************/

public void init(ServletConfig config)throws ServletException
{
	super.init(config);
	//calling method getServletConfig().
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

	}catch(IOException ioe)
	{
		ioe.printStackTrace();

	}catch(Exception e)
	{
		e.printStackTrace();
	}
 }
/* ******************************************************************\
	* This doGet service method calls doPost service method to handle all the requests and responses to manage brand loyalty program. 
	\* ********************************************************************/
public void doGet (HttpServletRequest req , HttpServletResponse res )throws IOException , ServletException
	{
	//calling doPost method with parameters as req and res
		doPost(req, res);
	}
/* ******************************************************************\
* This doPost service method handles all the requests and responses passing from doGet service method to define bookbattery Merchant loyalty program. 
\* ********************************************************************/
public void doPost( HttpServletRequest req , HttpServletResponse res )throws IOException , ServletException
{ 
	res.setContentType("text/html;charset=UTF-8");
        HttpSession session=req.getSession(true);
 		session=req.getSession(true);
		String struserName=(String)session.getAttribute("sesBatteryUserLogin"); 
		LogLevel.DEBUG(5,new Throwable(),"struserName :"+struserName );
		String strvendorName= "Amaron";
		Properties propsMOPConfig = (Properties)context.getAttribute("contextPropMOPConfig"); 	
		String domainname =  propsMOPConfig.getProperty("domainname");
		String fromemailid =  propsMOPConfig.getProperty("SupportEmailId");
 		 String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?(propsMOPConfig.getProperty("baseurldirectory")):"";

 		//String baseURL = (propsMOPConfig.getProperty("baseURL")!=null)?(propsMOPConfig.getProperty("baseURL")):"";

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
	//getting action parameter into string strWhatToDo
	String strWhatToDo = (req.getParameter("hidWhatToDo")!=null)?req.getParameter("hidWhatToDo"):"";	
	ServletOutputStream out=res.getOutputStream();
	String Loyalitypoints_count =  propsMOPConfig.getProperty("Loyalitypoints_count");
	String Recharge_loyalty_points =  propsMOPConfig.getProperty("Recharge_loyalty_money");
	String Redeem_points =  propsMOPConfig.getProperty("Redeem_points");
	String strIpAddress=(propsMOPConfig.getProperty("TRANSMITTER_IP_ADDR")!=null)?(propsMOPConfig.getProperty("TRANSMITTER_IP_ADDR")):"";
	String strPort=(propsMOPConfig.getProperty("TRANSMITTER_IP_PORT")!=null)?(propsMOPConfig.getProperty("TRANSMITTER_IP_PORT")):"9910";
	String SMSFromAddress=(propsMOPConfig.getProperty("SMSFromAddress")!=null)?(propsMOPConfig.getProperty("SMSFromAddress")):"";
	String FromEmailId=(propsMOPConfig.getProperty("SupportEmailId")!=null)?(propsMOPConfig.getProperty("SupportEmailId")):"";
/* ******************************************************************\
* This action is to fetch the brand loyalty programs
\* ********************************************************************/
		
if(strWhatToDo.equalsIgnoreCase("fetchBrandLoyalty"))
{ 	
	try
	{
		//calling fetchBrandLoyalty() method and storing the result in strRes String
		String strRes=fetchBrandLoyalty(req ,res,session,struserName);
		LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);

			
	}catch (Exception e)
	{										
		LogLevel.ERROR(1, e, "Error :" + e);

	}	
		
}

/* ******************************************************************\
* This action is to fetch the retailer loyalty programs
\* ********************************************************************/
if(strWhatToDo.equalsIgnoreCase("fetchRetailerLoyalty"))
{ 	
	try
	{
		//calling fetchRetailerLoyalty() method and storing the result in strRes String
		String strRes=fetchRetailerLoyalty(req ,res,session);
		LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);

			
	}catch (Exception e)
	{										
		LogLevel.ERROR(1, e, "Error :" + e);

	}	
		
}

/* **************************************************************************************\
* This action is to fetch the details of the merchant loyalty earned by the bookbattery user
\* **************************************************************************************/

if(strWhatToDo.equalsIgnoreCase("merchantLoyaltydetails"))
{ 	
	try
	{
		String strRes=merchantLoyaltydetails(req ,res,session);
		LogLevel.DEBUG(5,new Throwable(),"strRes"+strRes);
		out.println(strRes);
		out.close();
	}
	catch (Exception e)
	{										
		LogLevel.ERROR(1, e, "Error :" + e);
		out.println("-1");
		out.close();
	}	
	
}

/* ******************************************************************\
* This action is to fetch the points earned by the bookbattery user
\* ********************************************************************/

if(strWhatToDo.equalsIgnoreCase("merchantLoyalty"))
{ 	
	try
	{
		//calling merchantLoyalty() method and storing the result in strRes String
		String strRes=merchantLoyalty(req ,res,session);
		LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);

			
	}catch (Exception e)
	{										
		LogLevel.ERROR(1, e, "Error :" + e);

	}
	
}

if(strWhatToDo.equalsIgnoreCase("dispmerchantLoyalty"))
{ 	
	try
	{
		//calling dispmerchantLoyalty() method and storing the result in strRes String
		String strRes=dispmerchantLoyalty(req ,res,session);
		LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
		out.println(strRes);
		out.close();
			
	}catch (Exception e)
	{										
		LogLevel.ERROR(1, e, "Error :" + e);

	}
	
}
else if(strWhatToDo.equalsIgnoreCase("updateBrandloyaltypoints"))
{ 	
	try
	{
		String strRes=updateBrandloyaltypoints(req ,res,struserName,Loyalitypoints_count,Recharge_loyalty_points,Redeem_points,domainname,strIpAddress,	
	strPort,session,SMSFromAddress,FromEmailId);
		LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
		out.println(strRes);
		out.close();
		//return strRes;
	}catch (Exception e)
	{										
		LogLevel.ERROR(1, e, "Error :" + e);
		out.println("-1");
		out.close();
	}	
}
}//end of doPost

/* *************************************************************************************************************************************\
* This action is to fetch the brand loyalty programs
* @fetBrandProg  : carries the query to fetch loyalty_name, points, value, partner_name  from loyalty_program table based on vendor_name.
\* *************************************************************************************************************************************/
	
public String fetchBrandLoyalty(HttpServletRequest req , HttpServletResponse res, HttpSession session,String struserName)
{
	//if brandid parameter is not null then its value is passed in to the String, if its null then null is store in String	
	String strbrandid = (req.getParameter("brandid")!=null)?(req.getParameter("brandid")):"0";
	LogLevel.DEBUG(5,new Throwable(),"strbrandid :"+strbrandid );
	//if brandname parameter is not null then its value is passed in to the String, if its null then null is store in String	
	String strbrandname = (req.getParameter("brandname")!=null)?(req.getParameter("brandname")):"0";
	LogLevel.DEBUG(5,new Throwable(),"strbrandname :"+strbrandname );

	
	try
	{
		ServletOutputStream out=res.getOutputStream();
		String strSqlQry=" select merchant_category_name  from merchant_category where type like '%Deal%'";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQry to get the loyalty category :"+strSqlQry );

			ArrayList htcategory=new ArrayList();
			htcategory=qm.getField(strSqlQry);
			String category="";
			for(int i=0;i<htcategory.size();i++)
			{
				if(category.equals(""))
				category=htcategory.get(i).toString();
				else
				category=category+"','"+htcategory.get(i).toString();
			}
			LogLevel.DEBUG(5,new Throwable(),"loyalty category :"+category );

			Hashtable htvenodorrid = new Hashtable();
			htvenodorrid= qm.getRow(" select vendor_id from vendor_master where merchant_category in ('"+category+"') and vendor_id="+strbrandid+" ") ;
			LogLevel.DEBUG(5, new Throwable(), "htvenodorrid:" + htvenodorrid);
			String venodorid=String.valueOf(htvenodorrid.get("vendor_id"));
			if(venodorid.equals("") || venodorid.equalsIgnoreCase("null") || venodorid.equals("0"))
			{
				LogLevel.DEBUG(1,new Throwable(),"There are No loyaltyprogram for this Brand ");
			session.setAttribute("priority","1");
			session.removeAttribute("sesAllbrandVector");
			session.setAttribute("sesErrorMsg", "<font color='#483D8B' class='subtitle'>There are No loyaltyprogram for this Brand!</font> ");
			res.sendRedirect("../jsp/consumers/consumerloyaltyprogram/brandloyaltypage.jsp?brandname="+strbrandname);
	
				
			}
		else
		{
		//Query to fetch loyalty_name, points, value, partner_name  from loyalty_program table based on vendor_name.
		String fetBrandProg = "select partners_list, loyalty_name,loyalty_points,value,icon_name,description,vendor_name from loyalty_program where vendor_id="+strbrandid;
		LogLevel.DEBUG(5,new Throwable(),"fetBrandProg :"+fetBrandProg );

		//getting the result into a brandLoyalVector	
		Vector brandLoyalVector=qm.executeQuery(fetBrandProg);
		LogLevel.DEBUG(5,new Throwable(),"brandLoyalVector :"+brandLoyalVector );
		//checking if brandLoyalVector vector is null or not
		if( brandLoyalVector==null || brandLoyalVector.size() == 0)
		{
			//if null then sending an error message using sessions to the jsp page
			LogLevel.DEBUG(1,new Throwable(),"There are No loyaltyprogram for this Brand ");
			session.setAttribute("priority","1");
			session.removeAttribute("sesAllbrandVector");
			session.setAttribute("sesErrorMsg", "<font color='#483D8B' class='subtitle'>There are No loyaltyprogram for this Brand!</font> ");
			res.sendRedirect("../jsp/consumers/consumerloyaltyprogram/brandloyaltypage.jsp?brandname="+strbrandname);
			
		}
		else
		{
			//if not null then sending the result using session to jsp page
			LogLevel.DEBUG(1,new Throwable(),"Successfully fetched categories ");
			if(session.getAttribute("sesAllbrandVector")!=null)
			session.removeAttribute("sesAllbrandVector");
			session.setAttribute("sesAllbrandVector",brandLoyalVector );
			res.sendRedirect("../jsp/consumers/consumerloyaltyprogram/brandloyaltypage.jsp?brandname="+strbrandname);
				
		}
	}
	}
	catch(IOException ioe)
	{
		//If any exceptionsa re found in try block, this block will catch those exception
		LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
		//printstacktrace will print the line where exception is being arised
		ioe.printStackTrace();
		session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
				
	}catch(Exception e)
	{
		//If any exceptionsa re found in try block, this block will catch those exception
		LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
		//printstacktrace will print the line where exception is being arised
		e.printStackTrace();
		session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
				
	}
	//returning the result to the place where this method is called.
	return null;
}

/* ***********************************************************************************************************************************************\
* This action is to fetch the retailer loyalty programs
* @fetRetailProg  : carries the query to fetch loyalty_name, loyalty_points, value, partner_name from loyalty_program table based on retailer_name.
\* ***********************************************************************************************************************************************/
	
public String fetchRetailerLoyalty(HttpServletRequest req , HttpServletResponse res, HttpSession session)
{
	
	//if retailerid parameter is not null then its value is passed in to the String, if its null then null is store in String
	String strretailerid= (req.getParameter("retailerid")!=null)?(req.getParameter("retailerid")):"0";
	LogLevel.DEBUG(5,new Throwable(),"strretailerid :"+strretailerid );
	//if retailername parameter is not null then its value is passed in to the String, if its null then null is store in String		
	String strretailername= (req.getParameter("retailername")!=null)?(req.getParameter("retailername")):"0";
	LogLevel.DEBUG(5,new Throwable(),"strretailername :"+strretailername );

			
    try
	{
		ServletOutputStream out=res.getOutputStream();
		//query to fetch loyalty_name, loyalty_points, value, partner_name, retailer_name from loyalty_program table based on retailer_name.	
		String fetRetailProg = "select partners_list,loyalty_id,loyalty_name,loyalty_points,value,icon_name,description,retailer_name from loyalty_program where retailer_id="+strretailerid;
		LogLevel.DEBUG(5,new Throwable(),"fetRetailProg :"+fetRetailProg );

		//Getting the result in to hashtable	
		Vector RetailerVector=qm.executeQuery(fetRetailProg);
		LogLevel.DEBUG(5,new Throwable(),"RetailerVector :"+RetailerVector );

		//if null then sending an error message using sessions to the jsp page
		if( RetailerVector==null || RetailerVector.size() == 0)
		{
			//if null then sending the result using session to jsp page
			LogLevel.DEBUG(1,new Throwable(),"There are No loyaltyprogram for this retailer");
			session.setAttribute("priority","1");
			session.removeAttribute("sesRetVector");
			session.setAttribute("sesErrorMsg", "<font color='#483D8B' class='subtitle'>There are No loyaltyprogram for this retailer!</font> ");
			res.sendRedirect("../jsp/consumers/consumerloyaltyprogram/retailerloyaltypage.jsp?retailername="+strretailername);
			
		}
		else
		{
			//if not null then sending the result using session to jsp page
			LogLevel.DEBUG(1,new Throwable(),"Successfully fetched categories ");
			if(session.getAttribute("sesRetVector")!=null)
			session.removeAttribute("sesRetVector");
			session.setAttribute("sesRetVector",RetailerVector );
			res.sendRedirect("../jsp/consumers/consumerloyaltyprogram/retailerloyaltypage.jsp?retailername="+strretailername);
				
		}
	}catch(IOException ioe)
	{
		//If any exceptionsa re found in try block, this block will catch those exception
		LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
		//printstacktrace will print the line where exception is being arised
		ioe.printStackTrace();
		session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
				
	}catch(Exception e)
	{
		//If any exceptionsa re found in try block, this block will catch those exception
		LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
		//printstacktrace will print the line where exception is being arised
		e.printStackTrace();
		session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
				
	}
	//returning the result to the place where this method is called.
	return null;
}

/* ************************************************************************************************************************\
* This action is to fetch the loyalty programs from where user earned points
* @fetUsrPoints  : carries the query to fetch loyalty_name, points, value from user_loyalty_program table based on email_id.
\* ************************************************************************************************************************/
	
public String merchantLoyalty(HttpServletRequest req , HttpServletResponse res, HttpSession session)
{
			
	//if emailid parameter is not null then its value is passed in to the String, if its null then null is store in String	
	String stremailid= (req.getParameter("emailid")!=null)?(req.getParameter("emailid")):"0";
	LogLevel.DEBUG(5,new Throwable(),"stremailid:"+stremailid );
	Vector nameVector=new Vector();
	String pointsearned;
					
    try
	{
		ServletOutputStream out=res.getOutputStream();
		String strSqlfetchmerchantpoints = "select sum(a.points_earned) as count from  user_loyalty_program a,loyalty_program b where a.loyalty_id=b.loyalty_id and a.email_id='"+stremailid+"' and a.merchant_flag='yes'";
		LogLevel.DEBUG(5,new Throwable(),"strSqlfetchmerchantpoints:"+strSqlfetchmerchantpoints);
		Hashtable htmerchantpoints=qm.getRow(strSqlfetchmerchantpoints);	
		if( htmerchantpoints.isEmpty())
		{
			//if null then sending the result using session to jsp page
			pointsearned="0";
			LogLevel.DEBUG(1,new Throwable(),"There are No loyaltyprogram for this user");
			if(session.getAttribute("pointsearned")!=null)
			session.removeAttribute("pointsearned");
			session.setAttribute("pointsearned",pointsearned );	
			LogLevel.DEBUG(5,new Throwable(),"pointsearned :"+pointsearned );

			session.setAttribute("sesErrorMsg", "<font color='#483D8B' class='subtitle'>There are No loyaltyprogram for this retailer!</font> ");
			res.sendRedirect("../jsp/consumers/consumerloyaltyprogram/merchantloyaltypoints.jsp");
			
		} 
		else
		{
			String merchantpointsearned=String.valueOf(htmerchantpoints.get("count"));
			if (merchantpointsearned =="null")
			{
				merchantpointsearned = "0";
			}
			else
			{
				merchantpointsearned=merchantpointsearned;
			}
			Float strloyalitypoints1= Float.parseFloat(merchantpointsearned);
			int valuemerchant= (int)Math.round(strloyalitypoints1 * 10) / 10;
			pointsearned = Integer.toString(valuemerchant);
			LogLevel.DEBUG(5,new Throwable(),"pointsearned :"+pointsearned );
			
			if(session.getAttribute("pointsearned")!=null)
			session.removeAttribute("pointsearned");
			LogLevel.DEBUG(5,new Throwable(),"pointsearned :"+pointsearned );
			session.setAttribute("pointsearned",pointsearned );	
			LogLevel.DEBUG(5,new Throwable(),"pointsearned :"+pointsearned );
			res.sendRedirect("../jsp/consumers/consumerloyaltyprogram/merchantloyaltypoints.jsp");
		}
		
	}catch(IOException ioe)
	{
		//If any exceptionsa re found in try block, this block will catch those exception
		LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
		//printstacktrace will print the line where exception is being arised
		ioe.printStackTrace();
		session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
				
	}catch(Exception e)
	{
		//If any exceptionsa re found in try block, this block will catch those exception
		LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
		//printstacktrace will print the line where exception is being arised
		e.printStackTrace();
		session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
				
	}
	//returning the result to the place where this method is called.
	return null;
}

public String dispmerchantLoyalty(HttpServletRequest req , HttpServletResponse res, HttpSession session)
{
	//if emailid parameter is not null then its value is passed in to the String, if its null then null is store in String	
	String stremailid= (req.getParameter("emailid")!=null)?(req.getParameter("emailid")):"0";
	LogLevel.DEBUG(5,new Throwable(),"stremailid:"+stremailid );
	Vector nameVector=new Vector();	
	String strRes="";
					
    try
	{
		ServletOutputStream out=res.getOutputStream();
		if (stremailid==null)
		{
			strRes=strRes+"Session Timed Out";
			return strRes;
		}
		else
		{
			//query to fetch loyalty_id, points_earned, value_earned from user_loyalty_program table based on email_id.
			String fetUsrPoints = "select a.points_earned,a.value_earned,b.loyalty_name,b.partners_list,b.loyalty_id from user_loyalty_program a,loyalty_program b where a.loyalty_id=b.loyalty_id and a.email_id='"+stremailid+"' and a.merchant_flag='yes'";
			LogLevel.DEBUG(5,new Throwable(),"fetUsrPoints :"+fetUsrPoints );

			String strSqlfetchmerchantpoints = "select sum(a.points_earned) as count from  user_loyalty_program a,loyalty_program b where a.loyalty_id=b.loyalty_id and a.email_id='"+stremailid+"' and a.merchant_flag='yes'";
			
			Hashtable htmerchantpoints=qm.getRow(strSqlfetchmerchantpoints);	
			String merchantpointsearned=String.valueOf(htmerchantpoints.get("count"));
			Float strloyalitypoints1= Float.parseFloat(merchantpointsearned);
			int valuemerchant= (int)Math.round(strloyalitypoints1 * 10) / 10;
			String pointsearned = Integer.toString(valuemerchant);
			
			Vector userVector=qm.executeQuery(fetUsrPoints);
			//if null then sending an error message using sessions to the jsp page
			if( userVector==null || userVector.size() == 0)
			{
				//if null then sending the result using session to jsp page
				LogLevel.DEBUG(1,new Throwable(),"There are No loyaltyprogram for this user");
				if(session.getAttribute("pointsearned")!=null)
				session.removeAttribute("pointsearned");
				session.setAttribute("pointsearned",pointsearned );	
				LogLevel.DEBUG(5,new Throwable(),"pointsearned :"+pointsearned );
				session.setAttribute("sesErrorMsg", "<font color='#483D8B' class='subtitle'>There are No loyaltyprogram for this retailer!</font> ");
				//res.sendRedirect("../jsp/consumers/consumerloyaltyprogram/merchantloyaltypoints.jsp");
				
			} 
			else
			{
				strRes=strRes+"<tr><td><table align='center' width='360' border='0' cellspacing='0' cellpadding='0'><input type='checkbox' name='programname'  value='' style='display:none'/><input type='hidden' name='programvalue'  value=''>";
				for ( int i=0; i<userVector.size() ; i++)
				{
						Hashtable ht=(Hashtable)userVector.get(i);
						String strloyaltyname=String.valueOf(ht.get("loyalty_name"));
						String strloyalitypoints=String.valueOf(ht.get("points_earned"));
						String strloyalitypointsvalue=String.valueOf(ht.get("value_earned"));
						String strloyaltyid=String.valueOf(ht.get("loyalty_id"));
						strRes=strRes+"<tr><td width='25' align='center' valign='middle'><input type='checkbox' name='programname' id='programname' value='"+strloyaltyid+"' size='3'></td><td  class='top1' align='left'> <a class='click' href=\"javascript:showmerchantloyaltydetails('"+strloyaltyname+"','"+stremailid+"');\">"+strloyaltyname+"-"+strloyalitypoints+"<input type='hidden' name='programvalue' id='programvalue' value='"+strloyalitypoints+"'></td></tr>";
				}
				strRes=strRes+"</table></td></tr>";
				return strRes;	
			}
		}
		
	}catch(IOException ioe)
	{
		//If any exceptionsa re found in try block, this block will catch those exception
		LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
		//printstacktrace will print the line where exception is being arised
		ioe.printStackTrace();
		session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
				
	}catch(Exception e)
	{
		//If any exceptionsa re found in try block, this block will catch those exception
		LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
		//printstacktrace will print the line where exception is being arised
		e.printStackTrace();
		session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
				
	}
	//returning the result to the place where this method is called.
	return strRes;
}
/* ************************************************************************************************************************\
* This action is to fetch the merchant loyalty details from where user earned points
* @fetUsrPoints  : carries the query to fetch loyalty_name, points, value from user_loyalty_program table based on email_id.
\* ************************************************************************************************************************/
	
public String merchantLoyaltydetails(HttpServletRequest req , HttpServletResponse res, HttpSession session)
{
			
	//if emailid parameter is not null then its value is passed in to the String, if its null then null is store in String	
	String stremailid= (req.getParameter("emailid")!=null)?(req.getParameter("emailid")):"0";
	LogLevel.DEBUG(5,new Throwable(),"stremailid:"+stremailid );

	String strloyaltyname= (req.getParameter("loyaltyname")!=null)?(req.getParameter("loyaltyname")):"0";
	LogLevel.DEBUG(5,new Throwable(),"strloyaltyname:"+strloyaltyname );
	String loyaltydetails="";
					
    try
	{
		ServletOutputStream out=res.getOutputStream();
        //query to fetch loyalty_id, points_earned, value_earned from user_loyalty_program table based on email_id.
		String fetchloyatydetails = "select a.points_earned,a.value_earned,b.loyalty_name,b.partners_list from user_loyalty_program a,loyalty_program b where a.loyalty_id=b.loyalty_id and a.email_id='"+stremailid+"' and a.merchant_flag='yes' and b.loyalty_name='"+strloyaltyname+"' ";
		LogLevel.DEBUG(5,new Throwable(),"fetchloyatydetails :"+fetchloyatydetails );

		//Getting the result in to hashtable		
		//Vector loyatydetailsvector=qm.executeQuery(fetchloyatydetails);
		//LogLevel.DEBUG(5,new Throwable(),"loyatydetailsvector :"+loyatydetailsvector );
		Hashtable htloyaltydetails=qm.getRow(fetchloyatydetails);	
		String loyaltyname=(String)htloyaltydetails.get("loyalty_name");
		String pointsearned=String.valueOf(htloyaltydetails.get("points_earned"));
		String partnerslist=(String)htloyaltydetails.get("partners_list");
		String valueearned=String.valueOf(htloyaltydetails.get("value_earned"));
		LogLevel.DEBUG(1,new Throwable(),"loyaltyname"+loyaltyname);
		LogLevel.DEBUG(1,new Throwable(),"pointsearned"+pointsearned);
		LogLevel.DEBUG(1,new Throwable(),"partnerslist"+partnerslist);
		LogLevel.DEBUG(1,new Throwable(),"valueearned"+valueearned);
		//if null then sending an error message using sessions to the jsp page
		if( htloyaltydetails==null || htloyaltydetails.size() == 0)
		{
			//if null then sending the result using session to jsp page
			LogLevel.DEBUG(1,new Throwable(),"There are No loyaltyprogram for this user");
			//session.setAttribute("priority","1");
			//session.setAttribute("sesErrorMsg", "<font color='#483D8B' class='subtitle'>There are No loyaltyprogram for this retailer!</font> ");
			//res.sendRedirect("../jsp/consumers/consumerloyaltyprogram/merchantloyaltypoints.jsp");
			loyaltydetails=loyaltydetails+"<table width='73%' align='center' height='75%' bgcolor='CCCCCC' cellpadding='0' cellspacing='1' id='loyaltydetails'>";
			loyaltydetails=loyaltydetails+"<tr class='table1'><td height='20' width='50%' class='details'>There are No loyaltyprogram for this user</td></tr>";
			//loyaltydetails=loyaltydetails+"<tr height='10'></tr></table>";
			LogLevel.DEBUG(1,new Throwable(),"loyaltydetails"+loyaltydetails);
		} 
		else
		{
			//if not null then sending the result using session to jsp page
			LogLevel.DEBUG(1,new Throwable(),"Successfully fetched categories ");
			//if(session.getAttribute("sesAllloyalVector")!=null)
			//session.removeAttribute("sesAllloyalVector");
			//session.setAttribute("sesAllloyalVector",userVector );	
			//LogLevel.DEBUG(5,new Throwable(),"userVector :"+userVector );

			//res.sendRedirect("../jsp/consumers/consumerloyaltyprogram/merchantloyaltypoints.jsp");
			loyaltydetails=loyaltydetails+"<table width ='50%' align='center' height='75%' bgcolor='CCCCCC' cellpadding='0' cellspacing='1' id='loyaltydetails' border='0'>";
			loyaltydetails=loyaltydetails+"<tr class='table1'><td height='20' width='50%' class='details'>Loyalty Name</td><td height='20' class='details'>&nbsp;"+loyaltyname+"</td></tr>";
			loyaltydetails=loyaltydetails+"<tr class='table2' ><td height='20' class='details' >Points </td><td height='20' class='details' >&nbsp;"+pointsearned+"</td></tr>";
			loyaltydetails=loyaltydetails+"<tr class='table1' ><td height='20' class='details' >Value(Rs) </td><td height='20' class='details' >&nbsp;"+valueearned+"</td></tr>";
			loyaltydetails=loyaltydetails+"<tr class='table2' ><td height='20' class='details' >Redeem Points At </td><td height='20' class='details' > &nbsp;"+partnerslist+"</td></tr>";
			//loyaltydetails=loyaltydetails+"<tr height='10'></tr></table>";
			LogLevel.DEBUG(1,new Throwable(),"loyaltydetails"+loyaltydetails);
				
		}
		
		
	}catch(IOException ioe)
	{
		//If any exceptionsa re found in try block, this block will catch those exception
		LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
		//printstacktrace will print the line where exception is being arised
		ioe.printStackTrace();
		session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
				
	}catch(Exception e)
	{
		//If any exceptionsa re found in try block, this block will catch those exception
		LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
		//printstacktrace will print the line where exception is being arised
		e.printStackTrace();
		session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
				
	}
	//returning the result to the place where this method is called.
	return loyaltydetails;
}
public String updateBrandloyaltypoints (HttpServletRequest req , HttpServletResponse res,String struserName,String Loyalitypoints_count,String Recharge_loyalty_points,String Redeem_points,String domainname,String strIpAddress,	
String strPort,HttpSession session,String SMSFromAddress,String FromEmailId)
{
	String strAdress = (req.getParameter("useraddress")!=null)?(req.getParameter("useraddress")):"0";
	String strAdress2 = strAdress.replace("'","\\'");
	String mobileno = (req.getParameter("mobileno")!=null)?(req.getParameter("mobileno")):"";
	String reedempoints = (req.getParameter("reedempoints")!=null)?(req.getParameter("reedempoints")):"";
	String serviceprovider = (req.getParameter("serviceprovider")!=null)?(req.getParameter("serviceprovider")):"";
	String state = (req.getParameter("state")!=null)?(req.getParameter("state")):"";
	String flag = (req.getParameter("flag")!=null)?(req.getParameter("flag")):"";
	String strpointsearnd = (req.getParameter("loyalpoints")!=null)?(req.getParameter("loyalpoints")):"";
	LogLevel.DEBUG(5,new Throwable(),"strpointsearnd :"+strpointsearnd);
	String strloyaltyid = (req.getParameter("loyaltyid")!=null)?(req.getParameter("loyaltyid")):"";
	LogLevel.DEBUG(5,new Throwable(),"strloyaltyid :"+strloyaltyid);
	int loyalty1=0;
	int loyalty3=0;
	int result=0;
	int recharge=0;
	int rechargeamount=0;
	String addRptQry,strThankUMsg,strsub;
	ArrayList al = new ArrayList(); 
	try
	{
		if (flag.equals("profile"))
		{
			String updateprofile = "update batterywale_user_profile set address='"+strAdress2+"' where email_id='"+struserName+"'";
			int updateprofileresult=qm.executeUpdate(updateprofile);
		}
		String fetchloyaltydetails="select loyalty_points,value from loyalty_program where loyalty_id='"+strloyaltyid+"'";
		Hashtable htloyaltydetails=qm.getRow(fetchloyaltydetails);
		String strpoints=String.valueOf(htloyaltydetails.get("loyalty_points"));
		String strvalue=String.valueOf(htloyaltydetails.get("value"));
		int point1=Integer.parseInt(strpoints);
		int point2=Integer.parseInt(strpointsearnd);
		int point3=Integer.parseInt(reedempoints);
		int value1=Integer.parseInt(strvalue);
		int point4=(point2-point3);
		int addvalue=(point4*value1)/point1;
		if (point1 > value1)
		{recharge=(point1/value1);}
		else
		{recharge=(value1/point1);}
		if (point1 < value1)
		{rechargeamount= point3*recharge;}
		else
		{rechargeamount= point3/recharge;}
		if(addvalue == 0)
		{
			String deleteQry="delete from user_loyalty_program where loyalty_id='"+strloyaltyid+"' and merchant_flag='yes'";
			result=qm.executeUpdate(deleteQry);
		}
		else
		{
			String updateQry = "update user_loyalty_program set points_earned="+point4+",value_earned="+addvalue+" where loyalty_id='"+strloyaltyid+"' and email_id='"+struserName+"' and merchant_flag='yes'";
			//Converting the String variable into int for calculation
			result=qm.executeUpdate(updateQry);
			LogLevel.DEBUG(5,new Throwable(),"updateQry :"+updateQry);
		}
		String strUserID=getUserID(struserName);
		Random generator = new Random();
		generator.setSeed(System.currentTimeMillis());   
		int num = generator.nextInt(99999) + 99999;   
		if (num < 100000 || num > 999999)
		{   
			num = generator.nextInt(99999) + 99999;   
			if (num < 100000 || num > 999999)
			{   
				throw new Exception("Unable to generate PIN at this time..");   
			}
		}   
		String strPromID = "";
		strPromID = "PC" + num;
		if (flag.equals("profile"))
		{
			addRptQry = "insert into consumer_reedem_detl(reedem_id,user_id,email_id,mobile_number,points_reedem,recharge_amount,location,mobile_serviceprovider,promotional_code,loyalty_ids,creation_date,created_by,rechrg_flag) values(NULL,"+strUserID+",'"+struserName+"',"+mobileno+","+reedempoints+","+rechargeamount+",'NA','NA','"+strPromID+"','"+strloyaltyid+"',now(),'ngit','Cash Back')";
		}
		else
		{
			addRptQry = "insert into consumer_reedem_detl(reedem_id,user_id,email_id,mobile_number,points_reedem,recharge_amount,location,mobile_serviceprovider,promotional_code,loyalty_ids,creation_date,created_by,rechrg_flag) values(NULL,"+strUserID+",'"+struserName+"',"+mobileno+","+reedempoints+","+rechargeamount+",'"+state+"','"+serviceprovider+"','"+strPromID+"','"+strloyaltyid+"',now(),'ngit','Mobile Recharge')";
		}
		int i = qm.executeUpdate(addRptQry);
		if(i < 0)
		{
			String strRes="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'><b>BookBattery</b></td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Failed to add redeem points.please try again.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_merchntreedemclose();' class='button4'><br></td></tr><tr height='15'></tr></table>";
			return strRes;
		}
		else
		{
			String strSqlfetchmerchantpoints = "select sum(a.points_earned) as count from  user_loyalty_program a,loyalty_program b where a.loyalty_id=b.loyalty_id and a.email_id='"+struserName+"' and a.merchant_flag='yes'";
			Hashtable htmerchantpoints=qm.getRow(strSqlfetchmerchantpoints);	
			String merchantpointsearned=String.valueOf(htmerchantpoints.get("count"));
			if (merchantpointsearned =="null")
			{
				merchantpointsearned = "0";
			}
			else
			{
				merchantpointsearned=merchantpointsearned;
			}
			Float strloyalitypoints1= Float.parseFloat(merchantpointsearned);
			int valuemerchant= (int)Math.round(strloyalitypoints1 * 10) / 10;
			String pointsearned = Integer.toString(valuemerchant);
			if(session.getAttribute("pointsearned")!=null)
			session.removeAttribute("pointsearned");
			LogLevel.DEBUG(5,new Throwable(),"pointsearned :"+pointsearned );
			session.setAttribute("pointsearned",pointsearned );	
			LogLevel.DEBUG(5,new Throwable(),"pointsearned :"+pointsearned );

			//sms code starts from here to sent sms after successfully reedemed loyalty points
			SendSMS sendsms = new SendSMS(qm);
			
			if (flag.equals("profile"))
			{
				strThankUMsg="Your promotional code is "+strPromID+" . You have redeemed "+reedempoints+" points in BookBattery. Cheque will be received within one week to your address. If you have any queries call 919603467559."; 
			}
			else
			{	
				strThankUMsg="Your promotional code is "+strPromID+" . You have redeemed "+reedempoints+" points in BookBattery. Recharge will be done with in 48 hours. If you have any queries call 919603467559."; 
			}
			
			String strSMSResponse=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,strThankUMsg,mobileno);
			//sms code ends here to sent sms after successfully reedemed loyalty points
			//email code starts from here to sent email after successfully reedemed loyalty points
			if (flag.equals("profile"))
			{
				strsub="Dear "+struserName+",\r\n"+"You have redeemed "+reedempoints+" points in BookBattery. Your promotional code is "+strPromID+". Cheque will be received within one week to your address. If you have any queries regarding cash back call 919603467559 redeem expires with in 30 days.";
			}
			else
			{	
				strsub="Dear "+struserName+",\r\n"+"You have redeemed "+reedempoints+" points in BookBattery. Your promotional code is "+strPromID+". Recharge will be done within 48 hours. If you have any queries regarding recharge call 919603467559 redeem expires with in 30 days.";
			}
			String strThanks="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
			MailTrans mtrans1=new MailTrans();
			mtrans1.setStrSmtpHost(domainname);
			mtrans1.setStrFrom(FromEmailId);
			mtrans1.setStrTo(struserName);
			mtrans1.setStrSubject("BookBattery Redeemed points details");
			String activateLink = strsub+"\r\n\r\n"+strThanks+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
			mtrans1.setStrText(activateLink);	
			Thread mt=new MailThread(mtrans1,"");
			mt.start();

			//email code ends here to sent email after successfully reedemed loyalty points
			if (flag.equals("profile"))
			{
				String strRes="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:close_merchntreedemclose(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Your promotional code is "+strPromID+" <br>You have redeemed "+reedempoints+" points.Cheque will be received within one week to your address.<br><b>Note: </b>An Email and Sms has been sent.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_merchntreedemclose();' class='button4'><br></td></tr><tr height='15'></tr></table>";
				return strRes;
			}
			else
			{
				String strRes="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:close_merchntreedemclose(greyout(false));'style='font-weight:bold;text-transform:uppercase;color:#FFFFFF;letter-spacing:1pt;word-spacing:2pt;font-size:1em;text-align:left;font-family:georgia, serif;line-height:1;text-decoration:none;'>X</a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Your promotional code is "+strPromID+" <br>You have redeemed "+reedempoints+" points.Recharge will be done within 48 hours.<br><b>Note: </b>An Email and Sms has been sent.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_merchntreedemclose();' class='button4'><br></td></tr><tr height='15'></tr></table>";
				return strRes;
			}
		}
	}
	catch (Exception e)
	{
		LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
		e.printStackTrace();
		return null;
	}
}
/* This method is used to fetch the user id for a given email from the database.
* @strUserName : carries the email id to get user id
* @returns : String (This String contains the user id )
*            Null : query failure
*			 
*/
public String getUserID(String strUserName)
{
LogLevel.DEBUG(5, new Throwable(), "Method getUserID called ");

if (strUserName == null || strUserName.equals(""))
{
	LogLevel.DEBUG(5, new Throwable(),"Parameter to getUserID is null or empty ");
	return null;
}
Hashtable ht = new Hashtable();
try
{
	ht= qm.getRow("select user_id from batterywale_user_profile where email_id='"+strUserName+"'");
	LogLevel.DEBUG(5, new Throwable(), "result:" + ht);
	if(ht==null)
	{
		return null;
	}
	
	return String.valueOf(ht.get("user_id"));
}
catch (Exception e)
{
	LogLevel.DEBUG(5, e, "Exception while fetching categories:" + e);
	e.printStackTrace();
	return null;
}
}
}//class