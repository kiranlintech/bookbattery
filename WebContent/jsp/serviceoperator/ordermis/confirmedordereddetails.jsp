<%--
    Document   : MIS Ordered Details
	File name  : confirmedordereddetails.jsp
    Created on : Feb 18th, 2016
    Author     : Lavanya Chowdary.
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
Vector alist=(Vector)session.getAttribute("sesServiceOrderstatusVector");
LogLevel.DEBUG(1,new Throwable(),"alist: "+alist);

Vector alist1=(Vector)session.getAttribute("sesRetOrderstatusVectorVector");
LogLevel.DEBUG(1,new Throwable(),"alist1: "+alist1);


Hashtable htOptions=(Hashtable)session.getAttribute("sesOptions");
String orderedstatus=(String)htOptions.get("orderstatus"); 
String FromDate=(String)htOptions.get("txtFromDate");
String ToDate=(String)htOptions.get("txtToDate");

String strDateOpt=request.getParameter("dates");
if(strDateOpt==null || strDateOpt.equals(""))
	strDateOpt="current";

String strFromDate=(request.getParameter("txtFromDate")!=null)?request.getParameter("txtFromDate"):"";
String strToDate=(request.getParameter("txtToDate")!=null)?request.getParameter("txtToDate"):"";
String city=(request.getParameter("city")!=null)?request.getParameter("city"):"";
String keyword=(request.getParameter("keyword")!=null)?request.getParameter("keyword"):"";
LogLevel.DEBUG(1,new Throwable(),"keyword: "+keyword);

String fromdatefilter=(request.getParameter("fromdatefilter")!=null)?request.getParameter("fromdatefilter"):"";
String fromtimefilter=(request.getParameter("fromtimefilter")!=null)?request.getParameter("fromtimefilter"):"";
String todatefilter=(request.getParameter("todatefilter")!=null)?request.getParameter("todatefilter"):"";
String totimefilter=(request.getParameter("totimefilter")!=null)?request.getParameter("totimefilter"):"";

String statusfilter1=(request.getParameter("statusfilter")!=null)?request.getParameter("statusfilter"):"";
if(statusfilter1==null || statusfilter1.equals(""))
	statusfilter1="Select Status";
LogLevel.DEBUG(1,new Throwable(),"statusfilter1: "+statusfilter1);

String substatusfilter1=(request.getParameter("substatusfilter")!=null)?request.getParameter("substatusfilter"):"";
if(substatusfilter1==null || substatusfilter1.equals(""))
	substatusfilter1="Select Sub Status";
LogLevel.DEBUG(1,new Throwable(),"substatusfilter1: "+substatusfilter1);

String retselectname1=(request.getParameter("retselectname")!=null)?request.getParameter("retselectname"):"";
if(retselectname1==null || retselectname1.equals(""))
	retselectname1="Select Franchisee";
LogLevel.DEBUG(1,new Throwable(),"retselectname1: "+retselectname1);

String servicetypefilter1=(request.getParameter("servicetypefilter")!=null)?request.getParameter("servicetypefilter"):"";
if(servicetypefilter1==null || servicetypefilter1.equals(""))
	servicetypefilter1="All";
LogLevel.DEBUG(1,new Throwable(),"servicetypefilter1: "+servicetypefilter1);


// Keep all the headings in session
Vector vectHeaderwithord = new Vector();
vectHeaderwithord.add("ORD No");
vectHeaderwithord.add("Cust Name");
vectHeaderwithord.add("Cust Emailid");
vectHeaderwithord.add("Cust Mobile");
vectHeaderwithord.add("Ret Name");
vectHeaderwithord.add("Ret Mobile");
vectHeaderwithord.add("Services Type");
vectHeaderwithord.add("Services Package");
vectHeaderwithord.add("City");
vectHeaderwithord.add("Price");
vectHeaderwithord.add("Discount Price");
vectHeaderwithord.add("Ordered Status");
vectHeaderwithord.add("Ordered Reasons");
vectHeaderwithord.add("Agent Comments");
vectHeaderwithord.add("Ordered By");
vectHeaderwithord.add("Date");
vectHeaderwithord.add("Postponed Reason");
session.removeAttribute("sesHeaderwithcat");
session.setAttribute("sesHeaderwithcat",vectHeaderwithord);


// put the body data in session
session.removeAttribute("sesBodywithcat");
session.setAttribute("sesBodywithcat",alist);

// Put the tile in session
session.removeAttribute("sesTitlewithcat");
session.setAttribute("sesTitlewithcat",orderedstatus);
session.setAttribute("sesFromDate",FromDate);
session.setAttribute("sesToDate",ToDate);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<script src="https://apis.google.com/js/client.js" type="text/javascript"> </script>

<link rel="stylesheet" href="/bookbattery/css/bootstrap.min.css" />
 <link rel="stylesheet" href="/bookbattery/css/bootstrap-multiselect.css" />

<!-- Including Open Sans Condensed from Google Fonts -->
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans+Condensed:300,700,300italic" />


<title>BookBattery.com-India's No.1 Automobile Battery Store</title>
<meta name='Title' content='Neeru Chettu' />
<meta name='description' content="Neeru Chettu" />
<meta name='keywords' content='Neeru Chettu' />

<script src="/bookbattery/js/jquery.min.js"></script>
<script src="/bookbattery/js/bootstrap.min.js"></script>
<script src="/bookbattery/js/bootstrap-multiselect.js"></script>

<link href="../../../css/bookbattery.css" rel="stylesheet" type="text/css" />

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../images/favicon.png" type="image/x-icon">
<style type='text/css'>

.divpostponed{left:57%;top:30%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left; border:6px solid #424242; border-radius: 6px 6px 6px 6px;position:absolute;padding: 1px;display: none;z-index:0;background-color: white;}

.divpostponed1{left:50.5%;top:30%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left; border:6px solid #424242; border-radius: 6px 6px 6px 6px;position:absolute;padding: 1px;display: none;z-index:0;background-color: white;}

.divmobile{left:35.5%;top:30%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left; border:6px solid #424242; border-radius: 6px 6px 6px 6px;position:absolute;padding: 1px;display: none;z-index:60;background-color: white;}

table.tableizer-table {
	border: 3px solid #a7bc7a; 
	font-family:Verdana;font-size:12px;
	font-size: 12px;
} 
.tableizer-table td {
	padding: 1px;
	margin: 0px;
	border: 1px solid #a7bc7a;
}
.tableizer-table th {
	background-color: #a7bc7a; 
	color: #FFF;
	font-weight: bold;
}
</style>
<script language=javascript src="/bookbattery/js/datepicker.js"></script>
<script src="/bookbattery/js/date-time-picker/jquery.timepicker.min.js"></script>
<link rel="stylesheet" href="/bookbattery/js/date-time-picker/jquery.timepicker.css"/>

<script type="text/javascript" src="/bookbattery/js/date-time-picker/lib/bootstrap-datepicker.js"></script>
<link rel="stylesheet" type="text/css" href="/bookbattery/js/date-time-picker/lib/bootstrap-datepicker.css" />
<script src="http://jonthornton.github.io/Datepair.js/dist/datepair.js"></script>
<script src="http://jonthornton.github.io/Datepair.js/dist/jquery.datepair.js"></script>

<script language="javascript">

$(document).ready(function()
{
$('#div1').scrollTop($('#div1')[0].scrollHeight);
});

function onClickExportToExcel()
{
	strUrl = "../../../servlet/BatteryExcelandPdfServlet?hidWhatToDo=ExportToExcelorder";
	window.open(strUrl,"ExcelOpen","height=450,width=530,toobar=0,location=0,directories=0,status=yes,menubar=0,scrollbars=yes,resizable=1"); 
}

function onClickExportToPdf()
{
	strUrl = "../../../servlet/BatteryExcelandPdfServlet?hidWhatToDo=ExportToPDForder";
	window.open(strUrl,"ExcelOpen","height=450,width=530,toobar=0,location=0,directories=0,status=yes,menubar=0,scrollbars=yes,resizable=1"); 
}

/** Function to get sub status options **/
var temp_break="0";
function getsubstatusoptions()
{
	//alert(22);
	var statusfilter = document.misservicesstatus.statusfilter.value;
	//alert(statusfilter);
	var substatusfilter = $("#substatusfilter").val();
	//alert(substatusfilter);
	var errmsg1="";
	if(substatusfilter=="0")
	{
		temp_break="1";
	}
	else
	{
		 errmsg1="";
	}

	if(statusfilter=="" || statusfilter=="0" || statusfilter=="Select Status" || statusfilter=="confirmed")
	{
		errMsg ="<option value='0'>&lt;-- No Options--&gt;</option>";
	}
	else if(statusfilter=="Customer Contacted")
	{
		errMsg =errmsg1+"<option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Sub Status--&gt;</option><option value='installed' >Installed</option><option value='In Process' >In Process</option><option value='Not Installed' >Not Installed</option><option value='cancelled-franchisee-offbushrs' >Not Installed - Cancelled-Franchisee-OffBusHrs</option><option value='cancelled-franchisee-denied' >Not Installed - Cancelled-Franchisee-Denied</option><option value='cancelled-franchisee-notresponded' >Not Installed - Cancelled-Franchisee-NotResponded</option><option value='cancelled-franchisee-modeloutofstock' >Not Installed - Cancelled-Franchisee-ModelOutofStock</option><option value='cancelled-customer' >Not Installed - Cancelled-Customer</option><option value='cancelled-regenerated' >Not Installed - Cancelled-Regenerated</option>";
	}
	else if(statusfilter=="Customer Not Contacted")
	{
		errMsg =errmsg1+"<option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Sub Status--&gt;</option><option value='Phone Busy' >Phone Busy</option><option value='Phone Not Reachable'>Phone Not Reachable</option><option value='Phone Switched Off'>Phone Switched Off</option>";
	}
	else if(statusfilter=="Postponed" || statusfilter=="postponed")
	{
		errMsg =errmsg1+"<option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Sub Status--&gt;</option><option value='Customer is not picking the Call' >Customer is not picking the Call</option><option value='Customer Number Busy'>Customer Number is Busy</option><option value='Customer Number is Not Reachable' >Customer Number is Not Reachable</option><option value='Customer Number Switched Off' >Customer Number is Switched Off</option><option value='Customer is Out of Station' >Customer is Out of Station</option><option value='Customer is Not Responding to our Calls' >Customer is Not Responding to our Calls</option><option value='Customer need Installation Today' >Customer need Installation Tomorrow</option><option value='Need to check with franchisee whether the battery is currently available' >Franchisee is not having the Stock</option><option value='Customer Car Old battery is Working Fine need installation today' >Customer Car Old battery is Working Fine Presently</option><option value='Car Old Battery is working Fine He need installation Today' >Car Old Battery is working Fine, He donot need installation Now</option><option value='Yesterdays After Business Hour Order' >As it is not business hours, we will install the battery tomorrow</option><option value='Pitstop is So Far Customer will collect the battery whenever he is free' >Pitstop is So Far, Customer will collect the battery whenever he is free</option><option value='Customer need installation today' >Customer is busy today, he need installation later</option><option value='Customer need latest Manufacturing Battery' >Customer need latest Manufacturing Battery</option><option value='Order Status is not yet confirmed from Franchisee or Customer' >Order Status is not yet confirmed from Franchisee or Customer</option><option value='Customer will collect the battery today' >Customer will collect the battery tomorrow</option><option value='Customer will collect the battery today' >Customer Bike Old battery is Working Fine Presently</option><option value='Pitstop was on Leave Yesterday Need to process the order today' >Franchisee is on Leave Today</option>";
	}
	if(statusfilter=="Repeated")
	{
		errMsg =errmsg1+"<option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Sub Status--&gt;</option><option value='Customer Has Placed Order For Twice' >Customer Has Placed Order For Twice</option>";
	}


	$('#substatusfilter').multiselect('destroy');
	
	document.getElementById("substatusfilter").innerHTML=""; 
	document.getElementById("substatusfilter").innerHTML=errMsg 
	
	$('#substatusfilter').multiselect({
		includeSelectAllOption: true
	});
	
	if(temp_break=="0")
	{
		var data="<%=substatusfilter1%>";
		var dataarray=data.split(",");
		$("#substatusfilter").val(dataarray);
		$("#substatusfilter").multiselect("refresh");
		
		
		var data2="<%=servicetypefilter1%>";
		var data2array=data2.split(",");
		$("#servicetypefilter").val(data2array);
		$("#servicetypefilter").multiselect("refresh");
		temp_break="1";
	}

}

/** Function to change visitor status **/
function Updatevisitorstatus(orderid,ordernumber,state,city,area,cusmobile,PaymentMode)
{
	var orderedstatus = document.getElementById(orderid).value;
	
	//alert("inside updatevisitorstatus"+state);
	
	if(orderedstatus=="Customer Contacted")
	{
		CustomerContacted(orderid,ordernumber,orderedstatus,state,city,cusmobile,PaymentMode)
	}
	else if(orderedstatus=="Customer Not Contacted")
	{
		CustomernotContacted(orderid,ordernumber,orderedstatus)
	}
	else if(orderedstatus=="Postponed" || orderedstatus=="postponed")
	{
		Postponedvis(orderid,ordernumber,orderedstatus)
	}
	else if(orderedstatus=="Repeated")
	{
		Repeatedvis(orderid,ordernumber,orderedstatus)
	}
	else if(orderedstatus=="confirmed")
	{
		//alert("inside updatevisitorstatus confirmed"+state);
		Ordertoretailer(orderid,ordernumber,orderedstatus,state,city,area,cusmobile,PaymentMode)
	}
	
	else if(orderedstatus=="")
	{
	}
	else
	{
		var xmlhttp= "";
		var resp= "";
		var url ="../../../servlet/MISOperatorServiceDetails?hidWhatToDo=updateorderstatus&orderid="+orderid+"&ordernumber="+ordernumber+"&orderstatus="+orderedstatus;
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
					errMsg ="<table border='0' width='300px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closemobdiv();'><img src=\"../../../images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='300px' height='2px' bgcolor='#FFFFFF' valign'top'><tr bgcolor='#FFFFFF'><td align='justify' bgcolor='#FFFFFF' style='font-family:Verdana;font-size:11px;color:#000000;'>"+resp+"</td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='getupdatedorders();' class='button4'><br></td></tr><tr height='15'></tr></table>";
					document.getElementById("divpostponed").innerHTML=""; 
					document.getElementById("divpostponed").style.display='block';
					document.getElementById("divpostponed").innerHTML=errMsg
					
				}
			}			
		}
			xmlhttp.open("GET",url, true);		
			xmlhttp.send();	
		
	}

}


/** Function to show options to select if customer postponed **/

function Postponedvis(orderid,ordernumber,orderstatus)
{
	errMsg ="<table  width='340' cellspacing='10' cellpadding='0' bgcolor='#FFFFFF' border='0'><tr><td><table width='340' bgcolor='#FFFFFF' valign='top' border='0'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobdiv();\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr><td width='80%' colspan='3' align='center'><font size='2' color='#FF8C00'>Please Select Postpone Date&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></td><td width='20%'></td></tr><tr><td height='10' width='80%'></td><td width='20%'></td></tr><tr><td width='80%' align='center'><input type='ext' name='postponedate' class='insidecontent' readonly  onChange='CheckDate(this)' onKeyDown='FormatDate(this,  window.event.keyCode,'down')' onKeyUp='FormatDate(this, window.event.keyCode,'up')' value='' size='10' maxlength='10'  style='width:195px;height:35px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' /></td><td width='20%' align='center'><img src='../../../images/calender.jpg' valign='bottom' align='middle' style='cursor:hand;margin-right:10px;' onclick=\"javascript:displayDatePicker('postponedate', this);\" height='25' ></td><td></td></tr><tr><td height='5' width='80%'></td><td height='5' width='20%'></td></tr><tr><td width='63%' class='insidecontent' align='center' style='width:155px;  bgcolor:#FFF;'><div class='styled-select' align='center'><select name='orderreason'  id='orderreason' class='insidecontent' STYLE='width: 200px;' align='center' onChange='javascript:setagentcomments()'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Reason--&gt;</option><option value='High Priority' >High Priority</option><option value='Customer is not picking the Call' >Customer is not picking the Call</option><option value='Customer Number Busy'>Customer Number is Busy</option><option value='Customer Number is Not Reachable' >Customer Number is Not Reachable</option><option value='Customer Number Switched Off' >Customer Number is Switched Off</option><option value='Customer is Out of Station' >Customer is Out of Station</option><option value='Customer is Not Responding to our Calls' >Customer is Not Responding to our Calls</option><option value='Customer need Installation Today' >Customer need Installation Tomorrow</option><option value='Need to check with franchisee whether the battery is currently available' >Franchisee is not having the Stock</option><option value='Customer Car Old battery is Working Fine need installation today' >Customer Car Old battery is Working Fine Presently</option><option value='Car Old Battery is working Fine He need installation Today' >Car Old Battery is working Fine, He donot need installation Now</option><option value='Yesterdays After Business Hour Order' >As it is not business hours, we will install the battery tomorrow</option><option value='Pitstop is So Far Customer will collect the battery whenever he is free' >Pitstop is So Far, Customer will collect the battery whenever he is free</option><option value='Customer need installation today' >Customer is busy today, he need installation later</option><option value='Customer need latest Manufacturing Battery' >Customer need latest Manufacturing Battery</option><option value='Order Status is not yet confirmed from Franchisee or Customer' >Order Status is not yet confirmed from Franchisee or Customer</option><option value='Customer will collect the battery today' >Customer will collect the battery tomorrow</option><option value='Customer will collect the battery today' >Customer Bike Old battery is Working Fine Presently</option><option value='Pitstop was on Leave Yesterday Need to process the order today' >Franchisee is on Leave Today</option></select></div></td></tr><tr><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;Message</td></tr><tr height='10'><td width='100%' align='center'><textarea name='cusmessage' id='cusmessage' rows='6' cols='30'></textarea></td></tr><tr>&nbsp;&nbsp;&nbsp;<td  width='80%' align='center'><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:updatecustcontactedstatus('"+orderid+"','"+ordernumber+"','"+orderstatus+"');\"></td><td width='20%'></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr height='16'><td width='80%' align='center' class='subheading' id='displayrefinederrormsg1'></td><td width='20%'></td></tr><tr><td height='10'></td></tr></table></td></tr></table>";
	document.getElementById("divpostponed").innerHTML=""; 
	document.getElementById("divpostponed").style.display='block';
	document.getElementById("divpostponed").innerHTML=errMsg
}

/** Function to show options to select if CustomerContacted **/

/*function CustomerContacted(orderid,ordernumber,orderstatus)
{
	errMsg ="<table  width='340' cellspacing='10' cellpadding='0' bgcolor='#FFFFFF' border='0'><tr><td><table width='340' bgcolor='#FFFFFF' valign='top' border='0'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobdiv();\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr><td height='5' width='80%'></td><td height='5' width='20%'></td></tr><tr><td width='63%' class='insidecontent' align='center' style='width:155px;  bgcolor:#FFF;'><div class='styled-select' align='center'><select name='orderreason'  id='orderreason' class='insidecontent' STYLE='width: 200px;' align='center' onChange='javascript:setagentcomments('"+orderid+"','"+ordernumber+"','"+orderstatus+"','"+state+"','"+city+"','"+batterytype+"','"+cusmobile+"','"+PaymentMode+"','"+order_type+"')\'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Reason--&gt;</option><option value='installed' >Installed</option><option value='In Process' >In Process</option><option value='Not Installed' >Not Installed</option><option value='cancelled-franchisee-offbushrs' >Not Installed - Cancelled-Franchisee-OffBusHrs</option><option value='cancelled-franchisee-denied' >Not Installed - Cancelled-Franchisee-Denied</option><option value='cancelled-franchisee-notresponded' >Not Installed - Cancelled-Franchisee-NotResponded</option><option value='cancelled-franchisee-modeloutofstock' >Not Installed - Cancelled-Franchisee-ModelOutofStock</option><option value='cancelled-customer' >Not Installed - Cancelled-Customer</option><option value='cancelled-regenerated' >Not Installed - Cancelled-Regenerated</option></select></div></td></tr><tr><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;Message</td></tr><tr height='10'><td width='100%' align='center'><textarea name='cusmessage' id='cusmessage' rows='6' cols='30'></textarea></td></tr><tr>&nbsp;&nbsp;&nbsp;<td  width='80%' align='center'><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:updatecustcontactedstatus('"+orderid+"','"+ordernumber+"','"+orderstatus+"');\"></td><td width='20%'></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr height='16'><td width='80%' align='center' class='subheading' id='displayrefinederrormsg1'></td><td width='20%'></td></tr><tr><td height='10'></td></tr></table></td></tr></table>";
	document.getElementById("divpostponed").innerHTML=""; 
	document.getElementById("divpostponed").style.display='block';
	document.getElementById("divpostponed").innerHTML=errMsg
}
**/
function getcities()
{
	
	$.ajax({					 
		type: "GET",
	url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getcity",
	data: {state:$("#cusstate").val()},
	success: function(data)
	{	
		//alert(data);
		$("#cuscity").html(data);
	}
	});

}

function getareas()
{
	
	$.ajax
	({
		type: "GET",
		url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getarea",
		data: {city: $("#cuscity").val() },
		success: function(data)
		{	
			$("#cusarea").html(data);
		}
	});
}

function getbattype()
{
	
//alert(22);

if($("#servicetype").val()!= 0)
{
	//alert(22);
	
    if($("#servicetype").val()=="Jump Start")
    {
        $("#battype").html("<option value='0'>Select Battery Type</option><option value='Car Batteries'>Car Batteries</option><option value='Bike Batteries'>Bike Batteries</option>");
        $("#product_type").focus();
    }
	else
	{
        $("#battype").html("<option value='0'>Select Battery Type</option><option value='Car Batteries'>Car Batteries</option><option value='Inverter Batteries'>Inverter Batteries</option><option value='Bike Batteries'>Bike Batteries</option>");
        $("#product_type").focus();
	}
}
else
{

}

}

function getservicepricedetls()
{
	var servicetype = document.getElementById("servicetype").value; 
	var battype = document.getElementById("battype").value; 
	var serviceplace = document.getElementById("serviceplace").value; 
 	
		$.ajax
		({
			type: "GET",
			url: "/bookbattery/servlet/MISOperatorServiceDetails?hidWhatToDo=getservicepricedetls",
			data: {servicetype: servicetype,battype: battype,serviceplace: serviceplace},
			success: function(data)
			{			
				//alert(data);
				document.getElementById("serviceprice").value=data;
			}
		});
}

function CustomerContacted(orderid,ordernumber,orderstatus,state,city,cusmobile,PaymentMode)
{
	var PaymentModeClass="";
	if(PaymentMode=="Online")
	{
		PaymentModeClass="hide";
	}
	else
	{
		// DO Noting
	}
			$.ajax
			({
			type: "GET",
			url: "/bookbattery/servlet/MISOperatorBatteryDetails?hidWhatToDo=getretailerstoorder",
			data: {state: state,city: city },
			success: function(data)
			{	
				$("#retailertoorder").html(data)
			}
			});

			$.ajax
			({
			type: "GET",
			url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getarea",
			data: {city: city },
			success: function(data)
			{	
				$("#cusarea").html(data)
			}
			});
			
			 $.ajax({					 
					type: "GET",
					url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getstate",
					success: function(data)
					{	
						//alert(data);
						$("#cusstate").html(data);
						//alert(state);
						$('#cusstate').val(state);						
					}
				});

			 errMsg ="<table  width='340' cellspacing='10' cellpadding='0' bgcolor='#FFFFFF' border='0'><tr><td><table width='340' bgcolor='#FFFFFF' valign='top' border='0'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobdiv();\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr><td height='5' width='80%'></td><td height='5' width='20%'></td></tr><tr><td width='63%' class='insidecontent' align='center' style='width:155px;  bgcolor:#FFF;'><div class='styled-select' align='center'><select name='orderreason'  id='orderreason' class='insidecontent' STYLE='width: 200px;' align='center' onChange=\"javascript:setagentcomments('"+orderid+"','"+ordernumber+"','"+orderstatus+"','"+state+"','"+city+"','"+cusmobile+"','"+PaymentMode+"')\"><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Reason--&gt;</option><option value='installed' >Installed</option><option value='In Process' >In Process</option><option value='Not Installed' >Not Installed</option><option value='cancelled-franchisee-offbushrs' >Not Installed - Cancelled-Franchisee-OffBusHrs</option><option value='cancelled-franchisee-denied' >Not Installed - Cancelled-Franchisee-Denied</option><option value='cancelled-franchisee-notresponded' >Not Installed - Cancelled-Franchisee-NotResponded</option><option value='cancelled-franchisee-modeloutofstock' >Not Installed - Cancelled-Franchisee-ModelOutofStock</option><option value='cancelled-customer' >Not Installed - Cancelled-Customer</option><option value='cancelled-regenerated' >Not Installed - Cancelled-Regenerated</option><option value='Not Installed-Customer-Denied-After going to his place'>Not Installed-Customer-Denied-After going to his place</option><option value='Forwarded from BookBattery'>Forwarded from BookBattery</option><option value='Regenerated to Another Retailer'>Regenerated to Another Retailer</option><option class='"+PaymentModeClass+"' value='Not Confirmed Order - Customer Confirmed to Place Order'>Not Confirmed Order - Customer Confirmed to Place Order</option><option value='Update'>Update</option></select></div></td></tr><tr id='rettoorder1' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Select&nbsp;Retailer</td></tr><tr height='10' id='rettoorder' style='display:none;'><td width='100%' align='center'><select onChange=\"javascript:setagentcomments_retailer();\" name='retailertoorder'  id='retailertoorder' class='insidecontent' STYLE='width: 200px;' align='center' ><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Retailer--&gt;</option></select></td></tr>	<tr height='10' id='modifybatdetls13' readonly><td width='100%' align='center'><input type='text' value='"+state+"' name='constate' id='constate' style='display:none';></td></tr><tr height='10' id='modifybatdetls14' readonly><td width='100%' align='center'><input type='text' value='"+city+"' name='concity' id='concity' style='display:none';></td></tr><tr id='modifybatdetls4' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Select&nbsp;Service&nbsp;Type</td></tr><tr height='10' id='modifybatdetls5' style='display:none;'><td width='100%' align='center'><select name='servicetype'  id='servicetype' class='insidecontent' STYLE='width: 200px;' align='center' onchange='javascript:getbattype();'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Service Type--&gt;</option><option value='Health Check'>Battery Health Checkup</option><option value='Recharge'>Battery Recharge</option><option value='Jump Start'>Jump Start</option></select></td></tr><tr id='modifybatdetls6' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Select&nbsp;Battery&nbsp;Type</td></tr><tr height='10' id='modifybatdetls7' style='display:none;'><td width='100%' align='center'><select name='battype'  id='battype' class='insidecontent' STYLE='width: 200px;' align='center'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Battery Type--&gt;</option></select></td></tr><tr id='modifybatdetls8' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Select Service Place </td></tr><tr height='10' id='modifybatdetls9' style='display:none;'><td width='100%' align='center'><select name='serviceplace'  id='serviceplace' class='insidecontent' STYLE='width: 200px;' align='center' onChange=\"javascript:getservicepricedetls();\"><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Service Place--&gt;</option><option value='store' >At Store</option><option value='within_10km' >At Door Step (With in 10 KM)</option></select></td></tr><tr id='modifybatdetls91' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Service Price</td></tr><tr height='10' id='modifybatdetls11' style='display:none;'><td width='100%' align='center'><input type='text' value='' name='serviceprice' id='serviceprice'></td></tr>           <tr  class='Quantity_tr' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>&nbsp;Enter&nbsp;Quantity</td> </tr> <tr  class='Quantity_tr' style='display:none;'> <td width='100%' align='center'>&nbsp;<input class='insidecontent' type='text' autocomplete='off' name='Quantity' id='Quantity' value='1' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='50'></td></tr>                   <tr id='confirmord1' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Customer Name</td></tr><tr height='10' id='confirmord2' style='display:none;'><td width='100%' align='center'><input type='text'  name='cusname' id='cusname'></td></tr><tr id='confirmord3' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Customer Email ID</td></tr><tr height='10' id='confirmord4' style='display:none;'><td width='100%' align='center'><input type='text' name='cusemailid' id='cusemailid'></td></tr><tr id='confirmord5' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Customer Address1</td></tr><tr height='10' id='confirmord6' style='display:none;'><td width='100%' align='center'><input type='text' name='cusaddr1' id='cusaddr1'></td></tr><tr id='confirmord7' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Customer Address2</td></tr><tr height='10' id='confirmord8' style='display:none;'><td width='100%' align='center'><input type='text'  name='cusaddr2' id='cusaddr2'></td></tr><tr id='confirmord91' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Select&nbsp;State</td></tr><tr height='10' id='confirmord101' style='display:none;'><td width='100%' align='center'><select name='cusstate' value='"+state+"'  id='cusstate' onchange =\"javascript:getcities();\" class='insidecontent' STYLE='width: 200px;' align='center'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select State--&gt;</option></select></td></tr><tr id='confirmord92' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Select&nbsp;City</td></tr><tr height='10' id='confirmord102' style='display:none;'><td width='100%' align='center'><select name='cuscity' value='"+city+"'  id='cuscity' onchange =\"javascript:getareas();\" class='insidecontent' STYLE='width: 200px;' align='center'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select City--&gt;</option></select></td></tr><tr id='confirmord9' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Select&nbsp;Area</td></tr><tr height='10' id='confirmord10' style='display:none;'><td width='100%' align='center'><select name='cusarea'  id='cusarea' class='insidecontent' STYLE='width: 200px;' align='center'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Area--&gt;</option></select></td></tr> <tr id='confirmord11' style='display:none;' ><td width='63%' class='insidecontent' align='center' style='width:155px;  bgcolor:#FFF;'><div class='styled-select' align='center'><select onChange='javascript:setagentcomments_payment();' name='payment_mode'  id='payment_mode' class='insidecontent' STYLE='width: 200px;' align='center' ><option value='0' >&lt;-- Select Payment Method--&gt;</option> <option value='Cash' >Cash</option> <option value='Credit Card' >Credit Card</option><option value='Debit Card' >Debit Card</option>  <option value='Cheque'>Cheque</option><option value='Online Payment After Fitment'>Online Payment After Fitment</option><option value='UPI' >UPI</option> </select></div></td></tr><tr id='ratingid' style='display:none;' ><td width='63%' class='insidecontent' align='center' style='width:155px;  bgcolor:#FFF;'><div class='styled-select' align='center'><select name='rating'  id='rating' class='insidecontent' STYLE='width: 200px;' align='center' ><option value='0' >&lt;-- Select Satisfaction Type--&gt;</option> <option value='Satisfied' >Satisfied</option> <option value='Not Satisfied' >Not Satisfied</option></select></div><div class='styled-select' align='center'><select name='payment_collected'  id='payment_collected' class='insidecontent' STYLE='width: 200px;' align='center' ><option value='0' >&lt;-- Select Payment Collected--&gt;</option> <option value='Dealer' >Collected by Dealer</option> <option value='Asistmi' >Collected by Asitmi</option></select></div></td></tr><tr><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;Message</td></tr><tr height='10'><td width='100%' align='center'><textarea name='cusmessage' id='cusmessage' rows='6' cols='30'></textarea></td></tr><tr>&nbsp;&nbsp;&nbsp;<td  width='80%' align='center'><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:updatecustcontactedstatus('"+orderid+"','"+ordernumber+"','"+orderstatus+"','"+cusmobile+"');\"></td><td width='20%'></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr height='16'><td width='80%' align='center' class='subheading' id='displayrefinederrormsg1'></td><td width='20%'></td></tr><tr><td height='10'></td></tr></table></td></tr></table>";

	document.getElementById("divpostponed").innerHTML=""; 
	document.getElementById("divpostponed").style.display='block';
	document.getElementById("divpostponed").innerHTML=errMsg
}

/** Function to show options to select if CustomernotContacted **/
function CustomernotContacted(orderid,ordernumber,orderstatus)
{
	errMsg ="<table  width='340' cellspacing='10' cellpadding='0' bgcolor='#FFFFFF' border='0'><tr><td><table width='340' bgcolor='#FFFFFF' valign='top' border='0'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobdiv();\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr><td width='63%' class='insidecontent' align='center' style='width:155px;  bgcolor:#FFF;'><div class='styled-select' align='center'><select name='orderreason'  id='orderreason' class='insidecontent' STYLE='width: 200px;' align='center' onChange='javascript:setagentcomments()'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Reason--&gt;</option><option value='Phone Busy' >Phone Busy</option><option value='Phone Not Reachable'>Phone Not Reachable</option><option value='Phone Switched Off'>Phone Switched Off</option></select></div></td></tr><tr><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;Message</td></tr><tr height='10'><td width='100%' align='center'><textarea name='cusmessage' id='cusmessage' rows='6' cols='30'></textarea></td></tr><tr>&nbsp;&nbsp;&nbsp;<td  width='80%' align='center'><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:updatecustcontactedstatus('"+orderid+"','"+ordernumber+"','"+orderstatus+"');\"></td><td width='20%'></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr height='16'><td width='80%' align='center' class='subheading' id='displayrefinederrormsg1'></td><td width='20%'></td></tr><tr><td height='10'></td></tr></table></td></tr></table>";
	document.getElementById("divpostponed").innerHTML=""; 
	document.getElementById("divpostponed").style.display='block';
	document.getElementById("divpostponed").innerHTML=errMsg
}

/** Function to show options to select if Repeatedvis **/
function Repeatedvis(orderid,ordernumber,orderstatus)
{
	errMsg ="<table  width='340' cellspacing='10' cellpadding='0' bgcolor='#FFFFFF' border='0'><tr><td><table width='340' bgcolor='#FFFFFF' valign='top' border='0'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobdiv();\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr><td height='5' width='80%'></td><td height='5' width='20%'></td></tr><tr><td width='63%' class='insidecontent' align='center' style='width:155px;  bgcolor:#FFF;'><div class='styled-select' align='center'><select name='orderreason'  id='orderreason' class='insidecontent' STYLE='width: 200px;' align='center' onChange='javascript:setagentcomments()'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Reason--&gt;</option><option value='Customer Has Placed Order For Twice' >Customer Has Placed Order For Twice</option></select></div></td></tr><tr><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;Message</td></tr><tr height='10'><td width='100%' align='center'><textarea name='cusmessage' id='cusmessage' rows='6' cols='30'></textarea></td></tr><tr>&nbsp;&nbsp;&nbsp;<td  width='80%' align='center'><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:updatecustcontactedstatus('"+orderid+"','"+ordernumber+"','"+orderstatus+"');\"></td><td width='20%'></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr height='16'><td width='80%' align='center' class='subheading' id='displayrefinederrormsg1'></td><td width='20%'></td></tr><tr><td height='10'></td></tr></table></td></tr></table>";
	document.getElementById("divpostponed").innerHTML=""; 
	document.getElementById("divpostponed").style.display='block';
	document.getElementById("divpostponed").innerHTML=errMsg
}

function Ordertoretailer(orderid,ordernumber,orderstatus,state,city,area,cusmobile,PaymentMode)
{

//alert("inside OrdertoRetailer"+state);

		errMsg ="<table  width='340' cellspacing='10' cellpadding='0' bgcolor='#FFFFFF' border='0'><tr><td><table width='340' bgcolor='#FFFFFF' valign='top' border='0'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobdiv();\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr><td width='63%' class='insidecontent' align='center' style='width:155px;  bgcolor:#FFF;'><div class='styled-select' align='center'><select name='retailer'  id='retailer' class='insidecontent' STYLE='width: 300px;margin-left: 31px;' align='center'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Retailer--&gt;</option</select></div></td></tr><tr height='10'><td width='100%' align='center'><textarea name='cusarea' id='cusarea' rows='2' cols='30'></textarea></td></tr><tr>&nbsp;&nbsp;&nbsp;<td  width='80%' align='center'><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:updatecustcontactedstatus('"+orderid+"','"+ordernumber+"','"+orderstatus+"');\"></td><td width='20%'></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr height='16'><td width='80%' align='center' class='subheading' id='displayrefinederrormsg1'></td><td width='20%'></td></tr><tr><td height='10'></td></tr></table></td></tr></table>";
		document.getElementById("divpostponed").innerHTML=""; 
		document.getElementById("divpostponed").style.display='block';
		document.getElementById("divpostponed").innerHTML=errMsg	

		$.ajax
		({
			type: "GET",
			url: "/bookbattery/servlet/MISOperatorBatteryDetails?hidWhatToDo=getretailerstoorder",
			data: {state: state,city: city },
			success: function(data)
			{	
				//alert(data);
				$("#retailer").html(data)
			}
		});
		
		document.misservicesstatus.cusarea.value=area;
		
}

/** Function to change visitor status and reason **/
function updatecustcontactedstatus(orderid,ordernumber,orderstatus,cusmobile)
{

		var iChars3 = "`~!@#$%^&*()+=[]\\\';/{}|\":<>?";
		var dot=".";
		var SMSURL = "";
		var shorturl = "";
		var payment_mode = ""; 
		var rating = "";
        var paymentcollected = "";
		var retailertoorder = "";
		var cusarea="";	
		var Quantity = "";
		var cusstate = ""; 
		var cuscity = ""; 
		var servicetype= "";
		var battype= "";
		var serviceplace= "";
		var serviceprice = "";
		var cusname="";
		var cusemailid="";
		var cusaddr1="";
		var cusaddr2="";
		var cusarea="";	

		

		if(orderstatus=="confirmed")
		{
			var orderreason = "Confirmed Order To Retailer";
			var cusmessage = "";
			//alert(orderreason);
			//alert(orderstatus);
		}
		else
		{
			
		var orderreason = document.misservicesstatus.orderreason.value;
		var cusmessage = document.misservicesstatus.cusmessage.value;
			
		if(orderreason == "" || orderreason == "0")
		 {
			errMsg ="<font color='#9B5BDD'>Please Select Reason</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.misservicesstatus.orderreason.focus();
			return ;
		 }
		
		/** validations starts here for agent message **/
			if(cusmessage == "")
		 {
			errMsg ="<font color='#9B5BDD'>Please Enter Message</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.misservicesstatus.cusmessage.focus();
			return ;
		 }
		  if (document.misservicesstatus.cusmessage.value.indexOf(dot)==0 )
		 {
				errMsg ="<font color='#9B5BDD'>Message should not start with dot</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misservicesstatus.cusmessage.focus();
				return ;
		 }
		if (/[a-z][A-Z]{2}/i.test(document.misservicesstatus.cusmessage.value) != true) 
			{
			  errMsg ="<font color='#9B5BDD'>Please enter atleast 3 Charaters together in the Message Field.</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misservicesstatus.cusmessage.focus();
				return ;
			}
		}
		if(orderstatus=="postponed" || orderstatus=="Postponed")
		{
			var postponedate = document.misservicesstatus.postponedate.value;
			if(postponedate == "")
			 {
				errMsg ="<font color='#9B5BDD'>Please select postpone date</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				return ;
			 }
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
		
		else if(orderstatus=="confirmed")
		{
			//alert(22);
			retailertoorder = document.misservicesstatus.retailer.value;
			cusarea = document.getElementById("cusarea").value;
			
			//alert(retailertoorder);

			if(retailertoorder == "" || retailertoorder == "0" || retailertoorder == "default" || retailertoorder == "defaultss")
			{
				errMsg ="<font color='#9B5BDD'>Please Select Retailer</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misservicesstatus.retailer.focus();
				return ;
			}
			
			if(cusarea == "" || cusarea == "0"  || cusarea == "default"  || cusarea == "defaultss" )
			{
				errMsg ="<font color='#9B5BDD'>Please Enter Customer Area</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misbatstatus.cusarea.focus();
				return ;
			}

		 }
		
		else if(orderstatus=="Customer Contacted" && orderreason=="installed")
		{
			baseurl="http://www.BookBattery.com/bookbattery/";
			baseurl1="http://www.BookBattery.com/bookbattery/";
			//alert(baseurl);
			//alert("orderid:"+orderid);
			//var SMSURL="http://www.BookBattery.com/bookbattery/jsp/rating.jsp?phone=9108501361&email=rathaiahtulabandhula@gmail.com&ordernumber=ORDB4813098B";
			
			 SMSURL=baseurl+"jsp/rating.jsp?ordernumber="+ordernumber;
			 SMSURL1=baseurl1+"jsp/rating.jsp?ordernumber="+ordernumber;
			
			//alert(SMSURL);

			shorturl = document.getElementById("result").value; 
			shorturl1 = document.getElementById("result1").value; 

			if(shorturl =="" || shorturl == undefined|| shorturl == "undefined")
			{
				//makeRequest(SMSURL,orderid,ordernumber,orderstatus,cusmobile);
				//return;
			}
			else
			{
			
			}
			if(shorturl1 =="" || shorturl1 == undefined|| shorturl1 == "undefined" || (shorturl1==shorturl))
			{
				//alert(22);
				//makeRequest1(SMSURL1,orderid,ordernumber,orderstatus,cusmobile);
				//return;
			}
			else
			{
			
			}
			payment_mode = document.misservicesstatus.payment_mode.value;
			rating = document.misservicesstatus.rating.value;
            paymentcollected = document.misservicesstatus.payment_collected.value;
			//order_type = document.misservicesstatus.order_type.value;
			if(payment_mode == "" || payment_mode == "0" || payment_mode == "default" || payment_mode == "defaultss")
			{
				errMsg ="<font color='#9B5BDD'>Please Select Payment Mode</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misservicesstatus.payment_mode.focus();
				return ;
			}
			if(rating == "" || rating == "0" || rating == "default" || rating == "defaultss")
			{
				errMsg ="<font color='#9B5BDD'>Please Select Satisfaction Type</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misservicesstatus.rating.focus();
				return ;
			}
            
            if(paymentcollected == "" || paymentcollected == "0" || paymentcollected == "default" || paymentcollected == "defaultss")
			{
				errMsg ="<font color='#9B5BDD'>Please Select Payment Collected</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misbatstatus.payment_collected.focus();
				return ;
			}
			
			/* if(order_type == "" || order_type == "0" || order_type == "default" || order_type == "defaultss")
			{
				errMsg ="<font color='#9B5BDD'>Please Select Type</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misservicesstatus.order_type.focus();
				return ;
			} */
		}	
		
		else if(orderstatus=="Customer Contacted" && orderreason=="Not Confirmed Order - Customer Confirmed to Place Order")
		{
			batterytype = document.getElementById("battype").value;
				
			servicetype= document.getElementById("servicetype").value;
			battype= document.getElementById("battype").value;
			serviceplace= document.getElementById("serviceplace").value;
			serviceprice = document.getElementById("serviceprice").value; 
			cusname = document.getElementById("cusname").value; 
			cusemailid = document.getElementById("cusemailid").value; 
			cusaddr1 = document.getElementById("cusaddr1").value; 
			cusaddr2 = document.getElementById("cusaddr2").value;
			cusstate = document.getElementById("cusstate").value; 
			cuscity = document.getElementById("cuscity").value; 
			cusarea = document.getElementById("cusarea").value;
			Quantity = document.getElementById("Quantity").value;
			
			if(servicetype == "" || servicetype == "0" || servicetype == "default" || servicetype == "defaultss")
			{
				errMsg ="<font color='#9B5BDD'>Please Select Service Type</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misservicesstatus.servicetype.focus();
				return ;
			}
			
			if(battype == "" || battype == "0" || battype == "default" || battype == "defaultss")
			{
				errMsg ="<font color='#9B5BDD'>Please Select Battery Type</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misservicesstatus.battype.focus();
				return ;
			}
			
			if(serviceplace == "" || serviceplace == "0" || serviceplace == "default" || serviceplace == "defaultss")
			{
				errMsg ="<font color='#9B5BDD'>Please Select Service Place</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misservicesstatus.battype.focus();
				return ;
			}
			
			if(serviceprice == "" || serviceprice == "0" || serviceprice == "default" || serviceprice == "defaultss")
			{
				errMsg ="<font color='#9B5BDD'>Please Enter Battery Price</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misservicesstatus.serviceprice.focus();
				return ;
			}

			if(cusname == "" || cusname == "0" )
			{
				errMsg ="<font color='#9B5BDD'>Please Enter Customer Name</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misservicesstatus.cusname.focus();
				return ;
			}

			if(cusemailid == "" || cusemailid == "0" )
			{
				errMsg ="<font color='#9B5BDD'>Please Enter Customer Email ID</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misservicesstatus.cusemailid.focus();
				return ;
			}

			if(cusaddr1 == "" || cusaddr1 == "0" )
			{
				errMsg ="<font color='#9B5BDD'>Please Enter Customer Address1</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misservicesstatus.cusaddr1.focus();
				return ;
			}

			if(cusaddr2 == "" || cusaddr2 == "0" )
			{
				errMsg ="<font color='#9B5BDD'>Please Enter Customer Address2</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misservicesstatus.cusaddr2.focus();
				return ;
			}

			if(cusarea == "" || cusarea == "0"  || cusarea == "default"  || cusarea == "defaultss" )
			{
				errMsg ="<font color='#9B5BDD'>Please Select Customer Area</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misservicesstatus.cusarea.focus();
				return ;
			}
			
				
			var number_regex = /^\d+$/;
			if ((!Quantity.match(number_regex) && Quantity.length > 0) || (!Quantity.match(number_regex)) || (Quantity.length == 0) || (Quantity<=0) || (isNaN(parseFloat(Quantity))==true))
			{
				Quantity=1;
				errMsg ="<font color='#9B5BDD'>Please enter valid quantity...</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misservicesstatus.Quantity.focus();
				return ;
			}
		}
		else
		{
			var postponedate ="";
		}
		
		$('#divpostponed').hide();
		var xmlhttp= "";
		var resp= "";
		var url ="../../../servlet/MISOperatorServiceDetails?hidWhatToDo=updateorderstatus&orderid="+orderid+"&ordernumber="+ordernumber+"&orderstatus="+orderstatus+"&orderreason="+orderreason+"&agentcomments="+cusmessage+"&postponedate="+postponedate+"&retailertoorder="+retailertoorder+"&rating="+rating+"&payment_mode="+payment_mode+"&SMSURL="+SMSURL+"&shorturl="+shorturl+"&servicetype="+servicetype+"&battype="+battype+"&serviceplace="+serviceplace+"&serviceprice="+serviceprice+"&Quantity="+Quantity+"&cusarea="+cusarea+"&cusname="+cusname+"&cusemailid="+cusemailid+"&cusaddr1="+cusaddr1+"&cusaddr2="+cusaddr2+"&cusstate="+cusstate+"&cuscity="+cuscity+"&cusarea="+cusarea+"&paymentcollected="+paymentcollected;
				
		//alert(url);
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
					errMsg ="<table border='0' width='300px' height='10px' cellpadding='0' cellspacing='0' bgcolor='#88689f'><tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'><a href='javascript:closemobdiv();'><img src=\"../../../images/Delete1.png \" border='0'></a></td></tr></table><table border='0' width='300px' height='2px' bgcolor='#FFFFFF' valign'top'><tr bgcolor='#FFFFFF'><td align='justify' bgcolor='#FFFFFF' style='font-family:Verdana;font-size:11px;color:#000000;'>"+resp+"</td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='getupdatedorders();' class='button4'><br></td></tr><tr height='15'></tr></table>";
					document.getElementById("divpostponed").innerHTML=""; 
					document.getElementById("divpostponed").style.display='block';
					document.getElementById("divpostponed").innerHTML=errMsg
				}
			}			
		}
		xmlhttp.open("GET",url, true);		
		xmlhttp.send();	
}

function closemobdiv()
{
	$('#divpostponed').hide();
	
}
/** Function to get order details after changing order status and reason **/
function getupdatedorders()
{
	$('#divpostponed').hide();

	var city=document.misservicesstatus.city.value;
	var keyword=document.misservicesstatus.keyword.value;
	var strDateOpt = document.misservicesstatus.dates.value;
	var strFromDate = document.misservicesstatus.txtFromDate.value;
	var strToDate = document.misservicesstatus.txtToDate.value;

	document.misservicesstatus.method="POST";
	document.misservicesstatus.action="../../../servlet/MISOperatorServiceDetails?hidWhatToDo=getconfirmedordersmis&city="+city+"&keyword="+keyword+"&dates="+strDateOpt+"&txtFromDate="+strFromDate+"&txtToDate="+strToDate;
	document.misservicesstatus.submit();
	
}
function setagentcomments(orderid,ordernumber,orderstatus,state,city,cusmobile,PaymentMode)
{
		var orderreason = document.misservicesstatus.orderreason.value;
		document.misservicesstatus.cusmessage.value=orderreason;
		
		//alert(PaymentMode);
		
	if(orderreason =="installed")
	{
		$('#rettoorder').hide();
		$('#rettoorder1').hide();
		$('#modifybatdetls').hide();
		$('#modifybatdetls1').hide();
		$('#modifybatdetls2').hide();
		$('#modifybatdetls3').hide();
		$('#modifybatdetls4').hide();
		$('#modifybatdetls5').hide();
		$('#modifybatdetls6').hide();
		$('#modifybatdetls7').hide();
		$('#modifybatdetls8').hide();
		$('#modifybatdetls9').hide();
		$('#modifybatdetls10').hide();
		$('#modifybatdetls11').hide();
		$('#modifybatdetls12').hide();
		$('#modifybatdetls13').hide();
		$('#modifybatdetls14').hide();
		$('#confirmord1').hide();
		$('#confirmord2').hide();
		$('#confirmord3').hide();
		$('#confirmord4').hide();
		$('#confirmord5').hide();
		$('#confirmord6').hide();
		$('#confirmord7').hide();
		$('#confirmord8').hide();
		$('#confirmord9').hide();
		$('#confirmord10').hide();
		$('#confirmord91').hide();
		$('#confirmord101').hide();
		$('#confirmord92').hide();
		$('#confirmord102').hide();
		$('#ratingid').show();
		$('#modifybatdetls91').hide();
		
		if(PaymentMode =="Online")
		{
			//alert("inside PaymentMode");
			$('#payment_mode').html("<option value='Online' selected >Online</option>");
		}
		else if(PaymentMode =="Online Payment After Fitment")
		{
			//alert("in");
			$('#order_type').after("<p style='color: red; font-size: 12px; font-weight: 400;'>Customer has Opted for:"+PaymentMode+"</p>");
			$('#payment_mode').val(PaymentMode);
			$("#payment_mode").empty();
			$('#payment_mode').append("<option value='0' >&lt;-- Select Payment Method--&gt;</option>");
			$('#payment_mode').append("<option value='Cheque'>Cheque</option>");
			$('#payment_mode').append("<option value='Credit Card'>Credit Card</option>");
			$('#payment_mode').append("<option value='Debit Card'>Debit Card</option>");
			$('#payment_mode').append("<option value='Cash On Delivery'>Cash On Delivery</option>");
			$('#payment_mode').append("<option value='Online Payment After Fitment'>Online Payment After Fitment</option>");
			$('#payment_mode').append("<option value='UPI' >UPI</option>");
		}
		
		$('#confirmord11').show();
		$('#order_type_div').show();
		$('#ratingid').show();
		$('.Quantity_tr').hide();
	}
	else if(orderreason =="Not Confirmed Order - Customer Confirmed to Place Order")
	{
		$('#cuscity').html("<option value='"+city+"' selected >"+city+"</option>");
		$('#modifybatdetls').show();
		$('#modifybatdetls1').show();
		$('#modifybatdetls2').show();
		$('#modifybatdetls3').show();
		$('#modifybatdetls4').show();
		$('#modifybatdetls5').show();
		$('#modifybatdetls6').show();
		$('#modifybatdetls7').show();
		$('#modifybatdetls8').show();
		$('#modifybatdetls9').show();
		$('#modifybatdetls10').show();
		$('#modifybatdetls11').show();
		$('#modifybatdetls12').show();
		$('#modifybatdetls13').show();
		$('#modifybatdetls14').show();
		$('#modifybatdetls91').show();
		$('#confirmord1').show();
		$('#confirmord2').show();
		$('#confirmord3').show();
		$('#confirmord4').show();
		$('#confirmord5').show();
		$('#confirmord6').show();
		$('#confirmord7').show();
		$('#confirmord8').show();
		$('#confirmord9').show();
		$('#confirmord10').show();
		$('#confirmord91').show();
		$('#confirmord101').show();
		$('#confirmord92').show();
		$('#confirmord102').show();
		$('#confirmord11').hide();
		$('#ratingid').hide();
		$('#order_type_div').show();
		$('.Quantity_tr').show();
	}
	else
	{
		$('#rettoorder').hide();
		$('#rettoorder1').hide();
		$('#modifybatdetls').hide();
		$('#modifybatdetls1').hide();
		$('#modifybatdetls2').hide();
		$('#modifybatdetls3').hide();
		$('#modifybatdetls4').hide();
		$('#modifybatdetls5').hide();
		$('#modifybatdetls6').hide();
		$('#modifybatdetls7').hide();
		$('#modifybatdetls8').hide();
		$('#modifybatdetls9').hide();
		$('#modifybatdetls10').hide();
		$('#modifybatdetls11').hide();
		$('#modifybatdetls12').hide();
		$('#modifybatdetls13').hide();
		$('#modifybatdetls14').hide();
		$('#confirmord1').hide();
		$('#confirmord2').hide();
		$('#confirmord3').hide();
		$('#confirmord4').hide();
		$('#confirmord5').hide();
		$('#confirmord6').hide();
		$('#confirmord7').hide();
		$('#confirmord8').hide();
		$('#confirmord9').hide();
		$('#confirmord10').hide();
		$('#confirmord11').hide();
		$('#confirmord91').hide();
		$('#confirmord101').hide();
		$('#confirmord92').hide();
		$('#confirmord102').hide();
		$('#ratingid').hide();
		$('#order_type_div').hide();
		$('.Quantity_tr').hide();
		$('#modifybatdetls91').hide();
	}
		
}
function setagentcomments_payment()
{
	var payment_mode_temp = document.misservicesstatus.payment_mode.value;
	if(payment_mode_temp == "" || payment_mode_temp == "0" || payment_mode_temp == "default" || payment_mode_temp == "defaultss")
	{
		errMsg ="<font color='#9B5BDD'>Please Select Payment Mode</font>";
		document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
		document.misservicesstatus.payment_mode.focus();
		return ;
	}
	
	document.misservicesstatus.cusmessage.value="Installed - Payment done by "+ payment_mode_temp;
}


/** Function to get visitor details when agent selects filter **/
function getupdatedvisitors1(strDateOpts,strFromDates,strToDates,keyword,city)
{
	var statusfilter = document.misservicesstatus.statusfilter.value;
	var substatusfilter = document.misservicesstatus.substatusfilter.value;
	var retselectname = document.misservicesstatus.retnameselect.value;

	var selected;
	var strUserid = document.misservicesstatus.strUserid.value;
	//alert(strUserid);
	
	var operatorfilter = "";
	var fromdatefilter = "";
	var fromtimefilter = "";
	var todatefilter = "";
	var totimefilter = "";
	
	if(strUserid=="operator")
	{
		operatorfilter = document.misservicesstatus.operatorfilter.value;
	}
	else
	{
	}
	fromdatefilter = $("#fromdatefilter").val();
	fromtimefilter = $("#fromtimefilter").val();
	todatefilter = $("#todatefilter").val();
	totimefilter = $("#totimefilter").val();
	var orders_of = $("#orders_of").val();

	var servicetypefilterselect;

	selected = $("#servicetypefilter option:selected");
     servicetypefilterselect = "";
        selected.each(function () {
            servicetypefilterselect += "'" + $(this).val() + "',";
        });

		if(servicetypefilterselect=="")
		{
			servicetypefilterselect="All";
		}
		else
		{
			servicetypefilterselect = servicetypefilterselect.replace(/(^,)|(,$)/g, "")
			servicetypefilterselect=servicetypefilterselect;

		}
		
		var TestOrders_radio = $("input[name='TestOrders_radio']:checked").val()

	document.misservicesstatus.method="POST";
	document.misservicesstatus.action="../../../servlet/MISOperatorServiceDetails?hidWhatToDo=getconfirmedordersmis&city="+city+"&keyword="+keyword+"&dates="+strDateOpts+"&txtFromDate="+strFromDates+"&txtToDate="+strToDates+"&statusfilter="+statusfilter+"&substatusfilter="+substatusfilter+"&servicetypefilter="+servicetypefilterselect+"&retselectname="+retselectname+"&operatorfilter="+operatorfilter+"&fromdatefilter="+fromdatefilter+"&fromtimefilter="+fromtimefilter+"&todatefilter="+todatefilter+"&totimefilter="+totimefilter+"&test_orders="+TestOrders_radio+"&orders_of="+orders_of;
	
	//document.misservicesstatus.action="../../../servlet/MISOperatorServiceDetails?hidWhatToDo=getconfirmedordersmis&city="+city+"&keyword="+keyword+"&dates="+strDateOpts+"&txtFromDate="+strFromDates+"&txtToDate="+strToDates+"&statusfilter="+statusfilter+"&substatusfilter="+substatusfilter+"&servicetypefilter="+servicetypefilterselect+"&retselectname="+retselectname+"&operatorfilter="+operatorfilter+"&fromdatefilter="+fromdatefilter+"&fromtimefilter="+fromtimefilter+"&todatefilter="+todatefilter+"&totimefilter="+totimefilter+"&test_orders="+TestOrders_radio+"&orders_of="+orders_of; 
	
	
	document.misservicesstatus.submit();
	
}
</script>


</head>
<body onload="getsubstatusoptions();" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="misservicesstatus">
<div id='divpostponed' class='divpostponed' style="display:none;"></div>
<div id='divpostponed1' class='divpostponed1' style="display:none;"></div>
<div id='divmobile' class='divmobile' style="display:none;"></div>
<div id="result" style="display:none"></div>
<div id="result1" style="display:none"></div>
<!-- Services Header Starts -->
<tr>
	<jsp:include page = "../header.jsp" />
</tr>
<!-- Services Header Ends -->
<tr>
	<td>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#ffffff" >
		<tr>
		
			<td width="75%" align="left" valign="top">
				<table width="100%" border="1" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">
					<!-- your page content starts here  -->
					<table width="100%" cellspacing="1" align="center" bgcolor="#FFFFFF" cellpadding="0" border="0">
					<table width="450" border="0" align="center" cellpadding="5" cellspacing="0" bgcolor="#FFFFFF">
					<tr><td><a href="../../../jsp/serviceoperator/ordermis/misselconfirm.jsp?keyword=<%=keyword%>&dates=<%=strDateOpt%>&txtFromDate=<%=strFromDate%>&txtToDate=<%=strToDate%>" class="onclick1">Back</a></td></tr>
					<tr><td>
					&nbsp;&nbsp;&nbsp;Order Status : <select style='width:215px;background-color: #ffffff;color: #000;padding: .5em;padding-right: 2.5em;margin: 0;border-radius: 3px;text-indent: 0.01px;' name="statusfilter" id="statusfilter" onChange="javascript:getsubstatusoptions()"><option value='<%=statusfilter1%>'><%=statusfilter1%></option><option value=''>Select Status</option><option value="confirmed">Confirmed</option><option value='Customer Contacted'>Customer Contacted</option><option value='Customer Not Contacted'>Customer Not Contacted</option><option value='Postponed'>Postponed</option><option value='Repeated'>Repeated</option></select>
					
					&nbsp;&nbsp;&nbsp;&nbsp;Order Sub Status : <select style='width:215px;background-color: #ffffff;color: #000;padding: .5em;padding-right: 2.5em;margin: 0;border-radius: 3px;text-indent: 0.01px;' name="substatusfilter" id="substatusfilter" multiple="multiple" ><option value='<%=substatusfilter1%>'><%=substatusfilter1%></option><option value=''>Select Sub Status</option></select>
					
					&nbsp;&nbsp;&nbsp; Service Type : <select style='width:215px;background-color: #ffffff;color: #000;padding: .5em;padding-right: 2.5em;margin: 0;border-radius: 3px;text-indent: 0.01px;' name="servicetypefilter" id="servicetypefilter"  multiple="multiple">
							<option value="Tyre Services Wheel Balancing and Alignment" >Tyre Services Wheel Balancing and Alignment</option>
										<option value="Exterior and Interior Cleaning" >Exterior and Interior Cleaning</option>
										<option value="Dent Removal and Painting" >Dent Removal and Painting</option>
										<option value="Road Side Assistance" >Road Side Assistance</option>
										<option value="AC Tuneup and Repair" >AC Tuneup and Repair</option>
										<option value="Winshield and Glass Repair" >Winshield and Glass Repair</option>
										<option value="General Checkup" >General Checkup</option>
										</select>
							
							
							&nbsp;&nbsp;&nbsp;&nbsp; Franchisee : <select style='width:215px;background-color: #ffffff;color: #000;padding: .5em;padding-right: 2.5em;margin: 0;border-radius: 3px;text-indent: 0.01px;' name="retnameselect" id="retnameselect"  ><option value='<%=retselectname1%>'><%=retselectname1%></option>
							<option value="" >Select Franchisee</option>
							<%
							if(alist1!=null && alist1.size() > 0)
							{
								
									for(int m=0;m<alist1.size();m++)
									{
									Hashtable ht1=(Hashtable)alist1.get(m);
									String retname=(String)ht1.get("retailer_name");
							%>
							<option  value="<%=retname%>" ><%=retname%></option>
							<%

							}
							}
							%>
							</select>
							</td></tr>
							<tr><td align="center" class="subheading">							
							&nbsp;&nbsp;&nbsp;Orders by : <select style='width:215px;background-color: #ffffff;color: #000;padding: .5em;padding-right: 2.5em;margin: 0;border-radius: 3px;text-indent: 0.01px;' name="orders_of" id="orders_of" ><option value='0'>All</option><option value='Operator-Call' >Operator-Call</option> <option value='Operator-Chat' >Operator-Chat</option> <option value='Operator-WhatsApp' >Operator-WhatsApp</option><option value='Customer'>Customer</option></select>
			
			&nbsp;&nbsp;&nbsp;Select Date &nbsp;&nbsp;&nbsp;
		&nbsp;:&nbsp;&nbsp;	<span id="datepairExample">
								<input style="width: 70px;" type="text" id="fromdatefilter" class="date start" />
								<input style="width: 70px;" type="text" id="fromtimefilter" class="time start" /> to
								<input style="width: 70px;" type="text" id="todatefilter" class="date end" />
								<input style="width: 70px;" type="text" id="totimefilter" class="time end" />
							</span>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Show Test Orders:  <input type="radio" name="TestOrders_radio" id="TestOrders_radio_ID_1" value="no" checked> No &nbsp;&nbsp;<input type="radio" name="TestOrders_radio" id="TestOrders_radio_ID_2" value="yes" > Yes 
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:getupdatedvisitors1('<%=strDateOpt%>','<%=strFromDate%>','<%=strToDate%>','<%=keyword%>','<%=city%>')" class="button4">&nbsp;Submit&nbsp;</a>
							
					</td>
						</tr>

					     <tr>
						 <td>
							<table width="450" align="center" cellspacing="1" cellpadding="0">
							<tr><td height="5"></td></tr>
							<tr>
								<td class="subheading" align="left" size="2"><%=strDateOpt%> >> <%=htOptions.get("orderstatus")%>&nbsp;Orders >> <%=city%>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:onClickExportToExcel();" class="onclick1"><img src="../../../images/export_xls.gif" border="0"/>&nbsp;Export as Excel</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:onClickExportToPdf();" class="onclick1"><img src="../../../images/pdf_xls.gif" border="0"/>&nbsp;Export as PDF</a></td>
							</tr>
							<tr>
								<td>
									<table width="100%" cellspacing="1" bgcolor="#8B8D90" cellpadding="0">
									<tr>
										<td>
											<table width="100%" cellspacing="1" bgcolor="#8B8D90" cellpadding="0">
											<tr>
												<td bgcolor="#0081C7" align="center" class="subheading"><font color="#FFFFFF"><%=keyword%>&nbsp;Service&nbsp;Order&nbsp;Details&nbsp;Summary</font></td>
											</tr>
											<tr>
												<td bgcolor="#FFFFFF" align="center" colspan="2">
													<table width="100%" align="center" cellspacing='1'   bgcolor="#FFFFFF" cellpadding="15" class='tableizer-table'>
													<tr>
														<td  align="center" > 
															<table width="100%" cellspacing="0"  cellpadding="2" border="2">
															<tr>
																<td bgcolor="#DAD0E1" align="left" class="insidecontent" >&nbsp;Location</td><td align="left" class="insidecontent" >:&nbsp;<%=htOptions.get("city")%></td>
																
																<td bgcolor="#DAD0E1" align="left" class="insidecontent" >&nbsp;From Date</td><td align="left" class="insidecontent" >:&nbsp;<%=htOptions.get("txtFromDate")%></td>
																<td bgcolor="#DAD0E1" align="left" class="insidecontent" >&nbsp;To Date</td><td align="left" class="insidecontent" >:&nbsp;<%=htOptions.get("txtToDate")%></td>
																<td bgcolor="#DAD0E1" align="left" class="insidecontent" >&nbsp;Selected Services Type</td><td align="left" class="insidecontent" >:&nbsp;<%=servicetypefilter1%></td>
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
															<div style="width:1320px;height:400px;  overflow:scroll; overflow-X:auto;  -webkit-overflow-scrolling: touch;" id='div1'>
																<table width="100%" cellspacing="1"  cellpadding="2" bgcolor="#dddddd">
																	<tr  bgcolor="#cccccc">
																		<td align="center" class="subheading"><font color="#000000">Sl&nbsp;No</font></td>
																		<td align="center" class="subheading"><font color="#000000">ORD&nbsp;No</font></td>
																		<td align="center" class="subheading"><font color="#000000">Cus&nbsp;Name/Emaild/Mobile</font></td>
																		<td align="center" class="subheading"><font color="#000000">Ret&nbsp;Name/Mobile</font></td>
																		<td align="center" class="subheading"><font color="#000000">Service&nbsp;Type</font></td>
																		<td align="center" class="subheading"><font color="#000000">Service Place</font></td>
																		<td align="center" class="subheading"><font color="#000000">Veh&nbsp;Make-Model</font></td>
																		<td align="center" class="subheading"><font color="#000000">Payment Mode</font></td>
																		<td align="center" class="subheading"><font color="#000000">Battery Type</font></td>
																		<td align="center" class="subheading"><font color="#000000">Quantity</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Service Price</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Ord&nbsp;Status</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Ord&nbsp;Reasons</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Confirm&nbsp;By</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Agent&nbsp;Comments</font></td>
																		<td align="center" class="subheading"><font color="#000000">Update&nbsp;Status</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Ordered&nbsp;By</font></td>
																		<td align="center" class="subheading"><font color="#000000"> State</font></td>
																		<td align="center" class="subheading"><font color="#000000"> City</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Area</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Ordered Date</font></td>
																		<td align="center" class="subheading"><font color="#000000"> First Contacted Date</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Updated Date</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Postponed Date</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Installed Date</font></td>
																		<!--<td align="center" class="subheading"><font color="#000000"> Cus&nbsp;Invoice</font></td>-->

																	</tr>
																	<%
																	for(int i=0;i<alist.size();i++)
																		
																	{
																		Hashtable ht=(Hashtable)alist.get(i);
																		String ord_id=String.valueOf(ht.get("ord_id"));
																		String ordnum=String.valueOf(ht.get("order_number"));
																		String cusname=(String)ht.get("consumer_name");
																		String cusemailid=(String)ht.get("consumer_emailid");
																		String conmobile=String.valueOf(ht.get("consumer_mobnumber"));
																		String retname=(String)ht.get("retailer_name");
																		String retmobile=String.valueOf(ht.get("retailer_mobilnumber"));
																		String servicesplace=(String)ht.get("services_place");
																		String servicestype=(String)ht.get("services_type");
																		String state1=(String)ht.get("state");
																		LogLevel.DEBUG(1,new Throwable(),"state1: "+state1);
																		String consumer_address=(String)ht.get("consumer_address");
																		String payment_mode_type=String.valueOf(ht.get("payment_mode_type"));
																		String payment_mode=String.valueOf(ht.get("payment_mode"));
                                                                        String service_engineer_name=String.valueOf(ht.get("service_engineer_name"));
																		String service_engineer_mobile=String.valueOf(ht.get("service_engineer_mobile"));
																		String vehname=(String)ht.get("veh_name");
																		String vehmake=(String)ht.get("veh_model");
																		String city1=(String)ht.get("city");
																		String area=(String)ht.get("area");
																		String price=String.valueOf(ht.get("service_price_mrp"));
																		String obrpprice=String.valueOf(ht.get("service_price_discount"));
																		String consumer_address1=String.valueOf(ht.get("consumer_address1"));
																		String pincode=String.valueOf(ht.get("pincode"));
																		String orderstatus=(String)ht.get("order_status");
																		String order_reasons=(String)ht.get("order_reasons");
																		String agent_name=(String)ht.get("operator");
																		String confirm_by=(String)ht.get("confirm_by");
																		String first_contacted_date=(String)ht.get("first_contacted_date");
																		LogLevel.DEBUG(1,new Throwable(),"first_contacted_date: "+first_contacted_date);
																		LogLevel.DEBUG(1,new Throwable(),"confirm_by: "+confirm_by);
																		String order_agent_comments=(String)ht.get("order_agent_comments");
																		String quantity=String.valueOf(ht.get("quantity"));
																		String product_type=String.valueOf(ht.get("product_type"));
																		String date=String.valueOf(ht.get("creation_date"));
																		String postponedate=String.valueOf(ht.get("postpone_date"));
																		LogLevel.DEBUG(1,new Throwable(),"postponedate12: "+postponedate);
																		LogLevel.DEBUG(1,new Throwable(),"cusname12: "+cusname);
																		
																		String updated_date=(String)ht.get("updated_date");
																		LogLevel.DEBUG(1,new Throwable(),"updated_date12: "+updated_date);

																		String installed_date=(String)ht.get("installed_date");
																		LogLevel.DEBUG(1,new Throwable(),"installed_date12: "+installed_date);
																		
																		String pdfurl=String.valueOf(ht.get("pdfurl"));
																		if(vehname.equals("0") || vehname == "0")
																		{
																			vehname = "";
																		}
																		else
																		{
																			vehname=vehname;
																		}
																		if(vehmake.equals("0") || vehmake == "0")
																		{
																			vehmake = "";
																		}
																		else
																		{
																			vehmake=vehmake;
																		}
																		if(order_agent_comments == null || order_agent_comments .equals(""))
																		{
																			order_agent_comments = "-";
																		}
																		else
																		{
																			order_agent_comments=order_agent_comments;
																		}
																		if(order_reasons.equals("0") || order_reasons .equals(""))
																		{
																			order_reasons = "-";
																		}
																		else
																		{
																			order_reasons=order_reasons;
																		}
																		if(agent_name.equals("0") || agent_name.equals(""))
																		{
																			agent_name = "-";
																		}
																		else
																		{
																			agent_name=agent_name;
																		}
																		if(postponedate.equals("null") || postponedate.equals(""))
																		{
																			postponedate = "0000-00-00 00:00:00";
																		}
																		else
																		{
																			postponedate=postponedate;
																		}
																	
																	if(orderstatus.equals("confirmed"))
																		{
																	%>
																	<tr bgcolor="#FFFFFF">
																	<%}
																	else if(orderstatus.equals("Repeated"))
																		{
																		%>
																	<tr bgcolor="#FF5050">
																		<%
																		}
																	else if(orderstatus.equals("Customer Contacted") && order_reasons.equals("In Process"))
																		{
																		%>
																	<tr bgcolor="#FFFF66">
																		<%
																		}
																		else if(orderstatus.equals("Customer Not Contacted") && order_reasons.equals("Not Installed") || order_reasons.equals("cancelled-franchisee-offbushrs") ||order_reasons.equals("cancelled-franchisee-denied") ||order_reasons.equals("cancelled-franchisee-notresponded") ||order_reasons.equals("cancelled-franchisee-modeloutofstock") ||order_reasons.equals("cancelled-customer") ||order_reasons.equals("cancelled-regenerated"))
																		{
																		%>
																	<tr bgcolor="#FF5050">
																		<%
																		}
																	else if(orderstatus.equals("Customer Contacted") && order_reasons.equals("installed"))
																		{
																		%>
																			<tr bgcolor="#009933">
																		<%
																		}
																	
																		else if(orderstatus.equals("postponed") || orderstatus.equals("Postponed") && order_reasons.equals("High Priority"))
																		{
																		%>
																			<tr bgcolor="#C390D4">
																		<%
																		}
																		else if(orderstatus.equals("postponed") || orderstatus.equals("Postponed"))
																		{
																		%>
																			<tr bgcolor="#FF9933">
																		<%
																		}
																		else if(orderstatus.equals("Not Confirmed") || orderstatus.equals("Not Confirmed") || order_reasons.equals("Update"))
																		{
																		%>
																			<tr bgcolor="#DAD0E1">
																		<%
																		}
																	else
																		{
																		%>
																	<tr bgcolor="#FFFFFF">
																		<%
																		}
																		
																		%>
																	    <td  class="insidecontent" align="center"><%=(i+1)%> </td>
																		<td  class="insidecontent" align="left"><%=ordnum%> </td>
																		<td  class="insidecontent" align="left"><%=cusname%></br></br><%=cusemailid%></br></br><%=conmobile%></td>
																
                                                                        <%
																		if(service_engineer_name.equals("NULL") || service_engineer_name.equals("null"))
																		{
																		%>
																		<td  class="insidecontent" align="left"><%=retname%></br><%=retmobile%></td>
																		<%
																		}
																		else
																		{
																		%>
																		<td  class="insidecontent" align="left"><%=retname%></br><%=retmobile%></br></br>Service Engineer Details: <%=service_engineer_name%> - <%=service_engineer_mobile%></td>
																		<%
																		}
																		%>
                                                                        
                                                                        
																		<td  class="insidecontent" align="left"><%=servicestype%> </td>
																		<td  class="insidecontent" align="left"><%=servicesplace%> </td>
																		<td  class="insidecontent" align="left"><%=vehname%>-<%=vehmake%> </td>
																		
																		<td  class="insidecontent" align="left" 
																		<%
																		if(payment_mode.equals("Online") && !order_reasons.equals("Payment In Process"))
																		{ 
																		%> 
																		bgcolor='#E91E63'
																		<%
																		}
																		else if(payment_mode.equals("Online Payment After Fitment"))
																		{
																		%> 
																		bgcolor='#9900FF'
																		<%
																		}
																		else if(payment_mode.equals("UPI"))
																		{
																		%>
																		bgcolor='#CDDC39'
																		<%
																		}
																		else
																		{
																		}
																		%> 
																		> <%=payment_mode%> </td>
																		<td  class="insidecontent" align="left"><%=product_type%> </td>
																		<td  class="insidecontent" align="left"><%=quantity%> </td>
																		
																		<td  class="insidecontent" align="left">Rs.<%=obrpprice%> </td>
																		<td  class="insidecontent" align="left"><%=orderstatus%> </td>
																<td  class="insidecontent" align="left"><%=order_reasons%> </td>
																<%if(confirm_by.equals("0"))
													            {
													            %>
													<td class="insidecontent" align="left">-</td>
													            <%
													            }
													            else
													            {
													            %>
													<td  class="insidecontent" align="left">Confirmed by <%=confirm_by%></td>
													            <%
													            }
													            %>		
																		
																		<td  class="insidecontent" align="left"><%=order_agent_comments%> </td>
																		<td  class="insidecontent" align="left">
																		<select style='width:110px; height:30px' name="<%=ord_id%>" id="<%=ord_id%>" onChange="javascript:Updatevisitorstatus('<%=ord_id%>','<%=ordnum%>','<%=state1%>','<%=city1%>','<%=area%>','<%=conmobile%>','<%=payment_mode%>')"><option value=''>Update Status</option><option value="confirmed">Confirmed</option><option value='Customer Contacted'>Customer Contacted</option><option value='Customer Not Contacted'>Customer Not Contacted</option><option value='Postponed'>Postpone</option><option value='Repeated'>Repeated</option></select> </td>
																		<td  class="insidecontent" align="left"><%=agent_name%> </td>
																		<td  class="insidecontent" align="left"><a data-toggle="popover" title="Address" date-placement="top" data-content="<%=consumer_address1%> | Area : <%=area%> | Pincode :<%=pincode%>." style="color: #000001;  cursor: alias;"> <%=state1%></a> </td>
																		<td  class="insidecontent" align="left"><%=city1%> </td>
																		<td  class="insidecontent" align="left"><%=area%> </td>
																		<td  class="insidecontent" align="left"><%=date%> </td>
																		<td  class="insidecontent" align="left"><%=first_contacted_date%></td>
																		<td  class="insidecontent" align="left"><%=updated_date%></td>
																		
																		<td  class="insidecontent" align="left"><%=postponedate%> </td>
																		<td  class="insidecontent" align="left"><%=installed_date%> </td>
																		<!--<% if(pdfurl.equals("") || pdfurl == "NULL")
																		{
																		%>
																		<td  class="insidecontent" align="center">No PDF</td>
																		<%
																		}
																		else
																		{
																		%>
																		<td  class="insidecontent" align="center"><a href='<%=pdfurl%>' target="new"><img src="../../../images/pdf_xls.gif" border="0"/></td>
																		<%
																		}
																		%>-->
																		
																		
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
<input type="hidden" name="city" value="<%=city%>">
<input type="hidden" name="keyword" value="<%=keyword%>">
<input type="hidden" name="strUserid" value="<%=strUserid%>">

</form>

<script type="text/javascript">

	$(function (){
		$('[data-toggle="popover"]').popover();
	});
    $(function () {
        $('#servicetypefilter').multiselect({
            includeSelectAllOption: true
        });
		
        $('#substatusfilter').multiselect({
            includeSelectAllOption: true
        });
			
			 
    });

$(document).ready(function(){
	var fromdatefilter ="<%=fromdatefilter%>";
	var fromtimefilter = "<%=fromtimefilter%>";
	var todatefilter = "<%=todatefilter%>";
	var totimefilter = "<%=totimefilter%>";
	
	if(fromdatefilter=="0" || fromdatefilter=="null" || fromdatefilter==""|| fromdatefilter==" " )
	{
	}
	else
	{
		$("#fromdatefilter").val("<%=fromdatefilter%>");
	}
	
	if(todatefilter=="0" || todatefilter=="null" || todatefilter==""|| todatefilter==" " )
	{
	}
	else
	{
		$("#todatefilter").val("<%=todatefilter%>");
	}
	
	
	if(fromtimefilter=="0" || fromtimefilter=="null" || fromtimefilter==""|| fromtimefilter==" " )
	{
	}
	else
	{
		var fromtimefilterArr=fromtimefilter.split(":");
		if(fromtimefilterArr.length<3)
		{
		}
		else
		{
			$("#fromtimefilter").val("<%=fromtimefilter%>");
		}
	}
	
	if(totimefilter=="0" || totimefilter=="null" || totimefilter==""|| totimefilter==" " )
	{
	}
	else
	{
		var totimefilterArr=totimefilter.split(":");
		if(totimefilterArr.length<3)
		{
		}
		else
		{
			$("#totimefilter").val("<%=totimefilter%>");
		}
	}
});


   
</script>

<script>
    // initialize input widgets first
    $('#datepairExample .time').timepicker({
        'showDuration': true,
        'timeFormat': 'H:i:s'
    });

    $('#datepairExample .date').datepicker({
        'format': 'dd-mm-yyyy',
        'autoclose': true
    });

    // initialize datepair
    $('#datepairExample').datepair();
	
	
		function makeRequest(Url,orderid,ordernumber,orderstatus,cusmobile) {

		var request = gapi.client.urlshortener.url.insert({
		'resource': {
		'longUrl': Url
		}
		});
		var result=request.execute(function(response) {
		if (response.id != null) {
		str =response.id;
		document.getElementById("result").value = str;
		document.getElementById("result1").value = str;

		updatecustcontactedstatus(orderid,ordernumber,orderstatus,cusmobile);

		}
		else {
		alert("Error: creating short url \n" + response.error);
		}

		});

		}		
		
		function makeRequest1(Url,orderid,ordernumber,orderstatus,cusmobile) {

		var request = gapi.client.urlshortener.url.insert({
		'resource': {
		'longUrl': Url
		}
		});
		var result=request.execute(function(response) {
		if (response.id != null) {
		str =response.id;
		//document.getElementById("result").value = str;
		document.getElementById("result1").value = str;

		updatecustcontactedstatus(orderid,ordernumber,orderstatus,cusmobile);

		}
		else {
		alert("Error: creating short url \n" + response.error);
		}

		});

		}

		function load() {
		gapi.client.setApiKey('AIzaSyDV5_Ca9cEVSFaiLkyzGIcDcbnV_4CiA0o');
		gapi.client.load('urlshortener', 'v1', function() { document.getElementById("result").innerHTML = ""; document.getElementById("result1").innerHTML = ""; });
		}
		window.onload = load;
	
	
	
</script>
</center>
</body>
</html>