
 $('#myCarousel').carousel({
    interval: 15000
}); 


$("#other_batterywale").click(function(){
  
  $("#product_type").focus();
	$('html, body').animate({
              scrollTop: $("#battery_finder_div").offset().top
        }, 300); 
});

   $('.view_review').on('click', function(){
	   
	   if(this.id=="view_google_review")
	   {
		   $('#google_review_div').removeClass("hide");
		   $('#google_review_div').show();
		   $('#direct_review_div').hide();
		   $('#direct_review_div').addClass("hide");
		   
	   }
	   else
	   {
		  $('#google_review_div').addClass("hide");
		  $('#google_review_div').hide();
		  $('#direct_review_div').removeClass("hide");
		  $('#direct_review_div').show();
	   }
	   
   });
   

(function(d, s, id) {
	//alert(d);
	//alert(s);
	//alert(id);
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = 'https://connect.facebook.net/en_GB/sdk.js#xfbml=1&version=v3.0';
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));