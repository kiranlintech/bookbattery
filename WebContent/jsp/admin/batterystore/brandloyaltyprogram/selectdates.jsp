<%-- 
Document   : select Category.jsp.
Created on : Mar 18, 2012, 12:33:12 PM
Author     : Jhansi A
--%>

<!@page language="java">

<%@page language="java" import="java.util.Vector,java.util.Hashtable,com.ngit.javabean.loglevel.*,java.util.Enumeration,java.util.Date"%>
<%
//get sesAdminName(username) attribute from session and pass the name of attribute
String strUserid=(String)session.getAttribute("sesBatteryAdminName");
if(strUserid==null)
{
	strUserid="";
	session.setAttribute("sesErrorMsg","Session Timed Out. Please login again");
	response.sendRedirect("../../../../admin/index.html");
	return;
}
String strLogMsg=(String)session.getAttribute("sesErrorMsg");
if(strLogMsg==null)
{
strLogMsg="";
}
else
{

//Removing the attribute from session
session.removeAttribute("sesErrorMsg");
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../../images/favicon.png" type="image/x-icon">
<title>BookBattery.com - India's No.1 Automobile Battery Store</title>
<script language=javascript src="../../../../js/datepicker.js"></script>
<script language=javascript src="../../../../js/jquery-1.3.2.min.js"></script>
<script src="../../../../js/pophide.js"></script>
<script language="javascript">
function close_popupl()
{
	$('#popup').hide();
	greyout(false);
	document.misoptions.txtFromDate.focus();
}
function close_popup2()
{
	$('#popup').hide();
	greyout(false);
	document.misoptions.txtToDate.focus();
}
function close_popupl2()
{
	$('#popup').hide();
	greyout(false);
}

function funClickSubmit()
{

	var varRadioGroup = document.forms["misoptions"].rdbWhatSelected;
	var fdate=document.misoptions.txtFromDate.value;
	var tdate=document.misoptions.txtToDate.value;
	var dvalue;
	var mySplitResult = fdate.split("-");
	var fday=mySplitResult[0];
	var fmonth=mySplitResult[1];
	var fyear=mySplitResult[2];
	var mySplitResult1 = tdate.split("-");
	var tday=mySplitResult1[0];
	var tmonth=mySplitResult1[1];
	var tyear=mySplitResult1[2];


	date = new Date();
	var month = date.getMonth()+1;
	var day = date.getDate();
	var year = date.getFullYear();


	for (i=0;i<varRadioGroup.length;i++)
	{
		if(varRadioGroup[i].checked)
		{
			dvalue=varRadioGroup[i].value;
			if(dvalue=="current" || dvalue=="candpmonth" ||dvalue=="sixmonths" )
			{
				if(dvalue == "current")
				{
					var month = "currentmonth";
				}
				if(dvalue == "candpmonth")
				{
					var month = "previousmonth";
				}
				if(dvalue == "sixmonths")
				{
					var month = "sixmonths";
				}
				if(fdate != "" || tdate !="")
				{
					errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>you have selected another option "+month+" to continue please click on ok button</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popupl2();' class='button4'><br></td></tr>";
					greyout(true);
					document.getElementById("popup").style.display='block';
					document.getElementById("popupmessage").innerHTML=errMsg
					document.misoptions.txtFromDate.value="";
					document.misoptions.txtToDate.value="";
					return;
				}
			}
			else
			{
				if(dvalue=="selected")
				{
					if(fdate == "")
					{
						//alert("Please Enter FromDate 'For Eg: 27-04-2009'");
						errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please Enter From Date 'For Eg: 27-04-2009'.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popupl();' class='button4'><br></td></tr>";
						greyout(true);
						document.getElementById("popup").style.display='block';
						document.getElementById("popupmessage").innerHTML=errMsg
						document.getElementById(datePickerDivID).style.visibility = "hidden";
						return;
					}
					if(tdate == "")
					{
						//alert("Please Enter ToDate 'For Eg: 27-04-2009'");
						errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please Enter To Date 'For Eg: 27-04-2009'.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup2();' class='button4'><br></td></tr>";
						greyout(true);
						document.getElementById("popup").style.display='block';
						document.getElementById("popupmessage").innerHTML=errMsg
						document.getElementById(datePickerDivID).style.visibility = "hidden";
						return;
					}
					if(tyear<fyear)
					{
						//alert("Selected  \"To\" Date Should Be Greater Than \"From\" Date.");
						errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Selected  \"To\" Date Should Be Greater Than \"From\" Date.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup2();' class='button4'><br></td></tr>";
						greyout(true);
						document.getElementById("popup").style.display='block';
						document.getElementById("popupmessage").innerHTML=errMsg
						return;
					}
					if(tyear==fyear)
					{
						if(tmonth<fmonth)
						{
							//alert("Selected  \"To\" Date Should Be Greater Than \"From\" Date.");
							errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Selected  \"To\" Date Should Be Greater Than \"From\" Date.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup2();' class='button4'><br></td></tr>";
							greyout(true);
							document.getElementById("popup").style.display='block';
							document.getElementById("popupmessage").innerHTML=errMsg
							return;
						}
					}
					if(tyear==fyear && tmonth==fmonth)
					{
						if(tday<fday)
						{
							
							//alert("Selected  \"To\" Date Should Be Greater Than \"From\" Date.");
							errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Selected  \"To\" Date Should Be Greater Than \"From\" Date.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popup2();' class='button4'><br></td></tr>";
							greyout(true);
							document.getElementById("popup").style.display='block';
							document.getElementById("popupmessage").innerHTML=errMsg
							return;
						}
					}

					if(tyear>year || fyear>year)
					{
						errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please check the selected date.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popupl();' class='button4'><br></td></tr>";
						greyout(true);
						document.getElementById("popup").style.display='block';
						document.getElementById("popupmessage").innerHTML=errMsg
						return;
					}
					if(tyear==year)
					{
						if(tmonth>month)
						{	
							
							errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please check the selected date.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popupl();' class='button4'><br></td></tr>";
							greyout(true);
							document.getElementById("popup").style.display='block';
							document.getElementById("popupmessage").innerHTML=errMsg
							return;
						}
					}
					if(fyear ==year)
					{
						if(fmonth>month)
						{	
							
							errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please check the selected date.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popupl();' class='button4'><br></td></tr>";
							greyout(true);
							document.getElementById("popup").style.display='block';
							document.getElementById("popupmessage").innerHTML=errMsg
							return;
						}
					}
					if(fyear==year)
					{
						if(fmonth==month)
						{
							if(fday>day)
							{	
								
								errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please check the selected date.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popupl();' class='button4'><br></td></tr>";
								greyout(true);
								document.getElementById("popup").style.display='block';
								document.getElementById("popupmessage").innerHTML=errMsg
								return;
							}
						}
					}
					if(tyear ==year )
					{
						if(tmonth==month)
						{
							if(tday>day)
							{	
								
								errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='top1'><td align='center'>Please check the selected date.</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popupl();' class='button4'><br></td></tr>";
								greyout(true);
								document.getElementById("popup").style.display='block';
								document.getElementById("popupmessage").innerHTML=errMsg
								return;
							}
						}
					}
				}
			}
		}
	}
	document.misoptions.method="Post";
	document.misoptions.action="../../../../servlet/BrandLoyalty?hidWhatToDo=recharge&dates="+dvalue+"&txtFromDate="+fdate+"&txtToDate="+tdate;
	document.misoptions.submit();
}

function onClickLink(varRadioGroup)
{
	var fdate=document.misoptions.txtFromDate.value;
	var tdate=document.misoptions.txtToDate.value;
	var dvalue;
	document.misoptions.txtFromDate.value="";
	document.misoptions.txtToDate.value="";

	document.misoptions.method="Post";
	document.misoptions.action="../../../../servlet/BrandLoyalty?hidWhatToDo=recharge&dates="+varRadioGroup+"&txtFromDate="+fdate+"&txtToDate="+tdate;
	document.misoptions.submit();
}
function addDate()
{
	date = new Date();
	var month = date.getMonth()+1;
	var day = date.getDate();
	var year = date.getFullYear();

	if (document.getElementById('datetext').value == '')
	{
		document.getElementById('datetext').value = year + '-' + month + '-' + day;
	}
}
function Checkreset()
{
	document.misoptions.txtFromDate.value="";
	document.misoptions.txtToDate.value="";
	document.getElementById(datePickerDivID).style.visibility = "hidden";
}
</script>
<link href="../../../../css/bookbattery.css" rel="stylesheet" type="text/css" />
</head>
<body onload="addDate()" onkeydown="if($('#popup').is(':visible')){ if(event.keyCode==9){ return false;}};">
<center>
<form name="misoptions" method="post" >
<div id='popup' class='popup' style="display:none;">
	<div id='popuptitle' class='popuptitle'><table border='0' width='410px' height='10px' cellpadding='0' cellspacing='0'>
              <tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'>
                	<a href='javascript:closePopup(greyout(false));' class="bluelinks333">x</a></td></tr></table>
	</div>
	<div id='popupmessage' class='popupmessage'></div> 
</div>
<table cellpadding="0" cellspacing="0" border="0" width="960">
<tr>
<td>
<!-- top page starts here  -->
<jsp:include page = "../header.jsp" />
<!-- top page ends here  -->
</td>
</tr>
<tr>
<td align="left" valign="top" bgcolor="#FFFFFF">
<table width="960" border="0" cellspacing="0" cellpadding="0">
<tr>
<td width="180" height="438" align="left" valign="top">
<table width="180" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="180" height="438" align="left" valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td>
					<!-- categories starts here  -->
					<jsp:include page="../batteryadminleftmenu.jsp"/>
					<!-- categories ends here  -->
				</td>
			</tr>
		</table>
	</td>
		<td width="680" align="left" valign="top" >
			<table width="513" border="0" cellspacing="0" cellpadding="0">
				<!-- Inner content should be within the below table -->
				<table width="770" border="0" cellpadding="5" cellspacing="0" bgcolor="#FFFFFF">
				<tr>
					<td>
						<table width="713" cellspacing="1" cellpadding="0">
						<tr>
							<td class="subheading" size="2">Redeem Loyalty Points >> Select Date</td>
						</tr>
					<tr>
					<td>
						<table width="100%" cellspacing="1" bgcolor="#8B8D90" cellpadding="0">
							<tr>
								<td  bgcolor="#BEADCB" align="center" class="subheading"><font color="#000000">Select Date</font></td>
							</tr>
							<tr>
								<td bgcolor="#FFFFFF" align="center">
									<table width="50%" align="center" cellspacing="0" bgcolor="#FFFFFF" cellpadding="5">
										<tr>
											<td colspan="2" height="20">&nbsp;</td>
										</tr>
										<tr>
											<td class="table1" align="right" width="50%"><input type="radio" name="rdbWhatSelected" value="current" checked onChange="Checkreset()"></td>
											<td class="table1" align="left" width="50%"><a href="javascript:onClickLink('current')" class="insidecontent"  style="text-decoration:none">Current Month</a></td>
										</tr>
										<tr>
											<td class="table2" align="right" width="50%"><input type="radio" name="rdbWhatSelected" value="candpmonth" checked onChange="Checkreset()"></td>
											<td class="table2" align="left" width="50%"><a href="javascript:onClickLink('candpmonth')" class="insidecontent"  style="text-decoration:none">From Previous Month</a></td>
										</tr>
										<tr>
											<td class="table1" align="right" width="50%"><input type="radio" name="rdbWhatSelected" value="sixmonths" checked onChange="Checkreset()"></td>
											<td class="table1" align="left" width="50%"><a href="javascript:onClickLink('sixmonths')" class="insidecontent"  style="text-decoration:none">Past Six Months</a></td>
										</tr>
										<tr>
											<td class="table2" align="right" width="50%"><input type="radio" name="rdbWhatSelected" value="selected" checked ></td>
											<td class="table2" align="left" width="50%">Specify a Date Range</td>
										</tr>
										<tr>
											<td class="table1" align="right" width="30%" class="insidecontent">From Date</td>	 
											<td class="table1" >&nbsp;:&nbsp;&nbsp;<input type="text" name="txtFromDate" readonly  onChange="CheckDate(this)" onKeyDown="FormatDate(this,  window.event.keyCode,'down')" onKeyUp="FormatDate(this, window.event.keyCode,'up')" value="" size="10" maxlength="10"  >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="../../../../images/calender.jpg" style="cursor:hand" onclick="displayDatePicker('txtFromDate', this);"></td> 
										</tr>
										<tr>
											<td class="table2" align="right" width="30%" class="insidecontent">To Date</td> 
											<td class="table2" >&nbsp;:&nbsp;&nbsp;<input type="text"  name="txtToDate" readonly  onChange="CheckDate(this)" onKeyDown="FormatDate(this,  window.event.keyCode,'down')" onKeyUp="FormatDate(this, window.event.keyCode,'up')" value="" size="10" maxlength="10"  >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="../../../../images/calender.jpg" style="cursor:hand" onclick="displayDatePicker('txtToDate', this);"></td>
										</tr>
										<tr>
											<td align="center" colspan="9">
												<table border="0" align="center">
													<td>
														<tr>
															<td align="center"><a href="javascript:funClickSubmit();" class="smallbutton" id="btnSearch">Submit</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="../../../../jsp/admin/batterystore/brandloyaltyprogram/brandloyaltyselection.jsp" class="smallbutton">&nbsp;Back&nbsp;</a>
															</td>
														</tr>
														<tr>
															<td>&nbsp;</td>
														</tr>
													</td>
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
<!-- Inner content ends here -->
</table>
</td>
</tr>
</table>
</td>
</tr>
</table>
</td>
</tr>
<tr>
<td>
<!-- footer page starts here  -->
<jsp:include page = "../footer.jsp" />
<!-- top page ends here  -->
</td>
</tr>
</table>
<input type="hidden" name="dates" value="selected">
</form>
</center>
</body>
</html>
