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
	response.sendRedirect("../../../../admin/index.html");
	return;
}

Vector PincodeVector=(Vector)session.getAttribute("sespincodevector");
LogLevel.DEBUG(1,new Throwable(),"PincodeVector: "+PincodeVector);
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
	
		$category1 = $('#pincode');
        $category1.change(
            function() 
			{
				var splitvala =$category1.val();
				if(splitvala == 0)
				{
					for(i=document.deletretpincodemap.retailers.options.length-1;i>=1;i--)
					{
					document.deletretpincodemap.retailers.remove(i);
					}
					document.deletretpincodemap.pincode.focus();
					$('#divdeletretpincodemap').hide();
				}
				else
				{
					$('#divdeletretpincodemap').hide();
					document.getElementById("displayappdeletretpincodemaperrormsg").innerHTML ="";
						$.ajax({
							type: "GET",
							 url: "../../../../servlet/RetailerLocationMap?hidWhatToDo=getretailerstodeletepincode",
							data: {pincode: $category1.val() },
							success: function(data){
								$("#retailers").html(data)
								document.deletretpincodemap.retailers.focus();
							}
						});
				}
            }
        );
	});
function getpincoretdetodelete()
{
	$('#divdeletretpincodemap').show();
	var pincode = document.deletretpincodemap.pincode.value;
	var retailers = document.deletretpincodemap.retailers.value;
	//var keyword = "pincode";

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
					document.getElementById("divdeletretpincodemap").innerHTML = resp;
					document.getElementById("divdeletretpincodemap").innerHTML = xmlhttp.responseText;
				}
			}			
		}
		xmlhttp.open("POST","../../../../servlet/RetailerLocationMap?hidWhatToDo=gebatretlocationtodelete&pincode="+pincode+"&retailers="+retailers,true);		
		xmlhttp.send();	
}
function deleteretlocdetails(retlocid)
{
	var xmlhttp= "";
		var resp= "";
		var url ="../../../../servlet/RetailerLocationMap?hidWhatToDo=deleteretlocationmap&chkSi="+retlocid;
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
					document.getElementById("displayappdeletretpincodemaperrormsg").innerHTML = resp;
					document.getElementById("displayappdeletretpincodemaperrormsg").innerHTML = xmlhttp.responseText;
					getpincoretdetodelete()
				}
			}			
		}
		var agree=confirm("Are You sure want to delete Retailer Pincode Mapping details! ");
		if (agree)
		{
			xmlhttp.open("GET",url, true);		
			xmlhttp.send();	
		}
}
function FunReset()
{
	location.href="../../../../jsp/admin/batterystore/retailerlocmap/deletepincodemap.jsp"
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
<body onload="document.deletretpincodemap.pincode.focus(); noBack();" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="deletretpincodemap">
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
								<td class="subheading">Delete&nbsp;Retailer&nbsp;Pincode&nbsp;Mapping</td>
								<td  align="right"><a href="../../../../jsp/admin/batterystore/retailerlocmap/retlocareaoptions.jsp" class="onclick1">Back&nbsp;&nbsp;</a></td>
							</tr>
							<tr><td height="10"></td></tr>
							<tr><td align="center" class="insidecontentbat"><div id="displayappdeletretpincodemaperrormsg"></div></td></tr>
							<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
								<tr>
									<td width="39%" class="insidecontent" align="right">Select&nbsp;Pincode<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">
									<select name='pincode' id='pincode'  class="insidecontent" style='width:180px'>
										<option value='0'>&lt;-&nbsp;Select&nbsp;Pincode&nbsp;-&gt;</option>
										<%
										try
										   {
											if(PincodeVector!=null && PincodeVector.size()>0)
											{
											for(int i=0;i<PincodeVector.size();i++)
											{
												Hashtable ht=(Hashtable)PincodeVector.get(i);
												String pincode=(String)ht.get("pincode");
										%>
										<option value="<%=pincode%>"><%=pincode%></option>
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
									<td width="39%" class="insidecontent" align="right">Select&nbsp;Retailer<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left"><select name="retailers" id ="retailers" onChange="javascript:getpincoretdetodelete();" class="insidecontent" style='width:180px'>
									<option value="0" >&lt;-&nbsp;Select Retailer&nbsp;-&gt;</option>
									</select></td>
								</tr>
								
								</td>
								</tr>
								</table>
								<tr><td height="10"></td></tr>
								
								<tr>
									<td>
										<table width="100%" border="0" align="center">
											<tr class="#FFFFFF" bgcolor="FFFFFF">
												<td width="40%"><div id="divdeletretpincodemap"></div></td>
											</tr>
										</table>
									</td>
								</tr>
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
document.getElementById("displayappdeletretpincodemaperrormsg").innerHTML=loginmpamessg;
</script>
<%
	session.removeAttribute("sesretlocErrorMsg");
}
%>
</body>
</html>