<%-- 
Document   		 : online_transaction.jsp
Created on 		 : Feb 15, 2017, 10:14:12 AM
Author     		 : BookBattery
Copyright Notice : Asistmi Solutions Pvt.Ltd. Confidential.
Document         : This jsp is used as show details or orders.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.*,com.ngit.javabean.loglevel.LogLevel,javax.servlet.ServletContext,java.util.Properties,java.io.FileInputStream, com.ngit.javabean.qrymgr.QueryManager"%>
<%response.setHeader("Pragma","no-cache"); response.setHeader("Cache-Control","no-store"); response.setHeader("Expires","0"); response.setDateHeader("Expires",-1); %>
<%
Properties propsMOPConfig = new Properties();
ServletContext context = getServletContext();
FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
propsMOPConfig.load(fin1); 
fin1.close(); 

QueryManager qm;
qm = QueryManager.getInstance(propsMOPConfig) ;
	
	
String publicUrl = (propsMOPConfig.getProperty("publicUrl")!=null)?propsMOPConfig.getProperty("publicUrl"):"";
String baseURL = (propsMOPConfig.getProperty("baseURL")!=null)?propsMOPConfig.getProperty("baseURL"):"";
String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?propsMOPConfig.getProperty("baseurldirectory"):"";
String serverURL = (propsMOPConfig.getProperty("serverURL")!=null)?propsMOPConfig.getProperty("serverURL"):"";

String titles = "";
String description = "";
String keywords = "";

String Order_Type ="";
String Payment_Type="";
String Payment_Status="";
String Order_Number="";
String Icon_Url_1="";
String Icon_Url_2="";
String Product_Brand="";
String Product_Model="";
String Product_Capacity="";
String Product_Quantity="";
String Product_Replacement="";
String Product_Final_Price="";
String Product_Payment_Id="";
String delivery_mode="";
String delivery_charge="";
String bat_type="";

%>

<%
try
	{
		Payment_Status = (String) request.getAttribute("Payment_Status");
		Payment_Type = ((String) request.getAttribute("Payment_Type")!=null)?(String) request.getAttribute("Payment_Type"):"";
		LogLevel.DEBUG(5, new Throwable(), "Payment_Type :" + Payment_Type);
		Order_Type = (String) request.getAttribute("OrderType");
		LogLevel.DEBUG(5, new Throwable(), "Order_Type :" + Order_Type);
		
		Hashtable Battery_Order_Details = (Hashtable) request.getAttribute("BatteryOrderDetails");
		LogLevel.DEBUG(5, new Throwable(), "Battery_Order_Details :" + Battery_Order_Details);
		
		Hashtable Inverter_Order_Details = (Hashtable) request.getAttribute("InverterOrderDetails");
		LogLevel.DEBUG(5, new Throwable(), "Inverter_Order_Details :" + Inverter_Order_Details);

		if(Order_Type.equals("Battery"))
		{
			Order_Number=String.valueOf(Battery_Order_Details.get("order_number"));
			Product_Brand=String.valueOf(Battery_Order_Details.get("battery_brand"));
			Product_Model=String.valueOf(Battery_Order_Details.get("battery_model"));
			Product_Capacity=String.valueOf(Battery_Order_Details.get("battery_capacity"));
			Product_Replacement=String.valueOf(Battery_Order_Details.get("order_type"));
			String Order_Price=String.valueOf(Battery_Order_Details.get("price"));
			String Order_OBRP=String.valueOf(Battery_Order_Details.get("witholdbatprice"));
			Product_Quantity=String.valueOf(Battery_Order_Details.get("quantity"));
			Product_Payment_Id=String.valueOf(Battery_Order_Details.get("payment_id"));
			delivery_mode=String.valueOf(Battery_Order_Details.get("delivery_mode"));
			delivery_charge=String.valueOf(Battery_Order_Details.get("delivery_charge"));
			bat_type=String.valueOf(Battery_Order_Details.get("bat_type"));
			
			int Price_Temp=0;
			if (Product_Replacement=="New" || Product_Replacement.equals("New"))
			{
				Price_Temp = Integer.parseInt(Order_Price);
			}
			else
			{
				Price_Temp = Integer.parseInt(Order_OBRP);
			}
			int QTY_int = Integer.parseInt(Product_Quantity);
			int Final_Price_Int=Price_Temp*QTY_int;
			String Final_Price_Temp = Integer.toString(Final_Price_Int);
			
			Product_Final_Price="<i class='icon-inr' aria-hidden='true'></i> "+Integer.toString(Price_Temp)+" X "+Product_Quantity+"Pcs = <b><i class='icon-inr' aria-hidden='true'></i> "+Final_Price_Temp+"</b>";
			
			String SQL_TO_GET_ICON_URL= "select icon_url from battery_details WHERE bat_model='"+Product_Model+"'  LIMIT 1";
			LogLevel.DEBUG(5, new Throwable(), "SQL_TO_GET_ICON_URL :" + SQL_TO_GET_ICON_URL);
			
			Hashtable HT_TO_GET_ICON_URL = qm.getRow(SQL_TO_GET_ICON_URL);
			LogLevel.DEBUG(5, new Throwable(), "HT_TO_GET_ICON_URL :" + HT_TO_GET_ICON_URL);
			
			Icon_Url_1=String.valueOf(HT_TO_GET_ICON_URL.get("icon_url"));
						
		}
		else if (Order_Type.equals("Inverter"))
		{
			Order_Number=String.valueOf(Inverter_Order_Details.get("order_number"));
			LogLevel.DEBUG(5, new Throwable(), "Order_Number :" + Order_Number);
			
			Product_Brand=String.valueOf(Inverter_Order_Details.get("inverter_brand"));
			LogLevel.DEBUG(5, new Throwable(), "Product_Brand :" + Product_Brand);
			
			Product_Model=String.valueOf(Inverter_Order_Details.get("inverter_model"));
			LogLevel.DEBUG(5, new Throwable(), "Product_Model :" + Product_Model);
			
			Product_Capacity=String.valueOf(Inverter_Order_Details.get("inverter_capacity"));
			LogLevel.DEBUG(5, new Throwable(), "Product_Capacity :" + Product_Capacity);
			
			String Order_Price=String.valueOf(Inverter_Order_Details.get("price"));
			LogLevel.DEBUG(5, new Throwable(), "Order_Price :" + Order_Price);
			
			Product_Quantity=String.valueOf(Inverter_Order_Details.get("quantity"));
			LogLevel.DEBUG(5, new Throwable(), "Product_Quantity :" + Product_Quantity);
			
			Product_Payment_Id=String.valueOf(Inverter_Order_Details.get("payment_id"));
			LogLevel.DEBUG(5, new Throwable(), "Product_Payment_Id :" + Product_Payment_Id);
			
			int Price_Temp = Integer.parseInt(Order_Price);
		
			int QTY_int = Integer.parseInt(Product_Quantity);
			int Final_Price_Int=Price_Temp*QTY_int;
			String Final_Price_Temp = Integer.toString(Final_Price_Int);
			
			Product_Final_Price="<i class='icon-inr' aria-hidden='true'></i> "+Order_Price+" X "+Product_Quantity+"Pcs = <b><i class='icon-inr' aria-hidden='true'></i> "+Final_Price_Temp+"</b>";
			
			String SQL_TO_GET_ICON_URL= "select icon_url from inverter_details WHERE inverter_model='"+Product_Model+"'  LIMIT 1";
			LogLevel.DEBUG(5, new Throwable(), "SQL_TO_GET_ICON_URL :" + SQL_TO_GET_ICON_URL);
			
			Hashtable HT_TO_GET_ICON_URL = qm.getRow(SQL_TO_GET_ICON_URL);
			LogLevel.DEBUG(5, new Throwable(), "HT_TO_GET_ICON_URL :" + HT_TO_GET_ICON_URL);
			
			Icon_Url_1=String.valueOf(HT_TO_GET_ICON_URL.get("icon_url"));
			
		}
		else if (Order_Type.equals("Inverter_and_Battery"))
		{
			String Order_Number_BAT=String.valueOf(Battery_Order_Details.get("order_number"));
			String Product_Brand_BAT=String.valueOf(Battery_Order_Details.get("battery_brand"));
			String Product_Model_BAT=String.valueOf(Battery_Order_Details.get("battery_model"));
			String Product_Capacity_BAT=String.valueOf(Battery_Order_Details.get("battery_capacity"));
			String Product_Replacement_BAT=String.valueOf(Battery_Order_Details.get("order_type"));
			String Order_Price_BAT=String.valueOf(Battery_Order_Details.get("price"));
			String Order_OBRP_BAT=String.valueOf(Battery_Order_Details.get("witholdbatprice"));
			String Product_Quantity_BAT=String.valueOf(Battery_Order_Details.get("quantity"));
			
			int Price_Temp_BAT=0;
			if (Product_Replacement_BAT=="New" || Product_Replacement_BAT.equals("New"))
			{
				Price_Temp_BAT = Integer.parseInt(Order_Price_BAT);
			}
			else
			{
				Price_Temp_BAT = Integer.parseInt(Order_OBRP_BAT);
			}
			int QTY_int_BAT = Integer.parseInt(Product_Quantity_BAT);
			int Final_Price_Int_BAT=Price_Temp_BAT*QTY_int_BAT;
			String Final_Price_Temp_BAT = Integer.toString(Final_Price_Int_BAT);
			
			String SQL_TO_GET_ICON_URL_BAT= "select icon_url from battery_details WHERE bat_model='"+Product_Model_BAT+"'  LIMIT 1";
			LogLevel.DEBUG(5, new Throwable(), "SQL_TO_GET_ICON_URL_BAT :" + SQL_TO_GET_ICON_URL_BAT);
			
			Hashtable HT_TO_GET_ICON_URL_BAT = qm.getRow(SQL_TO_GET_ICON_URL_BAT);
			LogLevel.DEBUG(5, new Throwable(), "HT_TO_GET_ICON_URL_BAT :" + HT_TO_GET_ICON_URL_BAT);
			
			Icon_Url_2=String.valueOf(HT_TO_GET_ICON_URL_BAT.get("icon_url"));
			
			
			
			String Order_Number_INV=String.valueOf(Inverter_Order_Details.get("order_number"));
			LogLevel.DEBUG(5, new Throwable(), "Order_Number_INV :" + Order_Number_INV);
			
			String Product_Brand_INV=String.valueOf(Inverter_Order_Details.get("inverter_brand"));
			LogLevel.DEBUG(5, new Throwable(), "Product_Brand_INV :" + Product_Brand_INV);
			
			String Product_Model_INV=String.valueOf(Inverter_Order_Details.get("inverter_model"));
			LogLevel.DEBUG(5, new Throwable(), "Product_Model_INV :" + Product_Model_INV);
			
			String Product_Capacity_INV=String.valueOf(Inverter_Order_Details.get("inverter_capacity"));
			LogLevel.DEBUG(5, new Throwable(), "Product_Capacity_INV :" + Product_Capacity_INV);
			
			String Order_Price_INV=String.valueOf(Inverter_Order_Details.get("price"));
			LogLevel.DEBUG(5, new Throwable(), "Order_Price_INV :" + Order_Price_INV);
			
	
			
			String Product_Payment_Id_INV=String.valueOf(Inverter_Order_Details.get("payment_id"));
			LogLevel.DEBUG(5, new Throwable(), "Product_Payment_Id_INV :" + Product_Payment_Id_INV);
			
			int Price_Temp_INV = Integer.parseInt(Order_Price_INV);
			int Final_Price_Int_INV=Price_Temp_INV;
			String Final_Price_Temp_INV = Integer.toString(Final_Price_Int_INV);
			
			String SQL_TO_GET_ICON_URL_INV= "select icon_url from inverter_details WHERE inverter_model='"+Product_Model_INV+"'  LIMIT 1";
			LogLevel.DEBUG(5, new Throwable(), "SQL_TO_GET_ICON_URL_INV :" + SQL_TO_GET_ICON_URL_INV);
			
			Hashtable HT_TO_GET_ICON_URL_INV = qm.getRow(SQL_TO_GET_ICON_URL_INV);
			LogLevel.DEBUG(5, new Throwable(), "HT_TO_GET_ICON_URL_INV :" + HT_TO_GET_ICON_URL_INV);
			
			Icon_Url_1=String.valueOf(HT_TO_GET_ICON_URL_INV.get("icon_url"));
			
			
			
			int Final_Price_Temp = Final_Price_Int_INV + Final_Price_Int_BAT;
			
				
			Product_Final_Price="<i class='icon-inr' aria-hidden='true'></i> "+Order_Price_INV+" + "+Price_Temp_BAT+" X "+Product_Quantity_BAT+"Pcs = <b><i class='icon-inr' aria-hidden='true'></i> "+Final_Price_Temp+"</b>";
			
			Order_Number=""+Order_Number_INV+" & "+Order_Number_BAT+"";
			Product_Model=""+Product_Model_INV+" & "+Product_Model_BAT+"";
			Product_Capacity="</br>"+Product_Capacity_INV+" & "+Product_Capacity_BAT+"";
			Product_Brand=Product_Brand_BAT;
			Product_Quantity=Product_Quantity_BAT;
			Product_Replacement=Product_Replacement_BAT;
			Product_Payment_Id=Product_Payment_Id_INV;
			
			
		}
		else
		{
			
		}
	}
	catch (Exception e)
	{										
		LogLevel.ERROR(5, e, "Error :" + e);
	}	
%>


<!-- ###############################------------------------------------------------#############################-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head itemscope itemtype="http://schema.org/WebSite">
<title itemprop='name'><%=titles%></title>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<meta name="viewport" content="width=device-width">
	<meta name="description" content=" Oops! No Product Details Found ">
	<meta name="author" content="Sai Krishna Daddala">
	<meta name="robots" content="noindex">
	<meta name="googlebot" content="noindex">
	<title>Payment Transaction </title>



<!-- Mobile Specific -->
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<!-- Google Fonts -->
<link href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,300,700,800,400,600' rel='stylesheet' type='text/css'>
</head>
<!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_css.jsp" />
<!---################################## CSS Include Ends  ################################------>
</head>
<body>
<!---################################## Header Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_header.jsp" />
<!---################################## Header Include Ends  ################################------>
  <!--End main-container -->

  <!-- main-container -->
  <div class="main-container col2-right-layout">
    <div class="main container">
      <div class="row">
        <section class="col-main col-sm-9">
		
		
		
		
		<%
		if(Payment_Status.equals("completed"))
		{
		%>	
		
		
	
        <div class="checkout-page">
          <div class="page-title new_page_title">
            <h1>Order Placed Successfully</h1>
          </div>
			<div class="form-group"> 
				<div id="after_order_popup_data"> 
				<div style="text-align: center;">					
					<i class="icon-smile" style="font-size: 60px;color: #1d8e11;"></i>					
					<p style="font-size: 14px;font-weight: 600;">Thanks for Ordering with BookBattery</p>				
				</div>				
				<div>
					<div class="col-md-3 col-sm-12 col-xs-12 nopadding-xs ">		
						<img alt="<%=Product_Model%>" itemprop="image" src="<%=Icon_Url_1%>" style="width: 100%;">
						<%
						if(Order_Type.equals("Inverter_and_Battery"))
						{
						%>	
							<p style=" font-size: 18px;  font-weight: 700;  margin: 0px;  text-align: center;"> + </p>
							<img alt="<%=Product_Model%>" itemprop="image" src="<%=Icon_Url_2%>" style="width: 100%;">
						<%	
						}
						%>	
					</div>					
					
					<div class="col-md-9 col-sm-12 col-xs-12 nopadding-xs ">		
						<h4>Your Order Details</h4>						
						<table style=" color: #131212;font-size: 13px;" class="table table-condensed"> 
							<tbody> 
								<tr> 
									<th>Order Number</th> 
									<td>:&nbsp;<%=Order_Number%></td> 
								</tr>
								<tr>
									<th>Product Details</th> 
									<td>:&nbsp;<%=Product_Brand%>, <%=Product_Model%>, <%=Product_Capacity%></td> 
								</tr> 
								<tr>
									<th>Product Quanity</th> 
									<td>:&nbsp;<%=Product_Quantity%> Pcs</td> 
								</tr> 
								
								<%	if (Order_Type.equals("Inverter"))
									{
										
									}
									else
									{
								%>
								
								<tr> 
									<th>Product Replacement</th> 
									<td>:&nbsp;<%=Product_Replacement%></td> 
								</tr>
								
								<%	
								}
								%>	
								
								<tr> 
									<th>Price Details</th> 
									<td>:&nbsp;<%=Product_Final_Price%></td> 
								</tr> 
								<%	if ((bat_type=="Bike Batteries" || bat_type.equals("Bike Batteries"))&&((delivery_mode=="Yes") || delivery_mode.equals("Yes"))&&((delivery_charge!="0") || !delivery_charge.equals("0")))
								{
									%>	
									<tr> 
										<th>Delivery Charges</th> 
										<td>:&nbsp;<i class='icon-inr' aria-hidden='true'></i>&nbsp;<b><%=delivery_charge%></b></td> 
									</tr>
									<%
								}
								else
								{

								}
								%>
								
								<%	if (Payment_Type.equals("COD"))
									{
										
									}
									else
									{
								%>
								<tr> 
									<th>Transaction ID</th> 
									<td>:&nbsp;<%=Product_Payment_Id%></td> 
								</tr> 
								<%	
								}
								%>	
								
								
							</tbody>
						</table>					
					</div>					
					<hr size="100">					
					<div style="text-align: center;">						
					<p> 							
					Please Check your Email or Mobile for More Information regarding Your Order.
					<br> Our Team will Call you soon for Order Confirmation.</p>					
					</div>
				</div>
				</div>
				<div style="text-align: center;"> 	
				<button type="button" class="button button-continue"  onclick="window.location.href='<%=publicUrl%><%=baseurldirectory%>'">Okay</button> 
				</div> 	
			</div>
        </div>
		
		
		<%	
		}	
		else if (Payment_Status.equals("pending"))
		{
			
			String Payment_Link = (String) request.getAttribute("Payment_Link");
		%>

		
        <div class="checkout-page">
          <div class="page-title new_page_title">
            <h1>Payment is Pending</h1>
          </div>
			<div class="form-group"> 
				<div id="after_order_popup_data"> 
				<div style="text-align: center;">					
					<p> <i class="icon-money" style="font-size: 25px;color: #1d8e11;"></i>	</p>				
					<p style="font-size: 21px;font-weight: 600;">We have not yet received the payment for your order.</p>				
				</div>				
				<div>
					<hr size="100">					
					<div style="text-align: center;">						
					<p style="  font-size: 15px;"> 							
					If You Lost somewhere and unable to make payment
					<br> Please click on Make Payment to complete the payment process.</p>					
					</div>
				</div>
				</div>
				<div style="text-align: center;"> 	
				<button type="button" class="button button-continue" onclick="window.location.href='<%=Payment_Link%>'" >Make Payment</button> 
				</div> 	
			</div>
        </div>
		
		
		<%	
		}	
		else if (Payment_Status.equals("failed"))
		{
			String Payment_Link = (String) request.getAttribute("Payment_Link");
		%>

		<div class="checkout-page">
          <div class="page-title new_page_title">
            <h1>Payment is Failed</h1>
          </div>
			<div class="form-group"> 
				<div id="after_order_popup_data"> 
				<div style="text-align: center;">					
					<p> <i class="icon-frown" style="font-size: 25px;color: #1d8e11;"></i>	</p>				
					<p style="font-size: 14px;font-weight: 600;">Your Online Payment Got Failed.</p>				
				</div>				
				<div>
					<hr size="100">					
					<div style="text-align: center;">						
					<p style="font-size: 15px;"> 							
					If payment is made, Please contact our support team for further queries on Payment.
					<br>Phone +91 96034 67559 or write a mail contact@BookBattery.com				
					<br>Click Remake Payment to make payment again</p>					
					</div>
				</div>
				</div>
				<div style="text-align: center;"> 	
				<a type="button" class="button button-continue" href="<%=Payment_Link%>">Remake Payment</a> 
				</div> 	
			</div>
        </div>
		
		
		<%	
		}	
		else
		{
		%>
		
		
		
		
		
		<%	
		}	
		%>

		
		
		
		
		
		
		
		</section>
        <aside class="col-right sidebar col-sm-3 wow bounceInUp animated">
          <div class="block block-company">
            <div class="block-title">Quick Links </div>
            <div class="block-content">
              <ol id="recently-viewed-items">
                <li class="item odd"><strong>About Us</strong></li>
              <!--   <li class="item even"><a href="<%=publicUrl%><%=baseurldirectory%>sitemap.jsp">Sitemap</a></li>--> 
                <li class="item  odd"><a href="<%=publicUrl%><%=baseurldirectory%>payments.jsp">Payments</a></li>
                <li class="item even"><a href="<%=publicUrl%><%=baseurldirectory%>faq.jsp">FAQ's</a></li>
                <li class="item last"><a href="<%=publicUrl%><%=baseurldirectory%>contactus.jsp">Contact Us</a></li>
              </ol>
            </div>
          </div>
        </aside>
      </div>
    </div>
  </div>
  <!--End main-container --> 
  
  <!--End main-container --> 
 <input type="hidden" name="product_page_name" id='product_page_name' value="404">
<!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_js.jsp" />
<!---################################## CSS Include Ends  ################################------>

<!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_footer.jsp" />
<!---################################## CSS Include Ends  ################################------>

<script>
	function goBack() {
		window.history.back();
	}
</script>

</body>
</html>