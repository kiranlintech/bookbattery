<%-- 
Document   		 : configure-your-inverter.jsp
Created on 		 : Sep 15, 2016, 10:14:12 AM
Author     		 : BookBattery
Copyright Notice : BookBattery Confidential.
Document         : This jsp is used as Configure Your Inverter pages of BookBattery Batteries.
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

Properties product_details = new Properties(); 
FileInputStream product_details_file = new FileInputStream("/home/ngit/tomcat/webapps/bookbattery/dev/includes/content-pages/inverter-manufacturer-content.txt"); 
product_details.load(product_details_file); 
product_details_file.close();
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
          <li class="category13"><strong>Configure Your Inverter</strong></li>
        </ul>
      </div>
    </div>
  </div>
    <ol class="hide" itemscope itemtype="http://schema.org/BreadcrumbList">
	  <li itemprop="itemListElement" itemscope itemtype="http://schema.org/ListItem">
		<a itemscope itemtype="http://schema.org/Thing" itemprop="item" href="<%=publicUrl%><%=baseurldirectory%>configure-your-inverter.jsp">
		  <span itemprop="name">Configure Your Inverter</span>
		</a>
		<meta itemprop="position" content="1" />
	  </li>
	</ol>
  <!-- End breadcrumbs --> 
<div class="container">
	<div class="row" style="padding-top: 30px;">
		<div class="product-essential wow bounceInUp animated">
			<div class="slider-items-products">
				<div class="col-xs-12 ">
					<div class="stepwizard row">
						<div class="stepwizard-row setup-panel">
							<div class="stepwizard-step">
								<a href="#configure-your-inverter-step-1" id="nav-configure-your-inverter-step-1" type="button" class="btn btn-primary btn-circle">1</a>
								<p>Select your Inverter Brand</p>
							</div>
							<div class="stepwizard-step">
								<a href="#configure-your-inverter-step-2" id="nav-configure-your-inverter-step-2" type="button" class="btn btn-default btn-circle" disabled="disabled">2</a>
								<p>Select your Inverter Capacity</p>
							</div>
							<div class="stepwizard-step">
								<a href="#configure-your-inverter-step-3" id="nav-configure-your-inverter-step-3" type="button" class="btn btn-default btn-circle" disabled="disabled">3</a>
								<p>Select your Battery Capacity (Optional)</p>
							</div>
							<div class="stepwizard-step hide">
								<a href="#configure-your-inverter-step-4"id="nav-configure-your-inverter-step-4" type="button" class="btn btn-default btn-circle" disabled="disabled">4</a>
								<p>Select your Delivery Location</p>
							</div>
						</div>
					</div>
					<form role="form">
						<div class="row setup-content" id="configure-your-inverter-step-1">
							<div class="col-xs-12">
								<div class="col-md-12" style=" padding-bottom: 20px;">
									<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12" style=" padding: 0px;">
										<div class="form-group">
										  <label for="inverter_brand"><h4>Select your Inverter Brand :</h4></label>
										  <select class="form-control yes" id="inverter_brand">
											<option value="0">Select Brand</option>
										  </select>
										  <div id='inverter_brand-error'style="display:none;"></div>
										</div>
									</div>
									<div class="col-md-offset-8 col-lg-offset-8 col-sm-offset-6 col-xs-12" style=" padding: 0px;">
									</div>
									<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style=" padding: 0px;">
										<div class="form-group">
										  <label for="configure-your-inverter-nextBtn">&nbsp; &nbsp; &nbsp; &nbsp;</label>
											<button type='button' class='button button-next configure-your-inverter-nextBtn-1  btn-lg pull-right' id='order-form-ordernow-btn'>Next</button>
										</div>
									</div>
								</div>
								<hr width="100" style="width: 100%;">
								<div style=" display: block;">
									<div class="col-md-12">
										<div class="col-md-9 col-sm-8 col-xs-12" style=" padding: 0px;">
											<h1 style=" line-height: 2;font-size: 22px;"> About Amaron Inverters</h1>
										</div>
										<div class="col-md-3 col-sm-4 col-xs-12" style=" padding: 0px;">
											<img src="<%=product_details.getProperty("Amaron-logo-path")%>" alt="Amaron Company Logo" style="float: right;display: block; height: 60px;">
										</div>
										<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style=" padding: 0px;">
											<%=product_details.getProperty("Amaron")%>
										</div>
									</div>
									<div class="col-md-12">
										<div class="col-md-9 col-sm-8 col-xs-12" style=" padding: 0px;">
											<h1 style="line-height: 2;font-size: 22px;"> About Exide Inverters</h1>
										</div>
										<div class="col-md-3 col-sm-4 col-xs-12" style=" padding: 0px;">
											<img src="<%=product_details.getProperty("Exide-logo-path")%>" alt="Exide Company Logo" style="float: right;display: block; height: 60px;">
										</div>
										<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style=" padding: 0px;">
											<%=product_details.getProperty("Exide")%>
										</div>
									</div>
									<div class="col-md-12">
										<div class="col-md-9 col-sm-8 col-xs-12" style=" padding: 0px;">
											<h1 style=" line-height: 2;font-size: 22px;"> About Luminous Inverters</h1>
										</div>
										<div class="col-md-3 col-sm-4 col-xs-12" style=" padding: 0px;">
											<img src="<%=product_details.getProperty("Luminous-logo-path")%>" alt="Luminous Company Logo" style="float: right;display: block; height: 60px;">
										</div>
										<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style=" padding: 0px;">
											<%=product_details.getProperty("Luminous")%>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="row setup-content" id="configure-your-inverter-step-2">
							<div class="col-xs-12 nopadding-xs">
								<div class="col-md-12 nopadding-xs" style=" padding-bottom: 20px;">
									<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 hide" style=" padding: 0px;">
										<div class="form-group">
										  <label for="inverter_capacity"><h4>Select your Inverter Capacity *:</h4></label>
										  <select class="form-control yes" id="inverter_capacity">
											<option value="0">Select Capacity</option>
										  </select>
										  <!--- <div id='inverter_capacity-error'style="display:none;"></div>   --->
										</div>
									</div>
									<div class="col-lg-8 col-md-8 col-sm-12 col-xs-12 hide">
										<label for="" class="hidden-xs"><h4>&nbsp; &nbsp; &nbsp; &nbsp;</h4></label>
										<p>Please select your Inverter Capacity depending on your requirement.</p>
									</div>
									<h4>Select your Inverter Capacity</h4>
									<p>Choose your Inverter Capacity depending on your requirement from the bellow Table.</p>
									<div id='inverter_capacity-error'style="display:none;"></div>
									<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style=" padding: 0px;">
										<div id="inverter_capacity_table"></div>
									</div>
									<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pull-right" style="padding: 0px;text-align: right;font-size: 14px;font-weight: 600;color: #318a05;" id="inverter_capacity_table_selected">
										
									</div>
									<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style=" padding: 0px;">
										<div class="form-group">
										  <label for="configure-your-inverter-nextBtn">&nbsp; &nbsp; &nbsp; &nbsp;</label>
											<button type='button' class='button button-back btn-lg pull-left' tabindex='-1' onclick='order_form_Back("nav-configure-your-inverter-step-1");'>Previous</button>
											<button type='button' class='button button-next configure-your-inverter-nextBtn-2  btn-lg pull-right' id='order-form-ordernow-btn'>Next</button>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row setup-content" id="configure-your-inverter-step-3">
							<div class="col-xs-12 nopadding-xs">
								<div class="col-md-12 nopadding-xs" style="text-align: center;">
									<div class='form-group' id='configure-your-inverter-step-3-check' >
										<label for='configure-your-inverter-step-3-yes-no' id='configure-your-inverter-step-3-lable' style=" font-size: 19px;  color: #e02d29;" >Would you like to buy Inverter Battery for XXXXX Inverter </label> </br>
										<input type='hidden' id='configure-your-inverter-step-3-TMP' >
										<input type='radio' class='form-check-input' checked='checked' name='configure-your-inverter-step-3-yes-no' id='configure-your-inverter-step-3-yes' value='Yes' tabindex='-1' style="border: 0px;width: 18px;height: 1.3em;" >
										<span style=" font-size:22px; margin-left: 10px;  color: #06730f; font-weight: 600;"> Yes </span><span style=" font-size: 13px;font-weight: 600;"> (Recommended) </span>
										<input type='radio' class='form-check-input' name='configure-your-inverter-step-3-yes-no' id='configure-your-inverter-step-3-no' value='No' tabindex='-1' style="border: 0px;width: 18px;height: 1.3em;">
										<span  style=" font-size: 22px; margin-left: 10px;  color: #da0505; font-weight: 600;"> No </span>
										<div id='configure-your-inverter-step-3-TMP-error'style='display:none;'></div>
									</div>
								</div>
							</div>
							<hr width="100%">
							<div class="col-xs-12 nopadding-xs">
								<div class="col-md-12 nopadding-xs" style=" padding-bottom: 20px;">
									<div id="configure-your-inverter-step-3-select">
										<div class="col-lg-4 col-md-4 col-sm-12 col-xs-12 hide" style=" padding: 0px;">
											<div class="form-group">
											  <label for="inverter_battery_capacity"><h4>Select your Battery Capacity *:</h4></label>
											  <select class="form-control yes" id="inverter_battery_capacity">
												<option value="0">Select Battery</option>
											  </select>
											</div>
										</div>
										<div class="col-lg-8 col-md-8 col-sm-12 col-xs-12 hide">
											<label for="" class="hidden-xs"><h4>&nbsp; &nbsp; &nbsp; &nbsp;</h4></label>
											<p>Please select your Inverter Battery depending on your requirement.</p>
										</div>
										<h4>Select your Inverter Battery Capacity</h4>
										<p>Choose your Inverter Battery Capacity depending on your requirement from the bellow Table.</p>
										 <div id='inverter_battery_capacity-error'style="display:none;"></div>
										<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style=" padding: 0px;">
											<div id="inverter_battery_capacity_table"></div>
										</div>
										
										<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 pull-right" style="padding: 0px;text-align: right;font-size: 14px;font-weight: 600;color: #318a05;" id="inverter_battery_capacity_table_selected">
										</div>
									</div>
									<div id="configure-your-inverter-step-3-unselect" style="display:none;">
										<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style="padding: 0px;">
											<p style="text-align: center; font-size: 16px; border: 2px solid #e8e8e8; padding: 5px;color: #337ab7; font-weight: 600; margin: 25px 0px 40px 0px;">Order Inverter Battery with us and get Free Delivery at your door step, Free Professional Installation and<br/> 100% Genuine Products with Cash On Delivery.</p>
										</div>
									</div>
									
									<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style=" padding: 0px;">
										<div class="form-group">
										  <label for="configure-your-inverter-nextBtn">&nbsp; &nbsp; &nbsp; &nbsp;</label>
											<button type='button' class='button button-back btn-lg pull-left' tabindex='-1' onclick='order_form_Back("nav-configure-your-inverter-step-2");'>Previous</button>
											<button type='button' class='button button-next configure-your-inverter-nextBtn-3  btn-lg pull-right' id='order-form-ordernow-btn'>Next</button>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row setup-content hide" id="configure-your-inverter-step-4">
							<div class="col-xs-12 nopadding-xs">
								<div class="col-md-12 nopadding-xs" style=" padding-bottom: 20px;">
									<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<div class="form-group">
										  <label for="product_state_conf">Please Select your State *:</label>
										  <select class="form-control yes product_state" id="product_state_conf">
											<option value="0">Select State</option>
										  </select>
										  <div id='product_state_conf-error'style="display:none;"></div>
										</div>
									</div>
									<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
										<div class="form-group">
										  <label for="product_city_conf">Please Select your City *:</label>
										  <select class="form-control yes product_city" id="product_city_conf">
											<option value="0">Select City</option>
										  </select>
										  <div id='product_city_conf-error'style="display:none;"></div>
										</div>
									</div>
									<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style=" padding: 0px;">
										<div class="form-group">
										  <label for="configure-your-inverter-nextBtn">&nbsp; &nbsp; &nbsp; &nbsp;</label>
											<button type='button' class='button button-back btn-lg pull-left' tabindex='-1' onclick='order_form_Back("nav-configure-your-inverter-step-3");'>Back</button>
											<button type='button' class='button button-next configure-your-inverter-nextBtn-4  btn-lg pull-right' id='order-form-ordernow-btn'>Next</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

     

</div>
<!---################################## JS Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_js.jsp" />
<!---################################## JS Include Ends  ################################------>
	<script type="text/javascript" src="<%=publicUrl%>/bookbattery/dev/js/configure-all.js?v=5"></script>
<!---################################## Footer Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_footer.jsp" />
<!---################################## Footer Include Ends  ################################------>
</body>
</html>
