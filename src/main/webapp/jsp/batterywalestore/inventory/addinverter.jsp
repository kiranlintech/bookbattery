
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
                  <div class="header bg-blue-grey">
                      <h2>
                         ADD INVERTER INVENTORY
                      </h2> 
                  </div>
                  <div class="body">
                			<div class="alert bg-green" id="success_div" style="display:none">
                                Successfully Added Inventory Details.
                            </div> 
                            <div class="alert bg-red" id="failure_div" style="display:none">
                                 Failed to Add Inventory Details.
                            </div>
					 <form class="form-horizontal" id="myform">
                                 
                                <div class="row clearfix">
                                    <div class="col-lg-3 col-md-3 col-sm-5 col-xs-12">
                                        <label for="inverter_brand">Select Brand:&nbsp;<font color="red">*</font></label>
                                    </div>
                                    <div class="col-lg-9 col-md-9 col-sm-7 col-xs-12">
                                        <div class="form-group">
                                            <div class="form-line"> 
                                            	<select class="form-control yes" id="inverter_brand">
													 <option value="">Choose your option</option>
												 </select>
												 
                                            </div>
                                        </div>
                                        <div id="inverter_brand-error" style="display:none;"></div>
                                    </div>
                                </div> 
                                
                                <div class="row clearfix">
                                    <div class="col-lg-3 col-md-3 col-sm-5 col-xs-12">
                                        <label for="battery_capacity">Select Capacity:&nbsp;<font color="red">*</font></label>
                                    </div>
                                    <div class="col-lg-9 col-md-9 col-sm-7 col-xs-12">
                                        <div class="form-group">
                                            <div class="form-line"> 
                                            	<select class="form-control yes" id="inverter_capacity">
													 <option value="">Choose your option</option>
												 </select>
												
                                            </div>
                                        </div>
                                         <div id="inverter_capacity-error" style="display:none;"></div>
                                    </div>
                                </div> 
                                <div class="row clearfix">
                                    <div class="col-lg-3 col-md-3 col-sm-5 col-xs-12">
                                        <label for="battery_model">Select Model:&nbsp;<font color="red">*</font></label>
                                    </div>
                                    <div class="col-lg-9 col-md-9 col-sm-7 col-xs-12">
                                        <div class="form-group">
                                            <div class="form-line"> 
                                            	<select class="form-control yes" id="inverter_model">
													 <option value="">Choose your option</option>
												 </select>
												  
                                            </div>
                                        </div>
                                        <div id="inverter_model-error" style="display:none;"></div>
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
                                <input type="hidden" name="storeid" id="storeid" value="<%=strStoreId%>">
								<input type="hidden" name="storename" id="storename" value="<%=strStoreName%>">
                                <div class="row clearfix">
                                    <div class="col-lg-offset-4 col-md-offset-4 col-sm-offset-6 col-xs-offset-6">
                                        <button type="button" class="btn bg-blue-grey m-t-15 waves-effect" onclick="funtoAddInventoryDetails()">ADD INVENTORY</button>
                                         <button type="button" class="btn bg-light-blue m-t-15 waves-effect" onclick="funtoReset()">RESET</button>
                                    </div>
                                </div>
                            </form>

                  </div>
              </div>
           </div>
</section>
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog">
               
</div> 
 <script src="http://materializecss.com/bin/materialize.js" />
 
 <jsp:include page = "../storefooter.jsp" />
 
<script type="text/javascript">


$(document).ready(function(){	
	 $("#success_div").hide();
	 $("#failure_div").hide();
	var keyword="Common";
	$.ajax({
			type: "GET",
			url: "/bookbattery/servlet/BatterystoreInventoryDetails?hidWhatToDo=getinverterbrand",
			//data: {keyword: keyword},
			success: function(data){
				
				//alert(data); 
				$("#inverter_brand").html(data);
						
					 
			}
		}); 
});
$("#inverter_brand").change(function(){   // 1st
    // do your code here
    // When your element is already rendered
 var inverterbrand = $('#inverter_brand').val();
 
  //  alert(batterycapacity);
  $.ajax({
		type: "GET",
		url: "/bookbattery/servlet/BatterystoreInventoryDetails?hidWhatToDo=getinvertercapacity",
		data: {inverterbrand: inverterbrand},
		//data: {batterytype: $category.val() },
		success: function(data){
			// alert(data);
			$("#inverter_capacity").html(data);
 			 
		}
	});
}); 

$("#inverter_capacity").change(function(){   // 1st
    // do your code here
    // When your element is already rendered
 var inverterbrand = $('#inverter_brand').val();
 var invertercapacity = $('#inverter_capacity').val();
  //  alert(batterycapacity);
  $.ajax({
		type: "GET",
		url: "/bookbattery/servlet/BatterystoreInventoryDetails?hidWhatToDo=getinvertermodels",
		data: {invertercapacity: invertercapacity, inverterbrand: inverterbrand},
		//data: {batterytype: $category.val() },
		success: function(data){
			// alert(data);
			$("#inverter_model").html(data);
 			 
		}
	});
}); 


function funtoAddInventoryDetails(){
	
	 
	var inverter_brand =  $("#inverter_brand").val();
	if (selectValidation("","inverter_brand","select") == false)
	{
		return;
		
	} 
	var inverter_capacity =  $("#inverter_capacity").val();
	if (selectValidation("","inverter_capacity","select") == false)
	{
		return;
		
	} 
	var inverter_model =  $("#inverter_model").val();
	if (selectValidation("","inverter_model","select") == false)
	{
		return;
		
	} 
	var quantity	= $("#quantity").val();
	if (yesValidation("","quantity","yes") == false)
	{
		return;
		
	} 
	var storename =  $("#storename").val();
	var storeid	= $("#storeid").val();
	
	  $.ajax({
			type: "GET",
			url: "/bookbattery/servlet/BatterystoreInventoryDetails?hidWhatToDo=addinverterinventory",
			data: {inverter_brand: inverter_brand, inverter_capacity: inverter_capacity, inverter_model: inverter_model, quantity: quantity, storename: storename, storeid: storeid },
			success: function(data){
 				// alert(data);
				 
				 var OrderLable="";
				var OrderMessage="";
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
	
function okey_done(){
	
	 location.reload();
}

function funtoReset(){
	
	$("#myform")[0].reset();
}

</script>