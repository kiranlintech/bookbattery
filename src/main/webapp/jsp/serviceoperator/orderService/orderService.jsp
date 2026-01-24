<%--
    Document   : Order a Service
	File name  : orderService.jsp
    Created on : Feb 6th, 2016
    Author     : Bharath Beeky.
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
<title>BookBattery.com-India's No.1 Automobile Services Store</title>
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
$(document).ready(function(){
	  document.getElementById("bat_type").focus();
$("#bat_type").change(function(){
  //alert($("#"+this.id+"").val());

  if($("#"+this.id+"").val()=="Car Batteries" || $("#"+this.id+"").val()=="Bike Batteries")
  {
	  $("#car_make_div").show();
	  $("#car_model_div").show();
	  $("#inv_capacity_div").hide();
	  
			document.getElementById("displysesmsg").innerHTML="";
			for(i=document.serviceindex.vehicle_model.options.length-1;i>=1;i--)
			{
			document.serviceindex.vehicle_model.remove(i);
			}
			for(i=document.serviceindex.services_type.options.length-1;i>=1;i--)
			{
			document.serviceindex.services_type.remove(i);
			}
			for(i=document.serviceindex.services_place.options.length-1;i>=1;i--)
			{
			document.serviceindex.services_place.remove(i);
			}
			for(i=document.serviceindex.state.options.length-1;i>=1;i--)
			{
			document.serviceindex.state.remove(i);
			}
			for(i=document.serviceindex.city.options.length-1;i>=1;i--)
			{
			document.serviceindex.city.remove(i);
			}
			for(i=document.serviceindex.area.options.length-1;i>=1;i--)
			{
			document.serviceindex.area.remove(i);
			}
		
				$.ajax({
                    type: "GET",
                    url: "../../../servlet/BatteryHome?hidWhatToDo=getvehiclename",
                    data: {batterytype: $("#"+this.id+"").val()},
                    success: function(data){
						$('#vehicle_name').show();
						$('#vehicle_model').show();
						$('#services_type').show();
						$('#services_place').show();
						$('#searchpin').show();
						$('#findservicebutton').show();
						
							if(data.indexOf("defaultss")>=0)
							{
							 $("#vehicle_name").html(data)
							document.getElementById("vehicle_name").focus();
							}
							else
							{
                        $("#vehicle_name").html(data)
						document.serviceindex.vehicle_name.focus();
							}
					}
                });

  }
  else
  {
	  $("#car_make_div").hide();
	  $("#car_model_div").hide();
	  $("#inv_capacity_div").show();
	  
		document.getElementById("displysesmsg").innerHTML="";
		for(i=document.serviceindex.vehicle_model.options.length-1;i>=1;i--)
		{
		document.serviceindex.vehicle_model.remove(i);
		}
		for(i=document.serviceindex.services_type.options.length-1;i>=1;i--)
		{
		document.serviceindex.services_type.remove(i);
		}
		for(i=document.serviceindex.services_place.options.length-1;i>=1;i--)
		{
		document.serviceindex.services_place.remove(i);
		}
		for(i=document.serviceindex.state.options.length-1;i>=1;i--)
		{
		document.serviceindex.state.remove(i);
		}
		for(i=document.serviceindex.city.options.length-1;i>=1;i--)
		{
		document.serviceindex.city.remove(i);
		}
		for(i=document.serviceindex.area.options.length-1;i>=1;i--)
		{
		document.serviceindex.area.remove(i);
		}
	
			$.ajax({
				type: "GET",
				url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getbatterycapacity",
				data: {batterytype: "Inverter Batteries"},
				success: function(data){
					$('#vehicle_name').hide();
					$('#vehicle_model').show();
					$('#services_type').show();
					$('#services_place').show();
					$('#searchpin').show();
					$('#findservicebutton').show();
					
						if(data.indexOf("defaultss")>=0)
						{
							$("#inv_capacity").html(data)
							document.getElementById("inv_capacity").focus();
						}
						else
						{
							$("#inv_capacity").html(data)
							document.getElementById("inv_capacity").focus();
						}
				}
			});
	}
});
});
	
$(document).ready(function(){
	$category2 = $('#vehicle_name');
        $category2.change(
            function() {
			document.getElementById("displysesmsg").innerHTML="";
			for(i=document.serviceindex.services_type.options.length-1;i>=1;i--)
			{
			document.serviceindex.services_type.remove(i);
			}
			for(i=document.serviceindex.services_place.options.length-1;i>=1;i--)
			{
			document.serviceindex.services_place.remove(i);
			}
			for(i=document.serviceindex.state.options.length-1;i>=1;i--)
			{
			document.serviceindex.state.remove(i);
			}
			for(i=document.serviceindex.city.options.length-1;i>=1;i--)
			{
			document.serviceindex.city.remove(i);
			}
			for(i=document.serviceindex.area.options.length-1;i>=1;i--)
			{
			document.serviceindex.area.remove(i);
			}
			document.serviceindex.vehicle_name.focus();
			
                $.ajax({
                    type: "GET",
                    url: "../../../servlet/BatteryHome?hidWhatToDo=getvehicle_model",
                    data: {vehiclename: $category2.val(), batterytype: $("#bat_type").val()},
                    success: function(data){
						if(data.indexOf("defaultss")>=0)
						{
                        $("#vehicle_model").html(data)
						document.serviceindex.vehicle_name.focus();
						}
						else
						{
						 $("#vehicle_model").html(data)
						document.serviceindex.vehicle_model.focus();
						}
                    }
                });
            }
        );
	});
$(document).ready(function(){
	$category3 = $('#vehicle_model');
	        $category3.change(
            function() {
				document.getElementById("displysesmsg").innerHTML="";
				var keyword=document.getElementById("servicestypekeyword").value;
					var splitval =$category3.val();
					//alert(splitval);
					
						for(i=document.serviceindex.services_type.options.length-1;i>=1;i--)
						{
						document.serviceindex.services_type.remove(i);
						}
						for(i=document.serviceindex.services_place.options.length-1;i>=1;i--)
						{
						document.serviceindex.services_place.remove(i);
						}
						for(i=document.serviceindex.state.options.length-1;i>=1;i--)
						{
						document.serviceindex.state.remove(i);
						}
						for(i=document.serviceindex.city.options.length-1;i>=1;i--)
						{
						document.serviceindex.city.remove(i);
						}
						for(i=document.serviceindex.area.options.length-1;i>=1;i--)
						{
						document.serviceindex.area.remove(i);
						}
						document.serviceindex.vehicle_model.focus();
					if(splitval == "default")
						{
						for(i=document.serviceindex.services_type.options.length-1;i>=1;i--)
						{
						document.serviceindex.services_type.remove(i);
						}
						for(i=document.serviceindex.services_place.options.length-1;i>=1;i--)
						{
						document.serviceindex.services_place.remove(i);
						}
						for(i=document.serviceindex.state.options.length-1;i>=1;i--)
						{
						document.serviceindex.state.remove(i);
						}
						for(i=document.serviceindex.city.options.length-1;i>=1;i--)
						{
						document.serviceindex.city.remove(i);
						}
						for(i=document.serviceindex.area.options.length-1;i>=1;i--)
						{
						document.serviceindex.area.remove(i);
						}
						document.serviceindex.vehicle_model.focus();
					}
					else
					{
						 $("#services_type").html("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>Select&nbsp;Service&nbsp;Type</option><option value='Health Check'>Battery Health Checkup</option><option value='Recharge'>Recharge</option><option value='Jump Start'>Jump Start</option>");

					}
            }
        );
	});
	$(document).ready(function(){
	$category31 = $('#inv_capacity');
	        $category31.change(
            function() {
				document.getElementById("displysesmsg").innerHTML="";
				var keyword=document.getElementById("servicestypekeyword").value;
					var splitval1 =$category31.val();
					//alert(splitval1);
						for(i=document.serviceindex.services_type.options.length-1;i>=1;i--)
						{
						document.serviceindex.services_type.remove(i);
						}
						for(i=document.serviceindex.services_place.options.length-1;i>=1;i--)
						{
						document.serviceindex.services_place.remove(i);
						}
						for(i=document.serviceindex.state.options.length-1;i>=1;i--)
						{
						document.serviceindex.state.remove(i);
						}
						for(i=document.serviceindex.city.options.length-1;i>=1;i--)
						{
						document.serviceindex.city.remove(i);
						}
						for(i=document.serviceindex.area.options.length-1;i>=1;i--)
						{
						document.serviceindex.area.remove(i);
						}
						document.serviceindex.vehicle_model.focus();
						if(splitval1 == "default")
						{
						for(i=document.serviceindex.services_type.options.length-1;i>=1;i--)
						{
						document.serviceindex.services_type.remove(i);
						}
						for(i=document.serviceindex.services_place.options.length-1;i>=1;i--)
						{
						document.serviceindex.services_place.remove(i);
						}
						for(i=document.serviceindex.state.options.length-1;i>=1;i--)
						{
						document.serviceindex.state.remove(i);
						}
						for(i=document.serviceindex.city.options.length-1;i>=1;i--)
						{
						document.serviceindex.city.remove(i);
						}
						for(i=document.serviceindex.area.options.length-1;i>=1;i--)
						{
						document.serviceindex.area.remove(i);
						}
						document.serviceindex.vehicle_model.focus();
					}
					else
					{
						 $("#services_type").html("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='default'>Select&nbsp;Service&nbsp;Type</option><option value='Health Check'>Battery Health Checkup</option><option value='Recharge'>Recharge</option><option value='Jump Start'>Jump Start</option>");
					}
				 }
			);
		});
	$(document).ready(function(){
	$category4 = $('#services_type');
	        $category4.change(
            function() {
				document.getElementById("displysesmsg").innerHTML="";
					var splitvalcity =$category4.val();
					    for(i=document.serviceindex.services_place.options.length-1;i>=1;i--)
						{
						document.serviceindex.services_place.remove(i);
						}
						for(i=document.serviceindex.state.options.length-1;i>=1;i--)
						{
						document.serviceindex.state.remove(i);
						}
						for(i=document.serviceindex.city.options.length-1;i>=1;i--)
						{
						document.serviceindex.city.remove(i);
						}
						for(i=document.serviceindex.area.options.length-1;i>=1;i--)
						{
						document.serviceindex.area.remove(i);
						}
						document.serviceindex.services_type.focus();
						if(splitvalcity == "default")
						{
						for(i=document.serviceindex.services_place.options.length-1;i>=1;i--)
						{
						document.serviceindex.services_place.remove(i);
						}
						for(i=document.serviceindex.state.options.length-1;i>=1;i--)
						{
						document.serviceindex.state.remove(i);
						}
						for(i=document.serviceindex.city.options.length-1;i>=1;i--)
						{
						document.serviceindex.city.remove(i);
						}
						for(i=document.serviceindex.area.options.length-1;i>=1;i--)
						{
						document.serviceindex.area.remove(i);
						}
						document.serviceindex.services_type.focus();
						}
						else
						{
                            $("#services_place").html("<option style='font-family:Verdana;font-size:12px;color:#CCCCCC;' value='defaultss'>&nbsp;Select&nbsp;Service Place</option><option value='store'>At Store</option><option value='within_10km'>with in 10km</option>");
						}
            }
        );
	});
		
		$(document).ready(function(){
	$category5 = $('#services_place');
	        $category5.change(
            function() {
				document.getElementById("displysesmsg").innerHTML="";
					var splitvalcity =$category5.val();
					
						for(i=document.serviceindex.state.options.length-1;i>=1;i--)
						{
						document.serviceindex.state.remove(i);
						}
						for(i=document.serviceindex.city.options.length-1;i>=1;i--)
						{
						document.serviceindex.city.remove(i);
						}
						for(i=document.serviceindex.area.options.length-1;i>=1;i--)
						{
						document.serviceindex.area.remove(i);
						}
						document.serviceindex.services_type.focus();
						if(splitvalcity == "default")
						{
						for(i=document.serviceindex.state.options.length-1;i>=1;i--)
						{
						document.serviceindex.state.remove(i);
						}
						for(i=document.serviceindex.city.options.length-1;i>=1;i--)
						{
						document.serviceindex.city.remove(i);
						}
						for(i=document.serviceindex.area.options.length-1;i>=1;i--)
						{
						document.serviceindex.area.remove(i);
						}
						document.serviceindex.services_type.focus();
						}
						else
						{
						   $.ajax({					 
							type: "GET",
							url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getservicestate",
							success: function(data)
							{	
								$("#state").html(data);
							}
							});
						}
            }
        );
	});


		
	$(document).ready(function(){
	$category6 = $('#state');
	        $category6.change(
            function() {
				document.getElementById("displysesmsg").innerHTML="";
					var splitvalcity =$category6.val();
					
						for(i=document.serviceindex.city.options.length-1;i>=1;i--)
						{
						document.serviceindex.city.remove(i);
						}
						for(i=document.serviceindex.area.options.length-1;i>=1;i--)
						{
						document.serviceindex.area.remove(i);
						}
						document.serviceindex.state.focus();
						if(splitvalcity == "default")
						{
						for(i=document.serviceindex.city.options.length-1;i>=1;i--)
						{
						document.serviceindex.city.remove(i);
						}
						for(i=document.serviceindex.area.options.length-1;i>=1;i--)
						{
						document.serviceindex.area.remove(i);
						}
						document.serviceindex.state.focus();
						}
						else
						{
						
							$.ajax({					 
							type: "GET",
							url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getservicecity",
							 data: {state: $category6.val()},
							success: function(data)
							{	
								$("#city").html(data);
							}
							});
			
						}
            }
        );
	});
$(document).ready(function(){
	$category7 = $('#city');
	        $category7.change(
            function() {
				document.getElementById("displysesmsg").innerHTML="";
					var splitvalarea =$category7.val();
					
						for(i=document.serviceindex.area.options.length-1;i>=1;i--)
						{
						document.serviceindex.area.remove(i);
						}
						document.serviceindex.city.focus();
						if(splitvalarea == "default")
						{
						for(i=document.serviceindex.area.options.length-1;i>=1;i--)
						{
						document.serviceindex.area.remove(i);
						}
						document.serviceindex.city.focus();
						}
						else
						{
					
				$.ajax({
                    type: "GET",
                    url: "../../../servlet/BatteryHome?hidWhatToDo=getservicearea",
                   data: {city: $category7.val() },
                    success: function(data){
						if(data.indexOf("defaultss")>=0)
						{
                        $("#area").html(data)
						document.serviceindex.city.focus();
						}
						else
						{
						 $("#area").html(data)
						document.serviceindex.area.focus();
						}
                    }
                });
			  }
            }
        );
	});
$(document).ready(function(){
var serviceurl=window.location.search;
	if(serviceurl=="?nametype=payment")
	{
		$('#paymentdiv').show();
	}
	else if(serviceurl=="?nametype=shipping")
	{
		$('#shippingdiv').show();
	}
	else if(serviceurl=="?nametype=privacy")
	{
		$('#privacydiv').show();
	}
	else if(serviceurl=="?nametype=refund")
	{
		$('#refunddiv').show();
	}
	else
	{

	}
	});
function funToGetServicedetails()
{
	var bat_type = document.serviceindex.bat_type.value;
	var vehiclename = document.serviceindex.vehicle_name.value;
	var vehiclemodel = escape(document.serviceindex.vehicle_model.value);
	var servicestype = document.serviceindex.services_type.value;
	var servicesplace = document.serviceindex.services_place.value;
	var state = document.serviceindex.state.value;
	var city = document.serviceindex.city.value;
	var area = document.serviceindex.area.value;
	var pincode = document.serviceindex.pincode.value;
	var inv_capacity = document.serviceindex.inv_capacity.value;

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
	


	if(bat_type=="Car Batteries" || bat_type=="Bike Batteries")
	{
		if(document.serviceindex.vehicle_name.value == 0 || vehiclename=="default")
		{
			errMsg ="<font color='#ff3333'>Please Select \'Make\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.serviceindex.vehicle_name.focus();
			return ;
		}
		if(document.serviceindex.vehicle_model.value == 0 || vehiclemodel=="default")
		{
			errMsg ="<font color='#ff3333'>Please Select \'Model\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.serviceindex.vehicle_model.focus();
			return ;
		}
		vehiclename = vehiclename;
		vehiclemodel = vehiclemodel;
	}
	else
	{
		if(document.serviceindex.inv_capacity.value == 0 || inv_capacity=="default")
		{
			errMsg ="<font color='#ff3333'>Please Select \'Capacity\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.serviceindex.inv_capacity.focus();
			return ;
		}
		inv_capacity = inv_capacity;
	}
	servicestype = servicestype;
	servicesplace = servicesplace;
	if(document.serviceindex.services_type.value == 0 || servicestype=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'Services Type\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.serviceindex.services_type.focus();
		return ;
	}
	if(document.serviceindex.services_place.value == 0 || document.serviceindex.services_place.value=="defaultss")
	{
		errMsg ="<font color='#ff3333'>Please Select \'Services Package\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.serviceindex.services_place.focus();
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
			document.serviceindex.pincode.focus();
			return ;
		}
		if(document.serviceindex.state.value == 0 || state=="default")
		{
			errMsg ="<font color='#ff3333'>Please Select \'State\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.serviceindex.state.focus();
			return ;
		}
		if(document.serviceindex.city.value == 0 || city=="default")
		{
			errMsg ="<font color='#ff3333'>Please Select \'City\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.serviceindex.city.focus();
			return ;
		}
		if(document.serviceindex.area.value == 0 || area=="default")
		{
			errMsg ="<font color='#ff3333'>Please Select \'Area\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.serviceindex.area.focus();
			return ;
		}
	}
	else
	{
		if(document.serviceindex.pincode.value == "" || pincode=="default")
		{
			errMsg ="<font color='#ff3333'>Please Enter \'Pincode\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.serviceindex.pincode.focus();
			return ;
		}
		var pincoderegex=/^[0-9]{4,6}$/;
		if(!pincode.match(pincoderegex))
		{
			errMsg ="<font color='#ff3333'>Please Enter Valid \'Pincode\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.serviceindex.pincode.focus();
			return ;
		}
	}
	
	document.serviceindex.method="post";
	document.serviceindex.action="../../../servlet/OperatorServicesDetails?hidWhatToDo=getservicesdetails&vehiclename="+vehiclename+"&vehiclemodel="+vehiclemodel+"&servicestype="+servicestype+"&servicesplace="+servicesplace+"&state="+state+"&bat_type="+bat_type+"&city="+city+"&area="+area+"&inv_capacity="+inv_capacity+"&pincode="+pincode;
	document.serviceindex.submit();
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
	document.serviceindex.area.focus();
}
function changetopincode()
{
	
	document.getElementById("displysesmsg").innerHTML="";
	document.serviceindex.pincode.value="";
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
</style>
<link href="../../../css/bookbattery.css" rel="stylesheet" type="text/css" />
</head>
<body onload="onloadcity();" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="serviceindex" method="post" ENCTYPE="multipart/form-data">
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
				<!-- Starts Services admin left Menu -->
				<tr>
					<jsp:include page="../operatorleftmenu.jsp"/>
				</tr>
				<!-- Ends Services admin left Menu -->
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
								<td class="subheading">Order&nbsp;Services</td>
								<td  align="right"><a href="../../../servlet/OperatorLogin?hidWhatToDo=batteryoperatorhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
							</tr>
							<tr><td height="25"></td></tr>
							<tr><td align="center" class="insidecontentservices"><div id="displysesmsg"></div></td></tr>
							<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
							<tr>
							<td width="25%" align="left">
								<div class="styled-select">
									<select id="bat_type" name="bat_type_name"  class="insidecontent" style='width:155px'>
										<option style="color:#ccc" value="0" class='topindex1'>&nbsp;Select&nbsp;Battery Type</option>
                                        <option style="color:#000" value="Inverter Batteries" class='topindex1'>&nbsp;Inverter&nbsp;Battery</option>
										<option style="color:#000" value="Car Batteries" class='topindex1'>&nbsp;Car&nbsp;Battery</option>
										<option style="color:#000" value="Bike Batteries" class='topindex1'>&nbsp;Bike&nbsp;Battery</option>
										
									</select>
								</div>
							</td>	
								<td id="inv_capacity_div" style="display:none" width="25%" align="left">
									<div class="styled-select">
										<select name="inv_capacity" id ="inv_capacity" class="insidecontent" style='width:155px'>
											<option style="background:#FFF" value="0" class='topindex1'>&nbsp;&nbsp;Select&nbsp;Capacity</option>
										</select>
									</div>
								</td>	
							
							
													<td id="car_make_div" width="25%" align="left">
														<div class="styled-select">
															<select name="vehicle_name" id ="vehicle_name" class="insidecontent" style='width:155px'>
																<option style="background:#FFF" value="0" class='topindex1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;Make</option>
															</select>
															
														</div>
													</td>
													<td id="car_model_div" width="25%" align="left">
														<div class="styled-select" >
															<select name="vehicle_model" id ="vehicle_model" class="insidecontent" style='width:155px;'>
																<option style="background:#FFF" value="0" class='topindex1' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;Model</option>
															</select>
															
														</div>
													</td>
													<td width="25%" align="left">
														<div class="styled-select">
															<select name="services_type" id ="services_type" class="insidecontent" style='width:155px;' >
																<option style="background:#FFF" value="0" class='topindex1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;Services Type</option>
															</select>
														
														</div>
													</td>
													<td width="25%" align="left">
														<div class="styled-select">
															<select name="services_place" id ="services_place" class="insidecontent" style='width:155px;' >
																<option style="background:#FFF" value="0" class='topindex1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;Services Package</option>
															</select>
														
														</div>
													</td>						
												</tr>
												<tr><td height="5"></td><td height="5"></td><td height="5"></td><td height="5"></td></tr>
												<tr>
													<td width="25%" align="left"  valign="top">
													<div id="statediv">
														<div class="styled-select">
															<select  name='state' id='state' class="insidecontent" style='width:155px;  bgcolor:#FFF;' >
															<option style="background:#FFF" value="0" class='topindex1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;State</option>
															</select>
														</div>
														</div>
													</td>
													<td width="25%" align="left" valign="top" >
														<div id="citydiv">
															<div class="styled-select">
																<select name="city" id ="city" class="insidecontent"  style='width:155px;' >
																	<option style="background:#FFF" value="0" class='topindex1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;City</option>
																</select>
															</div>
														</div>
														<div id="pincode2" align="left">
															<font style='font-family:Verdana;font-size:10px;color:#000000;' >Please Enter Your PinCode:</font>
														</div>
													</td>
													<td width="25%" align="left" valign="top">
														<div id="areadiv">
															<div class="styled-select">
																<select name="area" id ="area" class="insidecontent"  style='width:155px;' >
																	<option style="background:#FFF" value="0"  class='topindex1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;Area</option>
																</select>
															</div>
														</div>							
														<div id="pincode3">
															<input class='insidecontent' type='text' name='pincode' id='pincode' placeholder='517001' style='width:149px;height:28px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='40'/>
														</div>
													</td>
													<td width="10%" align="left" valign="top">
												
													<div id='findservicebutton'>
														<a href="javascript:funToGetServicedetails();"><img src="../../../images/findbttry.png" alt="Order service for best service and best discount price." border="0" width="155" height="35" /></a>
													</div>
													</td>
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
<input type="hidden" name="servicestypekeyword" id='servicestypekeyword' value="Common">
<input type="hidden" name="backkeyword" id='backkeyword' value="ServicesType">
</form>
</center>
<%
String strserviceLogMsg=(String)session.getAttribute("sesbatterydetailsErrorMsg");
if(strserviceLogMsg ==null)
{
	strserviceLogMsg="";
}
else
{
%>
<script type="text/javascript">
var sesmessg; 
sesmessg= "<%=strserviceLogMsg%>";
document.getElementById("displysesmsg").innerHTML=sesmessg;
</script>
<%
	session.removeAttribute("sesbatterydetailsErrorMsg");
}
%>
</body>
</html>