/***********************************************************************		
	NGIT Confidential. 
	@File Name   : AdminBatteryLoginServlet.java
	@Description : This Servlet is used to allow the operator to Login
	@Date        : 30th August 2013
******************************************************************/ 
package com.ngit.servlets.admin.batterystore; 
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.FilenameUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.Iterator;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.io.File;
import java.util.Date;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Vector;
import java.io.IOException;
import com.oreilly.servlet.MultipartRequest;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/*
 * @author Sai Krishna Daddala.
 */
/* This class is to Admin Login. This class initializes the necessary connection pools and perform the transactions on the pools. */
public class BatteryPriceDetails extends HttpServlet 
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
		if(strWhatToDo.equalsIgnoreCase("insertbatteryprices"))
			{
			String strbatbrand = (req.getParameter("batbrand") != null)? (req.getParameter("batbrand")) : "";
			String strbatmodel1 = (req.getParameter("batmodel") != null)? (req.getParameter("batmodel")) : "";
			String mySplitResult1[] = strbatmodel1.split(",");
			String strbatcap=mySplitResult1[0];
			String strbatmodel=mySplitResult1[1];
			String strstate = (req.getParameter("stateselected") != null)? (req.getParameter("stateselected")) : ""; 
			String strcity = (req.getParameter("cityselected") != null)? (req.getParameter("cityselected")) : ""; 
			String strbatacprice = (req.getParameter("batacprice") != null)? (req.getParameter("batacprice")) : "";
			String strbatdisprice = (req.getParameter("batdisprice") != null)? (req.getParameter("batdisprice")) : "";
			String strbatwithprice = (req.getParameter("batwithprice") != null)? (req.getParameter("batwithprice")) : "";
			String strerpprice = (req.getParameter("erpprice") != null)? (req.getParameter("erpprice")) : "";
			
			String str_city_state="";
			String strstatefinal="";
			String strcityfinal="";

			int result = 0;
			try
			{
				
			String strstate_city_Array []=strcity.split(",");
			LogLevel.DEBUG(5,new Throwable(),"strstate_city_Array :"+strstate_city_Array);
			LogLevel.DEBUG(5,new Throwable(),"strstate_city_Array.length :"+strstate_city_Array.length);
			
			for(int i=0;i<strstate_city_Array.length;i++)			
			{

			String city=strstate_city_Array[i];
			
			LogLevel.DEBUG(5, new Throwable(), "city :" + city);
		
		
			String strstate_citySqlQry ="select distinct(city),state from search_whereever_keywords where city ="+city+" order by city asc";
			LogLevel.DEBUG(5, new Throwable(), "strstate_citySqlQry :" + strstate_citySqlQry);
			
			Vector state_district_Vector=qm.executeQuery(strstate_citySqlQry);
			
			LogLevel.DEBUG(5, new Throwable(), "state_district_Vector :" + state_district_Vector);
			
			Hashtable ht=(Hashtable)state_district_Vector.get(0);
			
			strstatefinal=String.valueOf(ht.get("state"));			
			LogLevel.DEBUG(5,new Throwable(),"strstatefinal :"+strstatefinal);
		
			strcityfinal =(String)ht.get("city");			
			LogLevel.DEBUG(5,new Throwable(),"strcityfinal :"+strcityfinal);	
				
				
			String StrSqlQery1 = "select bat_model from batteryprice where bat_brand='"+strbatbrand+"' and bat_model='"+strbatmodel+"' and state='"+strstatefinal+"' and city='"+strcityfinal+"'";
			LogLevel.DEBUG(5,new Throwable(),"StrSqlQery1 :"+StrSqlQery1);

			Hashtable htapp1 = qm.getRow(StrSqlQery1);
			String strbat_model1=(String)htapp1.get("bat_model");
			LogLevel.DEBUG(5,new Throwable(),"dhritima :"+htapp1.size());

			if(htapp1.isEmpty())
			{ 
			String strSqlQry = "insert into batteryprice(batprice_id,bat_brand,bat_model,state,city,bat_act_price,bat_witbat_price,bat_ret_price,erp_pre_tax,bat_capacity,creation_date,created_by) values(NULL,'"+strbatbrand+"','"+strbatmodel+"','"+strstatefinal+"','"+strcityfinal+"','"+strbatacprice+"','"+strbatdisprice+"','"+strbatwithprice+"','"+strerpprice+"','"+strbatcap+"',now(),'ngit')";
			//LogLevel.DEBUG(5,new Throwable(),"strSqlQry :"+strSqlQry );

			result=qm.executeUpdate(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"result :"+result );

				/*if(result < 0)
				{
					LogLevel.DEBUG(1,new Throwable(),"Failed to insert battery price details!");
					session.setAttribute("priority","1");
					session.setAttribute("sesbatpriceErrorMsg", "<font color='#ff3333' class='vrb10'>Failed to insert battery price details for this record! </font> ");
					res.sendRedirect("../jsp/admin/batterystore/battery/batteryprice.jsp");
					return;
				}
				else
				{
					LogLevel.DEBUG(1,new Throwable(),"Successfully inserted battery price details! ");
					session.setAttribute("priority","1");
					session.setAttribute("sesbatpriceErrorMsg", "<font color='#000000' class='vrb10'>Successfully inserted battery price details! </font> ");
					res.sendRedirect("../jsp/admin/batterystore/battery/batteryprice.jsp");
					return;
					
				}*/
			}
			else
			{
				/*LogLevel.DEBUG(1,new Throwable(),"Successfully inserted battery price details! ");
				session.setAttribute("priority","1");
				session.setAttribute("sesbatpriceErrorMsg", "<font color='#000000' class='vrb10'>Already have prices for this model! Please update ..... </font> ");
				res.sendRedirect("../jsp/admin/batterystore/battery/batteryprice.jsp");
				return;*/
			}
			}
				LogLevel.DEBUG(1,new Throwable(),"Successfully inserted battery price details! ");
				session.setAttribute("priority","1");
				session.setAttribute("sesbatpriceErrorMsg", "<font color='#000000' class='vrb10'>Successfully inserted battery price details! </font> ");
				res.sendRedirect("../jsp/admin/batterystore/battery/batteryprice.jsp");
				return;
			}
			catch(IOException ioe)
			{
				//LogLevel.ERROR(0,ioe,"problem Caught IOException if(editcomplaint) !! "+ioe);
				ioe.printStackTrace();
				session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
				res.sendRedirect("../jsp/admin/batterystore/battery/batteryprice.jsp");
			}
			catch(Exception e)
			{
				//LogLevel.ERROR(0,e,"Problem Caught Exception if(editcomplaint)!! "+e);
				e.printStackTrace();
				session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
				res.sendRedirect("../jsp/admin/batterystore/battery/batteryprice.jsp");
			}
		}
		else if(strWhatToDo.equalsIgnoreCase("getstatetoedit"))
		  {
			String keyword = (req.getParameter("keyword")!=null)?(req.getParameter("keyword")):"";
			LogLevel.DEBUG(5,new Throwable(),"keyword :"+keyword );
			try
			  {
				String strSqlQry ="select distinct(state) from batteryprice order by state asc";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

				Vector EditpriceBrandVector=qm.executeQuery(strSqlQry);
				LogLevel.DEBUG(5,new Throwable(),"EditpriceBrandVector:"+EditpriceBrandVector );
				if(EditpriceBrandVector.isEmpty())
				{
					LogLevel.DEBUG(1,new Throwable(),"Failed to fetch categories ");
					session.setAttribute("priority","1");
					session.setAttribute("sesadminloginErrorMsg", "<font color='#00dd00' class='vrb10'>Failed to Fetch States! </font> ");
					res.sendRedirect("../jsp/admin/batterystore/battery/editbatteryprice.jsp");
					return;
				}
				else
				{
					if(keyword.equals("editobrp"))
					{
					LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched Categories");
					session.setAttribute("seseditobrp", EditpriceBrandVector);
					session.setAttribute("sesErrorMsgss","<font color='#CC0000' class='vrb10'>Successfully Fetched batteryprices.</font> ");
					res.sendRedirect("../jsp/admin/batterystore/battery/editoldbatteryprice.jsp?keyword="+keyword);
					return;
					}
					else
					{
					LogLevel.DEBUG(1, new Throwable(),"Succesfully Fetched Categories");
					session.setAttribute("sesEditpriceBrandVector", EditpriceBrandVector);
					session.setAttribute("sesErrorMsgss","<font color='#CC0000' class='vrb10'>Succesfully Fetched States.</font> ");
					res.sendRedirect("../jsp/admin/batterystore/battery/editbatteryprice.jsp");
					return;
					}
				}
				}
			catch(IOException ioe)
				{
					//LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
					ioe.printStackTrace();
					session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/battery/editbatteryprice.jsp");
				}
			catch(Exception e)
				{
					//LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/battery/editbatteryprice.jsp");
				}
		     }
		else if(strWhatToDo.equalsIgnoreCase("getbrands"))
		  {
						
			String result="";
			try
			  {
			String battery_brand="";	

	 		ServletOutputStream out=res.getOutputStream();
			
			String strSqlQry ="select distinct(bat_brand) from batteryprice order by bat_brand asc";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector BatteryVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"BatteryVector:"+BatteryVector );

			if(BatteryVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&lt;-&nbsp;&nbsp; Select Brand &nbsp;&nbsp;-&gt;</option>");
				return;
			}
			else
			{
			for (int i=0; i<BatteryVector.size(); i++)
				{
					if(i==0)
					{
					out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&lt;-&nbsp;&nbsp; Select Brand &nbsp;&nbsp;-&gt;</option>");
					}
					Hashtable ht1=(Hashtable)BatteryVector.get(i);
					//bat_id =String.valueOf(ht1.get("bat_id"));
					battery_brand =String.valueOf(ht1.get("bat_brand"));
					out.print("<option style='background:#FFF' value='"+battery_brand+"'>"+battery_brand+"</option>"); 
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

		else if(strWhatToDo.equalsIgnoreCase("getbattype"))
		  {
			String strbrand = (req.getParameter("brand") != null)? (req.getParameter("brand")) : "";
			try
			  {
			String batname="";	

	 		ServletOutputStream out=res.getOutputStream();
			
			String strSqlQry ="select distinct(bat_name) from battery_details where bat_brand='"+strbrand+"' order by bat_name asc";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector BatNameVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"BatNameVector:"+BatNameVector );

			if(BatNameVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&lt;-&nbsp;Select Battery Name&nbsp;-&gt;</option>");
				return;
			}
			else
			{
			for (int i=0; i<BatNameVector.size(); i++)
				{
					if(i==0)
					{
					out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&lt;-&nbsp;Select Battery Name&nbsp;-&gt;</option>");
					}
					Hashtable ht1=(Hashtable)BatNameVector.get(i);
					//bat_id =String.valueOf(ht1.get("bat_id"));
					batname =String.valueOf(ht1.get("bat_name"));
					out.print("<option style='background:#FFF' value='"+batname+"'>"+batname+"</option>"); 
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
		else if(strWhatToDo.equalsIgnoreCase("gebattmodels"))
		  {
			String strbatname = (req.getParameter("batname") != null)? (req.getParameter("batname")) : "";
			try
			  {
			String batmodel="";	
			String batcap="";

	 		ServletOutputStream out=res.getOutputStream();
			
			String strSqlQry ="select distinct(bat_model),bat_capacity from battery_details where bat_name='"+strbatname+"' order by bat_model asc";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector BatModelVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"BatModelVector:"+BatModelVector );

			if(BatModelVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&lt;-&nbsp;Select Model&nbsp;-&gt;</option>");
				return;
			}
			else
			{
			for (int i=0; i<BatModelVector.size(); i++)
				{
					if(i==0)
					{
					out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&lt;-&nbsp;Select Model&nbsp;-&gt;</option>");
					}
					Hashtable ht1=(Hashtable)BatModelVector.get(i);
					//bat_id =String.valueOf(ht1.get("bat_id"));
					batmodel =String.valueOf(ht1.get("bat_model"));
					batcap =String.valueOf(ht1.get("bat_capacity"));
					out.print("<option style='background:#FFF' value='"+batcap+","+batmodel+"'>"+batmodel+"</option>"); 
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
		else if(strWhatToDo.equalsIgnoreCase("gebatcapacity"))
		  {
			String strbatname = (req.getParameter("batname") != null)? (req.getParameter("batname")) : "";
			try
			  {
			String batcap="";

	 		ServletOutputStream out=res.getOutputStream();
			
			String strSqlQry ="select distinct(bat_capacity) from battery_details where bat_name='"+strbatname+"' order by bat_capacity asc";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector BatCapacityVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"BatCapacityVector:"+BatCapacityVector );

			if(BatCapacityVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&lt;-&nbsp;Select Capacity&nbsp;-&gt;</option>");
				return;
			}
			else
			{
			for (int i=0; i<BatCapacityVector.size(); i++)
				{
					if(i==0)
					{
					out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&lt;-&nbsp;Select Capacity&nbsp;-&gt;</option>");
					}
					Hashtable ht1=(Hashtable)BatCapacityVector.get(i);
					//bat_id =String.valueOf(ht1.get("bat_id"));
					batcap =String.valueOf(ht1.get("bat_capacity"));
					out.print("<option style='background:#FFF' value='"+batcap+"'>"+batcap+"</option>"); 
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
		else if(strWhatToDo.equalsIgnoreCase("getcities"))
		  {
			String strstate = (req.getParameter("state") != null)? (req.getParameter("state")) : "";
			try
			  {
			String city="";	

	 		ServletOutputStream out=res.getOutputStream();
			
			String strSqlQry ="select distinct(city) from search_whereever_keywords where state='"+strstate+"' order by city asc";
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
			   
		else if(strWhatToDo.equalsIgnoreCase("getcities_mul"))
		  {
			String strstate = (req.getParameter("state") != null)? (req.getParameter("state")) : "";
			
			LogLevel.DEBUG(5,new Throwable(),"strstate:"+strstate );
			try
			  {
			String city="";	

	 		ServletOutputStream out=res.getOutputStream();
			
			String strSqlQry ="select distinct(city) from search_whereever_keywords where state in("+strstate+") order by city asc";
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
					//out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&lt;-&nbsp;Select City&nbsp;-&gt;</option>");
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
 
	   
			   
		else if(strWhatToDo.equalsIgnoreCase("getmodel"))
		  {
			String brands = (req.getParameter("brands") != null)? (req.getParameter("brands")) : "";
			String result="";
			try
			  {
			String bat_model="";	

	 		ServletOutputStream out=res.getOutputStream();
			
			String strSqlQry ="select distinct(bat_model) from batteryprice where bat_brand='"+brands+"' order by bat_model asc";
			LogLevel.DEBUG(5, new Throwable(), "strSqlQry :" + strSqlQry);

			Vector BatteryVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"BatteryVector:"+BatteryVector );

			if(BatteryVector.isEmpty())
			{ 
				out.println("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&lt;-&nbsp;&nbsp; Select Model &nbsp;&nbsp;-&gt;</option>");
				return;
			}
			else
			{
			for (int i=0; i<BatteryVector.size(); i++)
				{
					if(i==0)
					{
					out.print("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>&lt;-&nbsp;&nbsp; Select Model &nbsp;&nbsp;-&gt;</option>");
					}
					Hashtable ht1=(Hashtable)BatteryVector.get(i);
					//bat_id =String.valueOf(ht1.get("bat_id"));
					bat_model =String.valueOf(ht1.get("bat_model"));
					out.print("<option style='background:#FFF' value='"+bat_model+"'>"+bat_model+"</option>"); 
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
		else if(strWhatToDo.equalsIgnoreCase("getpricestoupdate"))
		  {
			//String state = (req.getParameter("state")!=null)?(req.getParameter("state")):"";
			//LogLevel.DEBUG(5,new Throwable(),"state :"+state );

			//String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
			//LogLevel.DEBUG(5,new Throwable(),"city :"+city );

			String brand= (req.getParameter("brand")!=null)?(req.getParameter("brand")):"";
			LogLevel.DEBUG(5,new Throwable(),"brand :"+brand );

			String model= (req.getParameter("model")!=null)?(req.getParameter("model")):"";
			LogLevel.DEBUG(5,new Throwable(),"model :"+model );
			ServletOutputStream out=res.getOutputStream();
			try
			  {  
				/* @strSqlQry3 : carries the query to Insert the user Work details into professional_detl table.*/
				//String strSqlQry4 = "select batprice_id,bat_act_price,bat_witbat_price,bat_ret_price,erp_pre_tax from batteryprice where state='"+state+"' and city='"+city+"' and bat_brand='"+brand+"' and bat_model='"+model+"'";
				String strSqlQry4 = "select batprice_id,bat_act_price,bat_witbat_price,bat_ret_price,erp_pre_tax from batteryprice where bat_brand='"+brand+"' and bat_model='"+model+"'";
				
				
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry4 :"+strSqlQry4 );

				Vector batteryprice=qm.executeQuery(strSqlQry4);
				LogLevel.DEBUG(5,new Throwable(),"batteryprice:"+batteryprice);
				String strRes="<table  align='center' width='100%' border='0'>";
							
				if(batteryprice==null || batteryprice.size() == 0)
				  {
					out.println("<p align='center' class='insidecontent'>No Prices are Avaliable based on selection!<a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
					return;
				  }
				 else
				  {
				   for(int j=0; j<batteryprice.size();j++)
					 {
						Hashtable ht3=(Hashtable)batteryprice.get(j);
						String batpriceid=String.valueOf(ht3.get("batprice_id"));
						String stractprice=String.valueOf(ht3.get("bat_act_price"));
						String strdisprice=String.valueOf(ht3.get("bat_witbat_price"));
						String strreturnprice=String.valueOf(ht3.get("bat_ret_price"));
						String strerpprice=String.valueOf(ht3.get("erp_pre_tax"));
						
						strRes=strRes+"<tr><td align='right' class='insidecontent' width='42%'>Actual&nbsp;Price</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='actprice' value='"+stractprice+"' size='30' maxlength='7'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='insidecontent' width='42%'>Discount&nbsp;Price</font>&nbsp;</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='disprice' value='"+strdisprice+"' size='30' maxlength='7'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='insidecontent' width='42%'>Battery&nbsp;Return&nbsp;Price</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='retnprice' value='"+strreturnprice+"' size='30' maxlength='7'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='insidecontent' width='42%'>E-Retailer&nbsp;Price[ERP]</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='erpprice' value='"+strerpprice+"' size='30' maxlength='7'></td></tr>";
						strRes=strRes+"<tr><td align='right' width='42%'></td><td align='left'><input type='button'  onclick=\"funToUpdatprices();\" value='Update' class='smallbutton'>&nbsp;<input type='button'  onclick=\"funToDeleteprices('"+batpriceid+"');\" value='Delete' class='smallbutton'></td>";
						//strRes=strRes+"<td align='left' ><input type='button' onclick=\"Schhide('"+id+"');\" value='Cancel' class='smallbutton'></td></tr>";
					   }
					}
					strRes=strRes+"</table>";							
					out.println(strRes);
					return;				
				}
			   catch(IOException ioe)
			    {
					LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
					ioe.printStackTrace();
					session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/battery/editbatteryprice.jsp");
			    }
			   catch(Exception e)
			    {
					LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/battery/editbatteryprice.jsp");
			    }
		  }
		  else if(strWhatToDo.equalsIgnoreCase("getobrptoupdate"))
		  {
			String state = (req.getParameter("state")!=null)?(req.getParameter("state")):"";
			LogLevel.DEBUG(5,new Throwable(),"state :"+state );

			String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
			LogLevel.DEBUG(5,new Throwable(),"city :"+city );

			String brand= (req.getParameter("brand")!=null)?(req.getParameter("brand")):"";
			LogLevel.DEBUG(5,new Throwable(),"brand :"+brand );

			String batname= (req.getParameter("batname")!=null)?(req.getParameter("batname")):"";
			LogLevel.DEBUG(5,new Throwable(),"batname :"+batname );

			String capacity= (req.getParameter("capacity")!=null)?(req.getParameter("capacity")):"";
			LogLevel.DEBUG(5,new Throwable(),"capacity :"+capacity );
			ServletOutputStream out=res.getOutputStream();
			try
			  {  
				String strSqlQrybatmodel ="select bat_model from battery_details where bat_capacity='"+capacity+"' and bat_name='"+batname+"'";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQrybatmodel :" + strSqlQrybatmodel);

				ArrayList htav=new ArrayList();
				htav=qm.getField(strSqlQrybatmodel);

				String batmodels="";
				for(int i=0;i<htav.size();i++)
				{
				if(batmodels.equals(""))
				batmodels="'"+htav.get(i).toString()+"'";
				else
				batmodels=batmodels+","+ "'"+htav.get(i).toString()+"'";
				}
				LogLevel.DEBUG(5,new Throwable(),"batmodels:"+batmodels);

				/* @strSqlQry3 : carries the query to Insert the user Work details into professional_detl table.*/
				String strSqlQry4 = "select distinct(bat_ret_price) from batteryprice where state='"+state+"' and city='"+city+"' and bat_brand='"+brand+"' and bat_model in("+batmodels+")";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQry4 :"+strSqlQry4 );

				Vector batteryobrprice=qm.executeQuery(strSqlQry4);
				LogLevel.DEBUG(5,new Throwable(),"batteryobrprice:"+batteryobrprice);
				String strRes="<table  align='center' width='100%' border='0'>";
							
				if(batteryobrprice==null || batteryobrprice.size() == 0)
				  {
					out.println("<p align='center' class='insidecontent'>No OBR Prices are Avaliable based on selection!<a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
					return;
				  }
				 else
				  {
				   for(int j=0; j<batteryobrprice.size();j++)
					 {
						Hashtable ht3=(Hashtable)batteryobrprice.get(j);
						//String batpriceid=String.valueOf(ht3.get("batprice_id"));
						String strreturnprice=String.valueOf(ht3.get("bat_ret_price"));
						
						//strRes=strRes+"<tr><td align='right' class='insidecontent' width='42%'>Actual&nbsp;Price</td>";
						//strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='actprice' value='"+stractprice+"' size='30' maxlength='7'></td></tr>";
						//strRes=strRes+"<tr><td align='right' class='insidecontent' width='42%'>Discount&nbsp;Price</font>&nbsp;</td>";
						//strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='disprice' value='"+strdisprice+"' size='30' maxlength='7'></td></tr>";
						strRes=strRes+"<tr><td align='right' class='insidecontent' width='42%'>Battery&nbsp;Return&nbsp;Price</td>";
						strRes=strRes+"<td align='left'><input class='insidecontent' type='text' name='retnprice' value='"+strreturnprice+"' size='30' maxlength='7'></td></tr>";
						strRes=strRes+"<tr><td align='right' width='42%'></td><td align='left'><input type='button'  onclick=\"funToUpdatobrprices();\" value='Update' class='smallbutton'></td>";
						//strRes=strRes+"<td align='left' ><input type='button' onclick=\"Schhide('"+id+"');\" value='Cancel' class='smallbutton'></td></tr>";
					   }
					}
					strRes=strRes+"</table>";							
					out.println(strRes);
					return;				
				}
			   catch(IOException ioe)
			    {
					LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
					ioe.printStackTrace();
					session.setAttribute("sesErrorMsg",	"Generall Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/battery/editoldbatteryprice.jsp");
			    }
			   catch(Exception e)
			    {
					LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
					e.printStackTrace();
					session.setAttribute("sesErrorMsg",	"General Error...Please Try Again" );
					res.sendRedirect("../jsp/admin/batterystore/battery/editoldbatteryprice.jsp");
			    }
		  }
		  /*This methos is used to insert vehicle details into vehicle_details*/
		else if(strWhatToDo.equalsIgnoreCase("edbattiteryprices"))
		{
			String batacprice = (req.getParameter("batacprice")!=null)?(req.getParameter("batacprice")):"";
			LogLevel.DEBUG(5,new Throwable(),"batacprice :"+batacprice );

			String batdisprice = (req.getParameter("batdisprice")!=null)?(req.getParameter("batdisprice")):"";
			LogLevel.DEBUG(5,new Throwable(),"batdisprice :"+batdisprice );

			String batwithprice = (req.getParameter("batwithprice")!=null)?(req.getParameter("batwithprice")):"";
			LogLevel.DEBUG(5,new Throwable(),"batwithprice :"+batwithprice );

			String erpprice = (req.getParameter("erpprice")!=null)?(req.getParameter("erpprice")):"";
			LogLevel.DEBUG(5,new Throwable(),"erpprice :"+erpprice );

			String batid = (req.getParameter("batid")!=null)?(req.getParameter("batid")):"";
			LogLevel.DEBUG(5,new Throwable(),"batid :"+batid );
			int reslt=0;
			try
			{
				/*Query to get the Admin User name and Password*/
 				String strSqlQry = "update batteryprice set bat_act_price='"+batacprice+"',bat_witbat_price='"+batdisprice+"',bat_ret_price='"+batwithprice+"',erp_pre_tax='"+erpprice+"' where batprice_id='"+batid+"'";
				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
				
				reslt = qm.executeUpdate(strSqlQry);
		
				if(reslt <0)
				{
						LogLevel.DEBUG(1,new Throwable()," Failed to update your details! ");
						session.setAttribute("priority","1");
						session.setAttribute("sesleditbatterypriceErrorMsg", "<font color='#FF3333' class='top1'>Failed to updat battery price! </font> ");
						res.sendRedirect("../jsp/admin/batterystore/battery/editbatteryprice.jsp");
						return;
					}
				   else
				    {
						LogLevel.DEBUG(1,new Throwable(),"Successfully updated your details! ");
						session.setAttribute("sesleditbatterypriceErrorMsg", "<font color='#000000' class='top1'>Successfully Updated Battery prices.!</font>");
						//session.setAttribute("sescontVector", reslt);
						res.sendRedirect("../jsp/admin/batterystore/battery/editbatteryprice.jsp");
				    }
			     }
			    catch(IOException ioe)
			     {
						LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
						ioe.printStackTrace();
						session.setAttribute("sesleditbatterypriceErrorMsg",	"Generall Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/battery/editbatteryprice.jsp");
			     }
			    catch(Exception e)
			     {
						LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
						e.printStackTrace();
						session.setAttribute("sesleditbatterypriceErrorMsg",	"General Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/battery/editbatteryprice.jsp");
			     }
		}
		  /*This methos is used to insert vehicle details into vehicle_details*/
		else if(strWhatToDo.equalsIgnoreCase("deletebatteryprices"))
		{
			String chkSi = (req.getParameter("chkSi")!=null)?(req.getParameter("chkSi")):"";
			LogLevel.DEBUG(5,new Throwable(),"chkSi :"+chkSi );
			int reslt=0;
			try
			{
				/*Query to get the Admin User name and Password*/
 				String strSqlQry = "delete from batteryprice where batprice_id='"+chkSi+"'";
				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
				reslt = qm.executeUpdate(strSqlQry);
		
				if(reslt <0)
				{
						LogLevel.DEBUG(1,new Throwable()," Failed to update your details! ");
						session.setAttribute("priority","1");
						session.setAttribute("sesleditbatterypriceErrorMsg", "<font color='#FF3333' class='top1'>Failed to updat battery price! </font> ");
						res.sendRedirect("../jsp/admin/batterystore/battery/editbatteryprice.jsp");
						return;
					}
				   else
				    {
						LogLevel.DEBUG(1,new Throwable(),"Successfully updated your details! ");
						session.setAttribute("sesleditbatterypriceErrorMsg", "<font color='#000000' class='top1'>Successfully Updated Battery prices.!</font>");
						//session.setAttribute("sescontVector", reslt);
						res.sendRedirect("../jsp/admin/batterystore/battery/editbatteryprice.jsp");
				    }
			     }
			    catch(IOException ioe)
			     {
						LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
						ioe.printStackTrace();
						session.setAttribute("sesleditbatterypriceErrorMsg",	"Generall Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/battery/editbatteryprice.jsp");
			     }
			    catch(Exception e)
			     {
						LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
						e.printStackTrace();
						session.setAttribute("sesleditbatterypriceErrorMsg",	"General Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/battery/editbatteryprice.jsp");
			     }
		}
		  /*This methos is used to insert vehicle details into vehicle_details*/
		else if(strWhatToDo.equalsIgnoreCase("edbattiteryobrprices"))
		{
			String batbrand = (req.getParameter("batbrand")!=null)?(req.getParameter("batbrand")):"";
			LogLevel.DEBUG(5,new Throwable(),"batbrand :"+batbrand ); 

			String batcap = (req.getParameter("batcap")!=null)?(req.getParameter("batcap")):"";
			LogLevel.DEBUG(5,new Throwable(),"batcap :"+batcap );

			String batname = (req.getParameter("batname")!=null)?(req.getParameter("batname")):"";
			LogLevel.DEBUG(5,new Throwable(),"batname :"+batname );
			
			String state = (req.getParameter("state")!=null)?(req.getParameter("state")):"";
			LogLevel.DEBUG(5,new Throwable(),"state :"+state );
		
			String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
			LogLevel.DEBUG(5,new Throwable(),"city :"+city );

			String batwithprice = (req.getParameter("batwithprice")!=null)?(req.getParameter("batwithprice")):"";
			LogLevel.DEBUG(5,new Throwable(),"batwithprice :"+batwithprice );

			int reslt=0;
			try
			{
				String strSqlQrybatmodel ="select bat_model from battery_details where bat_capacity='"+batcap+"' and bat_name='"+batname+"'";
				LogLevel.DEBUG(5, new Throwable(), "strSqlQrybatmodel :" + strSqlQrybatmodel);

				ArrayList htav=new ArrayList();
				htav=qm.getField(strSqlQrybatmodel);

				String batmodels="";
				for(int i=0;i<htav.size();i++)
				{
				if(batmodels.equals(""))
				batmodels="'"+htav.get(i).toString()+"'";
				else
				//batmodels=batmodels+","+htav.get(i).toString();
				batmodels=batmodels+","+ "'"+htav.get(i).toString()+"'";
				}
				LogLevel.DEBUG(5,new Throwable(),"batmodels:"+batmodels);

				/*Query to get the Admin User name and Password*/
 				String strSqlQry = "update batteryprice set bat_ret_price='"+batwithprice+"' where bat_brand='"+batbrand+"' and bat_model in("+batmodels+") and state='"+state+"' and  city='"+city+"'";
				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
				
				reslt = qm.executeUpdate(strSqlQry);
		
				if(reslt <0)
				{
						LogLevel.DEBUG(1,new Throwable()," Failed to update your details! ");
						session.setAttribute("priority","1");
						session.setAttribute("sesleditbatterypriceErrorMsg", "<font color='#FF3333' class='top1'>Failed to updat battery OBR price! </font> ");
						res.sendRedirect("../jsp/admin/batterystore/battery/editoldbatteryprice.jsp");
						return;
					}
				   else
				    {
						LogLevel.DEBUG(1,new Throwable(),"Successfully updated your details! ");
						session.setAttribute("sesleditbatterypriceErrorMsg", "<font color='#000000' class='top1'>Successfully Updated Battery OBR prices.!</font>");
						//session.setAttribute("sescontVector", reslt);
						res.sendRedirect("../jsp/admin/batterystore/battery/editoldbatteryprice.jsp");
				    }
			     }
			    catch(IOException ioe)
			     {
						LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
						ioe.printStackTrace();
						session.setAttribute("sesleditbatterypriceErrorMsg",	"Generall Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/battery/editoldbatteryprice.jsp");
			     }
			    catch(Exception e)
			     {
						LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
						e.printStackTrace();
						session.setAttribute("sesleditbatterypriceErrorMsg",	"General Error...Please Try Again" );
						res.sendRedirect("../jsp/admin/batterystore/battery/editoldbatteryprice.jsp");
			     }
		}
		
		
		
		
		else if(strWhatToDo.equalsIgnoreCase("updatestockdetails"))
		{
			String batbrand = (req.getParameter("batbrand")!=null)?(req.getParameter("batbrand")):"";
			LogLevel.DEBUG(5,new Throwable(),"batbrand :"+batbrand ); 

			//String batcap = (req.getParameter("batcap")!=null)?(req.getParameter("batcap")):"";
			//LogLevel.DEBUG(5,new Throwable(),"batcap :"+batcap );

			String batname = (req.getParameter("batname")!=null)?(req.getParameter("batname")):"";
			LogLevel.DEBUG(5,new Throwable(),"batname :"+batname );
			
			String batmodel1 = (req.getParameter("batmodel")!=null)?(req.getParameter("batmodel")):"";
			LogLevel.DEBUG(5,new Throwable(),"batmodel :"+batmodel1 );
			
			String mySplitResult1[] = batmodel1.split(",");
			String batcap=mySplitResult1[0];
			//alert(batcap);
			String batmodel=mySplitResult1[1];
			
			String state = (req.getParameter("state")!=null)?(req.getParameter("state")):"";
			LogLevel.DEBUG(5,new Throwable(),"state :"+state );
		
			String city = (req.getParameter("cityselected")!=null)?(req.getParameter("cityselected")):"";
			LogLevel.DEBUG(5,new Throwable(),"cityselected :"+city );
			
			String stock = (req.getParameter("stock")!=null)?(req.getParameter("stock")):"";
			LogLevel.DEBUG(5,new Throwable(),"stock :"+stock );


			int reslt=0;
			try
			{
				//String strSqlQrybatmodel ="update batteryprice set model_stock='"+stock+"',updated_date=now() where bat_brand='"+batbrand+"' and bat_model='"+batmodel+"' and state in("+state+") and city in("+city+")";
				
				
				
				String strSqlQrybatmodel ="update batteryprice set model_stock='"+stock+"',updated_date=now() where bat_brand='"+batbrand+"' and bat_model='"+batmodel+"' and city in("+city+")";

				LogLevel.DEBUG(5, new Throwable(), "strSqlQrybatmodel :" + strSqlQrybatmodel);

				
				reslt = qm.executeUpdate(strSqlQrybatmodel);
		
				if(reslt <0)
				{
						LogLevel.DEBUG(1,new Throwable()," Failed to update your details! ");
						session.setAttribute("priority","1");
						session.setAttribute("sesupdatebatterystockErrorMsg", "<font color='#FF3333' class='top1'>Failed to update Stock Details! </font> ");
						res.sendRedirect("/bookbattery/jsp/admin/batterystore/stock/batterystock.jsp");
						return;
					}
				   else
				    {
						LogLevel.DEBUG(1,new Throwable(),"Successfully updated your details! ");
						session.setAttribute("sesupdatebatterystockErrorMsg", "<font color='#000000' class='top1'>Successfully Updated Stock Details.!</font>");
						//session.setAttribute("sescontVector", reslt);
						res.sendRedirect("/bookbattery/jsp/admin/batterystore/stock/batterystock.jsp");
				    }
			     }
			    catch(IOException ioe)
			     {
						LogLevel.ERROR(0,ioe,"problem Caught IOException if(add) !! "+ioe);
						ioe.printStackTrace();
						session.setAttribute("sesupdatebatterystockErrorMsg",	"Generall Error...Please Try Again" );
						res.sendRedirect("/bookbattery/jsp/admin/batterystore/stock/batterystock.jsp");
			     }
			    catch(Exception e)
			     {
						LogLevel.ERROR(0,e,"Problem Caught Exception if(add)!! "+e);
						e.printStackTrace();
						session.setAttribute("sesupdatebatterystockErrorMsg",	"General Error...Please Try Again" );
						res.sendRedirect("/bookbattery/jsp/admin/batterystore/stock/batterystock.jsp");
			     }
		}
	
		
		
		
		
		
		}
	}