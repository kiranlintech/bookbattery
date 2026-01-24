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
Vector brandVector=(Vector)session.getAttribute("sesinverterbrandsvector");
LogLevel.DEBUG(1,new Throwable(),"brandVector: "+brandVector);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>


<link rel="stylesheet" href="/bookbattery/css/bootstrap.min.css" />
<link rel="stylesheet" href="/bookbattery/css/bootstrap-multiselect.css" />

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
<body onload="document.updateinverterstock.invbrand.focus();" >
<form name="updateinverterstock" action="updateinverterstock"  method="post">
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
								<td class="subheading">Update&nbsp;Inverter&nbsp;Stock</td>
								<td  align="right"><a href="/bookbattery/servlet/AdminBatteryLogin?hidWhatToDo=batteryadminhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
							</tr>
							<tr><td height="10"></td></tr>
							<tr><td align="center" class="insidecontentbat"><div id="displayinvpricerrormsg"></div></td></tr>
							<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
								<tr>
									<td width="39%" class="insidecontent" align="right">Select&nbsp;Inverter&nbsp;Brand<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">
									<select name="invbrand" id="invbrand" class="insidecontent" STYLE="width: 180px" >
										<option value="0" >&lt;-&nbsp;&nbsp; Select Brand &nbsp;&nbsp; --&gt;</option>
										<%
										try
										   {
											if(brandVector!=null && brandVector.size()>0)
											{
											for(int i=0;i<brandVector.size();i++)
											{
												Hashtable ht=(Hashtable)brandVector.get(i);
												String bname=(String)ht.get("inverter_brand");
										%>
										<option value="<%=bname%>"><%=bname%></option>
										<%
										}
										}	
										}
										catch(Exception e )
										{
										e.printStackTrace();
										}
										%>
									</select></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Select&nbsp;Inverter&nbsp;Capacity<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">
										<select name="invcapacity" id ="invcapacity"  class="insidecontent" style='width:180px'>
											<option value="0" >&lt;-&nbsp;Select&nbsp;Inverter&nbsp;Capacity-&gt;</option>
										</select>
									</td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Select&nbsp;State<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">
									<select name="state" id="state" class="insidecontent" style='width:180px'>
									<option value="0" >&lt;-&nbsp;&nbsp; Select State -&gt;</option>
									</select></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Select City<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">
										<select name="city" id="city" class="insidecontent" STYLE="width: 180px">
											<option value="0" >&lt;-&nbsp;&nbsp; Select City -&gt;</option>
										</select></td>
								<input type="hidden" name="cityselected" id="cityselected"/>
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
							</td>
								</tr>
								<table width="100%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
								<tr>
									<td width="50%" class="insidecontent" align="right"><input type="button" value="Submit" class="button4" onclick="javascript:funToAddAppInvPrice();"></td>
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

<script type="text/javascript">
	

	$(document).ready(function(){

	$.ajax({
	type: "GET",
	 url: "/bookbattery/servlet/InverterDetails?hidWhatToDo=getbrands",
	success: function(data){
		$("#invbrand").html(data)
		document.updateinverterstock.invbrand.focus();
	}
	});
	});

	$(document).ready(function(){
		$category2 = $('#invbrand');
		 $category2.change(
            function() {
			 var splitvalb =$category2.val();
			 if(splitvalb == "default" || splitvalb == 0)
			 {


			 }
			 else
			 {
				 
				for(i=document.updateinverterstock.invcapacity.options.length-1;i>=1;i--)
				{
				document.updateinverterstock.invcapacity.remove(i);
				}
				for(i=document.updateinverterstock.city.options.length-1;i>=1;i--)
				{
				document.updateinverterstock.city.remove(i);
				}

				
                $.ajax({
                    type: "GET",
                     url: "/bookbattery/servlet/InverterDetails?hidWhatToDo=getinvcapcity",
                    data: {brand: $category2.val() },
                    success: function(data){
                        $("#invcapacity").html(data)
						document.updateinverterstock.invcapacity.focus();
                    }
                });
			 }
            }
        );
	});
	
	
$(document).ready(function(){
		$invcapacity = $('#invcapacity');
		 $invcapacity.change(
            function() {
			 var splitvalb =$invcapacity.val();
			 if(splitvalb == "default")
			 {

			 }
			 else
			 {
				for(i=document.updateinverterstock.city.options.length-1;i>=1;i--)
				{
				document.updateinverterstock.city.remove(i);
				}
				document.updateinverterstock.invcapacity.focus();

				
				$("#state").html("<option value='Andhra Pradesh'>Andhra Pradesh</option><option value='Arunachal Pradesh'>Arunachal Pradesh</option><option value='Assam'>Assam</option><option value='Bihar'>Bihar</option><option value='Chattisgarh'>Chattisgarh</option><option value='Delhi'>Delhi</option><option value='Goa'>Goa</option><option value='Gujarat'>Gujarat</option><option value='Haryana'>Haryana</option><option value='Himachal Pradesh'>Himachal Pradesh</option><option value='Jammu and Kashmir'>Jammu and Kashmir</option><option value='Jharkhand'>Jharkhand</option><option value='Karnataka'>Karnataka</option><option value='Kerala'>Kerala</option><option value='Madhya Pradesh'>Madhya Pradesh</option><option value='Maharashtra'>Maharashtra</option><option value='Manipur'>Manipur</option><option value='Meghalaya'>Meghalaya</option><option value='Mizoram'>Mizoram</option><option value='Nagaland'>Nagaland</option><option value='Orissa'>Orissa</option><option value='Punjab'>Punjab</option><option value='Rajasthan'>Rajasthan</option><option value='Sikkim'>Sikkim</option><option value='Tamil Nadu'>Tamil Nadu</option><option value='Telangana'>Telangana</option><option value='Tripura'>Tripura</option><option value='Union Territories'>Union Territories</option><option value='Uttarakhand'>Uttarakhand</option><option value='Uttar Pradesh'>Uttar Pradesh</option><option value='West Bengal'>West Bengal</option>");
				
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
               document.updateinverterstock.state.focus();
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
				 
			
			//alert(22);	
			
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
						document.updateinverterstock.city.focus();						
                    }
                });
			 }
            }
        );
	});


	
function funToAddAppInvPrice()    
{
	var invbrand = document.updateinverterstock.invbrand.value; 
	var invcapacity = document.updateinverterstock.invcapacity.value; 
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
	document.updateinverterstock.cityselected.value = cityselect;
	//alert(cityselect);
	}
		var stock = document.updateinverterstock.stock.value; 
 

	if(document.updateinverterstock.invbrand.value == 0 || invbrand == "default")
	 {
		errMsg ="<font color='#ff3333'>Please select \'Inverter Brand\'.</font>";
		document.getElementById("displayinvpricerrormsg").innerHTML=errMsg;
		document.updateinverterstock.invbrand.focus();
		return ;
	 }
	 if(document.updateinverterstock.invcapacity.value == 0 || invcapacity == "default")
	 {
		errMsg ="<font color='#ff3333'>Please select \'inverter Capacity\'.</font>";
		document.getElementById("displayinvpricerrormsg").innerHTML=errMsg;
		document.updateinverterstock.invcapacity.focus();
		return ;
	 }
	if(document.updateinverterstock.state.value == 0 || state == "default")
	 {
		errMsg ="<font color='#ff3333'>Please select \'State\'.</font>";
		document.getElementById("displayinvpricerrormsg").innerHTML=errMsg;
		document.updateinverterstock.state.focus();
		return ;
     }
	if(document.updateinverterstock.city.value == 0 || city == "default")
	 {
		errMsg ="<font color='#ff3333'>Please select \'City\'.</font>";
		document.getElementById("displayinvpricerrormsg").innerHTML=errMsg;
		document.updateinverterstock.city.focus();
		return ;
     }
	
		
		//document.updateinverterstock.method="post";
		//document.updateinverterstock.action="/bookbattery/servlet/InverterDetails?hidWhatToDo=updateinverterstock&invbrand="+invbrand+"&invcapacity="+invcapacity+"&state="+stateselect+"&city="+cityselect+"&stock="+stock;
		//alert(document.updateinverterstock.action);  
		//document.updateinverterstock.submit();   
		
		updateinverterstock.action = "/bookbattery/servlet/InverterDetails?hidWhatToDo=updateinverterstock";

		updateinverterstock.method = "POST";

		updateinverterstock.submit();

			
}

/*function getstates()
{
document.updateinverterstock.state.focus();
$("#state").html("<option value='0' >&lt;-&nbsp;&nbsp; Select State -&gt;</option><option value='Andhra Pradesh'>Andhra Pradesh</option><option value='Arunachal Pradesh'>Arunachal Pradesh</option><option value='Assam'>Assam</option><option value='Bihar'>Bihar</option><option value='Chattisgarh'>Chattisgarh</option><option value='Delhi'>Delhi</option><option value='Goa'>Goa</option><option value='Gujarat'>Gujarat</option><option value='Haryana'>Haryana</option><option value='Himachal Pradesh'>Himachal Pradesh</option><option value='Jammu and Kashmir'>Jammu and Kashmir</option><option value='Jharkhand'>Jharkhand</option><option value='Karnataka'>Karnataka</option><option value='Kerala'>Kerala</option><option value='Madhya Pradesh'>Madhya Pradesh</option><option value='Maharashtra'>Maharashtra</option><option value='Manipur'>Manipur</option><option value='Meghalaya'>Meghalaya</option><option value='Mizoram'>Mizoram</option><option value='Nagaland'>Nagaland</option><option value='Orissa'>Orissa</option><option value='Punjab'>Punjab</option><option value='Rajasthan'>Rajasthan</option><option value='Sikkim'>Sikkim</option><option value='Tamil Nadu'>Tamil Nadu</option><option value='Tripura'>Tripura</option><option value='Union Territories'>Union Territories</option><option value='Uttarakhand'>Uttarakhand</option><option value='Uttar Pradesh'>Uttar Pradesh</option><option value='West Bengal'>West Bengal</option>")
}*/

function FunReset()
{
	location.href="/bookbattery/jsp/admin/batterystore/inverter/updateinverterstock.jsp"
}

</script>


</table>
<input type="hidden" name="strEmail" value="">
</form>
</center>
<%
String strLogmapMsg=(String)session.getAttribute("sesupdateinverterstockErrorMsg");
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
document.getElementById("displayinvpricerrormsg").innerHTML=loginmpamessg;
</script>
<%
	session.removeAttribute("sesupdateinverterstockErrorMsg");
}
%>
</body>
</html>