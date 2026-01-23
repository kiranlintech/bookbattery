
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
%>
<jsp:include page = "../storeheader.jsp" />
<jsp:include page = "../storeleftmenu.jsp" />
 <section class="content">
	<form name="orderservice">
        <div class="container-fluid">
        
            <!-- Advanced Select -->
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="header bg-light-blue">
                            <h2>
                                ORDER HEALTH CHECK SERVICE 
                            </h2>
                        </div>
                        <div class="body">
                            <div class="row clearfix">
								<div class="col-md-3">
									<p>
                                        <b>Battery Type&nbsp;<font color="red">*</font></b>
                                    </p>
                                    <select class="form-control show-tick" id="battery_type" name="battery_type" data-live-search="true">
												<option value="-1">Select Battery Type</option>
												<option value="Car Batteries">Car Battery</option>
 												<option value="Bike Batteries">Bike Battery</option>
 												<option value="Inverter Batteries">Inverter Battery</option>
                                    </select> 
									 <div id='battery_type-error'style="display:none;"></div>
                                </div>
								 
 									<div id="carbatteries">
										<div class="col-md-3">
											<p>
												<b>Vehicle Make&nbsp;<font color="red">*</font></b>
											</p>
											 <select class="form-control show-tick yes" id="vehicle_make">
											 <option value="">Choose your option</option>
											 </select>
											 <div id='vehicle_make-error'style="display:none;"></div>
										</div>
										<div class="col-md-3">
											<p>
												<b>Vehicle Model&nbsp;<font color="red">*</font></b>
											</p>
											 <select class="form-control show-tick yes" id="vehicle_model">
											 <option value="">Choose your option</option>
											 </select>
											 <div id='vehicle_model-error'style="display:none;"></div>
										</div>
									</div>
									<!---------------Inverter Batteries ------------------>
 									<div id="inverterbatteries" style="display:none">
										<div class="col-md-3">
											<p>
												<b>Inverter&nbsp;Capacity<font color="red">*</font></b>
											</p>
											 <select class="form-control show-tick yes" id="batt_capacity">
											 <option value="">Choose your option</option>
											 </select>
											 <div id='batt_capacity-error'style="display:none;"></div>
										</div>
									</div>
								 </div>
									<!---------------------Inverter Batteries Ends ----------------------->
									<!---------------------Pincode Starts ----------------------->
								   <div class="row clearfix">
									 <div class="" id="orderform_state">
										<div class="col-md-3">
											<p>
												<b>State&nbsp;<font color="red">*</font></b>
											</p>
											 <select class="form-control show-tick yes" id="state">
											 <option value="">Choose your option</option>
											 </select>
											 <div id='state-error'style="display:none;"></div>
										</div>
										<div class="col-md-3">
											<p>
												<b>City&nbsp;<font color="red">*</font></b>
											</p>
											 <select class="form-control show-tick yes" id="city">
											 <option value="">Choose your option</option>
											 </select>
											 <div id='city-error'style="display:none;"></div>
										</div>
										<div class="col-md-3">
											<p>
												<b>Area&nbsp;<font color="red">*</font></b>
											</p>
											 <select class="form-control show-tick yes" id="area">
											 <option value="">Choose your option</option>
											 </select>
											 <div id='area-error'style="display:none;"></div>
											 <small id="emailHelp2" class="form-text text-muted pull-right"><a href="javascript:DisplayPincode()">Order&nbsp;With&nbsp;Pincode.</a></small>
										</div>
										<div class="col-md-3">
											<br/>
											 <button type="button" class="btn bg-blue m-t-15 waves-effect" onclick="GetServicedetails()"><i class="material-icons">search</i>&nbsp;<span>FIND SERVICE DETAILS</span></button>
										</div> 
										 
								 	 </div>
									<!---------------------Pincode Ends ----------------------->
									<!---------------------Pincode Starts ----------------------->
									 <div class="" id="orderform_pincode" style="display:none;">
										
										
										<div class="col-md-6 col-lg-3 col-xs-12">
											<label for="pincode">Pincode<font color="red">*</font></label>
											<div class="form-group">
												<div class="form-line">
													<input type="text" id="pincode" class="form-control" placeholder="Eg: 560066">
												</div>
											</div>
											<small id="emailHelp2" class="form-text text-muted pull-right"><a href="javascript:DisplayStateCity()">Order&nbsp;With&nbsp;State,City,Area.</a></small>
										</div> 
										
										<div class="col-md-6 col-lg-3 col-xs-12">
										<br/>
												<button type="button" class="btn bg-blue waves-effect" onclick="GetServicedetails()">
													<i class="material-icons">search</i>
													<span>FIND SERVICE DETAILS</span>
												</button> 
										</div> 
										
									 </div>
									<!---------------------Pincode Ends ----------------------->
									</div>
								</div>
                           </div>
                    </div>
                </div>
            </div>
            <!-- #END# Advanced Select -->
        </div>
    </form>
    </section>

<script src="http://materializecss.com/bin/materialize.js" />

<input type="hidden" name="store_id" id="store_id" value="<%=strStoreId%>">
<input type="hidden" name="store_name" id="store_name" value="<%=strStoreName%>">
<input type="hidden" name="brandkeyword" id='brandkeyword' value="Common">

<jsp:include page = "../storefooter.jsp" />		

<script type="text/javascript">
$("#battery_type").change(function(){   // 1st
    // do your code here
    // When your element is already rendered
	var batterytype = $('#battery_type').val();
	//alert(batterytype);
	if(batterytype=="Inverter Batteries"){
		$.ajax({
			type: "GET",
			url: "../../../servlet/BatteryHome?hidWhatToDo=getbatterycapacity",
			data: {batterytype: batterytype, batterytype: batterytype },
			success: function(data){
				$("#batt_capacity").html(data)
			}
		});
		$("#inverterbatteries").show();
		$("#carbatteries").hide();
		
	} else {
		$.ajax({
			type: "GET",
			url: "../../../servlet/BatteryHome?hidWhatToDo=getvehiclename",
			data: {batterytype: batterytype },
			success: function(data){
				 
				$("#vehicle_make").html(data);
					 
			}
		});
		$("#inverterbatteries").hide();
		$("#carbatteries").show();
	}
}); 

$("#vehicle_make").change(function(){   // 1st
    // do your code here
    // When your element is already rendered
 var batterytype = $('#battery_type').val();	
var vehiclemake = $('#vehicle_make').val();	
	//alert(batterytype);

 $.ajax({
		type: "GET",
		url: "../../../servlet/BatteryHome?hidWhatToDo=getvehicle_model",
		data: {vehiclename: vehiclemake, batterytype: batterytype},
		//data: {batterytype: $category.val() },
		success: function(data){
			 
			$("#vehicle_model").html(data);
 			 
		}
	});
});  

$("#batt_capacity").change(function(){   // 1st
    // do your code here
    // When your element is already rendered
var batterytype = $('#battery_type').val();	
var vehiclemake = $('#vehicle_make').val();	
var vehiclemodel = $('#vehicle_model').val();	
var keyword = $('#brandkeyword').val();	
$('#state').val('');
if(batterytype==""){
	 	
		$("#state").html("<option value=''>Choose your option</option>");
	 
} else {
	 
	    $("#state").html("<option value=''>Choose your option</option><option value='Karnataka'>Karnataka</option>");
}

  /*$.ajax({
		type: "GET",
		url: "../../../servlet/BatteryHome?hidWhatToDo=getstate",
		data: {},
		success: function(data){
				if(data.indexOf("defaultss")>=0)
				{
					$("#state").html(data)
 				}
				else
				{
					$("#state").html(data)
 				}
			}
 	});*/
}); 
	
$("#vehicle_model1").change(function(){   // 1st
    // do your code here
    // When your element is already rendered
var batterytype = $('#battery_type').val();	
var vehiclemake = $('#vehicle_make').val();	
var vehiclemodel = $('#vehicle_model').val();	
   $.ajax({
		type: "GET",
		url: "../../../servlet/BatteryHome?hidWhatToDo=getstate",
		data: {},
		success: function(data){
				if(data.indexOf("defaultss")>=0)
				{
					$("#state").html(data)
 				}
				else
				{
					$("#state").html(data)
 				}
			}
		});
	}); 
$("#vehicle_model").change(function(){   // 1st
    // do your code here
    // When your element is already rendered
var batterytype = $('#battery_type').val();	
//alert(batterytype);
 $('#state').val('');
 if(batterytype==""){
	 	
		$("#state").html("<option value=''>Choose your option</option>");
	 
 } else {
	 
 	    $("#state").html("<option value=''>Choose your option</option><option value='Karnataka'>Karnataka</option>");
 }

   /*$.ajax({
		type: "GET",
		url: "../../../servlet/BatteryHome?hidWhatToDo=getstate",
		data: {},
		success: function(data){
			alert(data);
				if(data.indexOf("defaultss")>=0)
				{
					$("#state").html("<option value=''>Choose your option</option>")
 				}
				else
				{
					$("#state").html(data)
 				}
			}
		});*/
	}); 
$("#state").change(function(){   // 1st
    // do your code here
    // When your element is already rendered
var batterytype = $('#battery_type').val();	
var vehiclemake = $('#vehicle_make').val();	
var vehiclemodel = $('#vehicle_model').val();	
var state = $('#state').val();	
	$('#city').val('');
	 if(state==""){
			
			$("#city").html("<option value=''>Choose your option</option>");
		 
	 } else {
		 
			$("#city").html("<option value=''>Choose your option</option><option value='Bangalore'>Bangalore</option>");
	 }
});	 
 /*$.ajax({
			type: "GET",
			url: "../../../servlet/BatteryHome?hidWhatToDo=getcity",
			data: {state: state },
			success: function(data){
				if(data.indexOf("defaultss")>=0)
				{
				$("#city").html(data)
 				}
				else
				{
				 $("#city").html(data)
 				}
			}
		});
	}); */
$("#city").change(function(){   // 1st
    // do your code here
    // When your element is already rendered
var batterytype = $('#battery_type').val();	
var vehiclemake = $('#vehicle_make').val();	
var vehiclemodel = $('#vehicle_model').val();	
var state = $('#state').val();	
var city = $('#city').val();	
 $.ajax({
			type: "GET",
			url: "../../../servlet/BatteryHome?hidWhatToDo=getarea",
			data: {state: state, city: city },
			success: function(data){
				if(data.indexOf("defaultss")>=0)
				{
				$("#area").html(data)
 				}
				else
				{
				 $("#area").html(data)
 				}
			}
		});
}); 

function DisplayPincode(){
	
	//alert("DisplayPincode");
	$('#orderform_pincode').show();
	$('#orderform_state').hide();
}
function DisplayStateCity(){
	
	//alert("DisplayStateCity");
	$('#orderform_pincode').hide();
	$('#orderform_state').show();

}


function GetServicedetails(){
	//alert("inside function");
	var input_type;
	var service_type = "Health Check";
	var batterytype = $('#battery_type').val();	
	if (selectValidation("","battery_type",input_type) == false)
		{
			return;
			
		}  
	if(batterytype=="Inverter Batteries")	{
		
	var vehiclemake="";
	var vehiclemodel="";
	var batt_capacity = $('#batt_capacity').val();	
	if (selectValidation("","batt_capacity",input_type) == false)
		{
			return;
			
		} 
			
	} else {
		
		
			var vehiclemake = $('#vehicle_make').val();	
		if (selectValidation("","vehicle_make",input_type) == false)
			{
				return;
				
			} 
		var vehiclemodel = $('#vehicle_model').val();	
		if (selectValidation("","vehicle_model",input_type) == false)
			{
				return;
				
			}
		var batt_capacity ="";	
	}
	 
	var pincode = $('#pincode').val();	
 	if(pincode==""){
		
		var state = $('#state').val();	
		if (selectValidation("","state",input_type) == false)
		{
			return;
			
		}
		var city = $('#city').val();
		if (selectValidation("","city",input_type) == false)
		{
			return;
			
		}
		var area = $('#area').val();
		if (selectValidation("","area",input_type) == false)
		{
			return;
			
		}
		var pincode ="";
		
	} else {
		var state = "";
		var city = "";
		var area = "";
		var pincode = $('#pincode').val();
		if (pincodeValidation("","pincode","pincode") == false)
		{
			return;
			
		}
	}
	
	/*alert(state);
	alert(city);
	alert(area);
	alert(pincode);
 	alert(batt_capacity);
 	alert(batterytype);
	alert(vehiclemodel);
	alert(vehiclemake);*/
 	document.orderservice.method="post";
	document.orderservice.action="../../../servlet/BatterystoreServiceDetails?hidWhatToDo=getservicedetails&batterytype="+batterytype+"&vehiclename="+vehiclemake+"&vehiclemodel="+vehiclemodel+"&batt_capacity="+batt_capacity+"&state="+state+"&city="+city+"&area="+area+"&pincode="+pincode+"&service_type="+service_type;
	//alert(document.orderbattery.action);
	document.orderservice.submit();
}
</script>


				