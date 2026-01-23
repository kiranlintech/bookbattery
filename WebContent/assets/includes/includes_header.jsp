 <%-- 
Document   		 : includes_header.jsp
Created on 		 : June 6, 2020, 07:14:12 AM
Author     		 : Bharath Beeky
Document         : This jsp is used as header in all consumer pages of BookBattery Batteries.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.util.Hashtable,java.util.ResourceBundle,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Enumeration,java.util.Properties,java.io.FileInputStream,java.io.File,javax.servlet.ServletContext,com.ngit.javabean.consumers.products.GetCookie,com.ngit.javabean.qrymgr.QueryManager"%>
<%
Properties propsMOPConfig = new Properties();
ServletContext context = getServletContext();

ServletContext servapp = getServletConfig().getServletContext();
String fileName = servapp.getRealPath("properties/bookbatteryconfig.properties");
File fin1      = new File(fileName); 
//ResourceBundle fin1 = ResourceBundle.getBundle("properties/bookbatteryconfig.properties");
propsMOPConfig.load(new FileInputStream(fin1)); 
String domainname = (propsMOPConfig.getProperty("domainname")!=null)?propsMOPConfig.getProperty("domainname"):"";
String publicUrl = (propsMOPConfig.getProperty("publicUrl")!=null)?propsMOPConfig.getProperty("publicUrl"):"";
String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?propsMOPConfig.getProperty("baseurldirectory"):"";
QueryManager qm;
qm = QueryManager.getInstance(propsMOPConfig);
//################### Getting Location From Cookies 
GetCookie State_City = new GetCookie(qm);
String State_City_Resp=  State_City.getCookieStateCity(request,response,session);
LogLevel.DEBUG(5, new Throwable(), "State_City_Resp :" + State_City_Resp);

String[] State_City_Arr=State_City_Resp.split("~");

String Product_State_Cookie=State_City_Arr[0];
LogLevel.DEBUG(5, new Throwable(), "Product_State_Cookie :" + Product_State_Cookie);
String Product_City_Cookie=State_City_Arr[1];
LogLevel.DEBUG(5, new Throwable(), "Product_City_Cookie :" + Product_City_Cookie);

//################### Getting Location From Cookies 
String Product_State_Cookie_URL= Product_State_Cookie.replaceAll(" ", "-");
String Product_City_Cookie_URL= Product_City_Cookie.replaceAll(" ", "-");
			
%>
      <div id="ask_location" class="modal fade" style="display: none;padding-right: 17px;border: 2px Solid #e2070f;text-align: center;border-radius: 4px;opacity: unset;">

			<div class="form-group" style="font-size: 17px;color: #e2070f;margin: 5px;text-align: -webkit-center;font-weight: 600;">
					<span class="icon-map-marker" style="font-size: 16px;"></span>&nbsp;&nbsp;Select Your City
				</div>
				  <div class="hide">
					<p style="font-size: 15px;padding: 5px;"><span>Popular Cities</span></p>
				  </div>
				<div class="col-xs-12 col-sm-12 col-md-12" style="padding: 0;">
				<section class="latest-blog">
					<div class="pro-manifacture-custom ">
					<div class="col-xs-3 col-sm-2 col-md-2 loc_ico">
						<div>
							<div class="blog-img-height-custom blog-img" style="text-align: center;margin: 2px;"> 
								<a href="#" onclick="selectStateandCityFromPopular('Karnataka','Bangalore')">
									<img class="width_100_class" src="/bookbattery/images/loc_logo/location-bangalore.png" alt="car Battery Bangalore" style="width: 65px !important;">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="#" onclick="selectStateandCityFromPopular('Karnataka','Bangalore')">Bangalore</a> </p>
						</div>
					  </div>
					  <div class="col-xs-3 col-sm-2 col-md-2 loc_ico">
						<div>
							<div class="blog-img-height-custom blog-img" style="text-align: center;margin: 2px;"> 
								<a href="#" onclick="selectStateandCityFromPopular('Telangana','Hyderabad')">
									<img class="width_100_class" src="/bookbattery/images/loc_logo/location-hyderabad.svg" alt="Car battery Hyderabad" style="width: 65px !important;">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="#" onclick="selectStateandCityFromPopular('Telangana','Hyderabad')">Hyderabad</a> </p>
						</div>
					  </div>
					  <div class="col-xs-3 col-sm-2 col-md-2 loc_ico">
						<div>
							<div class="blog-img-height-custom blog-img" style="text-align: center;margin: 2px;"> 
								<a href="#" onclick="selectStateandCityFromPopular('Tamil Nadu','Chennai')">
									<img class="width_100_class" src="/bookbattery/images/loc_logo/location-chennai.png" alt="Car battery Chennai" style="width: 65px !important;">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="#" onclick="selectStateandCityFromPopular('Tamil Nadu','Chennai')">Chennai</a> </p>
						</div>
					  </div>
					  <div class="col-xs-3 col-sm-2 col-md-2 loc_ico">
						<div>
							<div class="blog-img-height-custom blog-img" style="text-align: center;margin: 2px;">
								<a href="#" onclick="selectStateandCityFromPopular('Delhi','New Delhi')">
									<img class="width_100_class" src="/bookbattery/images/loc_logo/location-delhi.svg" alt="Car battery Delhi" style="width: 65px !important;">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="#" onclick="selectStateandCityFromPopular('Delhi','New Delhi')">New Delhi</a> </p>
						</div>
					  </div>
					  <div class="col-xs-3 col-sm-2 col-md-2 loc_ico">
						<div>
							<div class="blog-img-height-custom blog-img" style="text-align: center;margin: 2px;"> 
								<a href="#" onclick="selectStateandCityFromPopular('Maharashtra','Mumbai')">
									<img class="width_100_class" src="/bookbattery/images/loc_logo/location-mumbai.svg" alt="Car battery Mumbai" style="width: 65px !important;">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="#" onclick="selectStateandCityFromPopular('Maharashtra','Mumbai')">Mumbai</a> </p>
						</div>
					  </div>
					  <div class="col-xs-3 col-sm-2 col-md-2 loc_ico">
						<div>
							<div class="blog-img-height-custom blog-img" style="text-align: center;margin: 2px;"> 
								<a href="#" onclick="selectStateandCityFromPopular('Uttar Pradesh','Noida')">
									<img class="width_100_class" src="/bookbattery/images/loc_logo/location-noida.svg" alt="Car battery Noida" style="width: 65px !important;">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="#" onclick="selectStateandCityFromPopular('Uttar Pradesh','Noida')">Noida</a> </p>
						</div>
					  </div>
					  	<div class="col-xs-3 col-sm-2 col-md-2 loc_ico hide">
						<div>
							<div class="blog-img-height-custom blog-img" style="text-align: center;margin: 2px;"> 
								<a href="#" onclick="selectStateandCityFromPopular('Gujarat','Ahmedabad')">
									<img class="width_100_class" src="/bookbattery/images/loc_logo/location-ahmedabad.png" alt="Car battery Noida" style="width: 65px !important;">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="#" onclick="selectStateandCityFromPopular('Gujarat','Ahmedabad')">Ahmedabad</a> </p>
						</div>
					  </div>
					  	  <div class="col-xs-3 col-sm-2 col-md-2 loc_ico">
						<div>
							<div class="blog-img-height-custom blog-img" style="text-align: center;margin: 2px;"> 
								<a href="#" onclick="selectStateandCityFromPopular('Maharashtra','Pune')">
									<img class="width_100_class" src="/bookbattery/images/loc_logo/location-pune.svg" alt="Car battery Noida" style="width: 65px !important;">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="#" onclick="selectStateandCityFromPopular('Maharashtra','Pune')">Pune</a> </p>
						</div>
					  </div>
					  <div class="col-xs-3 col-sm-2 col-md-2 loc_ico">
						<div>
							<div class="blog-img-height-custom blog-img" style="text-align: center;margin: 2px;"> 
								<a href="#" onclick="selectStateandCityFromPopular('Andhra Pradesh','Chittoor')">
									<img class="width_100_class" src="/bookbattery/images/loc_logo/location-chittoor.svg" alt="Car battery Chittoor" style="width: 65px !important;">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="#" onclick="selectStateandCityFromPopular('Andhra Pradesh','Chittoor')">Chittoor</a> </p>
						</div>
					  </div>
					</div>
				</section>
				</div>
				<div class="hide col-xs-12 col-sm-12 col-md-12" style="padding: 0;">
					<p style="font-size: 15px;padding: 5px;"><span>Other Cities</span></p>
				 </div>
				<div class="form-group col-xs-12 col-sm-5 col-md-5 dropdown_class hide">
					  <select class="form-control yes" id="product_state"><option style="font-family:Verdana;font-size:12px;color:#CCCCCC;" value="default">&nbsp;Select&nbsp;State</option><option value="Andaman and Nicobar">Andaman and Nicobar</option><option value="Andhra Pradesh">Andhra Pradesh</option><option value="Arunachal Pradesh">Arunachal Pradesh</option><option value="Assam">Assam</option><option value="Bihar">Bihar</option><option value="Chattisgarh">Chattisgarh</option><option value="Delhi">Delhi</option><option value="Goa">Goa</option><option value="Gujarat">Gujarat</option><option value="Haryana">Haryana</option><option value="Himachal Pradesh">Himachal Pradesh</option><option value="Jammu and Kashmir">Jammu and Kashmir</option><option value="Jharkhand">Jharkhand</option><option value="Karnataka">Karnataka</option><option value="Kerala">Kerala</option><option value="Madhya Pradesh">Madhya Pradesh</option><option value="Maharashtra">Maharashtra</option><option value="Manipur">Manipur</option><option value="Meghalaya">Meghalaya</option><option value="Nagaland">Nagaland</option><option value="Orissa">Orissa</option><option value="Punjab">Punjab</option><option value="Rajasthan">Rajasthan</option><option value="Tamil Nadu">Tamil Nadu</option><option value="Telangana">Telangana</option><option value="Tripura">Tripura</option><option value="Union Territories">Union Territories</option><option value="Uttar Pradesh">Uttar Pradesh</option><option value="Uttarakhand">Uttarakhand</option><option value="West Bengal">West Bengal</option>
</select>
					  <div id="product_state-error" style="display:none;"></div>
				</div>
				<div class="form-group col-xs-12 col-sm-5 col-md-5 dropdown_class hide">
				  <select class="form-control yes" id="product_city"><option style="font-family:Verdana;font-size:12px;color:#CCCCCC;" value="default">&nbsp;Select&nbsp;City</option><option value="Bangalore">Bangalore</option><option value="Mangalore">Mangalore</option><option value="Mysore">Mysore</option><option value="Hubli">Hubli</option><option value="Belgaum">Belgaum</option><option value="Bellary">Bellary</option><option value="Bidar">Bidar</option><option value="Bijapur">Bijapur</option><option value="Darwad">Darwad</option><option value="Davanagere">Davanagere</option><option value="Gulbarga">Gulbarga</option><option value="Hassan">Hassan</option><option value="Kolar">Kolar</option><option value="Madikeri">Madikeri</option><option value="Mandya">Mandya</option><option value="Raichur">Raichur</option><option value="Shimoga">Shimoga</option><option value="Tumkur">Tumkur</option><option value="Udupi">Udupi</option><option value="Ullal">Ullal</option>
</select>
				  <div id="product_city-error" style="display:none;"></div>
				</div>
				<div class="col-xs-12 col-sm-2 col-md-12 button_class">
					<button type="button" class="button btn btn-cart" data-dismiss="modal" style="margin-top: 15px; line-height: 0;" onclick="CloseLocationDetails()">Close</button>
				</div>
	</div>
      <!-- Header Area Start -->
        <header>
            <!-- Header Bottom Start -->
            <div id="myHeader" class="header-bottom header-sticky">
                <div class="container">    
               <div class="row hidden-xs">
               <div class="col-md-12 col-lg-12 col-sm-12 col-xs-12 d-none d-lg-block" style="">
               <div class=""><ul style="text-align: center;font-size: 14px; padding:5px; background: #e2070f;width: auto;float: right;">
<li>
<a onclick="AskLocationDetails()" href="javascript:void(0)" style="color: #f8f9fa;font-weight: 600;"><i class="fa fa-map-marker fa-5"></i>&nbsp;<span> <b id="product_city_top_nav"><%=Product_City_Cookie.replaceAll("-"," ")%></b>,<b id="product_state_top_nav"><%=Product_State_Cookie.replaceAll("-"," ")%></b></a>
</li>
</ul>
</div>
               </div>
               </div>            
                   <div class="row" style="display: flex;-ms-flex-wrap: wrap;flex-wrap: wrap;">
	                   <div class="col-md-2 col-lg-2 col-sm-3 col-xs-6">
	                       <div class="logo" style="margin-top: 3%;">
                                <a href="<%=publicUrl%><%=baseurldirectory%>" style="margin: -15px 0px -15px 0px;"><img src="/bookbattery/assets/img/logo/bookbatterylogo.jpeg" alt="logo-image"></a>
                            </div>                    
					   </div>
					   <div class="col-md-3 col-lg-3 col-sm-3 col-xs-6 d-lg-none hidden-lg hidden-md hidden-sm" style="max-width: 50%;">
	                       <div class="loc_class"><ul style="text-align: center;font-size: 13px; padding:2px; background: #e2070f;width: auto;">
<li>
<a onclick="AskLocationDetails()" href="javascript:void(0)" style="color: #f8f9fa;font-weight: 600;"><i class="fa fa-map-marker fa-5"></i>&nbsp;<span> <b id="product_city_top_nav"><%=Product_City_Cookie.replaceAll("-"," ")%></b>,<b id="product_state_top_nav"><%=Product_State_Cookie.replaceAll("-"," ")%></b></a>
</li>
</ul>
</div>                   
					   </div>
					   <div class="col-md-7 col-lg-7 col-sm-6 col-xs-6 d-none d-lg-block hidden-xs">
	                      <div class="middle-menu">
                                <nav style="height: auto;">
                                    <ul class="middle-menu-list">
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>">home</a></li>
                                        <li><a href="#">shop<i class="fa fa-angle-down"></i></a>
                                            <!-- Home Version Dropdown Start -->
                                            <ul class="ht-dropdown dropdown-style-two">
                                                <li><a href="#">Battery</a>
                                                    <!-- Start Two Step -->
                                                    <ul class="ht-dropdown dropdown-style-two sub-menu">
                                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Car-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Car Battery</a>
                                                            <!-- Start Three Step -->
                                                            <ul class="ht-dropdown dropdown-style-two sub-menu">
                                                                <li><a href="<%=publicUrl%><%=baseurldirectory%>BatteryBrand/Amaron/Car-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Amaron</a></li>                                                               
                                                                <li><a href="<%=publicUrl%><%=baseurldirectory%>BatteryBrand/Exide/Car-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Exide</a></li>
                                                            </ul>
                                                        </li>
                                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Bike-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Bike Battery</a>
                                                            <!-- Start Three Step -->
                                                            <ul class="ht-dropdown dropdown-style-two sub-menu">
                                                                <li><a href="<%=publicUrl%><%=baseurldirectory%>BatteryBrand/Amaron/Bike-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Amaron</a></li>
                                                                <li><a href="<%=publicUrl%><%=baseurldirectory%>BatteryBrand/Exide/Bike-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Exide</a></li>                                                               
                                                            </ul>
                                                        </li>
                                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>Brand/Inverter-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Inverter Battery</a>
                                                            <!-- Start Three Step -->
                                                            <ul class="ht-dropdown dropdown-style-two sub-menu">
                                                                <li><a href="<%=publicUrl%><%=baseurldirectory%>Inverter-Batteries/Amaron/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Amaron</a></li>
                                                                <li><a href="<%=publicUrl%><%=baseurldirectory%>Inverter-Batteries/Exide/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Exide</a></li>
                                                                <li><a href="<%=publicUrl%><%=baseurldirectory%>Inverter-Batteries/Luminous/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Luminous</a></li>                                                                 
                                                            </ul>
                                                        </li>
                                                    </ul>
                                                </li>
                                                <li><a href="<%=publicUrl%><%=baseurldirectory%>Brand/Inverter/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Inverter</a>
                                                <ul class="ht-dropdown dropdown-style-two sub-menu">
         											<li><a href="<%=publicUrl%><%=baseurldirectory%>Inverter/Amaron/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Amaron</a></li>
                                                    <li><a href="<%=publicUrl%><%=baseurldirectory%>Inverter/Exide/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Exide</a></li>
                                                    <li><a href="<%=publicUrl%><%=baseurldirectory%>Inverter/Luminous/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Luminous</a></li>
                                                </ul>                                               
                                                </li>
                                                <li><a href="<%=publicUrl%><%=baseurldirectory%>Brand/Inverter-and-Battery-Combo/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Inverter & Battery Combo</a>
                                                 <ul class="ht-dropdown dropdown-style-two sub-menu">
         											<li><a href="<%=publicUrl%><%=baseurldirectory%>Inverter-and-Battery-Combo/Amaron/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Amaron</a></li>
                                                    <li><a href="<%=publicUrl%><%=baseurldirectory%>Inverter-and-Battery-Combo/Exide/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Exide</a></li>
                                                    <li><a href="<%=publicUrl%><%=baseurldirectory%>Inverter-and-Battery-Combo/Luminous/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Luminous</a></li>
                                                </ul> 
                                                </li>
                                            </ul>
                                            <!-- Home Version Dropdown End -->
                                        </li>  
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Car-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Car Battery</a></li>                                      
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Bike-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Bike Battery</a></li>                                        
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>Brand/Inverter/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Inverter/Ups</a></li>                                        
                                    </ul>
                                </nav>
                            </div>                     
					   </div>
					   <div class="col-md-3 col-lg-3 col-sm-3 col-xs-12">
                           <div class="cart-box text-right" style="float: left;margin-left: 5px;">
								<ul>
									<li><a href="compare.html" style="font-size: 14px;padding: 0px;
									"><i class="fa fa-phone"></i>&nbsp;&nbsp;9603467559 (9 AM to 10 PM)</a>
									</li>              
								</ul>
							</div>            
					   </div>
                   </div>                             
                   <div class="mobile-menu" style="margin-right: 25px;">
                                <nav style="height: auto;">
                                    <ul>
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>">HOME</a>
                                        </li>
                                        <li><a href="#">SHOP</a>
                                            <!-- Mobile Menu Dropdown Start -->
                                            <ul>
                                                <li><a href="#">Battery</a>
                                                    <ul>
                                                        <li><a href="href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Car-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/"">Car Battery</a>
                                                            <!-- Start Three Step -->
                                                            <ul>
                                                                <li><a href="<%=publicUrl%><%=baseurldirectory%>BatteryBrand/Amaron/Car-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Amaron</a></li>
                                                                <li><a href="<%=publicUrl%><%=baseurldirectory%>BatteryBrand/Exide/Car-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Exide</a></li>
                                                            </ul>
                                                        </li>
                                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Bike-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Bike Battery</a>
                                                            <!-- Start Three Step -->
                                                            <ul>
                                                                <li><a href="<%=publicUrl%><%=baseurldirectory%>BatteryBrand/Amaron/Bike-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Amaron</a></li>
                                                                <li><a href="<%=publicUrl%><%=baseurldirectory%>BatteryBrand/Exide/Bike-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Exide</a></li>
                                                            </ul>
                                                        </li>
                                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>Brand/Inverter-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Inverter Battery</a>
                                                            <!-- Start Three Step -->
                                                            <ul>
                                                                <li><a href="<%=publicUrl%><%=baseurldirectory%>Inverter-Batteries/Amaron/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Amaron</a></li>
                                                                <li><a href="<%=publicUrl%><%=baseurldirectory%>Inverter-Batteries/Exide/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Exide</a></li>
                                                                <li><a href="<%=publicUrl%><%=baseurldirectory%>Inverter-Batteries/Luminous/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Luminous</a></li>
                                                            </ul>
                                                        </li>
                                                    </ul>
                                                </li>
                                                <li><a href="<%=publicUrl%><%=baseurldirectory%>Brand/Inverter/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Inverter</a>
                                                    <ul>
                                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>Inverter/Amaron/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Amaron</a></li>
                                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>Inverter/Exide/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Exide</a></li>
                                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>Inverter/Luminous/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Luminous</a></li>
                                                    </ul>
                                                </li>
                                                <li><a href="<%=publicUrl%><%=baseurldirectory%>Brand/Inverter-and-Battery-Combo/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Inverter Combo</a>
                                                    <ul>
                                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>Inverter-and-Battery-Combo/Amaron/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Amaron</a></li>
                                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>Inverter-and-Battery-Combo/Exide/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Exide</a></li>
                                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>Inverter-and-Battery-Combo/Luminous/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Luminous</a></li>
                                                    </ul>
                                                </li>
                                            </ul>
                                            <!-- Mobile Menu Dropdown End -->
                                        </li>
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Car-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">CAR BATTERY</a></li>
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>manufacturers/Bike-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">BIKE BATTERY</a></li>
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>Brand/Inverter/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">INVERTER/UPS</a></li>
                                    </ul>
                                </nav>
                            </div>
                        </div>
                        <!-- Mobile Menu  End -->
                <!-- Container End -->
            </div>
            <!-- Header Bottom End -->
        </header>
        <!-- Header Area End -->