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
<title>BookBattery | Amaron Exide online car battery and inverter battery store</title>
<meta name="og_site_name" property="og:site_name" content="BookBattery.com"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="robots" content="index, follow">
<link rel="canonical" href="https://www.BookBattery.com/"/>
<link rel="shortcut icon" href="<%=publicUrl%>/bookbattery/favicon.ico">
<meta name="description" content="Looking for a Car Battery, Inverter Battery and inverters in India. Buy Amaron, Exide & Luminious BookBattery online at discounted price with free delivery, free installation.">
<meta name="keywords" content="car battery, car battery online, inverter battery, battery store, 4  wheeler battery, car BookBattery online, Bangalore, Chennai, Delhi, Hyderabad, Kerala, Exide battery, amaron battery, amaron car battery, exide car battery, amaron inverter battery, exide inverter battery, inverter battery online, BookBattery, BookBattery.com">

<!-- Open Graph Data -->
<meta property="og:title" content="BookBattery | Amaron Exide online car battery and inverter battery store" />
<meta property="og:url" content="http://www.BookBattery.com" />
<meta property="og:type" content="BookBattery" />
<meta property="og:description" content="Looking for a Car Battery, Inverter Battery and inverters in India. Buy Amaron, Exide & Luminious BookBattery online at discounted price with free delivery, free installation." />
<meta property="og:image" content="https://www.BookBattery.com/bookbattery/images/bookbatterylogo.png" />

<!-- Twitter Card -->

<meta name="twitter:card" content="summary">
<meta name="twitter:title" content="BookBattery | Amaron Exide online car battery and inverter battery store">
<meta name="twitter:description" content="Looking for a Car Battery, Inverter Battery and inverters in India. Buy Amaron, Exide & Luminious BookBattery online at discounted price with free delivery, free installation.">
<meta name="twitter:image" content="https://www.BookBattery.com/bookbattery/images/bookbatterylogo.png">


<!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "dev/includes/jsp/includes_css.jsp" />
<!---################################## CSS Include Ends  ################################------>

</head>
<style>
.width_100_class{width: 100% !important;}
.fsize_11{font-size: 11px;}
.pad_0{padding:0px}
.d_none{display: none !important;}
</style>
<body>
<div class="page">

<!---################################## Header Include Starts  ################################------>
	<jsp:include page = "dev/includes/jsp/includes_header.jsp" />
<!---################################## Header Include Ends  ################################------>
     
	<div class="container slider_XAnxerMM">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
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
					  <img src="<%=publicUrl%><%=baseurldirectory%>images/banners/BN3.jpg" alt="BookBattery - Buy Exide Car Battery Online">
					</div>
					<div class="item">
					  <img src="<%=publicUrl%><%=baseurldirectory%>images/banners/BN5.jpg" alt="BookBattery - Buy Exide Car Battery Online">
					</div>
					<div class="item">
					  <img src="<%=publicUrl%><%=baseurldirectory%>images/banners/BookBatterybannerfinal1.png" alt="BookBattery - Buy Exide Car Battery Online">
					</div>
					<div class="item">
					  <img src="<%=publicUrl%><%=baseurldirectory%>images/banners/Why-shop-with-BookBattery.jpg" alt="BookBattery - Buy Exide Car Battery Online">
					</div>

					<div class="item">
					  <img src="<%=publicUrl%><%=baseurldirectory%>images/Exide_Batteries.png"  alt="Banner Image" >
					</div>

					<div class="item">
					  <img src="<%=publicUrl%><%=baseurldirectory%>images/BookBattery_Amaron.png" alt="Flower">
					</div>

					<div class="item">
					  <img  src="<%=publicUrl%><%=baseurldirectory%>images/Batteries_Reasonable_Price.png" alt="Flower">
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
 <div class="offer-banner-section">
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
			<!--- <div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
				<div class="form-group">
				  <label for="product_state">State *:</label>
				  <select class="form-control yes" id="product_state">
					<option value="0">Select State</option>
				  </select>
				  <div id='product_state-error'style="display:none;"></div>
				</div>
			</div>
			<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
				<div class="form-group">
				  <label for="product_city">City *:</label>
				  <select class="form-control yes" id="product_city">
					<option value="0">Select City</option>
				  </select>
				  <div id='product_city-error'style="display:none;"></div>
				</div>
			</div> --->
			<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 pull-right">
				<div class="form-group">
					<label for="product_type" class="width-100-custom">&nbsp; &nbsp; </label>
					<button onclick="AskforMobileNumber()" class="button btn-cart" type="button" id="find_battery_btn">
						<span><i class="icon-basket"></i> Find Product</span>
					</button>	
				</div>
			</div>
			<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12 pull-right" id="Configure_Your_Inverter_home" style="display:none;">
				<div class="form-group">
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
					<div class="blog-img"> <img class="width_100_class" src="/bookbattery/dev/includes/images-design/bike-battery-icon-BookBattery.png" alt="bike Battery">
					  <div class="mask"> <a class="info" href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Bike-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">More</a> </div>
					</div>
					<h2 class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Bike-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Bike Battery</a> </h2>
                </div>
                <div class="item">
					<div class="blog-img"> <img class="width_100_class" src="/bookbattery/dev/includes/images-design/inverter-icon-BookBattery.png" alt="Inverter">
					  <div class="mask"> <a class="info" href="<%=publicUrl%><%=baseurldirectory%>Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">More</a> </div>
					</div>
					<h2 class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Inverter</a> </h2>
                </div>
                <div class="item">
					<div class="blog-img"> <img class="width_100_class" src="/bookbattery/dev/includes/images-design/inverter-battery-icon-BookBattery.png" alt="inverter battery">
					  <div class="mask"> <a class="info" href="<%=publicUrl%><%=baseurldirectory%>Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">More</a> </div>
					</div>
					<h2 class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Inverter Battery</a> </h2>
                </div>               
				<div class="item">
					<div class="blog-img"> <img class="width_100_class" src="/bookbattery/dev/includes/images-design/inverter-battery-combo-icon-BookBattery.png" alt="inverter battery">
					  <div class="mask"> <a class="info" href="<%=publicUrl%><%=baseurldirectory%>Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">More</a> </div>
					</div>
					<h2 class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Inverter Battery</a> </h2>
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
							<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Car-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Car Battery</a> </p>
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
								<a href="<%=publicUrl%><%=baseurldirectory%>Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/inverter-icon-BookBattery.png" alt="inverter">
								</a>
							</div>
							<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Inverter</a> </p>
						</div>
					  </div>					  
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%><%=baseurldirectory%>Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/inverter-battery-icon-BookBattery.png" alt="Inverter Battery">
								</a>
							</div>
							<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Inverter Battery</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%><%=baseurldirectory%>Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/inverter-battery-combo-icon-BookBattery.png" alt="Inverter Battery">
								</a>
							</div>
							<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Inverter Combo</a> </p>
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
			</div> 
		</div>
</div>
  <!-- End main container -->
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
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Audi/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/audi-logo-BookBattery-270x12.png" alt="Car Battery">
								</a>
							</div>
							<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Audi/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Audi</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/BMW/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" > 
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/bmw-logo-BookBattery-270x12.png" alt="Exide Car Battery online">
								</a> 
							</div>
							<p class="text-align-center-custom"><a   href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/BMW/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >BMW</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2  col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Chevrolet/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/chevrolet-logo-BookBattery-270x12.png" alt="Car Battery bangalore">
								</a> 
							</div>
							<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Chevrolet/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Chevrolet</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2  col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Fiat/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/fiat-logo-BookBattery-270x12.png" alt="Car Battery chennai">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Fiat/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Fiat</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2  col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Alfa-Romeo/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/alfa-romeo-logo-BookBattery-270x12.png" alt="Car Battery hyderabad">
								</a> 
							</div>
							<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Alfa-Romeo/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Alfa Romeo</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Daewoo/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/daewoo-logo-BookBattery-270x12.png" alt="bus Battery">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Daewoo/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Daewoo</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Force-Motors/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/force-logo-BookBattery-270x12.png" alt="bus Battery online">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Force-Motors/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Force</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Ford/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/ford-logo-BookBattery-270x12.png" alt="bus Battery bangalore">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Ford/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Ford</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Hindustan-Motors/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/hm-morters-logo-BookBattery-270x12.png" alt="Exide Inverter Battery">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Hindustan-Motors/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Hindustan Motors</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Honda/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/honda-logo-BookBattery-270x12.png" alt="Exide Honda Batteries">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Honda/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Honda</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Hyundai/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/hyundai-logo-BookBattery-270x12.png" alt="Exide Hyundai Batteries">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Hyundai/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Hyundai</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/ICML/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/iclm-logo-BookBattery-270x12.png" alt="Car Battery chennai">
								</a>
							</div>
							<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/ICML/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >ICML</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Jaugar/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/jaguar-logo-BookBattery-270x12.png" alt="bus Battery mumbai">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Jaugar/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Jaguar</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/KIA-Motors/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/KIA-Morters-logo-BookBattery-270x12.png" alt="car Battery Mumbai">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/KIA-Motors/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >KIA Motors</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								 <a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Nissan/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/nissan-logo-BookBattery-270x12.png" alt="car Battery Pune">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Nissan/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Nissan</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Premier/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/premier-logo-BookBattery-270x12.png" alt="car Battery Delhi">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Premier/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Premier</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Volks-Wagen/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/volkswagen-logo-BookBattery-270x12.png" alt="car Battery Gurgoan">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Volks-Wagen/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Volkswagen</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Mahindra-and-Mahindra/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/mahindra-logo-BookBattery-270x12.png" alt="car Battery Noida">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Mahindra-and-Mahindra/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Mahindra</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Maruti-Suzuki/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/maruti-suzuki-logo-BookBattery-270x12.png" alt="car Battery Calcutta">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Maruti-Suzuki/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Maruti Suzuki</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Mercedes-Benz/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/mercedes-benz-logo-BookBattery-270x12.png" alt="car Battery Chandigarh">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Mercedes-Benz/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Mercedes Benz</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/GM-OPEL/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/gm-opel-logo-BookBattery-270x12.png" alt="Home Inverter">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/GM-OPEL/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >GM OPEL</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Peuguot/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/peugeot-logo-BookBattery-270x12.png" alt="Inverter Battery">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Peuguot/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Peuguot</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Porsche/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/porsehe-logo-BookBattery-270x12.png" alt="UPS Battery">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Porsche/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Porsche</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Renault/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/renault-logo-BookBattery-270x12.png" alt="Home UPS">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Renault/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Renault</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Skoda/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/skoda-logo-BookBattery-270x12.png" alt="inverter battery online">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Skoda/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Skoda</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Tata/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/tata-logo-BookBattery-270x12.png" alt="Inverter battery Hyderabad">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Tata/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Tata</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Volvo/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/volvo-logo-BookBattery-270x12.png" alt="Inverter battery Bangalore">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Volvo/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Volvo</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Toyota/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="/bookbattery/dev/includes/images-design/Brand-logo/toyota-logo-BookBattery-270x12.png" alt="Inverter battery Mumbai">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Toyota/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Toyota</a> </p>
						</div>
					  </div>
					</div>
				</section>
			</div> 
		</div>
    </div>
 </div>
<!--<div class="">
   <div class="container">
		<div class="slider-section">
			<div class="cat-block pro-block">
				<div class="pro1-block">
					<ul class="top-cat-box">
						<li>
						<div><img src="https://www.BookBattery.com/services/includes/images/BookBattery_tyres.png" alt="Tyres STORE"></div>
						<!--<p style="color: #000;font-size: 20px;font-weight: 400;margin-top: 0;width: 80%;padding: 5px 14px;position: relative;display: inline-block;font-family: 'Open Sans',sans-serif;text-transform: uppercase;line-height: 1.1;">Tyres STORE</h2>
						<div class="htwo_other_opt">TYRES STORE</div>
						<a href="https://www.BookBattery.com/tyres/" target="_blank">Buy Now</a></li>
						<li>
						<div><img src="https://www.BookBattery.com/tyres/includes/images/tyres%20car%20service.png" alt="SERVICE CENTRES"></div>
						<div class="htwo_other_opt">SERVICE CENTRES</div>
						<a href="https://www.BookBattery.com/services/" target="_blank">Book Now</a></li>
						<li>
						<div><img src="https://www.BookBattery.com/tyres/includes/images/tyres%20car%20insurance.png" alt="INSURANCE STORE"></div>
						<div class="htwo_other_opt">INSURANCE STORE</div>
						<a href="https://www.BookBattery.com/insurance/" target="_blank">Book Now</a></li>
						<li>
						<div><img src="https://www.BookBattery.com/tyres/includes/images/car_accsoeries.png" alt="ACCESSORIES STORE"></div>
						<div class="htwo_other_opt">ACCESSORIES STORE</div>
						<a href="https://www.BookBattery.com/accessories/" target="_blank">Buy Now</a></li>
					</ul>
				</div>
			</div>           
		</div>        
	 </div>        
</div>-->
  <!-- end Slider -->
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
              <p>BookBattery.com is India's No. 1 online automobile market place, offering 100% genuine products along with original manufacturer warranty. BookBattery provide car battery, bike battery, inverter battery, car tyres, car servicing like car body repairs, ac repair & services, battery health check up, dent removal & painting, general service, roadside assistance, wheel balancing & alignment, windshield & glass repair, car insurance, car tracking device.  <br /><br />
			Consumers can buy genuine & reliable automotive products through online from top automotive brands. Services are done by company trained professionals. At present, we deliver BookBattery in all major cities. We are gradually increasing our delivery network to include all major cities & towns in India. Car services, car tyres, car insurance & car accessories are provided only in & around Bangalore.
			Automobile owners can easily discover the branded & exclusive automobile component & service retailers nearest to their area look at the price & order online. An automobile consumer will be able to get the best product/service at the best price. With BookBattery.com, some of the services will be given at the consumer place itself which gives an open look at what has been done for their automobile.
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


  <script>
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
</script>

<!-- Small modal -->

</body>
</html>
