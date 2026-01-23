<%--
    Document   : batteryadminmain
    Created on : Aug 30, 2013, 4:22:12 PM
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
Vector laptopbrandVector=(Vector)session.getAttribute("sesLaptopbatterybrandsvector");
LogLevel.DEBUG(1,new Throwable(),"laptopbrandVector: "+laptopbrandVector);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../../images/favicon.png" type="image/x-icon">
<title>BookBattery.com - India's No.1 Automobile Battery Store</title>
<script language="JavaScript" src="../../../../js/jquery-1.8.2.js" ></script>
<script type="text/javascript">
$(document).ready(function(){
		$category4 = $('#batbrand');
		 $category4.change(
            function() {
			 var splitvalb =$category4.val();
			 if(splitvalb == "0")
			 {
				for(i=document.editbattery.model.options.length-1;i>=1;i--)
				{
				document.editbattery.model.remove(i);
				}
				document.editbattery.batbrand.focus();
				$('#diveditbattery').hide();
			 }
			 else
			 {
				 $('#diveditbattery').hide();
                $.ajax({
                    type: "GET",
                     url: "../../../../servlet/AddLaptopBatteryDetails?hidWhatToDo=getbatterymodels",
                    data: {brand: $category4.val() },
                    success: function(data){
                        $("#model").html(data)
						document.editbattery.model.focus();
                    }
                });
			 }
            }
        );
	});
	function getbatterydetails()
	{
	var brand = document.editbattery.batbrand.value;
	var model = document.editbattery.model.value;

	var xmlhttp= "";
		var resp= "";
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
					alert("Error Occured. Please try againdd.");
					return;
				}
				else
				{
					resp = xmlhttp.responseText;
					if(resp=="ERROR")
					{
						alert("Error occurred.Please try againpp.");
						return;
					}
					$('#diveditbattery').show();
					document.getElementById("diveditbattery").innerHTML = resp;
					document.getElementById("diveditbattery").innerHTML = xmlhttp.responseText;
				}
			}			
		}
		xmlhttp.open("POST","../../../../servlet/AddLaptopBatteryDetails?hidWhatToDo=gebatterydetailstoupdate&brand="+brand+"&model="+model,true);		
		xmlhttp.send();	
}
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

function funToUpdatbatterydetails(batid,model,brand)
{
	var batbrand= document.editbattery.batbrand.value
	var batwarrnty = escape(document.editbattery.batwar.value); 
	var batcellcount = document.editbattery.batcellcount.value;
	var batvolt = document.editbattery.batvolt.value;
	var batwatshrs = document.editbattery.batwatshrs.value;
	var batactprice = document.editbattery.batactprice.value;
	var batpartnumber = document.editbattery.batpartnumber.value;
	var attachment = document.editbattery.attachment.value;
	var amazonlink = escape(document.editbattery.amazonlink.value);

	var iChars3 = "`~!@#$%^&*()+=-_[]\\\';,/{}|\":<>?";
	var iChars1 = "`~!@#$%^&*()=_[]\\\';,/{}|\":<>?";
	var iChars2 = "`~!@#$%^&*()+=[]';,{}|\":<>?.";
	var regularexp=/^[^\s\-].*[^\s\-]$/;
	var regularexp1=/^(\d.+[a-zA-Z ]|[a-zA-Z ]+\d.)[a-zA-Z\d.]*$/
	var iCharsbatterypart="`~!@#$%^&*()=-_[]\\\';/{}|\":<>?";
	var regexp2 =/^[-a-zA-Z0-9@:%_\+.~#!-?&amp;//=]+$/;
	var regularexpbatterypart=/^([a-zA-Z0-9])$/;

	batwarrnty=batwarrnty.replace(/\+/g, '%2B');
	if(document.editbattery.batbrand.value == 0)
	 {
		errMsg ="<font color='#ff3333'>Please select \'Battery Brand\'.</font>";
		document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
		document.editbattery.batbrand.focus();
		return ;
     }
	 if(document.editbattery.model.value == 0)
	 {
		errMsg ="<font color='#ff3333'>Please select \'Battery Model\'.</font>";
		document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
		document.editbattery.model.focus();
		return ;
     }
	
		 if (document.editbattery.batwar.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the in the \'Warranty\' field.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batwar.focus();
			return ;
         }
		 for (var i = 0; i < document.editbattery.batwar.value.length; i++)
		{
         if (iChars1.indexOf(document.editbattery.batwar.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#ff3333'>Special characters are not allowed in \'Warranty\' field.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batwar.focus();
			return ;
         }
		}
		
		if(batcellcount == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Cellcount\'.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batcellcount.focus();
			return ;
         }
	
		if(batcellcount=="0 cell" || batcellcount=="0 Cell")
		{
			errMsg ="<font color='#ff3333'>Please enter valid \'cellcount\'.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batcellcount.focus();
			return ;
		}
		
		 if (document.editbattery.batcellcount.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the in the \'Cellcount\' field.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batcellcount.focus();
			return ;
         }
			if(batcellcount=="NA")
			{
				
			}
			else
			{
				if (regularexp1.test(batcellcount)==false)
				{
					errMsg ="<font color='#ff3333'>Please enter only Alphanumerics in the \'Cell Count\' field.</font>";
					document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
					document.editbattery.batcellcount.focus();
					return ;
				}
			}
		 /* if (document.editbattery.batcellcount.value.match(regularexp1))
		{
			errMsg ="<font color='#ff3333'>Please enter only Alphanumerics in the \'Cellcount\' field.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batcellcount.focus();
			return ;
		}*/
		 for (var i = 0; i < document.editbattery.batcellcount.value.length; i++)
		{
         if (iChars1.indexOf(document.editbattery.batcellcount.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#ff3333'>Special characters are not allowed in \'Cellcount\' field.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batcellcount.focus();
			return ;
         }
		}
		if (! document.editbattery.batcellcount.value.match(regularexp))
		{
			errMsg ="<font color='#ff3333'>\'Cellcount\' has Unnecessary spaces. Please remove spaces.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batcellcount.focus();
			return ;
		}
		
		 if(isDigits(batcellcount)==true)
		{
			errMsg ="<font color='#ff3333'>Only Numerics are not allowed in the \'Cellcount\' field.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batcellcount.focus();
			return ;
         }
		if(batvolt == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Voltage\'.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batvolt.focus();
			return ;
         }
		
		 if(batvolt == "0 v" || batvolt == "0 V")
		 {
			errMsg ="<font color='#ff3333'>Please enter valid \'voltage\'.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batvolt.focus();
			return ;
         }
		 	if(batvolt=="NA")
			{
				
			}
			else
			{
				  if (regularexp1.test(batvolt)==false)
				{
					errMsg ="<font color='#ff3333'>Please enter only Alphanumerics in the \'Voltage\' field.</font>";
					document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
					document.editbattery.batvolt.focus();
					return ;
				}
			}
	/*if (document.editbattery.batvolt.value.match(regularexp1))
		{
			errMsg ="<font color='#ff3333'>Please enter only Alphanumerics in the \'Voltage\' field.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batvolt.focus();
			return ;
		}	*/
		if(isDigits(batvolt)==true)
		{
			errMsg ="<font color='#ff3333'>Only Numerics are not allowed in the \'Voltage\' field.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batvolt.focus();
			return ;
         }
		 if (document.editbattery.batvolt.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the in the \'Voltage\' field.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batvolt.focus();
			return ;
         }
		 for (var i = 0; i < document.editbattery.batvolt.value.length; i++)
		{
         if (iChars1.indexOf(document.editbattery.batvolt.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#ff3333'>Special characters are not allowed in \'Voltage\' field.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batvolt.focus();
			return ;
         }
		}
		if (! document.editbattery.batvolt.value.match(regularexp))
		{
			errMsg ="<font color='#ff3333'>\'Voltage\' has Unnecessary spaces. Please remove spaces.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batvolt.focus();
			return ;
		}
		 if(isDigits(batvolt)==true)
		{
			errMsg ="<font color='#ff3333'>Only Numerics are not allowed in the \'Voltage\' field.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batvolt.focus();
			return ;
         }
		if(batwatshrs == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Watt hr\'.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batwatshrs.focus();
			return ;
         }
		  if(batwatshrs=="0 Wh" || batwatshrs=="0 wh" || batwatshrs=="0 WH")
		{
			errMsg ="<font color='#ff3333'>Please enter valid \'watthr\'.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batwatshrs.focus();
			return ;
		}
		
		 if (document.editbattery.batwatshrs.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the in the \'Watt hr\' field.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batwatshrs.focus();
			return ;
         }
		 if(isDigits(batwatshrs)==true)
		{
			errMsg ="<font color='#ff3333'>Only Numerics are not allowed in the \'Watt hr\' field.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batwatshrs.focus();
			return ;
         }
			 if(batwatshrs=="NA")
			{
				
			}
			else
			{
			   if (regularexp1.test(batwatshrs)==false)
				{
					errMsg ="<font color='#ff3333'>Please enter only Alphanumerics in the \'Watt Hr\' field.</font>";
					document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
					document.editbattery.batwatshrs.focus();
					return ;
				}
			}
		/*if (document.editbattery.batwatshrs.value.match(regularexp1))
		{
			errMsg ="<font color='#ff3333'>Please enter only Alphanumerics in the \'Watt hr\' field.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batwatshrs.focus();
			return ;
		}	*/
		 for (var i = 0; i < document.editbattery.batwatshrs.value.length; i++)
		{
         if (iChars1.indexOf(document.editbattery.batwatshrs.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#ff3333'>Special characters are not allowed in \'Watt hr\' field.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batwatshrs.focus();
			return ;
         }
		}
		if (! document.editbattery.batwatshrs.value.match(regularexp))
		{
			errMsg ="<font color='#ff3333'>\'Watt hr\' has Unnecessary spaces. Please remove spaces.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batwatshrs.focus();
			return ;
		}
		if(document.editbattery.batactprice.value == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Actual Price\'.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batactprice.focus();
			return ;
         }
		else if(document.editbattery.batactprice.value < 0.1)
		{
			errMsg ="<font color='#ff3333'>Please enter valid price in \'Actual Price\'.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batactprice.focus();
			return ;
		}
		else if (document.editbattery.batactprice.value.length < 2)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 2 numbers in the \'Actual Price\' field.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batactprice.focus();
			return ;
         }
		else if(batactprice < 100)
		{
			errMsg ="<font color='#ff3333'>\'Actual Price\' should not lessthan Rs 100.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batactprice.focus();
			return ;
		}
		else 
		{
		var checkOK = "0123456789";
		var checkStr = document.editbattery.batactprice.value;
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
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batactprice.focus();
			return ;
		}
		}
		if (document.editbattery.batpartnumber.value.indexOf(' ')==0 )
		{
				errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the in the \'Battery Part Number\' field.</font>";
				document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
				document.editbattery.batpartnumber.focus();
				return ;
        }		
		if (document.editbattery.batpartnumber.value.match(regularexpbatterypart))
		{
			errMsg ="<font color='#ff3333'>Please enter only Alphanumerics in the \'Battery Part Number\' field.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batpartnumber.focus();
			return ;
		}
		if(batpartnumber == 0)
		{
			errMsg ="<font color='#ff3333'>Please enter valid \'Battery Part Number\'.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batpartnumber.focus();
			return ;
         }
		
		 if(amazonlink == "")
		 {
			errMsg ="<font color='#ff3333'>Please enter \'amazonlink\'.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.amazonlink.focus();
			return ;
         }
			 if (! document.editbattery.amazonlink.value.match(regexp2))
			{
				errMsg ="<font color='#ff3333'>Please enter \'valid Link\'.</font>";
				document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
				document.editbattery.amazonlink.focus();
				return ;
			}
			if (/[a-z][A-Z]{2}/i.test(document.editbattery.amazonlink.value) != true) 
			{
				errMsg ="<font color='#ff3333'>Please enter \'valid Link\'.</font>";
				document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
				document.editbattery.amazonlink.focus();
				return ;
			}
			if(amazonlink=="http://")
			{	
				errMsg ="<font color='#ff3333'>Please enter \'valid Link\'.</font>";
				document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
				document.editbattery.amazonlink.focus();
				return ;
			}
			if(amazonlink=="https://")
			{	
				errMsg ="<font color='#ff3333'>Please enter \'valid Link\'.</font>";
				document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
				document.editbattery.amazonlink.focus();
				return ;
			}
			if((document.editbattery.elements["amazonlink"].value.indexOf("wwww") ==0) || (document.editbattery.elements["amazonlink"].value.indexOf("ww.") ==0) || (document.editbattery.elements["amazonlink"].value.indexOf("WWWW") ==0) || (document.editbattery.elements["amazonlink"].value.indexOf("WW.") ==0))
			{
				errMsg ="<font color='#ff3333'>Please enter \'valid Link\'.</font>";
				document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
				document.editbattery.amazonlink.focus();
				return ;
			}
			if(isDigits(amazonlink)==true)
			{
				errMsg ="<font color='#ff3333'>Please enter \'valid Link\'.</font>";
				document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
				document.editbattery.amazonlink.focus();
				return ;
			}

		document.editbattery.method="post";
		document.editbattery.action="../../../../servlet/AddLaptopBatteryDetails?hidWhatToDo=updatebatterydetails&batwarrnty="+batwarrnty+"&batcellcount="+batcellcount+"&batvolt="+batvolt+"&batwatshrs="+batwatshrs+"&batactprice="+batactprice+"&batpartnumber="+batpartnumber+"&batid="+batid+"&model="+model+"&brand="+brand+"&attachment="+attachment+"&amazonlink="+amazonlink;
		//alert(document.editbattery.action);  
		document.editbattery.submit();
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
<body onload="document.editbattery.batbrand.focus(); getBatteryBrands(); noBack();" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="editbattery" method="post" ENCTYPE="multipart/form-data">
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
								<td class="subheading">Edit&nbsp;Laptop&nbsp;Battery&nbsp;details</td>
								<td  align="right"><a href="../../../../servlet/AdminBatteryLogin?hidWhatToDo=batteryadminhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
							</tr>
							<tr><td height="10"></td></tr>
							<tr><td align="center" class="insidecontentbat"><div id="displaybatdetailsrrormsg"></div></td></tr>
							<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
								<tr>
									<td width="39%" class="insidecontent" align="right">Select&nbsp;Laptop&nbsp;Battery&nbsp;Brand<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">
									<select name="batbrand" id="batbrand" class="insidecontent" STYLE="width: 180px" >
										<option value="0" >&lt;-&nbsp;&nbsp; Select Brand &nbsp;&nbsp; --&gt;</option>
										<%
										try
										   {
											if(laptopbrandVector!=null && laptopbrandVector.size()>0)
											{
											for(int i=0;i<laptopbrandVector.size();i++)
											{
												Hashtable ht=(Hashtable)laptopbrandVector.get(i);
												String bname=(String)ht.get("battery_brand");
										%>
										<option value="<%=bname%>"><%=bname%></option>
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
									<td width="39%" class="insidecontent" align="right">Select&nbsp;Battery&nbsp;Model<font color="FF0000">*</font>&nbsp;:</label></td>
									<td width="59%" class="insidecontent" align="left">
										<select name="model" id="model" onChange="javascript:getbatterydetails();" class="insidecontent" STYLE="width: 180px">
											<option value="0" >&lt;-&nbsp;&nbsp; Select Model -&gt;</option>
										</select></td>
								</tr>
								</td>
								</tr>
								</td>
								</tr>
								</table>
								<tr>
									<td>
										<table width="100%" border="0" align="center">
											<tr class="#FFFFFF" bgcolor="FFFFFF">
												<td width="40%"><div id="diveditbattery"></div></td>
											</tr>
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
String strLogmapMsg=(String)session.getAttribute("sesleditbatterydetailsErrorMsg");
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
	document.getElementById("displaybatdetailsrrormsg").innerHTML=loginmpamessg;
	</script>
	<%
	session.removeAttribute("sesleditbatterydetailsErrorMsg");
}
%>
</body>
</html>