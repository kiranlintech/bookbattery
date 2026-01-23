
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
	<form name="ordertrolley">
        <div class="container-fluid">
        
            <!-- Advanced Select -->
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="header bg-light-blue">
                            <h2>
                                ORDER TROLLEY 
                            </h2>
                        </div>
                        <div class="body">
                             <div class="row clearfix">
								<div class="col-md-2 col-sm-12 col-xs-12">
                                 	 <label for="battery_type">Select Brand:&nbsp;<font color="red">*</font></label>
                                 	 <div class="form-group">
                                            <div class="form-line">
                                            	<select class="form-control yes" id="trolley_brand" data-live-search="true">
                                            		<option value="-1">Choose Option</option>
												</select>
												<div id="trolley_brand-error" style="display:none;"></div>	                                    	                                            	</select> 
                                             </div>
                                        </div>
                                       
                                </div>
                                <div class="col-md-2 col-sm-12 col-xs-12">
                                  <label for="battery_capacity">Select Model:&nbsp;<font color="red">*</font></label>
                                  <div class="form-group">
                                            <div class="form-line"> 
                                            	<select class="form-control yes" id="trolley_model">
													 <option value="">Choose Option</option>
												 </select>
												 <div id="trolley_model-error" style="display:none;"></div>
                                            </div>
                                     </div>
                                        
                                </div>
                                <!---------------------Pincode Starts ----------------------->
									 <div  id="orderform_state">
										<div class="col-md-2">
											<p>
												<b>State&nbsp;<font color="red">*</font></b>
											</p>
											<div class="form-group">
	                                            <div class="form-line"> 
	                                            	<select class="form-control yes" id="state">
														 <option value="">Choose Option</option>
													 </select>
													 <div id="state-error" style="display:none;"></div>
	                                            </div>
                                    	 	</div>
                                     	 
										</div>
										<div class="col-md-2">
											<p>
												<b>City&nbsp;<font color="red">*</font></b>
											</p>
											<div class="form-group">
	                                            <div class="form-line"> 
	                                            	<select class="form-control yes" id="city">
														 <option value="">Choose Option</option>
													 </select>
													 <div id="city-error" style="display:none;"></div>
	                                            </div>
                                    	 	</div>
                                    	 	
										</div>
										<div class="col-md-2">
											<p>
												<b>Area&nbsp;<font color="red">*</font></b>
											</p>
											<div class="form-group">
	                                            <div class="form-line"> 
	                                            	<select class="form-control yes" id="area">
														 <option value="">Choose Option</option>
													 </select>
													 <div id="area-error" style="display:none;"></div>
	                                            </div>
                                    	 	</div>
                                    	 	
											 <small id="emailHelp2" class="form-text text-muted pull-right"><a href="javascript:DisplayPincode()">Order&nbsp;With&nbsp;Pincode.</a></small>
										</div>
										<div class="col-md-2">
											<br/>
												<button type="button" class="btn bg-blue waves-effect" onclick="GetTrolleydetails()">
													<i class="material-icons">search</i>
													<span>FIND DETAILS</span>
												</button> 
										</div> 
										 
								 	 </div>
								 	 
								 	 	<!---------------------Pincode Starts ----------------------->
									 <div id="orderform_pincode" style="display:none;">
										 
										<div class="col-md-6 col-lg-2 col-xs-12">
											<label for="pincode">Pincode<font color="red">*</font></label>
											<div class="form-group">
												<div class="form-line">
													<input type="text" id="pincode" class="form-control" placeholder="Eg: 560066">
												</div>
											</div>
											<small id="emailHelp2" class="form-text text-muted pull-right"><a href="javascript:DisplayStateCity()">Order&nbsp;With&nbsp;State,City,Area.</a></small>
										</div> 
										
										<div class="col-md-6 col-lg-2 col-xs-12">
										<br/>
												<button type="button" class="btn bg-blue waves-effect" onclick="GetTrolleydetails()">
													<i class="material-icons">search</i>
													<span>FIND  DETAILS</span>
												</button>  
										</div> 
										
									 </div>
									<!---------------------Pincode Ends ----------------------->
							</div> 
						 </div>
									<!---------------------Trolley Ends ----------------------->
									
					</div>
               </div>
           </div>
            
        </div>
    </form>
 </section>

<script src="http://materializecss.com/bin/materialize.js" />

<input type="hidden" name="store_id" id="store_id" value="<%=strStoreId%>">
<input type="hidden" name="store_name" id="store_name" value="<%=strStoreName%>">
<input type="hidden" name="brandkeyword" id='brandkeyword' value="Common">

<jsp:include page = "../storefooter.jsp" />		

<script type="text/javascript">

$(document).ready(function(){	 
	$.ajax({
			type: "GET",
			url: "/bookbattery/servlet/BatterystoreInventoryDetails?hidWhatToDo=gettrolleybrands",
			//data: {},
			success: function(data){
				
				//alert(data); 
				$("#trolley_brand").html(data);
						
					 
			}
		}); 
}); 


$("#trolley_brand").change(function(){   // 1st
    // do your code here
    // When your element is already rendered
 var trolleybrand = $('#trolley_brand').val();
    //alert(batterytype);
   
  $.ajax({
		type: "GET",
		url: "/bookbattery/servlet/BatterystoreInventoryDetails?hidWhatToDo=gettrolleymodels",
		data: {trolleybrand: trolleybrand},
		success: function(data){
			// alert(data);
			$("#trolley_model").html(data);
 			 
		}
	});
}); 

$("#trolley_brand").change(function(){   // 1st
    // do your code here
    // When your element is already rendered
 var trolleybrand = $('#trolley_brand').val();
	
 $('#state').val('');
if(trolleybrand==""){
	 	
		$("#state").html("<option value=''>Choose Option</option>");
	 
} else {
	 
	    $("#state").html("<option value=''>Choose Option</option><option value='Karnataka'>Karnataka</option>");
}
 
}); 
	 
$("#state").change(function(){   // 1st
    // do your code here
    // When your element is already rendered
var trolleybrand = $('#trolley_brand').val();	
var trolleymodel = $('#trolley_model').val();	
var state = $('#state').val();	
	$('#city').val('');
	 if(state==""){
			
			$("#city").html("<option value=''>Choose your option</option>");
		 
	 } else {
		 
			$("#city").html("<option value=''>Choose your option</option><option value='Bangalore'>Bangalore</option>");
	 }
});	  
$("#city").change(function(){   // 1st
    // do your code here
    // When your element is already rendered
var trolleybrand = $('#trolley_brand').val();	
var trolleymodel = $('#trolley_model').val();	
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


function GetTrolleydetails(){
	//alert("inside function");
	var input_type;
	var trolleybrand = $('#trolley_brand').val();
	if (selectValidation("","trolley_brand",input_type) == false)
	{
		return;
		
	}
	var trolleymodel = $('#trolley_model').val();	
	if (selectValidation("","trolley_model",input_type) == false)
	{
		return;
		
	}
	//var state = $('#state').val();
	//var city = $('#city').val();
  
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
	
 	document.ordertrolley.method="post";
	document.ordertrolley.action="../../../servlet/BatterystoreTrolleyDetails?hidWhatToDo=gettrolleydetails&trolleybrand="+trolleybrand+"&trolleymodel="+trolleymodel+"&state="+state+"&city="+city+"&area="+area+"&pincode="+pincode;
	//alert(document.orderbattery.action);
	document.ordertrolley.submit();
}
</script>


				