<%-- 
Document   		 : batteryservice.jsp
Created on 		 : Sep 15, 2016, 10:14:12 AM
Author     		 : BookBattery
Copyright Notice : BookBattery Confidential.
--%> 
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.util.Hashtable,com.ngit.javabean.qrymgr.QueryManager,com.ngit.javabean.consumers.products.GetCookie,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Enumeration,java.util.Properties,java.io.*,javax.servlet.ServletContext"%>
<%
Properties propsMOPConfig = new Properties();
ServletContext context = getServletContext();
FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
propsMOPConfig.load(fin1); 
fin1.close(); 
String publicUrl = (propsMOPConfig.getProperty("publicUrl")!=null)?propsMOPConfig.getProperty("publicUrl"):"";
String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?propsMOPConfig.getProperty("baseurldirectory"):"";

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
String Product_State_Cookie_URL= Product_State_Cookie.replaceAll(" ", "-");
String Product_City_Cookie_URL= Product_City_Cookie.replaceAll(" ", "-");



String Bookbatteryservice = (propsMOPConfig.getProperty("Bookbatteryservice")!=null)?propsMOPConfig.getProperty("Bookbatteryservice"):"";
LogLevel.DEBUG(5,new Throwable(),"Bookbatteryservice :"+Bookbatteryservice );
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<!--<meta http-equiv="Refresh" content="0; url=https://www.BookBattery.com/services/battery-health-checkup-@-599/">-->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Buy Car Battery, Inverter Batteries & Inverters Online in India at BookBattery.com</title>
<meta name="description" content="Looking for a Car Battery, Inverter Battery in India. Buy Amaron, Exide & Luminious BookBattery with free delivery, free installation & company warranty ">
<meta name="keywords" content="car battery, car battery online, 4  wheeler battery, Exide battery, amaron battery, amaron car battery, exide car battery, inverter battery, amaron inverter battery, exide inverter battery, inverter battery online, BookBattery">

<!---################################## CSS Include Starts  ################################------>
<!---- <jsp:include page = "dev/includes/jsp/includes_css.jsp" />
<!---################################## CSS Include Ends  ################################------>

</head>
<body  style=" display: none;">
<div class="page">

<!---################################## Header Include Starts  ################################------>
	<!---- <jsp:include page = "dev/includes/jsp/includes_header.jsp" />
<!---################################## Header Include Ends  ################################------>
  <!-- breadcrumbs -->
  <div class="breadcrumbs">
    <div class="container">
      <div class="row">
        <ul>
          <li class="home"> <a href="<%=publicUrl%><%=baseurldirectory%>" title="Go to Home Page"> Home</a><span>&raquo;</span></li>
          <li class="category13"><strong>Book your Battery Service</strong></li>
        </ul>
      </div>
    </div>
  </div>
  <!-- End breadcrumbs --> 
  <div class="main-container col2-right-layout">
    <div class="main container">
      <div class="row">
        <section class="col-main col-sm-9" style=" font-size: 14px;">  <div class="static-contain">
          <div class="page-title new_page_title">
            <h2>Book your Battery Service Now for Just Rs.599/- </h2>
          </div>
        
		<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12 center-col">
			<div id="usermobilenumber_div">
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="form-group">
					  <label for="username">Your Name  *:</label>
					  <input type="text" class="form-control name yes" name='username' id='username' >
					  <div id='username-error'style="display:none;"></div>
					</div>
				</div>
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12" >
					<div class="form-group">
					  <label for="usermobilenumber">Your Mobile Number *:</label>
					  <input type="tel" class="form-control yes"  name='usermobilenumber' id='usermobilenumber'  placeholder="9913XXXXXX" maxlength="10">
					  <div id='usermobilenumber-error'style="display:none;"></div>
					</div>
				</div>
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="form-group">
						<label for="product_type" class="width-100-custom">&nbsp; &nbsp; </label>
						<button type="button" id="bookbatteryservicefun" name="bookbatteryservicefun"  class="button btn-cart" >
							<span><i class="icon-basket"></i>Book Battery Service</span>
						</button>	
					</div>
				</div>
			</div>
			<div id="usermobilenumber_verification_div" style="display:none;" >
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="form-group">
					  <label for="usermobilenumber_verification">Please Enter Verification Code *:</label>
					  <input type="tel" class="form-control yes"  name='usermobilenumber_verification' id='usermobilenumber_verification'  placeholder="XXXXXX" maxlength="6">
					  <div id='usermobilenumber_verification-error'style="display:none;"></div>
					</div>
				</div>
				<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<div class="form-group">
						<label for="product_type" class="width-100-custom">&nbsp; &nbsp; </label>
						<button type="button" onclick="javascript:insertservicedetails()" class="button button-continue" >
							<span><i class="icon-basket"></i>Confirm</span>
						</button>	
					</div>
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
<!---################################## CSS Include Starts  ################################------>
<!---- <jsp:include page = "/assets/includes/includes_js.jsp" />
<!---################################## CSS Include Ends  ################################------>
<input type="hidden" name="brandkeyword" id='brandkeyword' value="Common">
<input type="hidden" name="MobileNumberPopUpCheck" id='MobileNumberPopUpCheck' value="HomePage">
<input type="hidden" name="user_mobile_number_cookie_tmp" id='user_mobile_number_cookie_tmp' value="">
<!---################################## JS Include Starts  ################################------>
<!---- <jsp:include page = "dev/includes/jsp/includes_footer.jsp" />
<!---################################## JS Include Ends  ################################------>
<input type="hidden" name="strEmail" value="">
<input type="hidden" name="publicUrl" id='publicUrl' value="<%=publicUrl%>">
<input type="hidden" name="bookbatteryservice" id='bookbatteryservice' value="<%=Bookbatteryservice%>">

<script>
$(window).load(function() {
	 $('#scrollpopup').hide();
});

$( "#bookbatteryservicefun" ).click(function() {
	
	BookBatteryServiceFunc()
	//$(this).prop("disabled",true);
});


function checkbatteryservice() 
{
	var bookbatteryservice= document.getElementById("bookbatteryservice").value;
		if(bookbatteryservice =="Yes")
	{
		window.location.href = '/bookbattery/batteryservice.jsp';
	}
	else
	{
		$(".group5").colorbox({rel:'group5',  open:true});
	}
}

var verificationcode="";
var usermobile_name="";
var usermobile_number="";
var resp_tmp= "";
function BookBatteryServiceFunc()
{
	usermobile_name =$("#username").val();
	usermobile_number =$("#usermobilenumber").val();
	
	if (nameValidation("","username","name") == false)
	{
		return;
	}
	if (mobilenumberValidation("","usermobilenumber","mobilenumber") == false)
	{
		return;
	}
	$("#Loading_bar").show();
	var xmlhttp= "";
	var url="/bookbattery/servlet/BatteryService?hidWhatToDo=sentverificationcode&username="+usermobile_name+"&usermobilenumber="+usermobile_number+"&keyword=healthcheck";

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
				$("#usermobilenumber_verification_div").show();
				$("#usermobilenumber_div").hide();
				$("#usermobilenumber").attr('readonly', true); 
				$("#username").attr('readonly', true); 
				resp1 = xmlhttp.responseText;

				assetpos = resp1.split('|');
				resp_tmp=assetpos[0];
				var id=assetpos[1];	
					//alert(id);
				id=id.trim();
				verificationcode=id;

			}
			
			
		}	
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();
	
}


function insertservicedetails()
{
	
	var usermobilenumber_verification =$("#usermobilenumber_verification").val();
	if (usermobilenumber_verification.length < 6 || usermobilenumber_verification.length > 6 )
	{
		everyThingNotOk("usermobilenumber_verification","OTP You Entered is <b>Incorrect</b>.");
		return false ;
	}
	else
	{	
		if($.trim(usermobilenumber_verification) == $.trim(verificationcode))
		{
			$("#Loading_bar").show();
			var retmobilenumber="9945966973";
			//var retmobilenumber="9963754545";
			var retailername="ManjusreeBattery";
			var retaileremailid="manjusreebattery@gmail.com";
			//var retaileremailid="jhansi@ngit.in";

			var xmlhttp= "";
			var resp= "";
			var url="/bookbattery/servlet/BatteryService?hidWhatToDo=insertconsumerdetails&username="+usermobile_name+"&usermobileno="+usermobile_number+"&keyword=healthcheck&retailername="+retailername+"&retmobilenumber="+retmobilenumber+"&retaileremailid="+retaileremailid+"&agentname="+resp_tmp; 
			
			//alert(url);

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
						var reponse ="Successfully registered your battery service details. Customer Support Engineer will schedule a battery service appointment with you.";
						
						
						var insertconsumerdetails="<a class='btn hide' data-toggle='modal' data-target='.after_order_popup' data-toggle='modal' data-backdrop='static' data-keyboard='false' id='after_order_popup_btn'></a><div class='modal fade after_order_popup' tabindex='-1' role='dialog' aria-labelledby='after_order_popup_label'> <div class='modal-dialog modal-sm' role='document'> 	<div class='modal-content'> <div class='modal-header'> 	<button type='button' onclick='checkbatteryservice()' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'> x</span></button> 	<h4 class='modal-title' id='after_order_popup_label'>Successfully - BookBattery</h4> </div> <div class='modal-body'> 	<div class='form-group'> <div id='' style=' font-size: 16px; text-align: center;'> "+reponse+" <br /><br /> </div><div style='text-align: center;'> 	<button type='button' class='button button-continue' onclick='checkbatteryservice()'>Okay</button> </div>  </div> </div> 	</div> </div> </div>";
			
						$("#Order_form_PopUp").html(insertconsumerdetails);
						$("#after_order_popup_btn").click();
					}
				}	
			}
			xmlhttp.open("GET",url,true);
			xmlhttp.send();
			
		}
		else
		{
			order_usermobile_verify="no";
			everyThingNotOk("usermobilenumber_verification","OTP You Entered is <b>Incorrect</b>.");
			return false ;
		}
	}
}

</script>


</body>
</html>
