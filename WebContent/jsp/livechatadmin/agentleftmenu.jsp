<%-- 
Author(s)			: Ajay K
Created on			: Thursday, January 16, 2014 3:18:07 PM
Copyright Notice	: NGIT Pvt.Ltd. Confidential
Description			: Livechat Admin Left Menu
--%>
<body onload="getmessagetrigger();">
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<div id='popup_delete_posts' class='scrollpopup' style="display:block;"></div>
<div id='popuph' class='scrollpopup' style="display:block;"></div>
	<tr>
		<td align="center" valign="top">
			<table width="180" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<table width="180" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20" align="left" valign="middle"><img src="../../images/arrow-right.png" width="16" height="16" /></td>
								<td height="25" align="left" valign="middle"><a href="../../jsp/livechatadmin/agentregistration.jsp" class="category">Add&nbsp;Live&nbsp;Chat&nbsp;Agents </a></td>
							</tr>
						</table>
					</td>
				</tr> 
				<tr><td height="1" bgcolor="#CCCCCC"></td></tr>
				<tr><td height="1" bgcolor="#FFFFFF"></td></tr>
				<tr>
					<td>
						<table width="180" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20" align="left" valign="middle"><img src="../../images/arrow-right.png" width="16" height="16" /></td>
								<td height="25" align="left" valign="middle"><a href="../../servlet/LiveChatManagement?hidWhatToDo=fetchchatagents" class="category">Modify&nbsp;Live&nbsp;Chat&nbsp;Agent&nbsp;Details</a></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr><td height="1" bgcolor="#CCCCCC"></td></tr>
				<tr><td height="1" bgcolor="#FFFFFF"></td></tr>
				<tr>
					<td>
						<table width="180" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20" align="left" valign="middle"><img src="../../images/arrow-right.png" width="16" height="16" /></td>
								<td height="25" align="left" valign="middle"><a href="../../servlet/LiveChatManagement?hidWhatToDo=fetchchatagentsloginstatus" class="category">Chat&nbsp;Agent&nbsp;Login&nbsp;Details</a></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr><td height="1" bgcolor="#CCCCCC"></td></tr>
				<tr><td height="1" bgcolor="#FFFFFF"></td></tr>
				<tr>
					<td>
						<table width="180" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20" align="left" valign="middle"><img src="../../images/arrow-right.png" width="16" height="16" /></td>
								<td height="25" align="left" valign="middle"><a href="../../servlet/LiveChatManagement?hidWhatToDo=fetchchatagentsratings" class="category">View&nbsp;Live&nbsp;Chat&nbsp;Agents&nbsp;Rating</a></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr><td height="1" bgcolor="#CCCCCC"></td></tr>
				<tr><td height="1" bgcolor="#FFFFFF"></td></tr>
				<tr>
					<td>
						<table width="180" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20" align="left" valign="middle"><img src="../../images/arrow-right.png" width="16" height="16" /></td>
								<td height="25" align="left" valign="middle"><a href="../../servlet/LiveChatManagement?hidWhatToDo=fetchchathistory" class="category">View&nbsp;Agents&nbsp;&&nbsp;Users&nbsp;Chat&nbsp;History</a></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr><td height="1" bgcolor="#CCCCCC"></td></tr>
				<tr><td height="1" bgcolor="#FFFFFF"></td></tr>
				<tr>
					<td>
						<table width="180" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="20" align="left" valign="middle"><img src="../../images/arrow-right.png" width="16" height="16" /></td>
								<td height="25" align="left" valign="middle"><a href="../../servlet/LiveChatManagement?hidWhatToDo=fetchBusychatmsgs" class="category">View&nbsp;Users&nbsp;Messages&nbsp;(<span id="messages"></span>)</a></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr><td height="1" bgcolor="#CCCCCC"></td></tr>
				<tr><td height="1" bgcolor="#FFFFFF"></td></tr>
		</table>
	<td height="25" align="center" valign="top">&nbsp;</td>
</tr>
</body>
<script type="text/javascript">
//function used to logout from live chat admin
function adminlogout()
{
	window.location.href='../../livechatadmin/index.html';
}
var messagetrigger=self.setInterval(function(){getmessagetrigger()},10000);
function getmessagetrigger()
{
	var xmlhttp;
	var url="../../servlet/LiveChatManagement?hidWhatToDo=messagescount&requestno="+(Math.random()*100);
	if (window.XMLHttpRequest)
	{
		xmlhttp=new XMLHttpRequest();
	}
	else
	{
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	var response= "";
	xmlhttp.onreadystatechange=function()
	{
		if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			var response = xmlhttp.responseText;
			response=response.trim();
			$("#messages").empty();
			$("#messages").html(response);
		}
	}
	xmlhttp.open("GET",url,true);
	xmlhttp.send();
}
</script>