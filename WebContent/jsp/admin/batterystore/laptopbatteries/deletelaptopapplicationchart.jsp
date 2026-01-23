<%--
    Document   : deletelaptopapplicationchart
    Created on : 8, Feb 2014, 4:22:12 PM
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
	response.sendRedirect("../../../../admin/index.html");
	return;
}
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
	
	$category = $('#battery_type');
	
        $category.change(
            function() {
			//alert($category.val());
			document.getElementById("displayappchatmaperrormsg").innerHTML="";
			var splitval =$category.val().split(',');
                $.ajax({
                    type: "GET",
                    url: "../../../../servlet/LaptopApplicationChartMapping?hidWhatToDo=getlaptopnames",
                    data: {batterytype: $category.val() },
                    success: function(data){
                        $("#laptop_name").html(data)
						if (data.indexOf("defaultss") >= 0)
						{
							$('#appchatdetails').hide();
						for (i=document.editapplicationchatmapping.laptop_model.options.length-1; i >= 1; i--)
						{
						document.editapplicationchatmapping.laptop_model.remove(i);
						}
						for (i=document.editapplicationchatmapping.laptop_product.options.length-1; i >= 1; i--)
						{
						document.editapplicationchatmapping.laptop_product.remove(i);
						}
						for (i=document.editapplicationchatmapping.battery_brand.options.length-1; i >= 1; i--)
						{
						document.editapplicationchatmapping.battery_brand.remove(i);
						}
						for (i=document.editapplicationchatmapping.battery_model.options.length-1; i >= 1; i--)
						{
						document.editapplicationchatmapping.battery_model.remove(i);
						}
						document.editapplicationchatmapping.battery_type.focus();
						}
						else
						{
							for (i=document.editapplicationchatmapping.laptop_model.options.length-1; i >= 1; i--)
							{
							document.editapplicationchatmapping.laptop_model.remove(i);
							}
							for (i=document.editapplicationchatmapping.laptop_product.options.length-1; i >= 1; i--)
							{
							document.editapplicationchatmapping.laptop_product.remove(i);
							}
							for (i=document.editapplicationchatmapping.battery_brand.options.length-1; i >= 1; i--)
							{
							document.editapplicationchatmapping.battery_brand.remove(i);
							}
							for (i=document.editapplicationchatmapping.battery_model.options.length-1; i >= 1; i--)
							{
							document.editapplicationchatmapping.battery_model.remove(i);
							}
							$('#appchatdetails').hide();
							document.editapplicationchatmapping.laptop_name.focus();
						}
                    }
                });
			}
        );
	});
$(document).ready(function(){
	$category1 = $('#laptop_name');
        $category1.change(
            function() 
			{
                $.ajax({
                    type: "GET",
                    url: "../../../../servlet/LaptopApplicationChartMapping?hidWhatToDo=getlaptop_model",
                    data: {laptopname: $category1.val(),batterytype: $category.val() },
                    success: function(data)
					{
						$("#laptop_model").html(data)
						if (data.indexOf("defaultss") >= 0)
						{
							$('#appchatdetails').hide();
							for (i=document.editapplicationchatmapping.laptop_model.options.length-1; i >= 1; i--)
							{
							document.editapplicationchatmapping.laptop_model.remove(i);
							}
							for (i=document.editapplicationchatmapping.laptop_product.options.length-1; i >= 1; i--)
							{
							document.editapplicationchatmapping.laptop_product.remove(i);
							}
							for (i=document.editapplicationchatmapping.battery_brand.options.length-1; i >= 1; i--)
							{
							document.editapplicationchatmapping.battery_brand.remove(i);
							}
							for (i=document.editapplicationchatmapping.battery_model.options.length-1; i >= 1; i--)
							{
							document.editapplicationchatmapping.battery_model.remove(i);
							}
							document.editapplicationchatmapping.battery_type.focus();
							document.editapplicationchatmapping.laptop_name.focus();
						}
						else
						{
							for (i=document.editapplicationchatmapping.laptop_product.options.length-1; i >= 1; i--)
							{
							document.editapplicationchatmapping.laptop_product.remove(i);
							}
							for (i=document.editapplicationchatmapping.battery_brand.options.length-1; i >= 1; i--)
							{
							document.editapplicationchatmapping.battery_brand.remove(i);
							}
							for (i=document.editapplicationchatmapping.battery_model.options.length-1; i >= 1; i--)
							{
							document.editapplicationchatmapping.battery_model.remove(i);
							}
							$('#appchatdetails').hide();
							document.editapplicationchatmapping.laptop_model.focus();
						}
                    }
                });
            }
        );
	});
	$(document).ready(function(){
	$category11 = $('#laptop_model');
        $category11.change(
            function() 
			{
                $.ajax({
                    type: "GET",
                    url: "../../../../servlet/LaptopApplicationChartMapping?hidWhatToDo=getlaptop_product",
                    data: {laptopname: $category1.val(),batterytype: $category.val(),laptopmodel: $category11.val() },
                    success: function(data){
						$("#laptop_product").html(data)
						if (data.indexOf("defaultss") >= 0)
						{
							$('#appchatdetails').hide();
						for (i=document.editapplicationchatmapping.laptop_product.options.length-1; i >= 1; i--)
						{
						document.editapplicationchatmapping.laptop_product.remove(i);
						}
						for (i=document.editapplicationchatmapping.battery_brand.options.length-1; i >= 1; i--)
						{
						document.editapplicationchatmapping.battery_brand.remove(i);
						}
						for (i=document.editapplicationchatmapping.battery_model.options.length-1; i >= 1; i--)
						{
						document.editapplicationchatmapping.battery_model.remove(i);
						}
						document.editapplicationchatmapping.battery_type.focus();
						document.editapplicationchatmapping.laptop_model.focus();
						}
						else
						{
							for (i=document.editapplicationchatmapping.battery_brand.options.length-1; i >= 1; i--)
							{
								document.editapplicationchatmapping.battery_brand.remove(i);
							}
							for (i=document.editapplicationchatmapping.battery_model.options.length-1; i >= 1; i--)
							{
								document.editapplicationchatmapping.battery_model.remove(i);
							}
							$('#appchatdetails').hide();
							document.editapplicationchatmapping.laptop_product.focus();
						}
                    }
                });
            }
        );
	});
$(document).ready(function(){
	$category4 = $('#laptop_product');
        $category4.change(
            function() {
			var splitvalbaa =$category4.val();
                $.ajax({
                    type: "GET",
                    url: "../../../../servlet/LaptopApplicationChartMapping?hidWhatToDo=getlaptopbrandtoedit",
                    data: {batterytype: $category.val(),laptopname: $category1.val(), laptopmodel:$category11.val(),laptopproduct :$category4.val() },
                    success: function(data){
                        $("#battery_brand").html(data)
						if (splitvalbaa == "default")
						{
						for (i=document.editapplicationchatmapping.battery_brand.options.length-1; i >= 1; i--)
						{
						document.editapplicationchatmapping.battery_brand.remove(i);
						}
						for (i=document.editapplicationchatmapping.battery_model.options.length-1; i >= 1; i--)
						{
						document.editapplicationchatmapping.battery_model.remove(i);
						}
						document.editapplicationchatmapping.battery_type.focus();
						$('#appchatdetails').hide();
						document.editapplicationchatmapping.laptop_product.focus();
						}
						else
						{
							for (i=document.editapplicationchatmapping.battery_model.options.length-1; i >= 1; i--)
							{
							document.editapplicationchatmapping.battery_model.remove(i);
							}
							$('#appchatdetails').hide();
								document.editapplicationchatmapping.battery_brand.focus();
						}
	                  }
                });
            }
        );
	});
$(document).ready(function(){
	$category5 = $('#battery_brand');
        $category5.change(
            function() {
                $.ajax({
                    type: "GET",
                    url: "../../../../servlet/LaptopApplicationChartMapping?hidWhatToDo=getlaptopbatterymodeltoedit",
                    data: {batterytype: $category.val(),laptopname: $category1.val(), laptopmodel:$category11.val(), batterybrand: $category5.val(),laptopproduct :$category4.val()},
                    success: function(data){
                        $("#battery_model").html(data)
						if (data.indexOf("defaultss") >= 0)
						{
						for (i=document.editapplicationchatmapping.battery_model.options.length-1; i >= 1; i--)
						{
						document.editapplicationchatmapping.battery_model.remove(i);
						}
						document.editapplicationchatmapping.battery_type.focus();
						$('#appchatdetails').hide();
						document.editapplicationchatmapping.battery_brand.focus();
						}
						else
						{
						document.editapplicationchatmapping.battery_model.focus();
						}
                    }
                });
            }
        );
	});
function funToAddAppChatMap()
{
	var baterytype = document.editapplicationchatmapping.battery_type.value;
	var laptopmake = document.editapplicationchatmapping.laptop_name.value;
	var laptopmodel = document.editapplicationchatmapping.laptop_model.value;
	var laptopproduct = document.editapplicationchatmapping.laptop_product.value;

	var batbrand = document.editapplicationchatmapping.battery_brand.value;
	var batmodel = document.editapplicationchatmapping.battery_model.value;
	if(laptopmodel == "default")
	{
		laptopmodel = "0,default";
	}
	else
	{
		laptopmodel=laptopmodel;
	}

	if (laptopmodel.indexOf(",")!=-1)
	{
		var strlaptopmodel = laptopmodel.split(',');
		var laptopid = strlaptopmodel[0];
		var lapmodel = escape(strlaptopmodel[1]);

		laptopmodel = lapmodel;
	}
	else
	{
		laptopmodel = laptopmodel;
	}

	if (laptopproduct.indexOf(",")!=-1)
	{
		var strlaptopproduct = laptopproduct.split(',');
		var laptoppid = strlaptopproduct[0];
		var lapproduct = escape(strlaptopproduct[1]);

		laptopproduct = lapproduct;
	}
	else
	{
		laptopproduct = laptopproduct;
	}

		
		var xmlhttp= "";
		var resp= "";
		var url = "../../../../servlet/LaptopApplicationChartMapping?hidWhatToDo=getapplicationchartdetailstoedit&batterytype="+baterytype+"&laptopmake="+laptopmake+"&laptopmodel="+laptopmodel+"&laptopproduct="+laptopproduct+"&batterybrand="+batbrand+"&batterymodel="+batmodel;
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
		var url ="../../../../servlet/LaptopApplicationChartMapping?hidWhatToDo=deleteapplicationchartdetails&chkSi="+mapid;
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
		xmlhttp.open("GET",url, true);		
		xmlhttp.send();	

		}

}
function editStatus(mapid,displaystatus)
{
		var xmlhttp= "";
		var resp= "";
		var url ="../../../../servlet/LaptopApplicationChartMapping?hidWhatToDo=updateapplicationchartdetails&chkSi="+mapid+"&displaystatus="+displaystatus;
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

					funToAddAppChatMap();
					document.getElementById("displayappchatmaperrormsg").innerHTML = resp;
					document.getElementById("displayappchatmaperrormsg").innerHTML = xmlhttp.responseText;
				}
			}			
		}
		var agree=confirm("Are You sure want to update status. ");
		if (agree)
		{
		xmlhttp.open("GET",url, true);		
		xmlhttp.send();	

		}

}
function FunReset()
{
	location.href="../../../../jsp/admin/batterystore/laptopBookBattery/deletelaptopapplicationchart.jsp"
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
								<td class="subheading">Delete&nbsp;Laptop&nbsp;Application&nbsp;Chat&nbsp;Mapping</td>
								<td  align="right"><a href="../../../../servlet/AdminBatteryLogin?hidWhatToDo=batteryadminhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
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
										<option value='1,Laptop Batteries'>Laptop Batteries</option>
									</select></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right"><label id="dynamic_title2">Select Laptop Make<font color="FF0000">*</font>&nbsp;:</label></td>
									<td width="59%" class="insidecontent" align="left">
										<select name="laptop_name" id ="laptop_name"class="insidecontent" style='width:180px'>
											<option value="0" >&lt;-&nbsp;Select Laptop Make&nbsp;-&gt;</option>
										</select>
									</td>
								</tr>
								
								<tr>
									<td width="39%" class="insidecontent" align="right"><label id="dynamic_title">Select Laptop Model<font color="FF0000">*</font>&nbsp;:</label></td>
									<td width="59%" class="insidecontent" align="left"><select name="laptop_model" id ="laptop_model" class="insidecontent" style='width:180px'>
									<option value="0" >&lt;-&nbsp;Select Laptop  Model&nbsp;-&gt;</option>
										</select></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right"><label id="dynamic_title">Select Laptop Product<font color="FF0000">*</font>&nbsp;:</label></td>
									<td width="59%" class="insidecontent" align="left"><select name="laptop_product" id ="laptop_product" class="insidecontent" style='width:180px'>
									<option value="0" >&lt;-&nbsp;Select Laptop  Product&nbsp;-&gt;</option>
										</select></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Select Battery Brand<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left"><select name="battery_brand" id ="battery_brand" class="insidecontent" style='width:180px'>
									<option value="0" >&lt;-&nbsp;Select Battery Brand&nbsp;-&gt;</option>
										</select></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Select Battery Model<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left"><select name="battery_model" id ="battery_model" onChange="javascript:funToAddAppChatMap();" class="insidecontent" style='width:180px'>
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