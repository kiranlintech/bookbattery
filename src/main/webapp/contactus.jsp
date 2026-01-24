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
     
  <section class="main-container col1-layout">
    <div class="main container">
      <div class="col-main">
        <div class="cart wow">
          <div class="page-title">
            <h2>Contact Us</h2>
          </div>
          <div class="delivery">
          <div class="row">
        <div class="col-sm-8 col-md-8 col-lg-8 col-xs-12"> <iframe class="google-map2" frameborder="0" width="100%" height="300px" scrolling="no" marginheight="0" marginwidth="0" src="https://www.google.com/maps/embed?pb=!1m14!1m8!1m3!1d15551.97484702432!2d77.7506948!3d12.9722538!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0xd1280a6dc2fd61fb!2sAsistmi%20Solutions%20Pvt.%20Ltd.!5e0!3m2!1sen!2sin!4v1574137244792!5m2!1sen!2sin"></iframe> </div>

        
        <div class="col-sm-4 col-md-4 col-lg-4 col-xs-12">
         <h4 class="caps">Address</h4>
          <ul>
            <li><h4> <strong>Asistmi Solutions Pvt Ltd.</strong><br /> </h4>
			#304, 4th Floor,Regent Prime, No.48, <br />
			Whitefield Main Road, <br />
			Bangalore,<br />
			Karnataka 560066.<br />
            E-mail: <a href="mailto:contact@BookBattery.com">contact@BookBattery.com</a><br />
            Website: <a href="http://bookbattery.com/" target="_blank">www.bookbattery.com</a> </li>
          </ul>
         
        </div>
        </div>
      </div>
        </div>
		</div>
    </div>
  </section>
  
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
