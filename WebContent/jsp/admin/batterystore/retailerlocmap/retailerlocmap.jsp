<%--
    Document   : retailerlocationmapping
    Created on : Oct 30, 2013, 4:22:12 AM
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

Vector StateVector=(Vector)session.getAttribute("sesstatevector");
LogLevel.DEBUG(1,new Throwable(),"StateVector: "+StateVector);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../../images/favicon.png" type="image/x-icon">
<title>BookBattery.com - India's No.1 Automobile Battery Store</title>
<script language="JavaScript" src="../../../../js/jquery-1.8.2.js" ></script>
<script type="text/javascript">
$(document).ready(function(){
		$category = $('#state');
		 $category.change(
            function() {
			 document.getElementById("displayappretlocmaperrormsg").innerHTML="";
			 var splitval =$category.val();
			 if(splitval == 0)
			 {
				for(i=document.retlocmap.city.options.length-1;i>=1;i--)
				{
				document.retlocmap.city.remove(i);
				}
				for(i=document.retlocmap.brand.options.length-1;i>=1;i--)
				{
				document.retlocmap.brand.remove(i);
				}
				for(i=document.retlocmap.retailer.options.length-1;i>=1;i--)
				{
				document.retlocmap.retailer.remove(i);
				}
				document.retlocmap.state.focus();
				document.retlocmap.area.value="";
			 }
			 else
			 {
				for(i=document.retlocmap.city.options.length-1;i>=1;i--)
				{
				document.retlocmap.city.remove(i);
				}
				for(i=document.retlocmap.brand.options.length-1;i>=1;i--)
				{
				document.retlocmap.brand.remove(i);
				}
				for(i=document.retlocmap.retailer.options.length-1;i>=1;i--)
				{
				document.retlocmap.retailer.remove(i);
				}
				document.retlocmap.state.focus();
				document.retlocmap.area.value="";
                $.ajax({
                    type: "GET",
                     url: "../../../../servlet/RetailerLocationMap?hidWhatToDo=getcites",
                    data: {state: $category.val() },
                    success: function(data){
                        $("#city").html(data)
						document.retlocmap.city.focus();
                    }
                });
			 }
            }
        );
	});
$(document).ready(function(){
		$category1 = $('#city');
		 $category1.change(
            function() {
			 var splitvalc =$category1.val();
			 if(splitvalc == "default")
			 {
				for(i=document.retlocmap.brand.options.length-1;i>=1;i--)
				{
				document.retlocmap.brand.remove(i);
				}
				for(i=document.retlocmap.retailer.options.length-1;i>=1;i--)
				{
				document.retlocmap.retailer.remove(i);
				}
				document.retlocmap.city.focus();
				document.retlocmap.area.value="";
			 }
			 else
			 {
				 for(i=document.retlocmap.brand.options.length-1;i>=1;i--)
				{
				document.retlocmap.brand.remove(i);
				}
				for(i=document.retlocmap.retailer.options.length-1;i>=1;i--)
				{
				document.retlocmap.retailer.remove(i);
				}
				document.retlocmap.state.focus();
				document.retlocmap.area.value="";
                $.ajax({
                    type: "GET",
                     url: "../../../../servlet/RetailerLocationMap?hidWhatToDo=getbrands",
                    data: {city: $category1.val() },
                    success: function(data){
                        $("#brand").html(data)
						document.retlocmap.brand.focus();
                    }
                });
			 }
            }
        );
	});
	$(document).ready(function(){
		$category2 = $('#brand');
		 $category2.change(
            function() {
			 var splitvalb =$category2.val();
			 if(splitvalb == "default")
			 {
				for(i=document.retlocmap.retailer.options.length-1;i>=1;i--)
				{
				document.retlocmap.retailer.remove(i);
				}
				document.retlocmap.brand.focus();
				document.retlocmap.area.value="";
			 }
			 else
			 {
				 document.retlocmap.brand.focus();
				 document.retlocmap.area.value="";
                $.ajax({
                    type: "GET",
                     url: "../../../../servlet/RetailerLocationMap?hidWhatToDo=getretailers",
                    data: {brand: $category2.val(), city: $category1.val(), state: $category.val() },
                    success: function(data){
                        $("#retailer").html(data)
						document.retlocmap.retailer.focus();
                    }
                });
			 }
            }
        );
	});
function getrets()
{
	document.retlocmap.area.focus();
	var strretailer=document.retlocmap.retailer.value;
	if(strretailer == "default")
	{
		document.retlocmap.area.value="";
		document.retlocmap.retailer.focus();
	}
	else
	{
	}
}
function funToAddAppRerLocMap(varButton)
{
	var strstate=document.retlocmap.state.value;
	var strcity=document.retlocmap.city.value;
	var strarea=document.retlocmap.area.value;
	var strbrand=document.retlocmap.brand.value;
	var strretailer=document.retlocmap.retailer.value;
	//var weekenddealer = document.getElementById("weekenddealer").value;
	weekenddealer = document.getElementById("weekenddealer").checked;
		if(weekenddealer==true)
             {
					weekenddealer="Yes";
			 }
			if(weekenddealer==false)
             {
					weekenddealer="No";
			 }
	
	var iChars3 = "`~!@#$%^&*()+=-_[]\\\';,/{}|\":<>?";
	var regularexp=/^[^\s\-].*[^\s\-]$/;
	var regularexp1=/^([0-9]+)$/;
	if(document.retlocmap.state.value == 0 || strstate == "default")
	 {
		errMsg ="<font color='#ff3333'>Please select \'State\'.</font>";
		document.getElementById("displayappretlocmaperrormsg").innerHTML=errMsg;
		document.retlocmap.state.focus();
		return ;
	 }
	 if(document.retlocmap.city.value == 0 || strcity == "default")
	 {
		errMsg ="<font color='#ff3333'>Please select \'City\'.</font>";
		document.getElementById("displayappretlocmaperrormsg").innerHTML=errMsg;
		document.retlocmap.city.focus();
		return ;
	 }
	if(document.retlocmap.brand.value == 0 || strbrand == "default")
	 {
		errMsg ="<font color='#ff3333'>Please select \'Brand\'.</font>";
		document.getElementById("displayappretlocmaperrormsg").innerHTML=errMsg;
		document.retlocmap.brand.focus();
		return ;
	 }
	 if(document.retlocmap.retailer.value == 0 || strretailer == "default" || strretailer == "defaultss")
	 {
		errMsg ="<font color='#ff3333'>Please select \'Retailer\'.</font>";
		document.getElementById("displayappretlocmaperrormsg").innerHTML=errMsg;
		document.retlocmap.retailer.focus();
		return ;
	 }

	 if(strarea == "" || document.retlocmap.area.value == 0)
		  {
			errMsg ="<font color='#ff3333'>Please enter \'Area\'.</font>";
			document.getElementById("displayappretlocmaperrormsg").innerHTML=errMsg;
			document.retlocmap.area.focus();
			return ;
         }
		if (document.retlocmap.area.value.length < 3)
         {
			errMsg ="<font color='#ff3333'>Please enter at least 3 characters in the \'Area\' field.</font>";
			document.getElementById("displayappretlocmaperrormsg").innerHTML=errMsg;
			document.retlocmap.area.focus();
			return ;
         }
		if (document.retlocmap.area.value.length > 49)
		 {
			errMsg ="<font color='#ff3333'>Please enter only 49 characters in the in the \'Area\' field.</font>";
			document.getElementById("displayappretlocmaperrormsg").innerHTML=errMsg;
			document.retlocmap.area.focus();
			return ;
         }
		 if (document.retlocmap.area.value.indexOf(' ')==0 )
		 {
			 errMsg ="<font color='#ff3333'>Only spaces or space before text is not allowed in the \'Area\' field.</font>";
			document.getElementById("displayappretlocmaperrormsg").innerHTML=errMsg;
			document.retlocmap.area.focus();
			return ;
         }
		for (var i = 0; i < document.retlocmap.area.value.length; i++)
		{
         if (iChars3.indexOf(document.retlocmap.area.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#ff3333'>Special characters are not allowed in \'Area\' field.</font>";
			document.getElementById("displayappretlocmaperrormsg").innerHTML=errMsg;
			document.retlocmap.locaton.focus();
			return ;
         }
		}
		if (document.retlocmap.area.value.match(regularexp1))
		{
			errMsg ="<font color='#ff3333'>Please enter only Characters in the \'Area\' field.</font>";
			document.getElementById("displayappretlocmaperrormsg").innerHTML=errMsg;
			document.retlocmap.area.focus();
			return ;
		}
		if (! document.retlocmap.area.value.match(regularexp))
		{
			errMsg ="<font color='#ff3333'>\'Area\' has Unnecessary spaces. Please remove spaces.</font>";
			document.getElementById("displayappretlocmaperrormsg").innerHTML=errMsg;
			document.retlocmap.area.focus();
			return ;
		}

	document.retlocmap.method="post";
	document.retlocmap.action="../../../../servlet/RetailerLocationMap?hidWhatToDo=insertretlocmap&state="+strstate+"&city="+strcity+"&area="+strarea+"&retailer="+strretailer+"&weekenddealer="+weekenddealer+"&brand="+strbrand;
	document.retlocmap.submit();
	varButton.disabled=true;
}
function FunReset()
{
	location.href="../../../../jsp/admin/batterystore/retailerlocmap/retailerlocmap.jsp"
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
<body onload="document.retlocmap.state.focus(); noBack();" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="retlocmap">
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
								<td class="subheading">Add&nbsp;Retailer&nbsp;Location&nbsp;Mapping</td>
								<td  align="right"><a href="../../../../servlet/AdminBatteryLogin?hidWhatToDo=batteryadminhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
							</tr>
							<tr><td height="10"></td></tr>
							<tr><td align="center" class="insidecontentbat"><div id="displayappretlocmaperrormsg"></div></td></tr>
							<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
								<tr>
									<td width="39%" class="insidecontent" align="right">Select&nbsp;State<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">
									<select name='state' id='state' onChange="javascript:getcities();" class="insidecontent" style='width:180px'>
										<option value='0'>&lt;-&nbsp;Select&nbsp;State&nbsp;-&gt;</option>
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
									<td width="39%" class="insidecontent" align="right">Select&nbsp;City<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">
										<select name="city" id ="city"  class="insidecontent" style='width:180px'>
											<option value="0" >&lt;-&nbsp;Select City&nbsp;-&gt;</option>
										</select>
									</td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Select&nbsp;Brand<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left"><select name="brand" id ="brand" class="insidecontent" style='width:180px'>
									<option value="0" >&lt;-&nbsp;Select Brand&nbsp;-&gt;</option>
										</select></td>
								</tr>
									<tr>
									<td width="39%" class="insidecontent" align="right">Select&nbsp;Retailer<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left"><select name="retailer" id ="retailer" onChange="javascript:getrets();" class="insidecontent" style='width:180px'>
									<option value="0" >&lt;-&nbsp;Select Retailer&nbsp;-&gt;</option>
										</select></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Enter&nbsp;Area<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="area" size="20" maxlength="50"></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Weekend&nbsp;Dealer<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left"><input type="radio" id="weekenddealer" name="weekenddealer" value="Yes" >Yes&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" id="weekenddealer"  name="weekenddealer" value="No" checked>No</td>
									
								</tr>
								</td>
								</tr>
								</table>
								<tr>
								<td>
								<table width="100%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
								<tr>
									<td width="50%" class="insidecontent" align="right"><input type="button" value="Submit" class="button4" onclick="javascript:funToAddAppRerLocMap(this);"></td>
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
String strLogmapMsg=(String)session.getAttribute("sesretlocErrorMsg");
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
document.getElementById("displayappretlocmaperrormsg").innerHTML=loginmpamessg;
</script>
<%
	session.removeAttribute("sesretlocErrorMsg");
}
%>
</body>
</html>