<%@page language="java" import="java.util.Hashtable,java.io.File,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Enumeration,java.util.Properties,java.io.FileInputStream,javax.servlet.ServletContext,com.ngit.javabean.consumers.products.GetCookie,com.ngit.javabean.qrymgr.QueryManager"%>
<%
Properties propsMOPConfig = new Properties();
ServletContext context = getServletContext();

ServletContext servapp = getServletConfig().getServletContext();
String fileName = servapp.getRealPath("properties/bookbatteryconfig.properties");
File fin1      = new File(fileName); 
//ResourceBundle fin1 = ResourceBundle.getBundle("properties/bookbatteryconfig.properties");
propsMOPConfig.load(new FileInputStream(fin1)); 
new FileInputStream(fin1).close(); 
String publicUrl = (propsMOPConfig.getProperty("publicUrl")!=null)?propsMOPConfig.getProperty("publicUrl"):"";
String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?propsMOPConfig.getProperty("baseurldirectory"):"";
LogLevel.DEBUG(5, new Throwable(), "publicUrl :" + publicUrl);
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
<footer class="off-white-bg">
            <!-- Footer Top Start -->
            <div class="footer-top" style="background-color: #e2070f;">
    <div class="row">
                    <!-- Single Policy Start -->
                    <div class="col-lg-3 col-sm-6">
                        <div class="single-policy">
                            <div class="icone-img">
                                <img src="/bookbattery/assets/img/icon/1w.png" alt="">
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
                                <img src="/bookbattery/assets/img/icon/2w.png" alt="">
                            </div>
                            <div class="policy-desc">
                                <h3>Online Support 24/7</h3>
                                <p>Support online 24 hours</p>
                            </div>
                        </div>
                    </div>
                    <!-- Single Policy End -->
                    <!-- Single Policy Start -->
                    <div class="col-lg-3 col-sm-6">
                        <div class="single-policy">
                            <div class="icone-img">
                                <img src="/bookbattery/assets/img/icon/3w.png" alt="">
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
                                <img src="/bookbattery/assets/img/icon/4w.png" alt="">
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
            
            
            
            
            <div class="footer-top pt-50 pb-60">
                <div class="container">
                    <div class="row" style="margin: auto;">
                        <div class="col-lg-6 mr-auto ml-auto hide">
                            <div class="newsletter text-center">
                                <div class="main-news-desc">
                                     <div class="news-desc">
                                         <h3>Subscribe For Offer Updates</h3>
                                         <p>Get e-mail updates about our latest shop and special offers.</p>
                                     </div>
                                </div>
                                <div class="newsletter-box">
                                    <form action="#">
                                        <input class="subscribe" placeholder="Enter your email address" name="email" id="subscribe" type="text">
                                        <button type="submit" class="submit">subscribe</button>
                                    </form>
                                </div>
                             </div>                            
                        </div>
                    </div>                    
                    <div class="row" style="margin: auto;">
                        <!-- Single Footer Start -->
                        <div class="col-lg-4  col-md-7 col-sm-6">
                            <div class="single-footer">
                                <h3>Contact us</h3>
                                <div class="footer-content">
                                    <div class="loc-address">
                                        <span><i class="fa fa-map-marker"></i>#No 185 Springwoods,Bowrampet, Hyderabad, Telangana 500043.</span>
                                        <span><i class="fa fa-envelope-o"></i>Mail Us : contact@bookbattery.com</span>
                                        <span><i class="fa fa-phone"></i>Phone: +91 96034 67559</span>
                                    </div>
                                    <!--<div class="payment-mth"><a href="#"><img class="img" src="assets/img/footer/1.png" alt="payment-image"></a></div>-->
                                </div>
                            </div>
                        </div>
                        <!-- Single Footer Start -->
                        <!-- Single Footer Start -->
                        <div class="col-lg-2  col-md-5 col-sm-6 footer-full">
                            <div class="single-footer">
                                <h3 class="footer-title">By Car Brands</h3>
                                <div class="footer-content">
                                    <ul class="footer-list">
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Audi/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Audi Car Battery </a></li>
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/BMW/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">BMW Car Battery</a></li>
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Chevrolet/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Cheverlot Car Battery</a></li>
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Toyota/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Toyota Car Battery</a></li>
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Maruti-Suzuki/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Maruti Suzuki Car Battery</a></li>
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Ford/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Ford Car Battery</a></li>
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Mahindra-and-Mahindra/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Mahindra Car Battery</a></li>
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Honda/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Honda Car Battery</a></li>
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Hyundai/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Hyundai Car Battery</a></li>
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Car-Batteries/Tata/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Tata Car Battery</a></li>                                  
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!-- Single Footer Start -->
                        <!-- Single Footer Start -->
                        <div class="col-lg-2  col-md-4 col-md-4 col-sm-6 footer-full">
                            <div class="single-footer">
                                <h3 class="footer-title">By Bike Brands</h3>
                                <div class="footer-content">
                                    <ul class="footer-list">
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/Hero-MotoCorp/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Hero Bike Battery</a></li>
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/Honda/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Honda Bike Battery</a></li>
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/Bajaj/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Bajaj Bike Battery</a></li>
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/Yamaha/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Yamaha Bike Battery</a></li>
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/Royal-Enfield/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Royal Enfield Bike Battery</a></li>
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/TVS/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">TVS Bike Battery</a></li>
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/Suzuki/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Suzuki Bike Battery</a></li>
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/Mahindra-and-Mahindra/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Mahindra Bike Battery</a></li>
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/Kinetic/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Kinetic Bike Battery</a></li>
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>manufacturer/Bike-Batteries/Piaggio/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Piaggio Bike Battery</a></li>
                                        
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!-- Single Footer Start -->
                        <!-- Single Footer Start -->
                        <div class="col-lg-2 col-md-4 col-sm-6 footer-full">
                            <div class="single-footer">
                                <h3 class="footer-title">By Battery Brands</h3>
                                <div class="footer-content">
                                    <ul class="footer-list">
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>BatteryBrand/Amaron/Car-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Amaron Car Batteries</a></li>
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>BatteryBrand/Exide/Car-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Exide Car Batteries</a></li>
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>BatteryBrand/Amaron/Bike-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Amaron Bike Batteries</a></li>
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>BatteryBrand/Exide/Bike-Batteries/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Exide Bike Batteries</a></li>
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>Inverter-Batteries/Amaron/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>/">Amaron inverter Batteries</a></li>
                                        <li><a href="<%=publicUrl%><%=baseurldirectory%>Inverter-Batteries/Exide/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>">Exide inverter Batteries</a></li>
                                        <li style="width: max-content;"><a href="<%=publicUrl%><%=baseurldirectory%>Inverter-Batteries/Luminous/<%=Product_State_Cookie_URL%>/<%=Product_City_Cookie_URL%>">Luminous inverter Batteries</a></li>
                                        
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!-- Single Footer Start -->
                        <!-- Single Footer Start -->
                        <div class="col-lg-2 col-md-4 col-sm-6 footer-full hide">
                            <div class="single-footer">
                                <h3 class="footer-title">Let Us Help You</h3>
                                <div class="footer-content">
                                    <ul class="footer-list">
                                    	<li><a href="#">About us</a></li>
                                    	<li><a href="#">FAQ</a></li>
                                        <li><a href="#">Terms & Conditions</a></li>
                                        <li><a href="#">Privacy & Cookie Policy</a></li>
                                        <li><a href="#">Cancel & Refunds</a></li>
                                        <li><a href="#">Shipping Policy</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <!-- Single Footer Start -->
                    </div>
                    <!-- Row End -->
                </div>
                <!-- Container End -->
            </div>
            <!-- Footer Top End -->
            <!-- Footer Bottom Start -->
            <div class="footer-bottom off-white-bg2">
                <div class="container">
                    <div class="footer-bottom-content">
                        <p class="copy-right-text ml-15">Copyright © <a  href="#">BookBattery.com</a> All Rights Reserved.</p>
                        <div class="footer-social-content">
                            <ul class="social-content-list">
                                <li><a href="#"><i class="fa fa-twitter"></i></a></li>
                                <li><a href="#"><i class="fa fa-wifi"></i></a></li>
                                <li><a href="#"><i class="fa fa-google-plus"></i></a></li>
                                <li><a href="#"><i class="fa fa-facebook"></i></a></li>
                                <li><a href="#"><i class="fa fa-youtube"></i></a></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <!-- Container End -->
            </div>
            <!-- Footer Bottom End -->
        </footer>
        <!-- Footer End -->
