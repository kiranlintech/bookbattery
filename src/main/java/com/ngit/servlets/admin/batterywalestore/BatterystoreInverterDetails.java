/***********************************************************************		
	NGIT Confidential. 
	@File Name   : BatterystoreInverterDetails.java
	@Description : This Servlet is used to allow the operator to Login
	@Date        : 18th October 2019
******************************************************************/ 
package com.ngit.servlets.admin.batterywalestore; 

import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.mail.*;
import com.ngit.javabean.consumers.products.Order_Store_SMS;
import com.ngit.javabean.sendsms.SendSMS;

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
import static java.lang.System.out;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.json.*; 
import net.sf.ezmorph.*; 


/*
 * @author Prasanna Kumari.
 */
/* This class is to Admin Login. This class initializes the necessary connection pools and perform the transactions on the pools. */
public class BatterystoreInverterDetails extends HttpServlet 
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
	
	/*This doPost service method handles all the requests and responses passing from doGet service method to perform Admin Login.*/
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
		doGet(req,res);
	}
 	/* This doGet service method calls doPost service method to handle all the reqs and responses to perform Admin Login. */
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
		String strWhatToDo = (req.getParameter("hidWhatToDo")!=null)?req.getParameter("hidWhatToDo"):""; 
		//ServletOutputStream out=res.getOutputStream(); 
		
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
				String strRes=getinverterdetails(req,res,session);
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
		if(strWhatToDo.equalsIgnoreCase("viewinverterdetails"))
		{ 
			try
				{
					String strRes=viewinverterdetails(req,res,session);
					LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
					
					
					RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/batterywalestore/orders/inverterdetails.jsp");
					RD.forward(req, res);
					out.close();
				}
				catch (Exception e)
				{										
					LogLevel.ERROR(1, e, "Error :" + e);
				}	
		
	     }
	/* ****************************************************************************************\
	* This action is used to  calculatet the discount price
	\* *****************************************************************************************/		
	if(strWhatToDo.equalsIgnoreCase("calculatepercentage"))
		{ 
			try
			{
				String strRes=calculatepercentage(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				out.println(strRes);
				out.close();
			
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}	/* ****************************************************************************************\
	* This action is used to  calculatet the discount price
	\* *****************************************************************************************/		
	if(strWhatToDo.equalsIgnoreCase("insertorderdetails"))
		{ 
			try
			{
				String strRes=insertorderdetails(req,res,session);
				LogLevel.DEBUG(5, new Throwable(), "strRes :" + strRes);
				ServletOutputStream out=res.getOutputStream(); 
				out.println(strRes);
				out.close();
			
			}
			catch (Exception e)
			{										
				LogLevel.ERROR(1, e, "Error :" + e);
			}	
		}
		
	} //End of doGet
	/* **************************************************************************************************************************************\
	* This method is to get the inverter capacity
	* @strinvertercapacity : carries the query to get the inverter capacity from inverter_details table.
	\* **************************************************************************************************************************************/
	public String getinvertercapacity(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
	String strRes="";
		try
		{	
			String invertertype= (req.getParameter("invertertype")!=null)?(req.getParameter("invertertype")):"";
			
			String inverter_capacity="";
	 		ServletOutputStream out=res.getOutputStream();

			String strinvertercapacity = "select distinct(inverter_capacity) from inverter_details order by inverter_capacity asc"; 
			LogLevel.DEBUG(5, new Throwable(), "strinvertercapacity :" + strinvertercapacity);
			
			Vector invertercapacityvector=qm.executeQuery(strinvertercapacity);
			LogLevel.DEBUG(5,new Throwable(),"invertercapacityvector:"+invertercapacityvector);
			if(invertercapacityvector.isEmpty())
			{ 
				strRes=strRes+"<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>Select&nbsp;Inverter&nbsp;Capacity</option>";
				return strRes;
			}
			else
			{
				for (int i=0; i<invertercapacityvector.size(); i++)
					{
						if(i==0)
						{
						strRes=strRes+"<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>Select&nbsp;Inverter&nbsp;Capacity</option>";
						}
						Hashtable ht=(Hashtable)invertercapacityvector.get(i);
						
						inverter_capacity =String.valueOf(ht.get("inverter_capacity"));
						LogLevel.DEBUG(5,new Throwable(),"inverter_capacity:"+inverter_capacity);
						strRes=strRes+"<option style='background:#FFF' value='"+inverter_capacity+"'>"+inverter_capacity+"</option>"; 
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
	public String getinverterbrand(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String keyword= (req.getParameter("keyword")!=null)?(req.getParameter("keyword")):"";
			LogLevel.DEBUG(5, new Throwable(), "keyword :" + keyword);
	String strRes="";
		try
		{	
			
			String inverter_brand="";
			String strinverterbrand="";
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
				strRes=strRes+"<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>Select&nbsp;Inverter&nbsp;Make</option>";
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
	public String getinverterdetails(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strinvertertype= (req.getParameter("invertertype")!=null)?(req.getParameter("invertertype")):"";
		LogLevel.DEBUG(5,new Throwable(),"strinvertertype:"+strinvertertype );

		String invertercapacity= (req.getParameter("invertercapacity")!=null)?(req.getParameter("invertercapacity")):"";
		LogLevel.DEBUG(5,new Throwable(),"invertercapacity:"+invertercapacity );
		
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

		String strpincode= (req.getParameter("pincode")!=null)?(req.getParameter("pincode")):"";
		LogLevel.DEBUG(5,new Throwable(),"strpincode:"+strpincode);
		
		String sortpricess = "";
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
			if(!inverterbrand.equals("All") )
			{
				strConditions1= " and inverter_brand='"+inverterbrand+"' ";
			}
			else
			{	
				strConditions1= "";
			}
			String strConditionsDetails="";
			if(!inverterbrand.equals("All") )
			{
				strConditionsDetails= "where  a.inverter_brand='"+inverterbrand+"' and ";
			}
			else
			{	
				strConditionsDetails= "where";
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

			String strInverterDetails  ="select a.inverter_brand,a.inverter_model,a.inverter_capacity,a.inverter_warranty,a.computer,a.fans,a.tubelights,a.television,a.icon_url,a.description,b.inverter_actual_price,b.inverter_discount_price from inverter_details a, inverter_price_details b "+strConditionsDetails+"  b.inverter_capacity='"+invertercapacity+"' and b.state='"+strstate+"' and b.city='"+city+"' and a.inverter_brand=b.inverter_brand and a.inverter_model=b.inverter_model and a.inverter_id in ("+inverterIds+") group by a.inverter_model order by b.inverter_discount_price "+sortpricess+"";
			LogLevel.DEBUG(5, new Throwable(), "strInverterDetails :" + strInverterDetails);

			Vector InverterdetailsVector=qm.executeQuery(strInverterDetails);
			LogLevel.DEBUG(5,new Throwable(),"InverterdetailsVector:"+InverterdetailsVector );
			
			if(InverterdetailsVector.isEmpty())
			{ 
				LogLevel.DEBUG(1,new Throwable(),"Failed to fetch Inverter details ");
				session.setAttribute("priority","1");
				session.setAttribute("sesbatterydetailsErrorMsg", "<font color='#ff3333' class='vrb10'>No Inverter details found based on selection.! </font> ");
				session.removeAttribute("InverterdetailsVector");
				res.sendRedirect("/bookbattery/jsp/batterywalestore/orders/orderinverter.jsp");
				return strRes;
			}
			else
			{
				
				LogLevel.DEBUG(1, new Throwable(),"Successfully Fetched Inverter Details");
				session.setAttribute("InverterdetailsVector", InverterdetailsVector); 
				res.sendRedirect("/bookbattery/jsp/batterywalestore/orders/orderinverterdetails.jsp?invertertype="+strinvertertype+"&inverterbrand="+inverterbrand+"&invertercapacity="+invertercapacity+"&city="+strcity+"&pincity="+city+"&strarea="+area+"&strstate="+strstate+"&strpincode="+strpincode);
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
	public String viewinverterdetails(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strinverterbrand= (req.getParameter("inverterbrand")!=null)?(req.getParameter("inverterbrand")):"";
		LogLevel.DEBUG(5,new Throwable(),"strinverterbrand:"+strinverterbrand );

		String strinvertermodel= (req.getParameter("invertermodel")!=null)?(req.getParameter("invertermodel")):"";
		LogLevel.DEBUG(5,new Throwable(),"strinvertermodel:"+strinvertermodel ); 

		String strstate= (req.getParameter("state")!=null)?(req.getParameter("state")):"";
		LogLevel.DEBUG(5,new Throwable(),"strstate:"+strstate );

		String strcity= (req.getParameter("city")!=null)?(req.getParameter("city")):"";
		LogLevel.DEBUG(5,new Throwable(),"strcity:"+strcity );

		String area= (req.getParameter("area")!=null)?(req.getParameter("area")):"";
		LogLevel.DEBUG(5,new Throwable(),"area:"+area ); 
		
		String strpincode= (req.getParameter("pincode")!=null)?(req.getParameter("pincode")):"";
		LogLevel.DEBUG(5,new Throwable(),"strpincode:"+strpincode);
		
		String strRes="";
		String Battery_Type="";
		String Vehicle_Name="";
		String Vehicle_Model="";
		String Manufacturer="";
		String Model="";
		String State="";
		String City="";
		try
		{	
			
			
			
			State= strstate.replaceAll("-", " ");
			City= strcity.replaceAll("-", " ");

			String Get_Inverter_Details_SQL = "select DISTINCT a.inverter_brand,a.inverter_model,a.inverter_name,a.inverter_capacity,a.inverter_warranty,a.computer,a.fans,a.tubelights,a.television,a.icon_url,a.description,b.inverter_actual_price,b.inverter_discount_price,b.inverter_eretailer_price,b.model_stock from inverter_details a, inverter_price_details b  where  b.state='"+State+"' and b.city='"+City+"' and a.inverter_brand='"+strinverterbrand+"' and a.inverter_model='"+strinvertermodel+"' and  b.inverter_model='"+strinvertermodel+"' LIMIT 1"; 
			LogLevel.DEBUG(5, new Throwable(), "Get_Inverter_SQL :" + Get_Inverter_Details_SQL);
	
			Vector Vector_Inverter_Details=qm.executeQuery(Get_Inverter_Details_SQL);
			LogLevel.DEBUG(5,new Throwable(),"Vector_Inverter_Details:"+Vector_Inverter_Details);
			
			if(Vector_Inverter_Details.isEmpty())
			{ 
				strRes="404 Page Not Found";
				RequestDispatcher RD = getServletContext().getRequestDispatcher("/jsp/404.jsp");
				RD.forward(req, res);
				out.close();
				return strRes;
			}
			else
			{
					int i=0;
					Hashtable ht=(Hashtable)Vector_Inverter_Details.get(i);
					req.setAttribute("Inverter_Brand",String.valueOf(ht.get("inverter_brand")));
					LogLevel.DEBUG(5, new Throwable(), "Inverter_Brand :" + String.valueOf(ht.get("inverter_brand")));
					
					req.setAttribute("Inverter_Model",String.valueOf(ht.get("inverter_model")));
					LogLevel.DEBUG(5, new Throwable(), "Inverter_Model :" + String.valueOf(ht.get("inverter_model")));
					
					// This is used to get simular products or Batteries 
					String Inverter_Capacity_tmp=String.valueOf(ht.get("inverter_capacity"));
					
					req.setAttribute("Inverter_Capacity",String.valueOf(ht.get("inverter_capacity")));
					LogLevel.DEBUG(5, new Throwable(), "Inverter_Capacity :" + String.valueOf(ht.get("inverter_capacity")));
					
					req.setAttribute("Inverter_Warranty",String.valueOf(ht.get("inverter_warranty")));
					LogLevel.DEBUG(5, new Throwable(), "Inverter_Warranty :" + String.valueOf(ht.get("inverter_warranty")));
					
					req.setAttribute("Computer",String.valueOf(ht.get("computer")));
					LogLevel.DEBUG(5, new Throwable(), "Computer :" + String.valueOf(ht.get("computer")));
					
					req.setAttribute("Fans",String.valueOf(ht.get("fans")));
					LogLevel.DEBUG(5, new Throwable(), "Fans :" + String.valueOf(ht.get("fans")));
					
					req.setAttribute("Tubelights",String.valueOf(ht.get("tubelights")));
					LogLevel.DEBUG(5, new Throwable(), "Tubelights :" + String.valueOf(ht.get("tubelights")));
					
					req.setAttribute("Television",String.valueOf(ht.get("television")));
					LogLevel.DEBUG(5, new Throwable(), "Television :" + String.valueOf(ht.get("television")));
					
					req.setAttribute("Inverter_Image",String.valueOf(ht.get("icon_url")));
					LogLevel.DEBUG(5, new Throwable(), "Inverter_Image :" + String.valueOf(ht.get("icon_url")));
					
					req.setAttribute("Inverter_Description",String.valueOf(ht.get("description")));
					LogLevel.DEBUG(5, new Throwable(), "Inverter_Description :" + String.valueOf(ht.get("description")));
					
					req.setAttribute("Inverter_Actual_Price",String.valueOf(ht.get("inverter_actual_price")));
					LogLevel.DEBUG(5, new Throwable(), "Inverter_Actual_Price :" + String.valueOf(ht.get("inverter_actual_price")));
					
					req.setAttribute("Inverter_Discount_Price",String.valueOf(ht.get("inverter_discount_price")));
					LogLevel.DEBUG(5, new Throwable(), "Inverter_Discount_Price :" + String.valueOf(ht.get("inverter_discount_price")));
					
					req.setAttribute("Inverter_ERP_Price",String.valueOf(ht.get("inverter_eretailer_price")));
					LogLevel.DEBUG(5, new Throwable(), "Inverter_ERP_Price :" + String.valueOf(ht.get("inverter_eretailer_price")));
					
					req.setAttribute("Inverter_Stock",String.valueOf(ht.get("model_stock")));
					LogLevel.DEBUG(5, new Throwable(), "Inverter_Stock :" + String.valueOf(ht.get("model_stock")));
					
					req.setAttribute("State",State);
					LogLevel.DEBUG(5, new Throwable(), "State :" + State);
					
					req.setAttribute("City",City);
					LogLevel.DEBUG(5, new Throwable(), "City :" + City);
										
					req.setAttribute("Area",area);
					LogLevel.DEBUG(5, new Throwable(), "Area :" + area);
					
					req.setAttribute("Pincode",strpincode);
					LogLevel.DEBUG(5, new Throwable(), "Pincode :" + strpincode);
					
					// SQL to get Similar Batteries baced on AH
					
					String Get_Similar_Inverter_SQL = "select DISTINCT a.inverter_brand,a.inverter_model,a.inverter_capacity,a.inverter_warranty,a.icon_url,b.inverter_actual_price,b.inverter_discount_price from inverter_details a, inverter_price_details b  where b.state='"+State+"' and b.city='"+City+"' and a.inverter_brand=b.inverter_brand and a.inverter_model=b.inverter_model ORDER BY RAND() LIMIT 10";
					LogLevel.DEBUG(5, new Throwable(), "Get_Similar_Inverter_SQL :" + Get_Similar_Inverter_SQL);
					
					Vector Vector_Get_Similar_Inverter=qm.executeQuery(Get_Similar_Inverter_SQL);
					LogLevel.DEBUG(5,new Throwable(),"Vector_Get_Similar_Inverter:"+Vector_Get_Similar_Inverter);
			
					req.setAttribute("Vector_Get_Similar_Inverter",Vector_Get_Similar_Inverter);

					//return strRes;
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
* This method is used to calculate the negotiate price given by support
\* *************************************************************************************************************/
public String calculatepercentage(HttpServletRequest req , HttpServletResponse res,HttpSession session) 
{ 	
	String invertermodel = (req.getParameter("invertermodel")!=null)?(req.getParameter("invertermodel")):"";
	String inverterbrand = (req.getParameter("inverterbrand")!=null)?(req.getParameter("inverterbrand")):"";
	String invertercapacity = (req.getParameter("invertercapacity")!=null)?(req.getParameter("invertercapacity")):"";
	String actualprice = (req.getParameter("actualprice")!=null)?(req.getParameter("actualprice")):"";
	String consumerprice = (req.getParameter("consumerprice")!=null)?(req.getParameter("consumerprice")):"";
	String discountprice = (req.getParameter("discountprice")!=null)?(req.getParameter("discountprice")):"";
 	 
	
	String strRes=""; 
		try
		{
			ServletOutputStream out=res.getOutputStream();
			String strSqlQry = "select CAST(round("+consumerprice+"-("+consumerprice+"*"+discountprice+")) AS SIGNED) as discountprice";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
			
			Vector DiscountPriceVector=qm.executeQuery(strSqlQry);
			LogLevel.DEBUG(5,new Throwable(),"DiscountPriceVector :"+DiscountPriceVector);

			if(DiscountPriceVector==null || DiscountPriceVector.size() == 0)
			{
				out.println("<p align='center' valign='top' class='insidecontent'><table valign='top' width='500' cellspacing='0' border='1' align='center' bgcolor='#FFFFFF' cellpadding='0'><tr><td><table valign='top' width='500' bgcolor='#FFFFFF' border='0'><tr><td class='insidecontent' align='center'>Failed to Calculate discount price!</td></tr></table></td></tr></table><a href=\"\" STYLE='TEXT-DECORATION: NONE'></p>");
			}
			else
			{
				strRes=strRes+"<table class='table'>";
				
				for(int j=0; j<DiscountPriceVector.size();j++)
				{
					Hashtable ht1=(Hashtable)DiscountPriceVector.get(j);
				String strdiscountprices=String.valueOf(ht1.get("discountprice"));
				
				int discountprices = Integer.parseInt(strdiscountprices);		
				
 					 
 					strRes=strRes+"<tr><th>Negotiated&nbsp;Price</th><th>: "+discountprices+"</th></tr></table>"; 
  					strRes=strRes+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type='button' class='btn bg-indigo waves-effect' onclick=\"javascript:Invertercosumerdetails('"+inverterbrand+"','"+invertermodel+"','"+actualprice+"','"+discountprices+"');\"><i class='material-icons'>done_all</i><span>ORDER NOW</span></button>"; 
				} 
			}
			out.println(strRes);

		}
		catch (Exception e)
		{
		LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
		
		}
		return strRes;
}
	
/* *************************************************************************************************************\
* This method is used to calculate the negotiate price given by support
\* *************************************************************************************************************/
public String insertorderdetails(HttpServletRequest req , HttpServletResponse res,HttpSession session) 
{ 	
	 	String Consumer_Mobile = (req.getParameter("mobile_number")!=null)?(req.getParameter("mobile_number")):"";
 		String Consumer_Name = (req.getParameter("consumer_name")!=null)?(req.getParameter("consumer_name")):"";
 		String Consumer_Email = (req.getParameter("consumer_emailid")!=null)?(req.getParameter("consumer_emailid")):"";
 		String Consumer_Address = (req.getParameter("consumer_address")!=null)?(req.getParameter("consumer_address")):"";
 		String Ordered_By = (req.getParameter("ordered_by")!=null)?(req.getParameter("ordered_by")):"";
  		String Payment_Mode = (req.getParameter("payment_mode")!=null)?(req.getParameter("payment_mode")):"";
 		String Quantity = (req.getParameter("quantity")!=null)?(req.getParameter("quantity")):"";
 		String Pincode = (req.getParameter("zipcode")!=null)?(req.getParameter("zipcode")):"";
 		String Area = (req.getParameter("area")!=null)?(req.getParameter("area")):"";
 		String City = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
 		String State = (req.getParameter("state")!=null)?(req.getParameter("state")):"";
 		String InverterType = (req.getParameter("invertertype")!=null)?(req.getParameter("invertertype")):"";
 		String InverterModel = (req.getParameter("invertermodel")!=null)?(req.getParameter("invertermodel")):"";
 		String InverterBrand = (req.getParameter("inverterbrand")!=null)?(req.getParameter("inverterbrand")):"";
 		String InverterCapacity = (req.getParameter("invertercapacity")!=null)?(req.getParameter("invertercapacity")):"";
 		String MRP = (req.getParameter("invertermrpprice")!=null)?(req.getParameter("invertermrpprice")):"";
 		String Discount_Price = (req.getParameter("inverterdiscountprice")!=null)?(req.getParameter("inverterdiscountprice")):"";
 		String ERP = (req.getParameter("invertererpprice")!=null)?(req.getParameter("invertererpprice")):"";
 		String Referrence_Code = (req.getParameter("invertererpprice")!=null)?(req.getParameter("referrence_code")):"";
  		String StoreId = (req.getParameter("store_id")!=null)?(req.getParameter("store_id")):"";
 		String StoreName = (req.getParameter("store_name")!=null)?(req.getParameter("store_name")):"";
		 
		String strRes=""; 
		String CustomerAddress=""; 
		
		 
		try
		{
			ServletOutputStream out=res.getOutputStream();
			
			
			
			int Price_Temp=0;
	 
			Price_Temp = Integer.parseInt(Discount_Price);
			 
			int QTY_int = Integer.parseInt(Quantity);
			int Final_Price_Int=Price_Temp*QTY_int;
						
			String Final_Price = Integer.toString(Final_Price_Int);
			
			
				String StrSqlQrydet = "select retailer_id,retailer_name,mobile_number,email_id,address1,mobile_numberother,invoice_flag from "+State+"_retailers where retailer_id='"+StoreId+"' and retailer_name='"+StoreName+"'";
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
					strRes="Session Expired or Server Down. Please regenerate your order.";
				}
				else
				{
					//######################################## Order Number Code	####################
					String New_Order_ID_SQL = "SELECT ord_id as ORDER_ID FROM inverter_order_details ORDER BY ord_id DESC LIMIT 1";
					Hashtable New_Order_ID_HT = qm.getRow(New_Order_ID_SQL);
					LogLevel.DEBUG(5, new Throwable(), "New_Order_ID_HT :" + New_Order_ID_HT);
					int Last_Order_Count;
					if(New_Order_ID_HT.isEmpty())
					{
						/*following code is for generating the random number */
						Random Generator_Order_ID = new Random();   
						Generator_Order_ID.setSeed(System.currentTimeMillis());   
						int num = Generator_Order_ID.nextInt(90) + 10; 
						if (num < 100 || num > 999)
						{   
							num = Generator_Order_ID.nextInt(90) + 10;
							if (num < 100 || num > 999)
							{   
							}   
						} 
						Last_Order_Count=num;
					}
					else
					{
						Last_Order_Count=(Integer)New_Order_ID_HT.get("ORDER_ID");
						LogLevel.DEBUG(5, new Throwable(), "Last_Order_Count :" + Last_Order_Count);
					}
				
					// #######################Increment on order ID Count
					Last_Order_Count=Last_Order_Count+1;
					
					/*following code is for generating the random number */
					Random Generator_Order_ID_Ran = new Random();   
					Generator_Order_ID_Ran.setSeed(System.currentTimeMillis());   
					int Num = Generator_Order_ID_Ran.nextInt(90) + 10;
					if (Num < 100 || Num > 999)
					{   
						Num = Generator_Order_ID_Ran.nextInt(90) + 10;
						if (Num < 100 || Num > 999)
						{   
						}   
					} 
					
					String Last_Order_Count_String = Integer.toString(Last_Order_Count);
					String Order_Number_Generate = "";
					String Order_Number_Generate1 = Integer.toString(Num);
					Order_Number_Generate ="ORDI"+Last_Order_Count_String+""+Order_Number_Generate1+"B";
					LogLevel.DEBUG(5, new Throwable(), "Order_Number_Generate :" + Order_Number_Generate);
					Order_Number_Generate=Order_Number_Generate;
					 	 
					
					
					
					if(Consumer_Address.equals(""))
					{
						CustomerAddress="";
					}
					else
					{
					    
						CustomerAddress=", Address: "+Consumer_Address+"";
					}
					
		

					String strSqlQry="";
 					String User_Address_Landmark=CustomerAddress;
					User_Address_Landmark = User_Address_Landmark.replace("'","\\'");
					LogLevel.DEBUG(5,new Throwable()," User_Address_Landmark "+User_Address_Landmark);
					 
					strSqlQry = "insert into inverter_order_details(order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_address,retailer_name,retailer_mobile_number,retailer_emailid,state,city,area,pincode,inverter_brand,inverter_model,inverter_capacity,quantity,MRP_Price,price,erp_pre_tax,payment_mode,payment_mode_type,referred_coupon_code,city_percentage,order_status,operator,confirm_by,agent_name,creation_date)values('"+Order_Number_Generate+"','"+Consumer_Name+"','"+Consumer_Mobile+"','"+Consumer_Email+"','"+Consumer_Address+"','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+State+"','"+City+"','"+Area+"','"+Pincode+"','"+InverterBrand+"','"+InverterModel+"','"+InverterCapacity+"','"+Quantity+"','"+MRP+"','"+Discount_Price+"','"+ERP+"','"+Payment_Mode+"','"+Payment_Mode+"','"+Referrence_Code+"','1.18','confirmed','"+Ordered_By+"','Storeoperator','storeoperator',now())";
							
					LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);	
					
					LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
					int reslt = qm.executeUpdate(strSqlQry);
						
					 
						//######## Send SMS for ORDER
						Order_Store_SMS Send_Order_SMS_Checkout = new Order_Store_SMS(qm);
						String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,Order_Number_Generate,"Inverter","Yes","Yes","No","confirmed");
						LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
						//######## Send SMS for ORDER
						
						//######## Send Mail for ORDER
						Order_Store_SMS Send_Order_MAIL_Checkout = new Order_Store_SMS(qm);
						String MAIL_Report=Send_Order_MAIL_Checkout.Send_Order_Mail(req,res,session,Order_Number_Generate,"Inverter","Sir","Yes","Yes","No","confirmed");
						LogLevel.DEBUG(5, new Throwable(), "MAIL_Report :" + MAIL_Report);
						//######## Send Mail for ORDER
						
					strRes="Sucessfully | "+Order_Number_Generate+"";
					 
				}
			 

		}
		catch (Exception e)
		{
		LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
		
		}
		return strRes;
 }
	
	
} //End of Class