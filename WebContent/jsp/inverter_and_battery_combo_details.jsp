<%-- 
Document   		 : inverter_and_battery_combo_details.jsp
Created on 		 : Sep 15, 2016, 10:14:12 AM
Author     		 : BookBattery
Copyright Notice : BookBattery Confidential.
Document         : This jsp is used as Inverter and Battery Combo single Product details page of BookBattery Batteries.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.*,com.ngit.javabean.loglevel.LogLevel,javax.servlet.ServletContext,java.util.Properties,java.io.FileInputStream"%>
<%response.setHeader("Pragma","no-cache"); response.setHeader("Cache-Control","no-store"); response.setHeader("Expires","0"); response.setDateHeader("Expires",-1); %>
<%
ServletContext context = getServletContext();
Properties propsMOPConfig = new Properties();
FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
propsMOPConfig.load(fin1); 
fin1.close(); 
String publicUrl = (propsMOPConfig.getProperty("publicUrl")!=null)?propsMOPConfig.getProperty("publicUrl"):"";
String baseURL = (propsMOPConfig.getProperty("baseURL")!=null)?propsMOPConfig.getProperty("baseURL"):"";
String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?propsMOPConfig.getProperty("baseurldirectory"):"";
String serverURL = (propsMOPConfig.getProperty("serverURL")!=null)?propsMOPConfig.getProperty("serverURL"):"";

Properties product_details = new Properties(); 
FileInputStream product_details_file      = new FileInputStream(new File(context.getRealPath("jsp/product_details.html")));  
product_details.load(product_details_file); 
product_details_file.close();

String titles = "";
String description = "";
String keywords = "";

%>

<%
Vector Vector_Get_Similar_Inverter=(Vector)request.getAttribute("Vector_Get_Similar_Inverter");

//############# Details of Inverter
String Inverter_Type = "Inverter";
String Inverter_Brand = (String) request.getAttribute("Inverter_Brand");
String Inverter_Model = (String) request.getAttribute("Inverter_Model");
String Inverter_Name = (String) request.getAttribute("Inverter_Name");
String Inverter_Capacity = (String) request.getAttribute("Inverter_Capacity");
String Inverter_Battery_Pic = (String) request.getAttribute("Inverter_Battery_Pic");
String Inverter_Computer = (String) request.getAttribute("Computer");
String Inverter_Fans = (String) request.getAttribute("Fans");
String Inverter_Tubelights = (String) request.getAttribute("Tubelights");
String Inverter_Television = (String) request.getAttribute("Television");
String Inverter_Warranty = (String) request.getAttribute("Inverter_Warranty");
String Inverter_Image = (String) request.getAttribute("Inverter_Image");
String Inverter_Description = (String) request.getAttribute("Inverter_Description");
String Inverter_Actual_Price = (String) request.getAttribute("Inverter_Actual_Price");
String Inverter_Discount_Price = (String) request.getAttribute("Inverter_Discount_Price");
String Inverter_Stock = (String) request.getAttribute("Inverter_Stock");
String State = (String) request.getAttribute("State");
String City = (String) request.getAttribute("City");


Float Inverter_Combo_disc= (Float) request.getAttribute("Inverter_Combo_disc");

Float Final_Inverter_Combo_disc=1-Inverter_Combo_disc;

LogLevel.DEBUG(5,new Throwable(),"Inverter_Combo_disc: "+Inverter_Combo_disc);
LogLevel.DEBUG(5,new Throwable(),"Final_Inverter_Combo_disc: "+Final_Inverter_Combo_disc);

Inverter_Computer=Inverter_Computer.replaceAll(",", "</td><td>");
Inverter_Fans=Inverter_Fans.replaceAll(",", "</td><td>");
Inverter_Tubelights=Inverter_Tubelights.replaceAll(",", "</td><td>");
Inverter_Television=Inverter_Television.replaceAll(",", "</td><td>");

if(Inverter_Battery_Pic==null || Inverter_Battery_Pic==""|| Inverter_Battery_Pic=="0" )
{
	Inverter_Battery_Pic="1";
}
else{
	Inverter_Battery_Pic=Inverter_Battery_Pic;
}
//############# Details of Inverter Battery

String Inverter_Battery_Brand = (String) request.getAttribute("Inverter_Battery_Brand");
String Inverter_Battery_Type = (String) request.getAttribute("Inverter_Battery_Type");
String Inverter_Battery_Model = (String) request.getAttribute("Inverter_Battery_Model");
String Inverter_Battery_Capacity = (String) request.getAttribute("Inverter_Battery_Capacity");
String Inverter_Battery_Warranty = (String) request.getAttribute("Inverter_Battery_Warranty");
String Inverter_Battery_Name = (String) request.getAttribute("Inverter_Battery_Name");
String Inverter_Battery_Image = (String) request.getAttribute("Inverter_Battery_Image");
String Inverter_Battery_Act_Price = (String) request.getAttribute("Inverter_Battery_Act_Price");
String Inverter_Battery_Witbat_Price = (String) request.getAttribute("Inverter_Battery_Witbat_Price");
String Inverter_Battery_Ret_Price = (String) request.getAttribute("Inverter_Battery_Ret_Price");
String Inverter_Battery_Flag = (String) request.getAttribute("Inverter_Battery_Flag");
String Inverter_Battery_Stock = (String) request.getAttribute("Inverter_Battery_Stock");

String State_URL= State.replaceAll(" ", "-");
String City_URL= City.replaceAll(" ", "-");

Inverter_Battery_Warranty= Inverter_Battery_Warranty.replaceAll("Pro-rata", "<span class='Pro-rata' data-toggle='tooltip' data-placement='top' title='Pro-rata warranty is a kind of partial warranty, If your battery fails in the pro-rata warranty cycle then depending on the value of the battery, you will get discount on the Current MRP on the newly replaced battery.'>Pro-rata</span>");


String Inverter_Brand_URL= Inverter_Brand.replaceAll(" ", "-");
String Inverter_Model_URL= Inverter_Model.replaceAll(" ", "-");
String Inverter_Battery_Model_URL= Inverter_Battery_Model.replaceAll(" ", "-");
String Inverter_Battery_Capacity_URL= Inverter_Battery_Capacity.replaceAll(" ", "+");

String Inverter_Model_Cat=Inverter_Model;
Inverter_Model_Cat=Inverter_Model_Cat.replaceAll(" ", "-");
Inverter_Model_Cat=Inverter_Model_Cat.trim();

String Battery_Model_Cat=Inverter_Battery_Name;
Battery_Model_Cat= Battery_Model_Cat.replaceAll(" ", "-");
Battery_Model_Cat=Battery_Model_Cat.trim();

int Inverter_and_Battery_Actual_Price =Integer.parseInt(Inverter_Actual_Price)+(Integer.parseInt(Inverter_Battery_Act_Price)*Integer.parseInt(Inverter_Battery_Pic));
int Inverter_and_Battery_WithOut_Old_Battery_Price =Integer.parseInt(Inverter_Discount_Price)+(Integer.parseInt(Inverter_Battery_Witbat_Price)*Integer.parseInt(Inverter_Battery_Pic));
int Inverter_and_Battery_With_Old_Battery_Price =Integer.parseInt(Inverter_Discount_Price)+((Integer.parseInt(Inverter_Battery_Witbat_Price)-Integer.parseInt(Inverter_Battery_Ret_Price))*Integer.parseInt(Inverter_Battery_Pic));

int Inverter_and_Battery_Combo_WithOut_Old_Battery_Price;
int Inverter_and_Battery_Combo_With_Old_Battery_Price;
/*if(Integer.parseInt(Inverter_Battery_Pic) >1)
{
	Inverter_and_Battery_Combo_WithOut_Old_Battery_Price=(int)(Inverter_and_Battery_WithOut_Old_Battery_Price*Final_Inverter_Combo_disc);
	Inverter_and_Battery_Combo_With_Old_Battery_Price=(int)(Inverter_and_Battery_With_Old_Battery_Price*Final_Inverter_Combo_disc);
}*/
//else
//{
	Inverter_and_Battery_Combo_WithOut_Old_Battery_Price=Inverter_and_Battery_WithOut_Old_Battery_Price;
	Inverter_and_Battery_Combo_With_Old_Battery_Price=Inverter_and_Battery_With_Old_Battery_Price;
//}


int Inverter_and_Battery_WithOut_Old_Battery_Price_Percent=(((Inverter_and_Battery_Actual_Price-Inverter_and_Battery_WithOut_Old_Battery_Price)*100)/Inverter_and_Battery_Actual_Price);
int Inverter_and_Battery_With_Old_Battery_Price_Percent=(((Inverter_and_Battery_Actual_Price-Inverter_and_Battery_With_Old_Battery_Price)*100)/Inverter_and_Battery_Actual_Price);

int Save_Price = Inverter_and_Battery_Actual_Price-Inverter_and_Battery_With_Old_Battery_Price;
titles=Inverter_Brand+", "+Inverter_Model+" | Buy "+Inverter_Brand+" "+Inverter_Model+" model inverters Online at Best Price in India | BookBattery.com";
description="Buy 100% genuine "+Inverter_Brand+" "+Inverter_Model+" model Inverter Online with Maximum "+Inverter_and_Battery_With_Old_Battery_Price_Percent+"% Discount Price - Cash on delivery at BookBattery.com";
keywords=Inverter_Brand+", "+Inverter_Model+", "+Inverter_Warranty+" Warranty, "+Inverter_Capacity+" Capacity,"+Inverter_and_Battery_With_Old_Battery_Price_Percent+"% Discount Price";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head itemscope itemtype="http://schema.org/WebSite">
<title itemprop='name'><%=titles%></title>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<meta name="viewport" content="width=device-width">
<meta name="og_title" property="og:title" content="<%=titles%>"/>
<meta name="og_site_name" property="og:site_name" content="BookBattery.com"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="<%=publicUrl%>/bookbattery/images/favicon.png" type="image/x-icon">
<meta name='Description' content="<%=description%>"/>
<meta property="og:description" content="<%=description%>"/>
<meta name='Keywords' content='<%=keywords%>'/>

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
</style>

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
</head>
<body>
<!---################################## Header Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_header.jsp" />
<!---################################## Header Include Ends  ################################------>

  <!-- end nav -->
  <!-- end breadcrumbs -->
  <div class="breadcrumbs">
    <div class="container">
      <div class="row">
        <ul>

        </ul>
      </div>
    </div>
  </div>
  	<ol class="hide" itemscope itemtype="http://schema.org/BreadcrumbList">
	  <li itemprop="itemListElement" itemscope itemtype="http://schema.org/ListItem">
		<a itemscope itemtype="http://schema.org/Thing" itemprop="item" href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Inverter-and-Battery-Combo/">
		  <span itemprop="name">Inverter and Battery Combo</span>
		</a>
		<meta itemprop="position" content="1" />
	  </li>
	  <li itemprop="itemListElement" itemscope itemtype="http://schema.org/ListItem">
		<a itemscope itemtype="http://schema.org/Thing" itemprop="item" href="<%=publicUrl%><%=baseurldirectory%>Inverter-and-Battery-Combo/<%=Inverter_Brand_URL%>/">
		  <span itemprop="name"><%=Inverter_Brand%></span>
		</a>
		<meta itemprop="position" content="2" />
	  </li>
	  <li itemprop="itemListElement" itemscope itemtype="http://schema.org/ListItem">
		<a itemscope itemtype="http://schema.org/Thing" itemprop="item" href="<%=publicUrl%><%=baseurldirectory%>product-inverter-battery-combo/<%=Inverter_Brand_URL%>/<%=Inverter_Model_URL%>/<%=Inverter_Battery_Model_URL%>/<%=Inverter_Battery_Capacity_URL%>/">
		  <span itemprop="name"><%=Inverter_Brand%> <%=Inverter_Capacity%> <%=Inverter_Name%> Inverter with <% if(Integer.parseInt(Inverter_Battery_Pic) >1 ){%> <%=Inverter_Battery_Pic%> PCS <% } else {} %> <%=Inverter_Battery_Capacity%> Battery</span>
		</a>
		<meta itemprop="position" content="3" />
	  </li>
	</ol>
  <!-- end breadcrumbs -->
  <!-- main-container -->
 <section class="main-container"  id="Page_Result_ID">
    <div class="main container">
      <div class="col-main">
        <div class="row" style="display: block;">
          <div class="product-view">
		  <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 hidden-lg hidden-md" style="text-align:center;font-size: 15px;color: #e02d29;margin-bottom: 10px;padding: 0px;margin-top: -10px;" >
				 <span style=" font-size: 13px;font-weight: 700;"> Higher Warranty - Higher Life - Higher Price</span><span style=" font-size: 13px;font-weight: 700;"><a id="know_more_mob" class="more" style="text-decoration: none;cursor: pointer;color: #333;"> </br>Click here to Know More.</a></span>
				  <div class="red box"> In BookBattery, higher the warranty, battery life will be more and hence the price will be more for higher warranty BookBattery.</div>
			</div>				
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 hidden-sm hidden-xs" style="text-align:center;font-size: 15px;color: #e02d29;margin-bottom: 10px;padding: 0px;margin-top: -20px;">
				 <span style=" font-size: 13px;font-weight: 700;"> Higher Warranty - Higher Life - Higher Price</span><span style=" font-size: 13px;font-weight: 700;"><a id="know_more" class="more" style="text-decoration: none;cursor: pointer;"> &nbsp;&nbsp;&nbsp;&nbsp; Click here to Know More.</a></span>
				  <div class="red box"> In BookBattery, higher the warranty, battery life will be more and hence the price will be more for higher warranty BookBattery.</div>
			</div>	
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
                    <div class="large-image"> <a href="<%=Inverter_Image%>" class="cloud-zoom" id="zoom1" rel="useWrapper: false, adjustY:0, adjustX:20"> <img alt="<%=Inverter_Brand%> <%=Inverter_Name%> <%=Inverter_Capacity%> inverter and battery combo " title="<%=Inverter_Brand%> <%=Inverter_Name%> <%=Inverter_Capacity%> inverter and battery combo " src="<%=Inverter_Image%>"> </a> </div>
                    <div class="flexslider flexslider-thumb">
                      <ul class="previews-list slides">
                        <li><a href='<%=Inverter_Image%>' class='cloud-zoom-gallery' rel="useZoom: 'zoom1', smallImage: '<%=Inverter_Image%>' "><img src="<%=Inverter_Image%>" alt="<%=Inverter_Brand%> <%=Inverter_Name%> <%=Inverter_Capacity%> inverter and battery combo " title="<%=Inverter_Brand%> <%=Inverter_Name%> <%=Inverter_Capacity%> inverter and battery combo "/></a></li>
                        <li><a href='<%=Inverter_Battery_Image%>' class='cloud-zoom-gallery' rel="useZoom: 'zoom1', smallImage: '<%=Inverter_Battery_Image%>' "><img src="<%=Inverter_Battery_Image%>" title=" <%=Inverter_Brand%> <%=Inverter_Battery_Model%> <%=Inverter_Battery_Name%> inverter and battery combo " alt="<%=Inverter_Brand%> <%=Inverter_Battery_Model%> <%=Inverter_Battery_Name%> inverter and battery combo "/></a></li>
                      </ul>
                    </div>
                  </div>
                  <!-- end: more-images -->
                  <div class="clear"></div>
                </div>
                <div class="product-shop col-sm-5 col-xs-12">
                  <div class="product-name">
                    <h1>
					Inverter with 
					<% if(Integer.parseInt(Inverter_Battery_Pic) >1 )
						{%>
					<%=Inverter_Battery_Pic%> PCS
					<% } else {} %>
					
					<%=Inverter_Battery_Capacity%> Battery</h1>
                  </div>
                  <div class="short-description">
                    <h2><strong>Quick Overview</strong></h2>
                    <table style=" color: #494848;">
					 <colgroup> <col class="col-xs-5"> <col class="col-xs-7"> </colgroup>
						  <tr>
							  <th>Product Brand</th>
							  <td>: <%=Inverter_Brand%></td>
						  </tr>
						  <tr>
							  <th>Inverter Capacity</th>
							  <td>: <%=Inverter_Capacity%></td>
						  </tr>
						  <tr>
							  <th>Battery Capacity</th>
							  <td>: <%=Inverter_Battery_Capacity%></td>
						  </tr>
						  <tr>
							  <th>Inverter Warranty</th>
							  <td>: <%=Inverter_Warranty%></td>
						  </tr>
						  <tr>
							  <th>Battery Warranty</th>
							  <td>: <%=Inverter_Battery_Warranty%></td>
						  </tr>
					</table>
                  </div>
				<table class="table">
				 <colgroup> <col class="col-xs-9"> <col class="col-xs-3"> </colgroup>
				  <tr>
					  <td style="color: #848181; font-size: 12px;padding: 1px;border: 0;">Inverter Discount Price </td>
					  <td class="price" style="color: #848181; font-size: 12px;padding: 1px;border: 0;">: &nbsp;<i class="icon-inr" aria-hidden="true"></i> <%=Inverter_Discount_Price%></td>
				  </tr>
				  <tr>
					  <td  style="color: #848181; font-size: 12px;padding: 1px;border: 0;">Inverter Battery Price (With out old Battery Return)</td>
					  <td class="price" style="color: #848181; font-size: 12px;padding: 1px;border: 0;">: &nbsp;<i class="icon-inr" aria-hidden="true"></i> <%=(Integer.parseInt(Inverter_Battery_Witbat_Price))%> X <%=Integer.parseInt(Inverter_Battery_Pic)%>pcs</td>
				  </tr>
				  <tr>
					  <td style="color: #848181; font-size: 12px;padding: 1px;border: 0;">Inverter Battery Price (With old Battery Return)</td>
					  <td class="price" style="color: #848181; font-size: 12px;padding: 1px;border: 0;">: &nbsp;<i class="icon-inr" aria-hidden="true"></i> <%=Integer.parseInt(Inverter_Battery_Witbat_Price)-Integer.parseInt(Inverter_Battery_Ret_Price)%> X <%=Integer.parseInt(Inverter_Battery_Pic)%>pcs</td>
				  </tr>
				</table>
                  <p class="availability">Availability : 
				  
				  
					<%
					  if(Inverter_Stock.equals("No") || Inverter_Battery_Stock.equals("No"))
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
				   <div class="price-block" style=" padding-top: 15px;">
						<div class="price-box">
						
						<div> 
							<table><tbody>
							  <tr>
								  <td>
									  <p class="old-price"> 
										  <span class="price-label" style="display: block;">Regular Price: <span id="old-price-48" class="price"><i class="icon-inr" aria-hidden="true"></i>  <%=Inverter_and_Battery_Actual_Price%></span> </span>
									  </p> 
									  <p> Save Up to : &nbsp;<i class="icon-inr" aria-hidden="true"></i> <%=Save_Price%></p>
								  </td>
								  <td>
									   <span class="label label-danger" style="font-size: 16px;" id="discount_percent"><%=Inverter_and_Battery_With_Old_Battery_Price_Percent%>% off</span> 
								  </td>
							  </tr>
							</tbody></table>
						 </div>
						 <hr width="100" style=" margin: 0px;">
						  <table style=" color: #575656;" id="check_with_without_battery">
							  <tr>
								  <th><input type="radio" class="radio"  name="battery_buy_type" value="New" style="margin-right: 8px;"></th>
								  <th class="price-label">Inverter & Battery Combo Price <span>(With out old Battery Return)</span></th>
								  <td class="price" style="text-align: right;color: #323232;">: &nbsp;<i class="icon-inr" aria-hidden="true"></i> <%=Inverter_and_Battery_Combo_WithOut_Old_Battery_Price%> </td>
							  </tr>
							  <tr>
								  <th><input type="radio" class="radio" checked="checked" name="battery_buy_type" value="Replaced"  style="margin-right: 8px;"></th>
								  <th class="price-label">Inverter & Battery Combo Price <span>(With old Battery Return)</span><span style="font-size: 11px;"> (Same Ah)</span> </th>
								  <td class="price" style="text-align: right;color: #323232;">: &nbsp;<i class="icon-inr" aria-hidden="true"></i> <%=Inverter_and_Battery_Combo_With_Old_Battery_Price%> </td>
							  </tr>
						  </table>						  
						</div>
						<div>
							<div style="font-size:1em; margin-top:5px;" id="with-old-note"><b>Note</b>: <span style="color: #8e0707;font-weight: 500;">Price with old battery take back is price of the New Battery - (minus) Scrap price of your Old Battery.</span> Your Old Battery Should be of Same AH Capacity as the New Battery Ordered. If not Price with Old Battery Take Back will differ. </br>For help <b>Call +91 96034 67559</b> or <a href="javascript:Open_Chat()"><span class="label label-default chat-with-us">Chat with Us</span></a></div>
							<div style="font-size:1em; margin-top:5px;display:none;" id="without-old-note"><b>Note</b>: Need more assistance while ordering the Battery ??</br> We are here to help you, <b>Call +91 96034 67559</b> or <a href="javascript:Open_Chat()"><span class="label label-default chat-with-us">Chat with Us</span></a></div>
						</div>
                  </div>
                </div>
				<div class="product-shop col-sm-3 col-xs-12">
					<ul class="styled-list arrow" style="font-size: 15px;color: #727272;">
						<li><strong>Brand New & 100% Genuine</strong></li>
						<li><strong>Free Delivery & Installation</strong></li>
						<li><strong>Cash on Delivery</strong></li>
						<li><strong>Delivered in 4 - 24 Hours*</strong></li>
					</ul>
				<div class="add-to-box">
				<div class="price-overview-custom" id="final_price"><i class="icon-inr" aria-hidden="true"></i> <%=Inverter_and_Battery_Combo_With_Old_Battery_Price%> </div>
				<div style="font-size: 12px;padding-bottom: 10px;text-align: left;">(Prices are inclusive of all taxes)</div>
				<div id="vehicle_make_model_div"></div>
				
					<%
				  if(Inverter_Stock.equals("No") || Inverter_Battery_Stock.equals("No"))
					{
					%>						
						<div>
						<h1 class="danger" style="color: #F4511E;" >Out of Stock</h1>
						<input type="hidden"  value="1" maxlength="2" id="product_qty" name="product_qty">
						</div>
						</div>				
					<%}
					else
					{
					%>
				

				<div id="vehicle_make_model_select_div" style="display:none;">
					<input type="hidden" name="product_type" id='product_type' value="<%=Inverter_Type%>">
					<input type="hidden" name="product_qty" class="Battery_Quantity Battery_Quantity_value" id='product_qty' value="1">
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
				
					<div id="ref_code" class="form-group" style="margin-top: 15px;text-align: -webkit-left;">
					<table>
					<tbody><tr><label id="ref_code_label" for="name" class="control-label" style="font-size: larger;">Enter Referral Code</label></tr>
					<tr id="inp_ref_code"><td><input type="text" onkeyup="javascript:backspace_press(event.key)" name="name" class="form-control" style="font-size: 20px;" id="refcode" placeholder="XXXXXXX"></td><td><span class="label label-danger" style="font-size: 14px;background-color: #e02d29;cursor: pointer;" id="applycode" onclick='javascript:validate_code()'>Apply</span></td></tr></tbody>
					</table> 
					</div>
				
                    <div class="add-to-cart">
                      <button class="button btn-cart btn" title="Buy Now" type="button" disabled="disabled" onClick="javascript:OrderProductOnline()" style="font-size: 19px; padding: 15px 25px 15px;  margin-left: 0px;"><span> Please wait</span></button>
                    </div>
					
					<%
					
					
					}
					%>
					
					
                 </div>
				</div>
              </form>
            </div>
            <div class="col-sm-12 Product_Description">
              <div class="box-additional">
				 <div class="product-tabs-content-inner">
					<div class="block block-tags">
						<div class="block-title"> Product Description :</div>
						<div class="block-content">
							<h5><strong> Specification of <%=Inverter_Brand%> <%=Inverter_Type%> and <%=Inverter_Battery_Type%></strong></h5>
						   <div class="table-responsive"> 
								<table class="table table-bordered " > 
									<colgroup> <col class="col-xs-2"> <col class="col-xs-6"> </colgroup>
									 <tbody> 
										 <tr> <th scope="row">Type</th> <td><%=Inverter_Type%></td> </tr> 
										 <tr> <th scope="row">Inverter Brand</th> <td><%=Inverter_Brand%></td> </tr>
										 <tr> <th scope="row">Inverter Model</th> <td><%=Inverter_Model%></td> </tr>
										 <tr> <th scope="row">Inverter Name</th> <td><%=Inverter_Name%></td> </tr> 
										 <tr> <th scope="row">Inverter Capacity</th> <td><%=Inverter_Capacity%></td> </tr>
										 <tr> <th scope="row">Inverter Warranty</th> <td><%=Inverter_Warranty%></td> </tr>
										 <tr> <th scope="row">Inverter Batteries Required </th> <td><%=Inverter_Battery_Pic%> Pcs</td> </tr>
										 <tr> <th scope="row">Inverter Battery Model</th> <td><%=Inverter_Battery_Model%></td> </tr>
										 <tr> <th scope="row">Inverter Battery Name</th> <td><%=Inverter_Battery_Name%></td> </tr> 
										 <tr> <th scope="row">Inverter Battery Capacity</th> <td><%=Inverter_Battery_Capacity%></td> </tr>
										 <tr> <th scope="row">Inverter Battery Warranty</th> <td><%=Inverter_Battery_Warranty%></td> </tr>
										 <tr> <th scope="row">Inverter Battery Layout</th> <td><%=Inverter_Battery_Flag%></td> </tr>
									 </tbody> 
								</table> 
							</div>   
							<h5><strong> Appliance and Load Chart for <%=Inverter_Model%> <%=Inverter_Type%></strong></h5>
							<div class="table-responsive"> 
								<table class="table table-bordered"> 
									 <tbody> 
										 <tr>
											<th>Appliances</th>
											<th>Option 1</th>
											<th>Option 2</th>
											<th>Appliance wattage:(Approx..)</th>
										 </tr> 
										 <tr>
											<td><b>Computer</b></td>
											<td><%=Inverter_Computer%></td>
											<td>250W</td>
										 </tr> 
										 <tr>
											<td><b>Fans</b></td>
											<td><%=Inverter_Fans%></td>
											<td>80W</td>
										 </tr> 
										 <tr>
											<td><b>Tube lights</b></td>
											<td><%=Inverter_Tubelights%></td>
											<td>40W</td>
										 </tr> 
										 <tr>
											<td><b>TV (32 Inches)</b></td>
											<td><%=Inverter_Television%></td>
											<td>180W (32 Inch LED..)</td>
										 </tr> 
									 </tbody> 
								</table>
							</div>  
						</div>
						<div class="block-content">
							<h5><strong>About <%=Inverter_Brand%>, <%=Inverter_Model%> Inverter</strong></h4>
							<p>  <%=product_details.getProperty(Inverter_Model_Cat)%></p>
							<ul>  <%=product_details.getProperty(Inverter_Model_Cat+"-Benefits")%></ul>
							<div><%=product_details.getProperty(Inverter_Model_Cat+"-Table")%></div>
						</div>
						<div class="block-content">
							<h5><strong>About <%=Inverter_Brand%>, <%=Inverter_Battery_Model%> Inverter Battery</strong></h4>
							<p>  <%=product_details.getProperty(Battery_Model_Cat)%></p>
							<ul>  <%=product_details.getProperty(Battery_Model_Cat+"-Benefits")%></ul>
							<div><%=product_details.getProperty(Battery_Model_Cat+"-Table")%></div>
						</div>
					</div>
                  </div>
				</div>
			</div>

              <div class="box-additional">
              	<%
				if(Vector_Get_Similar_Inverter!=null && Vector_Get_Similar_Inverter.size() > 0)
				{
				try{
				%>
                <div class="product-essential">
                  <div class="slider-items-products">
                    <div class="new_title center">
                      <h2>Similar Inverter Products</h2>
                    </div>
                    <div id="upsell-products-slider" class="product-flexslider hidden-buttons">
                      <div class="slider-items slider-width-col4">
					  
					  <%
				
						for ( int i=0; i<Vector_Get_Similar_Inverter.size() ; i++){
							
							Hashtable ht=(Hashtable)Vector_Get_Similar_Inverter.get(i);
							
							String inverter_brand=String.valueOf(ht.get("inverter_brand"));
							String inverter_capacity=(String)ht.get("inverter_capacity");
							String icon_url=(String)ht.get("icon_url");
							String inverter_warranty=(String)ht.get("inverter_warranty");
							String inverter_model=String.valueOf(ht.get("inverter_model"));
							String inverter_discount_price=String.valueOf(ht.get("inverter_discount_price"));
							String inverter_actual_price=String.valueOf(ht.get("inverter_actual_price"));
													
							if(icon_url == null)
							{
								icon_url = "./images/noimage.jpg";
							}
							
							String inverter_brand_URL=inverter_brand;
							String inverter_model_URL=inverter_model;
							
							inverter_brand_URL= inverter_brand_URL.replaceAll(" ", "+");
							inverter_model_URL= inverter_model_URL.replaceAll(" ", "+");

						%>
					    <div class="item">
							  <div class="item-inner">
								<div class="item-img">
								  <div class="product-block">
									<div class="product-image"> <a href="<%=baseURL%>/bookbattery/product-inverters/<%=inverter_brand_URL%>/<%=inverter_model_URL%>/<%=State_URL%>/<%=City_URL%>/" style="height: auto;">
									  <figure class="product-display">
										<img src="<%=icon_url%>" class="lazyOwl product-mainpic" alt="<%=inverter_brand%> <%=inverter_model%> ( <%=inverter_capacity%> ) " title="<%=inverter_brand%> <%=inverter_model%> ( <%=inverter_capacity%> ) " style="display: block;"> <img class="product-secondpic" alt="<%=inverter_brand%> <%=inverter_model%> ( <%=inverter_capacity%> ) " title="<%=inverter_brand%> <%=inverter_model%> ( <%=inverter_capacity%> ) " src="<%=icon_url%>"> </figure>
									  </a> </div>
									<div class="product-meta">
									  <div class="product-action"> <a class="addcart" href="<%=baseURL%>/bookbattery/product-inverters/<%=inverter_brand_URL%>/<%=inverter_model_URL%>/<%=State_URL%>/<%=City_URL%>/" > <i class="icon-shopping-cart">&nbsp;</i> View More </a>
									</div>
								  </div>
								</div>
								<div class="item-info" style="text-align: left;">
								  <div class="info-inner">
									<div class="item-title"> <a href="<%=baseURL%>/bookbattery/product-inverters/<%=inverter_brand_URL%>/<%=inverter_model_URL%>/<%=State_URL%>/<%=City_URL%>/" title="Click to View "> <%=inverter_brand%> <%=inverter_model%> ( <%=inverter_capacity%> ) </a> </div>
									<div class="item-content">
									  <div class="item-price">
										<div class="price-box">
										  <div class="old-price"> <span class="price-label" style="display: block;">Regular Price: <span class="price" ><i class="icon-inr" aria-hidden="true"></i> <%=inverter_actual_price%> </span> </span></div>
										   <span class="price-label" style="display: block;">Warranty: <span  > <%=inverter_warranty%> </span> </span>
										   <span class="price-label" style="display: block;">Capacity: <span  > <%=inverter_capacity%> </span> </span>
										   <table style=" color: #494848;font-size: 15px;">
												  <tr>
													  <th>Discount Price</th>
													  <td>: <i class="icon-inr" aria-hidden="true"></i> <%=inverter_discount_price%> </td>
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
          
        </div>
      </div>
    </div>
   </div>
  </section>
  <div id="Order_form_PopUp"></div>
  <!--End main-container -->
  <!-- Footer -->
  <!-- Button trigger modal -->

  
<input type="hidden" name="product_type_page" id='product_type_page' value="Inverter and Battery Combo">
<input type="hidden" name="product_make_page" id='product_make_page' value="">
<input type="hidden" name="product_capacity_page" id='product_capacity_page' value="">
<input type="hidden" name="product_model_page" id='product_model_page' value="">
<input type="hidden" name="product_brand_page" id='product_brand_page' value="<%=Inverter_Brand%>">
<input type="hidden" name="product_state_page" id='product_state_page' value="<%=State%>">
<input type="hidden" name="product_city_page" id='product_city_page' value="<%=City%>">



<input type="hidden" name="Product_Brand" id='Product_Brand' value="<%=Inverter_Brand%>">
<input type="hidden" name="Product_Battery_Brand" id='Product_Battery_Brand' value="<%=Inverter_Battery_Brand%>">
<input type="hidden" name="Product_Model" id='Product_Model' value="<%=Inverter_Model%>">
<input type="hidden" name="Inverter_Battery_Model" id='Inverter_Battery_Model' value="<%=Inverter_Battery_Model%>">
<input type="hidden" name="Inverter_Battery_Pic" id='Inverter_Battery_Pic' value="<%=Inverter_Battery_Pic%>">
<input type="hidden" name="Inverter_Battery_Capacity" id='Inverter_Battery_Capacity' value="<%=Inverter_Battery_Capacity%>">




<input type="hidden" name="baseURL" id='baseURL' value="<%=baseURL%>">
<input type="hidden" name="publicUrl" id='publicUrl' value="<%=publicUrl%>">
<input type="hidden" name="baseurldirectory" id='baseurldirectory' value="<%=baseurldirectory%>">


<input type="hidden" name="Price_Without_Battery" id='Price_Without_Battery' value="<%=Inverter_and_Battery_Combo_WithOut_Old_Battery_Price%>">
<input type="hidden" name="WithOutOldBattery_Percent" id='WithOutOldBattery_Percent' value="<%=Inverter_and_Battery_WithOut_Old_Battery_Price_Percent%>">
<input type="hidden" name="Price_With_Battery" id='Price_With_Battery' value="<%=Inverter_and_Battery_Combo_With_Old_Battery_Price%>">
<input type="hidden" name="WithOldBattery_Percent" id='WithOldBattery_Percent' value="<%=Inverter_and_Battery_With_Old_Battery_Price_Percent%>">

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

<script type="text/javascript" src="<%=publicUrl%>/bookbattery/dev/js/product_details_function.js?v=21"></script>
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

 </script>
  
</body>
</html>