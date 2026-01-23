<%--
    Document   : inverters visitors summary
    Created on : April 2, 2014, 12:22:12 PM
    Author     : Pravallika.
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
Vector alist=(Vector)session.getAttribute("sesBookBatteryvisitorsVectorVector"); 
LogLevel.DEBUG(1,new Throwable(),"alist: "+alist);

Vector alist1=(Vector)session.getAttribute("sesvisitorcountVector"); 
LogLevel.DEBUG(1,new Throwable(),"alist1: "+alist1);

int j=0;
Hashtable htVisitCount=(Hashtable)alist1.get(j);
String visitorscount=String.valueOf(htVisitCount.get("visitors"));

Hashtable htOptions=(Hashtable)session.getAttribute("sesOptions");
//String orderedstatus=(String)htOptions.get("orderstatus"); 
String FromDate=(String)htOptions.get("txtFromDate");
String ToDate=(String)htOptions.get("txtToDate");

String strDateOpt=request.getParameter("dates");
if(strDateOpt==null || strDateOpt.equals(""))
	strDateOpt="current";

String strFromDate=(request.getParameter("txtFromDate")!=null)?request.getParameter("txtFromDate"):"";
String strToDate=(request.getParameter("txtToDate")!=null)?request.getParameter("txtToDate"):"";
String type=(request.getParameter("type")!=null)?request.getParameter("type"):"";


// Keep all the headings in session
Vector vectHeadervisitors = new Vector();
vectHeadervisitors.add("Bat Type"); 
vectHeadervisitors.add("Veh Make");
vectHeadervisitors.add("Veh Model");
vectHeadervisitors.add("Bat Brand");
vectHeadervisitors.add("Bat Capacity");
vectHeadervisitors.add("State");
vectHeadervisitors.add("City");
vectHeadervisitors.add("Area");
vectHeadervisitors.add("Pincode");
vectHeadervisitors.add("Mob Number");
vectHeadervisitors.add("Date");
session.removeAttribute("sesHeaderwithcat");
session.setAttribute("sesHeaderwithcat",vectHeadervisitors);


// put the body data in session
session.removeAttribute("sesBodywithcat");
session.setAttribute("sesBodywithcat",alist);

// Put the tile in session
session.removeAttribute("sesTitlewithcat");
session.setAttribute("sesTitlewithcat",visitorscount);
session.setAttribute("sesFromDate",FromDate);
session.setAttribute("sesToDate",ToDate);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../../images/favicon.png" type="image/x-icon">
<title>BookBattery.com-India's No.1 Automobile Battery Store</title>
<script language="javascript">
function onClickExportToExcel()
	{
		strUrl = "../../../../servlet/BatteryExcelandPdfServlet?hidWhatToDo=ExportToExcelinvertervisitors";
		window.open(strUrl,"ExcelOpen","height=450,width=530,toobar=0,location=0,directories=0,status=yes,menubar=0,scrollbars=yes,resizable=1"); 
	}
	function onClickExportToPdf()
	{
		strUrl = "../../../../servlet/BatteryExcelandPdfServlet?hidWhatToDo=ExportToPDFinvertervisitors";
		window.open(strUrl,"ExcelOpen","height=450,width=530,toobar=0,location=0,directories=0,status=yes,menubar=0,scrollbars=yes,resizable=1"); 
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
<body onload="" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="misbatstatus">
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
					<table width="100%" cellspacing="1" align="center" bgcolor="#FFFFFF" cellpadding="0" border="0">
					<table width="450" border="0" align="center" cellpadding="5" cellspacing="0" bgcolor="#FFFFFF">
					<tr><td align="left" class="subheading"></td><td width="50%"align="right"><a href="../../../../jsp/admin/batterystore/visitors/selectoptions.jsp?&dates=<%=strDateOpt%>&txtFromDate=<%=strFromDate%>&txtToDate=<%=strToDate%>" class="onclick1">Back</a></td></tr>
					<tr>
						<td>
							<table width="450" align="center" cellspacing="1" cellpadding="0">
							<tr><td height="20"></td></tr>
							<tr>
								<td class="subheading" align="left" size="2"><%=strDateOpt%> >> Number Of Visitors</td>
							</tr>
							<tr>
								<td>
									<table width="100%" cellspacing="1" bgcolor="#8B8D90" cellpadding="0">
									<tr>
										<td>
											<table width="100%" cellspacing="1" bgcolor="#8B8D90" cellpadding="0">
											<tr>
												<td bgcolor="#0081C7" align="center" class="subheading"><font color="#FFFFFF">BookBattery&nbsp;<%=type%>&nbsp;visitors&nbsp;summary</font></td>
											</tr>
											<tr>
												<td bgcolor="#FFFFFF" align="center" colspan="2">
													<table width="100%" align="center" cellspacing="0" bgcolor="#FFFFFF" cellpadding="5">
													<tr>
														<td  align="center" > 
															<table width="100%" cellspacing="0"  cellpadding="2">
															<!--<tr bgcolor="#DAD0E1">
																<td width="30%"  align="left" class="insidecontent" >&nbsp;Ordered&nbsp;Status</td><td align="left" class="insidecontent" >:&nbsp;<%=htOptions.get("orderstatus")%></td>
															</tr>-->
															<tr bgcolor="#DAD0E1">	
																<td width="#DAD0E1%"  align="left" class="insidecontent" >&nbsp;From Date</td><td align="left" class="insidecontent" >:&nbsp;<%=htOptions.get("txtFromDate")%></td>
															</tr>
															<tr bgcolor="#DAD0E1">
																<td width="30%" align="left" class="insidecontent" >&nbsp;To Date</td><td align="left" class="insidecontent" >:&nbsp;<%=htOptions.get("txtToDate")%></td>
															</tr>
																	<tr bgcolor="#DAD0E1">
																<td width="30%"  align="left" class="insidecontent" >&nbsp;No Of Visitors</td><td align="left" class="insidecontent" >:&nbsp;<%=visitorscount%></td>
															</tr>
															</table>
														</td>
													</tr>
													<%
													if(alist!=null && alist.size() > 0)
													{
													%>
													<tr bgcolor="#FFFFFF">
															<td  align="center" bgcolor="#FFFFFF"> 
																<table width="50%" cellspacing="5"  cellpadding="5">
																	<tr bgcolor="#dddddd">
																		<td align="center"><a href="javascript:onClickExportToExcel();" class="onclick1"><img src="../../../../images/export_xls.gif" border="0"/>&nbsp;Export as Excel</a></td>
																		<td align="center"><a href="javascript:onClickExportToPdf();" class="onclick1"><img src="../../../../images/pdf_xls.gif" border="0"/>&nbsp;Export as PDF</a></td>
																	 </tr>
																</table>
															</td>
														</tr>
														<tr>
															<td>
															<div style="width:550px;height:200px;  overflow:scroll; overflow-X:auto;  -webkit-overflow-scrolling: touch;">
																<table width="100%" cellspacing="1"  cellpadding="2" bgcolor="#dddddd">
																	<tr  bgcolor="#cccccc">
																		<td align="center" class="subheading"><font color="#000000">Sl&nbsp;No</font></td>
																		<td align="center" class="subheading"><font color="#000000">Inverter&nbsp;Type</font></td>
																		
																		<td align="center" class="subheading"><font color="#000000"> Inverter&nbsp;Brand</font></td>
																		<td align="center" class="subheading"><font color="#000000">Inverter&nbsp;Capacity</font></td>
																		<td align="center" class="subheading"><font color="#000000">Visitor&nbsp;Mob&nbsp;Num</font></td>
																		<td align="center" class="subheading"><font color="#000000">State</font></td>
																		<td align="center" class="subheading"><font color="#000000">City</font></td>
																		<td align="center" class="subheading"><font color="#000000">Area</font></td>
																		<td align="center" class="subheading"><font color="#000000">Pincode</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Date</font></td>
																	</tr>
																	<%
																	for(int i=0;i<alist.size();i++)
																	{
																		Hashtable ht=(Hashtable)alist.get(i);
																		String visid=String.valueOf(ht.get("vis_ord_id"));
																		String invertertype=(String)ht.get("bat_type");																						
																		String inverterbrand=(String)ht.get("bat_brand");
																		String invertercapacity=String.valueOf(ht.get("bat_capacity"));
																		String state=(String)ht.get("state");
																		String city=(String)ht.get("city");
																		String area=(String)ht.get("area");
																		String pincode=(String)ht.get("pincode");
																		String mobilenumber=(String)ht.get("mobile_number");
																		String date=String.valueOf(ht.get("creation_date"));
																		
																		if(state.equals("null") || state == "null")
																		{
																			state = "";
																		}
																		else
																		{
																			state=state;
																		}
																		if(city.equals("null") || city == "null")
																		{
																			city = "";
																		}
																		else
																		{
																			city=city;
																		}
																	%>
																	<tr bgcolor="#DAD0E1">
																		<td  class="insidecontent" align="center"><%=(i+1)%> </td>
																		<td  class="insidecontent" align="left"><%=invertertype%> </td>
																		
																		<td  class="insidecontent" align="left"><%=inverterbrand%> </td>
																		<td  class="insidecontent" align="left"><%=invertercapacity%> </td>
																		<td  class="insidecontent" align="left"><%=mobilenumber%> </td>
																		<td  class="insidecontent" align="left"><%=state%> </td>
																		<td  class="insidecontent" align="left"><%=city%> </td>
																		<td  class="insidecontent" align="left"><%=area%> </td>
																		<td  class="insidecontent" align="left"><%=pincode%> </td>
																		<td  class="insidecontent" align="left"><%=date%> </td>
																	</tr>
																	<%
																	}
																	%>
																	</table>
																</div>
																</table>
															</td>
														</tr>
														<%
														}
														else
														{
														%>
														<tr bgcolor="#CCCCCC">
															<td  class="table1" align="center"><font color="#000000">There are no records in the specified period</font></td>
														</tr>
														<%
														}
														%>
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