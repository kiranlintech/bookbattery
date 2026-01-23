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
Vector alist=(Vector)session.getAttribute("sesBatHealthChkVector");
LogLevel.DEBUG(1,new Throwable(),"alist: "+alist);

Hashtable htOptions=(Hashtable)session.getAttribute("sesOptions");
String bathealthChks=(String)htOptions.get("bathealthChks"); 
String FromDate=(String)htOptions.get("txtFromDate");
String ToDate=(String)htOptions.get("txtToDate");

String strDateOpt=request.getParameter("dates");
if(strDateOpt==null || strDateOpt.equals(""))
	strDateOpt="current";

String strFromDate=(request.getParameter("txtFromDate")!=null)?request.getParameter("txtFromDate"):"";
String strToDate=(request.getParameter("txtToDate")!=null)?request.getParameter("txtToDate"):"";

// Keep all the headings in session
Vector vectHeaderwithcat = new Vector();
vectHeaderwithcat.add("Bat Type");
vectHeaderwithcat.add("Veh type");
vectHeaderwithcat.add("Veh Model");
vectHeaderwithcat.add("Bat Brand");
vectHeaderwithcat.add("Bat Model");
vectHeaderwithcat.add("Cus Name");
vectHeaderwithcat.add("Cus Mobile");
vectHeaderwithcat.add("Ins Month");
vectHeaderwithcat.add("Ins Year");
vectHeaderwithcat.add("City");
vectHeaderwithcat.add("Area");
vectHeaderwithcat.add("Date");
session.removeAttribute("sesHeaderwithcat");
session.setAttribute("sesHeaderwithcat",vectHeaderwithcat);


// put the body data in session
session.removeAttribute("sesBodywithcat");
session.setAttribute("sesBodywithcat",alist);

// Put the tile in session
session.removeAttribute("sesTitlewithcat");
session.setAttribute("sesTitlewithcat",bathealthChks);
session.setAttribute("sesFromDate",FromDate);
session.setAttribute("sesToDate",ToDate);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../../images/favicon.png" type="image/x-icon">
<title>BookBattery.com - India's No.1 Automobile Battery Store</title>
<script language="javascript">
function onClickExportToExcel()
	{
		strUrl = "../../../../servlet/BatteryExcelandPdfServlet?hidWhatToDo=ExportToExcelbat";
		window.open(strUrl,"ExcelOpen","height=450,width=530,toobar=0,location=0,directories=0,status=yes,menubar=0,scrollbars=yes,resizable=1"); 
	}
	function onClickExportToPdf()
	{
		strUrl = "../../../../servlet/BatteryExcelandPdfServlet?hidWhatToDo=ExportToPDFbat";
		window.open(strUrl,"ExcelOpen","height=450,width=530,toobar=0,location=0,directories=0,status=yes,menubar=0,scrollbars=yes,resizable=1"); 
	}

</script>
<style type="text/css">
<!--
body 
{
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	/*background-image: url(../../../../images/index_01_01.gif);
	background-repeat: repeat-x;*/
}
-->
</style>
<link href="/bookbattery.css/bookbattery.css" rel="stylesheet" type="text/css" />
</head>
<body onload="" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="healthchecksummary">
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
					<tr><td align="left" class="subheading"></td><td width="50%"align="right"><a href="../../../../jsp/admin/batterystore/mis/healthcheckmisoption.jsp?dates=<%=strDateOpt%>&txtFromDate=<%=strFromDate%>&txtToDate=<%=strToDate%>" class="onclick1">Back</a></td></tr>
					<tr>
						<td>
							<table width="450" align="center" cellspacing="1" cellpadding="0">
							<tr><td height="20"></td></tr>
							<tr>
								<td class="subheading" align="left" size="2"><%=strDateOpt%> >> <%=htOptions.get("bathealthChks")%></td>
							</tr>
							<tr>
								<td>
									<table width="100%" cellspacing="1" bgcolor="#8B8D90" cellpadding="0">
									<tr>
										<td>
											<table width="100%" cellspacing="1" bgcolor="#8B8D90" cellpadding="0">
											<tr>
												<td bgcolor="#0081C7" align="center" class="subheading"><font color="#FFFFFF">Battery&nbsp;Health&nbsp;Check&nbsp;Details&nbsp;Summary</font></td>
											</tr>
											<tr>
												<td bgcolor="#FFFFFF" align="center" colspan="2">
													<table width="100%" align="center" cellspacing="0" bgcolor="#FFFFFF" cellpadding="5">
													<tr>
														<td  align="center" > 
															<table width="100%" cellspacing="0"  cellpadding="2">
															<tr bgcolor="#DAD0E1">
																<td width="30%"  align="left" class="insidecontent" >&nbsp;Option</td><td align="left" class="insidecontent" >:&nbsp;<%=htOptions.get("bathealthChks")%></td>
															</tr>
															<tr bgcolor="#DAD0E1">	
																<td width="#DAD0E1%"  align="left" class="insidecontent" >&nbsp;From Date</td><td align="left" class="insidecontent" >:&nbsp;<%=htOptions.get("txtFromDate")%></td>
															</tr>
															<tr bgcolor="#DAD0E1">
																<td width="30%" align="left" class="insidecontent" >&nbsp;To Date</td><td align="left" class="insidecontent" >:&nbsp;<%=htOptions.get("txtToDate")%></td>
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
																		<td align="center" class="subheading"><font color="#000000">Bat&nbsp;Type</font></td>
																		<td align="center" class="subheading"><font color="#000000">Veh&nbsp;Type</font></td>
																		<td align="center" class="subheading"><font color="#000000">Veh&nbsp;Model</font></td>
																		<td align="center" class="subheading"><font color="#000000">Bat&nbsp;Brand</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Bat&nbsp;Ins&nbsp;Month</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Bat&nbsp;Ins&nbsp;Year</font></td>
																		<td align="center" class="subheading"><font color="#000000">Service Need Date</font></td>
																		<td align="center" class="subheading"><font color="#000000">Cust&nbsp;Name</font></td>
																		<td align="center" class="subheading"><font color="#000000">Cust&nbsp;Mobile</font></td>
																		<td align="center" class="subheading"><font color="#000000">Supp&nbsp;Mobile</font></td>
																		<td align="center" class="subheading"><font color="#000000">Supp&nbsp;Name</font></td>
																		<td align="center" class="subheading"><font color="#000000">City</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Area</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Pincode</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Date</font></td>

																	</tr>
																	<%
																	for(int i=0;i<alist.size();i++)
																	{
																		Hashtable ht=(Hashtable)alist.get(i);
																		String Batchlid=String.valueOf(ht.get("bat_chk_id"));
																		String insmonth=(String)ht.get("bat_ins_month");
																		String insyear=(String)ht.get("bat_ins_year");
																		String vehtype=String.valueOf(ht.get("veh_type"));
																		String vehmodel=(String)ht.get("veh_model");
																		String batype=String.valueOf(ht.get("bat_type"));
																		String batbrand=String.valueOf(ht.get("bat_brand"));
																		String batmodel=(String)ht.get("bat_model");
																		String cusname=(String)ht.get("customer_name");
																		String cusmobile=(String)ht.get("customer_mobilnumber");
																		String supname=String.valueOf(ht.get("support_name"));
																		String suppmobile=String.valueOf(ht.get("support_mobilnumber"));	
																		String city=String.valueOf(ht.get("city"));	
																		String area=String.valueOf(ht.get("area"));	
																		String pincode=String.valueOf(ht.get("pincode"));	
																		String date=String.valueOf(ht.get("creation_date"));
																		String service_need_date=String.valueOf(ht.get("service_need_date"));		

																		if(vehtype.equals("0") || vehtype == "0")
																		{
																			vehtype = "";
																		}
																		else
																		{
																			vehtype=vehtype;
																		}
																		if(vehmodel.equals("0") || vehmodel == "0")
																		{
																			vehmodel = "";
																		}
																		else
																		{
																			vehmodel=vehmodel;
																		}
																		%>
																	<tr bgcolor="#DAD0E1">
																		<td  class="insidecontent" align="center"><%=(i+1)%> </td>
																		<td  class="insidecontent" align="left"><%=batype%> </td>
																		<td  class="insidecontent" align="left"><%=vehtype%> </td>
																		<td  class="insidecontent" align="left"><%=vehmodel%> </td>
																		<td  class="insidecontent" align="left"><%=batbrand%> </td>
																		<td  class="insidecontent" align="left"><%=insmonth%> </td>
																		<td  class="insidecontent" align="left"><%=insyear%> </td>
																		<td  class="insidecontent" align="left"><%=service_need_date%> </td>
																		<td  class="insidecontent" align="left"><%=cusname%> </td>
																		<td  class="insidecontent" align="left"><%=cusmobile%> </td>
																		<td  class="insidecontent" align="left"><%=suppmobile%> </td>
																		<td  class="insidecontent" align="left"><%=supname%> </td>
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