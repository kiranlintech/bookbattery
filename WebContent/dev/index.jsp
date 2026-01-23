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
	<jsp:include page = "includes/jsp/includes_css.jsp" />
<!---################################## CSS Include Ends  ################################------>

</head>
<body>
<div class="page">

<!---################################## Header Include Starts  ################################------>
	<jsp:include page = "includes/jsp/includes_header.jsp" />
<!---################################## Header Include Ends  ################################------>
     
  <div class="container">
    <div class="slider-section">
		<div class="slider-intro">
			<div class="the-slideshow slideshow-wrapper">
				<ul class="slideshow" style="overflow: hidden;">
				<li class="slide">
				<p><a href="#"> <img src="https://www.BookBattery.com/bookbattery/images/Exide_Batteries.png"  style="width: 100%;" alt="Banner Image" /></a></p>
				<div class="caption light1 top-right">
					<div class="caption-inner">
					</div>
				</div>
				</li>
				<li class="slide">
				<p><a href="#"> <img src="https://www.BookBattery.com/bookbattery/images/BookBattery_Amaron.png"  style="width: 100%;" alt="Banner Image1" /></a></p>
				<div class="caption light1 top-right">
					<div class="caption-inner">
					</div>
				</div>
				</li>
				<li class="slide">
				<p><a title="#" href="#"> <img src="https://www.BookBattery.com/bookbattery/images/Batteries_Reasonable_Price.png"  style="width: 100%;" alt="Banner Image"/> </a></p>
				<div class="caption light1 top-right">
				<div class="caption-inner">
				</div>
				</div>
				</li>
				</ul>
				<a id="home-slides-prev" class="backward browse-button" href="#">previous</a> <a id="home-slides-next" class="forward browse-button" href="#">next</a>
				<div id="home-slides-pager" class="tab-pager tab-pager-img tab-pager-ring-lgray">&nbsp;</div>
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
			<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
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
			</div>
			<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">
				<div class="form-group">
					<label for="product_type" class="width-100-custom">&nbsp; &nbsp; </label>
					<button onclick="AskforMobileNumber()" class="button btn-cart" type="button">
						<span><i class="icon-basket"></i> Find Battery</span>
					</button>	
				</div>
			</div>
		</div>
		</div> 
      </div>
    </div>
	  <!-- main container -->
  <section class="col1-layout home-content-container">
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
					<div class="blog-img"> <img src="/bookbattery/dev/includes/images-design/car-battery-icon-BookBattery.png" alt="Car Battery BookBattery" style="width: 100%;">
					  <div class="mask"> <a class="info" href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/">More</a> </div>
					</div>
					<h2 class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/">Car Battery</a> </h2>
                </div>
                <div class="item">
					<div class="blog-img"> <img src="/bookbattery/dev/includes/images-design/bike-battery-icon-BookBattery.png" alt="bike Battery BookBattery" style="width: 100%;">
					  <div class="mask"> <a class="info" href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/">More</a> </div>
					</div>
					<h2 class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/">Bike Battery</a> </h2>
                </div>
                <div class="item">
					<div class="blog-img"> <img src="/bookbattery/dev/includes/images-design/inverter-icon-BookBattery.png" alt="Auto Battery BookBattery" style="width: 100%;">
					  <div class="mask"> <a class="info" href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Inverter/">More</a> </div>
					</div>
					<h2 class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Inverter/">Inverter</a> </h2>
                </div>
                <div class="item">
					<div class="blog-img"> <img src="/bookbattery/dev/includes/images-design/inverter-battery-icon-BookBattery.png" alt="Auto Battery BookBattery" style="width: 100%;">
					  <div class="mask"> <a class="info" href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Inverter-Batteries/">More</a> </div>
					</div>
					<h2 class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Inverter-Batteries/">Inverter Battery</a> </h2>
                </div>
                <div class="item">
					<div class="blog-img"> <img src="/bookbattery/dev/includes/images-design/three-wheeler-icon-BookBattery.png" alt="Auto Battery BookBattery" style="width: 100%;">
					  <div class="mask"> <a class="info" href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Three-Wheeler-Batteries/">More</a> </div>
					</div>
					<h2 class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Three-Wheeler-Batteries/">Three Wheeler Battery</a> </h2>
                </div>
                <div class="item">
					<div class="blog-img"> <img src="/bookbattery/dev/includes/images-design/bus-battery-icon-BookBattery.png" alt="bus Battery BookBattery" style="width: 100%;">
					  <div class="mask"> <a class="info" href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bus-Batteries/">More</a> </div>
					</div>
					<h2 class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bus-Batteries/">Bus Battery</a> </h2>
                </div>
                <div class="item">
					<div class="blog-img"> <img src="/bookbattery/dev/includes/images-design/tractor2-battery-icon-BookBattery.png" alt="bus Battery BookBattery" style="width: 100%;">
					  <div class="mask"> <a class="info" href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Tractor-Batteries/">More</a> </div>
					</div>
					<h2 class="text-align-center-custom"><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Tractor-Batteries/">Tractor Battery</a> </h2>
                </div>
             </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
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
						<div class="col-xs-12 col-sm-12 col-md-12" style="width: 100%;">
							<h4>Car Manufacturers</h4>
						</div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Audi/">
									<img src="/bookbattery/dev/includes/images-design/Brand-logo/audi-logo-BookBattery-270x12.png" alt="Car Battery BookBattery" style="width: 100%;">
								</a>
							</div>
							<h2 class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Audi/" >Audi</a> </h2>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/BMW/" > 
									<img src="/bookbattery/dev/includes/images-design/Brand-logo/bmw-logo-BookBattery-270x12.png" alt="Car Battery BookBattery" style="width: 100%;">
								</a> 
							</div>
							<h2 class="text-align-center-custom"><a   href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/BMW/" >BMW</a> </h2>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2  col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Chevrolet/" >
									<img src="/bookbattery/dev/includes/images-design/Brand-logo/chevrolet-logo-BookBattery-270x12.png" alt="Car Battery BookBattery" style="width: 100%;">
								</a> 
							</div>
							<h2 class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Chevrolet/" >Chevrolet</a> </h2>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2  col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Fiat/" >
									<img src="/bookbattery/dev/includes/images-design/Brand-logo/fiat-logo-BookBattery-270x12.png" alt="Car Battery BookBattery" style="width: 100%;">
								</a>
							</div>
						<h2 class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Fiat/" >Fiat</a> </h2>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2  col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Alfa-Romeo/" >
									<img src="/bookbattery/dev/includes/images-design/Brand-logo/alfa-romeo-logo-BookBattery-270x12.png" alt="Car Battery 	BookBattery" style="width: 100%;">
								</a> 
							</div>
							<h2 class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Alfa-Romeo/" >Alfa Romeo</a> </h2>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Daewoo/" >
									<img src="/bookbattery/dev/includes/images-design/Brand-logo/daewoo-logo-BookBattery-270x12.png" alt="bus Battery BookBattery" style="width: 100%;">
								</a> 
							</div>
						<h2 class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Daewoo/" >Daewoo</a> </h2>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Force-Motors/" >
									<img src="/bookbattery/dev/includes/images-design/Brand-logo/force-logo-BookBattery-270x12.png" alt="bus Battery BookBattery" style="width: 100%;">
								</a> 
							</div>
						<h2 class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Force-Motors/" >Force</a> </h2>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Ford/" >
									<img src="/bookbattery/dev/includes/images-design/Brand-logo/ford-logo-BookBattery-270x12.png" alt="bus Battery BookBattery" style="width: 100%;">
								</a>
							</div>
						<h2 class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Ford/" >Ford</a> </h2>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Hindustan-Motors/" >
									<img src="/bookbattery/dev/includes/images-design/Brand-logo/hm-morters-logo-BookBattery-270x12.png" alt="bus Battery BookBattery" style="width: 100%;">
								</a> 
							</div>
						<h2 class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Hindustan-Motors/" >Hindustan Motors</a> </h2>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Honda/" >
									<img src="/bookbattery/dev/includes/images-design/Brand-logo/honda-logo-BookBattery-270x12.png" alt="bus Battery BookBattery" style="width: 100%;">
								</a> 
							</div>
						<h2 class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Honda/" >Honda</a> </h2>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Hyundai/" >
									<img src="/bookbattery/dev/includes/images-design/Brand-logo/hyundai-logo-BookBattery-270x12.png" alt="bus Battery BookBattery" style="width: 100%;">
								</a>
							</div>
						<h2 class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Hyundai/" >Hyundai</a> </h2>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/ICML/" >
									<img src="/bookbattery/dev/includes/images-design/Brand-logo/iclm-logo-BookBattery-270x12.png" alt="bus Battery BookBattery" style="width: 100%;">
								</a>
							</div>
							<h2 class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/ICML/" >ICLM</a> </h2>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Jaugar/" >
									<img src="/bookbattery/dev/includes/images-design/Brand-logo/jaguar-logo-BookBattery-270x12.png" alt="bus Battery BookBattery" style="width: 100%;">
								</a> 
							</div>
						<h2 class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Jaugar/" >Jaguar</a> </h2>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/KIA-Motors/" >
									<img src="/bookbattery/dev/includes/images-design/Brand-logo/KIA-Morters-logo-BookBattery-270x12.png" alt="Auto Battery BookBattery" style="width: 100%;">
								</a>
							</div>
						<h2 class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/KIA-Motors/" >KIA Motors</a> </h2>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								 <a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Nissan/" >
									<img src="/bookbattery/dev/includes/images-design/Brand-logo/nissan-logo-BookBattery-270x12.png" alt="Auto Battery BookBattery" style="width: 100%;">
								</a> 
							</div>
						<h2 class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Nissan/" >Nissan</a> </h2>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Premier/" >
									<img src="/bookbattery/dev/includes/images-design/Brand-logo/premier-logo-BookBattery-270x12.png" alt="Auto Battery BookBattery" style="width: 100%;">
								</a>
							</div>
						<h2 class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Premier/" >Premier</a> </h2>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Volks-Wagen/" >
									<img src="/bookbattery/dev/includes/images-design/Brand-logo/volkswagen-logo-BookBattery-270x12.png" alt="Auto Battery BookBattery" style="width: 100%;">
								</a> 
							</div>
						<h2 class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Volks-Wagen/" >Volkswagen</a> </h2>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Mahindra-&-Mahindra/" >
									<img src="/bookbattery/dev/includes/images-design/Brand-logo/mahindra-logo-BookBattery-270x12.png" alt="Auto Battery BookBattery" style="width: 100%;">
								</a>
							</div>
						<h2 class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Mahindra-&-Mahindra/">Mahindra</a> </h2>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Maruti-Suzuki/" >
									<img src="/bookbattery/dev/includes/images-design/Brand-logo/maruti-suzuki-logo-BookBattery-270x12.png" alt="Auto Battery BookBattery" style="width: 100%;">
								</a>
							</div>
						<h2 class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Maruti-Suzuki/" >Maruti Suzuki</a> </h2>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Mercedes-Benz/" >
									<img src="/bookbattery/dev/includes/images-design/Brand-logo/mercedes-benz-logo-BookBattery-270x12.png" alt="Auto Battery BookBattery" style="width: 100%;">
								</a> 
							</div>
						<h2 class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Mercedes-Benz/" >Mercedes Benz</a> </h2>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/GM-OPEL/" >
									<img src="/bookbattery/dev/includes/images-design/Brand-logo/gm-opel-logo-BookBattery-270x12.png" alt="Auto Battery BookBattery" style="width: 100%;">
								</a>
							</div>
						<h2 class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/GM-OPEL/" >GM OPEL</a> </h2>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Peuguot/" >
									<img src="/bookbattery/dev/includes/images-design/Brand-logo/peugeot-logo-BookBattery-270x12.png" alt="Auto Battery BookBattery" style="width: 100%;">
								</a> 
							</div>
						<h2 class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Peuguot/" >Peuguot</a> </h2>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Porsche/" >
									<img src="/bookbattery/dev/includes/images-design/Brand-logo/porsehe-logo-BookBattery-270x12.png" alt="Auto Battery BookBattery" style="width: 100%;">
								</a> 
							</div>
						<h2 class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Porsche/" >Porsche</a> </h2>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Renault/" >
									<img src="/bookbattery/dev/includes/images-design/Brand-logo/renault-logo-BookBattery-270x12.png" alt="Auto Battery BookBattery" style="width: 100%;">
								</a>
							</div>
						<h2 class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Renault/" >Renault</a> </h2>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Skoda/" >
									<img src="/bookbattery/dev/includes/images-design/Brand-logo/skoda-logo-BookBattery-270x12.png" alt="Auto Battery BookBattery" style="width: 100%;">
								</a> 
							</div>
						<h2 class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Skoda/" >Skoda</a> </h2>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Tata/" >
									<img src="/bookbattery/dev/includes/images-design/Brand-logo/tata-logo-BookBattery-270x12.png" alt="Auto Battery BookBattery" style="width: 100%;">
								</a>
							</div>
						<h2 class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Tata/" >Tata</a> </h2>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img">
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Volvo/" >
									<img src="/bookbattery/dev/includes/images-design/Brand-logo/volvo-logo-BookBattery-270x12.png" alt="Auto Battery BookBattery" style="width: 100%;">
								</a>
							</div>
						<h2 class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Volvo/" >Volvo</a> </h2>
						</div>
					  </div>
					  <div class="col-xs-6 col-sm-2 col-md-2">
						<div class="prop">
							<div class="blog-img-height-custom blog-img"> 
								<a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Toyota/" >
									<img src="/bookbattery/dev/includes/images-design/Brand-logo/toyota-logo-BookBattery-270x12.png" alt="Auto Battery BookBattery" style="width: 100%;">
								</a> 
							</div>
						<h2 class="text-align-center-custom"><a href="<%=publicUrl%>/bookbattery/manufacturer/Car-Batteries/Toyota/" >Toyota</a> </h2>
						</div>
					  </div>
					</div>
				</section>
			</div> 
		</div>
    </div>
 </div>
<div class="">
   <div class="container">
		<div class="slider-section">
			<div class="cat-block pro-block">
				<div class="pro1-block">
					<ul class="top-cat-box">
						<li>
						<div><img src="https://www.BookBattery.com/services/includes/images/BookBattery_tyres.png" alt="Tyres STORE"></div>
						<h2>Tyres STORE</h2>
						<a href="#">Buy Now</a></li>
						<li>
						<div><img src="https://www.BookBattery.com/tyres/includes/images/tyres%20car%20service.png" alt="SERVICE CENTRES"></div>
						<h2>SERVICE CENTRES</h2>
						<a href="#">Book Now</a></li>
						<li>
						<div><img src="https://www.BookBattery.com/tyres/includes/images/tyres%20car%20insurance.png" alt="INSURANCE STORE"></div>
						<h2>INSURANCE STORE</h2>
						<a href="#">Book Now</a></li>
						<li>
						<div><img src="https://www.BookBattery.com/tyres/includes/images/car_accsoeries.png" alt="ACCESSORIES STORE"></div>
						<h2>ACCESSORIES STORE</h2>
						<a href="#">Buy Now</a></li>
					</ul>
				</div>
			</div>           
		</div>        
	 </div>        
</div>
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
	<jsp:include page = "/assets/includes/includes_js.jsp" />
<!---################################## CSS Include Ends  ################################------>
<input type="hidden" name="MobileNumberPopUpCheck" id='MobileNumberPopUpCheck' value="HomePage">
<input type="hidden" name="user_mobile_number_cookie_tmp" id='user_mobile_number_cookie_tmp' value="">
<!---################################## JS Include Starts  ################################------>
	<jsp:include page = "includes/jsp/includes_footer.jsp" />
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
