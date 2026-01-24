/***********************************************************************		
	NGIT Confidential. 
	@File Name   : OperatorLaptopBatteryDetails.java 
	@Description : This Servlet is used to fetch the drop down values 
	@Author	     : Sai Krishna Daddala
	@Date        : 25th Feb 2014
******************************************************************/ 
 
package com.ngit.servlets.operator; 
 
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.mail.*;
import com.ngit.javabean.sendsms.SendSMS;
import com.ngit.javabean.consumers.products.CompareTrans;

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
import java.net.URLDecoder;  
import java.net.URLEncoder;
import java.util.Random;
import java.security.SecureRandom;
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

  
public class OperatorLaptopBatteryDetails extends HttpServlet 
{  
	private ServletContext context; 
	QueryManager qm;
	String baseURL;

	private static final Random RANDOM = new SecureRandom();
		 /** Length of password. @see #generateRandomPassword() */
	public static final int PASSWORD_LENGTH = 8;
 
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
		String strUserName=(String)session.getAttribute("sesBatteryOperatorName"); 

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

		String strIpAddress =(propsMOPConfig.getProperty("TRANSMITTER_IP_ADDR")!=null)?(propsMOPConfig.getProperty("TRANSMITTER_IP_ADDR")):"";
		String strPort=(propsMOPConfig.getProperty("TRANSMITTER_IP_PORT")!=null)?(propsMOPConfig.getProperty("TRANSMITTER_IP_PORT")):"9910";
		String SMSFromAddress=(propsMOPConfig.getProperty("SMSFromAddress")!=null)?(propsMOPConfig.getProperty("SMSFromAddress")):"";
		String FromEmailId=(propsMOPConfig.getProperty("SupportEmailId")!=null)?(propsMOPConfig.getProperty("SupportEmailId")):"";
		String strsuppemaild=(propsMOPConfig.getProperty("suppemaild")!=null)?(propsMOPConfig.getProperty("suppemaild")):"";
		String domainname = (propsMOPConfig.getProperty("domainname")!=null)?(propsMOPConfig.getProperty("domainname")).trim():"bookbattery.com";
		String baseUrl =  propsMOPConfig.getProperty("publicUrl");
		String strsuppmobnumber =  propsMOPConfig.getProperty("suppmobnumber");
		String strsuppmobnumber1 =  propsMOPConfig.getProperty("suppmobnumber1");
		String strCMSSERVERIP	= (propsMOPConfig.getProperty("CMS_SERVER_IP")!=null)?(propsMOPConfig.getProperty("CMS_SERVER_IP")):"";
		String strPDFFilePath	= (propsMOPConfig.getProperty("PDFFilePath")!=null)?(propsMOPConfig.getProperty("PDFFilePath")):"";
		String strPDFRelFilePath = (propsMOPConfig.getProperty("PDFRelFilePath")!=null)?(propsMOPConfig.getProperty("PDFRelFilePath")):"";

	/* ****************************************************************************************\
	* This action is used to get battery barnd.
	\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("getlaptopbatterydetails"))
		{ 
			try
			{
				String strRes=getlaptopbatterydetails(req,res,session,strIpAddress,strPort,strsuppmobnumber,strsuppmobnumber1,SMSFromAddress);
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
	* This method is to fetch laptop battery details details.
	* @strSqlQry : carries the query to select laptop battery details from latop_battery_details and laptop_application_chart_mapping table.
	* @strSqlQry1 : Query has multiple pages to select laptop battery details from laptopbattery_details and laptop_application_chart_mapping table.
	\* **************************************************************************************************************************************/
	public String getlaptopbatterydetails(HttpServletRequest req,HttpServletResponse res,HttpSession session,String strIpAddress,String strPort,String strsuppmobnumber,String strsuppmobnumber1,String SMSFromAddress)
	{
		String strbattype= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
		LogLevel.DEBUG(5,new Throwable(),"strbattype:"+strbattype );

		String strlaptopmake= (req.getParameter("laptopname")!=null)?(req.getParameter("laptopname")):"";
		LogLevel.DEBUG(5,new Throwable(),"strlaptopmake:"+strlaptopmake );
		
		String strlaptopmodel= (req.getParameter("laptopmodel")!=null)?(req.getParameter("laptopmodel")):"";
		LogLevel.DEBUG(5,new Throwable(),"strlaptopmodel:"+strlaptopmodel );

		String laptopproduct= (req.getParameter("laptopproduct")!=null)?(req.getParameter("laptopproduct")):"";
		LogLevel.DEBUG(5,new Throwable(),"laptopproduct:"+laptopproduct );
		
		String state= (req.getParameter("state")!=null)?(req.getParameter("state")):"";
		LogLevel.DEBUG(5,new Throwable(),"state:"+state );

		String city= (req.getParameter("city")!=null)?(req.getParameter("city")):"";
		LogLevel.DEBUG(5,new Throwable(),"city:"+city );

		String area= (req.getParameter("area")!=null)?(req.getParameter("area")):"";
		LogLevel.DEBUG(5,new Throwable(),"area:"+area );
		
		String sortprice= (req.getParameter("sortprice")!=null)?(req.getParameter("sortprice")):"";
		LogLevel.DEBUG(5,new Throwable(),"sortprice:"+sortprice);

		String keyword= (req.getParameter("keyword")!=null)?(req.getParameter("keyword")):"";
		LogLevel.DEBUG(5,new Throwable(),"keyword:"+keyword);

		String backlink= (req.getParameter("backlink")!=null)?(req.getParameter("backlink")):"";
		LogLevel.DEBUG(5,new Throwable(),"backlink:"+backlink);

		String pagenumber = (req.getParameter("pagenumber")!=null)?(req.getParameter("pagenumber")):"0";
		LogLevel.DEBUG(5,new Throwable(),"pagenumber :"+pagenumber );

		String pagetype = (req.getParameter("pagetype")!=null)?(req.getParameter("pagetype")):"0";
		LogLevel.DEBUG(5,new Throwable(),"pagetype :"+pagetype );

		String lastpage = (req.getParameter("lastpage")!=null)?(req.getParameter("lastpage")):"0";
		LogLevel.DEBUG(5,new Throwable(),"lastpage :"+lastpage );

		String Dummypagenumber="0";
					
		if(lastpage.equals("0"))
		{
			lastpage="1";
		}
		if(pagetype.equals("first"))
		{
			pagenumber="1"; 
		}
		if(pagetype.equals("previous"))
		{
			int pn=Integer.parseInt(pagenumber)-1;
			pagenumber=Integer.toString(pn);
		}
		if(pagetype.equals("next"))
		{
			int pn=Integer.parseInt(pagenumber)+1;
			pagenumber=Integer.toString(pn);				
		}
		if(pagetype.equals("last"))
		{
			pagenumber=lastpage;
		}
		if(pagenumber.equals("0"))
		{
			pagenumber="1";
		}
		
		int batterywale_per_page=5;
		int Startlimt=batterywale_per_page*(Integer.parseInt(pagenumber)-1);
		String Startindex=Integer.toString(Startlimt);
		LogLevel.DEBUG(5,new Throwable(),"Startindex:"+Startindex );


		String sortpricess = "";
		if(keyword.equals("sortprices"))
		{
			sortpricess = sortprice;
		}
		else
		{
			sortpricess = "asc";
		}
		String strRes="";
		try
		{	
			
			String strSqlQrybatteryid="";
			
			//String message="";
			//String strcustax="";
			String strlaptopmodelencode = URLEncoder.encode(strlaptopmodel, "UTF-8");
			LogLevel.DEBUG(5,new Throwable(),"strlaptopmodelencode:"+strlaptopmodelencode);

			String strlaptopproductmodelencode = URLEncoder.encode(laptopproduct, "UTF-8");
			LogLevel.DEBUG(5,new Throwable(),"strlaptopproductmodelencode:"+strlaptopproductmodelencode);
		
			
			strSqlQrybatteryid ="select distinct(battery_id) from laptop_application_chart_mapping where battery_type='"+strbattype+"' and laptop_name='"+strlaptopmake+"' and laptop_model='"+strlaptopmodel+"' and laptop_product='"+laptopproduct+"'";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQrybatteryid :" + strSqlQrybatteryid);
			
			
			ArrayList laptopbatteryids=new ArrayList();
			laptopbatteryids=qm.getField(strSqlQrybatteryid);

			String batteryids="";
			for(int i=0;i<laptopbatteryids.size();i++)
			{
				if(batteryids.equals(""))
				batteryids=laptopbatteryids.get(i).toString();
				else
				batteryids=batteryids+","+laptopbatteryids.get(i).toString();
			}
			LogLevel.DEBUG(5,new Throwable(),"batteryids:"+batteryids);
			
			
			String strSqlQry ="select battery_brand,battery_model,laptop_model,battery_cellcount,battery_warranty,battery_part_no,voltage,watt_hr,battery_actual_price,icon_url,amazonlink,description from laptop_application_chart_mapping  where battery_type='"+strbattype+"' and laptop_name='"+strlaptopmake+"' and laptop_model='"+strlaptopmodel+"' and laptop_product='"+laptopproduct+"' and battery_id in ("+batteryids+") order by battery_actual_price "+sortpricess+" limit "+Startindex+",5 ";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector LaptopBatteryDetailsVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"LaptopBatteryDetailsVector:"+LaptopBatteryDetailsVector );

			String strSqlQrycount ="select count(*) as count from laptop_application_chart_mapping  where battery_type='"+strbattype+"' and laptop_name='"+strlaptopmake+"' and laptop_model='"+strlaptopmodel+"' and laptop_product='"+laptopproduct+"' and battery_id in ("+batteryids+") order by battery_actual_price "+sortpricess+"";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQrycount :" + strSqlQrycount);

			Vector LaptopBatteryDetailsVectorCount=qm.executeQuery(strSqlQrycount);
			LogLevel.DEBUG(5,new Throwable(),"LaptopBatteryDetailsVectorCount:"+LaptopBatteryDetailsVectorCount );
			
			if(LaptopBatteryDetailsVector.isEmpty())
			{ 
				LogLevel.DEBUG(1,new Throwable(),"Failed to fetch Laptop Battery details ");
				session.setAttribute("priority","1");
				session.setAttribute("sesbatterydetailsErrorMsg", "<font color='#ff3333' class='vrb10'>No Laptop Battery details found based on selection.! </font> ");
				session.removeAttribute("LaptopBatteryDetailsVector");
				res.sendRedirect("../jsp/operator/orderbattery/orderbattery.jsp");
				return strRes;
			}
			else
			{
				LogLevel.DEBUG(1, new Throwable(),"Successfully Fetched Laptop Battery Details");
				session.setAttribute("LaptopBatteryDetailsVector", LaptopBatteryDetailsVector);
				session.setAttribute("LaptopBatteryDetailsVectorCount", LaptopBatteryDetailsVectorCount);
				//session.setAttribute("BatteryLocalTaxVector", strcustax);
				res.sendRedirect("../jsp/operator/orderbattery/laptopbatterydetails.jsp?batterytype="+strbattype+"&laptopmake="+strlaptopmake+"&laptopmodel="+strlaptopmodelencode+"&laptopproduct="+strlaptopproductmodelencode+"&state="+state+"&city="+city+"&area="+area+"&sortpricess="+sortpricess+"&backlink="+backlink+"&pagenumber="+pagenumber+"&Dummypagenumber="+Dummypagenumber);
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
