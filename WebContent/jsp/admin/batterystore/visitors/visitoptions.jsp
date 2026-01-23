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
<title>BookBattery.com-India's No.1 Automobile Battery Store</title>
<script language="javascript">
function seldate()
{
	var param = "fromdate";

	if($("#"+param).is(':visible'))
	{
	$("#"+param).hide();
	}
}
function showseldates()
{
	var varRadioGroup = document.forms["visitoptions"].rdbWhatSelected;
	var dvalue1;
	var param = "fromdate";
	for (i=0;i<varRadioGroup.length;i++)
	{
		if(varRadioGroup[i].checked)
		{
			dvalue1=varRadioGroup[i].value;
		}
			if(dvalue1 == "selected")
			{
				$("#"+param).show();
			}
			else
			{
				$("#"+param).hide();
				document.getElementById("showerror").innerHTML="";
			}
	}
}
function funClickSubmit()
{
	var varRadioGroup = document.forms["visitoptions"].rdbWhatSelected;
	var fdate=document.visitoptions.txtFromDate1.value;
	var tdate=document.visitoptions.txtToDate1.value;
	var mySplitResult = fdate.split("-");
	var fday=mySplitResult[0];
	var fmonth=mySplitResult[1];
	var fyear=mySplitResult[2];
	var mySplitResult1 = tdate.split("-");
	var tday=mySplitResult1[0];
	var tmonth=mySplitResult1[1];
	var tyear=mySplitResult1[2];
	date = new Date();
	var month = date.getMonth()+1;
	var day = date.getDate();
	var year = date.getFullYear();
	var dvalue;
	for (i=0;i<varRadioGroup.length;i++)
	{
		if(varRadioGroup[i].checked)
		{
			dvalue=varRadioGroup[i].value;
		}
	}
if(dvalue=="selected")
	{
	if(fdate == "")
		{
			errMsg ="<font color='#9D0E0E' class='vrb10'>Please select From Date</font>";
			document.getElementById("showerror").innerHTML=errMsg
			document.visitoptions.txtFromDate1.focus();
			return;
		}
	if(tdate == "")
		{
			errMsg ="<font color='#9D0E0E' class='vrb10'>Please select To Date</font>";
			document.getElementById("showerror").innerHTML=errMsg
			document.visitoptions.txtToDate1.focus();
			return;
		}
	if(tyear<fyear)
		{
			errMsg ="<font color='#9D0E0E' class='vrb10'>Selected  \"To\" Date Should Be Greater Than \"From\" Date.</font>";
			document.getElementById("showerror").innerHTML=errMsg
			document.visitoptions.txtToDate1.focus();
			return;
		}
	if(tyear==fyear)
		{
			if(tmonth<fmonth)
			{
				errMsg ="<font color='#9D0E0E' class='vrb10'>Selected  \"To\" Date Should Be Greater Than \"From\" Date.</font>";
				document.getElementById("showerror").innerHTML=errMsg
				document.visitoptions.txtToDate1.focus();
				return;
			}
		}
	if(tyear==fyear && tmonth==fmonth)
		{
			if(tday<fday)
			{
				errMsg ="<font color='#9D0E0E' class='vrb10'>Selected  \"To\" Date Should Be Greater Than \"From\" Date.</font>";
				document.getElementById("showerror").innerHTML=errMsg
				document.visitoptions.txtToDate1.focus();
				return;
			}
		}
	if(tyear>year || fyear>year)
		{
				errMsg ="<font color='#9D0E0E' class='vrb10'>Please check the selected date.</font>";
				document.getElementById("showerror").innerHTML=errMsg
				document.visitoptions.txtToDate1.focus();
				return;
		}
	if(tyear==year)
		{
			if(tmonth>month)
			{
				errMsg ="<font color='#9D0E0E' class='vrb10'>Selected  \"To\" Date Should Be Greater Than \"From\" Date.</font>";
				document.getElementById("showerror").innerHTML=errMsg
				document.visitoptions.txtToDate1.focus();
				return;
			}
		}
	if(tyear==fyear && tmonth==fmonth)
		{
			if(tday<fday)
			{
				errMsg ="<font color='#9D0E0E' class='vrb10'>Selected  \"To\" Date Should Be Greater Than \"From\" Date.</font>";
				document.getElementById("showerror").innerHTML=errMsg
				document.visitoptions.txtToDate1.focus();
				return;
			}
		}
	if(tyear>year || fyear>year)
		{

				errMsg ="<font color='#9D0E0E' class='vrb10'>Please check the selected date.</font>";
				document.getElementById("showerror").innerHTML=errMsg
				document.visitoptions.txtToDate1.focus();
				return;
		}
	if(tyear==year)
		{
			if(tmonth>month)
			{	
				errMsg ="<font color='#9D0E0E' class='vrb10'>Please check the selected date.</font>";
				document.getElementById("showerror").innerHTML=errMsg
				document.visitoptions.txtToDate1.focus();
				return;
			}
		}
	if(fyear ==year)
		{
			if(fmonth>month)
			{
				errMsg ="<font color='#9D0E0E' class='vrb10'>Please check the selected date.</font>";
				document.getElementById("showerror").innerHTML=errMsg
				document.visitoptions.txtToDate1.focus();
				return;
			}
		}
	if(fyear==year)
		{
			if(fmonth==month)
			{
				if(fday>day)
				{	
					errMsg ="<font color='#9D0E0E' class='vrb10'>Please check the selected date.</font>";
					document.getElementById("showerror").innerHTML=errMsg
					document.visitoptions.txtToDate1.focus();
					return;
				}
			}
		}
	if(tyear ==year )
		{
			if(tmonth==month)
			{
				if(tday>day)
				{
					errMsg ="<font color='#9D0E0E' class='vrb10'>Please check the selected date.</font>";
					document.getElementById("showerror").innerHTML=errMsg
					document.visitoptions.txtToDate1.focus();
					return;
				}
			}
		}
	}
	
	document.visitoptions.method="Post";
	document.visitoptions.action="../../../../jsp/admin/batterystore/visitors/selectoptions.jsp?dates="+dvalue+"&txtFromDate="+fdate+"&txtToDate="+tdate;
	document.visitoptions.submit();
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
<form name="visitoptions">
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
					<tr>
						<td>
							<table width="450" align="center" cellspacing="1" cellpadding="0">
							<tr><td height="20"></td></tr>
							<tr>
								<td class="subheading" align="left" size="2">Visitors in BookBattery</td>
							</tr>
							<tr>
								<td>
									<table width="100%" cellspacing="1" bgcolor="#8B8D90" cellpadding="0">
									<tr>
										<td  bgcolor="#0081C7" align="center" class="subheading"><font color="#FFFFFF">Date&nbsp;Range</font></td>
									</tr>
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
															<input type="radio" name="rdbWhatSelected" onClick="showseldates();" value="current"  checked onChange="Checkreset()" ></td>
															<td class="table1" align="left" width="60%"><a href="../../../../jsp/admin/batterystore/visitors/selectoptions.jsp?dates=current" class="insidecontent"  style="text-decoration:none">&nbsp;&nbsp;Current Month</a>
															</td>
														</tr>
														<tr>
															<td class="table2" align="right" height="25" width="50%">
															<input type="radio" name="rdbWhatSelected" onClick="showseldates();" value="candpmonth"   onChange="Checkreset()" >
															</td>
															<td class="table2" align="left" width="50%"><a href="../../../../jsp/admin/batterystore/visitors/selectoptions.jsp?dates=candpmonth" class="insidecontent"  style="text-decoration:none">&nbsp;&nbsp;From Previous Month</a>
															</td>
														</tr>
														<tr>
															<td class="table1" align="right" height="25" width="50%">
															<input type="radio" name="rdbWhatSelected" onClick="showseldates();" value="sixmonths"  onChange="Checkreset()" >
															</td>
															<td class="table1" align="left" width="50%"><a href="../../../../jsp/admin/batterystore/visitors/selectoptions.jsp?dates=sixmonths" class="insidecontent"  style="text-decoration:none">&nbsp;&nbsp;Past Six Months</a>
															</td>
														</tr>
														<tr>
															<td class="table2" align="right" height="25" width="50%">
															<input type="radio" name="rdbWhatSelected" onClick="showseldates();" value="selected" >
															</td>
															<td class="table2" align="left" height="25" width="50%">&nbsp;&nbsp;Specify a Date Range
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td>
												<div id="fromdate">
													<table width="100%" align="center" border="0" cellspacing="0" bgcolor="#FFFFFF" cellpadding="0">
														<tr >
															<td class="table1" align="right" width="40%" class="insidecontent">From Date</td>	 
															<td class="table1" width="60%" align="left">&nbsp;:&nbsp;&nbsp;<input type="text" name="txtFromDate1" readonly  onChange="CheckDate(this)" onKeyDown="FormatDate(this,  window.event.keyCode,'down')" onKeyUp="FormatDate(this, window.event.keyCode,'up')" value="" size="10" maxlength="10"  >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="../../../../images/calender.jpg" valign="middle" style="cursor:hand" onclick="displayDatePicker('txtFromDate1', this);"></td> 
														</tr>
														<tr>
															<td class="table2" align="right" width="40%" class="insidecontent">To Date</td> 
															<td class="table2" width="60%" align="left">&nbsp;:&nbsp;&nbsp;<input type="text"  name="txtToDate1" readonly  onChange="CheckDate(this)" onKeyDown="FormatDate(this,  window.event.keyCode,'down')" onKeyUp="FormatDate(this, window.event.keyCode,'up')" value="" size="10" maxlength="10"  >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="../../../../images/calender.jpg" valign="middle" style="cursor:hand" onclick="displayDatePicker('txtToDate1', this);">
															</td>
														</tr>
														</table>
													</div>
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