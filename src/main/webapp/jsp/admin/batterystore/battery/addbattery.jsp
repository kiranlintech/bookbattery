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
String allowedImgFormats =  "gif,jpg,jpeg,png,bmp,jpe,wbmp";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../../images/favicon.png" type="image/x-icon">
<title>BookBattery.com - India's No.1 Automobile Battery Store</title>
<script language="javascript">
function isDigits(argvalue) {
    argvalue = argvalue.toString();
	if(argvalue=="")
	{
		return false;
	}
    var validChars = "0123456789";
    var startFrom = 0;
    if (argvalue.substring(0, 2) == "0x") {
       validChars = "0123456789abcdefABCDEF";
       startFrom = 2;
    } else if (argvalue.charAt(0) == "0") {
       validChars = "01234567";
       startFrom = 1;
    }
    for (var n = 0; n < argvalue.length; n++) {
        if (validChars.indexOf(argvalue.substring(n, n+1)) == -1) return false;
    }
  return true;
}
function funToAddBattery()
{
		var batname = document.addbattery.batname.value;
		var batbrand = document.addbattery.batbrand.value;
		var batmodel = document.addbattery.batmodel.value;
		var batwarrnty = escape(document.addbattery.batwarrnty.value);
		var batcapcity = document.addbattery.batcapcity.value;
		//var batacprice = parseInt(document.addbattery.batacprice.value);
		//var batdisprice = parseInt(document.addbattery.batdisprice.value); 
		var bardesc = document.addbattery.bardesc.value;
		var fileName = document.addbattery.batattachment.value;
		varAllowedFormats = '<%=allowedImgFormats%>';
		var iChars3 = "`~!@#$%^&*()+=-_[]\\\';/{}|\":<>?";
		var iChars1 = "`~!@#$%^&*()=_[]\\\';,/{}|\":<>?";
		var iChars2 = "`~!@#$%^&*()+=[]';,{}|\":<>?.";
		var regularexp=/^[^\s\-].*[^\s\-]$/;
		var regularexp1=/^([a-zA-Z0-9]+)$/;
		var iCharsdesc = "`~!@#$%^*()=_[]\\\';/{}|\":<>";	
		var regularexpwaranty=/^(\d.+[a-zA-Z ]|[a-zA-Z ]+\d.)[a-zA-Z\d.]*$/
		batwarrnty=batwarrnty.replace(/\+/g, '%2B');

		if(batname == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Battery Name\'.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batname.focus();
			return ;
         }
		if (document.addbattery.batname.value.length < 2)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 2 characters in the \'Battery Name\' field.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batname.focus();
			return ;
         }
		if (document.addbattery.batname.value.length > 49)
		 {
			errMsg ="<font color='#ff3333'>Please enter only 49 characters in the in the \'Battery Name\' field.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batname.focus();
			return ;
         }
		 if (document.addbattery.batname.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the \'Battery Name\' field.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batname.focus();
			return ;
         }
		for (var i = 0; i < document.addbattery.batname.value.length; i++)
		{
         if (iChars3.indexOf(document.addbattery.batname.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#ff3333'>Special characters are not allowed in \'Battery Name\' field.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batname.focus();
			return ;
         }
		}
		if(isDigits(batname)==true)
		{
			errMsg ="<font color='#ff3333'>Only Numerics are not allowed in the \'Battery Name\' field.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batname.focus();
			return ;
         }
		if (! document.addbattery.batname.value.match(regularexp))
		{
			errMsg ="<font color='#ff3333'>\'Battery Name\' has Unnecessary spaces. Please remove spaces.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batname.focus();
			return ;
		}
		if(batbrand == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Battery Brand\'.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batbrand.focus();
			return ;
         }
	    if (document.addbattery.batbrand.value.length < 3)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 3 characters in the \'Battery Brand\' field.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batbrand.focus();
			return ;
         }
		if (document.addbattery.batbrand.value.length > 49)
		 {
			errMsg ="<font color='#ff3333'>Please enter only 49 characters in the in the \'Battery Brand\' field.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batbrand.focus();
			return ;
         }
		 if (document.addbattery.batbrand.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the \'Battery Brand\' field.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batbrand.focus();
			return ;
         }
		 for (var i = 0; i < document.addbattery.batbrand.value.length; i++)
		{
         if (iChars3.indexOf(document.addbattery.batbrand.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#ff3333'>Special characters are not allowed in \'Battery Brand\' field.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batbrand.focus();
			return ;
         }
		}
		if(isDigits(batbrand)==true)
		{
			errMsg ="<font color='#ff3333'>Only Numerics are not allowed in the \'Battery Brand\' field.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batbrand.focus();
			return ;
         }
		 if (! document.addbattery.batbrand.value.match(regularexp))
		{
			errMsg ="<font color='#ff3333'>\'Battery Brand\' has Unnecessary spaces. Please remove spaces.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batbrand.focus();
			return ;
		}
		if(batmodel == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Battery Model\'.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batmodel.focus();
			return ;
         }
	    if (document.addbattery.batmodel.value.length < 3)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 3 characters in the \'Battery Model\' field.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batmodel.focus();
			return ;
         }
		if (document.addbattery.batmodel.value.length > 49)
		 {
			errMsg ="<font color='#ff3333'>Please enter only 49 characters in the in the \'Battery Model\' field.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batmodel.focus();
			return ;
         }
		 if (document.addbattery.batmodel.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the in the \'Battery Model\' field.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batmodel.focus();
			return ;
         }
		for (var i = 0; i < document.addbattery.batmodel.value.length; i++)
		{
         if (iChars2.indexOf(document.addbattery.batmodel.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#ff3333'>Special characters are not allowed in \'Battery Model\' field.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batmodel.focus();
			return ;
         }
		}
		if (! document.addbattery.batmodel.value.match(regularexp))
		{
			errMsg ="<font color='#ff3333'>\'Battery Model\' has Unnecessary spaces. Please remove spaces.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batmodel.focus();
			return ;
		}
		if(batwarrnty == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Warranty\'.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batwarrnty.focus();
			return ;
         }
		 if(batwarrnty == 0)
		{
			errMsg ="<font color='#ff3333'>Please enter valid \'Warranty\'.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batwarrnty.focus();
			return ;
         }
		 if (document.addbattery.batwarrnty.value.length < 3)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 3 characters in the \'Warranty\' field.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batwarrnty.focus();
			return ;
         }
		 if (document.addbattery.batwarrnty.value.length > 49)
         {
			errMsg ="<font color='#ff3333'>Please enter 49 characters in the \'Warranty\' field.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batwarrnty.focus();
			return ;
         }
		  if (regularexpwaranty.test(batwarrnty)==false)
		{
			errMsg ="<font color='#ff3333'>Please enter only Alphanumerics in the \'Warranty\' field.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batwarrnty.focus();
			return ;
		}
		 if (document.addbattery.batwarrnty.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the in the \'Warranty\' field.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batwarrnty.focus();
			return ;
         }
		/* if (document.addbattery.batwarrnty.value.match(regularexp1))
		{
			errMsg ="<font color='#ff3333'>Please enter only Alphanumerics in the \'Warranty\' field.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batwarrnty.focus();
			return ;
		}*/
		 for (var i = 0; i < document.addbattery.batwarrnty.value.length; i++)
		{
         if (iChars1.indexOf(document.addbattery.batwarrnty.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#ff3333'>Special characters are not allowed in \'Warranty\' field.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batwarrnty.focus();
			return ;
         }
		}
		/*if (! document.addbattery.batwarrnty.value.match(regularexp))
		{
			errMsg ="<font color='#ff3333'>\'Warranty\' has Unnecessary spaces. Please remove spaces.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batwarrnty.focus();
			return ;
		}*/
		 if(batcapcity == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Capacity\'.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batcapcity.focus();
			return ;
         }
		  if(batcapcity == 0 || batcapcity == "0 Ah" || batcapcity == "0 ah" || batcapcity == "0 AH" || batcapcity == "0 aH")
		  {
			errMsg ="<font color='#ff3333'>Please enter valid \'Capacity\'.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batcapcity.focus();
			return ;
         }
		 if (document.addbattery.batcapcity.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the \'Capacity\' field.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batcapcity.focus();
			return ;
         }
		for (var i = 0; i < document.addbattery.batcapcity.value.length; i++)
		{
         if (iChars3.indexOf(document.addbattery.batcapcity.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#ff3333'>Special characters are not allowed in \'Capacity\' field.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batcapcity.focus();
			return ;
         }
		}
		 if (document.addbattery.batcapcity.value.match(regularexp1))
		{
			errMsg ="<font color='#ff3333'>Please enter only Alphanumerics in the \'Capacity\' field.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batcapcity.focus();
			return ;
		}
		if (! document.addbattery.batcapcity.value.match(regularexp))
		{
			errMsg ="<font color='#ff3333'>\'Capacity\' has Unnecessary spaces. Please remove spaces.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batcapcity.focus();
			return ;
		}
		/*if(document.addbattery.batacprice.value == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Actual Price\'.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batacprice.focus();
			return ;
         }
		else if (document.addbattery.batacprice.value.length < 2)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 2 numbers in the \'Actual Price\' field.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batacprice.focus();
			return ;
         }
		 else 
		{
		var checkOK = "0123456789";
		var checkStr = batacprice;
		var allValid = true;
		var decPoints = 0;
		var allNum = "";
		for (i = 0;  i < checkStr.length;  i++)
		{
			ch = checkStr.charAt(i);
			for (j = 0;  j < checkOK.length;  j++)
			if (ch == checkOK.charAt(j))
			break;
			if (j == checkOK.length)
			{
				allValid = false;
				break;
			}

		}
		if (!allValid)
		{
			errMsg ="<font color='#ff3333'>Please enter only digits in the \'Actual Price\' field.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batacprice.focus();
			return ;
		}
		}
		if(document.addbattery.batdisprice.value == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Discount Price\'.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batdisprice.focus();
			return ;
         }
		 else if (document.addbattery.batdisprice.value.length < 2)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 2 numbers in the \'Discount Price\' field.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batdisprice.focus();
			return ;
         }
		 else 
		{
		var checkOK = "0123456789";
		var checkStr = batdisprice;
		var allValid = true;
		var decPoints = 0;
		var allNum = "";
		for (i = 0;  i < checkStr.length;  i++)
		{
			ch = checkStr.charAt(i);
			for (j = 0;  j < checkOK.length;  j++)
			if (ch == checkOK.charAt(j))
			break;
			if (j == checkOK.length)
			{
				allValid = false;
				break;
			}

		}
		if (!allValid)
		{
			errMsg ="<font color='#ff3333'>Please enter only digits in the \'Discount Price\' field.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batdisprice.focus();
			return ;
		}
		}
		if(batacprice < batdisprice)
		{
			errMsg ="<font color='#ff3333'>\'Discount Price\' should be lessthan \'Actual Price\'.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batdisprice.focus();
			return ;
		}*/
		if(bardesc == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Description\'.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.bardesc.focus();
			return ;
         }
		  if (document.addbattery.bardesc.value.length < 3)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 3 characters in the \'Description\' field.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.bardesc.focus();
			return ;
         }
		 if (document.addbattery.bardesc.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the \'Description\' field.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.bardesc.focus();
			return ;
         }
		 if (! document.addbattery.bardesc.value.match(regularexp))
		{
			errMsg ="<font color='#ff3333'>\'Description\' has Unnecessary spaces. Please remove spaces.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.bardesc.focus();
			return ;
		}
		for (var i = 0; i < document.addbattery.bardesc.value.length; i++)
		{
			 if (iCharsdesc.indexOf(document.addbattery.bardesc.value.charAt(i))!= -1)
			 {
				errMsg ="<font color='#ff3333'>Special characters are not allowed in \'Description\' field.</font>";
				document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
				document.addbattery.bardesc.focus();
				return;
			 }
		}
		if(isDigits(bardesc)==true)
		{
			errMsg ="<font color='#ff3333'>Only Numerics are not allowed in the \'Description\' field.</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.bardesc.focus();
			return ;
         }

		 if(fileName.length >0)
		{
			var varIndex = fileName.lastIndexOf(".");
				//alert(varIndex);
			var varBolMatch = false;
			if(varIndex > 0)
			{
				var varFileExt  = fileName.substring(++varIndex);
				var varStringArr = varAllowedFormats.split(",");
					

				for(var i=0;i<varStringArr.length;i++){
					if(varStringArr[i].toLowerCase() == varFileExt.toLowerCase()){
						varBolMatch = true;
					}
				}
				if(!varBolMatch){ 
					errMsg ="<font color='#ff3333'>You can attach "+varAllowedFormats+" only.</font>";
					document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
					document.addbattery.batattachment.focus();
					return ;
				}
			}
		}
		if(fileName == "")
		  {
			errMsg ="<font color='#ff3333'>Please select a image file..</font>";
			document.getElementById("displayaddbatteryerrormsg").innerHTML=errMsg;
			document.addbattery.batattachment.focus();
			return ;
		  }
		  fileName=escape(fileName);
	document.addbattery.action = "../../../../servlet/AddBatteryDetails?hidWhatToDo=addbatterydetails&batname="+batname+"&batbrand="+batbrand+"&batmodel="+batmodel+"&batwar="+batwarrnty+"&batcap="+batcapcity+"&fileName="+fileName+"&batdesc="+bardesc;
	//alert(document.addbattery.action);
	document.addbattery.method="post";
	document.addbattery.submit();
}
function funOnClickModel()	
{ 
	var batname = document.addbattery.batname.value;
	var batbrand = document.addbattery.batbrand.value;
	var batmodel = document.addbattery.batmodel.value;
	var url="../../../../servlet/AddBatteryDetails?hidWhatToDo=checkmodel&batname="+batname+"&batbrand="+batbrand+"&batmodel="+batmodel;
	if (window.XMLHttpRequest)
	  {// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	  }
	else
	  {// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	  }
	  xmlhttp.onreadystatechange=function()
	  {
		  if (xmlhttp.readyState==4 && xmlhttp.status==200)
		  {
			  resp =xmlhttp.responseText;
			  if(resp.indexOf("Already")>=0)
				{
					document.getElementById("chkmodel").innerHTML=xmlhttp.responseText;
					document.getElementById("chkmodel").innerHTML=resp;
					document.addbattery.batmodel.value="";
					document.addbattery.batmodel.focus();
				}
				else
				{
					document.getElementById("chkmodel").innerHTML=xmlhttp.responseText;
					document.getElementById("chkmodel").innerHTML=resp;
				}
		  }
	  }
	xmlhttp.open("GET",url,true);
	xmlhttp.send();
	
}
function FunReset()
{
	location.href="../../../../jsp/admin/batterystore/battery/addbattery.jsp"
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
<body onload="document.addbattery.batname.focus();" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="addbattery" method="post" ENCTYPE="multipart/form-data">
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
								<td class="subheading">Add&nbsp;Battery</td>
								<td  align="right"><a href="../../../../servlet/AdminBatteryLogin?hidWhatToDo=batteryadminhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
							</tr>
							<tr><td height="10"></td></tr>
							<tr><td align="center" class="insidecontentbat"><div id="displayaddbatteryerrormsg"></div></td></tr>
							<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
								<tr>
									<td width="39%" class="insidecontent" align="right">Battery&nbsp;Name<font color="red">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="batname" size="20" maxlength="50"><span class="hint">Provide Battery Name in the format 'Pro'.<span class="hint-pointer">&nbsp;</span></span></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Battery&nbsp;Brand<font color="red">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="batbrand" size="20" maxlength="50"><span class="hint">Provide Battery Brand in the format 'Amaron'.<span class="hint-pointer">&nbsp;</span></span></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Battery&nbsp;Model<font color="red">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="batmodel" size="20"  maxlength="50" onchange="funOnClickModel();"><span class="hint">Provide Battery Model in the format 'AAM-FL-555059054'.<span class="hint-pointer">&nbsp;</span></span><div id="chkmodel"></div></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Warranty<font color="red">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="batwarrnty" size="20" maxlength="100"><span class="hint">Provide Warranty in the format '24 months + free service'.<span class="hint-pointer">&nbsp;</span></span></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Capacity<font color="red">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="batcapcity" size="20" maxlength="10"><span class="hint">Provide Capacity in the format '80 Ah'.<span class="hint-pointer">&nbsp;</span></span></td>
								</tr>
								<!--<tr>
									<td width="39%" class="insidecontent" align="right">Actual&nbsp;Price<font color="red">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="batacprice" size="20" maxlength="20"><span class="hint">Provide Price in the format '1000'.<span class="hint-pointer">&nbsp;</span></span></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Discount&nbsp;Price<font color="red">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="batdisprice" size="20" maxlength="20"><span class="hint">Provide Price in the format '1000'.<span class="hint-pointer">&nbsp;</span></span></td>
								</tr>-->
								<tr>
									<td width="39%" class="insidecontent" align="right" valign="top">Description<font color="red">*</font></td><td width="2%" align="center" valign="top">:</td>
									<td width="59%" class="insidecontent" align="left"><textarea name="bardesc" cols="20" rows="4"></textarea></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right" valign="top">Select&nbsp;an&nbsp;image&nbsp;file<font color="red">*</font></td><td width="2%" align="center" valign="top">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="file" name="batattachment" size="20"/><br><br>
									<font class="insidecontent" width="70%">&nbsp;* <b>Image&nbsp;formats</b>&nbsp;:&nbsp; gif,bmp,jpg,jpeg,jpe,png,wbmp<br>&nbsp;&nbsp;* Please check your image file name should not consist 
									of spaces.<br>* Image Resolution is 100*100.</td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right"><input type="button" value="Submit" class="button4" onclick="javascript:funToAddBattery();"></td><td width="1%"></td>
									<td width="59%" class="insidecontent" align="left"><input type="button" value="Reset" class="button4" onclick="javascript:FunReset();"></td>
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
String strLogMsg=(String)session.getAttribute("sesbatErrorMsg");
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
document.getElementById("displayaddbatteryerrormsg").innerHTML=loginmessg;
</script>
<%
	session.removeAttribute("sesbatErrorMsg");
}
%>
</body>
</html>