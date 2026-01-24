<%-- 
Document   		 : online_transaction.jsp
Created on 		 : Feb 15, 2017, 10:14:12 AM
Author     		 : BookBattery
Copyright Notice : Asistmi Solutions Pvt.Ltd. Confidential.
Document         : This jsp is used as show details or orders.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.text.*,java.util.Calendar,java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Date,java.util.Properties,java.util.*,com.ngit.javabean.loglevel.LogLevel,javax.servlet.ServletContext,java.util.Properties,java.io.FileInputStream, com.ngit.javabean.qrymgr.QueryManager"%>
<%response.setHeader("Pragma","no-cache"); response.setHeader("Cache-Control","no-store"); response.setHeader("Expires","0"); response.setDateHeader("Expires",-1); 
ServletContext context = getServletContext();
Properties propsMOPConfig = new Properties();
FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
propsMOPConfig.load(fin1); 
fin1.close(); 

QueryManager qm;
qm = QueryManager.getInstance(propsMOPConfig) ;
	
String publicUrl = (propsMOPConfig.getProperty("publicUrl")!=null)?propsMOPConfig.getProperty("publicUrl"):"";
String baseURL = (propsMOPConfig.getProperty("baseURL")!=null)?propsMOPConfig.getProperty("baseURL"):"";
String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?propsMOPConfig.getProperty("baseurldirectory"):"";
String serverURL = (propsMOPConfig.getProperty("serverURL")!=null)?propsMOPConfig.getProperty("serverURL"):"";


String order_number = (String) request.getAttribute("order_number");
String consumer_name = (String) request.getAttribute("consumer_name");
String mobile_number = (String) request.getAttribute("mobile_number");
String product_type = (String) request.getAttribute("product_type");
String product_brand = (String) request.getAttribute("product_brand");
String product_model = (String) request.getAttribute("product_model");
String veh_name = (String) request.getAttribute("veh_name");
String veh_model = (String) request.getAttribute("veh_model");
String exisiting_inverter_make = (String) request.getAttribute("exisiting_inverter_make");
String exisiting_inverter_model = (String) request.getAttribute("exisiting_inverter_model");
String exisiting_inverter_working = (String) request.getAttribute("exisiting_inverter_working");
String capacity = (String) request.getAttribute("capacity");
String quantity = (String) request.getAttribute("quantity");
String terminals_src_spc = (String) request.getAttribute("terminals_src_spc");
String battery_cleanup_cloth = (String) request.getAttribute("battery_cleanup_cloth");
String volts_before_car_started = (String) request.getAttribute("volts_before_car_started");
String volts_after_car_started = (String) request.getAttribute("volts_after_car_started");
String volts_car_acceleration = (String) request.getAttribute("volts_car_acceleration");
String volts_car_cranking = (String) request.getAttribute("volts_car_cranking");
String battery_not_charge = (String) request.getAttribute("battery_not_charge");
String invoice_bill = (String) request.getAttribute("invoice_bill");
String warranty_card_seal = (String) request.getAttribute("warranty_card_seal");
String customer_satisfied = (String) request.getAttribute("customer_satisfied");
String warranty_expiry_date = (String) request.getAttribute("warranty_expiry_date");
String service_eng_mobile = (String) request.getAttribute("service_eng_mobile");

LogLevel.DEBUG(5, new Throwable(), "warranty_expiry_date :" + warranty_expiry_date);

consumer_name=consumer_name.replace("_"," ");
product_type=product_type.replace("_"," ");
veh_name=veh_name.replace("_"," ");
veh_model=veh_model.replace("_"," ");
capacity=capacity.replace("_"," ");

String vehname_model=veh_name+" "+veh_model;
String product_brand_model=product_brand+" "+product_model;
String exisiting_inverter_make_model=exisiting_inverter_make+" "+exisiting_inverter_model;

%>

<!-- ###############################------------------------------------------------#############################-->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en">
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content=" Oops! No Product Details Found ">
	<meta name="author" content="Bharath Kumar">
	<meta name="robots" content="noindex">
	<meta name="googlebot" content="noindex">
	<title>Payment Tranasaction </title>


<!-- Mobile Specific -->
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<!-- Google Fonts -->
<link href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,300,700,800,400,600' rel='stylesheet' type='text/css'>
	<link rel="stylesheet" href="/bookbattery/css/sky-forms.css">
	<link rel="stylesheet" href="/bookbattery/css/sky-forms-orange.css">
<!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_css.jsp" />
<!---################################## CSS Include Ends  ################################------>
<script type="text/javascript">


</script>

</script>
<style type="text/css">
 
#share-buttons img {
width: 35px;
padding: 5px;
border: 0;
box-shadow: 0;
display: inline;
}
 #st-1.st-has-labels .st-btn {
    min-width: 50px!important;
} 
.st-label {
    display: none !important;
}
.Product_Description .block-tags li:before {
    content: "\f054";
    font-family: FontAwesome;
    color: #f57a20;
    font-size: 13px;
    padding: 0px 5px 0 0;
    line-height: 30px;
}
.Product_Description .block-tags li {
    display: table;
    font-size: 14px;
    font-weight: 500;
    color: #4a4a4a;
    display: flex;
    line-height: 30px;
}
.checkout-page {
    padding: 10px;
    background: #fff;
    z-index: 1;
    overflow: hidden;
}
.rating input {
    position: absolute;
    left: -9999px;
}
.rating {
    margin-bottom: 4px;
    font-size: 15px;
    line-height: 27px;
    color: #404040;
}
.rating label {
    display: block;
    float: right;
    height: 17px;
    margin-top: 5px;
    padding: 0 2px;
    font-size: 40px;
    line-height: 17px;
    cursor: pointer;
}
.sky-form {

  color: #333;
}

.sky-form .rating label {
    display: block;
    float: right;
    height: 17px;
    margin: 1px;
    padding: 0 2px;
    font-size: 30px;
    line-height: 13px;
    cursor: pointer;
}
</style>
</head>
<body>
<!---################################## Header Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_header.jsp" />
<!---################################## Header Include Ends  ################################------>
  <!--End main-container -->
  <!-- main-container -->
  
  <div class="modal in col-md-12 col-sm-12 col-xs-12" id="myModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
  aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header" style="background: #69b044;color: #ffff;">
       <button type="button" class="close closepopup" data-dismiss="modal" aria-label="Close" style="color: #ffff;opacity: unset;">
          <span aria-hidden="true">&times;</span>
        </button>
	   <h5 class="modal-title" style="font-size: 19px;" id="exampleModalLabel">BookBattery - Customer FeedBack</h5>
      </div>
      <div class="modal-body">
      <div id="body-text" style="color: #495057;font-size: 16px;text-align: -webkit-center;">
      </div>
      </div>
      <div class="modal-footer"style="justify-content: center;text-align: -webkit-center;">
        <button type="button" id="Ok_button" class="closepopup btn btn-success" style="color: rgb(255, 255, 255);font-weight: bold;background-color: rgba(249, 116, 6, 0.76);font-size: medium;">OK</button>
      </div>
    </div>
  </div>
</div>
  <div class="main-container col2-right-layout" style="padding: 0px;">
    <div class="main container" style="margin-top: 0px;">
      <div class="row">
        <section class="col-main col-sm-9 col-lg-12">
        <div class="checkout-page">
          <div class="page-title new_page_title" style="text-align: center;">
            <h1>Please Provide Your FeedBack</h1>
          </div>
		<div class="col-sm-12 col-xs-12 col-md-12">
			<h3 style="color: #f15942;margin-top: 5px;margin-bottom: 5px;">Help Us To Provide You Best Service</h3>
		</div>
		<div class="row"> 
			<div class="col-sm-12 col-xs-12 col-md-12"  style="margin-top: 15px;">
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
			<div class="form-group">	<label for="ordernumber">Your Order Number :</label>	<input type="tel" class="form-control yes" id="ordernumber" style="font-weight: 600;" readonly="readonly" value="<%=order_number%>" placeholder="ORDBXXXXX" maxlength="10"></div>
			</div>			
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
			<div class="form-group">	<label for="username">Your Name:</label>	<input type="tel" class="form-control yes" id="username" value="<%=consumer_name%>" style="font-weight: 600;" placeholder="JOHN SMITH" readonly="readonly" maxlength="10"></div>
			</div>			
			</div>	
			<div class="col-sm-12 col-xs-12 col-md-12">			
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
			<div class="form-group">	<label for="product_brand_model">Your Product Brand and Model :</label>	<input type="tel" class="form-control yes" id="product_brand_model" style="font-weight: 600;"  placeholder="Amaron" readonly="readonly" value="<%=product_brand_model%>" maxlength="10"></div>
			</div>			
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
			<div class="form-group"><label for="producttype">Your Product Type:</label>	<input type="tel" class="form-control yes" id="producttype" placeholder="AAM-CR-AR200TT54" style="font-weight: 600;" value="<%=product_type%>" readonly="readonly" maxlength="10"></div>
			</div>			
			</div>	
			<div class="col-sm-12 col-xs-12 col-md-12">			
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
			<div class="form-group">	<label for="usermobilenumber">Your Mobile Number :</label>	<input type="tel" class="form-control yes" id="usermobilenumber" style="font-weight: 600;" readonly="readonly" value="<%=mobile_number%>" placeholder="9913XXXXXX" maxlength="10"></div>  
			</div>	
			
			<%
			if(product_type.equals("Inverter Batteries"))
			{
			%>
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
			<div class="form-group"><label for="capacity">Battery Capacity:</label>	<input type="tel" class="form-control yes" id="capacity" style="font-weight: 600;" value="<%=capacity%>" readonly="readonly" placeholder="Audi A4 Diesel " maxlength="10"></div>
			</div></div>
			<div class="col-sm-12 col-xs-12 col-md-12">			
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
			<div class="form-group"><label for="exisiting_inverter_make_model">Existing Inverter Make Model</label>	<input type="tel" class="form-control yes" style="font-weight: 600;" readonly="readonly" value="<%=exisiting_inverter_make_model%>" id="exisiting_inverter_make_model" placeholder="Yes" maxlength="10"></div>
			</div>
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
			<div class="form-group"><label for="existing_inverter_working">Is Existing Inverter Working?</label>	<input type="tel" class="form-control yes" style="font-weight: 600;" readonly="readonly" value="<%=exisiting_inverter_working%>" id="exisiting_inverter_working" placeholder="Yes" maxlength="10"></div>
			</div>			
			</div>
			<%
			}
			else
			{
			%>
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
			<div class="form-group"><label for="vehiclemakemodel">Your Vehicle Make and Model :</label>	<input type="tel" class="form-control yes" id="vehiclemakemodel" style="font-weight: 600;" readonly="readonly" value="<%=vehname_model%>" placeholder="Audi A4 Diesel " maxlength="10"></div>  
			</div></div>	
			<div class="col-sm-12 col-xs-12 col-md-12">			
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
			<div class="form-group"><label for="volts_before_car_started">Volts Before Car Started</label>	<input type="tel" class="form-control yes" style="font-weight: 600;" readonly="readonly" value="<%=volts_before_car_started%>" id="volts_before_car_started" placeholder="Yes" maxlength="10"></div>
			</div>
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
			<div class="form-group"><label for="volts_car_cranking">Volts at crancking</label>	<input type="tel" class="form-control yes" style="font-weight: 600;" readonly="readonly" value="<%=volts_car_cranking%>" id="volts_car_cranking" placeholder="Yes" maxlength="10"></div>
			</div>			
			</div>
			<div class="col-sm-12 col-xs-12 col-md-12">			
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
			<div class="form-group"><label for="volts_after_car_started">Volts After Car Start/Idle</label>	<input type="tel" class="form-control yes" id="volts_after_car_started" style="font-weight: 600;" readonly="readonly" value="<%=volts_after_car_started%>" placeholder="Yes" maxlength="10"></div>
			</div>	
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
			<div class="form-group"><label for="volts_car_acceleration">Volts at acceleration</label><input type="tel" class="form-control yes" id="volts_car_acceleration" style="font-weight: 600;" readonly="readonly" value="<%=volts_car_acceleration%>" placeholder="Yes" maxlength="10"></div>
			</div>		
			</div>
			<%
			}
			%>
			
			<div class="col-sm-12 col-xs-12 col-md-12">	

			<%
			if(warranty_expiry_date.equals(""))
			{
			%>
			
			<%
			}
			else
			{
			%>
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
			<div class="form-group">	<label for="warrantyexpirydate">Your Battery Warranty Expiry Date:</label>	<input type="tel" class="form-control yes" id="warrantyexpirydate" readonly="readonly" style="font-weight: 600;" value="<%=warranty_expiry_date%>" placeholder="2021-01-01" maxlength="10"></div>
			</div>	
			<%
			}
			%>			
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
			<div class="form-group"><label for="terminalclean">Are Terminal Plugs Cleaned with the Rust Paper and Applied Gel for Terminals?</label><input type="tel" style="font-weight: 600;" readonly="readonly" value="<%=terminals_src_spc%>" class="form-control yes" id="terminalclean" placeholder="Yes" maxlength="10"></div>
			</div>			
			</div>
			<div class="col-sm-12 col-xs-12 col-md-12">			
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
			<div class="form-group"><label for="batteryclean">Is Total Battery and Battery Compartment Cleaned with Cloth?</label>	<input type="tel" class="form-control yes" style="font-weight: 600;" readonly="readonly" value="<%=battery_cleanup_cloth%>" id="batteryclean" placeholder="Yes" maxlength="10"></div>
			</div>
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
			<div class="form-group"><label for="provideinvoicebill">Is Invoice / Bill Provided to You?</label>	<input type="tel" class="form-control yes" id="provideinvoicebill" style="font-weight: 600;" readonly="readonly" value="<%=invoice_bill%>" placeholder="Yes" maxlength="10"></div>
			</div>		
			</div>
			<div class="col-sm-12 col-xs-12 col-md-12">			
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
			<div class="form-group"><label for="providewarrantycard">Is Warranty Card with Seal of the Dealer provided to You?</label>	<input type="tel" class="form-control yes" readonly="readonly" style="font-weight: 600;" value="<%=warranty_card_seal%>" id="providewarrantycard" placeholder="Yes" maxlength="10"></div>
			</div>
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
			<div class="form-group"><label for="cus_satisfied">Are You Satisfied with our Service?</label>	<input type="tel" class="form-control yes" id="cus_satisfied" readonly="readonly" style="font-weight: 600;" value="<%=customer_satisfied%>" placeholder="Yes" maxlength="10"></div>
			</div>
			</div>
			<div class="col-sm-12 col-xs-12 col-md-12">
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 sky-form">
			<label for="cus_satisfied">Please Provide Your Valuable Rating</label>
				<div id="rating_div" class="wrap-input100 validate-input" data-validate = "Valid email is required: ex@abc.xyz" style="padding: 10px;">
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
					<br><br>
					</div>	
			<div id='rating_div-error'style="display:none;"></div>
			</div>	
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
			<div class="form-group"><label for="message">Write your valuable comments</label>	<textarea class="form-control yes" id="message" name="message" style="font-weight: 600;margin: 0px 2px 0px 0px;height: 150px;"  placeholder="Please enter your comments to improve our service"></textarea></div>
			<div id='message-error'style="display:none;"></div>
			</div>			
			</div>		
			<div style="text-align: center;" class="col-md-12 col-sm-6 col-xs-12"> 	
			<button type="button" class="button button-continue">Submit</button> 
			</div> 	
			<div class="col-sm-12 col-xs-12 col-md-12" style="text-align: center;margin-top: 15px;">					
			<i class="icon-smile" style="font-size: 50px;color: #1d8e11;"></i>					
			<p style="font-size: 14px;font-weight: 600;">Thanks for your Support</p>				
			</div>	
		</div>
        </div>
		</section>
      </div>
    </div>
  </div>
  <!--End main-container --> 
  
  <!--End main-container --> 
<input type="hidden" name="product_page_name" id='product_page_name' value="404">
<input type="hidden" name="service_eng_mobile" id='service_eng_mobile' value="<%=service_eng_mobile%>">
<!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_js.jsp" />
<!---################################## CSS Include Ends  ################################------>

<!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_footer.jsp" />
<!---################################## CSS Include Ends  ################################------>
	<script src="<%=baseurldirectory%>cusfeedback/js/main.js"></script>
	<script src="<%=baseurldirectory%>cusfeedback/vendor/animsition/js/animsition.min.js"></script>
<!--===============================================================================================-->
	<script src="<%=baseurldirectory%>cusfeedback/vendor/bootstrap/js/popper.js"></script>
	<script src="<%=baseurldirectory%>js/popgreyout.js"></script>

<script>
	function goBack() {
		window.history.back();
	}	
</script>
<script type="text/javascript">
	
$('.button-continue').click(function(){
	
	//alert(22);
	
	var ordernumber=$('#ordernumber').val();
	var username=$('#username').val();
	var product_brand_model=$('#product_brand_model').val();
	var producttype=$('#producttype').val();
	var usermobilenumber=$('#usermobilenumber').val();
	var vehiclemakemodel=$('#vehiclemakemodel').val();
	var capacity=$('#capacity').val();
	var terminalclean=$('#terminalclean').val();
	var batteryclean=$('#batteryclean').val();
	var volts_before_car_started=$('#volts_before_car_started').val();
	var volts_after_car_started=$('#volts_after_car_started').val();
	var volts_car_cranking=$('#volts_car_cranking').val();
	var volts_car_acceleration=$('#volts_car_acceleration').val();
	var provideinvoicebill=$('#provideinvoicebill').val();
	var warrantyexpirydate=$('#warrantyexpirydate').val();
	var providewarrantycard=$('#providewarrantycard').val();
	var cus_satisfied=$('#cus_satisfied').val();
	var exisiting_inverter_make_model=$('#exisiting_inverter_make_model').val();
	var exisiting_inverter_working=$('#exisiting_inverter_working').val();
	var message=$('#message').val();
	var service_eng_mobile=$('#service_eng_mobile').val();
	
	
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
			$('#rating_div-error').show();
			var errorMSG ="<font color='red'><b>Please provide Your Rating!</b></font>";
			$('#rating_div').css("border-color", "#c80000");
			document.getElementById("rating_div-error").innerHTML=errorMSG;
			$('#rating_div-error').focus();
			return;
		}
		$('#rating_div-error').hide();
			//alert(star_rated);
		if (message=="")
		{
			$('#message-error').show();
			var errMsg ="<span class='error'>Please Enter Your Comments.</span>";
			document.getElementById('message-error').innerHTML=errMsg;
			$('#message').addClass('error');
			$('#message').removeClass('warning success info');
			$('#message-error').focus();
			return ;
		} 
		$('#message-error').hide();
		
		$.ajax
		({					 
			type: "GET",
			url: "/bookbattery/feedback?hidWhatToDo=insertcustomerfeedbackbattery",
			data: {
				   ordernumber: ordernumber,
				   username: username,
				   usermobilenumber: usermobilenumber,
				   producttype: producttype,
				   product_brand_model: product_brand_model,
				   vehiclemakemodel: vehiclemakemodel,
				   capacity: capacity,
				   terminalclean: terminalclean,
				   batteryclean: batteryclean,
				   volts_before_car_started: volts_before_car_started,
				   volts_after_car_started: volts_after_car_started,
				   volts_car_cranking: volts_car_cranking,
				   volts_car_acceleration: volts_car_acceleration,
				   provideinvoicebill: provideinvoicebill,
				   warrantyexpirydate: warrantyexpirydate,
				   providewarrantycard: providewarrantycard,
				   cus_satisfied: cus_satisfied,
				   exisiting_inverter_make_model: exisiting_inverter_make_model,
				   exisiting_inverter_working: exisiting_inverter_working,
				   cus_satisfied: cus_satisfied,
				   message: message,
				   star_rated: star_rated,
				   service_eng_mobile: service_eng_mobile,
			},
			success: function(res)
			{	
				//alert(res);
				greyout(true);
				$("#myModal").show();
				
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
				
			}	
		});
	
});
	
</script>

</body>
</html>