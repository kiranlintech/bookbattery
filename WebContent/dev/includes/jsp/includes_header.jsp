<%-- 
Document   		 : product_details.html
Created on 		 : Sep 15, 2016, 10:14:12 AM
Author     		 : BookBattery
Copyright Notice : BookBattery Confidential.
Document         : This jsp is used as header in all consumer pages of BookBattery Batteries.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Enumeration,java.util.Properties,java.io.FileInputStream,javax.servlet.ServletContext,com.ngit.javabean.consumers.products.GetCookie,com.ngit.javabean.qrymgr.QueryManager"%>
<%
Properties propsMOPConfig = new Properties();
ServletContext context = getServletContext();
FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
propsMOPConfig.load(fin1);
fin1.close();
String domainname = (propsMOPConfig.getProperty("domainname")!=null)?propsMOPConfig.getProperty("domainname"):"";
String publicUrl = (propsMOPConfig.getProperty("publicUrl")!=null)?propsMOPConfig.getProperty("publicUrl"):"";
QueryManager qm;
qm = QueryManager.getInstance(propsMOPConfig);
//################### Getting Location From Cookies 
GetCookie State_City = new GetCookie(qm);
String State_City_Resp=  State_City.getCookieStateCity(request,response,session);
LogLevel.DEBUG(5, new Throwable(), "State_City_Resp :" + State_City_Resp);

String[] State_City_Arr=State_City_Resp.split("~");
String Product_State_Cookie=State_City_Arr[0];
String Product_City_Cookie=State_City_Arr[1];
//################### Getting Location From Cookies 
String Product_State_Cookie_URL= Product_State_Cookie.replaceAll(" ", "-");
String Product_City_Cookie_URL= Product_City_Cookie.replaceAll(" ", "-");
			
%>

<input type="hidden" name="domainname" id='domainname' value="<%=domainname%>" />
<input type="hidden" name="publicUrl" id='publicUrl' value="<%=publicUrl%>" />
<!-- load code starts  -->
<div id="Loading_bar" style="display:none;">
	<div class="loader">
	  <div class="loader__figure"></div>
	  </br>
	  <h3 style="position: absolute;top: 50%;left: 50%;-webkit-transform: translate(-50%, -50%);-moz-transform: translate(-50%, -50%);-ms-transform: translate(-50%, -50%);-o-transform: translate(-50%, -50%);transform: translate(-50%, -50%);color: #333232;padding-top: 22px;"> Loading.. Please wait.</h3>
	</div>
</div>
<div style="position: fixed;left: 15px;bottom: 14px;z-index: 9999999;">
<a href="https://wa.me/919603467559" title="BookBattery Whatsapp"><img style="width: 50px;" alt="BookBattery Logo" src="<%=publicUrl%>/bookbattery/images/whatsapp_icon.png"></a>
</div>

  <!-- Header -->

  <header class="header">
<!-- Fixed navbar -->
    <div class="navbar-fixed-top custom_fixed_nav">
      <div class="container">
		<a onclick="AskLocationDetails()" href="javascript:void(0)">
			<span class="icon-map-marker" style="font-size: 16px;"> </span>	<span> Current Location : <b id="product_city_top_nav"><%=Product_City_Cookie%></b>, <b id="product_state_top_nav"><%=Product_State_Cookie%></b>... <span style="font-size: 11px;"> Change</span></span>
		</a>
      </div>
    </div>
    <div class="container">
      <div class="row">
        <div class="col-lg-12 col-sm-12 col-md-12" style="padding-top: 10px;">
			<div class="col-lg-2 col-sm-4 col-md-2 col-xs-3 hidden-lg hidden-md" style=" padding: 0px;">
			  <!-- Header Logo -->
			  <a class="logo" title="BookBattery Home" href="<%=publicUrl%>/bookbattery/"><img alt="BookBattery Logo" src="<%=publicUrl%>/bookbattery/images/BookBattery_logo.png"></a>
			  <!-- End Header Logo -->
			</div>
			<div class="col-lg-2 col-sm-4 col-md-2 col-xs-3 hidden-xs hidden-sm" style=" padding: 3px;">
			  <!-- Header Logo -->
			   <a class="logo" title="BookBattery Home" href="<%=publicUrl%>/bookbattery/"><img alt="BookBattery Logo" src="<%=publicUrl%>/bookbattery/images/BookBattery_logo.png"></a>
			  <!-- End Header Logo -->
			</div>
			<div class="col-lg-10 col-sm-8 col-md-10 col-xs-9 mtop" style="padding-right: 0px;">
				<div class=" col-lg-12 col-sm-12 col-md-12 col-xs-12" style="padding: 0px;font-size: 1.2em;text-align: right;" id="why_shop_with_us">
					<span><i class="icon-thumbs-up-alt"></i><strong> Why Shop With BookBattery : </strong></span>
					<span><i class="icon-user-2"></i> Free Professional Installation</span><i class="icon-ellipsis-vertical-after"></i>
					<span><i class="icon-truck-2"></i> Free and Cash On Delivery</span><i class="icon-ellipsis-vertical-after"></i>
					<span><i class="icon-gear"></i> Genuine Battery</span><i class="icon-ellipsis-vertical-after"></i>
					<span><i class="icon-heart"></i> Best Prices</span>
				</div>
				<div class="col-lg-12 col-sm-12 col-md-12 col-xs-12" style="padding:0px;">
					<div class="clearfix"></div>
					<div class="header-top"></div>
					<div class="col-sm-12 col-md-6 col-xs-12" style="  text-align: center;   font-size: 15px;"></div>
					<div class="header-mobile-text col-sm-12 col-md-12 col-xs-12 hidden-xs hidden-sm" style="margin-top: 10px; padding: 0px;">
					<div class="col-sm-12 col-md-6 col-xs-12" id="go_to_review" style="text-align: -webkit-right;margin-top: 10px;"><b><a class="cutomer_reviews" onclick="window.location.href='http://bit.ly/32fk0Dd'" style="font-size: 21px;color: #e02d29;cursor: pointer;">Our Customer Reviews</a></b></div>Order By Call or WhatsApp <a href="https://wa.me/919603467559" title="BookBattery Whatsapp"><img alt="BookBattery Logo" src="<%=publicUrl%>/bookbattery/images/whatsapp_icon.png" style="width: 25px;"></a> 9 AM to 6:30 PM (Mon - Sat)<div class="header-mobile-no"> &nbsp;<a href="tel:+919603467559" style="color: #e02d29;">89194 01115</a></div>
					</div>
					<div class="header-mobile-text col-sm-12 col-md-6 col-xs-12 hidden-lg hidden-md" style="padding: 5px;">
						Order By Call or WhatsApp <a href="https://wa.me/919603467559" title="BookBattery Whatsapp"><img alt="BookBattery Logo" src="<%=publicUrl%>/bookbattery/images/whatsapp_icon.png" style="width: 15px;"></a> 9 AM to 6:30 PM (Mon - Sat)<b class="header-mobile-no"> &nbsp;<a href="tel:+919603467559">89194 01115</a></b>
					</div>
				</div>
			</div>
		</div>
		</div>
		
      <div class="col-sm-12 col-md-6 col-xs-12 hidden-lg hidden-md" style="text-align:center;margin-top: 5px;margin-bottom: 3px;"><b><a style="cursor: pointer;text-align: center;color: #e02d29;font-size: 12px;margin-top: 5px;">Our Services</a></b>
</div>
<div class="col-sm-12 col-md-6 col-xs-12 hidden-lg hidden-md">
<div class="col-sm-3 col-md-3 col-xs-3 hidden-lg hidden-md" style="text-align: -webkit-center"><a href="<%=publicUrl%>/bookbattery/battery-health-checkup-@-599/Health Check"><img class="width_100_class" src="/bookbattery/images/batteryservice.png" alt="Battery Service"></a><a href="<%=publicUrl%>/bookbattery/battery-health-checkup-@-599/Health Check" style="font-size: 9px;font-weight: 600;">Battery Service</a>
</div>
<div class="col-sm-3 col-md-3 col-xs-3 hidden-lg hidden-md" style="text-align: -webkit-center"><a href="<%=publicUrl%>/bookbattery/battery-health-checkup-@-599/Recharge"><img class="width_100_class" src="/bookbattery/images/batteryrecharge.png" alt="Battery Recharge"></a><a href="<%=publicUrl%>/bookbattery/battery-health-checkup-@-599/Recharge"style="font-size: 9px;font-weight: 600;">Battery Recharge</a>
</div>
<div class="col-sm-3 col-md-3 col-xs-3 hidden-lg hidden-md" style="text-align: -webkit-center"><a href="<%=publicUrl%>/bookbattery/battery-health-checkup-@-599/Jump Start"><img class="width_100_class" src="/bookbattery/images/jumpstart.png" alt="Jump Start"></a><a href="<%=publicUrl%>/bookbattery/battery-health-checkup-@-599/Jump Start" style="font-size: 9px;font-weight: 600;">Jump Start</a>
</div>
<div class="col-sm-3 col-md-3 col-xs-3 hidden-lg hidden-md" style="text-align: -webkit-center"><a class="bat_replace"><img class="width_100_class" src="/bookbattery/images/batteryreplacement.png" alt="Battery Replacement"></a><a class="bat_replace" style="font-size: 9px;font-weight: 600;">Battery Replacement</a>
</div>
</div>

      </div>
    </div>
  </header>
  <!-- end header -->
  <!-- Navbar -->
  
  <nav>
    <div class="container">
      <div class="nav-inner">
        <!-- mobile-menu -->
        <div class=" " id="mobile-menu">
          <ul class="navmenu">
            <li>
              <div class="menutop">
                <div class="toggle"> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span></div>
                <h2 class="title_menu">Menu</h2>
              </div>
              <ul style="display:none;" class="submenu">
                <li>
                  <ul class="topnav">
				  <li class="level0 nav-6 level-top first parent"> <a class="level-top" href="<%=publicUrl%>/bookbattery/"> <span>Home</span> </a></li>
				<li class="level0 nav-6 level-top first parent"> <a class="level-top" href="javascript:void(0)"> <span>Select By Car Brands</span> </a>
				  <ul class="level0">
					<li class="level1"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Maruti-Suzuki/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"><span>Maruti Suzuki</span></a> </li>
					<li class="level1"><a  href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Hyundai/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" ><span>Hyundai</span></a> </li>
					<li class="level1"><a  href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Tata/<%=
					Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"><span>Tata</span></a> </li>
					<li class="level1"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Mahindra-and-Mahindra/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"><span>Mahindra and Mahindra</span></a> </li>
					<li class="level1"><a  href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Toyota/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"><span>Toyota</span></a> </li>
					<li class="level1"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Honda/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" ><span>Honda</span></a> </li>
					<li class="level1 first"><a  href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Alfa-Romeo/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" ><span>Alfa Romeo</span></a></li>
					<li class="level1 nav-10-2"> <a  href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Audi/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" > <span>Audi</span> </a> </li>
					<li class="level1 nav-10-2"> <a  href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/BMW/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" > <span>BMW India</span> </a> </li>
					<li class="level1 nav-10-3"> <a  href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Chevrolet/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" > <span>Chevrolet</span> </a> </li>
					<li class="level1 nav-10-4"> <a  href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Daewoo/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" > <span>Daewoo</span> </a> </li>
					<li class="level1 nav-10-4"> <a  href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Fiat/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" > <span>Fiat</span> </a> </li>
					<li class="level1"> <a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Force-Motors/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" > <span>Force Motors</span> </a> </li>
					<li class="level1"> <a  href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Ford/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Ford</span> </a> </li>
					<li class="level1"> <a  href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/GM-OPEL/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" > <span>GM OPEL</span> </a> </li>
					<li class="level1"><a  href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Hindustan-Motors/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" ><span>Hindustan Motors</span></a> </li>
					<li class="level1"><a  href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/ICML/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" ><span>ICML</span></a> </li>
					<li class="level1"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Jaugar/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" ><span>Jaugar</span></a> </li>
					<li class="level1"><a  href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/KIA-Motors/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"><span>KIA Motors</span></a> </li>
					<li class="level1"><a  href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Mercedes-Benz/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"><span>Mercedes Benz</span></a> </li>
					<li class="level1"><a  href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Nissan/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"><span>Nissan</span></a> </li>
					<li class="level1"><a  href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Peuguot/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"><span>Peuguot</span></a> </li>
					<li class="level1"><a  href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Porsche/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"><span>Porsche</span></a> </li>
				  </ul>
				</li>
					<li class="level0 nav-6 level-top first parent"> <a class="level-top" href="javascript:void(0)"> <span>Select By Bike Brands</span> </a>
					<ul class="level0">
					
					<li class="level1 nav-10-2"> <a href="<%=publicUrl%>/bookbattery/fueltype/Bike-Batteries/Hero-MotoCorp/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Hero MotoCorp</span> </a> </li>
					<li class="level1 nav-10-3"> <a  href="<%=publicUrl%>/bookbattery/fueltype/Bike-Batteries/Honda/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Honda</span> </a> </li>
					<li class="level1 first"><a href="<%=publicUrl%>/bookbattery/fueltype/Bike-Batteries/Bajaj/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" ><span>Bajaj</span></a></li>
                    <li class="level1"><a href="<%=publicUrl%>/bookbattery/fueltype/Bike-Batteries/Yamaha/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"><span>Yamaha</span></a> </li>
                    <li class="level1"> <a href="<%=publicUrl%>/bookbattery/fueltype/Bike-Batteries/Royal-Enfield/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Royal Enfield</span> </a> </li>
                    <li class="level1"><a href="<%=publicUrl%>/bookbattery/fueltype/Bike-Batteries/TVS/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"><span>TVS</span></a> </li>
                    <li class="level1 nav-10-4"> <a  href="<%=publicUrl%>/bookbattery/fueltype/Bike-Batteries/Kandaa-Motors/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Kandaa Motors</span> </a> </li>
					<li class="level1 first"><a href="<%=publicUrl%>/bookbattery/fueltype/Bike-Batteries/Kinetic/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"><span>Kinetic</span></a> </li>
					<li class="level1 nav-10-4"> <a  href="<%=publicUrl%>/bookbattery/fueltype/Bike-Batteries/LML/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>LML</span> </a> </li>
					<li class="level1"> <a href="<%=publicUrl%>/bookbattery/fueltype/Bike-Batteries/Mahindra-and-Mahindra/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Mahindra and Mahindra</span> </a> </li>
					<li class="level1"> <a href="<%=publicUrl%>/bookbattery/fueltype/Bike-Batteries/Piaggio/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Piaggio</span> </a> </li>
					
					<li class="level1"><a href="<%=publicUrl%>/bookbattery/fueltype/Bike-Batteries/Suzuki/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"><span>Suzuki</span></a> </li>
					
					
				  </ul>
				</li>
				<li class="level0 nav-6 level-top first parent">
					<a tabindex="-1" href="javascript:void(0)">Inverter Batteries</a>
					<ul class="level0">
						<li class="level0 nav-6 level-top first parent"> <a class="level-top" href="javascript:void(0)"> <span>Shop By Battery Brand</span> </a>
							<ul class="level0">
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/Amaron/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Amaron</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/Exide/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Exide</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/Luminous/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Luminous</span> </a> </li>
							</ul>
						</li>
						<li class="level0 nav-6 level-top first parent"> <a class="level-top" href="javascript:void(0)"> <span>Shop By Battery Capacity</span> </a>
							<ul class="level0">
							<li><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=100+Ah/"> <span>100 Ah</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=120+Ah/"> <span>120 Ah</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=135+Ah/"> <span>135 Ah</span> </a> </li>
							<li class="hide"><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=145+Ah/"> <span>145 Ah</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=150+Ah/"> <span>150 Ah</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=165+Ah/"> <span>165 Ah</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=170+Ah/"> <span>170 Ah</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=180+Ah/"> <span>180 Ah</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=200+Ah/"> <span>200 Ah</span> </a> </li>
							<li class="hide"><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=220+Ah/"> <span>220 Ah</span> </a> </li>
							</ul>
						</li>
						<li class="level0 nav-6 level-top first parent"> <a class="level-top" href="javascript:void(0)"> <span>Shop By Battery Layout</span> </a>
							<ul class="level0">
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity/Type=Flat+Plate+Battery/"> <span>Flat Plate Battery</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity/Type=Tubular+Battery/"> <span>Tubular Battery</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity/Type=Tall+Tubular+Battery/"> <span>Tall Tubular Battery</span> </a> </li>
							</ul>
						</li>
					</ul>
				</li>
				<li class="level0 nav-6 level-top first parent"> <a class="level-top" href="javascript:void(0)"> <span>Inverters and Home Ups</span> </a>
					<ul class="level0">
						<li class="level0 nav-6 level-top first parent"> <a class="level-top" href="javascript:void(0)"> <span>Shop By Inverters Brand</span> </a>
							<ul class="level0">
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/Amaron/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Amaron</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/Exide/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Exide</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/Luminous/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Luminous</span> </a> </li>
							</ul>
						</li>
						<li class="level0 nav-6 level-top first parent"> <a class="level-top" href="javascript:void(0)"> <span>Shop By Inverters Capacity</span> </a>
							<ul class="level0">
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=400+VA/"> <span>400 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=650+VA/"> <span>650 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=675+VA/"> <span>675 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=700+VA/"> <span>700 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=825+VA/"> <span>825 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=850+VA/"> <span>850 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=880+VA/"> <span>880 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=900+VA/"> <span>900 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=1250+VA/"> <span>1250 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=1400+VA/"> <span>1400 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=1450+VA/"> <span>1450 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=1500+VA/"> <span>1500 VA</span> </a> </li>
								<li class="hide"><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=1650+VA/"> <span>1650 VA</span> </a> </li>
								<li class="hide"><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=1700+VA/"> <span>1700 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=2+KVA/"> <span>2 KVA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=3.5+KVA/"> <span>3.5 KVA</span> </a> </li>
							</ul>
						</li>
						<li class="level0 nav-6 level-top first parent"> <a class="level-top" href="javascript:void(0)"> <span>Shop By Inverters Output Type</span> </a>
							<ul class="level0">
							<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity/OutputType=Pure+Sine+Wave/"> <span>Pure Sine Wave</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity/OutputType=Sinewave/"> <span>Sinewave</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity/OutputType=Square+Wave/"> <span>Square Wave</span> </a> </li>
							</ul>
						</li>
						<li class="level0 nav-6 level-top first parent"><a href="<%=publicUrl%>/bookbattery/configure-your-inverter-and-battery.jsp"><span>Configure Your Inverter</span> </a></li>
					</ul>
				</li>
						
				<li class="level0 nav-6 level-top first parent">
					<a tabindex="-1" href="javascript:void(0)">Inverter and Battery Combo</a>
					<ul class="level0">
						<li class="level0 nav-6 level-top first parent"> <a class="level-top" href="javascript:void(0)"> <span>Shop By Inverters and Battery Brand</span> </a>
							<ul class="level0">
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/Amaron/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Amaron</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/Exide/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Exide</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/Luminous/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Luminous</span> </a> </li>
							</ul>
						</li>
						<li class="level0 nav-6 level-top first parent"> <a class="level-top" href="javascript:void(0)"> <span>Shop By Inverters Capacity</span> </a>
							<ul class="level0">
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=400+VA/"> <span>400 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=650+VA/"> <span>650 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=675+VA/"> <span>675 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=700+VA/"> <span>700 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=825+VA/"> <span>825 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=850+VA/"> <span>850 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=880+VA/"> <span>880 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=900+VA/"> <span>900 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=1250+VA/"> <span>1250 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=1400+VA/"> <span>1400 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=1450+VA/"> <span>1450 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=1500+VA/"> <span>1500 VA</span> </a> </li>
								<li class="hide"><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=1650+VA/"> <span>1650 VA</span> </a> </li>
								<li class="hide"><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=1700+VA/"> <span>1700 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=2+KVA/"> <span>2 KVA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=3.5+KVA/"> <span>3.5 KVA</span> </a> </li>
							</ul>
						</li>
						<li class="level0 nav-6 level-top first parent"> <a class="level-top" href="javascript:void(0)"> <span>Shop By Battery Capacity</span> </a>
							<ul class="level0">
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity/Battery-Capacity=100+Ah/"> <span>100 Ah</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=1250+VA,1400+VA,1450+VA,1500+VA,1650+VA,1700+VA,2+KVA,3.5+KVA,650+VA,675+VA,700+VA,825+VA,850+VA,880+VA,900+VA/Battery-Capacity=135+Ah/"> <span>135 Ah</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=1250+VA,1400+VA,1450+VA,1500+VA,1650+VA,1700+VA,2+KVA,3.5+KVA,650+VA,675+VA,700+VA,825+VA,850+VA,880+VA,900+VA/Battery-Capacity=150+Ah/"> <span>150 Ah</span> </a> </li>
								<li class="hide"><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=1250+VA,1400+VA,1450+VA,1500+VA,1650+VA,1700+VA,2+KVA,3.5+KVA,880+VA,900+VA/Battery-Capacity=180+Ah/"> <span>180 Ah</span> </a> </li>
							</ul>
						</li>
						<li class="level0 nav-6 level-top first parent"><a href="<%=publicUrl%>/bookbattery/configure-your-inverter-and-battery.jsp"><span>Configure Your Inverter</span> </a></li>
					</ul>
				</li>
				<li class="level0 nav-6 level-top first parent"> <a class="level-top" href="javascript:void(0)"> <span>Other Categories </span> </a>
				  <ul class="level0">
					<li class="level2 nav-6-4-15 first"> <a href="<%=publicUrl%>/bookbattery/Three-Wheeler-Batteries/"> <span>Three Wheeler Batteries</span> </a> </li>
					<li class="level2 nav-6-4-15"> <a href="<%=publicUrl%>/bookbattery/Bus-Batteries/"> <span>Bus Batteries</span> </a> </li>
					<li class="level2 nav-6-4-15"> <a href="<%=publicUrl%>/bookbattery/Genset-Batteries/"> <span>Genset Batteries</span> </a> </li>
					<li class="level2 nav-6-4-16"> <a href="<%=publicUrl%>/bookbattery/Tractor-Batteries/"> <span>Tractor Batteries</span> </a> </li>
					<li class="level2 nav-6-4-17"> <a href="<%=publicUrl%>/bookbattery/Truck-Batteries/"> <span>Truck Batteries</span> </a> </li>
					<li class="level2 nav-6-4-17"> <a href="<%=publicUrl%>/bookbattery/Special-Vehicle-Batteries/"> <span>Special Vehicle Batteries</span> </a> </li>
					<li class="level2 nav-6-4-17"> <a href="<%=publicUrl%>/bookbattery/Crane-Batteries/"> <span>Crane Batteries</span> </a> </li>
					<li class="level2 nav-6-4-17"> <a href="<%=publicUrl%>/bookbattery/Roller-Batteries/"> <span>Roller Batteries</span> </a> </li>
					<li class="level2 nav-6-4-17"> <a href="<%=publicUrl%>/bookbattery/Loader-Batteries/"> <span>Loader Batteries</span> </a> </li>
					<li class="level2 nav-6-4-17"> <a href="<%=publicUrl%>/bookbattery/Excavator-Batteries/"> <span>Excavator Batteries</span> </a> </li>
					<li class="level2 nav-6-4-17"> <a href="<%=publicUrl%>/bookbattery/Tyre-Handler-Batteries/"> <span>Tyre Handler Batteries</span> </a> </li>
					<li class="level2 nav-6-4-16"> <a href="<%=publicUrl%>/bookbattery/Dozer-Batteries/"> <span>Dozer Batteries</span> </a> </li>
					<li class="level2 nav-6-4-13"> <a href="<%=publicUrl%>/bookbattery/Harvestor-Batteries/"> <span>Harvestor Batteries</span> </a> </li>
					<li class="level2 nav-6-4-15"> <a href="<%=publicUrl%>/bookbattery/Generator-Batteries/"> <span>Generator Batteries</span> </a> </li>
					<li class="level2 nav-6-4-15"> <a href="<%=publicUrl%>/bookbattery/Compactor-Batteries/"> <span>Compactor Batteries</span> </a> </li>
					<li class="level2 nav-6-4-15"> <a href="<%=publicUrl%>/bookbattery/Telescopic-Handler-Batteries/"> <span>Telescopic Handler Batteries</span> </a> </li>
					<li class="level2 nav-6-4-16"> <a href="<%=publicUrl%>/bookbattery/Forwarder-Batteries/"> <span>Forwarder Batteries</span> </a> </li>
					<li class="level2 nav-6-4-17"> <a href="<%=publicUrl%>/bookbattery/Construction-Equipment-Batteries/"> <span>Construction Equipment Batteries</span> </a> </li>
					<li class="level2 nav-6-4-17"> <a href="<%=publicUrl%>/bookbattery/Hydralic-Excavator-Batteries/"> <span>Hydralic Excavator Batteries</span> </a> </li>
					<li class="level2 nav-6-4-17"> <a href="<%=publicUrl%>/bookbattery/Dumper-Batteries/"> <span>Dumper Batteries</span> </a> </li>
					<li class="level2 nav-6-4-17"> <a href="<%=publicUrl%>/bookbattery/Roller-Batteries/"> <span>Roller Batteries</span> </a> </li>
					<li class="level2 nav-6-4-17"> <a href="<%=publicUrl%>/bookbattery/Loader-Batteries/"> <span>Loader Batteries</span> </a> </li>
					<li class="level2 nav-6-4-17"> <a href="<%=publicUrl%>/bookbattery/Excavator-Batteries/"> <span>Excavator Batteries</span> </a> </li>
					<li class="level2 nav-6-4-17"> <a href="<%=publicUrl%>/bookbattery/Tyre-Handler-Batteries/"> <span>Tyre Handler Batteries</span> </a> </li>
					<li class="level2 nav-6-4-16 last"> <a href="<%=publicUrl%>/bookbattery/Hydraulic-Shovel-Batteries/"> <span>Hydraulic Shovel Batteries</span> </a> </li>
				  </ul>
				</li>
			<li class="level0 nav-6 level-top first parent"><a href="<%=publicUrl%>/bookbattery/battery-health-checkup-@-599/Health Check"><span>Book Battery Service</span> </a></li>
			<li class="level0 nav-6 level-top first parent"><a href="<%=publicUrl%>/bookbattery/battery-health-checkup-@-599/Recharge"><span>Book Battery Recharge</span> </a></li>
			<li class="level0 nav-6 level-top first parent"><a href="<%=publicUrl%>/bookbattery/battery-health-checkup-@-599/Jump Start"><span>Book Jump Start</span> </a></li>
			<li class="level0 nav-6 level-top first parent"><a href="<%=publicUrl%>/bookbattery/configure-your-inverter-and-battery.jsp"><span>Configure Your Inverter</span> </a></li>
			<!-- <li class="level0 nav-6 level-top first parent"><a href="<%=publicUrl%>/bookbattery/checkwarranty.jsp"><span>Check Battery Warranty</span> </a></li>
			<li class="level0 nav-6 level-top first parent"><a href="<%=publicUrl%>/bookbattery/registerbattery.jsp"><span>Register Your Battery</span> </a></li>-->
			
			  </ul>
			</li>
              </ul>
            </li>
          </ul>
          <!--navmenu-->
        </div>
        <!--End mobile-menu -->
        <ul id="nav" class="desktop_menu_Custom">
          <li id="nav-home" class="level0 parent drop-menu active"><a href="<%=publicUrl%>/bookbattery/index.jsp"><span>Home</span> </a>
          </li> 
		  <li class="level0 parent dropdown dropdown-2-open">
		  <a id="dLabel" role="button" data-toggle="dropdown" class="btn" data-target="#" href="javascript:void(0)" title="Click to See Category">
           <span> Shop by Category</span> <span class="icon-sort-down"></span>
          </a>
    		<ul class="dropdown-menu dropdown-menu-2 multi-level" role="menu" aria-labelledby="dropdownMenu" style=" min-width: 250px; font-size: 15px;">
              <li class="dropdown-submenu">
                <a tabindex="-1" href="<%=publicUrl%>/bookbattery/manufacturers/Car-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Car Batteries</a>
                <ul class="dropdown-menu home_drop_down_menu">
					
				<div class="col-sm-12">
					<div class="col-sm-4">
						<h5>Shop By Battery Brand</h5>
						<!--<li><a tabindex="-1" href="<%=publicUrl%>/bookbattery/manufacturers/Car-Batteries/Amaron/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Amaron</span> </a> </li>
						<li><a tabindex="-1" href="<%=publicUrl%>/bookbattery/manufacturers/Car-Batteries/Exide/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Exide</span> </a> </li>
						<li><a tabindex="-1" href="<%=publicUrl%>/bookbattery/manufacturers/Car-Batteries/Luminous/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Luminous</span> </a> </li>-->
						<li><a tabindex="-1" href="<%=publicUrl%>/bookbattery/BatteryBrand/Amaron/Car-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Amaron</span> </a> </li>
						<li><a tabindex="-1" href="<%=publicUrl%>/bookbattery/BatteryBrand/Exide/Car-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Exide</span> </a> </li>
					</div>
					<div class="col-sm-8">
						<h5 class="col-sm-12">Shop by Car Manufacturers</h5>
						<div class="col-sm-4">
							<li><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Maruti-Suzuki/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Maruti Suzuki</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Hyundai/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Hyundai</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Audi/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Audi</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Mahindra-and-Mahindra/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Mahindra and Mahindra</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Toyota/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Toyota</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Honda/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Honda</span> </a></li>
							<li><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Alfa-Romeo/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Alfa Romeo</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/BMW/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>BMW India</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Chevrolet/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Chevrolet</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Daewoo/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Daewoo</span> </a> </li>
						</div> 
						<div class="col-sm-4">
							<li><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Fiat/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Fiat</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Force-Motors/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Force Motors</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Ford/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Ford</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/GM-OPEL/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>GM OPEL</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Hindustan-Motors/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Hindustan Motors</span> </a> </li>							
							<li><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/ICML/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>ICML</span> </a> </li> 
							<li><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Jaugar/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Jaugar</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/KIA-Motors/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>KIA Motors</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Mercedes-Benz/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Mercedes Benz</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Nissan/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Nissan</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Peuguot/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Peuguot</span> </a> </li>
						</div> 
						<div class="col-sm-4">							
							<li><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Porsche/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Porsche</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Premier/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Premier</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Renault/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Renault</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Skoda/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Skoda</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Tata/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Tata</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Volks-Wagen/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Volks Wagen</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Volvo/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Volvo</span> </a> </li>
						</div>
					</div>
				</div>
                </ul>
              </li>
              <li class="dropdown-submenu">
                <a tabindex="-1" href="<%=publicUrl%>/bookbattery/Brand/Inverter-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Inverter Batteries</a>           
			   
                <ul class="dropdown-menu home_drop_down_menu">
					<div class="col-sm-12">
						<div class="col-sm-4">
							<h5>Shop By Battery Brand</h5>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/Amaron/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Amaron</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/Exide/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Exide</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/Luminous/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Luminous</span> </a> </li>
						</div>
						<div class="col-sm-4">
							<h5>Battery Capacity</h5>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=100+Ah/"> <span>100 Ah</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=120+Ah/"> <span>120 Ah</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=135+Ah/"> <span>135 Ah</span> </a> </li>
							<li class="hide"><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=145+Ah/"> <span>145 Ah</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=150+Ah/"> <span>150 Ah</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=165+Ah/"> <span>165 Ah</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=170+Ah/"> <span>170 Ah</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=180+Ah/"> <span>180 Ah</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=200+Ah/"> <span>200 Ah</span> </a> </li>
							<li class="hide"><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=220+Ah/"> <span>220 Ah</span> </a> </li>
						</div>
						<div class="col-sm-4">
							<h5>Battery Layout</h5>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity/Type=Flat+Plate+Battery/"> <span>Flat Plate Battery</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity/Type=Tubular+Battery/"> <span>Tubular Battery</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity/Type=Tall+Tubular+Battery/"> <span>Tall Tubular Battery</span> </a> </li>
						</div>
					</div>
                </ul>
              </li>
              <li class="dropdown-submenu">
                <a tabindex="-1"href="<%=publicUrl%>/bookbattery/Brand/Inverter/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Inverters and Home Ups</a>
                <ul class="dropdown-menu home_drop_down_menu">
					<div class="col-sm-12">
						<div class="col-sm-4">
							<h5>Shop By Inverters Brand</h5>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter/Amaron/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Amaron</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter/Exide/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Exide</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter/Luminous/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Luminous</span> </a> </li>
							<h5><a href="<%=publicUrl%>/bookbattery/configure-your-inverter-and-battery.jsp"><span style="color: #e02d29;">Configure Your Inverter</span> </a></h5>
						</div>
						<div class="col-sm-5">
							<h5 class="col-sm-12">Inverters Capacity</h5>
							<div class="col-sm-6">
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=400+VA/"> <span>400 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=650+VA/"> <span>650 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=675+VA/"> <span>675 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=700+VA/"> <span>700 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=825+VA/"> <span>825 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=850+VA/"> <span>850 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=880+VA/"> <span>880 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=900+VA/"> <span>900 VA</span> </a> </li>
							</div>
							<div class="col-sm-6">
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=1250+VA/"> <span>1250 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=1400+VA/"> <span>1400 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=1450+VA/"> <span>1450 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=1500+VA/"> <span>1500 VA</span> </a> </li>
								<li class="hide"><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=1650+VA/"> <span>1650 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=1700+VA/"> <span>1700 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=2+KVA/"> <span>2 KVA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity=3.5+KVA/"> <span>3.5 KVA</span> </a> </li>
							</div>
						</div>
						<div class="col-sm-3">
							<h5>Inverters Output Type</h5>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity/OutputType=Pure+Sine+Wave/"> <span>Pure Sine Wave</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity/OutputType=Sinewave/"> <span>Sinewave</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Capacity/OutputType=Square+Wave/"> <span>Square Wave</span> </a> </li>
						</div>
					</div>
                </ul>
              </li>
              <li class="dropdown-submenu">
                 <a tabindex="-1" href="<%=publicUrl%>/bookbattery/Brand/Inverter-and-Battery-Combo/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Inverter and Battery Combo</a>
                <ul class="dropdown-menu home_drop_down_menu">
					<div class="col-sm-12">
						<div class="col-sm-4">
							<h5>Shop By Inverters and Battery Brand</h5>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/Amaron/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Amaron</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/Exide/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Exide</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/Luminous/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Luminous</span> </a> </li>
							<h5><a href="<%=publicUrl%>/bookbattery/configure-your-inverter-and-battery.jsp"><span style="color: #e02d29;">Configure Your Inverter</span> </a></h5>
						</div>
						<div class="col-sm-5">
							<h5 class="col-sm-12">Inverters Capacity</h5>
							<div class="col-sm-6">
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=400+VA/"> <span>400 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=650+VA/"> <span>650 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=675+VA/"> <span>675 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=700+VA/"> <span>700 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=825+VA/"> <span>825 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=850+VA/"> <span>850 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=880+VA/"> <span>880 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=900+VA/"> <span>900 VA</span> </a> </li>
							</div>
							<div class="col-sm-6">
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=1250+VA/"> <span>1250 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=1400+VA/"> <span>1400 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=1450+VA/"> <span>1450 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=1500+VA/"> <span>1500 VA</span> </a> </li>
								<li class="hide"><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=1650+VA/"> <span>1650 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=1700+VA/"> <span>1700 VA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=2+KVA/"> <span>2 KVA</span> </a> </li>
								<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=3.5+KVA/"> <span>3.5 KVA</span> </a> </li>
							</div>
						</div>
						<div class="col-sm-3">
							<h5>Battery Capacity</h5>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity/Battery-Capacity=100+Ah/"> <span>100 Ah</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=1250+VA,1400+VA,1450+VA,1500+VA,1650+VA,1700+VA,2+KVA,3.5+KVA,650+VA,675+VA,700+VA,825+VA,850+VA,880+VA,900+VA/Battery-Capacity=135+Ah/"> <span>135 Ah</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=1250+VA,1400+VA,1450+VA,1500+VA,1650+VA,1700+VA,2+KVA,3.5+KVA,650+VA,675+VA,700+VA,825+VA,850+VA,880+VA,900+VA/Battery-Capacity=150+Ah/"> <span>150 Ah</span> </a> </li>
							<li class="hide"><a href="<%=publicUrl%>/bookbattery/Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/Inverter-Capacity=1250+VA,1400+VA,1450+VA,1500+VA,1650+VA,1700+VA,2+KVA,3.5+KVA,880+VA,900+VA/Battery-Capacity=180+Ah/"> <span>180 Ah</span> </a> </li>
						</div>
					</div>
                </ul>
              </li>
              <li class="dropdown-submenu">
                <a tabindex="-1" href="<%=publicUrl%>/bookbattery/manufacturers/Bike-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>">Bike Batteries</a>
                <ul class="dropdown-menu home_drop_down_menu">
				<div class="col-sm-12">
						<div class="col-sm-4">
						<h5>Shop By Battery Brand</h5>
						<!--<li><a tabindex="-1" href="<%=publicUrl%>/bookbattery/manufacturers/Car-Batteries/Amaron/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Amaron</span> </a> </li>
						<li><a tabindex="-1" href="<%=publicUrl%>/bookbattery/manufacturers/Car-Batteries/Exide/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Exide</span> </a> </li>
						<li><a tabindex="-1" href="<%=publicUrl%>/bookbattery/manufacturers/Car-Batteries/Luminous/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Luminous</span> </a> </li>-->
						<li><a tabindex="-1" href="<%=publicUrl%>/bookbattery/BatteryBrand/Amaron/Bike-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Amaron</span> </a> </li>
						<li><a tabindex="-1" href="<%=publicUrl%>/bookbattery/BatteryBrand/Exide/Bike-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Exide</span> </a> </li>
					</div>
					
					<div class="col-sm-8">
						<h5>Shop by Bike Manufacturers</h5>
						<div class="col-sm-4">
							<li> <a href="<%=publicUrl%>/bookbattery/fueltype/Bike-Batteries/Bajaj/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Bajaj</span> </a> </li>
							<li> <a href="<%=publicUrl%>/bookbattery/fueltype/Bike-Batteries/Hero-MotoCorp/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Hero MotoCorp</span> </a> </li>
							<li> <a href="<%=publicUrl%>/bookbattery/fueltype/Bike-Batteries/Honda/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Honda</span> </a> </li>
							<li> <a href="<%=publicUrl%>/bookbattery/fueltype/Bike-Batteries/Kandaa-Motors/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Kandaa Motors</span> </a> </li>
							<li> <a href="<%=publicUrl%>/bookbattery/fueltype/Bike-Batteries/Kinetic/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Kinetic</span> </a> </li>
						</div>
						<div class="col-sm-4">
							<li> <a href="<%=publicUrl%>/bookbattery/fueltype/Bike-Batteries/LML/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>LML</span> </a> </li>
							<li> <a href="<%=publicUrl%>/bookbattery/fueltype/Bike-Batteries/Mahindra-and-Mahindra/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Mahindra and Mahindra</span> </a> </li>
							<li> <a href="<%=publicUrl%>/bookbattery/fueltype/Bike-Batteries/Piaggio/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Piaggio</span> </a> </li>
							<li> <a href="<%=publicUrl%>/bookbattery/fueltype/Bike-Batteries/Royal-Enfield/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Royal Enfield</span> </a> </li>
							<li> <a href="<%=publicUrl%>/bookbattery/fueltype/Bike-Batteries/Suzuki/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Suzuki</span> </a> </li>
							<li> <a href="<%=publicUrl%>/bookbattery/fueltype/Bike-Batteries/TVS/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>TVS</span> </a> </li>
							<li> <a href="<%=publicUrl%>/bookbattery/fueltype/Bike-Batteries/Yamaha/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Yamaha</span> </a> </li>
						</div>
					</div>
				</div>
                </ul>
              </li>
			  <li class="divider"></li>
              <li class="dropdown-submenu">
                <a tabindex="-1" href="javascript:void(0)">Other Categories</a>
                <ul class="dropdown-menu home_drop_down_menu">
					<div class="col-sm-12">
						<h5>Shop By Other Categories</h5>
						<div class="col-sm-6">
							<li><a href="<%=publicUrl%>/bookbattery/manufacturers/Three-Wheeler-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Three Wheeler Batteries</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/manufacturers/Bus-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Bus Batteries</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/manufacturers/Genset-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Genset Batteries</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/manufacturers/Tractor-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Tractor Batteries</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/manufacturers/Truck-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Truck Batteries</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/manufacturers/Special-Vehicle-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Special Vehicle Batteries</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/manufacturers/Crane-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Crane Batteries</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/manufacturers/Roller-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Roller Batteries</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/manufacturers/Loader-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Loader Batteries</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/manufacturers/Excavator-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Excavator Batteries</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/manufacturers/Tyre-Handler-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Tyre Handler Batteries</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/manufacturers/Dozer-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Dozer Batteries</span> </a> </li>
						</div>
						<div class="col-sm-6">
							<li><a href="<%=publicUrl%>/bookbattery/manufacturers/Harvestor-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Harvestor Batteries</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/manufacturers/Generator-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Generator Batteries</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/manufacturers/Compactor-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Compactor Batteries</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/manufacturers/Telescopic-Handler-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Telescopic Handler Batteries</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/manufacturers/Forwarder-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Forwarder Batteries</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/manufacturers/Construction-Equipment-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Construction Equipment Batteries</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/manufacturers/Hydralic-Excavator-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Hydralic Excavator Batteries</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/manufacturers/Dumper-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Dumper Batteries</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/manufacturers/Roller-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Roller Batteries</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/manufacturers/Loader-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Loader Batteries</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/manufacturers/Excavator-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Excavator Batteries</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/manufacturers/Tyre-Handler-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Tyre Handler Batteries</span> </a> </li>
							<li><a href="<%=publicUrl%>/bookbattery/manufacturers/Hydraulic-Shovel-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"> <span>Hydraulic Shovel Batteries</span> </a> </li>
						</div>
					</div>
                </ul>
              </li>
            </ul>
          </li>		  
		  <!--  <li class="level0 parent drop-menu"><a href="<%=publicUrl%>/bookbattery/checkwarranty.jsp"><span>Check Battery Warranty</span> </a>
          </li>
		  <li class="level0 parent drop-menu"><a href="<%=publicUrl%>/bookbattery/registerbattery.jsp"><span>Register Your Battery</span> </a>
          </li>-->
          <li class="level0 parent drop-menu"><a href="<%=publicUrl%>/bookbattery/battery-health-checkup-@-599/Health Check"><span>Book Battery Service</span> </a></li>
          <li class="level0 parent drop-menu"><a href="<%=publicUrl%>/bookbattery/battery-health-checkup-@-599/Recharge"><span>Book Battery Recharge</span> </a></li>
          <li class="level0 parent drop-menu"><a href="<%=publicUrl%>/bookbattery/battery-health-checkup-@-599/Jump Start"><span>Book Jump Start</span> </a></li>
		  <li class="level0 parent drop-menu"><a href="<%=publicUrl%>/bookbattery/configure-your-inverter-and-battery.jsp"><span>Configure Your Inverter</span> </a>
          </li>
		  
        </ul>
      </div>
    </div>
  </nav>
  <div class="col-sm-12 col-md-6 col-xs-12 hidden-lg hidden-md" id="go_to_review_mob" style="text-align:center;margin-top: -5px;margin-bottom: 3px;"><b><a class="cutomer_reviews" onclick="window.location.href='http://bit.ly/32fk0Dd'">Our Customer Reviews</a></b></div>
  
  <!-- end nav -->
  <!-- Slider -->

