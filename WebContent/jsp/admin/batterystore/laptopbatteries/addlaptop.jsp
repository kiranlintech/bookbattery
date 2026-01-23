<%--
    Document   : add laptop details
    Created on : Feb 6, 2014, 4:22:12 PM
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
function addLaptopDetails()
{
	
	var batterytype = document.addlaptop.batterytype.value;
	var laptopbrand = document.addlaptop.laptopbrand.value; 
	var laptopmodel = escape(document.addlaptop.laptopmodel.value);
	var laptopproduct = escape(document.addlaptop.laptopproduct.value);
	var description = document.addlaptop.description.value;

	var iChars3 = "`~!@#$%^&*()+=-_[]\\\';,/{}|\":<>?.";
	var iChars2 = "`~!@#$%^*+=[]';,{}|\":<>?";
	var regularexp=/^[^\s\-].*[^\s\-]$/;
	var iChars1 = "`~!@#$%^&*()=_[]\\\';,/{}|\":<>?";
		var iCharsdesc = "`~!@#$%^*()=_[]\\\';/{}|\":<>";
	if(document.addlaptop.battery_type.value == 0)
	 {
		errMsg ="<font color='#ff3333'>Please select \'Battery Type\'.</font>";
		document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
		document.addlaptop.battery_type.focus();
		return ;
     }
	
	     if(laptopbrand == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Laptop Brand\'.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addlaptop.laptopbrand.focus();
			return ;
         }
		if (document.addlaptop.laptopbrand.value.length < 2)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 2 characters in the \'Laptop Brand\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addlaptop.laptopbrand.focus();
			return ;
         }
		if (/[a-z][A-Z]{1}/i.test(document.addlaptop.laptopbrand.value) != true) 
		{
			errMsg ="<font color='#ff3333'>Please enter at least three charaters together in the \'Laptop Brand\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addlaptop.laptopbrand.focus();
			return ;
         }
		if (document.addlaptop.laptopbrand.value.length > 49)
		 {
			errMsg ="<font color='#ff3333'>Please enter only 49 characters in the in the \'Laptop Brand\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addlaptop.laptopbrand.focus();
			return ;
         }
		 if (document.addlaptop.laptopbrand.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the \'Laptop Brand\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addlaptop.laptopbrand.focus();
			return ;
         }
		for (var i = 0; i < document.addlaptop.laptopbrand.value.length; i++)
		{
         if (iChars3.indexOf(document.addlaptop.laptopbrand.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#ff3333'>Special characters are not allowed in Laptop Brand field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addlaptop.laptopbrand.focus();
			return ;
         }
		}
		if(isDigits(laptopbrand)==true)
		{
			errMsg ="<font color='#ff3333'>Only Numerics are not allowed in the \'Laptop Brand\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addlaptop.laptopbrand.focus();
			return ;
         }
		 if (! document.addlaptop.laptopbrand.value.match(regularexp))
		{
			errMsg ="<font color='#ff3333'>\'Laptop Brand\' has Unnecessary spaces. Please remove spaces.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addlaptop.laptopbrand.focus();
			return ;
		}
	    
		 if(laptopmodel == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Model\'.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addlaptop.laptopmodel.focus();
			return ;
         }
		if (document.addlaptop.laptopmodel.value.length < 3)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 3 characters in the \'Model\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addlaptop.laptopmodel.focus();
			return ;
         }
		if (document.addlaptop.laptopmodel.value.length > 49)
		 {
			errMsg ="<font color='#ff3333'>Please enter only 49 characters in the \'Model\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addlaptop.laptopmodel.focus();
			return ;
         }
		if (document.addlaptop.laptopmodel.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the \'Model\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addlaptop.laptopmodel.focus();
			return ;
         }
		for (var i = 0; i < document.addlaptop.laptopmodel.value.length; i++)
		{
         if (iChars2.indexOf(document.addlaptop.laptopmodel.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#ff3333'>Special characters are not allowed in Laptop Model field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addlaptop.laptopmodel.focus();
			return ;
         }
		}
		/*if(isDigits(vehmodel)==true)
		{
			errMsg ="<font color='#ff3333'>Only Numerics are not allowed in the \'Model\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addlaptop.vehmodel.focus();
			return ;
         }*/
		  if (! document.addlaptop.laptopmodel.value.match(regularexp))
		{
			errMsg ="<font color='#ff3333'>\'Model\' has Unnecessary spaces. Please remove spaces.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addlaptop.laptopmodel.focus();
			return ;
		}

		if(laptopproduct == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Product\'.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addlaptop.laptopproduct.focus();
			return ;
         }
		if (document.addlaptop.laptopproduct.value.length < 3)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 3 characters in the \'Product\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addlaptop.laptopproduct.focus();
			return ;
         }
		if (document.addlaptop.laptopproduct.value.length > 49)
		 {
			errMsg ="<font color='#ff3333'>Please enter only 49 characters in the \'Product\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addlaptop.laptopproduct.focus();
			return ;
         }
		if (document.addlaptop.laptopproduct.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the \'Product\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addlaptop.laptopproduct.focus();
			return ;
         }
		for (var i = 0; i < document.addlaptop.laptopproduct.value.length; i++)
		{
         if (iChars2.indexOf(document.addlaptop.laptopproduct.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#ff3333'>Special characters are not allowed in Laptop Product field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addlaptop.laptopproduct.focus();
			return ;
         }
		}
		/*if(isDigits(vehmodel)==true)
		{
			errMsg ="<font color='#ff3333'>Only Numerics are not allowed in the \'Model\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addlaptop.vehmodel.focus();
			return ;
         }*/
		  if (! document.addlaptop.laptopproduct.value.match(regularexp))
		{
			errMsg ="<font color='#ff3333'>\'Product\' has Unnecessary spaces. Please remove spaces.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addlaptop.laptopproduct.focus();
			return ;
		}
		if(description == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Description\'.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addlaptop.description.focus();
			return ;
         }
		 if (document.addlaptop.description.value.length < 3)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 3 characters in the \'Description\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addlaptop.description.focus();
			return ;
         }
		 if (document.addlaptop.description.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the \'Description\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addlaptop.vehdesc.focus();
			return ;
         }
		  if (! document.addlaptop.description.value.match(regularexp))
		{
			errMsg ="<font color='#ff3333'>\'Description\' has Unnecessary spaces. Please remove spaces.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addlaptop.description.focus();
			return ;
		}
		for(var i = 0; i < document.addlaptop.description.value.length; i++)
		{
			 if (iCharsdesc.indexOf(document.addlaptop.description.value.charAt(i))!= -1)
			 {
				errMsg ="<font color='#ff3333'>Special characters are not allowed in \'Description\' field.</font>";
				document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
				document.addlaptop.description.focus();
				return ;
			 }
		}
		if(isDigits(description)==true)
		{
			errMsg ="<font color='#ff3333'>Only Numerics are not allowed in the \'Description\' field.</font>";
			document.getElementById("displayaddvehicleerrormsg").innerHTML=errMsg;
			document.addlaptop.description.focus();
			return ;
         }
		
	document.addlaptop.action = "../../../../servlet/AddLaptopDetails?hidWhatToDo=addlaptopdetails&batterytype="+batterytype+"&laptopbrand="+laptopbrand+"&laptopmodel="+laptopmodel+"&laptopproduct="+laptopproduct+"&description="+description;
//	alert(document.addlaptop.action);
	document.addlaptop.method="post";
	document.addlaptop.submit();
}
function checkLaptopProduct()	
{ 
	var batterytype = document.addlaptop.batterytype.value;
	var laptopbrand = document.addlaptop.laptopbrand.value;
	var laptopmodel = document.addlaptop.laptopmodel.value;
	var laptopproduct = document.addlaptop.laptopproduct.value;
	var url="../../../../servlet/AddLaptopDetails?hidWhatToDo=checklaptopproduct&batterytype="+batterytype+"&laptopbrand="+laptopbrand+"&laptopmodel="+laptopmodel+"&laptopproduct="+laptopproduct;
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
					document.addlaptop.vehmodel.value="";
					document.addlaptop.vehmodel.focus();
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
	location.href="../../../../jsp/admin/batterystore/laptopBookBattery/addlaptop.jsp"
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
<body onload="document.addlaptop.batterytype.focus(); " >
<form name="addlaptop" action="request_for_quote.asp"  method="post">
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
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
								<td class="subheading">Add&nbsp;Laptop&nbsp;Details</td>
								<td  align="right"><a href="../../../../servlet/AdminBatteryLogin?hidWhatToDo=batteryadminhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
							</tr>
							<tr><td height="10"></td></tr>
							<tr><td align="center" class="insidecontentbat"><div id="displayaddvehicleerrormsg"></div></td></tr>
							<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
								<tr>
									<td width="39%" class="insidecontent" align="right">Select&nbsp;Battery&nbsp;Type<font color="red">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left"><select name='batterytype' id='battery_type' class="insidecontent"  style='width:180px'><option value='0'>Select&nbsp;Battery&nbsp;Type</option>
										<option value='1,Laptop Batteries'>Laptop Batteries</option>									
									</select></td>
								</tr>
								<tr>
									<td  width="39%" class="insidecontent" align="right">Laptop&nbsp;Brand<font color="red">*</font></td><td width="2%" align="center">:</td>
									<!--<td id="gensets" width="39%" class="insidecontent" align="right">Gensets&nbsp;Make<font color="red">*</font></td>
									<td id="generators" width="39%" class="insidecontent" align="right">Generators&nbsp;Make<font color="red">*</font></td>
									<td width="2%" align="center">:</td>-->
									<td width="59%" class="insidecontent" align="left"><input type="text" name="laptopbrand" size="20" maxlength="50"><span class="hint">Provide Laptop Brand in the format 'Acer'.<span class="hint-pointer">&nbsp;</span></span></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Laptop&nbsp;Model<font color="red">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="laptopmodel" size="20"  maxlength="50" ><span class="hint">Provide Laptop Model in the format 'Acer AO'.<span class="hint-pointer">&nbsp;</span></span><div id="chkvehmodel"></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Laptop&nbsp;Product<font color="red">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="laptopproduct" size="20"  maxlength="50" onchange="checkLaptopProduct();"><span class="hint">Provide Laptop Product in the format 'Acer AO 5332'.<span class="hint-pointer">&nbsp;</span></span><div id="chkvehmodel"></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right" valign="top">Description<font color="red">*</font></td><td width="2%" align="center" valign="top">:</td>
									<td width="59%" class="insidecontent" align="left"><textarea name="description" cols="20" rows="4"></textarea></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right"><input type="button" value="Submit" class="button4" onclick="javascript:addLaptopDetails();"></td><td width="1%"></td>
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