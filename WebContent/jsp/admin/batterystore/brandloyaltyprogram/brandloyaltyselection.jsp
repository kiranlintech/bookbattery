<%-- Author Of The Document
Copyright Notice : NGIT Private Lmtd
Document   : Brand Loyalty
Created on : March 23, 2013, 7:22:20 PM
Author     : Kishore
--%>
<%@page language="java" import="java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.LogLevel,javax.servlet.ServletContext"%>
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
	session.removeAttribute("sesErrorMsg");
}
%>
<!-- end of scriplet -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link href="../../../../css/bookbattery.css" rel="stylesheet" type="text/css" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../../images/favicon.png" type="image/x-icon">
<title>BookBattery.com - India's No.1 Automobile Battery Store</title>
</head>
<!-- body content starts here-->
<body onload= "displayleftmenu();">
<center>
<form name="loyaltyoptions" method="post" >
<table cellpadding="0" cellspacing="0"  width="960">
	<tr>
		<td>
			<!-- top page starts here  -->
			<jsp:include page = "../header.jsp" />
			<!-- top page ends here  -->
		</td>
	</tr>
	<tr>
		<td>
			<table width="960" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
				<tr>
					<td width="210" align="left" valign="top" bgcolor="#FFFFFF">
						<table width="200" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<!-- categories starts here  -->
									<jsp:include page="../batteryadminleftmenu.jsp"/>
									<!-- categories ends here  -->
								</td>
							</tr>
						</table>
					</td>
					<td width="750" align="left" valign="top">
						<table width="750" border="0" cellspacing="0" cellpadding="0">
							<!-- your page content starts here  -->
							<table width="750" border="0" cellpadding="5" cellspacing="0" bgcolor="#FFFFFF">
								<tr>
									<td>
										<table width="713" cellspacing="1" cellpadding="0">
											<tr>
												<td class="subheading" size="2">Loyalty Management Options:: </td>
											</tr>
											<tr>
												<td>
													<table width="100%" cellspacing="1" bgcolor="#BEADCB" cellpadding="0">
														<tr bgcolor="#FFFFFF">
															<td bgcolor="#88689f" align="center" class="subheading"><font color="#FFFFFF">Loyalty Management Options</font></td>
														</tr>
														<tr bgcolor="#FFFFFF">
															<td bgcolor="#FFFFFF" align="center">
																<table width="50%" align="center" cellspacing="0" bgcolor="#FFFFFF" cellpadding="5">
																	<tr>
																		<td colspan="2" height="20">&nbsp;</td>
																	</tr>
																	<tr>
																		<td class="table2" align="right" width="50%"><input type="radio" name="rdbWhatSelected" value="loyaltyManagement" ></td>
																		<td class="table2" align="left" width="50%"><a href="../../../../servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement" class="table2"  style="text-decoration:none" class="insidecontent">Setup Loyalty Program</a></td></tr>
																	<tr>
																		<td class="table1" align="right" width="50%"><input type="radio" name="rdbWhatSelected" value="loyaltyManagement1" checked ></td>
																		<td class="table1" align="left" width="50%"><a href="../../../../servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement1" class="table1"  style="text-decoration:none" class="insidecontent">Add Loyalty Points</a></td>
																	</tr>
																	<tr>
																		<td class="table2" align="right" width="50%"><input type="radio" name="rdbWhatSelected" value="redeem" checked ></td>
																		<td class="table2" align="left" width="50%"><a href="../../../../jsp/admin/batterystore/brandloyaltyprogram/selectdates.jsp" class="table2"  style="text-decoration:none" class="insidecontent">Redeem Loyalty Points</a></td>
																	</tr>
																	<tr>
																		<td class="table1" align="right" width="50%"><input type="radio" name="rdbWhatSelected" value="checkrecharge" checked ></td>
																		<td class="table1" align="left" width="50%"><a href="../../../../jsp/admin/batterystore/brandloyaltyprogram/checkrecharge.jsp" class="table1"  style="text-decoration:none" class="insidecontent">Check Recharge</a></td>
																	</tr>
																	<tr>
																		<td  align="center" colspan="9">
																			<table border="0" align="center">
																				<td>
																					<tr>
																						<td  class="button4"><a href="javascript:onClickSubmit();" target="_self" class="smallbutton">Submit</a></td>
																					</tr>
																				</td>
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
							<!-- your page content ENDS here  -->
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
</form>
</center>
</body>
<!-- body content ends here-->
<!--javascript starts here-->

<script language="javascript">

// This function is used for the on click link option to select options.
function  onClickLink(opt)
{
		document.forms["loyaltyoptions"].rdbWhatSelected[opt].checked=true;
		onClickSubmit();
}
// This function is used to check one of the options(Setup Loyalty / AddPoints / RedeemPoints) to navigate to another Jsp page.
function onClickSubmit()
{
		var varRadioGroup = document.forms["loyaltyoptions"].rdbWhatSelected;
		var reportType="overall";
		for (i=0;i<varRadioGroup.length;i++)
		{
			if(varRadioGroup[i].checked)
			{
				reportType=varRadioGroup[i].value;
			}
		}
		if(reportType == "redeem")
		{
			document.loyaltyoptions.method="Post";
			document.loyaltyoptions.action="../../../../jsp/admin/batterystore/brandloyaltyprogram/selectdates.jsp";
			document.loyaltyoptions.submit();
		}
		else if(reportType=="checkrecharge")
		{
			document.loyaltyoptions.method="Post";
			document.loyaltyoptions.action="../../../../jsp/admin/batterystore/brandloyaltyprogram/checkrecharge.jsp";
			document.loyaltyoptions.submit();
		}
		else
		{
			document.loyaltyoptions.method="Post";
			document.loyaltyoptions.action="../../../../servlet/BrandLoyalty?hidWhatToDo="+reportType;
			document.loyaltyoptions.submit();
		}
}
</script>
<!-- end of javascript-->
</html>
