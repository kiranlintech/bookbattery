<%--
    Document   : batteryadminmain
    Created on : Aug 30, 2013, 4:22:12 PM
    Author     : Sai Krishna Daddala.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.util.ArrayList,java.util.Hashtable,java.util.Vector"%>
<%
String strUserid=(String)session.getAttribute("sesBatteryOperatorName");
if(strUserid==null)
{
	strUserid="";
	session.setAttribute("sesErrorMsg","Session Timed Out. Please login again");
	response.sendRedirect("/bookbattery/operator/index.html");
	return;
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="/bookbattery/images/favicon.png" type="image/x-icon">
<title>BookBattery.com-Online Battery Store</title>
<link rel="stylesheet" href="/bookbattery/dev/includes/css/bootstrap.min.css" type="text/css">
<link rel="stylesheet" href="/bookbattery/dev/includes/css/custom.css?v=27" type="text/css">

<style type='text/css'>

.divpostponed{left:57%;top:30%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left; border:6px solid #424242; border-radius: 6px 6px 6px 6px;position:absolute;padding: 1px;display: none;z-index:0;}
.divpostponed1{left:50.5%;top:30%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left; border:6px solid #424242; border-radius: 6px 6px 6px 6px;position:absolute;padding: 1px;display: none;z-index:0;}
table.tableizer-table {
	border: 1px solid #a7bc7a; font-family: Arial, Helvetica, sans-serif
	font-size: 12px;
} 
.tableizer-table td {
	padding: 1px;
	margin: 0px;
	border: 1px solid #a7bc7a;
}
.tableizer-table th {
	background-color: #a7bc7a; 
	color: #FFF;
	font-weight: bold;
}
</style>

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
</head>
<body>
<form name="cancelorder" method="post" ENCTYPE="multipart/form-data">
<div id='divpostponed' class='divpostponed' style="display:none;"></div>
<div id='divpostponed1' class='divpostponed1' style="display:none;"></div>
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
					<jsp:include page="../operatorleftmenu.jsp"/>
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
								<td class="subheading">Convert Order From Online To Cash&nbsp;Order</td>
								<td  align="right"><a href="/bookbattery/servlet/OperatorLogin?hidWhatToDo=batteryoperatorhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
							</tr>
							<tr><td height="25"></td></tr>
							<tr><td align="center" class="insidecontentbat"><div id="displysesmsg"></div></td></tr>
							<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
							<tr>
									<td width="30%" class="insidecontent" align="right">Enter&nbsp;Ord&nbsp;Ref&nbsp;Number<font color="#ff3333">*</font>&nbsp;:</td>
									<td width="50%" class="insidecontent" align="left"><input type="text" name="ordrefnumber" id="ordrefnumber" size="20" maxlength="15" style=" width: 200px;">
									<div id="ordrefnumber-error" style="display:none;"></div>
									</td>
								</tr>
								</table>
								</td>
								</tr>
								<tr>
								<td>
								
								<table width="90%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center" >
									<div style=" font-size: 16px; font-weight: 700;  color: #FF9800;  display: none;" id="Order_details_div" >
										Order Details
									 </div>
									<tbody id="Order_details">
									</tbody>
								</table>
								<table width="90%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center"  >
								<div style=" font-size: 16px; font-weight: 700;  color: #FF9800;  display: none;"  id="Online_details_div">
									Online Payment Details
								 </div>
									<tbody id="Online_details">
									</tbody>
								</table>
								
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
								<tr>
									<td width="50%" class="insidecontent" align="right" id="update_button"><input type="button" value="Submit" class="button4" onclick="javascript:GetOrderDetails();"></td>
									<td width="50%" class="insidecontent" align="left"><input type="button" value="Reset" class="button4" onclick="javascript:FunReset();"></td>
								</tr>
								<tr><td height="15"></td><td></td></tr>
								</table>
								
						
								<tr>
									<td>
										<table class="table table-bordered" width="100%" border="0" align="center">
											<tr class="#FFFFFF" bgcolor="FFFFFF">
												<td width="40%"><div id="divordrefnumber"></div></td>
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

<script type="text/javascript" src="/bookbattery/dev/includes/js/jquery.min.js"></script>
<script type="text/javascript" src="/bookbattery/dev/includes/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/bookbattery/dev/js/valadation.js?v=20"></script>
<script type="text/javascript" src="/bookbattery/jsp/operator/order-from-online-to-cash/order-from-online-to-cash.js?v=4"></script>



<%
String strbatLogMsg=(String)session.getAttribute("sesErrorMsgss");
if(strbatLogMsg ==null)
{
	strbatLogMsg="";
}
else
{
%>
<script type="text/javascript">
var sesmessg; 
sesmessg= "<%=strbatLogMsg%>";
document.getElementById("displysesmsg").innerHTML=sesmessg;
</script>
<%
	session.removeAttribute("sesErrorMsgss");
}
%>
</body>
</html>