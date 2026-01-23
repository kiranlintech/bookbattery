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
<script language="javascript">
$(document).ready(function(){
		$category = $('#state');
		 $category.change(
            function() {
			 var splitval =$category.val();
			 if(splitval == 0)
			 {
				for(i=document.addpincode.city.options.length-1;i>=1;i--)
				{
				document.addpincode.city.remove(i);
				}
				document.addpincode.state.focus();
				document.addpincode.areacode.value="";
			 }
			 else
			 {
                $.ajax({
                    type: "GET",
                     url: "../../../../servlet/RetailerLocationMap?hidWhatToDo=getcites",
                    data: {state: $category.val() },
                    success: function(data){
                        $("#city").html(data)
						document.addpincode.city.focus();
                    }
                });
			 }
            }
        );
	});
function funToAddAreacode()   
{
		var state = document.addpincode.state.value;
		var city = document.addpincode.city.value;
		var areacode = document.addpincode.areacode.value;
		var keyword = document.addpincode.keyword.value;
			
		if(document.addpincode.state.value == 0 || state == "default")
		{
			errMsg ="<font color='#ff3333'>Please enter \'State\'.</font>";
			document.getElementById("displaypincodeerrormsg").innerHTML=errMsg;
			document.addpincode.state.focus();
			return ;
		}
		if(document.addpincode.city.value == 0 || city == "default")
		{
			errMsg ="<font color='#ff3333'>Please enter \'City\'.</font>";
			document.getElementById("displaypincodeerrormsg").innerHTML=errMsg;
			document.addpincode.city.focus();
			return ;
		}
		if(document.addpincode.areacode.value == "" || areacode=="default")
		{
		errMsg ="<font color='#ff3333'>Please Enter  \'Pincode\'.</font>";
		document.getElementById("displaypincodeerrormsg").innerHTML=errMsg;
		document.addpincode.areacode.focus();
		return ;
		}
	 var pincoderegex=/^[0-9]{4,6}$/;
		if(!areacode.match(pincoderegex))
		{
		errMsg ="<font color='#ff3333'>Please Enter Valid \'Pincode\'.</font>";
		document.getElementById("displaypincodeerrormsg").innerHTML=errMsg;
		document.addpincode.areacode.focus();
		return ;
		}
		
		
	document.addpincode.action = "../../../../servlet/RetailerLocationMap?hidWhatToDo=insertarea&state="+state+"&city="+city+"&areacode="+areacode+"&keyword="+keyword;
	//alert(document.addbattery.action);
	document.addpincode.method="post";
	document.addpincode.submit();
}
function FunReset()
{
	location.href="../../../../servlet/RetailerLocationMap?hidWhatToDo=getstates&keyword=area"
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
<body onload="document.addpincode.state.focus();" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="addpincode" method="post" ENCTYPE="multipart/form-data">
<script type="text/javascript" src="../../../../js/cluetip_new.js"> </script>
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
								<td class="subheading">Add&nbsp;Pincode</td>
								<td  align="right"><a href="../../../../servlet/AdminBatteryLogin?hidWhatToDo=batteryadminhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
							</tr>
							<tr><td height="10"></td></tr>
							<tr><td align="center" class="insidecontentbat"><div id="displaypincodeerrormsg"></div></td></tr>
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
									<td width="59%" class="insidecontent" align="left"><select name="city" id ="city"  class="insidecontent" style='width:180px'>
									<option value="0" >&lt;-&nbsp;Select City&nbsp;-&gt;</option>
										</select></td>
								</tr>
								<tr>
									<td width="39%" class="insidecontent" align="right">Enter&nbsp;Pincode&nbsp;:</td>
									<td width="59%" class="insidecontent" align="left"><input type="text" name="areacode" size="20" maxlength="7"></td>
								</tr>
								<table width="100%" border='0' align="right">
								<tr>
									<td width="45%" class="insidecontent" align="right"><input type="button" value="Submit" class="button4" onclick="javascript:funToAddAreacode();"></td><td width="1%"></td>
									<td width="45%" class="insidecontent" align="left"><input type="button" value="Reset" class="button4" onclick="javascript:FunReset();"></td>
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
String strLogMsg=(String)session.getAttribute("sespincodeErrorMsg");
if(strLogMsg ==null)
{
	strLogMsg="";
}
else
{
%>
<script type="text/javascript">
var loginmessg; 
loginmessg= "<%=strLogMsg%>";
document.getElementById("displaypincodeerrormsg").innerHTML=loginmessg;
</script>
<%
	session.removeAttribute("sespincodeErrorMsg");
}
%>
</body>
</html>