<%-- 
Document   		 : choosecarbattery.jsp
Created on 		 : Sep 15, 2016, 10:14:12 AM
Author     		 : BookBattery
Copyright Notice : BookBattery Confidential.
Document         : This jsp is used as Choose Car Battery Jsp page.
--%>

<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.util.Hashtable,java.io.File,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Enumeration,java.util.Properties,java.io.FileInputStream,javax.servlet.ServletContext"%>
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
          <li class="category13"><strong>Choose Car Battery</strong></li>
        </ul>
      </div>
    </div>
  </div>
  <!-- End breadcrumbs --> 
  <section class="main-container col1-layout">
    <div class="main container">
      <div class="col-main">
        <div class="cart wow">
          <div class="page-title">
            <h2>How to Choose your Car Battery</h2>
          </div>
          <div class="delivery">
        <div class="row">
			<div class="">
	 <div class="tab-content">
        <div>
		<br>
			<div class="col-md-12" style=" /* float: right; */ text-align: right;">
                <a href="/bookbattery/Car-Batteries/"><input name="BUY NOW" value="BUY NOW"class="button btn-cart" type="button"></a>
			 </div>
			<br>
			<p style="text-align:justify">
				<br>
				<br>
					Imagine you are out with your friends, driving your beloved car when you stop for a photo session. After a score of selfies you get back in your car and it just refuses to start, the low battery warning sign starts flashing on your dashboard. You go back a few days when you went out to buy your car battery. You were not really sure which one to buy and then you twist the key for another futile attempt to crank the car. Well, we now have compiled a checklist that will help you zero down on the most appropriate battery for your wheels so that your crazy night out goes on to be memorable. 
				</p>

                <p style="text-align:justify">
					The car battery is an integral and important part of a complex system that has been designed and engineered to power your set of wheels and that is the reason why not just any battery would be able to keep your car running hassle-free. To choose the right battery for your car, you need to know the basic nomenclature for BookBattery. The basic parameters are Ampere-Hour (Ah) rating, Cranking amps (CA), Cold Cranking amps (CCA) and Reserve Capacity (RC). The voltage for which the electrical systems for cars are designed is standardised to 12V while it may differ in case of motorcycles. You have 9V systems for old motorcycles but that too has mostly been standardised to 12V.
				</p>
				
				
				
				<br>
				<p ><img src="/bookbattery/images/choosebattery1.jpg" style="width: 250px;"></p>
				<h2> Learn what battery size you need for your car's make and model.</h2>  
				<h4>Step-1 : Consult you car's owner's manual. The manual usually specifies the battery size you should buy.</h4>
				<p ><img src="/bookbattery/images/choosebattery2.jpg" style="width: 250px;"></p>
				<h4>Step-2 : Ask an auto supply store employee to help you figure out the correct battery size.</h4>
				<p ><img src="/bookbattery/images/choosebattery3.jpg" style="width: 250px;"></p>
				
				<h4>Step-3 : Pick the correct size and type of battery for your driving needs.</h4>
				<h5>Keep driving needs and climate in mind and check your owner's manual for the correct physical size. Consider group size, which refers to the outside dimensions of the battery and the placement of the terminals. If you get a battery that is too small, it won't fit securely in the battery compartment of your car.</h5>
				<h5>High temperatures are hard on car BookBattery. The electrolyte solution in car BookBattery evaporates more rapidly in hot climates.A battery with a long life is important if your daily driving habits are primarily short, stop-and-start trips. Short trips don't allow very much time for your battery to recharge. A battery with a long life is better able to withstand those shorter trips.</h5>
				<p ><img src="/bookbattery/images/choosebattery4.jpg" style="width: 250px;"></p>
				<h4>Step-4 : Look for a battery that has been on the store shelf for less than 6 months.</h4>
				<h5>The date stamp code gives you the battery's freshness information. The first 2 characters are a letter and a digit--A stands for January, B for February, etc.; the digit specifies the year the battery was manufactured--7 stands for 2007, 9 for 2009. The date code is engraved into the cover of the battery. You can find it as you look down at the top of the battery.</h5>
				<p ><img src="/bookbattery/images/choosebattery5.jpg" style="width: 250px;"></p>


				<h4>Step-5 : Ask about "cold cranking amps" (CCA) and "cranking amps" (CA).These 2 terms are critical, especially if you live in a colder climate.</h4>
				<h5>CCA indicates a battery's ability to start a car in 0 degree F (-17 C). CCA also tells you how much current the battery delivers to your car's starter.</h5>
				<h5>CA tells you how much current your battery delivers to your car when temperatures are 32 degrees F (0 C). This rating is usually higher than the CCA.</h5>

				<h4>Step-6 : Inquire about reserve capacity for the BookBattery that are in stock.</h4>
				<h5>Reserve capacity measures how many minutes the battery may run using its power alone. You need to know the reserve capacity in case your car's alternator should fail.</h5>
				<p ><img src="/bookbattery/images/choosebattery6.jpg" style="width: 250px;"></p>

				<h4>Step-7 : Check the difference between maintenance free (sealed) and low maintenance BookBattery.</h4>
				<h5>Maintenance free BookBattery do not need to have water added to them.</h5>
				<p ><img src="/bookbattery/images/choosebattery7.jpg" style="width: 250px;"></p>

				<h5>Low maintenance BookBattery are unsealed and have caps on top that allow you to add water--an important consideration if you live in a hot climate.</h5>
				<p ><img src="/bookbattery/images/choosebattery8.jpg" style="width: 250px;"></p>

				<p style="text-align:justify">
					The Ampere-hour rating will tell you for how long the battery be able to provide current of the particular amperage. Higher the Ah rating, more powerful the battery. When the engine is being cranked, it needs a surge of high amperage current for a short time to beat the inertia of the static engine to get it rolling and firing. The Cranking amp rating of the battery denotes the peak current a battery can deliver for thirty seconds at 0 °C – basically the firepower of the battery in quick bursts. Next we have the Cold Cranking Amps (CCA) which is nothing but the cranking amperes but at –18 °C. The CA and CCA are important if you live in regions where the mercury dips quite a bit. When the engine is cold, it needs a lot of energy to get it rolling as compared to when it is warm around. So, a good CA and CCA will ensure a smooth hassle free cold start. There are a lot of car/motorcycle owners in India that do not get to regularly drive their machines and most of the times the battery gets weakened or discharged. A battery with higher Ah and CCA should help you a bit, but then, to keep your car/motorcycle and engine healthy, it is advisable to start your car at least once in two to three days and take it out for a spin once a week. 
				</p>

				<p style="text-align:justify">
					Now that we know the terminologies, let us go ahead to choose the right battery for your machine.
					• Every vehicle is sold along with the Owner’s Manual which has information about all the standard parts the car needs for regular maintenance. You can find the battery dimensions and the ampere hour ratings, the CA, CCA ratings there. If you do not have the Owners’ Manual, you can check the top or the body of the existing battery for all the required ratings.
					• Never choose a battery that has Ah rating lower than your existing battery or as mentioned in the manual. It will give you starting trouble, especially when it comes to cold starts. Also, the battery life will be affected.
					• A battery with higher Ah rating will work, but if you choose a much higher rating battery, it will lead to slow charging and load on the alternator.
					• You have to choose a battery with higher CCA if you live in climatic conditions where the temperature gets considerably low.
					• A battery with good Reserve Capacity shall come handy in case the alternator of your car fails, to keep the car and lamps running for a longer period of time.
					• Do not forget to check the battery dimensions. Most battery mounts/battery boxes are standardised, but it is always good to check on the size if it fits your car snugly.
					• Also, if you plan to maintain your own car, buying a battery with handles/loops will be convenient.
					• Most commonly available BookBattery are Lead Acid BookBattery and Dry Cell BookBattery. Dry cells are slightly more expensive but have a longer life than lead acid BookBattery. Average car battery life in India is about 3-4 years.
					• When you buy a battery, you need to check the manufacturing date which will be mentioned on the battery. In case the date is not mentioned, there will be a code that indicates the date of manufacture. (A – M except I for Jan – Dec and 15 for 2015). 
					• A battery purchased within 6 months of manufacture is considered as fresh. 
					• Keep the Warranty Card safe just in case the battery gives way in the warranty period to avail replacements. 
				</p>
     </div>
	</div>
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
