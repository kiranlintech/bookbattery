<html lang="en">
<head>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.*,com.ngit.javabean.loglevel.LogLevel,javax.servlet.ServletContext,java.util.Properties,java.io.FileInputStream"%>
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

String order_number = (String) request.getAttribute("order_number");
String consumer_name = (String) request.getAttribute("consumer_name");
String mobile_number = (String) request.getAttribute("mobile_number");
String battery_type = (String) request.getAttribute("battery_type");
String vehicle_make = (String) request.getAttribute("vehicle_make");
String vehicle_model = (String) request.getAttribute("vehicle_model");
String capacity = (String) request.getAttribute("capacity");
String quantity = (String) request.getAttribute("quantity");
String terminals_src_spc = (String) request.getAttribute("terminals_src_spc");
String battery_cleanup_cloth = (String) request.getAttribute("battery_cleanup_cloth");
String battery_good = (String) request.getAttribute("battery_good");
String battery_not_charge = (String) request.getAttribute("battery_not_charge");
String battery_overcharge = (String) request.getAttribute("battery_overcharge");
String battery_dead = (String) request.getAttribute("battery_dead");
String volts_before_car_started = (String) request.getAttribute("volts_before_car_started");
String volts_after_car_started = (String) request.getAttribute("volts_after_car_started");
String volts_car_cranking = (String) request.getAttribute("volts_car_cranking");
String volts_car_acceleration = (String) request.getAttribute("volts_car_acceleration");
String battery_needs_charging = (String) request.getAttribute("battery_needs_charging");
String volts_power_off = (String) request.getAttribute("volts_power_off");
String volts_power_on = (String) request.getAttribute("volts_power_on");
String water_gravity = (String) request.getAttribute("water_gravity");
String warranty_expiry_date = (String) request.getAttribute("warranty_expiry_date");
String service_eng_mobile = (String) request.getAttribute("service_eng_mobile");

consumer_name=consumer_name.replace("_"," ");
battery_type=battery_type.replace("_"," ");
vehicle_make=vehicle_make.replace("_"," ");
vehicle_model=vehicle_model.replace("_"," ");
capacity=capacity.replace("_"," ");

%>
	<title>Customer FeedBack</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<link rel="stylesheet" href="<%=baseurldirectory%>css/bootstrap.min.css">

<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="<%=baseurldirectory%>cusfeedback/css/util.css">
	<link rel="stylesheet" type="text/css" href="<%=baseurldirectory%>cusfeedback/css/main.css">
<!--===============================================================================================-->
<!--===============================================================================================-->
	<link rel="icon" type="image/png" href="<%=baseurldirectory%>images/favicon.png"/>
	<link rel="stylesheet" href="/bookbattery/dev/includes/css/font-awesome.css">
	<link rel="stylesheet" href="/bookbattery/css/sky-forms.css">
	<link rel="stylesheet" href="/bookbattery/css/sky-forms-orange.css">
</head>



<body>

<!-- Button trigger modal -->
<!-- Modal -->
<div class="modal col-md-12 col-sm-12 col-xs-12 modal" id="feedback_popup" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
  aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header" style="background: #69b044;color: #ffff;">
       <button type="button" class="close closepopup" data-dismiss="modal" aria-label="Close" style="color: #ffff;opacity: unset;">
          <span aria-hidden="true">&times;</span>
        </button>
	   <h5 class="modal-title" style="font-size: 19px;" id="exampleModalLabel">BookBattery - Customer FeedBack</h5>
      </div>
      <div class="modal-body">
      <div id="body-text" style="color: #495057;font-size: 16px;text-align: -webkit-center;">
      </div>
      </div>
      <div class="modal-footer"style="justify-content: center;text-align: -webkit-center;">
        <button type="button" id="Ok_button" class="closepopup btn btn-success" style="color: rgb(255, 255, 255);font-weight: bold;background-color: rgba(249, 116, 6, 0.76);font-size: medium;">OK</button>
      </div>
    </div>
  </div>
</div>
	<div class="container-contact100">

		<div class="wrap-contact100" style="background-color: cornsilk;">
			<form class="contact100-form validate-form sky-form">
			  
				<span class="contact100-form-title" style="padding-bottom: 5px;">
				<a class="logo" title="BookBattery Home" href="<%=publicUrl%><%=baseurldirectory%>"><img alt="BookBattery Logo" src="<%=publicUrl%><%=baseurldirectory%>images/BookBattery_logo.png"></a>
				</span>
				<span class="contact100-form-title" style="padding-bottom: 18px;font-size: 20px;">
				Send Us Your FeedBack
				</span>
				<p>Order Number</p>
				<div class="wrap-input100 validate-input" data-validate="Name is required">
					<input class="input100" type="text" readonly="readonly" name='order_number' id="order_number" value="<%=order_number%>" placeholder="Full Name">
					<span class="focus-input100-1"></span>
					<span class="focus-input100-2"></span>
				</div>
				<p>Name</p>				
				<div class="wrap-input100 validate-input" data-validate="Name is required">
					<input class="input100" type="text" id="consumer_name" name="consumer_name" readonly="readonly" value="<%=consumer_name%>" placeholder="Full Name">
					<span class="focus-input100-1"></span>
					<span class="focus-input100-2"></span>
				</div>	
				<p>Mobile Number</p>						
				<div class="wrap-input100 validate-input" data-validate="Name is required">
					<input class="input100" type="text" id="mobile_number" name="mobile_number" readonly="readonly" value="<%=mobile_number%>" placeholder="Full Name">
					<span class="focus-input100-1"></span>
					<span class="focus-input100-2"></span>
				</div>			
				<p>Battery Type</p>					
				<div class="wrap-input100 validate-input" data-validate="Name is required">
					<input class="input100" type="text" id="battery_type" name="battery_type" readonly="readonly" value="<%=battery_type%>" placeholder="Battery Type">
					<span class="focus-input100-1"></span>
					<span class="focus-input100-2"></span>
				</div>
				
				<%
				if(battery_type.equals("Car Batteries")|| battery_type.equals("Car_Batteries")|| battery_type=="Car Batteries" || battery_type.equals("Bike Batteries")|| battery_type.equals("Bike_Batteries")|| battery_type=="Bike Batteries")
				{
				%>
				<p>Car Make</p>		
				<div class="wrap-input100 validate-input" data-validate="Name is required">
					<input class="input100" type="text" id="vehicle_make" name="vehicle_make" readonly="readonly" value="<%=vehicle_make.replace("_"," ")%>" placeholder="Full Name">
					<span class="focus-input100-1"></span>
					<span class="focus-input100-2"></span>
				</div>	
				<p>Car Model</p>					
				<div class="wrap-input100 validate-input" data-validate="Name is required">
					<input class="input100" type="text" id="vehicle_model" name="vehicle_model" readonly="readonly" value="<%=vehicle_model.replace("_"," ")%>" placeholder="Full Name">
					<span class="focus-input100-1"></span>
					<span class="focus-input100-2"></span>
				</div>	
				<p>Volts Before Car Starts</p>				
				<div class="wrap-input100 validate-input" data-validate="Name is required">
					<input class="input100" type="text" id="volts_before_car_started" name="volts_before_car_started" readonly="readonly" value="<%=volts_before_car_started.replace("_"," ")%>" placeholder="12.0">
					<span class="focus-input100-1"></span>
					<span class="focus-input100-2"></span>
				</div>	
				<p>Volts at Car Crancking</p>					
				<div class="wrap-input100 validate-input" data-validate="Name is required">
					<input class="input100" type="text" id="volts_car_cranking" name="volts_car_cranking" readonly="readonly" value="<%=volts_car_cranking.replace("_"," ")%>" placeholder="14.0">
					<span class="focus-input100-1"></span>
					<span class="focus-input100-2"></span>
				</div>				
				<p>Volts Before Car Starts/Idle</p>				
				<div class="wrap-input100 validate-input" data-validate="Name is required">
					<input class="input100" type="text" id="volts_after_car_started" name="volts_after_car_started" readonly="readonly" value="<%=volts_after_car_started.replace("_"," ")%>" placeholder="12.0">
					<span class="focus-input100-1"></span>
					<span class="focus-input100-2"></span>
				</div>	
				<p>Volts at Car Acceleration</p>					
				<div class="wrap-input100 validate-input" data-validate="Name is required">
					<input class="input100" type="text" id="volts_car_acceleration" name="volts_car_acceleration" readonly="readonly" value="<%=volts_car_acceleration.replace("_"," ")%>" placeholder="14.0">
					<span class="focus-input100-1"></span>
					<span class="focus-input100-2"></span>
				</div>
				<%
				}
				else
				{
				%>
					<p>Inverter Battery Capacity</p>	
					<div class="wrap-input100 validate-input" data-validate="Name is required">
						<input class="input100" type="text" id="capacity" name="capacity" readonly="readonly" value="<%=capacity.replace("_"," ")%>" placeholder="Full Name">
						<span class="focus-input100-1"></span>
						<span class="focus-input100-2"></span>
					</div>
					<p>Inverter Battery Quantity</p>					
					<div class="wrap-input100 validate-input" data-validate="Name is required">
						<input class="input100" type="text" id="quantity" name="quantity" readonly="readonly" value="<%=quantity%>" placeholder="Full Name">
						<span class="focus-input100-1"></span>
						<span class="focus-input100-2"></span>
					</div>					
					<p>Volts When Power is OFF</p>	
					<div class="wrap-input100 validate-input" data-validate="Name is required">
						<input class="input100" type="text" id="volts_power_off" name="volts_power_off" readonly="readonly" value="<%=volts_power_off.replace("_"," ")%>" placeholder="Full Name">
						<span class="focus-input100-1"></span>
						<span class="focus-input100-2"></span>
					</div>
					<p>Volts When Power is ON</p>					
					<div class="wrap-input100 validate-input" data-validate="Name is required">
						<input class="input100" type="text" id="volts_power_on" name="volts_power_on" readonly="readonly" value="<%=volts_power_on%>" placeholder="Full Name">
						<span class="focus-input100-1"></span>
						<span class="focus-input100-2"></span>
					</div>					
					<p>Whether water gravity levels are checked?</p>					
					<div class="wrap-input100 validate-input" data-validate="Name is required">
						<input class="input100" type="text" id="water_gravity" name="water_gravity" readonly="readonly" value="<%=water_gravity%>" placeholder="Full Name">
						<span class="focus-input100-1"></span>
						<span class="focus-input100-2"></span>
					</div>
				<%
				}

				if(warranty_expiry_date.equals("null")|| warranty_expiry_date.equals("Null")|| warranty_expiry_date=="null" || warranty_expiry_date.equals("")|| warranty_expiry_date.equals("NULL")|| warranty_expiry_date=="NULL")
				{
				%>
					
				<%	
				}
				else
				{
				%>
					<p>Warranty Expiry Date?</p>
					<div class="wrap-input100 validate-input" data-validate = "Valid email is required: ex@abc.xyz">
					<input class="input100" type="text" id="warranty_expiry_date" name="warranty_expiry_date" readonly="readonly" value="<%=warranty_expiry_date.replace("_"," ")%>" placeholder="Yes">
					</div>
				<%	
				}
				%>
				<p>Are terminals salt rust cleaned up with sand paper/cloth and greasing?</p>
				<div class="wrap-input100 validate-input" data-validate = "Valid email is required: ex@abc.xyz">
				<input class="input100" type="text" id="terminals_src_spc" name="terminals_src_spc" readonly="readonly" value="<%=terminals_src_spc.replace("_"," ")%>" placeholder="Yes">
				</div>				
				<p>Whether total battery cleaned with cloth?</p>
				<div class="wrap-input100 validate-input" data-validate = "Valid email is required: ex@abc.xyz">				
				<input class="input100" type="text" id="battery_cleanup_cloth" name="battery_cleanup_cloth" readonly="readonly" value="<%=battery_cleanup_cloth.replace("_"," ")%>" placeholder="Yes">
				</div>
				<p>Whether battery is working good after service?</p>		
				<div class="wrap-input100 validate-input" data-validate = "Valid email is required: ex@abc.xyz">				
				<input class="input100" type="text" id="battery_good" name="battery_good" readonly="readonly" value="<%=battery_good.replace("_"," ")%>" placeholder="Yes">
				</div>
				<p>Whether battery is charging properly after service?</p>		
				<div class="wrap-input100 validate-input" data-validate = "Valid email is required: ex@abc.xyz">				
				<input class="input100" type="text" id="battery_not_charge" name="battery_not_charge" readonly="readonly" value="<%=battery_not_charge.replace("_"," ")%>" placeholder="Yes">
				</div>
				<p>Whether battery is overcharging after service?</p>						
				<div class="wrap-input100 validate-input" data-validate = "Valid email is required: ex@abc.xyz">				
				<input class="input100" type="text" id="battery_overcharge" name="battery_overcharge" readonly="readonly" value="<%=battery_overcharge.replace("_"," ")%>" placeholder="Yes">
				</div>			
				<p>Whether battery is Dead Or replaced with new one?</p>		
				<div class="wrap-input100 validate-input" data-validate = "Valid email is required: ex@abc.xyz">				
				<input class="input100" type="text" id="battery_dead" name="battery_dead" readonly="readonly" value="<%=battery_dead.replace("_"," ")%>" placeholder="Yes">
				</div>
				<p>Whether battery needs charging at the shop?</p>		
				<div class="wrap-input100 validate-input" data-validate = "Valid email is required: ex@abc.xyz">				
				<input class="input100" type="text" id="battery_needs_charging" name="battery_needs_charging" readonly="readonly" value="<%=battery_needs_charging.replace("_"," ")%>" placeholder="Yes">
				</div>
				<p>Please Provide Your Valuable Rating</p>
				<div id="rating_div" class="wrap-input100 validate-input" data-validate = "Valid email is required: ex@abc.xyz" style="padding: 13px;line-height: 30px;">
							
						<div class="row rating" style="float:left">
							<input type="radio" name="quality" value="5" id="quality-5" >
							<label for="quality-5"><i class="fa fa-star fa-6" ></i></label>
							<input type="radio" name="quality" value="4" id="quality-4" >
							<label for="quality-4"><i class="fa fa-star fa-6"></i></label>
							<input type="radio" name="quality" value="3" id="quality-3" >
							<label for="quality-3"><i class="fa fa-star fa-6"></i></label>
							<input type="radio" name="quality" value="2" id="quality-2" >
							<label for="quality-2"><i class="fa fa-star fa-6"></i></label>
							<input type="radio" name="quality" value="1" id="quality-1" >
							<label for="quality-1"><i class="fa fa-star fa-6"></i></label>
						</div>	
					<br><br><div class="row" id="ratingmsg">
					</div>	
					</div>	
	
				<div class="wrap-input100 validate-input" data-validate = "Please enter valid message with more than 10 characters">
					<textarea class="input100" id="message" name="message" placeholder="Please enter your comments to improve our service"></textarea>
					<span class="focus-input100-1"></span>
					<span class="focus-input100-2"></span>
				</div>
				<div class="row" id="message_error" name="message_error">
				<div class="container-contact100-form-btn">
					<input type="button" id="submit" value="submit" class="contact100-form-btn">
				</div>
			</form>
		</div>
	</div>
<!-- Button trigger modal -->


	 <input type="hidden" name="service_eng_mobile" id='service_eng_mobile' value="<%=service_eng_mobile%>">
     
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!--===============================================================================================-->
	<script src="<%=baseurldirectory%>cusfeedback/vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
	<script src="<%=baseurldirectory%>cusfeedback/vendor/animsition/js/animsition.min.js"></script>
<!--===============================================================================================-->
	<script src="<%=baseurldirectory%>cusfeedback/vendor/bootstrap/js/popper.js"></script>
	<script src="<%=baseurldirectory%>cusfeedback/vendor/bootstrap/js/bootstrap.min.js"></script>

	<script src="<%=baseurldirectory%>cusfeedback/js/main.js?v=2"></script>
	<script src="<%=baseurldirectory%>js/popgreyout.js"></script>
	
	<script type="text/javascript">
	
	
	</script>

</body>
</html>
