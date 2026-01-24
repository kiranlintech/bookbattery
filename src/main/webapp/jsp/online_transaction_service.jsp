<%-- 
Document   		 : online_transaction.jsp
Created on 		 : Feb 15, 2017, 10:14:12 AM
Author     		 : Bharath Kumar
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

String Payment_Status="";

String Order_Number="";
String Consumer_Name="";
String Services_Type="";
String Services_Package="";
String services_place="";
String Service_Discount_Price="";
String Icon_Url="";
String Vehicle_Name="";
String Vehicle_Model="";
String Product_Type="";
String Product_Capacity="";
String Product_Quantity="";




%>

<%
try
	{
		Payment_Status = (String) request.getAttribute("Payment_Status");
%>


<!-- ###############################------------------------------------------------#############################-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content=" Oops! No Product Details Found ">
	<meta name="author" content="Sai Krishna Daddala">
	<meta name="robots" content="noindex">
	<meta name="googlebot" content="noindex">
	<title>Payment Tranasaction </title>



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
			
			Hashtable Service_Order_Details = (Hashtable) request.getAttribute("ServiceOrderDetails");
			LogLevel.DEBUG(5, new Throwable(), "Service_Order_Details :" + Service_Order_Details);

			Order_Number=String.valueOf(Service_Order_Details.get("order_number"));
			Consumer_Name=String.valueOf(Service_Order_Details.get("consumer_name"));
			Services_Type=String.valueOf(Service_Order_Details.get("services_type"));
			Services_Package=String.valueOf(Service_Order_Details.get("services_package"));
			services_place=String.valueOf(Service_Order_Details.get("services_place"));
			Service_Discount_Price=String.valueOf(Service_Order_Details.get("service_price_discount"));
			Icon_Url=String.valueOf(Service_Order_Details.get("icon_url"));
			Vehicle_Name=String.valueOf(Service_Order_Details.get("veh_name"));
			Vehicle_Model=String.valueOf(Service_Order_Details.get("veh_model"));
			Product_Type=String.valueOf(Service_Order_Details.get("product_type"));
			Product_Capacity=String.valueOf(Service_Order_Details.get("product_capacity"));
			Product_Quantity=String.valueOf(Service_Order_Details.get("quantity"));
		%>	
		
		
	
        <div class="checkout-page">
          <div class="page-title new_page_title">
            <h1>Battery Health Check Booked Successfully</h1>
          </div>
			<div class="form-group"> 
				<div id="after_order_popup_data"> 
				<div style="text-align: center;">					
					<i class="icon-smile" style="font-size: 60px;color: #1d8e11;"></i>					
					<p style="font-size: 14px;font-weight: 600;">Thanks for Booking Battery Health Check with BookBattery.com</p>				
				</div>				
				<div>
					<div class="col-md-3 col-sm-12 col-xs-12 nopadding-xs ">		
						<img alt="<%=Services_Package%>" itemprop="image" src="<%=Icon_Url%>" style="width: 100%;">
					</div>					
					
					<div class="col-md-9 col-sm-12 col-xs-12 nopadding-xs ">		
						<h4>Your Service Order Details</h4>						
						<table style=" color: #131212;font-size: 13px;" class="table table-condensed"> 
							<tbody> 
								<tr> 
									<th>Order Number</th> 
									<td>:&nbsp;<%=Order_Number%></td> 
								</tr>
								<tr>
									<th>Service Type</th> 
									<td>:&nbsp;<%=Services_Type%></td> 
								</tr> 
								<!--<tr>
									<th>Service Package</th> 
									<td>:&nbsp;<%=Services_Package%></td> 
								</tr>-->
								<tr>
									<th>Service Place</th> 
									<td>:&nbsp;<%=services_place.replace("_"," ")%></td> 	
								</tr>
								<%
								if(Product_Type.equals("Car Batteries") || Product_Type.equals("Bike Batteries"))
								{
								%>
									<tr>
										<th>Vehicle Details</th> 
										<td>:&nbsp;<%=Vehicle_Name%>, <%=Vehicle_Model%></td> 
									</tr> 
								<%
								}
								else
								{
								%>
										<tr>
											<th>Inverter Battery Capacity</th> 
											<td>:&nbsp;<%=Product_Capacity%></td> 
										</tr> 
										<tr>
											<th>Inverter Battery Quantity</th> 
											<td>:&nbsp;<%=Product_Quantity%></td> 
										</tr> 
								<%
								}
								%>
								<tr> 
									<th>Price</th> 
									<td>:&nbsp;<i class='icon-inr' aria-hidden='true'></i> <%=Service_Discount_Price%></td> 
								</tr> 
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

		
		
<%
	}
	catch (Exception e)
	{										
		LogLevel.ERROR(5, e, "Error :" + e);
	}	
%>

		
		
		
		
		</section>
        <aside class="col-right sidebar col-sm-3 wow bounceInUp animated">
          <div class="block block-company">
            <div class="block-title">Quick Links </div>
            <div class="block-content">
              <ol id="recently-viewed-items">
                <li class="item odd"><strong>About Us</strong></li>
                <li class="item even"><a href="<%=publicUrl%><%=baseurldirectory%>battery-health-checkup-@-599/">Battery Health Checkup</a></li>
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