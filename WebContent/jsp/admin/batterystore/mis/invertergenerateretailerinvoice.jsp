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
	response.sendRedirect("/bookbattery/admin/index.html");
	return;
}

Vector RetailerVector=(Vector)session.getAttribute("sesretailervector");
LogLevel.DEBUG(1,new Throwable(),"RetailerVector: "+RetailerVector);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="/bookbattery/images/favicon.png" type="image/x-icon">
<script language=javascript src="/bookbattery/js/datepicker.js"></script> 
<title>BookBattery.com - India's No.1 Automobile Battery Store</title>
<script language="JavaScript" src="/bookbattery/js/jquery-1.8.2.js" ></script>
<script type="text/javascript">
function funToGenerateinvoice(varButton)
{
	var retailer=document.invgenretinvoice.retailer.value;
	var fdate=document.invgenretinvoice.txtFromDate1.value;
	var tdate=document.invgenretinvoice.txtToDate1.value;
	
	var mySplitResult = fdate.split("-");
	var fday=mySplitResult[0];
	var fmonth=mySplitResult[1];
	var fyear=mySplitResult[2];
	
	var mySplitResult1 = tdate.split("-");  
	var tday=mySplitResult1[0];
	var tmonth=mySplitResult1[1];
	var tyear=mySplitResult1[2];

	var objDate = new Date(tmonth),
    locale = "en-us",
    months = objDate.toLocaleString(locale, { month: "long" });
	
	date = new Date();
	var month = date.getMonth()+1;
	var day = date.getDate();
	var year = date.getFullYear();
	$('#showerror').show();

	if(document.invgenretinvoice.retailer.value == 0 || retailer == "default")
	 {
		errMsg ="<font color='#ff3333'>Please select \'Retailer\'.</font>";
		document.getElementById("showerror").innerHTML=errMsg;
		document.invgenretinvoice.retailer.focus();
		return ;
	 }
	if(fdate == "")
		{
			errMsg ="<font color='#ff3333' class='vrb10'>Please select From Date</font>";
			document.getElementById("showerror").innerHTML=errMsg
			document.misoption.txtFromDate1.focus();
			return;
		}
	if(tdate == "")
		{
			errMsg ="<font color='#ff3333' class='vrb10'>Please select To Date</font>";
			document.getElementById("showerror").innerHTML=errMsg
			document.misoption.txtToDate1.focus();
			return;
		}
	if(tyear<fyear)
		{
			errMsg ="<font color='#ff3333' class='vrb10'>Selected  \"To\" Date Should Be Greater Than \"From\" Date.</font>";
			document.getElementById("showerror").innerHTML=errMsg
			document.misoption.txtToDate1.focus();
			return;
		}
	if(tyear==fyear)
		{
		
			if(tmonth<fmonth)
			{
				errMsg ="<font color='#ff3333' class='vrb10'>Selected  \"To\" Date Should Be Greater Than \"From\" Date.</font>";
				document.getElementById("showerror").innerHTML=errMsg
				document.misoption.txtToDate1.focus();
				return;
			}
		}
	if(tyear==fyear && tmonth==fmonth)
		{
			if(tday<fday)
			{
				errMsg ="<font color='#ff3333' class='vrb10'>Selected  \"To\" Date Should Be Greater Than \"From\" Date.</font>";
				document.getElementById("showerror").innerHTML=errMsg
				document.misoption.txtToDate1.focus();
				return;
			}
		}
	if(tyear>year || fyear>year)
		{
				errMsg ="<font color='#ff3333' class='vrb10'>Please check the selected date.</font>";
				document.getElementById("showerror").innerHTML=errMsg
				document.misoption.txtToDate1.focus();
				return;
		}
	if(tyear==year)
		{
			if(tmonth>month)
			{
				errMsg ="<font color='#ff3333' class='vrb10'>Selected  \"To\" Date Should Be Greater Than \"From\" Date.</font>";
				document.getElementById("showerror").innerHTML=errMsg
				document.misoption.txtToDate1.focus();
				return;
			}
		}
	if(tyear==fyear && tmonth==fmonth)
		{
			if(tday<fday)
			{
				errMsg ="<font color='#ff3333' class='vrb10'>Selected  \"To\" Date Should Be Greater Than \"From\" Date.</font>";
				document.getElementById("showerror").innerHTML=errMsg
				document.misoption.txtToDate1.focus();
				return;
			}
		}
	if(tyear>year || fyear>year)
		{
				errMsg ="<font color='#ff3333' class='vrb10'>Please check the selected date.</font>";
				document.getElementById("showerror").innerHTML=errMsg
				document.misoption.txtToDate1.focus();
				return;
		}
	if(tyear==year)
		{
			if(tmonth>month)
			{
				errMsg ="<font color='#ff3333' class='vrb10'>Please check the selected date.</font>";
				document.getElementById("showerror").innerHTML=errMsg
				document.misoption.txtToDate1.focus();
				return;
			}
		}
	if(fyear ==year)
		{
			if(fmonth>month)
			{
				errMsg ="<font color='#ff3333' class='vrb10'>Please check the selected date.</font>";
				document.getElementById("showerror").innerHTML=errMsg
				document.misoption.txtToDate1.focus();
				return;
			}
		}
	if(fyear==year)
		{
			if(fmonth==month)
			{
				if(fday>day)
				{
					errMsg ="<font color='#ff3333' class='vrb10'>Please check the selected date.</font>";
					document.getElementById("showerror").innerHTML=errMsg
					document.misoption.txtToDate1.focus();
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
					errMsg ="<font color='#ff3333' class='vrb10'>Please check the selected date.</font>";
					document.getElementById("showerror").innerHTML=errMsg
					document.misoption.txtToDate1.focus();
					return;
				}
			}
		}
		var xmlhttp= "";
		var resp= "";
		//var url ="/bookbattery/servlet/GenerateRetailerInvoice?hidWhatToDo=Generateinvoicereport&city="+city+"&retailer="+retailer+"&txtFromDate="+fdate+"&txtToDate="+tdate;
		var url ="/bookbattery/servlet/GenerateRetailerInvoice?hidWhatToDo=InverterGenerateinvoicereport&retailer="+retailer+"&txtFromDate="+fdate+"&txtToDate="+tdate+"&months="+months;
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
					$('#showerror').hide();

					resp = xmlhttp.responseText;
					document.getElementById("divcreatepdf").innerHTML = resp;
					document.getElementById("divcreatepdf").innerHTML = xmlhttp.responseText;
					document.invgenretinvoice.txtFromDate1.value="";
					document.invgenretinvoice.txtToDate1.value="";
				}
			}			
		}		
		xmlhttp.open("GET",url, true);		
		xmlhttp.send();	
}
function FunReset()
{
	location.href="../../../../jsp/admin/batterystore/mis/generateretailerinvoice.jsp"
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
<body onload="document.invgenretinvoice.state.focus(); noBack();" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="invgenretinvoice">
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
								<td class="subheading">Generate&nbsp;Retailer&nbsp;invoice&nbsp;[Inverter]</td>
								<td  align="right"><a href="../../../../servlet/AdminBatteryLogin?hidWhatToDo=batteryadminhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
							</tr>
							<tr><td height="5"></td></tr>
							<tr><td align="center" class="insidecontentbat"><div id="showerror"></div><div id="divcreatepdf"></div></td></tr>
							<td>
								<table width="90%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
								<tr>
									<td width="39%" class="insidecontent" align="right">Select&nbsp;Retailer<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">
									<select name='retailer' id='retailer' class="insidecontent" style='width:180px'>
										<option value='0'>&lt;-&nbsp;Select&nbsp;Retailer&nbsp;-&gt;</option>
										<option value="ALL" >All</option>
										<%
										try
										   {
											if(RetailerVector!=null && RetailerVector.size()>0)
											{
											for(int i=0;i<RetailerVector.size();i++)
											{
												Hashtable ht=(Hashtable)RetailerVector.get(i);
												String retailers=(String)ht.get("retailer_name");
										%>
										<option value="<%=retailers%>"><%=retailers%></option>
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
									<td width="39%" class="insidecontent" align="right">Select&nbsp;Date&nbsp;Range<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">&nbsp;From&nbsp;
										<input type="text" name="txtFromDate1" readonly  onChange="CheckDate(this)" onKeyDown="FormatDate(this,  window.event.keyCode,'down')" onKeyUp="FormatDate(this, window.event.keyCode,'up')" value="" size="10" maxlength="10"  >&nbsp;&nbsp;<img src="../../../../images/calender.jpg" valign="bottom" style="cursor:hand" onclick="displayDatePicker('txtFromDate1', this);">&nbsp;&nbsp;To&nbsp;<input type="text"  name="txtToDate1" readonly  onChange="CheckDate(this)" onKeyDown="FormatDate(this,  window.event.keyCode,'down')" onKeyUp="FormatDate(this, window.event.keyCode,'up')" value="" size="10" maxlength="10"  >&nbsp;&nbsp;&nbsp;<img src="../../../../images/calender.jpg" valign="middle" style="cursor:hand" onclick="displayDatePicker('txtToDate1', this);">
									</td>
								</tr>
								</td>
								</tr>
								</table>
								<tr>
								<td>
								<table width="40%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="right">
								<tr>
									<td width="30%" class="insidecontent" align="right"><input type="button" value="Generate" class="button41" onclick="javascript:funToGenerateinvoice(this);"></td>
									<td width="30%" class="insidecontent" align="left"><input type="button" value="Reset" class="button4" onclick="javascript:FunReset();"></td>
								</tr>
								</table>
							</td>
						</tr>
					<tr><td height="5"></td></tr>
					<tr><td align="center" class="insidecontentbat"><div style="width:770px;height:200px;  overflow:scroll; overflow-X:auto; overflow-Y:auto; -webkit-overflow-scrolling: touch;"><div id="divviewpdf"></div></div></td></tr>
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
String strLogmapMsg=(String)session.getAttribute("sesretlocErrorMsg");
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
document.getElementById("displayappinvgenretinvoiceerrormsg").innerHTML=loginmpamessg;
</script>
<%
	session.removeAttribute("sesretlocErrorMsg");
}
%>
</body>
</html>