/***********************************************************************		
	NGIT Confidential. 
	@File Name   : CancelOrderDetails.java
	@Description : This Servlet is used to allow the operator to Login
	@Date        : 12th November 2019
******************************************************************/ 
package com.ngit.servlets.admin.batterywalestore; 
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
import java.sql.*;
import java.io.File;

/*
 * @author Prasanna Kumari
 */
/* This class is to Admin Login. This class initializes the necessary connection pools and perform the transactions on the pools. */
public class CancelOrderDetails extends HttpServlet 
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
		String struserName=(String)session.getAttribute("sesStoreOperatorName"); 
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
		
		/*This methos is used to validate adminlogin details*/
		if(strWhatToDo.equalsIgnoreCase("cancelorderdetails"))
		{
			
			try
			{
				String strRes=cancelorderdetails(req,res,session);
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
	   /* ****************************************************************************************\
		* This action is used to get new order ref number.
		\* *****************************************************************************************/		
	  if(strWhatToDo.equalsIgnoreCase("updatestatus"))
		{ 
		try
		{
			String strRes=updatestatus(req,res,session);
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
		 
	}//End of dopost
	/* **************************************************************************************************************************************\
	* This method is to fetch battery details details.
	* @strSqlQry : carries the query to select battery details from battery_details table.
	* @strSqlQry1 : Query has multiple pages to select battery details from battery_details table.
	\* **************************************************************************************************************************************/
	public String cancelorderdetails(HttpServletRequest req,HttpServletResponse res,HttpSession session)
	{
		String strRes="";
		String TableName="";
		String OrderDetails_SQL="";
		try
		{	
			String strordernumber= (req.getParameter("ordernumber")!=null)?(req.getParameter("ordernumber")):"";
			LogLevel.DEBUG(5,new Throwable(),"strordernumber:"+strordernumber );
			
			//String lastChar = strordernumber.substring(strordernumber.length() - 1);
			String splitChar = strordernumber.substring(0, 4);
			String lastChar = splitChar.substring(splitChar.length() - 1);
			LogLevel.DEBUG(5,new Throwable(),"lastChar:"+lastChar );
		
			if(lastChar.equals("S")) {
				
				TableName = "service_order_details";
				OrderDetails_SQL = "select ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_address1,retailer_name,retailer_mobilnumber,retailer_emailid,state,city,area,pincode,product_type,veh_name,veh_model,product_capacity,quantity,services_type,service_price_discount,service_price_mrp,payment_mode,payment_mode_type,order_status,order_reasons,order_agent_comments,creation_date from "+TableName+" where order_number='"+strordernumber+"'";
				
			} else if(lastChar.equals("I")) {
				
				TableName = "inverter_order_details";
				
				OrderDetails_SQL = "Select order_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_address,retailer_name,retailer_mobile_number,retailer_emailid,state,city,area,pincode,inverter_brand,inverter_model,inverter_capacity,quantity,MRP_Price,price,erp_pre_tax,payment_mode_type,payment_mode,order_status,order_reasons,order_agent_comments,creation_date from "+TableName+" where order_number='"+strordernumber+"'";
				
			} else if(lastChar.equals("B")) {
				
				TableName = "battery_order_details";
				
				OrderDetails_SQL = "Select ord_id,order_number,consumer_name,consumer_mobnumber,consumer_emailid,consumer_address,retailer_name,retailer_mobilnumber,retailer_emailis,state,city,area,pincode,bat_type,battery_brand,battery_model,battery_capacity,quantity,veh_name,veh_model,MRP_Price,price,witholdbatprice,erp_pre_tax,order_type,payment_mode_type,payment_mode,order_status,order_reasons,order_agent_comments,creation_date from "+TableName+" where order_number='"+strordernumber+"'";
				
			} else {
				
				
			}
			
			LogLevel.DEBUG(5,new Throwable(),"TableName:"+TableName );
			LogLevel.DEBUG(5,new Throwable(),"OrderDetails_SQL:"+OrderDetails_SQL );
			
			Vector orddetails=qm.executeQuery(OrderDetails_SQL);
			LogLevel.DEBUG(5,new Throwable(),"orddetails:"+orddetails);
	
			if(orddetails==null || orddetails.size() == 0)
			{
				strRes=strRes+"<table class='table table-bordered' style='color:red'>Invalid Ord Ref Number</table>";
			}
			else
			{
 						
				strRes=strRes+"<div style='overflow-x:auto;'>";
				strRes=strRes+"<table class='table table-bordered'>";
				
			  	for(int j=0; j<orddetails.size();j++)
				{
						Hashtable ht3=(Hashtable)orddetails.get(j);
						String OID=String.valueOf(ht3.get("ord_id"));
						String ONM=String.valueOf(ht3.get("order_number"));
						String CN=String.valueOf(ht3.get("consumer_name"));
						String CMN=String.valueOf(ht3.get("consumer_mobnumber"));
						String CEM=String.valueOf(ht3.get("consumer_emailid"));
 						String City=String.valueOf(ht3.get("city"));
 						String Area=String.valueOf(ht3.get("area"));
 						String Pincode=String.valueOf(ht3.get("pincode"));
						String OT=String.valueOf(ht3.get("order_type"));
						String OS=String.valueOf(ht3.get("order_status"));
						String OR=String.valueOf(ht3.get("order_reasons"));
						String OC=String.valueOf(ht3.get("order_agent_comments"));
						String PO=String.valueOf(ht3.get("payment_mode"));
						String DT=String.valueOf(ht3.get("creation_date")); 
						
						if(TableName.equals("service_order_details")){
							
							String BTY=String.valueOf(ht3.get("product_type"));
							String BC=String.valueOf(ht3.get("product_capacity"));
							String QTY=String.valueOf(ht3.get("quantity"));
							String VN=String.valueOf(ht3.get("veh_name"));
							String VM=String.valueOf(ht3.get("veh_model"));
							String Price=String.valueOf(ht3.get("service_price_mrp"));
							String DP=String.valueOf(ht3.get("service_price_discount"));
							String PMI=String.valueOf(ht3.get("payment_mode_type"));
							String CA=String.valueOf(ht3.get("consumer_address1"));
						
						if(BTY.equals("Inverter Batteries")) {
							
							strRes=strRes+"<tr><td><strong>Order Number</strong></td><td>"+ONM+"</td><td><strong>Name</strong></td><td>"+CN+"</td><td><strong>Mobile Number</strong></td><td>"+CMN+"</td></tr><tr><td><strong>Email Id</strong></td><td>"+CEM+"</td><td><strong>Address</strong></td><td>"+CA+"</td><td><strong>Location</strong></td><td>"+Area+"</td></tr><tr><td><strong>Pincode</strong></td><td>"+Pincode+"</td><td><strong>Service Type</strong></td><td>Battery Health Check UP</td><td><strong>Service MRP</strong></td><td>"+Price+"</td></tr><tr><td><strong>Battery Type</strong></td><td>"+BTY+"</td><td><strong>Battery Quantity</strong></td><td>"+QTY+"</td><td><strong>Battery Capacity</strong></td><td>"+BC+"</td></tr><tr><td><strong>Discount Price</strong></td><td>"+DP+"</td><td><strong>Ordered Date</strong></td><td>"+DT+"</td><td><strong>Order Status</strong></td><td><span class='label bg-indigo'>"+OS+" "+OR+"</span></td></tr><tr><td><strong>Payment Details</strong></td><td>"+PO+"</td><td><strong>Agent Comments</strong></td><td>"+OC+"</td><td><strong>Update Status</strong></td><td><strong><select class='form-control show-tick yes' id='cancel_status' name='cancel_status' onChange=\"javascript:UpdateOrderStatus('"+ONM+"','"+OS+"');\"><option value=''>Select Status</option><option value='cancelled-offbushrs'>Cancelled-OffBusHrs</option><option value='cancelled-customer-denied'>Cancelled-Customer-Denied</option><option value='cancelled-notresponded'>Cancelled-NotResponded</option><option value='cancelled-modeloutofstock'>Cancelled-ModelOutofStock</option><option value='cancelled-customer'>Cancelled-Customer</option><option value='cancelled-regenerated'>Cancelled-Regenerated</option></select></td></tr>";
						
						
						} else {
							
								strRes=strRes+"<tr><td><strong>Order Number</strong></td><td>"+ONM+"</td><td><strong>Name</strong></td><td>"+CN+"</td><td><strong>Mobile Number</strong></td><td>"+CMN+"</td></tr><tr><td><strong>Email Id</strong></td><td>"+CEM+"</td><td><strong>Address</strong></td><td>"+CA+"</td><td><strong>Location</strong></td><td>"+Area+"</td></tr><tr><td><strong>Pincode</strong></td><td>"+Pincode+"</td><td><strong>Service Type</strong></td><td>Battery Health Check UP</td><td><strong>Service MRP</strong></td><td>"+Price+"</td></tr><tr><td><strong>Battery Type</strong></td><td>"+BTY+"</td><td><strong>Vehicle Make</strong></td><td>"+VN+"</td><td><strong>Vehicle Model</strong></td><td>"+VM+"</td></tr><tr><td><strong>Discount Price</strong></td><td>"+DP+"</td><td><strong>Ordered Date</strong></td><td>"+DT+"</td><td><strong>Order Status</strong></td><td><span class='label bg-indigo'>"+OS+" "+OR+"</span></td></tr><tr><td><strong>Payment Details</strong></td><td>"+PO+"</td><td><strong>Agent Comments</strong></td><td>"+OC+"</td><td><strong>Update Status</strong></td><td><strong><select class='form-control show-tick yes' id='cancel_status' name='cancel_status' onChange=\"javascript:UpdateOrderStatus('"+ONM+"','"+OS+"');\"><option value=''>Select Status</option><option value='cancelled-offbushrs'>Cancelled-OffBusHrs</option><option value='cancelled-customer-denied'>Cancelled-Customer-Denied</option><option value='cancelled-notresponded'>Cancelled-NotResponded</option><option value='cancelled-modeloutofstock'>Cancelled-ModelOutofStock</option><option value='cancelled-customer'>Cancelled-Customer</option><option value='cancelled-regenerated'>Cancelled-Regenerated</option></select></td></tr>";
						
						}
						
						} else if(TableName.equals("inverter_order_details")){
							
							String ITY=String.valueOf(ht3.get("inverter_type"));
							String IB=String.valueOf(ht3.get("inverter_brand"));
							String IM=String.valueOf(ht3.get("inverter_model"));
							String IC=String.valueOf(ht3.get("inverter_capacity"));
							String QTY=String.valueOf(ht3.get("quantity"));
							String MRP=String.valueOf(ht3.get("MRP_Price"));
							String Price=String.valueOf(ht3.get("price"));
							String ERP=String.valueOf(ht3.get("erp_pre_tax"));
							String PBM=String.valueOf(ht3.get("payment_mode_type"));
							String CA=String.valueOf(ht3.get("consumer_address"));
							strRes=strRes+"<tr><td><strong>Order Number</strong></td><td>"+ONM+"</td><td><strong>Name</strong></td><td>"+CN+"</td><td><strong>Mobile Number</strong></td><td>"+CMN+"</td></tr><tr><td><strong>Email Id</strong></td><td>"+CEM+"</td><td><strong>Address</strong></td><td>"+CA+"</td><td><strong>Location</strong></td><td>"+Area+"</td></tr><tr><td><strong>Pincode</strong></td><td>"+Pincode+"</td><td><strong>Inverter Brand</strong></td><td>Inverter Model</td><td><strong>MRP</strong></td><td>"+MRP+"</td></tr><tr><td><strong>Discount Price</strong></td><td>"+Price+"</td><td><strong>Ordered Date</strong></td><td>"+DT+"</td><td><strong>Order Status</strong></td><td><span class='label bg-indigo'>"+OS+" "+OR+"</span></td></tr><tr><td><strong>Payment Details</strong></td><td>"+PO+"</td><td><strong>Agent Comments</strong></td><td>"+OC+"</td><td><strong>Update Status</strong></td><td><strong><select class='form-control show-tick yes' id='cancel_status' name='cancel_status' onChange=\"javascript:UpdateOrderStatus('"+ONM+"','"+OS+"');\"><option value=''>Select Status</option><option value='cancelled-offbushrs'>Cancelled-OffBusHrs</option><option value='cancelled-customer-denied'>Cancelled-Customer-Denied</option><option value='cancelled-notresponded'>Cancelled-NotResponded</option><option value='cancelled-modeloutofstock'>Cancelled-ModelOutofStock</option><option value='cancelled-customer'>Cancelled-Customer</option><option value='cancelled-regenerated'>Cancelled-Regenerated</option></select></td></tr>";
						
						} else if(TableName.equals("battery_order_details")){
							
							String BTY=String.valueOf(ht3.get("bat_type"));
							String BB=String.valueOf(ht3.get("battery_brand"));
							String BM=String.valueOf(ht3.get("battery_model"));
							String BC=String.valueOf(ht3.get("battery_capacity"));
							String QTY=String.valueOf(ht3.get("quantity"));
							String VN=String.valueOf(ht3.get("veh_name"));
							String VM=String.valueOf(ht3.get("veh_model"));
							String MRP=String.valueOf(ht3.get("MRP_Price"));
							String Price=String.valueOf(ht3.get("price"));
							String OBRP=String.valueOf(ht3.get("witholdbatprice"));
							String ERP=String.valueOf(ht3.get("erp_pre_tax"));
							String PBM=String.valueOf(ht3.get("payment_mode_type"));
							String CA=String.valueOf(ht3.get("consumer_address"));
							
							if(BTY.equals("Inverter Batteries")) {
							
							strRes=strRes+"<tr><td><strong>Order Number</strong></td><td>"+ONM+"</td><td><strong>Name</strong></td><td>"+CN+"</td><td><strong>Mobile Number</strong></td><td>"+CMN+"</td></tr><tr><td><strong>Email Id</strong></td><td>"+CEM+"</td><td><strong>Address</strong></td><td>"+CA+"</td><td><strong>Location</strong></td><td>"+Area+"</td></tr><tr><td><strong>Pincode</strong></td><td>"+Pincode+"</td><td><strong>Regular Price</strong></td><td>"+MRP+"</td><td><strong>Price</strong></td><td>"+Price+"</td></tr><tr><td><strong>OBRP</strong></td><td>"+OBRP+"</td><td><strong>Battery Quantity</strong></td><td>"+QTY+"</td><td><strong>Battery Capacity</strong></td><td>"+BC+"</td></tr><tr><td><strong>Ordered Date</strong></td><td>"+DT+"</td><td><strong>Order Status</strong></td><td><span class='label bg-indigo'>"+OS+" "+OR+"</span></td></tr><tr><td><strong>Payment Details</strong></td><td>"+PO+"</td><td><strong>Agent Comments</strong></td><td>"+OC+"</td><td><strong>Update Status</strong></td><td><strong><select class='form-control show-tick yes' id='cancel_status' name='cancel_status' onChange=\"javascript:UpdateOrderStatus('"+ONM+"','"+OS+"');\"><option value=''>Select Status</option><option value='cancelled-offbushrs'>Cancelled-OffBusHrs</option><option value='cancelled-customer-denied'>Cancelled-Customer-Denied</option><option value='cancelled-notresponded'>Cancelled-NotResponded</option><option value='cancelled-modeloutofstock'>Cancelled-ModelOutofStock</option><option value='cancelled-customer'>Cancelled-Customer</option><option value='cancelled-regenerated'>Cancelled-Regenerated</option></select></td></tr>";
						
						
						} else {
							
								strRes=strRes+"<tr><td><strong>Order Number</strong></td><td>"+ONM+"</td><td><strong>Name</strong></td><td>"+CN+"</td><td><strong>Mobile Number</strong></td><td>"+CMN+"</td></tr><tr><td><strong>Email Id</strong></td><td>"+CEM+"</td><td><strong>Address</strong></td><td>"+CA+"</td><td><strong>Location</strong></td><td>"+Area+"</td></tr><tr><td><strong>Pincode</strong></td><td>"+Pincode+"</td><td><strong>Regular Price</strong></td><td>"+MRP+"</td><td><strong>Price</strong></td><td>"+Price+"</td></tr><tr><td><strong>OBRP</strong></td><td>"+OBRP+"</td><td><strong>Vehicle Make</strong></td><td>"+VN+"</td><td><strong>Vehicle Model</strong></td><td>"+VM+"</td></tr><tr><td><strong>Battery Brand</strong></td><td>"+BB+"</td><td><strong>Ordered Date</strong></td><td>"+DT+"</td><td><strong>Order Status</strong></td><td><span class='label bg-indigo'>"+OS+" "+OR+"</span></td></tr><tr><td><strong>Payment Details</strong></td><td>"+PO+"</td><td><strong>Agent Comments</strong></td><td>"+OC+"</td><td><strong>Update Status</strong></td><td><strong><select class='form-control show-tick yes' id='cancel_status' name='cancel_status' onChange=\"javascript:UpdateOrderStatus('"+ONM+"','"+OS+"');\"><option value=''>Select Status</option><option value='cancelled-offbushrs'>Cancelled-OffBusHrs</option><option value='cancelled-customer-denied'>Cancelled-Customer-Denied</option><option value='cancelled-notresponded'>Cancelled-NotResponded</option><option value='cancelled-modeloutofstock'>Cancelled-ModelOutofStock</option><option value='cancelled-customer'>Cancelled-Customer</option><option value='cancelled-regenerated'>Cancelled-Regenerated</option></select></td></tr>";
						
						}
						}  
						
						
					int Jcnt=j+1;
					 
					strRes=strRes+"</table>";
					strRes=strRes+"</div>";
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
	
	/* **************************************************************************************************************************************\
	* This method is to get order ref number.
	* @strSqlQry : carries the query to fetch the order ref number details in service_order_details table.
	\* **************************************************************************************************************************************/
	public String updatestatus(HttpServletRequest req , HttpServletResponse res,HttpSession session)
	{
		String strorderno = (req.getParameter("orderno")!=null)?(req.getParameter("orderno")):"";
		LogLevel.DEBUG(5, new Throwable(), "strorderno :" + strorderno);

		String strordstatus = (req.getParameter("ordstatus")!=null)?(req.getParameter("ordstatus")):"";
		LogLevel.DEBUG(5, new Throwable(), "strordstatus :" + strordstatus);

		String strcancelstatus = (req.getParameter("cancel_status")!=null)?(req.getParameter("cancel_status")):"";
		LogLevel.DEBUG(5, new Throwable(), "strcancelstatus :" + strcancelstatus);

		String strremarks = (req.getParameter("remarks")!=null)?(req.getParameter("remarks")):"";
		LogLevel.DEBUG(5, new Throwable(), "strremarks :" + strremarks);
		
		String TableName="";
		String strRes="";
		String strSqlQry="";
		
		String splitChar = strorderno.substring(0, 4);
		String lastChar = splitChar.substring(splitChar.length() - 1);
		LogLevel.DEBUG(5,new Throwable(),"lastChar:"+lastChar );
		
			if(lastChar.equals("S")) {
				
				TableName = "service_order_details";
								
			} else if(lastChar.equals("I")) {
				
				TableName = "inverter_order_details";

			} else if(lastChar.equals("B")) {
				
				TableName = "battery_order_details";
				
			} else {
				
				
			}
	
		try
		{	
			
			strSqlQry= "update "+TableName+" set order_status='Cancelled',order_reasons='"+strcancelstatus+"',order_agent_comments='"+strremarks+"',updated_date=now() where order_number='"+strorderno+"'";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
			
			int i=qm.executeUpdate(strSqlQry);
			if(i <0)
			{
				LogLevel.DEBUG(1,new Throwable(),"Failed to Update");
				session.setAttribute("priority","1");
				session.setAttribute("sesErrorMsg", "<font color='#00dd00' class='vrb10'></font> ");
				strRes = "Failed to update ordered status!";
			}
			else
			{
				LogLevel.DEBUG(1,new Throwable(),"");
				session.setAttribute("priority","1");
				session.setAttribute("sesErrorMsg", "<font color='#CC0000' class='vrb10'></font> ");
				//System.out.println(email);
				strRes = "Sucessfully";
				
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