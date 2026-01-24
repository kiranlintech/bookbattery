//### Name 		 : product_details_function.js
//### Author	 : BookBattery
//### Description: This contains Product Order Js and Jquerry Code, Order Form, Valadation.

var order_usermobile_number;
var order_usermobile_otp;
var order_usermobile_verify;
var order_username;
var order_useremail;
var order_user_address;
var order_user_area;
var order_user_landmark;
var order_user_pincode;
var order_vehiclemake;
var order_vehiclemodel;
var showdelivery;
var order_number;
var order_product_qty;
var order_battery_buy_type;
var order_product_payment_mode;
var order_product_type=$("#product_type_page").val();
var order_product_brand=$("#Product_Brand").val();
var order_product_model=$("#Product_Model").val();
//alert(order_product_brand);
var order_with_old_battery=$("#Price_With_Battery").val();
var order_without_old_battery=$("#Price_Without_Battery").val();


//########### Inverter and Battery Combo
var order_inverter_battery_model=$("#Inverter_Battery_Model").val();

//alert(order_inverter_battery_model);

var order_inverter_battery_capacity=$("#Inverter_Battery_Capacity").val();
var order_inverter_battery_brand=$("#Product_Battery_Brand").val();

//alert(order_inverter_battery_brand);


// Inverter Values
var order_inverter_discount_price=$("#Inverter_Discount_Price").val();

var order_state =$("#product_state_page").val();
var order_city =$("#product_city_page").val();

var current_order_details="";
var current_order_details_after_order_popup="";

var order_battery_fit;

function UpdateProduct_Filter()
{

	//alert("inside");
	var publicUrl =$("#publicUrl").val();
	var baseurldirectory =$("#baseurldirectory").val();
	var Product_Brand =$("#Product_Brand").val();
	var Product_Model =$("#Product_Model").val();
	var product_state =$("#product_state").val();
	var product_city =$("#product_city").val();
	var product_type_page=$("#product_type_page").val();
	var ctime=365*24*60*60*1000;
	
	//alert(66);
	
	if (selectValidation("","product_state","select") == false)
	{
		return;
	}
	else
	{
		setCookie("product_state_cookie", product_state.replace(/ /g, "-"),ctime)
	}	
	if (selectValidation("","product_city","select") == false)
	{
		return;
	}
	else
	{
		setCookie("product_city_cookie", product_city.replace(/ /g, "-"),ctime)
	}
	
	Product_Brand= Product_Brand.replace(/ /g, "-");
	Product_Model= Product_Model.replace(/ /g, "+");
	product_state= product_state.replace(/ /g, "-");
	product_city= product_city.replace(/ /g, "-");
	
	if(product_type_page=="Inverter")
	{
		window.location.href = publicUrl+baseurldirectory+'product-inverters/'+Product_Brand+'/'+Product_Model+'/'+product_state+'/'+product_city+'/';
	}
	else if(product_type_page=="Inverter and Battery Combo")
	{ 
		
		var Inverter_Battery_Model=$("#Inverter_Battery_Model").val();
		var Inverter_Battery_Capacity=$("#Inverter_Battery_Capacity").val();
		
		Inverter_Battery_Model= Inverter_Battery_Model.replace(/ /g, "+");
		Inverter_Battery_Capacity= Inverter_Battery_Capacity.replace(/ /g, "+");
		
		window.location.href = publicUrl+baseurldirectory+'product-inverter-battery-combo/'+Product_Brand+'/'+Product_Model+'/'+Inverter_Battery_Model+'/'+Inverter_Battery_Capacity+'/'+product_state+'/'+product_city+'/';
	}
	else{
		window.location.href = publicUrl+baseurldirectory+'products/'+Product_Brand+'/'+Product_Model+'/'+product_state+'/'+product_city+'/';
	}
}


$(document).ready(function() {
	
	var product_type_page=$("#product_type_page").val();
	
	showdelivery = false;
	
		
	if(product_type_page=="Bike Batteries")
	{
		var product_city =$("#product_city_page").val();
		var delivery_cities =$("#delivery_cities").val();
		//alert(product_type_page);
		
		
		var citiiesarray = delivery_cities.split(",");
		//alert(citiiesarray[0]);
		//alert(citiiesarray[1]);
		
		for (var i = 0; i < citiiesarray.length && !showdelivery; i++) {
			//alert(citiiesarray[i]);
			//alert(product_city);
			var fetchedcity=citiiesarray[i];
			product_city=product_city.trim();
			fetchedcity=fetchedcity.trim();
		  if (fetchedcity == product_city) {
			  //alert("inside");
			showdelivery = true;
			break;
		  }
		}
	}
	else
	{}
	
var Order_Form_pop_up="\
<a data-toggle='modal' data-target='#battery-order-form' id='battery-order-form-btn' type='hidden'  class='btn hide' data-toggle='modal' data-backdrop='static' data-keyboard='false'  ></a>\
<div class='modal fade' id='battery-order-form' role='dialog' aria-labelledby='battery-order-formLable'>\
	  <div class='modal-dialog' role='document'>\
		<div class='modal-content'>\
		  <div class='modal-header'>\
			<button type='button' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'>&times;</span></button>\
			<h4 class='modal-title' id='battery-order-formLable'>Order Form - BookBattery</h4>\
		  </div>\
		  <div class='modal-body'>\
			<div class='stepwizard'>\
				<div class='stepwizard-row setup-panel'>\
					<div class='stepwizard-step'>\
						<a href='#step-1' type='button' class='btn btn-primary btn-circle' id='order-form-nav-1'>1</a>\
						<p>Personal Details</p>\
					</div>\
					<div class='stepwizard-step'>\
						<a href='#step-2' type='button' class='btn btn-default btn-circle' disabled='disabled' id='order-form-nav-2'>2</a>\
						<p>Location</p>\
					</div>";

					if(product_type_page=="Bike Batteries" && showdelivery==true)
					{
						//alert("inside");
						Order_Form_pop_up=Order_Form_pop_up+"<div class='stepwizard-step'>\
						<a href='#step-3' type='button' class='btn btn-default btn-circle' disabled='disabled' id='order-form-nav-3'>3</a>\
						<p>Home Delivery Option </p>\
						</div>\<div class='stepwizard-step'>\
						<a href='#step-4' type='button' class='btn btn-default btn-circle' disabled='disabled' id='order-form-nav-4'>4</a>\
						<p>Payment Mode</p>\</div><div class='stepwizard-step'>\
						<a href='#step-5' type='button' class='btn btn-default btn-circle' disabled='disabled' id='order-form-nav-5'>5</a>\
						<p>Order Details</p>\</div>";
					}
					else
					{  
						Order_Form_pop_up=Order_Form_pop_up+"<div class='stepwizard-step'>\
						<a href='#step-3' type='button' class='btn btn-default btn-circle' disabled='disabled' id='order-form-nav-3'>3</a>\<p>Payment Mode</p>\</div>\
						<div class='stepwizard-step'>\
						<a href='#step-4' type='button' class='btn btn-default btn-circle' disabled='disabled' id='order-form-nav-4'>4</a>\
						<p>Order Details</p>\</div>";
					}
					

	Order_Form_pop_up=Order_Form_pop_up+"</div>\</div>\
			<form role='form'>\
				<div class='row setup-content' id='step-1'>\
					<div class='col-xs-12'>\
						<div class='col-md-12'>\
							<h4 class='hide'> Personal Details</h4>\
							  <div class='form-group'>\
								<label for='order-form-usermobile-number'>Please Enter Your Mobile Number :</label>\
								<input type='tel' class='form-control yes' id='order-form-usermobile-number' placeholder='9913XXXXXX' maxlength='10' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:SendSmsConfirmation(); return false; } else return true;\">\
								<div id='order-form-usermobile-number-error'style='display:none;'></div>\
								<small id='order-form-usermobile-number-help' class='form-text text-muted'>Need help in placing order <b>Call +91 96034 67559</b> or <a href='javascript:Open_Chat()' tabindex='-1'><span class='label label-default chat-with-us' tabindex='-1'>Chat with Us</span></a></small>\
							  </div>\
							  <div id='order-form-usermobile-otp-div' style='display:none;'>\
								  <div class='form-group'>\
									<label for='order-form-usermobile-otp'>Please Enter Verification Code :</label>\
									<input type='tel' class='form-control' id='order-form-usermobile-otp' placeholder='XXXXXX' maxlength='6'  onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:VerifySmsConfirmation(); return false; } else return true;\"><input type='hidden' name='OTP' id='OTP'/>\
									<div id='order-form-usermobile-otp-error'style='display:none;'></div>\
									<small id='order-form-usermobile-otp-help' class='form-text text-muted'> SMS sent to XXXXXXXXXXX, This OTP will be valid for 15 Minutes.</small>\
									</br>\
									<div style='margin: 10px 0px 10px 0px;'>\
										<button tabindex='-1' type='button' class='button-2-order-2 btn button-resendotp' onClick='javascript:SendSmsConfirmation()'>Re-Send OTP</button> \
										<button tabindex='-1' type='button' class='button-2-order-2 btn button-changenumber'  onClick='javascript:ChangeSmsMobileNumber()'>Change Mobile Number</button> \
									</div>\
								  </div>\
							  </div>\
							  <div id='order-form-details-div' style='display:none;'> \
								  <div class='form-group'>\
									<label for='order-form-name'>Please Enter Your Name :</label>\
									<input type='text' class='form-control name yes' id='order-form-name' placeholder='Your Name' maxlength='60'  onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){$('#order-form-email').focus(); return false; } else return true;\" >\
									<div id='order-form-name-error'style='display:none;'></div>\
								  </div>\
								  <div class='form-group'>\
									<label for='order-form-email'>Please Enter Email :</label>\
									<input type='email' class='form-control email yes' id='order-form-email' aria-describedby='emailHelp' placeholder='Enter Email' maxlength='30' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){$('#order-form-address').focus(); return false; } else return true;\" >\
									<div id='order-form-email-error'style='display:none;'></div>\
									<small id='order-form-email-help' class='form-text text-muted'>We'll never share your email with anyone else.</small>\
								  </div>\
								  <div class='form-group'>\
									<label for='order-form-address'>Please Enter Your Delivery Address :</label>\
									<textarea class='form-control' class='form-control email yes' id='order-form-address' rows='4' maxlength='300'  placeholder='Enter Your Address'></textarea>\
									<div id='order-form-address-error'style='display:none;'></div>\
								  </div>\
								  <div class='form-group'>\
									<label for='order-form-landmark'>Landmark :</label>\
									<input type='text' class='form-control' id='order-form-landmark' aria-describedby='emailHelp' placeholder='E.g. Near AIIMS Flyover, Behind Regal Cinema, etc.' maxlength='100'  onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){$('#order-form-ordernow-btn').focus(); return false; } else return true;\" >\
									<div id='order-form-landmark-error' style='display:none;'></div>\
								  </div>\
								  <div class='form-group'>\
									<label for='order-form-pincode'>Your City :</label>\
									<input type='text' class='form-control' id='order-form-city' readonly='readonly' tabindex='-1' >\
								  </div>\
								  <div class='form-group'>\
									<label for='order-form-pincode'>Your State :</label>\
									<input type='text' class='form-control' id='order-form-state' readonly='readonly' tabindex='-1' >\
								  </div>\
								</div>\
							 <div class='clearfix'></div>\
							 <div class='modal-footer' style='padding-left: 0px; padding-right: 0px;'>\
								  <div style=' float: right;  text-align: right;' id='order-form-usermobile-number-btn-div'>\
									<button type='button' class='button btn-cart button-sendotp pull-right' onClick='javascript:SendSmsConfirmation()'>Next Step</button>\
								  </div>\
								  <div style=' float: right;  text-align: right; display:none;'  id='order-form-confirmotp-btn-div'>\
									<button type='button' class='button button-confirmotp' onClick='javascript:VerifySmsConfirmation();'>Confirm OTP</button>\
								  </div>\
								  <div style='float: right;  text-align: right;'>\
									<button type='button' class='button button-next nextBtn' id='order-form-ordernow-btn' style='display:none;'>Next Step</button>\
								  </div>\
							  </div>\
						</div>\
					</div>\
				</div>\
				<div class='row setup-content' id='step-2'>\
					<div class='col-xs-12'>\
						<div class='col-md-12'>\
							<h4 class='hide'> Location</h4>\
								 <div class='form-group'  id='order-form-location' >\
									<label for='order-form-areapincode'>Order by Area or Pincode :</label>\
									<input type='hidden' id='order-form-location_TMP' >\
									<input type='radio' class='form-check-input' checked='checked' name='order-form-areapincode' id='order-form-location-area' value='Area' tabindex='-1' >\
									Area (Recommended) &nbsp;&nbsp;&nbsp;\
									<input type='radio' class='form-check-input' name='order-form-areapincode' id='order-form-location-pincode' value='Pincode' tabindex='-1' >\
									Pincode\
									<div id='order-form-location_TMP-error'style='display:none;'></div>\
								</div>\
								<div class='form-group' id='order-form-area-div'>\
									<label for='order-form-area'>Please Select Your Nearest Area :</label>\
									<select class='form-control yes' id='order-form-area' onChange='find_retaileres_avaliable();' onClick='scroll_on_area();'>\
									  <option>Please Select Area</option>\
									</select>\
									<div id='order-form-area-error'style='display:none;'></div>\
									</br><small id='order-form-area-error-help' class='form-text text-muted'>The Area Selection does not have to be Exact Match of Your Delivery Address. Select the Area in the List which is closest (5 to 10 KM) to Your Delivery Address.</small>\
								</div>\
								<div class='form-group'  id='order-form-pincode-div' style='display:none;'> \
									<label for='order-form-pincode'>Please Enter Your Pincode :</label>\
									<input type='tel' class='form-control yes' onChange='find_retaileres_avaliable();' id='order-form-pincode' placeholder='E.g. 560048' maxlength='6'  onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){$('#order-form-terms_conditions').focus(); return false; } else return true;\" >\
									<div id='order-form-pincode-error'style='display:none;'></div>\
								</div>\
								<div id='retailer_response_error' style='display:none; color: #de3315;'><p style=' font-size: 14px;'><i class='icon-frown' style='color: #de3315;'></i> Oops!.., We are Sorry, The Area or Pincode you selected is Out of our delivery zone. Please select some other nearer Area or Pincode to you.</p> <p style='font-size: 14px;'>Please Contact our Support Team at <b>+91 96034 67559</b> or <a href='javascript:Open_Chat()'><span class='label label-default chat-with-us'>Chat with Us</span></a> </p> </div>\
								<div class='modal-footer' style='padding-left: 0px; padding-right: 0px;'>\
									  <div style=' width: 50%; float: left;  text-align: left;'>\
										<button type='button' class='button button-back' tabindex='-1' onclick='order_form_Back(\"order-form-nav-1\")'>Back</button>\
									  </div>\
									  <div style=' width: 50%; float: right;  text-align: right;'>\
											<button type='button' class='button button-next nextBtn2' id='order-form-ordernow-btn-2'>Next Step</button>\
									  </div>\
								</div>\
						</div>\
					</div>\
				</div>";

				if(product_type_page=="Bike Batteries" && showdelivery==true)
				{
					Order_Form_pop_up=Order_Form_pop_up+"<div class='row setup-content' id='step-3'>\
					<div class='col-xs-12'>\
						<div class='col-md-12'>\
							<h5 style='margin: 9px;padding: 6px;'><b>Home Delivery Available with in 10 KM of radius from our Store</b></h5>\
							<h5 style='margin: 15px;'><b>Do you require Home Delivery ?</b></h5>\
							<div class='form-group'>\
								<div class='' style='padding-left: 40px;'>\
									<div class='payment_radio'>\
										<div class='payment_radio-success'>\
											<input type='radio' name='Delivery_Mode' id='Delivery_Mode-1'  value='Yes' />\
											<label for='Delivery_Mode-1'><span class='icon-check payment-mode-icon'> </span>&nbsp;YES ( + Delivery charges 150 Rs on Price)</label>\
										</div>\
										<div class='payment_radio-success'>\
											<input type='radio' name='Delivery_Mode' id='Delivery_Mode-2' value='No'/>\
											<label for='Delivery_Mode-2'><span class='icon-remove payment-mode-icon'> </span>&nbsp;NO (Get Installed at store with no extra charges)</label>\
										</div>\
									</div>\
									<div id='Delivery_Mode-error'style='display:none;'></div>\
								</div>\
							</div>\
							<div class='modal-footer' style='padding-left: 0px; padding-right: 0px;'>\
							  <div style=' width: 50%; float: left;  text-align: left;'>\
								<button type='button' class='button button-back' tabindex='-1' onclick='order_form_Back(\"order-form-nav-2\")'>Back</button>\
							  </div>\
							  <div style=' width: 50%; float: right;  text-align: right;'>\
									<button type='button' class='button button-next nextBtn3' id='order-form-ordernow-btn-3'>Next Step</button>\
							  </div>\
							</div>\
						</div>\
					</div>\</div></div>\
					<div class='row setup-content' id='step-4'>\
					<div class='col-xs-12'>\
						<div class='col-md-12'>\
							<h4 class='' style='padding: 15px;'> Select Payment Mode</h4>\
							<hr style='margin: 5px;'>\
							<div class='form-group'>\
								<div class=''>\
									<div style='margin: 3%;'>\
										<div class='payment_radio-success'>\
											<input type='checkbox' name='Payment_Mode' id='Payment_Mode-1'  value='Cash On Delivery' />\
											<label for='Payment_Mode-1'><span class='icon-rupee payment-mode-icon'> </span>&nbsp;Cash On Delivery</label>\
										</div>\
									</div>\
									<div id='Payment_Mode-error'style='display:none;'></div>\
								</div>\
							</div>\
							<div class='modal-footer' style='padding-left: 0px; padding-right: 0px;'>\
							  <div style=' width: 50%; float: left;  text-align: left;'>\
								<button type='button' class='button button-back' tabindex='-1' onclick='order_form_Back(\"order-form-nav-3\")'>Back</button>\
							  </div>\
							  <div style=' width: 50%; float: right;  text-align: right;'>\
									<button type='button' class='button button-next nextBtn4' id='order-form-ordernow-btn-4'>Next Step</button>\
							  </div>\
							</div>\
						</div>\
					</div>\
				</div>\
					<div class='row setup-content' id='step-5'>\
					<div class='col-xs-12'>\
						<div class='col-md-12'>\
							<h4 class='hide'> Order Details</h4>\
							 <div class='form-group'>\
								<label for='current_order_details'>Your Current Order Details.</label>\
								<div id='current_order_details'  style=' font-size: 14px;'>\
									<table class='table table-condensed'> <tbody>"+current_order_details_after_order_popup+"</tbody></table>\
								</div>\
									<div id='order-form-delivery-charge-div' style='display:none; font-size: 14px;'>\
									<tr><th><b>Delivery Charges&nbsp;:</b></th><td>150 Rs</td></tr>\
								</div>\
							</div>\
							<div class='form-check'>\
								<label class='form-check-label'>\
								  <input type='checkbox' class='form-check-input'  checked='checked' name='order-form-terms_conditions' id='order-form-terms_conditions'>\
								  I accept all Terms and Conditions.\
								</label>\
								<div id='order-form-terms_conditions-error'style='display:none;'></div> \
							</div>\
							<div class='modal-footer' style='padding-left: 0px; padding-right: 0px;'>\
							  <div style=' width: 50%; float: left;  text-align: left;'>\
								<button type='button' class='button button-back' tabindex='-1' onclick='order_form_Back(\"order-form-nav-4\")'>Back</button>\
							  </div>\
							  <div style=' width: 50%; float: right;  text-align: right;'>\
									<button type='button' class='button button-ordernow nextBtn5' id='order-form-ordernow-btn'>Order Now</button>\
							  </div>\
							</div>\
						</div>\
					</div>\</div>"
				}
				else
				{
					//alert("inside");
					Order_Form_pop_up=Order_Form_pop_up+"<div class='row setup-content' id='step-3'>\
					<div class='col-xs-12'>\
						<div class='col-md-12'>\
							<h4 class='' style='padding: 15px;'> Select Payment Mode</h4>\
							<hr style='margin: 5px;'>\
							<div class='form-group'>\
								<div class=''>\
									<div style='margin: 3%;'>\
										<div class='payment_radio-success'>\
											<input type='checkbox' name='Payment_Mode' id='Payment_Mode-1'  value='Cash On Delivery' />\
											<label for='Payment_Mode-1'><span class='icon-rupee payment-mode-icon'> </span>&nbsp;Cash On Delivery</label>\
										</div>\
									</div>\
									<div id='Payment_Mode-error'style='display:none;'></div>\
								</div>\
							</div>\
							<div class='modal-footer' style='padding-left: 0px; padding-right: 0px;'>\
							  <div style=' width: 50%; float: left;  text-align: left;'>\
								<button type='button' class='button button-back' tabindex='-1' onclick='order_form_Back(\"order-form-nav-2\")'>Back</button>\
							  </div>\
							  <div style=' width: 50%; float: right;  text-align: right;'>\
									<button type='button' class='button button-next nextBtn3' id='order-form-ordernow-btn-3'>Next Step</button>\
							  </div>\
							</div>\
						</div>\
					</div>\
				</div><div class='row setup-content' id='step-4'>\
					<div class='col-xs-12'>\
						<div class='col-md-12'>\
							<h4 class='hide'> Order Details</h4>\
							 <div class='form-group'>\
								<label for='current_order_details'>Your Current Order Details.</label>\
								<div id='current_order_details'  style=' font-size: 14px;'>\
									<table class='table table-condensed'> <tbody>"+current_order_details_after_order_popup+"</tbody></table>\
								</div>\
							</div>\
							<div class='form-check'>\
								<label class='form-check-label'>\
								  <input type='checkbox' class='form-check-input'  checked='checked' name='order-form-terms_conditions' id='order-form-terms_conditions'>\
								  I accept all Terms and Conditions.\
								</label>\
								<div id='order-form-terms_conditions-error'style='display:none;'></div> \
							</div>\
							<div class='modal-footer' style='padding-left: 0px; padding-right: 0px;'>\
							  <div style=' width: 50%; float: left;  text-align: left;'>\
								<button type='button' class='button button-back' tabindex='-1' onclick='order_form_Back(\"order-form-nav-3\")'>Back</button>\
							  </div>\
							  <div style=' width: 50%; float: right;  text-align: right;'>\
									<button type='button' class='button button-ordernow nextBtn4' id='order-form-ordernow-btn'>Order Now</button>\
							  </div>\
							</div>\
						</div>\
					</div>\
				</div>";
					}

			Order_Form_pop_up=Order_Form_pop_up+"</form>\
		</div>\
	</div>\
  </div>\
</div>\
";
	
	 
	$("#Order_form_PopUp").html(Order_Form_pop_up);	

});


$(document).ready(function() {
var Price_Without_Battery =$("#Price_Without_Battery").val();
var Price_With_Battery =$("#Price_With_Battery").val();

var WithOutOldBattery_Percent =$("#WithOutOldBattery_Percent").val();
var WithOldBattery_Percent =$("#WithOldBattery_Percent").val();

    $('#check_with_without_battery input:radio').change(function() {
		var battery_buy_type = $("input[name='battery_buy_type']:checked").val();
		order_product_qty=$("#product_qty").val();
		//alert(22);
		//alert(battery_buy_type);
		if(battery_buy_type=="New" || battery_buy_type=="without-old")
		{
			$('#with-old-note').hide();
			$('#without-old-note').show();		
			$('#final_price').html("<i class='fa fa-inr' aria-hidden='true'></i>&nbsp;"+Price_Without_Battery * order_product_qty);		
			$('#discount_percent').html(WithOutOldBattery_Percent+"% off");		
		}
		else if(battery_buy_type=="Replaced" || battery_buy_type=="with-old")
		{
			$('#with-old-note').show();
			$('#without-old-note').hide();
			$('#final_price').html("<i class='fa fa-inr' aria-hidden='true'></i>&nbsp;"+Price_With_Battery * order_product_qty);		
			$('#discount_percent').html(WithOldBattery_Percent+"% off");		
		}
		else
		{
			alert("Opps !. Some thing went wrong. Please reload the Page.");
		}
		
    });
	
    $('.Battery_Quantity_value').change(function() {
		var battery_buy_type = $("input[name='battery_buy_type']:checked").val();
		order_product_qty=$("#product_qty").val();
		
		var number_regex = /^\d+$/;
		if ((!order_product_qty.match(number_regex) && order_product_qty.length > 0) || (!order_product_qty.match(number_regex)) || (order_product_qty.length == 0) || (order_product_qty<=0) || (isNaN(parseFloat(order_product_qty))==true))
		{
			$("#product_qty").val(1);
			order_product_qty=1;
		}
		else
		{	
		}	
		
		if(battery_buy_type=="New" && order_product_type!="Inverter")
		{
			$('#with-old-note').hide();
			$('#without-old-note').show();		
			$('#final_price').html("<i class='fa fa-inr' aria-hidden='true'></i>&nbsp;"+Price_Without_Battery * order_product_qty);		
			$('#discount_percent').html(WithOutOldBattery_Percent+"% off");		
		}
		else if(battery_buy_type=="Replaced" && order_product_type!="Inverter")
		{
			$('#with-old-note').show();
			$('#without-old-note').hide();
			$('#final_price').html("<i class='fa fa-inr' aria-hidden='true'></i>&nbsp;"+Price_With_Battery * order_product_qty);		
			$('#discount_percent').html(WithOldBattery_Percent+"% off");		
		}
		else if(order_product_type=="Inverter")
		{
			$('#final_price').html("<i class='fa fa-inr' aria-hidden='true'></i>&nbsp;"+order_inverter_discount_price * order_product_qty);
		}
		else
		{
			alert("Opps !. Some thing went wrong. Please reload the Page.");
		}
    });
	
    $('.Battery_Quantity').click(function() {
		$('.Battery_Quantity_value').change();
    });
	
});


$(document).ready(function() {

    $('#order-form-location input:radio').change(function() {
		var order_form_areapincode = $("input[name='order-form-areapincode']:checked").val()
		if(order_form_areapincode=="Area")
		{
			$('#order-form-area-div').show();		
			//$('#order-form-area').val("default");		
			$('#order-form-pincode-div').hide();
			//$("#order-form-area").select2();
			Activate_Model_Scroll();
		}
		else if(order_form_areapincode=="Pincode")
		{
			$('#order-form-area-div').hide();		
			$('#order-form-pincode-div').show();		
			$('#order-form-pincode').val("");	
			//$("#order-form-area").select2("destroy");
		}
		else
		{
			alert("Opps !. Some thing went wrong. Please reload the Page.");
		}
		  
    });
});


$(document).ready(function() {
	
	$("#product_make_page").val(getCookie('product_make_cookie'));
	//alert($("#product_make_page").val());
	$("#product_capacity_page").val(getCookie('product_capacity_cookie'));
	$("#product_model_page").val(getCookie('product_model_cookie'));
	//$("#product_fuel_page").val(getCookie('product_fuel_cookie'));
	//alert($("#product_model_page").val());
	var Product_Model =$("#Product_Model").val();
	var product_make_page =$("#product_make_page").val();
	var product_type_page =$("#product_type_page").val();
	var product_model_page =$("#product_model_page").val();
	var product_capacity_page =$("#product_capacity_page").val();
	//alert("product_make_page"+product_make_page);
	//alert("product_model_page"+product_model_page);
	
	if(product_type_page=="Inverter Batteries" || product_type_page=="Inverter"|| product_type_page=="Inverter and Battery Combo")
	{
		order_battery_fit="yes";
		$('#vehicle_make_model_div').hide();
		$('#vehicle_make_model_div').html("");
	}
	else if((product_make_page=="" || product_make_page=="null" || product_make_page==null|| product_make_page=="undefined" || product_make_page==undefined || product_model_page=="" || product_model_page=="null" || product_model_page==null|| product_model_page=="undefined" || product_model_page==undefined) && product_type_page!="Inverter Batteries" && product_type_page!="Inverter"&& product_type_page!="Inverter and Battery Combo")
	{
		order_battery_fit="no";
		$('#vehicle_make_model_div').html("<p style='text-align: left;'><a href='javascript:ShowVehicleMakeModel()' class='products-vehicle-details-error-custom'> Please check whether this battery supports for your Vehicle. Click Here</a> </p> ");
	}
	else
	{
		 $.ajax
		({					 
			type: "GET",
			url: "/bookbattery/servlet/Functions?hidWhatToDo=CheckBatteryModelwithVehicle",
			data: {BatteryModel:Product_Model,VehicleMake:product_make_page,VehicleModel:product_model_page},
			success: function(data)
			{	
				//alert(data);
				if(data.indexOf("Yep")>=0)
				{
					$('#vehicle_make_model_div').show();
					order_battery_fit="yes";
					$('#vehicle_make_model_div').html("<p class='products-vehicle-details-success-custom'> This Battery Model supports for your current Vehicle <strong>"+product_make_page+"</strong>, <strong>"+product_model_page+"</strong>. </br> <a href='javascript:ShowVehicleMakeModel()' class='button-2-order'> Click here to change Vehicle details. </a> </p> ");
					
					setCookie("product_make_cookie", product_make_page);
					setCookie("product_model_cookie", product_model_page);
				}
				else
				{
					$('#vehicle_make_model_div').show();
					order_battery_fit="no";
					$('#vehicle_make_model_div').html("<p class='products-vehicle-details-error-custom'> This Battery Model Dosen't supports for your current Vehicle <strong>"+product_make_page+"</strong>, <strong>"+product_model_page+"</strong>. <br> <a href='javascript:ShowVehicleMakeModel()' class='button-2-order'> Click here to change Vehicle details. </a> </p> ");
					setCookie("product_make_cookie", product_make_page);
					setCookie("product_model_cookie", product_model_page);
				}				
			}	
		});
	}
});


function ShowVehicleMakeModel()
{
	$('#vehicle_make_model_div').hide();
	$('#vehicle_make_model_select_div').show();
}

function CheckBatteryModelwithVehicle()
{
	var Product_Model =$("#Product_Model").val();
	var product_make =$("#product_make").val();
	var product_model =$("#product_model").val();
	
	//alert("1"+product_model);
	
	$.ajax
	({					 
		type: "GET",
		url: "/bookbattery/servlet/Functions?hidWhatToDo=CheckBatteryModelwithVehicle",
		data: {BatteryModel:Product_Model,VehicleMake:product_make,VehicleModel:product_model },
		success: function(data)
		{	
			if(data.indexOf("Yep")>=0)
			{
				$('#vehicle_make_model_div').show();
				$('#vehicle_make_model_select_div').hide();
				order_battery_fit="yes";
				$('#vehicle_make_model_div').html("<p class='products-vehicle-details-success-custom'> This Battery Model supports for your current Vehicle <strong>"+product_make+"</strong>, <strong>"+product_model+"</strong>. <br><br> <a href='javascript:ShowVehicleMakeModel()' class='button-2-order'> Click here to change Vehicle details. </a> </p> ");
				
				setCookie("product_make_cookie", product_make);
				setCookie("product_model_cookie", product_model);
			}
			else
			{
				$('#vehicle_make_model_div').hide();
				$('#vehicle_make_model_select_div').show();
				order_battery_fit="no";
				$('#vehicle_make_model_select_text_div').html("<p class='products-vehicle-details-error-custom'> This Battery Model Dosen't supports for your current Vehicle <strong>"+product_make+"</strong>, <strong>"+product_model+"</strong>. <br> <a href='javascript:productProductList_ProductPage()' class='products-vehicle-details-error-custom'  style=' font-weight: 700;'> Click here get Batteries for Current Vehicle details. </a> </p> ");
				
				setCookie("product_make_cookie", product_make);
				setCookie("product_model_cookie", product_model);
			}				
		}	
	});

}


function productProductList_ProductPage(){

var product_type =$("#product_type").val();
var product_make =$("#product_make").val();
var product_capacity =$("#product_capacity").val();
var product_model =$("#product_model").val();
var product_brand ="All";
var product_state =$("#product_state").val();
var product_city =$("#product_city").val();
var publicUrl =$("#publicUrl").val();

//alert(product_model);

// This is declared to send input_type in valadation
var input_type;
	input_type="select";
	if (selectValidation("","product_type",input_type) == false)
	{
		return;
	}
	else
	{
		setCookie("product_type_cookie", product_type)
	}
	
	if (product_type=="Inverter Batteries"){
		
		if (selectValidation("","product_capacity",input_type) == false)
		{
			return;
		}
		else
		{
			setCookie("product_capacity_cookie", product_capacity)
		}
	}
	else{

		if (selectValidation("","product_make",input_type) == false)
		{
			return;
		}
		else
		{
			setCookie("product_make_cookie", product_make)
		}
		
		if (selectValidation("","product_model",input_type) == false)
		{
			return;
		}
		else
		{
			setCookie("product_model_cookie", product_model)
		}
	}


	setCookie("product_brand_cookie", product_brand)

	if (selectValidation("","product_state",input_type) == false)
	{
		return;
	}
	else
	{
		setCookie("product_state_cookie", product_state)
	}
	
	if (selectValidation("","product_city",input_type) == false)
	{
		return;
	}
	else
	{
		setCookie("product_city_cookie", product_city)
	}
	
	if (product_type=="Inverter Batteries"){
		
	}
	else{
		
		product_type= product_type.replace(/ /g, "-");
		product_make= product_make.replace(/ /g, "-");
		product_brand= product_brand.replace(/ /g, "-");
		product_state= product_state.replace(/ /g, "-");
		product_city= product_city.replace(/ /g, "-");
		product_model= product_model.replace(/ /g, "+");
		
		window.location.href = publicUrl+'/bookbattery/'+product_type+'/'+product_make+'/'+product_model+'/'+product_state+'/'+product_city+'/'+product_brand+'/' ;
	}
	
}

var SendSmsConfirmation_limit=0;
var InsertBeforeOrder_limit=0;
function SendSmsConfirmation()
{
	var order_form_usermobile_number =$("#order-form-usermobile-number").val();
	order_usermobile_number=order_form_usermobile_number;
	//var OTP =$("#OTP").val();
	//alert(OTP);
	var input_type="mobilenumber";
	if (mobilenumberValidation("","order-form-usermobile-number",input_type) == false)
	{
		return false;
	}
	$("#Loading_bar").show();
	/*$.ajax
	({					 
		type: "GET",
		url: "/bookbattery/servlet/Functions?hidWhatToDo=GetVerificationSms",
		data: {UserMobileNumber:order_form_usermobile_number,OTP:$("#OTP").val()},
		success: function(data)
		{	
			order_usermobile_otp=data;
			
			document.getElementById("OTP").value=order_usermobile_otp;
			
			
			$("#Loading_bar").hide();
			
			if (!data == "")
			{
				if(data.indexOf("Opps")>=0)
				{
					alert("Opps !. Some thing went wrong. Please reload the Page.");
				}
				else
				{
					order_usermobile_number=order_form_usermobile_number;
					order_usermobile_verify="no";
					$("#order-form-usermobile-number").attr('readonly', true); 
					$("#order-form-usermobile-number-btn-div").hide();
					$("#order-form-confirmotp-btn-div").show();
					$("#order-form-usermobile-otp-div").show();
					$("#order-form-usermobile-number-help").hide();
					$('#order-form-usermobile-otp').focus();
					
					if(SendSmsConfirmation_limit==0)
					{
						SendSmsConfirmation_limit=SendSmsConfirmation_limit+1;
						$("#order-form-usermobile-otp-help").html(" SMS sent to +91"+order_usermobile_number+". Need asisitance Please Call <b>+91 96034 67559</b>.");
						return;
					}
					else if(SendSmsConfirmation_limit==1)
					{
						SendSmsConfirmation_limit=SendSmsConfirmation_limit+1;
						$("#order-form-usermobile-otp-help").html("<p style='color: red;'> SMS sent 2nd time to +91"+order_usermobile_number+". Need asisitance Please Call <b>+91 96034 67559</b>.");
						$(".button-resendotp").attr('onClick','#').hide(); 
						$(".button-changenumber").attr('onClick','#').hide(); 
						return;
					}	
				}
			}	
			else
			{
				alert("Opps !. Some thing went wrong. Please reload the Page.");
			}
		}	
	});*/
	
	/*Added the code to hide the OTP*/
	
	
			$.ajax
			({					 
				type: "GET",
				url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getarea",
				data: {city:$("#product_city").val()},
				success: function(options)
				{
					$("#order-form-area").html(options);
					//$("#order-form-area").select2();
				}
			});
	
		$("#order-form-usermobile-number").attr('readonly', true); 
		$("#order-form-usermobile-number-btn-div").hide();
		$("#order-form-confirmotp-btn-div").hide();
		$("#order-form-usermobile-otp-div").hide();
		$("#order-form-usermobile-number-help").hide();
		$("#order-form-details-div").show();
		$("#order-form-ordernow-btn").show();
		//$('#order-form-usermobile-otp').focus();
		$("#order-form-state").val($("#product_state").val());
		$("#order-form-city").val($("#product_city").val());
		$('#order-form-name').focus();
				if(order_product_type=="Inverter and Battery Combo")
			{
				var Inverter_Battery_Pic =$("#Inverter_Battery_Pic").val();
				var Inverter_Battery_Model =$("#Inverter_Battery_Model").val();
				current_order_details_after_order_popup="<tr> <th>Inverter Details&nbsp;:</th> <td>"+order_product_brand+", "+order_product_model+"</td> </tr><tr><th>Battery Details&nbsp;:</th> <td>"+order_product_brand+", "+Inverter_Battery_Model+", "+Inverter_Battery_Pic+" PCS.</td> </tr> <tr> <th>Price Details&nbsp;:</th> <td>Without Old Battery - Rs. "+order_without_old_battery+"</br>With Old Battery - Rs. "+order_with_old_battery+"</td> </tr> ";
				
				current_order_details="Inverter Details : "+order_product_brand+", "+order_product_model+". &#13;&#10;Battery Details : "+order_product_brand+", "+Inverter_Battery_Model+", "+Inverter_Battery_Pic+" PCS. &#13;&#10;Price Details : Without Old Battery - Rs. "+order_without_old_battery+", With Old Battery - Rs. "+order_with_old_battery+". ";
				$("#current_order_details").html(current_order_details_after_order_popup);
			}
			else if(order_product_type=="Inverter Batteries")
			{
				current_order_details_after_order_popup="<tr> <th>Battery Details&nbsp;:</th> <td>"+order_product_brand+", "+order_product_model+"</td> </tr> <tr> <th>Price Details&nbsp;:</th> <td>Without Old Battery - Rs. "+order_without_old_battery+"</br>With Old Battery - Rs. "+order_with_old_battery+"</td> </tr> ";
				
				current_order_details="Battery Details : "+order_product_brand+", "+order_product_model+". &#13;&#10;Price Details : Without Old Battery - Rs. "+order_without_old_battery+", With Old Battery - Rs. "+order_with_old_battery+". ";
				$("#current_order_details").html(current_order_details_after_order_popup);
			}
			else if(order_product_type=="Inverter")
			{
				current_order_details_after_order_popup="<tr> <th>Inverter Details&nbsp;:</th> <td>"+order_product_brand+", "+order_product_model+"</td> </tr> <tr> <th>Price Details&nbsp;:</th> <td> Discount Price - Rs. "+order_inverter_discount_price+"</td> </tr> ";
				
				current_order_details="Inverter Details : "+order_product_brand+", "+order_product_model+" . &#13;&#10;Price Details : Discount Price - Rs. "+order_inverter_discount_price+".";
				$("#current_order_details").html(current_order_details_after_order_popup);
			}
			else
			{
				//alert(22);
				
				current_order_details_after_order_popup="<tr> <th>Vehicle Details&nbsp;:</th> <td>"+order_vehiclemake+", "+order_vehiclemodel+"</td> </tr> <tr> <th>Battery Details&nbsp;:</th> <td>"+order_product_brand+", "+order_product_model+"</td> </tr> <tr> <th>Price Details&nbsp;:</th> <td>Without Old Battery - Rs. "+order_without_old_battery+"</br>With Old Battery - Rs. "+order_with_old_battery+"</td> </tr> ";
				
				current_order_details="Vehicle Details :"+order_vehiclemake+", "+order_vehiclemodel+" . &#13;&#10;Battery Details : "+order_product_brand+", "+order_product_model+" . &#13;&#10;Price Details : Without Old Battery - Rs. "+order_without_old_battery+", With Old Battery - Rs. "+order_with_old_battery+" . ";
				$("#current_order_details").html(current_order_details_after_order_popup);
			}
	
	if(order_product_type=="Inverter Batteries" || order_product_type=="Inverter")
	{	
		order_vehiclemake = "";
		order_vehiclemodel ="";
		order_inverter_battery_model ="";
		order_inverter_battery_capacity ="";
		order_inverter_battery_brand ="";
	}
	else if(order_product_type=="Inverter and Battery Combo")
	{
		order_vehiclemake = "";
		order_vehiclemodel ="";
	}
	else
	{
		order_inverter_battery_model ="";
		order_inverter_battery_capacity ="";
		order_inverter_battery_brand ="";
		
	}
	
	if(InsertBeforeOrder_limit==0)
	{
		var order_usermobile_number_URL = order_form_usermobile_number.replace(/ /g, "+");
		var order_product_type_URL = order_product_type.replace(/ /g, "+");
		var order_product_brand_URL = order_product_brand.replace(/ /g, "+");
		var order_product_model_URL = order_product_model.replace(/ /g, "+");
		var order_vehiclemake_URL = order_vehiclemake.replace(/ /g, "+");
		var order_vehiclemodel_URL = order_vehiclemodel.replace(/ /g, "+");
		var order_state_URL = order_state.replace(/ /g, "+");
		var order_city_URL = order_city.replace(/ /g, "+");
		var order_inverter_battery_model_URL = order_inverter_battery_model.replace(/ /g, "+");
		var order_inverter_battery_capacity_URL = order_inverter_battery_capacity.replace(/ /g, "+");
		var order_inverter_battery_brand_URL = order_inverter_battery_brand.replace(/ /g, "+");
		//alert(order_inverter_battery_brand_URL);
		
		order_usermobile_number_URL=escape(order_usermobile_number_URL);
		order_product_type_URL=escape(order_product_type_URL);
		order_product_brand_URL=escape(order_product_brand_URL);
		order_product_model_URL=escape(order_product_model_URL);
		order_vehiclemake_URL=escape(order_vehiclemake_URL);
		order_vehiclemodel_URL=escape(order_vehiclemodel_URL);
		order_state_URL=escape(order_state_URL);
		order_city_URL=escape(order_city_URL);

		order_inverter_battery_model_URL=escape(order_inverter_battery_model_URL);
		order_inverter_battery_capacity_URL=escape(order_inverter_battery_capacity_URL);
		order_inverter_battery_brand_URL=escape(order_inverter_battery_brand_URL);
	

		$.ajax
		({					 
			url: "/bookbattery/servlet/Functions?hidWhatToDo=InsertBeforeOrder",
			type: 'GET',
			data:{	UMN:order_usermobile_number_URL,
					UOTP:order_usermobile_otp,
					UPT:order_product_type_URL,
					UPB:order_product_brand_URL,
					UPM:order_product_model_URL,
					UVMK:order_vehiclemake_URL,
					UVMD:order_vehiclemodel_URL,
					UST:order_state_URL,
					UCT:order_city_URL,
					UIBM:order_inverter_battery_model_URL,
					UIBC:order_inverter_battery_capacity_URL,
					UIBB:order_inverter_battery_brand_URL,
			},
			success: function(data)
			{	
				$("#Loading_bar").hide();
				current_order_details= current_order_details.replace(" . ", "</br>");
				var OrderLable="";
				var OrderMessage="";
				if(data.indexOf("ORD")>=0)
				{
					order_number=data;
					InsertBeforeOrder_limit=1;
				}
				else
				{
					order_number="Failed";
				}
				
			}	
		});
	}
	else
	{
		
	}
}


function VerifySmsConfirmation()
{
	//var order_form_usermobile_otp =$("#order-form-usermobile-otp").val();
	var order_form_usermobile_otp =0;
	/*if (order_form_usermobile_otp.length < 6 || order_form_usermobile_otp.length > 6 )
	{
		order_usermobile_verify="no";
		everyThingNotOk("order-form-usermobile-otp","OTP You Entered is <b>Incorrect</b>.");
		return false ;
	}
	else
	{	
		everyThingOk("order-form-usermobile-otp");		
		order_usermobile_otp=order_usermobile_otp.trim();
		if (order_usermobile_otp.length > 6) 
		{		
			order_usermobile_otp=order_usermobile_otp.substring(4, 10);
		}				
		else
		{
			order_usermobile_otp=order_usermobile_otp;			
		}
		
		if($.trim(order_form_usermobile_otp) == $.trim(order_usermobile_otp))
		{*/

			$("#order-form-usermobile-number-help").show();
			order_usermobile_verify="yes";
			$("#order-form-area").html("<option>Loading ..</option>");
			$.ajax
			({					 
				type: "GET",
				url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getarea",
				data: {city:$("#product_city").val()},
				success: function(options)
				{
					$("#order-form-area").html(options);
					//$("#order-form-area").select2();
				}
			});
			//setCookie("user_mobile_number_cookie", order_usermobile_number);
			$("#order-form-details-div").show();
			$("#order-form-ordernow-btn").show();
			$("#order-form-usermobile-number-help").html("</br><span class='label label-success'>Your Mobile number Verified Successfully.</span>");
			$("#order-form-usermobile-otp-div").html("");
			$("#order-form-confirmotp-btn-div").hide();
			$("#order-form-state").val($("#product_state").val());
			$("#order-form-city").val($("#product_city").val());
			$('#order-form-name').focus();
			
			if(order_product_type=="Inverter and Battery Combo")
			{
				var Inverter_Battery_Pic =$("#Inverter_Battery_Pic").val();
				var Inverter_Battery_Model =$("#Inverter_Battery_Model").val();
				current_order_details_after_order_popup="<tr> <th>Inverter Details&nbsp;:</th> <td>"+order_product_brand+", "+order_product_model+"</td> </tr><tr><th>Battery Details&nbsp;:</th> <td>"+order_product_brand+", "+Inverter_Battery_Model+", "+Inverter_Battery_Pic+" PCS.</td> </tr> <tr> <th>Price Details&nbsp;:</th> <td>Without Old Battery - Rs. "+order_without_old_battery+"</br>With Old Battery - Rs. "+order_with_old_battery+"</td> </tr> ";
				
				current_order_details="Inverter Details : "+order_product_brand+", "+order_product_model+". &#13;&#10;Battery Details : "+order_product_brand+", "+Inverter_Battery_Model+", "+Inverter_Battery_Pic+" PCS. &#13;&#10;Price Details : Without Old Battery - Rs. "+order_without_old_battery+", With Old Battery - Rs. "+order_with_old_battery+". ";
				$("#current_order_details").html(current_order_details_after_order_popup);
			}
			else if(order_product_type=="Inverter Batteries")
			{
				current_order_details_after_order_popup="<tr> <th>Battery Details&nbsp;:</th> <td>"+order_product_brand+", "+order_product_model+"</td> </tr> <tr> <th>Price Details&nbsp;:</th> <td>Without Old Battery - Rs. "+order_without_old_battery+"</br>With Old Battery - Rs. "+order_with_old_battery+"</td> </tr> ";
				
				current_order_details="Battery Details : "+order_product_brand+", "+order_product_model+". &#13;&#10;Price Details : Without Old Battery - Rs. "+order_without_old_battery+", With Old Battery - Rs. "+order_with_old_battery+". ";
				$("#current_order_details").html(current_order_details_after_order_popup);
			}
			else if(order_product_type=="Inverter")
			{
				current_order_details_after_order_popup="<tr> <th>Inverter Details&nbsp;:</th> <td>"+order_product_brand+", "+order_product_model+"</td> </tr> <tr> <th>Price Details&nbsp;:</th> <td> Discount Price - Rs. "+order_inverter_discount_price+"</td> </tr> ";
				
				current_order_details="Inverter Details : "+order_product_brand+", "+order_product_model+" . &#13;&#10;Price Details : Discount Price - Rs. "+order_inverter_discount_price+".";
				$("#current_order_details").html(current_order_details_after_order_popup);
			}
			else
			{
				current_order_details_after_order_popup="<tr> <th>Vehicle Details&nbsp;:</th> <td>"+order_vehiclemake+", "+order_vehiclemodel+"</td> </tr> <tr> <th>Battery Details&nbsp;:</th> <td>"+order_product_brand+", "+order_product_model+"</td> </tr> <tr> <th>Price Details&nbsp;:</th> <td>Without Old Battery - Rs. "+order_without_old_battery+"</br>With Old Battery - Rs. "+order_with_old_battery+"</td> </tr> ";
				
				current_order_details="Vehicle Details :"+order_vehiclemake+", "+order_vehiclemodel+" . &#13;&#10;Battery Details : "+order_product_brand+", "+order_product_model+" . &#13;&#10;Price Details : Without Old Battery - Rs. "+order_without_old_battery+", With Old Battery - Rs. "+order_with_old_battery+" . ";
				$("#current_order_details").html(current_order_details_after_order_popup);
			}
			
			$('textarea, input[type="text"],[type="email"],[type="search"]').on('change', function(event){
				event.preventDefault();
				var id =$(this).attr('id');
				var input_type;
				if ( $(this).hasClass("name"))
				{
					input_type="name";
					return nameValidation(event,id,input_type)
				}
				if ( $(this).hasClass("email"))
				{
					input_type="email";
					return emailValidation(event,id,input_type)
				}
				if ( $(this).hasClass("number"))
				{
					input_type="number";
					return onlyNumberKey(event,id,input_type)
				}
				if ( $(this).hasClass("address"))
				{
					input_type="address";
					return addressValidation(event,id,input_type)
				}
				if ( $(this).hasClass("yes"))
				{
					input_type="yes";
					return yesValidation(event,id,input_type)
				}
				else{
					input_type="";
				}		
			});	
			
			$('input[type="tel"]').change(function(event){
				event.preventDefault();
				var id =$(this).attr('id');
				var input_type;
				if ( $(this).hasClass("mobilenumber"))
				{
					input_type="mobilenumber";
					return mobilenumberValidation(event,id,input_type)
				}
				else if ( $(this).hasClass("pincode"))
				{
					input_type="pincode";
					return pincodeValidation(event,id,input_type)
				}
				else{
					input_type="";
					return onlyNumberKey(event,id,input_type)
				}	
			});	

			
		//}
		/*else
		{
			order_usermobile_verify="no";
			everyThingNotOk("order-form-usermobile-otp","OTP You Entered is <b>Incorrect</b>.");
			return false ;
		}*/
	}
//}


function ChangeSmsMobileNumber()
{
	$("#order-form-usermobile-number").attr('readonly', false); 
	$("#order-form-usermobile-number-btn-div").show();
	$("#order-form-usermobile-otp-div").hide();
	$("#order-form-confirmotp-btn-div").hide();
	$("#order-form-usermobile-number-help").show();
	SendSmsConfirmation_limit=0;
	InsertBeforeOrder_limit=0;
}

function OrderProductOnline()
{
	var return_validate=validate_refer_code();
	
	if($('#refcode').val()!="")
	{
		if(return_validate.trim()=="Code Applied Successfully")
		{
			
		}
		else
		{	
			//alert("inside");
			$('#refcode').focus();
			return;
		}
	}
	else
	{
		
	}

	var product_type =$("#product_type").val();
	var product_make =$("#product_make").val();
	var product_model =$("#product_model").val();
	//alert(product_model);
	var product_state =$("#product_state").val();
	var product_city =$("#product_city").val();
	
		
    $('.Battery_Quantity').click(function() {
		$('.Battery_Quantity_value').change();
    });
	

	// This is declared to send input_type in valadation
	var input_type;
	input_type="select";
	
	if(product_type!="Inverter Batteries" && product_type!="Inverter" && product_type!="Inverter and Battery Combo")
	{	
		if (selectValidation("","product_make",input_type) == false)
		{
			ShowVehicleMakeModel();
			return;
		}
		else
		{
			order_vehiclemake=product_make;
		}	

		if (selectValidation("","product_model",input_type) == false)
		{
			ShowVehicleMakeModel();
			return;
		}
		else
		{
			order_vehiclemodel=product_model;
			//alert("before"+order_vehiclemodel);
			//var fuel_type =$("#fuel_type").val();
			//order_vehiclemodel=product_model+" "+fuel_type;
			//alert("after"+order_vehiclemodel);
		} 
	}

	if (selectValidation("","product_state",input_type) == false)
	{
		return;
	}
	else
	{
		order_city=product_state;
	}

	if (selectValidation("","product_city",input_type) == false)
	{
		return;
	}
	else
	{
		order_city=product_city;
	}
	if ((order_battery_fit=="no" || order_battery_fit=="" || order_battery_fit=="NO" || order_battery_fit=="No" ) && product_type!="Inverter Batteries" && product_type!="Inverter" && product_type!="Inverter and Battery Combo")
	{
		ShowVehicleMakeModel();
		return;
	}
	else
	{
		if(SendSmsConfirmation_limit == 0 )
		{
			$("#order-form-usermobile-number").val(getCookie('user_mobile_number_cookie'));
		}
		else
		{
		}
	
		if (mobilenumberValidation("","order-form-usermobile-number",input_type) == false || $("#order-form-usermobile-number").val()=="" || SendSmsConfirmation_limit > 0 )
		{
			setTimeout(function(){
				$('#order-form-usermobile-number').focus();
			}, 1000);
			$("#battery-order-form-btn").click();
		}
		else
		{
			//setCookie("UserMobileNumber_Coo","");
			//setCookie("UserMobileNumber_code_Coo","");
			SendSmsConfirmation();
			setTimeout(function(){
				$('#order-form-usermobile-otp').focus();
			}, 1000);
			$("#battery-order-form-btn").click();
		}
		everyThingOk("order-form-usermobile-number");
		return;
	}
}

function OrderProductOnline_step_2()
{
	
	if (nameValidation("","order-form-name","name") == false)
	{
		return false;
	}
	else
	{
		order_username =$("#order-form-name").val();
	}	
	if (emailValidation("","order-form-email","email") == false)
	{
		return false;
	}
	else
	{
		order_useremail =$("#order-form-email").val();
	}	

	if (addressValidation("","order-form-address","address") == false)
	{
		return false;
	}
	else
	{
		order_user_address =$("#order-form-address").val();
	}
	
	order_user_landmark =$("#order-form-landmark").val();

}

function OrderProductOnline_step_3()
{
	
	var order_form_areapincode = $("input[name='order-form-areapincode']:checked").val()
	if(order_form_areapincode=="" || order_form_areapincode=="undefined" || order_form_areapincode==undefined || order_form_areapincode==null)
	{
		everyThingNotOk("order-form-location_TMP","Please select <b>Order by Area or Pincode.</b>");
		return false ;
	}
	else 
	{
		everyThingOk("order-form-location_TMP");
		if(order_form_areapincode=="Area")
		{
			if (selectValidation("","order-form-area","select") == false)
			{
				return false;
			}
			else
			{
				order_user_area =$("#order-form-area").val();
				order_user_pincode="";
				return true;
			}		
		}
		else if(order_form_areapincode=="Pincode")
		{
			if (pincodeValidation("","order-form-pincode","pincode") == false)
			{
				return false;
			}
			else
			{
				order_user_pincode =$("#order-form-pincode").val();
				order_user_area="";
				return true;
			}		
		}
		else
		{
			everyThingNotOk("order-form-location_TMP","Please select <b>Order by Area or Pincode.</b>");
			return false ;
		}
	}
}

function OrderProductOnline_step_4()
{
	
	var order_product_payment_mode_val = $("input[name='Payment_Mode']:checked").val();
	console.log(order_product_payment_mode_val);
	
	if(order_product_payment_mode_val == undefined ||order_product_payment_mode_val =="" || order_product_payment_mode_val =="null" || order_product_payment_mode_val =="NULL"  || order_product_payment_mode_val =="undefined" )
	{
		everyThingNotOk("Payment_Mode","Please Select <b>Some Payment Mode.</b>");
		return false ;
	}
	else if(order_product_payment_mode_val=="Cash On Delivery" || order_product_payment_mode_val=="Debit Card" || order_product_payment_mode_val=="Credit Card" || order_product_payment_mode_val=="Net Banking" || order_product_payment_mode_val=="UPI - Unified Payment Interface" || order_product_payment_mode_val=="Digital Wallets"|| order_product_payment_mode_val=="EMI" || order_product_payment_mode_val=="Online Payment After Fitment")
	{
		everyThingOk("Payment_Mode");
	}
	else
	{
		everyThingNotOk("Payment_Mode","Some thing went wrong, <br />Please contact our<b> Support team at +91 96034 67559</b> Or<br /><b>Change your selected payment mode.</b>");
		return false ;
	}
	
	
	if(order_product_payment_mode_val=="Cash On Delivery")
	{
		$("#order-form-ordernow-btn").html("Order Now");
	}
	else
	{
		$("#order-form-ordernow-btn").html("Proceed for Payment");
	}
	
	$("#current_order_payment_mode").html("<br> Your Current Payment Mode : <b>"+order_product_payment_mode_val+"</b>");
	
	order_product_payment_mode=order_product_payment_mode_val;
	
	return order_product_payment_mode;
}

function OrderProductOnline_step_4a()
{
	
	var order_product_deliver_mode_val = $("input[name='Delivery_Mode']:checked").val();
	console.log(order_product_deliver_mode_val);
	
	if(order_product_deliver_mode_val == undefined ||order_product_deliver_mode_val =="" || order_product_deliver_mode_val =="null" || order_product_deliver_mode_val =="NULL"  || order_product_deliver_mode_val =="undefined" )
	{
		everyThingNotOk("Delivery_Mode","Please Select <b>Some Delivery Mode.</b>");
		return false ;
	}
	else if(order_product_deliver_mode_val=="Yes" || order_product_deliver_mode_val=="No")
	{
		everyThingOk("Delivery_Mode");
	}
	else
	{
		everyThingNotOk("Payment_Mode","Some thing went wrong, <br />Please contact our<b> Support team at +91 96034 67559</b> Or<br /><b>Change your selected payment mode.</b>");
		return false ;
	}
	
	
	if(order_product_deliver_mode_val=="Yes")
	{
		$("#order-form-delivery-charge-div").show();
	}
	else
	{
		$("#order-form-delivery-charge-div").hide();
	}
	

	order_product_deliver_mode=order_product_deliver_mode_val;
	
	return order_product_deliver_mode;
}

function OrderProductOnline_step_5()
{
	var order_terms_conditions=$("input[name='order-form-terms_conditions']").prop("checked");
	var refcode="";
	var order_product_battery_type;
	
	// Code added by Sai Krishna Daddala on 27/01/2017
	//#################### Code for Battery Quantity and Battery Type ######################
	$('.Battery_Quantity').click(function() {
		$('.Battery_Quantity_value').change();
    });
	
	if($('#refcode').val()=="")
	{
		refcode="0";
	}
	else
	{
		//alert($('#refcode').val());
		refcode=$('#refcode').val();
	}
	
	if(order_product_type_URL=="Inverter" || order_product_type=="Inverter")
	{			
		order_product_battery_type = "Inverter";
		order_product_qty=$("#product_qty").val();
	}
	else if(order_product_type_URL=="Inverter+and+Battery+Combo" || order_product_type=="Inverter and Battery Combo")
	{	
		order_product_battery_type = $("input[name='battery_buy_type']:checked").val();
		order_product_qty="1";
	}
	else
	{
		order_product_battery_type = $("input[name='battery_buy_type']:checked").val();
		order_product_qty=$("#product_qty").val();
	}
	// Code added by Sai Krishna Daddala on 27/01/2017
	
	if(order_terms_conditions == true)
	{
		everyThingOk("order-form-terms_conditions");
	}
	else if(order_terms_conditions == false)
	{
		everyThingNotOk("order-form-terms_conditions","Please Accept <b>Terms and Conditions.</b>");
		return false ;
	}
	
	if(order_product_type=="Inverter Batteries" || order_product_type=="Inverter")
	{	
		order_vehiclemake = "";
		order_vehiclemodel ="";
		order_inverter_battery_model ="";
		order_inverter_battery_capacity ="";
		order_inverter_battery_brand ="";
		
	}
	else if(order_product_type=="Inverter and Battery Combo")
	{
		order_vehiclemake = "";
		order_vehiclemodel ="";
	}
	else
	{
		order_inverter_battery_model ="";
		order_inverter_battery_capacity ="";
		order_inverter_battery_brand ="";
	}
	
	var order_usermobile_number_URL = order_usermobile_number.replace(/ /g, "+");
	//var order_usermobile_otp_URL = order_usermobile_otp.replace(/ /g, "+");
	var order_product_type_URL = order_product_type.replace(/ /g, "+");
	var order_product_brand_URL = order_product_brand.replace(/ /g, "+");
	var order_product_model_URL = order_product_model.replace(/ /g, "+");
	var order_vehiclemake_URL = order_vehiclemake.replace(/ /g, "+");
	var order_vehiclemodel_URL = order_vehiclemodel.replace(/ /g, "+");
	var order_username_URL = order_username.replace(/ /g, "+");
	var order_useremail_URL = order_useremail.replace(/ /g, "+");
	var order_user_area_URL = order_user_area.replace(/ /g, "+");
	var order_user_pincode_URL = order_user_pincode.replace(/ /g, "+");
	var order_state_URL = order_state.replace(/ /g, "+");
	var order_city_URL = order_city.replace(/ /g, "+");
	var order_user_address_URL = order_user_address.replace(/ /g, "+");
	var order_user_landmark_URL = order_user_landmark.replace(/ /g, "+");
	var order_product_battery_type_URL = order_product_battery_type.replace(/ /g, "+");
	var order_product_qty_URL = order_product_qty.replace(/ /g, "+");
	var order_product_payment_mode_URL = order_product_payment_mode.replace(/ /g, "+");
	var refcode_URL = refcode.replace(/ /g, "+");
	var order_product_deliver_mode_URL;
	if(order_product_type=="Bike Batteries"&& showdelivery==true)
	{
	  order_product_deliver_mode_URL = order_product_deliver_mode.replace(/ /g, "+");
	}
	else
	{
		order_product_deliver_mode_URL="Yes";
	}
	
	
	var order_inverter_battery_model_URL = order_inverter_battery_model.replace(/ /g, "+");
	var order_inverter_battery_capacity_URL = order_inverter_battery_capacity.replace(/ /g, "+");
	var order_inverter_battery_brand_URL = order_inverter_battery_brand.replace(/ /g, "+");
	
	//alert("combo");
	//alert(order_inverter_battery_brand_URL);
	
	order_number=order_number.trim();
	order_product_payment_mode_URL=order_product_payment_mode_URL.trim();
	//order_usermobile_otp_URL=order_usermobile_otp_URL.replace(/\r?\n/g,'');
	order_usermobile_number_URL.trim(order_usermobile_number_URL);
	order_username_URL=order_username_URL.trim();
	order_usermobile_number_URL=escape(order_usermobile_number_URL);
	//order_usermobile_otp_URL=escape(order_usermobile_otp_URL);
	order_product_type_URL=escape(order_product_type_URL);
	order_product_brand_URL=escape(order_product_brand_URL);
	//alert(order_product_brand_URL);
	order_product_model_URL=escape(order_product_model_URL);
	order_vehiclemake_URL=escape(order_vehiclemake_URL);
	order_vehiclemodel_URL=escape(order_vehiclemodel_URL);
	order_username_URL=escape(order_username_URL);
	order_useremail_URL=escape(order_useremail_URL);
	order_user_area_URL=escape(order_user_area_URL);
	order_user_pincode_URL=escape(order_user_pincode_URL);
	order_state_URL=escape(order_state_URL);
	order_city_URL=escape(order_city_URL);
	order_user_address_URL=escape(order_user_address_URL);
	order_user_landmark_URL=escape(order_user_landmark_URL);
	order_product_battery_type_URL=escape(order_product_battery_type_URL);
	order_product_qty_URL=escape(order_product_qty_URL);
	order_product_payment_mode_URL=escape(order_product_payment_mode_URL);
	order_product_deliver_mode_URL=escape(order_product_deliver_mode_URL);
	refcode_URL=escape(refcode_URL);
	
	order_inverter_battery_model_URL=escape(order_inverter_battery_model_URL);
	order_inverter_battery_capacity_URL=escape(order_inverter_battery_capacity_URL);
	order_inverter_battery_brand_URL=escape(order_inverter_battery_brand_URL);
	
	order_number=escape(order_number);
	
	var Order_Servlet_Name="";
	var More_info="";
	if(order_product_type_URL=="Inverter" || order_product_type=="Inverter")
	{	
		Order_Servlet_Name="InsertOrderDetails_Inverter";
		
		More_info="<div style='text-align: center;padding: 8px 8px; margin-top: 5px;  background: #efefef;'><p><b> Would you like to buy Inverter Batteries at best warranty and cheap cost for your Inverter?  </b></p><button type='button' class='button-2-order-2 btn' onclick='order_inv_battery()'>Click here to Buy</button> </div>"
	}
	else if(order_product_type_URL=="Inverter+and+Battery+Combo" || order_product_type=="Inverter and Battery Combo")
	{	
		Order_Servlet_Name="InsertOrderDetails_Combo";
	}
	else
	{
		Order_Servlet_Name="InsertOrderDetails_Battery";
	}
	$("#Loading_bar").show();
	$.ajax
	({					 
		url: "/bookbattery/servlet/Checkout?hidWhatToDo="+Order_Servlet_Name+"",
		type: 'GET',
		data:{	UMN:order_usermobile_number_URL,
				UOTP:order_usermobile_otp,
				UPT:order_product_type_URL,
				UPB:order_product_brand_URL,
				UPM:order_product_model_URL,
				UVMK:order_vehiclemake_URL,
				UVMD:order_vehiclemodel_URL,
				UNA:order_username_URL,
				UEM:order_useremail_URL,
				UAR:order_user_area_URL,
				UPN:order_user_pincode_URL,
				UST:order_state_URL,
				UCT:order_city_URL,
				UIBM:order_inverter_battery_model_URL,
				UIBC:order_inverter_battery_capacity_URL,
				UIBB:order_inverter_battery_brand_URL,
				UAD:order_user_address_URL,
				ULM:order_user_landmark_URL,
				ORD:order_number,
				OBT:order_product_battery_type_URL,
				OPQ:order_product_qty_URL,
				OPM:order_product_payment_mode_URL,
				ODM:order_product_deliver_mode_URL,
				ORC:refcode_URL,
				SDY:showdelivery,
		},
		success: function(data)
		{	
			
			window.location.href = data;
			return;
			
			$("#Loading_bar").hide();
			current_order_details= current_order_details.replace(" . ", "</br>");
			var OrderLable="";
			var OrderMessage="";
			if(data.indexOf("Sucessfully")>=0)
			{
				$("#Order_form_PopUp").html("");
				Response = data.split('|');
				OrderLable="Successfully Ordered - BookBattery";
				OrderMessage="<div style='text-align: center;'>\
					<i class='icon-smile' style='font-size: 60px;color: #1d8e11;'></i>\
					<p style='font-size: 14px;font-weight: 600;'>Thanks for Ordering with BookBattery</p>\
				</div>\
				<div>\
					<div class='center-col col-md-9 col-sm-12 col-xs-12 nopadding-xs '>\
						<h4>Your Order Details</h4>\
						<table style=' color: #131212;font-size: 13px;'  class='table table-condensed'> <tbody> <tr> <th>Order Number&nbsp;:</th> <td>"+Response[1]+" </td> </tr>"+ current_order_details_after_order_popup+"</tbody></table>\
					</div>"+More_info+"\
					<hr size='100'>\
					<div style='text-align: center;'>\
						<p> \
							Please Check your Email or Mobile for More Information regarding Your Order.<br /> Our Team will Call you soon for Order Confirmation.\
						</p>\
					</div>\
				</div>";
			}
			else if (data.indexOf("No Retailers Found")>=0)
			{
				OrderLable="Failed to Order - BookBattery";
				OrderMessage="<div style='text-align: center;'>\
					<i class='icon-frown' style='font-size: 60px;color: #de3315;'></i>\
					<h4>We are Sorry, No Retailers Found on Selected City or Area!! </h4>\
					<p style='font-size: 17px;'>Please Contact our Support Team at <br/><b>+91 96034 67559</b> or <a href='javascript:Open_Chat()'><span class='label label-default chat-with-us'>Chat with Us</span></a> </p>\
				</div>\
				</br>\
				";
				
			}else
			{
				OrderLable="Failed to Order - BookBattery";
				OrderMessage="<div style='text-align: center;'>\
					<i class='icon-frown' style='font-size: 60px;color: #de3315;'></i>\
					<h4>We are Sorry,Some thing went wrong !! </h4>\
					<p style='font-size: 17px;'>Please try to reorder or Contact our Support Team at <br/><b>+91 96034 67559</b> or <a href='javascript:Open_Chat()'><span class='label label-default chat-with-us'>Chat with Us</span></a></p>\
				</div>\
				</br>\
				";
				
			}
			
			var AfterOrder_PopUp="<a class='btn hide' data-toggle='modal' data-target='.after_order_popup' data-toggle='modal' data-backdrop='static' data-keyboard='false' id='after_order_popup_btn'></a><div class='modal fade after_order_popup' tabindex='-1' role='dialog' aria-labelledby='after_order_popup_label'> <div class='modal-dialog' role='document'> 	<div class='modal-content'> <div class='modal-header'> 	<button type='button' onclick='okey_done()' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'> x</span></button> 	<h4 class='modal-title' id='after_order_popup_label'>"+OrderLable+"</h4> </div> <div class='modal-body'> 	<div class='form-group'> <div id='after_order_popup_data'> "+OrderMessage+"</div> <div style='text-align: center;'> 	<button type='button' class='button button-continue' onclick='okey_done()'>Okay</button> </div> 	</div> </div> 	</div> </div> </div>";
			
			$("#Order_form_PopUp").html(AfterOrder_PopUp);
			$("#after_order_popup_btn").click();
		}	
	});
}

function backspace_press(event)
{
	//alert(event);
	$("#ref_code_label").html("Enter Referral Code");
	$("#applycode").html("Apply");
	$("#applycode").css("background-color","#e02d29");
	$("#ref_code_label").css("color","#333");
	$("#refcode").css({"border-bottom-color":"#e02d29","color":"#333"});
}

function okey_done()
{
	location.reload();
}

function order_inv_battery()
{
	var publicUrl =$("#publicUrl").val();
	var baseurldirectory =$("#baseurldirectory").val();
	var product_state =$("#product_state").val();
	var product_city =$("#product_city").val();
	window.location.href = publicUrl+baseurldirectory+'Inverter-Batteries/All/'+product_state+'/'+product_city+'/';
}


function validate_code(){
	
	if($("#refcode").val()=="")
	{
	$("#ref_code_label").html("Please enter a valid reference code");
	$("#applycode").html("Apply");
	$("#applycode").css("background-color","#e02d29");
	$("#ref_code_label").css("color","red");
	$("#refcode").css({"border-bottom-color":"red","color":"#333"});
	}
	else
	{
		validate_refer_code();
	}
}


function updateResult(data)
{
	 //alert(data);
	 var refcode=$("#refcode").val();
	 
	 if(refcode!="")
	 {
		 if(data.indexOf("Applied Successfully")>=0)
		{
			//alert("success");
			$("#ref_code").html("<label id='ref_code_label' for='name' class='control-label' style='font-size: larger;color:#258e25;'>Applied Successfully</label><table><tbody><tr></tr><tr id='inp_ref_code'><td><input type='text' value='"+refcode+"' name='name' class='form-control' onkeyup='javascript:backspace_press(event.key)' id='refcode' placeholder='Referral Code' style='border-bottom-color: #258e25;color: #258e25;font-size: 20px;'></td><td><span class='label label-danger' style='font-size: 14px;background-color: #258e25;cursor: pointer;' id='applycode' onclick='javascript:validate_code()'>Applied</span></td></tr></tbody></table>");
			return true;
		}				
		else if(data.indexOf("expired")>=0)
		{
			$("#ref_code").html("<label id='ref_code_label' for='name' class='control-label' style='font-size: larger;color:#ff0000;'>Code is Expired</label><table><tbody><tr></tr><tr id='inp_ref_code'><td><input type='text' value='"+refcode+"' name='name' class='form-control' onkeyup='javascript:backspace_press(event.key)' id='refcode' placeholder='Referral Code' style='border-bottom-color: #ff0000;color: #ff0000;font-size: 20px;'></td><td><span class='label label-danger' style='font-size: 14px;background-color: #ff0000;cursor: pointer;' id='applycode' onclick='javascript:validate_code()'>Expired</span></td></tr></tbody></table>");
			return false;
		}		
		else if((data.indexOf("Invalid Code")>=0)|| (data.indexOf("not Available")>=0))
		{
			$("#ref_code").html("<label id='ref_code_label' for='name' class='control-label' style='font-size: larger;color:#ff0000;'> Invalid Code</label><table><tbody><tr></tr><tr id='inp_ref_code'><td><input type='text' value='"+refcode+"' name='name' class='form-control' onkeyup='javascript:backspace_press(event.key)' id='refcode' placeholder='Referral Code' style='border-bottom-color: #ff0000;font-size: 20px;color: red;'></td><td><span class='label label-danger' style='font-size: 14px;background-color: #ff0000;cursor: pointer;' id='applycode' onclick='javascript:validate_code()'>Invalid</span></td></tr></tbody></table>");
			return false;
		}
	 }
	 else
	 {
		 
	 }
	
}

function validate_refer_code(){
	var refcode=$("#refcode").val();
	var returnvalue="";
		$.ajax
		({					 
			url: "/bookbattery/servlet/Functions?hidWhatToDo=Validate_Ref_Code",
			type: 'GET',
			async: false,
			data:{refcode:refcode},
			success: function(data)
			{	
				//alert("data"+data);
				returnvalue=data;
				 //return(data);
				 updateResult(data);
			}
		});
		//alert(returnvalue);
		return returnvalue;
	}
$(document).ready(function () {

    var navListItems = $('div.setup-panel div a'),
            allWells = $('.setup-content'),
            allNextBtn = $('.nextBtn');
            allNextBtn2 = $('.nextBtn2');
            allNextBtn3 = $('.nextBtn3');
            allNextBtn4 = $('.nextBtn4');
			allNextBtn5 = $('.nextBtn5');
			var product_type_page=$("#product_type_page").val();

    allWells.hide();

    navListItems.click(function (e) {
        e.preventDefault();
        var $target = $($(this).attr('href')),
                $item = $(this);

        if (!$item.hasClass('disabled')) {
            navListItems.removeClass('btn-primary').addClass('btn-default');
            $item.addClass('btn-primary');
            allWells.hide();
            $target.show();
            $target.find('input:eq(0)').focus();
        }
    });

    allNextBtn.click(function(){
		var curStep = $(this).closest(".setup-content"),
		curStepBtn = curStep.attr("id"),
		nextStepWizard = $('div.setup-panel div a[href="#' + curStepBtn + '"]').parent().next().children("a"),
		curInputs = curStep.find("input[type='text'],input[type='tel'],select"),
		isValid = true;
		
			if (OrderProductOnline_step_2() == false)
			{
				return false;
			}
			else
			{
				nextStepWizard.removeAttr('disabled').trigger('click');
				//$("#order-form-area").select2();
				Activate_Model_Scroll();
			}
    });
	
    allNextBtn2.click(function(){
		var curStep = $(this).closest(".setup-content"),
		curStepBtn = curStep.attr("id"),
		nextStepWizard = $('div.setup-panel div a[href="#' + curStepBtn + '"]').parent().next().children("a"),
		curInputs = curStep.find("input[type='text'],input[type='tel'],select"),
		isValid = true;
		
		
		if (OrderProductOnline_step_3() == false)
		{
			return false;
		}
		else
		{
			nextStepWizard.removeAttr('disabled').trigger('click');
			setTimeout(function(){
				$('#select2-order-form-area-container').focus();
			}, 1000);
			
			$(".modal").animate({ scrollTop:300,},'slow');
		}
    });
	
	
	
	
	//alert("product_type_page"+product_type_page);
	if(product_type_page=="Bike Batteries" && showdelivery==true)
	{
		//alert("inside method");
		
			allNextBtn3.click(function(){
			var curStep = $(this).closest(".setup-content"),
			curStepBtn = curStep.attr("id"),
			nextStepWizard = $('div.setup-panel div a[href="#' + curStepBtn + '"]').parent().next().children("a"),
			curInputs = curStep.find("input[type='text'],input[type='tel'],select"),
			isValid = true;
			
			
			if (OrderProductOnline_step_4a() == false)
			{
				return false;
			}
			else
			{
				nextStepWizard.removeAttr('disabled').trigger('click');
			}
		});
		allNextBtn4.click(function(){
		var curStep = $(this).closest(".setup-content"),
		curStepBtn = curStep.attr("id"),
		nextStepWizard = $('div.setup-panel div a[href="#' + curStepBtn + '"]').parent().next().children("a"),
		curInputs = curStep.find("input[type='text'],input[type='tel'],select"),
		isValid = true;
		
		
		if (OrderProductOnline_step_4() == false)
		{
			return false;
		}
		else
		{
			nextStepWizard.removeAttr('disabled').trigger('click');
		}
    });
		allNextBtn5.click(function(){
		var curStep = $(this).closest(".setup-content"),
		curStepBtn = curStep.attr("id"),
		nextStepWizard = $('div.setup-panel div a[href="#' + curStepBtn + '"]').parent().next().children("a"),
		curInputs = curStep.find("input[type='text'],input[type='tel'],select"),
		isValid = true;
		
		
		if (OrderProductOnline_step_5() == false)
		{
			return false;
		}
		else
		{
			nextStepWizard.removeAttr('disabled').trigger('click');
		}
    });
	}
	else
	{
		allNextBtn3.click(function(){
		var curStep = $(this).closest(".setup-content"),
		curStepBtn = curStep.attr("id"),
		nextStepWizard = $('div.setup-panel div a[href="#' + curStepBtn + '"]').parent().next().children("a"),
		curInputs = curStep.find("input[type='text'],input[type='tel'],select"),
		isValid = true;
		
		
		if (OrderProductOnline_step_4() == false)
		{
			return false;
		}
		else
		{
			nextStepWizard.removeAttr('disabled').trigger('click');
		}
    });


	allNextBtn4.click(function(){
		var curStep = $(this).closest(".setup-content"),
		curStepBtn = curStep.attr("id"),
		nextStepWizard = $('div.setup-panel div a[href="#' + curStepBtn + '"]').parent().next().children("a"),
		curInputs = curStep.find("input[type='text'],input[type='tel'],select"),
		isValid = true;
		
		
		if (OrderProductOnline_step_5() == false)
		{
			return false;
		}
		else
		{
			nextStepWizard.removeAttr('disabled').trigger('click');
		}
    });
	}

    $('div.setup-panel div a.btn-primary').trigger('click');
});

function order_form_Back(id)
{
	$("#"+id+"").click();
	$("#order-form-area").select2("close");
}

function find_retaileres_avaliable()
{
	
	var order_form_areapincode = $("input[name='order-form-areapincode']:checked").val()
	if(order_form_areapincode=="Area")
	{
		if (selectValidation("","order-form-area","select") == false)
		{
			return false;
		}
		else
		{
			order_user_area =$("#order-form-area").val();
			order_user_pincode="";
		}		
	}
	else
	{
		if (pincodeValidation("","order-form-pincode","pincode") == false)
		{
			return false;
		}
		else
		{
			order_user_pincode =$("#order-form-pincode").val();
			order_user_area="";
		}		
	}

	
	var order_product_brand_URL = order_product_brand.replace(/ /g, "+");
	var order_user_area_URL =order_user_area;
	var order_user_pincode_URL =order_user_pincode;
	var order_state_URL = order_state.replace(/ /g, "+");
	var order_city_URL = order_city.replace(/ /g, "+");
	
	order_product_brand_URL=escape(order_product_brand_URL);
	order_user_area_URL=escape(order_user_area_URL);
	order_user_pincode_URL=escape(order_user_pincode_URL);
	order_state_URL=escape(order_state_URL);
	order_city_URL=escape(order_city_URL);
	
  	$("#Loading_bar").show();
	$.ajax
	({					 
		url: "/bookbattery/servlet/Functions_Service?hidWhatToDo=Find_Retailer",
		type: 'GET',
		data:{
				UPB:order_product_brand_URL,
				UAR:order_user_area_URL,
				UPN:order_user_pincode_URL,
				UST:order_state_URL,
				UCT:order_city_URL,
		},
		success: function(data)
		{	
			$("#Loading_bar").hide();
			current_order_details= current_order_details.replace(" . ", "</br>");
			var OrderLable="";
			var OrderMessage="";
			if(data.indexOf("Invalid Area or Pincode")>=0)
			{
				if(order_form_areapincode=="Area")
				{
					everyThingNotOk("order-form-area","Entered <b>Area</b> mayn’t belongs to Current Location -"+order_state+", "+order_city+".");
				}
				else
				{
					everyThingNotOk("order-form-pincode","Entered <b>Zip Code</b> mayn’t belongs to Current Location -"+order_state+", "+order_city+".");
				}
				$("#order-form-ordernow-btn-2").addClass("btn").prop('disabled', true);
				return false ;
			}
			else if(data.indexOf("Sorry")>=0)
			{
				$('#retailer_response_error').show();
				$("#order-form-ordernow-btn-2").addClass("btn").prop('disabled', true);
				Tawk_API.addEvent('Selected-No-Retailer-Area-or-Pincode', {
									'Name'    : ''+order_username+'',
									'Mobile'  : ''+order_usermobile_number+'',
									'Email' :''+order_useremail+'',
									'Address' :''+order_user_address+'',
									'Area' :''+order_user_area_URL+'',
									'Pincode' :''+order_user_pincode_URL+'',
								}, function(error){});
					return false ;
			}
			else
			{
				$("#order-form-ordernow-btn-2").removeClass("btn").prop('disabled', false);
				everyThingOk("order-form-area-error");
				everyThingOk("order-form-pincode-error");
				$("#order-form-area").select2("close")
				$('#retailer_response_error').hide();
				$('#order-form-ordernow-btn-2').focus();
			}
		}	
	});
}

function scroll_on_area()
{
	//$(".modal").animate({ scrollTop:300,},'slow');
}

//########## Back Up code to enable Buy now button
//07-11-2016 this code is to enable buy now button in Single product pages-- By Sai Krishna Daddala
$(document).ready(function() {
	setTimeout(function(){
		$("[type='button'][onclick='javascript:OrderProductOnline()']").removeClass("btn").prop('disabled', false).html(" <span> Buy Now</span>");
	}, 10000);
});
//########## Back Up code to enable Buy now button


$('#battery-order-form').on('hidden.bs.modal', function () {
   $("#order-form-area").select2("close");
})



function fn_Add_Product_to_Cart()
{
	
	order_product_qty=$("#product_qty").val();
	var order_product_image=$("#product_image").val();
	order_battery_buy_type = $("input[name='battery_buy_type']:checked").val()

	var cart;
	var cartValue = localStorage.getItem("cart");
	var cartObj = JSON.parse( cartValue );
	//var cartObj = getCookie('cart');
	var Cart_Product_Type;
	var Cart_Product_Make;
	var Cart_Product_Model;
	var Cart_Product_Price;
	var Cart_Product_Obrp;
	var Cart_Product_Quantity;
	var Cart_Product_State;
	var Cart_Product_City;
	var Cart_Product_Return;
	var Cart_Product_Grand_Total_Price;


	Cart_Product_Type=order_product_type;
	Cart_Product_Make=order_product_brand;
	Cart_Product_Model=order_product_model;
	Cart_Product_Price=order_with_old_battery;
	Cart_Product_Obrp=order_without_old_battery;
	Cart_Product_Quantity=order_product_qty;
	Cart_Product_State=order_state;
	Cart_Product_City=order_city;
	Cart_Product_Return=order_battery_buy_type;
	Cart_Product_Image=order_product_image;
	//Cart_Product_Grand_Total_Price 	
	cart = [{ "Cart_Product_Type": Cart_Product_Type, "Cart_Product_Make": Cart_Product_Make, "Cart_Product_Model": Cart_Product_Model, "Cart_Product_Price": Cart_Product_Price, "Cart_Product_Obrp": Cart_Product_Obrp, "Cart_Product_Quantity": Cart_Product_Quantity, "Cart_Product_State": Cart_Product_State, "Cart_Product_City": Cart_Product_City, "Cart_Product_Return": Cart_Product_Return , "Cart_Product_Image": Cart_Product_Image }];
	
	if (cartObj=="null"|| cartObj=="")
	{
	}
	else
	{
		//cart=cart.concat(cartObj);
	}
	
	//setCookie("cart",cart);

	var jsonStr = JSON.stringify( cart );
	localStorage.setItem( "cart", jsonStr );
	//now the cart is {"item":"Product 1","price":35.50,"qty":2}
	var cartValue = localStorage.getItem( "cart" );
	var cartObj = JSON.parse( cartValue );
	console.log( cart );
	console.log( cartObj );
}