/***********************************************************************		
	NGIT Confidential. 
	@File Name   : OperatorInverterDetails.java 
	@Description : This Servlet is used to fetch the drop down values 
	@Author	     : Sai Krishna Daddala
	@Date        : 1th April 2014
******************************************************************/ 
 
package com.ngit.servlets.operator; 
 
import com.ngit.javabean.qrymgr.QueryManager;
import com.ngit.javabean.loglevel.LogLevel;
import com.ngit.javabean.mail.*;
import com.ngit.javabean.sendsms.SendSMS;
import com.ngit.javabean.consumers.products.Order_SMS;
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

  
public class OperatorInvertersDetailsRetailer extends HttpServlet 
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
				String strRes=getinverterdetails(req,res,session,strIpAddress,strPort,strsuppmobnumber,strsuppmobnumber1,SMSFromAddress);
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
				String strRes=insertconsumerorderdetails(req,res,session,domainname,strIpAddress,strPort,SMSFromAddress,FromEmailId,strsuppemaild,baseUrl,strsuppmobnumber,strCMSSERVERIP,strPDFFilePath,strPDFRelFilePath,struserName);
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
	* This action is used to  calculatet the discount price
	\* *****************************************************************************************/		
	if(strWhatToDo.equalsIgnoreCase("calculatenegotiateprice"))
		{ 
			try
			{
				String strRes=calculatenegotiateprice(req,res,session);
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
	public String getinverterdetails(HttpServletRequest req,HttpServletResponse res,HttpSession session,String strIpAddress,String strPort,String strsuppmobnumber,String strsuppmobnumber1,String SMSFromAddress)
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

		String retailer= (req.getParameter("retailer")!=null)?(req.getParameter("retailer")):"";
		LogLevel.DEBUG(5,new Throwable(),"retailer:"+retailer);
		
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
				res.sendRedirect("/bookbattery/jsp/operator/inverter/orderinverterretailer.jsp");
				return strRes;
			}
			else
			{
				
				LogLevel.DEBUG(1, new Throwable(),"Successfully Fetched Inverter Details");
				session.setAttribute("InverterdetailsVector", InverterdetailsVector);
				//session.setAttribute("RefreshKeyword", "refresh");
				//session.setAttribute("BatteryLocalTaxVector", strcustax);
				res.sendRedirect("/bookbattery/jsp/operator/inverter/inverterdetailsretailer.jsp?invertertype="+strinvertertype+"&inverterbrand="+inverterbrand+"&invertercapacity="+invertercapacity+"&city="+strcity+"&pincity="+city+"&strarea="+area+"&strstate="+strstate+"&strpincode="+strpincode+"&sortpricess="+sortpricess+"&retailer="+retailer);
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
	
/* *************************************************************************************************************\
* This method is used to insert consumer and order details details
\* *************************************************************************************************************/
public String insertconsumerorderdetails(HttpServletRequest req , HttpServletResponse res,HttpSession session,String strdomainname,String strIpAddress,String strPort,String SMSFromAddress, String FromEmailId, String strsuppemaild, String baseUrl, String strsuppmobnumber,String strCMSSERVERIP,String strPDFFilePath,String strPDFRelFilePath,String struserName) 
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
	String verifycode ="";
	String inverterbrand = (req.getParameter("inverterbrand")!=null)?(req.getParameter("inverterbrand")):"";
	String city = (req.getParameter("city")!=null)?(req.getParameter("city")):"";
	String pincity = (req.getParameter("pincity")!=null)?(req.getParameter("pincity")):"";
	String strarea = (req.getParameter("strarea")!=null)?(req.getParameter("strarea")):"";
	String strstate3 = (req.getParameter("strstate")!=null)?(req.getParameter("strstate")):"";
	String strpincode = (req.getParameter("strpincode")!=null)?(req.getParameter("strpincode")):"";
	String invertercapacity = (req.getParameter("invertercapacity")!=null)?(req.getParameter("invertercapacity")):"";
	String retailer = (req.getParameter("retailer")!=null)?(req.getParameter("retailer")):"";
	LogLevel.DEBUG(5, new Throwable(), "retailer :" + retailer);

	String orderedby = (req.getParameter("orderedby")!=null)?(req.getParameter("orderedby")):"";
	LogLevel.DEBUG(5, new Throwable(), "orderedby :" + orderedby);
		
	String Quantity = (req.getParameter("Quantity")!=null)?(req.getParameter("Quantity")):"";
	LogLevel.DEBUG(5, new Throwable(), "Quantity :" + Quantity);
	
	String order_from_batterywale = (req.getParameter("order_from_batterywale")!=null)?(req.getParameter("order_from_batterywale")):"";
	
	LogLevel.DEBUG(5, new Throwable(), "order_from_batterywale :" + order_from_batterywale);
	
	String payment_mode = (req.getParameter("payment_mode")!=null)?(req.getParameter("payment_mode")):"";
	LogLevel.DEBUG(5, new Throwable(), "payment_mode :" + payment_mode);
	String order_agent_comments = (req.getParameter("order_agent_comments")!=null)?(req.getParameter("order_agent_comments")):"";
	LogLevel.DEBUG(5, new Throwable(), "order_agent_comments :" + order_agent_comments);	
	
	String payment_mode_type = (req.getParameter("payment_mode_type")!=null)?(req.getParameter("payment_mode_type")):"";
	LogLevel.DEBUG(5, new Throwable(), "payment_mode_type :" + payment_mode_type);	
	
	String order_coupon_code = (req.getParameter("order_coupon_code")!=null)?(req.getParameter("order_coupon_code")):"";
	LogLevel.DEBUG(5, new Throwable(), "order_coupon_code :" + order_coupon_code);

	String referred_coupon_code = (req.getParameter("referred_coupon_code")!=null)?(req.getParameter("referred_coupon_code")):"";
	LogLevel.DEBUG(5, new Throwable(), "referred_coupon_code :" + referred_coupon_code);
	
	String coupon_code_expiry_date = (req.getParameter("coupon_code_expiry_date")!=null)?(req.getParameter("coupon_code_expiry_date")):"";
	LogLevel.DEBUG(5, new Throwable(), "coupon_code_expiry_date :" + coupon_code_expiry_date);
	


	String strRes="";
	String strstate="";
	String strstate1="";
	String StrSqlQry ="";
	String Strretid="";
	String Strlocorpin="";
	String strcustax="";
	String strcusper="";
	String forwareded_order="";
	
	String User_Address_Landmark=addrs1+"\r\n Landmark : "+addrs2;
	User_Address_Landmark = User_Address_Landmark.replace("'","\\'");
	LogLevel.DEBUG(5,new Throwable()," User_Address_Landmark "+User_Address_Landmark);
	
	
	Hashtable htretailerid =new Hashtable();
	try
	{		
			ServletOutputStream out=res.getOutputStream();

		if(orderedby.equals("Operator"))
		{
			orderedby="yes";
		}
		else
		{
			orderedby=orderedby;
		}
		if(order_from_batterywale.contains("perator"))
		{
			struserName=order_from_batterywale;
			forwareded_order="Yes";
		}
		else
		{
			forwareded_order="No";
		}
		

			/*if(city.equals("0") || city.equals("") || city == "")
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
			if(Strretid.equals(null) || Strretid.equals("null") || Strretid == null || Strretid == "null" || Strretid =="")
			{
				strRes="<table border='0' width='300px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closemobdiv(greyout(false));'><img src=\"../images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='300px' height='2px' bgcolor='#FFFFFF' valign'top'><tr class='pages' bgcolor='#FFFFFF'><td align='center' bgcolor='#FFFFFF'>No Retailers Found on Selected City.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closemobdiv();' class='button4'><br></td></tr><tr height='15'></tr></table>";
			}
			else
			{*/

			String StrSqlQrystate = "select state from search_whereever_keywords where city='"+city+"'";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQrystate :" + StrSqlQrystate); 

			Hashtable htgetstate = qm.getRow(StrSqlQrystate); 
			strstate1=(String)htgetstate.get("state");

			strstate=strstate1.trim().replaceAll(" ", "_"); 
			LogLevel.DEBUG(5, new Throwable(), "strstate :" + strstate);

			String[] tempArr12=retailer.split(",");
			String Strretids=tempArr12[0];

			String StrSqlQrydet = "select retailer_id,retailer_name,mobile_number,email_id,address1,mobile_numberother,invoice_flag from "+strstate+"_retailers where retailer_id='"+Strretids+"'";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQrydet :" + StrSqlQrydet);

			Hashtable htretailerdetls = qm.getRow(StrSqlQrydet); 
			Strretid=String.valueOf(htretailerdetls.get("retailer_id"));
			LogLevel.DEBUG(5, new Throwable(), "Strretid :" + Strretid);
			String strretmobnum=String.valueOf(htretailerdetls.get("mobile_number"));
			String strretname=(String)htretailerdetls.get("retailer_name");
			String strretemail=(String)htretailerdetls.get("email_id");
			String straddress1=(String)htretailerdetls.get("address1");
			String strretothermobnum=String.valueOf(htretailerdetls.get("mobile_numberother"));
			String strinvoiceflag=String.valueOf(htretailerdetls.get("invoice_flag"));


			//######################################## Order Number Code	####################
			String New_Order_ID_SQL = "SELECT order_id as ORDER_ID FROM inverter_order_details ORDER BY order_id DESC LIMIT 1";
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
			String verificationcode = "";
			String verificationcode1 = Integer.toString(Num);
			verificationcode ="ORDI"+Last_Order_Count_String+""+verificationcode1+"B";
			LogLevel.DEBUG(5, new Throwable(), "verificationcode :" + verificationcode);
			
			/*following code is for generating the random number */
			String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789";

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
			
			Calendar cal = Calendar.getInstance();
			int currentMonth = cal.get(Calendar.MONTH) + 1;
			int currentYear = cal.get(Calendar.YEAR);

			String strPdfURL="";
			strRes="<table border='0' width='300px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href=\"javascript:navigatetobatterywale('"+emailid+"','"+strUsermobileno+"','"+pw+"','"+strusername+"','"+usercity+"','"+strstate3+"','inverter','','','"+inverterbrand+"','"+invertermodel+"','"+discountprice+"','','1','"+strretname+"','"+strretmobnum+"');\"><img src=\"/bookbattery/images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='300px' height='2px' bgcolor='#FFFFFF' valign'top'><tr bgcolor='#FFFFFF'><td align='justify' bgcolor='#FFFFFF' style='font-family:Verdana;font-size:11px;color:#000000;'>Your BookBattery Inverter Ord Ref No: <b>"+verificationcode+"</b>. <br>Inverter Model: <b>"+invertermodel+"</b>. <br>MRP: <b>"+price+"</b> Discount Price: <b>"+discountprice+"</b>. <br>"+strretname+", Mob No-"+strretmobnum+" will fullfill your order. </td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' class='button4' onclick=\"javascript:closemobdiv(greyout(false));\"><br></td></tr><tr height='15'></tr></table>";
			
			String agent_name="";

				if(struserName.equals("chatoperator"))
				{
					//Query to check mobile number in visitors_orders table
					String StrSqlQryOperatorname1 = "select agent_name from visitors_orders where mobile_number='"+strUsermobileno+"' and (NOT agent_name = '0' or agent_name = ' ' ) and YEAR(creation_date) = YEAR(NOW()) AND MONTH(creation_date) = MONTH(NOW()) AND DAY(creation_date) = DAY(NOW()) order by creation_date limit 1";
					LogLevel.DEBUG(5, new Throwable(), "StrSqlQryOperatorname1 :" + StrSqlQryOperatorname1);

					Hashtable htgetoperatorname1 = qm.getRow(StrSqlQryOperatorname1);
					LogLevel.DEBUG(5, new Throwable(), "htgetoperatorname1 :" + htgetoperatorname1);

					if(htgetoperatorname1.isEmpty())
					{
						
							//Query to check mobile number in inverter_order_details table
							String StrSqlQryOperatorname12 = "select agent_name from inverter_order_details where consumer_mobnumber='"+strUsermobileno+"' and (NOT agent_name = '0' or agent_name = ' ' ) and YEAR(creation_date) = YEAR(NOW()) AND MONTH(creation_date) = MONTH(NOW()) AND DAY(creation_date) = DAY(NOW()) order by creation_date limit 1";
							LogLevel.DEBUG(5, new Throwable(), "StrSqlQryOperatorname12 :" + StrSqlQryOperatorname12);

							Hashtable htgetoperatorname12 = qm.getRow(StrSqlQryOperatorname12);
							LogLevel.DEBUG(5, new Throwable(), "htgetoperatorname12 :" + htgetoperatorname12);

							if(htgetoperatorname12.isEmpty())
							{
								//Query to check mobile number in battery_order_details table
								String StrSqlQryOperatorname123 = "select agent_name from battery_order_details where consumer_mobnumber='"+strUsermobileno+"' and (NOT agent_name = '0' or agent_name = ' ' ) and YEAR(creation_date) = YEAR(NOW()) AND MONTH(creation_date) = MONTH(NOW()) AND DAY(creation_date) = DAY(NOW()) order by creation_date limit 1";
								LogLevel.DEBUG(5, new Throwable(), "StrSqlQryOperatorname123 :" + StrSqlQryOperatorname123);

								Hashtable htgetoperatorname123 = qm.getRow(StrSqlQryOperatorname123);
								LogLevel.DEBUG(5, new Throwable(), "htgetoperatorname123 :" + htgetoperatorname123);

								if(htgetoperatorname123.isEmpty())
								{
									
										//Query to get the operator name which has been assigned last
										String StrSqlQryOperatorname = "select agent_name from battery_order_details where (NOT agent_name = '0' or agent_name = ' ' ) order by creation_date desc limit 1";
										LogLevel.DEBUG(5, new Throwable(), "StrSqlQryOperatorname :" + StrSqlQryOperatorname);

										Hashtable htgetoperatorname = qm.getRow(StrSqlQryOperatorname); 
										agent_name=(String)htgetoperatorname.get("agent_name");
										LogLevel.DEBUG(5, new Throwable(), "agent_name :" + agent_name);

										
										if(agent_name.equals("operator1"))
										{
											agent_name="operator2";
										}
										else if(agent_name.equals("operator2"))
										{
											agent_name="operator3";
										}
										else if(agent_name.equals("operator3"))
										{
											agent_name="operator4";
										}
										else if(agent_name.equals("operator4"))
										{
											agent_name="operator1";
										}
										else
										{
											agent_name="operator1";
										}
										String strSqlQry = "insert into inverter_order_details(order_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_verify_code,retailer_name,retailer_mobile_number,retailer_emailid,state,city,inverter_brand,inverter_model,inverter_capacity,price,area,pincode,operator,creation_date,agent_name,payment_mode,order_agent_comments,payment_mode_type,forwareded_order,order_coupon_code,referred_coupon_code,coupon_code_expiry_date)values(NULL,'"+verificationcode+"','"+strusername+"','"+strUsermobileno+"','"+emailid+"','0','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+strstate3+"','"+pincity+"','"+inverterbrand+"','"+invertermodel+"','"+invertercapacity+"','"+discountprice+"','"+strarea+"','"+strpincode+"','"+orderedby+"',now(),'"+agent_name+"','"+payment_mode+"','"+order_agent_comments+"','"+payment_mode_type+"','"+forwareded_order+"','"+order_coupon_code+"','"+referred_coupon_code+"','"+coupon_code_expiry_date+"')";
										LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
										int reslt = qm.executeUpdate(strSqlQry); 
									

								}
								else
								{
									agent_name=(String)htgetoperatorname123.get("agent_name");
									LogLevel.DEBUG(5, new Throwable(), "agent_name :" + agent_name);
									String strSqlQry = "insert into inverter_order_details(order_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_verify_code,retailer_name,retailer_mobile_number,retailer_emailid,state,city,inverter_brand,inverter_model,inverter_capacity,price,area,pincode,operator,creation_date,agent_name,payment_mode,order_agent_comments,payment_mode_type,forwareded_order,order_coupon_code,referred_coupon_code,coupon_code_expiry_date)values(NULL,'"+verificationcode+"','"+strusername+"','"+strUsermobileno+"','"+emailid+"','0','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+strstate3+"','"+pincity+"','"+inverterbrand+"','"+invertermodel+"','"+invertercapacity+"','"+discountprice+"','"+strarea+"','"+strpincode+"','"+orderedby+"',now(),'"+agent_name+"','"+payment_mode+"','"+order_agent_comments+"','"+payment_mode_type+"','"+forwareded_order+"','"+order_coupon_code+"','"+referred_coupon_code+"','"+coupon_code_expiry_date+"')";
									LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
									int reslt = qm.executeUpdate(strSqlQry);
								}

							}
							else
							{
								agent_name=(String)htgetoperatorname12.get("agent_name");
								LogLevel.DEBUG(5, new Throwable(), "agent_name :" + agent_name);
								String strSqlQry = "insert into inverter_order_details(order_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_verify_code,retailer_name,retailer_mobile_number,retailer_emailid,state,city,inverter_brand,inverter_model,inverter_capacity,price,area,pincode,operator,creation_date,agent_name,payment_mode,order_agent_comments,payment_mode_type,forwareded_order,order_coupon_code,referred_coupon_code,coupon_code_expiry_date)values(NULL,'"+verificationcode+"','"+strusername+"','"+strUsermobileno+"','"+emailid+"','0','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+strstate3+"','"+pincity+"','"+inverterbrand+"','"+invertermodel+"','"+invertercapacity+"','"+discountprice+"','"+strarea+"','"+strpincode+"','"+orderedby+"',now(),'"+agent_name+"','"+payment_mode+"','"+order_agent_comments+"','"+payment_mode_type+"','"+forwareded_order+"','"+order_coupon_code+"','"+referred_coupon_code+"','"+coupon_code_expiry_date+"')";
								LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
								int reslt = qm.executeUpdate(strSqlQry);

							}
						
					}
					else
					{
							agent_name=(String)htgetoperatorname1.get("agent_name");
							LogLevel.DEBUG(5, new Throwable(), "agent_name :" + agent_name);
							String strSqlQry = "insert into inverter_order_details(order_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_verify_code,retailer_name,retailer_mobile_number,retailer_emailid,state,city,inverter_brand,inverter_model,inverter_capacity,price,area,pincode,operator,creation_date,agent_name,payment_mode,order_agent_comments,payment_mode_type,forwareded_order,order_coupon_code,referred_coupon_code,coupon_code_expiry_date)values(NULL,'"+verificationcode+"','"+strusername+"','"+strUsermobileno+"','"+emailid+"','0','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+strstate3+"','"+pincity+"','"+inverterbrand+"','"+invertermodel+"','"+invertercapacity+"','"+discountprice+"','"+strarea+"','"+strpincode+"','"+orderedby+"',now(),'"+agent_name+"','"+payment_mode+"','"+order_agent_comments+"','"+payment_mode_type+"','"+forwareded_order+"','"+order_coupon_code+"','"+referred_coupon_code+"','"+coupon_code_expiry_date+"')";
							LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
							int reslt = qm.executeUpdate(strSqlQry);
				   }
				}
				else
				{

					String strSqlQry = "insert into inverter_order_details(order_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_verify_code,retailer_name,retailer_mobile_number,retailer_emailid,state,city,inverter_brand,inverter_model,inverter_capacity,price,area,pincode,operator,creation_date,agent_name,payment_mode,order_agent_comments,payment_mode_type,forwareded_order,order_coupon_code,referred_coupon_code,coupon_code_expiry_date)values(NULL,'"+verificationcode+"','"+strusername+"','"+strUsermobileno+"','"+emailid+"','0','"+strretname+"','"+strretmobnum+"','"+strretemail+"','"+strstate3+"','"+pincity+"','"+inverterbrand+"','"+invertermodel+"','"+invertercapacity+"','"+discountprice+"','"+strarea+"','"+strpincode+"','"+orderedby+"',now(),'"+struserName+"','"+payment_mode+"','"+order_agent_comments+"','"+payment_mode_type+"','"+forwareded_order+"','"+order_coupon_code+"','"+referred_coupon_code+"','"+coupon_code_expiry_date+"')";
					LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
					int reslt = qm.executeUpdate(strSqlQry);
				}
			String strSqlQryleads ="insert into leads(lead_id,retailer_name,retailer_id,deal_id,email_id,vendor_id,product_id,product_name,mobile_number,vendor_name,promotion_code,price,subcategory_id,category_id,creation_date,created_by,updated_date,updated_by)values(NULL,'"+strretname+"','"+Strretid+"','0','"+emailid+"','22','0','undefined','"+strUsermobileno+"','"+inverterbrand+"','0','0','0','0',now(),'ngit',now(),'ngit')"; 
			LogLevel.DEBUG(5,new Throwable()," :strSqlQryleads "+strSqlQryleads);
			int resltleads = qm.executeUpdate(strSqlQryleads);
			

	
			String Get_Product_Price_SQL ="select DISTINCT a.inverter_actual_price, a.inverter_discount_price, a.inverter_capacity, a.inverter_eretailer_price, b.city_percentage from inverter_price_details a, percentage b where a.state='"+strstate3+"' and a.city='"+pincity+"' and a.inverter_brand='"+inverterbrand+"' and a.inverter_model='"+invertermodel+"' and a.city=b.city "; 
			
			LogLevel.DEBUG(5, new Throwable(), "Get_Product_Price_SQL :" + Get_Product_Price_SQL);

			Hashtable Get_Product_Price_SQL_HT = qm.getRow(Get_Product_Price_SQL);
			LogLevel.DEBUG(5, new Throwable(), "Get_Product_Price_SQL_HT :" + Get_Product_Price_SQL_HT);

			if(Get_Product_Price_SQL_HT.isEmpty())
			{
				strRes="Session Expired or Server Down. Please regenerate your order.";
				return strRes;
			}
			else
			{
				int ProductActualPrice_int=(Integer)Get_Product_Price_SQL_HT.get("inverter_actual_price");
				LogLevel.DEBUG(5, new Throwable(), "ProductActualPrice_int :" + ProductActualPrice_int);
				
				int ProductDiscountPrice_int=(Integer)Get_Product_Price_SQL_HT.get("inverter_discount_price");
				LogLevel.DEBUG(5, new Throwable(), "ProductDiscountPrice_int :" + ProductDiscountPrice_int);
				
				int ProductERP_Pre_TAX_int=(Integer)Get_Product_Price_SQL_HT.get("inverter_eretailer_price");
				LogLevel.DEBUG(5, new Throwable(), "ProductERP_Pre_TAX_int :" + ProductERP_Pre_TAX_int);
				
				String ProductActualPrice = Integer.toString(ProductActualPrice_int);
				String ProductDiscountPrice = Integer.toString(ProductDiscountPrice_int);
				String ERP_Pre_TAX = Integer.toString(ProductERP_Pre_TAX_int);
				
				String ProductCapacity=(String)Get_Product_Price_SQL_HT.get("inverter_capacity");
				LogLevel.DEBUG(5, new Throwable(), "ProductCapacity :" + ProductCapacity);
				
				String City_Percentage=(String)Get_Product_Price_SQL_HT.get("city_percentage");
				LogLevel.DEBUG(5, new Throwable(), "City_Percentage :" + City_Percentage);

				String strSqlQry_Update = "UPDATE inverter_order_details SET erp_pre_tax = '"+ERP_Pre_TAX+"', MRP_Price = '"+ProductActualPrice+"', city_percentage = '"+City_Percentage+"', consumer_address='"+User_Address_Landmark+"',payment_mode='"+payment_mode+"', payment_mode_type='"+payment_mode+"', quantity='"+Quantity+"'  WHERE  order_number='"+verificationcode+"' ";
				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry_Update);
				
				int reslt_Update = qm.executeUpdate(strSqlQry_Update);
			}
			

			String StrSqlQryuser = "select email_id from batterywale_user_profile where email_id='"+emailid+"'";
			LogLevel.DEBUG(5, new Throwable(), "StrSqlQryuser :" + StrSqlQryuser);

			Hashtable htruser = qm.getRow(StrSqlQryuser); 
			String Stremailid=String.valueOf(htruser.get("email_id"));
			LogLevel.DEBUG(5, new Throwable(), "Stremailid :" + Stremailid);

			if(Stremailid.equals(null) || Stremailid.equals("null") || Stremailid == null || Stremailid == "null" || Stremailid =="")
			{
				addrs1 = addrs1.replace("'","\\'");
				addrs2 = addrs2.replace("'","\\'");

				String strSqlQryuserprof = "insert into batterywale_user_profile(user_id,email_id,mobile_number,password,name,address,address1,zipcode,city,state,mobile_verify_code,creation_date,created_by) values(NULL,'"+emailid+"','"+strUsermobileno+"','"+pw+"','"+strusername+"','"+addrs1+"','"+addrs2+"','"+userzipcode+"','"+city+"','"+strstate3+"','0',now(),'ngit')";

				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQryuserprof);
				int reslt1 = qm.executeUpdate(strSqlQryuserprof);
			}
			else
			{
				String strSqlQryupdatepassword = "update batterywale_user_profile set password='"+pw+"' where email_id='"+emailid+"'";
				LogLevel.DEBUG(5,new Throwable(),"strSqlQryupdatepassword : "+strSqlQryupdatepassword);
				int reslt12 = qm.executeUpdate(strSqlQryupdatepassword);
			}

			
				if(forwareded_order.equals("Yes"))
				{
					//######## Send SMS for Forwarded ORDER
					Order_SMS Send_Order_SMS_Checkout = new Order_SMS(qm);
					String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,verificationcode,"Inverter","No","No","No");
					LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
					//######## Send SMS for ORDER
				}
				else
				{
					//######## Send SMS for new ORDER retailer
					Order_SMS Send_Order_SMS_Checkout = new Order_SMS(qm);
					String SMS_Report=Send_Order_SMS_Checkout.Send_Order_SMS(req,res,session,verificationcode,"Inverter","Yes","No","No");
					LogLevel.DEBUG(5, new Throwable(), "SMS_Report :" + SMS_Report);
					//######## Send SMS for ORDER					
				}
						
				//######## Send Mail for ORDER
				Order_SMS Send_Order_MAIL_Checkout = new Order_SMS(qm);
				String MAIL_Report=Send_Order_MAIL_Checkout.Send_Order_Mail(req,res,session,verificationcode,"Inverter","Sir","No","No");
				LogLevel.DEBUG(5, new Throwable(), "MAIL_Report :" + MAIL_Report);
				//######## Send Mail for ORDER		
				
	}
	catch (Exception e)
	{
	LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
	strRes=strRes;
	}
	return strRes;
}
/* *************************************************************************************************************\
* This method is used to calculate the negotiate price given by support
\* *************************************************************************************************************/
public String calculatenegotiateprice(HttpServletRequest req , HttpServletResponse res,HttpSession session) 
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
			strRes=strRes+"<table border='0' align='center' bgcolor='#FFFFFF' width='200'><tr>";

			for(int j=0; j<DiscountPriceVector.size();j++)
			{
				Hashtable ht1=(Hashtable)DiscountPriceVector.get(j);
				String strdiscountprices=String.valueOf(ht1.get("discountprice"));
				
				int discountprices = Integer.parseInt(strdiscountprices);		
			
				strRes=strRes+"<table width='95%' border='0' class='tablebat1'  align='center'>";
				strRes=strRes+"<tr><td class='tablebat1'  width='55%'  align='left'><b>Negotiated&nbsp;Price</b></td><td><b>:</b></td>";
				strRes=strRes+"<td class='tablebat1' width='45%' align='left'><b>"+discountprices+"</b></td></tr>";
				strRes=strRes+"<tr><td height='6'></td><td height='5'></td></tr>";				
				strRes=strRes+"<tr><td width='100%' align='right' style='font-family:Verdana;font-size:9px;color:#cccccc;	text-decoration:none;padding:1px 1px;'><input type='button' value='Order&nbsp;it&nbsp;Now' class='buttonindex' onclick=\"javascript:askcosumerdetails('"+inverterbrand+"','"+invertermodel+"','"+actualprice+"','"+discountprices+"');\"></td></tr>";
				strRes=strRes+"</table>";
				strRes=strRes+"</table>";
		
			} 
		}

	}
	catch (Exception e)
	{
	LogLevel.DEBUG(5, e, "Exception while inserting post:" + e);
	
	}
	return strRes;
}
}// end of class
