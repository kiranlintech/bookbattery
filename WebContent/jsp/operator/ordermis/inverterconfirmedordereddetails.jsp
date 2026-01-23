<%--
    Document   : batteryadminmain
    Created on : Aug 30, 2013, 4:22:12 PM
    Author     : Sai Krishna Daddala.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.LogLevel"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+path+"/";
//out.println(path);
//out.println(basePath);

%>
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

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../images/favicon.png" type="image/x-icon">
<title>BookBattery.com-India's No.1 Automobile Battery Store</title>

<link rel="stylesheet" href="/bookbattery/css/bootstrap.min.css" />
 <link rel="stylesheet" href="/bookbattery/css/bootstrap-multiselect.css" />
<script src="/bookbattery/js/jquery.min.js"></script>
<script src="/bookbattery/js/bootstrap.min.js"></script>
<script src="/bookbattery/js/bootstrap-multiselect.js"></script>


<link href="../../../css/bookbattery.css" rel="stylesheet" type="text/css" />



<style type='text/css'>

.divpostponed{left:57%;top:30%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left; border:6px solid #424242; border-radius: 6px 6px 6px 6px;position:absolute;padding: 1px;display: none;z-index:0;background: white;}

.divpostponed1{left:50.5%;top:30%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left; border:6px solid #424242; border-radius: 6px 6px 6px 6px;position:absolute;padding: 1px;display: none;z-index:0;background: white;}

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
		strUrl = "../../../servlet/BatteryExcelandPdfServlet?hidWhatToDo=ExportToExcelinverterorder";
		window.open(strUrl,"ExcelOpen","height=450,width=530,toobar=0,location=0,directories=0,status=yes,menubar=0,scrollbars=yes,resizable=1"); 
	}
	function onClickExportToPdf()
	{
		strUrl = "../../../servlet/BatteryExcelandPdfServlet?hidWhatToDo=ExportToPDFinverterorder";
		window.open(strUrl,"ExcelOpen","height=450,width=530,toobar=0,location=0,directories=0,status=yes,menubar=0,scrollbars=yes,resizable=1"); 
	}

	/** Function to change Orders status **/
function Updatevisitorstatus(orderid,ordernumber,state,city,area,payment_mode)
{
	
	var orderedstatus = document.getElementById(orderid).value;
	

	if(orderedstatus=="Customer Contacted")
	{
		CustomerContacted(orderid,ordernumber,orderedstatus,state,city,payment_mode)
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
		Ordertoretailer(orderid,ordernumber,orderedstatus,state,city,area,payment_mode)
	}
	
	else if(orderedstatus=="")
	{
	}
	else
	{
		var xmlhttp= "";
		var resp= "";
		var url ="../../../servlet/MISOperatorInverterDetails?hidWhatToDo=updateorderstatus&orderid="+orderid+"&ordernumber="+ordernumber+"&orderstatus="+orderedstatus;
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
	errMsg ="<table  width='340' cellspacing='10' cellpadding='0' bgcolor='#FFFFFF' border='0'><tr><td><table width='340' bgcolor='#FFFFFF' valign='top' border='0'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobdiv();\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr><td width='80%' colspan='3' align='center'><font size='2' color='#FF8C00'>Please Select Postpone Date&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></td><td width='20%'></td></tr><tr><td height='10' width='80%'></td><td width='20%'></td></tr><tr><td width='80%' align='center'><input type='ext' name='postponedate' class='insidecontent' readonly  onChange='CheckDate(this)' onKeyDown='FormatDate(this,  window.event.keyCode,'down')' onKeyUp='FormatDate(this, window.event.keyCode,'up')' value='' size='10' maxlength='10'  style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' /></td><td width='20%' align='center'><img src='../../../images/calender.jpg' valign='bottom' align='middle' style='cursor:hand;margin-right:10px;' onclick=\"javascript:displayDatePicker('postponedate', this);\" height='25' ></td><td></td></tr><tr><td height='5' width='80%'></td><td height='5' width='20%'></td></tr><tr><td width='63%' class='insidecontent' align='center' style='width:155px;  bgcolor:#FFF;'><div class='styled-select' align='center'><select name='orderreason'  id='orderreason' class='insidecontent' STYLE='width: 200px;' align='center' onChange='javascript:setagentcomments()'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Reason--&gt;</option><option value='High Priority' >High Priority</option><option value='Customer is not picking the Call' >Customer is not picking the Call</option><option value='Customer Number Busy'>Customer Number is Busy</option><option value='Customer Number is Not Reachable' >Customer Number is Not Reachable</option><option value='Customer Number Switched Off' >Customer Number is Switched Off</option><option value='Customer is Out of Station' >Customer is Out of Station</option><option value='Customer is Not Responding to our Calls' >Customer is Not Responding to our Calls</option><option value='Customer need Installation Today' >Customer need Installation Tomorrow</option><option value='Need to check with franchisee whether the battery is currently available' >Franchisee is not having the Stock</option><option value='Customer Car Old battery is Working Fine need installation today' >Customer Car Old battery is Working Fine Presently</option><option value='Car Old Battery is working Fine He need installation Today' >Car Old Battery is working Fine, He donot need installation Now</option><option value='Yesterdays After Business Hour Order' >As it is not business hours, we will install the battery tomorrow</option><option value='Pitstop is So Far Customer will collect the battery whenever he is free' >Pitstop is So Far, Customer will collect the battery whenever he is free</option><option value='Customer need installation today' >Customer is busy today, he need installation later</option><option value='Customer need latest Manufacturing Battery' >Customer need latest Manufacturing Battery</option><option value='Order Status is not yet confirmed from Franchisee or Customer' >Order Status is not yet confirmed from Franchisee or Customer</option><option value='Customer will collect the battery today' >Customer will collect the battery tomorrow</option><option value='Customer will collect the battery today' >Customer Bike Old battery is Working Fine Presently</option><option value='Pitstop was on Leave Yesterday Need to process the order today' >Franchisee is on Leave Today</option></select></div></td></tr><tr><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;Message</td></tr><tr height='10'><td width='100%' align='center'><textarea name='cusmessage' id='cusmessage' rows='6' cols='30'></textarea></td></tr><tr>&nbsp;&nbsp;&nbsp;<td  width='80%' align='center'><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:updatecustcontactedstatus('"+orderid+"','"+ordernumber+"','"+orderstatus+"');\"></td><td width='20%'></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr height='16'><td width='80%' align='center' class='subheading' id='displayrefinederrormsg1'></td><td width='20%'></td></tr><tr><td height='10'></td></tr></table></td></tr></table>";
	document.getElementById("divpostponed").innerHTML=""; 
	document.getElementById("divpostponed").style.display='block';
	document.getElementById("divpostponed").innerHTML=errMsg
}



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

	$("#invertercap").html("<option value='0' selected=''>&nbsp;Select&nbsp;Capacity</option>");
	$("#invertermodel").html("<option value='0' selected=''>&nbsp;Select&nbsp;Inverter&nbsp;Models</option>");
	$("#inverterprice").val(" ");
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
	
	$("#invertercap").html("<option value='0' selected=''>&nbsp;Select&nbsp;Capacity</option>");
	$("#invertermodel").html("<option value='0' selected=''>&nbsp;Select&nbsp;Inverter&nbsp;Models</option>");
	$("#inverterprice").val(" ");
}

/** Function to show options to select if CustomerContacted **/



function CustomerContacted(orderid,ordernumber,orderstatus,state,city,payment_mode)
{
	var PaymentModeClass="";
	if(payment_mode=="Online")
	{
		PaymentModeClass="hide";
	}else{
		// DO Noting
	}
	
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
	 
	$.ajax
	({
		type: "GET",
		url: "/bookbattery/servlet/InvertersDetails?hidWhatToDo=getinverterbrand",
		data: {keyword:"Common"},
		success: function(data)
		{	
			$("#inverterbrand").html(data)
		}
	});

	errMsg ="<table  width='340' cellspacing='10' cellpadding='0' bgcolor='#FFFFFF' border='0'><tr><td><table width='340' bgcolor='#FFFFFF' valign='top' border='0'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobdiv();\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr><td height='5' width='80%'></td><td height='5' width='20%'></td></tr><tr><td width='63%' class='insidecontent' align='center' style='width:155px;  bgcolor:#FFF;'><div class='styled-select' align='center'><select name='orderreason'  id='orderreason' class='insidecontent' STYLE='width: 200px;' align='center' onChange=\"javascript:setagentcomments('"+payment_mode+"','"+state+"','"+city+"')\"><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Reason--&gt;</option><option value='installed' >Installed</option><option value='In Process' >In Process</option><option value='Not Installed' >Not Installed</option><option value='cancelled-franchisee-offbushrs' >Not Installed - Cancelled-Franchisee-OffBusHrs</option><option value='cancelled-franchisee-denied' >Not Installed - Cancelled-Franchisee-Denied</option><option value='cancelled-franchisee-notresponded' >Not Installed - Cancelled-Franchisee-NotResponded</option><option value='cancelled-franchisee-modeloutofstock' >Not Installed - Cancelled-Franchisee-ModelOutofStock</option><option value='cancelled-customer' >Not Installed - Cancelled-Customer</option><option value='cancelled-regenerated' >Not Installed - Cancelled-Regenerated</option><option value='Not Installed-Customer-Denied-After going to his place'>Not Installed-Customer-Denied-After going to his place</option><option value='Not Installed-Cancelled-Order Forwarded to BookBattery'>Not Installed-Cancelled-Order Forwarded to BookBattery</option><option value='Regenerated to Another Retailer'>Regenerated to Another Retailer</option><option class='"+PaymentModeClass+"' value='Modify Battery Details'>Modify Battery Details</option><option class='"+PaymentModeClass+"' value='Not Confirmed Order - Customer Confirmed to Place Order'>Not Confirmed Order - Customer Confirmed to Place Order</option> <option value='Update'>Update</option> </select></div></td></tr><tr id='rettoorder1' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Select&nbsp;Retailer</td></tr><tr height='10' id='rettoorder' style='display:none;'><td width='100%' align='center'><select name='retailertoorder'  id='retailertoorder' class='insidecontent' STYLE='width: 200px;' align='center' ><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Retailer--&gt;</option></select></td></tr><tr height='10' id='modifybatdetls13' readonly><td width='100%' align='center'><input type='text' value='"+state+"' name='constate' id='constate' style='display:none';></td></tr><tr height='10' id='modifybatdetls14' readonly><td width='100%' align='center'><input type='text' value='"+city+"' name='concity' id='concity' style='display:none';></td></tr><tr id='confirmord91' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Select&nbsp;State</td></tr><tr height='10' id='confirmord101' style='display:none;'><td width='100%' align='center'><select name='cusstate' value='"+state+"'  id='cusstate' onchange =\"javascript:getcities();\" class='insidecontent' STYLE='width: 200px;' align='center'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select State--&gt;</option></select></td></tr><tr id='confirmord92' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Select&nbsp;City</td></tr><tr height='10' id='confirmord102' style='display:none;'><td width='100%' align='center'><select name='cuscity' value='"+city+"'  id='cuscity' onchange =\"javascript:getareas();\" class='insidecontent' STYLE='width: 200px;' align='center'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select City--&gt;</option></select></td></tr><tr id='modifybatdetls4' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Select&nbsp;Brand</td></tr><tr height='10' id='modifybatdetls5' style='display:none;'><td width='100%' align='center'><select name='inverterbrand'  id='inverterbrand' class='insidecontent' STYLE='width: 200px;' align='center' onchange='javascript:getinvertercapacities();'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Brand--&gt;</option></select></td></tr><tr id='modifybatdetls6' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Select&nbsp;Capacity</td></tr><tr height='10' id='modifybatdetls7' style='display:none;'><td width='100%' align='center'><select name='invertercap'  id='invertercap' class='insidecontent' STYLE='width: 200px;' align='center' onchange='javascript:getinvertermodels();'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Capacity--&gt;</option></select></td></tr><tr id='modifybatdetls10' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Select&nbsp;Model</td></tr><tr height='10' id='modifybatdetls11' style='display:none;'><td width='100%' align='center'><select name='invertermodel'  id='invertermodel' class='insidecontent' STYLE='width: 200px;' align='center' onchange='javascript:getinverterpricedetls();'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Model--&gt;</option></select></td></tr><tr id='modifybatdetls8' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Price</td></tr><tr height='10' id='modifybatdetls9' style='display:none;'><td width='100%' align='center'><input type='text' value='' name='inverterprice' id='inverterprice'></td></tr>          <tr  class='Quantity_tr' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>&nbsp;Enter&nbsp;Quantity</td> </tr> <tr  class='Quantity_tr' style='display:none;'> <td width='100%' align='center'>&nbsp;<input class='insidecontent' type='text' autocomplete='off' name='Quantity' id='Quantity' value='1' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='50'></td></tr>                <tr id='confirmord1' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Customer Name</td></tr><tr height='10' id='confirmord2' style='display:none;'><td width='100%' align='center'><input type='text'  name='cusname' id='cusname'></td></tr><tr id='confirmord3' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Customer Email ID</td></tr><tr height='10' id='confirmord4' style='display:none;'><td width='100%' align='center'><input type='text' name='cusemailid' id='cusemailid'></td></tr><tr id='confirmord5' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Customer Address1</td></tr><tr height='10' id='confirmord6' style='display:none;'><td width='100%' align='center'><input type='text' name='cusaddr1' id='cusaddr1'></td></tr><tr id='confirmord7' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Customer Address2</td></tr><tr height='10' id='confirmord8' style='display:none;'><td width='100%' align='center'><input type='text'  name='cusaddr2' id='cusaddr2'></td></tr><tr id='confirmord9' style='display:none;'><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Select&nbsp;Area</td></tr><tr height='10' id='confirmord10' style='display:none;'><td width='100%' align='center'><select name='cusarea'  id='cusarea' class='insidecontent' STYLE='width: 200px;' align='center'><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Area--&gt;</option></select></td></tr> <tr id='confirmord11' style='display:none;' ><td width='63%' class='insidecontent' align='center' style='width:155px;  bgcolor:#FFF;'><div class='styled-select' align='center'><select onChange='javascript:setagentcomments_payment();' name='payment_mode'  id='payment_mode' class='insidecontent' STYLE='width: 200px;' align='center' ><option value='0' >&lt;-- Select Payment Method--&gt;</option> <option value='Cash' >Cash</option> <option value='Credit Card' >Credit Card</option> <option value='Debit Card' >Debit Card</option> <option value='UPI' >UPI</option>  <option value='Cheque' >Cheque</option> <option value='Online Payment After Fitment'>Online Payment After Fitment</option></select></div></td></tr><tr id='ratingid' style='display:none;' ><td width='63%' class='insidecontent' align='center' style='width:155px;  bgcolor:#FFF;'><div class='styled-select' align='center'><select name='rating'  id='rating' class='insidecontent' STYLE='width: 200px;' align='center' ><option value='0' >&lt;-- Select Satisfaction Type--&gt;</option> <option value='Satisfied' >Satisfied</option> <option value='Not Satisfied' >Not Satisfied</option></select><select name='payment_collected'  id='payment_collected' class='insidecontent' STYLE='width: 200px;' align='center' ><option value='0' >&lt;-- Select Payment Collected--&gt;</option> <option value='Dealer' >Collected by Dealer</option> <option value='Asistmi' >Collected by Asitmi</option></select></div></td></tr><tr><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;Message</td></tr><tr height='10'><td width='100%' align='center'><textarea name='cusmessage' id='cusmessage' rows='6' cols='30'></textarea></td></tr><tr>&nbsp;&nbsp;&nbsp;<td  width='80%' align='center'><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:updatecustcontactedstatus('"+orderid+"','"+ordernumber+"','"+orderstatus+"');\"></td><td width='20%'></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr height='16'><td width='80%' align='center' class='subheading' id='displayrefinederrormsg1'></td><td width='20%'></td></tr><tr><td height='10'></td></tr></table></td></tr></table>";
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

function Ordertoretailer(orderid,ordernumber,orderstatus,state,city,area,payment_mode)
{

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
				$("#retailer").html(data)
			}
		});
		document.misbatstatus.cusarea.value=area;
}



/** Function to change Orders status and reason **/
function updatecustcontactedstatus(orderid,ordernumber,orderstatus)
{
	
		var retailertoorder = "";
		var inverterbrand = "";
		var invertercap = "";
		var inverterprice = "";
		var invertermodel="";
		var Quantity = "";
		var SMSURL = "";
		var SMSURL1 = "";
		var shorturl1 = "";
		var shorturl = "";
		var rating = "";
        var paymentcollected = "";
		var baseurl1 = "";
		var baseurl = "";
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

		if(orderstatus=="postponed" || orderstatus=="Postponed")
		{
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
			cusarea = document.getElementById("cusarea").value;
			
			//alert(retailertoorder);

			if(retailertoorder == "" || retailertoorder == "0" || retailertoorder == "default" || retailertoorder == "defaultss")
			{
				errMsg ="<font color='#9B5BDD'>Please Select Retailer</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misbatstatus.retailer.focus();
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
			//baseurl="http://192.168.1.95/bookbattery/";
			//baseurl="http://192.168.1.95/bookbattery/";
			baseurl="http://192.168.1.95/bookbattery/";
			
			//alert(baseurl);
			//alert("orderid:"+orderid);
			//var SMSURL="http://www.BookBattery.com/bookbattery/jsp/rating.jsp?phone=9108501361&email=rathaiahtulabandhula@gmail.com&ordernumber=ORDB4813098B";
			 SMSURL=baseurl+"jsp/rating.jsp?ordernumber="+ordernumber;
			 SMSURL1=baseurl1+"jsp/rating.jsp?ordernumber="+ordernumber;
			//alert(SMSURL);

			var ordernumbersplit=ordernumber.split("ORDI");
			
			SMSURL=baseurl+"jsp/customerreferral.jsp?refcode=REF"+ordernumbersplit[1];
			
			//alert(SMSURL);

			shorturl = document.getElementById("result").value; 
			shorturl1 = document.getElementById("result1").value; 
			
			//alert("shorturl"+shorturl);
			
			if(shorturl =="" || shorturl == undefined|| shorturl == "undefined")
			{
				//makeRequest(SMSURL,orderid,ordernumber,orderstatus);
				//return;
			}
			else
			{
			
			}
			
			if(shorturl1 =="" || shorturl1 == undefined|| shorturl1 == "undefined" || (shorturl1==shorturl))
			{
				//alert(22);
				//makeRequest1(SMSURL1,orderid,ordernumber,orderstatus);
				//return;
			}
			else
			{
			
			}

			//alert(23);
			
			payment_mode = document.misbatstatus.payment_mode.value;
			rating = document.misbatstatus.rating.value;
			paymentcollected = document.misbatstatus.payment_collected.value;
			if(payment_mode == "" || payment_mode == "0" || payment_mode == "default" || payment_mode == "defaultss")
			{
				errMsg ="<font color='#9B5BDD'>Please Select Payment Mode</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misbatstatus.payment_mode.focus();
				return ;
			}
			if(rating == "" || rating == "0" || rating == "default" || rating == "defaultss")
			{
				errMsg ="<font color='#9B5BDD'>Please Select Satisfaction Type</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misbatstatus.rating.focus();
				return ;
			}
            if(paymentcollected == "" || paymentcollected == "0" || paymentcollected == "default" || paymentcollected == "defaultss")
			{
				errMsg ="<font color='#9B5BDD'>Please Select Payment Collected</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misbatstatus.payment_collected.focus();
				return ;
			}
		}
		else if(orderstatus=="Customer Contacted" && orderreason=="Modify Battery Details")
		{
			inverterbrand = document.getElementById("inverterbrand").value; 
			invertercap = document.getElementById("invertercap").value; 
			inverterprice = document.getElementById("inverterprice").value;
			invertermodel = document.getElementById("invertermodel").value;
			Quantity = document.getElementById("Quantity").value;

			if(inverterbrand == "" || inverterbrand == "0" || inverterbrand == "default" || inverterbrand == "defaultss")
			{
				errMsg ="<font color='#9B5BDD'>Please Select Inverter Brand</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misbatstatus.inverterbrand.focus();
				return ;
			}

			if(invertercap == "" || invertercap == "0" || invertercap == "default" || invertercap == "defaultss")
			{
				errMsg ="<font color='#9B5BDD'>Please Select Inverter Capacity</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misbatstatus.invertercap.focus();
				return ;
			}

			if(invertermodel == "" || invertermodel == "0" || invertermodel == "default" || invertermodel == "defaultss")
			{
				errMsg ="<font color='#9B5BDD'>Please Select Inverter Model</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misbatstatus.invertermodel.focus();
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
			inverterbrand = document.getElementById("inverterbrand").value; 
			invertercap = document.getElementById("invertercap").value; 
			inverterprice = document.getElementById("inverterprice").value;
			invertermodel = document.getElementById("invertermodel").value;
			cusname = document.getElementById("cusname").value; 
			cusemailid = document.getElementById("cusemailid").value; 
			cusaddr1 = document.getElementById("cusaddr1").value; 
			cusaddr2 = document.getElementById("cusaddr2").value;
			cusstate = document.getElementById("cusstate").value; 
			cuscity = document.getElementById("cuscity").value; 
			cusarea = document.getElementById("cusarea").value;
			Quantity = document.getElementById("Quantity").value;

			if(inverterbrand == "" || inverterbrand == "0" || inverterbrand == "default" || inverterbrand == "defaultss")
			{
				errMsg ="<font color='#9B5BDD'>Please Select Inverter Brand</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misbatstatus.inverterbrand.focus();
				return ;
			}

			if(invertercap == "" || invertercap == "0" || invertercap == "default" || invertercap == "defaultss")
			{
				errMsg ="<font color='#9B5BDD'>Please Select Inverter Capacity</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misbatstatus.invertercap.focus();
				return ;
			}

			if(invertermodel == "" || invertermodel == "0" || invertermodel == "default" || invertermodel == "defaultss")
			{
				errMsg ="<font color='#9B5BDD'>Please Select Inverter Model</font>";
				document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
				document.misbatstatus.invertermodel.focus();
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
		
		//alert(SMSURL);
		//alert(SMSURL1);
		//alert(shorturl);
		//alert(shorturl1);
		
		$('#divpostponed').hide();
		var xmlhttp= "";
		var resp= "";
		var url ="../../../servlet/MISOperatorInverterDetails?hidWhatToDo=updateorderstatus&orderid="+orderid+"&ordernumber="+ordernumber+"&orderstatus="+orderstatus+"&orderreason="+orderreason+"&agentcomments="+cusmessage+"&postponedate="+postponedate+"&retailertoorder="+retailertoorder+"&inverterbrand="+inverterbrand+"&invertercap="+invertercap+"&inverterprice="+inverterprice+"&invertermodel="+invertermodel+"&cusname="+cusname+"&cusemailid="+cusemailid+"&cusaddr1="+cusaddr1+"&cusaddr2="+cusaddr2+"&cusstate="+cusstate+"&cuscity="+cuscity+"&cusarea="+cusarea+"&rating="+rating+"&payment_mode="+payment_mode+"&SMSURL="+SMSURL+"&SMSURL1="+SMSURL1+"&shorturl="+shorturl+"&shorturl1="+shorturl1+"&Quantity="+Quantity+"&paymentcollected="+paymentcollected;


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
	document.misbatstatus.action="../../../servlet/MISOperatorInverterDetails?hidWhatToDo=getconfirmedordersmis&city="+city+"&keyword="+keyword+"&dates="+strDateOpt+"&txtFromDate="+strFromDate+"&txtToDate="+strToDate;
	document.misbatstatus.submit();
	
}

function setagentcomments(PaymentModem,state,city)
{
	var orderreason = document.misbatstatus.orderreason.value;

	//alert(22);
	
	//alert(orderreason);
	
	if(orderreason =="Regenerated to Another Retailer")
	{
		$('#rettoorder').show();
		$('#rettoorder1').show();
		$('#confirmord11').hide();
		$('#ratingid').hide();
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
		$('#confirmord91').hide();
		$('#confirmord101').hide();
		$('#confirmord92').hide();
		$('#confirmord102').hide();
		$('#modifybatdetls11').show();
		$('#modifybatdetls12').show();
		$('#modifybatdetls13').show();
		$('#modifybatdetls14').show();
		$('#confirmord11').hide();
		$('#ratingid').hide();
		$('.Quantity_tr').show();
		//$('#rettoorder1').show();

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
		$('.Quantity_tr').show();
	}
	else if(orderreason =="installed")
	{
		
		//alert("insdie");
		
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
		$('#confirmord11').show();
		$('#ratingid').show();
		$('#order_type_div').show();
		
		
		$('.Quantity_tr').hide();
		
		if(PaymentMode =="Online")
		{
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
	  
		} else if(PaymentMode =="Cash")
		{
			//alert("cash");
			$('#order_type').after("<p style='color: red; font-size: 12px; font-weight: 400;'>Customer has Opted for:"+PaymentMode+"</p>");
			$('#payment_mode').val(PaymentMode);
			$("#payment_mode").empty();
			$('#payment_mode').append("<option value='0' >&lt;-- Select Payment Method--&gt;</option>");
			$('#payment_mode').append("<option value='Cash'>Cash</option>");
			$('#payment_mode').append("<option value='Cheque'>Cheque</option>");
			$('#payment_mode').append("<option value='Credit Card'>Credit Card</option>");
			$('#payment_mode').append("<option value='Debit Card'>Debit Card</option>");
			$('#payment_mode').append("<option value='UPI'>UPI</option>");
			$('#payment_mode').append("<option value='Online Payment After Fitment'>Online Payment After Fitment</option>");
	  
		}
		

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
	var strUserid = document.misbatstatus.strUserid.value;
	//alert(strUserid);
	var operatorfilter = "";
	var fromdatefilter = "";
	var fromtimefilter = "";
	var todatefilter = "";
	var totimefilter = "";
		
	
	if(strUserid=="operator")
	{
		operatorfilter = document.misbatstatus.operatorfilter.value;
	}
	else
	{
	}
	fromdatefilter = $("#fromdatefilter").val();
	fromtimefilter = $("#fromtimefilter").val();
	todatefilter = $("#todatefilter").val();
	totimefilter = $("#totimefilter").val();
	var orders_of = $("#orders_of").val();
	
	
	if(fromdatefilter!="" && todatefilter!="")
	{
		strDateOpts="selected";
	}
	else
	{
	}

	var TestOrders_radio = $("input[name='TestOrders_radio']:checked").val()
	
	document.misbatstatus.method="POST";
	document.misbatstatus.action="../../../servlet/MISOperatorInverterDetails?hidWhatToDo=getconfirmedordersmis&city="+city+"&keyword="+keyword+"&dates="+strDateOpts+"&txtFromDate="+strFromDates+"&txtToDate="+strToDates+"&statusfilter="+statusfilter+"&substatusfilter="+substatusfilter+"&retselectname="+retselectname+"&operatorfilter="+operatorfilter+"&fromdatefilter="+fromdatefilter+"&fromtimefilter="+fromtimefilter+"&todatefilter="+todatefilter+"&totimefilter="+totimefilter+"&test_orders="+TestOrders_radio+"&orders_of="+orders_of;
	document.misbatstatus.submit();
	
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
	}
	else
	{
	}

	if(statusfilter=="" || statusfilter=="Select Status")
	{
		errMsg ="<option value='0'>&lt;-- Select Sub Status--&gt;</option>";
	}
	else if(statusfilter=="Customer Contacted")
	{
		errMsg =errmsg1+"<option value='installed' >Installed</option><option value='In Process' >In Process</option><option value='Not Installed' >Not Installed</option><option value='cancelled-franchisee-offbushrs' >Not Installed - Cancelled-Franchisee-OffBusHrs</option><option value='cancelled-franchisee-denied' >Not Installed - Cancelled-Franchisee-Denied</option><option value='cancelled-franchisee-notresponded' >Not Installed - Cancelled-Franchisee-NotResponded</option><option value='cancelled-franchisee-modeloutofstock' >Not Installed - Cancelled-Franchisee-ModelOutofStock</option><option value='cancelled-customer' >Not Installed - Cancelled-Customer</option><option value='cancelled-regenerated' >Not Installed - Cancelled-Regenerated</option><option value='Not Installed-Customer-Denied-After going to his place'>Not Installed-Customer-Denied-After going to his place</option><option value='Forwarded from BookBattery'>Forwarded from BookBattery</option><option value='Regenerated to Another Retailer'>Regenerated to Another Retailer</option><option value='Modify Battery Details'>Modify Battery Details</option><option value='Not Confirmed Order - Customer Confirmed to Place Order'>Not Confirmed Order - Customer Confirmed to Place Order</option>";
	}
	else if(statusfilter=="Customer Not Contacted")
	{
		errMsg =errmsg1+"<option value='Phone Busy' >Phone Busy</option><option value='Phone Not Reachable'>Phone Not Reachable</option><option value='Phone Switched Off'>Phone Switched Off</option>";
	}
	else if(statusfilter=="Postponed" || statusfilter=="postponed")
	{
		errMsg =errmsg1+"<option value='High Priority'>High Priority</option><option value='Customer is not picking the Call' >Customer is not picking the Call</option><option value='Customer Number Busy'>Customer Number is Busy</option><option value='Customer Number is Not Reachable' >Customer Number is Not Reachable</option><option value='Customer Number Switched Off' >Customer Number is Switched Off</option><option value='Customer is Out of Station' >Customer is Out of Station</option><option value='Customer is Not Responding to our Calls' >Customer is Not Responding to our Calls</option><option value='Customer need Installation Today' >Customer need Installation Tomorrow</option><option value='Need to check with franchisee whether the battery is currently available' >Franchisee is not having the Stock</option><option value='Customer Car Old battery is Working Fine need installation today' >Customer Car Old battery is Working Fine Presently</option><option value='Car Old Battery is working Fine He need installation Today' >Car Old Battery is working Fine, He donot need installation Now</option><option value='Yesterdays After Business Hour Order' >As it is not business hours, we will install the battery tomorrow</option><option value='Pitstop is So Far Customer will collect the battery whenever he is free' >Pitstop is So Far, Customer will collect the battery whenever he is free</option><option value='Customer need installation today' >Customer is busy today, he need installation later</option><option value='Customer need latest Manufacturing Battery' >Customer need latest Manufacturing Battery</option><option value='Order Status is not yet confirmed from Franchisee or Customer' >Order Status is not yet confirmed from Franchisee or Customer</option><option value='Customer will collect the battery today' >Customer will collect the battery tomorrow</option><option value='Customer will collect the battery today' >Customer Bike Old battery is Working Fine Presently</option><option value='Pitstop was on Leave Yesterday Need to process the order today' >Franchisee is on Leave Today</option>";
	}
	if(statusfilter=="Repeated")
	{
		errMsg =errmsg1+"<option value='Customer Has Placed Order For Twice' >Customer Has Placed Order For Twice</option>";
	}

	$('.multiselect').multiselect('destroy');
	document.getElementById("substatusfilter").innerHTML=""; 
	document.getElementById("substatusfilter").innerHTML=errMsg 
	
	$('.multiselect').multiselect({
		includeSelectAllOption: true
	});
	
	if(temp_break=="0")
	{
		var data="<%=substatusfilter1%>";
		var dataarray=data.split(",");
		$("#substatusfilter").val(dataarray);
		$("#substatusfilter").multiselect("refresh");
		temp_break="1";
	}
}
function getinvertercapacities()
	{
		
				 
				var inverterbrand = document.getElementById("inverterbrand").value; 
				$.ajax
				({
					type: "GET",
					url: "/bookbattery/servlet/InvertersDetails?hidWhatToDo=getinvertercapacity",
					data: {inverterbrand: inverterbrand},
					success: function(data)
					{	
						$("#invertercap").html(data)
					}
				});
		
	}

	function getinvertermodels()
	{
		
				 
				var inverterbrand = document.getElementById("inverterbrand").value; 
				var invertercap = document.getElementById("invertercap").value; 
				$.ajax
				({
					type: "GET",
					url: "/bookbattery/servlet/InvertersDetails?hidWhatToDo=getinvertermodels",
					data: {inverterbrand: inverterbrand,invertercap: invertercap},
					success: function(data)
					{	
						$("#invertermodel").html(data)
					}
				});
		
	}

	function getinverterpricedetls()
	{
		var constate = document.getElementById("cusstate").value; 
		var concity = document.getElementById("cuscity").value; 
		
		if(concity=="0" || concity==0 || constate=="0" || constate==0)
		{
			//alert("inside if");
			constate = document.getElementById("constate").value; 
			concity = document.getElementById("concity").value; 
		}
		else
		{
			//alert("inside else");
			constate = document.getElementById("cusstate").value; 
			concity = document.getElementById("cuscity").value; 
		}
		
		var inverterbrand = document.getElementById("inverterbrand").value; 
		var invertercap = document.getElementById("invertercap").value;
		var invertermodel = document.getElementById("invertermodel").value; 
				
				$.ajax
				({
					type: "GET",
					url: "/bookbattery/servlet/MISOperatorInverterDetails?hidWhatToDo=getinverterpricedetls",
					data: {constate: constate,concity: concity,inverterbrand: inverterbrand,invertercap: invertercap,invertermodel: invertermodel},
					success: function(data)
					{	
						//alert(data);
						//$("#batmodel").html(data)
						/*var results=data.split(",");
								var withoutoldbatprice=results[0];
								var witholdbatprice=results[1];*/

								document.getElementById("inverterprice").value=data;
								//document.getElementById("witholdbatprice").value=witholdbatprice;

					}
				});
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
<div id="result" style="display:none"></div>
<div id="result1" style="display:none"></div>
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
			
			<td width="75%" align="left" valign="top">
				<table width="100%" border="1" cellspacing="0" cellpadding="0">
				<tr>
					<td align="left" valign="top">
					<!-- your page content starts here  -->
					<table width="100%" cellspacing="1" align="center" bgcolor="#FFFFFF" cellpadding="0" border="0">
					<table width="450" border="0" align="center" cellpadding="5" cellspacing="0" bgcolor="#FFFFFF">
					
					<tr><td align="left" class="subheading"><a href="../../../jsp/operator/ordermis/invertermisselconfirm.jsp?keyword=<%=keyword%>&dates=<%=strDateOpt%>&txtFromDate=<%=strFromDate%>&txtToDate=<%=strToDate%>" class="onclick1">Back</a>
					
					
					&nbsp;&nbsp;&nbsp;Orders Status : <select style='width:215px;background-color: #ffffff;color: #000;padding: .5em;padding-right: 2.5em;margin: 0;border-radius: 3px;text-indent: 0.01px;' name="statusfilter" id="statusfilter" onChange="javascript:getsubstatusoptions()"><option value='<%=statusfilter1%>'><%=statusfilter1%></option><option value=''>All</option><option value="confirmed">Confirmed</option><option value="Not Confirmed">Not Confirmed</option><option value='Customer Contacted'>Customer Contacted</option><option value='Customer Not Contacted'>Customer Not Contacted</option><option value='Postponed'>Postponed</option><option value='Repeated'>Repeated</option><option value='SortByCreationDate'>SortByCreationDate</option></select>
					
					&nbsp;&nbsp;&nbsp;&nbsp;Orders Sub Status : <select style='width:215px;background-color: #ffffff;color: #000;padding: .5em;padding-right: 2.5em;margin: 0;border-radius: 3px;text-indent: 0.01px;'   class="multiselect"  name="substatusfilter" id="substatusfilter"  multiple="multiple"></select>
					
					&nbsp;&nbsp;&nbsp;&nbsp; Franchisee : <select style='width:215px;background-color: #ffffff;color: #000;padding: .5em;padding-right: 2.5em;margin: 0;border-radius: 3px;text-indent: 0.01px;' name="retnameselect" id="retnameselect"  >
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
					
					
					
					
					</td></tr>
					
					<tr>
						<td>
							<table width="450" align="center" cellspacing="1" cellpadding="0">
							<tr><td height="20"></td></tr>
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
												<td bgcolor="#0081C7" align="center" class="subheading"><font color="#FFFFFF"><%=keyword%>&nbsp;Inverter&nbsp;Orders&nbsp;Details&nbsp;Summary</font></td>
											</tr>
											<tr>
												<td bgcolor="#FFFFFF" align="center" colspan="2">
													<table width="100%" align="center" cellspacing="0" class="tableizer-table" bgcolor="#FFFFFF" cellpadding="5">
													<tr>
														<td  align="center" > 
															<table width="100%" cellspacing="0"  cellpadding="2" class="tableizer-table"   border="1">
															<tr>
																<td bgcolor="#DAD0E1" align="left" class="insidecontent" >&nbsp;Location</td><td align="left" class="insidecontent" >:&nbsp;<%=htOptions.get("city")%></td>
																
																<td bgcolor="#DAD0E1" align="left" class="insidecontent" >&nbsp;From Date</td><td align="left" class="insidecontent" >:&nbsp;<%=htOptions.get("txtFromDate")%></td>
																<td bgcolor="#DAD0E1" align="left" class="insidecontent" >&nbsp;To Date</td><td align="left" class="insidecontent" >:&nbsp;<%=htOptions.get("txtToDate")%></td>
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
															<div style="width:1320px;height:400px;overflow:scroll; overflow-X:auto;  -webkit-overflow-scrolling: touch;" id='div1'>
																<table width="100%" cellspacing="1"  cellpadding="2" bgcolor="#dddddd">
																	<tr  bgcolor="#cccccc">
																		<td align="center" class="subheading"><font color="#000000">Sl&nbsp;No</font></td>
																		<td align="center" class="subheading"><font color="#000000">ORD&nbsp;No</font></td>
																		<td align="center" class="subheading"><font color="#000000">Cus&nbsp;Name/Emailid/Mobile</font></td>
																		<td align="center" class="subheading"><font color="#000000">Ret&nbsp;Name/Mobile</font></td>
																		<td align="center" class="subheading"><font color="#000000">Inv&nbsp;Brand</font></td>
																		<td align="center" class="subheading"><font color="#000000">Inv&nbsp;Capacity</font></td>
																		<td align="center" class="subheading"><font color="#000000">Qty</font></td>
																		<td align="center" class="subheading"><font color="#000000">Inv&nbsp;Model</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Price</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Ord&nbsp;Status</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Ord&nbsp;Reasons</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Confirm&nbsp;By</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Agent&nbsp;Comments</font></td>
																		<td align="center" class="subheading"><font color="#000000">Update&nbsp;Status</font></td>
																		<td align="center" class="subheading"><font color="#000000"> State/ City</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Area</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Pincode</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Ordered By</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Ordered Date</font></td>
																		<td align="center" class="subheading"><font color="#000000"> First Contacted Date</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Updated Date</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Postponed Date</font></td>
																		<td align="center" class="subheading"><font color="#000000"> Installed Date</font></td>
																		
																																		
																	</tr>
																	<%
																	for(int i=0;i<alist.size();i++)
																	{
																		Hashtable ht=(Hashtable)alist.get(i);
																		String ord_id=String.valueOf(ht.get("order_id"));
																		String ordnum=String.valueOf(ht.get("order_number"));
																		String cusname=(String)ht.get("consumer_name");
																		String cusemailid=(String)ht.get("consumer_emailid");
																		String conmobile=String.valueOf(ht.get("consumer_mobnumber"));
																		String retname=(String)ht.get("retailer_name");
																		String service_engineer_name=String.valueOf(ht.get("service_engineer_name"));
																		LogLevel.DEBUG(5,new Throwable(),"service_engineer_name: "+service_engineer_name);
																		String service_engineer_mobile=(String)ht.get("service_engineer_mobile");
																		LogLevel.DEBUG(5,new Throwable(),"service_engineer_mobile: "+service_engineer_mobile);
																		String retmobile=String.valueOf(ht.get("retailer_mobile_number"));
																		String retemail=String.valueOf(ht.get("retailer_emailid"));
																		String invbrand=(String)ht.get("inverter_brand");
																		String invcap=(String)ht.get("inverter_capacity");
																		String invmodel=(String)ht.get("inverter_model");
																		String quantity=(String)ht.get("quantity");
																		String payment_mode=(String)ht.get("payment_mode");
																		String payment_mode_type=(String)ht.get("payment_mode_type");
																		String state=(String)ht.get("state");
																		String consumer_address=(String)ht.get("consumer_address");
																		String city1=(String)ht.get("city");
																		String area=(String)ht.get("area");
																		String pincode=(String)ht.get("pincode");
																		String price=String.valueOf(ht.get("price"));
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

																		String operator=(String)ht.get("operator");
																		LogLevel.DEBUG(1,new Throwable(),"operator: "+operator);
																		
																		String confirm_by=(String)ht.get("confirm_by");
																		LogLevel.DEBUG(1,new Throwable(),"confirm_by: "+confirm_by);
																		
																		String first_contacted_date=(String)ht.get("first_contacted_date");
																		LogLevel.DEBUG(1,new Throwable(),"first_contacted_date: "+first_contacted_date);
																		
																		int ProductDiscountPrice_int_Temp = Integer.parseInt(price);
																		int QTY_int = Integer.parseInt(quantity);
																		int Final_Price_Int=ProductDiscountPrice_int_Temp*QTY_int;
																		String Final_Price = Integer.toString(Final_Price_Int);
																		

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
																		<td  class="insidecontent" align="left"><%=cusname%><br><%=cusemailid%><br><%=conmobile%></td>
																		
																		
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
																		

																		<td  class="insidecontent" align="left"><%=invbrand%> </td>
																		<td  class="insidecontent" align="left"><%=invcap%> </td>
																		<td  class="insidecontent" align="left"><%=invmodel%> </td>
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
																		else
																		{
																		}
																		%> 
																		
																		><a data-toggle="popover" title="Payment Mode" date-placement="top" data-content="<%=payment_mode%> - <%=payment_mode_type%> " style="color: #000001;  cursor: alias;"> <%=quantity%></a> </td>
																		<td  class="insidecontent" align="left">Rs.<%=price%>&nbsp;X&nbsp;<%=quantity%></a><br>=&nbsp;<%=Final_Price%>&nbsp;</td>
																		
																		<td  class="insidecontent" align="left"><%=orderstatus%> </td>
																		<td  class="insidecontent" align="left"><%=order_reasons%> </td>
																		<%if(confirm_by.equals("0"))
																		{
																		%>
																			<td  class="insidecontent" align="left">-</td>
																		<%
																		}
																		else
																		{
																		%>
																		<td  class="insidecontent" align="left">Confirmed by <%=confirm_by%> </td>
																		<%
																		}
																		%>
																		<td  class="insidecontent" align="left"><%=order_agent_comments%> </td>
																		
																		<%if(orderstatus.equals("Customer Contacted") && order_reasons.equals("installed"))
																		{
																		%>
																		<td  class="insidecontent" align="left">
																		<select disabled style='width:110px;height:30px;' name="<%=ord_id%>" id="<%=ord_id%>"><option selected value='Installed'>Installed</option></select> </td>
																		<%
																		}
																		else
																		{
																		%>
																		<td  class="insidecontent" align="left">
																		<select style='width:110px;height:30px;' name="<%=ord_id%>" id="<%=ord_id%>" onChange="javascript:Updatevisitorstatus('<%=ord_id%>','<%=ordnum%>','<%=state%>','<%=city1%>','<%=area%>','<%=payment_mode%>')"><option value=''>Update Status</option><option value="confirmed">Confirmed</option><option value='Customer Contacted'>Customer Contacted</option><option value='Customer Not Contacted'>Customer Not Contacted</option><option value='Postponed'>Postpone</option><option value='Repeated'>Repeated</option></select> </td>
																		<%
																		}
																		%>
																		
																		<td  class="insidecontent" align="left"><a data-toggle="popover" title="Address" date-placement="top" data-content="<%=consumer_address%> | Area : <%=area%> | Pincode :<%=pincode%>. " style="color: #000001;  cursor: alias;"> <%=state%></br><%=city1%></a> </td>
																		<td  class="insidecontent" align="left"><%=area%> </td>
																		<td  class="insidecontent" align="left"><%=pincode%> </td>
																		<td  class="insidecontent" align="left"><%=operator%> </td>
																		<td  class="insidecontent" align="left"><%=date%> </td>
																		<td  class="insidecontent" align="left"><%=first_contacted_date%></td>
																		<td  class="insidecontent" align="left"><%=updated_date%></td>
																		<td  class="insidecontent" align="left"><%=postponedate%> </td>
																		<td  class="insidecontent" align="left"><%=installed_date%> </td>
																		
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


<script type="text/javascript">
$(function () {
	$('.multiselect').multiselect({
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
	
	
function makeRequest(Url,orderid,ordernumber,orderstatus) {

//alert("inside makeRequest");
$.ajax
({					 
	type: "GET",
	dataType: "json",
	url: "https://api-ssl.bitly.com/v3/shorten",
	data:{access_token:"9d31af737fd0644e87be16ff102ac97d09dd554e",longUrl:Url},
	success: function(response)
	{	
		//alert(response.status_txt);
		var responsearr=response.data;
		//alert("responsearr"+responsearr.url);
		str =responsearr.url;
		document.getElementById("result").value = str;
		updatecustcontactedstatus(orderid,ordernumber,orderstatus);
	}
});	

}

function makeRequest1(Url,orderid,ordernumber,orderstatus) {

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

updatecustcontactedstatus(orderid,ordernumber,orderstatus);

}
else {
alert("Error: creating short url1 \n" + response.error);
}

});

}

function load() {
gapi.client.setApiKey('AIzaSyDV5_Ca9cEVSFaiLkyzGIcDcbnV_4CiA0o');
gapi.client.load('urlshortener', 'v1', function() { document.getElementById("result").innerHTML = ""; });
}
window.onload = load;
	
</script>


<script>
    // initialize of popover
	$(function (){
		$('[data-toggle="popover"]').popover()
	})
</script>
				

</form>
</center>
</body>
</html>