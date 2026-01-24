<%--
    Document   : searchorder.jsp
    Created on : Feb 8th, 2016
    Author     : Lavanya Chowdary.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.util.ArrayList,java.util.Hashtable,java.util.Vector"%>
<%
String strUserid=(String)session.getAttribute("sesBatteryOperatorName");
if(strUserid==null)
{
	strUserid="";
	session.setAttribute("sesErrorMsg","Session Timed Out. Please login again");
	response.sendRedirect("../../../operator/index.html");
	return;
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../images/favicon.png" type="image/x-icon">
<title>BookBattery.com-India's No.1 Automobile Services Store</title>
<script language="JavaScript" src="../../../js/jquery-1.8.2.js" ></script>
<script src="../../../js/jquery-1.3.2.min.js"></script>
<script language=javascript src="../../../js/datepicker.js"></script>
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
<script type="text/javascript">
function funToGetOreRefNumber()
{
	var ordrefnumber = document.searchorder.ordrefnumber.value;
 
	 if(ordrefnumber == "")
	 {
		errMsg ="<font color='#ff3333'>Please enter \'Ord Ref Number\'.</font>";
		document.getElementById("displyoredmsg").innerHTML=errMsg;
		document.searchorder.ordrefnumber.focus();
		return ;
	 }
	if (/[ord][ORD]{2}/i.test(document.searchorder.ordrefnumber.value) != true)
	{
		errMsg ="<font color='#ff3333'>\'Ord Ref Number\' format should be \'ORD123456\' .</font>";
		document.getElementById("displyoredmsg").innerHTML=errMsg;
		document.searchorder.ordrefnumber.focus();
		return ;
	}



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
			//alert(xmlhttp.readyState);
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
					if(resp=="ERROR")
					{
						alert("Error occurred.Please try again.");
						return;
					}
					else if(resp.indexOf("Invalid")>=0)
					{
					document.getElementById("divordrefnumber").innerHTML = resp;
					document.getElementById("divordrefnumber").innerHTML = xmlhttp.responseText;
					document.getElementById("displyoredmsg").innerHTML ="";
					}
					else
					{
					document.getElementById("divordrefnumber").innerHTML = resp;
					document.getElementById("divordrefnumber").innerHTML = xmlhttp.responseText;
					}
				}
			}			
		}
		xmlhttp.open("POST","../../../servlet/SearchOrdRefNumber?hidWhatToDo=getordrefnumbernew&ordrefnumber="+ordrefnumber,true);	
		xmlhttp.send();	
}



function closemobilediv()
{
	$('#divpostponed').hide();
}
function closemobilediv1()
{
	$('#divpostponed1').hide();
}
function FunReset()
{
	location.href="../../../jsp/serviceoperator/cancelorder/searchorder.jsp"
}
</script>
<style type="text/css">
</style>
<link href="../../../css/sgservices.css" rel="stylesheet" type="text/css" />
</head>
<body onload="document.searchorder.ordrefnumber.focus();" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="searchorder" method="post" ENCTYPE="multipart/form-data">
<div id='divpostponed' class='divpostponed' style="display:none;"></div>
<div id='divpostponed1' class='divpostponed1' style="display:none;"></div>
<!-- Services Header Starts -->
<tr>
	<jsp:include page = "../header.jsp" />
</tr>
<!-- Services Header Ends -->
<tr>
	<td>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#ffffff">
		<tr>
			<td width="25%" align="left" valign="top" bgcolor="#ffffff">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<!-- Starts services admin left Menu -->
				<tr>
					<jsp:include page="../operatorleftmenu.jsp"/>
				</tr>
				<!-- Ends services admin left Menu -->
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
								<td class="subheading">Search&nbsp;Order</td>
								<td  align="right"><a href="../../../servlet/OperatorLogin?hidWhatToDo=batteryoperatorhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
							</tr>
							<tr><td height="25"></td></tr>
							<tr><td align="center" class="insidecontentservices"><div id="displyoredmsg"></div></td></tr>
							<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
							<tr>
									<td width="30%" class="insidecontent" align="right">Enter&nbsp;Ord&nbsp;Ref&nbsp;Number<font color="#ff3333">*</font>&nbsp;:</td>
									<td width="50%" class="insidecontent" align="left"><input type="text" name="ordrefnumber" size="20" onkeydown="if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:funToGetOreRefNumber(this);return false;} else return true;" maxlength="15"></td>
								</tr>
								</table>
								</td>
								</tr>
								<tr>
								<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
								<tr>
									<td width="50%" class="insidecontent" align="right"><input type="button" value="Submit" class="button4" onclick="javascript:funToGetOreRefNumber(this);"></td>
									<td width="50%" class="insidecontent" align="left"><input type="button" value="Reset" class="button4" onclick="javascript:FunReset();"></td>
								</tr>
								<tr><td height="15"></td><td></td></tr>
								</table>
								<tr>
									<td>
										<table width="100%" border="0" align="center">
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
<%
String strservicesLogMsg=(String)session.getAttribute("sesbatterydetailsErrorMsg");
if(strservicesLogMsg ==null)
{
	strservicesLogMsg="";
}
else
{
%>
<script type="text/javascript">
var sesmessg; 
sesmessg= "<%=strservicesLogMsg%>";
document.getElementById("displysesmsg").innerHTML=sesmessg;
</script>
<%
	session.removeAttribute("sesbatterydetailsErrorMsg");
}
%>
</body>
</html>