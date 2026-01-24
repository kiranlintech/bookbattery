/***********************************************************************		
	NGIT Confidential. 
	@File Name   : BatteryHome.java 
	@Description : This Servlet is used to select the Battery Details.
	@Author	     : Gowtham.K
	@Date        : 30th August 2013
******************************************************************/ 
 
package com.ngit.servlets.operator; 
 
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.mail.*;
import com.ngit.javabean.sendsms.SendSMS;


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

public class CancelOrdRefNumber extends HttpServlet 
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
		String struserName=(String)session.getAttribute("sesBatteryOperatorName"); 
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

		String strIpAddress =(propsMOPConfig.getProperty("TRANSMITTER_IP_ADDR")!=null)?(propsMOPConfig.getProperty("TRANSMITTER_IP_ADDR")):"";
		String strPort=(propsMOPConfig.getProperty("TRANSMITTER_IP_PORT")!=null)?(propsMOPConfig.getProperty("TRANSMITTER_IP_PORT")):"9910";
		String SMSFromAddress=(propsMOPConfig.getProperty("SMSFromAddress")!=null)?(propsMOPConfig.getProperty("SMSFromAddress")):"";
		String FromEmailId=(propsMOPConfig.getProperty("SupportEmailId")!=null)?(propsMOPConfig.getProperty("SupportEmailId")):"";
		String strsuppemaild=(propsMOPConfig.getProperty("suppemaild")!=null)?(propsMOPConfig.getProperty("suppemaild")):"";
		String domainname = (propsMOPConfig.getProperty("domainname")!=null)?(propsMOPConfig.getProperty("domainname")).trim():"bookbattery.com";
		String baseUrl =  propsMOPConfig.getProperty("publicUrl");
		String strsuppmobnumber =  propsMOPConfig.getProperty("suppmobnumber");
		String strCMSSERVERIP	= (propsMOPConfig.getProperty("CMS_SERVER_IP")!=null)?(propsMOPConfig.getProperty("CMS_SERVER_IP")):"";
	/* ****************************************************************************************\
	* This action is used to get battery brands.
	\* *****************************************************************************************/		
		  if(strWhatToDo.equalsIgnoreCase("getordrefnumber"))
			{ 
			try
			{
				String strRes=getordrefnumber(req,res,session);
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
	* This action is used to get inverter order details.
	\* *****************************************************************************************/		
		  if(strWhatToDo.equalsIgnoreCase("getinverterordrefnumber"))
			{ 
			try
			{
				String strRes=getinverterordrefnumber(req,res,session);
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
		  if(strWhatToDo.equalsIgnoreCase("getordrefnumbernew"))
			{ 
			try
			{
				String strRes=getordrefnumbernew(req,res,session);
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
	* This action is used to get inverter order details.
	\* *****************************************************************************************/		
		  if(strWhatToDo.equalsIgnoreCase("getinverterordrefnumbernew"))
			{ 
			try
			{
				String strRes=getinverterordrefnumbernew(req,res,session);
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
		  if(strWhatToDo.equalsIgnoreCase("cancelorder"))
			{ 
			try
			{
				String strRes=cancelorder(req,res,session,domainname,strIpAddress,strPort,FromEmailId,strsuppemaild,SMSFromAddress);
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
	* This action is used to cancel inverter order
	\* *****************************************************************************************/		
		 if(strWhatToDo.equalsIgnoreCase("cancelinverterorder"))
		{ 
			try
			{
				String strRes=cancelinverterorder(req,res,session,domainname,strIpAddress,strPort,FromEmailId,strsuppemaild,SMSFromAddress);
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
	* This method is to get battery models.
	* @strSqlQry : carries the query to battery models details in battery_details table.
	\* **************************************************************************************************************************************/
	public String getordrefnumber(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
		String ordrefnumber= (req.getParameter("ordrefnumber")!=null)?(req.getParameter("ordrefnumber")):"";
		LogLevel.DEBUG(5, new Throwable(), "ordrefnumber :" + ordrefnumber);

		String strRes="";
		try
		{	
			ServletOutputStream out=res.getOutputStream();

			/* @strSqlQry3 : carries the query to Insert the user Work details into professional_detl table.*/
				String strSqlQry4 = "select ord_id,order_number,consumer_name,consumer_mobnumber,retailer_name,retailer_mobilnumber,city,battery_brand,battery_model,price,order_status from battery_order_details where order_number='"+ordrefnumber+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry4 :"+strSqlQry4 );

				Vector orddetails=qm.executeQuery(strSqlQry4);
				LogLevel.DEBUG(5,new Throwable(),"orddetails:"+orddetails);
	
			if(orddetails==null || orddetails.size() == 0)
			{
				out.println("<p align='center' valign='top' class='insidecontent'><table valign='top' width='500' cellspacing='0' border='1' align='center' bgcolor='#FFFFFF' cellpadding='0'><tr><td><table valign='top' width='500' bgcolor='#FFFFFF' border='0'><tr><td class='insidecontent' align='center'>Invalid Ord Ref Number!</td></tr></table></td></tr></table><a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
			}
			else
			{
				strRes=strRes+"<table border='0' align='center' bgcolor='#FFFFFF' width='750'><tr>";
				//strRes=strRes+"<td align='center'  valign='top'><input type='button' onClick=\"javascript:deleteappichatdetails();\" value='DELETE' class='button4'></td></tr></table>";
						
				strRes=strRes+"<table border='0' cellspacing='1' align='center' cellpadding='2' bgcolor='#FFFFFF' width='100%'><tr bgcolor='#2364b1'><td class='prodheading' width='20%'>CustName</td><td  class='prodheading' width='15%'>Brand</td><td class='prodheading' width='15%'>Model</td><td class='prodheading' width='15%'>City</td><td class='prodheading' width='15%'>Status</td><td class='prodheading' width='20%'>Update&nbsp;Status</td></tr>";
			  	for(int j=0; j<orddetails.size();j++)
				{
						Hashtable ht3=(Hashtable)orddetails.get(j);
						String ordid=String.valueOf(ht3.get("ord_id"));
						String ordnumber=String.valueOf(ht3.get("order_number"));
						String customername=String.valueOf(ht3.get("consumer_name"));
						String cusmobilenumber=String.valueOf(ht3.get("consumer_mobnumber"));
						String retailername=String.valueOf(ht3.get("retailer_name"));
						String retmobilenumber=String.valueOf(ht3.get("retailer_mobilnumber"));
						String city=String.valueOf(ht3.get("city"));
						String batterybrand=String.valueOf(ht3.get("battery_brand"));
						String batterymodel=String.valueOf(ht3.get("battery_model"));
						String price=String.valueOf(ht3.get("price"));
						String orderstatus=String.valueOf(ht3.get("order_status"));

					int Jcnt=j+1;
					
					strRes=strRes+"<table bgcolor='#FFFFFF' width='100%' cellspacing='1' border='0' align='center'  cellpadding='2'><input type='checkbox' name='chkSi' value='' style='display:none'/>";
					//strRes=strRes+"<tr><td width='6%' class='table1' align='left' >"+Jcnt+"<input type='checkbox' name='chkSi' value='"+strmapid+"'/></td>";
					strRes=strRes+"<td width='20%'   class='table1 align='left'  >"+customername+"</td>";
					strRes=strRes+"<td  width='15%'  class='table1 align='left'  >"+batterybrand+"</td>";
					strRes=strRes+"<td  width='15%'  class='table1 align='left'>"+batterymodel+"</td>";
					strRes=strRes+"<td  width='15%'  class='table1 align='left'  >"+city+"</td>";
					strRes=strRes+"<td  width='15%'  class='table1 align='left'  >"+orderstatus+"</td>";
					strRes=strRes+"<td  width='20%'  class='table1 align='left'  ><select style='width:110px;' name="+ordid+" id="+ordid+" onChange=\"javascript:Updatetocancelorder('"+ordid+"','"+orderstatus+"');\"><option value=''>Select Status</option><option value='ordered'>Ordered</option><option value='confirmed'>Confirmed</option><option value='installed'>Installed</option><option value='postponed'>Postponed</option><option value='cancelled-franchisee-offbushrs'>Cancelled-Franchisee-OffBusHrs</option><option value='cancelled-franchisee-denied'>Cancelled-Franchisee-Denied</option><option value='cancelled-franchisee-notresponded'>Cancelled-Franchisee-NotResponded</option><option value='cancelled-franchisee-modeloutofstock'>Cancelled-Franchisee-ModelOutofStock</option><option value='cancelled-customer'>Cancelled-Customer</option><option value='cancelled-regenerated'>Cancelled-Regenerated</option></select></td></tr>";
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
	public String getordrefnumbernew(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
		String ordrefnumber= (req.getParameter("ordrefnumber")!=null)?(req.getParameter("ordrefnumber")):"";
		LogLevel.DEBUG(5, new Throwable(), "ordrefnumber :" + ordrefnumber);

		String strRes="";
		try
		{	
			ServletOutputStream out=res.getOutputStream();

			/* @strSqlQry3 : carries the query to Insert the user Work details into professional_detl table.*/
				String strSqlQry4 = "select ord_id,order_number,consumer_name,consumer_emailid,consumer_mobnumber,retailer_name,retailer_mobilnumber,bat_type,veh_name,veh_model,battery_brand,battery_model,price,witholdbatprice,order_status,order_reasons,order_agent_comments,agent_name,city,area,creation_date,CAST(postpone_date AS CHAR) as postpone_date,CAST(installed_date AS CHAR) as installed_date from battery_order_details where order_number='"+ordrefnumber+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry4 :"+strSqlQry4 );

				Vector orddetails=qm.executeQuery(strSqlQry4);
				LogLevel.DEBUG(5,new Throwable(),"orddetails:"+orddetails);
	
			if(orddetails==null || orddetails.size() == 0)
			{
				out.println("<p align='center' valign='top' class='insidecontent'><table valign='top' width='500' cellspacing='0' border='1' align='center' bgcolor='#FFFFFF' cellpadding='0'><tr><td><table valign='top' width='500' bgcolor='#FFFFFF' border='0'><tr><td class='insidecontent' align='center'>Invalid Ord Ref Number!</td></tr></table></td></tr></table><a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
			}
			else
			{
				strRes=strRes+"<table border='0' align='center' bgcolor='#FFFFFF' width='100%' class='tableizer-table'><tr><td>";
				
						
				strRes=strRes+"<table border='0' cellspacing='1' align='center' cellpadding='2' bgcolor='#FFFFFF' width='100%' class='tableizer-table'><tr bgcolor='#2364b1'><td class='prodheading' >Order Number</td><td class='prodheading' >Customer Name</td><td class='prodheading' >Customer Email ID</td><td class='prodheading'>Customer Mobile Number</td><td class='prodheading'>Retailer Name</td><td class='prodheading'>Retailer Mobile Number</td><td class='prodheading'>Battery Type</td><td class='prodheading'>Vehicle Make</td><td class='prodheading'>Vehicle Model</td><td class='prodheading'>Battery Brand</td><td class='prodheading'>Battery Model</td><td class='prodheading'>Price</td><td class='prodheading'>OBRP</td><td class='prodheading'>Order Status</td><td class='prodheading'>Order Reasons</td><td class='prodheading'>Agent Comments</td><td class='prodheading'>Agent Name</td><td class='prodheading'>City</td><td class='prodheading'>Area</td><td class='prodheading'>Ordered Date</td><td class='prodheading'>Postponed Date</td><td class='prodheading'>Installed Date</td></tr>";
				strRes=strRes+"<table bgcolor='#FFFFFF' width='100%' cellspacing='1' border='0' align='center'  cellpadding='2' class='tableizer-table'><input type='checkbox' name='chkSi' value='' style='display:none'/>";
			  	for(int j=0; j<orddetails.size();j++)
				{

						Hashtable ht3=(Hashtable)orddetails.get(j);
						String ord_id=String.valueOf(ht3.get("ord_id"));
						String order_number=String.valueOf(ht3.get("order_number"));
						String consumer_name=String.valueOf(ht3.get("consumer_name"));
						String consumer_emailid=String.valueOf(ht3.get("consumer_emailid"));
						String consumer_mobnumber=String.valueOf(ht3.get("consumer_mobnumber"));
						String retailer_name=String.valueOf(ht3.get("retailer_name"));
						String retailer_mobilnumber=String.valueOf(ht3.get("retailer_mobilnumber"));
						String bat_type=String.valueOf(ht3.get("bat_type"));
						String veh_name=String.valueOf(ht3.get("veh_name"));
						String veh_model=String.valueOf(ht3.get("veh_model"));
						String battery_brand=String.valueOf(ht3.get("battery_brand"));
						String battery_model=String.valueOf(ht3.get("battery_model"));
						String price=String.valueOf(ht3.get("price"));
						String witholdbatprice=String.valueOf(ht3.get("witholdbatprice"));
						String order_status=String.valueOf(ht3.get("order_status"));
						String order_reasons=String.valueOf(ht3.get("order_reasons"));
						String order_agent_comments=String.valueOf(ht3.get("order_agent_comments"));
						String agent_name=String.valueOf(ht3.get("agent_name"));
						String city=String.valueOf(ht3.get("city"));
						String area=String.valueOf(ht3.get("area"));
						String creation_date=String.valueOf(ht3.get("creation_date"));
						String postpone_date=String.valueOf(ht3.get("postpone_date"));
						String installed_date=String.valueOf(ht3.get("installed_date"));


					int Jcnt=j+1;
					
					
					
					strRes=strRes+"<tr><td class='table1 align='left'>"+order_number+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+consumer_name+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+consumer_emailid+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+consumer_mobnumber+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+retailer_name+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+retailer_mobilnumber+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+bat_type+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+veh_name+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+veh_model+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+battery_brand+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+battery_model+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+price+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+witholdbatprice+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+order_status+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+order_reasons+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+order_agent_comments+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+agent_name+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+city+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+area+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+creation_date+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+postpone_date+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+installed_date+"</td></tr>";
					
					
				}
				strRes=strRes+"</table>";
				strRes=strRes+"</table></td></tr></table>";
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
	/**************************************************************************************************************************************\
	* This method is to get the order details
	* @strSqlQry : carries the query to fetch the order details of inverter
	\* **************************************************************************************************************************************/
	public String getinverterordrefnumber(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
		String ordrefnumber= (req.getParameter("ordrefnumber")!=null)?(req.getParameter("ordrefnumber")):"";
		LogLevel.DEBUG(5, new Throwable(), "ordrefnumber :" + ordrefnumber);

		String strRes="";
		try
		{	
			ServletOutputStream out=res.getOutputStream();

			/* @strSqlQry3 : carries the query to Insert the user Work details into professional_detl table.*/
				String strSqlQry4 = "select order_id,order_number,consumer_name,consumer_mobnumber,retailer_name,retailer_mobile_number,city,inverter_brand,inverter_model,inverter_capacity,price,order_status from inverter_order_details where order_number='"+ordrefnumber+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry4 :"+strSqlQry4 );

				Vector orddetails=qm.executeQuery(strSqlQry4);
				LogLevel.DEBUG(5,new Throwable(),"orddetails:"+orddetails);
	
			if(orddetails==null || orddetails.size() == 0)
			{
				out.println("<p align='center' valign='top' class='insidecontent'><table valign='top' width='500' cellspacing='0' border='1' align='center' bgcolor='#FFFFFF' cellpadding='0'><tr><td><table valign='top' width='500' bgcolor='#FFFFFF' border='0'><tr><td class='insidecontent' align='center'>Invalid Ord Ref Number!</td></tr></table></td></tr></table><a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
			}
			else
			{
				strRes=strRes+"<table border='0' align='center' bgcolor='#FFFFFF' width='750'><tr>";
				//strRes=strRes+"<td align='center'  valign='top'><input type='button' onClick=\"javascript:deleteappichatdetails();\" value='DELETE' class='button4'></td></tr></table>";
						
				strRes=strRes+"<table border='0' cellspacing='1' align='center' cellpadding='2' bgcolor='#FFFFFF' width='100%'><tr bgcolor='#2364b1'><td class='prodheading' width='20%'>CustName</td><td  class='prodheading' width='15%'>Brand</td><td class='prodheading' width='15%'>Model</td><td class='prodheading' width='15%'>City</td><td class='prodheading' width='15%'>Status</td><td class='prodheading' width='20%'>Update&nbsp;Status</td></tr>";
			  	for(int j=0; j<orddetails.size();j++)
				{
						Hashtable ht3=(Hashtable)orddetails.get(j);
						String ordid=String.valueOf(ht3.get("order_id"));
						String ordnumber=String.valueOf(ht3.get("order_number"));
						String customername=String.valueOf(ht3.get("consumer_name"));
						String cusmobilenumber=String.valueOf(ht3.get("consumer_mobnumber"));
						String retailername=String.valueOf(ht3.get("retailer_name"));
						String retmobilenumber=String.valueOf(ht3.get("retailer_mobile_number"));
						String city=String.valueOf(ht3.get("city"));
						String inverterbrand=String.valueOf(ht3.get("inverter_brand"));
						String invertermodel=String.valueOf(ht3.get("inverter_model"));
						String invertercapacity=String.valueOf(ht3.get("inverter_capacity"));
						String price=String.valueOf(ht3.get("price"));
						String orderstatus=String.valueOf(ht3.get("order_status"));

					int Jcnt=j+1;
					
					strRes=strRes+"<table bgcolor='#FFFFFF' width='100%' cellspacing='1' border='0' align='center'  cellpadding='2'><input type='checkbox' name='chkSi' value='' style='display:none'/>";
					//strRes=strRes+"<tr><td width='6%' class='table1' align='left' >"+Jcnt+"<input type='checkbox' name='chkSi' value='"+strmapid+"'/></td>";
					strRes=strRes+"<td width='20%'   class='table1 align='left'  >"+customername+"</td>";
					strRes=strRes+"<td  width='15%'  class='table1 align='left'  >"+inverterbrand+"</td>";
					strRes=strRes+"<td  width='15%'  class='table1 align='left'>"+invertermodel+"</td>";
					strRes=strRes+"<td  width='15%'  class='table1 align='left'  >"+city+"</td>";
					strRes=strRes+"<td  width='15%'  class='table1 align='left'  >"+orderstatus+"</td>";
					strRes=strRes+"<td  width='20%'  class='table1 align='left'  ><select style='width:110px;' name="+ordid+" id="+ordid+" onChange=\"javascript:Updatetocancelorder('"+ordid+"','"+orderstatus+"');\"><option value=''>Select Status</option><option value='ordered'>Ordered</option><option value='confirmed'>Confirmed</option><option value='installed'>Installed</option><option value='postponed'>Postponed</option><option value='cancelled-franchisee-offbushrs'>Cancelled-Franchisee-OffBusHrs</option><option value='cancelled-franchisee-denied'>Cancelled-Franchisee-Denied</option><option value='cancelled-franchisee-notresponded'>Cancelled-Franchisee-NotResponded</option><option value='cancelled-franchisee-modeloutofstock'>Cancelled-Franchisee-ModelOutofStock</option><option value='cancelled-customer'>Cancelled-Customer</option><option value='cancelled-regenerated'>Cancelled-Regenerated</option></select></td></tr>";
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
	/**************************************************************************************************************************************\
	* This method is to get the order details
	* @strSqlQry : carries the query to fetch the order details of inverter
	\* **************************************************************************************************************************************/
	public String getinverterordrefnumbernew(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
		String ordrefnumber= (req.getParameter("ordrefnumber")!=null)?(req.getParameter("ordrefnumber")):"";
		LogLevel.DEBUG(5, new Throwable(), "ordrefnumber :" + ordrefnumber);

		String strRes="";
		try
		{	
			ServletOutputStream out=res.getOutputStream();

			/* @strSqlQry3 : carries the query to Insert the user Work details into professional_detl table.*/
				String strSqlQry4 = "select order_id,order_number,consumer_name,consumer_emailid,consumer_mobnumber,retailer_name,retailer_mobile_number,inverter_brand,inverter_model,inverter_capacity,price,order_status,order_reasons,order_agent_comments,agent_name,city,area,creation_date,CAST(postpone_date AS CHAR) as postpone_date,CAST(installed_date AS CHAR) as installed_date from inverter_order_details where order_number='"+ordrefnumber+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry4 :"+strSqlQry4 );

				Vector orddetails=qm.executeQuery(strSqlQry4);
				LogLevel.DEBUG(5,new Throwable(),"orddetails:"+orddetails);
	
			if(orddetails==null || orddetails.size() == 0)
			{
				out.println("<p align='center' valign='top' class='insidecontent'><table valign='top' width='500' cellspacing='0' border='1' align='center' bgcolor='#FFFFFF' cellpadding='0'><tr><td><table valign='top' width='500' bgcolor='#FFFFFF' border='0'><tr><td class='insidecontent' align='center'>Invalid Ord Ref Number!</td></tr></table></td></tr></table><a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
			}
			else
			{
				strRes=strRes+"<table border='0' align='center' bgcolor='#FFFFFF' width='100%' class='tableizer-table'><tr><td>";
				
						
				strRes=strRes+"<table border='0' cellspacing='1' align='center' cellpadding='2' bgcolor='#FFFFFF' width='100%' class='tableizer-table'><tr bgcolor='#2364b1'><td class='prodheading' >Order Number</td><td class='prodheading' >Customer Name</td><td class='prodheading' >Customer Email ID</td><td class='prodheading'>Customer Mobile Number</td><td class='prodheading'>Retailer Name</td><td class='prodheading'>Retailer Mobile Number</td><td class='prodheading'>Inverter Brand</td><td class='prodheading'>Inverter Model</td><td class='prodheading'>Inverter Capacity</td><td class='prodheading'>Price</td><td class='prodheading'>Order Status</td><td class='prodheading'>Order Reasons</td><td class='prodheading'>Agent Comments</td><td class='prodheading'>Agent Name</td><td class='prodheading'>City</td><td class='prodheading'>Area</td><td class='prodheading'>Ordered Date</td><td class='prodheading'>Postponed Date</td><td class='prodheading'>Installed Date</td></tr>";
				strRes=strRes+"<table bgcolor='#FFFFFF' width='100%' cellspacing='1' border='0' align='center'  cellpadding='2' class='tableizer-table'><input type='checkbox' name='chkSi' value='' style='display:none'/>";
			  	for(int j=0; j<orddetails.size();j++)
				{
						Hashtable ht3=(Hashtable)orddetails.get(j);
						String ord_id=String.valueOf(ht3.get("order_id"));
						String order_number=String.valueOf(ht3.get("order_number"));
						String consumer_name=String.valueOf(ht3.get("consumer_name"));
						String consumer_emailid=String.valueOf(ht3.get("consumer_emailid"));
						String consumer_mobnumber=String.valueOf(ht3.get("consumer_mobnumber"));
						String retailer_name=String.valueOf(ht3.get("retailer_name"));
						String retailer_mobilnumber=String.valueOf(ht3.get("retailer_mobile_number"));
						String inverter_brand=String.valueOf(ht3.get("inverter_brand"));
						String inverter_model=String.valueOf(ht3.get("inverter_model"));
						String inverter_capacity=String.valueOf(ht3.get("inverter_capacity"));
						String price=String.valueOf(ht3.get("price"));
						String order_status=String.valueOf(ht3.get("order_status"));
						String order_reasons=String.valueOf(ht3.get("order_reasons"));
						String order_agent_comments=String.valueOf(ht3.get("order_agent_comments"));
						String agent_name=String.valueOf(ht3.get("agent_name"));
						String city=String.valueOf(ht3.get("city"));
						String area=String.valueOf(ht3.get("area"));
						String creation_date=String.valueOf(ht3.get("creation_date"));
						String postpone_date=String.valueOf(ht3.get("postpone_date"));
						String installed_date=String.valueOf(ht3.get("installed_date"));
					
					int Jcnt=j+1;
					strRes=strRes+"<tr><td class='table1 align='left'>"+order_number+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+consumer_name+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+consumer_emailid+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+consumer_mobnumber+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+retailer_name+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+retailer_mobilnumber+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+inverter_brand+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+inverter_model+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+inverter_capacity+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+price+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+order_status+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+order_reasons+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+order_agent_comments+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+agent_name+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+city+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+area+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+creation_date+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+postpone_date+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+installed_date+"</td></tr>";
					
					
				}
				strRes=strRes+"</table>";
				strRes=strRes+"</table></td></tr></table>";
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
	public String cancelorder(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort, String FromEmailId,String strsuppemaild,String SMSFromAddress)
	{
		String chkSi= (req.getParameter("chkSi")!=null)?(req.getParameter("chkSi")):"";
		LogLevel.DEBUG(5, new Throwable(), "chkSi :" + chkSi);

		String orderedstatus= (req.getParameter("orderedstatus")!=null)?(req.getParameter("orderedstatus")):"";
		LogLevel.DEBUG(5, new Throwable(), "orderedstatus :" + orderedstatus);

		String orderstatus= (req.getParameter("orderstatus")!=null)?(req.getParameter("orderstatus")):"";
		LogLevel.DEBUG(5, new Throwable(), "orderstatus :" + orderstatus);

		String postponedate= (req.getParameter("postponedate")!=null)?(req.getParameter("postponedate")):"00-00-0000";
		LogLevel.DEBUG(5, new Throwable(), "postponedate :" + postponedate);

		String cusmessage= (req.getParameter("cusmessage")!=null)?(req.getParameter("cusmessage")):"00-00-0000";
		LogLevel.DEBUG(5, new Throwable(), "cusmessage :" + cusmessage);

		String postponemessage= (req.getParameter("postponemessage")!=null)?(req.getParameter("postponemessage")):"";
		LogLevel.DEBUG(5, new Throwable(), "postponemessage :" + postponemessage);
		
		String offbushrsmsg= (req.getParameter("offbushrsmsg")!=null)?(req.getParameter("offbushrsmsg")):"";
		LogLevel.DEBUG(5, new Throwable(), "offbushrsmsg :" + offbushrsmsg);
		
		String franchiseedenied= (req.getParameter("franchiseedenied")!=null)?(req.getParameter("franchiseedenied")):"";
		LogLevel.DEBUG(5, new Throwable(), "franchiseedenied :" + franchiseedenied);
		
		String notrespondedmsg= (req.getParameter("notrespondedmsg")!=null)?(req.getParameter("notrespondedmsg")):"";
		LogLevel.DEBUG(5, new Throwable(), "notrespondedmsg :" + notrespondedmsg);
		
		String modeloutofstockmsg= (req.getParameter("modeloutofstockmsg")!=null)?(req.getParameter("modeloutofstockmsg")):"";
		LogLevel.DEBUG(5, new Throwable(), "modeloutofstockmsg :" + modeloutofstockmsg);
		
		String regeneratedmessage= (req.getParameter("regeneratedmessage")!=null)?(req.getParameter("regeneratedmessage")):"";
		LogLevel.DEBUG(5, new Throwable(), "regeneratedmessage :" + regeneratedmessage);

		String strRes="";
		try
		{	
			SendSMS sendsms = new SendSMS(qm);
			ServletOutputStream out=res.getOutputStream();
			String strpostponedate="";
			SimpleDateFormat sdfDate=new SimpleDateFormat("dd-MM-yyyy");
			Date txtpostponedate=sdfDate.parse(postponedate);

			SimpleDateFormat sdfString=new SimpleDateFormat("yyyy-MM-dd");
			strpostponedate=sdfString.format(txtpostponedate); 
			LogLevel.DEBUG(5, new Throwable(), "strpostponedate :"+ strpostponedate);

			String strSqlQryordstat = "SELECT order_status,consumer_name,consumer_emailid,battery_brand,battery_model,order_number,veh_name,veh_model,bat_type,consumer_mobnumber,retailer_name,retailer_mobilnumber,retailer_emailis,cancelled_message,postpone_message,offbusiness_message,franchiseedenied_message,notresponded_message,outofstock_message,cancelled_regenerated_message,city FROM battery_order_details WHERE  order_status='"+orderstatus+"' and ord_id='"+chkSi+"'";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQryordstat :"+strSqlQryordstat);

			Hashtable ht = qm.getRow(strSqlQryordstat);
			String order_status=(String)ht.get("order_status");
			String consumername=(String)ht.get("consumer_name");
			String consumermobilenumber=(String)ht.get("consumer_mobnumber");
			String retailername=(String)ht.get("retailer_name");
			String consumeremailid=(String)ht.get("consumer_emailid");
			String batterybrand=(String)ht.get("battery_brand");
			String batterymodel=(String)ht.get("battery_model");
			LogLevel.DEBUG(5,new Throwable(),"batterymodel :"+batterymodel);

			String ordernumber=(String)ht.get("order_number");
			String vehiclename=(String)ht.get("veh_name");
			String vehiclemodel=(String)ht.get("veh_model");
			String batterytype=(String)ht.get("bat_type");
			String retaileremail=(String)ht.get("retailer_emailis");

			String city=(String)ht.get("city");
			LogLevel.DEBUG(5,new Throwable(),"city :"+city);
			
			LogLevel.DEBUG(5,new Throwable(),"retaileremail :"+retaileremail);
			String retailermobilenumber=(String)ht.get("retailer_mobilnumber");
			String postpone_message=(String)ht.get("postpone_message");
			LogLevel.DEBUG(5,new Throwable(),"postpone_message :"+postpone_message);
			String cancelled_message=(String)ht.get("cancelled_message");
			LogLevel.DEBUG(5,new Throwable(),"cancelled_message :"+cancelled_message);
			String offbusiness_message=(String)ht.get("offbusiness_message");
			LogLevel.DEBUG(5,new Throwable(),"offbusiness_message :"+offbusiness_message);
			String franchiseedenied_message=(String)ht.get("franchiseedenied_message");
			LogLevel.DEBUG(5,new Throwable(),"franchiseedenied_message :"+franchiseedenied_message);
			String notresponded_message=(String)ht.get("notresponded_message");
			LogLevel.DEBUG(5,new Throwable(),"notresponded_message :"+notresponded_message);
			String outofstock_message=(String)ht.get("outofstock_message");
			LogLevel.DEBUG(5,new Throwable(),"outofstock_message :"+outofstock_message);
			String cancelled_regenerated_message=(String)ht.get("cancelled_regenerated_message");
			LogLevel.DEBUG(5,new Throwable(),"cancelled_regenerated_message :"+cancelled_regenerated_message);
			
			LogLevel.DEBUG(5,new Throwable(),"order_status :"+order_status);
			String strSqlQry ="";
			if(orderedstatus.equals("postponed"))
			{
					LogLevel.DEBUG(5,new Throwable(),"if conditions:");
					strSqlQry= "update battery_order_details set order_status='"+orderedstatus+"',postpone_date='"+strpostponedate+"',postpone_message='"+postponemessage+"',cancelled_message='0',offbusiness_message='0',outofstock_message='0',franchiseedenied_message='0',notresponded_message='0',cancelled_regenerated_message='0',updated_date=now() where ord_id='"+chkSi+"'";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
			}
			else if(orderedstatus.equals("cancelled-franchisee-offbushrs"))
			{
					LogLevel.DEBUG(5,new Throwable(),"if conditions:");
					strSqlQry= "update battery_order_details set order_status='"+orderedstatus+"',offbusiness_message='"+offbushrsmsg+"',cancelled_message='0',postpone_message='0',outofstock_message='0',franchiseedenied_message='0',notresponded_message='0',cancelled_regenerated_message='0',updated_date=now() where ord_id='"+chkSi+"'";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
			}
			
			else if(orderedstatus.equals("cancelled-franchisee-denied"))
			{
					LogLevel.DEBUG(5,new Throwable(),"if conditions:");
					strSqlQry= "update battery_order_details set order_status='"+orderedstatus+"',franchiseedenied_message='"+franchiseedenied+"',cancelled_message='0',postpone_message='0',outofstock_message='0',offbusiness_message='0',notresponded_message='0',cancelled_regenerated_message='0',updated_date=now() where ord_id='"+chkSi+"'";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
			}
			else if(orderedstatus.equals("cancelled-franchisee-notresponded"))
			{
					LogLevel.DEBUG(5,new Throwable(),"if conditions:");
					strSqlQry= "update battery_order_details set order_status='"+orderedstatus+"',notresponded_message='"+notrespondedmsg+"',cancelled_message='0',postpone_message='0',outofstock_message='0',offbusiness_message='0',franchiseedenied_message='0',cancelled_regenerated_message='0',updated_date=now() where ord_id='"+chkSi+"'";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
			}
			else if(orderedstatus.equals("cancelled-franchisee-modeloutofstock"))
			{
					LogLevel.DEBUG(5,new Throwable(),"if conditions:");
					strSqlQry= "update battery_order_details set order_status='"+orderedstatus+"',outofstock_message='"+modeloutofstockmsg+"',cancelled_message='0',postpone_message='0',notresponded_message='0',offbusiness_message='0',franchiseedenied_message='0',cancelled_regenerated_message='0',updated_date=now() where ord_id='"+chkSi+"'";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
			}
			else if(orderedstatus.equals("cancelled-regenerated"))
				{
						LogLevel.DEBUG(5,new Throwable(),"if conditions:");
						strSqlQry= "update battery_order_details set order_status='"+orderedstatus+"',cancelled_regenerated_message='"+regeneratedmessage+"',cancelled_message='0',postpone_message='0',outofstock_message='0',notresponded_message='0',offbusiness_message='0',franchiseedenied_message='0',updated_date=now() where ord_id='"+chkSi+"'";
						LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
				}
			else
			{			
				if(order_status.equals(orderedstatus))
				{ 
					LogLevel.DEBUG(5,new Throwable(),"if conditions:");
					LogLevel.DEBUG(1,new Throwable(),"");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'></font> ");
					out.println("Already Updated ordered Status as "+orderedstatus+".!");	
				}
				else
				{			
						String grandtotal="";
						LogLevel.DEBUG(5,new Throwable(),"else conditions:");
					if(batterybrand.equals("Amaron"))
					{
						if(retailername.contains("Amaron-Pitstop"))
					{

					}
					else
						{
							/** jhansi started placing code to calculate commission amount **/
							String strSqlQrycalculatecom = "select CAST(round(((bat_witbat_price))-(erp_pre_tax))/ 2 AS SIGNED) as grandtotal FROM batteryprice WHERE  bat_model='"+batterymodel+"' and city='"+city+"'";
							LogLevel.DEBUG(5,new Throwable(),"strSqlQrycalculatecom :"+strSqlQrycalculatecom);

							Hashtable htcalculate = qm.getRow(strSqlQrycalculatecom);
							grandtotal=String.valueOf(htcalculate.get("grandtotal"));
							LogLevel.DEBUG(5,new Throwable(),"grandtotal :"+grandtotal);
							String strSMSResponse22 ="";
							/** jhansi ended placing code to calculate commission amount **/

						}
						
					}
					else
					{
						/** jhansi started placing code to calculate commission amount **/
						String strSqlQrycalculatecom = "select CAST(round(((bat_witbat_price))-(erp_pre_tax))/ 2 AS SIGNED) as grandtotal FROM batteryprice WHERE  bat_model='"+batterymodel+"' and city='"+city+"'";
						LogLevel.DEBUG(5,new Throwable(),"strSqlQrycalculatecom :"+strSqlQrycalculatecom);

						Hashtable htcalculate = qm.getRow(strSqlQrycalculatecom);
						grandtotal=String.valueOf(htcalculate.get("grandtotal"));
						LogLevel.DEBUG(5,new Throwable(),"grandtotal :"+grandtotal);
						String strSMSResponse22 ="";
						/** jhansi ended placing code to calculate commission amount **/

					}

					strSqlQry= "update battery_order_details set order_status='"+orderedstatus+"',cancelled_message='"+cusmessage+"',postpone_message='0',offbusiness_message='0',outofstock_message='0',notresponded_message='0',franchiseedenied_message='0',cancelled_regenerated_message='0',updated_date=now(),battery_commission_amount='"+grandtotal+"' where ord_id='"+chkSi+"'";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
				}
			}

			int i=qm.executeUpdate(strSqlQry);
			if(i <0)
			{
				LogLevel.DEBUG(1,new Throwable(),"No Name exists u can continue ");
				session.setAttribute("priority","1");
				session.setAttribute("sesErrorMsg", "<font color='#00dd00' class='vrb10'></font> ");
				out.println("Failed to update ordered status!");
			}
			else
			{
				LogLevel.DEBUG(1,new Throwable(),"");
				session.setAttribute("priority","1");
				session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'></font> ");
				//System.out.println(email);
				out.println("Successfully Updated ordered Status as "+orderedstatus+".!");
				
				if(orderedstatus.equals("installed"))
				{
					String CustomerMessage;
					if(batterytype.equals("Inverter Batteries"))
					{

						CustomerMessage="Your ordered battery on "+batterybrand+" "+batterymodel+" with order number "+ordernumber+" has been successfully installed by "+retailername+".";
					}
					else
					{
						CustomerMessage="Your ordered battery on "+batterybrand+" "+batterymodel+" for "+vehiclename+" "+vehiclemodel+" with order number "+ordernumber+" has been successfully installed by "+retailername+".";
					}

					String strCustomerMessage="Dear "+consumername+",\r\n\r\n"+""+CustomerMessage+"";
					String strThanksMessage="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
					MailTrans consumer=new MailTrans();
					consumer.setStrSmtpHost(strdomainname);
					consumer.setStrFrom(FromEmailId);
					consumer.setStrTo(consumeremailid);
					LogLevel.DEBUG(5, new Throwable(), "consumeremailid :" + consumeremailid);
					consumer.setStrSubject("BookBattery Battery Ordered Status");
					String customerMail = strCustomerMessage+"\r\n\r\n"+strThanksMessage+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "customerMail :" + customerMail);
					consumer.setStrText(customerMail);
					Thread customerMailThread=new MailThread(consumer,"");
					customerMailThread.start();
					String FranchiseMessage="";					
					String FranchiseemailMessage="";

					LogLevel.DEBUG(5, new Throwable(), "batterybrand jhansi :" + batterybrand);

					if(batterybrand.equals("Amaron"))
					{
						if(retailername.contains("Amaron-Pitstop"))
						{

						if(batterytype.equals("Inverter Batteries"))
						{
							FranchiseMessage="The ordered battery with order number "+ordernumber+" has been successfully installed. Order Details Customer Name- "+consumername+", Customer Mobile Num- "+consumermobilenumber+", Battery Brand- "+batterybrand+", Battery Model- "+batterymodel+"";

							FranchiseemailMessage="The ordered battery with order number "+ordernumber+" has been successfully installed. Order Details Customer Name- "+consumername+", Customer Mobile Num- "+consumermobilenumber+", Battery Brand- "+batterybrand+", Battery Model- "+batterymodel+"";
						}
						else
						{
							FranchiseMessage="The ordered battery with order number "+ordernumber+" has been successfully installed. Order Details Customer Name- "+consumername+", Customer Mobile Num- "+consumermobilenumber+", Battery Brand- "+batterybrand+", Battery Model- "+batterymodel+", Vehicle Make- "+vehiclename+", Vehicle Model- "+vehiclemodel+"";

							FranchiseemailMessage="The ordered battery with order number "+ordernumber+" has been successfully installed. Order Details Customer Name- "+consumername+", Customer Mobile Num- "+consumermobilenumber+", Battery Brand- "+batterybrand+", Battery Model- "+batterymodel+", Vehicle Make- "+vehiclename+", Vehicle Model- "+vehiclemodel+"";
						}
						}

						else
						{

							/** jhansi started placing code to calculate commission amount **/
							String strSqlQrycalculatecom = "select CAST(round(((bat_witbat_price))-(erp_pre_tax))/ 2 AS SIGNED) as grandtotal FROM batteryprice WHERE  bat_model='"+batterymodel+"' and city='"+city+"'";
							LogLevel.DEBUG(5,new Throwable(),"strSqlQrycalculatecom :"+strSqlQrycalculatecom);

							Hashtable htcalculate = qm.getRow(strSqlQrycalculatecom);
							String grandtotal=String.valueOf(htcalculate.get("grandtotal"));
							LogLevel.DEBUG(5,new Throwable(),"grandtotal :"+grandtotal);
							String strSMSResponse22 ="";
							/** jhansi ended placing code to calculate commission amount **/

							if(batterytype.equals("Inverter Batteries"))
							{
								//FranchiseMessage="The ordered battery with order number "+ordernumber+" has been successfully installed. Order Details Customer Name- "+consumername+", Customer Mobile Num- "+consumermobilenumber+", Battery Brand- "+batterybrand+", Battery Model- "+batterymodel+". Please credit commission amount Rs."+grandtotal+" in the company bank account with in 72hours.";
								FranchiseemailMessage="The ordered battery with order number "+ordernumber+" has been successfully installed. Order Details Customer Name- "+consumername+" , Customer Mobile Num- "+consumermobilenumber+" , Battery Brand- "+batterybrand+" , Battery Model- "+batterymodel+" . Please credit commission amount Rs. "+grandtotal+" in the company bank account with in 72hours.";

								FranchiseMessage="The ordered battery with order number "+ordernumber+" has been successfully installed. Please credit commission amount Rs. "+grandtotal+" in the company bank account with in 72hours";

								//The ordered battery with order number ORD497739 has been successfully installed. Order Details Customer Name- Harinath , Customer Mobile Num- 9705168668 , Battery Brand- Exide , Battery Model- EXFO-IP2000 . Please credit commission amount Rs. 1544 in the company bank account with in 72hours.
								LogLevel.DEBUG(5, new Throwable(), "strIpAddress :" + strIpAddress);
								LogLevel.DEBUG(5, new Throwable(), "strPort :" + strPort);
								LogLevel.DEBUG(5, new Throwable(), "SMSFromAddress :" + SMSFromAddress);
								LogLevel.DEBUG(5, new Throwable(), "FranchiseMessage :" + FranchiseMessage);
								LogLevel.DEBUG(5, new Throwable(), "retailermobilenumber :" + retailermobilenumber);

								strSMSResponse22 =  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,FranchiseMessage, retailermobilenumber);
								LogLevel.DEBUG(5, new Throwable(), "strSMSResponse22 :" + strSMSResponse22);

							}
							else
							{
								//FranchiseMessage="The ordered battery with order number "+ordernumber+" has been successfully installed. Order Details Customer Name- "+consumername+", Customer Mobile Num- "+consumermobilenumber+", Battery Brand- "+batterybrand+", Battery Model- "+batterymodel+", Vehicle Make- "+vehiclename+", Vehicle Model- "+vehiclemodel+".Please credit commission amount Rs."+grandtotal+" in the company bank account with in 72hours.";

								FranchiseemailMessage="The ordered battery with order number "+ordernumber+" has been successfully installed. Order Details Customer Name- "+consumername+" , Customer Mobile Num- "+consumermobilenumber+" , Battery Brand- "+batterybrand+" , Battery Model- "+batterymodel+" , Vehicle Make- "+vehiclename+" , Vehicle Model- "+vehiclemodel+" .Please credit commission amount Rs. "+grandtotal+" in the company bank account with in 72hours.";
								FranchiseMessage="The ordered battery with order number "+ordernumber+" has been successfully installed. Please credit commission amount Rs. "+grandtotal+" in the company bank account with in 72hours";

								//The ordered battery with order number XXXX has been successfully installed. Order Details Customer Name- XXXX , Customer Mobile Num- XXXX , Battery Brand- XXXX , Battery Model- XXXX , Vehicle Make- XXXX , Vehicle Model- XXXX .Please credit commission amount Rs. XXXX in the company bank account with in 72hours.
								LogLevel.DEBUG(5, new Throwable(), "strIpAddress :" + strIpAddress);
								LogLevel.DEBUG(5, new Throwable(), "strPort :" + strPort);
								LogLevel.DEBUG(5, new Throwable(), "SMSFromAddress :" + SMSFromAddress);
								LogLevel.DEBUG(5, new Throwable(), "FranchiseMessage :" + FranchiseMessage);
								LogLevel.DEBUG(5, new Throwable(), "retailermobilenumber :" + retailermobilenumber);

								strSMSResponse22 =  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,FranchiseMessage, retailermobilenumber);
								LogLevel.DEBUG(5, new Throwable(), "strSMSResponse22 :" + strSMSResponse22);

								
							}


						}
					}

					else
					{
						/** jhansi started placing code to calculate commission amount **/
							String strSqlQrycalculatecom = "select CAST(round(((bat_witbat_price))-(erp_pre_tax))/ 2 AS SIGNED) as grandtotal FROM batteryprice WHERE  bat_model='"+batterymodel+"' and city='"+city+"'";
							LogLevel.DEBUG(5,new Throwable(),"strSqlQrycalculatecom :"+strSqlQrycalculatecom);

							Hashtable htcalculate = qm.getRow(strSqlQrycalculatecom);
							String grandtotal=String.valueOf(htcalculate.get("grandtotal"));
							LogLevel.DEBUG(5,new Throwable(),"grandtotal :"+grandtotal);
							String strSMSResponse22 ="";
						/** jhansi ended placing code to calculate commission amount **/

						if(batterytype.equals("Inverter Batteries"))
						{
							//FranchiseMessage="The ordered battery with order number "+ordernumber+" has been successfully installed. Order Details Customer Name- "+consumername+", Customer Mobile Num- "+consumermobilenumber+", Battery Brand- "+batterybrand+", Battery Model- "+batterymodel+". Please credit commission amount Rs."+grandtotal+" in the company bank account with in 72hours.";
							FranchiseemailMessage="The ordered battery with order number "+ordernumber+" has been successfully installed. Order Details Customer Name- "+consumername+" , Customer Mobile Num- "+consumermobilenumber+" , Battery Brand- "+batterybrand+" , Battery Model- "+batterymodel+" . Please credit commission amount Rs. "+grandtotal+" in the company bank account with in 72hours.";

							FranchiseMessage="The ordered battery with order number "+ordernumber+" has been successfully installed. Please credit commission amount Rs. "+grandtotal+" in the company bank account with in 72hours";

							//The ordered battery with order number ORD497739 has been successfully installed. Order Details Customer Name- Harinath , Customer Mobile Num- 9705168668 , Battery Brand- Exide , Battery Model- EXFO-IP2000 . Please credit commission amount Rs. 1544 in the company bank account with in 72hours.
							LogLevel.DEBUG(5, new Throwable(), "strIpAddress :" + strIpAddress);
							LogLevel.DEBUG(5, new Throwable(), "strPort :" + strPort);
							LogLevel.DEBUG(5, new Throwable(), "SMSFromAddress :" + SMSFromAddress);
							LogLevel.DEBUG(5, new Throwable(), "FranchiseMessage :" + FranchiseMessage);
							LogLevel.DEBUG(5, new Throwable(), "retailermobilenumber :" + retailermobilenumber);

							strSMSResponse22 =  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,FranchiseMessage, retailermobilenumber);
							LogLevel.DEBUG(5, new Throwable(), "strSMSResponse22 :" + strSMSResponse22);

						}
						else
						{
							//FranchiseMessage="The ordered battery with order number "+ordernumber+" has been successfully installed. Order Details Customer Name- "+consumername+", Customer Mobile Num- "+consumermobilenumber+", Battery Brand- "+batterybrand+", Battery Model- "+batterymodel+", Vehicle Make- "+vehiclename+", Vehicle Model- "+vehiclemodel+".Please credit commission amount Rs."+grandtotal+" in the company bank account with in 72hours.";

							FranchiseemailMessage="The ordered battery with order number "+ordernumber+" has been successfully installed. Order Details Customer Name- "+consumername+" , Customer Mobile Num- "+consumermobilenumber+" , Battery Brand- "+batterybrand+" , Battery Model- "+batterymodel+" , Vehicle Make- "+vehiclename+" , Vehicle Model- "+vehiclemodel+" .Please credit commission amount Rs. "+grandtotal+" in the company bank account with in 72hours.";
							FranchiseMessage="The ordered battery with order number "+ordernumber+" has been successfully installed. Please credit commission amount Rs. "+grandtotal+" in the company bank account with in 72hours";

							//The ordered battery with order number XXXX has been successfully installed. Order Details Customer Name- XXXX , Customer Mobile Num- XXXX , Battery Brand- XXXX , Battery Model- XXXX , Vehicle Make- XXXX , Vehicle Model- XXXX .Please credit commission amount Rs. XXXX in the company bank account with in 72hours.
							LogLevel.DEBUG(5, new Throwable(), "strIpAddress :" + strIpAddress);
							LogLevel.DEBUG(5, new Throwable(), "strPort :" + strPort);
							LogLevel.DEBUG(5, new Throwable(), "SMSFromAddress :" + SMSFromAddress);
							LogLevel.DEBUG(5, new Throwable(), "FranchiseMessage :" + FranchiseMessage);
							LogLevel.DEBUG(5, new Throwable(), "retailermobilenumber :" + retailermobilenumber);

							strSMSResponse22 =  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,FranchiseMessage, retailermobilenumber);
							LogLevel.DEBUG(5, new Throwable(), "strSMSResponse22 :" + strSMSResponse22);

							
						}

					}

					String strFranchiseMessage="Dear "+retailername+",\r\n\r\n"+""+FranchiseemailMessage+"";
					MailTrans franchise=new MailTrans();
					franchise.setStrSmtpHost(strdomainname);
					franchise.setStrFrom(FromEmailId);
					franchise.setStrTo(retaileremail);
					LogLevel.DEBUG(5, new Throwable(), "retailermobilenumber :" + retailermobilenumber);
					String strThanksMessagedealer=""; 
					String franchiseMail ="";
					if(batterybrand.equals("Amaron"))
					{
						if(retailername.contains("Amaron-Pitstop"))
						{
							strThanksMessagedealer="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
							franchise.setStrSubject("BookBattery Battery Ordered Status");
							franchiseMail = strFranchiseMessage+"\r\n\r\n"+strThanksMessage+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
							LogLevel.DEBUG(5, new Throwable(), "franchiseMail :" + franchiseMail);
						}
						else
						{
							strThanksMessagedealer="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 

							franchise.setStrSubject("BookBattery Battery Ordered Status");
							franchiseMail = strFranchiseMessage+"\r\n\r\n"+strThanksMessage+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
							LogLevel.DEBUG(5, new Throwable(), "franchiseMail :" + franchiseMail);


						}

					}

					else
					{
						strThanksMessagedealer="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 

						franchise.setStrSubject("BookBattery Battery Ordered Status");
						franchiseMail = strFranchiseMessage+"\r\n\r\n"+strThanksMessage+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
						LogLevel.DEBUG(5, new Throwable(), "franchiseMail :" + franchiseMail);

					}
					franchise.setStrText(franchiseMail);
					Thread franchiseMailThread=new MailThread(franchise,"");
					franchiseMailThread.start();

					

					String OperatorMessage;
					if(batterytype.equals("Inverter Batteries"))
					{
						OperatorMessage="The ordered battery with order number "+ordernumber+" has been successfully installed. Order Details Customer Name- "+consumername+", Customer Mobile Num- "+consumermobilenumber+", Battery Brand- "+batterybrand+", Battery Model- "+batterymodel+", Franchisee Name- "+retailername+", Franchisee Mobile Number- "+retailermobilenumber+"";
					}
					else
					{
						OperatorMessage="The ordered battery with order number "+ordernumber+" has been successfully installed. Order Details Customer Name- "+consumername+", Customer Mobile Num- "+consumermobilenumber+", Battery Brand- "+batterybrand+", Battery Model- "+batterymodel+", Vehicle Make- "+vehiclename+", Vehicle Model- "+vehiclemodel+", Franchisee Name- "+retailername+", Franchisee Mobile Number- "+retailermobilenumber+"";
					}
					String strOperatorMessage="Dear Operator,\r\n\r\n"+""+OperatorMessage+"";
					MailTrans operator=new MailTrans();
					operator.setStrSmtpHost(strdomainname);
					operator.setStrFrom(FromEmailId);
					operator.setStrTo(strsuppemaild);
					LogLevel.DEBUG(5, new Throwable(), "strsuppemaild :" + strsuppemaild);
					operator.setStrSubject("BookBattery Battery Ordered Status");
					String operatorMail = strOperatorMessage+"\r\n\r\n"+strThanksMessage+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "operatorMail :" + operatorMail);
					operator.setStrText(operatorMail);
					Thread operatorMailThread=new MailThread(operator,"");
					operatorMailThread.start();
				} 
				if(orderedstatus.equals("cancelled-franchisee-notresponded")||orderedstatus.equals("cancelled-franchisee-modeloutofstock")||orderedstatus.equals("cancelled-franchisee-offbushrs")||orderedstatus.equals("cancelled-franchisee-denied")||orderedstatus.equals("cancelled-customer") ||orderedstatus.equals("cancelled-regenerated"))
				{
					String CustomerMessage;
					String CancelledCustomerMesg="";
					String CancelledOperatorMesg="";
					if(orderedstatus.equals("cancelled-franchisee-notresponded"))
					{
						CancelledOperatorMesg="due to franchisee has not responsed";
						CancelledCustomerMesg="due to your ordered model is out of stock";
					}
					else if(orderedstatus.equals("cancelled-franchisee-modeloutofstock"))
					{
						CancelledOperatorMesg="due to your ordered model is out of stock";
						CancelledCustomerMesg="due to your ordered model is out of stock";
					}
					else if(orderedstatus.equals("cancelled-franchisee-offbushrs"))
					{
						CancelledOperatorMesg="due to off business hours for franchisee";
						CancelledCustomerMesg="due to off business hours for franchisee";
					}
					else if(orderedstatus.equals("cancelled-franchisee-denied"))
					{
						CancelledOperatorMesg="due to our franchisee has denied";
						CancelledCustomerMesg="due to your ordered model is out of stock";
					}
					else if(orderedstatus.equals("cancelled-customer"))
					{
						CancelledOperatorMesg="by customer";
						CancelledCustomerMesg="due to "+cusmessage+"";
					}
					else if(orderedstatus.equals("cancelled-regenerated"))
					{
						if(batterytype.equals("Inverter Batteries"))
						{
							CancelledOperatorMesg="and regenerated with another model which fits your inverter";
							CancelledCustomerMesg="and regenerated with another model which fits your inverter";
						}
						else
						{
							CancelledOperatorMesg="and regenerated with another model which fits your vehicle";
							CancelledCustomerMesg="and regenerated with another model which fits your vehicle";
						}						
					}
					else
					{

					}
					if(batterytype.equals("Inverter Batteries"))
					{
						CustomerMessage="Your ordered battery on "+batterybrand+" "+batterymodel+" with order number "+ordernumber+" has been cancelled "+CancelledCustomerMesg+".\r\n\r\n We are looking forward to see you again at www.bookbattery.com .";
					}
					else
					{
						CustomerMessage="Your ordered battery on "+batterybrand+" "+batterymodel+" for "+vehiclename+" "+vehiclemodel+" with order number "+ordernumber+" has been cancelled "+CancelledCustomerMesg+".\r\n\r\n We are looking forward to see you again at www.bookbattery.com .";
					}

					String strCustomerMessage="Dear "+consumername+",\r\n\r\n"+""+CustomerMessage+"";
					String strThanksMessage="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
					MailTrans consumer=new MailTrans();
					consumer.setStrSmtpHost(strdomainname);
					consumer.setStrFrom(FromEmailId);
					consumer.setStrTo(consumeremailid);
					LogLevel.DEBUG(5, new Throwable(), "consumeremailid :" + consumeremailid);
					consumer.setStrSubject("BookBattery Battery Ordered Status");
					String customerMail = strCustomerMessage+"\r\n\r\n"+strThanksMessage+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "customerMail :" + customerMail);
					consumer.setStrText(customerMail);
					Thread customerMailThread=new MailThread(consumer,"");
					customerMailThread.start();

					String OperatorMessage;
					if(batterytype.equals("Inverter Batteries"))
					{
						OperatorMessage="The ordered battery with order number "+ordernumber+" has been cancelled "+CancelledOperatorMesg+". Order Details Customer Name- "+consumername+", Customer Mobile Num- "+consumermobilenumber+", Battery Brand- "+batterybrand+", Battery Model- "+batterymodel+", Franchisee Name- "+retailername+"";
					}
					else
					{
						OperatorMessage="The ordered battery with order number "+ordernumber+" has been cancelled "+CancelledOperatorMesg+". Order Details Customer Name- "+consumername+", Customer Mobile Num- "+consumermobilenumber+", Battery Brand- "+batterybrand+", Battery Model- "+batterymodel+", Vehicle Make- "+vehiclename+", Vehicle Model- "+vehiclemodel+", Franchisee Name- "+retailername+"";
					}
					String strOperatorMessage="Dear Operator,\r\n\r\n"+""+OperatorMessage+"";
					MailTrans operator=new MailTrans();
					operator.setStrSmtpHost(strdomainname);
					operator.setStrFrom(FromEmailId);
					operator.setStrTo(strsuppemaild);
					LogLevel.DEBUG(5, new Throwable(), "strsuppemaild :" + strsuppemaild);
					operator.setStrSubject("BookBattery Battery Ordered Status");
					String operatorMail = strOperatorMessage+"\r\n\r\n"+strThanksMessage+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "operatorMail :" + operatorMail);
					operator.setStrText(operatorMail);
					Thread operatorMailThread=new MailThread(operator,"");
					operatorMailThread.start();
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
	/* **************************************************************************************************************************************\
	* This method is to cancel the inverter order
	* @strSqlQry : carries the query to cancel the inverter order.
	\* **************************************************************************************************************************************/
	public String cancelinverterorder(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort, String FromEmailId,String strsuppemaild,String SMSFromAddress)
	{
		String chkSi= (req.getParameter("chkSi")!=null)?(req.getParameter("chkSi")):"";
		LogLevel.DEBUG(5, new Throwable(), "chkSi :" + chkSi);

		String orderedstatus= (req.getParameter("orderedstatus")!=null)?(req.getParameter("orderedstatus")):"";
		LogLevel.DEBUG(5, new Throwable(), "orderedstatus :" + orderedstatus);

		String orderstatus= (req.getParameter("orderstatus")!=null)?(req.getParameter("orderstatus")):"";
		LogLevel.DEBUG(5, new Throwable(), "orderstatus :" + orderstatus);

		String postponedate= (req.getParameter("postponedate")!=null)?(req.getParameter("postponedate")):"00-00-0000";
		LogLevel.DEBUG(5, new Throwable(), "postponedate :" + postponedate);

		String cusmessage= (req.getParameter("cusmessage")!=null)?(req.getParameter("cusmessage")):"";
		LogLevel.DEBUG(5, new Throwable(), "cusmessage :" + cusmessage);

		String postponemessage= (req.getParameter("postponemessage")!=null)?(req.getParameter("postponemessage")):"";
		LogLevel.DEBUG(5, new Throwable(), "postponemessage :" + postponemessage);
		
		String offbushrsmsg= (req.getParameter("offbushrsmsg")!=null)?(req.getParameter("offbushrsmsg")):"";
		LogLevel.DEBUG(5, new Throwable(), "offbushrsmsg :" + offbushrsmsg);
		
		String franchiseedenied= (req.getParameter("franchiseedenied")!=null)?(req.getParameter("franchiseedenied")):"";
		LogLevel.DEBUG(5, new Throwable(), "franchiseedenied :" + franchiseedenied);
		
		String notrespondedmsg= (req.getParameter("notrespondedmsg")!=null)?(req.getParameter("notrespondedmsg")):"";
		LogLevel.DEBUG(5, new Throwable(), "notrespondedmsg :" + notrespondedmsg);
		
		String modeloutofstockmsg= (req.getParameter("modeloutofstockmsg")!=null)?(req.getParameter("modeloutofstockmsg")):"";
		LogLevel.DEBUG(5, new Throwable(), "modeloutofstockmsg :" + modeloutofstockmsg);

		String regeneratedmessage= (req.getParameter("regeneratedmessage")!=null)?(req.getParameter("regeneratedmessage")):"";
		LogLevel.DEBUG(5, new Throwable(), "regeneratedmessage :" + regeneratedmessage);
		
		String strRes="";
		try
		{	
			SendSMS sendsms = new SendSMS(qm);

			ServletOutputStream out=res.getOutputStream();
			String strpostponedate="";
			SimpleDateFormat sdfDate=new SimpleDateFormat("dd-MM-yyyy");
			Date txtpostponedate=sdfDate.parse(postponedate);

			SimpleDateFormat sdfString=new SimpleDateFormat("yyyy-MM-dd");
			strpostponedate=sdfString.format(txtpostponedate); 
			LogLevel.DEBUG(5, new Throwable(), "strpostponedate :"+ strpostponedate);

			String strSqlQryordstat = "SELECT order_status,consumer_name,consumer_emailid,inverter_brand,inverter_model,inverter_capacity,order_number,consumer_mobnumber,retailer_name,retailer_mobile_number,retailer_emailid,postpone_message,cancelled_message,offbusiness_message,franchiseedenied_message,notresponded_message,outofstock_message,cancelled_regenerated_message,city FROM inverter_order_details WHERE  order_status='"+orderstatus+"' and order_id='"+chkSi+"'";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQryordstat :"+strSqlQryordstat);

			Hashtable ht = qm.getRow(strSqlQryordstat);
			String order_status=(String)ht.get("order_status");
			String consumername=(String)ht.get("consumer_name");
			String consumermobilenumber=(String)ht.get("consumer_mobnumber");
			String retailername=(String)ht.get("retailer_name");
			String consumeremailid=(String)ht.get("consumer_emailid");
			String inverterbrand=(String)ht.get("inverter_brand");
			String invertermodel=(String)ht.get("inverter_model");
			LogLevel.DEBUG(5,new Throwable(),"invertermodel :"+invertermodel);
			String invertercapacity=(String)ht.get("inverter_capacity");
			String ordernumber=(String)ht.get("order_number");
			String city=(String)ht.get("city");
			LogLevel.DEBUG(5,new Throwable(),"city :"+city);
			
			String retaileremail=(String)ht.get("retailer_emailid");
			LogLevel.DEBUG(5,new Throwable(),"retaileremail :"+retaileremail);
			String retailermobilenumber=(String)ht.get("retailer_mobile_number");
			String postpone_message=(String)ht.get("postpone_message");
			String cancelled_message=(String)ht.get("cancelled_message");
			LogLevel.DEBUG(5,new Throwable(),"cancelled_message :"+cancelled_message);
			String offbusiness_message=(String)ht.get("offbusiness_message");
			LogLevel.DEBUG(5,new Throwable(),"offbusiness_message :"+offbusiness_message);
			String franchiseedenied_message=(String)ht.get("franchiseedenied_message");
			LogLevel.DEBUG(5,new Throwable(),"franchiseedenied_message :"+franchiseedenied_message);
			String notresponded_message=(String)ht.get("notresponded_message");
			LogLevel.DEBUG(5,new Throwable(),"notresponded_message :"+notresponded_message);
			String outofstock_message=(String)ht.get("outofstock_message");
			LogLevel.DEBUG(5,new Throwable(),"outofstock_message :"+outofstock_message);
			String cancelled_regenerated_message=(String)ht.get("cancelled_regenerated_message");
			LogLevel.DEBUG(5,new Throwable(),"cancelled_regenerated_message :"+cancelled_regenerated_message);
			
			
			LogLevel.DEBUG(5,new Throwable(),"order_status :"+order_status);
			String strSqlQry ="";
			if(orderedstatus.equals("postponed"))
			{
					LogLevel.DEBUG(5,new Throwable(),"if conditions:");
					strSqlQry= "update inverter_order_details set order_status='"+orderedstatus+"',postpone_date='"+strpostponedate+"',cancelled_message='0',offbusiness_message='0',outofstock_message='0',notresponded_message='0',franchiseedenied_message='0',cancelled_regenerated_message='0',updated_date=now(),postpone_message='"+postponemessage+"' where order_id='"+chkSi+"'";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
			}
			else if(orderedstatus.equals("cancelled-franchisee-offbushrs"))
				{
						LogLevel.DEBUG(5,new Throwable(),"if conditions:");
						strSqlQry= "update inverter_order_details set order_status='"+orderedstatus+"',offbusiness_message='"+offbushrsmsg+"',cancelled_message='0',postpone_message='0',outofstock_message='0',notresponded_message='0',franchiseedenied_message='0',cancelled_regenerated_message='0',updated_date=now() where order_id='"+chkSi+"'";
						LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
				}
			else if(orderedstatus.equals("cancelled-franchisee-denied"))
				{
						LogLevel.DEBUG(5,new Throwable(),"if conditions:");
						strSqlQry= "update inverter_order_details set order_status='"+orderedstatus+"',franchiseedenied_message='"+franchiseedenied+"',cancelled_message='0',postpone_message='0',outofstock_message='0',notresponded_message='0',offbusiness_message='0',cancelled_regenerated_message='0',updated_date=now() where order_id='"+chkSi+"'";
						LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
				}
			else if(orderedstatus.equals("cancelled-franchisee-notresponded"))
				{
						LogLevel.DEBUG(5,new Throwable(),"if conditions:");
						strSqlQry= "update inverter_order_details set order_status='"+orderedstatus+"',notresponded_message='"+notrespondedmsg+"',cancelled_message='0',postpone_message='0',outofstock_message='0',offbusiness_message='0',franchiseedenied_message='0',cancelled_regenerated_message='0',updated_date=now() where order_id='"+chkSi+"'";
						LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
				}
			else if(orderedstatus.equals("cancelled-franchisee-modeloutofstock"))
				{
						LogLevel.DEBUG(5,new Throwable(),"if conditions:");
						strSqlQry= "update inverter_order_details set order_status='"+orderedstatus+"',outofstock_message='"+modeloutofstockmsg+"',cancelled_message='0',postpone_message='0',notresponded_message='0',offbusiness_message='0',franchiseedenied_message='0',cancelled_regenerated_message='0',updated_date=now() where order_id='"+chkSi+"'";
						LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
				}
			else if(orderedstatus.equals("cancelled-regenerated"))
				{
						LogLevel.DEBUG(5,new Throwable(),"if conditions:");
						strSqlQry= "update inverter_order_details set order_status='"+orderedstatus+"',cancelled_regenerated_message='"+regeneratedmessage+"',cancelled_message='0',postpone_message='0',notresponded_message='0',outofstock_message='0',offbusiness_message='0',franchiseedenied_message='0',updated_date=now() where order_id='"+chkSi+"'";
						LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
				}
			else
			{			
				if(order_status.equals(orderedstatus))
				{ 
					LogLevel.DEBUG(5,new Throwable(),"if conditions:");
					LogLevel.DEBUG(1,new Throwable(),"");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'></font> ");
					out.println("Already Updated ordered Status as "+orderedstatus+".!");	
				}
				else
				{
					
					String grandtotal="";
					if(inverterbrand.equals("Amaron"))
					{
						if(retailername.contains("Amaron-Pitstop"))
						{



						}
						else
						{

							/** jhansi started placing code to calculate commission amount **/
							String strSqlQrycalculatecom = "select CAST(round(((inverter_discount_price))-(inverter_eretailer_price))/ 2 AS SIGNED) as grandtotal FROM inverter_price_details WHERE  inverter_capacity='"+invertercapacity+"' and inverter_model='"+invertermodel+"' and city='"+city+"'";
							LogLevel.DEBUG(5,new Throwable(),"strSqlQrycalculatecom :"+strSqlQrycalculatecom);
							Hashtable htcalculate = qm.getRow(strSqlQrycalculatecom);
							grandtotal=String.valueOf(htcalculate.get("grandtotal"));
							LogLevel.DEBUG(5,new Throwable(),"grandtotal :"+grandtotal);
							/** jhansi ended placing code to calculate commission amount **/

						}
					}
					else
					{

						/** jhansi started placing code to calculate commission amount **/
						String strSqlQrycalculatecom = "select CAST(round(((inverter_discount_price))-(inverter_eretailer_price))/ 2 AS SIGNED) as grandtotal FROM inverter_price_details WHERE  inverter_capacity='"+invertercapacity+"' and inverter_model='"+invertermodel+"' and city='"+city+"'";
						LogLevel.DEBUG(5,new Throwable(),"strSqlQrycalculatecom :"+strSqlQrycalculatecom);
						Hashtable htcalculate = qm.getRow(strSqlQrycalculatecom);
						grandtotal=String.valueOf(htcalculate.get("grandtotal"));
						LogLevel.DEBUG(5,new Throwable(),"grandtotal :"+grandtotal);
						/** jhansi ended placing code to calculate commission amount **/

					}
					
						LogLevel.DEBUG(5,new Throwable(),"else conditions:");
						strSqlQry= "update inverter_order_details set order_status='"+orderedstatus+"',cancelled_message='"+cusmessage+"',postpone_message='0',offbusiness_message='0',outofstock_message='0',notresponded_message='0',franchiseedenied_message='0',cancelled_regenerated_message='0',updated_date=now(),inverter_commission_amount='"+grandtotal+"' where order_id='"+chkSi+"'";
						LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
				}
			}

			int i=qm.executeUpdate(strSqlQry);
			if(i <0)
			{
				LogLevel.DEBUG(1,new Throwable(),"No Name exists u can continue ");
				session.setAttribute("priority","1");
				session.setAttribute("sesErrorMsg", "<font color='#00dd00' class='vrb10'></font> ");
				out.println("Failed to update ordered status!");
			}
			else
			{
				LogLevel.DEBUG(1,new Throwable(),"");
				session.setAttribute("priority","1");
				session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'></font> ");
				//System.out.println(email);
				out.println("Successfully Updated ordered Status as "+orderedstatus+".!");
				if(orderedstatus.equals("installed"))
				{
					String CustomerMessage;
					
					CustomerMessage="Your inverter order battery on "+inverterbrand+" "+invertermodel+" with  "+invertercapacity+" with order number "+ordernumber+" has been successfully installed by "+retailername+".";
					

					String strCustomerMessage="Dear "+consumername+",\r\n\r\n"+""+CustomerMessage+"";
					String strThanksMessage="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
					MailTrans consumer=new MailTrans();
					consumer.setStrSmtpHost(strdomainname);
					consumer.setStrFrom(FromEmailId);
					consumer.setStrTo(consumeremailid);
					LogLevel.DEBUG(5, new Throwable(), "consumeremailid :" + consumeremailid);
					consumer.setStrSubject("BookBattery Inverter Ordered Status");
					String customerMail = strCustomerMessage+"\r\n\r\n"+strThanksMessage+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "customerMail :" + customerMail);
					consumer.setStrText(customerMail);
					Thread customerMailThread=new MailThread(consumer,"");
					customerMailThread.start();

					/** jhansi started placing code to calculate commission amount **/
					String strSqlQrycalculatecom = "select CAST(round(((inverter_discount_price))-(inverter_eretailer_price))/ 2 AS SIGNED) as grandtotal FROM inverter_price_details WHERE  inverter_capacity='"+invertercapacity+"' and inverter_model='"+invertermodel+"' and city='"+city+"'";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQrycalculatecom :"+strSqlQrycalculatecom);

					Hashtable htcalculate = qm.getRow(strSqlQrycalculatecom);
					String grandtotal=String.valueOf(htcalculate.get("grandtotal"));
					LogLevel.DEBUG(5,new Throwable(),"grandtotal :"+grandtotal);
					/** jhansi ended placing code to calculate commission amount **/

					String FranchiseMessage="";
					String FranchiseemailMessage="";

					if(inverterbrand.equals("Amaron"))
					{
						if(retailername.contains("Amaron-Pitstop"))
						{
						FranchiseMessage="The ordered inverter with order number "+ordernumber+" has been successfully installed. Order Details Customer Name- "+consumername+", Customer Mobile Num- "+consumermobilenumber+", Inverter Brand- "+inverterbrand+", Inverter Model- "+invertermodel+", Inverter Capacity- "+invertercapacity+"";

						FranchiseemailMessage="The ordered inverter with order number "+ordernumber+" has been successfully installed. Order Details Customer Name- "+consumername+", Customer Mobile Num- "+consumermobilenumber+", Inverter Brand- "+inverterbrand+", Inverter Model- "+invertermodel+", Inverter Capacity- "+invertercapacity+"";

						}
						else
						{

						//FranchiseMessage="The ordered inverter with order number "+ordernumber+" has been successfully installed. Order Details Customer Name- "+consumername+", Customer Mobile Num- "+consumermobilenumber+", Inverter Brand- "+inverterbrand+", Inverter Model- "+invertermodel+", Inverter Capacity- "+invertercapacity+".Please credit commission amount Rs."+grandtotal+" in the company bank account with in 72hours.";

						FranchiseemailMessage="The ordered inverter with order number "+ordernumber+" has been successfully installed. Order Details Customer Name- "+consumername+" , Customer Mobile Num- "+consumermobilenumber+" , Inverter Brand- "+inverterbrand+" , Inverter Model- "+invertermodel+" , Inverter Capacity- "+invertercapacity+" .Please credit commission amount Rs. "+grandtotal+" in the company bank account with in 72hours.";

						FranchiseMessage="The ordered battery with order number "+ordernumber+" has been successfully installed. Please credit commission amount Rs. "+grandtotal+" in the company bank account with in 72hours";

						String strSMSResponse22 =  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,FranchiseMessage, retailermobilenumber);
						LogLevel.DEBUG(5, new Throwable(), "strSMSResponse22 :" + strSMSResponse22);


						}
					}
					else
					{

						//FranchiseMessage="The ordered inverter with order number "+ordernumber+" has been successfully installed. Order Details Customer Name- "+consumername+", Customer Mobile Num- "+consumermobilenumber+", Inverter Brand- "+inverterbrand+", Inverter Model- "+invertermodel+", Inverter Capacity- "+invertercapacity+".Please credit commission amount Rs."+grandtotal+" in the company bank account with in 72hours.";

						FranchiseemailMessage="The ordered inverter with order number "+ordernumber+" has been successfully installed. Order Details Customer Name- "+consumername+" , Customer Mobile Num- "+consumermobilenumber+" , Inverter Brand- "+inverterbrand+" , Inverter Model- "+invertermodel+" , Inverter Capacity- "+invertercapacity+" .Please credit commission amount Rs. "+grandtotal+" in the company bank account with in 72hours.";

						FranchiseMessage="The ordered battery with order number "+ordernumber+" has been successfully installed. Please credit commission amount Rs. "+grandtotal+" in the company bank account with in 72hours";

						String strSMSResponse22 =  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,FranchiseMessage, retailermobilenumber);
						LogLevel.DEBUG(5, new Throwable(), "strSMSResponse22 :" + strSMSResponse22);
					}
				

					String strFranchiseMessage="Dear "+retailername+",\r\n\r\n"+""+FranchiseemailMessage+"";
					MailTrans franchise=new MailTrans();
					franchise.setStrSmtpHost(strdomainname);
					franchise.setStrFrom(FromEmailId);
					franchise.setStrTo(retaileremail);
					LogLevel.DEBUG(5, new Throwable(), "retailermobilenumber :" + retailermobilenumber);
					String franchiseMail ="";
					String strThanksMessagedealer=""; 

					if(inverterbrand.equals("Amaron"))
					{

						if(retailername.contains("Amaron-Pitstop"))
						{
							strThanksMessagedealer="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
							franchise.setStrSubject("BookBattery Inverter Ordered Status");
							franchiseMail = strFranchiseMessage+"\r\n\r\n"+strThanksMessage+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
							LogLevel.DEBUG(5, new Throwable(), "franchiseMail :" + franchiseMail);
						}

						else
						{
							strThanksMessagedealer="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
							franchise.setStrSubject("BookBattery Inverter Ordered Status");
							franchiseMail = strFranchiseMessage+"\r\n\r\n"+strThanksMessage+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
							LogLevel.DEBUG(5, new Throwable(), "franchiseMail :" + franchiseMail);

						}
					}
					else
					{
						strThanksMessagedealer="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
						franchise.setStrSubject("BookBattery Inverter Ordered Status");
						franchiseMail = strFranchiseMessage+"\r\n\r\n"+strThanksMessage+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
						LogLevel.DEBUG(5, new Throwable(), "franchiseMail :" + franchiseMail);

					}
					franchise.setStrText(franchiseMail);
					Thread franchiseMailThread=new MailThread(franchise,"");
					franchiseMailThread.start();
					String OperatorMessage;
					
					OperatorMessage="The ordered inverter with order number "+ordernumber+" has been successfully installed. Order Details Customer Name- "+consumername+", Customer Mobile Num- "+consumermobilenumber+", Inverter Brand- "+inverterbrand+", Inverter Model- "+invertermodel+", Inverter Capacity- "+invertercapacity+", Franchisee Name- "+retailername+", Franchisee Mobile Number- "+retailermobilenumber+"";
				
					String strOperatorMessage="Dear Operator,\r\n\r\n"+""+OperatorMessage+"";
					MailTrans operator=new MailTrans();
					operator.setStrSmtpHost(strdomainname);
					operator.setStrFrom(FromEmailId);
					operator.setStrTo(strsuppemaild);
					LogLevel.DEBUG(5, new Throwable(), "strsuppemaild :" + strsuppemaild);
					operator.setStrSubject("BookBattery Inverter Ordered Status");
					String operatorMail = strOperatorMessage+"\r\n\r\n"+strThanksMessage+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "operatorMail :" + operatorMail);
					operator.setStrText(operatorMail);
					Thread operatorMailThread=new MailThread(operator,"");
					operatorMailThread.start();
				}
				if(orderedstatus.equals("cancelled-franchisee-notresponded")||orderedstatus.equals("cancelled-franchisee-modeloutofstock")||orderedstatus.equals("cancelled-franchisee-offbushrs")||orderedstatus.equals("cancelled-franchisee-denied")||orderedstatus.equals("cancelled-customer") ||orderedstatus.equals("cancelled-regenerated"))
				{
					String CustomerMessage;
					String CancelledCustomerMesg="";
					String CancelledOperatorMesg="";
					if(orderedstatus.equals("cancelled-franchisee-notresponded"))
					{
						CancelledOperatorMesg="due to franchisee has not responsed";
						CancelledCustomerMesg="due to your ordered model is out of stock";
					}
					else if(orderedstatus.equals("cancelled-franchisee-modeloutofstock"))
					{
						CancelledOperatorMesg="due to your ordered model is out of stock";
						CancelledCustomerMesg="due to your ordered model is out of stock";
					}
					else if(orderedstatus.equals("cancelled-franchisee-offbushrs"))
					{
						CancelledOperatorMesg="due to off business hours for franchisee";
						CancelledCustomerMesg="due to off business hours for franchisee";
					}
					else if(orderedstatus.equals("cancelled-franchisee-denied"))
					{
						CancelledOperatorMesg="due to our franchisee has denied";
						CancelledCustomerMesg="due to your ordered model is out of stock";
					}
					else if(orderedstatus.equals("cancelled-customer"))
					{
						CancelledOperatorMesg="by customer";
						CancelledCustomerMesg="due to "+cusmessage+"";
					}
					else if(orderedstatus.equals("cancelled-regenerated"))
					{						
						CancelledOperatorMesg="and regenerated with another model";
						CancelledCustomerMesg="and regenerated with another model";												
					}
					else
					{
					}
					CustomerMessage="Your ordered inverter on "+inverterbrand+" "+invertermodel+" with "+invertercapacity+"  with order number "+ordernumber+" has been cancelled "+CancelledCustomerMesg+".\r\n\r\n We are looking forward to see you again at www.bookbattery.com .";
				
					String strCustomerMessage="Dear "+consumername+",\r\n\r\n"+""+CustomerMessage+"";
					String strThanksMessage="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
					MailTrans consumer=new MailTrans();
					consumer.setStrSmtpHost(strdomainname);
					consumer.setStrFrom(FromEmailId);
					consumer.setStrTo(consumeremailid);
					LogLevel.DEBUG(5, new Throwable(), "consumeremailid :" + consumeremailid);
					consumer.setStrSubject("BookBattery Inverter Ordered Status");
					String customerMail = strCustomerMessage+"\r\n\r\n"+strThanksMessage+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "customerMail :" + customerMail);
					consumer.setStrText(customerMail);
					Thread customerMailThread=new MailThread(consumer,"");
					customerMailThread.start();


					String OperatorMessage;
					
						OperatorMessage="The ordered inverter with order number "+ordernumber+" has been cancelled "+CancelledOperatorMesg+". Order Details Customer Name- "+consumername+", Customer Mobile Num- "+consumermobilenumber+", Inverter Brand- "+inverterbrand+", Inverter Model- "+invertermodel+", Inverter Capacity- "+invertercapacity+", Franchisee Name- "+retailername+"";
			
					String strOperatorMessage="Dear Operator,\r\n\r\n"+""+OperatorMessage+"";
					MailTrans operator=new MailTrans();
					operator.setStrSmtpHost(strdomainname);
					operator.setStrFrom(FromEmailId);
					operator.setStrTo(strsuppemaild);
					LogLevel.DEBUG(5, new Throwable(), "strsuppemaild :" + strsuppemaild);
					operator.setStrSubject("BookBattery Inverter Ordered Status");
					String operatorMail = strOperatorMessage+"\r\n\r\n"+strThanksMessage+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "operatorMail :" + operatorMail);
					operator.setStrText(operatorMail);
					Thread operatorMailThread=new MailThread(operator,"");
					operatorMailThread.start();
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

}// end of class
