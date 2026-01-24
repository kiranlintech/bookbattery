<%-- 
	Author(s)			: Ajay K
	Created on			: Friday, January 17, 2014 1:06:45 PM
	Copyright Notice	: BookBattery Pvt.Ltd. Confidential
	Description			: Modify chat agents details.
--%>
<%@page language="java" import="java.util.Hashtable,java.util.Vector,java.util.Enumeration,java.util.Properties,java.io.FileInputStream"%>
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
<title>Live Chat Modify Chat Agents</title>
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
		<form name="modifychatagent" method="post"  ENCTYPE="multipart/form-data">
			<div id='popup' class='popup' style="display:none;">
				<div id='popuptitle' class='popuptitle'>
					<table border='0' width='410px' height='10px' cellpadding='0' cellspacing='0'>
						<tr class='top1'><td>&nbsp;<font color='#FFFFFF'>LiveChat</td>
							<td align='right'><a href='javascript:closePopup(greyout(false));' class="bluelinks333">x</a></td>
						</tr>
					</table>
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
												<table width="770" cellspacing="1" border="0"  cellpadding="0">
													<tr>
														<td>
															<table width="100%" cellspacing="1" border="0" bgcolor="#BEADCB" cellpadding="0">
																<tr>
																	<td>
																		<table width="100%" cellspacing="0" bgcolor="#FFFFFF" cellpadding="5px">
																			<tr>
																				<td height="10" bgcolor="#FFFFFF"></td>
																			</tr>
																			<tr>
																				<td class="subheading"> <font size="2">Modify ChatAgent </td>
																			</tr>
																			<tr>
																				<td>
																					<table width="80%" cellspacing="1" border="0" bgcolor="#FFFFFF" cellpadding="0">
																						<tr>
																							<td>
																								<table width="80%" cellspacing="3" bgcolor="#FFFFFF" cellpadding="5" border="0">
																									<tr height="10">
																										<td colspan="3" align="center"><%=strLogMsg%></td>
																									</tr>
																									<%
																									try
																									{
																										int m=1;
																										for ( int i=0; i<alist.size() ; i++)
																										{
																											Hashtable ht=(Hashtable)alist.get(i);
																											String agentfirstname=(String)ht.get("agentfirst_name");
																											String agentlastname=(String)ht.get("agentlast_name");
																											String strloginname=(String)ht.get("agent_loginname");
																											String stricon_url=(String)ht.get("agent_picture_url");
																											String stremail_id=(String)ht.get("agent_emailid");
																											String mobilenumber=(String)ht.get("agent_mobile_number");
																											//String chatlimit=String.valueOf(ht.get("agent_chatlimit"));
																											String thumbNailUrl="";
																											if(stricon_url=="" || stricon_url==null || stricon_url.equals(""))
																											{
																												stricon_url ="../../images/defaultagent.png";
																											}
																											else
																											{
																												stricon_url = stricon_url;
																											}
																											int indexofSlash=stricon_url.lastIndexOf("/");
																											thumbNailUrl=stricon_url.substring(0,indexofSlash);
																											thumbNailUrl=thumbNailUrl+"/"+stricon_url.substring(indexofSlash+1);
																											String agent_id=String.valueOf(ht.get("agent_id"));
																											%>
																											<tr>
																												<td class="subheading" >ChatAgent&nbsp;First&nbsp;Name<font color="FF0000">*</font></td>
																												<td>&nbsp;<input  type="text" name="cafirstname" maxlength="50" size="31" value="<%=agentfirstname%>"></td>
																												<td colspan="3" class="prodheading1"></td>
																												<td>&nbsp;<input  type="hidden" name="chatagentid" maxlength="50" size="31" value="<%=agent_id%>"></td>
																											</tr>
																											<tr>
																												<td class="subheading">ChatAgent&nbsp;Last&nbsp;Name<font color="FF0000">*</font></td>
																												<td>&nbsp;<input type="text" name="calastname"  size="31" value="<%=agentlastname%>"></td>
																											</tr>
																											<tr class="#FFFFFF" bgcolor="FFFFFF">
																												<td width="30%" class="subheading" >ChatAgent&nbsp;Login&nbsp;Name<font color="FF0000">*</font></td>
																												<td width="40%">&nbsp;<input type="text" readonly name="chatagentloginname"  maxlength="50" size="31" value="<%=strloginname%>" onchange="javascript:checkreporterloginname()"onkeydown="clearmsg();"  onmousedown="clearmsg();"></td>
																												<tr><td></td><td width="30%" class="insidecontent"><div id="divsesmsgretlogin"></div></td></tr>
																											</tr>
																											<tr>
																												<td class="subheading">ChatAgent&nbsp;Email-id<font color="FF0000">*</font></td>
																												<td>&nbsp;<input type="text" name="emailid"  size="31" value="<%=stremail_id%>"></td>
																											</tr>
																											<tr>
																												<td class="subheading">Chat&nbsp;Agent&nbsp;Mobile&nbsp;Num<font color="FF0000">*</font></td>
																												<td>&nbsp;<input type="text" id="mobilenumber" name="mobilenumber"  size="31" value="<%=mobilenumber%>"  maxlength="10" ></td>
																											</tr>
																											<!--<tr>
																												<td class="subheading">Chat&nbsp;Users&nbsp;Limit<font color="FF0000">*</font></td>
																												<td>&nbsp;<input type="text" name="chatlimit"  size="31" maxlength="3" ></td>
																											</tr>-->
																											<tr>
																												<td class="subheading">ChatAgent&nbsp;Picture<font color="FF0000">*</font></td>
																												<td width="30%" align="left">&nbsp;<img src=<%=thumbNailUrl%> bordercolor="#000000" border="1" height="75" width="75"/>
																												&nbsp;&nbsp;<input type="file" name="attachment" class="top1" onkeydown="javascript:return false;" onchange="checkFile(this)">
																												<font class="insidecontent" width="70%">*<u>&nbsp;Image&nbsp;formats&nbsp;</u>:&nbsp;jpg&nbsp;&&nbsp;png</td>
																											</tr>
																											<tr bgcolor="FFFFFF">
																												<td class="subheading" colspan="4">
																													<table align="center" border="0" cellpadding="0" cellspacing="0">
																														<tr height="20">
																															<td >&nbsp;&nbsp;&nbsp;<a href="javascript:modifychatagent()" class="button4">Submit</a>&nbsp;&nbsp;&nbsp;</td>
																															<td >&nbsp;&nbsp;&nbsp;<a href="../../jsp/livechatadmin/modifychatagents.jsp" class="button4">Reset</a>&nbsp;&nbsp;&nbsp;</td>
																															<td >&nbsp;&nbsp;&nbsp;<a href="javascript:back()" class="button4">Back</a>&nbsp;&nbsp;&nbsp;</td>
																														</tr>
																													</table>
																												</td>
																											</tr>
																										<%
																										}
																									}
																									catch(Exception e )
																									{
																										e.printStackTrace();
																									}
																									%>
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
					</td>
				</tr>
			</table>
			<input type="hidden" name="pagenumber" value="<%=pagenumber%>">
		</form>
	</center>
</body>
<script type="text/JavaScript">
/*Function to modify the retailers details**/
function modifychatagent()
{
	var fname=document.modifychatagent.cafirstname.value;
	var agentid=document.modifychatagent.chatagentid.value;
	var lname=document.modifychatagent.calastname.value;
	var caloginname=document.modifychatagent.chatagentloginname.value;
	var emailid=document.modifychatagent.emailid.value;
	var mobilenumber=document.modifychatagent.mobilenumber.value;
	//var chatlimit=document.modifychatagent.chatlimit.value;
	var filename = document.modifychatagent.attachment.value;
	var pagenumber=document.modifychatagent.pagenumber.value;
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
	if(document.modifychatagent.cafirstname.value.length < 3)
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
	if(document.modifychatagent.calastname.value.length < 3)
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
	if(document.modifychatagent.chatagentloginname.value.length < 3)
	{
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter at least 3 characters in the \"chatagent login name\" field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_caloginname();' class='button4'><br></td></tr>";
		document.getElementById("popup").style.display='block';
		greyout(true);
		document.getElementById("popupmessage").innerHTML=errMsg
		document.getElementById("emailok").focus();
		return;
	}
	if (document.modifychatagent.chatagentloginname.value.length > 50)
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
		
		if (! document.modifychatagent.emailid.value.match(re))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok'  value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("emailok").focus();
			return ;
		}
		if (! document.modifychatagent.emailid.value.match(regexp))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("emailok").focus();
			return ;
		}
		if (! document.modifychatagent.emailid.value.match(reg))
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of free2rhyme@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
			document.getElementById("popup").style.display='block';
			greyout(true);
			document.getElementById("popupmessage").innerHTML=errMsg
			document.getElementById("emailok").focus();
			return ;
		}
		if (document.modifychatagent.emailid.value.indexOf('gmail') >=0)
		{
			if (document.modifychatagent.emailid.value.indexOf('com')<0)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of smith@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				document.getElementById("emailok").focus();
				return ;
			}
		}
		if (document.modifychatagent.emailid.value.indexOf('rediffmail') >=0)
		{
			if (document.modifychatagent.emailid.value.indexOf('com')<0)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of smith@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				document.getElementById("emailok").focus();
				return ;
			}
		}
		if (document.modifychatagent.emailid.value.indexOf('hotmail') >=0)
		{
			if (document.modifychatagent.emailid.value.indexOf('com')<0)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of smith@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				document.getElementById("emailok").focus();
				return ;
			}
		}
		if (document.modifychatagent.emailid.value.indexOf('live') >=0)
		{
			if (document.modifychatagent.emailid.value.indexOf('com')<0)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of smith@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				document.getElementById("emailok").focus();
				return ;
			}
		}
		if (document.modifychatagent.emailid.value.indexOf('ymail') >=0)
		{
			if (document.modifychatagent.emailid.value.indexOf('com')<0)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Email Id Should be in the form of smith@ngit.in</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='&nbsp;&nbsp;OK&nbsp;&nbsp;' onclick='close_popup_email();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				document.getElementById("emailok").focus();
				return ;
			}
		}
		if (document.modifychatagent.emailid.value.indexOf('com.in') >=0)
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
		for (var i = 0; i < document.modifychatagent.emailid.value.length; i++)
		{
			if (iChars1.indexOf(document.modifychatagent.emailid.value.charAt(i)) != -1)
			{
				errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Special characters are not allowed in Email-Id field.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup_email();' class='button4'><br></td></tr>";
				document.getElementById("popup").style.display='block';
				greyout(true);
				document.getElementById("popupmessage").innerHTML=errMsg
				document.getElementById("emailok").focus();
				return;
			}
		}
		if (document.modifychatagent.emailid.value.length >50)
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
	if(filename!="")
	{
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
	}
	if (filename =="")
	{
		imagename="NA";
	}
	$('#button').hide();
	document.modifychatagent.method="post";
	document.modifychatagent.action="../../servlet/LiveChatManagement?hidWhatToDo=updatechatagent&firstname="+fname+"&lastname="+lname+"&loginname="+caloginname+"&feature=livechat&emailid="+emailid+"&mobilenumber="+mobilenumber+"&check="+agentid+"&pagenumber="+pagenumber+"&imagattach="+imagename;
	document.modifychatagent.submit();
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
		return;
	}
	
}
//function used when admin click on back button page should navigate to fetch agents
function back()
{
	var agentid=document.modifychatagent.chatagentid.value;
	var pagenumber=document.modifychatagent.pagenumber.value;
	document.modifychatagent.method="POST";
	document.modifychatagent.action="../../servlet/LiveChatManagement?hidWhatToDo=fetchchatagents&check="+agentid+"&pagenumber="+pagenumber;
	document.modifychatagent.submit();
}
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
	document.modifychatagent.cafirstname.focus();
}
function close_popup_mobilenumber()
{
	$('#popup').hide();
	greyout(false);
	document.modifychatagent.mobilenumber.focus();
}

//function to close the popup for chatagent last name validations
function close_popup_lastname()
{
	$('#popup').hide();
	greyout(false);
	document.modifychatagent.calastname.focus();
}
//function to close the popup for chatagent login name validations
function close_popup_caloginname()
{
	$('#popup').hide();
	greyout(false);
	document.modifychatagent.chatagentloginname.focus();
}
//function to close the popup for chatagent emailid validations
function close_popup_email()
{
	$('#popup').hide();
	greyout(false);
	document.modifychatagent.emailid.focus();
}
//function to close the popup for chatagent image validations
function closeimage()
{
	$('#popup').hide();
	greyout(false);
	document.modifychatagent.attachment.focus();
}
//function to close the popup for chatagent chat user limit validations
function closechatlimit()
{
	$('#popup').hide();
	greyout(false);
	document.modifychatagent.chatlimit.focus();
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