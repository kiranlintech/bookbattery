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
	response.sendRedirect("../../../../admin/index.html");
	return;
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../../images/favicon.png" type="image/x-icon">
<title>BookBattery.com - India's No.1 Automobile Battery Store</title>
<script language="javascript">
function funToAddlocation()
{
		var state = document.locandarea.state.value;
		var locaton = document.locandarea.locaton.value;
		var area = document.locandarea.area.value; 
			
		var iChars3 = "`~!@#$%^&*()+=-_[]\\\';,/{}|\":<>?";
		var regularexp=/^[^\s\-].*[^\s\-]$/;
		var regularexp1=/^([0-9]+)$/;

		if(document.locandarea.state.value == "" || state=="")
		  {
			errMsg ="<font color='#ff3333'>Please select \'State\'.</font>";
			document.getElementById("displaylocationerrormsg").innerHTML=errMsg;
			document.locandarea.state.focus();
			return ;
         }
		if(locaton == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Location\'.</font>";
			document.getElementById("displaylocationerrormsg").innerHTML=errMsg;
			document.locandarea.locaton.focus();
			return ;
         }
		if (document.locandarea.locaton.value.length < 3)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 3 characters in the \'Location\' field.</font>";
			document.getElementById("displaylocationerrormsg").innerHTML=errMsg;
			document.locandarea.locaton.focus();
			return ;
         }
		if (document.locandarea.locaton.value.length > 49)
		 {
			errMsg ="<font color='#ff3333'>Please enter only 49 characters in the in the \'Location\' field.</font>";
			document.getElementById("displaylocationerrormsg").innerHTML=errMsg;
			document.locandarea.locaton.focus();
			return ;
         }
		 if (document.locandarea.locaton.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the \'Location\' field.</font>";
			document.getElementById("displaylocationerrormsg").innerHTML=errMsg;
			document.locandarea.locaton.focus();
			return ;
         }
		for (var i = 0; i < document.locandarea.locaton.value.length; i++)
		{
         if (iChars3.indexOf(document.locandarea.locaton.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#ff3333'>Special characters are not allowed in \'Location\' field.</font>";
			document.getElementById("displaylocationerrormsg").innerHTML=errMsg;
			document.locandarea.locaton.focus();
			return ;
         }
		}
		if (document.locandarea.locaton.value.match(regularexp1))
		{
			errMsg ="<font color='#ff3333'>Please enter only Characters in the \'Location\' field.</font>";
			document.getElementById("displaylocationerrormsg").innerHTML=errMsg;
			document.locandarea.locaton.focus();
			return ;
		}
		if (! document.locandarea.locaton.value.match(regularexp))
		{
			errMsg ="<font color='#ff3333'>\'Location\' has Unnecessary spaces. Please remove spaces.</font>";
			document.getElementById("displaylocationerrormsg").innerHTML=errMsg;
			document.locandarea.locaton.focus();
			return ;
		}
		if(area == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Area\'.</font>";
			document.getElementById("displaylocationerrormsg").innerHTML=errMsg;
			document.locandarea.area.focus();
			return ;
         }
		if (document.locandarea.area.value.length < 3)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 3 characters in the \'Area\' field.</font>";
			document.getElementById("displaylocationerrormsg").innerHTML=errMsg;
			document.locandarea.area.focus();
			return ;
         }
		if (document.locandarea.area.value.length > 49)
		 {
			errMsg ="<font color='#ff3333'>Please enter only 49 characters in the in the \'Area\' field.</font>";
			document.getElementById("displaylocationerrormsg").innerHTML=errMsg;
			document.locandarea.area.focus();
			return ;
         }
		 if (document.locandarea.area.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the \'Area\' field.</font>";
			document.getElementById("displaylocationerrormsg").innerHTML=errMsg;
			document.locandarea.area.focus();
			return ;
         }
		for (var i = 0; i < document.locandarea.area.value.length; i++)
		{
         if (iChars3.indexOf(document.locandarea.area.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#ff3333'>Special characters are not allowed in \'Area\' field.</font>";
			document.getElementById("displaylocationerrormsg").innerHTML=errMsg;
			document.locandarea.area.focus();
			return ;
         }
		}
		if (document.locandarea.area.value.match(regularexp1))
		{
			errMsg ="<font color='#ff3333'>Please enter only Characters in the \'Area\' field.</font>";
			document.getElementById("displaylocationerrormsg").innerHTML=errMsg;
			document.locandarea.area.focus();
			return ;
		}
		if (! document.locandarea.area.value.match(regularexp))
		{
			errMsg ="<font color='#ff3333'>\'Area\' has Unnecessary spaces. Please remove spaces.</font>";
			document.getElementById("displaylocationerrormsg").innerHTML=errMsg;
			document.locandarea.area.focus();
			return ;
		}
		
	document.locandarea.action = "../../../../servlet/AddBatteryDetails?hidWhatToDo=insertlocandarea&state="+state+"&locaton="+locaton+"&area="+area;
	//alert(document.addbattery.action);
	document.locandarea.method="post";
	document.locandarea.submit();
}
function FunReset()
{
	location.href="../../../../jsp/admin/batterystore/locationarea/locandarea.jsp"
}
</script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	/*background-image: url(../../../../images/index_01_01.gif);
	background-repeat: repeat-x;*/
}
-->
</style>
<link href="../../../../css/bookbattery.css" rel="stylesheet" type="text/css" />
</head>
<body onload="document.locandarea.state.focus();" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="locandarea" method="post" ENCTYPE="multipart/form-data">
<script type="text/javascript" src="../../../../js/cluetip_new.js"> </script>
<!-- Battery Header Starts -->
<tr>
	<jsp:include page = "../header.jsp" />
</tr>
<!-- Battery Header Ends -->
<!--<tr>
	<td>
		<img src="../../../../images/flag1234.JPG" width="880" height="15">
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
					<jsp:include page="../batteryadminleftmenu.jsp"/>
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
								<td class="subheading">Add&nbsp;Location&nbsp;and&nbsp;Area</td>
								<td  align="right"><a href="../../../../servlet/AdminBatteryLogin?hidWhatToDo=batteryadminhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
							</tr>
							<tr><td height="10"></td></tr>
							<tr><td align="center" class="insidecontentbat"><div id="displaylocationerrormsg"></div></td></tr>
							<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
								<tr>
									<td width="39%" class="insidecontent" align="right">Select&nbsp;State<font color="FF0000">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left">
									<select id="state"  name="state" class="insidecontent" style='width:145px'>
									<option value="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;select&nbsp;State</option>
										<option value="Andhra Pradesh">Andhra Pradesh</option>
										<option value="Arunachal Pradesh">Arunachal Pradesh</option>
										<option value="Assam">Assam</option>
										<option value="Bihar">Bihar</option>
										<option value="Chattisgarh">Chattisgarh</option>
										<option value="Delhi">Delhi</option>
										<option value="Goa">Goa</option>
										<option value="Gujarat">Gujarat</option>
										<option value="Haryana">Haryana</option>
										<option value="Himachal Pradesh">Himachal Pradesh</option>
										<option value="Jammu and Kashmir">Jammu and Kashmir</option>
										<option value="Jharkhand">Jharkhand</option>
										<option value="Karnataka">Karnataka</option>
										<option value="Kerala">Kerala</option>
										<option value="Madhya Pradesh">Madhya Pradesh</option>
										<option value="Maharashtra">Maharashtra</option>
										<option value="Manipur">Manipur</option>
										<option value="Meghalaya">Meghalaya</option>
										<option value="Mizoram">Mizoram</option>
										<option value="Nagaland">Nagaland</option>
										<option value="Orissa">Orissa</option>
										<option value="Punjab">Punjab</option>
										<option value="Rajasthan">Rajasthan</option>
										<option value="Sikkim">Sikkim</option>
										<option value="Tamil Nadu">Tamil Nadu</option>
										<option value="Telangana">Telangana</option>
										<option value="Tripura">Tripura</option>
										<option value="Union Territories">Union Territories</option>
										<option value="Uttarakhand">Uttarakhand</option>
										<option value="Uttar Pradesh">Uttar Pradesh</option>
										<option value="West Bengal">West Bengal</option>
									</select></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Location<font color="red">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="locaton" size="20" maxlength="50"><span class="hint">Provide Location in the format 'Bangalore'.<span class="hint-pointer">&nbsp;</span></span></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Area<font color="red">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="area" size="20" maxlength="50"><span class="hint">Provide Area in the format 'Koramangla'.<span class="hint-pointer">&nbsp;</span></span></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right"><input type="button" value="Submit" class="button4" onclick="javascript:funToAddlocation();"></td><td width="1%"></td>
									<td width="59%" class="insidecontent" align="left"><input type="button" value="Reset" class="button4" onclick="javascript:FunReset();"></td>
								</tr>
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
String strLogMsg=(String)session.getAttribute("seslocandareaErrorMsg");
if(strLogMsg ==null)
{
	strLogMsg="";
}
else
{
%>
<script type="text/javascript">
var loginmessg; 
loginmessg= "<%=strLogMsg%>";
document.getElementById("displaylocationerrormsg").innerHTML=loginmessg;
</script>
<%
	session.removeAttribute("seslocandareaErrorMsg");
}
%>
</body>
</html>