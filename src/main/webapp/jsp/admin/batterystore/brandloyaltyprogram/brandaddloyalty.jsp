<%-- Author Of The Document
Copyright Notice : NGIT Private Lmtd
Document   : Brand Loyalty
Created on : March 23, 2013, 7:22:20 PM
Author     : Kishore
--%>
<%@page language="java" import="java.util.Vector,java.util.Hashtable,com.ngit.javabean.loglevel.*,java.util.Enumeration,javax.servlet.ServletContext"%>
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
String strloyaltyname = (request.getParameter("loyalname")!=null)?(request.getParameter("loyalname")):"";
LogLevel.DEBUG(5,new Throwable(),"strloyaltyname :"+strloyaltyname );
String loyalpartner = (request.getParameter("programpartner")!=null)?(request.getParameter("programpartner")):"";
LogLevel.DEBUG(5,new Throwable(),"loyalpartner :"+loyalpartner );
String strimage = (request.getParameter("image")!=null)?(request.getParameter("image")):"";
LogLevel.DEBUG(5,new Throwable(),"strimage :"+strimage );
String allowedImgFormats =  "gif,jpg,jpeg,png,bmp,jpe,wbmp";
Vector alist=(Vector)session.getAttribute("sesAllLoyalVector");
LogLevel.DEBUG(5,new Throwable(),"alist :"+alist );
session.removeAttribute("sesAllLoyalVector");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link href="../../../../css/bookbattery.css" rel="stylesheet" type="text/css" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../../images/favicon.png" type="image/x-icon">
<title>BookBattery.com - India's No.1 Automobile Battery Store</title>
</head>
<!-- body content starts here-->
<body onload="onClickLoyaltyPartner();" onkeydown="if($('#scrollpopup').is(':visible')){ if(event.keyCode==9){ return false;}};">
<center>
<form name="addloyalty" method="post"  ENCTYPE="multipart/form-data">
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
					<td width="680" align="left" valign="top" >
						<table width="513" border="0" cellspacing="0" cellpadding="0">
							<!-- your page content starts here  -->
							<table width="770" border="0" cellpadding="5" cellspacing="0" bgcolor="#FFFFFF">
								<tr>
									<td>
										<table width="713"  cellpadding="5" cellspacing="0" bgcolor="#FFFFFF">
											<tr>
												<td class="subheading"> <font size="2">Loyalty Management Options>>Loyalty Management>>Add Loyalty Program::</td>
											</tr>
											<tr>
												<td>
													<table width="100%" cellspacing="1" bgcolor="#BEADCB" cellpadding="0">
														<tr>
															<td>
																<table border="0" width="100%" cellspacing="0" bgcolor="#FFFFFF" cellpadding="5">
																	<tr height="10">
																		<td colspan="5" align="center"><%=strLogMsg%></td>
																	</tr>
																	<tr class="#FFFFFF" bgcolor="FFFFFF">
																		<td>
																			<table width="100%" border="0" cellspacing="3" bgcolor="#FFFFFF" cellpadding="0" >
																				<tr height="10">
																					<td class="subheading" width="148" >Loyalty Program Name<font color="FF0000">*</font></td>
																					<td >:&nbsp;<input type="text" name="loyaltyname"  value="" size="31" maxlength='50'  onchange="javascript:loyaltyCheck()"></td>
																					<td width="200"colspan="3" class="insidecontent"><div id="divloyalcheck"></div></td>
																				</tr>
																			</table>
																			<table width="100%" border="0" cellspacing="3" bgcolor="#FFFFFF" cellpadding="0" >
																				<tr class="#FFFFFF" bgcolor="FFFFFF">
																					<td class="subheading" width="150">No. of points<font color="FF0000">*</font></td>
																					<td width="525">:&nbsp;<input type="text" name="loyaltypoints" size="31" maxlength='4'></td>
																				</tr>
																				<tr class="#FFFFFF" bgcolor="FFFFFF">
																					<td class="subheading" >Value(Rs)<font color="FF0000">*</font></td>
																					<td width="525" >:&nbsp;<input type="text" name="rupeevalue"  size="31" maxlength='4'></td>
																				</tr>
																				<tr class="#FFFFFF">
																					<td class="subheading" width="150">Image</td>
																					<td width="525">:&nbsp;<input type="file" name="image"  value="<%=strimage%>" maxlength="50" size="31"></td>
																				</tr>
																				<tr>
																					<td></td>
																					<td><font size="1">[Please check your image file name should not consists of spaces and special characters]</font></td>
																				</tr>
																				<tr class="#FFFFFF" bgcolor="FFFFFF">
																					<td class="subheading" width="150" >Program Description<font color="FF0000">*</font></td>
																					<td valign="center" width="525">:&nbsp;<textarea name = "desc" rows = "8" cols = "26" id="desc"  maxlength="150" style="overflow:hidden" onkeyup="limitText(document.addloyalty.countdown.value,150);"
																					onchange="limitText(document.addloyalty.countdown.value,150);"></textarea>&nbsp;&nbsp;<font size="2"><input readonly="" type="text" name="countdown" size="2" value="150" disabled='true'>Characters Left </font></td>
																				</tr>
																				<tr bgcolor="FFFFFF" >
																					<td class="subheading" colspan="2">
																						<table border="0" width="100%" align="left">
																							<tr bgcolor="FFFFFF" height="310">
																								<td width="150" class="subheading" valign="center">Loyalty Program Partners:</td>
																								<td valign="top" class="insidecontent"><div id="divpartner1"></div></td>
																							</tr>
																						</table>
																					</td>
																				</tr>	
																				<tr bgcolor="FFFFFF"><td class="subheading" colspan="4"></td></tr>
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
							<!-- your page content ends here  -->
						</table>
					</td>
				</tr>
			</table>
			<tr>
		<td>
			<!-- footer page starts here  -->
			<jsp:include page = "../footer.jsp" />
			<!-- top page ends here  -->
		</td>
	</tr>
		</td>
	</tr>
</table>
<input type="hidden" name="hidWhatToDo" value="add">
<input type="hidden" name="programpartner" value="<%=loyalpartner%>">
</form>
</center>
</body>
<!-- body content ends here-->
<script language="JavaScript" src="../../../../js/jquery-1.3.2.min.js" ></script>
<script type="text/javascript" src="../../../../js/pophide.js"></script>
<script type="text/javascript">
(function(){var po=document.createElement('script');po.type = 'text/javascript';po.async=true;
po.src = "../../../../js/jquery-1.3.2.min.js" ;
var s=document.getElementsByTagName('script')[0];s.parentNode.insertBefore(po,s);})();
</script>

<script type="text/javascript">
(function(){var po=document.createElement('script');po.type = 'text/javascript';po.async=true;
po.src = "../../../../js/pophide.js" ;
var s=document.getElementsByTagName('script')[0];s.parentNode.insertBefore(po,s);})();
</script>
<!--javascript starts here-->
<script type="text/JavaScript">

//This function is used to  focus the cursor in loyalty program name field on , loading page.
function onfocus()
{
	var loyalname=document.addloyalty.loyaltyname.value;
	if(loyalname=="")
	{
		document.addloyalty.loyaltyname.focus();
		return;
	}
	else
	{
		document.addloyalty.loyaltypoints.focus();
		return;
	}
}
//This function is used to  trim the loyalty points.

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

//This function is used to  trim the loyalvalue.

function Trim(loyalvalue)
{
    while (loyalvalue.substring(0,1) == ' ') // check for white spaces from beginning
    {
        loyalvalue = loyalvalue.substring(1, loyalvalue.length);
    }
    while (loyalvalue.substring(loyalvalue.length-1, loyalvalue.length) == ' ') // check white space from end
    {
        loyalvalue = loyalvalue.substring(0,loyalvalue.length-1);
    }
    return loyalvalue;
}

function limitText(limitCount,limitNum) 
{
	if (document.addloyalty.desc.value.length > limitNum)
	{
		document.addloyalty.desc.value= document.addloyalty.desc.value.substring(0,limitNum);
		document.addloyalty.countdown.value=0;
	}
	else
	{
		document.addloyalty.countdown.value = limitNum - document.addloyalty.desc.value.length;
	}
}

// This function is used to  add a loyalty program.
function addLoyaltyProgram(selbrand)
{
	var loyalname=document.addloyalty.loyaltyname.value;
	var loyalpoints=document.addloyalty.loyaltypoints.value;
	var loyalvalue=document.addloyalty.rupeevalue.value;
	var loyalpartner=document.addloyalty.programpartner.value;
	var image=document.addloyalty.image.value;

	var progdesc1=escape(document.addloyalty.desc.value);
	var progdesc=progdesc1.replace(/\+/g, '%2B');

	if(loyalname == "")
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter loyalty program name.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_loyaltyname();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	if(loyalname == 0)
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter Loyalty program name.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_loyaltyname();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	if(document.addloyalty.loyaltyname.value.length < 3)
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter atleast 3 characters in the \"Loyalty program name\" Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_loyaltyname();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	if(loyalname != "")
	{
		var checkOK = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		var checkStr = loyalname;
		var allValid = true;
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
			errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter only letters and numeric characters in the \"Loyalty program name\" Field without spaces.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_loyaltyname();' class='button4'><br></td></tr><tr height='15'></tr></table>";
			document.getElementById("scrollpopup").style.display='block';
			document.getElementById("scrollpopup").innerHTML=errMsg
			document.getElementById("emailok").focus();
			greyout(true);
			return;
		}
	}
	if (/[a-z][A-Z]{2}/i.test(document.addloyalty.loyaltyname.value) != true) 
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter atleast three characters together in \"Loyalty program name\" Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_loyaltyname();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	var selectedArray = new Array();
	count = 0;
	for(x=0; x<document.addloyalty.programpartner.length; x++)
	{
		if(document.addloyalty.programpartner[x].checked==true)
		{
			selectedArray[count]= document.addloyalty.programpartner[x].value;
			count++;
		}
	}	
	var points=Trim(loyalpoints);
	if(points == "")
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter loyalty points.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_points();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	if(points == 0)
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter loyalty points greater than zero.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_points();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	if(points != "")
	{
		var checkOK = "0123456789";
		var checkStr = points;
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
			errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter only digits in the Loyalty Points Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_points();' class='button4'><br></td></tr><tr height='15'></tr></table>";
			document.getElementById("scrollpopup").style.display='block';
			document.getElementById("scrollpopup").innerHTML=errMsg
			document.getElementById("emailok").focus();
			greyout(true);
			return;
		}
	}
	var value=Trim(loyalvalue);
	if(value == "")
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter Value.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_value();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	if(value == 0)
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter Value greater than zero.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_value();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	if(value != "")
	{
		var checkOK = "0123456789";
		var checkStr = value;
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
			errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter only digits in the Value Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_value();' class='button4'><br></td></tr><tr height='15'></tr></table>";
			document.getElementById("scrollpopup").style.display='block';
			document.getElementById("scrollpopup").innerHTML=errMsg
			document.getElementById("emailok").focus();
			greyout(true);
			return;
		}
	}
	varAllowedFormats = '<%=allowedImgFormats%>';
	var fileName = document.addloyalty.image.value;
	if(fileName.length >0)
	{
		var varIndex = fileName.lastIndexOf(".");
		var varBolMatch = false;
		if(varIndex > 0)
		{
			var varFileExt  = fileName.substring(++varIndex);
			var varStringArr = varAllowedFormats.split(",");
			for(var i=0;i<varStringArr.length;i++)
			{
				if(varStringArr[i].toLowerCase() == varFileExt.toLowerCase())
				{
					varBolMatch = true;
				}
			}
			if(!varBolMatch)
			{
				errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>You can attach "+varAllowedFormats+" only</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closePopup_image();' class='button4'><br></td></tr><tr height='15'></tr></table>";
				document.getElementById("scrollpopup").style.display='block';
				document.getElementById("scrollpopup").innerHTML=errMsg
				document.getElementById("emailok").focus();
				greyout(true);
				return;
			}
		}
		var fileName = document.addloyalty.image.value;
		var varimageindex = fileName.lastIndexOf("\\");
		if(varIndex > 0)
		{
			var varimagename  = fileName.substring(++varimageindex);
			var checkOK = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.";
			var checkStr = varimagename;
			var allValid = true;
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
				errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Your image name should not have spaces or special characters</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closePopup_image();' class='button4'><br></td></tr><tr height='15'></tr></table>";
				document.getElementById("scrollpopup").style.display='block';
				document.getElementById("scrollpopup").innerHTML=errMsg
				document.getElementById("emailok").focus();
				greyout(true);
				return;
			}
		}
		if(varimagename.length >50)
		{
			errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Your image name should not exceed 50 characters including file extension</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closePopup_image();' class='button4'><br></td></tr><tr height='15'></tr></table>";
			document.getElementById("scrollpopup").style.display='block';
			document.getElementById("scrollpopup").innerHTML=errMsg
			document.getElementById("emailok").focus();
			greyout(true);
			return;
		}
	}
	var progdesc=Trim(progdesc);
	if(progdesc== "")
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please Enter Description.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_desc();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	if (/[a-z][A-Z]{2}/i.test(document.addloyalty.desc.value) != true) 
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please enter atleast three characters together In the \"Description\" Field</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_desc();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	var url ="";
	if(selbrand!="" && selectedArray!="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=addloyaltyprog&loyaltyname="+loyalname+"&loyalpartner="+selbrand+","+selectedArray+"&loyaltypoints="+loyalpoints+"&progdesc="+progdesc+"&image="+image+"&loyaltyvalue="+loyalvalue;
	}
	else if(selbrand=="" && selectedArray!="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=addloyaltyprog&loyaltyname="+loyalname+"&loyalpartner="+selectedArray+"&loyaltypoints="+loyalpoints+"&progdesc="+progdesc+"&image="+image+"&loyaltyvalue="+loyalvalue;
	}
	else if (selbrand!="" && selectedArray=="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=addloyaltyprog&loyaltyname="+loyalname+"&loyalpartner="+selbrand+"&loyaltypoints="+loyalpoints+"&progdesc="+progdesc+"&image="+image+"&loyaltyvalue="+loyalvalue;
	}
	else if(selbrand=="" && selectedArray=="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=addloyaltyprog&loyaltyname="+loyalname+"&loyalpartner="+selectedArray+"&loyaltypoints="+loyalpoints+"&progdesc="+progdesc+"&image="+image+"&loyaltyvalue="+loyalvalue;
	}
	document.addloyalty.method="post";
	document.addloyalty.action=url;		
	document.addloyalty.submit();
}

// This function is used to  have a loyalty check.
function loyaltyCheck()
{
	var loyalname=document.addloyalty.loyaltyname.value;
	var xmlhttp= "";
	var resp= "";
	if (window.XMLHttpRequest)
	{
		xmlhttp=new XMLHttpRequest();
	}
	else
	{
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
				if(resp.indexOf("No Loyalty Name exists u can continue")>=0)
				{
					document.getElementById("divloyalcheck").innerHTML = resp;
					return;
				}
				else if(resp.indexOf("Loyalty Name already exists")>=0)
				{
					document.addloyalty.loyaltyname.value="";
					document.addloyalty.loyaltyname.focus();
					document.getElementById("divloyalcheck").innerHTML = resp;
					return;	 
				}
				document.getElementById("divloyalcheck").innerHTML = resp;
				return;	  
			}
		}			
	}
	xmlhttp.open("POST","../../../../servlet/BrandLoyalty?hidWhatToDo=loyaltyCheck&loyaltyname="+loyalname,true);		
	xmlhttp.send();	
}



function funOnClickFirstbrands(pagenumber,selbrand)
{	
	var selectedArray = new Array();
	count = 0;
	for(x=0; x<document.addloyalty.programpartner.length; x++)
	{
		if(document.addloyalty.programpartner[x].checked==true)
		{
			selectedArray[count]= document.addloyalty.programpartner[x].value;
			count++;
		}
	}
	var url="";
	if(selbrand!="" && selectedArray!="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getPartners&pagetype=first&pagenumber="+pagenumber+"&selbrand="+selbrand+","+selectedArray+"&requestno="+(Math.random()*100);
	}
	else if(selbrand=="" && selectedArray!="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getPartners&pagetype=first&pagenumber="+pagenumber+"&selbrand="+selectedArray+"&requestno="+(Math.random()*100);
	}
	else if (selbrand!="" && selectedArray=="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getPartners&pagetype=first&pagenumber="+pagenumber+"&selbrand="+selbrand+"&requestno="+(Math.random()*100);
	}
	else if(selbrand=="" && selectedArray=="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getPartners&pagetype=first&pagenumber="+pagenumber+"&selbrand="+selbrand+"&requestno="+(Math.random()*100);
	}
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	}
	else
	{// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if(xmlhttp.readyState!=4)
		{
			document.getElementById("divpartner1").innerHTML="<br><br><br><br><center><img src='../../../../images/loader.gif' align='center'><br><br><span class='style1'>Please wait! Fetching loyalty partners....</span></center>";
		}
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			document.getElementById("divpartner1").innerHTML="";
			document.getElementById("divpartner1").innerHTML=xmlhttp.responseText;
		}
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();
}

function funOnClickPreviousbrands(pagenumber,selbrand)
{
	var selectedArray = new Array();
	count = 0;
	for(x=0; x<document.addloyalty.programpartner.length; x++)
	{
		if(document.addloyalty.programpartner[x].checked==true)
		{
			selectedArray[count]= document.addloyalty.programpartner[x].value;
			count++;
		}
	}
	var url="";
	if(selbrand!="" && selectedArray!="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getPartners&pagetype=previous&pagenumber="+pagenumber+"&selbrand="+selbrand+","+selectedArray+"&requestno="+(Math.random()*100);
	}
	else if(selbrand=="" && selectedArray!="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getPartners&pagetype=previous&pagenumber="+pagenumber+"&selbrand="+selectedArray+"&requestno="+(Math.random()*100);
	}
	else if (selbrand!="" && selectedArray=="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getPartners&pagetype=previous&pagenumber="+pagenumber+"&selbrand="+selbrand+"&requestno="+(Math.random()*100);
	}
	else if(selbrand=="" && selectedArray=="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getPartners&pagetype=previous&pagenumber="+pagenumber+"&selbrand="+selbrand+"&requestno="+(Math.random()*100);
	}
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	}
	else
	{// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if(xmlhttp.readyState!=4)
		{
			document.getElementById("divpartner1").innerHTML="<br><br><br><br><center><img src='../../../../images/loader.gif' align='center'><br><br><span class='style1'>Please wait! Fetching loyalty partners....</span></center>";
		}
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			document.getElementById("divpartner1").innerHTML="";
			document.getElementById("divpartner1").innerHTML=xmlhttp.responseText;
		}
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();
}

function funOnClickNextbrands(pagenumber,selbrand)
{
	var selectedArray = new Array();
	count = 0;
	for(x=0; x<document.addloyalty.programpartner.length; x++)
	{
		if(document.addloyalty.programpartner[x].checked==true)
		{
			selectedArray[count]= document.addloyalty.programpartner[x].value;
			count++;
		}
	}
	var url="";
	if(selbrand!="" && selectedArray!="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getPartners&pagetype=next&pagenumber="+pagenumber+"&selbrand="+selbrand+","+selectedArray+"&requestno="+(Math.random()*100);
	}
	else if(selbrand=="" && selectedArray!="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getPartners&pagetype=next&pagenumber="+pagenumber+"&selbrand="+selectedArray+"&requestno="+(Math.random()*100);
	}
	else if (selbrand!="" && selectedArray=="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getPartners&pagetype=next&pagenumber="+pagenumber+"&selbrand="+selbrand+"&requestno="+(Math.random()*100);
	}
	else if(selbrand=="" && selectedArray=="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getPartners&pagetype=next&pagenumber="+pagenumber+"&selbrand="+selbrand+"&requestno="+(Math.random()*100);
	}
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	}
	else
	{// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if(xmlhttp.readyState!=4)
		{
			document.getElementById("divpartner1").innerHTML="<br><br><br><br><center><img src='../../../../images/loader.gif' align='center'><br><br><span class='style1'>Please wait! Fetching loyalty partners....</span></center>";
		}
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			document.getElementById("divpartner1").innerHTML="";
			document.getElementById("divpartner1").innerHTML=xmlhttp.responseText;
		}
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();
}

function getpagebrands(selbrand)
{
	var page = document.getElementById("page").value;
	
	var selectedArray = new Array();
	count = 0;
	for(x=0; x<document.addloyalty.programpartner.length; x++)
	{
		if(document.addloyalty.programpartner[x].checked==true)
		{
			selectedArray[count]= document.addloyalty.programpartner[x].value;
			count++;
		}
	}
	var url="";
	if(selbrand!="" && selectedArray!="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getPartners&pagenumber="+page+"&selbrand="+selbrand+","+selectedArray+"&requestno="+(Math.random()*100);
	}
	else if(selbrand=="" && selectedArray!="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getPartners&pagenumber="+page+"&selbrand="+selectedArray+"&requestno="+(Math.random()*100);
	}
	else if (selbrand!="" && selectedArray=="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getPartners&pagenumber="+page+"&selbrand="+selbrand+"&requestno="+(Math.random()*100);
	}
	else if(selbrand=="" && selectedArray=="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getPartners&pagenumber="+page+"&selbrand="+selbrand+"&requestno="+(Math.random()*100);
	}
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	}
	else
	{// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if(xmlhttp.readyState!=4)
		{
			document.getElementById("divpartner1").innerHTML="<br><br><br><br><center><img src='../../../../images/loader.gif' align='center'><br><br><span class='style1'>Please wait! Fetching loyalty partners....</span></center>";
		}
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			document.getElementById("divpartner1").innerHTML="";
			document.getElementById("divpartner1").innerHTML=xmlhttp.responseText;
		}
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();
}


function funOnClickLastbrands(Lastpage,pagenumber,selbrand)
{
	var selectedArray = new Array();
	count = 0;
	for(x=0; x<document.addloyalty.programpartner.length; x++)
	{
		if(document.addloyalty.programpartner[x].checked==true)
		{
			selectedArray[count]= document.addloyalty.programpartner[x].value;
			count++;
		}
	}
	var url="";
	if(selbrand!="" && selectedArray!="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getPartners&pagetype=last&pagenumber="+pagenumber+"&lastpage="+Lastpage+"&selbrand="+selbrand+","+selectedArray+"&requestno="+(Math.random()*100);
	}
	else if(selbrand=="" && selectedArray!="")
	{
	 	url="../../../../servlet/BrandLoyalty?hidWhatToDo=getPartners&pagetype=last&pagenumber="+pagenumber+"&lastpage="+Lastpage+"&selbrand="+selectedArray+"&requestno="+(Math.random()*100);
	}
	else if (selbrand!="" && selectedArray=="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getPartners&pagetype=last&pagenumber="+pagenumber+"&lastpage="+Lastpage+"&selbrand="+selbrand+"&requestno="+(Math.random()*100);
	}
	else if(selbrand=="" && selectedArray=="")
	{
		url="../../../../servlet/BrandLoyalty?hidWhatToDo=getPartners&pagetype=last&pagenumber="+pagenumber+"&lastpage="+Lastpage+"&selbrand="+selbrand+"&requestno="+(Math.random()*100);
	}
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp=new XMLHttpRequest();
	}
	else
	{// code for IE6, IE5
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		if(xmlhttp.readyState!=4)
		{
			document.getElementById("divpartner1").innerHTML="<br><br><br><br><center><img src='../../../../images/loader.gif' align='center'><br><br><span class='style1'>Please wait! Fetching loyalty partners....</span></center>";
		}
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			document.getElementById("divpartner1").innerHTML="";
			document.getElementById("divpartner1").innerHTML=xmlhttp.responseText;
		}
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();
}
//This function is used to  get loyalty partners by clicking on it.

function onClickLoyaltyPartner()
{
	var paramName = "divpartner";
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
				document.getElementById("divpartner1").innerHTML = resp;
				document.getElementById("divpartner1").innerHTML = xmlhttp.responseText;
			}
		}			
	}
	xmlhttp.open("POST","../../../../servlet/BrandLoyalty?hidWhatToDo=getPartners",true);		
	xmlhttp.send();	
}

//This functions is to hide popups and to place cusrsors on the fields.
function close_popup_loyaltyname()
{
	$('#scrollpopup').hide();
	greyout(false);
	document.addloyalty.loyaltyname.value="";
	document.addloyalty.loyaltyname.focus();
}

// This functions is to hide popups and to place cusrsors on the fields.
function close_popup_desc()
{
	$('#scrollpopup').hide();
	greyout(false);
	document.addloyalty.desc.value="";
	document.addloyalty.desc.focus();
	limitText(document.addloyalty.countdown.value,150);
}
		    		    
// This functions is to hide popups and to place cusrsors on the fields.
function close_popup_points()
{
	$('#scrollpopup').hide();
	greyout(false);
	document.addloyalty.loyaltypoints.value="";
	document.addloyalty.loyaltypoints.focus();
}
//This functions is to hide popups and to place cusrsors on the fields.
function close_popup_value()
{
	$('#scrollpopup').hide();
	greyout(false);
	document.addloyalty.rupeevalue.value="";
	document.addloyalty.rupeevalue.focus();
}

// This functions is to hide popups and to place cusrsors on the fields.
function closePopup_image()
{
	$('#scrollpopup').hide();
	greyout(false);
	document.addloyalty.image.value="";
	document.addloyalty.image.focus();
}
//This functions is to hide popups and to place cusrsors on the fields.
function close_popup()
{
	$('#scrollpopup').hide();
	greyout(false);
	document.addloyalty.desc.value="";
	document.addloyalty.desc.focus();
}
//This functions is to hide popups.
function closeScrollPopup()
{
	$('#scrollpopup').hide();
	greyout(false);
}
/*  This function is used to focus loaylty name on load.*/
function onfocus()
{
	document.addloyalty.loyaltyname.focus();
	return;
}

</script>
<!-- end of javascript-->
</html>
