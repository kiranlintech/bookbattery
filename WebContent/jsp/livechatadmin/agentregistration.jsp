<%-- 
Author(s)			: Ajay K
Created on			: Thursday, January 16, 2014 3:18:07 PM
Copyright Notice	: NGIT Pvt.Ltd. Confidential
Description			: Livechat Agent Registration
--%>
<%@page language="java" import="java.util.Enumeration,java.util.Properties,java.io.FileInputStream"%>
<%
String strLogMsg=(String)session.getAttribute("sesErrorMsg");
if(strLogMsg==null)
{
	strLogMsg="";
}
else
{
	session.removeAttribute("sesErrorMsg");
}
Properties propslivechatConfig = new Properties();
FileInputStream configfile      = new FileInputStream("/bookbattery/properties/bookbatteryconfig.properties"); 
propslivechatConfig.load(configfile); 
configfile.close();
String adminlogo = (propslivechatConfig.getProperty("adminlogo")!=null)?propslivechatConfig.getProperty("adminlogo"):"";
String allowedImgFormats =  "jpg,png";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link href="../../css/livechat.css" rel="stylesheet" type="text/css" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../images/favicon.png" type="image/x-icon">
<title>Live Chat Agent Registration</title>
</head>
<script language="JavaScript" src="../../js/pophide.js" ></script>
<body>
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<center>
	<form name="addchatagent" method="post"  ENCTYPE="multipart/form-data">
	<div id='popup' class='popup' style="display:none;">
		<div id='popuptitle' class='popuptitle'><table border='0' width='410px' height='10px' cellpadding='0' cellspacing='0'>
                <tr class='top1'><td>&nbsp;<font color='#FFFFFF'>LiveChat</td><td align='right'>
                <a href='javascript:closePopup(greyout(false));' class="bluelinks333">x</a></td></tr></table>
		</div>
		<div id='popupmessage' class='popupmessage'></div> 
		</div>
		<table cellpadding="0" cellspacing="0" border="0" width="960">
			<tr>
				<td align="left" valign="top">
					<table width="960" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<IMG SRC="<%=adminlogo%>" WIDTH="170" HEIGHT="50" BORDER="0" ALT="logo">
							</td>
							<td align='right' class="subheading">Live Chat Admin Portal</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align='right' class="subheading"><INPUT TYPE="button" style='cursor:pointer;' class="button4" VALUE="Logout" ONCLICK="javascript:adminlogout();"></td>
			</tr>
			<tr>
				<td width="960"><hr width="100%" align="center" size="1" bgcolor="#BEADCB"></td>
			</tr>
			<tr>
				<td align="left" valign="top" bgcolor="#FFFFFF">
					<table width="960" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="180" height="400" align="left" valign="top">
								<table width="180" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="180" height="438" align="left" valign="top">
											<table width="100%" border="0" cellspacing="0" cellpadding="0">
												<tr>
													<jsp:include page="agentleftmenu.jsp"/>
												</tr>
											</table>
										</td>
										<td width="680" align="left" valign="top" >
											<table width="770" cellspacing="1" bgcolor="#BEADCB" cellpadding="0">
											<!-- Inner content should be within the below table -->
											<tr>
												<td>
													<table width="100%" cellspacing="1" border="0" bgcolor="#FFFFFF" cellpadding="0">
														<tr>
															<td>
																<table width="100%" border="0" cellspacing="0" bgcolor="#FFFFFF" cellpadding="5px">
																	<tr>
																		<td height="10" bgcolor="#FFFFFF"></td>
																	</tr>
																	<tr>
																		<td class="subheading"> <font size="2">Live Chat Agent Registration</td>
																	</tr>
																	<tr>
																		<td>
																			<table width="100%" align="center" cellspacing="0" bgcolor="#FFFFFF" cellpadding="5">
																				<tr height="10">
																					<td colspan="3" align="center"><%=strLogMsg%>
																					</td>
																				</tr>
																			</table>
																			<table width="100%" cellspacing="1" border="0" bgcolor="#FFFFFF" cellpadding="0">
																				<tr>
																					<td>
																						<table width="100%" border="0" cellspacing="0" bgcolor="#FFFFFF" cellpadding="5" border="0">
																							<tr height="10">
																								<td colspan="3" align="center"></td>
																							</tr>
																							<tr>
																								<td class="subheading" width="22%">ChatAgent&nbsp;First&nbsp;Name<font color="FF0000">*</font></td>
																								<td width="38%">:&nbsp;&nbsp;<input class="insidecontent" type="text" name="cafirstname" maxlength="50" size="31"   ></div></td></td>
																							</tr>
																							<tr>
																								<td class="subheading" width="22%">ChatAgent&nbsp;Last&nbsp;Name<font color="FF0000">*</font></td>
																								<td width="38%">:&nbsp;&nbsp;<input class="insidecontent" type="text" name="calastname" maxlength="50" size="31"   ></div></td></td>
																							</tr>
																							<tr>
																								<td class="subheading" width="22%">ChatAgent&nbsp;Login&nbsp;Name<font color="FF0000">*</font></td>
																								<td width="38%">:&nbsp;&nbsp;<input type="text" name="chatagentloginname" class="insidecontent" maxlength="50"  size="31" onchange="javascript:checkchatagentloginname()" onkeydown="clearmsg();"  onmousedown="clearmsg();"><td class="insidecontent"><div id="divsesmsgretlogin"></div></td></td>
																							</tr>
																							<tr>
																								<td class="subheading" width="22%" >Password<font color="FF0000">*</font></td>
																								<td width="38%" align="left">:&nbsp;&nbsp;<input type="password" class="insidecontent" name="password" maxlength="50" size="31"  ></td>
																							</tr>
																							<tr>
																								<td class="subheading" width="22%">Confirm&nbsp;Password<font color="FF0000">*</font></td>
																								<td width="38%">:&nbsp;&nbsp;<input  type="password" name="cnfrmpassword" class="insidecontent" maxlength="50" size="31" ></td>
																							</tr>
																							<tr>
																								<td class="subheading">Chat&nbsp;Agent&nbsp;Email-id<font color="FF0000">*</font></td>
																								<td colspan="2">:&nbsp;&nbsp;<input type="text" name="emailid"  size="31" class="insidecontent" maxlength="50" ></td>
																							</tr>
																							<tr>
																								<td class="subheading">Chat&nbsp;Agent&nbsp;Mobile&nbsp;Num<font color="FF0000">*</font></td>
																								<td colspan="2">:&nbsp;&nbsp;<input type="text" id="mobilenumber" name="mobilenumber"  size="31" class="insidecontent" maxlength="10" ></td>
																							</tr>
																							<!-- <tr>
																								<td class="subheading">Chat&nbsp;Users&nbsp;Limit&nbsp;&nbsp;&nbsp;&nbsp;<font color="FF0000">*</font></td>
																								<td colspan="2">:&nbsp;&nbsp;<input type="text" name="chatlimit"  size="31" class="insidecontent" maxlength="3" ></td>
																							</tr> -->
																							<tr>
																								<td class="subheading" width="22%">Upload ChatAgent Picture<font color="FF0000">*</font></td>
																								<td width="38%">:&nbsp;&nbsp;<input type="file" name="attachment" class="top1" onkeydown="javascript:return false;" onchange="checkFile(this)">
																								</td>
																							</tr>
																							<tr>
																								<td class="insidecontent" align="right" valign="top" width="22%"></td>
																								<td class="insidecontent" align="left" width="58%">
																								<font class="insidecontent" width="70%">*<u>&nbsp;Image&nbsp;formats&nbsp;</u>:&nbsp;jpg&nbsp;&&nbsp;png</td>
																							</tr>
																							<tr id="imagecharid">
																								<td class="insidecontent" align="right" valign="top" width="22%"></td>
																								<td class="insidecontent" align="left" width="58%">
																								<font class="insidecontent" width="70%">*File&nbsp;Name&nbsp;should&nbsp;not&nbsp;contain&nbsp;Special&nbsp;Characters and spaces</td>
																							</tr>
																							<tr id="limitid">
																								<td class="insidecontent" align="right" valign="top" width="22%"></td>
																								<td class="insidecontent" align="left" width="38%">
																								<font class="insidecontent" width="70%">*Image&nbsp;size&nbsp;should&nbsp;not&nbsp;exceed<u>&nbsp;4&nbsp;MB</u></td>
																							</tr>
																						</table>
																						<tr bgcolor='#FFFFFF'>
																							<td>
																								<table width="700" cellspacing="2" cellpadding="5" border="0">
																									<tr bgcolor="FFFFFF">
																										<td class="subheading" colspan="3">
																											<table  align="left" border="0" width="500">
																												<tr height="20" id="button">
																													<td width="194" align="right" ><a href="javascript:addchatagent()" class="button4">Submit</a></td>
																													<td width="100" align="center"><a href="javascript:reset();" class="button4">Reset</a></td>
																													<td width="196" align="left"><a href="../../jsp/livechatadmin/adminmain.jsp" class="button4">Back</a></td>
																												</tr>
																											</table>
																										</td>
																									</tr>
																								</table>
																							</td>
																						</tr>
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
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</form>
</center>
</body>
<script type="text/JavaScript">
//Function to  trim the emailid
function Trim(emailid)
{
    while (emailid.substring(0,1) == ' ') // check for white spaces from beginning
    {
        emailid = emailid.substring(1, emailid.length);
    }
    while (emailid.substring(emailid.length-1, emailid.length) == ' ') // check white space from end
    {
        emailid = emailid.substring(0,emailid.length-1);
    }
    return emailid;
}
//function to close the popup for chatagent first name validations
function close_popup_firstname()
{
	$('#popup').hide();
	greyout(false);
	document.addchatagent.cafirstname.focus();
}
//function to close the popup for chatagent last name validations
function close_popup_lastname()
{
	$('#popup').hide();
	greyout(false);
	document.addchatagent.calastname.focus();
}
//function to close the popup for chatagent login name validations
function close_popup_caloginname()
{
	$('#popup').hide();
	greyout(false);
	document.addchatagent.chatagentloginname.focus();
}
//function to close the popup for chatagent password validations
function close_popup_password()
{
	$('#popup').hide();
	greyout(false);
	document.addchatagent.password.focus();
}
//function to close the popup for chatagent confirm password validations
function close_popup_cnfrmpasswrd()
{
	$('#popup').hide();
	greyout(false);
	document.addchatagent.cnfrmpassword.focus();
}
//function to close the popup for chatagent emailid validations
function close_popup_email()
{
	$('#popup').hide();
	greyout(false);
	document.addchatagent.emailid.focus();
}
function close_popup_mobilenumber()
{
	$('#popup').hide();
	greyout(false);
	document.addchatagent.mobilenumber.focus();
}
//function to close the popup for chatagent image validations
function closeimage()
{
	$('#popup').hide();
	greyout(false);
	document.addchatagent.attachment.focus();
}
//function to close the popup for chatagent chat users limit validations
function closechatlimit()
{
	$('#popup').hide();
	greyout(false);
	document.addchatagent.chatlimit.focus();
}
//function to refresh the chat login name message div
function clearmsg()
{
	document.getElementById("divsesmsgretlogin").innerHTML ="";
}
//function used to hide popup
function closePopup()
{
	$('#popup').hide();
	greyout(false);
}
function reset()
{
	location.href="../../jsp/livechatadmin/agentregistration.jsp";
}
//Function to add chatagent in to the database
function addchatagent()
{
	var fname=document.addchatagent.cafirstname.value;
	var lname=document.addchatagent.calastname.value;
	var caloginname=document.addchatagent.chatagentloginname.value;
	var pwd=escape(document.addchatagent.password.value);
	var pwd1=escape(document.addchatagent.cnfrmpassword.value);
	var emailid=document.addchatagent.emailid.value;
	var mobilenumber=document.addchatagent.mobilenumber.value;
	//var chatlimit = document.addchatagent.chatlimit.value;
	var filename = document.addchatagent.attachment.value;
	varAllowedFormats = '<%=allowedImgFormats%>';
	fname=Trim(fname);
	if((fname == ""))
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter chatagent first name.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_firstname();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		document.getElementById("emailok").focus();
		return;
	}
	if(document.addchatagent.cafirstname.value.length < 3)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter at least 3 characters in the \"chatagent first name\" field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_firstname();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		document.getElementById("emailok").focus();
		return;
	}
	if(fname != "")
	{
		var checkOK = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		var checkStr = fname;
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
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please Enter Alphabets in the \"chatagent first name\" Field without spaces.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_firstname();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("emailok").focus();
			return ;
		}
	}
	var regexLetter = /\d/;
	var regexNum = /[a-zA-Z]/;
	if(!regexNum.test(fname) && regexLetter.test(fname))
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Only numerics are not allowed in chatagent first name field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_firstname();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		document.getElementById("emailok").focus();
		return ;
	}
	lname=Trim(lname);
	if((lname == ""))
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter chatagent last name.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_lastname();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		document.getElementById("emailok").focus();
		return;
	}
	if(document.addchatagent.calastname.value.length < 3)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter at least 3 characters in the \"chatagent last name\" field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_lastname();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		document.getElementById("emailok").focus();
		return;
	}
	if(lname != "")
	{
		var checkOK = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		var checkStr = lname;
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
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please Enter Alphabets in the \"chatagent last name\" Field without spaces.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_lastname();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("emailok").focus();
			return ;
		}
	}
	var regexLetter = /\d/;
	var regexNum = /[a-zA-Z]/;
	if(!regexNum.test(lname) && regexLetter.test(lname))
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Only numerics are not allowed in chatagent last name field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_lastname();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		document.getElementById("emailok").focus();
		return ;
	}
	if((caloginname == ""))
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter chatagent login name.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_caloginname();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		document.getElementById("emailok").focus();
		return;
	}
	if(document.addchatagent.chatagentloginname.value.length < 3)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter at least 3 characters in the \"chatagent login name\" field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_caloginname();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		document.getElementById("emailok").focus();
		return;
	}
	if (document.addchatagent.chatagentloginname.value.length > 50)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only 50 characters in the \"chatagent login name\" field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_caloginname();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		document.getElementById("emailok").focus();
		return;
	}
	if(caloginname != "")
	{
		var checkOK = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		var checkStr = caloginname;
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
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please Enter Alphabets in the \"chatagent login name\" Field without spaces.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_caloginname();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("emailok").focus();
			return ;
		}
	}
	var regexLetter = /\d/;
	var regexNum = /[a-zA-Z]/;
	if(!regexNum.test(caloginname) && regexLetter.test(caloginname))
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Only numerics are not allowed in chatagent Login Name field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_caloginname();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		document.getElementById("emailok").focus();
		return ;
	}
	if(pwd == "")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter password</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_password();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		document.getElementById("emailok").focus();
		return;
	}
	if (document.addchatagent.password.value.length < 5)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter at least 5 characters in the \"Password\" field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_password();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		document.getElementById("emailok").focus();
		return;
	}
	if (document.addchatagent.password.value.length > 50)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only 50 characters in the \"Password\" field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_password();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		document.getElementById("emailok").focus();
		return;
	}
	if (document.addchatagent.password.value.indexOf(' ') >= 0 ) 
	{     
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please Enter Only Alphabets,Numerics and Special Characters in Password Field..</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_password();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		document.getElementById("emailok").focus();
		return;
	}
	if(pwd1 == "")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter Confirm Password.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_cnfrmpasswrd();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		document.getElementById("emailok").focus();
		return;
	}
	if (document.addchatagent.cnfrmpassword.value.length < 5)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Password and Confirm Password are not same.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_cnfrmpasswrd();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		document.getElementById("emailok").focus();
		return ;
	}
	if (document.addchatagent.cnfrmpassword.value.length > 50)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Password and Confirm Password are not same.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_cnfrmpasswrd();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		document.getElementById("emailok").focus();
		return ;
	}
	if (document.addchatagent.cnfrmpassword.value.indexOf(' ') >= 0 ) 
	{     
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please Enter Only Alphabets,Numerics and Special Characters in Confirm Password Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_cnfrmpasswrd();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		document.getElementById("emailok").focus();
		return;
	}
	if (document.addchatagent.password.value != document.addchatagent.cnfrmpassword.value)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Password and Confirm Password are not same.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_cnfrmpasswrd();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		document.getElementById("emailok").focus();
		return ;
	}
	emailid=Trim(emailid);
	if(emailid == "")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter Email-id</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		document.getElementById("emailok").focus();
		return;
	}
	if(emailid == "john@gmail.com")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter Email-id</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		document.getElementById("emailok").focus();
		return;
	}
	if(emailid!="")
	{
		var at = "@";
		var dot = ".";
		var lat = emailid.indexOf(at);
		var lstr = emailid.length;
		var ldot = emailid.indexOf(dot);
		var sst = emailid.substring(at,lstr);
		var sstdot=sst.indexOf(dot);
		var hi = "-";
		var us = "_";
		var lhi = emailid.indexOf(hi);
		var lstr = emailid.length;
		var lus = emailid.indexOf(us);
		var iChars1 = "!#$%^&*()+=[]\\\';/{}|\":<>?,";
		if (emailid.indexOf(at) == -1)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Email Id\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@ngit.in)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("emailok").focus();
			return;
		}
		if (emailid.indexOf(at) == -1 || emailid.indexOf(at) == 0 || emailid.indexOf(at) == lstr)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Email Id\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@ngit.in)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("emailok").focus();
			return;
		}
		if((lstr-1)-sstdot<2)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter valid emailid</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("emailok").focus();
			return ;
		}
		if (emailid.indexOf(dot) == -1 || emailid.indexOf(dot) == 0 || emailid.indexOf(dot) == lstr)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Email Id\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@ngit.in)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("emailok").focus();
			return;
		}
		if (emailid.indexOf(at, (lat + 1)) != -1)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>You Have Entered Invalid E-mail ID.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("emailok").focus();
			return;
		}
		if (emailid.substring(lat - 1, lat) == dot|| emailid.substring(lat + 1, lat + 2) == dot || emailid.substring(ldot+1,ldot+2)==dot)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Emailid\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@ngit.in)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("emailok").focus();
			return;
		}
		if (emailid.indexOf(dot, (lat + 2)) == -1)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Emailid\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@ngi.in)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("emailok").focus();
			return;
		}
		if (emailid.indexOf(" ") != -1)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Invalid E-mail ID.The \"Emailid\" Field Must Contain An \"@\" And a \".\".(e.g. free2rhyme@ngit.in)</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("emailok").focus();
			return;
		}
		var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
		var reg=/@(([^\.]*\.[^\.]*)?){1,3}$/;
		var regexp=/^[a-zA-Z0-9_\+-]+(\.[a-zA-Z0-9_\+-]+)*@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.([a-zA-Z]{2,3})$/;
		
		if (! document.addchatagent.emailid.value.match(re))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok'  value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("emailok").focus();
			return ;
		}
		if (! document.addchatagent.emailid.value.match(regexp))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("emailok").focus();
			return ;
		}
		if (! document.addchatagent.emailid.value.match(reg))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("emailok").focus();
			return ;
		}
		if (document.addchatagent.emailid.value.indexOf('gmail') >=0)
		{
			if (document.addchatagent.emailid.value.indexOf('com')<0)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of smith@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				document.getElementById("emailok").focus();
				return ;
			}
		}
		if (document.addchatagent.emailid.value.indexOf('rediffmail') >=0)
		{
			if (document.addchatagent.emailid.value.indexOf('com')<0)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of smith@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				document.getElementById("emailok").focus();
				return ;
			}
		}
		if (document.addchatagent.emailid.value.indexOf('hotmail') >=0)
		{
			if (document.addchatagent.emailid.value.indexOf('com')<0)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of smith@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				document.getElementById("emailok").focus();
				return ;
			}
		}
		if (document.addchatagent.emailid.value.indexOf('live') >=0)
		{
			if (document.addchatagent.emailid.value.indexOf('com')<0)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of smith@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				document.getElementById("emailok").focus();
				return ;
			}
		}
		if (document.addchatagent.emailid.value.indexOf('ymail') >=0)
		{
			if (document.addchatagent.emailid.value.indexOf('com')<0)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of smith@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				document.getElementById("emailok").focus();
				return ;
			}
		}
		if (document.addchatagent.emailid.value.indexOf('com.in') >=0)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("emailok").focus();
			return ;
		}
		if (emailid.substring(lhi-1,lhi)==hi || emailid.substring(lhi+1,lhi+2)==hi)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("emailok").focus();
			return ;
		}
		if ((emailid.substring(lhi-1,lhi)==hi || emailid.substring(lus+1,lus+2)==us) || (emailid.substring(lus-1,lus)==us || emailid.substring(lhi+1,lhi+2)==hi))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("emailok").focus();
			return ;
		}
		if (emailid.substring(lhi - 1, lhi) == us || emailid.substring(lhi + 1, lhi + 2) == us || emailid.substring(lus+1,lus+2)==us)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("emailok").focus();
			return ;
		}
		if (emailid.indexOf("__") != -1)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("emailok").focus();
			return ;
		}
		for (var i = 0; i < document.addchatagent.emailid.value.length; i++)
		{
			if (iChars1.indexOf(document.addchatagent.emailid.value.charAt(i)) != -1)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Special characters are not allowed in Email-Id field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				document.getElementById("emailok").focus();
				return;
			}
		}
		if (document.addchatagent.emailid.value.length >50)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only 50 characters in Email-id field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("emailok").focus();
			return ;
		}
	}
	if(mobilenumber == 0 || mobilenumber == "")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter Mobile Number.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobilenumber();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("mobilenumber").focus();
			return ;
	}
	else 
	{
		var checkOK = "0123456789";
		var checkStr = mobilenumber;
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

			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter only digits.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobilenumber();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("mobilenumber").focus();
			return ;

	
		
		}
	}
	if (document.getElementById("mobilenumber").value.length<10)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter valid Number.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobilenumber();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("mobilenumber").focus();
			return ;		
	}
	if (document.getElementById("mobilenumber").value.length==10)
	{
		if (mobilenumber < 7000000000 )
		 {
			
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Number Should start with 7 or 8 or 9.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobilenumber();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("mobilenumber").focus();
			return ;		
		 }
	}  
	if (document.getElementById("mobilenumber").value.length>10)
	{
		if (mobilenumber < 917000000000 || mobilenumber >= 920000000000 )
		{
			
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Number Should start with 91-7 or 8 or 9.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_mobilenumber();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("mobilenumber").focus();
			return ;	
		}
	}  
	/*if (chatlimit == "")
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please enter Chat users limit. </br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closechatlimit();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}
	if (chatlimit != "")
	{
		var checkOK = "0123456789";
		var checkStr = chatlimit;
		var allValid = true;
		var decPoints = 0;
		var allNum = "";
		for (i = 0; i < checkStr.length; i++)
		{
			ch = checkStr.charAt(i);
			for (j = 0; j < checkOK.length; j++)
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
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please enter only numerics in the Chat Users Limit Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closechatlimit();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		if((chatlimit.charAt(0)=='0' && chatlimit.charAt(1)=='0') || chatlimit.charAt(0)=='0' )
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Starting with Zero's are not allowed in the Chat Users Limit Field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closechatlimit();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
	}*/
	if(filename == "")
	{	
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please select a image file. </br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closeimage();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		return;
	}

	if(filename.length >0)
	{
		var fileIndex = filename.lastIndexOf(".");
		var varBolMatch = false;
		if(fileIndex > 0)
		{
			var fileExt  = filename.substring(++fileIndex);
			var imagesplit = varAllowedFormats.split(",");
			for(var i=0;i<imagesplit.length;i++)
			{
				if(imagesplit[i].toLowerCase() == fileExt.toLowerCase())
				{
					varBolMatch = true;
				}
			}
			if(!varBolMatch)
			{ 
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>You can attach "+varAllowedFormats+" images only </br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closeimage();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				return ;
			}
		}
		else
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>You can attach "+varAllowedFormats+" images only </br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closeimage();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
	}
	var imagelastindex = filename.lastIndexOf("\\");
	if(fileIndex > 0)
	{
		var imagename  = filename.substring(++imagelastindex);
		var allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.-";
		var checkimagename = imagename;
		var imageValid = true;
		for (i = 0;  i < checkimagename.length;  i++)
		{
			ch = checkimagename.charAt(i);
			for (j = 0;  j < allowedCharacters.length;  j++)
			if (ch == allowedCharacters.charAt(j))
			break;
			if (j == allowedCharacters.length)
			{
			imageValid = false;
			break;
			}
		}
		if (!imageValid)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Your image name should not have spaces or special characters</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closeimage();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
		if(imagename.length >60)
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Your image name should not exceed 60 characters including file extension</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closeimage();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
	}
	$('#button').hide();
	document.addchatagent.method="post";
	document.addchatagent.action="../../servlet/LiveChatManagement?hidWhatToDo=addlivechatagent&firstname="+fname+"&lastname="+lname+"&loginname="+caloginname+"&password="+pwd+"&feature=livechat&emailid="+emailid+"&mobilenumber="+mobilenumber+"&imagattach="+imagename;
	document.addchatagent.submit();
}
 function checkFile(fieldObj)
{
	var FileName  = fieldObj.value;
	var FileExt = FileName.substr(FileName.lastIndexOf('.')+1);
	var FileSize = fieldObj.files[0].size;
	var FileSizeMB = (FileSize/10485760).toFixed(2);
	if(FileSize>4194304)
	{

		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please attach image with size less than or equal to 4MB.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='closeimage();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
			return ;
	}
	
}
//Function to close popup and focus on area of eretailer
function checkchatagentloginname()
{	
	var chatlogname=document.addchatagent.chatagentloginname.value;
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
				alert("Chatagent Registration Page is on Process.");
				return;
			}
			else
			{
				resp = xmlhttp.responseText;
				if(resp.indexOf("No chatagent Login Name exists u can continue")>=0)
				{
					document.getElementById("divsesmsgretlogin").innerHTML = resp;
					return;
				}
				else if(resp.indexOf("chatagent Login Name already exists")>=0)
				{
					document.addchatagent.chatagentloginname.value="";
					errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Chatagent Login Name already exists</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button'  name='emailok' id='emailok'  value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_caloginname();' class='button4'><br></td></tr>";
					document.getElementById("popup").style.display='block';
					greyout(true);
					var topPops=380+(0*90);
					var newToppops=topPops+"px";
					document.getElementById("popup").style.top=newToppops;
					document.getElementById("popupmessage").innerHTML=errMsg
					document.getElementById("emailok").focus();
					document.getElementById("divsesmsgretlogin").innerHTML = resp;
					return;
				}
			}
		}			
	}
	xmlhttp.open("POST","/../../servlet/LiveChatManagement?hidWhatToDo=checkchatagentloginname&chatagentloginname="+chatlogname,true);		
	xmlhttp.send();
}
</script>
<script type='text/javascript'>
(function() {
var script = document.createElement('script');
script.src = '../../js/jquery-1.3.2.min.js';
script.async = true;
//it gets a list of SCRIPT elements already in the page, and chooses the last one in the list. Then insertBefore is used to insert the new dynamic SCRIPT element into the document
var s = document.getElementsByTagName('script')[0];
s.parentNode.insertBefore(script, s);
})();
</script>
</html>