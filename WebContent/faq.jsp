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
            <h2>FAQ - Frequently Asking Questions </h2>
          </div>
          <div class="delivery">
          <div class="row">
        <div class="col-sm-6 col-md-6 col-lg-6 col-xs-12"> <img src="/bookbattery/images/auto-machanic.jpg?v=2" class="img-responsive animate scale" alt=""></div>
        
        <div class="col-sm-6 col-md-6 col-lg-6 col-xs-12">
        		
		<p><h4>1. How does a battery work?</h4>

		A battery stores energy in chemical form that can be released on demand as electricity. This electrical power is used by the vehicle's ignition system for cranking the engine. The vehicle's battery may also power the lights and other electrical accessories. In case the alternator belt fails, the battery might also need to power the vehicle's entire electrical system for a short period of time.
		</p>
		<p><h4>2. What should I consider when buying a battery?</h4>
               <li>
               <strong>SIZE:</strong>What are the dimensions of your original battery?<br>
               </li>
			   <li>
			   <strong>POWER:</strong> What are the Cold Cranking Amps required to power your vehicle?<br>
               </li>
			   <li>
			   <strong>WARRANTY:</strong> Automotive BookBattery are backed by a warranty package. Choose one that is right for your vehicle's needs.
			   </li>


</p>
         
         
        </div>
        </div>
      </div>
      
      
        </div>
		<div class="cart wow">
         <div class="row">
		<div class="col-sm-12 col-md-12 col-lg-12">



		<p><h4>3. When I am replacing my battery or cleaning the terminals, why is it important to remove the ground wire first?</h4>

		Before you start, always check the type of grounding system the vehicle has. If you remove the positive connector first in a negative ground system, you risk the chance of creating a spark. That could happen if the metal tool you're using to remove the positive terminal connector comes in contact with any piece of metal on the car. If you are working near the battery when this occurs, it might create an ignition source that could cause the battery to explode. It is extremely important to remove the ground source first.

		</p>

		<p><h4>4. What does CCA mean?</h4>
		Cold Cranking Amps is a rating used in the battery industry to define a battery's ability to start an engine in cold temperatures. The rating is the number of amps a new, fully charged battery can deliver at 0° Fahrenheit for 30 seconds, while maintaining a voltage of at least 7.2 volts, for a 12 volt battery. The higher the CCA rating, the greater the starting power of the battery.

		</p>

		<p><h4>5. What MCA or CA rates?</h4>
		This is a rating used to describe the discharge load in amperes which a new, fully charged battery at 32 degrees F (0C), can continuously deliver for 30 seconds and maintain a terminal voltage equal or greater than 1.2 volts per cell. It is sometimes referred to as Marine Cranking Amps or Cranking Amps.

		</p>

		<p><h4>6. What is Reserve Capacity?</h4>
		Reserve Capacity, (RC) is a battery industry rating, defining a battery's ability to power a vehicle with an inoperative alternator or fan belt. The rating is the number of minutes a battery at 80 degrees F can be discharged at 25 amps and maintain a voltage of 10.5 volts for a 12 volt battery. The higher the reserve rating, the longer your vehicle can operate should your alternator or fan belt fail.

		</p>

		<p><h4>7. What can excessive heat do to battery?</h4>
		Hot temperatures will deteriorate a battery's life quicker by evaporating the water from the electrolyte, and corroding and weakening the positive grids.

		</p>

		<p><h4>8. When my vehicle won't start how do I know for sure if my battery needs to be replaced?</h4>
		Many other problems can keep a vehicle from starting, so you need to do some troubleshooting. BookBattery sell BookBattery and a host of other products will often do battery testing and give you feedback on the condition of your battery.

		</p>

		<p><h4>9. What is battery cycle life?</h4>
		One cycle of a battery is a discharge from full charge to full discharge and a return to full charge again. The total number of cycles a battery can perform before failure is called its Cycle Life. Most battery manufacturers will not discuss the Cycle Life of their product. Many advertised Deep Cycle BookBattery have not been tested, or, which is the case with cranking BookBattery, were never designed for long Cycle Life.

		</p>
		
		
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
