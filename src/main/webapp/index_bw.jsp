<%-- 
Document   		 : index.jsp
Created on 		 : Sep 15, 2016, 10:14:12 AM
Author     		 : Bharath Kumar
Copyright Notice : BookBattery Confidential.
Document         : This jsp is used as home pages of BookBattery Batteries.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Enumeration,java.util.Properties,java.io.FileInputStream,javax.servlet.ServletContext,com.ngit.javabean.consumers.products.GetCookie,com.ngit.javabean.qrymgr.QueryManager"%>
<%
Properties propsMOPConfig = new Properties();
ServletContext context = getServletContext();
FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
propsMOPConfig.load(fin1); 
fin1.close(); 
String publicUrl = (propsMOPConfig.getProperty("publicUrl")!=null)?propsMOPConfig.getProperty("publicUrl"):"";
String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?propsMOPConfig.getProperty("baseurldirectory"):"";


QueryManager qm;
qm = QueryManager.getInstance(propsMOPConfig) ;
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

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<!--[if IE]>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<![endif]-->
<title>BookBattery - India's Best Online Store for Car Battery, Bike Battery and Inverter Battery</title>
<meta name="og_site_name" property="og:site_name" content="BookBattery.com"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="robots" content="index, follow">
<link rel="canonical" href="https://www.BookBattery.com/"/>
<link rel="shortcut icon" href="<%=publicUrl%>/bookbattery/favicon.ico">
<meta name="description" content="BookBattery is India's Multi Branded Online Store for Car Batteries, Bike Batteries, Inverter Batteries and Inverters. Buy Car, Bike and Inverter batteries online at discounted price with free delivery and installation.">
<meta name="keywords" content="car battery, car battery online, car battery near me,Car Battery Shop Near Me, Inverter Battery Shop Near Me, Bike Battery Shop Near Me, exide battery, amaron battery, luminous battery, inverter battery, bike battery, two wheeler battery, automotive battery, inverter, BookBattery, bangalore, hyderabad, kolkata, chennai, mumbai, delhi, gurgoan, noida, ahmedabad, pune, Buy Car Battery Online">
<!-- Open Graph Data -->
<meta property="og:title" content="BookBattery | Amaron Exide online car battery and inverter battery store" />
<meta property="og:url" content="https://www.BookBattery.com" />
<meta property="og:type" content="BookBattery" />
<meta property="og:description" content="BookBattery India's Multibrand Online Store for Batteries, Inverter Batteries and Inverters. Buy car, bike, inverter batteries and inverters online at discounted price with free delivery and installation."/>
<meta property="og:image" content="https://www.BookBattery.com/bookbattery/images/bookbatterylogo.png" />
<!-- Twitter Card -->
<meta name="twitter:card" content="summary">
<meta name="twitter:title" content="BookBattery | Amaron Exide online car battery and inverter battery store">
<meta name="twitter:site" content="@BookBattery">
<meta name="twitter:description" content="Looking for a Car Battery, Inverter Battery and inverters in India. Buy Amaron, Exide & Luminious batteries online at discounted price with free delivery and installation.">
<meta name="twitter:image" content="https://www.BookBattery.com/bookbattery/images/bookbatterylogo.png">
<link rel="stylesheet" href="/bookbattery/dev/includes/css/google_api.css?v=1">
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&key=AIzaSyBNWbuRMcjdbACNBCZ7u5OMji_F-KWpesA&signed_in=true&libraries=places"></script>
<!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "dev/includes/jsp/includes_css.jsp" />
<!---################################## CSS Include Ends  ################################------>

</head>

<body>
<nav id="sidebar" style="display:none">
<div id="dismiss" class="leftarrow">
<i id="icon" class="glyphicon glyphicon-remove"></i>
</div>                
<div class="sidebar-header hide" id="banner_img">
<a href="<%=publicUrl%><%=baseurldirectory%>battery-health-checkup-@-599/">					
<img class="hidden-xs Width_100 hide" id="bat_check" src="<%=publicUrl%><%=baseurldirectory%>images/car_battery_health_check_up.png" alt="Car battery Dealer">
<img id="bat_check1" class="hidden-lg hidden-md Width_100" src="<%=publicUrl%><%=baseurldirectory%>images/car_battery_health_check_mobile_new.png" alt="UPS Battery Dealer"></a>
</div>
<div class="sidebar-header hidden-xs hide" id="banner_img1" style="margin-right: 1%;">				
<img id="sidebar_img" src="<%=publicUrl%><%=baseurldirectory%>images/car_battery_health_check_copy.png" alt="Home UPS Dealer">
</div>
</nav>
<div class="overlay hide"></div>
<div class="page">

<!---################################## Header Include Starts  ################################------>
	<jsp:include page = "dev/includes/jsp/includes_header.jsp" />
<!---################################## Header Include Ends  ################################------>
     
	<div class="container slider_XAnxerMM">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="padding-left: 15px;padding-right: 15px;">
				<div id="myCarousel" class="carousel slide" data-ride="carousel">
				  <!-- Indicators -->
				  <ol class="carousel-indicators hidden-xs">
					<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
					<li data-target="#myCarousel" data-slide-to="1"></li>
					<li data-target="#myCarousel" data-slide-to="2"></li>
					<li data-target="#myCarousel" data-slide-to="3"></li>
					<li data-target="#myCarousel" data-slide-to="4"></li>
					<li data-target="#myCarousel" data-slide-to="5"></li>
				  </ol>

				  <!-- Wrapper for slides -->
				  <div class="carousel-inner" role="listbox">										
                     
				
				    <div class="item active">
						<a class="cutomer_reviews">	
					  <img src="<%=publicUrl%><%=baseurldirectory%>images/banners/served_customer.png" alt="Inverter Battery Dealer"></a>
					</div>			
				    <div class="item">
						<a href="<%=publicUrl%><%=baseurldirectory%>battery-health-checkup-@-599/">	
					  <img src="<%=publicUrl%><%=baseurldirectory%>images/banners/BW_healthcheckupl_2018.png" alt="Bike Battery Dealer"></a>
					</div>						
					<div class="item">
					  <img src="<%=publicUrl%><%=baseurldirectory%>images/banners/EMIBanner.png" alt="BookBattery - 
					  EMI BannerUPS Dealer">
					</div>
					<div class="item">
					  <img src="<%=publicUrl%><%=baseurldirectory%>images/banners/BookBatterybannerfinal1.png" alt="UPS Shop">
					</div>
					<div class="item">
					  <img src="<%=publicUrl%><%=baseurldirectory%>images/banners/Why-shop-with-BookBattery.jpg" alt="Home UPS Shop">
					</div>

					<div class="item">
					  <img src="<%=publicUrl%><%=baseurldirectory%>images/Exide_Batteries.png"  alt="Car Battery Shop" >
					</div>

					<div class="item">
					  <img src="<%=publicUrl%><%=baseurldirectory%>images/BookBattery_Amaron.png" alt="Inverter Battery Shop">
					</div>

					<div class="item">
					  <img  src="<%=publicUrl%><%=baseurldirectory%>images/Batteries_Reasonable_Price.png" alt="Bike Battery Shop">
					</div>
				  </div>

				  <!-- Left and right controls -->
				  <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
					<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
					<span class="sr-only">Previous</span>
				  </a>
				  <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
					<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
					<span class="sr-only">Next</span>
				  </a>
				</div>
				<div class="arrow_down_button_div">
					<div style="font-size: 4em;z-index:9;text-align: center;margin-top: -27px;display: block;position: absolute;color: #e02d29; margin-left: 41%; cursor: pointer;"> <span class="icon-chevron-sign-down arrow_down_button"  href='javascript:void(0)' onclick="$('html,body').animate({scrollTop:400},'slow');return false;"></span> </div>
				</div>
			</div>
		</div>
	</div>


	<!-- main container -->
  <section class="col1-layout home-content-container hidden-xs">
    <div class="container">
      <div class="std">
        <div class="best-seller-pro wow bounceInUp animated">
          <div class="slider-items-products">
            <div class="new_title center">
              <h2>Shop By Category</h2>
            </div>
            <div id="best-seller-slider" class="product-flexslider hidden-buttons latest-blog">
              <div class="slider-items slider-width-col4">
                <div class="item">
					<div class="blog-img"> <img class="width_100_class" src="/bookbattery/dev/includes/images-design/car-battery-icon-BookBattery.png" alt="Car Battery">
					  <div class="mask"> <a class="info" href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Car-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">More</a> </div>
					</div>
					<h1 class="text-align-center-custom" style="margin: auto;font-size: 30px;"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Car-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Car Battery</a> </h1>
                </div>
                <div class="item">
					<div class="blog-img"> <img class="width_100_class" src="/bookbattery/dev/includes/images-design/bike-battery-icon-BookBattery.png" alt="bike Battery shop">
					  <div class="mask"> <a class="info" href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Bike-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">More</a> </div>
					</div>
					<h2 class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Bike-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Bike Battery</a> </h2>
                </div>
                <div class="item">
					<div class="blog-img"> <img class="width_100_class" src="/bookbattery/dev/includes/images-design/inverter-icon-BookBattery.png" alt="Inverter">
					  <div class="mask"> <a class="info" href="<%=publicUrl%><%=baseurldirectory%>Brand/Inverter/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">More</a> </div>
					</div>
					<h2 class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>Brand/Inverter/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Inverter</a> </h2>
                </div>
                <div class="item">
					<div class="blog-img"> <img class="width_100_class" src="/bookbattery/dev/includes/images-design/inverter-battery-icon-BookBattery.png" alt="inverter battery">
					  <div class="mask"> <a class="info" href="<%=publicUrl%><%=baseurldirectory%>Brand/Inverter-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">More</a> </div>
					</div>
					<h2 class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>Brand/Inverter-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Inverter Battery</a></h2>
                </div>               
				<div class="item">
					<div class="blog-img"> <img class="width_100_class" src="/bookbattery/dev/includes/images-design/inverter-battery-combo-icon-BookBattery.png" alt="Home UPS Shop">
					  <div class="mask"> <a class="info" href="<%=publicUrl%><%=baseurldirectory%>Brand/Inverter-and-Battery-Combo/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">More</a> </div>
					</div>
					<h2 class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>Brand/Inverter-and-Battery-Combo/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Inverter Combo</a> </h2>
                </div>
                <div class="item">
					<div class="blog-img"> <img class="width_100_class" src="/bookbattery/dev/includes/images-design/three-wheeler-icon-BookBattery.png" alt="Three wheeler Battery">
					  <div class="mask"> <a class="info" href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Three-Wheeler-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">More</a> </div>
					</div>
					<h2 class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Three-Wheeler-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Three Wheeler Battery</a> </h2>
                </div>                
				<div class="item">
					<div class="blog-img"> <img class="width_100_class" src="/bookbattery/dev/includes/images-design/truck-battery-icon-BookBattery.png" alt="Three wheeler Battery">
					  <div class="mask"> <a class="info" href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Truck-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">More</a> </div>
					</div>
					<h2 class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Truck-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Truck Battery</a> </h2>
                </div>
                <div class="item">
					<div class="blog-img"> <img class="width_100_class" src="/bookbattery/dev/includes/images-design/bus-battery-icon-BookBattery.png" alt="bus Battery">
					  <div class="mask"> <a class="info" href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Bus-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">More</a> </div>
					</div>
					<h2 class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Bus-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Bus Battery</a> </h2>
                </div>
                <div class="item">
					<div class="blog-img"> <img class="width_100_class" src="/bookbattery/dev/includes/images-design/tractor2-battery-icon-BookBattery.png" alt="tractor Battery">
					  <div class="mask"> <a class="info" href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Tractor-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">More</a> </div>
					</div>
					<h2 class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Tractor-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Tractor Battery</a> </h2>
                </div>
             </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
  <div class="offer-banner-section hidden-lg hidden-md">
		<div class="container">
			<div class="row">
				<div class="newsletter-wrap">
					<div class="newsletter">
						  <div>
							<h4><span>Shop By Category</span></h4>
						  </div>
					</div>
				</div>
				<section class="latest-blog">
					<div class="pro-manifacture-custom ">
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Car-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/car-battery-icon-BookBattery.png" alt="Car Battery">
								</a>
							</div>
							<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Car-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Car Battery</a> </p>
						</div>
					  </div>					  
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Bike-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/bike-battery-icon-BookBattery.png" alt="Bike Battery">
								</a>
							</div>
							<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Bike-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Bike Battery</a> </p>
						</div>
					  </div>					  
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%><%=baseurldirectory%>Brand/Inverter/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/inverter-icon-BookBattery.png" alt="inverter">
								</a>
							</div>
							<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>Brand/Inverter/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Inverter</a> </p>
						</div>
					  </div>					  
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%><%=baseurldirectory%>Brand/Inverter-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/inverter-battery-icon-BookBattery.png" alt="Inverter Battery">
								</a>
							</div>
							<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>Brand/Inverter-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Inverter Battery</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%><%=baseurldirectory%>Brand/Inverter-and-Battery-Combo/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/inverter-battery-combo-icon-BookBattery.png" alt="Home Inverter Dealer">
								</a>
							</div>
							<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>Brand/Inverter-and-Battery-Combo/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Inverter Combo</a> </p>
						</div>
					  </div>					  
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Truck-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/truck-battery-icon-BookBattery.png" alt="Truck Battery">
								</a>
							</div>
							<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Truck-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Truck Battery</a> </p>
						</div>
					  </div>					  
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Bus-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/bus-battery-icon-BookBattery.png" alt="Bus Battery">
								</a>
							</div>
							<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Bus-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Bus Battery</a> </p>
						</div>
					  </div>					  
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Tractor-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">
								<img class="width_100_class" src="/bookbattery/dev/includes/images-design/tractor2-battery-icon-BookBattery.png" alt="Tractor Battery">
								</a>
							</div>
							<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Tractor-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Tractor Battery</a> </p>
						</div>
					  </div>					  
				     </div>
				</section>
				<div>
					<label for="other_BookBattery" class="width-100-custom">&nbsp; &nbsp; </label>
					<button id="other_BookBattery" class="button btn-cart" type="button">
						<span><i class="icon-basket"></i> Other Battery</span>
					</button>	
				</div>
				
			</div> 
		</div>
</div>
  <!-- End main container -->
 <div class="offer-banner-section" id="battery_finder_div">
    <div class="container">
      <div class="row">
		<div class="newsletter-wrap">
			<div class="newsletter">
				  <div>
					<h4><span>Battery Finder</span></h4>
				  </div>
			</div>
		</div>
		<div id="Battery_Finder">
			<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
				<div class="form-group">
				  <label for="product_type">Product Type *:</label>
				  <select class="form-control yes" id="product_type">
					<option value="0">Select Product Type</option>
					<option value="Car Batteries" >Car Batteries</option>
					<option value="Inverter Batteries" >Inverter Batteries</option>
					<option value="Inverter" >Inverter</option>
					<option value="Inverter and Battery Combo" >Inverter & Battery Combo</option>
					<option value="Bike Batteries" >Bike Batteries</option>
					<option value="Bus Batteries" >Bus Batteries</option>
					<option value="Tractor Batteries" >Tractor Batteries</option>
					<option value="Truck Batteries" >Truck Batteries</option>
					<option value="Three Wheeler Batteries" >Three Wheeler Batteries</option>					
					<option value="Special Vehicle Batteries" >Special Vehicle Batteries</option>
					<option value="Genset Batteries" >Genset Batteries</option>
					<option value="Crane Batteries" >Crane Batteries</option>
					<option value="Roller Batteries" >Roller Batteries</option>
					<option value="Loader Batteries" >Loader Batteries</option>
					<option value="Dozer Batteries" >Dozer Batteries</option>
					<option value="Excavator Batteries" >Excavator Batteries</option>
					<option value="Tyre Handler Batteries" >Tyre Handler Batteries</option>
					<option value="Hydraulic Shovel Batteries" >Hydraulic Shovel Batteries</option>
					<option value="Harvestor Batteries" >Harvestor Batteries</option>
					<option value="Generator Batteries" >Generator Batteries</option>
					<option value="Compactor Batteries" >Compactor Batteries</option>
					<option value="Telescopic Handler Batteries" >Telescopic Handler Batteries</option>
					<option value="Forwarder Batteries" >Forwarder Batteries</option>
					<option value="Wheeled Harvester Batteries" >Wheeled Harvester Batteries</option>
					<option value="Minibus Batteries" >Minibus Batteries</option>
					<option value="Dumper Batteries" >Dumper Batteries</option>
					<option value="Construction Equipment Batteries" >Construction Equipment Batteries</option>
					<option value="Hydralic Excavator Batteries" >Hydralic Excavator Batteries</option>
				  </select>
				  <div id='product_type-error'style="display:none;"></div>
				</div>
			</div>
			
			<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12" id="product_capacity_div" style="display:none;">
				<div class="form-group">
				  <label for="product_capacity">Capacity *:</label>
				  <select class="form-control yes" id="product_capacity">
					<option value="0">Select Capacity</option>
				  </select>
				  <div id='product_capacity-error'style="display:none;"></div>
				</div>
			</div>
			<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12" id="product_make_div">
				<div class="form-group">
				  <label for="product_make">Make *:</label>
				  <select class="form-control yes" id="product_make">
					<option value="0">Select Make</option>
				  </select>
				  <div id='product_make-error'style="display:none;"></div>
				</div>
			</div>
			<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12"  id="product_fuel_div">
				<div class="form-group">
				  <label for="product_fuel">Fuel Type *:</label>
				  <select class="form-control yes" id="product_fuel">
					<option value="0">Select Fuel Type</option>
				  </select>
				  <div id='product_fuel-error' style="display:none;"></div>
				</div>
			</div>
			<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12"  id="product_model_div">
				<div class="form-group">
				  <label for="product_model">Model *:</label>
				  <select class="form-control yes" id="product_model">
					<option value="0">Select Model</option>
				  </select>
				  <div id='product_model-error'style="display:none;"></div>
				</div>
			</div>
			<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
				<div class="form-group">
				  <label for="product_brand">Brand :</label>
				  <select class="form-control yes" id="product_brand">
					<option value="0">Select Brand</option>
				  </select>
				  <div id='product_brand-error'style="display:none;"></div>
				</div>
			</div>
			<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12" id="product_inverter_capacity_div" style="display:none;">
				<div class="form-group">
				  <label for="product_inverter_capacity">Inverter Capacity *:</label>
				  <select class="form-control yes" id="product_inverter_capacity">
					<option value="0">Select Inverter Capacity</option>
				  </select>
				  <div id='product_inverter_capacity-error'style="display:none;"></div>
				</div>
			</div>
			
			<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12" id="product_inverter_battery_div" style="display:none;">
				<div class="form-group">
				  <label for="product_inverter_battery">Inverter Battery *:</label>
				  <select class="form-control yes" id="product_inverter_battery">
					<option value="0">Select Inverter Battery</option>
				  </select>
				  <div id='product_inverter_battery-error'style="display:none;"></div>
				</div>
			</div>
			<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 pull-right">
				<div>
					<label for="product_type" class="width-100-custom">&nbsp; &nbsp; </label>
					<button onclick="AskforMobileNumber()" class="button btn-cart" type="button" id="find_battery_btn">
						<span><i class="icon-basket"></i> Find Product</span>
					</button>	
				</div>
			</div>
			<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 pull-right" id="Configure_Your_Inverter_home" style="display:none;">
				<div>
					<label for="product_type" class="width-100-custom">&nbsp; &nbsp; </label>
					<button onclick="javascript:location.href='<%=publicUrl%>/bookbattery/configure-your-inverter-and-battery.jsp'" class="button btn-cart" type="button">
						<span><i class="icon-basket"></i>Configure Your Inverter</span>
					</button>	
				</div>
			</div>
		</div>
		</div> 
      </div>
    </div>

	<div class="offer-banner-section">
		<div class="container">
			<div class="row">
				<div class="newsletter-wrap">
					<div class="newsletter">
						  <div>
							<h4><span>Shop By Manufacturers</span></h4>
						  </div>
					</div>
				</div>
				<section class="latest-blog">
					<div class="pro-manifacture-custom ">
						<div class="col-xs-12 col-sm-12 col-md-12 width_100_class">
							<h4>Car Manufacturers</h4>
						</div>
					<div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Maruti-Suzuki/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/maruti-suzuki-logo-BookBattery-270x12.png" alt="car Battery Calcutta">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Maruti-Suzuki/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Maruti Suzuki</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Hyundai/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/hyundai-logo-BookBattery-270x12.png" alt="Exide Hyundai Batteries">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Hyundai/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Hyundai</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Tata/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/tata-logo-BookBattery-270x12.png" alt="Inverter battery Hyderabad">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Tata/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Tata</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Mahindra-and-Mahindra/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/mahindra-logo-BookBattery-270x12.png" alt="car Battery Noida">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Mahindra-and-Mahindra/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Mahindra</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Toyota/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/toyota-logo-BookBattery-270x12.png" alt="Inverter battery Mumbai">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Toyota/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Toyota</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Honda/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/honda-logo-BookBattery-270x12.png" alt="Exide Honda Batteries">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Honda/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Honda</a> </p>
						</div>
					  </div>
					 <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Volks-Wagen/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/volkswagen-logo-BookBattery-270x12.png" alt="car Battery Gurgoan">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Volks-Wagen/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Volkswagen</a> </p>
						</div>
					</div>
				 <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Skoda/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/skoda-logo-BookBattery-270x12.png" alt="inverter battery online">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Skoda/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Skoda</a> </p>
						</div>
				</div>
				<div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Renault/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/renault-logo-BookBattery-270x12.png" alt="Home UPS">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Renault/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Renault</a> </p>
						</div>
				</div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								 <a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Nissan/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/nissan-logo-BookBattery-270x12.png" alt="car Battery Pune">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Nissan/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Nissan</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Ford/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/ford-logo-BookBattery-270x12.png" alt="bus Battery bangalore">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Ford/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Ford</a> </p>
						</div>
					</div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Audi/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/audi-logo-BookBattery-270x12.png" alt="Car Battery">
								</a>
							</div>
							<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Audi/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Audi</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/BMW/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" > 
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/bmw-logo-BookBattery-270x12.png" alt="Exide Car Battery online">
								</a> 
							</div>
							<p class="text-align-center-custom"><a   href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/BMW/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >BMW</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2  col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Chevrolet/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/chevrolet-logo-BookBattery-270x12.png" alt="Car Battery bangalore">
								</a> 
							</div>
							<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Chevrolet/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Chevrolet</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2  col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Fiat/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/fiat-logo-BookBattery-270x12.png" alt="Car Battery chennai">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Fiat/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Fiat</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2  col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Alfa-Romeo/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/alfa-romeo-logo-BookBattery-270x12.png" alt="Car Battery hyderabad">
								</a> 
							</div>
							<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Alfa-Romeo/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Alfa Romeo</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Daewoo/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/daewoo-logo-BookBattery-270x12.png" alt="bus Battery">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Daewoo/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Daewoo</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Force-Motors/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/force-logo-BookBattery-270x12.png" alt="bus Battery online">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Force-Motors/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Force</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Hindustan-Motors/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/hm-morters-logo-BookBattery-270x12.png" alt="Exide Inverter Battery">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Hindustan-Motors/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Hindustan Motors</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/ICML/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/iclm-logo-BookBattery-270x12.png" alt="Car Battery chennai">
								</a>
							</div>
							<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/ICML/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >ICML</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Jaugar/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/jaguar-logo-BookBattery-270x12.png" alt="bus Battery mumbai">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Jaugar/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Jaguar</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/KIA-Motors/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/KIA-Morters-logo-BookBattery-270x12.png" alt="car Battery Mumbai">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/KIA-Motors/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >KIA Motors</a> </p>
						</div>
					  </div>

					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Premier/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/premier-logo-BookBattery-270x12.png" alt="car Battery Delhi">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Premier/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Premier</a> </p>
						</div>
					  </div>

					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Mercedes-Benz/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/mercedes-benz-logo-BookBattery-270x12.png" alt="car Battery Chandigarh">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Mercedes-Benz/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Mercedes Benz</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/GM-OPEL/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/gm-opel-logo-BookBattery-270x12.png" alt="Home Inverter">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/GM-OPEL/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >GM OPEL</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Peuguot/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/peugeot-logo-BookBattery-270x12.png" alt="Inverter Battery">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Peuguot/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Peuguot</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Porsche/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/porsehe-logo-BookBattery-270x12.png" alt="UPS Battery">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Porsche/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Porsche</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Volvo/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/volvo-logo-BookBattery-270x12.png" alt="Inverter battery Bangalore">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Volvo/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Volvo</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Jeep/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/jeep1-logo-BookBattery-270x12.png" alt="Inverter battery Bangalore">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/fueltype/Car-Batteries/Jeep/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Jeep</a> </p>
						</div>
					  </div>
					</div>
				</section>
			</div> 
		</div>
    </div>
 </div>
 <!--Code added by Bharath inorder to disaply the reviews starts here--->
	
<div class="offer-banner-section hide reviewdiv" id="google_review_div">
    <div class="container">
	<div class="row">
	
	  			<div class="newsletter-wrap">
					<div class="newsletter">
						  <div>
							<h4><span>OUR HAPPY CUSTOMERS</span></h4>
						  </div>
					</div>
				</div>
		<h2>Google Reviews</h2>
	<div id="testimonial4" class="carousel col-md-12 slide testimonial4_indicators testimonial4_control_button thumb_scroll_x swipe_x">
		<div class="owl-carousel" id="google-reviews" style="height: auto;">
		</div>
	</div>
			<div id="view_direct_review" class="col-lg-10 col-md-10 col-sm-6 col-xs-12 view_review" style="text-align:center"><a style="cursor:pointer;width:100%">View Direct reviews </a></div>
			<div class="col-lg-2 col-md-2 col-sm-6 col-xs-12 more_review pull-right" style="">
					<label for="product_type">&nbsp; &nbsp; </label>
					<button onclick="window.location.href='http://bit.ly/32fk0Dd'" class="button btn-cart" type="button" id="find_battery_btn"> 
					<span><i class="icon-basket"></i> More Reviews</span>
					</button>	
			</div>
	</div>
     </div>
</div>
<div class="offer-banner-section reviewdiv" id="direct_review_div">
<div class="container">
	<div class="row">
		  			<div class="newsletter-wrap">
					<div class="newsletter">
						  <div>
							<h4><span>OUR HAPPY CUSTOMERS</span></h4>
						  </div>
					</div>
				</div>
		 <div class="col-md-12 col-center m-auto">
			<h2>Direct Reviews</h2>
			<div id="myCarousel1" class="carousel slide" data-ride="carousel">
			</div>
			<div id="view_google_review" class="col-lg-12 col-md-12 col-sm-12 col-xs-12 view_review" style="text-align: -webkit-center; font-size: 15px;font-weight: bold; width:100%"><a style="cursor:pointer">View Google reviews </a></div>

		</div>
	</div>
</div>
</div>
<!--Code added by Bharath inorder to disaply the reviews ends here--->
  <!-- main container -->
  <section class="main-container col1-layout home-content-container">
    <div class="container">
      <div class="std">
	  <!-- Featured Slider -->
        <div class="featured-pro">
          <div class="slider-items-products">
            <div class="title col-lg-6">
              <div class="new_title center">
                <h2><span>Why to Shop with BookBattery</span></h2>
              </div>
              <p>BookBattery.com is India's No. 1 online multi-brand battery store, offering 100% genuine batteries along with original manufacturer warranty. BookBattery provides car battery, bike battery, inverter battery, ups battery, home inverters and home ups. BookBattery also provides regular maintenance services such as Battery Health Check up services to keep the battery life longer and prevent sudden failures. Confirmed Battery orders will be delivered to your door step and installed for free. Customers who are buying batteries can pay in multiple modes such as cash on delivery, online payment and other UPI payments like Phonepe, GooglePay and PayTM. Attractive EMI options are available with select banks.<br /><br />
			Consumers can buy genuine and reliable batteries through online from top automotive brands like Amaron, Exide and Luminous. Installation services are done by company trained professionals. BookBattery has the ability to deliver car batteries, bike batteries and inverter batteries in all major cities of India.  Automobile owners can easily discover the model that is suitable for their vehicle along with its price and warranty details and order online. Consumer looking for Car Batteries, Bike Batteries, Home UPS Batteries, Home Inverter Batteries and Home UPS / Home Inverters will be able to get the best product/service at the best price. BookBattery is trying to bring complete trust and confidence along with price transparency to the consumers who are in need to buy car batteries, bike batteries and inverter batteries.
			 </p>
			</div>
            <div id="featured-slider-home" class="product-flexslider hidden-buttons col-lg-6">
				<div class="slider-items slider-width-col4"  id="featured-slider-home-page" >
				</div>
            </div>
          </div>
        </div>
        <!-- End Featured Slider -->
	     
      </div>
    </div>
  </section>
<div id="AskforMobileNumber_Pop_Up"></div>

<!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "dev/includes/jsp/includes_js.jsp" />
<!---################################## CSS Include Ends  ################################------>
<input type="hidden" name="brandkeyword" id='brandkeyword' value="Common">
<input type="hidden" name="MobileNumberPopUpCheck" id='MobileNumberPopUpCheck' value="HomePage">
<input type="hidden" name="user_mobile_number_cookie_tmp" id='user_mobile_number_cookie_tmp' value="">
<!---################################## JS Include Starts  ################################------>
	<jsp:include page = "dev/includes/jsp/includes_footer.jsp" />
<!---################################## JS Include Ends  ################################------>


<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- jQuery Custom Scroller CDN -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="/bookbattery/dev/js/index.js?v=1"></script>
<script type="text/javascript">
       $(document).ready(function () {
                $("#sidebar").mCustomScrollbar({
                    theme: "minimal"
                });
				//alert(this.id);
				$("#google-reviews").googlePlaces({
					placeId: 'ChIJxVnNzEyXyzsRQwE7THPDUas'
				  , render: ['reviews','staticMap','hours']
				  , min_rating: 5
				  , max_rows:5
				  , rotateTime:8000
				  , height: 300
				  , schema: {
							displayElement: '#schema' // optional, will use "#schema" by default
						  , beforeText: 'Googlers rated'
						  , middleText: 'based on'
						  , afterText: 'awesome reviewers.'
						  , type: 'Hostel'
					  }
				  , address:{
					displayElement: "#custom-address-id" // optional, will use "#google-address" by default
				  }
				  , phone:{
					displayElement: "#custom-phone-id" // optional, will use "#google-phone" by default
				  }
				  , staticMap:{
						displayElement: "#google-static-map"
					  , width: 200
					  , height: 200
					  , zoom: 15
					  , type: "satellite"
				  }
				  , hours:{
					  displayElement: "#google-hours"
				  }
				});
				var pageURL = $(location).attr("href");
				//alert(pageURL);
				if((pageURL.indexOf("direct_review_div") != -1)||(pageURL.indexOf("google_review_div") != -1))  
				{
					//alert("yes");
						$('#sidebar').removeClass('active');
						$('.overlay').fadeOut();
						$('#icon').removeClass('glyphicon-remove');
						$('#icon').addClass('glyphicon-arrow-right');						
						$('#dismiss').removeClass('leftarrow');
						$('#dismiss').addClass('rightarrow');
						$('#banner_img').hide();
						$('#banner_img1').show();
				}
				else
				{
					$('#sidebar').removeClass('active');
					$('.overlay').fadeOut();
					$('#icon').removeClass('glyphicon-remove');
					$('#icon').addClass('glyphicon-arrow-right');						
					$('#dismiss').removeClass('leftarrow');
					$('#dismiss').addClass('rightarrow');
					$('#banner_img').hide();
					$('#banner_img1').show();
				}
				
                $('#dismiss, .overlay').on('click', function(){
					if(this.className=="leftarrow")
					{
						$('#sidebar').removeClass('active');
						$('.overlay').fadeOut();
						$('#icon').removeClass('glyphicon-remove');
						$('#icon').addClass('glyphicon-arrow-right');						
						$('#dismiss').removeClass('leftarrow');
						$('#dismiss').addClass('rightarrow');
						$('#banner_img').hide();
						$('#banner_img1').show();
					}
					else if(this.className=="rightarrow")
					{
						$('#sidebar').addClass('active');
						$('.overlay').fadeIn();
						$('#icon').removeClass('glyphicon-arrow-right');
						$('#icon').addClass('glyphicon-remove');	
						$('#dismiss').removeClass('rightarrow');
						$('#dismiss').addClass('leftarrow');
						$('#banner_img1').hide();
						$('#banner_img').show();						
					}
					else
					{
						//alert(this.className);
						$('#sidebar').removeClass('active');
						$('.overlay').fadeOut();
						$('#icon').removeClass('glyphicon-remove');
						$('#icon').addClass('glyphicon-arrow-right');						
						$('#dismiss').removeClass('leftarrow');
						$('#dismiss').addClass('rightarrow');
						$('#banner_img').hide();
						$('#banner_img1').show();
					}
                });

});
</script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="/bookbattery/dev/js/google_api.js?v=1"></script>
  <script>
  	$.ajax
	({					 
		type: "GET",
		url: "/bookbattery/servlet/Functions?hidWhatToDo=Get_Customer_ratings_Testimonial",
		success: function(data)
		{	
			$("#myCarousel1").html(data);
			//alert(data);
			$('#myCarousel1').carousel({
				interval: 7000
				, navigation: true
				, pagination: false
			});

		}	
	});	

	$.ajax
	({					 
		type: "GET",
		url: "/bookbattery/servlet/Functions?hidWhatToDo=Get_BatteryList_Random",
		success: function(data)
		{	
			$("#featured-slider-home-page").html(data);
			   $("#featured-slider-home .slider-items").owlCarousel({
				items: 2, //10 items above 1000px browser width
				itemsDesktop: [1024, 3], //5 items between 1024px & 901px
				itemsDesktopSmall: [900, 3], // 3 items betweem 900px & 601px
				itemsTablet: [600, 2], //2 items between 600 & 0;
				itemsMobile: [320, 1]
				, navigation: true
				, navigationText: ["<a class=\"flex-prev\"></a>", "<a class=\"flex-next\"></a>"]
				, slideSpeed: 500
				, pagination: false
			  });
		}	
	});
  </script>
<script>
	  setTimeout(function(){

	}, 5);
	
$('#myCarousel').carousel({
    interval: 10000
});

$("#other_BookBattery").click(function(){
  
  $("#product_type").focus();
	$('html, body').animate({
              scrollTop: $("#battery_finder_div").offset().top
        }, 300); 
});


function UpdateProduct_Filter()
{
	//alert("indexcity"+city);
	
	var product_state =$("#product_state").val();
	var product_city =$("#product_city").val();
	
	//alert("product_city"+product_city);
	
	var ctime=365*24*60*60*1000;
	
	$("#product_city").val("Chennai");
	
	//alert($("#product_city").val());
	
	//alert("index"+product_state);
	
	//alert(66);
	
	if (selectValidation("","product_state","select") == false)
	{
		return;
	}
	else
	{
		setCookie("product_state_cookie", product_state.replace(/ /g, "-"),ctime)
	}	
	alert("index"+product_city);
	if (selectValidation("","product_city","select") == false)
	{
		return;
	}
	else
	{
		setCookie("product_city_cookie", product_city.replace(/ /g, "-"),ctime)
	}
	
	product_state= product_state.replace(/ /g, "-");
	product_city= product_city.replace(/ /g, "-");
 	window.location.href = publicUrl+'/bookbattery/';	
}

</script>

<!-- Small modal -->

</body>
</html>
