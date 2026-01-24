<%-- 
Author(s)		: Chandra Prakash G.
Created on		: July 09, 2013.
Copyright Notice	: BookBattery Pvt.Ltd. Confidential
Description		: Retailer Registration.
--%>
<%@page language="java" import="java.io.File,java.util.Properties,java.io.FileInputStream,javax.servlet.ServletContext"%>
<%
ServletContext context = getServletContext();
String strUserid=(String)session.getAttribute("sesBatteryAdminName");
if(strUserid==null)
{
	strUserid="";
	session.setAttribute("sesErrorMsg","Session Timed Out. Please login again");
	response.sendRedirect("../../../../admin/index.html");
	return;
}
	String retailername = (request.getParameter("retailername")!=null)?(request.getParameter("retailername")):"";
	String retailerid = (request.getParameter("retailerid")!=null)?(request.getParameter("retailerid")):"";
	String strcity = (request.getParameter("city")!=null)?(request.getParameter("city")):"";
	String strphnum = (request.getParameter("phone_number")!=null)?(request.getParameter("phone_number")):"";
	String strmbnum = (request.getParameter("mobile_number")!=null)?(request.getParameter("mobile_number")):"";
	String straddr2 = (request.getParameter("website")!=null)?(request.getParameter("website")):"";
	String strstate = (request.getParameter("state")!=null)?(request.getParameter("state")):"";
	String strzipcode = (request.getParameter("zipcode")!=null)?(request.getParameter("zipcode")):"";
	String stremail= (request.getParameter("emailid")!=null)?(request.getParameter("emailid")):"";
	String strretailerloginname = (request.getParameter("retailerloginname")!=null)?(request.getParameter("retailerloginname")):"";
	String strarea= (request.getParameter("area")!=null)?(request.getParameter("area")):"";
	String eretailer_flag = (request.getParameter("eretailer_flag")!=null)?(request.getParameter("eretailer_flag")):"";
	String straddr1 = (request.getParameter("address")!=null)?(request.getParameter("address")):"";
	String temp = (request.getParameter("temp")!=null)?(request.getParameter("temp")):"";
	String key = (request.getParameter("key")!=null)?(request.getParameter("key")):"";
		String stateold = (request.getParameter("stateold")!=null)?(request.getParameter("stateold")):"";
	Properties propsMOPConfig = new Properties();
	FileInputStream fin1      = new FileInputStream(new File(context.getRealPath("properties/bookbatteryconfig.properties"))); 
	propsMOPConfig.load(fin1); 
	fin1.close(); 
	String passwordretailerMsg = (propsMOPConfig.getProperty("passwordretailer")!=null)?propsMOPConfig.getProperty("passwordretailer"):"";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link type="text/css" href="../../../../css/bookbattery.css" rel="stylesheet"/><head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../../images/favicon.ico" type="image/x-icon">
<h1><title>BookBattery.com-Online Battery Store</title></h1>
<script src="../../../../js/jquery-1.3.2.min.js"></script>
</head>
<body onload="getCategories(); noBack();" onkeydown="if(($('#popup').is(':visible')) || ($('#popuph').is(':visible'))){ if(event.keyCode==9){ return false;}};">
<center>
<form name="addretailer" method="post"  ENCTYPE="multipart/form-data" >
<div id='popup' class='popup' style="display:none;">
<div id='popuptitle' class='popuptitle'>
<table border='0' width='410px' height='10px' cellpadding='0' cellspacing='0'>
	<tr class='top1'>
	<td>&nbsp;<font color='#FFFFFF'>BookBattery</font></td><td align='right'><a href='javascript:closePopup(greyout(false));' class="bluelinks333">x</a></td>
	</tr>
</table>
</div>
<div id='popupmessage' class='popupmessage'></div> 
</div>
<div id='popuph' class='popuph' style="display:none;">
<div id='popuptitle' class='popuptitle'>
<table border='0' width='410px' height='10px' cellpadding='0' cellspacing='0'>
	<tr class='top1'>
	<td>&nbsp;<font color='#FFFFFF'>BookBattery</font></td><td align='right'><a href='javascript:closePopupsh(greyout(false));' class="bluelinks333"></a></td>
	</tr>
</table>
</div>
<div id='popupmessageh' class='popupmessageh'></div> 
</div>
<div id='sessionpopup' class='popup' style="display:none;">
<div id='sessionpopuptitle' class='popuptitle'>
<table border='0' width='410px' height='10px' cellpadding='0' cellspacing='0'>
	<tr class='top1'>
	<td>&nbsp;<font color='#FFFFFF'>BookBattery</font></td>
	</tr>
</table>
</div>
<div id='sessionpopupmessage' class='popupmessage'></div> 
</div>
<div id='popupreply1' class='popup' style="display:none;">
<div id='popuptitle' class='popuptitle'><table border='0' width='410px' height='1px' cellpadding='0' cellspacing='0'>
<tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closePopupreport(greyout(false));' class="bluelinks333">x</a></td></tr></table>
</div>
<div id='popupmessagereply1' class='popupmessage'></div> 
</div>
<table cellpadding="0" cellspacing="0" border="0" width="960">
<tr>
	<td>
	<jsp:include page = "../header.jsp" />
	</td>
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
			<jsp:include page="../batteryadminleftmenu.jsp"/>
			</tr>
		</table>                
		</td>
		<td width="680" align="left" valign="top" >
		<table width="650" cellspacing="1" bgcolor="#BEADCB" cellpadding="0">
			<tr>
			<td>
			<table width="100%" cellspacing="1" border="0" bgcolor="#FFFFFF" cellpadding="0">
				<tr>
				<td>
				<table width="100%" cellspacing="0" bgcolor="#FFFFFF" cellpadding="5px">
					<tr>
						<td height="10" bgcolor="#FFFFFF"></td>
					</tr>
					<tr>
						<td class="subheading"> <font size="2">Retailer subcatdetails</td>
					</tr>
					<tr>
					<td>
					<table width="80%" cellspacing="0" border="0" bgcolor="#FFFFFF" cellpadding="5" >
						<tr height="10">
						<td colspan="3" align="center"></td>
						</tr>
						<tr height="10"></tr>
						</table>
						<table width="80%" cellspacing="0" border="0" bgcolor="#FFFFFF" cellpadding="5" border="00">
							<tr class="#FFFFFF">
								<td class="insidecontent" align="right">Category<font color="FF0000">*</font></td>
								<td class="details" align="left">
								<select width="100" STYLE="width: 100px" name="category" onChange = "javascript: getSubCategories(); ">
								<option value="" >&lt;---Select---&gt</option>
								</select>
								</td>
								<td class="insidecontent" align="right">SubCategory<font color="FF0000">*</font></td>
								<td class="details" align="left">
								<select  multiple="multiple"  style="height: 128px; width: 250px;" name="subcategory" onChange = "javascript: getBrands(); ">
								<option value="" >&lt;---Select---&gt</option>
								</select>
								</td>
								<td class="insidecontent" align="right">Brand</td>
								<td class="details" align="left">
								<select  multiple="multiple" width="250" style="height: 128px; width: 300px;" name="brand"  >
								<option value="" >&lt;-------------------Select------------------------&gt</option>
								</select>
								</td>
							</tr>
						</table>
						<table width="100%" cellspacing="0" border="0" bgcolor="#FFFFFF" cellpadding="5" border="00">
							<tr>
							<td class="insidecontent" align="right">*&nbsp;To select more than one Brand Press Ctrl Key and  Select Brands</td>
							</tr>
						</table>
						<tr bgcolor="FFFFFF">
						<td class="subheading" colspan="4">
						<table align="center" border="0" cellpadding="0" cellspacing="0">
							<tr height="20">
							<td >&nbsp;&nbsp;&nbsp;<a href="javascript:insertSubcategorybranddetails()" class="smallbutton">Submit</a>&nbsp;&nbsp;&nbsp;</td>
							<td >&nbsp;&nbsp;&nbsp;<a href="javascript:categorydetailsreset()" class="smallbutton">Reset</a>&nbsp;&nbsp;&nbsp;</td>								
							</tr>
						</table>
						<%
						if(key.equals("addretailer"))
						{%>
						<tr><td class="insidecontent" align="center">Please select atleast one category</td></tr>

						<%}
						%>
						</td>
						</tr>
						</td>
						</tr>
					</table>
					<tr>
					<td>
					<div id="SubcatDetails"></div>
					</td>
					</tr>
					</td>
					</tr>
					<tr>
					<td align="left" valign="top" width="18" height="18"></td>
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
</table>

<input type="hidden" name="hidWhatToDo" value="add">
<input type="hidden" name="retailername" value="<%=retailername%>">
<input type="hidden" name="retailerid" value="<%=retailerid%>">
<input type="hidden" name="city" value="<%=strcity%>">
<input type="hidden" name="strretailerloginname" value="<%=strretailerloginname%>">
<input type="hidden" name="strphnum" value="<%=strphnum%>">
<input type="hidden" name="strmbnum" value="<%=strmbnum%>">
<input type="hidden" name="straddr1" value="<%=straddr1%>">
<input type="hidden" name="straddr2" value="<%=straddr2%>">
<input type="hidden" name="strstate" value="<%=strstate%>">
<input type="hidden" name="strzipcode" value="<%=strzipcode%>">
<input type="hidden" name="stremail" value="<%=stremail%>">
<input type="hidden" name="strarea" value="<%=strarea%>">
<input type="hidden" name="eretailer_flag" value="<%=eretailer_flag%>">
<input type="hidden" name="temp" value="<%=temp%>">
<input type="hidden" name="strUserid" value="<%=strUserid%>">
<input type="hidden" name="key" value="<%=key%>">
<input type="hidden" name="stateold" value="<%=stateold%>">
<input type="hidden" name="passwordretailerMsg" id='passwordretailerMsg' value="<%=passwordretailerMsg%>">
</form>
</center>
<script type="text/javascript" src="../../../../js/retailersubcat.js"></script>
</body>
<script type="text/javascript">
(function() {
var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
po.src = '../../../../js/pophide.js';
var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
})();
</script>
</html>
