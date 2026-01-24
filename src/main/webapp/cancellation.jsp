<%-- 
Document   		 : cancellation.jsp
Created on 		 : Sep 15, 2016, 10:14:12 AM
Author     		 : BookBattery
Copyright Notice : BookBattery Confidential.
Document         : This jsp is used as Cancellation Jsp page.
--%>

<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Enumeration,java.util.Properties,java.io.FileInputStream,javax.servlet.ServletContext"%>
<%
Properties propsMOPConfig = new Properties();
ServletContext context = getServletContext();
FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
propsMOPConfig.load(fin1); 
fin1.close(); 
String publicUrl = (propsMOPConfig.getProperty("publicUrl")!=null)?propsMOPConfig.getProperty("publicUrl"):"";
String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?propsMOPConfig.getProperty("baseurldirectory"):"";
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<!--[if IE]>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<![endif]-->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Buy Car Battery, Inverter Batteries & Inverters Online in India at BookBattery.com</title>
<meta name="description" content="Looking for a Car Battery, Inverter Battery in India. Buy Amaron, Exide & Luminious BookBattery with free delivery, free installation & company warranty ">
<meta name="keywords" content="car battery, car battery online, 4  wheeler battery, Exide battery, amaron battery, amaron car battery, exide car battery, inverter battery, amaron inverter battery, exide inverter battery, inverter battery online, BookBattery">

<!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "dev/includes/jsp/includes_css.jsp" />
<!---################################## CSS Include Ends  ################################------>

</head>
<body>
<div class="page">

<!---################################## Header Include Starts  ################################------>
	<jsp:include page = "dev/includes/jsp/includes_header.jsp" />
<!---################################## Header Include Ends  ################################------>
  <!-- breadcrumbs -->
  <div class="breadcrumbs">
    <div class="container">
      <div class="row">
        <ul>
          <li class="home"> <a href="<%=publicUrl%><%=baseurldirectory%>" title="Go to Home Page"> Home</a><span>&raquo;</span></li>
          <li class="category13"><strong>Order Cancellation</strong></li>
        </ul>
      </div>
    </div>
  </div>
  <!-- End breadcrumbs --> 
  <div class="main-container col2-right-layout">
    <div class="main container">
      <div class="row">
        <section class="col-main col-sm-9" style=" font-size: 14px;">  <div class="static-contain">
          <div class="page-title new_page_title">
            <h2>Cancellation and Refund</h2>
          </div>
        
			<h5>Option 1 : Cash on Delivery or Online Payment After Fitment or UPI Options such as PayTM, GooglePay and Phonepay</h5>
                <p  style="text-align:justify">
				The bill amount collected after the car battery or bike battery or inverter battery is installed. Once installed and verified, there will not be any refund and will be governed by the warranty limits of the manufacturer.</p>
			<h5>Option 2: Payment using Credit/Debit/Net banking options/EMI</h5>
                <p  style="text-align:justify">
				The customer is eligible for full refund, if the customer cancels the order before the car battery or bike battery or inverter battery is dispatched.Cancellation charges of Credit or Debit card will be withheld. </p>
				
				<p>If the customer cancels the order after the car battery or bike battery or inverter battery is dispatched and not installed, a Rs 200 for delivery charges plus any credit card / debit card charges will be withheld. In case the car battery or bike battery or inverter battery is dispatched and installed there will be no refund and will abide by the warranty options of the manufacturer. Customers can cancel the order by calling +91 9603467559 or by sending an email to <a href = "mailto:contact@BookBattery.com">contact@BookBattery.com.</a></p>
				
			<p><b>Note : If the payment is made using a credit card, there will be an extra charge of 2%. If the payment is made using debit card, there will be extra charge of 1%.</b></p>
			<p>The refund amount minus the credit card charges(2%) or debit card charges(1%) will be sent to the Customer with in 2 - 4 weeks from the date of cancellation.</p>
			
          </div>
        </section>
        <aside class="col-right sidebar col-sm-3 wow bounceInUp animated">
          <div class="block block-company">
            <div class="block-title">Quick Links </div>
            <div class="block-content">
              <ol id="recently-viewed-items">
               <li class="item  even"><a href="choosecarbattery.jsp">Choose your Car Battery</a></li>
                <li class="item even"><a href="faq.jsp">FAQ's</a></li>
                <li class="item last"><a href="contactus.jsp">Contact Us</a></li>
              </ol>
            </div>
          </div>
        </aside>
      </div>
    </div>
  </div>
<!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_js.jsp" />
<!---################################## CSS Include Ends  ################################------>
<input type="hidden" name="brandkeyword" id='brandkeyword' value="Common">
<input type="hidden" name="MobileNumberPopUpCheck" id='MobileNumberPopUpCheck' value="HomePage">
<input type="hidden" name="user_mobile_number_cookie_tmp" id='user_mobile_number_cookie_tmp' value="">
<!---################################## JS Include Starts  ################################------>
	<jsp:include page = "dev/includes/jsp/includes_footer.jsp" />
<!---################################## JS Include Ends  ################################------>

</body>
</html>
