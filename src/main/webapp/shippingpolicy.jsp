<%-- 
Document   		 : shippingpolicy.jsp
Created on 		 : Sep 15, 2016, 10:14:12 AM
Author     		 : BookBattery
Copyright Notice : BookBattery Confidential.
Document         : This jsp is used as Shipping Policy Jsp page.
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
          <li class="category13"><strong>Shipping Policy</strong></li>
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
            <h2>Shipping Policy</h2>
          </div>
        
			<h3>Car Batteries and Inverter Batteries</h3>
                <p style="text-align:justify">
				Upon booking the order, customers will get an SMS and an Email about the battery delivery shop with contact telephone number. The customer will also get a call from the delivery shop regarding the fitment of the battery. The customers can opt to go to the delivery shop and get the battery installed or ask the delivery shop person to come and install the battery at customers place(home or office whichever is convenient). Usually the battery is installed within 2 - 24 hours from the booking time depending on the customers availability, service engineers availability, battery model availability in stock and the distance from the delivery shop to the customers delivery address. Customers are instructed to show the order SMS/Email and mention to the delivery person about BookBattery to avail our prices quoted. If the delivery person requests to change the battery model or requests to pay more than what is quoted, please call our support number <a href="tel:+919603467559">+91 9603467559</a>. Battery delivery is done free of cost if the customer delivery or installation place is located within 10 - 15 KM from our delivery shop in that area. We have about 300 delivery shops all over India to serve most locations. Our delivery shops are opened from 9am – 6:30pm Monday through Saturday except for holidays. The Inverter battery installation into a home inverter is free of cost. However, the Inverter installation and wiring will be extra cost to the customer, if the pre-wiring is not done to plug in the inverter.
				</p>

                <h3 id="BikeBatteries" >Bike Batteries</h3>
                <p  style="text-align:justify">
				Upon booking the order, customers will get an SMS and an Email about the battery delivery shop with contact telephone number. The customer will also get a call from the delivery shop regarding the fitment of the battery. The customers will have to go to the delivery shop and get the bike battery installed. Bike Batteries can also be home delivered and installed for an extra cost of Rs.150 if the customer is located within 5kms range from our retailer shop and Rs.300 if the customer is located within 10 kms from our retailer shop. Customers are instructed to mention and show the SMS/Email to the delivery shop to avail the price quoted by us. If the delivery shop changes the battery model or quotes a different price please call our support number <a href="tel:+919603467559">+91 9603467559</a>. There is no extra installation charge for the same. Usually the battery is installed within 2 - 24 hours from the booking time depending on the customers availability, service engineers availability and battery model availability in stock. We have about 300 delivery shops all over India to serve most locations. Our delivery shops are opened from 9am – 6:30pm Monday through Saturday except for holidays. If you want to pay by credit card or debit card, based on the availability of this facility in the delivery shop, there will be a separate service fee charged to the extent of 2 - 3%.
				</p>
				<br>
				<br>
				<p style="text-align:justify">Battery will be delivered within 4 – 24 hours from the time the payment is made. In case the battery is not in stock, it will be delivered in 3 – 5 days. If the battery is not delivered and if the customer cancels the order before the shipment happens, the full amount paid will be refunded</p>
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
