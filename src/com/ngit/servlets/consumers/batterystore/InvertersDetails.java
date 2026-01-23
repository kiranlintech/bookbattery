/***********************************************************************		
	NGIT Confidential. 
	@File Name   : InverterDetails.java 
	@Description : This Servlet is used to fetch the drop down values 
	@Author	     : Sai Krishna Daddala
	@Date        : 1th April 2014
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

  
public class InvertersDetails extends HttpServlet 
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
		* This action is used to get inverter capacity.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("getinvertercapacity"))
		{ 
			try
			{
				String strRes=getinvertercapacity(req,res,session);
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
		* This action is used to get inverter brands name.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("getinverterbrand"))
		{ 
			try
			{
				String strRes=getinverterbrand(req,res,session);
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
	* This action is used to get inverter details.
	\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("getinverterdetails"))
		{ 
			try
			{
				String strRes=getinverterdetails(req,res,session,strIpAddress,strPort,strsuppmobnumber,strsuppmobnumber1,SMSFromAddress,OperatorTeamCount);
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
				String strRes=insertconsumerorderdetails(req,res,session,domainname,strIpAddress,strPort,SMSFromAddress,FromEmailId,strsuppemaild,baseUrl,strsuppmobnumber,strCMSSERVERIP,strPDFFilePath,strPDFRelFilePath,OperatorTeamCount);
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
		* This action is used to get inverter capacity.
		\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("getinvertermodels"))
		{ 
			try
			{
				String strRes=getinvertermodels(req,res,session);
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
	* This method is to get the inverter capacity
	* @strinvertercapacity : carries the query to get the inverter capacity from inverter_details table.
	\* **************************************************************************************************************************************/
	public String getinvertercapacity(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		String selectedvalue= (req.getParameter("selectedvalue")!=null)?(req.getParameter("selectedvalue")):"";
		LogLevel.DEBUG(5,new Throwable(),"selectedvalue:"+selectedvalue );

		String inverterbrand= (req.getParameter("inverterbrand")!=null)?(req.getParameter("inverterbrand")):"";
		LogLevel.DEBUG(5,new Throwable(),"inverterbrand:"+inverterbrand );
		try
		{	
			
			String inverter_capacity="";
	 		ServletOutputStream out=res.getOutputStream();
			String strinvertercapacity = "";

			if(inverterbrand.equals("All"))
			{
				strinvertercapacity = "select distinct(inverter_capacity) from inverter_details order by inverter_capacity asc"; 
				LogLevel.DEBUG(5, new Throwable(), "strinvertercapacity :" + strinvertercapacity);
			}
			else
			{
				strinvertercapacity = "select distinct(inverter_capacity) from inverter_details where inverter_brand='"+inverterbrand+"' order by inverter_capacity asc"; 
				LogLevel.DEBUG(5, new Throwable(), "strinvertercapacity :" + strinvertercapacity);

			}
			
			Vector invertercapacityvector=qm.executeQuery(strinvertercapacity);
			LogLevel.DEBUG(5,new Throwable(),"invertercapacityvector:"+invertercapacityvector);
			if(invertercapacityvector.isEmpty())
			{ 
				strRes=strRes+"<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>Select&nbsp;Capacity</option>";
				return strRes;
			}
			else
			{
				for (int i=0; i<invertercapacityvector.size(); i++)
				{
					if(i==0)
					{
						strRes=strRes+"<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>Select&nbsp;Capacity</option>";				
					} 
					Hashtable ht=(Hashtable)invertercapacityvector.get(i);					
					inverter_capacity =String.valueOf(ht.get("inverter_capacity"));
					LogLevel.DEBUG(5,new Throwable(),"inverter_capacity:"+inverter_capacity);
					if(selectedvalue.equals(inverter_capacity))
					{
						strRes=strRes+"<option style='background:#FFF' value='"+inverter_capacity+"' selected >"+inverter_capacity+"</option>"; 
					}
					else
					{
						strRes=strRes+"<option style='background:#FFF' value='"+inverter_capacity+"' >"+inverter_capacity+"</option>"; 
					}
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
	* This method is to get the inverter brands
	* @strinverterbrand : carries the query to get the inverter brands from inverter_details table.
	\* **************************************************************************************************************************************/
	/* **************************************************************************************************************************************\
	* This method is to get the inverter brands
	* @strinverterbrand : carries the query to get the inverter brands from inverter_details table.
	\* **************************************************************************************************************************************/
	public String getinverterbrand(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String keyword= (req.getParameter("keyword")!=null)?(req.getParameter("keyword")):"";
			LogLevel.DEBUG(5, new Throwable(), "keyword :" + keyword);
		
			String strinverterbrand="";

			String inverter_brand="";
	 		ServletOutputStream out=res.getOutputStream();
			if(keyword.equals("Common"))
			{
				strinverterbrand = "select distinct(inverter_brand) from inverter_details order by inverter_brand asc"; 
				LogLevel.DEBUG(5, new Throwable(), "strinverterbrand :" + strinverterbrand);
			}
			else
			{
					strinverterbrand = "select distinct(inverter_brand) from inverter_details  where inverter_brand='"+keyword+"' order by inverter_brand asc"; 
					LogLevel.DEBUG(5, new Throwable(), "strinverterbrand :" + strinverterbrand);
				
			}
			
			Vector inverterbrandvector=qm.executeQuery(strinverterbrand);
			LogLevel.DEBUG(5,new Throwable(),"inverterbrandvector:"+inverterbrandvector);
			if(inverterbrandvector.isEmpty())
			{ 
				strRes=strRes+"<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>Select&nbsp;Make</option>";
				return strRes;
			}
			else
			{
					for (int i=0; i<inverterbrandvector.size(); i++)
					{
						if(keyword.equals("Common"))
						{
							if(i==0)
							{
								strRes=strRes+"<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>Select&nbsp;Make</option>";
								strRes=strRes+"<option style='background:#FFF' value='All'>All</option>";
							}
						}
						else
						{
								strRes=strRes+"<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>Select&nbsp;Make</option>";
						}
						Hashtable ht=(Hashtable)inverterbrandvector.get(i);
						
						inverter_brand =String.valueOf(ht.get("inverter_brand"));
						LogLevel.DEBUG(5,new Throwable(),"inverter_brand:"+inverter_brand);
						strRes=strRes+"<option style='background:#FFF' value='"+inverter_brand+"'>"+inverter_brand+"</option>"; 
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
	* This method is to fetch inverter details .
	* @strSqlQry : carries the query to select inverter details from inverter_details and inverter_price_details table.

	\* **************************************************************************************************************************************/
	public String getinverterdetails(HttpServletRequest req,HttpServletResponse res,HttpSession session,String strIpAddress,String strPort,String strsuppmobnumber,String strsuppmobnumber1,String SMSFromAddress,String OperatorTeamCount)
	{
		String strinvertertype= (req.getParameter("invertertype")!=null)?(req.getParameter("invertertype")):"";
		LogLevel.DEBUG(5,new Throwable(),"strinvertertype:"+strinvertertype );

		String invertercapacity= (req.getParameter("invertercapacity")!=null)?(req.getParameter("invertercapacity")):"";
		LogLevel.DEBUG(5,new Throwable(),"invertercapacity:"+invertercapacity );
		
		String inverterbattery= (req.getParameter("inverterbattery")!=null)?(req.getParameter("inverterbattery")):"";
		LogLevel.DEBUG(5,new Throwable(),"inverterbattery:"+inverterbattery );

		String inverterbrand= (req.getParameter("invertername")!=null)?(req.getParameter("invertername")):"";
		LogLevel.DEBUG(5,new Throwable(),"inverterbrand:"+inverterbrand );


		String state= (req.getParameter("state")!=null)?(req.getParameter("state")):"";
		LogLevel.DEBUG(5,new Throwable(),"state:"+state );

		String strcity= (req.getParameter("city")!=null)?(req.getParameter("city")):"";
		LogLevel.DEBUG(5,new Throwable(),"strcity:"+strcity );

		String area= (req.getParameter("area")!=null)?(req.getParameter("area")):"";
		LogLevel.DEBUG(5,new Throwable(),"area:"+area );
		
		String sortprice= (req.getParameter("sortprice")!=null)?(req.getParameter("sortprice")):"";
		LogLevel.DEBUG(5,new Throwable(),"sortprice:"+sortprice);

		String keyword= (req.getParameter("keyword")!=null)?(req.getParameter("keyword")):"";
		LogLevel.DEBUG(5,new Throwable(),"keyword:"+keyword);

		String backlink= (req.getParameter("backlink")!=null)?(req.getParameter("backlink")):"";
		LogLevel.DEBUG(5,new Throwable(),"backlink:"+backlink);

		String strUsermobileno= (req.getParameter("Usermobileno")!=null)?(req.getParameter("Usermobileno")):"";
		LogLevel.DEBUG(5,new Throwable(),"strUsermobileno:"+strUsermobileno);
		
		String strpincode= (req.getParameter("pincode")!=null)?(req.getParameter("pincode")):"";
		LogLevel.DEBUG(5,new Throwable(),"strpincode:"+strpincode);

		String mobileversion= (req.getParameter("mobileversion")!=null)?(req.getParameter("mobileversion")):"";
		LogLevel.DEBUG(5,new Throwable(),"mobileversion:"+mobileversion);

		String backkeyword= (req.getParameter("backkeyword")!=null)?(req.getParameter("backkeyword")):"";
		LogLevel.DEBUG(5,new Throwable(),"backkeyword:"+backkeyword);
		
		String sortpricess = "";

		String strSqlQry = "";
		String strSqlQrybat = "";
		String batterycapacity1="";
		String batteryah="";
		String batterytype="";
		String batterycapacity="";
		String agent_name="";
		String message="";

		if(inverterbattery.equals("0"))
		{

		}
		else
		{
		String[] inverterbatterya=inverterbattery.split(" ");

		batterycapacity1=inverterbatterya[0];
		LogLevel.DEBUG(5,new Throwable(),"batterycapacity1:"+batterycapacity1);

		batteryah=inverterbatterya[1];
		LogLevel.DEBUG(5,new Throwable(),"batteryah:"+batteryah);

		batterytype=inverterbatterya[2];
		LogLevel.DEBUG(5,new Throwable(),"batterytype:"+batterytype);

		batterycapacity=batterycapacity1+" "+batteryah;
		LogLevel.DEBUG(5,new Throwable(),"batterycapacity:"+batterycapacity);
		}
		Vector BatdetailsVector = new Vector();

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
			String strstate="";
			String city="";
			
			if(strpincode == "")
			{
			
				strstate=state;
				city=strcity;
			}
			else
			{
				String StrSqlQrystate1 = "select state,city from battery_pincode where pincode='"+strpincode+"'";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQrystate1 :" + StrSqlQrystate1);

				Hashtable htgetstate1 = qm.getRow(StrSqlQrystate1); 
				strstate=(String)htgetstate1.get("state");
				String strcity1=(String)htgetstate1.get("city");
				city=strcity1;
			}
			String strConditions1="";
			String strConditions2="";

			if(!inverterbrand.equals("All") )
			{
				strConditions1= " and inverter_brand='"+inverterbrand+"' ";
				strConditions2= " and bat_brand='"+inverterbrand+"' ";
			}
			else
			{	
				strConditions1= "";
				strConditions2= "";
			}
			String strConditionsDetails="";
			String strConditionsDetails1="";

			if(!inverterbrand.equals("All") )
			{
				strConditionsDetails= "where  a.inverter_brand='"+inverterbrand+"' and ";
				strConditionsDetails1= " and a.bat_brand='"+inverterbrand+"' ";

			}
			else
			{	
				strConditionsDetails= "where";
				strConditionsDetails1= "";

			}




			String strSqlQryInverterids ="select distinct(inverter_id) from inverter_details where inverter_capacity='"+invertercapacity+"' "+strConditions1+" ";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQryInverterids :" + strSqlQryInverterids);

			ArrayList invertids=new ArrayList();
			invertids=qm.getField(strSqlQryInverterids);

			String inverterIds="";
			for(int i=0;i<invertids.size();i++)
			{
				if(inverterIds.equals(""))
				inverterIds=invertids.get(i).toString();
				else
				inverterIds=inverterIds+","+invertids.get(i).toString();
			}
			LogLevel.DEBUG(5,new Throwable(),"inverterIds:"+inverterIds);

			String strInverterDetails  ="select a.inverter_brand,a.inverter_model,a.inverter_capacity,a.inverter_warranty,a.computer,a.fans,a.tubelights,a.television,a.icon_url,a.description,b.inverter_actual_price,b.inverter_discount_price from inverter_details a, inverter_price_details b  "+strConditionsDetails+" b.inverter_capacity='"+invertercapacity+"' and b.state='"+strstate+"' and b.city='"+city+"' and a.inverter_brand=b.inverter_brand and a.inverter_model=b.inverter_model and a.inverter_id in ("+inverterIds+") group by a.inverter_model order by b.inverter_discount_price "+sortpricess+"";
			LogLevel.DEBUG(5, new Throwable(), "strInverterDetails :" + strInverterDetails);

			Vector InverterdetailsVector=qm.executeQuery(strInverterDetails);
			LogLevel.DEBUG(5,new Throwable(),"InverterdetailsVector:"+InverterdetailsVector );
			if(strUsermobileno.equals("") || strUsermobileno.equals("null") || strUsermobileno.equals(null) || strUsermobileno.equals("undefined"))
			{

			}
			else if(strUsermobileno.equals("9603467559") || strUsermobileno.equals("") || strUsermobileno.equals("9999999999") || area.equals("Test Area"))
			{

				String strSqlQryvisitors = "insert into visitors_orders(vis_ord_id,bat_type,veh_make,veh_model,bat_brand,bat_capacity,state,city,area,pincode,mobile_number,option_type,keynumber,visitors_comments,creation_date)values(NULL,'"+strinvertertype+"','','','"+inverterbrand+"','"+invertercapacity+"','"+strstate+"','"+city+"','"+area+"','"+strpincode+"','"+strUsermobileno+"','inverter','','',now())";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQryvisitors: "+strSqlQryvisitors);
				int reslt = qm.executeUpdate(strSqlQryvisitors);

			}
			else
			{


				//Query to get the operator name which has been assigned last
				String StrSqlQryOperatorname1 = "select agent_name from visitors_orders where mobile_number='"+strUsermobileno+"' and (NOT agent_name = '0' or agent_name = ' ' ) and YEAR(creation_date) = YEAR(NOW()) AND MONTH(creation_date) = MONTH(NOW()) AND DAY(creation_date) = DAY(NOW()) order by creation_date limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQryOperatorname1 :" + StrSqlQryOperatorname1);

				Hashtable htgetoperatorname1 = qm.getRow(StrSqlQryOperatorname1);
				LogLevel.DEBUG(5, new Throwable(), "htgetoperatorname1 :" + htgetoperatorname1);

				if(htgetoperatorname1.isEmpty())
				{
					

						//Query to get the operator name which has been assigned last
						String StrSqlQryOperatorname = "select agent_name from visitors_orders where (NOT agent_name = '0' or agent_name = ' ' ) order by creation_date desc limit 1";
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

					String strSqlQryvisitors = "insert into visitors_orders(vis_ord_id,bat_type,veh_make,veh_model,bat_brand,bat_capacity,state,city,area,pincode,mobile_number,option_type,keynumber,visitors_comments,creation_date,agent_name)values(NULL,'"+strinvertertype+"','','','"+inverterbrand+"','"+invertercapacity+"','"+strstate+"','"+city+"','"+area+"','"+strpincode+"','"+strUsermobileno+"','inverter','','',now(),'"+agent_name+"')";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQryvisitors: "+strSqlQryvisitors);
					int reslt = qm.executeUpdate(strSqlQryvisitors);
					
					SendSMS sendsms = new SendSMS(qm);
					message = "User with Mobile Num: "+strUsermobileno+" has selected  Inverter Brand: "+inverterbrand+" with Capacity: "+invertercapacity+" from "+city+"";			
				}
				else
				{
					agent_name=(String)htgetoperatorname1.get("agent_name");
					LogLevel.DEBUG(5, new Throwable(), "agent_name :" + agent_name);

					String strSqlQryvisitors = "insert into visitors_orders(vis_ord_id,bat_type,veh_make,veh_model,bat_brand,bat_capacity,state,city,area,pincode,mobile_number,option_type,keynumber,visitors_comments,creation_date,agent_name)values(NULL,'"+strinvertertype+"','','','"+inverterbrand+"','"+invertercapacity+"','"+strstate+"','"+city+"','"+area+"','"+strpincode+"','"+strUsermobileno+"','inverter','','',now(),'"+agent_name+"')";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQryvisitors: "+strSqlQryvisitors);
					int reslt = qm.executeUpdate(strSqlQryvisitors);

				}
			
			
			/** code added by jhansi to fetch inverter battery **/
				if(!inverterbattery.equals("0"))
				{

					if(strUsermobileno.equals("9603467559") || strUsermobileno.equals(" ") || strUsermobileno.equals("9999999999"))
					{

						String strSqlQryvisit = "insert into visitors_orders(vis_ord_id,bat_type,veh_make,veh_model,bat_brand,bat_capacity,state,city,area,pincode,mobile_number,option_type,keynumber,visitors_comments,creation_date)values(NULL,'Inverter Batteries','','','"+inverterbrand+"','"+batterycapacity+"','"+strstate+"','"+city+"','"+area+"','"+strpincode+"','"+strUsermobileno+"','order','','',now())";
						LogLevel.DEBUG(5,new Throwable(),"strSqlQryvisit: "+strSqlQryvisit);
						int reslt1 = qm.executeUpdate(strSqlQryvisit);

					}
					else
					{
						//agent_name=(String)htgetoperatorname1.get("agent_name");
						//LogLevel.DEBUG(5, new Throwable(), "agent_name :" + agent_name);

						String strSqlQryvisit = "insert into visitors_orders(vis_ord_id,bat_type,veh_make,veh_model,bat_brand,bat_capacity,state,city,area,pincode,mobile_number,option_type,keynumber,visitors_comments,creation_date,agent_name)values(NULL,'Inverter Batteries','','','"+inverterbrand+"','"+batterycapacity+"','"+strstate+"','"+city+"','"+area+"','"+strpincode+"','"+strUsermobileno+"','order','','',now(),'"+agent_name+"')";
						LogLevel.DEBUG(5,new Throwable(),"strSqlQryvisit: "+strSqlQryvisit);
						int reslt1 = qm.executeUpdate(strSqlQryvisit);
					}

					if(batterytype.equals("Current"))
					{
						strSqlQrybat ="select distinct(bat_id) from application_chat_mapping where bat_capacity='"+batterycapacity+"'  and battery_type_flag='Flat Plate Battery' "+strConditions2+" ";
						LogLevel.DEBUG(5, new Throwable(), "strSqlQrybat :" + strSqlQrybat);
					}
					else if(batterytype.equals("Tubular"))
					{
						strSqlQrybat ="select distinct(bat_id) from application_chat_mapping where  bat_capacity='"+batterycapacity+"' and battery_type_flag='Tubular Battery' "+strConditions2+" ";
						LogLevel.DEBUG(5, new Throwable(), "strSqlQrybat :" + strSqlQrybat);
					}
					else if(batterytype.equals("Tall"))
					{
						strSqlQrybat ="select distinct(bat_id) from application_chat_mapping where  bat_capacity='"+batterycapacity+"' and battery_type_flag='Tall Tubular Battery' "+strConditions2+" ";
						LogLevel.DEBUG(5, new Throwable(), "strSqlQrybat :" + strSqlQrybat);
					}
					else
					{

					}

					ArrayList htav=new ArrayList();
					htav=qm.getField(strSqlQrybat);

					String batids="";
					for(int i=0;i<htav.size();i++)
					{
					if(batids.equals(""))
					batids=htav.get(i).toString();
					else
					batids=batids+","+htav.get(i).toString();
					}
					LogLevel.DEBUG(5,new Throwable(),"batids:"+batids);

			if(batterytype.equals("Current"))
			{
				strSqlQry ="select a.bat_brand,a.bat_model,a.veh_model,a.bat_capacity,a.bat_warranty,a.icon_url,a.description,b.bat_act_price,b.bat_witbat_price,b.bat_ret_price,a.battery_type_flag from application_chat_mapping a, batteryprice b where a.battery_type_flag='Flat Plate Battery' and a.bat_capacity='"+batterycapacity+"' "+strConditionsDetails1+" and b.state='"+strstate+"' and b.city='"+city+"' and a.bat_model=b.bat_model and a.bat_id in ("+batids+") order by CAST(b.bat_witbat_price AS SIGNED) ";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
				
				message = "User selected  Inverter Batteries >> "+batterycapacity+" >> "+city+" for order Battery. User Mob No: "+strUsermobileno+""; 
			}
			else if(batterytype.equals("Tubular"))
			{
				strSqlQry ="select a.bat_brand,a.bat_model,a.veh_model,a.bat_capacity,a.bat_warranty,a.icon_url,a.description,b.bat_act_price,b.bat_witbat_price,b.bat_ret_price,a.battery_type_flag from application_chat_mapping a, batteryprice b where a.battery_type_flag='Tubular Battery' and a.bat_capacity='"+batterycapacity+"' "+strConditionsDetails1+" and b.state='"+strstate+"' and b.city='"+city+"' and a.bat_model=b.bat_model and a.bat_id in ("+batids+") order by CAST(b.bat_witbat_price AS SIGNED) ";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
				
				message = "User selected  Inverter Batteries >> "+batterycapacity+" >> "+city+" for order Battery. User Mob No: "+strUsermobileno+""; 
			}
			else if(batterytype.equals("Tall"))
			{
				strSqlQry ="select a.bat_brand,a.bat_model,a.veh_model,a.bat_capacity,a.bat_warranty,a.icon_url,a.description,b.bat_act_price,b.bat_witbat_price,b.bat_ret_price,a.battery_type_flag from application_chat_mapping a, batteryprice b where a.battery_type_flag='Tall Tubular Battery' and a.bat_capacity='"+batterycapacity+"' "+strConditionsDetails1+" and b.state='"+strstate+"' and b.city='"+city+"' and a.bat_model=b.bat_model and a.bat_id in ("+batids+") order by CAST(b.bat_witbat_price AS SIGNED) ";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
				
				message = "User selected  Inverter Batteries >> "+batterycapacity+" >> "+city+" for order Battery. User Mob No: "+strUsermobileno+""; 
			}
			else
					{


					}

					BatdetailsVector=qm.executeQuery(strSqlQry);
					LogLevel.DEBUG(5,new Throwable(),"BatdetailsVector:"+BatdetailsVector );
			
				}

			}

				/** code Ended by jhansi to fetch inverter battery **/
			if(InverterdetailsVector.isEmpty())
			{ 
				if(mobileversion.equals("mobile"))
			    {
					LogLevel.DEBUG(1,new Throwable(),"Failed to fetch Inverter details ");
					session.setAttribute("priority","1");
					session.setAttribute("sesbatterydetailsErrorMsg", "<font color='#ff3333' class='vrb10'>No Inverter details found based on selection.! </font> ");
					session.removeAttribute("InverterdetailsVector");
					session.removeAttribute("BatdetailsVector");
					res.sendRedirect("/bookbattery/mobile/mobinverter.jsp");
					return strRes;
				}
				else
				{
					LogLevel.DEBUG(1,new Throwable(),"Failed to fetch Inverter details ");
					session.setAttribute("priority","1");
					session.setAttribute("sesbatterydetailsErrorMsg", "<font color='#ff3333' class='vrb10'>No Inverter details found based on selection.! </font> ");
					session.removeAttribute("InverterdetailsVector");
					session.removeAttribute("BatdetailsVector");
					res.sendRedirect("/bookbattery/"+backkeyword+".jsp");
					return strRes;
				}
			}
			else
			{	
				if(mobileversion.equals("mobile"))
			    {
					LogLevel.DEBUG(1, new Throwable(),"Successfully Fetched Inverter Details");
					session.setAttribute("InverterdetailsVector", InverterdetailsVector);
					session.setAttribute("InverterBatterydetailsVector", BatdetailsVector);
					session.setAttribute("RefreshKeyword", "refresh");
					res.sendRedirect("/bookbattery/mobile/mobinverterdetails.jsp?invertertype="+strinvertertype+"&inverterbrand="+inverterbrand+"&invertercapacity="+invertercapacity+"&city="+strcity+"&pincity="+city+"&strarea="+area+"&strstate="+strstate+"&strpincode="+strpincode+"&sortpricess="+sortpricess+"&chatmobilenumber="+strUsermobileno+"&agentname="+agent_name);
					return strRes;
				}
				else
				{
					LogLevel.DEBUG(1, new Throwable(),"Successfully Fetched Inverter Details");
					session.setAttribute("InverterdetailsVector", InverterdetailsVector);
					session.setAttribute("InverterBatterydetailsVector", BatdetailsVector);
					session.setAttribute("RefreshKeyword", "refresh");			
					res.sendRedirect("/bookbattery/inverterdetails.jsp?invertertype="+strinvertertype+"&inverterbrand="+inverterbrand+"&invertercapacity="+invertercapacity+"&city="+strcity+"&pincity="+city+"&strarea="+area+"&strstate="+strstate+"&strpincode="+strpincode+"&backkeyword="+backkeyword+"&sortpricess="+sortpricess+"&chatmobilenumber="+strUsermobileno+"&agentname="+agent_name);
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
* This method is used to send  verification to user email id and mobile number when user enters mobile number in order now inverter 
\* *************************************************************************************************************/
public String sentverificationcode(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort,String SMSFromAddress, String FromEmailId) 
{ 	
	String strUsermobileno = (req.getParameter("strUsermobileno")!=null)?(req.getParameter("strUsermobileno")):"0";
		String inverterbrand = (req.getParameter("inverterbrand")!=null)?(req.getParameter("inverterbrand")):"0";

	String invertermodel = (req.getParameter("invertermodel")!=null)?(req.getParameter("invertermodel")):"0";
	String price = (req.getParameter("price")!=null)?(req.getParameter("price")):"0";
	String discountprice = (req.getParameter("discountprice")!=null)?(req.getParameter("discountprice")):"0";
	LogLevel.DEBUG(5, new Throwable(), "price :" + price);
	String mobileversion = (req.getParameter("mobileversion")!=null)?(req.getParameter("mobileversion")):"0";

	//LogLevel.DEBUG(5, new Throwable(), "price :" + price);


	String batterymodel = (req.getParameter("batterymodel")!=null)?(req.getParameter("batterymodel")):"0";
	LogLevel.DEBUG(5, new Throwable(), "batterymodel :" + batterymodel);

	String batteryprice = (req.getParameter("batteryprice")!=null)?(req.getParameter("batteryprice")):"0";
	LogLevel.DEBUG(5, new Throwable(), "batteryprice :" + batteryprice);

	String withbatprice = (req.getParameter("withbatprice")!=null)?(req.getParameter("withbatprice")):"0";
	LogLevel.DEBUG(5, new Throwable(), "withbatprice :" + withbatprice);

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
		
		
		strRes=strRes+"<table width='100%' bgcolor='#FFFFFF' valign='top'><tr height='2'></tr><tr><table width='100%' bgcolor='#FFFFFF' valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: center;'> BookBattery</td><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: left;' onclick=\"javascript:closemobdiv(greyout(false));\"> X</td></tr></table><hr style='background: white; width: 95%;margin-top: 1px;margin-bottom: 1px;'></tr><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF'><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontentmob' align='center'><font size='4' color='#FFFFFF' style='padding: 2px;'>Please Enter Verification code received on SMS</font></td></tr></table></td><tr height='10'><td colspan='3' align='center' class='subheading' id='codeerrormsg'></td></tr><tr><td height='10'></td></tr></tr><tr><td width='100%' align='center'><input type='hidden' name='passcode' value='"+verificationcode+"'><input class='insidecontent' type='tel' autocomplete='off' name='verifycode' id='verifycode' placeholder='123456' style='width:250px;height:40px;border: 2px solid #CCC;font-size: 13px;' maxlength='6' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:checkverification('"+strUsermobileno+"','"+verificationcode+"','"+inverterbrand+"','"+invertermodel+"','"+price+"','"+discountprice+"','"+batterymodel+"','"+batteryprice+"','"+withbatprice+"');return false;} else return true;\"/></td></tr><tr><td align='center' class='popupmobilehints'><font color='#FF3333'>*</font>If you did not get verification code <br> please call <font color='#FF8C00'>+91 9603467559.</font></td></tr><tr><td height='0'></td></tr><tr><td  width='50%' align='center' ><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn' class='batterywalesubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:checkverification('"+strUsermobileno+"','"+verificationcode+"','"+inverterbrand+"','"+invertermodel+"','"+price+"','"+discountprice+"','"+batterymodel+"','"+batteryprice+"','"+withbatprice+"');\");\"><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  value='Close' align='left' onclick=\"javascript:closemobdiv(greyout(false));\"> </td></tr><tr><td height='15'></td></tr></table></table>";
		
		}
		else
		{
	
		
		
		strRes=strRes+"<div class='col-md-4 col-md-offset-4'> <table cellspacing='10' cellpadding='0' bgcolor='#FFFFFF'><tr height='10'><table width='100%' bgcolor='#FFFFFF' valign='top'></table><table  width='100%' bgcolor='#FFFFFF' valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 25px;font-weight: 600;text-align: center;'> BookBattery</td> <td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: left;'  href=\"javascript:closemobdiv(greyout(false));\" > <a style='color: #F96F2B;' href=\"javascript:closemobdiv(greyout(false));\"> X </a> </td></tr></table><hr style='background: white; width: 90%;margin-top: 1px;margin-bottom: 1px;'></tr><tr><td><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font style='font-size: 15px;color: #ffffff;'>Please Enter Verification code received on SMS</font></td></tr></table></td></tr><tr><td height='10'></td></tr> <table cellspacing='10' cellpadding='0'  width='100%' height='10'> <tr><td width='100%' align='center'><input type='hidden' name='passcode' value='"+verificationcode+"'><input class='insidecontent' type='tel' autocomplete='off' name='verifycode' id='verifycode' placeholder='123456' style='width:70%;height:38px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 0px 0px 0px 0px;' maxlength='6' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:checkverification('"+strUsermobileno+"','"+verificationcode+"','"+inverterbrand+"','"+invertermodel+"','"+price+"','"+discountprice+"','"+batterymodel+"','"+batteryprice+"','"+withbatprice+"');return false;} else return true;\"/></td></tr> </table> <table cellspacing='10' cellpadding='0'  width='100%' height='10' > <tr><td align='center' style='font-family:Verdana;font-size:12px;color:#FFFFFF; font-weight:bold;text-decoration:none;padding:1px 1px;'> <font color='#FF3333'>*</font>If you did not get verification code <br> please call <font color='#FF8C00'>+91 9603467559.</font></td></tr> </table> <tr><td height='10'></td></tr>  <table cellspacing='10' cellpadding='0'  width='100%' height='10' >  <tr><td  width='50%' align='right' ><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:checkverification('"+strUsermobileno+"','"+verificationcode+"','"+inverterbrand+"','"+invertermodel+"','"+price+"','"+discountprice+"','"+batterymodel+"','"+batteryprice+"','"+withbatprice+"');\");\"> </td> <td><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  value='Close' align='left' onclick=\"javascript:closemobdiv(greyout(false));\" ></td></tr> </table> <table cellspacing='10' cellpadding='0'  width='100%' height='10' >  <tr height='26'><td colspan='3' align='center' class='subheading' id='codeerrormsg'></td></tr> </table ><tr><td height='15'></td></tr></table></div>"; 
		
		
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
public String insertconsumerorderdetails(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort,String SMSFromAddress, String FromEmailId, String strsuppemaild, String baseUrl, String strsuppmobnumber,String strCMSSERVERIP,String strPDFFilePath,String strPDFRelFilePath,String OperatorTeamCount) 
{ 	
	String strUsermobileno = (req.getParameter("mobilenumber")!=null)?(req.getParameter("mobilenumber")):"";
	String strusername= (req.getParameter("username")!=null)?(req.getParameter("username")):"";
	String emailid= (req.getParameter("emailid")!=null)?(req.getParameter("emailid")):"";
	String addrs1= (req.getParameter("addrs1")!=null)?(req.getParameter("addrs1")):""; 
	LogLevel.DEBUG(5, new Throwable(), "addrs1 :" + addrs1);
	String addrs2= (req.getParameter("addrs2")!=null)?(req.getParameter("addrs2")):"";
	LogLevel.DEBUG(5, new Throwable(), "addrs2 :" + addrs2);

	String userarea= (req.getParameter("userarea")!=null)?(req.getParameter("userarea")):"";
	String usercity= (req.getParameter("usercity")!=null)?(req.getParameter("usercity")):"";
	String userzipcode= (req.getParameter("userzipcode")!=null)?(req.getParameter("userzipcode")):"";
	String invertermodel = (req.getParameter("invertermodel")!=null)?(req.getParameter("invertermodel")):"";
	String price = (req.getParameter("price")!=null)?(req.getParameter("price")):"";
	String discountprice = (req.getParameter("discountprice")!=null)?(req.getParameter("discountprice")):"";
	String verifycode = (req.getParameter("verifycode")!=null)?(req.getParameter("verifycode")):"";
	String inverterbrand = (req.getParameter("inverterbrand")!=null)?(req.getParameter("inverterbrand")):"";
	String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
	String pincity = (req.getParameter("pincity")!=null)?(req.getParameter("pincity")):"";
	String strarea = (req.getParameter("strarea")!=null)?(req.getParameter("strarea")):"";
	String strstate3 = (req.getParameter("strstate")!=null)?(req.getParameter("strstate")):"";
	LogLevel.DEBUG(5, new Throwable(), "strstate3 :" + strstate3);
	String strpincode = (req.getParameter("strpincode")!=null)?(req.getParameter("strpincode")):"";
	String invertertype = (req.getParameter("invertertype")!=null)?(req.getParameter("invertertype")):"";
	String invertercapacity = (req.getParameter("invertercapacity")!=null)?(req.getParameter("invertercapacity")):"";
	String mobileversion = (req.getParameter("mobileversion")!=null)?(req.getParameter("mobileversion")):"0";

	String batterymodel = (req.getParameter("batterymodel")!=null)?(req.getParameter("batterymodel")):"";
	LogLevel.DEBUG(5, new Throwable(), "batterymodel :" + batterymodel);

	String batteryprice = (req.getParameter("batteryprice")!=null)?(req.getParameter("batteryprice")):"";
	LogLevel.DEBUG(5, new Throwable(), "batteryprice :" + batteryprice);

	String withbatprice = (req.getParameter("withbatprice")!=null)?(req.getParameter("withbatprice")):"0";
	LogLevel.DEBUG(5, new Throwable(), "withbatprice :" + withbatprice);

	String agent_name = (req.getParameter("agentname")!=null)?(req.getParameter("agentname")):"0";
	LogLevel.DEBUG(5, new Throwable(), "agent_name :" + agent_name);

	String strRes="";
	String strstate="";
	String strstate1="";
	String StrSqlQry ="";
	String Strretid="";
	String Strlocorpin="";
	String strcustax="";
	String strcusper="";
	Hashtable htretailerid =new Hashtable();
	try
	{		
			ServletOutputStream out=res.getOutputStream();

			if(city.equals("0") || city.equals("") || city == "")
			{
				String strstate2 = strstate3;
				strstate=strstate2.trim().replaceAll(" ", "_"); 
				LogLevel.DEBUG(5, new Throwable(), "strstate :" + strstate);

			Date now = new Date();
			SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely
			//System.out.println(simpleDateformat.format(now));
			LogLevel.DEBUG(5, new Throwable(), "Day Info :" + simpleDateformat.format(now));
			String day =simpleDateformat.format(now);
			LogLevel.DEBUG(5, new Throwable(), "Day Info String:" + day);
			
			if(day.equals("Sunday"))
			{
				String StrSqlQry13 = "select retailer_id from retailer_location_mapping where pincode='"+strpincode+"' and bat_brand='"+inverterbrand+"' and weekend_dealer_flag='Yes' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry13 :" + StrSqlQry13);
				Hashtable htretailerid12 = qm.getRow(StrSqlQry13); 
			if(htretailerid12.isEmpty())
			{
				StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+strpincode+"' and bat_brand='"+inverterbrand+"' and weekend_dealer_flag='No' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);

			}
			else
				{
					StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+strpincode+"' and bat_brand='"+inverterbrand+"' and weekend_dealer_flag='Yes' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);

				}
			}
			else
			{
				StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+strpincode+"' and bat_brand='"+inverterbrand+"' and weekend_dealer_flag='No' limit 1";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
			}

				htretailerid = qm.getRow(StrSqlQry); 
				Strretid=String.valueOf(htretailerid.get("retailer_id"));
				LogLevel.DEBUG(5, new Throwable(), "Strretid :" + Strretid);
				Strlocorpin = strpincode;

			}
			else
			{
				String StrSqlQrystate = "select state from search_whereever_keywords where city='"+city+"'";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQrystate :" + StrSqlQrystate); 

				Hashtable htgetstate = qm.getRow(StrSqlQrystate); 
				strstate1=(String)htgetstate.get("state");

				strstate=strstate1.trim().replaceAll(" ", "_"); 
				LogLevel.DEBUG(5, new Throwable(), "strstate :" + strstate);
				
				Date now = new Date();
			SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely
			//System.out.println(simpleDateformat.format(now));
			LogLevel.DEBUG(5, new Throwable(), "Day Info :" + simpleDateformat.format(now));
			String day =simpleDateformat.format(now);
			LogLevel.DEBUG(5, new Throwable(), "Day Info String:" + day);
			
			if(day.equals("Sunday"))
			{

				String StrSqlQry12 = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+city+"' and area='"+strarea+"' and bat_brand='"+inverterbrand+"' and weekend_dealer_flag='Yes' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry12 :" + StrSqlQry12);
				Hashtable htretailerid1 = qm.getRow(StrSqlQry12); 
				if(htretailerid1.isEmpty())
				{
					StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+city+"' and area='"+strarea+"' and bat_brand='"+inverterbrand+"' and weekend_dealer_flag='No' limit 1";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);

				}
				else
				{

					StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+city+"' and area='"+strarea+"' and bat_brand='"+inverterbrand+"' and weekend_dealer_flag='Yes' limit 1";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
				}
			}
			else
			{
				StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+city+"' and area='"+strarea+"' and bat_brand='"+inverterbrand+"' and weekend_dealer_flag='No' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
			}

				htretailerid = qm.getRow(StrSqlQry); 
				Strretid=String.valueOf(htretailerid.get("retailer_id"));
				LogLevel.DEBUG(5, new Throwable(), "Strretid :" + Strretid);
				Strlocorpin = city;
			}
			LogLevel.DEBUG(5, new Throwable(), "strstate :" + strstate);
			if(Strretid.equals(null) || Strretid.equals("null") || Strretid == null || Strretid == "null" || Strretid =="")
			{
				if(mobileversion.equals("mobile"))
				{
				//strRes="<table border='0' width='100%' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td align='left'>&nbsp;<font color='#562d82'>BookBattery</td><td align='right'><a href='javascript:divorderdetails(greyout(false));'><img src=\"/bookbattery/images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='100%' height='2px' bgcolor='#FFFFFF' valign'top'><tr class='pages' bgcolor='#FFFFFF'><td align='center' bgcolor='#FFFFFF'>No Retailers Found on Selected City.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closemobdivindex();' class='button4'><br></td></tr><tr height='15'></tr></table>";//
				
				//strRes="<table border='0' width='100%' height='10px' cellpadding='0' cellspacing='0'><tr><table width='100%' bgcolor='#FFFFFF' valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: center;'> BookBattery</td><td width='100%' align='center' style='COLOR:#F96F2B;     padding-right: 10px; font-size: 24px;font-weight: 600;text-align: left;' href='javascript:closemobdivindex(greyout(false));'> X</td></tr></table><hr style='background: white; width: 95%;margin-top: 1px;margin-bottom: 1px;'></tr></table><table border='0' width='100%' height='2px' valign'top'><tr ><td align='justify' style='padding: 10px;font-size:14px;color:#ffffff;'>No Retailers Found on Selected City </td></tr><tr height='10'><td></td></tr><tr height='10'><td align='center'><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  value='Ok' align='left' onclick='closemobdivindex();'><br></td></tr><tr><td height='10'></td></tr></table>";
				
				strRes="<table border='0' width='100%' height='10px' cellpadding='0' cellspacing='0'><tr><table width='100%' bgcolor='#FFFFFF' valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: center;'> BookBattery</td><td width='100%' align='center' style='COLOR:#F96F2B;     padding-right: 10px; font-size: 24px;font-weight: 600;text-align: left;' onclick='closemobdivindex();'> X</td></tr></table><hr style='background: white; width: 95%;margin-top: 1px;margin-bottom: 1px;'></tr></table><table border='0' width='100%' height='2px' valign'top'><tr ><td align='justify' style='padding: 10px;font-size:14px;color:#ffffff;'>No Retailers Found on Selected City.</td></tr><tr height='10'><td></td></tr><tr height='10'><td align='center'><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  value='Ok' align='left' onclick='closemobdivindex();'><br></td></tr><tr><td height='10'></td></tr></table>"; 
				
				}
				else
				{
				
				//strRes="<table border='0' width='100%' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td align='left'>&nbsp;<font color='#562d82'>BookBattery</td><td align='right'><a href='javascript:closemobdivindex(greyout(false));'><img src=\"/bookbattery/images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='100%' height='2px' bgcolor='#FFFFFF' valign'top'><tr class='pages' bgcolor='#FFFFFF'><td align='center' bgcolor='#FFFFFF'>No Retailers Found on Selected City.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closemobdivindex();' class='button4'><br></td></tr><tr height='15'></tr></table>";
				
				strRes="<div class='col-md-4 col-md-offset-4'>  <table border='0' width='300px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'>  <tr height='10'><table width='100%'  valign='top'></table><table  width='100%'  valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 25px;font-weight: 600;text-align: center;'> BookBattery</td> <td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: left;'  href=\"javascript:closemobdiv(greyout(false));\" > <a style='color: #F96F2B;' href=\"javascript:closemobdivindex();\"> X </a> </td></tr></table><hr style='background: white; width: 90%;margin-top: 1px;margin-bottom: 1px;'></tr> </table><table border='0' style='margin-top: 10px;' width='100%' height='2px'  valign'top'><tr ><td align='justify'  style='font-family:Verdana;font-size:14px;color:#FFFFFF;padding: 16px;'>No Retailers Found on Selected City.</td></tr><tr height='10'><td></td></tr><tr height='10'><td align='center'> <input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn' name='Submitrret' value='OK' disable='false' onclick='closemobdivindex();' > <br></td></tr><tr><td height='10'></td></tr></table> </div>";
				
				}
			}
			else
			{
			String StrSqlQrydet = "select retailer_name,mobile_number,email_id,address1,mobile_numberother,invoice_flag from "+strstate+"_retailers where retailer_id='"+Strretid+"'";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQrydet :" + StrSqlQrydet);

			Hashtable htretailerdetls = qm.getRow(StrSqlQrydet); 
			String strretmobnum=String.valueOf(htretailerdetls.get("mobile_number"));
			String strretname=(String)htretailerdetls.get("retailer_name");
			String strretemail=(String)htretailerdetls.get("email_id");
			String straddress1=(String)htretailerdetls.get("address1");
			String strretothermobnum=String.valueOf(htretailerdetls.get("mobile_numberother"));
			String strinvoiceflag=String.valueOf(htretailerdetls.get("invoice_flag"));


			/*following code is for generating the random number */
			GregorianCalendar date = new GregorianCalendar();
			int millseconds = date.get(Calendar.MILLISECOND);
			LogLevel.DEBUG(5, new Throwable(), "millseconds :" + millseconds);

			String milli = Integer.toString(millseconds);
			LogLevel.DEBUG(5, new Throwable(), "milli :" + milli);
			char milliseond = milli.charAt(0);
			LogLevel.DEBUG(5, new Throwable(), "milliseond :" + milliseond);
			
			Random r = new Random( System.currentTimeMillis() );
			LogLevel.DEBUG(5, new Throwable(), "r :" + r);
			int num = ((milliseond + r.nextInt(2)) * 10000 + r.nextInt(10000));
			//LogLevel.DEBUG(5, new Throwable(), "formula :" + r.nextInt(2) * 100000 + r.nextInt(100000));
			LogLevel.DEBUG(5, new Throwable(), "num :" + num);
			

			String verificationcode = "";
			String verificationcode1 = Integer.toString(num);
			verificationcode ="ORD"+verificationcode1+"";
			LogLevel.DEBUG(5, new Throwable(), "verificationcode :" + verificationcode);
			
			/*following code is for generating the random number */
			String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";

			String pw = "";
				for (int i=0; i<PASSWORD_LENGTH; i++)
				{
				int index = (int)(RANDOM.nextDouble()*letters.length());
				pw += letters.substring(index, index+1);
				}
			LogLevel.DEBUG(5, new Throwable(), "pw :" + pw);
			String ThankUSMSMsg = "";
			String ThankUMsg="";
			/*code to generate the random number ends here */
			/* code to send SMS and Email retailers details to consumer */ 
			CompareTrans ct = new CompareTrans(qm);
			SendSMS sendsms = new SendSMS(qm);

				/*if(inverterbrand.equals("Amaron"))
				{

					ThankUMsg="Your BookBattery Inverter Ord Ref No: "+verificationcode+" Inverter Model: "+invertermodel+" Inverter Capacity: "+invertercapacity+" Price: Rs "+price+" Discount Price: Rs. "+discountprice+" "+strretname+" with Mob No-"+strretmobnum+" and address "+straddress1+" will fullfill your order. Please call +91-9603467559 to avail additional 1% cash back.";
				}
				else
				{
					ThankUMsg="Your BookBattery Inverter Ord Ref No: "+verificationcode+" Inverter Model: "+invertermodel+" Inverter Capacity: "+invertercapacity+" Price: Rs "+price+" Discount Price: Rs. "+discountprice+" "+strretname+" with Mob No-"+strretmobnum+" and address "+straddress1+" will fullfill your order. Please call +91-9603467559 to avail additional 1% cash back.";

				}*/

			ThankUMsg="Your BookBattery Inverter Ord Ref No: "+verificationcode+" Inverter Model: "+invertermodel+" Inverter Capacity: "+invertercapacity+" Price: Rs "+price+" Discount Price: Rs. "+discountprice+" "+strretname+" with Mob No-"+strretmobnum+" and address "+straddress1+" will fullfill your order.For any kind of Assistance please call to 9603467559. ";

			

			ThankUSMSMsg ="Thanks for choosing bookbattery.com .Your Inverter Ord Ref No: "+verificationcode+" for Inverter Model: "+invertermodel+" Price: Rs. "+price+" Discount Price: Rs. "+discountprice+" You will shortly receive a call.For any kind of Assistance please call to 9603467559. ";
			
			String strSMSResponse=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUSMSMsg, strUsermobileno);
			LogLevel.DEBUG(5, new Throwable(), "strSMSResponse :" + strSMSResponse);

			String strsub="Dear "+strusername+",\r\n\r\n"+""+ThankUMsg+"";
			String strThanks="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
			MailTrans sendverifycode=new MailTrans();
			sendverifycode.setStrSmtpHost(strdomainname);
			sendverifycode.setStrFrom(FromEmailId);
			sendverifycode.setStrTo(emailid);
			sendverifycode.setStrSubject("BookBattery Inverter Details.");
			String activateLink = strsub+"\r\n\r\n"+strThanks+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
			LogLevel.DEBUG(5, new Throwable(), "activateLink :" + activateLink);
			sendverifycode.setStrText(activateLink);
			Thread mt=new MailThread(sendverifycode,"");
			mt.start();

			/* code to send SMS and Email consumer details to retailers */ 
			SendSMS sendsms1 = new SendSMS(qm);
			String ThankUMsg1="";
			String activateLink1 ="";
			String strThanks1="";
			String CustomerAddress="";

			if(addrs1.equals("") && addrs2.equals(""))
			{
				CustomerAddress="";

			}
			else
			{
				CustomerAddress="Address: "+addrs1+","+addrs2+"";

			}
			if(inverterbrand.equals("Amaron"))
				{
					if(strretname.contains("Amaron-Pitstop"))
					{
						ThankUMsg1="BookBattery Consumer "+strusername+" with Mob no-"+strUsermobileno+" "+CustomerAddress+" and Order Ref Number "+verificationcode+" ordered for "+inverterbrand+" >> "+invertermodel+" >> "+invertercapacity+" at price: Rs "+price+" Discount Price: Rs "+discountprice+". Please deliver it.For any kind of Assistance please call to 9603467559. ";
					}
					else
					{
					ThankUMsg1="BookBattery Consumer "+strusername+" with Mob no-"+strUsermobileno+" "+CustomerAddress+" and Order Ref Number "+verificationcode+" ordered for "+inverterbrand+" >> "+invertermodel+" >> "+invertercapacity+" at price: Rs "+price+" Discount Price: Rs "+discountprice+". Please deliver it.For any kind of Assistance please call to 9603467559. ";

					}
				}
				else
				{
					ThankUMsg1="BookBattery Consumer "+strusername+" with Mob no-"+strUsermobileno+" "+CustomerAddress+" and Order Ref Number "+verificationcode+" ordered for "+inverterbrand+" >> "+invertermodel+" >> "+invertercapacity+" at price: Rs "+price+" Discount Price: Rs "+discountprice+". Please deliver it.For any kind of Assistance please call to 9603467559. ";


				}
			LogLevel.DEBUG(5, new Throwable(), "ThankUMsg1 :" + ThankUMsg1);

			String ThanksmsMsg=""+strusername+" Mob No: "+strUsermobileno+" and Ord Ref No: "+verificationcode+" ordered "+inverterbrand+" >> "+invertermodel+" >> "+invertercapacity+" at price: Rs "+price+" Discount Price:  Rs "+discountprice+" Please install";

			String strSMSResponse1=  sendsms1.sendSMS(strIpAddress,strPort,SMSFromAddress,ThanksmsMsg, strretmobnum);
			LogLevel.DEBUG(5, new Throwable(), "strSMSResponse1 :" + strSMSResponse1);

			String strsub1="Dear "+strretname+",\r\n\r\n"+""+ThankUMsg1+"";
			
			MailTrans retdetails=new MailTrans();
			retdetails.setStrSmtpHost(strdomainname);
			retdetails.setStrFrom(FromEmailId);
			retdetails.setStrTo(strretemail);
			if(inverterbrand.equals("Amaron"))
				{
					strThanks1="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
					retdetails.setStrSubject("BookBattery Inverter Details.");
					activateLink1 = strsub1+"\r\n\r\n"+strThanks1+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "activateLink1 :" + activateLink1);

				}
				else
				{
					strThanks1="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
					retdetails.setStrSubject("BookBattery Inverter Details.");
					activateLink1 = strsub1+"\r\n\r\n"+strThanks1+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "activateLink1 :" + activateLink1);

				}
			
			retdetails.setStrText(activateLink1);
			Thread mt1=new MailThread(retdetails,"");
			mt1.start();

			String[] tempArr=strretothermobnum.split(",");
			LogLevel.DEBUG(5,new Throwable(),"tempArr.length :"+tempArr.length );
			LogLevel.DEBUG(5,new Throwable(),"tempArr:"+tempArr);
				if(tempArr.length>0)
				  {
					String strTempValue="";
					for (int k=0;k<tempArr.length ;k++ )
						{	
						strTempValue=tempArr[k];
						LogLevel.DEBUG(5,new Throwable(),"tempArr:"+strTempValue);
						String strSMSResponse11=  sendsms1.sendSMS(strIpAddress,strPort,SMSFromAddress,ThanksmsMsg, strTempValue);
						LogLevel.DEBUG(5, new Throwable(), "strSMSResponse11 :" + strSMSResponse11);

						}
				  }
			
			/* code to send SMS and Email consumer details to operators */ 

			String ThanksuppsmsMsg="Inverter Ord Ref No: "+verificationcode+" Cust Mob No: "+strUsermobileno+" Franchisee Mob No: "+strretmobnum+" Location : "+Strlocorpin+""; 

			String strSMSResponse22 =  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThanksuppsmsMsg, strsuppmobnumber);
			LogLevel.DEBUG(5, new Throwable(), "strSMSResponse22 :" + strSMSResponse22);

			String ThankUMsgopt="BookBattery Consumer "+strusername+" with Mob no-"+strUsermobileno+", EmailID : "+emailid+"   ordered for "+inverterbrand+" >> "+invertermodel+" >> "+invertercapacity+" at price: Rs."+price+" Discount Price:  Rs. "+discountprice+" for  "+invertertype+" , Order Ref Number "+verificationcode+" Franchisee Name: "+strretname+" Franchisee Mob No: "+strretmobnum+" Franchisee EmailID: "+strretemail+" Location : "+Strlocorpin+".";

			String strsubop="Dear operator,\r\n\r\n"+""+ThankUMsgopt+"";
			String strThanksop="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
			MailTrans operator=new MailTrans();
			operator.setStrSmtpHost(strdomainname);
			operator.setStrFrom(FromEmailId);
			operator.setStrTo(strsuppemaild);
			operator.setStrSubject("BookBattery Inverter Ordered Details.");
			String activateLinkop = strsubop+"\r\n\r\n"+strThanksop+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
			LogLevel.DEBUG(5, new Throwable(), "activateLinkop :" + activateLinkop);
			operator.setStrText(activateLinkop);
			Thread mtopt=new MailThread(operator,"");
			mtopt.start();

			Calendar cal = Calendar.getInstance();
			int currentMonth = cal.get(Calendar.MONTH) + 1;
			int currentYear = cal.get(Calendar.YEAR);

			String strPdfURL="";
			
			if(agent_name == null || agent_name == "0" || agent_name == "" || agent_name.equals("0") || agent_name.equals(""))
			{
					String StrSqlQryOperatorname = "select agent_name from inverter_order_details where (NOT agent_name = '0' or agent_name = ' ' ) order by creation_date desc limit 1";
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
			}
			else
			{

				agent_name=agent_name;
			}

				if(strarea.equals("Test Area"))
				{
					String strSqlQry = "insert into inverter_order_details(order_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_verify_code,retailer_name,retailer_mobile_number,retailer_emailid,state,city,inverter_brand,inverter_model,inverter_capacity,price,area,pincode,creation_date)values(NULL,'"+verificationcode+"','"+strusername+"','"+strUsermobileno+"','"+emailid+"','"+verifycode+"','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+strstate3+"','"+pincity+"','"+inverterbrand+"','"+invertermodel+"','"+invertercapacity+"','"+discountprice+"','"+strarea+"','"+strpincode+"',now())";
					LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
					int reslt = qm.executeUpdate(strSqlQry);
				}
				else
				{
					String strSqlQry = "insert into inverter_order_details(order_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_verify_code,retailer_name,retailer_mobile_number,retailer_emailid,state,city,inverter_brand,inverter_model,inverter_capacity,price,area,pincode,creation_date,agent_name)values(NULL,'"+verificationcode+"','"+strusername+"','"+strUsermobileno+"','"+emailid+"','"+verifycode+"','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+strstate3+"','"+pincity+"','"+inverterbrand+"','"+invertermodel+"','"+invertercapacity+"','"+discountprice+"','"+strarea+"','"+strpincode+"',now(),'"+agent_name+"')";
					LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
					int reslt = qm.executeUpdate(strSqlQry);

				}

			String strSqlQryleads ="insert into leads(lead_id,retailer_name,retailer_id,deal_id,email_id,vendor_id,product_id,product_name,mobile_number,vendor_name,promotion_code,price,subcategory_id,category_id,creation_date,created_by,updated_date,updated_by)values(NULL,'"+strretname+"','"+Strretid+"','0','"+emailid+"','22','0','undefined','"+strUsermobileno+"','"+inverterbrand+"','0','0','0','0',now(),'ngit',now(),'ngit')"; 
			LogLevel.DEBUG(5,new Throwable()," :strSqlQryleads "+strSqlQryleads);
			int resltleads = qm.executeUpdate(strSqlQryleads);



		/** Jhansi Started placing code for inverter battery **/
			String verificationcodeinvbat = "";

			if(batterymodel.equals("0") || batterymodel.equals("") || batterymodel == "")
				{
					LogLevel.DEBUG(5, new Throwable(), "battery model jhansi :" + batterymodel);

			}
			else
			{
				int looprun;
				if(invertercapacity.equals("1400 VA") || invertercapacity.equals("1450 VA"))
				{
					looprun=2;
				}
				else
				{
					looprun=1;

				}
				for(int z=0;z<looprun;z++)
				{
					LogLevel.DEBUG(5, new Throwable(), "Entering to for loop :" + z);

			/*following code is for generating the random number */
			GregorianCalendar date1 = new GregorianCalendar();
			int millseconds1 = date1.get(Calendar.MILLISECOND);
			LogLevel.DEBUG(5, new Throwable(), "millseconds1 :" + millseconds1);

			String milli1 = Integer.toString(millseconds1);
			LogLevel.DEBUG(5, new Throwable(), "milli1 :" + milli1);
			char milliseond1 = milli1.charAt(0);
			LogLevel.DEBUG(5, new Throwable(), "milliseond1 :" + milliseond1);
			
			Random r1 = new Random( System.currentTimeMillis() );
			LogLevel.DEBUG(5, new Throwable(), "r1 :" + r1);
			int num1 = ((milliseond1 + r1.nextInt(2)) * 10000 + r1.nextInt(10000));
			//LogLevel.DEBUG(5, new Throwable(), "formula :" + r.nextInt(2) * 100000 + r.nextInt(100000));
			LogLevel.DEBUG(5, new Throwable(), "num1 :" + num1);
			

			String verificationcode11 = Integer.toString(num1);
			verificationcodeinvbat ="ORD"+verificationcode11+"";
			LogLevel.DEBUG(5, new Throwable(), "verificationcodeinvbat :" + verificationcodeinvbat);
			
			/*following code is for generating the random number */
			String letters1 = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";

			String pw1 = "";
				for (int i=0; i<PASSWORD_LENGTH; i++)
				{
					int index = (int)(RANDOM.nextDouble()*letters1.length());
					pw1 += letters1.substring(index, index+1);
				}
			LogLevel.DEBUG(5, new Throwable(), "pw1 :" + pw1);
			
			/*code to generate the random number ends here */

			/* code to send SMS and Email retailers details to consumer */ 
			String ThankUMsg12="";
			String strThanks12=""; 
			String activateLink12 ="";
			if(inverterbrand.equals("Amaron"))
				{
					ThankUMsg12="Thanks for choosing bookbattery.com .Your BookBattery Battery Ord Ref No: "+verificationcodeinvbat+". Battery Model: "+batterymodel+". Price: "+batteryprice+" OBRP Price: Rs. "+withbatprice+". "+strretname+" with Mob No-"+strretmobnum+" and address "+straddress1+" will fullfill your order.For any kind of Assistance please call to 9603467559.";
					strThanks12="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
				}
				else
				{

					ThankUMsg12="Thanks for choosing bookbattery.com .Your BookBattery Battery Ord Ref No: "+verificationcodeinvbat+". Battery Model: "+batterymodel+". Price: "+batteryprice+" OBRP Price: Rs. "+withbatprice+". "+strretname+" with Mob No-"+strretmobnum+" and address "+straddress1+" will fullfill your order.For any kind of Assistance please call to 9603467559.";
					strThanks12="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 

				}

			//String ThankUSMSMsg = "Ord Ref No: "+verificationcode+" . Model: "+batterymodel+" . Price: Rs."+price+" . OBRP Price : Rs."+withbatprice+" . You will shortly receive a call about battery pickup or delivery details.";
		
			//String ThankUSMSMsginvbat ="Ord Ref No: "+verificationcodeinvbat+" . Model: "+batterymodel+" Price: Rs. "+batteryprice+" OBRP Price: Rs. "+withbatprice+" You will shortly receive a call about battery pickup or delivery details";

			String ThankUSMSMsginvbat ="Thanks for choosing bookbattery.com .Your Battery Ord Ref No: "+verificationcodeinvbat+" for Battery Model: "+batterymodel+" Price: Rs. "+batteryprice+" OBRP Price: Rs. "+withbatprice+" You will shortly receive a call.For any kind of Assistance please call to 9603467559. ";
			
			String strSMSResponse12=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUSMSMsginvbat, strUsermobileno);
			LogLevel.DEBUG(5, new Throwable(), "strSMSResponse12 :" + strSMSResponse12);

			String strsub12="Dear "+strusername+",\r\n\r\n"+""+ThankUMsg12+"";
			MailTrans sendverifycode12=new MailTrans();
			sendverifycode12.setStrSmtpHost(strdomainname);
			sendverifycode12.setStrFrom(FromEmailId);
			sendverifycode12.setStrTo(emailid);
			if(inverterbrand.equals("Amaron"))
				{
					sendverifycode12.setStrSubject("BookBattery Inverter Battery Details.");
					activateLink12 = strsub12+"\r\n\r\n"+strThanks12+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "activateLink12 :" + activateLink12);


				}
			else
				{

					sendverifycode12.setStrSubject("BookBattery Inverter Battery Details.");
					activateLink12 = strsub12+"\r\n\r\n"+strThanks12+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "activateLink12 :" + activateLink12);

				}
			sendverifycode12.setStrText(activateLink12);
			Thread mt12=new MailThread(sendverifycode12,"");
			mt12.start();

			
			/* code to send SMS and Email consumer details to retailers */ 
			String ThankUMsg123="";
			String strThanks123=""; 
			String activateLink123 ="";
			if(inverterbrand.equals("Amaron"))
				{
					ThankUMsg123="BookBattery Consumer "+strusername+" with Mob no-"+strUsermobileno+" and Order Ref Number "+verificationcodeinvbat+" ordered for "+inverterbrand+" >> "+batterymodel+" at price: Rs."+batteryprice+" OBRP Price: Rs. "+withbatprice+". Please deliver it..For any kind of Assistance please call to 9603467559.";
					strThanks123="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 

				}
				else
				{
					ThankUMsg123="BookBattery Consumer "+strusername+" with Mob no-"+strUsermobileno+" and Order Ref Number "+verificationcodeinvbat+" ordered for "+inverterbrand+" >> "+batterymodel+" at price: Rs."+batteryprice+" OBRP Price: Rs. "+withbatprice+". Please deliver it..For any kind of Assistance please call to 9603467559.";
					strThanks123="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 


				}

			String ThanksmsMsg123=""+strusername+" Mob No: "+strUsermobileno+" and Ord Ref No: "+verificationcodeinvbat+" ordered "+inverterbrand+" >> "+batterymodel+" at Price: Rs. "+batteryprice+" OBRP Price: Rs. "+withbatprice+" Please install";

			String strSMSResponse1234=  sendsms1.sendSMS(strIpAddress,strPort,SMSFromAddress,ThanksmsMsg123, strretmobnum);
			LogLevel.DEBUG(5, new Throwable(), "strSMSResponse1234 :" + strSMSResponse1234);

			String strsub123="Dear "+strretname+",\r\n\r\n"+""+ThankUMsg123+"";
			MailTrans retdetails123=new MailTrans();
			retdetails123.setStrSmtpHost(strdomainname);
			retdetails123.setStrFrom(FromEmailId);
			retdetails123.setStrTo(strretemail);

				if(inverterbrand.equals("Amaron"))
				{
					retdetails123.setStrSubject("BookBattery Inverter Battery Details.");
					activateLink123 = strsub123+"\r\n\r\n"+strThanks123+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "activateLink123 :" + activateLink123);
				}

				else
				{
					retdetails123.setStrSubject("BookBattery Inverter Battery Details.");
					activateLink123 = strsub123+"\r\n\r\n"+strThanks123+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
					LogLevel.DEBUG(5, new Throwable(), "activateLink123 :" + activateLink123);


				}
			retdetails123.setStrText(activateLink123);
			Thread mt123=new MailThread(retdetails123,"");
			mt123.start();

			String[] tempArr123=strretothermobnum.split(",");
			LogLevel.DEBUG(5,new Throwable(),"tempArr123.length :"+tempArr123.length );
			LogLevel.DEBUG(5,new Throwable(),"tempArr123:"+tempArr123);
				if(tempArr123.length>0)
				  {
					String strTempValue123="";
					for (int k=0;k<tempArr123.length ;k++ )
						{	
						strTempValue123=tempArr123[k];
						LogLevel.DEBUG(5,new Throwable(),"tempArr123:"+strTempValue123);
						String strSMSResponse1123=  sendsms1.sendSMS(strIpAddress,strPort,SMSFromAddress,ThanksmsMsg123, strTempValue123);
						LogLevel.DEBUG(5, new Throwable(), "strSMSResponse1123 :" + strSMSResponse1123);

						}
				  }
			

			/* Code to send SMS and Email consumer details to Operator */ 

			String ThanksuppsmsMsg123="Battery Ord Ref No: "+verificationcodeinvbat+" Cust Mob No: "+strUsermobileno+" Franchisee Mob No: "+strretmobnum+" Location : "+Strlocorpin+""; 

			String strSMSResponse223 =  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThanksuppsmsMsg123, strsuppmobnumber);
			LogLevel.DEBUG(5, new Throwable(), "strSMSResponse223 :" + strSMSResponse223);

			String ThankUMsgopt123="BookBattery Consumer "+strusername+" with Mob no-"+strUsermobileno+", EmailID : "+emailid+"   ordered for "+inverterbrand+" >> "+batterymodel+" at price: Rs."+batteryprice+" OBRP Price: Rs. "+withbatprice+" for  Inverter Batteries , Order Ref Number "+verificationcodeinvbat+" Franchisee Name: "+strretname+" Franchisee Mob No: "+strretmobnum+" Franchisee EmailID: "+strretemail+" Location : "+Strlocorpin+"";

			String strsubop123="Dear operator,\r\n\r\n"+""+ThankUMsgopt123+"";
			String strThanksop123="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
			MailTrans operator123=new MailTrans();
			operator123.setStrSmtpHost(strdomainname);
			operator123.setStrFrom(FromEmailId);
			operator123.setStrTo(strsuppemaild);
			operator123.setStrSubject("BookBattery Inverter Battery Ordered Details.");
			String activateLinkop123 = strsubop123+"\r\n\r\n"+strThanksop123+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
			LogLevel.DEBUG(5, new Throwable(), "activateLinkop123 :" + activateLinkop123);
			operator123.setStrText(activateLinkop123);
			Thread mtopt123=new MailThread(operator123,"");
			mtopt123.start();

			/*String StrSqlQryOperatorname1 = "select agent_name from battery_order_details order by creation_date desc limit 1";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQryOperatorname1 :" + StrSqlQryOperatorname1);

			Hashtable htgetoperatorname1 = qm.getRow(StrSqlQryOperatorname1); 
			String agent_name1=(String)htgetoperatorname1.get("agent_name");
			
			if(agent_name1.equals("0"))
			{
				agent_name1="operator1";
			}
			else if(agent_name1.equals("operator1"))
			{
				agent_name1="operator2";
			}
			else if(agent_name1.equals("operator2"))
			{
				agent_name1="operator3";
			}
			else if(agent_name1.equals("operator3"))
			{
				agent_name1="operator1";
			}*/

				if(strarea.equals("Test Area"))
				{
					String strSqlQry123 = "insert into battery_order_details(ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_verif_code,retailer_name,retailer_mobilnumber,retailer_emailis,city,battery_brand,battery_model,price,area,pincode,pdfurl,bat_type,veh_name,veh_model,witholdbatprice,creation_date,agent_name)values(NULL,'"+verificationcodeinvbat+"','"+strusername+"','"+strUsermobileno+"','"+emailid+"','"+verifycode+"','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+pincity+"','"+inverterbrand+"','"+batterymodel+"','"+batteryprice+"','"+strarea+"','"+strpincode+"','','Inverter Batteries','','','"+withbatprice+"',now(),'"+agent_name+"')";
					LogLevel.DEBUG(5,new Throwable()," strSqlQry123: "+strSqlQry123);
					int reslt123 = qm.executeUpdate(strSqlQry123);
				}
				else
					{
						String strSqlQry123 = "insert into battery_order_details(ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_verif_code,retailer_name,retailer_mobilnumber,retailer_emailis,city,battery_brand,battery_model,price,area,pincode,pdfurl,bat_type,veh_name,veh_model,witholdbatprice,creation_date,agent_name)values(NULL,'"+verificationcodeinvbat+"','"+strusername+"','"+strUsermobileno+"','"+emailid+"','"+verifycode+"','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+pincity+"','"+inverterbrand+"','"+batterymodel+"','"+batteryprice+"','"+strarea+"','"+strpincode+"','','Inverter Batteries','','','"+withbatprice+"',now(),'"+agent_name+"')";
						LogLevel.DEBUG(5,new Throwable()," strSqlQry123: "+strSqlQry123);
						int reslt123 = qm.executeUpdate(strSqlQry123);

					}

			String strSqlQryleads123 ="insert into leads(lead_id,retailer_name,retailer_id,deal_id,email_id,vendor_id,product_id,product_name,mobile_number,vendor_name,promotion_code,price,subcategory_id,category_id,creation_date,created_by,updated_date,updated_by)values(NULL,'"+strretname+"','"+Strretid+"','0','"+emailid+"','22','0','undefined','"+strUsermobileno+"','"+inverterbrand+"','0','0','0','0',now(),'ngit',now(),'ngit')"; 
			LogLevel.DEBUG(5,new Throwable()," :strSqlQryleads123 "+strSqlQryleads123);
			int resltleads123 = qm.executeUpdate(strSqlQryleads123);

				}

			}

		String portalname="";
			if(batterymodel.equals("0") || batterymodel.equals("") || batterymodel == "")
				{
					if(inverterbrand.equals("Amaron"))
					{
						portalname="BookBattery";
					}
					else
					{
						portalname="BookBattery";
					}
					if(mobileversion.equals("mobile"))
						{
						//strRes="<table border='0' width='100%' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td align='left'>&nbsp;<font color='#562d82'>"+portalname+"</td><td align='right'><a href=\"javascript:navigatetobatterywale('"+emailid+"','"+strUsermobileno+"','"+pw+"','"+strusername+"','"+usercity+"','"+strstate3+"');\");\"><img src=\"/bookbattery/images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='100%' height='2px' bgcolor='#FFFFFF' valign'top'><tr bgcolor='#FFFFFF'><td align='justify' bgcolor='#FFFFFF' style='font-family:Verdana;font-size:11px;color:#000000;'>Your Order <b>"+verificationcode+"</b> for Inverter Model: <b>"+invertermodel+"</b>, MRP: Rs <b>"+price+"</b>, Discount Price: Rs. <b>"+discountprice+"</b> is confirmed. Our Installation Engineer will call you shortly for delivery/pickup details.</td></tr><tr><td><table width='100%' border='0'><tr><td style='text-decoration: none; font-family:Verdana;font-size:11px;color:#FF8C00;	text-decoration:none;padding:1px 1px;' valign='middle' width='40%' align='right'><b>Click here to </b></td><td width='60%' align='left'><a href='/bookbattery/mobile/orderbattery.jsp?keyword=inverters' ><input type='button' class='inverterbatterybutton' name='Submitrret' value='Order Inverter Battery' ></a></td></tr></table></td></tr><tr height='15'><td></td></tr><tr><td align='left' style='font-family:Verdana;font-size:14px;color:#FF8C00;text-decoration:none;padding:1px 1px;'></font><b>Why To Order Inverter Battery:</b></td></tr><tr><td height='2'></td></tr><table width='100%' border='0' align='center' valign='top' bgcolor='#FFFFFF'><tr><td width='1%' align='right' valign='top'><img  src='/bookbattery/images/arrow2.PNG' height='10' width='15'/></td><td width='99%' align='left'  style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' vliagn='top' ><div align='justify'>By Clicking on<font color='#FF8C00'><b> Order Inverter Battery</b></font> button. You will find <font color='#FF8C00'><b>Inverter Batteries</b></font> which is suitable for your Inverter and get to <font color='#FF8C00'><b>Install as Package</b></font></div></td></tr><tr><td width='1%' align='right' valign='top'><img  src='/bookbattery/images/arrow2.PNG' height='10' width='15'/></td><td width='99%' align='left'  style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' vliagn='top' ><div align='justify'>Get the Inverter Battery Delivered and Fitted At Your Place - <font color='#FF8C00'><b>No Need to Search for Inverter Batteries which will Save your Time</b></font></div></td></tr><tr><td width='1%' align='right' valign='top'><img  src='/bookbattery/images/arrow2.PNG' height='10' width='15'/></td><td width='99%' align='left'  style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' valign='top'><div align='justify'>Get the latest stock Inverter Battery from an <font color='#FF8C00'><b>Official Authorised Branded Online Dealer</b></font> like us</div></td></tr><tr><td width='1%' align='right' valign='top'><img  src='/bookbattery/images/arrow2.PNG' height='10' width='15'/></td><td width='99%' align='left'  style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' valign='top'><div align='justify'>Get <font color='#FF8C00'><b>Original Warranty</b></font> from an <font color='#FF8C00'><b>Official Authorised Branded Online Dealer</b></font> like us</div></td></tr><tr><td width='1%' align='right' valign='top'><img  src='/bookbattery/images/arrow2.PNG' height='10' width='15'/></td><td width='99%' align='left'  style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' valign='top'><div align='justify'>Pay only when the Inverter Battery is Delivered and Fitted - <font color='#FF8C00'><b>No Upfront Payment</b></font></div></td></tr><tr height='20'><td width='1%'></td><td align='center'><input type='button' name='emailok' id='emailok' value='OK' class='button4' onclick=\"javascript:navigatetobatterywale('"+emailid+"','"+strUsermobileno+"','"+pw+"','"+strusername+"','"+usercity+"','"+strstate3+"');\");\" ><br></td></tr><tr height='15'></tr></table></table>";
						
						//strRes="<table border='0' width='100%' height='10px' cellpadding='0' cellspacing='0'><tr class='top1'><td align='left'>&nbsp;<font color='#562d82'>BookBattery</td><td align='right'<a href=\"javascript:navigatetobatterywale('"+emailid+"','"+strUsermobileno+"','"+pw+"','"+strusername+"','"+usercity+"','"+strstate3+"');\");\"><img src=\"/bookbattery/images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='100%' height='2px' bgcolor='#FFFFFF' valign'top'><tr bgcolor='#FFFFFF'><td align='justify' bgcolor='#FFFFFF' style='font-family:Verdana;font-size:11px;color:#000000;'>Your Order <b>"+verificationcode+"</b> for Inverter Model: <b>"+invertermodel+"</b>, MRP: Rs <b>"+price+"</b>, Discount Price: Rs. <b>"+discountprice+"</b> is confirmed. Our Installation Engineer will call you shortly for delivery/pickup details.</td></tr><tr><td><table width='100%' border='0'><tr><td style='text-decoration: none; font-family:Verdana;font-size:11px;color:#FF8C00;	text-decoration:none;padding:1px 1px;' valign='middle' width='40%' align='right'><b>Click here to </b></td><td width='60%' align='left'><a href='/bookbattery/mobile/orderbattery.jsp?keyword=inverters' ><input type='button' class='inverterbatterybutton' name='Submitrret' value='Order Inverter Battery' ></a></td></tr></table></td></tr><tr height='15'><td></td></tr><br></td></tr><tr><td height='10'></td></tr></table>";
						
						
						strRes ="<table cellspacing='10' cellpadding='0'><table width='100%' valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: center;'> BookBattery</td><td width='100%' align='center' style='COLOR:#F96F2B;     padding-right: 10px; font-size: 24px;font-weight: 600;text-align: left;' onclick=\"javascript:navigatetobatterywale('"+emailid+"','"+strUsermobileno+"','"+pw+"','"+strusername+"','"+usercity+"','"+strstate3+"','inverter','','','"+inverterbrand+"','"+invertermodel+"','"+discountprice+"','','1','"+strretname+"','"+strretmobnum+"');\");\" > X</td></tr></table><hr style='background: white; width: 95%;margin-top: 1px;margin-bottom: 1px;'><table border='0' width='100%' height='2px'  valign'top'><tr><td align='justify' style='font-family:Verdana;font-size:14px;color:#FFFFFF;padding: 10px;'>Your Order <b>"+verificationcode+"</b> for Inverter Model: <b>"+invertermodel+"</b>, MRP: Rs <b>"+price+"</b>, Discount Price: Rs. <b>"+discountprice+"</b> is confirmed. Our Installation Engineer will call you shortly for delivery/pickup details.</td></tr><tr><td><table width='100%' border='0'><tr><td style='text-decoration: none; font-family:Verdana;font-size:11px;color:#FF8C00;	text-decoration:none;padding:1px 1px;' valign='middle' width='100%' align='center'><b>Click here to </b></td></tr></tr><td width='100%' align='center'><a href='/bookbattery/mobile/orderbattery.jsp?keyword=inverters' ><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif;background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn' name='Submitrret' value='Order Inverter Battery' ></a> <input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  value='Close' align='left'  onclick=\"javascript:navigatetobatterywale('"+emailid+"','"+strUsermobileno+"','"+pw+"','"+strusername+"','"+usercity+"','"+strstate3+"','inverter','','','"+inverterbrand+"','"+invertermodel+"','"+discountprice+"','','1','"+strretname+"','"+strretmobnum+"');\");\" ></td></tr></table></table><tr height='26'><td colspan='3' align='center' class='subheading' id='displayerrormsg'><tr></tr>tr><td height='10'></td></tr></td></tr></table><tr><td height='0'></td></tr></table>";
						
					}
					else
					{
						//strRes="<table border='0' width='550px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#562d82'>"+portalname+"</td><td align='right'><a href=\"javascript:navigatetobatterywale('"+emailid+"','"+strUsermobileno+"','"+pw+"','"+strusername+"','"+usercity+"','"+strstate3+"');\");\" ><img src=\"/bookbattery/images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='550px' height='2px' bgcolor='#FFFFFF' valign'top'><tr bgcolor='#FFFFFF'><td align='justify' bgcolor='#FFFFFF' style='font-family:Verdana;font-size:11px;color:#000000;'>Your Order <b>"+verificationcode+"</b> for Inverter Model: <b>"+invertermodel+"</b>, MRP: Rs <b>"+price+"</b>, Discount Price: Rs. <b>"+discountprice+"</b> is confirmed. Our Installation Engineer will call you shortly for delivery/pickup details.</td></tr><tr><td><table width='100%' border='0'><tr><td style='text-decoration: none; font-family:Verdana;font-size:11px;color:#FF8C00;	text-decoration:none;padding:1px 1px;' valign='middle' width='40%' align='right'><b>Click here to </b></td><td width='60%' align='left'><a href='/bookbattery/inverterbattery.jsp' ><input type='button' class='inverterbatterybutton' name='Submitrret' value='Order Inverter Battery' ></a></td></tr></table></td></tr><tr height='15'><td></td></tr><tr><td align='left' style='font-family:Verdana;font-size:14px;color:#FF8C00;text-decoration:none;padding:1px 1px;'></font><b>Why To Order Inverter Battery:</b></td></tr><tr><td height='2'></td></tr><table width='550px' border='0' align='center' valign='top' bgcolor='#FFFFFF'><tr><td width='1%' align='right' valign='top'><img  src='/bookbattery/images/arrow2.PNG' height='10' width='15'/></td><td width='99%' align='left'  style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' vliagn='top' ><div align='justify'>By Clicking on<font color='#FF8C00'><b> Order Inverter Battery</b></font> button. You will find <font color='#FF8C00'><b>Inverter Batteries</b></font> which is suitable for your Inverter and get to <font color='#FF8C00'><b>Install as Package</b></font></div></td></tr><tr><td width='1%' align='right' valign='top'><img  src='/bookbattery/images/arrow2.PNG' height='10' width='15'/></td><td width='99%' align='left'  style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' vliagn='top' ><div align='justify'>Get the Inverter Battery Delivered and Fitted At Your Place - <font color='#FF8C00'><b>No Need to Search for Inverter Batteries which will Save your Time</b></font></div></td></tr><tr><td width='1%' align='right'><img  src='/bookbattery/images/arrow2.PNG' height='10' width='15'/></td><td width='99%' align='left'  style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' ><div align='justify'>Get the latest stock Inverter Battery from an <font color='#FF8C00'><b>Official Authorised Branded Online Dealer</b></font> like us</div></td></tr><tr><td width='1%' align='right' ><img  src='/bookbattery/images/arrow2.PNG' height='10' width='15'/></td><td width='99%' align='left'  style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' ><div align='justify'>Get <font color='#FF8C00'><b>Original Warranty</b></font> from an <font color='#FF8C00'><b>Official Authorised Branded Online Dealer</b></font> like us</div></td></tr><tr><td width='1%' align='right'><img  src='/bookbattery/images/arrow2.PNG' height='10' width='15'/></td><td width='99%' align='left'  style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;'><div align='justify'>Pay only when the Inverter Battery is Delivered and Fitted - <font color='#FF8C00'><b>No Upfront Payment</b></font></div></td></tr><tr height='20'><td width='1%'></td><td align='center'><input type='button' name='emailok' id='emailok' value='OK'  class='button4' onclick=\"javascript:navigatetobatterywale('"+emailid+"','"+strUsermobileno+"','"+pw+"','"+strusername+"','"+usercity+"','"+strstate3+"');\");\" ><br></td></tr><tr height='15'></tr></table></table>";
						
						
						strRes ="<div class='col-md-6 col-md-offset-3'> <table cellspacing='10' cellpadding='0' bgcolor='#FFFFFF'><table width='650' bgcolor='#FFFFFF' valign='top'><tr height='10'><table width='650' bgcolor='#FFFFFF' valign='top'></table><table  width='650' bgcolor='#FFFFFF' valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 25px;font-weight: 600;text-align: center;'> BookBattery</td><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: left;' href=\"javascript:navigatetobatterywale('"+emailid+"','"+strUsermobileno+"','"+pw+"','"+strusername+"','"+usercity+"','"+strstate3+"','inverter','','','"+inverterbrand+"','"+invertermodel+"','"+discountprice+"','','1','"+strretname+"','"+strretmobnum+"');\"> <a style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: left;' href=\"javascript:navigatetobatterywale('"+emailid+"','"+strUsermobileno+"','"+pw+"','"+strusername+"','"+usercity+"','"+strstate3+"','inverter','','','"+inverterbrand+"','"+invertermodel+"','"+discountprice+"','','1','"+strretname+"','"+strretmobnum+"');\")> X</a></td></tr></table><hr style='background: white; width: 45%;margin-top: 1px;margin-bottom: 1px;'></tr></table><tr><td> <table width='650'  cellspacing='0' cellpadding='0' style='padding-left:10px;padding-right:10px;'>  <tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font style='font-size: 15px;' color='#FFFFFF'>Your Order <b>"+verificationcode+"</b> for Inverter Model: <b>"+invertermodel+"</b>, MRP: Rs <b>"+price+"</b>, Discount Price: Rs. <b>"+discountprice+"</b> is confirmed. Our Installation Engineer will call you shortly for delivery/pickup details.</font></td></tr></table></td><tr><td height='10'></td></tr></tr><tr height='16'><td colspan='3' align='center' class='subheading' id='displayrefinederrormsg1'></td></tr><tr><td  width='50%' align='center' > <a href='/bookbattery/inverterbattery.jsp' > <input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn' name='Submitrret' value='Order Inverter Battery' disable='false' ></a> <input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  value='Close' align='left' onclick=\"javascript:closemobiledivnavigatetobatterywalehomepage(greyout(false));\"></td></tr><tr height='16'><td colspan='3' align='center' class='subheading' id='displayrefinederrormsg1'></td></tr><tr><td height='5'></td></tr><tr><td height='15' align='left' style='font-family:;font-size:15px;color:#FFFFFF;text-decoration:none;padding:1px 1px;'><i class='fa fa-ban'style='font-size:large; color: #E2530C;'></i>&nbsp;&nbsp;Don’t Buy a Re-furbished or Re-labelled Battery Available at Cheaper Price in the Market</td></tr><tr><td height='15' align='left' style='font-family:;font-size:15px;color:#FFFFFF;text-decoration:none;padding:1px 1px;'><i class='fa fa-asterisk' style='font-size:large; color: #E2530C;'></i>&nbsp;&nbsp;Wiring will be charged extra depending on the wiring of the house/premise </td></tr><tr><td height='15' width='90%' align='left' style='font-family:;font-size:15px;color:#FFFFFF;text-decoration:none;padding:1px 1px;'><i class='fa fa-shopping-cart' style='font-size:large; color: #E2530C;'></i>&nbsp;&nbsp;Order with us and Buy a Genuine Battery at Reasonable Cost to save money in the Long Run.</td></tr><tr<tr><td height='15' width='90%' align='left' style='font-family:;font-size:15px;color:#FFFFFF;text-decoration:none;padding:1px 1px;'><i class='fa fa-heart' style='font-size:large; color: #E2530C;'></i>&nbsp;&nbsp;Free Delivery and Installation</b></font> if the <font><b>house/premise</b></font> is pre-wired and is <font ><b>within 10KM.</td></tr><tr><td height='10'></td></tr></table></div>";
						
						
					}
				}
				else
				{

				if(mobileversion.equals("mobile"))
					{
						strRes ="<table cellspacing='10' cellpadding='0'><table width='100%' valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: center;'> BookBattery</td><td width='100%' align='center' style='COLOR:#F96F2B;     padding-right: 10px; font-size: 24px;font-weight: 600;text-align: left;' onclick=\"javascript:navigatetobatterywale('"+emailid+"','"+strUsermobileno+"','"+pw+"','"+strusername+"','"+usercity+"','"+strstate3+"','inverterandinverterbattery','','','"+inverterbrand+"','"+invertermodel+"','"+discountprice+"','','2','"+strretname+"','"+strretmobnum+"');\"> X</td></tr></table><hr style='background: white; width: 95%;margin-top: 1px;margin-bottom: 1px;'><table border='0' width='100%' height='2px'  valign'top'><tr><td align='justify' style='font-family:Verdana;font-size:14px;color:#FFFFFF;padding: 10px;'>Your Order <b>"+verificationcode+"</b> for Inverter Model: <b>"+invertermodel+"</b>, MRP: Rs <b>"+price+"</b>, Discount Price: Rs. <b>"+discountprice+"</b> and Order <b>"+verificationcodeinvbat+"</b> for Inverter Battery Model: <b>"+batterymodel+"</b>, Price with out Old Battery: Rs <b>"+batteryprice+"</b>, Price with old battery return: Rs. <b>"+withbatprice+"</b> is confirmed. Our Installation Engineer will call you shortly for delivery/pickup details.</td></tr><tr><td><table width='100%' border='0'></tr><td width='100%' align='center'><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  value='Close' align='left'  onclick=\"javascript:navigatetobatterywale('"+emailid+"','"+strUsermobileno+"','"+pw+"','"+strusername+"','"+usercity+"','"+strstate3+"','inverterandinverterbattery','','','"+inverterbrand+"','"+invertermodel+"','"+discountprice+"','','2','"+strretname+"','"+strretmobnum+"');\" ></td></tr></table></table><tr height='26'><td colspan='3' align='center' class='subheading' id='displayerrormsg'><tr></tr>tr><td height='10'></td></tr></td></tr></table><tr><td height='0'></td></tr></table>";

						
					}
					else
					{
						//strRes="<table border='0' width='550px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#562d82'>"+portalname+"</td><td align='right'><a href=\"javascript:navigatetobatterywale('"+emailid+"','"+strUsermobileno+"','"+pw+"','"+strusername+"','"+usercity+"','"+strstate3+"');\");\" ><img src=\"/bookbattery/images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='550px' height='2px' bgcolor='#FFFFFF' valign'top'><tr bgcolor='#FFFFFF'><td align='justify' bgcolor='#FFFFFF' style='font-family:Verdana;font-size:11px;color:#000000;'>Your Order <b>"+verificationcode+"</b> for Inverter Model: <b>"+invertermodel+"</b>, MRP: Rs <b>"+price+"</b>, Discount Price: Rs. <b>"+discountprice+"</b> and Order <b>"+verificationcodeinvbat+"</b> for Inverter Battery Model: <b>"+batterymodel+"</b>, Price with out Old Battery: Rs <b>"+batteryprice+"</b>, Price with old battery return: Rs. <b>"+withbatprice+"</b> is confirmed. Our Installation Engineer will call you shortly for delivery/pickup details.</td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' class='button4'onclick=\"javascript:navigatetobatterywale('"+emailid+"','"+strUsermobileno+"','"+pw+"','"+strusername+"','"+usercity+"','"+strstate3+"');\");\"  ><br></td></tr><tr height='15'></tr></table>";
						
						
						strRes="<div class='col-md-4 col-md-offset-4'>  <table border='0' width='300px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'>  <tr height='10'><table width='100%'  valign='top'></table><table  width='100%'  valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 25px;font-weight: 600;text-align: center;'> BookBattery</td> <td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: left;'  > <a style='color: #F96F2B;' href=\"javascript:navigatetobatterywale('"+emailid+"','"+strUsermobileno+"','"+pw+"','"+strusername+"','"+usercity+"','"+strstate3+"','inverterandinverterbattery','','','"+inverterbrand+"','"+invertermodel+"','"+discountprice+"','','2','"+strretname+"','"+strretmobnum+"');\"> X </a> </td></tr></table><hr style='background: white; width: 90%;margin-top: 1px;margin-bottom: 1px;'></tr> </table><table border='0' style='margin-top: 10px;' width='100%' height='2px'  valign'top'><tr ><td align='justify'  style='font-family:Verdana;font-size:14px;color:#FFFFFF;padding: 16px;'>Your Order <b>"+verificationcode+"</b> for Inverter Model: <b>"+invertermodel+"</b>, MRP: Rs <b>"+price+"</b>, Discount Price: Rs. <b>"+discountprice+"</b> and Order <b>"+verificationcodeinvbat+"</b> for Inverter Battery Model: <b>"+batterymodel+"</b>, Price with out Old Battery: Rs <b>"+batteryprice+"</b>, Price with old battery return: Rs. <b>"+withbatprice+"</b> is confirmed. Our Installation Engineer will call you shortly for delivery/pickup details.</td></tr><tr height='10'><td></td></tr><tr height='10'><td align='center'> <input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn' name='Submitrret' value='OK' disable='false' onclick=\"javascript:navigatetobatterywale('"+emailid+"','"+strUsermobileno+"','"+pw+"','"+strusername+"','"+usercity+"','"+strstate3+"','inverterandinverterbattery','','','"+inverterbrand+"','"+invertermodel+"','"+discountprice+"','','2','"+strretname+"','"+strretmobnum+"');\" > <br></td></tr><tr><td height='10'></td></tr></table> </div>";
						
						
					}

				}
		/** Jhansi Ended placing code for inverter battery **/
			String StrSqlQryuser = "select email_id from batterywale_user_profile where email_id='"+emailid+"'";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQryuser :" + StrSqlQryuser);

			Hashtable htruser = qm.getRow(StrSqlQryuser); 
			String Stremailid=String.valueOf(htruser.get("email_id"));
			LogLevel.DEBUG(5, new Throwable(), "Stremailid :" + Stremailid);

			if(Stremailid.equals(null) || Stremailid.equals("null") || Stremailid == null || Stremailid == "null" || Stremailid =="")
			{
				addrs1 = addrs1.replace("'","\\'");
				addrs2 = addrs2.replace("'","\\'");

				String strSqlQryuserprof = "insert into batterywale_user_profile(user_id,email_id,mobile_number,password,name,address,address1,zipcode,city,state,mobile_verify_code,creation_date,created_by) values(NULL,'"+emailid+"','"+strUsermobileno+"','"+pw+"','"+strusername+"','"+addrs1+"','"+addrs2+"','"+userzipcode+"','"+city+"','"+strstate3+"','"+verifycode+"',now(),'ngit')";

				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQryuserprof);
				int reslt1 = qm.executeUpdate(strSqlQryuserprof);
			}
			else
			{
				String strSqlQryupdatepassword = "update batterywale_user_profile set password='"+pw+"' where email_id='"+emailid+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQryupdatepassword : "+strSqlQryupdatepassword);
				int reslt12 = qm.executeUpdate(strSqlQryupdatepassword);
			}

			/* code to send SMS and Email new user signup details to consumer */ 

			/*String ThankUMsguser="Please visit www.bookbattery.com with login name: "+emailid+" and Password: "+pw+" to know interesting news related to automobiles.";
			String strSMSResponse2=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUMsguser, strUsermobileno);
			LogLevel.DEBUG(5, new Throwable(), "strSMSResponse2 :" + strSMSResponse2);
			String strsub11="Dear "+strusername+",\r\n\r\n"+""+ThankUMsguser+"";
			String strThanks11="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
			MailTrans retdetails1=new MailTrans();
			retdetails1.setStrSmtpHost(strdomainname);
			retdetails1.setStrFrom(FromEmailId);
			retdetails1.setStrTo(emailid);
			retdetails1.setStrSubject("Thank You for Ordering the Inverter.");
			String activateLink12 = strsub11+"\r\n\r\n"+strThanks11+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
			LogLevel.DEBUG(5, new Throwable(), "activateLink12 :" + activateLink12);
			retdetails1.setStrText(activateLink12);
			Thread mt11=new MailThread(retdetails1,"");
			mt11.start();	*/		
		}	
	}
	catch (Exception e)
	{
		LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
		strRes=strRes;
	}
	return strRes;
}
/* **************************************************************************************************************************************\
	* This method is to get the inverter capacity
	* @strinvertercapacity : carries the query to get the inverter capacity from inverter_details table.
	\* **************************************************************************************************************************************/
	public String getinvertermodels(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		String inverterbrand= (req.getParameter("inverterbrand")!=null)?(req.getParameter("inverterbrand")):"";
		LogLevel.DEBUG(5,new Throwable(),"inverterbrand:"+inverterbrand );

		String invertercap= (req.getParameter("invertercap")!=null)?(req.getParameter("invertercap")):"";
		LogLevel.DEBUG(5,new Throwable(),"invertercap:"+invertercap );


		try
		{	
			
			String inverter_model="";
	 		ServletOutputStream out=res.getOutputStream();

			String strinvertercapacity = "select distinct(inverter_model) from inverter_details where inverter_brand='"+inverterbrand+"' and inverter_capacity='"+invertercap+"' order by inverter_model asc"; 
			LogLevel.DEBUG(5, new Throwable(), "strinvertercapacity :" + strinvertercapacity);
			
			Vector invertercapacityvector=qm.executeQuery(strinvertercapacity);
			LogLevel.DEBUG(5,new Throwable(),"invertercapacityvector:"+invertercapacityvector);
			if(invertercapacityvector.isEmpty())
			{ 
				strRes=strRes+"<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>Select&nbsp;Inverter&nbsp;Models</option>";
				return strRes;
			}
			else
			{
				for (int i=0; i<invertercapacityvector.size(); i++)
				{
					if(i==0)
					{
						strRes=strRes+"<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>Select&nbsp;Inverter&nbsp;Models</option>";				
					} 
					Hashtable ht=(Hashtable)invertercapacityvector.get(i);					
					inverter_model =String.valueOf(ht.get("inverter_model"));
					LogLevel.DEBUG(5,new Throwable(),"inverter_model:"+inverter_model);
					
					strRes=strRes+"<option style='background:#FFF' value='"+inverter_model+"' >"+inverter_model+"</option>"; 
					
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
}// end of class
