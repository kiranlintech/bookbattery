/***********************************************************************		
	NGIT Confidential. 
	@File Name   : AdminBatteryLoginServlet.java
	@Description : This Servlet is used to allow the operator to Login
	@Date        : 30th August 2013
******************************************************************/ 
package com.ngit.servlets.admin.batterystore; 
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
public class RetailerLocationMap extends HttpServlet 
{
 	private ServletContext context; 
	QueryManager qm;
	String baseURL;
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
		String struserName=(String)session.getAttribute("sesBatteryAdminName"); 
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
			String keyword  = (req.getParameter("keyword")!=null)?req.getParameter("keyword"):"";
			LogLevel.DEBUG(5,new Throwable(),"keyword :"+keyword );
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
					res.sendRedirect("../jsp/admin/batterystore/retailerlocmap/retailerlocmap.jsp");
					return;
				}
				else
				{
					if(keyword.equals("pin"))
					{
					LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched states");
					session.setAttribute("sesstatevector", StateVector);
					session.setAttribute("sesErrorMsgsst","<font color='#CC0000' class='vrb10'>Succesfully Fetched states.</font> ");
					res.sendRedirect("../jsp/admin/batterystore/retailerlocmap/pincode.jsp?keyword="+keyword);
					return;
					}
					else if(keyword.equals("area"))
					{
					LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched states");
					session.setAttribute("sesstatevector", StateVector);
					session.setAttribute("sesErrorMsgsst","<font color='#CC0000' class='vrb10'>Succesfully Fetched states.</font> ");
					res.sendRedirect("../jsp/admin/batterystore/locationarea/addpincode.jsp?keyword="+keyword);
					return;
					}
					else
					{
					LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched states");
					session.setAttribute("sesstatevector", StateVector);
					session.setAttribute("sesErrorMsgsst","<font color='#CC0000' class='vrb10'>Succesfully Fetched states.</font> ");
					res.sendRedirect("../jsp/admin/batterystore/retailerlocmap/retailerlocmap.jsp");
					return;
					}
				}
				}
			catch(IOException ioe)
				{
					//LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
					ioe.printStackTrace();
					session.setAttribute("sesErrorMsgsst",	"Generall Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/retailerlocmap/retailerlocmap.jsp");
				}
			catch(Exception e)
				{
					//LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsgsst",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/retailerlocmap/retailerlocmap.jsp");
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
					//bat_id =String.valueOf(ht1.get("bat_id"));
					city =String.valueOf(ht1.get("city"));
					out.print("<option style='background:#FFF' value='"+city+"'>"+city+"</option>"); 
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
		else if(strWhatToDo.equalsIgnoreCase("getretailers"))
		  {
			String state  = (req.getParameter("state")!=null)?req.getParameter("state"):"";
			LogLevel.DEBUG(5,new Throwable(),"state :"+state );

			String brand  = (req.getParameter("brand")!=null)?req.getParameter("brand"):"";
			LogLevel.DEBUG(5,new Throwable(),"brand :"+brand );

			String city  = (req.getParameter("city")!=null)?req.getParameter("city"):"";
			LogLevel.DEBUG(5,new Throwable(),"city :"+city );

			state=state.trim().replaceAll(" ", "_"); 
			LogLevel.DEBUG(5, new Throwable(), "strstate :" + state);
			String retailekeyword ="";
			if(brand.equals("Amaron") || brand.equals("amaron"))
			{
				//retailekeyword ="Amaron-Pitstop";
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

	 		ServletOutputStream out=res.getOutputStream();
			
			String strSqlQry ="select distinct(retailer_id),retailer_name from "+state+"_retailers where retailer_name like '%"+retailekeyword+"%' and city='"+city+"' order by retailer_name asc";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector retailerVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"retailerVector:"+retailerVector );

			if(retailerVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&lt;-&nbsp;Select Retailer&nbsp;-&gt;</option>");
				return;
			}
			else
			{
			for (int i=0; i<retailerVector.size(); i++)
				{
					if(i==0)
					{
					out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&lt;-&nbsp;Select Retailer&nbsp;-&gt;</option>");
					}
					Hashtable ht1=(Hashtable)retailerVector.get(i);
					//bat_id =String.valueOf(ht1.get("bat_id"));
					retailer_id =String.valueOf(ht1.get("retailer_id"));
					retailer_name =String.valueOf(ht1.get("retailer_name"));
					out.print("<option style='background:#FFF' value='"+retailer_id+","+retailer_name+"'>"+retailer_name+"</option>"); 
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
		else if(strWhatToDo.equalsIgnoreCase("getarea"))
		  {
			String state  = (req.getParameter("state")!=null)?req.getParameter("state"):"";
			LogLevel.DEBUG(5,new Throwable(),"state :"+state );

			String city  = (req.getParameter("city")!=null)?req.getParameter("city"):"";
			LogLevel.DEBUG(5,new Throwable(),"city :"+city );

			String[] strcity = city.split(",");
			String id = strcity[0];
			String city1 = strcity[1];

			state=state.trim().replaceAll(" ", "_"); 
			LogLevel.DEBUG(5, new Throwable(), "strstate :" + state);
			
			String result="";
			try
			  {
				ServletOutputStream out = res.getOutputStream();

				String strSqlQry1 = "select distinct(area) from "+state+"_retailers where city='"+city1+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry1:"+strSqlQry1 );
			
				Vector areaVector=qm.executeQuery(strSqlQry1);
				LogLevel.DEBUG(5,new Throwable(),"areaVector :"+areaVector);
				
				if( areaVector==null || areaVector.size() == 0)
					{
						LogLevel.DEBUG(1, new Throwable(),"Failed to fetch model ");
						session.setAttribute("priority", "1");
						session.setAttribute("sesErrorMsg",	"<font color='#CC0000' class='vrb10'>Failed to fetch area</font> ");
						out.println("Areas are not available under the selected city");
					}
					else
					{
						for (int i = 0; i < areaVector.size(); i++)
						{
							Hashtable ht = (Hashtable)areaVector.get(i);
							String area = (String)ht.get("area");
							int ids=0;
							if (result.equals(""))
							result = ids + ":" + area;
							else
							result = result + "|" + ids + ":" + area;
						}
						out.println(result);
					 }
					}
					catch(Exception e)
					{
						LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
						e.printStackTrace();
					}
					return;
				   } 
		else if(strWhatToDo.equalsIgnoreCase("insertretlocmap"))
			{
			String strstate = (req.getParameter("state") != null)? (req.getParameter("state")) : "";
			String city = (req.getParameter("city") != null)? (req.getParameter("city")) : "";
			String area = (req.getParameter("area") != null)? (req.getParameter("area")) : "";
			String pincode = (req.getParameter("pincode") != null)? (req.getParameter("pincode")) : "";
			String keyword = (req.getParameter("keyword") != null)? (req.getParameter("keyword")) : "";
			String strretailer = (req.getParameter("retailer") != null)? (req.getParameter("retailer")) : "";
			String weekenddealer = (req.getParameter("weekenddealer") != null)? (req.getParameter("weekenddealer")) : "";
			String brand = (req.getParameter("brand") != null)? (req.getParameter("brand")) : "";

			String[] strsretailer = strretailer.split(",");
			String retid = strsretailer[0];
			String retname = strsretailer[1];
			int result = 0;
			String StrSqlQery="";
			String strretailer_name="";
			String strSqlQry="";
			try
			{
				if(keyword == "pin" || keyword.equals("pin"))
				{
			StrSqlQery = "select retailer_name from retailer_location_mapping where retailer_id='"+retid+"' and retailer_name='"+retname+"' and pincode='"+pincode+"' and bat_brand='"+brand+"'";
			LogLevel.DEBUG(5,new Throwable(),"StrSqlQery :"+StrSqlQery);

			Hashtable htapp1 = qm.getRow(StrSqlQery);
			strretailer_name=(String)htapp1.get("retailer_name");
			LogLevel.DEBUG(5,new Throwable(),"strretailer_name :"+strretailer_name);

			if(htapp1.isEmpty())
			{ 
			strSqlQry = "insert into retailer_location_mapping(ret_loc_id,state,city,area,retailer_id,retailer_name,pincode,bat_brand,weekend_dealer_flag,creation_date,created_by) values(NULL,'','','','"+retid+"','"+retname+"','"+pincode+"','"+brand+"','"+weekenddealer+"',now(),'ngit')";
			//LogLevel.DEBUG(5,new Throwable(),"strSqlQry :"+strSqlQry );

			result=qm.executeUpdate(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"result :"+result );
			}
			else
			{
			}
				}
				else
				{
			StrSqlQery = "select retailer_name from retailer_location_mapping where state='"+strstate+"' and city='"+city+"' and area='"+area+"' and retailer_id='"+retid+"' and retailer_name='"+retname+"' and bat_brand='"+brand+"'";
			LogLevel.DEBUG(5,new Throwable(),"StrSqlQery :"+StrSqlQery);

			Hashtable htapp1 = qm.getRow(StrSqlQery);
			strretailer_name=(String)htapp1.get("retailer_name");
			LogLevel.DEBUG(5,new Throwable(),"strretailer_name :"+strretailer_name);

			if(htapp1.isEmpty())
			{ 
			strSqlQry = "insert into retailer_location_mapping(ret_loc_id,state,city,area,retailer_id,retailer_name,pincode,bat_brand,weekend_dealer_flag,creation_date,created_by) values(NULL,'"+strstate+"','"+city+"','"+area+"','"+retid+"','"+retname+"','','"+brand+"','"+weekenddealer+"',now(),'ngit')";
			//LogLevel.DEBUG(5,new Throwable(),"strSqlQry :"+strSqlQry );

			result=qm.executeUpdate(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"result :"+result );
			}
			else
			{
			}
				}
			if(result < 0)
			{
				LogLevel.DEBUG(1,new Throwable(),"Failed to insert retailer location mapping details!");
				session.setAttribute("priority","1");
				session.setAttribute("sesretlocErrorMsg", "<font color='#ff3333' class='vrb10'>Failed to insert retailer location mapping details! </font> ");
				res.sendRedirect("../jsp/admin/batterystore/retailerlocmap/retailerlocmap.jsp");
				return;
			}
			else
			{
				if(keyword.equals("pin"))
				{
				LogLevel.DEBUG(1,new Throwable(),"Successfully inserted retailer pincode mapping details! ");
				session.setAttribute("priority","1");
				session.setAttribute("sesretpioncErrorMsg", "<font color='#000000' class='vrb10'>Successfully inserted retailer pincode mapping details! </font> ");
				res.sendRedirect("../jsp/admin/batterystore/retailerlocmap/pincode.jsp?keyword="+keyword);
				return;
				}
				else
				{
				LogLevel.DEBUG(1,new Throwable(),"Successfully inserted retailer location mapping details! ");
				session.setAttribute("priority","1");
				session.setAttribute("sesretlocErrorMsg", "<font color='#000000' class='vrb10'>Successfully inserted retailer location mapping details! </font> ");
				res.sendRedirect("../jsp/admin/batterystore/retailerlocmap/retailerlocmap.jsp");
				return;
				}
				
			}
			}
			catch(IOException ioe)
			{
				//LogLevel.ERROR(0,ioe,"problem Caught IOException if(editcomplaint) !! "+ioe);
				ioe.printStackTrace();
				session.setAttribute("sesretlocErrorMsg",	"Generall Error...Please Try Again" );
				res.sendRedirect("../jsp/admin/batterystore/retailerlocmap/retailerlocmap.jsp");
			}
			catch(Exception e)
			{
				//LogLevel.ERROR(0,e,"Problem Caught Exception if(editcomplaint)!! "+e);
				e.printStackTrace();
				session.setAttribute("sesretlocErrorMsg",	"General Error...Please Try Again" );
				res.sendRedirect("../jsp/admin/batterystore/retailerlocmap/retailerlocmap.jsp");
			}
		}
		/*This methos is used to check vehicle models avliable or not*/
		else if(strWhatToDo.equalsIgnoreCase("getstatetodelete"))
		  {
			try
			  {
				String strSqlQry ="select distinct(state) from retailer_location_mapping where state!='' order by state asc";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

				Vector StateVector=qm.executeQuery(strSqlQry);
				LogLevel.DEBUG(5,new Throwable(),"StateVector:"+StateVector );
				if(StateVector.isEmpty())
				{
					LogLevel.DEBUG(1,new Throwable(),"Failed to get state ");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsgsst", "<font color='#00dd00' class='vrb10'>Failed to get state! </font> ");
					res.sendRedirect("../jsp/admin/batterystore/retailerlocmap/deleteretailerlocmap.jsp");
					return;
				}
				else
				{
					LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched states");
					session.setAttribute("sesstatevector", StateVector);
					session.setAttribute("sesErrorMsgsst","<font color='#CC0000' class='vrb10'>Succesfully Fetched states.</font> ");
					res.sendRedirect("../jsp/admin/batterystore/retailerlocmap/deleteretailerlocmap.jsp");
					
				}
				}
			catch(IOException ioe)
				{
					//LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
					ioe.printStackTrace();
					session.setAttribute("sesErrorMsgsst",	"Generall Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/retailerlocmap/deleteretailerlocmap.jsp");
				}
			catch(Exception e)
				{
					//LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsgsst",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/retailerlocmap/deleteretailerlocmap.jsp");
				}
		     }
		 else if(strWhatToDo.equalsIgnoreCase("getcity"))
		  {
			String state = (req.getParameter("state") != null)? (req.getParameter("state")) : "";
			String result="";
			try
			  {
			String city="";	

	 		ServletOutputStream out=res.getOutputStream();
			
			String strSqlQry ="select distinct(city) from retailer_location_mapping  where state='"+state+"' order by city asc";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector CityVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"CityVector:"+CityVector );

			if(CityVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>Select&nbsp;City</option>");
				return;
			}
			else
			{
			for (int i=0; i<CityVector.size(); i++)
				{
					if(i==0)
					{
					out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>Select&nbsp;City</option>");
					}
					Hashtable ht1=(Hashtable)CityVector.get(i);
					//bat_id =String.valueOf(ht1.get("bat_id"));
					city =String.valueOf(ht1.get("city"));
					out.print("<option style='background:#FFF' value='"+city+"'>"+city+"</option>"); 
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
		else if(strWhatToDo.equalsIgnoreCase("getareatoselete"))
		  {
			String city = (req.getParameter("city") != null)? (req.getParameter("city")) : ""; 
			String state = (req.getParameter("state") != null)? (req.getParameter("state")) : "";
			String result="";
			try
			  {
			String area="";	

	 		ServletOutputStream out=res.getOutputStream();
			
			String strSqlQry ="select distinct(area) from retailer_location_mapping  where city='"+city+"' and state='"+state+"' order by area asc";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector AreaVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"AreaVector:"+AreaVector );

			if(AreaVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>Select&nbsp;Area</option>");
				return;
			}
			else
			{
			for (int i=0; i<AreaVector.size(); i++)
				{
					if(i==0)
					{
					out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>Select&nbsp;Area</option>");
					}
					Hashtable ht1=(Hashtable)AreaVector.get(i);
					//bat_id =String.valueOf(ht1.get("bat_id"));
					area =String.valueOf(ht1.get("area"));
					out.print("<option style='background:#FFF' value='"+area+"'>"+area+"</option>"); 
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
		else if(strWhatToDo.equalsIgnoreCase("getretailerstodelete"))
		  {
			String state = (req.getParameter("state") != null)? (req.getParameter("state")) : "";
			String city = (req.getParameter("city") != null)? (req.getParameter("city")) : ""; 
			String area = (req.getParameter("area") != null)? (req.getParameter("area")) : "";

			String result="";
			try
			  {
			String retailer_name="";	

	 		ServletOutputStream out=res.getOutputStream();
			
			String strSqlQry ="select distinct(retailer_name) from retailer_location_mapping  where city='"+city+"' and state='"+state+"' and area='"+area+"' order by retailer_name asc";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector RetailerVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"RetailerVector:"+RetailerVector );

			if(RetailerVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>Select&nbsp;Retailer</option>");
				return;
			}
			else
			{
			for (int i=0; i<RetailerVector.size(); i++)
				{
					if(i==0)
					{
					out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>Select&nbsp;Retailer</option>");
					}
					Hashtable ht1=(Hashtable)RetailerVector.get(i);
					//bat_id =String.valueOf(ht1.get("bat_id"));
					retailer_name =String.valueOf(ht1.get("retailer_name"));
					out.print("<option style='background:#FFF' value='"+retailer_name+"'>"+retailer_name+"</option>"); 
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
		else if(strWhatToDo.equalsIgnoreCase("getretailerstodeletepincode"))
		  {
			String pincode = (req.getParameter("pincode") != null)? (req.getParameter("pincode")) : "";

			String result="";
			try
			  {
			String retailer_name="";	

	 		ServletOutputStream out=res.getOutputStream();
			
			String strSqlQry ="select distinct(retailer_name) from retailer_location_mapping  where pincode='"+pincode+"' order by retailer_name asc";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector RetailerVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"RetailerVector:"+RetailerVector );

			if(RetailerVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>Select&nbsp;Retailer</option>");
				return;
			}
			else
			{
			for (int i=0; i<RetailerVector.size(); i++)
				{
					if(i==0)
					{
					out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>Select&nbsp;Retailer</option>");
					}
					Hashtable ht1=(Hashtable)RetailerVector.get(i);
					//bat_id =String.valueOf(ht1.get("bat_id"));
					retailer_name =String.valueOf(ht1.get("retailer_name"));
					out.print("<option style='background:#FFF' value='"+retailer_name+"'>"+retailer_name+"</option>"); 
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
		else if(strWhatToDo.equalsIgnoreCase("gebatretlocationtodelete"))
		  {
			String state= (req.getParameter("state")!=null)?(req.getParameter("state")):"";
			LogLevel.DEBUG(5,new Throwable(),"state :"+state );

			String city= (req.getParameter("city")!=null)?(req.getParameter("city")):"";
			LogLevel.DEBUG(5,new Throwable(),"city :"+city );

			String area= (req.getParameter("area")!=null)?(req.getParameter("area")):"";
			LogLevel.DEBUG(5,new Throwable(),"area :"+area );

			String retailers= (req.getParameter("retailers")!=null)?(req.getParameter("retailers")):"";
			LogLevel.DEBUG(5,new Throwable(),"retailers :"+retailers );

			String pincode= (req.getParameter("pincode")!=null)?(req.getParameter("pincode")):"0";
			LogLevel.DEBUG(5,new Throwable(),"pincode :"+pincode );

			ServletOutputStream out=res.getOutputStream();
			String strRes="";
			Vector retdeleteVector = new Vector();
			try
			 {  
			if(pincode.equals("0") || (pincode == "0") || (pincode.equals(0)))
			{
			String strSqlQry = "select ret_loc_id,retailer_id,state,city,area,retailer_name from retailer_location_mapping where state='"+state+"' and city='"+city+"' and area='"+area+"' and retailer_name='"+retailers+"'";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
			
			 retdeleteVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"retdeleteVector :"+retdeleteVector);	
			}
			else
			{
			String strSqlQry = "select ret_loc_id,retailer_id,state,city,area,retailer_name from retailer_location_mapping where pincode='"+pincode+"' and retailer_name='"+retailers+"'";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
			
			retdeleteVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"retdeleteVector :"+retdeleteVector);
			}
			if(retdeleteVector==null || retdeleteVector.size() == 0)
			{
				out.println("<p align='center' valign='top' class='insidecontent'><table valign='top' width='500' cellspacing='0' border='1' align='center' bgcolor='#FFFFFF' cellpadding='0'><tr><td><table valign='top' width='500' bgcolor='#FFFFFF' border='0'><tr><td class='insidecontent' align='center'>No Retailer location mapping details Avaliable!</td></tr></table></td></tr></table><a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
			}
			else
			{
				strRes=strRes+"<table border='0' align='center' bgcolor='#FFFFFF' width='700'><tr>";
				//strRes=strRes+"<td align='center'  valign='top'><input type='button' onClick=\"javascript:deleteappichatdetails();\" value='DELETE' class='button4'></td></tr></table>";
						
				strRes=strRes+"<table border='0' cellspacing='1' align='center' cellpadding='2' bgcolor='#FFFFFF' width='100%'><tr bgcolor='#2364b1'><td class='prodheading' width='10%'>Retailer Id</td><td class='prodheading' width='40%'>Retailer Name</td><td class='prodheading' width='20%'>DELETE</td></tr>";
			  	for(int j=0; j<retdeleteVector.size();j++)
				{
					Hashtable ht1=(Hashtable)retdeleteVector.get(j);
					String retid=String.valueOf(ht1.get("retailer_id"));
					String retlocid=String.valueOf(ht1.get("ret_loc_id"));
					//String strstate=(String)ht1.get("state");
					//String strcity=(String)ht1.get("city");
					//String strarea=(String)ht1.get("area");
					String strretname=(String)ht1.get("retailer_name");
					
					LogLevel.DEBUG(5,new Throwable(),"retlocid :"+retlocid );

					int Jcnt=j+1;
					
					strRes=strRes+"<table bgcolor='#FFFFFF' width='100%' cellspacing='1' border='0' align='center'  cellpadding='2'><input type='checkbox' name='chkSi' value='' style='display:none'/>";
					//strRes=strRes+"<tr><td width='6%' class='table1' align='left' >"+Jcnt+"<input type='checkbox' name='chkSi' value='"+strmapid+"'/></td>";
					//strRes=strRes+"<td width='20%'   class='table1 align='left'  >"+strstate+"</td>";
					//strRes=strRes+"<td  width='20%'  class='table1 align='left'  >"+strcity+"</td>";
					strRes=strRes+"<td  width='10%'  class='table1 align='left'>"+retid+"</td>";
					strRes=strRes+"<td  width='40%'  class='table1 align='left'  >"+strretname+"</td>";
					strRes=strRes+"<td  width='20%'  class='table1 align='left'  ><a href=\"javascript:deleteretlocdetails('"+retlocid+"');\">Delete</td></tr>";
					strRes=strRes+"</table>";
					strRes=strRes+"</table>";
				} 
				out.println(strRes);
					return;				
			}			
				}
			   catch(IOException ioe)
			    {
					LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
					ioe.printStackTrace();
					session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/retailerlocmap/deleteretailerlocmap.jsp");
			    }
			   catch(Exception e)
			    {
					LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/retailerlocmap/deleteretailerlocmap.jsp");
			    }
		  }
		  /*This methos is used to insert vehicle details into vehicle_details*/
		else if(strWhatToDo.equalsIgnoreCase("deleteretlocationmap"))
		{
		String chkSi= (req.getParameter("chkSi")!=null)?(req.getParameter("chkSi")):"";
		LogLevel.DEBUG(5, new Throwable(), "chkSi :" + chkSi);

		String strRes="";
		try
		{	
			ServletOutputStream out=res.getOutputStream();

			String strSqlQry = "delete from retailer_location_mapping where ret_loc_id='"+chkSi+"'";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
			
			int i=qm.executeUpdate(strSqlQry);

			if(i <0)
				{
					LogLevel.DEBUG(1,new Throwable(),"No Name exists u can continue ");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#00dd00' class='vrb10'></font> ");
					out.println("Failed to delete Retailer Location Mapping details ");
				}
				else
				{
					LogLevel.DEBUG(1,new Throwable(),"");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'></font> ");
					//System.out.println(email);
					out.println("Successfully deleted Retailer Location Mapping details");
				}

			
		}
		catch (Exception e)
		{
			LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
			e.printStackTrace();
		}
		return;
		}
		/*This methos is used to check vehicle models avliable or not*/
		else if(strWhatToDo.equalsIgnoreCase("pincode"))
		  {
			try
			  {
				String strSqlQry ="select distinct(pincode) from retailer_location_mapping where pincode!='' order by pincode asc";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

				Vector PincodeVector=qm.executeQuery(strSqlQry);
				LogLevel.DEBUG(5,new Throwable(),"PincodeVector:"+PincodeVector );
				if(PincodeVector.isEmpty())
				{
					LogLevel.DEBUG(1,new Throwable(),"Failed to get state ");
					session.setAttribute("priority","1");
					session.setAttribute("sesErrorMsgsst", "<font color='#00dd00' class='vrb10'>Failed to get pincode! </font> ");
					res.sendRedirect("../jsp/admin/batterystore/retailerlocmap/deletepincodemap.jsp");
					return;
				}
				else
				{
					LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched states");
					session.setAttribute("sespincodevector", PincodeVector);
					session.setAttribute("sesErrorMsgsst","<font color='#CC0000' class='vrb10'>Succesfully Fetched pincode.</font> ");
					res.sendRedirect("../jsp/admin/batterystore/retailerlocmap/deletepincodemap.jsp");
					
				}
				}
			catch(IOException ioe)
				{
					//LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
					ioe.printStackTrace();
					session.setAttribute("sesErrorMsgsst",	"Generall Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/retailerlocmap/deletepincodemap.jsp");
				}
			catch(Exception e)
				{
					//LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsgsst",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/retailerlocmap/deletepincodemap.jsp");
				}
		     }
	 else if(strWhatToDo.equalsIgnoreCase("insertarea"))
			{
			String state = (req.getParameter("state") != null)? (req.getParameter("state")) : ""; 
			String city = (req.getParameter("city") != null)? (req.getParameter("city")) : ""; 
			String areacode = (req.getParameter("areacode") != null)? (req.getParameter("areacode")) : ""; 
			String keyword = (req.getParameter("keyword") != null)? (req.getParameter("keyword")) : ""; 

			int result = 0;
			try
			{
			String StrSqlQery1 = "select state from battery_pincode where pincode='"+areacode+"' and state='"+state+"' and city='"+city+"'";
			LogLevel.DEBUG(5,new Throwable(),"StrSqlQery1 :"+StrSqlQery1);

			Hashtable htapp = qm.getRow(StrSqlQery1);
			String state1=(String)htapp.get("state");
			
			if(htapp.isEmpty())
			{ 
			String strSqlQry = "insert into battery_pincode(pincode,city,state,pincodeid,creation_date) values('"+areacode+"','"+city+"','"+state+"',NULL,now())";
			//LogLevel.DEBUG(5,new Throwable(),"strSqlQry :"+strSqlQry );

			result=qm.executeUpdate(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"result :"+result );

				if(result < 0)
				{
					LogLevel.DEBUG(1,new Throwable(),"Failed to insert pincode details!");
					session.setAttribute("priority","1");
					session.setAttribute("sespincodeErrorMsg", "<font color='#ff3333' class='vrb10'>Failed to insert pincode details! </font> ");
					res.sendRedirect("../jsp/admin/batterystore/locationarea/addpincode.jsp?keyword="+keyword);
					return;
				}
				else
				{
					LogLevel.DEBUG(1,new Throwable(),"Successfully inserted battery price details! ");
					session.setAttribute("priority","1");
					session.setAttribute("sespincodeErrorMsg", "<font color='#000000' class='vrb10'>Successfully inserted pincode details! </font> ");
					res.sendRedirect("../jsp/admin/batterystore/locationarea/addpincode.jsp?keyword="+keyword);
					return;
					
				}
			}
			else
			{
				LogLevel.DEBUG(1,new Throwable(),"Successfully inserted battery price details! ");
				session.setAttribute("priority","1");
				session.setAttribute("sespincodeErrorMsg", "<font color='#000000' class='vrb10'>Already added pincode. </font> ");
				res.sendRedirect("../jsp/admin/batterystore/locationarea/addpincode.jsp?keyword="+keyword);
				return;
			}
			}
			catch(IOException ioe)
			{
				//LogLevel.ERROR(0,ioe,"problem Caught IOException if(editcomplaint) !! "+ioe);
				ioe.printStackTrace();
				session.setAttribute("sespincodeErrorMsg",	"Generall Error...Please Try Again" );
				res.sendRedirect("../jsp/admin/batterystore/locationarea/addpincode.jsp");
			}
			catch(Exception e)
			{
				//LogLevel.ERROR(0,e,"Problem Caught Exception if(editcomplaint)!! "+e);
				e.printStackTrace();
				session.setAttribute("sespincodeErrorMsg",	"General Error...Please Try Again" );
				res.sendRedirect("../jsp/admin/batterystore/locationarea/addpincode.jsp");
			}
		}
	 }
 }


