<%-- 
Author(s)			: Sai Krishna Daddala.
Created on			: March 18, 2015.
Copyright Notice	: BookBattery Pvt.Ltd. Confidential.
Description			: Retailer Registration.
--%>
<%@page language="java" import="javax.servlet.ServletContext"%>
<%
ServletContext context = getServletContext();
String strUserid=(String)session.getAttribute("sesBatteryAdminName");
if(strUserid==null)
{
	strUserid="";
	session.setAttribute("sesErrorMsg","Session Timed Out. Please login again");
	response.sendRedirect("/bookbattery/admin/index.html");
	return;
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link type="text/css" href="/bookbattery/css/bookbattery.css" rel="stylesheet"/>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<script type="text/javascript" src="/bookbattery/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="/bookbattery/js/retailerreg.js"></script>
<link rel="shortcut icon" href="/bookbattery/images/favicon.ico" type="image/x-icon">
<h1><title>BookBattery.com-Online Battery Store</title></h1>
<style>
.lighterShadeappend{font-family:Tahoma;font-size:11px;color:#000000;text-decoration:none}
</style>
</head>
<body onload="onloaderetailerhide(); hideMoreFields(); noBack();" onkeydown="if($('#popup').is(':visible')){ if(event.keyCode==9){ return false;}};">
	<center>
		<form name="addretailer" id="thisform" method="post"  ENCTYPE="multipart/form-data">
			<div id='popup' class='popup' style="display:none;">
			<div id='popuptitle' class='popuptitle'>
			<table border='0' width='410px' height='10px' cellpadding='0' cellspacing='0'>
				<tr class='top1'>
				<td>&nbsp;<font color='#FFFFFF'>BookBattery</font></td><td align='right'><a href='javascript:closePopup(greyout(false));' 	                    		class="bluelinks333">x</a></td>
				</tr>
			</table>
			</div>
			<div id='popupmessage' class='popupmessage'></div> 
			</div>
			<table cellpadding="0" cellspacing="0" border="0" width="960">
				<tr>
					<td>
						<jsp:include page = "../header.jsp" />
					</td>
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
				<td width="680" align="left" valign="top">
				<table width="600" cellspacing="1" bgcolor="#BEADCB" cellpadding="0">
					<tr>
					<td>									
				<table width="100%" border="1" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">								
					<table width="100%" border="0" cellspacing="0" bgcolor="#FFFFFF" cellpadding="5px">
						<tr>
						<td height="10" bgcolor="#FFFFFF" align="center"><div id="displaysesretailermsg"></div></td>
						</tr>
						<tr>
						<td class="subheading" align="left"> <font size="2">Retailer Registration ::</td>
						<td  align="right"><a href="/bookbattery/servlet/AdminBatteryLogin?hidWhatToDo=batteryadminhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
						</tr>
						<tr>
						<td>
						<table width="100%" cellspacing="1" border="0" bgcolor="#FFFFFF" cellpadding="0">
						<tr>
						<td>
						<table width="100%" border="0" cellspacing="0" bgcolor="#FFFFFF" cellpadding="5" >
						<tr height="10">
						<td colspan="3" align="center"></td>
						</tr>
						<tr class="#FFFFFF" bgcolor="FFFFFF" style="display:none;">
						<td class="subheading" align="left">eRetailer<font color="FF0000"></font></td><td>:&nbsp;<input type="checkbox" name="eretailer" id="eretailer" onclick="javascript:eretailercheck()" size="31"></td>
						</tr>
						<tr bgcolor = "#FFFFFF" id="retstate">
						<td class = "subheading" align="left">State<font color = "#FF0000">*</font></td>
						<td>:&nbsp;<select  STYLE="width: 185px" name = "State" id = "State" onchange = "retailersetcities();" onchange="javascript:searchKeyPress11(event);"><jsp:include page = "state.jsp" /></select></td>
						<td class = "subheading" align="left">City<font color = "#FF0000">*</font>
						:&nbsp;<select STYLE="width: 185px"  name = "city" id = "city" onchange="javascript:checkcityretname();"><option value="0">&lt;-----select city-----&gt</option></select></td>
						</tr>
						<tr bgcolor = "#FFFFFF" id="eretstate">
						<td class = "subheading" align="left">State</td>
						<td>&nbsp;<select STYLE="width: 185px" name = "Stateeret" id = "Stateeret" onchange = "eretailersetcities();"><jsp:include page = "state.jsp" /></select></td>
						<td class = "subheading" align="left">City&nbsp;<select STYLE="width: 185px" name = "cityeret" id = "cityeret" onchange="javascript:searchKeyPress24(event);"><option value="0">&lt;-----select city-----&gt</option></select></td>
						</tr>
						<tr>
						<td class="subheading" width="22%">Retailer Name<font color="FF0000">*</font></td>
						<td width="38%">:&nbsp;<input class="lighterShade" type="text" name="retailername" maxlength="100" size="31" value="AmaronBatteries-NehruNagar-Hyd" id="inputTextboxId"  onchange="javascript:checkretailername();" onfocus="watermarkretailername('inputTextboxId','AmaronBatteries-NehruNagar-Hyd');" onblur="watermarkretailername('inputTextboxId','AmaronBatteries-NehruNagar-Hyd');"></td><td width="150"  class="insidecontent"><div id="divsesmsg"></div></td>
						</tr>
						
						<tr class="#FFFFFF" bgcolor="FFFFFF">
						<td class="subheading" >Retailer Login Name<font color="FF0000">*</font></td>
						<td >:&nbsp;<input class="lighterShade" type="text" name="retailerloginname" id="inputTextboxId14" maxlength="50" value="Amaronenterprises" size="31" onchange="javascript:checkretailerloginname();" onfocus="watermarkretloginname('inputTextboxId14','Amaronenterprises');" onblur="watermarkretloginname('inputTextboxId14','Amaronenterprises');"></td><td class="insidecontent"><div id="divsesmsgretlogin"></div></td>
						</tr>
						<tr class="#FFFFFF" bgcolor="FFFFFF">
						<td class="subheading" align="left" >Password<font color="FF0000">*</font></td>
						<td >:&nbsp;<input type="password" class="insidecontent" name="password" size="31"></td>
						</tr>
						<tr>
						<tr>
						<td class="subheading" >Confirm Password<font color="FF0000">*</font></td>
						<td >:&nbsp;<input  type="password" name="password1" class="insidecontent" size="31" ></td>
						</tr>
						<tr style="display:none;">
						<td class="subheading" >Show Timings</td>
						<td >:&nbsp;<input type="text" name="timings" class="insidecontent" size="31" ></td>
						</tr>
						<tr>
						<td class="subheading" >Email-id<font color="FF0000">*</font></td>
						<td colspan="2">:&nbsp;<input type="text" class="lighterShade" name="emailid"  size="31" value="amaronBookBattery@gmail.com" id="inputTextboxId1" onfocus="watermarkemailid('inputTextboxId1','amaronBookBattery@gmail.com');" onblur="watermarkemailid('inputTextboxId1','amaronBookBattery@gmail.com');"><a href="javascript:onclickemailid()" class="click1" title="if u have other Email Id">&nbsp;Add&nbsp;More..&nbsp;</a></td>
						</tr>
						<table  id="otherEmailId" width="100%" cellspacing="0" align="left" border="0">
							<tr>
							<td width="22%" align="left" valign="middle" class="subheading"> Email-id(others) </td>
							<td width="38%">&nbsp;:&nbsp;<input class = "insidecontent" type = "text" name = "emailidother" value="" size = "31"></td>
							<td class="insidecontent" width="40%">Multiple Emailid's can be given with a comma seperated</td>
							</tr>
						</table>
				
					</table>
					<tr>
					<td>
					<div id="retailerdiv">
					<table width="100%" cellspacing="2" cellpadding="5" border="0">
						<tr>
						<td>
						<table width="100%" cellspacing="2" cellpadding="5" border="0">
							<tr class="#FFFFFF" bgcolor="FFFFFF">
							<td class="subheading" align="left" width="21%">LandLine Number</td>
							<td width="79%">:&nbsp;<input type="text" class="lighterShade" name="phone_number" value="+918572-234545" id="inputTextboxId2"  size="33" onfocus="watermarklandline('inputTextboxId2','+918572-234545');" onblur="watermarklandline('inputTextboxId2','+918572-234545');"><a href="javascript:onclicklandlineretailer()" class="click1" title="if u have other Landline Number">&nbsp;Add&nbsp;More..&nbsp;</a></td>
							</tr>
						</table>
						<tr>
						<td>
						<div id="otherLandline">
						<table align="left" border="0"  width="100%">
							<tr>
							<td width="22%" class="subheading" align="left" >LandLine Number(others)</td>
							<td width="38%" cellspacing="0">:&nbsp;<input type="text" class="insidecontent" name="phone_numberother" value="" size="33"></td>
							<td class="insidecontent" width="40%">Multiple Landline Numbers can be given with comma seperated</td>
							</tr>
						</table>
						</div>
						</td>
						</tr>
						<tr>
						<td>
						<table width="100%" cellspacing="2" cellpadding="5" border="0">
							<tr><td></td><td><font>(OR)</font></td></tr>
							<tr class="#FFFFFF" bgcolor="FFFFFF">
							<td class="subheading" align="left" width="21%">Mobile Number</td>
							<td width="79%">:&nbsp;<input  type="text" name="mobappend" size="3" class="lighterShadeappend" value="+91" readonly><input type="text" class="lighterShade" name="mobile_number" maxlength="10" value="9002265577" id="inputTextboxId3"  size="28" onfocus="watermarkmobilenumber('inputTextboxId3','9002265577');" onblur="watermarkmobilenumber('inputTextboxId3','9002265577');"><a href="javascript:onclickMobile()" class="click1" title="if u have other Mobile Number">&nbsp;Add&nbsp;More..&nbsp;</a></td>
							</tr>
						</table>
						<tr>
						<td>
						<div id="otherMobile">
						<table align="left" border="0" width="100%">
							<tr>
							<td class="subheading" align="left" width="22%">Mobile Number(others)</td>
							<td width="38%" cellspacing="0">:&nbsp;<input type="text" class="insidecontent" name="mobile_numberother" value=""  size="33" ></td>
							<td class="insidecontent" width="40%">Multiple Mobile Numbers can be given with comma seperated</td>
							</tr>
						</table>
						</div>
						</td>
						</tr>
						<tr>
						<td>
						<table width="100%" cellspacing="2" cellpadding="5" border="0">
							<tr class="#FFFFFF" bgcolor="FFFFFF">
							<td class="subheading" align="left" width="21%">Tollfree Number</td>
							<td width="79%">:&nbsp;<input type="text" class="lighterShadeappend" name="tollfreenumber" value="" size="33"></td>
							</tr>
						</table>
						</td>
						</tr>
						<tr>
						<td>
						<table width="100%" cellspacing="2" cellpadding="5" border="0">
							<tr class="#FFFFFF" bgcolor="FFFFFF">
							<td class="subheading" >Address<font color="FF0000">*</font></td>
							<td class="details">&nbsp;<textarea class="lighterShade" name="retaileraddress" rows="4" id="inputTextboxId4"  cols="37" onfocus="watermarkaddress('inputTextboxId4','Near My Home Tycoon, NehruNagar');" onblur="watermarkaddress('inputTextboxId4','Near My Home Tycoon, NehruNagar');">Near My Home Tycoon, NehruNagar</textarea></td>
							<td class="subheading" >Web Address</td>
							<td class="details" colspan="2" valign="middle" >&nbsp;<textarea class="lighterShade" name="retaileraddress2" id="inputTextboxId5"  rows="4" cols="37" onfocus="watermarkurl('inputTextboxId5','www.BookBattery.com');" onblur="watermarkurl('inputTextboxId5','www.BookBattery.com');">www.BookBattery.com</textarea></td>
							</tr>
							<tr>
							<td class="insidecontent" align="right" valign="top" width="50%"></td>
							<td class="insidecontent" align="left" width="50%">
							<td><td><font class="insidecontent"  width="70%"></td></td></td>
							</tr>
							<tr class="#FFFFFF"  bgcolor="FFFFFF">
							<td class="subheading" >Area<font color = "#FF0000">*</font></td>
							<td class="details">&nbsp;<input class="lighterShade" type="text" name="area" maxlength="40" size="33" value="NehruNagar" id="inputTextboxId6"  onkeyup="javascript:areanamecheck()" onfocus="watermarkarea('inputTextboxId6','NehruNagar');" onblur="watermarkarea('inputTextboxId6','NehruNagar');"></td>
							<td class="subheading" >ZipCode</td>
							<td class="details">&nbsp;<input class="lighterShade" type="text" name="zipcode" value="500214" id="inputTextboxId7" maxlength="40" size="33"  onfocus="watermarkzipcode('inputTextboxId7','500214');" onblur="watermarkzipcode('inputTextboxId7','500214');"></td>
							</tr>
						</table>
						<tr bgcolor="FFFFFF">
						<td class="subheading" colspan="4">
						<table align="center" border="0" cellpadding="0" cellspacing="0">
							<tr height="20" id="retsubmitbutton">
							<td >&nbsp;&nbsp;&nbsp;<a href="javascript:onClickRetailerSubmit()" class="smallbutton">Submit</a>&nbsp;&nbsp;&nbsp;</td>
							<td >&nbsp;&nbsp;&nbsp;<a href="/bookbattery/jsp/admin/batterystore/retailers/addretailer.jsp" class="smallbutton">Reset</a>&nbsp;&nbsp;&nbsp;</td>
							</tr>
						</table>
						</td>
						</tr>
						</td>
						</tr>
						</td>
						</tr>
						</td>
						</tr>
					</table>
					</div>
					</td>
					</tr>
					<!--<tr>
					<td>
					<div id="eretailerdiv">
					<table width="100%">
						<tr>
						<td>
						<table width="100%" cellspacing="2" cellpadding="5" border="0">
							<td class="subheading" align="left" width="21%">LandLine Number</td>
							<td width="79%">:&nbsp;<input  class="lighterShade" type="text" name="phone_numbereret" value="+918572-234545" id="inputTextboxId8" size="33"   onfocus="watermarklandlineeret('inputTextboxId8','+918572-234545');" onblur="watermarklandlineeret('inputTextboxId8','+918572-234545');"><a href="javascript:onclicklandlineeretailer()" class="click1" title="if u have other Landline Number">&nbsp;Add&nbsp;More..&nbsp;</a></td>
						</table>
						</td>
						</tr>
						<tr>
						<td>
						<div id="ERetLandlinediv">
						<table align="left" border="0"  width="100%">
							<tr>
							<td class="subheading" align="left" width="22%">LandLine Number(others)</td>
							<td width="38%" cellspacing="0" >:&nbsp;<input class="insidecontent" type="text" name="phone_numberothereret" value=""  size="33"></td>
							<td class="insidecontent" width="40%">Multiple Landline Numbers can be given with comma seperated</td>
							</tr>
						</table>
						</div>
						</td>
						</tr>
						<tr>
						<td>
						<table width="100%" cellspacing="2" cellpadding="5" border="0">
						<tr><td></td><td><font>(OR)</font></td></tr>
							<tr>
							<td class="subheading" align="left" width="21%">Mobile Number</td>
							<td width="79%">:&nbsp;<input  type="text" name="mobappend" size="3" class="lighterShadeappend" value="+91" readonly><input class="lighterShade" type="text" name="mobile_numbereret" maxlength="10" value="9008867652" id="inputTextboxId9" size="28"  onfocus="watermarkmobileeret('inputTextboxId9','9008867652');" onblur="watermarkmobileeret('inputTextboxId9','9008867652');"><a href="javascript:onclickMobileeretailer()" class="click1" title="if u have other Mobile Number">&nbsp;Add&nbsp;More..&nbsp;</a></td>
							</tr>
						</table>
						</td>
						</tr>
						<tr>
						<td>
						<div id="ERetMobile">
							<table align="left" border="0"  width="100%">
							<tr>
							<td class="subheading" align="left" width="22%">Mobile Number(others)</td>
							<td width="38%" cellspacing="0">:&nbsp;<input class="insidecontent" type="text" name="mobile_numberothereret" value="" size="33"></td>
							<td class="insidecontent" width="40%">Multiple Mobile Numbers can be given with comma seperated</td>
							</tr>
						</table>
						</div>
						</td>
						</tr>
						<tr>
						<td>
						<table width="100%" cellspacing="2" cellpadding="5" border="0">
							<tr class="#FFFFFF" bgcolor="FFFFFF">
							<td class="subheading" align="left" width="21%">Tollfree Number</td>
							<td width="79%">:&nbsp;<input type="text" class="lighterShadeappend" name="etollfreenumber" value="" size="33"></td>
							</tr>
						</table>
						</td>
						</tr>
						<tr>
						<td>
						<table width="100%" cellspacing="2" cellpadding="5" border="0">
							<tr class="#FFFFFF" bgcolor="FFFFFF">
							<td class="subheading" >Address</td>
							<td class="details">&nbsp;<textarea  class="lighterShade" name="eretaddress1" id="inputTextboxId10" rows="4" cols="37"  onfocus="watermarkaddresseret('inputTextboxId10','Near My Home Tycoon, NehruNagar');" onblur="watermarkaddresseret('inputTextboxId10','Near My Home Tycoon, NehruNagar');">Near My Home Tycoon, NehruNagar</textarea></td>
							<td class="subheading" >Web Address<font color="FF0000">*</font></td>
							<td class="details" colspan="2" valign="middle" >&nbsp;<textarea class="lighterShade" name="eretaddress2" id="inputTextboxId11" rows="4" cols="37" onfocus="watermarkereturl('inputTextboxId11','www.amaronBookBattery.com');" onblur="watermarkereturl('inputTextboxId11','www.amaronBookBattery.com');">www.amaronBookBattery.com</textarea></td>
							</tr>
							<tr>
							<td class="insidecontent" align="right" valign="top" width="50%"></td>
							<td class="insidecontent" align="left" width="50%">
							<td><td><font class="insidecontent"  width="70%"></td></td></td>
							</tr>
							<tr class="#FFFFFF"  bgcolor="#FFFFFF">
							<td class="subheading" >Area</td>
							<td class="details">&nbsp;<input class="lighterShade" type="text" name="areaeret" maxlength="40" size="33" value="NehruNagar" id="inputTextboxId12"  onkeyup="javascript:areanamecheckeretailer()" onfocus="watermarkeretarea('inputTextboxId12','NehruNagar');" onblur="watermarkeretarea('inputTextboxId12','NehruNagar');"></td>
							<td class="subheading" >ZipCode</td>
							<td class="details">&nbsp;<input class="lighterShade" type="text" name="zipcodeeret" maxlength="40" value="500214" id="inputTextboxId13" size="33"  onfocus="watermarkeretzipcode('inputTextboxId13','500214');" onblur="watermarkeretzipcode('inputTextboxId13','500214');"></td>
							</tr>
						</table>
						</td>
						</tr>
						<tr bgcolor="#FFFFFF">
						<td class="subheading" colspan="4">
						<table align="center" border="0" cellpadding="0" cellspacing="0">
							<tr height="20" id="eretsubmitbutton">
							<td >&nbsp;&nbsp;&nbsp;<a href="javascript:onClickERetailerSubmit()" class="smallbutton">Submit</a>&nbsp;&nbsp;&nbsp;</td>
							<td >&nbsp;&nbsp;&nbsp;<a href="/bookbattery/jsp/admin/batterystore/retailers/addretailer.jsp" class="smallbutton">Reset</a>&nbsp;&nbsp;&nbsp;</td>
							</tr>
						</table>
						</td>
						</tr>
					</table>
					</div>
					</td>
					</tr>
					</td>
					</tr>-->
				</table>
				</td>
				</tr>
				</table>
				</td>				
				</tr>
				</table>
				</td>
				<td valign="top">
					<table width="50" border="0" valign="top" align="right" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" >
						<td bgcolor="#FFFFFF" valign="top" align="left" width="100"><div id="Checkretailernamediv"></div></td>
						<td bgcolor="#FFFFFF" valign="top" align="left" width="100"><div id="Checkareanamediv"></div></td>
					</table>
				</td>
				</tr>
				</table>
				</td>
				</tr>
					<tr>
					<td align="left" valign="top" width="18" height="18"></td>
					</tr>
				</table>
				<input type="hidden" name="hidWhatToDo" value="add">
			<!-- Footer Starts Here -->
			<tr>
				<td>
					<jsp:include page = "../footer.jsp" />
				</td>
			</tr>                           
			<!-- footer Ends Here -->
		</form>
	</center>
</body>
<script type="text/javascript">
	(function() {
	var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
	po.src = '/bookbattery/js/pophide.js';
	var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
	})();
	(function() {
	var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
	po.src = '/bookbattery/js/jquery-1.3.1.min.js';
	var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
	})();
</script>
<%
String strsuprLogMsg=(String)session.getAttribute("sesretErrorMsg");
if(strsuprLogMsg ==null)
{
	strsuprLogMsg="";
}
else
{
%>
<script type="text/javascript">
//<![CDATA[
var sesmessg; 
sesmessg= "<%=strsuprLogMsg%>";
document.getElementById("displaysesretailermsg").innerHTML=sesmessg;
//]]>
</script>
<%
	session.removeAttribute("sesretErrorMsg");
}
%>
</html>

