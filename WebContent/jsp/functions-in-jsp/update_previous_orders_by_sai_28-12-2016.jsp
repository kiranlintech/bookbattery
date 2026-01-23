<%-- 
Document   		 : index.jsp
Created on 		 : Sep 15, 2016, 10:14:12 AM
Author     		 : BookBattery
Copyright Notice : BookBattery Confidential.
Document         : This jsp is used as home pages of BookBattery.
--%>

<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.io.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.*,com.ngit.javabean.loglevel.LogLevel,javax.servlet.ServletContext,java.util.Properties,java.io.FileInputStream, com.ngit.javabean.qrymgr.QueryManager"%>
<%
	Properties propsMOPConfig = new Properties();
	ServletContext context = getServletContext();
	FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
	propsMOPConfig.load(fin1); 
	fin1.close(); 
	QueryManager qm;
	qm = QueryManager.getInstance(propsMOPConfig) ;
%>
<%
	String SQL_for_old_orders = "select ord_id,order_number from battery_order_details where city_percentage='0' ";
	LogLevel.DEBUG(5, new Throwable(), "SQL_for_old_orders :" + SQL_for_old_orders);
	Vector Vector_for_old_orders=qm.executeQuery(SQL_for_old_orders);
	LogLevel.DEBUG(5,new Throwable(),"Vector_for_old_orders:"+Vector_for_old_orders);
	
	if(Vector_for_old_orders.isEmpty())
	{ 
		out.println("Battery - Failed Update");
	}
	else
	{ 
		for (int i=0; i<Vector_for_old_orders.size(); i++)
		{
			
			Hashtable HT_for_old_orders=(Hashtable)Vector_for_old_orders.get(i);
			String orderid =String.valueOf(HT_for_old_orders.get("ord_id"));
			String ordernumber =String.valueOf(HT_for_old_orders.get("order_number"));
					
					
			String ProductActualPrice="";
			String ERP_Pre_TAX="";
			String City_Percentage="";
			
			String Get_Product_Price_SQL ="select DISTINCT  a.bat_act_price,a.erp_pre_tax, b.city_percentage from batteryprice a, percentage b, battery_order_details c WHERE c.order_number='"+ordernumber+"' and c.ord_id='"+orderid+"' and a.bat_brand=c.battery_brand and a.bat_model=c.battery_model and a.city=c.city and b.city=c.city ORDER BY batprice_id DESC LIMIT 1"; 
			LogLevel.DEBUG(5, new Throwable(), "Get_Product_Price_SQL :" + Get_Product_Price_SQL);

			Hashtable Get_Product_Price_SQL_HT = qm.getRow(Get_Product_Price_SQL);
			LogLevel.DEBUG(5, new Throwable(), "Get_Product_Price_SQL_HT :" + Get_Product_Price_SQL_HT);

			if(Get_Product_Price_SQL_HT.isEmpty())
			{
				out.println("Battery - Failed to Update: "+ordernumber);
				out.println("</br>");
			}
			else
			{
				ProductActualPrice=(String)Get_Product_Price_SQL_HT.get("bat_act_price");
				LogLevel.DEBUG(5, new Throwable(), "ProductActualPrice :" + ProductActualPrice);
				
				ERP_Pre_TAX =(String)Get_Product_Price_SQL_HT.get("erp_pre_tax");
				LogLevel.DEBUG(5, new Throwable(), "ERP_Pre_TAX :" + ERP_Pre_TAX);
				
				City_Percentage=(String)Get_Product_Price_SQL_HT.get("city_percentage");
				LogLevel.DEBUG(5, new Throwable(), "City_Percentage :" + City_Percentage);
				
										
				String strSqlQry = "UPDATE battery_order_details SET erp_pre_tax = '"+ERP_Pre_TAX+"', MRP_Price = '"+ProductActualPrice+"', city_percentage = '"+City_Percentage+"'WHERE  order_number='"+ordernumber+"' and ord_id='"+orderid+"' ";
				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
				
				int reslt = qm.executeUpdate(strSqlQry);
				if(reslt <0)
				{
					out.println("Battery - Failed Update "+ordernumber);
					out.println("</br>");
				}
				else
				{
					out.println("Battery - Successfully Updated "+ordernumber);
					out.println("</br>");
				}
			}
		}
			
	}
									
%>
<%
	String SQL_for_old_orders_Inverter = "select order_id,order_number from inverter_order_details where city_percentage='0'";
	LogLevel.DEBUG(5, new Throwable(), "SQL_for_old_orders_Inverter :" + SQL_for_old_orders_Inverter);
	Vector Vector_for_old_orders_Inverter=qm.executeQuery(SQL_for_old_orders_Inverter);
	LogLevel.DEBUG(5,new Throwable(),"Vector_for_old_orders_Inverter:"+Vector_for_old_orders_Inverter);
	
	if(Vector_for_old_orders_Inverter.isEmpty())
	{ 
		out.println("Inverter - Failed Update");
	}
	else
	{ 
		for (int i=0; i<Vector_for_old_orders_Inverter.size(); i++)
		{
			
			Hashtable HT_for_old_orders=(Hashtable)Vector_for_old_orders_Inverter.get(i);
			String orderid =String.valueOf(HT_for_old_orders.get("order_id"));
			String ordernumber =String.valueOf(HT_for_old_orders.get("order_number"));
					
					
			int ProductActualPrice_2=0;
			int ERP_Pre_TAX_2=0;
			String City_Percentage="";
			
			String Get_Product_Price_SQL ="select DISTINCT a.inverter_actual_price,a.inverter_eretailer_price, b.city_percentage from inverter_price_details a, percentage b, inverter_order_details c WHERE c.order_number='"+ordernumber+"' and c.order_id='"+orderid+"' and a.inverter_brand=c.inverter_brand and a.inverter_model=c.inverter_model and a.city=c.city and b.city=c.city ORDER BY inverter_price_id DESC LIMIT 1"; 
			LogLevel.DEBUG(5, new Throwable(), "Get_Product_Price_SQL :" + Get_Product_Price_SQL);

			Hashtable Get_Product_Price_SQL_HT = qm.getRow(Get_Product_Price_SQL);
			LogLevel.DEBUG(5, new Throwable(), "Get_Product_Price_SQL_HT :" + Get_Product_Price_SQL_HT);

			if(Get_Product_Price_SQL_HT.isEmpty())
			{
				out.println("Inverter - Failed to Update: "+ordernumber);
				out.println("</br>");
			}
			else
			{
				ProductActualPrice_2=(Integer)Get_Product_Price_SQL_HT.get("inverter_actual_price");
				LogLevel.DEBUG(5, new Throwable(), "ProductActualPrice :" + ProductActualPrice_2);
				
				ERP_Pre_TAX_2 =(Integer)Get_Product_Price_SQL_HT.get("inverter_eretailer_price");
				LogLevel.DEBUG(5, new Throwable(), "ERP_Pre_TAX :" + ERP_Pre_TAX_2);
				
				String ProductActualPrice = Integer.toString(ProductActualPrice_2);
				String ERP_Pre_TAX = Integer.toString(ERP_Pre_TAX_2);
				
				City_Percentage=(String)Get_Product_Price_SQL_HT.get("city_percentage");
				LogLevel.DEBUG(5, new Throwable(), "City_Percentage :" + City_Percentage);
				
										
				String strSqlQry = "UPDATE inverter_order_details SET erp_pre_tax = '"+ERP_Pre_TAX+"', MRP_Price = '"+ProductActualPrice+"', city_percentage = '"+City_Percentage+"'WHERE  order_number='"+ordernumber+"' and order_id='"+orderid+"' ";
				
				LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
				
				int reslt = qm.executeUpdate(strSqlQry);
				if(reslt <0)
				{
					out.println("Inverter - Failed Update "+ordernumber);
					out.println("</br>");
				}
				else
				{
					out.println("Inverter - Successfully Updated "+ordernumber);
					out.println("</br>");
				}
			}
		}
			
	}
									
%>
