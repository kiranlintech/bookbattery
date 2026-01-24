
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
                        <div class="header bg-light-green">
                            <h2>
								CHECK PAYMENT STATUS
                            </h2>
                        </div>
                        <div class="body">
                              <!-- Inline Layout -->
								 
									<form>
										<div class="row clearfix">
											<div class="col-lg-3 col-md-3 col-sm-3 col-xs-6">
												
											</div>
											<div class="col-lg-3 col-md-3 col-sm-3 col-xs-6">
												 <div class="input-group">
													<span class="input-group-addon">
														
													</span>
													<div class="form-line">
														<input type="text" class="form-control yes" id="order_number" placeholder="Enter Order Number">
													</div>
												</div>
 											</div>
											<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
												 
												<button type="button" class="btn bg-light-green waves-effect" onclick="GetPaymentStatus()">
													<i class="material-icons">search</i>
													<span>GET PAYMENT DETAILS</span>
												</button>
											</div>
										</div>
										<div id="orderdetails_div"></div>
									</form>
											 
								<!-- #END# Inline Layout -->
						</div>
                    </div>
                </div>
            </div>
            <!-- #END# Advanced Select -->
        </div>
    </section>



<jsp:include page = "../storefooter.jsp" />		

<script type="text/javascript">
function GetPaymentStatus(){
	
	var order_number = $("#order_number").val();
	//alert(order_number);
	$.ajax({
			type: "GET",
			url: "/bookbattery/servlet/CheckOrderDetails?hidWhatToDo=getpaymentstatus",
			data: {ordernumber: order_number },
			success: function(data){
				//alert(data);
				document.getElementById("orderdetails_div").innerHTML=data;
			}
		}); 
}
</script>


				