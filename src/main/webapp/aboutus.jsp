<%-- 
Document   : index.jsp
Created on : Sep 15, 2016, 10:14:12 AM
Author     : Sai Krishna Daddala
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
     
  <div class="main-container col2-right-layout">
    <div class="main container">
      <div class="row">
        <section class="col-main col-sm-9" style=" font-size: 14px;">  <div class="static-contain">
          <div class="page-title new_page_title">
            <h2>About Us</h2>
          </div>
        
            <p style="text-align:justify">
 
				</br> BookBattery is a registered trademark of Asistmi Solutions Private Limited. BookBattery.com is a market place for Automobile Batteries, Inverter / Home UPS Batteries and Home UPS/Inverters upto 5 KVA Capacity. Batterwale.com is developed, maintained and marketed by Asistmi Solutions Private Limited. Asistmi Solutions Private Limited is a Bangalore, India based Company. </p>
				 <p>BookBattery.com enables an automobile consumer to easily find the battery model that fits their vehicles, transparently discover the price, order and get them delivered and fitted at door steps at their convenient time.
				</p>
           <p style="text-align:justify">
				At BookBattery.com, consumers across India in 240 cities can buy Car Batteries, Two Wheeler Batteries, Bike Batteries, Home UPS Batteries, Inverter Batteries, Home UPS Systems, Inverters, Three Wheeler Auto Batteries, Tractor Batteries, Bus Batteries, Truck Batteries, Lorry Batteries, JCB Batteries, Generator Batteries and related services like Battery Health Check Service, Battery Recharge Service, Battery Jump Start Service. BookBattery provides it services at door steps within 4 – 8 hours from confirmed order.
			</p>
			 <p style="text-align:justify">
				BookBattery.com is bringing complete transparency, convenience and reliability for automobile consumers in replacing and servicing their vehicle batteries. Automobile consumers can now avoid paying high prices and long waiting times at their service showrooms, avoid going to un reliable and un professional mechanic shops and dealers for any battery related issues.
			</p>
			
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
