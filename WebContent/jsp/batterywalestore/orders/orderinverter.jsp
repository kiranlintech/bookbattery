
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
		<form name="orderinverter">

        <div class="container-fluid">
            <!-- Advanced Select -->
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="header bg-orange">
                            <h2>
                                ORDER A INVERTER 
                            </h2>
                        </div>
                        <div class="body">
                            <div class="row clearfix">
								<div class="col-md-3">
                                    <p>
                                        <b>Inverter Type&nbsp;<font color="red">*</font></b>
                                    </p>
                                    <select class="form-control show-tick" id="inverter_type" name="inverter_type" data-live-search="true">
												<option value="-1">Select Inverter Type</option>
												<option style="background:#FFF" value='Inverter'>Inverter</option>
                                    </select> 
									 <div id='inverter_type-error'style="display:none;"></div>
                                </div>
								 <!---------------Inverter Batteries ------------------>
										<div class="col-md-3">
											<p>
												<b>Inverter&nbsp;Capacity<font color="red">*</font></b>
											</p>
											 <!--select class="form-control show-tick yes" data-trigger="focus" data-container="body" data-toggle="popover" data-placement="top" title="Details" data-content=" " id="inverter_capacity"-->
											 <select class="form-control show-tick yes" id="inverter_capacity">
											 <option value="">Choose your option</option>
											 </select>
											 <div id='inverter_capacity-error'style="display:none;"></div>
												 
											 </div>
										
										<div class="col-md-3">
											<p>
												<b>Inverter Brand&nbsp;<font color="red">*</font></b>
											</p>
											 <select class="form-control show-tick yes" id="inverter_name">
											 <option value="">Choose your option</option>
											 </select>
											 <div id='inverter_name-error'style="display:none;"></div>
										</div>
									</div>
									<!---------------------Inverter Batteries Ends ----------------------->
									<!---------------------Pincode Starts ----------------------->
									 <div class="row clearfix" id="orderform_state">
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
											 <button type="button" class="btn bg-orange waves-effect"  onclick="GetDetails()">
													<i class="material-icons">search</i>
													<span>FIND INVERTER DETAILS</span>
												</button> 
										</div> 
										 
								 	 </div>
									<!---------------------Pincode Ends ----------------------->
									<!---------------------Pincode Starts ----------------------->
									 <div class="row clearfix" id="orderform_pincode" style="display:none;">
										
										<div class="col-md-6 col-lg-3 col-xs-12">
									 
										</div>

										<div class="col-md-6 col-lg-3 col-xs-12">
									 
										</div> 
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
												<button type="button" class="btn bg-indigo waves-effect" onclick="GetDetails()">
													<i class="material-icons">search</i>
													<span>FIND BATTERY DETAILS</span>
												</button>  
										</div> 
										
									 </div>
									<!---------------------Pincode Ends ----------------------->
                            
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
$("#inverter_type").change(function(){   // 1st
    // do your code here
    // When your element is already rendered
	var invertertype = $("#inverter_type").val();
   $.ajax({
		type: "GET",
		url: "../../../servlet/OperatorInvertersDetails?hidWhatToDo=getinvertercapacity",
		data: {invertertype: invertertype },
		success: function(data)
		{	 
 				$("#inverter_capacity").html(data)
				 
		}
	});
}); 

$("#inverter_capacity").change(function(){   // 1st
    // do your code here
    // When your element is already rendered
var keyword = "Common";
	$.ajax({
		type: "GET",
		url: "../../../servlet/OperatorInvertersDetails?hidWhatToDo=getinverterbrand",
		data: {keyword:keyword},
		success: function(data){
		 
			$("#inverter_name").html(data)
			 
		}
	});
}); 

$("#inverter_name").change(function(){   // 1st
    // do your code here
    // When your element is already rendered
	$('#state').val('');
	 if(inverter_name==""){
		 	
			$("#state").html("<option value=''>Choose your option</option>");
		 
	 } else {
		 
	 	    $("#state").html("<option value=''>Choose your option</option><option value='Karnataka'>Karnataka</option>");
	 }
 /*$.ajax({
		type: "GET",
		url: "../../../servlet/BatteryHome?hidWhatToDo=getstate",
		data: {},
		success: function(data){
			 
					$("#state").html(data)
 				 
			}
		});*/
}); 

  
$("#state").change(function(){   // 1st
    // do your code here
    // When your element is already rendered
var batterytype = $('#battery_type').val();	
var state = $('#state').val();	
$('#city').val('');
if(state==""){
		
		$("#city").html("<option value=''>Choose your option</option>");
	 
} else {
	 
		$("#city").html("<option value=''>Choose your option</option><option value='Bangalore'>Bangalore</option>");
}
/* $.ajax({
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
		});*/
	}); 
$("#city").change(function(){   // 1st
    // do your code here
    // When your element is already rendered
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

 
function GetDetails (){
	//alert("inside function");
	var input_type;
	var inverter_type = $('#inverter_type').val();	
	if (selectValidation("","inverter_type",input_type) == false)
		{
			return;
			
		} 
 
	var inverter_capacity = $('#inverter_capacity').val();	
	if (selectValidation("","inverter_capacity",input_type) == false)
	{
		return;
		
	}
	var inverter_name = $('#inverter_name').val();	
	if (selectValidation("","inverter_name",input_type) == false)
	{
		return;
		
	}
	var pincode = $('#pincode').val();	
	//alert(pincode);
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
	
	
	document.orderinverter.method="post";
	document.orderinverter.action="../../../servlet/BatterystoreInverterDetails?hidWhatToDo=getinverterdetails&invertertype="+inverter_type+"&invertercapacity="+inverter_capacity+"&invertername="+inverter_name+"&state="+state+"&city="+city+"&area="+area+"&pincode="+pincode;
	//alert(document.orderinverter.action);
	document.orderinverter.submit();
}
</script>


				