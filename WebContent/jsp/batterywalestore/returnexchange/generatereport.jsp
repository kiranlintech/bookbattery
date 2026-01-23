
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.*,com.ngit.javabean.loglevel.LogLevel,java.util.Properties,java.io.FileInputStream"%>
<%
String strUserid=(String)session.getAttribute("sesStoreOperatorName");
LogLevel.DEBUG(5,new Throwable(),"Prasanna strUserid: "+strUserid);
session.setAttribute("sesBatteryOperatorName","storeoperator");
LogLevel.DEBUG(5,new Throwable(),"Prasanna sesBatteryOperatorName: "+strUserid);

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
                        <div class="header bg-red">
                            <h2>
                               GENERATE BATTERY RETURN/EXCHANGE REPORT
                            </h2>
                        </div>
                        <div class="body">
                               <div class="row clearfix">
                                <div class="col-sm-3">
                                <label for="form_date">Select From Date:&nbsp;<font color="red">*</font></label>
                                    <div class="form-group">
                                        <div class="form-line">
                                            <input type="text" class="datepicker form-control yes" id="fromdate" placeholder="Please choose a date..." readonly>
                                        	<div id='fromdate-error' style='display:none;'></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-3">
                                <label for="to_date">Select To Date:&nbsp;<font color="red">*</font></label>
                                    <div class="form-group">
                                        <div class="form-line">
                                            <input type="text" class="datepicker form-control yes" id="todate" placeholder="Please choose a date..." readonly>
                                       		<div id='todate-error' style='display:none;'></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
                                <label for="to_date">Select Report Type:&nbsp;<font color="red">*</font></label>
								<div class="form-group">
									<div class="form-line"> 
										<select class="form-control yes" id="report_type">
											 <option value="">Choose Report Type</option>
											 <option value="Battery Return">Battery Return</option>
											 <option value="Battery Exchange">Battery Exchange</option>
										 </select> 
									</div>
									<div id='report_type-error' style='display:none;'></div>  
								</div>
								
							 </div>
							 
								<div class="col-lg-3 col-md-3 col-sm-3 col-xs-12">
	                                <label for="to_date">Select Brand:&nbsp;</label>
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
										<div id="brand_type-error" style="display:none;"></div>
									</div>
								
								</div>
                            </div>
                            <div class="row clearfix">
                            	<div class="col-sm-4">
                                    <div class="form-group">
                                         <button type="button" class="btn bg-red waves-effect" onclick="GetReports()">
											<i class="material-icons">search</i>
											<span>GENERATE REPORT</span>
										</button>  
                                    </div>
                                </div>
                            </div>
                            <div class="row clearfix" id="return_report"></div>
						</div>
                    </div>
                </div>
            </div>
            <!-- #END# Advanced Select -->
        </div>
    </section>

<input type="hidden" name="store_id" id="store_id" value="<%=strStoreId%>">
<input type="hidden" name="store_name" id="store_name" value="<%=strStoreName%>">
 
<div class="modal fade" id="defaultModal" tabindex="-1" role="dialog">
   
</div>	
<jsp:include page = "../storefooter.jsp" />		
<script type="text/javascript">

$('#fromdate').bootstrapMaterialDatePicker({ format : 'YYYY-MM-DD', weekStart : 0, time: false });
$('#todate').bootstrapMaterialDatePicker({ format : 'YYYY-MM-DD', weekStart : 0, time: false });

function GetReports(){
	
	 var fromdate = $("#fromdate").val();
	 if (yesValidation("","fromdate","yes") == false)
	 {
		return;
		
	 }
	 var todate = $("#todate").val();
		if (yesValidation("","todate","yes") == false)
		{
			return;
			
		}
	 var report_type = $("#report_type").val();
		if (selectValidation("","report_type","select") == false)
		{
			return;
			
		}
	 var brand_type =  $("#brand_type").val();
	 var store_id =  $("#store_id").val();
	 var store_name =  $("#store_name").val();
	 
	 if(report_type=="Battery Return"){
		 
		 Servlet_Name="BatteryReturn_Details";
		 
	 } else {
		 
		 Servlet_Name="BatteryExchange_Details";
	 }
	 
	 $.ajax
		({					 
			url: "/bookbattery/servlet/BatterystoreScrapDetails?hidWhatToDo="+Servlet_Name+"",
			type: 'GET',
			data:{
				
				FDate:fromdate,
				TDate:todate,
				Brand:brand_type,
				SID:store_id,
				SNAME:store_name
			},
		success: function(data){
			
			//alert(data); 
			$("#return_report").html(data);
					 
		}
		});
	 

}
</script>