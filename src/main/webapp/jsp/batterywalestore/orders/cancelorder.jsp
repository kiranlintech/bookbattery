
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.*,com.ngit.javabean.loglevel.LogLevel,java.util.Properties,java.io.FileInputStream"%>
<%
String strUserid=(String)session.getAttribute("sesStoreOperatorName");
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
            <!-- Advanced Select -->
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="header bg-blue-grey">
                            <h2>
                                CANCEL A ORDER 
                            </h2>
                        </div>
                        <div class="body">
                              <!-- Inline Layout -->
								 
									<form>
										<div class="row clearfix">
											<div class="col-lg-3 col-md-3 col-sm-3 col-xs-6">
												
											</div>
											<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
												 <div class="input-group">
													<span class="input-group-addon">
														<i class="material-icons">highlight_off</i> 
													</span>
													<div class="form-line">
														<input type="text" class="form-control yes" id="order_number" name="order_number" placeholder="Enter Order Number">
													</div>
												</div>
 											</div>
											<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
												 
												<button type="button" class="btn bg-blue-grey waves-effect" onclick="GetOrderDetails()">
													<i class="material-icons">search</i>
													<span>GET ORDER DETAILS</span>
												</button>  
											</div>
										</div>
										<div class="row clearfix">
											<p class="col-lg-12 col-md-12 col-sm-12 col-xs-12" id="orderdetails"></p>
										</div>
									</form>
											 
								<!-- #END# Inline Layout -->
						</div>
                    </div>
                </div>
            </div>
            <!-- #END# Advanced Select -->
        </div>
    </section>

<input type="hidden" id="ordno" name="ordno" class="form-control" value="">
<input type="hidden" id="ordstatus" name="ordstatus" class="form-control" value="">
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog">
           
</div>	 
<!-- Small Size -->
  

<script type="text/javascript">
function GetOrderDetails(){
	
	var input_type;
	var order_number = $('#order_number').val();
	$.ajax({
			type: "GET",
			url: "/bookbattery/servlet/CancelOrderDetails?hidWhatToDo=cancelorderdetails",
			data: {ordernumber: order_number },
			success: function(data){
				//alert(data);
				document.getElementById("orderdetails").innerHTML=data;
			}
		}); 
}
 
function UpdateOrderStatus(ordernumber, orderstatus){
	//alert(ordernumber);
	//alert(orderstatus);
	
	var OrderMsg = '<div class="modal-dialog modal-sm" role="document">\
		<div class="modal-content">\
			<div class="modal-header">\
				<h4 class="modal-title" id="smallModalLabel">Update Order Status</h4>\
			</div>\
			 <div class="modal-body">\
				<label for="email_address">Enter Remarks :<font color="red">*</font></label><div class="form-group"><div class="form-line"><textarea rows="4" id="remarks" name="remarks" class="form-control no-resize" placeholder="Please type your Remarks..."></textarea></div></div>\
			</div>\
			 <div class="modal-footer">\
				<button type="button" class="btn bg-green waves-effect" onclick="CancelStatus()">UPDATE REMARKS</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="button" class="btn bg-blue waves-effect" data-dismiss="modal">CLOSE</button>\
 			 </div> \
		</div>\
	</div>';
	document.getElementById("ordno").value=ordernumber;
	document.getElementById("ordstatus").value=orderstatus;
	$("#exampleModal").modal({show: true, backdrop: 'static', keyboard: false});
	$("#exampleModal").html(OrderMsg);
}

function CancelStatus(){
	
	var orderno= $('#ordno').val();
	var ordstatus= $('#ordstatus').val();
	var cancel_status= $('#cancel_status').val();
	var remarks= $('#remarks').val();
	/*alert(orderno);
	alert(ordstatus);
	alert(cancel_status);
	alert(remarks);*/
 
	  $.ajax({
			type: "GET",
			url: "/bookbattery/servlet/CancelOrderDetails?hidWhatToDo=updatestatus",
			data: {orderno: orderno, ordstatus: ordstatus, cancel_status: cancel_status,remarks: remarks },
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
					OrderLable="Successfully Updated Status - BookBattery";
					OrderMessage="<div style='text-align: center;'><i class='icon-frown' style='font-size: 60px;color: #de3315;'></i><h4>Order Status has been Updated Successfully </h4></div></br>";
				}
				else
				{
					OrderLable="Failed to Update Status - BookBattery";
					OrderMessage="<div style='text-align: center;'><i class='icon-frown' style='font-size: 60px;color: #de3315;'></i><h4>We are Sorry,Some thing went wrong !! </h4><p style='font-size: 17px;'>Please try Again to Update</p></div></br>";
					
				}
				
				var AfterOrder_PopUp="<a class='btn hide' data-toggle='modal' data-target='.after_order_popup' data-toggle='modal' data-backdrop='static' data-keyboard='false' id='after_order_popup_btn'></a><div class='modal fade after_order_popup' tabindex='-1' role='dialog' aria-labelledby='after_order_popup_label'> <div class='modal-dialog' role='document'> 	<div class='modal-content'> <div class='modal-header'><h4 class='modal-title' id='after_order_popup_label'>"+OrderLable+"</h4><button type='button' onclick='okey_done()' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'> x</span></button> 	</div> <div class='modal-body'> 	<div class='form-group'> <div id='after_order_popup_data'> "+OrderMessage+"</div> <div style='text-align: center;'> 	<button type='button' class='btn btn-secondary' onclick='okey_done()'>Okay</button> </div> 	</div> </div> 	</div> </div> </div>";
				
				$("#exampleModal").modal({show: true, backdrop: 'static', keyboard: false});
				$("#exampleModal").html(AfterOrder_PopUp);
				$("#after_order_popup_btn").click();
				}
		});
}

function okey_done(){
	
	 location.replace("/bookbattery/jsp/BookBatterystore/orders/cancelorder.jsp");
}
</script>

<jsp:include page = "../storefooter.jsp" />		

				