<%--
    Document   : confirmedordereddetails.jsp
    Created on : Jan 25th, 2016
    Author     : Lavanya Chowdary.
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
Vector alist=(Vector)session.getAttribute("sesServicesOrderstatusVectorVector");
LogLevel.DEBUG(1,new Throwable(),"alist: "+alist);
//out.println(alist);

Hashtable htOptions=(Hashtable)session.getAttribute("sesOptions");
String orderedstatus=(String)htOptions.get("orderstatus"); 
String FromDate=(String)htOptions.get("txtFromDate");
String ToDate=(String)htOptions.get("txtToDate");

String strDateOpt=request.getParameter("dates");
if(strDateOpt==null || strDateOpt.equals(""))
	strDateOpt="current";

String strFromDate=(request.getParameter("txtFromDate")!=null)?request.getParameter("txtFromDate"):"";
String strToDate=(request.getParameter("txtToDate")!=null)?request.getParameter("txtToDate"):"";
String city=(request.getParameter("city")!=null)?request.getParameter("city"):"";
String keyword=(request.getParameter("keyword")!=null)?request.getParameter("keyword"):"";
// Keep all the headings in session
Vector vectHeaderwithord = new Vector();
vectHeaderwithord.add("ORD No");
vectHeaderwithord.add("Cust Name");
vectHeaderwithord.add("Cust Emailid");
vectHeaderwithord.add("Cust Mobile");
vectHeaderwithord.add("Ret Name");
vectHeaderwithord.add("Ret Mobile");
vectHeaderwithord.add("services type");
vectHeaderwithord.add("services package");
vectHeaderwithord.add("City");
vectHeaderwithord.add("Price");
vectHeaderwithord.add("Discountprice");
vectHeaderwithord.add("Ordered Status");
vectHeaderwithord.add("Date");
vectHeaderwithord.add("Postponed Reason");
session.removeAttribute("sesHeaderwithcat");
session.setAttribute("sesHeaderwithcat",vectHeaderwithord);


// put the body data in session
session.removeAttribute("sesBodywithcat");
session.setAttribute("sesBodywithcat",alist);

// Put the tile in session
session.removeAttribute("sesTitlewithcat");
session.setAttribute("sesTitlewithcat",orderedstatus);
session.setAttribute("sesFromDate",FromDate);
session.setAttribute("sesToDate",ToDate);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../images/favicon.png" type="image/x-icon">
<title>BookBattery.com-India's No.1 Automobile Battery Store</title>
<script language="javascript">
function onClickExportToExcel()
	{
		strUrl = "../../../servlet/BatteryExcelandPdfServlet?hidWhatToDo=ExportToExcelorder";
		window.open(strUrl,"ExcelOpen","height=450,width=530,toobar=0,location=0,directories=0,status=yes,menubar=0,scrollbars=yes,resizable=1"); 
	}
	function onClickExportToPdf()
	{
		strUrl = "../../../servlet/BatteryExcelandPdfServlet?hidWhatToDo=ExportToPDForder";
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
<form name="misbatstatus">
<!-- BookBattery Services Header Starts -->
<tr>
	<jsp:include page = "../header.jsp" />
</tr>
<!-- BookBattery Services Header Ends -->
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
				<!-- Starts operator admin left Menu -->
				<tr>
					<jsp:include page="../operatorleftmenu.jsp"/>
				</tr>
				<!-- Ends operator admin left Menu -->
				</table>
			</td>
			<td width="75%" align="left" valign="top">
				<table width="100%" border="1" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">
					<!-- your page content starts here  -->
					<table width="100%" cellspacing="1" align="center" bgcolor="#FFFFFF" cellpadding="0" border="0">
					<table width="450" border="0" align="center" cellpadding="5" cellspacing="0" bgcolor="#FFFFFF">
					<% if(keyword.equals("confirm"))
					{
					%>
					<tr><td align="left" class="subheading"></td><td width="50%"align="right"><a href="../../../jsp/operator/mis/misselconfirm.jsp?keyword=<%=keyword%>&dates=<%=strDateOpt%>&txtFromDate=<%=strFromDate%>&txtToDate=<%=strToDate%>" class="onclick1">Back</a></td></tr>
					<%
					}
					else if(keyword.equals("postponed"))
					{
					%>
					<tr><td align="left" class="subheading"></td><td width="50%"align="right"><a href="../../../jsp/operator/mis/selectlocation.jsp?keyword=<%=keyword%>&txtFromDate=<%=strFromDate%>&txtToDate=<%=strToDate%>" class="onclick1">Back</a></td></tr>
					<%
					}
					else
					{
					%>
					<tr><td align="left" class="subheading"></td><td width="50%"align="right"><a href="../../../jsp/operator/mis/misselnonconfirm.jsp?keyword=<%=keyword%>&dates=<%=strDateOpt%>&txtFromDate=<%=strFromDate%>&txtToDate=<%=strToDate%>" class="onclick1">Back</a></td></tr>
					<%
					}
					%>
					<tr>
						<td>
							<table width="450" align="center" cellspacing="1" cellpadding="0">
							<tr><td height="20"></td></tr>
							<tr>
								<td class="subheading" align="left" size="2"><%=strDateOpt%> >> <%=htOptions.get("orderstatus")%>&nbsp;Orders >> <%=city%></td>
							</tr>
							<tr>
								<td>
									<table width="100%" cellspacing="1" bgcolor="#8B8D90" cellpadding="0">
									<tr>
										<td>
											<table width="100%" cellspacing="1" bgcolor="#8B8D90" cellpadding="0">
											<tr>
												<td bgcolor="#0081C7" align="center" class="subheading"><font color="#FFFFFF"><%=keyword%>&nbsp;Service&nbsp;Order&nbsp;Details&nbsp;Summary</font></td>
											</tr>
											<tr>
												<td bgcolor="#FFFFFF" align="center" colspan="2">
													<table width="100%" align="center" cellspacing="0" bgcolor="#FFFFFF" cellpadding="5">
													<tr>
														<td  align="center" > 
															<table width="100%" cellspacing="0"  cellpadding="2">
															<tr bgcolor="#DAD0E1">
																<td width="30%"  align="left" class="insidecontent" >&nbsp;Location</td><td align="left" class="insidecontent" >:&nbsp;<%=htOptions.get("city")%></td>
															</tr>
															<tr bgcolor="#DAD0E1">
																<td width="30%"  align="left" class="insidecontent" >&nbsp;Ordered&nbsp;Status</td><td align="left" class="insidecontent" >:&nbsp;<%=htOptions.get("orderstatus")%></td>
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
																		<td align="center"><a href="javascript:onClickExportToExcel();" class="onclick1"><img src="../../../images/export_xls.gif" border="0"/>&nbsp;Export as Excel</a></td>
																		<td align="center"><a href="javascript:onClickExportToPdf();" class="onclick1"><img src="../../../images/pdf_xls.gif" border="0"/>&nbsp;Export as PDF</a></td>
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
																		<td align="center" class="subheading"><font color="#000000">ORD&nbsp;No</font></td>
																		<td align="center" class="subheading"><font color="#000000">Cus&nbsp;Name</font></td>
																		<td align="center" class="subheading"><font color="#000000">Cus&nbsp;Emailid</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Cus&nbsp;MObile</font></td>
																		<td align="center" class="subheading"><font color="#000000">Ret&nbsp;Name</font></td>
																		<td align="center" class="subheading"><font color="#000000">Ret&nbsp;Mobile</font></td>
																		<td align="center" class="subheading"><font color="#000000">services&nbsp;Type</font></td>
																		<td align="center" class="subheading"><font color="#000000">services&nbsp;package</font></td>
																		<td align="center" class="subheading"><font color="#000000">Veh&nbsp;Make</font></td>
																		<td align="center" class="subheading"><font color="#000000">Veh&nbsp;Model</font></td>
																		<td align="center" class="subheading"><font color="#000000"> City</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Area</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Price</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Discountprice</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Ord&nbsp;Status</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Date</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Cus&nbsp;Invoice</font></td>
																		<%
																		if(keyword.equals("postponed"))
																		{
																		%>
																		<td align="center" class="subheading"><font color="#000000"> Postponed&nbsp;date</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Postponed&nbsp;Reason</font></td>
																		<%
																		}
																		%>
																		
	
																	</tr>
																	<%
																	for(int i=0;i<alist.size();i++)
																	{
																		Hashtable ht=(Hashtable)alist.get(i);
																		String ordnum=String.valueOf(ht.get("order_number"));
																		String cusname=(String)ht.get("consumer_name");
																		String cusemailid=(String)ht.get("consumer_emailid");
																		String conmobile=String.valueOf(ht.get("consumer_mobnumber"));
																		String retname=(String)ht.get("retailer_name");
																		String retmobile=String.valueOf(ht.get("retailer_mobilnumber"));
																		String servicestype=(String)ht.get("services_type");
																		String servicespackage=(String)ht.get("services_package");
																		String vehname=(String)ht.get("veh_name");
																		String vehmake=(String)ht.get("veh_model");
																		String city1=(String)ht.get("city");
																		String area=(String)ht.get("area");
																		String price=String.valueOf(ht.get("price"));
																		String obrpprice=String.valueOf(ht.get("Discountprice"));
																		String orderstatus=(String)ht.get("order_status");
																		String date=String.valueOf(ht.get("creation_date"));
																		String postponedate=String.valueOf(ht.get("postpone_date"));
																		String pdfurl=String.valueOf(ht.get("pdfurl"));
																		String postponemessage=String.valueOf(ht.get("postpone_message"));
																		if(vehname.equals("0") || vehname == "0")
																		{
																			vehname = "";
																		}
																		else
																		{
																			vehname=vehname;
																		}
																		if(vehmake.equals("0") || vehmake == "0")
																		{
																			vehmake = "";
																		}
																		else
																		{
																			vehmake=vehmake;
																		}
																		%>
																	<tr bgcolor="#DAD0E1">
																	    <td  class="insidecontent" align="center"><%=(i+1)%> </td>
																		<td  class="insidecontent" align="left"><%=ordnum%> </td>
																		<td  class="insidecontent" align="left"><%=cusname%> </td>
																		<td  class="insidecontent" align="left"><%=cusemailid%> </td>
																		<td  class="insidecontent" align="left"><%=conmobile%> </td>
																		<td  class="insidecontent" align="left"><%=retname%> </td>
																		<td  class="insidecontent" align="left"><%=retmobile%> </td>
																		<td  class="insidecontent" align="left"><%=servicestype%> </td>
																		<td  class="insidecontent" align="left"><%=servicespackage%> </td>
																		<td  class="insidecontent" align="left"><%=vehname%> </td>
																		<td  class="insidecontent" align="left"><%=vehmake%> </td>
																		<td  class="insidecontent" align="left"><%=city1%> </td>
																		<td  class="insidecontent" align="left"><%=area%> </td>
																		<td  class="insidecontent" align="left"><%=price%> </td>
																		<td  class="insidecontent" align="left"><%=obrpprice%> </td>
																		<td  class="insidecontent" align="left"><%=orderstatus%> </td>
																		<td  class="insidecontent" align="left"><%=date%> </td>
																		<% if(pdfurl.equals("") || pdfurl == "NULL")
																		{
																		%>
																		<td  class="insidecontent" align="center">No PDF</td>
																		<%
																		}
																		else
																		{
																		%>
																		<td  class="insidecontent" align="center"><a href='<%=pdfurl%>' target="new"><img src="../../../images/pdf_xls.gif" border="0"/></td>
																		<%
																		}
																		%>
																		<%
																		if(keyword.equals("postponed"))
																		{
																		%>
																		<td  class="insidecontent" align="left"><%=postponedate%> </td>
																		<td  class="insidecontent" align="left"><%=postponemessage%> </td>
																		<%
																		}
																		%>
																		
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
<input type="hidden" name="city" value="<%=city%>">
<input type="hidden" name="keyword" value="<%=keyword%>">
</form>
</center>
</body>
</html>