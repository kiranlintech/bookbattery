<%-- 
	Author(s)			: Ajay K
	Created on			: Friday, January 17, 2014 1:06:45 PM
	Copyright Notice	: BookBattery Pvt.Ltd. Confidential
	Description			: chat agents login.
--%>
<%@page language="java" import="java.util.Properties,java.io.FileInputStream"%>
<%
String sesErrorMsg=(String)session.getAttribute("seschatagentErrorMsg");
if(sesErrorMsg==null)
{
	sesErrorMsg="";
}
else
{
	session.removeAttribute("seschatagentErrorMsg");
}
Properties propslivechatConfig = new Properties();
FileInputStream configfile      = new FileInputStream("/bookbattery/properties/bookbatteryconfig.properties"); 
propslivechatConfig.load(configfile); 
configfile.close();
String adminlogo = (propslivechatConfig.getProperty("adminlogo")!=null)?propslivechatConfig.getProperty("adminlogo"):"";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link href="../../css/livechat.css" rel="stylesheet" type="text/css" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../images/favicon.png" type="image/x-icon">
<h1><title>LiveChat Chat Agent Login</title></h1>
<meta name="keywords" content="LiveChat Chat Agent Login"/>
<meta name="description" content="LiveChat Chat Agent Login"/>
<script language="JavaScript" src="../../js/pophide.js" ></script>
</head>
<body onload="focus_uname();">
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<center>
<form name="chatagentlogin"  method="post">
<div id='scrollpopup' class='scrollpopup' style="display:none;"></div>
<table width="960" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left" valign="top">
			<table width="960" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<IMG SRC="<%=adminlogo%>" WIDTH="170" HEIGHT="50" BORDER="0" ALT="logo">
					</td>
					<td align='right' class="subheading">Chat Agent Login</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td width="960"><hr width="100%" align="center" size="1" color="#BEADCB"></td>
   </tr>
	<tr>
		<td align="left" valign="top" bgcolor="#FFFFFF">
			<table width="960" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="180" height="438" align="left" valign="top">
						<table width="180" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="680" align="left" valign="top" >
									<table width="513" border="0" cellspacing="0" cellpadding="0">
										<!-- Inner content should be within the below table -->
										<tr>
											<td>
												<table width="955" border="0" valign="top"cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" height="430">
													<tr height="5"><td></td></tr>
													<tr>
														<td>
															<table width="350" border="0" cellpadding="0" align="center" cellspacing="1" bgcolor="#FF8c00">
																<tr>
																	<td bgcolor="#2364b1" class="prodheading" height="25">Chat Agent Login</td>
																</tr>
																<tr>
																	<td>
																		<table width="100%" cellspacing="1" bgcolor="#FFFFFF" cellpadding="0">
																			<tr>
																				<td>
																					<tr>
																						<td height="35"><%=sesErrorMsg%> </td>
																					</tr>
																					<tr>
																						<td>
																							<table width="281" border="0" cellspacing="0" cellpadding="0" align="center" >
																								 <tr>
																									<td height="35" align="right" valign="middle" class="insidecontent">Chat&nbsp;Agent&nbsp;Login&nbsp;Name:<font color="#FF0000">*</font></td>
																									<td height="35" align="left" valign="middle"><input type="text" name="username" maxlength="50"/></td>
																								 </tr>
																								 <tr>
																									<td height="35" align="right" valign="middle" class="insidecontent">Password:<font color="#FF0000">*</font></td>
																									<td height="35" align="left" valign="middle"><input type="password" name="passwd" maxlength="50" onkeypress="if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:login();return false;} else return true;"/></td>
																								</tr>
																							</table>
																						</td>
																					</tr>
																					<tr>
																						<td>
																							<table width="182" border="0" cellspacing="2" cellpadding="0" align="center">
																								<tr>
																									<td width="96" height="25" align="center" valign="middle">&nbsp;</td>
																									<td width="175" align="right" valign="middle" class="links"><input type="button"   id="btnSearch" onkeypress='searchKeyPress(event);' class="button4" Value="Login" onclick="login(); "></td>
																									<td width="100" align="center" valign="middle"><span class="links"><button onclick="refresh();" class="button4">Reset</span></td>
																								</tr>
																							</table>
																						</td>
																					</tr>
																				</td>
																			</tr>
																			<tr height="10">
																				<td colspan="2">&nbsp;</td>
																			</tr>
																		</table>
																	</td>
																</tr>
															</table>
															<table width="160" border="0" align="center" cellspacing="0" cellpadding="2">
																<tr bgcolor="#FFFFFF">
																	<td class="content" colspan="6" width="100%" height="20">
																		<table align="right"  border="0" cellpadding="2" cellspacing="0">
																			<tr height="20"></tr>
																		</table>
																	</td>
																</tr>
															</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr height="10">
											<td colspan="4">&nbsp;</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
		<td width="40" align="right" valign="top"></td>
	</tr>
</table>
</body>
<script language="javascript">
function searchKeyPress(e)        
{  
	// look for window.event in case event isn't passed in                 
	if (window.event) { e = window.event; }  
	if (e.keyCode == 13)
	{                        
		document.getElementById('btnSearch').click(); 
	}       
}
function onfocus()
{
	var un=document.chatagentlogin.username.value;
	if(un == "")
	{
		document.chatagentlogin.username.focus();
		return ;
	}
}
function login()
{
	var un=document.chatagentlogin.username.value;
	var pwd=escape(document.chatagentlogin.passwd.value);
	if(un == "")
	{

		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>LiveChat</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please Enter User Name</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_uname();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
		
	}
	if(pwd == "")
	{
		errMsg ="<table border='0' width='400px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>LiveChat</td><td align='right'><a href='javascript:closeScrollPopup(greyout(false));'><img src=\"../../images/Delete1.png \" border='0'></a></td></tr></table><br><table border='0' width='400px' height='10px'><tr class='pages'><td align='center'>Please Enter Password</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_pwd();' class='button4'><br></td></tr><tr height='15'></tr></table>";
		document.getElementById("scrollpopup").style.display='block';
		document.getElementById("scrollpopup").innerHTML=errMsg
		document.getElementById("emailok").focus();
		greyout(true);
		return;
	}
	document.chatagentlogin.method="post";
	document.chatagentlogin.action="../../servlet/LiveChatManagement?hidWhatToDo=chatagentlogin&username="+un+"&passwd="+pwd;
	document.chatagentlogin.submit();
}
function close_popup_uname()
{
	 $('#scrollpopup').hide();
	 greyout(false);
	 document.chatagentlogin.username.focus();
}
function close_popup_pwd()
{
	 $('#scrollpopup').hide();
	 greyout(false);
	document.chatagentlogin.passwd.focus();
}
function closeScrollPopup()
{
	 $('#scrollpopup').hide();
	 greyout(false);
}
function focus_uname()
{
	document.chatagentlogin.username.focus();
}
function refresh()
{
	window.location.href="../../jsp/supportlogin/chatagentlogin.jsp";
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