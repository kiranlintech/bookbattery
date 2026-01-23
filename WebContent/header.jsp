<%-- 
Author           : Prakash M
Created on       : Oct 21, 2013, 10:10:12 AM
Copyright Notice : BookBattery Confidential.
Document         : This jsp is used as header in all consumer pages of BookBattery.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.io.File,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.*,java.util.Enumeration,java.util.Properties,java.io.FileInputStream,javax.servlet.ServletContext"%>
<%
Properties propsMOPConfig = new Properties();

ServletContext context = getServletContext();
FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
propsMOPConfig.load(fin1); 
fin1.close(); 
String strheight = (propsMOPConfig.getProperty("HEIGHT")!=null)?propsMOPConfig.getProperty("HEIGHT"):"";
String strwidth = (propsMOPConfig.getProperty("WIDTH")!=null)?propsMOPConfig.getProperty("WIDTH"):"";
String strads = (propsMOPConfig.getProperty("ADVERTISEMENTS")!=null)?propsMOPConfig.getProperty("ADVERTISEMENTS"):"";
String domainname = (propsMOPConfig.getProperty("domainname")!=null)?propsMOPConfig.getProperty("domainname"):"";
String publicUrl = (propsMOPConfig.getProperty("publicUrl")!=null)?propsMOPConfig.getProperty("publicUrl"):"";
String baseurldirectory = (propsMOPConfig.getProperty("baseurldirectory")!=null)?propsMOPConfig.getProperty("baseurldirectory"):"";

%>
<%
String strUserid=(String)session.getAttribute("sesBatteryUserLogin");
String strloyaltyvalue=(String)session.getAttribute("sesloyaltyvalue"); 
String strsesusername=(String)session.getAttribute("sesusername");
%>
<script src="./js/jqFancyTransitions.1.8.min.js" type="text/javascript">
</script>
<script type="text/javascript">

function scrollingtopadsDefault()
{
	   url="./servlet/ScrollingTopAdsServlet?hidWhatToDo=scrolldefaulttopads&requestno="+(Math.random()*100);
        $.get(url,function(response, status, xhr) {
        if (status == "success" ) 
        {
                $('#topads').html(response);
        }
        $('#topads').jqFancyTransitions({effect: 'wave', delay: 2250, width: 950, height: 150, links: true ,navigation: true,direction: 'left', strips: 1 });});
}

</script>
<!-- code for personal ads image zooming div for tablet-->
<% if (strUserid == "" || strUserid == null) { %>
<table border='0' cellpadding="0" width="950" align="center">
	<tr>
		<td width="30%" align="left"><a href="./index.jsp" tabindex="0" >
		<img  src="./images/bookbatterylogo.png" alt="Amaron online genuine car battery or inverter battery or bike battery or bus battery or tractor battery or truck battery store in india." width="190" height="56"  border="0" /></a>
		</td>
		<td width="43%" align='center'>
		<img width='160' height='26' src='./images/phone.png' alt="Call Now to order for car battery or inverter battery or bike battery or bus battery or tractor battery or truck battery"  border='0' />
		</td>
		<td width="18%" align='right'>
		<a class="insidecontent" href="./index.jsp"><img width='25' height='25' src='./images/home25.png' alt="Go to BookBattery home page"  border='0' />&nbsp;&nbsp;<br>
		<font style="font-family:Verdana;font-size:10px;font-weight:bold;color:#FF8C00;text-decoration:none;padding:1px 1px;">Home</font></a>
		</td>
		<td align='right' width='9%'><a href="./jsp/login/login.jsp"style='text-decoration:none;'><font size='2' color='#FF8c00'><b>&nbsp;&nbsp;&nbsp;&nbsp;Log In</b></font></a>
		</td>
	</tr>
	<tr><td height='4'></td></tr>
</table>
<%
}
else
{
%>
<table border='0' cellpadding="0" width="950" align="center">
	<tr>
		<td width="20%" align="left"><a href="./servlet/UserLoginDetails?hidWhatToDo=merchantLoyalty&emailid=<%=strUserid%>" tabindex="0" >
		<img  src="./images/bookbatterylogo.png" alt="Amaron online genuine car battery or inverter battery or bike battery or bus battery or tractor battery or truck battery store in india." width="190" height="56"  border="0" /></a>
		</td>
		<td width="20%" align='center'><font style="font-family:Verdana;font-size:12px;font-weight:bold;color:#010101;text-decoration:none;padding:1px 1px;">You have</font> <font style="font-family:Verdana;font-size:12px;font-weight:bold;color:#FF8C00;text-decoration:none;padding:1px 1px;"><%=strloyaltyvalue%></font> <font style="font-family:Verdana;font-size:12px;font-weight:bold;color:#010101;text-decoration:none;padding:1px 1px;">loyalty points.</font></td>
		<td width="20%" align='center'>
		<img width='160' height='26' src='./images/phone.png' alt="Call Now to order for car battery or inverter battery or bike battery or bus battery or tractor battery or truck battery"  border='0' />
		</td>
		<td width="5%" align='center'>
		<a class="insidecontent" href="./servlet/UserLoginDetails?hidWhatToDo=merchantLoyalty&emailid=<%=strUserid%>"><img width='25' height='25' src='./images/home25.png' alt="Go to BookBattery home page"  border='0' />&nbsp;&nbsp;<br>
		<font style="font-family:Verdana;font-size:10px;font-weight:bold;color:#FF8C00;text-decoration:none;padding:1px 1px;">Home</font></a>
		</td>
		<td align='right' width='20%'><font style="font-family:Verdana;font-size:10px;color:#000000;text-decoration:none;padding:1px 1px;">Welcome&nbsp;<%=strsesusername%>&nbsp;!&nbsp;</font><br><br><a href="./jsp/login/logout.jsp"style='text-decoration:none;'><font size='2' color='#FF8c00'><b>Log Out&nbsp;</b></font></a>
		</td>
	</tr>
	<tr><td height='4'></td></tr>
</table>

<%
}
%>
<table width = "950" border = "0" align = "center" cellpadding = "0" cellspacing = "0">
	<tr>
		<td align = "left" valign = "top">
			<table width="99%" border="0" cellspacing="0" cellpadding="0" style="background-repeat:repeat-x">
				
				<tr align="left">
					<td>
						<table border='0' cellspacing='0' width="100%">
							<tr>
								<% if (strads.equals("YES") || strads.equals("yes")) { %>
								<td height="150" width="950" align="center"  style="background-image:url('http://<%=domainname%>/bookbattery/userdata/ads/Default/Default.png?<%=context.getInitParameter("BookBatterydefaultversionnumber")%>'); background-repeat: no-repeat; border: #FFF 2px solid; background-position:center;">
									<div id="topads"></div>
								</td>
							</tr>
						</table>
						<%
						}
						else
						{
						%>
						
						<%
						}
						%>							
					</td>
				</tr>
			</table>
		</td>
	</tr>		
</table>
<input type="hidden" name="domainname" id='domainname' value="<%=domainname%>" />
<input type="hidden" name="publicUrl" id='publicUrl' value="<%=publicUrl%>" />
