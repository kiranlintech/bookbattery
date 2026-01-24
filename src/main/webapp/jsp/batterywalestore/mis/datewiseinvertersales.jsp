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
        <div class="container-fluid">
            <!-- Advanced Select -->
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="header bg-blue">
                            <h2>
                               DATE WISE SALES - INVERTERS
                            </h2>
                        </div>
                        <div class="body">
                               <div class="row clearfix">
                                <div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
                                <label for="form_date">From Date:&nbsp;<font color="red">*</font></label>
                                    <div class="form-group">
                                        <div class="form-line">
                                            <input type="text" class="datepicker form-control yes" id="fromdate" placeholder="Choose a date" readonly>
                                        	<div id='fromdate-error' style='display:none;'></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
                                <label for="form_date">To Date:&nbsp;<font color="red">*</font></label>
                                    <div class="form-group">
                                        <div class="form-line">
                                            <input type="text" class="datepicker form-control yes" id="todate" placeholder="Choose a date" readonly>
                                        	<div id='todate-error' style='display:none;'></div>
                                        </div>
                                    </div>
                                </div> 
                                <div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
	                                <label for="to_date"> Brand:&nbsp;<font color="red">*</font></label>
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
								<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
								<br/>
                                    <div class="form-group">
                                         <button type="button" class="btn bg-blue waves-effect" onclick="GetInverterSalesReports()">
											<i class="material-icons">search</i>
											<span>GET REPORTS</span>
										</button>  
                                    </div>
                                </div>
                            </div>
                            <div class="row clearfix" id="invertersales_report"></div>
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

function GetInverterSalesReports(){
	
	var store_name = $("#store_name").val();
	var store_id = $("#store_id").val();
	var fromdate = $("#fromdate").val();
	if (yesValidation("","fromdate","yes") == false)
	{
		return;
		
	}
	var todate = $("#todate").val();
	if (yesValidation("","fromdate","yes") == false)
	{
		return;
		
	}
	var brand_type = $("#brand_type").val();
	if (selectValidation("","brand_type","select") == false)
	{
		return;
		
	}
	$.ajax({
		type: "GET",
		url: "/bookbattery/servlet/BatterystoreInventoryDetails?hidWhatToDo=getsalesinverterreport",
		data: {fromdate : fromdate, todate: todate, brand_type: brand_type, store_name: store_name},
		success: function(data){
			
			//alert(data); 
			$("#invertersales_report").html(data);
					 
		}
	}); 

	
}
</script>
