<%-- 
Document   		 : registerbattery.jsp
Created on 		 : Sep 15, 2016, 10:14:12 AM
Author     		 : BookBattery
Copyright Notice : BookBattery Confidential.
Document         : This jsp is used as Inverter Multi Product list and details page of BookBattery Batteries.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Enumeration,java.util.Properties,java.io.FileInputStream,javax.servlet.ServletContext"%>
<%
Properties propsMOPConfig = new Properties();
ServletContext context = getServletContext();
FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
propsMOPConfig.load(fin1); 
fin1.close(); 
String publicUrl = (propsMOPConfig.getProperty("publicUrl")!=null)?propsMOPConfig.getProperty("publicUrl"):"";
String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?propsMOPConfig.getProperty("baseurldirectory"):"";

%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<!--[if IE]>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<![endif]-->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Buy Car Battery, Inverter Batteries & Inverters Online in India at BookBattery.com</title>
<meta name="description" content="Looking for a Car Battery, Inverter Battery in India. Buy Amaron, Exide & Luminious BookBattery with free delivery, free installation & company warranty ">
<meta name="keywords" content="car battery, car battery online, 4  wheeler battery, Exide battery, amaron battery, amaron car battery, exide car battery, inverter battery, amaron inverter battery, exide inverter battery, inverter battery online, BookBattery">

<!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "dev/includes/jsp/includes_css.jsp" />
<!---################################## CSS Include Ends  ################################------>

</head>
<body>
<div class="page">

<!---################################## Header Include Starts  ################################------>
	<jsp:include page = "dev/includes/jsp/includes_header.jsp" />
<!---################################## Header Include Ends  ################################------>
  <!-- breadcrumbs -->
  <div class="breadcrumbs">
    <div class="container">
      <div class="row">
        <ul>
          <li class="home"> <a href="<%=publicUrl%><%=baseurldirectory%>" title="Go to Home Page"> Home</a><span>&raquo;</span></li>
          <li class="category13"><strong>Register Your Battery with Us </strong></li>
        </ul>
      </div>
    </div>
  </div>
  	<ol class="hide" itemscope itemtype="http://schema.org/BreadcrumbList">
	  <li itemprop="itemListElement" itemscope itemtype="http://schema.org/ListItem">
		<a itemscope itemtype="http://schema.org/Thing" itemprop="item" href="<%=publicUrl%><%=baseurldirectory%>registerbattery.jsp">
		  <span itemprop="name">Register Your Battery with Us</span>
		</a>
		<meta itemprop="position" content="1" />
	  </li>
	</ol>
  <!-- End breadcrumbs --> 
  <div class="main-container col2-right-layout">
    <div class="main container">
      <div class="row">
        <section class="col-main col-sm-9" style=" font-size: 14px;">  <div class="static-contain">
          <div class="page-title new_page_title">
            <h2>Register Your Battery with Us </h2>
          </div>
		<div id="Battery_Finder">
			<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
				<div class="form-group">
				  <label for="product_type">Product Type *:</label>
				  <select class="form-control yes" id="product_type">
					<option value="0">Select Product Type</option>
					<option value="Car Batteries" >Car Batteries</option>
					<option value="Inverter Batteries" >Inverter Batteries</option>
					<option value="Bike Batteries" >Bike Batteries</option>
					<option value="Bus Batteries" >Bus Batteries</option>
					<option value="Tractor Batteries" >Tractor Batteries</option>
					<option value="Truck Batteries" >Truck Batteries</option>
					<option value="Three Wheeler Batteries" >Three Wheeler Batteries</option>
					<option value="Special Vehicle Batteries" >Special Vehicle Batteries</option>
					<option value="Genset Batteries" >Genset Batteries</option>
					<option value="Crane Batteries" >Crane Batteries</option>
					<option value="Roller Batteries" >Roller Batteries</option>
					<option value="Loader Batteries" >Loader Batteries</option>
					<option value="Dozer Batteries" >Dozer Batteries</option>
					<option value="Excavator Batteries" >Excavator Batteries</option>
					<option value="Tyre Handler Batteries" >Tyre Handler Batteries</option>
					<option value="Hydraulic Shovel Batteries" >Hydraulic Shovel Batteries</option>
					<option value="Harvestor Batteries" >Harvestor Batteries</option>
					<option value="Generator Batteries" >Generator Batteries</option>
					<option value="Compactor Batteries" >Compactor Batteries</option>
					<option value="Telescopic Handler Batteries" >Telescopic Handler Batteries</option>
					<option value="Forwarder Batteries" >Forwarder Batteries</option>
					<option value="Wheeled Harvester Batteries" >Wheeled Harvester Batteries</option>
					<option value="Minibus Batteries" >Minibus Batteries</option>
					<option value="Dumper Batteries" >Dumper Batteries</option>
					<option value="Construction Equipment Batteries" >Construction Equipment Batteries</option>
					<option value="Hydralic Excavator Batteries" >Hydralic Excavator Batteries</option>
				  </select>
				  <div id='product_type-error'style="display:none;"></div>
				</div>
			</div>
			
			<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12" id="product_capacity_div" style="display:none;">
				<div class="form-group">
				  <label for="product_capacity">Capacity *:</label>
				  <select class="form-control yes" id="product_capacity">
					<option value="0">Select Capacity</option>
				  </select>
				  <div id='product_capacity-error'style="display:none;"></div>
				</div>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12" id="product_make_div">
				<div class="form-group">
				  <label for="product_make">Make *:</label>
				  <select class="form-control yes" id="product_make">
					<option value="0">Select Make</option>
				  </select>
				  <div id='product_make-error'style="display:none;"></div>
				</div>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12"  id="product_model_div">
				<div class="form-group">
				  <label for="product_model">Model *:</label>
				  <select class="form-control yes" id="product_model">
					<option value="0">Select Model</option>
				  </select>
				  <div id='product_model-error'style="display:none;"></div>
				</div>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
				<div class="form-group">
				  <label for="product_brand_reg">Brand :</label>
				  <select class="form-control yes" id="product_brand_reg">
					<option value="0">Select Brand</option>
				  </select>
				  <div id='product_brand_reg-error'style="display:none;"></div>
				</div>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
				<div class="form-group">
				  <label for="product_state_reg">State *:</label>
				  <select class="form-control yes product_state" id="product_state_reg">
					<option value="0">Select State</option>
				  </select>
				  <div id='product_state_reg-error'style="display:none;"></div>
				</div>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
				<div class="form-group">
				  <label for="product_city_reg">City *:</label>
				  <select class="form-control yes product_city" id="product_city_reg">
					<option value="0">Select City</option>
				  </select>
				  <div id='product_city_reg-error'style="display:none;"></div>
				</div>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
				<div class="form-group">
					<label for="product_type" class="width-100-custom">&nbsp; &nbsp; </label>
					<button onclick="funToGetbatterydetails()" class="button btn-cart" type="button">
						<span><i class="icon-basket"></i> Submit</span>
					</button>	
				</div>
			</div>
		</div>
					
        </div>
        
        </section>
		<div id="Order_form_PopUp"></div>
        <aside class="col-right sidebar col-sm-3 wow bounceInUp animated">
          <div class="block block-company">
            <div class="block-title">Quick Links </div>
            <div class="block-content">
              <ol id="recently-viewed-items">
                <li class="item odd"><strong>About Us</strong></li>
                <li class="item  even"><a href="payments.jsp">Payments</a></li>
                <li class="item  even"><a href="cancellation.jsp">Cancellation and Refund</a></li>
                <li class="item  even"><a href="choosecarbattery.jsp">Choose your Car Battery</a></li>
                <li class="item  even"><a href="privacypolicy.jsp">Privacy Policy</a></li>
                <li class="item  even"><a href="shippingpolicy.jsp">Shipping Policy</a></li>
                <li class="item even"><a href="faq.jsp">FAQ's</a></li>
                <li class="item last"><a href="contactus.jsp">Contact Us</a></li>
              </ol>
            </div>
          </div>
        </aside>
      </div>
    </div>
  </div> 
  <div id="AskforMobileNumber_Pop_Up"></div>
<!---################################## CSS Include Starts  ################################------>
	<jsp:include page = "/assets/includes/includes_js.jsp" />
<!---################################## CSS Include Ends  ################################------>
<input type="hidden" name="brandkeyword" id='brandkeyword' value="Common">
<!---################################## JS Include Starts  ################################------>
	<jsp:include page = "dev/includes/jsp/includes_footer.jsp" />
<!---################################## JS Include Ends  ################################------>
<input type="hidden" name="strEmail" value="">
<input type="hidden" name="publicUrl" id='publicUrl' value="<%=publicUrl%>">

<script>

function funToGetbatterydetails()
{
	
	var batterytype  =$("#product_type").val();
	var vehiclename  =$("#product_make").val();
	var batterycapty  =$("#product_capacity").val();
	var vehiclemodel =$("#product_model").val();
	var batterybrand =$("#product_brand_reg").val();
	var state =$("#product_state_reg").val();
	var city =$("#product_city_reg").val();
	var area = "";
	var pincode = "";

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
	}
	
	if (product_type=="Inverter Batteries"){
		
		if (selectValidation("","product_capacity",input_type) == false)
		{
			return;
		}
		else
		{
		}
	}
	else
	{
		if (selectValidation("","product_make",input_type) == false)
		{
			return;
		}
		else
		{
			setCookie("product_make_cookie", product_make)
		}
		
		if (selectValidation("","product_model",input_type) == false)
		{
			return;
		}
		else
		{
		}
	}

	if (selectValidation("","product_brand_reg",input_type) == false)
	{
		return;
	}
	else
	{
	}
	
	if (selectValidation("","product_state_reg",input_type) == false)
	{
		return;
	}
	else
	{
	}
	
	if (selectValidation("","product_city_reg",input_type) == false)
	{
		return;
	}
	else
	{
	}
	
	var MobileNumberPopUp_html=" <a data-toggle='modal' data-target='.ask_user_mobile_number' id='AskforMobileNumber_Pop_Up_btn' type='hidden'  class='btn hide' data-toggle='modal' data-backdrop='static' data-keyboard='false'  ></a> <div class='modal fade ask_user_mobile_number' tabindex='-1' role='dialog' aria-labelledby='mySmallModalLabel'>	<div class='modal-dialog modal-sm' role='document'>		<div class='modal-content'><div class='modal-header'>	<button type='button' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'> x</span></button>	<h4 class='modal-title' id='mySmallModalLabel'>Enter Your Battery Details</h4></div><div class='modal-body'>  <div id='ask_mobile_div'> <div class='form-group'>	<label for='usermobilenumber'>Your Mobile Number *:</label>	<input type='tel' class='form-control yes' id='usermobilenumber' placeholder='9913XXXXXX' maxlength='10'>	<div id='usermobilenumber-error'style='display:none;'></div>  </div>  <div class='form-group'>	<label for='inmonth'>Month Of Battery Installation :</label>	<input type='tel' class='form-control' id='inmonth' placeholder='04' maxlength='2'>	<div id='inmonth-error' style='display:none;'></div>  </div>  <div class='form-group'>	<label for='inyear'>Year Of Battery Installation :</label>	<input type='tel' class='form-control' id='inyear' placeholder='1994' maxlength='4'>	<div id='inyear-error' style='display:none;'></div>  </div>  <div class='form-group'>	<label for='batmodelnumber'>Battery Model Number :</label>	<input type='text' class='form-control' id='batmodelnumber' placeholder='AAM-FL-555112054' maxlength='30'>	<div id='batmodelnumber-error' style='display:none;'></div>  </div> <div class='form-group'>		<label for='product_type' class='width-100-custom'>&nbsp; &nbsp; </label>		<button  name='Submitrret' onclick=\"javascript:askcosumerdetails1('"+batterytype+"','"+vehiclename+"','"+vehiclemodel+"','"+batterybrand+"','"+batterycapty+"','"+state+"','"+city+"','"+area+"','"+pincode+"','Submitrret');\" class='button btn-cart' type='button'>			<span><i class='icon-basket'></i> Submit</span>		</button>	</div> </div> <div id='confirm_otp_div' style='display:none;'> <div class='form-group'>	<label for='verifycode'>Please Enter Verification code received on SMS :</label>	<input type='tel' class='form-control' id='verifycode' placeholder='xxxxxx' maxlength='6'>	<div id='verifycode-error' style='display:none;'></div>  </div> <div class='form-group'>		<label for='product_type' class='width-100-custom'>&nbsp; &nbsp; </label>		<button  id='confirm_otp' onclick=\"javascript:askcosumerdetails1('"+batterytype+"','"+vehiclename+"','"+vehiclemodel+"','"+batterybrand+"','"+batterycapty+"','"+state+"','"+city+"','"+area+"','"+pincode+"','Submitrret');\" class='button btn-cart' type='button'>			<span><i class='icon-basket'></i> Submit</span>		</button>		</div> </div>  </div></div>		</div>	  </div>	 ";
	
	$("#AskforMobileNumber_Pop_Up").html(MobileNumberPopUp_html);	
	$("#AskforMobileNumber_Pop_Up_btn").click();
}

function askcosumerdetails1(batterytype,vehiclename,vehmodel,batterybrand,batterycapcity,state,city,area,pincode,varButton)
{
	var strUsermobileno  =$("#usermobilenumber").val();
	var inmonth  =$("#inmonth").val();
	var inyear  =$("#inyear").val();
	var batmodelnumber  =$("#batmodelnumber").val();
	

	if (mobilenumberValidation("","usermobilenumber","mobilenumber") == false)
	{
		return;
	}
	if (numberValidation("","inmonth","") == false)
	{
		return;
	}
	else
	{
		if ((inmonth.length < 1 || inmonth.length > 2 || inmonth > 12 )& inmonth.length > 0)
		{
			everyThingNotOk("inmonth","Invalid<b> Month.</b>");
			return false ;
		}
		else
		{	
			everyThingOk("inmonth");
		}	
	}
	if (numberValidation("","inyear","") == false)
	{
		return;
	}
	else
	{
		if ((inyear.length < 4 || inyear.length > 4 || inyear > 2099 || inyear < 1090 )& inyear.length > 0)
		{
			everyThingNotOk("inyear","Invalid<b> Year.</b>");
			return false ;
		}
		else
		{	
			everyThingOk("inyear");
		}	
	}
	if ((batmodelnumber.length < 5 || batmodelnumber.length > 30 )& batmodelnumber.length > 0)
	{
		everyThingNotOk("batmodelnumber","Invalid<b> Battery Model.</b>");
		return false ;
	}
	else
	{	
		everyThingOk("batmodelnumber");
	}	
	
	$("#Loading_bar").show();
	var xmlhttp= "";
	var resp= "";
	var url="/bookbattery/servlet/BatteryHealthcheck?hidWhatToDo=sentverificationcode1&inmonth="+inmonth+"&inyear="+inyear+"&batmodelnumber="+batmodelnumber+"&strUsermobileno="+strUsermobileno+"&batterytype="+batterytype+"&vehiclename="+vehiclename+"&vehmodel="+vehmodel+"&batterybrand="+batterybrand+"&batterycapcity="+batterycapcity+"&state="+state+"&city="+city+"&area="+area+"&pincode="+pincode+"&keyword=register";
	if (window.XMLHttpRequest)
	{
	 xmlhttp=new XMLHttpRequest();
	}
	else
	{
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if (xmlhttp.readyState==4)
		{
			if(xmlhttp.status!=200)
			{
				return;
			}
			else
			{
				$("#Loading_bar").hide();
				resp = xmlhttp.responseText;
				//$('#divmobile').hide();
				$("#confirm_otp_div").show();
				$("#ask_mobile_div").hide();
				$('#confirm_otp').attr("onClick",resp);
				document.getElementById("verifycode").focus();
			}
		}	
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();	

}
function checkverification(inmonth,inyear,batmodelnumber,strUsermobileno,verificationcode,batterytype,vehiclename,vehmodel,batterybrand,batterycapcity,state,city,area,pincode)
{
	var verifycode  =$("#verifycode").val();
	if (verificationcode == verifycode)
	{
		errMsg ="<a data-toggle='modal' data-target='#battery_address_form' id='AskforMobileNumber_Pop_Up_btn' type='hidden'  class='btn hide' data-toggle='modal' data-backdrop='static' data-keyboard='false'  ></a>     <div class='modal fade' id='battery_address_form' tabindex='-1' role='dialog' aria-labelledby='battery_address_formLable'> <div class='modal-dialog' role='document'> <div class='modal-content'> <div class='modal-header'> <button type='button' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'>&times;</span></button> <h4 class='modal-title' id='battery_address_formLable'>Please Enter Your Location Details!</h4> </div> <div class='modal-body'>  <div class='form-group'> <label for='username'>Please Enter Your Name :</label> <input type='text' class='form-control name yes' id='username' placeholder='Your Name' maxlength='60'> <div id='username-error'style='display:none;'></div> </div> <div class='form-group'> <label for='emailid'>Please Enter Email :</label> <input type='email' class='form-control email yes' id='emailid' aria-describedby='emailHelp' placeholder='Enter Email' maxlength='30'> <div id='emailid-error'style='display:none;'></div> <small id='emailid-help' class='form-text text-muted'>We'll never share your email with anyone else.</small> </div> <div class='form-group'> <label for='addrs1'>Please Enter Your Address :</label> <textarea class='form-control' class='form-control email yes' id='addrs1' rows='4' maxlength='300' placeholder='Enter Your Address'></textarea> <div id='addrs1-error'style='display:none;'></div> </div> <div class='form-group'> <label for='addrs2'>Landmark :</label> <input type='text' class='form-control' id='addrs2' aria-describedby='emailHelp' placeholder='E.g. Near AIIMS Flyover, Behind Regal Cinema, etc.' maxlength='30' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:getconsumerdetails('"+inmonth+"','"+inyear+"','"+batmodelnumber+"','"+strUsermobileno+"','"+verificationcode+"','"+batterytype+"','"+vehiclename+"','"+vehmodel+"','"+batterybrand+"','"+batterycapcity+"','"+state+"','"+city+"','"+area+"','"+pincode+"','Submitrret');return false;} else return true;\"> <div id='addrs2-error' style='display:none;'></div> </div> <div class='form-group'> <label for='order-form-pincode'>Your City :</label> <input type='text' class='form-control' id='order-form-city' value='"+city+"' readonly='readonly'> </div> <div class='form-group'> <label for='order-form-pincode'>Your State :</label> <input type='text' class='form-control' id='order-form-state' readonly='readonly' value='"+state+"' > </div> <div class='form-group'> <label for='product_type' class='width-100-custom'>&nbsp; &nbsp; </label><button id='confirm_otp' class='button btn-cart' type='button' onclick=\"javascript:getconsumerdetails('"+inmonth+"','"+inyear+"','"+batmodelnumber+"','"+strUsermobileno+"','"+verificationcode+"','"+batterytype+"','"+vehiclename+"','"+vehmodel+"','"+batterybrand+"','"+batterycapcity+"','"+state+"','"+city+"','"+area+"','"+pincode+"','Submitrret');\"> <span><i class='icon-basket'></i> Submit</span> </button> </div></div> </div></div></div>";

		$("#AskforMobileNumber_Pop_Up").html(errMsg);	
		$("#AskforMobileNumber_Pop_Up_btn").click();
		
						
		$('#battery_address_form').on('hidden.bs.modal', function () {
		  $(".modal-backdrop").removeClass( "modal-backdrop fade in");
		  
		});
	}
	else
	{
		everyThingNotOk("verifycode","Invalid<b> Verification Code.</b>");
		return false ;
    }

}


function getconsumerdetails(inmonth,inyear,batmodelnumber,strUsermobileno,verificationcode,batterytype,vehiclename,vehmodel,batterybrand,batterycapcity,state,city,area,pincode,varButton)
{
	var strusername  =$("#username").val();
	var emailid  =$("#emailid").val();
	var addrs1  =$("#addrs1").val();
	var addrs2  =$("#addrs2").val();
	
    if (nameValidation("","username","name") == false)
	{
		return;
	}
	else
	{
	}	
	if (emailValidation("","emailid","email") == false)
	{
		return;
	}
	else
	{
	}	

	if (addressValidation("","addrs1","address") == false)
	{
		return;
	}
	else
	{
	}
	
	strusername =escape(strusername);
	emailid=escape(emailid);
	addrs1=escape(addrs1);
	addrs2=escape(addrs2);
	$("#Loading_bar").show();
    var xmlhttp= "";
	var resp= "";
	var url="/bookbattery/servlet/BatteryHealthcheck?hidWhatToDo=insertconsumerdetails1&username="+strusername+"&emailid="+emailid+"&addrs1="+addrs1+"&addrs2="+addrs2+"&userarea="+area+"&usercity="+city+"&userzipcode="+pincode+"&inmonth="+inmonth+"&inyear="+inyear+"&batmodelnumber="+batmodelnumber+"&mobilenumber="+strUsermobileno+"&verifycode="+verificationcode+"&batterytype="+batterytype+"&vehiclename="+vehiclename+"&vehmodel="+vehmodel+"&batterybrand="+batterybrand+"&batterycapcity="+batterycapcity+"&state="+state+"&city="+city+"&area="+area+"&pincode="+pincode;     

	if (window.XMLHttpRequest)
	{
		xmlhttp=new XMLHttpRequest();
	}
	else
	{
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if (xmlhttp.readyState==4)
		{
			if(xmlhttp.status!=200)
			{
				return;
			}
			else
			{
				$("#Loading_bar").hide();
				resp = xmlhttp.responseText;
				$(".modal-backdrop").removeClass( "modal-backdrop fade in");
				errMsg ="<a data-toggle='modal' data-target='.ask_user_mobile_number' id='AskforMobileNumber_Pop_Up_btn' type='hidden'  class='btn hide' data-toggle='modal' data-backdrop='static' data-keyboard='false'  ></a> <div id='ask_user_mobile_number' class='modal fade ask_user_mobile_number' tabindex='-1' role='dialog' aria-labelledby='mySmallModalLabel' style=' text-align: center;' font-size: 16px;'>	<div class='modal-dialog modal-sm' role='document'>		<div class='modal-content'><div class='modal-header'>	<button onclick='javascript:closemobilediv()' type='button' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'> x</span></button>	<h4 class='modal-title' id='mySmallModalLabel'>Successfully registered</h4></div><div class='modal-body'><div>  Successfully registered your battery details. We will remind you on your appropriate battery replacement.!</div>  <div> <div class='form-group'>		<label for='product_type' class='width-100-custom'>&nbsp; &nbsp; </label>		<button  id='confirm_otp' onclick='javascript:closemobilediv()' class='button btn-cart' type='button'>			<span><i class='icon-basket'></i> Submit</span>		</button>		</div> </div>  </div></div>		</div></div>";
				$(".modal-backdrop").removeClass( "modal-backdrop fade in");
				$("#AskforMobileNumber_Pop_Up").html(errMsg);	
				$("#AskforMobileNumber_Pop_Up_btn").click();
				
				$('#ask_user_mobile_number').on('hidden.bs.modal', function () {
				  $(".modal-backdrop").removeClass( "modal-backdrop fade in");
				  
				});
			}
		}	
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();
}
function closemobilediv()
{
	location.href="https://www.BookBattery.com/";	
}

// Code to get Product Brand #################################################
$( document ).ready(function() {
	var inputs = $('#product_model, #product_capacity');
	$(inputs).change(function() {
		$("#product_brand_reg").html("<option>Loading ... </option>");
		
		 $.ajax({					 
			type: "GET",
			url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getbat_brand&keyword=Common",
			//data: {vehiclemodel:product_model, keyword:brandkeyword },
			success: function(data)
			{	
				data=data.replace("<option style='' value='All'>All</option>","");
				data=data.replace("&nbsp;Select&nbsp;Battery&nbsp;Brand","&nbsp;Select&nbsp;Brand");
				$("#product_brand_reg").html(data).focus();
			}
		});
	});	
});	
		

</script>


</body>
</html>
