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

String keyword = (request.getParameter("keyword")!=null)?(request.getParameter("keyword")):""; 
LogLevel.DEBUG(5,new Throwable(),"keyword :"+keyword );
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
			 document.getElementById("displayappretpincodeerrormsg").innerHTML="";
			 var splitval =$category.val();
			 if(splitval == 0)
			 {
				for(i=document.retpincode.city.options.length-1;i>=1;i--)
				{
				document.retpincode.city.remove(i);
				}
				for(i=document.retpincode.brand.options.length-1;i>=1;i--)
				{
				document.retpincode.brand.remove(i);
				}
				for(i=document.retpincode.retailer.options.length-1;i>=1;i--)
				{
				document.retpincode.retailer.remove(i);
				}
				document.retpincode.state.focus();
				document.retpincode.pincode.value="";
			 }
			 else
			 {
				 for(i=document.retpincode.brand.options.length-1;i>=1;i--)
				{
				document.retpincode.brand.remove(i);
				}
				for(i=document.retpincode.retailer.options.length-1;i>=1;i--)
				{
				document.retpincode.retailer.remove(i);
				}
                $.ajax({
                    type: "GET",
                     url: "../../../../servlet/RetailerLocationMap?hidWhatToDo=getcites",
                    data: {state: $category.val() },
                    success: function(data){
                        $("#city").html(data)
						document.retpincode.city.focus();
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
				for(i=document.retpincode.brand.options.length-1;i>=1;i--)
				{
				document.retpincode.brand.remove(i);
				}
				for(i=document.retpincode.retailer.options.length-1;i>=1;i--)
				{
				document.retpincode.retailer.remove(i);
				}
				document.retpincode.city.focus();
				document.retpincode.pincode.value="";
			 }
			 else
			 {

				 for(i=document.retpincode.retailer.options.length-1;i>=1;i--)
				{
				document.retpincode.retailer.remove(i);
				}
                $.ajax({
                    type: "GET",
                     url: "../../../../servlet/RetailerLocationMap?hidWhatToDo=getbrands",
                    data: {city: $category1.val() },
                    success: function(data){
                        $("#brand").html(data)
						document.retpincode.brand.focus();
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
				for(i=document.retpincode.retailer.options.length-1;i>=1;i--)
				{
				document.retpincode.retailer.remove(i);
				}
				document.retpincode.brand.focus();
				document.retpincode.pincode.value="";
			 }
			 else
			 {
                $.ajax({
                    type: "GET",
                     url: "../../../../servlet/RetailerLocationMap?hidWhatToDo=getretailers",
                    data: {brand: $category2.val(), city: $category1.val(), state: $category.val() },
                    success: function(data){
                        $("#retailer").html(data)
						document.retpincode.retailer.focus();
                    }
                });
			 }
            }
        );
	});
function getrets()
{
	document.retpincode.pincode.focus();
	var strretailer=document.retpincode.retailer.value;
	if(strretailer == "default")
	{
		document.retpincode.pincode.value="";
		document.retpincode.retailer.focus();
	}
	else
	{
	}
}
function funToAddAppRerLocMap(varButton)
{
	var strstate=document.retpincode.state.value;
	var strcity=document.retpincode.city.value;
	var strbrand=document.retpincode.brand.value; 
	var strretailer=document.retpincode.retailer.value;
	var pincode=document.retpincode.pincode.value;

	var keyword=document.retpincode.keyword.value;
//	var weekenddealer = document.getElementById("weekenddealer").value;
	weekenddealer = document.getElementById("weekenddealer").checked;
		if(weekenddealer==true)
             {
					weekenddealer="Yes";
			 }
			if(weekenddealer==false)
             {
					weekenddealer="No";
			 }
	
	if(document.retpincode.state.value == 0 || strstate == "default")
	 {
		errMsg ="<font color='#ff3333'>Please select \'State\'.</font>";
		document.getElementById("displayappretpincodeerrormsg").innerHTML=errMsg;
		document.retpincode.state.focus();
		return ;
	 }
	 if(document.retpincode.city.value == 0 || strcity == "default")
	 {
		errMsg ="<font color='#ff3333'>Please select \'City\'.</font>";
		document.getElementById("displayappretpincodeerrormsg").innerHTML=errMsg;
		document.retpincode.city.focus();
		return ;
	 }
	 if(document.retpincode.brand.value == 0 || strbrand == "default")
	 {
		errMsg ="<font color='#ff3333'>Please select \'Brand\'.</font>";
		document.getElementById("displayappretpincodeerrormsg").innerHTML=errMsg;
		document.retpincode.brand.focus();
		return ;
	 }
	 if(document.retpincode.retailer.value == 0 || strretailer == "default" || strretailer == "defaultss")
	 {
		errMsg ="<font color='#ff3333'>Please select \'Retailer\'.</font>";
		document.getElementById("displayappretpincodeerrormsg").innerHTML=errMsg;
		document.retpincode.retailer.focus();
		return ;
	 }
	 if(document.retpincode.pincode.value == "" || pincode=="default")
		{
		errMsg ="<font color='#ff3333'>Please Enter  \'Pincode\'.</font>";
		document.getElementById("displayappretpincodeerrormsg").innerHTML=errMsg;
		document.retpincode.pincode.focus();
		return ;
		}
	 var pincoderegex=/^[0-9]{4,6}$/;
		if(!pincode.match(pincoderegex))
		{
		errMsg ="<font color='#ff3333'>Please Enter Valid \'Pincode\'.</font>";
		document.getElementById("displayappretpincodeerrormsg").innerHTML=errMsg;
		document.retpincode.pincode.focus();
		return ;
		}

	document.retpincode.method="post";
	document.retpincode.action="../../../../servlet/RetailerLocationMap?hidWhatToDo=insertretlocmap&state="+strstate+"&city="+strcity+"&pincode="+pincode+"&keyword="+keyword+"&retailer="+strretailer+"&weekenddealer="+weekenddealer;
	document.retpincode.submit();
	varButton.disabled=true;
}
function FunReset()
{
	location.href="../../../../servlet/RetailerLocationMap?hidWhatToDo=getstates&keyword=pin"
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
<body onload="document.retpincode.state.focus(); noBack();" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="retpincode">
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
								<td class="subheading">Add&nbsp;Retailer&nbsp;Pincode&nbsp;Mapping</td>
								<td  align="right"><a href="../../../../servlet/AdminBatteryLogin?hidWhatToDo=batteryadminhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
							</tr>
							<tr><td height="10"></td></tr>
							<tr><td align="center" class="insidecontentbat"><div id="displayappretpincodeerrormsg"></div></td></tr>
							<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
								<tr>
									<td width="39%" class="insidecontent" align="right">Select&nbsp;State<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">
									<select name='state' id='state' class="insidecontent" style='width:180px'>
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
										<select name="city" id ="city" class="insidecontent" style='width:180px'>
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
									<td width="39%" class="insidecontent" align="right">Enter&nbsp;Pincode&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="pincode" size="20" maxlength="7"></td>
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
<input type="hidden" name="keyword" value="<%=keyword%>">
</form>
</center>
<%
String strLogmapMsg=(String)session.getAttribute("sesretpioncErrorMsg");
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
document.getElementById("displayappretpincodeerrormsg").innerHTML=loginmpamessg;
</script>
<%
	session.removeAttribute("sesretpioncErrorMsg");
}
%>
</body>
</html>