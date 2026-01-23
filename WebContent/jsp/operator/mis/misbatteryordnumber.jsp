<%--
    Document   : batteryadminmain
    Created on : Aug 30, 2013, 4:22:12 PM
    Author     : Sai Krishna Daddala.
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
Vector OrdnumberVector=(Vector)session.getAttribute("sesbatteryOrdNovector");
LogLevel.DEBUG(1,new Throwable(),"OrdnumberVector: "+OrdnumberVector);

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
<script language="JavaScript" src="../../../js/jquery-1.8.2.js" ></script>
<title>BookBattery.com-India's No.1 Automobile Battery Store</title>
<script language="javascript">
$(document).ready(function(){
		$category3 = $('#ordnumb');
        $category3.change(
            function() {
 			document.getElementById("displayerrormsg").innerHTML="";
                $.ajax({
                    type: "GET",
                     url: "../../../servlet/MISOperatorBatteryDetails?hidWhatToDo=getlocationord",
                    data: {ordnumb: $category3.val() },
                    success: function(data){
                        $("#locations").html(data)
						document.batordnum.locations.focus();
                    }
                });
            }
        );
	});
function funClickSubmit()
{
	var ordnumb=document.batordnum.ordnumb.value;
	var locations=document.batordnum.locations.value;
	var strDateOpt = document.batordnum.dates.value;
	var strFromDate = document.batordnum.txtFromDate.value;
	var strToDate = document.batordnum.txtToDate.value;

		 if(document.batordnum.ordnumb.value == 0)
		 {
			errMsg ="<font color='#FF3333'>Please select Ord Ref Number</font>";
			document.getElementById("displayerrormsg").innerHTML=errMsg;
			document.batordnum.ordnumb.focus();
			return ;
		 }
		  if(document.batordnum.locations.value == 0 || locations == "default") 
		 {
			errMsg ="<font color='#FF3333'>Please select Location</font>";
			document.getElementById("displayerrormsg").innerHTML=errMsg;
			document.batordnum.locations.focus();
			return ;
		 }
		
	document.batordnum.method="POST";
	document.batordnum.action="../../../servlet/MISOperatorBatteryDetails?hidWhatToDo=getbatteryordernodetails&ordnumb="+ordnumb+"&locations="+locations+"&dates="+strDateOpt+"&txtFromDate="+strFromDate+"&txtToDate="+strToDate;
	document.batordnum.submit();

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
<body onload="" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="batordnum">
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
								<td class="subheading" align="left" size="2"><%=strDateOpt%> >> Ord ref Number&nbsp;and&nbsp;Location</td>
							</tr>
							<tr>
								<td>
									<table width="100%" cellspacing="1" bgcolor="#8B8D90" cellpadding="0">
										<tr>
											<td  bgcolor="#0081C7" align="center" class="subheading"><font color="#FFFFFF">Select&nbsp;Options</font></td>
										</tr>
											<tr>
												<td bgcolor="#FFFFFF" align="center">
													<table width="85%" align="center" border="0" cellspacing="0" bgcolor="#FFFFFF" cellpadding="0">
													<tr><td height="10"></td></tr>
													<tr >
														<td colspan="2" height="10" align="center" class="insidecontent"><div id="displayerrormsg"></div>&nbsp;</td>
													</tr>
													<tr>
														<td class="table1" align="right" height="25" width="40%">Select Ord Ref Number<sup color="Red"><font color="Red">*</font><sup>:&nbsp;</td>
														<td class="table1" align="left" width="60%">
															<select name="ordnumb" id="ordnumb" class="insidecontent" STYLE="width: 180px">
																<option value="0" >&lt;-&nbsp;&nbsp; Select Ord Ref Number &nbsp;&nbsp; --&gt;</option>
																<option value="ALL" >ALL</option>
																<%
																try
																   {
																	if(OrdnumberVector!=null && OrdnumberVector.size()>0)
																	{
																	for(int i=0;i<OrdnumberVector.size();i++)
																	{
																		Hashtable ht=(Hashtable)OrdnumberVector.get(i);
																		String ordnumber=(String)ht.get("order_number");
																%>
																<option value="<%=ordnumber%>"><%=ordnumber%></option>
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
														<td class="table1" align="right" height="25" width="40%">Select Location<sup color="Red"><font color="Red">*</font><sup>:&nbsp;</td>
														<td class="table1" align="left" width="60%">
															<select name="locations" id="locations" class="insidecontent" STYLE="width: 180px" >
																<option value="0" >&lt;-&nbsp;&nbsp; Select Location -&gt;</option>
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