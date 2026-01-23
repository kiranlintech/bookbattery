<%--
    Document   : addlaptop BookBattery
    Created on : feb 6, 2014, 4:22:12 PM
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
function addlaptopbatteryDetails()
{
	
		var batterybrand = document.addlaptopbattery.batterybrand.value;
		var batterymodel = document.addlaptopbattery.batterymodel.value;
		var voltage = document.addlaptopbattery.voltage.value;
		var batterywarrnty = escape(document.addlaptopbattery.batterywarrnty.value);
		var cellcount = document.addlaptopbattery.cellcount.value;
		var watthr = document.addlaptopbattery.watthr.value;
		var batteryactualprice = parseInt(document.addlaptopbattery.batteryactualprice.value);	
		var batpartnum = document.addlaptopbattery.batpartnum.value;
		var batterydesc = document.addlaptopbattery.batterydesc.value;
		var batteryattachment = document.addlaptopbattery.batteryattachment.value;
		var amazonlink = escape(document.addlaptopbattery.amazonlink.value);
		varAllowedFormats = '<%=allowedImgFormats%>'; 
		var iChars3 = "`~!@#$%^&*()+=-_[]\\\';,/{}|\":<>?";
		var iChars1 = "`~!@#$%^&*()=_[]\\\';,/{}|\":<>?";
		var iChars2 = "`~!@#$%^&*+=[]';,{}|\":<>?.";
		var voltregex = "!#$%^&*()+=[]\\\';/{}|\":<>?,~@%_/-`"; 
		var iCharsbatterypart = "`~!@#$%^&*()=-_[]\\\';/{}|\":<>?";
		var iCharsdesc = "`~!@#$%^*()=_[]\\\';/{}|\":<>";

		var regularexp=/^[^\s\-].*[^\s\-]$/;
		var regularexp1=/^(\d.+[a-zA-Z ]|[a-zA-Z ]+\d.)[a-zA-Z\d.]*$/
		var regexp2 =/^[-a-zA-Z0-9@:%_\+.~#!-?&amp;//=]+$/;
		var regularexpbatterypart=/^([a-zA-Z0-9])$/;

		batterywarrnty=batterywarrnty.replace(/\+/g, '%2B');
		if(batterybrand == "")
		{
			errMsg ="<font color='#ff3333'>Please enter \'Laptop Battery Brand\'.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.batterybrand.focus();
			return ;
       }
		if (document.addlaptopbattery.batterybrand.value.length < 2)
		 {
			errMsg ="<font color='#ff3333'>Please enter at least 2 characters in the \'Laptop Battery Brand\' field.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.batterybrand.focus();
			return ;
		 }
		if (document.addlaptopbattery.batterybrand.value.length > 49)
		 {
			errMsg ="<font color='#ff3333'>Please enter only 49 characters in the in the \'Laptop Battery Brand\' field.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.batterybrand.focus();
			return ;
         }
		 if (document.addlaptopbattery.batterybrand.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the \'Laptop Battery Brand\' field.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.batterybrand.focus();
			return ;
         }
		for (var i = 0; i < document.addlaptopbattery.batterybrand.value.length; i++)
		{
			 if (iChars3.indexOf(document.addlaptopbattery.batterybrand.value.charAt(i))!= -1)
			 {
				errMsg ="<font color='#ff3333'>Special characters are not allowed in \'Laptop Battery Brand\' field.</font>";
				document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
				document.addlaptopbattery.batterybrand.focus();
				return ;
			 }
		}
		if(isDigits(batterybrand)==true)
		{
			errMsg ="<font color='#ff3333'>Only Numerics are not allowed in the \'Laptop Battery Brand\' field.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.batterybrand.focus();
			return ;
         }
		if (! document.addlaptopbattery.batterybrand.value.match(regularexp))
		{
			errMsg ="<font color='#ff3333'>\'Laptop Battery Brand\' has Unnecessary spaces. Please remove spaces.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.batterybrand.focus();
			return ;
		}
		
		if(batterymodel == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Battery Model\'.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.batterymodel.focus();
			return ;
         }
	    if (document.addlaptopbattery.batterymodel.value.length < 3)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 3 characters in the \'Battery Model\' field.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.batterymodel.focus();
			return ;
         }
		if (document.addlaptopbattery.batterymodel.value.length > 49)
		 {
			errMsg ="<font color='#ff3333'>Please enter only 49 characters in the in the \'Battery Model\' field.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.batterymodel.focus();
			return ;
         }
		 if (document.addlaptopbattery.batterymodel.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the in the \'Battery Model\' field.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.batterymodel.focus();
			return ;
         }
		for (var i = 0; i < document.addlaptopbattery.batterymodel.value.length; i++)
		{
         if (iChars2.indexOf(document.addlaptopbattery.batterymodel.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#ff3333'>Special characters are not allowed in \'Battery Model\' field.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.batterymodel.focus();
			return ;
         }
		}
		if (! document.addlaptopbattery.batterymodel.value.match(regularexp))
		{
			errMsg ="<font color='#ff3333'>\'Battery Model\' has Unnecessary spaces. Please remove spaces.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.batterymodel.focus();
			return ;
		}		 
		 if (document.addlaptopbattery.batterywarrnty.value.indexOf(' ')==0 )
		 {
				errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the in the \'Warranty\' field.</font>";
				document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
				document.addlaptopbattery.batterywarrnty.focus();
				return ;
         }
		for (var i = 0; i < document.addlaptopbattery.batterywarrnty.value.length; i++)
		{
         if (iChars1.indexOf(document.addlaptopbattery.batterywarrnty.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#ff3333'>Special characters are not allowed in \'Warranty\' field.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.batterywarrnty.focus();
			return ;
         }
		}
		if(voltage == "")
		 {
			errMsg ="<font color='#ff3333'>Please enter \'voltage\'.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.voltage.focus();
			return ;
         }
	
		 if(voltage == 0)
		 {
			errMsg ="<font color='#ff3333'>Please enter valid \'voltage\'.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.voltage.focus();
			return ;
         }
		  if(voltage == "0 v" || voltage == "0 V")
		 {
			errMsg ="<font color='#ff3333'>Please enter valid \'voltage\'.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.voltage.focus();
			return ;
         }
		 if (document.addlaptopbattery.voltage.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the in the \'voltage\' field.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.voltage.focus();
			return ;
         }
		 if(voltage=="NA")
		{
			
		}
		else
		{
			 if (regularexp1.test(voltage)==false)
			{
				errMsg ="<font color='#ff3333'>Please enter only Alphanumerics in the \'Voltage\' field.</font>";
				document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
				document.addlaptopbattery.voltage.focus();
				return ;
			}
		}
		if(isDigits(voltage)==true)
		{
			errMsg ="<font color='#ff3333'>Only Numerics are not allowed in the \'Voltage\' field.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.voltage.focus();
			return ;
         }
		for (var i = 0; i < document.addlaptopbattery.voltage.value.length; i++)
		{
			 if (iChars1.indexOf(document.addlaptopbattery.voltage.value.charAt(i))!= -1)
			 {
				errMsg ="<font color='#ff3333'>Special characters are not allowed in \'Voltage\' field.</font>";
				document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
				document.addlaptopbattery.voltage.focus();
				return ;
			 }
		}
		if (document.addlaptopbattery.voltage.value.indexOf('.')==0)
		 {
			 errMsg ="<font color='#ff3333'>Please enter valid \'Voltage\'.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.voltage.focus();
			return ;
         }
		if(cellcount == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'cellcount\'.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.cellcount.focus();
			return ;
         }
		 if(cellcount == 0)
		{
			errMsg ="<font color='#ff3333'>Please enter valid \'cellcount\'.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.cellcount.focus();
			return ;
         }
		
		if(cellcount=="0 cell" || cellcount=="0 Cell")
		{
			errMsg ="<font color='#ff3333'>Please enter valid \'cellcount\'.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.cellcount.focus();
			return ;
		}
		 if (document.addlaptopbattery.cellcount.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the in the \'cellcount\' field.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.cellcount.focus();
			return ;
         }
		  if(cellcount=="NA")
			{
				
			}
			else
			{
				 if (regularexp1.test(cellcount)==false)
				{
					errMsg ="<font color='#ff3333'>Please enter only Alphanumerics in the \'Cellcount\' field.</font>";
					document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
					document.addlaptopbattery.cellcount.focus();
					return ;
				}
			}
		 for (var i = 0; i < document.addlaptopbattery.cellcount.value.length; i++)
		{
         if (iChars1.indexOf(document.addlaptopbattery.cellcount.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#ff3333'>Special characters are not allowed in \'cellcount\' field.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.cellcount.focus();
			return ;
         }
		}
		if(watthr == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'watthr\'.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.watthr.focus();
			return ;
         }
		 if(watthr == 0)
		{
			errMsg ="<font color='#ff3333'>Please enter valid \'watthr\'.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.watthr.focus();
			return ;
         }
		 if(watthr=="0 Wh" || watthr=="0 wh" || watthr=="0 WH")
		{
			errMsg ="<font color='#ff3333'>Please enter valid \'watthr\'.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.watthr.focus();
			return ;
		}
		
		 if (document.addlaptopbattery.watthr.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the in the \'watthr\' field.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.watthr.focus();
			return ;
         }
		   if(watthr=="NA")
			{
				
			}
			else
			{
				if (regularexp1.test(watthr)==false)
				{
					errMsg ="<font color='#ff3333'>Please enter only Alphanumerics in the \'Watthr\' field.</font>";
					document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
					document.addlaptopbattery.watthr.focus();
					return ;
				}
			}
		 for (var i = 0; i < document.addlaptopbattery.watthr.value.length; i++)
		{
         if (iChars1.indexOf(document.addlaptopbattery.watthr.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#ff3333'>Special characters are not allowed in \'watthr\' field.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.watthr.focus();
			return ;
         }
		}
		
		if(document.addlaptopbattery.batteryactualprice.value == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Actual Price\'.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.batteryactualprice.focus();
			return ;
         }
		else if(document.addlaptopbattery.batteryactualprice.value < 0.1)
		{
			errMsg ="<font color='#ff3333'>Please enter valid price in \'Actual Price\'.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.batteryactualprice.focus();
			return ;
		}
		else if (document.addlaptopbattery.batteryactualprice.value.length < 2)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 2 numbers in the \'Actual Price\' field.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.batteryactualprice.focus();
			return ;
         }
		else if(batteryactualprice < 100)
		{
			errMsg ="<font color='#ff3333'>\'Actual Price\' should not lessthan Rs 100.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.batteryactualprice.focus();
			return ;
		}
		else 
		{
			var checkOK = "0123456789";
			var checkStr = document.addlaptopbattery.batteryactualprice.value;
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
				document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
				document.addlaptopbattery.batteryactualprice.focus();
				return ;
			}
		}
		if (document.addlaptopbattery.batpartnum.value.indexOf(' ')==0 )
		{
				errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the in the \'Battery Part Number\' field.</font>";
				document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
				document.addlaptopbattery.batpartnum.focus();
				return ;
        }		
		if (document.addlaptopbattery.batpartnum.value.match(regularexpbatterypart))
		{
			errMsg ="<font color='#ff3333'>Please enter only Alphanumerics in the \'Battery Part Number\' field.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.batpartnum.focus();
			return ;
		}
		 if(batpartnum == 0)
		{
			errMsg ="<font color='#ff3333'>Please enter valid \'Battery Part Number\'.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.batpartnum.focus();
			return ;
         }
		
		if(batterydesc == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Description\'.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.batterydesc.focus();
			return ;
         }
		  if (document.addlaptopbattery.batterydesc.value.length < 3)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 3 characters in the \'Description\' field.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.batterydesc.focus();
			return ;
         }
		 if (document.addlaptopbattery.batterydesc.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the \'Description\' field.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.batterydesc.focus();
			return ;
         }
		 if (! document.addlaptopbattery.batterydesc.value.match(regularexp))
		{
			errMsg ="<font color='#ff3333'>\'Description\' has Unnecessary spaces. Please remove spaces.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.batterydesc.focus();
			return ;
		}
		for(var i = 0; i < document.addlaptopbattery.batterydesc.value.length; i++)
		{
			 if (iCharsdesc.indexOf(document.addlaptopbattery.batterydesc.value.charAt(i))!= -1)
			 {
				errMsg ="<font color='#ff3333'>Special characters are not allowed in \'Description\' field.</font>";
				document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
				document.addlaptopbattery.batterydesc.focus();
				return ;
			 }
		}
		if(isDigits(batterydesc)==true)
			{
				errMsg ="<font color='#ff3333'>Only numerics are not allowes in \'Description\'.</font>";
				document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
				document.addlaptopbattery.batterydesc.focus();
				return ;
			}

		 if(batteryattachment.length >0)
		{
			var varIndex = batteryattachment.lastIndexOf(".");
				//alert(varIndex);
			var varBolMatch = false;
			if(varIndex > 0)
			{
				var varFileExt  = batteryattachment.substring(++varIndex);
				var varStringArr = varAllowedFormats.split(",");
					

				for(var i=0;i<varStringArr.length;i++){
					if(varStringArr[i].toLowerCase() == varFileExt.toLowerCase()){
						varBolMatch = true;
					}
				}
				if(!varBolMatch){ 
					errMsg ="<font color='#ff3333'>You can attach "+varAllowedFormats+" only.</font>";
					document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
					document.addlaptopbattery.batteryattachment.focus();
					return ;
				}
			}
		}
		if(batteryattachment == "")
		  {
			errMsg ="<font color='#ff3333'>Please select a image file..</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.batattachment.focus();
			return ;
		  }
		  if(amazonlink == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Amazon redirect Link\'.</font>";
			document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
			document.addlaptopbattery.amazonlink.focus();
			return ;
         }

		 if (! document.addlaptopbattery.amazonlink.value.match(regexp2))
			{
				errMsg ="<font color='#ff3333'>Please enter \'valid Link\'.</font>";
				document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
				document.addlaptopbattery.amazonlink.focus();
				return ;
			}
			if (/[a-z][A-Z]{2}/i.test(document.addlaptopbattery.amazonlink.value) != true) 
			{
				errMsg ="<font color='#ff3333'>Please enter \'valid Link\'.</font>";
				document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
				document.addlaptopbattery.amazonlink.focus();
				return ;
			}
			if(amazonlink=="http://")
			{	
				errMsg ="<font color='#ff3333'>Please enter \'valid Link\'.</font>";
				document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
				document.addlaptopbattery.amazonlink.focus();
				return ;
			}
			if(amazonlink=="https://")
			{	
				errMsg ="<font color='#ff3333'>Please enter \'valid Link\'.</font>";
				document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
				document.addlaptopbattery.amazonlink.focus();
				return ;
			}
			if((document.addlaptopbattery.elements["amazonlink"].value.indexOf("wwww") ==0) || (document.addlaptopbattery.elements["amazonlink"].value.indexOf("ww.") ==0) || (document.addlaptopbattery.elements["amazonlink"].value.indexOf("WWWW") ==0) || (document.addlaptopbattery.elements["amazonlink"].value.indexOf("WW.") ==0))
			{
				errMsg ="<font color='#ff3333'>Please enter \'valid Link\'.</font>";
				document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
				document.addlaptopbattery.amazonlink.focus();
				return ;
			}
			if(isDigits(amazonlink)==true)
			{
				errMsg ="<font color='#ff3333'>Please enter \'valid Link\'.</font>";
				document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=errMsg;
				document.addlaptopbattery.amazonlink.focus();
				return ;
			}

		  
	document.addlaptopbattery.action = "../../../../servlet/AddLaptopBatteryDetails?hidWhatToDo=addlaptopbatterydetails&batterybrand="+batterybrand+"&batterymodel="+batterymodel+"&batterywarrnty="+batterywarrnty+"&voltage="+voltage+"&cellcount="+cellcount+"&watthr="+watthr+"&batteryactualprice="+batteryactualprice+"&batpartnum="+batpartnum+"&batterydesc="+batterydesc+"&batteryattachment="+batteryattachment+"&amazonlink="+amazonlink;
	//alert(document.addlaptopbattery.action);
	document.addlaptopbattery.method="post";
	document.addlaptopbattery.submit();
}
function checkModel()	
{ 
	var batbrand = document.addlaptopbattery.batterybrand.value;
	var batmodel = document.addlaptopbattery.batterymodel.value;
	var url="../../../../servlet/AddLaptopBatteryDetails?hidWhatToDo=checkmodel&batterybrand="+batbrand+"&batterymodel="+batmodel;
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
					document.addlaptopbattery.batmodel.value="";
					document.addlaptopbattery.batmodel.focus();
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
	location.href="../../../../jsp/admin/batterystore/laptopBookBattery/addlaptopBookBattery.jsp"
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
<body onload="document.addlaptopbattery.batterybrand.focus();" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="addlaptopbattery" method="post" ENCTYPE="multipart/form-data">
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
								<td class="subheading">Add&nbsp;Laptop&nbsp;Battery</td>
								<td  align="right"><a href="../../../../servlet/AdminBatteryLogin?hidWhatToDo=batteryadminhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
							</tr>
							<tr><td height="10"></td></tr>
							<tr><td align="center" class="insidecontentbat"><div id="displayaddlaptopbatteryerrormsg"></div></td></tr>
							<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">					
								<tr>
									<td width="39%" class="insidecontent" align="right">Laptop&nbsp;Battery&nbsp;Brand<font color="red">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="batterybrand" size="20" maxlength="50"><span class="hint">Provide Battery Brand in the format 'Acer'.<span class="hint-pointer">&nbsp;</span></span></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Laptop&nbsp;Battery&nbsp;Model<font color="red">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="batterymodel" size="20"  maxlength="50" onchange="checkModel();"><span class="hint">Provide Battery Model in the format 'Acer AO'.<span class="hint-pointer">&nbsp;</span></span><div id="chkmodel"></div></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Warranty</td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="batterywarrnty" size="20" maxlength="50"><span class="hint">Provide Warranty in the format '24 Months'.<span class="hint-pointer">&nbsp;</span></span></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Voltage<font color="red">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="voltage" size="20" maxlength="10"><span class="hint">Provide voltage in the format '11.1V'.<span class="hint-pointer">&nbsp;</span></span></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Cell Count<font color="red">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="cellcount" size="20" maxlength="10"><span class="hint">Provide cell count in the format '6 Cell'.<span class="hint-pointer">&nbsp;</span></span></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Watt&nbsp;hr<font color="red">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="watthr" size="20" maxlength="10"><span class="hint">Provide watt hour in the format '49 Wh'.<span class="hint-pointer">&nbsp;</span></span></td>
								</tr>
								
								<tr>
									<td width="39%" class="insidecontent" align="right">Battery&nbsp;Actual&nbsp;Price<font color="red">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="batteryactualprice" size="20" maxlength="10"><span class="hint">Provide Price in the format '1000'.<span class="hint-pointer">&nbsp;</span></span></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Battery&nbsp;Part&nbsp;Number<font color="red">*</font></td><td width="2%" align="center">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="batpartnum" size="20" maxlength="60"><span class="hint">Provide Battery Part Number in the format 'AS10B75, BT.00607.124'.<span class="hint-pointer">&nbsp;</span></span></td>
								</tr>
								<tr>
								
								<tr>
									<td width="39%" class="insidecontent" align="right" valign="top">Description<font color="red">*</font></td><td width="2%" align="center" valign="top">:</td>
									<td width="59%" class="insidecontent" align="left"><textarea name="batterydesc" cols="20" rows="4"></textarea></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right" valign="top">Select&nbsp;an&nbsp;image&nbsp;file<font color="red">*</font></td><td width="2%" align="center" valign="top">:</td>
									<td width="59%" class="insidecontent" align="left"><input type="file" name="batteryattachment" size="20"/></td>
									</tr>
									<tr>
									<td width="39%"></td><td width="2%"></td><td width="59%" align="left"  class="insidecontent" ><b>* Image&nbsp;formats:</b>&nbsp;gif,bmp,jpg,jpeg,jpe,png,wbmp</td>
									</tr>
									<tr>
									<td width="39%"></td><td width="2%"></td><td width="59%" align="left" class="insidecontent" >* Please check your image file name should not consist of spaces.</td>
									</tr>
									<tr>
									<td width="39%"></td><td width="2%"></td><td width="59%" align="left" class="insidecontent" >* Image Resolution is 250*250.</td>
									</tr>
								
								<tr>
									<td width="39%" class="insidecontent" align="right" valign="top">Amazon affiliate Link<font color="red">*</font></td><td width="2%" align="center" valign="top">:</td>
									<td width="59%" class="insidecontent" align="left"><textarea name="amazonlink" cols="20" rows="4"></textarea></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right"><input type="button" value="Submit" class="button4" onclick="javascript:addlaptopbatteryDetails();"></td><td width="1%"></td>
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
document.getElementById("displayaddlaptopbatteryerrormsg").innerHTML=loginmessg;
</script>
<%
	session.removeAttribute("sesbatErrorMsg");
}
%>
</body>
</html>