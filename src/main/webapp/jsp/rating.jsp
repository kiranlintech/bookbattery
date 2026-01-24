
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.*,com.ngit.javabean.loglevel.LogLevel,javax.servlet.ServletContext,java.util.Properties,java.io.FileInputStream"%>

<%
    String ordernumber = request.getParameter("ordernumber");
	//out.println(ordernumber);
%>



<!DOCTYPE html>
<html>
	<head>
		<title>BookBattery Customer Review</title>
		
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">

		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
		<link rel="stylesheet" href="../css/demo.css">
		<link rel="stylesheet" href="../css/font-awesome.css">
		<link rel="stylesheet" href="../css/sky-forms.css">
		<link rel="stylesheet" href="../css/sky-forms-orange.css">
		<!--[if lt IE 9]>
			<link rel="stylesheet" href="css/sky-forms-ie8.css">
		<![endif]-->
		 
		 
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
		
		<!--[if lt IE 10]>
			<script src="js/jquery.placeholder.min.js"></script>
		<![endif]-->		
		<!--[if lt IE 9]>
			<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
			<script src="js/sky-forms-ie8.js"></script>
		<![endif]-->
	</head>
	
<style type="text/css">
.rcorners1 {

position: fixed;

z-index: 1;

display: none;

top: 0;

left: 0;

display: none;

width: 100%;

height: 100%;

background: rgba(0,0,0,0.7);

}


</style>


	<body class="bg-orange">
	<div id="rcorners1" class="rcorners1" style="display:none;position:fixed;"></div>
		<div class="body body-s">		
			<form id="ratingform" class="sky-form">
				<header><h3 style="color: #fd7337;">BookBattery Customer Review</h3></header>			
				<fieldset>					
					<section>
						<p><h4>Please Provide Your Valuable Review</h4></p></br>
						<div class="row rating" style="float:left">													
							<input type="radio" name="quality" value="5" id="quality-5" >
							<label for="quality-5"><i class="fa fa-star fa-6" ></i></label>
							<input type="radio" name="quality" value="4" id="quality-4" >
							<label for="quality-4"><i class="fa fa-star fa-6"></i></label>
							<input type="radio" name="quality" value="3" id="quality-3" >
							<label for="quality-3"><i class="fa fa-star fa-6"></i></label>
							<input type="radio" name="quality" value="2" id="quality-2" >
							<label for="quality-2"><i class="fa fa-star fa-6"></i></label>
							<input type="radio" name="quality" value="1" id="quality-1" >
							<label for="quality-1"><i class="fa fa-star fa-6"></i></label>
						</div>	
					</section>
					<br><br><div class="row" id="ratingmsg"></div>
					
					<section>
						<label class="label"></label>
						<label class="textarea">
							<i class="icon-append fa fa-comment"></i>
							<textarea rows="10" name="review" id="review" placeholder="Text of the review"></textarea>
							
						</label>
						<div id="reviewmsg"></div>
					</section>
					
					
					
				</fieldset>
				<footer>
					<button type="button" class="button pull-center" onclick="insertrating();">Submit a review</button>
				</footer>
			</form>			
		</div>

  <!-- Modal -->
 
  
	

	<input type="hidden" name="ordernumber" id='ordernumber' value="<%=ordernumber%>">	


		
		
		
		
		<script type="text/javascript">
			function validate_form()
			{
				
				//alert(22);
				// Validation
				$("#ratingform").validate(
				{					
					// Rules for form validation
					rules:
					{						
						quality:
						{
							required: true
						},
						review:
						{
							required: true,
							minlength: 20
						}
					},
										
					// Messages for form validation
					messages:
					{
						quality:
						{
							required: 'Please rate quality of the product'
						},
						review:
						{
							required: 'Please enter your review'
						}
					},
					// Do not change code below
					errorPlacement: function(error, element)
					{
						error.insertAfter(element.parent());
					},					
					
					success: function(){         
					//alert(22);
					insertrating();						
					}
					
				}

				);
				 
				 
				//insertrating();				
			}

			
		$('.modal-closer').click(function(){
		 window.location = "rating.jsp";	
		});				
						
function insertrating()
{
//alert(23);
	var rating_content = document.getElementById("review").value;	
	var ordernumber = document.getElementById("ordernumber").value;	
	//alert(ordernumber);
	var rating= document.getElementsByName('quality');
	//alert(rating);
	var star_rated="";
	for(i=0;i<rating.length;i++)
	{
		  if(rating[i].checked){
			
			star_rated = rating[i].value;
		}
		//alert(star_rated);
	}
	if(star_rated==""){
		
		errorMSG ="<font color='red'><b>Please Select atleast one Star</b></font>";
		document.getElementById("ratingmsg").innerHTML=errorMSG;
		document.getElementsByName("quality").focus();
		return;
	}
	$('#ratingmsg').hide();
	

	if(rating_content==""){
		
		errorMSG ="<font color='red'><b>Please Provide Your Valuable Review</b></font>";
		document.getElementById("reviewmsg").innerHTML=errorMSG;
		document.getElementById("review").focus();
		return;
	} 
	$('#reviewmsg').hide();
		
		
		//alert(ordernumber);
		
		$.ajax
		({
			type:'POST',
			url: "/bookbattery/servlet/CustomerRatings?hidWhatToDo=insertratings",
			data: {rating_content:rating_content,rating:star_rated,ordernumber:ordernumber},
			success: function(data)
			{					
				//alert(data);
				if(data.indexOf("Successfully inserted")>=0)
				{				   
				   //$("#myModal").modal();
				   //$("#myModal").moal('show');
				   //alert("Successfully updated");
				   $('#rcorners1').show();
				   document.getElementById("rcorners1").innerHTML='<div id ="success" class="sky-form" style="max-width: 400px;margin: 90px auto;"><header Style="background-color:green;color:#fff;"><h3>Success</h3><img src="../assets/includes/images/Mobclose.png" alt="" style="float:right;width: 10%;margin-top: -18%;margin-right: -14%;cursor: pointer;" onclick="Close()" /></header><fieldset Style="background-color:#fff;padding-bottom:25px"><section><label class="label"><h4>Thanks for your valuable Review</h4></label></section></fieldset><footer style="padding: 1px 30px 12px;"><button type="button" name="submit" class="button" style="background-color: #ff5106;color:#fff" onclick="Close()" >Close</button></footer></div>';
				  		   
				}
				else
				{
					 //$("#myModal").modal('show');
					//alert("Already exists");
				}
			}
		});	
}
function Close(){
	
	window.location.href="https://www.BookBattery.com/bookbattery/";
}



</script>
</body>
</html>