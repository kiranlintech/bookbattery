<%-- 
Author(s)			: Bharath Kumar.
Created on			: November 01, 2017.
Copyright Notice	: BookBattery Pvt.Ltd. Confidential
Description			: Add GSTID Retailers.
--%>
<%@page language="java" import="javax.servlet.ServletContext"%>
<%
ServletContext context = getServletContext();
String strUserid=(String)session.getAttribute("sesBatteryAdminName");
if(strUserid==null)
{
	strUserid="";
	session.setAttribute("sesErrorMsg","Session Timed Out. Please login again");
	response.sendRedirect("/bookbattery/admin/index.html");
	return;
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link type="text/css" href="/bookbattery/css/bookbattery.css" rel="stylesheet"/>
<head>

<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<link rel="stylesheet" href="/bookbattery/dev/includes/css/bootstrap.min.css?v=2" type="text/css">
<script language="JavaScript" src="/bookbattery/js/jquery-1.8.2.js" ></script>
<script type="text/javascript" src="/bookbattery/js/retailerreg.js"></script>
<link rel="shortcut icon" href="/bookbattery/images/favicon.ico" type="image/x-icon">
<h1><title>BookBattery.com-Online Battery Store</title></h1>
<style>
.lighterShadeappend{font-family:Tahoma;font-size:11px;color:#000000;text-decoration:none}
</style>
</head>
<body>
<center>
		<form name="editretailer" id="thisform" method="post">
			<div id='popup' class='popup' style="display:none;">
			<div id='popuptitle' class='popuptitle'>
			<table border='0' width='410px' height='10px' cellpadding='0' cellspacing='0'>
				<tr class='top1'>
				<td>&nbsp;<font color='#FFFFFF'>BookBattery</font></td><td align='right'><a href='javascript:closePopup(greyout(false));' class="bluelinks333">x</a></td>
				</tr>
			</table>
			</div>
			<div id='popupmessage' class='popupmessage'></div> 
			</div>
			<table cellpadding="0" cellspacing="0" border="0" width="960">
				<tr>
					<td>
						<jsp:include page = "../header.jsp" />
					</td>
				</tr>
				<tr>
					<td align="left" valign="top" bgcolor="#FFFFFF">
						<table width="960" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="180" height="400" align="left" valign="top">
									<table width="180" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="180" height="438" align="left" valign="top">
												<table width="100%" border="0" cellspacing="0" cellpadding="0">
													<tr>
														<jsp:include page="../batteryadminleftmenu.jsp"/>
													</tr>
												</table>
											</td>
											<td width="680" align="left" valign="top">
												<table width="600" cellspacing="1" bgcolor="#BEADCB" cellpadding="0">
													<tr>
														<td>
															<table  class="table table-bordered" width="100%" cellspacing="0" cellpadding="0" style="margin-left: 10px;">
																<tr>
																	<td align="left" valign="top">
																		 
																			 
																			<div class="row">
																				<div class="col-lg-10 col-md-4 col-sm-6 col-xs-12">
																					<strong>EDIT GST ID:</strong>
																				</div>
																				<div class="col-lg-2 col-md-4 col-sm-6 col-xs-12"><a href="/bookbattery/servlet/AdminBatteryLogin?hidWhatToDo=batteryadminhome" class="onclick1">Back&nbsp;&nbsp;</a>
																				</div>
																			</div>
																			<br/>
																			<div class="row">
																				<div class="col-lg-2 col-md-4 col-sm-6 col-xs-12"></div>
																				<div class="col-lg-6 col-md-4 col-sm-6 col-xs-12">
																					<div style="color:red" id="divsessionmsg"></div>
																				</div>
																				<div class="col-lg-2 col-md-4 col-sm-6 col-xs-12">
																				</div>
																			</div>
																			<div class="row">
																				 
																				<div class="col-lg-6 col-md-4 col-sm-6 col-xs-12">
																					<div class="form-group">
																					  <label for="state">Select&nbsp;State <font color = "#FF0000">*</font>:</label>
																					  <select class="form-control yes" name = "State" id = "State" onchange = "retailersetcities();clearfields()" onchange="javascript:searchKeyPress11(event);"><jsp:include page = "state.jsp" /></select>
																					  <div id='state-error'style="display:none;"></div>
																					</div>
																				</div>
																				<div class="col-lg-6 col-md-4 col-sm-6 col-xs-12">
																					<div class="form-group">
																					  <label for="batterymodel">Select&nbsp;City <font color = "#FF0000">*</font>:</label>
																					  <select class="form-control yes" name = "city" id = "city" ><option value="0">Select City</option></select>
																					  <div id='city-error'style="display:none;"></div>
																					</div>
																				</div> 
 																			</div>
																		<div class="row">
																			<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12" >
																				<div class="form-group">
																				  <label for="Retailer">Select&nbsp;Retailer <font color = "#FF0000">*</font>:</label>
																				  <select  class="form-control yes" name = "retailername" id = "retailername" onchange = "getGSTdetails(this.value);cleargstfield()"><option value="0"> Select Retailer </option></select>
																				  <div id='retailername-error'style="display:none;"></div>
																				</div> 
																			</div> 
																			<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12" >
																				<div class="form-group">
																				  <label for="GST">GST&nbsp;ID<font color = "#FF0000">*</font>:</label>
																				  <input type="text" class="form-control yes" name="gstid" maxlength="16" id="gstid">
																				  <div id='gstid-error'style="display:none;"></div>
																				</div> 
																			</div>
																		</div> 
																		<div class="row">
																			<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12" >
																				<button type="button" class="btn btn-primary pull-right" onclick="UpdateGSTDetails()"> Update</button>	
																			</div> 
																		</div> 
  																	</td> 
																</tr>
															</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
						
					
				</tr>
				
				<!-- Footer Starts Here -->
				<tr>
					<td>
						<jsp:include page = "../footer.jsp" />
					</td>
				</tr>                           
				<!-- footer Ends Here -->
			
			</table>
		</form>
	</center>

</body>

<script type="text/javascript">
	(function() {
	var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
	po.src = '/bookbattery/js/pophide.js';
	var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
	})();
	(function() {
	var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
	po.src = '/bookbattery/js/jquery-1.3.2.min.js';
	var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
	})();
	
$(document).ready(function(){
		$categorycity = $('#city');
        $categorycity.change(
            function() {
			var splitvalm =$categorycity.val();
			var state = document.editretailer.State.value;
			if(splitvalm == "default" || splitvalm == 0)
			 {
				/*for(i=document.editretailer.model.options.length-1;i>=1;i--)
				{
				document.editretailer.model.remove(i);
				}
				document.editretailer.batname.focus();*/
			 }
			 else
			 {
                $.ajax({
                    type: "GET",
                     url: "/bookbattery/servlet/RetailerLocationMap?hidWhatToDo=getretailers",
                    data: {state:state, city: $categorycity.val() },
                    success: function(data){
                        $("#retailername").html(data)
						document.editretailer.retailername.focus();
                    }
                });
			 }
            }
        );
	});
	
function getGSTdetails(rname)	{
	
 	 $.ajax({
			type: "GET",
			 url: "/bookbattery/servlet/RetailerRegistration?hidWhatToDo=getgstiddetails",
			data: {retailername:rname},
			success: function(data){
				
				//$("#gstid").html(data)
				$("#gstid").val(data);
				document.editretailer.gstid.focus();
			}
		});
				
}
function UpdateGSTDetails()	{
	
	var state =$("#State").val();
	var city =$("#city").val();
	var retailername =$("#retailername").val();
	var gstid =$("#gstid").val();
	
 	 $.ajax({
			type: "GET",
			 url: "/bookbattery/servlet/RetailerRegistration?hidWhatToDo=updategstiddetails",
			data: {retailername:retailername, state:state, city: city, gstid: gstid },
			success: function(data){
				//alert(data); 
				if(data.indexOf("Success")>=0)
				{
					document.getElementById("divsessionmsg").innerHTML = data;
					document.getElementById("divsessionmsg").innerHTML = "Successfully Updated GST Details";
					$('#editretailer').trigger("reset");
				}
				else
				{
					document.getElementById("divsessionmsg").innerHTML = data;
					document.getElementById("divsessionmsg").innerHTML = "Failed to Updated GST Details";
					$('#editretailer').trigger("reset");

					
				}
			}
		});
				
}
function clearfields(){
	
	$("#retailername").val('');
	$("#gstid").val('');
	document.getElementById("divsessionmsg").innerHTML = "";
	
}
function cleargstfield(){
	
 	$("#gstid").val('');
	
}
</script>
</html>
