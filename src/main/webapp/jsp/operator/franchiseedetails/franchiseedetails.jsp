<%--
    Document   : find franchisee details
    Created on : Jan 27, 2014, 11:22:12 AM
    Author     : Sai Krishna Daddala
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.LogLevel"%>
<%
String strUserid=(String)session.getAttribute("sesBatteryOperatorName");
if(strUserid==null)
{
	strUserid="";
	session.setAttribute("sesErrorMsg","Session Timed Out. Please login again");
	response.sendRedirect("../../../operator/index.html");
	return;
}
Vector StateVector=(Vector)session.getAttribute("sesstatevector");
LogLevel.DEBUG(1,new Throwable(),"StateVector: "+StateVector);

String strDateOpt=request.getParameter("dates");
if(strDateOpt==null || strDateOpt.equals(""))
	strDateOpt="current";

String keyword=(request.getParameter("keyword")!=null)?request.getParameter("keyword"):"";
String strFromDate=(request.getParameter("txtFromDate")!=null)?request.getParameter("txtFromDate"):"";
String strToDate=(request.getParameter("txtToDate")!=null)?request.getParameter("txtToDate"):"";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../images/favicon.png" type="image/x-icon">
<script language="JavaScript" src="../../../js/jquery-1.8.2.js" ></script>
<title>BookBattery.com-India's No.1 Automobile Battery Store</title>
<script language="javascript">

$(document).ready(function()
{
	$selectedstate = $('#state');
	$selectedstate.change
	(
        function()
		{
			 
			 var splitval =$selectedstate.val();
			 if(splitval == 0)
			 {
				for(i=document.findfranchisee.city.options.length-1;i>=1;i--)
				{
				document.findfranchisee.city.remove(i);
				}
				$('#divfranchiseedetails').hide();
				document.findfranchisee.state.focus();
			 }
			 else
			 {
				 $('#divfranchiseedetails').hide();
                $.ajax({
                    type: "GET",
                     url: "../../../servlet/FindFranchiseeDetails?hidWhatToDo=getcites",
                    data: {state: $selectedstate.val() },
                    success: function(data)
					{
                        $("#city").html(data)
						document.findfranchisee.city.focus();
                    }
                });
			 }
         }
    );
});
$(document).ready(function()
{
	$selectedcity = $('#city');
	$selectedcity.change
	(function(){
		 var splitval =$selectedcity.val();
		 	 if(splitval == "default")
			 {
			
				for(i=document.findfranchisee.brand.options.length-1;i>=1;i--)
				{
				document.findfranchisee.brand.remove(i);
				}
				$('#divfranchiseedetails').hide();
				document.findfranchisee.city.focus();
			 }
			 else
			 {
				$('#divfranchiseedetails').hide();
                $.ajax({
                    type: "GET",
                    url: "/bookbattery/servlet/FindFranchiseeDetails?hidWhatToDo=getbrands",
                    data: {state: $selectedcity.val() },
                    success: function(data)
					{
                        $("#brand").html(data)
						document.findfranchisee.brand.focus();
                    }
                });
			}
         }
    );
});
function funFranchiseeDetails()
{
	
		var state=document.findfranchisee.state.value;
		var city=document.findfranchisee.city.value;
		var brand=document.findfranchisee.brand.value;

		 if(document.findfranchisee.state.value == 0)
		 {
			 $("#displayerrormsg").show();

			errMsg ="<font color='#FF3333'>Please select State</font>";
			document.getElementById("displayerrormsg").innerHTML=errMsg;
			document.findfranchisee.state.focus();
			return ;
		 }
		 if(document.findfranchisee.city.value == "default")
		 {
			 $("#displayerrormsg").show();
			errMsg ="<font color='#FF3333'>Please select City</font>";
			document.getElementById("displayerrormsg").innerHTML=errMsg;
			document.findfranchisee.city.focus();
			return ;
		 }
		  if(document.findfranchisee.brand.value == "default")
		 {
			 $("#displayerrormsg").show();
			errMsg ="<font color='#FF3333'>Please select Brand</font>";
			document.getElementById("displayerrormsg").innerHTML=errMsg;
			document.findfranchisee.brand.focus();
			return ;
		 }
		
		var xmlhttp= "";
		var resp= "";
		var url ="../../../servlet/FindFranchiseeDetails?hidWhatToDo=getfranchiseedetails&state="+state+"&city="+city+"&brand="+brand;
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
					$("#displayerrormsg").hide();
					$("#divfranchiseedetails").show();
					resp = xmlhttp.responseText;
					document.getElementById("divfranchiseedetails").innerHTML = resp;
					document.getElementById("divfranchiseedetails").innerHTML = xmlhttp.responseText;
					
				}
			}			
		}
		
		xmlhttp.open("GET",url, true);		
		xmlhttp.send();

}
function checkCity()
{
	var city=document.findfranchisee.city.value;
		if(document.findfranchisee.city.value == "default")
		 {
			$('#divfranchiseedetails').hide();
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
	/*background-image: url(../../../images/index_01_01.gif);
	background-repeat: repeat-x;*/
}
-->
</style>
<link href="../../../css/bookbattery.css" rel="stylesheet" type="text/css" />
</head>
<body onload="document.findfranchisee.state.focus();" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="findfranchisee">
<!-- Battery Header Starts -->
<tr>
	<jsp:include page = "../header.jsp" />
</tr>
<tr>
	<td>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#ffffff">
			<tr>
				<td width="25%" align="left" valign="top" bgcolor="#ffffff">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<!-- Starts battery admin left Menu -->
					<tr>
						<jsp:include page="../operatorleftmenu.jsp"/>
					</tr>
					<!-- Ends battery admin left Menu -->
					</table>
				</td>
				<td width="75%" align="left" valign="top">
					<table width="100%" border="1" cellspacing="0" cellpadding="0">
						<tr>
							<td align="left" valign="top">
							<!-- your page content starts here  -->
							<table width="100%" cellspacing="1" align="center" bgcolor="#FFFFFF" cellpadding="0" border="0">
								<table width="650" border="0" align="center" cellpadding="5" cellspacing="0" bgcolor="#FFFFFF">
									<tr><td align="left" width="50%" class="subheading">Franchisee Details</td><td width="50%"align="right"><a href="../../../servlet/OperatorLogin?hidWhatToDo=batteryoperatorhome" class="onclick1">Back&nbsp;&nbsp;</a></td></tr>
									
									<tr>
										<td>
											<table width="450" align="center" cellspacing="1" cellpadding="0">
												<tr><td height="20"></td></tr>
												
												<tr>
													<td>
														<table width="100%" cellspacing="1" cellpadding="0">
															
															<tr>
																<td bgcolor="#FFFFFF" align="center">
																	<table width="80%" align="center" border="0" cellspacing="0" bgcolor="#FFFFFF" cellpadding="0">
																		<tr><td height="10"></td></tr>
																		<tr >
																			<td colspan="2" height="10" align="center" class="insidecontent"><div id="displayerrormsg"></div>&nbsp;</td>
																		</tr>
																		<tr>
																			<td width="39%" class="insidecontent" align="right">Select&nbsp;State<font color="FF0000">*</font>&nbsp;:</td>
																			<td width="59%" class="insidecontent" align="left">
																				<select name="state" id="state"  class="insidecontent" style='width:180px'>
																					<option value="0" >&lt;-&nbsp;Select State&nbsp;-&gt;</option>
																				
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
																				</select>
																			</td>
																		</tr>
																		<tr>
																			<td width="39%" class="insidecontent" align="right">Select&nbsp;City<font color="FF0000">*</font>&nbsp;:</td>
																			<td width="59%" class="insidecontent" align="left">
																				<select name="city" id="city"  class="insidecontent" style='width:180px' onchange="checkCity();">
																					<option value="0" >&lt;-&nbsp;Select City&nbsp;-&gt;</option>
																				</select>
																			</td>
																		</tr>
																			<tr>
																			<td width="39%" class="insidecontent" align="right">Select&nbsp;Brand<font color="FF0000">*</font>&nbsp;:</td>
																			<td width="59%" class="insidecontent" align="left">
																				<select name="brand" id="brand"  class="insidecontent" style='width:180px' onchange="checkCity();">
																					<option value="0" >&lt;-&nbsp;Select Brand&nbsp;-&gt;</option>
																				</select>
																			</td>
																		</tr>
																		<tr><td height="10"></td></tr>
																		<table border="0" width="90%" align="center">
																			<tr><td width="15%"></td>
																			<td align="center" width="90%"><a href="javascript:funFranchiseeDetails()" class="button4">Find</a></td>
																			</tr>
																		</table>
																		<tr><td align="center" class="insidecontentbat"><div id="divfranchiseedetails" style="width:770px;height:200px;  overflow:scroll; overflow-X:auto; overflow-Y:auto; -webkit-overflow-scrolling: touch;display:none;"></div></td></tr>
																		
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
<input type="hidden" name="dates" value="<%=strDateOpt%>">
<input type="hidden" name="txtFromDate" value="<%=strFromDate%>">
<input type="hidden" name="txtToDate" value="<%=strToDate%>">
</form>
</center>
</body>
</html>