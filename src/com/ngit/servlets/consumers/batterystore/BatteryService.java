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

  
public class BatteryService extends HttpServlet 
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
		String OperatorTeamCount = (propsMOPConfig.getProperty("OperatorTeamCount")!=null)?(propsMOPConfig.getProperty("OperatorTeamCount")):"";
		LogLevel.DEBUG(5, new Throwable(), "OperatorTeamCount :" + OperatorTeamCount);

	/* ****************************************************************************************\
	* This action is used to get send mobile verification code details.
	\* *****************************************************************************************/		
	if(strWhatToDo.equalsIgnoreCase("sentverificationcode"))
	{ 
		try
		{
			String strRes=sentverificationcode(req,res,session,domainname,strIpAddress,strPort,SMSFromAddress,FromEmailId,OperatorTeamCount);
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
	* This action is used to  check retailer details
	\* *****************************************************************************************/		
	if(strWhatToDo.equalsIgnoreCase("checkserviceengineeravail"))
		{ 
			try
			{
				String strRes=checkserviceengineeravail(req,res,session);
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
				String strRes=insertconsumerdetails(req,res,session,domainname,strIpAddress,strPort,SMSFromAddress,FromEmailId,strsuppemaild,baseUrl,strsuppmobnumber,OperatorTeamCount);
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
	if(strWhatToDo.equalsIgnoreCase("insertconsumerdetailswithoutsms"))
		{ 
			try
			{
				String strRes=insertconsumerdetailswithoutsms(req,res,session,domainname,strIpAddress,strPort,SMSFromAddress,FromEmailId,strsuppemaild,baseUrl,strsuppmobnumber);
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
/* *************************************************************************************************************\
* This method is used to send  verification to user email id and mobile number when user enters mobile number in order now battery 
\* *************************************************************************************************************/
public String sentverificationcode(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort,String SMSFromAddress, String FromEmailId,String OperatorTeamCount) 
{ 	
	String username = (req.getParameter("username")!=null)?(req.getParameter("username")):"";
	LogLevel.DEBUG(5,new Throwable(),"username: "+username);

	String strUsermobileno = (req.getParameter("usermobilenumber")!=null)?(req.getParameter("usermobilenumber")):"";
	LogLevel.DEBUG(5,new Throwable(),"strUsermobileno: "+strUsermobileno);

	String keyword = (req.getParameter("keyword")!=null)?(req.getParameter("keyword")):"";
	LogLevel.DEBUG(5,new Throwable(),"keyword: "+keyword);

	String mobileversion = (req.getParameter("mobileversion")!=null)?(req.getParameter("mobileversion")):"";
	LogLevel.DEBUG(5,new Throwable(),"mobileversion: "+mobileversion);


	String strRes="";
	String visitorcity="";
	String strstate="";
	String agent_name="";
	try
	{
			
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

						String strSqlQryvisit = "insert into visitors_orders(vis_ord_id,bat_type,veh_make,veh_model,bat_brand,bat_capacity,state,city,area,pincode,mobile_number,option_type,keynumber,visitors_comments,creation_date,agent_name)values(NULL,'','','','','','','','','','"+strUsermobileno+"','"+keyword+"','','',now(),'"+agent_name+"')";
						LogLevel.DEBUG(5,new Throwable(),"strSqlQryvisit: "+strSqlQryvisit);
						int reslt = qm.executeUpdate(strSqlQryvisit);
				
				}
				else
				{
						agent_name=(String)htgetoperatorname1.get("agent_name");
						LogLevel.DEBUG(5, new Throwable(), "agent_name :" + agent_name);

						String strSqlQryvisit = "insert into visitors_orders(vis_ord_id,bat_type,veh_make,veh_model,bat_brand,bat_capacity,state,city,area,pincode,mobile_number,option_type,keynumber,visitors_comments,creation_date,agent_name)values(NULL,'','','','','','','','','','"+strUsermobileno+"','"+keyword+"','','',now(),'"+agent_name+"')";
						LogLevel.DEBUG(5,new Throwable(),"strSqlQryvisit: "+strSqlQryvisit);
						int reslt = qm.executeUpdate(strSqlQryvisit);

				}

			

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
				strRes=agent_name+"|"+verificationcode;
			}
			else
			{
				strRes=agent_name+"|"+verificationcode;
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
public String checkserviceengineeravail(HttpServletRequest req , HttpServletResponse res,HttpSession session) 
{ 	

	String state = (req.getParameter("state")!=null)?(req.getParameter("state")):"";
	String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
	String area = (req.getParameter("area")!=null)?(req.getParameter("area")):"";
	String pincode = (req.getParameter("pincode")!=null)?(req.getParameter("pincode")):"";
	String mobileversion = (req.getParameter("mobileversion")!=null)?(req.getParameter("mobileversion")):"";

	//password = password.replace("\\","\\\\");  

	String strRes="";
	String strstate="";
	String strstate1="";
	String StrSqlQry ="";
	String Strretid="";
	String Strlocorpin="";
	try
	{
			if(city.equals("0") || city.equals("") || city == "")
			{
				StrSqlQry = "select service_id from service_engineer_detls where pincode='"+pincode+"' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);

				Hashtable htretailerid = qm.getRow(StrSqlQry); 
				Strretid=String.valueOf(htretailerid.get("service_id"));
			
				String StrSqlQrystate1 = "select state,city from battery_pincode where pincode='"+pincode+"'";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQrystate1 :" + StrSqlQrystate1);

				Hashtable htgetstate1 = qm.getRow(StrSqlQrystate1); 
				strstate1=(String)htgetstate1.get("state");	
				String strcity=(String)htgetstate1.get("city");
				if(strcity == null)
				{
					city = "";
				}
				else
				{
					city=strcity;
					strstate=strstate1.trim().replaceAll(" ", "_"); 
					LogLevel.DEBUG(5, new Throwable(), "strstate :" + strstate);
					Strlocorpin = pincode;
				}
			}
			else
			{	
				strstate1 = state;
				strstate=strstate1.trim().replaceAll(" ", "_"); 
				LogLevel.DEBUG(5, new Throwable(), "strstate :" + strstate);
				
				StrSqlQry = "select service_id from service_engineer_detls where state='"+strstate1+"' and city='"+city+"' and area='"+area+"' limit 1";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQry :" + StrSqlQry);

				Hashtable htretailerid = qm.getRow(StrSqlQry); 
				Strretid=String.valueOf(htretailerid.get("service_id"));
				LogLevel.DEBUG(5, new Throwable(), "Strretid :" + Strretid);
				Strlocorpin = city;
			}
			if(Strretid.equals(null) || Strretid.equals("null") || Strretid == null || Strretid == "null" || Strretid =="")
			{
				if(mobileversion.equals("mobile"))
				{
					//strRes="<table border='0' width='100%' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td align='left'>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closemobdiv(greyout(false));'><img src=\"bookbattery/images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='100%' height='2px' bgcolor='#FFFFFF' valign'top'><tr class='pages' bgcolor='#FFFFFF'><td align='center' bgcolor='#FFFFFF'>No service is provided for selected area.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closemobdiv();' class='button4'><br></td></tr><tr height='15'></tr></table>";
					
					strRes="Franchisee Details are found";

				}
				else
				{
					//strRes="<table border='0' width='300px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closemobdiv(greyout(false));'><img src=\"bookbattery/images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='300px' height='2px' bgcolor='#FFFFFF' valign'top'><tr class='pages' bgcolor='#FFFFFF'><td align='center' bgcolor='#FFFFFF'>No service is provided for selected area.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closemobdiv();' class='button4'><br></td></tr><tr height='15'></tr></table>";
					strRes="Franchisee Details are found";

				}
			}
			else
			{	
				strRes="Franchisee Details are found";
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
public String insertconsumerdetails(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort,String SMSFromAddress, String FromEmailId, String strsuppemaild, String baseUrl, String strsuppmobnumber,String OperatorTeamCount) 
{ 	
	String strusername= (req.getParameter("username")!=null)?(req.getParameter("username")):"";
	LogLevel.DEBUG(5, new Throwable(), "strusername :" + strusername);

	String strUsermobileno= (req.getParameter("usermobileno")!=null)?(req.getParameter("usermobileno")):"";
	LogLevel.DEBUG(5, new Throwable(), "strUsermobileno :" + strUsermobileno);

	String mobileversion = (req.getParameter("mobileversion")!=null)?(req.getParameter("mobileversion")):"";
	LogLevel.DEBUG(5, new Throwable(), "mobileversion :" + mobileversion);

	String strretname = (req.getParameter("retailername")!=null)?(req.getParameter("retailername")):"";
	LogLevel.DEBUG(5, new Throwable(), "strretname :" + strretname);

	String strretmobile = (req.getParameter("retmobilenumber")!=null)?(req.getParameter("retmobilenumber")):"";
	LogLevel.DEBUG(5, new Throwable(), "strretmobile :" + strretmobile);

	String engineer_email_id = (req.getParameter("retaileremailid")!=null)?(req.getParameter("retaileremailid")):"";
	LogLevel.DEBUG(5, new Throwable(), "engineer_email_id :" + engineer_email_id);

	String agentname = (req.getParameter("agentname")!=null)?(req.getParameter("agentname")):"";
	LogLevel.DEBUG(5, new Throwable(), "agentname :" + agentname);


	//password = password.replace("\\","\\\\");  

	String strRes="";
	
	String StrSqlQry ="";

	try
	{
		
			
			
			//String strretmobile="9945966973";
			/*String strretmobile="9963754545";
			LogLevel.DEBUG(5, new Throwable(), "strretmobile :" + strretmobile);

			String strretname="ManjusreeBattery";
			LogLevel.DEBUG(5, new Throwable(), "strretname :" + strretname);

			String engineer_email_id="manjusreebattery@gmail.com";
			LogLevel.DEBUG(5, new Throwable(), "engineer_email_id :" + engineer_email_id);


			/* code to send SMS and Email to service engineer */ 
			CompareTrans ct = new CompareTrans(qm);
			SendSMS sendsms = new SendSMS(qm);
			
			//String ThankUMsg="BookBattery Customer : "+strusername+" , Mobile: "+strUsermobileno+"  has booked for battery service.Please check details and contact customer.Thanks, BookBattery Support Team.";
			String ThankUMsg="BookBattery Customer: "+strusername+" , Mobile: "+strUsermobileno+" has booked for battery service.Please check details and contact customer.Thanks, BookBattery Support Team.";
			String strSMSResponse=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUMsg, strretmobile);

			String ThankUMsgengineer="Customer Name "+strusername+" with Mob no-"+strUsermobileno+" has booked for battery service. Please check details and contact customer.";
			LogLevel.DEBUG(5, new Throwable(), "ThankUMsgengineer :" + ThankUMsgengineer);

			String strsubengineer="Dear "+strretname+",\r\n\r\n"+""+ThankUMsgengineer+"";
			String strThanksengineer="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
			MailTrans serviceengineer=new MailTrans();
			serviceengineer.setStrSmtpHost(strdomainname);
			serviceengineer.setStrFrom(FromEmailId);
			serviceengineer.setStrTo(engineer_email_id);
			serviceengineer.setStrSubject("BookBattery Battery Service Details.");
			String activateLinkengineer = strsubengineer+"\r\n\r\n"+strThanksengineer+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
			LogLevel.DEBUG(5, new Throwable(), "activateLinkengineer :" + activateLinkengineer);
			serviceengineer.setStrText(activateLinkengineer);
			Thread mtengineer=new MailThread(serviceengineer,"");
			mtengineer.start();

			
			  
			 /* code to send SMS and Email support team*/ 
			String ThanksuppsmsMsg="BookBattery Customer: "+strusername+" , Mobile: "+strUsermobileno+" has booked for battery service.Please check details and contact customer.Thanks, BookBattery Support Team.";  
			String strSMSResponse22 =  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThanksuppsmsMsg, strsuppmobnumber);
			LogLevel.DEBUG(5, new Throwable(), "strSMSResponse22 :" + strSMSResponse22);
			
			String ThankUMsgopt="Customer Name "+strusername+" with Mob no-"+strUsermobileno+" has booked for battery service. Please check details and contact customer.";
			LogLevel.DEBUG(5, new Throwable(), "ThankUMsgopt :" + ThankUMsgopt);

			String strsubop="Dear operator,\r\n\r\n"+""+ThankUMsgopt+"";
			String strThanksop="Thanks & Regards,"+"\r\n"+"BookBattery Support Team."; 
			MailTrans operator=new MailTrans();
			operator.setStrSmtpHost(strdomainname);
			operator.setStrFrom(FromEmailId);
			operator.setStrTo(strsuppemaild);
			operator.setStrSubject("BookBattery Battery Service Details.");
			String activateLinkop = strsubop+"\r\n\r\n"+strThanksop+"\r\n\rNote: This is an auto-generated response. Kindly do not reply on this mail.\nConfidentiality Information and Disclaimer:\nThis communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
			LogLevel.DEBUG(5, new Throwable(), "activateLinkop :" + activateLinkop);
			operator.setStrText(activateLinkop);
			Thread mtopt=new MailThread(operator,"");
			mtopt.start();

			if(mobileversion.equals("mobile"))
			{
				
				
				strRes="<table width='100%' bgcolor='#FFFFFF' valign='top'><tr height='2'></tr><tr><table width='100%' bgcolor='#FFFFFF' valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: center;'> BookBattery</td><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: left;' onclick=\"javascript:closemobdiv(greyout(false));\"> X</td></tr></table></tr><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF'><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontentmob' align='center'><font size='4' color='#FF8C00' style='padding: 2px;'>Successfully registered your battery service details. Customer Support Engineer will schedule a battery service appointment with you.</font></td></tr></table></td><tr><td height='0'></td></tr><tr><td  width='50%' align='center' ><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: -40px;margin-top: 5px;' type='button' class='btn'  value='Close' align='left' onclick=\"javascript:closemobdiv(greyout(false));\"> </td></tr><tr><td height='15'></td></tr></table></table>";
				
			}
			else
			{
				
				
				strRes="<div class='col-md-4 col-md-offset-4'> <table cellspacing='10' cellpadding='0' bgcolor='#FFFFFF'><tr height='10'><table width='100%' bgcolor='#FFFFFF' valign='top'></table><table  width='100%' bgcolor='#FFFFFF' valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 25px;font-weight: 600;text-align: center;'> BookBattery</td> <td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: left;'  href=\"javascript:closemobdiv(greyout(false));\" > <a style='color: #F96F2B;' href=\"javascript:closemobdiv(greyout(false));\"> X </a> </td></tr></table><hr style='background: white; width: 90%;margin-top: 1px;margin-bottom: 1px;'></tr><tr><td><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font style='font-size: 15px;color: #ffffff;'>Successfully registered your battery service details. Customer Support Engineer will schedule a battery service appointment with you.</font></td></tr></table></td></tr><tr><td height='10'></td></tr>   <tr><td height='10'></td></tr>  <table cellspacing='10' cellpadding='0'  width='100%' height='10' >  <tr> <td><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 155px;margin-top: 5px;' type='button' class='btn'  value='Close' align='left' onclick=\"javascript:closemobdiv(greyout(false));\" ></td></tr> </table> <table cellspacing='10' cellpadding='0'  width='100%' height='10' >  <tr height='26'><td colspan='3' align='center' class='subheading' id='codeerrormsg'></td></tr> </table ><tr><td height='15'></td></tr></table></div>"; 
				
			}

			String strSqlQry = "insert into battery_service(bat_service_id,customer_name,customer_mobilnumber,support_name,support_mobilnumber,creation_date,agent_name,order_status)values(NULL,'"+strusername+"','"+strUsermobileno+"','"+strretname+"','"+strretmobile+"',now(),'"+agentname+"','confirmed')";
			LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
			int reslt = qm.executeUpdate(strSqlQry);

			

			/* code to send SMS  to customer regarding battery service */ 
			//String ThankUMsguser="Thank You for Booking the Battery Service in BookBattery.com. Our trained Customer Support Engineer Name: "+strretname+" with Contact No: "+strretmobile+" will call you to set the time of service.For any kind of Assistance please call to 919603467559."; 
			String BatteryWaleSupportNumber="9603467559";
			String ThankUMsguser="Thank you for booking the Battery Service at BookBattery.com. Our trained customer support engineer name: "+strretname+" with contact no: "+strretmobile+" will call you to set the time of service. For any kind of assistance please call to "+BatteryWaleSupportNumber+"";
		
			String strSMSResponse2=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUMsguser, strUsermobileno);
			LogLevel.DEBUG(5, new Throwable(), "strSMSResponse2 :" + strSMSResponse2);
			
			
		
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
public String insertconsumerdetailswithoutsms(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort,String SMSFromAddress, String FromEmailId, String strsuppemaild, String baseUrl, String strsuppmobnumber) 
{ 	
	String strusername= (req.getParameter("username")!=null)?(req.getParameter("username")):"";
	String emailid= (req.getParameter("emailid")!=null)?(req.getParameter("emailid")):"";
	String addrs1= (req.getParameter("addrs1")!=null)?(req.getParameter("addrs1")):""; 
	String addrs2= (req.getParameter("addrs2")!=null)?(req.getParameter("addrs2")):"";
	//String userstate= (req.getParameter("userstate")!=null)?(req.getParameter("userstate")):"";
	String userarea= (req.getParameter("userarea")!=null)?(req.getParameter("userarea")):"";
	String usercity= (req.getParameter("usercity")!=null)?(req.getParameter("usercity")):"";
	String userzipcode= (req.getParameter("userzipcode")!=null)?(req.getParameter("userzipcode")):"";
	String inmonth = (req.getParameter("inmonth")!=null)?(req.getParameter("inmonth")):"";
	String inyear = (req.getParameter("inyear")!=null)?(req.getParameter("inyear")):"";
	String strUsermobileno = (req.getParameter("mobilenumber")!=null)?(req.getParameter("mobilenumber")):"";
	String verifycode = (req.getParameter("verifycode")!=null)?(req.getParameter("verifycode")):"";
	String batterytype = (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
	String vehiclename = (req.getParameter("vehiclename")!=null)?(req.getParameter("vehiclename")):"";
	String vehmodel = (req.getParameter("vehmodel")!=null)?(req.getParameter("vehmodel")):"";
	String batterybrand = (req.getParameter("batterybrand")!=null)?(req.getParameter("batterybrand")):"";
	String batterymodel = (req.getParameter("batterymodel")!=null)?(req.getParameter("batterymodel")):"";
	String state = (req.getParameter("state")!=null)?(req.getParameter("state")):"";
	String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
	String area = (req.getParameter("area")!=null)?(req.getParameter("area")):"";
	String pincode = (req.getParameter("pincode")!=null)?(req.getParameter("pincode")):"";
	String mobileversion = (req.getParameter("mobileversion")!=null)?(req.getParameter("mobileversion")):"";
	String serviceneeddate= (req.getParameter("serviceneeddate")!=null)?(req.getParameter("serviceneeddate")):"";

	//password = password.replace("\\","\\\\");  

	String strRes="";
	String strstate="";
	String strstate1="";
	String StrSqlQry ="";
	String Strretid="";
	String Strlocorpin="";
	try
	{
			SimpleDateFormat sdfDate=new SimpleDateFormat("dd-MM-yyyy");
			Date serviceDate=sdfDate.parse(serviceneeddate); 

			SimpleDateFormat sdfString=new SimpleDateFormat("yyyy-MM-dd");
			String strserviceDate=sdfString.format(serviceDate); 
			LogLevel.DEBUG(5, new Throwable(), "strserviceDate :"+ strserviceDate);


			if(city.equals("0") || city.equals("") || city == "")
			{
				String StrSqlQrystate1 = "select state,city from battery_pincode where pincode='"+pincode+"'";
				LogLevel.DEBUG(5, new Throwable(), "StrSqlQrystate1 :" + StrSqlQrystate1);

				Hashtable htgetstate1 = qm.getRow(StrSqlQrystate1); 
				strstate1=(String)htgetstate1.get("state");	
				String strcity=(String)htgetstate1.get("city");
				if(strcity == null)
				{
					city = "";
				}
				else
				{
					city=strcity;
					strstate=strstate1.trim().replaceAll(" ", "_"); 
					LogLevel.DEBUG(5, new Throwable(), "strstate :" + strstate);
					Strlocorpin = pincode;
				}
			}
			else
			{
				strstate1 = state;
				strstate=strstate1.trim().replaceAll(" ", "_"); 
				LogLevel.DEBUG(5, new Throwable(), "strstate :" + strstate);
				Strlocorpin = city;
			}

			if(mobileversion.equals("mobile"))
			{
				
				
				strRes="<table width='100%'  valign='top'><tr height='2'></tr><tr><table width='100%' valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: center;'> BookBattery</td><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: left;' onclick=\"javascript:closemobdiv(greyout(false));\"> X</td></tr></table><hr style='background: white; width: 95%;margin-top: 1px;margin-bottom: 1px;'></tr><table width='100%'  cellspacing='0' cellpadding='0' ><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontentmob' align='center'><font size='4' color='#FFFFFF' style='padding: 2px;'>Successfully registered your battery service details. Tech Support member will schedule a battery service appointment with you.</font></td></tr></table></td><tr><td height='0'></td></tr><tr><td  width='50%' align='center' ><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  value='Close' align='left' onclick='closemobdivbookservice();'> </td></tr><tr><td height='15'></td></tr></table></table>";
				
			}
			else
			{
				//strRes="<table border='0' width='350px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobdivbookservice(greyout(false));\" style='color:#848484;text-decoration:none;'><img src='./bookbattery/images/sgpopup2.png' alt='BookBattery.com'>X&nbsp;&nbsp;</a></b></td></tr></table><table border='0' width='330px' height='2px' bgcolor='#FFFFFF' valign'top' align='center'><tr bgcolor='#FFFFFF'><td align='justify' bgcolor='#FFFFFF' style='font-family:Verdana;font-size:11px;color:#000000;padding: 12px 5px 9px 17px;'>Successfully registered your Battery service details.<br>Thank You for Booking the Battery Service.Our Tech Support member will schedule a battery service appointment with you.</td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closemobdivbookservice();' class='button4'><br></td></tr><tr height='15'></tr></table>";
				
				strRes="<div class='col-md-4 col-md-offset-4'> <table cellspacing='10' cellpadding='0' bgcolor='#FFFFFF'><tr height='10'><table width='100%' bgcolor='#FFFFFF' valign='top'></table><table  width='100%' bgcolor='#FFFFFF' valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 25px;font-weight: 600;text-align: center;'> BookBattery</td> <td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: left;'  href=\"javascript:closemobdiv(greyout(false));\" > <a style='color: #F96F2B;' href=\"javascript:closemobdiv(greyout(false));\"> X </a> </td></tr></table><hr style='background: white; width: 90%;margin-top: 1px;margin-bottom: 1px;'></tr><tr><td><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font style='font-size: 15px;color: #ffffff;'>Successfully registered your battery service details. Tech Support member will schedule a battery service appointment with you.</font></td></tr></table></td></tr><tr><td height='10'></td></tr>   <tr><td height='10'></td></tr>  <table cellspacing='10' cellpadding='0'  width='100%' height='10' >  <tr> <td  align='center' ><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  value='Close' align='center' onclick='closemobdivbookservice();' ></td></tr> </table> <table cellspacing='10' cellpadding='0'  width='100%' height='10' >  <tr height='26'><td colspan='3' align='center' class='subheading' id='codeerrormsg'></td></tr> </table ><tr><td height='15'></td></tr></table></div>"; 
				
			}
			String strSqlQry = "insert into battery_service(bat_service_id,bat_ins_month,bat_ins_year,veh_type,veh_model,bat_type,bat_brand,bat_model,customer_name,customer_mobilnumber,support_name,support_mobilnumber,city,area,pincode,service_need_date,customer_emailid,customer_address1,customer_address2,creation_date)values(NULL,'"+inmonth+"','"+inyear+"','"+vehiclename+"','"+vehmodel+"','"+batterytype+"','"+batterybrand+"','"+batterymodel+"','"+strusername+"','"+strUsermobileno+"','','','"+city+"','"+area+"','"+pincode+"','"+strserviceDate+"','"+emailid+"','"+addrs1+"','"+addrs2+"',now())";
			LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
			int reslt = qm.executeUpdate(strSqlQry);

			String StrSqlQryuser = "select email_id from batterywale_user_profile where email_id='"+emailid+"'";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQryuser :" + StrSqlQryuser);

			Hashtable htruser = qm.getRow(StrSqlQryuser); 
			String Stremailid=String.valueOf(htruser.get("email_id"));
			LogLevel.DEBUG(5, new Throwable(), "Stremailid :" + Stremailid);
			
			/*following code is for generating the random number */
			String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";

			String pw = "";
				for (int i=0; i<PASSWORD_LENGTH; i++)
				{
					int index = (int)(RANDOM.nextDouble()*letters.length());
					pw += letters.substring(index, index+1);
				}
			LogLevel.DEBUG(5, new Throwable(), "pw :" + pw);

			/*code to generate the random number ends here */

			if(Stremailid.equals(null) || Stremailid.equals("null") || Stremailid == null || Stremailid == "null" || Stremailid =="")
			{
				addrs1 = addrs1.replace("'","\\'");
				addrs2 = addrs2.replace("'","\\'");
				//String strSqlQryuserprof = "insert into user_profile(user_id,email_id,mobile_number,password,first_name,city,state,status,creation_date,created_by)values(NULL,'"+emailid+"','"+strUsermobileno+"','"+password+"','"+strusername+"','"+city+"','"+strstate+"','active',now(),'ngit')";
				String strSqlQryuserprof = "insert into batterywale_user_profile(user_id,email_id,mobile_number,password,name,address,address1,zipcode,city,state,mobile_verify_code,creation_date,created_by) values(NULL,'"+emailid+"','"+strUsermobileno+"','"+pw+"','"+strusername+"','"+addrs1+"','"+addrs2+"','"+userzipcode+"','"+city+"','"+strstate1+"','"+verifycode+"',now(),'ngit')";

				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQryuserprof);
				int reslt1 = qm.executeUpdate(strSqlQryuserprof);
			}
			else
			{
				String strSqlQryupdatepassword = "update batterywale_user_profile set password='"+pw+"' where email_id='"+emailid+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQryupdatepassword : "+strSqlQryupdatepassword);
				int reslt12 = qm.executeUpdate(strSqlQryupdatepassword);
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
