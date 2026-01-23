<%--
    Document   : batteryadminmain
    Created on : Aug 30, 2013, 4:22:12 PM
    Author     : Prakash Mallidi.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.LogLevel"%>
<%
String strUserid=(String)session.getAttribute("sesServicesOperatorName");
if(strUserid==null)
{
	strUserid="";
	session.setAttribute("sesErrorMsg","Session Timed Out. Please login again");
	response.sendRedirect("../../../operator/index.html");
	return;
}
Vector brandVector=(Vector)session.getAttribute("sesbatterybrandvector");
LogLevel.DEBUG(1,new Throwable(),"brandVector: "+brandVector);

String strDateOpt=request.getParameter("dates");
if(strDateOpt==null || strDateOpt.equals(""))
	strDateOpt="current";

String strFromDate=(request.getParameter("txtFromDate")!=null)?request.getParameter("txtFromDate"):"";
String strToDate=(request.getParameter("txtToDate")!=null)?request.getParameter("txtToDate"):"";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../images/favicon.png" type="image/x-icon">
<script src="../../../js/jquery-1.3.2.min.js" type="text/javascript"></script>

<title>BookBattery.com-India's No.1 Automobile Battery Store</title>
<script language="javascript">
function getmodels()
{ 
		document.getElementById("displayerrormsg").innerHTML="";
		var strbatbname1=document.misseloption.batbrand.value;

		if(strbatbname1 == "0,ALL")
		{			
		var mySplitResult = strbatbname1.split(",");
		var bid=mySplitResult[0];
		var strbatbname=mySplitResult[1];
	     }
	   else
		{
		var strbatbname = strbatbname1;
		 }
		var xmlHttp;
		var url="../../../servlet/MISOperatorBatteryDetails?hidWhatToDo=getbatterymodels&batbrand="+strbatbname;
		try
		{ 
			xmlHttp = new XMLHttpRequest();
		}
		catch (e)
		{
			try
			{
				xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
			}
			catch (e)
			{
				try
				{
					xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
				}
				catch (e)
				{
					alert("Your	browser	does not support AJAX!");
					return false;
				}
			}
		}
		xmlHttp.onreadystatechange = function()
		{
			if (xmlHttp.readyState == 4)
			{
				varModels = xmlHttp.responseText;
				if (varModels.indexOf("Evaluating Session") >= 0)
				{
					alert("Session Timed out, Please login again");
					return;
				}
				if (xmlHttp.statusText != "OK")
				{
					alert("Exception Occured, Please try again later");
					return;
				}
				if (varModels.indexOf("Battery Models are not available under the selected BatteryBrand") >= 0)
				{
					for (i=document.misseloption.model.options.length-1; i >= 1; i--)
					{
						document.misseloption.model.remove(i);
					}
					for (i=document.misseloption.city.options.length-1; i >= 1; i--)
					{
						document.misseloption.city.remove(i);
					}
					for (i=document.misseloption.status.options.length-1; i >= 1; i--)
					{
						document.misseloption.status.remove(i);
					}
					$('#status').html('<option value="0" >&lt;-&nbsp;&nbsp; Select Status -&gt;</option><option value="ordered">Ordered</option><option value="confirmed">Confirmed</option><option value="installed">Installed</option><option value="postponed">Postponed</option><option value="cancelled-franchisee-offbushrs">Cancelled-Franchisee-OffBusHrs</option><option value="cancelled-franchisee-denied">Cancelled-Franchisee-Denied</option><option value="cancelled-franchisee-notresponded">Cancelled-Franchisee-NotResponded</option><option value="cancelled-franchisee-modeloutofstock">Cancelled-Franchisee-ModelOutofStock</option><option value="cancelled-customer">Cancelled-Customer</option><option value="cancelled-regenerated">Cancelled-Regenerated</option>');
					return;
					document.misseloption.batbrand.focus();
				}
				if (varModels != "")
				{
					varEMPFlag = true;
					varModelsArry = varModels.split("|");
					var len = varModelsArry.length;
					for (i= document.misseloption.model.options.length- 1; i >= 1; i--)
					{
						document.misseloption.model.remove(i);
					}
					var optn1 = document.createElement("OPTION");
						optn1.value = "0,ALL";
						optn1.text = "All";
						document.misseloption.model.options.add(optn1);
					for (var i = 0; i < len; i++)
					{
						varOptArray = varModelsArry[i].split(":");
						var optn = document.createElement("OPTION");
						optn.value = +varOptArray[0]+","+varOptArray[1];
						optn.text = varOptArray[1];
						document.misseloption.model.options.add(optn);
					}
					document.misseloption.model.focus();						
				}
			}
		}
		xmlHttp.open("GET",url, true);
		xmlHttp.send(null);
}
function getcity()
{ 
	var model = document.misseloption.model.value;
	if(model == 0)
	{
			for (i=document.misseloption.city.options.length-1; i >= 1; i--)
			{
			document.misseloption.city.remove(i);
			}
			for (i= document.misseloption.status.options.length- 1; i >= 1; i--)
			{
				document.misseloption.status.remove(i);
			}
			$('#status').html('<option value="0" >&lt;-&nbsp;&nbsp; Select Status -&gt;</option><option value="ordered">Ordered</option><option value="confirmed">Confirmed</option><option value="installed">Installed</option><option value="postponed">Postponed</option><option value="cancelled-franchisee-offbushrs">Cancelled-Franchisee-OffBusHrs</option><option value="cancelled-franchisee-denied">Cancelled-Franchisee-Denied</option><option value="cancelled-franchisee-notresponded">Cancelled-Franchisee-NotResponded</option><option value="cancelled-franchisee-modeloutofstock">Cancelled-Franchisee-ModelOutofStock</option><option value="cancelled-customer">Cancelled-Customer</option><option value="cancelled-regenerated">Cancelled-Regenerated</option>');
			document.misseloption.model.focus();
	}
	else
	{
		var xmlHttp;
		var url="../../../servlet/MISOperatorBatteryDetails?hidWhatToDo=getcity";
		try
		{ 
			xmlHttp = new XMLHttpRequest();
		}
		catch (e)
		{
			try
			{
				xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
			}
			catch (e)
			{
				try
				{
					xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
				}
				catch (e)
				{
					alert("Your	browser	does not support AJAX!");
					return false;
				}
			}
		}
		xmlHttp.onreadystatechange = function()
		{
			if (xmlHttp.readyState == 4)
			{
				varCity = xmlHttp.responseText;
				if (varCity.indexOf("Evaluating Session") >= 0)
				{
					alert("Session Timed out, Please login again");
					return;
				}
				if (xmlHttp.statusText != "OK")
				{
					alert("Exception Occured, Please try again later");
					return;
				}
				if (varCity.indexOf("City are not available under the selected Model") >= 0)
				{
					for (i=document.misseloption.city.options.length-1; i >= 1; i--)
					{
						document.misseloption.city.remove(i);
					}
					for (i= document.misseloption.status.options.length- 1; i >= 1; i--)
					{
						document.misseloption.status.remove(i);
					}
					$('#status').html('<option value="0" >&lt;-&nbsp;&nbsp; Select Status -&gt;</option><option value="ordered">Ordered</option><option value="confirmed">Confirmed</option><option value="installed">Installed</option><option value="postponed">Postponed</option><option value="cancelled-franchisee-offbushrs">Cancelled-Franchisee-OffBusHrs</option><option value="cancelled-franchisee-denied">Cancelled-Franchisee-Denied</option><option value="cancelled-franchisee-notresponded">Cancelled-Franchisee-NotResponded</option><option value="cancelled-franchisee-modeloutofstock">Cancelled-Franchisee-ModelOutofStock</option><option value="cancelled-customer">Cancelled-Customer</option><option value="cancelled-regenerated">Cancelled-Regenerated</option>');
					return;
				}
				if (varCity != "")
				{
					varEMPFlag = true;
					varCityArry = varCity.split("|");
					var len = varCityArry.length;
					for (i= document.misseloption.city.options.length- 1; i >= 1; i--)
					{
						document.misseloption.city.remove(i);
					}
						for (i= document.misseloption.status.options.length- 1; i >= 1; i--)
					{
						document.misseloption.status.remove(i);
					}
					$('#status').html('<option value="0" >&lt;-&nbsp;&nbsp; Select Status -&gt;</option><option value="ordered">Ordered</option><option value="confirmed">Confirmed</option><option value="installed">Installed</option><option value="postponed">Postponed</option><option value="cancelled-franchisee-offbushrs">Cancelled-Franchisee-OffBusHrs</option><option value="cancelled-franchisee-denied">Cancelled-Franchisee-Denied</option><option value="cancelled-franchisee-notresponded">Cancelled-Franchisee-NotResponded</option><option value="cancelled-franchisee-modeloutofstock">Cancelled-Franchisee-ModelOutofStock</option><option value="cancelled-customer">Cancelled-Customer</option><option value="cancelled-regenerated">Cancelled-Regenerated</option>');
						var optn1 = document.createElement("OPTION");
						optn1.value = "0,ALL";
						optn1.text = "All";
						document.misseloption.city.options.add(optn1);
					for (var i = 0; i < len; i++)
					{
						varOptArray = varCityArry[i].split(":");
						var optn = document.createElement("OPTION");
						optn.value = +varOptArray[0]+","+varOptArray[1];
						optn.text = varOptArray[1];
						document.misseloption.city.options.add(optn);
					}
					document.misseloption.city.focus();
				}
			}
		}
		xmlHttp.open("GET",url, true);
		xmlHttp.send(null);
	}
}
function getstatus()
{
		var city = document.misseloption.city.value;
		
		for (i= document.misseloption.status.options.length- 1; i >= 1; i--)
		{
			document.misseloption.status.remove(i);
		}
		$('#status').html('<option value="0" >&lt;-&nbsp;&nbsp; Select Status -&gt;</option><option value="ordered">Ordered</option><option value="confirmed">Confirmed</option><option value="installed">Installed</option><option value="postponed">Postponed</option><option value="cancelled-franchisee-offbushrs">Cancelled-Franchisee-OffBusHrs</option><option value="cancelled-franchisee-denied">Cancelled-Franchisee-Denied</option><option value="cancelled-franchisee-notresponded">Cancelled-Franchisee-NotResponded</option><option value="cancelled-franchisee-modeloutofstock">Cancelled-Franchisee-ModelOutofStock</option><option value="cancelled-customer">Cancelled-Customer</option><option value="cancelled-regenerated">Cancelled-Regenerated</option>');

		document.misseloption.status.focus();
		if(city==0)
		{
			document.misseloption.city.focus();
		}				
}
function funClickSubmit()
{
	var strbatbname1=document.misseloption.batbrand.value;
	var strmodel1 = document.misseloption.model.value;
	var strcity1 = document.misseloption.city.value;
		var status = document.misseloption.status.value;

	var strDateOpt = document.misseloption.dates.value;
	var strFromDate = document.misseloption.txtFromDate.value;
	var strToDate = document.misseloption.txtToDate.value;

	if(strbatbname1 == "0,ALL")
		{			
		var mySplitResult2 = strbatbname1.split(",");
		var bid=mySplitResult2[0];
		var strbatbname=mySplitResult2[1];
	     }
	   else
		{
		var strbatbname = strbatbname1;
		 }
		 if(document.misseloption.batbrand.value == 0)
		 {
			errMsg ="<font color='#FF3333'>Please select battery brand</font>";
			document.getElementById("displayerrormsg").innerHTML=errMsg;
			document.misseloption.batbrand.focus();
			return ;
		 }
		  if(document.misseloption.model.value == 0)
		 {
			errMsg ="<font color='#FF3333'>Please select model</font>";
			document.getElementById("displayerrormsg").innerHTML=errMsg;
			document.misseloption.model.focus();
			return ;
		 }
		  if(document.misseloption.city.value == 0)
		 {
			errMsg ="<font color='#FF3333'>Please select city</font>";
			document.getElementById("displayerrormsg").innerHTML=errMsg;
			document.misseloption.city.focus();
			return ;
		 }
		 if(document.misseloption.status.value == 0)
		 {
			errMsg ="<font color='#FF3333'>Please select Status</font>";
			document.getElementById("displayerrormsg").innerHTML=errMsg;
			document.misseloption.status.focus();
			return ;
		 }

	var mySplitResult = strmodel1.split(",");
	var modelid=mySplitResult[0];
	var strmodel=mySplitResult[1];

	var mySplitResult1 = strcity1.split(",");
	var cityid=mySplitResult1[0];
	var strcity=mySplitResult1[1];

	document.misseloption.method="POST";
	document.misseloption.action="../../../servlet/MISOperatorBatteryDetails?hidWhatToDo=getorderbatterydetails&batname="+strbatbname+"&model="+strmodel+"&city="+strcity+"&status="+status+"&&dates="+strDateOpt+"&txtFromDate="+strFromDate+"&txtToDate="+strToDate;
	document.misseloption.submit();

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
<link href="../../../css/sgservices.css" rel="stylesheet" type="text/css" />
</head>
<body onload="" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="misseloption">
<!-- Battery Header Starts -->
<tr>
	<jsp:include page = "../header.jsp" />
</tr>
<!-- Battery Header Ends -->
<!--<tr>
	<td>
		<img src="../../../images/flag1234.JPG" width="880" height="15">
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
					<table width="450" border="0" align="center" cellpadding="5" cellspacing="0" bgcolor="#FFFFFF">
					<tr><td align="left" class="subheading"></td><td width="50%"align="right"><a href="../../../jsp/operator/ordermis/misoption.jsp?dates=<%=strDateOpt%>&txtFromDate=<%=strFromDate%>&txtToDate=<%=strToDate%>" class="onclick1">Back</a></td></tr>
					<tr>
						<td>
							<table width="450" align="center" cellspacing="1" cellpadding="0">
							<tr><td height="20"></td></tr>
							<tr>
								<td class="subheading" align="left" size="2"><%=strDateOpt%> >> Brand&nbsp;and&nbsp;Location</td>
							</tr>
							<tr>
								<td>
									<table width="100%" cellspacing="1" bgcolor="#8B8D90" cellpadding="0">
										<tr>
											<td  bgcolor="#0081C7" align="center" class="subheading"><font color="#FFFFFF">Select&nbsp;Options</font></td>
										</tr>
											<tr>
												<td bgcolor="#FFFFFF" align="center">
													<table width="80%" align="center" border="0" cellspacing="0" bgcolor="#FFFFFF" cellpadding="0">
													<tr><td height="10"></td></tr>
													<tr >
														<td colspan="2" height="10" align="center" class="insidecontent"><div id="displayerrormsg"></div>&nbsp;</td>
													</tr>
													<tr>
														<td class="table1" align="right" height="25" width="40%">Battery Brand<sup color="Red"><font color="Red">*</font><sup>:&nbsp;</td>
														<td class="table1" align="left" width="60%">
															<select name="batbrand" class="insidecontent" STYLE="width: 180px" onChange="javascript:getmodels();">
																<option value="0" >&lt;-&nbsp;&nbsp; Select Brand &nbsp;&nbsp; --&gt;</option>
																<!--<option value="0,ALL" >All</option>-->
																<%
																try
																   {
																	if(brandVector!=null && brandVector.size()>0)
																	{
																	for(int i=0;i<brandVector.size();i++)
																	{
																		Hashtable ht=(Hashtable)brandVector.get(i);
																		String bname=(String)ht.get("battery_brand");
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
															</select>
														</td>
													</tr>
													<tr >
														<td class="table1" align="right" height="25" width="40%">Select Model<sup color="Red"><font color="Red">*</font><sup>:&nbsp;</td>
														<td class="table1" align="left" width="60%">
															<select name="model" class="insidecontent" STYLE="width: 180px" onChange="javascript:getcity();">
																<option value="0" >&lt;-&nbsp;&nbsp; Select Model -&gt;</option>
															</select>
														</td>
													</tr>
													<tr >
														<td class="table1" align="right" height="25" width="40%">Select City<sup color="Red"><font color="Red">*</font><sup>:&nbsp;</td>
														<td class="table1" align="left" width="60%">
															<select name="city" class="insidecontent" STYLE="width: 180px" onChange="javascript:getstatus();">
																<option value="0" >&lt;-&nbsp;&nbsp; Select city -&gt;</option>
															</select>
														</td>
													</tr>
													<tr >
														<td class="table1" align="right" height="25" width="40%">Select Status<sup color="Red"><font color="Red">*</font><sup>:&nbsp;</td>
														<td class="table1" align="left" width="60%">
															<select name="status" id="status" class="insidecontent" STYLE="width: 180px">
																<option value="0" >&lt;-&nbsp;&nbsp; Select Status -&gt;</option>
																<option value='ordered'>Ordered</option><option value='confirmed'>Confirmed</option>
																<option value='installed'>Installed</option>
																<option value='postponed'>Postponed</option>
																<option value='cancelled-franchisee-offbushrs'>Cancelled-Franchisee-OffBusHrs</option>
																<option value='cancelled-franchisee-denied'>Cancelled-Franchisee-Denied</option>
																<option value='cancelled-franchisee-notresponded'>Cancelled-Franchisee-NotResponded</option>
																<option value='cancelled-franchisee-modeloutofstock'>Cancelled-Franchisee-ModelOutofStock</option>
																<option value='cancelled-customer'>Cancelled-Customer</option><option value='cancelled-regenerated'>Cancelled-Regenerated</option>
															</select>
														</td>
													</tr>
													<table border="0" width="90%" align="center">
													<tr><td height="5"></td><td></td></tr>
													<tr><td align="center" width="90%"><a href="javascript:funClickSubmit()" class="button4">Submit</a></td>
													</tr>
													<tr><td height="5"></td><td></td></tr>
													</table>
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
<input type="hidden" name="dates" value="<%=strDateOpt%>">
<input type="hidden" name="txtFromDate" value="<%=strFromDate%>">
<input type="hidden" name="txtToDate" value="<%=strToDate%>">
</form>
</center>
</body>
</html>