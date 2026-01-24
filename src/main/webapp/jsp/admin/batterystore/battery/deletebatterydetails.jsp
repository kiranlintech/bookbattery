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
	$('#divdeletebattery').hide();

	var strbatbname=document.deletebattery.batbrand.value;
		
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
				if (xmlHttp.statusText != "OK")
				{
					alert("Exception Occured, Please try again later");
					return;
				}
				if (varModels.indexOf("Battery models are not available under the selected BatteryBrand") >= 0)
				{
					$('#divdeletebattery').hide();
					for (i=document.deletebattery.model.options.length-1; i >= 1; i--)
					{
						document.deletebattery.model.remove(i);
					}
					return;
				}
				if (varModels != "")
				{
					varEMPFlag = true;
					varModelsArry = varModels.split("|");
					var len = varModelsArry.length;
					for (i= document.deletebattery.model.options.length- 1; i >= 1; i--)
					{
						document.deletebattery.model.remove(i);
					}
					for (var i = 0; i < len; i++)
					{
						varOptArray = varModelsArry[i].split(":");
						var optn = document.createElement("OPTION");
						optn.value = +varOptArray[0]+","+varOptArray[1];
						optn.text = varOptArray[1];
						document.deletebattery.model.options.add(optn);
						document.deletebattery.model.focus();
					}
						
				}
			}
		}
		xmlHttp.open("GET",url, true);
		xmlHttp.send(null);
	}
function getdeletebatterydetails()
{
	var brand = document.deletebattery.batbrand.value;
	var model = document.deletebattery.model.value;
	
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
					alert("Error Occured. Please try again.");
					return;
				}
				else
				{
					resp = xmlhttp.responseText;
					//alert(resp);
					if(resp=="ERROR")
					{
						alert("Error occurred.Please try again.");
						return;
					}
					$('#divdeletebattery').show();
					document.getElementById("divdeletebattery").innerHTML = resp;
					document.getElementById("divdeletebattery").innerHTML = xmlhttp.responseText;
				}
			}			
		}
		xmlhttp.open("POST","../../../../servlet/AddBatteryDetails?hidWhatToDo=gebatterydetailstodelete&brand="+brand+"&model="+model,true);		
		xmlhttp.send();	
}
function deletebatterydetails(batid,strbatbrand,strbattmodel)
{
		var xmlhttp= "";
		var resp= "";
		var url ="../../../../servlet/AddBatteryDetails?hidWhatToDo=deletebatterydetails&chkSi="+batid+"&batterybrand="+strbatbrand+"&batterymodel="+strbattmodel;
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
					resp = xmlhttp.responseText;
					$('#displaybatdetailsrrormsg').show();
					document.getElementById("displaybatdetailsrrormsg").innerHTML = resp;
					document.getElementById("displaybatdetailsrrormsg").innerHTML = xmlhttp.responseText;
					getdeletebatterydetails()
					
					for (i=document.deletebattery.model.options.length-1; i >= 1; i--)
					{
						document.deletebattery.model.remove(i);
					}
					getmodels()
				}
			}			
		}
		var agree=confirm("Are You sure want to delete Battery details! ");
		if (agree)
		{
		xmlhttp.open("GET",url, true);		
		xmlhttp.send();	

		}

}
function onchangemodel()
{
	$('#displaybatdetailsrrormsg').hide();
}
function FunReset()
{
	location.href="../../../../jsp/admin/batterystore/battery/deletebatterydetails.jsp"
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
<body onload="document.deletebattery.batbrand.focus(); getBatteryBrands(); noBack();" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="deletebattery" action="request_for_quote.asp"  method="post">
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
								<td class="subheading">Delete&nbsp;Battery&nbsp;details</td>
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
										<select name="model" class="insidecontent" onChange="javascript:getdeletebatterydetails();onchangemodel();" STYLE="width: 180px">
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
												<td width="40%"><div id="divdeletebattery"></div></td>
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
String strLogmapMsg=(String)session.getAttribute("sesldeletebatterydetailsErrorMsg");
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
	session.removeAttribute("sesldeletebatterydetailsErrorMsg");
}
%>
</body>
</html>