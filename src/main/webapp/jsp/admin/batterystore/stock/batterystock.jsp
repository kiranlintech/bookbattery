<%--
    Document   : batteryadminmain
    Created on : Aug 30, 2013, 4:22:12 PM
    Author     : Sai Krishna Daddala.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.LogLevel"%>
<%
String strUserid=(String)session.getAttribute("sesBatteryAdminName");
if(strUserid==null)
{
	strUserid="";
	session.setAttribute("sesErrorMsg","Session Timed Out. Please login again");
	response.sendRedirect("/bookbattery/admin/index.html");
	return;
}
Vector brandVector=(Vector)session.getAttribute("sesbatterybrandsvector");
LogLevel.DEBUG(1,new Throwable(),"brandVector: "+brandVector);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>


<link rel="stylesheet" href="/bookbattery/css/bootstrap.min.css" />
 <link rel="stylesheet" href="/bookbattery/css/bootstrap-multiselect.css" />

<!-- Including Open Sans Condensed from Google Fonts -->
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans+Condensed:300,700,300italic" />

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="/bookbattery/images/favicon.png" type="image/x-icon">
<title>BookBattery.com-Online Battery Store</title>
 
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	/*background-image: url(/bookbattery/images/index_01_01.gif);
	background-repeat: repeat-x;*/
}
-->
</style>
<link href="/bookbattery/css/bookbattery.css" rel="stylesheet" type="text/css" />
</head>
<body onload="document.batteryprice.batbrand.focus(); " >

<!-- End Alexa Certify Javascript -->
<form name="batteryprice" method="post">
<!-- Battery Header Starts -->
<tr>
	<jsp:include page = "../header.jsp" />
</tr>
<!-- Battery Header Ends -->
<!--<tr>
	<td>
		<img src="/bookbattery/images/flag1234.JPG" width="880" height="15">
	</td>
</tr>-->
<tr>
	<td>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#ffffff">
		<tr>
			<td width="25%" align="left" valign="top" bgcolor="#ffffff">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<!-- Starts battery admin left Menu -->
				<tr>
					<jsp:include page="../batteryadminleftmenu.jsp"/>
				</tr>
				<!-- Ends battery admin left Menu -->
				</table>
			</td>
			<td width="75%" align="left" valign="top">
				<table width="100%" border="1" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">
					<!-- your page content starts here  -->
					<table width="100%" cellspacing="1" bgcolor="#FFFFFF" cellpadding="0" border="0">
					<tr>
						<td align="left" valign="top">
							<tr>
								<td class="subheading">Update&nbsp;Battery&nbsp;Stock</td>
								<td  align="right"><a href="/bookbattery/servlet/AdminBatteryLogin?hidWhatToDo=batteryadminhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
							</tr>
							<tr><td height="10"></td></tr>
							<tr><td align="center" class="insidecontentbat"><div id="displaybatpricerrormsg"></div></td></tr>
							<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
								<tr>
									<td width="39%" class="insidecontent" align="right">Select&nbsp;Battery&nbsp;Brand<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">
									<select name="batbrand" id="batbrand" class="insidecontent" STYLE="width: 180px" >
										<option value="0" >&lt;-&nbsp;&nbsp; Select Brand &nbsp;&nbsp; --&gt;</option>
									</select></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Select&nbsp;Bat&nbsp;Name<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">
										<select name="batname" id ="batname"  class="insidecontent" style='width:180px'>
											<option value="0" >&lt;-&nbsp;Select&nbsp;Bat&nbsp;Name-&gt;</option>
										</select>
									</td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Select Model<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">
										<select name="batmodel" id="batmodel"  class="insidecontent" STYLE="width: 180px">
											<option value="0" >&lt;-&nbsp;&nbsp; Select Model -&gt;</option>
										</select></td>
								</tr>
									<tr>
									<td width="39%" class="insidecontent" align="right">Select&nbsp;State<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">
									<select name="state" class="insidecontent" id="state" STYLE="width: 180px">
										<option value="0">&lt;-&nbsp;&nbsp; Select State -&gt;</option>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Select City<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">
										<select name="city" id="city" class="insidecontent" STYLE="width: 180px">
											<option value="0" >&lt;-&nbsp;&nbsp; Select City -&gt;</option>
										</select></td>
									
								<input type="hidden" name="cityselected" id="cityselected"/>	
								</td>
								</tr>		
								<tr>
									<td width="39%" class="insidecontent" align="right">Select Stock<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">
										<select name="stock" id="stock" class="insidecontent" STYLE="width: 180px">
											<option value="Yes" > Yes </option>
											<option value="No" > No </option>
										</select></td>
								</td>
								</tr>
								<table width="100%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
								<tr>
									<td width="50%" class="insidecontent" align="right"><input type="button" value="Submit" class="button4" onclick="javascript:funToupdatestockdetails();"></td>
									<td width="50%" class="insidecontent" align="left"><input type="button" value="Reset" class="button4" onclick="javascript:FunReset();"></td>
								</tr>
								</table>
							</td>
						</tr>
					<tr><td height="10"></td></tr>
			</td>
	</tr>
	</table>
	</td>
	</tr>
	</table>
	</td>
	</tr>
	</table>
	<!-- Inner content ends here -->
	<!-- Footer Starts Here -->
	<tr>
		<td>
			<jsp:include page = "../footer.jsp" />
		</td>
	</tr>                           
	<!-- footer Ends Here -->
</td>
</tr>


 
<script src="/bookbattery/js/jquery.min.js"></script>
<script src="/bookbattery/js/bootstrap.min.js"></script>
<script src="/bookbattery/js/bootstrap-multiselect.js"></script>

<script language=javascript src="/bookbattery/js/datepicker.js"></script>
<!--JS and CSS Files included ends here for multi select drop down -->
<script type="text/javascript">
		
		
			$(document).ready(function(){
			 
                $.ajax({
                    type: "GET",
                     url: "/bookbattery/servlet/BatteryPriceDetails?hidWhatToDo=getbrands",
                    success: function(data){
                        $("#batbrand").html(data)
						document.batteryprice.batbrand.focus();
                    }
                });
            });

	$(document).ready(function(){
		$category2 = $('#batbrand');
		 $category2.change(
            function() {
			 var splitvalb =$category2.val();
			 if(splitvalb == "default" || splitvalb == 0)
			 {
				 
			 }
			 else
			 {
				 
                $.ajax({
                    type: "GET",
                     url: "/bookbattery/servlet/BatteryPriceDetails?hidWhatToDo=getbattype",
                    data: {brand: $category2.val() },
                    success: function(data){
                        $("#batname").html(data)
						document.batteryprice.batname.focus();
                    }
                });
			 }
            }
        );
	});
	$(document).ready(function(){
		$category3 = $('#batname');
		 $category3.change(
            function() {
			 var splitvalbc =$category3.val();
			 if(splitvalbc == "default" || splitvalbc == 0)
			 {

			 }
			 else
			 {

                $.ajax({
                    type: "GET",
                     url: "/bookbattery/servlet/BatteryPriceDetails?hidWhatToDo=gebattmodels",
                    data: {batname: $category3.val() },
                    success: function(data){
				        $("#batmodel").html(data)
						document.batteryprice.batmodel.focus();
                    }
                });
			 }
            }
        );
	});
$(document).ready(function(){
		$categorymodel = $('#batmodel');
		 $categorymodel.change(
            function() {
			 var splitvalb =$categorymodel.val();
			 if(splitvalb == "default")
			 {

			 }
			 else
			 {
				for(i=document.batteryprice.city.options.length-1;i>=1;i--)
				{
				document.batteryprice.city.remove(i);
				}
				document.batteryprice.batmodel.focus();

				
				$("#state").html("<option value='Andhra Pradesh'>Andhra Pradesh</option><option value='Arunachal Pradesh'>Arunachal Pradesh</option><option value='Assam'>Assam</option><option value='Bihar'>Bihar</option><option value='Chattisgarh'>Chattisgarh</option><option value='Delhi'>Delhi</option><option value='Goa'>Goa</option><option value='Gujarat'>Gujarat</option><option value='Haryana'>Haryana</option><option value='Himachal Pradesh'>Himachal Pradesh</option><option value='Jammu and Kashmir'>Jammu and Kashmir</option><option value='Jharkhand'>Jharkhand</option><option value='Karnataka'>Karnataka</option><option value='Kerala'>Kerala</option><option value='Madhya Pradesh'>Madhya Pradesh</option><option value='Maharashtra'>Maharashtra</option><option value='Manipur'>Manipur</option><option value='Meghalaya'>Meghalaya</option><option value='Mizoram'>Mizoram</option><option value='Nagaland'>Nagaland</option><option value='Orissa'>Orissa</option><option value='Punjab'>Punjab</option><option value='Rajasthan'>Rajasthan</option><option value='Sikkim'>Sikkim</option><option value='Tamil Nadu'>Tamil Nadu</option><option value='Telangana'>Telangana</option><option value='Tripura'>Tripura</option><option value='Union Territories'>Union Territories</option><option value='Uttarakhand'>Uttarakhand</option><option value='Uttar Pradesh'>Uttar Pradesh</option><option value='West Bengal'>West Bengal</option>")
				
				$('#state').attr('multiple', true);
				$('#state').val(''); 
				$('#state').multiselect('destroy');
				$('#state').multiselect({
				includeSelectAllOption: true,	
				buttonWidth: '180px',
				enableFiltering: true,
				maxHeight: 350 
				});
	
				$('#state').multiselect('refresh');
				$('#city').attr('multiple', false);
				$('#city').val(''); 
				$("#city").html("<option value='0'>Select City</option>")
				$('#city').multiselect('destroy');	
               document.batteryprice.state.focus();
			 }
            }
        );
	});
	$(document).ready(function(){
		$category4 = $('#state');
		 $category4.change(
            function() {
			 var splitvalb =$category4.val();
			 if(splitvalb == "default")
			 {

			 }
			 else
			 {
				 
			selected = $("#state option:selected");
				stateselect = "";
			selected.each(function () {
				stateselect += "'" + $(this).val() + "',";
			});

			  if(stateselect=="")
			  {
			   //alert(21);
			   stateselect="All";
			  }
			  else
			  {
			   //alert(22);
			   stateselect = stateselect.replace(/(^,)|(,$)/g, "")
				//alert(message);
			   stateselect=stateselect;
			   //alert(stateselect);

			  }

                $.ajax({
                    type: "GET",
                     url: "/bookbattery/servlet/BatteryPriceDetails?hidWhatToDo=getcities_mul",
                    data: {state: stateselect },
                    success: function(data){	
						$("#city").html(data) 
						$('#city').attr('multiple', true);
							$('#city').val(''); 
							$('#city').multiselect('destroy');
							 $('#city').multiselect({
							 includeSelectAllOption: true,	
							buttonWidth: '180px',
							enableFiltering: true,
							maxHeight: 350
							 });
						document.batteryprice.city.focus();						
                    }
                });
			 }
            }
        );
	});
function funToupdatestockdetails()    
{
	var batbrand = document.batteryprice.batbrand.value; 
	var batname = document.batteryprice.batname.value; 
	var batmodel1 = document.batteryprice.batmodel.value; 
	var state_selected = $("#state option:selected");
	stateselect = "";
	state_selected.each(function () {
	stateselect += "'" + $(this).val() + "',";
	});

	if(stateselect=="")
	{
	//alert(21);
	stateselect="All";
	}
	else
	{
	//alert(22);
	stateselect = stateselect.replace(/(^,)|(,$)/g, "")
	//alert(message);
	stateselect=stateselect;
	//alert(stateselect);
	}
	
	var city_selected = $("#city option:selected");
	cityselect = "";
	city_selected.each(function () {
	cityselect += "'" + $(this).val() + "',";
	});

	if(cityselect=="")
	{
	//alert(21);
	cityselect="All";
	}
	else
	{
	//alert(22);
	cityselect = cityselect.replace(/(^,)|(,$)/g, "")
	//alert(message);
	cityselect=cityselect;
	//alert(cityselect);
	document.batteryprice.cityselected.value = cityselect; 
	}
	
	var stock = document.batteryprice.stock.value; 
 

	var mySplitResult1 = batmodel1.split(",");
	var batcap=mySplitResult1[0];
	//alert(batcap);
	var batmodel=mySplitResult1[1];
	//alert(batmodel);




	if(document.batteryprice.batbrand.value == 0 || batbrand == "default")
	 {
		errMsg ="<font color='#ff3333'>Please select \'Battery Brand\'.</font>";
		document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
		document.batteryprice.batbrand.focus();
		return ;
	 }
	 if(document.batteryprice.batname.value == 0 || batname == "default")
	 {
		errMsg ="<font color='#ff3333'>Please select \'Battery Name\'.</font>";
		document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
		document.batteryprice.batname.focus();
		return ;
	 }
	 if(document.batteryprice.batmodel.value == 0 || batmodel == "default" || batmodel1 == "default")
	 {
		errMsg ="<font color='#ff3333'>Please select \'Battery Model\'.</font>";
		document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
		document.batteryprice.batmodel.focus();
		return ;
	 }
	if(document.batteryprice.state.value == 0 || state == "default")
	 {
		errMsg ="<font color='#ff3333'>Please select \'State\'.</font>";
		document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
		document.batteryprice.state.focus();
		return ;
     }
	if(document.batteryprice.city.value == 0 || city == "default")
	 {
		errMsg ="<font color='#ff3333'>Please select \'City\'.</font>";
		document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
		document.batteryprice.city.focus();
		return ;
     }		
	 
	 if(!document.batteryprice.stock.value == "Yes" || !document.batteryprice.stock.value == "No")
	 {
		errMsg ="<font color='#ff3333'>Please select \'Stock\'.</font>";
		document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
		document.batteryprice.stock.focus();
		return ;
     }	

	//document.batteryprice.method="post";

		//document.batteryprice.action="/bookbattery/servlet/BatteryPriceDetails?hidWhatToDo=updatestockdetails&batbrand="+batbrand+"&batmodel="+batmodel+"&batcap="+batcap+"&state="+stateselect+"&city="+cityselect+"&stock="+stock;
		//alert(document.batteryprice.action);  
		//document.batteryprice.submit();
		batteryprice.action = "/bookbattery/servlet/BatteryPriceDetails?hidWhatToDo=updatestockdetails";

		batteryprice.method = "POST";

		batteryprice.submit();
}
function getstates()
{
document.batteryprice.state.focus();
$("#state").html("<option value='0' >&lt;-&nbsp;&nbsp; Select State -&gt;</option><option value='Andhra Pradesh'>Andhra Pradesh</option><option value='Arunachal Pradesh'>Arunachal Pradesh</option><option value='Assam'>Assam</option><option value='Bihar'>Bihar</option><option value='Chattisgarh'>Chattisgarh</option><option value='Delhi'>Delhi</option><option value='Goa'>Goa</option><option value='Gujarat'>Gujarat</option><option value='Haryana'>Haryana</option><option value='Himachal Pradesh'>Himachal Pradesh</option><option value='Jammu and Kashmir'>Jammu and Kashmir</option><option value='Jharkhand'>Jharkhand</option><option value='Karnataka'>Karnataka</option><option value='Kerala'>Kerala</option><option value='Madhya Pradesh'>Madhya Pradesh</option><option value='Maharashtra'>Maharashtra</option><option value='Manipur'>Manipur</option><option value='Meghalaya'>Meghalaya</option><option value='Mizoram'>Mizoram</option><option value='Nagaland'>Nagaland</option><option value='Orissa'>Orissa</option><option value='Punjab'>Punjab</option><option value='Rajasthan'>Rajasthan</option><option value='Sikkim'>Sikkim</option><option value='Tamil Nadu'>Tamil Nadu</option><option value='Tripura'>Tripura</option><option value='Union Territories'>Union Territories</option><option value='Uttarakhand'>Uttarakhand</option><option value='Uttar Pradesh'>Uttar Pradesh</option><option value='West Bengal'>West Bengal</option>")
var batmodel=document.batteryprice.batmodel.value;
}
function FunReset()
{
	location.href="/bookbattery/jsp/admin/batterystore/battery/batterystock.jsp"
}
</script>


</table>
<input type="hidden" name="strEmail" value="">
</form>
</center>
<%
String strLogmapMsg=(String)session.getAttribute("sesupdatebatterystockErrorMsg");
if(strLogmapMsg ==null)
{
	strLogmapMsg="";
}
else
{
%>




<script type="text/javascript">
var loginmpamessg; 
loginmpamessg= "<%=strLogmapMsg%>";
document.getElementById("displaybatpricerrormsg").innerHTML=loginmpamessg;
</script>
<%
	session.removeAttribute("sesupdatebatterystockErrorMsg");
}
%>

</body>
</html>