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
<script language="JavaScript" src="../../../../js/jquery-1.8.2.js" ></script>
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
function funToAddAutomobiles()
{
	var battypeid = document.addvechicle.battery_type.value;
	var vehmake = document.addvechicle.vehmake.value; 
	var vehmodel = escape(document.addvechicle.vehmodel.value);
	var vehfuel = escape(document.addvechicle.vehfuel.value);
	var vehdesc = document.addvechicle.vehdesc.value;

	var iChars3 = "`~!@#$%^&*()+=-_[]\\\';,/{}|\":<>?.";
	var iChars2 = "`~!@#$%^*+=[]';,{}|\":<>?";
	var regularexp=/^[^\s\-].*[^\s\-]$/;

	if(document.addvechicle.battery_type.value == 0)
	 {
		errMsg ="<font color='#ff3333'>Please select \'Battery Type\'.</font>";
		document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
		document.addvechicle.battery_type.focus();
		return ;
     }
	/* if(battypeid == "8,Gensets")
	{
		  if(vehmake == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Gensets Make\'.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehmake.focus();
			return ;
         }
		if (document.addvechicle.vehmake.value.length < 3)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 3 characters in the \'Gensets Make\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehmake.focus();
			return ;
         }
		if (document.addvechicle.vehmake.value.length > 49)
		 {
			errMsg ="<font color='#ff3333'>Please enter only 49 characters in the \'Gensets Make\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehmake.focus();
			return ;
         }
		 if (document.addvechicle.vehmake.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the in the \'Gensets Make\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehmake.focus();
			return ;
         }
		for (var i = 0; i < document.addvechicle.vehmake.value.length; i++)
		{
         if (iChars3.indexOf(document.addvechicle.vehmake.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#ff3333'>Special characters are not allowed in Gensets Make field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehmake.focus();
			return ;
         }
		}
		if(isDigits(vehmake)==true)
		{
			errMsg ="<font color='#ff3333'>Only Numerics are not allowed in the \'Gensets Make\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehmake.focus();
			return ;
         }
		 if (! document.addvechicle.vehmake.value.match(regularexp))
		{
			errMsg ="<font color='#ff3333'>\'Gensets Make\' has Unnecessary spaces. Please remove spaces.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehmake.focus();
			return ;
		}
	 }
	 else if(battypeid == "18,Generators")
	{
		 	  if(vehmake == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Generators Make\'.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehmake.focus();
			return ;
         }
		if (document.addvechicle.vehmake.value.length < 3)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 3 characters in the \'Generators Make\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehmake.focus();
			return ;
         }
		if (document.addvechicle.vehmake.value.length > 49)
		 {
			errMsg ="<font color='#ff3333'>Please enter only 49 characters in the in the \'Generators Make\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehmake.focus();
			return ;
         }
		 if (document.addvechicle.vehmake.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the \'Generators Make\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehmake.focus();
			return ;
         }
		for (var i = 0; i < document.addvechicle.vehmake.value.length; i++)
		{
         if (iChars3.indexOf(document.addvechicle.vehmake.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#ff3333'>Special characters are not allowed in Generators Make field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehmake.focus();
			return ;
         }
		}
		if(isDigits(vehmake)==true)
		{
			errMsg ="<font color='#ff3333'>Only Numerics are not allowed in the \'Generators Make\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehmake.focus();
			return ;
         }
		  if (! document.addvechicle.vehmake.value.match(regularexp))
		{
			errMsg ="<font color='#ff3333'>\'Generators Make\' has Unnecessary spaces. Please remove spaces.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehmake.focus();
			return ;
		}
	}
	 else
	{*/
	     if(vehmake == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Vehicle Make\'.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehmake.focus();
			return ;
         }
		if (document.addvechicle.vehmake.value.length < 3)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 3 characters in the \'Vehicle Make\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehmake.focus();
			return ;
         }
		if (/[a-z][A-Z]{2}/i.test(document.addvechicle.vehmake.value) != true) 
		{
			errMsg ="<font color='#ff3333'>Please enter at least three charaters together in the \'Vehicle Make\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehmake.focus();
			return ;
         }
		if (document.addvechicle.vehmake.value.length > 49)
		 {
			errMsg ="<font color='#ff3333'>Please enter only 49 characters in the in the \'Vehicle Make\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehmake.focus();
			return ;
         }
		 if (document.addvechicle.vehmake.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the \'Vehicle Make\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehmake.focus();
			return ;
         }
		for (var i = 0; i < document.addvechicle.vehmake.value.length; i++)
		{
         if (iChars3.indexOf(document.addvechicle.vehmake.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#ff3333'>Special characters are not allowed in Vehicle Make field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehmake.focus();
			return ;
         }
		}
		if(isDigits(vehmake)==true)
		{
			errMsg ="<font color='#ff3333'>Only Numerics are not allowed in the \'Vehicle Make\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehmake.focus();
			return ;
         }
		 if (! document.addvechicle.vehmake.value.match(regularexp))
		{
			errMsg ="<font color='#ff3333'>\'Vehicle Make\' has Unnecessary spaces. Please remove spaces.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehmake.focus();
			return ;
		}
	    
		 if(vehmodel == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Model\'.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehmodel.focus();
			return ;
         }
		if (document.addvechicle.vehmodel.value.length < 3)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 3 characters in the \'Model\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehmodel.focus();
			return ;
         }
		if (document.addvechicle.vehmodel.value.length > 49)
		 {
			errMsg ="<font color='#ff3333'>Please enter only 49 characters in the \'Model\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehmodel.focus();
			return ;
         }
		if (document.addvechicle.vehmodel.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the \'Model\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehmodel.focus();
			return ;
         }
		for (var i = 0; i < document.addvechicle.vehmodel.value.length; i++)
		{
         if (iChars2.indexOf(document.addvechicle.vehmodel.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#ff3333'>Special characters are not allowed in Vehicle Model field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehmodel.focus();
			return ;
         }
		}
		
		 if(vehfuel == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Fuel Type\'.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehfuel.focus();
			return ;
        }
		if (document.addvechicle.vehfuel.value.length < 3)
        {
			errMsg ="<font color='#ff3333'>Please enter at least 3 characters in the \'Fuel Type\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehfuel.focus();
			return ;
        }
		if (document.addvechicle.vehfuel.value.length > 49)
		 {
			errMsg ="<font color='#ff3333'>Please enter only 49 characters in the \'Fuel Type\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehfuel.focus();
			return ;
        }
		if (document.addvechicle.vehfuel.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the \'Fuel Type\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehfuel.focus();
			return ;
        }
		for (var i = 0; i < document.addvechicle.vehfuel.value.length; i++)
		{
        if (iChars2.indexOf(document.addvechicle.vehfuel.value.charAt(i))!= -1)
        {
			errMsg ="<font color='#ff3333'>Special characters are not allowed in Vehicle Fuel Type field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehfuel.focus();
			return ;
        }
		}
		/*if(isDigits(vehmodel)==true)
		{
			errMsg ="<font color='#ff3333'>Only Numerics are not allowed in the \'Model\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehmodel.focus();
			return ;
         }*/
		  if (! document.addvechicle.vehmodel.value.match(regularexp))
		{
			errMsg ="<font color='#ff3333'>\'Model\' has Unnecessary spaces. Please remove spaces.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehmodel.focus();
			return ;
		}
		if(vehdesc == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Description\'.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehdesc.focus();
			return ;
		 }
		 if (document.addvechicle.vehdesc.value.length < 3)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 3 characters in the \'Description\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehdesc.focus();
			return ;
         }
		 if (document.addvechicle.vehdesc.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the \'Description\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehdesc.focus();
			return ;
         }
		if (! document.addvechicle.vehdesc.value.match(regularexp))
		{
			errMsg ="<font color='#ff3333'>\'Description\' has Unnecessary spaces. Please remove spaces.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehdesc.focus();
			return ;
		}
		for (var i = 0; i < document.addvechicle.vehdesc.value.length; i++)
		{
			 if (iChars2.indexOf(document.addvechicle.vehdesc.value.charAt(i))!= -1)
			 {
				errMsg ="<font color='#ff3333'>Special characters are not allowed in Description field.</font>";
				document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
				document.addvechicle.vehdesc.focus();
				return ;
			 }
		}
		if(isDigits(vehdesc)==true)
		{
			errMsg ="<font color='#ff3333'>Only Numerics are not allowed in the \'Description\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addvechicle.vehdesc.focus();
			return ;
         }
		
	document.addvechicle.action = "../../../../servlet/AddVehicleDetails?hidWhatToDo=addvehicledetails&battypeid="+battypeid+"&vehmak="+vehmake+"&vehmodel="+vehmodel+"&vehfuel="+vehfuel+"&vecdesc="+vehdesc;
//	alert(document.addvechicle.action);
	document.addvechicle.method="post";
	document.addvechicle.submit();
}
function funOnClickvehModel()	
{ 
	var battery_type = document.addvechicle.battery_type.value;
	var vehmake = document.addvechicle.vehmake.value;
	var vehmodel = document.addvechicle.vehmodel.value;
	var url="../../../../servlet/AddVehicleDetails?hidWhatToDo=checkvehmodel&battery_type="+battery_type+"&vehmake="+vehmake+"&vehmodel="+vehmodel;
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
					document.getElementById("chkvehmodel").innerHTML=xmlhttp.responseText;
					document.getElementById("chkvehmodel").innerHTML=resp;
					document.addvechicle.vehmodel.value="";
					document.addvechicle.vehmodel.focus();
				}
				else
				{
					document.getElementById("chkvehmodel").innerHTML=xmlhttp.responseText;
					document.getElementById("chkvehmodel").innerHTML=resp;
				}
		  }
	  }
	xmlhttp.open("GET",url,true);
	xmlhttp.send();
	
}
function FunReset()
{
	location.href="../../../../jsp/admin/batterystore/automobiles/addvehicles.jsp"
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
<body onload="document.addvechicle.battery_type.focus(); " >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="addvechicle" action="request_for_quote.asp"  method="post">
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
								<td class="subheading">Add&nbsp;Automobiles</td>
								<td  align="right"><a href="../../../../servlet/AdminBatteryLogin?hidWhatToDo=batteryadminhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
							</tr>
							<tr><td height="10"></td></tr>
							<tr><td align="center" class="insidecontentbat"><div id="displayaddvehicleerrormsg"></div></td></tr>
							<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
								<tr>
									<td width="39%" class="insidecontent" align="right">Select&nbsp;Battery&nbsp;Type<font color="red">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left"><select name='battery_type' id='battery_type' class="insidecontent"  style='width:180px'><option value='0'>Select&nbsp;Battery&nbsp;Type</option>
										<option value='1,Bike Batteries'>Bike Batteries</option>
										<option value="2,Car Batteries" >Car Batteries</option>
										<option value="3,Three Wheeler Batteries" >Three Wheeler Batteries</option>
										<!--<option value="4,Inverters" >Inverters</option>-->
										<option value="5,Truck Batteries" >Truck Batteries</option>
										<option value="6,Bus Batteries" >Bus Batteries</option>
										<option value="7,Special Vehicle Batteries" >Special Vehicle Batteries</option>
										<option value="8,Genset Batteries" >Genset Batteries</option>
										<option value="9,Crane Batteries" >Crane Batteries</option>
										<option value="10,Roller Batteries" >Roller Batteries</option>
										<option value="11,Loader Batteries" >Loader Batteries</option>
										<option value="12,Tractor Batteries" >Tractor Batteries</option>
										<option value="13,Dozer Batteries" >Dozer Batteries</option>
										<option value="14,Excavator Batteries" >Excavator Batteries</option>
										<option value="15,Tyre Handler Batteries" >Tyre Handler Batteries</option>
										<option value="16,Hydraulic Shovel Batteries" >Hydraulic Shovel Batteries</option>
										<option value="17,Harvestor Batteries" >Harvestor Batteries</option>
										<option value="18,Generator Batteries" >Generator Batteries</option>
										<option value="19,Compactor Batteries" >Compactor Batteries</option>
										<option value="20,Telescopic Handler Batteries" >Telescopic Handler Batteries</option>
										<option value="21,Forwarder Batteries" >Forwarder Batteries</option>
										<option value="22,Wheeled Harvester Batteries" >Wheeled Harvester Batteries</option>
										<option value="23,Minibus Batteries" >Minibus Batteries</option>
										<option value="24,Dumper Batteries" >Dumper Batteries</option>
										<option  value="25,Construction Equipment Batteries" >Construction Equipment Batteries</option>
										<option  value="26,Hydralic Excavator Batteries" >Hydralic Excavator Batteries</option>
									</select></td>
								</tr>
								<tr>
									<td  width="39%" class="insidecontent" align="right">Vehicle&nbsp;Make<font color="red">*</font></td><td width="2%" align="center">:</td>
									<!--<td id="gensets" width="39%" class="insidecontent" align="right">Gensets&nbsp;Make<font color="red">*</font></td>
									<td id="generators" width="39%" class="insidecontent" align="right">Generators&nbsp;Make<font color="red">*</font></td>
									<td width="2%" align="center">:</td>-->
									<td width="59%" class="insidecontent" align="left"><input type="text" name="vehmake" size="20" maxlength="50"><span class="hint">Provide Vehicle Make in the format 'Suzuki'.<span class="hint-pointer">&nbsp;</span></span></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Model<font color="red">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="vehmodel" size="20"  maxlength="50" onchange="funOnClickvehModel();"><span class="hint">Provide Vehicle Model in the format 'GS 150 R'.<span class="hint-pointer">&nbsp;</span></span><div id="chkvehmodel"></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">FuelType<font color="red">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="vehfuel" size="20"  maxlength="50" onchange="funOnClickvehModel();"><span class="hint">Provide Vehicle Fuel Type in the format 'Petrol or Diesel'.<span class="hint-pointer">&nbsp;</span></span><div id="chkvehfuel"></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right" valign="top">Description<font color="red">*</font></td><td width="2%" align="center" valign="top">:</td>
									<td width="59%" class="insidecontent" align="left"><textarea name="vehdesc" cols="20" rows="4"></textarea></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right"><input type="button" value="Submit" class="button4" onclick="javascript:funToAddAutomobiles();"></td><td width="1%"></td>
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
String strLogvMsg=(String)session.getAttribute("sesvehErrorMsg");
if(strLogvMsg ==null)
{
	strLogvMsg="";
}
else
{
%>
<script type="text/javascript">
var loginvmessg; 
loginvmessg= "<%=strLogvMsg%>";
document.getElementById("displayaddvehicleerrormsg").innerHTML=loginvmessg;
</script>
<%
	session.removeAttribute("sesvehErrorMsg");
}
%>
</body>
</html>