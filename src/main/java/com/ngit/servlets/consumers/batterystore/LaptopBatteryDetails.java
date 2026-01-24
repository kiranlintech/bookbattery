/***********************************************************************		
	NGIT Confidential. 
	@File Name   : LaptopBatteryDetails.java 
	@Description : This Servlet is used to fetch the drop down values 
	@Author	     : Sai Krishna Daddala
	@Date        : 6th Feb 2014
******************************************************************/ 
 
package com.ngit.servlets.consumers.batterystore; 
 
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

  
public class LaptopBatteryDetails extends HttpServlet 
{  
	private ServletContext context; 
	QueryManager qm;

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
		String strUserName=(String)session.getAttribute("sesAdminName"); 
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
		String OperatorTeamCount = (propsMOPConfig.getProperty("OperatorTeamCount")!=null)?(propsMOPConfig.getProperty("OperatorTeamCount")):"";
		LogLevel.DEBUG(5, new Throwable(), "OperatorTeamCount :" + OperatorTeamCount);
		/* ****************************************************************************************\
		* This action is used to get laptop name.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("getlaptopname"))
		{ 
			try
			{
				String strRes=getlaptopname(req,res,session);
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
	* This action is used to get laptop model.
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
	* This action is used to get battery barnd.
	\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("getbattery_brand"))
		{ 
			try
			{
				String strRes=getbattery_brand(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("getlaptopbatterydetails"))
		{ 
			try
			{
				String strRes=getlaptopbatterydetails(req,res,session,strIpAddress,strPort,strsuppmobnumber,strsuppmobnumber1,SMSFromAddress,baseurldirectory,OperatorTeamCount);
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
	* This action is used to get send mobile verification code details.
	\* *****************************************************************************************/		
	if(strWhatToDo.equalsIgnoreCase("sentverificationcode"))
		{ 
			try
			{
				String strRes=sentverificationcode(req,res,session,domainname,strIpAddress,strPort,SMSFromAddress,FromEmailId);
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
	* This action is used to  insert consumer details.
	\* *****************************************************************************************/		
	if(strWhatToDo.equalsIgnoreCase("insertconsumerorderdetails"))
		{ 
			try
			{
				String strRes=insertconsumerorderdetails(req,res,session);
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
	* This method is to get laptop name.
	* @strSqlQry : carries the query to battery details in laptop_battery_details table.
	\* **************************************************************************************************************************************/
	public String getlaptopname(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String batterytype= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
			String veh_id_fetched="";
			String laptop_name="";	

	 		ServletOutputStream out=res.getOutputStream();

			String strlaptopname = "select distinct(laptop_name) from laptop_details where battery_type='"+batterytype+"' order by laptop_name asc"; 
			LogLevel.DEBUG(5, new Throwable(), "strlaptopname :" + strlaptopname);
			
			Vector laptopvec=qm.executeQuery(strlaptopname);
			LogLevel.DEBUG(5,new Throwable(),"laptopvec:"+laptopvec);
			if(laptopvec.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>Select&nbsp;Laptop&nbsp;Make</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<laptopvec.size(); i++)
					{
						if(i==0)
						{
						out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&nbsp;Select&nbsp;Laptop&nbsp;Make</option>");
						}
						Hashtable ht=(Hashtable)laptopvec.get(i);
						
						laptop_name =String.valueOf(ht.get("laptop_name"));
						LogLevel.DEBUG(5,new Throwable(),"laptop_name:"+laptop_name);
						out.print("<option style='background:#FFF' value='"+laptop_name+"'>"+laptop_name+"</option>"); 
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
	* This method is to get laptop model.
	* @strSqlQry : carries the query to laptop model details in laptop_details table.
	\* **************************************************************************************************************************************/
	public String getlaptop_model(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		try
		{	
			String laptopname= (req.getParameter("laptopname")!=null)?(req.getParameter("laptopname")):"";
			LogLevel.DEBUG(5,new Throwable(),"laptopname:"+laptopname);
			String batterytype= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
			LogLevel.DEBUG(5,new Throwable(),"batterytype:"+batterytype);

			String veh_name_fetched="";
			String laptop_model="";	

	 		ServletOutputStream out=res.getOutputStream();

			String strlaptopid = "select distinct(laptop_model) from laptop_details where laptop_name='"+laptopname+"' and battery_type='"+batterytype+"' order by laptop_model asc"; 
			LogLevel.DEBUG(5, new Throwable(), "strlaptopid :" + strlaptopid);
			
			Vector laptopidvect=qm.executeQuery(strlaptopid);
			LogLevel.DEBUG(5,new Throwable(),"laptopidvect:"+laptopidvect);
			if(laptopidvect.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>Select&nbsp;Laptop&nbsp;Model</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<laptopidvect.size(); i++)
					{
						if(i==0)
						{
						out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&nbsp;Select&nbsp;Laptop&nbsp;Model</option>");
						}
						Hashtable ht=(Hashtable)laptopidvect.get(i);
						//veh_name_fetched =String.valueOf(ht.get("vehicle_name"));
						laptop_model =String.valueOf(ht.get("laptop_model"));
						out.print("<option style='background:#FFF' value='"+laptop_model+"'>"+laptop_model+"</option>"); 
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
	* This method is to get laptop model.
	* @strSqlQry : carries the query to laptop model details in laptop_details table.
	\* **************************************************************************************************************************************/
	public String getlaptop_product(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		try
		{	
			String laptopname= (req.getParameter("laptopname")!=null)?(req.getParameter("laptopname")):"";
			LogLevel.DEBUG(5,new Throwable(),"laptopname:"+laptopname);
			String laptopmodel= (req.getParameter("laptopmodel")!=null)?(req.getParameter("laptopmodel")):"";
			LogLevel.DEBUG(5,new Throwable(),"laptopmodel:"+laptopmodel);
			String batterytype= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
			LogLevel.DEBUG(5,new Throwable(),"batterytype:"+batterytype);

			String veh_name_fetched="";
			String laptop_product="";	

	 		ServletOutputStream out=res.getOutputStream();

			//String strlaptopid = "select laptop_id,laptop_product from laptop_details where laptop_name='"+laptopname+"' and laptop_model='"+laptopmodel+"' and battery_type='"+batterytype+"' order by laptop_product asc"; 
			//LogLevel.DEBUG(5, new Throwable(), "strlaptopid :" + strlaptopid);

			String strlaptopid = "select distinct(a.laptop_id),a.laptop_product from laptop_details a, laptop_application_chart_mapping b where a.laptop_name='"+laptopname+"' and a.laptop_model='"+laptopmodel+"' and a.battery_type='"+batterytype+"' and b.display_status='active' and a.laptop_product=b.laptop_product order by a.laptop_product asc";
			LogLevel.DEBUG(5, new Throwable(), "strlaptopid :" + strlaptopid);

			Vector laptopidvect=qm.executeQuery(strlaptopid);
			LogLevel.DEBUG(5,new Throwable(),"laptopidvect:"+laptopidvect);
			if(laptopidvect.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>Select&nbsp;Laptop&nbsp;Product</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<laptopidvect.size(); i++)
					{
						if(i==0)
						{
						out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&nbsp;Select&nbsp;Laptop&nbsp;Product</option>");
						}
						Hashtable ht=(Hashtable)laptopidvect.get(i);
						//veh_name_fetched =String.valueOf(ht.get("vehicle_name"));
						laptop_product =String.valueOf(ht.get("laptop_product"));
						out.print("<option style='background:#FFF' value='"+laptop_product+"'>"+laptop_product+"</option>"); 
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
	public String getbattery_brand(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			
			String battery_brand="";	

	 		ServletOutputStream out=res.getOutputStream();

			
			String strSqlQry ="select distinct(battery_brand) from laptop_battery_details order by battery_brand asc";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector BatteryVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"BatteryVector:"+BatteryVector );

			if(BatteryVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>Select&nbsp;Battery&nbsp;Brand</option>");
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
						battery_brand =String.valueOf(ht1.get("battery_brand"));
						out.print("<option style='background:#FFF' value='"+battery_brand+"'>"+battery_brand+"</option>"); 
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
	* This method is to fetch laptop battery details details.
	* @strSqlQry : carries the query to select laptop battery details from latop_battery_details and laptop_application_chart_mapping table.
	* @strSqlQry1 : Query has multiple pages to select laptop battery details from laptopbattery_details and laptop_application_chart_mapping table.
	\* **************************************************************************************************************************************/
	public String getlaptopbatterydetails(HttpServletRequest req,HttpServletResponse res,HttpSession session,String strIpAddress,String strPort,String strsuppmobnumber,String strsuppmobnumber1,String SMSFromAddress,String baseurldirectory,String OperatorTeamCount)
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

		String strUsermobileno= (req.getParameter("Usermobileno")!=null)?(req.getParameter("Usermobileno")):"";
		LogLevel.DEBUG(5,new Throwable(),"strUsermobileno:"+strUsermobileno);

		String mobileversion= (req.getParameter("mobileversion")!=null)?(req.getParameter("mobileversion")):"";
		LogLevel.DEBUG(5,new Throwable(),"mobileversion:"+mobileversion);

		String back= (req.getParameter("back")!=null)?(req.getParameter("back")):"";
		LogLevel.DEBUG(5,new Throwable(),"back:"+back);

		String url="";

		if(backlink.equals("laptopbattery"))
		{
			url=""+baseurldirectory+"laptopbattery.jsp";
		}
		else if(backlink.equals("indexpage"))
		{
			url=""+baseurldirectory+"index.jsp";
		}
		else if(backlink.equals("batterycheckpage"))
		{
			url=""+baseurldirectory+"batterycheckyourself.jsp";
		}
		else if(backlink.equals("knowaboutbatterywale"))
		{
			url=""+baseurldirectory+"knowaboutbatterywale.jsp";
		}
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
		String agent_name="";

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
			
			
			String strSqlQry ="select battery_brand,battery_model,laptop_model,battery_cellcount,battery_warranty,battery_part_no,voltage,watt_hr,battery_actual_price,icon_url,amazonlink,description from laptop_application_chart_mapping  where battery_type='"+strbattype+"' and laptop_name='"+strlaptopmake+"' and laptop_model='"+strlaptopmodel+"' and laptop_product='"+laptopproduct+"' and display_status='active' and battery_id in ("+batteryids+") order by battery_actual_price "+sortpricess+" limit "+Startindex+",5 ";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector LaptopBatteryDetailsVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"LaptopBatteryDetailsVector:"+LaptopBatteryDetailsVector );

			String strSqlQrycount ="select count(*) as count from laptop_application_chart_mapping  where battery_type='"+strbattype+"' and laptop_name='"+strlaptopmake+"' and laptop_model='"+strlaptopmodel+"' and laptop_product='"+laptopproduct+"' and battery_id in ("+batteryids+") order by battery_actual_price "+sortpricess+"";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQrycount :" + strSqlQrycount);

			Vector LaptopBatteryDetailsVectorCount=qm.executeQuery(strSqlQrycount);
			LogLevel.DEBUG(5,new Throwable(),"LaptopBatteryDetailsVectorCount:"+LaptopBatteryDetailsVectorCount );
			
			if(strUsermobileno.equals("") || strUsermobileno.equals("null") || strUsermobileno.equals(null) || strUsermobileno.equals("undefined") || strUsermobileno.equals("9603467559")  || strUsermobileno.equals("9999999999") || area.equals("Test Area"))
			{
				String strSqlQryvisitors = "insert into visitors_orders(vis_ord_id,bat_type,veh_make,veh_model,bat_brand,bat_capacity,state,city,area,pincode,mobile_number,option_type,keynumber,visitors_comments,creation_date)values(NULL,'"+strbattype+"','"+strlaptopmake+"','"+strlaptopmodel+"','','','"+state+"','"+city+"','"+area+"','','"+strUsermobileno+"','laptopbattery','','',now())";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQryvisitors: "+strSqlQryvisitors);
				int reslt = qm.executeUpdate(strSqlQryvisitors);
			}
			else
			{

				//Query to get the operator name which has been assigned last
				String StrSqlQryOperatorname1 = "select agent_name from visitors_orders where mobile_number='"+strUsermobileno+"' and YEAR(creation_date) = YEAR(NOW()) AND MONTH(creation_date) = MONTH(NOW()) AND DAY(creation_date) = DAY(NOW()) order by creation_date limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQryOperatorname1 :" + StrSqlQryOperatorname1);

				Hashtable htgetoperatorname1 = qm.getRow(StrSqlQryOperatorname1);
				LogLevel.DEBUG(5, new Throwable(), "htgetoperatorname1 :" + htgetoperatorname1);

				if(htgetoperatorname1.isEmpty())
				{
					agent_name=(String)htgetoperatorname1.get("agent_name");
					LogLevel.DEBUG(5, new Throwable(), "agent_name :" + agent_name);
					if(agent_name == null || agent_name == "0" || agent_name == "" || agent_name.equals("0") || agent_name.equals(""))

					{
						String StrSqlQryOperatorname = "select agent_name from visitors_orders order by creation_date desc limit 1";
						LogLevel.DEBUG(5, new Throwable(), "StrSqlQryOperatorname :" + StrSqlQryOperatorname);

						Hashtable htgetoperatorname = qm.getRow(StrSqlQryOperatorname); 
						agent_name=(String)htgetoperatorname.get("agent_name");
						
						String assigntoagent="";
						
						int operatorteamcountint = Integer.parseInt(OperatorTeamCount);

						 for(int i=1; i<operatorteamcountint; i++)
						{
							LogLevel.DEBUG(5, new Throwable(), "i value:" +i);						

							if(agent_name.equals("operator"+i+""))
							{

								int jk = new Integer(i+ 1);

								if(jk == operatorteamcountint)
								{
									assigntoagent="operator1";
									LogLevel.DEBUG(5, new Throwable(), "assigntoagent:" +assigntoagent);
									break;
								}
								else
								{
									assigntoagent="operator"+jk;
									LogLevel.DEBUG(5, new Throwable(), "assigntoagent:" +assigntoagent);
									break;
								}

							}
							
						}
						
						if(assigntoagent.equals(""))
						{
							agent_name="operator1";

						}
						else
						{
							agent_name=assigntoagent;

						}
						String strSqlQryvisitors = "insert into visitors_orders(vis_ord_id,bat_type,veh_make,veh_model,bat_brand,bat_capacity,state,city,area,pincode,mobile_number,option_type,keynumber,visitors_comments,creation_date,agent_name)values(NULL,'"+strbattype+"','"+strlaptopmake+"','"+strlaptopmodel+"','','','"+state+"','"+city+"','"+area+"','','"+strUsermobileno+"','laptopbattery','','',now(),'"+agent_name+"')";
						LogLevel.DEBUG(5,new Throwable(),"strSqlQryvisitors: "+strSqlQryvisitors);
						int reslt = qm.executeUpdate(strSqlQryvisitors);
						
						SendSMS sendsms = new SendSMS(qm);
						String message = "User selected  "+strbattype+" >> "+strlaptopmake+" >> "+strlaptopmodel+" >> "+city+" for order Battery. User Mob No: "+strUsermobileno+"";
					}
					else
					{
						agent_name=(String)htgetoperatorname1.get("agent_name");
						LogLevel.DEBUG(5, new Throwable(), "agent_name :" + agent_name);
						String strSqlQryvisitors = "insert into visitors_orders(vis_ord_id,bat_type,veh_make,veh_model,bat_brand,bat_capacity,state,city,area,pincode,mobile_number,option_type,keynumber,visitors_comments,creation_date,agent_name)values(NULL,'"+strbattype+"','"+strlaptopmake+"','"+strlaptopmodel+"','','','"+state+"','"+city+"','"+area+"','','"+strUsermobileno+"','laptopbattery','','',now(),'"+agent_name+"')";
						LogLevel.DEBUG(5,new Throwable(),"strSqlQryvisitors: "+strSqlQryvisitors);
						int reslt = qm.executeUpdate(strSqlQryvisitors);
						
						SendSMS sendsms = new SendSMS(qm);
						String message = "User selected  "+strbattype+" >> "+strlaptopmake+" >> "+strlaptopmodel+" >> "+city+" for order Battery. User Mob No: "+strUsermobileno+"";
					}
				}
				else
				{
						agent_name=(String)htgetoperatorname1.get("agent_name");
						LogLevel.DEBUG(5, new Throwable(), "agent_name :" + agent_name);
						String strSqlQryvisitors = "insert into visitors_orders(vis_ord_id,bat_type,veh_make,veh_model,bat_brand,bat_capacity,state,city,area,pincode,mobile_number,option_type,keynumber,visitors_comments,creation_date,agent_name)values(NULL,'"+strbattype+"','"+strlaptopmake+"','"+strlaptopmodel+"','','','"+state+"','"+city+"','"+area+"','','"+strUsermobileno+"','laptopbattery','','',now(),'"+agent_name+"')";
						LogLevel.DEBUG(5,new Throwable(),"strSqlQryvisitors: "+strSqlQryvisitors);
						int reslt = qm.executeUpdate(strSqlQryvisitors);
						
						SendSMS sendsms = new SendSMS(qm);
						String message = "User selected  "+strbattype+" >> "+strlaptopmake+" >> "+strlaptopmodel+" >> "+city+" for order Battery. User Mob No: "+strUsermobileno+"";
				}
			}
			if(LaptopBatteryDetailsVector.isEmpty())
			{ 
				if(mobileversion.equals("mobile"))
			    {
				LogLevel.DEBUG(1,new Throwable(),"Failed to fetch Laptop Battery details ");
				session.setAttribute("priority","1");
				session.setAttribute("sesbatterydetailsErrorMsg", "<font color='#ff3333' class='vrb10'>No Laptop Battery details found based on selection.! </font> ");
				session.removeAttribute("LaptopBatteryDetailsVector");
				res.sendRedirect(""+baseurldirectory+"mobile/moblaptopbattery.jsp");
				return strRes;
				}
				else
				{
				LogLevel.DEBUG(1,new Throwable(),"Failed to fetch Laptop Battery details ");
				session.setAttribute("priority","1");
				session.setAttribute("sesbatterydetailsErrorMsg", "<font color='#ff3333' class='vrb10'>No Laptop Battery details found based on selection.! </font> ");
				session.removeAttribute("LaptopBatteryDetailsVector");
				res.sendRedirect(url);
				return strRes;
				}
			}
			else
			{
				if(mobileversion.equals("mobile"))
			    {
				LogLevel.DEBUG(1, new Throwable(),"Successfully Fetched Laptop Battery Details");
				session.setAttribute("RefreshKeyword", "refresh");
				session.setAttribute("LaptopBatteryDetailsVector", LaptopBatteryDetailsVector);
				session.setAttribute("LaptopBatteryDetailsVectorCount", LaptopBatteryDetailsVectorCount);
				//session.setAttribute("BatteryLocalTaxVector", strcustax);
				res.sendRedirect(""+baseurldirectory+"mobile/moblaptopbatterydetails.jsp?batterytype="+strbattype+"&laptopmake="+strlaptopmake+"&laptopmodel="+strlaptopmodelencode+"&laptopproduct="+strlaptopproductmodelencode+"&state="+state+"&city="+city+"&area="+area+"&sortpricess="+sortpricess+"&backlink="+backlink+"&pagenumber="+pagenumber+"&back="+back+"&Dummypagenumber="+Dummypagenumber+"&chatmobilenumber="+strUsermobileno);
				return strRes;
				}
				else
				{
				LogLevel.DEBUG(1, new Throwable(),"Successfully Fetched Laptop Battery Details");
				session.setAttribute("RefreshKeyword", "refresh");
				session.setAttribute("LaptopBatteryDetailsVector", LaptopBatteryDetailsVector);
				session.setAttribute("LaptopBatteryDetailsVectorCount", LaptopBatteryDetailsVectorCount);
				//session.setAttribute("BatteryLocalTaxVector", strcustax);
				res.sendRedirect(""+baseurldirectory+"laptopbatterydetails.jsp?batterytype="+strbattype+"&laptopmake="+strlaptopmake+"&laptopmodel="+strlaptopmodelencode+"&laptopproduct="+strlaptopproductmodelencode+"&state="+state+"&city="+city+"&area="+area+"&sortpricess="+sortpricess+"&backlink="+backlink+"&pagenumber="+pagenumber+"&Dummypagenumber="+Dummypagenumber+"&chatmobilenumber="+strUsermobileno);
				return strRes;
				}
			}
		}
		catch (Exception e)
		{
			LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
			e.printStackTrace();

		}
		return strRes;
	}
	/* *************************************************************************************************************\
* This method is used to send  verification to user email id and mobile number when user enters mobile number in order now battery 
\* *************************************************************************************************************/
public String sentverificationcode(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort,String SMSFromAddress, String FromEmailId) 
{ 	
	String strUsermobileno = (req.getParameter("strUsermobileno")!=null)?(req.getParameter("strUsermobileno")):"0";
	String batterymodel = (req.getParameter("batterymodel")!=null)?(req.getParameter("batterymodel")):"0";
	String price = (req.getParameter("price")!=null)?(req.getParameter("price")):"0";
	String batterybrand = (req.getParameter("batterybrand")!=null)?(req.getParameter("batterybrand")):"0";
	String amazonlink = (req.getParameter("amazonlink")!=null)?(req.getParameter("amazonlink")):"0";
	String mobileversion = (req.getParameter("mobileversion")!=null)?(req.getParameter("mobileversion")):"0";

	//LogLevel.DEBUG(5, new Throwable(), "price :" + price);

	String strRes="";
	try
	{
		/*following code is for generating the random number */
		Random generator = new Random();   
		generator.setSeed(System.currentTimeMillis());   
		int num = generator.nextInt(99999) + 99999;   
		if (num < 100000 || num > 999999)
		{   
		num = generator.nextInt(99999) + 99999;   
		if (num < 100000 || num > 999999)
		{   
		}   
		}   
		String verificationcode = "";
		verificationcode = Integer.toString(num);
		/*code to generate the random number ends here */
		CompareTrans ct = new CompareTrans(qm);
		SendSMS sendsms = new SendSMS(qm);
		String ThankUMsg="Your Verification Code is "+verificationcode+"";
		String strSMSResponse=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUMsg, strUsermobileno);		
		if(mobileversion.equals("mobile"))
		{
		//strRes=strRes+"<table width='100%' bgcolor='#FFFFFF' valign='top'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobdiv(greyout(false));\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b></td></tr><tr><td><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF'><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font size='2' color='#FF8C00'>Please Enter Verification code received on <br> SMS</font></td></tr></table></td><tr height='10'><td colspan='3' align='center' class='subheading' id='codeerrormsg'></td></tr><tr><td height='10'></td></tr></tr><tr><td width='100%' align='center'><input type='hidden' name='passcode' value='"+verificationcode+"'><input class='insidecontent' type='tel' autocomplete='off' name='verifycode' id='verifycode' placeholder='123456' style='width:195px;height:30px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='6' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:checkverification('"+strUsermobileno+"','"+verificationcode+"','"+batterybrand+"','"+batterymodel+"','"+price+"','"+amazonlink+"');return false;} else return true;\"/></td></tr><tr><td align='center' class='popupmobilehints'><font color='#FF3333'>*</font>If you did not get verification code <br> please call <font color='#FF8C00'>+91 9603467559.</font></td></tr><tr><td height='0'></td></tr><tr><td  width='50%' align='center' ><input type='button' class='batterywalesubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:checkverification('"+strUsermobileno+"','"+verificationcode+"','"+batterybrand+"','"+batterymodel+"','"+price+"','"+amazonlink+"');\");\"></td></tr><tr><td height='15'></td></tr></table></table>";
		
		strRes=strRes+"<table width='100%' valign='top'><tr height='2'></tr><tr><table width='100%' valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: center;'> BookBattery</td><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: left;' onclick=\"javascript:closemobdiv(greyout(false));\"> X</td></tr></table><hr style='background: white; width: 95%;margin-top: 1px;margin-bottom: 1px;'></tr><table width='100%'  cellspacing='0' cellpadding='0' ><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontentmob' align='center'><font size='4' color='#FFFFFF' style='padding: 2px;'>Please Enter Verification code received on SMS</font></td></tr></table></td><tr height='10'><td colspan='3' align='center' class='subheading' id='codeerrormsg'></td></tr><tr><td height='10'></td></tr></tr><tr><td width='100%' align='center'><input type='hidden' name='passcode' value='"+verificationcode+"'><input class='insidecontent' type='tel' autocomplete='off' name='verifycode' id='verifycode' placeholder='123456' style='width:250px;height:40px;border: 2px solid #CCC;font-size: 13px;' maxlength='6' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:checkverification('"+strUsermobileno+"','"+verificationcode+"','"+batterybrand+"','"+batterymodel+"','"+price+"','"+amazonlink+"');return false;} else return true;\"/> </td></tr><tr><td align='center' class='popupmobilehints'><font color='#FF3333'>*</font>If you did not get verification code <br> please call <font color='#FF8C00'>+91 9603467559.</font></td></tr><tr><td height='0'></td></tr><tr><td  width='50%' align='center' ><input style='border-width: 0px 0px 0px 0px; font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn' class='batterywalesubmit' name='Submitrret' value='Submit' disable='false'onclick=\"javascript:checkverification('"+strUsermobileno+"','"+verificationcode+"','"+batterybrand+"','"+batterymodel+"','"+price+"','"+amazonlink+"');\");\"> <input style='border-width: 0px 0px 0px 0px; font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  value='Close' align='left' onclick=\"javascript:closemobdiv(greyout(false));\"> </td></tr><tr><td height='15'></td></tr></table></table>";
		
		}
		else
		{
		//strRes=strRes+"<table cellspacing='10' cellpadding='0' bgcolor='#FFFFFF'><table width='350' bgcolor='#FFFFFF' valign='top'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobdiv(greyout(false));\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b></td></tr></table><tr><td><table width='350'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF'><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font size='2' color='#FF8C00'>Please Enter Verification code received on <br> SMS</font></td></tr></table></td><tr><td height='10'></td></tr></tr><tr><td width='100%' align='center'><input type='hidden' name='passcode' value='"+verificationcode+"'><input class='insidecontent' type='tel' autocomplete='off' name='verifycode' id='verifycode' placeholder='123456' style='width:195px;height:30px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='6' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:checkverification('"+strUsermobileno+"','"+verificationcode+"','"+batterybrand+"','"+batterymodel+"','"+price+"','"+amazonlink+"');return false;} else return true;\"/></td></tr><tr><td align='center' style='font-family:Verdana;font-size:10px;color:#000000;font-weight:bold;text-decoration:none;padding:1px 1px;'><font color='#FF3333'>*</font>If you did not get verification code <br> please call <font color='#FF8C00'>+91 9603467559.</font></td></tr><tr><td height='0'></td></tr><tr><td  width='50%' align='center' ><input type='button' class='batterywalesubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:checkverification('"+strUsermobileno+"','"+verificationcode+"','"+batterybrand+"','"+batterymodel+"','"+price+"','"+amazonlink+"');\");\"></td></tr><tr height='26'><td colspan='3' align='center' class='subheading' id='codeerrormsg'></td></tr><tr><td height='15'></td></tr></table>";
		
		
		strRes=strRes+"<div class='col-md-4 col-md-offset-4'> <table cellspacing='10' cellpadding='0' bgcolor='#FFFFFF'><tr height='10'><table width='100%' bgcolor='#FFFFFF' valign='top'></table><table  width='100%' bgcolor='#FFFFFF' valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 25px;font-weight: 600;text-align: center;'> BookBattery</td> <td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: left;'  href=\"javascript:closemobdiv(greyout(false));\" > <a style='color: #F96F2B;' href=\"javascript:closemobdiv(greyout(false));\"> X </a> </td></tr></table><hr style='background: white; width: 90%;margin-top: 1px;margin-bottom: 1px;'></tr><tr><td><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font style='font-size: 15px;color: #ffffff;'>Please Enter Verification code received on SMS</font></td></tr></table></td></tr><tr><td height='10'></td></tr> <table cellspacing='10' cellpadding='0'  width='100%' height='10'> <tr><td width='100%' align='center'><input type='hidden' name='passcode' value='"+verificationcode+"'><input class='insidecontent' type='tel' autocomplete='off' name='verifycode' id='verifycode' placeholder='123456' style='width:70%;height:38px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 0px 0px 0px 0px;' maxlength='6' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:checkverification('"+strUsermobileno+"','"+verificationcode+"','"+batterybrand+"','"+batterymodel+"','"+price+"','"+amazonlink+"');return false;} else return true;\"/> </td></tr> </table> <table cellspacing='10' cellpadding='0'  width='100%' height='10' > <tr><td align='center' style='font-family:Verdana;font-size:12px;color:#FFFFFF; font-weight:bold;text-decoration:none;padding:1px 1px;'> <font color='#FF3333'>*</font>If you did not get verification code <br> please call <font color='#FF8C00'>+91 9603467559.</font></td></tr> </table> <tr><td height='10'></td></tr>  <table cellspacing='10' cellpadding='0'  width='100%' height='10' >  <tr><td  width='50%' align='right' ><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:checkverification('"+strUsermobileno+"','"+verificationcode+"','"+batterybrand+"','"+batterymodel+"','"+price+"','"+amazonlink+"');\");\"> </td> <td><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  value='Close' align='left' onclick=\"javascript:closemobdiv(greyout(false));\" ></td></tr> </table> <table cellspacing='10' cellpadding='0'  width='100%' height='10' >  <tr height='26'><td colspan='3' align='center' class='subheading' id='codeerrormsg'></td></tr> </table ><tr><td height='15'></td></tr></table></div>";
		
		
		
		}
	}
	catch (Exception e)
	{
		LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
		strRes=strRes;
	}
	return strRes;
}
/* *************************************************************************************************************\
* This method is used to insert consumer and order details details
\* *************************************************************************************************************/
public String insertconsumerorderdetails(HttpServletRequest req , HttpServletResponse res,HttpSession session) 
{ 	
	String strUsermobileno = (req.getParameter("mobilenumber")!=null)?(req.getParameter("mobilenumber")):"";
	LogLevel.DEBUG(5, new Throwable(), "strUsermobileno :" + strUsermobileno);
	String state= (req.getParameter("state")!=null)?(req.getParameter("state")):"";
	String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
	String area = (req.getParameter("area")!=null)?(req.getParameter("area")):"";
	String batterymodel = (req.getParameter("batterymodel")!=null)?(req.getParameter("batterymodel")):"";
	String price = (req.getParameter("price")!=null)?(req.getParameter("price")):"";
	String verifycode = (req.getParameter("verifycode")!=null)?(req.getParameter("verifycode")):"";
	LogLevel.DEBUG(5, new Throwable(), "verifycode :" + verifycode);
	String batterybrand = (req.getParameter("batterybrand")!=null)?(req.getParameter("batterybrand")):"";
	String batterytype = (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
	String laptopmake = (req.getParameter("laptopmake")!=null)?(req.getParameter("laptopmake")):"";
	String laptopmodel = (req.getParameter("laptopmodel")!=null)?(req.getParameter("laptopmodel")):"";
	String laptopproduct = (req.getParameter("laptopproduct")!=null)?(req.getParameter("laptopproduct")):"";
	LogLevel.DEBUG(5, new Throwable(), "laptopproduct :" + laptopproduct);

	String strRes="";

	String StrSqlQry ="";
	String Strretid="";
	try
	{		
			ServletOutputStream out=res.getOutputStream();

			String verificationcode = "";
			

			String strSqlQry = "insert into laptop_battery_order_details(lap_bat_ord_id,consumer_mobnumber,consumer_verif_code,state,city,battery_brand,battery_model,price,area,battery_type,laptop_make,laptop_model,laptop_product,creation_date)values(NULL,'"+strUsermobileno+"','"+verifycode+"','"+state+"','"+city+"','"+batterybrand+"','"+batterymodel+"','"+price+"','"+area+"','"+batterytype+"','"+laptopmake+"','"+laptopmodel+"','"+laptopproduct+"',now())";
			LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
			int reslt = qm.executeUpdate(strSqlQry);
			if(reslt<0)
			{
				out.println("Failed to insert order details");
			}
			else
			{
				out.println("Successfully inserted order details");
			}
	
	}
	catch (Exception e)
	{
	LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
	strRes=strRes;
	}
	return strRes;
}
}// end of class
