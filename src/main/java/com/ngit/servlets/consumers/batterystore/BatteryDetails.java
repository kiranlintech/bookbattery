/***********************************************************************		
	NGIT Confidential. 
	@File Name   : BatteryDetails.java 
	@Description : This Servlet is used to select the Battery details.
	@Author	     : Sai Krishna Daddala.
	@Date        : 1st September 2013
******************************************************************/ 
 
package com.ngit.servlets.consumers.batterystore;  
 
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.mail.*;
import com.ngit.javabean.sendsms.SendSMS;
import com.ngit.javabean.consumers.products.CompareTrans;
import com.ngit.javabean.consumers.products.GenerateExcelinvoice;

import java.util.Properties;
import java.util.Vector;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.io.*;
import java.util.Random;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.net.URLDecoder;  
import java.net.URLEncoder; 

import java.util.Properties;
import javax.mail.*;
import javax.activation.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

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

  
public class BatteryDetails extends HttpServlet 
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
	* This action is used to get battery details.
	\* *****************************************************************************************/		
	if(strWhatToDo.equalsIgnoreCase("getbatdetails"))
		{ 
			try
			{
				String strRes=getbatdetails(req,res,session,strIpAddress,strPort,strsuppmobnumber,strsuppmobnumber1,SMSFromAddress,baseurldirectory,OperatorTeamCount);
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
	* This action is used to get battery details.
	\* *****************************************************************************************/		
	if(strWhatToDo.equalsIgnoreCase("insertvisitor"))
		{ 
			try
			{
				String strRes=insertvisitor(req,res,session,strIpAddress,strPort,strsuppmobnumber,strsuppmobnumber1,SMSFromAddress,baseurldirectory,OperatorTeamCount);
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
				String strRes=sentverificationcode(req,res,session,domainname,strIpAddress,strPort,SMSFromAddress,FromEmailId,baseurldirectory);
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
	if(strWhatToDo.equalsIgnoreCase("insertconsumerdetails"))
		{ 
			try
			{
				String strRes=insertconsumerdetails(req,res,session,domainname,strIpAddress,strPort,SMSFromAddress,FromEmailId,strsuppemaild,baseUrl,strsuppmobnumber,strCMSSERVERIP,strPDFFilePath,strPDFRelFilePath,baseurldirectory,OperatorTeamCount);
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
	* This method is to fetch battery details details.
	* @strSqlQry : carries the query to select battery details from battery_details table.
	* @strSqlQry1 : Query has multiple pages to select battery details from battery_details table.
	\* **************************************************************************************************************************************/
	public String insertvisitor(HttpServletRequest req,HttpServletResponse res,HttpSession session,String strIpAddress,String strPort,String strsuppmobnumber,String strsuppmobnumber1,String SMSFromAddress,String baseurldirectory,String OperatorTeamCount)
	{
			
			String strUsermobileno= (req.getParameter("Usermobileno")!=null)?(req.getParameter("Usermobileno")):"";
			LogLevel.DEBUG(5,new Throwable(),"strUsermobileno:"+strUsermobileno);
			String strRes="";
			String agent_name="";
			int reslt;
		try
		{	
			
		
			ServletOutputStream out=res.getOutputStream();

			if(strUsermobileno.equals("9603467559") || strUsermobileno.equals(" ") || strUsermobileno.equals("9999999999") || strUsermobileno.equals(""))
			{
				String strSqlQryvisit = "insert into visitors_orders(vis_ord_id,bat_type,veh_make,veh_model,bat_brand,bat_capacity,state,city,area,pincode,mobile_number,option_type,keynumber,visitors_comments,creation_date)values(NULL,'Enquiry','','','','','','','','','"+strUsermobileno+"','order','','',now())";
				reslt = qm.executeUpdate(strSqlQryvisit);
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
						LogLevel.DEBUG(5, new Throwable(), "agent_name :" + agent_name);

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
					String strSqlQryvisit = "insert into visitors_orders(vis_ord_id,bat_type,veh_make,veh_model,bat_brand,bat_capacity,state,city,area,pincode,mobile_number,option_type,keynumber,visitors_comments,creation_date,agent_name)values(NULL,'Enquiry','','','','','','','','','"+strUsermobileno+"','order','','',now(),'"+agent_name+"')";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQryvisit: "+strSqlQryvisit);
					reslt = qm.executeUpdate(strSqlQryvisit);
				
				}
				else
				{
						agent_name=(String)htgetoperatorname1.get("agent_name");
						LogLevel.DEBUG(5, new Throwable(), "agent_name :" + agent_name);
						String strSqlQryvisit = "insert into visitors_orders(vis_ord_id,bat_type,veh_make,veh_model,bat_brand,bat_capacity,state,city,area,pincode,mobile_number,option_type,keynumber,visitors_comments,creation_date,agent_name)values(NULL,'Enquiry','','','','','','','','','"+strUsermobileno+"','order','','',now(),'"+agent_name+"')";
						LogLevel.DEBUG(5,new Throwable(),"strSqlQryvisit: "+strSqlQryvisit);
						reslt = qm.executeUpdate(strSqlQryvisit);
				}

			if( reslt>0)
			{
				strRes="Sucessfully inserted visitor";
			LogLevel.DEBUG(5,new Throwable(),"strRes: "+strRes);


			}
			else
			{

					strRes="Failed to insert visitor";
					LogLevel.DEBUG(5,new Throwable(),"strRes: "+strRes);


			}
			}
			
			//}
		}
		catch (Exception e)
		{
			LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
			e.printStackTrace();

		}
		return strRes;
	}
	/* **************************************************************************************************************************************\
	* This method is to fetch battery details details.
	* @strSqlQry : carries the query to select battery details from battery_details table.
	* @strSqlQry1 : Query has multiple pages to select battery details from battery_details table.
	\* **************************************************************************************************************************************/
	public String getbatdetails(HttpServletRequest req,HttpServletResponse res,HttpSession session,String strIpAddress,String strPort,String strsuppmobnumber,String strsuppmobnumber1,String SMSFromAddress,String baseurldirectory,String OperatorTeamCount)
	{
			String strbattype= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbattype:"+strbattype );

			String strvehmake= (req.getParameter("vehiclename")!=null)?(req.getParameter("vehiclename")):"";
			LogLevel.DEBUG(5,new Throwable(),"strvehmake:"+strvehmake );
			
			String strvehmodel= (req.getParameter("vehiclemodel")!=null)?(req.getParameter("vehiclemodel")):"";
			LogLevel.DEBUG(5,new Throwable(),"strvehmodel:"+strvehmodel );

			String strbatterycapacity= (req.getParameter("batterycapacity")!=null)?(req.getParameter("batterycapacity")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatterycapacity:"+strbatterycapacity );
			
			String strbattbrand= (req.getParameter("batterybrand")!=null)?(req.getParameter("batterybrand")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbattbrand:"+strbattbrand );

			String strbatterycapty= (req.getParameter("batterycapty")!=null)?(req.getParameter("batterycapty")):"";
			LogLevel.DEBUG(5,new Throwable(),"strbatterycapty:"+strbatterycapty );

			String state= (req.getParameter("state")!=null)?(req.getParameter("state")):"";
			LogLevel.DEBUG(5,new Throwable(),"state:"+state);
			
			String strcity= (req.getParameter("city")!=null)?(req.getParameter("city")):"";
			LogLevel.DEBUG(5,new Throwable(),"strcity:"+strcity);
			
			String strarea= (req.getParameter("area")!=null)?(req.getParameter("area")):"";
			LogLevel.DEBUG(5,new Throwable(),"strarea:"+strarea);

			String strpincode= (req.getParameter("pincode")!=null)?(req.getParameter("pincode")):"";
			LogLevel.DEBUG(5,new Throwable(),"strpincode:"+strpincode);

			String strUsermobileno= (req.getParameter("Usermobileno")!=null)?(req.getParameter("Usermobileno")):"";
			LogLevel.DEBUG(5,new Throwable(),"strUsermobileno:"+strUsermobileno);

			String sortprice= (req.getParameter("sortprice")!=null)?(req.getParameter("sortprice")):"";
			LogLevel.DEBUG(5,new Throwable(),"sortprice:"+sortprice); 

			String keyword= (req.getParameter("keyword")!=null)?(req.getParameter("keyword")):"";
			LogLevel.DEBUG(5,new Throwable(),"keyword:"+keyword);

			String optkeyword= (req.getParameter("optkeyword")!=null)?(req.getParameter("optkeyword")):"";
			LogLevel.DEBUG(5,new Throwable(),"optkeyword:"+optkeyword);

			String mobileversion= (req.getParameter("mobileversion")!=null)?(req.getParameter("mobileversion")):"";
			LogLevel.DEBUG(5,new Throwable(),"mobileversion:"+mobileversion);

			String backkeyword= (req.getParameter("backkeyword")!=null)?(req.getParameter("backkeyword")):"";
			LogLevel.DEBUG(5,new Throwable(),"backkeyword:"+backkeyword);

			String sortpricess = "";
			String agent_name="";

			if(keyword.equals("sortprices"))
			  {
				sortpricess = sortprice;
			  }
			  else
			  {
				sortpricess = "asc";
			  }
				String url="";
				if(optkeyword.equals("Car Batteries"))
				{
					url=""+baseurldirectory+"carbattery.jsp";
				}
				else if(optkeyword.equals("Bike Batteries"))
				{
					url=""+baseurldirectory+"bikebattery.jsp";
				}
				else if(optkeyword.equals("Inverter Batteries"))
				{
					url=""+baseurldirectory+"inverterbattery.jsp";
				}
				else if(optkeyword.equals("Bus Batteries"))
				{
					url=""+baseurldirectory+"busbattery.jsp";
				}
				else if(optkeyword.equals("Truck Batteries"))
				{
					url=""+baseurldirectory+"truckbattery.jsp";
				}
				else if(optkeyword.equals("Tractor Batteries"))
				{
					url=""+baseurldirectory+"tractorbattery.jsp";
				}
				else if(optkeyword.equals("Three Wheeler Batteries"))
				{
					url=""+baseurldirectory+"autorickbattery.jsp";
				}
				else if(optkeyword.equals("Gensets Batteries"))
				{
					url=""+baseurldirectory+"gensetbattery.jsp";
				}
				else
				{
					url=""+baseurldirectory+"index.jsp";
				}
			  
		String strRes="";
		try
		{	
			String strSqlQry ="";
			String strstate="";
			String strpincity="";
			String strSqlQrybat="";
			String city="";
			String message="";
			String strConditions1="";
			String strConditionsDetails="";
			//String strcustax="";
			String strvehmodel1 = URLEncoder.encode(strvehmodel, "UTF-8");
			LogLevel.DEBUG(5,new Throwable(),"strvehmodel1:"+strvehmodel1);
		
			if(strpincode == "")
			{
			//String StrSqlQrystate1 = "select state from search_whereever_keywords where city='"+strcity+"'";
			//LogLevel.DEBUG(5, new Throwable(), "StrSqlQrystate1 :" + StrSqlQrystate1);

			//Hashtable htgetstate1 = qm.getRow(StrSqlQrystate1); 
			//strstate=(String)htgetstate1.get("state");
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
			if(!strbattbrand.equals("All") )
			{
				strConditions1= " and bat_brand='"+strbattbrand+"' ";
			}
			else
			{	
				strConditions1= "";
			}


			if(!strbattbrand.equals("All") )
			{
				strConditionsDetails= " and a.bat_brand='"+strbattbrand+"' ";
			}
			else
			{	
				strConditionsDetails= "";
			}
			if(strbattype.equals("Inverter Batteries"))
			{
				strSqlQrybat ="select distinct(bat_id) from application_chat_mapping where bat_type='"+strbattype+"' and bat_capacity='"+strbatterycapty+"' "+strConditions1+" ";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQrybat :" + strSqlQrybat);
			}
			else if(strbattype.equals("Flat Plate Battery"))
			{
				strSqlQrybat ="select distinct(bat_id) from application_chat_mapping where bat_capacity='"+strbatterycapty+"'  and battery_type_flag='"+strbattype+"' "+strConditions1+" ";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQrybat :" + strSqlQrybat);
			}
			else if(strbattype.equals("Tubular Battery"))
			{
				strSqlQrybat ="select distinct(bat_id) from application_chat_mapping where  bat_capacity='"+strbatterycapty+"' and battery_type_flag='"+strbattype+"' "+strConditions1+" ";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQrybat :" + strSqlQrybat);
			}
			else if(strbattype.equals("Tall Tubular Battery"))
			{
				strSqlQrybat ="select distinct(bat_id) from application_chat_mapping where  bat_capacity='"+strbatterycapty+"' and battery_type_flag='"+strbattype+"' "+strConditions1+" ";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQrybat :" + strSqlQrybat);
			}
			else
			{
				strSqlQrybat ="select distinct(bat_id) from application_chat_mapping where bat_type='"+strbattype+"' and veh_name='"+strvehmake+"' and veh_model='"+strvehmodel+"' "+strConditions1+" ";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQrybat :" + strSqlQrybat);
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
			//Vector battmodel=qm.executeQuery(strSqlQrybat);
			//LogLevel.DEBUG(5,new Throwable(),"battmodel:"+battmodel);

			//for ( int j=0; j<battmodel.size(); j++)
			//{
			//Hashtable ht21=(Hashtable)battmodel.get(j);
			//String bat_model =String.valueOf(ht21.get("bat_model"));
			
			//String strSqlQrym ="select bat_act_price,bat_witbat_price from batteryprice where bat_brand='"+strbattbrand+"' and bat_model='"+bat_model+"' and state='"+strstate+"'";
			//LogLevel.DEBUG(5, new Throwable(), "strSqlQrym :" + strSqlQrym);
			
			
			//Vector BatpriceVector=qm.executeQuery(strSqlQrym);
			//LogLevel.DEBUG(5,new Throwable(),"BatpriceVector:"+BatpriceVector );
			if(strbattype.equals("Inverter Batteries"))
			{
				strSqlQry ="select a.bat_brand,a.bat_model,a.veh_model,a.bat_capacity,a.bat_warranty,a.icon_url,a.description,b.bat_act_price,b.bat_witbat_price,b.bat_ret_price,a.battery_type_flag from application_chat_mapping a, batteryprice b where a.bat_type='"+strbattype+"' and a.bat_capacity='"+strbatterycapty+"' "+strConditionsDetails+" and b.state='"+strstate+"' and b.city='"+city+"' and a.bat_model=b.bat_model and a.bat_id in ("+batids+") order by CAST(b.bat_witbat_price AS SIGNED) "+sortpricess+"";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
				
				message = "User selected  "+strbattype+" >> "+strbatterycapty+" >> "+city+" for order Battery. User Mob No: "+strUsermobileno+""; 
			}
			else if(strbattype.equals("Flat Plate Battery"))
			{
				strSqlQry ="select a.bat_brand,a.bat_model,a.veh_model,a.bat_capacity,a.bat_warranty,a.icon_url,a.description,b.bat_act_price,b.bat_witbat_price,b.bat_ret_price,a.battery_type_flag from application_chat_mapping a, batteryprice b where a.battery_type_flag='"+strbattype+"' and a.bat_capacity='"+strbatterycapty+"' "+strConditionsDetails+" and b.state='"+strstate+"' and b.city='"+city+"' and a.bat_model=b.bat_model and a.bat_id in ("+batids+") order by CAST(b.bat_witbat_price AS SIGNED) "+sortpricess+"";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
				
				message = "User selected  "+strbattype+" >> "+strbatterycapty+" >> "+city+" for order Battery. User Mob No: "+strUsermobileno+""; 
			}
			else if(strbattype.equals("Tubular Battery"))
			{
				strSqlQry ="select a.bat_brand,a.bat_model,a.veh_model,a.bat_capacity,a.bat_warranty,a.icon_url,a.description,b.bat_act_price,b.bat_witbat_price,b.bat_ret_price,a.battery_type_flag from application_chat_mapping a, batteryprice b where a.battery_type_flag='"+strbattype+"' and a.bat_capacity='"+strbatterycapty+"' "+strConditionsDetails+" and b.state='"+strstate+"' and b.city='"+city+"' and a.bat_model=b.bat_model and a.bat_id in ("+batids+") order by CAST(b.bat_witbat_price AS SIGNED) "+sortpricess+"";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
				
				message = "User selected  "+strbattype+" >> "+strbatterycapty+" >> "+city+" for order Battery. User Mob No: "+strUsermobileno+""; 
			}
			else if(strbattype.equals("Tall Tubular Battery"))
			{
				strSqlQry ="select a.bat_brand,a.bat_model,a.veh_model,a.bat_capacity,a.bat_warranty,a.icon_url,a.description,b.bat_act_price,b.bat_witbat_price,b.bat_ret_price,a.battery_type_flag from application_chat_mapping a, batteryprice b where a.battery_type_flag='"+strbattype+"' and a.bat_capacity='"+strbatterycapty+"' "+strConditionsDetails+" and b.state='"+strstate+"' and b.city='"+city+"' and a.bat_model=b.bat_model and a.bat_id in ("+batids+") order by CAST(b.bat_witbat_price AS SIGNED) "+sortpricess+"";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
				
				message = "User selected  "+strbattype+" >> "+strbatterycapty+" >> "+city+" for order Battery. User Mob No: "+strUsermobileno+""; 
			}
			else
			{
				strSqlQry ="select a.bat_brand,a.bat_model,a.veh_model,a.bat_capacity,a.bat_warranty,a.icon_url,a.description,b.bat_act_price,b.bat_witbat_price,b.bat_ret_price,a.battery_type_flag from application_chat_mapping a, batteryprice b where a.bat_type='"+strbattype+"' and a.veh_name='"+strvehmake+"' and a.veh_model='"+strvehmodel+"' "+strConditionsDetails+" and b.state='"+strstate+"' and b.city='"+city+"' and a.bat_model=b.bat_model and a.bat_id in ("+batids+") order by CAST(b.bat_witbat_price AS SIGNED) "+sortpricess+"";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

				message = "User selected  "+strbattype+" >> "+strvehmake+" >> "+strvehmodel+" >> "+city+" for order Battery. User Mob No: "+strUsermobileno+"";
			}

			Vector BatdetailsVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"BatdetailsVector:"+BatdetailsVector );
			String strstatevis ="";
			String cityvis ="";

			if(strbatterycapty.equals("0") || strbatterycapty == "0"){ strbatterycapty = ""; } else  { strbatterycapty=strbatterycapty; }
			if(strstate == null) { strstatevis = ""; } else { strstatevis = strstate; } 
			if(city == null) { cityvis = ""; } else { cityvis=city;	}
			
			if(strUsermobileno.equals("") || strUsermobileno.equals("null") || strUsermobileno.equals(null) || strUsermobileno.equals("undefined")|| keyword.equals("sortprices"))
			{

			}
			else if(strUsermobileno.equals("9603467559") || strUsermobileno.equals("9999999999") || strarea.equals("Test Area"))
			{
				String strSqlQryvisit = "insert into visitors_orders(vis_ord_id,bat_type,veh_make,veh_model,bat_brand,bat_capacity,state,city,area,pincode,mobile_number,option_type,keynumber,visitors_comments,creation_date)values(NULL,'"+strbattype+"','"+strvehmake+"','"+strvehmodel+"','"+strbattbrand+"','"+strbatterycapty+"','"+strstatevis+"','"+cityvis+"','"+strarea+"','"+strpincode+"','"+strUsermobileno+"','order','','',now())";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQryvisit: "+strSqlQryvisit);
				int reslt = qm.executeUpdate(strSqlQryvisit);



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
						LogLevel.DEBUG(5, new Throwable(), "agent_name :" + agent_name);

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

						String strSqlQryvisit = "insert into visitors_orders(vis_ord_id,bat_type,veh_make,veh_model,bat_brand,bat_capacity,state,city,area,pincode,mobile_number,option_type,keynumber,visitors_comments,creation_date,agent_name)values(NULL,'"+strbattype+"','"+strvehmake+"','"+strvehmodel+"','"+strbattbrand+"','"+strbatterycapty+"','"+strstatevis+"','"+cityvis+"','"+strarea+"','"+strpincode+"','"+strUsermobileno+"','order','','',now(),'"+agent_name+"')";
						LogLevel.DEBUG(5,new Throwable(),"strSqlQryvisit: "+strSqlQryvisit);
						int reslt = qm.executeUpdate(strSqlQryvisit);
						SendSMS sendsms = new SendSMS(qm);
				
				}
				else
				{
						agent_name=(String)htgetoperatorname1.get("agent_name");
						LogLevel.DEBUG(5, new Throwable(), "agent_name :" + agent_name);

						String strSqlQryvisit = "insert into visitors_orders(vis_ord_id,bat_type,veh_make,veh_model,bat_brand,bat_capacity,state,city,area,pincode,mobile_number,option_type,keynumber,visitors_comments,creation_date,agent_name)values(NULL,'"+strbattype+"','"+strvehmake+"','"+strvehmodel+"','"+strbattbrand+"','"+strbatterycapty+"','"+strstatevis+"','"+cityvis+"','"+strarea+"','"+strpincode+"','"+strUsermobileno+"','order','','',now(),'"+agent_name+"')";
						LogLevel.DEBUG(5,new Throwable(),"strSqlQryvisit: "+strSqlQryvisit);
						int reslt = qm.executeUpdate(strSqlQryvisit);
						SendSMS sendsms = new SendSMS(qm);

				}
			}
			if(BatdetailsVector.isEmpty())
			{ 
				if(mobileversion.equals("mobile"))
			    {
				LogLevel.DEBUG(1,new Throwable(),"Failed to fetch Battery details ");
				session.setAttribute("priority","1");
				session.setAttribute("sesbatterydetailsErrorMsg", "<font color='#ff3333' class='vrb10'>No Battery details found based on selection.! </font> ");
				session.removeAttribute("BatdetailsVector");
				res.sendRedirect(""+baseurldirectory+"mobile/orderbattery.jsp?keyword="+optkeyword+"");
				return strRes;
				}
				else
				{
				LogLevel.DEBUG(1,new Throwable(),"Failed to fetch Battery details ");
				session.setAttribute("priority","1");
				session.setAttribute("sesbatterydetailsErrorMsg", "<font color='#ff3333' class='vrb10'>No Battery details found based on selection.! </font> ");
				session.removeAttribute("BatdetailsVector");
				res.sendRedirect(url);
				return strRes;
				}
			}
			else
			{
				if(mobileversion.equals("mobile"))
			    {
				LogLevel.DEBUG(1, new Throwable(),"Successfully Fetched Battery Details");
				session.setAttribute("BatdetailsVector", BatdetailsVector);
				if(keyword.equals("sortprices"))
				{
				}
				else
				{
				session.setAttribute("RefreshKeyword", "refresh");
				}
				//session.setAttribute("BatteryLocalTaxVector", strcustax);
				res.sendRedirect(""+baseurldirectory+"mobile/mobbatterydetails.jsp?batterytype="+strbattype+"&vehiclemake="+strvehmake+"&vehiclemodel="+strvehmodel1+"&batterybrand="+strbattbrand+"&city="+strcity+"&pincity="+city+"&strarea="+strarea+"&strstate="+strstate+"&strpincode="+strpincode+"&strbatterycapty="+strbatterycapty+"&sortpricess="+sortpricess+"&chatmobilenumber="+strUsermobileno+"&agentname="+agent_name);
				return strRes;
				}
				else
				{
				//String RefreshKeyword="refresh";
				LogLevel.DEBUG(1, new Throwable(),"Successfully Fetched Battery Details");
				session.setAttribute("BatdetailsVector", BatdetailsVector);
				if(keyword.equals("sortprices"))
				{
				}
				else
				{
				session.setAttribute("RefreshKeyword", "refresh");
				}
				//session.setAttribute("BatteryLocalTaxVector", strcustax);
				res.sendRedirect(""+baseurldirectory+"batterydetails.jsp?batterytype="+strbattype+"&vehiclemake="+strvehmake+"&vehiclemodel="+strvehmodel1+"&batterybrand="+strbattbrand+"&city="+strcity+"&pincity="+city+"&strarea="+strarea+"&strstate="+strstate+"&strpincode="+strpincode+"&strbatterycapty="+strbatterycapty+"&sortpricess="+sortpricess+"&chatmobilenumber="+strUsermobileno+"&agentname="+agent_name);
				return strRes;
				}
			}
			//}
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
public String sentverificationcode(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort,String SMSFromAddress, String FromEmailId,String baseurldirectory) 
{ 	
	String strUsermobileno = (req.getParameter("strUsermobileno")!=null)?(req.getParameter("strUsermobileno")):"0";
	String batterymodel = (req.getParameter("batterymodel")!=null)?(req.getParameter("batterymodel")):"0";
	String batterybrand = (req.getParameter("batterybrand")!=null)?(req.getParameter("batterybrand")):"0";

	String price = (req.getParameter("price")!=null)?(req.getParameter("price")):"0";
	String withbatprice = (req.getParameter("withbatprice")!=null)?(req.getParameter("withbatprice")):"0";
	String mobileversion = (req.getParameter("mobileversion")!=null)?(req.getParameter("mobileversion")):"0";
	LogLevel.DEBUG(5, new Throwable(), "price :" + price);


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
			LogLevel.DEBUG(5, new Throwable(), "strIpAddress :" + strIpAddress);

	LogLevel.DEBUG(5, new Throwable(), "strPort :" + strPort);

	LogLevel.DEBUG(5, new Throwable(), "SMSFromAddress :" + SMSFromAddress);
	LogLevel.DEBUG(5, new Throwable(), "ThankUMsg :" + ThankUMsg);

	LogLevel.DEBUG(5, new Throwable(), "strUsermobileno :" + strUsermobileno);

			String strSMSResponse=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUMsg, strUsermobileno);
				LogLevel.DEBUG(5, new Throwable(), "strSMSResponse :" + strSMSResponse);

			if(mobileversion.equals("mobile"))
			{ 
				
				
				
				strRes=strRes+"<table width='100%' bgcolor='#FFFFFF' valign='top'><tr height='2'></tr><tr><table width='100%' bgcolor='#FFFFFF' valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: center;'> BookBattery</td><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: left;' onclick=\"javascript:closemobdiv(greyout(false));\"> X</td></tr></table><hr style='background: white; width: 95%;margin-top: 1px;margin-bottom: 1px;'></tr><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF'><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontentmob' align='center'><font size='4' color='#FFFFFF' style='padding: 2px;'>Please Enter Verification code received on SMS</font></td></tr></table></td><tr height='10'><td colspan='3' align='center' class='subheading' id='codeerrormsg'></td></tr><tr><td height='10'></td></tr></tr><tr><td width='100%' align='center'><input class='insidecontent' type='tel' autocomplete='off' name='verifycode' id='verifycode' placeholder='123456' style='width:250px;height:40px;border: 2px solid #CCC;font-size: 13px;' maxlength='6' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:checkverification('"+strUsermobileno+"','"+batterybrand+"','"+batterymodel+"','"+price+"','"+withbatprice+"');return false;} else return true;\"/></td></tr><tr><td align='center' class='popupmobilehints'><font color='#FF3333'>*</font>If you did not get verification code <br> please call <font color='#FF8C00'>+91 9603467559.</font></td></tr><tr><td height='0'></td></tr><tr><td  width='50%' align='center' ><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn' class='batterywalesubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:checkverification('"+strUsermobileno+"','"+batterybrand+"','"+batterymodel+"','"+price+"','"+withbatprice+"');\");\"> <input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  value='Close' align='left' onclick=\"javascript:closemobdiv(greyout(false));\"> </td></tr><tr><td height='15'></td></tr></table></table>^"+verificationcode+"";
				
				
			}
			else
			{
				
				strRes=strRes+"<div class='col-md-4 col-md-offset-4'> <table cellspacing='10' cellpadding='0' bgcolor='#FFFFFF'><tr height='10'><table width='100%' bgcolor='#FFFFFF' valign='top'></table><table  width='100%' bgcolor='#FFFFFF' valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 25px;font-weight: 600;text-align: center;'> BookBattery</td> <td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: left;'  href=\"javascript:closemobdiv(greyout(false));\" > <a style='color: #F96F2B;' href=\"javascript:closemobdiv(greyout(false));\"> X </a> </td></tr></table><hr style='background: white; width: 90%;margin-top: 1px;margin-bottom: 1px;'></tr><tr><td><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font style='font-size: 15px;color: #ffffff;'>Please Enter Verification code received on SMS</font></td></tr></table></td></tr><tr><td height='10'></td></tr> <table cellspacing='10' cellpadding='0'  width='100%' height='10'> <tr><td width='100%' align='center'><input class='insidecontent' type='tel' autocomplete='off' name='verifycode' id='verifycode' placeholder='123456' style='width:70%;height:38px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 0px 0px 0px 0px;' maxlength='6' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:checkverification('"+strUsermobileno+"','"+batterybrand+"','"+batterymodel+"','"+price+"','"+withbatprice+"');return false;} else return true;\"/></td></tr> </table> <table cellspacing='10' cellpadding='0'  width='100%' height='10' > <tr><td align='center' style='font-family:Verdana;font-size:12px;color:#FFFFFF; font-weight:bold;text-decoration:none;padding:1px 1px;'> <font color='#FF3333'>*</font>If you did not get verification code <br> please call <font color='#FF8C00'>+91 9603467559.</font></td></tr> </table> <tr><td height='10'></td></tr>  <table cellspacing='10' cellpadding='0'  width='100%' height='10' >  <tr><td  width='50%' align='right' ><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn' name='Submitrret' value='Submit' disable='false'  onclick=\"javascript:checkverification('"+strUsermobileno+"','"+batterybrand+"','"+batterymodel+"','"+price+"','"+withbatprice+"');\");\"> </td> <td><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  value='Close' align='left' onclick=\"javascript:closemobdiv(greyout(false));\" ></td></tr> </table> <table cellspacing='10' cellpadding='0'  width='100%' height='10' >  <tr height='26'><td colspan='3' align='center' class='subheading' id='codeerrormsg'></td></tr> </table ><tr><td height='15'></td></tr></table></div>^"+verificationcode+"";
				
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
* This method is used to insert consumer details
\* *************************************************************************************************************/
public String insertconsumerdetails(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort,String SMSFromAddress, String FromEmailId, String strsuppemaild, String baseUrl, String strsuppmobnumber,String strCMSSERVERIP,String strPDFFilePath,String strPDFRelFilePath,String baseurldirectory,String OperatorTeamCount) 
{ 	
	String strusername= (req.getParameter("username")!=null)?(req.getParameter("username")):"";
	String emailid= (req.getParameter("emailid")!=null)?(req.getParameter("emailid")):"";
	String addrs1= (req.getParameter("addrs1")!=null)?(req.getParameter("addrs1")):""; 
	LogLevel.DEBUG(5, new Throwable(), "addrs1 :" + addrs1);
	String addrs2= (req.getParameter("addrs2")!=null)?(req.getParameter("addrs2")):"";
	LogLevel.DEBUG(5, new Throwable(), "addrs2 :" + addrs2);

	//String userstate= (req.getParameter("userstate")!=null)?(req.getParameter("userstate")):"";
	String userarea= (req.getParameter("userarea")!=null)?(req.getParameter("userarea")):"";
	String usercity= (req.getParameter("usercity")!=null)?(req.getParameter("usercity")):"";
	String userzipcode= (req.getParameter("userzipcode")!=null)?(req.getParameter("userzipcode")):"";
	String strUsermobileno = (req.getParameter("mobilenumber")!=null)?(req.getParameter("mobilenumber")):"";
	String batterymodel = (req.getParameter("batterymodel")!=null)?(req.getParameter("batterymodel")):"";
	String price = (req.getParameter("price")!=null)?(req.getParameter("price")):"";
	String withbatprice = (req.getParameter("withbatprice")!=null)?(req.getParameter("withbatprice")):"";
	String verifycode = (req.getParameter("verifycode")!=null)?(req.getParameter("verifycode")):"";
	String batterybrand = (req.getParameter("batterybrand")!=null)?(req.getParameter("batterybrand")):"";
	String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
		LogLevel.DEBUG(5, new Throwable(), "city :" + city);

	String pincity = (req.getParameter("pincity")!=null)?(req.getParameter("pincity")):"";
	String strarea = (req.getParameter("strarea")!=null)?(req.getParameter("strarea")):"";
	String strstate3 = (req.getParameter("strstate")!=null)?(req.getParameter("strstate")):"";
	String strpincode = (req.getParameter("strpincode")!=null)?(req.getParameter("strpincode")):"";
	String batterytype = (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
	String vehiclemake = (req.getParameter("vehiclemake")!=null)?(req.getParameter("vehiclemake")):"";
	String vehiclemodel = (req.getParameter("vehiclemodel")!=null)?(req.getParameter("vehiclemodel")):"";
	String mobileversion = (req.getParameter("mobileversion")!=null)?(req.getParameter("mobileversion")):"0";
	String agent_name = (req.getParameter("agentname")!=null)?(req.getParameter("agentname")):"0";
	LogLevel.DEBUG(5, new Throwable(), "agent_name:" + agent_name);
	//password = password.replace("\\","\\\\"); 

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

			String StrSqlQry13 = "select retailer_id from retailer_location_mapping where pincode='"+strpincode+"' and bat_brand='"+batterybrand+"' and weekend_dealer_flag='Yes' limit 1";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQry13 :" + StrSqlQry13);
			Hashtable htretailerid12 = qm.getRow(StrSqlQry13); 
			if(htretailerid12.isEmpty())
			{
				StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+strpincode+"' and bat_brand='"+batterybrand+"' and weekend_dealer_flag='No' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);

			}
			else
				{
					StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+strpincode+"' and bat_brand='"+batterybrand+"' and weekend_dealer_flag='Yes' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);

				}
			}
			else
			{
				StrSqlQry = "select retailer_id from retailer_location_mapping where pincode='"+strpincode+"' and bat_brand='"+batterybrand+"' and weekend_dealer_flag='No' limit 1";
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

				String StrSqlQry12 = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+city+"' and area='"+strarea+"' and bat_brand='"+batterybrand+"' and weekend_dealer_flag='Yes' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry12 :" + StrSqlQry12);
				Hashtable htretailerid1 = qm.getRow(StrSqlQry12); 
				if(htretailerid1.isEmpty())
				{
					StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+city+"' and area='"+strarea+"' and bat_brand='"+batterybrand+"' and weekend_dealer_flag='No' limit 1";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);

				}
				else
				{

					StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+city+"' and area='"+strarea+"' and bat_brand='"+batterybrand+"' and weekend_dealer_flag='Yes' limit 1";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
				}
			}
			else
			{
				StrSqlQry = "select retailer_id from retailer_location_mapping where state='"+strstate1+"' and city='"+city+"' and area='"+strarea+"' and bat_brand='"+batterybrand+"' and weekend_dealer_flag='No' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);
			}

			htretailerid = qm.getRow(StrSqlQry); 
			Strretid=String.valueOf(htretailerid.get("retailer_id"));
			LogLevel.DEBUG(5, new Throwable(), "Strretid :" + Strretid);
			Strlocorpin = city;
			}
			if(Strretid.equals(null) || Strretid.equals("null") || Strretid == null || Strretid == "null" || Strretid =="")
			{
				if(mobileversion.equals("mobile"))
				{
					
					strRes="<table border='0' width='100%' height='10px' cellpadding='0' cellspacing='0'><tr><table width='100%' bgcolor='#FFFFFF' valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: center;'> BookBattery</td><td width='100%' align='center' style='COLOR:#F96F2B;     padding-right: 10px; font-size: 24px;font-weight: 600;text-align: left;' onclick=\"javascript:closemobdivindex1(greyout(false));\"> X</td></tr></table><hr style='background: white; width: 95%;margin-top: 1px;margin-bottom: 1px;'></tr></table><table border='0' width='100%' height='2px' valign'top'><tr ><td align='justify' style='padding: 10px;font-size:14px;color:#ffffff;'>No Retailers Found on Selected City.</td></tr><tr height='10'><td></td></tr><tr height='10'><td align='center'><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  value='Ok' align='left' onclick=\"javascript:closemobdivindex1(greyout(false));\"><br></td></tr><tr><td height='10'></td></tr></table>";  
					
					//strRes="<table border='0' width='100%' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td align='left'>&nbsp;<font color='#562d82'>BookBattery</td><td align='right'><a href='javascript:closemobdivindex1(greyout(false));'><img src=\"/bookbattery/images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='100%' height='2px' bgcolor='#FFFFFF' valign'top'><tr class='pages' bgcolor='#FFFFFF'><td align='center' bgcolor='#FFFFFF'>No Retailers Found on Selected City.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closemobdivindex1();' class='button4'><br></td></tr><tr height='15'></tr></table>";
				}
				else
				{
					//strRes="<table border='0' width='300px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#562d82'>BookBattery</td><td align='right'><a href='javascript:closemobdivindex1(greyout(false));'><img src=\"/bookbattery/images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='300px' height='2px' bgcolor='#FFFFFF' valign'top'><tr class='pages' bgcolor='#FFFFFF'><td align='center' bgcolor='#FFFFFF'>No Retailers Found on Selected City.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closemobdivindex1();' class='button4'><br></td></tr><tr height='15'></tr></table>";
					
					
					strRes="<div class='col-md-4 col-md-offset-4'>  <table border='0' width='300px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'>  <tr height='10'><table width='100%'  valign='top'></table><table  width='100%'  valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 25px;font-weight: 600;text-align: center;'> BookBattery</td> <td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: left;'  href=\"javascript:closemobdiv(greyout(false));\" > <a style='color: #F96F2B;' href='javascript:closemobdivindex1(greyout(false));'> X </a> </td></tr></table><hr style='background: white; width: 90%;margin-top: 1px;margin-bottom: 1px;'></tr> </table><table border='0' style='margin-top: 10px;' width='100%' height='2px'  valign'top'><tr ><td align='justify'  style='font-family:Verdana;font-size:14px;color:#FFFFFF;padding: 16px;'>No Retailers Found on Selected City.</td></tr><tr height='10'><td></td></tr><tr height='10'><td align='center'> <input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn' name='Submitrret' value='OK' disable='false' onclick=\"javascript:closemobdivindex1(greyout(false));\");\" > <br></td></tr><tr><td height='10'></td></tr></table> </div>";
					
					
				}
			}
			else
			{
			String StrSqlQrydet = "select retailer_id,retailer_name,mobile_number,email_id,address1,mobile_numberother,invoice_flag from "+strstate+"_retailers where retailer_id='"+Strretid+"'";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQrydet :" + StrSqlQrydet);

			Hashtable htretailerdetls = qm.getRow(StrSqlQrydet); 
			String retailer_id=String.valueOf(htretailerdetls.get("retailer_id"));
			String strretmobnum=String.valueOf(htretailerdetls.get("mobile_number"));
			String strretname=(String)htretailerdetls.get("retailer_name");
			String strretemail=(String)htretailerdetls.get("email_id");
			String straddress1=(String)htretailerdetls.get("address1");
			String strretothermobnum=String.valueOf(htretailerdetls.get("mobile_numberother"));
			String strinvoiceflag=String.valueOf(htretailerdetls.get("invoice_flag"));
			
			if(retailer_id.equals(null) || retailer_id.equals("null") || retailer_id == null || retailer_id == "null" || retailer_id =="")
			{
			strRes="<table border='0' width='300px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#562d82'>BookBattery</td><td align='right'><a href='javascript:closemobdivindex1(greyout(false));'><img src=\"/bookbattery/images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='300px' height='2px' bgcolor='#FFFFFF' valign'top'><tr class='pages' bgcolor='#FFFFFF'><td align='center' bgcolor='#FFFFFF'>Session Expired or Server down. please regenerate your order.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closemobdivindex1();' class='button4'><br></td></tr><tr height='15'></tr></table>";
			}
			else
			{
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

				if(batterybrand.equals("Amaron"))
				{
					ThankUMsg="Your BookBattery Battery Ord Ref No: "+verificationcode+". Battery Model: "+batterymodel+". Price: "+price+" OBRP Price: Rs. "+withbatprice+". "+strretname+" with Mob No-"+strretmobnum+" and address "+straddress1+" will fullfill your order.For any kind of Assistance please call to +919603467559.";
				}
				else
				{
					ThankUMsg="Your BookBattery Battery Ord Ref No: "+verificationcode+". Battery Model: "+batterymodel+". Price: "+price+" OBRP Price: Rs. "+withbatprice+". "+strretname+" with Mob No-"+strretmobnum+" and address "+straddress1+" will fullfill your order.For any kind of Assistance please call to +919603467559.";

				}

				
			if(batterytype.equals("Bike Batteries"))
			  {
				
					ThankUSMSMsg ="Thanks for choosing bookbattery.com .Your Battery Ord Ref No: "+verificationcode+" for Battery Model: "+batterymodel+" Price: Rs. "+price+" OBRP Price: Rs. "+withbatprice+" Please pick up the bike battery at the store.For any kind of Assistance please call to 9603467559. ";
			  }
			  else
			  {
					
					ThankUSMSMsg ="Thanks for choosing bookbattery.com .Your Battery Ord Ref No: "+verificationcode+" for Battery Model: "+batterymodel+" Price: Rs. "+price+" OBRP Price: Rs. "+withbatprice+" You will shortly receive a call.For any kind of Assistance please call to 9603467559. ";
			  }

			String strSMSResponse=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUSMSMsg, strUsermobileno);
			LogLevel.DEBUG(5, new Throwable(), "strSMSResponse :" + strSMSResponse);
			String strsub="Dear "+strusername+",\r\n\r\n"+""+ThankUMsg+"";
			String strThanks="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
			MailTrans sendverifycode=new MailTrans();
			sendverifycode.setStrSmtpHost(strdomainname);
			sendverifycode.setStrFrom(FromEmailId);
			sendverifycode.setStrTo(emailid);
			sendverifycode.setStrSubject("BookBattery Battery Details.");
			String activateLink = strsub+"\r\n\r\n"+strThanks+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
			LogLevel.DEBUG(5, new Throwable(), "activateLink :" + activateLink);
			sendverifycode.setStrText(activateLink);
			Thread mt=new MailThread(sendverifycode,"");
			mt.start();

			/* code to send SMS and Email consumer details to retailers */ 
			SendSMS sendsms1 = new SendSMS(qm);
			//String ThankUMsg1="Asistmi Battery Consumer details is "+strusername+",mob-"+strUsermobileno+". Ordered Ref N0: "+verificationcode+". Model: "+batterymodel+". Price: "+price+".";
			String ThankUMsg1="";
			String activateLink1 = "";
			String strThanks1="";
			String CustomerAddress="";

			if(addrs1.equals("") && addrs2.equals(""))
			{
				CustomerAddress="";

			}
			else
			{
				CustomerAddress=", Address: "+addrs1+","+addrs2+"";

			}
			if(batterybrand.equals("Amaron"))
			{
				ThankUMsg1="BookBattery Consumer "+strusername+" with Mob no-"+strUsermobileno+" "+CustomerAddress+" and Order Ref Number "+verificationcode+" ordered for "+batterybrand+" >> "+batterymodel+" at price: Rs."+price+" OBRP Price: Rs. "+withbatprice+". Please deliver it.For any kind of Assistance please call to 9603467559. ";
			}
			else
			{

				ThankUMsg1="BookBattery Consumer "+strusername+" with Mob no-"+strUsermobileno+" "+CustomerAddress+" and Order Ref Number "+verificationcode+" ordered for "+batterybrand+" >> "+batterymodel+" at price: Rs."+price+" OBRP Price: Rs. "+withbatprice+". Please deliver it.For any kind of Assistance please call to 9603467559. ";

			}
			LogLevel.DEBUG(5, new Throwable(), "ThankUMsg1 :" + ThankUMsg1);
			
			String ThanksmsMsg=""+strusername+" Mob No: "+strUsermobileno+" and Ord Ref No: "+verificationcode+" ordered "+batterybrand+" >> "+batterymodel+" at Price: Rs. "+price+" OBRP Price: Rs. "+withbatprice+" Please install";

			String strSMSResponse1=  sendsms1.sendSMS(strIpAddress,strPort,SMSFromAddress,ThanksmsMsg, strretmobnum);
			LogLevel.DEBUG(5, new Throwable(), "strSMSResponse1 :" + strSMSResponse1);
			String strsub1="Dear "+strretname+",\r\n\r\n"+""+ThankUMsg1+"";
			MailTrans retdetails=new MailTrans();
			retdetails.setStrSmtpHost(strdomainname);
			retdetails.setStrFrom(FromEmailId);
			retdetails.setStrTo(strretemail);
			if(batterybrand.equals("Amaron"))
			{
				strThanks1="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
				retdetails.setStrSubject("BookBattery Battery Details.");
				activateLink1 = strsub1+"\r\n\r\n"+strThanks1+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
				LogLevel.DEBUG(5, new Throwable(), "activateLink1 :" + activateLink1);
			}
			else
			{
				strThanks1="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
				retdetails.setStrSubject("BookBattery Battery Details.");
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
			
			/* code to send SMS and Email consumer details to Operators */ 
			//String ThanksuppsmsMsg="Battery Ord Ref No: "+verificationcode+" ,Cust Mob No: "+strUsermobileno+" ,Franchisee Mob No: "+strretmobnum+" ,Location : "+Strlocorpin+" .";
			String ThanksuppsmsMsg="Battery Ord Ref No: "+verificationcode+" Cust Mob No: "+strUsermobileno+" Franchisee Mob No: "+strretmobnum+" Location : "+Strlocorpin+""; 
			String strSMSResponse22 =  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThanksuppsmsMsg, strsuppmobnumber);
			LogLevel.DEBUG(5, new Throwable(), "strSMSResponse22 :" + strSMSResponse22);
			String ThankUMsgopt="BookBattery Consumer "+strusername+" with Mob no-"+strUsermobileno+", EmailID : "+emailid+"   ordered for "+batterybrand+" >> "+batterymodel+" at price: Rs."+price+" OBRP Price: Rs. "+withbatprice+" for  "+batterytype+" >> "+vehiclemake+" >> "+vehiclemodel+", Order Ref Number "+verificationcode+" Franchisee Name: "+strretname+" Franchisee Mob No: "+strretmobnum+" Franchisee EmailID: "+strretemail+" Location : "+Strlocorpin+"";

			String strsubop="Dear operator,\r\n\r\n"+""+ThankUMsgopt+"";
			String strThanksop="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
			MailTrans operator=new MailTrans();
			operator.setStrSmtpHost(strdomainname);
			operator.setStrFrom(FromEmailId);
			operator.setStrTo(strsuppemaild);
			operator.setStrSubject("BookBattery Battery Ordered Details.");
			String activateLinkop = strsubop+"\r\n\r\n"+strThanksop+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
			LogLevel.DEBUG(5, new Throwable(), "activateLinkop :" + activateLinkop);
			operator.setStrText(activateLinkop);
			Thread mtopt=new MailThread(operator,"");
			mtopt.start();

			Calendar cal = Calendar.getInstance();
			int currentMonth = cal.get(Calendar.MONTH) + 1;
			int currentYear = cal.get(Calendar.YEAR);

			String strPdfURL="";


			if(mobileversion.equals("mobile"))
			{
				//strRes="<table border='0' width='100%' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td align='left'>&nbsp;<font color='#562d82'>BookBattery</td><td align='right'><a href='javascript:closemobdivindex1(greyout(false));'><img src=\"/bookbattery/images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='100%' height='2px' bgcolor='#FFFFFF' valign'top'><tr bgcolor='#FFFFFF'><td align='justify' bgcolor='#FFFFFF' style='font-family:Verdana;font-size:11px;color:#000000;'>Your Order <b>"+verificationcode+"</b> for Battery Model: <b>"+batterymodel+"</b>, Price with out Old Battery: Rs <b>"+price+"</b>, Price with old battery return: Rs. <b>"+withbatprice+"</b> is confirmed. Our Installation Engineer will call you shortly for delivery/pickup details.</td></tr><tr height='10'><td></td></tr><tr height='10'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closemobdivindex();' class='button4'><br></td></tr><tr><td height='10'></td></tr></table>";

				strRes="<table border='0' width='100%' height='10px' cellpadding='0' cellspacing='0'><tr><table width='100%' bgcolor='#FFFFFF' valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: center;'> BookBattery</td><td width='100%' align='center' style='COLOR:#F96F2B;     padding-right: 10px; font-size: 24px;font-weight: 600;text-align: left;' onclick=\"javascript:navigatetobatterywale('"+emailid+"','"+strUsermobileno+"','"+pw+"','"+strusername+"','"+usercity+"','"+strstate3+"','battery','"+vehiclemake+"','"+vehiclemodel+"','"+batterybrand+"','"+batterymodel+"','"+price+"','"+withbatprice+"','1','"+strretname+"','"+strretmobnum+"');\"> X</td></tr></table><hr style='background: white; width: 95%;margin-top: 1px;margin-bottom: 1px;'></tr></table><table border='0' width='100%' height='2px' valign'top'><tr ><td align='justify' style='padding: 10px;font-size:14px;color:#ffffff;'>Your Order <b>"+verificationcode+"</b> for Battery Model: <b>"+batterymodel+"</b>, Price with out Old Battery: Rs <b>"+price+"</b>, Price with old battery return: Rs. <b>"+withbatprice+"</b> is confirmed. Our Installation Engineer will call you shortly for delivery/pickup details.</td></tr><tr height='10'><td></td></tr><tr height='10'><td align='center'><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  value='Ok' align='left' onclick=\"javascript:navigatetobatterywale('"+emailid+"','"+strUsermobileno+"','"+pw+"','"+strusername+"','"+usercity+"','"+strstate3+"','battery','"+vehiclemake+"','"+vehiclemodel+"','"+batterybrand+"','"+batterymodel+"','"+price+"','"+withbatprice+"','1','"+strretname+"','"+strretmobnum+"');\"><br></td></tr><tr><td height='10'></td></tr></table>";
			}
			else
			{
				//strRes="<table border='0' width='300px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#562d82'>BookBattery</td><td align='right'><a href='javascript:closemobdivindex1(greyout(false));'><img src=\"/bookbattery/images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='300px' height='2px' bgcolor='#FFFFFF' valign'top'><tr bgcolor='#FFFFFF'><td align='justify' bgcolor='#FFFFFF' style='font-family:Verdana;font-size:11px;color:#000000;'>Your Order <b>"+verificationcode+"</b> for Battery Model: <b>"+batterymodel+"</b>, Price with out Old Battery: Rs <b>"+price+"</b>, Price with old battery return: Rs. <b>"+withbatprice+"</b> is confirmed. Our Installation Engineer will call you shortly for delivery/pickup details.</td></tr><tr height='10'><td></td></tr><tr height='10'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closemobdivindex();' class='button4'><br></td></tr><tr><td height='10'></td></tr></table>";

				//strRes="<table border='0' width='300px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#562d82'>BookBattery</td><td align='right'><a href=\"javascript:navigatetobatterywale('"+emailid+"','"+strUsermobileno+"','"+pw+"','"+strusername+"','"+usercity+"','"+strstate3+"');\");\"><img src=\"/bookbattery/images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='300px' height='2px' bgcolor='#FFFFFF' valign'top'><tr bgcolor='#FFFFFF'><td align='justify' bgcolor='#FFFFFF' style='font-family:Verdana;font-size:11px;color:#000000;'>Your Order <b>"+verificationcode+"</b> for Battery Model: <b>"+batterymodel+"</b>, Price with out Old Battery: Rs <b>"+price+"</b>, Price with old battery return: Rs. <b>"+withbatprice+"</b> is confirmed. Our Installation Engineer will call you shortly for delivery/pickup details.</td></tr><tr height='10'><td></td></tr><tr height='10'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' class='button4' onclick=\"javascript:navigatetobatterywale('"+emailid+"','"+strUsermobileno+"','"+pw+"','"+strusername+"','"+usercity+"','"+strstate3+"');\");\"><br></td></tr><tr><td height='10'></td></tr></table>";
				
				
				
				strRes="<div class='col-md-4 col-md-offset-4'>  <table border='0' width='300px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'>  <tr height='10'><table width='100%'  valign='top'></table><table  width='100%'  valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 25px;font-weight: 600;text-align: center;'> BookBattery</td> <td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: left;'  href=\"javascript:closemobdiv(greyout(false));\" > <a style='color: #F96F2B;' href=\"javascript:navigatetobatterywale('"+emailid+"','"+strUsermobileno+"','"+pw+"','"+strusername+"','"+usercity+"','"+strstate3+"','battery','"+vehiclemake+"','"+vehiclemodel+"','"+batterybrand+"','"+batterymodel+"','"+price+"','"+withbatprice+"','1','"+strretname+"','"+strretmobnum+"');\"> X </a> </td></tr></table><hr style='background: white; width: 90%;margin-top: 1px;margin-bottom: 1px;'></tr> </table><table border='0' style='margin-top: 10px;' width='100%' height='2px'  valign'top'><tr ><td align='justify'  style='font-family:Verdana;font-size:14px;color:#FFFFFF;padding: 16px;'>Your Order <b>"+verificationcode+"</b> for Battery Model: <b>"+batterymodel+"</b>, Price with out Old Battery: Rs <b>"+price+"</b>, Price with old battery return: Rs. <b>"+withbatprice+"</b> is confirmed. Our Installation Engineer will call you shortly for delivery/pickup details.</td></tr><tr height='10'><td></td></tr><tr height='10'><td align='center'> <input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn' name='Submitrret' value='OK' disable='false' onclick=\"javascript:navigatetobatterywale('"+emailid+"','"+strUsermobileno+"','"+pw+"','"+strusername+"','"+usercity+"','"+strstate3+"','battery','"+vehiclemake+"','"+vehiclemodel+"','"+batterybrand+"','"+batterymodel+"','"+price+"','"+withbatprice+"','1','"+strretname+"','"+strretmobnum+"');\" > <br></td></tr><tr><td height='10'></td></tr></table> </div>";
				
				
			}
			if(strinvoiceflag.equals("yes") || strinvoiceflag == "yes")
			{
			String strSqlQryloctax ="select city_percentage,city_local_percentage  from customer_percentage where city='"+pincity+"'";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQryloctax :" + strSqlQryloctax);

			Hashtable htgetloctax = qm.getRow(strSqlQryloctax); 
			strcusper=String.valueOf(htgetloctax.get("city_percentage"));
			strcustax=String.valueOf(htgetloctax.get("city_local_percentage"));
			LogLevel.DEBUG(5,new Throwable(),"mallidipd:"+strcustax );

			String Strlocalstring ="";
			String Strvatstring="";

			String StrQryPrice = "select CAST(round("+price+"-("+price+" * ("+strcusper+" + "+strcustax+"))) AS SIGNED) as batteryprice";
			LogLevel.DEBUG(5, new Throwable(), "StrQryPrice :" + StrQryPrice);

			Hashtable htprice = qm.getRow(StrQryPrice); 
			String Strbatteryprice=String.valueOf(htprice.get("batteryprice"));
			LogLevel.DEBUG(5, new Throwable(), "Strbatteryprice :" + Strbatteryprice);

			String StrQryvatPrice = "select CAST(round("+price+" * "+strcusper+") AS SIGNED) as battervatyprice";
			LogLevel.DEBUG(5, new Throwable(), "StrQryvatPrice :" + StrQryvatPrice);

			Hashtable htvatprice = qm.getRow(StrQryvatPrice); 
			String Strbatteryvatprice=String.valueOf(htvatprice.get("battervatyprice"));
			
			if(pincity.equals("Mumbai") || pincity == "Mumbai")
			{
			Strvatstring = "12.5% VAT";
			}
			else if(pincity.equals("Pune") || pincity == "Pune")
			{
			Strvatstring = "12.5% VAT";
			}
			else
			{
			Strvatstring = "14.5% VAT";
			}
			LogLevel.DEBUG(5, new Throwable(), "Strbatteryvatprice :" + Strbatteryvatprice);

			String StrQrylocaltax = "select CAST(round("+price+" * "+strcustax+") AS SIGNED) as localtax";
			LogLevel.DEBUG(5, new Throwable(), "StrQrylocaltax :" + StrQrylocaltax);

			Hashtable htvatlocal = qm.getRow(StrQrylocaltax); 
			String Strbatterylocalprice=String.valueOf(htvatlocal.get("localtax"));
			if(pincity.equals("Mumbai") || pincity == "Mumbai")
			{
			Strlocalstring = "5.5% Locala";
			}
			else if(pincity.equals("Pune") || pincity == "Pune")
			{
			Strlocalstring = "3.5% Octroi";
			}

			LogLevel.DEBUG(5, new Throwable(), "Strbatterylocalprice :" + Strbatterylocalprice);

			String verifiycode ="ASSIST-AMR-BT"+verificationcode1+"";
			LogLevel.DEBUG(5, new Throwable(), "verificationcode :" + verificationcode);

			//String excelName = "invoice.pdf";
			String excelName = "ASPL_"+strusername+"_"+verifiycode+"_Invoice.pdf";
			String invoice ="ASSIST-AMR-BT"+verificationcode1+"";
			
			strPDFFilePath = strPDFFilePath +"/"+ currentYear + "/" +currentMonth;
			File excelUploadPathCreate = new File(strPDFFilePath);

			if(excelUploadPathCreate.mkdirs())
			{
				LogLevel.DEBUG(3,new Throwable()," PDF for invoice	Name directory created :  "	+ excelUploadPathCreate);
			}
			
			strPDFRelFilePath	= strPDFRelFilePath +"/"+ currentYear + "/" +currentMonth;

			String excelFile = excelUploadPathCreate+"/"+excelName;
			File file=new File(excelFile); 
			if(!file.exists()) 
				file.createNewFile(); 
			FileWriter fw=new FileWriter(file);	
			GenerateExcelinvoice generateExcel	= new GenerateExcelinvoice();
			generateExcel.writeToPdf(strusername,pincity,verificationcode,invoice,batterybrand,batterymodel,price,Strbatteryprice,Strbatteryvatprice,Strvatstring,Strbatterylocalprice,Strlocalstring,excelFile,strCMSSERVERIP);

			final String username = "admin@ngit.in";
			final String password = "adminNGIT";
			String subject="BookBattery Customer Invoice for "+strusername+""; 
		
			Properties props = new Properties();
			props.put("mail.smtp.auth", true);
			props.put("mail.smtp.starttls.enable", true);
			props.put("mail.smtp.host", "smtp.net4india.com");
			props.put("mail.smtp.port", "587");

			Session session1 = Session.getInstance(props,
			new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
						}
					});

			 Message msg = new MimeMessage(session1);  
            
              msg.setFrom(new InternetAddress(FromEmailId));  
           // create the message part   
              MimeBodyPart messageBodyPart = new MimeBodyPart();  
          //fill message  
              messageBodyPart.setText("Dear "+strretname+",\r\n\r\nThis is an automated invoice generated by www.bookbattery.com  for the battery ordered by "+strusername+". Please find the attachment for invoice details. \r\n\r\n\r\n Thanks & Regards,\r\n BookBattery Team.\r\n www.BookBattery.com.");  
              Multipart multipart = new MimeMultipart();  
              multipart.addBodyPart(messageBodyPart);  
           // Part two is attachment  
              messageBodyPart = new MimeBodyPart();  
              DataSource source =   
                new FileDataSource(file);  
              messageBodyPart.setDataHandler(  
                new DataHandler(source));  
             // messageBodyPart.setFileName(file); 
			  messageBodyPart.setFileName(""+strusername+"_invoice.pdf");
              multipart.addBodyPart(messageBodyPart);  
          // Put parts in message  
              msg.setContent(multipart);  
           
  
              //msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(SMTP_TO_ADDRESS));  
			  msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(strretemail));
  
              msg.setSubject(subject); 
			  Transport.send(msg);  

			LogLevel.DEBUG(5,new Throwable(),"trans :"+ msg );

			strPdfURL="http://"+strCMSSERVERIP+""+baseurldirectory+"userdata/billing/consumerinvoices/"+currentYear+"/"+currentMonth+"/"+excelName;
			LogLevel.DEBUG(5, new Throwable(), "strPdfURL : " + strPdfURL);
			}

			if(agent_name == null || agent_name == "0" || agent_name == "" || agent_name.equals("0") || agent_name.equals(""))
			{
					String StrSqlQryOperatorname = "select agent_name from battery_order_details where (NOT agent_name = '0' or agent_name = ' ' ) order by creation_date desc limit 1";
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
					String strSqlQry = "insert into battery_order_details(ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_verif_code,retailer_name,retailer_mobilnumber,retailer_emailis,city,battery_brand,battery_model,price,area,pincode,pdfurl,bat_type,veh_name,veh_model,witholdbatprice,creation_date)values(NULL,'"+verificationcode+"','"+strusername+"','"+strUsermobileno+"','"+emailid+"','"+verifycode+"','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+pincity+"','"+batterybrand+"','"+batterymodel+"','"+price+"','"+strarea+"','"+strpincode+"','"+strPdfURL+"','"+batterytype+"','"+vehiclemake+"','"+vehiclemodel+"','"+withbatprice+"',now())";
					LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
					int reslt = qm.executeUpdate(strSqlQry);

				}
				else
				{
					String strSqlQry = "insert into battery_order_details(ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_verif_code,retailer_name,retailer_mobilnumber,retailer_emailis,city,battery_brand,battery_model,price,area,pincode,pdfurl,bat_type,veh_name,veh_model,witholdbatprice,creation_date,agent_name)values(NULL,'"+verificationcode+"','"+strusername+"','"+strUsermobileno+"','"+emailid+"','"+verifycode+"','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+pincity+"','"+batterybrand+"','"+batterymodel+"','"+price+"','"+strarea+"','"+strpincode+"','"+strPdfURL+"','"+batterytype+"','"+vehiclemake+"','"+vehiclemodel+"','"+withbatprice+"',now(),'"+agent_name+"')";
					LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
					int reslt = qm.executeUpdate(strSqlQry);
				}

			String strSqlQryleads ="insert into leads(lead_id,retailer_name,retailer_id,deal_id,email_id,vendor_id,product_id,product_name,mobile_number,vendor_name,promotion_code,price,subcategory_id,category_id,creation_date,created_by,updated_date,updated_by)values(NULL,'"+strretname+"','"+Strretid+"','0','"+emailid+"','22','0','undefined','"+strUsermobileno+"','"+batterybrand+"','0','0','0','0',now(),'ngit',now(),'ngit')"; 
			LogLevel.DEBUG(5,new Throwable()," :strSqlQryleads "+strSqlQryleads);
			int resltleads = qm.executeUpdate(strSqlQryleads);

			String StrSqlQryuser = "select email_id from batterywale_user_profile where email_id='"+emailid+"'";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQryuser :" + StrSqlQryuser);

			Hashtable htruser = qm.getRow(StrSqlQryuser); 
			String Stremailid=String.valueOf(htruser.get("email_id"));
			LogLevel.DEBUG(5, new Throwable(), "Stremailid :" + Stremailid);

			if(Stremailid.equals(null) || Stremailid.equals("null") || Stremailid == null || Stremailid == "null" || Stremailid =="")
			{
			addrs1 = addrs1.replace("'","\\'");
			addrs2 = addrs2.replace("'","\\'");

				//String strSqlQryuserprof = "insert into user_profile(user_id,email_id,mobile_number,password,first_name,city,state,status,creation_date,created_by)values(NULL,'"+emailid+"','"+strUsermobileno+"','"+password+"','"+strusername+"','"+city+"','"+strstate+"','active',now(),'ngit')";
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
			retdetails1.setStrSubject("Thank You for Ordering the Battery.");
			String activateLink12 = strsub11+"\r\n\r\n"+strThanks11+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
			LogLevel.DEBUG(5, new Throwable(), "activateLink12 :" + activateLink12);
			retdetails1.setStrText(activateLink12);
			Thread mt11=new MailThread(retdetails1,"");
			mt11.start();*/
		}
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
