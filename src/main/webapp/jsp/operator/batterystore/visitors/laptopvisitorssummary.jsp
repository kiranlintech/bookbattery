<%--
    Document   : Laptop Visitors Summary
    Created on : April 26, 2014, 4:22:12 PM
    Author     : Sai Krishna Daddala
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.LogLevel"%>
<%
String strUserid=(String)session.getAttribute("sesBatteryOperatorName");
if(strUserid==null)
{
	strUserid="";
	session.setAttribute("sesErrorMsg","Session Timed Out. Please login again");
	response.sendRedirect("../../../../operator/index.html");
	return;
}
Vector alist=(Vector)session.getAttribute("sesBookBatteryvisitorsVectorVector"); 
LogLevel.DEBUG(1,new Throwable(),"alist: "+alist);

Vector alist1=(Vector)session.getAttribute("sesvisitorcountVector"); 
LogLevel.DEBUG(1,new Throwable(),"alist1: "+alist1);

int j=0;
Hashtable htVisitCount=(Hashtable)alist1.get(j);
String visitorscount=String.valueOf(htVisitCount.get("visitors"));

Hashtable htOptions=(Hashtable)session.getAttribute("sesOptions");
//String orderedstatus=(String)htOptions.get("orderstatus"); 
String FromDate=(String)htOptions.get("txtFromDate");
String ToDate=(String)htOptions.get("txtToDate");

String strDateOpt=request.getParameter("dates");
if(strDateOpt==null || strDateOpt.equals(""))
	strDateOpt="current";

String strFromDate=(request.getParameter("txtFromDate")!=null)?request.getParameter("txtFromDate"):"";
String strToDate=(request.getParameter("txtToDate")!=null)?request.getParameter("txtToDate"):"";
String type=(request.getParameter("type")!=null)?request.getParameter("type"):"";

String statusfilter1=(request.getParameter("statusfilter")!=null)?request.getParameter("statusfilter"):"";
if(statusfilter1==null || statusfilter1.equals(""))
	statusfilter1="Select Status";
LogLevel.DEBUG(1,new Throwable(),"statusfilter1: "+statusfilter1);

// Keep all the headings in session
Vector vectHeadervisitors = new Vector();
vectHeadervisitors.add("Bat Type"); 
vectHeadervisitors.add("Veh Make");
vectHeadervisitors.add("Veh Model");
vectHeadervisitors.add("Bat Brand");
vectHeadervisitors.add("Bat Capacity");
vectHeadervisitors.add("Mob Number");
vectHeadervisitors.add("Visitor Status");
vectHeadervisitors.add("Visitor Reason");
vectHeadervisitors.add("Agent Comments");
vectHeadervisitors.add("State");
vectHeadervisitors.add("City");
vectHeadervisitors.add("Area");
vectHeadervisitors.add("Pincode");
vectHeadervisitors.add("Date");
vectHeadervisitors.add("Agent Name");

session.removeAttribute("sesHeaderwithcat");
session.setAttribute("sesHeaderwithcat",vectHeadervisitors);


// put the body data in session
session.removeAttribute("sesBodywithcat");
session.setAttribute("sesBodywithcat",alist);

// Put the tile in session
session.removeAttribute("sesTitlewithcat");
session.setAttribute("sesTitlewithcat",visitorscount);
session.setAttribute("sesFromDate",FromDate);
session.setAttribute("sesToDate",ToDate);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../../images/favicon.png" type="image/x-icon">
<title>BookBattery.com-India's No.1 Automobile Battery Store</title>

<script language="JavaScript" src="../../../../js/jquery-1.8.2.js" ></script>
<script src="../../../../js/jquery-1.3.2.min.js"></script>
<script language=javascript src="../../../../js/datepicker.js"></script>
<link href="../../../../css/bookbattery.css" rel="stylesheet" type="text/css" />

<style type='text/css'>

body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	/*background-image: url(../../../../images/index_01_01.gif);
	background-repeat: repeat-x;*/
}
.divpostponed{left:57%;top:30%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left; border:6px solid #424242; border-radius: 6px 6px 6px 6px;position:absolute;padding: 1px;display: none;z-index:0;}

.divpostponed1{left:50.5%;top:30%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left; border:6px solid #424242; border-radius: 6px 6px 6px 6px;position:absolute;padding: 1px;display: none;z-index:0;}

.divmobile{left:35.5%;top:30%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left; border:6px solid #424242; border-radius: 6px 6px 6px 6px;position:absolute;padding: 1px;display: none;z-index:60;background-color: white;}
</style>


<script language="javascript">

$(document).ready(function()
{
//$(".messages").animate({ scrollTop: $(document).height() }, "slow");
 //return true;
//document.misbatstatus.focusable.focus();
$('#div1').scrollTop($('#div1')[0].scrollHeight);
});

function onClickExportToExcel()
	{
		strUrl = "../../../../servlet/BatteryExcelandPdfServlet?hidWhatToDo=ExportToExcelLaptopvisitors";
		window.open(strUrl,"ExcelOpen","height=450,width=530,toobar=0,location=0,directories=0,status=yes,menubar=0,scrollbars=yes,resizable=1"); 
	}
	function onClickExportToPdf()
	{
		strUrl = "../../../../servlet/BatteryExcelandPdfServlet?hidWhatToDo=ExportToPDFLaptopvisitors";
		window.open(strUrl,"ExcelOpen","height=450,width=530,toobar=0,location=0,directories=0,status=yes,menubar=0,scrollbars=yes,resizable=1"); 
	}
/** Function to change visitor status **/
function Updatevisitorstatus(visitorid)
{
	
	var orderedstatus = document.getElementById(visitorid).value;
	

	if(orderedstatus=="Customer Contacted")
	{
		CustomerContacted(visitorid,orderedstatus)
	}
	else if(orderedstatus=="Customer Not Contacted")
	{
		CustomernotContacted(visitorid,orderedstatus)
	}
	else if(orderedstatus=="Postponed")
	{
		Postponedvis(visitorid,orderedstatus)
	}
	else if(orderedstatus=="Repeated")
	{
		Repeatedvis(visitorid,orderedstatus)
	}
	
	else if(orderedstatus=="")
	{
	}
	else
	{
		var xmlhttp= "";
		var resp= "";
		var url ="../../../../servlet/BookBatteryVisitors?hidWhatToDo=updatevisitorstatus&chkSi="+visitorid+"&visitorstatus="+orderedstatus;
		if (window.XMLHttpRequest)
		{
			// code for IE7+, Firefox, Chrome, Opera, Safari
			 xmlhttp=new XMLHttpRequest();
		}
		else
		{
			// code for IE6, IE5
			xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange=function()
		{
			if (xmlhttp.readyState==4)
			{
				if(xmlhttp.status!=200)
				{
					alert("Error Occured. Please try again.");
					return;
				}
				else 
				{
					resp = xmlhttp.responseText;
					errMsg ="<table border='0' width='300px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closemobdiv();'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='300px' height='2px' bgcolor='#FFFFFF' valign'top'><tr bgcolor='#FFFFFF'><td align='justify' bgcolor='#FFFFFF' style='font-family:Verdana;font-size:11px;color:#000000;'>"+resp+"</td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='getupdatedvisitors();' class='button4'><br></td></tr><tr height='15'></tr></table>";
					document.getElementById("divpostponed").innerHTML=""; 
					document.getElementById("divpostponed").style.display='block';
					document.getElementById("divpostponed").innerHTML=errMsg
					
				}
			}			
		}
		var agree=confirm("Are You sure want to update the Visitor Status! ");
		if (agree)
		{
			xmlhttp.open("GET",url, true);		
			xmlhttp.send();	
		}
		
	}

}

/** Function to show options to select if agent contacts customer **/

function CustomerContacted(visitorid,visitorstatus)
{
	errMsg ="<table  width='340' cellspacing='10' cellpadding='0' bgcolor='#FFFFFF' border='0'><tr><td><table width='340' bgcolor='#FFFFFF' valign='top' border='0'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobdiv();\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr><td height='5' width='80%'></td><td height='5' width='20%'></td></tr><tr><td width='63%' class='insidecontent' align='center' style='width:155px;  bgcolor:#FFF;'><div class='styled-select' align='center'><select name='visitorreason'  id='visitorreason' class='insidecontent' STYLE='width: 200px;' align='center' onChange='javascript:setagentcomments()'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Reason--&gt;</option><option value='Converted � Ordered by Operator' >Converted � Ordered by Operator</option><option value='Converted � Ordered by Customer' >Converted � Ordered by Customer</option><option value='Converted � Ordered through Chat' >Converted � Ordered through Chat</option><option value='Not Converted � High Price' >Not Converted � High Price</option><option value='Not Converted � Just Checking' >Not Converted � Just Checking</option><option value='Not Converted � Need Immediately' >Not Converted � Need Immediately</option><option value='Not Converted -  Will Order Later' >Not Converted -  Will Order Later</option><option value='	Not Converted - With out responding disconnected the call'>Not Converted - With out responding disconnected the call</option><option value='Not Converted-Wrong Number' >Not Converted-Wrong Number</option><option value='Not Converted- Not Looking for any battery' >Not Converted- Not Looking for any battery</option><option value='Not Converted-Will Compare With Othersites/Local Dealers' >Not Converted-Will Compare With Othersites/Local Dealers</option><option value='Not Converted-Model Out Of Stock' >Not Converted-Model Out Of Stock</option><option value='Not Converted-Location Faraway' >Not Converted-Location Faraway</option><option value='Not Converted- Online/Credit Card Payment' >Not Converted- Online/Credit Card Payment</option><option value='Not Converted- Already Purchased the Battery' >Not Converted- Already Purchased the Battery</option><option value='Not Converted- Under Pro-Rata Warranty' >Not Converted- Under Pro-Rata Warranty</option><option value='Not Converted-Call Back' >Not Converted-Call Back</option></select></div></td></tr><tr><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;Message</td></tr><tr><td height='2'></td></tr></tr><tr><td width='100%' align='center'><input class='insidecontent' type='text'  name='cusmessage' id='cusmessage' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='100' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:updatecustcontactedstatus('"+visitorid+"','"+visitorstatus+"');return false;} else return true;\"/></td></tr><tr>&nbsp;&nbsp;&nbsp;<td  width='80%' align='center'><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:updatecustcontactedstatus('"+visitorid+"','"+visitorstatus+"');\"></td><td width='20%'></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr height='16'><td width='80%' align='center' class='subheading' id='displayrefinederrormsg1'></td><td width='20%'></td></tr><tr height='26'><td colspan='3' align='center' class='subheading' id='displayrefinederrormsg1'></td></tr><tr><td height='10'></td></tr></table></td></tr></table>";
	document.getElementById("divpostponed").innerHTML=""; 
	document.getElementById("divpostponed").style.display='block';
	document.getElementById("divpostponed").innerHTML=errMsg
}

/** Function to show options to select if agent not able to contact customer **/

function CustomernotContacted(visitorid,visitorstatus)
{
	errMsg ="<table  width='340' cellspacing='10' cellpadding='0' bgcolor='#FFFFFF' border='0'><tr><td><table width='340' bgcolor='#FFFFFF' valign='top' border='0'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobdiv();\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr><td height='5' width='80%'></td><td height='5' width='20%'></td></tr><tr><td width='63%' class='insidecontent' align='center' style='width:155px;  bgcolor:#FFF;'><div class='styled-select' align='center'><select name='visitorreason'  id='visitorreason' class='insidecontent' STYLE='width: 200px;' align='center' onChange='javascript:setagentcomments()'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Reason--&gt;</option><option value='Phone Busy' >Phone Busy</option><option value='Phone Not Reachable'>Phone Not Reachable</option><option value='Phone Switched Off'>Phone Switched Off</option><option value='Check The Number'>Check The Number</option><option value='In Correct Number'>In Correct Number</option><option value='Customer Care/Asistmi SupportNumber'>Customer Care/Asistmi SupportNumber</option><option value='Not Lifting'>Not Lifting</option><option value='Not Connecting'>Not Connecting</option><option value='Testing Purpose'>Testing Purpose</option></select></div></td></tr><tr><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;Message</td></tr><tr><td height='2'></td></tr></tr><tr><td width='100%' align='center'><input class='insidecontent' type='text'  name='cusmessage' id='cusmessage' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='100' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:updatecustcontactedstatus('"+visitorid+"','"+visitorstatus+"');return false;} else return true;\"/></td></tr><tr>&nbsp;&nbsp;&nbsp;<td  width='80%' align='center'><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:updatecustcontactedstatus('"+visitorid+"','"+visitorstatus+"');\"></td><td width='20%'></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr height='16'><td width='80%' align='center' class='subheading' id='displayrefinederrormsg1'></td><td width='20%'></td></tr><tr height='26'><td colspan='3' align='center' class='subheading' id='displayrefinederrormsg1'></td></tr><tr><td height='10'></td></tr></table></td></tr></table>";
	document.getElementById("divpostponed").innerHTML=""; 
	document.getElementById("divpostponed").style.display='block';
	document.getElementById("divpostponed").innerHTML=errMsg
}

/** Function to show options to select if customer postponed **/

function Postponedvis(visitorid,visitorstatus)
{
	errMsg ="<table  width='340' cellspacing='10' cellpadding='0' bgcolor='#FFFFFF' border='0'><tr><td><table width='340' bgcolor='#FFFFFF' valign='top' border='0'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobdiv();\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr><td width='80%' colspan='3' align='center'><font size='2' color='#FF8C00'>Please Select Postpone Date&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></td><td width='20%'></td></tr><tr><td height='10' width='80%'></td><td width='20%'></td></tr><tr><td width='80%' align='center'><input type='ext' name='postponedate' class='insidecontent' readonly  onChange='CheckDate(this)' onKeyDown='FormatDate(this,  window.event.keyCode,'down')' onKeyUp='FormatDate(this, window.event.keyCode,'up')' value='' size='10' maxlength='10'  style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' /></td><td width='20%' align='center'><img src='../../../../images/calender.jpg' valign='bottom' align='middle' style='cursor:hand;margin-right:10px;' onclick=\"javascript:displayDatePicker('postponedate', this);\" height='25' ></td><td></td></tr><tr><td height='5' width='80%'></td><td height='5' width='20%'></td></tr><tr><td width='63%' class='insidecontent' align='center' style='width:155px;  bgcolor:#FFF;'><div class='styled-select' align='center'><select name='visitorreason'  id='visitorreason' class='insidecontent' STYLE='width: 200px;' align='center' onChange='javascript:setagentcomments()'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Reason--&gt;</option><option value='Call Back- Call Back Tomorrow' >Call Back- Call Back Tomorrow</option><option value='Call Back- Model Out Of Stock'>Call Back- Model Out Of Stock</option></select></div></td></tr><tr height='10'><td width='100%' align='center'><input class='insidecontent' type='text'  name='cusmessage' id='cusmessage' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='100' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:updatecustcontactedstatus('"+visitorid+"','"+visitorstatus+"');return false;} else return true;\"/></td></tr><tr>&nbsp;&nbsp;&nbsp;<td  width='80%' align='center'><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:updatecustcontactedstatus('"+visitorid+"','"+visitorstatus+"');\"></td><td width='20%'></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr height='16'><td width='80%' align='center' class='subheading' id='displayrefinederrormsg1'></td><td width='20%'></td></tr><tr><td height='10'></td></tr></table></td></tr></table>";
	document.getElementById("divpostponed").innerHTML=""; 
	document.getElementById("divpostponed").style.display='block';
	document.getElementById("divpostponed").innerHTML=errMsg
}

/** Function to show options to select if agent not able to contact customer **/

function Repeatedvis(visitorid,visitorstatus)
{
	errMsg ="<table  width='340' cellspacing='10' cellpadding='0' bgcolor='#FFFFFF' border='0'><tr><td><table width='340' bgcolor='#FFFFFF' valign='top' border='0'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobdiv();\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr><td height='5' width='80%'></td><td height='5' width='20%'></td></tr><tr><td width='63%' class='insidecontent' align='center' style='width:155px;  bgcolor:#FFF;'><div class='styled-select' align='center'><select name='visitorreason'  id='visitorreason' class='insidecontent' STYLE='width: 200px;' align='center' onChange='javascript:setagentcomments()'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Reason--&gt;</option><option value='Customer Has Already Visited Our Site' >Customer Has Already Visited Our Site</option></select></div></td></tr><tr><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;Message</td></tr><tr><td height='2'></td></tr></tr><tr><td width='100%' align='center'><input class='insidecontent' type='text'  name='cusmessage' id='cusmessage' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='100' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:updatecustcontactedstatus('"+visitorid+"','"+visitorstatus+"');return false;} else return true;\"/></td></tr><tr>&nbsp;&nbsp;&nbsp;<td  width='80%' align='center'><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:updatecustcontactedstatus('"+visitorid+"','"+visitorstatus+"');\"></td><td width='20%'></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr height='16'><td width='80%' align='center' class='subheading' id='displayrefinederrormsg1'></td><td width='20%'></td></tr><tr height='26'><td colspan='3' align='center' class='subheading' id='displayrefinederrormsg1'></td></tr><tr><td height='10'></td></tr></table></td></tr></table>";
	document.getElementById("divpostponed").innerHTML=""; 
	document.getElementById("divpostponed").style.display='block';
	document.getElementById("divpostponed").innerHTML=errMsg
}
function setagentcomments()
{
//alert("s");
		var visitorreason = document.laptopvisitors.visitorreason.value;
		document.laptopvisitors.cusmessage.value=visitorreason;
		//var cusmessage = document.misbatstatus.cusmessage.value;
}
/** Function to change visitor status and reason **/
function updatecustcontactedstatus(visitorid,visitorstatus)
{
	
		var visitorreason = document.laptopvisitors.visitorreason.value;
		var cusmessage = document.laptopvisitors.cusmessage.value;

		var iChars3 = "`~!@#$%^&*()+=[]\\\';/{}|\":<>?";
		var dot=".";
		
		/** validations starts here for agent message **/
			if(cusmessage == "")
		 {
			errMsg ="<font color='#9B5BDD'>Please Enter Message</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.laptopvisitors.cusmessage.focus();
			return ;
		 }
		  if (document.laptopvisitors.cusmessage.value.indexOf(dot)==0 )
		 {
				errMsg ="<font color='#9B5BDD'>Message should not start with dot</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.laptopvisitors.cusmessage.focus();
				return ;
		 }
		/* for (var i = 0; i < document.laptopvisitors.cusmessage.value.length; i++)
		 {
			 if (iChars3.indexOf(document.laptopvisitors.cusmessage.value.charAt(i))!= -1)
			 {
				errMsg ="<font color='#9B5BDD'>Special characters are not allowed in Message field.</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.laptopvisitors.cusmessage.focus();
				return ;
			 }
		 }*/
		
		if (/[a-z][A-Z]{2}/i.test(document.laptopvisitors.cusmessage.value) != true) 
			{
			  errMsg ="<font color='#9B5BDD'>Please enter atleast 3 Charaters together in the Message Field.</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.laptopvisitors.cusmessage.focus();
				return ;
			 }

		if(visitorstatus=="Postponed")
		{
			var postponedate = document.laptopvisitors.postponedate.value;

		 /** Validations starts here for postponed date **/
			var mySplitResult = postponedate.split("-");
			var postday=mySplitResult[0];
			var postmonth=mySplitResult[1];
			var postyear=mySplitResult[2];
			date = new Date();
			var month = date.getMonth()+1;
			var day = date.getDate();
			var year = date.getFullYear();
	
			if(postponedate == "")
			{
				$('#displayrefinederrormsg1').show();
				errMsg ="<font color='#9B5BDD'>Please select postpone date</font>";
				document.getElementById("displayrefinederrormsg1").style.display='block';
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			}
			else if((postday<day)&&(postyear<year))
			{
				$('#displayrefinederrormsg1').show();
				errMsg ="<font color='#9B5BDD'>Selected Date should be greater than Today's Date</font>";
				document.getElementById("displayrefinederrormsg1").style.display='block';
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				
			}
			else if((postday<day)&&(postmonth<=month)&&(postyear<=year))
			{
				$('#displayrefinederrormsg1').show();
				errMsg ="<font color='#9B5BDD'>Selected Date should be greater than Today's Date</font>";
				document.getElementById("displayrefinederrormsg1").style.display='block';
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				
			}
			else if((postday>day)&&(postmonth<month)&&(postyear<=year))
			{
				$('#displayrefinederrormsg1').show();
				errMsg ="<font color='#9B5BDD'>Selected Date should be greater than Today's Date</font>";
				document.getElementById("displayrefinederrormsg1").style.display='block';
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;		
			}	
			else if((postday>day)&&(postyear<year))
			{
				$('#displayrefinederrormsg1').show();
				errMsg ="<font color='#9B5BDD'>Selected Date should be greater than Today's Date</font>";
				document.getElementById("displayrefinederrormsg1").style.display='block';
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;		
			}
			else if(postday==day&&(postmonth<=month)&&(postyear<=year))
			{
				$('#displayrefinederrormsg1').show();
				errMsg ="<font color='#9B5BDD'>Selected Date should be greater than Today's Date</font>";
				document.getElementById("displayrefinederrormsg1").style.display='block';
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;		
			}
		}
		else
		{

			var postponedate ="";

		}
		$('#divpostponed').hide();
		var xmlhttp= "";
		var resp= "";
		var url ="../../../../servlet/BookBatteryVisitors?hidWhatToDo=updatevisitorstatus&chkSi="+visitorid+"&visitorstatus="+visitorstatus+"&visitorreason="+visitorreason+"&agentcomments="+cusmessage+"&postponedate="+postponedate;
		if (window.XMLHttpRequest)
		{
			// code for IE7+, Firefox, Chrome, Opera, Safari
			 xmlhttp=new XMLHttpRequest();
		}
		else
		{
			// code for IE6, IE5
			xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange=function()
		{
			if (xmlhttp.readyState==4)
			{
				if(xmlhttp.status!=200)
				{
					alert("Error Occured. Please try again.");
					return;
				}
				else 
				{
					resp = xmlhttp.responseText;
					errMsg ="<table border='0' width='300px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closemobdiv();'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='300px' height='2px' bgcolor='#FFFFFF' valign'top'><tr bgcolor='#FFFFFF'><td align='justify' bgcolor='#FFFFFF' style='font-family:Verdana;font-size:11px;color:#000000;'>"+resp+"</td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='getupdatedvisitors();' class='button4'><br></td></tr><tr height='15'></tr></table>";
					document.getElementById("divpostponed").innerHTML=""; 
					document.getElementById("divpostponed").style.display='block';
					document.getElementById("divpostponed").innerHTML=errMsg
				}
			}			
		}
		var agree=confirm("Are You sure want to update the Visitor Status! ");
		if (agree)
		{
		xmlhttp.open("GET",url, true);		
		xmlhttp.send();	
		}
}
function closemobdiv()
{
	$('#divpostponed').hide();
	
}

/** Function to get visitor details after changing visitor status and reason **/
function getupdatedvisitors()
{
	$('#divpostponed').hide();

	var strDateOpts = document.laptopvisitors.dates.value;
	var strFromDates = document.laptopvisitors.txtFromDate.value;
	var strToDates = document.laptopvisitors.txtToDate.value;
	var type = document.laptopvisitors.type.value;
	var statusfilter = document.laptopvisitors.statusfilter.value;
	document.laptopvisitors.method="POST";
	document.laptopvisitors.action="../../../../servlet/BookBatteryVisitors?hidWhatToDo=getvisitorsoperator&dates="+strDateOpts+"&txtFromDate="+strFromDates+"&txtToDate="+strToDates+"&type="+type+"&statusfilter="+statusfilter+"";
	document.laptopvisitors.submit();
	
}

/** Function to get visitor details when agent selects filter **/
function getupdatedvisitors1(strDateOpts,strFromDates,strToDates,type)
{
	var statusfilter = document.laptopvisitors.statusfilter.value;
	document.laptopvisitors.method="POST";
	document.laptopvisitors.action="../../../../servlet/BookBatteryVisitors?hidWhatToDo=getvisitorsoperator&dates="+strDateOpts+"&txtFromDate="+strFromDates+"&txtToDate="+strToDates+"&type="+type+"&statusfilter="+statusfilter+"";
	document.laptopvisitors.submit();
	
}
</script>

</head>
<body onload="" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="laptopvisitors">
<div id='divpostponed' class='divpostponed' style="display:none;"></div>
<div id='divpostponed1' class='divpostponed1' style="display:none;"></div>
<div id='divmobile' class='divmobile' style="display:none;"></div>

<!-- Battery Header Starts -->
<tr>
	<jsp:include page = "../../header.jsp" />
</tr>
<!-- Battery Header Ends -->
<tr>
	<td>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#ffffff">
		<tr>
			
			<td width="75%" align="left" valign="top">
				<table width="100%" border="1" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">
					<!-- your page content starts here  -->
					<table width="100%" cellspacing="1" align="center" bgcolor="#FFFFFF" cellpadding="0" border="0">
					<table width="450" border="0" align="center" cellpadding="5" cellspacing="0" bgcolor="#FFFFFF">
					<tr><td align="left" class="subheading"><a href="../../../../jsp/operator/batterystore/visitors/selectoptions.jsp?&dates=<%=strDateOpt%>&txtFromDate=<%=strFromDate%>&txtToDate=<%=strToDate%>" class="onclick1">Back</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Filters : <select style='width:215px;background-color: #ffffff;color: #000;padding: .5em;padding-right: 2.5em;margin: 0;border-radius: 3px;text-indent: 0.01px;' name="statusfilter" id="statusfilter" onChange="javascript:getupdatedvisitors1('<%=strDateOpt%>','<%=strFromDate%>','<%=strToDate%>','<%=type%>')"><option value='<%=statusfilter1%>'><%=statusfilter1%></option><option value=''>Select Status </option><option value='Not Called'>Not Called </option><option value='Customer Contacted'>Customer Contacted</option><option value='Customer Not Contacted'>Customer Not Contacted</option><option value='Postponed'>Postponed</option><option value='Repeated'>Repeated</option></select></td></tr>
					<tr>
						<td>
							<table width="100%" align="center" cellspacing="1" cellpadding="0">
							<tr>
								<td class="subheading" align="left" size="2"><%=strDateOpt%> >> Number Of Visitors
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:onClickExportToExcel();" class="onclick1"><img src="../../../../images/export_xls.gif" border="0"/>&nbsp;Export as Excel</a>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:onClickExportToPdf();" class="onclick1"><img src="../../../../images/pdf_xls.gif" border="0"/>&nbsp;Export as PDF</a></td>
							</tr>
							<tr>
								<td>
									<table width="100%" cellspacing="1" bgcolor="#8B8D90" cellpadding="0">
									<tr>
										<td>
											<table width="100%" cellspacing="1" bgcolor="#8B8D90" cellpadding="0">
											<tr>
												<td bgcolor="#0081C7" align="center" class="subheading"><font color="#FFFFFF">BookBattery&nbsp;<%=type%>&nbsp;visitors&nbsp;summary</font></td>
											</tr>
											<tr>
												<td bgcolor="#FFFFFF" align="center" colspan="2">
													<table width="100%" align="center" cellspacing="0" bgcolor="#FFFFFF" cellpadding="5">
													<tr>
														<td  align="center" > 
															<table width="50%" cellspacing="0"  cellpadding="2" border="2" align='left'>
															<!--<tr bgcolor="#DAD0E1">
																<td width="30%"  align="left" class="insidecontent" >&nbsp;Ordered&nbsp;Status</td><td align="left" class="insidecontent" >:&nbsp;<%=htOptions.get("orderstatus")%></td>
															</tr>-->
															<tr >	
																<td bgcolor="#DAD0E1" align="left" class="insidecontent" >&nbsp;From Date</td><td align="left" class="insidecontent" >:&nbsp;<%=htOptions.get("txtFromDate")%></td><td  align="left" class="insidecontent" bgcolor="#DAD0E1" >&nbsp;To Date</td><td align="left" class="insidecontent" >:&nbsp;<%=htOptions.get("txtToDate")%></td><td  align="left" class="insidecontent" bgcolor="#DAD0E1">&nbsp;No Of Visitors</td><td align="left" class="insidecontent" >:&nbsp;<%=visitorscount%></td>
															</tr>
														
															</table>
														</td>
													</tr>
													<%
													if(alist!=null && alist.size() > 0)
													{
													%>
													
														<tr>
															<td>
															<div style="width:1175px;height:400px; overflow:scroll; overflow-X:auto;  -webkit-overflow-scrolling: touch;" id='div1'>
																<table width="100%" cellspacing="1"  cellpadding="2" bgcolor="#dddddd">
																	<tr  bgcolor="#cccccc">
																		<td align="center" class="subheading"><font color="#000000">Sl&nbsp;No</font></td>
																		<td align="center" class="subheading"><font color="#000000">Battery&nbsp;Type</font></td>
																		<td align="center" class="subheading"><font color="#000000">Laptop Make</font></td>
																		<td align="center" class="subheading"><font color="#000000">Laptop Model</font></td>						<td align="center" class="subheading"><font color="#000000">Visitor&nbsp;Mobile&nbsp;Num</font></td>
																		<td align="center" class="subheading"><font color="#000000">Current&nbsp;Status</font></td>
																		<td align="center" class="subheading"><font color="#000000">Current&nbsp;Reasons</font></td>
																		<td align="center" class="subheading"><font color="#000000">Agent&nbsp;Comments</font></td>
																		<td align="center" class="subheading"><font color="#000000">Update&nbsp;Status</font></td>
																		<td align="center" class="subheading"><font color="#000000">State</font></td>
																		<td align="center" class="subheading"><font color="#000000">City</font></td>
																		<td align="center" class="subheading"><font color="#000000">Area</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Visit Time</font></td>
																		<td align="center" class="subheading"><font color="#000000">Operator Last update time</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Agent Name</font></td>
																	</tr>
																	<%
																	for(int i=0;i<alist.size();i++)
																	{
																		Hashtable ht=(Hashtable)alist.get(i);
																		String visid=String.valueOf(ht.get("vis_ord_id"));
																		String battype=(String)ht.get("bat_type");
																		String vehmake=(String)ht.get("veh_make");
																		String vehmodel=String.valueOf(ht.get("veh_model"));																		
																		String state=(String)ht.get("state");
																		String city=(String)ht.get("city");
																		String area=(String)ht.get("area");															
																		String mobilenumber=(String)ht.get("mobile_number");						String visitor_status=(String)ht.get("visitor_status");
																		String visitor_reasons=(String)ht.get("visitor_reasons");
																		String visitors_comments=(String)ht.get("visitors_comments");
																		String agent_name=(String)ht.get("agent_name");											
																		String date=String.valueOf(ht.get("creation_date"));
																		String updateddate=String.valueOf(ht.get("updateddate"));
																		LogLevel.DEBUG(1,new Throwable(),"updateddate: "+updateddate);
																		if( visitors_comments == null)
																		{
																			visitors_comments = "";
																		}
																		else
																		{
																			visitors_comments=visitors_comments;
																		}
																		if(state.equals("null") || state == "null")
																		{
																			state = "";
																		}
																		else
																		{
																			state=state;
																		}
																		if(city.equals("null") || city == "null")
																		{
																			city = "";
																		}
																		else
																		{
																			city=city;
																		}
																	if(visitor_status.equals("Not Called"))
																		{
																	%>
																	<tr bgcolor="#FFFFFF">
																	<%}
																	else if(visitor_status.equals("Repeated"))
																		{
																		%>
																	<tr bgcolor="#0066FF">
																		<%
																		}
																	else if(visitor_status.equals("Postponed"))
																		{
																		%>
																	<tr bgcolor="#FFFF66">
																		<%
																		}
																		else if(visitor_status.equals("Customer Not Contacted"))
																		{
																		%>
																	<tr bgcolor="#FF5050">
																		<%
																		}
																	else if(visitor_status.equals("Customer Contacted") && visitor_reasons.equals("Converted � Ordered by Operator") || visitor_reasons.equals("Converted � Ordered by Customer") || visitor_reasons.equals("Converted � Ordered through Chat"))
																		{
																		%>
																	<tr bgcolor="#009933">
																		<%
																		}
																	else
																		{
																		%>
																	<tr bgcolor="#DAD0E1">
																		<%
																		}

																		%>
																		<td  class="insidecontent" align="center"><%=(i+1)%> </td>
																		<td  class="insidecontent" align="left"><%=battype%> </td>
																		<td  class="insidecontent" align="left"><%=vehmake%> </td>
																		<td  class="insidecontent" align="left"><%=vehmodel%> </td>					<td  class="insidecontent" align="left"><%=mobilenumber%> </td>
																		<td  class="insidecontent" align="left"><%=visitor_status%> </td>
																		<td  class="insidecontent" align="left"><%=visitor_reasons%> </td>
																		<td  class="insidecontent" align="left"><%=visitors_comments%> </td>
																		<td  class="insidecontent" align="left">
																		<select style='width:110px;' name="<%=visid%>" id="<%=visid%>" onChange="javascript:Updatevisitorstatus('<%=visid%>')"><option value=''>Update Status</option><option value='Not Called'>Not Called </option><option value='Customer Contacted'>Customer Contacted</option><option value='Customer Not Contacted'>Customer Not Contacted</option><option value='Postponed'>Postponed</option><option value='Repeated'>Repeated</option></select> </td>
																		<td  class="insidecontent" align="left"><%=state%> </td>
																		<td  class="insidecontent" align="left"><%=city%> </td>
																		<td  class="insidecontent" align="left"><%=area%> </td>
																		<td  class="insidecontent" align="left"><%=date%> </td>
																		<td  class="insidecontent" align="left"><%=updateddate%> </td>
																		<td  class="insidecontent" align="left"><%=agent_name%> </td>

																	</tr>
																	<%
																	}
																	%>
																	</table>
																</div>
																</table>
															</td>
														</tr>
														<%
														}
														else
														{
														%>
														<tr bgcolor="#CCCCCC">
															<td  class="table1" align="center"><font color="#000000">There are no records in the specified period</font></td>
														</tr>
														<%
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
	<!-- Inner content ends here -->
	<!-- Footer Starts Here -->
	<tr>
		<td>
			<jsp:include page = "../../footer.jsp" />
		</td>
	</tr>                           
	<!-- footer Ends Here -->
</td>
</tr>
</table>
<input type="hidden" name="dates" value="<%=strDateOpt%>">
<input type="hidden" name="txtFromDate" value="<%=strFromDate%>">
<input type="hidden" name="txtToDate" value="<%=strToDate%>">
<input type="hidden" name="type"  value="<%=type%>">

</form>
</center>
</body>
</html>