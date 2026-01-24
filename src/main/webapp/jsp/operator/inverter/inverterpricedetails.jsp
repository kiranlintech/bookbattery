<%--
    Document   : inverter price details
    Created on : April 7, 2014, 10:22:12 AM
    Author     : Sai Krishna Daddala
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.LogLevel"%>
<%
String strUserid=(String)session.getAttribute("sesBatteryOperatorName");
if(strUserid==null)
{
	strUserid="";
	session.setAttribute("sesErrorMsg","Session Timed Out. Please login again");
	response.sendRedirect("/bookbattery/operator/index.html");
	return;
}
Vector BatBrandVector=(Vector)session.getAttribute("BatBrandVector");
LogLevel.DEBUG(1,new Throwable(),"BatBrandVector: "+BatBrandVector);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="/bookbattery/images/favicon.png" type="image/x-icon">
<title>BookBattery.com-Online Battery Store</title>
<script language="JavaScript" src="/bookbattery/js/jquery-1.8.2.js" ></script>
<script type="text/javascript">
$(document).ready(function(){
		$category12 = $('#brand');
		 $category12.change(
            function() {
			 var splitvalb =$category12.val();
			 if(splitvalb == "default" || splitvalb==0)    
			 {
				for(i=document.getinverterprice.capacity.options.length-1;i>=1;i--)
				{
				document.getinverterprice.capacity.remove(i);
				}
				for(i=document.getinverterprice.state.options.length-1;i>=1;i--)
				{
				document.getinverterprice.state.remove(i);
				}
				for(i=document.getinverterprice.city.options.length-1;i>=1;i--)
				{
				document.getinverterprice.city.remove(i);
				}
				document.getinverterprice.brand.focus();
				$('#divinverterpricedetails').hide();
			 }
			 else
			 {
				for(i=document.getinverterprice.state.options.length-1;i>=1;i--)
				{
				document.getinverterprice.state.remove(i);
				}
				 for(i=document.getinverterprice.city.options.length-1;i>=1;i--)
				{
				document.getinverterprice.city.remove(i);
				}
				$('#divinverterpricedetails').hide();
                $.ajax({
                    type: "GET",
                     url: "/bookbattery/servlet/OperatorBatteryPriceDetails?hidWhatToDo=getinvertercapacity",
                    data: {brand: $category12.val() },
                    success: function(data){
					$("#capacity").html(data)
					document.getinverterprice.capacity.focus();
                    }
                });
			 }
            }
        );
	});
$(document).ready(function(){
		$category1 = $('#capacity');
		 $category1.change(
            function() {
			 var splitvalb =$category1.val();
			 if(splitvalb == "default" || splitvalb==0)    
			 {
				for(i=document.getinverterprice.state.options.length-1;i>=1;i--)
				{
				document.getinverterprice.state.remove(i);
				}
				for(i=document.getinverterprice.city.options.length-1;i>=1;i--)
				{
				document.getinverterprice.city.remove(i);
				}
				document.getinverterprice.capacity.focus();
				$('#divinverterpricedetails').hide();
			 }
			 else
			 {
				 for(i=document.getinverterprice.city.options.length-1;i>=1;i--)
				{
				document.getinverterprice.city.remove(i);
				}
				$('#divinverterpricedetails').hide();
                $.ajax({
                    type: "GET",
                     url: "/bookbattery/servlet/OperatorBatteryPriceDetails?hidWhatToDo=getinverterstates",
                    data: {capacity: $category1.val() },
                    success: function(data){
					$("#state").html(data)
					document.getinverterprice.state.focus();
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
				for(i=document.getinverterprice.city.options.length-1;i>=1;i--)
				{
				document.getinverterprice.city.remove(i);
				}
				document.getinverterprice.state.focus();
				$('#divinverterpricedetails').hide();
			 }
			 else
			 {
				 $('#divinverterpricedetails').hide();
                $.ajax({
                    type: "GET",
                     url: "/bookbattery/servlet/OperatorBatteryPriceDetails?hidWhatToDo=getinvertercities",
                    data: {state: $category3.val() },
                    success: function(data){
                        $("#city").html(data)
						document.getinverterprice.city.focus();
                    }
                });
			 }
            }
        );
	});
function getinverterprices()
{
	var brand = document.getinverterprice.brand.value; 
	var capacity = document.getinverterprice.capacity.value; 
	var state = document.getinverterprice.state.value; 
	var city = document.getinverterprice.city.value; 

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
					$('#divinverterpricedetails').show();
					document.getElementById("divinverterpricedetails").innerHTML = resp;
					document.getElementById("divinverterpricedetails").innerHTML = xmlhttp.responseText;
				}
			}			
		}
		xmlhttp.open("POST","/bookbattery/servlet/OperatorBatteryPriceDetails?hidWhatToDo=getinverterprices&brand="+brand+"&capacity="+capacity+"&state="+state+"&city="+city,true);		
		xmlhttp.send();	
		}
</script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	/*background-image: url(/bookbattery/images/index_01_01.gif);
	background-repeat: repeat-x;*/
}
-->
</style>
<link href="/bookbattery/css/bookbattery.css" rel="stylesheet" type="text/css" />
</head>
<body onload="document.getinverterprice.brand.focus(); getBatteryBrands(); noBack();" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="getinverterprice" action="request_for_quote.asp"  method="post">
<!-- Battery Header Starts -->
<tr>
	<jsp:include page = "../header.jsp" />
</tr>
<!-- Battery Header Ends -->
<!--<tr>
	<td>
		<img src="/bookbattery/images/flag1234.JPG" width="880" height="15">
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
					<jsp:include page="../operatorleftmenu.jsp"/>
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
								<td class="subheading">Find&nbsp;Inverter&nbsp;Price</td>
								<td  align="right"><a href="/bookbattery/servlet/OperatorLogin?hidWhatToDo=batteryoperatorhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
							</tr>
							<tr><td height="10"></td></tr>
							<tr><td align="center" class="insidecontentbat"><div id="displaybatpricerrormsg"></div></td></tr>
							<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
								<tr>
									<td width="39%" class="insidecontent" align="right">Select&nbsp;Brand<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">
									<select name="brand"  id="brand" class="insidecontent" STYLE="width: 180px" >
										<option value="0" >&lt;- Select Brand -&gt;</option>
										<%
										try
										   {
											if(BatBrandVector!=null && BatBrandVector.size()>0)
											{
											for(int i=0;i<BatBrandVector.size();i++)
											{
												Hashtable ht=(Hashtable)BatBrandVector.get(i);
												String inverterbrand=(String)ht.get("inverter_brand");
										%>
										<option value="<%=inverterbrand%>"><%=inverterbrand%></option>
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
									<td width="39%" class="insidecontent" align="right">Select Model<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">
										<select name="capacity" id="capacity" class="insidecontent" STYLE="width: 180px">
											<option value="0" >&lt;-&nbsp;&nbsp; Select Capacity -&gt;</option>
										</select>
									</td>
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
										<select name="city" id="city" Onchange="javascript:getinverterprices();" class="insidecontent" STYLE="width: 180px">
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
												<td width="40%"><div id="divinverterpricedetails"></div></td>
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
String strLogmapMsg=(String)session.getAttribute("sesleditbatterypriceErrorMsg");
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
document.getElementById("displaybatpricerrormsg").innerHTML=loginmpamessg;
</script>
<%
	session.removeAttribute("sesleditbatterypriceErrorMsg");
}
%>
</body>
</html>