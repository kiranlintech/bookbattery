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
<script language=javascript src="../../../../js/datepicker.js"></script> 
<script src="../../../../js/jquery-1.3.2.min.js"></script>
<title>BookBattery.com - India's No.1 Automobile Battery Store</title>
<script language="javascript">
function funClickSubmit()
{
	var varRadioGroup = document.forms["retlocareaoption"].rdbWhatSelected;
	for (i=0;i<varRadioGroup.length;i++)
	{
		if(varRadioGroup[i].checked)
		{
			dvalue=varRadioGroup[i].value;
		}
	}
	
	document.retlocareaoption.method="Post";
	document.retlocareaoption.action="../../../../servlet/RetailerLocationMap?hidWhatToDo="+dvalue;
	document.retlocareaoption.submit();
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
<body onload="seldate();" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="retlocareaoption">
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
					<table width="100%" cellspacing="1" align="center" bgcolor="#FFFFFF" cellpadding="0" border="0">
					<tr>
						<td class="subheading" align="left" size="2">Delete Retailer Location or Pincode Mapping</td>
						<td  align="right"><a href="../../../../servlet/AdminBatteryLogin?hidWhatToDo=batteryadminhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
					</tr>
					<table width="450" border="0" align="center" cellpadding="5" cellspacing="0" bgcolor="#FFFFFF">
					<tr>
						<td>
							<table width="100%" align="center" cellspacing="0" cellpadding="0">
							<tr><td height="20"></td></tr>
							
							<tr>
								<td>
									<table width="100%" cellspacing="0" bgcolor="#8B8D90" cellpadding="0">
									<tr>
										<td bgcolor="#FFFFFF" align="center">
											<table width="80%" align="center" border="0" cellspacing="0" bgcolor="#FFFFFF" cellpadding="0">
											<tr>
												<td>
													<table width="100%" align="center" border="0" cellspacing="0" bgcolor="#FFFFFF" cellpadding="0">
														<tr><td height="10"></td></tr>
														<tr >
															<td colspan="2" height="10" align="center" class="insidecontent"><div id="showerror"></div>&nbsp;</td>
														</tr>
														<tr>
															<td class="table1" align="right" height="25" width="40%">
															<input type="radio" name="rdbWhatSelected" value="getstatetodelete"  checked onChange="Checkreset()" ></td>
															<td class="table1" align="left" width="60%"><a href="../../../../servlet/RetailerLocationMap?hidWhatToDo=getstatetodelete" class="insidecontent"  style="text-decoration:none">&nbsp;&nbsp;Delete with Area</a>
															</td>
														</tr>
														<tr>
															<td class="table2" align="right" height="25" width="50%">
															<input type="radio" name="rdbWhatSelected" value="pincode"   onChange="Checkreset()" >
															</td>
															<td class="table2" align="left" width="50%"><a href="../../../../servlet/RetailerLocationMap?hidWhatToDo=pincode" class="insidecontent"  style="text-decoration:none">&nbsp;&nbsp;Delete with Pincode</a>
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<table align="center" border="0" width="100%">
											<tr><td height="5"></td><td></td></tr>
											<tr>
												<td align="center" width="100%"><a href="javascript:funClickSubmit()" class="button4">&nbsp;Submit&nbsp;</a></td>
											</tr>
											<tr><td height="5"></td><td></td></tr>
											</table>
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
</body>
</html>