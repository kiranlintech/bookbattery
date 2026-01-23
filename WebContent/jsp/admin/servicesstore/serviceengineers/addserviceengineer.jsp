<%--
    Document   : batteryadminmain
    Created on : Aug 30, 2013, 4:22:12 PM
    Author     : Sai Krishna Daddala.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.util.ArrayList,java.util.Hashtable,java.util.Vector"%>
<%
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
<head>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="/bookbattery/images/favicon.png" type="image/x-icon">
<title>BookBattery.com-Online Battery Store</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-multiselect/0.9.13/css/bootstrap-multiselect.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">


<body>
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="applicationchatmapping" id="thisform" action="request_for_quote.asp"  method="post">
<!-- Battery Header Starts -->
<tr>
	<jsp:include page = "../header.jsp" />
</tr>
<!-- Battery Header Ends -->
<!--<tr>
	<td>
		<img src="/bookbattery/images/flag1234.JPG" width="880" height="15">
	</td>
</tr>-->
<tr>
	<td>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#ffffff">
		<tr>
			<td width="25%" align="left" valign="top" bgcolor="#ffffff">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<!-- Starts battery admin left Menu -->
				<tr>
					<jsp:include page="../../batterystore/batteryadminleftmenu.jsp"/>
				</tr>
				<!-- Ends battery admin left Menu -->
				</table>
			</td>
			<td width="75%" align="left" valign="top">
				<table width="100%" border="1" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">
					<!-- your page content starts here  -->
					<table width="100%" cellspacing="1" bgcolor="#FFFFFF" cellpadding="0" border="0">
					<tr>
						<td align="left" valign="top">
							<tr>
								<td class="subheading">Add&nbsp;Pincode&nbsp;Mapping&nbsp;Details</td>
								<td  align="right"><a href="/bookbattery/servlet/AdminBatteryLogin?hidWhatToDo=batteryadminhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
							</tr>
							<tr><td height="10"></td></tr>
							<tr><td align="center" class="insidecontentbat"><div id="displayappchatmaperrormsg"></div></td></tr>
							<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
								<div class="row">
								<div class="col-lg-2 col-md-4 col-sm-6 col-xs-12"></div>
								<div class="col-lg-6 col-md-4 col-sm-6 col-xs-12">
								<div style="color:red" id="divsessionmsg"></div>
								</div>
								<div class="col-lg-2 col-md-4 col-sm-6 col-xs-12">
								</div>
								</div>
								<div class="row" style="margin-left:10px">
								<div class="col-lg-6 col-md-4 col-sm-6 col-xs-12">
									<div class="form-group">
									  <label for="state">Select&nbsp;State <font color = "#FF0000">*</font>:</label>
									  <select class="form-control yes" name = "State" id = "State"></select>
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
							<div class="row" style="margin-left:10px"> 
							<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12" >
								<div class="form-group">
								  <label for="username">UserName<font color = "#FF0000">*</font>:</label>
								  <input type="text" class="form-control yes" name="username" maxlength="16" id="username">
								  <div id='username-error'style="display:none;"></div>
								</div> 
							</div>
							<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12" >
								<div class="form-group">
								  <label for="GST">Mobile&nbsp;Number<font color = "#FF0000">*</font>:</label>
								  <input type="text" class="form-control yes" name="mobilenumber" maxlength="16" id="mobilenumber">
								  <div id='mobilenumber-error'style="display:none;"></div>
								</div> 
							</div>
						</div>
						<div class="row">
							<div class="col-lg-6 col-md-12 col-sm-12 col-xs-12" >
								<button type="button" class="btn btn-primary pull-right" onclick="AddDetails()"> ADD&nbsp;Details</button>	
							</div> 
						</div> 
								</table>
								<tr>
								<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">

								</table>
								</td>
								</tr>
					<tr><td height="10"></td></tr>
			</td>
	</tr>
	</table>
	</td>
	</tr>
	</table>
	</td>
	</tr>
	</table>
	<!-- Inner content ends here -->
	<!-- Footer Starts Here -->
	<tr>
		<td>
			<jsp:include page = "../footer.jsp" />
		</td>
	</tr>                           
	<!-- footer Ends Here -->
</td>
</tr>
</table>
<input type="hidden" name="strEmail" value="">
</form>
</center>
<%
String strLogmapMsg=(String)session.getAttribute("sesmapErrorMsg");
if(strLogmapMsg ==null)
{
	strLogmapMsg="";
}
else
{
%>
<script type="text/javascript">
var loginmpamessg; 
loginmpamessg= "<%=strLogmapMsg%>";
document.getElementById("displayappchatmaperrormsg").innerHTML=loginmpamessg;
</script>
<%
	session.removeAttribute("sesmapErrorMsg");
}
%>
</body>

<script src="/bookbattery/js/jquery.min.js"></script>
<script src="/bookbattery/js/bootstrap.min.js"></script>
<script src="/bookbattery/js/bootstrap-multiselect.js"></script>

<script type="text/javascript">

$( document ).ready(function() {
	$("#State").html("<option>Loading ... </option>");
	 $.ajax({					 
		type: "GET",
		url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getstate",
		success: function(data)
		{	
			$("#State").html(data);
		}
	});
});

$( "#State" ).change(function() {
		var product_state=$("#State").val();
		
		if (product_state=="0" || product_state=="default" || product_state==""){
			$("#city").html("<option>Select City</option>");
		}
		else{

			$("#city").html("<option>Loading ... </option>");
			
			 $.ajax({					 
				type: "GET",
				url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getcity",
				data: {state:product_state},
				success: function(data)
				{	
					$("#city").html(data).focus();
				}
			});
		}

	});
	
	$( "#city" ).change(function() {
		var product_state=$("#State").val();
		var product_city=$("#city").val();
		
		if (product_city=="0" || product_city=="default" || product_city==""){
			$("#city").html("<option>Select City</option>");
		}
		else{

			$("#pincode").html("<option>Loading ... </option>");
			
			 $.ajax({					 
				type: "GET",
				url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getpincode",
				data: {state:product_state,city:product_city},
				success: function(data)
				{	
						$("#pincode").html(data);
						$('#pincode').attr('multiple', true);
						$('#pincode').val(''); 
						$('#pincode').multiselect('destroy');
						$('#pincode').multiselect({
						includeSelectAllOption: true,	
						buttonWidth: '300px',
						enableFiltering: true,
						maxHeight: 350 
						});
				}
			});
		}

	});
	
	function AddDetails(){
		
	var state =$("#State").val();
	var city =$("#city").val();
	var mobilenumber =$("#mobilenumber").val();
	var username =$("#username").val();

 	 $.ajax({
			type: "GET",
			 url: "/bookbattery/servlet/ServiceEngineerDetails?hidWhatToDo=insertserviceengineerdetails",
			data: {state:state, city: city, mobilenumber: mobilenumber, username: username },
			success: function(data){
				//alert(data); 
				/*if(data.indexOf("inserted")>=0)
				{
					document.getElementById("divsessionmsg").innerHTML = data;
					//document.getElementById("divsessionmsg").innerHTML = "Successfully Inserted Engineer Details";
					$('#applicationchatmapping').trigger("reset");
					//alert("Success")
				}
				if(data.indexOf("already")>=0)
				{
					document.getElementById("divsessionmsg").innerHTML = data;
					//document.getElementById("divsessionmsg").innerHTML = "Engineer Details Already Exists";
					$('#applicationchatmapping').trigger("reset");
					//alert("Success")
				}
				else
				{*/
					document.getElementById("divsessionmsg").innerHTML = data;
					//document.getElementById("divsessionmsg").innerHTML = "Failed to Updated GST Details";
					$('#applicationchatmapping').trigger("reset");
					//alert("Failure")
				//}
			}
		});
}
</script>

<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	/*background-image: url(/bookbattery/images/index_01_01.gif);
	background-repeat: repeat-x;*/
}
-->
</style>
<link href="/bookbattery/css/bookbattery.css" rel="stylesheet" type="text/css" />
</head>

</html>