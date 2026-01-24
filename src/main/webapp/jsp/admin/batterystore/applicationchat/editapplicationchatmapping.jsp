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
	response.sendRedirect("/bookbattery/admin/index.html");
	return;
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="/bookbattery/images/favicon.png" type="image/x-icon">
<title>BookBattery.com - India's No.1 Automobile Battery Store</title>
<script language="JavaScript" src="/bookbattery/js/jquery-1.8.2.js" ></script>
<script type="text/javascript">
$(document).ready(function(){
	
	$category = $('#battery_type');
	
        $category.change(
            function() {
			//alert($category.val());
			document.getElementById("displayappchatmaperrormsg").innerHTML="";
			var splitval =$category.val().split(',');
			
			if(splitval[1]=="Inverter Batteries")
				{
				 $.ajax({
                    type: "GET",
                    url: "/bookbattery/servlet/ApplicationChatMapping?hidWhatToDo=getbatterycapacity&key=app",
                    data: {batterytype: $category.val() },
                    success: function(data){
                    $("#vehicle_model").html(data)
			        }
                });
				document.editapplicationchatmapping.vehicle_name.focus();
					$labelval=$('#dynamic_title');
					//alert($labelval.html());
					$labelval.html("Battery Capacity<font color='FF0000'>*</font>&nbsp;:");
					$('#vehmodel').hide();
					$('#dynamic_title2').hide();
					$('#vehicle_name').hide();
				}
				else
				{
					for (i=document.editapplicationchatmapping.vehicle_model.options.length-1; i >= 1; i--)
						{
						document.editapplicationchatmapping.vehicle_model.remove(i);
						}
						for (i=document.editapplicationchatmapping.bat_brand.options.length-1; i >= 1; i--)
						{
						document.editapplicationchatmapping.bat_brand.remove(i);
						}
						for (i=document.editapplicationchatmapping.bat_model.options.length-1; i >= 1; i--)
						{
						document.editapplicationchatmapping.bat_model.remove(i);
						}
						$('#appchatdetails').hide();
						document.editapplicationchatmapping.battery_type.focus();
					$.ajax({
                    type: "GET",
                    url: "/bookbattery/servlet/ApplicationChatMapping?hidWhatToDo=getvehiclenames",
                    data: {batterytype: $category.val() },
                    success: function(data){
                        $("#vehicle_name").html(data)
						if (data.indexOf("defaultss") >= 0)
						{
						for (i=document.editapplicationchatmapping.vehicle_model.options.length-1; i >= 1; i--)
						{
						document.editapplicationchatmapping.vehicle_model.remove(i);
						}
						for (i=document.editapplicationchatmapping.bat_brand.options.length-1; i >= 1; i--)
						{
						document.editapplicationchatmapping.bat_brand.remove(i);
						}
						for (i=document.editapplicationchatmapping.bat_model.options.length-1; i >= 1; i--)
						{
						document.editapplicationchatmapping.bat_model.remove(i);
						}
						$('#appchatdetails').hide();
						document.editapplicationchatmapping.battery_type.focus();
						}
						else
						{
						document.editapplicationchatmapping.vehicle_name.focus();
						}
                    }
                });
					$labelval=$('#dynamic_title');
					//alert($labelval.html());
					$labelval.html("Select  Model<font color='FF0000'>*</font>&nbsp:");
					$('#vehmodel').show();
					$('#dynamic_title2').show();
					$('#vehicle_name').show();
				}
            }
        );
	});
$(document).ready(function(){
	$category1 = $('#vehicle_name');
        $category1.change(
            function() {
			for (i=document.editapplicationchatmapping.vehicle_model.options.length-1; i >= 1; i--)
						{
						document.editapplicationchatmapping.vehicle_model.remove(i);
						}
						for (i=document.editapplicationchatmapping.bat_brand.options.length-1; i >= 1; i--)
						{
						document.editapplicationchatmapping.bat_brand.remove(i);
						}
						for (i=document.editapplicationchatmapping.bat_model.options.length-1; i >= 1; i--)
						{
						document.editapplicationchatmapping.bat_model.remove(i);
						}
						document.editapplicationchatmapping.battery_type.focus();
						$('#appchatdetails').hide();
						document.editapplicationchatmapping.vehicle_name.focus();
                $.ajax({

                    type: "GET",
                    url: "/bookbattery/servlet/ApplicationChatMapping?hidWhatToDo=getvehicle_model",
                    data: {vehiclename: $category1.val(),batterytype: $category.val() },
                    success: function(data){
						$("#vehicle_model").html(data)
						if (data.indexOf("defaultss") >= 0)
						{
						for (i=document.editapplicationchatmapping.vehicle_model.options.length-1; i >= 1; i--)
						{
						document.editapplicationchatmapping.vehicle_model.remove(i);
						}
						for (i=document.editapplicationchatmapping.bat_brand.options.length-1; i >= 1; i--)
						{
						document.editapplicationchatmapping.bat_brand.remove(i);
						}
						for (i=document.editapplicationchatmapping.bat_model.options.length-1; i >= 1; i--)
						{
						document.editapplicationchatmapping.bat_model.remove(i);
						}
						document.editapplicationchatmapping.battery_type.focus();
						$('#appchatdetails').hide();
						document.editapplicationchatmapping.vehicle_name.focus();
						}
						else
						{
						document.editapplicationchatmapping.vehicle_model.focus();
						}
                    }
                });
            }
        );
	});
$(document).ready(function(){
	$category4 = $('#vehicle_model');
        $category4.change(
            function() {
			var splitvalbaa =$category4.val();
			for (i=document.editapplicationchatmapping.bat_brand.options.length-1; i >= 1; i--)
						{
						document.editapplicationchatmapping.bat_brand.remove(i);
						}
						for (i=document.editapplicationchatmapping.bat_model.options.length-1; i >= 1; i--)
						{
						document.editapplicationchatmapping.bat_model.remove(i);
						}
						$('#appchatdetails').hide();
						document.editapplicationchatmapping.vehicle_model.focus();
                $.ajax({
                    type: "GET",
                    url: "/bookbattery/servlet/ApplicationChatMapping?hidWhatToDo=getbrandtoedit",
                    data: {vehiclename: $category4.val(),batterytype: $category.val() },
                    success: function(data){
                        $("#bat_brand").html(data)
						if (splitvalbaa == "default")
						{
						for (i=document.editapplicationchatmapping.bat_brand.options.length-1; i >= 1; i--)
						{
						document.editapplicationchatmapping.bat_brand.remove(i);
						}
						for (i=document.editapplicationchatmapping.bat_model.options.length-1; i >= 1; i--)
						{
						document.editapplicationchatmapping.bat_model.remove(i);
						}
						document.editapplicationchatmapping.battery_type.focus();
						$('#appchatdetails').hide();
						document.editapplicationchatmapping.vehicle_model.focus();
						}
						else
						{
						document.editapplicationchatmapping.bat_brand.focus();
						}
	                  }
                });
            }
        );
	});
$(document).ready(function(){
	$category5 = $('#bat_brand');
        $category5.change(
            function() {
			
						$('#appchatdetails').hide();
						document.editapplicationchatmapping.bat_brand.focus();
                $.ajax({
                    type: "GET",
                    url: "/bookbattery/servlet/ApplicationChatMapping?hidWhatToDo=getmodeltoedit",
                    data: {batterytype: $category.val(),vehiclename: $category1.val(), vehiclemodel:$category4.val(), batterybrand: $category5.val() },
                    success: function(data){
                        $("#bat_model").html(data)
						if (data.indexOf("defaultss") >= 0)
						{
						for (i=document.editapplicationchatmapping.bat_model.options.length-1; i >= 1; i--)
						{
						document.editapplicationchatmapping.bat_model.remove(i);
						}
						document.editapplicationchatmapping.battery_type.focus();
						$('#appchatdetails').hide();
						document.editapplicationchatmapping.bat_brand.focus();
						}
						else
						{
						document.editapplicationchatmapping.bat_model.focus();
						}
                    }
                });
            }
        );
	});
function funToAddAppChatMap()
{
	var baterytype = document.editapplicationchatmapping.battery_type.value;
	var vehiclemake = document.editapplicationchatmapping.vehicle_name.value;
	var vehicemodel = document.editapplicationchatmapping.vehicle_model.value;
	var batbrand = document.editapplicationchatmapping.bat_brand.value;
	var batmodel = document.editapplicationchatmapping.bat_model.value;

	if(vehicemodel == "default")
	{
		vehicemodel = "0,default";
	}
	else
	{
		vehicemodel=vehicemodel;
	}

	if (vehicemodel.indexOf(",")!=-1)
	{
		var strvehiclemodel = vehicemodel.split(',');
		var vehid = strvehiclemodel[0];
		var vehmodel = escape(strvehiclemodel[1]);

		vehicemodel = vehmodel;
	}
	else
	{
		vehicemodel = vehicemodel;
	}

		
		var xmlhttp= "";
		var resp= "";
		var url = "/bookbattery/servlet/ApplicationChatMapping?hidWhatToDo=getapplichatdetailstoedit&batterytype="+baterytype+"&vehiclemake="+vehiclemake+"&vehicemodel="+vehicemodel+"&batterybrand="+batbrand+"&batterymodel="+batmodel;
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
					$('#appchatdetails').show();
					document.getElementById("appchatdetails").innerHTML = resp;
					document.getElementById("appchatdetails").innerHTML = xmlhttp.responseText;
				}
			}			
		}
		xmlhttp.open("GET",url, true);		
		xmlhttp.send();	

}
function deleteappichatdetails(mapid)
{
		var xmlhttp= "";
		var resp= "";
		var url ="/bookbattery/servlet/ApplicationChatMapping?hidWhatToDo=getdeleteappication&chkSi="+mapid;
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
					$('#appchatdetails').hide();
					document.getElementById("displayappchatmaperrormsg").innerHTML = resp;
					document.getElementById("displayappchatmaperrormsg").innerHTML = xmlhttp.responseText;
				}
			}			
		}
		var agree=confirm("Are You sure want to delete Application chat details! ");
		if (agree)
		{
				for (i=document.editapplicationchatmapping.battery_type.options.length-1; i >= 1; i--)
				{
					document.editapplicationchatmapping.battery_type.remove(i);
				}
				for (i=document.editapplicationchatmapping.vehicle_name.options.length-1; i >= 1; i--)
				{
					document.editapplicationchatmapping.vehicle_name.remove(i);
				}
				for (i=document.editapplicationchatmapping.vehicle_model.options.length-1; i >= 1; i--)
				{
					document.editapplicationchatmapping.vehicle_model.remove(i);
				}
				for (i=document.editapplicationchatmapping.bat_brand.options.length-1; i >= 1; i--)
				{
					document.editapplicationchatmapping.bat_brand.remove(i);
				}
				for (i=document.editapplicationchatmapping.bat_model.options.length-1; i >= 1; i--)
				{
					document.editapplicationchatmapping.bat_model.remove(i);
				}
				 $("#battery_type").html('	<option value="0">&lt;-&nbsp;Select&nbsp;Type&nbsp;-&gt;</option>	<option value="1,Bike Batteries">Bike Batteries</option>				<option value="2,Car Batteries" >Car Batteries</option><option value="3,Three Wheeler Batteries" >Three Wheeler Batteries</option><option value="4,Inverter Batteries" >Inverter Batteries</option><option value="5,Truck Batteries" >Truck Batteries</option><option value="6,Bus Batteries" >Bus Batteries</option><option value="7,Special Vehicle Batteries" >Special Vehicle Batteries</option><option value="8,Genset Batteries" >Genset Batteries</option><option value="9,Crane Batteries" >Crane Batteries</option>	<option value="10,Roller Batteries" >Roller Batteries</option>	<option value="11,Loader Batteries" >Loader Batteries</option>	<option value="12,Tractor Batteries" >Tractor Batteries</option><option value="13,Dozer Batteries" >Dozer Batteries</option><option value="14,Excavator Batteries" >Excavator Batteries</option><option value="15,Tyre Handler Batteries" >Tyre Handler Batteries</option><option value="16,Hydraulic Shovel Batteries" >Hydraulic Shovel Batteries</option><option value="17,Harvestor Batteries" >Harvestor Batteries</option>	<option value="18,Generator Batteries >Generator Batteries</option>	<option value="19,Compactor Batteries" >Compactor Batteries</option><option value="20,Telescopic Handler Batteries" >Telescopic Handler Batteries</option><option value="21,Forwarder Batteries" >Forwarder Batteries</option><option value="22,Wheeled Harvester Batteries" >Wheeled Harvester Batteries</option><option value="23,Minibus Batteries" >Minibus Batteries</option>	<option value="24,Dumper Batteries" >Dumper Batteries</option><option  value="25,Construction Equipment Batteries" >Construction Equipment Batteries</option><option  value="26,Hydralic Excavator Batteries" >Hydralic Excavator Batteries</option>')
				xmlhttp.open("GET",url, true);		
				xmlhttp.send();	
		}

}
function FunReset()
{
	location.href="/bookbattery/jsp/admin/batterystore/applicationchat/editapplicationchatmapping.jsp"
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
<body onload="document.editapplicationchatmapping.battery_type.focus();" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="editapplicationchatmapping" method="post">
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
								<td class="subheading">Delete&nbsp;Application&nbsp;Chart&nbsp;Mapping</td>
								<td  align="right"><a href="/bookbattery/servlet/AdminBatteryLogin?hidWhatToDo=batteryadminhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
							</tr>
							<tr><td height="10"></td></tr>
							<tr><td align="center" class="insidecontentbat"><div id="displayappchatmaperrormsg"></div></td></tr>
							<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
								<tr>
									<td width="39%" class="insidecontent" align="right">Select&nbsp;Battery&nbsp;Type<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">
									<select name='battery_type' id='battery_type' class="insidecontent" style='width:180px'>
										<option value='0'>&lt;-&nbsp;Select&nbsp;Type&nbsp;-&gt;</option>
									<option value='1,Bike Batteries'>Bike Batteries</option>
										<option value="2,Car Batteries" >Car Batteries</option>
										<option value="3,Three Wheeler Batteries" >Three Wheeler Batteries</option>
										<option value="4,Inverter Batteries" >Inverter Batteries</option>
										<option value="5,Truck Batteries" >Truck Batteries</option>
										<option value="6,Bus Batteries" >Bus Batteries</option>
										<option value="7,Special Vehicle Batteries" >Special Vehicle Batteries</option>
										<option value="8,Genset Batteries" >Genset Batteries</option>
										<option value="9,Crane Batteries" >Crane Batteries</option>
										<option value="10,Roller Batteries" >Roller Batteries</option>
										<option value="11,Loader Batteries" >Loader Batteries</option>
										<option value="12,Tractor Batteries" >Tractor Batteries</option>
										<option value="13,Dozer Batteries" >Dozer Batteries</option>
										<option value="14,Excavator Batteries" >Excavator Batteries</option>
										<option value="15,Tyre Handler Batteries" >Tyre Handler Batteries</option>
										<option value="16,Hydraulic Shovel Batteries" >Hydraulic Shovel Batteries</option>
										<option value="17,Harvestor Batteries" >Harvestor Batteries</option>
										<option value="18,Generator Batteries" >Generator Batteries</option>
										<option value="19,Compactor Batteries" >Compactor Batteries</option>
										<option value="20,Telescopic Handler Batteries" >Telescopic Handler Batteries</option>
										<option value="21,Forwarder Batteries" >Forwarder Batteries</option>
										<option value="22,Wheeled Harvester Batteries" >Wheeled Harvester Batteries</option>
										<option value="23,Minibus Batteries" >Minibus Batteries</option>
										<option value="24,Dumper Batteries" >Dumper Batteries</option>
										<option  value="25,Construction Equipment Batteries" >Construction Equipment Batteries</option>
										<option  value="26,Hydralic Excavator Batteries" >Hydralic Excavator Batteries</option>
									</select></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right"><label id="dynamic_title2">Select Make<font color="FF0000">*</font>&nbsp;:</label></td>
									<td width="59%" class="insidecontent" align="left">
										<select name="vehicle_name" id ="vehicle_name"class="insidecontent" style='width:180px'>
											<option value="0" >&lt;-&nbsp;Select Make&nbsp;-&gt;</option>
										</select>
									</td>
								</tr>
								
								<tr>
									<td width="39%" class="insidecontent" align="right"><label id="dynamic_title">Select Model<font color="FF0000">*</font>&nbsp;:</label></td>
									<td width="59%" class="insidecontent" align="left"><select name="vehicle_model" id ="vehicle_model" class="insidecontent" style='width:180px'>
									<option value="0" >&lt;-&nbsp;Select Model&nbsp;-&gt;</option>
										</select></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Select Battery Brand<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left"><select name="bat_brand" id ="bat_brand" class="insidecontent" style='width:180px'>
									<option value="0" >&lt;-&nbsp;Select Brand&nbsp;-&gt;</option>
										</select></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Select Battery Model<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left"><select name="bat_model" id ="bat_model" onChange="javascript:funToAddAppChatMap();" class="insidecontent" style='width:180px'>
									<option value="0" >&lt;-&nbsp;Select Model&nbsp;-&gt;</option>
										</select></td>
								</tr>
								</table>
								
					<tr><td height="5"></td></tr>
					<tr>
									<td>
										<table width="100%" border="0" align="center">
											<tr class="#FFFFFF" bgcolor="FFFFFF">
												<td width="40%"><div id="appchatdetails"></div></td>
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
String strLogmapMsg=(String)session.getAttribute("sesmapErrorMsg");
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
document.getElementById("displayappchatmaperrormsg").innerHTML=loginmpamessg;
</script>
<%
	session.removeAttribute("sesmapErrorMsg");
}
%>
</body>
</html>