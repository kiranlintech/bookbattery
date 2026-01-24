<%--
    Document   : retailerlocationmapping
    Created on : Oct 30, 2013, 4:22:12 AM
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

Vector CityVector=(Vector)session.getAttribute("sescityvector");
LogLevel.DEBUG(1,new Throwable(),"CityVector: "+CityVector);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../../images/favicon.png" type="image/x-icon">
<title>BookBattery.com - India's No.1 Automobile Battery Store</title>
<script language="JavaScript" src="../../../../js/jquery-1.8.2.js" ></script>
<script type="text/javascript">
function funToAddAppRetPercent(varButton)
{
	var city=document.retpercent.city.value;
	var srptax=document.retpercent.srptax.value;
	//var erptax=document.retpercent.erptax.value;

	var iChars3 = "`~!@#$%^&*()+=-_[]\\\';,/{}|\":<>?";
	var regularexp=/^[^\s\-].*[^\s\-]$/;
	var regularexp1=/[^0-9\.]/g;

	if(document.retpercent.city.value == 0 || city == "default")
	 {
		errMsg ="<font color='#ff3333'>Please select \'City\'.</font>";
		document.getElementById("displayappretpercenterrormsg").innerHTML=errMsg;
		document.retpercent.city.focus();
		return ;
	 }
	 if(srptax == "")
	 {
		errMsg ="<font color='#ff3333'>Please enter \'SRP Percentage\'.</font>";
		document.getElementById("displayappretpercenterrormsg").innerHTML=errMsg;
		document.retpercent.srptax.focus();
		return ;
	 }
	 if (srptax.match(regularexp1))
	 {
		errMsg ="<font color='#ff3333'>Please enter \'SRP Percentage as format of 1.035\'.</font>";
		document.getElementById("displayappretpercenterrormsg").innerHTML=errMsg;
		document.retpercent.srptax.focus();
		return ;
	 }
	 if(isNaN(srptax) || srptax.indexOf(".")<0)
	{
      errMsg ="<font color='#ff3333'>Please enter \'SRP Percentage as format of 1.035\'.</font>";
		document.getElementById("displayappretpercenterrormsg").innerHTML=errMsg;
		document.retpercent.srptax.focus();
		return ;
   }
	/* if(erptax == "") 
	 {
		errMsg ="<font color='#ff3333'>Please enter \'ERP Percentage\'.</font>";
		document.getElementById("displayappretpercenterrormsg").innerHTML=errMsg;
		document.retpercent.erptax.focus();
		return ;
	 }
	  if (erptax.match(regularexp1))
	 {
		errMsg ="<font color='#ff3333'>Please enter \'ERP Percentage as format of 0.35\'.</font>";
		document.getElementById("displayappretpercenterrormsg").innerHTML=errMsg;
		document.retpercent.erptax.focus();
		return ;
	 }*/
	

	document.retpercent.method="post";
	document.retpercent.action="../../../../servlet/GenerateRetailerInvoice?hidWhatToDo=insertretpercent&city="+city+"&srptax="+srptax;
	document.retpercent.submit();
	varButton.disabled=true;
}
function FunReset()
{
	location.href="../../../../jsp/admin/batterystore/percentage/retailerpercent.jsp"
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
<body onload="document.retpercent.city.focus(); noBack();" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="retpercent">
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
								<td class="subheading">Add&nbsp;Retailer&nbsp;Invoice&nbsp;Percentage</td>
								<td  align="right"><a href="../../../../servlet/AdminBatteryLogin?hidWhatToDo=batteryadminhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
							</tr>
							<tr><td height="10"></td></tr>
							<tr><td align="center" class="insidecontentbat"><div id="displayappretpercenterrormsg"></div></td></tr>
							<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
								<tr>
									<td width="39%" class="insidecontent" align="right">Select&nbsp;City<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">
									<select name='city' id='city'  class="insidecontent" style='width:180px'>
										<option value='0'>&lt;-&nbsp;Select&nbsp;City&nbsp;-&gt;</option>
										<%
										try
										   {
											if(CityVector!=null && CityVector.size()>0)
											{
											for(int i=0;i<CityVector.size();i++)
											{
												Hashtable ht=(Hashtable)CityVector.get(i);
												String location=(String)ht.get("location");
										%>
										<option value="<%=location%>"><%=location%></option>
										<%
										}
										}	
										}
										catch(Exception e )
										{
										e.printStackTrace();
										}
										%>
									</select></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Enter&nbsp;SRP&nbsp;Percentage(%)<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="srptax" size="20" maxlength="8"></td>
								</tr>
								<!--<tr>
									<td width="39%" class="insidecontent" align="right">Enter&nbsp;ERP&nbsp;Percentage(%)<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="erptax" size="20" maxlength="8"></td>
								</tr>-->
								<tr>
									<td width="39%" class="insidecontent" align="right"><font color="FF0000">*</font>&nbsp;Note</td>
									<td width="59%" class="insidecontent" align="left">please enter 3.5% as 1.035 [OR] 14.5% as 1.145.</td>
								</tr>
								<!--<tr>
									<td width="39%" class="insidecontent" align="right">City&nbsp;Local&nbsp;Tax<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="localtax" size="20" maxlength="50"></td>
								</tr>-->
								</td>
								</tr>
								</table>
								<tr>
								<td>
								<table width="100%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
								<tr>
									<td width="50%" class="insidecontent" align="right"><input type="button" value="Submit" class="button4" onclick="javascript:funToAddAppRetPercent(this);"></td>
									<td width="50%" class="insidecontent" align="left"><input type="button" value="Reset" class="button4" onclick="javascript:FunReset();"></td>
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
String strLogmapMsg=(String)session.getAttribute("sespercentErrorMsg");
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
document.getElementById("displayappretpercenterrormsg").innerHTML=loginmpamessg;
</script>
<%
	session.removeAttribute("sespercentErrorMsg");
}
%>
</body>
</html>