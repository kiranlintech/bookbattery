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

Vector alist=(Vector)session.getAttribute("sesCountofBatterynrand");
LogLevel.DEBUG(1,new Throwable(),"alist: "+alist);
Vector alist1=(Vector)session.getAttribute("sesCountofInverterbrand");
LogLevel.DEBUG(1,new Throwable(),"alist1: "+alist1);
Vector alist2=(Vector)session.getAttribute("sesCountofServiceHealth");
LogLevel.DEBUG(1,new Throwable(),"alist2: "+alist2);
//Vector alist3=(Vector)session.getAttribute("sesCountofServiceRecharge");
//LogLevel.DEBUG(1,new Throwable(),"alist3: "+alist3);
//Vector alist4=(Vector)session.getAttribute("sesCountofServiceJumpStart");
//LogLevel.DEBUG(1,new Throwable(),"alist4: "+alist4);
 %>
<jsp:include page = "../storeheader.jsp" />
<jsp:include page = "../storeleftmenu.jsp" />
 <section class="content">
        <div class="container-fluid">
            <!-- Advanced Select -->
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="header bg-blue">
                            <h2>
                               MIS REPORTS
                            </h2>
                        </div>
                        <div class="body">
                               <div class="row clearfix">
                                <div class="col-lg-2 col-md-2 col-sm-2 col-xs-12">
                                <label for="form_date">From Date:&nbsp;</label>
                                    <div class="form-group">
                                        <div class="form-line">
                                            <input type="text" class="datepicker form-control" id="fromdate" placeholder="Choose a date" readonly>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-2 col-md-2 col-sm-2 col-xs-12">
                                <label for="form_date">To Date:&nbsp;</label>
                                    <div class="form-group">
                                        <div class="form-line">
                                            <input type="text" class="datepicker form-control" id="todate" placeholder="Choose a date" readonly>
                                        </div>
                                    </div>
                                </div>  
                                 <div class="col-lg-2 col-md-2 col-sm-2 col-xs-12">
	                                <label for="to_date">Battery Type:&nbsp;</label>
									<div class="form-group">
										<div class="form-line"> 
											<select class="form-control yes" id="battery_type">
												<option value="">Choose Option</option>
												<option value="All">All</option>
												<option value="Car Batteries">Car Batteries</option>
 												<option value="Bike Batteries">Bike Batteries</option>
 												<option value="Inverter Batteries">Inverter Batteries</option>
												
											 </select> 
										</div>
									</div>
									<div id="battery_type-error" style="display:none;"></div>
								</div> 
								
                                 <div class="col-lg-2 col-md-2 col-sm-2 col-xs-12">
	                                <label for="to_date">Payment Type:&nbsp;</label>
									<div class="form-group">
										<div class="form-line"> 
											<select class="form-control yes" id="payment_type">
												 <option value="">Choose Option</option>
												 <option value="All">All</option>
												 <option value="Cash On Delivery">Cash On Delivery</option>
												 <option value="Store UPI PhonePe">Store UPI PhonePe</option> 
												 <option value="Online Payment After Fitment">Online Payment After Fitment</option> 
											 </select> 
										</div>
									</div>
									<div id="payment_type-error" style="display:none;"></div>
								</div>
								<div class="col-lg-2 col-md-2 col-sm-2 col-xs-12">
								<br/>
                                    <div class="form-group">
                                         <button type="button" class="btn bg-blue waves-effect" onclick="GetServiceReports()">
											<i class="material-icons">search</i>
											<span>GET REPORTS</span>
										</button>  
                                    </div>
                                </div>
                            </div>
                            <div class="row clearfix" id="misservicereports"></div>
						</div>
                    </div>
                </div>
            </div>
            <!-- #END# Advanced Select -->
        </div>
    </section>
 <input type="hidden" name="store_id" id="store_id" value="<%=strStoreId%>">
 <input type="hidden" name="store_name" id="store_name" value="<%=strStoreName%>">
<jsp:include page = "../storefooter.jsp" />
<script type="text/javascript">

$('#fromdate').bootstrapMaterialDatePicker({ format : 'YYYY-MM-DD', weekStart : 0, time: false });
$('#todate').bootstrapMaterialDatePicker({ format : 'YYYY-MM-DD', weekStart : 0, time: false });


function GetServiceReports(){
	
	var store_name = $("#store_name").val();
	var store_id = $("#store_id").val();
	var fromdate = $("#fromdate").val();
	var todate = $("#todate").val();
 	var battery_type = $("#battery_type").val();
	var payment_type = $("#payment_type").val();
	var order_status = "total";
	
	$.ajax({
		type: "GET",
		url: "/bookbattery/servlet/BatterystoreMISReports?hidWhatToDo=misservicedetails",
		data: {todate: todate, fromdate: fromdate, store_id: store_id, store_name: store_name, battery_type: battery_type, payment_type: payment_type, order_status: order_status },
		success: function(data){
			
			//alert(data);
			 
			$("#misservicereports").html(data);
					
				 
		}
	}); 
}
</script>