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
	response.sendRedirect("../../../../admin/index.html");
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

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../../images/favicon.png" type="image/x-icon">
<title>BookBattery.com - India's No.1 Automobile Battery Store</title>
<script language="JavaScript" src="../../../../js/jquery-1.8.2.js" ></script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	/*background-image: url(../../../../images/index_01_01.gif);
	background-repeat: repeat-x;*/
}
-->
</style>
<link href="../../../../css/bookbattery.css" rel="stylesheet" type="text/css" />
</head>
<body onload="document.batteryprice.batbrand.focus(); getBatteryBrands(); noBack();" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="batteryprice" action="request_for_quote.asp"  method="post">
<!-- Battery Header Starts -->
<tr>
	<jsp:include page = "../header.jsp" />
</tr>
<!-- Battery Header Ends -->
<!--<tr>
	<td>
		<img src="../../../../images/flag1234.JPG" width="880" height="15">
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
								<td class="subheading">Add&nbsp;Battery&nbsp;Price</td>
								<td  align="right"><a href="../../../../servlet/AdminBatteryLogin?hidWhatToDo=batteryadminhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
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
										<%
										try
										   {
											if(brandVector!=null && brandVector.size()>0)
											{
											for(int i=0;i<brandVector.size();i++)
											{
												Hashtable ht=(Hashtable)brandVector.get(i);
												String bname=(String)ht.get("bat_brand");
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
									<select name="state" id="state" class="insidecontent" style='width:180px'>
										<option value="0" >&lt;-&nbsp;&nbsp; Select State -&gt;</option>
									</select></td>
									<input type="hidden" name="stateselected" id="stateselected"/>
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
									<td width="39%" class="insidecontent" align="right">Actual&nbsp;Price<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="batacprice" size="20" maxlength="7"><span class="hint">Provide Price in the format '1000'.<span class="hint-pointer">&nbsp;</span></span></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Discount&nbsp;Price<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="batdisprice" size="20" maxlength="7"><span class="hint">Provide Price in the format '1000'.<span class="hint-pointer">&nbsp;</span></span></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Battery&nbsp;return&nbsp;Price<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="batwithprice" size="20" maxlength="7"><span class="hint">Provide Price in the format '1000'.<span class="hint-pointer">&nbsp;</span></span></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">E-Retailer&nbsp;Price[ERP]<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="erpprice" size="20" maxlength="7"><span class="hint">Provide ERP Price in the format '1000'.<span class="hint-pointer">&nbsp;</span></span></td>
								</tr>
								</td>
								</tr>
								<table width="100%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
								<tr>
									<td width="50%" class="insidecontent" align="right"><input type="button" value="Submit" class="button4" onclick="javascript:funToAddAppBatPrice();"></td>
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
	<!-- Inner content ends Here -->
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
		$category2 = $('#batbrand');
		 $category2.change(
            function() {
			 var splitvalb =$category2.val();
			 if(splitvalb == "default" || splitvalb == 0)
			 {
				 
				for(i=document.batteryprice.batname.options.length-1;i>=1;i--)
				{
				document.batteryprice.batname.remove(i);
				}
				for(i=document.batteryprice.batmodel.options.length-1;i>=1;i--)
				{
				document.batteryprice.batmodel.remove(i);
				}
				for(i=document.batteryprice.state.options.length-1;i>=1;i--)
				{
				document.batteryprice.state.remove(i);
				}
				for(i=document.batteryprice.city.options.length-1;i>=1;i--)
				{
				document.batteryprice.city.remove(i);
				}
				document.batteryprice.batbrand.focus();
				document.batteryprice.batacprice.value="";
				document.batteryprice.batdisprice.value="";
				document.batteryprice.batwithprice.value="";
			 }
			 else
			 {
				 
				for(i=document.batteryprice.batmodel.options.length-1;i>=1;i--)
				{
				document.batteryprice.batmodel.remove(i);
				}
				for(i=document.batteryprice.state.options.length-1;i>=1;i--)
				{
				document.batteryprice.state.remove(i);
				}
				for(i=document.batteryprice.city.options.length-1;i>=1;i--)
				{
				document.batteryprice.city.remove(i);
				}
				document.batteryprice.batacprice.value="";
				document.batteryprice.batdisprice.value="";
				document.batteryprice.batwithprice.value="";
                $.ajax({
                    type: "GET",
                     url: "../../../../servlet/BatteryPriceDetails?hidWhatToDo=getbattype",
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
				for(i=document.batteryprice.batmodel.options.length-1;i>=1;i--)
				{
				document.batteryprice.batmodel.remove(i);
				}
			for(i=document.batteryprice.state.options.length-1;i>=1;i--)
				{
				document.batteryprice.state.remove(i);
				}
				for(i=document.batteryprice.city.options.length-1;i>=1;i--)
				{
				document.batteryprice.city.remove(i);
				}
				document.batteryprice.batname.focus();
				document.batteryprice.batacprice.value="";
				document.batteryprice.batdisprice.value="";
				document.batteryprice.batwithprice.value="";
			 }
			 else
			 {
				for(i=document.batteryprice.state.options.length-1;i>=1;i--)
				{
				document.batteryprice.state.remove(i);
				}
				for(i=document.batteryprice.city.options.length-1;i>=1;i--)
				{
				document.batteryprice.city.remove(i);
				}
				document.batteryprice.batacprice.value="";
				document.batteryprice.batdisprice.value="";
				document.batteryprice.batwithprice.value="";
                $.ajax({
                    type: "GET",
                     url: "../../../../servlet/BatteryPriceDetails?hidWhatToDo=gebattmodels",
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
				 for(i=document.batteryprice.state.options.length-1;i>=1;i--)
				{
				document.batteryprice.state.remove(i);
				}
				for(i=document.batteryprice.city.options.length-1;i>=1;i--)
				{
				document.batteryprice.city.remove(i);
				}
				document.batteryprice.batmodel.focus();
				document.batteryprice.batacprice.value="";
				document.batteryprice.batdisprice.value="";
				document.batteryprice.batwithprice.value="";
			 }
			 else
			 {
				for(i=document.batteryprice.city.options.length-1;i>=1;i--)
				{
				document.batteryprice.city.remove(i);
				}
				document.batteryprice.batmodel.focus();
				document.batteryprice.batacprice.value="";
				document.batteryprice.batdisprice.value="";
				document.batteryprice.batwithprice.value="";
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
				for(i=document.batteryprice.city.options.length-1;i>=1;i--)
				{
					document.batteryprice.city.remove(i);
				}
				document.batteryprice.state.focus();
				document.batteryprice.batacprice.value="";
				document.batteryprice.batdisprice.value="";
				document.batteryprice.batwithprice.value="";
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
			  // alert(stateselect);
			   stateselect = stateselect.replace(/(^,)|(,$)/g, "")
				//alert(message);
			   stateselect=stateselect;
			  // alert(stateselect);
			  }		 
				document.batteryprice.batacprice.value="";
				document.batteryprice.batdisprice.value="";
				document.batteryprice.batwithprice.value="";
                $.ajax({
                    type: "GET",
                     url: "../../../../servlet/BatteryPriceDetails?hidWhatToDo=getcities_mul",
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
function funToAddAppBatPrice()    
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
	document.batteryprice.stateselected.value = stateselect; 
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
	var batacprice = parseFloat(document.batteryprice.batacprice.value);
	var batdisprice = parseFloat(document.batteryprice.batdisprice.value); 
	var batwithprice = parseFloat(document.batteryprice.batwithprice.value); 
	var erpprice = parseFloat(document.batteryprice.erpprice.value); 

	var mySplitResult1 = batmodel1.split(",");
	var batcap=mySplitResult1[0];
	var batmodel=mySplitResult1[1];

	//batacprice=batacprice.replace(/\./g, 'x');
	//batdisprice=batdisprice.replace(/\./g, 'x'); 


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
	  if(document.batteryprice.batacprice.value == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Actual Price\'.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.batteryprice.batacprice.focus();
			return ;
         }
		else if(document.batteryprice.batacprice.value < 0.1)
		{
			errMsg ="<font color='#ff3333'>Please enter valid price in \'Actual Price\'.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.batteryprice.batacprice.focus();
			return ;
		}
		else if (document.batteryprice.batacprice.value.length < 2)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 2 numbers in the \'Actual Price\' field.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.batteryprice.batacprice.focus();
			return ;
         }
		 else if(batacprice < 100)
		{
			errMsg ="<font color='#ff3333'>\'Actual Price\' should not lessthan Rs 100.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.batteryprice.batacprice.focus();
			return ;
		}
		else 
		{
		var checkOK = "0123456789";
		var checkStr = document.batteryprice.batacprice.value;
		var allValid = true;
		var decPoints = 0;
		var allNum = "";
		for (i = 0;  i < checkStr.length;  i++)
		{
			ch = checkStr.charAt(i);
			for (j = 0;  j < checkOK.length;  j++)
			if (ch == checkOK.charAt(j))
			break;
			if (j == checkOK.length)
			{
				allValid = false;
				break;
			}

		}
		if (!allValid)
		{
			errMsg ="<font color='#ff3333'>Please enter only digits in the \'Actual Price\' field.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.batteryprice.batacprice.focus();
			return ;
		}
		}
		if(document.batteryprice.batdisprice.value == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Discount Price\'.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.batteryprice.batdisprice.focus();
			return ;
         }
		 else if(document.batteryprice.batdisprice.value < 0.1)
		{
			errMsg ="<font color='#ff3333'>Please enter valid price in \'Discount Price\'.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.batteryprice.batdisprice.focus();
			return ;
		}
		 else if (document.batteryprice.batdisprice.value.length < 2)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 2 numbers in the \'Discount Price\' field.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.batteryprice.batdisprice.focus();
			return ;
         }
		 else 
		{
		var checkOK = "0123456789";
		var checkStr = document.batteryprice.batdisprice.value;
		var allValid = true;
		var decPoints = 0;
		var allNum = "";
		for (i = 0;  i < checkStr.length;  i++)
		{
			ch = checkStr.charAt(i);
			for (j = 0;  j < checkOK.length;  j++)
			if (ch == checkOK.charAt(j))
			break;
			if (j == checkOK.length)
			{
				allValid = false;
				break;
			}
		}
		if (!allValid)
		{
			errMsg ="<font color='#ff3333'>Please enter only digits in the \'Discount Price\' field.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.batteryprice.batdisprice.focus();
			return ;
		}
		}
		if(batacprice <= batdisprice)
		{
			errMsg ="<font color='#ff3333'>\'Discount Price\' should be lessthan \'Actual Price\'.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.batteryprice.batdisprice.focus();
			return ;
		}

		if(document.batteryprice.batwithprice.value == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Battery Return Price\'.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.batteryprice.batwithprice.focus();
			return ;
         }
		  else if(document.batteryprice.batwithprice.value < 0.1)
		{
			errMsg ="<font color='#ff3333'>Please enter valid price in \'Battery Return Price\'.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.batteryprice.batwithprice.focus();
			return ;
		}
		 else if (document.batteryprice.batwithprice.value.length < 2)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 2 numbers in the \'Battery Return Price\' field.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.batteryprice.batwithprice.focus();
			return ;
         }
		 else 
		{
		var checkOK = "0123456789";
		var checkStr = document.batteryprice.batwithprice.value;
		var allValid = true;
		var decPoints = 0;
		var allNum = "";
		for (i = 0;  i < checkStr.length;  i++)
		{
			ch = checkStr.charAt(i);
			for (j = 0;  j < checkOK.length;  j++)
			if (ch == checkOK.charAt(j))
			break;
			if (j == checkOK.length)
			{
				allValid = false;
				break;
			}

		}
		if (!allValid)
		{
			errMsg ="<font color='#ff3333'>Please enter only digits in the \'Battery Return Price\' field.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.batteryprice.batwithprice.focus();
			return ;
		}
		}
		if(batdisprice <= batwithprice)
		{
			errMsg ="<font color='#ff3333'>\'Battery Return Price\' should be lessthan \'Discount Price\'.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.batteryprice.batwithprice.focus();
			return ;
		}
		if(document.batteryprice.erpprice.value == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'ERP Price\'.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.batteryprice.erpprice.focus();
			return ;
         }
		else if(document.batteryprice.erpprice.value < 0.1)
		{
			errMsg ="<font color='#ff3333'>Please enter valid price in \'ERP Price\'.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.batteryprice.erpprice.focus();
			return ;
		}
		else if (document.batteryprice.erpprice.value.length < 2)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 2 numbers in the \'ERP Price\' field.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.batteryprice.erpprice.focus();
			return ;
         }
		else 
		{
		var checkOK = "0123456789";
		var checkStr = document.batteryprice.erpprice.value;
		var allValid = true;
		var decPoints = 0;
		var allNum = "";
		for (i = 0;  i < checkStr.length;  i++)
		{
			ch = checkStr.charAt(i);
			for (j = 0;  j < checkOK.length;  j++)
			if (ch == checkOK.charAt(j))
			break;
			if (j == checkOK.length)
			{
				allValid = false;
				break;
			}

		}
		if (!allValid)
		{
			errMsg ="<font color='#ff3333'>Please enter only digits in the \'ERP Price\' field.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.batteryprice.erpprice.focus();
			return ;
		}
		}
		if(erpprice >= batacprice)
		{
			errMsg ="<font color='#ff3333'>\'E-Retailer Price\' should be lessthan \'Actual Price\'.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.batteryprice.erpprice.focus();
			return ;
		}
		if(erpprice >= batdisprice)
		{
			errMsg ="<font color='#ff3333'>\'E-Retailer Price\' should be lessthan \'Discount Price\'.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.batteryprice.erpprice.focus();
			return ;
		}
		//document.batteryprice.method="post";
		//document.batteryprice.action="../../../../servlet/BatteryPriceDetails?hidWhatToDo=insertbatteryprices&batbrand="+batbrand+"&batmodel="+batmodel+"&batcap="+batcap+"&state="+state+"&city="+city+"&batacprice="+batacprice+"&batdisprice="+batdisprice+"&batwithprice="+batwithprice+"&erpprice="+erpprice;
		//alert(document.batteryprice.action);  
		//document.batteryprice.submit();
		
		batteryprice.action = "../../../../servlet/BatteryPriceDetails?hidWhatToDo=insertbatteryprices";

		batteryprice.method = "POST";

		batteryprice.submit();
		
			
}



function getstates()
{
document.batteryprice.state.focus();
$("#state").html("<option value='0' >&lt;-&nbsp;&nbsp; Select State -&gt;</option><option value='Andhra Pradesh'>Andhra Pradesh</option><option value='Arunachal Pradesh'>Arunachal Pradesh</option><option value='Assam'>Assam</option><option value='Bihar'>Bihar</option><option value='Chattisgarh'>Chattisgarh</option><option value='Delhi'>Delhi</option><option value='Goa'>Goa</option><option value='Gujarat'>Gujarat</option><option value='Haryana'>Haryana</option><option value='Himachal Pradesh'>Himachal Pradesh</option><option value='Jammu and Kashmir'>Jammu and Kashmir</option><option value='Jharkhand'>Jharkhand</option><option value='Karnataka'>Karnataka</option><option value='Kerala'>Kerala</option><option value='Madhya Pradesh'>Madhya Pradesh</option><option value='Maharashtra'>Maharashtra</option><option value='Manipur'>Manipur</option><option value='Meghalaya'>Meghalaya</option><option value='Mizoram'>Mizoram</option><option value='Nagaland'>Nagaland</option><option value='Orissa'>Orissa</option><option value='Punjab'>Punjab</option><option value='Rajasthan'>Rajasthan</option><option value='Sikkim'>Sikkim</option><option value='Tamil Nadu'>Tamil Nadu</option><option value='Telangana'>Telangana</option><option value='Tripura'>Tripura</option><option value='Union Territories'>Union Territories</option><option value='Uttarakhand'>Uttarakhand</option><option value='Uttar Pradesh'>Uttar Pradesh</option><option value='West Bengal'>West Bengal</option>")
var batmodel=document.batteryprice.batmodel.value;
}
function FunReset()
{
	location.href="../../../../jsp/admin/batterystore/battery/batteryprice.jsp"
}
</script>
</table>
<input type="hidden" name="strEmail" value="">
</form>
</center>
<%
String strLogmapMsg=(String)session.getAttribute("sesbatpriceErrorMsg");
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
	session.removeAttribute("sesbatpriceErrorMsg");
}
%>
</body>
</html>