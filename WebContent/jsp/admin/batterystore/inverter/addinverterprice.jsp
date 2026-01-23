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
Vector brandVector=(Vector)session.getAttribute("sesinverterbrandsvector");
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
<body onload="document.addinverterprice.invbrand.focus();" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="addinverterprice" action="request_for_quote.asp"  method="post">
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
				<!-- Ends battery admin left Menu here -->
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
								<td class="subheading">Add&nbsp;Inverter&nbsp;Price</td>
								<td  align="right"><a href="../../../../servlet/AdminBatteryLogin?hidWhatToDo=batteryadminhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
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
										<select name="invcapacity" id ="invcapacity"  class="insidecontent" style='width:180px' >
											<option value="0" >&lt;-&nbsp;Select&nbsp;Inverter&nbsp;Capacity-&gt;</option>
										</select>
									</td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Select&nbsp;Inverter&nbsp;Model<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">
										<select name="invmodel" id ="invmodel"  class="insidecontent" style='width:180px' onchange="getstates();">
											<option value="0" >&lt;-&nbsp;Select&nbsp;Inverter&nbsp;Model-&gt;</option>
										</select>
									</td>
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
										<select name="city" id="city" class="insidecontent" STYLE="width: 180px" onchange="document.addinverterprice.batacprice.focus();">
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
									<td width="39%" class="insidecontent" align="right">E-Retailer&nbsp;Price[ERP]<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="erpprice" size="20" maxlength="7"><span class="hint">Provide ERP Price in the format '1000'.<span class="hint-pointer">&nbsp;</span></span></td>
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
		$category2 = $('#invbrand');
		 $category2.change(
            function() {
			 var splitvalb =$category2.val();
			 if(splitvalb == "default" || splitvalb == 0)
			 {
				for(i=document.addinverterprice.invcapacity.options.length-1;i>=1;i--)
				{
				document.addinverterprice.invcapacity.remove(i);
				}
				for(i=document.addinverterprice.state.options.length-1;i>=1;i--)
				{
				document.addinverterprice.state.remove(i);
				}
				for(i=document.addinverterprice.city.options.length-1;i>=1;i--)
				{
				document.addinverterprice.city.remove(i);
				}
				document.addinverterprice.invbrand.focus();
				document.addinverterprice.batacprice.value="";
				document.addinverterprice.batdisprice.value="";
				document.addinverterprice.batwithprice.value="";
			 }
			 else
			 {
				 
				for(i=document.addinverterprice.invcapacity.options.length-1;i>=1;i--)
				{
				document.addinverterprice.invcapacity.remove(i);
				}
				for(i=document.addinverterprice.city.options.length-1;i>=1;i--)
				{
				document.addinverterprice.city.remove(i);
				}
				document.addinverterprice.batacprice.value="";
				document.addinverterprice.batdisprice.value="";
				document.addinverterprice.erpprice.value="";
				
                $.ajax({
                    type: "GET",
                     url: "../../../../servlet/InverterDetails?hidWhatToDo=getinvcapcity",
                    data: {brand: $category2.val() },
                    success: function(data){
                        $("#invcapacity").html(data)
						document.addinverterprice.invcapacity.focus();
                    }
                });
			 }
            }
        );
	});

	/** function to get inverter models **/

	$(document).ready(function(){
		$category3 = $('#invcapacity');
		 $category3.change(
            function() {
			 var splitvalb =$category3.val();
			 if(splitvalb == "default" || splitvalb == 0)
			 {
				for(i=document.addinverterprice.invmodel.options.length-1;i>=1;i--)
				{
				document.addinverterprice.invmodel.remove(i);
				}
				for(i=document.addinverterprice.state.options.length-1;i>=1;i--)
				{
				document.addinverterprice.state.remove(i);
				}
				for(i=document.addinverterprice.city.options.length-1;i>=1;i--)
				{
				document.addinverterprice.city.remove(i);
				}
				document.addinverterprice.invbrand.focus();
				document.addinverterprice.batacprice.value="";
				document.addinverterprice.batdisprice.value="";
				document.addinverterprice.batwithprice.value="";
			 }
			 else
			 {
				 
				for(i=document.addinverterprice.invmodel.options.length-1;i>=1;i--)
				{
				document.addinverterprice.invmodel.remove(i);
				}
				for(i=document.addinverterprice.city.options.length-1;i>=1;i--)
				{
				document.addinverterprice.city.remove(i);
				}
				document.addinverterprice.batacprice.value="";
				document.addinverterprice.batdisprice.value="";
				document.addinverterprice.erpprice.value="";
				
                $.ajax({
                    type: "GET",
                     url: "../../../../servlet/InverterDetails?hidWhatToDo=getinvmodels",
                    data: {brand: $category2.val(), invcapacity: $category3.val() },
                    success: function(data){
                        $("#invmodel").html(data)
						document.addinverterprice.invmodel.focus();
                    }
                });
			 }
            }
        );
	});


		$(document).ready(function(){
		$category31 = $('#invmodel');
		 $category31.change(
            function() {
			 var splitvalbc =$category31.val();
			 if(splitvalbc == "default" || splitvalbc == 0)
			 {
				for(i=document.addinverterprice.batmodel.options.length-1;i>=1;i--)
				{
				document.addinverterprice.batmodel.remove(i);
				}
				for(i=document.addinverterprice.state.options.length-1;i>=1;i--)
				{
				document.addinverterprice.state.remove(i);
				}
				for(i=document.addinverterprice.city.options.length-1;i>=1;i--)
				{
				document.addinverterprice.city.remove(i);
				}
			 }
			 else
			 {
					
					$("#state").html("<option value='Andhra Pradesh'>Andhra Pradesh</option><option value='Arunachal Pradesh'>Arunachal Pradesh</option><option value='Assam'>Assam</option><option value='Bihar'>Bihar</option><option value='Chattisgarh'>Chattisgarh</option><option value='Delhi'>Delhi</option><option value='Goa'>Goa</option><option value='Gujarat'>Gujarat</option><option value='Haryana'>Haryana</option><option value='Himachal Pradesh'>Himachal Pradesh</option><option value='Jammu and Kashmir'>Jammu and Kashmir</option><option value='Jharkhand'>Jharkhand</option><option value='Karnataka'>Karnataka</option><option value='Kerala'>Kerala</option><option value='Madhya Pradesh'>Madhya Pradesh</option><option value='Maharashtra'>Maharashtra</option><option value='Manipur'>Manipur</option><option value='Meghalaya'>Meghalaya</option><option value='Mizoram'>Mizoram</option><option value='Nagaland'>Nagaland</option><option value='Orissa'>Orissa</option><option value='Punjab'>Punjab</option><option value='Rajasthan'>Rajasthan</option>option value='Sikkim'>Sikkim</option><option value='Tamil Nadu'>Tamil Nadu</option><option value='Telangana'>Telangana</option><option value='Tripura'>Tripura</option><option value='Union Territories'>Union Territories</option><option value='Uttarakhand'>Uttarakhand</option><option value='Uttar Pradesh'>Uttar Pradesh</option><option value='West Bengal'>West Bengal</option>");
					
				$('#state').attr('multiple', true);
				$('#state').val(''); 
				$('#state').multiselect('destroy');
				$('#state').multiselect({
				includeSelectAllOption: true,	
				buttonWidth: '180px',
				enableFiltering: true,
				maxHeight: 350 
				});
					
					
			 }
            }
        );
	});
	

	$(document).ready(function(){
		$category4 = $('#state');
		 $category4.change(
            function() {
			 var splitvalb =$category4.val();
			 if(splitvalb == "0")
			 {
				for(i=document.addinverterprice.city.options.length-1;i>=1;i--)
				{
					document.addinverterprice.city.remove(i);
				}
				document.addinverterprice.state.focus();
				document.addinverterprice.batacprice.value="";
				document.addinverterprice.batdisprice.value="";
				document.addinverterprice.erpprice.value="";
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
				
				
				
				document.addinverterprice.batacprice.value="";
				document.addinverterprice.batdisprice.value="";
				document.addinverterprice.erpprice.value="";
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
						document.addinverterprice.city.focus();
                    }
                });
			 }
            }
        );
	});

	
function funToAddAppInvPrice()    
{
	var invbrand = document.addinverterprice.invbrand.value; 
	var invcapacity = document.addinverterprice.invcapacity.value;
	var invmodel = document.addinverterprice.invmodel.value; 
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
	document.addinverterprice.stateselected.value = stateselect; 
	//alert(document.addinverterprice.stateselected.value);
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
	document.addinverterprice.cityselected.value = cityselect; 
	//alert(document.addinverterprice.cityselected.value);
	}
	var batacprice = parseFloat(document.addinverterprice.batacprice.value);
	var batdisprice = parseFloat(document.addinverterprice.batdisprice.value); 
	var erpprice = parseFloat(document.addinverterprice.erpprice.value);  

	if(document.addinverterprice.invbrand.value == 0 || invbrand == "default")
	 {
		errMsg ="<font color='#ff3333'>Please select \'Inverter Brand\'.</font>";
		document.getElementById("displayinvpricerrormsg").innerHTML=errMsg;
		document.addinverterprice.invbrand.focus();
		return ;
	 }
	 if(document.addinverterprice.invcapacity.value == 0 || invcapacity == "default")
	 {
		errMsg ="<font color='#ff3333'>Please select \'inverter Capacity\'.</font>";
		document.getElementById("displayinvpricerrormsg").innerHTML=errMsg;
		document.addinverterprice.invcapacity.focus();
		return ;
	 }
	  if(document.addinverterprice.invmodel.value == 0 || invmodel == "default")
	 {
		errMsg ="<font color='#ff3333'>Please select \'inverter Model\'.</font>";
		document.getElementById("displayinvpricerrormsg").innerHTML=errMsg;
		document.addinverterprice.invmodel.focus();
		return ;
	 }
	if(document.addinverterprice.state.value == 0 || state == "default")
	 {
		errMsg ="<font color='#ff3333'>Please select \'State\'.</font>";
		document.getElementById("displayinvpricerrormsg").innerHTML=errMsg;
		document.addinverterprice.state.focus();
		return ;
     }
	if(document.addinverterprice.city.value == 0 || city == "default")
	 {
		errMsg ="<font color='#ff3333'>Please select \'City\'.</font>";
		document.getElementById("displayinvpricerrormsg").innerHTML=errMsg;
		document.addinverterprice.city.focus();
		return ;
     }
	  if(document.addinverterprice.batacprice.value == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Actual Price\'.</font>";
			document.getElementById("displayinvpricerrormsg").innerHTML=errMsg;
			document.addinverterprice.batacprice.focus();
			return ;
         }
		else if(document.addinverterprice.batacprice.value < 0.1)
		{
			errMsg ="<font color='#ff3333'>Please enter valid price in \'Actual Price\'.</font>";
			document.getElementById("displayinvpricerrormsg").innerHTML=errMsg;
			document.addinverterprice.batacprice.focus();
			return ;
		}
		else if (document.addinverterprice.batacprice.value.length < 2)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 2 numbers in the \'Actual Price\' field.</font>";
			document.getElementById("displayinvpricerrormsg").innerHTML=errMsg;
			document.addinverterprice.batacprice.focus();
			return ;
         }
		 else if(batacprice < 100)
		{
			errMsg ="<font color='#ff3333'>\'Actual Price\' should not lessthan Rs 100.</font>";
			document.getElementById("displayinvpricerrormsg").innerHTML=errMsg;
			document.addinverterprice.batacprice.focus();
			return ;
		}
		else 
		{
		var checkOK = "0123456789";
		var checkStr = document.addinverterprice.batacprice.value;
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
			document.getElementById("displayinvpricerrormsg").innerHTML=errMsg;
			document.addinverterprice.batacprice.focus();
			return ;
		}
		}
		if(document.addinverterprice.batdisprice.value == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Discount Price\'.</font>";
			document.getElementById("displayinvpricerrormsg").innerHTML=errMsg;
			document.addinverterprice.batdisprice.focus();
			return ;
         }
		 else if(document.addinverterprice.batdisprice.value < 0.1)
		{
			errMsg ="<font color='#ff3333'>Please enter valid price in \'Discount Price\'.</font>";
			document.getElementById("displayinvpricerrormsg").innerHTML=errMsg;
			document.addinverterprice.batdisprice.focus();
			return ;
		}
		 else if (document.addinverterprice.batdisprice.value.length < 2)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 2 numbers in the \'Discount Price\' field.</font>";
			document.getElementById("displayinvpricerrormsg").innerHTML=errMsg;
			document.addinverterprice.batdisprice.focus();
			return ;
         }
		 else 
		{
		var checkOK = "0123456789";
		var checkStr = document.addinverterprice.batdisprice.value;
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
			document.getElementById("displayinvpricerrormsg").innerHTML=errMsg;
			document.addinverterprice.batdisprice.focus();
			return ;
		}
		}
		if(batacprice <= batdisprice)
		{
			errMsg ="<font color='#ff3333'>\'Discount Price\' should be lessthan \'Actual Price\'.</font>";
			document.getElementById("displayinvpricerrormsg").innerHTML=errMsg;
			document.addinverterprice.batdisprice.focus();
			return ;
		}

		if(document.addinverterprice.erpprice.value == "")
		  {
			errMsg ="<font color='#ff3333'>Please enter \'ERP Price\'.</font>";
			document.getElementById("displayinvpricerrormsg").innerHTML=errMsg;
			document.addinverterprice.erpprice.focus();
			return ;
         }
		 if(batacprice <= erpprice)
		{
			errMsg ="<font color='#ff3333'>\'E-Retailer Price\' should be lessthan \'Actual Price\'.</font>";
			document.getElementById("displayinvpricerrormsg").innerHTML=errMsg;
			document.addinverterprice.erpprice.focus();
			return ;
		}
		 if(batdisprice <= erpprice)
		{
			errMsg ="<font color='#ff3333'>\'E-Retailer Price\' should be lessthan \'Discount Price\'.</font>";
			document.getElementById("displayinvpricerrormsg").innerHTML=errMsg;
			document.addinverterprice.erpprice.focus();
			return ;
		}
		 if(erpprice >= batacprice)
		{
			errMsg ="<font color='#ff3333'>\'E-Retailer Price\' should be lessthan \'Actual Price\'.</font>";
			document.getElementById("displayinvpricerrormsg").innerHTML=errMsg;
			document.addinverterprice.erpprice.focus();
			return ;
		}
		 if(erpprice >= batdisprice)
		{
			errMsg ="<font color='#ff3333'>\'E-Retailer Price\' should be lessthan \'Discount Price\'.</font>";
			document.getElementById("displayinvpricerrormsg").innerHTML=errMsg;
			document.addinverterprice.erpprice.focus();
			return ;
		}
		else if(document.addinverterprice.erpprice.value < 0.1)
		{
			errMsg ="<font color='#ff3333'>Please enter valid price in \'ERP Price\'.</font>";
			document.getElementById("displayinvpricerrormsg").innerHTML=errMsg;
			document.addinverterprice.erpprice.focus();
			return ;
		}
		else if (document.addinverterprice.erpprice.value.length < 2)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 2 numbers in the \'ERP Price\' field.</font>";
			document.getElementById("displayinvpricerrormsg").innerHTML=errMsg;
			document.addinverterprice.erpprice.focus();
			return ;
         }
		else 
		{
		var checkOK = "0123456789";
		var checkStr = document.addinverterprice.erpprice.value;
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
			document.getElementById("displayinvpricerrormsg").innerHTML=errMsg;
			document.addinverterprice.erpprice.focus();
			return ;
		}
		}
		//document.addinverterprice.method="post";
		//document.addinverterprice.action="../../../../servlet/InverterDetails?hidWhatToDo=insertinverterprices&invbrand="+invbrand+"&invcapacity="+invcapacity+"&state="+state+"&city="+city+"&batacprice="+batacprice+"&batdisprice="+batdisprice+"&erpprice="+erpprice+"&invmodel="+invmodel;
		//alert(document.addinverterprice.action);  
		//document.addinverterprice.submit();     
		addinverterprice.action = "../../../../servlet/InverterDetails?hidWhatToDo=insertinverterprices";

		addinverterprice.method = "POST";

		addinverterprice.submit();


		
}
function FunReset()
{
	location.href="../../../../jsp/admin/batterystore/inverter/addinverterprice.jsp"
}
function getstates()
{
	var invcapacity=document.addinverterprice.invcapacity.value;
	if(invcapacity=="default")
	{
				for(i=document.addinverterprice.state.options.length-1;i>=1;i--)
				{
				document.addinverterprice.state.remove(i);
				}
				for(i=document.addinverterprice.city.options.length-1;i>=1;i--)
				{
				document.addinverterprice.city.remove(i);
				}
				$("#state").html("<option value='0' >&lt;-&nbsp;&nbsp; Select State -&gt;</option><option value='Andhra Pradesh'>Andhra Pradesh</option><option value='Arunachal Pradesh'>Arunachal Pradesh</option><option value='Assam'>Assam</option<option value='Bihar'>Bihar</option><option value='Chattisgarh'>Chattisgarh</option><option value='Delhi'>Delhi</option><option value='Goa'>Goa</option><option value='Gujarat'>Gujarat</option><option value='Haryana'>Haryana</option><option value='Himachal Pradesh'>Himachal Pradesh</option><option value='Jammu and Kashmir'>Jammu and Kashmir</option><option value='Jharkhand'>Jharkhand</option><option value='Karnataka'>Karnataka</option><option value='Kerala'>Kerala</option><option value='Madhya Pradesh'>Madhya Pradesh</option><option value='Maharashtra'>Maharashtra</option><option value='Manipur'>Manipur</option><option value='Meghalaya'>Meghalaya</option><option value='Mizoram'>Mizoram</option><option value='Nagaland'>Nagaland</option><option value='Orissa'>Orissa</option><option value='Punjab'>Punjab</option><option value='Rajasthan'>Rajasthan</option>option value='Sikkim'>Sikkim</option><option value='Tamil Nadu'>Tamil Nadu</option><option value='Telangana'>Telangana</option><option value='Tripura'>Tripura</option><option value='Union Territories'>Union Territories</option><option value='Uttarakhand'>Uttarakhand</option><option value='Uttar Pradesh'>Uttar Pradesh</option><option value='West Bengal'>West Bengal</option>");
				document.addinverterprice.invcapacity.focus();
				document.addinverterprice.batacprice.value="";
				document.addinverterprice.batdisprice.value="";
				document.addinverterprice.batwithprice.value="";
	}
	else
	{
		document.addinverterprice.state.focus();
	}
}
</script>
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

</table>
<input type="hidden" name="strEmail" value="">
</form>
</center>
<%
String strLogmapMsg=(String)session.getAttribute("sesinverterErrorMsg");
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
	session.removeAttribute("sesinverterErrorMsg");
}
%>
</body>
</html>