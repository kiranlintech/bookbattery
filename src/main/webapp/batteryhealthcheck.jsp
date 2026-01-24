<%-- 
Document   : batteryhealthcheck.jsp
Created on : Aug 23, 2013, 10:10:12 AM
Author     : Sai Krishna Daddala
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,com.ngit.javabean.loglevel.LogLevel,javax.servlet.ServletContext,java.util.Properties,java.io.FileInputStream"%>
<%response.setHeader("Pragma","no-cache"); response.setHeader("Cache-Control","no-store"); response.setHeader("Expires","0"); response.setDateHeader("Expires",-1); %>
<%
	String nametype =(session.getAttribute("nametype") != null)?(String)session.getAttribute("nametype"):""; 
	Properties propsMOPConfig = new Properties();
	ServletContext context = getServletContext();
	FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
	propsMOPConfig.load(fin1); 
	fin1.close(); 
	String publicUrl = (propsMOPConfig.getProperty("publicUrl")!=null)?propsMOPConfig.getProperty("publicUrl"):"";
	String baseURL = (propsMOPConfig.getProperty("baseURL")!=null)?propsMOPConfig.getProperty("baseURL"):"";
	String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?propsMOPConfig.getProperty("baseurldirectory"):"";

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=baseURL%>"></base>
<meta http-equiv="Refresh" content="0; url=<%=publicUrl%>/bookbattery/">
<title>Free Battery Health Check for Car,Bike,Inverter Batteries� BookBattery</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="./images/favicon.png" type="image/x-icon">
<meta name='Title' content='Free Battery Health Check for Car,Bike,Inverter Batteries� BookBattery' />
<meta name='description' content="Order for Free Battery Health Check for your car, bike, laptop, inverters BookBattery or home inverters, etc.. at BookBattery, Trained Service Engineer will come to your place to check your battery." />
<meta name='keywords' content='Free Battery health Check, Battery Health Check Free, Car Battery Health Check, Battery Health Check, Checking Battery Health.' />

<!-- On load images starts  -->

<style>
.no-js #loader { display: none;  }
.js #loader { display: block; position: absolute; left: 100px; top: 0; }
.se-pre-con {
	position: fixed;
	left: 0px;
	top: 0px;
	width: 100%;
	height: 100%;
	z-index: 9999;
	background: url(/bookbattery/images/loader/BookBatteryload.gif) center no-repeat #fff;
content: "Joe's Task:"
}
</style>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5.2/jquery.min.js"></script>
<script>
	//paste this code under head tag or in a seperate js file.
	// Wait for window load
	$(window).load(function() {
		// Animate loader off screen
		$(".se-pre-con").fadeOut("slow");;
	});
</script>

<!-- On load images ends  -->
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="Expires" CONTENT="0"/>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache"/>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-store"/>
<link href="./css/bookbattery.css" rel="stylesheet" type="text/css" />
<script src="./js/jquery-1.3.2.min.js"></script>
<script language="JavaScript" src="./js/popupfrontend.js" ></script>

<script language="JavaScript" src="./js/jquery-1.8.2.js" ></script>
<style type='text/css'>
/*<![CDATA[*/
table{font-size:inherit;font:100%;}pre,code,kbd,samp,tt{font-family:monospace;*font-size:108%;line-height:100%;}
td.loading_image
{
	background:  url('./images/loader.gif');width:100px; /* use you own image size; */ 
	height: 40px; /* use you own image size; */ 
	background-repeat: no-repeat; 
	background-position: center; 
	vertical-align: top;
}
div.borderdiv {padding:7px; border:1px solid #585858; -moz-border-radius: 8px; -webkit-border-radius: 8px;border-radius: 8px 8px; width:96%; } 

.button_example
{
	border:0px; -webkit-border-radius: 3px; -moz-border-radius: 3px;border-radius: 3px;font-size:12px;font-family:arial, helvetica, sans-serif; padding: 10px 10px 10px 10px; text-decoration:none; display:inline-block;text-shadow: -1px -1px 0 rgba(0,0,0,0.3);font-weight:bold; color: #FFFFFF;
	 background-color: #FF8c00; background-image: -webkit-gradient(linear, left top, left bottom, from(#FF8c00), to(#FF8c00));
	 background-image: -webkit-linear-gradient(top, #FF8c00, #FF8c00);
	 background-image: -moz-linear-gradient(top, #FF8c00, #FF8c00);
	 background-image: -ms-linear-gradient(top, #FF8c00, #FF8c00);
	 background-image: -o-linear-gradient(top, #FF8c00, #FF8c00);
	 background-image: linear-gradient(to bottom, #FF8c00, #FF8c00);filter:progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr=#FF8c00, endColorstr=#FF8c00);
 }

.divmobile{width:100%;left:0%;top:30%;font: 16px/22px; text-align:left;padding: 10px;display: none; z-index:9999; background-color: #232323; border-width: 0px 0px 0px 0px;}


.8/5/2014{left:45.5%;top:85%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left; border:1px solid #FF8C00;position:absolute;padding: 1px;display: none;z-index:60;}

/*]]>*/
</style>
<script type="text/javascript">
//<![CDATA[

$(function () {
    scrollTo(($(document).width() - $(window).width()) / 0, 170);
});
$(document).ready(function(){
	$('#pincode2').hide();
	$('#pincode3').hide();
	$('#searcharea').hide();
	$category = $('#battery_type');
        $category.change(
        function() {
		var splitvalb =$category.val();
			document.getElementById("displysesmsg").innerHTML="";
			for(i=document.batteryhealthindex.vehicle_model.options.length-1;i>=1;i--)
			{
			document.batteryhealthindex.vehicle_model.remove(i);
			}
			for(i=document.batteryhealthindex.bat_brand.options.length-1;i>=1;i--)
			{
			document.batteryhealthindex.bat_brand.remove(i);
			}
			for(i=document.batteryhealthindex.bat_brand1.options.length-1;i>=1;i--)
			{
				document.batteryhealthindex.bat_brand1.remove(i);
			}
			for(i=document.batteryhealthindex.state.options.length-1;i>=1;i--)
			{
			document.batteryhealthindex.state.remove(i);
			}
			for(i=document.batteryhealthindex.city.options.length-1;i>=1;i--)
			{
			document.batteryhealthindex.city.remove(i);
			}
			for(i=document.batteryhealthindex.area.options.length-1;i>=1;i--)
			{
			document.batteryhealthindex.area.remove(i);
			}
		
			document.batteryhealthindex.battery_type.focus();
		if(splitvalb=="Inverter Batteries")
		  {
			$('#img2').show();
			
		    $.ajax({
                    type: "GET",
                    url: "./servlet/BatteryHome?hidWhatToDo=getbatterycapacity",
                    data: {batterytype: $category.val(), batterytype: $category.val() },
                    success: function(data){
						$('#img2').hide();
						
                        $("#batt_capacity").html(data)
                    }
                });
			$('#vehicle_name').hide();
			$('#vehicle_model').hide();
			$('#bat_brand1').show();
			$('#bat_brand').hide();
			$('#batt_capacity').show();
			document.batteryhealthindex.batt_capacity.focus();
		   }
		 else
		   {
			 $('#img2').show();
                $.ajax({
                    type: "GET",
                    url: "./servlet/BatteryHome?hidWhatToDo=getvehiclename",
                    data: {batterytype: $category.val() },
                    success: function(data){
						$('#img2').hide();
						$('#vehicle_name').show();
						$('#vehicle_model').show();
						$('#bat_brand').show();
						$('#bat_brand1').hide();
						$('#batt_capacity').hide();
						
							if(data.indexOf("defaultss")>=0)
							{
							 $("#vehicle_name").html(data)
							document.batteryhealthindex.battery_type.focus();
							}
							else
							{
                        $("#vehicle_name").html(data)
						document.batteryhealthindex.vehicle_name.focus();
							}
					}
                });
		      }
            }
        );
	});
$(document).ready(function(){
	$category2 = $('#vehicle_name');
        $category2.change(
            function() {
			document.getElementById("displysesmsg").innerHTML="";
			for(i=document.batteryhealthindex.bat_brand.options.length-1;i>=1;i--)
			{
			document.batteryhealthindex.bat_brand.remove(i);
			}
			for(i=document.batteryhealthindex.state.options.length-1;i>=1;i--)
			{
			document.batteryhealthindex.state.remove(i);
			}
			for(i=document.batteryhealthindex.city.options.length-1;i>=1;i--)
			{
			document.batteryhealthindex.city.remove(i);
			}
			for(i=document.batteryhealthindex.area.options.length-1;i>=1;i--)
			{
			document.batteryhealthindex.area.remove(i);
			}
			document.batteryhealthindex.vehicle_name.focus();
			$('#img3').show();
                $.ajax({
                    type: "GET",
                    url: "./servlet/BatteryHome?hidWhatToDo=getvehicle_model",
                    data: {vehiclename: $category2.val(), batterytype: $category.val()},
                    success: function(data){
						$('#img3').hide();
						if(data.indexOf("defaultss")>=0)
						{
                        $("#vehicle_model").html(data)
						document.batteryhealthindex.vehicle_name.focus();
						}
						else
						{
						 $("#vehicle_model").html(data)
						document.batteryhealthindex.vehicle_model.focus();
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
					var splitval =$category3.val();
					
						for(i=document.batteryhealthindex.bat_brand.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.bat_brand.remove(i);
						}
						for(i=document.batteryhealthindex.state.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.state.remove(i);
						}
						for(i=document.batteryhealthindex.city.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.city.remove(i);
						}
						for(i=document.batteryhealthindex.area.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.area.remove(i);
						}
						document.batteryhealthindex.vehicle_model.focus();
					if(splitval == "default")
						{
						for(i=document.batteryhealthindex.bat_brand.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.bat_brand.remove(i);
						}
						for(i=document.batteryhealthindex.state.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.state.remove(i);
						}
						for(i=document.batteryhealthindex.city.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.city.remove(i);
						}
						for(i=document.batteryhealthindex.area.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.area.remove(i);
						}
						document.batteryhealthindex.vehicle_model.focus();
					}
					else
					{
						$('#img4').show();
				 $.ajax({
                    type: "GET",
                    url: "./servlet/BatteryHome?hidWhatToDo=getbat_brand1",
                    data: {vehiclemodel: $category3.val(), batterytype: $category.val() },
                    success: function(data){
						$('#img4').hide();
						if(data.indexOf("defaultss")>=0)
						{
                        $("#bat_brand").html(data)
						document.batteryhealthindex.vehicle_model.focus();
						}
						else
						{
						 $("#bat_brand").html(data)
						document.batteryhealthindex.bat_brand.focus();
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
					var splitvalban =$category4.val();
					
						for(i=document.batteryhealthindex.bat_brand1.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.bat_brand1.remove(i);
						}
						for(i=document.batteryhealthindex.state.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.state.remove(i);
						}
						for(i=document.batteryhealthindex.city.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.city.remove(i);
						}
						for(i=document.batteryhealthindex.area.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.area.remove(i);
						}
						document.batteryhealthindex.batt_capacity.focus();
						if(splitvalban == "default")
						{
						for(i=document.batteryhealthindex.bat_brand1.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.bat_brand1.remove(i);
						}
						for(i=document.batteryhealthindex.state.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.state.remove(i);
						}
						for(i=document.batteryhealthindex.city.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.city.remove(i);
						}
						for(i=document.batteryhealthindex.area.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.area.remove(i);
						}
						document.batteryhealthindex.batt_capacity.focus();
						}
						else
						{
					$('#img3').show();
			    $.ajax({
                    type: "GET",
                    url: "./servlet/BatteryHome?hidWhatToDo=getbat_brand1",
                    data: {vehiclemodel: $category4.val(), batterytype: $category.val() },
                    success: function(data){
						$('#img3').hide();
						if(data.indexOf("defaultss")>=0)
						{
                        $("#bat_brand1").html(data)
						document.batteryhealthindex.batt_capacity.focus();
						}
						else
						{
						 $("#bat_brand1").html(data)
						document.batteryhealthindex.bat_brand1.focus();
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
						for(i=document.batteryhealthindex.state.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.state.remove(i);
						}
						for(i=document.batteryhealthindex.city.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.city.remove(i);
						}
						for(i=document.batteryhealthindex.area.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.area.remove(i);
						}
						document.batteryhealthindex.bat_brand.focus();
						if(splitvalcity == "default")
						{
						for(i=document.batteryhealthindex.state.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.state.remove(i);
						}
						for(i=document.batteryhealthindex.city.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.city.remove(i);
						}
						for(i=document.batteryhealthindex.area.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.area.remove(i);
						}
						document.batteryhealthindex.bat_brand.focus();
						}
						else
						{
							$('#img5').show();
				$.ajax({
                    type: "GET",
                    url: "./servlet/BatteryHome?hidWhatToDo=getstate",
                    data: {brands: $categorybat.val() },
                    success: function(data){
						$('#img5').hide();
						if(data.indexOf("defaultss")>=0)
						{
                        $("#state").html(data)
						document.batteryhealthindex.state.focus();
						}
						else
						{
						 $("#state").html(data)
						document.batteryhealthindex.state.focus();
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
						for(i=document.batteryhealthindex.state.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.state.remove(i);
						}
						for(i=document.batteryhealthindex.city.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.city.remove(i);
						}
						for(i=document.batteryhealthindex.area.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.area.remove(i);
						}
						document.batteryhealthindex.bat_brand.focus();
						if(splitvalcity == "default")
						{
						for(i=document.batteryhealthindex.state.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.state.remove(i);
						}
						for(i=document.batteryhealthindex.city.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.city.remove(i);
						}
						for(i=document.batteryhealthindex.area.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.area.remove(i);
						}
						document.batteryhealthindex.bat_brand.focus();
						}
						else
						{
							$('#img5').show();
							greyout(true);
							$.ajax({
							type: "GET",
							url: "./servlet/BatteryHome?hidWhatToDo=getstate",
							data: {brands: $category21.val() },
							success: function(data)
							{
							$('#img5').hide();
							greyout(false);
							if(data.indexOf("defaultss")>=0)
							{
							$("#state").html(data)
							document.batteryhealthindex.state.focus();
							}
							else
							{
							 $("#state").html(data)
							document.batteryhealthindex.state.focus();
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
					
						for(i=document.batteryhealthindex.city.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.city.remove(i);
						}
						for(i=document.batteryhealthindex.area.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.area.remove(i);
						}
						document.batteryhealthindex.state.focus();
						if(splitvalcity == "default")
						{
						for(i=document.batteryhealthindex.city.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.city.remove(i);
						}
						for(i=document.batteryhealthindex.area.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.area.remove(i);
						}
						document.batteryhealthindex.state.focus();
						}
						else
						{
						$('#img6').show();
				$.ajax({
                    type: "GET",
                    url: "./servlet/BatteryHome?hidWhatToDo=getcity",
                    data: {state: $category5.val() },
                    success: function(data)
					{
						$('#img6').hide();
						if(data.indexOf("defaultss")>=0)
						{
							$("#city").html(data)
							document.batteryhealthindex.city.focus();
						}
						else
						{
							$("#city").html(data)
							document.batteryhealthindex.city.focus();
						}
					}
                });
				}
            }
        );
	});
$(document).ready(function(){
	$category6 = $('#city');
	        $category6.change(
            function() {
				document.getElementById("displysesmsg").innerHTML="";
					var splitvalarea =$category6.val();
					
						for(i=document.batteryhealthindex.area.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.area.remove(i);
						}
						document.batteryhealthindex.city.focus();
						if(splitvalarea == "default")
						{
						for(i=document.batteryhealthindex.area.options.length-1;i>=1;i--)
						{
						document.batteryhealthindex.area.remove(i);
						}
						document.batteryhealthindex.city.focus();
						}
						else
						{
								$('#img7').show();
							$.ajax({
							type: "GET",
							url: "./servlet/BatteryHome?hidWhatToDo=getarea",
							data: {city: $category6.val() },
							success: function(data)
							{
								$('#img7').hide();
								if(data.indexOf("defaultss")>=0)
								{
									$("#area").html(data)
									document.batteryhealthindex.area.focus();
								}
								else
								{
									 $("#area").html(data)
									document.batteryhealthindex.area.focus();
								}
						 }
                });
			  }
            }
        );
	});
$(document).ready(function()
{
	$area= $('#area');
    $area.change
	(
  		function() 
		{
			document.getElementById("displysesmsg").innerHTML="";
		}
	);
});
function isDigits(argvalue) 
{
    argvalue = argvalue.toString();
	if(argvalue=="")
	{
		return false;
	}
    var validChars = "0123456789";
    var startFrom = 0;
    if (argvalue.substring(0, 2) == "0x") {
       validChars = "0123456789abcdefABCDEF";
       startFrom = 2;
    } else if (argvalue.charAt(0) == "0") {
       validChars = "01234567";
       startFrom = 1;
    }
    for (var n = 0; n < argvalue.length; n++) {
        if (validChars.indexOf(argvalue.substring(n, n+1)) == -1) return false;
    }
  return true;
}
function funToGetbatterydetails()
{
	var batterytype = document.batteryhealthindex.battery_type.value;      
	var vehiclename = document.batteryhealthindex.vehicle_name.value;
	var vehiclemodel = escape(document.batteryhealthindex.vehicle_model.value);
	var batterybrand = document.batteryhealthindex.bat_brand.value;
	var batterybrand1 = document.batteryhealthindex.bat_brand1.value;
	var batterycapty = document.batteryhealthindex.batt_capacity.value;
	var state = document.batteryhealthindex.state.value;
	var city = document.batteryhealthindex.city.value;
	var area = document.batteryhealthindex.area.value;
	var pincode = document.batteryhealthindex.pincode.value;

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
		if(document.batteryhealthindex.battery_type.value == 0)
		{
			errMsg ="<font color='#ff3333'>Please Select \'Battery Type\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.batteryhealthindex.battery_type.focus();
			return ;
		}
		if(document.batteryhealthindex.batt_capacity.value == 0 || batterycapty=="default")
		{
			errMsg ="<font color='#ff3333'>Please Select \'Battery Capacity\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.batteryhealthindex.batt_capacity.focus();
			return ;
		}
		if(document.batteryhealthindex.bat_brand1.value == 0 || batterybrand1=="default")
		{
			errMsg ="<font color='#ff3333'>Please Select \'Battery Brand\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.batteryhealthindex.bat_brand1.focus();
			return ;
		}
		vehiclename = "";
		vehiclemodel = "";
		batterybrand = batterybrand1;
		batterycapty = batterycapty;
	}
	else
	{
		if(document.batteryhealthindex.battery_type.value == 0)
		{
			errMsg ="<font color='#ff3333'>Please Select \'Battery Type\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.batteryhealthindex.battery_type.focus();
			return ;
		}
		if(document.batteryhealthindex.vehicle_name.value == 0 || vehiclename=="default")
		{
			errMsg ="<font color='#ff3333'>Please Select \'Make\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.batteryhealthindex.vehicle_name.focus();
			return ;
		}
		if(document.batteryhealthindex.vehicle_model.value == 0 || vehiclemodel=="default")
		{
			errMsg ="<font color='#ff3333'>Please Select \'Model\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.batteryhealthindex.vehicle_model.focus();
			return ;
		}
		vehiclename = vehiclename;
		vehiclemodel = vehiclemodel;
		batterybrand = batterybrand;
		batterycapty ="";
		if(document.batteryhealthindex.bat_brand.value == 0 || batterybrand=="default")
		{
			errMsg ="<font color='#ff3333'>Please Select \'Battery Brand\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.batteryhealthindex.bat_brand.focus();
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
			document.batteryregister.pincode.focus();
			return ;
		}
		if(document.batteryhealthindex.state.value == 0 || state=="default")
		{
			errMsg ="<font color='#ff3333'>Please Select \'State\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.batteryhealthindex.state.focus();
			return ;
		}
		if(document.batteryhealthindex.city.value == 0 || city=="default")
		{
			errMsg ="<font color='#ff3333'>Please Select \'Location\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.batteryhealthindex.city.focus();
			return ;
		}
		if(document.batteryhealthindex.area.value == 0 || area=="default")
		{
			errMsg ="<font color='#ff3333'>Please Select \'Area\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.batteryhealthindex.area.focus();
			return ;
		}
	}
	else
	{
		if(document.batteryhealthindex.pincode.value == "" || pincode=="default")
		{
			errMsg ="<font color='#ff3333'>Please Enter \'Pincode\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.batteryhealthindex.pincode.focus();
			return ;
		}
		var pincoderegex=/^[0-9]{4,6}$/;
		if(!pincode.match(pincoderegex))
		{
			errMsg ="<font color='#ff3333'>Please Enter Valid \'Pincode\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.batteryhealthindex.pincode.focus();
			return ;
		}
	}
	var xmlhttp= "";
	var resp= "";
	var url="./servlet/BatteryHealthcheck?hidWhatToDo=checkretailerdetails&state="+state+"&city="+city+"&area="+area+"&pincode="+pincode+"";
	if (window.XMLHttpRequest)
	{
		xmlhttp=new XMLHttpRequest();
	}
	else
	{
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if (xmlhttp.readyState==4)
		{
			if(xmlhttp.status!=200)
			{
				return;
			}
			else
			{
				resp = xmlhttp.responseText;				
				if(resp.indexOf("Franchisee Details are found")>=0)
				{
					errMsg ="<table cellspacing='10' cellpadding='0' bgcolor='#FFFFFF'><table width='350' bgcolor='#FFFFFF' valign='top'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobilediv(greyout(false));\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b>				</td></tr></table><tr><td><table width='350'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF'><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font size='2' color='#FF8C00'>Enter Your Battery Installation Time!</font></td></tr></table></td></tr><tr><td ><table width='100%' cellspacing='0' cellpadding='0'><tr><td width='20%'></td><td width='80%' height='10'></td></tr><tr><td width='20%'></td><td width='80%' align='left' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Your&nbsp;Mobile&nbsp;Number</td></tr><tr><td width='20%'></td><td width='80%' align='left'>&nbsp;<input class='insidecontent' type='tel' autocomplete='off' name='usermobilenumber' id='usermobilenumber' placeholder='9603467559' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='10'></td></tr><tr><td width='20%'></td><td width='80%' align='left' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'>&nbsp;Month&nbsp;Of&nbsp;Battery&nbsp;Installation</td></tr><tr><td width='20%'></td><td width='80%' align='left'>&nbsp;<input class='insidecontent' type='text'  name='inmonth' id='inmonth' placeholder='04' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='02' ></td></tr><tr><td width='20%'></td><td width='80%' align='left' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'>&nbsp;Year&nbsp;Of&nbsp;Battery&nbsp;Installation</td></tr><tr><td width='20%'></td><td width='80%' align='left'>&nbsp;<input class='insidecontent' type='text' autocomplete='off' name='inyear' id='inyear' placeholder='1980' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='04' ></td></tr><tr><td width='20%'></td><td width='80%' align='left'><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript: askcosumerdetails1('"+batterytype+"','"+vehiclename+"','"+vehiclemodel+"','"+batterybrand+"','"+batterycapty+"','"+state+"','"+city+"','"+area+"','"+pincode+"',Submitrret);\"></td></tr>  </table></td></tr><tr height='26'><td colspan='3' align='center' class='subheading' id='displayrefinederrormsg1'></td></tr><tr><td height='15'></td></tr></table>";

					document.getElementById("divmobile").innerHTML="";
					document.getElementById("divmobile").style.display='block';
					document.getElementById("divmobile").innerHTML=errMsg
					greyout(true);
					document.batteryhealthindex.usermobilenumber.focus();	
				}
				else
				{			
					greyout(true);
					document.getElementById("divmobile").style.display='block';
					document.getElementById("divmobile").innerHTML="";
					document.getElementById("divmobile").innerHTML=resp;
				}
			}
		}	
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();
}
function askcosumerdetails1(batterytype,vehiclename,vehmodel,batterybrand,batterycapcity,state,city,area,pincode,varButton)
{
	var strUsermobileno=document.batteryhealthindex.usermobilenumber.value;
	var inmonth=document.batteryhealthindex.inmonth.value;
	var inyear=document.batteryhealthindex.inyear.value;
	
	var minYear=1901;
	var maxYear=2099;

	if(inmonth == "")
	{

	}
	else
	{
		if(document.batteryhealthindex.inmonth.value.length<1 || inmonth<1 || inmonth>12)
		{
			errMsg ="<font color='#9B5BDD'>Please enter valid Month...!</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.batteryhealthindex.inmonth.focus();
			return ;
		}
		var monthregex=/^[0-9]{1,2}$/;
		if(!inmonth.match(monthregex))
		{
			errMsg ="<font color='#9B5BDD'>Please enter month in the format of \"04\" ...!</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.batteryhealthindex.inmonth.focus();
			return ;
		}
	}
	if(inyear == "")
	{

	}
	else
	{
		if (document.batteryhealthindex.inyear.value.length != 4 || inyear==0 || inyear<minYear || inyear>maxYear)
		{
			errMsg ="<font color='#9B5BDD'>Please enter a valid 4 digit year between "+minYear+" and "+maxYear+"...!</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.batteryhealthindex.inyear.focus();
			return ;
		}
		var yearregex=/^[0-9]{4}$/;
		if(!inyear.match(yearregex))
		{
			errMsg ="<font color='#9B5BDD'>Please enter year in the format of \"1980\" ...!</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.batteryhealthindex.inyear.focus();
			return ;
		}
	}
	if(strUsermobileno == 0 || strUsermobileno == "")
	{
		errMsg ="<font color='#9B5BDD'>Please enter Mobile Number...!</font>";
		document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
		document.batteryhealthindex.usermobilenumber.focus();
		return ;
	}
	else 
	{
		var checkOK = "0123456789";
		var checkStr = strUsermobileno;
		var allValid = true;
		var decPoints = 0;
		var allNum = "";
		for (i = 0;  i < checkStr.length;  i++)
		{
			ch = checkStr.charAt(i);
			for (j = 0;  j < checkOK.length;  j++)
			if (ch == checkOK.charAt(j))
			break;
			if (j == checkOK.length)
			{
				allValid = false;
				break;
			}
		}
		if (!allValid)
		{
			errMsg ="<font color='#9B5BDD'>Please enter only digits...!</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.batteryhealthindex.usermobilenumber.value="";
			document.batteryhealthindex.usermobilenumber.focus();
			return ;		
		}
	}
	if (document.getElementById("usermobilenumber").value.length<10)
	{
		errMsg ="<font color='#9B5BDD'>Please enter valid Number...!</font>";
		document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
		document.batteryhealthindex.usermobilenumber.focus();
		return;
	}
	if (document.getElementById("usermobilenumber").value.length==10)
	{
		if (strUsermobileno < 7000000000 )
		 {
			errMsg ="<font color='#9B5BDD'>Number Should start with 7 or 8 or 9...!</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.batteryhealthindex.usermobilenumber.focus();
			return ;
		 }
	}  
	if (document.getElementById("usermobilenumber").value.length>10)
	{
		if (strUsermobileno < 917000000000 || strUsermobileno >= 920000000000 )
		{
			errMsg ="<font color='#9B5BDD'>Number Should start with 91-7 or 8 or 9...!</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.batteryhealthindex.usermobilenumber.focus();
			return ;
		}
	}  
	varButton.disabled=true;
	varButton.value='Please Wait...';  
	var xmlhttp= "";
	var resp= "";
	var url="./servlet/BatteryHealthcheck?hidWhatToDo=sentverificationcode&inmonth="+inmonth+"&inyear="+inyear+"&strUsermobileno="+strUsermobileno+"&batterytype="+batterytype+"&vehiclename="+vehiclename+"&vehmodel="+vehmodel+"&batterybrand="+batterybrand+"&batterycapcity="+batterycapcity+"&state="+state+"&city="+city+"&area="+area+"&pincode="+pincode+"&keyword=healthcheck";

	if (window.XMLHttpRequest)
	{
		xmlhttp=new XMLHttpRequest();
	}
	else
	{
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if (xmlhttp.readyState==4)
		{
			if(xmlhttp.status!=200)
			{
				return;
			}
			else
			{
				resp = xmlhttp.responseText;				
				document.getElementById("divmobile").innerHTML="";
				document.getElementById("divmobile").innerHTML=resp;
				document.getElementById("verifycode").focus();
			}
		}	
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();	
}
function checkverification(inmonth,inyear,strUsermobileno,verificationcode,batterytype,vehiclename,vehmodel,batterybrand,batterycapcity,state,city,area,pincode)
{
		if(city == "" || city == 0)
		{
			var styless = "style='display:none;'";
		}
		else
		{
			var styles ="style='display:none;'";
		}
		var verifycode=document.batteryhealthindex.verifycode.value;
		
		if (verificationcode == verifycode)
		{
			errMsg ="<table cellspacing='10' cellpadding='0' bgcolor='#FFFFFF'><table width='350' bgcolor='#FFFFFF' valign='top'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobilediv(greyout(false));\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b>				</td></tr></table><tr><td><table width='350'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF'><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font size='2' color='#FF8C00'>Please Enter Your Installation Location Details!</font></td></tr></table></td></tr><tr><td ><table width='100%' cellspacing='0' cellpadding='0'><tr><td width='20%'></td><td width='80%' height='10'></td></tr><tr><td width='20%'></td><td width='80%' align='left' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;your&nbsp;Name</td></tr><tr><td width='20%'></td><td width='80%' align='left'>&nbsp;<input class='insidecontent' type='text' autocomplete='off' name='username' id='username' placeholder='Johan' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='40' ></td></tr><tr><td width='20%'></td><td width='80%' height='5'></td></tr><tr><td width='20%'></td><td width='80%' align='left' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;your&nbsp;Email</td></tr><tr><td width='20%'></td><td width='80%' align='left'>&nbsp;<input class='insidecontent' type='text' autocomplete='off' name='emailid' id='emailid' placeholder='johan@gmail.com' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='50' ></td></tr><tr><td width='20%'></td><td width='80%' height='5'></td></tr><tr><td width='20%'></td><td width='100%' align='left' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;your&nbsp;Address&nbsp;1</td></tr><tr><td width='20%'></td><td width='80%' align='left'>&nbsp;<input class='insidecontent' type='text' autocomplete='off' name='addrs1' id='addrs1' placeholder='2-124,Road No:4,HSR Layout,Bangalore' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='225' ></td></tr><tr><td width='20%'></td><td width='80%' height='5'></td></tr><tr><td width='20%'></td><td width='100%' align='left' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'>&nbsp;Enter&nbsp;your&nbsp;Address&nbsp;2</td></tr><tr><td width='20%'></td><td width='80%' align='left'>&nbsp;<input class='insidecontent' type='text' autocomplete='off' name='addrs2' id='addrs2' placeholder='Near Forum Mall' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='225' ></td></tr "+styless+"><tr><td width='20%'></td><td width='80%' height='5'></td></tr><tr "+styless+"><td width='20%'></td><td width='100%' align='left' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'>&nbsp;Enter&nbsp;your&nbsp;Area</td></tr><tr "+styless+"><td width='20%'></td><td width='80%' align='left'>&nbsp;<input class='insidecontent' type='text' autocomplete='off' name='userarea' id='userarea' value='"+area+"' placeholder='Benson Town' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='50' readonly ></td></tr><tr><td width='20%'></td><td width='80%' height='5'></td></tr><tr "+styless+"><td width='20%'></td><td width='100%' align='left' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'>&nbsp;Enter&nbsp;your&nbsp;City</td></tr><tr "+styless+"><td width='20%'></td><td width='80%' align='left'>&nbsp;<input class='insidecontent' type='text' autocomplete='off' name='usercity' id='usercity' value='"+city+"' placeholder='Bangalore' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='50' readonly onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:getconsumerdetails('"+inmonth+"','"+inyear+"','"+strUsermobileno+"','"+verificationcode+"','"+batterytype+"','"+vehiclename+"','"+vehmodel+"','"+batterybrand+"','"+batterycapcity+"','"+state+"','"+city+"','"+area+"','"+pincode+"',Submitrret);return false;} else return true;\"/></td></tr><tr><td width='20%'></td><td width='80%' height='5'></td></tr><tr "+styles+"><td width='20%'></td><td width='80%' align='left' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'>&nbsp;Enter&nbsp;your&nbsp;ZipCode</td></tr><tr "+styles+"><td width='20%'></td><td width='80%' align='left'>&nbsp;<input class='insidecontent' type='text' autocomplete='off' name='userzipcode' id='userzipcode' value='"+pincode+"' placeholder='560037' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='7' readonly onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:getconsumerdetails('"+inmonth+"','"+inyear+"','"+strUsermobileno+"','"+verificationcode+"','"+batterytype+"','"+vehiclename+"','"+vehmodel+"','"+batterybrand+"','"+batterycapcity+"','"+state+"','"+city+"','"+area+"','"+pincode+"',Submitrret);return false;} else return true;\"/></td></tr><tr><td width='20%'></td><td width='80%' align='left'><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:getconsumerdetails('"+inmonth+"','"+inyear+"','"+strUsermobileno+"','"+verificationcode+"','"+batterytype+"','"+vehiclename+"','"+vehmodel+"','"+batterybrand+"','"+batterycapcity+"','"+state+"','"+city+"','"+area+"','"+pincode+"',Submitrret);\"></td></tr>  </table></td></tr><tr height='26'><td colspan='3' align='center' class='subheading' id='displayrefinederrormsg'></td></tr><tr><td height='15'></td></tr></table>";
			document.getElementById("divmobile").innerHTML="";
			document.getElementById("divmobile").style.display='block';
			document.getElementById("divmobile").innerHTML=errMsg
			greyout(true);
			document.batteryhealthindex.username.focus();
	}
	else
	{
		if (verifycode=="")
		{
			errMsg ="<font color='#9B5BDD'>Please enter verification code</font>";
			document.getElementById("codeerrormsg").innerHTML=errMsg;
			document.batteryhealthindex.verifycode.focus();
			return ;
		}
		else
		{
			errMsg ="<font color='#9B5BDD'>Verification Code Does Not Match Please Try Again</font>";
			document.getElementById("codeerrormsg").innerHTML=errMsg;
			document.batteryhealthindex.verifycode.focus();
			return ;
		}
    }
}
function getconsumerdetails(inmonth,inyear,strUsermobileno,verificationcode,batterytype,vehiclename,vehmodel,batterybrand,batterycapcity,state,city,area,pincode,varButton)
{
	var strusername=document.batteryhealthindex.username.value;
	var emailid=document.batteryhealthindex.emailid.value;
	var addrs1=document.batteryhealthindex.addrs1.value;
	var addrs2=document.batteryhealthindex.addrs2.value;
	var userarea=document.batteryhealthindex.userarea.value; 
	var usercity=document.batteryhealthindex.usercity.value; 
	var userzipcode=document.batteryhealthindex.userzipcode.value;
	
     var reg="/@(([^\.-]*\.[^\.]*)?){1,3}$/";
     var re =
/^(([^()[\]\\.;:\s@\"]+(\.[^()[\]\\.;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
     var regexp=/^[a-zA-Z0-9_\+-]+(\.[a-zA-Z0-9_\+-]+)*@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.([a-zA-Z]{2,3})$/;
     var iChars1 = "`~,!#$%^&*()+=[]\\\';/{}|\":<>?";
     var iChars3 = "`~!@#$%^&*()+=-_[]\\\';,/{}|\":<>?";
     var iChars = "!@~`������'\"#";
     var alpchar="/[a-zA-z]/";
     var expr="/^[.-_]/";
     var reg=/@(([^\.-]*\.[^\.]*)?){1,3}$/;
     var iChars2 = "`~!@#$%^&*()+=[]\\\';,/{}|\"<>?";
	 var nonums ="1234567890";
	 var dot="."
	var iChars12 = "\\\'";
	var nonums1 =/^([a-z]|[A-Z]| )*$/;

	if(strusername == "")
     {
		errMsg ="<font color='#9B5BDD'>Please enter User Name...!</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
		document.batteryhealthindex.username.focus();
		return ;
     }
     if(strusername.length <3)
     {
		errMsg ="<font color='#9B5BDD'>UserName should have minimum 3 characters</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
		document.batteryhealthindex.username.focus();
		return ;
     }
     if(strusername.length == 3)
     {
         if (strusername.indexOf(' ') >= 0 )
         {
			errMsg ="<font color='#9B5BDD'>User Name should have minimum 3 characters</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.batteryhealthindex.username.focus();
			return ;
         }
     }
     if(strusername.length == 3)
     {
         if (strusername.indexOf(dot) >= 0 )
         {
			errMsg ="<font color='#9B5BDD'>UserName is invalid</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.batteryhealthindex.username.focus();
			return ;
         }
     }
     if (document.batteryhealthindex.username.value.indexOf(' ')==0 )
     {
			errMsg ="<font color='#9B5BDD'>UserName should not start with space</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.batteryhealthindex.username.focus();
			return ;
     }
     if (strusername.indexOf('  ') >= 0 )
     {
			errMsg ="<font color='#9B5BDD'> Invalid User Name</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.batteryhealthindex.username.focus();
			return ;
     }
     if (strusername.indexOf('..') >= 0 )
     {
			errMsg ="<font color='#9B5BDD'> Invalid User Name</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.batteryhealthindex.username.focus();
			return ;
     }
     if (document.batteryhealthindex.username.value.indexOf(dot)==0 )
     {
			errMsg ="<font color='#9B5BDD'> User Name should not start with dot</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.batteryhealthindex.username.focus();
			return ;
     }
     for (var i = 0; i < document.batteryhealthindex.username.value.length; i++)
     {
         if (iChars3.indexOf(document.batteryhealthindex.username.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#9B5BDD'>Special characters are not allowed in User Name field.</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.batteryhealthindex.username.focus();
			return ;
         }
     }
     for (var i = 0; i < document.batteryhealthindex.username.value.length; i++)
     {
         if (nonums.indexOf(document.batteryhealthindex.username.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#9B5BDD'>Numbers are not allowed in User Name field.</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.batteryhealthindex.username.focus();
			return ;
         }
     }
	  if (/[a-z][A-Z]{2}/i.test(document.batteryhealthindex.username.value) != true) 
		{
		  errMsg ="<font color='#9B5BDD'>Please enter atleast 3 Charaters together in the User Name Field.</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.batteryhealthindex.username.focus();
			return ;
         }

		var at="@"
		var lat=emailid.indexOf(at)
		var lstr=emailid.length
		var ldot=emailid.indexOf(dot)
		var sst=emailid.substring(at,lstr)
		var sstdot=sst.indexOf(dot)
		var hi = "-"
		var us = "_"
		var lhi = emailid.indexOf(hi)
		var lus = emailid.indexOf(us)
	

     if(emailid == "")
     {
         errMsg ="<font color='#9B5BDD'>Please Enter Email id</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
     if (document.batteryhealthindex.emailid.value.indexOf(' ') >= 0 )
     {
         errMsg ="<font color='#9B5BDD'>Spaces are not allowed for Email id</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
     if (emailid.indexOf(at)==-1)
     {
         errMsg ="<font color='#9B5BDD'>Email-id should be in the form of abcxyz@gmail.com</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
     if (emailid.indexOf(at) < 2)
     {
         errMsg ="<font color='#9B5BDD'>Please Enter Valid Email id</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
     if (emailid.indexOf(at)==-1 || emailid.indexOf(at)==0 || emailid.indexOf(at)==lstr)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
     if (emailid.indexOf(dot)==-1 || emailid.indexOf(dot)==0 || emailid.indexOf(dot)==lstr)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
     if (emailid.indexOf(at) >50)
     {
         errMsg ="<font color='#9B5BDD'>Email id should not exceed more than 50 characters</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
     if (emailid.indexOf(dot) == -1 || emailid.indexOf(dot) == 0 || emailid.indexOf(dot) == lstr)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
     if (! document.batteryhealthindex.emailid.value.match(reg))
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
         document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
     if (emailid.indexOf(at, (lat + 1)) != -1)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
     if (emailid.substring(lat - 1, lat) == dot || emailid.substring(lat + 1, lat + 2) == dot || emailid.substring(ldot+1,ldot+2)==dot)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
     if (emailid.substring(lat - 1, lat) == us || emailid.substring(lat + 1, lat + 2) == us || emailid.substring(lat+1,lat+2)==us)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
     if (emailid.substring(lat - 1, lat) == hi || emailid.substring(lat + 1, lat + 2) == hi || emailid.substring(lhi+1,lhi+2)==hi)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
     if (emailid.indexOf(dot, (lat + 2)) == -1)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
     if (emailid.indexOf(" ") != -1)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
     if (emailid.substring(lhi-1,lhi)==hi || emailid.substring(lhi+1,lhi+2)==hi)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
     if ((emailid.substring(lhi-1,lhi)==hi || emailid.substring(lus+1,lus+2)==us) || (emailid.substring(lus-1,lus)==us ||   emailid.substring(lhi+1,lhi+2)==hi))
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
     if (emailid.substring(lhi - 1, lhi) == dot || emailid.substring(lhi + 1, lhi + 2) == dot || emailid.substring(lus+1,lus+2)==dot)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
     if (emailid.substring(lus - 1, lus) == dot || emailid.substring(lus + 1, lus + 2) == dot || emailid.substring(ldot+1,ldot+2)==dot)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
     if (document.batteryhealthindex.emailid.value.indexOf('com.in') >=0)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
     if (emailid.substring(lhi - 1, lhi) == us || emailid.substring(lhi + 1, lhi + 2) == us || emailid.substring(lus+1,lus+2)==us)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
	     document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
     if (emailid.indexOf(hi, (lhi + 1)) != -1)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
     if (! document.batteryhealthindex.emailid.value.match(re))
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
     for (var i = 0; i < document.batteryhealthindex.emailid.value.length; i++)
     {
         if (iChars1.indexOf(document.batteryhealthindex.emailid.value.charAt(i))!= -1)
         {
             errMsg ="<font color='#9B5BDD'>Invalid characters are not allowed in Email id field</font>";
		     document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
             document.batteryhealthindex.emailid.focus();
             return ;
         }
     }
     if (! document.batteryhealthindex.emailid.value.match(regexp))
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
     if (document.batteryhealthindex.emailid.value.indexOf('gmail') >=0)
     {
         if (document.batteryhealthindex.emailid.value.indexOf('com')<0)
         {
             errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
			 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
             document.batteryhealthindex.emailid.focus();
             return ;
         }
     }
     if (document.batteryhealthindex.emailid.value.indexOf('rediffmail') >=0)
     {
         if (document.batteryhealthindex.emailid.value.indexOf('com')<0)
         {
             errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
			 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
             document.batteryhealthindex.emailid.focus();
             return ;
         }
     }
     if (document.batteryhealthindex.emailid.value.indexOf('hotmail') >=0)
     {
         if (document.batteryhealthindex.emailid.value.indexOf('com')<0)
         {
             errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
			 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
             document.batteryhealthindex.emailid.focus();
             return ;
         }
     }
    
     if (document.batteryhealthindex.emailid.value.indexOf('ymail') >=0)
     {
         if (document.batteryhealthindex.emailid.value.indexOf('com')<0)
         {
             errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
			 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
             document.batteryhealthindex.emailid.focus();
             return ;
         }
     }
     if (emailid.indexOf("__") != -1)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
     if (emailid.indexOf(at,(lat+1))!=-1)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
     if (emailid.substring(lat-1,lat)==dot || emailid.substring(lat+1,lat+2)==dot)
        {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
     if (emailid.indexOf(dot,(lat+2))==-1)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
         openPopUp(errMsg);
         return;
         return false
     }
     if (emailid.indexOf(" ")!=-1)
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
     if (! document.batteryhealthindex.emailid.value.match(re))
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
     if (document.batteryhealthindex.emailid.value.indexOf("-")==0 )
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
     if (document.batteryhealthindex.emailid.value.indexOf("_")==0 )
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		 document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
     if (document.batteryhealthindex.emailid.value.indexOf("0123456789")==3 )
     {
         errMsg ="<font color='#9B5BDD'>Email id should be in the form of abcxyz@gmail.com</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
         document.batteryhealthindex.emailid.focus();
         return ;
     }
	 if(addrs1 == "")
     {
		errMsg ="<font color='#9B5BDD'>Please enter Address 1...!</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
		document.batteryhealthindex.addrs1.focus();
		return ;
     }
	   if(isDigits(addrs1)==true)
		{
			errMsg ="<font color='#9B5BDD'>Only Numerics are not allowed in the \'Address 1\' field.</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.batteryhealthindex.addrs1.focus();
			return ;
         }
	 if (document.batteryhealthindex.addrs1.value.indexOf(' ') == 0 ) 
	 {
		 errMsg ="<font color='#9B5BDD'>Address 1 should not start with space</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
		document.batteryhealthindex.addrs1.focus();
		return ;
	  }
	  var checkOK = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ#&()+=-[]',./\|_\":?0123456789 ";
		var checkStr = addrs1;
		var allValid = true;
		var decPoints = 0;
		var allNum = "";
		for (i = 0;  i < checkStr.length;  i++)
		{
			ch = checkStr.charAt(i);
			for (j = 0;  j < checkOK.length;  j++)
			if (ch == checkOK.charAt(j))
			break;
			if (j == checkOK.length)
			{
				allValid = false;
				break;
			}
		}
		if (!allValid)
		{
		errMsg ="<font color='#9B5BDD'>Address 1 have some special character. Please remove them and try again...!</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
		document.batteryhealthindex.addrs1.focus();
		return ;
	  }
	 if(addrs2 == "")
     {
		
     }
	 if(isDigits(addrs2)==true)
		{
			errMsg ="<font color='#9B5BDD'>Only Numerics are not allowed in the \'Address 2\' field.</font>";
			document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
			document.batteryhealthindex.addrs2.focus();
			return ;
         }
	 else
	{
	 if (document.batteryhealthindex.addrs2.value.indexOf(' ') == 0 ) 
	 {
		 errMsg ="<font color='#9B5BDD'>Address 2 should not start with space</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
		document.batteryhealthindex.addrs2.focus();
		return ;
	  }
	  var checkOK = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ#&()+=-[]',./\|_\":?0123456789 ";
		var checkStr = addrs2;
		var allValid = true;
		var decPoints = 0;
		var allNum = "";
		for (i = 0;  i < checkStr.length;  i++)
		{
			ch = checkStr.charAt(i);
			for (j = 0;  j < checkOK.length;  j++)
			if (ch == checkOK.charAt(j))
			break;
			if (j == checkOK.length)
			{
				allValid = false;
				break;
			}
		}
		if (!allValid)
		{
		errMsg ="<font color='#9B5BDD'>Address 2 have some special character. Please remove them and try again...!</font>";
		document.getElementById("displayrefinederrormsg").innerHTML=errMsg;
		document.batteryhealthindex.addrs2.focus();
		return ;
	  }
	}
	
		vehmodel =escape(vehmodel);
		addrs1=escape(addrs1);
		addrs2=escape(addrs2);
		varButton.disabled=true;
		varButton.value='Please Wait...';
	
		var xmlhttp= "";
		var resp= "";
		var url="./servlet/BatteryHealthcheck?hidWhatToDo=insertconsumerdetails&username="+strusername+"&emailid="+emailid+"&addrs1="+addrs1+"&addrs2="+addrs2+"&userarea="+userarea+"&usercity="+usercity+"&userzipcode="+userzipcode+"&inmonth="+inmonth+"&inyear="+inyear+"&mobilenumber="+strUsermobileno+"&verifycode="+verificationcode+"&batterytype="+batterytype+"&vehiclename="+vehiclename+"&vehmodel="+vehmodel+"&batterybrand="+batterybrand+"&batterycapcity="+batterycapcity+"&state="+state+"&city="+city+"&area="+area+"&pincode="+pincode; 

		document.getElementById("displayrefinederrormsg").innerHTML=""; 
		if (window.XMLHttpRequest)
		{
			xmlhttp=new XMLHttpRequest();
		}
		else
		{
			xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange=function()
		{
			if (xmlhttp.readyState==4)
			{
				if(xmlhttp.status!=200)
				{
					return;
				}
				else
				{
					resp = xmlhttp.responseText;
					document.getElementById("divmobile").innerHTML="";
					document.getElementById("divmobile").innerHTML=resp;
					resp= "";
				}
			}	
		}
		xmlhttp.open("GET",url,true);
		xmlhttp.send();
		
}

function onarea()
{
	document.getElementById("displysesmsg").innerHTML="";
	$('#areadiv').show();
	$('#areassdiv').hide();
	document.batteryhealthindex.area.focus();
}
function changetopincode()
{
	
	document.getElementById("displysesmsg").innerHTML="";
	document.batteryhealthindex.pincode.value="";
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
function closemobilediv()
{
	$('#divmobile').hide();
	greyout(false);
}
function closemobdiv()
{
	$('#divmobile').hide();
	greyout(false);
	location.href="./batteryhealthcheck.jsp";
}
function globalbanneradbattery(whatevertopad)
{
	whatevertopad="Batteries";
	scrollglobal=whatevertopad;
	url="./servlet/ScrollingTopAdsServlet?hidWhatToDo=scrollcategorytopads&catname="+whatevertopad+"&requestno="+(Math.random()*100),true;	
	$.get(url,function(response, status, xhr) 
	{
		if (status == "success") 
		{
			$('#topads').html(response);
		}
		$('#topads').jqFancyTransitions({effect: 'wave', delay: 2250, width: 950, height: 150, links: true ,navigation: true,direction: 'left', strips: 1 });
	});
}
//]]>
</script>
</head>
<body  onload="document.batteryhealthindex.battery_type.focus();scrollingtopadsDefault();globalbanneradbattery('whatevertopad');">


<!-- On load images  -->

<div class="se-pre-con"></div>
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="batteryhealthindex">

<div id="preloader" class='preloader' style="display:none;"><table border='0' width='75px' bgcolor='#ffffff'><tr><td height='55px' align='center'><img src="./images/loader.gif" /></td></tr></table></div> 
<table border='0' cellpadding="0" width="950" align="center">
		<tr>
			<td>
			
			
	<jsp:include page = "header.jsp" />
	<table width="950" border="0" class="insidecontent c24" align="center" cellspacing="0" cellpadding="0" >
		<tr>
			<td align = "center" valign = "top" bgcolor = "#FFFFFF">
	<!-- Inner content should be within the below table -->
				<table width="100%" border="0" bgcolor="#FFFFFF" class="insidecontent" cellspacing="0" cellpadding="0" >
				<tr>
					<td width="100%" class="insidecontent" align="left"><a class="insidecontent" href="./index.jsp"><img src="./images/back25.png" alt="order car battery online" border="0" width="25" height="25"/></a></td>
				</tr>
				<tr>
					<td height="2"><div id='scrollpopup'  style="display:block;"><table width="100%" border="0" align="center" valign="top"><tr><td align='right' class='loading_image'></td></tr><tr><td align='center' style='font-family:Verdana;font-size:12px;color:#000000; text-decoration:none;padding:1px 1px;'>Please wait page is loading....</td></tr></table></div></td></td>
				</tr>
				
			
					<tr><td height="2"></td></tr>
					<tr>
						<td>
							<table border="0" align="left"  width="99.5%" bgcolor="#FFFFFF" cellpadding="0" cellspacing="0" >
								<tr>
									<td width="70%">
									<!--Popup messages div starts -->
										<div id='divmobile' class='divmobile' style="display:none;"></div>
											<div id='popup' class='popup' style="display:none;">
												<div id='popuptitle' class='popuptitle'>
													<table border='0' width='410px' height='10px' cellpadding='0' cellspacing='0'>
														<tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'>
														<a href='javascript:closePopup(greyout(false));' class="bluelinks333">x</a></td></tr></table>
												</div>
												<div id='popupmessage' class='popupmessage'></div> 
											</div>
												<!--Popup messages div Ends -->
												<table border="0" width="100%" valign="top">
													<tr>
														<td valign="top" width="70%" align="right">
															<table width="100%" border="0" valign="top">
																<tr>
																	<td width="100%" class="insidecontent" align="center" style='font-family:arial, helvetica, sans-serif;font-size:10px;color:#FF8C00;font-weight:bold;text-decoration:none;padding:1px 1px;'><h1>Order a Free Battery Health Check</h1></td>
																</tr>
															</table>
															<table width="100%" border="0" >
																<tr>
																	<td class="insidecontent" align="center"><div id="displysesmsg"></div></td>
																</tr>	
															</table>
															<table width="100%" border="0" align="center">
																
																<tr>
																	<td width="25%" align="left">
																		<div id="img1" align='center' style='display:none;'><img src="./images/loader.gif" width="20" height="20"/></div>
																		<div class="styled-select">
																			<select  name='battery_type' id='battery_type' class="insidecontent" style='width:155px;  bgcolor:#FFF;' >
																				<option style="background:#FFF" value='' class='topindex1'>Select&nbsp;Battery&nbsp;Type</option>
																				<option style="background:#FFF" value="Car Batteries" >Car Batteries</option>
																				<option style="background:#FFF" value="Inverter Batteries" >Inverter Batteries</option>
																				<option style="background:#FFF" value="Bike Batteries" >Bike Batteries</option>
																				<option style="background:#FFF" value="Bus Batteries" >Bus Batteries</option>
																				<option style="background:#FFF" value="Tractor Batteries" >Tractor Batteries</option>
																				<option style="background:#FFF" value="Truck Batteries" >Truck Batteries</option>
																				<option style="background:#FFF" value="Truck Batteries" >Three Wheeler Batteries</option>
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
																		<div id="img2" align='center' style="margin-left:0%;margin-top:0.4%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left;position:absolute;padding: 1px;display: none;z-index:2;"><img src="./images/loader.gif" width="20" height="20"/></div>
																		<div class="styled-select">
																			<select name="vehicle_name" id ="vehicle_name" class="insidecontent" style='width:155px'>
																				<option style="background:#FFF" value="0" class='topindex1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;Make</option>
																			</select>
																			<select name="batt_capacity" id="batt_capacity" class="insidecontent"  style='width:155px;display:none;'>
																				<option style="background:#FFF" value="0" >&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;Capacity</option>
																			</select>
																		</div>
																	</td>
																	<td width="25%" align="left">
																		<div id="img3" align='center' style="margin-left:0%;margin-top:0.4%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left;position:absolute;padding: 1px;display: none;z-index:2;"><img src="./images/loader.gif" width="20" height="20"/></div>	
																		<div class="styled-select" >
																			<select name="vehicle_model" id ="vehicle_model" class="insidecontent" style='width:155px;'>
																				<option style="background:#FFF" value="0" class='topindex1' >&nbsp;&nbsp;&nbsp;Select&nbsp;Model</option>
																			</select>
																			<select name="bat_brand1" id ="bat_brand1" class="insidecontent" style='width:155px;display:none;' >
																				<option style="background:#FFF" value="0" class='topindex1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;Brand</option>
																		</div>
																	</td>
																	<td width="25%" align="left">
																		<div id="img4" align='center' style="margin-left:0%;margin-top:0.4%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left;position:absolute;padding: 1px;display: none;z-index:2;"><img src="./images/loader.gif" width="20" height="20"/></div>
																		<div class="styled-select">
																			<select name="bat_brand" id ="bat_brand" class="insidecontent" style='width:155px;' >
																				<option style="background:#FFF" value="0" class='topindex1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;Brand</option>
																			</select>
																		</div>
																	</td>						
																</tr>
																<tr><td height="5"></td><td height="5"></td><td height="5"></td><td height="5"></td></tr>
																<tr>
																	<td width="25%" align="left"  valign="top">
																		<div id="statediv">
																			<div id="img5" align='center' style="margin-left:0%;margin-top:0.4%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left;position:absolute;padding: 1px;display: none;z-index:2;"><img src="./images/loader.gif" width="20" height="20"/></div>
																			<div class="styled-select">
																				<select  name='state' id='state' class="insidecontent" style='width:155px;  bgcolor:#FFF;' >
																				<option style="background:#FFF" value="0" class='topindex1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;State</option>
																				</select>
																			</div>
																			</div>
																	</td>
																	<td width="25%" align="left" valign="top" >
																		<div id="citydiv">
																			<div id="img6" align='center' style="margin-left:0%;margin-top:0.4%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left;position:absolute;padding: 1px;display: none;z-index:2;"><img src="./images/loader.gif" width="20" height="20"/></div>
																			<div class="styled-select">
																				<select name="city" id ="city" class="insidecontent"  style='width:155px;' >
																					<option style="background:#FFF" value="0" class='topindex1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;Location</option>
																				</select>
																			</div>
																		</div>
																		<div id="pincode2" align="left">
																			<font style='font-family:Verdana;font-size:11px;color:#000000;' >Please Enter Your PinCode:</font>
																		</div>
																	</td>
																	<td width="25%" align="left" valign="top">
																		<div id="areadiv">
																			<div id="img7" align='center' style="margin-left:0%;margin-top:0.4%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left;position:absolute;padding: 1px;display: none;z-index:2;"><img src="./images/loader.gif" width="20" height="20"/></div>
																			<div class="styled-select">
																				<select name="area" id ="area" class="insidecontent"  style='width:155px;' >
																					<option style="background:#FFF" value="0"  class='topindex1'>&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;Area</option>
																				</select>
																			</div>
																		</div>							
																		<div id="pincode3">
																			<input class='insidecontent' type='text' name='pincode' id='pincode' placeholder='517001' style='width:149px;height:28px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='40'/>
																		</div>
																	</td>
																	<td width="10%" align="left" valign="top"><a href="javascript:funToGetbatterydetails();"><img src="./images/findbttry3.png" alt="Order car battery online/inverter battery online for best discounted price with valid warranty period and install car BookBattery or inverter BookBattery or bike BookBattery with free installation charges." border="0" width="155" height="35" /></a>
																	</td>
																</tr>
																
																<tr id="searchpin"><td></td><td></td><td align="center" ><a href="javascript:changetopincode();" class="footerbat">Order&nbsp;With&nbsp;Pin&nbsp;Code</a></td></tr>
																<tr id="searcharea"><td></td><td></td><td align="left" ><a href="javascript:changetoarea();" class="footerbat">Order&nbsp;With&nbsp;State,City,Area</a></td></tr>
																
															</table>
														</td>
														<td valign="top" width="30%" align="right">
															<table width="100%" border="0" valign="top">
																<tr><td height="10"></td></tr>
																<tr>
																	<td width="88%" align="right"><a href="./index.jsp" style='font-family:Verdana;font-size:11px;color:#000000; text-decoration:none;padding:1px 1px;' title="Order for new battery">Order&nbsp;New&nbsp;Battery</a></td><td width="12%" align="right"><img src="./images/newBookBattery.png" border="0" valign="bottom" width="20" height="20" alt="Order new battery"/></td>
																</tr>
																<tr><td height="1"></td></tr>
																<tr>
																	<td width="88%" align="right"><a href="./batteryhealthcheck.jsp" style='font-family:Verdana;font-size:11px;color:#FF8C00; text-decoration:none;padding:1px 1px;' title="Regular check up of your battery is important.">Order&nbsp;For&nbsp;Free&nbsp;Battery&nbsp;Health&nbsp;Check</a></td><td width="12%" align="right"><img src="./images/image2.png" border="0" valign="bottom" width="20" height="20" alt="Battery health check"/></td>
																</tr>
																<tr><td height="1"></td></tr>
																<tr>
																	<td width="88%" align="right"><a href="./batterycheckyourself.jsp" style='font-family:Verdana;font-size:11px;color:#000000; text-decoration:none;padding:1px 1px;' title="Click link to view videos on how to maintain & replace a battery.">How&nbsp;To&nbsp;Check&nbsp;A&nbsp;Car&nbsp;Battery Yourself</a></td><td width="12%" align="right"><img src="./images/video_icon.png" border="0" valign="bottom" width="20" height="20" alt="How to check battery"/></td>
																</tr>
																<tr><td height="1"></td></tr>
																<tr>
																	<td width="88%" align="right"><a href="./registerbattery.jsp" style='font-family:Verdana;font-size:11px;color:#000000; text-decoration:none;padding:1px 1px;' title="Register and get SMS on when to replace the battery.">Register&nbsp;Your&nbsp;Battery&nbsp;Details</a></td><td width="12%" align="right"><img src="./images/new_battery.png" border="0" valign="bottom" width="20" height="20" alt="Register battery"/></td>
																</tr>
																<tr><td height="1"></td></tr>
																<tr>
																	<td width="88%" align="right"><a href="./knowaboutBookBattery.jsp" style='font-family:Verdana;font-size:11px; color:#000000; text-decoration:none; padding:1px 1px;' title="Learn about a battery warranty, price etc.">About&nbsp;Batteries</a></td><td width="12%" align="right"><img src="./images/image1.png" border="0" valign="bottom" width="20" height="20" alt="About BookBattery"/></td>
																</tr>
																<tr><td height="1"></td></tr>
																<tr>
																	<td width="88%" align="right"><a href="./checkwarranty.jsp" style='font-family:Verdana;font-size:11px; color:#000000; text-decoration:none; padding:1px 1px;'  title="Check your car battery or bike battery or inverter battery warranty">Check&nbsp;Your&nbsp;Warranty&nbsp;Period</a></td><td width="12%" align="right"><img src="./images/warranty.jpg" border="0"  width="20" height="20" alt="Know more about the car battery or inverter battery or bike battery"/></td>
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
					<tr><td height="135"></td></tr>
					<!-- Inner content ends here -->
					<!-- Footer Starts Here -->
					<tr>
						<td>
							<jsp:include page = "footer.jsp" />
						</td>
					</tr>                           
	<!-- footer Ends Here -->
				</td>
			</tr>
		</table>
<input type="hidden" name="strEmail" value="">
<input type="hidden" name="publicUrl" id='publicUrl' value="<%=publicUrl%>">
</form>
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
<script>
$(window).load(function() {
	 $('#scrollpopup').hide();
});
if (/MSIE (\d+\.\d+);/.test(navigator.userAgent))
{
	var ieversion=new Number(RegExp.$1)
	if (ieversion > 9)
	{
		(function() {
		var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
		po.src = './js/jqFancyTransitions.1.8.min.js';
		var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
		})();
	} 
}
browsername=navigator.appName;
if ((browsername.indexOf("Netscape")!=-1) || (browsername.indexOf("Opera")!=-1))
{
		(function() {
		var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
		po.src = './js/jqFancyTransitions.1.8.min.js';
		var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
		})();
}
</script>
</html>
