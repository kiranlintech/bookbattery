<%-- 
Document   		 : service_details.jsp
Created on 		 : Apr 03 2017, 10:04:12 AM
Author     		 : BookBattery
Copyright Notice : BookBattery Confidential.
Document         : This jsp is used as show the Single Service Package and Some other Random Packages for Vehical make and model. 
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.*,com.ngit.javabean.loglevel.LogLevel,javax.servlet.ServletContext,java.util.Properties,java.io.FileInputStream, com.ngit.javabean.consumers.products.GetCookie,com.ngit.javabean.qrymgr.QueryManager"%>
<%response.setHeader("Pragma","no-cache"); response.setHeader("Cache-Control","no-store"); response.setHeader("Expires","0"); response.setDateHeader("Expires",-1); %>
<%

String Vehicle_Make = (String) request.getAttribute("Vehicle_Make");
LogLevel.DEBUG(5, new Throwable(), "Vehicle_Make :" + Vehicle_Make);

String Vehicle_Model = (String) request.getAttribute("Vehicle_Model");
LogLevel.DEBUG(5, new Throwable(), "Vehicle_Model :" + Vehicle_Model);

String Product_Type = (String) request.getAttribute("Product_Type");
LogLevel.DEBUG(5, new Throwable(), "Product_Type :" + Product_Type);

String Product_Capacity = (String) request.getAttribute("Product_Capacity");
LogLevel.DEBUG(5, new Throwable(), "Product_Capacity :" + Product_Capacity);

String Quantity = (String) request.getAttribute("Quantity");
LogLevel.DEBUG(5, new Throwable(), "Quantity :" + Quantity);

String Services_Type = (String) request.getAttribute("Services_Type");
String Services_Place = (String) request.getAttribute("Services_Place");

String Vehicle_Make_URL= Vehicle_Make.replaceAll(" ", "-");
String Vehicle_Model_URL= Vehicle_Model.replaceAll(" ", "+");

Vector Services_Package_Vector=(Vector)request.getAttribute("Services_Package_Vector");
ServletContext context = getServletContext();
Properties propsMOPConfig = new Properties();
FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
propsMOPConfig.load(fin1); 
fin1.close();

String publicUrl = (propsMOPConfig.getProperty("publicUrl")!=null)?propsMOPConfig.getProperty("publicUrl"):"";
String baseURL = (propsMOPConfig.getProperty("baseURL")!=null)?propsMOPConfig.getProperty("baseURL"):"";
String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?propsMOPConfig.getProperty("baseurldirectory"):"";
String serverURL = (propsMOPConfig.getProperty("serverURL")!=null)?propsMOPConfig.getProperty("serverURL"):"";

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

Hashtable Services_Package_Hashtable_TMP=(Hashtable)Services_Package_Vector.get(0);

String Services_Type_TMP=String.valueOf(Services_Package_Hashtable_TMP.get("service_type"));
String Vehicle_Make_TMP=String.valueOf(Services_Package_Hashtable_TMP.get("vehicle_name"));
LogLevel.DEBUG(5, new Throwable(), "Vehicle_Make_TMP :" + Vehicle_Make_TMP);
String Vehicle_Model_TMP=String.valueOf(Services_Package_Hashtable_TMP.get("vehicle_model"));
LogLevel.DEBUG(5, new Throwable(), "Vehicle_Model_TMP :" + Vehicle_Model_TMP);

String titles = Services_Type_TMP+", Service Details";
String description =Services_Type_TMP+",service for "+Vehicle_Make_TMP+", "+Vehicle_Model_TMP+".";
String keywords = Services_Type_TMP+", "+Vehicle_Make_TMP+", "+Vehicle_Model_TMP+".";
String Service_Decription_Temp = "Professional and Experienced Technician will come to your place , Check the Voltage and AMPS at different stages, Check the water level/acid levels of the battery(top up if required), Clean the Battery Terminals and Provide a Report Card. If Battery Recharge is required, additional Rs 300 will be charged for getting the Battery Recharged. The battery will be taken for recharge at our service center and will be provided back within 24 - 48 hrs. If the battery needs to be replaced, it will be replaced at additional cost";
String Service_Price_MRP_RXT = "";
String Service_Price_Discount_RXT = "";
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
<link rel="shortcut icon" href="<%=publicUrl%><%=baseurldirectory%>images/favicon.png" type="image/x-icon">
<meta name='Description' content="<%=description%>"/>
<meta name='Keywords' content='<%=keywords%>'/>

<!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_css.jsp" />
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
</style>
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
  <!-- end breadcrumbs -->
  <!-- main-container -->
 <section class="main-container" id="Page_Result_ID">
    <div class="main container">
      <div class="col-main">
        <div class="row">
          <div class="product-view">
			<div class="product-essential wow bounceInUp animated" style="margin-bottom: 5px;padding-top: 14px;">
			  <div class="slider-items-products">
			  <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" style=" font-size: 15px; padding: 4px 0px 0px 20px; color: #e02d29;" >
				<div class="col-lg-5 col-md-5 col-sm-12 col-xs-12">
					<span class="blink_me"> <span class="icon-bell" aria-hidden="true"></span>	&nbsp;&nbsp; Below Prices are for <strong><%=Product_City_Cookie%></strong>, <strong><%=Product_State_Cookie%></strong>.</span> <span style=" font-size: 13px;font-weight: 700;"></span>
				</div>				
				<div id="loc_div" class="hide col-lg-6 col-md-5 col-sm-12 col-xs-12" style=" font-size: 18px;color: red;">
					<span><span class="icon-map-marker" aria-hidden="true"> </span> &nbsp;&nbsp;Service is available only at <strong>Bangalore</strong>, <strong>Karnataka</strong>.</span> <span style=" font-size: 18px;font-weight: 800;"></span>
				</div>
			</div>
		 </div>
	</div>
			
		  <%
				Hashtable Services_Package_Hashtable=(Hashtable)Services_Package_Vector.get(0);

				// String Name Contains RXT, This is to Create a unique String name- Not much than this ///#### Sai Krishna
				
				String Services_Type_RXT=String.valueOf(Services_Package_Hashtable.get("service_type"));
				String Vehicle_Make_RXT=String.valueOf(Services_Package_Hashtable.get("vehicle_name"));
				String Vehicle_Model_RXT=String.valueOf(Services_Package_Hashtable.get("vehicle_model"));
				String store=String.valueOf(Services_Package_Hashtable.get("store"));
                LogLevel.DEBUG(5, new Throwable(), "store in jsp :" + store);
				String within_5km=String.valueOf(Services_Package_Hashtable.get("within_5km"));
                LogLevel.DEBUG(5, new Throwable(), "within_5km in jsp :" + within_5km);
				String within_10km=String.valueOf(Services_Package_Hashtable.get("within_10km"));
                LogLevel.DEBUG(5, new Throwable(), "within_10km in jsp :" + within_10km);
                String icon_path="";
                
                
				
					if(Services_Type_RXT.equals("Jump Start"))
					{

				Service_Decription_Temp="Our Technician will come to your place and jump start your vehicle with a spare battery and get you going. He will also check your battery condition and let you know the condition of the battery. If the battery is not good, you can order a new battery and we will fit the new battery with out any extra installation charge. You just pay for the new battery.";
						
						icon_path="/dev/includes/images-design/icons/jump_start.png";
					
					} else if(Services_Type_RXT.equals("Health Check")){
					Service_Decription_Temp="Professional and Experienced Technician will come to your place , Check the Voltage and AMPS at different stages, Check the water level/acid levels of the battery(top up if required), Clean the Battery Terminals and Provide a Report Card. If Battery Recharge is required, additional Rs 300 will be charged for getting the Battery Recharged. The battery will be taken for recharge at our service center and will be provided back within 24 - 48 hrs. If the battery needs to be replaced, it will be replaced at additional cost.";
					icon_path="/dev/includes/images-design/icons/battery_health_check.png";
					} else if(Services_Type_RXT.equals("Recharge")){	
				Service_Decription_Temp="Our Technician will come to your place, un install the battery, take it to our store and put for charging using our state of the art charging machines. Once the battery is charged to required voltage, the technician will keep it for observation for few hours to see if the battery can hold the voltage and not discharges fast. If the battery is holding the voltage, the technician will bring back the battery and fits to your vehicle. This complete process will take minimum 1 day. If the battery is not able to charge or hold the required voltage, our technician will call and let you know that the battery is not good. You can order a new battery and we will bring and fit it with no extra installation fee. You just have to pay for the new battery.";
				icon_path="/dev/includes/images-design/icons/battery_recharge.png";
					} else {							
					Service_Decription_Temp="Our Technician will come to your place and jump start your vehicle with a spare battery and get you going. He will also check your battery condition and let you know the condition of the battery. If the battery is not good, you can order a new battery and we will fit the new battery with out any extra installation charge. You just pay for the new battery.";
					icon_path="/dev/includes/images-design/icons/battery_health_check.png";
					}
						
				String Description_RXT=Service_Decription_Temp;
				Product_Type = (String) request.getAttribute("Product_Type");
                LogLevel.DEBUG(5, new Throwable(), "Product_Type :" + Product_Type);
                
                Product_Type=Product_Type.trim();
                
                if(Services_Place.equals("store"))
                {
                    Service_Price_Discount_RXT=String.valueOf(Services_Package_Hashtable.get("store"));
                     LogLevel.DEBUG(5, new Throwable(), "Inside Service_Price_Discount_RXT :" + Service_Price_Discount_RXT);
                }
                else if(Services_Place.equals("within_5km"))
                {
                     Service_Price_Discount_RXT=String.valueOf(Services_Package_Hashtable.get("within_5km"));
                     LogLevel.DEBUG(5, new Throwable(), "Inside Service_Price_Discount_RXT :" + Service_Price_Discount_RXT);
                }
                else if(Services_Place.equals("within_10km"))
                {
                     Service_Price_Discount_RXT=String.valueOf(Services_Package_Hashtable.get("within_10km"));
                     LogLevel.DEBUG(5, new Throwable(), "Inside Service_Price_Discount_RXT :" + Service_Price_Discount_RXT);
                }
                else
                {
                     Service_Price_Discount_RXT=String.valueOf(Services_Package_Hashtable.get("store"));
                     LogLevel.DEBUG(5, new Throwable(), "Inside Service_Price_Discount_RXT :" + Service_Price_Discount_RXT);
                }
                LogLevel.DEBUG(5, new Throwable(), "Service_Price_Discount_RXT in jspp :" + Service_Price_Discount_RXT);
                
				if(Product_Type.equals("Car Batteries"))
				{
                     LogLevel.DEBUG(5, new Throwable(), "Inside :" + Product_Type);
                     Service_Price_MRP_RXT="400";
				}				
                else if(Product_Type.equals("Bike Batteries"))
				{
                    Service_Price_MRP_RXT="250";
				}
				else
				{
                    LogLevel.DEBUG(5, new Throwable(), "Inside else:" + Product_Type);
                    Service_Price_MRP_RXT="700";
				}				
                if(Product_Type.equals("Car Batteries") && store.equals("NA"))
				{
                     store="100";
				}				
                else if(Product_Type.equals("Bike Batteries")&& store.equals("NA"))
				{
                     store="50";
				}
				else
				{
                    store=store;
				}
				
                int Service_Save_Price = Integer.parseInt(Service_Price_MRP_RXT)-Integer.parseInt(Service_Price_Discount_RXT);
				int Service_Discount_Percent=(((Integer.parseInt(Service_Price_MRP_RXT)-Integer.parseInt(Service_Price_Discount_RXT))*100)/Integer.parseInt(Service_Price_MRP_RXT));
                
				
			%>
			
						
            <div class="product-essential" style="font-family: 'Open Sans';">
              <form action="#" method="post" id="product_addtocart_form">
                <input name="form_key" value="6UbXroakyQlbfQzK" type="hidden">
                <div class="product-img-box col-sm-6  col-md-4 col-xs-12">
                  <div class="product-image">
                    <div class="large-image"> <a href="<%=publicUrl%><%=baseurldirectory%><%=icon_path%>" class="cloud-zoom" id="zoom1" rel="useWrapper: false, adjustY:0, adjustX:20"> 
                    <img alt="<%=Services_Type_RXT%>" src="<%=publicUrl%><%=baseurldirectory%><%=icon_path%>">
                    </a>
                    </div>
                  </div>
                  <!-- end: more-images -->
				  <div class="addthis_inline_share_toolbox"></div>
                  <div class="clear"></div>
                </div>
                <div class="product-shop col-sm-6  col-md-5 col-xs-12">
                  <div class="product-name">
                    <h1><%=Services_Type_RXT%> Service - <%=Product_Type%></h1>
                  </div>
                  <div class="short-description">
                    <h2><strong>Quick Overview</strong></h2>
						<div>
							<%=Description_RXT.substring(0, Math.min(Description_RXT.length(), 200))%>
							<%
						
								if(Description_RXT.length()>=200)
								{
							%>
							.....
							<a href="javascript:viewFullDescription()" class="button" style=" padding: 4px; margin-top: 5px; margin-bottom: 20px; font-size: 11px;">Click Here to View More</a>
							
							<%
								}
								else
								{
									//Do Nothing 
								}
							%>
						</div>
                  </div>
                    <p class="availability"><b>Service Prices </b>: 						  
                            <table id="check_with_service_place">
                              <%if(Services_Place.equals("store"))
                              {
                              %> 
                               <tr><th class="store_price"><input type="radio" class="radio" name="service_place_select" value="store" style="margin-right: 2px; margin-left: 5px;" onclick="changeprice(this);" checked="checked"></th><th class="price-label store_price">Service Price At Store<td class="price" id="discount_price_store" style="text-align: right;color: #323232;font-size: 20px;">: <i class="fa fa-inr" aria-hidden="true"></i>&nbsp<%=store%></td></th></tr>
                               <!-- <th class="within_5km_price"><input type="radio" class="radio" name="service_place_select" value="within_5km" onclick="changeprice(this);" style="margin-right: 2px; margin-left: 5px;"></th>
							  <th class="price-label within_5km_price">With in 5 KM</th> -->
                             <tr><th class="within_10km_price"><input type="radio" onclick="changeprice(this);" class="radio" name="service_place_select" value="within_10km" style="margin-right: 2px; margin-left: 5px;"></th>
							  <th class="price-label within_10km_price">Service Price At Your Door Step (With in 10 KM)</th><td class="price" id="discount_price_store" style="text-align: right;color: #323232;font-size: 20px;">&nbsp;: <i class="fa fa-inr" aria-hidden="true"></i>&nbsp<%=within_10km%></td></tr>
                             <% 
                             }else if(Services_Place.equals("within_5km")){
                              %> 
                              
                              <%if(Services_Type.equals("Jump Start"))
                              {

                              }else{
                              %>
                               <tr><th class="store_price"><input type="radio" onclick="changeprice(this.value);" class="radio" name="service_place_select" value="store" style="margin-right: 2px; margin-left: 5px;"></th><th class="price-label store_price">Service Price At Store<td class="price" id="discount_price_store" style="text-align: right;color: #323232;font-size: 20px;">: <i class="fa fa-inr" aria-hidden="true"></i>&nbsp<%=store%></td></th></tr>
                              <%
                              }
                              %>
                               
                              <!--   <th class="within_5km_price"><input type="radio" onclick="changeprice(this.value);" class="radio" checked="checked" name="service_place_select" value="within_5km" style="margin-right: 2px; margin-left: 5px;"></th>
							  <th class="price-label within_5km_price">With in 5 KM</th>-->
                              
                                <tr><th class="within_10km_price"><input type="radio" class="radio" onclick="changeprice(this.value);" name="service_place_select" value="within_10km" style="margin-right: 2px; margin-left: 5px;"></th>
                                <th class="price-label within_10km_price">Service Price At Your Door Step (With in 10 KM)</th><td class="price" id="discount_price_store" style="text-align: right;color: #323232;font-size: 20px;">&nbsp;: <i class="fa fa-inr" aria-hidden="true"></i>&nbsp<%=within_10km%></td></tr>
                            <% 
                            }else if(Services_Place.equals("within_10km")){%>
                            
                              <tr> <th class="store_price"><input type="radio" onclick="changeprice(this.value);" class="radio" name="service_place_select" value="store" style="margin-right: 2px; margin-left: 5px;"></th><th class="price-label store_price">Service Price At Store<td class="price" id="discount_price_store" style="text-align: right;color: #323232;font-size: 20px;">: <i class="fa fa-inr" aria-hidden="true"></i>&nbsp<%=store%></td></th></tr>
                              <!--   <th class="within_5km_price"><input type="radio" class="radio" onclick="changeprice(this.value);" name="service_place_select" value="within_5km" style="margin-right: 2px; margin-left: 5px;"></th>
							  <th class="price-label within_5km_price">With in 5 KM</th>-->
                              
                              <%if(Product_Type.equals("Bike Batteries") || Product_Type.equals("Bike Battery"))
                              {
                              }
                              else
                              {
                              %> 
                                 <tr><th class="within_10km_price"><input type="radio" class="radio" onclick="changeprice(this.value);" checked="checked" name="service_place_select" value="within_10km" style="margin-right: 2px; margin-left: 5px;"></th>
                                 <th class="price-label within_10km_price">Service Price At Your Door Step (With in 10 KM)</th><td class="price" id="discount_price_store" style="text-align: right;color: #323232;font-size: 20px;">&nbsp;: <i class="fa fa-inr" aria-hidden="true"></i>&nbsp<%=within_10km%></td></tr>
                              <%
                              }
                              %>
                            <% 
                            }else{
                            %>
                               <tr> <th class="store_price"><input type="radio" class="radio" onclick="changeprice(this.value);" name="service_place_select" value="store" style="margin-right: 2px; margin-left: 5px;"></th><th class="price-label store_price">Service Price At Store<td class="price" id="discount_price_store" style="text-align: right;color: #323232;font-size: 20px;"><i class="fa fa-inr" aria-hidden="true"></i>&nbsp<%=store%></td></th></tr>
                                 <!-- <th class="within_5km_price"><input type="radio" class="radio" onclick="changeprice(this.value);" name="service_place_select" value="within_5km" style="margin-right: 2px; margin-left: 5px;"></th>
                                <th class="price-label within_5km_price">With in 5 KM</th>-->
                               <tr> <th class="within_10km_price"><input type="radio" class="radio" name="service_place_select" onclick="changeprice(this.value);" value="within_10km" style="margin-right: 2px; margin-left: 5px;"></th>
                                <th class="price-label within_10km_price">Service Price At Your Door Step (With in 10 KM)</th><td class="price" id="discount_price_store" style="text-align: right;color: #323232;font-size: 20px;">&nbsp;: <i class="fa fa-inr" aria-hidden="true"></i>&nbsp<%=within_10km%></td></tr>
                            <% 
                            }
                            %>
						   </table>
                          </p>
                  <div class="price-block" style=" padding-top: 15px;">
                    <div class="price-box">
					<div> 
					 </div>
					  <table style=" color: #575656;" id="check_with_without_battery">
						  <tr>
							  <th class="price-label" style="font-size: 20px;">Final Service Price &nbsp;&nbsp;</th>
							  <td class="price" id="discount_price" style="text-align: right;color: #323232;font-size: 20px;">: &nbsp;&nbsp;&nbsp;<i class="fa fa-inr" aria-hidden="true"></i> <%=Service_Price_Discount_RXT%></td>
						  </tr>
					  </table>
                    </div>
					<div>
						  <div style="font-size:1em; margin-top:5px;" id="without-old-note"><b>Note</b>: Need more assistance while ordering the Service ??</br> We are here to help you, <b>Call +91 96034 67559</b> or <a href="javascript:Open_Chat()"><span class="label label-default chat-with-us">Chat with Us</span></a> </div></br>
					 </div>
                  </div>
                </div>
				<div class="product-shop col-sm-12 col-md-3 col-xs-12">
					<div class="col-sm-6 col-md-12 col-xs-12" style=" padding: 0px;">
						<ul class="styled-list arrow" style="font-size: 15px;color: #727272;">
							<li><strong>Trained Professionals</strong></li>
							<li><strong>100% Customer Satisfaction</strong></li>
							<li><strong>Fastest & Professional Serivce</strong></li>
						</ul>
						</br>
					</div>
					<div class="col-sm-6 col-md-12 col-xs-12" style=" padding: 0px;">											
						<div class="add-to-box">
							<div class="price-overview-custom" id="final_price"><i class="fa fa-inr" aria-hidden="true"></i> <%=Service_Price_Discount_RXT%> </div>
							<div style="font-size: 10px;padding-bottom: 1px;text-align: left;">(Prices are inclusive of all taxes)</div>
													
					<div id="ref_code" class="form-group hide" style="margin-top: 15px;text-align: -webkit-left;">
					<table>
					<tbody><tr><label id="ref_code_label" for="name" class="control-label" style="font-size: larger;">Enter Referral Code</label></tr>
					<tr id="inp_ref_code"><td><input type="text" onkeyup="javascript:backspace_press(event.key)" name="name" class="form-control" style="font-size: 20px;" id="refcode" placeholder="XXXXXXXX"></td><td><span class="label label-danger" style="font-size: 14px;background-color: #e02d29;cursor: pointer;" id="applycode" onclick='javascript:validate_code()'>Apply</span></td></tr></tbody>
					</table> 
					</div>
					
						<%
						if(Product_Type.equals("Bike Batteries") || Product_Type.equals("Bike Battery") || Product_Type.equals("Car Batteries") || Product_Type.equals("Car Battery"))
						{
						%>
                                <div class="add-to-cart" style="display: table;">
                                <p><strong>Rs.<%=store%> for each additional battery service</strong>. 
                                    </p> 
                                  <label for="qty">Quantity:</label>
                                  <div class="pull-left">
                                    <div class="custom pull-left">
                                      <button onClick="var result = document.getElementById('product_qty'); var product_qty = result.value; if( !isNaN( product_qty ) &amp;&amp; product_qty &gt; 1 ) result.value--;return false;" class="reduced items-count Battery_Quantity" type="button"><i class="icon-minus">&nbsp;</i></button>
                                      <input type="tel" class="input-text qty Battery_Quantity_value form-control yes" title="Battery Quantity" value="<%=Quantity%>" maxlength="2" id="product_qty" name="product_qty">
                                      <!--- <div id="product_qty-error" style="display:none;"></div>  --->
                                      <button onClick="var result = document.getElementById('product_qty'); var product_qty = result.value; if( !isNaN( product_qty )) result.value++;return false;" class="increase items-count Battery_Quantity" type="button"><i class="icon-plus">&nbsp;</i></button>
                                    </div>
                                  </div>
                                </div>
                               <div id="vehicle_make_model_div" style=" margin-top: 7px;">
								<p class="products-vehicle-details-success-custom">Your current Vehicle Details: <br> <strong><%=Vehicle_Make%></strong>, <strong><%=Vehicle_Model%></strong>. <br> 
								</p> 
							</div>
						<%
						}
						else
						{
						%>
							<div class="add-to-cart" style="display: table;">
							<p><strong>Rs.<%=store%> for each additional battery service</strong>. 
								</p> 
							  <label for="qty">Quantity:</label>
							  <div class="pull-left">
								<div class="custom pull-left">
								  <button onClick="var result = document.getElementById('product_qty'); var product_qty = result.value; if( !isNaN( product_qty ) &amp;&amp; product_qty &gt; 1 ) result.value--;return false;" class="reduced items-count Battery_Quantity" type="button"><i class="icon-minus">&nbsp;</i></button>
								  <input type="tel" class="input-text qty Battery_Quantity_value form-control yes" title="Battery Quantity" value="<%=Quantity%>" maxlength="2" id="product_qty" name="product_qty">
								  <!--- <div id="product_qty-error" style="display:none;"></div>  --->
								  <button onClick="var result = document.getElementById('product_qty'); var product_qty = result.value; if( !isNaN( product_qty )) result.value++;return false;" class="increase items-count Battery_Quantity" type="button"><i class="icon-plus">&nbsp;</i></button>
								</div>
							  </div>
							</div>
							<div id="Inverter_battery_div" style=" margin-top: 7px;">
								<p class="products-vehicle-details-success-custom">Your current Inverter Battery Capacity : <br> <strong><%=Product_Capacity%></strong>. <br> 
								</p> 
							</div>
						<%
						}
						%>
						
						<div class="add-to-box">
						<button class="button btn-cart btn" title="Buy Now" type="button" disabled="disabled" onClick="javascript:Book_Service_Online_Fun()" style="font-size: 19px; padding: 15px 25px 15px;  margin-left: 0px;"><span> Please wait</span></button>
						</div>
						 </div>
						<div class="add-to-box">
							<input type="hidden" name="product_services_type_page" id='product_services_type_page' class='product_services_type_page' value="<%=Services_Type_RXT%>">
							<input type="hidden" name="vehicle_make_page" id='vehicle_make_page' class='vehicle_make_page' value="<%=Vehicle_Make%>">
							<input type="hidden" name="vehicle_model_page" id='vehicle_model_page' class='vehicle_model_page' value="<%=Vehicle_Model%>">
							<input type="hidden" name="service_price_page" id='service_price_page' class='service_price_page' value="<%=Service_Price_MRP_RXT%>">
							<input type="hidden" name="service_discount_price_page" id='service_discount_price_page' class='service_discount_price_page' value="<%=Service_Price_Discount_RXT%>">
						</div>
					</div>
				</div>
              </form>
            </div>
			
			<div class="col-sm-12 Product_Description" id="Product_Description">
				<div class="box-additional">
					<div class="product-tabs-content-inner">
							<div class="block block-tags">
								<div class="block-title">Service Description :</div>
								<div class="block-content">
								<p  class="hide" >  </p><br />
								<h5 class="hide" ><strong>  Benefits</strong></h4>
								<div><%=Service_Decription_Temp%></div>
								<p  class="hide"></p><br/>
							</div>
						</div>
					</div>
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
  <!-- Button trigger modal -->

  

<!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "/dev/includes/jsp/includes_service_js.jsp" />
<!---################################## CSS Include Ends  ################################------>

<!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_footer.jsp" />
<!---################################## CSS Include Ends  ################################------>

<script>
$(document).ready(function() {
	if (screen.width <= 767)
	{
	 $('html, body').animate({
			scrollTop: $("#Page_Result_ID").offset().top-100
		},500);
	}
    
    //alert("inside");
    if(($('#store_price').val()=="NA" && $('#bat_type').val()=="Bike Batteries")||($('#store_price').val()=="NA" && $('#bat_type').val()=="Bike Battery"))
    {
        $('#store_price').val('50');
    }    
    else if(($('#store_price').val()=="NA" && $('#bat_type').val()=="Car Batteries")||($('#store_price').val()=="NA" && $('#bat_type').val()=="Car Battery"))
    {
        $('#store_price').val('100');
    }
    else
    {}

});
</script>
  <script>
    function betterParseFloat(x){
      if(isNaN(parseFloat(x)) && x.length > 0)
        return betterParseFloat(x.substr(1));
      return parseFloat(x);
    }
    function usd(x){
      x = betterParseFloat(x);
      if(isNaN(x))
        return "$0.00";
      var dollars = Math.floor(x);
      var cents = Math.round((x - dollars) * 100) + "";
      if(cents.length==1)cents = "0"+cents;
      return "$"+dollars+"."+cents;
    }
  </script>
  
 
<input type="hidden" name="publicUrl" id='publicUrl' value="<%=publicUrl%>">
<input type="hidden" name="baseURL" id='baseURL' value="<%=baseURL%>">
<input type="hidden" name="baseurldirectory" id='baseurldirectory' value="<%=baseurldirectory%>">
<input type="hidden" name="brandkeyword" id='brandkeyword' value="Common">
<input type="hidden" name="backkeyword" id='backkeyword' value="index">
<input type="hidden" name="store_price" id='store_price' class='store' value="<%=store%>">
<input type="hidden" name="service_place_select" id='service_place_select' class='service_place_select' value="<%=Services_Place%>">
<input type="hidden" name="within_5km_price" id='within_5km_price' class='within_5km' value="<%=within_5km%>">
<input type="hidden" name="within_10km_price" id='within_10km_price' class='within_10km' value="<%=within_10km%>">
<input type="hidden" name="product_page_type" id='product_page_type' class="product_page_type" value="single_service_page">
<input type="hidden" name="product_page_type" id='product_page_type' class="product_page_type" value="single_service_page">
<input type="hidden" name="product_vehicle_make" id='product_vehicle_make' value="<%=Vehicle_Make%>">
<input type="hidden" name="product_vehicle_model" id='product_vehicle_model' value="<%=Vehicle_Model%>">
<input type="hidden" name="product_capacity" id='product_capacity' value="<%=Product_Capacity%>">
<input type="hidden" name="Quantity" id='Quantity' value="<%=Quantity%>">
<input type="hidden" name="product_type" id='product_type' value="<%=Product_Type%>">
<input type="hidden" name="bat_type" id='bat_type' value="<%=Product_Type%>">
<input type="hidden" name="product_state_page" id='product_state_page' value="<%=Product_State_Cookie%>">
<input type="hidden" name="product_city_page" id='product_city_page' value="<%=Product_City_Cookie%>">


<script type="text/javascript" src="<%=publicUrl%><%=baseurldirectory%>dev/js/product_details_service_function.js?v=12"></script>
 <script>
$(document).ready(function() {
	if (screen.width <= 767)
	{
	 $('html, body').animate({
			scrollTop: $("#Page_Result_ID").offset().top
		},500);
	}
    
 var product_type=$('#product_type').val();
 var order_product_qty;
    
if(product_type=="Car Batteries" || product_type=="Car Battery" || product_type=="Bike Batteries" || product_type=="Bike Battery")
{
order_product_qty="1";
}
else
{
 order_product_qty=$("#product_qty").val();
}
//alert(order_product_qty);
//alert(product_type);
var number_regex = /^\d+$/;
if ((!order_product_qty.match(number_regex) && order_product_qty.length > 0) || (!order_product_qty.match(number_regex)) || (order_product_qty.length == 0) || (order_product_qty<=0) || (isNaN(parseFloat(order_product_qty))==true) || order_product_qty== "undefined")
{
	$("#product_qty").val(1);
	order_product_qty=1;
	FinalService_Price_Discount_RXT=order_discount_price;
	//alert(11);
}
else
{	
	FinalService_Price_Discount_RXT=+Service_Price_Discount_RXT+($('#store_price').val()*(order_product_qty-1));
	$('#final_price').html("<i class='fa fa-inr' aria-hidden='true'></i>&nbsp;"+FinalService_Price_Discount_RXT);
	FinalService_Price_MRP_RXT=+Service_Price_MRP_RXT+($('#store_price').val() *(order_product_qty-1));
	$('#discount_price').html("<i class='fa fa-inr' aria-hidden='true'></i>&nbsp;"+FinalService_Price_Discount_RXT);
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
     
     
    
	   function changeprice(){
            
        //alert(22);
        var FinalService_Price_Discount_RXT;
        //alert($('#bat_type').val());
        //alert(value);
        var battery_buy_type = $("input[name='service_place_select']:checked").val();
		order_product_qty=$("#product_qty").val();
		//alert("order_product_qty"+order_product_qty);
        
        if(order_product_qty=="undefined" || order_product_qty==undefined)
        {
            order_product_qty=1;
        }
        else
        {
            order_product_qty=order_product_qty;
        }
        

          if(battery_buy_type=="store")
            {
               // alert($('#store_price').val());
                FinalService_Price_Discount_RXT=+$('#store_price').val()*(order_product_qty);
                $('#final_price').html("<i class='fa fa-inr' aria-hidden='true'></i>&nbsp;"+FinalService_Price_Discount_RXT);		
                $('#discount_price').html("<i class='fa fa-inr' aria-hidden='true'></i>&nbsp;"+FinalService_Price_Discount_RXT);		
                $('#service_discount_price_page').val($('#store_price').val());
                $('#service_place_select').val("store");
            }	
            else if(battery_buy_type=="within_5km")
            {
                //alert($('#within_5km_price').val());
                 FinalService_Price_Discount_RXT=+$('#within_5km_price').val()+($('#store_price').val() *(order_product_qty-1));
                 $('#final_price').html("<i class='fa fa-inr' aria-hidden='true'></i>&nbsp;"+FinalService_Price_Discount_RXT);		
                 $('#discount_price').html("<i class='fa fa-inr' aria-hidden='true'></i>&nbsp;"+FinalService_Price_Discount_RXT);
                 $('#service_discount_price_page').val($('#within_5km_price').val());
                 $('#service_place_select').val("within_5km");
            }		
            else if(battery_buy_type=="within_10km")
            {
                //alert($('#within_10km_price').val());
                FinalService_Price_Discount_RXT=+$('#within_10km_price').val()+($('#store_price').val() *(order_product_qty-1));
                $('#final_price').html("<i class='fa fa-inr' aria-hidden='true'></i>&nbsp;"+FinalService_Price_Discount_RXT);	
                 $('#discount_price').html("<i class='fa fa-inr' aria-hidden='true'></i>&nbsp;"+FinalService_Price_Discount_RXT);
                 $('#service_discount_price_page').val($('#within_10km_price').val());
                 $('#service_place_select').val("within_10km");
            }
            else
            {
                alert("Opps !. Some thing went wrong. Please reload the Page.");
            } 
            
        

    }
	function Scroll_to_next()
	{
		var total_height=$(document).height();
		var current_height=$(document).scrollTop();
		var window_height=screen.height-100;
		var goHeight=current_height+window_height;
		$('html, body').animate({scrollTop:goHeight}, 'slow');
	}
	function viewFullDescription()
	{
		$('html, body').animate({
			scrollTop: $("#Product_Description").offset().top-100
		},1000);
	}
	
	function GetServicePackagePage_Function(event)
	{
		if ($(event).closest(".actions").find('.Services_Package').val() == "0" || $(event).closest(".actions").find('.Services_Package').val() == "" || $(event).closest(".actions").find('.Services_Package').val() == "default")
		{
			$(event).closest(".actions").find('.Services_Package-error').show();
			var errMsg ="<span class='error'>Please select <b>Some Service Package</b></span>";
			$(event).closest(".actions").find('.Services_Package-error').html(errMsg);
			$(event).closest(".actions").find('.Services_Package-error').addClass('error');
			$(event).closest(".actions").find('.Services_Package-error').focus();
			return;
		}
		else
		{
			$(event).closest(".actions").find('.Services_Package-error').hide();
		}
		var ServicePackage_URL=$("#publicUrl").val()+$("#baseurldirectory").val()+"carservices/"+$("#product_state_page").val().replace(/ /g, "-")+"/"+$("#product_city_page").val().replace(/ /g, "-")+"/"+$("#product_vehicle_make").val().replace(/ /g, "-")+"/"+$("#product_vehicle_model").val().replace(/ /g, "-")+"/"+$(event).closest("div").find('.Services_Type').val().replace(/ /g, "-")+"/"+$(event).closest(".actions").find('.Services_Package').val().replace(/ /g, "+")+"/";
		window.location.href= ServicePackage_URL;
		console.log(ServicePackage_URL);
	}

 </script>
  

</body>
</html>