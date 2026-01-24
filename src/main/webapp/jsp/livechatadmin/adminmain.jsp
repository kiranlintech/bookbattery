<%-- 
Author(s)			: Ajay K
Created on			: Thursday, January 16, 2014 3:18:07 PM
Copyright Notice	: NGIT Pvt.Ltd. Confidential
Description			: Livechat Admin main page
--%>
<%@page language="java" import="java.io.File,java.util.Properties,java.io.FileInputStream"%>
<%
Properties propslivechatConfig = new Properties();
ServletContext context = getServletContext();
FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
propslivechatConfig.load(fin1); 
fin1.close(); 
String adminlogo = (propslivechatConfig.getProperty("adminlogo")!=null)?propslivechatConfig.getProperty("adminlogo"):"";
String allowedImgFormats =  "jpg,png";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link href="../../css/livechat.css" rel="stylesheet" type="text/css" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../images/favicon.png" type="image/x-icon">
<title>Live Chat Agent Admin</title>
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
								<IMG SRC="<%=adminlogo%>" WIDTH="170" HEIGHT="50" BORDER="0" ALT="">
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
				<td class='content'><font face='verdana' size='2' color='#ff8c00'><b>Welcome&nbsp;Live&nbsp;Chat&nbsp;Administrator!</b></font></td>
			</tr>
			<tr>
				<td align="left" valign="middle" bgcolor="#FFFFFF">
					<table width="960" border="0" cellspacing="0"  cellpadding="0">
						<tr>
							<td width="180" height="400" align="left" valign="middle">
								
								<table width="180" border="0" align='center' cellspacing="0" cellpadding="0">
									<tr>
										<td width="680" align="left" valign="middle" >
											<table width="700" cellspacing="1" align='center' bgcolor="#ff8c00" cellpadding="0">
											<!-- Inner content should be within the below table -->
											<tr>
												<td>
													<table width="100%" cellspacing="1" border="0" bgcolor="#FFFFFF" cellpadding="0">
														<tr>
															<td>
																<table width="100%" valign='middle' border="0" cellspacing="0" bgcolor="#FFFFFF" cellpadding="0">
																	<tr>
																		<td height="10" bgcolor="#FFFFFF"></td>
																	</tr>
																	<tr>
																		<td align='center'><a href='../../jsp/livechatadmin/agentregistration.jsp' class='classbutton'>Add&nbsp;Live&nbsp;Chat&nbsp;Agents</a></td>
																	</tr>
																	<tr height='10'></tr>
																	<tr >
																		<td align='center'><a href='../../servlet/LiveChatManagement?hidWhatToDo=fetchchatagents'class='classbutton'>Modify&nbsp;Live&nbsp;Chat&nbsp;Agent&nbsp;Details</a></td>
																	</tr>
																	<tr height='10'></tr>
																	<tr >
																		<td align='center'><a href='../../servlet/LiveChatManagement?hidWhatToDo=fetchchatagentsloginstatus'class='classbutton'>Chat&nbsp;Agent&nbsp;Login&nbsp;Details</a></td>
																	</tr>
																	<tr height='10'></tr>
																	<tr>
																		<td align='center'><a href='../../servlet/LiveChatManagement?hidWhatToDo=fetchchatagentsratings' class='classbutton'>View&nbsp;Live&nbsp;Chat&nbsp;Agents&nbsp;Rating</a></td>
																	</tr>
																	<tr height='10'></tr>
																	<tr>
																		<td align='center'><a href='../../servlet/LiveChatManagement?hidWhatToDo=fetchchathistory' class='classbutton'>View&nbsp;Agents&nbsp;&&nbsp;Users&nbsp;Chat&nbsp;History</a></td>
																	</tr>
																	<tr height='10'></tr>
																	<tr>
																		<td align='center'><a href='../../servlet/LiveChatManagement?hidWhatToDo=fetchBusychatmsgs' class='classbutton'>View&nbsp;Users&nbsp;Messages</a></td>
																	</tr>
																	<tr height='10'></tr>
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
<script type='text/javascript'>
//function used to logout from live chat admin
function adminlogout()
{
	window.location.href='../../livechatadmin/index.html';
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