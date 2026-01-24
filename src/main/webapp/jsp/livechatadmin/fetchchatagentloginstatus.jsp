<%-- 
	Author(s)			:	 Sai Krishna Daddala
	Created on			: Wednesday, April 16, 2014 6:38:12 PM
	Copyright Notice	: BookBattery Pvt.Ltd. Confidential
	Description			: Fetch chat agents login status details.
--%>
<%@page language="java" import="java.util.Hashtable,java.util.Vector,java.util.Properties,java.io.FileInputStream"%>
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
Vector alist=(Vector)session.getAttribute("ChatAgntDetls");

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
<title>Live Chat Agents Login Details</title>
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
		<form name="fetchagentdetails" method="post" >
			<div id='popup' class='popup' style="display:none;">
				<div id='popuptitle' class='popuptitle'>
					<table border='0' width='410px' height='10px' cellpadding='0' cellspacing='0'>
						<tr class='top1'>
							<td>&nbsp;<font color='#FFFFFF'>LiveChat</td><td align='right'><a href='javascript:closePopup(greyout(false));' class="bluelinks333">x</a>
							</td>
						</tr>
					</table>
				</div>
				<div id='popupmessage' class='popupmessage'></div> 
			</div>
			<table width="960" border="0" align="center" cellpadding="0" cellspacing="0">
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
					<td align="left" valign="top" bgcolor="#ffffff">
						<table width="960" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="180" height="438" align="left" valign="top">
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
												<table width="513" border="0" cellspacing="0" cellpadding="0">
													<table width="770" border="0" cellpadding="0" cellspacing="1" bgcolor="#BEADCB">
														<tr>
															<td bgcolor='#FFFFFF'>
																<table width="713" border="0" cellpadding="5" cellspacing="0" bgcolor="#FFFFFF">
																	<tr>
																		<td class="subheading"><font size="2">Chat&nbsp;Agent&nbsp;Login&nbsp;Details</font></td>
																	</tr>
																	<tr>
																		<td>
																			<table width="100%" cellspacing="1" bgcolor="#FFFFFF" cellpadding="0">
																				<tr>
																					<td>
																						<table width="100%" cellspacing="0" bgcolor="#FFFFFF" cellpadding="5">
																							<tr height="10">
																								<td colspan="3" align="center"><%=strLogMsg%></td>
																							</tr>
																							<tr>
																								<td colspan="8" align="right">
																									<table width="100%">
																										<td width="65%" align="right">
																										
																										</table>
																									</td>
																								</tr>
																							</td>
																						</tr>
																					</table>
																					<%
																					if(alist==null || alist.size() == 0)
																					{
																						%>
																						<tr>
																							<td colspan="3" align="center"> No Chat Agent is Available.</td>
																						</tr>	
																						
																						<%
																					}
																					else
																					{
																						%>
																						
																						<tr>
																							<td>
																								<table  width="100%" cellspacing="1"  cellpadding="2" bgcolor="#dddddd">
																									<tr  bgcolor="#2364b1">
																										<td align="center" class="prodheading" width="4%">S.No</td>
																										<td align="center" class="prodheading" width="20%">Chat Agent First Name</td>
																										<td align="center" class="prodheading" width="20%">Chat Agent last Name</td>
																										<td align="center" class="prodheading" width="23%">Chat Agent Emailid</td>
																										<td align="center" class="prodheading" width="13%">Status</td>
																										<td align="center" class="prodheading" width="20%">Login Time</td>
																										
																									</tr>
																									<%
																									try
																									{
																										int j=1;
																										for ( int i=0; i<alist.size() ; i++)
																										{
																											Hashtable ht=(Hashtable)alist.get(i);
																											String agent_id=String.valueOf(ht.get("agent_id"));
																											String agent_firstname=(String)ht.get("agentfirst_name");
																											String agent_lastname=String.valueOf(ht.get("agentlast_name"));
																											String agent_emailid=String.valueOf(ht.get("agent_emailid"));
																											String agent_status=String.valueOf(ht.get("agent_status"));
																											String agent_logintime=String.valueOf(ht.get("agent_logintime"));
																											if (j==(j/2)*2)
																											{
																												%>
																												<tr bgcolor="#aaaaaa">
																													<td  class="table1" width="4%"><%=j%></td>
																													<td  class="table1" width="20%"><%=agent_firstname%></td>
																													<td  class="table1" width="20%"><%=agent_lastname%></td>
																													<td  class="table1" width="23%"><%=agent_emailid%></td>
																													<td  class="table1" width="13%"><%=agent_status%></td>
																													<td  class="table1" width="20%"><%=agent_logintime%></td>
																												</tr>
																												<%
																											}
																											else
																											{
																												%>	
																												<tr bgcolor="#aaaaaa">
																													<td  class="table1" width="7%"><%=j%></td>
																													<td  class="table1" width="20%"><%=agent_firstname%></td>
																													<td  class="table1" width="20%"><%=agent_lastname%></td>
																													<td  class="table1" width="23%"><%=agent_emailid%></td>
																													<td  class="table1" width="13%"><%=agent_status%></td>
																													<td  class="table1" width="20%"><%=agent_logintime%></td>
																												</tr>
																												<% 
																											}
																											j++;
																										}
																									}
																									catch(Exception e)
																									{
																										e.printStackTrace();
																									}
																									%>
																								</table>
																							</td>
																						</tr>
																						<%
																					}
																					%>
																					<tr height="10">
																						<td colspan="3">&nbsp;</td>
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

				</form>
			</center>
	</body>
<script language="javascript">
//function used to navigate to add chat agents page when admin clicks on add button.

//function to close the popup for chatagent modify validations
function close_popup()
{
	$('#popup').hide();
	greyout(false);
}
//function used to hide popup
function closePopup()
{
	$('#popup').hide();
	greyout(false);
}
</script>
<script type='text/javascript'>
(function() {
var script = document.createElement('script');
script.src = '../../js/jquery-1.3.2.min.js';
script.async = true;
var s = document.getElementsByTagName('script')[0];
s.parentNode.insertBefore(script, s);
})();
</script>
</html>