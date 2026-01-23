<%@page language="java" import="java.io.File,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Enumeration,java.util.Properties,java.io.FileInputStream,javax.servlet.ServletContext,com.ngit.javabean.consumers.products.GetCookie,com.ngit.javabean.qrymgr.QueryManager"%>
<%
Properties propsMOPConfig = new Properties();
ServletContext context = getServletContext();
FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
propsMOPConfig.load(fin1); 
fin1.close(); 
String publicUrl = (propsMOPConfig.getProperty("publicUrl")!=null)?propsMOPConfig.getProperty("publicUrl"):"";

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
<style>
.coppyright{
    padding:5px;
}
.coppyright a {
    
    font-size:13px;
    padding:7px;
}
footer h4.yellow {
    border-bottom: 2px solid #fbe270e0;
    margin-bottom: 10px;
}


</style>

	<div id="ask_location" class="modal fade">

			<div class="form-group" style="font-size: 15px;color: #484242;margin: 5px;text-align: -webkit-center;">
					<span class="icon-map-marker" style="font-size: 16px;"></span>&nbsp;&nbsp;Choose your <b>State</b> & <b>City</b>
				</div>
				  <div>
					<p style="font-size: 15px;padding: 5px;"><span>Popular Cities</span></p>
				  </div>
				<div class="col-xs-12 col-sm-12 col-md-12" style="padding: 0;">
				<section class="latest-blog">
					<div class="pro-manifacture-custom ">
					<div class="col-xs-3 col-sm-2 col-md-2 loc_ico">
						<div>
							<div class="blog-img-height-custom blog-img" style="text-align: center;margin: 2px;"> 
								<a href="#" onclick="selectStateandCityFromPopular('Karnataka','Bangalore')">
									<img class="width_100_class" src="/bookbattery/images/loc_logo/BookBattery-bangalore.png" alt="car Battery Bangalore" style="width: 65px !important;">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="#" onclick="selectStateandCityFromPopular('Karnataka','Bangalore')">Bangalore</a> </p>
						</div>
					  </div>
					  <div class="col-xs-3 col-sm-2 col-md-2 loc_ico">
						<div>
							<div class="blog-img-height-custom blog-img" style="text-align: center;margin: 2px;"> 
								<a href="#" onclick="selectStateandCityFromPopular('Telangana','Hyderabad')">
									<img class="width_100_class" src="/bookbattery/images/loc_logo/BookBattery-hyderabad.svg" alt="Car battery Hyderabad" style="width: 65px !important;">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="#" onclick="selectStateandCityFromPopular('Telangana','Hyderabad')" >Hyderabad</a> </p>
						</div>
					  </div>
					  <div class="col-xs-3 col-sm-2 col-md-2 loc_ico">
						<div>
							<div class="blog-img-height-custom blog-img" style="text-align: center;margin: 2px;"> 
								<a href="#" onclick="selectStateandCityFromPopular('Tamil Nadu','Chennai')">
									<img class="width_100_class" src="/bookbattery/images/loc_logo/BookBattery-chennai.svg" alt="Car battery Chennai" style="width: 65px !important;">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="#" onclick="selectStateandCityFromPopular('Tamil Nadu','Chennai')">Chennai</a> </p>
						</div>
					  </div>
					  <div class="col-xs-3 col-sm-2 col-md-2 loc_ico">
						<div>
							<div class="blog-img-height-custom blog-img" style="text-align: center;margin: 2px;">
								<a href="#" onclick="selectStateandCityFromPopular('Delhi','New Delhi')">
									<img class="width_100_class" src="/bookbattery/images/loc_logo/BookBattery-delhi.svg" alt="Car battery Delhi" style="width: 65px !important;">
								</a>
							</div>
						<p class="text-align-center-custom"><a href="#" onclick="selectStateandCityFromPopular('Delhi','New Delhi')">New Delhi</a> </p>
						</div>
					  </div>
					  <div class="col-xs-3 col-sm-2 col-md-2 loc_ico">
						<div>
							<div class="blog-img-height-custom blog-img" style="text-align: center;margin: 2px;"> 
								<a href="#" onclick="selectStateandCityFromPopular('Maharashtra','Mumbai')">
									<img class="width_100_class" src="/bookbattery/images/loc_logo/BookBattery-mumbai.svg" alt="Car battery Mumbai" style="width: 65px !important;">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="#" onclick="selectStateandCityFromPopular('Maharashtra','Mumbai')" >Mumbai</a> </p>
						</div>
					  </div>
					  <div class="col-xs-3 col-sm-2 col-md-2 loc_ico">
						<div>
							<div class="blog-img-height-custom blog-img" style="text-align: center;margin: 2px;"> 
								<a href="#" onclick="selectStateandCityFromPopular('Uttar Pradesh','Noida')">
									<img class="width_100_class" src="/bookbattery/images/loc_logo/BookBattery-noida.svg" alt="Car battery Noida" style="width: 65px !important;">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="#" onclick="selectStateandCityFromPopular('Uttar Pradesh','Noida')" >Noida</a> </p>
						</div>
					  </div>
					  	<div class="col-xs-3 col-sm-2 col-md-2 loc_ico">
						<div>
							<div class="blog-img-height-custom blog-img" style="text-align: center;margin: 2px;"> 
								<a href="#" onclick="selectStateandCityFromPopular('Gujarat','Ahmedabad')">
									<img class="width_100_class" src="/bookbattery/images/loc_logo/BookBattery-ahmedabad.png" alt="Car battery Noida" style="width: 65px !important;">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="#" onclick="selectStateandCityFromPopular('Gujarat','Ahmedabad')" >Ahmedabad</a> </p>
						</div>
					  </div>
					  	  <div class="col-xs-3 col-sm-2 col-md-2 loc_ico">
						<div>
							<div class="blog-img-height-custom blog-img" style="text-align: center;margin: 2px;"> 
								<a href="#" onclick="selectStateandCityFromPopular('Maharashtra','Pune')">
									<img class="width_100_class" src="/bookbattery/images/loc_logo/BookBattery-pune.svg" alt="Car battery Noida" style="width: 65px !important;">
								</a> 
							</div>
						<p class="text-align-center-custom"><a href="#" onclick="selectStateandCityFromPopular('Maharashtra','Pune')" >Pune</a> </p>
						</div>
					  </div>
					</div>
				</section>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-12" style="padding: 0;">
					<p style="font-size: 15px;padding: 5px;"><span>Other Cities</span></p>
				 </div>
				<div class="form-group col-xs-12 col-sm-5 col-md-5 dropdown_class">
					  <select class="form-control yes" id="product_state">
						<option value="0">Select State</option>
					  </select>
					  <div id='product_state-error'style="display:none;"></div>
				</div>
				<div class="form-group col-xs-12 col-sm-5 col-md-5 dropdown_class">
				  <select class="form-control yes" id="product_city">
					<option value="0">Select City</option>
				  </select>
				  <div id='product_city-error'style="display:none;"></div>
				</div>
				<div class="form-group col-xs-12 col-sm-2 col-md-2 button_class">
					<button type="button" class="button btn btn-cart" onclick="UpdateLocationDetails()">Continue</button>
				</div>
	</div>
		
  <div class="service-section">
    <div class="container">
      <div class="row">
        <div id="store-messages">
          <div class="col-md-4 col-xs-12 col-sm-4"><i class="icon-refresh">&nbsp;</i>
            <div class="message"> <span><strong>Free Professional </strong>Installation</span> Cash On Delivery, Best Prices. </div>
          </div>
          <div class="col-md-4 col-xs-12 col-sm-4"><i class="icon-truck">&nbsp;</i>
            <div class="message"><span><strong>Free Shipping</strong> in 4 - 24 Hours*</span>Not applicable for Bike Batteries . </div>
          </div>
          <div class="col-md-4 col-xs-12 col-sm-4"><i class="icon-discount">&nbsp;</i>
            <div class="message"><span><strong>100% Genuine Batteries & Inverters </strong> </span>We sell Authentic Branded Batteries and Inverters.</div>
          </div>
        </div>
      </div>
    </div>
  </div>

 <footer>
    <div class="container">
      <div class="inner">
        <div class="brand-logo">
          <div class="slider-items-products">
           </div>
        </div>
        <div class="footer-middle" class="col-md-12 col-sm-12 col-xs-12">

      <div class="col-md-3 col-sm-12 col-xs-12">
            <div class="footer-column-1" itemscope itemtype="http://schema.org/Organization">
              <div class="footer-logo"><a href="http://www.BookBattery.com/" title="Logo"  itemprop="url"><img src="<%=publicUrl%>/bookbattery/images/BookBattery_logo_footer.png" alt="logo" ></a></div>
              <p>BookBattery.com-  India's No. 1 online multibranded Battery and Inverter Store.</p>
             <div class="contacts-info">     
              <div style="color: #bebebe;font-size: 14px;padding-bottom: 8px;padding-top: 0px;"><b style="padding-left: 45px;">Head Quarters</b></br></div><div style="color: #bebebe;font-size: 14px;padding-bottom: 10px;padding-top: 0px;margin-left: 42px;">Asistmi Solutions Private Limited</div>            
			 <address>
             <i class="add-icon"></i><div style="padding-left: 45px; font-size: 13px;">#304, 4th Floor, Regent Prime,<br>No.48, Whitefield Main Road,<br>Bangalore - 560066.</div>
              </address>
              <div class="phone-footer"><i class="phone-icon">&nbsp;</i><a href="tel:+919603467559" style="font-weight: 700;">+91 96034 67559</a></div>
              <div class="email-footer"><i class="email-icon">&nbsp;</i> 
			   <a href="mailto:contact@BookBattery.com" alt="BookBattery | Amaron Exide online car battery and inverter battery store">Email Us</a>
            </div>
          </div>
              <div class="social">
                <ul class="link">
                  <li class="fb"><a itemprop="sameAs" title="BookBattery.com-is India's one and only official Amaron Battery Online e-retailer offering genuine batteries with free battery delivery & installation."  href="https://www.facebook.com/bookbattery/" target="_blank"></a></li>
				  <li class="fb-like hide" data-href="https://developers.facebook.com/docs/plugins/" data-layout="standard" data-action="like" data-size="small" data-show-faces="true" data-share="true"></li>
                  <li class="tw"><a itemprop="sameAs" title="http://BookBattery.com  -is India's one and only official Amaron Battery Online e-retailer offering genuine batteries with free battery delivery & installation." href="https://twitter.com/bookbattery/" target="_blank"></a></li>
                  <li class="linkedin"><a itemprop="sameAs" title="BookBattery.com Linkedin"  href="https://www.linkedin.com/company/BookBattery1" target="_blank"></a></li>
                  <li class="youtube"><a itemprop="sameAs" title="BookBattery.com YouTube"  href="https://www.youtube.com/c/BookBatteryamaron" target="_blank"></a></li>
			      <li class="instagram"><a itemprop="sameAs" href="https://www.instagram.com/bookbattery/" target="_blank"></a></li>
                  <li class="pinterest"><a itemprop="sameAs" href="https://www.pinterest.com/BookBatteryasis/" target="_blank"></a></li>
                </ul>
              </div>
            </div>
          </div>
          <div class="col-md-2 col-sm-6 col-xs-12">
            <h4 class="green">Car Batteries</h4>
            <ul class="links">
              <li class="first"><a href="<%=publicUrl%>/bookbattery/manufacturers/Car-Batteries/Gujarat/Ahmedabad/" title="How to buy">Car Battery Ahmedabad</a></li>
              <li class="first"><a href="<%=publicUrl%>/bookbattery/manufacturers/Car-Batteries/Karnataka/Bangalore/" title="How to buy">Car Battery Bangalore</a></li>
              <li class="first"><a href="<%=publicUrl%>/bookbattery/manufacturers/Car-Batteries/Tamil-Nadu/Chennai/" title="How to buy">Car Battery Chennai</a></li>
              <li class="first"><a href="<%=publicUrl%>/bookbattery/manufacturers/Car-Batteries/Delhi/New-Delhi/" title="How to buy">Car Battery Delhi</a></li>
              <li class="first"><a href="<%=publicUrl%>/bookbattery/manufacturers/Car-Batteries/Haryana/Gurgaon/" title="How to buy">Car Battery Gurgaon</a></li>
              <li class="first"><a href="<%=publicUrl%>/bookbattery/manufacturers/Car-Batteries/Telangana/Hyderabad/" title="How to buy">Car Battery Hyderabad</a></li>
              <li class="first"><a href="<%=publicUrl%>/bookbattery/manufacturers/Car-Batteries/West-Bengal/Kolkata/" title="How to buy">Car Battery Kolkata</a></li>
              <li class="first"><a href="<%=publicUrl%>/bookbattery/manufacturers/Car-Batteries/Maharashtra/Mumbai/" title="How to buy">Car Battery Mumbai</a></li>
              <li class="first"><a href="<%=publicUrl%>/bookbattery/manufacturers/Car-Batteries/Uttar-Pradesh/Noida/" title="How to buy">Car Battery Noida</a></li>
              <li class="first"><a href="<%=publicUrl%>/bookbattery/manufacturers/Car-Batteries/Maharashtra/Pune/" title="How to buy">Car Battery Pune</a></li>

            </ul>
          </div>

          <div class="col-md-2 col-sm-6 col-xs-12">
            <h4 class="orange">Inverter Batteries</h4>
            <ul class="links">
            <li class="first"><a href="<%=publicUrl%>/bookbattery/Brand/Inverter-Batteries/Gujarat/Ahmedabad/" title="How to buy">Inverter Battery Ahmedabad</a></li>
            <li class="first"><a href="<%=publicUrl%>/bookbattery/Brand/Inverter-Batteries/Karnataka/Bangalore/" title="How to buy">Inverter Battery Bangalore</a></li>
            <li class="first"><a href="<%=publicUrl%>/bookbattery/Brand/Inverter-Batteries/Tamil-Nadu/Chennai/" title="How to buy">Inverter Battery Chennai</a></li>
            <li class="first"><a href="<%=publicUrl%>/bookbattery/Brand/Inverter-Batteries/Delhi/New-Delhi/" title="How to buy">Inverter Battery Delhi</a></li>
            <li class="first"><a href="<%=publicUrl%>/bookbattery/Brand/Inverter-Batteries/Haryana/Gurgaon/" title="How to buy">Inverter Battery Gurgaon</a></li>
            <li class="first"><a href="<%=publicUrl%>/bookbattery/Brand/Inverter-Batteries/Telangana/Hyderabad/" title="How to buy">Inverter Battery Hyderabad</a></li>
            <li class="first"><a href="<%=publicUrl%>/bookbattery/Brand/Inverter-Batteries/West-Bengal/Kolkata/" title="How to buy">Inverter Battery Kolkata</a></li>
            <li class="first"><a href="<%=publicUrl%>/bookbattery/Brand/Inverter-Batteries/Maharashtra/Mumbai/" title="How to buy">Inverter Battery Mumbai</a></li>
            <li class="first"><a href="<%=publicUrl%>/bookbattery/Brand/Inverter-Batteries/Uttar-Pradesh/Noida/" title="How to buy">Inverter Battery Noida</a></li>
            <li class="first"><a href="<%=publicUrl%>/bookbattery/Brand/Inverter-Batteries/Maharashtra/Pune/" title="How to buy">Inverter Battery Pune</a></li>
            </ul>
          </div>    
          <div class="col-md-2 col-sm-6 col-xs-12">
            <h4 class="blue">Bike Batteries</h4>
            <ul class="links">
            <li class="first"><a href="<%=publicUrl%>/bookbattery/manufacturers/Bike-Batteries/Gujarat/Ahmedabad/" title="How to buy">Bike Battery Ahmedabad</a></li>
            <li class="first"><a href="<%=publicUrl%>/bookbattery/manufacturers/Bike-Batteries/Karnataka/Bangalore/" title="How to buy">Bike Battery Bangalore</a></li>
            <li class="first"><a href="<%=publicUrl%>/bookbattery/manufacturers/Bike-Batteries/Tamil-Nadu/Chennai/" title="How to buy">Bike Battery Chennai</a></li>
            <li class="first"><a href="<%=publicUrl%>/bookbattery/manufacturers/Bike-Batteries/Delhi/New-Delhi/" title="How to buy">Bike Battery Delhi</a></li>
            <li class="first"><a href="<%=publicUrl%>/bookbattery/manufacturers/Bike-Batteries/Haryana/Gurgaon/" title="How to buy">Bike Battery Gurgaon</a></li>
            <li class="first"><a href="<%=publicUrl%>/bookbattery/manufacturers/Bike-Batteries/Telangana/Hyderabad/" title="How to buy">Bike Battery Hyderabad</a></li>
            <li class="first"><a href="<%=publicUrl%>/bookbattery/manufacturers/Bike-Batteries/West-Bengal/Kolkata/" title="How to buy">Bike Battery Kolkata</a></li>
            <li class="first"><a href="<%=publicUrl%>/bookbattery/manufacturers/Bike-Batteries/Maharashtra/Mumbai/" title="How to buy">Bike Battery Mumbai</a></li>
            <li class="first"><a href="<%=publicUrl%>/bookbattery/manufacturers/Bike-Batteries/Uttar-Pradesh/Noida/" title="How to buy">Bike Battery Noida</a></li>
            <li class="first"><a href="<%=publicUrl%>/bookbattery/manufacturers/Bike-Batteries/Maharashtra/Pune/" title="How to buy">Bike Battery Pune</a></li>
            </ul>
          </div>                   
          <div class="col-md-2 col-sm-6 col-xs-12">
            <h4 class="yellow">Inverters</h4>
            <ul class="links">
            <li class="first"><a href="<%=publicUrl%>/bookbattery/Brand/Inverter/Gujarat/Ahmedabad/" title="How to buy">Inverter Ahmedabad</a></li>
            <li class="first"><a href="<%=publicUrl%>/bookbattery/Brand/Inverter/Karnataka/Bangalore/" title="How to buy">Inverter Bangalore</a></li>
            <li class="first"><a href="<%=publicUrl%>/bookbattery/Brand/Inverter/Tamil-Nadu/Chennai/" title="How to buy">Inverter Chennai</a></li>
            <li class="first"><a href="<%=publicUrl%>/bookbattery/Brand/Inverter/Delhi/New-Delhi/" title="How to buy">Inverter Delhi</a></li>
            <li class="first"><a href="<%=publicUrl%>/bookbattery/Brand/Inverter/Haryana/Gurgaon/" title="How to buy">Inverter Gurgaon</a></li>
            <li class="first"><a href="<%=publicUrl%>/bookbattery/Brand/Inverter/Telangana/Hyderabad/" title="How to buy">Inverter Hyderabad</a></li>
            <li class="first"><a href="<%=publicUrl%>/bookbattery/Brand/Inverter/West-Bengal/Kolkata/" title="How to buy">Inverter Kolkata</a></li>
            <li class="first"><a href="<%=publicUrl%>/bookbattery/Brand/Inverter/Maharashtra/Mumbai/" title="How to buy">Inverter Mumbai</a></li>
            <li class="first"><a href="<%=publicUrl%>/bookbattery/Brand/Inverter/Uttar-Pradesh/Noida/" title="How to buy">Inverter Noida</a></li>
            <li class="first"><a href="<%=publicUrl%>/bookbattery/Brand/Inverter/Maharashtra/Pune/" title="How to buy">Inverter Pune</a></li>
            </ul>
        </div>
        </div>
        <div class="footer-bottom">
          <div class="col-sm-8 col-xs-12 coppyright"> 
              <a href="<%=publicUrl%>/bookbattery/aboutus.jsp" title="How to buy">About us</a> |  
              <a href="<%=publicUrl%>/bookbattery/faq.jsp" title="How to buy">FAQs</a> |  
              <a href="<%=publicUrl%>/bookbattery/payments.jsp" title="How to buy">Payment</a> |  
              <a href="<%=publicUrl%>/bookbattery/shippingpolicy.jsp" title="How to buy">Shipping Policy</a> |  
              <a href="<%=publicUrl%>/bookbattery/privacypolicy.jsp" title="How to buy">Privacy Policy</a> | 
              <a href="<%=publicUrl%>/bookbattery/cancellation.jsp" title="How to buy">Cancellation</a> | 
              <a href="<%=publicUrl%>/bookbattery/contactus.jsp" title="How to buy">Contact Us</a> 
          </div>
        <div class="col-sm-4 col-xs-12 coppyright">Copyright's <i class="fa fa-copyright"></i> 2014 - 2020 BookBattery.com . All rights reserved. </div>
        </div>
        
     </div>
    </div>
    </div>
  </footer>
 </div>