//### Name 		 : valadation.js
//### Author	 : Sai Krishna Daddala
//### Description: This contains auto validations and few more functions.
// ########### JQuery Code Valadation #######// Added By Sai Krishna
function everyThingOk(id)
{
	$('#'+id+'').removeClass('error success warning info');
	$('#'+id+'_error').hide();
	$('#'+id+'-error').hide();
	//$('#'+id+'').focus();
	return true;
}
function everyThingNotOk(id,error_key)
{
	$('#'+id+'_error').show();
	$('#'+id+'-error').show();
	errMsg ="<span class='error'>"+error_key+"</span>";
	$('#'+id+'-error').html(errMsg);
	$('#'+id+'_error').html(errMsg);
	$('#'+id+'').addClass('error');
	$('#'+id+'').removeClass('warning success info');
	$('#'+id+'').focus();
	return false;
}
function onlyNumberKey(evt,id,input_type)
{	
var text_tmp_val = $("#"+id+"").val();
		
	var charCode = (evt.which) ? evt.which : evt.keyCode
	if (charCode > 31 && (charCode < 48 || charCode > 57 || charCode == 46))
	{
		if (charCode == 46 && input_type=="decimal")
		{
			if (($("#"+id+"").val()).indexOf(".") != -1)
			{
				 return false;
			}
			else
			{
				everyThingOk(id);
				return true;
			}
		}
		else
		{
			everyThingNotOk(id,"Please Enter only <b>Numerics.</b>");
			return false ;
		}
	}
	else if(input_type=="decimal")
	{	
		var number = text_tmp_val.split('.');
		if (number.length == 2 && number[1].length == 2)
		{
			everyThingNotOk(id,"Only <b>Two Digits are Allowed after (.)DOT</b>");
			return false;
		}
		else
		{
			everyThingOk(id);
			return true;
		}
	}
	else	
	{
		everyThingOk(id);
		
		return true;
	}	
}

function nameValidation(evt,id,input_type)
{
// Initializing Variables With Regular Expressions	
var name_regex = /^[a-zA-Z\.\ ]*$/;
var text_tmp_val = $("#"+id+"").val();
	if (text_tmp_val.length == 0 && input_type=="name" && $("#"+id+"").hasClass("yes"))
	{
		everyThingNotOk(id,"Please<b> Fill</b> this.");
		return false ;
	}
	else if ((!text_tmp_val.match(name_regex)) && input_type=="name" && $("#"+id+"").hasClass("yes"))
	{
		everyThingNotOk(id,"Please Use only<b> Alphabets.</b>");
		return false ;
	}
	else if ((!text_tmp_val.match(name_regex) && text_tmp_val.length > 0) && input_type=="name" && !jQuery("#"+id+"").hasClass("yes"))
	{
		everyThingNotOk(id,"Please Use only<b> Alphabets.</b>");
		return false ;
	}
	else if (text_tmp_val.length > 60 && input_type=="name" && $("#"+id+"").hasClass("yes"))
	{
		everyThingNotOk(id,"Please reduce your<b> Name Length.</b>");
		return false ;
	}
	else
	{	
		everyThingOk(id);
		return true;

	}	
}


function numberValidation(evt,id,input_type)
{
// Initializing Variables With Regular Expressions	
var number_regex = /^\d+$/;
var text_tmp_val = $("#"+id+"").val();
	if (text_tmp_val.length == 0 && $("#"+id+"").hasClass("yes"))
	{
		everyThingNotOk(id,"Please<b> Fill</b> this.");
		return false ;
	}
	else if ((!text_tmp_val.match(number_regex)) && $("#"+id+"").hasClass("yes"))
	{
		everyThingNotOk(id,"Please Use only<b> Numerics.</b>");
		return false ;
	}
	else if ((!text_tmp_val.match(number_regex) && text_tmp_val.length > 0) && !jQuery("#"+id+"").hasClass("yes"))
	{
		everyThingNotOk(id,"Please Use only<b> Numerics.</b>");
		return false ;
	}
	else if ((!text_tmp_val.match(number_regex) && text_tmp_val.length > 0))
	{
		everyThingNotOk(id,"Please Use only<b> Numerics.</b>");
		return false ;
	}
	else
	{	
		everyThingOk(id);
		return true;

	}		
}

function mobilenumberValidation(evt,id,input_type)
{
// Initializing Variables With Regular Expressions	
var mobilenumber_regex = /^[6789]\d{9}$/;
var text_tmp_val = $("#"+id+"").val();
	if ((!text_tmp_val.match(mobilenumber_regex) || text_tmp_val.length == 0) && input_type=="mobilenumber" && $("#"+id+"").hasClass("yes"))
	{
		everyThingNotOk(id,"Invalid <b>Mobile Number.</b>");
		return false ;
	}
	else if ((!text_tmp_val.match(mobilenumber_regex) && text_tmp_val.length > 0) && input_type=="mobilenumber" && !jQuery("#"+id+"").hasClass("yes"))
	{
		everyThingNotOk(id,"Invalid <b>Mobile Number.</b>");
		return false ;
	}
	else
	{	
		everyThingOk(id);
		return true;

	}	
}
function pincodeValidation(evt,id,input_type)
{
// Initializing Variables With Regular Expressions	
var pincode_regex = /^[1-9][0-9]{5}$/;
var text_tmp_val = $("#"+id+"").val();
	if ((!text_tmp_val.match(pincode_regex) || text_tmp_val.length == 0) && input_type=="pincode" && $("#"+id+"").hasClass("yes"))
	{
		everyThingNotOk(id,"Invalid <b>Pincode.</b>");
		return false ;
	}
	else if ((!text_tmp_val.match(pincode_regex) && text_tmp_val.length > 0) && input_type=="pincode" && !jQuery("#"+id+"").hasClass("yes"))
	{
		everyThingNotOk(id,"Invalid <b>Pincode.</b>");
		return false ;
	}
	else
	{	
		everyThingOk(id);
		return true;

	}	
}

function emailValidation(evt,id,input_type)
{
// Initializing Variables With Regular Expressions	
var email_regex = /^[\w\-\.\+]+\@[a-zA-Z0-9\.\-]+\.[a-zA-z0-9]{2,4}$/;
var add_regex = /^[0-9a-zA-Z]+$/;
var zip_regex = /^[0-9]+$/;	
var text_tmp_val = $("#"+id+"").val();
	if ((!text_tmp_val.match(email_regex) || text_tmp_val.length == 0) && input_type=="email" && $("#"+id+"").hasClass("yes"))
	{
		everyThingNotOk(id,"Please enter a <b>Valid Email Address.</b>");
		return false ;
	}
	else if ((!text_tmp_val.match(email_regex) && text_tmp_val.length > 0) && input_type=="email" && !jQuery("#"+id+"").hasClass("yes"))
	{
		everyThingNotOk(id,"Please enter a <b>Valid Email Address.</b>");
		return false ;
	}
	else if ((text_tmp_val.length > 30) && input_type=="email" && !jQuery("#"+id+"").hasClass("yes"))
	{
		everyThingNotOk(id,"Please enter a <b>Valid Email Address.</b>");
		return false ;
	}
	else
	{	
		everyThingOk(id);
		return true;

	}	
}

function descriptionValidation(evt,id,input_type)
{
// Initializing Variables With Regular Expressions	
var email_regex = /^[\w\-\.\+]+\@[a-zA-Z0-9\.\-]+\.[a-zA-z0-9]{2,4}$/;
var add_regex = /^[0-9a-zA-Z]+$/;
var zip_regex = /^[0-9]+$/;	
var text_tmp_val = $("#"+id+"").val();
	if ((text_tmp_val.length <= 10 || text_tmp_val.length == 0) && input_type=="description" && $("#"+id+"").hasClass("yes"))
	{
		everyThingNotOk(id,"Please enter a <b>Valid Description.</b>");
		return false ;
	}
	else if ((text_tmp_val.length <= 10) && input_type=="description" && !jQuery("#"+id+"").hasClass("yes"))
	{
		everyThingNotOk(id,"Please enter a <b>Valid Description.</b>");
		return false ;
	}
	else
	{	
		everyThingOk(id);
		return true;

	}	
}

function addressValidation(evt,id,input_type)
{
// Initializing Variables With Regular Expressions	
var text_tmp_val = $("#"+id+"").val();
	if ((text_tmp_val.length <= 15 || text_tmp_val.length == 0) && input_type=="address" && $("#"+id+"").hasClass("yes"))
	{
		everyThingNotOk(id,"Please enter a <b>Full Address.</b>");
		return false ;
	}
	else if ((text_tmp_val.length <= 9) && input_type=="address" && !jQuery("#"+id+"").hasClass("yes"))
	{
		everyThingNotOk(id,"Please enter a <b>Full Address.</b>");
		return false ;
	}
	else if ((text_tmp_val.length > 300) && input_type=="address" && !jQuery("#"+id+"").hasClass("yes"))
	{
		everyThingNotOk(id,"Please reduce your <b>Address Length.</b>");
		return false ;
	}
	else
	{	
		everyThingOk(id);
		return true;
	}	
}

function yesValidation(evt,id,input_type)
{
	var text_tmp_val = $("#"+id+"").val();
	if ((text_tmp_val.length == 0) && $("#"+id+"").hasClass("yes"))
	{
		everyThingNotOk(id,"Please enter a <b> Value.</b>");
		return false ;
	}
	else if ((text_tmp_val.length == 0) && !jQuery("#"+id+"").hasClass("yes"))
	{
		everyThingOk(id);
		return true;
	}
	else
	{	
		everyThingOk(id);
		return true;

	}	
}
function selectValidation(evt,id,input_type)
{
	var text_tmp_val = $("#"+id+"").val();
	if ((text_tmp_val == 0 || text_tmp_val == "" || text_tmp_val == "default"  || text_tmp_val == "null"  || text_tmp_val == null  || text_tmp_val == "0" || text_tmp_val == "defaults" ) && $("#"+id+"").hasClass("yes"))
	{
		everyThingNotOk(id,"Please <b> Select</b> some option.");
		return false ;
	}
	else
	{	
		everyThingOk(id);
		return true;

	}	
}

$( document ).ready(function() {
	$('textarea, input[type="text"],[type="email"],[type="search"]').on('change', function(event){
		event.preventDefault();
		var id =$(this).attr('id');
		var input_type;
		if ( $(this).hasClass("name"))
		{
			input_type="name";
			return nameValidation(event,id,input_type)
		}
		if ( $(this).hasClass("email"))
		{
			input_type="email";
			return emailValidation(event,id,input_type)
		}
		if ( $(this).hasClass("number"))
		{
			input_type="number";
			return onlyNumberKey(event,id,input_type)
		}
		if ( $(this).hasClass("description"))
		{
			input_type="description";
			return descriptionValidation(event,id,input_type)
		}
		if ( $(this).hasClass("address"))
		{
			input_type="address";
			return addressValidation(event,id,input_type)
		}
		if ( $(this).hasClass("yes"))
		{
			input_type="yes";
			return yesValidation(event,id,input_type)
		}
		else{
			input_type="";
		}		
	});	
});

$( document ).ready(function() {
	$('input[type="tel"]').change(function(event){
		event.preventDefault();
		var id =$(this).attr('id');
		var input_type;
		if ( $(this).hasClass("decimal"))
		{
			//input_type="decimal";
			//return onlyNumberKey(event,id,input_type
			return
		}
		else if ( $(this).hasClass("mobilenumber"))
		{
			input_type="mobilenumber";
			return mobilenumberValidation(event,id,input_type)
		}
		else if ( $(this).hasClass("pincode"))
		{
			input_type="pincode";
			return pincodeValidation(event,id,input_type)
		}
		else if ( $(this).hasClass("yes"))
		{
			input_type="yes";
			return yesValidation(event,id,input_type)
		}
		else{
			input_type="";
			return onlyNumberKey(event,id,input_type)
		}	
		
	});	
});

$( document ).ready(function() {
	$('input[type="tel"]').keypress(function(event){
		event = event || window.event
		var id =$(this).attr('id');
		var input_type;
		if ( $(this).hasClass("decimal"))
		{
			input_type="decimal";
		}
		else{
			input_type="";
		}	
		return onlyNumberKey(event,id,input_type)
	});
});

$(window).on('shown.bs.modal', function() { 
	$('input[type="tel"]').keypress(function(event){
		event = event || window.event
		var id =$(this).attr('id');
		var input_type;
		if ( $(this).hasClass("decimal"))
		{
			input_type="decimal";
		}
		else{
			input_type="";
		}	
		return onlyNumberKey(event,id,input_type)
	});
});



$( document ).ready(function() {
	$('select').on('change', function(event){
		event.preventDefault();
		var id =$(this).attr('id');
		var input_type;
		input_type="select";
		return selectValidation(event,id,input_type)
				
	});	
});

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length,c.length);
        }
    }
    return "";
}

function setCookie(cname,cvalue,ctime) {
	//console.log(ctime);
	var now = new Date();
	var time = now.getTime();
	if(ctime==undefined || ctime=="" || ctime==null || ctime=="0")
	{
		time += 24*60*60*1000;
	}
	else
	{
		time += ctime;
	}
	now.setTime(time);
	var expires = "expires="+ now.toUTCString();
	document.cookie = cname + "=" + cvalue + "; " + expires +"; path=/;";
	
	return true;
}


$( document ).ready(function() {
	var inputs = $('input, textarea, select').not(':input[type=button], :input[type=radio], :input[type=check], :input[type=submit], :input[type=reset]');
	$(inputs).focus(function(){
		if ( $(this).hasClass("error") ) 
		{
			var center = $(window).height()/2;
			var center_half = $(window).height()/4;
			var top = $(this).offset().top ;
			if (top > center){
				if (screen.width <= 767)
				{
					 $('body').animate({
						 scrollTop:top-center_half
					},'slow');
				}
				else
				{
					// $('body').animate({
						// scrollTop:top-center
					// },'slow');
				}
			}
		}
		else
		{
			var MaxScroll = $(window).height()*0.8;
			var center = $(window).height()/2;
			var top = $(this).offset().top ;
			if (top > MaxScroll){
				if (screen.width <= 767)
				{
					$('body').animate({
						scrollTop:top-center
					},'slow');
				}
				else
				{
				}
			}
		}
	});	
});

function Activate_Model_Scroll()
{
	var inputs = $('.modal input,.modal textarea,.modal select,.modal .select2-choice').not(':input[type=button], :input[type=radio], :input[type=checkbox], :input[type=submit], :input[type=reset]');
		$(inputs).on("click focus",function(e) {
		 e.stopPropagation();
		//var MaxScroll = $('.modal').offset().top + (screen.height/4);
		var center_half =screen.height*0.2;
		var center = screen.height/4;
		var Modeltop = $('.modal').offset().top;
		var top = $(this).offset().top;
		var MaxScroll=center_half+Modeltop;
		//console.log(top);
		//console.log(Modeltop);

		if (top > MaxScroll){
			console.log('scroll neaded');
			if (screen.width <= 767)
			{
				$(".modal").animate({
					 scrollTop: $(this).position().top+90,
				},'slow');
			}
			else
			{
			}
		}
		else
		{
			console.log('scroll not neaded');
		}
	});
}

$( document ).ready(function() {
	$(window).on('shown.bs.modal', function() { 
		Activate_Model_Scroll();
	});
});


 