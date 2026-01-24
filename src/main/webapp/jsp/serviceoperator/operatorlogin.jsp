<%-- 
Document   : operatorlogin.jsp
Created on : Feb 8th, 2016
Author     : Lavanya Chowdary.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Enumeration,java.util.Properties,java.io.FileInputStream"%>
<%
String strUserid=(String)session.getAttribute("sesServicesOperatorName");
if(strUserid==null)
{
	strUserid="";
}
String sesErrorMsg=(String)session.getAttribute("sesErrorMsg");
if(sesErrorMsg==null)
{
	sesErrorMsg="";
}
else
{
	session.removeAttribute("sesErrorMsg");
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="shortcut icon" href="../../images/favicon.png" type="image/x-icon">
<h1><title>BookBattery.com-India's No.1 Automobile Services Store</title></h1>
<meta name="keywords" content="operator page of BookBattery, Operator login page for BookBattery"/>
<meta name="description" content="BookBattery operator page"/>
<script src="../../js/jquery-1.3.2.min.js"></script>
<script language="JavaScript" src="../../js/pophide.js" ></script>
<script language="javascript">
function searchKeyPress(e)        
{   
	if (window.event) { e = window.event; }  
	if (e.keyCode == 13)
	{                        
		document.getElementById('btnSearch').click(); 
	}        
}
function check()
{
	var strUserid="<%=strUserid%>";
	if(strUserid=="null" || strUserid=="" || strUserid==null)
	{
		window.close();
		window.opener.location ="../../admin/index.html";
	}
	else
	{
	}
}	 
function onfocus()
{
	var un=document.operatorlogin.username.value;
	if(un == "")
	{
		document.operatorlogin.username.focus();
		return;
	}
}
function noBack()
{
	window.history.forward(-3)
	session.invalidate();
	window.location.href="../../admin/index.html";
}
window.onpageshow = function(evt) { if (evt.persisted) noBack() }
window.onunload = function() { void (0) }

function close_popupl()
{
	$('#popup').hide();
	greyout(false);
	document.operatorlogin.username.focus();
}
function close_popup2()
{
	$('#popup').hide();
	greyout(false);
	document.operatorlogin.passwd.focus();
}
function login()
{
	var un=document.operatorlogin.username.value;
	var pwd=document.operatorlogin.passwd.value;
	if(un == "")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please enter user name.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popupl();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if(pwd == "")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please enter password.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup2();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	document.operatorlogin.method="post";
	document.operatorlogin.action="../../servlet/OperatorLogin?hidWhatToDo=operatorlogin";
	document.operatorlogin.submit();
}
function closePopup()
{
	$('#popup').hide();
	greyout(false);
}
</script>
<style type="text/css">
</style>
<link href="../../css/sgservices.css" rel="stylesheet" type="text/css" />
</head>
<body  onload="document.operatorlogin.username.focus();">
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="operatorlogin">
<!-- Services Header Starts -->
<tr>
	<jsp:include page = "header.jsp" />
</tr>
<!-- Services Header Ends -->
<tr>
	<td align = "left" valign = "top" bgcolor = "#FFFFFF">
	<!-- Inner content should be within the below table -->
	<table width="100%" bgcolor="#FFFFFF" class="insidecontent" cellspacing="0" cellpadding="0" >
	<tr><td height="2"></td></tr>
	<tr><td height="85"></td></tr>
	<tr>
		<td valign="top" width="60%">
		<!--Popup messages div starts -->
	<div id='popup' class='popup' style="display:none;">
		<div id='popuptitle' class='popuptitle'><table border='0' width='410px' height='10px' cellpadding='0' cellspacing='0'>
                <tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'>
                <a href='javascript:closePopup(greyout(false));' class="bluelinks333">x</a></td></tr></table>
		</div>
		<div id='popupmessage' class='popupmessage'></div> 
	</div>
	<!--Popup messages div Ends -->
			<table width="100%" border="0" >
			<tr>
				<td>
					<table width="350" border="1" cellpadding="0" align="center"cellspacing="0" bgcolor="#FFFFFF">
					<tr>
						<td bgcolor="#FFFFFF" class="prodheading" height="25">Operator Login: </td>
					</tr>
					<tr>
						<td>
							<table width="100%" cellspacing="1" bgcolor="#FFFFFF" cellpadding="0">
							<tr>
								<td>
									<tr>
										<td height="35"><%=sesErrorMsg%> </td>
									</tr>
									<tr>
										<td>
											<table width="281" border="0" cellspacing="0" cellpadding="0" align="center" >
											<tr>
												<td height="35" align="right" valign="middle" class="insidecontent">User Name: <font color="#FF0000">*</font></td>
												<td height="35" align="left" valign="middle"><input type="text" name="username" /></td>
											</tr>
											<tr>
												<td height="35" align="right" valign="middle" class="insidecontent">Password:<font color="#FF0000">*</font></td>
												<td height="35" align="left" valign="middle"><input type="password" name="passwd" onkeydown="if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:login();return false;} else return true;" onkeypress="searchKeyPress(event);"/></td>
											</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td>
											<table width="220" border="0" cellspacing="2" cellpadding="0" align="center">
											<tr>
												<td width="116" height="25" align="center" valign="middle">&nbsp;</td>
												<td width="69" align="center" valign="middle"><input type="button" id="btnSearch" class="smallbutton" Value="Login" onclick="login();"></td><td></td>
												<td width="54" align="center" valign="middle"><span class="links"><button onclick="javascript:reset()" class="smallbutton">Reset</span></td>
												<td width="92" align="center" valign="middle">&nbsp;</td>
											</tr>
											</table>
										</td>
									</tr>
								</td>
							</tr>
							<tr height="10">
								<td colspan="2">&nbsp;</td>
							</tr>
							</table>
							</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr><td height="85"></td></tr>
			</table>
		</td>
	</tr>
	<!-- Inner content ends here -->
	<!-- Footer Starts Here -->
	<tr>
		<td>
			<jsp:include page = "footer.jsp" />
		</td>
	</tr>                           
	<!-- footer Ends Here -->
</td>
</tr>
</table>
<input type="hidden" name="strEmail" value="">
</form>
</center>
</body>
</html>
