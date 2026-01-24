<%-- 
	Author(s)			: Ajay K
	Created on			: Thursday, January 16, 2014 6:38:12 PM
	Copyright Notice	: BookBattery Pvt.Ltd. Confidential
	Description			: Fetch chat agents details.
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
String pagenumber = (request.getParameter("pagenumber")!=null)?(request.getParameter("pagenumber")):"0";
String Dummypagenumber = (request.getParameter("Dummypagenumber")!=null)?(request.getParameter("Dummypagenumber")):"0";
String count=(String)session.getAttribute("sesChatAgntcount");
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
<title>Live Chat Agents details</title>
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
		<form name="fetchaccount" method="post" >
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
																		<td class="subheading"><font size="2">Modify&nbsp;Live&nbsp;Chat&nbsp;Agent&nbsp;Details</font></td>
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
																											<%
																											try
																											{   
																												int j=0;
																												String strCount=count;
																												int TotalCount=Integer.parseInt(strCount);
																												int CHATAGENTS_PER_PAGE=10;
																												int tp=((int)(Math.ceil((double)TotalCount/CHATAGENTS_PER_PAGE)));
																												String Lastpage=Integer.toString(tp);
																												if(pagenumber.equals("1"))
																												{
																													%>
																													<font class="blue2">First</font>	
																													&nbsp;&nbsp;
																													<font class="blue2">Previous</font>&nbsp;&nbsp;
																													<%
																												}
																												else
																												{
																													%>	
																													<font color="#929ba1"  ><a class="blue1" href="javascript:funOnClickFirst();">First</a></font>&nbsp;&nbsp;
																													<font color="#929ba1" > <a class="blue1" href="javascript:funOnClickPrevious();" > Previous</a> </font>&nbsp;&nbsp;
																													<%
																												}
																												%>
																												<%
																												if(pagenumber.equals(Lastpage))
																												{
																													%>
																													&nbsp;&nbsp;<font class="blue2">Next</font>		
																													&nbsp;&nbsp;
																													<font class="blue2">Last</font>&nbsp;&nbsp;
																													<%
																												}
																												else
																												{
																													%>
																													&nbsp;&nbsp;<font color="#929ba1" ><a class="blue1" href="javascript:funOnClickNext();" > Next</a> </font>
																													&nbsp;&nbsp; 
																													<font color="#929ba1"><a class="blue1" href="javascript:funOnClickLast('<%=Lastpage%>');" >Last</a>&nbsp;&nbsp;</font>
																													<%
																												}
																												%>
																												</td>
																												<td width="35%" align="right"><font class="blue1">Showing page&nbsp;&nbsp;<select name="page" onChange="javascript:getpage()">
																												<%
																												String strsel="";
																												for(int i=1;i<=tp;i++)
																												{
																													String si=Integer.toString(i);
																													if(pagenumber.equals(si))
																													{
																														strsel="selected";
																													}
																													else
																													{
																														strsel="";
																													}
																													%>
																													<option value="<%=i%>"<%=strsel%>><%=i%></option> 
																													<%
																												}
																												%>
																												<select>  of&nbsp;&nbsp; 
																												<%
																												if(Lastpage.equals("0"))
																												{
																													Lastpage="1";
																												}
																												else
																												{
																													Lastpage=Lastpage;
																												}
																												%>
																												<%=Lastpage%>
																												</font>
																												</td>
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
																							</td>
																						</tr>
																					</table>
																					<%
																					if(alist==null || alist.size() == 0)
																					{
																						%>
																						<tr>
																							<td colspan="3" align="center"> There are no Chat Agents. Please click on 'Add' to add Chat Agents.
																							</td>
																						</tr>	
																						<tr bgcolor="#FFFFFF">
																							<td colspan="3" align="center" bgcolor="#FFFFFF"> 
																								<table width="15%" cellspacing="5"  cellpadding="5">
																									<tr>
																										<td align="center"><a href="javascript:addchatagent()" class="button4">Add</a></td>
																									</tr>
																								</table>	
																							</td>
																						</tr>
																						<%
																					}
																					else
																					{
																						%>
																						<tr bgcolor="#FFFFFF">
																							<td colspan="3" align="center" bgcolor="#FFFFFF"> 
																								<table width="30%" cellspacing="5"  cellpadding="5">
																									<tr>
																										<td align="center"><a href="javascript:addchatagent()" class="button4">Add</a></td>
																										<td align="center"><a href="javascript:modifyagntaccount()" class="button4">Modify</a></td>										
																										<td align="center"><a href="javascript:deleteaccount()" class="button4">Delete</a></td>
																									</tr>
																								</table>	
																							</td>
																						</tr>
																						<tr>
																							<td>
																								<table  width="100%" cellspacing="1"  cellpadding="2" bgcolor="#dddddd">
																									<tr  bgcolor="#2364b1">
																										<td align="center" class="prodheading" width="7%">SI no<input type="checkbox"  onclick="Checkall(document.fetchaccount.check)" style="display:none"></td>
																										<td align="center" class="prodheading" width="40%">ChatAgent First Name</td>
																										<td align="center" class="prodheading" width="30%">ChatAgent last Name</td>
																										<td align="center" class="prodheading" width="23%">ChatAgent Emailid</td>
																										
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
																											int pagenum=Integer.parseInt(pagenumber);
																											if (j==(j/2)*2)
																											{
																												%>
																												<tr bgcolor="#aaaaaa">
																													<td  class="table1" align="center" width="7%"><%=(10*(pagenum-1)+j)%>
																													<input type="checkbox" name="check" value="<%=agent_id%>"></td>
																													<td  class="table1" width="40%"><%=agent_firstname%></td>
																													<td  class="table1" width="30%"><%=agent_lastname%></td>
																													<td  class="table1" width="23%"><%=agent_emailid%></td>
																												</tr>
																												<%
																											}
																											else
																											{
																												%>	
																												<tr bgcolor="#aaaaaa">
																													<td  class="table1" align="center" width="7%"><%=(10*(pagenum-1)+j)%>
																													<input type="checkbox" name="check" value="<%=agent_id%>"><input type="checkbox" name="check" value="" style="display:none"></td>
																													<td  class="table1" width="40%"><%=agent_firstname%></td>
																													<td  class="table1" width="30%"><%=agent_lastname%></td>
																													<td  class="table1" width="23%"><%=agent_emailid%></td>
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
					<input type="hidden" name="pagenumber" value="<%=pagenumber%>">
					<input type="hidden" name="Dummypagenumber" value="<%=Dummypagenumber%>">
				</form>
			</center>
	</body>
<script language="javascript">
//function used to navigate to add chat agents page when admin clicks on add button.
function addchatagent()
{
	document.fetchaccount.method="post";
	document.fetchaccount.action="../../jsp/livechatadmin/agentregistration.jsp";
	document.fetchaccount.submit();
}
//function used to display chat agents list when admin clicks on first link
function funOnClickFirst()
{
	var pagenumber=document.fetchaccount.pagenumber.value;
	document.fetchaccount.method="post";
	document.fetchaccount.action="../../servlet/LiveChatManagement?hidWhatToDo=fetchchatagents&pagenumber="+pagenumber+"&pagetype=first";
	document.fetchaccount.submit();
}
//function used to display chat agents list when admin clicks on previous link
function funOnClickPrevious()
{
	var pagenumber=document.fetchaccount.pagenumber.value;
	document.fetchaccount.method="post";
	document.fetchaccount.action="../../servlet/LiveChatManagement?hidWhatToDo=fetchchatagents&pagenumber="+pagenumber+"&pagetype=previous";
	document.fetchaccount.submit();
}
//function used to display chat agents list when admin clicks on next link
function funOnClickNext()
{
	var pagenumber=document.fetchaccount.pagenumber.value;
	document.fetchaccount.method="post";
	document.fetchaccount.action="../../servlet/LiveChatManagement?hidWhatToDo=fetchchatagents&pagenumber="+pagenumber+"&pagetype=next";
	document.fetchaccount.submit();
}
//function used to display chat agents list when admin clicks on last link
function funOnClickLast(lastpage)
{
	var pagenumber=document.fetchaccount.pagenumber.value;
	document.fetchaccount.method="post";
	document.fetchaccount.action="../../servlet/LiveChatManagement?hidWhatToDo=fetchchatagents&pagenumber="+pagenumber+"&lastpage="+lastpage+"&pagetype=last";
	document.fetchaccount.submit();
}
//function used to display chat agents list when admin selects pagenumber
function getpage()
{
	var pagenumber=document.fetchaccount.page.value;
	document.fetchaccount.method="post";
	document.fetchaccount.action="../../servlet/LiveChatManagement?hidWhatToDo=fetchchatagents&pagenumber="+pagenumber;			
	document.fetchaccount.submit();
}
//function used to delete chat agent
function deleteaccount()
{
	var pagenumber=document.fetchaccount.pagenumber.value;
	var Dummypagenumber=document.fetchaccount.Dummypagenumber.value;
	var agent_id=0;
	for (i = 0; i<document.fetchaccount.check.length; i++)
	{
		if(document.fetchaccount.check[i].checked)
		{
			agent_id++;
		}
	}
	if(agent_id==0)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please Select One Check Box to delete.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		document.getElementById("emailok").focus();
		return;
	}
	if(agent_id>1)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>please select one Check Box to Delete.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		document.getElementById("emailok").focus();
		return;
	}
	var agree=confirm("Are you sure you wish to Delete Chat Agent?");
	if (agree)
	{
		document.fetchaccount.method="post";
		document.fetchaccount.action="../../servlet/LiveChatManagement?hidWhatToDo=deleteaccount&pagenumber="+pagenumber+"&feature=livechat&Dummypagenumber="+Dummypagenumber;	
		document.fetchaccount.submit();
	}
}
function modifyagntaccount()
{
	var pagenumber=document.fetchaccount.pagenumber.value;
	var agent_id=0;
	for (i = 0; i<document.fetchaccount.check.length; i++)
	{
		if(document.fetchaccount.check[i].checked)
		{
			agent_id++;
		}
	}
	if(agent_id==0)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please Select One Check Box to modify.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		document.getElementById("emailok").focus();
		return;
	}
	if(agent_id>1)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>please select one Check box to modify.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		document.getElementById("emailok").focus();
		return;
	}
	document.fetchaccount.method="post";
	document.fetchaccount.action="../../servlet/LiveChatManagement?hidWhatToDo=modifychatagent&pagenumber="+pagenumber;
	document.fetchaccount.submit();
}
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