<%--
    Document   : batteryadminmain
    Created on : Aug 30, 2013, 4:22:12 PM
    Author     : Sai Krishna Daddala.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.util.ArrayList,java.util.Hashtable,java.util.Vector"%>
<%response.setHeader("Pragma","no-cache");  
response.setHeader("Cache-Control","no-store");  
response.setHeader("Expires","0");  
response.setDateHeader("Expires",-1);  
%>
<%
String strUserid=(String)session.getAttribute("sesBatteryAdminName");
if(strUserid==null)
{
	strUserid="";
	session.setAttribute("sesErrorMsg","Session Timed Out. Please login again");
	response.sendRedirect("/bookbattery/admin/index.html");
	return;
}
String strLogMsg=(String)session.getAttribute("sesErrorMsg");
if(strLogMsg==null)
{
	strLogMsg="";
}
else
{
	session.removeAttribute("sesErrorMsg");
}
Vector alist=(Vector)session.getAttribute("sesCountofBatterynrand");
Vector laptopbrandcount=(Vector)session.getAttribute("sesLaptopBrandCount");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="/bookbattery/images/favicon.png" type="image/x-icon">
<title>BookBattery.com - India's No.1 Automobile Battery Store</title>
<script language="javascript">
function serprovhome()
    {		
		document.serhome.method="post";
		//calling the adminhome action from AdminHomeServlet.java
        document.serhome.action="/bookbattery/servlet/AdminHomeServlet?hidWhatToDo=adminhome";
        document.serhome.submit();
    }
	function noBack()
{
	window.history.forward(-3)
	session.invalidate();
	window.location.href="/bookbattery/jsp/admin/batterystore/batteryadminlogin.jsp";
}
window.onpageshow = function(evt) { if (evt.persisted) noBack() }
window.onunload = function() { void (0) }
</script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	/*background-image: url(../../../images/index_01_01.gif);
	background-repeat: repeat-x;*/
}
-->
</style>
<link href="/bookbattery/css/bookbattery.css" rel="stylesheet" type="text/css" />
</head>
<body onload="noBack();" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="batteryadminmain" action="request_for_quote.asp"  method="post">
<!-- Battery Header Starts -->
<tr>
	<jsp:include page = "header.jsp" />
</tr>
<!-- Battery Header Ends -->
<tr>
	<td>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#ffffff">
		<tr>
			<td width="25%" align="left" valign="top" bgcolor="#ffffff">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<!-- Starts battery admin left Menu -->
				<tr>
					<jsp:include page="batteryadminleftmenu.jsp"/>
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
								<td class="subheading">Welcome Administrator!</td>
							</tr>
							<tr><td height="10"></td></tr>
							<td>
								<table width="80%" cellspacing="0" bgcolor="#FFFFFF" cellpadding="0" border="1" align="center">
								<tr>
									<td bgcolor="#BEADCB" class="insidecontent" valign="top" height="2" align="center"><b>Summary :</b></td></tr>
									<tr>
									<td>
										<table  width="100%" cellspacing="1"  border="0" cellpadding="2" bgcolor="#FFFFFF" align="center" height="100">
										<%
										try
										  {
											int i=0;
											Hashtable ht=(Hashtable)alist.get(i);
											String strbatterycount=String.valueOf(ht.get("count"));

											int j=0;
											Hashtable latopbrandcount=(Hashtable)laptopbrandcount.get(j);
											String strlatopbrandcount=String.valueOf(latopbrandcount.get("count"));
										%>
										<tr align="left">
											<br><td width="50%" align="right" valign="top" class="subheading" >&nbsp;&nbsp;<strong>Battery&nbsp;Brands </strong></td>
											<td width="50%" align="left" valign="top" class="insidecontent">&nbsp:&nbsp;<%=strbatterycount%></a></b></td>
										</tr>
										<tr align="left">
											<br><td width="50%" align="right" valign="top" class="subheading" >&nbsp;&nbsp;<strong>Laptop&nbsp;Battery&nbsp;Brands </strong></td>
											<td width="50%" align="left" valign="top" class="insidecontent">&nbsp:&nbsp;<%=strlatopbrandcount%></a></b></td>
										</tr>
										<%
										}
										catch(Exception e)
										{
											e.printStackTrace();
										}
										%>
										</table>
									</td>
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