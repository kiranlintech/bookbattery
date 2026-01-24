	<%-- 
Document   		 : product_details.jsp
Created on 		 : Sep 15, 2016, 10:14:12 AM
Author     		 : BookBattery
Copyright Notice : BookBattery Confidential.
Document         : This jsp is used as Battery single Product details page of BookBattery Batteries.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.*,com.ngit.javabean.loglevel.LogLevel,javax.servlet.ServletContext,java.util.Properties,java.io.FileInputStream"%>
<%response.setHeader("Pragma","no-cache"); response.setHeader("Cache-Control","no-store"); response.setHeader("Expires","0"); response.setDateHeader("Expires",-1); %>
<%
Properties propsMOPConfig = new Properties();
ServletContext context = getServletContext();
FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
propsMOPConfig.load(fin1); 
fin1.close(); 
String publicUrl = (propsMOPConfig.getProperty("publicUrl")!=null)?propsMOPConfig.getProperty("publicUrl"):"";
String baseURL = (propsMOPConfig.getProperty("baseURL")!=null)?propsMOPConfig.getProperty("baseURL"):"";
String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?propsMOPConfig.getProperty("baseurldirectory"):"";
String serverURL = (propsMOPConfig.getProperty("serverURL")!=null)?propsMOPConfig.getProperty("serverURL"):"";

Properties product_details = new Properties(); 
FileInputStream product_details_file = new FileInputStream(new File(context.getRealPath("jsp/product_details.html"))); 
product_details.load(product_details_file); 
product_details_file.close();

String titles = "";
String description = "";
String keywords = "";

%>

<%
Vector Vector_Other_Vehicle_List=(Vector)request.getAttribute("Vector_Other_Vehicle_List");
Vector Vector_Get_Similar_Batteries_List=(Vector)request.getAttribute("Vector_Get_Similar_Batteries_List");

String Battery_Type = (String) request.getAttribute("Battery_Type");
String Battery_Brand = (String) request.getAttribute("Battery_Brand");
String Battery_Model = (String) request.getAttribute("Battery_Model");
String Vehicle_Make = (String) request.getAttribute("Vehicle_Make");
String Vehicle_Model = (String) request.getAttribute("Vehicle_Model");
LogLevel.DEBUG(5, new Throwable(), "Vehicle_Model in jsp :" + Vehicle_Model);
String Battery_Capacity = (String) request.getAttribute("Battery_Capacity");
String Battery_Name = (String) request.getAttribute("Battery_Name");
String Battery_Warranty = (String) request.getAttribute("Battery_Warranty");
String Battery_Image = (String) request.getAttribute("Battery_Image");
String Battery_Description = (String) request.getAttribute("Battery_Description");
String Battery_Act_Price = (String) request.getAttribute("Battery_Act_Price");
String Battery_Witbat_Price = (String) request.getAttribute("Battery_Witbat_Price");
String Battery_Ret_Price = (String) request.getAttribute("Battery_Ret_Price");
String Battery_Flag = (String) request.getAttribute("Battery_Flag");
String Stock_Status = (String) request.getAttribute("Stock_Status");
String State = (String) request.getAttribute("State");
String City = (String) request.getAttribute("City");
String FuelType = (String) request.getAttribute("FuelType");
String delivery_cities  = (String) request.getAttribute("delivery_cities");
boolean showdelivery=false;
	if(Battery_Type.equals("Bike Batteries"))
	{
		String Cityselected = (String) request.getAttribute("City");
		LogLevel.DEBUG(5, new Throwable(), "Cityselected in jsp :" + Cityselected);
		String delivery_cities1  = (String) request.getAttribute("delivery_cities");
		LogLevel.DEBUG(5, new Throwable(), "delivery_cities1 in jsp :" + delivery_cities1);

		String citiiesarray []= delivery_cities1.split(",");

		for (int i = 0; i < citiiesarray.length && !showdelivery; i++) {
			String fetchedcity=citiiesarray[i];
			Cityselected=Cityselected.trim();
			fetchedcity=fetchedcity.trim();
		  if (fetchedcity.equals(Cityselected)){
			showdelivery = true;
			break;
		  }
		}
	}
	else
	{}
LogLevel.DEBUG(5, new Throwable(), "showdelivery in jsp :" + showdelivery);
LogLevel.DEBUG(5, new Throwable(), "delivery_cities in jsp :" + delivery_cities);

String State_URL= State.replaceAll(" ", "-");
String City_URL= City.replaceAll(" ", "-");


String Battery_Type_URL= Battery_Type.replaceAll(" ", "-");
String Battery_Brand_URL= Battery_Brand.replaceAll(" ", "-");
String Battery_Model_URL= Battery_Model.replaceAll(" ", "+");

Battery_Warranty= Battery_Warranty.replaceAll("Pro-rata", "<span class='Pro-rata' data-toggle='tooltip' data-placement='top' title='Pro-rata warranty is a kind of partial warranty, If your battery fails in the pro-rata warranty cycle then depending on the value of the battery, you will get discount on the Current MRP on the newly replaced battery.'>Pro-rata</span>");

String Battery_Model_Cat=Battery_Name;
Battery_Model_Cat= Battery_Model_Cat.replaceAll(" ", "-");
Battery_Model_Cat=Battery_Model_Cat.trim();
LogLevel.DEBUG(5,new Throwable(),"Battery_Model_Cat :"+Battery_Model_Cat );
int Battery_Act_Price_int = Integer.parseInt(Battery_Act_Price);
int WithOutOldBattery = Integer.parseInt(Battery_Witbat_Price);
int WithOldBattery_Less = Integer.parseInt(Battery_Ret_Price);
int WithOldBattery = WithOutOldBattery - WithOldBattery_Less;
int Save_Price = Battery_Act_Price_int - WithOldBattery;

int WithOutOldBattery_Percent=(((Battery_Act_Price_int-WithOutOldBattery)*100)/Battery_Act_Price_int);
int WithOldBattery_Percent=(((Battery_Act_Price_int-WithOldBattery)*100)/Battery_Act_Price_int);

LogLevel.DEBUG(5,new Throwable(),"WithOldBattery :"+WithOldBattery );
LogLevel.DEBUG(5,new Throwable(),"WithOutOldBattery :"+WithOutOldBattery );


titles=Battery_Brand+", "+Battery_Model+" | Buy "+Battery_Brand+" "+Battery_Model+" model "+Battery_Type+" Online at Best Price in India | BookBattery.com";
description="Buy 100% genuine "+Battery_Brand+" "+Battery_Model+" model "+Battery_Type+" from Online with Maximum "+Integer.toString(WithOldBattery_Percent)+"% Discount Price - Free Installation and Cash on delivery at BookBattery.com";
keywords=Battery_Brand+" Battery,"+Battery_Model+","+Battery_Type+" "+City+","+Battery_Type+" "+Integer.toString(WithOldBattery_Percent)+"% Discount Price";

%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head itemscope itemtype="http://schema.org/WebSite">
<title itemprop='name'><%=titles%></title>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="og_title" property="og:title" content="<%=titles%>"/>
<meta name="og_site_name" property="og:site_name" content="BookBattery.com"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="<%=publicUrl%>/bookbattery/images/favicon.png" type="image/x-icon">
<meta name='Description' content="<%=description%>"/>
<meta name='Keywords' content='<%=keywords%>'/>


<title itemprop='name'><%=titles%></title>
<link rel="canonical" href="<%=publicUrl%>" itemprop="url">

<!---################################## CSS Include Starts  ################################------>
<link rel="stylesheet" href="/bookbattery/dev/includes/css/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="/bookbattery/dev/includes/css/style.css" type="text/css">

    <!-- Google Font css -->
    <link href="https://fonts.googleapis.com/css?family=Lily+Script+One" rel="stylesheet"> 
    <!-- mobile menu css -->

    <!-- animate css -->
    <link rel="stylesheet" href="/bookbattery/assets/css/animate.css">
    <!-- nivo slider css -->
    <link rel="stylesheet" href="/bookbattery/assets/css/nivo-slider.css">
    <!-- owl carousel css -->
    <link rel="stylesheet" href="/bookbattery/assets/css/owl.carousel.min.css">
    <!-- slick css -->
   <link rel="stylesheet" href="/bookbattery/assets/css/slick.css">
    <!-- price slider css -->
    <link rel="stylesheet" href="/bookbattery/assets/css/jquery-ui.min.css">
    <!-- fontawesome css -->
    <link rel="stylesheet" href="/bookbattery/assets/css/font-awesome.min.css">
     <!-- fancybox css -->
    <link rel="stylesheet" href="/bookbattery/assets/css/jquery.fancybox.css">     
    <!-- default css  -->
    <link rel="stylesheet" href="/bookbattery/assets/css/default.css">
    <!-- responsive css -->
    <link rel="stylesheet" href="/bookbattery/assets/css/responsive.css">
    <link rel="stylesheet" href="/bookbattery/dev/includes/css/custom.css?v=31" type="text/css">
    <link rel="stylesheet" href="/bookbattery/assets/css/meanmenu.min.css">
	<!-- style css -->
    <link rel="stylesheet" href="/bookbattery/assets/css/style.css">

<!---################################## CSS Include Ends  ################################------>
<style>
#ref_code .form-control {
  background: transparent;
  border: none;
  border-bottom: 2px solid #e02d29;
  -webkit-box-shadow: none;
  box-shadow: none;
  border-radius: 0;
  padding: 18px 6px !important;
  width: 90%;
}
#ref_code .form-control:focus {
  -webkit-box-shadow: none;
  box-shadow: none;
}
    .box{
	color: #444;
    padding: 15px;
    display: none;
    background: #ffefd56b;
    margin: 11px 15px 10px;
    border: 2px solid burlywood;
    border-radius: 3px;
    font-size: 14px;
    font-weight: 700;
    }

@media (min-width: 992px)
.d-lg-none {
    display: none!important;
}


</style>
</head>
<body>
<!---################################## Header Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_header.jsp" />
<!---################################## Header Include Ends  ################################------>

  <div class="breadcrumbs" >
    <div class="container">
      <div class="row">
        <ul>

        </ul>
      </div>
    </div>
  </div>
	<ol class="hide" itemscope itemtype="http://schema.org/BreadcrumbList">
	  <li itemprop="itemListElement" itemscope itemtype="http://schema.org/ListItem">
		<a itemscope itemtype="http://schema.org/Thing" itemprop="item" href="<%=publicUrl%><%=baseurldirectory%>manufacturer/<%=Battery_Type_URL%>/" id="<%=publicUrl%><%=baseurldirectory%>manufacturer/<%=Battery_Type_URL%>/">
		  <span itemprop="name"><%=Battery_Type%></span>
		</a>
		<meta itemprop="position" content="1" />
	  </li>
	  <li itemprop="itemListElement" itemscope itemtype="http://schema.org/ListItem">
		<a itemscope itemtype="http://schema.org/Thing" itemprop="item" href="<%=publicUrl%><%=baseurldirectory%>" id="<%=publicUrl%><%=baseurldirectory%>">
		  <span itemprop="name"><%=Battery_Brand%></span>
		</a>
		<meta itemprop="position" content="2" />
	  </li>
	  <li itemprop="itemListElement" itemscope itemtype="http://schema.org/ListItem">
		<a itemscope itemtype="http://schema.org/Thing" itemprop="item"   href="<%=publicUrl%><%=baseurldirectory%>products/<%=Battery_Brand_URL%>/<%=Battery_Model_URL%>/" id="<%=publicUrl%><%=baseurldirectory%>products/<%=Battery_Brand_URL%>/<%=Battery_Model_URL%>/">
		  <span itemprop="name"><%=Battery_Model%></span>
		</a>
		<meta itemprop="position" content="3" />
	  </li>
	</ol>
  <!-- main-container -->
  <section class="main-container" id="Page_Result_ID">
    <div class="main container" itemscope itemtype="http://schema.org/Product">
      <div class="col-main">
        <div class="row" style="display: block;">
          <div class="product-view">
		  	<!-- <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 hidden-lg hidden-md" style="text-align:center;font-size: 15px;color: #e02d29;margin-bottom: 10px;padding: 0px;margin-top: -10px;" >
				 <span style=" font-size: 13px;font-weight: 700;"> Higher Warranty - Higher Life - Higher Price</span><span style=" font-size: 13px;font-weight: 700;"><a id="know_more_mob" class="more" style="text-decoration: none;cursor: pointer;color: #333;"> </br>Click here to Know More.</a></span>
				  <div class="red box"> In BookBattery, higher the warranty, battery life will be more and hence the price will be more for higher warranty BookBattery.</div>
			</div>				
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 hidden-sm hidden-xs" style="text-align:center;font-size: 15px;color: #e02d29;margin-bottom: 10px;padding: 0px;margin-top: -20px;">
				 <span style=" font-size: 13px;font-weight: 700;"> Higher Warranty - Higher Life - Higher Price</span><span style=" font-size: 13px;font-weight: 700;"><a id="know_more" class="more" style="text-decoration: none;cursor: pointer;"> &nbsp;&nbsp;&nbsp;&nbsp; Click here to Know More.</a></span>
				  <div class="red box"> In BookBattery, higher the warranty, battery life will be more and hence the price will be more for higher warranty BookBattery.</div>
			</div> -->
			<div class="product-essential wow bounceInUp animated" style="margin-bottom: 5px;padding-top: 14px;">
			  <div class="slider-items-products">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style=" font-size: 15px; padding: 4px 0px 0px 20px; color: #e02d29;" >
					<span class="blink_me"> <span class="icon-bell" aria-hidden="true"></span>	&nbsp;&nbsp; Below Prices are for <strong><%=City%></strong>, <strong><%=State%></strong>.</span> <span style=" font-size: 13px;font-weight: 700;"><a href="javascript:AskLocationDetails()" style="text-decoration: none;"> Click here to change Location.</a></span>
				</div>
			  </div>
			</div>
            <div class="product-essential">
              <form action="#" method="post" id="product_addtocart_form">
                <input name="form_key" value="6UbXroakyQlbfQzK" type="hidden">
                <div class="product-img-box col-sm-4 col-xs-12">
                  <div class="product-image">
                    <div class="large-image"> <a href="<%=Battery_Image%>" class="cloud-zoom" id="zoom1" rel="useWrapper: false, adjustY:0, adjustX:20"> <img alt="<%=Battery_Brand%> <%=Battery_Model%>" itemprop="image" src="<%=Battery_Image%>"> </a> </div>
                  </div>
                  <!-- end: more-images -->
				  <div class="addthis_inline_share_toolbox"></div>
                  <div class="clear"></div>
                </div>
                <div class="product-shop col-sm-5 col-xs-12">
                  <div class="product-name">
                    <h1 itemprop="name"><%=Battery_Brand%>  <%=Battery_Model%></h1>
                  </div>
                  <div class="short-description">
                    <h2><strong>Quick Overview</strong></h2>
                    <table style=" color: #494848;">
					 <colgroup> <col class="col-xs-5"> <col class="col-xs-7"> </colgroup>
						  <tr>
							  <th>Product Brand </th>
							  <td itemprop="brand">: <%=Battery_Brand%></td>
						  </tr>
						  <tr>
							  <th>Model Number</th>
							  <td itemprop="mpn">: <%=Battery_Model%></td>
						  </tr>
						     <%
							  if(Battery_Name.equals("0") || Battery_Name.equals("")|| Battery_Name.equals("null"))
								{}
								else
								{							
							%>
						  <tr>
							  <th>Product Name</th>
							  <td>: <%=Battery_Name%></td>
						  </tr>
						  	 <%
								}
							 %>
						  <tr>
							  <th>Product Capacity</th>
							  <td>: <%=Battery_Capacity%></td>
						  </tr>
						    <%
							  if(Battery_Flag.equals("0") || Battery_Flag.equals("")|| Battery_Flag.equals("null"))
								{}
								else
								{							
							%>
						  <tr>
							  <th>Product Type</th>
							  <td>: <%=Battery_Flag%></td>
						  </tr>
						  	 <%
								}
							 %>
						  <tr>
							  <th>Product Warranty</th>
							  <td>: <%=Battery_Warranty%></td>
						  </tr>
					  </table>
                  </div>
                  <p class="availability">Availability : 
				    
				 <%
				  if(Stock_Status.equals("No") || Stock_Status.equals("no"))
					{
				%>	
					<span class="label label-danger" itemprop="availability" /> Out of Stock</span>
				<%
					}
					else
					{							
				%>
					<span class="label label-success" itemprop="availability" />In Stock</span>
				<%
					}						
				%>

				  </p>
                  <div class="price-block" style=" padding-top:5px;">
                    <div class="price-box">
					<div> 
						<table><tbody>
						  <tr>
							  <td>
								  <p class="old-price"> 
									  <span class="price-label" style="display: block;">Regular Price: <span id="old-price-48" class="price"><i class="fa fa-inr" aria-hidden="true"></i>  <%=Battery_Act_Price%></span> </span>
								  </p> 
								  <p style="font-weight: bold;"> Save Up to : &nbsp;<i class="fa fa-inr" aria-hidden="true"></i> <%=Save_Price%></p>
							  </td>
							  <td>
								   <span class="label label-danger" style="font-size: 16px;" id="discount_percent"><%=WithOldBattery_Percent%>% off</span> 
							  </td>
						  </tr>
						</tbody></table>
					 </div>
					  <hr width="100" style=" margin: 0px;">
					  <table style=" color: #575656;" id="check_with_without_battery">
						  <tr>
							  <th><input type="radio" class="radio" name="battery_buy_type" value="New" style="margin-right: 8px;"></th>
							  <th class="price-label">Price With Out Old Battery Take Back</th>
							  <td class="price" style="text-align: right;color: #323232;">: &nbsp;<i class="fa fa-inr" aria-hidden="true"></i> <%=Battery_Witbat_Price%> </td>
						  </tr>
						  <tr>
							  <th><input type="radio" class="radio" checked="checked" name="battery_buy_type" value="Replaced"  style="margin-right: 8px;"></th>
							  <th class="price-label">Price With Old Battery Take Back<span style="font-size: 11px;"> (Same Ah)</span> </th>
							  <td itemprop="offers" itemscope itemtype="http://schema.org/Offer" class="price" style="text-align: right;color: #323232;">: &nbsp;<i class="fa fa-inr" aria-hidden="true"></i><span itemprop="price"> <%=WithOldBattery%></span></td>
						  </tr>
					  </table>
                    </div>
					<div>
						  <div style="font-size:1em; margin-top:5px;" id="with-old-note"><b>Note</b>: <span style="color: #8e0707;font-weight: 500;">Price with old battery take back is price of the New Battery - (minus) Scrap price of your Old Battery.</span>  Your Old Battery Should be of Same AH Capacity as the New Battery Ordered. If not Price with Old Battery Take Back will differ. </br> For help <b>Call <a href="tel:+919603467559">+91 96034 67559</a></b></b> or <a href="javascript:Open_Chat()"><span class="label label-default chat-with-us">Chat with Us</span></a> </div>
						  <div style="font-size:1em; margin-top:5px;display:none;" id="without-old-note"><b>Note</b>: Need more assistance while ordering the Battery ??</br> We are here to help you, <b>Call <a href="tel:+919603467559">+91 96034 67559</a></b> or <a href="javascript:Open_Chat()"><span class="label label-default chat-with-us">Chat with Us</span></a> </div></br>
					 </div>
                  </div>
                </div>
				<div class="product-shop col-sm-3 col-xs-12">
					<ul class="styled-list arrow" style="font-size: 15px;color: #727272;">
						<li><strong>Brand New & 100% Genuine </strong></li>
						<%
					    if(Battery_Type.equals("Bike Batteries"))
						{
						%>
							<li><strong>Free Installation</strong></li>
							<%
							if(showdelivery)
							{
							%>
							<li><strong>Bike Batteries are Deliverable <br> with in 10 Km (Extra 150 Rs)</strong></li>
							<%
							}
							else
							{
							%>
								<li><strong>No Delivery for Bike Batteries</strong></li>
							<%
							}
						}
						else
						{
						%>
						<li><strong>Delivered in 4 - 24 Hours*</strong></li>
						<li><strong>Free Delivery & Installation</strong></li>						
						<% } %>
						<li><strong>Cash on Delivery</strong></li>
						
					</ul>
				<div class="add-to-box">
				<div class="price-overview-custom" id="final_price"><i class="fa fa-inr" aria-hidden="true"></i> <%=WithOldBattery%> </div>
				<div style="font-size: 10px;padding-bottom: 1px;text-align: left;">(Prices are inclusive of all taxes)</div>
				
                    
				<%
				  if(Stock_Status.equals("No") || Stock_Status.equals("no"))
					{
				%>	
					<div>
					<h1 class="danger" style="color: #F4511E;" >Out of Stock</h1>
					<input type="hidden"  value="1" maxlength="2" id="product_qty" name="product_qty">
					</div>
				</div>
				<%
					}
					else
					{							
				%>
					
					<div id="ref_code" class="form-group hide" style="margin-top: 15px;text-align: -webkit-left;">
					<table>
					<tbody><tr><label id="ref_code_label" for="name" class="control-label" style="font-size: larger;">Enter Referral Code</label></tr>
					<tr id="inp_ref_code"><td><input type="text" onkeyup="javascript:backspace_press(event.key)" name="name" class="form-control" style="font-size: 20px;" id="refcode" placeholder="XXXXXXX"></td><td><span class="label label-danger" style="font-size: 14px;background-color: #e02d29;cursor: pointer;" id="applycode" onclick='javascript:validate_code()'>Apply</span></td></tr></tbody>
					</table> 
					</div>

					<div class="add-to-cart" style="display: table;">
                      <label for="qty">Quantity:</label>
                      <div class="pull-left">
                        <div class="custom pull-left">
                          <button onClick="var result = document.getElementById('product_qty'); var product_qty = result.value; if( !isNaN( product_qty ) &amp;&amp; product_qty &gt; 1 ) result.value--;return false;" class="reduced items-count Battery_Quantity" type="button"><i class="fa fa-minus">&nbsp;</i></button>
                          <input type="tel" class="input-text qty Battery_Quantity_value form-control yes" title="Battery Quantity" value="1" maxlength="2" id="product_qty" name="product_qty">
						  <!--- <div id="product_qty-error" style="display:none;"></div>  --->
                          <button onClick="var result = document.getElementById('product_qty'); var product_qty = result.value; if( !isNaN( product_qty )) result.value++;return false;" class="increase items-count Battery_Quantity" type="button"><i class="fa fa-plus">&nbsp;</i></button>
                        </div>
                      </div>
                    </div>
                    <div class="add-to-cart" style=" margin-top: 7px;">
						<button class="button btn-cart btn" title="Buy Now" type="button" disabled="disabled" onClick="javascript:OrderProductOnline()" style="font-size: 19px; padding: 15px 25px 15px;  margin-left: 0px;"><span> Please wait</span></button>
                    </div>
				</div>
				<div class="add-to-box">
					<div id="vehicle_make_model_div" style=" margin-top: 7px;"> </div>
					
					<div id="vehicle_make_model_select_div" style="display:none;">
						<input type="hidden" name="product_type" id='product_type' value="<%=Battery_Type%>">
						<input type="hidden" name="product_brand" id='product_brand' value="All">
						<div class="products-vehicle-details-custom" id="vehicle_make_model_select_text_div" > Check this model Supports for your Vehicle or Not </div>
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" id="product_make_div" style=" padding-left: 0px; padding-right: 0px; text-align: left;">
							<div class="form-group">
							  <label for="product_make">Make *:</label>
							  <select class="form-control yes" id="product_make">
								<option value="0">Select Make</option>
							  </select>
							  <div id='product_make-error'style="display:none;"></div>
							</div>
						</div>
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 hide"  id="product_fuel_div"  style=" padding-left: 0px; padding-right: 0px; text-align: left;">
							<div class="form-group">
							  <label for="product_fuel">Fuel Type *:</label>
							  <select class="form-control yes" id="product_fuel">
								<option value="0">Select Fuel Type</option>
							  </select>
							  <div id='product_fuel-error'style="display:none;"></div>
							</div>
						</div>
						<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12"  id="product_model_div"  style=" padding-left: 0px; padding-right: 0px; text-align: left;">
							<div class="form-group">
							  <label for="product_model">Model *:</label>
							  <select class="form-control yes" id="product_model" onchange="javascript:CheckBatteryModelwithVehicle()">
								<option value="0">Select Model</option>
							  </select>
							  <div id='product_model-error'style="display:none;"></div>
							</div>
						</div>
					</div>
                </div>
				
				<%
					}						
				%>

				<div class="add-to-box">
				<%
					if(Battery_Type.equals("Bike Batteries"))
					{
				%>
					<%
					if(showdelivery)
					{
					%>
					<p style=" color: #b40500;padding-top: 5px;text-align: left;"><b>Bike Batteries are Deliverable with in 10 Km (Extra 150 Rs) </br><a href="javascript:void(0);"  class="register" onclick="fnknowmoreaboutBikeBatteryDelivery ();">Know more</a></b></p>
					<%
					}else
					{
					%>
					<p style=" color: #b40500;padding-top: 5px;text-align: left;"><b>No Delivery for Bike Batteries</br><a href="javascript:void(0);"  class="register" onclick="fnknowmoreaboutBikeBatteryDelivery ();">Know more</a></b></p>
					<%
					}
					%>
				<%
					}
				%>
				</div>
				</div>
              </form>
            </div>
			<div class="box-additional">
				<%
				if(Vector_Get_Similar_Batteries_List!=null && Vector_Get_Similar_Batteries_List.size() > 0)
				{
				try{
				%>
				<div class="product-essential">
				  <div class="slider-items-products">
					<div class="new_title center">
					  <h2>Similar Models That Can Fit Your Needs</h2>
					</div>
					<div id="upsell-products-slider" class="product-flexslider hidden-buttons">
					  <div class="slider-items slider-width-col4">
					  
					  <%
				
						for ( int i=0; i<Vector_Get_Similar_Batteries_List.size() ; i++){
							
							Hashtable ht=(Hashtable)Vector_Get_Similar_Batteries_List.get(i);
							
							String bat_brand=String.valueOf(ht.get("bat_brand"));
							String bat_capacity=(String)ht.get("bat_capacity");
							String icon_url=(String)ht.get("icon_url");
							String bat_warranty=(String)ht.get("bat_warranty");
							String bat_model=String.valueOf(ht.get("bat_model"));
							
							String WithOutOldBattery_String=String.valueOf(ht.get("bat_witbat_price"));
							String WithOldBattery_Less_String=String.valueOf(ht.get("bat_ret_price"));
							String bat_act_price=String.valueOf(ht.get("bat_act_price"));
							
							int WithOutOldBattery_Similar_Products = Integer.parseInt(WithOutOldBattery_String);
							int WithOldBattery_Less_Similar_Products = Integer.parseInt(WithOldBattery_Less_String);
							int WithOldBattery_Similar_Products = WithOutOldBattery_Similar_Products - WithOldBattery_Less_Similar_Products;
							
							LogLevel.DEBUG(5,new Throwable(),"WithOldBattery :"+WithOldBattery_Similar_Products );
							LogLevel.DEBUG(5,new Throwable(),"WithOutOldBattery :"+WithOutOldBattery_Similar_Products );
													
							if(icon_url == null)
							{
								icon_url = "./images/noimage.jpg";
							}
							
							String bat_brand_URL=bat_brand;
							String bat_model_URL=bat_model;
							
							bat_brand_URL= bat_brand_URL.replaceAll(" ", "+");
							bat_model_URL= bat_model_URL.replaceAll(" ", "+");

						%> 
					   <div class="item">
						  <div class="item-inner">
							<div class="item-img">
							  <div class="product-block">
								<div class="product-image"> <a href="<%=baseURL%>/bookbattery/products/<%=bat_brand_URL%>/<%=bat_model_URL%>/<%=FuelType%>/<%=State_URL%>/<%=City_URL%>/" style="height: auto;">
								  <figure class="product-display">
									<img src="<%=icon_url%>" class="lazyOwl product-mainpic" alt=" <%=bat_brand%> <%=bat_model%> ( <%=bat_capacity%> )" style="display: block;"> <img class="product-secondpic" alt="<%=bat_brand%> <%=bat_model%> ( <%=bat_capacity%> )" src="<%=icon_url%>"> </figure>
								  </a> </div>
								<div class="product-meta">
								  <div class="product-action"> <a class="addcart" href="<%=baseURL%>/bookbattery/products/<%=bat_brand_URL%>/<%=bat_model_URL%>/<%=FuelType%>/<%=State_URL%>/<%=City_URL%>/" > <i class="icon-external-link-sign">&nbsp;</i> View More </a>
								</div>
							  </div>
							</div>
							<div class="item-info" style="text-align: left;">
							  <div class="info-inner">
								<div class="item-title"> <a href="<%=baseURL%>/bookbattery/products/<%=bat_brand_URL%>/<%=bat_model_URL%>/<%=FuelType%>/<%=State_URL%>/<%=City_URL%>/" title="Click to View "> <%=bat_brand%> <%=bat_model%> ( <%=bat_capacity%> ) </a> </div>
								<div class="item-content">
								  <div class="item-price">
									<div class="price-box">
									  <div class="old-price"> <span class="price-label" style="display: block;">Regular Price: <span class="price" ><i class="fa fa-inr" aria-hidden="true"></i> <%=bat_act_price%> </span> </span></div>
									   <span class="price-label" style="display: block;">Warranty: <span  > <%=bat_warranty%> </span> </span>
										<table style=" color: #494848;font-size: 15px;">
											  <tr>
												  <th>With Out Old Battery Price</th>
												  <th>: <i class="fa fa-inr" aria-hidden="true"></i> <%=WithOutOldBattery_Similar_Products%> </th>
											  </tr>
											  <tr>
												  <th>With Old Battery Price</th>
												  <th>: <i class="fa fa-inr" aria-hidden="true"></i> <%=WithOldBattery_Similar_Products%> </th>
											  </tr>
										</table> 
										<br>
									</div>
								  </div>
								</div>
							  </div>
							</div>
						  </div>
						</div>
					   </div>
					   <%
						}
						%>
					</div>
				  </div>
				</div>
			  </div>
				<%
					}
					catch(Exception e)
					{
							e.printStackTrace();
					}
				}
				%>
		  </div>
		  <div class="clearfix"></div>
		  			
            <div class="col-sm-12 Product_Description">
				 <div class="box-additional">
					 <div class="product-tabs-content-inner">
						<div class="block block-tags">
							<div class="block-title">Product Description :</div>
							<div class="block-content">
								<p itemprop="description">  <%=product_details.getProperty(Battery_Model_Cat)%></p><br />
								<h5><strong> <%=Battery_Brand%> <%=Battery_Model%> Benefits</strong></h4>
								<ul>  <%=product_details.getProperty(Battery_Model_Cat+"-Benefits")%></ul>
								<div><%=product_details.getProperty(Battery_Model_Cat+"-Table")%></div>
							</div>
						</div>
					</div>
				</div>
			</div>
            <div class="col-sm-12">
              <div class="box-additional">
			<%
				if(Vector_Other_Vehicle_List!=null && Vector_Other_Vehicle_List.size() > 0 && Battery_Type!="Inverter Batteries")
				{
				try{
			%>
				 <div class="product-tabs-content-inner">
					<div class="block block-tags">
						<div class="block-title"> Also Recommended for:</div>
						<div class="block-content">
							<ul class="tags-list">
						<%
								String List_Vehicle_Make_tmp="";
								for ( int i=0; i<Vector_Other_Vehicle_List.size() ; i++){
									
									Hashtable ht=(Hashtable)Vector_Other_Vehicle_List.get(i);
									
									String List_Vehicle_Make=String.valueOf(ht.get("veh_name"));
									String List_Vehicle_Model=(String)ht.get("veh_model");
									String List_Battery_Type=(String)ht.get("bat_type");
									
									String List_Battery_Type_Url= List_Battery_Type.replaceAll(" ", "-");
									String List_Vehicle_Make_Url= List_Vehicle_Make.replaceAll(" ", "-");
									String List_Vehicle_Model_Url= List_Vehicle_Model.replaceAll(" ", "+");
									List_Vehicle_Make=List_Vehicle_Make.trim();
									if(!List_Vehicle_Make_tmp.equals(List_Vehicle_Make))
										{
											List_Vehicle_Make_tmp=List_Vehicle_Make.trim();
											
											if (i!=0)
											{
											%>. <br><%
											}
											else{}
										
											%> 
												</br><li><p style="margin-bottom: 0px;"><strong> <%=List_Vehicle_Make%></strong></p> </li>
											<%
										}
									else
										{}
										%>
											<li style="padding-right:5px;font-size: 14px;font-weight: 500;">  <a href="<%=baseURL%>/bookbattery/<%=List_Battery_Type_Url%>/<%=List_Vehicle_Make_Url%>/<%=List_Vehicle_Model_Url%>/<%=FuelType%>/<%=State%>/<%=City%>/"><%=List_Vehicle_Make%> <%=List_Vehicle_Model%>,</a>  </li>
									<%
										}
									%>
						  </ul>
						</div>
					</div>
                  </div>
					<%
						}
						catch(Exception e)
						{
								e.printStackTrace();
						}
					}
					%>
         
            </div>
          </div>
        </div>
      </div>
    </div>
   </div>
  </section>
  <div id="Order_form_PopUp"></div>


  <!--End main-container -->
  <!-- Footer -->
  
<input type="hidden" name="product_type_page" id='product_type_page' value="<%=Battery_Type%>">
<input type="hidden" name="product_make_page" id='product_make_page' value="<%=Vehicle_Make%>">
<input type="hidden" name="product_capacity_page" id='product_capacity_page' value="">
<input type="hidden" name="product_model_page" id='product_model_page' value="<%=Vehicle_Model%>">
<input type="hidden" name="product_brand_page" id='product_brand_page' value="<%=Battery_Brand%>">
<input type="hidden" name="product_state_page" id='product_state_page' value="<%=State%>">
<input type="hidden" name="product_city_page" id='product_city_page' value="<%=City%>">
<input type="hidden" name="product_image" id='product_image' value="<%=Battery_Image%>">
<input type="hidden" name="delivery_cities" id='delivery_cities' value="<%=delivery_cities%>">
<input type="hidden" name="latitude" id='latitude' value="">
<input type="hidden" name="longitude" id='longitude' value="">



<input type="hidden" name="Product_Brand" id='Product_Brand' value="<%=Battery_Brand%>">
<input type="hidden" name="Product_Model" id='Product_Model' value="<%=Battery_Model%>">
<input type="hidden" name="baseURL" id='baseURL' value="<%=baseURL%>">
<input type="hidden" name="publicUrl" id='publicUrl' value="<%=publicUrl%>">
<input type="hidden" name="baseurldirectory" id='baseurldirectory' value="<%=baseurldirectory%>">

<input type="hidden" name="Price_Without_Battery" id='Price_Without_Battery' value="<%=WithOutOldBattery%>">
<input type="hidden" name="WithOutOldBattery_Percent" id='WithOutOldBattery_Percent' value="<%=WithOutOldBattery_Percent%>">
<input type="hidden" name="Price_With_Battery" id='Price_With_Battery' value="<%=WithOldBattery%>">
<input type="hidden" name="WithOldBattery_Percent" id='WithOldBattery_Percent' value="<%=WithOldBattery_Percent%>">

<!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "/dev/includes/jsp/includes_js.jsp" />
    <!-- mobile menu js  -->
    <script src="/bookbattery/assets/js/jquery.meanmenu.min.js"></script>
    <!-- main js -->
    <script src="/bookbattery/assets/js/main.js"></script>
<!---################################## CSS Include Ends  ################################------>

<!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_footer.jsp" />
<!---################################## CSS Include Ends  ################################------>
<!--- <script type="text/javascript" src="//s7.addthis.com/js/300/addthis_widget.js#pubid=ra-57ebc92c78969904"></script>  --->

<script type="text/javascript" src="<%=publicUrl%>/bookbattery/dev/js/product_details_function.js?v=27"></script>
<script>
$(document).ready(function() {
	if (screen.width <= 767)
	{
	 $('html, body').animate({
			scrollTop: $("#mobile-menu").offset().top
		},500);
	}
});
</script>
  
<script> 
	$(document).ready(function()
	{
		if (screen.width <= 767)
		{
			$("body").append( "<div class='btn Buy_Now_Mobile hidden-sm hidden-md hidden-lg' id='Buy_Now_Mobile'><div style='font-size: 3em;margin-top: -27px;color: #e02d29;cursor: pointer;'><span class='icon-chevron-sign-down arrow_down_button' onClick='javascript:Scroll_to_next()'></span></div></div>" );
		}
		

		$(window).scroll(function() {
			var total_height=$(document).height();
			var current_height=$(document).scrollTop();
			var window_height=screen.height-100;
			if(total_height-(current_height+window_height+window_height)<=window_height)
			{
				$( "#Buy_Now_Mobile" ).fadeOut("slow");
			}
			else
			{
				$( "#Buy_Now_Mobile" ).fadeIn("slow");
			}
		});
		
	})
	
	function Scroll_to_next()
	{
		var total_height=$(document).height();
		var current_height=$(document).scrollTop();
		var window_height=screen.height-100;
		var goHeight=current_height+window_height;
		$('html, body').animate({scrollTop:goHeight}, 'slow');
	}
	
	function fnknowmoreaboutBikeBatteryDelivery (){
		  var retVal = confirm("Would you like to know more about Bike Battery Delivery");
		  if( retVal == true ){ 
				window.open('<%=publicUrl%><%=baseurldirectory%>shippingpolicy.jsp#BikeBatteries','aboutus','width=10020, height=400 ,status=1, scrollbars=1, location=0');
		  }
	}


 </script>
 <script src="/bookbattery/dev/js/PlacePicker.js?v=1"></script>
<script type="text/javascript"><!--
google_ad_client = "ca-pub-2783044520727903";
/* jQuery_demo */
google_ad_slot = "2780937993";
google_ad_width = 728;
google_ad_height = 90;
//-->
</script>
<script type="text/javascript">
    $(document).ready(function(){
    	$("#useraddress").PlacePicker({
    		btnClass:"btn btn-xs btn-default",
    		//key:"AIzaSyA-FwkEuPz6efVsc-NszVSD-Fgo4-gXcvA",
    		key:"AIzaSyAFOoC5m2ozoO4ln3ClvDL9uf3l4meyYj0",
    		center: {lat: 17.6868, lng: 83.2185},
    		success:function(data,address){
    			//data contains address elements and
    			//address conatins you searched text
    			//Your logic here
    			//$("#pickup_country").val(data.formatted_address);
    			$("#useraddress").val(data.formatted_address);
    		}
    	});
    });
    $.scrollUp({
        scrollName: 'scrollUp', // Element ID
        topDistance: '550', // Distance from top before showing element (px)
        topSpeed: 1000, // Speed back to top (ms)
        animation: 'fade', // Fade, slide, none
        scrollSpeed: 900,
        animationInSpeed: 1000, // Animation in speed (ms)
        animationOutSpeed: 1000, // Animation out speed (ms)
        scrollText: '<i class="fa fa-angle-up"></i>', // Text for element
        activeOverlay: false // Set CSS color to display scrollUp active point, e.g '#00FFFF'
    });

</script>

</body>
</html>