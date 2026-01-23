<%-- 
	Document   : brandmodifypoints
	Created on : 3/2/2012,4:32 PM
	Author     : Manjunath G,Bhanu Prasad N
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.util.Vector,java.util.Hashtable,com.ngit.javabean.loglevel.*,java.util.Enumeration"%>
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
Vector alist=(Vector)session.getAttribute("sesPoints1");
LogLevel.DEBUG(5, new Throwable(), "alist :" + alist);
String pagenumber = (request.getParameter("pagenumber")!=null)?(request.getParameter("pagenumber")):"0";
LogLevel.DEBUG(5,new Throwable(),"pagenumber :"+pagenumber );
String selectedemailId = (request.getParameter("emailid") != null)?(request.getParameter("emailid")) : "0";
LogLevel.DEBUG(5, new Throwable(), "selectedemailId :" + selectedemailId);

%>
<!-- end of scriplet -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../../images/favicon.png" type="image/x-icon">
<title>BookBattery.com-Online Battery Store</title>
<script language="JavaScript" src="../../../../js/jquery-1.3.2.min.js" ></script>
<script type="text/javascript" src="../../../../js/pophide.js"></script>
<link href="../../../../css/bookbattery.css" rel="stylesheet" type="text/css" />
<!--javascript starts here-->

<script type="text/JavaScript">
// This function is used to  trim the loyalpoints.
function Trim(loyalpoints)
{
	while (loyalpoints.substring(0,1) == ' ') // check for white spaces from beginning
	{
		loyalpoints = loyalpoints.substring(1, loyalpoints.length);
	}
	while (loyalpoints.substring(loyalpoints.length-1, loyalpoints.length) == ' ') // check white space from end
	{
	loyalpoints = loyalpoints.substring(0,loyalpoints.length-1);
	}
	return loyalpoints;
}
// This function is used to update loyaltypoints.
function updateLoyaltyPoints()
{
	var emailid=document.modifypoints.emailID.value;
	var loyalprogramid=document.modifypoints.program.value;
	if(emailid == "")
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter Email-id.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	if(loyalprogramid == 0)
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please select Loyalty Program.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_pgrm();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	var loyalpointsArray=document.getElementsByName("pointsearned");
	var loyalpoints=loyalpointsArray[0].value;
	var pointsArray=document.getElementsByName("points");
	var points=pointsArray[0].value;
	var valueArray=document.getElementsByName("value");
	var value=valueArray[0].value;
	var addArray=document.getElementsByName("addpoints");
	var addpoints=addArray[0].value;
	var loyalprograms=document.modifypoints.program.value;
	var earnpoints=Trim(addpoints);
	if(earnpoints == "")
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter loyalty points.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_points();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	if(earnpoints == 0)
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter more than Zero.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_points();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		document.addloyalty.loyalpoints.focus();
		return;
	}
	if(earnpoints != "")
	{
		var checkOK = "0123456789";
		var checkStr = earnpoints;
		var allValid = true;
		var decPoints = 0;
		var allNum = "";
		for (i = 0;  i < checkStr.length;  i++)
		{
			ch = checkStr.charAt(i);
			for (j = 0;  j < checkOK.length;  j++)
			if (ch == checkOK.charAt(j))
			break;
			if (j == checkOK.length)
			{
				allValid = false;
				break;
			}
		}
		if (!allValid)
		{
			errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter only digits in the \"Add Points\" field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_points();' class='button4'><br></td></tr><tr height='15'></tr></table>";
			document.getElementById("scrollpopup").style.display='block';
			document.getElementById("scrollpopup").innerHTML=errMsg
			document.getElementById("emailok").focus();
			greyout(true);
			document.addloyalty.loyalpoints.focus();
			return;
		}
	}
	var newvalue="";
	var add = parseFloat(loyalpoints) + parseFloat(addpoints);
	newvalue=(add*value)/points;
	var varOne = Math.ceil(newvalue);
	if(varOne < 1)
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter more points to get value greater than zero.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_points();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
	}
	document.modifypoints.method="post";
	document.modifypoints.action="../../../../servlet/BrandLoyalty?hidWhatToDo=updateloyaltypoints&email_id="+emailid+"&loyalprogramid="+loyalprogramid+"&loyaltypoints="+add;			
	document.modifypoints.submit();
}

// This function is used to have userchesk.
function userCheck()
{
	var username=document.modifypoints.emailID.value;
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
				resp = xmlhttp.responseText;
				if(resp.indexOf("This user does not exist in BookBattery")>=0)
				{
					document.modifypoints.emailID.value="";
					document.getElementById("divusercheck").innerHTML = resp;
					return;
				}
				else if(resp.indexOf("This user exist in BookBattery")>=0)
				{
					document.getElementById("divusercheck").innerHTML = resp;
					return;	 
				}
				document.getElementById("divusercheck").innerHTML = resp;
				return;
			}
		}			
	}
	xmlhttp.open("POST","../../../../servlet/BrandLoyalty?hidWhatToDo=userCheck&username="+username,true);		
	xmlhttp.send();	
}
// This function is used to select program
function getSelProgram()
{
	var emailid=document.modifypoints.emailID.value;
	if(emailid == "")
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter Email-id.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		document.modifypoints.emailID.focus();
		return;
	}
	var loyaltyid=document.modifypoints.program.value;
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
				resp = xmlhttp.responseText;
				if(resp=="ERROR")
				{
					alert("Error occurred.Please try again.");
					return;
				}
				document.getElementById("divpoints").innerHTML = resp;
				document.getElementById("divpoints").innerHTML = xmlhttp.responseText;
			}
		}			
	}
	xmlhttp.open("POST","../../../../servlet/BrandLoyalty?hidWhatToDo=getSelProgram&emailid="+emailid+"&loyaltyid="+loyaltyid,true);		
	xmlhttp.send();
}
// This functions is to hide popup of email and to place cusrsors on the emailfields.
function close_popup_email()
{
	$('#scrollpopup').hide();
	greyout(false);
	document.modifypoints.emailID.value="";
}
function close_popup_pgrm()
{
	$('#scrollpopup').hide();
	greyout(false);
	document.modifypoints.program.focus();
}
function close_popup_points()
{
	$('#scrollpopup').hide();
	greyout(false);
	document.modifypoints.addpoints.value="";
	document.modifypoints.addpoints.focus();
}
function closeScrollPopup()
{
	$('#scrollpopup').hide();
	greyout(false);
}
/*  This function is used to focus on addloyaltypoints on click.*/
function onfocus()
{
	document.modifypoints.program.focus();
	return;
}
</script>
<!--javascript ends here-->
</head>
<!-- body content starts here -->
<body onload=" onfocus(); displayleftmenu();" onkeydown="if($('#scrollpopup').is(':visible')){ if(event.keyCode==9){ return false;}};">
<center>
<form name="modifypoints" method="post"  ENCTYPE="multipart/form-data">
	<div id='scrollpopup' class='scrollpopup' style="display:none;"></div>
<table cellpadding="0" cellspacing="0" border="0" width="960">
	<tr>
		<td>
			<!-- top page starts here  -->
			<jsp:include page = "../header.jsp" />
			<!-- top page ends here  -->
		</td>
	</tr>
	<tr>
		<td>
			<table width="960" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#ffffff">
				<tr>
					<td width="210" align="left" valign="top" bgcolor="#FFFFFF">
						<table width="200" border="0s" cellspacing="0" cellpadding="0">
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
						<table width="513" border="0" cellspacing="0" cellpadding="0">
							<!-- your page content starts here  -->
							<table width="770" border="0" cellpadding="5" cellspacing="0" bgcolor="#FFFFFF">
								<tr>
									<td>
										<table width="713" border="0" cellpadding="5" cellspacing="0" bgcolor="#FFFFFF">
											<tr>
												<td class="subheading"> <font size="2">Loyalty Management Options>>Loyalty User Management>>Modify Loyalty Points::</td>
											</tr>
												<%
												String stremailid="";
												String strloyaltyname="";
												try
												{
												for(int i=0;i<alist.size();i++)
												{
												Hashtable ht=(Hashtable)alist.get(i);
												stremailid=(String)ht.get("email_id");
												LogLevel.DEBUG(5,new Throwable(),"stremailid :"+stremailid);
												if(stremailid==null)
												{
												stremailid="";
												}
												else
												{
												stremailid=stremailid;
												}
												}
												%>
											<tr>
												<td>
													<table width="100%" border="0" cellspacing="1" bgcolor="#BEADCB" cellpadding="0">
														<tr>
															<td>
																<table width="100%" border="0" cellspacing="0" bgcolor="#FFFFFF" cellpadding="0" >
																	<tr height="10">
																		<td colspan="4" align="center"></td>
																	</tr>
																	<tr  bgcolor="FFFFFF">
																		<td class="subheading" ></td>
																		<td colspan="3"><input type="text" name="email_id" value="<%=stremailid%>" size="31"  class="noborder" readonly="readonly" style="display:none"></td>
																	</tr>
																	<tr class="#FFFFFF" bgcolor="FFFFFF">
																		<td width="30%" class="insidecontent" >&nbsp;&nbsp;UserID/EmailID<font color="FF0000">*</font></td>
																		<td width="10%">:&nbsp;<input type="text" disabled="true" name="emailID"  value="<%=stremailid%>" maxlength="50" size="31" onchange="javascript:userCheck()"></td>
																		<td colspan="3" class="insidecontent"><div id="divusercheck"></div></td>
																	</tr>
																	<tr height="10"><td colspan="4" align="center"></td></tr>
																	<tr class="#FFFFFF" bgcolor="FFFFFF">
																		<td align="left" valign="middle" class="insidecontent">&nbsp;&nbsp;Select Loyalty Program<font color = "#FF0000">*</font></td>
																		<td>:&nbsp;<select name = "program"onChange = "javascript: getSelProgram()" class = "top1"> 
																			<option value = "0">&lt;-----------------Select----------------&gt;</option>
																				<%
																				try
																				{
																				if (alist!= null&& alist.size()> 0)
																				{
																				String strsel ="";
																				for (int j =0;j<alist.size();j++)
																				{
																				Hashtable ht1 =(Hashtable)alist.get(j);
																				String progname =(String)ht1.get("loyalty_name");
																				String loyaltyid = String.valueOf(ht1.get("loyalty_id"));
																				if (selectedemailId!= null && selectedemailId.equals(stremailid))
																				{
																				strsel = "selected";
																				}
																				else
																				{
																				strsel= "";
																				}
																				%>
																				<option value = "<%=loyaltyid%>"<%=strsel %>><%=progname%></option>
																				<%
																				}
																				}
																				}
																				catch(Exception e)
																				{
																				e.printStackTrace();
																				}
																				%>
																			</select>
																		</td>
																	</tr>
																	<tr bgcolor="FFFFFF">
																		<td class="insidecontent" colspan="4">
																			<table align="center" border="0" bgcolor="#FFFFFF" cellpadding="0" cellspacing="1">
																				<tr class="insidecontent" height="50" align="center" valign="middle">
																					<td class="insidecontent"><div id="divpoints"></div></td>
																				</tr>
																			</table>
																		</td>
																	</tr>
																	<tr bgcolor="FFFFFF">
																		<td class="subheading" colspan="4">
																			<table align="center" border="0" bgcolor="#FFFFFF" cellpadding="0" cellspacing="1">
																				<tr height="20">
																					<td >&nbsp;&nbsp;&nbsp;<a href="javascript:updateLoyaltyPoints()" class="smallbutton">Submit</a>&nbsp;&nbsp;&nbsp;</td>
																					<td >&nbsp;&nbsp;&nbsp;<a href="../../../../jsp/admin/batterystore/brandloyaltyprogram/brandmodifypoints.jsp" class="smallbutton">Reset</a>&nbsp;&nbsp;&nbsp;</td>
																					<td>&nbsp;&nbsp;&nbsp;<a href="../../../../servlet/BrandLoyalty?hidWhatToDo=loyaltyManagement1" class="smallbutton">Back</a>&nbsp;&nbsp;&nbsp;</td>
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
												<%
												}
												catch(Exception e)
												{
												e.printStackTrace();
												}
												%>
										</table>
									</td>
								</tr>
							</table>
							<!-- your page content ends here  -->
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
<input type="hidden" name="hidWhatToDo" value="add">
</form>
</center>
</body>
<!-- body  content ends here-->
</html>
