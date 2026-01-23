<%-- 
Document   : knowaboutBookBattery.jsp
Created on : Dec 26, 2013, 10:10:12 AM
Author     : Sai Krishna Daddala
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.*,com.ngit.javabean.loglevel.LogLevel,javax.servlet.ServletContext,java.util.Properties,java.io.FileInputStream"%>
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
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=baseURL%>"></base>
<title>BookBattery.com-India's No.1 Automobile Battery Store</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="/bookbattery/images/favicon.png" type="image/x-icon">
<meta name='Title' content='BookBattery.com - Learn About Batteries' />
<meta name='description' content="Learn About Your Batteries, Battery Condition, Battery Life, Battery Health, and How to Maintain Your Batteries, at BookBattery or Call 09603467559 for More Details" />
<meta name='keywords' content='Learn About Batteries, Learn About Your Battery, All About Batteries, Learn More About Battery, BookBattery' />
<meta http-equiv="Refresh" content="0; url=<%=publicUrl%>/bookbattery/">
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="Expires" CONTENT="0"/>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache"/>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"/>
<META HTTP-EQUIV="Cache-Control" CONTENT="no-store"/>
<meta property="fb:admins" content="450845698352781" />

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

<link href="/bookbattery/css/bookbattery.css?<%=context.getInitParameter("cornercss")%>" rel="stylesheet" type="text/css" />
<script src="/bookbattery/js/jquery-1.3.2.min.js"></script>
<script language="JavaScript" src="/bookbattery/js/popupfrontend.js" ></script>

<script language="JavaScript" src="/bookbattery/js/jquery-1.8.2.js" ></script>
<style type='text/css'>
/*<![CDATA[*/

table{font-size:inherit;font:100%;}pre,code,kbd,samp,tt{font-family:monospace;*font-size:108%;line-height:100%;}

td.loading_image
{
	background:  url('/bookbattery/images/loader.gif');width:100px; /* use you own image size; */ 
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

.divmobilelaptop{left:37.5%;top:15%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left; border:6px solid #424242; border-radius: 6px 6px 6px 6px;position:absolute;padding: 1px;display: none;z-index:60;}

.preloader{left:45.5%;top:85%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left; border:1px solid #FF8C00;position:absolute;padding: 1px;display: none;z-index:60;}

/*]]>*/

</style>
<script type="text/javascript">
//<![CDATA[

$(document).ready(function(){
	$('#pincode2').hide();
	$('#pincode3').hide();
	$('#searcharea').hide();
	prepareInputsForHints()
	$category = $('#battery_type');
        $category.change(
        function() {
		var splitvalb =$category.val();
			document.getElementById("displysesmsg").innerHTML="";
			for(i=document.knowaboutBookBattery.vehicle_model.options.length-1;i>=1;i--)
			{
			document.knowaboutBookBattery.vehicle_model.remove(i);
			}
			for(i=document.knowaboutBookBattery.bat_brand.options.length-1;i>=1;i--)
			{
				document.knowaboutBookBattery.bat_brand.remove(i);
			}
			for(i=document.knowaboutBookBattery.bat_brand1.options.length-1;i>=1;i--)
			{
				document.knowaboutBookBattery.bat_brand1.remove(i);
			}
			for(i=document.knowaboutBookBattery.state.options.length-1;i>=1;i--)
			{
				document.knowaboutBookBattery.state.remove(i);
			}
			for(i=document.knowaboutBookBattery.city.options.length-1;i>=1;i--)
			{
				document.knowaboutBookBattery.city.remove(i);
			}
			for(i=document.knowaboutBookBattery.area.options.length-1;i>=1;i--)
			{
				document.knowaboutBookBattery.area.remove(i);
			}
			for(i=document.knowaboutBookBattery.laptop_model.options.length-1;i>=1;i--)
			{
				document.knowaboutBookBattery.laptop_model.remove(i);
			}
			for(i=document.knowaboutBookBattery.laptop_product.options.length-1;i>=1;i--)
			{
				document.knowaboutBookBattery.laptop_product.remove(i);
			}		
			document.knowaboutBookBattery.battery_type.focus();
		if(splitvalb=="Inverter Batteries")
		 {
			$('#img2').show();
		    $.ajax({
                    type: "GET",
                    url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getbatterycapacity",
                    data: {batterytype: $category.val(), batterytype: $category.val() },
                    success: function(data)
					{
						$('#img2').hide();
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
			document.knowaboutBookBattery.batt_capacity.focus();
		   }
		    else if(splitvalb=="Laptop Batteries")
		  {
				$('#img2').show();
				$.ajax
				({
                    type: "GET",
                    url: "/bookbattery/servlet/LaptopBatteryDetails?hidWhatToDo=getlaptopname",
                    data: {batterytype: $category.val(), batterytype: $category.val() },
                    success: function(data)
					{
						$('#img2').hide();
                        $("#laptop_name").html(data)
                    }
                });
				$('#vehicle_name').hide();
				$('#vehicle_model').hide();
				$('#laptop_name').show();
				$('#bat_brand').hide();
				$('#laptop_model').show();
				$('#statediv').show();
				$('#citydiv').show();
				$('#areadiv').show();
				$('#bat_brand1').hide();
				$('#batt_capacity').hide();
				$('#searchpin').hide();
				$('#laptop_product').show();
				$('#laptopsbutton').show();
				$('#findbatterybutton').hide();
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
				document.knowaboutBookBattery.laptop_name.focus();
		   }
		   else if(splitvalb=="Inverter")
		   {
				location.href="/bookbattery/inverter.jsp"
		   }
		 else
		  {
			 $('#img2').show();
                $.ajax({
                    type: "GET",
                    url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getvehiclename",
                    data: {batterytype: $category.val() },
                    success: function(data)
					{
						$('#img2').hide();
						$('#vehicle_name').show();
						$('#vehicle_model').show();
						$('#bat_brand').show();
						$('#bat_brand1').hide();
						$('#batt_capacity').hide();$('#vehicle_name').show();
						$('#vehicle_model').show();
						$('#bat_brand').show();
						$('#bat_brand1').hide();
						$('#batt_capacity').hide();						
						$('#searchpin').show();
						$('#laptopsbutton').hide();
						$('#laptop_model').hide();
						$('#laptop_name').hide();
						$('#laptop_product').hide();
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
							document.knowaboutBookBattery.battery_type.focus();
						}
						else
						{
							$("#vehicle_name").html(data)
							document.knowaboutBookBattery.vehicle_name.focus();
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
				for(i=document.knowaboutBookBattery.laptop_model.options.length-1;i>=1;i--)
					{
					document.knowaboutBookBattery.laptop_model.remove(i);
					}
					for(i=document.knowaboutBookBattery.laptop_product.options.length-1;i>=1;i--)
					{
					document.knowaboutBookBattery.laptop_product.remove(i);
					}
					for(i=document.knowaboutBookBattery.state.options.length-1;i>=1;i--)
					{
					document.knowaboutBookBattery.state.remove(i);
					}
					for(i=document.knowaboutBookBattery.city.options.length-1;i>=1;i--)
					{
					document.knowaboutBookBattery.city.remove(i);
					}
					for(i=document.knowaboutBookBattery.area.options.length-1;i>=1;i--)
					{
					document.knowaboutBookBattery.area.remove(i);
					}		
				document.knowaboutBookBattery.laptop_name.focus();
				$('#img3').show();
                $.ajax
				({
                    type: "GET",
                    url: "/bookbattery/servlet/LaptopBatteryDetails?hidWhatToDo=getlaptop_model",
                    data: {laptopname: $category25.val(), batterytype: $category.val()},
					//data: {batterytype: $category.val() },
                    success: function(data)
					{
						$('#img3').hide();
						if(data.indexOf("defaultss")>=0)
						{
							$("#laptop_model").html(data)
							document.knowaboutBookBattery.laptop_name.focus();
						}
						else
						{
							$("#laptop_model").html(data)
							document.knowaboutBookBattery.laptop_model.focus();
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
				for(i=document.knowaboutBookBattery.laptop_product.options.length-1;i>=1;i--)
				{
				document.knowaboutBookBattery.laptop_product.remove(i);
				}
				for(i=document.knowaboutBookBattery.state.options.length-1;i>=1;i--)
				{
				document.knowaboutBookBattery.state.remove(i);
				}
				for(i=document.knowaboutBookBattery.city.options.length-1;i>=1;i--)
				{
				document.knowaboutBookBattery.city.remove(i);
				}
				for(i=document.knowaboutBookBattery.area.options.length-1;i>=1;i--)
				{
				document.knowaboutBookBattery.area.remove(i);
				}	
				document.knowaboutBookBattery.laptop_model.focus();
				$('#img4').show();
                $.ajax({
                    type: "GET",
                    url: "/bookbattery/servlet/LaptopBatteryDetails?hidWhatToDo=getlaptop_product",
                    data: {laptopname: $category25.val(), batterytype: $category.val(), laptopmodel: $categoryproduct.val()},
					//data: {batterytype: $category.val() },
                    success: function(data)
					{
						$('#img4').hide();
						if(data.indexOf("defaultss")>=0)
						{
							$("#laptop_product").html(data)
							document.knowaboutBookBattery.laptop_model.focus();
						}
						else
						{
							$("#laptop_product").html(data)
							document.knowaboutBookBattery.laptop_product.focus();
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
			for(i=document.knowaboutBookBattery.bat_brand.options.length-1;i>=1;i--)
			{
			document.knowaboutBookBattery.bat_brand.remove(i);
			}
			for(i=document.knowaboutBookBattery.state.options.length-1;i>=1;i--)
			{
			document.knowaboutBookBattery.state.remove(i);
			}
			for(i=document.knowaboutBookBattery.city.options.length-1;i>=1;i--)
			{
			document.knowaboutBookBattery.city.remove(i);
			}
			for(i=document.knowaboutBookBattery.area.options.length-1;i>=1;i--)
			{
			document.knowaboutBookBattery.area.remove(i);
			}
			document.knowaboutBookBattery.vehicle_name.focus();
			$('#img3').show();
                $.ajax({
                    type: "GET",
                    url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getvehicle_model",
                    data: {vehiclename: $category2.val(), batterytype: $category.val()},
					//data: {batterytype: $category.val() },
                    success: function(data)
					{
						$('#img3').hide();
						if(data.indexOf("defaultss")>=0)
						{
							$("#vehicle_model").html(data)
							document.knowaboutBookBattery.vehicle_name.focus();
						}
						else
						{
							 $("#vehicle_model").html(data)
							document.knowaboutBookBattery.vehicle_model.focus();
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
					
						for(i=document.knowaboutBookBattery.bat_brand.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.bat_brand.remove(i);
						}
						for(i=document.knowaboutBookBattery.state.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.state.remove(i);
						}
						for(i=document.knowaboutBookBattery.city.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.city.remove(i);
						}
						for(i=document.knowaboutBookBattery.area.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.area.remove(i);
						}
						document.knowaboutBookBattery.vehicle_model.focus();
					if(splitval == "default")
						{
						for(i=document.knowaboutBookBattery.bat_brand.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.bat_brand.remove(i);
						}
						for(i=document.knowaboutBookBattery.state.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.state.remove(i);
						}
						for(i=document.knowaboutBookBattery.city.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.city.remove(i);
						}
						for(i=document.knowaboutBookBattery.area.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.area.remove(i);
						}
						document.knowaboutBookBattery.vehicle_model.focus();
					}
					else
					{
						$('#img4').show();
					 $.ajax({
                    type: "GET",
                    url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getbat_brand",
                    data: {vehiclemodel: $category3.val() },
                    success: function(data)
					{
						$('#img4').hide();
						if(data.indexOf("defaultss")>=0)
						{
                        $("#bat_brand").html(data)
						document.knowaboutBookBattery.vehicle_model.focus();
						}
						else
						{
						 $("#bat_brand").html(data)
						document.knowaboutBookBattery.bat_brand.focus();
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
					
						for(i=document.knowaboutBookBattery.bat_brand1.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.bat_brand1.remove(i);
						}
						for(i=document.knowaboutBookBattery.state.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.state.remove(i);
						}
						for(i=document.knowaboutBookBattery.city.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.city.remove(i);
						}
						for(i=document.knowaboutBookBattery.area.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.area.remove(i);
						}
						document.knowaboutBookBattery.batt_capacity.focus();
						if(splitvalban == "default")
						{
						for(i=document.knowaboutBookBattery.bat_brand1.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.bat_brand1.remove(i);
						}
						for(i=document.knowaboutBookBattery.state.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.state.remove(i);
						}
						for(i=document.knowaboutBookBattery.city.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.city.remove(i);
						}
						for(i=document.knowaboutBookBattery.area.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.area.remove(i);
						}
						document.knowaboutBookBattery.batt_capacity.focus();
						}
						else
						{
							$('#img3').show();
				 $.ajax({
                    type: "GET",
                    url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getbat_brand",
                    data: {vehiclemodel: $category4.val() },
                    success: function(data)
					{
						$('#img3').hide();
						if(data.indexOf("defaultss")>=0)
						{
                        $("#bat_brand1").html(data)
						document.knowaboutBookBattery.batt_capacity.focus();
						}
						else
						{
						 $("#bat_brand1").html(data)
						document.knowaboutBookBattery.bat_brand1.focus();
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
						for(i=document.knowaboutBookBattery.state.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.state.remove(i);
						}
						for(i=document.knowaboutBookBattery.city.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.city.remove(i);
						}
						for(i=document.knowaboutBookBattery.area.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.area.remove(i);
						}
						document.knowaboutBookBattery.bat_brand.focus();
						if(splitvalcity == "default")
						{
						for(i=document.knowaboutBookBattery.state.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.state.remove(i);
						}
						for(i=document.knowaboutBookBattery.city.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.city.remove(i);
						}
						for(i=document.knowaboutBookBattery.area.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.area.remove(i);
						}
						document.knowaboutBookBattery.bat_brand.focus();
						}
						else
						{
							$('#img5').show();
					$.ajax({
                    type: "GET",
                    url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getstate",
                    data: {brands: $categorybat.val() },
                    success: function(data)
					{
						$('#img5').hide();
						if(data.indexOf("defaultss")>=0)
						{
                        $("#state").html(data)
						document.knowaboutBookBattery.state.focus();
						}
						else
						{
						 $("#state").html(data)
						document.knowaboutBookBattery.state.focus();
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
						for(i=document.knowaboutBookBattery.state.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.state.remove(i);
						}
						for(i=document.knowaboutBookBattery.city.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.city.remove(i);
						}
						for(i=document.knowaboutBookBattery.area.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.area.remove(i);
						}
						document.knowaboutBookBattery.bat_brand1.focus();
						if(splitvalcity == "default")
						{
						for(i=document.knowaboutBookBattery.state.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.state.remove(i);
						}
						for(i=document.knowaboutBookBattery.city.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.city.remove(i);
						}
						for(i=document.knowaboutBookBattery.area.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.area.remove(i);
						}
						document.knowaboutBookBattery.bat_brand1.focus();
						}
						else
						{
						$('#img5').show();
					$.ajax({
                    type: "GET",
                    url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getstate",
                    data: {brands: $category21.val() },
                    success: function(data)
					{
						$('#img5').hide();
						if(data.indexOf("defaultss")>=0)
						{
                        $("#state").html(data)
						document.knowaboutBookBattery.state.focus();
						}
						else
						{
						 $("#state").html(data)
						document.knowaboutBookBattery.state.focus();
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
					
						for(i=document.knowaboutBookBattery.state.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.state.remove(i);
						}
						for(i=document.knowaboutBookBattery.city.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.city.remove(i);
						}
						for(i=document.knowaboutBookBattery.area.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.area.remove(i);
						}
						document.knowaboutBookBattery.laptop_product.focus();
						if(splitvalcity == "default")
						{
							for(i=document.knowaboutBookBattery.state.options.length-1;i>=1;i--)
							{
							document.knowaboutBookBattery.state.remove(i);
							}
							for(i=document.knowaboutBookBattery.city.options.length-1;i>=1;i--)
							{
							document.knowaboutBookBattery.city.remove(i);
							}
							for(i=document.knowaboutBookBattery.area.options.length-1;i>=1;i--)
							{
							document.knowaboutBookBattery.area.remove(i);
							}
							document.knowaboutBookBattery.laptop_product.focus();
						}
						else
						{
							$('#img5').show();
							$.ajax({
								type: "GET",
								url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getstate",
								data: {laptop_product: $category212.val() },
								success: function(data)
								{
									$('#img5').hide();
									if(data.indexOf("defaultss")>=0)
									{
										$("#state").html(data)
										document.knowaboutBookBattery.state.focus();
									}
									else
									{
										$("#state").html(data)
										document.knowaboutBookBattery.state.focus();
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
					
						for(i=document.knowaboutBookBattery.city.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.city.remove(i);
						}
						for(i=document.knowaboutBookBattery.area.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.area.remove(i);
						}
						document.knowaboutBookBattery.state.focus();
						if(splitvalcity == "default")
						{
						for(i=document.knowaboutBookBattery.city.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.city.remove(i);
						}
						for(i=document.knowaboutBookBattery.area.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.area.remove(i);
						}
						document.knowaboutBookBattery.state.focus();
						}
						else
						{
							$('#img6').show();
					$.ajax({
                    type: "GET",
                    url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getcity",
                    data: {state: $category5.val() },
                    success: function(data)
					{
						$('#img6').hide();
						if(data.indexOf("defaultss")>=0)
						{
                        $("#city").html(data)
						document.knowaboutBookBattery.city.focus();
						}
						else
						{
						 $("#city").html(data)
						document.knowaboutBookBattery.city.focus();
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
					
						for(i=document.knowaboutBookBattery.area.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.area.remove(i);
						}
						document.knowaboutBookBattery.city.focus();
						if(splitvalarea == "default")
						{
						for(i=document.knowaboutBookBattery.area.options.length-1;i>=1;i--)
						{
						document.knowaboutBookBattery.area.remove(i);
						}
						document.knowaboutBookBattery.city.focus();
						}
						else
						{
							$('#img7').show();
				$.ajax({
                    type: "GET",
                    url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getarea",
                    data: {city: $category6.val() },
                    success: function(data)
					{
						$('#img7').hide();
						if(data.indexOf("defaultss")>=0)
						{
                        $("#area").html(data)
						document.knowaboutBookBattery.area.focus();
						}
						else
						{
						 $("#area").html(data)
						document.knowaboutBookBattery.area.focus();
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
function funToGetbatterydetails()
{
	var batterytype = document.knowaboutBookBattery.battery_type.value;      
	var vehiclename = document.knowaboutBookBattery.vehicle_name.value;
	var vehiclemodel = escape(document.knowaboutBookBattery.vehicle_model.value);
	var batterybrand = document.knowaboutBookBattery.bat_brand.value;
	var batterybrand1 = document.knowaboutBookBattery.bat_brand1.value;
	var batterycapty = document.knowaboutBookBattery.batt_capacity.value;
	var state = document.knowaboutBookBattery.state.value;
	var city = document.knowaboutBookBattery.city.value;
	var area = document.knowaboutBookBattery.area.value;
	var pincode = document.knowaboutBookBattery.pincode.value;

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
	if(document.knowaboutBookBattery.battery_type.value == 0)
	{
		errMsg ="<font color='#ff3333'>Please Select \'Battery Type\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.knowaboutBookBattery.battery_type.focus();
		return ;
	}
	if(document.knowaboutBookBattery.batt_capacity.value == 0 || batterycapty=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'Battery Capacity\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.knowaboutBookBattery.batt_capacity.focus();
		return ;
	}
	if(document.knowaboutBookBattery.bat_brand1.value == 0 || batterybrand1=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'Battery Brand\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.knowaboutBookBattery.bat_brand1.focus();
		return ;
	}
	vehiclename = "";
	vehiclemodel = "";
	batterybrand = batterybrand1;
	}
	else
	{
	if(document.knowaboutBookBattery.battery_type.value == 0)
	{
		errMsg ="<font color='#ff3333'>Please Select \'Battery Type\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.knowaboutBookBattery.battery_type.focus();
		return ;
	}
	if(document.knowaboutBookBattery.vehicle_name.value == 0 || vehiclename=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'Make\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.knowaboutBookBattery.vehicle_name.focus();
		return ;
	}
	if(document.knowaboutBookBattery.vehicle_model.value == 0 || vehiclemodel=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'Model\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.knowaboutBookBattery.vehicle_model.focus();
		return ;
	}
	vehiclename = vehiclename;
	vehiclemodel = vehiclemodel;
	batterybrand = batterybrand;
	batterycapty = "";
	if(document.knowaboutBookBattery.bat_brand.value == 0 || batterybrand=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'Battery Brand\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.knowaboutBookBattery.bat_brand.focus();
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
			document.knowaboutBookBattery.pincode.focus();
			return ;
		}
		if(document.knowaboutBookBattery.state.value == 0 || state=="default")
		{
			errMsg ="<font color='#ff3333'>Please Select \'State\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.knowaboutBookBattery.state.focus();
			return ;
		}
		if(document.knowaboutBookBattery.city.value == 0 || city=="default")
		{
			errMsg ="<font color='#ff3333'>Please Select \'Location\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.knowaboutBookBattery.city.focus();
			return ;
		}
		if(document.knowaboutBookBattery.area.value == 0 || area=="default")
		{
			errMsg ="<font color='#ff3333'>Please Select \'Area\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.knowaboutBookBattery.area.focus();
			return ;
		}
	}
	else
	{
		if(document.knowaboutBookBattery.pincode.value == "" || pincode=="default")
		{
			errMsg ="<font color='#ff3333'>Please Enter \'Pincode\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.knowaboutBookBattery.pincode.focus();
			return ;
		}
		var pincoderegex=/^[0-9]{4,6}$/;
		if(!pincode.match(pincoderegex))
		{
			errMsg ="<font color='#ff3333'>Please Enter Valid \'Pincode\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.knowaboutBookBattery.pincode.focus();
			return ;
		}
	}
		var sentence="";
	if(batterytype=="Bike Batteries")
	{
		sentence="<tr><td height='15' align='left' style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;'><img src='/bookbattery/images/arrow2.PNG' height='10' width='15'/>&nbsp;&nbsp;Amaron is the Only Battery with SilvenX Alloy for Long Lasting Life and Fits for any Vehicle</td></tr><tr><td height='15' align='left' style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;'><img src='/bookbattery/images/arrow2.PNG' height='10' width='15'/>&nbsp;&nbsp;Get the latest stock Battery from an <font color='#FF8C00'><b>Official Amaron Online Dealer</b></font> like us</td></tr><tr><td height='15' width='90%' align='left' style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;'><img src='/bookbattery/images/arrow2.PNG' height='10' width='15'/>&nbsp;&nbsp;Get <font color='#FF8C00'><b>Original Warranty</b></font> from an <font color='#FF8C00'><b>Official Amaron Online Dealer</b></font> like us</td></tr><tr><td height='15' align='left' style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;'><img src='/bookbattery/images/arrow2.PNG' height='10' width='15'/>&nbsp;&nbsp;Get additional 1% Cash Back by Talking to our Support Person</td></tr><tr><td height='15' align='left' style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;'><img src='/bookbattery/images/arrow2.PNG' height='10' width='15'/>&nbsp;&nbsp;Nearest installation contact details will be sent in SMS for you to go and get it fitted there</td></tr><tr><td width='100%' align='left' style='font-family:Verdana;font-size:11px;color:red;	text-decoration:none;padding:1px 1px;'><font color='#ff0000'>&nbsp;&nbsp;*</font>&nbsp;&nbsp;<I>Delivery&nbsp;of&nbsp;Bike&nbsp;Battery&nbsp;to&nbsp;your&nbsp;place&nbsp;is&nbsp;not&nbsp;avaliable.</I></td></tr>";
	}
	else if(batterytype=="Inverter Batteries")
	{
		sentence="<tr><td height='15' align='left' style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;'><img src='/bookbattery/images/arrow2.PNG' height='10' width='15'/>&nbsp;&nbsp;Get the Battery Delivered and Fitted At Your Place - Avoid Traffic and <font color='#FF8C00'><b>Save on Your Fuel Costs</b></font></td></tr><tr><td height='15' align='left' style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;'><img src='/bookbattery/images/arrow2.PNG' height='10' width='15'/>&nbsp;&nbsp;Get the latest stock Battery from an <font color='#FF8C00'><b>Official Amaron Online Dealer</b></font> like us</td></tr><tr><td height='15' width='90%' align='left' style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;'><img src='/bookbattery/images/arrow2.PNG' height='10' width='15'/>&nbsp;&nbsp;Get <font color='#FF8C00'><b>Original Warranty</b></font> from an <font color='#FF8C00'><b>Official Amaron Online Dealer</b></font> like us</td></tr><tr><td height='15' align='left' style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;'><img src='/bookbattery/images/arrow2.PNG' height='10' width='15'/>&nbsp;&nbsp;Pay only when the Battery is Delivered and Fitted – <font color='#FF8C00'><b>No Upfront Payment</b></font></td></tr><tr><td height='15' align='left' style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;'><img src='/bookbattery/images/arrow2.PNG' height='10' width='15'/>&nbsp;&nbsp;Get additional 1% Cash Back by Talking to our Support Person</td></tr>";
	}
	else
	{
		sentence="<tr><td height='15' align='left' style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;'><img src='/bookbattery/images/arrow2.PNG' height='10' width='15'/>&nbsp;&nbsp;Get the Battery Delivered and Fitted At Your Place - Avoid Traffic and <font color='#FF8C00'><b>Save on Your Fuel Costs</b></font></td></tr><tr><td height='15' align='left' style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;'><img src='/bookbattery/images/arrow2.PNG' height='10' width='15'/>&nbsp;&nbsp;Amaron is the Only Battery with SilvenX Alloy for Long Lasting Life and Fits for any Vehicle</td></tr><tr><td height='15' align='left' style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;'><img src='/bookbattery/images/arrow2.PNG' height='10' width='15'/>&nbsp;&nbsp;Get the latest stock Battery from an <font color='#FF8C00'><b>Official Amaron Online Dealer</b></font> like us</td></tr><tr><td height='15' width='90%' align='left' style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;'><img src='/bookbattery/images/arrow2.PNG' height='10' width='15'/>&nbsp;&nbsp;Get <font color='#FF8C00'><b>Original Warranty</b></font> from an <font color='#FF8C00'><b>Official Amaron Online Dealer</b></font> like us</td></tr><tr><td height='15' align='left' style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;'><img src='/bookbattery/images/arrow2.PNG' height='10' width='15'/>&nbsp;&nbsp;Pay only when the Battery is Delivered and Fitted – <font color='#FF8C00'><b>No Upfront Payment</b></font></td></tr><tr><td height='15' align='left' style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;'><img src='/bookbattery/images/arrow2.PNG' height='10' width='15'/>&nbsp;&nbsp;Get additional 1% Cash Back by Talking to our Support Person</td></tr>";
	}
	
	errMsg ="<table cellspacing='10' cellpadding='0' bgcolor='#FFFFFF'><table width='610' bgcolor='#FFFFFF' valign='top'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobilediv(greyout(false));\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b>				</td></tr></table><tr><td><table width='610'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;'><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font size='2' color='#FF8C00'>Please Enter Your Mobile Number!</font></td></tr></table></td><tr><td height='10'></td></tr></tr><tr><td width='100%' align='center'><input class='insidecontent' type='tel' autocomplete='off' name='usermobilenumber' id='usermobilenumber' placeholder='9603467559' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='10' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:askmobilenumber('"+batterytype+"','"+vehiclename+"','"+vehiclemodel+"','"+batterybrand+"','"+batterycapty+"','"+state+"','"+city+"','"+area+"','"+pincode+"',Submitrret);return false;} else return true;\"/></td></tr><tr><td height='5'></td></tr><tr><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff0000'>*</font>&nbsp;Enter&nbsp;your&nbsp;10&nbsp;digit&nbsp;mobile&nbsp;number</td></tr><tr><td  width='50%' align='center' ><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:askmobilenumber('"+batterytype+"','"+vehiclename+"','"+vehiclemodel+"','"+batterybrand+"','"+batterycapty+"','"+state+"','"+city+"','"+area+"','"+pincode+"',Submitrret);\"></td></tr><tr height='16'><td colspan='3' align='center' class='subheading' id='displayrefinederrormsg1'></td></tr><tr><td height='15' align='left' style='font-family:Verdana;font-size:16px;color:#FF8C00;text-decoration:none;padding:1px 1px;'></font><b>Why Us:</b></td></tr><tr><td height='5'></td></tr>"+sentence+"<tr><td height='10'></td></tr></table>";
	document.getElementById("divmobile").innerHTML=""; 
	document.getElementById("divmobile").style.display='block';
	document.getElementById("divmobile").innerHTML=errMsg
	greyout(true);	
	document.knowaboutBookBattery.usermobilenumber.focus();	
}
function askmobilenumber(batterytype,vehiclename,vehiclemodel,batterybrand,batterycapty,state,city,area,pincode,varButton)
{
	var strUsermobileno=document.knowaboutBookBattery.usermobilenumber.value;
	if(strUsermobileno == 0 || strUsermobileno == "")
	{
		errMsg ="<font color='#9B5BDD'>Please enter Mobile Number...!</font>";
		document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
		document.knowaboutBookBattery.usermobilenumber.focus();
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
		document.knowaboutBookBattery.usermobilenumber.value="";
		document.knowaboutBookBattery.usermobilenumber.focus();
		return ;
		
		}
	}
	if (document.getElementById("usermobilenumber").value.length<10)
	{
		errMsg ="<font color='#9B5BDD'>Please enter valid Number...!</font>";
		document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
		document.knowaboutBookBattery.usermobilenumber.focus();
		return;
	}
	if (document.getElementById("usermobilenumber").value.length==10)
	{
		if (strUsermobileno < 7000000000 )
		 {
			errMsg ="<font color='#9B5BDD'>Number Should start with 7 or 8 or 9...!</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.knowaboutBookBattery.usermobilenumber.focus();
			return ;
		 }
	}  
	if (document.getElementById("usermobilenumber").value.length>10)
	{
		if (strUsermobileno < 917000000000 || strUsermobileno >= 920000000000 )
		{
			errMsg ="<font color='#9B5BDD'>Number Should start with 91-7 or 8 or 9...!</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.knowaboutBookBattery.usermobilenumber.focus();
			return ;
		}
	}
	varButton.disabled=true;
	varButton.value='Please Wait...';
	document.knowaboutBookBattery.method="post";
	document.knowaboutBookBattery.action="/bookbattery/servlet/BatteryDetails?hidWhatToDo=getbatdetails&batterytype="+batterytype+"&vehiclename="+vehiclename+"&vehiclemodel="+vehiclemodel+"&batterybrand="+batterybrand+"&batterycapty="+batterycapty+"&state="+state+"&city="+city+"&area="+area+"&pincode="+pincode+"&Usermobileno="+strUsermobileno;
	document.knowaboutBookBattery.submit();
}
function getLaptopBatteryDetails()
{
	var batterytype = document.knowaboutBookBattery.battery_type.value;      
	var laptopname = document.knowaboutBookBattery.laptop_name.value;
	var laptopmodel = escape(document.knowaboutBookBattery.laptop_model.value);	
	var laptopproduct = escape(document.knowaboutBookBattery.laptop_product.value);
	var state = document.knowaboutBookBattery.state.value;
	var city = document.knowaboutBookBattery.city.value;
	var area = document.knowaboutBookBattery.area.value;

	if(document.knowaboutBookBattery.battery_type.value == 0)
	{
		errMsg ="<font color='#ff3333'>Please Select \'Battery Type\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.knowaboutBookBattery.battery_type.focus();
		return ;
	}
	if(document.knowaboutBookBattery.laptop_name.value == 0 || laptopname=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'Laptop Make\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.knowaboutBookBattery.laptop_name.focus();
		return ;
	}
	if(document.knowaboutBookBattery.laptop_model.value == 0 || laptopmodel=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'Laptop Model\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.knowaboutBookBattery.laptop_model.focus();
		return ;
	}
	if(document.knowaboutBookBattery.laptop_product.value == 0 || laptopproduct=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'Laptop Product\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.knowaboutBookBattery.laptop_product.focus();
		return ;
	}
	if(document.knowaboutBookBattery.state.value == 0 || state=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'State\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.knowaboutBookBattery.state.focus();
		return ;
	}
	if(document.knowaboutBookBattery.city.value == 0 || city=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'Location\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.knowaboutBookBattery.city.focus();
		return ;
	}
	if(document.knowaboutBookBattery.area.value == 0 || area=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'Area\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.knowaboutBookBattery.area.focus();
		return ;
	}
	errMsg ="<table cellspacing='10' cellpadding='0' bgcolor='#FFFFFF'><table width='310' bgcolor='#FFFFFF' valign='top'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobilelaptopdiv(greyout(false));\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b>				</td></tr></table><tr><td><table width='310'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;'><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font size='2' color='#FF8C00'>Please Enter Your Mobile Number!</font></td></tr></table></td><tr><td height='10'></td></tr></tr><tr><td width='100%' align='center'><input class='insidecontent' type='tel' autocomplete='off' name='usermobilenumber' id='usermobilenumber' placeholder='9603467559' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='10' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:askmobilenumberlaptop('"+batterytype+"','"+laptopname+"','"+laptopmodel+"','"+laptopproduct+"','"+state+"','"+city+"','"+area+"',Submitrret);return false;} else return true;\"/></td></tr><tr><td height='5'></td></tr><tr><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff0000'>*</font>&nbsp;Enter&nbsp;your&nbsp;10&nbsp;digit&nbsp;mobile&nbsp;number</td></tr><tr><td  width='50%' align='center' ><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:askmobilenumberlaptop('"+batterytype+"','"+laptopname+"','"+laptopmodel+"','"+laptopproduct+"','"+state+"','"+city+"','"+area+"',Submitrret);\"></td></tr><tr height='16'><td colspan='3' align='center' class='subheading' id='displayrefinederrormsg1'></td></tr><tr><td height='10'></td></tr></table>";
	document.getElementById("divmobilelaptop").innerHTML=""; 
	document.getElementById("divmobilelaptop").style.display='block';
	document.getElementById("divmobilelaptop").innerHTML=errMsg
	greyout(true);	
	document.knowaboutBookBattery.usermobilenumber.focus();
	
}
function askmobilenumberlaptop(batterytype,laptopname,laptopmodel,laptopproduct,state,city,area,varButton)
{
	var strUsermobileno=document.knowaboutBookBattery.usermobilenumber.value;
	if(strUsermobileno == 0 || strUsermobileno == "")
	{
		errMsg ="<font color='#9B5BDD'>Please enter Mobile Number...!</font>";
		document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
		document.knowaboutBookBattery.usermobilenumber.focus();
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
		document.knowaboutBookBattery.usermobilenumber.value="";
		document.knowaboutBookBattery.usermobilenumber.focus();
		return ;
		
		}
	}
	if (document.getElementById("usermobilenumber").value.length<10)
	{
		errMsg ="<font color='#9B5BDD'>Please enter valid Number...!</font>";
		document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
		document.knowaboutBookBattery.usermobilenumber.focus();
		return;
	}
	if (document.getElementById("usermobilenumber").value.length==10)
	{
		if (strUsermobileno < 7000000000 )
		 {
			errMsg ="<font color='#9B5BDD'>Number Should start with 7 or 8 or 9...!</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.knowaboutBookBattery.usermobilenumber.focus();
			return ;
		 }
	}  
	if (document.getElementById("usermobilenumber").value.length>10)
	{
		if (strUsermobileno < 917000000000 || strUsermobileno >= 920000000000 )
		{
			errMsg ="<font color='#9B5BDD'>Number Should start with 91-7 or 8 or 9...!</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.knowaboutBookBattery.usermobilenumber.focus();
			return ;
		}
	}
	varButton.disabled=true;
	varButton.value='Please Wait...';
	document.knowaboutBookBattery.method="post";
	document.knowaboutBookBattery.action="/bookbattery/servlet/LaptopBatteryDetails?hidWhatToDo=getlaptopbatterydetails&batterytype="+batterytype+"&laptopname="+laptopname+"&laptopmodel="+laptopmodel+"&laptopproduct="+laptopproduct+"&state="+state+"&city="+city+"&area="+area+"&Usermobileno="+strUsermobileno+"&backlink=knowaboutBookBattery";
	document.knowaboutBookBattery.submit();

}
function closemobilediv()
{
	$('#divmobile').hide();
	greyout(false);
}
function closemobilelaptopdiv()
{
	$('#divmobilelaptop').hide();
	greyout(false);
}
function onarea()
{
	document.getElementById("displysesmsg").innerHTML="";

	$('#areadiv').show();
	$('#areassdiv').hide();
	document.knowaboutBookBattery.area.focus();
}
function changetopincode()
{
	
	document.getElementById("displysesmsg").innerHTML="";
	document.knowaboutBookBattery.pincode.value="";
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
function globalbanneradbattery(whatevertopad)
{
whatevertopad="Batteries";

	scrollglobal=whatevertopad;

	url="/bookbattery/servlet/ScrollingTopAdsServlet?hidWhatToDo=scrollcategorytopads&catname="+whatevertopad+"&requestno="+(Math.random()*100),true;
		
	$.get(url,function(response, status, xhr) {
	if (status == "success") 
	{
		$('#topads').html(response);
	}
	$('#topads').jqFancyTransitions({effect: 'wave', delay: 2250, width: 950, height: 150, links: true ,navigation: true,direction: 'left', strips: 1 });});
}
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
//]]>
</script>
</head>
<body  onload="document.knowaboutBookBattery.battery_type.focus();scrollingtopadsDefault();globalbanneradbattery('whatevertopad');">
<!-- On load images  -->

<div class="se-pre-con"></div>
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="knowaboutBookBattery">
<div id='divmobile' class='divmobile' style="display:none;"></div>
<div id='divmobilelaptop' class='divmobilelaptop' style="display:none;"></div>

<div id="preloader" class='preloader' style="display:none;"><table border='0' width='75px' bgcolor='#ffffff'><tr><td height='55px' align='center'><img src="/bookbattery/images/loader.gif" /></td></tr></table></div> 
<table border='0' cellpadding="0" width="950" align="center">
		<tr>
			<td>
				
	<jsp:include page = "header.jsp" />
	<table width="950" border="0" class="insidecontent c24" align="center" cellspacing="0" cellpadding="0" >
		<tr>
			<td align = "center" valign = "top" bgcolor = "#FFFFFF">
			<!-- Inner content should be within the below table -->
				<table width="100%" border="0" bgcolor="#FFFFFF" class="insidecontent" cellspacing="0" cellpadding="0" >
					<tr><td height="10"></td></tr>
					<tr>
						<td width="100%" class="insidecontent" align="left"><a class="insidecontent" href="/bookbattery/index.jsp"><img src="/bookbattery/images/back25.png" alt="order car battery online" border="0" width="25" height="25"/></a></td>
					</tr>
					<tr><td height="2"><div id='scrollpopup'  style="display:block;"><table width="100%" border="0" align="center" valign="top"><tr><td align='right' class='loading_image'></td></tr><tr><td align='center' style='font-family:Verdana;font-size:12px;color:#000000; text-decoration:none;padding:1px 1px;'>Please wait page is loading....</td></tr></table></div></td></tr>
					<tr>
						<td>
							<table border="0" align="left"  width="100%" bgcolor="#FFFFFF" cellpadding="0" cellspacing="0" >
								<tr>
									<td>
										<table border="0" width="100%">
											<tr>
												<td valign="top" width="70%" align="right">
													<table width="100%" border="0">
														<tr>
															<td width="100%" class="insidecontent" align="center" style='font-family:arial, helvetica, sans-serif;font-size:19px;color:#FF8C00;font-weight:bold;text-decoration:none;padding:1px 1px;'>Order Your Battery Now</td><td width="1%"></td>
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
															<div id="img1" align='center' style='display:none;'><img src="/bookbattery/images/loader.gif" width="20" height="20"/></div>
															<div class="styled-select">
																<select  name='battery_type' id='battery_type' class="insidecontent" style='width:155px;  bgcolor:#FFF;' >
																	<option style="background:#FFF" value='' class='topindex1'>Select&nbsp;Battery&nbsp;Type</option>
																	<option style="background:#FFF" value="Car Batteries" >Car Batteries</option>
																	<option style="background:#FFF" value="Inverter Batteries" >Inverter Batteries</option>
																	<option style="background:#FFF" value="Inverter" >Inverter</option>
																	<option style="background:#FFF" value="Bike Batteries" >Bike Batteries</option>
																	<option style="background:#FFF" value="Bus Batteries" >Bus Batteries</option>
																	<option style="background:#FFF" value="Tractor Batteries" >Tractor Batteries</option>
																	<option style="background:#FFF" value="Truck Batteries" >Truck Batteries</option>
																	<option style="background:#FFF" value="Three Wheeler Batteries" >Three Wheeler Batteries</option>					
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
															<div id="img2" align='center' style="margin-left:0%;margin-top:0.4%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left;position:absolute;padding: 1px;display: none;z-index:2;"><img src="/bookbattery/images/loader.gif" width="20" height="20"/></div>
															<div class="styled-select">
																<select name="vehicle_name" id ="vehicle_name" class="insidecontent" style='width:155px'>
																	<option style="background:#FFF" value="0" class='topindex1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;Make</option>
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
															<div id="img3" align='center' style="margin-left:0%;margin-top:0.4%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left;position:absolute;padding: 1px;display: none;z-index:2;"><img src="/bookbattery/images/loader.gif" width="20" height="20"/></div>
															<div class="styled-select" >
																<select name="vehicle_model" id ="vehicle_model" class="insidecontent" style='width:155px;'>
																	<option style="background:#FFF" value="0" class='topindex1' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;Model</option>
																</select>
																<select name="bat_brand1" id ="bat_brand1" class="insidecontent" style='width:155px;display:none;' >
																	<option style="background:#FFF" value="0" class='topindex1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;Brand</option>
																	</select>
																<select name="laptop_model" id ="laptop_model" class="insidecontent" style='width:155px;display:none;'>
																<option style="background:#FFF" value="0" class='topindex1' >Select&nbsp;Laptop&nbsp;Model</option>
															</select>
															</div>
														</td>
														<td width="25%" align="left">
															<div id="img4" align='center' style="margin-left:0%;margin-top:0.4%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left;position:absolute;padding: 1px;display: none;z-index:2;"><img src="/bookbattery/images/loader.gif" width="20" height="20"/></div>
															<div class="styled-select">
																<select name="bat_brand" id ="bat_brand" class="insidecontent" style='width:155px;' >
																	<option style="background:#FFF" value="0" class='topindex1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;Brand</option>
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
															<div id="img5" align='center' style="margin-left:0%;margin-top:0.4%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left;position:absolute;padding: 1px;display: none;z-index:2;"><img src="/bookbattery/images/loader.gif" width="20" height="20"/></div>
															<div class="styled-select">
																<select  name='state' id='state' class="insidecontent" style='width:155px;  bgcolor:#FFF;' >
																<option style="background:#FFF" value="0" class='topindex1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;State</option>
																</select>
															</div>
															</div>
														</td>
														<td width="25%" align="left" valign="top" >
															<div id="citydiv">
																<div id="img6" align='center' style="margin-left:0%;margin-top:0.4%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left;position:absolute;padding: 1px;display: none;z-index:2;"><img src="/bookbattery/images/loader.gif" width="20" height="20"/></div>
																<div class="styled-select">
																	<select name="city" id ="city" class="insidecontent"  style='width:155px;' >
																		<option style="background:#FFF" value="0" class='topindex1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;Location</option>
																	</select>
																	<span class="hint1"><div align="justify">Select the closest Location where you want the battery to be installed. Call +91 9603467559, if your Location is not there.</div><span class="hint-pointer1">&nbsp;</span></span>
																</div>
															</div>
															<div id="pincode2" align="left">
																<font style='font-family:Verdana;font-size:11px;color:#000000;' >Please Enter Your PinCode:</font>
															</div>
														</td>
														<td width="25%" align="left" valign="top">
															<div id="areadiv">
																<div id="img7" align='center' style="margin-left:0%;margin-top:0.4%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left;position:absolute;padding: 1px;display: none;z-index:2;"><img src="/bookbattery/images/loader.gif" width="20" height="20"/></div>
																<div class="styled-select">
																	<select name="area" id ="area" class="insidecontent"  style='width:155px;' >
																		<option style="background:#FFF" value="0"  class='topindex1'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Select&nbsp;Area</option>
																	</select>
																	<span class="hint1"><div align="justify">Select the closest Area where you want the battery to be installed. Call +91 9603467559, if your Area is not there.</div><span class="hint-pointer1">&nbsp;</span></span>
																</div>
															</div>							
															<div id="pincode3">
																<input class='insidecontent' type='text' name='pincode' id='pincode' placeholder='517001' style='width:149px;height:28px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='40'/>
															</div>
														</td>
														<td width="10%" align="left" valign="top">
														<div id='laptopsbutton' style='display:none;'>
																	<a href="javascript:getLaptopBatteryDetails();"><img src="/bookbattery/images/findbttry.png" alt="Order car battery online/inverter battery online for best discounted price with valid warranty period and install car BookBattery or inverter BookBattery or bike BookBattery with free installation charges." border="0" width="155" height="33" /></a>
																</div>
														<div id='findbatterybutton'><a href="javascript:funToGetbatterydetails();"><img src="/bookbattery/images/findbttry.png" alt="Order car battery online/inverter battery online for best discounted price with valid warranty period and install car BookBattery or inverter BookBattery or bike BookBattery with free installation charges." border="0" width="155" height="35" /></a>
														</div>
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
														<td width="88%" align="right"><a href="/bookbattery/index.jsp" style='font-family:Verdana;font-size:11px;color:#000000; text-decoration:none;padding:1px 1px;' title="Order for new battery">Order&nbsp;New&nbsp;Battery</a></td><td width="12%" align="right"><img src="/bookbattery/images/newBookBattery.png" border="0" valign="bottom" width="20" height="20" alt="Order new battery"/></td>
													</tr>
													<tr><td height="1"></td></tr>
													<tr>
														<td width="88%" align="right"><a href="/bookbattery/batteryhealthcheck.jsp" style='font-family:Verdana;font-size:11px;color:#000000; text-decoration:none;padding:1px 1px;' title="Regular check up of your battery is important.">Order&nbsp;For&nbsp;Free&nbsp;Battery&nbsp;Health&nbsp;Check</a></td><td width="12%" align="right"><img src="/bookbattery/images/image2.png" border="0" valign="bottom" width="20" height="20" alt="Battery health check"/></td>
													</tr>
													<tr><td height="1"></td></tr>
													<tr>
														<td width="88%" align="right"><a href="/bookbattery/batterycheckyourself.jsp" style='font-family:Verdana;font-size:11px;color:#000000; text-decoration:none;padding:1px 1px;' title="Click link to view videos on how to maintain & replace a battery.">How&nbsp;To&nbsp;Check&nbsp;A&nbsp;Car&nbsp;Battery Yourself</a></td><td width="12%" align="right"><img src="/bookbattery/images/video_icon.png" border="0" valign="bottom" width="20" height="20" alt="How to check battery"/></td>
													</tr>
													<tr><td height="1"></td></tr>
													<tr>
														<td width="88%" align="right"><a href="/bookbattery/registerbattery.jsp" style='font-family:Verdana;font-size:11px;color:#000000; text-decoration:none;padding:1px 1px;' title="Register and get SMS on when to replace the battery.">Register&nbsp;Your&nbsp;Battery&nbsp;Details</a></td><td width="12%" align="right"><img src="/bookbattery/images/new_battery.png" border="0" valign="bottom" width="20" height="20" alt="Register battery"/></td>
													</tr>
													<tr><td height="1"></td></tr>
														<tr>
														<td width="88%" align="right"><a href="javascript:aboutBookBattery();" style='font-family:Verdana;font-size:11px; color:#FF8C00; text-decoration:none; padding:1px 1px;' title="Learn about a battery warranty, price etc.">About&nbsp;Batteries</a></td><td width="12%" align="right"><img src="/bookbattery/images/image1.png" border="0" valign="bottom" width="20" height="20" alt="About BookBattery"/></td>
													</tr>
													<tr><td height="1"></td></tr>
													<tr>
														<td width="88%" align="right"><a href="/bookbattery/checkwarranty.jsp" style='font-family:Verdana;font-size:11px; color:#000000; text-decoration:none; padding:1px 1px;'  title="Check your car battery or bike battery or inverter battery warranty">Check&nbsp;Your&nbsp;Warranty&nbsp;Period</a></td><td width="12%" align="right"><img src="/bookbattery/images/warranty.jpg" border="0"  width="20" height="20" alt="Know more about the car battery or inverter battery or bike battery"/></td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
									<table width="100%" border="0" align="center" valign="top">
										<tr>
											<td style='font-family:Verdana;font-size:14px;color:#000000;font-weight:bold;text-decoration:none;padding:1px 1px;'><div align="justify">About Batteries</div></td>
										</tr>
										<tr><td height="4"></td></tr>
										<tr>
											<td style='font-family:Verdana;font-size:11px;color:#FF8C00;font-weight:bold;text-decoration:none;padding:1px 1px;' ><div align="justify">How to detect if there is a battery problem.</div></td>
										</tr>
										<tr><td height="4"></td></tr>
										<tr>
											<td style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' ><div align="justify">The most obvious sign of a car battery or bike battery or inverter battery problem is a dead battery. The car battery itself provides other clues to whether it's on its way out.</div></td>
										</tr>
										<tr><td height="4"></td></tr>
										<tr>
											<td style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' ><div align="justify">1.The first is age. If the car battery or inverter battery or bike battery is older than the warranty period given, start expecting problems and it is better to call BookBattery.com at +91 9603467559 to check regarding your warranty period and the problem.</div></td>
										</tr>
										<tr><td height="4"></td></tr>
										<tr>
											<td style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' ><div align="justify">2. Some cars or bikes will show a car or bike battery indication light when car battery or bike battery is not functioning properly, which means it is time to replace</div></td>
										</tr>
										<tr><td height="4"></td></tr>
										<tr>
											<td style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' ><div align="justify">3. It Makes a Ticking Sound</div></td>
										</tr>
										<tr><td height="4"></td></tr>
										<tr>
											<td style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' ><div align="justify">If you find that your car is making a ticking sound instead of the usual sounds that are heard when the key is turned on, probably the car battery is dead.</div></td>
										</tr>
										<tr><td height="4"></td></tr>
										<tr>
											<td style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' ><div align="justify">4. It Drags When Started</div></td>
										</tr>
										<tr><td height="4"></td></tr>
										<tr>
											<td style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' ><div align="justify">If the car battery is dead, the use of extra power will make the car drag when started. For instance, if the car refuses to start after the headlights are turned on for a while; it is an indication that the car battery is dead and needs to be replaced.</div></td>
										</tr>
										<tr><td height="4"></td></tr>
										<tr>
											<td style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' ><div align="justify">5. It Doesn’t Start At All</div></td>
										</tr>
										<tr><td height="4"></td></tr>
										<tr>
											<td style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' ><div align="justify">The car won’t start at all, in case its battery is dead. Faulty battery connections may also lead to this problem. You can test the working condition of the battery by installing it in a different car. In that case, if the vehicle starts and runs for a while, probably the connections to the car battery got loose.</div></td>
										</tr>
										<tr><td height="4"></td></tr>
										<tr>
											<td style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' ><div align="justify">6. Take a look at your driving habits. Remember, short trips and long periods of inactivity will sap a car battery's life. </div></td>
										</tr>
										<tr><td height="4"></td></tr>
										<tr>
											<td style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' ><div align="justify">7. Take a look at the battery itself. Corrosion or stains mean you have a leak. If your battery is covered in a case or insulating sleeve, remove it every once in a while to see what's going on underneath. Look for buildup around the terminals as well. You can clean the buildup off with baking soda and water -- just remember to use gloves and safety glasses while working. The electrolytic solution is partially sulfuric acid, which is not gentle on the skin.  </div></td>
										</tr>
										<tr><td height="4"></td></tr>
										<tr>
											<td style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' ><div align="justify">8.  Finally, smell the car battery, paying attention to rotten egg odors (sulfur) or the smell of the car battery overheating.  </div></td>
										</tr>
										<tr><td height="4"></td></tr>
										<tr>
											<td style='font-family:Verdana;font-size:11px;color:#FF8C00;font-weight:bold;text-decoration:none;padding:1px 1px;' ><div align="justify">How to Make Sure</div></td>
										</tr>
										<tr><td height="4"></td></tr>
										<tr>
											<td style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' ><div align="justify">Call BookBattery at +91 9603467559 to ensure the car battery or bike battery or inverter battery is infact bad and not the alternator. It will help if you know when you have last installed a new battery and its make and model. BookBattery has a Free Battery Health Check program. Go to <a href = "http://www.BookBattery.com"
									class = "bluelinks1">www.BookBattery.com </a> and click on Free Battery Health Check link.</div></td>
										</tr>
										<tr><td height="4"></td></tr>
										<tr>
											<td style='font-family:Verdana;font-size:11px;color:#FF8C00;font-weight:bold;text-decoration:none;padding:1px 1px;' ><div align="justify">Tips</div></td>
										</tr>
										<tr><td height="4"></td></tr>
										<tr>
											<td style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' ><div align="justify">Ensure the car battery is checked as part of your normal service and if the mechanic says that the car battery is bad and it needs to be replaced, call +91 9603467559 immediately before you agree to replace the car battery by the mechanic. Most probably, you will end up buying a second hand car battery or pay a premium price. BookBattery will help you install a genuine branded car battery at a best price.</div></td>
										</tr>
										<tr><td height="4"></td></tr>
										<tr>
											<td style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' ><div align="justify">Buying a Right car battery for the car is important as it not only powers the engine but provides charge to run Music System, Lights and Ignition of the car. Below are some of important points to be aware before buying car battery.</div></td>
										</tr>
										<tr><td height="4"></td></tr>
										<tr>
											<td style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' ><div align="justify">Every Car comes with a recommended car battery group size as specified by Manufacturer in Owner Manual. Its always advisable to buy only that car battery which comes in the recommended group size as it fits in the car.</div></td>
										</tr>
										<tr><td height="4"></td></tr>
										<tr>
											<td style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' ><div align="justify">Car Battery comes with warranty from 12 months to 30 months. It is always advisable to buy car BookBattery with warranty in range of 24 months or higher for Petrol Cars and 18 months or more for Diesel Cars.</div></td>
										</tr>
										<tr><td height="4"></td></tr>
										<tr>
											<td style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' ><div align="justify">Ensure that Warranty is Valid PAN India and has replacement guarantee with replacement period terms mentioned.</div></td>
										</tr>
										<tr><td height="4"></td></tr>
										<tr>
											<td style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' ><div align="justify">Old Batteries do get exchanged and has a value  as Exchange Price with New Battery. Please ask for that.</div></td>
										</tr>
										<tr><td height="4"></td></tr>
										<tr>
											<td style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' ><div align="justify">It is recommended to always buy car battery having manufacturing date lesser than 6 months.</div></td>
										</tr>
										<tr><td height="4"></td></tr>
										<tr>
											<td style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' ><div align="justify">Some of the Popular and Renowned Battery Brands for Cars in India – Amaron and Exide.</div></td>
										</tr>
										<tr><td height="4"></td></tr>
										<tr>
											<td style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' ><div align="justify">Though Most of the Renowned Brands now come with Maintenance Free Battery, even if you are buying some other brand - ensure that its Maintenance Free Sealed Batteries without a cap. However we recommend buying branded genuine car BookBattery or bike BookBattery or inverter BookBattery from <a href = "http://www.BookBattery.com"
										class = "bluelinks1">www.BookBattery.com </a>as battery is like a heart in the body without which no other part of the automobile will work, if it can’t be started.</div></td>
										</tr>
										<tr><td height="4"></td></tr>
										<tr>
											<td style='font-family:Verdana;font-size:11px;color:#FF8C00;font-weight:bold;text-decoration:none;padding:1px 1px;' ><div align="justify">Free Warranty</div></td>
										</tr>
										<tr><td height="4"></td></tr>
										<tr>
											<td style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' ><div align="justify">In the free warranty period, the car battery or bike battery or inverter battery will be replaced or repaired fully free of cost.</div></td>
										</tr>

										<tr><td height="4"></td></tr>
										<tr>
											<td style='font-family:Verdana;font-size:11px;color:#FF8C00;font-weight:bold;text-decoration:none;padding:1px 1px;' ><div align="justify">Pro - rata Warranty</div></td>
										</tr>
										<tr><td height="4"></td></tr>
										<tr>
											<td style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' ><div align="justify">If your car battery or bike battery or inverter battery fails in the pro-rata warranty period then depending on the age of the battery, you will get discount on the newly replaced car battery or bike battery or inverter battery. The pro-rata warranty is counted from the date of purchase to the date failed.</div></td>
										</tr>
										<tr><td height="4"></td></tr>
										<tr>
											<td style='font-family:Verdana;font-size:11px;color:#000000;text-decoration:none;padding:1px 1px;' ><div align="justify">For example if a car battery or bike battery or inverter battery has 12 months free warranty and additional 12 months pro-rata warranty, and it fails in the 13th month, then 50% of its duty cycle is over on that car battery or bike battery or inverter battery. So 50% value of the car battery or bike battery or inverter battery will be deducted from its current price at that point of time. And you will get 50% discount on the MRP of the newly replaced car battery or bike battery or inverter battery.</div></td>
										</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr><td height="10"></td></tr>
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

$(window).bind("pageshow", function() 
{
    document.knowaboutBookBattery.reset();	
});

if (/MSIE (\d+\.\d+);/.test(navigator.userAgent))
{
	var ieversion=new Number(RegExp.$1)
	if (ieversion > 9)
	{
		(function() {
		var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
		po.src = '/bookbattery/js/jqFancyTransitions.1.8.min.js';
		var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
		})();
	} 
}

browsername=navigator.appName;
if ((browsername.indexOf("Netscape")!=-1) || (browsername.indexOf("Opera")!=-1))
{
		(function() {
		var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
		po.src = '/bookbattery/js/jqFancyTransitions.1.8.min.js';
		var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
		})();
}
 
</script>
</html>
