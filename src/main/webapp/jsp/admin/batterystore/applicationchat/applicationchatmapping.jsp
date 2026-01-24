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
<title>BookBattery.com-Online Battery Store</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-multiselect/0.9.13/css/bootstrap-multiselect.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">


<body onload="document.applicationchatmapping.battery_type.focus(); getBatteryBrands();" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="applicationchatmapping" action="request_for_quote.asp"  method="post">
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
								<td class="subheading">Add&nbsp;Application&nbsp;Chart&nbsp;Mapping</td>
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
									<td width="39%" class="insidecontent" align="right"><label id="dynamic_title">Select Make<font color="FF0000">*</font>&nbsp;:</label></td>
									<td width="59%" class="insidecontent" align="left">
										<select name="vehicle_name" id ="vehicle_name"class="insidecontent" style='width:180px'>
											<option value="0" >&lt;-&nbsp;Select Make&nbsp;-&gt;</option>
										</select>
									</td>
								</tr>
								
								<tr>
									<td width="39%" class="insidecontent" align="right"><label id="dynamic_title2">Select Model<font color="FF0000">*</font>&nbsp;:</label></td>
									<td width="59%" class="insidecontent" align="left"><select name="vehicle_model" id ="vehicle_model" class="insidecontent" style='width:180px'>
									<option value="0" >&lt;-&nbsp;Select Model&nbsp;-&gt;</option>
										</select></td>
								</tr>
								<tr class="#FFFFFF" bgcolor="FFFFFF">
									<td class="subheading" align="left" > Select Battery Brand</td>
								</tr>
								</table>
								<tr>
									<td>
										<table width="80%" border="0" align="center">
											<tr class="#FFFFFF" bgcolor="FFFFFF">
												<td width="40%"><div id="divbatband"></div></td>
												<td></td>
												<td width="60%"><div id="divbatmodel"></div></td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
								<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
								<tr>
									<td width="39%" class="insidecontent" align="right"><input type="button" value="Submit" class="button4" onclick="javascript:funToAddAppChatMap(this);"></td>
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

<script src="/bookbattery/js/jquery.min.js"></script>
<script src="/bookbattery/js/bootstrap.min.js"></script>
<script src="/bookbattery/js/bootstrap-multiselect.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	
	$category = $('#battery_type');
	
        $category.change(
            function() {
			//alert($category.val());
			document.getElementById("displayappchatmaperrormsg").innerHTML="";
			var splitval =$category.val().split(',');
			
			if(splitval=="0")
			{
				for(i=document.applicationchatmapping.vehicle_model.options.length-1;i>=1;i--)
					{
						document.applicationchatmapping.vehicle_model.remove(i);
					}
					for(i=document.applicationchatmapping.vehicle_name.options.length-1;i>=1;i--)
					{
						document.applicationchatmapping.vehicle_name.remove(i);
					}
					document.applicationchatmapping.battery_type.focus();
			}
			else
			{
			if(splitval[1]=="Inverter Batteries")
				{
				 $.ajax({
                    type: "GET",
                    url: "/bookbattery/servlet/ApplicationChatMapping?hidWhatToDo=getbatterycapacity",
                    data: {batterytype: $category.val() },
                    success: function(data){
                        $("#vehicle_name").html(data)
						if (data.indexOf("Select Battery Capacity") >= 0)
						{
						for (i=document.applicationchatmapping.vehicle_model.options.length-1; i >= 1; i--)
						{
						document.applicationchatmapping.vehicle_model.remove(i);
						}
						return;
						}
						document.applicationchatmapping.vehicle_name.focus();
                    }
                });
					$labelval=$('#dynamic_title');
					//alert($labelval.html());
					$labelval.html("Battery Capacity<font color='FF0000'>*</font>&nbsp;:");
					$('#vehmodel').hide();
					$('#vehicle_model').multiselect('destroy');
					$('#dynamic_title2').hide();
					$('#vehicle_model').hide();
					document.applicationchatmapping.vehicle_name.focus();
				}
				else if(splitval[1] == "Gensets")
				{
                $.ajax({
                    type: "GET",
                    url: "/bookbattery/servlet/ApplicationChatMapping?hidWhatToDo=getvehiclenames",
                    data: {batterytype: $category.val() },
                    success: function(data){
                        $("#vehicle_name").html(data)
						if (data.indexOf("Select Make") >= 0)
						{
						for (i=document.applicationchatmapping.vehicle_model.options.length-1; i >= 1; i--)
						{
						document.applicationchatmapping.vehicle_model.remove(i);
						}
						return;
						}
						document.applicationchatmapping.vehicle_name.focus();
                    }
                });
					$labelval=$('#dynamic_title');
					//alert($labelval.html());
					$labelval.html("Select Gensets<font color='FF0000'>*</font>&nbsp:");
					$('#vehmodel').show();
					
					$('#dynamic_title2').show();
					$('#vehicle_model').show();
					$('#vehicle_model').multiselect('destroy');
					document.applicationchatmapping.vehicle_name.focus();
				}
				else if(splitval[1] == "Generators")
				{

                $.ajax({
                    type: "GET",
                    url: "/bookbattery/servlet/ApplicationChatMapping?hidWhatToDo=getvehiclenames",
                    data: {batterytype: $category.val() },
                    success: function(data){
                        $("#vehicle_name").html(data)
						if (data.indexOf("Select Make") >= 0)
						{
						for (i=document.applicationchatmapping.vehicle_model.options.length-1; i >= 1; i--)
						{
						document.applicationchatmapping.vehicle_model.remove(i);
						}
						return;
						}
						document.applicationchatmapping.vehicle_name.focus();
                    }
                });
					$labelval=$('#dynamic_title');
					//alert($labelval.html());
					$labelval.html("Select Generators<font color='FF0000'>*</font>&nbsp:");
					$('#vehmodel').show();
					$('#dynamic_title2').show();
					$('#vehicle_model').show();
					$('#vehicle_model').multiselect('destroy');
					document.applicationchatmapping.vehicle_name.focus();
				}
				else
				{
					$.ajax({
                    type: "GET",
                    url: "/bookbattery/servlet/ApplicationChatMapping?hidWhatToDo=getvehiclenames",
                    data: {batterytype: $category.val() },
                    success: function(data){
                        $("#vehicle_name").html(data)
						if (data.indexOf("Select Make") >= 0)
						{
						for (i=document.applicationchatmapping.vehicle_model.options.length-1; i >= 1; i--)
						{
						document.applicationchatmapping.vehicle_model.remove(i);
						}
						return;
						}
						document.applicationchatmapping.vehicle_name.focus();
                    }
                });
					$labelval=$('#dynamic_title');
					//alert($labelval.html());
					$labelval.html("Select Make<font color='FF0000'>*</font>&nbsp:");
					$('#vehmodel').show();
					$('#dynamic_title2').show();
					$('#vehicle_model').show();
					$('#vehicle_model').attr('multiple', false);
					$('#vehicle_model').multiselect('destroy');
					document.applicationchatmapping.vehicle_name.focus();

				}
            }
		}
        );
	});
	$category1 = $('#vehicle_name');
        $category1.change(
            function() {
			var splitval =$category1.val();
			if(splitval=="default")
			{
				for (i=document.applicationchatmapping.vehicle_model.options.length-1; i >= 1; i--)
				{
				document.applicationchatmapping.vehicle_model.remove(i);
				}
				document.applicationchatmapping.vehicle_name.focus();
				//$('#vehicle_model').multiselect('destroy');
			}
			else
			{
                $.ajax({
                    type: "GET",
                    url: "/bookbattery/servlet/ApplicationChatMapping?hidWhatToDo=getvehicle_model",
                    data: {vehiclename: $category1.val(),batterytype: $category.val() },
                    success: function(data){
                       
					var battery_type_selected = $('#battery_type').val();

					if(battery_type_selected=='4,Inverter Batteries')
					{
						//alert(battery_type_selected);
						$("#vehicle_model").html(data);
						document.applicationchatmapping.vehicle_model.focus();
					}
					else
					{
						//alert(battery_type_selected);

					   $("#vehicle_model").html(data);
						$('#vehicle_model').attr('multiple', true);
						$('#vehicle_model').val(''); 
						$('#vehicle_model').multiselect('destroy');
						$('#vehicle_model').multiselect({
						includeSelectAllOption: true,	
						buttonWidth: '180px',
						enableFiltering: true,
						maxHeight: 350 
						});
						//$('#vehicle_model').multiselect('refresh');
						document.applicationchatmapping.vehicle_model.focus();

					}
					

                    }
                });
            }
		}
        );

function getBatteryBrands()
	{
		//alert("calling");
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
					document.getElementById("divbatband").innerHTML = resp;
					document.getElementById("divbatband").innerHTML = xmlhttp.responseText;
				}
			}			
		}
		xmlhttp.open("POST","/bookbattery/servlet/ApplicationChatMapping?hidWhatToDo=getbatterybrands",true);		
		xmlhttp.send();	
	}
	function getbattertmod()
	{
		//alert("calling");
		var selectedArray = new Array();
		count = 0;
		for(x=0; x<document.applicationchatmapping.batteryban.length; x++)
		{
			if(document.applicationchatmapping.batteryban[x].checked==true)
			{
				selectedArray[count]= document.applicationchatmapping.batteryban[x].value;
				count++;
			}
		}
	    //alert(selectedArray+"dsfdsf");
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
					document.getElementById("divbatmodel").innerHTML = resp;
					document.getElementById("divbatmodel").innerHTML = xmlhttp.responseText;
				}
			}			
		}
		xmlhttp.open("POST","/bookbattery/servlet/ApplicationChatMapping?hidWhatToDo=getbatterymodels&batterybrand="+selectedArray,true);		
		xmlhttp.send();	
	}
function funToAddAppChatMap(varButton)
{
	var baterytype = document.applicationchatmapping.battery_type.value;
	var vehiclemake = document.applicationchatmapping.vehicle_name.value;
	//var vehicemodel = escape(document.applicationchatmapping.vehicle_model.value);
	
	var vehicemodel_selected = $("#vehicle_model option:selected");
	var vehicemodel = "";
	vehicemodel_selected.each(function () {
	vehicemodel += $(this).val()+"~";
	});
	
	if(vehicemodel == "default" || vehicemodel == "defaultss")
	{
		vehicemodel = "0,default";
	}
	else
	{
		//vehicemodel=vehicemodel;
		
		//vehicemodel = vehicemodel.replace(/(^,)|(,$)/g, "")
		//alert(vehicemodel);
		
		
		
		vehicemodel=escape(vehicemodel);
		
		vehicemodel=vehicemodel;
		//alert(vehicemodel);
		//document.applicationchatmapping.vehicle_model.value = vehicemodel; 
		
		 //alert(document.applicationchatmapping.vehicle_model.value);
	}

	//alert(vehicemodel);
	
	if(baterytype == "4,Inverter Batteries")
	{
	if(document.applicationchatmapping.vehicle_name.value == 0 || vehiclemake == "default")
	 {
		errMsg ="<font color='#ff3333'>Please select \'Battery Capacity\'.</font>";
		document.getElementById("displayappchatmaperrormsg").innerHTML=errMsg;
		document.applicationchatmapping.vehicle_name.focus();
		return ;
	 }
	}
	else
	{
	if(document.applicationchatmapping.battery_type.value == 0)
	 {
		errMsg ="<font color='#ff3333'>Please select \'Battery Type\'.</font>";
		document.getElementById("displayappchatmaperrormsg").innerHTML=errMsg;
		document.applicationchatmapping.battery_type.focus();
		return ;
     }
	 if(document.applicationchatmapping.vehicle_name.value == 0 || vehiclemake == "default")
	 {
		 if(baterytype == "8,Gensets")
		 {
		errMsg ="<font color='#ff3333'>Please select \'Gensets Make\'.</font>";
		document.getElementById("displayappchatmaperrormsg").innerHTML=errMsg;
		document.applicationchatmapping.vehicle_name.focus();
		return ;
		 }
		 else if(baterytype == "18,Generators")
		 {
		errMsg ="<font color='#ff3333'>Please select \'Generators Make\'.</font>";
		document.getElementById("displayappchatmaperrormsg").innerHTML=errMsg;
		document.applicationchatmapping.vehicle_name.focus();
		return ;
		 }
		 else
		{
		errMsg ="<font color='#ff3333'>Please select \'Vehicle Make\'.</font>";
		document.getElementById("displayappchatmaperrormsg").innerHTML=errMsg;
		document.applicationchatmapping.vehicle_name.focus();
		return ;
		 }

     }
	 
		//alert(vehicemodel);
	
	
	  if(vehicemodel == 0 || vehicemodel == "0,default")
	 {
		vehicemodel="";
		errMsg ="<font color='#ff3333'>Please select \'Model\'.</font>";
		document.getElementById("displayappchatmaperrormsg").innerHTML=errMsg;
		document.applicationchatmapping.vehicle_model.focus();
		return ;
		
		
     }
	}
	var selectedArray = new Array();
		count = 0;
		for(x=0; x<document.applicationchatmapping.batteryban.length; x++)
		{
			if(document.applicationchatmapping.batteryban[x].checked==true)
			{
				selectedArray[count]= document.applicationchatmapping.batteryban[x].value;
				count++;
			}
		}
		if(selectedArray == "")
		{
			errMsg ="<font color='#ff3333'>Please select one of the \'Battery Brand\' options.</font>";
			document.getElementById("displayappchatmaperrormsg").innerHTML=errMsg;
			return ;
        }

	var selectedmodelArray = new Array();
		count = 0;
		for(x=0; x<document.applicationchatmapping.batterymodel.length; x++)
		{
			if(document.applicationchatmapping.batterymodel[x].checked==true)
			{
				selectedmodelArray[count]= document.applicationchatmapping.batterymodel[x].value;
				count++;
			}
		}
		if(selectedmodelArray == "")
		{
			errMsg ="<font color='#ff3333'>Please select one of the \'Battery Model\' options.</font>";
			document.getElementById("displayappchatmaperrormsg").innerHTML=errMsg;
			return ;
        }

		document.applicationchatmapping.method="post";
		document.applicationchatmapping.action="/bookbattery/servlet/ApplicationChatMapping?hidWhatToDo=insertapplicationchatmapping&batterytype="+baterytype+"&vehiclemake="+vehiclemake+"&vehicemodel="+vehicemodel+"&batterbrand="+selectedArray+"&batterymodel="+selectedmodelArray;
		
		//alert(document.applicationchatmapping.action);
		
		document.applicationchatmapping.submit();

		varButton.disabled=true;
}
function FunReset()
{
	location.href="/bookbattery/jsp/admin/batterystore/applicationchat/applicationchatmapping.jsp"
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

</html>