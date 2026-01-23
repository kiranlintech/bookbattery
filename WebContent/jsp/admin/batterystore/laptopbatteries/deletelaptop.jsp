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

Vector LaptopBatterytypeVector=(Vector)session.getAttribute("sesLaptopBatteryTypeVector");
LogLevel.DEBUG(1,new Throwable(),"LaptopBatterytypeVector: "+LaptopBatterytypeVector);
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
		$category3 = $('#batterytype');
        $category3.change(
            function() {
			 var splitvalb =$category3.val();
			 if(splitvalb == "0")
			 {
				 $('#divdeletelaptop').hide();
				for(i=document.deletelaptop.make.options.length-1;i>=1;i--)
				{
					document.deletelaptop.make.remove(i);
				}
				for(i=document.deletelaptop.model.options.length-1;i>=1;i--)
				{
					document.deletelaptop.model.remove(i);
				}
				document.deletelaptop.batterytype.focus();
			 }
			 else
			 {
				$('#divdeletelaptop').hide();
				document.getElementById("displayappdeletelaptoperrormsg").innerHTML ="";
                $.ajax({
                    type: "GET",
                     url: "../../../../servlet/AddLaptopDetails?hidWhatToDo=getlaptopmakes",
                    data: {batterytype: $category3.val() },
                    success: function(data){
                        $("#make").html(data)
						document.deletelaptop.make.focus();
                    }
                });
			 }
            }
        );
	});
	$(document).ready(function(){
		$category31 = $('#make');
        $category31.change(
            function() {
			 var splitvalb =$category31.val();
			 if(splitvalb == "default")
			 {
				 $('#divdeletelaptop').hide();
				for(i=document.deletelaptop.model.options.length-1;i>=1;i--)
				{
					document.deletelaptop.model.remove(i);
				}
				document.deletelaptop.make.focus();
			 }
			 else
			 {
				$('#divdeletelaptop').hide();
				document.getElementById("displayappdeletelaptoperrormsg").innerHTML ="";
                $.ajax({
                    type: "GET",
                     url: "../../../../servlet/AddLaptopDetails?hidWhatToDo=getlaptopmodels",
                    data: {batterytype: $category3.val(),make: $category31.val() },
                    success: function(data){
                        $("#model").html(data)
						document.deletelaptop.model.focus();
                    }
                });
			 }
            }
        );
	});
function getlaptopsproducts()
{
	var batterytype = document.deletelaptop.batterytype.value;
	var make = document.deletelaptop.make.value;
	var model = document.deletelaptop.model.value;

	
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
					$('#divdeletelaptop').show();
					resp = xmlhttp.responseText;
					//alert(resp);
					if(resp=="ERROR")
					{
						alert("Error occurred.Please try again.");
						return;
					}
					document.getElementById("divdeletelaptop").innerHTML = resp;
					document.getElementById("divdeletelaptop").innerHTML = xmlhttp.responseText;
				}
			}			
		}
		xmlhttp.open("POST","../../../../servlet/AddLaptopDetails?hidWhatToDo=getlaptopsproducts&batterytype="+batterytype+"&make="+make+"&model="+model,true);		
		xmlhttp.send();	
}
function deletelaptops(lapid,laptopproduct)
{
	var batterytype = document.deletelaptop.batterytype.value;
	var make = document.deletelaptop.make.value;
	var model = document.deletelaptop.model.value;

	var xmlhttp= "";
	var resp= "";
	var url ="../../../../servlet/AddLaptopDetails?hidWhatToDo=deletelaptops&batterytype="+batterytype+"&make="+make+"&model="+model+"&laptopproduct="+laptopproduct+"&chkSi="+lapid;
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
					document.getElementById("displayappdeletelaptoperrormsg").innerHTML = resp;
					document.getElementById("displayappdeletelaptoperrormsg").innerHTML = xmlhttp.responseText;
					getlaptopsproducts()
				}
			}			
		}
		var agree=confirm("Are You sure want to delete laptop product  details! ");
		if (agree)
		{

				
		xmlhttp.open("GET",url, true);		
		xmlhttp.send();	

		}
}
function FunReset()
{
	location.href="../../../../jsp/admin/batterystore/laptopBookBattery/deletelaptop.jsp"
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
<body onload="document.deletelaptop.batterytype.focus(); noBack();" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="deletelaptop">
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
								<td class="subheading">Delete&nbsp;Laptops</td>
								<td  align="right"><a href="../../../../servlet/AdminBatteryLogin?hidWhatToDo=batteryadminhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
							</tr>
							<tr><td height="10"></td></tr>
							<tr><td align="center" class="insidecontentbat"><div id="displayappdeletelaptoperrormsg"></div></td></tr>
							<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
								<tr>
									<td width="39%" class="insidecontent" align="right">Select&nbsp;Battery&nbsp;Type<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">
									<select name='batterytype' id='batterytype'  class="insidecontent" style='width:180px'>
										<option value='0'>&lt;-&nbsp;Select&nbsp;Battery&nbsp;Type&nbsp;-&gt;</option>
										<%
										try
										   {
											if(LaptopBatterytypeVector!=null && LaptopBatterytypeVector.size()>0)
											{
											for(int i=0;i<LaptopBatterytypeVector.size();i++)
											{
												Hashtable ht=(Hashtable)LaptopBatterytypeVector.get(i);
												String batterytype=(String)ht.get("battery_type");
										%>
										<option value="<%=batterytype%>"><%=batterytype%></option>
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
									<td width="39%" class="insidecontent" align="right">Select&nbsp;Make<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">
										<select name="make" id ="make"class="insidecontent" style='width:180px'>
											<option value="0" >&lt;-&nbsp;Select Make&nbsp;-&gt;</option>
										</select>
									</td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Select&nbsp;Model<font color="FF0000">*</font>&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left">
										<select name="model" id ="model" onChange="javascript:getlaptopsproducts();" class="insidecontent" style='width:180px'>
											<option value="0" >&lt;-&nbsp;Select Model&nbsp;-&gt;</option>
										</select>
									</td>
								</tr>
								</td>
								</tr>
								</table>
								<tr><td height="10"></td></tr>
								
								<tr>
									<td>
										<table width="100%" border="0" align="center">
											<tr class="#FFFFFF" bgcolor="FFFFFF">
												<td width="40%"><div id="divdeletelaptop"></div></td>
											</tr>
										</table>
									</td>
								</tr>
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
document.getElementById("displayappdeletelaptoperrormsg").innerHTML=loginmpamessg;
</script>
<%
	session.removeAttribute("sesretlocErrorMsg");
}
%>
</body>
</html>