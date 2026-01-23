<%-- 
Document   		 : service_type_list.jsp
Created on 		 : Mar 28 2017, 10:05:12 AM
Author     		 : BookBattery
Copyright Notice : BookBattery Confidential.
Document         : This jsp is used as Show the list of Vehical Models in BookBattery Services.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.*,com.ngit.javabean.loglevel.LogLevel,javax.servlet.ServletContext,java.util.Properties,java.io.FileInputStream, com.ngit.javabean.consumers.products.GetCookie,com.ngit.javabean.qrymgr.QueryManager"%>
<%response.setHeader("Pragma","no-cache"); response.setHeader("Cache-Control","no-store"); response.setHeader("Expires","0"); response.setDateHeader("Expires",-1); %>
<%
ServletContext context = getServletContext();
Properties propsMOPConfig = new Properties();
FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
propsMOPConfig.load(fin1); 
fin1.close();


String publicUrl = (propsMOPConfig.getProperty("publicUrl")!=null)?propsMOPConfig.getProperty("publicUrl"):"";
String baseURL = (propsMOPConfig.getProperty("baseURL")!=null)?propsMOPConfig.getProperty("baseURL"):"";
String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?propsMOPConfig.getProperty("baseurldirectory"):"";
String serverURL = (propsMOPConfig.getProperty("serverURL")!=null)?propsMOPConfig.getProperty("serverURL"):"";

QueryManager qm;
qm = QueryManager.getInstance(propsMOPConfig) ;
//################### Getting Location From Cookies 
GetCookie State_City = new GetCookie(qm);
String State_City_Resp=  State_City.getCookieStateCity(request,response,session);
LogLevel.DEBUG(5, new Throwable(), "State_City_Resp :" + State_City_Resp);

String[] State_City_Arr=State_City_Resp.split("~");
String Product_State_Cookie=State_City_Arr[0];
String Product_City_Cookie=State_City_Arr[1];
//################### Getting Location From Cookies 
String ServiceType = (String) request.getAttribute("ServiceType");
LogLevel.DEBUG(5, new Throwable(), "ServiceType in jsp:" + ServiceType);

String Product_State_Cookie_URL= Product_State_Cookie.replaceAll(" ", "-");
String Product_City_Cookie_URL= Product_City_Cookie.replaceAll(" ", "-");

String titles = "Battery Health Checkup @ 599 Rs Only - Electrical Health Checkup ";
String keywords = "inverter Battery, Car Battery, Health Checkup 599 Rs Only, Electrical Health Checkup";
String description = "Battery Health Checkup @ 599 Rs Only - Electrical Health Checkup";
%> 


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title><%=titles%></title>
<meta name="og_title" property="og:title" content="<%=titles%>"/>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="og_site_name" property="og:site_name" content="BookBattery.com"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="<%=publicUrl%><%=baseurldirectory%>images/favicon.png" type="image/x-icon">
<meta name='Description' content="<%=description%>"/>
<meta name='Keywords' content='<%=keywords%>'/>

<!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_css.jsp" />
<!---################################## CSS Include Ends  ################################------>
</head>
<body>
<!---################################## Header Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_header.jsp" />
<!---################################## Header Include Ends  ################################------>

  <!-- breadcrumbs -->
  <div class="breadcrumbs">
    <div class="container">
      <div class="row">
        <ul>
          <li class="home"> <a href="<%=baseURL%><%=baseurldirectory%>" title="Go to Home Page">Home</a><span>&raquo;</span></li>
          
		  <%if(ServiceType.replaceAll("%20", " ").equals("Health Check"))
		  {%>
	  <li class="category13"><strong>Battery Service</strong></li>
		<%}else{%>
		  <li class="category13"><strong><%=ServiceType.replaceAll("%20", " ")%></strong></li>
		<%}%>
        </ul>
      </div>
    </div>
  </div>
  <!-- End breadcrumbs --> 

  <!-- main-container -->
  <div class="main-container col2-right-layout" id="Page_Result_ID">
    <div class="main container" style=" margin-top: 0px;">
      <div class="row">
        <section class="col-main col-sm-12 col-xs-12 col-md-12">  

			<div class="static-contain">
				<div class="col-sm-12 col-xs-12 col-md-12">
		<%if(ServiceType.replaceAll("%20", " ").equals("Health Check"))
		  {%>
			<h3 style="color: #f15942;"> Book For Battery Service</h3>
		<%}else{%>
			<h3 style="color: #f15942;">Book For <%=ServiceType.replaceAll("%20", " ")%></h3>
		<%}%>
			</div>
			<div class="col-sm-12 col-xs-12 col-md-12 no-padding" id="Get_Service" style=" padding: 15px 0px;">
					<div class="col-sm-3 col-xs-12 col-md-3">  
						<div class="form-group">
						  <label for="service_type">Service Type * :</label>
						  <select class="form-control yes" id="service_type">
							<option value="0">Select Service Type</option>
							<option value="Health Check">Battery Health Checkup</option>
							<option value="Recharge">Battery Recharge</option>
							<option value="Jump Start">Car / Bike Jump Start</option>
						  </select>
						  <div id='service_type-error'style="display:none;"></div>
						</div>
					</div>
                    <div class="col-sm-3 col-xs-12 col-md-3">  
						<div class="form-group">
						  <label for="product_type">Battery Type * :</label>
						  <select class="form-control yes" id="product_type">
							<option value="0">Select Battery Type</option>
						  </select>
						  <div id='product_type-error'style="display:none;"></div>
						</div>
					</div>
					<div id="car_bat_div">
					<div class="col-sm-3 col-xs-12 col-md-3">  
						<div class="form-group">
						  <label for="product_make">Vehicle Make * :</label>
						  <select class="form-control yes" id="product_make">
							<option value="0">Select Make</option>
						  </select>
						  <div id='product_make-error'style="display:none;"></div>
						</div>
					</div>
					<div class="col-sm-3 col-xs-12 col-md-3">  
						<div class="form-group">
						  <label for="product_model">Vehicle Model * :</label>
						  <select class="form-control yes" id="product_model">
							<option value="0">Select Model</option>
						  </select>
						  <div id='product_model-error'style="display:none;"></div>
						</div>
					</div>
					</div>
					<div id="inv_bat_div" style="display:none">
					<div class="col-sm-3 col-xs-12 col-md-3">  
						<div class="form-group">
						  <label for="product_cap">Capacity * :</label>
						  <select class="form-control yes" id="product_cap">
							<option value="0">Select Capacity</option>
						  </select>
						  <div id='product_cap-error'style="display:none;"></div>
						</div>
					</div>
					<div class="col-sm-3 col-xs-12 col-md-3">  
						<div class="form-group">
						  <label for="product_qty">Quantity * :</label>
						  <select class="form-control yes" id="product_qty">
							<option value="0">Select Quantity</option>
							<option value="1" selected>1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="8">8</option>
							<option value="9">9</option>
							<option value="10">10</option>
						  </select>
						  <div id='product_qty-error'style="display:none;"></div>
						</div>
					</div>
					</div>
					<div class="col-sm-3 col-xs-12 col-md-3"> 
						<div class="form-group">
						 <label class="width-100-custom" >&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; </label>
							<button onclick="Get_Service()" class="button btn-cart" type="button" id="find_service_btn">
								<span><i class="icon-basket"></i> Book A Service Now</span>
							</button>	
						</div>					
					</div>	
				</div>

                <hr width="100" style="  width: 100%; border-top: 2px solid #bfbfbf;">
				<div>  
					<p style=" padding-top: 20px; font-size: 16px; color: #676b6b;font-weight: 600;">Car Battery is like your Heart. Even though your heart has warranty, you still go for routine checkup. Similarly Car Battery needs some routine maintenance check up despite its warranty.  This will increase your battery life and avoid last minute car starting issues.</p>
				</div>
				<div style="padding-top: 20px;" class="Product_Description">  
					<div class="col-sm-6 col-xs-12 col-md-6">  
						<div>  
							<div>  
								<h4 style=" color: #4CAF50;"><b>Why to do Battery Health Checkup ?</b></h4>
							</div>
							<div class=" block-tags">
								<div>
									<li>Will improve your Battery Efficiency.</li>
									<li>Increase your Battery life.</li>
									<li>Avoid your car starting troubles.</li>
									<li>Know your Car Battery Present Condition.</li>
									<li>Bad Condition battery will affect your fuel economy</li>
									<li>The charging system or starter can be affected by a weak battery </li>
								</div>  
							</div>
						</div>
						<div class="hidden-sm hidden-md hidden-lg ">
							<img src="<%=baseURL%><%=baseurldirectory%>dev/includes/images-design/What-to-do-health-checkup.png" style="width: 131%;margin-left: -15%;">
						</div>
						<div style="padding-top: 30px;">  
							<div>  
								<h4 style=" color: #4CAF50;"><b>What You will Get on a Battery Health Checkup?</b></h4>
							</div>
							<div class=" block-tags">
								<div>
									<p style="  font-size: 16px;color: #009688;"><b>Professional and Experienced Technician will come to your place</b></p>
									<li>Check the Voltage and AMPS at different stages, Check the water level/acid levels of the battery(top up if required).</li>
									<li>If Battery Recharge is required, additional Rs 300 will be charged for getting the Battery Recharged.</li>
									<li>The battery will be taken for recharge at our service center and will be provided back within 24 - 48 hrs.</li>
									<li>If the battery needs to be replaced and replaced by us immediately the above charges will not be applicable</li>
								</div>  
							</div>
						</div>
					</div>
					<div class="col-sm-6 col-xs-12 col-md-6 hidden-xs">  
						<img src="<%=baseURL%><%=baseurldirectory%>dev/includes/images-design/What-to-do-health-checkup.png" style="width: 100%;">
					</div>
				</div>
				

			</div>
        </section>
      </div>
		<div style=" background-image: url('<%=baseURL%><%=baseurldirectory%>dev/includes/images-design/Book-Your-Car-Battery-Service.png'); background-size: contain; background-repeat: no-repeat;background-position: center bottom; height: 260px;" class="hidden-xs hidden-sm  ">
		</div>
		<div style=" background-image: url('<%=baseURL%><%=baseurldirectory%>dev/includes/images-design/Book-Your-Car-Battery-Service.png'); background-size: contain; background-repeat: no-repeat;background-position: center bottom; height: 190px;"  class="hidden-md hidden-xs hidden-lg">
		</div>
		<div style=" background-image: url('<%=baseURL%><%=baseurldirectory%>dev/includes/images-design/Book-Your-Car-Battery-Service-Mobile.png'); background-size: contain; background-repeat: no-repeat;background-position: center bottom; height: 300px;"  class="hidden-md hidden-sm hidden-lg">
		</div>
    </div>
  </div>
  <!--End main-container --> 


  </br>

 <!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "/dev/includes/jsp/includes_service_js.jsp" />
<!---################################## CSS Include Ends  ################################------>

<!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_footer.jsp" />
<!---################################## CSS Include Ends  ################################------>

<script>
$(document).ready(function() {
	if (screen.width <= 767)
	{
	 $('html, body').animate({
			scrollTop: $("#Page_Result_ID").offset().top
		},500);
	}
});
</script>

 
<input type="hidden" name="publicUrl" id='publicUrl' value="<%=publicUrl%>">
<input type="hidden" name="baseURL" id='baseURL' value="<%=baseURL%>">
<input type="hidden" name="baseurldirectory" id='baseurldirectory' value="<%=baseurldirectory%>">
<input type="hidden" name="product_page_type" id='product_page_type' class="product_page_type" value="battery-health-checkup-at-599">
<input type="hidden" name="product_vehicle_make" id='product_vehicle_make' value="0">
<input type="hidden" name="product_make_page" id='product_make_page' value="batteryservice">
<input type="hidden" name="product_vehicle_model" id='product_vehicle_model' value="0">
<input type="hidden" name="product_state_page" id='product_state_page' value="<%=Product_State_Cookie%>">
<input type="hidden" name="product_city_page" id='product_city_page' value="<%=Product_City_Cookie%>">
<input type="hidden" name="service_type_page" id='service_type_page' value="<%=ServiceType%>">

<script type="text/javascript" src="<%=publicUrl%><%=baseurldirectory%>dev/js/product_details_service_function.js?v=5"></script>

<script>
$(document).ready(function()
{
	//alert($('#service_type_page').val());
	
	var servicetype=$('#service_type_page').val();
	
	servicetype=servicetype.replace(/%20/g," ");

	//alert(servicetype);
	$('#service_type').val(servicetype);
	
	if(servicetype!= 0)
	{
		//alert(22);
		
	    if($("#service_type").val()=="Jump Start")
	    {
	        $("#product_type").html("<option value='0'>Select Battery Type</option><option value='Car Batteries'>Car Batteries</option><option value='Bike Batteries'>Bike Batteries</option>");
	        $("#product_type").focus();
	    }
		else
		{
	        $("#product_type").html("<option value='0'>Select Battery Type</option><option value='Car Batteries'>Car Batteries</option><option value='Inverter Batteries'>Inverter Batteries</option><option value='Bike Batteries'>Bike Batteries</option>");
	        $("#product_type").focus();
		}
	}
	else
	{
	
	}
	
});

	$(document).ready(function()
	{
		if (screen.width <= 767)
		{
			$("body").append( "<div class='btn Buy_Now_Mobile hidden-sm hidden-md hidden-lg' id='Buy_Now_Mobile'><div style='font-size: 3em;margin-top: -27px;color: #e02d29;cursor: pointer;'><span class='icon-chevron-sign-down arrow_down_button' onClick='javascript:Scroll_to_next()'></span></div></div>" );
		}
		

		$(window).scroll(function() {
			var total_height=$(document).height();
			var current_height=$(document).scrollTop();
			var window_height=screen.height-100;
			if(total_height-(current_height+window_height+window_height)<=window_height)
			{
				$( "#Buy_Now_Mobile" ).fadeOut("slow");
			}
			else
			{
				$( "#Buy_Now_Mobile" ).fadeIn("slow");
			}
		});
	})
	
	function Scroll_to_next()
	{
		var total_height=$(document).height();
		var current_height=$(document).scrollTop();
		var window_height=screen.height-150;
		var goHeight=current_height+window_height;
		$('html, body').animate({scrollTop:goHeight}, 'slow');
	}
	
	
	
	function Get_Service()
	{
		//alert(22);
        
        if (selectValidation("","product_type","select") == false)
		return;
	
		if($("#product_type").val()=="Car Batteries" || $("#product_type").val()=="Bike Batteries" )
		{
			if (selectValidation("","product_make","select") == false)
			return;
			if (selectValidation("","product_model","select") == false)
			return;		
		}
		else
		{
			if (selectValidation("","product_cap","select") == false)
			return;
		}
		//alert($("#product_make").val());
		//alert($("#product_model").val());
		//alert($("#product_cap").val());
		//alert($("#product_cap").val());
		var product_make= $("#product_make").val();
		var product_model= $("#product_model").val();
		
		if(product_make=="Select Make"){product_make="0";}else{product_make=product_make.replace(/ /g, "-");}
		if(product_model=="Select Model"){
		//alert("inside");
		product_model="0";
		}
		else{
			//alert("inlside");
			product_model=product_model.replace(/ /g, "+").replace(/\//g, '_');

		}
		var ServicePackage_URL=$("#publicUrl").val()+$("#baseurldirectory").val()+"batteryservice/"+$("#product_state_page").val().replace(/ /g, "-")+"/"+$("#product_city_page").val().replace(/ /g, "-")+"/"+product_make+"/"+product_model+"/"+$("#product_type").val().replace(/ /g, "+").replace(/\//g, '_')+"/"+$("#product_cap").val().replace(/ /g, "+").replace(/\//g, '_')+"/"+$("#service_type").val().replace(/ /g, "+").replace(/\//g, '_')+"/store/"+$("#product_qty").val().replace(/ /g, "+").replace(/\//g, '_');
		
		window.location.href= ServicePackage_URL;
		$("#Loading_bar").show();
	}
	function goToGet_Service()
	{
		$('html, body').animate({scrollTop: $("#Get_Service").offset().top-100
		},1000);
		everyThingNotOk("product_make","Please Select <b>Your Vehicle Make</b>.");
	}

	
	
 </script>
<style>
.Product_Description .block-tags li {
    display: table;
    font-size: 15px;
    font-weight: 300;
    color: #7b7979;
    display: flex;
    line-height: 30px;
}
.Product_Description .block-tags li:before {
    content: "\f054";
    font-family: FontAwesome;
    color: #f57a20;
    font-size: 13px;
    padding: 0px 5px 0 0;
    line-height: 30px;
}
</style>
<script type="text/javascript">
/* 	$(document).ready(function()
	{
		$('#nav-home').removeClass('active');
		$('.battery_health_checkup').addClass('active'); 
	}); */
		
$("#service_type").change(function() {
	
	$("#product_type").html("<option>Loading ... </option>");
	
    //alert($("#service_type").val());
    
    if($("#service_type").val()=="Jump Start")
    {
        $("#product_type").html("<option value='0'>Select Battery Type</option><option value='Car Batteries'>Car Batteries</option><option value='Bike Batteries'>Bike Batteries</option>");
        $("#product_type").focus();
    }
	else
	{
        $("#product_type").html("<option value='0'>Select Battery Type</option><option value='Car Batteries'>Car Batteries</option><option value='Inverter Batteries'>Inverter Batteries</option><option value='Bike Batteries'>Bike Batteries</option>");
        $("#product_type").focus();
	}
});

$("#product_type").change(function() {
	
	$("#product_make").html("<option>Loading ... </option>");
	
	if($("#product_type").val()=="Car Batteries" ||$("#product_type").val()=="Car Battery" || $("#product_type").val()=="Bike Battery" || $("#product_type").val()=="Bike Batteries")
	{
		$("#car_bat_div").show();
		$("#inv_bat_div").hide();
		
		$.ajax({					 
		type: "GET",
		url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getvehiclename",
		data: {batterytype: $("#product_type").val()},
		success: function(data)
		{	
			$("#product_make").html(data);
			$("#product_model").html("<option>Select Vehicle Model</option>");
			$("#product_services_type").html("<option>Select Service Type</option>");
			$("#product_services_package").html("<option>Select Service Package</option>");
			
			if ($("#product_vehicle_make").val()!='0')
			{
				$("#product_make").val($("#product_vehicle_make").val()).change();
			}
            
		}
	});
	}
	else
	{
		$("#car_bat_div").hide();
		$("#inv_bat_div").show();
			$.ajax
			({
				type: "GET",
				url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getbatterycapacity",
				data: {batterytype: $("#product_type").val()},
				success: function(data)
				{	
					$("#product_cap").html(data).focus();
					$("#product_cap").append("<option value='Dont Know'>Dont Know</option>");
				}
			});
	}
    
        if ($("#product_type").val()=="Bike Batteries" || $("#product_type").val()=="Bike Battery" || $("#product_type").val()=="Car Battery"|| $("#product_type").val()=="Car Batteries")
        {
            if($("#service_type").val()=="Jump Start")
            {
                if ($("#product_type").val()=="Bike Batteries" || $("#product_type").val()=="Bike Battery")
                {
                    $("#service_loc").html("<option value='0'>Select Service Place</option><option value='within_5km'>With in 5 KM</option>"); 
                }
                else
                {
                    $("#service_loc").html("<option value='0'>Select Service Place</option><option value='within_5km'>With in 5 KM</option><option value='within_10km'>With in 10 KM</option>"); 
                }
            }
            else
            {
                 if ($("#product_type").val()=="Bike Batteries" || $("#product_type").val()=="Bike Battery")
                 {
                      $("#service_loc").html("<option value='0'>Select Service Place</option><option value='store'>At Store</option><option value='within_5km'>With in 5 KM</option>"); 
                 }
                 else
                 {
                       $("#service_loc").html("<option value='0'>Select Service Place</option><option value='store'>At Store</option><option value='within_5km'>With in 5 KM</option><option value='within_10km'>With in 10 KM</option>"); 
                 }
            }
        }
        else
        {
            $("#service_loc").html("<option value='0'>Select Service Place</option><option value='store'>At Store</option><option value='within_5km'>With in 5 KM</option><option value='within_10km'>With in 10 KM</option>");
        }
	
});

	
	$("#product_make").change(function() {
		
		if (selectValidation("","product_make","select") == false)
		{
			return;
		}
		else
		{
			var product_make=$("#product_make").val();
			$("#product_model").html("<option>Loading ... </option>");
			
			$.ajax({					 
				type: "GET",
				url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getvehicle_model",
				data: {vehiclename: product_make, batterytype:$("#product_type").val()},
				success: function(data)
				{	
					$("#product_model").html(data);
					$("#product_services_type").html("<option>Select Service Type</option>");
					$("#product_services_package").html("<option>Select Service Package</option>");
					
					if ($("#product_vehicle_model").val()!='0')
					{
						$("#product_model").val($("#product_vehicle_model").val()).change();
					}
					else if ($("#page_type").val()=='Home_Page' || $("#page_type").val()=='404' )
					{
						$("#product_model").focus();
					}
					else
					{
						//Do Nothing 
					}
				}
			});
			
		}
	});	

	
</script>
</body>
</html>