<%--
    Document   : seelctlocation
    Created on : Feb 24, 2014, 12:22:12 PM
    Author     : Sai Krishna Daddala.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.LogLevel"%>
<%
String strUserid=(String)session.getAttribute("sesBatteryAdminName");
if(strUserid==null)
{
	strUserid="";
	session.setAttribute("sesErrorMsg","Session Timed Out. Please login again");
	response.sendRedirect("../../../../admin/index.html");
	return;
}
Vector LocationVector=(Vector)session.getAttribute("seslocatiovector");
LogLevel.DEBUG(1,new Throwable(),"LocationVector: "+LocationVector);



String keyword=(request.getParameter("keyword")!=null)?request.getParameter("keyword"):"";
String strFromDate=(request.getParameter("txtFromDate")!=null)?request.getParameter("txtFromDate"):"";
String strToDate=(request.getParameter("txtToDate")!=null)?request.getParameter("txtToDate"):"";%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../../images/favicon.png" type="image/x-icon">
<script language="JavaScript" src="../../../../js/jquery-1.8.2.js" ></script>
<title>BookBattery.com - India's No.1 Automobile Battery Store</title>
<script language="javascript">
function funClickSubmit()
{
	var city=document.misselconfirm.city.value;
	var keyword=document.misselconfirm.keyword.value;
	var strFromDate = document.misselconfirm.txtFromDate.value;
	var strToDate = document.misselconfirm.txtToDate.value;


		 if(document.misselconfirm.city.value == 0)
		 {
			errMsg ="<font color='#FF3333'>Please select City</font>";
			document.getElementById("displayerrormsg").innerHTML=errMsg;
			document.misselconfirm.city.focus();
			return ;
		 }
		
	document.misselconfirm.method="POST";
	document.misselconfirm.action="../../../../servlet/MISBatteryDetails?hidWhatToDo=getconfirmedorders&city="+city+"&keyword="+keyword+"&txtFromDate="+strFromDate+"&txtToDate="+strToDate;
	document.misselconfirm.submit();

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
<body  >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="misselconfirm">
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
					<table width="450" border="0" align="center" cellpadding="5" cellspacing="0" bgcolor="#FFFFFF">
					<tr><td align="left" class="subheading"></td><td width="50%"align="right"><a href="../../../../jsp/admin/batterystore/mis/selectpostponedate.jsp" class="onclick1">Back</a></td></tr>
					<tr>
						<td>
							<table width="450" align="center" cellspacing="1" cellpadding="0">
							<tr><td height="20"></td></tr>
							<tr>
								<td class="subheading" align="left" size="2">Postponed&nbsp;Orders</td>
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
														<td colspan="2" height="10" align="center" class="insidecontent"><div id="displayerrormsg"></div>&nbsp;</td>
													</tr>
													<tr>
														<td class="table1" align="right" height="25" width="40%">Select Location<sup color="Red"><font color="Red">*</font><sup>:&nbsp;</td>
														<td class="table1" align="left" width="60%">
															<select name="city" id="city" class="insidecontent" STYLE="width: 180px">
																<option value="0" >&lt;-&nbsp;&nbsp; Select Location &nbsp;&nbsp; --&gt;</option>
																<option value="ALL" >ALL</option>
																<%
																try
																   {
																	if(LocationVector!=null && LocationVector.size()>0)
																	{
																	for(int i=0;i<LocationVector.size();i++)
																	{
																		Hashtable ht=(Hashtable)LocationVector.get(i);
																		String city=(String)ht.get("city");
																%>
																<option value="<%=city%>"><%=city%></option>
																<%
																}
																}	
																}
																catch(Exception e )
																{
																e.printStackTrace();
																}
																%>
															</select>
														</td>
													</tr>
													<table border="0" width="90%" align="center">
													<tr><td height="5"></td><td></td></tr>
													<tr><td align="center" width="90%"><a href="javascript:funClickSubmit()" class="button4">Submit</a></td>
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
<input type="hidden" name="keyword" value="<%=keyword%>">
<input type="hidden" name="txtFromDate" value="<%=strFromDate%>">
<input type="hidden" name="txtToDate" value="<%=strToDate%>">
</form>
</center>
</body>
</html>