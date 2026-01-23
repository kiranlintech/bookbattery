<%-- 
	Author(s)			: Ajay K
	Created on			: Thursday, January 30, 2014 12:38:12 PM
	Copyright Notice	: BookBattery Pvt.Ltd. Confidential
	Description			: Live Chat Agents View History.
--%>
<%@page language="java" import="java.io.File,java.util.Hashtable,java.util.Vector,java.util.Properties,java.io.FileInputStream"%>
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
ServletContext context = getServletContext();
FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
propslivechatConfig.load(fin1); 
fin1.close();
String adminlogo = (propslivechatConfig.getProperty("adminlogo")!=null)?propslivechatConfig.getProperty("adminlogo"):"";
Vector alist=(Vector)session.getAttribute("ChatAgntHistryDetls");
String pagenumber = (request.getParameter("pagenumber")!=null)?(request.getParameter("pagenumber")):"0";
String Dummypagenumber = (request.getParameter("Dummypagenumber")!=null)?(request.getParameter("Dummypagenumber")):"0";
String count=(String)session.getAttribute("sesChatAgntHistrycount");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link href="../../css/livechat.css" rel="stylesheet" type="text/css" />
<style type = "text/css">
.chathistory{left:35%;font:16px/22px 'Trebuchet MS',Verdana,Arial;text-align:left;border:1px solid #ff8c00;position:fixed;padding:0;display:none;z-index:10;top:20%;outline:0;text-decoration:none;background:#fff}
* html .chathistory{position:absolute}
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../images/favicon.png" type="image/x-icon">
<title>Live Chat Agents View History</title>
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
<DIV ID="chathistory" style="display:none;max-height:30%;width:600px;overflow-Y:auto; overflow-X:hidden;" class="chathistory"></DIV>
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
																		<td class="subheading"><font size="2">View&nbsp;Agents&nbsp;&&nbsp;Users&nbsp;Chat&nbsp;History</td>
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
																							<td colspan="3" align="center"> There are no Chat Agent History available.
																							</td>
																						</tr>	
																						<tr bgcolor="#FFFFFF">
																							<td colspan="3" align="center" bgcolor="#FFFFFF"> 
																								<table width="15%" cellspacing="5"  cellpadding="5">
																									<tr>
																										<td align="center"><a href="javascript:back()" class="button4">Back</a></td>
																									</tr>
																								</table>	
																							</td>
																						</tr>
																						<%
																					}
																					else
																					{
																						%>
																						<tr>
																							<td>
																								<table  width="100%" cellspacing="1"  cellpadding="3" bgcolor="#dddddd">
																									<tr  bgcolor="#2364b1">
																										<td align="center" class="prodheading" width="7%">SI no</td>
																										<td align="center" class="prodheading" width="20%">ChatAgent&nbsp;Name</td>
																										<td align="center" class="prodheading" width="20%">User&nbsp;Name</td>
																										<td align="center" class="prodheading" width="33%">Chat&nbsp;History</td>
																										<td align="center" class="prodheading" width="43%">Creation&nbsp;Date</td>
																										
																									</tr>
																									<%
																									try
																									{
																										int j=1;
																										for ( int i=0; i<alist.size() ; i++)
																										{
																											Hashtable ht=(Hashtable)alist.get(i);
																											String agentfirst_name=(String)ht.get("agentfirst_name");
																											String agentlast_name=(String)ht.get("agentlast_name");
																											String user_name=(String)ht.get("user_name");
																											String creationdate=String.valueOf(ht.get("creation_date"));
																											String agentid=String.valueOf(ht.get("agent_id"));
																											String userid=String.valueOf(ht.get("user_id"));
																											int pagenum=Integer.parseInt(pagenumber);
																											if (j==(j/2)*2)
																											{
																												%>
																												<tr bgcolor="#aaaaaa">
																													<td  class="table1" align="center" width="7%"><%=(10*(pagenum-1)+j)%>
																													<td  class="table1" width="20%"><%=agentfirst_name%></td>
																													<td  class="table1" width="20%"><%=user_name%></td>
																													<td  class="table1" width="33%"><A HREF="javascript:loadchathistory('<%=agentid%>','<%=userid%>')" style='text-decoration:underline;font-family:verdana;text-align:left;font-size:11px;color:#0000ff;padding:4px 12px;' >Click Here to View Chat history</A></td>
																													<td  class="table1" width="43%"><%=creationdate%></td>
																												</tr>
																												<%
																											}
																											else
																											{
																												%>	
																												<tr bgcolor="#aaaaaa">
																													<td  class="table1" align="center" width="7%"><%=(10*(pagenum-1)+j)%>
																													<td  class="table1" width="20%"><%=agentfirst_name%></td>
																													<td  class="table1" width="20%"><%=user_name%></td>
																													<td  class="table1" width="33%"><A HREF="javascript:loadchathistory('<%=agentid%>','<%=userid%>')" style='text-decoration:underline;font-family:verdana;text-align:left;font-size:11px;color:#0000ff;padding:4px 12px;' >Click Here to View Chat history</A></td>
																													<td  class="table1" width="43%"><%=creationdate%></td>
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
//function used to navigate to admin main page when admin clicks on back button.
function back()
{
	document.fetchaccount.method="post";
	document.fetchaccount.action="../../jsp/livechatadmin/adminmain.jsp";
	document.fetchaccount.submit();
}
//function used to display chat agents ratings list when admin clicks on first link
function funOnClickFirst()
{
	var pagenumber=document.fetchaccount.pagenumber.value;
	document.fetchaccount.method="post";
	document.fetchaccount.action="../../servlet/LiveChatManagement?hidWhatToDo=fetchchathistory&pagenumber="+pagenumber+"&pagetype=first";
	document.fetchaccount.submit();
}
//function used to display chat agents ratings list when admin clicks on previous link
function funOnClickPrevious()
{
	var pagenumber=document.fetchaccount.pagenumber.value;
	document.fetchaccount.method="post";
	document.fetchaccount.action="../../servlet/LiveChatManagement?hidWhatToDo=fetchchathistory&pagenumber="+pagenumber+"&pagetype=previous";
	document.fetchaccount.submit();
}
//function used to display chat agents ratings list when admin clicks on next link
function funOnClickNext()
{
	var pagenumber=document.fetchaccount.pagenumber.value;
	document.fetchaccount.method="post";
	document.fetchaccount.action="../../servlet/LiveChatManagement?hidWhatToDo=fetchchathistory&pagenumber="+pagenumber+"&pagetype=next";
	document.fetchaccount.submit();
}
//function used to display chat agents ratings list when admin clicks on last link
function funOnClickLast(lastpage)
{
	var pagenumber=document.fetchaccount.pagenumber.value;
	document.fetchaccount.method="post";
	document.fetchaccount.action="../../servlet/LiveChatManagement?hidWhatToDo=fetchchathistory&pagenumber="+pagenumber+"&lastpage="+lastpage+"&pagetype=last";
	document.fetchaccount.submit();
}
//function used to display chat agents ratings list when admin selects pagenumber
function getpage()
{
	var pagenumber=document.fetchaccount.page.value;
	document.fetchaccount.method="post";
	document.fetchaccount.action="../../servlet/LiveChatManagement?hidWhatToDo=fetchchathistory&pagenumber="+pagenumber;			
	document.fetchaccount.submit();
}
function loadchathistory(agentid,userid)
{
	$.get("../../servlet/LiveChatManagement?hidWhatToDo=fetchchathistorymsgs&agentid="+agentid+"&userid="+userid+"&requestno="+(Math.random()*100),function(response, status, xhr) 
	{
		if (status == "success" )
		{
				$("#chathistory").empty();
				$("#chathistory").html(response);
				$("#chathistory").show();
				greyout(true);
		}
	});
}
function closechatdiv()
{
	$("#chathistory").hide();
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