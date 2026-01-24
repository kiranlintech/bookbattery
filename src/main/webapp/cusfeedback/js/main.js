
(function ($) {
    "use strict";


    /*==================================================================
    [ Validate ]*/
    var input = $('.validate-input .input100');

	$('.contact100-form-btn').click(function(){
		var check = true;
		var rating= document.getElementsByName('quality');
		var star_rated="";
		
		for(var i=0;i<rating.length;i++)
		{
		if(rating[i].checked){

		star_rated = rating[i].value;
		}
		//alert(star_rated);
		}
		
		if(star_rated=="")
		{
			//alert(15);
			var errorMSG ="<font color='#c80000'><b>Please provide Your Rating!</b></font>";
			$('#rating_div').css("border-color", "#c80000");
			document.getElementById("ratingmsg").innerHTML=errorMSG;
			return;
			check=false;
		}
		$('#ratingmsg').hide();
		$('#rating_div').css("border-color", "#e6e6e6");
		for(var i=0; i<input.length; i++) {
		if(validate(input[i]) == false){
		showValidate(input[i]);
		check=false;
		}
		else
		{
		//check=true;
		}
		}



		
		if(check)
		{
			var vehicle_make="0";
			var vehicle_model="0"
			var volts_before_car_started="0"
			var volts_car_cranking="0"
			var volts_after_car_started="0"
			var volts_car_acceleration="0"
			var volts_power_off="0"
			var volts_power_on="0"
			var water_gravity="0"
			var capacity="0"
			var quantity="1"
			
			var order_number=$('[name=order_number]').val();
			var consumer_name=$('[name=consumer_name]').val();
			var mobile_number=$('[name=mobile_number]').val();
			var battery_type=$('[name=battery_type]').val();
			
			if(battery_type=="Car Batteries")
			{
				 vehicle_make=$('[name=vehicle_make]').val();
				 vehicle_model=$('[name=vehicle_model]').val();
				 volts_before_car_started=$('[name=volts_before_car_started]').val();
				 volts_car_cranking=$('[name=volts_car_cranking]').val();
				 volts_after_car_started=$('[name=volts_after_car_started]').val();
				 volts_car_acceleration=$('[name=volts_car_acceleration]').val();
				 
				 vehicle_make = vehicle_make.replace(/ /g, "+");
				 vehicle_model = vehicle_model.replace(/ /g, "+");
				 volts_before_car_started = volts_before_car_started.replace(/ /g, "+");
				 volts_car_cranking = volts_car_cranking.replace(/ /g, "+");
				 volts_after_car_started = volts_after_car_started.replace(/ /g, "+");
				 volts_car_acceleration = volts_car_acceleration.replace(/ /g, "+");
				 
				 vehicle_make=escape(vehicle_make);
				 vehicle_model=escape(vehicle_model);
			}
			else
			{
				 capacity=$('[name=capacity]').val();
				 quantity=$('[name=quantity]').val();
				 volts_power_off=$('[name=volts_power_off]').val();
				 volts_power_on=$('[name=volts_power_on]').val();
				 water_gravity=$('[name=water_gravity]').val();
				 capacity = capacity.replace(/ /g, "+");
				 quantity = vehicle_model.replace(/ /g, "+");
				 volts_power_off = volts_power_off.replace(/ /g, "+");
				 volts_power_on = volts_power_on.replace(/ /g, "+");
				 water_gravity = water_gravity.replace(/ /g, "+");
				 
				 capacity=escape(capacity);
				 quantity=escape(quantity);
				 volts_power_off=escape(volts_power_off);
				 volts_power_on=escape(volts_power_on);
				 water_gravity=escape(water_gravity);
			}
			
			var terminals_src_spc=$('[name=terminals_src_spc]').val();
			var battery_cleanup_cloth=$('[name=battery_cleanup_cloth]').val();
			var battery_good=$('[name=battery_good]').val();
			var battery_not_charge=$('[name=battery_not_charge]').val();
			var battery_dead=$('[name=battery_dead]').val();
			var battery_overcharge=$('[name=battery_overcharge]').val();
			var battery_needs_charging=$('[name=battery_needs_charging]').val();
			var warranty_expiry_date=$('[name=warranty_expiry_date]').val();
			var message=$('[name=message]').val();
			var service_eng_mobile=$('[name=service_eng_mobile]').val();
			
			consumer_name = consumer_name.replace(/ /g, "+");
			battery_type = battery_type.replace(/ /g, "+");
			message = message.replace(/ /g, "+");

			consumer_name=escape(consumer_name);
			battery_type=escape(battery_type);
			message=escape(message);
			
			//alert(star_rated);
			
			$.ajax
			({					 
				type: "GET",
				url: "/bookbattery/feedback?hidWhatToDo=insertcustomerfeedback",
				data: {order_number: order_number,
					   consumer_name: consumer_name,
					   mobile_number: mobile_number,
					   battery_type: battery_type,
					   vehicle_make: vehicle_make,
					   vehicle_model: vehicle_model,
					   capacity: capacity,
					   quantity: quantity,
					   terminals_src_spc: terminals_src_spc,
					   battery_cleanup_cloth: battery_cleanup_cloth,
					   volts_before_car_started: volts_before_car_started,
					   volts_after_car_started: volts_after_car_started,
					   volts_car_cranking: volts_car_cranking,
					   volts_car_acceleration: volts_car_acceleration,
					   battery_good: battery_good,
					   battery_not_charge: battery_not_charge,
					   battery_dead: battery_dead,
					   battery_overcharge: battery_overcharge,
					   battery_needs_charging: battery_needs_charging,
					   warranty_expiry_date: warranty_expiry_date,
					   volts_power_off: volts_power_off,
					   volts_power_on: volts_power_on,
					   water_gravity: water_gravity,
					   star_rated: star_rated,
					   service_eng_mobile: service_eng_mobile,
				message: message},
				success: function(res)
				{	
					//alert(res);
					$("#feedback_popup").show();
					
					if(res.indexOf("Successfully Added")>=0)
					{
						 $('.modal-header').css("background-color", "#69b044");
						 $('#Ok_button').css("background-color", "#69b044");
						 $('#Ok_button').removeClass("btn-error");
						 $('#Ok_button').addClass("btn-success");
						 $('#body-text').html("Successfully Added Feedback. Thanks for your support");
					}
					else if(res.indexOf("Your FeedBack Already Submited")>=0)
					{
						$('.modal-header').css("background-color", "#e02d29c2");
						 $('#Ok_button').css("background-color", "#e02d29c2");
						 $('#Ok_button').removeClass("btn-success");
						 $('#Ok_button').addClass("btn-error");
						 $('#body-text').html("Your Feedback Already Submited. Thanks for your support");
					}
					else
					{
						$('.modal-header').css("background-color", "#dc3535de");
						 $('#Ok_button').css("background-color", "#dc3535de");
						 $('#Ok_button').removeClass("btn-success");
						 $('#Ok_button').addClass("btn-error");
						 $('#body-text').html("Failed to insert feedback. Please TryAgain");
						
					}
					

					greyout(true);
				}	
			});
		}
		else
		{
		return check;
		}
	});
	
    $('.validate-form .input100').each(function(){
        $(this).focus(function(){
           hideValidate(this);
        });
    });

	$('.closepopup').click(function(){
	
		//window.location.href = "http://192.168.1.95/bookbattery/";
		window.location.href = "http://www.bookbattery.com/bookbattery/";
		
	});
    function validate (input) {
		
		//alert($(input).attr('type'));
		
        if($(input).attr('type') == 'email' || $(input).attr('name') == 'email') {
            if($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
                return false;
            }
        }       
		else if($(input).attr('name') == 'fb1') {
			//alert("in");
			//alert($(input).val());
        }
        else {
			if($(input).attr('name') == 'message')
			{
				if($(input).val().trim() != '')
				{
					//alert("in");
					//Should add vaildation similar to description.
					if ($(input).val().length <= 10)
					{
						return false;
					}
				}
            }
        }
    }

    function showValidate(input) {
        var thisAlert = $(input).parent();
        $(thisAlert).addClass('alert-validate');
        $(thisAlert).css("border-color", "#c80000");
    }

    function hideValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).removeClass('alert-validate');
		$(thisAlert).css("border-color", "#e6e6e6");
    }
    
    

})(jQuery);