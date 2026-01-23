
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.*,com.ngit.javabean.loglevel.LogLevel,java.util.Properties,java.io.FileInputStream"%>
<%
String strUserid=(String)session.getAttribute("sesStoreOperatorName");
LogLevel.DEBUG(5,new Throwable(),"Prasanna strUserid: "+strUserid);
session.setAttribute("sesBatteryOperatorName","storeoperator");
LogLevel.DEBUG(5,new Throwable(),"Prasanna sesBatteryOperatorName: "+strUserid);
String strStoreId=(String)session.getAttribute("sesstrStoreId");
String strStoreName=(String)session.getAttribute("sesstrStoreName");
LogLevel.DEBUG(5,new Throwable(),"Prasanna strStoreId: "+strStoreId);
LogLevel.DEBUG(5,new Throwable(),"Prasanna strStoreName: "+strStoreName);

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
        <div class="container-fluid">
				<div class="card">
                  <div class="header bg-cyan">
                      <h2>
                         EXCHANGE BATTERIES
                      </h2> 
                  </div>
                 <div class="alert bg-primary">
                        BATTERY GIVEN TO EXCHANGE
                  </div>
                   <div class="body">
            			<div class="alert bg-green" id="success_div" style="display:none">
                            Successfully Added Battery Details.
                        </div> 
                        <div class="alert bg-red" id="failure_div" style="display:none">
                             Failed to Add Battery Details.
                        </div>
                         <form class="form-horizontal" id="myform">
                                <div class="row clearfix">
                                    <div class="col-lg-3 col-md-3 col-sm-5 col-xs-12">
                                        <label for="battery_type">Select Battery Type:&nbsp;<font color="red">*</font></label>
                                    </div>
                                    <div class="col-lg-9 col-md-9 col-sm-7 col-xs-12">
                                        <div class="form-group">
                                            <div class="form-line">
                                            	<select class="form-control yes" id="battery_type" data-live-search="true">
                                            		<option value="-1">Select Batteries Type</option>
													<option value="Car Batteries">Car Batteries</option>
	 												<option value="Bike Batteries">Bike Batteries</option>
	 												<option value="Inverter Batteries">Inverter Batteries</option>                                           	                                            	</select> 
                                             </div>
                                        </div>
                                        	<div id="battery_type-error" style="display:none;"></div>
                                    </div>
                                </div>
                                <div class="row clearfix">
                                    <div class="col-lg-3 col-md-3 col-sm-5 col-xs-12">
                                        <label for="battery_brand">Select Battery Brand:&nbsp;<font color="red">*</font></label>
                                    </div>
                                    <div class="col-lg-9 col-md-9 col-sm-7 col-xs-12">
                                        <div class="form-group">
                                            <div class="form-line"> 
                                            	<select class="form-control yes" id="battery_brand">
													 <option value="">Choose your option</option>
												 </select>
												 
                                            </div>
                                        </div>
                                        <div id="battery_brand-error" style="display:none;"></div>
                                    </div>
                                </div> 
                                
                                <div class="row clearfix">
                                    <div class="col-lg-3 col-md-3 col-sm-5 col-xs-12">
                                        <label for="battery_capacity">Select Battery Name:&nbsp;<font color="red">*</font></label>
                                    </div>
                                    <div class="col-lg-9 col-md-9 col-sm-7 col-xs-12">
                                        <div class="form-group">
                                            <div class="form-line"> 
                                            	<select class="form-control yes" id="battery_name">
													 <option value="">Choose your option</option>
												 </select>
												
                                            </div>
                                        </div>
                                         <div id="battery_name-error" style="display:none;"></div>
                                    </div>
                                </div> 
                                <div class="row clearfix">
                                    <div class="col-lg-3 col-md-3 col-sm-5 col-xs-12">
                                        <label for="battery_model">Select Battery Model:&nbsp;<font color="red">*</font></label>
                                    </div>
                                    <div class="col-lg-9 col-md-9 col-sm-7 col-xs-12">
                                        <div class="form-group">
                                            <div class="form-line"> 
                                            	<select class="form-control yes" id="battery_model">
													 <option value="">Choose your option</option>
												 </select>
												  
                                            </div>
                                        </div>
                                        <div id="battery_model-error" style="display:none;"></div>
                                    </div>
                                </div>  
                                
                                <div class="row clearfix">
                                    <div class="col-lg-3 col-md-3 col-sm-5 col-xs-12">
                                        <label for="quantity">Enter Quantity:&nbsp;<font color="red">*</font></label>
                                    </div>
                                    <div class="col-lg-9 col-md-9 col-sm-7 col-xs-12">
                                        <div class="form-group">
                                            <div class="form-line"> 
                                            	 <input type="text" id="quantity" class="form-control yes" placeholder="Enter Quantity" maxlength="2"> 
                                            </div>
                                        </div>
                                         <div id="quantity-error" style="display:none;"></div>
                                    </div>
                                </div> 

                  
                   
                    <div class="alert bg-pink">
                        BATTERY EXCHANGED
                    </div>
                     <div class="row clearfix">
							<div class="col-lg-3 col-md-3 col-sm-5 col-xs-12">
								<label for="battery_type_exchanged">Select Battery Type:&nbsp;<font color="red">*</font></label>
							</div>
							<div class="col-lg-9 col-md-9 col-sm-7 col-xs-12">
								<div class="form-group">
									<div class="form-line">
										<select class="form-control yes" id="battery_type_exchanged" data-live-search="true">
											<option value="-1">Select Batteries Type</option>
											<option value="Car Batteries">Car Batteries</option>
											<option value="Bike Batteries">Bike Batteries</option>
											<option value="Inverter Batteries">Inverter Batteries</option>                                           	                                            	</select> 
									 </div>
								</div>
									<div id="battery_type_exchanged-error" style="display:none;"></div>
							</div>
							</div>
							<div class="row clearfix">
							<div class="col-lg-3 col-md-3 col-sm-5 col-xs-12">
								<label for="battery_brand_exchanged">Select Battery Brand:&nbsp;<font color="red">*</font></label>
							</div>
							<div class="col-lg-9 col-md-9 col-sm-7 col-xs-12">
								<div class="form-group">
									<div class="form-line"> 
										<select class="form-control yes" id="battery_brand_exchanged">
											 <option value="">Choose your option</option>
										 </select>
										 
									</div>
								</div>
								<div id="battery_brand_exchanged-error" style="display:none;"></div>
							</div>
							</div> 
					
							<div class="row clearfix">
							<div class="col-lg-3 col-md-3 col-sm-5 col-xs-12">
								<label for="battery_name_exchanged">Select Battery Name:&nbsp;<font color="red">*</font></label>
							</div>
							<div class="col-lg-9 col-md-9 col-sm-7 col-xs-12">
								<div class="form-group">
									<div class="form-line"> 
										<select class="form-control yes" id="battery_name_exchanged">
											 <option value="">Choose your option</option>
										 </select>
										
									</div>
								</div>
								 <div id="battery_name_exchanged-error" style="display:none;"></div>
							</div>
							</div> 
							<div class="row clearfix">
							<div class="col-lg-3 col-md-3 col-sm-5 col-xs-12">
								<label for="battery_model_exchanged">Select Battery Model:&nbsp;<font color="red">*</font></label>
							</div>
							<div class="col-lg-9 col-md-9 col-sm-7 col-xs-12">
								<div class="form-group">
									<div class="form-line"> 
										<select class="form-control yes" id="battery_model_exchanged">
											 <option value="">Choose your option</option>
										 </select>
										  
									</div>
								</div>
								<div id="battery_model_exchanged-error" style="display:none;"></div>
							</div>
							</div>  
					
							<div class="row clearfix">
							<div class="col-lg-3 col-md-3 col-sm-5 col-xs-12">
								<label for="quantity_exchanged">Enter Quantity:&nbsp;<font color="red">*</font></label>
							</div>
							<div class="col-lg-9 col-md-9 col-sm-7 col-xs-12">
								<div class="form-group">
									<div class="form-line"> 
										 <input type="text" id="quantity_exchanged" class="form-control yes" placeholder="Enter Quantity" maxlength="2"> 
									</div>
								</div>
								 <div id="quantity_exchanged-error" style="display:none;"></div>
							</div>
							</div> 
                    
                    
                     <input type="hidden" name="storeid" id="storeid" value="<%=strStoreId%>">
					 <input type="hidden" name="storename" id="storename" value="<%=strStoreName%>">
                     <div class="row clearfix">
                         <div class="col-lg-offset-4 col-md-offset-4 col-sm-offset-6 col-xs-offset-6">
                             <button type="button" class="btn btn-primary m-t-15 waves-effect" onclick="funtoAddExchangeDetails()">ADD</button>
                              <button type="button" class="btn bg-red m-t-15 waves-effect" onclick="funtoReset()">RESET</button>
                    	 </div> 
                     </div>
                     </div>              
					</form>
					
                   </div>
       </div>
 </section>



<script src="http://materializecss.com/bin/materialize.js" />
 
<jsp:include page = "../storefooter.jsp" />
 
<script type="text/javascript">

$("#battery_type").change(function(){   // 1st
    // do your code here
    // When your element is already rendered
 var batterytype = $('#battery_type').val();
    //alert(batterytype);
    $("#success_div").hide();
    $("#failure_div").hide();
  $.ajax({
		type: "GET",
		url: "../../../servlet/BatterystoreBatteryDetails?hidWhatToDo=getbatterybrands",
		data: {batterytype: batterytype},
		//data: {batterytype: $category.val() },
		success: function(data){
			// alert(data);
			$("#battery_brand").html(data);
 			 
		}
	});
}); 

/*$("#battery_brand").change(function(){   // 1st
    // do your code here
    // When your element is already rendered
 var batterybrand = $('#battery_brand').val();
 var batterytype = $('#battery_type').val();

  //  alert(batterycapacity);
  $.ajax({
		type: "GET",
		url: "../../../servlet/BatterystoreBatteryDetails?hidWhatToDo=getbrandcapacity",
		data: {batterybrand: batterybrand, batterytype: batterytype},
		//data: {batterytype: $category.val() },
		success: function(data){
			// alert(data);
			$("#battery_capacity").html(data);
 			 
		}
	});
}); 

$("#battery_capacity").change(function(){   // 1st
    // do your code here
    // When your element is already rendered
 var batterybrand = $('#battery_brand').val();
 var batterycapacity = $('#battery_capacity').val();
  //  alert(batterycapacity);
  $.ajax({
		type: "GET",
		url: "../../../servlet/BatterystoreBatteryDetails?hidWhatToDo=getmodels",
		data: {batterycapacity: batterycapacity, batterybrand: batterybrand},
		//data: {batterytype: $category.val() },
		success: function(data){
			// alert(data);
			$("#battery_model").html(data);
 			 
		}
	});
}); 
*/

$("#battery_brand").change(function(){   // 1st
	// do your code here
	// When your element is already rendered
	var batterybrand = $('#battery_brand').val();
	var batterytype = $('#battery_type').val();

	//  alert(batterycapacity);
	$.ajax({
		type: "GET",
		url: "../../../servlet/BatterystoreBatteryDetails?hidWhatToDo=getbatteryname",
		data: {batterybrand: batterybrand, batterytype: batterytype},
		//data: {batterytype: $category.val() },
		success: function(data){
			// alert(data);
			$("#battery_name").html(data);
				 
		}
	});
	}); 

$("#battery_name").change(function(){   // 1st
    // do your code here
    // When your element is already rendered
 var batterybrand = $('#battery_brand').val();
 var batteryname = $('#battery_name').val();
  //  alert(batterycapacity);
  $.ajax({
		type: "GET",
		url: "../../../servlet/BatterystoreBatteryDetails?hidWhatToDo=getcapacitymodels",
		data: {batteryname: batteryname, batterybrand: batterybrand},
		//data: {batterytype: $category.val() },
		success: function(data){
			// alert(data);
			$("#battery_model").html(data);
 			 
		}
	});
}); 

$("#battery_type_exchanged").change(function(){   // 1st
    // do your code here
    // When your element is already rendered
 var batterytype_exchanged = $('#battery_type_exchanged').val();
    //alert(batterytype);
    $("#success_div").hide();
    $("#failure_div").hide();
  $.ajax({
		type: "GET",
		url: "../../../servlet/BatterystoreBatteryDetails?hidWhatToDo=getbatterybrands",
		data: {batterytype: batterytype_exchanged},
		//data: {batterytype: $category.val() },
		success: function(data){
			// alert(data);
			$("#battery_brand_exchanged").html(data);
 			 
		}
	});
}); 

/*$("#battery_brand_exchanged").change(function(){   // 1st
    // do your code here
    // When your element is already rendered
 var batterybrand_exchanged = $('#battery_brand_exchanged').val();
 var batterytype_exchanged = $('#battery_type_exchanged').val();

  //  alert(batterycapacity);
  $.ajax({
		type: "GET",
		url: "../../../servlet/BatterystoreBatteryDetails?hidWhatToDo=getbrandcapacity",
		data: {batterybrand: batterybrand_exchanged, batterytype: batterytype_exchanged},
		//data: {batterytype: $category.val() },
		success: function(data){
			// alert(data);
			$("#battery_capacity_exchanged").html(data);
 			 
		}
	});
}); 

$("#battery_capacity_exchanged").change(function(){   // 1st
    // do your code here
    // When your element is already rendered
 var batterybrand_exchanged = $('#battery_brand_exchanged').val();
 var batterycapacity_exchanged = $('#battery_capacity_exchanged').val();
  //  alert(batterycapacity);
  $.ajax({
		type: "GET",
		url: "../../../servlet/BatterystoreBatteryDetails?hidWhatToDo=getmodels",
		data: {batterycapacity: batterycapacity_exchanged, batterybrand: batterybrand_exchanged},
		//data: {batterytype: $category.val() },
		success: function(data){
			// alert(data);
			$("#battery_model_exchanged").html(data);
 			 
		}
	});
}); */

$("#battery_brand_exchanged").change(function(){   // 1st
	// do your code here
	// When your element is already rendered
	var batterybrand = $('#battery_brand').val();
	var batterytype = $('#battery_type').val();

	//  alert(batterycapacity);
	$.ajax({
		type: "GET",
		url: "../../../servlet/BatterystoreBatteryDetails?hidWhatToDo=getbatteryname",
		data: {batterybrand: batterybrand, batterytype: batterytype},
		//data: {batterytype: $category.val() },
		success: function(data){
			// alert(data);
			$("#battery_name_exchanged").html(data);
				 
		}
	});
}); 

$("#battery_name_exchanged").change(function(){   // 1st
    // do your code here
    // When your element is already rendered
 var batterybrand_exchanged = $('#battery_brand_exchanged').val();
 var battery_name_exchanged = $('#battery_name_exchanged').val();
  //  alert(batterycapacity);
  $.ajax({
		type: "GET",
		url: "../../../servlet/BatterystoreBatteryDetails?hidWhatToDo=getcapacitymodels",
		data: {batteryname: battery_name_exchanged, batterybrand: batterybrand_exchanged},
		//data: {batterytype: $category.val() },
		success: function(data){
			// alert(data);
			$("#battery_model_exchanged").html(data);
 			 
		}
	});
}); 

function funtoAddExchangeDetails(){
	
	 var batterytype = $('#battery_type').val();
		if (selectValidation("","battery_type","select") == false)
		{
			return;
			
		}
	 var batterybrand = $('#battery_brand').val();
	 if (selectValidation("","battery_brand","select") == false)
		{
			return;
			
		}
	 var battery_name = $('#battery_name').val();
	 if (selectValidation("","battery_name","select") == false)
		{
			return;
			
		}
	 var batterymodel = $('#battery_model').val();
	 if (selectValidation("","battery_model","select") == false)
		{
			return;
			
		}
	 var quantity  = $('#quantity').val();
	 if (yesValidation("","quantity","yes") == false)
		{
			return;
			
		}
	 var batterytype_exchanged = $('#battery_type_exchanged').val();
		if (selectValidation("","battery_type_exchanged","select") == false)
		{
			return;
			
		}
	 var batterybrand_exchanged = $('#battery_brand_exchanged').val();
		if (selectValidation("","battery_brand_exchanged","select") == false)
		{
			return;
			
		}
	 var battery_name_exchanged = $('#battery_name_exchanged').val();
		if (selectValidation("","battery_name_exchanged","select") == false)
		{
			return;
			
		}
	 var batterymodel_exchanged = $('#battery_model_exchanged').val();
		if (selectValidation("","battery_model_exchanged","select") == false)
		{
			return;
			
		}
	 var quantity_exchanged  = $('#quantity_exchanged').val();
	 if (yesValidation("","quantity_exchanged","yes") == false)
		{
			return;
			
		}
	 
	 var storename =  $("#storename").val();
	 var storeid	= $("#storeid").val();
	 //alert(storename);
	//alert(storeid);
	 $.ajax({
			type: "GET",
			url: "/bookbattery/servlet/BatterystoreScrapDetails?hidWhatToDo=insertexchangebatterydetails",
			data: {battery_type: batterytype, battery_brand : batterybrand, battery_name: battery_name, battery_model: batterymodel, quantity: quantity, battery_type_exchanged: batterytype_exchanged, battery_brand_exchanged : batterybrand_exchanged, battery_name_exchanged: battery_name_exchanged, battery_model_exchanged: batterymodel_exchanged, quantity_exchanged: quantity_exchanged, storename: storename, storeid: storeid },
			success: function(data){
				// alert(data);
				if(data.indexOf("Sucessfully Inserted")>=0)
				{
					 
					$("#success_div").show();
					$("#myform")[0].reset();
				}
				else
				{
					 
					$("#failure_div").show();
					$("#myform")[0].reset();
				} 
			 
				}
		});
}
</script>