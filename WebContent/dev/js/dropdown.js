//### Name 		 : dropdown..js
//### Author	 : Sai Krishna Daddala
//### Description: This contains Battery finder valadation, Mobile number capture Popup code and other common Funcionality Js and Jquerry code.

// Variables Declaration Global #################################################
var product_type;
var product_make;
var product_fuel;
var product_capacity;
var product_model;
var product_inverter_capacity;
var product_inverter_battery;
var product_type_page;
var product_brand;
var product_state;
var product_city;
var UserMobileNumber_Cookie_Tmp;
var publicUrl =$("#publicUrl").val();

$(document).ready(function()
{
	
	//alert(25);
	// Code to get Product Type #################################################
	$( "#product_type" ).change(function() {
		 product_type=$("#product_type").val();
		
		if (product_type=="0" || product_type=="default" || product_type==""){
			$("#product_make").html("<option>Select Make</option>");
			$("#product_capacity").html("<option>Select Capacity</option>");
			$("#product_model").html("<option>Select Model</option>");
			$("#product_fuel").html("<option>Select Fuel</option>");
			$("#product_brand").html("<option>Select Brand</option>");
			$("#product_inverter_battery").html("<option>Select Inverter Battery</option>");
			$("#product_inverter_capacity").html("<option>Select Inverter Capacity</option>");
			$('#product_inverter_capacity_div').popover('destroy');
			$('#product_inverter_battery_div').popover('destroy');
			
		}

		else if (product_type=="Inverter Batteries"){
			
			$("#find_battery_btn").html("<span><i class='icon-basket'></i> Find Inverter Battery</span>");
			
			$("#product_make_div").hide();
			$("#product_model_div").hide();
			$("#product_fuel_div").hide();
			$("#product_capacity_div").show();
			$("#product_inverter_capacity_div").hide();
			$("#product_inverter_battery_div").hide();
			$("#Configure_Your_Inverter_home").hide();
			$("#product_inverter_battery").html("<option>Select Inverter Battery</option>");
			$("#product_inverter_capacity").html("<option>Select Inverter Capacity</option>");
			$("#product_capacity").html("<option>Loading ..</option>");
			$("#product_make").html("<option>Select Make</option>");
			$("#product_model").html("<option>Select Model</option>");
			$("#product_brand").html("<option>Select Brand</option>");
			
			$('#product_inverter_capacity_div').popover('destroy');
			$('#product_inverter_battery_div').popover('destroy');
			
			
			$.ajax
			({
				type: "GET",
				url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getbatterycapacity",
				data: {batterytype: product_type},
				success: function(data)
				{	
					$("#product_capacity").html(data).focus();
				}
			});
		}
		else if (product_type=="Inverter"){
			
			//alert(22)
			
			window.location.href = publicUrl+'/bookbattery/configure-your-inverter.jsp';
			$("#Loading_bar").show();
			$("#find_battery_btn").html("<span><i class='icon-basket'></i> Find Inverter</span>");
			$( "#product_inverter_battery_div" ).find( "label" ).html("Inverter Battery (Optional):");
			 
			$("#product_make_div").hide();
			$("#product_model_div").hide();
			$("#product_fuel_div").hide();
			$("#product_capacity_div").hide();
			$("#product_inverter_capacity_div").show();
			$("#product_inverter_battery_div").show();
			$("#Configure_Your_Inverter_home").show();
			$("#product_capacity").html("<option>Select Capacity</option>");
			$("#product_make").html("<option>Select Make</option>");
			$("#product_model").html("<option>Select Model</option>");
			$("#product_inverter_battery").html("<option>Select Inverter Battery</option>");
			$("#product_inverter_capacity").html("<option>Select Inverter Capacity</option>");
			
			$('#product_inverter_capacity_div').popover('destroy');
			$('#product_inverter_battery_div').popover('destroy');
			
			
			$("#product_brand").html("<option>Loading ....</option>");			
			$.ajax
			({
				type: "GET",
				url: "/bookbattery/servlet/InvertersDetails?hidWhatToDo=getinverterbrand&keyword=Common",
				//data: {batterytype: product_type},
				success: function(data)
				{	
					$("#product_brand").html(data).focus();
				}
			});
		}
		else if (product_type=="Inverter and Battery Combo"){
			
			$("#find_battery_btn").html("<span><i class='icon-basket'></i> Find Inverter and Battery Combo</span>");
			
			$("#product_make_div").hide();
			$("#product_model_div").hide();
			$("#product_fuel_div").hide();
			$("#product_capacity_div").hide();
			$("#product_inverter_capacity_div").show();
			$("#product_inverter_battery_div").show();
			$("#Configure_Your_Inverter_home").show();
			$("#product_capacity").html("<option>Select Capacity</option>");
			$("#product_make").html("<option>Select Make</option>");
			$("#product_model").html("<option>Select Model</option>");
			$("#product_inverter_battery").html("<option>Select Inverter Battery</option>");
			$("#product_inverter_capacity").html("<option>Select Inverter Capacity</option>");
			
			$('#product_inverter_capacity_div').popover('destroy');
			$('#product_inverter_battery_div').popover('destroy');
			
			
			$("#product_brand").html("<option>Loading ....</option>");			
			$.ajax
			({
				type: "GET",
				url: "/bookbattery/servlet/InvertersDetails?hidWhatToDo=getinverterbrand&keyword=Common",
				//data: {batterytype: product_type},
				success: function(data)
				{	
					$("#product_brand").html(data).focus();
				}
			});
		}
		else{
			$("#find_battery_btn").html("<span><i class='icon-basket'></i> Find Battery</span>");
			
			$("#product_make_div").show();
			$("#product_model_div").show();
			$("#product_fuel_div").show();
			$("#product_capacity_div").hide();
			$("#product_inverter_capacity_div").hide();
			$("#product_inverter_battery_div").hide();
			$("#Configure_Your_Inverter_home").hide();
			$("#product_make").html("<option>Loading ... </option>");
			$("#product_model").html("<option>Select Model</option>");
			$("#product_brand").html("<option>Select Brand</option>");
			$("#product_inverter_battery").html("<option>Select Inverter Battery</option>");
			$("#product_inverter_capacity").html("<option>Select Inverter Capacity</option>");
			$('#product_inverter_capacity_div').popover('destroy');
			$('#product_inverter_battery_div').popover('destroy');
			
			
			 $.ajax({					 
				type: "GET",
				 url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getvehiclename",
				data: {batterytype: product_type},
				success: function(data)
				{	
					$("#product_make").html(data).focus();
				}
			});
		}
		

	});	
	
	
	// Code to get Product Model #################################################
	 
	$( "#product_make" ).change(function() {
		product_make=$("#product_make").val();
		
		if (product_make=="0" || product_make=="default" || product_make==""){
			$("#product_capacity").html("<option>Select Capacity</option>");
			$("#product_fuel").html("<option>Select Fuel Type</option>");
			$("#product_model").html("<option>Select Model</option>");
			$("#product_brand").html("<option>Select Brand</option>");
		}
		else{

			$("#product_fuel").html("<option>Loading ... </option>");
			$("#product_model").html("<option>Select Model</option>");
			$("#product_brand").html("<option>Select Brand</option>");
			
			 $.ajax({					 
				type: "GET",
				url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getvehicle_model",
				data: {vehiclename: product_make, batterytype:product_type},
				success: function(data)
				{	
					$("#product_model").html(data).focus();
				}
			});
		}

	});
		
	// Code to get Product Fuel Type #################################################
	
	$( "#product_fuel" ).change(function() {
		product_fuel=$("#product_fuel").val();
		
		if (product_make=="0" || product_make=="default" || product_make==""){
			$("#product_capacity").html("<option>Select Capacity</option>");
			$("#product_model").html("<option>Select Model</option>");
			$("#product_brand").html("<option>Select Brand</option>");
		}
		else{

			$("#product_model").html("<option>Loading ... </option>");
			$("#product_brand").html("<option>Select Brand</option>");
			
			 $.ajax({					 
				type: "GET",
				url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getvehicle_model",
				data: {vehiclename: product_make, batterytype:product_type,fueltype:product_fuel},
				success: function(data)
				{	
					$("#product_model").html(data).focus();
				}
			});
		}

	});
	
	// Code to get Product Brand #################################################
	 
	$( "#product_model" ).change(function() {
		product_model=$("#product_model").val();
		var brandkeyword=$("#brandkeyword").val();
		
		
		if (product_model=="0" || product_model=="default" || product_model==""){
			$("#product_brand").html("<option>Select Brand</option>");
		}
		else{

			$("#product_brand").html("<option>Loading ... </option>");
			
			 $.ajax({					 
				type: "GET",
				url: "/bookbattery/servlet/Functions?hidWhatToDo=Fx_Get_Product_Brand",
				data: {product_type:$("#product_type").val() },
				success: function(data)
				{	
					if(data.split("value").length<=4)
					{
						data= data.replace("<option style='' value='All'>All</option>","");
					}	
					else
					{}
				
					$("#product_brand").html(data).focus();
				}
			});
		}

	});	
		
	// Code to get Inverter Capacity Paced on Brand #################################################
	 
	$( "#product_brand" ).change(function() {
		product_brand=$("#product_brand").val();
		var brandkeyword=$("#brandkeyword").val();
		
		if (product_type=="Inverter" || product_type=="Inverter and Battery Combo")
		{
			if (product_brand=="0" || product_brand=="default" || product_brand==""){
				$("#product_inverter_capacity").html("<option>Select Inverter Capacity</option>");
				$("#product_inverter_battery").html("<option>Select Inverter Battery</option>");
				$('#product_inverter_capacity_div').popover('destroy');
				$('#product_inverter_battery_div').popover('destroy');
			}
			else{

				$("#product_inverter_capacity").html("<option>Loading ... </option>");
				
				 $.ajax({					 
					type: "GET",
					 url: "/bookbattery/servlet/InvertersDetails?hidWhatToDo=getinvertercapacity",
					data: {inverterbrand:product_brand},
					success: function(data)
					{	
						$("#product_inverter_capacity").html(data).focus();
					}
				});
			}
		}
		else
		{
			$("#product_state").focus();
		}

	});	

	
	// Code to get Product Brand for Inverters Baced on Capacity #################################################
	$( "#product_capacity" ).change(function() {
		product_capacity=$("#product_capacity").val();
		var brandkeyword=$("#brandkeyword").val();
		
		if (product_capacity=="0" || product_capacity=="default" || product_capacity==""){
			$("#product_brand").html("<option>Select Brand</option>");
		}
		else{

			$("#product_brand").html("<option>Loading ... </option>");
			
			 $.ajax({					 
				type: "GET",
				url: "/bookbattery/servlet/Functions?hidWhatToDo=Fx_Get_Product_Brand",
				data: {product_type:$("#product_type").val() },
				success: function(data)
				{	
					if(data.split("value").length<=4)
					{
						data= data.replace("<option style='' value='All'>All</option>","");
					}	
					else
					{}
					$("#product_brand").html(data).focus();
				}
			});
		}

	});		
	
	// Code to get City #################################################
	 
	$( "#product_state" ).change(function() {
		product_state=$("#product_state").val();
		
		if (product_state=="0" || product_state=="default" || product_state==""){
			$("#product_city").html("<option>Select City</option>");
		}
		else{

			$("#product_city").html("<option>Loading ... </option>");
			
			 $.ajax({					 
				type: "GET",
				url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getcity",
				data: {state:product_state},
				success: function(data)
				{	
					$("#product_city").html(data).focus();
				}
			});
		}

	});
	
});

$('#know_more').click(function(){
   // alert(this.className)
	if(this.className=="more")
	{
		$('.box').show();
		$('#know_more').removeClass("more");
		$('#know_more').addClass("less");
		$('#know_more').html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Click here to Know Less.");	
	}
	else
	{
		$('.box').hide();
		$('#know_more').removeClass("less");
		$('#know_more').addClass("more");
		$('#know_more').html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Click here to Know More.");	
	}
});
$('#know_more_mob').click(function(){
	//alert(22);
	if(this.className=="more")
	{
		$('.box').show();
		$('#know_more_mob').removeClass("more");
		$('#know_more_mob').addClass("less");
		$('#know_more_mob').html("</br> Click here to Know Less.");	
	}
	else
	{
		$('.box').hide();
		$('#know_more_mob').removeClass("less");
		$('#know_more_mob').addClass("more");
		$('#know_more_mob').html("</br> Click here to Know More.");	
	}
});

function AskLocationDetails()
{
    //alert(55);
	$('#ask_location').modal({ backdrop: 'static', keyboard: false });
}

function UpdateLocationDetails()
{
	// Cookie expire time to 1 year
	
	//alert(22);
	var ctime=365*24*60*60*1000;
	product_state =$("#product_state").val();
	product_city =$("#product_city").val();
	
	//alert("location"+product_state);
	//alert("location"+product_city);
	
	$("#product_state_page").val(product_state);
	$("#product_city_page").val(product_city);
	
	if (selectValidation("","product_state","select") == false)
	{
		return;
	}
	
	if (selectValidation("","product_city","select") == false)
	{
		return;
	}
	
	
	if (setCookie("product_state_cookie", product_state.replace(/ /g, "-"),ctime) == true)
	{
		console.log(product_state);
	}
	if (setCookie("product_city_cookie", product_city.replace(/ /g, "-"),ctime) == true)
	{
		console.log(product_city);
	}
	
	
	//alert($("#product_state_page").val());
	
	if ($("#product_page_name").val()=="404")
	{
		window.history.back();
	}
	else if ($("#product_state_page").val()=="0" || $("#product_state_page").val()=="" || $("#product_state_page").val()=="null" || $("#product_state_page").val()=="undefined"|| $("#product_state_page").val()==undefined )
	{
		location.reload();
	}
	else
	{
		//alert("inside else");
		UpdateProduct_Filter()
	}

}

function SkipLocationDetails()
{
	// Cookie expire time to 1 year
	var ctime=365*24*60*60*1000;
    //setCookie("product_state_cookie","Karnataka",ctime)
    //setCookie("product_city_cookie","Bangalore",ctime)
	location.reload();
}


// Code to load selected State and City values on load #################################################
function selectStateandCity(){
	
	//alert("inside");
	
	//alert($("#product_state_page").val());
	var ctime=365*24*60*60*1000;
	if (!$("#product_state_page").val()=="0" || !$("#product_state_page").val()=="" || !$("#product_state_page").val()=="null" || !$("#product_state_page").val()=="undefined"|| !$("#product_state_page").val()==undefined )
	{
		$("#product_state").val($("#product_state_page").val().replace(/-/g," "));
		$(".product_state").val($("#product_state_page").val().replace(/-/g," "));
		
		 product_state=$("#product_state").val();
		 //alert("ajax"+product_state);
		$.ajax({					 
			type: "GET",
			url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getcity",
			data: {state:$("#product_state").val()},
			success: function(data)
			{	
				setCookie("product_state_cookie", product_state.replace(/ /g, "-"),ctime);
				$("#product_state_top_nav").html(product_state.replace("-"," "));
				$("#product_city").html(data);
				$(".product_city").html(data);

				
				if (!$("#product_city_page").val()=="0" || !$("#product_city_page").val()=="" || !$("#product_city_page").val()=="null" || !$("#product_city_page").val()=="undefined"|| !$("#product_city_page").val()==undefined)
				{
					$("#product_city").val($("#product_city_page").val().replace(/-/g," "));
					$(".product_city").val($("#product_city_page").val().replace(/-/g," "));
					product_city=$("#product_city").val();
					setCookie("product_city_cookie", product_city.replace(/ /g, "-"),ctime);
					$("#product_city_top_nav").html(product_city.replace("-"," "));
					$("#SkipLocationDetails_btn").hide();
					//07-11-2016 this code is to enable buy now button in Single product pages-- By Sai Krishna Daddala
					$("[type='button'][onclick='javascript:OrderProductOnline()']").removeClass("btn").prop('disabled', false).html(" <span> Buy Now</span>");
				}
				else
				{
					selectStateandCityFromCookie();
				}
			}
		});
	}
	else
	{
		selectStateandCityFromCookie();
	}
}

function selectStateandCityFromPopular(state,city){
	
	var ctime=365*24*60*60*1000;
	var statepick=state.trim();
	var citypick=city.trim();
	
	//alert(statepick);
	//alert(citypick);
	
	$("#product_state").val(statepick.replace(/-/g," "));
	$(".product_state").val(statepick.replace(/-/g," "));

		if (selectValidation("","product_state","select") == false)
		{
			//alert("product state false");
			AskLocationDetails();
			everyThingOk("product_state");
			$("#product_state").val("default");
			return;
		}
		else
		{
					$("#product_state_top_nav").html(statepick.replace(/-/g," "));
					
					//alert("else"+product_state_cookie_tmp);
					setCookie("product_state_cookie", statepick.replace(/ /g, "-"),ctime);
					
					$.ajax({					 
						type: "GET",
					url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getcity",
					data: {state:$("#product_state").val()},
					success: function(data)
					{	
							$("#product_city").html(data);
							$(".product_city").html(data);
							$("#product_city").val(citypick.replace(/-/g," "));
							$(".product_city").val(citypick.replace(/-/g," "));
							
							if (selectValidation("","product_city","select") == false)
							{
								//alert("insdie null");
								AskLocationDetails();
								everyThingOk("product_city");
								$("#product_city").val("default");
								return;
							}
							else
							{
								//alert("else city"+citypick);
								$("#product_city_top_nav").html(citypick.replace(/-/g," "));
								setCookie("product_city_cookie", citypick.replace(/ /g, "-"),ctime);
								UpdateLocationDetails();	
							}
						}
					});
		}
	
	
}

function selectStateandCityFromCookie(){
	var ctime=365*24*60*60*1000;
	var product_state_cookie_tmp=getCookie('product_state_cookie');
	//alert("onload"+product_state_cookie_tmp);
	var product_city_cookie_tmp=getCookie('product_city_cookie');
	//alert("onload"+product_city_cookie_tmp);
	$("#product_state").val(product_state_cookie_tmp.replace(/-/g," "));
	$(".product_state").val(product_state_cookie_tmp.replace(/-/g," "));
	
	if (selectValidation("","product_state","select") == false)
	{
		//alert("product state false");
		AskLocationDetails();
		everyThingOk("product_state");
		$("#product_state").val("default");
		return;
	}
	else
	{
		$("#product_state_top_nav").html(product_state_cookie_tmp.replace(/-/g," "));
		
		//alert("else"+product_state_cookie_tmp);
		setCookie("product_state_cookie", product_state_cookie_tmp.replace(/ /g, "-"),ctime);
		
		$.ajax({					 
			type: "GET",
			url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getcity",
			data: {state:$("#product_state").val()},
			success: function(data)
			{	
				$("#product_city").html(data);
				$(".product_city").html(data);
				$("#product_city").val(product_city_cookie_tmp.replace(/-/g," "));
				$(".product_city").val(product_city_cookie_tmp.replace(/-/g," "));
				
				if (selectValidation("","product_city","select") == false)
				{
					AskLocationDetails();
					everyThingOk("product_city");
					$("#product_city").val("default");
					return;
				}
				else
				{
					//alert("else city"+product_city_cookie_tmp);
					$("#product_city_top_nav").html(product_city_cookie_tmp.replace(/-/g," "));
					setCookie("product_city_cookie", product_city_cookie_tmp.replace(/ /g, "-"),ctime);
					$("#SkipLocationDetails_btn").hide();
					//07-11-2016 this code is to enable buy now button in Single product pages-- By Sai Krishna Daddala
					$("[type='button'][onclick='javascript:OrderProductOnline()']").removeClass("btn").prop('disabled', false).html(" <span> Buy Now</span>");
				}
			}
		});
	}
}

// Code to get States on load #################################################
$( document ).ready(function() {
	$("#product_state").html("<option>Loading ... </option>");
	 $.ajax({					 
		type: "GET",
		url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getstate",
		success: function(data)
		{	
			$("#product_state").html(data);
			$(".product_state").html(data);
			selectStateandCity();
		}
	});
});
	
$(document).ready(function()
{	
	$("#product_make").html("<option value='0'>Select Make</option>");
	$("#product_capacity").html("<option value='0'>Select Capacity</option>");
	$("#product_model").html("<option value='0'>Select Model</option>");
	$("#product_brand").html("<option value='0'>Select Brand</option>");
	$("#product_type").val("0");
});

	// Code to load selected values on load #################################################
$(document).ready(function()
{	
	
	//alert($("#product_type_page").val());
	
	if (!$("#product_type_page").val()=="0" || !$("#product_type_page").val()=="" || !$("#product_type_page").val()=="null" || !$("#product_type_page").val()=="undefined"|| !$("#product_type_page").val()==undefined )
	{
		$("#product_type").val($("#product_type_page").val());
		
		 product_type=$("#product_type").val();
		 //alert()
		
		if ($("#product_type").val()=="Inverter Batteries")
		{
			$.ajax
			({
				type: "GET",
				url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getbatterycapacity",
				data: {batterytype: $("#product_type").val()},
				success: function(data)
				{	
					
					$("#product_make_div").hide();
					$("#product_fuel_div").hide();
					$("#product_model_div").hide();
					$("#product_capacity_div").show();
					$("#product_capacity").html(data);
					
					if (!$("#product_capacity_page").val()=="0" || !$("#product_capacity_page").val()=="" || !$("#product_capacity_page").val()=="null" || !$("#product_capacity_page").val()=="undefined"|| !$("#product_capacity_page").val()==undefined) 
					{
						$("#product_capacity").val($("#product_capacity_page").val());
						product_capacity=$("#product_capacity").val();
						setCookie("product_capacity_cookie", product_capacity);
						var brandkeyword=$("#brandkeyword").val();
						
						 $.ajax
						 ({					 
							type: "GET",
							url: "/bookbattery/servlet/Functions?hidWhatToDo=Fx_Get_Product_Brand",
							data: {product_type:$("#product_type").val() },
							success: function(data)
							{
								
								$("#product_brand").html(data);
								if (!$("#product_brand_page").val()=="0" || !$("#product_brand_page").val()=="" || !$("#product_brand_page").val()=="null" || !$("#product_brand_page").val()=="undefined"|| !$("#product_brand_page").val()==undefined)
								{
									$("#product_brand").val($("#product_brand_page").val());
									product_brand=$("#product_brand").val();
									setCookie("product_brand_cookie", product_brand);
								}
							}
						});
					}
				}
			});
		}		
		else if ($("#product_type").val()=="Inverter" || $("#product_type").val()=="Inverter and Battery Combo")
		{
			$.ajax
			({
				type: "GET",
				url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getbatterycapacity",
				data: {batterytype: $("#product_type").val()},
				success: function(data)
				{	
					
					$("#product_make_div").hide();
					$("#product_fuel_div").hide();
					$("#product_model_div").hide();
					$("#product_capacity_div").show();
					$("#product_capacity").html(data);
			
					if (!$("#product_capacity_page").val()=="0" || !$("#product_capacity_page").val()=="" || !$("#product_capacity_page").val()=="null" || !$("#product_capacity_page").val()=="undefined"|| !$("#product_capacity_page").val()==undefined) 
					{
						$("#product_capacity").val($("#product_capacity_page").val());
						product_capacity=$("#product_capacity").val();
						setCookie("product_capacity_cookie", product_capacity);
						var brandkeyword=$("#brandkeyword").val();
						
						 $.ajax
						 ({					 
							type: "GET",
							url: "/bookbattery/servlet/Functions?hidWhatToDo=Fx_Get_Product_Brand",
							data: {product_type:$("#product_type").val() },
							//alert(data);
							success: function(data)
							{
							
								$("#product_brand").html(data);
								if (!$("#product_brand_page").val()=="0" || !$("#product_brand_page").val()=="" || !$("#product_brand_page").val()=="null" || !$("#product_brand_page").val()=="undefined"|| !$("#product_brand_page").val()==undefined)
								{
									$("#product_brand").val($("#product_brand_page").val());
									product_brand=$("#product_brand").val();
									setCookie("product_brand_cookie", product_brand);
								}
							}
						});
					}
				}
			});
		}
		else
		{
			$.ajax
			({					 
				type: "GET",
				 url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getvehiclename",
				data: {batterytype: $("#product_type").val()},
				success: function(data)
				{	
					//alert(data);
					
					$("#product_make_div").show();
					$("#product_fuel_div").show();
					$("#product_model_div").show();
					$("#product_capacity_div").hide();
					$("#product_make").html(data);
					
					if (!$("#product_make_page").val()=="0" || !$("#product_make_page").val()=="" || !$("#product_make_page").val()=="null" || !$("#product_make_page").val()=="undefined"|| !$("#product_make_page").val()==undefined) 
					{
						
						//alert($("#product_make_page").val());
						
							if($("#product_make_page").val()=="ALL"||$("#product_make_page").val()=="All")
							{
								//alert("inside");
								$("#product_make").val(0);	
								//alert($("#product_make").val());
								product_make=$("#product_make").val();
								setCookie("product_make_cookie", product_make);
							}
						    else
							{
								$("#product_make").val($("#product_make_page").val());											
								product_make=$("#product_make").val();
								setCookie("product_make_cookie", product_make);
							}						
									
									$.ajax
									({					 
										type: "GET",
										url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getvehicle_model",
										data: {vehiclename: $("#product_make").val(),fueltype: "Diesel",batterytype:$("#product_type").val()},
										success: function(data)
											{
											
											//alert(data);
											$("#product_model").html(data);		
											
											if (!$("#product_model_page").val()=="0" || !$("#product_model_page").val()=="" || !$("#product_model_page").val()=="null" || !$("#product_model_page").val()=="undefined"|| !$("#product_model_page").val()==undefined) 
											{						
												$("#product_model").val($("#product_model_page").val());
												
												product_model=$("#product_model").val();
												
												setCookie("product_model_cookie", product_model);

												var brandkeyword=$("#brandkeyword").val();
									
												 $.ajax
												({					 
													type: "GET",
													url: "/bookbattery/servlet/Functions?hidWhatToDo=Fx_Get_Product_Brand",
													data: {product_type:$("#product_type").val() },
													success: function(data)
														{	
															$("#product_brand").html(data);
														
															if (!$("#product_brand_page").val()=="0" || !$("#product_brand_page").val()=="" || !$("#product_brand_page").val()=="null" || !$("#product_brand_page").val()=="undefined"|| !$("#product_brand_page").val()==undefined) 
															{
																$("#product_brand").val($("#product_brand_page").val());
																 product_brand=$("#product_brand").val();
																 setCookie("product_brand_cookie", product_brand);
															}

														}
														
												});
											}

										 }
											
									});
						
						
						
						
						
						
					}
				}
			});
		}
	}
});


function AskforMobileNumber(){


	product_type =$("#product_type").val();
	product_make =$("#product_make").val();
	product_fuel =$("#product_fuel").val();
	product_capacity =$("#product_capacity").val();
	product_model =$("#product_model").val();
	product_inverter_capacity =$("#product_inverter_capacity").val();
	product_inverter_battery =$("#product_inverter_battery").val();
	product_brand =$("#product_brand").val();
	product_state =$("#product_state").val();
	product_city =$("#product_city").val();

var publicUrl =$("#publicUrl").val();

// This is declared to send input_type in valadation
var input_type;
	input_type="select";
	if (selectValidation("","product_type",input_type) == false)
	{
		return;
	}
	else
	{
		setCookie("product_type_cookie", product_type)
	}
	
	if (product_type=="Inverter Batteries"){
		
		if (selectValidation("","product_capacity",input_type) == false)
		{
			return;
		}
		else
		{
			setCookie("product_capacity_cookie", product_capacity)
		}
	}
	
	else if (product_type=="Inverter"){
		
		
		if (selectValidation("","product_brand",input_type) == false)
		{
			return;
		}
		else
		{
			setCookie("product_brand_cookie", product_brand)
		}
		
		if (selectValidation("","product_inverter_capacity",input_type) == false)
		{
			return;
		}
		else
		{
			setCookie("product_inverter_capacity_cookie", product_inverter_capacity)
		}
	}	
	else if (product_type=="Inverter and Battery Combo"){
		
		if (selectValidation("","product_brand",input_type) == false)
		{
			return;
		}
		else
		{
			setCookie("product_brand_cookie", product_brand)
		}
		
		if (selectValidation("","product_inverter_capacity",input_type) == false)
		{
			return;
		}
		else
		{
			setCookie("product_inverter_capacity_cookie", product_inverter_capacity)
		}
		if (selectValidation("","product_inverter_battery",input_type) == false)
		{
			return;
		}
		else
		{
			setCookie("product_inverter_battery_cookie", product_inverter_battery)
		}
	}
	else{
		
		//alert(22);
		
		if (selectValidation("","product_make",input_type) == false)
		{
			return;
		}
		else
		{
			setCookie("product_make_cookie", product_make)
		}
		
		if (selectValidation("","product_fuel",input_type) == false)
		{
			return;
		}
		else
		{
			setCookie("product_fuel_cookie", product_model)
		}
		
		if (selectValidation("","product_model",input_type) == false)
		{
			return;
		}
		else
		{
			setCookie("product_model_cookie", product_model)
		}
		
	}	

	if (selectValidation("","product_brand",input_type) == false)
	{
		return;
	}
	else
	{
		setCookie("product_brand_cookie", product_brand)
	}
	
	alert(35);
	
	RequestProductDetails();
}

function RequestProductDetails(RequestType){
	
	alert("inside");
	
	var UserMobileNumber="0";	
	var product_capacity_TEMP;
	if (product_type=="Inverter" || product_type=="Inverter"){
		product_capacity_TEMP=product_inverter_capacity;
		
		product_make="";
		product_model="";
	}
	else if (product_type=="Inverter-and-Battery-Combo" || product_type=="Inverter and Battery Combo"){
		product_capacity_TEMP=product_inverter_capacity;
		
		product_make="";
		product_model="";
	}
	else if (product_type=="Inverter Batteries" || product_type=="Inverter Batteries"){
		product_capacity_TEMP=product_capacity;
		product_make="";
		product_model="";
	}
	else{
		product_capacity_TEMP="";
	}
	
	$("#Loading_bar").show();
	$.ajax
	({					 
		type: "GET",
		url: "/bookbattery/servlet/Functions?hidWhatToDo=Insert_Visitor_Details",
		data: {product_type:product_type,product_make:product_make,product_capacity:product_capacity_TEMP,product_brand:product_brand,product_model:product_model,product_state:product_state,product_city:product_city,UserMobileNumber:UserMobileNumber},
		success: function(data)
		{	
			if(data.indexOf("Successfully Added")>=0)
			{
				 RequestProductDetails_Step_2();
			}	
			else
			{
				alert("Opps !. Some thing went wrong. Please reload the Page.");
			}
		}	
	});
	// RequestProductDetails_Step_2();
}

function RequestProductDetails_Step_2()
{
	product_type =$("#product_type").val();
	product_make =$("#product_make").val();
	product_fuel =$("#product_fuel").val();
	product_capacity =$("#product_capacity").val();
	product_model =$("#product_model").val();
	product_inverter_capacity =$("#product_inverter_capacity").val();
	product_inverter_battery =$("#product_inverter_battery").val();
	product_brand =$("#product_brand").val();
	product_state =$("#product_state").val();
	product_city =$("#product_city").val();
	
	
alert(55);

	var publicUrl =$("#publicUrl").val();
	
	if (product_type=="Inverter")
	{
		if (selectValidation("","product_inverter_battery","select") == false)
		{
			everyThingOk("product_inverter_battery");
		}
		else
		{
			setCookie("product_inverter_battery_cookie", product_inverter_battery)
			product_type="Inverter and Battery Combo";
		}
	}
	
	var product_type_URL;
	var product_make_URL;
	var product_fuel_URL;
	var product_inverter_capacity_URL;
	var product_inverter_battery_URL;
	var product_brand_URL;
	var product_capacity_URL;
	var product_state_URL;
	var product_city_URL;
	var product_model_URL;
	
	product_type_URL= product_type.replace(/ /g, "-");
	product_make_URL= product_make.replace(/ /g, "-");
	product_fuel_URL= product_fuel.replace(/ /g, "-");
	
	product_capacity_URL= product_capacity.replace(/ /g, "+");
	product_inverter_capacity_URL= product_inverter_capacity.replace(/ /g, "+");
	product_inverter_battery_URL= product_inverter_battery.replace(/ /g, "+");

	product_brand_URL= product_brand.replace(/ /g, "-");
	product_state_URL= product_state.replace(/ /g, "-");
	product_city_URL= product_city.replace(/ /g, "-");
	
	product_model_URL= product_model.replace(/ /g, "+");
	
	if (product_type_URL=="Inverter-Batteries" || product_type=="Inverter Batteries"){
		window.location.href = publicUrl+'/bookbattery/'+product_type_URL+'/'+product_brand_URL+'/'+product_state_URL+'/'+product_city_URL+'/Capacity='+product_capacity_URL+'/';
	}
	else if (product_type_URL=="Inverter" || product_type=="Inverter"){
		window.location.href = publicUrl+'/bookbattery/'+product_type_URL+'/'+product_brand_URL+'/'+product_state_URL+'/'+product_city_URL+'/InverterCapacity='+product_inverter_capacity_URL+'/';
	}
	else if (product_type_URL=="Inverter-and-Battery-Combo" || product_type=="Inverter and Battery Combo"){
		window.location.href = publicUrl+'/bookbattery/'+product_type_URL+'/'+product_brand_URL+'/'+product_state_URL+'/'+product_city_URL+'/Inverter-Capacity='+product_inverter_capacity_URL+'/Battery-Capacity='+product_inverter_battery_URL+'/';
	}
	else{
		//alert(23);
		
			var Filter_Battery_Warranty_Array = [];
			$.each($("input[name='Filter_Battery_Warranty']:checked"), function(){            
			Filter_Battery_Warranty_Array.push($(this).val());
			});
			var Filter_Battery_Warranty=Filter_Battery_Warranty_Array.join(",");
			//alert(Filter_Battery_Warranty);
			
				
			if(Filter_Battery_Warranty=="" || Filter_Battery_Warranty=="undefined")
			{
			Filter_Battery_Warranty_URL="Warranty/";
			}
			else{
			Filter_Battery_Warranty_URL="Warranty="+Filter_Battery_Warranty+"/";
			}
			
			Filter_Battery_Warranty_URL= Filter_Battery_Warranty_URL.replace(/ /g, "_");
			//alert(Filter_Battery_Warranty_URL);
			Filter_Battery_Warranty_URL = Filter_Battery_Warranty_URL.replace(/\+/g, 'plus');
			//alert(Filter_Battery_Warranty_URL);
			
	
		window.location.href = publicUrl+'/bookbattery/'+product_type_URL+'/'+product_make_URL+'/'+product_model_URL+'/'+product_fuel_URL+'/'+product_state_URL+'/'+product_city_URL+'/'+product_brand_URL+'/'+Filter_Battery_Warranty_URL+'/' ;
	}
}

function AskforMobileNumber_Pop_Up(){
	
	var MobileNumberPopUp_html=" <a id='AskforMobileNumber_Pop_Up_btn' class='btn hide' data-toggle='modal' data-backdrop='static' data-keyboard='false' data-target='.ask_user_mobile_number'></a>\
		<div class='modal fade ask_user_mobile_number' tabindex='-1' role='dialog' aria-labelledby='mySmallModalLabel'>\
	  <div class='modal-dialog modal-sm' role='document'>\
		<div class='modal-content'> \
			<div class='modal-header'> \
				<button type='button' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'> x</span></button> \
				<h4 class='modal-title' id='mySmallModalLabel'>Mobile Number - BookBattery</h4> \
			</div> \
			<div class='modal-body'>\
				<div class='form-group'>\
					<label for='home-page-user-mobile-number'>Please Enter Your Mobile Number :</label>\
					<input type='tel' class='form-control yes' id='home-page-user-mobile-number' placeholder='9913XXXXXX' maxlength='10'>\
					<div id='home-page-user-mobile-number-error'style='display:none;'></div>\
					<div style='text-align: center;'>\
						<small id='order-form-usermobile-number-help' class='form-text text-muted'> Need help in placing order. We are here to Help you,<br /><b>Call +91 96034 67559</b> or <a href='javascript:Open_Chat()'><span class='label label-default chat-with-us'>Chat with Us</span></a></small>\
					</div>\
					</br>\
					<div style='text-align: center;'>\
						<button type='button' class='button button-close' data-dismiss='modal'>Close</button>\
						<button type='button' class='button button-continue' onclick='RequestProductDetails()'>Continue</button>\
					</div>\
					<div><br />\
						<h4>Why to Shop with Us:</h4>\
						<p>\
						<table>\
							<tr>\
								<td>\
									<span class='icon-star'></span>\
								</td>\
								<td>\
									Don't Buy a Re-furbished or Re-labelled Battery Available at Cheaper Price in the Market.\
								</td>\
							</tr>\
							<tr>\
								<td style='  padding-right: 10px;'>\
									<span class='icon-star'></span>\
								</td>\
								<td>\
									Order with us & Buy a Genuine Battery at Reasonable Cost to save money in the Long Run.\
								</td>\
							</tr>\
						</table>\
						</p>\
					</div>\
				</div>\
			</div>\
		</div>\
	  </div>\
	</div>\ ";
	$("#AskforMobileNumber_Pop_Up").html(MobileNumberPopUp_html);
	$("#AskforMobileNumber_Pop_Up_btn").click();
	setTimeout(function(){
		$('#home-page-user-mobile-number').focus();
	}, 1000);
}

$(document).ready(function()
{
	$(function () {
	 $('[data-toggle="popover"]').popover()
	})
})


function get_inverter_capacity_popover()
{
	if($("#product_type").val()=="Inverter and Battery Combo" || $("#product_type").val()=="Inverter")
	{
		var inverterbrand =$("#product_brand").val();
		var pop_over_msg_inverter;
		
		$('#product_inverter_capacity_div').popover('destroy');
		$('#product_inverter_battery_div').popover('destroy');		
		
		if(inverterbrand=="Amaron")
		{
			pop_over_msg_inverter ="<table width='100%' ><tr><td align='left' style='font-size: 12px;'><b>Choose Your Inverter</b></td><td align='right' ><a href='javascript:hidepopover(); ' ><b>Close</b></a></td></tr></table><div class='table-responsive' style=' margin-bottom: 0px;'><table width='100%' class='popover_custom_table table table-condensed table-bordered table-striped'> <tbody> <tr><th colspan='2'>Inverter Capacity</th><th>Computer</th><th>Fans</th><th>Tube Lights</th><th>TV(32&#8243;)</th></tr> <tr><td rowspan='2'><a href=\"javascript:Select_inverter_capacity_popover('400 VA');\" >400&nbsp;VA</a></td><td><b>Option&nbsp;1</b></td><td>0</td><td>2</td><td>3</td><td>0</td></tr> <tr><td><b>Option&nbsp;2</b></td><td>1</td><td>1</td><td>1</td><td>1</td></tr> <tr><td rowspan='2'><a href=\"javascript:Select_inverter_capacity_popover('650 VA');\" >650&nbsp;VA</a><br /><a href=\"javascript:Select_inverter_capacity_popover('675 VA');\" >675&nbsp;VA</a></td><td><b>Option&nbsp;1</b></td><td>0</td><td>2</td><td>4</td><td>1</td></tr> <tr><td><b>Option&nbsp;2</b></td><td>1</td><td>2</td><td>3</td><td>0</td></tr>  <tr><td rowspan='2'><a href=\"javascript:Select_inverter_capacity_popover('825 VA');\" >825&nbsp;VA</a><br /><a href=\"javascript:Select_inverter_capacity_popover('880 VA');\" >880&nbsp;VA</a></td><td><b>Option&nbsp;1</b></td><td>1</td><td>2</td><td>2</td><td>1</td></tr> <tr><td><b>Option&nbsp;2</b></td><td>0</td><td>4</td><td>4</td><td>1</td></tr>  <tr><td rowspan='2'><a href=\"javascript:Select_inverter_capacity_popover('1400 VA');\" >1400&nbsp;VA</a> </td><td><b>Option&nbsp;1</b></td><td>1</td><td>5</td><td>6</td><td>1</td></tr> <tr><td><b>Option&nbsp;2</b></td><td>1</td><td>6</td><td>5</td><td>1</td></tr> <tr><td colspan='2'>Wattage:(Approx..)</td><td>250W</td><td>80W</td><td>40W</td><td>180W</td></tr> </tbody> </table> </div>";
		}
		if(inverterbrand=="Exide")
		{
			pop_over_msg_inverter ="<table width='100%' ><tr><td align='left' style='font-size: 12px;'><b>Choose Your Inverter</b></td><td align='right' ><a href='javascript:hidepopover(); ' ><b>Close</b></a></td></tr></table><div class='table-responsive' style=' margin-bottom: 0px;'>  <table width='100%' class='popover_custom_table table table-condensed table-bordered table-striped'> <tbody> <tr><th colspan='2'>Inverter Capacity</th><th>Computer</th><th>Fans</th><th>Tube Lights</th><th>TV(32&#8243;)</th></tr> <tr><td rowspan='2'><a href=\"javascript:Select_inverter_capacity_popover('650 VA');\" >650&nbsp;VA</a></td> <td><b>Option&nbsp;1</b></td><td>0</td><td>2</td><td>4</td><td>1</td></tr> <tr><td><b>Option&nbsp;2</b></td><td>1</td><td>2</td><td>3</td><td>0</td></tr> <tr><td rowspan='2'><a href=\"javascript:Select_inverter_capacity_popover('850 VA');\" >850&nbsp;VA</a></td> <td><b>Option&nbsp;1</b></td><td>1</td><td>2</td><td>2</td><td>1</td></tr> <tr><td><b>Option&nbsp;2</b></td><td>0</td><td>4</td><td>4</td><td>1</td></tr> <tr><td rowspan='2'><a href=\"javascript:Select_inverter_capacity_popover('1450 VA');\" >1450&nbsp;VA</a> </td> <td><b>Option&nbsp;1</b></td><td>1</td><td>5</td><td>6</td><td>1</td></tr> <tr><td><b>Option&nbsp;2</b></td><td>1</td><td>6</td><td>5</td><td>1</td></tr> <tr><td colspan='2'>Wattage:(Approx..)</td><td>250W</td><td>80W</td><td>40W</td><td>180W</td></tr> </tbody> </table> </div> ";
		}
		if(inverterbrand=="Luminous")
		{
			pop_over_msg_inverter ="<table width='100%' ><tr><td align='left' style='font-size: 12px;'><b>Choose Your Inverter</b></td><td align='right' ><a href='javascript:hidepopover(); ' ><b>Close</b></a></td></tr></table><div class='table-responsive' style=' margin-bottom: 0px;'> <table width='100%' class='popover_custom_table table table-condensed table-bordered table-striped'><tbody><tr><th colspan='2'>Inverter Capacity</th><th>Computer</th><th>Fans</th><th>Tube Lights</th><th>TV(32&#8243;)</th></tr><tr><td rowspan='2'><a href=\"javascript:Select_inverter_capacity_popover('650 VA');\" >650&nbsp;VA</a> <a href=\"javascript:Select_inverter_capacity_popover('700 VA');\" >700&nbsp;VA</a><br /> <a href=\"javascript:Select_inverter_capacity_popover('850 VA');\" >850&nbsp;VA</a> <a href=\"javascript:Select_inverter_capacity_popover('900 VA');\" >900&nbsp;VA</a></td><td><b>Option&nbsp;1</b></td><td>1</td><td>2</td><td>2</td><td>1</td></tr><tr><td><b>Option&nbsp;2</b></td><td>0</td><td>4</td><td>4</td><td>1</td></tr><tr><td rowspan='2'><a href=\"javascript:Select_inverter_capacity_popover('1250 VA');\" >1250&nbsp;VA</a> <a href=\"javascript:Select_inverter_capacity_popover('1500 VA');\" >1500&nbsp;VA</a><br /> <a href=\"javascript:Select_inverter_capacity_popover('1650 VA');\" >1650&nbsp;VA</a> <a href=\"javascript:Select_inverter_capacity_popover('1700 VA');\" >1700&nbsp;VA</a></td><td><b>Option&nbsp;1</b></td><td>1</td><td>5</td><td>6</td><td>1</td></tr><tr><td><b>Option&nbsp;2</b></td><td>0</td><td>7</td><td>7</td><td>1</td></tr><tr><td rowspan='2'><a href=\"javascript:Select_inverter_capacity_popover('2 KVA');\" >2 KVA</a> <a href=\"javascript:Select_inverter_capacity_popover('3.5 KVA');\" >3.5 KVA</a> </td><td><b>Option&nbsp;1</b></td><td>1</td><td>10</td><td>10</td><td>2</td></tr><tr><td><b>Option&nbsp;2</b></td><td>2</td><td>9</td><td>9</td><td>1</td></tr><tr><td colspan='2'>Wattage:(Approx..)</td><td>250W</td><td>80W</td><td>40W</td><td>180W</td></tr></tbody></table></div>";
		}
		if(inverterbrand=="All")
		{
			pop_over_msg_inverter ="<table width='100%' ><tr><td align='left' style='font-size: 12px;'><b>Choose Your Inverter</b></td><td align='right' ><a href='javascript:hidepopover(); ' ><b>Close</b></a></td></tr></table><div class='table-responsive' style=' margin-bottom: 0px;'> <table width='100%' class='popover_custom_table table table-condensed table-bordered table-striped'><tbody><tr><th colspan='2'>Inverter Capacity</th><th>Computer</th><th>Fans</th><th>Tube Lights</th><th>TV(32&#8243;)</th></tr><tr><td rowspan='2'><a href=\"javascript:Select_inverter_capacity_popover('400 VA');\" >400&nbsp;VA</a></td> <td><b>Option&nbsp;1</b></td><td>0</td><td>2</td><td>3</td><td>0</td></tr> <tr><td><b>Option&nbsp;2</b></td><td>1</td><td>1</td><td>1</td><td>1</td></tr> <tr><td rowspan='2'><a href=\"javascript:Select_inverter_capacity_popover('650 VA');\" >650&nbsp;VA</a> <a href=\"javascript:Select_inverter_capacity_popover('675 VA');\" >675&nbsp;VA</a> <a href=\"javascript:Select_inverter_capacity_popover('700 VA');\" >700&nbsp;VA</a> <a href=\"javascript:Select_inverter_capacity_popover('825 VA');\" >825&nbsp;VA</a><br /> <a href=\"javascript:Select_inverter_capacity_popover('850 VA');\" >850&nbsp;VA</a> <a href=\"javascript:Select_inverter_capacity_popover('880 VA');\" >880&nbsp;VA</a> <a href=\"javascript:Select_inverter_capacity_popover('900 VA');\" >900&nbsp;VA</a></td> <td><b>Option&nbsp;1</b></td><td>1</td><td>2</td><td>2</td><td>1</td></tr> <tr><td><b>Option&nbsp;2</b></td><td>0</td><td>4</td><td>4</td><td>1</td></tr> <tr><td rowspan='2'><a href=\"javascript:Select_inverter_capacity_popover('1250 VA');\" >1250&nbsp;VA</a> <a href=\"javascript:Select_inverter_capacity_popover('1500 VA');\" >1500&nbsp;VA</a><br /> <a href=\"javascript:Select_inverter_capacity_popover('1650 VA');\" >1650&nbsp;VA</a> <a href=\"javascript:Select_inverter_capacity_popover('1700 VA');\" >1700&nbsp;VA</a></td> <td><b>Option&nbsp;1</b></td><td>1</td><td>5</td><td>6</td><td>1</td></tr> <tr><td><b>Option&nbsp;2</b></td><td>0</td><td>7</td><td>7</td><td>1</td></tr> <tr><td rowspan='2'><a href=\"javascript:Select_inverter_capacity_popover('1400 VA');\" >1400&nbsp;VA</a> <a href=\"javascript:Select_inverter_capacity_popover('1450 VA');\" >1450&nbsp;VA</a> </td> <td><b>Option&nbsp;1</b></td><td>1</td><td>5</td><td>6</td><td>1</td></tr> <tr><td><b>Option&nbsp;2</b></td><td>1</td><td>6</td><td>5</td><td>1</td></tr> <tr><td rowspan='2'><a href=\"javascript:Select_inverter_capacity_popover('2 KVA');\" >2 KVA</a> <a href=\"javascript:Select_inverter_capacity_popover('3.5 KVA');\" >3.5 KVA</a> </td> <td><b>Option&nbsp;1</b></td><td>1</td><td>10</td><td>10</td><td>2</td></tr> <tr><td><b>Option&nbsp;2</b></td><td>2</td><td>9</td><td>9</td><td>1</td></tr> <tr><td colspan='2'>Wattage:(Approx..)</td><td>250W</td><td>80W</td><td>40W</td><td>180W</td></tr>  </tbody></table></div>";
		}
		else
		{
			$('#product_inverter_capacity_div').popover('destroy');
		}
		
		$('#product_inverter_capacity_div').popover({
			content:pop_over_msg_inverter,
			placement: "top",
			title: "Inverter Capacity depending on usage ",
			html: true
		})
	
		$("#product_inverter_capacity_div").popover('show');
		
		if (screen.width <= 767)
		{
			
			setTimeout(function(){
			var describedby = $("#product_inverter_capacity_div").attr("aria-describedby");
			 $('html, body').animate({scrollTop: $("#"+describedby+"").offset().top},500);
			}, 500);
			
		}
		else{}
		

		
	}
}


$( "#product_inverter_capacity" ).focus(function() 
{
	get_inverter_capacity_popover()
})

$( "#product_brand" ).change(function() 
{
	//get_inverter_capacity_popover()
	$("#product_inverter_capacity").focus();
})



function get_inverter_battery_popover()
{
	if($("#product_type").val()=="Inverter and Battery Combo" || $("#product_type").val()=="Inverter")
	{
		var invertermake =$("#product_brand").val();
		var inverter_capacity =$("#product_inverter_capacity").val();
		
		var pop_over_msg_inverter_battery;
		var inverter_battery_options;
		
		$('#product_inverter_capacity_div').popover('destroy');
		$('#product_inverter_battery_div').popover('destroy');

		
		if(inverter_capacity=="400 VA")
		{
			inverter_battery_options="<option value='0'>Select&nbsp;Battery</option><option value='100 AH Current Battery'>100 AH Current Battery</option><option value='100 AH Tubular Battery'>100 AH Tubular Battery</option>";

			pop_over_msg_inverter_battery ="<table width='100%' ><tr><td align='left' style='font-size: 12px;'><b>Suggested Inverter Battery for "+inverter_capacity+"</b></td><td align='right' ><a href='javascript:hidepopover(); ' ><b>Close</b></a></td></tr></table><div class='table-responsive' style=' margin-bottom: 0px;'> <table width='100%' cellpadding='2' class='popover_custom_table table table-condensed table-bordered table-striped'> <tr> <td colspan='1'><b>Backup Hours</b></td><td colspan='1'><b>Battery Type</b></td><td colspan='1'><b>Pcs of Battery</b></td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery_popover('100 AH Current Battery');\"> 100 AH Current Battery</a></td> <td>3 Hours</td> <td>x 1</td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery_popover('100 AH Tubular Battery');\"> 100 AH Tubular Battery</a></td><td>3 Hours</td> <td>x 1</td> </tr> </table> </div>";
		}

		else if((inverter_capacity=="650 VA" || inverter_capacity=="675 VA" || inverter_capacity=="700 VA" || inverter_capacity=="825 VA"|| inverter_capacity=="850 VA" || inverter_capacity=="880 VA" || inverter_capacity=="900 VA") && (invertermake=="Amaron" || invertermake=="All" || invertermake=="All" || invertermake=="Luminous"))
		{
			inverter_battery_options="<option value='0'>Select&nbsp;Battery</option><option value='100 AH Current Battery'>100 AH Current Battery</option><option value='100 AH Tubular Battery'>100 AH Tubular Battery</option><option value='135 AH Current Battery'>135 AH Current Battery</option><option value='135 AH Tubular Battery'>135 AH Tubular Battery</option><option value='150 AH Current Battery'>150 AH Current Battery</option><option value='150 AH Tubular Battery'>150 AH Tubular Battery</option><option value='150 AH Tall Tubular Battery'>150 AH Tall Tubular Battery</option>";

			pop_over_msg_inverter_battery ="<table width='100%' ><tr><td align='left' style='font-size: 12px;'><b>Suggested Inverter Battery for "+inverter_capacity+"</b></td><td align='right' ><a href='javascript:hidepopover(); ' ><b>Close</b></a></td></tr></table><div class='table-responsive' style=' margin-bottom: 0px;'> <table width='100%' cellpadding='2' class='popover_custom_table table table-condensed table-bordered table-striped'> <tr> <td colspan='1'><b>Battery Type</b></td> <td colspan='1'><b>Backup Hours</b></td> <td colspan='1'><b>Pcs of Battery</b></td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery_popover('100 AH Current Battery');\"> 100 AH Current Battery</a></td> <td>2 Hours</td> <td>x 1</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery_popover('100 AH Tubular Battery');\"> 100 AH Tubular Battery</a></td> <td>2 Hours</td> <td>x 1</td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery_popover('135 AH Current Battery');\"> 135 AH Current Battery</a></td> <td>3 Hours</td> <td>x 1</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery_popover('135 AH Tubular Battery');\"> 135 AH Tubular Battery</a></td> <td>3 Hours</td> <td>x 1</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery_popover('150 AH Current Battery');\"> 150 AH Current Battery</a></td> <td>4 Hours</td> <td>x 1</td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery_popover('150 AH Tubular Battery');\"> 150 AH Tubular Battery</a></td> <td>4 Hours</td> <td>x 1</td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery_popover('150 AH Tall Tubular Battery');\"> 150 AH Tall Tubular Battery</a></td> <td>4 Hours</td> <td>x 1</td> </tr> </table> </div>";
		}

		else if(inverter_capacity=="650 VA" && invertermake=="Exide")
		{
			inverter_battery_options="<option value='0'>Select&nbsp;Battery</option><option value='100 AH Tubular Battery'>100 AH Tubular Battery</option><option value='150 AH Tubular Battery'>150 AH Tubular Battery</option><option value='150 AH Tall Tubular Battery'>150 AH Tall Tubular Battery</option>";
				
			pop_over_msg_inverter_battery ="<table width='100%' ><tr><td align='left' style='font-size: 12px;'><b>Suggested Inverter Battery for "+inverter_capacity+"</b></td><td align='right' ><a href='javascript:hidepopover(); ' ><b>Close</b></a></td></tr></table><div class='table-responsive' style=' margin-bottom: 0px;'> <table width='100%' cellpadding='2' class='popover_custom_table table table-condensed table-bordered table-striped'> <tr><td colspan='1'><b>Battery Type</b></td> <td colspan='1'><b>Backup Hours</b></td> <td colspan='1'><b>Pcs of Battery</b></td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery_popover('100 AH Current Battery');\"> 100 AH Current Battery</a></td> <td>2 Hours</td> <td>x 1</td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery_popover('100 AH Tubular Battery');\"> 100 AH Tubular Battery</a></td> <td>2 Hours</td> <td>x 1</td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery_popover('150 AH Current Battery');\"> 150 AH Current Battery</a></td> <td>3 Hours</td> <td>x 1</td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery_popover('150 AH Tubular Battery');\"> 150 AH Tubular Battery</a></td> <td>3 Hours</td> <td>x 1</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery_popover('150 AH Tall Tubular Battery');\"> 150 AH Tall Tubular Battery</a></td> <td>3 Hours</td> <td>x 1</td> </tr> </table> </div> ";
		}

		else if(inverter_capacity=="825 VA" || inverter_capacity=="880 VA"  || inverter_capacity=="1250 VA")
		{
			inverter_battery_options="<option value='0'>Select&nbsp;Battery</option><option value='100 AH Current Battery'>100 AH Current Battery</option><option value='100 AH Tubular Battery'>100 AH Tubular Battery</option><option value='135 AH Current Battery'>135 AH Current Battery</option><option value='135 AH Tubular Battery'>135 AH Tubular Battery</option><option value='150 AH Current Battery'>150 AH Current Battery</option><option value='150 AH Tubular Battery'>150 AH Tubular Battery</option><option value='150 AH Tall Tubular Battery'>150 AH Tall Tubular Battery</option><option value='165 AH Tall Tubular Battery'>165 AH Tall Tubular Battery</option>";

			pop_over_msg_inverter_battery ="<table width='100%' ><tr><td align='left' style='font-size: 12px;'><b>Suggested Inverter Battery for "+inverter_capacity+"</b></td><td align='right' ><a href='javascript:hidepopover(); ' ><b>Close</b></a></td></tr></table><div class='table-responsive' style=' margin-bottom: 0px;'> <table width='100%' cellpadding='2' class='popover_custom_table table table-condensed table-bordered table-striped'> <tr> <td colspan='1'><b>Battery Type</b></td> <td colspan='1'><b>Backup Hours</b></td> <td colspan='1'><b>Pcs of Battery</b></td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery_popover('100 AH Current Battery');\"> 100 AH Current Battery</a></td> <td>2 Hours</td> <td>x 1</td> </tr><tr> <td> <a href=\"javascript:Select_inverter_battery_popover('100 AH Tubular Battery');\"> 100 AH Tubular Battery</a></td> <td>2 Hours</td> <td>x 1</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery_popover('135 AH Current Battery');\"> 135 AH Current Battery</a></td> <td>3 Hours</td> <td>x 1</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery_popover('135 AH Tubular Battery');\"> 135 AH Tubular Battery</a></td> <td>3 Hours</td> <td>x 1</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery_popover('150 AH Current Battery');\"> 150 AH Current Battery</a></td> <td>3 Hours</br>30 Minutes</td> <td>x 1</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery_popover('150 AH Tubular Battery');\"> 150 AH Tubular Battery</a></td> <td>3 Hours</br>30 Minutes</td> <td>x 1</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery_popover('150 AH Tall Tubular Battery');\"> 150 AH Tall Tubular Battery</a></td> <td>3 Hours</br>30 Minutes</td> <td>x 1</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery_popover('165 AH Tall Tubular Battery');\"> 165 AH Tall Tubular Batter </a></td> <td>4 Hours</td> <td>x 1</td> </tr> </table> </div> ";
		}

		else if(inverter_capacity=="850 VA")
		{
			inverter_battery_options="<option value='0'>Select&nbsp;Battery</option><option value='100 AH Tubular Battery'>100 AH Tubular Battery</option><option value='150 AH Tubular Battery'>150 AH Tubular Battery</option><option value='150 AH Tall Tubular Battery'>150 AH Tall Tubular Battery</option><option value='200 AH Tall Tubular Battery'>200 AH Tall Tubular Battery</option>";
			
			pop_over_msg_inverter_battery ="<table width='100%' ><tr><td align='left' style='font-size: 12px;'><b>Suggested Inverter Battery for "+inverter_capacity+"</b></td><td align='right' ><a href='javascript:hidepopover(); ' ><b>Close</b></a></td></tr></table><div class='table-responsive' style=' margin-bottom: 0px;'> <table width='100%' cellpadding='2' class='popover_custom_table table table-condensed table-bordered table-striped'> <tr> <td colspan='1'><b>Battery Type</b></td> <td colspan='1'><b>Backup Hours</b></td> <td colspan='1'><b>Pcs of Battery</b></td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery_popover('100 AH Current Battery');\"> 100 AH Current Battery</a></td> <td>1 Hour</br>45 minutes</td> <td>x 1</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery_popover('100 AH Tubular Battery');\"> 100 AH Tubular Battery</a></td> <td>1 Hour</br>45 minutes</td> <td>x 1</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery_popover('150 AH Current Battery');\"> 150 AH Current Battery</a></td> <td>3 Hours</td> <td>x 1</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery_popover('150 AH Tubular Battery');\"> 150 AH Tubular Battery</a></td> <td>3 Hours</td> <td>x 1</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery_popover('150 AH Tall Tubular Battery');\"> 150 AH Tall Tubular Battery</a></td> <td>3 Hours</td> <td>x 1</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery_popover('200 AH Tall Tubular Battery');\"> 200 AH Tall Tubular Batter</a></td> <td>4 Hours</td> <td>x 1</td> </tr> </table> </div> ";
		}

		else if(inverter_capacity=="1400 VA" || inverter_capacity=="1450 VA"  || inverter_capacity=="1500 VA" || inverter_capacity=="1650 VA" || inverter_capacity=="1700 VA" )
		{
			inverter_battery_options="<option value='0'>Select&nbsp;Battery</option><option value='100 AH Current Battery'>100 AH Current Battery</option><option value='100 AH Tubular Battery'>100 AH Tubular Battery</option><option value='135 AH Current Battery'>135 AH Current Battery</option><option value='135 AH Tubular Battery'>135 AH Tubular Battery</option><option value='150 AH Current Battery'>150 AH Current Battery</option><option value='150 AH Tubular Battery'>150 AH Tubular Battery</option><option value='150 AH Tall Tubular Battery'>150 AH Tall Tubular Battery</option><option value='165 AH Tall Tubular Battery'>165 AH Tall Tubular Battery</option><option value='170 AH Current Battery'>170 AH Current Battery</option>";
			
			pop_over_msg_inverter_battery ="<table width='100%' ><tr><td align='left' style='font-size: 12px;'><b>Suggested Inverter Battery for "+inverter_capacity+"</b></td><td align='right' ><a href='javascript:hidepopover(); ' ><b>Close</b></a></td></tr></table><div class='table-responsive' style=' margin-bottom: 0px;'> <table width='100%' cellpadding='2' class='popover_custom_table table table-condensed table-bordered table-striped'> <tr> <td colspan='1'><b>Backup Hours</b></td><td colspan='1'><b>Battery Type</b></td><td colspan='1'><b>Pcs of Battery</b></td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery_popover('100 AH Current Battery');\"> 100 AH Current Battery</a></td> <td>2 Hours</td> <td>x 2</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery_popover('100 AH Tubular Battery');\"> 100 AH Tubular Battery</a></td> <td>2 Hours</td> <td>x 2</td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery_popover('135 AH Current Battery');\"> 135 AH Current Battery</a></td> <td>3 Hours</td> <td>x 2</td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery_popover('135 AH Tubular Battery');\"> 135 AH Tubular Battery</a></td> <td>3 Hours</td> <td>x 2</td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery_popover('150 AH Current Battery');\"> 150 AH Current Battery</a></td> <td>3 Hours</br>30 Minutes</td> <td>x 2</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery_popover('150 AH Tubular Battery');\"> 150 AH Tubular Battery</a></td> <td>3 Hours</br>30 Minutes</td> <td>x 2</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery_popover('150 AH Tall Tubular Battery');\"> 150 AH Tall Tubular Battery</a></td> <td>3 Hours</br>30 Minutes</td> <td>x 2</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery_popover('165 AH Tall Tubular Battery');\"> 165 AH Tall Tubular Battery</a></td> <td>4 Hours</td> <td>x 2</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery_popover('170 AH Current Battery');\"> 170 AH Current Battery</a></td> <td>4 Hours</br>15 Minutes</td> <td>x 2</td> </tr> </table> </div> ";
		}
		
		else if(inverter_capacity=="2 KVA" || inverter_capacity=="3.5 KVA"  )
		{
			inverter_battery_options="<option value='0'>Select&nbsp;Battery</option><option value='100 AH Current Battery'>100 AH Current Battery</option><option value='100 AH Tubular Battery'>100 AH Tubular Battery</option><option value='135 AH Current Battery'>135 AH Current Battery</option><option value='135 AH Tubular Battery'>135 AH Tubular Battery</option><option value='150 AH Current Battery'>150 AH Current Battery</option><option value='150 AH Tubular Battery'>150 AH Tubular Battery</option><option value='150 AH Tall Tubular Battery'>150 AH Tall Tubular Battery</option><option value='165 AH Tall Tubular Battery'>165 AH Tall Tubular Battery</option><option value='170 AH Current Battery'>170 AH Current Battery</option>";
			
			pop_over_msg_inverter_battery ="<table width='100%' ><tr><td align='left' style='font-size: 12px;'><b>Suggested Inverter Battery for "+inverter_capacity+"</b></td><td align='right' ><a href='javascript:hidepopover(); ' ><b>Close</b></a></td></tr></table><div class='table-responsive' style=' margin-bottom: 0px;'> <table width='100%' cellpadding='2' class='popover_custom_table table table-condensed table-bordered table-striped'> <tr> <td colspan='1'><b>Backup Hours</b></td><td colspan='1'><b>Battery Type</b></td><td colspan='1'><b>Pcs of Battery</b></td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery_popover('100 AH Current Battery');\"> 100 AH Current Battery</a></td> <td>2 Hours</td> <td>x 4</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery_popover('100 AH Tubular Battery');\"> 100 AH Tubular Battery</a></td> <td>2 Hours</td> <td>x 4</td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery_popover('135 AH Current Battery');\"> 135 AH Current Battery</a></td> <td>3 Hours</td> <td>x 4</td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery_popover('135 AH Tubular Battery');\"> 135 AH Tubular Battery</a></td> <td>3 Hours</td> <td>x 4</td> </tr> <tr><td> <a href=\"javascript:Select_inverter_battery_popover('150 AH Current Battery');\"> 150 AH Current Battery</a></td> <td>3 Hours</br>30 Minutes</td> <td>x 4</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery_popover('150 AH Tubular Battery');\"> 150 AH Tubular Battery</a></td> <td>3 Hours</br>30 Minutes</td> <td>x 4</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery_popover('150 AH Tall Tubular Battery');\"> 150 AH Tall Tubular Battery</a></td> <td>3 Hours</br>30 Minutes</td> <td>x 4</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery_popover('165 AH Tall Tubular Battery');\"> 165 AH Tall Tubular Battery</a></td> <td>4 Hours</td> <td>x 4</td> </tr> <tr> <td> <a href=\"javascript:Select_inverter_battery_popover('170 AH Current Battery');\"> 170 AH Current Battery</a></td> <td>4 Hours</br>15 Minutes</td> <td>x 4</td> </tr> </table> </div> ";
		}

		else if(inverter_capacity=="1450 VA" )
		{
			
			inverter_battery_options="<option value='0'>Select&nbsp;Battery</option><option value='100 AH Tubular Battery'>100 AH Tubular Battery</option><option value='150 AH Tubular Battery'>150 AH Tubular Battery</option><option value='150 AH Tall Tubular Battery'>150 AH Tall Tubular Battery</option><option value='200 AH Tall Tubular Battery'>200 AH Tall Tubular Battery</option>";
				
			pop_over_msg_inverter_battery ="<table width='100%' ><tr><td align='left' style='font-size: 12px;'><b>Suggested Inverter Battery for "+inverter_capacity+"</b></td><td align='right' ><a href='javascript:hidepopover(); ' ><b>Close</b></a></td></tr></table><div class='table-responsive' style=' margin-bottom: 0px;'> <table width='100%'  cellpadding='2' class='popover_custom_table table table-condensed table-bordered table-striped'><tr > <td colspan='1' ><b>Backup</b></td> <td colspan='1' ><b>&nbsp;2&nbsp;&nbsp;Hours&nbsp;</b></td><td colspan='1'><b>3 Hours 30Minutes</b></td><td colspan='1'><b>4 Hours 30Minutes</b></td></tr><tr > <td >Tubular Battery</td> <td><a href=\"javascript:Select_inverter_battery_popover('100 AH Tubular Battery');\" >100AH<br>&nbsp;&nbsp;&nbsp;&nbsp;+ <br>100AH</a></td> <td ><a href=\"javascript:Select_inverter_battery_popover('150 AH Tubular Battery');\" >150AH<br>&nbsp;&nbsp;&nbsp;&nbsp;+ <br>150AH </a></td> <td > - </td><td > - </td></tr><tr > <td >Tall Tubular Battery</td> <td > - </td> <td ><a href=\"javascript:Select_inverter_battery_popover('150 AH Tall Tubular Battery');\" >150AH<br>&nbsp;&nbsp;&nbsp;&nbsp;+ <br>150AH </a></td> <td ><a href=\"javascript:Select_inverter_battery_popover('200 AH Tall Tubular Battery');\" >200AH<br>&nbsp;&nbsp;&nbsp;&nbsp;+ <br>200AH </a> </td><td > - </td></tr></table></div>";
		}
		else
		{
			$("#product_inverter_battery").html("Loading ...");
			$('#product_inverter_capacity_div').popover('destroy');
			$('#product_inverter_battery_div').popover('destroy');
			$.ajax
			({
				type: "GET",
				url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getbatterycapacity",
				data: {batterytype: "Inverter Batteries"},
				success: function(data)
				{	
					$("#product_inverter_battery").html(data);
					return;
				}
			});
		}
		
		$("#product_inverter_battery").html(inverter_battery_options);
		
		$('#product_inverter_battery_div').popover({
			content:pop_over_msg_inverter_battery,
			placement: "top",
			title: "Battery Range ",
			html: true
		})
		
		$("#product_inverter_battery_div").popover('show');
		
		if (screen.width <= 767)
		{
			
			setTimeout(function(){
			var describedby = $("#product_inverter_battery_div").attr("aria-describedby");
			 $('html, body').animate({scrollTop: $("#"+describedby+"").offset().top},500);
			}, 500);
		}
		else{}
	}
	else
	{
		$('#product_inverter_capacity_div').popover('destroy');
		$('#product_inverter_battery_div').popover('destroy');
	}
}
function Select_inverter_capacity_popover(selectValue){
	
	$("#product_inverter_capacity").val(selectValue).change();
}

function Select_inverter_battery_popover(selectValue){
	
	$("#product_inverter_battery").val(selectValue).change();
}



$( "#product_inverter_battery" ).focus(function() 
{
	get_inverter_battery_popover()
})

$( "#product_inverter_capacity" ).change(function() 
{
	//get_inverter_battery_popover()
	if($("#product_type").val()=="Inverter")
	{
		$('#product_inverter_capacity_div').popover('destroy');
		$('#product_inverter_battery_div').popover('destroy');
		$("#product_inverter_battery").focus();
	}
	else
	{
		$('#product_inverter_capacity_div').popover('destroy');
		$("#product_inverter_battery").focus();
	}
})

$( "#product_city" ).change(function() 
{
	$("#find_battery_btn").focus();
})

$("#product_inverter_battery").change(function() 
{
	$("#product_state").focus();
	$('#product_inverter_capacity_div').popover('destroy');
	$('#product_inverter_battery_div').popover('destroy');
})


function hidepopover(){
	
	$(this).popover('hide');
	$('#product_inverter_capacity_div').popover('destroy');
	$('#product_inverter_battery_div').popover('destroy');
}