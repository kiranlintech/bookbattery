/* 
Author(s)		: Chandra Prakash G.
File Name		: RetailerRegistration.java
Created on		: July 09, 2013.
Copyright Notice	: BookBattery Pvt.Ltd. Confidential
Description		: Retailer Registration
*/
package com.ngit.servlets.admin.batterystore; 
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.sendsms.SendSMS;
import com.ngit.javabean.mail.*;
import java.io.IOException; 
import java.io.FileInputStream; 
import java.util.Properties;
import java.util.Hashtable; 
import java.util.ArrayList; 
import java.util.Vector; 
import javax.servlet.ServletConfig; 
import javax.servlet.ServletContext; 
import javax.servlet.ServletException; 
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse; 
import javax.servlet.http.HttpSession;
import java.io.File;
/*This class is used to register a retailer*/
public class RetailerRegistration extends HttpServlet 
{ 
	private ServletContext context; 
	QueryManager qm;
	String baseURL;
	static final long serialVersionUID=21;
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
			String strLogFilePath = (propsMOPConfig.getProperty("LogFilePath")!=null)?propsMOPConfig.getProperty("LogFilePath"):"";
			if(strLogFilePath.equals(""))
			strLogFilePath = "/home/ngit/tomcat/webapps/bookbattery/logs/";
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
	/* This doGet service method calls doPost service method to handle all the requests and responses to perform register a retailer. */
	public void doGet (HttpServletRequest req , HttpServletResponse res )throws IOException , ServletException
	{
		doPost(req, res);
	}
	/* This doPost service method handles all the requests and responses passing from doGet service method to perform register a retailer.*/
	public void doPost( HttpServletRequest req , HttpServletResponse res )throws IOException , ServletException
	{ 
		res.setContentType("text/html;charset=UTF-8");
        HttpSession session=req.getSession(true);
 		session=req.getSession(true);
		String struserName=(String)session.getAttribute("sesBatteryAdminName"); 
		LogLevel.DEBUG(5,new Throwable(),"struserName :"+struserName );
		Properties propsMOPConfig = (Properties)context.getAttribute("contextPropMOPConfig"); 	
		String domainname =  propsMOPConfig.getProperty("domainname");
		String fromemailid =  propsMOPConfig.getProperty("SupportEmailId");
 		String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?(propsMOPConfig.getProperty("baseurldirectory")):"";
 		baseURL = (propsMOPConfig.getProperty("baseURL")!=null)?(propsMOPConfig.getProperty("baseURL")):"";
		LogLevel.DEBUG(5, new Throwable(), "baseURL :" + baseURL);
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
		ServletOutputStream out=res.getOutputStream();
		String strWhatToDo        = (req.getParameter("hidWhatToDo")!=null)?req.getParameter("hidWhatToDo"):""; 
		/*This action is to add the retailers*/
		if(strWhatToDo.equalsIgnoreCase("addRetailer"))
		{ 
			String strretailername = (req.getParameter("retailername")!=null)?(req.getParameter("retailername")):"";
			String strretailerloginname = (req.getParameter("retailerloginname")!=null)?(req.getParameter("retailerloginname")):"";
			String password = (req.getParameter("password")!=null)?(req.getParameter("password")):"";
			String strPhonenumber = (req.getParameter("phone_number")!=null)?(req.getParameter("phone_number")):"";
			String strPhonenumberother = (req.getParameter("phone_numberother")!=null)?(req.getParameter("phone_numberother")):"";
			String strMobilenumber = (req.getParameter("mobile_number")!=null)?(req.getParameter("mobile_number")):"";
			String strMobilenumberother = (req.getParameter("mobile_numberother")!=null)?(req.getParameter("mobile_numberother")):"";
			String strAddress1 = (req.getParameter("address")!=null)?(req.getParameter("address")):"";
			String timings = (req.getParameter("timings")!=null)?(req.getParameter("timings")):"";
            		String strAddress2 = (req.getParameter("website")!=null)?(req.getParameter("website")):"";
			String strCity = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
			String strState = (req.getParameter("state")!=null)?(req.getParameter("state")):"";
			String strZipcode = (req.getParameter("zipcode")!=null)?(req.getParameter("zipcode")):"";
			String pagenumber = (req.getParameter("pagenumber")!=null)?(req.getParameter("pagenumber")):"0";
			String strEmail= (req.getParameter("emailid")!=null)?(req.getParameter("emailid")):"";
			String strEmailother= (req.getParameter("emailidother")!=null)?(req.getParameter("emailidother")):"";
			String strArea= (req.getParameter("area")!=null)?(req.getParameter("area")):"";
			String strretailersbusinessflag= (req.getParameter("retailersbusinessflag")!=null)?(req.getParameter("retailersbusinessflag")):"";
			String eretailer_flag = (req.getParameter("eretailer_flag")!=null)?(req.getParameter("eretailer_flag")):"";
			String temp= (req.getParameter("temp")!=null)?(req.getParameter("temp")):"";
			String strsqlCity="";
			strAddress1 = strAddress1.replace("%22","&#34;"); strAddress1 = strAddress1.replace("'","&#39;"); strAddress1 = strAddress1.replace("\\","\\\\"); strAddress1 = strAddress1.replace("%u201C","&ldquo;"); strAddress1 = strAddress1.replace("%u201D","&rdquo;"); strAddress1 = strAddress1.replace("%20","\\ "); strAddress1 = strAddress1.replace("%7E","\\~"); strAddress1 = strAddress1.replace("%21","\\!"); strAddress1 = strAddress1.replace("%23","\\#"); strAddress1 = strAddress1.replace("%24","\\$");
			strAddress1 = strAddress1.replace("%25","&#37;"); strAddress1 = strAddress1.replace("%5E","\\^"); strAddress1 = strAddress1.replace("%26","\\&"); strAddress1 = strAddress1.replace("%28","\\("); strAddress1 = strAddress1.replace("%29","\\)"); strAddress1 = strAddress1.replace("%7C","\\|"); strAddress1 = strAddress1.replace("%7D","\\{");
			strAddress1 = strAddress1.replace("%7B","\\}"); strAddress1 = strAddress1.replace("%3A","\\:"); strAddress1 = strAddress1.replace("%3F","\\?"); strAddress1 = strAddress1.replace("%3E","\\>"); strAddress1 = strAddress1.replace("%3C","\\<"); strAddress1 = strAddress1.replace("%60","\\`"); strAddress1 = strAddress1.replace("%3D","\\=");
			strAddress1 = strAddress1.replace("%5C","\\\\"); strAddress1 = strAddress1.replace("%5D","\\]"); strAddress1 = strAddress1.replace("%5B","\\["); strAddress1 = strAddress1.replace("%u2018","&lsquo;"); strAddress1 = strAddress1.replace("%u2019","&rsquo;"); strAddress1 = strAddress1.replace("%3B","\\;"); strAddress1 = strAddress1.replace("%2C","\\,"); 
			strAddress1 = strAddress1.replace("%u2022","&bull;"); strAddress1 = strAddress1.replace("%09","\\ "); strAddress1 = strAddress1.replace("%0D","&nbsp;"); strAddress1 = strAddress1.replace("%0A","&nbsp");
			try
			{
				if((struserName==null) || (struserName==""))
				{
					session.removeAttribute("sesErrorMsg");
					session.setAttribute("sesErrorMsg","<font color='#CC0000' class='vrb10'>Session Expired. Please login again to Register a Retailer</font>");
					res.sendRedirect("../jsp/admin/batterystore/batteryadminlogin.jsp");
					return;
				}
				else
				{
					int AddRetailerresult = addRetailer(req ,res,struserName,session);
					if(AddRetailerresult == -3)
					{
						LogLevel.DEBUG(1, new Throwable(), "Failed to Register a Retailer");
						session.setAttribute("priority","1");
						session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'></font> ");
						res.sendRedirect("../jsp/admin/batterystore/retailers/addretailer.jsp");
						return;
					}
					else if(AddRetailerresult == -2)
					{
						LogLevel.DEBUG(1, new Throwable(), "Failed to Register a Retailer");
						session.setAttribute("priority","1");
						session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'></font> ");
						res.sendRedirect("../jsp/admin/batterystore/retailers/addretailer.jsp");
						return;
					}
					else if(AddRetailerresult <0)
					{
						LogLevel.DEBUG(1, new Throwable(), "Failed to Register a Retailer");
						session.setAttribute("priority","1");
						session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'></font> ");
						res.sendRedirect("../jsp/admin/batterystore/retailers/addretailer.jsp");
						return;
					}
					else
					{	
						if(eretailer_flag.equals("0"))
						{
						strState=strState.replaceAll(" ","_");
						/*query to select retailer id and city to insert into retailers_subcatbrandmap table*/
						strsqlCity = "select retailer_id,city from "+strState+"_retailers where retailer_name='"+strretailername+"'"; 
						}
						else if(eretailer_flag.equals("1"))
						{
						/*query to select retailer id and city to insert into retailers_subcatbrandmap table*/
						strsqlCity = "select retailer_id,city from eretailers where retailer_name='"+strretailername+"'"; 
						}
						Hashtable htfetchcity = qm.getRow(strsqlCity);
						String retailerid=String.valueOf(htfetchcity.get("retailer_id"));
						String city=String.valueOf(htfetchcity.get("city"));
						String key="addretailer";
						session.setAttribute("priority","1");
						session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'></font> ");
						
						strretailername=strretailername.replaceAll("&","%26");
						strretailername=strretailername.replaceAll("/","%2F");
						strretailername=strretailername.replaceAll(":","%3A");
						strretailername=strretailername.replaceAll("_","%5F");
						strretailername=strretailername.replaceAll("-","%2D");
					//	String rest="../jsp/admin/batterystore/retailers/retailersubcatbrand.jsp?retailername="+strretailername+"&retailerid="+retailerid+"&city="+city+"&phone_number="+strPhonenumber+"&mobile_number="+strMobilenumber+"&website="+strAddress2+"&state="+strState+"&zipcode="+strZipcode+"&emailid="+strEmail+"&retailerloginname="+strretailerloginname+"&area="+strArea+"&eretailer_flag="+eretailer_flag+"&key="+key+"&address="+strAddress1+"&temp="+temp;
						String rest="../jsp/admin/batterystore/retailers/addretailer.jsp";
						session.setAttribute("sesretErrorMsg", "<font color='#FF3333' class='top1'>Successfully inserted Retailer Details! </font> ");
						LogLevel.DEBUG(5, new Throwable(),"gundalachandu :"+rest);
						res.sendRedirect(rest);
						return;
					}
				}
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
				out.println("-1");
				out.close();
			}
		}
		/*This action is used to insert category,subcategory and brand details of a retailer*/
		else if(strWhatToDo.equalsIgnoreCase("insertsubcategorybranddetails"))
		{ 			
			String categoryid = (req.getParameter("categoryid") != null)? (req.getParameter("categoryid")) : "0";
			String categoryname = (req.getParameter("categoryname") != null)? (req.getParameter("categoryname")) : "0";
			String subcategoryid = (req.getParameter("subcategoryid") != null)? (req.getParameter("subcategoryid")) : "0";
			String subcategoryname = (req.getParameter("subcategoryname") != null)? (req.getParameter("subcategoryname")) : "0";
			String brand= (req.getParameter("brand")!=null)?(req.getParameter("brand")):"default";
			String retailerid = (req.getParameter("retailerid") != null)? (req.getParameter("retailerid")) : "0";
			String retailername = (req.getParameter("retailername") != null)? (req.getParameter("retailername")) : "0";
			String retailerloginname = (req.getParameter("retailerloginname") != null)? (req.getParameter("retailerloginname")) : "0";
			String state = (req.getParameter("state") != null)? (req.getParameter("state")) : "0";
			String city = (req.getParameter("city") != null)? (req.getParameter("city")) : "0";
			String area = (req.getParameter("area") != null)? (req.getParameter("area")) : "0";
			String stremail= (req.getParameter("emailid")!=null)?(req.getParameter("emailid")):"";
			String eretailer_flag= (req.getParameter("eretailer_flag")!=null)?(req.getParameter("eretailer_flag")):"";
			String temp= (req.getParameter("temp")!=null)?(req.getParameter("temp")):"";
			try
			{ 
 				String strResSubcatdetls = insertsubcategorybranddetails(req ,res,struserName,session);
				out.println(strResSubcatdetls);
				out.close();
			} 
			catch(IOException ioe)
			{
				LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
				ioe.printStackTrace();
				session.setAttribute("sesErrorMsg", "General Error...Please Try Again" );
				res.sendRedirect("../jsp/admin/batterystore/retailers/retailersubcatbrand.jsp");
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
				out.println("-1");
				out.close();
			}
		}
		/*This action is used to display the category,subcategory and brand details of a retailer in body onload */
		else if(strWhatToDo.equalsIgnoreCase("fetchsubcatbranddetailsonload"))
		{ 			
			try
			{ 
 				String strResFetchdetls = fetchsubcatbranddetailsonload(req ,res,struserName,session);
				out.println(strResFetchdetls);
				out.close();
			} 
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
				out.println("-1");
				out.close();
			}
		}
		/*This action is used to delete retailer category, subcategory and brand details*/
		else if(strWhatToDo.equalsIgnoreCase("deletesubcategorybranddetails"))
		{ 			
			try
			{ 
 				String strResDelSubcatdetls = deletesubcategorybranddetails(req ,res,struserName,session);
				out.println(strResDelSubcatdetls);
				out.close();
			} 
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
				out.println("-1");
				out.close();
			}
		}
		/*This action is used to check the retailer loginname*/
		else if(strWhatToDo.equalsIgnoreCase("checkretailerloginname"))
		{ 			
			String retailerloginname = (req.getParameter("retailerloginname")!=null)?(req.getParameter("retailerloginname")):"";
			try
			{ 
 				String strResCheckRetloginname = checkretailerloginname(req ,res,struserName,session);
				out.println(strResCheckRetloginname);
				out.close();
			} 
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
				out.println("-1");
				out.close();
			}
		}
		/*This action is used to check the retailer name*/
		else if(strWhatToDo.equalsIgnoreCase("checkretailername"))
		{ 			
			String retailername = (req.getParameter("retailername")!=null)?(req.getParameter("retailername")):"";
			try
			{ 
 				String strResCheckRetname = checkretailername(req ,res,struserName,session);
				out.println(strResCheckRetname);
				out.close();
			} 
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
				out.println("-1");
				out.close();
			}
		}
		
		/*This action is used to insert GSTID*/
		else if(strWhatToDo.equalsIgnoreCase("insertgstiddetails"))
		{ 			
			try
			{ 
 				String strinsertgstiddetails = insertgstiddetails(req ,res,struserName,session);
				out.println(strinsertgstiddetails);
				out.close();
			} 
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
				out.println("-1");
				out.close();
			}
		}		/*This action is used to insert GSTID*/
		/*This action is used to Get GSTID*/
		else if(strWhatToDo.equalsIgnoreCase("getgstiddetails"))
		{ 			
			try
			{ 
 				String strgetgstiddetails = getgstiddetails(req ,res,struserName,session);
				out.println(strgetgstiddetails);
				out.close();
			} 
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
				out.println("-1");
				out.close();
			}
		}		/*This action is used to Get GSTID*/
		
		/*This action is used to Update GSTID*/
		else if(strWhatToDo.equalsIgnoreCase("updategstiddetails"))
		{ 			
			try
			{ 
 				String strupdategstiddetails = updategstiddetails(req ,res,struserName,session);
				out.println(strupdategstiddetails);
				out.close();
			} 
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
				out.println("-1");
				out.close();
			}
		}		/*This action is used to insert GSTID*/
		
		/*This action is used to display the retailer name message wheteher it is already exist or not*/
		else if(strWhatToDo.equalsIgnoreCase("checkretailernamemessage"))
		{ 			
			String retailername = (req.getParameter("retailername")!=null)?(req.getParameter("retailername")):"";
			try
			{ 
 				String strResCheckRetsesmsg = checkretailernamemessage(req ,res,struserName,session);
				out.println(strResCheckRetsesmsg);
				out.close();
			} 
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
				out.println("-1");
				out.close();
			}
		}
		/*This action is used to check the area name*/
		else if(strWhatToDo.equalsIgnoreCase("areanamecheck"))
		{ 			
			String strareaname = (req.getParameter("areaname")!=null)?(req.getParameter("areaname")):"";
			try
			{ 
 				String strResAreacheck = areanamecheck(req ,res,struserName,session);
				out.println(strResAreacheck);
				out.close();
			} 
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
				out.println("-1");
				out.close();
			}
		}
		/*This action is to send the mail after registration of the retailer*/	
		else if(strWhatToDo.equalsIgnoreCase("sendSMStoRetailer"))
		{ 
			String strretailername = (req.getParameter("retailername")!=null)?(req.getParameter("retailername")):"";
			String stremail= (req.getParameter("email")!=null)?(req.getParameter("email")):"";
			String retailerid= (req.getParameter("retailerid")!=null)?(req.getParameter("retailerid")):"";
			String mobilenumber= (req.getParameter("mobilenumber")!=null)?(req.getParameter("mobilenumber")):"";
			String eretailerflag= (req.getParameter("eretailerflag")!=null)?(req.getParameter("eretailerflag")):"";
			String state= (req.getParameter("state")!=null)?(req.getParameter("state")):"";
			LogLevel.DEBUG(5,new Throwable(),"eretailerflag"+eretailerflag);
			LogLevel.DEBUG(5,new Throwable(),"state"+state);
			LogLevel.DEBUG(5,new Throwable(),"retailerid"+retailerid);
			state=state.replaceAll(" ","_");
			String querysubcat="";
			String SMSMsg="";
			String strMailMessage1="";
			try
			{	
				if(eretailerflag.equals("0") || eretailerflag.equals("false")){
				querysubcat="select count(*) as count from "+state+"_subcatbrandmap where retailer_id="+retailerid+" and category_id='778'";
				}
				else{
				querysubcat="select count(*) as count from eretailers_subcatbrandmap where retailer_id="+retailerid+" and category_id='778'";
				}
				Hashtable htsubcat = qm.getRow(querysubcat);
				String strcountsubcat=String.valueOf(htsubcat.get("count"));
				int subcat=Integer.parseInt(strcountsubcat);				
				LogLevel.DEBUG(5,new Throwable(),"subcat"+subcat);
				String IpAddress=(propsMOPConfig.getProperty("TRANSMITTER_IP_ADDR")!=null)?(propsMOPConfig.getProperty("TRANSMITTER_IP_ADDR")):"";
				String Portaddress=(propsMOPConfig.getProperty("TRANSMITTER_IP_PORT")!=null)?(propsMOPConfig.getProperty("TRANSMITTER_IP_PORT")):"9910";
				String SMSFromAddress=(propsMOPConfig.getProperty("SMSFromAddress")!=null)?(propsMOPConfig.getProperty("SMSFromAddress")):"";
				MailTrans mtrans1 = new MailTrans();
				mtrans1.setStrSmtpHost(domainname);
				mtrans1.setStrFrom(fromemailid);
				mtrans1.setStrTo(stremail);
				mtrans1.setStrSubject("Registration Acknowledgement");
				if(subcat<1)
				{
				SMSMsg="BookBattery has Listed your business for free. Please register at www.bookbattery.com or call  919603467559, 919666300002 to get more benefits for free.";
				strMailMessage1 =
				"Dear " + strretailername
				+ "\n Welcome to BookBattery. \n  You are now successfully registered with http://www.bookbattery.com as a Retailer \n Please contact our marketing people to get your login name and password to access https://www.bookbattery.com/retailer/ \n Warm Regards\n BookBattery Support Team \n Note: This is an auto-generated response. Kindly do not reply on this mail.\n \n Confidentiality Information and Disclaimer: \n This communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
				}
				else
				{
				SMSMsg="BookBattery has listed you as Blood Donor. For further assistance please visit www.bookbattery.com or call 919603467559.";
				strMailMessage1 =
				"Dear " + strretailername
				+ "\n Welcome to BookBattery. \n  You are now successfully registered as a Blood Donor in http://www.bookbattery.com  \n For further details please visit https://www.bookbattery.com/ \n Warm Regards\n BookBattery Support Team \n Note: This is an auto-generated response. Kindly do not reply on this mail.\n \n Confidentiality Information and Disclaimer: \n This communication sent from BookBattery is confidential and intended solely for the use of addressee. Any retransmission, dissemination or the use of  this information by persons other than addressee is unlawful and prohibited. The views expressed here-in (including any attachments) are those of the individual sender only. BookBattery does not accept any liability what-so-ever including on account of any  errors, omissions, viruses etc in the contents  of  this message.";
				}
				mtrans1.setStrText(strMailMessage1);
				Thread mt1=new MailThread(mtrans1,"");
				mt1.start();
				if(!mobilenumber.equals(""))
				{
				SendSMS sendsms = new SendSMS(qm);
				String strSMSResponse=sendsms.sendSMS(IpAddress,Portaddress,SMSFromAddress,SMSMsg,mobilenumber);
				session.setAttribute("priority","1");
				session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'></font> ");
				res.sendRedirect("../jsp/admin/batterystore/retailers/retailersubcatbrand.jsp");
				return;
				}
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
				out.println("-1");
				out.close();
			}
		}
	}//end of do post
	/* This method is used to insert Retailer details into retailers table.*/
	public int addRetailer (HttpServletRequest req , HttpServletResponse res,String struserName,HttpSession session)
	{
		String strretailername = (req.getParameter("retailername")!=null)?(req.getParameter("retailername")):"";
		LogLevel.DEBUG(5,new Throwable(),"gundala :"+strretailername);
		String strretailerloginname = (req.getParameter("retailerloginname")!=null)?(req.getParameter("retailerloginname")):"";
		String password = (req.getParameter("password")!=null)?(req.getParameter("password")):"";
		String strPhonenumber = (req.getParameter("phone_number")!=null)?(req.getParameter("phone_number")):"";
		String strPhonenumberother = (req.getParameter("phone_numberother")!=null)?(req.getParameter("phone_numberother")):"";
		String strMobilenumber = (req.getParameter("mobile_number")!=null)?(req.getParameter("mobile_number")):"";
		String strMobilenumberother = (req.getParameter("mobile_numberother")!=null)?(req.getParameter("mobile_numberother")):"";
		String strAddress1 = (req.getParameter("address")!=null)?(req.getParameter("address")):"";
		String timings = (req.getParameter("timings")!=null)?(req.getParameter("timings")):"";
		String strAddress2 = (req.getParameter("website")!=null)?(req.getParameter("website")):"";
		String strCity = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
		String strState = (req.getParameter("state")!=null)?(req.getParameter("state")):"";
		String strZipcode = (req.getParameter("zipcode")!=null)?(req.getParameter("zipcode")):"";
		String pagenumber = (req.getParameter("pagenumber")!=null)?(req.getParameter("pagenumber")):"0";
		String strEmail= (req.getParameter("emailid")!=null)?(req.getParameter("emailid")):"";
		String strEmailother= (req.getParameter("emailidother")!=null)?(req.getParameter("emailidother")):"";
		String tollfreenumber= (req.getParameter("tollfreenumber")!=null)?(req.getParameter("tollfreenumber")):"";
		String strArea= (req.getParameter("area")!=null)?(req.getParameter("area")):"";
		String temp= (req.getParameter("temp")!=null)?(req.getParameter("temp")):"";
		String strretailersbusinessflag= (req.getParameter("retailersbusinessflag")!=null)?(req.getParameter("retailersbusinessflag")):"";
		String eretailer_flag = (req.getParameter("eretailer_flag")!=null)?(req.getParameter("eretailer_flag")):"";
		String strAddress2replace = strAddress2.replace("\\","\\\\");
		String strAddress2rep = strAddress2replace.replace("'","\\'");
		String strAddress1trim=strAddress1.trim();
		String strAddress2trim=strAddress2rep.trim();
		ArrayList htRetid=new ArrayList();
		String statetable= strState.replaceAll(" ","_");
		String inserretailer="";
		strAddress1trim = strAddress1trim.replace("%22","&#34;"); strAddress1trim = strAddress1trim.replace("'","&#39;"); strAddress1trim = strAddress1trim.replace("\\","\\\\"); strAddress1trim = strAddress1trim.replace("%u201C","&ldquo;"); strAddress1trim = strAddress1trim.replace("%u201D","&rdquo;"); strAddress1trim = strAddress1trim.replace("%20","\\ "); strAddress1trim = strAddress1trim.replace("%7E","\\~"); strAddress1trim = strAddress1trim.replace("%21","\\!"); strAddress1trim = strAddress1trim.replace("%23","\\#"); strAddress1trim = strAddress1trim.replace("%24","\\$");
		strAddress1trim = strAddress1trim.replace("%25","&#37;"); strAddress1trim = strAddress1trim.replace("%5E","\\^"); strAddress1trim = strAddress1trim.replace("%26","\\&"); strAddress1trim = strAddress1trim.replace("%28","\\("); strAddress1trim = strAddress1trim.replace("%29","\\)"); strAddress1trim = strAddress1trim.replace("%7C","\\|"); strAddress1trim = strAddress1trim.replace("%7D","\\{");
		strAddress1trim = strAddress1trim.replace("%7B","\\}"); strAddress1trim = strAddress1trim.replace("%3A","\\:"); strAddress1trim = strAddress1trim.replace("%3F","\\?"); strAddress1trim = strAddress1trim.replace("%3E","\\>"); strAddress1trim = strAddress1trim.replace("%3C","\\<"); strAddress1trim = strAddress1trim.replace("%60","\\`"); strAddress1trim = strAddress1trim.replace("%3D","\\=");
		strAddress1trim = strAddress1trim.replace("%5C","\\\\"); strAddress1trim = strAddress1trim.replace("%5D","\\]"); strAddress1trim = strAddress1trim.replace("%5B","\\["); strAddress1trim = strAddress1trim.replace("%u2018","&lsquo;"); strAddress1trim = strAddress1trim.replace("%u2019","&rsquo;"); strAddress1trim = strAddress1trim.replace("%3B","\\;"); strAddress1trim = strAddress1trim.replace("%2C","\\,"); 
		strAddress1trim = strAddress1trim.replace("%u2022","&bull;"); strAddress1trim = strAddress1trim.replace("%09","\\ "); strAddress1trim = strAddress1trim.replace("%0D","&nbsp;"); strAddress1trim = strAddress1trim.replace("%0A","&nbsp");
		try
		{
			/*query used to insert retailer details*/
			String strInsertRetailer = "insert into retailers(retailer_name,retailer_loginname,password,city,state,eretailer_flag,creation_date,created_by) values('"+strretailername+"','"+strretailerloginname+"','"+password+"','"+strCity+"','"+strState+"','"+eretailer_flag+"', now(),'ngit')";
			int InsertRetailerresult = qm.executeUpdate(strInsertRetailer);
			String fetchretailerid = "select retailer_id from retailers where retailer_loginname='"+strretailerloginname+"'";
			htRetid=qm.getField(fetchretailerid);
			String retailerid = htRetid.toString().replace("[", "").replace("]", "");
			if(eretailer_flag.equals("0")){
			inserretailer = "insert into "+statetable+"_retailers(retailer_id,retailer_name,email_id,phone_number,mobile_number,email_id1,phone_numberother,mobile_numberother,retailer_loginname,password,address1,address2,zipcode,city,state,eretailer_flag,area,creation_date,created_by,retailer_flag,account_expiry,miniurl_flag,tollfree_number,timings) values('"+retailerid+"','"+strretailername+"','"+strEmail+"','"+strPhonenumber+"','"+strMobilenumber+"','"+strEmailother+"','"+strPhonenumberother+"','"+strMobilenumberother+"','"+strretailerloginname+"','"+password+"','"+strAddress1trim+"','"+strAddress2trim+"','"+strZipcode+"','"+strCity+"','"+strState+"','"+eretailer_flag+"','"+strArea+"', now(),'ngit','no',DATE_ADD(now(),interval '12' month),'No','"+tollfreenumber+"','"+timings+"')";
			}
			else if(eretailer_flag.equals("1")){
			inserretailer = "insert into eretailers(retailer_id,retailer_name,email_id,phone_number,mobile_number,email_id1,phone_numberother,mobile_numberother,retailer_loginname,password,address1,address2,zipcode,city,state,eretailer_flag,area,creation_date,created_by,retailer_flag,account_expiry,miniurl_flag,tollfree_number,timings) values('"+retailerid+"','"+strretailername+"','"+strEmail+"','"+strPhonenumber+"','"+strMobilenumber+"','"+strEmailother+"','"+strPhonenumberother+"','"+strMobilenumberother+"','"+strretailerloginname+"','"+password+"','"+strAddress1trim+"','"+strAddress2trim+"','"+strZipcode+"','"+strCity+"','"+strState+"','"+eretailer_flag+"','"+strArea+"', now(),'ngit','no',DATE_ADD(now(),interval '12' month),'No','"+tollfreenumber+"','"+timings+"')";
			}
			int InserteRetailerresult = qm.executeUpdate(inserretailer);
			return (InserteRetailerresult < 0)?-1:InserteRetailerresult;
		}
		catch (Exception e)
		{
			LogLevel.DEBUG(5, e, "Exception while inserting retailer:" + e);
			e.printStackTrace();
			return -1;
		}
	}
	/* This Method is to insert category, subcategory brand details of a retailer into retailers_subcatbrandmap table.*/
     public String insertsubcategorybranddetails (HttpServletRequest req , HttpServletResponse res, String struserName,HttpSession session)
    {
		String retailerid = (req.getParameter("retailerid") != null)? (req.getParameter("retailerid")) : "0";
		String retailername = (req.getParameter("retailername") != null)? (req.getParameter("retailername")) : "0";
		String retailerloginname = (req.getParameter("retailerloginname") != null)? (req.getParameter("retailerloginname")) : "0";
		String city = (req.getParameter("city") != null)? (req.getParameter("city")) : "0";
		String area = (req.getParameter("area") != null)? (req.getParameter("area")) : "0";
		String stremail= (req.getParameter("emailid")!=null)?(req.getParameter("emailid")):"";
		String categoryid = (req.getParameter("categoryid") != null)? (req.getParameter("categoryid")) : "0";
		String categoryname = (req.getParameter("categoryname") != null)? (req.getParameter("categoryname")) : "0";
		String subcategoryid1 = (req.getParameter("subcategoryid") != null)? (req.getParameter("subcategoryid")) : "0";
		String subcategoryname1 = (req.getParameter("subcategoryname") != null)? (req.getParameter("subcategoryname")) : "0";
		String brand= (req.getParameter("brand")!=null)?(req.getParameter("brand")):"default";
		String selbrandname= (req.getParameter("brandname")!=null)?(req.getParameter("brandname")):"default";		
		String temp= (req.getParameter("temp")!=null)?(req.getParameter("temp")):"";
		String vendorid="";
		String vendorname="";	
		String brandname="";
		String strResSubcatdetls="";
		String strResSubcat="";
		String pagenumber = (req.getParameter("pagenumber")!=null)?(req.getParameter("pagenumber")):"0";
		String pagetype = (req.getParameter("pagetype")!=null)?(req.getParameter("pagetype")):"0";
		String lastpage = (req.getParameter("lastpage")!=null)?(req.getParameter("lastpage")):"0";
		String state = (req.getParameter("state") != null)? (req.getParameter("state")) : "0";
		String eretailer_flag= (req.getParameter("eretailer_flag")!=null)?(req.getParameter("eretailer_flag")):"";
		LogLevel.DEBUG(5, new Throwable(), "eretailer_flag:" + eretailer_flag);
		String strCatname="",strSqlQryRetname="",work_flag="";
		String statetable=state.replaceAll(" ","_");
		String categoryCount="";
		try
		{  
			if((struserName==null) || (struserName==""))
			{
				strResSubcatdetls=strResSubcatdetls+"Session Expired Please login to add/update the retailer again";
				return strResSubcatdetls;
			}
			else
			{
				strSqlQryRetname="select work_flag from retailers where retailer_id='"+retailerid+"'";
				Hashtable htRetname=(Hashtable)qm.getRow(strSqlQryRetname);
				work_flag=(String)htRetname.get("work_flag");
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
				int RETAILERS_PER_PAGE=10;
				int Startlimt=RETAILERS_PER_PAGE*(Integer.parseInt(pagenumber)-1);
				String Startindex=Integer.toString(Startlimt);
				if(brand!=null)
				{
					String[] BrandArray=brand.split(",");
					String[] BrandArrayName=selbrandname.split(",");
					String[] subcategoryidarray=subcategoryid1.split(",");
					
					String[] subcategorynamearray=subcategoryname1.split(",");
				
					LogLevel.DEBUG(5, new Throwable(), "subcategoryidarray:" + subcategoryidarray.length);
					if(BrandArray.length>0)
					{
						String strBrandid="";
						String brandnamenew="";
						String subcategoryname="";
						String subcategoryid="";
					for(int p=0;p<subcategorynamearray.length ;p++)
					{
						subcategoryname=subcategorynamearray[p];
						subcategoryid=subcategoryidarray[p];
					if(eretailer_flag.equals("0") || eretailer_flag.equals("false"))
					{
						for (int j=0;j<BrandArray.length ;j++ )
						{	
							strBrandid=BrandArray[j];
							brandnamenew=BrandArrayName[j];
							String strBrand ="select distinct brandname,vendor_id from vendor_cat_detl where vendor_id="+strBrandid+" and category_id="+subcategoryid+" and brandname='"+brandnamenew+"' order by brandname ASC ";
LogLevel.DEBUG(5, new Throwable(), "strBrand:" + strBrand);
							Vector brandVector = qm.executeQuery(strBrand);

							if(strBrandid.equals("") || brandVector == null || brandVector.size() == 0)
							{
								strBrandid="0";
								brandnamenew="default";
							}
							
							/*query to select retailer id based on selected category, subcategory and brand*/
							String strRetid = "SELECT retailer_id FROM "+statetable+"_subcatbrandmap WHERE retailer_id ='"+retailerid+"' and category_id='"+categoryid+"' and category_name='"+categoryname+"' and subcategory_id='"+subcategoryid+"' and subcategory_name='"+subcategoryname+"' and vendor_id='"+strBrandid+"' and brandname='"+brandnamenew+"'"; 
							LogLevel.DEBUG(5, new Throwable(), "strRetid:" + strRetid);
							Hashtable htRetid = qm.getRow(strRetid);
							if(htRetid.isEmpty())
							{ 
								if ( retailerloginname.equals("") || retailerloginname.equals("NULL") || retailerloginname.equals("0") || retailerloginname.equals("null"))
								{
									strResSubcat="<span class='pages'><br><center><font size='3' color='red'>Failed to add/update a retailer. Please add/update the retailer again<br></center></span>";
									return strResSubcat;
								}	
								else
								{
								/*query used to insert retailer details into retailer_subcatbrandmap table*/
								String strInsertSubcat = "insert into "+statetable+"_subcatbrandmap(retailer_id,retailer_name,city,area,category_id,category_name,subcategory_id,subcategory_name,vendor_id,brandname,creation_date,created_by,retailer_loginname,work_flag) values('"+retailerid+"','"+retailername+"','"+city+"','"+area+"','"+categoryid+"','"+categoryname+"','"+subcategoryid+"','"+subcategoryname+"','"+strBrandid+"','"+brandnamenew+"', now(),'ngit','"+retailerloginname+"','"+work_flag+"')";
								LogLevel.DEBUG(5, new Throwable(), "strInsertSubcat:" + strInsertSubcat);
								int retailerbrandmap=qm.executeUpdate(strInsertSubcat);
								/*query used to update the retailer flag as yes*/	
								}
							}
							else
							{
							}
							
						}
					String strRetflag= "update "+statetable+"_retailers set retailer_flag='yes' where retailer_id="+retailerid;
					LogLevel.DEBUG(5, new Throwable(), "strRetflag:" + strRetflag);
					int retflag=qm.executeUpdate(strRetflag);
					}
					else if(eretailer_flag.equals("1") || eretailer_flag.equals("true"))
					{
						for (int j=0;j<BrandArray.length ;j++ )
						{	
							strBrandid=BrandArray[j];
							brandnamenew=BrandArrayName[j];
							if(strBrandid.equals(""))
							{
								strBrandid="0";
								brandnamenew="default";
							}
							/*query to select retailer id based on selected category, subcategory and brand*/
							String strRetid = "SELECT retailer_id FROM eretailers_subcatbrandmap WHERE retailer_id ='"+retailerid+"' and category_id='"+categoryid+"' and category_name='"+categoryname+"' and subcategory_id='"+subcategoryid+"' and subcategory_name='"+subcategoryname+"' and vendor_id='"+strBrandid+"' and brandname='"+brandnamenew+"'"; 
							Hashtable htRetid = qm.getRow(strRetid);
							if(htRetid.isEmpty())
							{ 
								if ( retailerloginname.equals("") || retailerloginname.equals("NULL") || retailerloginname.equals("0") || retailerloginname.equals("null"))

								{
									strResSubcat="<span class='pages'><br><center><font size='3' color='red'>Failed to add/update a retailer. Please add/update the retailer again<br></center></span>";
									return strResSubcat;
								}	
								else
								{
								/*query used to insert retailer details into retailer_subcatbrandmap table*/
								String strInsertSubcat = "insert into eretailers_subcatbrandmap(retailer_id,retailer_name,city,area,category_id,category_name,subcategory_id,subcategory_name,vendor_id,brandname,creation_date,created_by,retailer_loginname,work_flag) values('"+retailerid+"','"+retailername+"','"+city+"','"+area+"','"+categoryid+"','"+categoryname+"','"+subcategoryid+"','"+subcategoryname+"','"+strBrandid+"','"+brandnamenew+"', now(),'ngit','"+retailerloginname+"','"+work_flag+"')";
								int retailerbrandmap=qm.executeUpdate(strInsertSubcat);
								/*query used to update the retailer flag as yes*/	
								}
							}
							else
							{
							}
							
						}
					String strRetflag= "update eretailers set retailer_flag='yes' where retailer_id="+retailerid;
					int retflag=qm.executeUpdate(strRetflag);		
					}
					}
					}
				}
				strResSubcatdetls=strResSubcatdetls+"<tr><td colspan='6' ><hr width='500' align='center'  color='#BEADCB'></td></tr>";	strResSubcatdetls=strResSubcatdetls+"<table align='center' border='0' cellpadding='0' cellspacing='0'>";
				strResSubcatdetls=strResSubcatdetls+"<tr id='button1' height='20'><td >&nbsp;&nbsp;&nbsp;<a href='javascript:deleteSubcategorybranddetails()' class='smallbutton'>Delete</a>&nbsp;&nbsp;&nbsp;</td></tr><tr height='10'></tr></table>";
				strResSubcatdetls=strResSubcatdetls+"<table width ='500' cellspacing = '2' cellpadding = '2'  bgcolor='#BEADCB' align='center'>";	
				strResSubcatdetls=strResSubcatdetls+"<tr><td><table width = '550' cellspacing = '0' cellpadding = '0'  bgcolor='#FFFFFF' align='center'>";
				if(eretailer_flag.equals("0") || eretailer_flag.equals("false")){
				/*query used to select category name, subcategory name and brand for the inserted retailer*/
				strCatname = "select category_id,category_name,subcategory_id,subcategory_name,brandname,vendor_id from "+statetable+"_subcatbrandmap where retailer_id='"+retailerid+"' order by creation_date desc,brandname desc limit "+Startindex+",10";
				/*query used to fetch category_name count from retailers_subcatbrandmap based on retailer_id*/
				categoryCount="select count(category_name) as count from "+statetable+"_subcatbrandmap where retailer_id='"+retailerid+"' " ;
				}
				else if(eretailer_flag.equals("1") || eretailer_flag.equals("true")){
				/*query used to select category name, subcategory name and brand for the inserted retailer*/
								strCatname = "select category_id,category_name,subcategory_id,subcategory_name,brandname,vendor_id from eretailers_subcatbrandmap where retailer_id='"+retailerid+"' order by creation_date desc,brandname desc limit "+Startindex+",10";
				/*query used to fetch category_name count from retailers_subcatbrandmap based on retailer_id*/
				categoryCount="select count(category_name) as count from eretailers_subcatbrandmap where retailer_id='"+retailerid+"'" ;
				}
				Hashtable htcount = qm.getRow(categoryCount);
				String strcount=String.valueOf(htcount.get("count"));
				Vector Catdetails= qm.executeQuery(strCatname);
				if(Catdetails==null || Catdetails.size() <=0 )
				{
					strResSubcatdetls=strResSubcatdetls+"<tr ><td class='click1' align='center'> No categories are available.please select atleast one category</td>";
					strResSubcatdetls=strResSubcatdetls+"</tr>";
				}
				else
				{
					int TotalCount=Integer.parseInt(strcount);
					int SUBCATDETLSPERPAGE=10;
					int tp=((int)(Math.ceil((double)TotalCount/SUBCATDETLSPERPAGE)));
					String Lastpage=Integer.toString(tp);
					strResSubcatdetls=strResSubcatdetls+"<tr><td colspan='8' align='center'><table border='0' width='100%'><tr><td width='45%' align='center'> ";
					if(pagenumber.equals("1"))
					{
						strResSubcatdetls=strResSubcatdetls+"<font class='blue2'>First</font>&nbsp;&nbsp;";
						strResSubcatdetls=strResSubcatdetls+"<font class='blue2'>Previous</font>&nbsp;&nbsp;<input type='hidden' name='rettotalcount' id='rettotalcount' value='"+strcount+"'>";
					}
					else
					{
						strResSubcatdetls=strResSubcatdetls+"<font ><a class='blue1'	href=\"javascript:funOnClickFirstSubcatDetls('"+pagenumber+"','"+retailerid+"','"+retailername+"','"+state+"','"+eretailer_flag+"');\">First</a></font>&nbsp;&nbsp;";
						strResSubcatdetls=strResSubcatdetls+"<font> <a class='blue1' href=\"javascript:funOnClickPreviousSubcatDetls('"+pagenumber+"','"+retailerid+"','"+retailername+"','"+state+"','"+eretailer_flag+"');\" > Previous</a> </font>&nbsp;&nbsp;";
					}
					if(pagenumber.equals(Lastpage))
					{
						strResSubcatdetls=strResSubcatdetls+"&nbsp;&nbsp;<font class='blue2'>Next</font>&nbsp;&nbsp;";
						strResSubcatdetls=strResSubcatdetls+"<font class='blue2'>Last</font>&nbsp;&nbsp;<input type='hidden' name='rettotalcount' id='rettotalcount' value='"+strcount+"'>";
					}
					else
					{
						strResSubcatdetls=strResSubcatdetls+"&nbsp;&nbsp;<font><a class='blue1' href=\"javascript:funOnClickNextSubcatDetls('"+pagenumber+"','"+retailerid+"','"+retailername+"','"+state+"','"+eretailer_flag+"');\" > Next</a> </font>&nbsp;&nbsp;";
						strResSubcatdetls=strResSubcatdetls+"<font><a class='blue1' href=\"javascript:funOnClickLastSubcatDetls('"+Lastpage+"','"+pagenumber+"','"+retailerid+"','"+retailername+"','"+state+"','"+eretailer_flag+"');\" >Last</a>&nbsp;&nbsp;</font>";
					}
					strResSubcatdetls=strResSubcatdetls+"</td><td align='center' width='35%'><font class='blue1'>Showing page&nbsp;&nbsp; <select name='page' id='page' onChange=\"javascript:getpageSubcatDetls('"+retailerid+"','"+retailername+"','"+state+"','"+eretailer_flag+"');\">";
					String strsel="";
					for(int i=1;i<=tp;i++)
					{
						String si=Integer.toString(i);
						if(pagenumber.equals(si))
						{
							strsel="selected";
						}
						else
						{
							strsel="";
						}
						strResSubcatdetls=strResSubcatdetls+"<option value='"+i+"'"+strsel+">"+i+"</option>"; 
					}
					strResSubcatdetls=strResSubcatdetls+"</select>  of&nbsp;&nbsp;"; 
					if(Lastpage.equals("0"))
					{
						Lastpage="1";
					}
					else
					{
						Lastpage=Lastpage;
					}
					strResSubcatdetls=strResSubcatdetls+""+Lastpage+"</font></td></tr>";
					strResSubcatdetls=strResSubcatdetls+"</table></td></tr>";
					strResSubcatdetls=strResSubcatdetls+"<tr><td><table width='550' align='center'><tr><td align='center' valign='middle' class='prodheading'>SI.NO</td>";
					strResSubcatdetls=strResSubcatdetls+"<td align='center' valign='middle' class='prodheading'>RetailerName</td>";
					strResSubcatdetls=strResSubcatdetls+"<td align='center' valign='middle' class='prodheading'>Category</td>";
					strResSubcatdetls=strResSubcatdetls+"<td align='center' valign='middle' class='prodheading'>Subcategory</td>";
					strResSubcatdetls=strResSubcatdetls+"<td align='center' valign='middle' class='prodheading'>Brand</td></tr>";
					int k=1;
					for ( int j=0; j<Catdetails.size() ; j++)
					{
						Hashtable htCatdetails=(Hashtable)Catdetails.get(j);
						String htcategoryname=String.valueOf(htCatdetails.get("category_name"));
						String htsubcategoryname=String.valueOf(htCatdetails.get("subcategory_name"));
						String htbrandname=String.valueOf(htCatdetails.get("brandname"));
						String htvendorid=String.valueOf(htCatdetails.get("vendor_id"));
						String htcategoryid=String.valueOf(htCatdetails.get("category_id"));
						String htsubcategoryid=String.valueOf(htCatdetails.get("subcategory_id"));
						if (htbrandname.equals("default"))
						{
							htbrandname="NA";
						}
						else
						{
							htbrandname=htbrandname;	
						}
						String displaycategoryname="";
						if(htcategoryname.length()>15)
						{
							int Catlen = htcategoryname.length();
							int Cattotalchar = 15;
							for(int i=0; i<Catlen; i+=Cattotalchar)
							  {
								String part = htcategoryname.substring(i, Math.min(Catlen,i + Cattotalchar));
								displaycategoryname = displaycategoryname+part+"<br>";
							  }
						}
						else
						{	
							displaycategoryname = htcategoryname;
						}
						String displaysubcategoryname="";
						if(htsubcategoryname.length()>15)
						{
							int Subcatlen = htsubcategoryname.length();
							int Subcattotalchar = 15;
							for(int s=0; s<Subcatlen; s+=Subcattotalchar)
							  {
								String part = htsubcategoryname.substring(s, Math.min(Subcatlen,s + Subcattotalchar));
								displaysubcategoryname = displaysubcategoryname+part+"<br>";
							  }
						}
						else
						{	
							displaysubcategoryname = htsubcategoryname;
						}
						String displaybrandname="";
						if(htbrandname.length()>15)
						{
							int brandlen = htbrandname.length();
							int brandtotalchar = 15;
							for(int b=0; b<brandlen; b+=brandtotalchar)
							  {
								String part = htbrandname.substring(b, Math.min(brandlen,b + brandtotalchar));
								displaybrandname = displaybrandname+part+"<br>";
							  }
						}
						else
						{	
							displaybrandname = htbrandname;
						}
						strResSubcatdetls=strResSubcatdetls+"<tr><td align='center' valign='middle' class='table1'>"+k+"";
						strResSubcatdetls=strResSubcatdetls+"<input type='checkbox' name='check' value="+retailerid+"|"+htcategoryid+"|"+htsubcategoryid+"|"+htvendorid+"></td>";
						strResSubcatdetls=strResSubcatdetls+"<td align='center' valign='middle' class='table1'>"+retailername+"</td>";
						strResSubcatdetls=strResSubcatdetls+"<td align='center' valign='middle' class='table1'>"+displaycategoryname+"</td>";
						strResSubcatdetls=strResSubcatdetls+"<td align='center' valign='middle' class='table1'>"+displaysubcategoryname+"</td>";
						strResSubcatdetls=strResSubcatdetls+"<td align='center' valign='middle' class='table1'>"+displaybrandname+"</td></tr></td></tr>";
						k++;
					}
					strResSubcatdetls=strResSubcatdetls+"</table></td></tr></table></td></tr></table>";
					return strResSubcatdetls;
				}
			}
		}
		catch(Exception e)
		{
			LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
			e.printStackTrace();
			session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
			strResSubcatdetls=strResSubcatdetls+"Problem Caught Exception";
			return strResSubcatdetls;
		}
		return strResSubcatdetls;		
	}
	/* This method is to fetch the category, subcategory and brand details by onload.*/
	public String fetchsubcatbranddetailsonload (HttpServletRequest req , HttpServletResponse res, String struserName,HttpSession session)
    {
		String strResFetchSubcatdetls=""; 
		String retailerid = (req.getParameter("retailerid") != null)? (req.getParameter("retailerid")) : "0";
		String retailername = (req.getParameter("retailername") != null)? (req.getParameter("retailername")) : "0";
		String pagenumber = (req.getParameter("pagenumber")!=null)?(req.getParameter("pagenumber")):"0";
		String pagetype = (req.getParameter("pagetype")!=null)?(req.getParameter("pagetype")):"0";
		String lastpage = (req.getParameter("lastpage")!=null)?(req.getParameter("lastpage")):"0";
		String state=(req.getParameter("state")!=null)?(req.getParameter("state")):"0";
		LogLevel.DEBUG(5, new Throwable(), "state:" + state);
		String eretailerflag=(req.getParameter("eretailerflag")!=null)?(req.getParameter("eretailerflag")):"0";
		LogLevel.DEBUG(5, new Throwable(), "eretailerflag:" + eretailerflag);
		LogLevel.DEBUG(5, new Throwable(), "retailerid:" + retailerid);
		String stateold=(req.getParameter("stateold")!=null)?(req.getParameter("stateold")):"0";
		LogLevel.DEBUG(5, new Throwable(), "stateold:" + stateold);
 stateold=stateold.replaceAll(" ","_");
		String strCategory="";
		String categoryCount="";
		String statetable=state.replaceAll(" ","_");
		try
		{  
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
			int RETAILERS_PER_PAGE=10;
			int Startlimt=RETAILERS_PER_PAGE*(Integer.parseInt(pagenumber)-1);
			String Startindex=Integer.toString(Startlimt);
			if(eretailerflag.equals("false") || eretailerflag.equals("0")){
			strCategory = "select category_id,category_name,subcategory_id,subcategory_name,brandname,vendor_id from "+statetable+"_subcatbrandmap where retailer_id='"+retailerid+"' order by creation_date desc,brandname desc limit "+Startindex+",10 ";
			categoryCount="select count(category_name) as count from "+statetable+"_subcatbrandmap where retailer_id='"+retailerid+"' " ;
			}
			else if(eretailerflag.equals("true") || eretailerflag.equals("1")){
			strCategory = "select category_id,category_name,subcategory_id,subcategory_name,brandname,vendor_id from eretailers_subcatbrandmap where retailer_id='"+retailerid+"' order by creation_date desc,brandname desc limit "+Startindex+",10 ";
			categoryCount="select count(category_name) as count from eretailers_subcatbrandmap where retailer_id='"+retailerid+"' " ;
			}
			LogLevel.DEBUG(5, new Throwable(), "strCategory:" + strCategory);
			Hashtable htcount = qm.getRow(categoryCount);
			String strcount=String.valueOf(htcount.get("count"));
			Vector Catdetails= qm.executeQuery(strCategory);
			if(Catdetails==null || Catdetails.size() <=0 )
			{
				strResFetchSubcatdetls=strResFetchSubcatdetls+"<table width='100%' align='middle'><tr ><td class='insidecontent' align='center'> Please select atleast one category</td>";
				strResFetchSubcatdetls=strResFetchSubcatdetls+"</tr></table>";
			}
			else
			{
				int TotalCount=Integer.parseInt(strcount);
				int SUBCATDETLSPERPAGE=10;
				int tp=((int)(Math.ceil((double)TotalCount/SUBCATDETLSPERPAGE)));
				String Lastpage=Integer.toString(tp);
				strResFetchSubcatdetls=strResFetchSubcatdetls+"<tr><td colspan='6' ><hr width='500' align='center'  color='#BEADCB'></td></tr>";
				strResFetchSubcatdetls=strResFetchSubcatdetls+"<table align='center' border='0' cellpadding='0' cellspacing='0'>";
				strResFetchSubcatdetls=strResFetchSubcatdetls+"<tr id='button1' height='20'><td >&nbsp;&nbsp;&nbsp;<a href='javascript:deleteSubcategorybranddetails()' class='smallbutton'>Delete</a>&nbsp;&nbsp;&nbsp;</td></tr><tr height='10'></tr></table>";
				strResFetchSubcatdetls=strResFetchSubcatdetls+"<table cellpadding='2' cellspacing='2' bgcolor='#BEADCB' width='500' align='center'><tr><td><table width = '550' cellspacing = '0' cellpadding = '0'  bgcolor='#FFFFFF' align='center'>";
				strResFetchSubcatdetls=strResFetchSubcatdetls+"<tr><td colspan='8' align='center'><table border='0' align='center' width='550' ><tr><td width='300' align='center'> ";
				if(pagenumber.equals("1"))
				{
					strResFetchSubcatdetls=strResFetchSubcatdetls+"<font class='blue2'>First</font>&nbsp;&nbsp;";
					strResFetchSubcatdetls=strResFetchSubcatdetls+"<font class='blue2'>Previous</font>&nbsp;&nbsp;<input type='hidden' name='rettotalcount' id='rettotalcount' value='"+strcount+"'>";
				}
				else
				{
					strResFetchSubcatdetls=strResFetchSubcatdetls+"<font ><a class='blue1'	href=\"javascript:funOnClickFirstSubcatDetls('"+pagenumber+"','"+retailerid+"','"+retailername+"','"+state+"','"+eretailerflag+"');\">First</a></font>&nbsp;&nbsp;";
					strResFetchSubcatdetls=strResFetchSubcatdetls+"<font> <a class='blue1' href=\"javascript:funOnClickPreviousSubcatDetls('"+pagenumber+"','"+retailerid+"','"+retailername+"','"+state+"','"+eretailerflag+"');\" > Previous</a> </font>&nbsp;&nbsp;<input type='hidden' name='rettotalcount' id='rettotalcount' value='"+strcount+"'>";
				}
				if(pagenumber.equals(Lastpage))
				{
					strResFetchSubcatdetls=strResFetchSubcatdetls+"&nbsp;&nbsp;<font class='blue2'>Next</font>&nbsp;&nbsp;";
					strResFetchSubcatdetls=strResFetchSubcatdetls+"<font class='blue2'>Last</font>&nbsp;&nbsp;";
					strResFetchSubcatdetls=strResFetchSubcatdetls+"<input type='checkbox' name='check'  value=''style='display:none'/>";
					strResFetchSubcatdetls=strResFetchSubcatdetls+"<input type='checkbox' name='check'  value='' style='display:none'/>";
				}
				else
				{
					strResFetchSubcatdetls=strResFetchSubcatdetls+"&nbsp;&nbsp;<font><a class='blue1' href=\"javascript:funOnClickNextSubcatDetls('"+pagenumber+"','"+retailerid+"','"+retailername+"','"+state+"','"+eretailerflag+"');\" > Next</a> </font>&nbsp;&nbsp;";
					strResFetchSubcatdetls=strResFetchSubcatdetls+"<font><a class='blue1' href=\"javascript:funOnClickLastSubcatDetls('"+Lastpage+"','"+pagenumber+"','"+retailerid+"','"+retailername+"','"+state+"','"+eretailerflag+"');\" >Last</a>&nbsp;&nbsp;</font>";
				}
				strResFetchSubcatdetls=strResFetchSubcatdetls+"</td><td align='left' width='250'><font class='blue1'>Showing page&nbsp;&nbsp; <select name='page' id='page' onChange=\"javascript:getpageSubcatDetls('"+retailerid+"','"+retailername+"','"+state+"','"+eretailerflag+"');\">";
				String strsel="";
				for(int i=1;i<=tp;i++)
				{
					String si=Integer.toString(i);
					if(pagenumber.equals(si))
					{
						strsel="selected";
					}
					else
					{
						strsel="";
					}
					strResFetchSubcatdetls=strResFetchSubcatdetls+"<option value='"+i+"'"+strsel+">"+i+"</option>"; 
				}
				strResFetchSubcatdetls=strResFetchSubcatdetls+"</select>  of&nbsp;&nbsp;"; 
				if(Lastpage.equals("0"))
				{
					Lastpage="1";
				}
				else
				{
					Lastpage=Lastpage;
				}
				strResFetchSubcatdetls=strResFetchSubcatdetls+""+Lastpage+"</font></td></tr>";
				strResFetchSubcatdetls=strResFetchSubcatdetls+"</table></td></tr>";
				strResFetchSubcatdetls=strResFetchSubcatdetls+"<tr><td><table width='550' align='center'><tr><td align='center' valign='middle' class='prodheading'>SI.NO</td>";
				strResFetchSubcatdetls=strResFetchSubcatdetls+"<td align='center' valign='middle' class='prodheading'>RetailerName</td>";
				strResFetchSubcatdetls=strResFetchSubcatdetls+"<td align='center' valign='middle' class='prodheading'>Category</td>";
				strResFetchSubcatdetls=strResFetchSubcatdetls+"<td align='center' valign='middle' class='prodheading'>Subcategory</td>";
				strResFetchSubcatdetls=strResFetchSubcatdetls+"<td align='center' valign='middle' class='prodheading'>Brand</td></tr>";
				for ( int j=0; j<Catdetails.size() ; j++)
				{
					Hashtable htCatdetails=(Hashtable)Catdetails.get(j);
					String htcategoryname=String.valueOf(htCatdetails.get("category_name"));
					String htsubcategoryname=String.valueOf(htCatdetails.get("subcategory_name"));
					String htbrandname=String.valueOf(htCatdetails.get("brandname"));
					String htvendorid=String.valueOf(htCatdetails.get("vendor_id"));
					String htcategoryid=String.valueOf(htCatdetails.get("category_id"));
					String htsubcategoryid=String.valueOf(htCatdetails.get("subcategory_id"));
					int Jcount = j+1;
					int Startin=Integer.parseInt(Startindex);
					int RetCount=Jcount + Startin;
					if (htbrandname.equals("default"))
					{
						htbrandname="NA";
					}
					else
					{
						htbrandname=htbrandname;	
					}
					String displaycategoryname="";
					if(htcategoryname.length()>15)
					{
						int Catlen = htcategoryname.length();
						int Cattotalchar = 15;
						for(int i=0; i<Catlen; i+=Cattotalchar)
						{
							String part = htcategoryname.substring(i, Math.min(Catlen,i + Cattotalchar));
							displaycategoryname = displaycategoryname+part+"<br>";
						}
					}
					else
					{	
						displaycategoryname = htcategoryname;
					}
					String displaysubcategoryname="";
					if(htsubcategoryname.length()>15)
					{
						int Subcatlen = htsubcategoryname.length();
						int Subcattotalchar = 15;
						for(int s=0; s<Subcatlen; s+=Subcattotalchar)
						  {
							String part = htsubcategoryname.substring(s, Math.min(Subcatlen,s + Subcattotalchar));
							displaysubcategoryname = displaysubcategoryname+part+"<br>";
						  }
					}
					else
					{	
						displaysubcategoryname = htsubcategoryname;
					}
					String displaybrandname="";
					if(htbrandname.length()>15)
					{
						int brandlen = htbrandname.length();
						int brandtotalchar = 15;
						for(int b=0; b<brandlen; b+=brandtotalchar)
						  {
							String part = htbrandname.substring(b, Math.min(brandlen,b + brandtotalchar));
							displaybrandname = displaybrandname+part+"<br>";
						  }
					}
					else
					{	
						displaybrandname = htbrandname;
					}
					strResFetchSubcatdetls=strResFetchSubcatdetls+"<tr><td align='center' valign='middle' class='table1'>"+RetCount+"";
					strResFetchSubcatdetls=strResFetchSubcatdetls+"<input type='checkbox' name='check' value="+retailerid+"|"+htcategoryid+"|"+htsubcategoryid+"|"+htvendorid+"></td>";
					strResFetchSubcatdetls=strResFetchSubcatdetls+"<td align='center' valign='middle' class='table1'>"+retailername+"</td>";
					strResFetchSubcatdetls=strResFetchSubcatdetls+"<td align='center' valign='middle' class='table1'>"+displaycategoryname+"</td>";
					strResFetchSubcatdetls=strResFetchSubcatdetls+"<td align='center' valign='middle' class='table1'>"+displaysubcategoryname+"</td>";
					strResFetchSubcatdetls=strResFetchSubcatdetls+"<td align='center' valign='middle' class='table1'>"+displaybrandname+"</td></tr></td></tr>";
				}
				strResFetchSubcatdetls=strResFetchSubcatdetls+"</table></td></tr></table></td></tr></table></td></tr></table>";
				return strResFetchSubcatdetls;
			}
		}
		catch(Exception e)
		{
			LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
			e.printStackTrace();
			session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
			strResFetchSubcatdetls=strResFetchSubcatdetls+"Problem Caught Exception";
			return strResFetchSubcatdetls;
		}
		return strResFetchSubcatdetls;
	}
	/* This method is to delete the subcategory, brand details of a retailer and to fetch the remaining category, subcategory and brand details.*/
    public String deletesubcategorybranddetails (HttpServletRequest req , HttpServletResponse res, String struserName,HttpSession session)
    {
		String strResDelSubcatdetls="";
		String retailerid = (req.getParameter("retailerid") != null)? (req.getParameter("retailerid")) : "0";
		String categoryname = (req.getParameter("categoryname") != null)? (req.getParameter("categoryname")) : "0";
		String subcategoryname = (req.getParameter("subcategoryname") != null)? (req.getParameter("subcategoryname")) : "0";
		String vendorid = (req.getParameter("vendorid") != null)? (req.getParameter("vendorid")) : "0";
		String categoryid = (req.getParameter("categoryid") != null)? (req.getParameter("categoryid")) : "0";
		String subcategoryid = (req.getParameter("subcategoryid") != null)? (req.getParameter("subcategoryid")) : "0";
		//String pagenumber = (req.getParameter("pagenumber")!=null)?(req.getParameter("pagenumber")):"0";
		String pagetype = (req.getParameter("pagetype")!=null)?(req.getParameter("pagetype")):"0";
		String lastpage = (req.getParameter("lastpage")!=null)?(req.getParameter("lastpage")):"0";
		String state = (req.getParameter("state") != null)? (req.getParameter("state")) : "0";
		String eretailer_flag= (req.getParameter("eretailer_flag")!=null)?(req.getParameter("eretailer_flag")):"";
		String deletearray= (req.getParameter("deletearray")!=null)?(req.getParameter("deletearray")):"";
		String delSubcatdetails="";
		String strSqlQryCat="";
		String categoryCount="";
		String fetchsubcat="";
		String strretid="";
		String statetable=state.replaceAll(" ","_");
		String previouspagenumber  = (req.getParameter("page")!=null)?req.getParameter("page"):"";
		String originalpagenumber  = "";
		String pagenumber="";
		String Startindex="";
		try
		{  
			if((struserName==null) || (struserName==""))
			{
				strResDelSubcatdetls=strResDelSubcatdetls+"Session Expired Please login to delete retailer";
			}
			else
			{
				String[] RetDelArray=deletearray.split(",");
				for(int j=0;j<RetDelArray.length;j++)
				{	
				String retbeforesplit=RetDelArray[j].toString();	
				String[] parts = retbeforesplit.split("\\|");
				retailerid = parts[0]; 
				categoryid = parts[1]; 
				subcategoryid = parts[2]; 
				vendorid = parts[3]; 
				if(eretailer_flag.equals("0") || eretailer_flag.equals("false")){
				
				/*query used to delete retailer from retailers_subcatbrandmap table*/
				delSubcatdetails = "delete from "+statetable+"_subcatbrandmap where retailer_id ="+retailerid+" and category_id="+categoryid+" and subcategory_id="+subcategoryid+" and vendor_id="+vendorid+"";
				
				}
				else if(eretailer_flag.equals("1") || eretailer_flag.equals("true")){
				
				/*query used to delete retailer from retailers_subcatbrandmap table*/
				delSubcatdetails = "delete from eretailers_subcatbrandmap where retailer_id ="+retailerid+" and category_id="+categoryid+" and subcategory_id="+subcategoryid+" and vendor_id="+vendorid+"";
				
				} 
				int delretsubcatbrandmap=qm.executeUpdate(delSubcatdetails);
				/*query used to delete retailer from impression table */
				String delQueryimpression = "delete from impression where retailer_id ="+retailerid+" and category_id="+categoryid+" and subcategory_id="+subcategoryid+" and vendor_id="+vendorid+"";
				int delimpression=qm.executeUpdate(delQueryimpression);
				if(eretailer_flag.equals("false") || eretailer_flag.equals("0")){
				fetchsubcat="select distinct subcategory_id from "+statetable+"_subcatbrandmap where retailer_id ="+retailerid+" and vendor_id="+vendorid+"";
				}
				else if(eretailer_flag.equals("true") || eretailer_flag.equals("1")){
				fetchsubcat="select distinct subcategory_id from eretailers_subcatbrandmap where retailer_id ="+retailerid+" and vendor_id="+vendorid+"";
				}
				Hashtable htfetchsubcat=qm.getRow(fetchsubcat);
				if (htfetchsubcat.isEmpty())
				{
					/*query used to delete retailer from leads table */
					String delQueryleadswithoutsubcat = "delete from leads where retailer_id ="+retailerid+" and subcategory_id=0 and vendor_id="+vendorid+" ";
					int delleadswithoutsubcat=qm.executeUpdate(delQueryleadswithoutsubcat);
				}

				/*query used to delete retailer from leads table */
				String delQueryleads = "delete from leads where retailer_id ="+retailerid+" and subcategory_id="+subcategoryid+" and vendor_id="+vendorid+" ";
				int delleads=qm.executeUpdate(delQueryleads);

				/*query used to delete retailer from referprod_frnds_det table */
				String delQueryrefer = "delete from referprod_frnds_det where retailer_id ="+retailerid+" and subcategory_id="+subcategoryid+" and vendor_id="+vendorid+"";
				int delreferprodfrnds=qm.executeUpdate(delQueryrefer);

				/*query used to delete retailer from complaints_master table */
				String delQuerycomplaints = "delete from complaints_master where retiler_id="+retailerid+" and catgory_id="+subcategoryid+" and vendor_id="+vendorid+"";
				int delcmplntsmaster=qm.executeUpdate(delQuerycomplaints);

				/*query used to delete retailer from review_master table */
				String delQueryreview = "delete from review_master where retiler_id="+retailerid+" and catgory_id="+subcategoryid+" and vendor_id="+vendorid+"";
				int delreviewmaster=qm.executeUpdate(delQueryreview);
				if(eretailer_flag.equals("0") || eretailer_flag.equals("false")){
				/*query used to select the retailer id based on subcategory id and retailer iad*/
				strretid="select retailer_id from "+statetable+"_subcatbrandmap where retailer_id ="+retailerid+" and	subcategory_id="+subcategoryid+"";	
}
				else if(eretailer_flag.equals("1") || eretailer_flag.equals("true")){
				/*query used to select the retailer id based on subcategory id and retailer iad*/
				strretid="select retailer_id from eretailers_subcatbrandmap where retailer_id ="+retailerid+" and	subcategory_id="+subcategoryid+"";
				}
				ArrayList retailerids=qm.getField(strretid);
				if(retailerids.size() == 0)
				{
				/*query used to delete retailer from deals table */
				String delQuerydealssubcat = "delete from deals where retailer_id ="+retailerid+" and category_id="+categoryid+" and subcategory_id='"+subcategoryid+"'";
				int deletedeals=qm.executeUpdate(delQuerydealssubcat);

				/*query used to delete retailer from impression table */
				String delQueryimpressionsubcat = "delete from impression where retailer_id ="+retailerid+" and category_id="+categoryid+" and subcategory_id="+subcategoryid+" ";
				int deleteimpression=qm.executeUpdate(delQueryimpressionsubcat);

				/*query used to delete retailer from leads table */
				String delQueryleadssubcat = "delete from leads where retailer_id ="+retailerid+" and subcategory_id="+subcategoryid+" ";
				int deleteleads=qm.executeUpdate(delQueryleadssubcat);

				/*query used to delete retailer from referprod_frnds_det table */
				String delQueryrefersubcat = "delete from referprod_frnds_det where retailer_id ="+retailerid+" and subcategory_id="+subcategoryid+" ";
				int deletereferprodfrnds=qm.executeUpdate(delQueryrefersubcat);

				/*query used to delete retailer from complaints_master table */
				String delQuerycomplaintssubcat = "delete from complaints_master where retiler_id="+retailerid+" and catgory_id="+subcategoryid+" ";
				int deletecmplntsmaster=qm.executeUpdate(delQuerycomplaintssubcat);

				/*query used to delete retailer from review_master table */
				String delQueryreviewsubcat = "delete from review_master where retiler_id="+retailerid+" and catgory_id="+subcategoryid+" ";
					int deletereview_master=qm.executeUpdate(delQueryreviewsubcat);
				}
				else
				{
					LogLevel.DEBUG(5,new Throwable(),"retailerid else :"+retailerids );
				}
				/*query used to delete retailer from user_complaints_table table */
				String delQueryusercom1 = "delete from user_complaints_table where retailer_id ="+retailerid+" and catgory_id="+subcategoryid+" and vendor_id="+vendorid+"";
				int deleteusercmplnts=qm.executeUpdate(delQueryusercom1);
				}
				int previousretcount=Integer.parseInt(previouspagenumber);
				/*query used to fetch subcat details count for a retailer*/
				if(eretailer_flag.equals("0") || eretailer_flag.equals("false"))
				{
					categoryCount="select count(category_name) as count from "+statetable+"_subcatbrandmap where retailer_id='"+retailerid+"' " ;		
				}
				else if(eretailer_flag.equals("1") || eretailer_flag.equals("true"))
				{
					categoryCount="select count(category_name) as count from eretailers_subcatbrandmap where retailer_id='"+retailerid+"'" ;	
				}
				Hashtable htcount = qm.getRow(categoryCount);
				String strcount=String.valueOf(htcount.get("count"));
				int TotalCount=Integer.parseInt(strcount);
				int SUBCATDETLSPERPAGE=10;
				int tp=((int)(Math.ceil((double)TotalCount/SUBCATDETLSPERPAGE)));
				String Lastpage=Integer.toString(tp);
				LogLevel.DEBUG(1,new Throwable(),"passedcount:"+previousretcount);
				LogLevel.DEBUG(1,new Throwable(),"originalcount:"+tp);
				if ((tp == previousretcount) || (tp < previousretcount))
				{
					pagenumber = Lastpage;
				}
				else
				{
					pagenumber = previouspagenumber;
				}
				LogLevel.DEBUG(1,new Throwable(),"pagenumber:"+pagenumber);
				int RETAILERS_PER_PAGE=10;
				int Startlimt=RETAILERS_PER_PAGE*(Integer.parseInt(pagenumber)-1);
				Startindex=Integer.toString(Startlimt);
				/*query used to fetch subcat details count for a retailer*/
				if(eretailer_flag.equals("0") || eretailer_flag.equals("false"))
				{
					/*query used to fetch category name, subcategory name, brand name, vendor_id based on retailer_id*/
					strSqlQryCat = "select category_id,category_name,subcategory_id,subcategory_name,brandname,vendor_id from "+statetable+"_subcatbrandmap where retailer_id='"+retailerid+"' order by creation_date desc,brandname desc limit "+Startindex+",10 ";
				}
				else if(eretailer_flag.equals("1") || eretailer_flag.equals("true"))
				{
					/*query used to fetch category name, subcategory name, brand name, vendor_id based on retailer_id*/
					strSqlQryCat = "select category_id,category_name,subcategory_id,subcategory_name,brandname,vendor_id from eretailers_subcatbrandmap where retailer_id='"+retailerid+"' order by creation_date desc,brandname desc limit "+Startindex+",10 ";
				}
				LogLevel.DEBUG(1,new Throwable(),"strSqlQryCat:"+strSqlQryCat);
				/*query used to fetch retailer name based on retailer_id*/
				String fetchRetname="select retailer_name from retailers where retailer_id='"+retailerid+"'" ;
				Hashtable htRetname = qm.getRow(fetchRetname);
				String retailername=String.valueOf(htRetname.get("retailer_name"));
				strResDelSubcatdetls=strResDelSubcatdetls+"<tr><td colspan='6' ><hr width='600' align='center'  color='#BEADCB'></td></tr>";	
				strResDelSubcatdetls=strResDelSubcatdetls+"<table align='center' border='0' cellpadding='0' cellspacing='0'>";
				strResDelSubcatdetls=strResDelSubcatdetls+"<tr id='button1' height='20'><td >&nbsp;&nbsp;&nbsp;<a href='javascript:deleteSubcategorybranddetails()' class='smallbutton'>Delete</a>&nbsp;&nbsp;&nbsp;</td></tr><tr height='10'></tr></table>";
				strResDelSubcatdetls=strResDelSubcatdetls+"<table width ='600' cellspacing = '2' cellpadding = '2'  bgcolor='#BEADCB' align='center'>";	
				strResDelSubcatdetls=strResDelSubcatdetls+"<tr><td><table width = '600' cellspacing = '0' cellpadding = '0'  bgcolor='#FFFFFF' align='center'>";
				Vector Catdetails= qm.executeQuery(strSqlQryCat);
				if(Catdetails==null || Catdetails.size() <=0 )
				{
					strResDelSubcatdetls=strResDelSubcatdetls+"<tr ><td class='click1' align='center'> No categories are available.please select atleast one category </td>";
					strResDelSubcatdetls=strResDelSubcatdetls+"</tr>";
				}
				else
				{
					strResDelSubcatdetls=strResDelSubcatdetls+"<tr><td colspan='8' align='center'><table border='0' align='center' width='550' ><tr><td width='300' align='center'>";
					if(pagenumber.equals("1"))
					{
						strResDelSubcatdetls=strResDelSubcatdetls+"<font class='blue2'>First</font>&nbsp;&nbsp;";
						strResDelSubcatdetls=strResDelSubcatdetls+"<font class='blue2'>Previous</font>&nbsp;&nbsp;<input type='hidden' name='rettotalcount' id='rettotalcount' value='"+strcount+"'>";
					}
					else
					{
						strResDelSubcatdetls=strResDelSubcatdetls+"<font ><a class='blue1'	href=\"javascript:funOnClickFirstSubcatDetls('"+pagenumber+"','"+retailerid+"','"+retailername+"');\">First</a></font>&nbsp;&nbsp;<input type='hidden' name='rettotalcount' id='rettotalcount' value='"+strcount+"'>";
						strResDelSubcatdetls=strResDelSubcatdetls+"<font> <a class='blue1' href=\"javascript:funOnClickPreviousSubcatDetls('"+pagenumber+"','"+retailerid+"','"+retailername+"');\" >Previous</a> </font>&nbsp;&nbsp;";
					}
					if(pagenumber.equals(Lastpage))
					{
						strResDelSubcatdetls=strResDelSubcatdetls+"&nbsp;&nbsp;<font class='blue2'>Next</font>&nbsp;&nbsp;";
						strResDelSubcatdetls=strResDelSubcatdetls+"<font class='blue2'>Last</font>&nbsp;&nbsp;<input type='hidden' name='rettotalcount' id='rettotalcount' value='"+strcount+"'>";
					}
					else
					{
						strResDelSubcatdetls=strResDelSubcatdetls+"&nbsp;&nbsp;<font><a class='blue1' href=\"javascript:funOnClickNextSubcatDetls('"+pagenumber+"','"+retailerid+"','"+retailername+"');\" > Next</a> </font>&nbsp;&nbsp;<input type='hidden' name='rettotalcount' id='rettotalcount' value='"+strcount+"'>";
						strResDelSubcatdetls=strResDelSubcatdetls+"<font><a class='blue1' href=\"javascript:funOnClickLastSubcatDetls('"+Lastpage+"','"+pagenumber+"','"+retailerid+"','"+retailername+"');\" >Last</a>&nbsp;&nbsp;</font>";
					}
					strResDelSubcatdetls=strResDelSubcatdetls+"</td><td align='left' width='250'><font class='blue1'>Showing page&nbsp;&nbsp; <select name='page' id='page' onChange=\"javascript:getpageSubcatDetls('"+retailerid+"','"+retailername+"');\">";
					String strsel="";
					for(int i=1;i<=tp;i++)
					{
						String si=Integer.toString(i);
						if(pagenumber.equals(si))
						{
							strsel="selected";
						}
						else
						{
							strsel="";
				
						}
						strResDelSubcatdetls=strResDelSubcatdetls+"<option value='"+i+"'"+strsel+">"+i+"</option>"; 
					}
					strResDelSubcatdetls=strResDelSubcatdetls+"</select>  of&nbsp;&nbsp;"; 
					if(Lastpage.equals("0"))
					{
						Lastpage="1";
					}
					else
					{
						Lastpage=Lastpage;
					}
					strResDelSubcatdetls=strResDelSubcatdetls+""+Lastpage+"</font></td></tr>";
					strResDelSubcatdetls=strResDelSubcatdetls+"</table></td></tr>";
					strResDelSubcatdetls=strResDelSubcatdetls+"<tr><td><table width='600' align='center'><tr><td align='center' valign='middle' class='prodheading'>SI.NO</td>";
					strResDelSubcatdetls=strResDelSubcatdetls+"<td align='center' valign='middle' class='prodheading'>RetailerName</td>";
					strResDelSubcatdetls=strResDelSubcatdetls+"<td align='center' valign='middle' class='prodheading'>Category</td>";
					strResDelSubcatdetls=strResDelSubcatdetls+"<td align='center' valign='middle' class='prodheading'>Subcategory</td>";
					strResDelSubcatdetls=strResDelSubcatdetls+"<td align='center' valign='middle' class='prodheading'>Brand</td></tr>";
					//int k=1;
					for ( int j=0; j<Catdetails.size() ; j++)
					{
						Hashtable htCatdetails=(Hashtable)Catdetails.get(j);
						String htcategoryname=String.valueOf(htCatdetails.get("category_name"));
						String htsubcategoryname=String.valueOf(htCatdetails.get("subcategory_name"));
						String htbrandname=String.valueOf(htCatdetails.get("brandname"));
						String htvendorid=String.valueOf(htCatdetails.get("vendor_id"));
						String htcategoryid=String.valueOf(htCatdetails.get("category_id"));
						String htsubcategoryid=String.valueOf(htCatdetails.get("subcategory_id"));
						int count = j+1;
						int Startin=Integer.parseInt(Startindex);
						int k=count + Startin;
						if (htbrandname.equals("default"))
						{
							htbrandname="NA";
						}
						else
						{
							htbrandname=htbrandname;	
						}
						String displaycategoryname="";
						if(htcategoryname.length()>15)
						{
							int Catlen = htcategoryname.length();
							int Cattotalchar = 15;
							for(int i=0; i<Catlen; i+=Cattotalchar)
							  {
								String part = htcategoryname.substring(i, Math.min(Catlen,i + Cattotalchar));
								displaycategoryname = displaycategoryname+part+"<br>";
							  }
						}
						else
						{	
							displaycategoryname = htcategoryname;
						}
						String displaysubcategoryname="";
						if(htsubcategoryname.length()>15)
						{
							int Subcatlen = htsubcategoryname.length();
							int Subcattotalchar = 15;
							for(int s=0; s<Subcatlen; s+=Subcattotalchar)
							  {
								String part = htsubcategoryname.substring(s, Math.min(Subcatlen,s + Subcattotalchar));
								displaysubcategoryname = displaysubcategoryname+part+"<br>";
							  }
						}
						else
						{	
							displaysubcategoryname = htsubcategoryname;
						}
						String displaybrandname="";
						if(htbrandname.length()>15)
						{
							int brandlen = htbrandname.length();
							int brandtotalchar = 15;
							for(int b=0; b<brandlen; b+=brandtotalchar)
							  {
								String part = htbrandname.substring(b, Math.min(brandlen,b + brandtotalchar));
								displaybrandname = displaybrandname+part+"<br>";
							  }
						}
						else
						{	
							displaybrandname = htbrandname;
						}
						strResDelSubcatdetls=strResDelSubcatdetls+"<tr><td align='center' valign='middle' class='table1'>"+k+"";
						strResDelSubcatdetls=strResDelSubcatdetls+"<input type='checkbox' name='check' value="+retailerid+"|"+htcategoryid+"|"+htsubcategoryid+"|"+htvendorid+"></td>";
						strResDelSubcatdetls=strResDelSubcatdetls+"<td align='center' valign='middle' class='table1'>"+retailername+"</td>";
						strResDelSubcatdetls=strResDelSubcatdetls+"<td align='center' valign='middle' class='table1'>"+displaycategoryname+"</td>";
						strResDelSubcatdetls=strResDelSubcatdetls+"<td align='center' valign='middle' class='table1'>"+displaysubcategoryname+"</td>";
						strResDelSubcatdetls=strResDelSubcatdetls+"<td align='center' valign='middle' class='table1'>"+displaybrandname+"</td></tr></td></tr>";
						//k++;
					}
					strResDelSubcatdetls=strResDelSubcatdetls+"</table></td></tr></table></td></tr></table>";
					return strResDelSubcatdetls;
				}
			}
		}
		catch(Exception e)
		{
			LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
			e.printStackTrace();
			session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
			strResDelSubcatdetls=strResDelSubcatdetls+"Problem Caught Exception";
			return strResDelSubcatdetls;
		}
		return strResDelSubcatdetls;		
	}
	/* This method is to check the existing retailer login name*/
	public String checkretailerloginname (HttpServletRequest req , HttpServletResponse res, String struserName,HttpSession session)
    {
		String strResCheckRetloginname="";
		String retailerloginname = (req.getParameter("retailerloginname")!=null)?(req.getParameter("retailerloginname")):"0";
		try
		{  
			/*query used to fetch retailer_loginname from retailers based on entered loginname*/
			String strRetloginname = "SELECT retailer_loginname FROM retailers WHERE retailer_loginname='"+retailerloginname+"'"; 
			Hashtable htretloginname = qm.getRow(strRetloginname);
			if(htretloginname.isEmpty() )
			{ 
				strResCheckRetloginname=strResCheckRetloginname+"No Retailer Login Name exists u can continue";
			}
			else
			{
				strResCheckRetloginname=strResCheckRetloginname+"Retailer Login Name already exists";
			}
		 }
		 catch(Exception e)
		 {
			LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
			e.printStackTrace();
			session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
			strResCheckRetloginname=strResCheckRetloginname+"Problem Caught Exception";
		 }
		 return strResCheckRetloginname;
	}
	/*This method is used to display the message whether retailer name it is already exist or not*/
    public String checkretailernamemessage (HttpServletRequest req , HttpServletResponse res, String struserName,HttpSession session)
    {
		String strResCheckRetsesmsg="";
		String retailername = (req.getParameter("retailername")!=null)?(req.getParameter("retailername")):"";
		String eretailerfalg = (req.getParameter("eretailerfalg")!=null)?(req.getParameter("eretailerfalg")):"";
		String state = (req.getParameter("state")!=null)?(req.getParameter("state")):"";
		String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
		String strRetname="";
		String statetable=state.replaceAll(" ","_");
		try
		{  
			/*query used to fetch retailer name from retailers based on entered retailer name*/
			if(eretailerfalg.equals("0"))
			{
			strRetname = "SELECT retailer_name FROM "+statetable+"_retailers WHERE retailer_name ='"+retailername+"' and city='"+city+"'"; 
			}
			else
			{
			strRetname = "SELECT retailer_name FROM eretailers WHERE retailer_name ='"+retailername+"'"; 
			}
			//String strRetname = "SELECT retailer_name FROM retailers WHERE retailer_name ='"+retailername+"'"; 
			Hashtable htRetname = qm.getRow(strRetname);
			if( htRetname.isEmpty())
			{ 
				strResCheckRetsesmsg=strResCheckRetsesmsg+"No Retailer Name exists u can continue";
			}
			else
			{
				strResCheckRetsesmsg=strResCheckRetsesmsg+"Retailer Name already exists";
			}
		}
		catch(Exception e)
		{
			LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
			e.printStackTrace();
			session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
			strResCheckRetsesmsg=strResCheckRetsesmsg+"Problem Caught Exception";
		}
		return strResCheckRetsesmsg;
	}
	/*This method is used to check the existing area name*/
	public String areanamecheck (HttpServletRequest req , HttpServletResponse res, String struserName,HttpSession session)
	{
		String strResAreacheck="";
		String strareaname = (req.getParameter("areaname")!=null)?(req.getParameter("areaname")):"";
		String strcity = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
		String state = (req.getParameter("state")!=null)?(req.getParameter("state")):"";
		String flag = (req.getParameter("flag")!=null)?(req.getParameter("flag")):"";
		String statetable=state.replaceAll(" ","_");
		String strsqlArea="";
		try
		{ 	if(flag.equals("ret")){
				if(strcity=="0" || strcity.equals("0"))
				{
					strsqlArea = "SELECT distinct(area) FROM "+statetable+"_retailers WHERE  area like '"+strareaname+"%' limit 0,10"; 
				}
				else
				{
					strsqlArea = "SELECT distinct(area) FROM "+statetable+"_retailers WHERE city='"+strcity+"' and  area like '"+strareaname+"%' limit 0,10"; 
				}
			}
			else if(flag.equals("eret")){
			
				if(state=="0")
				{
					strsqlArea = "SELECT distinct(area) FROM eretailers WHERE area like '"+strareaname+"%' limit 0,10"; 
				}
				else
				{
			strsqlArea = "SELECT distinct(area) FROM eretailers WHERE area like '"+strareaname+"%' and state='"+state+"' limit 0,10"; 
				}
					
			}
			Vector vectorGetAreaname= qm.executeQuery(strsqlArea);
			strResAreacheck=strResAreacheck+"<table width='150' border='0' bgcolor='#FFFFFF'>";
			if(vectorGetAreaname==null || vectorGetAreaname.size() <=0 )
			{
				strResAreacheck=strResAreacheck+"<tr ><td class='click1' align='left'> </td>";
				strResAreacheck=strResAreacheck+"</tr>";
			}
			else
			{
				strResAreacheck=strResAreacheck+"<table  width='150' border='0' align='center' valign='top'><tr  class='insidecontent'><td width='80%' valign='middle' align='center' class='insidecontent'>Existing Area Name with <font class='click1'>'"+strareaname+"' </font></td></tr>";
				for ( int k=0; k<vectorGetAreaname.size(); k++)
				{
					Hashtable htArea=(Hashtable)vectorGetAreaname.get(k);
					String strArea=String.valueOf(htArea.get("area"));
					strResAreacheck=strResAreacheck+"<tr><td ><hr size='0' color='#BEADCB' width='150'/></td></tr>";
					strResAreacheck=strResAreacheck+"<tr ><td align='left'><a  class='click1' href=\"javascript:onClickAreaname('"+strArea+"');onClickAreanameeret('"+strArea+"');\"> "+strArea+"</a></td></tr>";
				}
				strResAreacheck=strResAreacheck+"</table>";
			}
		} 
		catch(Exception e)
		{ 
			LogLevel.ERROR(0,e,"problem Caught Exception  !! "+e);
			e.printStackTrace(); 
			session.setAttribute("sesErrorMsg",	"Error...Please Try Again" );
		} 
		strResAreacheck=strResAreacheck+"</table>";							
		return strResAreacheck;	
	}
public String checkretailername (HttpServletRequest req , HttpServletResponse res, String struserName,HttpSession session)
    {
		String strResCheckRetname="";
		String retailername = (req.getParameter("retailername")!=null)?(req.getParameter("retailername")):"0";
		LogLevel.DEBUG(5,new Throwable(),"retailernamechandu :"+retailername);
		String eretailerflag = (req.getParameter("eretailerflag")!=null)?(req.getParameter("eretailerflag")):"0";
		String state = (req.getParameter("state")!=null)?(req.getParameter("state")):"0";
		String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"0";
		String strRetname="";
		String statetable=state.replaceAll(" ","_");
		try
		{  
			if(eretailerflag.equals("0"))
			{
			strRetname = "SELECT retailer_name FROM "+statetable+"_retailers WHERE city='"+city+"' and retailer_name like '"+retailername+"%' limit 0,10"; 
			}
			else
			{
			strRetname = "SELECT retailer_name FROM eretailers WHERE retailer_name like '"+retailername+"%' limit 0,10";			
			}
			//String strRetname = "SELECT retailer_name FROM retailers WHERE retailer_name like '"+retailername+"%' limit 0,10"; 
			Vector vectorGetRetName= qm.executeQuery(strRetname);
			strResCheckRetname="<table  width='150' border='0' bgcolor='#FFFFFF'>";
			if(vectorGetRetName==null || vectorGetRetName.size() <=0)
			{
				strResCheckRetname=strResCheckRetname+"<tr ><td class='click1' align='left'> </td>";
				strResCheckRetname=strResCheckRetname+"</tr>";
			}
			else
			{
				strResCheckRetname=strResCheckRetname+"<table  width='150' border='0' align='center' valign='top'><tr  class='insidecontent'><td width='80%' valign='middle' align='center' class='insidecontent'>Existing Retailer Name with <font class='click1'>'"+retailername+"'</font> </td></tr>";
				for ( int k=0; k<vectorGetRetName.size() ; k++)
				{
					Hashtable htRetname=(Hashtable)vectorGetRetName.get(k);
					String DisplayRetname=String.valueOf(htRetname.get("retailer_name"));
					strResCheckRetname=strResCheckRetname+"<tr><td ><hr size='0' color='#BEADCB' width='150'/></td></tr>";
					strResCheckRetname=strResCheckRetname+"<tr ><td class='click1' align='left'> "+DisplayRetname+"</td></tr>";
				}
				strResCheckRetname=strResCheckRetname+"</table>";
			}
		} 
		catch(Exception e)
		{ 
			LogLevel.ERROR(0,e,"problem Caught Exception !! "+e);
			e.printStackTrace(); 
			session.setAttribute("sesErrorMsg",	"Error...Please Try Again" );
		} 
		strResCheckRetname=strResCheckRetname+"</table>";							
		return strResCheckRetname;	
	}
	
	/*****this method is added to insert GST Details**/

	public String insertgstiddetails(HttpServletRequest req , HttpServletResponse res, String struserName,HttpSession session)
    {
		String strResGSTRetname="";
		String retailername = (req.getParameter("retailername")!=null)?(req.getParameter("retailername")):"0";
		LogLevel.DEBUG(5,new Throwable(),"retailername :"+retailername);
		String gstid = (req.getParameter("gstid")!=null)?(req.getParameter("gstid")):"0";
		LogLevel.DEBUG(5,new Throwable(),"gstid :"+gstid);
		String state = (req.getParameter("state")!=null)?(req.getParameter("state")):"0";
		String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"0";
		String strRetname="";
		String statetable=state.replaceAll(" ","_");
		String[] retailername1 = retailername.split(",");
		String retid = retailername1[0];
		String retname = retailername1[1];
		
		
		try
		{  
			String strSqlQry4 = "select retailerid from gstdetailstable where retailerid="+retid+"";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQry4 :"+strSqlQry4 );

			Vector RetailerVector=qm.executeQuery(strSqlQry4);
			LogLevel.DEBUG(5,new Throwable(),"RetailerVector:"+RetailerVector);
			
			if(RetailerVector==null || RetailerVector.size() == 0)
			{
				
				String insergstretailer = "insert into gstdetailstable(state,city,retailerid,retailername,gstid) values('"+state+"','"+city+"','"+retid+"','"+retname+"','"+gstid+"')";
				
				int Insertgstdetails = qm.executeUpdate(insergstretailer);
				if(Insertgstdetails > 0)
				{
				  strResGSTRetname+="<p align='center' class='insidecontent'>Successfully inserted details!<a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>";
				}
				else
				{
				  strResGSTRetname+="<p align='center' class='insidecontent'>Failed to insert details!<a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>";
				}

			}
			else
			{
				strResGSTRetname+="<p align='center' class='insidecontent'>GST Details are already added for this franchisee.<a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>";
			}
		} 
		catch(Exception e)
		{ 
			LogLevel.ERROR(0,e,"problem Caught Exception !! "+e);
			e.printStackTrace(); 
			session.setAttribute("sesErrorMsg",	"Error...Please Try Again" );
		} 

		return strResGSTRetname;
	}	
	
	/*****this method is added to Get GST Details**/

	public String getgstiddetails(HttpServletRequest req , HttpServletResponse res, String struserName,HttpSession session)
    {
		String strGSTRetname="";
		String retailername = (req.getParameter("retailername")!=null)?(req.getParameter("retailername")):"0";
		LogLevel.DEBUG(5,new Throwable(),"retailername :"+retailername);
		String gstid = (req.getParameter("gstid")!=null)?(req.getParameter("gstid")):"0";
		LogLevel.DEBUG(5,new Throwable(),"gstid :"+gstid);
		String state = (req.getParameter("state")!=null)?(req.getParameter("state")):"0";
		String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"0";
		String strRetname="";
		String statetable=state.replaceAll(" ","_");
		String[] retailername1 = retailername.split(",");
		String retid = retailername1[0];
		String retname = retailername1[1];
		
		
		try
		{  
			String strSqlQry5 = "select gstid from gstdetailstable where retailerid='"+retid+"' and retailername='"+retname+"'";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQry5 :"+strSqlQry5 );

			Hashtable ht = qm.getRow(strSqlQry5);
			LogLevel.DEBUG(5,new Throwable(),"ht:"+ht);
			
			if(ht.isEmpty())
			{
				
				strGSTRetname+="GST Details Not Found";
				
			}
			else
			{
					String gst_id=(String)ht.get("gstid");
					LogLevel.DEBUG(5,new Throwable(),"gst_id :"+gst_id);
				    strGSTRetname = gst_id;
			}
		} 
		catch(Exception e)
		{ 
			LogLevel.ERROR(0,e,"problem Caught Exception !! "+e);
			e.printStackTrace(); 
			session.setAttribute("sesErrorMsg",	"Error...Please Try Again" );
		} 

		return strGSTRetname;
	}	
	
	/*****this method is added to Get GST Details**/

	public String updategstiddetails(HttpServletRequest req , HttpServletResponse res, String struserName,HttpSession session)
    {
		String strGSTRes="";
		String retailername = (req.getParameter("retailername")!=null)?(req.getParameter("retailername")):"0";
		LogLevel.DEBUG(5,new Throwable(),"retailername :"+retailername);
		String gstid = (req.getParameter("gstid")!=null)?(req.getParameter("gstid")):"0";
		LogLevel.DEBUG(5,new Throwable(),"gstid :"+gstid);
		String state = (req.getParameter("state")!=null)?(req.getParameter("state")):"0";
		String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"0";
		String strRetname="";
		String statetable=state.replaceAll(" ","_");
		String[] retailername1 = retailername.split(",");
		String retid = retailername1[0];
		String retname = retailername1[1];
		
		
		try
		{  
			String strGSTSqlQry= "update gstdetailstable set gstid='"+gstid+"' WHERE  retailername='"+retname+"' and retailerid='"+retid+"'";
			LogLevel.DEBUG(5,new Throwable(),"strGSTSqlQry:"+strGSTSqlQry );
			 
			
			int i=qm.executeUpdate(strGSTSqlQry);
			if(i > 0)
			{
				LogLevel.DEBUG(1,new Throwable(),"");
				session.setAttribute("priority","1");
				session.setAttribute("sesErrorMsg", "<font color='#00dd00' class='vrb10'></font> ");
				strGSTRes = "Success";
				
			}
			else
			{
				LogLevel.DEBUG(1,new Throwable(),"");
				session.setAttribute("priority","1");
				session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'></font> ");
				strGSTRes = "Failed";
			}
		} 
		catch(Exception e)
		{ 
			LogLevel.ERROR(0,e,"problem Caught Exception !! "+e);
			e.printStackTrace(); 
			session.setAttribute("sesErrorMsg",	"Error...Please Try Again" );
		} 

		return strGSTRes;
	}	
	
	
}//End of class
