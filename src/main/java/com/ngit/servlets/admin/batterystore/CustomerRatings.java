/***********************************************************************		
	NGIT Confidential. 
	@File Name   : BatteryHome.java 
	@Description : This Servlet is used to select the Battery Details.
	@Author	     : Sai Krishna Daddala.
	@Date        : 30th August 2013
******************************************************************/ 
 
package com.ngit.servlets.admin.batterystore; 
 
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
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.io.*;
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
import java.net.URLDecoder;  
import java.net.URLEncoder; 
import java.util.List;
import java.io.*;
import java.util.Random;
import java.security.SecureRandom;
import javax.activation.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

  
public class CustomerRatings extends HttpServlet 
{  
	private ServletContext context; 
	QueryManager qm;
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
		String struserName=(String)session.getAttribute("sesBatteryUserLogin"); 
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
		ServletOutputStream out=res.getOutputStream();
		
		String strIpAddress =(propsMOPConfig.getProperty("TRANSMITTER_IP_ADDR")!=null)?(propsMOPConfig.getProperty("TRANSMITTER_IP_ADDR")):"";
		String strPort=(propsMOPConfig.getProperty("TRANSMITTER_IP_PORT")!=null)?(propsMOPConfig.getProperty("TRANSMITTER_IP_PORT")):"9910";
		String SMSFromAddress=(propsMOPConfig.getProperty("SMSFromAddress")!=null)?(propsMOPConfig.getProperty("SMSFromAddress")):"";
		String FromEmailId=(propsMOPConfig.getProperty("SupportEmailId")!=null)?(propsMOPConfig.getProperty("SupportEmailId")):"";
		String baseUrl =  propsMOPConfig.getProperty("publicUrl");
		String domainname = (propsMOPConfig.getProperty("domainname")!=null)?(propsMOPConfig.getProperty("domainname")).trim():"bookbattery.com";
		
		
		


		  /* ****************************************************************************************\
	* This action is used to get cities.
	\* *****************************************************************************************/		
		if(strWhatToDo.equalsIgnoreCase("getcity"))
		{ 
			try
			{
				String strRes=getcity(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);
				out.close();
			}
			catch (Exception e)
			{										
			LogLevel.ERROR(5, e, "Error :" + e);
			}	
		}
		
		if(strWhatToDo.equalsIgnoreCase("insertcustomerdetails"))
		{ 
			try
			{
				String strRes=insertcustomerdetails(req,res,session,strIpAddress,strPort,SMSFromAddress,baseUrl,FromEmailId,domainname);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);
				out.close();
			}
			catch (Exception e)
			{										
			LogLevel.ERROR(5, e, "Error :" + e);
			}	
		}		
		
		if(strWhatToDo.equalsIgnoreCase("insertratings"))
		{ 
			try
			{
				String strRes=insertratings(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);
				out.close();
			}
			catch (Exception e)
			{										
			LogLevel.ERROR(5, e, "Error :" + e);
			}	
		}
		
			
	}
			
  /* ****************************************************************************************\
	* This action is used to  get cities.
	\* *****************************************************************************************/	
	
	public String getcity(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			//String batterytype= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
			//String bat_id="";
			//LogLevel.DEBUG(5, new Throwable(), "batterytype :" + batterytype);
			String location="";	

			ServletOutputStream out=res.getOutputStream();
			
			String strSqlQry ="select distinct(city) from search_whereever_keywords";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector LocationVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"LocationVector:"+LocationVector );

			if(LocationVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&nbsp;Select&nbsp;City</option>");
				return strRes;
			}
			else
			{
				for (int i=0; i<LocationVector.size(); i++)
					{
						if(i==0)
						{
						out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&nbsp;Select&nbsp;City</option>");
						}
						Hashtable ht1=(Hashtable)LocationVector.get(i);
						//bat_id =String.valueOf(ht1.get("bat_id"));
						location =String.valueOf(ht1.get("city"));
						out.print("<option  value='"+location.trim()+"'>"+location.trim()+"</option>"); 
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
		
 /* ****************************************************************************************\
* This action is used to insert Customer Details.
\* *****************************************************************************************/	
	
	public String insertcustomerdetails(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strIpAddress,String strPort,String SMSFromAddress,String baseUrl,String FromEmailId,String strdomainname)
	{
	String strRes="";
	String ThankUSMSMsg2="";
		try
		{	
			
			LogLevel.DEBUG(5, new Throwable(), "Inside bookbattery file ");
			
			String name= (req.getParameter("name")!=null)?(req.getParameter("name")):"";
			LogLevel.DEBUG(5, new Throwable(), "name :" + name);
			String ordernumber= (req.getParameter("ordernumber")!=null)?(req.getParameter("ordernumber")):"";
			LogLevel.DEBUG(5, new Throwable(), "ordernumber :" + ordernumber);
			String email= (req.getParameter("email")!=null)?(req.getParameter("email")):"";
			LogLevel.DEBUG(5, new Throwable(), "email :" + email);
			String phone= (req.getParameter("phone")!=null)?(req.getParameter("phone")):"";
			LogLevel.DEBUG(5, new Throwable(), "phone :" + phone);
			String batterytype= (req.getParameter("batterytype")!=null)?(req.getParameter("batterytype")):"";
			LogLevel.DEBUG(5, new Throwable(), "batterytype :" + batterytype);
			String city= (req.getParameter("city")!=null)?(req.getParameter("city")):"";
			LogLevel.DEBUG(5, new Throwable(), "city :" + city);
			String sms_link_url= (req.getParameter("sms_link_url")!=null)?(req.getParameter("sms_link_url")):"";
			LogLevel.DEBUG(5, new Throwable(), "sms_link_url :" + sms_link_url);
			String shorturl= (req.getParameter("shorturl")!=null)?(req.getParameter("shorturl")):"";
			LogLevel.DEBUG(5, new Throwable(), "shorturl :" + shorturl);			
			String rating= (req.getParameter("rating")!=null)?(req.getParameter("rating")):"";
			LogLevel.DEBUG(5, new Throwable(), "rating :" + rating);
			String googleshorturl="https://goo.gl/RnpFVA";
			LogLevel.DEBUG(5, new Throwable(), "googleshorturl :" + googleshorturl);
			//String bat_id="";
			String location="";	
			String rating_link="";	
			String linkpath=""+baseUrl+"/bookbattery/jsp/rating.jsp?phone="+phone+"&email="+email+"&ordernumber="+ordernumber+"";
			String DomainName="BookBattery.com";
			
			SendSMS sendsms = new SendSMS(qm);
			
			ServletOutputStream out=res.getOutputStream();
			
			String strSqlQry ="select order_id from customer_rating_details where order_id='"+ordernumber+"' and mobile_number='"+phone+"'";
			
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector Order_numberVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"Order_numberVector:"+Order_numberVector );

						if(!Order_numberVector.isEmpty())
						{ 								
							//out.print("Already Exists");				
							LogLevel.DEBUG(1,new Throwable(),"Already Exists");
							
							if(rating.equals("Satisfied"))
							{
							   ThankUSMSMsg2 ="Your Order with "+DomainName+" , Ref No: "+ordernumber+" has been successfully installed. Please provide your valuable ratings by clicking "+googleshorturl+" "; 
							   LogLevel.DEBUG(5, new Throwable(), "ThankUSMSMsg2 :" + ThankUSMSMsg2);
							}
							else
							{
								ThankUSMSMsg2 ="Your Order with "+DomainName+" , Ref No: "+ordernumber+" has been successfully installed."; 
								LogLevel.DEBUG(5, new Throwable(), "ThankUSMSMsg2 :" + ThankUSMSMsg2);
							}
						}
						else
						{
								if(rating.equals("Satisfied"))
								{
									String SteSqlQry = "insert into customer_rating_details(s_id,name,order_id,email,mobile_number,city,batterytype,rating_comments,no_of_rating,sms_link_url,short_url,creation_date,updated_date,sent_flag,satisfy_flag) values(NULL,'"+name+"','"+ordernumber+"','"+email+"','"+phone+"','"+city+"','"+batterytype+"','','','"+linkpath+"','"+shorturl+"',now(),now(),'No','Yes')";												
									int reslt = qm.executeUpdate(SteSqlQry);
									
									if(reslt <0)
									{
										LogLevel.DEBUG(1,new Throwable(),"Failed to update your details! ");
									}
								   else
									{						
										LogLevel.DEBUG(5, new Throwable(), "baseUrl :" + baseUrl);						
										//String linkpath=""+baseUrl+"/bookbattery/jsp/rating.jsp?phone="+phone+"&email="+email+"&ordernumber="+ordernumber+"";

										//String ThankUMsg="Thanks for buying a Inverter/Battery with BookBattery/BookBattery. Please provide your valuable rating and feedback on our service by clicking "+linkpath+"";
										
										ThankUSMSMsg2 ="Your Order with "+DomainName+" , Ref No: "+ordernumber+" has been successfully installed. Please provide your valuable ratings by clicking "+googleshorturl+" "; 
									
										LogLevel.DEBUG(1,new Throwable(),"Successfully inserted your details! ");	
									}
								}
								else
								{
									String SteSqlQry = "insert into customer_rating_details(s_id,name,order_id,email,mobile_number,city,batterytype,rating_comments,no_of_rating,sms_link_url,short_url,creation_date,updated_date,sent_flag,satisfy_flag) values(NULL,'"+name+"','"+ordernumber+"','"+email+"','"+phone+"','"+city+"','"+batterytype+"','','','"+linkpath+"','"+shorturl+"',now(),now(),'No','No')";												
									int reslt = qm.executeUpdate(SteSqlQry);
									
									if(reslt <0)
									{
										LogLevel.DEBUG(1,new Throwable(),"Failed to update your details! ");
									}
								   else
									{						
										LogLevel.DEBUG(5, new Throwable(), "baseUrl :" + baseUrl);						
										
										ThankUSMSMsg2 ="Your Order with "+DomainName+" , Ref No: "+ordernumber+" has been successfully installed."; 
										LogLevel.DEBUG(5, new Throwable(), "ThankUSMSMsg2 :" + ThankUSMSMsg2);
									
										LogLevel.DEBUG(1,new Throwable(),"Successfully inserted your details! ");	
									}
								}
					    }	
					
					
					String strSMSResponse2=  sendsms.sendSMS(strIpAddress,strPort,SMSFromAddress,ThankUSMSMsg2, phone);
					LogLevel.DEBUG(5, new Throwable(), "strSMSResponse2 :" + strSMSResponse2);
					return strRes;
		
		}		
		catch (Exception e)
		{
			LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
			e.printStackTrace();
		}
		return strRes;
	}	
	
	
 /* ****************************************************************************************\
* This action is used to insert Ratings.
\* *****************************************************************************************/		
	
	public String insertratings(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String rating_content= (req.getParameter("rating_content")!=null)?(req.getParameter("rating_content")):"";
			LogLevel.DEBUG(5, new Throwable(), "rating_content :" + rating_content);
			String rating= (req.getParameter("rating")!=null)?(req.getParameter("rating")):"";
			LogLevel.DEBUG(5, new Throwable(), "rating :" + rating);
			//String email= (req.getParameter("email")!=null)?(req.getParameter("email")):"";
			//LogLevel.DEBUG(5, new Throwable(), "email :" + email);
			//String phone= (req.getParameter("phone")!=null)?(req.getParameter("phone")):"";
			//LogLevel.DEBUG(5, new Throwable(), "phone :" + phone);
			String ordernumber= (req.getParameter("ordernumber")!=null)?(req.getParameter("ordernumber")):"";
			LogLevel.DEBUG(5, new Throwable(), "ordernumber :" + ordernumber);
			//String bat_id="";
			String location="";	
			String rating_link="";	

			
			//buyerbrandname= buyerbrandname.replace("'","\\'");
			rating_content= rating_content.replace("'","\\'");
			
			ServletOutputStream out=res.getOutputStream();
			
			String strSqlQry3 ="update customer_rating_details set rating_comments='"+rating_content+"',no_of_rating='"+rating+"',upd_comm_flag='Yes' where order_id='"+ordernumber+"'";
			
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry3 :" + strSqlQry3);

			int reslt=qm.executeUpdate(strSqlQry3);
			
				if(reslt <0)
				{
					LogLevel.DEBUG(1,new Throwable(),"Failed to update your details! ");
				}
			   else
				{						
					out.print("Successfully inserted");	
					LogLevel.DEBUG(1,new Throwable(),"Successfully updated your details! ");						
				}
					

			}

		
		catch (Exception e)
		{
			LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
			e.printStackTrace();
		}
		return strRes;
	}	
	
	
	
}

// end of class
