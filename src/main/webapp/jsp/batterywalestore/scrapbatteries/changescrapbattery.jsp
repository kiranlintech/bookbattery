
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
                         CHANGE SCRAP BATTERY PRICE
                      </h2> 
                  </div>
                  <div class="body">
                			<div class="alert bg-green" id="success_div" style="display:none">
                                Successfully Updated Scrap Battery Price.
                            </div> 
                            <div class="alert bg-red" id="failure_div" style="display:none">
                                 Failed to Update Scrap Battery Price.
                            </div>
					 <form class="form-horizontal" id="changepriceform">
                                <div class="row clearfix">
                                    <div class="col-lg-3 col-md-3 col-sm-5 col-xs-12">
                                        <label for="battery_type">Select Battery Type:&nbsp;<font color="red">*</font></label>
                                    </div>
                                    <div class="col-lg-9 col-md-9 col-sm-7 col-xs-12">
                                        <div class="form-group">
                                            <div class="form-line">
                                            	<select class="form-control" id="battery_type" name="battery_type" data-live-search="true">
                                            		<option value="-1">Select Batteries Type</option>
													<option value="Car Batteries">Car Batteries</option>
	 												<option value="Bike Batteries">Bike Batteries</option>
	 												<option value="Inverter Batteries">Inverter Batteries</option>                                           	
                                            	</select>
                                            	<div id="battery_type-error" style="display:none;"></div>
                                             </div>
                                        </div>
                                    </div>
                                </div> 
                                 
                                <div class="row clearfix">
                                    <div class="col-lg-3 col-md-3 col-sm-5 col-xs-12">
                                        <label for="scrap_price">Enter Scrap Price:&nbsp;<font color="red">*</font></label>
                                    </div>
                                    <div class="col-lg-9 col-md-9 col-sm-7 col-xs-12">
                                        <div class="form-group">
                                            <div class="form-line"> 
                                            	 <input type="text" id="scrap_price" class="form-control yes" placeholder="73.69">
                                            <div id="scrap_price-error" style="display:none;"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div> 
                                <input type="hidden" name="storeid" id="storeid" value="<%=strStoreId%>">
								<input type="hidden" name="storename" id="storename" value="<%=strStoreName%>">
                                <div class="row clearfix">
                                    <div class="col-lg-offset-4 col-md-offset-4 col-sm-offset-6 col-xs-offset-6">
                                        <button type="button" class="btn bg-blue-grey m-t-15 waves-effect" onclick="funtoChangeScrapDetails()">Update SCRAP PRICE</button>
                                         <button type="button" class="btn bg-brown m-t-15 waves-effect" onclick="funtoReset()">RESET</button>
                                    </div>
                                </div>
                            </form>

                  </div>
              </div>
           </div>
</section>

 <script src="http://materializecss.com/bin/materialize.js" />
 
 <jsp:include page = "../storefooter.jsp" />
 
<script type="text/javascript">
function funtoChangeScrapDetails(){
	
	var battery_type = $("#battery_type").val();
	if (selectValidation("","battery_type","select") == false)
	{
		return;
		
	} 
	var scrap_price	= $("#scrap_price").val();
	if (yesValidation("","scrap_price","yes") == false)
	{
		return;
		
	} 
	var storename =  $("#storename").val();
	var storeid	= $("#storeid").val();
	//alert(battery_type);
	//alert(scrap_price);
	  $.ajax({
			type: "GET",
			url: "/bookbattery/servlet/BatterystoreScrapDetails?hidWhatToDo=changescrapdetails",
			data: {battery_type: battery_type, scrap_price: scrap_price, storename: storename, storeid: storeid },
			success: function(data){
				//alert(data);
			
				if(data.indexOf("Sucessfully Updated")>=0)
				{
					 
					$("#success_div").show();
					$("#changepriceform")[0].reset();
				}
				else
				{
					 
					$("#failure_div").show();
					$("#changepriceform")[0].reset();
				} 
			 
				}
		});
}

function funtoReset(){
	
	$("#changepriceform")[0].reset();
}
</script>