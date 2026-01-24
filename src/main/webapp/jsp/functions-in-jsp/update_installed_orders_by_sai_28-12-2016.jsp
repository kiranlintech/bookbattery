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
try
{
	String SQL_for_update_orders = "select battery_brand,retailer_name,ord_id,order_number,payment_mode,order_type from battery_order_details where total_commission_amount='0'  and order_status='Customer Contacted' and order_reasons='installed'";
	LogLevel.DEBUG(5, new Throwable(), "SQL_for_update_orders :" + SQL_for_update_orders);
	Vector Vector_for_update_orders=qm.executeQuery(SQL_for_update_orders);
	LogLevel.DEBUG(5,new Throwable(),"Vector_for_update_orders:"+Vector_for_update_orders);
	
	String strSqlQry="";
	String strRes="";
	
	if(Vector_for_update_orders.isEmpty())
	{ 
		out.println("Battery - No Orders to update");
	}
	else
	{ 
		for (int i=0; i<Vector_for_update_orders.size(); i++)
		{
			
			Hashtable HT_for_update_orders=(Hashtable)Vector_for_update_orders.get(i);
			String orderid =String.valueOf(HT_for_update_orders.get("ord_id"));
			String ordernumber =String.valueOf(HT_for_update_orders.get("order_number"));
			String payment_mode =String.valueOf(HT_for_update_orders.get("payment_mode"));
			String order_type =String.valueOf(HT_for_update_orders.get("order_type"));
			String battery_brand =String.valueOf(HT_for_update_orders.get("battery_brand"));
			String retailer_name =String.valueOf(HT_for_update_orders.get("retailer_name"));
			
			String Total_Commission_Amount="";
			String Final_Commission_Amount="";
			double Commission_Diction_Rate;
			String Commission_Diction_Amount="";
			String Commission_WithOut_Old_Battery="";
			String Commission_With_Old_Battery="";
			
		
			if(battery_brand.equals("Amaron"))
			{
				if(retailer_name.contains("Amaron-Pitstop"))
				{
					if(payment_mode=="Debit Card" || payment_mode.equals("Debit Card") || payment_mode.equals("debit card")){
						Commission_Diction_Rate=0.01;
					}
					else if(payment_mode=="Credit Card" || payment_mode.equals("Credit Card") || payment_mode.equals("credit card")){
						Commission_Diction_Rate=0.02;
					}
					else{
						Commission_Diction_Rate=0.00;
					}
					
					if(order_type=="New" || order_type.equals("New") || order_type.equals("new") ){
						Commission_Diction_Amount="price";
					}
					else{
						Commission_Diction_Amount="witholdbatprice";
					}
					
					
					String Total_Commission_Amount_SQL = "SELECT price,erp_pre_tax,witholdbatprice,CAST(round((price/city_percentage)-(erp_pre_tax )) AS SIGNED) as Total_Commission,CAST(round((price/city_percentage)-(erp_pre_tax ))-("+Commission_Diction_Rate+"*"+Commission_Diction_Amount+") AS SIGNED) as Final_Commission FROM battery_order_details WHERE order_number='"+ordernumber+"' and ord_id='"+orderid+"'";
					
					Hashtable Total_Commission_Amount_HT = qm.getRow(Total_Commission_Amount_SQL);
					LogLevel.DEBUG(5, new Throwable(), "Total_Commission_Amount_HT :" + Total_Commission_Amount_HT);
					LogLevel.DEBUG(5, new Throwable(), "Total_Commission_Amount_SQL :" + Total_Commission_Amount_SQL);
					if(Total_Commission_Amount_HT.isEmpty())
					{
						
					}
					else
					{
						String erp_pre_tax_tmp=(String)Total_Commission_Amount_HT.get("erp_pre_tax");
						LogLevel.DEBUG(5, new Throwable(), "erp_pre_tax_tmp :" + erp_pre_tax_tmp);
						
						if(erp_pre_tax_tmp.equals(null) || erp_pre_tax_tmp.equals("NULL") || erp_pre_tax_tmp.equals("0") || erp_pre_tax_tmp.equals("null") || erp_pre_tax_tmp == null || erp_pre_tax_tmp == "null")
						{
							String ProductActualPrice="";
							String ERP_Pre_TAX="";
							String City_Percentage="";
							
							String Get_Product_Price_SQL ="select DISTINCT  a.bat_act_price,a.erp_pre_tax, b.city_percentage from batteryprice a, percentage b, battery_order_details c WHERE c.order_number='"+ordernumber+"' and c.ord_id='"+orderid+"' and a.bat_brand=c.battery_brand and a.bat_model=c.battery_model and a.city=c.city and b.city=c.city ORDER BY batprice_id DESC LIMIT 1"; 
							LogLevel.DEBUG(5, new Throwable(), "Get_Product_Price_SQL :" + Get_Product_Price_SQL);

							Hashtable Get_Product_Price_SQL_HT = qm.getRow(Get_Product_Price_SQL);
							LogLevel.DEBUG(5, new Throwable(), "Get_Product_Price_SQL_HT :" + Get_Product_Price_SQL_HT);

							if(Get_Product_Price_SQL_HT.isEmpty())
							{
								strRes="Session Expired or Server Down. Please regenerate your order.";
								
							}
							else
							{
								ProductActualPrice=(String)Get_Product_Price_SQL_HT.get("bat_act_price");
								LogLevel.DEBUG(5, new Throwable(), "ProductActualPrice :" + ProductActualPrice);
								
								ERP_Pre_TAX =(String)Get_Product_Price_SQL_HT.get("erp_pre_tax");
								LogLevel.DEBUG(5, new Throwable(), "ERP_Pre_TAX :" + ERP_Pre_TAX);
								
								City_Percentage=(String)Get_Product_Price_SQL_HT.get("city_percentage");
								LogLevel.DEBUG(5, new Throwable(), "City_Percentage :" + City_Percentage);
							}
							
							strSqlQry = "UPDATE battery_order_details SET erp_pre_tax = '"+ERP_Pre_TAX+"', MRP_Price = '"+ProductActualPrice+"', city_percentage = '"+City_Percentage+"'WHERE  order_number='"+ordernumber+"' and ord_id='"+orderid+"' ";
							LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
							
							int reslt = qm.executeUpdate(strSqlQry);
							if(reslt <0)
							{
								out.println("Failed to update ordered status!");
								strRes="Failed to update ordered status!";
							}
							else
							{
								out.println("Please Try again!");
								strRes="Please Try again!- Order Values updated";
							}
							
						}
												
						long Total_Commission_Amount2=(Long)Total_Commission_Amount_HT.get("Total_Commission");
						LogLevel.DEBUG(5, new Throwable(), "Total_Commission_Amount2 :" + Total_Commission_Amount2);
						
						Total_Commission_Amount = Long.toString(Total_Commission_Amount2);
						LogLevel.DEBUG(5, new Throwable(), "Total_Commission_Amount :" + Total_Commission_Amount);
						
						long Final_Commission2=(Long)Total_Commission_Amount_HT.get("Final_Commission");
						LogLevel.DEBUG(5, new Throwable(), "Final_Commission2 :" + Final_Commission2);
						
						Final_Commission_Amount = Long.toString(Final_Commission2);
						LogLevel.DEBUG(5, new Throwable(), "Final_Commission_Amount :" + Final_Commission_Amount);
						
						Commission_WithOut_Old_Battery=(String)Total_Commission_Amount_HT.get("price");
						LogLevel.DEBUG(5, new Throwable(), "Commission_WithOut_Old_Battery :" + Commission_WithOut_Old_Battery);
						
						Commission_With_Old_Battery=(String)Total_Commission_Amount_HT.get("witholdbatprice");
						LogLevel.DEBUG(5, new Throwable(), "Commission_With_Old_Battery :" + Commission_With_Old_Battery);
					}
				}
				else
				{
					
					if(payment_mode=="Debit Card" || payment_mode.equals("Debit Card") || payment_mode.equals("debit card")){
						Commission_Diction_Rate=0.01;
					}
					else if(payment_mode=="Credit Card" || payment_mode.equals("Credit Card") || payment_mode.equals("credit card")){
						Commission_Diction_Rate=0.02;
					}
					else{
						Commission_Diction_Rate=0.00;
					}
					
					if(order_type=="New" || order_type.equals("New") || order_type.equals("new") ){
						Commission_Diction_Amount="price";
					}
					else{
						Commission_Diction_Amount="witholdbatprice";
					}
					
					String Total_Commission_Amount_SQL = "SELECT price,erp_pre_tax,witholdbatprice,CAST(round(((price))-(erp_pre_tax*city_percentage))/ 2 AS SIGNED) as Total_Commission,CAST(round(((((price))-(erp_pre_tax*city_percentage))/ 2 )-(("+Commission_Diction_Rate+"*"+Commission_Diction_Amount+")/2) )AS SIGNED) as Final_Commission FROM battery_order_details WHERE order_number='"+ordernumber+"' and ord_id='"+orderid+"'";
					
					Hashtable Total_Commission_Amount_HT = qm.getRow(Total_Commission_Amount_SQL);
					LogLevel.DEBUG(5, new Throwable(), "Total_Commission_Amount_HT :" + Total_Commission_Amount_HT);
					LogLevel.DEBUG(5, new Throwable(), "Total_Commission_Amount_SQL :" + Total_Commission_Amount_SQL);
					if(Total_Commission_Amount_HT.isEmpty())
					{
						
					}
					else
					{
						String erp_pre_tax_tmp=(String)Total_Commission_Amount_HT.get("erp_pre_tax");
						LogLevel.DEBUG(5, new Throwable(), "erp_pre_tax_tmp :" + erp_pre_tax_tmp);
						
						if(erp_pre_tax_tmp.equals(null) || erp_pre_tax_tmp.equals("NULL") || erp_pre_tax_tmp.equals("0") || erp_pre_tax_tmp.equals("null") || erp_pre_tax_tmp == null || erp_pre_tax_tmp == "null")
						{
							String ProductActualPrice="";
							String ERP_Pre_TAX="";
							String City_Percentage="";
							
							String Get_Product_Price_SQL ="select DISTINCT  a.bat_act_price,a.erp_pre_tax, b.city_percentage from batteryprice a, percentage b, battery_order_details c WHERE c.order_number='"+ordernumber+"' and c.ord_id='"+orderid+"' and a.bat_brand=c.battery_brand and a.bat_model=c.battery_model and a.city=c.city and b.city=c.city ORDER BY batprice_id DESC LIMIT 1"; 
							LogLevel.DEBUG(5, new Throwable(), "Get_Product_Price_SQL :" + Get_Product_Price_SQL);

							Hashtable Get_Product_Price_SQL_HT = qm.getRow(Get_Product_Price_SQL);
							LogLevel.DEBUG(5, new Throwable(), "Get_Product_Price_SQL_HT :" + Get_Product_Price_SQL_HT);

							if(Get_Product_Price_SQL_HT.isEmpty())
							{
								strRes="Session Expired or Server Down. Please regenerate your order.";
								
							}
							else
							{
								ProductActualPrice=(String)Get_Product_Price_SQL_HT.get("bat_act_price");
								LogLevel.DEBUG(5, new Throwable(), "ProductActualPrice :" + ProductActualPrice);
								
								ERP_Pre_TAX =(String)Get_Product_Price_SQL_HT.get("erp_pre_tax");
								LogLevel.DEBUG(5, new Throwable(), "ERP_Pre_TAX :" + ERP_Pre_TAX);
								
								City_Percentage=(String)Get_Product_Price_SQL_HT.get("city_percentage");
								LogLevel.DEBUG(5, new Throwable(), "City_Percentage :" + City_Percentage);
							}
							
							strSqlQry = "UPDATE battery_order_details SET erp_pre_tax = '"+ERP_Pre_TAX+"', MRP_Price = '"+ProductActualPrice+"', city_percentage = '"+City_Percentage+"'WHERE  order_number='"+ordernumber+"' and ord_id='"+orderid+"' ";
							LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
							
							int reslt = qm.executeUpdate(strSqlQry);
							if(reslt <0)
							{
								out.println("Failed to update ordered status!");
								strRes="Failed to update ordered status!";
							}
							else
							{
								out.println("Please Try again!");
								strRes="Please Try again!- Order Values updated";
							}
							
						}
												
						long Total_Commission_Amount2=(Long)Total_Commission_Amount_HT.get("Total_Commission");
						LogLevel.DEBUG(5, new Throwable(), "Total_Commission_Amount2 :" + Total_Commission_Amount2);
						
						Total_Commission_Amount = Long.toString(Total_Commission_Amount2);
						LogLevel.DEBUG(5, new Throwable(), "Total_Commission_Amount :" + Total_Commission_Amount);
						
						long Final_Commission2=(Long)Total_Commission_Amount_HT.get("Final_Commission");
						LogLevel.DEBUG(5, new Throwable(), "Final_Commission2 :" + Final_Commission2);
						
						Final_Commission_Amount = Long.toString(Final_Commission2);
						LogLevel.DEBUG(5, new Throwable(), "Final_Commission_Amount :" + Final_Commission_Amount);
						
						Commission_WithOut_Old_Battery=(String)Total_Commission_Amount_HT.get("price");
						LogLevel.DEBUG(5, new Throwable(), "Commission_WithOut_Old_Battery :" + Commission_WithOut_Old_Battery);
						
						Commission_With_Old_Battery=(String)Total_Commission_Amount_HT.get("witholdbatprice");
						LogLevel.DEBUG(5, new Throwable(), "Commission_With_Old_Battery :" + Commission_With_Old_Battery);
					}
				}
			}
			else
			{
					
				if(payment_mode=="Debit Card" || payment_mode.equals("Debit Card") || payment_mode.equals("debit card")){
					Commission_Diction_Rate=0.01;
				}
				else if(payment_mode=="Credit Card" || payment_mode.equals("Credit Card") || payment_mode.equals("credit card")){
					Commission_Diction_Rate=0.02;
				}
				else{
					Commission_Diction_Rate=0.00;
				}
				
				if(order_type=="New" || order_type.equals("New") || order_type.equals("new") ){
					Commission_Diction_Amount="price";
				}
				else{
					Commission_Diction_Amount="witholdbatprice";
				}
				
				String Total_Commission_Amount_SQL = "SELECT price,erp_pre_tax,witholdbatprice,CAST(round((price)-(erp_pre_tax))/ 2 AS SIGNED) as Total_Commission,CAST(( round((price)-(erp_pre_tax))/ 2 )-(("+Commission_Diction_Rate+"*"+Commission_Diction_Amount+")/2) AS SIGNED) as Final_Commission FROM battery_order_details WHERE order_number='"+ordernumber+"' and ord_id='"+orderid+"'";
				
				Hashtable Total_Commission_Amount_HT = qm.getRow(Total_Commission_Amount_SQL);
				LogLevel.DEBUG(5, new Throwable(), "Total_Commission_Amount_HT :" + Total_Commission_Amount_HT);
				LogLevel.DEBUG(5, new Throwable(), "Total_Commission_Amount_SQL :" + Total_Commission_Amount_SQL);
				if(Total_Commission_Amount_HT.isEmpty())
				{
					
				}
				else
				{
					String erp_pre_tax_tmp=(String)Total_Commission_Amount_HT.get("erp_pre_tax");
					LogLevel.DEBUG(5, new Throwable(), "erp_pre_tax_tmp :" + erp_pre_tax_tmp);
					
					if(erp_pre_tax_tmp.equals(null) || erp_pre_tax_tmp.equals("NULL") || erp_pre_tax_tmp.equals("0") || erp_pre_tax_tmp.equals("null") || erp_pre_tax_tmp == null || erp_pre_tax_tmp == "null")
					{
						String ProductActualPrice="";
						String ERP_Pre_TAX="";
						String City_Percentage="";
						
						String Get_Product_Price_SQL ="select DISTINCT  a.bat_act_price,a.erp_pre_tax, b.city_percentage from batteryprice a, percentage b, battery_order_details c WHERE c.order_number='"+ordernumber+"' and c.ord_id='"+orderid+"' and a.bat_brand=c.battery_brand and a.bat_model=c.battery_model and a.city=c.city and b.city=c.city ORDER BY batprice_id DESC LIMIT 1"; 
						LogLevel.DEBUG(5, new Throwable(), "Get_Product_Price_SQL :" + Get_Product_Price_SQL);

						Hashtable Get_Product_Price_SQL_HT = qm.getRow(Get_Product_Price_SQL);
						LogLevel.DEBUG(5, new Throwable(), "Get_Product_Price_SQL_HT :" + Get_Product_Price_SQL_HT);

						if(Get_Product_Price_SQL_HT.isEmpty())
						{
							strRes="Session Expired or Server Down. Please regenerate your order.";
							
						}
						else
						{
							ProductActualPrice=(String)Get_Product_Price_SQL_HT.get("bat_act_price");
							LogLevel.DEBUG(5, new Throwable(), "ProductActualPrice :" + ProductActualPrice);
							
							ERP_Pre_TAX =(String)Get_Product_Price_SQL_HT.get("erp_pre_tax");
							LogLevel.DEBUG(5, new Throwable(), "ERP_Pre_TAX :" + ERP_Pre_TAX);
							
							City_Percentage=(String)Get_Product_Price_SQL_HT.get("city_percentage");
							LogLevel.DEBUG(5, new Throwable(), "City_Percentage :" + City_Percentage);
						}
						
						strSqlQry = "UPDATE battery_order_details SET erp_pre_tax = '"+ERP_Pre_TAX+"', MRP_Price = '"+ProductActualPrice+"', city_percentage = '"+City_Percentage+"'WHERE  order_number='"+ordernumber+"' and ord_id='"+orderid+"' ";
						LogLevel.DEBUG(5,new Throwable()," : "+strSqlQry);
						
						int reslt = qm.executeUpdate(strSqlQry);
						if(reslt <0)
						{
							out.println("Failed to update ordered status!");
							strRes="Failed to update ordered status!";
						}
						else
						{
							out.println("Please Try again!");
							strRes="Please Try again!- Order Values updated";
						}
						
					}
											
					long Total_Commission_Amount2=(Long)Total_Commission_Amount_HT.get("Total_Commission");
					LogLevel.DEBUG(5, new Throwable(), "Total_Commission_Amount2 :" + Total_Commission_Amount2);
					
					Total_Commission_Amount = Long.toString(Total_Commission_Amount2);
					LogLevel.DEBUG(5, new Throwable(), "Total_Commission_Amount :" + Total_Commission_Amount);
					
					long Final_Commission2=(Long)Total_Commission_Amount_HT.get("Final_Commission");
					LogLevel.DEBUG(5, new Throwable(), "Final_Commission2 :" + Final_Commission2);
					
					Final_Commission_Amount = Long.toString(Final_Commission2);
					LogLevel.DEBUG(5, new Throwable(), "Final_Commission_Amount :" + Final_Commission_Amount);
					
					Commission_WithOut_Old_Battery=(String)Total_Commission_Amount_HT.get("price");
					LogLevel.DEBUG(5, new Throwable(), "Commission_WithOut_Old_Battery :" + Commission_WithOut_Old_Battery);
					
					Commission_With_Old_Battery=(String)Total_Commission_Amount_HT.get("witholdbatprice");
					LogLevel.DEBUG(5, new Throwable(), "Commission_With_Old_Battery :" + Commission_With_Old_Battery);
				}
			}
			String strSqlQry_final= "update battery_order_details set battery_commission_amount='"+Final_Commission_Amount+"',total_commission_amount='"+Total_Commission_Amount+"' WHERE  order_number='"+ordernumber+"' and ord_id='"+orderid+"' ";
			LogLevel.DEBUG(5,new Throwable(),"strSqlQry_final:"+strSqlQry_final );
				
			int strSqlQry_final_res = qm.executeUpdate(strSqlQry_final);
			if(strSqlQry_final_res <0)
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
	catch (Exception e)
	{
		LogLevel.ERROR(0, e, "Problem Caught Exception if(add)!! " + e);
		e.printStackTrace();
	}	
%>
<%
	String SQL_for_update_orders_Inverter = "select order_id,order_number,payment_mode from inverter_order_details where total_commission_amount='0'  and order_status='Customer Contacted' and order_reasons='installed'";
	LogLevel.DEBUG(5, new Throwable(), "SQL_for_update_orders_Inverter :" + SQL_for_update_orders_Inverter);
	Vector Vector_for_update_orders_Inverter=qm.executeQuery(SQL_for_update_orders_Inverter);
	LogLevel.DEBUG(5,new Throwable(),"Vector_for_update_orders_Inverter:"+Vector_for_update_orders_Inverter);
	
	if(Vector_for_update_orders_Inverter.isEmpty())
	{ 
		out.println("Inverter - No Orders to update");
	}
	else
	{ 
		for (int i=0; i<Vector_for_update_orders_Inverter.size(); i++)
		{
			Hashtable HT_for_update_orders_Inverter=(Hashtable)Vector_for_update_orders_Inverter.get(i);
			String orderid =String.valueOf(HT_for_update_orders_Inverter.get("order_id"));
			String ordernumber =String.valueOf(HT_for_update_orders_Inverter.get("order_number"));
			String payment_mode =String.valueOf(HT_for_update_orders_Inverter.get("payment_mode"));
			
			
			String Total_Commission_Amount="";
			String Final_Commission_Amount="";
			double Commission_Diction_Rate;
			String Commission_Diction_Amount="";
			String Commission_WithOut_Old_Battery="";
			String Commission_With_Old_Battery="";
			
			LogLevel.DEBUG(5,new Throwable(),"payment_mode :"+payment_mode);
			
			if(payment_mode=="Debit Card" || payment_mode.equals("Debit Card") || payment_mode.equals("debit card")){
				Commission_Diction_Rate=0.01;
			}
			else if(payment_mode=="Credit Card" || payment_mode.equals("Credit Card") || payment_mode.equals("credit card")){
				Commission_Diction_Rate=0.02;
			}
			else{
				Commission_Diction_Rate=0.00;
			}
			
			
			Commission_Diction_Amount="price";

			String Total_Commission_Amount_SQL = "SELECT price,erp_pre_tax,CAST(round((price/city_percentage)-(erp_pre_tax )) AS SIGNED) as Total_Commission,CAST(round((price/city_percentage)-(erp_pre_tax ))-("+Commission_Diction_Rate+"*"+Commission_Diction_Amount+") AS SIGNED) as Final_Commission FROM inverter_order_details WHERE order_number='"+ordernumber+"' and order_id='"+orderid+"'";
			
			Hashtable Total_Commission_Amount_HT = qm.getRow(Total_Commission_Amount_SQL);
			LogLevel.DEBUG(5, new Throwable(), "Total_Commission_Amount_HT :" + Total_Commission_Amount_HT);
			LogLevel.DEBUG(5, new Throwable(), "Total_Commission_Amount_SQL :" + Total_Commission_Amount_SQL);
			if(Total_Commission_Amount_HT.isEmpty())
			{
				/*following code is for generating the random number */
			}
			else
			{
				String erp_pre_tax_tmp=(String)Total_Commission_Amount_HT.get("erp_pre_tax");
				LogLevel.DEBUG(5, new Throwable(), "erp_pre_tax_tmp :" + erp_pre_tax_tmp);
				
				if(erp_pre_tax_tmp.equals(null) || erp_pre_tax_tmp.equals("NULL") || erp_pre_tax_tmp.equals("0") || erp_pre_tax_tmp.equals("null") || erp_pre_tax_tmp == null || erp_pre_tax_tmp == "null")
				{
					out.println("Inverter - Failed Update "+ordernumber);
					out.println("</br>");
				}
				else
				{
											
					long Total_Commission_Amount2=(Long)Total_Commission_Amount_HT.get("Total_Commission");
					LogLevel.DEBUG(5, new Throwable(), "Total_Commission_Amount2 :" + Total_Commission_Amount2);
					
					Total_Commission_Amount = Long.toString(Total_Commission_Amount2);
					LogLevel.DEBUG(5, new Throwable(), "Total_Commission_Amount :" + Total_Commission_Amount);
					
					long Final_Commission2=(Long)Total_Commission_Amount_HT.get("Final_Commission");
					LogLevel.DEBUG(5, new Throwable(), "Final_Commission2 :" + Final_Commission2);
					
					Final_Commission_Amount = Long.toString(Final_Commission2);
					LogLevel.DEBUG(5, new Throwable(), "Final_Commission_Amount :" + Final_Commission_Amount);
					
					Commission_WithOut_Old_Battery=(String)Total_Commission_Amount_HT.get("price");
					LogLevel.DEBUG(5, new Throwable(), "Commission_WithOut_Old_Battery :" + Commission_WithOut_Old_Battery);
					
					Commission_With_Old_Battery=(String)Total_Commission_Amount_HT.get("witholdbatprice");
					LogLevel.DEBUG(5, new Throwable(), "Commission_With_Old_Battery :" + Commission_With_Old_Battery);
					
					String strSqlQry= "update inverter_order_details set total_commission_amount='"+Total_Commission_Amount+"',inverter_commission_amount='"+Final_Commission_Amount+"' WHERE  order_number='"+ordernumber+"' and order_id='"+orderid+"'";
					LogLevel.DEBUG(5,new Throwable(),"strSqlQry:"+strSqlQry );
					
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
			
	}
									
%>

