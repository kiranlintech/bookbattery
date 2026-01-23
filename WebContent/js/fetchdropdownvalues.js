
/** Function to fetch battery capactities when use select battery type **/


$(document).ready(function()
{
	$('#pincode2').hide();
	$('#pincode3').hide();
	$('#searcharea').hide();
	prepareInputsForHints()
	var keyword= document.getElementById("keyword").value;

	if(keyword=="Inverter Batteries")
	{
			$('#pincode2').hide();
			$('#pincode3').hide();
			$('#searcharea').hide();

			//document.getElementById("vehicle_name").focus();
			$category = $('#battery_type');
		   $category.change(
        function() 
		{
			var splitvalb =$category.val();
		
				for(i=document.getElementById("vehicle_model").options.length-1;i>=1;i--)
				{
					document.getElementById("vehicle_model").remove(i);
				}
				for(i=document.getElementById("bat_brand").options.length-1;i>=1;i--)
				{
				document.getElementById("bat_brand").remove(i);
				}
				for(i=document.getElementById("bat_brand1").options.length-1;i>=1;i--)
				{
					document.getElementById("bat_brand1").remove(i);
				}
				for(i=document.getElementById("state").options.length-1;i>=1;i--)
				{
				document.getElementById("state").remove(i);
				}
				for(i=document.getElementById("city").options.length-1;i>=1;i--)
				{
				document.getElementById("city").remove(i);
				}
				/*for(i=document.getElementById("area").options.length-1;i>=1;i--)
				{
				document.getElementById("area").remove(i);
				}*/
				for(i=document.getElementById("laptop_model").options.length-1;i>=1;i--)
				{
				document.getElementById("laptop_model").remove(i);
				}
				for(i=document.getElementById("laptop_product").options.length-1;i>=1;i--)
				{
				document.getElementById("laptop_product").remove(i);
				}
				document.getElementById("battery_type").focus();

				if(splitvalb=="Inverter Batteries" || splitvalb=="Flat Plate Battery" || splitvalb=="Tubular Battery" || splitvalb=="Tall Tubular Battery")
				{	
					
					$('#img2').show();
					$.ajax
					({
						type: "GET",
						url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getbatterycapacity",
						data: {batterytype: $category.val() },
						success: function(data)
						{	
							$('#img2').hide();
							$("#batt_capacity").html(data)
						}
					});
					$.ajax
					({
						type: "GET",
						url: "/bookbattery/servlet/InvertersDetails?hidWhatToDo=getinvertercapacity",
						data: { },
						success: function(data)
						{	
							$("#inverter_capacity").html(data)
							
						}
					});
					$('#vehiclemake').hide();
					$('#vehiclemodel').hide();
					$('#inverterbatterybrand').show();
					$('#vehiclebrand').hide();
					$('#inverterbatterycapacity').show();
					$('#invertercapacity').show();
					$('#laptopproduct').hide();
					$('#searchpin').show();
					$('#laptopsbutton').hide();
					$('#laptopmodel').hide();
					$('#laptopmake').hide();
					$('#findbatterybutton').show();
					if($('#pincode2').is(':visible')&&$('#pincode2').is(':visible')&&$('#searcharea').is(':visible'))
					{				
						$('#statediv').hide();
						$('#citydiv').hide();
						$('#areadiv').hide();
						$('#searchpin').hide();
					}		
					document.getElementById("batt_capacity").focus();
		  }
		});
		
	}
	});



$(document).ready(function()
{
	$('#pincode2').hide();
	$('#pincode3').hide();
	$('#searcharea').hide();
	prepareInputsForHints()
	var keyword= document.getElementById("keyword").value;

	if(keyword=="Car Batteries" ||  keyword=="Bike Batteries" || keyword=="Inverter Batteries" || keyword=="Bus Batteries"  || keyword=="Truck Batteries"  || keyword=="Tractor Batteries" || keyword=="Three Wheeler Batteries"|| keyword=="Gensets Batteries" )
	{
			$('#pincode2').hide();
			$('#pincode3').hide();
			$('#searcharea').hide();

			//document.getElementById("vehicle_name").focus();
			$category = $('#battery_type');
		
			var splitvalb =$category.val();
		
				for(i=document.getElementById("vehicle_model").options.length-1;i>=1;i--)
				{
					document.getElementById("vehicle_model").remove(i);
				}
				for(i=document.getElementById("bat_brand").options.length-1;i>=1;i--)
				{
				document.getElementById("bat_brand").remove(i);
				}
				for(i=document.getElementById("bat_brand1").options.length-1;i>=1;i--)
				{
					document.getElementById("bat_brand1").remove(i);
				}
				for(i=document.getElementById("state").options.length-1;i>=1;i--)
				{
				document.getElementById("state").remove(i);
				}
				for(i=document.getElementById("city").options.length-1;i>=1;i--)
				{
				document.getElementById("city").remove(i);
				}
				/*for(i=document.getElementById("area").options.length-1;i>=1;i--)
				{
				document.getElementById("area").remove(i);
				}*/
				for(i=document.getElementById("laptop_model").options.length-1;i>=1;i--)
				{
				document.getElementById("laptop_model").remove(i);
				}
				for(i=document.getElementById("laptop_product").options.length-1;i>=1;i--)
				{
				document.getElementById("laptop_product").remove(i);
				}
				document.getElementById("battery_type").focus();

				if(splitvalb=="Inverter Batteries" || splitvalb=="Flat Plate Battery" || splitvalb=="Tubular Battery" || splitvalb=="Tall Tubular Battery")
				{	
					
					$('#img2').show();
					$.ajax
					({
						type: "GET",
						url: "./servlet/BatteryHome?hidWhatToDo=getbatterycapacity",
						data: {batterytype: $category.val() },
						success: function(data)
						{	
							$('#img2').hide();
							$("#batt_capacity").html(data)
						}
					});
					$('#vehiclemake').hide();
					$('#vehiclemodel').hide();
					$('#inverterbatterybrand').show();
					$('#vehiclebrand').hide();
					$('#inverterbatterycapacity').show();
					$('#laptopproduct').hide();
					$('#searchpin').show();
					$('#laptopsbutton').hide();
					$('#laptopmodel').hide();
					$('#laptopmake').hide();
					$('#findbatterybutton').show();
					if($('#pincode2').is(':visible')&&$('#pincode2').is(':visible')&&$('#searcharea').is(':visible'))
					{				
						$('#statediv').hide();
						$('#citydiv').hide();
						$('#areadiv').hide();
						$('#searchpin').hide();
					}		
					document.getElementById("batt_capacity").focus();
		  }
		 else
		 {	
			 $('#img2').show();
		         $.ajax
				({					 
                    type: "GET",
                     url: "./servlet/BatteryHome?hidWhatToDo=getvehiclename",
                     data: {batterytype: $category.val() },
                    success: function(data)
					{	
					
						$('#img2').hide();
						$('#vehiclemake').show();
						$('#vehiclemodel').show();
						$('#vehiclebrand').show();
						$('#inverterbatterybrand').hide();
						$('#inverterbatterycapacity').hide();
						$('#laptopproduct').hide();
						$('#searchpin').show();
						$('#laptopsbutton').hide();
						$('#laptopmodel').hide();
						$('#laptopmake').hide();
						$('#findbatterybutton').show();
						if($('#pincode2').is(':visible')&&$('#pincode2').is(':visible')&&$('#searcharea').is(':visible'))
						{				
							$('#statediv').hide();
							$('#citydiv').hide();
							$('#areadiv').hide();
							$('#searchpin').hide();
						}
						if(data.indexOf("defaultss")>=0)
						{
							$("#vehicle_name").html(data)
							//document.getElementById("vehicle_name").focus();
						}
						else
						{
							$("#vehicle_name").html(data)
							//document.getElementById("vehicle_name").focus();

						}
		            }
                });	
			}
	}
	else
	{
		$category = $('#battery_type');
        $category.change(
        function() 
		{
			
			//alert("s1");
			var splitvalb =$category.val();
			//alert(splitvalb);
			if(splitvalb=="")
			{
				//alert("1");
				$('#battery_type').addClass('highlightbefore');
				$('#battery_type').removeClass('highlightafter');

			}
			else
			{
				//alert("2");
				$('#battery_type').removeClass('highlightbefore');
				$('#battery_type').addClass('highlightafter');
			}
			
			var splitvalb =$category.val();
			document.getElementById("displysesmsg").innerHTML="";
			for(i=document.getElementById("vehicle_model").options.length-1;i>=1;i--)
			{
				document.getElementById("vehicle_model").remove(i);
			}
			for(i=document.getElementById("bat_brand").options.length-1;i>=1;i--)
			{
				document.getElementById("bat_brand").remove(i);
			}
			for(i=document.getElementById("bat_brand1").options.length-1;i>=1;i--)
			{
				document.getElementById("bat_brand1").remove(i);
			}
			for(i=document.getElementById("state").options.length-1;i>=1;i--)
			{
				document.getElementById("state").remove(i);
			}
			for(i=document.getElementById("city").options.length-1;i>=1;i--)
			{
				document.getElementById("city").remove(i);
			}
			/*for(i=document.getElementById("area").options.length-1;i>=1;i--)
			{
				document.getElementById("area").remove(i);
			}*/
			for(i=document.getElementById("laptop_model").options.length-1;i>=1;i--)
			{
				document.getElementById("laptop_model").remove(i);
			}
			for(i=document.getElementById("laptop_product").options.length-1;i>=1;i--)
			{
				document.getElementById("laptop_product").remove(i);
			}		
			document.getElementById("battery_type").focus();
			/*document.getElementById("city").options.value=0;
			document.batteryindex.city_names.options.value=0;*/
		if(splitvalb=="Inverter Batteries")
		{
			$('#img2').show();
				
				$.ajax
				({
					
                    type: "GET",
                    url: "./servlet/BatteryHome?hidWhatToDo=getbatterycapacity",
                    data: {batterytype: $category.val(), batterytype: $category.val() },
                    success: function(data)
					{
						$('#img2').hide();
						
                        $("#batt_capacity").html(data)
                    }
                });
				$('#vehiclemake').hide();
				$('#vehiclemodel').hide();
				$('#inverterbatterybrand').show();
				$('#vehiclebrand').hide();
				$('#inverterbatterycapacity').show();
				$('#laptopproduct').hide();
				$('#searchpin').show();
				$('#laptopsbutton').hide();
				$('#laptopmodel').hide();
				$('#laptopmake').hide();
				$('#findbatterybutton').show();
				if($('#pincode2').is(':visible')&&$('#pincode3').is(':visible')&&$('#searcharea').is(':visible'))
				{				
					$('#statediv').hide();
					$('#citydiv').hide();
					$('#areadiv').hide();
					$('#searchpin').hide();
				}		
				document.getElementById("batt_capacity").focus();
		  }
		  else if(splitvalb=="Laptop Batteries")
		  {
			 
			  $('#img2').show();
			  
			$.ajax
				({
                    type: "GET",
                    url: "./servlet/LaptopBatteryDetails?hidWhatToDo=getlaptopname",
                    data: {batterytype: $category.val(), batterytype: $category.val() },
                    success: function(data)
					{
						$('#img2').hide();
						

                        $("#laptop_name").html(data)
                    }
                });
				
				$('#vehiclemake').hide();
				$('#vehiclemodel').hide();
				$('#laptopmake').show();
				$('#vehiclebrand').hide();
				$('#laptopmodel').show();
				$('#laptopproduct').show();
				$('#statediv').show();
				$('#citydiv').show();
				$('#areadiv').show();
				$('#laptopsbutton').show();
				$('#findbatterybutton').hide();
				$('#inverterbatterybrand').hide();
				$('#inverterbatterycapacity').hide();
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
				document.getElementById("laptop_name").focus();
		   }
		   else if(splitvalb=="Inverter")
		   {
				location.href="./inverter.jsp"
		   }
		  else
		  {
			  $('#img2').show();

              $.ajax
				({
                    type: "GET",
                    url: "./servlet/BatteryHome?hidWhatToDo=getvehiclename",
                    data: {batterytype: $category.val() },
                    success: function(data)
					{
						$('#img2').hide();

				
						$('#vehiclemake').show();
						$('#vehiclemodel').show();
						$('#vehiclebrand').show();
						$('#inverterbatterybrand').hide();
						$('#inverterbatterycapacity').hide();
						$('#laptopproduct').hide();
						$('#searchpin').show();
						$('#laptopsbutton').hide();
						$('#laptopmodel').hide();
						$('#laptopmake').hide();
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
							document.getElementById("battery_type").focus();
						}
						else
						{

								var vehtype="";
								//alert(splitvalb);
							if(splitvalb == "Car Batteries")
							{
								vehtype="Car";
							}
							if(splitvalb == "Bike Batteries")
							{
								vehtype="Bike";
							}
							if(splitvalb == "Bus Batteries")
							{
								vehtype="Bus";
							}
							if(splitvalb == "Tractor Batteries")
							{
								vehtype="Tractor";
							}
							if(splitvalb == "Truck Batteries")
							{
								vehtype="Truck";
							}
							if(splitvalb == "Three Wheeler Batteries")
							{
								vehtype="Three Wheeler";
							}
							if(splitvalb == "Special Vehicle Batteries")
							{
								vehtype="Special Vehicle";
							}
							if(splitvalb == "Genset Batteries")
							{
								vehtype="Genset";
							}
							if(splitvalb == "Crane Batteries")
							{
								vehtype="Crane";
							}
							if(splitvalb == "Roller Batteries")
							{
								vehtype="Roller";
							}
							if(splitvalb == "Loader Batteries")
							{
								vehtype="Loader";
							}
							if(splitvalb == "Dozer Batteries")
							{
								vehtype="Dozer";
							}
							if(splitvalb == "Excavator Batteries")
							{
								vehtype="Excavator";
							}
							if(splitvalb == "Tyre Handler Batteries")
							{
								vehtype="Tyre Handler";
							}
							if(splitvalb == "Hydraulic Shovel Batteries")
							{
								vehtype="Hydraulic Shovel";
							}
							if(splitvalb == "Harvestor Batteries")
							{
								vehtype="Harvestor";
							}
							if(splitvalb == "Generator Batteries")
							{
								vehtype="Generator";
							}
							if(splitvalb == "Compactor Batteries")
							{
								vehtype="Compactor";
							}
							if(splitvalb == "Telescopic Handler Batteries")
							{
								vehtype="Telescopic Handler";
							}
							if(splitvalb == "Forwarder Batteries")
							{
								vehtype="Forwarder";
							}
							if(splitvalb == "Wheeled Harvester Batteries")
							{
								vehtype="Wheeled Harvester";
							}
							if(splitvalb == "Minibus Batteries")
							{
								vehtype="Minibus";
							}
							if(splitvalb == "Dumper Batteries")
							{
								vehtype="Dumper";
							}
							if(splitvalb == "Construction Equipment Batteries")
							{
								vehtype="Construction Equipment";
							}
							if(splitvalb == "Hydralic Excavator Batteries")
							{
								vehtype="Hydralic Excavator";
							}

							var displaymsg="<option style='background:#FFF;color: black;' value='default'>&nbsp;Select&nbsp;"+vehtype+"&nbsp;Model</option>";
							$("#vehicle_model").html(displaymsg)
							$("#vehicle_name").html(data)
							document.getElementById("vehicle_name").focus();
						}
					}
                });
				}
            }
        );
	}
	});
$(document).ready(function()
{
	$category25 = $('#laptop_name');
        $category25.change(
            function() 
			{
					//alert("s1");
			var splitvalb =$category25.val();
			//alert(splitvalb);
			if(splitvalb=="default")
			{
				//alert("1");
				$('#laptop_name').addClass('highlightbefore');
				$('#laptop_name').removeClass('highlightafter');

			}
			else
			{
				//alert("2");
				$('#laptop_name').removeClass('highlightbefore');
				$('#laptop_name').addClass('highlightafter');
			}
				
				document.getElementById("displysesmsg").innerHTML="";
					for(i=document.getElementById("laptop_model").options.length-1;i>=1;i--)
					{
					document.getElementById("laptop_model").remove(i);
					}
					for(i=document.getElementById("laptop_product").options.length-1;i>=1;i--)
					{
					document.getElementById("laptop_product").remove(i);
					}
					for(i=document.getElementById("state").options.length-1;i>=1;i--)
					{
					document.getElementById("state").remove(i);
					}
					for(i=document.getElementById("city").options.length-1;i>=1;i--)
					{
					document.getElementById("city").remove(i);
					}
					/*for(i=document.getElementById("area").options.length-1;i>=1;i--)
					{
					document.getElementById("area").remove(i);
					}	*/	
				document.getElementById("laptop_name").focus();
				$('#img3').show();
			
                $.ajax({
					
                    type: "GET",
                    url: "./servlet/LaptopBatteryDetails?hidWhatToDo=getlaptop_model",
                    data: {laptopname: $category25.val(), batterytype: $category.val()},
					//data: {batterytype: $category.val() },
                    success: function(data)
					{
						$('#img3').hide();
						if(data.indexOf("defaultss")>=0)
						{
							$("#laptop_model").html(data)
							document.getElementById("laptop_name").focus();
						}
						else
						{
							$("#laptop_model").html(data)
							document.getElementById("laptop_model").focus();
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
						//alert("s1");
			var splitvalb =$categoryproduct.val();
			//alert(splitvalb);
			if(splitvalb=="default")
			{
				//alert("1");
				$('#laptop_model').addClass('highlightbefore');
				$('#laptop_model').removeClass('highlightafter');

			}
			else
			{
				//alert("2");
				$('#laptop_model').removeClass('highlightbefore');
				$('#laptop_model').addClass('highlightafter');
			}
				
				document.getElementById("displysesmsg").innerHTML="";
				for(i=document.getElementById("laptop_product").options.length-1;i>=1;i--)
				{
				document.getElementById("laptop_product").remove(i);
				}
				for(i=document.getElementById("state").options.length-1;i>=1;i--)
				{
				document.getElementById("state").remove(i);
				}
				for(i=document.getElementById("city").options.length-1;i>=1;i--)
				{
				document.getElementById("city").remove(i);
				}
				/*for(i=document.getElementById("area").options.length-1;i>=1;i--)
				{
				document.getElementById("area").remove(i);
				}*/	
				document.getElementById("laptop_model").focus();
				$('#img4').show();
				
                $.ajax({
					
                    type: "GET",
                    url: "./servlet/LaptopBatteryDetails?hidWhatToDo=getlaptop_product",
                    data: {laptopname: $category25.val(), batterytype: $category.val(), laptopmodel: $categoryproduct.val()},
					//data: {batterytype: $category.val() },
                    success: function(data)
					{
						$('#img4').hide();
						
						if(	data.indexOf("defaultss")>=0)
						{
							$("#laptop_product").html(data)
							document.getElementById("laptop_model").focus();
						}
						else
						{
							$("#laptop_product").html(data)
							document.getElementById("laptop_product").focus();
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
				
				var splitvalb =$category2.val();
			//alert(splitvalb);
			if(splitvalb=="default")
			{
				//alert("1");
				$('#vehicle_name').addClass('highlightbefore');
				$('#vehicle_name').removeClass('highlightafter');

			}
			else
			{
				//alert("2");
				$('#vehicle_name').removeClass('highlightbefore');
				$('#vehicle_name').addClass('highlightafter');
			}
			document.getElementById("displysesmsg").innerHTML="";
			for(i=document.getElementById("bat_brand").options.length-1;i>=1;i--)
			{
			document.getElementById("bat_brand").remove(i);
			}
			for(i=document.getElementById("state").options.length-1;i>=1;i--)
			{
			document.getElementById("state").remove(i);
			}
			for(i=document.getElementById("city").options.length-1;i>=1;i--)
			{
			document.getElementById("city").remove(i);
			}
			/*for(i=document.getElementById("area").options.length-1;i>=1;i--)
			{
			document.getElementById("area").remove(i);
			}*/
			document.getElementById("vehicle_name").focus();
			 $('#img3').show();
			
                $.ajax({
					
                    type: "GET",
                    url: "./servlet/BatteryHome?hidWhatToDo=getvehicle_model",
                    data: {vehiclename: $category2.val(), batterytype: $category.val()},
				
                    success: function(data)
					{
							 $('#img3').hide();
						if(data.indexOf("defaultss")>=0)
						{
                        $("#vehicle_model").html(data)
						document.getElementById("vehicle_name").focus();
						}
						else
						{
						 $("#vehicle_model").html(data)
						document.getElementById("vehicle_model").focus();
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
				
				var splitvalb =$category3.val();
			//alert(splitvalb);
			if(splitvalb=="default")
			{
				//alert("1");
				$('#vehicle_model').addClass('highlightbefore');
				$('#vehicle_model').removeClass('highlightafter');

			}
			else
			{
				//alert("2");
				$('#vehicle_model').removeClass('highlightbefore');
				$('#vehicle_model').addClass('highlightafter');
			}
				
				document.getElementById("displysesmsg").innerHTML="";
							var keyword=document.getElementById("brandkeyword").value;

					var splitval =$category3.val();
					
						for(i=document.getElementById("bat_brand").options.length-1;i>=1;i--)
						{
						document.getElementById("bat_brand").remove(i);
						}
						for(i=document.getElementById("state").options.length-1;i>=1;i--)
						{
						document.getElementById("state").remove(i);
						}
						for(i=document.getElementById("city").options.length-1;i>=1;i--)
						{
						document.getElementById("city").remove(i);
						}
						/*for(i=document.getElementById("area").options.length-1;i>=1;i--)
						{
						document.getElementById("area").remove(i);
						}*/
						document.getElementById("vehicle_model").focus();
					if(splitval == "default")
						{
						for(i=document.getElementById("bat_brand").options.length-1;i>=1;i--)
						{
						document.getElementById("bat_brand").remove(i);
						}
						for(i=document.getElementById("state").options.length-1;i>=1;i--)
						{
						document.getElementById("state").remove(i);
						}
						for(i=document.getElementById("city").options.length-1;i>=1;i--)
						{
						document.getElementById("city").remove(i);
						}
						/*for(i=document.getElementById("area").options.length-1;i>=1;i--)
						{
						document.getElementById("area").remove(i);
						}*/
						document.getElementById("vehicle_model").focus();
					}
					else
					{
						 $('#img4').show();
			
						 $.ajax({
							type: "GET",
							url: "./servlet/BatteryHome?hidWhatToDo=getbat_brand",
							data: {vehiclemodel: $category3.val(), keyword:keyword },
							success: function(data)
							{
									 $('#img4').hide();
								if(data.indexOf("defaultss")>=0)
								{
								$("#bat_brand").html(data)
								document.getElementById("vehicle_model").focus();
								}
								else
								{
								 $("#bat_brand").html(data)
								document.getElementById("bat_brand").focus();
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
				
							//alert("s1");
			var splitvalb =$category4.val();
			//alert(splitvalb);
			if(splitvalb=="default")
			{
				//alert("1");
				$('#batt_capacity').addClass('highlightbefore');
				$('#batt_capacity').removeClass('highlightafter');

			}
			else
			{
				//alert("2");
				$('#batt_capacity').removeClass('highlightbefore');
				$('#batt_capacity').addClass('highlightafter');
			}
				
				document.getElementById("displysesmsg").innerHTML="";
				var keyword=document.getElementById("brandkeyword").value;

					var splitvalban =$category4.val();
					
						for(i=document.getElementById("bat_brand1").options.length-1;i>=1;i--)
						{
						document.getElementById("bat_brand1").remove(i);
						}
						for(i=document.getElementById("state").options.length-1;i>=1;i--)
						{
						document.getElementById("state").remove(i);
						}
						for(i=document.getElementById("city").options.length-1;i>=1;i--)
						{
						document.getElementById("city").remove(i);
						}
						/*for(i=document.getElementById("area").options.length-1;i>=1;i--)
						{
						document.getElementById("area").remove(i);
						}*/
						document.getElementById("batt_capacity").focus();
						if(splitvalban == "default")
						{
						for(i=document.getElementById("bat_brand1").options.length-1;i>=1;i--)
						{
						document.getElementById("bat_brand1").remove(i);
						}
						for(i=document.getElementById("state").options.length-1;i>=1;i--)
						{
						document.getElementById("state").remove(i);
						}
						for(i=document.getElementById("city").options.length-1;i>=1;i--)
						{
						document.getElementById("city").remove(i);
						}
						/*for(i=document.getElementById("area").options.length-1;i>=1;i--)
						{
						document.getElementById("area").remove(i);
						}*/
						document.getElementById("batt_capacity").focus();
						}
						else
						{
					
							$('#img3').show();
							
			    $.ajax({
                    type: "GET",
                    url: "./servlet/BatteryHome?hidWhatToDo=getbat_brand",
                    data: {vehiclemodel: $category4.val(), keyword:keyword,flag:"inverterbattery" },
                    success: function(data)
					{
						$('#img3').hide();
						if(data.indexOf("defaultss")>=0)
						{
							$("#bat_brand1").html(data)
							document.getElementById("batt_capacity").focus();
						}
						else
						{
							 $("#bat_brand1").html(data)
							document.getElementById("bat_brand1").focus();
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
				
					//alert("s1");
			var splitvalb =$categorybat.val();
			//alert(splitvalb);
			if(splitvalb=="default")
			{
				//alert("1");
				$('#bat_brand').addClass('highlightbefore');
				$('#bat_brand').removeClass('highlightafter');

			}
			else
			{
				//alert("2");
				$('#bat_brand').removeClass('highlightbefore');
				$('#bat_brand').addClass('highlightafter');
			}
				
				document.getElementById("displysesmsg").innerHTML="";
					var splitvalcity =$categorybat.val();
						for(i=document.getElementById("state").options.length-1;i>=1;i--)
						{
						document.getElementById("state").remove(i);
						}
						for(i=document.getElementById("city").options.length-1;i>=1;i--)
						{
						document.getElementById("city").remove(i);
						}
						/*for(i=document.getElementById("area").options.length-1;i>=1;i--)
						{
						document.getElementById("area").remove(i);
						}*/
						document.getElementById("bat_brand").focus();
						if(splitvalcity == "default")
						{
						for(i=document.getElementById("state").options.length-1;i>=1;i--)
						{
						document.getElementById("state").remove(i);
						}
						for(i=document.getElementById("city").options.length-1;i>=1;i--)
						{
						document.getElementById("city").remove(i);
						}
						/*for(i=document.getElementById("area").options.length-1;i>=1;i--)
						{
						document.getElementById("area").remove(i);
						}*/
						document.getElementById("bat_brand").focus();
						}
						else
						{
							$('#img5').show();
							
							$.ajax({
								type: "GET",
								url: "./servlet/BatteryHome?hidWhatToDo=getstate",
								data: {brands: $categorybat.val() },
								success: function(data)
								{
										$('#img5').hide();
									if(data.indexOf("defaultss")>=0)
									{
									$("#state").html(data)
									document.getElementById("state").focus();
									}
									else
									{
									 $("#state").html(data)
									document.getElementById("state").focus();
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
				
					//alert("s1");
			var splitvalb =$category21.val();
			//alert(splitvalb);
			if(splitvalb=="default")
			{
				//alert("1");
				$('#bat_brand1').addClass('highlightbefore');
				$('#bat_brand1').removeClass('highlightafter');

			}
			else
			{
				//alert("2");
				$('#bat_brand1').removeClass('highlightbefore');
				$('#bat_brand1').addClass('highlightafter');
			}
				
				document.getElementById("displysesmsg").innerHTML="";
					var splitvalcity =$category21.val();
					
						for(i=document.getElementById("state").options.length-1;i>=1;i--)
						{
						document.getElementById("state").remove(i);
						}
						for(i=document.getElementById("city").options.length-1;i>=1;i--)
						{
						document.getElementById("city").remove(i);
						}
						/*for(i=document.getElementById("area").options.length-1;i>=1;i--)
						{
						document.getElementById("area").remove(i);
						}*/
						document.getElementById("bat_brand1").focus();
						if(splitvalcity == "default")
						{
							for(i=document.getElementById("state").options.length-1;i>=1;i--)
							{
							document.getElementById("state").remove(i);
							}
							for(i=document.getElementById("city").options.length-1;i>=1;i--)
							{
							document.getElementById("city").remove(i);
							}
							/*for(i=document.getElementById("area").options.length-1;i>=1;i--)
							{
							document.getElementById("area").remove(i);
							}*/
							document.getElementById("bat_brand1").focus();
						}
						else
						{
								$('#img5').show();
								
							$.ajax({
								type: "GET",
								url: "./servlet/BatteryHome?hidWhatToDo=getstate",
								data: {brands: $category21.val() },
								success: function(data)
								{
									$('#img5').hide();
									if(data.indexOf("defaultss")>=0)
									{
										$("#state").html(data)
										document.getElementById("state").focus();
									}
									else
									{
										$("#state").html(data)
										document.getElementById("state").focus();
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
				
					//alert("s1");
			var splitvalb =$category212.val();
			//alert(splitvalb);
			if(splitvalb=="default")
			{
				//alert("1");
				$('#laptop_product').addClass('highlightbefore');
				$('#laptop_product').removeClass('highlightafter');

			}
			else
			{
				//alert("2");
				$('#laptop_product').removeClass('highlightbefore');
				$('#laptop_product').addClass('highlightafter');
			}
				
				document.getElementById("displysesmsg").innerHTML="";
					var splitvalcity =$category212.val();
					
						for(i=document.getElementById("state").options.length-1;i>=1;i--)
						{
						document.getElementById("state").remove(i);
						}
						for(i=document.getElementById("city").options.length-1;i>=1;i--)
						{
						document.getElementById("city").remove(i);
						}
						/*for(i=document.getElementById("area").options.length-1;i>=1;i--)
						{
						document.getElementById("area").remove(i);
						}*/
						document.getElementById("laptop_product").focus();
						if(splitvalcity == "default")
						{
							for(i=document.getElementById("state").options.length-1;i>=1;i--)
							{
								document.getElementById("state").remove(i);
							}
							for(i=document.getElementById("city").options.length-1;i>=1;i--)
							{
								document.getElementById("city").remove(i);
							}
							/*for(i=document.getElementById("area").options.length-1;i>=1;i--)
							{
								document.getElementById("area").remove(i);
							}*/
							document.getElementById("laptop_product").focus();
						}
						else
						{
							$('#img5').show();
							$.ajax({

								type: "GET",
								url: "./servlet/BatteryHome?hidWhatToDo=getstate",
								data: {laptop_product: $category212.val() },
								success: function(data)
								{
									$('#img5').hide();
									

									if(data.indexOf("defaultss")>=0)
									{
										$("#state").html(data)
										document.getElementById("state").focus();
									}
									else
									{
										$("#state").html(data)
										document.getElementById("state").focus();
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
				
				//alert("s1");
			var splitvalb =$category5.val();
			//alert(splitvalb);
			if(splitvalb=="default")
			{
				//alert("1");
				$('#state').addClass('highlightbefore');
				$('#state').removeClass('highlightafter');

			}
			else
			{
				//alert("2");
				$('#state').removeClass('highlightbefore');
				$('#state').addClass('highlightafter');
			}
				
				document.getElementById("displysesmsg").innerHTML="";
					var splitvalcity =$category5.val();
					
						for(i=document.getElementById("city").options.length-1;i>=1;i--)
						{
						document.getElementById("city").remove(i);
						}
						/*for(i=document.getElementById("area").options.length-1;i>=1;i--)
						{
						document.getElementById("area").remove(i);
						}*/
						document.getElementById("state").focus();
						if(splitvalcity == "default")
						{
						for(i=document.getElementById("city").options.length-1;i>=1;i--)
						{
						document.getElementById("city").remove(i);
						}
						/*for(i=document.getElementById("area").options.length-1;i>=1;i--)
						{
						document.getElementById("area").remove(i);
						}*/
						document.getElementById("state").focus();
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
						document.getElementById("city").focus();
						}
						else
						{
						 $("#city").html(data)
						document.getElementById("city").focus();
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
				
				//alert("s1");
			var splitvalb =$category6.val();
			//alert(splitvalb);
			if(splitvalb=="default")
			{
				//alert("1");
				$('#city').addClass('highlightbefore');
				$('#city').removeClass('highlightafter');

			}
			else
			{
				//alert("2");
				$('#city').removeClass('highlightbefore');
				$('#city').addClass('highlightafter');
			}	
				
			
				document.getElementById("displysesmsg").innerHTML="";
					var splitvalarea =$category6.val();
					
						for(i=document.getElementById("area").options.length-1;i>=1;i--)
						{
						document.getElementById("area").remove(i);
						}
						document.getElementById("city").focus();
						if(splitvalarea == "default")
						{
						/*for(i=document.getElementById("area").options.length-1;i>=1;i--)
						{
						document.getElementById("area").remove(i);
						}*/
						document.getElementById("city").focus();
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
						document.getElementById("area").focus();
						}
						else
						{
						 $("#area").html(data)
						document.getElementById("area").focus();
						}
                    }
                });
			  }
            }
        );
	});
function funToGetbatterydetails()
{
	var batterytype = document.getElementById("battery_type").value;      
	var vehiclename = document.getElementById("vehicle_name").value;
	var vehiclemodel = escape(document.getElementById("vehicle_model").value);
	var batterybrand = document.getElementById("bat_brand").value;
	var batterybrand1 = document.getElementById("bat_brand1").value;
	var batterycapty = document.getElementById("batt_capacity").value;
	var state = document.getElementById("state").value;
	var city = document.getElementById("city").value;
	//var area = document.getElementById("area").value;
	//var pincode = document.getElementById("pincode").value;
	var backkeyword = document.getElementById("backkeyword").value;
	var area ="";
	var pincode="";
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
	
	if(batterytype == "Inverter Batteries" || batterytype == "Flat Plate Battery" || batterytype == "Tubular Battery" || batterytype == "Tall Tubular Battery")
	{
	if(document.getElementById("battery_type").value == 0)
	{
		errMsg ="<font color='#ff3333'>Please Select \'Battery Type\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.getElementById("battery_type").focus();
		return ;
	}
	if(document.getElementById("batt_capacity").value == 0 || batterycapty=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'Battery Capacity\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.getElementById("batt_capacity").focus();
		return ;
	}
	if(document.getElementById("bat_brand1").value == 0 || batterybrand1=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'Battery Brand\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.getElementById("bat_brand1").focus();
		return ;
	}
	vehiclename = "";
	vehiclemodel = "";
	batterybrand = batterybrand1;
	}
	else
	{
		var vehtype="";

		if(batterytype == "Car Batteries")
		{
			vehtype="Car";
		}
		if(batterytype == "Bike Batteries")
		{
			vehtype="Bike";
		}
		if(batterytype == "Bus Batteries")
		{
			vehtype="Bus";
		}
		if(batterytype == "Tractor Batteries")
		{
			vehtype="Tractor";
		}
		if(batterytype == "Truck Batteries")
		{
			vehtype="Truck";
		}
		if(batterytype == "Three Wheeler Batteries")
		{
			vehtype="Three Wheeler";
		}
		if(batterytype == "Special Vehicle Batteries")
		{
			vehtype="Special Vehicle";
		}
		if(batterytype == "Genset Batteries")
		{
			vehtype="Genset";
		}
		if(batterytype == "Crane Batteries")
		{
			vehtype="Crane";
		}
		if(batterytype == "Roller Batteries")
		{
			vehtype="Roller";
		}
		if(batterytype == "Loader Batteries")
		{
			vehtype="Loader";
		}
		if(batterytype == "Dozer Batteries")
		{
			vehtype="Dozer";
		}
		if(batterytype == "Excavator Batteries")
		{
			vehtype="Excavator";
		}
		if(batterytype == "Tyre Handler Batteries")
		{
			vehtype="Tyre Handler";
		}
		if(batterytype == "Hydraulic Shovel Batteries")
		{
			vehtype="Hydraulic Shovel";
		}
		if(batterytype == "Harvestor Batteries")
		{
			vehtype="Harvestor";
		}
		if(batterytype == "Generator Batteries")
		{
			vehtype="Generator";
		}
		if(batterytype == "Compactor Batteries")
		{
			vehtype="Compactor";
		}
		if(batterytype == "Telescopic Handler Batteries")
		{
			vehtype="Telescopic Handler";
		}
		if(batterytype == "Forwarder Batteries")
		{
			vehtype="Forwarder";
		}
		if(batterytype == "Wheeled Harvester Batteries")
		{
			vehtype="Wheeled Harvester";
		}
		if(batterytype == "Minibus Batteries")
		{
			vehtype="Minibus";
		}
		if(batterytype == "Dumper Batteries")
		{
			vehtype="Dumper";
		}
		if(batterytype == "Construction Equipment Batteries")
		{
			vehtype="Construction Equipment";
		}
		if(batterytype == "Hydralic Excavator Batteries")
		{
			vehtype="Hydralic Excavator";
		}

	if(document.getElementById("battery_type").value == 0)
	{
		errMsg ="<font color='#ff3333'>Please Select \'Battery Type\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.getElementById("battery_type").focus();
		return ;
	}
	if(document.getElementById("vehicle_name").value == 0 || vehiclename=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select "+vehtype+"\'Make\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.getElementById("vehicle_name").focus();
		return ;
	}
	if(document.getElementById("vehicle_model").value == 0 || vehiclemodel=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select "+vehtype+"\'Model\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.getElementById("vehicle_model").focus();
		return ;
	}
	vehiclename = vehiclename;
	vehiclemodel = vehiclemodel;
	batterybrand = batterybrand;
	batterycapty = "";
	if(document.getElementById("bat_brand").value == 0 || batterybrand=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'Battery Brand\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.getElementById("bat_brand").focus();
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
			document.getElementById("pincode").focus();
			return ;
		}
		if(document.getElementById("state").value == 0 || state=="default")
		{
			errMsg ="<font color='#ff3333'>Please Select \'State\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.getElementById("state").focus();
			return ;
		}
		if(document.getElementById("city").value == 0 || city=="default")
		{
			errMsg ="<font color='#ff3333'>Please Select \'City\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.getElementById("city").focus();
			return ;
		}
		/*if(document.getElementById("area").value == 0 || area=="default")
		{
			errMsg ="<font color='#ff3333'>Please Select \'Area\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.getElementById("area").focus();
			return ;
		}*/
	}
	else
	{
		/*if(document.getElementById("pincode").value == "" || pincode=="default")
		{
			errMsg ="<font color='#ff3333'>Please Enter \'Pincode\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.getElementById("pincode").focus();
			return ;
		}
		var pincoderegex=/^[0-9]{4,6}$/;
		if(!pincode.match(pincoderegex))
		{
			errMsg ="<font color='#ff3333'>Please Enter Valid \'Pincode\'.</font>";
			document.getElementById("displysesmsg").innerHTML=errMsg;
			document.getElementById("pincode").focus();
			return ;
		}*/
	}
	var sentence="";
	if(batterytype=="Bike Batteries")
	{
		sentence="<tr><td height='15' align='' style='font-family:;font-size:15px;color:#FFFFFF;text-decoration:none;padding:1px 1px;'><i class='fa fa-heart' style='font-size:large; color: #E2530C;'></i>&nbsp;&nbsp;Battery is like Heart of your Automobile</td></tr><tr><td height='15' align='left' style='font-family:;font-size:15px;color:#FFFFFF;text-decoration:none;padding:1px 1px;'><i class='fa fa-ban'style='font-size:large; color: #E2530C;'></i>&nbsp;&nbsp;Don't Buy a Re-furbished or Re-labelled Battery Available at Cheaper Price in the Market</td></tr><tr><td height='15' width='90%' align='left' style='font-family:;font-size:15px;color:#FFFFFF;text-decoration:none;padding:1px 1px;'><i class='fa fa-shopping-cart' style='font-size:large; color: #E2530C;'></i>&nbsp;&nbsp;Order with us and Buy a Genuine Battery at Reasonable Cost to save money in the Long Run.</td></tr><tr><td width='100%' align='left' style='font-family:;font-size:15px;color:red;text-decoration:none;padding:1px 1px;'><font color='#ff0000'>&nbsp;&nbsp;*</font>&nbsp;&nbsp;<I> Delivery&nbsp;of&nbsp;Bike&nbsp;Battery&nbsp;to&nbsp;your&nbsp;place&nbsp;is&nbsp;not&nbsp;avaliable. </I></td></tr>";
	}
	
	else if(batterytype=="Inverter Batteries")
	{
		sentence="<tr><td height='15' align='' style='font-family:;font-size:15px;color:#FFFFFF;text-decoration:none;padding:1px 1px;'><i class='fa fa-heart' style='font-size:large; color: #E2530C;'></i>&nbsp;&nbsp;Battery is like Heart of your Inverter</td></tr><tr><td height='15' align='left' style='font-family:;font-size:15px;color:#FFFFFF;text-decoration:none;padding:1px 1px;'><i class='fa fa-ban'style='font-size:large; color: #E2530C;'></i>&nbsp;&nbsp;Don't Buy a Re-furbished or Re-labelled Battery Available at Cheaper Price in the Market</td></tr><tr><td height='15' width='90%' align='left' style='font-family:;font-size:15px;color:#FFFFFF;text-decoration:none;padding:1px 1px;'><i class='fa fa-shopping-cart' style='font-size:large; color: #E2530C;'></i>&nbsp;&nbsp;Order with us and Buy a Genuine Battery at Reasonable Cost to save money in the Long Run.</td></tr>";
	}
	else
	{
		sentence="<tr><td height='15' align='' style='font-family:;font-size:15px;color:#FFFFFF;text-decoration:none;padding:1px 1px;'><i class='fa fa-heart' style='font-size:large; color: #E2530C;'></i>&nbsp;&nbsp;Battery is like Heart of your Automobile</td></tr><tr><td height='15' align='left' style='font-family:;font-size:15px;color:#FFFFFF;text-decoration:none;padding:1px 1px;'><i class='fa fa-ban'style='font-size:large; color: #E2530C;'></i>&nbsp;&nbsp;Don't Buy a Re-furbished or Re-labelled Battery Available at Cheaper Price in the Market</td></tr><tr><td height='15' width='90%' align='left' style='font-family:;font-size:15px;color:#FFFFFF;text-decoration:none;padding:1px 1px;'><i class='fa fa-shopping-cart' style='font-size:large; color: #E2530C;'></i>&nbsp;&nbsp;Order with us and Buy a Genuine Battery at Reasonable Cost to save money in the Long Run.</td></tr>";
	}

	//errMsg ="<table cellspacing='10' cellpadding='0' bgcolor='#FFFFFF'><table width='650' bgcolor='#FFFFFF' valign='top'><tr height='10'></tr></table><tr><td> <table width='650'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;'><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font size='5' color='#F7F7F9'>Please Enter Your Mobile Number!</font></td></tr></table></td><tr><td height='10'></td></tr></tr><tr><td width='100%' align='center'><input class='insidecontent' type='tel' autocomplete='off' name='usermobilenumber' id='usermobilenumber' placeholder='9603467559' style='width:195px;height:35px;border: 0px solid #CCC;font-size: 13px;border-radius: 0px 0px 0px 0px;' maxlength='10' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:askmobilenumber('"+batterytype+"','"+vehiclename+"','"+vehiclemodel+"','"+batterybrand+"','"+batterycapty+"','"+state+"','"+city+"','"+area+"','"+pincode+"',Submitrret);return false;} else return true;\"/></td></tr><tr><td height='5'></td></tr><tr><td width='100%' align='center' style='font-family:;font-size:12px;color:#FFFFFF;	text-decoration:none;padding:1px 1px;'><font color='#ff0000'>*</font>&nbsp;Enter&nbsp;your&nbsp;10&nbsp;digit&nbsp;mobile&nbsp;number</td></tr><tr><td  width='50%' align='center' ><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:askmobilenumber('"+batterytype+"','"+vehiclename+"','"+vehiclemodel+"','"+batterybrand+"','"+batterycapty+"','"+state+"','"+city+"','"+area+"','"+pincode+"',Submitrret);\"><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  value='Close' align='left' onclick=\"javascript:closemobiledivnavigatetobatterywalehomepage(greyout(false));\"></td></tr><tr height='16'><td colspan='3' align='center' class='subheading' id='displayrefinederrormsg1'></td></tr><tr><td height='15' align='left' style='font-family:;font-size:18px;color:#F7F7F9;text-decoration:none;padding:1px 1px;'></font><b>Why Us:</b></td></tr><tr><td height='5'></td></tr>"+sentence+"<tr><td height='10'></td></tr></table>";
	
	errMsg ="<div align='center'> <table cellspacing='10' cellpadding='0' bgcolor='#FFFFFF'><table width='650' bgcolor='#FFFFFF' valign='top'><tr height='10'><table width='650' bgcolor='#FFFFFF' valign='top'></table><table  width='650' bgcolor='#FFFFFF' valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 25px;font-weight: 600;text-align: center;'> BookBattery</td><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: left;' onclick=\"javascript:closemobilediv(greyout(false));\"><a href=\"javascript:closemobilediv(greyout(false));\" style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: left;'> X</a></td></tr></table><hr style='background: white; width: 45%;margin-top: 1px;margin-bottom: 1px;'></tr></table><tr><td> <table width='650'  cellspacing='0' cellpadding='0' style='padding-left:10px;padding-right:10px;'>  <tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font style='font-size: 18px;' color='#F7F7F9'>Please Enter Your Mobile Number!</font></td></tr></table></td><tr><td height='10'></td></tr></tr><tr><td width='100%' align='center'><input class='insidecontent' type='tel' autocomplete='off' name='usermobilenumber' id='usermobilenumber' placeholder='9603467559' style='width:195px;height:35px;border: 0px solid #CCC;font-size: 13px;border-radius: 0px 0px 0px 0px;' maxlength='10' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:askmobilenumber('"+batterytype+"','"+vehiclename+"','"+vehiclemodel+"','"+batterybrand+"','"+batterycapty+"','"+state+"','"+city+"','"+area+"','"+pincode+"',Submitrret);return false;} else return true;\"/></td></tr><tr><td height='5'></td></tr><tr><td width='100%' align='center' style='font-family:;font-size:12px;color:#FFFFFF;	text-decoration:none;padding:1px 1px;'><font color='#ff0000'>*</font>&nbsp;Enter&nbsp;your&nbsp;10&nbsp;digit&nbsp;mobile&nbsp;number</td></tr><tr><td  width='50%' align='center' ><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:askmobilenumber('"+batterytype+"','"+vehiclename+"','"+vehiclemodel+"','"+batterybrand+"','"+batterycapty+"','"+state+"','"+city+"','"+area+"','"+pincode+"',Submitrret);\"><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  value='Close' align='left' onclick=\"javascript:closemobilediv(greyout(false));\"></td></tr><tr height='16'><td colspan='3' align='center' class='subheading' id='displayrefinederrormsg1'></td></tr><tr><td height='15' align='left' style='font-family:;font-size:18px;color:#F7F7F9;text-decoration:none;padding:1px 1px;'></font><b>Why Us:</b></td></tr><tr><td height='5'></td></tr>"+sentence+"<tr><td height='10'></td></tr></table></div>";

	document.getElementById("divmobile").innerHTML=""; 
	document.getElementById("divmobile").style.display='block';
	document.getElementById("divmobile").innerHTML=errMsg
	greyout(true);	
	document.getElementById("usermobilenumber").focus();
}
function closemobiledivnavigatetobatterywalehomepage()
{
	var serverURL=document.getElementById("serverURL").value;
	$('#divmobile').hide();
	greyout(false);
	location.href="http://"+serverURL+"/bookbattery/stores.jsp";
}
function askmobilenumber(batterytype,vehiclename,vehiclemodel,batterybrand,batterycapty,state,city,area,pincode,varButton)
{
	var strUsermobileno=document.getElementById("usermobilenumber").value;
	var optkeyword=document.getElementById("keyword").value;
	if(strUsermobileno == 0 || strUsermobileno == "")
	{
		errMsg ="<font color='#FFFFFF'>Please enter Mobile Number...!</font>";
		document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
		document.getElementById("usermobilenumber").focus();
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
		 errMsg ="<font color='#FFFFFF'>Please enter only digits...!</font>";
		document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
		document.getElementById("usermobilenumber").value="";
		document.getElementById("usermobilenumber").focus();
		return ;
		
		}
	}
	if (document.getElementById("usermobilenumber").value.length<10)
	{
		errMsg ="<font color='#FFFFFF'>Please enter valid Number...!</font>";
		document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
		document.getElementById("usermobilenumber").focus();
		return;
	}
	if (document.getElementById("usermobilenumber").value.length==10)
	{
		if (strUsermobileno < 7000000000 )
		 {
			errMsg ="<font color='#FFFFFF'>Number Should start with 7 or 8 or 9...!</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.getElementById("usermobilenumber").focus();
			return ;
		 }
	}  
	if (document.getElementById("usermobilenumber").value.length>10)
	{
		if (strUsermobileno < 917000000000 || strUsermobileno >= 920000000000 )
		{
			errMsg ="<font color='#FFFFFF'>Number Should start with 91-7 or 8 or 9...!</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.getElementById("usermobilenumber").focus();
			return ;
		}
	}
	varButton.disabled=true;
	varButton.value='Please Wait...';
	document.forms[0].method="post";
	document.forms[0].action="./servlet/BatteryDetails?hidWhatToDo=getbatdetails&batterytype="+batterytype+"&vehiclename="+vehiclename+"&vehiclemodel="+vehiclemodel+"&batterybrand="+batterybrand+"&batterycapty="+batterycapty+"&state="+state+"&city="+city+"&area="+area+"&pincode="+pincode+"&backkeyword="+backkeyword+"&optkeyword="+optkeyword+"&Usermobileno="+strUsermobileno;
	//alert(document.batteryindex.action);
	document.forms[0].submit();
	
}
function getLaptopBatteryDetails()
{
	var batterytype = document.getElementById("battery_type").value;      
	var laptopname = document.getElementById("laptop_name").value;
	var laptopmodel = escape(document.getElementById("laptop_model").value);
	
	var laptopproduct = escape(document.getElementById("laptop_product").value);
	var state = document.getElementById("state").value;
	var city = document.getElementById("city").value;
	var area = document.getElementById("area").value;

	if(document.getElementById("battery_type").value == 0)
	{
		errMsg ="<font color='#ff3333'>Please Select \'Battery Type\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.getElementById("battery_type").focus();
		return ;
	}
	if(document.getElementById("laptop_name").value == 0 || laptopname=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'Laptop Make\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.getElementById("laptop_name").focus();
		return ;
	}
	if(document.getElementById("laptop_model").value == 0 || laptopmodel=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'Laptop Model\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.getElementById("laptop_model").focus();
		return ;
	}
	
	if(document.getElementById("laptop_product").value == 0 || laptopproduct=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'Laptop Product\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.getElementById("laptop_product").focus();
		return ;
	}
	if(document.getElementById("state").value == 0 || state=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'State\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.getElementById("state").focus();
		return ;
	}
	if(document.getElementById("city").value == 0 || city=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'City\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.getElementById("city").focus();
		return ;
	}
	if(document.getElementById("area").value == 0 || area=="default")
	{
		errMsg ="<font color='#ff3333'>Please Select \'Area\'.</font>";
		document.getElementById("displysesmsg").innerHTML=errMsg;
		document.getElementById("area").focus();
		return ;
	}
	//errMsg ="<table cellspacing='10' cellpadding='0' bgcolor='#FFFFFF'><table width='310' bgcolor='#FFFFFF' valign='top'><tr height='10'></tr></table> <tr><td><table width='310'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;'><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font size='5' color='#F7F7F9'>Please Enter Your Mobile Number!</font></td></tr></table></td><tr><td height='10'></td></tr></tr><tr><td width='100%' align='center'><input class='insidecontent' type='tel' autocomplete='off' name='usermobilenumber' id='usermobilenumber' placeholder='9603467559' style='width:195px;height:30px;border: 0px solid #CCC;font-size: 13px;border-radius: 0px 0px 0px 0px;' maxlength='10' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:askmobilenumberlaptop('"+batterytype+"','"+laptopname+"','"+laptopmodel+"','"+laptopproduct+"','"+state+"','"+city+"','"+area+"',Submitrret);return false;} else return true;\"/></td></tr><tr><td height='5'></td></tr><tr><td width='100%' align='center' style='font-family:;font-size:12px;color:#FFFFFF;	text-decoration:none;padding:1px 1px;'><font color='#ff0000'>*</font>&nbsp;Enter&nbsp;your&nbsp;10&nbsp;digit&nbsp;mobile&nbsp;number</td></tr><tr><td  width='50%' align='center' ><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:askmobilenumberlaptop('"+batterytype+"','"+laptopname+"','"+laptopmodel+"','"+laptopproduct+"','"+state+"','"+city+"','"+area+"',Submitrret);\"><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  value='Close' align='left' onclick=\"javascript:closemobilediv(greyout(false));\"></td></tr><tr height='16'><td colspan='3' align='center' class='subheading' id='displayrefinederrormsg1'></td></tr><tr><td height='10'></td></tr></table>";
	
	errMsg ="<div class='col-md-4 col-md-offset-4'> <table  cellspacing='10' cellpadding='0' bgcolor='#FFFFFF'><table width='350' bgcolor='#FFFFFF' valign='top'><tr height='10'><table width='100%' valign='top'></table><table  width='100%'  valign='top'><tr><td width='100%' align='center' style='COLOR:#F96F2B; font-size: 25px;font-weight: 600;text-align: center;'> BookBattery</td> <td width='100%' align='center' style='COLOR:#F96F2B; font-size: 24px;font-weight: 600;text-align: left;'  href=\"javascript:closemobilediv(greyout(false));\" > <a style='color: #F96F2B;' href=\"javascript:closemobilediv(greyout(false));\"> X </a> </td></tr></table><hr style='background: white; width: 90%;margin-top: 1px;margin-bottom: 1px;'></tr></table><tr><td><table width='100%'  cellspacing='0' cellpadding='0' ><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font style='font-size: 15px;' color='#FFFFFF'>Please Enter Your Mobile Number!</font></td></tr></table></td><tr><td height='10'></td></tr></tr><tr><td width='100%' align='center'><input class='insidecontent' type='tel' autocomplete='off' name='usermobilenumber' id='usermobilenumber' placeholder='9603467559' style='width:300px;height:40px;border: 2px solid #232323;font-size: 13px;' maxlength='10' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:askmobilenumberlaptop('"+batterytype+"','"+laptopname+"','"+laptopmodel+"','"+laptopproduct+"','"+state+"','"+city+"','"+area+"',Submitrret);return false;} else return true;\"/> </td></tr><tr><td height='5'></td></tr><tr><td width='100%' align='center' style='font-size:13px;color:#FFFFFF;	text-decoration:none;padding:1px 1px;'><font color='#ff0000'>*</font>&nbsp;Enter&nbsp;your&nbsp;10&nbsp;digit&nbsp;mobile&nbsp;number</td></tr>    <tr><td  width='50%' align='center' ><input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:askmobilenumberlaptop('"+batterytype+"','"+laptopname+"','"+laptopmodel+"','"+laptopproduct+"','"+state+"','"+city+"','"+area+"',Submitrret);\"> <input style='font-family: Helvetica Neue,Helvetica,Arial,sans-serif; background-color:#D6511C; color: white; font-size: 17px;padding: 5px 15px;font-weight: lighter; margin-left: 5px;margin-top: 5px;' type='button' class='btn'  value='Close' align='left' onclick=\"javascript:closemobilediv(greyout(false));\" ></td></tr> <td colspan='3' align='center' class='subheading' id='displayrefinederrormsg1'></td><tr><td height='15'></td></tr></table></div>";
	
	document.getElementById("divmobilelaptop").innerHTML=""; 
	document.getElementById("divmobilelaptop").style.display='block';
	document.getElementById("divmobilelaptop").innerHTML=errMsg
	greyout(true);	
	document.getElementById("usermobilenumber").focus();

}
function askmobilenumberlaptop(batterytype,laptopname,laptopmodel,laptopproduct,state,city,area,varButton)
{
	var strUsermobileno=document.getElementById("usermobilenumber").value;
	if(strUsermobileno == 0 || strUsermobileno == "")
	{
		errMsg ="<font color='#FFFFFF'>Please enter Mobile Number...!</font>";
		document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
		document.getElementById("usermobilenumber").focus();
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
			 errMsg ="<font color='#FFFFFF'>Please enter only digits...!</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.getElementById("usermobilenumber").value="";
			document.getElementById("usermobilenumber").focus();
			return ;
		
		}
	}
	if (document.getElementById("usermobilenumber").value.length<10)
	{
		errMsg ="<font color='#FFFFFF'>Please enter valid Number...!</font>";
		document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
		document.getElementById("usermobilenumber").focus();
		return;
	}
	if (document.getElementById("usermobilenumber").value.length==10)
	{
		if (strUsermobileno < 7000000000 )
		 {
			errMsg ="<font color='#FFFFFF'>Number Should start with 7 or 8 or 9...!</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.getElementById("usermobilenumber").focus();
			return ;
		 }
	}  
	if (document.getElementById("usermobilenumber").value.length>10)
	{
		if (strUsermobileno < 917000000000 || strUsermobileno >= 920000000000 )
		{
			errMsg ="<font color='#FFFFFF'>Number Should start with 91-7 or 8 or 9...!</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.getElementById("usermobilenumber").focus();
			return ;
		}
	}
	varButton.disabled=true;
	varButton.value='Please Wait...';
	
	document.forms[0].method="post";
	document.forms[0].action="./servlet/LaptopBatteryDetails?hidWhatToDo=getlaptopbatterydetails&batterytype="+batterytype+"&laptopname="+laptopname+"&laptopmodel="+laptopmodel+"&laptopproduct="+laptopproduct+"&state="+state+"&city="+city+"&area="+area+"&Usermobileno="+strUsermobileno+"&backlink=indexpage";
	//alert(document.batteryindex.action);
	document.forms[0].submit();
	
}
function closemobilediv()
{
	$('#divmobile').hide();
	$('#divmobilelaptop').hide();
	greyout(false);
}
function closemobilelaptopdiv()
{
	$('#divmobilelaptop').hide();
	greyout(false);
}
/*function onloadcity()
{
	 //window.scrollTo(0, 50); // (X,Y)
	$('#pincode2').hide();
	$('#pincode3').hide();
	$('#searcharea').hide();
}*/
function onarea()
{
	document.getElementById("displysesmsg").innerHTML="";
	$('#areadiv').show();
	$('#areassdiv').hide();
	document.getElementById("area").focus();
}
function changetopincode()
{
	//alert("calling pincode");
	document.getElementById("displysesmsg").innerHTML="";
	document.getElementById("pincode").value="";
	$('#citydiv').hide();
	$('#areadiv').hide();
	$('#statediv').hide();
	$('#pincode2').show();
	$('#pincode3').show();
	$('#searchpin').hide();
	$('#searcharea').show();
	document.getElementById("pincode").focus();

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

	url="./servlet/ScrollingTopAdsServlet?hidWhatToDo=scrollcategorytopads&catname="+whatevertopad+"&requestno="+(Math.random()*100),true;
		
	$.get(url,function(response, status, xhr) {
	if (status == "success") 
	{
		$('#topads').html(response);
	}
	$('#topads').jqFancyTransitions({effect: 'wave', delay: 2250, width: 950, height: 150, links: true ,navigation: true,direction: 'left', strips: 1 });});

}
function prepareInputsForHints() 
{	
		// repeat the same tests as above for selects
		var selects = document.getElementsByTagName("select");
		for (var k=0; k<selects.length; k++){
		if (selects[k].parentNode.getElementsByTagName("span")[0]) 
		{
			selects[k].onfocus = function () {
				this.parentNode.getElementsByTagName("span")[0].style.display = "inline";
			}
			selects[k].onblur = function () 
			{
				this.parentNode.getElementsByTagName("span")[0].style.display = "none";
			}
		}
	}
}
$(document).ready(function()
{
	$area= $('#area');
    $area.change
	(
  		function() 
		{
			
				//alert("s1");
			var splitvalb =$area.val();
			//alert(splitvalb);
			if(splitvalb=="default")
			{
				//alert("1");
				$('#area').addClass('highlightbefore');
				$('#area').removeClass('highlightafter');

			}
			else
			{
				//alert("2");
				$('#area').removeClass('highlightbefore');
				$('#area').addClass('highlightafter');
			}
				
			document.getElementById("displysesmsg").innerHTML="";
		}
	);
});

$( "#findbattery" ).click(function() {
	var battery_type= document.getElementById("battery_type").value;

	if (battery_type == "Laptop Batteries") 
	{
		getLaptopBatteryDetails()
	}
		else
	{
		funToGetbatterydetails()
	}
});

$( "#orderpincode" ).click(function() {

	changetopincode()
	
});

$( "#orderarea" ).click(function() {

	changetoarea()
	
});

function checkbatteryservice() 
{
		var bookbatteryservice= document.getElementById("bookbatteryservice").value;
		if(bookbatteryservice =="Yes")
	{

		window.location.href = './batteryservice.jsp';
	}
	else
	{
		//$('#boxes').hide();
		//$(".group5").colorbox({rel:'group5', slideshow:true, open:true});
       window.location.href = './batteryservice.jsp';
	}

}


