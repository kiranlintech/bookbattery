<%--
    Document   : select options
	File name  : selectoptions.jsp
    Created on : Feb 9th, 2016
    Author     : Lavanya Chowdary.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.util.ArrayList,java.util.Hashtable,java.util.Vector"%>
<%
String strUserid=(String)session.getAttribute("sesServicesOperatorName");
if(strUserid==null)
{
	strUserid="";
	session.setAttribute("sesErrorMsg","Session Timed Out. Please login again");
	response.sendRedirect("../../../../operator/index.html");
	return;
}
String strDateOpt=request.getParameter("dates");
if(strDateOpt==null || strDateOpt.equals(""))
	strDateOpt="current";

String strFromDate=(request.getParameter("txtFromDate")!=null)?request.getParameter("txtFromDate"):"";
String strToDate=(request.getParameter("txtToDate")!=null)?request.getParameter("txtToDate"):"";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../../images/favicon.png" type="image/x-icon">
<title>BookBattery.com-India's No.1 Automobile Services Store</title>
<script language="javascript">
function funClickSubmit()
{
	var strDateOpts = document.selectoption.dates.value;
	var strFromDates = document.selectoption.txtFromDate.value;
	var strToDates = document.selectoption.txtToDate.value;
	
		document.selectoption.method="POST";
		document.selectoption.action="../../../../servlet/ServicesVisitors?hidWhatToDo=getvisitorsoperator&dates="+strDateOpts+"&txtFromDate="+strFromDates+"&txtToDate="+strToDates+"&type=order";
		document.selectoption.submit();
}
</script>
<style type="text/css">
</style>
<link href="../../../../css/sgservices.css" rel="stylesheet" type="text/css" />
</head>
<body >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="selectoption">
<!-- Services Header Starts -->
<tr>
	<jsp:include page = "../../header.jsp" />
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
							<jsp:include page="../../operatorleftmenu.jsp"/>
						</tr>
					<!-- Ends services admin left Menu -->
					</table>
				</td>
				<td width="75%" align="left" valign="top">
					<table width="100%" border="1" cellspacing="0" cellpadding="0">
						<tr>
							<td align="left" valign="top">
							<!-- your page content starts here  -->
								<table width="100%" cellspacing="1" align="center" bgcolor="#FFFFFF" cellpadding="0" border="0">
									<table width="450" border="0" align="center" cellpadding="5" cellspacing="0" bgcolor="#FFFFFF">
										<tr><td align="left" class="subheading"></td><td width="50%"align="right"><a href="../../../../jsp/operator/servicesstore/visitors/visitoptions.jsp?dates=<%=strDateOpt%>&txtFromDate=<%=strFromDate%>&txtToDate=<%=strToDate%>" class="onclick1">Back</a></td></tr>
					<tr>
						<td>
							<table width="450" align="center" cellspacing="1" cellpadding="0">
							<tr><td height="20"></td></tr>
							<tr>
								<td class="subheading" align="left" size="2"><%=strDateOpt%> >> Select&nbsp;Options</td>
							</tr>
							<tr>
								<td>
									<table width="100%" cellspacing="1" bgcolor="#8B8D90" cellpadding="0">
										<tr>
											<td  bgcolor="#0081C7" align="center" class="subheading"><font color="#FFFFFF">Select&nbsp;Options</font></td>
										</tr>
											<tr>
												<td bgcolor="#FFFFFF" align="center">
													<table width="80%" align="center" border="0" cellspacing="0" bgcolor="#FFFFFF" cellpadding="0">
														<tr><td height="10"></td></tr>
														<tr >
															<td colspan="2" height="10" align="center" class="insidecontent"><div id="showerror"></div>&nbsp;</td>
														</tr>
														<tr>
															<td class="table1" align="right" height="25" width="35%">
															<input type="radio" name="rdbWhatSelected" value="order"  checked  onChange="Checkreset()" ></td>
															<td class="table1" align="left" width="65%"><a href="../../../../servlet/ServicesVisitors?hidWhatToDo=getvisitorsoperator&dates=<%=strDateOpt%>&txtFromDate=<%=strFromDate%>&txtToDate=<%=strToDate%>&type=order" class="insidecontent"  style="text-decoration:none">&nbsp;Services Visitors</a>
															</td>
														</tr>
														
													
														
														<tr><td height="10"></td><td></td></tr>
														<table align="center" border="0" width="100%">
														<tr><td height="5"></td><td></td></tr>
														<tr>
															<td align="center" width="100%"><a href="javascript:funClickSubmit()" class="button4">&nbsp;Submit&nbsp;</a></td>
														</tr>
														<tr><td height="5"></td><td></td></tr>
														</table>
														</table>
													</table>
												</td>
											</tr>
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
			<jsp:include page = "../../footer.jsp" />
		</td>
	</tr>                           
	<!-- footer Ends Here -->
</td>
</tr>
</table>
<input type="hidden" name="hidWhatToDo" value="overallmis">
<input type="hidden" name="dates" value="<%=strDateOpt%>">
<input type="hidden" name="txtFromDate" value="<%=strFromDate%>">
<input type="hidden" name="txtToDate" value="<%=strToDate%>">
</form>
</center>
</body>
</html>