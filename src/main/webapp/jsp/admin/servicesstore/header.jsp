<%
String strUserid=(String)session.getAttribute("sesBatteryAdminName");
if(strUserid==null)
{
	strUserid="";
}
else
{
	strUserid=strUserid;
}
%>
<% if (strUserid == "" || strUserid == null) { %>
<table width = "68%" border = "0" align = "center" cellpadding = "0" cellspacing = "0">
<tr><td height="0"></td></tr>
<tr>
	<td align = "left" valign = "top">
		<table  border="0" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="50%" align="left" valign="middle" >
					<a href="/bookbattery/admin/index.html"><img src="/bookbattery/images/bookbatterylogo.png" width="190" height="56" border="0"/></a>
				</td>
				<td width="50%" valign="center">
					<table class="insidecontent" valign="center" border="0" align="center" cellspacing="0"  cellpadding="0" width="100%">
						<tr>
							<td width="30%" class="subheadingbat" align="right"><font color="#999999">BookBattery Batteries&nbsp;Admin&nbsp;Portal</font></td><td width="5%"></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<tr><td><hr></td></tr>
<%
}
else
{
%>
<div id='divmobile' class='divmobile' style="display:none;"></div>
<table width = "68%" border = "0" align = "center" cellpadding = "0" cellspacing = "0">
<tr><td height="2"></td></tr>
<tr>
	<td align = "left" valign = "top">
		<table  border="0" width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="50%" align="left" valign="middle" >
					<a href="/bookbattery/servlet/AdminBatteryLogin?hidWhatToDo=batteryadminhome"><img src="/bookbattery/images/bookbatterylogo.png" width="190" height="56" border="0"/></a>
				</td>
				<td width="50%" valign="center">
					<table class="insidecontent" valign="center" border="0" align="center" cellspacing="0"  cellpadding="0" width="100%">
						<tr>
							<td width="30%" class="subheadingbat" align="right"><font color="#999999">BookBattery Batteries&nbsp;Admin&nbsp;Portal</font></td><td width="5%"></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<tr><td><hr></td></tr>
<%
}
%>
