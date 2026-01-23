<%-- 
    Document   : Check Recharge.jsp 
	Created on : march 18, 2012, 12:33:12 PM
	Author     : Jhansi A
--%>
<!@page language="java">

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
String strLogMsg=(String)session.getAttribute("sesErrorMsg");
if(strLogMsg==null)
{
	strLogMsg="";
}
else
{
//Removing the attribute from session
	session.removeAttribute("sesErrorMsg");
}

Vector alist=(Vector)session.getAttribute("result");
 
//session.removeAttribute("sesVector");

//Get Date(i.e returns the Date of the category as a string or null )
String strDateOpt=request.getParameter("dates");

if(strDateOpt==null || strDateOpt.equals(""))
	strDateOpt="current";

//Get strFromDate(i.e returns the strFromDate of the category as a string or null )
String strFromDate=(request.getParameter("txtFromDate")!=null)?request.getParameter("txtFromDate"):"";

//Get ToDate(i.e returns the ToDate of the category as a string or null )
String strToDate=(request.getParameter("txtToDate")!=null)?request.getParameter("txtToDate"):"";

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../../images/favicon.png" type="image/x-icon">
<title>BookBattery.com - India's No.1 Automobile Battery Store</title>
<script language="JavaScript" src="../../../../js/jquery-1.3.2.min.js" ></script>
<script language="javascript">
/* ******************************************************************\
* This function is used to showrechargedetails when admin needs to showrechargedetails.
* Path of the java class to perform this function.
* home/ngit/tomcat/webapps/bookbattery/WEB-INF/classes/com/ngit/servlets/admin/loyaltypoints/ReedemPoints.
* Action used in this java class-checkrechargedetails.
\* ********************************************************************/

function showrechargedetails()
{
	var promotionalcode=document.checkrecharge.promotionalcode.value;			
	var xmlhttp;
	var url="../../../../servlet/BrandLoyalty?hidWhatToDo=checkrechargedetails&promotionalcode="+promotionalcode+"&requestno="+(Math.random()*100);
	
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
		if(xmlhttp.readyState!=4)
		{
			document.getElementById("otherRet1").innerHTML="<span class='style1'><center><br><br><b><img src='../../../../images/loader.gif' align='center'><br>Please wait! Fetching details of promotional code.</b></center><br><br></span>";
		}

		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			res=xmlhttp.responseText;
			$('#otherRet1').show();	
			document.getElementById("otherRet1").innerHTML="";
			document.getElementById("otherRet1").innerHTML=xmlhttp.responseText;
		}
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();		
}

/* ******************************************************************\
* This function is used to showrechargedetails when admin needs to showrechargedetails.
************************************************************************/
function hiderechargedetails()
{
	$(document).ready(function()
	{
		$('#otherRet1').hide();	
	});
}
function res()
{
	document.checkrecharge.reset();
}
</script>
<link href="../../../../css/bookbattery.css" rel="stylesheet" type="text/css" />
</head>
<body onload="" >
<center>
<form name="checkrecharge" method="post" >
<table cellpadding="0" cellspacing="0" border="0" width="960">
	<tr>
		<td>
			<!-- top page starts here  -->
			<jsp:include page = "../header.jsp" />
			<!-- top page ends here  -->
		</td>
	<tr>
		<td align="left" valign="top" bgcolor="#FFFFFF">
			<table width="960" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="180" height="438" align="left" valign="top">
						<table width="180" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="180" height="438" align="left" valign="top">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td>
												<!-- categories starts here  -->
												<jsp:include page="../batteryadminleftmenu.jsp"/>
												<!-- categories ends here  -->
											</td>
										</tr>
									</table>
								</td>
								<td width="680" align="left" valign="top" >
									<!-- Inner content should be within the below table -->
									<table width="770" border="0" cellpadding="5" cellspacing="0" bgcolor="#FFFFFF">
										<tr>
											<td>
												<table width="713" cellspacing="1" cellpadding="0" border="0">
													<tr>
														<td class="subheading" size="2">Consumer Loyalty Options >> Check Recharge</td>
													</tr>
													<tr>
														<td width="35%" align="right"><a href="../../../../jsp/admin/batterystore/brandloyaltyprogram/brandloyaltyselection.jsp" class="onclick1">Back&nbsp;&nbsp;</a></td>
													</tr>
													<tr>
														<td>
															<table width="100%" cellspacing="1" bgcolor="#FFFFFF" cellpadding="0">
																<tr>
																	<td  bgcolor="#BEADCB" align="center" class="subheading"><font color="#000000">Check Recharge</font></td>
																</tr>
																<tr>
																	<td bgcolor="#FFFFFF" align="center">
																		<table width="250" align="center" border="0" cellspacing="0" bgcolor="#FFFFFF" cellpadding="5">
																			<tr>
																				<td colspan="2" height="20">&nbsp;</td>
																			</tr>
																		</table>
																		<table width="450" align="center" border="0" cellspacing="0" bgcolor="#FFFFFF" cellpadding="5">
																			<tr>
																				<td width="200" align="center" valign="middle" class="content"><b>Promotional code:</b></td>
																				<td width="50" align="left" ><input class = "top1" type = "text" name = "promotionalcode" value="" maxlength = "8" size = "20"></td>
																				<td class="content" width="100" align="left"><a href="javascript:showrechargedetails()" class="button4">Show</a></td>
																				<td class="content" width="100" align="left"><a href="../../../../jsp/admin/batterystore/brandloyaltyprogram/checkrecharge.jsp" valign="middle" class="button4">Reset</a></td>
																				<td class="content" width="100" align="left"><a href="javascript:hiderechargedetails()" valign="middle" class="button4">Hide</a></td>
																			</tr>
																		</table>
																		<tr>
																			<td>
																				<div id="otherRet1"></div>
																			</td>
																		</tr>
																	</td>
																</tr>
																<!-- Inner content ends here -->
															</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td>
												<!-- footer page starts here  -->
												<jsp:include page = "../footer.jsp" />
												<!-- top page ends here  -->
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
<input type="hidden" name="hidWhatToDo" value="overallmis">
<input type="hidden" name="dates" value="<%=strDateOpt%>">
<input type="hidden" name="txtFromDate" value="<%=strFromDate%>">
<input type="hidden" name="txtToDate" value="<%=strToDate%>">
</form>
</center>
</body>
</html>