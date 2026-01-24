<!--
    Document   : orderbatterydetails
    Created on : Oct 19, 2019, 4:22:12 PM
    Author     : C Prasanna Kumari.
-->

<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.*,com.ngit.javabean.loglevel.LogLevel,javax.servlet.ServletContext,java.util.Properties,java.io.FileInputStream"%>
<%response.setHeader("Pragma","no-cache"); response.setHeader("Cache-Control","no-store"); response.setHeader("Expires","0"); response.setDateHeader("Expires",-1); %>

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
%>

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
FileInputStream product_details_file = new FileInputStream("/home/ngit/tomcat/webapps/bookbattery/jsp/BookBatterystore/orders/product_details.html"); 
product_details.load(product_details_file); 
product_details_file.close();
 
%> 
<%
Vector Vector_Get_Similar_Inverter=(Vector)request.getAttribute("Vector_Get_Similar_Inverter");

String Inverter_Type = "Inverter";
String Inverter_Brand = (String) request.getAttribute("Inverter_Brand");
String Inverter_Model = (String) request.getAttribute("Inverter_Model");
String Inverter_Capacity = (String) request.getAttribute("Inverter_Capacity");
String Inverter_Warranty = (String) request.getAttribute("Inverter_Warranty");
String Inverter_Image = (String) request.getAttribute("Inverter_Image");
String Inverter_Description = (String) request.getAttribute("Inverter_Description");
String Inverter_Actual_Price = (String) request.getAttribute("Inverter_Actual_Price");
String Inverter_Discount_Price = (String) request.getAttribute("Inverter_Discount_Price");
String Inverter_Computer = (String) request.getAttribute("Computer");
String Inverter_Fans = (String) request.getAttribute("Fans");
String Inverter_Tubelights = (String) request.getAttribute("Tubelights");
String Inverter_Television = (String) request.getAttribute("Television");
String Inverter_ERP_Price = (String) request.getAttribute("Inverter_ERP_Price");
String Battery_Flag = (String) request.getAttribute("Battery_Flag");
String Inverter_Stock = (String) request.getAttribute("Inverter_Stock");
String State = (String) request.getAttribute("State");
String City = (String) request.getAttribute("City");
String Area = (String) request.getAttribute("Area");
String Pincode = (String) request.getAttribute("Pincode");

String State_URL= State.replaceAll(" ", "-");
String City_URL= City.replaceAll(" ", "-");
String Inverter_Brand_URL= Inverter_Brand.replaceAll(" ", "-");
String Inverter_Model_URL= Inverter_Model.replaceAll(" ", "-");


String Inverter_Model_Cat=Inverter_Model;
Inverter_Model_Cat= Inverter_Model_Cat.replaceAll(" ", "-");

int Inverter_Actual_Price_int = Integer.parseInt(Inverter_Actual_Price);
int Inverter_Discount_Price_int = Integer.parseInt(Inverter_Discount_Price);
int Save_Price = Inverter_Actual_Price_int-Inverter_Discount_Price_int;

Inverter_Computer=Inverter_Computer.replaceAll(",", "</td><td>");
Inverter_Fans=Inverter_Fans.replaceAll(",", "</td><td>");
Inverter_Tubelights=Inverter_Tubelights.replaceAll(",", "</td><td>");
Inverter_Television=Inverter_Television.replaceAll(",", "</td><td>");

int Inverter_Discount_Percent=(((Inverter_Actual_Price_int-Inverter_Discount_Price_int)*100)/Inverter_Actual_Price_int);

LogLevel.DEBUG(5,new Throwable(),"Inverter_Actual_Price_int :"+Inverter_Actual_Price_int );
LogLevel.DEBUG(5,new Throwable(),"Inverter_Discount_Price_int :"+Inverter_Discount_Price_int );
%>
<jsp:include page = "../storeheader.jsp" />
<jsp:include page = "../storeleftmenu.jsp" />
<!-- Bootstrap Spinner Css -->
<link href="/bookbattery/css/BookBatterystore/plugins/jquery-spinner/css/bootstrap-spinner.css" rel="stylesheet">


<section class="content">
      <form name="viewproductdetails" method="post">
		<div class="container-fluid">
             

            <!-- CKEditor -->
            <div class="row clearfix">
                <div class="col-md-12">
                    <div class="card">
                        <div class="header">
                            <h2>
                                VIEW DETAILS 
                            </h2>
							     
                        </div>
							<div class="body">
								<div class="row">
									 <div class="col-sm-4 col-xs-12">
											<img alt="<%=Inverter_Brand%> <%=Inverter_Model%>" itemprop="image" src="<%=Inverter_Image%>" style="width: 100%;"> 
									 </div>
									 <div class="col-sm-5 col-xs-12">
										<h4 itemprop="name"><%=Inverter_Brand%>  <%=Inverter_Model%></h4>
										<div class="short-description">
										<h5><strong><u>Quick Overview</u></strong></h5>
										<table style=" color: #494848;">
										 <colgroup> <col class="col-xs-5"> <col class="col-xs-7"> </colgroup>
											  <tr>
												  <th>Product Brand </th>
												  <td itemprop="brand">: <%=Inverter_Brand%></td>
											  </tr>
											  <tr>
												  <th>Model Number</th>
												  <td itemprop="mpn">: <%=Inverter_Model%></td>
											  </tr>
											  <tr>
												 
												  <th>Product Capacity</th>
												  <td>: <%=Inverter_Capacity%></td>
											  </tr>
												
											  <tr>
												  <th>Product Type</th>
												  <td>: <%=Inverter_Type%></td>
											  </tr>
											  <tr>
												  <th>Product Warranty</th>
												  <td>: <%=Inverter_Warranty%></td>
											  </tr>
										  </table>
									  </div>
									  
									  <p class="availability">Availability : 
				    
										 <%
										  if(Inverter_Stock.equals("No") || Inverter_Stock.equals("no"))
											{
										%>	
										
											<span class="label label-danger">Out of Stock</span>
										<%
											}
											else
											{							
										%>
											
											<span class="label label-success">In Stock</span>
										<%
											}						
										%>
									  </p>
									  <div class="table-responsive">
											<table class="table">
												<thead>
													<tr>
														<th>Regular Price</th><th>: <strike><%=Inverter_Actual_Price%></strike></th> 
													</tr>
												</thead>
												<tbody>
													<div class="demo-radio-button">
														<tr>
															<th> <label for="radio_1">Discount Price</th><th>: <%=Inverter_Discount_Price_int%></th> 
														</tr> 
													</div> 
												</tbody>
											</table>
										</div>
										 <%
										  if(Inverter_Stock.equals("No") || Inverter_Stock.equals("no"))
											{
										%>	
										
											
										<%
											}
											else
											{							
										%>
											
											<div class="col-sm-6 col-xs-12"> 
											 <button type="button" class="btn bg-orange waves-effect" onclick="javascript:negotitateprice('<%=Inverter_Brand%>','<%=Inverter_Model%>','<%=Inverter_Capacity%>','<%=Inverter_Actual_Price%>','<%=Inverter_Discount_Price_int%>')">
												<i class="material-icons">shuffle</i>
												<span>NEGOTIATE PRICE</span>
											</button>
										</div>
										<div class="col-sm-6 col-xs-12"> 
											<button type="button" class="btn bg-deep-purple waves-effect" onclick="Invertercosumerdetails('<%=Inverter_Brand%>','<%=Inverter_Model%>','<%=Inverter_Actual_Price%>','<%=Inverter_Discount_Price_int%>')">
												<i class="material-icons">done_all</i>
												<span>ORDER NOW</span>
											</button>
										</div>
										<%
											}						
										%>
										
										<div id="inverterdiscountdetails"></div>
										
									 </div>
									
								</div>
							</div>
                        </div>
                   </div>
				</div>
			 
            <!-- #END# CKEditor -->
            
        </div>
    </form>
    </section>
<!-- Small Size -->
<div class="modal fade" id="smallModal" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-sm" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" id="smallModalLabel">Negotitate Price</h4>
			</div>
			<div id="discount_div"></div>
		</div>
	</div>
</div> 	
<!-- Small Size -->
	<!-- Default Size -->
<div class="modal fade" id="orderModal" tabindex="-1" role="dialog">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="defaultModalLabel">Enter Customer/Installation Location Details</h4>
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
									<div class="col-md-12">
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
                            <button type="button" class="btn bg-deep-orange waves-effect" onclick="FuntoOrderInverter()">ORDER Inverter</button>
                            <button type="button" class="btn bg-indigo waves-effect" data-dismiss="modal">CLOSE</button>
                        </div>
                    </div>
                </div>
            </div>
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog">
           
</div>			
<input type="hidden" name="invertertype" id="invertertype" value="<%=Inverter_Type%>">
<input type="hidden" name="invertermodel" id="invertermodel" value="<%=Inverter_Model%>">
<input type="hidden" name="inverterbrand" id="inverterbrand" value="<%=Inverter_Brand%>">
<input type="hidden" name="invertercapacity" id="invertercapacity" value="<%=Inverter_Capacity%>">
<input type="hidden" name="invertermrpprice" id="invertermrpprice" value="<%=Inverter_Actual_Price%>">
<input type="hidden" name="inverterdiscountprice" id="inverterdiscountprice" value="<%=Inverter_Discount_Price%>">
<input type="hidden" name="invertererpprice" id="invertererpprice" value="<%=Inverter_ERP_Price%>">
<input type="hidden" name="strcity" id='strcity' value="<%=City%>">
<input type="hidden" name="strarea" id="strarea" value="<%=Area%>">
<input type="hidden" name="strstate" id="strstate" value="<%=State%>">
<input type="hidden" name="strpincode" id="strpincode" value="<%=Pincode%>">
<input type="hidden" name="strEmail" id="strEmail" value="">
<input type="hidden" name="store_id" id="store_id" value="<%=strStoreId%>">
<input type="hidden" name="store_name" id="store_name" value="<%=strStoreName%>">
<input type="hidden" name="neg_price" id="neg_price" value="">
<input type="hidden" name="serverURL" id='serverURL' value="<%=serverURL%>">
<!-- Jquery Spinner Plugin Js -->
<script src="/bookbattery/css/BookBatterystore/plugins/jquery-spinner/js/jquery.spinner.js"></script>
<jsp:include page = "../storefooter.jsp" />
	
<script type="text/javascript">
 function negotitateprice(inverterbrand,invertermodel,invertercapacity,actualprice,consumerprice){
	 
	  document.getElementById("discount_div").innerHTML = "<div class='modal-body'><label for='email_address'>Enter Percentage to discount price.<font color='red'>*</font></label><div class='form-group'><div class='form-line'><input type='text' id='discountprice' class='form-control' placeholder='1% as 0.01'></div><small>&nbsp;Note&nbsp;please&nbsp;enter&nbsp;1%&nbsp;as&nbsp;0.01.</small></div><button type='button' class='btn bg-green waves-effect' onclick=\"javascript:getdiscountprices('"+inverterbrand+"','"+invertermodel+"','"+invertercapacity+"','"+actualprice+"','"+consumerprice+"')\">UPDATE PRICES</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type='button' class='btn bg-blue waves-effect' data-dismiss='modal'>CLOSE</button></div>";
	  $("#smallModal").modal({show: true, backdrop: 'static', keyboard: false});
	  
 }
 
 function getdiscountprices(inverterbrand,invertermodel,invertercapacity,actualprice,consumerprice){
	 
	/* alert(inverterbrand);
	 alert(invertermodel);
	 alert(invertercapacity);
	 alert(actualprice);
	 alert(consumerprice);*/
	 var discountprice=$("#discountprice").val();
	 // alert(discountprice);
	 $.ajax({
			type: "GET",
			url: "/bookbattery/servlet/BatterystoreInverterDetails?hidWhatToDo=calculatepercentage",
			data: {inverterbrand: inverterbrand, invertermodel: invertermodel, invertercapacity: invertercapacity, actualprice: actualprice, consumerprice: consumerprice, discountprice: discountprice },
			success: function(data){
				$("#smallModal").modal('hide');
				//alert(data);
				 document.getElementById("inverterdiscountdetails").innerHTML = data;
			}
		});	  
 }
 
 function Invertercosumerdetails(inverterbrand,invertermodel,actualprice,consumerprice){
	 
	 $("#orderModal").modal({show: true, backdrop: 'static', keyboard: false});
	  var pincode = $('#strpincode').val();
	  var state = $('#strstate').val();
	  var city = $('#strcity').val();
	  var area = $('#strarea').val();
	  /*alert(pincode);
	  alert(state);
	  alert(city);
	  alert(area);*/
	  document.getElementById("neg_price").value = consumerprice;
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
 
function FuntoOrderInverter(){
	 
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
	var payment_mode = $('#order-form-payment_mode').val();
	//alert(payment_mode);
	if (selectValidation("","order-form-payment_mode","select") == false)
	{
		return;
		
	}
	var quantity = $('#order-form-quantity').val();
	//alert(quantity);
	if (yesValidation("","order-form-quantity","yes") == false)
	{
		return;
		
	}
	var zipcode = $('#strpincode').val();
	var area = $('#strarea').val();
	//alert(area);
	var city = $('#strcity').val();
	//alert(city);
	var state = $('#strstate').val();
	//alert(state);
	var invertertype = $('#invertertype').val();
	var invertermodel = $('#invertermodel').val();
	var inverterbrand = $('#inverterbrand').val();
	var invertercapacity = $('#invertercapacity').val();
	var invertermrpprice = $('#invertermrpprice').val();
	var inverterdiscountprice = $('#inverterdiscountprice').val();
	var invertererpprice = $('#invertererpprice').val();
	var store_id = $('#store_id').val();
	var store_name = $('#store_name').val();
	var neg_price = $('#neg_price').val();
	//alert(invertererpprice);
	var referrence_code = $('#order-form-referrence_code').val(); 
	 var More_info="";
	 var current_order_details ="";
	  $.ajax({
			type: "GET",
			url: "/bookbattery/servlet/BatterystoreInverterDetails?hidWhatToDo=insertorderdetails",
			data: {mobile_number: orderform_mobile_number, consumer_name: consumer_name, consumer_emailid: consumer_emailid,consumer_address: consumer_address, ordered_by: ordered_by, payment_mode: payment_mode, quantity: quantity, zipcode: zipcode, area: area, city: city, state: state, invertertype: invertertype, invertermodel: invertermodel, inverterbrand: inverterbrand, invertercapacity: invertercapacity, invertermrpprice: invertermrpprice, inverterdiscountprice: neg_price, invertererpprice: invertererpprice, referrence_code: referrence_code, store_id: store_id, store_name: store_name },
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
					OrderMessage="<div style='text-align: center;'><i class='icon-smile' style='font-size: 60px;color: #1d8e11;'></i><p style='font-size: 14px;font-weight: 600;'>Thanks for Ordering with BookBattery</p></div><div><div class='center-col col-md-12 col-sm-12 col-xs-12 nopadding-xs '><h4>Your Order Details</h4><table style=' color: #131212;font-size: 13px;'  class='table table-condensed'> <tbody> <tr> <th>Order Number&nbsp;:</th> <td>"+Response[1]+" </td> </tr><tr> <th>Inverter Model&nbsp;:</th> <td>"+invertermodel+"</td> </tr> <tr> <th>Inverter Brand&nbsp;:</th> <td>"+inverterbrand+"</td> </tr> <tr> <th>Price Details&nbsp;:</th> <td>Discount Price - Rs. "+neg_price+"</td> </tr></tbody></table></div>"+More_info+"<hr size='100'><div style='text-align: center;'><p>Please Check your Email or Mobile for More Information regarding Your Order.<br /> Our Team will Call you soon for Order Confirmation.</p></div></div>";
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
				
				var AfterOrder_PopUp="<a class='btn hide' data-toggle='modal' data-target='.after_order_popup' data-toggle='modal' data-backdrop='static' data-keyboard='false' id='after_order_popup_btn'></a><div class='modal fade after_order_popup' tabindex='-1' role='dialog' aria-labelledby='after_order_popup_label'> <div class='modal-dialog' role='document'> 	<div class='modal-content'> <div class='modal-header'><h4 class='modal-title' id='after_order_popup_label'>"+OrderLable+"</h4><button type='button' onclick='okey_done()' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'> x</span></button> 	</div> <div class='modal-body'> 	<div class='form-group'> <div id='after_order_popup_data'> "+OrderMessage+"</div> <div style='text-align: center;'> 	<button type='button' class='btn btn-secondary' onclick='okey_done()'>Okay</button> </div> 	</div> </div> 	</div> </div> </div>";
				
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

