<%-- 
    Document   : serviceprovider.
    Created on : March 18, 2012, 4:22:12 PM
    Author     : jhansi A
--%>
<!@page language="java">

<%@page language="java" import="java.util.ArrayList,java.util.Hashtable,java.util.Vector,com.ngit.javabean.loglevel.LogLevel"%>
<%
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
	session.removeAttribute("sesErrorMsg");
}

Vector alist=(Vector)session.getAttribute("result");
 


String strDateOpt=request.getParameter("dates");
if(strDateOpt==null || strDateOpt.equals(""))
strDateOpt="current";

//Get FromDate(i.e returns the FromDate of the category as a string or null )
String strFromDate=(request.getParameter("txtFromDate")!=null)?request.getParameter("txtFromDate"):"";
LogLevel.DEBUG(5,new Throwable(),"strFromDate :"+strFromDate);

//Get ToDate(i.e returns the ToDate of the category as a string or null )
String strToDate=(request.getParameter("txtToDate")!=null)?request.getParameter("txtToDate"):"";
LogLevel.DEBUG(5,new Throwable(),"strToDate :"+strToDate);

//Get pagenumber(i.e returns the pagenumber of the category as a string or null )
String pagenumber=(request.getParameter("pagenumber")!=null)?request.getParameter("pagenumber"):"";
LogLevel.DEBUG(5,new Throwable(),"pagenumber :"+pagenumber);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<link rel="shortcut icon" href="../../../../images/favicon.png" type="image/x-icon">
<title>BookBattery.com - India's No.1 Automobile Battery Store</title>


<script src="../../../../js/jquery-1.3.2.min.js"></script>
<script src="../../../../js/pophide.js"></script>
<script language="javascript">

 function deleterecharge()
    {
		var pagenumber=document.serviceprovider.pagenumber.value;
		var txtFromDate=document.serviceprovider.txtFromDate.value;
		var txtToDate=document.serviceprovider.txtToDate.value;
		var dates=document.serviceprovider.dates.value;
		var serviecprovider=document.serviceprovider.rdbWhatSelected.value;

		 var venid=0;
		
		for (i = 0; i<document.serviceprovider.check.length; i++)
		{
			if(document.serviceprovider.check[i].checked)
			{
				venid++;
 				
			}
		}
		if(venid==0)
		{
			//alert("Please Select One Check Box to delete");
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please Select One Check Box to delete</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popups();' class='button4'><br></td></tr>";
			greyout(true);
			document.getElementById("popup").style.display='block';
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		else if(venid >1)
		{
			//alert("Please Select only One Check Box to delete online survey");
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please Select only One Check Box to delete redeem mobile recharge</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popups();' class='button4'><br></td></tr>";
			greyout(true);
			document.getElementById("popup").style.display='block';
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		var agree=confirm("Are you sure you wish to Delete?");
		if (agree)
		{
		document.serviceprovider.method="post";
        document.serviceprovider.action="../../../../servlet/BrandLoyalty?hidWhatToDo=deleterechargeforreedempoints&date="+dates+"&txtFromDate="+txtFromDate+"&txtToDate="+txtToDate+"&pagenumber="+pagenumber+"&serviecprovider="+serviecprovider;
		
        document.serviceprovider.submit();
		}
	}


 function recharge()
    {
		var pagenumber=document.serviceprovider.pagenumber.value;
		var txtFromDate=document.serviceprovider.txtFromDate.value;
		var txtToDate=document.serviceprovider.txtToDate.value;
		var dates=document.serviceprovider.dates.value;
		var serviecprovider=document.serviceprovider.rdbWhatSelected.value;

			
			
		 var venid=0;
		for (i = 0; i<document.serviceprovider.check.length; i++)
		{
			if(document.serviceprovider.check[i].checked)
			{
				venid++;
 				
			}
		}
		
		if(venid==0)
		{
			//alert("Please Select One Check Box to recharge");
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please Select One Check Box to recharge</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popups();' class='button4'><br></td></tr>";
			greyout(true);
			document.getElementById("popup").style.display='block';
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
		else if(venid >1)
		{
			//alert("Please Select only One Check Box to recharge");
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please Select only One Check Box to recharge</br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='emailok' id='emailok' value='OK' onclick='close_popups();' class='button4'><br></td></tr>";
			greyout(true);
			document.getElementById("popup").style.display='block';
			document.getElementById("popupmessage").innerHTML=errMsg
			return;
		}
	
		else
		{
			errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'><br>Confirm code :  &nbsp;&nbsp;</br><br></td><td><input type='text'  name='rechargeconfirmcode' value='' size='10' autofocus='autofocus' ></td></tr><tr height='20'><td><tr class='pages'></tr><tr height='20'><td></td><td align='center'><input type='button' name='Add' id='Add' value='Add' onclick=\" closepopupadd(greyout(false)); afterrechargeconfirmcode();\" class='button4'></td></tr></table>";
		greyout(true);
		document.getElementById("popup1").style.display='block';
		document.getElementById("popupmessage1").innerHTML=errMsg
			document.serviceprovider.rechargeconfirmcode.focus();
		}
	}

	 function afterrechargeconfirmcode()
    {
		var pagenumber=document.serviceprovider.pagenumber.value;
		var txtFromDate=document.serviceprovider.txtFromDate.value;
		var txtToDate=document.serviceprovider.txtToDate.value;
		var dates=document.serviceprovider.dates.value;
		var serviecprovider=document.serviceprovider.rdbWhatSelected.value;

		var rechargeconfirmcode=document.serviceprovider.rechargeconfirmcode.value;

	if(rechargeconfirmcode=="")
	{
		//If admin doesn't enter any message into the text field.
		errMsg ="<br><table align='center' border='0' cellpadding='0' cellspacing='0'><tr class='pages'><td align='center'>Please enter recharge confirm code </br></td></tr><tr height='20'><td></td></tr><tr height='20'><td align='center'><input type='button' name='ok' id='ok' value='OK' onclick='closePopupconfirmcode();' class='button4'><br></td></tr>";
		greyout(true);
		document.getElementById("popupc").style.display='block';
		document.getElementById("popupmessagec").innerHTML=errMsg
		//document.fetchreportabuse.message.focus();
		return;
	}
			
			document.serviceprovider.method="post";
			document.serviceprovider.action="../../../../servlet/BrandLoyalty?hidWhatToDo=rechargeforreedempoints&date="+dates+"&txtFromDate="+txtFromDate+"&txtToDate="+txtToDate+"&pagenumber="+pagenumber+"&serviecprovider="+serviecprovider+"&rechargeconfirmcode="+rechargeconfirmcode;	
			document.serviceprovider.submit();
		
	}
	function closePopupconfirmcode()
{
	$('#popupc').hide();
	$('#popup1').show();
	//greyout(false);
	document.serviceprovider.rechargeconfirmcode.focus();
}
function close_popups()
{
$('#popup').hide();
greyout(false);

}
function closepopupadd()
{
	$('#popup1').hide();
	greyout(false);
}

/* ******************************************************************\
* This function is used to show online survey details when admin needs to show online survey details.
* Path of the java class to perform this function.
* home/ngit/tomcat/webapps/bookbattery/WEB-INF/classes/com/ngit/servlets/admin/loyaltypoints/ReedemPoints.
* Action used in this java class-onlinesurveydetails.
\* ********************************************************************/
function showReedemrechargedetails()
{


	var txtFromDate=document.serviceprovider.txtFromDate.value;
	var txtToDate=document.serviceprovider.txtToDate.value;
	var dates=document.serviceprovider.dates.value;
	var varRadioGroup = document.forms["serviceprovider"].rdbWhatSelected;
		
	for (i=0;i<varRadioGroup.length;i++)
	{
		if(varRadioGroup[i].checked)
		{
			reportType=varRadioGroup[i].value;
 		}
	}
	

var xmlhttp;
var url="../../../../servlet/BrandLoyalty?hidWhatToDo=fetchreedempointstorecharge&dates="+dates+"&txtFromDate="+txtFromDate+"&txtToDate="+txtToDate+"&serviecprovider="+reportType+"&requestno="+(Math.random()*100);


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

if(xmlhttp.readyState!=4)
{
document.getElementById("otherReedempoints").innerHTML="<span class='style1'><center><br><br><b><img src='../../../../images/loader.gif' align='center'><br>Please wait! Fetching details of redeem recharge.</b></center><br><br></span>";
}

if (xmlhttp.readyState==4 && xmlhttp.status==200)
{
res=xmlhttp.responseText;

document.getElementById("otherReedempoints").innerHTML="";
document.getElementById("otherReedempoints").innerHTML=xmlhttp.responseText;
}

}
xmlhttp.open("GET",url,true);
xmlhttp.send();		

}
function funOnClickPreviousonlinesurveys(pagenumber,serviecprovider)
{
var txtFromDate=document.serviceprovider.txtFromDate.value;
var txtToDate=document.serviceprovider.txtToDate.value;
var dates=document.serviceprovider.dates.value;

var url="../../../../servlet/BrandLoyalty?hidWhatToDo=fetchreedempointstorecharge&pagetype=previous&pagenumber="+pagenumber+"&serviecprovider="+serviecprovider+"&dates="+dates+"&txtFromDate="+txtFromDate+"&txtToDate="+txtToDate+"&requestno="+(Math.random()*100);




if (window.XMLHttpRequest)
{	
// code for IE7+, Firefox, Chrome, Opera, Safari
xmlhttp=new XMLHttpRequest();
}
else
{	// code for IE6, IE5
xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
}

xmlhttp.onreadystatechange=function()
{

	if(xmlhttp.readyState!=4)
	{
	document.getElementById("otherReedempoints").innerHTML="<br><br><br><br><center><img src='../../../../images/loader.gif' align='center'><br><br><span class='style1'>Please wait! Fetching details.</span></center>";
	}
	if (xmlhttp.readyState==4 && xmlhttp.status==200)
	{
	document.getElementById("otherReedempoints").innerHTML="";
	document.getElementById("otherReedempoints").innerHTML=xmlhttp.responseText;

	}
	}
	xmlhttp.open("POST",url,true);
	xmlhttp.send();

}

function funOnClickNextonlinesurveys(pagenumber,serviecprovider)
{
var txtFromDate=document.serviceprovider.txtFromDate.value;
var txtToDate=document.serviceprovider.txtToDate.value;
var dates=document.serviceprovider.dates.value;
	
var url="../../../../servlet/BrandLoyalty?hidWhatToDo=fetchreedempointstorecharge&pagetype=next&pagenumber="+pagenumber+"&serviecprovider="+serviecprovider+"&dates="+dates+"&txtFromDate="+txtFromDate+"&txtToDate="+txtToDate+"&requestno="+(Math.random()*100);



	if (window.XMLHttpRequest){// code for IE7+, Firefox, Chrome, Opera, Safari
	  xmlhttp=new XMLHttpRequest();
	}else{// code for IE6, IE5
	  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	xmlhttp.onreadystatechange=function()
	  {

		if(xmlhttp.readyState!=4){
			document.getElementById("otherReedempoints").innerHTML="<br><br><br><br><center><img src='../../../../images/loader.gif' align='center'><br><br><span class='style1'>Please wait! fetching details.</span></center>";
		}
	  if (xmlhttp.readyState==4 && xmlhttp.status==200)
		{
		document.getElementById("otherReedempoints").innerHTML="";
		document.getElementById("otherReedempoints").innerHTML=xmlhttp.responseText;

		}
	  }
	xmlhttp.open("POST",url,true);
	xmlhttp.send();
	
}

function closePopup1()
{
	$('#popup1').hide();
	
	greyout(false);
	
}

function closePopup2()
{
	$('#popup').hide();
	
	greyout(false);
	
}

</script>
<link href="../../../../css/bookbattery.css" rel="stylesheet" type="text/css" />
</head>
<body onload="showReedemrechargedetails();" onkeydown="if(($('#popup').is(':visible')) || ($('#popup1').is(':visible')) || ($('#popupc').is(':visible')) ){ if(event.keyCode==9){ return false;}};">
<center>
<form name="serviceprovider" method="post" >

<div id='popup' class='popup' style="display:none;">
	<div id='popuptitle' class='popuptitle'><table border='0' width='410px' height='10px' cellpadding='0' cellspacing='0'>
              <tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'>
                	<a href='javascript:closePopup2(greyout(false));' class="bluelinks333">x</a></td></tr></table>
	</div>
	<div id='popupmessage' class='popupmessage'></div> 
</div>

<div id='popup1' class='popup' style="display:none;">
	<div id='popuptitle1' class='popuptitle'><table border='0' width='410px' height='10px' cellpadding='0' cellspacing='0'>
              <tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'>
                	<a href='javascript:closePopup1(greyout(false));' class="bluelinks333">x</a></td></tr></table>
	</div>
	<div id='popupmessage1' class='popupmessage'></div> 
</div>

<div id='popupc' class='popup' style="display:none;">
	<div id='popuptitle' class='popuptitle'><table border='0' width='410px' height='10px' cellpadding='0' cellspacing='0'>
              <tr class='top1'><td>&nbsp;<font color='#FFFFFF'>BookBattery</td><td align='right'>
                	<a href='javascript:closePopupconfirmcode();' class="bluelinks333">x</a></td></tr></table>
	</div>
	<div id='popupmessagec' class='popupmessage'></div> 
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
			<table width="960" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
				<tr>
					<td width="180" height="438" align="left" valign="top">
						<table width="180" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
							<tr>
								<td width="180" height="438" align="left" valign="top">
									<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
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
									<table width="513" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF">
									<!-- Inner content should be within the below table -->
									<table width="770" border="1" cellpadding="5" cellspacing="0" bgcolor="#FFFFFF">
									<td width="680" align="left" valign="top" > <input type="checkbox" value="" name="check" style="display:none">
									<tr>
										<td>
											<table width="713" cellspacing="1" cellpadding="0">
											<tr>
												<td class="subheading" size="2">Redeem Loyalty Points >> selectdates >> serviceprovider</td>
											</tr>
											<tr>
												<td width="35%" align="right"><a href="../../../../jsp/admin/batterystore/brandloyaltyprogram/selectdates.jsp" class="onclick1">Back&nbsp;&nbsp;</a></td>
											</tr>
										<tr>
										<td>
											<table width="100%" cellspacing="1" bgcolor="#8B8D90" cellpadding="0">
												<tr>
													<td  bgcolor="#CCCCCC" align="center" class="subheading"><font color="#000000">Service provider</font></td>
												</tr>
												<tr>
													<td bgcolor="#FFFFFF" align="center">
														<table width="500" border="0" align="center" cellspacing="0" bgcolor="#FFFFFF" cellpadding="5">
															<tr >
																<td colspan="2" height="20">&nbsp;</td>
															</tr>
																<%
																if(alist==null || alist.size() == 0)
																{
																%>
																<tr>
																	<td colspan="3" align="center"> There are no Redeem points to recharge.
																	</td>
																</tr>	

															<%
																}
															else
															{
																		try
																		{
																			
																			for ( int i=0; i<alist.size() ; i++)
																			{
																				Hashtable getserviceproviders=(Hashtable)alist.get(i);
																				String strmobile_serviceprovider=String.valueOf(getserviceproviders.get("mobile_serviceprovider"));
																				%>
															<tr>
																	<td class="table1" align="right" width="50%"><input type="radio" name="rdbWhatSelected" onclick="javascript:showReedemrechargedetails()" value="<%=strmobile_serviceprovider%>"></td>
																	<td class="table1" align="left" width="50%"><%=strmobile_serviceprovider%></td>
																</tr>
																<%}

																		}
																		catch(Exception e)
																		{
																			e.printStackTrace();
																		}

																		%>
																			<tr>
																	<td class="table1" align="right" width="50%"><input type="radio" name="rdbWhatSelected" value="overall" checked onclick="javascript:showReedemrechargedetails()" ></td>
																	<td class="table1" align="left" width="50%">overall</td>
																</tr>

																<!--<tr>
																	<td align="center" colspan="9">
																		<table border="0" align="center">
																			<td>
																				<tr>
																					<td class="button4"><a href="javascript:showReedemrechargedetails()" class="button4">Submit</a></td>
																				</tr>
																			<tr>
																				<td>&nbsp;</td>
																			</tr>
																		</td>
																	</table>
																	
																</td>
															</tr>-->
															<%}%>
														</table>
													
													</td>
												</tr>
											</table>
													<tr>
		<td>
		<div id="otherReedempoints">

		</div>
		</td>
		</tr>	
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
		
	<tr>
		<td>
			<!-- footer page starts here  -->
			<jsp:include page = "../footer.jsp" />
			<!-- top page ends here  -->
		</td>
	</tr>
</table>
<input type="hidden" name="hidWhatToDo" value="overallmis">
<input type="hidden" name="dates" value="<%=strDateOpt%>">
<input type="hidden" name="txtFromDate" value="<%=strFromDate%>">
<input type="hidden" name="txtToDate" value="<%=strToDate%>">
<input type="hidden" name="pagenumber" value="<%=pagenumber%>">

</form>
</center>
</body>
</html>