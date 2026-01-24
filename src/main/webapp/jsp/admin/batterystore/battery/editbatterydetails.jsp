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
Vector brandVector=(Vector)session.getAttribute("sesbatterybrandsvector");
LogLevel.DEBUG(1,new Throwable(),"brandVector: "+brandVector);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../../images/favicon.png" type="image/x-icon">
<title>BookBattery.com - India's No.1 Automobile Battery Store</title>
<script language="JavaScript" src="../../../../js/jquery-1.8.2.js" ></script>
<script type="text/javascript">
function getmodels()
{
	$('#diveditbattery').hide();
	var strbatbname=document.editbattery.batbrand.value;
	document.getElementById("displaybatdetailsrrormsg").innerHTML="";
	var xmlHttp;
		var url="../../../../servlet/AddBatteryDetails?hidWhatToDo=getbatterymodels&batbrand="+strbatbname;
		try
		{ 
			xmlHttp = new XMLHttpRequest();
		}
		catch (e)
		{
			try
			{
				xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
			}
			catch (e)
			{
				try
				{
					xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
				}
				catch (e)
				{
					alert("Your	browser	does not support AJAX!");
					return false;
				}
			}
		}
		xmlHttp.onreadystatechange = function()
		{
			if (xmlHttp.readyState == 4)
			{
				varModels = xmlHttp.responseText;
				if (varModels.indexOf("Evaluating Session") >= 0)
				{
					alert("Session Timed out, Please login again");
					return;
				}
				if (xmlHttp.status != 200)
				{
					alert("Exception Occured, Please try again later");
					return;
				}
				if (varModels.indexOf("Battery models are not available under the selected BatteryBrand") >= 0)
				{
					$('#diveditbattery').hide();
					for (i=document.editbattery.model.options.length-1; i >= 1; i--)
					{
						document.editbattery.model.remove(i);
					}
					return;
				}
				if (varModels != "")
				{
					varEMPFlag = true;
					varModelsArry = varModels.split("|");
					var len = varModelsArry.length;
					for (i= document.editbattery.model.options.length- 1; i >= 1; i--)
					{
						document.editbattery.model.remove(i);
					}
					for (var i = 0; i < len; i++)
					{
						varOptArray = varModelsArry[i].split(":");
						var optn = document.createElement("OPTION");
						optn.value = +varOptArray[0]+","+varOptArray[1];
						optn.text = varOptArray[1];
						document.editbattery.model.options.add(optn);
						document.editbattery.model.focus();
					}
						
				}
			}
		}
		xmlHttp.open("GET",url, true);
		xmlHttp.send(null);
	}
	function getbatterydetails()
	{
	var brand = document.editbattery.batbrand.value;
	var model = document.editbattery.model.value;

	if(model == 0)
	{
		model = "0,default";
	}
	else
	{
		model=model;
	}
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
		xmlhttp.open("POST","../../../../servlet/AddBatteryDetails?hidWhatToDo=gebatterydetailstoupdate&brand="+brand+"&model="+model,true);		
		xmlhttp.send();	
	}
function funToUpdatbatterydetails(batid,model,brand)
{
	var batwarrnty = escape(document.editbattery.batwar.value); 
	var batcapcity = document.editbattery.batcap.value;
	var attachment = document.editbattery.attachment.value;

	var iChars3 = "`~!@#$%^&*()+=-_[]\\\';,/{}|\":<>?";
	var iChars1 = "`~!@#$%^&*()=_[]\\\';,/{}|\":<>?";
	var iChars2 = "`~!@#$%^&*()+=[]';,{}|\":<>?.";
	var regularexp=/^[^\s\-].*[^\s\-]$/;
	var regularexp1=/^([a-zA-Z0-9]+)$/;
	var regularexpwaranty=/^(\d.+[a-zA-Z ]|[a-zA-Z ]+\d.)[a-zA-Z\d.]*$/
	batwarrnty=batwarrnty.replace(/\+/g, '%2B');
	
	if(batwarrnty == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Warranty\'.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batwar.focus();
			return ;
         }
		  if (document.editbattery.batwar.value.length < 3)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 3 characters in the \'Warranty\' field.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batwar.focus();
			return ;
         }
		 if (document.editbattery.batwar.value.length > 49)
         {
			errMsg ="<font color='#ff3333'>Please enter 49 characters in the \'Warranty\' field.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batwar.focus();
			return ;
         }
		 if (document.editbattery.batwar.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the in the \'Warranty\' field.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batwar.focus();
			return ;
         }
		 if (regularexpwaranty.test(batwarrnty)==false)
		{
			errMsg ="<font color='#ff3333'>Please enter only Alphanumerics in the \'Warranty\' field.</font>";
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
		if (! document.editbattery.batwar.value.match(regularexp))
		{
			errMsg ="<font color='#ff3333'>\'Warranty\' has Unnecessary spaces. Please remove spaces.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batwar.focus();
			return ;
		}
		 if(batcapcity == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Capacity\'.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batcap.focus();
			return ;
         }
		  if(batcapcity == 0 || batcapcity == "0 Ah" || batcapcity == "0 ah" || batcapcity == "0 AH" || batcapcity == "0 aH")
		  {
			errMsg ="<font color='#ff3333'>Please enter valid \'Capacity\'.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batcap.focus();
			return ;
         }
		 if (document.editbattery.batcap.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the \'Capacity\' field.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batcap.focus();
			return ;
         }
		for (var i = 0; i < document.editbattery.batcap.value.length; i++)
		{
         if (iChars3.indexOf(document.editbattery.batcap.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#ff3333'>Special characters are not allowed in \'Capacity\' field.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batcapcity.focus();
			return ;
         }
		}
		 if (document.editbattery.batcap.value.match(regularexp1))
		{
			errMsg ="<font color='#ff3333'>Please enter only Alphanumerics in the \'Capacity\' field.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batcap.focus();
			return ;
		}
		if (! document.editbattery.batcap.value.match(regularexp))
		{
			errMsg ="<font color='#ff3333'>\'Capacity\' has Unnecessary spaces. Please remove spaces.</font>";
			document.getElementById("displaybatdetailsrrormsg").innerHTML=errMsg;
			document.editbattery.batcap.focus();
			return ;
		}

		document.editbattery.method="post";
		document.editbattery.action="../../../../servlet/AddBatteryDetails?hidWhatToDo=updatebatterydetails&batwarrnty="+batwarrnty+"&batcapcity="+batcapcity+"&batid="+batid+"&model="+model+"&attachment="+attachment+"&brand="+brand;
		//alert(document.editbattery.action);  
		document.editbattery.submit();
}
function FunReset()
{
	location.href="../../../../jsp/admin/batterystore/battery/editbatterydetails.jsp"
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
								<td class="subheading">Edit&nbsp;Battery&nbsp;details</td>
								<td  align="right"><a href="../../../../servlet/AdminBatteryLogin?hidWhatToDo=batteryadminhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
							</tr>
							<tr><td height="10"></td></tr>
							<tr><td align="center" class="insidecontentbat"><div id="displaybatdetailsrrormsg"></div></td></tr>
							<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
								<tr>
									<td width="39%" class="insidecontent" align="right">Select&nbsp;Battery&nbsp;Brand<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">
									<select name="batbrand" onChange="javascript:getmodels();" class="insidecontent" STYLE="width: 180px" >
										<option value="0" >&lt;-&nbsp;&nbsp; Select Brand &nbsp;&nbsp; --&gt;</option>
										<%
										try
										   {
											if(brandVector!=null && brandVector.size()>0)
											{
											for(int i=0;i<brandVector.size();i++)
											{
												Hashtable ht=(Hashtable)brandVector.get(i);
												String bname=(String)ht.get("bat_brand");
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
									<td width="39%" class="insidecontent" align="right"><label id="dynamic_title2">Select Model<font color="FF0000">*</font>&nbsp;:</label></td>
									<td width="59%" class="insidecontent" align="left">
										<select name="model" class="insidecontent" onChange="javascript:getbatterydetails();" STYLE="width: 180px">
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