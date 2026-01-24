
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
                        <div class="header bg-deep-orange">
                            <h2>
                               GENERATE INVENTORY REPORT
                            </h2>
                        </div>
                        <div class="body">
                               <div class="row clearfix">
                                <div class="col-sm-4">
                                    <div class="form-group">
                                        <div class="form-line">
                                            <input type="text" class="datepicker form-control yes" id="fromdate" placeholder="Please choose a date..." readonly>
                                       	    <div id='fromdate-error' style='display:none;'></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-4">
                                    <div class="form-group">
                                        <div class="form-line">
                                            <input type="text" class="datepicker form-control yes" id="todate" placeholder="Please choose a date..." readonly>
                                       		<div id='todate-error' style='display:none;'></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-4">
                                    <div class="form-group">
                                         <button type="button" class="btn bg-deep-orange waves-effect" onclick="GetReports()">
											<i class="material-icons">search</i>
											<span>GENERATE REPORT</span>
										</button>  
                                    </div>
                                </div>
                            </div>
                            <div class="row clearfix" id="inventory_report"></div>
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
  var store_name = $("#store_name").val();
   
	$.ajax({
			type: "GET",
			url: "/bookbattery/servlet/BatterystoreInventoryDetails?hidWhatToDo=getinventoryreport",
			data: {fromdate : fromdate, todate: todate, store_name: store_name},
			success: function(data){
				
				//alert(data); 
				$("#inventory_report").html(data);
						 
			}
		}); 
 
}
</script>