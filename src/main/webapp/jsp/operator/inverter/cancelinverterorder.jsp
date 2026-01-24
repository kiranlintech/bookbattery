<%--
    Document   : cancel inverter orders
    Created on : April 2, 2013, 11:22:12 AM
    Author     : Sai Krishna Daddala.
--%>
<%@ page language="java" import="java.sql.*"%>
<%@page language="java" import="java.util.ArrayList,java.util.Hashtable,java.util.Vector"%>
<%
String strUserid=(String)session.getAttribute("sesBatteryOperatorName");
if(strUserid==null)
{
	strUserid="";
	session.setAttribute("sesErrorMsg","Session Timed Out. Please login again");
	response.sendRedirect("../../../operator/index.html");
	return;
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../images/favicon.png" type="image/x-icon">
<title>BookBattery.com-India's No.1 Automobile Battery Store</title>
<script language="JavaScript" src="../../../js/jquery-1.8.2.js" ></script>
<script src="../../../js/jquery-1.3.2.min.js"></script>
<script language=javascript src="../../../js/datepicker.js"></script>
<style type='text/css'>

.divpostponed{left:57%;top:30%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left; border:6px solid #424242; border-radius: 6px 6px 6px 6px;position:absolute;padding: 1px;display: none;z-index:0;}
.divpostponed1{left:50.5%;top:30%;font: 16px/22px 'Trebuchet MS', Verdana, Arial; text-align:left; border:6px solid #424242; border-radius: 6px 6px 6px 6px;position:absolute;padding: 1px;display: none;z-index:0;}
table.tableizer-table {
	border: 1px solid #a7bc7a; font-family: Arial, Helvetica, sans-serif
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
<script type="text/javascript">
function funToGetOreRefNumber()
{
	var ordrefnumber = document.cancelorder.ordrefnumber.value;
 
	 if(ordrefnumber == "")
	 {
		errMsg ="<font color='#ff3333'>Please enter \'Ord Ref Number\'.</font>";
		document.getElementById("displyoredmsg").innerHTML=errMsg;
		document.cancelorder.ordrefnumber.focus();
		return ;
	 }
	if (/[ord][ORD]{2}/i.test(document.cancelorder.ordrefnumber.value) != true)
	{
		errMsg ="<font color='#ff3333'>\'Ord Ref Number\' format should be \'ORD123456\' .</font>";
		document.getElementById("displyoredmsg").innerHTML=errMsg;
		document.cancelorder.ordrefnumber.focus();
		return ;
	}



	var xmlhttp= "";
		var resp= "";
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
					if(resp=="ERROR")
					{
						alert("Error occurred.Please try again.");
						return;
					}
					else if(resp.indexOf("Invalid")>=0)
					{
					document.getElementById("divordrefnumber").innerHTML = resp;
					document.getElementById("divordrefnumber").innerHTML = xmlhttp.responseText;
					document.getElementById("displyoredmsg").innerHTML ="";
					}
					else
					{
					document.getElementById("divordrefnumber").innerHTML = resp;
					document.getElementById("divordrefnumber").innerHTML = xmlhttp.responseText;
					}
				}
			}			
		}
		xmlhttp.open("POST","../../../servlet/CancelOrdRefNumber?hidWhatToDo=getinverterordrefnumbernew&ordrefnumber="+ordrefnumber,true);	
		xmlhttp.send();	
		//document.getElementById("displyoredmsg").innerHTML ="";
}
function Updatetocancelorder(oreid,orderstatus)
{
	var orderedstatus = document.getElementById(oreid).value;
	if(orderedstatus=="postponed")
	{
		getPostponedDates(oreid,orderstatus)
	}
	else if(orderedstatus=="cancelled-customer")
	{
		getcancelledcustomer(oreid,orderstatus)
	}
	else if(orderedstatus=="cancelled-franchisee-offbushrs")
	{
		getcancelledoffbusinesshours(oreid,orderstatus)
	}
	else if(orderedstatus=="cancelled-franchisee-denied")
	{
		getcancelledfranchiseedenied(oreid,orderstatus)
	}
	else if(orderedstatus=="cancelled-franchisee-notresponded")
	{
		getcancellednotresponded(oreid,orderstatus)
	}
	else if(orderedstatus=="cancelled-franchisee-modeloutofstock")
	{
		getcancelledmodeloutofstock(oreid,orderstatus)
	}
	else if(orderedstatus=="cancelled-regenerated")
	{
		getcancelledregenerated(oreid,orderstatus)
	}
	else if(orderedstatus=="")
	{

	}
	else
	{
		var xmlhttp= "";
		var resp= "";
		var url ="../../../servlet/CancelOrdRefNumber?hidWhatToDo=cancelinverterorder&chkSi="+oreid+"&orderedstatus="+orderedstatus+"&orderstatus="+orderstatus;
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
					document.getElementById("displyoredmsg").innerHTML = resp;
					document.getElementById("displyoredmsg").innerHTML = xmlhttp.responseText;
					funToGetOreRefNumber()
				}
			}			
		}
		var agree=confirm("Are You sure want to update the Battery Status! ");
		if (agree)
		{
		xmlhttp.open("GET",url, true);		
		xmlhttp.send();	
		}
		if (!agree)
		{
			$("#"+oreid).html('<option value="">Select Status</option><option value="ordered">Ordered</option><option value="confirmed">Confirmed</option><option value="installed">Installed</option><option value="postponed">Postponed</option><option value="cancelled-franchisee-offbushrs">Cancelled-Franchisee-OffBusHrs</option><option value="cancelled-franchisee-denied">Cancelled-Franchisee-Denied</option><option value="cancelled-franchisee-notresponded">Cancelled-Franchisee-NotResponded</option><option value="cancelled-franchisee-modeloutofstock">Cancelled-Franchisee-ModelOutofStock</option><option value="cancelled-customer">Cancelled-Customer</option><option value="cancelled-regenerated">Cancelled-Regenerated</option>');
		}
	}

}
/*Code added by Prasanna for Cancelled-Customer Details*/
/*code starts for Franchisee Off business hours ordered status*/
function getcancelledoffbusinesshours(oreid,orderstatus)
{
	errMsg ="<table cellspacing='10' cellpadding='0' bgcolor='#FFFFFF'><table width='350' bgcolor='#FFFFFF' valign='top'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobilediv1();\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b>				</td></tr></table><tr><td><table width='350'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF'><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font size='2' color='#FF8C00'>Send&nbsp;Message&nbsp;to&nbspFranchisee&nbsp;to &nbsp;Cancel&nbsp;Order!</font></td></tr></table></td></tr><tr><td height='5'></td></tr><tr><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;Message</td></tr><tr><td height='2'></td></tr></tr><tr><td width='100%' align='center'><input class='insidecontent' type='text'  name='offbushrsmsg' id='offbushrsmsg' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='100' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:updateoffbusinessorders('"+oreid+"','"+orderstatus+"');return false;} else return true;\"/></td></tr><tr><td height='5'></td></tr><tr><td  width='50%' align='center' ><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:updateoffbusinessorders('"+oreid+"','"+orderstatus+"');\"></td></tr><tr height='26'><td colspan='3' align='center' class='subheading' id='displayrefinederrormsg1'></td></tr><tr><td height='15'></td></tr></table>";
	
	document.getElementById("divpostponed1").innerHTML=""; 
	document.getElementById("divpostponed1").style.display='block';
	document.getElementById("divpostponed1").innerHTML=errMsg
	document.cancelorder.offbushrsmsg.focus();
}
function updateoffbusinessorders(oreid,orderstatus)
{
	var orderedstatus = document.getElementById(oreid).value;
	var offbushrsmsg = document.cancelorder.offbushrsmsg.value;
	var iChars3 = "`~!@#$%^&*()+=[]\\\';/{}|\":<>?";
	var dot=".";

	if(offbushrsmsg == "")
     {
		errMsg ="<font color='#9B5BDD'>Please Enter Message</font>";
		document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
		document.cancelorder.offbushrsmsg.focus();
		return ;
     }
	  if (document.cancelorder.offbushrsmsg.value.indexOf(dot)==0 )
     {
			errMsg ="<font color='#9B5BDD'>Message should not start with dot</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.cancelorder.offbushrsmsg.focus();
			return ;
     }
     for (var i = 0; i < document.cancelorder.offbushrsmsg.value.length; i++)
     {
         if (iChars3.indexOf(document.cancelorder.offbushrsmsg.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#9B5BDD'>Special characters are not allowed in Message field.</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.cancelorder.offbushrsmsg.focus();
			return ;
         }
     }
    if (/[a-z][A-Z]{2}/i.test(document.cancelorder.offbushrsmsg.value) != true) 
		{
		  errMsg ="<font color='#9B5BDD'>Please enter atleast 3 Charaters together in the Message Field.</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.cancelorder.offbushrsmsg.focus();
			return ;
         }
	
	$('#divpostponed1').hide();
	var xmlhttp= "";
		var resp= "";
		var url ="../../../servlet/CancelOrdRefNumber?hidWhatToDo=cancelinverterorder&chkSi="+oreid+"&orderedstatus="+orderedstatus+"&orderstatus="+orderstatus+"&offbushrsmsg="+offbushrsmsg;
		
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
					document.getElementById("displyoredmsg").innerHTML = resp;
					document.getElementById("displyoredmsg").innerHTML = xmlhttp.responseText;
					funToGetOreRefNumber()
				}
			}			
		}
		xmlhttp.open("GET",url, true);		
		xmlhttp.send();	
}

/*code ended for Franchisee Off business hours ordered status*/
/*code starts for cancelled-franchisee-denied ordered status*/
function getcancelledfranchiseedenied(oreid,orderstatus)
{
	errMsg ="<table cellspacing='10' cellpadding='0' bgcolor='#FFFFFF'><table width='350' bgcolor='#FFFFFF' valign='top'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobilediv1();\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b>				</td></tr></table><tr><td><table width='350'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF'><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font size='2' color='#FF8C00'>Send&nbsp;Message&nbsp;to&nbspFranchisee&nbsp;to &nbsp;Cancel&nbsp;Order!</font></td></tr></table></td></tr><tr><td height='5'></td></tr><tr><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;Message</td></tr><tr><td height='2'></td></tr></tr><tr><td width='100%' align='center'><input class='insidecontent' type='text'  name='franchiseedenied' id='franchiseedenied' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='100' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:updatefranchiseedeniedorders('"+oreid+"','"+orderstatus+"');return false;} else return true;\"/></td></tr><tr><td height='5'></td></tr><tr><td  width='50%' align='center' ><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:updatefranchiseedeniedorders('"+oreid+"','"+orderstatus+"');\"></td></tr><tr height='26'><td colspan='3' align='center' class='subheading' id='displayrefinederrormsg1'></td></tr><tr><td height='15'></td></tr></table>";
	
	document.getElementById("divpostponed1").innerHTML=""; 
	document.getElementById("divpostponed1").style.display='block';
	document.getElementById("divpostponed1").innerHTML=errMsg
	document.cancelorder.franchiseedenied.focus();
}
function updatefranchiseedeniedorders(oreid,orderstatus)
{
	var orderedstatus = document.getElementById(oreid).value;
	var franchiseedenied = document.cancelorder.franchiseedenied.value;
	var iChars3 = "`~!@#$%^&*()+=[]\\\';/{}|\":<>?";
	var dot=".";

	if(franchiseedenied == "")
     {
		errMsg ="<font color='#9B5BDD'>Please Enter Message</font>";
		document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
		document.cancelorder.franchiseedenied.focus();
		return ;
     }
	  if (document.cancelorder.franchiseedenied.value.indexOf(dot)==0 )
     {
			errMsg ="<font color='#9B5BDD'>Message should not start with dot</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.cancelorder.franchiseedenied.focus();
			return ;
     }
     for (var i = 0; i < document.cancelorder.franchiseedenied.value.length; i++)
     {
         if (iChars3.indexOf(document.cancelorder.franchiseedenied.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#9B5BDD'>Special characters are not allowed in Message field.</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.cancelorder.franchiseedenied.focus();
			return ;
         }
     }
    if (/[a-z][A-Z]{2}/i.test(document.cancelorder.franchiseedenied.value) != true) 
		{
		  errMsg ="<font color='#9B5BDD'>Please enter atleast 3 Charaters together in the Message Field.</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.cancelorder.franchiseedenied.focus();
			return ;
         }
	
	$('#divpostponed1').hide();
	var xmlhttp= "";
		var resp= "";
		var url ="../../../servlet/CancelOrdRefNumber?hidWhatToDo=cancelinverterorder&chkSi="+oreid+"&orderedstatus="+orderedstatus+"&orderstatus="+orderstatus+"&franchiseedenied="+franchiseedenied;
		
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
					document.getElementById("displyoredmsg").innerHTML = resp;
					document.getElementById("displyoredmsg").innerHTML = xmlhttp.responseText;
					funToGetOreRefNumber()
				}
			}			
		}
		xmlhttp.open("GET",url, true);		
		xmlhttp.send();	
}
/*code ended for cancelled-franchisee-denied ordered status*/

/*code starts for cancelled-franchisee-notresponded ordered status*/
function getcancellednotresponded(oreid,orderstatus)
{
	errMsg ="<table cellspacing='10' cellpadding='0' bgcolor='#FFFFFF'><table width='350' bgcolor='#FFFFFF' valign='top'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobilediv1();\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b>				</td></tr></table><tr><td><table width='350'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF'><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font size='2' color='#FF8C00'>Send&nbsp;Message&nbsp;to&nbspFranchisee&nbsp;to &nbsp;Cancel&nbsp;Order!</font></td></tr></table></td></tr><tr><td height='5'></td></tr><tr><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;Message</td></tr><tr><td height='2'></td></tr></tr><tr><td width='100%' align='center'><input class='insidecontent' type='text'  name='notrespondedmsg' id='notrespondedmsg' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='100' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:updatenotrespondedorders('"+oreid+"','"+orderstatus+"');return false;} else return true;\"/></td></tr><tr><td height='5'></td></tr><tr><td  width='50%' align='center' ><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:updatenotrespondedorders('"+oreid+"','"+orderstatus+"');\"></td></tr><tr height='26'><td colspan='3' align='center' class='subheading' id='displayrefinederrormsg1'></td></tr><tr><td height='15'></td></tr></table>";
	
	document.getElementById("divpostponed1").innerHTML=""; 
	document.getElementById("divpostponed1").style.display='block';
	document.getElementById("divpostponed1").innerHTML=errMsg
	document.cancelorder.notrespondedmsg.focus();
}
function updatenotrespondedorders(oreid,orderstatus)
{
	var orderedstatus = document.getElementById(oreid).value;
	var notrespondedmsg = document.cancelorder.notrespondedmsg.value;
	var iChars3 = "`~!@#$%^&*()+=[]\\\';/{}|\":<>?";
	var dot=".";

	if(notrespondedmsg == "")
     {
		errMsg ="<font color='#9B5BDD'>Please Enter Message</font>";
		document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
		document.cancelorder.notrespondedmsg.focus();
		return ;
     }
	  if (document.cancelorder.notrespondedmsg.value.indexOf(dot)==0 )
     {
			errMsg ="<font color='#9B5BDD'>Message should not start with dot</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.cancelorder.notrespondedmsg.focus();
			return ;
     }
     for (var i = 0; i < document.cancelorder.notrespondedmsg.value.length; i++)
     {
         if (iChars3.indexOf(document.cancelorder.notrespondedmsg.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#9B5BDD'>Special characters are not allowed in Message field.</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.cancelorder.notrespondedmsg.focus();
			return ;
         }
     }
    if (/[a-z][A-Z]{2}/i.test(document.cancelorder.notrespondedmsg.value) != true) 
		{
		  errMsg ="<font color='#9B5BDD'>Please enter atleast 3 Charaters together in the Message Field.</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.cancelorder.notrespondedmsg.focus();
			return ;
         }
	
	$('#divpostponed1').hide();
	var xmlhttp= "";
		var resp= "";
		var url ="../../../servlet/CancelOrdRefNumber?hidWhatToDo=cancelinverterorder&chkSi="+oreid+"&orderedstatus="+orderedstatus+"&orderstatus="+orderstatus+"&notrespondedmsg="+notrespondedmsg;
		
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
					document.getElementById("displyoredmsg").innerHTML = resp;
					document.getElementById("displyoredmsg").innerHTML = xmlhttp.responseText;
					funToGetOreRefNumber()
				}
			}			
		}
		xmlhttp.open("GET",url, true);		
		xmlhttp.send();	
}
/*code ended for cancelled-franchisee-notresponded ordered status*/
/*code starts for cancelled-franchisee-out of Stock ordered status*/
function getcancelledmodeloutofstock(oreid,orderstatus)
{
	errMsg ="<table cellspacing='10' cellpadding='0' bgcolor='#FFFFFF'><table width='350' bgcolor='#FFFFFF' valign='top'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobilediv1();\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b>				</td></tr></table><tr><td><table width='350'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF'><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font size='2' color='#FF8C00'>Send&nbsp;Message&nbsp;to&nbspFranchisee&nbsp;to &nbsp;Cancel&nbsp;Order!</font></td></tr></table></td></tr><tr><td height='5'></td></tr><tr><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;Message</td></tr><tr><td height='2'></td></tr></tr><tr><td width='100%' align='center'><input class='insidecontent' type='text'  name='modeloutofstockmsg' id='modeloutofstockmsg' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='100' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:updatecancelledorders('"+oreid+"','"+orderstatus+"');return false;} else return true;\"/></td></tr><tr><td height='5'></td></tr><tr><td  width='50%' align='center' ><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:updatecancelledorders('"+oreid+"','"+orderstatus+"');\"></td></tr><tr height='26'><td colspan='3' align='center' class='subheading' id='displayrefinederrormsg1'></td></tr><tr><td height='15'></td></tr></table>";
	
	document.getElementById("divpostponed1").innerHTML=""; 
	document.getElementById("divpostponed1").style.display='block';
	document.getElementById("divpostponed1").innerHTML=errMsg
	document.cancelorder.modeloutofstockmsg.focus();
}
function updatecancelledorders(oreid,orderstatus)
{
	var orderedstatus = document.getElementById(oreid).value;
	var modeloutofstockmsg = document.cancelorder.modeloutofstockmsg.value;
	var iChars3 = "`~!@#$%^&*()+=[]\\\';/{}|\":<>?";
	var dot=".";

	if(modeloutofstockmsg == "")
     {
		errMsg ="<font color='#9B5BDD'>Please Enter Message</font>";
		document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
		document.cancelorder.modeloutofstockmsg.focus();
		return ;
     }
	  if (document.cancelorder.modeloutofstockmsg.value.indexOf(dot)==0 )
     {
			errMsg ="<font color='#9B5BDD'>Message should not start with dot</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.cancelorder.modeloutofstockmsg.focus();
			return ;
     }
     for (var i = 0; i < document.cancelorder.modeloutofstockmsg.value.length; i++)
     {
         if (iChars3.indexOf(document.cancelorder.modeloutofstockmsg.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#9B5BDD'>Special characters are not allowed in Message field.</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.cancelorder.modeloutofstockmsg.focus();
			return ;
         }
     }
    if (/[a-z][A-Z]{2}/i.test(document.cancelorder.modeloutofstockmsg.value) != true) 
		{
		  errMsg ="<font color='#9B5BDD'>Please enter atleast 3 Charaters together in the Message Field.</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.cancelorder.modeloutofstockmsg.focus();
			return ;
         }
	
	$('#divpostponed1').hide();
	var xmlhttp= "";
		var resp= "";
		var url ="../../../servlet/CancelOrdRefNumber?hidWhatToDo=cancelinverterorder&chkSi="+oreid+"&orderedstatus="+orderedstatus+"&orderstatus="+orderstatus+"&modeloutofstockmsg="+modeloutofstockmsg;
		
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
					document.getElementById("displyoredmsg").innerHTML = resp;
					document.getElementById("displyoredmsg").innerHTML = xmlhttp.responseText;
					funToGetOreRefNumber()
				}
			}			
		}
		xmlhttp.open("GET",url, true);		
		xmlhttp.send();	
}
/*code ended for cancelled-franchisee-out of Stock ordered status*/

/*code starts for cancelled-franchisee-Regenerated ordered status*/
function getcancelledregenerated(oreid,orderstatus)
{
	errMsg ="<table cellspacing='10' cellpadding='0' bgcolor='#FFFFFF'><table width='350' bgcolor='#FFFFFF' valign='top'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobilediv1();\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b>				</td></tr></table><tr><td><table width='350'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF'><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font size='2' color='#FF8C00'>Send&nbsp;Message&nbsp;to&nbspFranchisee&nbsp;to &nbsp;Cancel&nbsp;Order!</font></td></tr></table></td></tr><tr><td height='5'></td></tr><tr><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;Message</td></tr><tr><td height='2'></td></tr></tr><tr><td width='100%' align='center'><input class='insidecontent' type='text'  name='regeneratedmessage' id='regeneratedmessage' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='100' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:updateregeneratedorders('"+oreid+"','"+orderstatus+"');return false;} else return true;\"/></td></tr><tr><td height='5'></td></tr><tr><td  width='50%' align='center' ><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:updateregeneratedorders('"+oreid+"','"+orderstatus+"');\"></td></tr><tr height='26'><td colspan='3' align='center' class='subheading' id='displayrefinederrormsg1'></td></tr><tr><td height='15'></td></tr></table>";
	
	document.getElementById("divpostponed1").innerHTML=""; 
	document.getElementById("divpostponed1").style.display='block';
	document.getElementById("divpostponed1").innerHTML=errMsg
	document.cancelorder.regeneratedmessage.focus();
}
function updateregeneratedorders(oreid,orderstatus)
{
	var orderedstatus = document.getElementById(oreid).value;
	var regeneratedmessage = document.cancelorder.regeneratedmessage.value;
	var iChars3 = "`~!@#$%^&*()+=[]\\\';/{}|\":<>?";
	var dot=".";

	if(regeneratedmessage == "")
     {
		errMsg ="<font color='#9B5BDD'>Please Enter Message</font>";
		document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
		document.cancelorder.regeneratedmessage.focus();
		return ;
     }
	  if (document.cancelorder.regeneratedmessage.value.indexOf(dot)==0 )
     {
			errMsg ="<font color='#9B5BDD'>Message should not start with dot</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.cancelorder.regeneratedmessage.focus();
			return ;
     }
     for (var i = 0; i < document.cancelorder.regeneratedmessage.value.length; i++)
     {
         if (iChars3.indexOf(document.cancelorder.regeneratedmessage.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#9B5BDD'>Special characters are not allowed in Message field.</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.cancelorder.regeneratedmessage.focus();
			return ;
         }
     }
    if (/[a-z][A-Z]{2}/i.test(document.cancelorder.regeneratedmessage.value) != true) 
		{
		  errMsg ="<font color='#9B5BDD'>Please enter atleast 3 Charaters together in the Message Field.</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.cancelorder.regeneratedmessage.focus();
			return ;
         }
	
	$('#divpostponed1').hide();
	var xmlhttp= "";
		var resp= "";
		var url ="../../../servlet/CancelOrdRefNumber?hidWhatToDo=cancelinverterorder&chkSi="+oreid+"&orderedstatus="+orderedstatus+"&orderstatus="+orderstatus+"&regeneratedmessage="+regeneratedmessage;
		
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
					document.getElementById("displyoredmsg").innerHTML = resp;
					document.getElementById("displyoredmsg").innerHTML = xmlhttp.responseText;
					funToGetOreRefNumber()
				}
			}			
		}
		xmlhttp.open("GET",url, true);		
		xmlhttp.send();	
}
/*code ended for cancelled-Regenerated ordered status*/
/*Code ended by Prasanna for Cancelled-Customer Details*/
function getcancelledcustomer(oreid,orderstatus)
{
	errMsg ="<table cellspacing='10' cellpadding='0' bgcolor='#FFFFFF'><table width='350' bgcolor='#FFFFFF' valign='top'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobilediv1();\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b>				</td></tr></table><tr><td><table width='350'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF'><tr><td width='100%' colspan='3'><table width='100%' cellspacing='0' cellpadding='0'><tr><td class='insidecontent' align='center'><font size='2' color='#FF8C00'>Send&nbsp;Message&nbsp;to&nbspCustomer&nbsp;to &nbsp;Cancel&nbsp;Order!</font></td></tr></table></td></tr><tr><td height='5'></td></tr><tr><td width='100%' align='center' style='font-family:Verdana;font-size:10px;color:#999999;	text-decoration:none;padding:1px 1px;'><font color='#ff3333'>&nbsp;*&nbsp;</font>Enter&nbsp;Message</td></tr><tr><td height='2'></td></tr></tr><tr><td width='100%' align='center'><input class='insidecontent' type='text'  name='cusmessage' id='cusmessage' style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;' maxlength='100' onkeydown=\"if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:sendmessagetocust('"+oreid+"','"+orderstatus+"');return false;} else return true;\"/></td></tr><tr><td height='5'></td></tr><tr><td  width='50%' align='center' ><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:sendmessagetocust('"+oreid+"','"+orderstatus+"');\"></td></tr><tr height='26'><td colspan='3' align='center' class='subheading' id='displayrefinederrormsg1'></td></tr><tr><td height='15'></td></tr></table>";
	
	document.getElementById("divpostponed1").innerHTML=""; 
	document.getElementById("divpostponed1").style.display='block';
	document.getElementById("divpostponed1").innerHTML=errMsg
	document.cancelorder.cusmessage.focus();
}
function sendmessagetocust(oreid,orderstatus)
{
	var orderedstatus = document.getElementById(oreid).value;
	var cusmessage = document.cancelorder.cusmessage.value;
	var iChars3 = "`~!@#$%^&*()+=[]\\\';/{}|\":<>?";
	var dot=".";

	if(cusmessage == "")
     {
		errMsg ="<font color='#9B5BDD'>Please Enter Message</font>";
		document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
		document.cancelorder.cusmessage.focus();
		return ;
     }
	  if (document.cancelorder.cusmessage.value.indexOf(dot)==0 )
     {
			errMsg ="<font color='#9B5BDD'>Message should not start with dot</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.cancelorder.cusmessage.focus();
			return ;
     }
     for (var i = 0; i < document.cancelorder.cusmessage.value.length; i++)
     {
         if (iChars3.indexOf(document.cancelorder.cusmessage.value.charAt(i))!= -1)
         {
			errMsg ="<font color='#9B5BDD'>Special characters are not allowed in Message field.</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.cancelorder.cusmessage.focus();
			return ;
         }
     }
    if (/[a-z][A-Z]{2}/i.test(document.cancelorder.cusmessage.value) != true) 
		{
		  errMsg ="<font color='#9B5BDD'>Please enter atleast 3 Charaters together in the Message Field.</font>";
			document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
			document.cancelorder.cusmessage.focus();
			return ;
         }
	
	$('#divpostponed1').hide();
	var xmlhttp= "";
		var resp= "";
		var url ="../../../servlet/CancelOrdRefNumber?hidWhatToDo=cancelinverterorder&chkSi="+oreid+"&orderedstatus="+orderedstatus+"&orderstatus="+orderstatus+"&cusmessage="+cusmessage;
		
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
					document.getElementById("displyoredmsg").innerHTML = resp;
					document.getElementById("displyoredmsg").innerHTML = xmlhttp.responseText;
					funToGetOreRefNumber()
				}
			}			
		}
		xmlhttp.open("GET",url, true);		
		xmlhttp.send();	
}
function getPostponedDates(oreid,orderstatus)
{
	errMsg ="<table  width='360 cellspacing='10' cellpadding='0' bgcolor='#FFFFFF' border='0'><tr><td><table width='360' bgcolor='#FFFFFF' valign='top' border='0'><tr height='30'><td align='right' valign='top' style='cursor:pointer;' bgcolor='#E6E6E6'><font color='red' size='3'><b><a href=\"javascript:closemobilediv();\" style='color:#848484;text-decoration:none;'>X&nbsp;&nbsp;</a></b></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr><td width='80%' colspan='3' align='center'><font size='2' color='#FF8C00'>Please Select Postpone Date&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font></td><td width='20%'></td></tr><tr><td height='10' width='80%'></td><td width='20%'></td></tr><tr><td width='80%' align='right'><input type='ext' name='postponedate' class='insidecontent' readonly  onChange='CheckDate(this)' onKeyDown='FormatDate(this,  window.event.keyCode,'down')' onKeyUp='FormatDate(this, window.event.keyCode,'up')' value='' size='10' maxlength='10'  style='width:195px;height:20px;border: 2px solid #CCC;font-size: 13px;font-family: Verdana;border-radius: 4px 4px 4px 4px;'  /></td><td width='20%' align='center'><img src='../../../images/calender.jpg' valign='bottom' style='cursor:hand' onclick=\"javascript:displayDatePicker('postponedate', this);\" height='25' ></td></tr><tr><td height='5' width='80%'></td><td height='5' width='20%'></td></tr><tr>	<td width='63%' class='insidecontent' align='right' style='width:155px; bgcolor:#FFF;'><div class='styled-select'><select name='postponemessage'  id='postponemessage' class='insidecontent' STYLE='width: 200px' ><option value='0' >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&lt;-- Select Reason--&gt;</option><option value='Customer is not picking the Call' >Customer is not picking the Call</option><option value='Customer Number Busy'>Customer Number is Busy</option><option value='Customer Number is Not Reachable' >Customer Number is Not Reachable</option><option value='Customer Number Switched Off' >Customer Number is Switched Off</option><option value='Customer is Out of Station' >Customer is Out of Station</option><option value='Customer is Not Responding to our calls'>Customer is Not Responding to our calls</option><option value='Customer need Installation Today' >Customer need Installation Tomorrow</option><option value='Need to check with franchisee whether the Inverter is currently available' >Franchisee is not having the Stock</option><option value='Customer Old Inverter is Working Fine need installation today' >Customer Old Inverter is Working Fine Presently</option><option value='Old Inverter is working Fine He need installation Today' >Old Inverter is working Fine, He donot need installation Now</option><option value='Yesterdays After Business Hour Order' >As it is not business hours, we will install the Inverter tomorrow</option><option value='Pitstop is So Far Customer will collect the Inverter whenever he is free' >Pitstop is SoFar,  Customer will collect the Inverter whenever he is free</option><option value='Customer need installation today' >Customer is busy today, he need installation later</option></option><option value='Customer need latest Manufacturing Inverter' >Customer need latest Manufacturing Inverter</option><option value='Order Status is not yet confirmed from Franchisee or Customer' >Order Status is not yet confirmed from Franchisee or Customer</option><option value='Customer will collect the Inverter today' >Customer will collect the Inverter tomorrow</option><option value='Pitstop was on Leave Yesterday Need to process the order today' >Franchisee is on Leave Today</option></select></div></td></tr><tr><td height='5' width='80%'></td><td height='5' width='20%'></td></tr><tr>&nbsp;&nbsp;&nbsp;<td  width='80%' align='right' ><input type='button' class='BookBatterysubmit' name='Submitrret' value='Submit' disable='false' onclick=\"javascript:updatepostponestatus('"+oreid+"','"+orderstatus+"');\"></td><td width='20%'></td></tr></table><table width='100%'  cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' style='padding-left:10px;padding-right:10px;' border='0'><tr height='16'><td width='100%' colspan='3' align='center' class='subheading' id='displayrefinederrormsg1'></td></tr><tr><td height='10'></td></tr></table></td></tr></table>";
	document.getElementById("divpostponed").innerHTML=""; 
	document.getElementById("divpostponed").style.display='block';
	document.getElementById("divpostponed").innerHTML=errMsg
}
function updatepostponestatus(oreid,orderstatus)
{
	
	var orderedstatus = document.getElementById(oreid).value;
	var postponedate = document.cancelorder.postponedate.value;
	var postponemessage = document.cancelorder.postponemessage.value;
	//alert(postponemessage);
	var mySplitResult = postponedate.split("-");
	var postday=mySplitResult[0];
	var postmonth=mySplitResult[1];
	var postyear=mySplitResult[2];
	date = new Date();
	var month = date.getMonth()+1;
	var day = date.getDate();
	var year = date.getFullYear();
	if(postponemessage == "0")
	{
		$('#displayrefinederrormsg1').show();
		errMsg ="<font color='#9B5BDD'>Please select postpone reason</font>";
		document.getElementById("displayrefinederrormsg1").style.display='block';
		document.getElementById("displayrefinederrormsg1").innerHTML=errMsg;
	}
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
	else
	{
		$('#divpostponed').hide();
		var xmlhttp= "";
		var resp= "";
		var url ="../../../servlet/CancelOrdRefNumber?hidWhatToDo=cancelinverterorder&chkSi="+oreid+"&orderedstatus="+orderedstatus+"&orderstatus="+orderstatus+"&postponedate="+postponedate+"&postponemessage="+postponemessage;
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
					document.getElementById("displyoredmsg").innerHTML = resp;
					document.getElementById("displyoredmsg").innerHTML = xmlhttp.responseText;
					funToGetOreRefNumber()
				}
			}			
		}
		var agree=confirm("Are You sure want to update the Battery Status! ");
		if (agree)
		{
		xmlhttp.open("GET",url, true);		
		xmlhttp.send();	
		}
	}
	
}
function closemobilediv()
{
	$('#divpostponed').hide();
}
function closemobilediv1()
{
	$('#divpostponed1').hide();
}
function FunReset()
{
	location.href="../../../jsp/operator/inverter/cancelinverterorder.jsp"
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
</style>
<link href="../../../css/bookbattery.css" rel="stylesheet" type="text/css" />
</head>
<body onload="document.cancelorder.ordrefnumber.focus();" >
<!-- Start Alexa Certify Javascript -->
<script type="text/javascript">
_atrk_opts = { atrk_acct:"VrG0j1a4ZP00yt", domain:"BookBattery.com",dynamic: true};
(function() { var as = document.createElement('script'); as.type = 'text/javascript'; as.async = true; as.src = "https://d31qbv1cthcecs.cloudfront.net/atrk.js"; var s = document.getElementsByTagName('script')[0];s.parentNode.insertBefore(as, s); })();
</script>
<noscript><img src="https://d5nxst8fruw4z.cloudfront.net/atrk.gif?account=VrG0j1a4ZP00yt" style="display:none" height="1" width="1" alt="" /></noscript>
<!-- End Alexa Certify Javascript -->
<form name="cancelorder" method="post" ENCTYPE="multipart/form-data">
<div id='divpostponed' class='divpostponed' style="display:none;"></div>
<div id='divpostponed1' class='divpostponed1' style="display:none;"></div>
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
					<table width="100%" cellspacing="1" bgcolor="#FFFFFF" cellpadding="0" border="0">
					<tr>
						<td align="left" valign="top">
							<tr>
								<td class="subheading">Cancel&nbsp;Inverter&nbsp;Order</td>
								<td  align="right"><a href="../../../servlet/OperatorLogin?hidWhatToDo=batteryoperatorhome" class="onclick1">Back&nbsp;&nbsp;</a></td>
							</tr>
							<tr><td height="25"></td></tr>
							<tr><td align="center" class="insidecontentbat"><div id="displyoredmsg"></div></td></tr>
							<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
							<tr>
									<td width="30%" class="insidecontent" align="right">Enter&nbsp;Ord&nbsp;Ref&nbsp;Number<font color="#ff3333">*</font>&nbsp;:</td>
									<td width="50%" class="insidecontent" align="left"><input type="text" name="ordrefnumber" size="20" onkeydown="if ((event.which && event.which == 13) || (event.keyCode && event.keyCode == 13)){javascript:funToGetOreRefNumber(this);return false;} else return true;" maxlength="15"></td>
								</tr>
								</table>
								</td>
								</tr>
								<tr>
								<td>
								<table width="80%" cellspacing="2" bgcolor="#FFFFFF" cellpadding="0" border="0" align="center">
								<tr>
									<td width="50%" class="insidecontent" align="right"><input type="button" value="Submit" class="button4" onclick="javascript:funToGetOreRefNumber(this);"></td>
									<td width="50%" class="insidecontent" align="left"><input type="button" value="Reset" class="button4" onclick="javascript:FunReset();"></td>
								</tr>
								<tr><td height="15"></td><td></td></tr>
								</table>
								<tr>
									<td>
										<table width="100%" border="0" align="center">
											<tr class="#FFFFFF" bgcolor="FFFFFF">
												<td width="40%"><div id="divordrefnumber"></div></td>
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
<input type="hidden" name="strEmail" value="">
</form>
</center>
<%
String strbatLogMsg=(String)session.getAttribute("sesbatterydetailsErrorMsg");
if(strbatLogMsg ==null)
{
	strbatLogMsg="";
}
else
{
%>
<script type="text/javascript">
var sesmessg; 
sesmessg= "<%=strbatLogMsg%>";
document.getElementById("displysesmsg").innerHTML=sesmessg;
</script>
<%
	session.removeAttribute("sesbatterydetailsErrorMsg");
}
%>
</body>
</html>