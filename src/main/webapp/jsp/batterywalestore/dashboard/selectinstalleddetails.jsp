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
	                                <label for="to_date"> Brand:&nbsp;</label>
									<div class="form-group">
										<div class="form-line"> 
											<select class="form-control yes" id="brand_type">
												 <option value="">Choose Brand</option>
												 <option value="All">All</option>
												 <option value="Amaron">Amaron</option>
												 <option value="Exide">Exide</option>
												 <option value="Luminous">Luminous</option>
											 </select> 
										</div>
									</div>
									<div id="brand_type-error" style="display:none;"></div>
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
												<option value="Bus Batteries">Bus Batteries</option>
												<option value="Tractor Batteries">Tractor Batteries</option>
												<option value="Truck Batteries">Truck Batteries</option>
												<option value="Three Wheeler Batteries">Three Wheeler Batteries</option>
  												<option value="Special Vehicle Batteries">Special Vehicle Batteries</option>
												<option value="Genset Batteries">Genset Batteries</option>
												<option value="Crane Batteries">Crane Batteries</option>
												<option value="Roller Batteries">Roller Batteries</option>
												<option value="Loader Batteries">Loader Batteries</option>
												<option value="Dozer Batteries">Dozer Batteries</option>
												<option value="Excavator Batteries">Excavator Batteries</option>
												<option value="Tyre Handler Batteries">Tyre Handler Batteries</option>
												<option value="Hydraulic Shovel Batteries">Hydraulic Shovel Batteries</option>
												<option value="Harvestor Batteries">Harvestor Batteries</option>
												<option value="Generator Batteries">Generator Batteries</option>
												<option value="Compactor Batteries">Compactor Batteries</option>
												<option value="Telescopic Handler Batteries">Telescopic Handler Batteries</option>
												<option value="Forwarder Batteries">Forwarder Batteries</option>
												<option value="Wheeled Harvester Batteries">Wheeled Harvester Batteries</option>
												<option value="Minibus Batteries">Minibus Batteries</option>
												<option value="Dumper Batteries">Dumper Batteries</option>
												<option value="Construction Equipment Batteries">Construction Equipment Batteries</option>
												<option value="Hydralic Excavator Batteries">Hydralic Excavator Batteries</option>
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
                                         <button type="button" class="btn bg-blue waves-effect" onclick="GetMISReports()">
											<i class="material-icons">search</i>
											<span>GET REPORTS</span>
										</button>  
                                    </div>
                                </div>
                            </div>
                            <div class="row clearfix" id="misreports"></div>
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


function GetMISReports(){
	
	var store_name = $("#store_name").val();
	var store_id = $("#store_id").val();
	var fromdate = $("#fromdate").val();
	var todate = $("#todate").val();
	var brand_type = $("#brand_type").val();
	var battery_type = $("#battery_type").val();
	var payment_type = $("#payment_type").val();
	var battery_status = "installed";
	
	$.ajax({
		type: "GET",
		url: "/bookbattery/servlet/BatterystoreMISReports?hidWhatToDo=misbatterydetails",
		data: {todate: todate, fromdate: fromdate, store_id: store_id, store_name: store_name, brand_type: brand_type, battery_type: battery_type, payment_type: payment_type, order_status: battery_status },
		success: function(data){
			
			//alert(data);
			 
			$("#misreports").html(data);
					
				 
		}
	}); 
}
</script>