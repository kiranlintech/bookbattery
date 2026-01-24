<%-- 
Document   		 : checkwarranty.jsp
Created on 		 : Sep 15, 2016, 10:14:12 AM
Author     		 : BookBattery
Copyright Notice : BookBattery Confidential.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.util.Hashtable,java.io.File,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Enumeration,java.util.Properties,java.io.FileInputStream,javax.servlet.ServletContext"%>
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
          <li class="category13"><strong>Check Your Battery Warranty Period </strong></li>
        </ul>
      </div>
    </div>
  </div>
    <ol class="hide" itemscope itemtype="http://schema.org/BreadcrumbList">
	  <li itemprop="itemListElement" itemscope itemtype="http://schema.org/ListItem">
		<a itemscope itemtype="http://schema.org/Thing" itemprop="item" href="<%=publicUrl%><%=baseurldirectory%>checkwarranty.jsp">
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
            <h2>Check Your Battery Warranty Period </h2>
          </div>
        
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
			<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
				<div class="form-group">
				  <label for="brand">Select&nbsp;Brand *:</label>
				  <select class="form-control yes" id="brand">
					<option style='background:#FFF;color: black;' value='' >Select&nbsp;Brand</option>
					<option style='background:#FFF' value='Amaron'>Amaron</option>
					<option style='background:#FFF' value='Exide'>Exide</option>
					<option style='background:#FFF' value='Luminous'>Luminous</option>
				  </select>
				  <div id='brand-error'style="display:none;"></div>
				</div>
			</div>
			<div class="col-lg-4 col-md-4 col-sm-6 col-xs-12">
				<div class="form-group">
				  <label for="batterymodel">Select&nbsp;Model *:</label>
				  <select class="form-control yes" id="batterymodel">
					<option style='background:#FFF;color: black;' value='' >Select&nbsp;Model</option>
				  </select>
				  <div id='batterymodel-error'style="display:none;"></div>
				</div>
			</div>
			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<div class="form-group">
					<label for="product_type" class="width-100-custom">&nbsp; &nbsp; </label>
					<button type="button" id="checkbatterywarranty" class="button button-continue" >
						<span><i class="icon-basket"></i>Check Battery Warranty Details</span>
					</button>	
				</div>
			</div>
		</div>
		<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12"  style='margin-left: 0px;margin-bottom: 20px;'>
		<div id="diveditprice"></div>
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
	<jsp:include page = "/assets/includes/includes_js.jsp" />
<!---################################## CSS Include Ends  ################################------>
<input type="hidden" name="brandkeyword" id='brandkeyword' value="Common">
<!---################################## JS Include Starts  ################################------>
	<jsp:include page = "dev/includes/jsp/includes_footer.jsp" />
<!---################################## JS Include Ends  ################################------>
<input type="hidden" name="strEmail" value="">
<input type="hidden" name="publicUrl" id='publicUrl' value="<%=publicUrl%>">

<script>
$(document).ready(function()
{
	$brand = $('#brand');
	$brand.change(
	function() {
	$("#batterymodel").html("Loading..")
			$.ajax({
				
				type: "GET",
				url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getbatterymodelsdetailsajax",
				data: {brand: $brand.val()},
				//data: {batterytype: $category.val() },
				success: function(data)
				{
					//alert(data);
					//$('#img3').hide();
					if(data.indexOf("defaultss")>=0)
					{
						$("#batterymodel").html(data)
						document.getElementById("batterymodel").focus();
					}
					else
					{
						$("#batterymodel").html(data)
						document.getElementById("batterymodel").focus();
					}
				}
			});
			}
	);
	   
});

$( "#checkbatterywarranty" ).click(function() {
	funToGetbatterydetails()
});


function funToGetbatterydetails()
{
	var brand =$("#brand").val();
	var model =$("#batterymodel").val();
	//alert(brand);
	
	if (selectValidation("","brand","select") == false)
	{
		return;
	}
	if (selectValidation("","batterymodel","select") == false)
	{
		return;
	}


	//alert(model);
	var xmlhttp= "";
		var resp= "";
		$("#Loading_bar").show();
		if (window.XMLHttpRequest)
		{
			// code for IE7+, Firefox, Chrome, Opera, Safari
			 xmlhttp=new XMLHttpRequest();
		}
		else
		{
			// code for IE6, IE5
			xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange=function()
		{
			if (xmlhttp.readyState==4)
			{
				if(xmlhttp.status!=200)
				{
					alert("Error Occured. Please try again.");
					return;
				}
				else
				{
					$("#Loading_bar").hide();
					resp = xmlhttp.responseText;
					//alert(resp);
					if(resp=="ERROR")
					{
						alert("Error occurred.Please try again.");
						return;
					}
					$('#displaysessupermsg').hide();
					$('#diveditprice').show();
					//console.log(resp);
					resp= resp.replace(/Pro-rata/,"<span class='Pro-rata' data-toggle='tooltip' data-placement='top' title='Pro-rata warranty is a kind of partial warranty, If your battery fails in the pro-rata warranty cycle then depending on the value of the battery, you will get discount on the Current MRP on the newly replaced battery.'>Pro-rata</span>");
					resp= resp.replace(/border: 1px solid orange/,"border: 2px solid #dedddd");
					resp= resp.replace(/orange/,"#dedddd");
					resp= resp.replace(/orange/gi,"#dedddd");
					resp= resp.replace("orange","#dedddd");
					
					//console.log(resp_2);
					document.getElementById("diveditprice").innerHTML = resp;
					//alert(resp);
					//document.getElementById("diveditprice").innerHTML = xmlhttp.responseText;
					
						$(function () {
						  $('[data-toggle="tooltip"]').tooltip()
						})
				}
			}			
		}
		xmlhttp.open("POST","/bookbattery/servlet/BatteryHome?hidWhatToDo=getbatterywarrantydetails&model="+model,true);		
		xmlhttp.send();	
}
</script>


</body>
</html>
