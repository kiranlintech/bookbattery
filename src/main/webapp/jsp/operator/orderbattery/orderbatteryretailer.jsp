<%--
    Document   : batteryadminmain
    Created on : Aug 30, 2013, 4:22:12 PM
    Author     : Sai Krishna Daddala.
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
$(document).ready(function(){
	$category = $('#battery_type');
        $category.change(
        function() {
		var splitvalb =$category.val();
			document.getElementById("displysesmsg").innerHTML="";
			for(i=document.batteryindex.vehicle_model.options.length-1;i>=1;i--)
			{
			document.batteryindex.vehicle_model.remove(i);
			}
			for(i=document.batteryindex.bat_brand.options.length-1;i>=1;i--)
			{
			document.batteryindex.bat_brand.remove(i);
			}
			for(i=document.batteryindex.state.options.length-1;i>=1;i--)
			{
			document.batteryindex.state.remove(i);
			}
			for(i=document.batteryindex.city.options.length-1;i>=1;i--)
			{
			document.batteryindex.city.remove(i);
			}
			for(i=document.batteryindex.area.options.length-1;i>=1;i--)
			{
			document.batteryindex.area.remove(i);
			}
			for(i=document.batteryindex.laptop_model.options.length-1;i>=1;i--)
			{
			document.batteryindex.laptop_model.remove(i);
			}
			for(i=document.batteryindex.laptop_product.options.length-1;i>=1;i--)
			{
			document.batteryindex.laptop_product.remove(i);
			}
		
			document.batteryindex.battery_type.focus();
			/*for(i=document.batteryindex.batt_capacity.options.length-1;i>=1;i--)
			{
			document.batteryindex.batt_capacity.remove(i);
			}
			document.batteryindex.city.options.value=0;
			document.batteryindex.city_names.options.value=0;*/
		if(splitvalb=="Inverter Batteries")
		  {
		    $.ajax({
                    type: "GET",
                    url: "../../../servlet/BatteryHome?hidWhatToDo=getbatterycapacity",
                    data: {batterytype: $category.val(), batterytype: $category.val() },
                    success: function(data){
                        $("#batt_capacity").html(data)
                    }
                });
			$('#vehicle_name').hide();
			$('#vehicle_model').hide();
			$('#bat_brand1').show();
			$('#bat_brand').hide();
			$('#batt_capacity').show();
			$('#laptop_product').hide();
				$('#searchpin').show();
				$('#laptopsbutton').hide();
				$('#laptop_model').hide();
				$('#laptop_name').hide();
				$('#findbatterybutton').show();
				if($('#pincode2').is(':visible')&&$('#pincode3').is(':visible')&&$('#searcharea').is(':visible'))
				{				
					$('#statediv').hide();
					$('#citydiv').hide();
					$('#areadiv').hide();
					$('#searchpin').hide();
				}		
				document.batteryindex.batt_capacity.focus();
		   }
		   else if(splitvalb=="Laptop Batteries")
		  {
				$.ajax
				({
                    type: "GET",
                    url: "../../../servlet/LaptopBatteryDetails?hidWhatToDo=getlaptopname",
                    data: {batterytype: $category.val(), batterytype: $category.val() },
                    success: function(data){
                        $("#laptop_name").html(data)
                    }
                });
				$('#vehicle_name').hide();
				$('#vehicle_model').hide();
				$('#laptop_name').show();
				$('#bat_brand').hide();
				$('#laptop_model').show();
				$('#laptop_product').show();
				$('#statediv').show();
				$('#citydiv').show();
				$('#areadiv').show();
				$('#laptopsbutton').show();
				$('#findbatterybutton').hide();
				$('#bat_brand1').hide();
				$('#batt_capacity').hide();
				$('#searchpin').hide();
				
				if($('#pincode2').is(':visible'))
				{					
					$('#pincode2').hide();
				}
				if($('#pincode3').is(':visible'))
				{
					
					$('#pincode3').hide();
				}
				if($('#searcharea').is(':visible'))
				{
					
					$('#searcharea').hide();
				}
				document.batteryindex.laptop_name.focus();
		   }
		   else if(splitvalb=="Inverter")
		   {
				location.href="../../../jsp/operator/inverter/orderinverter.jsp"
		   }
		  else
		   {
                $.ajax({
                    type: "GET",
                    url: "../../../servlet/BatteryHome?hidWhatToDo=getvehiclename",
                    data: {batterytype: $category.val() },
                    success: function(data){
						$('#vehicle_name').show();
						$('#vehicle_model').show();
						$('#bat_brand').show();
						$('#bat_brand1').hide();
						$('#batt_capacity').hide();
						$('#laptop_product').hide();
						$('#searchpin').show();
						$('#laptopsbutton').hide();
						$('#laptop_model').hide();
						$('#laptop_name').hide();
						$('#findbatterybutton').show();
						if($('#pincode2').is(':visible')&&$('#pincode3').is(':visible')&&$('#searcharea').is(':visible'))
						{				
							$('#statediv').hide();
							$('#citydiv').hide();
							$('#areadiv').hide();
							$('#searchpin').hide();
						}
							if(data.indexOf("defaultss")>=0)
							{
							 $("#vehicle_name").html(data)
							document.batteryindex.battery_type.focus();
							}
							else
							{
                        $("#vehicle_name").html(data)
						document.batteryindex.vehicle_name.focus();
							}
					}
                });
			}
            }
        );
	});
	$(document).ready(function()
{
	$category25 = $('#laptop_name');
        $category25.change(
            function() 
			{
				document.getElementById("displysesmsg").innerHTML="";
					for(i=document.batteryindex.laptop_model.options.length-1;i>=1;i--)
					{
					document.batteryindex.laptop_model.remove(i);
					}
					for(i=document.batteryindex.laptop_product.options.length-1;i>=1;i--)
					{
					document.batteryindex.laptop_product.remove(i);
					}
					for(i=document.batteryindex.state.options.length-1;i>=1;i--)
					{
					document.batteryindex.state.remove(i);
					}
					for(i=document.batteryindex.city.options.length-1;i>=1;i--)
					{
					document.batteryindex.city.remove(i);
					}
					for(i=document.batteryindex.area.options.length-1;i>=1;i--)
					{
					document.batteryindex.area.remove(i);
					}		
				document.batteryindex.laptop_name.focus();
			
                $.ajax({
                    type: "GET",
                    url: "../../../servlet/LaptopBatteryDetails?hidWhatToDo=getlaptop_model",
                    data: {laptopname: $category25.val(), batterytype: $category.val()},
					//data: {batterytype: $category.val() },
                    success: function(data)
					{
						if(data.indexOf("defaultss")>=0)
						{
							$("#laptop_model").html(data)
							document.batteryindex.laptop_name.focus();
						}
						else
						{
							$("#laptop_model").html(data)
							document.batteryindex.laptop_model.focus();
						}
                    }
                });
            }
        );
	});
	$(document).ready(function()
	{
		$categoryproduct = $('#laptop_model');
        $categoryproduct.change(
            function() 
			{
				document.getElementById("displysesmsg").innerHTML="";
				for(i=document.batteryindex.laptop_product.options.length-1;i>=1;i--)
				{
				document.batteryindex.laptop_product.remove(i);
				}
				for(i=document.batteryindex.state.options.length-1;i>=1;i--)
				{
				document.batteryindex.state.remove(i);
				}
				for(i=document.batteryindex.city.options.length-1;i>=1;i--)
				{
				document.batteryindex.city.remove(i);
				}
				for(i=document.batteryindex.area.options.length-1;i>=1;i--)
				{
				document.batteryindex.area.remove(i);
				}	
				document.batteryindex.laptop_model.focus();
			
                $.ajax({
                    type: "GET",
                    url: "../../../servlet/LaptopBatteryDetails?hidWhatToDo=getlaptop_product",
                    data: {laptopname: $category25.val(), batterytype: $category.val(), laptopmodel: $categoryproduct.val()},
					//data: {batterytype: $category.val() },
                    success: function(data)
					{
						if(data.indexOf("defaultss")>=0)
						{
							$("#laptop_product").html(data)
							document.batteryindex.laptop_model.focus();
						}
						else
						{
							$("#laptop_product").html(data)
							document.batteryindex.laptop_product.focus();
						}
                    }
                });
            }
        );
	});
$(document).ready(function(){
	$category2 = $('#vehicle_name');
        $category2.change(
            function() {
			document.getElementById("displysesmsg").innerHTML="";
			for(i=document.batteryindex.bat_brand.options.length-1;i>=1;i--)
			{
			document.batteryindex.bat_brand.remove(i);
			}
			for(i=document.batteryindex.state.options.length-1;i>=1;i--)
			{
			document.batteryindex.state.remove(i);
			}
			for(i=document.batteryindex.city.options.length-1;i>=1;i--)
			{
			document.batteryindex.city.remove(i);
			}
			for(i=document.batteryindex.area.options.length-1;i>=1;i--)
			{
			document.batteryindex.area.remove(i);
			}
			document.batteryindex.vehicle_name.focus();
			
                $.ajax({
                    type: "GET",
                    url: "../../../servlet/BatteryHome?hidWhatToDo=getvehicle_model",
                    data: {vehiclename: $category2.val(), batterytype: $category.val()},
					//data: {batterytype: $category.val() },
                    success: function(data){
						if(data.indexOf("defaultss")>=0)
						{
                        $("#vehicle_model").html(data)
						document.batteryindex.vehicle_name.focus();
						}
						else
						{
						 $("#vehicle_model").html(data)
						document.batteryindex.vehicle_model.focus();
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
											var keyword=document.getElementById("brandkeyword").value;

					var splitval =$category3.val();
					
						for(i=document.batteryindex.bat_brand.options.length-1;i>=1;i--)
						{
						document.batteryindex.bat_brand.remove(i);
						}
						for(i=document.batteryindex.state.options.length-1;i>=1;i--)
						{
						document.batteryindex.state.remove(i);
						}
						for(i=document.batteryindex.city.options.length-1;i>=1;i--)
						{
						document.batteryindex.city.remove(i);
						}
						for(i=document.batteryindex.area.options.length-1;i>=1;i--)
						{
						document.batteryindex.area.remove(i);
						}
						document.batteryindex.vehicle_model.focus();
					if(splitval == "default")
						{
						for(i=document.batteryindex.bat_brand.options.length-1;i>=1;i--)
						{
						document.batteryindex.bat_brand.remove(i);
						}
						for(i=document.batteryindex.state.options.length-1;i>=1;i--)
						{
						document.batteryindex.state.remove(i);
						}
						for(i=document.batteryindex.city.options.length-1;i>=1;i--)
						{
						document.batteryindex.city.remove(i);
						}
						for(i=document.batteryindex.area.options.length-1;i>=1;i--)
						{
						document.batteryindex.area.remove(i);
						}
						document.batteryindex.vehicle_model.focus();
					}
					else
					{
				 $.ajax({
                    type: "GET",
                    url: "../../../servlet/BatteryHome?hidWhatToDo=getbat_brand",
                    data: {vehiclemodel: $category3.val(), keyword:keyword },
                    success: function(data){
						if(data.indexOf("defaultss")>=0)
						{
                        $("#bat_brand").html(data)
						document.batteryindex.vehicle_model.focus();
						}
						else
						{
						 $("#bat_brand").html(data)
						document.batteryindex.bat_brand.focus();
						}
                    }
                });
				}
            }
        );
	});
$(document).ready(function(){
	$category4 = $('#batt_capacity');
	        $category4.change(
            function() {
				document.getElementById("displysesmsg").innerHTML="";
											var keyword=document.getElementById("brandkeyword").value;

					var splitvalban =$category4.val();
					
						for(i=document.batteryindex.bat_brand1.options.length-1;i>=1;i--)
						{
						document.batteryindex.bat_brand1.remove(i);
						}
						for(i=document.batteryindex.state.options.length-1;i>=1;i--)
						{
						document.batteryindex.state.remove(i);
						}
						for(i=document.batteryindex.city.options.length-1;i>=1;i--)
						{
						document.batteryindex.city.remove(i);
						}
						for(i=document.batteryindex.area.options.length-1;i>=1;i--)
						{
						document.batteryindex.area.remove(i);
						}
						document.batteryindex.batt_capacity.focus();
						if(splitvalban == "default")
						{
						for(i=document.batteryindex.bat_brand1.options.length-1;i>=1;i--)
						{
						document.batteryindex.bat_brand1.remove(i);
						}
						for(i=document.batteryindex.state.options.length-1;i>=1;i--)
						{
						document.batteryindex.state.remove(i);
						}
						for(i=document.batteryindex.city.options.length-1;i>=1;i--)
						{
						document.batteryindex.city.remove(i);
						}
						for(i=document.batteryindex.area.options.length-1;i>=1;i--)
						{
						document.batteryindex.area.remove(i);
						}
						document.batteryindex.batt_capacity.focus();
						}
						else
						{
					
			    $.ajax({
                    type: "GET",
                    url: "../../../servlet/BatteryHome?hidWhatToDo=getbat_brand",
                    data: {vehiclemodel: $category4.val(), keyword:keyword },
                    success: function(data){
						if(data.indexOf("defaultss")>=0)
						{
                        $("#bat_brand1").html(data)
						document.batteryindex.batt_capacity.focus();
						}
						else
						{
						 $("#bat_brand1").html(data)
						document.batteryindex.bat_brand1.focus();
						}
                    }
                });
			}
            }
        );
	});
	$(document).ready(function(){
	$categorybat = $('#bat_brand');
	        $categorybat.change(
            function() {
				document.getElementById("displysesmsg").innerHTML="";
					var splitvalcity =$categorybat.val();
						for(i=document.batteryindex.state.options.length-1;i>=1;i--)
						{
						document.batteryindex.state.remove(i);
						}
						for(i=document.batteryindex.city.options.length-1;i>=1;i--)
						{
						document.batteryindex.city.remove(i);
						}
						for(i=document.batteryindex.area.options.length-1;i>=1;i--)
						{
						document.batteryindex.area.remove(i);
						}
						document.batteryindex.bat_brand.focus();
						if(splitvalcity == "default")
						{
						for(i=document.batteryindex.state.options.length-1;i>=1;i--)
						{
						document.batteryindex.state.remove(i);
						}
						for(i=document.batteryindex.city.options.length-1;i>=1;i--)
						{
						document.batteryindex.city.remove(i);
						}
						for(i=document.batteryindex.area.options.length-1;i>=1;i--)
						{
						document.batteryindex.area.remove(i);
						}
						document.batteryindex.bat_brand.focus();
						}
						else
						{
			
				$.ajax({
                    type: "GET",
                    url: "../../../servlet/BatteryHome?hidWhatToDo=getstate",
                    data: {brands: $categorybat.val() },
                    success: function(data){
						if(data.indexOf("defaultss")>=0)
						{
                        $("#state").html(data)
						document.batteryindex.state.focus();
						}
						else
						{
						 $("#state").html(data)
						document.batteryindex.state.focus();
						}
                    }
                });
				}
            }
        );
	});
		$(document).ready(function(){
	$category21 = $('#bat_brand1');
	        $category21.change(
            function() {
				document.getElementById("displysesmsg").innerHTML="";
					var splitvalcity =$category21.val();
						for(i=document.batteryindex.state.options.length-1;i>=1;i--)
						{
						document.batteryindex.state.remove(i);
						}
						for(i=document.batteryindex.city.options.length-1;i>=1;i--)
						{
						document.batteryindex.city.remove(i);
						}
						for(i=document.batteryindex.area.options.length-1;i>=1;i--)
						{
						document.batteryindex.area.remove(i);
						}
						document.batteryindex.bat_brand.focus();
						if(splitvalcity == "default")
						{
						for(i=document.batteryindex.state.options.length-1;i>=1;i--)
						{
						document.batteryindex.state.remove(i);
						}
						for(i=document.batteryindex.city.options.length-1;i>=1;i--)
						{
						document.batteryindex.city.remove(i);
						}
						for(i=document.batteryindex.area.options.length-1;i>=1;i--)
						{
						document.batteryindex.area.remove(i);
						}
						document.batteryindex.bat_brand.focus();
						}
						else
						{
			
				$.ajax({
                    type: "GET",
                    url: "../../../servlet/BatteryHome?hidWhatToDo=getstate",
                    data: {brands: $category21.val() },
                    success: function(data){
						if(data.indexOf("defaultss")>=0)
						{
                        $("#state").html(data)
						document.batteryindex.state.focus();
						}
						else
						{
						 $("#state").html(data)
						document.batteryindex.state.focus();
						}
					}
                });
				}
            }
        );
	});
		$(document).ready(function(){
	$category212 = $('#laptop_product');
	        $category212.change(
            function() {
				document.getElementById("displysesmsg").innerHTML="";
					var splitvalcity =$category212.val();
					
						for(i=document.batteryindex.state.options.length-1;i>=1;i--)
						{
						document.batteryindex.state.remove(i);
						}
						for(i=document.batteryindex.city.options.length-1;i>=1;i--)
						{
						document.batteryindex.city.remove(i);
						}
						for(i=document.batteryindex.area.options.length-1;i>=1;i--)
						{
						document.batteryindex.area.remove(i);
						}
						document.batteryindex.laptop_product.focus();
						if(splitvalcity == "default")
						{
							for(i=document.batteryindex.state.options.length-1;i>=1;i--)
							{
							document.batteryindex.state.remove(i);
							}
							for(i=document.batteryindex.city.options.length-1;i>=1;i--)
							{
							document.batteryindex.city.remove(i);
							}
							for(i=document.batteryindex.area.options.length-1;i>=1;i--)
							{
							document.batteryindex.area.remove(i);
							}
							document.batteryindex.laptop_product.focus();
						}
						else
						{
			
							$.ajax({
								type: "GET",
								url: "../../../servlet/BatteryHome?hidWhatToDo=getstate",
								data: {laptop_product: $category212.val() },
								success: function(data){
									if(data.indexOf("defaultss")>=0)
									{
										$("#state").html(data)
										document.batteryindex.state.focus();
									}
									else
									{
										$("#state").html(data)
										document.batteryindex.state.focus();
									}
								}
							});
						}
            }
        );
	});

	$(document).ready(function(){
	$category5 = $('#state');
	        $category5.change(
            function() {
				document.getElementById("displysesmsg").innerHTML="";
					var splitvalcity =$category5.val();
					
						for(i=document.batteryindex.city.options.length-1;i>=1;i--)
						{
						document.batteryindex.city.remove(i);
						}
						for(i=document.batteryindex.area.options.length-1;i>=1;i--)
						{
						document.batteryindex.area.remove(i);
						}
						document.batteryindex.state.focus();
						if(splitvalcity == "default")
						{
						for(i=document.batteryindex.city.options.length-1;i>=1;i--)
						{
						document.batteryindex.city.remove(i);
						}
						for(i=document.batteryindex.area.options.length-1;i>=1;i--)
						{
						document.batteryindex.area.remove(i);
						}
						document.batteryindex.state.focus();
						}
						else
						{
			
				$.ajax({
                    type: "GET",
                    url: "../../../servlet/BatteryHome?hidWhatToDo=getcity",
                    data: {state: $category5.val() },
                    success: function(data){
						if(data.indexOf("defaultss")>=0)
						{
                        $("#city").html(data)
						document.batteryindex.city.focus();
						}
						else
						{
						 $("#city").html(data)
						document.batteryindex.city.focus();
						}
					}
                });
				}
            }
        );
	});
$(document).ready(function(){
	$category5 = $('#state');
	$category6 = $('#city');
	        $category6.change(
            function() {
				document.getElementById("displysesmsg").innerHTML="";
					var splitvalarea =$category6.val();
					
						for(i=document.batteryindex.area.options.length-1;i>=1;i--)
						{
						document.batteryindex.area.remove(i);
						}
						document.batteryindex.city.focus();
						if(splitvalarea == "default")
						{
						for(i=document.batteryindex.area.options.length-1;i>=1;i--)
						{
						document.batteryindex.area.remove(i);
						}
						document.batteryindex.city.focus();
						}
						else
						{
					
				$.ajax({
                    type: "GET",
                    url: "../../../servlet/BatteryHome?hidWhatToDo=getarea",
                    data: {city: $category6.val() },
                    success: function(data){
						if(data.indexOf("defaultss")>=0)
						{
                        $("#area").html(data)
						document.batteryindex.area.focus();
						}
						else
						{
						 $("#area").html(data)
						document.batteryindex.area.focus();
						}
                    }
                });
			
			/** code starts here to fetch retailers **/
				$.ajax({
                    type: "GET",
                    url: "../../../servlet/OperatorBatteryDetailsRetailer?hidWhatToDo=getretailers",
                    data: {state: $category5.val(),city: $category6.val() },
                    success: function(data){
						if(data.indexOf("defaultss")>=0)
						{
                        $("#retailer").html(data)
						}
						else
						{
						 $("#retailer").html(data)
						
						}
                    }
                });
			/** code ends here to fetch retailers **/

			  }
            }
        );
	});
$(document).ready(function(){
var batteryurl=window.location.search;
	if(batteryurl=="?nametype=payment")
	{
		$('#paymentdiv').show();
	}
	else if(batteryurl=="?nametype=shipping")
	{
		$('#shippingdiv').show();
	}
	else if(batteryurl=="?nametype=privacy")
	{
		$('#privacydiv').show();
	}
	else if(batteryurl=="?nametype=refund")
	{
		$('#refunddiv').show();
	}
	else
	{

	}
	});
function funToGetbatterydetails()
{
	var batterytype = document.batteryindex.battery_type.value;      
	var vehiclename = document.batteryindex.vehicle_name.value;
	var vehiclemodel = escape(document.batteryindex.vehicle_model.value);
	var batterybrand = document.batteryindex.bat_brand.value;
	var batterybrand1 = document.batteryindex.bat_brand1.value;
	var batterycapty = document.batteryindex.batt_capacity.value;
	var state = document.batteryindex.state.value;
	var city = document.batteryindex.city.value;
	var area = document.batteryindex.area.value;
	var pincode = document.batteryindex.pincode.value;
	var retailer = document.batteryindex.retailer.value;

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
	if(batterytype == "Inverter Batteries")
	{
	if(document.batteryindex.battery_type.value == 0)
	{
		errMsg ="<font color='#ff3333'>Please Select \'Battery Type\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.batteryindex.battery_type.focus();
		return ;
	}
	if(document.batteryindex.batt_capacity.value == 0 || batterycapty=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'Battery Capacity\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.batteryindex.batt_capacity.focus();
		return ;
	}
	if(document.batteryindex.bat_brand1.value == 0 || batterybrand1=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'Battery Brand\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.batteryindex.bat_brand1.focus();
		return ;
	}
	vehiclename = "";
	vehiclemodel = "";
	batterybrand = batterybrand1;
	}
	else
	{
	if(document.batteryindex.battery_type.value == 0)
	{
		errMsg ="<font color='#ff3333'>Please Select \'Battery Type\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.batteryindex.battery_type.focus();
		return ;
	}
	if(document.batteryindex.vehicle_name.value == 0 || vehiclename=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'Make\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.batteryindex.vehicle_name.focus();
		return ;
	}
	if(document.batteryindex.vehicle_model.value == 0 || vehiclemodel=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'Model\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.batteryindex.vehicle_model.focus();
		return ;
	}
	vehiclename = vehiclename;
	vehiclemodel = vehiclemodel;
	batterybrand = batterybrand;
	if(document.batteryindex.bat_brand.value == 0 || batterybrand=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'Battery Brand\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.batteryindex.bat_brand.focus();
		return ;
	}
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
			document.batteryindex.pincode.focus();
			return ;
		}
		if(document.batteryindex.state.value == 0 || state=="default")
		{
			errMsg ="<font color='#ff3333'>Please Select \'State\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.batteryindex.state.focus();
			return ;
		}
		if(document.batteryindex.city.value == 0 || city=="default")
		{
			errMsg ="<font color='#ff3333'>Please Select \'Location\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.batteryindex.city.focus();
			return ;
		}
		if(document.batteryindex.area.value == 0 || area=="default")
		{
			errMsg ="<font color='#ff3333'>Please Select \'Area\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.batteryindex.area.focus();
			return ;
		}
		if(document.batteryindex.retailer.value == 0 || retailer=="default")
		{
			errMsg ="<font color='#ff3333'>Please Select \'Retailer\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.batteryindex.retailer.focus();
			return ;
		}
	}
	else
	{
		if(document.batteryindex.pincode.value == "" || pincode=="default")
		{
			errMsg ="<font color='#ff3333'>Please Enter \'Pincode\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.batteryindex.pincode.focus();
			return ;
		}
		var pincoderegex=/^[0-9]{4,6}$/;
		if(!pincode.match(pincoderegex))
		{
			errMsg ="<font color='#ff3333'>Please Enter Valid \'Pincode\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.batteryindex.pincode.focus();
			return ;
		}
		if(document.batteryindex.retailer.value == 0 || retailer=="default")
		{
			errMsg ="<font color='#ff3333'>Please Select \'Retailer\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.batteryindex.retailer.focus();
			return ;
		}
	}
	
	document.batteryindex.method="post";
	document.batteryindex.action="../../../servlet/OperatorBatteryDetailsRetailer?hidWhatToDo=getbatdetails&batterytype="+batterytype+"&vehiclename="+vehiclename+"&vehiclemodel="+vehiclemodel+"&batterybrand="+batterybrand+"&batterycapty="+batterycapty+"&state="+state+"&city="+city+"&area="+area+"&pincode="+pincode+"&retailer="+retailer;
	//alert(document.batteryindex.action);
	document.batteryindex.submit();
}
function getLaptopBatteryDetails()
{
	var batterytype = document.batteryindex.battery_type.value;      
	var laptopname = document.batteryindex.laptop_name.value;
	var laptopmodel = escape(document.batteryindex.laptop_model.value);
	
	var laptopproduct = escape(document.batteryindex.laptop_product.value);
	var state = document.batteryindex.state.value;
	var city = document.batteryindex.city.value;
	var area = document.batteryindex.area.value;

	if(document.batteryindex.battery_type.value == 0)
	{
		errMsg ="<font color='#ff3333'>Please Select \'Battery Type\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.batteryindex.battery_type.focus();
		return ;
	}
	if(document.batteryindex.laptop_name.value == 0 || laptopname=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'Laptop Make\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.batteryindex.laptop_name.focus();
		return ;
	}
	if(document.batteryindex.laptop_model.value == 0 || laptopmodel=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'Laptop Model\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.batteryindex.laptop_model.focus();
		return ;
	}
	
	if(document.batteryindex.laptop_product.value == 0 || laptopproduct=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'Laptop Product\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.batteryindex.laptop_product.focus();
		return ;
	}
	if(document.batteryindex.state.value == 0 || state=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'State\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.batteryindex.state.focus();
		return ;
	}
	if(document.batteryindex.city.value == 0 || city=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'Location\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.batteryindex.city.focus();
		return ;
	}
	if(document.batteryindex.area.value == 0 || area=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'Area\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.batteryindex.area.focus();
		return ;
	}
	document.batteryindex.method="post";
	document.batteryindex.action="../../../servlet/OperatorLaptopBatteryDetails?hidWhatToDo=getlaptopbatterydetails&batterytype="+batterytype+"&laptopname="+laptopname+"&laptopmodel="+laptopmodel+"&laptopproduct="+laptopproduct+"&state="+state+"&city="+city+"&area="+area;
	//alert(document.batteryindex.action);
	document.batteryindex.submit();

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
	document.batteryindex.area.focus();
}
function changetopincode()
{
	//alert("calling pincode");
	document.getElementById("displysesmsg").innerHTML="";
	document.batteryindex.pincode.value="";
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
-->
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
<form name="batteryindex" method="post" ENCTYPE="multipart/form-data">
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
								<td class="subheading">Order&nbsp;Battery</td>
								<td  align="right"><a href="../../../servlet/OperatorLogin?hidWhatToDo=batteryoperatorhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
							</tr>
							<tr><td height="25"></td></tr>
							<tr><td align="center" class="insidecontentbat"><div id="displysesmsg"></div></td></tr>
							<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
							<tr>
													<td width="25%" align="left">
														<div class="styled-select">
															<select  name='battery_type' id='battery_type' class="insidecontent" style='width:155px;  bgcolor:#FFF;' >
																<option style="background:#FFF" value='' class='topindex1'>Select&nbsp;Battery&nbsp;Type</option>
															<option style="background:#FFF" value="Car Batteries" >Car Batteries</option>
																<option style="background:#FFF" value="Inverter Batteries" >Inverter Batteries</option>
																<option style="background:#FFF" value="Bike Batteries" >Bike Batteries</option>
																<option style="background:#FFF" value="Bus Batteries" >Bus Batteries</option>
																<option style="background:#FFF" value="Tractor Batteries" >Tractor Batteries</option>
																<option style="background:#FFF" value="Truck Batteries" >Truck Batteries</option>
																<option style="background:#FFF" value="Three Wheeler Batteries" >Three Wheeler Batteries</option>
																<option style="background:#FFF" value="Inverter" >Inverter</option>
																<option style="background:#FFF" value="Laptop Batteries" >Laptop Batteries</option>
																<option style="background:#FFF" value="Special Vehicle Batteries" >Special Vehicle Batteries</option>
																<option style="background:#FFF" value="Genset Batteries" >Genset Batteries</option>
																<option style="background:#FFF" value="Crane Batteries" >Crane Batteries</option>
																<option style="background:#FFF" value="Roller Batteries" >Roller Batteries</option>
																<option style="background:#FFF" value="Loader Batteries" >Loader Batteries</option>
																<option style="background:#FFF" value="Dozer Batteries" >Dozer Batteries</option>
																<option style="background:#FFF" value="Excavator Batteries" >Excavator Batteries</option>
																<option style="background:#FFF" value="Tyre Handler Batteries" >Tyre Handler Batteries</option>
																<option style="background:#FFF" value="Hydraulic Shovel Batteries" >Hydraulic Shovel Batteries</option>
																<option style="background:#FFF" value="Harvestor Batteries" >Harvestor Batteries</option>
																<option style="background:#FFF" value="Generator Batteries" >Generator Batteries</option>
																<option style="background:#FFF" value="Compactor Batteries" >Compactor Batteries</option>
																<option style="background:#FFF" value="Telescopic Handler Batteries" >Telescopic Handler Batteries</option>
																<option style="background:#FFF" value="Forwarder Batteries" >Forwarder Batteries</option>
																<option style="background:#FFF" value="Wheeled Harvester Batteries" >Wheeled Harvester Batteries</option>
																<option style="background:#FFF" value="Minibus Batteries" >Minibus Batteries</option>
																<option style="background:#FFF" value="Dumper Batteries" >Dumper Batteries</option>
																<option style="background:#FFF" value="Construction Equipment Batteries" >Construction Equipment Batteries</option>
																<option style="background:#FFF" value="Hydralic Excavator Batteries" >Hydralic Excavator Batteries</option>
															</select>
														</div>
													</td>
													<td width="25%" align="left">
														<div class="styled-select">
															<select name="vehicle_name" id ="vehicle_name" class="insidecontent" style='width:155px'>
																<option style="background:#FFF" value="0" class='topindex1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;Make</option>
															</select>
															<select name="batt_capacity" id="batt_capacity" class="insidecontent"  style='width:155px;display:none;'>
																<option style="background:#FFF" value="0" >&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;Capacity</option>
															</select>
															<select name="laptop_name" id="laptop_name" class="insidecontent"  style='width:155px;display:none;'>
																<option style="background:#FFF" value="0" >Select&nbsp;Laptop&nbsp;Make</option>
															</select>
														</div>
													</td>
													<td width="25%" align="left">
														<div class="styled-select" >
															<select name="vehicle_model" id ="vehicle_model" class="insidecontent" style='width:155px;'>
																<option style="background:#FFF" value="0" class='topindex1' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;Model</option>
															</select>
															<select name="bat_brand1" id ="bat_brand1" class="insidecontent" style='width:155px;display:none;' >
																<option style="background:#FFF" value="0" class='topindex1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;Brand</option>
															</select>
															<select name="laptop_model" id ="laptop_model" class="insidecontent" style='width:155px;display:none;'>
																<option style="background:#FFF" value="0" class='topindex1' >Select&nbsp;Laptop&nbsp;Model</option>
															</select>
														</div>
													</td>
													<td width="25%" align="left">
														<div class="styled-select">
															<select name="bat_brand" id ="bat_brand" class="insidecontent" style='width:155px;' >
																<option style="background:#FFF" value="0" class='topindex1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;Brand</option>
															</select>
															<select name="laptop_product" id ="laptop_product" class="insidecontent" style='width:160px;display:none;'>
																<option style="background:#FFF" value="0" class='topindex1' >Select&nbsp;Laptop&nbsp;Product</option>
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
																	<option style="background:#FFF" value="0" class='topindex1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;Location</option>
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
													<div id='laptopsbutton' style='display:none;'>
														<a href="javascript:getLaptopBatteryDetails();"><img src="../../../images/findbttry.png" alt="Order car battery online/inverter battery online for best discounted price with valid warranty period and install car BookBattery or inverter BookBattery or bike BookBattery with free installation charges." border="0" width="155" height="33" /></a>
													</div>
													<div id='findbatterybutton'>
														<a href="javascript:funToGetbatterydetails();"><img src="../../../images/findbttry.png" alt="Order car battery online/inverter battery online for best discounted price with valid warranty period and install car BookBattery or inverter BookBattery or bike BookBattery with free installation charges." border="0" width="155" height="35" /></a>
													</div>
													</td>
												</tr>
												<tr id="searchpin">
													<td></td><td></td><td align="center" >
														<a href="javascript:changetopincode();" class="footerbat">Order&nbsp;With&nbsp;Pin&nbsp;Code</a>
													</td>
												</tr>
												<tr id="searcharea"><td></td><td></td><td align="left" ><a href="javascript:changetoarea();" class="footerbat">Order&nbsp;With&nbsp;State,City,Area</a></td></tr>
												<tr>
													<td>
														<div class="styled-select">
															<select name="retailer" id ="retailer" class="insidecontent"  style='width:155px;' >
																<option style="background:#FFF" value="0"  class='topindex1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;Retailer</option>
															</select>
														</div>
													</td>
													<td></td>
													<td align="center" >
														
													</td>
												</tr>

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
<input type="hidden" name="backkeyword" id='backkeyword' value="carbattery">
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