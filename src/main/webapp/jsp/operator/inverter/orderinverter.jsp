<%--
    Document   : order inverter
    Created on : April 2, 2014, 10:22:12 AM
    Author     : Sai Krishna Daddala
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.util.ArrayList,java.util.Hashtable,java.util.Vector"%>
<%
String strUserid=(String)session.getAttribute("sesBatteryOperatorName");
if(strUserid==null)
{
	strUserid="";
	session.setAttribute("sesErrorMsg","Session Timed Out. Please login again");
	response.sendRedirect("../../../operator/index.html");
	return;
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../images/favicon.png" type="image/x-icon">
<title>BookBattery.com-India's No.1 Automobile Battery Store</title>
<script language="JavaScript" src="../../../js/jquery-1.8.2.js" ></script>
<script src="../../../js/jquery-1.3.2.min.js"></script>
<script type="text/javascript">
$(function () {

	var userAgent = navigator.userAgent.toLowerCase(); 
	if ((userAgent.search("android") > -1)) 
	{
		scrollTo(($(document).width() - $(window).width()) / 0, 450);
	}
	else
	{
		scrollTo(($(document).width() - $(window).width()) / 0, 170);
	}
});
function prepareInputsForHints() {
	
	// repeat the same tests as above for selects
	var selects = document.getElementsByTagName("select");
	for (var k=0; k<selects.length; k++){
		if (selects[k].parentNode.getElementsByTagName("span")[0]) {
			selects[k].onfocus = function () {
				this.parentNode.getElementsByTagName("span")[0].style.display = "inline";
			}
			selects[k].onblur = function () {
				this.parentNode.getElementsByTagName("span")[0].style.display = "none";
			}
		}
	}
}
$(document).ready(function()
{
	$('#pincode2').hide();
	$('#pincode3').hide();
	$('#searcharea').hide();
	prepareInputsForHints();
	$category = $('#inverter_type');
        $category.change(
        function() {
		var splitvalb =$category.val();
			document.getElementById("displysesmsg").innerHTML="";
			if(splitvalb=="")
			{
			for(i=document.inverter.inverter_capacity.options.length-1;i>=1;i--)
			{
			document.inverter.inverter_capacity.remove(i);
			}
			for(i=document.inverter.inverter_name.options.length-1;i>=1;i--)
			{
			document.inverter.inverter_name.remove(i);
			}
			for(i=document.inverter.state.options.length-1;i>=1;i--)
			{
			document.inverter.state.remove(i);
			}
			for(i=document.inverter.city.options.length-1;i>=1;i--)
			{
			document.inverter.city.remove(i);
			}
			for(i=document.inverter.area.options.length-1;i>=1;i--)
			{
			document.inverter.area.remove(i);
			}		
			document.inverter.inverter_type.focus();			
			}
			else
			{
            $.ajax({
                    type: "GET",
                    url: "../../../servlet/OperatorInvertersDetails?hidWhatToDo=getinvertercapacity",
                    data: {invertertype: $category.val() },
                    success: function(data)
					{					
						if(data.indexOf("defaultss")>=0)
						{
							$("#inverter_capacity").html(data)
							document.inverter.inverter_type.focus();
						}
						else
						{
							$("#inverter_capacity").html(data)
							document.inverter.inverter_capacity.focus();
						}
					}
                });
			}
			
            }
        );
	});
	$(document).ready(function(){
	$categorycapacity = $('#inverter_capacity');
	
	        $categorycapacity.change(
            function() {
				document.getElementById("displysesmsg").innerHTML="";
									var keyword=document.getElementById("brandkeyword").value;

					var splitvalcity =$categorycapacity.val();
						for(i=document.inverter.inverter_name.options.length-1;i>=1;i--)
						{
						document.inverter.inverter_name.remove(i);
						}
						for(i=document.inverter.state.options.length-1;i>=1;i--)
						{
						document.inverter.state.remove(i);
						}
						for(i=document.inverter.city.options.length-1;i>=1;i--)
						{
						document.inverter.city.remove(i);
						}
						for(i=document.inverter.area.options.length-1;i>=1;i--)
						{
						document.inverter.area.remove(i);
						}
						document.inverter.inverter_capacity.focus();
						if(splitvalcity == "default")
						{
							for(i=document.inverter.inverter_name.options.length-1;i>=1;i--)
							{
							document.inverter.inverter_name.remove(i);
							}
							for(i=document.inverter.state.options.length-1;i>=1;i--)
							{
							document.inverter.state.remove(i);
							}
							for(i=document.inverter.city.options.length-1;i>=1;i--)
							{
							document.inverter.city.remove(i);
							}
							for(i=document.inverter.area.options.length-1;i>=1;i--)
							{
							document.inverter.area.remove(i);
							}
							document.inverter.inverter_capacity.focus();
						}
						else
						{
			
				$.ajax({
                    type: "GET",
                    url: "../../../servlet/OperatorInvertersDetails?hidWhatToDo=getinverterbrand",
                    data: {keyword:keyword},
                    success: function(data){
						if(data.indexOf("defaultss")>=0)
						{
                        $("#inverter_name").html(data)
						document.inverter.inverter_name.focus();
						}
						else
						{
						 $("#inverter_name").html(data)
						document.inverter.inverter_name.focus();
						}
					}
                });
				}
            }
        );
	});
$(document).ready(function()
{
	$category2 = $('#inverter_name');
        $category2.change(
            function() 
			{

			var splitvalue =$category2.val();
				document.getElementById("displysesmsg").innerHTML="";
			
				for(i=document.inverter.state.options.length-1;i>=1;i--)
				{
				document.inverter.state.remove(i);
				}
				for(i=document.inverter.city.options.length-1;i>=1;i--)
				{
				document.inverter.city.remove(i);
				}
				for(i=document.inverter.area.options.length-1;i>=1;i--)
				{
				document.inverter.area.remove(i);
				}	
				document.inverter.inverter_name.focus();
				if(splitvalue == "default")
				{
					
					for(i=document.inverter.state.options.length-1;i>=1;i--)
					{
					document.inverter.state.remove(i);
					}
					for(i=document.inverter.city.options.length-1;i>=1;i--)
					{
					document.inverter.city.remove(i);
					}
					for(i=document.inverter.area.options.length-1;i>=1;i--)
					{
					document.inverter.area.remove(i);
					}
					document.inverter.inverter_name.focus();
				}
				else
				{
			
					$.ajax({
						type: "GET",
						url: "../../../servlet/BatteryHome?hidWhatToDo=getstate",
						data: {},
						//data: {batterytype: $category.val() },
						success: function(data)
						{
							if(data.indexOf("defaultss")>=0)
							{
								$("#state").html(data)
								document.inverter.state.focus();
							}
							else
							{
								$("#state").html(data)
								document.inverter.state.focus();
							}
						}
					});
				}
			}
        );
	});
	

$(document).ready(function(){
	$categorycity = $('#state');
	        $categorycity.change(
            function() {
				document.getElementById("displysesmsg").innerHTML="";
					var splitvalcity =$categorycity.val();
					
						for(i=document.inverter.city.options.length-1;i>=1;i--)
						{
						document.inverter.city.remove(i);
						}
						for(i=document.inverter.area.options.length-1;i>=1;i--)
						{
						document.inverter.area.remove(i);
						}
						document.inverter.state.focus();
						if(splitvalcity == "default")
						{
							for(i=document.inverter.city.options.length-1;i>=1;i--)
							{
							document.inverter.city.remove(i);
							}
							for(i=document.inverter.area.options.length-1;i>=1;i--)
							{
							document.inverter.area.remove(i);
							}
							document.inverter.state.focus();
						}
						else
						{
			
							$.ajax({
							type: "GET",
							url: "../../../servlet/BatteryHome?hidWhatToDo=getcity",
							data: {state: $categorycity.val() },
							success: function(data){
								if(data.indexOf("defaultss")>=0)
								{
								$("#city").html(data)
								document.inverter.city.focus();
								}
								else
								{
								 $("#city").html(data)
								document.inverter.city.focus();
								}
							}
						});
					}
				}
			);
	});
	$(document).ready(function(){
	$categoryarea = $('#city');
	        $categoryarea.change(
            function() {
				document.getElementById("displysesmsg").innerHTML="";
					var splitvalarea =$categoryarea.val();
					
						for(i=document.inverter.area.options.length-1;i>=1;i--)
						{
						document.inverter.area.remove(i);
						}
						document.inverter.city.focus();
						if(splitvalarea == "default")
						{
							for(i=document.inverter.area.options.length-1;i>=1;i--)
							{
							document.inverter.area.remove(i);
							}
							document.inverter.city.focus();
						}
						else
						{
					
				$.ajax({
                    type: "GET",
                    url: "../../../servlet/BatteryHome?hidWhatToDo=getarea",
                    data: {city: $categoryarea.val() },
                    success: function(data){
						if(data.indexOf("defaultss")>=0)
						{
                        $("#area").html(data)
						document.inverter.area.focus();
						}
						else
						{
						 $("#area").html(data)
						document.inverter.area.focus();
						}
                    }
                });
			  }
            }
        );
	});
function getInverterDetails()
{
	var invertertype = document.inverter.inverter_type.value;      
	var invertercapacity = escape(document.inverter.inverter_capacity.value);
	var invertername = document.inverter.inverter_name.value;
	
	var state = document.inverter.state.value;
	var city = document.inverter.city.value;
	var area = document.inverter.area.value;
	var pincode = document.inverter.pincode.value;
	
	if(pincode == "" || pincode == "default")
	{
		city = city;
		area =area;
		state=state;
	}
	else
	{
		city = "";
		area ="";
		state="";
	}
	if(document.inverter.inverter_type.value == 0)
	{
		errMsg ="<font color='#ff3333'>Please Select \'inverter Type\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.inverter.inverter_type.focus();
		return ;
	}
	if(document.inverter.inverter_capacity.value == 0 || invertercapacity=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'Inverter Capacity\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.inverter.inverter_capacity.focus();
		return ;
	}
	if(document.inverter.inverter_name.value == 0 || invertername=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'Inverter Make\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.inverter.inverter_name.focus();
		return ;
	}
	
	if($("#citydiv").is(':visible'))
	{
		if(!pincode=="")
		{
			$('#pincode2').show();
			$('#pincode3').show();
			$('#citydiv').hide();
			$('#areadiv').hide();
			$('#statediv').hide();
			$('#searchpin').hide();
			$('#searcharea').show();
			errMsg ="<font color='#ff3333'>Already entered pincode. Please remove pincode and try or continue with pincode</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.inverter.pincode.focus();
			return ;
		}
		if(document.inverter.state.value == 0 || state=="default")
		{
			errMsg ="<font color='#ff3333'>Please Select \'State\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.inverter.state.focus();
			return ;
		}
		if(document.inverter.city.value == 0 || city=="default")
		{
			errMsg ="<font color='#ff3333'>Please Select \'Location\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.inverter.city.focus();
			return ;
		}
		if(document.inverter.area.value == 0 || area=="default")
		{
			errMsg ="<font color='#ff3333'>Please Select \'Area\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.inverter.area.focus();
			return ;
		}
	}
	else
	{
		if(document.inverter.pincode.value == "" || pincode=="default")
		{
			errMsg ="<font color='#ff3333'>Please Enter \'Pincode\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.inverter.pincode.focus();
			return ;
		}
		var pincoderegex=/^[0-9]{4,6}$/;
		if(!pincode.match(pincoderegex))
		{
			errMsg ="<font color='#ff3333'>Please Enter Valid \'Pincode\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.inverter.pincode.focus();
			return ;
		}
	}
	document.inverter.method="post";
	document.inverter.action="../../../servlet/OperatorInvertersDetails?hidWhatToDo=getinverterdetails&invertertype="+invertertype+"&invertercapacity="+invertercapacity+"&invertername="+invertername+"&state="+state+"&city="+city+"&area="+area+"&pincode="+pincode;
	document.inverter.submit();
}

function onloadcity()
{
	 //window.scrollTo(0, 50); // (X,Y)
	$('#pincode2').hide();
	$('#pincode3').hide();
	$('#searcharea').hide();
}
function onarea()
{
	document.getElementById("displysesmsg").innerHTML="";
	$('#areadiv').show();
	$('#areassdiv').hide();
	document.inverter.area.focus();
}
function changetopincode()
{
	//alert("calling pincode");
	document.getElementById("displysesmsg").innerHTML="";
	document.inverter.pincode.value="";
	$('#citydiv').hide();
	$('#areadiv').hide();
	$('#statediv').hide();
	$('#pincode2').show();
	$('#pincode3').show();
	$('#searchpin').hide();
	$('#searcharea').show();
}
function changetoarea()
{
	document.getElementById("displysesmsg").innerHTML="";
	$('#citydiv').show();
	$('#areadiv').show();
	$('#statediv').show();
	$('#pincode2').hide();
	$('#pincode3').hide();
	$('#searchpin').show();
	$('#searcharea').hide();
	
}
	</script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	/*background-image: url(../../../images/index_01_01.gif);
	background-repeat: repeat-x;*/
}
.applianceshint{
   	display: none;
    position: absolute;
	font-family:Verdana;
    width:440px;
	top:23px;
	margin-left: -160px;
    border: 1px solid #c93;
    padding: 10px 2px;
	z-index:9999;
    /* to fix IE6, I can't just declare a background-color,
    I must do a bg image, too!  So I'm duplicating the pointer.gif
    image, and positioning it so that it doesn't show up
    within the box */
    background: #ffc url("../../../images/yellow_pointerdown.png") no-repeat -10px -10px;
}

/* The pointer image is hadded by using another span */
.applianceshint .applianceshint-pointer {
    position: absolute;
    margin-top: 10px;
    left: 5px;
    width: 20px;
    height: 19px;
	z-index:9999;
    background: url("../../../images/yellow_pointerdown.png") top left no-repeat;
}

.appliancecontent 
{
    color: #000000;
    font-family: Verdana;
    font-size: 11px;
	z-index:9999;
}
-->
</style>
<link href="../../../css/bookbattery.css" rel="stylesheet" type="text/css" />
</head>
<body onload="onloadcity(); document.inverter.inverter_type.focus();" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="inverter" method="post" ENCTYPE="multipart/form-data">
<!-- Battery Header Starts -->
<tr>
	<jsp:include page = "../header.jsp" />
</tr>
<!-- Battery Header Ends -->
<!--<tr>
	<td>
		<img src="../../../images/flag1234.JPG" width="880" height="15">
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
								<td class="subheading">Order&nbsp;Inverter</td>
								<td  align="right"><a href="../../../servlet/OperatorLogin?hidWhatToDo=batteryoperatorhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
							</tr>
							<tr><td height="45"></td></tr>
							<tr><td align="center" class="insidecontentbat"><div id="displysesmsg"></div></td></tr>
							<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
							<tr>
													<td width="25%" align="left">
														<div class="styled-select">
															<select  name='inverter_type' id='inverter_type' class="insidecontent" style='width:155px;  bgcolor:#FFF;' >
																<option style="background:#FFF" value='' class='topindex1'>Select&nbsp;Inverter&nbsp;Type</option>
																<option style="background:#FFF" value='Inverter'>Inverter</option>
																
															</select>
														</div>
													</td>
													<td width="25%" align="left">
														<div class="styled-select" >
															<select name="inverter_capacity" id ="inverter_capacity" class="insidecontent" style='width:155px;'>
																<option style="background:#FFF" value="0" class='topindex1' >Select&nbsp;Inverter&nbsp;Capacity</option>
															</select>
															<span class="applianceshint"><div align="justify"><table width='440'  cellspacing='0' cellpadding='0' ><tr><td><table width='440'  cellspacing='0' cellpadding='0'  style='padding-left:10px;padding-right:10px;'><tr><td width='100%' colspan='3'><table width='100%' border='0' cellspacing='0' cellpadding='2' class='appliancecontent' style='border: 1px solid #FF8C00;border-collapse:collapse;'><tr style='border: 1px solid #FF8C00;'><td align='center' style='border: 1px solid #FF8C00;'><b>Appliances</b></td> <td colspan='2' align='center' style='border: 1px solid #FF8C00;'><b>400 VA</b></td><td colspan='2' align='center' style='border: 1px solid #FF8C00;'><b>650/675 VA</b></td><td colspan='2' align='center' style='border: 1px solid #FF8C00;'><b>825/880 VA</b></td>  <td colspan='2' align='center' style='border: 1px solid #FF8C00;'><b>1400 VA</b></td> </tr><tr style='border: 1px solid #FF8C00;'> <td style='border: 1px solid #FF8C00;'>Computer</td>  <td style='border: 1px solid #FF8C00;'> 0 </td>  <td style='border: 1px solid #FF8C00;'> 0 </td> <td style='border: 1px solid #FF8C00;'> 0 </td><td style='border: 1px solid #FF8C00;'> 1 </td><td style='border: 1px solid #FF8C00;'> 1 </td>  <td style='border: 1px solid #FF8C00;'> 0 </td> <td style='border: 1px solid #FF8C00;'> 1 </td> <td style='border: 1px solid #FF8C00;'> 1 </td> </tr><tr style='border: 1px solid #FF8C00;'> <td style='border: 1px solid #FF8C00;'>Fans</td> <td style='border: 1px solid #FF8C00;'> 2 </td>  <td style='border: 1px solid #FF8C00;'> 1 </td>  <td style='border: 1px solid #FF8C00;'> 2 </td> <td style='border: 1px solid #FF8C00;'>2 </td><td style='border: 1px solid #FF8C00;'> 2 </td> <td style='border: 1px solid #FF8C00;'> 4 </td> <td style='border: 1px solid #FF8C00;'> 5 </td> <td style='border: 1px solid #FF8C00;'> 6 </td></tr><tr style='border: 1px solid #FF8C00;'><td style='border: 1px solid #FF8C00;'>Tube Lights</td> <td style='border: 1px solid #FF8C00;'> 3 </td><td style='border: 1px solid #FF8C00;'> 1 </td><td style='border: 1px solid #FF8C00;'> 4 </td> <td style='border: 1px solid #FF8C00;'> 3 </td><td style='border: 1px solid #FF8C00;'> 2 </td>  <td style='border: 1px solid #FF8C00;'> 4 </td>  <td style='border: 1px solid #FF8C00;'> 6 </td>  <td style='border: 1px solid #FF8C00;'> 5 </td>  </tr>  <tr style='border: 1px solid #FF8C00;'>  <td style='border: 1px solid #FF8C00;'>TV(32- Inches)</td>  <td style='border: 1px solid #FF8C00;'> 0 </td>    <td style='border: 1px solid #FF8C00;'> 1 </td>  <td style='border: 1px solid #FF8C00;'> 1 </td>  <td style='border: 1px solid #FF8C00;'> 0 </td>  <td style='border: 1px solid #FF8C00;'> 1 </td>  <td style='border: 1px solid #FF8C00;'> 1 </td>  <td style='border: 1px solid #FF8C00;'> 1 </td>  <td style='border: 1px solid #FF8C00;'> 1 </td>  </tr></table></td></tr></table></td></tr></table></div></span>
														</div>
													</td>
													<td width="25%" align="left">
														<div class="styled-select">
															<select name="inverter_name" id ="inverter_name" class="insidecontent" style='width:155px'>
																<option style="background:#FFF" value="0" class='topindex1'>Select&nbsp;Inverter&nbsp;Make</option>
															</select>
															
														</div>
													</td>								
													<td width="25%" align="left">														
													</td>		
																		
												</tr>
												<tr><td height="5"></td><td height="5"></td><td height="5"></td><td height="5"></td></tr>
												<tr>
													<td width="25%" align="left">
													<div id="statediv">
														<div class="styled-select">
															<select  name='state' id='state' class="insidecontent" style='width:155px;  bgcolor:#FFF;' >
																<option style="background:#FFF" value='0' class='topindex1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;State</option>																
															</select>
														</div>
														</div>
													</td>
													<td width="25%" align="left">
													<div id="citydiv">
														<div class="styled-select">
															<select name="city" id ="city" class="insidecontent" style='width:155px'>
																<option style="background:#FFF" value="0" class='topindex1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;Location</option>
															</select>
															
														</div>
													</div>
													<div id="pincode2" align="left">
															<font style='font-family:Verdana;font-size:9px;color:#000000;' >Please Enter Your PinCode:</font>
														</div>
													</td>
												
													<td width="25%" align="left">
													<div id="areadiv">
														<div class="styled-select" >
															<select name="area" id ="area" class="insidecontent" style='width:155px;'>
																<option style="background:#FFF" value="0" class='topindex1' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;Area</option>
															</select>
															
														</div>
														</div>
														<div id="pincode3">
															<input class='insidecontent' type='text' name='pincode' id='pincode' placeholder='517001' style='width:149px;height:28px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='40'/>
														</div>
		
													</td>
													<td width="25%" align="center"><a href="javascript:getInverterDetails();"><img src="../../../images/findinverteronline.png" alt="Order for inverter online" border="0" width="155" height="33" /></a></td>	
												</tr>
												<tr id="searchpin"><td></td><td></td><td align="center" ><a href="javascript:changetopincode();" class="footerbat">Order&nbsp;With&nbsp;Pin&nbsp;Code</a></td></tr>
												<tr id="searcharea"><td></td><td></td><td align="left" ><a href="javascript:changetoarea();" class="footerbat">Order&nbsp;With&nbsp;State,City,Area</a></td></tr>

					<tr><td height="25"></td></tr>
			</td>
	</tr>
	</table>
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
<input type="hidden" name="brandkeyword" id='brandkeyword' value="Common">
<input type="hidden" name="backkeyword" id='backkeyword' value="inverter">
</form>
</center>
<%
String strbatLogMsg=(String)session.getAttribute("sesbatterydetailsErrorMsg");
if(strbatLogMsg ==null)
{
	strbatLogMsg="";
}
else
{
%>
<script type="text/javascript">
var sesmessg; 
sesmessg= "<%=strbatLogMsg%>";
document.getElementById("displysesmsg").innerHTML=sesmessg;
</script>
<%
	session.removeAttribute("sesbatterydetailsErrorMsg");
}
%>
</body>
</html>