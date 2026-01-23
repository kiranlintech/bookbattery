$(function () {
    $('#sign_in').validate({
        highlight: function (input) {
            console.log(input);
            $(input).parents('.form-line').addClass('error');
        },
        unhighlight: function (input) {
            $(input).parents('.form-line').removeClass('error');
        },
        errorPlacement: function (error, element) {
            $(element).parents('.input-group').append(error);
        }
    });
});

function SignIn(){
	
	var input_type;
	var username=document.storelogin.username.value;
	if (nameValidation("","username","name") == false)
		{
			return;
			
		} 
	var password=document.storelogin.password.value;	
	if (yesValidation("","password","yes") == false)
		{
			return;
			
		} 
	document.storelogin.method="post";
	document.storelogin.action="/bookbattery/servlet/BatteryStoreLogin?hidWhatToDo=checkstorelogin";
 	document.storelogin.submit();
}