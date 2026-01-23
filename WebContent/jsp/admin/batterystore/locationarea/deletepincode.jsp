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
<script language="JavaScript" src="../../../../js/jquery-1.8.2.js" ></script>
<script language="javascript">
$(document).ready(function(){
		$category = $('#state');
		 $category.change(
            function() {
			 var splitval =$category.val();
			 var keywords = "deletepincode";
			 if(splitval == "")
			 {
				  $('#divlocandarea').hide();
				for(i=document.deletepincode.city.options.length-1;i>=1;i--)
				{
				document.deletepincode.city.remove(i);
				}
				document.deletepincode.state.focus();
			 }
			 else
			 {
				  $('#divlocandarea').hide();
                $.ajax({
                    type: "GET",
                     url: "../../../../servlet/AddBatteryDetails?hidWhatToDo=getlocation",
                    data: {state: $category.val(), keyword: keywords },
                    success: function(data){
                        $("#city").html(data)
						document.deletepincode.city.focus();
                    }
                });
			 }
            }
        );
	});
function getlocandarea()
{
	var state = document.deletepincode.state.value; 
	var city = document.deletepincode.city.value;
	var keywords = "deletepincode";

	var xmlhttp= "";
	var resp= "";
	if (window.XMLHttpRequest)
	{
		// code for IE7+, Firefox, Chrome, Opera, Safari
		 xmlhttp=new XMLHttpRequest();
	}
	else
	{
		// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if (xmlhttp.readyState==4)
		{
			if(xmlhttp.status!=200)
			{
				alert("Error Occured. Please try again.");
				return;
			}
			else
			{
				 $('#divlocandarea').show();
				resp = xmlhttp.responseText;
				if(resp=="ERROR")
				{
					alert("Error occurred.Please try again.");
					return;
				}
				document.getElementById("divlocandarea").innerHTML = resp;
				document.getElementById("divlocandarea").innerHTML = xmlhttp.responseText;
			}
		}			
	}
	xmlhttp.open("POST","../../../../servlet/AddBatteryDetails?hidWhatToDo=getlocandaraetodelete&state="+state+"&city="+city+"&keyword="+keywords,true);		
	xmlhttp.send();	
	}
function deletelocandareas(strlocid)
{
	var keywords = "deletepincode";
	var xmlhttp= "";
	var resp= "";
	var url ="../../../../servlet/AddBatteryDetails?hidWhatToDo=deletelocandarea&chkSi="+strlocid+"&keyword="+keywords;
	if (window.XMLHttpRequest)
	{
		// code for IE7+, Firefox, Chrome, Opera, Safari
		 xmlhttp=new XMLHttpRequest();
	}
	else
	{
		// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if (xmlhttp.readyState==4)
		{
			if(xmlhttp.status!=200)
			{
				alert("Error Occured. Please try again.");
				return;
			}
			else
			{
				resp = xmlhttp.responseText;
				document.getElementById("displaylocationerrormsg").innerHTML = resp;
				document.getElementById("displaylocationerrormsg").innerHTML = xmlhttp.responseText;
				getlocandarea()
			}
		}			
	}
	var agree=confirm("Are You sure want to delete Pincode details! ");
	if (agree)
	{
	xmlhttp.open("GET",url, true);		
	xmlhttp.send();	
	}
}
function FunReset()
{
	location.href="../../../../jsp/admin/batterystore/locationarea/deletepincode.jsp"
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
<body onload="document.deletepincode.state.focus();" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="deletepincode" method="post" ENCTYPE="multipart/form-data">
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
								<td class="subheading">Delete&nbsp;Pincode</td>
								<td  align="right"><a href="../../../../servlet/AdminBatteryLogin?hidWhatToDo=batteryadminhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
							</tr>
							<tr><td height="10"></td></tr>
							<tr><td align="center" class="insidecontentbat"><div id="displaylocationerrormsg"></div></td></tr>
							<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
								<tr>
									<td width="39%" class="insidecontent" align="right">Select&nbsp;State<font color="FF0000">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left">
									<select id="state"  name="state" class="insidecontent"  style='width:150px'>
									<option value="">&lt;-&nbsp;Select State&nbsp;-&gt;</option>
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
									<td width="39%" class="insidecontent" align="right">Select&nbsp;City<font color="FF0000">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left">
									<select name="city" id ="city" onChange="javascript:getlocandarea();" class="insidecontent" style='width:150px'>
									<option value="0" >&lt;-&nbsp;Select City&nbsp;-&gt;</option>
									</select></td>
								</tr>
								</table>
								<tr><td><div id="divlocandarea"></td></tr>
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