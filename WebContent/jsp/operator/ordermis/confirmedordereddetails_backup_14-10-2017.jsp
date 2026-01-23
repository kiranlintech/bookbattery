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
Vector alist=(Vector)session.getAttribute("sesBatOrderstatusVectorVector");
LogLevel.DEBUG(1,new Throwable(),"alist: "+alist);
//out.println(alist);

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

String batterytypefilter1=(request.getParameter("batterytypefilter")!=null)?request.getParameter("batterytypefilter"):"";
if(batterytypefilter1==null || batterytypefilter1.equals(""))
	batterytypefilter1="All";
LogLevel.DEBUG(1,new Throwable(),"batterytypefilter1: "+batterytypefilter1);

String operatorfilter1=(request.getParameter("operatorfilter")!=null)?request.getParameter("operatorfilter"):"";
if(operatorfilter1==null || operatorfilter1.equals(""))
	operatorfilter1="Select Operator";
LogLevel.DEBUG(1,new Throwable(),"operatorfilter1: "+operatorfilter1);


String test_orders_radio=(request.getParameter("test_orders")!=null)?request.getParameter("test_orders"):"";

LogLevel.DEBUG(1,new Throwable(),"test_orders_radio: "+test_orders_radio);

String orders_of=(request.getParameter("orders_of")!=null)?request.getParameter("orders_of"):"";
LogLevel.DEBUG(1,new Throwable(),"orders_of: "+orders_of);
if(orders_of==null || orders_of.equals(""))
orders_of="0";
LogLevel.DEBUG(1,new Throwable(),"orders_of: "+orders_of);

// Keep all the headings in session
Vector vectHeaderwithord = new Vector();
vectHeaderwithord.add("ORD No");
vectHeaderwithord.add("Cust Name");
vectHeaderwithord.add("Cust Emailid");
vectHeaderwithord.add("Cust Mobile");
vectHeaderwithord.add("Ret Name");
vectHeaderwithord.add("Ret Mobile");
vectHeaderwithord.add("Battery");
vectHeaderwithord.add("Model");
vectHeaderwithord.add("City");
vectHeaderwithord.add("Price");
vectHeaderwithord.add("price(OBRP)");
vectHeaderwithord.add("Ordered Status");
vectHeaderwithord.add("Ordered Reasons");
vectHeaderwithord.add("Agent Comments");
vectHeaderwithord.add("Agent Name");
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
.battery_order_type {
    background: green;
    color: white;
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
//$(".messages").animate({ scrollTop: $(document).height() }, "slow");
 //return true;
//document.misbatstatus.focusable.focus();
$('#div1').scrollTop($('#div1')[0].scrollHeight);
});

$(document).ready(function()
{
		$("input[name='TestOrders_radio'][value='<%=test_orders_radio%>']").prop('checked', true);
});
$(document).ready(function()
{
	$("#orders_of").val("<%=orders_of%>");
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
	var statusfilter = document.misbatstatus.statusfilter.value;
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
		errMsg =errmsg1+"<option value='installed' >Installed</option><option value='In Process' >In Process</option><option value='Not Installed' >Not Installed</option><option value='cancelled-franchisee-offbushrs' >Not Installed - Cancelled-Franchisee-OffBusHrs</option><option value='cancelled-franchisee-denied' >Not Installed - Cancelled-Franchisee-Denied</option><option value='cancelled-franchisee-notresponded' >Not Installed - Cancelled-Franchisee-NotResponded</option><option value='cancelled-franchisee-modeloutofstock' >Not Installed - Cancelled-Franchisee-ModelOutofStock</option><option value='cancelled-customer' >Not Installed - Cancelled-Customer</option><option value='cancelled-regenerated' >Not Installed - Cancelled-Regenerated</option><option value='Not Installed-Customer-Denied-After going to his place'>Not Installed-Customer-Denied-After going to his place</option><option value='Forwarded from BookBattery'>Forwarded from BookBattery</option><option value='Regenerated to Another Retailer'>Regenerated to Another Retailer</option><option value='Modify Battery Details'>Modify Battery Details</option><option value='Not Confirmed Order - Customer Confirmed to Place Order'>Not Confirmed Order - Customer Confirmed to Place Order</option><option value='Update'>Update</option>";
	}
	else if(statusfilter=="Customer Not Contacted")
	{
		errMsg =errmsg1+"<option value='Phone Busy' >Phone Busy</option><option value='Phone Not Reachable'>Phone Not Reachable</option><option value='Phone Switched Off'>Phone Switched Off</option>";
	}
	else if(statusfilter=="Postponed" || statusfilter=="postponed")
	{
		errMsg =errmsg1+"<option value='High Priority' >High Priority</option><option value='Customer is not picking the Call' >Customer is not picking the Call</option><option value='Customer Number Busy'>Customer Number is Busy</option><option value='Customer Number is Not Reachable' >Customer Number is Not Reachable</option><option value='Customer Number Switched Off' >Customer Number is Switched Off</option><option value='Customer is Out of Station' >Customer is Out of Station</option><option value='Customer is Not Responding to our Calls' >Customer is Not Responding to our Calls</option><option value='Customer need Installation Today' >Customer need Installation Tomorrow</option><option value='Need to check with franchisee whether the battery is currently available' >Franchisee is not having the Stock</option><option value='Customer Car Old battery is Working Fine need installation today' >Customer Car Old battery is Working Fine Presently</option><option value='Car Old Battery is working Fine He need installation Today' >Car Old Battery is working Fine, He donot need installation Now</option><option value='Yesterdays After Business Hour Order' >As it is not business hours, we will install the battery tomorrow</option><option value='Pitstop is So Far Customer will collect the battery whenever he is free' >Pitstop is So Far, Customer will collect the battery whenever he is free</option><option value='Customer need installation today' >Customer is busy today, he need installation later</option><option value='Customer need latest Manufacturing Battery' >Customer need latest Manufacturing Battery</option><option value='Order Status is not yet confirmed from Franchisee or Customer' >Order Status is not yet confirmed from Franchisee or Customer</option><option value='Customer will collect the battery today' >Customer will collect the battery tomorrow</option><option value='Customer will collect the battery today' >Customer Bike Old battery is Working Fine Presently</option><option value='Pitstop was on Leave Yesterday Need to process the order today' >Franchisee is on Leave Today</option>";
	}
	if(statusfilter=="Repeated")
	{
		errMsg =errmsg1+"<option value='Customer Has Placed Order For Twice' >Customer Has Placed Order For Twice</option>";
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
		
		
		var data2="<%=batterytypefilter1%>";
		var data2array=data2.split(",");
		$("#batterytypefilter").val(data2array);
		$("#batterytypefilter").multiselect("refresh");
		temp_break="1";
	}

}

/** Function to change Orders status **/
function Updatevisitorstatus(orderid,ordernumber,state,city,batterytype,cusmobile, PaymentMode, order_type)
{
	//alert(orderid);
		//alert(ordernumber);

	var orderedstatus = document.getElementById(orderid).value;
	
	//alert(orderedstatus);
	if(orderedstatus=="Customer Contacted")
	{
		CustomerContacted(orderid,ordernumber,orderedstatus,state,city,batterytype,cusmobile, PaymentMode, order_type)
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
		Ordertoretailer(orderid,ordernumber,orderedstatus,state,city,batterytype,cusmobile, PaymentMode, order_type)
	}
	
	else if(orderedstatus=="")
	{
	}
	else
	{
		var xmlhttp= "";
		var resp= "";
		var url ="../../../servlet/MISOperatorBatteryDetails?hidWhatToDo=updateorderstatus&orderid="+orderid+"&ordernumber="+ordernumber+"&orderstatus="+orderedstatus;
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
		/*var agree=confirm("Are you sure want to update the Order Status! ");
		if (agree)
		{*/
			xmlhttp.open("GET",url, true);		
			xmlhttp.send();	
		/*}*/
		
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

function CustomerContacted(orderid,ordernumber,orderstatus,state,city,batterytype,cusmobile, PaymentMode, order_type)
{
	var PaymentModeClass="";
	if(PaymentMode=="Online")
	{
		PaymentModeClass="hide";
	}else{
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

	if(batterytype=="Inverter Batteries" || batterytype=="Flat Plate Battery" || batterytype=="Tubular Battery" || batterytype=="Tall Tubular Battery" )
	{
		$.ajax
		({
			type: "GET",
			url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getbat_brand",
			data: {batterytype: batterytype,keyword :"Common"},
			success: function(data)
			{	
				$("#batbrand").html(data)
			}
		});
	errMsg ="<table  width='340' cellspacing='10' cellpadding='0' bgcolor='#FFFFFF' border='0'><tr><td><table width='340' bgcolor='#FFFFFF' valign='top' border='0'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobdiv();\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr><td height='5' width='80%'></td><td height='5' width='20%'></td></tr><tr><td width='63%' class='insidecontent' align='center' style='width:155px;  bgcolor:#FFF;'><div class='styled-select' align='center'><select name='orderreason'  id='orderreason' class='insidecontent' STYLE='width: 200px;' align='center' onChange=\"javascript:setagentcomments('"+orderid+"','"+ordernumber+"','"+orderstatus+"','"+state+"','"+city+"','"+batterytype+"','"+cusmobile+"','"+PaymentMode+"','"+order_type+"')\"><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Reason--&gt;</option><option value='installed' >Installed</option><option value='In Process' >In Process</option><option value='Not Installed' >Not Installed</option><option value='cancelled-franchisee-offbushrs' >Not Installed - Cancelled-Franchisee-OffBusHrs</option><option value='cancelled-franchisee-denied' >Not Installed - Cancelled-Franchisee-Denied</option><option value='cancelled-franchisee-notresponded' >Not Installed - Cancelled-Franchisee-NotResponded</option><option value='cancelled-franchisee-modeloutofstock' >Not Installed - Cancelled-Franchisee-ModelOutofStock</option><option value='cancelled-customer' >Not Installed - Cancelled-Customer</option><option value='cancelled-regenerated' >Not Installed - Cancelled-Regenerated</option><option value='Not Installed-Customer-Denied-After going to his place'>Not Installed-Customer-Denied-After going to his place</option><option value='Forwarded from BookBattery'>Forwarded from BookBattery</option><option value='Regenerated to Another Retailer'>Regenerated to Another Retailer</option><option class='"+PaymentModeClass+"' value='Modify Battery Details'>Modify Battery Details</option><option class='"+PaymentModeClass+"' value='Not Confirmed Order - Customer Confirmed to Place Order'>Not Confirmed Order - Customer Confirmed to Place Order</option><option value='Update'>Update</option></select></div></td></tr><tr id='rettoorder1' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Select&nbsp;Retailer</td></tr><tr height='10' id='rettoorder' style='display:none;'><td width='100%' align='center'><select onChange=\"javascript:setagentcomments_retailer();\" name='retailertoorder'  id='retailertoorder' class='insidecontent' STYLE='width: 200px;' align='center' ><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Retailer--&gt;</option></select></td></tr>	<tr height='10' id='modifybatdetls13' readonly><td width='100%' align='center'><input type='text' value='"+state+"' name='constate' id='constate' style='display:none';></td></tr><tr height='10' id='modifybatdetls14' readonly><td width='100%' align='center'><input type='text' value='"+city+"' name='concity' id='concity' style='display:none';></td></tr><tr height='10' id='modifybatdetls12' readonly><td width='100%' align='center'><input type='text' value='"+batterytype+"' name='battype' id='battype' style='display:none';></td></tr>		<tr id='modifybatdetls4' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Select&nbsp;Brand</td></tr><tr height='10' id='modifybatdetls5' style='display:none;'><td width='100%' align='center'><select name='batbrand'  id='batbrand' class='insidecontent' STYLE='width: 200px;' align='center' onchange='javascript:getbatmodels();'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Brand--&gt;</option></select></td></tr><tr id='modifybatdetls6' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Select&nbsp;Model</td></tr><tr height='10' id='modifybatdetls7' style='display:none;'><td width='100%' align='center'><select name='batmodel'  id='batmodel' class='insidecontent' STYLE='width: 200px;' align='center' onchange='javascript:getbatpricedetls();'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Model--&gt;</option></select></td></tr><tr id='modifybatdetls8' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>With out Old Battery Price</td></tr><tr height='10' id='modifybatdetls9' style='display:none;'><td width='100%' align='center'><input type='text' value='' name='batprice' id='batprice'></td></tr><tr id='modifybatdetls10' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>With Old Battery Price</td></tr><tr height='10' id='modifybatdetls11' style='display:none;'><td width='100%' align='center'><input type='text' value='' name='witholdbatprice' id='witholdbatprice'></td></tr>           <tr  class='Quantity_tr' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>&nbsp;Enter&nbsp;Quantity</td> </tr> <tr  class='Quantity_tr' style='display:none;'> <td width='100%' align='center'>&nbsp;<input class='insidecontent' type='text' autocomplete='off' name='Quantity' id='Quantity' value='1' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='50'></td></tr>                   <tr id='confirmord1' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Customer Name</td></tr><tr height='10' id='confirmord2' style='display:none;'><td width='100%' align='center'><input type='text'  name='cusname' id='cusname'></td></tr><tr id='confirmord3' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Customer Email ID</td></tr><tr height='10' id='confirmord4' style='display:none;'><td width='100%' align='center'><input type='text' name='cusemailid' id='cusemailid'></td></tr><tr id='confirmord5' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Customer Address1</td></tr><tr height='10' id='confirmord6' style='display:none;'><td width='100%' align='center'><input type='text' name='cusaddr1' id='cusaddr1'></td></tr><tr id='confirmord7' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Customer Address2</td></tr><tr height='10' id='confirmord8' style='display:none;'><td width='100%' align='center'><input type='text'  name='cusaddr2' id='cusaddr2'></td></tr><tr id='confirmord9' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Select&nbsp;Area</td></tr><tr height='10' id='confirmord10' style='display:none;'><td width='100%' align='center'><select name='cusarea'  id='cusarea' class='insidecontent' STYLE='width: 200px;' align='center'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Area--&gt;</option></select></td></tr> <tr id='confirmord11' style='display:none;' ><td width='63%' class='insidecontent' align='center' style='width:155px;  bgcolor:#FFF;'><div class='styled-select' align='center'><select onChange='javascript:setagentcomments_payment();' name='payment_mode'  id='payment_mode' class='insidecontent' STYLE='width: 200px;' align='center' ><option value='0' >&lt;-- Select Payment Method--&gt;</option> <option value='Cash' >Cash</option> <option value='Credit Card' >Credit Card</option> <option value='Debit Card' >Debit Card</option> <option value='Online' >Online</option> <option value='Cheque' >Cheque</option> </select></div></td></tr><tr id='order_type_div' style='display:none;' ><td width='63%' class='insidecontent' align='center' style='width:155px;  bgcolor:#FFF;'><div class='styled-select' align='center'><select  name='order_type'  id='order_type' class='insidecontent' STYLE='width: 200px;' align='center' ><option value='0' >&lt;-- Select Order Type--&gt;</option> <option value='New' >New</option> <option value='Replaced' >Replaced</option> </select></div></td></tr><tr><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;Message</td></tr><tr height='10'><td width='100%' align='center'><textarea name='cusmessage' id='cusmessage' rows='6' cols='30'></textarea></td></tr><tr>&nbsp;&nbsp;&nbsp;<td  width='80%' align='center'><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:updatecustcontactedstatus('"+orderid+"','"+ordernumber+"','"+orderstatus+"','"+cusmobile+"');\"></td><td width='20%'></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr height='16'><td width='80%' align='center' class='subheading' id='displayrefinederrormsg1'></td><td width='20%'></td></tr><tr><td height='10'></td></tr></table></td></tr></table>";



	}
	else
	{

		$.ajax
		({
			type: "GET",
			url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getvehiclename",
			data: {batterytype: batterytype},
			success: function(data)
			{	
				$("#vehmake").html(data)
			}
		});


	errMsg ="<table  width='340' cellspacing='10' cellpadding='0' bgcolor='#FFFFFF' border='0'><tr><td><table width='340' bgcolor='#FFFFFF' valign='top' border='0'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobdiv();\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr><td height='5' width='80%'></td><td height='5' width='20%'></td></tr><tr><td width='63%' class='insidecontent' align='center' style='width:155px;  bgcolor:#FFF;'><div class='styled-select' align='center'><select name='orderreason'  id='orderreason' class='insidecontent' STYLE='width: 200px;' align='center' onChange=\"javascript:setagentcomments('"+orderid+"','"+ordernumber+"','"+orderstatus+"','"+state+"','"+city+"','"+batterytype+"','"+cusmobile+"','"+PaymentMode+"','"+order_type+"')\"><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Reason--&gt;</option><option value='installed' >Installed</option><option value='In Process' >In Process</option><option value='Not Installed' >Not Installed</option><option value='cancelled-franchisee-offbushrs' >Not Installed - Cancelled-Franchisee-OffBusHrs</option><option value='cancelled-franchisee-denied' >Not Installed - Cancelled-Franchisee-Denied</option><option value='cancelled-franchisee-notresponded' >Not Installed - Cancelled-Franchisee-NotResponded</option><option value='cancelled-franchisee-modeloutofstock' >Not Installed - Cancelled-Franchisee-ModelOutofStock</option><option value='cancelled-customer' >Not Installed - Cancelled-Customer</option><option value='cancelled-regenerated' >Not Installed - Cancelled-Regenerated</option><option value='Not Installed-Customer-Denied-After going to his place'>Not Installed-Customer-Denied-After going to his place</option><option value='Forwarded from BookBattery'>Forwarded from BookBattery</option><option value='Regenerated to Another Retailer'>Regenerated to Another Retailer</option><option value='Modify Battery Details' class='"+PaymentModeClass+"'>Modify Battery Details</option><option class='"+PaymentModeClass+"' value='Not Confirmed Order - Customer Confirmed to Place Order'>Not Confirmed Order - Customer Confirmed to Place Order</option><option value='Update'>Update</option></select></div></td></tr><tr id='rettoorder1' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Select&nbsp;Retailer</td></tr><tr height='10' id='rettoorder' style='display:none;'><td width='100%' align='center'><select onChange=\"javascript:setagentcomments_retailer();\" name='retailertoorder'  id='retailertoorder' class='insidecontent' STYLE='width: 200px;' align='center' ><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Retailer--&gt;</option></select></td></tr>	<tr height='10' id='modifybatdetls13' readonly><td width='100%' align='center'><input type='text' value='"+state+"' name='constate' id='constate' style='display:none';></td></tr><tr height='10' id='modifybatdetls14' readonly><td width='100%' align='center'><input type='text' value='"+city+"' name='concity' id='concity' style='display:none';></td></tr><tr height='10' id='modifybatdetls12' readonly><td width='100%' align='center'><input type='text' value='"+batterytype+"' name='battype' id='battype' style='display:none';></td></tr>		<tr id='modifybatdetls' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Select&nbsp;Make</td></tr><tr height='10' id='modifybatdetls1' style='display:none;'><td width='100%' align='center'><select name='vehmake'  id='vehmake' class='insidecontent' STYLE='width: 200px;' align='center' onchange='javascript:getvehmodels();' ><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Make--&gt;</option></select></td></tr><tr id='modifybatdetls2' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Select&nbsp;Model</td></tr><tr height='10' id='modifybatdetls3' style='display:none;'><td width='100%' align='center'><select name='vehmodel'  id='vehmodel' class='insidecontent' STYLE='width: 200px;' align='center' onchange='javascript:getbatbrands();' ><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Model--&gt;</option></select></td></tr><tr id='modifybatdetls4' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Select&nbsp;Brand</td></tr><tr height='10' id='modifybatdetls5' style='display:none;'><td width='100%' align='center'><select name='batbrand'  id='batbrand' class='insidecontent' STYLE='width: 200px;' align='center' onchange='javascript:getbatmodels();'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Brand--&gt;</option></select></td></tr><tr id='modifybatdetls6' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Select&nbsp;Model</td></tr><tr height='10' id='modifybatdetls7' style='display:none;'><td width='100%' align='center'><select name='batmodel'  id='batmodel' class='insidecontent' STYLE='width: 200px;' align='center' onchange='javascript:getbatpricedetls();'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Model--&gt;</option></select></td></tr><tr id='modifybatdetls8' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>With out Old Battery Price</td></tr><tr height='10' id='modifybatdetls9' style='display:none;'><td width='100%' align='center'><input type='text' value='' name='batprice' id='batprice'></td></tr><tr id='modifybatdetls10' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>With Old Battery Price</td></tr><tr height='10' id='modifybatdetls11' style='display:none;'><td width='100%' align='center'><input type='text' value='' name='witholdbatprice' id='witholdbatprice'></td></tr>          <tr  class='Quantity_tr' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>&nbsp;Enter&nbsp;Quantity</td> </tr> <tr  class='Quantity_tr' style='display:none;'> <td width='100%' align='center'>&nbsp;<input class='insidecontent' type='text' autocomplete='off' name='Quantity' id='Quantity' value='1' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='50'></td></tr>                 <tr id='confirmord1' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Customer Name</td></tr><tr height='10' id='confirmord2' style='display:none;'><td width='100%' align='center'><input type='text'  name='cusname' id='cusname'></td></tr><tr id='confirmord3' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Customer Email ID</td></tr><tr height='10' id='confirmord4' style='display:none;'><td width='100%' align='center'><input type='text' name='cusemailid' id='cusemailid'></td></tr><tr id='confirmord5' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Customer Address1</td></tr><tr height='10' id='confirmord6' style='display:none;'><td width='100%' align='center'><input type='text' name='cusaddr1' id='cusaddr1'></td></tr><tr id='confirmord7' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Customer Address2</td></tr><tr height='10' id='confirmord8' style='display:none;'><td width='100%' align='center'><input type='text'  name='cusaddr2' id='cusaddr2'></td></tr><tr id='confirmord9' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Select&nbsp;Area</td></tr><tr height='10' id='confirmord10' style='display:none;'><td width='100%' align='center'><select name='cusarea'  id='cusarea' class='insidecontent' STYLE='width: 200px;' align='center'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Area--&gt;</option></select></td></tr> <tr id='confirmord11' style='display:none;' ><td width='63%' class='insidecontent' align='center' style='width:155px;  bgcolor:#FFF;'><div class='styled-select' align='center'><select onChange='javascript:setagentcomments_payment();' name='payment_mode'  id='payment_mode' class='insidecontent' STYLE='width: 200px;' align='center' ><option value='0' >&lt;-- Select Payment Method--&gt;</option> <option value='Cash' >Cash</option> <option value='Credit Card' >Credit Card</option> <option value='Debit Card' >Debit Card</option> <option value='Online' >Online</option> <option value='Cheque' >Cheque</option> </select></div></td></tr><tr id='order_type_div' style='display:none;' ><td width='63%' class='insidecontent' align='center' style='width:155px;  bgcolor:#FFF;'><div class='styled-select' align='center'><select  name='order_type'  id='order_type' class='insidecontent' STYLE='width: 200px;' align='center' ><option value='0' >&lt;-- Select Order Type--&gt;</option> <option value='New' >New</option> <option value='Replaced' >Replaced</option> </select></div></td></tr><tr><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;Message</td></tr><tr height='10'><td width='100%' align='center'><textarea name='cusmessage' id='cusmessage' rows='6' cols='30'></textarea></td></tr><tr>&nbsp;&nbsp;&nbsp;<td  width='80%' align='center'><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:updatecustcontactedstatus('"+orderid+"','"+ordernumber+"','"+orderstatus+"','"+cusmobile+"');\"></td><td width='20%'></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr height='16'><td width='80%' align='center' class='subheading' id='displayrefinederrormsg1'></td><td width='20%'></td></tr><tr><td height='10'></td></tr></table></td></tr></table>";
	

	}
	document.getElementById("divpostponed").innerHTML=""; 
	document.getElementById("divpostponed").style.display='block';
	document.getElementById("divpostponed").innerHTML=errMsg
}


function getvehmodels()
	{
				var batterytype = document.getElementById("battype").value; 
				var vehmake = document.getElementById("vehmake").value; 
				$.ajax
				({
					type: "GET",
					url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getvehicle_model",
					data: {vehiclename: vehmake,batterytype: batterytype},
					success: function(data)
					{	
						$("#vehmodel").html(data)
					}
				});
	}

	function getbatbrands()
	{
				
				$.ajax
				({
					type: "GET",
					url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getbat_brand",
					data: {keyword :"Common"},
					success: function(data)
					{	
						$("#batbrand").html(data)
					}
				});
	}

	function getbatmodels()
	{
		
				var batterytype = document.getElementById("battype").value;
				if(batterytype=="Inverter Batteries" || batterytype=="Flat Plate Battery" || batterytype=="Tubular Battery" || batterytype=="Tall Tubular Battery" )
	
		{
				batterytype="Inverter Batteries";
				var batbrand = document.getElementById("batbrand").value; 
				$.ajax
				({
					type: "GET",
					url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getbat_models_orders_update",
					data: {batterytype: batterytype,batbrand: batbrand},
					success: function(data)
					{	
						$("#batmodel").html(data)
					}
				});

		}
		else
		{
				var vehmake = document.getElementById("vehmake").value; 
				var vehmodel = document.getElementById("vehmodel").value; 
				var batbrand = document.getElementById("batbrand").value; 
				$.ajax
				({
					type: "GET",
					url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getbat_models_orders_update",
					data: {batterytype: batterytype,vehiclemake: vehmake,vehiclemodel: vehmodel,batbrand: batbrand},
					success: function(data)
					{	
						$("#batmodel").html(data)
					}
				});
		}
	}

	function getbatpricedetls()
	{
		var constate = document.getElementById("constate").value; 
		var concity = document.getElementById("concity").value; 
		var batbrand = document.getElementById("batbrand").value; 
		var batmodel = document.getElementById("batmodel").value; 
				
				$.ajax
				({
					type: "GET",
					url: "/bookbattery/servlet/BatteryHome?hidWhatToDo=getbatpricedetls",
					data: {constate: constate,concity: concity,batbrand: batbrand,batmodel: batmodel},
					success: function(data)
					{	
						//alert(data);
						//$("#batmodel").html(data)
						var results=data.split(",");
								var withoutoldbatprice=results[0];
								var witholdbatprice=results[1];

								document.getElementById("batprice").value=withoutoldbatprice;
								document.getElementById("witholdbatprice").value=witholdbatprice;

					}
				});
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

function Ordertoretailer(orderid,ordernumber,orderstatus,state,city,batterytype,cusmobile, PaymentMode, order_type)
{

		errMsg ="<table  width='340' cellspacing='10' cellpadding='0' bgcolor='#FFFFFF' border='0'><tr><td><table width='340' bgcolor='#FFFFFF' valign='top' border='0'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobdiv();\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr><td width='63%' class='insidecontent' align='center' style='width:155px;  bgcolor:#FFF;'><div class='styled-select' align='center'><select name='retailer'  id='retailer' class='insidecontent' STYLE='width: 300px;margin-left: 31px;' align='center'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Retailer--&gt;</option</select></div></td></tr><tr>&nbsp;&nbsp;&nbsp;<td  width='80%' align='center'><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:updatecustcontactedstatus('"+orderid+"','"+ordernumber+"','"+orderstatus+"');\"></td><td width='20%'></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr height='16'><td width='80%' align='center' class='subheading' id='displayrefinederrormsg1'></td><td width='20%'></td></tr><tr><td height='10'></td></tr></table></td></tr></table>";
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
				$("#retailer").html(data)
			}
		});
		
}


/** Function to change Orders status and reason **/
function updatecustcontactedstatus(orderid,ordernumber,orderstatus)
{
	
		//alert(orderstatus);
		var retailertoorder = "";
		var order_type ="";
		var batterytype = "";
		var vehmake = "";
		var vehmodel = "";
		var batbrand = "";
		var batmodel = "";
		var batprice = "";
		var witholdbatprice = "";
		var Quantity = "";
		
		var cusname="";
		var cusemailid="";
		var cusaddr1="";
		var cusaddr2="";
		var cusarea="";	
		var cusstate = ""; 
		var cuscity = ""; 
		var payment_mode = ""; 

		var iChars3 = "`~!@#$%^&*()+=[]\\\';/{}|\":<>?";
		var dot=".";

		
				
		if(orderstatus=="confirmed")
		{
			var orderreason = "Confirmed Order To Retailer";
			var cusmessage = "";
			//alert(orderreason);
			//alert(orderstatus);
		}
		else
		{
			var orderreason = document.misbatstatus.orderreason.value;
			var cusmessage = document.misbatstatus.cusmessage.value;
			
		 if(orderreason == "" || orderreason == "0")
		 {
			errMsg ="<font color='#9B5BDD'>Please Select Reason</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.misbatstatus.orderreason.focus();
			return ;
		 }
		
		/** validations starts here for agent message **/
			if(cusmessage == "")
		 {
			errMsg ="<font color='#9B5BDD'>Please Enter Message</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.misbatstatus.cusmessage.focus();
			return ;
		 }
		  if (document.misbatstatus.cusmessage.value.indexOf(dot)==0 )
		 {
				errMsg ="<font color='#9B5BDD'>Message should not start with dot</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misbatstatus.cusmessage.focus();
				return ;
		 }
		 
		 		
		if (/[a-z][A-Z]{2}/i.test(document.misbatstatus.cusmessage.value) != true) 
			{
			  errMsg ="<font color='#9B5BDD'>Please enter atleast 3 Charaters together in the Message Field.</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misbatstatus.cusmessage.focus();
				return ;
			 }
			
			
		}

		//alert(21);
		
		if(orderstatus=="postponed" || orderstatus=="Postponed")
		{
			
//alert(25);
			var postponedate = document.misbatstatus.postponedate.value;
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
				return ;
			}
			else if((postday<day)&&(postyear<year))
			{
				$('#displayrefinederrormsg1').show();
				errMsg ="<font color='#9B5BDD'>Selected Date should be greater than Today's Date</font>";
				document.getElementById("displayrefinederrormsg1").style.display='block';
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				return ;
				
			}
			else if((postday<day)&&(postmonth<=month)&&(postyear<=year))
			{
				$('#displayrefinederrormsg1').show();
				errMsg ="<font color='#9B5BDD'>Selected Date should be greater than Today's Date</font>";
				document.getElementById("displayrefinederrormsg1").style.display='block';
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				return ;
				
			}
			else if((postday>day)&&(postmonth<month)&&(postyear<=year))
			{
				$('#displayrefinederrormsg1').show();
				errMsg ="<font color='#9B5BDD'>Selected Date should be greater than Today's Date</font>";
				document.getElementById("displayrefinederrormsg1").style.display='block';
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;	
				return ;
			}	
			else if((postday>day)&&(postyear<year))
			{
				$('#displayrefinederrormsg1').show();
				errMsg ="<font color='#9B5BDD'>Selected Date should be greater than Today's Date</font>";
				document.getElementById("displayrefinederrormsg1").style.display='block';
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;	
				return ;
			}
			else if(postday==day&&(postmonth<=month)&&(postyear<=year))
			{
				$('#displayrefinederrormsg1').show();
				errMsg ="<font color='#9B5BDD'>Selected Date should be greater than Today's Date</font>";
				document.getElementById("displayrefinederrormsg1").style.display='block';
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;	
				return ;
			}
		}

		
		else if(orderstatus=="confirmed")
		{
			//alert(22);
			retailertoorder = document.misbatstatus.retailer.value;
			
			//alert(retailertoorder);

			if(retailertoorder == "" || retailertoorder == "0" || retailertoorder == "default" || retailertoorder == "defaultss")
			{
				errMsg ="<font color='#9B5BDD'>Please Select Retailer</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misbatstatus.retailer.focus();
				return ;
			}

		}

		else if(orderstatus=="Customer Contacted" && orderreason=="Regenerated to Another Retailer")
		{

			retailertoorder = document.misbatstatus.retailertoorder.value;

			if(retailertoorder == "" || retailertoorder == "0" || retailertoorder == "default" || retailertoorder == "defaultss")
			{
				errMsg ="<font color='#9B5BDD'>Please Select Retailer</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misbatstatus.retailertoorder.focus();
				return ;
			}

		}		
		

		else if(orderstatus=="Customer Contacted" && orderreason=="installed")
		{
			payment_mode = document.misbatstatus.payment_mode.value;
			order_type = document.misbatstatus.order_type.value;
			if(payment_mode == "" || payment_mode == "0" || payment_mode == "default" || payment_mode == "defaultss")
			{
				errMsg ="<font color='#9B5BDD'>Please Select Payment Mode</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misbatstatus.payment_mode.focus();
				return ;
			}
			
			if(order_type == "" || order_type == "0" || order_type == "default" || order_type == "defaultss")
			{
				errMsg ="<font color='#9B5BDD'>Please Select Type</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misbatstatus.order_type.focus();
				return ;
			}
		}
		else if(orderstatus=="Customer Contacted" && orderreason=="Modify Battery Details")
		{
				batterytype = document.getElementById("battype").value;
				
				if(batterytype=="Inverter Batteries" || batterytype=="Flat Plate Battery" || batterytype=="Tubular Battery" || batterytype=="Tall Tubular Battery" )	
				{
					vehmake = ""; 
					vehmodel = ""; 
				
					
				}
				else
				{

					vehmake = document.getElementById("vehmake").value; 
					vehmodel = document.getElementById("vehmodel").value; 

					if(vehmake == "" || vehmake == "0" || vehmake == "default" || vehmake == "defaultss")
					{
						errMsg ="<font color='#9B5BDD'>Please Select Vehicle Make</font>";
						document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
						document.misbatstatus.vehmake.focus();
						return ;
					}

					if(vehmodel == "" || vehmodel == "0" || vehmodel == "default" || vehmodel == "defaultss")
					{
						errMsg ="<font color='#9B5BDD'>Please Select Vehicle Model</font>";
						document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
						document.misbatstatus.vehmodel.focus();
						return ;
					}
					
				}

					batbrand = document.getElementById("batbrand").value; 
					batmodel = document.getElementById("batmodel").value; 
					batprice = document.getElementById("batprice").value; 
					witholdbatprice = document.getElementById("witholdbatprice").value;
					order_type = document.getElementById("order_type").value;
					Quantity = document.getElementById("Quantity").value;

					if(batbrand == "" || batbrand == "0" || batbrand == "default" || batbrand == "defaultss")
					{
						errMsg ="<font color='#9B5BDD'>Please Select Battery Brand</font>";
						document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
						document.misbatstatus.batbrand.focus();
						return ;
					}

					if(batmodel == "" || batmodel == "0" || batmodel == "default" || batmodel == "defaultss")
					{
						errMsg ="<font color='#9B5BDD'>Please Select Battery Model</font>";
						document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
						document.misbatstatus.batmodel.focus();
						return ;
					}
					
				if(order_type == "" || order_type == "0" || order_type == "default" || order_type == "defaultss")
				{
					errMsg ="<font color='#9B5BDD'>Please Select Battery Model</font>";
					document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
					document.misbatstatus.order_type.focus();
					return ;
				}
					
									
				var number_regex = /^\d+$/;
				if ((!Quantity.match(number_regex) && Quantity.length > 0) || (!Quantity.match(number_regex)) || (Quantity.length == 0) || (Quantity<=0) || (isNaN(parseFloat(Quantity))==true))
				{
					Quantity=1;
					errMsg ="<font color='#9B5BDD'>Please enter valid quantity...</font>";
					document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
					document.misbatstatus.Quantity.focus();
					return ;
				}

					
		}
		else if(orderstatus=="Customer Contacted" && orderreason=="Not Confirmed Order - Customer Confirmed to Place Order")
		{
			batterytype = document.getElementById("battype").value;
				
			if(batterytype=="Inverter Batteries" || batterytype=="Flat Plate Battery" || batterytype=="Tubular Battery" || batterytype=="Tall Tubular Battery" )	
			{

			}
			else
			{
				vehmake = document.getElementById("vehmake").value; 
				vehmodel = document.getElementById("vehmodel").value; 

				if(vehmake == "" || vehmake == "0" || vehmake == "default" || vehmake == "defaultss")
					{
						errMsg ="<font color='#9B5BDD'>Please Select Vehicle Make</font>";
						document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
						document.misbatstatus.vehmake.focus();
						return ;
					}

					if(vehmodel == "" || vehmodel == "0" || vehmodel == "default" || vehmodel == "defaultss")
					{
						errMsg ="<font color='#9B5BDD'>Please Select Vehicle Model</font>";
						document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
						document.misbatstatus.vehmodel.focus();
						return ;
					}
			}

			batbrand = document.getElementById("batbrand").value; 
			batmodel = document.getElementById("batmodel").value; 
			batprice = document.getElementById("batprice").value; 
			witholdbatprice = document.getElementById("witholdbatprice").value;
			cusname = document.getElementById("cusname").value; 
			cusemailid = document.getElementById("cusemailid").value; 
			cusaddr1 = document.getElementById("cusaddr1").value; 
			cusaddr2 = document.getElementById("cusaddr2").value;
			cusstate = document.getElementById("constate").value; 
			cuscity = document.getElementById("concity").value; 
			cusarea = document.getElementById("cusarea").value;
			order_type = document.getElementById("order_type").value;
			Quantity = document.getElementById("Quantity").value;
			
			if(batbrand == "" || batbrand == "0" || batbrand == "default" || batbrand == "defaultss")
			{
				errMsg ="<font color='#9B5BDD'>Please Select Battery Brand</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misbatstatus.batbrand.focus();
				return ;
			}

			if(batmodel == "" || batmodel == "0" || batmodel == "default" || batmodel == "defaultss")
			{
				errMsg ="<font color='#9B5BDD'>Please Select Battery Model</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misbatstatus.batmodel.focus();
				return ;
			}

			if(cusname == "" || cusname == "0" )
			{
				errMsg ="<font color='#9B5BDD'>Please Enter Customer Name</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misbatstatus.cusname.focus();
				return ;
			}

			if(cusemailid == "" || cusemailid == "0" )
			{
				errMsg ="<font color='#9B5BDD'>Please Enter Customer Email ID</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misbatstatus.cusemailid.focus();
				return ;
			}

			if(cusaddr1 == "" || cusaddr1 == "0" )
			{
				errMsg ="<font color='#9B5BDD'>Please Enter Customer Address1</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misbatstatus.cusaddr1.focus();
				return ;
			}

			if(cusaddr2 == "" || cusaddr2 == "0" )
			{
				errMsg ="<font color='#9B5BDD'>Please Enter Customer Address2</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misbatstatus.cusaddr2.focus();
				return ;
			}

			if(cusarea == "" || cusarea == "0"  || cusarea == "default"  || cusarea == "defaultss" )
			{
				errMsg ="<font color='#9B5BDD'>Please Select Customer Area</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misbatstatus.cusarea.focus();
				return ;
			}
			
			
			if(order_type == "" || order_type == "0" || order_type == "default" || order_type == "defaultss")
			{
				errMsg ="<font color='#9B5BDD'>Please Select Battery Model</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misbatstatus.order_type.focus();
				return ;
			}
				
								
			var number_regex = /^\d+$/;
			if ((!Quantity.match(number_regex) && Quantity.length > 0) || (!Quantity.match(number_regex)) || (Quantity.length == 0) || (Quantity<=0) || (isNaN(parseFloat(Quantity))==true))
			{
				Quantity=1;
				errMsg ="<font color='#9B5BDD'>Please enter valid quantity...</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misbatstatus.Quantity.focus();
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
		var url ="../../../servlet/MISOperatorBatteryDetails?hidWhatToDo=updateorderstatus&orderid="+orderid+"&ordernumber="+ordernumber+"&orderstatus="+orderstatus+"&orderreason="+orderreason+"&agentcomments="+cusmessage+"&postponedate="+postponedate+"&retailertoorder="+retailertoorder+"&batterytype="+batterytype+"&vehmake="+vehmake+"&vehmodel="+vehmodel+"&batbrand="+batbrand+"&batmodel="+batmodel+"&batprice="+batprice+"&witholdbatprice="+witholdbatprice+"&cusname="+cusname+"&cusemailid="+cusemailid+"&cusaddr1="+cusaddr1+"&cusaddr2="+cusaddr2+"&cusstate="+cusstate+"&cuscity="+cuscity+"&cusarea="+cusarea+"&order_type="+order_type+"&payment_mode="+payment_mode+"&Quantity="+Quantity;
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
		/*var agree=confirm("Are You sure want to update the Order Status! ");
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
/** Function to get order details after changing order status and reason **/
function getupdatedorders()
{
	$('#divpostponed').hide();

	var city=document.misbatstatus.city.value;
	var keyword=document.misbatstatus.keyword.value;
	var strDateOpt = document.misbatstatus.dates.value;
	var strFromDate = document.misbatstatus.txtFromDate.value;
	var strToDate = document.misbatstatus.txtToDate.value;

	document.misbatstatus.method="POST";
	document.misbatstatus.action="../../../servlet/MISOperatorBatteryDetails?hidWhatToDo=getconfirmedordersmis&city="+city+"&keyword="+keyword+"&dates="+strDateOpt+"&txtFromDate="+strFromDate+"&txtToDate="+strToDate;
	document.misbatstatus.submit();
	
}
function setagentcomments(orderid,ordernumber,orderstatus,state,city,batterytype,cusmobile, PaymentMode, order_type)
{
	var orderreason = document.misbatstatus.orderreason.value;

	if(orderreason =="Regenerated to Another Retailer")
	{
		$('#rettoorder').show();
		$('#rettoorder1').show();
		$('#confirmord11').hide();
		$('.Quantity_tr').hide();

	}

	else if(orderreason =="Modify Battery Details")
	{
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
		$('#confirmord11').hide();
		$('#order_type_div').show();
		$('.Quantity_tr').show();
		//$('#rettoorder1').show();

	}
	else if(orderreason =="Not Confirmed Order - Customer Confirmed to Place Order")
	{
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

		$('#confirmord11').hide();
		$('#order_type_div').show();
		$('.Quantity_tr').show();
	}
	else if(orderreason =="installed")
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
		
		
		if(PaymentMode =="Online")
		{
			$('#payment_mode').html("<option value='Online' selected >Online</option>");
			$('#order_type').after("<p style='color: red; font-size: 14px; font-weight: 600;'>User Ordered for :"+order_type+"</p>");
			$('#order_type').val(order_type);
		}
		
		$('#confirmord11').show();
		$('#order_type_div').show();
		
		
		$('.Quantity_tr').hide();
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
		$('#order_type_div').hide();
		$('.Quantity_tr').hide();

	}

	document.misbatstatus.cusmessage.value=orderreason;
}
function setagentcomments_payment()
{
	var payment_mode_temp = document.misbatstatus.payment_mode.value;
	if(payment_mode_temp == "" || payment_mode_temp == "0" || payment_mode_temp == "default" || payment_mode_temp == "defaultss")
	{
		errMsg ="<font color='#9B5BDD'>Please Select Payment Mode</font>";
		document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
		document.misbatstatus.payment_mode.focus();
		return ;
	}
	
	document.misbatstatus.cusmessage.value="Installed - Payment done by "+ payment_mode_temp;
}
/** Function to get Orders details when agent selects filter **/
function getupdatedvisitors1(strDateOpts,strFromDates,strToDates,keyword,city)
{
	var statusfilter = document.misbatstatus.statusfilter.value;
	var substatusfilter = $("#substatusfilter").val();
	var retselectname = document.misbatstatus.retnameselect.value;
	//alert(substatusfilter);
	var strUserid = document.misbatstatus.strUserid.value;
	//alert(strUserid);
	
	var operatorfilter = "";
	var fromdatefilter = "";
	var fromtimefilter = "";
	var todatefilter = "";
	var totimefilter = "";
	var orders_of = $("#orders_of").val();

	if(strUserid=="operator")
	{
		operatorfilter = document.misbatstatus.operatorfilter.value;
	}
	else
	{
	}	fromdatefilter = $("#fromdatefilter").val();
	fromtimefilter = $("#fromtimefilter").val();
	todatefilter = $("#todatefilter").val();
	totimefilter = $("#totimefilter").val();
	
	if(fromdatefilter!="" && todatefilter!="")
	{
		strDateOpts="selected";
	}
	else
	{
	}
	
	var selected;

	var batterytypefilterselect;

	selected = $("#batterytypefilter option:selected");
     batterytypefilterselect = "";
        selected.each(function () {
            batterytypefilterselect += "'" + $(this).val() + "',";
        });

		if(batterytypefilterselect=="")
		{
			batterytypefilterselect="All";
		}
		else
		{
			batterytypefilterselect = batterytypefilterselect.replace(/(^,)|(,$)/g, "")
				//alert(message);
			batterytypefilterselect=batterytypefilterselect;

		}

	var TestOrders_radio = $("input[name='TestOrders_radio']:checked").val()
		
	document.misbatstatus.method="POST";
	document.misbatstatus.action="../../../servlet/MISOperatorBatteryDetails?hidWhatToDo=getconfirmedordersmis&city="+city+"&keyword="+keyword+"&dates="+strDateOpts+"&txtFromDate="+strFromDates+"&txtToDate="+strToDates+"&statusfilter="+statusfilter+"&substatusfilter="+substatusfilter+"&batterytypefilter="+batterytypefilterselect+"&retselectname="+retselectname+"&operatorfilter="+operatorfilter+"&fromdatefilter="+fromdatefilter+"&fromtimefilter="+fromtimefilter+"&todatefilter="+todatefilter+"&totimefilter="+totimefilter+"&test_orders="+TestOrders_radio+"&orders_of="+orders_of;
	document.misbatstatus.submit();
	
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
<form name="misbatstatus">
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
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#ffffff" >
		<tr>
		
			<td width="75%" align="left" valign="top">
				<table width="100%" border="1" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">
					<!-- your page content starts here  -->
					<table width="100%" cellspacing="1" align="center" bgcolor="#FFFFFF" cellpadding="0" border="0">
					<table width="450" border="0" align="center" cellpadding="5" cellspacing="0" bgcolor="#FFFFFF">
					
					<tr><td align="left" ><a href="../../../jsp/operator/ordermis/misselconfirm.jsp?keyword=<%=keyword%>&dates=<%=strDateOpt%>&txtFromDate=<%=strFromDate%>&txtToDate=<%=strToDate%>" class="onclick1">Back</a>
					
					&nbsp;&nbsp;&nbsp;Orders Status : <select style='width:215px;background-color: #ffffff;color: #000;padding: .5em;padding-right: 2.5em;margin: 0;border-radius: 3px;text-indent: 0.01px;' name="statusfilter" id="statusfilter" onChange="javascript:getsubstatusoptions()"><option value='<%=statusfilter1%>'><%=statusfilter1%></option><option value=''>All</option><option value="Not Confirmed">Not Confirmed</option><option value="confirmed">Confirmed</option><option value='Customer Contacted'>Customer Contacted</option><option value='Customer Not Contacted'>Customer Not Contacted</option><option value='Postponed'>Postponed</option><option value='Repeated'>Repeated</option><option value='SortByCreationDate'>SortByCreationDate</option></select>
					
					&nbsp;&nbsp;&nbsp;&nbsp;Orders Sub Status : <select style='width:215px;background-color: #ffffff;color: #000;padding: .5em;padding-right: 2.5em;margin: 0;border-radius: 3px;text-indent: 0.01px;' class="multiselect"  name="substatusfilter" id="substatusfilter"  multiple="multiple"><option value='<%=substatusfilter1%>'><%=substatusfilter1%></option><option value=''>Select Sub Status</option></select>
					
					
					&nbsp;&nbsp;&nbsp; Battery Type : <select style='width:215px;background-color: #ffffff;color: #000;padding: .5em;padding-right: 2.5em;margin: 0;border-radius: 3px;text-indent: 0.01px;' name="batterytypefilter" id="batterytypefilter"  multiple="multiple">
							<option  value="Car Batteries" >Car Batteries</option>
							<option  value="Inverter Batteries" >Inverter Batteries</option>
							<option  value="Bike Batteries" >Bike Batteries</option>
							<option  value="Bus Batteries" >Bus Batteries</option>
							<option  value="Tractor Batteries" >Tractor Batteries</option>
							<option  value="Truck Batteries" >Truck Batteries</option>
							<option  value="Three Wheeler Batteries" >Three Wheeler Batteries</option>
							<option  value="Special Vehicle Batteries" >Special Vehicle Batteries</option>
							<option  value="Genset Batteries" >Genset Batteries</option>
							<option  value="Crane Batteries" >Crane Batteries</option>
							<option  value="Roller Batteries" >Roller Batteries</option>
							<option  value="Loader Batteries" >Loader Batteries</option>
							<option  value="Dozer Batteries" >Dozer Batteries</option>
							<option  value="Excavator Batteries" >Excavator Batteries</option>
							<option  value="Tyre Handler Batteries" >Tyre Handler Batteries</option>
							<option  value="Hydraulic Shovel Batteries" >Hydraulic Shovel Batteries</option>
							<option  value="Harvestor Batteries" >Harvestor Batteries</option>
							<option  value="Generator Batteries" >Generator Batteries</option>
							<option  value="Compactor Batteries" >Compactor Batteries</option>
							<option  value="Telescopic Handler Batteries" >Telescopic Handler Batteries</option>
							<option  value="Forwarder Batteries" >Forwarder Batteries</option>
							<option  value="Wheeled Harvester Batteries" >Wheeled Harvester Batteries</option>
							<option  value="Minibus Batteries" >Minibus Batteries</option>
							<option  value="Dumper Batteries" >Dumper Batteries</option>
							<option  value="Construction Equipment Batteries" >Construction Equipment Batteries</option>
							<option  value="Hydralic Excavator Batteries" >Hydralic Excavator Batteries</option></select>
							
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
							<% if(strUserid.equals("operator"))
							{%>
																		
								&nbsp;&nbsp;&nbsp;Select Operator : <select style='width:215px;background-color: #ffffff;color: #000;padding: .5em;padding-right: 2.5em;margin: 0;border-radius: 3px;text-indent: 0.01px;' name="operatorfilter" id="operatorfilter" ><option value='<%=operatorfilter1%>'><%=operatorfilter1%></option><option value=''>Select Operator</option><option value="operator1">Operator1</option><option value='operator2'>Operator2</option><option value='operator3'>Operator3</option><option value='operator4'>Operator4</option></select>
								
							<%}
							else
							{
							}%>
							
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
												<td bgcolor="#0081C7" align="center" class="subheading"><font color="#FFFFFF"><%=keyword%>&nbsp;Battery&nbsp;Order&nbsp;Details&nbsp;Summary</font></td>
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
																<td bgcolor="#DAD0E1" align="left" class="insidecontent" >&nbsp;Selected Battery Type</td><td align="left" class="insidecontent" >:&nbsp;<%=batterytypefilter1%></td>
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
																		<th align="center" class="subheading"><font color="#000000">Sl&nbsp;No</font></th>
																		<th align="center" class="subheading"><font color="#000000">ORD&nbsp;No</font></th>
																		<th align="center" class="subheading"><font color="#000000">Cus&nbsp;Name</font></th>
																		<th align="center" class="subheading"><font color="#000000">Cus&nbsp;Emailid</font></th>
																		<th align="center" class="subheading"><font color="#000000"> Cus&nbsp;Mobile</font></th>
																		<th align="center" class="subheading"><font color="#000000">Ret&nbsp;Name</font></th>
																		<th align="center" class="subheading"><font color="#000000">Ret&nbsp;Mobile</font></th>
																		<th align="center" class="subheading"><font color="#000000">Bat&nbsp;Type</font></th>
																		<th align="center" class="subheading"><font color="#000000">Qty</font></th>
																		<th align="center" class="subheading"><font color="#000000">Veh&nbsp;Make</font></th>
																		<th align="center" class="subheading"><font color="#000000">Veh&nbsp;Model</font></th>
																		<th align="center" class="subheading"><font color="#000000">Battery</font></th>
																		<th align="center" class="subheading"><font color="#000000">Model</font></th>
																		<th align="center" class="subheading"><font color="#000000"> Price</font></th>
																		<th align="center" class="subheading"><font color="#000000"> price(OBRP)</font></th>
																		<th align="center" class="subheading"><font color="#000000"> Ord&nbsp;Status</font></th>
																		<th align="center" class="subheading"><font color="#000000"> Ord&nbsp;Reasons</font></th>
																		<th align="center" class="subheading"><font color="#000000"> Agent&nbsp;Comments</font></th>
																		<th align="center" class="subheading"><font color="#000000">Update&nbsp;Status</font></th>
																		<th align="center" class="subheading"><font color="#000000"> Agent&nbsp;Name</font></th>
																		<th align="center" class="subheading"><font color="#000000"> State</font></th>
																		<th align="center" class="subheading"><font color="#000000"> City</font></th>
																		<th align="center" class="subheading"><font color="#000000"> Area</font></th>
																		<th align="center" class="subheading"><font color="#000000"> Ordered By</font></th>
																		<th align="center" class="subheading"><font color="#000000"> Ordered Date</font></th>
																		<th align="center" class="subheading"><font color="#000000"> Updated Date</font></th>
																		<th align="center" class="subheading"><font color="#000000"> Postponed Date</font></th>
																		<th align="center" class="subheading"><font color="#000000"> Installed Date</font></th>
																		<th align="center" class="subheading"><font color="#000000"> Cus&nbsp;Invoice</font></th>
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
																		String battype=(String)ht.get("bat_type");
																		String vehname=(String)ht.get("veh_name");
																		String vehmake=(String)ht.get("veh_model");
																		String battery=(String)ht.get("battery_brand");
																		String battery_capacity=(String)ht.get("battery_capacity");
																		String model=(String)ht.get("battery_model");
																		String state1=(String)ht.get("state");
																		String consumer_address=(String)ht.get("consumer_address");
																		String pincode=(String)ht.get("pincode");
																		String city1=(String)ht.get("city");
																		String area=(String)ht.get("area");
																		String order_type=(String)ht.get("order_type");
																		String price=String.valueOf(ht.get("price"));
																		String obrpprice=String.valueOf(ht.get("witholdbatprice"));
																		String quantity=String.valueOf(ht.get("quantity"));
																		String payment_mode_type=String.valueOf(ht.get("payment_mode_type"));
																		String payment_mode=String.valueOf(ht.get("payment_mode"));
																		String orderstatus=(String)ht.get("order_status");
																		String order_reasons=(String)ht.get("order_reasons");
																		String agent_name=(String)ht.get("agent_name");
																		String order_agent_comments=(String)ht.get("order_agent_comments");
																		String date=String.valueOf(ht.get("creation_date"));
																		String postponedate=String.valueOf(ht.get("postpone_date"));
																		LogLevel.DEBUG(1,new Throwable(),"postponedate12: "+postponedate);
																		LogLevel.DEBUG(1,new Throwable(),"cusname12: "+cusname);

																		String installed_date=(String)ht.get("installed_date");
																		LogLevel.DEBUG(1,new Throwable(),"installed_date12: "+installed_date);	
																		
																		String updated_date=(String)ht.get("updated_date");
																		LogLevel.DEBUG(1,new Throwable(),"updated_date12: "+updated_date);
																		
																		String pdfurl=String.valueOf(ht.get("pdfurl"));
																		
																int QTY_int = Integer.parseInt(quantity);
																int Price_Temp_price = Integer.parseInt(price);
																int Price_Temp_obrpprice = Integer.parseInt(obrpprice);
															
																String Final_Price_obrpprice = Integer.toString(Price_Temp_obrpprice*QTY_int);
																String Final_Price_price = Integer.toString(Price_Temp_price*QTY_int);
																		

																		String operator=(String)ht.get("operator");
																		LogLevel.DEBUG(1,new Throwable(),"operator: "+operator);

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
																	if(battype.equals("Bike Batteries"))
																		{
																			if(orderstatus.equals("Repeated"))
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
																	else if(orderstatus.equals("postponed") || orderstatus.equals("Postponed"))
																		{
																		%>
																	<tr bgcolor="#FF9933">
																		<%
																		}
																	
																		else
																		{
																			%>
																			<tr bgcolor="#0066FF">
																			<%
																		}
																		}
																	else
																		{
																	if(orderstatus.equals("confirmed")||  orderstatus.equals("Customer Contacted") && order_reasons.equals("Regenerated to Another Retailer")||  orderstatus.equals("Customer Contacted") && order_reasons.equals("Modify Battery Details")||  orderstatus.equals("Customer Contacted") && order_reasons.equals("Not Confirmed Order - Customer Confirmed to Place Order"))
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
																	else
																		{
																		%>
																	<tr bgcolor="#DAD0E1">
																		<%
																		}
																		}

																		LogLevel.DEBUG(5,new Throwable(),"area:"+ area );

																		if(area.equals("0") || area.equals("default") ||  area == "0" || area == "default")
																		{
																			area = "";
																		}
																		else
																		{
																			area=area;
																		}
																		if(operator.equals("no"))
																		{
																			operator = "Customer";
																		}
																		else
																		{
																			operator=operator;
																		}
																		%>
																	    <td  class="insidecontent" align="center"><%=(i+1)%> </td>
																		<td  class="insidecontent" align="left"><%=ordnum%> </td>
																		<td  class="insidecontent" align="left"><%=cusname%> </td>
																		<td  class="insidecontent" align="left"><%=cusemailid%> </td>
																		<td  class="insidecontent" align="left"><%=conmobile%> </td>
																		<td  class="insidecontent" align="left"><%=retname%> </td>
																		<td  class="insidecontent" align="left"><%=retmobile%> </td>
																		<td  class="insidecontent" align="left"><%=battype%> </td>
																		<td  class="insidecontent" align="left" 
																		
																		<%
																		if(payment_mode.equals("Online") && !order_reasons.equals("Payment In Process"))
																		{ 
																		%> 
																		bgcolor='#E91E63'
																		<%
																		}
																		%> 
																		
																		
																		
																		
																		><a data-toggle="popover" title="Payment Mode and order type" date-placement="top" data-content="<%=payment_mode%> - <%=payment_mode_type%> || Battery Order Type : <%=order_type%>" style="color: #000001;  cursor: alias;"> <%=quantity%></a> </td>
																		<td  class="insidecontent" align="left"><%=vehname%> </td>
																		<td  class="insidecontent" align="left"><%=vehmake%> </td>
																		<td  class="insidecontent" align="left"><%=battery%> </td>
																		<td  class="insidecontent" align="left"><a data-toggle="popover" title="Battery Capacity" date-placement="top" data-content="<%=battery_capacity%>" style="color: #000001;  cursor: alias;"> <%=model%></a> </td>
																		<td  class="insidecontent <% if (order_type.equals("New")){	%> battery_order_type<%}%>" align="left">Rs.<%=price%>&nbsp;X&nbsp;<%=quantity%></a><br>=&nbsp;<%=Final_Price_price%>&nbsp;</td>
																		<td  class="insidecontent <% if (order_type.equals("Replaced")){	%> battery_order_type<%}%>" align="left">Rs.<%=obrpprice%>&nbsp;X&nbsp;<%=quantity%></a><br>=&nbsp;<%=Final_Price_obrpprice%>&nbsp;</td>
																		<td  class="insidecontent" align="left"><%=orderstatus%> </td>
																		<td  class="insidecontent" align="left"><%=order_reasons%> </td>
																		<td  class="insidecontent" align="left"><%=order_agent_comments%> </td>
																		<td  class="insidecontent" align="left">
																		<select style='width:110px; height:30px' name="<%=ord_id%>" id="<%=ord_id%>" onChange="javascript:Updatevisitorstatus('<%=ord_id%>','<%=ordnum%>','<%=state1%>','<%=city1%>','<%=battype%>','<%=conmobile%>','<%=payment_mode%>','<%=order_type%>')"><option value=''>Update Status</option><option value="confirmed">Confirmed</option><option value='Customer Contacted'>Customer Contacted</option><option value='Customer Not Contacted'>Customer Not Contacted</option><option value='Postponed'>Postpone</option><option value='Repeated'>Repeated</option></select> </td>
																		<td  class="insidecontent" align="left"><%=agent_name%> </td>
																		<td  class="insidecontent" align="left"><a data-toggle="popover" title="Address" date-placement="top" data-content="<%=consumer_address%> | Area : <%=area%> | Pincode :<%=pincode%>." style="color: #000001;  cursor: alias;"> <%=state1%></a> </td>
																		<td  class="insidecontent" align="left"><%=city1%> </td>
																		<td  class="insidecontent" align="left"><%=area%> </td>
																		<td  class="insidecontent" align="left"><%=operator%> </td>
																		<td  class="insidecontent" align="left"><%=date%> </td>
																		<td  class="insidecontent" align="left"><%=updated_date%> </td>
																		<td  class="insidecontent" align="left"><%=postponedate%> </td>
																		<td  class="insidecontent" align="left"><%=installed_date%> </td>
																		
																		<% if(pdfurl.equals("") || pdfurl == "NULL")
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
																		%>
																		
																		
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


    $(function () {
        $('#batterytypefilter').multiselect({
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
</script>
		

<script>
    // initialize of popover
	$(function (){
		$('[data-toggle="popover"]').popover()
	})
</script>
</center>
</body>
</html>