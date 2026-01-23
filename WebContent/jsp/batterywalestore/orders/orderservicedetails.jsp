<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.*,com.ngit.javabean.loglevel.LogLevel,java.util.Properties,java.io.FileInputStream"%>
<%
String strUserid=(String)session.getAttribute("sesStoreOperatorName");
String strStoreId=(String)session.getAttribute("sesstrStoreId");
String strStoreName=(String)session.getAttribute("sesstrStoreName");
if(strUserid==null)
{
	strUserid="";
	session.setAttribute("sesErrorMsg","Session Timed Out. Please login again");
	response.sendRedirect("../../../BookBatterystore/index.html");
	return;
}
Vector alist=(Vector)session.getAttribute("ServicesdetailsVector");

String City ="";
String batterytype = (request.getParameter("batterytype")!=null)?(request.getParameter("batterytype")):"0";
LogLevel.DEBUG(5,new Throwable(),"batterytype :"+batterytype );

String vehiclemake = (request.getParameter("vehiclemake")!=null)?(request.getParameter("vehiclemake")):"0";
LogLevel.DEBUG(5,new Throwable(),"vehiclemake :"+vehiclemake );

String vehiclemodel = (request.getParameter("vehiclemodel")!=null)?(request.getParameter("vehiclemodel")):"0";
LogLevel.DEBUG(5,new Throwable(),"vehiclemodel :"+vehiclemodel );
 
String batterycapty = (request.getParameter("batterycapty")!=null)?(request.getParameter("batterycapty")):"0";
LogLevel.DEBUG(5,new Throwable(),"batterycapty :"+batterycapty );
 
if(City.equals("")){
	
	City = (request.getParameter("pincity")!=null)?(request.getParameter("pincity")):"0";
	LogLevel.DEBUG(5,new Throwable(),"city :"+City );

} else {
	
	City = (request.getParameter("city")!=null)?(request.getParameter("city")):"0";
	LogLevel.DEBUG(5,new Throwable(),"city :"+City );
}

String Pincity = (request.getParameter("pincity")!=null)?(request.getParameter("pincity")):"0";
LogLevel.DEBUG(5,new Throwable(),"strpincity :"+Pincity );

String Area = (request.getParameter("strarea")!=null)?(request.getParameter("strarea")):"0"; 
LogLevel.DEBUG(5,new Throwable(),"strarea :"+Area );

String State = (request.getParameter("strstate")!=null)?(request.getParameter("strstate")):"0"; 
LogLevel.DEBUG(5,new Throwable(),"strstate :"+State );

String Pincode = (request.getParameter("strpincode")!=null)?(request.getParameter("strpincode")):"0"; 
LogLevel.DEBUG(5,new Throwable(),"strpincode :"+Pincode );

%>

<jsp:include page = "../storeheader.jsp" />
<jsp:include page = "../storeleftmenu.jsp" />
 <section class="content">
	<form name="orderservice">
        <div class="container-fluid">
			 <!-- Radio -->
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
						<div class="card">
						<%
						if(alist!=null && alist.size() > 0)
						{
						%>
						<%
						try
						{
							for ( int i=0; i<alist.size() ; i++)
							{
								Hashtable ht=(Hashtable)alist.get(i);
								String service_type=String.valueOf(ht.get("service_type")); 
								LogLevel.DEBUG(5, new Throwable(), "service_type :" + service_type);
								String battery_type=String.valueOf(ht.get("battery_type")); 		
								String store=String.valueOf(ht.get("store")); 		
								String within_5km=String.valueOf(ht.get("within_5km")); 		
								String within_10km=String.valueOf(ht.get("within_10km")); 		
						%>
                        <div class="header">
                            <h2>
                               SERVICE RANGE PRICES
                            </h2> 
                        </div>
                        <div class="body">  
                           <div class="row clearfix">   
							  <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
								<div class="card">
									<div class="header bg-pink">
										<h2 class="card-inside-title"  style="text-align:center;">
											AT STORE
										</h2> 
									</div>
									<div class="body">
									<div class="col-md-offset-2">
									 <% if (service_type=="Health Check" ||  service_type.equals("Health Check")){ %>
										
										 <img align="center" style="width:60%;" src="/bookbattery/dev/includes/images-design/icons/Car_Battery_Health_Up.png" ALT="battery"/>
										
										<%} else if (service_type=="Recharge" ||  service_type.equals("Recharge")){%>	
										
										 <img align="center" style="width:60%;" src="/bookbattery/dev/includes/images-design/icons/Car_Battery_Recharge.png" ALT="battery"/>
										
										<%} else {%> 
										
										 <img align="center" style="width:60%;" src="/bookbattery/dev/includes/images-design/icons/Car_Battery_Jump_Start.png" ALT="battery"/>
										
										<%}%> 
											 <br/>
											 <strong><%=battery_type%>&nbsp;<%=service_type%>&nbsp;</strong> 
											  
 											<table style=" color: #494848;font-size: 13px;">
												<tbody>  
												  <tr>
													  <td>Service Price</td>
													  <td>: <i class="icon-inr" aria-hidden="true"></i> <%=store%> </td>
												  </tr>
												</tbody>
											</table>
											<br/>
										<%
										if((battery_type=="Bike Batteries") || (battery_type.equals("Bike Batteries"))&&(service_type=="Jump Start") || (service_type.equals("Jump Start"))) { %>
										
											 <button type="button" class="btn bg-pink waves-effect" disabled="disabled" onclick="javascript:OrderService('<%=service_type%>','<%=battery_type%>','<%=store%>','Store')">ORDER SERVICE NOW</button>
											
										<% } else { %>
											
											 <button type="button" class="btn bg-pink waves-effect"  onclick="javascript:OrderService('<%=service_type%>','<%=battery_type%>','<%=store%>','Store')">ORDER SERVICE NOW</button>
											
										<%} %>
										 
										
									</div>
									</div>
								</div>
							</div>
							
							<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
								<div class="card">
									<div class="header bg-blue-grey">
										<h2 class="card-inside-title" style="text-align:center;">
											Within 5-KM
										</h2> 
									</div>
									<div class="body">
									<div class="col-md-offset-2">		
										   <% if (service_type=="Health Check" ||  service_type.equals("Health Check")){ %>
										
										 <img align="center" style="width:60%;" src="/bookbattery/dev/includes/images-design/icons/Car_Battery_Health_Up.png" ALT="battery"/>
										
										<%} else if (service_type=="Recharge" ||  service_type.equals("Recharge")){%>	
										
										 <img align="center" style="width:60%;" src="/bookbattery/dev/includes/images-design/icons/Car_Battery_Recharge.png" ALT="battery"/>
										
										<%} else {%> 
										
										 <img align="center" style="width:60%;" src="/bookbattery/dev/includes/images-design/icons/Car_Battery_Jump_Start.png" ALT="battery"/>
										
										<%}%> 
											 <br/>
											 <strong><%=battery_type%>&nbsp;<%=service_type%>&nbsp;</strong> 
											  
 											<table style=" color: #494848;font-size: 13px;">
												<tbody>  
												  <tr>
													  <td>Service Price</td>
													  <td>: <i class="icon-inr" aria-hidden="true"></i> <%=within_5km%> </td>
												  </tr>
												</tbody>
											</table>
											<br/>
										  <button type="button" class="btn bg-blue-grey waves-effect" onclick="javascript:OrderService('<%=service_type%>','<%=battery_type%>','<%=within_5km%>','Within 5 KM')">ORDER SERVICE NOW</button>
									</div>
									</div>
								</div>
							</div>
							
							
							<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
								<div class="card">
									<div class="header bg-teal">
										<h2 class="card-inside-title"  style="text-align:center;">
											Within 10-KM
										</h2> 
									</div>
									<div class="body">
									<div class="col-md-offset-2">
									
										   <% if (service_type=="Health Check" ||  service_type.equals("Health Check")){ %>
										
										 <img align="center" style="width:60%;" src="/bookbattery/dev/includes/images-design/icons/Car_Battery_Health_Up.png" ALT="battery"/>
										
										<%} else if (service_type=="Recharge" ||  service_type.equals("Recharge")){%>	
										
										 <img align="center" style="width:60%;" src="/bookbattery/dev/includes/images-design/icons/Car_Battery_Recharge.png" ALT="battery"/>
										
										<%} else {%> 
										
										 <img align="center" style="width:60%;" src="/bookbattery/dev/includes/images-design/icons/Car_Battery_Jump_Start.png" ALT="battery"/>
										
										<%}%> 
											 <br/>
											 <strong><%=battery_type%>&nbsp;<%=service_type%>&nbsp;</strong> 
											  
 											<table style=" color: #494848;font-size: 13px;">
												<tbody>  
												  <tr>
													  <td>Service Price</td>
													  <td>: <i class="icon-inr" aria-hidden="true"></i> <%=within_10km%> </td>
												  </tr>
												</tbody>
											</table>
											<br/>
										<%
										if((battery_type=="Bike Batteries") || (battery_type.equals("Bike Batteries"))){ %>
										
											<button type="button" class="btn bg-teal waves-effect" disabled="disabled"  onclick="javascript:OrderService('<%=service_type%>','<%=battery_type%>','<%=within_5km%>','Within 10 KM')">ORDER SERVICE NOW</button>
											
										<% } else { %>
											
											<button type="button" class="btn bg-teal waves-effect"  onclick="javascript:OrderService('<%=service_type%>','<%=battery_type%>','<%=within_5km%>','Within 10 KM')">ORDER SERVICE NOW</button>
											
										<%} %>
										 
									</div>
									</div>
								</div>
							</div>
							
							
                            <!--div class="demo-radio-button">
                                <input name="group5" type="radio" id="service_place" value="store" class="with-gap radio-col-pink" checked onclick="Checkprice(this);"/>
                                <label for="radio_30">
									
								</label>
                                <input name="group5" type="radio" id="service_place" value="within_5km"  class="with-gap radio-col-green" onclick="Checkprice(this);"/>
                                <label for="radio_39">
									<h2 class="card-inside-title"  style="margin-top: 4px;">
											Within 5-KM
									</h2>
								</label>
                                <input name="group5" type="radio" id="service_place" value="within_10km" class="with-gap radio-col-orange" onclick="Checkprice(this);"/>
                                <label for="radio_44">
									<h2 class="card-inside-title" style="margin-top: 4px;">
											Within 10-KM
									</h2>
								</label>
                            </div-->
                          </div>
                        </div>
						<%
						}
						}
						catch(Exception e)
						{
								e.printStackTrace();
						}
						%>  <!-----End of Try---->
						
						<%
						}
						else
						{
						%>
						 No Service details found based on selection.! 
						 
						<%
						}
						%>
                    </div>
                </div>
            </div>
            <!-- #END# Radio -->
           
        </div>
    </form>
    </section>
<!------------------Modal---------------->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog">
   
</div>
<div class="modal fade" id="orderModal" tabindex="-1" role="dialog">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="defaultModalLabel">Enter Service Location Details</h4>
                        </div>
                        <div class="modal-body">
                             <form id="form_validation" method="POST">
                              <div class="row clearfix">
									<div class="col-md-6">
										  <div class="form-group form-float">
										   <label class="form-label">Mobile Number<font color="red"> *</font></label>
			                                 <div class="form-line">
			                                    <input type="tel" class="form-control yes" name="order-form-mobile_number" id="order-form-mobile_number" placeholder="9603467559" maxlength="10">
			                                        <div id="order-form-mobile_number-error" style="display:none;"></div>
			                                    </div>
			                                </div>
                                	</div>
                                	<div class="col-md-6">
		                                <div class="form-group form-float">
		                                 <label class="form-label">Name<font color="red"> *</font></label>
		                                    <div class="form-line">
		                                        <input type="text" class="form-control yes name" name="order-form-consumer_name"  id="order-form-consumer_name"  placeholder="Alex">
		                                       
												<div id="order-form-consumer_name-error" style="display:none;"></div>
		                                    </div>
		                                </div>
                                </div>
                                </div>
                                <div class="form-group form-float">
                                   
                                    <label class="form-label">Email<font color="red"> *</font></label>
                                     <div class="form-line">
                                     	    <input type="email" class="form-control yes" name="order-form-emailid"  id="order-form-emailid"  placeholder="alex@gmail.com">
                                        
										<div id="order-form-emailid-error" style="display:none;"></div>
                               		 </div>
                                </div>
                                <div class="form-group form-float"> 
                                     <label class="form-label">Address<font color="red"> *</font></label>
                                      <div class="form-line">
                                        <textarea name="order-form-address" id="order-form-address" cols="30" rows="3" class="form-control no-resize yes" placeholder="2-124,Road No:4,Whitefield,Bangalore"></textarea>
                                       
										<div id="order-form-address-error" style="display:none;"></div>
                                       </div>
                                </div>
                                <div class="row clearfix">
									<div class="col-md-6">
		                                <div class="form-group">
		                                    <div class="form-line">
		                                    <label>Select Ordered By:<font color="red"> *</font></label>
		                                       <select class="form-control show-tick yes" id="order-form-ordered_by" name="order-form-ordered_by">
													<option value="-1">-- Please select --</option>
													<option value="Online Customer">Online Customer</option>
													<option value="Walk-in Customer">Walk-in Customer</option>
												</select>
												<div id="order-form-ordered_by-error" style="display:none;"></div>
		                                    </div>
		                                </div> 
                               		 </div> 
                               		 <div class="col-md-6">
		                                <div class="form-group">
		                                    <div class="form-line">
		                                    <label>Select Order Type:<font color="red"> *</font></label>
		                                       <select class="form-control show-tick yes" id="order-form-order_type" name="order-form-order_type">
													<option value="-1">-- Please select --</option>
													<option value="Free Order">Free Order</option>
													<option value="Paid Order">Paid Order</option>
												</select>
												<div id="order-form-order_type-error" style="display:none;"></div>
		                                    </div>
		                                </div> 
                               		 </div> 
                                </div>
                               <div class="row clearfix">
									<div class="col-md-6">
										  <div class="form-group">
											<div class="form-line">
											<label>Select Payment Mode:<font color="red"> *</font></label>
											   <select class="form-control show-tick yes" id="order-form-payment_mode" name="order-form-payment_mode">
													<option value="-1">-- Please select --</option>
													<option value="Cash On Delivery">Cash On Delivery</option>
													<option value="Store UPI PhonePe">Store UPI PhonePe</option> 
													<option value="Online Payment After Fitment">Online Payment After Fitment</option> 
													<option value="Card On Delivery" disabled>Card On Delivery</option> 
												</select>
												<div id="order-form-payment_mode-error" style="display:none;"></div>
											</div>
										</div> 
									</div>  
									<div class="col-md-6">
										<div class="input-group spinner" data-trigger="spinner">
											<div class="form-line">
											<label class="form-label">Quantity:</label>
												<input type="text" class="form-control text-center yes" id="order-form-quantity" name="order-form-quantity" value="1" data-rule="quantity" readonly>
											</div>
											<span class="input-group-addon">
												<a href="javascript:;" class="spin-up" data-spin="up"><i class="glyphicon glyphicon-chevron-up"></i></a>
												<a href="javascript:;" class="spin-down" data-spin="down"><i class="glyphicon glyphicon-chevron-down"></i></a>
											</span>
										</div>
									</div> 
                                </div>  
                                <div class="row clearfix">
									<div class="col-md-6">
										<div class="form-group">
										<label class="form-label">Enter Reference Code</label>
											<div class="form-line">
											
												<input type="text" class="form-control yes name" name="order-form-referrence_code"  id="order-form-referrence_code">
												
												<div id="order-form-referrence_code-error" style="display:none;"></div>
											</div>
										</div>
									</div>
									<div class="col-md-6" id="pincode_div">
									<label class="form-label">Zipcode<font color="red"> *</font></label>
										 <div class="form-group">
											<div class="form-line">
											
												 <input type="text" class="form-control yes" name="order-form-zipcode"  id="order-form-zipcode"> 
												<div id="order-form-zipcode-error" style="display:none;"></div>
											</div>
										</div> 
									</div>
								</div> 
                                <div class="row clearfix" id="cityarea_div" style="display:none;">
									<div class="col-md-6">
										<label class="form-label">Area<font color="red"> *</font></label>
										<div class="form-group">
											<div class="form-line">
												 <input type="text" class="form-control yes" name="order-form-area"  id="order-form-area" disabled> 
												<div id="order-form-area-error" style="display:none;"></div>
											</div>
										</div>
									</div>
									<div class="col-md-6" >
									<label class="form-label">City<font color="red"> *</font></label>
										 <div class="form-group">
											<div class="form-line">
												 <input type="text" class="form-control yes" name="order-form-city"  id="order-form-city" disabled>
												
												<div id="order-form-city-error" style="display:none;"></div>
											</div>
										</div> 
									</div>
								</div> 
								    						
                            </form>
                         </div>
                        <div class="modal-footer">
                            <button type="button" class="btn bg-deep-orange waves-effect" onclick="FuntoOrderService()">ORDER SERVICE</button>
                            <button type="button" class="btn bg-indigo waves-effect" data-dismiss="modal">CLOSE</button>
                        </div>
                    </div>
                </div>
            </div>
<!------------------Modal---------------->
<input type="hidden" name="batterycapacity" id="batterycapacity" value="<%=batterycapty%>">
<input type="hidden" name="batterytype" id="batterytype" value="<%=batterytype%>">
<input type="hidden" name="vehiclemake" id="vehiclemake" value="<%=vehiclemake%>">
<input type="hidden" name="vehiclemodel" id="vehiclemodel" value="<%=vehiclemodel%>">
<input type="hidden" name="service_range" id="service_range" value="">
<input type="hidden" name="service_price" id="service_price" value="">
<input type="hidden" name="service_type" id="service_type" value="">
<input type="hidden" name="strarea" id="strarea" value="<%=Area%>">
<input type="hidden" name="strcity" id="strcity" value="<%=City%>">
<input type="hidden" name="strstate" id="strstate" value="<%=State%>">
<input type="hidden" name="strpincode" id="strpincode" value="<%=Pincode%>">
<input type="hidden" name="store_id" id="store_id" value="<%=strStoreId%>">
<input type="hidden" name="store_name" id="store_name" value="<%=strStoreName%>">
 

<!-- Jquery Spinner Plugin Js -->
<script src="/bookbattery/css/BookBatterystore/plugins/jquery-spinner/js/jquery.spinner.js"></script>
<jsp:include page = "../storefooter.jsp" />		

<script type="text/javascript">
function OrderService(servicetype, batterytype, price, servicerange){
	 
	   $("#orderModal").modal({show: true, backdrop: 'static', keyboard: false});
	  var pincode = $('#strpincode').val();
	  var state = $('#strstate').val();
	  var city = $('#strcity').val();
	  var area = $('#strarea').val();
	  
	  document.getElementById("service_type").value = servicetype;
	  document.getElementById("service_range").value = servicerange;
	  document.getElementById("service_price").value = price;
	  if(batterytype=="Inverter Batteries"){
		   
		   $("#quantity_div").show();
		  
	  } else {
		  
		  $("#quantity_div").hide();
	  }
	  if(pincode=="")	  {
		  
		  $("#cityarea_div").show();
		  $("#pincode_div").hide();
		  document.getElementById("order-form-city").value = city;
		  document.getElementById("order-form-area").value = area;
		  
	  } else {
		  
		  $("#cityarea_div").hide();
		  $("#pincode_div").show();
		  document.getElementById("order-form-zipcode").value = pincode;
	  }
 } 


 function FuntoOrderService(){
	 
	  var input_type;
	  var orderform_mobile_number = $('#order-form-mobile_number').val();
	  //alert(orderform_mobile_number); 
	  if (mobilenumberValidation("","order-form-mobile_number","mobilenumber") == false)
		{
			return;
			
		} 
	  var consumer_name = $('#order-form-consumer_name').val();
	  //alert(consumer_name); 
	  if (nameValidation("","order-form-consumer_name","name") == false)
		{
			return;
			
		} 
		
	  var consumer_emailid = $('#order-form-emailid').val();
	  //alert(consumer_emailid);
	  if (emailValidation("","order-form-emailid","email") == false)
		{
			return;
			
		} 
		
	  var consumer_address = $('#order-form-address').val();
	  //alert(consumer_address);
	  if (addressValidation("","order-form-address","address") == false)
		{
			return;
			
		}
	  var ordered_by = $('#order-form-ordered_by').val();
	   //alert(ordered_by);
	   if (selectValidation("","order-form-ordered_by","select") == false)
		{
			return;
			
		} 
	   var order_type = $('#order-form-order_type').val();
	   //alert(order_type);
	   if (selectValidation("","order-form-order_type","select") == false)
		{
			return;
			
		} 
	  var payment_mode = $('#order-form-payment_mode').val();
	   //alert(payment_mode);
	  if (selectValidation("","order-form-payment_mode","select") == false)
		{
			return;
			
		}
	  var batterytype = $('#batterytype').val();
	  if(batterytype=="Inverter Batteries"){
		  
		  var quantity = $('#order-form-quantity').val();
		  //alert(quantity);
		  if (yesValidation("","order-form-quantity","yes") == false)
			{
				return;
				
			}
			
 		  } else {
			  
			  var quantity ="";
			  
		  }
	  var referrence_code = $('#order-form-referrence_code').val();
	  var zipcode = $('#strpincode').val();
	  var area = $('#strarea').val();
	  var city = $('#strcity').val();
	  var state = $('#strstate').val();
	  var batterycapacity = $('#batterycapacity').val();
	  var vehiclemake = $('#vehiclemake').val();
	  var vehiclemodel = $('#vehiclemodel').val();
	  var service_price = $('#service_price').val();
	  var service_range = $('#service_range').val();
	  var service_type = $('#service_type').val();
	 // alert(service_type);
	  var store_id = $('#store_id').val();
	  var store_name = $('#store_name').val();
		 
	 var More_info="";
	 var current_order_details ="";
	  $.ajax({
			type: "GET",
			url: "/bookbattery/servlet/BatterystoreServiceDetails?hidWhatToDo=insertserviceorderdetails",
			data: {mobile_number: orderform_mobile_number, consumer_name: consumer_name, consumer_emailid: consumer_emailid, consumer_address: consumer_address, ordered_by: ordered_by, order_type: order_type, payment_mode: payment_mode, quantity: quantity, zipcode: zipcode, area: area, city: city, state: state, batterytype: batterytype, vehiclemake: vehiclemake, vehiclemodel: vehiclemodel, batterycapacity: batterycapacity, service_price: service_price, service_range: service_range, service_type: service_type, referrence_code: referrence_code, store_id: store_id, store_name: store_name },
			success: function(data){
				$("#orderModal").modal('hide');
				 //alert(data);
				 //current_order_details= current_order_details.replace(" . ", "</br>");
				var OrderLable="";
				var OrderMessage="";
				if(data.indexOf("Sucessfully")>=0)
				{
					$("#exampleModal").html("");
					Response = data.split('|');
					OrderLable="Successfully Ordered - BookBattery";
					OrderMessage="<div style='text-align: center;'><i class='icon-smile' style='font-size: 60px;color: #1d8e11;'></i><p style='font-size: 14px;font-weight: 600;'>Thanks for Ordering with BookBattery</p></div><div><div class='center-col col-md-12 col-sm-12 col-xs-12 nopadding-xs '><h4>Your Order Details</h4><table style=' color: #131212;font-size: 13px;'  class='table table-condensed'> <tbody> <tr> <th>Order Number&nbsp;:</th> <td>"+Response[1]+" </td> </tr><tr> <th>Vehicle Details&nbsp;:</th> <td>"+vehiclemake+", "+vehiclemodel+"</td> </tr><tr> <th>Price Details&nbsp;:</th> <td>Service Price - Rs. "+service_price+"</td> </tr><tr> <th>Service&nbsp;Type:</th> <td> "+service_type+" at/with in "+service_range+"</td> </tr></tbody></table></div>"+More_info+"<hr size='100'><div style='text-align: center;'><p>Please Check your Email or Mobile for More Information regarding Your Order.<br /> Our Team will Call you soon for Order Confirmation.</p></div></div>";
				}
				else if (data.indexOf("No Retailers Found")>=0)
				{
					OrderLable="Failed to Order - BookBattery";
					OrderMessage="<div style='text-align: center;'><i class='icon-frown' style='font-size: 60px;color: #de3315;'></i><h4>We are Sorry, No Retailers Found on Selected City or Area!! </h4><p style='font-size: 17px;'>Please Contact our Support Team at <br/><b>+91 96034 67559</b></p></div></br>";
					
				}else
				{
					OrderLable="Failed to Order - BookBattery";
					OrderMessage="<div style='text-align: center;'><i class='icon-frown' style='font-size: 60px;color: #de3315;'></i><h4>We are Sorry,Some thing went wrong !! </h4><p style='font-size: 17px;'>Please try to reorder or Contact our Support Team at <br/><b>+91 96034 67559</b></p></div></br>";
					
				}
				
				var AfterOrder_PopUp="<a class='btn hide' data-toggle='modal' data-target='.after_order_popup' data-toggle='modal' data-backdrop='static' data-keyboard='false' id='after_order_popup_btn'></a><div class='modal fade after_order_popup' tabindex='-1' role='dialog' aria-labelledby='after_order_popup_label'> <div class='modal-dialog' role='document'> 	<div class='modal-content'> <div class='modal-header bg-orange'><h4 class='modal-title' id='after_order_popup_label'>"+OrderLable+"</h4><button type='button' onclick='okey_done()' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'> x</span></button> 	</div> <div class='modal-body'> 	<div class='form-group'> <div id='after_order_popup_data'> "+OrderMessage+"</div> <div style='text-align: center;'> 	<button type='button' class='btn btn-light-bue' onclick='okey_done()'>Okay</button> </div> 	</div> </div> 	</div> </div> </div>";
				
				$("#exampleModal").modal({show: true, backdrop: 'static', keyboard: false});
				$("#exampleModal").html(AfterOrder_PopUp);
				$("#after_order_popup_btn").click();
				}
		});	 
 }
 
 
function okey_done(){
	
	 location.replace("/bookbattery/jsp/BookBatterystore/storehome.jsp");
}
</script>


				