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
function isDigits(argvalue) 
{
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
       startFrom = 0;
    }
    for (var n = 0; n < argvalue.length; n++) {
        if (validChars.indexOf(argvalue.substring(n, n+1)) == -1) return false;
    }
  return true;
}
function funToAddInverter()
{
		var invname = document.addinverter.invname.value;
		var invbrand = document.addinverter.invbrand.value;
		var invmodel = document.addinverter.invmodel.value;
		var invwarrnty = document.addinverter.invwarrnty.value;
		var invcapcity = document.addinverter.invcapcity.value;
		var tubelights = document.addinverter.tubelights.value;
		var fans = document.addinverter.fans.value;
		var telivision = document.addinverter.telivision.value;
		var computers = document.addinverter.computers.value;
		var invdesc = document.addinverter.invdesc.value;
		var fileName = document.addinverter.invattachment.value;
		varAllowedFormats = '<%=allowedImgFormats%>';
		var iChars3 = "`~!@#$%^&*()+=-_[]\\\';,/{}|\":<>?";
		var iChars1 = "`~!@#$%^&*()=_[]\\\';,/{}|\":<>?";
		var iChars2 = "`~!@#$%^&*()+=[]';,{}|\":<>?.";
		var regularexp=/^[^\s\-].*[^\s\-]$/;
		//var regularexp1=/^([a-zA-Z0-9]+)$/;
		var iCharsdesc = "`~!@#$%^*()=_[]\\\';/{}|\":<>";
		
		var regularexp1=/^(\d.+[a-zA-Z ]|[a-zA-Z ]+\d.)[a-zA-Z\d.]*$/
		var regularexpappliances=/^(([0-9],?)+)$/
		invwarrnty=invwarrnty.replace(/\+/g, '%2B');
		if(invname == "")
		 {
			errMsg ="<font color='#ff3333'>Please enter \'Inverter Name\'.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.invname.focus();
			return ;
        }
		if (document.addinverter.invname.value.length < 2)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 2 characters in the \'Inverter Name\' field.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.invname.focus();
			return ;
         }
		if (document.addinverter.invname.value.length > 49)
		 {
			errMsg ="<font color='#ff3333'>Please enter only 49 characters in the in the \'Inverter Name\' field.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.invname.focus();
			return ;
         }
		 if (document.addinverter.invname.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the \'Inverter Name\' field.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.invname.focus();
			return ;
         }
		for (var i = 0; i < document.addinverter.invname.value.length; i++)
		{
			 if (iChars3.indexOf(document.addinverter.invname.value.charAt(i))!= -1)
			 {
				errMsg ="<font color='#ff3333'>Special characters are not allowed in \'Inverter Name\' field.</font>";
				document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
				document.addinverter.invname.focus();
				return ;
			 }
		}
		if(isDigits(invname)==true)
		{
			errMsg ="<font color='#ff3333'>Only Numerics are not allowed in the \'Inverter Name\' field.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.invname.focus();
			return ;
         }
		if (! document.addinverter.invname.value.match(regularexp))
		{
			errMsg ="<font color='#ff3333'>\'Inverter Name\' has Unnecessary spaces. Please remove spaces.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.invname.focus();
			return ;
		}
		if(invbrand == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Inverter Brand\'.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.invbrand.focus();
			return ;
         }
	    if (document.addinverter.invbrand.value.length < 3)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 3 characters in the \'Inverter Brand\' field.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.invbrand.focus();
			return ;
         }
		if (document.addinverter.invbrand.value.length > 49)
		 {
			errMsg ="<font color='#ff3333'>Please enter only 49 characters in the in the \'Inverter Brand\' field.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.invbrand.focus();
			return ;
         }
		 if (document.addinverter.invbrand.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the \'Inverter Brand\' field.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.invbrand.focus();
			return ;
         }
		 for (var i = 0; i < document.addinverter.invbrand.value.length; i++)
		{
         if (iChars3.indexOf(document.addinverter.invbrand.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#ff3333'>Special characters are not allowed in \'Inverter Brand\' field.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.invbrand.focus();
			return ;
         }
		}
		if(isDigits(invbrand)==true)
		{
			errMsg ="<font color='#ff3333'>Only Numerics are not allowed in the \'Inverter Brand\' field.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.invbrand.focus();
			return ;
         }
		 if (! document.addinverter.invbrand.value.match(regularexp))
		{
			errMsg ="<font color='#ff3333'>\'Inverter Brand\' has Unnecessary spaces. Please remove spaces.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.invbrand.focus();
			return ;
		}
		if(invmodel == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Inverter Model\'.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.invmodel.focus();
			return ;
         }
		else
			{
			if (document.addinverter.invmodel.value.length < 3)
			 {
				errMsg ="<font color='#ff3333'>Please enter at least 3 characters in the \'Inverter Model\' field.</font>";
				document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
				document.addinverter.invmodel.focus();
				return ;
			 }
			if (document.addinverter.invmodel.value.length > 49)
			 {
				errMsg ="<font color='#ff3333'>Please enter only 49 characters in the in the \'Inverter Model\' field.</font>";
				document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
				document.addinverter.invmodel.focus();
				return ;
			 }
			 if (document.addinverter.invmodel.value.indexOf(' ')==0 )
			 {
				 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the in the \'Inverter Model\' field.</font>";
				document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
				document.addinverter.invmodel.focus();
				return ;
			 }
			for (var i = 0; i < document.addinverter.invmodel.value.length; i++)
			{
			 if (iChars2.indexOf(document.addinverter.invmodel.value.charAt(i))!= -1)
			 {
				errMsg ="<font color='#ff3333'>Special characters are not allowed in \'Inverter Model\' field.</font>";
				document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
				document.addinverter.invmodel.focus();
				return ;
			 }
			}
			if (! document.addinverter.invmodel.value.match(regularexp))
			{
				errMsg ="<font color='#ff3333'>\'Inverter Model\' has Unnecessary spaces. Please remove spaces.</font>";
				document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
				document.addinverter.invmodel.focus();
				return ;
			}
		}
		if(invwarrnty == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Warranty\'.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.invwarrnty.focus();
			return ;
         }
		 if(invwarrnty == 0)
		{
			errMsg ="<font color='#ff3333'>Please enter valid \'Warranty\'.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.invwarrnty.focus();
			return ;
         }
		 if (document.addinverter.invwarrnty.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the in the \'Warranty\' field.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.invwarrnty.focus();
			return ;
         }
		 for (var i = 0; i < document.addinverter.invwarrnty.value.length; i++)
		{
         if (iChars1.indexOf(document.addinverter.invwarrnty.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#ff3333'>Special characters are not allowed in \'Warranty\' field.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.invwarrnty.focus();
			return ;
         }
		}
		if(invwarrnty=="NA")
		{
		}
		else
		{
				if (regularexp1.test(invwarrnty)==false)
				{
					errMsg ="<font color='#ff3333'>Please enter only Alphanumerics in the \'Warranty\' field.</font>";
					document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
					document.addinverter.invwarrnty.focus();
					return ;
				}
		}
			 if(invcapcity == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Capacity\'.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.invcapcity.focus();
			return ;
         }
		  if(invcapcity == 0 || invcapcity == "0 Va" || invcapcity == "0 va" || invcapcity == "0 VA" || invcapcity == "0 vA")
		  {
			errMsg ="<font color='#ff3333'>Please enter valid \'Capacity\'.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.invcapcity.focus();
			return ;
         }
		 if (document.addinverter.invcapcity.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the \'Capacity\' field.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.invcapcity.focus();
			return ;
         }
		for (var i = 0; i < document.addinverter.invcapcity.value.length; i++)
		{
         if (iChars3.indexOf(document.addinverter.invcapcity.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#ff3333'>Special characters are not allowed in \'Capacity\' field.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.invcapcity.focus();
			return ;
         }
		}
		if(invcapcity=="NA")
		{
		}
		else
		{
				if (regularexp1.test(invcapcity)==false)
				{
					errMsg ="<font color='#ff3333'>Please enter only Alphanumerics in the \'Capacity\' field.</font>";
					document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
					document.addinverter.invcapcity.focus();
					return ;
				}
		}
		/* if (document.addinverter.invcapcity.value.match(regularexp1))
		{
			errMsg ="<font color='#ff3333'>Please enter only Alphanumerics in the \'Capacity\' field.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.invcapcity.focus();
			return ;
		}*/
		if (! document.addinverter.invcapcity.value.match(regularexp))
		{
			errMsg ="<font color='#ff3333'>\'Capacity\' has Unnecessary spaces. Please remove spaces.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.invcapcity.focus();
			return ;
		}
		if(tubelights == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Tube Lights field\'.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.tubelights.focus();
			return ;
         }
		 if (document.addinverter.tubelights.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the \'Tube Lights\' field.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.tubelights.focus();
			return ;
         }
		if(isDigits(tubelights)==true)
		{
			errMsg ="<font color='#ff3333'>Only Numerics are allowed in the \'Tube Lights\' field.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.tubelights.focus();
			return ;
         }
		 if (! document.addinverter.tubelights.value.match(regularexpappliances))
		{
			errMsg ="<font color='#ff3333'>Please enter tube lights in the form of 1,1</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.tubelights.focus();
			return ;
		}
		var result = tubelights.slice(-1);		
		if(result==",")
		{
			errMsg ="<font color='#ff3333'>Please enter tube lights in the form of 1,1</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.tubelights.focus();
			return ;
		}
		if(fans == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Fans field\'.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.fans.focus();
			return ;
         }
		 if (document.addinverter.fans.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the \'Fans\' field.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.fans.focus();
			return ;
         }
		if(isDigits(fans)==true)
		{
			errMsg ="<font color='#ff3333'>Only Numerics are allowed in the \'Fans\' field.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.fans.focus();
			return ;
         }
		  if (! document.addinverter.fans.value.match(regularexpappliances))
		{
			errMsg ="<font color='#ff3333'>Please enter fans in the form of 1,1</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.fans.focus();
			return ;
		}
		var resultfan = fans.slice(-1);		
		if(resultfan==",")
		{
			errMsg ="<font color='#ff3333'>Please enter fans in the form of 1,1</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.fans.focus();
			return ;
		}
		  if(computers == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Computers field\'.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.computers.focus();
			return ;
         }
		 if (document.addinverter.computers.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the \'Computers\' field.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.computers.focus();
			return ;
         }
		if(isDigits(computers)==true)
		{
			errMsg ="<font color='#ff3333'>Only Numerics are allowed in the \'Computers\' field.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.computers.focus();
			return ;
         }
		if (! document.addinverter.computers.value.match(regularexpappliances))
		{
			errMsg ="<font color='#ff3333'>Please enter computers in the form of 1,1</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.computers.focus();
			return ;
		}
		var resultcomputers = computers.slice(-1);		
		if(resultcomputers==",")
		{
			errMsg ="<font color='#ff3333'>Please enter computers in the form of 1,1</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.computers.focus();
			return ;
		}
		 if(telivision == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'TV field\'.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.telivision.focus();
			return ;
         }
		 if (document.addinverter.telivision.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the \'TV\' field.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.telivision.focus();
			return ;
         }
		if(isDigits(telivision)==true)
		{
			errMsg ="<font color='#ff3333'>Only Numerics are allowed in the \'TV\' field.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.telivision.focus();
			return ;
        }
		if (! document.addinverter.telivision.value.match(regularexpappliances))
		{
			errMsg ="<font color='#ff3333'>Please enter telivision in the form of 1,1</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.telivision.focus();
			return ;
		}
		var resulttelivision = telivision.slice(-1);		
		if(resulttelivision==",")
		{
			errMsg ="<font color='#ff3333'>Please enter telivision in the form of 1,1</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.telivision.focus();
			return ;
		}
		if(invdesc == "")
		{
			errMsg ="<font color='#ff3333'>Please enter \'Description\'.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.invdesc.focus();
			return ;
        }
		if (document.addinverter.invdesc.value.length < 3)
        {
			errMsg ="<font color='#ff3333'>Please enter at least 3 characters in the \'Description\' field.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.invdesc.focus();
			return ;
        }
		if (document.addinverter.invdesc.value.indexOf(' ')==0 )
		{
			errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the \'Description\' field.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.invdesc.focus();
			return ;
        }
		if (! document.addinverter.invdesc.value.match(regularexp))
		{
			errMsg ="<font color='#ff3333'>\'Description\' has Unnecessary spaces. Please remove spaces.</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.invdesc.focus();
			return ;
		}
		for(var i = 0; i < document.addinverter.invdesc.value.length; i++)
		{
			 if (iCharsdesc.indexOf(document.addinverter.invdesc.value.charAt(i))!= -1)
			 {
				errMsg ="<font color='#ff3333'>Special characters are not allowed in \'Description\' field.</font>";
				document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
				document.addinverter.invdesc.focus();
				return ;
			 }
		}
		if(isDigits(invdesc)==true)
		{
				errMsg ="<font color='#ff3333'>Only numerics are not allowes in \'Description\'.</font>";
				document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
				document.addinverter.invdesc.focus();
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
					document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
					document.addinverter.invattachment.focus();
					return ;
				}
			}
		}
		if(fileName == "")
		  {
			errMsg ="<font color='#ff3333'>Please select a image file..</font>";
			document.getElementById("displayaddinvertererrormsg").innerHTML=errMsg;
			document.addinverter.invattachment.focus();
			return ;
		  }

		fileName=escape(fileName);
	document.addinverter.action = "../../../../servlet/InverterDetails?hidWhatToDo=addinverterdetails&invname="+invname+"&invbrand="+invbrand+"&invmodel="+invmodel+"&invwarrnty="+invwarrnty+"&invcapcity="+invcapcity+"&tubelights="+tubelights+"&fans="+fans+"&telivision="+telivision+"&computers="+computers+"&fileName="+fileName+"&invdesc="+invdesc;
	//alert(document.addinverter.action);
	document.addinverter.method="post";
	document.addinverter.submit();
}
function funOnClickModel()	
{ 
	var invname = document.addinverter.invname.value;
	var invbrand = document.addinverter.invbrand.value;
	var invcapcity = document.addinverter.invcapcity.value;
	var url="../../../../servlet/InverterDetails?hidWhatToDo=checkcapacity&invname="+invname+"&invbrand="+invbrand+"&invcapcity="+invcapcity;
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
		  if (xmlhttp.readyState==4 && xmlhttp.status==200)
		  {
			    resp =xmlhttp.responseText;
			    if(resp.indexOf("Already")>=0)
				{
					document.getElementById("chkmodel").innerHTML=xmlhttp.responseText;
					document.getElementById("chkmodel").innerHTML=resp;
					document.addinverter.invcapcity.value="";
					document.addinverter.invcapcity.focus();
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
	location.href="../../../../jsp/admin/batterystore/inverter/addinverter.jsp"
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
<body onload="document.addinverter.invname.focus();" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="addinverter" method="post" ENCTYPE="multipart/form-data">
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
								<td class="subheading">Add&nbsp;Inverter</td>
								<td  align="right"><a href="../../../../servlet/AdminBatteryLogin?hidWhatToDo=batteryadminhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
							</tr>
							<tr><td height="10"></td></tr>
							<tr><td align="center" class="insidecontentbat"><div id="displayaddinvertererrormsg"></div></td></tr>
							<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
								<tr>
									<td width="39%" class="insidecontent" align="right">Inverter&nbsp;Name<font color="red">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="invname" size="20" maxlength="50"><span class="hint">Provide Inverter Name in the format 'Pro'.<span class="hint-pointer">&nbsp;</span></span></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Inverter&nbsp;Brand<font color="red">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="invbrand" size="20" maxlength="50"><span class="hint">Provide Inverter Brand in the format 'Amaron'.<span class="hint-pointer">&nbsp;</span></span></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Inverter&nbsp;Model<font color="red">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="invmodel" size="20"  maxlength="50"><span class="hint">Provide Inverter Model.<span class="hint-pointer">&nbsp;</span></span></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Warranty<font color="red">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="invwarrnty" size="20" maxlength="100"><span class="hint">Provide Warranty in the format '24 months + free service'.<span class="hint-pointer">&nbsp;</span></span></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Capacity<font color="red">*</font></td><td width="2%" align="center">:</td>
									<!--<td width="59%" class="insidecontent" align="left"><input type="text" name="invcapcity" size="20" maxlength="10" onchange="funOnClickModel();"><span class="hint">Provide Capacity in the format '80 VA'.<span class="hint-pointer">&nbsp;</span></span><div id="chkmodel"></div></td>-->
									<td width="59%" class="insidecontent" align="left"><input type="text" name="invcapcity" size="20" maxlength="10" ><span class="hint">Provide Capacity in the format '80 VA'.<span class="hint-pointer">&nbsp;</span></span><div id="chkmodel"></div></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Tube&nbsp;Lights<font color="red">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="tubelights" size="20" maxlength="15"><span class="hint">Provide Tube Lights in the format '1,2'.<span class="hint-pointer">&nbsp;</span></span></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Fans<font color="red">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="fans" size="20" maxlength="15"><span class="hint">Provide Fans in the format '1,2'.<span class="hint-pointer">&nbsp;</span></span></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Computers<font color="red">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="computers" size="20" maxlength="15"><span class="hint">Provide Computers in the format '1,2'.<span class="hint-pointer">&nbsp;</span></span></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">TV<font color="red">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="telivision" size="20" maxlength="15"><span class="hint">Provide TV in the format '1,2'.<span class="hint-pointer">&nbsp;</span></span></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right" valign="top">Description<font color="red">*</font></td><td width="2%" align="center" valign="top">:</td>
									<td width="59%" class="insidecontent" align="left"><textarea name="invdesc" cols="20" rows="4"></textarea></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right" valign="top">Select&nbsp;an&nbsp;image&nbsp;file<font color="red">*</font></td><td width="2%" align="center" valign="top">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="file" name="invattachment" size="20"/><br><br>
									<font class="insidecontent" width="70%">&nbsp;* <b>Image&nbsp;formats</b>&nbsp;:&nbsp; gif,bmp,jpg,jpeg,jpe,png,wbmp<br>&nbsp;&nbsp;* Please check your image file name should not consist 
									of spaces.<br>* Image Resolution is 100*100.</td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right"><input type="button" value="Submit" class="button4" onclick="javascript:funToAddInverter();"></td><td width="1%"></td>
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
document.getElementById("displayaddinvertererrormsg").innerHTML=loginmessg;
</script>
<%
	session.removeAttribute("sesbatErrorMsg");
}
%>
</body>
</html>