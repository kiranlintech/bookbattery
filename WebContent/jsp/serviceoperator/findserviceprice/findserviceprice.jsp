<%--
    Document   : findserviceprice.jsp
    Created on : Feb 8th, 2016
    Author     : Lavanya Chowdary.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.LogLevel"%>
<%
String strUserid=(String)session.getAttribute("sesServicesOperatorName");
if(strUserid==null)
{
	strUserid="";
	session.setAttribute("sesErrorMsg","Session Timed Out. Please login again");
	response.sendRedirect("/services/operator/index.html");
	return;
}
Vector ServiceVector=(Vector)session.getAttribute("ServicesTypeVector");
LogLevel.DEBUG(1,new Throwable(),"ServiceVector: "+ServiceVector);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="/services/images/favicon.png" type="image/x-icon">
<title>BookBattery.com-India's No.1 Automobile Services Store</title>
<script language="JavaScript" src="/services/js/jquery-1.8.2.js" ></script>
<script type="text/javascript">

$(document).ready(function(){
		$category1 = $('#servicetype');
		 $category1.change(
            function() {
			 var splitvalb =$category1.val();
			 if(splitvalb == "default" || splitvalb==0)    
			 {
				for(i=document.getserviceprice.state.options.length-1;i>=1;i--)
				{
				document.getserviceprice.state.remove(i);
				}
				for(i=document.getserviceprice.city.options.length-1;i>=1;i--)
				{
				document.getserviceprice.city.remove(i);
				}
				document.getserviceprice.servicepackage.focus();
				$('#diveditprice').hide();
			 }
			 else
			 {
				 for(i=document.getserviceprice.city.options.length-1;i>=1;i--)
				{
				document.getserviceprice.city.remove(i);
				}
				$('#diveditprice').hide();
                $.ajax({
                    type: "GET",
                     url: "/services/servlet/OperatorServicePriceDetails?hidWhatToDo=getstates",
                    data: {servicetype: $category1.val()},
                    success: function(data){
					$("#state").html(data)
					document.getserviceprice.state.focus();
                    }
                });
			 }
            }
        );
	});
$(document).ready(function(){
		$category3 = $('#state');
		 $category3.change(
            function() {
			 var splitvalc =$category3.val();
			 if(splitvalc == "default" || splitvalc == 0)
			 {
				for(i=document.getserviceprice.city.options.length-1;i>=1;i--)
				{
				document.getserviceprice.city.remove(i);
				}
				document.getserviceprice.state.focus();
				$('#diveditprice').hide();
			 }
			 else
			 {
				 $('#diveditprice').hide();
                $.ajax({
                    type: "GET",
                     url: "/services/servlet/OperatorServicePriceDetails?hidWhatToDo=getcities",
                    data: {state: $category3.val() },
                    success: function(data){
                        $("#city").html(data)
						document.getserviceprice.city.focus();
                    }
                });
			 }
            }
        );
	});
function getserviceprices()
{
	var servicetype = document.getserviceprice.servicetype.value;
	var state = document.getserviceprice.state.value; 
	var city = document.getserviceprice.city.value; 

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
					if(resp=="ERROR")
					{
						alert("Error occurred.Please try again.");
						return;
					}
					$('#diveditprice').show();
					document.getElementById("diveditprice").innerHTML = resp;
					document.getElementById("diveditprice").innerHTML = xmlhttp.responseText;
				}
			}			
		}
		xmlhttp.open("POST","/services/servlet/OperatorServicePriceDetails?hidWhatToDo=getserviceprices&servicetype="+servicetype+"&state="+state+"&city="+city,true);		
		xmlhttp.send();	
		}
</script>
<style type="text/css">
</style>
<link href="/services/css/sgservices.css" rel="stylesheet" type="text/css" />
</head>
<body onload="document.getserviceprice.servicetype.focus();  noBack();" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="getserviceprice" action="request_for_quote.asp"  method="post">
<!-- Service Header Starts -->
<tr>
	<jsp:include page = "../header.jsp" />
</tr>
<!-- Service Header Ends -->
<tr>
	<td>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#ffffff">
		<tr>
			<td width="25%" align="left" valign="top" bgcolor="#ffffff">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<!-- Starts services admin left Menu -->
				<tr>
					<jsp:include page="../operatorleftmenu.jsp"/>
				</tr>
				<!-- Ends services admin left Menu -->
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
								<td class="subheading">Find&nbsp;Service&nbsp;Price</td>
								<td  align="right"><a href="/services/servlet/OperatorLogin?hidWhatToDo=servicesoperatorhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
							</tr>
							<tr><td height="10"></td></tr>
							<tr><td align="center" class="insidecontentservices"><div id="displayservicespricerrormsg"></div></td></tr>
							<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
								<tr>
									<td width="39%" class="insidecontent" align="right">Select&nbsp;Service&nbsp;Type<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">
									<select name="servicetype"  id="servicetype" class="insidecontent" STYLE="width: 180px" >
										<option value="0" >&lt;-&nbsp;&nbsp; Select Service Type &nbsp;&nbsp; --&gt;</option>
										<%
										try
										   {
											if(ServiceVector!=null && ServiceVector.size()>0)
											{
											for(int i=0;i<ServiceVector.size();i++)
											{
												Hashtable ht=(Hashtable)ServiceVector.get(i);
												String services_type=(String)ht.get("services_type");
										%>
										<option value="<%=services_type%>"><%=services_type%></option>
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
									<td width="39%" class="insidecontent" align="right">Select State<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">
										<select name="state" id="state" class="insidecontent" STYLE="width: 180px">
											<option value="0" >&lt;-&nbsp;&nbsp; Select State -&gt;</option>
										</select>
									</td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Select City<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">
										<select name="city" id="city" Onchange="javascript:getserviceprices();" class="insidecontent" STYLE="width: 180px">
											<option value="0" >&lt;-&nbsp;&nbsp; Select City -&gt;</option>
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
												<td width="40%"><div id="diveditprice"></div></td>
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
String strLogmapMsg=(String)session.getAttribute("sesleditservicespriceErrorMsg");
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
document.getElementById("displayservicespricerrormsg").innerHTML=loginmpamessg;
</script>
<%
	session.removeAttribute("sesleditservicespriceErrorMsg");
}
%>
</body>
</html>