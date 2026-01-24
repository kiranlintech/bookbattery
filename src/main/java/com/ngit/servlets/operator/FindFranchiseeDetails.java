/***********************************************************************		
	NGIT Confidential. 
	@File Name   : FindFranchiseeDetails.java
	@Description : This Servlet is used to fetch the franchise details
	@Date        : 27th january 2014
******************************************************************/ 
package com.ngit.servlets.operator; 
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import java.io.IOException; 
import java.io.FileInputStream; 
import java.util.Properties; 
import java.util.Hashtable; 
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
/*
 * @author Sai Krishna Daddala.
 */
/* This class is to Admin Login. This class initializes the necessary connection pools and perform the transactions on the pools. */
public class FindFranchiseeDetails extends HttpServlet 
{
 	private ServletContext context; 
	QueryManager qm;
	static final long serialVersionUID=21;
	String baseURL;
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
		String struserName=(String)session.getAttribute("sesBatteryOperatorName"); 
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
		/*This methos is used to check vehicle models avliable or not*/
		if(strWhatToDo.equalsIgnoreCase("getstates"))
		{
			try
			{
				String strSqlQry ="select distinct(state) from search_whereever_keywords";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);
				Vector StateVector=qm.executeQuery(strSqlQry);
				LogLevel.DEBUG(5,new Throwable(),"StateVector:"+StateVector );
				if(StateVector.isEmpty())
				{
					LogLevel.DEBUG(1,new Throwable(),"Failed to get state ");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsgsst", "<font color='#00dd00' class='vrb10'>Failed to get state! </font> ");
					res.sendRedirect("../jsp/operator/franchiseedetails/franchiseedetails.jsp");
					return;
				}
				else
				{
					
					LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched states");
					session.setAttribute("sesstatevector", StateVector);
					session.setAttribute("sesErrorMsgsst","<font color='#CC0000' class='vrb10'>Succesfully Fetched states.</font> ");
					res.sendRedirect("../jsp/operator/franchiseedetails/franchiseedetails.jsp");
					return;
					
				}
			}
			catch(IOException ioe)
			{
					ioe.printStackTrace();
					session.setAttribute("sesErrorMsgsst",	"Generall Error...Please Try Again" );
					res.sendRedirect("../jsp/operator/franchiseedetails/franchiseedetails.jsp");
			}
			catch(Exception e)
			{
					e.printStackTrace();
					session.setAttribute("sesErrorMsgsst",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/operator/franchiseedetails/franchiseedetails.jsp");
			}
		}
		else if(strWhatToDo.equalsIgnoreCase("getcites"))
		{
			String state  = (req.getParameter("state")!=null)?req.getParameter("state"):"";
			LogLevel.DEBUG(5,new Throwable(),"state :"+state );		
			
			String result="";
			try
			{
				String city="";	

	 			ServletOutputStream out=res.getOutputStream();
			
				String strSqlQry ="select distinct(city) from search_whereever_keywords where state='"+state+"'";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

				Vector CityVector=qm.executeQuery(strSqlQry);
				LogLevel.DEBUG(5,new Throwable(),"CityVector:"+CityVector );

				if(CityVector.isEmpty())
				{ 
					out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&lt;-&nbsp;Select City&nbsp;-&gt;</option>");
					return;
				}
				else
				{
					for (int i=0; i<CityVector.size(); i++)
					{
						if(i==0)
						{
							out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&lt;-&nbsp;Select City&nbsp;-&gt;</option>");
						}
						Hashtable ht1=(Hashtable)CityVector.get(i);
						city =String.valueOf(ht1.get("city"));
						out.print("<option style='background:#FFF' value='"+city+"'>"+city+"</option>"); 
					}
				}
			}
			catch(Exception e)
			{
				LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
				e.printStackTrace();
			}
			return;
		} 
		else if(strWhatToDo.equalsIgnoreCase("getbrands"))
		  {
			try
			  {
			String bat_brand="";	

	 		ServletOutputStream out=res.getOutputStream();
			
			String strSqlQry ="select distinct(bat_brand) from battery_details";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector BrandVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"BrandVector:"+BrandVector );

			if(BrandVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&lt;-&nbsp;Select Brand&nbsp;-&gt;</option>");
				return;
			}
			else
			{
			for (int i=0; i<BrandVector.size(); i++)
				{
					if(i==0)
					{
					out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&lt;-&nbsp;Select Brand&nbsp;-&gt;</option>");
					}
					Hashtable ht1=(Hashtable)BrandVector.get(i);
					//bat_id =String.valueOf(ht1.get("bat_id"));
					bat_brand =String.valueOf(ht1.get("bat_brand"));
					out.print("<option style='background:#FFF' value='"+bat_brand+"'>"+bat_brand+"</option>"); 
				}
				//out.println(result);
				 }
				}
				catch(Exception e)
				{
					LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
					e.printStackTrace();
				}
				return;
			   } 
		else if(strWhatToDo.equalsIgnoreCase("getfranchiseedetails"))
		{
			String state  = (req.getParameter("state")!=null)?req.getParameter("state"):"";
			LogLevel.DEBUG(5,new Throwable(),"state :"+state );

			String city  = (req.getParameter("city")!=null)?req.getParameter("city"):"";
			LogLevel.DEBUG(5,new Throwable(),"city :"+city );
			
			String brand  = (req.getParameter("brand")!=null)?req.getParameter("brand"):"";
			LogLevel.DEBUG(5,new Throwable(),"brand :"+brand );
			String strRes="";

			state=state.trim().replaceAll(" ", "_"); 
			LogLevel.DEBUG(5, new Throwable(), "strstate :" + state);

				
			String retailekeyword ="";

			if(brand.equals("Amaron") || brand.equals("amaron"))
			{
				retailekeyword ="Amaron";
			}
			else if(brand.equals("Exide") || brand.equals("exide"))
			{
				retailekeyword ="Exide";
			}
			else 
			{
				retailekeyword = brand;
			}
			try
			{
				String retailer_id="";	
				String retailer_name="";
				String mobilenumber="";
				String mobilenumberother="";

				String landnumber="";
				String landnumberother="";
				String address="";
				String emailid="";
				String emailidother="";
				String zipcode="";

	 			ServletOutputStream out=res.getOutputStream();
			
				String strSqlQry ="select distinct(retailer_id),retailer_name,mobile_number,mobile_numberother,phone_number,phone_numberother,address1,email_id,email_id1,zipcode from "+state+"_retailers where retailer_name like '%"+retailekeyword+"%' and city='"+city+"'";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

				Vector retailerVector=qm.executeQuery(strSqlQry);
				LogLevel.DEBUG(5,new Throwable(),"retailerVector:"+retailerVector );
				if(retailerVector==null || retailerVector.size() == 0)
				{ 
					out.println("<p align='center' valign='top' class='insidecontent'><table valign='top' width='500' cellspacing='0' border='1' align='center' bgcolor='#FFFFFF' cellpadding='0'><tr><td><table valign='top' width='500' bgcolor='#FFFFFF' border='0'><tr><td class='insidecontent' align='center'>No Franchisee Avaliable</td></tr></table></td></tr></table><a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
					return;
				}
				else
				{

					
					strRes=strRes+"<table border='0' align='center' bgcolor='#FFFFFF' width='100%'><tr>";
				//strRes=strRes+"<td align='center'  valign='top'><input type='button' onClick=\"javascript:deleteappichatdetails();\" value='DELETE' class='button4'></td></tr></table>";
						
				strRes=strRes+"<table border='0' cellspacing='1' align='center' cellpadding='2' bgcolor='#FFFFFF' width='100%'><tr bgcolor='#2364b1'><td class='prodheading' width='22%'>Franchisee Name</td><td class='prodheading' width='16%'>Address</td><td class='prodheading' width='14%'>Mob Num</td><td class='prodheading' width='14%'>Land Num</td><td class='prodheading' width='34%'>Email Id</td></tr>";
				
					for (int i=0; i<retailerVector.size(); i++)
					{

							
							Hashtable ht1=(Hashtable)retailerVector.get(i);
							retailer_id =String.valueOf(ht1.get("retailer_id"));
							retailer_name =String.valueOf(ht1.get("retailer_name"));
							address =String.valueOf(ht1.get("address1"));
							emailid =String.valueOf(ht1.get("email_id"));
							emailidother =String.valueOf(ht1.get("email_id1"));
							zipcode =String.valueOf(ht1.get("zipcode"));

							mobilenumber =String.valueOf(ht1.get("mobile_number"));
							mobilenumber =String.valueOf(ht1.get("mobile_number"));
							mobilenumberother =String.valueOf(ht1.get("mobile_numberother"));
							landnumber =String.valueOf(ht1.get("phone_number"));
							landnumberother =String.valueOf(ht1.get("phone_numberother"));
							String mobnumber="";
							String landlinenumber="";
							String emailids="";
							String addresss = address.replaceAll(",", ", "); 

							if(mobilenumberother.equals(""))
							{
								mobnumber=mobilenumber;
							}
							else
							{
								mobnumber=mobilenumber+","+mobilenumberother;
							}
							String mobnumberoutput = mobnumber.replaceAll(",", ", "); 
							LogLevel.DEBUG(5, new Throwable(), "mobnumberoutput :" + mobnumberoutput);
							if(landnumberother.equals(""))
							{
								landlinenumber=landnumber;
							}
							else
							{
								landlinenumber=landnumber+","+landnumberother;
							}
							String landlinenumberoutput = landlinenumber.replaceAll(",", ", "); 
							LogLevel.DEBUG(5, new Throwable(), "landlinenumberoutput :" + landlinenumberoutput);
							if(emailidother.equals(""))
							{
								emailids=emailid;
							}
							else
							{
								emailids=emailid+","+emailidother;
							}
							String emailidoutput = emailids.replaceAll(",", ", "); 
							LogLevel.DEBUG(5, new Throwable(), "emailidoutput :" + emailidoutput);

							strRes=strRes+"<table bgcolor='#FFFFFF' width='100%' cellspacing='1' border='0' align='center'  cellpadding='2'><input type='checkbox' name='chkSi' value='' style='display:none'/>";
							strRes=strRes+"<tr>";
							strRes=strRes+"<td width='22%' class='table1' align='left'  >"+retailer_name+"</td>";
							strRes=strRes+"<td width='16%'  class='table1' align='left'  >"+addresss+"-"+zipcode+"</td>";
							strRes=strRes+"<td width='14%'  class='table1' align='left'  >"+mobnumberoutput+"</td>";
							strRes=strRes+"<td width='14%'  class='table1' align='left'  >"+landlinenumberoutput+"</td>";

							strRes=strRes+"<td width='34%'  class='table1' align='left'  >"+emailidoutput+"</td></tr>";

							strRes=strRes+"</table>";
							strRes=strRes+"</table>";
							
							
					}
					out.println(strRes);
					return;
				}
			}
			catch(Exception e)
			{
				LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
				e.printStackTrace();
			}
			return;
		}		 
	 }
 }


