<%--
    Document   : batteryadminmain
    Created on : Aug 30, 2013, 4:22:12 PM
    Author     : Sai Krishna Daddala.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.LogLevel"%>
<%
String strUserid=(String)session.getAttribute("sesBatteryOperatorName");
if(strUserid==null)
{
	strUserid="";
	session.setAttribute("sesErrorMsg","Session Timed Out. Please login again");
	response.sendRedirect("../../../operator/index.html");
	return;
}
Vector alist=(Vector)session.getAttribute("sesBatHealthChkVector");
LogLevel.DEBUG(1,new Throwable(),"alist: "+alist);

Hashtable htOptions=(Hashtable)session.getAttribute("sesOptions");
String bathealthChks=(String)htOptions.get("bathealthChks"); 
String FromDate=(String)htOptions.get("txtFromDate");
String ToDate=(String)htOptions.get("txtToDate");

String strDateOpt=request.getParameter("dates");
if(strDateOpt==null || strDateOpt.equals(""))
	strDateOpt="current";

String strFromDate=(request.getParameter("txtFromDate")!=null)?request.getParameter("txtFromDate"):"";
String strToDate=(request.getParameter("txtToDate")!=null)?request.getParameter("txtToDate"):"";

// Keep all the headings in session
Vector vectHeaderwithcat = new Vector();
vectHeaderwithcat.add("Bat Type");
vectHeaderwithcat.add("Veh type");
vectHeaderwithcat.add("Veh Model");
vectHeaderwithcat.add("Bat Brand");
vectHeaderwithcat.add("Bat Model");
vectHeaderwithcat.add("Cus Name");
vectHeaderwithcat.add("Cus Mobile");
vectHeaderwithcat.add("Ins Month");
vectHeaderwithcat.add("Ins Year");
vectHeaderwithcat.add("City");
vectHeaderwithcat.add("Area");
vectHeaderwithcat.add("Date");
session.removeAttribute("sesHeaderwithcat");
session.setAttribute("sesHeaderwithcat",vectHeaderwithcat);


// put the body data in session
session.removeAttribute("sesBodywithcat");
session.setAttribute("sesBodywithcat",alist);

// Put the tile in session
session.removeAttribute("sesTitlewithcat");
session.setAttribute("sesTitlewithcat",bathealthChks);
session.setAttribute("sesFromDate",FromDate);
session.setAttribute("sesToDate",ToDate);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../images/favicon.png" type="image/x-icon">
<title>BookBattery.com-India's No.1 Automobile Battery Store</title>
<script language="javascript">
function onClickExportToExcel()
	{
		strUrl = "../../../servlet/BatteryExcelandPdfServlet?hidWhatToDo=ExportToExcelbat";
		window.open(strUrl,"ExcelOpen","height=450,width=530,toobar=0,location=0,directories=0,status=yes,menubar=0,scrollbars=yes,resizable=1"); 
	}
	function onClickExportToPdf()
	{
		strUrl = "../../../servlet/BatteryExcelandPdfServlet?hidWhatToDo=ExportToPDFbat";
		window.open(strUrl,"ExcelOpen","height=450,width=530,toobar=0,location=0,directories=0,status=yes,menubar=0,scrollbars=yes,resizable=1"); 
	}


/** Function to change visitor status **/
function Updatevisitorstatus(visitorid)
{
	//alert(visitorid);
	
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
		var url ="../../../../servlet/MISOperatorBatteryDetails?hidWhatToDo=updatevisitorstatus&chkSi="+visitorid+"&visitorstatus="+orderedstatus;
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
		/*var agree=confirm("Are You sure want to update the Visitor Status! ");
		if (agree)
		{*/
			xmlhttp.open("GET",url, true);		
			xmlhttp.send();	
		/*}*/
		
	}

}

/** Function to show options to select if agent contacts customer **/

function CustomerContacted(visitorid,visitorstatus)
{
	errMsg ="<table  width='340' cellspacing='10' cellpadding='0' bgcolor='#FFFFFF' border='0'><tr><td><table width='340' bgcolor='#FFFFFF' valign='top' border='0'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobdiv();\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr><td height='5' width='80%'></td><td height='5' width='20%'></td></tr><tr><td width='63%' class='insidecontent' align='center' style='width:155px;  bgcolor:#FFF;'><div class='styled-select' align='center'><select name='visitorreason'  id='visitorreason'  class='insidecontent' STYLE='width: 200px;' align='center' onChange='javascript:setagentcomments()'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Reason--&gt;</option><option value='Battery  Health Checkup has been done' >Battery  Health Checkup has been done</option><option value='In Process' >In Process</option><option value='Battery  Health Checkup-Not Done' >Battery  Health Checkup-Not Done</option><option value='cancelled-franchisee-offbushrs' >Battery  Health Checkup-Not Done - Cancelled-Franchisee-OffBusHrs</option><option value='cancelled-franchisee-denied' >Battery  Health Checkup-Not Done - Cancelled-Franchisee-Denied</option><option value='cancelled-franchisee-notresponded' >Battery  Health Checkup-Not Done - Cancelled-Franchisee-NotResponded</option><option value='cancelled-customer' >Battery  Health Checkup-Not Done- Cancelled-Customer</option><option value='Battery  Health Checkup-Not Done-Customer-Denied-After going to his place'>Battery  Health Checkup-Not Done-Customer-Denied-After going to his place</option></select></div></td></tr><tr><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;Message</td></tr><tr><td height='2'></td></tr></tr><tr><td width='100%' align='center'><textarea name='cusmessage' id='cusmessage' rows='6' cols='30'></textarea></td></tr><tr>&nbsp;&nbsp;&nbsp;<td  width='80%' align='center'><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:updatecustcontactedstatus('"+visitorid+"','"+visitorstatus+"');\"></td><td width='20%'></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr height='16'><td width='80%' align='center' class='subheading' id='displayrefinederrormsg1'></td><td width='20%'></td></tr><tr height='26'><td colspan='3' align='center' class='subheading' id='displayrefinederrormsg1'></td></tr><tr><td height='10'></td></tr></table></td></tr></table>";
	document.getElementById("divpostponed").innerHTML=""; 
	document.getElementById("divpostponed").style.display='block';
	document.getElementById("divpostponed").innerHTML=errMsg
}

/** Function to show options to select if agent not able to contact customer **/

function CustomernotContacted(visitorid,visitorstatus)
{
	errMsg ="<table  width='340' cellspacing='10' cellpadding='0' bgcolor='#FFFFFF' border='0'><tr><td><table width='340' bgcolor='#FFFFFF' valign='top' border='0'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobdiv();\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr><td height='5' width='80%'></td><td height='5' width='20%'></td></tr><tr><td width='63%' class='insidecontent' align='center' style='width:155px;  bgcolor:#FFF;'><div class='styled-select' align='center'><select name='visitorreason'  id='visitorreason' class='insidecontent' STYLE='width: 200px;' align='center' onChange='javascript:setagentcomments()'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Reason--&gt;</option><option value='Phone Busy' >Phone Busy</option><option value='Phone Not Reachable'>Phone Not Reachable</option><option value='Phone Switched Off'>Phone Switched Off</option></select></div></td></tr><tr><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;Message</td></tr><tr><td height='2'></td></tr></tr><tr><td width='100%' align='center'><textarea name='cusmessage' id='cusmessage' rows='6' cols='30'></textarea></td></tr><tr>&nbsp;&nbsp;&nbsp;<td  width='80%' align='center'><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:updatecustcontactedstatus('"+visitorid+"','"+visitorstatus+"');\"></td><td width='20%'></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr height='16'><td width='80%' align='center' class='subheading' id='displayrefinederrormsg1'></td><td width='20%'></td></tr><tr height='26'><td colspan='3' align='center' class='subheading' id='displayrefinederrormsg1'></td></tr><tr><td height='10'></td></tr></table></td></tr></table>";
	document.getElementById("divpostponed").innerHTML=""; 
	document.getElementById("divpostponed").style.display='block';
	document.getElementById("divpostponed").innerHTML=errMsg
}

/** Function to show options to select if customer postponed **/

function Postponedvis(visitorid,visitorstatus)
{
	errMsg ="<table  width='340' cellspacing='10' cellpadding='0' bgcolor='#FFFFFF' border='0'><tr><td><table width='340' bgcolor='#FFFFFF' valign='top' border='0'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobdiv();\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr><td width='80%' colspan='3' align='center'><font size='2' color='#FF8C00'>Please Select Postpone Date&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></td><td width='20%'></td></tr><tr><td height='10' width='80%'></td><td width='20%'></td></tr><tr><td width='80%' align='center'><input type='ext' name='postponedate' class='insidecontent' readonly  onChange='CheckDate(this)' onKeyDown='FormatDate(this,  window.event.keyCode,'down')' onKeyUp='FormatDate(this, window.event.keyCode,'up')' value='' size='10' maxlength='10'  style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' /></td><td width='20%' align='center'><img src='../../../../images/calender.jpg' valign='bottom' align='middle' style='cursor:hand;margin-right:10px;' onclick=\"javascript:displayDatePicker('postponedate', this);\" height='25' ></td><td></td></tr><tr><td height='5' width='80%'></td><td height='5' width='20%'></td></tr><tr><td width='63%' class='insidecontent' align='center' style='width:155px;  bgcolor:#FFF;'><div class='styled-select' align='center'><select name='visitorreason'  id='visitorreason' class='insidecontent' STYLE='width: 200px;' align='center' onChange='javascript:setagentcomments()'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Reason--&gt;</option><option value='High Priority' >High Priority</option><option value='Customer is not picking the Call' >Customer is not picking the Call</option><option value='Customer Number Busy'>Customer Number is Busy</option><option value='Customer Number is Not Reachable' >Customer Number is Not Reachable</option><option value='Customer Number Switched Off' >Customer Number is Switched Off</option><option value='Customer is Out of Station' >Customer is Out of Station</option><option value='Customer is Not Responding to our Calls' >Customer is Not Responding to our Calls</option><option value='Customer need Health Checkup Today' >Customer need Health Checkup Today</option><option value='Yesterdays After Business Hour Order' >As it is not business hours, we will provide the service tomorrow</option><option value='Pitstop is So Far Customer will come whenever he is free' >Pitstop is So Far, Customer will come whenever he is free</option><option value='Order Status is not yet confirmed from Franchisee or Customer' >Order Status is not yet confirmed from Franchisee or Customer</option><option value='Pitstop was on Leave Yesterday Need to process the order today' >Franchisee is on Leave Today</option></select></div></td></tr><tr height='10'><td width='100%' align='center'><textarea name='cusmessage' id='cusmessage' rows='6' cols='30'></textarea></td></tr><tr>&nbsp;&nbsp;&nbsp;<td  width='80%' align='center'><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:updatecustcontactedstatus('"+visitorid+"','"+visitorstatus+"');\"></td><td width='20%'></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr height='16'><td width='80%' align='center' class='subheading' id='displayrefinederrormsg1'></td><td width='20%'></td></tr><tr><td height='10'></td></tr></table></td></tr></table>";
	document.getElementById("divpostponed").innerHTML=""; 
	document.getElementById("divpostponed").style.display='block';
	document.getElementById("divpostponed").innerHTML=errMsg
}

/** Function to show options to select if agent not able to contact customer **/

function Repeatedvis(visitorid,visitorstatus)
{
	errMsg ="<table  width='340' cellspacing='10' cellpadding='0' bgcolor='#FFFFFF' border='0'><tr><td><table width='340' bgcolor='#FFFFFF' valign='top' border='0'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobdiv();\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr><td height='5' width='80%'></td><td height='5' width='20%'></td></tr><tr><td width='63%' class='insidecontent' align='center' style='width:155px;  bgcolor:#FFF;'><div class='styled-select' align='center'><select name='visitorreason'  id='visitorreason' class='insidecontent' STYLE='width: 200px;' align='center' onChange='javascript:setagentcomments()'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Reason--&gt;</option><option value='Customer Has Placed Order For Twice' >Customer Has Placed Order For Twice</option></select></div></td></tr><tr><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;Message</td></tr><tr><td height='2'></td></tr></tr><tr><td width='100%' align='center'><textarea name='cusmessage' id='cusmessage' rows='6' cols='30'></textarea></td></tr><tr>&nbsp;&nbsp;&nbsp;<td  width='80%' align='center'><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:updatecustcontactedstatus('"+visitorid+"','"+visitorstatus+"');\"></td><td width='20%'></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr height='16'><td width='80%' align='center' class='subheading' id='displayrefinederrormsg1'></td><td width='20%'></td></tr><tr height='26'><td colspan='3' align='center' class='subheading' id='displayrefinederrormsg1'></td></tr><tr><td height='10'></td></tr></table></td></tr></table>";
	document.getElementById("divpostponed").innerHTML=""; 
	document.getElementById("divpostponed").style.display='block';
	document.getElementById("divpostponed").innerHTML=errMsg
}

function setagentcomments()
{
//alert("s");
		var visitorreason = document.healthchecksummary.visitorreason.value;
		document.healthchecksummary.cusmessage.value=visitorreason;
		//var cusmessage = document.healthchecksummary.cusmessage.value;
}

/** Function to change visitor status and reason **/
function updatecustcontactedstatus(visitorid,visitorstatus)
{
	
//alert(visitorid);
//alert(visitorstatus);
		
		var visitorreason = document.healthchecksummary.visitorreason.value;
		var cusmessage = document.healthchecksummary.cusmessage.value;

		//alert(visitorreason);
		//alert(cusmessage);

		var iChars3 = "`~!@#$%^&*()+=[]\\\';/{}|\":<>?";
		var dot=".";
		
		/** validations starts here for agent message **/
		 
		 if(visitorstatus=="Postponed")
		{
			var postponedate = document.healthchecksummary.postponedate.value;

		 /** Validations starts here for postponed date **/

	
			if(postponedate == 0 ||  postponedate == "")	
			{
				errMsg ="<font color='#9B5BDD'>Please select postpone date</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				javascript:displayDatePicker('postponedate');				
				return ;				
			}
			
			var mySplitResult = postponedate.split("-");
			var postday=mySplitResult[0];
			var postmonth=mySplitResult[1];
			var postyear=mySplitResult[2];
			date = new Date();
			var month = date.getMonth()+1;
			var day = date.getDate();
			var year = date.getFullYear();



			if((postday<day)&&(postyear<year))
			{
				errMsg ="<font color='#9B5BDD'>Selected Date should be greater than Today's Date</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				javascript:displayDatePicker('postponedate');				
				return ;				
			}
			 if((postday<day)&&(postmonth<=month)&&(postyear<=year))
			{
				errMsg ="<font color='#9B5BDD'>Selected Date should be greater than Today's Date</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				javascript:displayDatePicker('postponedate');				
				return ;	
				
			}
			 if((postday>day)&&(postmonth<month)&&(postyear<=year))
			{
				errMsg ="<font color='#9B5BDD'>Selected Date should be greater than Today's Date</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				javascript:displayDatePicker('postponedate');				
				return ;			
			}	
			 if((postday>day)&&(postyear<year))
			{
				errMsg ="<font color='#9B5BDD'>Selected Date should be greater than Today's Date</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				javascript:displayDatePicker('postponedate');				
				return ;		
			}
			if((postday==day)&&(postmonth<=month)&&(postyear<=year))
			{
				errMsg ="<font color='#9B5BDD'>Selected Date should be greater than Today's Date</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				javascript:displayDatePicker('postponedate');				
				return ;			
			}
		}
		else
		{

			var postponedate ="";

		}
		 
		 if(visitorreason == 0 || visitorreason == "default")	
		 {
			errMsg ="<font color='#9B5BDD'>Please Select Reason</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.healthchecksummary.visitorreason.focus();
			return ;
		 }

		 if(cusmessage == "")
		 {
			errMsg ="<font color='#9B5BDD'>Please Enter Message</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.healthchecksummary.cusmessage.focus();
			return ;
		 }
		  if (document.healthchecksummary.cusmessage.value.indexOf(dot)==0 )
		 {
				errMsg ="<font color='#9B5BDD'>Message should not start with dot</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.healthchecksummary.cusmessage.focus();
				return ;
		 }
		/* for (var i = 0; i < document.healthchecksummary.cusmessage.value.length; i++)
		 {
			 if (iChars3.indexOf(document.healthchecksummary.cusmessage.value.charAt(i))!= -1)
			 {
				errMsg ="<font color='#9B5BDD'>Special characters are not allowed in Message field.</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.healthchecksummary.cusmessage.focus();
				return ;
			 }
		 }*/
		
		    if (/[a-z][A-Z]{2}/i.test(document.healthchecksummary.cusmessage.value) != true) 
			{
			  errMsg ="<font color='#9B5BDD'>Please enter atleast 3 Charaters together in the Message Field.</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.healthchecksummary.cusmessage.focus();
				return ;
			}

		
		$('#divpostponed').hide();
		var xmlhttp= "";
		var resp= "";
		var url ="../../../servlet/MISOperatorBatteryDetails?hidWhatToDo=updatevisitorstatus&chkSi="+visitorid+"&visitorstatus="+visitorstatus+"&visitorreason="+visitorreason+"&agentcomments="+cusmessage+"&postponedate="+postponedate;
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
					//alert(resp);
					errMsg ="<table border='0' width='300px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closemobdiv();'><img src=\"../../../../images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='300px' height='2px' bgcolor='#FFFFFF' valign'top'><tr bgcolor='#FFFFFF'><td align='justify' bgcolor='#FFFFFF' style='font-family:Verdana;font-size:11px;color:#000000;'>"+resp+"</td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='getupdatedvisitors();' class='button4'><br></td></tr><tr height='15'></tr></table>";
					document.getElementById("divpostponed").innerHTML=""; 
					document.getElementById("divpostponed").style.display='block';
					document.getElementById("divpostponed").innerHTML=errMsg
				}
			}			
		}
		/*var agree=confirm("Are You sure want to update the Visitor Status! ");
		if (agree)
		{*/
		xmlhttp.open("GET",url, true);		
		xmlhttp.send();	
		/*}*/
}
function closemobdiv()
{
	$('#divpostponed').hide();
	
}

/** Function to get visitor details after changing visitor status and reason **/
function getupdatedvisitors()
{
	$('#divpostponed').hide();

	var strDateOpts = document.healthchecksummary.dates.value;
	var strFromDates = document.healthchecksummary.txtFromDate.value;
	var strToDates = document.healthchecksummary.txtToDate.value;
	

	document.healthchecksummary.method="POST";
	document.healthchecksummary.action="../../../servlet/MISOperatorBatteryDetails?hidWhatToDo=getbatteryhealthcheck&dates="+strDateOpts+"&txtFromDate="+strFromDates+"&txtToDate="+strToDates;
	document.healthchecksummary.submit();
	
}
</script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	/*background-image: url(../../../images/index_01_01.gif);
	background-repeat: repeat-x;*/
}
-->

.divpostponed{left:57%;top:30%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left; border:6px solid #424242; border-radius: 6px 6px 6px 6px;position:absolute;padding: 1px;display: none;z-index:0;}

.divpostponed1{left:50.5%;top:30%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left; border:6px solid #424242; border-radius: 6px 6px 6px 6px;position:absolute;padding: 1px;display: none;z-index:0;}

.divmobile{left:35.5%;top:30%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left; border:6px solid #424242; border-radius: 6px 6px 6px 6px;position:absolute;padding: 1px;display: none;z-index:60;background-color: white;}
</style>
<script language="JavaScript" src="../../../js/jquery-1.8.2.js" ></script>
<script src="../../../js/jquery-1.3.2.min.js"></script>
<script language=javascript src="../../../js/datepicker.js"></script>
<script type='text/javascript' src="../../../js/popgreyout.js"></script>
<link href="../../../css/bookbattery.css" rel="stylesheet" type="text/css" />
</head>
<body onload="" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="healthchecksummary">
<div id='divpostponed' class='divpostponed' style="display:none;"></div>
<div id='divpostponed1' class='divpostponed1' style="display:none;"></div>
<div id='divmobile' class='divmobile' style="display:none;"></div>
<!-- Battery Header Starts -->
<tr>
	<jsp:include page = "../header.jsp" />
</tr>
<!-- Battery Header Ends -->
<!--<tr>
	<td>
		<img src="../../../images/flag1234.JPG" width="880" height="15">
	</td>
</tr>-->
<tr>
	<td>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#ffffff">
		<tr>
			<td width="25%" align="left" valign="top" bgcolor="#ffffff">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<!-- Starts battery admin left Menu -->
				<tr>
					<jsp:include page="../operatorleftmenu.jsp"/>
				</tr>
				<!-- Ends battery admin left Menu -->
				</table>
			</td>
			<td width="75%" align="left" valign="top">
				<table width="100%" border="1" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">
					<!-- your page content starts here  -->
					<table width="100%" cellspacing="1" align="center" bgcolor="#FFFFFF" cellpadding="0" border="0">
					<table width="100%" border="0" align="center" cellpadding="5" cellspacing="0" bgcolor="#FFFFFF">
					<tr><td align="left" class="subheading"></td><td width="50%"align="right"><a href="../../../jsp/operator/mis/healthcheckmisoption.jsp?dates=<%=strDateOpt%>&txtFromDate=<%=strFromDate%>&txtToDate=<%=strToDate%>" class="onclick1">Back</a></td></tr>
					<tr>
						<td>
							<table width="100%" align="center" cellspacing="1" cellpadding="0">
							<tr><td height="20"></td></tr>
							<tr>
								<td class="subheading" align="left" size="2"><%=strDateOpt%> >> <%=htOptions.get("bathealthChks")%></td>
							</tr>
							<tr>
								<td>
									<table width="100%" cellspacing="1" bgcolor="#8B8D90" cellpadding="0">
									<tr>
										<td>
											<table width="100%" cellspacing="1" bgcolor="#8B8D90" cellpadding="0">
											<tr>
												<td bgcolor="#0081C7" align="center" class="subheading"><font color="#FFFFFF">Battery&nbsp;Health&nbsp;Check&nbsp;Details&nbsp;Summary</font></td>
											</tr>
											<tr>
												<td bgcolor="#FFFFFF" align="center" colspan="2">
													<table width="100%" align="center" cellspacing="0" bgcolor="#FFFFFF" cellpadding="5">
													<tr>
														<td  align="center" > 
															<table width="100%" cellspacing="0"  cellpadding="2">
															<tr bgcolor="#DAD0E1">
																<td width="30%"  align="left" class="insidecontent" >&nbsp;Option</td><td align="left" class="insidecontent" >:&nbsp;<%=htOptions.get("bathealthChks")%></td>
															</tr>
															<tr bgcolor="#DAD0E1">	
																<td width="#DAD0E1%"  align="left" class="insidecontent" >&nbsp;From Date</td><td align="left" class="insidecontent" >:&nbsp;<%=htOptions.get("txtFromDate")%></td>
															</tr>
															<tr bgcolor="#DAD0E1">
																<td width="30%" align="left" class="insidecontent" >&nbsp;To Date</td><td align="left" class="insidecontent" >:&nbsp;<%=htOptions.get("txtToDate")%></td>
															</tr>
															</table>
														</td>
													</tr>
													<%
													if(alist!=null && alist.size() > 0)
													{
													%>
													<tr bgcolor="#FFFFFF">
															<td  align="center" bgcolor="#FFFFFF"> 
																<table width="50%" cellspacing="5"  cellpadding="5">
																	<tr bgcolor="#dddddd">
																		<td align="center"><a href="javascript:onClickExportToExcel();" class="onclick1"><img src="../../../images/export_xls.gif" border="0"/>&nbsp;Export as Excel</a></td>
																		<td align="center"><a href="javascript:onClickExportToPdf();" class="onclick1"><img src="../../../images/pdf_xls.gif" border="0"/>&nbsp;Export as PDF</a></td>
																	 </tr>
																</table>
															</td>
														</tr>
														<tr>
															<td>
															<div style="width:100%;height:200px;  overflow:scroll; overflow-X:auto;  -webkit-overflow-scrolling: touch;">
																<table width="100%" cellspacing="1"  cellpadding="2" bgcolor="#dddddd">
																	<tr  bgcolor="#cccccc">
																		<td align="center" class="subheading"><font color="#000000">Sl&nbsp;No</font></td>
																		<td align="center" class="subheading"><font color="#000000">Service Need Date</font></td>
																		<td align="center" class="subheading"><font color="#000000">Cust&nbsp;Name</font></td>
																		<td align="center" class="subheading"><font color="#000000">Cust&nbsp;Mobile</font></td>
																		<td align="center" class="subheading"><font color="#000000">Supp&nbsp;Mobile</font></td>
																		<td align="center" class="subheading"><font color="#000000">Supp&nbsp;Name</font></td>
																		<td align="center" class="subheading"><font color="#000000">City</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Area</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Pincode</font></td>
																		<td align="center" class="subheading"><font color="#000000"> CreationDate</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Agent Name</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Order Status</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Order Reasons</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Agent Comments</font></td>
																		<td align="center" class="subheading"><font color="#000000">Update&nbsp;Status</font></td>

																	</tr>
																	<%
																	for(int i=0;i<alist.size();i++)
																	{
																		Hashtable ht=(Hashtable)alist.get(i);
																		String Batchlid=String.valueOf(ht.get("bat_service_id"));
																		String insmonth=(String)ht.get("bat_ins_month");
																		String insyear=(String)ht.get("bat_ins_year");
																		String vehtype=String.valueOf(ht.get("veh_type"));
																		String vehmodel=(String)ht.get("veh_model");
																		String batype=String.valueOf(ht.get("bat_type"));
																		String batbrand=String.valueOf(ht.get("bat_brand"));
																		String cusname=(String)ht.get("customer_name");
																		String cusmobile=(String)ht.get("customer_mobilnumber");
																		String supname=String.valueOf(ht.get("support_name"));
																		String suppmobile=String.valueOf(ht.get("support_mobilnumber"));	
																		String city=String.valueOf(ht.get("city"));	
																		String area=String.valueOf(ht.get("area"));	
																		String pincode=String.valueOf(ht.get("pincode"));	
																		String date=String.valueOf(ht.get("creation_date"));
																		String service_need_date=String.valueOf(ht.get("service_need_date"));
																		String agent_name=String.valueOf(ht.get("agent_name"));
																		String order_status=String.valueOf(ht.get("order_status"));
																		String order_reasons=String.valueOf(ht.get("order_reasons"));
																		String order_agent_comments=String.valueOf(ht.get("order_agent_comments"));

																		if(vehtype.equals("0") || vehtype == "0")
																		{
																			vehtype = "";
																		}
																		else
																		{
																			vehtype=vehtype;
																		}
																		if(vehmodel.equals("0") || vehmodel == "0")
																		{
																			vehmodel = "";
																		}
																		else
																		{
																			vehmodel=vehmodel;
																		}
																		%>
																	<tr bgcolor="#DAD0E1">
																		<td  class="insidecontent" align="center"><%=(i+1)%> </td>
																		<td  class="insidecontent" align="left"><%=service_need_date%> </td>
																		<td  class="insidecontent" align="left"><%=cusname%> </td>
																		<td  class="insidecontent" align="left"><%=cusmobile%> </td>
																		<td  class="insidecontent" align="left"><%=suppmobile%> </td>
																		<td  class="insidecontent" align="left"><%=supname%> </td>
																		<td  class="insidecontent" align="left"><%=city%> </td>
																		<td  class="insidecontent" align="left"><%=area%> </td>
																		<td  class="insidecontent" align="left"><%=pincode%> </td>
																		<td  class="insidecontent" align="left"><%=date%> </td>
																		<td  class="insidecontent" align="left"><%=agent_name%> </td>
																		<td  class="insidecontent" align="left"><%=order_status%> </td>
																		<td  class="insidecontent" align="left"><%=order_reasons%> </td>
																		<td  class="insidecontent" align="left"><%=order_agent_comments%> </td>
																		<td  class="insidecontent" align="left">
																		<select style='width:110px;' name="<%=Batchlid%>" id="<%=Batchlid%>" onChange="javascript:Updatevisitorstatus('<%=Batchlid%>')"><option value=''>Update Status</option><option value='Not Called'>Not Called </option><option value='Customer Contacted'>Customer Contacted</option><option value='Customer Not Contacted'>Customer Not Contacted</option><option value='Postponed'>Postpone</option><option value='Repeated'>Repeated</option></select> </td>
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
			<jsp:include page = "../footer.jsp" />
		</td>
	</tr>                           
	<!-- footer Ends Here -->
</td>
</tr>
</table>
<input type="hidden" name="dates" value="<%=strDateOpt%>">
<input type="hidden" name="txtFromDate" value="<%=strFromDate%>">
<input type="hidden" name="txtToDate" value="<%=strToDate%>">
</form>
</center>
</body>
</html>