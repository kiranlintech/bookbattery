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
Vector StateVector=(Vector)session.getAttribute("sesEditpriceBrandVector");
LogLevel.DEBUG(1,new Throwable(),"StateVector: "+StateVector);
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
<body onload="document.editbatteryprice.state.focus(); getBatteryBrands(); noBack();" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="editbatteryprice" action="request_for_quote.asp"  method="post">
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
								<td class="subheading">Edit/Delete&nbsp;Battery&nbsp;Price</td>
								<td  align="right"><a href="../../../../servlet/AdminBatteryLogin?hidWhatToDo=batteryadminhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
							</tr>
							<tr><td height="10"></td></tr>
							<tr><td align="center" class="insidecontentbat"><div id="displaybatpricerrormsg"></div></td></tr>
							<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
								<tr>
									<td width="39%" class="insidecontent" align="right">Select&nbsp;State<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">
									<select name="state"  id="state" class="insidecontent" STYLE="width: 180px" >
										<option value="0" >&lt;-&nbsp;&nbsp; Select State &nbsp;&nbsp; --&gt;</option>
										<%
										try
										   {
											if(StateVector!=null && StateVector.size()>0)
											{
											for(int i=0;i<StateVector.size();i++)
											{
												Hashtable ht=(Hashtable)StateVector.get(i);
												String state=(String)ht.get("state");
										%>
										<option value="<%=state%>"><%=state%></option>
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
									<td width="39%" class="insidecontent" align="right">Select City<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">
										<select name="city" id="city" class="insidecontent" STYLE="width: 180px">
											<option value="0" >&lt;-&nbsp;&nbsp; Select City -&gt;</option>
										</select></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Select Brand<font color="FF0000">*</font>&nbsp;:</label></td>
									<td width="59%" class="insidecontent" align="left">
										<select name="brands" id="brands" class="insidecontent" STYLE="width: 180px">
											<option value="0" >&lt;-&nbsp;&nbsp; Select Brand &nbsp;&nbsp;-&gt;</option>
										</select>
									</td>
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
									<td width="39%" class="insidecontent" align="right">Select Model<font color="FF0000">*</font>&nbsp;:</label></td>
									<td width="59%" class="insidecontent" align="left">
										<select name="model" id="model" onChange="javascript:getprices();" class="insidecontent" STYLE="width: 180px">
											<option value="0" >&lt;-&nbsp;&nbsp; Select Model -&gt;</option>
										</select></td>
								</tr>
								</td>
								</tr>
								</td>
								</tr>
								</table>
									<tr>
									<td>
										<table width="100%" border="0" align="center">
											<tr class="#FFFFFF" bgcolor="FFFFFF">
												<td width="40%"><div id="diveditprice"></div></td>
											</tr>
										</table>
									</td>
								</tr>
								
								
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
</table>
<input type="hidden" name="strEmail" value="">
</form>
</center>
<%
String strLogmapMsg=(String)session.getAttribute("sesleditbatterypriceErrorMsg");
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
	session.removeAttribute("sesleditbatterypriceErrorMsg");
}
%>
</body>

<script src="/bookbattery/js/jquery.min.js"></script>
<script src="/bookbattery/js/bootstrap.min.js"></script>
<script src="/bookbattery/js/bootstrap-multiselect.js"></script>

<script type="text/javascript">

$(document).ready(function(){
	
	$('#state').attr('multiple', true);
	$('#state').val(''); 
	$('#state').multiselect('destroy');
	$('#state').multiselect({
	includeSelectAllOption: true,	
	buttonWidth: '180px',
	enableFiltering: true,
	maxHeight: 350 
	});
	
	$('#city').attr('multiple', false);
	$('#city').val(''); 
	$("#city").html("<option value='0'>Select City</option>")
	$('#city').multiselect('destroy');	
    document.editbatteryprice.state.focus();
	});

	$(document).ready(function(){
		$category4 = $('#state');
		 $category4.change(
            function() {
			 var splitvalb =$category4.val();
			 if(splitvalb == "default")
			 {
				for(i=document.editbatteryprice.city.options.length-1;i>=1;i--)
				{
					document.editbatteryprice.city.remove(i);
				}

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
						document.editbatteryprice.city.focus();
                    }
                });
			 }
            }
        );
	});
$(document).ready(function(){
		$category3 = $('#city');
		 $category3.change(
            function() {
			 var splitvalc =$category3.val();
			 if(splitvalc == "default" || splitvalc == 0)
			 {
				for(i=document.editbatteryprice.brands.options.length-1;i>=1;i--)
				{
				document.editbatteryprice.brands.remove(i);
				}
				for(i=document.editbatteryprice.batname.options.length-1;i>=1;i--)
				{
				document.editbatteryprice.batname.remove(i);
				}
				for(i=document.editbatteryprice.model.options.length-1;i>=1;i--)
				{
				document.editbatteryprice.model.remove(i);
				}
				document.editbatteryprice.city.focus();
				$('#diveditprice').hide();
			 }
			 else
			 {

				 for(i=document.editbatteryprice.batname.options.length-1;i>=1;i--)
				{
				document.editbatteryprice.batname.remove(i);
				}
				for(i=document.editbatteryprice.model.options.length-1;i>=1;i--)
				{
				document.editbatteryprice.model.remove(i);
				}
				$('#diveditprice').hide();
                $.ajax({
                    type: "GET",
                     url: "../../../../servlet/BatteryPriceDetails?hidWhatToDo=getbrands",
                    data: {vehiclename: "All" },
                    success: function(data){
                        $("#brands").html(data)
						document.editbatteryprice.brands.focus();
                    }
                });
			 }
            }
        );
	});
	
	$(document).ready(function(){
		$category2 = $('#brands');
		 $category2.change(
            function() {
			 var splitvalb =$category2.val();
			 if(splitvalb == "default" || splitvalb == 0) 
			 {
				for(i=document.editbatteryprice.batname.options.length-1;i>=1;i--)
				{
				document.editbatteryprice.batname.remove(i);
				}
				for(i=document.editbatteryprice.model.options.length-1;i>=1;i--)
				{
				document.editbatteryprice.model.remove(i);
				}
				document.editbatteryprice.brands.focus();
				$('#diveditprice').hide();
			 }
			 else
			 {

				 for(i=document.editbatteryprice.model.options.length-1;i>=1;i--)
				{
				document.editbatteryprice.model.remove(i);
				}
				
				$('#diveditprice').hide();
                $.ajax({
                    type: "GET",
                     url: "../../../../servlet/BatteryPriceDetails?hidWhatToDo=getbattype",
                    data: {brand: $category2.val() },
                    success: function(data){
                        $("#batname").html(data)
						document.editbatteryprice.batname.focus();
                    }
                });
			 }
            }
        );
	});
	$(document).ready(function(){
		$category4 = $('#batname');
        $category4.change(
            function() {
			var splitvalm =$category4.val();
			if(splitvalm == "default" || splitvalm == 0)
			 {
				for(i=document.editbatteryprice.model.options.length-1;i>=1;i--)
				{
				document.editbatteryprice.model.remove(i);
				}
				document.editbatteryprice.batname.focus();
				$('#diveditprice').hide();
			 }
			 else
			 {
				$('#diveditprice').hide();

                $.ajax({
                    type: "GET",
                     url: "../../../../servlet/BatteryPriceDetails?hidWhatToDo=gebattmodels",
                    data: {batname: $category4.val() },
                    success: function(data){
                        $("#model").html(data)
						document.editbatteryprice.model.focus();
                    }
                });
			 }
            }
        );
	});
function getprices()
{
	var batbrand = document.editbatteryprice.brands.value; 
	var batmodel1 = document.editbatteryprice.model.value;
	//var state = document.editbatteryprice.state.value; 
	//var city = document.editbatteryprice.city.value; 

	var mySplitResult1 = batmodel1.split(",");
	var batcap=mySplitResult1[0];
	var batmodel=mySplitResult1[1];

	var xmlhttp= "";
		var resp= "";
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
					resp = xmlhttp.responseText;
					//alert(resp);
					if(resp=="ERROR")
					{
						alert("Error occurred.Please try again.");
						return;
					}
					$('#diveditprice').show();
					document.getElementById("diveditprice").innerHTML = resp;
					document.getElementById("diveditprice").innerHTML = xmlhttp.responseText;
				}
			}			
		}
		xmlhttp.open("POST","../../../../servlet/BatteryPriceDetails?hidWhatToDo=getpricestoupdate&brand="+batbrand+"&model="+batmodel,true);		
		xmlhttp.send();	
		}

function funToUpdatprices(batpriceid)
{
	//var state = document.editbatteryprice.state.value; 
	//var batbrand = document.editbatteryprice.brands.value;
	//var model = document.editbatteryprice.model.value;
	var batacprice = parseFloat(document.editbatteryprice.actprice.value);
	var batdisprice = parseFloat(document.editbatteryprice.disprice.value);
	var batwithprice = parseFloat(document.editbatteryprice.retnprice.value);
	var erpprice = parseFloat(document.editbatteryprice.erpprice.value);
	//batacprice=batacprice.replace(/\./g, 'x');
	//batdisprice=batdisprice.replace(/\./g, 'x');
	/*if(document.editbatteryprice.state.value == 0 || state == "default")
	 {
		errMsg ="<font color='#ff3333'>Please select \'State\'.</font>";
		document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
		document.editbatteryprice.state.focus();
		return ;
     }
	if(document.editbatteryprice.brands.value == 0 || batbrand == "default")
	 {
		errMsg ="<font color='#ff3333'>Please select \'Battery Brand\'.</font>";
		document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
		document.editbatteryprice.brands.focus();
		return ;
	 }
	 if(document.editbatteryprice.model.value == 0 || model == "default")
	 {
		errMsg ="<font color='#ff3333'>Please select \'Battery Model\'.</font>";
		document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
		document.editbatteryprice.model.focus();
		return ;
	 }*/
	  if(document.editbatteryprice.actprice.value == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Actual Price\'.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.editbatteryprice.actprice.focus();
			return ;
         }
		  if(document.editbatteryprice.actprice.value < 0.1)
		  {
			errMsg ="<font color='#ff3333'>Please enter valid price in \'Actual Price\'.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.editbatteryprice.actprice.focus();
			return ;
         }
		else if (document.editbatteryprice.actprice.value.length < 2)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 2 numbers in the \'Actual Price\' field.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.editbatteryprice.actprice.focus();
			return ;
         }
		 else if(batacprice < 100)
		{
			errMsg ="<font color='#ff3333'>\'Actual Price\' should not lessthan Rs 100.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.editbatteryprice.actprice.focus();
			return ;
		}
		else 
		{
		var checkOK = "0123456789";
		var checkStr = document.editbatteryprice.actprice.value;
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
			document.editbatteryprice.actprice.focus();
			return ;
		}
		}
		if(document.editbatteryprice.disprice.value == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Discount Price\'.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.editbatteryprice.disprice.focus();
			return ;
         }
		 if(document.editbatteryprice.disprice.value < 0.1)
		  {
			errMsg ="<font color='#ff3333'>Please enter valid price in \'Discount Price\'.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.editbatteryprice.disprice.focus();
			return ;
         }
		 else if (document.editbatteryprice.disprice.value.length < 2)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 2 numbers in the \'Discount Price\' field.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.editbatteryprice.disprice.focus();
			return ;
         }
		 else 
		{
		var checkOK = "0123456789";
		var checkStr = document.editbatteryprice.disprice.value;
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
			document.editbatteryprice.disprice.focus();
			return ;
		}
		}
		if(batacprice <= batdisprice)
		{
			errMsg ="<font color='#ff3333'>\'Discount Price\' should be lessthan \'Actual Price\'.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.editbatteryprice.batdisprice.focus();
			return ;
		}

		if(document.editbatteryprice.retnprice.value == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Battery Return Price\'.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.editbatteryprice.retnprice.focus();
			return ;
         }
		 if(document.editbatteryprice.retnprice.value < 0.1)
		  {
			errMsg ="<font color='#ff3333'>Please enter valid price in\'Battery Return Price\'.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.editbatteryprice.retnprice.focus();
			return ;
         }
		 else if (document.editbatteryprice.retnprice.value.length < 2)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 2 numbers in the \'Battery Return Price\' field.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.editbatteryprice.retnprice.focus();
			return ;
         }
		 else 
		{
		var checkOK = "0123456789";
		var checkStr = document.editbatteryprice.retnprice.value;
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
			errMsg ="<font color='#ff3333'>Please enter only digits in the \'Battery Return  Price\' field.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.editbatteryprice.retnprice.focus();
			return ;
		}
		}
		if(batdisprice <= batwithprice)
		{
			errMsg ="<font color='#ff3333'>\'Battery Return Price\' should be lessthan \'Discount Price\'.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.editbatteryprice.batwithprice.focus();
			return ;
		}
		 if(document.editbatteryprice.erpprice.value == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'ERP Price\'.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.editbatteryprice.erpprice.focus();
			return ;
         }
		  if(document.editbatteryprice.erpprice.value < 0.1)
		  {
			errMsg ="<font color='#ff3333'>Please enter valid price in \'ERP Price\'.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.editbatteryprice.erpprice.focus();
			return ;
         }
		else if (document.editbatteryprice.erpprice.value.length < 2)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 2 numbers in the \'ERP Price\' field.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.editbatteryprice.erpprice.focus();
			return ;
         }
		else 
		{
		var checkOK = "0123456789";
		var checkStr = document.editbatteryprice.erpprice.value;
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
			document.editbatteryprice.erpprice.focus();
			return ;
		}
		}
		if(erpprice >= batacprice)
		{
			errMsg ="<font color='#ff3333'>\'E-Retailer Price\' should be lessthan \'Actual Price\'.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.editbatteryprice.erpprice.focus();
			return ;
		}
		if(erpprice >= batdisprice)
		{
			errMsg ="<font color='#ff3333'>\'E-Retailer Price\' should be lessthan \'Discount Price\'.</font>";
			document.getElementById("displaybatpricerrormsg").innerHTML=errMsg;
			document.editbatteryprice.erpprice.focus();
			return ;
		}
		document.editbatteryprice.method="post";
		document.editbatteryprice.action="../../../../servlet/BatteryPriceDetails?hidWhatToDo=edbattiteryprices&batacprice="+batacprice+"&batdisprice="+batdisprice+"&batwithprice="+batwithprice+"&erpprice="+erpprice+"&batid="+batpriceid;
		//alert(document.editbatteryprice.action);  
		document.editbatteryprice.submit();
}
function funToDeleteprices(batpriceid)
{
	var agree=confirm("Are You sure want to delete Battery Price details! ");
	if (agree)
	{
		document.editbatteryprice.method="post";
		document.editbatteryprice.action="../../../../servlet/BatteryPriceDetails?hidWhatToDo=deletebatteryprices&chkSi="+batpriceid;
		//alert(document.editbatteryprice.action);  
		document.editbatteryprice.submit();
	}
}
function FunReset()
{
	location.href="../../../../jsp/admin/batterystore/battery/editbatteryprice.jsp"
}
</script>

</html>