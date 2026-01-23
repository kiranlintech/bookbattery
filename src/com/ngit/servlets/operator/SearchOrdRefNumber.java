/***********************************************************************		
	Asistmi Solutions Confidential. 
	@File Name   : SearchOrdRefNumber.java 
	@Description : This Servlet is used to fetch the ord ref number details.
	@Author	     : Bharath Kumar
	@Date        : Feb 9th, 2016.
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

public class SearchOrdRefNumber extends HttpServlet 
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
	* This action is used to get order ref number.
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
	* This action is used to get new order ref number.
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
	
	}
	/* **************************************************************************************************************************************\
	* This method is to get order ref number.
	* @strSqlQry : carries the query to fetch the order ref number details in service_order_details table.
	\* **************************************************************************************************************************************/
	public String getordrefnumber(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
		String ordrefnumber= (req.getParameter("ordrefnumber")!=null)?(req.getParameter("ordrefnumber")):"";
		LogLevel.DEBUG(5, new Throwable(), "ordrefnumber :" + ordrefnumber);

		String strRes="";
		try
		{	
			ServletOutputStream out=res.getOutputStream();

			/* @strSqlQry3 : carries the query to fetch the order ref number details in service_order_details table*/
				String strSqlQry4 = "select ord_id,order_number,consumer_name,consumer_mobnumber,retailer_name,retailer_mobilnumber,city,services_type,services_package,service_price_mrp,order_status from service_order_details where order_number='"+ordrefnumber+"'";
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
						
				strRes=strRes+"<table border='0' cellspacing='1' align='center' cellpadding='2' bgcolor='#FFFFFF' width='100%'><tr bgcolor='#2364b1'><td class='prodheading' width='20%'>CustName</td><td  class='prodheading' width='15%'>servicestype</td><td class='prodheading' width='15%'>servicespackage</td><td class='prodheading' width='15%'>City</td><td class='prodheading' width='15%'>Status</td><td class='prodheading' width='20%'>Update&nbsp;Status</td></tr>";
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
						String servicestype=String.valueOf(ht3.get("services_type"));
						String servicespackage=String.valueOf(ht3.get("services_package"));
						String price=String.valueOf(ht3.get("price"));
						String orderstatus=String.valueOf(ht3.get("order_status"));

					int Jcnt=j+1;
					
					strRes=strRes+"<table bgcolor='#FFFFFF' width='100%' cellspacing='1' border='0' align='center'  cellpadding='2'><input type='checkbox' name='chkSi' value='' style='display:none'/>";
					
					strRes=strRes+"<td width='20%'   class='table1 align='left'  >"+customername+"</td>";
					strRes=strRes+"<td  width='15%'  class='table1 align='left'  >"+servicestype+"</td>";
					strRes=strRes+"<td  width='15%'  class='table1 align='left'>"+servicespackage+"</td>";
					strRes=strRes+"<td  width='15%'  class='table1 align='left'  >"+city+"</td>";
					strRes=strRes+"<td  width='15%'  class='table1 align='left'  >"+orderstatus+"</td>";
					strRes=strRes+"<td  width='20%'  class='table1 align='left'  ><select style='width:110px;' name="+ordid+" id="+ordid+" onChange=\"javascript:Updatetocancelorder('"+ordid+"','"+orderstatus+"');\"><option value=''>Select Status</option><option value='ordered'>Ordered</option><option value='confirmed'>Confirmed</option><option value='installed'>Installed</option><option value='postponed'>Postponed</option><option value='cancelled-franchisee-offbushrs'>Cancelled-Franchisee-OffBusHrs</option><option value='cancelled-franchisee-denied'>Cancelled-Franchisee-Denied</option><option value='cancelled-franchisee-notresponded'>Cancelled-Franchisee-NotResponded</option><option value='cancelled-franchisee-modeloutofstock'>Cancelled-Franchisee-ModelOutofStock</option><option value='cancelled-customer'>Cancelled-Customer</option><option value='cancelled-regenerated'>Cancelled-Regenerated</option></select></td></tr>";
					strRes=strRes+"</table>";
					strRes=strRes+"</table>";
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
	* This method is to get new order ref number.
	* @strSqlQry : carries the query to fetch the new order ref number details in service_order_details table.
	\* **************************************************************************************************************************************/
	public String getordrefnumbernew(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
		String ordrefnumber= (req.getParameter("ordrefnumber")!=null)?(req.getParameter("ordrefnumber")):"";
		LogLevel.DEBUG(5, new Throwable(), "ordrefnumber :" + ordrefnumber);

		String strRes="";
		try
		{	
			ServletOutputStream out=res.getOutputStream();

			/* @strSqlQry3 : carries the query to fetch the new order ref number details in service_order_details table*/
				String strSqlQry4 = "select ord_id,order_number,consumer_name,consumer_emailid,consumer_mobnumber,retailer_name,retailer_mobilnumber,veh_name,veh_model,services_type,services_package,service_price_mrp,service_price_discount,order_status,order_reasons,order_agent_comments,agent_name,city,area,creation_date,CAST(postpone_date AS CHAR) as postpone_date,CAST(installed_date AS CHAR) as installed_date from service_order_details where order_number='"+ordrefnumber+"'";
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
				
						
				strRes=strRes+"<table border='0' cellspacing='1' align='center' cellpadding='2' bgcolor='#FFFFFF' width='100%' class='tableizer-table'><tr bgcolor='#2364b1'><td class='prodheading' >Order Number</td><td class='prodheading'>Customer Name</td><td class='prodheading'>Customer Email ID</td><td class='prodheading'>Customer Mobile Number</td><td class='prodheading'>Retailer Name</td><td class='prodheading'>Retailer Mobile Number</td><td class='prodheading'>Vehicle Make</td><td class='prodheading'>Vehicle Model</td><td class='prodheading'>Services Type</td><td class='prodheading'>Services Package</td><td class='prodheading'>Price</td><td class='prodheading'>OTRP</td><td class='prodheading'>Order Status</td><td class='prodheading'>Order Reasons</td><td class='prodheading'>Agent Comments</td><td class='prodheading'>Agent Name</td><td class='prodheading'>City</td><td class='prodheading'>Area</td><td class='prodheading'>Ordered Date</td><td class='prodheading'>Postponed Date</td><td class='prodheading'>Installed Date</td></tr>";
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
						String veh_name=String.valueOf(ht3.get("veh_name"));
						String veh_model=String.valueOf(ht3.get("veh_model"));
						String services_type=String.valueOf(ht3.get("services_type"));
						String services_package=String.valueOf(ht3.get("services_package"));
						String service_price_mrp=String.valueOf(ht3.get("service_price_mrp"));
						String service_price_discount=String.valueOf(ht3.get("service_price_discount"));
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
					strRes=strRes+"<td class='table1 align='left'>"+veh_name+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+veh_model+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+services_type+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+services_package+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+service_price_mrp+"</td>";
					strRes=strRes+"<td class='table1 align='left'>"+service_price_discount+"</td>";
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
		}
		catch (Exception e)
		{
			LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
			e.printStackTrace();
		}
		return strRes;
	}
	
	

}// end of class