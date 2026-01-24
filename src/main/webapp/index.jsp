<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.util.Hashtable,java.util.ResourceBundle,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Enumeration,java.util.Properties,java.io.FileInputStream,java.io.File,javax.servlet.ServletContext,com.ngit.javabean.consumers.products.GetCookie,com.ngit.javabean.qrymgr.QueryManager"%>
<%
Properties propsMOPConfig = new Properties();
ServletContext context = getServletContext();

//String fileName = context.getRealPath("properties/bookbatteryconfig.properties");
FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
//ResourceBundle fin1 = ResourceBundle.getBundle("properties/bookbatteryconfig.properties");
propsMOPConfig.load(fin1); 
fin1.close(); 
String publicUrl = (propsMOPConfig.getProperty("publicUrl")!=null)?propsMOPConfig.getProperty("publicUrl"):"";
String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?propsMOPConfig.getProperty("baseurldirectory"):"";
LogLevel.DEBUG(5, new Throwable(), "publicUrl :" + publicUrl);

LogLevel.DEBUG(5, new Throwable(), "baseurldirectory :" + baseurldirectory);
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
<!doctype html>

<html class="no-js" lang="en-US">

<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>BookBattery || Online Battery Store</title>
    <meta name="description" content="Default Description">
    <meta name="keywords" content="E-commerce" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- place favicon.ico in the root directory -->
    <link rel="shortcut icon" type="image/x-icon" href="assets/img/icon/favicon.png">

        <!---################################## CSS Include Starts  ################################------>
    <!-- Google Font css -->
    <link href="https://fonts.googleapis.com/css?family=Lily+Script+One" rel="stylesheet"> 

    <!-- mobile menu css -->
    <link rel="stylesheet" href="assets/css/meanmenu.min.css">
    <!-- animate css -->
    <link rel="stylesheet" href="assets/css/animate.css">
    <!-- nivo slider css -->
    <link rel="stylesheet" href="assets/css/nivo-slider.css">
    <!-- owl carousel css -->
    <link rel="stylesheet" href="assets/css/owl.carousel.min.css">
    <!-- slick css -->
   <link rel="stylesheet" href="assets/css/slick.css">
    <!-- price slider css -->
    <link rel="stylesheet" href="assets/css/jquery-ui.min.css">
    <!-- fontawesome css -->
    <link rel="stylesheet" href="assets/css/font-awesome.min.css">
     <!-- fancybox css -->
    <link rel="stylesheet" href="assets/css/jquery.fancybox.css">     
    <!-- bootstrap css -->
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
    <!-- default css  -->
    <link rel="stylesheet" href="assets/css/default.css">
    <!-- responsive css -->
    <link rel="stylesheet" href="assets/css/responsive.css">
    <link rel="stylesheet" href="/bookbattery/dev/includes/css/custom.css?v=31" type="text/css">
	   
	       <!-- style css -->
    <link rel="stylesheet" href="assets/css/style.css">
<!---################################## CSS Include Ends  ################################------>

</head>

<body>
    <!--[if lt IE 8]>
	<p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
	<![endif]-->
    <!-- Wrapper Start -->
    <div class="wrapper homepage">
        <!-- Newsletter Popup Start -->
        <div class="popup_wrapper">
            <div class="test">
                <span class="popup_off">Close</span>
                <div class="subscribe_area text-center mt-60">
                    <h2>Newsletter</h2>
                    <p>Subscribe to the Jantrik mailing list to receive updates on new arrivals, special offers and other discount information.</p>
                    <div class="subscribe-form-group">
                        <form action="#">
                            <input autocomplete="off" type="text" name="message" id="message" placeholder="Enter your email address">
                            <button type="submit">subscribe</button>
                        </form>
                    </div>
                    <div class="subscribe-bottom mt-15">
                        <input type="checkbox" id="newsletter-permission">
                        <label for="newsletter-permission">Don't show this popup again</label>
                    </div>
                </div>
            </div>
        </div>
        <!-- Newsletter Popup End -->
       
        
        <!---################################## Header Include Starts  ################################------>
	<jsp:include page = "assets/includes/includes_header.jsp" />
<!---################################## Header Include Ends  ################################------>
        <!-- Slider Area Start -->
        <div class="slider-area slider-style-three pl-15 pr-15">
            <div class="container">
                <div class="row" style="margin: 0px -15px 0px -15px;">
                    <div class="col-md-12 col-lg-8">
                        <div class="slider-wrapper theme-default">
                            <!-- Slider Background  Image Start-->
                            <div id="slider" class="nivoSlider">
                                <a href="shop.html"> <img src="assets/img/slider/5.jpg" data-thumb="img/slider/5.jpg" alt="" title="#slider-1-caption1"/></a>
                                <a href="shop.html"><img src="assets/img/slider/6.jpg" data-thumb="img/slider/6.jpg" alt="" title="#slider-1-caption2"/></a>
                            </div>
                            <!-- Slider Background  Image Start-->
                            <div id="slider-1-caption1" class="nivo-html-caption nivo-caption">
                                <div class="text-content-wrapper">
                                    <!-- <div class="text-content">
                                        <h4 class="title2 wow bounceInLeft text-white mb-16" data-wow-duration="2s" data-wow-delay="0s">Big Sale</h4>
                                        <h1 class="title1 wow bounceInRight text-white mb-16" data-wow-duration="2s" data-wow-delay="1s">Hand Tools <br>Power Saw Machine</h1>
                                        <div class="banner-readmore wow bounceInUp mt-35" data-wow-duration="2s" data-wow-delay="2s">
                                            <a class="button slider-btn" href="shop.html">Shop Now</a>                    
                                        </div>
                                    </div> -->
                                </div>
                            </div>  
                            <div id="slider-1-caption2" class="nivo-html-caption nivo-caption">
                                <div class="text-content-wrapper">
                                    <!-- <div class="text-content slide-2">
                                        <h4 class="title2 wow bounceInLeft text-white mb-16" data-wow-duration="1s" data-wow-delay="1s">Big Sale</h4>
                                        <h1 class="title1 wow flipInX text-white mb-16" data-wow-duration="1s" data-wow-delay="2s">Hand Tools <br>Power Saw Machine</h1>
                                        <div class="banner-readmore wow bounceInUp mt-35" data-wow-duration="1s" data-wow-delay="3s">
                                            <a class="button slider-btn" href="shop.html">Shop Now</a>                    
                                        </div>
                                    </div>  -->      
                                </div>
                            </div>   
                        </div>
                    </div>
                    <div class="col-md-12 col-lg-4">
                         
                        <div class="battery-find" id="battery-find">
                                <h3>Battery Finder</h3>
                                <div class="form-group">
                                        <label class="control-label" style="color: #e2070fe8;">Product Type *</label>
                                        <select class="formcontrol yes" name="product_type" id="product_type" placeholder="Select Product Type">
    <option value="0">Select Product Type</option>
                    <option value="Car Batteries" >Car Batteries</option>
					<option value="Inverter Batteries" >Inverter Batteries</option>
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
                                    <div id="product_type-error" style="display:none;"></div>
                                    </div>
                                     <div  id="product_make_div" class="form-group">
                                        <label class="control-label" style="color: #e2070fe8;">Vehicle Make *</label>
                                        <select class="formcontrol" name="product_make" id="product_make" placeholder="Select Vehicle Make">
    <option value="0">Select Vehicle Make</option>
                                        
                                    </select> 
                                    <div id="product_make-error" style="display:none;"></div>
                                    </div>   
                                     <div id="product_model_div" class="form-group">
                                        <label class="control-label" style="color: #e2070fe8;">Vehicle Model *</label>
                                        <select class="formcontrol" name="product_model" id="product_model" placeholder="Select Product Model">
    <option value="0">Select Vehicle Model</option>
                                        
                                    </select> 
                                     <div id="product_model-error" style="display:none;"></div>
                                    </div>   
                                     <div id="product_capacity_div" class="form-group" style="display: none;">
                                        <label class="control-label" style="color: #e2070fe8;">Battery Capacity *</label>
                                        <select class="formcontrol" name="product_capacity" id="product_capacity" placeholder="Select Battery Capacity">
    <option value="0">Select Battery Capacity</option>
                                        
                                    </select> 
                                    <div id="product_capacity-error" style="display:none;"></div>
                                    </div> 
                                    <div class="form-group">
                                        <label class="control-label" style="color: #e2070fe8;">Battery Brand *</label>
                                        <select class="formcontrol" name="product_brand" id="product_brand" placeholder="Select Battery Brand">
    <option value="0">Select Battery Brand</option>
                                        
                                    </select> 
                                    <div id="product_brand-error" style="display:none;"></div>
                                    </div>                                 
                                    <div class="payment-accordion" style="text-align: right;">                                        
                                        <div class="batfind-button" style="margin-top: 34px;margin-bottom: 3px;">
                                            <input type="submit" value="Find Battery" onclick="AskforMobileNumber()">
                                        </div>
                                    </div>
                                
                            </div>
                       
                        
                    </div>
                </div>
            </div>
        </div>
        <!-- Slider Area End --> 
                <!-- Company Policy Start -->
        <div class="company-policy pb-30 pt-20">
            <div class="container" style="background: #d6d6d6a6;">
                <div class="row">
                    <!-- Single Policy Start -->
                    <div class="col-lg-3 col-sm-6">
                        <div class="single-policy">
                            <div class="icone-img">
                                <img src="assets/img/icon/1.png" alt="">
                            </div>
                            <div class="policy-desc">
                                <h3>Free Delivery</h3>
                                <p>Free Delivery and Installation</p>
                            </div>
                        </div>
                    </div>
                    <!-- Single Policy End -->
                    <!-- Single Policy Start -->
                    <div class="col-lg-3 col-sm-6">
                        <div class="single-policy">
                            <div class="icone-img">
                                <img src="assets/img/icon/2.png" alt="">
                            </div>
                            <div class="policy-desc">
                                <h3>Online Support 24/7</h3>
                                <p>Support Online 24 hours</p>
                            </div>
                        </div>
                    </div>
                    <!-- Single Policy End -->
                    <!-- Single Policy Start -->
                    <div class="col-lg-3 col-sm-6">
                        <div class="single-policy">
                            <div class="icone-img">
                                <img src="assets/img/icon/3.png" alt="">
                            </div>
                            <div class="policy-desc">
                                <h3>Cash On Delivery</h3>
                                <p>UPI Payments Acceptable</p>
                            </div>
                        </div>
                    </div>
                    <!-- Single Policy End -->
                    <!-- Single Policy Start -->
                    <div class="col-lg-3 col-sm-6">
                        <div class="single-policy">
                            <div class="icone-img">
                                <img src="assets/img/icon/4.png" alt="">
                            </div>
                              <div class="policy-desc">
                                <h3>Genuine Products</h3>
                                <p>100% Original Quality</p>
                            </div>
                        </div>
                    </div>
                    <!-- Single Policy End -->
                </div>
            </div>
        </div>
        <!-- Company Policy End -->   
       
        <!-- Banner Start -->
        <div class="upper-banner banner pb-30">
            <div class="container">
               <div class="row">
                   <!-- Single Banner Start -->
                   <div style ="-webkit-box-shadow: 6px 7px 46px -5px rgb(130 130 130 / 96%);margin: 15px;">
                        <div class="single-banner zoom">
                            <a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Car-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"><img src="assets/img/banner/2.png" alt="slider-banner"></a>
                        </div>
                    </div>
                   <!-- Single Banner End -->
                    <!-- Single Banner Start -->
                   <div  style ="-webkit-box-shadow: 6px 7px 46px -5px rgb(130 130 130 / 96%);margin: 15px;">
                        <div class="single-banner zoom">
                            <a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Bike-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"><img src="assets/img/banner/1.png" alt="slider-banner"></a>
                        </div>
                    </div>
                   <!-- Single Banner End -->
               </div>
               <!-- Row End -->
            </div>
            <!-- Container End -->
        </div>                                
        <!-- Banner End -->
                <!-- Best Products Start -->
        <div class="best-seller-product pb-30">
            <div class="container" style="-webkit-box-shadow: 6px 7px 46px -5px rgba(130,130,130,.96);">
                <div class="group-title">
                    <h2>Shop By Category</h2>
                </div>
                <!-- Best Product Activation Start -->
                <div class="best-seller-pro-active  owl-carousel slider-right-content">
                    <!-- Double Product Start -->
                    <div class="double-pro">
                        <!-- Single Product Start -->
                        <div class="single-product">
                            <div class="pro-img">
                               <a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Car-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"><img class="img" src="assets/img/icon/car-battery-icon.png" alt="product-image"></a></div>
                            <div style="text-align: center;">                               
                               <h5 style="font-size: 17px;"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Car-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" style="">Car Battery</a></h5></div></div>
                        <!-- Single Product End -->
                    </div>
                    <!-- Double Product End -->
                     <!-- Double Product Start -->
                    <div class="double-pro">
                        <!-- Single Product Start -->
                        <div class="single-product">
                            <div class="pro-img">
                               <a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Bike-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"><img class="img" src="assets/img/icon/bike-battery-icon.png" alt="product-image"></a></div>
                            <div style="text-align: center;">                               
                               <h5 style="font-size: 17px;"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Bike-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" style="">Bike Battery</a></h5></div></div>
                        <!-- Single Product End -->
                    </div>
                    <!-- Double Product End -->
                     <!-- Double Product Start -->
                    <div class="double-pro">
                        <!-- Single Product Start -->
                        <div class="single-product">
                            <div class="pro-img">
                               <a href="<%=publicUrl%><%=baseurldirectory%>Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"><img class="img" src="assets/img/icon/inverter-icon.png" alt="product-image"></a></div>
                            <div style="text-align: center;">                               
                               <h5 style="font-size: 17px;"><a href="<%=publicUrl%><%=baseurldirectory%>Inverter/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" style="">Inverter/UPS</a></h5></div></div>
                        <!-- Single Product End -->
                    </div>
                    <!-- Double Product End -->
                     <!-- Double Product Start -->
                    <div class="double-pro">
                        <!-- Single Product Start -->
                        <div class="single-product">
                            <div class="pro-img">
                               <a href="<%=publicUrl%><%=baseurldirectory%>Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"><img class="img" src="assets/img/icon/inverter-battery-icon.png" alt="product-image"></a></div>
                            <div style="text-align: center;">                               
                               <h5 style="font-size: 17px;"><a href="<%=publicUrl%><%=baseurldirectory%>Inverter-Batteries/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" style="">Inverter/UPS Battery</a></h5></div></div>
                        <!-- Single Product End -->
                    </div>
                    <!-- Double Product End -->
                     <!-- Double Product Start -->
                    <div class="double-pro">
                        <!-- Single Product Start -->
                        <div class="single-product">
                            <div class="pro-img">
                               <a href="<%=publicUrl%><%=baseurldirectory%>Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"><img class="img" src="assets/img/icon/inverter-battery-combo-icon.png" alt="product-image"></a></div>
                            <div style="text-align: center;">                               
                               <h5 style="font-size: 17px;"><a href="<%=publicUrl%><%=baseurldirectory%>Inverter-and-Battery-Combo/All/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" style="">Inverter/UPS Combo</a></h5></div></div>
                        <!-- Single Product End -->
                    </div>
                    <!-- Double Product End -->
                     <!-- Double Product Start -->
                    <div class="double-pro">
                        <!-- Single Product Start -->
                        <div class="single-product">
                            <div class="pro-img">
                               <a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Three-Wheeler-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"><img class="img" src="assets/img/icon/three-wheeler-icon.png" alt="product-image"></a></div>
                            <div style="text-align: center;">                               
                               <h5 style="font-size: 17px;"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Three-Wheeler-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" style="">Three Wheeler Battery</a></h5></div></div>
                        <!-- Single Product End -->
                    </div>
                    <!-- Double Product End -->
                     <!-- Double Product Start -->
                    <div class="double-pro">
                        <!-- Single Product Start -->
                        <div class="single-product">
                            <div class="pro-img">
                               <a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Truck-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"><img class="img" src="assets/img/icon/truck-battery-icon.png" alt="product-image"></a></div>
                            <div style="text-align: center;">                               
                               <h5 style="font-size: 17px;"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Truck-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" style="">Truck Battery</a></h5></div></div>
                        <!-- Single Product End -->
                    </div>
                    <!-- Double Product End -->
                     <!-- Double Product Start -->
                    <div class="double-pro">
                        <!-- Single Product Start -->
                        <div class="single-product">
                            <div class="pro-img">
                               <a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Tractor-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"><img class="img" src="assets/img/icon/tractor2-battery-icon.png" alt="product-image"></a></div>
                            <div style="text-align: center;">                               
                               <h5 style="font-size: 17px;"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Tractor-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" style="">Tractor Battery</a></h5></div></div>
                        <!-- Single Product End -->
                    </div>
                    <!-- Double Product End -->
                    
                </div>
                <!-- Best Product Activation End -->
            </div>
            <!-- Container End -->
        </div>
        <!-- Best Product End --> 
        <!-- Shop By Manufacturer -->
        <!-- Shop By Manufacturer End -->
        <!-- New Products Start -->
        <div class="new-products pb-30">
            <div class="container" style="-webkit-box-shadow: 6px 7px 46px -5px rgba(130,130,130,.96);padding: 15px;">
              <div class="group-title">
                    <h2 id ="car_brands_div_h">Shop By Car Brands</h2>
                     <h2 id ="bike_brands_div_h" style="display:none">Shop By Bike Brands</h2>
                </div>
                <div class="row" style="display: flex;">
                     <div class="col-xl-12 col-lg-12  order-lg-2">
                        <!-- New Pro Content End -->
                        <div class="new-pro-content">
                            <div class="pro-tab-title" style="text-align: end;">
                                <!-- Featured Product List Item Start -->
                                <ul class="nav product-list product-tab-list">
                                    <li><a class="active" data-toggle="tab" id="car_brands_butn">Car Brands</a></li>
                                    <li><a data-toggle="tab" id="bike_brands_butn">Bike Brands</a></li>
                                </ul>
                                <!-- Featured Product List Item End -->
                            </div>
                            
                            <div id ="car_brands_div" class="tab-content product-tab-content jump">
<section class="latest-blog">
					<div class="pro-manifacture-custom">
						<div class="col-xs-12 col-sm-12 col-md-12 width_100_class" style="text-align: -webkit-center;margin-top: 5px;margin-bottom: 10px;">
							<h3 style="color: #e2070f;">Car Brands</h3>
						</div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Audi/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">
									<img class="width_100_class" src="dev/includes/images-design/Brand-logo/audi-brand-logo-270x12.png" alt="Car Battery">
								</a>
							</div>
							<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Audi/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Audi</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/BMW/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" > 
									<img class="width_100_class" src="dev/includes/images-design/Brand-logo/bmw-brand-logo-270x12.png" alt="Exide Car Battery online">
								</a> 
							</div>
							<p class="text-align-center-custom"><a   href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/BMW/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >BMW</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2  col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Chevrolet/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="dev/includes/images-design/Brand-logo/chevrolet-brand-logo-270x12.png" alt="Car Battery bangalore">
								</a> 
							</div>
							<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Chevrolet/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Chevrolet</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2  col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Fiat/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="dev/includes/images-design/Brand-logo/fiat-brand-logo-270x12.png" alt="Car Battery chennai">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Fiat/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Fiat</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2  col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Alfa-Romeo/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="dev/includes/images-design/Brand-logo/alfa-romeo-brand-logo-270x12.png" alt="Car Battery hyderabad">
								</a> 
							</div>
							<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Alfa-Romeo/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Alfa Romeo</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Daewoo/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="dev/includes/images-design/Brand-logo/daewoo-brand-logo-270x12.png" alt="bus Battery">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Daewoo/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Daewoo</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Force-Motors/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="dev/includes/images-design/Brand-logo/force-brand-logo-270x12.png" alt="bus Battery online">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Force-Motors/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Force</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Ford/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="dev/includes/images-design/Brand-logo/ford-brand-logo-270x12.png" alt="bus Battery bangalore">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Ford/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Ford</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Hindustan-Motors/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="dev/includes/images-design/Brand-logo/hm-morters-brand-logo-270x12.png" alt="Exide Inverter Battery">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Hindustan-Motors/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Hindustan Motors</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Honda/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="dev/includes/images-design/Brand-logo/honda-brand-logo-270x12.png" alt="Exide Honda Batteries">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Honda/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Honda</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Hyundai/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="dev/includes/images-design/Brand-logo/hyundai-brand-logo-270x12.png" alt="Exide Hyundai Batteries">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Hyundai/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Hyundai</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/ICML/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="dev/includes/images-design/Brand-logo/iclm-brand-logo-270x12.png" alt="Car Battery chennai">
								</a>
							</div>
							<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/ICML/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >ICML</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Jaugar/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="dev/includes/images-design/Brand-logo/jaguar-brand-logo-270x12.png" alt="bus Battery mumbai">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Jaugar/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Jaguar</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/KIA-Motors/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="dev/includes/images-design/Brand-logo/KIA-Morters-brand-logo-270x12.png" alt="car Battery Mumbai">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/KIA-Motors/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >KIA Motors</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								 <a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Nissan/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="dev/includes/images-design/Brand-logo/nissan-brand-logo-270x12.png" alt="car Battery Pune">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Nissan/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Nissan</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Premier/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="dev/includes/images-design/Brand-logo/premier-brand-logo-270x12.png" alt="car Battery Delhi">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Premier/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Premier</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Volks-Wagen/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="dev/includes/images-design/Brand-logo/volkswagen-brand-logo-270x12.png" alt="car Battery Gurgoan">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Volks-Wagen/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Volkswagen</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Mahindra-and-Mahindra/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="dev/includes/images-design/Brand-logo/mahindra-brand-logo-270x12.png" alt="car Battery Noida">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Mahindra-and-Mahindra/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Mahindra</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Maruti-Suzuki/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="dev/includes/images-design/Brand-logo/maruti-suzuki-brand-logo-270x12.png" alt="car Battery Calcutta">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Maruti-Suzuki/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Maruti Suzuki</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Mercedes-Benz/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="dev/includes/images-design/Brand-logo/mercedes-benz-brand-logo-270x12.png" alt="car Battery Chandigarh">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Mercedes-Benz/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Mercedes Benz</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/GM-OPEL/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="dev/includes/images-design/Brand-logo/gm-opel-brand-logo-270x12.png" alt="Home Inverter">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/GM-OPEL/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >GM OPEL</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Peuguot/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="dev/includes/images-design/Brand-logo/peugeot-brand-logo-270x12.png" alt="Inverter Battery">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Peuguot/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Peuguot</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Porsche/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="dev/includes/images-design/Brand-logo/porsehe-brand-logo-270x12.png" alt="UPS Battery">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Porsche/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Porsche</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Renault/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="dev/includes/images-design/Brand-logo/renault-brand-logo-270x12.png" alt="Home UPS">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Renault/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Renault</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Skoda/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="dev/includes/images-design/Brand-logo/skoda-brand-logo-270x12.png" alt="inverter battery online">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Skoda/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Skoda</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Tata/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="dev/includes/images-design/Brand-logo/tata-brand-logo-270x12.png" alt="Inverter battery Hyderabad">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Tata/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Tata</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Volvo/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="dev/includes/images-design/Brand-logo/volvo-brand-logo-270x12.png" alt="Inverter battery Bangalore">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Volvo/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Volvo</a> </p>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Toyota/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >
									<img class="width_100_class" src="dev/includes/images-design/Brand-logo/toyota-brand-logo-270x12.png" alt="Inverter battery Mumbai">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Toyota/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/" >Toyota</a> </p>
						</div>
					  </div>
					</div>
				</section>                                  
                              </div>
                              <div id ="bike_brands_div" style="display:none" class="tab-content product-tab-content jump">
<section class="latest-blog">
								<div class="pro-manifacture-custom ">
								<div class="col-xs-12 col-sm-12 col-md-12 width_100_class" style="text-align: -webkit-center;margin-top: 5px;margin-bottom: 10px;">
							<h3 style="color: #e2070f;">Bike Brands</h3>
						</div>
							
							<div class="col-xs-6 col-sm-2 col-md-2">
								<div class="prop">
									<div class="blog-img-height-custom blog-img"> 
										<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/Hero-MotoCorp/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">
											<img src="dev/includes/images-design/Brand-logo/hero-motocorp-brand-logo-270x12.png" alt="Hero-MotoCorp Company Logo" style="width: 100%;">
										</a>
									</div>
									<h2 class="text-align-center-custom" ><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/Hero-MotoCorp/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Hero MotoCorp</a> </h2>
								</div>
							</div>
							<div class="col-xs-6 col-sm-2 col-md-2">
								<div class="prop">
									<div class="blog-img-height-custom blog-img"> 
										<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/Honda/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">
											<img src="dev/includes/images-design/Brand-logo/honda-bike-brand-logo-270x12.png" alt="Honda Company Logo" style="width: 100%;">
										</a>
									</div>
									<h2 class="text-align-center-custom" ><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/Honda/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Honda</a> </h2>
								</div>
							</div>
							<div class="col-xs-6 col-sm-2 col-md-2">
								<div class="prop">
									<div class="blog-img-height-custom blog-img"> 
										<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/Bajaj/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">
											<img src="dev/includes/images-design/Brand-logo/bajaj-brand-logo-270x12.png" alt="Bajaj Company Logo" style="width: 100%;">
										</a>
									</div>
									<h2 class="text-align-center-custom" ><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/Bajaj/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Bajaj</a> </h2>
								</div>
							</div>
							<div class="col-xs-6 col-sm-2 col-md-2">
								<div class="prop">
									<div class="blog-img-height-custom blog-img"> 
										<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/Yamaha/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">
											<img src="dev/includes/images-design/Brand-logo/yamaha-brand-logo-270x12.png" alt="Yamaha Company Logo" style="width: 100%;">
										</a>
									</div>
									<h2 class="text-align-center-custom" ><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/Yamaha/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Yamaha</a> </h2>
								</div>
							</div>
							<div class="col-xs-6 col-sm-2 col-md-2">
								<div class="prop">
									<div class="blog-img-height-custom blog-img"> 
										<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/Royal-Enfield/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">
											<img src="dev/includes/images-design/Brand-logo/royal-enfield-brand-logo-270x12.png" alt="Royal-Enfield Company Logo" style="width: 100%;">
										</a>
									</div>
									<h2 class="text-align-center-custom" ><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/Royal-Enfield/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Royal Enfield</a> </h2>
								</div>
							</div>
							<div class="col-xs-6 col-sm-2 col-md-2">
								<div class="prop">
									<div class="blog-img-height-custom blog-img"> 
										<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/TVS/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">
											<img src="dev/includes/images-design/Brand-logo/tvs-brand-logo-270x12.png" alt="TVS Company Logo" style="width: 100%;">
										</a>
									</div>
									<h2 class="text-align-center-custom" ><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/TVS/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">TVS</a> </h2>
								</div>
							</div>
							<div class="col-xs-6 col-sm-2 col-md-2">
								<div class="prop">
									<div class="blog-img-height-custom blog-img"> 
										<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/Kinetic/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">
											<img src="dev/includes/images-design/Brand-logo/kinetic-brand-logo-270x12.png" alt="Kinetic Company Logo" style="width: 100%;">
										</a>
									</div>
									<h2 class="text-align-center-custom" ><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/Kinetic/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Kinetic</a> </h2>
								</div>
							</div>
							<div class="col-xs-6 col-sm-2 col-md-2">
								<div class="prop">
									<div class="blog-img-height-custom blog-img"> 
										<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/LML/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">
											<img src="dev/includes/images-design/Brand-logo/lml-brand-logo-270x12.png" alt="LML Company Logo" style="width: 100%;">
										</a>
									</div>
									<h2 class="text-align-center-custom" ><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/LML/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">LML</a> </h2>
								</div>
							</div>
							<div class="col-xs-6 col-sm-2 col-md-2">
								<div class="prop">
									<div class="blog-img-height-custom blog-img"> 
										<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/Suzuki/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">
											<img src="dev/includes/images-design/Brand-logo/suzuki-brand-logo-270x12.png" alt="Suzuki Company Logo" style="width: 100%;">
										</a>
									</div>
									<h2 class="text-align-center-custom" ><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/Suzuki/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Suzuki</a> </h2>
								</div>
							</div>
							<div class="col-xs-6 col-sm-2 col-md-2">
								<div class="prop">
									<div class="blog-img-height-custom blog-img"> 
										<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/Mahindra-and-Mahindra/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">
											<img src="dev/includes/images-design/Brand-logo/mahindra-brand-logo-270x12.png" alt="Mahindra-and-Mahindra Company Logo" style="width: 100%;">
										</a>
									</div>
									<h2 class="text-align-center-custom" ><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/Mahindra-and-Mahindra/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Mahindra and Mahindra</a> </h2>
								</div>
							</div>
							<div class="col-xs-6 col-sm-2 col-md-2">
								<div class="prop">
									<div class="blog-img-height-custom blog-img"> 
										<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/Kandaa-Motors/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">
											<img src="dev/includes/images-design/Brand-logo/kandaa-motors-opel-brand-logo-270x12.png" alt="Kandaa-Motors Company Logo" style="width: 100%;">
										</a>
									</div>
									<h2 class="text-align-center-custom" ><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/Kandaa-Motors/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Kandaa Motors</a> </h2>
								</div>
							</div>
							<div class="col-xs-6 col-sm-2 col-md-2">
								<div class="prop">
									<div class="blog-img-height-custom blog-img"> 
										<a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/Piaggio/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">
											<img src="dev/includes/images-design/Brand-logo/piaggio-brand-logo-270x12.png" alt="Piaggio Company Logo" style="width: 100%;">
										</a>
									</div>
									<h2 class="text-align-center-custom" ><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/Piaggio/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Piaggio</a> </h2>
								</div>
							</div>
						</div>
			</section>                      
            </div>
                            </div>
                            <!-- Tab-Content End -->
                            
                        </div>
                        <!-- New Pro Content End -->                        
                    </div>
            </div>
            <!-- Container End -->
        </div>
        <!-- New Products End -->
        
        
        <div class="new-products pb-30">
            <div class="container" style="-webkit-box-shadow: 6px 7px 46px -5px rgba(130,130,130,.96);padding: 15px;">
              <div class="group-title">
                    <h2>Why You Choose Book Battery</h2>
                </div>
                <div class="row">
                     <div class="col-xl-12 col-lg-12  order-lg-2" style="padding: 0px 25px 25px 25px;text-align: justify;">
                        <!-- New Pro Content End -->
                         <p>
                  BookBattery is a leading online battery store in India for car batteries, inverter batteries, inverters etc. Looking to buy car battery online or Inverter battery online? Be it Amaron or Exide batteries/inverters or any other top brand, BookBattery is a one stop-shop that sells 100% genuine car/inverter batteries of all major battery brands in India.
              </p>
              <p>
                BookBattery has 24/7 support which helps customers who searches car batteries online and to provide them the best solution. We are committed to provide assistance as well as an excellent product variety in a price point that is highly competitive and unprecedented. Some customers who wants to purchase batteries for car online have doubt in mind about the after sales service. We want to clear one thing that automobile batteries don't require any type of servicing; charging of battery will help only when situations like alternator failure, Stand by mode etc. arises. Battery can never be re-used or refurbished. If you want to buy car battery online, contact BookBattery, the one stop solution for all your needs.
              </p>

              <p>
                <p>Month and Year of manufacture are mentioned on every car battery &amp; inverter battery irrespective of the brand, thus genuineness of battery can be confirmed.</p>
              </p>
              <p>
                We take utmost pride to not only provide the best quality product at the best discounted price but also to provide FREE delivery* and installation in just few hours of an order placed by the customer. Contact or whats app our dedicated call center number +91-96034-67559 to buy car battery online or if you have any question regarding our products and you can also place an order via phone or online.
              </p>
              <p>
                <b>Express Delivery is available in Mumbai and Bangalore. Express delivery is available area wise in Mumbai ( Andheri, Bandra, Borivali, Colaba, Dadar, Goregaon, Juhu, Mulund, Powai, Santacruz, Worli...) and in Bangalore ( Banashankari, Basaveshwaranagar, BTM layout, Electronic city, Frazer Town, Hebbal, HSR Layout, Marathahalli, Mahadevpura, Malleswaram, K R Puram, Kormangala, RT nagar, Rajajinagar, Whitefield...) For any enquiry regarding delivery time call us on 9603467559.</b>
              </p>
                            
                        </div>
                        <!-- New Pro Content End -->                        
                    </div>
                    
            </div>
            <!-- Container End -->
   </div>
<!-- New Products End -->

           <input type="hidden" name="publicUrl" id='publicUrl' value="<%=publicUrl%>">
       
  <!---################################## JS Include Starts  ################################------>
	<jsp:include page = "assets/includes/includes_footer.jsp" />
<!---################################## JS Include Ends  ################################------>
            </div>
    <!-- Wrapper End -->
    <!-- jquery 3.12.4 -->
            <!---################################## JS Include Starts  ################################------>
	<jsp:include page = "assets/includes/includes_js.jsp" />
<!---################################## JS Include Ends  ################################------>
<script>

$('#car_brands_butn').click(function(){ 
	
	$('#bike_brands_div').hide();
	$('#car_brands_div').show();
	$('#bike_brands_div_h').hide();
	$('#car_brands_div_h').show();
	

	}); 
$('#bike_brands_butn').click(function(){ 
	$('#car_brands_div').hide();
	$('#bike_brands_div').show();
	$('#car_brands_div_h').hide();
	$('#bike_brands_div_h').show();
}); 

</script>
</body>

</html>