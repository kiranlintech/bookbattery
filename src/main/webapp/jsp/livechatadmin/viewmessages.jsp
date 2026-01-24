<%-- 
	Author(s)			: Ajay K
	Created on			: Thursday, January 30, 2014 12:38:12 PM
	Copyright Notice	: BookBattery Pvt.Ltd. Confidential
	Description			: Live Chat Agents Busy Messages.
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
Vector alist=(Vector)session.getAttribute("ChatAgntBusyMsgDetls");
String pagenumber = (request.getParameter("pagenumber")!=null)?(request.getParameter("pagenumber")):"0";
String Dummypagenumber = (request.getParameter("Dummypagenumber")!=null)?(request.getParameter("Dummypagenumber")):"0";
String count=(String)session.getAttribute("sesChatBusyMsgcount");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link href="../../css/livechat.css" rel="stylesheet" type="text/css" />
<style type = "text/css">
</style>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../images/favicon.png" type="image/x-icon">
<title>Live Chat User Busy Messages</title>
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
<div id='divmobile' class='divmobile'  style="background-color:white"></div>
	<center>
		<form name="fetchbusymsg" method="post" >
			<div id='popup' class='popup' style="display:none;">
				<div id='popuptitle' class='popuptitle'>
					<table border='0' width='410px' height='10px' cellpadding='0' cellspacing='0'>
						<tr class='top1'>
							<td>&nbsp;<font color='#FFFFFF'>LiveChat</td><td align='right'><a href='javascript:reloadpage(greyout(false));' class="bluelinks333">x</a>
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
																		<td class="subheading"><font size="2">View Users Messages</font></td>
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
																							<td colspan="3" align="center">User messages are not available.
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
																								<table  width="100%" cellspacing="1"  cellpadding="3" bgcolor="#dddddd" style='table-layout:fixed;'>
																									<tr  bgcolor="#2364b1">
																										<td align="center" class="prodheading" width="7%">SI no</td>
																										<td align="center" class="prodheading" width="15%">User&nbsp;Name</td>
																										<td align="center" class="prodheading" width="18%">User&nbsp;Email</td>
																										<td align="center" class="prodheading" width="33%">User&nbsp;Message</td>
																										<td align="center" class="prodheading" width="15%">Creation&nbsp;Date</td>
																										<td align="center" class="prodheading" width="12%">Send&nbsp;Reply</td>
																										
																									</tr>
																									<%
																									try
																									{
																										int j=1;
																										for ( int i=0; i<alist.size() ; i++)
																										{
																											Hashtable ht=(Hashtable)alist.get(i);
																											String user_name=(String)ht.get("user_name");
																											String user_emailid=(String)ht.get("user_emailid");
																											String user_message=(String)ht.get("user_message");
																											String creationdate=String.valueOf(ht.get("creation_date"));
																											String user_id=String.valueOf(ht.get("user_id"));
																											int pagenum=Integer.parseInt(pagenumber);
																											if (j==(j/2)*2)
																											{
																												%>
																												<tr bgcolor="#aaaaaa">
																													<td  class="table1" align="center" width="7%"><%=(10*(pagenum-1)+j)%>
																													<td  class="table1" style='word-wrap: break-word;' width="15%"><%=user_name%></td>
																													<td  class="table1" style='word-wrap: break-word;' width="18%"><%=user_emailid%></td>
																													<td  class="table1" style='word-wrap: break-word;' width="33%"><%=user_message%></td>
																													<td  class="table1" width="15%"><%=creationdate%></td>
																													<td  align="center" class="table1" width="12%"><INPUT TYPE="button" VALUE="Reply" ONCLICK="sendreply('<%=user_emailid%>','<%=user_name%>','<%=user_id%>')" class='button4'></td>
																												</tr>
																												<%
																											}
																											else
																											{
																												%>	
																												<tr bgcolor="#aaaaaa">
																													<td  class="table1" align="center" width="7%"><%=(10*(pagenum-1)+j)%>
																													<td  class="table1" style='word-wrap: break-word;' width="15%"><%=user_name%></td>
																													<td  class="table1" style='word-wrap: break-word;' width="18%"><%=user_emailid%></td>
																													<td  class="table1" style='word-wrap: break-word;' width="33%"><%=user_message%></td>
																													<td  class="table1" width="15%"><%=creationdate%></td>
																													<td  align="center" class="table1" width="12%"><INPUT TYPE="button" VALUE="Reply" ONCLICK="sendreply('<%=user_emailid%>','<%=user_name%>','<%=user_id%>')" class='button4'></td>
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
	document.fetchbusymsg.method="post";
	document.fetchbusymsg.action="../../jsp/livechatadmin/adminmain.jsp";
	document.fetchbusymsg.submit();
}
//function used to display chat agents ratings list when admin clicks on first link
function funOnClickFirst()
{
	var pagenumber=document.fetchbusymsg.pagenumber.value;
	document.fetchbusymsg.method="post";
	document.fetchbusymsg.action="../../servlet/LiveChatManagement?hidWhatToDo=fetchBusychatmsgs&pagenumber="+pagenumber+"&pagetype=first";
	document.fetchbusymsg.submit();
}
//function used to display chat agents ratings list when admin clicks on previous link
function funOnClickPrevious()
{
	var pagenumber=document.fetchbusymsg.pagenumber.value;
	document.fetchbusymsg.method="post";
	document.fetchbusymsg.action="../../servlet/LiveChatManagement?hidWhatToDo=fetchBusychatmsgs&pagenumber="+pagenumber+"&pagetype=previous";
	document.fetchbusymsg.submit();
}
//function used to display chat agents ratings list when admin clicks on next link
function funOnClickNext()
{
	var pagenumber=document.fetchbusymsg.pagenumber.value;
	document.fetchbusymsg.method="post";
	document.fetchbusymsg.action="../../servlet/LiveChatManagement?hidWhatToDo=fetchBusychatmsgs&pagenumber="+pagenumber+"&pagetype=next";
	document.fetchbusymsg.submit();
}
//function used to display chat agents ratings list when admin clicks on last link
function funOnClickLast(lastpage)
{
	var pagenumber=document.fetchbusymsg.pagenumber.value;
	document.fetchbusymsg.method="post";
	document.fetchbusymsg.action="../../servlet/LiveChatManagement?hidWhatToDo=fetchBusychatmsgs&pagenumber="+pagenumber+"&lastpage="+lastpage+"&pagetype=last";
	document.fetchbusymsg.submit();
}
//function used to display chat agents ratings list when admin selects pagenumber
function getpage()
{
	var pagenumber=document.fetchbusymsg.page.value;
	document.fetchbusymsg.method="post";
	document.fetchbusymsg.action="../../servlet/LiveChatManagement?hidWhatToDo=fetchBusychatmsgs&pagenumber="+pagenumber;			
	document.fetchbusymsg.submit();
}
//function used to display div for admin to send reply to user.
function sendreply(user_emailid,user_name,user_id)
{
	var results="";
	results=results+"<table cellspacing='0' cellpadding='0' border='0'>";
	results=results+"<tr><td width='15' height='15' align='left' valign='top'><img src=\"../../images/index_21.gif\" /></td>";
	results=results+"<td align='left' valign='top' background=\"../../images/index_22.gif\" style='background-repeat:repeat-x'></td>";
	results=results+"<td width='15' height='15' align='left' valign='top'><img src=\"../../images/index_24.gif\" /></td> </tr>";
	results=results+"<tr><td align='left' valign='top' background=\"../../images/index_30.gif\" style='background-repeat:repeat-y'></td>";
	results=results+"<td  bgcolor='#FFFFFF' valign='top'><table width='400' height='200' border='0'><tr><td></td><td align='right'><a href='javascript:closemaildiv();'><img src=\"../../images/Delete1.png \" valign='top' border='0' width='20' height='20'></a></td></tr>";
	results=results+"<tr valign='top'><td align='left' class='insidecontent' valign='top'>User Email-id<font color='red'>*</font></td><td valign='top'><input type='text' name ='useremail' id='useremail' readonly value="+user_emailid+"></td></tr>";
	results=results+"<tr valign='top'><td class='insidecontent' align='left' valign='top'>Message<font color='red'>*</font></td><td align='left' class='insidecontent' valign='top'><textarea rows='4' cols='25' name='usermessage' id='usermessage'></textarea></td></tr><tr><td></td><td id='validation'></td></tr><tr><table align='center'><tr><td align='center'><a href=\"javascript:generatemail('"+user_name+"','"+user_id+"');\" class='button4'><b>Submit</b></a></td><td align='center'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:closemaildiv();' class='button4'><b>Cancel</b></a></td></tr></table></td>";
	results=results+"<td align='left' valign='top' background=\"../../images/index_32.gif\" style='background-repeat:repeat-y'></td></tr>";
	results=results+"<tr><td align='left' valign='top'><img src=\"../../images/index_44.gif\"/></td>";
	results=results+"<td align='left' valign='top' background=\"../../images/index_45.gif\" style='background-repeat:repeat-x'></td>";
	results=results+"<td align='left' valign='top'><img src=\"../../images/index_47.gif\"/></td></tr>";
	results=results+"</table>";
	document.getElementById("divmobile").innerHTML="";
	document.getElementById("divmobile").innerHTML=results;
	$("#divmobile").show();
	greyout(true);
}
function generatemail(username,user_id)
{
	var useremail=document.getElementById("useremail").value;
	var usermessage=document.getElementById("usermessage").value;
	//var username=document.getElementById("username").value;
	if(usermessage =="" || usermessage.indexOf(' ')==0)
	{
			errMsg ="<font color='blue' size='2px' >Please Enter Message.</font>";
			document.getElementById("validation").innerHTML=errMsg
			document.getElementById("usermessage").focus();
			return;
	}
	var msglength = usermessage.length;
	if(msglength < 3)
	{
			errMsg ="<font color='blue' size='2px' >Enter Atleast Three Characters.</font>";
			document.getElementById("validation").innerHTML=errMsg
			document.getElementById("usermessage").focus();
			return;
	}
	if(msglength> 2500)
	{
			errMsg ="<font color='blue' size='2px' >Yor message length has exceeded limit. Please Reduce.</font>";
			document.getElementById("validation").innerHTML=errMsg
			document.getElementById("usermessage").focus();
			return;
	}
	
	var regexLetter = /\d/;
	var regexNum = /[a-zA-Z]/;
	if(!regexNum.test(usermessage) && regexLetter.test(usermessage))
	{

		errMsg ="<font color='blue' size='2px' >Only numerics are not allowed in chatagent Message field.</font>";			document.getElementById("validation").innerHTML=errMsg
			document.getElementById("usermessage").focus();
			return;
		
	}
	usermessage=escape(usermessage);
	$.get("../../servlet/LiveChatManagement?hidWhatToDo=sendmessageTouser&useremail="+useremail+"&adminmessage="+usermessage+"&username="+username+"&user_id="+user_id+"&requestno="+(Math.random()*100),function(response, status, xhr)
	{
		if (status == "success")
		{
			$("#divmobile").hide();
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>You have sent Email Successfully</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='reloadpage();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			return ;
		}
	});
}
//function used to close email div
function closemaildiv()
{
	$("#divmobile").hide();
	greyout(false);
}
//function used to reload the page
function reloadpage()
{
	$("#popup").hide();
	greyout(false);
	document.fetchbusymsg.method="post";
	document.fetchbusymsg.action="../../servlet/LiveChatManagement?hidWhatToDo=fetchBusychatmsgs";			
	document.fetchbusymsg.submit();
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